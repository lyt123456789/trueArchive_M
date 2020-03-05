/**
 * 文件名称:ConstantUtil.java
 * 作者:陈静<br>
 * 邮箱:c_jing1984@163.com<br>
 * 创建时间：2014-3-5 上午11:41:28<br>
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.base.util;

/**
 * 
 * 描述：TODO 常量类<br>
 * 作者：陈静<br>
 * 邮箱:c_jing1984@163.com<br>
 * 创建时间：2014-3-5 上午11:41:28<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
public class ConstantUtil {

	/**
	 * 登录账号
	 */
	public final static String LOGIN_USER = "SysUser";

	/**
	 * 当前系统id
	 */
	public final static String SYS_MENU_ID = "sys_menu_id";
	
	public static final String MYSQL = "mysql";

	public static final String ORACLE = "oracle";

	// 用户状态1.启用，0，禁用
	// 禁用
	public static final String USER_STATUS_FORBIDDEN = "0";

	// 启用
	public static final String USER_STATUS_START = "1";

	// 已删除
	public static final String ISDELETE_YES = "0";

	// 未删除
	public static final String ISDELETE_NO = "1";

	// 字典表单位性质code
	public static final String DEPTNATURE = "dwxz";

	// 模板类型 专题页
	public static final String SPECIAL_FTL = "3";

	// 模板类型 列表页
	public static final String LIST_FTL = "2";

	// 模板类型 内容页
	public static final String CONTEXT_FTL = "1";

	// 参数类型 模型
	public static final String PARAM_TYPE_MODEL = "1";

	// 参数类型 查询
	public static final String PARAM_TYPE_SELECT = "2";

	// 参数类型 列表
	public static final String PARAM_TYPE_LIST = "3";

	// 参数类型 内容
	public static final String PARAM_TYPE_CONTENT = "4";
	
	// license验证是否通过标志位 0.内容为空，1.验证通过，2.超过有效期，3.验证不通过
	public static String LICENSE_CHECK_PASSED = "3";

	// 可以创建的站点个数
	public static int LICENSE_CHECK_NUMBER = 0;
	
	public static final String LOGIN_VISITOR = "VisitorUser";
	
	//单位编码
	public final static String DEPT_CODE="deptCode";
	//单位编码
	public final static String DEPT_NAME="deptName";
	//实体单位代码
	public final static String ST_DEPT_CODE="stDeptCode";
	//国库集中支付代码
	public final static String ZF_DEPT_CODE="zfDeptCode";
	//预算单位代码
	public final static String YS_DEPT_CODE="ysDeptCode";
	//工资统发代码
	public final static String GZ_DEPT_CODE="gzDeptCode";
	//非税执收代码
	public final static String FS_DEPT_CODE="fsDeptCode";
	//主管部门代码
	public final static String ZG_DEPT_CODE="zgDeptCode";
	//是否主管部门;默认为0,1为主管部门
	public final static String IS_ZG_DEPT="isZgDept";
	//员工代码
	public final static String YGDM="ygdm";
	//员工姓名
	public final static String YGXM="ygxm";
	//单位ID
	public final static String DEPT_ID="deptId";
	//业务年度
	public final static String YEAR="year";
}
