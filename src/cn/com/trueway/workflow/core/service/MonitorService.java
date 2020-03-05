package cn.com.trueway.workflow.core.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.pojo.MonitorInfoBean;
import cn.com.trueway.workflow.core.pojo.WfInterfaceCheck;

public interface MonitorService {

	/**
	 * 
	 * 描述：新增监控信息
	 * @param info void
	 *
	 * 作者:Yuxl
	 * 创建时间:2018-5-7 下午2:03:31
	 */
	public void addMonitorInfo(MonitorInfoBean info);
	
	/**
	 * 
	 * 描述：根据参数获取监控信息
	 * @param params
	 * @return MonitorInfoBean
	 *
	 * 作者:Yuxl
	 * 创建时间:2018-5-7 下午2:03:54
	 */
	public List<MonitorInfoBean> getMonitorInfoByParam(Map<String,Object> params);
	
	/**
	 * 描述：根据日期获取cpu占比
	 * @param date
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2018-5-7 下午2:16:07
	 */
	public List<String> getDayCpuInfo(String date);
	
	/**
	 * 描述：根据日期获取内存信息
	 * @param date
	 * @return List<String>
	 *
	 * 作者:Yuxl
	 * 创建时间:2018-5-7 下午2:16:46
	 */
	public List<String> getDayMemoryInfo(String date);
	
	public List<WfInterfaceCheck> getInterfaceList(Map<String, String> map);
	
	public void updateInterfaceResult(String id, String result);
	
	public List<Object[]> getDfStatisticBySite(Map<String, String> map);
	
	public List<Object[]> getDfStaByItemType(Map<String, String> map);
	
	public List<Object[]> getActDeptList(Map<String, String> map);
}
