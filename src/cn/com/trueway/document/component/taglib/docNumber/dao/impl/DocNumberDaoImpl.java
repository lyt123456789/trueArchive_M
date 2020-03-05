package cn.com.trueway.document.component.taglib.docNumber.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumOnlyWh;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhBw;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhFw;
import cn.com.trueway.document.component.taglib.docNumber.dao.DocNumberDao;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.WfItem;

public class DocNumberDaoImpl extends BaseDao implements DocNumberDao{
	/**
	 * 
	 * 描述：根据条件获取办文最大序号<br>
	 * @param siteId
	 * @param wh
	 * @param nd
	 * @param lwdwlx
	 * @return Integer
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:15:15
	 */
	public Integer getMaxBWXH(String siteId,String wh, String nd, String lwdwlx, String webId,String workflowId) {
		String newWhWorkFlowId = SystemParamConfigUtil.getParamValueByParam("newWhWorkFlowId");
		String hql = "select max(to_number(BWXH)) from T_WF_CORE_WH_BW "
				+ " where 1 = 1";
		if(wh != null && !"".equals(wh)){
//			if(wh.contains("-")){
				if(newWhWorkFlowId!=null && newWhWorkFlowId.contains(workflowId)){
					hql += "  and BWLX like '"+wh+"%' ";
					wh = "";
				}else{
					hql += " and BWLX =:bwlx ";
				}
//			}else{
//				hql += "  and BWLX = :bwlx";
//			}
		}else{
			hql += "  and BWLX is null";
		}
		if (nd != null && !"".equals(nd)) {
			hql += "  and BWNH = :bwnh";
		}
		if (lwdwlx != null && !"".equals(lwdwlx)) {
			hql += "  and lwdwlx = :lwdwlx";
		}
		if (CommonUtil.stringNotNULL(siteId)) {
			hql += "  and siteid = :siteId";
		}
		if (workflowId != null && !"".equals(workflowId)) {
			hql += "  and workflowId = :workflowId";
		}
		if (webId != null && !"".equals(webId)) {
			hql += "  and webId = :webId";
		}
		//hql +=" and result != :result and webid = :webid";
		hql +=" and result != :result";
		Query query = super.getEm().createNativeQuery(hql);
		query.setParameter("result", Constant.BWD_RESULT_ZUOFEI);
		if (webId != null && !"".equals(webId)) {
			query.setParameter("webId", webId);
		}
		if(wh != null && !"".equals(wh)){
			query.setParameter("bwlx", wh);
		}
		if (nd != null && !"".equals(nd)) {
			query.setParameter("bwnh", nd);
		}
		if (CommonUtil.stringNotNULL(siteId)) {
			query.setParameter("siteId", siteId);
		}
		if (workflowId != null && !"".equals(workflowId)) {
			query.setParameter("workflowId", workflowId);
		}
		if (lwdwlx != null && !"".equals(lwdwlx)) {
			query.setParameter("lwdwlx", lwdwlx);
		}
		Object value = (Object) query.getSingleResult();
		return value == null ? 0 : Integer.parseInt(value.toString());
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getMaxBWXH(String webId) {
		String hql = "select wh from OA_DOC_REST "
			+ " where 1 = 1";
		hql +=" and result != :result and webid = :webid";
		Query query = super.getEm().createNativeQuery(hql);
		query.setParameter("result", Constant.BWD_RESULT_ZUOFEI);
		query.setParameter("webid", webId);
		return query.getResultList();
	}
	
	/**
	 * 
	 * 描述：TODO 对此方法进行描述<br>
	 *
	 * @param siteId
	 * @param wh
	 * @param nd
	 * @return Integer
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:15:46
	 */
	public Integer getMaxFWXH(String siteId,String wh, String nd,String workflowId) {
		String newWhWorkFlowId = SystemParamConfigUtil.getParamValueByParam("newWhWorkFlowId");
		String hql = "select max(to_number(FWXH))" + "  from T_WF_CORE_WH_FW "
				+ " where 1=1 ";
		if(StringUtils.isNotBlank(workflowId)){
			if(StringUtils.isNotBlank(newWhWorkFlowId) && newWhWorkFlowId.contains(workflowId)){
				hql += " and JGDZ like '"+wh+"%' ";
			}else{
				hql += " and JGDZ = '"+wh+"' ";
			}
		}
		if (nd != null && !"".equals(nd)) {
			hql += "  and FWNH = :fwnh";
		}
		if(CommonUtil.stringNotNULL(siteId)){
			hql += " and siteid = :siteId ";
		}
		Query query = super.getEm().createNativeQuery(hql);
		if(("").equals(wh) || wh == null){
			wh = "号";
		}
		//query.setParameter("jgdz", wh);
		if (nd != null && !"".equals(nd)) { 
			query.setParameter("fwnh", nd);
		}
		if (CommonUtil.stringNotNULL(siteId)) { 
			query.setParameter("siteId", siteId);
		}
		BigDecimal value = (BigDecimal) query.getSingleResult();
		if(value==null){
			return 0;
		}
		return value.intValue();
	}
	/**
	 * 
	 * @param gidzs
	 * @param nh
	 * @return Integer
	 */
	public Integer getGroupMaxFWXH(String siteId,String gidzs,String nh){
		String hql="select max(to_number(FWXH)) from t_wf_core_wh_fw where ";
		String str="jgdz in ("+gidzs+")";
		hql+=str;
		if(nh!=null&&!"".equals(nh)){
			hql+=" and FWNH = :fwnh ";
		}
		if(CommonUtil.stringNotNULL(siteId)){
			hql+=" and siteid = :siteId ";
		}
		Query query=super.getEm().createNativeQuery(hql);
		if(nh!=null&&!"".equals(nh)){
			query.setParameter("fwnh", nh);
		}
		if(CommonUtil.stringNotNULL(siteId)){
			query.setParameter("siteId", siteId);
		}
		BigDecimal value=(BigDecimal)query.getSingleResult();
		if(value==null){
			return 0;
		}
		return value.intValue();
	}
	/**
	 * 
	 * 描述：TODO 对此方法进行描述<br>
	 *
	 * @param jgdz
	 * @param fwnh
	 * @param fwxh
	 * @param pageIndex
	 * @param pageSize
	 * @return List<Doc>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:15:55
	 */
	@SuppressWarnings("unchecked") 
	public List<DocNumberWhFw> fwAlreadyUsedDocNum(String siteId,String workflowId,String jgdz, String fwnh,
			String fwxh, int pageIndex, int pageSize) {
		String newWhWorkFlowId = SystemParamConfigUtil.getParamValueByParam("newWhWorkFlowId");
		List<DocNumberWhFw> list = new ArrayList<DocNumberWhFw>();
		jgdz = jgdz == null ? "" : jgdz.trim();
		fwnh = fwnh == null ? "" : fwnh.trim();
		fwxh = fwxh == null ? "" : fwxh.trim();
		workflowId = workflowId == null ? "" : workflowId.trim();
		String sql = "select n.jgdz,n.fwnh,n.fwxh from T_WF_CORE_WH_FW n where 1=1 ";
		if(CommonUtil.stringNotNULL(jgdz) && !"undefined".equalsIgnoreCase(jgdz)){
//			 sql = sql + " and n.jgdz =:jgdz ";
			if(newWhWorkFlowId!=null && newWhWorkFlowId.contains(workflowId)){
				sql = sql + " and n.jgdz like '"+jgdz+"%' ";
				jgdz = "";
			}else{
				sql += " and n.jgdz =:jgdz ";
			}
		}
		if(CommonUtil.stringNotNULL(fwnh) && !"undefined".equalsIgnoreCase(fwnh)){
			 sql = sql + " and n.fwnh =:fwnh ";
		}
		/*if(CommonUtil.stringNotNULL(workflowId) && !"undefined".equalsIgnoreCase(workflowId)){
			 sql = sql + " and n.workflowid =:workflowId ";
		}*/
		if(CommonUtil.stringNotNULL(siteId)){
			sql = sql + " and n.siteid =:siteId ";
		}
		
		
		sql = sql + " order by n.fwxh";
		final int startRow = pageIndex * pageSize;
		Query query = super.getEm().createNativeQuery(sql);
		if(CommonUtil.stringNotNULL(jgdz) && !"undefined".equalsIgnoreCase(jgdz)){
			query.setParameter("jgdz", jgdz);
		}
		if(CommonUtil.stringNotNULL(fwnh) && !"undefined".equalsIgnoreCase(fwnh)){
			query.setParameter("fwnh", fwnh);
		}
		if(CommonUtil.stringNotNULL(siteId)){
			query.setParameter("siteId", siteId);
		}
		/*if(CommonUtil.stringNotNULL(workflowId)  && !"undefined".equalsIgnoreCase(workflowId)){
			query.setParameter("workflowId", workflowId);
		}*/
		try {
			query.setFirstResult(startRow);
			query.setMaxResults(pageSize);
			List<Object[]> object=query.getResultList();
			for(Object[] obj:object){
				DocNumberWhFw d = new DocNumberWhFw();
				d.setJgdz(obj[0]==null?null:obj[0].toString());
				d.setFwnh(obj[1]==null?null:obj[1].toString());
				d.setFwxh(obj[2]==null?null:obj[2].toString());
				list.add(d);
			}
		} catch (javax.persistence.NoResultException e) {
			list = null;
		}
		return list;
	}
	/**
	 * @param siteId
	 * @param jgdzs
	 * @param fwnh
	 * @param pageIndex
	 * @param pageSize
	 * @return List<Doc>
	 */
	@SuppressWarnings("unchecked")
	public List<DocNumberWhFw> fwGroupAlreadyUsedDocNum(String siteId,String jgdzs,String fwnh,String workflowId,int pageIndex,int pageSize){
		String newWhWorkFlowId = SystemParamConfigUtil.getParamValueByParam("newWhWorkFlowId");
		List<DocNumberWhFw> lists=new ArrayList<DocNumberWhFw>();
		jgdzs=jgdzs==null?"":jgdzs.trim();
		fwnh=fwnh==null?"":fwnh.trim();
//		String sql="select o.fwnh,o.fwxh from t_wf_core_wh_fw o where o.jgdz in ("+jgdzs+") ";
		String sql="select o.fwnh,o.fwxh from t_wf_core_wh_fw o where 1=1  ";
		if(newWhWorkFlowId!=null && newWhWorkFlowId.contains(workflowId)){
			sql += " and o.jgdz like '"+jgdzs+"%' ";
		}
		if(CommonUtil.stringNotNULL(fwnh) && !"undefined".equalsIgnoreCase(fwnh)){
			 sql = sql + " and o.fwnh=:fwnh ";
		}
		if(CommonUtil.stringNotNULL(siteId)){
			sql = sql + " and o.siteid=:siteId ";
		}
		sql = sql + " order by o.fwxh";
		int startRow=pageIndex*pageSize;
		Query query=super.getEm().createNativeQuery(sql);
		if(CommonUtil.stringNotNULL(fwnh) && !"undefined".equalsIgnoreCase(fwnh)){
			query.setParameter("fwnh", fwnh);
		}
		if(CommonUtil.stringNotNULL(siteId)){
			query.setParameter("siteId", siteId);
		}		
		try{
			query.setFirstResult(startRow);
			query.setMaxResults(pageSize);
			List<Object[]> object=query.getResultList();
			for(Object[] obj:object){
				DocNumberWhFw docFw=new DocNumberWhFw();
				docFw.setFwnh(obj[0]==null?null:obj[0].toString());
				docFw.setFwxh(obj[1]==null?null:obj[1].toString());
				lists.add(docFw);
			}
		}catch(javax.persistence.NoResultException e){
			lists=null;
		}
		return lists;
	}
	
	/**
	 * 
	 * 描述：发文已用文号的条数<br>
	 *
	 * @param jgdz
	 * @param fwnh
	 * @param fwxh
	 * @param title
	 * @return int
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:17:46
	 */
	public int findCountwenhao(String jgdz, String fwnh,
			String fwxh, String title) {
		title = title == null ? "" : title.trim();
		jgdz = jgdz == null ? "" : jgdz.trim();
		fwnh = fwnh == null ? "" : fwnh.trim();
		fwxh = fwxh == null ? "" : fwxh.trim();
		String sql = "select count(n.fwxh) from OA_DOC_DOCBOX n where n.jgdz = :jgdz and n.fwnh = :fwnh";
		Query query = super.getEm().createNativeQuery(sql);
		BigDecimal bd = (BigDecimal)query.setParameter("jgdz", jgdz).setParameter("fwnh", fwnh).getSingleResult();
		return bd.intValue();
	}
	
	public int getIsfwDocNumUsed(String siteId,String xh, String gjdz,String nh,String webId,String workflowId){
		String newWhWorkFlowId = SystemParamConfigUtil.getParamValueByParam("newWhWorkFlowId");
//		String sql = "select count(n.fwxh) from T_WF_CORE_WH_FW n where n.fwxh = :fwxh and n.fwnh = :fwnh and n.jgdz = :gjdz";
		String sql = "select count(n.fwxh) from T_WF_CORE_WH_FW n where n.fwxh = :fwxh and n.fwnh = :fwnh ";
		if(StringUtils.isNotBlank(workflowId)){
			if(StringUtils.isNotBlank(newWhWorkFlowId)&&newWhWorkFlowId.contains(workflowId)){
				sql += " and n.jgdz like '"+gjdz+"%' ";
			}else{
				sql += " and n.jgdz ='"+gjdz+"' ";
			}
		}
		if(CommonUtil.stringNotNULL(siteId)){
			sql += " and n.siteid=:siteId ";
		}
		//String sql = "select count(n.fwxh) from T_WF_CORE_WH_FW n where n.fwxh = :fwxh and n.fwnh = :fwnh and n.jgdz = :gjdz and n.webid = '"+webId+"' and n.workflowid = '"+workflowId+"'";
		Query query = getEm().createNativeQuery(sql);
		if(CommonUtil.stringNotNULL(siteId)){
			query.setParameter("siteId", siteId);
		}
		BigDecimal bd = (BigDecimal)query.setParameter("fwxh", xh).setParameter("fwnh", nh)/*.setParameter("gjdz", gjdz)*/.getSingleResult();
		return bd.intValue();
	}
	
	public int getIsbwDocNumUsed(String siteId,String xh, String gjdz,String nh,String lwdwlx,String webId,String workflowId){
		String newWhWorkFlowId = SystemParamConfigUtil.getParamValueByParam("newWhWorkFlowId");
		String sql = "select count(n.bwxh) from T_WF_CORE_WH_BW n where n.bwxh = :bwxh and n.bwnh = :bwnh  and n.result != :result ";
		if(StringUtils.isNotBlank(workflowId)){
			if(StringUtils.isNotBlank(newWhWorkFlowId)&&newWhWorkFlowId.contains(workflowId)){
				sql += " and n.bwlx like '"+gjdz+"%'";
			}else{
				sql += " and n.bwlx = '"+gjdz+"' ";
			}
		}
		if(lwdwlx!=null&&!lwdwlx.trim().equals("")){
			sql += " and n.lwdwlx = :lwdwlx ";
		}
		if(workflowId!=null&&!workflowId.trim().equals("")){
			sql += " and n.workflowid = :workflowId ";
		}
		if(StringUtils.isNotBlank(webId)){
			sql += " and n.webid = :webid ";
		}
		if(CommonUtil.stringNotNULL(siteId)){
			sql += " and n.siteid = :siteId ";
		}
		Query query = getEm().createNativeQuery(sql).setParameter("bwxh", xh).setParameter("bwnh", nh)/*.setParameter("bwlx", gjdz)*/.setParameter("result", Constant.BWD_RESULT_ZUOFEI);
		if(lwdwlx!=null&&!lwdwlx.trim().equals("")){
			query.setParameter("lwdwlx", lwdwlx);
		}
		if(workflowId!=null&&!workflowId.trim().equals("")){
			query.setParameter("workflowId", workflowId);
		}
		if(StringUtils.isNotBlank(webId)){
			query.setParameter("webid", webId);
		}
		if(CommonUtil.stringNotNULL(siteId)){
			query.setParameter("siteId", siteId);
		}
		BigDecimal bd = (BigDecimal)query.getSingleResult();
		return bd.intValue();
	}
	/**
	 * 
	 * 描述：办文已用文号<br>
	 *
	 * @param nh
	 * @param bwlx
	 * @param lwdwlx
	 * @param pageIndex
	 * @param pageSize
	 * @return List
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:17:54
	 */
	@SuppressWarnings("unchecked")
	public List<DocNumberWhBw> bwDocNumUsed(String siteId,String workflowId,String nh, String bwlx, String lwdwlx, String webid, int pageIndex, int pageSize) {
		String newWhWorkFlowId = SystemParamConfigUtil.getParamValueByParam("newWhWorkFlowId");
		List<DocNumberWhBw> list = new ArrayList<DocNumberWhBw>();
		StringBuilder hql = new StringBuilder();
		bwlx =  bwlx== null ? "" : bwlx.trim();
		lwdwlx =  lwdwlx== null ? "" : lwdwlx.trim();
		workflowId =  workflowId== null ? "" : workflowId.trim();
		hql.append("select n.bwnh,n.bwlx,n.lwdwlx,n.bwxh from t_wf_core_wh_bw n where n.result!=:result ");
		if(nh!=null && !"undefined".equalsIgnoreCase(nh) && nh.trim().length()!=0){
			hql.append("and n.bwnh = :bwnh");
		}
		if(workflowId!=null&&!workflowId.trim().equals("")){
			hql.append(" and n.workflowid = :workflowId ");
		}
		if (!bwlx.equals("")) {
//			if(!bwlx.contains("-")){
//				hql.append(" and n.bwlx = :bwlx ");
//			}else{
			if(newWhWorkFlowId!=null && newWhWorkFlowId.contains(workflowId)){
				hql.append(" and n.bwlx like '"+bwlx+"%' ");
				bwlx = "";
			}else{
				hql.append(" and n.bwlx = :bwlx ");
			}
//			}
		} else {
			
			hql.append(" and n.bwlx is null ");
		}
//		hql.append(" and n.bwlx is not null ");
		if (!lwdwlx.equals("")) {
			hql.append(" and n.lwdwlx = :lwdwlx ");
		}
		if(StringUtils.isNotBlank(siteId)){
			hql.append(" and n.siteid = :siteid ");
		}
		// 暂时注释掉 不要去部门 移动端也是没有 session 可以取掉  如果药监的话 必须   配置html页面 也加上 webid= webid
		//hql.append(" and n.webid = '").append(webid).append("'");
		//.append(" order by n.recDate desc");

		final int startRow = pageIndex * pageSize;
		Query query = super.getEm().createNativeQuery(hql.toString());
		query.setParameter("result", Constant.BWD_RESULT_ZUOFEI);
		if(nh!=null && !"undefined".equalsIgnoreCase(nh) && nh.trim().length()!=0){
			query.setParameter("bwnh", nh);
		}
		if(!bwlx.equals("")){
			query.setParameter("bwlx", bwlx);
		}
		if(!lwdwlx.equals("")){
			query.setParameter("lwdwlx", lwdwlx);
		}
		if(workflowId!=null&&!workflowId.trim().equals("")){
			query.setParameter("workflowId", workflowId);
		}
		if(siteId!=null&&!siteId.trim().equals("")){
			query.setParameter("siteid", siteId);
		}
		try {
			query.setFirstResult(startRow);
			query.setMaxResults(pageSize);
			List<Object[]> objs = query.getResultList();
			for(Object[] obj : objs){
				DocNumberWhBw bw = new DocNumberWhBw();
				bw.setBwnh(obj[0]==null?null:obj[0].toString());
				bw.setBwlx(obj[1]==null?null:obj[1].toString());
				bw.setLwdwlx(obj[2]==null?null:obj[2].toString());
				bw.setBwxh(obj[3]==null?null:obj[3].toString());
				list.add(bw);
			}
		} catch (javax.persistence.NoResultException e) {
			list = null;
		}
		return list;
	}
	/**
	 * 
	 * 描述：办文已用文号的总条数<br>
	 *
	 * @param nh
	 * @param bwlx
	 * @param lwdwlx
	 * @param pageIndex
	 * @param pageSize
	 * @return int
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午11:17:54
	 */
	public int bwDocNumUsedCount(String nh, String bwlx, String lwdwlx,int pageIndex, int pageSize) {
		StringBuilder hql = new StringBuilder();
		bwlx =  bwlx== null ? "" : bwlx.trim();
		lwdwlx =  lwdwlx== null ? "" : lwdwlx.trim();
		hql.append("select count(n.bwguid) from oa_doc_bw n where n.result!=:result");
		if(nh!=null&&nh.trim().length()!=0){
			hql.append("and n.bwnh = :bwnh");
		}
		if (!bwlx.equals("")) {
			hql.append(" and n.bwlx = :bwlx ");
		}
		hql.append(" and n.bwlx is not null ");
		if (!lwdwlx.equals("")) {
			hql.append(" and n.lwdwlx = :lwdwlx ");
		}
		hql.append(" order by n.recDate desc");

		Query query = super.getEm().createQuery(hql.toString());
		if(nh!=null&&nh.trim().length()!=0){
			query.setParameter("bwnh", nh);
		}
		if(!bwlx.equals("")){
			query.setParameter("bwlx", bwlx);
		}
		if(!lwdwlx.equals("")){
			query.setParameter("lwdwlx", lwdwlx);
		}
		BigDecimal bd = (BigDecimal)query.setParameter("result", Constant.BWD_RESULT_ZUOFEI).getSingleResult();
		return bd.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Object> getBindWhModelByWebid(String webid, String defid) {
		String sql="select t.content from DOCNUMBER_MODEL t,Docnumber_Doc d,wf_main f where t.modelid=d.whmodelid and d.doctypeid=f.wfm_id and ";
		if(CommonUtil.stringNotNULL(webid)){
		    sql = sql + "t.webid=:webid and ";
		}
		sql = sql + "f.wfm_id=:defid ";
		sql = sql + "order by t.time";
		Query query=super.getEm().createNativeQuery(sql);
		if(CommonUtil.stringNotNULL(webid)){
		    query.setParameter("webid", webid).setParameter("defid", defid);
		}
		query.setParameter("defid", defid);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public String getSingleDocNum(String gjdz) {
		String sql = "select t.wh from OA_DOC_BW t where t.bwlx = :bwlx and t.parent_id is null and t.result != :result and t.wh is not null";
		List<Object> l = getEm().createNativeQuery(sql).setParameter("bwlx", gjdz).setParameter("result", Constant.BWD_RESULT_ZUOFEI).setMaxResults(1).getResultList();
		if(l.size()==0){
			String otherSql = "select t.jgdz,t.fwnh,t.fwxh from OA_DOC_DOCBOX t where t.jgdz = :jgdz and t.fwxh is not null";
			List<Object[]> otherl = getEm().createNativeQuery(otherSql).setParameter("jgdz", gjdz).setMaxResults(1).getResultList();
			if(otherl.size()!=0){
//				for(Object o : otherl){
//					if(o.toString()!=null&&o.toString().length()!=0){
//						return o.toString();
//					}
//				}
				return otherl.get(0)[0].toString()+"["+otherl.get(0)[1].toString()+"]"+otherl.get(0)[2].toString();
			}
		}else{
//			for(Object o : l){
//				if(o.toString()!=null&&o.toString().length()!=0){
//					return o.toString();
//				}
//			}
			return l.get(0).toString();
		}
		return "";
	}
	
	public Integer getDocNumAttach(String docNum){
		String sql = "select max(to_number(f.bwfh)) from OA_DOC_BW f where f.parent_id = (select bw.bwguid from OA_DOC_BW bw where bw.wh = :docNum and bw.result != :result) and f.result != :fresult";
		try {
			return Integer.valueOf(getEm().createNativeQuery(sql).setParameter("docNum", docNum).setParameter("result", Constant.BWD_RESULT_ZUOFEI).setParameter("fresult", Constant.BWD_RESULT_ZUOFEI).getSingleResult().toString());
		} catch (Exception e) {
			return null;
		}
	}

	public DocNumberStrategy getStrategy(String numStra) {
		if(numStra == null || numStra.trim().length()==0) return null;
		return (DocNumberStrategy) getEm().createNativeQuery("select * from DOCNUMBER_STRATEGY t where t.strategyid = '"+numStra+"'", DocNumberStrategy.class).getSingleResult();
	}

	public WfItem findItemByWorkFlowId(String defId) {
		String sql = "select * from t_wf_core_item t where t.lcid='"+defId+"'";
		if(getEm().createNativeQuery(sql,WfItem.class).getResultList().size()>0){
			return (WfItem) getEm().createNativeQuery(sql,WfItem.class).getResultList().get(0);
		}else{
			return null;
		}
	}

	public void addBw(DocNumberWhBw docNumBw) {
		getEm().persist(docNumBw);
	}

	public void addFw(DocNumberWhFw docNumFw) {
		getEm().persist(docNumFw);
	}

	public DocNumberWhBw findDocNumBw(String instanceId) {
		String sql = "select * from t_wf_core_wh_bw twb where twb.instanceid='"+instanceId+"'";
		getEm().createNativeQuery(sql,DocNumberWhBw.class).getResultList();
		if(getEm().createNativeQuery(sql,DocNumberWhBw.class).getResultList().size()>0){
			return (DocNumberWhBw) getEm().createNativeQuery(sql,DocNumberWhBw.class).getResultList().get(0);
		}else{
			return null;
		}
	}

	public DocNumberWhFw findDocNumFw(String instanceId) {
		String sql = "select * from t_wf_core_wh_fw tfb where tfb.instanceid='"+instanceId+"'";
		getEm().createNativeQuery(sql,DocNumberWhFw.class).getResultList();
		if(getEm().createNativeQuery(sql,DocNumberWhFw.class).getResultList().size()>0){
			return (DocNumberWhFw) getEm().createNativeQuery(sql,DocNumberWhFw.class).getResultList().get(0);
		}else{
			return null;
		}
	}

	public void updateBw(DocNumberWhBw docNumberWhBw) {
		getEm().merge(docNumberWhBw);
	}

	public void updateFw(DocNumberWhFw docNumberWhFw) {
		getEm().merge(docNumberWhFw);
	}

	@Override
	public WfItem findItemById(String itemId) {
		return getEm().find(WfItem.class, itemId);
	}

	@Override
	public void addDocNumOnlyWh(DocNumOnlyWh docNumOnlyWh) {
		if(null == docNumOnlyWh){
			return;
		}
		getEm().persist(docNumOnlyWh);
		
	}
	
	@Override
	public void updateNumOnlyWh(DocNumOnlyWh docNumOnlyWh) {
		if(null == docNumOnlyWh){
			return;
		}
		getEm().merge(docNumOnlyWh);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocNumOnlyWh> selectDocNumOnlyWh(String workflowId,
			String itemId, String isUsed) {
		StringBuffer hql = new StringBuffer(" from DocNumOnlyWh t where 1=1 ");
		if(StringUtils.isNotBlank(workflowId)){
			hql.append(" and t.workflowid = :workflowid");
		}
		if(StringUtils.isNotBlank(itemId)){
			hql.append(" and t.itemid = :itemid");
		}
		if(StringUtils.isNotBlank(isUsed) && isUsed.equals("0")){
			hql.append(" and (t.isUsed = '0' or t.isUsed is null) ");
		}
		hql.append(" order by t.wh");
		Query query = getEm().createQuery(hql.toString());
		if(StringUtils.isNotBlank(workflowId)){
			query.setParameter("workflowid", workflowId);
		}
		if(StringUtils.isNotBlank(itemId)){
			query.setParameter("itemid", itemId);
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer selectMaxDocNumOnlyWh(String workflowId, String itemId) {
		StringBuffer hql = new StringBuffer("select max(t.wh) from DocNumOnlyWh t where 1=1 ");
		if(StringUtils.isNotBlank(workflowId)){
			hql.append(" and t.workflowid = :workflowid");
		}
		if(StringUtils.isNotBlank(itemId)){
			hql.append(" and t.itemid = :itemid");
		}
		hql.append(" order by t.wh");
		Query query = getEm().createQuery(hql.toString());
		if(StringUtils.isNotBlank(workflowId)){
			query.setParameter("workflowid", workflowId);
		}
		if(StringUtils.isNotBlank(itemId)){
			query.setParameter("itemid", itemId);
		}
		List<Integer> list = query.getResultList();
		if(null != list && list.size()>0){
			if(list.get(0) == null){
				return 0;
			}
			return list.get(0);
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DocNumOnlyWh selectDocNumOnlyWh(String workflowId,
			String itemId, Integer num, String status) {
		StringBuffer hql = new StringBuffer(" from DocNumOnlyWh t where 1=1 ");
		if(StringUtils.isNotBlank(workflowId)){
			hql.append(" and t.workflowid = :workflowid");
		}
		if(StringUtils.isNotBlank(itemId)){
			hql.append(" and t.itemid = :itemid");
		}
		if(null != num){
			hql.append(" and t.wh = :wh ");
		}
		if(StringUtils.isNotBlank(status) && status.equals("1")){
			hql.append(" and t.isUsed = 1 ");
		}
		Query query = getEm().createQuery(hql.toString());
		if(StringUtils.isNotBlank(workflowId)){
			query.setParameter("workflowid", workflowId);
		}
		if(StringUtils.isNotBlank(itemId)){
			query.setParameter("itemid", itemId);
		}
		if(null != num){
			query.setParameter("wh", num);
		}
		List<DocNumOnlyWh> list = query.getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
