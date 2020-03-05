package cn.com.trueway.document.component.docNumberManager.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.velocity.runtime.directive.Define;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.Step;
import cn.com.trueway.document.business.model.Doc;
import cn.com.trueway.document.business.model.DocBw;
import cn.com.trueway.document.component.docNumberManager.dao.DocNumberManagerDao;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberDoc;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberFlow;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberModel;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberType;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhBw;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhFw;
import cn.com.trueway.document.component.docNumberManager.model.vo.DocNumber;
import cn.com.trueway.document.component.docNumberManager.model.vo.DocNumberSmallClass;


public class DocNumberManagerDaoImpl extends BaseDao implements DocNumberManagerDao{

	@SuppressWarnings("unchecked")
	public List<DocNumberType> getDocNumBigClassList(String conditionSql ,Integer pageindex, Integer pagesize){
		String querySql = "select * from docnumber_type t where 1=1 and t.isparent = '0' ";
		querySql += conditionSql;
		Query query=super.getEm().createNativeQuery(querySql,DocNumberType.class);
		if (pageindex!=null&&pagesize!=null) {
			query.setFirstResult(pageindex);
			query.setMaxResults(pagesize);
		}
		return query.getResultList();  
	}
	
	public int getDocnumberTypeCount(String webid,String isparent){
		String sql = "select count(typeid) from docnumber_type where webid = :webid";
		if(isparent!=null&&isparent.trim().length()==0){
			sql += " and isparent = :isparent";
		}
		Query q = getEm().createNativeQuery(sql).setParameter("webid", webid);
		if(isparent!=null&&isparent.trim().length()==0){
			q.setParameter("isparent", isparent);
		}
		return ((BigDecimal)q.getSingleResult()).intValue();
	}
	
	public DocNumberType getSingleDocNumberType(String typeid) {
		return getEm().find(DocNumberType.class, typeid==null?"":typeid);
	}
	
	/**
	 * 
	 * 描述：持久化实例类型<br>
	 *
	 * @param whType void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-12 下午04:22:51
	 */
	public void addDocNumber(DocNumberType type){
		if(type!=null&&(type.getTypeid()==null||type.getTypeid().trim().length()==0)){
			type.setTypeid(null);
		}
		super.getEm().persist(type);
	}

	public void updateDocNumber(DocNumberType type){
		super.getEm().merge(type);
	}
	
