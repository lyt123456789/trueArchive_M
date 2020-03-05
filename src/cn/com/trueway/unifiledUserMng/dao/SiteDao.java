package cn.com.trueway.unifiledUserMng.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.unifiledUserMng.entity.ReturnEmp;
import cn.com.trueway.unifiledUserMng.entity.ReturnSite;
import cn.com.trueway.unifiledUserMng.entity.RoleUser;
import cn.com.trueway.unifiledUserMng.entity.WbApp;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.vo.Tree;
import cn.com.trueway.workflow.set.pojo.InnerUser;

public interface SiteDao {

	int getCountByUserId(String userId,String depId);
	
	List<ReturnSite> getReturnSite(Map<String,String> map);
	
	List<RoleUser> getRoleUser(String siteId,String role_id);
	
	List<ReturnSite> getFatherDep();
	
	String getMaxSeq();
	
	ReturnSite getReturnSiteById(String id);
	
	List<ReturnSite> getDepBySiteId(Map<String,String> map);
	
	List<ReturnSite> getAllSite();
	
	List<ReturnSite> getDepByPid(String pid);
	
	String getSiteByUserId(String userId,String depId);
	
	String getdepNameById(String id);
	
	int getCount(String depId);
	
	DTPageBean  getAllEmp(int count, int selectIndex, int pageSize,Map<String,String> map);
	
	int getAllEmpCount(Map<String,String> map);
	
	String getJonSiteName(String id);
	
	List<String> getFristDepId(String siteId);
	
	Map<String, String> getRoleId(String userId,String depId); 
	
	List<WbApp> getPortalRSList(Map<String, String> map);
	
	List<WbApp> getOARSList(Map<String, String> map);
	
	List<WfNode> checkProcessRole(String roleId);
	
	InnerUser getRole(String roleId);
	
	String getMaxEmpSeq();

	Tree getDeptById(String deptId);

	List<Tree> findDeptAllByPidNull();

	List<Tree> findDeptAllByPid(String id);
}
