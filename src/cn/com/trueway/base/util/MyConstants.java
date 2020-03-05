package cn.com.trueway.base.util;


/**
 * 常量类
 * @author panh
 * @time 2011-7-14 上午10:15:37
 *
 */
public class MyConstants {
	
	//数据库配置
	public static  String jdbc_driverClassName = null;
	public static  String jdbc_url = null;
	public static  String jdbc_username = null;
	public static  String jdbc_password = null;
	//点击选择工作流存入session_id
	public static String workflow_session_id = "workflow_defined_id";
	
	public static String loginEmployee="loginEmployee";
	
	public static String roleUser="roleUser";
	
	/**
	 * 对应Map<String, Object>中的key，deparmentIds--本user对应部门所对应的webId对应的几个部门Id
	 */
	public static String DEPARMENT_IDS = "deparmentIds";
	
	public static String DEPARMENT_ID = "deparmentId";
	/**
	 * 对应Map<String, Object>中的key，deparmentIds--本user对应部门所对应的webId对应的几个部门Name
	 */
	public static String DEPARMENT_NAME = "deparmentName";
	
	public static String bigDepartmentId="bigDepartmentId";
}
