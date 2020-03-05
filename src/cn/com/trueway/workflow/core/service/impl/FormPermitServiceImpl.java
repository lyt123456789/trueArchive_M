package cn.com.trueway.workflow.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.dao.FormPermitDao;
import cn.com.trueway.workflow.core.dao.WorkflowBasicFlowDao;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfFormPermit;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.service.FormPermitService;
import cn.com.trueway.workflow.set.dao.DepartmentDao;
import cn.com.trueway.workflow.set.dao.EmployeeDao;
import cn.com.trueway.workflow.set.dao.GroupDao;
import cn.com.trueway.workflow.set.dao.ZwkjFormDao;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.pojo.InnerUserMapEmployee;

public class FormPermitServiceImpl implements FormPermitService {

	private FormPermitDao formPermitDao;
	
	public FormPermitDao getFormPermitDao() {
		return formPermitDao;
	}

	public void setFormPermitDao(FormPermitDao formPermitDao) {
		this.formPermitDao = formPermitDao;
	}

	public boolean addFormPermit(List<WfFormPermit> list) {
		for(WfFormPermit permit : list){
			formPermitDao.addFormPermit(permit);
		}
		return true;
	}

	public boolean delFormPermit(WfFormPermit permit) {
		formPermitDao.delFormPermit(permit);
		return true;
	}
	
	public List<WfFormPermit> getPermitByFormId( String vc_formid){
		return formPermitDao.getPermitByFormId(vc_formid);
	}
	
	public List<WfFormPermit> getPermitByTagName( String vc_formid, String vc_tagname){
		return formPermitDao.getPermitByTagName(vc_formid, vc_tagname);
	}
	
	public List<WfFormPermit> getPermitByParam(WfFormPermit permit){
		return formPermitDao.getPermitByParam(permit);
	}
	
	public List<WfFormPermit> getPermitByParam2(WfFormPermit permit){
		return formPermitDao.getPermitByParam2(permit);
	}
	
	private WorkflowBasicFlowDao workflowBasicFlowDao;
	private ZwkjFormDao zwkjFormDao;
	private GroupDao groupDao;
	private DepartmentDao departmentDao;
	private EmployeeDao employeeDao;
	
	public WorkflowBasicFlowDao getWorkflowBasicFlowDao() {
		return workflowBasicFlowDao;
	}

	public void setWorkflowBasicFlowDao(WorkflowBasicFlowDao workflowBasicFlowDao) {
		this.workflowBasicFlowDao = workflowBasicFlowDao;
	}

	public ZwkjFormDao getZwkjFormDao() {
		return zwkjFormDao;
	}

