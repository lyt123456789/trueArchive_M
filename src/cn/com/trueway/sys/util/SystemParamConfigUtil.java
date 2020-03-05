package cn.com.trueway.sys.util;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import cn.com.trueway.sys.pojo.Dic;

/**
 * 
 * 描述：系统参数工具类
 * 作者：刘钰冬
 * 创建时间：2016-8-15 上午10:58:37
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public final class SystemParamConfigUtil {


	private static Map<String, String> sysParamMap = null;

	
	/**
	 * 
	 * 描述：获取参数
	 * @param param
	 * @return String
	 * 作者:刘钰冬
	 * 创建时间:2016-8-12 下午6:01:24
	 */
	public synchronized static String getParamValueByParam(String param){
		if (sysParamMap == null) {
			initSmsParam();
		}
		String result = (String)sysParamMap.get(param.toUpperCase());
		return  result;
	}
	
	/**
	 * 
	 * 描述：初始化参数
	 * 作者:刘钰冬
	 * 创建时间:2016-8-12 下午6:01:38
	 */
	private static void initSmsParam() {
		List<Dic> list = XMLUtil.getAllDic();
		
		sysParamMap = new Hashtable<String, String>();
		for(int i=0;i<list.size();i++){
			String key = list.get(i).getDicKey();
			String value = list.get(i).getDicValue();
			sysParamMap.put(key.toUpperCase(),value.trim());
		}
		
	}
	
	/**
	 * 
	 * 描述：刷新内存
	 * 作者:刘钰冬
	 * 创建时间:2016-8-12 下午6:01:52
	 */
	public static void refreshSmsParam() {
		initSmsParam();
	}
	
}