	public void deletebigWh(String[] typeids){
		if(typeids.length>0){
			StringBuffer sb=new StringBuffer("delete from docnumber_type w where w.TYPEID in (");//删除大类
			StringBuffer sb1=new StringBuffer("delete from docnumber_type w where w.PARENTID in (");//删除子类
			for(int i=0;i<typeids.length;i++){
				if (i==typeids.length-1) {
					sb.append("'"+typeids[i]+"'");
					sb1.append("'"+typeids[i]+"'");
				}else {
					sb.append("'"+typeids[i]+"',");
					sb1.append("'"+typeids[i]+"'");
				}
			}
			sb.append(")");
			sb1.append(")");
			getEm().createNativeQuery(sb.toString()).executeUpdate();
			getEm().createNativeQuery(sb1.toString()).executeUpdate();
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<DocNumberSmallClass> getDocNumSmallClass(int currentPage, int numPerPage,String name, String parentId,String workflowId){
			
		StringBuffer querySql = new StringBuffer();
		querySql.append("select t.typeid as typeid,t.parentid as parentid,t.name as name,t.webid as webid,t.doctype as doctype,par.name as parentName,t.OPERATE_LOG_ID as type from DOCNUMBER_TYPE t,(select d.typeid,d.name from docnumber_type d where d.isparent = '0' ");
		if(parentId!=null&&parentId.trim().length()!=0){
			querySql.append(" and d.typeid = '").append(parentId).append("'");
		}
		querySql.append(") par where t.parentid = par.typeid");
		querySql.append(" and t.webid = '"+workflowId+"'");
		if(name!=null&&name.trim().length()!=0){
			querySql.append(" and t.name like '%").append(name).append("%'");
		}
		List<Object[]> olist = (List<Object[]>)getEm().createNativeQuery(querySql.toString()).setFirstResult(currentPage).setMaxResults(numPerPage).getResultList();
		List<DocNumberSmallClass> list = new ArrayList<DocNumberSmallClass>();
		for(Object[] obj : olist){
			if(obj!=null){
				DocNumberSmallClass ds = new DocNumberSmallClass();
				ds.setTypeid(obj[0]==null?null:obj[0].toString());
				ds.setParentid(obj[1]==null?null:obj[1].toString());
				ds.setName(obj[2]==null?null:obj[2].toString());
				ds.setWebid(obj[3]==null?null:obj[3].toString());
				ds.setDoctype(obj[4]==null?null:obj[4].toString());
				ds.setParentName(obj[5]==null?null:obj[5].toString());
				ds.setType(obj[6]==null?null:obj[6].toString());
				list.add(ds);
			}
		}
//		int counts = ((BigDecimal)getEm().createNativeQuery(totalRowsSql.toString()).getSingleResult()).intValue();
//		dt.setDataList(list);
//		dt.setTotalPages(counts % numPerPage == 0 ? counts / numPerPage : counts/numPerPage + 1);
//		dt.setTotalRows(counts);
//		dt.setCurrentPage(currentPage);
		return list;
	}

	public void deleteDocNumDoc(String defineid,String modelId){
		String sql = "delete from DOCNUMBER_DOC t where t.doctypeid= :defineid ";
		if(modelId!=null&&modelId.trim().length()!=0){
			sql += " and t.whmodelid = :modelid ";
		}
		Query q = getEm().createNativeQuery(sql).setParameter("defineid", defineid);
		if(modelId!=null&&modelId.trim().length()!=0){
			q.setParameter("modelid", modelId);
		}
		q.executeUpdate();
	}
	
	/**
	 * 小类删除
	 */
	public void deletesmallWh(String[] typeids){
//		//如果已经被WHFLOW表使用过，就不可以删除，只能修改。
//		if(typeids.length>0){
//			for(int i=0;i<typeids.length;i++){
//				StringBuilder hql = new StringBuilder();
//				hql.append(" from  WhFlow t where t.typeid='"+typeids[i]+"'");
//				Query query = super.getEm().createQuery(hql.toString());
//				List list=query.getResultList();
//				if(!list.isEmpty()){}else{
//					WhType w=this.getupdatebigWh(typeids[i]);
//					super.getEm().remove(w);
//				}
//			}
//		}
		if(typeids.length>0){
			StringBuffer sb=new StringBuffer("delete from docnumber_type w where w.TYPEID in (");
			for(int i=0;i<typeids.length;i++){
				if (i==typeids.length-1) {
					sb.append("'"+typeids[i]+"'");
				}else {
					sb.append("'"+typeids[i]+"',");
				}
			}
			sb.append(")");
			Query query=super.getEm().createNativeQuery(sb.toString());
			query.executeUpdate();
		}

	}
	
	public void addDocNumberDoc(DocNumberDoc doc){
		getEm().persist(doc);
	}
	
	@SuppressWarnings("unchecked")
	public List<DocNumberType> getAllWhTypeByWebid(String webid, String isparent,String parentid) {
		String	hql="from DocNumberType wt where wt.isparent=:isparent";
		if(parentid!=null){
			hql += " and wt.parentid=:parentid";
		}
		if(webid!=null){
			hql += " and wt.webid = :webid";
		}
		Query query=super.getEm().createQuery(hql);
		query.setParameter("isparent", isparent);
		if(parentid!=null){
			query.setParameter("parentid", parentid);
		}
		if(webid!=null){
			query.setParameter("webid", webid);
		}
		return query.getResultList();
	}
	/**
	 * 
	 * @Title: addWHModel
	 * @Description: 添加文号实例模型
	 * @param whModel
	 * @throws DataAccessException void    返回类型
	 */
	public void addWHFlow(DocNumberFlow whFlow) throws DataAccessException {
		super.getEm().persist(whFlow);
	}
	/**
	 * 
	 * @Title: addWHFlow
	 * @Description: 添加文号实例流程绑定数据
	 * @param whFlow
	 * @throws DataAccessException void    返回类型
	 */
	public void addWHModel(DocNumberModel whModel) throws DataAccessException {
		super.getEm().persist(whModel);
	}
	
	public Define getDefineById(String define){
		try {
			return getEm().find(Define.class, define);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 查询文号实例
	 */
	public DocNumberModel getWHModelByParam(String userid, String date, String webid)
			throws DataAccessException {
		String hql="from DocNumberModel wh where wh.userid=? and to_char(wh.time,'YYYY-MM-DD HH24:MI:SS')=? and wh.webid=?";
		Query query=super.getEm().createQuery(hql);
		query.setParameter(1, userid);
		query.setParameter(2, date);
		query.setParameter(3, webid);
		return (DocNumberModel)query.getResultList().get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<DocNumberModel> getDocNumModel(String webid, String content, int currentPage, int numPerPage, String lcid){
		String querySql = "select * from docnumber_model where webid = '"+webid+"' and workflowid='"+lcid+"'";
		if( content != null){
			querySql += " and content like '%" + content + "%'";
		}
		List<Object[]> olist = (List<Object[]>)getEm().createNativeQuery(querySql.toString()).setFirstResult((currentPage-1) * numPerPage<=0?0:(currentPage-1) * numPerPage).setMaxResults(numPerPage).getResultList();
		List<DocNumberModel> list = new ArrayList<DocNumberModel>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Object[] obj : olist){
			if(obj!=null){
				DocNumberModel ds = new DocNumberModel();
				ds.setModelid(obj[0]==null?null:obj[0].toString());
				ds.setModelindex(Integer.parseInt(obj[1]==null?null:obj[1].toString()));
				ds.setUserid(obj[2]==null?null:obj[2].toString());
				try {
//					String dt = sdf.format(sdf.parse(obj[3]==null?null:obj[3].toString()));
					ds.setTime(sdf.parse(obj[3]==null?null:obj[3].toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				ds.setWebid(obj[4]==null?null:obj[4].toString());
				ds.setDocids(obj[5]==null?null:obj[5].toString());
				ds.setContent(obj[6]==null?null:obj[6].toString());
				list.add(ds);
			}
		}
		return list;
	}
	/**
	 * 查询所有文号实例(分页、条件)
	 */
	public Object[] getAllWHModel(int pageIndex, int pagesize,
			String webid, String content) throws DataAccessException {
		Object[] objects=new Object[2];
		String hql="from DocNumberModel wh where wh.webid=?";
		if (content!=null) {
			hql+=" and wh.content like '%"+content+"%'";
		}
		Query query=super.getEm().createQuery(hql);
		query.setParameter(1, webid);
		query.setFirstResult(pageIndex);
		query.setMaxResults(pagesize);
		objects[0]=query.getResultList();
		
		Query query1=super.getEm().createQuery(hql);
		query1.setParameter(1, webid);
		objects[1]=query1.getResultList().size();
		return objects;
	}
	/**
	 * 删除多个文号实例
	 */
	public void deleteManyWHModels(String ids) throws DataAccessException {
		//删除实例流程表信息
		String flowSql="delete from docnumber_flow w where w.MODELID in ("+ids+")";
		getEm().createNativeQuery(flowSql).executeUpdate();
		//删除文号实例信息
		String modelSql="delete from docnumber_model w where w.MODELID in ("+ids+")";
		getEm().createNativeQuery(modelSql).executeUpdate();
	}

	public DocNumberModel getDocNumModelById(String modelId){
		return getEm().find(DocNumberModel.class, modelId);
	}
	/**
	 * 更新文号实例
	 */
	public void updateWhModel(DocNumberModel whModel) throws DataAccessException {
		String sql="update docnumber_model w set w.MODELINDEX=? where w.MODELID=?";
		Query query=super.getEm().createNativeQuery(sql);
		query.setParameter(1, whModel.getModelindex());
		query.setParameter(2, whModel.getModelid());
		query.executeUpdate();
	}
	
	public void updatesmallWh(DocNumberType whType){
		super.getEm().merge(whType);
	}
	@SuppressWarnings("unchecked")
	public List<DocNumberModel> getAllWhModelByWebid(String webid,String workflowId)
			throws DataAccessException {
		String hql="from DocNumberModel wh where wh.webid=? and wh.workflowId=? order by wh.modelindex asc";
		Query query=super.getEm().createQuery(hql);
		query.setParameter(1, webid);
		query.setParameter(2, workflowId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getBindWhModelByWebid(String webid,String defid){
		String sql="select t.content from DOCNUMBER_MODEL t,Docnumber_Doc d,wf_define f where t.modelid=d.whmodelid and d.doctypeid=f.wf_uid and t.webid=:webid and f.wf_uid=:defid";
		Query query=super.getEm().createNativeQuery(sql);
		query.setParameter("webid", webid).setParameter("defid", defid);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Define> getAllDefines(List<String> deps) throws DataAccessException {
		StringBuffer buffer = new StringBuffer("from Define t where (");
		//临时使用，只针对如东县使用
//		buffer.append("t.depIds like '%{BFA7FFA6-0000-0000-5556-04F100000002};%'");
		//如需改，改成下面注释的
		for (int i = 0; i < deps.size(); i++) {
			String depid = deps.get(i);
			if(i!=0)buffer.append(" or ");
			buffer.append("t.depIds like '%");
			buffer.append(depid);
			buffer.append("%'");
		}
		buffer.append(")");
		Query query = super.getEm().createQuery(buffer.toString());
		List<Define> l = query.getResultList();
		return l;
	}

	public void updateWHModelForBangdingDoc(String doctypeids,String modelid)
			throws DataAccessException {
		String sql="update docnumber_model m set m.DOCIDS=? where m.MODELID=?";
		Query query=super.getEm().createNativeQuery(sql);
		query.setParameter(1, doctypeids);
		query.setParameter(2, modelid);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<DocNumberDoc> getWHModelsByDoctypeid(String doctypeid)
			throws DataAccessException {
		String hql="from DocNumberDoc w where w.doctypeid = :doctypeid";
		Query query=super.getEm().createQuery(hql);
		query.setParameter("doctypeid", doctypeid);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Step> getDocStepByStepId(String stepid)
			throws DataAccessException {
		String hql="from Step s where s.wfStUid=?";
		Query query=super.getEm().createQuery(hql);
		query.setParameter(1, stepid);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<DocNumberModel> getWHModelsByModelIds(List<DocNumberDoc> whdocs)
			throws DataAccessException {
		StringBuffer hql=new StringBuffer();
		hql.append("from DocNumberModel m where 1=1");
		if (whdocs!=null&&whdocs.size()>0) {
			hql.append(" and m.modelid in (");
			for (int i = 0; i < whdocs.size(); i++) {
				if (i==whdocs.size()-1) {
					hql.append("'"+whdocs.get(i).getWhmodelid()+"'");
				}else {
					hql.append("'"+whdocs.get(i).getWhmodelid()+"',");
				}
			}
			hql.append(")");
		}
		return super.getEm().createQuery(hql.toString()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<DocNumberModel> getWHModelsByContent(String content, String webid, String workflowId)
			throws DataAccessException {
		String hql="from DocNumberModel m where m.webid=? and m.content=? and m.workflowId=?";
		Query query=super.getEm().createQuery(hql);
		query.setParameter(1, webid);
		query.setParameter(2, content);
		query.setParameter(3, workflowId);
		return (List<DocNumberModel>)query.getResultList();
	}
	
	public DTPageBean bwDocNumUsed(String nh, String bwlx,String lwdwlx, String bwxh, String order,int pageIndex, int pageSize) {
		StringBuilder hql = new StringBuilder();
		bwlx =  bwlx== null ? "" : bwlx.trim();
		lwdwlx =  lwdwlx== null ? "" : lwdwlx.trim();
		hql.append("select n.bwnh as nh,n.bwlx as lx,n.bwxh as xh,n.title as title,n.recdate as usetime,n.lwdwlx as dw, '1' as isused,n.ngr as ngr,n.security as miji,n.ngrbm as ngrbm from oa_doc_bw n where n.PARENT_ID is null and n.result != ").append(Constant.BWD_RESULT_ZUOFEI);
		StringBuffer count = new StringBuffer("select count(n.bwguid) from oa_doc_bw n where n.PARENT_ID is null and n.result != ").append(Constant.BWD_RESULT_ZUOFEI);
		if(nh!=null&&nh.trim().length()!=0){
			hql.append(" and n.bwnh = '").append(nh).append("' ");
			count.append(" and n.bwnh = '").append(nh).append("' ");
		}
		if (!bwlx.equals("")) {
			hql.append(" and n.bwlx = '").append(bwlx).append("' ");
			count.append(" and n.bwlx = '").append(bwlx).append("' ");
		}
		hql.append(" and n.bwlx is not null ");
		if (!lwdwlx.equals("")) {
			hql.append(" and n.lwdwlx = '").append(lwdwlx).append("' ");
			count.append(" and n.lwdwlx = '").append(lwdwlx).append("' ");
		}
		if(bwxh!=null&&bwxh.trim().length()!=0){
			hql.append(" and n.bwxh = '").append(bwxh).append("' ");
			count.append(" and n.bwxh = '").append(bwxh).append("' ");
		}
		if(order != null && order.trim().length() != 0){
			hql.append("order by ");
			if(Constant.DOCNUM_NUM_ASC.split("_")[0].equals(order.split("_")[0])){
				hql.append(" n.bwxh ");
			}else{
				hql.append(" n.recdate ");
			}
			hql.append(order.split("_")[1]);
		}else{
			hql.append(" order by n.bwxh ");
		}
		DTPageBean dt = super.pagingQuery(count.toString(), hql.toString(), pageIndex, pageSize, "DocNumVOResults");
		return dt;
	}
	
	public DTPageBean fwAlreadyUsedDocNum(String jgdz, String fwnh,String fwxh,String order, int pageIndex, int pageSize) {
		jgdz = jgdz == null ? "" : jgdz.trim();
		fwnh = fwnh == null ? "" : fwnh.trim();
		StringBuffer querySql = new StringBuffer("select  n.jgdz as lx,n.fwnh as nh, n.fwxh as xh,'1' as isused, '' as dw,n.title as title,n.submittm as usetime,n.ngr as ngr,n.record as miji,n.ngrbm as ngrbm from OA_DOC_DOCBOX n where n.jgdz = '"+jgdz+"' and n.fwnh = '"+fwnh+"' ");
		StringBuffer totalRowsSql = new StringBuffer("select count(n.docguid) from OA_DOC_DOCBOX n where n.jgdz = '"+jgdz+"' and n.fwnh = '"+fwnh+"' ");
		if(fwxh!=null&&fwxh.trim().length()!=0){
			querySql.append(" and n.fwxh= '").append(fwxh).append("' ");
			totalRowsSql.append(" and n.fwxh= '").append(fwxh).append("' ");
		}
		if(order != null && order.trim().length() != 0){
			querySql.append("order by ");
			if(Constant.DOCNUM_NUM_ASC.split("_")[0].equals(order.split("_")[0])){
				querySql.append(" n.fwxh ");
			}else{
				querySql.append(" n.submittm ");
			}
			querySql.append(order.split("_")[1]);
		}else{
			querySql.append(" order by n.fwxh ");
		}
		return pagingQuery(totalRowsSql.toString(), querySql.toString(), pageIndex, pageSize, "DocNumVOResults");
	}

	public void addDoDocNumber(DocNumber dn) {
		DocBw bw = new DocBw();
		bw.setBwGuid(UuidGenerator.generate36UUID());
		bw.setBwlx(dn.getType());
		bw.setBwnh(dn.getYear());
		bw.setBwxh(dn.getNumber());
		bw.setLwdwlx(dn.getLwdwlx());
		bw.setWh("["+dn.getYear()+"]"+dn.getType()+(dn.getLwdwlx()==null?"":dn.getLwdwlx())+dn.getNumber()+"号");
		bw.setTitle(dn.getTitle());
		bw.setNgr(dn.getNgr());
		bw.setSecurity(dn.getSecurity());
		bw.setNgrbm(dn.getNgrbm());
		bw.setResult(1);
		getEm().persist(bw);
	}
	
	public void addSendDocNumber(DocNumber dn) {
		Doc bw = new Doc();
		bw.setDocguid(UuidGenerator.generate36UUID());
		bw.setJgdz(dn.getType());
		bw.setFwnh(dn.getYear());
		bw.setFwxh(dn.getNumber());
		bw.setTitle(dn.getTitle());
		bw.setSender(dn.getDepGuid());
		bw.setSubmittm(dn.getTime());
		bw.setRecord(dn.getSecurity());
		bw.setNgr(dn.getNgr());
		bw.setNgrbm(dn.getNgrbm());
		getEm().persist(bw);
	}
	
	/*-------------------------------------------------策略维护-----------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public List<DocNumberStrategy> getStrategyList(){
		String sql = "from DocNumberStrategy";
		List<DocNumberStrategy> list = (List<DocNumberStrategy>)getEm().createQuery(sql).getResultList();
		return list;
	}
	
	public DocNumberStrategy getSingnStrategy(String id){
		String sql = "from DocNumberStrategy t where t.strategyId=:id";
		try {
			return (DocNumberStrategy)getEm().createQuery(sql).setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public void updateStrategy(DocNumberStrategy doc){
	 	getEm().merge(doc);
	}
	
	public void addStrategy(DocNumberStrategy doc){
	 	getEm().persist(doc);
	}
	
	public void delStrategy(String id){
	 	getEm().createNativeQuery("delete from docnumber_strategy where STRATEGYID = :id").setParameter("id", id).executeUpdate();
	}

	public int getDocNumBigClasses(String conditionSql) {
		String hql="from DocNumberType t where t.parentid='0'";
		hql += conditionSql;
		return super.getEm().createQuery(hql).getResultList().size();
	}

	public int getDocNumBigClasses(String name,String parentId,String workflowId) {
		StringBuffer totalRowsSql = new StringBuffer();
		totalRowsSql.append("select count(t.typeid) from DOCNUMBER_TYPE t,")
		.append("(select d.typeid from docnumber_type d where d.isparent = '0' ");
		if(parentId!=null&&parentId.trim().length()!=0){
			totalRowsSql.append(" and d.typeid = '").append(parentId).append("'");
		}
		totalRowsSql.append(") par where t.parentid = par.typeid");
		totalRowsSql.append(" and t.webid = '"+workflowId+"'");
		if(name!=null&&name.trim().length()!=0){
			totalRowsSql.append(" and t.name like '%").append(name).append("%'");
		}
		return Integer.parseInt(super.getEm().createNativeQuery(totalRowsSql.toString()).getResultList().get(0).toString());
	}

	public int getDocNumModelCount(String webid, String content,String workflowId) {
		String totalRowsSql = "select count(modelid) from docnumber_model where webid = '"+webid+"' and workflowId = '"+workflowId+"'";
		if( content != null){
			totalRowsSql += " and content like '%" + content + "%'";
		}
		return Integer.parseInt(super.getEm().createNativeQuery(totalRowsSql.toString()).getResultList().get(0).toString());
	}

	public DocNumberWhFw findDocNumWhFw(String workFlowId,String instanceId) {
		String sql = "select twf.* from T_WF_CORE_WH_FW twf where twf.workflowid='"+workFlowId+"' and twf.instanceid='"+instanceId+"'";
		
		List<DocNumberWhFw> list = super.getEm().createNativeQuery(sql,DocNumberWhFw.class).getResultList();
		if(list != null && list.size() >0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public DocNumberWhBw findDocNumWhBw(String workFlowId, String instanceId) {
			String sql = "select twf.* from T_WF_CORE_WH_FW twf where twf.workflowid='"+workFlowId+"' and twf.instanceid='"+instanceId+"'";
		
		List<DocNumberWhBw> list = super.getEm().createNativeQuery(sql,DocNumberWhBw.class).getResultList();
		if(list != null && list.size() >0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