	public void setZwkjFormDao(ZwkjFormDao zwkjFormDao) {
		this.zwkjFormDao = zwkjFormDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public String getAllDepartment(String depid, String result){
		List<Department> list = departmentDao.queryDepartmentsBydepIds("'"+depid+"'");
		for(Department dep : list){
			result += dep.getDepartmentGuid()+",";
			result = getAllDepartment(dep.getSuperiorGuid(), result);
		}
		return result;
	}
	
	public Map<String, String> getEmployeeLimit(String vc_employeeid, String deptId,String vc_nodeid, String workflowid){
		Map<String, String> tagMap = new HashMap<String, String>();
		//取得流程节点，表单ID,表单标签，用户对象
		WfNode node = workflowBasicFlowDao.getWfNode(vc_nodeid);
		String vc_formid = node.getWfn_defaultform();
		List<FormTagMapColumn> list=zwkjFormDao.getFormTagMapColumnByFormId(vc_formid);
		Employee emp = employeeDao.queryEmployeeById(vc_employeeid);
		String depId = deptId;
		if(("").equals(depId) || depId == null || ("null").equals(depId)){
			depId =	emp.getDepartmentGuid();
		}
		//递归获得该用户在部门和所有父部门
		String dep = getAllDepartment(depId, "");
		if(dep.length() > 0){
			dep = dep.substring(0, dep.length() - 1);
		}
		dep = dep.replace(",", "','");
		
		//获得该用户所包含的所有角色
		List<InnerUserMapEmployee> userList = groupDao.getListByEmployeeId(vc_employeeid, workflowid);
		//组织查询权限查询sql条件   vc_roletype和vc_typeid。类型1到5表示角色，6表示部门，7表示个人
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		InnerUser user = null;
		for(int i = 0; userList != null && i < userList.size(); i++){
			user = groupDao.getOneInnerUserById(userList.get(i).getInneruser_id());
			String userType = "";
			if(user != null){
				if(user.getType() == 4 && CommonUtil.stringIsNULL(user.getWorkflowId())){
					userType = "3"; 
				}else{
					userType = user.getType() + "";
				}
			}
			sb.append("( vc_roletype ='"+userType+"' and vc_roleid='"+user.getId()+"')");
			sb.append(" or ");
		}
		
		//人员基本权限
		sb.append("(vc_roletype='7' and vc_roleid='"+vc_employeeid+"')");
		sb.append(" or (vc_roletype='6' and vc_roleid in ('" + dep + "'))");
		sb.append(")");
		
		//查询该表单该节点的权限
		String formPermitSql = "select max(vc_limit), '1' from t_wf_core_formpermit where vc_formid='" + vc_formid
			+ "' and vc_tagname is null and vc_missionid='" + node.getWfn_id() + "' and "
			+ sb.toString();
		
		List<Object[]> formPermitList = formPermitDao.queryBySql(formPermitSql);
		String vc_limit = "";
		if(formPermitList != null && formPermitList.size() > 0){
			vc_limit = (String)formPermitList.get(0)[0];
			if(!CommonUtil.stringNotNULL(vc_limit)){
				vc_limit = "0";
			}
		}

		//统一查询角色步骤权限(设定为只读)
		String tagPermitSql = null;
		String vc_tagLimit = "";
		String tagName = "";
		for(FormTagMapColumn mapColumn : list){
			tagName += "'"+mapColumn.getFormtagname()+"',";
		}
		if(tagName!=null && !tagName.equals("")){
			tagName = tagName.substring(0, tagName.length()-1);
		}
		List<Object[]> data = null;
		//String sql = "";
		if(tagName!=null && !tagName.equals("")){
			tagPermitSql = "select max(vc_limit), vc_tagname  from t_wf_core_formpermit where vc_formid='" + vc_formid
			+ "' and vc_tagname in ("+tagName+") and vc_missionid='" + node.getWfn_id() + "' and "
			+ sb.toString()+" group by vc_tagname";
			data = formPermitDao.queryBySql(tagPermitSql);
		}
		for(FormTagMapColumn mapColumn : list){
			String tagname = mapColumn.getFormtagname();
			boolean isExist = false;
			for(Object[] dat: data){
				String name = dat[1].toString();
				if(name!=null && name.equals(tagname)){
					vc_tagLimit = dat[0]!=null?dat[0].toString():"";
					tagMap.put(mapColumn.getFormtagname(), vc_tagLimit+","+mapColumn.getFormtagtype());
					isExist = true;
					break;
				}
			}
			if(!isExist){
				tagMap.put(mapColumn.getFormtagname(), "1,"+mapColumn.getFormtagtype());			//默认设置为只读
			}
		}
		return tagMap;
	}
	

	public List<WfFormPermit> getPermitByFormId_new(String vc_formid,String nodeid,String type) {
		return formPermitDao.getPermitByFormId_new(vc_formid,nodeid,type);
	}

	public List<WfFormPermit> getPermitByParam_new(WfFormPermit permit) {
		return formPermitDao.getPermitByParam_new(permit);
	}

	@Override
	public Map<String, String> getLimitByNodeId(String nodeId, String formId,
			String workflowId) {
		if(nodeId==null || nodeId.equals("")){
			return formPermitDao.getLimitByEndNode(nodeId, formId, workflowId);
		}else{
			return formPermitDao.getLimitByNodeId(nodeId, formId, workflowId);
		}
		
	}

	@Override
	public String findSingleFormPermitByUserId(String workFlowId,
			String nodeId, String userId, String formtagname) {

		String permitValue = "1";
		boolean inner = formPermitDao.checkNodePermitByUserId(workFlowId, nodeId, userId);
		if(!inner){
			return permitValue;
		}
		WfFormPermit permit = formPermitDao.findFormFormPermit(workFlowId, nodeId,formtagname);
		//只获取过滤读写操作
		if(permit != null){
			permitValue =  permit.getVc_limit();
		}
		return permitValue;
	
	}

}
