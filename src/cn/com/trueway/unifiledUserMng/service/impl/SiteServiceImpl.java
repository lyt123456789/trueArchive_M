package cn.com.trueway.unifiledUserMng.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.unifiledUserMng.dao.SiteDao;
import cn.com.trueway.unifiledUserMng.entity.ReturnSite;
import cn.com.trueway.unifiledUserMng.entity.WbApp;
import cn.com.trueway.unifiledUserMng.service.SiteService;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.vo.Tree;
import cn.com.trueway.workflow.set.pojo.InnerUser;

public class SiteServiceImpl implements SiteService{

	private SiteDao siteDao;

	public SiteDao getSiteDao() {
		return siteDao;
	}

	public void setSiteDao(SiteDao siteDao) {
		this.siteDao = siteDao;
	}
	
	public int getCountByUserId(String userId,String depId){
		return siteDao.getCountByUserId(userId,depId);
	}
	
	/*public List<ReturnSite> getReturnSite(Map<String,String> map){
		List<ReturnSite> list = new ArrayList<ReturnSite>();
		List<ReturnSite> returnList = new ArrayList<ReturnSite>();
		//获取所有站点
		list = siteDao.getReturnSite(map);
		if(list!=null){
			for(int i=0;i<list.size();i++){
				ReturnSite returnSite = new ReturnSite();
				String siteId = list.get(i).getId();
				returnSite.setId(siteId);
				returnSite.setName(list.get(i).getName());
				//根据站点获取各管理员
				 List<RoleUser> list1 = siteDao.getRoleUser(siteId,"1");
				 List<RoleUser> list2 = siteDao.getRoleUser(siteId,"2");
				 List<RoleUser> list3 = siteDao.getRoleUser(siteId,"3");
				 List<RoleUser> list4 = siteDao.getRoleUser(siteId,"4");
				 if(list1!=null&&list1.size()>0){
					 String namestr = "";
					 String guidstr = "";
					 for(int j=0;j<list1.size();j++){
						 namestr += list1.get(j).getUserName()+";";
						 guidstr += list1.get(j).getUserId()+";";
					 }
					 returnSite.setSiteAdminName(namestr);
					 returnSite.setSiteAdminGuid(guidstr);
				 }
				 if(list2!=null&&list2.size()>0){
					 String namestr = "";
					 String guidstr = "";
					 for(int j=0;j<list2.size();j++){
						 namestr += list2.get(j).getUserName()+";";
						 guidstr += list2.get(j).getUserId()+";";
					 }
					 returnSite.setDepAdminName(namestr);
					 returnSite.setDepAdminGuid(guidstr);
				 }
				 if(list3!=null&&list3.size()>0){
					 String namestr = "";
					 String guidstr = "";
					 for(int j=0;j<list3.size();j++){
						 namestr += list3.get(j).getUserName()+";";
						 guidstr += list3.get(j).getUserId()+";";
					 }
					 returnSite.setEmpAdminName(namestr);
					 returnSite.setEmpAdminGuid(guidstr);
				 }
				 if(list4!=null&&list4.size()>0){
					 String namestr = "";
					 String guidstr = "";
					 for(int j=0;j<list4.size();j++){
						 namestr += list4.get(j).getUserName()+";";
						 guidstr += list4.get(j).getUserId()+";";
					 }
					 returnSite.setRoleAdminName(namestr);
					 returnSite.setRoleAdminGuid(guidstr);
				 }
				 returnList.add(returnSite);
			}
		}
		
		return returnList;
	}*/
	
	public List<ReturnSite> getFatherDep(){
		return siteDao.getFatherDep();
	}
	
	public String getMaxSeq(){
		return siteDao.getMaxSeq();
	}
	
