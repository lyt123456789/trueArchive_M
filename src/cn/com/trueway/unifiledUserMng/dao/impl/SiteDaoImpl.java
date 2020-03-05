package cn.com.trueway.unifiledUserMng.dao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.oa.info.SystemParamConfigUtil;
import cn.com.trueway.unifiledUserMng.dao.SiteDao;
import cn.com.trueway.unifiledUserMng.entity.ReturnEmp;
import cn.com.trueway.unifiledUserMng.entity.ReturnSite;
import cn.com.trueway.unifiledUserMng.entity.RoleUser;
import cn.com.trueway.unifiledUserMng.entity.WbApp;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.vo.Tree;
import cn.com.trueway.workflow.set.pojo.InnerUser;

public class SiteDaoImpl extends BaseDao implements SiteDao{
	
	private static String IDSURL = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
	
	private static String IDSDEPTENTITY = "id, name, pid, address, code, icon, seq, createDate, area_id, siteid, issite,sitename,siteids";

	public int getCountByUserId(String userId,String depId){
		String sql = " select count(*) from t_sys_role_user where user_id = '"+userId+"' and dept_id= '"+depId+"' and role_id in('1','2','3','4')";
		Query query =getEm().createNativeQuery(sql.toString());
		int i = ((BigDecimal)query.getSingleResult()).intValue();
		return i;
	}
	
