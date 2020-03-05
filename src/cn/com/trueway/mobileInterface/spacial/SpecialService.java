package cn.com.trueway.mobileInterface.spacial;

import cn.com.trueway.mobileInterface.spacial.bean.SpecialInfo;


public class SpecialService {

	private SpecialDao specialDao;

	public SpecialDao getSpecialDao() {
		return specialDao;
	}

	public void setSpecialDao(SpecialDao specialDao) {
		this.specialDao = specialDao;
	}

	public void addSpecialInfo(SpecialInfo info, String instanceId,
			String formId, String workFlowId, String processId, String zxbdw) {
		specialDao.addSpecialInfo( info, instanceId,formId, workFlowId, processId,zxbdw);
	}

	public void addProcess(String workFlowId, String instanceId, String itemId,
			String nodeId, String formId, String processId, String title,
			String empId) {
		specialDao.addProcess( workFlowId,  instanceId,  itemId, nodeId,  formId,  processId,  title, empId);
		
	}

	public String findUserName(String userId) {
		// TODO Auto-generated method stub
		return specialDao.findUserName(userId);
	}
	
	
}
