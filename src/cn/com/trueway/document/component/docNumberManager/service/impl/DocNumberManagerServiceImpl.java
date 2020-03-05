package cn.com.trueway.document.component.docNumberManager.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.velocity.runtime.directive.Define;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.document.component.docNumberManager.dao.DocNumberManagerDao;
import cn.com.trueway.document.component.docNumberManager.dao.StrategyDao;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberDoc;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberFlow;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberModel;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberType;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhBw;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhFw;
import cn.com.trueway.document.component.docNumberManager.model.vo.DocNumber;
import cn.com.trueway.document.component.docNumberManager.model.vo.DocNumberSmallClass;
import cn.com.trueway.document.component.docNumberManager.service.DocNumberManagerService;
import cn.com.trueway.document.component.docNumberManager.unit.ManageStrategyParse;

/**
 * 
 * 描述：文号管理端的业务处理<br>
 * 作者：张亚杰<br>
 * 创建时间：2011-12-8 上午10:11:39<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class DocNumberManagerServiceImpl implements DocNumberManagerService{
	private Logger logger = Logger.getLogger(this.getClass());
	private DocNumberManagerDao docNumberManagerDao;
	private StrategyDao strategyDao;
	public StrategyDao getStrategyDao() {
		return strategyDao;
	}
	public void setStrategyDao(StrategyDao strategyDao) {
		this.strategyDao = strategyDao;
	}

	public DocNumberManagerDao getDocNumberManagerDao() {
		return docNumberManagerDao;
	}
	public void setDocNumberManagerDao(DocNumberManagerDao docNumberManagerDao) {
		this.docNumberManagerDao = docNumberManagerDao;
	}

	public List<DocNumberType> getDocNumBigClassList(String sql, Integer currentPage, Integer numPerPage) {
		return docNumberManagerDao.getDocNumBigClassList(sql, currentPage, numPerPage);
	}

	public String editDocNumType(DocNumberType type,String op) {
		if(CommonUtil.stringIsNULL(type.getTypeid())){
			//新增
			docNumberManagerDao.addDocNumber(type);
			return "addOk";
		}else{
			//更新
			docNumberManagerDao.updateDocNumber(type);
			return "modifyOk";
		}
	}
	
	public DocNumberType getSingleDocNumTypeById(String typeid){
		return docNumberManagerDao.getSingleDocNumberType(typeid);
	}
	public List<DocNumberSmallClass> getDocNumSmallClass(int pageIndex, int pageSize, String name, String parentId,String workflowId) {
		List<DocNumberSmallClass> dt = docNumberManagerDao.getDocNumSmallClass(pageIndex, pageSize, name, parentId,workflowId);	
		return dt;
	}
	public int getDocnumberTypeCount(String webid,String isparent){
		return docNumberManagerDao.getDocnumberTypeCount(webid, isparent);
	}
	
	public void addDocNumberDocs(String doctypeid,String docNumModels,String allmodelids){
		if(doctypeid!=null&&doctypeid.trim().length()!=0&&docNumModels!=null&&docNumModels.trim().length()!=0){
			//得到流程绑定的文号实例id
			String str = this.getBindDocNumModelIds(doctypeid);
			// 得到同一流程 同一部门的  文号实例id；
			List<DocNumberDoc>  docNums = getWHModelsByDoctypeid(doctypeid);
			List<String> list = new ArrayList<String>();
			//将所有id存入List集合
			if(allmodelids.length()!=0&&allmodelids.contains(",")){
				for(String s : allmodelids.split(",")){
					list.add(s);
				}
			}
			if(docNumModels.contains(",")){
				//将前台提交的数据入库,库中已有就将list中清除
				String[] docNumModel = docNumModels.split(",");
				for(String id : docNumModel){
					if(!str.contains(id)){
						DocNumberDoc doc = new DocNumberDoc(id,doctypeid);
						docNumberManagerDao.addDocNumberDoc(doc);
					}else{
						list.remove(id);
					}
				}
			}else{
				// docNumModels 只用一个
				if(!str.contains(docNumModels)){
					DocNumberDoc doc = new DocNumberDoc(docNumModels,doctypeid);
					docNumberManagerDao.addDocNumberDoc(doc);
				}else{
					list.remove(docNumModels);
				}
			}
			//将list中多余的从库中清除 暂时注释掉  原因是 文号 多个部门绑定的时候 只有一个部门可以用 其他部门提示文号绑定出错
			for(String s : list){
				docNumberManagerDao.deleteDocNumDoc(doctypeid, s);
			}
		}
		//提交的实例id为空的字符串则说明去掉所有的关联
		if(doctypeid!=null&&doctypeid.trim().length()!=0&&docNumModels!=null&&docNumModels.trim().length()==0){
			docNumberManagerDao.deleteDocNumDoc(doctypeid, null);
		}
	}
	
	public List<DocNumberModel> getDocNumModel(String webid, String content, int currentPage, int numPerPage, String lcid){
		if(content!=null && content.trim().length()!=0){
			content = content.trim();
		}else{ 
			content = null;
		}
		return docNumberManagerDao.getDocNumModel(webid, content, currentPage, numPerPage, lcid);
	}
	
	public void deleteDocNumModels(String ids) {
		if(ids!=null&&ids.trim().length()!=0){
			StringBuffer sb = new StringBuffer();
			if(ids.contains(",")){
				for(String s : ids.split(",")){
					sb.append("'").append(s).append("',");
				}
			}else{
				sb.append("'").append(ids).append("',");
			}
			String str = sb.toString().substring(0,sb.toString().length()-1);
			docNumberManagerDao.deleteManyWHModels(str);
		}
	}
	
	public void addDocNumModel(String userId,String allmodelstr,String webid,String modelstr,String workflowId){
		String[] models=modelstr.split(";");
		DocNumberModel w=new DocNumberModel();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		w.setContent(allmodelstr);
		w.setUserid(userId);
		w.setWebid(webid);
		w.setModelindex(999);
		Date d=new Date();
		w.setTime(d);
		w.setWorkflowId(workflowId);
		docNumberManagerDao.addWHModel(w);	
		//增加流程绑定表相应数据
		DocNumberModel whModel=docNumberManagerDao.getWHModelByParam(userId, sdf.format(d), webid);
		String whmodelId=whModel.getModelid();
		for (int i = 0; i < models.length; i++) {
			DocNumberFlow whFlow=new DocNumberFlow();
			whFlow.setModelid(whmodelId);
			whFlow.setTypeid(models[i].split(",")[0]);
			whFlow.setFlowindex(Integer.parseInt(models[i].split(",")[1]));
			docNumberManagerDao.addWHFlow(whFlow);
		}
	}
	
	public List<Define> getAllDefines(List<String> depas) {
		return docNumberManagerDao.getAllDefines(depas);
	}
	
	public void deletebigWh(String[] typeids) {
		docNumberManagerDao.deletebigWh(typeids);
	}

	public List<DocNumberType> getAllWhTypeByWebid(String webid, String isparent,String parentid) {
		return docNumberManagerDao.getAllWhTypeByWebid(webid, isparent,parentid);
	}
	public void deletesmallWh(String[] typeids) {
		docNumberManagerDao.deletesmallWh(typeids);
		
	}
	

	public void addWHFlow(DocNumberFlow whFlow) throws DataAccessException {
		docNumberManagerDao.addWHFlow(whFlow);
	}
	public void addWHModel(DocNumberModel whModel) throws DataAccessException {
		docNumberManagerDao.addWHModel(whModel);
	}
	public DocNumberModel getWHModelByParam(String userid, String date, String webid)
			throws DataAccessException {
		return docNumberManagerDao.getWHModelByParam(userid, date, webid);
	}

	public DocNumberModel getDocNumModelById(String modelId){
		return docNumberManagerDao.getDocNumModelById(modelId);
	}
	public void updateWhModel(DocNumberModel whModel) throws DataAccessException {
		docNumberManagerDao.updateWhModel(whModel);
	}
	public void updatesmallWh(DocNumberType whType) {
		docNumberManagerDao.updatesmallWh(whType);
	}
	public List<DocNumberModel> getAllWhModelByWebid(String webid,String workflowId)
			throws DataAccessException {
		return docNumberManagerDao.getAllWhModelByWebid(webid, workflowId);
	}

	public void updateWHModelForBangdingDoc(String doctypeids,String modelid)
			throws DataAccessException {
		docNumberManagerDao.updateWHModelForBangdingDoc(doctypeids,modelid);
	}

	public List<DocNumberDoc> getWHModelsByDoctypeid(String doctypeid)
			throws DataAccessException {
		return docNumberManagerDao.getWHModelsByDoctypeid(doctypeid);
	}
	
	public String getBindDocNumModelIds(String doctypeid){
		List<DocNumberDoc> list = docNumberManagerDao.getWHModelsByDoctypeid(doctypeid);
		StringBuffer sb = new StringBuffer();
		//得到的ID用逗号隔开
		if(list.size()!=0){
			for(DocNumberDoc d : list){
				sb.append(d.getWhmodelid()+",");
			}
			return sb.substring(0, sb.length()-1);
		}
		return sb.toString();
	}
	
//	public List<Step> getDocStepByStepId(String stepid)
//			throws DataAccessException {
//		return docNumberManagerDao.getDocStepByStepId(stepid);
//	}
	public List<DocNumberModel> getWHModelsByModelIds(List<DocNumberDoc> whdocs)
			throws DataAccessException {
		return docNumberManagerDao.getWHModelsByModelIds(whdocs);
	}
	public List<DocNumberModel> getWHModelsByContent(String content, String webid, String workflowId)
			throws DataAccessException {
		return docNumberManagerDao.getWHModelsByContent(content, webid, workflowId);
	}
	
	
	/*----------------------------------------------------------------以下是文号维护的处理-----------------------------------------------------------------------*/
	
	public boolean addDocNum(String define,String docNum,String title,String time,String deparementId,String ngr,String security,String ngrbm){
		Define def = docNumberManagerDao.getDefineById(define);
		try {
			if(def!=null){
				DocNumber dn = paserDocNum(docNum, title, time, deparementId,ngr,security,ngrbm);
				//新增办文文号
//				if(Constant.DEFINE_TYPE_DO.equals(def.getDefineType())){
//					docNumberManagerDao.addDoDocNumber(dn);
//					return true;
//				}
//				//新增发文文号
//				if(Constant.DEFINE_TYPE_SEND.equals(def.getDefineType())){
//					docNumberManagerDao.addSendDocNumber(dn);
//					return true;
//				}
			}
			return false;
		} catch (Exception e) {
			logger.error(e);
			logger.error("新增文号失败,请重试");
			return false;
		}
	}
	