	public List<ReturnSite> getReturnSite(Map<String,String> map){
		String userName = map.get("userName");
		String siteName = map.get("siteName");
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		//ids中获取所有的站点信息
		String sql = " select id,name from "+idsOrcl+".ids_dept where 1=1 and issite = 1";
		if(siteName != null && !"".equals(siteName)){
			sql +=" and name like '%"+siteName+"%'";
		}
		if(userName != null && !"".equals(userName)){
			sql +=" and  siteid in"
					+" (select t.siteid from "+idsOrcl+".ids_user t where t.name = '"+userName+"'"
					+" union all"
					+" select distinct(siteid) from  "+idsOrcl+".ids_user where dept_id = "
					+" (select jobcode from "+idsOrcl+".ids_user_ptjob where user_id in"
					+" (select t.id from "+idsOrcl+".ids_user t where t.name = '"+userName+"')))";
		}
		sql +=" order by seq";
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		List<ReturnSite> returnSiteList = new ArrayList<ReturnSite>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				ReturnSite returnSite = new ReturnSite();
				returnSite.setId(getSqlVal(((Object[]) list.get(i))[0]));
				returnSite.setName(getSqlVal(((Object[]) list.get(i))[1]));
				returnSiteList.add(returnSite);
			}
		} else {
			returnSiteList = null;
		}
		return returnSiteList;
	}
	
	
	public String getSqlVal(Object obj) {
		String val = "";
		if (obj != null) {
			val = obj.toString();
		}
		return val;
	}
	
	@SuppressWarnings("unchecked")
	public List<RoleUser> getRoleUser(String siteId,String role_id){
		String sql = "select * from T_SYS_ROLE_USER where role_Id = '"+role_id+"' and dept_Id in(select distinct(department_guid) from zwkj_employee where siteid = '"+siteId+"')";
		Query query = getEm().createNativeQuery(sql,RoleUser.class);
		List<RoleUser> rr=(List<RoleUser>)query.getResultList();
		if (rr != null && rr.size() > 0) 
			 return rr;
			 else return null;
	}
	
	public List<ReturnSite> getFatherDep(){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select id,name,seq from ( select id,name,seq from "+idsOrcl+".ids_dept where pid is null union";
		sql += " select id,name,seq from "+idsOrcl+".ids_dept where pid in(select dept.id from "+idsOrcl+".ids_dept dept where dept.pid is null)) order by seq";
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		List<ReturnSite> returnSiteList = new ArrayList<ReturnSite>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				ReturnSite returnSite = new ReturnSite();
				returnSite.setId(getSqlVal(((Object[]) list.get(i))[0]));
				returnSite.setName(getSqlVal(((Object[]) list.get(i))[1]));
				returnSiteList.add(returnSite);
			}
		} else {
			returnSiteList =  null;
		}
		return returnSiteList;
	}
	
	public String getMaxSeq(){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select max(seq) from "+idsOrcl+".ids_dept";
		Query query =getEm().createNativeQuery(sql.toString());
		String seq = query.getSingleResult().toString();
		return seq;
	}
	
	public ReturnSite getReturnSiteById(String id){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select id,name,seq,pid,issite,siteId from "+idsOrcl+".ids_dept where id='"+id+"'";
		ReturnSite returnSite = new ReturnSite();
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		if (list.size() > 0) {
			returnSite.setId(getSqlVal(((Object[]) list.get(0))[0]));
			returnSite.setName(getSqlVal(((Object[]) list.get(0))[1]));
			returnSite.setSeq(getSqlVal(((Object[]) list.get(0))[2]));
			returnSite.setFatherDep(getSqlVal(((Object[]) list.get(0))[3]));
			returnSite.setIssite(getSqlVal(((Object[]) list.get(0))[4]));
			returnSite.setSiteId(getSqlVal(((Object[]) list.get(0))[5]));
		} else {
			returnSite =  null;
		}
		return returnSite;
	}
	
	public List<ReturnSite> getDepBySiteId(Map<String,String> map){
		String siteId = map.get("siteId");
		String name = map.get("name");
		String seq = map.get("seq");
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select t.id,t.name,t.seq,t.pid,t.code,t.issite, t.siteid from "+idsOrcl+".ids_dept t where t.siteId='"+siteId+"' ";
		if((name ==null || "".equals(name) ) && (seq == null || "".equals(seq))){
			sql +=" and (t.issite = '1' or t.siteId = '"+siteId+"') ";
		}
		if(name != null && !"".equals(name)){
			sql += " and t.name like '%"+name+"%'";
		}
		if(seq != null && !"".equals(seq)){
			sql += " and t.seq like '%"+seq+"%' ";
		}
				sql +=" order by t.issite desc, t.seq asc";
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		List<ReturnSite> returnSiteList = new ArrayList<ReturnSite>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				ReturnSite returnSite = new ReturnSite();
				returnSite.setId(getSqlVal(((Object[]) list.get(i))[0]));
				returnSite.setName(getSqlVal(((Object[]) list.get(i))[1]));
				returnSite.setSeq(getSqlVal(((Object[]) list.get(i))[2]));
				returnSite.setFatherDep(getSqlVal(((Object[]) list.get(i))[3]));
				returnSite.setCode(getSqlVal(((Object[]) list.get(i))[4]));
				returnSite.setIssite(getSqlVal(((Object[]) list.get(i))[5]));
				returnSite.setSiteId(getSqlVal(((Object[]) list.get(i))[6]));
				returnSiteList.add(returnSite);
			}
		} else {
			returnSiteList =  null;
		}
		return returnSiteList;
		
	}
	
	public List<ReturnSite> getAllSite(){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select id,name,seq,pid from "+idsOrcl+".ids_dept where issite= '1' order by seq";
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		List<ReturnSite> returnSiteList = new ArrayList<ReturnSite>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				ReturnSite returnSite = new ReturnSite();
				returnSite.setId(getSqlVal(((Object[]) list.get(i))[0]));
				returnSite.setName(getSqlVal(((Object[]) list.get(i))[1]));
				returnSite.setSeq(getSqlVal(((Object[]) list.get(i))[2]));
				returnSite.setFatherDep(getSqlVal(((Object[]) list.get(i))[3]));
				returnSiteList.add(returnSite);
			}
		} else {
			returnSiteList =  null;
		}
		return returnSiteList;
	}
	
	public List<ReturnSite> getDepByPid(String pid){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select id,name,seq,pid from "+idsOrcl+".ids_dept where pid= '"+pid+"' order by seq";
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		List<ReturnSite> returnSiteList = new ArrayList<ReturnSite>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				ReturnSite returnSite = new ReturnSite();
				returnSite.setId(getSqlVal(((Object[]) list.get(i))[0]));
				returnSite.setName(getSqlVal(((Object[]) list.get(i))[1]));
				returnSite.setSeq(getSqlVal(((Object[]) list.get(i))[2]));
				returnSite.setFatherDep(getSqlVal(((Object[]) list.get(i))[3]));
				returnSiteList.add(returnSite);
			}
		} else {
			returnSiteList =  null;
		}
		return returnSiteList;
	}
	
	public String getSiteByUserId(String userId,String depId){
		String sql=" select distinct(siteid) from zwkj_employee where department_guid =(select distinct dept_id from t_sys_role_user where user_id = '"+userId+"'"
				+" and dept_id = '"+depId+"' and role_id = '2')";
		Query query =getEm().createNativeQuery(sql.toString());
		List<String> list = query.getResultList();
		String siteId = "";
		if(list != null && list.size() > 0){
			siteId = list.get(0).toString();
		}
		return siteId;
	}

	@Override
	public String getdepNameById(String id) {
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select name from "+idsOrcl+".ids_dept where id= '"+id+"'";
		Query query =getEm().createNativeQuery(sql);
		String pname = query.getSingleResult().toString();
		return pname;
	}
	
	public int getCount(String depId){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select count(*) from "+idsOrcl+".ids_user where dept_id = '"+depId+"'";
		Query query =getEm().createNativeQuery(sql.toString());
		String count = query.getSingleResult().toString();
		if(count != null && !"".equals(count)){
			return Integer.valueOf(count);
		}else{
			return 0;
		}
	}
	
	public int getAllEmpCount(Map<String,String> map){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String depId = map.get("depId");
		String siteId = map.get("siteId");
		String name = map.get("name");
		String beginTime = map.get("beginTime");
		String endTime = map.get("endTime");
		String sql="select count(1) " + 
				"  from "+idsOrcl+".ids_user t" + 
				" where 1 = 1 and t.isleave!='1' ";
		if(depId !=null &&!"".equals(depId)){
			sql += " and (t.dept_id = '"+depId+"' or t.id in (select pj.user_id  from "+idsOrcl+".ids_user_ptjob pj where pj.jobcode like '%"+depId+"%' )) ";
//			sql += " and t.dept_id = '"+depId+"'";
		}
		if(siteId !=null &&!"".equals(siteId)){
//			sql += " and t.siteid = '"+siteId+"'";
			sql += " and (t.siteid = '"+siteId+"' or t.id in (select pj.user_id from "+idsOrcl+".ids_user_ptjob pj where pj.jobcode in (select d.id from "+idsOrcl+".ids_dept d where d.siteid = '"+siteId+"'))) ";
		}
		
		if(name !=null &&!"".equals(name)){
				sql += " and t.name like '%"+name+"%'  ";
		}
		
		String count =getEm().createNativeQuery(sql).getSingleResult().toString();
		return Integer.parseInt(count);
	}
	
	public DTPageBean  getAllEmp(int count, int selectIndex, int pageSize,Map<String,String> map){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String depId = map.get("depId");
		String name = map.get("name");
		String siteId = map.get("siteId");
		String beginTime = map.get("beginTime");
		String endTime = map.get("endTime");
		
		String sql="select t.id," + 
				"       t.loginname," + 
				"       t.name," + 
				"       t.password," + 
				"       t.sex," + 
				"       t.age," + 
				"       t.dept_id," + 
				"       t.createdate," + 
				"       t.status," + 
				"       t.phone," + 
				"       t.tel," + 
				"       t.seq," + 
				"       t.usertype," + 
				"       (select wm_concat(b.JOBCODE)" + 
				"          from "+idsOrcl+ ".ids_user_ptjob b" + 
				"         where t.id = b.user_id) as jobcode," + 
				"       t.jobtitle," + 
				"       t.fax," + 
				"       t.dn," + 
				"       t.shortphonenum," + 
				"       t.dutytitle," + 
				"       t.jobseq," + 
				"       t.isleave," + 
				"       t.group_id," + 
				"       t.nickname," + 
				"       t.siteid," + 
				"       t.email," + 
				"       t.ssnum," + 
				"       t.sfznum," + 
				"       t.smknum," + 
				"       t.position," + 
				"       t.staffids," + 
				"       (select wm_concat(d.name)" + 
				"          from "+idsOrcl+".ids_user_ptjob b, "+idsOrcl+".ids_dept d" + 
				"         where t.id = b.user_id" + 
				"           and b.JOBCODE = d.id) as jobName," +
				"(select  wm_concat(r.name)   from "+idsOrcl+".ids_role r , "+idsOrcl+".ids_user_role u where r.id = u.role_id and u.user_id = t.id ) as roleName "+
				"  from "+idsOrcl+".ids_user t" + 
				" where 1 = 1";
			
		if(depId !=null &&!"".equals(depId)){
			sql += " and (t.dept_id = '"+depId+"' or t.id in (select pj.user_id  from "+idsOrcl+".ids_user_ptjob pj where pj.jobcode like '%"+depId+"%' )) ";
//			sql += " and t.dept_id = '"+depId+"'";
		}
		if(siteId !=null &&!"".equals(siteId)){
//			sql += " and t.siteid = '"+siteId+"'";
			sql += " and (t.siteid = '"+siteId+"' or t.id in (select pj.user_id from "+idsOrcl+".ids_user_ptjob pj where pj.jobcode in (select d.id from "+idsOrcl+".ids_dept d where d.siteid = '"+siteId+"'))) ";
		}
		if(name !=null &&!"".equals(name)){
			sql += " and t.name like '%"+name+"%'  ";
		}
		sql+= " and t.isleave!='1' order by t.seq";
		DTPageBean pagingQuery = pagingQuery(count, sql.toString(),selectIndex, pageSize, ReturnEmp.class);
		return pagingQuery;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DTPageBean pagingQuery(int count, String querySql, int currentPage,
			int numPerPage, Class resultClass) {
		// 开始记录的位置
		int startIndex = (currentPage - 1) * numPerPage;
		// 总页数
		int totalPages = 0;
		if (count % numPerPage == 0) {
			totalPages = count / numPerPage;
		} else {
			totalPages = count / numPerPage + 1;
		}
		// 最后一条记录的位置
		int lastIndex = 0;
		if (count < numPerPage) {
			lastIndex = count;
		} else if ((currentPage == totalPages && count % numPerPage == 0)
				|| currentPage < totalPages) {
			lastIndex = currentPage * numPerPage;
		} else if ((currentPage == totalPages && count % numPerPage != 0)) {
			lastIndex = count;
		}
		// 分页sql
		StringBuffer pagingSql = new StringBuffer();
		pagingSql
				.append("select * from ( select my_table.*,rownum as my_rownum from ( ");
		pagingSql.append(querySql);
		pagingSql.append(" ) my_table where rownum <= ").append(lastIndex);
		pagingSql.append(") where my_rownum > ").append(startIndex);
		// 根据分页sql得到结果集
		List dataList = this.getEm().createNativeQuery(pagingSql.toString()).getResultList();
		// 构造返回对象 DTPageBean
		DTPageBean dtPageBean = new DTPageBean();
		dtPageBean.setDataList(dataList);
		dtPageBean.setNumPerPage(numPerPage);
		dtPageBean.setTotalRows(count);
		dtPageBean.setTotalPages(totalPages);
		dtPageBean.setCurrentPage(currentPage);
		return dtPageBean;
	}
	
	public String getJonSiteName(String id){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select name  from "+idsOrcl+".ids_dept where id = (select distinct(siteId) from "+idsOrcl+".ids_user where dept_id = '"+id+"')";
		Query query =getEm().createNativeQuery(sql);
		String name = query.getSingleResult().toString();
		return name;
	}
	
	public List<String> getFristDepId(String id){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select id,rownum from (select id,rownum as n from (select t.id from "+idsOrcl+".ids_dept t start with id = '"+id+"' connect by prior id = pid  order by seq asc)  e)";
		Query query =getEm().createNativeQuery(sql);
		List list = query.getResultList();
		List<String> returnSiteList = new ArrayList<String>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				String depId = getSqlVal(((Object[]) list.get(i))[0]);
				returnSiteList.add(depId);
			}
		} else {
			returnSiteList =  null;
		}
		return returnSiteList;
	}
	
	public Map<String,String> getRoleId(String userId,String depId){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select role_id,id from "+idsOrcl+".ids_user_role where user_id = '"+userId+"' and dept_id = '"+depId+"'";
		Query query =getEm().createNativeQuery(sql);
		List list = query.getResultList();
		String roleId = "";
		String roleName="";
		String str = "'";
		Map<String,String>  map = new HashMap<String,String>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				roleId += getSqlVal(((Object[]) list.get(i))[0])+";";
				if(i<(list.size()-1)){
					str +=getSqlVal(((Object[]) list.get(i))[0])+"','";
				}else{
					str +=getSqlVal(((Object[]) list.get(i))[0])+"'";
				}
			}
			String hql = " select name from "+idsOrcl+".ids_role where id in("+str+")";
			Query queryName =getEm().createNativeQuery(sql);
			List namelist = query.getResultList();
			if (namelist.size() > 0) {
				for(int i=0;i<namelist.size();i++){
					roleName  += getSqlVal(((Object[]) list.get(i))[0])+";";
				}
			}
		} 
		map.put("roleId", roleId);
		map.put("roleName", roleName);
		return map;
	}

	@Override
	public List<WbApp> getPortalRSList(Map<String, String> map) {
		String siteId = map.get("siteId");
		String portalOrcl = SystemParamConfigUtil.getParamValueByParam("portalOrcl");
		String sql = " select ID,NAME,RIGHT_INDEX, FATHER from "+portalOrcl+".t_sys_menu where 1=1 ";
		if(StringUtils.isNotBlank(siteId)){
			sql += " and  (SITE_ID = '"+siteId+"' or  SITE_ID = '1')  and (flag != '1' or flag is null)";
		}
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		List<WbApp> resourceList = new ArrayList<WbApp>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				WbApp wbApp = new WbApp();
				wbApp.setId(getSqlVal(((Object[]) list.get(i))[0]));
				wbApp.setName(getSqlVal(((Object[]) list.get(i))[1]));
				wbApp.setSeq(getSqlVal(((Object[]) list.get(i))[2]));
				wbApp.setpId(getSqlVal(((Object[]) list.get(i))[3]));
				wbApp.setResourceType("1");
				resourceList.add(wbApp);
			}
		} 
		return resourceList;
	}
	
	@Override
	public List<WbApp> getOARSList(Map<String, String> map) {
		String siteId = map.get("siteId");
		String oaOrcl = SystemParamConfigUtil.getParamValueByParam("oaOrcl");
		String sql = " select MENU_ID,MENU_NAME,MENU_SORT, MENU_FATER_ID from "+oaOrcl+".t_sys_menu where 1=1 ";
		if(StringUtils.isNotBlank(siteId)){
			//flag是否面向用户
			sql += " and  (SITE_ID = '"+siteId+"' or  SITE_ID IS NULL) and (flag != '1' or flag is null)";
		}
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		List<WbApp> resourceList = new ArrayList<WbApp>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				WbApp wbApp = new WbApp();
				wbApp.setId(getSqlVal(((Object[]) list.get(i))[0]));
				wbApp.setName(getSqlVal(((Object[]) list.get(i))[1]));
				wbApp.setSeq(getSqlVal(((Object[]) list.get(i))[2]));
				wbApp.setpId(getSqlVal(((Object[]) list.get(i))[3]));
				wbApp.setResourceType("2");
				resourceList.add(wbApp);
			}
		}
		return resourceList;
	}

	@Override
	public List<WfNode> checkProcessRole(String roleId) {
		String hql = "from WfNode where wfn_staff ='"+roleId+"'";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public InnerUser getRole(String roleId) {
		String hql = "from InnerUser where id ='"+roleId+"'";
		List<InnerUser> list = getEm().createQuery(hql).getResultList();
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	public String getMaxEmpSeq(){
		String idsOrcl = SystemParamConfigUtil.getParamValueByParam("idsOrcl");
		String sql = " select nvl(max(seq),0) from "+idsOrcl+".ids_user ";
		Query query =getEm().createNativeQuery(sql.toString());
		String seq = query.getSingleResult().toString();
		return seq;
	}

	@Override
	public Tree getDeptById(String deptId) {
		String sql = " select id,name from "+IDSURL+".ids_dept where ids_dept.id='"+deptId+"'";
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		if (list.size() > 0) {
			Tree tree = new Tree();
			tree.setId(getSqlVal(((Object[]) list.get(0))[0]));
			tree.setText(getSqlVal(((Object[]) list.get(0))[1]));
			return tree;
		}
		return null;
	}

	@Override
	public List<Tree> findDeptAllByPidNull() {
		String sql = " select distinct "+IDSDEPTENTITY+" from "+IDSURL+".ids_dept dp where dp.pid is null and (dp.ISSITE=0 or dp.ISSITE is null) order by dp.seq ";
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		List<Tree> listResult = new ArrayList<Tree>();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Tree tree = new Tree();
				tree.setId(getSqlVal(((Object[]) list.get(i))[0]));
				tree.setText(getSqlVal(((Object[]) list.get(i))[1]));
				tree.setPid(getSqlVal(((Object[]) list.get(i))[2]));
				listResult.add(tree);
			}
		}
		return listResult;
	}

	@Override
	public List<Tree> findDeptAllByPid(String id) {
		String sql = " select distinct "+IDSDEPTENTITY+" from "+IDSURL+".ids_dept dp where dp.pid ='"+id+"' order by dp.seq ";
		Query query =getEm().createNativeQuery(sql.toString());
		List list = query.getResultList();
		List<Tree> listResult = new ArrayList<Tree>();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Tree tree = new Tree();
				tree.setId(getSqlVal(((Object[]) list.get(i))[0]));
				tree.setText(getSqlVal(((Object[]) list.get(i))[1]));
				tree.setPid(getSqlVal(((Object[]) list.get(i))[2]));
				listResult.add(tree);
			}
		}
		return listResult;
	}
}
