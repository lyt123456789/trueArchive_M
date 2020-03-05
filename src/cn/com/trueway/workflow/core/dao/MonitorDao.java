package cn.com.trueway.workflow.core.dao;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.MonitorInfoBean;
import cn.com.trueway.workflow.core.pojo.WfInterfaceCheck;

public interface MonitorDao {

	void addMonitorInfo(MonitorInfoBean info);

	List<MonitorInfoBean> getMonitorInfoByParam(Map<String, Object> params);

	List<String> getDayCpuInfo(String date);

	List<String> getDayMemoryInfo(String date);

	public List<WfInterfaceCheck> getInterfaceList(Map<String, String> map);
	
	public void updateInterfaceResult(String id, String result);
	
	public List<Object[]> getDfStatisticBySite(Map<String, String> map);
	
	public List<Object[]> getDfStaByItemType(Map<String, String> map);
	
	public List<Object[]> getActDeptList(Map<String, String> map);
}
