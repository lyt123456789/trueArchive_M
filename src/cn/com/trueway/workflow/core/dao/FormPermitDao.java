package cn.com.trueway.workflow.core.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.WfFormPermit;

public interface FormPermitDao {

	WfFormPermit addFormPermit(WfFormPermit permit);
	
	void delFormPermit(WfFormPermit permit);
	
	List<WfFormPermit> getPermitByTagName( String vc_formid, String vc_tagname);
	
	List<WfFormPermit> getPermitByFormId( String vc_formid);
	
	List<WfFormPermit> getPermitByFormId_new( String vc_formid,String nodeid,String type);
	
	List<WfFormPermit> getPermitByParam(WfFormPermit permit);
	
	List<WfFormPermit> getPermitByParam_new(WfFormPermit permit);
	
	List<WfFormPermit> getPermitByParam2(WfFormPermit permit);
	
	List<Object[]> queryBySql(String sql);
	
	Map<String,String> getLimitByNodeId(String nodeId,String formId, String workflowId);
	
	Map<String,String> getLimitByEndNode(String nodeId,String formId, String workflowId);

	boolean checkNodePermitByUserId(String workFlowId, String nodeId,
			String userId);

	WfFormPermit findFormFormPermit(String workFlowId, String nodeId,
			String formtagname);
	
	List<WfFormPermit> findFormPermitListByLcId(String lcId);
	
	void saveFormPermit(WfFormPermit permit);
	
}
