package cn.com.trueway.document.component.taglib.docNumber.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.document.component.docNumberManager.dao.StrategyDao;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumOnlyWh;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhBw;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhFw;
import cn.com.trueway.document.component.taglib.docNumber.dao.DocNumberDao;
import cn.com.trueway.document.component.taglib.docNumber.service.DocNumberService;
import cn.com.trueway.document.component.taglib.docNumber.util.StrategyParse;
import cn.com.trueway.workflow.core.pojo.WfDictionary;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.service.DictionaryService;


public class DocNumberServiceImpl implements DocNumberService{
	private StrategyDao strategyDao;
	private DocNumberDao docNumberDao;
	private DictionaryService dictionaryService;
	
	public StrategyDao getStrategyDao() {
		return strategyDao;
	}
	public void setStrategyDao(StrategyDao strategyDao) {
		this.strategyDao = strategyDao;
	}
	public DocNumberDao getDocNumberDao() {
		return docNumberDao;
	}
	public void setDocNumberDao(DocNumberDao docNumberDao) {
		this.docNumberDao = docNumberDao;
	}
	
	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	/**
	 * 
	 * 描述：解析文号模型<br>
	 *
	 * @param model
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午10:36:13
	 */
	public String paserModel(String model) {
		StringBuilder sb = new StringBuilder();
		if(model!=null&&model.length()!=0){
			String[] mstr = model.split(";");
			String[] snstr = mstr[0].split(",");
			for(String str : snstr){
				List<String> strArr = StrategyParse.parseStrategy(str);
				
				StringBuilder smalsb = new StringBuilder();
				for(String s : strArr){
					if(s.indexOf("$")==-1){
						smalsb.append(StrategyParse.parseBySelf(s));
					}else{
						DocNumberStrategy strategy = strategyDao.getSingnStrategy(s);
						if(strategy!=null){
							if("jv".equals(strategy.getType())){
								smalsb.append(StrategyParse.parseByYear(strategy));	
							}
							if("kv".equals(strategy.getType())){
								smalsb.append(StrategyParse.parseKeyValue(strategy));
							}
							if("ns".equals(strategy.getType())){
								smalsb.append(StrategyParse.parseNumber(strategy));
							}
							if("gp".equals(strategy.getType())){
							   smalsb.append(StrategyParse.parseGroupNumber(strategy));
							}
							if("cs".equals(strategy.getType())){
								List<WfDictionary> keys = dictionaryService.getDictionaryByName("deptChoice");
								if(keys.size()>0){
									WfDictionary defaultKey = keys.get(0);
									String dicValuesDefault = defaultKey.getVc_value().split(",")[0];
									smalsb.append("<div class='default-select default-select-chus'>");
									smalsb.append("<div class='form-select-control select-control-chus'>");
									smalsb.append("<input type='text' class='default-select-input select-input-chus' id='type_select_chus' value='"+dicValuesDefault+"'>");
									smalsb.append("<input type='hidden' id='chus' value='"+dicValuesDefault+"' />");
									smalsb.append("</div>");
									smalsb.append("<ul class='select-menu select-menu-chus'>");
									for(int index = 0 ; index <keys.size() ; index++){
										WfDictionary dic = keys.get(index);
										String[] dicKeys = dic.getVc_key().split(",");
										String[] dicValues = dic.getVc_value().split(",");
	//									smalsb.append("<select id='"+parse2Id(strategy.getStrategyId())+"' class='dept-select' name='deptChoice'>");
										for(int j = 0 ; j < dicValues.length ; j++){
	//										smalsb.append("<option value='"+dicValues[j]+"'>"+dicValues[j]+"</option>");
											smalsb.append("<li data-value='"+dicValues[j]+"'>"+dicValues[j]+"</li>");
											
										}
									}
	//								smalsb.append("</select>");
									smalsb.append("</ul>");
									smalsb.append("</div>");
								}
							}
						}
					}
				}
				sb.append(smalsb);
			}
		}
		return sb.toString();
	}
	public int getDocNum(String defId, String gjdz, String yearNum, String lwdwlx, String webId,String xhModel,String isChildWf,String itemId) {
		try {
			//根据流程ID判断是发文还是办文或传阅确定序号
//			Define define = workflowDefineDao.fineByDefineId(defId);
			WfItem item = null;
			if(!("true").equals(isChildWf)){
				item = docNumberDao.findItemByWorkFlowId(defId);
			}else{
				item = docNumberDao.findItemById(itemId);
			}
			if(item!=null&&item.getVc_sxlx().equals(Constant.DEFINE_TYPE_SEND)){
				if(xhModel!=null&&xhModel.indexOf("$group_number")!=-1){
					String xhStrategy=StrategyParse.parseStrategy(xhModel).get(0);
					DocNumberStrategy strategy=strategyDao.getSingnStrategy(xhStrategy);
					String gidzs=StrategyParse.parseGroupGidzs(strategy);
					return docNumberDao.getGroupMaxFWXH(item.getVc_ssbmid(),gidzs,yearNum)+1;
				}
				return docNumberDao.getMaxFWXH(item.getVc_ssbmid(),gjdz, yearNum,item.getLcid())+1;
			}
			if(item!=null&&(item.getVc_sxlx().equals(Constant.DEFINE_TYPE_DO) || ("2").equals(item.getVc_sxlx()))){
//				//请假流程文号的生成(PS：只对只有该流程绑定有效，如果其他流程中也有绑定的文号与请假流程中的一样，则下面逻辑需修改)
//				if(("请假").equals(define.getWfName())){
//					List<String> docNumList = docNumberDao.getMaxBWXH(webId);
//					if(docNumList!=null&&!docNumList.isEmpty()){
//						String[] str = new String[docNumList.size()];
//						for (int i=0;i<docNumList.size();i++) {
//							String num = docNumList.get(i).substring(5,docNumList.get(i).length()-1);
//							str[docNumList.size()-i-1] = num;
//						}
//						 Arrays.sort(str);
//						 return Integer.parseInt(str[str.length-1])+1;
//					}else{
//						return 1;
//					}
//				}else{
					return docNumberDao.getMaxBWXH(item.getVc_ssbmid(),gjdz, yearNum, lwdwlx, webId,item.getLcid())+1;
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public boolean getIsalreadyUsedDocNumber(String defId, String xh, String gjdz, String nh, String lwdwlx, String isChildWf, String itemId,String webid){
		try {
			//根据流程ID判断是发文还是办文或传阅确定序号
			WfItem item = null;
			if(!("true").equals(isChildWf)){
				item = docNumberDao.findItemByWorkFlowId(defId);
			}else{
				item = docNumberDao.findItemById(itemId);
			}
			if(item!=null&&item.getVc_sxlx().equals(Constant.DEFINE_TYPE_SEND)){
				return docNumberDao.getIsfwDocNumUsed(item.getVc_ssbmid(),xh,gjdz,nh,webid,item.getLcid())==0;
			}
			if(item!=null&&(item.getVc_sxlx().equals(Constant.DEFINE_TYPE_DO) || ("2").equals(item.getVc_sxlx()))){
				return docNumberDao.getIsbwDocNumUsed(item.getVc_ssbmid(),xh,gjdz,nh,lwdwlx,webid,item.getLcid())==0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Object[] findDocNumberUnused(String defId,String nh,String jgdz, String lwdwlx, String fwxh, String webid, int pageIndex, int pageSize,String numModel, String isChildWf, String itemId){
		//TODO
		try {
			//根据流程ID判断是发文还是办文或传阅确定序号
//			Define define = workflowDefineDao.fineByDefineId(defId);
			WfItem item = null;
			if(!("true").equals(isChildWf)){
				item = docNumberDao.findItemByWorkFlowId(defId);
			}else{
				item = docNumberDao.findItemById(itemId);
			}
			if(item!=null&&item.getVc_sxlx().equals(Constant.DEFINE_TYPE_SEND)){
				return this.findfwDocNumberUnused(item.getVc_ssbmid(),item.getLcid(),jgdz, nh, fwxh, pageIndex, pageSize,numModel);
			}
			if(item!=null&&(item.getVc_sxlx().equals(Constant.DEFINE_TYPE_DO) || ("2").equals(item.getVc_sxlx()))){
				return this.findbwDocNumberUnused(item.getVc_ssbmid(),item.getLcid(),nh, jgdz, lwdwlx, fwxh, webid, pageIndex, pageSize,numModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object[] findfwDocNumberUnused(String siteId,String workflowId,String jgdz,
			String fwnh, String fwxh, int pageIndex,
			int pageSize,String numModel) {
		List<DocNumberWhFw> unusedWenhao=new ArrayList<DocNumberWhFw>();
		if(numModel!=null&&numModel.indexOf("$group_num")!=-1){
			DocNumberStrategy strategy=this.findStrategy(numModel);
			String jgdzs=StrategyParse.parseGroupGidzs(strategy);
			List<DocNumberWhFw> usedWenhao=docNumberDao.fwGroupAlreadyUsedDocNum(siteId,jgdzs, fwnh,workflowId, 0, Integer.MAX_VALUE);
			Collections.sort(usedWenhao);
			//List<Doc>unusedWenhaoList=new ArrayList<Doc>();
			String lastFwnh="";
			String lastFwxh="";
			
			for(DocNumberWhFw wenhao:usedWenhao){
				String thisFwnh=wenhao.getFwnh();
				String thisFwxh=wenhao.getFwxh();
				String thisJgdz=wenhao.getJgdz();
				if(thisFwxh != null){
					if(thisFwnh.equals(lastFwnh)&&(Integer.parseInt(thisFwxh)-Integer.parseInt(lastFwxh))>1){
						for(int i=Integer.parseInt(lastFwxh)+1;i<Integer.parseInt(thisFwxh);i++){
							DocNumberWhFw doc=new DocNumberWhFw();
							if(thisJgdz!=null&&thisJgdz.contains("-")){
								thisJgdz = thisJgdz.split("-")[0]+"**";
							}
							doc.setJgdz(thisJgdz);
							doc.setFwnh(thisFwnh);
							if(numModel!=null && numModel.indexOf("num4")!=-1){
								doc.setFwxh(CommonUtil.getFourNumberString(i));
							}else{
								doc.setFwxh(i+"");
							}
							unusedWenhao.add(doc);
						}
					}
					lastFwnh=thisFwnh;
					lastFwxh=thisFwxh;
				}
				
				
			}
		}else{
			List<DocNumberWhFw> usedWenhao = docNumberDao.fwAlreadyUsedDocNum(siteId,workflowId,jgdz, fwnh, fwxh, 0, Integer.MAX_VALUE);
			Collections.sort(usedWenhao);
			//List<Doc> unusedWenhao = new ArrayList<Doc>();
			String lastJgdz = jgdz;
			String lastFwnh = fwnh;
			String lastFwxh = "0";
			for (DocNumberWhFw wenhao : usedWenhao) {
				String thisJgdz = wenhao.getJgdz();
				String thisFwnh = wenhao.getFwnh();
				String thisFwxh = wenhao.getFwxh();
				if(thisJgdz!=null&&thisJgdz.contains("-")){
					thisJgdz = thisJgdz.split("-")[0]+"**";
				}
				if(thisFwxh != null){
					if (thisJgdz.startsWith(lastJgdz)
							&& thisFwnh.equals(lastFwnh)
							&& (Integer.parseInt(thisFwxh) - Integer.parseInt(lastFwxh)) >= 1) {
						for (int i = Integer.parseInt(lastFwxh) + 1; i < Integer.parseInt(thisFwxh); i++) {
							DocNumberWhFw od = new DocNumberWhFw();
							od.setJgdz(thisJgdz);
							od.setFwnh(thisFwnh);
							if(numModel!=null && numModel.indexOf("num4")!=-1){
								od.setFwxh(CommonUtil.getFourNumberString(i));
							}else{
								od.setFwxh(i+"");
							}
							unusedWenhao.add(od);
						}
					}
					lastJgdz = thisJgdz;
					lastFwnh = thisFwnh;
					lastFwxh = thisFwxh;
				}
			}
		}  
		int count = unusedWenhao.size();
		if (pageSize != Integer.MAX_VALUE) {
			unusedWenhao = unusedWenhao.subList(pageIndex * pageSize, Math.min(
					(pageIndex + 1) * pageSize, unusedWenhao.size()));
		}
		return new Object[] { unusedWenhao, count ,"fw"};
	}
	
	/**
	 * 描述：找到办文文号未被使用的列<br>
	 *
	 * @param deps
	 * @param jgdz
	 * @param fwnh
	 * @param fwxh
	 * @param title
	 * @param pageIndex
	 * @param pageSize
	 * @param order
	 * @return Object[]
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-9-6 上午10:59:02
	 */
	public Object[] findbwDocNumberUnused(String siteId,String workflowId,String nh,String jgdz, String lwdwlx, String fwxh, String webid, int pageIndex, int pageSize,String numModel) {
		//先取得使用过的文号
		List<DocNumberWhBw> usedBwWenhao = (List<DocNumberWhBw>)docNumberDao.bwDocNumUsed(siteId,workflowId,nh, jgdz, lwdwlx, webid, 0, Integer.MAX_VALUE);
		Collections.sort(usedBwWenhao);
		List<DocNumberWhBw> unusedWenhao = new ArrayList<DocNumberWhBw>();
		String lastJgdz = "";
		String lastFwnh = "";
		String lastFwxh = "0";
		for (DocNumberWhBw wenhao : usedBwWenhao){
			String thisJgdz = wenhao.getBwlx();
			String thisFwnh = wenhao.getBwnh();
			String thisFwxh = wenhao.getBwxh();
			if(thisJgdz!=null&&thisJgdz.contains("-")){
				thisJgdz = thisJgdz.split("-")[0]+"**";
			}
			if(lastJgdz!=null &&lastFwnh!=null&&lastJgdz.equals("")&&lastFwnh.equals("")){
				lastJgdz = thisJgdz;
				lastFwnh = thisFwnh;
			}
			if(thisFwxh != null ){
				/*if (thisJgdz.equals(lastJgdz) 
						&& ((thisFwnh == null 
						&& lastFwnh ==null) || thisFwnh.equals(lastFwnh)) 
						&& (Integer.parseInt(thisFwxh) - Integer.parseInt(lastFwxh)) > 1){*/
				if(thisFwnh.equals(lastFwnh)&&(Integer.parseInt(thisFwxh)-Integer.parseInt(lastFwxh))>=1){
					for (int i=Integer.parseInt(lastFwxh)+1; i<Integer.parseInt(thisFwxh); i++){
						DocNumberWhBw od = new DocNumberWhBw();
						od.setBwlx(thisJgdz);
						od.setBwnh(thisFwnh);
						//lwdwlx是空时是请示件文号，非空时是办文文号
						od.setBwxh(i+"");
						if(numModel!=null && numModel.indexOf("num4")!=-1){
							od.setBwxh(CommonUtil.getFourNumberString(i));
						}else{
							od.setBwxh(i+"");
						}
						od.setLwdwlx("".equals(lwdwlx)?null:lwdwlx);
						unusedWenhao.add(od);
					}
				}
				lastJgdz = thisJgdz;
				lastFwnh = thisFwnh;
				lastFwxh = thisFwxh;
			}
		}
		int count = unusedWenhao.size();
		if (pageSize != Integer.MAX_VALUE){
			unusedWenhao = unusedWenhao.subList(pageIndex*pageSize, Math.min((pageIndex+1)*pageSize, unusedWenhao.size()));
		}
		return new Object[]{unusedWenhao, count, "bw"};
	}
	
	public List<String[]> getBindWhModelByWebid(String webid, String defid, String value) {
		List<Object> list = docNumberDao.getBindWhModelByWebid(webid, defid);			
		//List<Object> list = new ArrayList<Object>();
		//list.add("[$year$],来字,$kv$$num$号;fwnh,gjdz,fwxh");
		//value="[2011]来字07283号";
		String[] mv = new String[2];
		List<String[]> l = new ArrayList<String[]>();
		if(value!=null&&value.trim().length()!=0){
			for(Object o : list){
				String str = o.toString();
				if(str.contains(";")){
					if(str.split(";")[0].contains(",")){
						for(String s : str.split(";")[0].split(",")){
							if(value.contains(s)){
								mv[0]=o.toString();
								mv[1]=value;
								l.add(mv);
							}
						}
					}
				}
			}
		}else{
			for(Object o : list){
				String[] s = new String[2];
				s[0]=o.toString();
				Map<String,String> map = this.parsemodel(o.toString());
				if(map!=null){
					//s[1]=docNumberDao.getSingleDocNum(map.get("gjdz"));
					if(s[1] == null ) s[1] = s[0].split(";")[0].replaceAll(",", "");
					//if(s[1].length()==0) s[1] = s[0].split(";")[0].replaceAll(",", "");
				}
				l.add(s);
			}
		}
		return l;
	}
	
	private Map<String,String> parsemodel(String str){
		Map<String,String> map = new HashMap<String, String>();
		if(str.contains(";")){
			String[] s = str.split(";");
			if(s[0].contains(",")){
				for(int i=0,j=s[0].split(",").length;i<j;i++){
					map.put(s[1].split(",")[i], s[0].split(",")[i]);
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
		return map;
	}
	
	public int findDocNumAttach(String docNum) {
		Integer f = docNumberDao.getDocNumAttach(docNum);
		f = f==null?0:f;
		return ++f;
	}
	
	public DocNumberStrategy findStrategy(String numStra) {
		return docNumberDao.getStrategy(numStra);
	}
	
	public WfItem findItemByWorkFlowId(String workFlowId) {
		return docNumberDao.findItemByWorkFlowId(workFlowId);
	}
	
	public void addBw(DocNumberWhBw docNumBw) {
		docNumberDao.addBw(docNumBw);
	}
	
	public void addFw(DocNumberWhFw docNumFw) {
		docNumberDao.addFw(docNumFw);
	}
	
	public DocNumberWhBw findDocNumBw(String instanceId) {
		return docNumberDao.findDocNumBw(instanceId);
	}
	
	public DocNumberWhFw findDocNumFw(String instanceId) {
		return docNumberDao.findDocNumFw(instanceId);
	}
	
	public void updateBw(DocNumberWhBw docNumberWhBw) {
		docNumberDao.updateBw(docNumberWhBw);
	}
	
	public void updateFw(DocNumberWhFw docNumberWhFw) {
		docNumberDao.updateFw(docNumberWhFw);
	}
	
	@Override
	public void addDocNumOnlyWh(DocNumOnlyWh docNumOnlyWh) {
		docNumberDao.addDocNumOnlyWh(docNumOnlyWh);
	}

	@Override
	public List<DocNumOnlyWh> getDocNumOnlyWh(String workflowId, String itemId, String isUsed) {
		return docNumberDao.selectDocNumOnlyWh(workflowId, itemId, isUsed);
	}
	
	@Override
	public Integer getMaxDocNumOnlyWh(String workflowId, String itemId) {
		return docNumberDao.selectMaxDocNumOnlyWh(workflowId, itemId);
	}
	
	@Override
	public void editNumOnlyWh(DocNumOnlyWh docNumOnlyWh) {
		docNumberDao.updateNumOnlyWh(docNumOnlyWh);
	}
	
	@Override
	public DocNumOnlyWh getDocNumOnlyWh(String workflowId, String itemId,
			Integer num, String status) {
		// TODO Auto-generated method stub
		return docNumberDao.selectDocNumOnlyWh(workflowId, itemId, num, status);
	}
	
	private static String parse2Id(String str){
		return str.split("\\$")[1];
	}
	
	public String paserModelByMobile(String model) {
		StringBuilder sb = new StringBuilder();
		if(model!=null&&model.length()!=0){
			String[] mstr = model.split(";");
			String[] snstr = mstr[0].split(",");
			for(String str : snstr){
				List<String> strArr = StrategyParse.parseStrategy(str);
				
				StringBuilder smalsb = new StringBuilder();
				for(String s : strArr){
					if(s.indexOf("$")==-1){
						smalsb.append(StrategyParse.parseBySelf(s));
					}else{
						DocNumberStrategy strategy = strategyDao.getSingnStrategy(s);
						if(strategy!=null){
							if("jv".equals(strategy.getType())){
								smalsb.append(StrategyParse.parseByYear(strategy));	
							}
							if("kv".equals(strategy.getType())){
								smalsb.append(StrategyParse.parseKeyValue(strategy));
							}
							if("ns".equals(strategy.getType())){
								smalsb.append(StrategyParse.parseNumber(strategy));
							}
							if("gp".equals(strategy.getType())){
							   smalsb.append(StrategyParse.parseGroupNumber(strategy));
							}
							if("cs".equals(strategy.getType())){
								List<WfDictionary> keys = dictionaryService.getDictionaryByName("deptChoice");
								if(keys.size()>0){
									WfDictionary defaultKey = keys.get(0);
									String dicValuesDefault = defaultKey.getVc_value().split(",")[0];
									smalsb.append("<div class='m-default-select'>");
									smalsb.append("<div class='m-select-control'>");
									smalsb.append("<input type='text' class='form-input-control' id='type_select_chus' value='"+dicValuesDefault+"'>");
									smalsb.append("<input type='hidden' id='chus' value='"+dicValuesDefault+"' />");
									smalsb.append("</div>");
									smalsb.append("<ul class='m-select-menu'>");
//									smalsb.append("<select id='"+parse2Id(strategy.getStrategyId())+"' class='weui-select new-weui-select' name='deptChoice'>");
									for(int index = 0 ; index <keys.size() ; index++){
										WfDictionary dic = keys.get(index);
										String[] dicKeys = dic.getVc_key().split(",");
										String[] dicValues = dic.getVc_value().split(",");
										for(int j = 0 ; j < dicValues.length ; j++){
//											smalsb.append("<option value='"+dicValues[j]+"'>"+dicValues[j]+"</option>");
											smalsb.append("<li data-value='"+dicValues[j]+"'>"+dicValues[j]+"</li>");
											
										}
									}
//									smalsb.append("</select>");
									smalsb.append("</ul>");
									smalsb.append("</div>");
								}
							}
						}
					}
				}
				sb.append(smalsb);
			}
		}
		return sb.toString();
	}
}
