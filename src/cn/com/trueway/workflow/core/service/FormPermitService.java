package cn.com.trueway.workflow.core.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.WfFormPermit;

public interface FormPermitService {

	boolean addFormPermit(List<WfFormPermit> list);
	
	boolean delFormPermit(WfFormPermit permit);
	
	List<WfFormPermit> getPermitByFormId( String vc_formid);
	
	List<WfFormPermit> getPermitByFormId_new( String vc_formid,String nodeid,String type);
	
	List<WfFormPermit> getPermitByTagName( String vc_formid, String vc_tagname);
	
	List<WfFormPermit> getPermitByParam(WfFormPermit permit);
	
	List<WfFormPermit> getPermitByParam_new(WfFormPermit permit);
	
	List<WfFormPermit> getPermitByParam2(WfFormPermit permit);
	
	Map<String, String> getEmployeeLimit(String vc_employeeid,String deptId, String vc_nodeid, String workflowid);

	Map<String,String> getLimitByNodeId(String nodeId,String formId, String workflowId);

	String findSingleFormPermitByUserId(String workFlowId, String nodeid,
			String userId, String formtagname);
}