	/*public ReturnSite getReturnSiteById(String id){
		ReturnSite returnSite = siteDao.getReturnSiteById(id);
		//根据站点获取各管理员
		 List<RoleUser> list1 = siteDao.getRoleUser(id,"1");
		 List<RoleUser> list2 = siteDao.getRoleUser(id,"2");
		 List<RoleUser> list3 = siteDao.getRoleUser(id,"3");
		 List<RoleUser> list4 = siteDao.getRoleUser(id,"4");
		 if(list1!=null&&list1.size()>0){
			 String namestr = "";
			 String guidstr = "";
			 for(int j=0;j<list1.size();j++){
				 namestr += list1.get(j).getUserName()+";";
				 guidstr += list1.get(j).getUserId()+";";
			 }
			 returnSite.setSiteAdminName(namestr);
			 returnSite.setSiteAdminGuid(guidstr);
		 }
		 if(list2!=null&&list2.size()>0){
			 String namestr = "";
			 String guidstr = "";
			 for(int j=0;j<list2.size();j++){
				 namestr += list2.get(j).getUserName()+";";
				 guidstr += list2.get(j).getUserId()+";";
			 }
			 returnSite.setDepAdminName(namestr);
			 returnSite.setDepAdminGuid(guidstr);
		 }
		 if(list3!=null&&list3.size()>0){
			 String namestr = "";
			 String guidstr = "";
			 for(int j=0;j<list3.size();j++){
				 namestr += list3.get(j).getUserName()+";";
				 guidstr += list3.get(j).getUserId()+";";
			 }
			 returnSite.setEmpAdminName(namestr);
			 returnSite.setEmpAdminGuid(guidstr);
		 }
		 if(list4!=null&&list4.size()>0){
			 String namestr = "";
			 String guidstr = "";
			 for(int j=0;j<list4.size();j++){
				 namestr += list4.get(j).getUserName()+";";
				 guidstr += list4.get(j).getUserId()+";";
			 }
			 returnSite.setRoleAdminName(namestr);
			 returnSite.setRoleAdminGuid(guidstr);
		 }
		return returnSite;
	}*/
	
	public List<ReturnSite> getDepBySiteId(Map<String,String> map){
		return siteDao.getDepBySiteId(map);
	}
	
	public List<ReturnSite> getAllSite(){
		return siteDao.getAllSite();
	}
	
	public List<ReturnSite> getDepByPid(String pid){
		return siteDao.getDepByPid(pid);
	}
	
	public String getSiteByUserId(String userId,String depId){
		return siteDao.getSiteByUserId(userId,depId);
	}
	
	public ReturnSite getReturnSite(String id){
		return siteDao.getReturnSiteById(id);
	}
	
	public String getdepNameById(String id){
		return siteDao.getdepNameById(id);
	}
	
	public int getCount(String depId){
		return siteDao.getCount(depId);
	}
	
	public DTPageBean  getAllEmp(int count, int selectIndex, int pageSize,Map<String,String> map){
		return siteDao.getAllEmp(count,selectIndex,pageSize,map);
	}
	
	public int getAllEmpCount(Map<String,String> map){
		return siteDao.getAllEmpCount(map);
	}
	
	public String getJonSiteName(String id){
		return siteDao.getJonSiteName(id); 
	}
	
	public String getFristDepId(String siteId){
		String depId = siteId;
		int count=0;
		//判断该部门下是否存在人，无人继续查询下一层
		while(count<2){
			List<String> list = siteDao.getFristDepId(depId);
			if(list !=null){
				if(list.size()>1){
					depId=list.get(1);
					count = siteDao.getCount(depId);
				}else{
					depId=list.get(0);//最底层
					break;
				}
			}else{
				break;
			}
		}
		return depId;
	}
	
	public Map<String,String> getRoleId(String userId,String depId){
		return siteDao.getRoleId(userId, depId);
	}

	@Override
	public List<WbApp> getPortalRSList(Map<String, String> map) {
		return siteDao.getPortalRSList(map);
	}
	
	@Override
	public List<WbApp> getOARSList(Map<String, String> map) {
		return siteDao.getOARSList(map);
	}

	@Override
	public List<WfNode> checkProcessRole(String roleId) {
		return siteDao.checkProcessRole(roleId);
	}

	@Override
	public InnerUser getRole(String roleId) {
		
		return siteDao.getRole(roleId);
	}

	@Override
	public String getMaxEmpSeq() {
		
		return siteDao.getMaxEmpSeq();
	}

	@Override
	public Tree getDeptById(String deptId) {
		return siteDao.getDeptById(deptId);
	}

	@Override
	public List<Tree> findTree(Tree tree) {
		return orgTree(tree);
	}

	/**
	 * 方法描述: [递归获取部门树]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2019-2-28-下午2:50:59<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param tree
	 * @return
	 * List<Tree>
	 */
	private List<Tree> orgTree(Tree deptFather) {
		List<Tree> trees = new ArrayList<Tree>();
    	List<Tree> deptSon = null;
    	if(deptFather == null){
    		deptSon = siteDao.findDeptAllByPidNull();
    	}else{
    		deptSon = siteDao.findDeptAllByPid(deptFather.getId());
    	}
    	if (deptSon != null) {
    		for (Tree dept : deptSon) {
    			Tree tree = new Tree();
    			tree.setId(dept.getId());
    			tree.setText(dept.getText());
    			tree.setChildren(orgTree(dept));
    			trees.add(tree);
    		}
    	}
    	return trees;
	}
	
}