//	private DocNumber paserDocNum(String docNum,String title,String time,String deparementId) throws Exception{
//		DocNumber dn = new DocNumber();
//		//取字号
//		String regChina = "[\u4e00-\u9fa5]+";
//        Pattern p = Pattern.compile(regChina);
//        Matcher m = p.matcher(docNum);
//        if (m.find()) {
//        	dn.setType(m.group());
//        }
//        //取年号
//        p = Pattern.compile("\\[\\d{4}\\]");
//        m = p.matcher(docNum);
//        if (m.find()) {
//        	 p = Pattern.compile("\\d{4}");
//             m = p.matcher(m.group());
//             if(m.find()){
//            	 dn.setYear(m.group());
//             }
//        }
//        //取序号
//        p = Pattern.compile("\\d+");
//        m = p.matcher(docNum);
//        while (m.find()) {
//        	String str = m.group();
//        	if(str.length()==4){
//        		dn.setNumber(str);
//        	}
//        	if(str.length()>=5){
//        		dn.setLwdwlx(str.substring(0,2));
//        		dn.setNumber(str.substring(2));
//        	}
//        }
//        //取得时间
//        DateFormat df = DateFormat.getDateInstance();
//        dn.setTime(new java.sql.Date(df.parse(time).getTime())) ;
//        dn.setTitle(title);
//         System.out.println(dn.toString());//
//        if(dn.getNumber()==null||dn.getYear()==null||dn.getType()==null||dn.getTitle()==null){
//        	throw new Exception();
//        }
//        return dn;
//	}
	
	private DocNumber paserDocNum(String docNum,String title,String time,String deparementId,String ngr,String security,String ngrbm) throws Exception{
	DocNumber dn = new DocNumber();
	//取字号
	String regChina = "[\u4e00-\u9fa5]+";
    Pattern p = Pattern.compile(regChina);
    Matcher m = p.matcher(docNum);
    if (m.find()) {
    	dn.setType(m.group());
    }
    //取年号
    p = Pattern.compile("\\d{4}");
    m = p.matcher(docNum);
    if (m.find()) {
     	 dn.setYear(m.group());
    }
    //取序号
    p = Pattern.compile("\\d+号");
    m = p.matcher(docNum);
    if (m.find()) {
    	String str = m.group();
    	str = str.replace("号", "");
    	if(str.length()>=5){
    		dn.setLwdwlx(str.substring(0,2));
    		dn.setNumber(str.substring(2));
    	}else{
    	   dn.setNumber(str);
    	}
    }
    //取得时间
    DateFormat df = DateFormat.getDateInstance();
    dn.setTime(new java.sql.Date(df.parse(time).getTime())) ;
    dn.setTitle(title);
    dn.setNgr(ngr);
    dn.setSecurity(security);
    dn.setNgrbm(ngrbm);
    if(dn.getNumber()==null||dn.getYear()==null||dn.getType()==null||dn.getTitle()==null){
    	throw new Exception();
    }
    return dn;
}
	
	public String paserModel(String model) {
		StringBuilder sb = new StringBuilder();
		if(model!=null&&model.length()!=0){
			String[] mstr = model.split(";");
			String[] snstr = mstr[0].split(",");
			for(String str : snstr){
				//将实例解析为单个策略
				List<String> strArr = ManageStrategyParse.parseStrategy(str);
				StringBuilder smalsb = new StringBuilder();
				for(String s : strArr){
					if(s.indexOf("$")==-1){
						smalsb.append(ManageStrategyParse.parseBySelf(s));
					}else{
						DocNumberStrategy strategy = strategyDao.getSingnStrategy(s);
						//对应策略类型的解析
						if(strategy!=null){
							if("jv".equals(strategy.getType())){
								smalsb.append(ManageStrategyParse.parseByYear(strategy));	
							}
							if("kv".equals(strategy.getType())){
								smalsb.append(ManageStrategyParse.parseKeyValue(strategy));
							}
							if("ns".equals(strategy.getType())){
								smalsb.append(ManageStrategyParse.parseNumber(strategy));
							}
							if("gp".equals(strategy.getType())){
								smalsb.append(ManageStrategyParse.parseGroupNumber(strategy));
							}
						}
					}
				}
				sb.append(smalsb);
			}
		}
		return sb.toString();
	}
	
	public String getModel(String define){
		String modelids = this.getBindDocNumModelIds(define);
		if(modelids==null||modelids.length()==0){
			return "";
		}
		StringBuffer sb = new StringBuffer("<select id='model' name='model' >");
		for(String modelId: modelids.split(",")){
			sb.append("<option value='");
			DocNumberModel d = this.getDocNumModelById(modelId);
			String model = "";
			for(String s : d.getContent().split(";")[0].split(",")){
				model += s;
			}
			sb.append(d.getContent()).append("' >").append(model);
			sb.append("</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public DTPageBean findBwDocNumAll(String nh,String jgdz,String lwdwlx,String order, int pageIndex, int pageSize){
		List<Object> list = new ArrayList<Object>();
		List<DocNumber> usedBwWenhao = (List<DocNumber>) docNumberManagerDao.bwDocNumUsed(nh, jgdz, lwdwlx,null,null, 0, Integer.MAX_VALUE).getDataList();
		List<DocNumber> unUsedBwWenhao = (List<DocNumber>) findbwDocNumberUnused(nh, jgdz, lwdwlx,null, 0, Integer.MAX_VALUE).getDataList();
		//取得所有文号
		usedBwWenhao.addAll(unUsedBwWenhao);
		//排序
		if (order != null && !order.equals("")) {
			if (order.equals("number_asc")) {
				Collections.sort(usedBwWenhao, new AscComparatorDocWH());
			} else if (order.equals("number_desc")) {
				Collections.sort(usedBwWenhao, new DescComparatorDocWH());
			} else if (order.equals("time_desc")) {
				Collections.sort(usedBwWenhao, new DescComparatorDocFWSJ());
			} else if (order.equals("time_asc")) {
				Collections.sort(usedBwWenhao, new AscComparatorDocFWSJ());
			}
		}else{
			Collections.sort(usedBwWenhao);
		}
		list.addAll(usedBwWenhao);
		//清掉不用的list集合
		unUsedBwWenhao = null;unUsedBwWenhao = null;
		int count = list.size();
		if (pageSize != Integer.MAX_VALUE){
			list = list.subList((pageIndex-1)*pageSize, Math.min((pageIndex)*pageSize, list.size()));
		}
		DTPageBean dt = new DTPageBean();
		dt.setDataList(list);
		dt.setTotalPages(count);
		dt.setTotalRows(count);
		dt.setCurrentPage(pageIndex);
		dt.setNumPerPage(pageSize);
		return dt;
	}
	
	@SuppressWarnings("unchecked")
	public DTPageBean findbwDocNumberUnused(String nh,String jgdz, String lwdwlx,String order, int pageIndex, int pageSize) {
		//先取得使用过的文号
		List<DocNumber> usedBwWenhao = (List<DocNumber>)docNumberManagerDao.bwDocNumUsed(nh, jgdz, lwdwlx,null,null, 0, Integer.MAX_VALUE).getDataList();
		List<DocNumber> unusedWenhao = new ArrayList<DocNumber>();
		String lastJgdz = "";
		String lastFwnh = "";
		String lastFwxh = "0";
		//取得未使用的文号
		for (DocNumber wenhao : usedBwWenhao){
			String thisJgdz = wenhao.getType();
			String thisFwnh = wenhao.getYear();
			String thisFwxh = wenhao.getNumber();
			if(lastJgdz!=null &&lastFwnh!=null&&lastJgdz.equals("")&&lastFwnh.equals("")){
				lastJgdz = thisJgdz;
				lastFwnh = thisFwnh;
			}
			if (thisJgdz.equals(lastJgdz) && thisFwnh.equals(lastFwnh) && (Integer.parseInt(thisFwxh) - Integer.parseInt(lastFwxh)) > 1){
				for (int i=Integer.parseInt(lastFwxh)+1; i<Integer.parseInt(thisFwxh); i++){
					DocNumber od = new DocNumber();
					od.setType(thisJgdz);
					od.setYear(thisFwnh);
					od.setIsused(Constant.DOCNUM_UNUSE);
					//lwdwlx是空时是请示件文号，非空时是办文文号
					od.setNumber(lwdwlx==null||"".equals(lwdwlx)?String.format("%04d", i):String.format("%03d", i));
					od.setLwdwlx("".equals(lwdwlx)?null:lwdwlx);
					unusedWenhao.add(od);
				}
			}
			lastJgdz = thisJgdz;
			lastFwnh = thisFwnh;
			lastFwxh = thisFwxh;
		}
		if (order != null && !order.equals("")) {
			if (order.equals("number_asc")) {
				Collections.sort(unusedWenhao, new AscComparatorDocWH());
			} else if (order.equals("number_desc")) {
				Collections.sort(unusedWenhao, new DescComparatorDocWH());
			} else if (order.equals("time_desc")) {
				Collections.sort(unusedWenhao, new DescComparatorDocFWSJ());
			} else if (order.equals("time_asc")) {
				Collections.sort(unusedWenhao, new AscComparatorDocFWSJ());
			}
		}else{
			Collections.sort(unusedWenhao);
		}
		int count = unusedWenhao.size();
		if (pageSize != Integer.MAX_VALUE){
			unusedWenhao = unusedWenhao.subList((pageIndex-1)*pageSize, Math.min((pageIndex)*pageSize, unusedWenhao.size()));
		}
		List<Object> list = new ArrayList<Object>();
		list.addAll(unusedWenhao);
		//清掉不用的list集合
		usedBwWenhao = null;unusedWenhao = null;
		DTPageBean dt = new DTPageBean();
		dt.setCurrentPage(pageIndex);
		dt.setNumPerPage(pageSize);
		dt.setTotalRows(count);
		dt.setDataList(list);
		return dt;
	}
	
	@SuppressWarnings("unchecked")
	private DTPageBean findfwDocNumberUnused(String jgdz,String fwnh,String order, int pageIndex,int pageSize) {
		// 先取得使用过的文号
		List<DocNumber> usedWenhao = (List<DocNumber>)docNumberManagerDao.fwAlreadyUsedDocNum(jgdz, fwnh,null,null, 0, Integer.MAX_VALUE).getDataList();
		Collections.sort(usedWenhao);
		List<DocNumber> unusedWenhao = new ArrayList<DocNumber>();
		String lastJgdz = "";
		String lastFwnh = "";
		String lastFwxh = "0";
		for (DocNumber wenhao : usedWenhao) {
			String thisJgdz = wenhao.getType();
			String thisFwnh = wenhao.getYear();
			String thisFwxh = wenhao.getNumber();

			if (thisJgdz.equals(lastJgdz)
					&& thisFwnh.equals(lastFwnh)
					&& (Integer.parseInt(thisFwxh) - Integer.parseInt(lastFwxh)) > 1) {
				for (int i = Integer.parseInt(lastFwxh) + 1; i < Integer
						.parseInt(thisFwxh); i++) {
					DocNumber od = new DocNumber();
					od.setType(thisJgdz);
					od.setYear(thisFwnh);
					od.setIsused(Constant.DOCNUM_UNUSE);
					od.setNumber(String.format("%04d", i));

					unusedWenhao.add(od);
				}
			}
			lastJgdz = thisJgdz;
			lastFwnh = thisFwnh;
			lastFwxh = thisFwxh;
		}
		if (order != null && !order.equals("")) {
			if (order.equals("number_asc")) {
				Collections.sort(unusedWenhao, new AscComparatorDocWH());
			} else if (order.equals("number_desc")) {
				Collections.sort(unusedWenhao, new DescComparatorDocWH());
			} else if (order.equals("time_desc")) {
				Collections.sort(unusedWenhao, new DescComparatorDocFWSJ());
			} else if (order.equals("time_asc")) {
				Collections.sort(unusedWenhao, new AscComparatorDocFWSJ());
			}
		}else{
			Collections.sort(unusedWenhao);
		}
		int count = unusedWenhao.size();
		if (count!=0&&pageSize != Integer.MAX_VALUE) {
			unusedWenhao = unusedWenhao.subList((pageIndex-1) * pageSize, Math.min(
					pageIndex* pageSize, unusedWenhao.size()));
		}
		List<Object> list = new ArrayList<Object>();
		list.addAll(unusedWenhao);
		//清掉不用的list集合
		unusedWenhao = null;usedWenhao = null;
		DTPageBean dt = new DTPageBean();
		dt.setCurrentPage(pageIndex);
		dt.setNumPerPage(pageSize);
		dt.setTotalRows(count);
		dt.setDataList(list);
		return dt;
	}
	
	@SuppressWarnings("unchecked")
	private DTPageBean findfwDocNumAll(String jgdz,String nh,String order, int pageIndex, int pageSize){
		List<Object> list = new ArrayList<Object>();
		List<DocNumber> usedBwWenhao = (List<DocNumber>) docNumberManagerDao.fwAlreadyUsedDocNum(jgdz, nh,null,null, 0, Integer.MAX_VALUE).getDataList();
		List<DocNumber> unUsedBwWenhao = (List<DocNumber>) findfwDocNumberUnused(jgdz, nh, null,0, Integer.MAX_VALUE).getDataList();
		usedBwWenhao.addAll(unUsedBwWenhao);
		if (order != null && !order.equals("")) {
			if (order.equals("number_asc")) {
				Collections.sort(usedBwWenhao, new AscComparatorDocWH());
			} else if (order.equals("number_desc")) {
				Collections.sort(usedBwWenhao, new DescComparatorDocWH());
			} else if (order.equals("time_desc")) {
				Collections.sort(usedBwWenhao, new DescComparatorDocFWSJ());
			} else if (order.equals("time_asc")) {
				Collections.sort(usedBwWenhao, new AscComparatorDocFWSJ());
			}
		}else{
			Collections.sort(usedBwWenhao);
		}
		list.addAll(usedBwWenhao);
		//清掉不用的list集合
		unUsedBwWenhao = null;unUsedBwWenhao = null;
		int count = list.size();
		if (pageSize != Integer.MAX_VALUE){
			list = list.subList((pageIndex-1)*pageSize, Math.min((pageIndex)*pageSize, list.size()));
		}
		DTPageBean dt = new DTPageBean();
		dt.setDataList(list);
		dt.setTotalPages(count);
		dt.setTotalRows(count);
		dt.setCurrentPage(pageIndex);
		dt.setNumPerPage(pageSize);
		return dt;
	}

	public DTPageBean findDocNum(DocNumber dn, int pageIndex, int pageSize,String order){
		DTPageBean dt = new DTPageBean();
		if(dn!=null){
			//参数的处理
			Calendar calendar = new GregorianCalendar();
			Date trialTime = new Date();
		 	calendar.setTime(trialTime);
			dn.setYear(dn.getYear()==""?String.valueOf(calendar.get(Calendar.YEAR)):dn.getYear());
			order = order==null || order.trim().length()==0?null:order;
			//办文文号维护的处理
			if(Constant.DEFINE_TYPE_DO.equals(dn.getDefine())){
				//查询单个文号的处理
				if(dn.getNumber()!=null&&dn.getNumber().trim().length()!=0){
					dt = docNumberManagerDao.bwDocNumUsed(dn.getYear(), dn.getType(),dn.getNumber(), dn.getLwdwlx(),order, pageIndex, pageSize);
					if(dt.getDataList().size()>0){
						return dt;
					}else{
						List<Object> dataList = new ArrayList<Object>();
						dn.setIsused(Constant.DOCNUM_UNUSE);
						dataList.add(dn);
						dt.setDataList(dataList);
						return dt;
					}
				}else{
					if(Constant.DOCNUM_USE.equals(dn.getIsused())){
						dt = docNumberManagerDao.bwDocNumUsed(dn.getYear(), dn.getType(), dn.getLwdwlx(),null,order, pageIndex, pageSize);
					}else if(Constant.DOCNUM_UNUSE.equals(dn.getIsused())){
						dt = this.findbwDocNumberUnused(dn.getYear(), dn.getType(), dn.getLwdwlx(),order, pageIndex, pageSize);
					}else{
						dt = findBwDocNumAll(dn.getYear(), dn.getType(), dn.getLwdwlx(),order, pageIndex, pageSize);
					}
				}
				//发文文号维护的处理
			}else if(Constant.DEFINE_TYPE_SEND.equals(dn.getDefine())){
				//查询单个文号的处理
				if(dn.getNumber()!=null&&dn.getNumber().trim().length()!=0){
					dt = docNumberManagerDao.fwAlreadyUsedDocNum(dn.getType(), dn.getYear(),dn.getNumber(),order, pageIndex, pageSize);
					if(dt.getDataList().size()>0){
						return dt;
					}else{
						List<Object> dataList = new ArrayList<Object>();
						dn.setIsused(Constant.DOCNUM_UNUSE);
						dataList.add(dn);
						dt.setDataList(dataList);
						return dt;
					}
				}else{
					if(Constant.DOCNUM_USE.equals(dn.getIsused())){
						dt = docNumberManagerDao.fwAlreadyUsedDocNum(dn.getType(), dn.getYear(),null,order, pageIndex, pageSize);
					}else if(Constant.DOCNUM_UNUSE.equals(dn.getIsused())){
						dt = findfwDocNumberUnused(dn.getType(), dn.getYear(),order, pageIndex, pageSize);
					}else{
						dt = findfwDocNumAll(dn.getType(), dn.getYear(), order, pageIndex, pageSize);
					}
				}
			}
		}
		return dt;
	}
	
	private class AscComparatorDocWH implements Comparator<DocNumber> {
		public int compare(DocNumber a, DocNumber b) {
			String aJgdz = a.getType() == null ? "" : a.getType();
			String aFwnh = a.getYear() == null ? "" : a.getYear();
			String aFwxh = a.getNumber() == null || a.getNumber().equals("") ? "0"
					: a.getNumber();

			String oJgdz = b.getType() == null ? "" : b.getType();
			String oFwnh = b.getYear() == null ? "" : b.getYear();
			String oFwxh = b.getNumber() == null || b.getNumber().equals("") ? "0"
					: b.getNumber();

			if (aJgdz.equals(oJgdz)) {
				if (aFwnh.equals(oFwnh)) {
					return Integer.parseInt(aFwxh) - Integer.parseInt(oFwxh);
				} else {
					return aFwnh.compareTo(oFwnh);
				}
			} else {
				return aJgdz.compareTo(oJgdz);
			}
		}
	}

	private class DescComparatorDocWH implements Comparator<DocNumber> {
		public int compare(DocNumber b, DocNumber a) {
			String aJgdz = a.getType() == null ? "" : a.getType();
			String aFwnh = a.getYear() == null ? "" : a.getYear();
			String aFwxh = a.getNumber() == null || a.getNumber().equals("") ? "0" : a.getNumber();

			String oJgdz = b.getType() == null ? "" : b.getType();
			String oFwnh = b.getYear() == null ? "" : b.getYear();
			String oFwxh = b.getNumber() == null || b.getNumber().equals("") ? "0" : b.getNumber();

			if (aJgdz.equals(oJgdz)) {
				if (aFwnh.equals(oFwnh)) {
					return Integer.parseInt(aFwxh) - Integer.parseInt(oFwxh);
				} else {
					return aFwnh.compareTo(oFwnh);
				}
			} else {
				return aJgdz.compareTo(oJgdz);
			}
		}
	}

	private class AscComparatorDocFWSJ implements Comparator<DocNumber> {
		public int compare(DocNumber a, DocNumber b) {
			Date date1;
			if (a.getTime() != null) {
				date1 = a.getTime();
			} else {
				date1 = new Date();
				date1.setTime(date1.getTime() + 20 * 365 * 24 * 60 * 60 * 1000);
			}
			Date date2;
			if (b.getTime() != null) {
				date2 = b.getTime();
			} else {
				date2 = new Date();
				date2.setTime(date2.getTime() + 20 * 365 * 24 * 60 * 60 * 1000);
			}
			return date1.compareTo(date2);
		}
	}
	
	private class DescComparatorDocFWSJ implements Comparator<DocNumber> {
		public int compare(DocNumber b, DocNumber a) {
			Date date1;
			if (a.getTime() != null) {
				date1 = a.getTime();
			} else {
				date1 = new Date();
				date1.setTime(date1.getTime() + 20 * 365 * 24 * 60 * 60 * 1000);
			}
			Date date2;
			if (b.getTime() != null) {
				date2 = b.getTime();
			} else {
				date2 = new Date();
				date2.setTime(date2.getTime() + 20 * 365 * 24 * 60 * 60 * 1000);
			}
			return date1.compareTo(date2);
		}
	}
	/*--------------------------------------------策略维护------------------------------------------------------*/
	public DocNumberStrategy getSingnStrategy(String id) {
		return docNumberManagerDao.getSingnStrategy(id);
	}
	public List<DocNumberStrategy> getStrategyList() {
		return docNumberManagerDao.getStrategyList();
	}
	public void addStrategy(String content,String type,String description) {
		DocNumberStrategy ds = new DocNumberStrategy();
		ds.setStrategyId(UUID.randomUUID().toString().toUpperCase());
		ds.setContent(content);
		ds.setDescription(description);
		ds.setType(type);
		docNumberManagerDao.addStrategy(ds);
	}
	public void updateStrategy(String id,String content,String type,String description){
		DocNumberStrategy ds = new DocNumberStrategy();
		ds.setStrategyId(id);
		ds.setContent(content);
		ds.setDescription(description);
		ds.setType(type);
		docNumberManagerDao.updateStrategy(ds);
	}
	public void delStrategy(String id) {
		docNumberManagerDao.delStrategy(id);
	}
	
	/**
	 * 
	 * 描述：根据流程Id取得文号模型
	 *
	 * @param defineId
	 * @return List<DocNumberModel>
	 *
	 * 作者:ZhangYJ<br>
	 * 创建时间:2012-2-6 上午11:20:06
	 */
	public List<DocNumberModel> findDocNumberModel(String defineId) {
		String modelids = this.getBindDocNumModelIds(defineId);
		List<DocNumberModel> list = new ArrayList<DocNumberModel>();
		if(modelids==null||modelids.length()==0){
			return null;
		}
		for(String modelId: modelids.split(",")){
			DocNumberModel d = this.getDocNumModelById(modelId);
			list.add(d);
		}
		return list;
	}
	
	public int getDocNumBigClasses(String conditionSql) {
		return docNumberManagerDao.getDocNumBigClasses(conditionSql);
	}
	
	public int getDocNumSmallClasses(String name,String parentid, String workflowId) {
		return docNumberManagerDao.getDocNumBigClasses(name, parentid,workflowId);
	}
	
	public int getDocNumModelCount(String webid, String content,String workflowId) {
		return docNumberManagerDao.getDocNumModelCount(webid,content, workflowId);
	}

	public DocNumberWhFw findDocNumWhFw(String workFlowId,String instanceId) {
		return docNumberManagerDao.findDocNumWhFw(workFlowId,instanceId);
	}
	@Override
	public DocNumberWhBw findDocNumWhBw(String workFlowId, String instanceId) {
		return docNumberManagerDao.findDocNumWhBw(workFlowId,instanceId);
	}
	@Override
	public void addOrDelDoc(String whId, String workFlowId) {
		String whs = this.getBindDocNumModelIds(workFlowId);
		if(!whs.contains(whId)){
			DocNumberDoc doc = new DocNumberDoc(whId,workFlowId);
			docNumberManagerDao.addDocNumberDoc(doc);
		}else{
			docNumberManagerDao.deleteDocNumDoc(workFlowId, whId);
		}
	}
}
