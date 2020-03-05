/**
 * 文件名称：Constant.java<br>
 * 作者：吴新星<br>
 * 创建时间：Feb 9, 2010 2:19:47 PM
 * CopyRight (c)2009-2011:江苏中威科技信息系统有限公司<br>
 */
package cn.com.trueway.base.util;

import java.util.Map;

import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.action.TableInfoAction;


/**
 * 描述：常数类，保存系统中用到的一些常量<br>
 * 作者：吴新星<br>
 * 创建时间：Feb 9, 2010 2:19:47 PM<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5
 */
public class Constant {

	/**
	 * 对应Map<String, Object>中的key，Step
	 */
	public static String STEP = "STEP";

	/**
	 * 对应Map<String, Object>中的key，Steps
	 */
	public static String STEPS = "STEPS";

	/**
	 * 对应Map<String, Object>中的key，Process
	 */
	public static String PROCESS = "PROCESS";

	/**
	 * 此Key对应 List<String> instanceIds
	 */
	public static String INSTANCEIDS = "INSTANCEIDS";
	
	/**
	 * 此Key对应 List<String> toUserIds
	 */
	public static String TO_USERIDS = "TO_USERIDS";
	
	public static String NEW_PROCESS="NEW_PROCESS";

	/**
	 * 此Key对应 List<String> processIds
	 */
	public static String PROCESSIDS = "PROCESSIDS";
	
	/**
	 * session的Map<String, Object>中的key，webInfos--本user对应的List<WebInfo>
	 */
	public static String WEBINFOS = "webInfos";
	
	/**
	 * 对应Map<String, Object>中的key，userId--本user的Id
	 */
	public static String USER_ID = "userId";
	
	/**
	 * 对应Map<String, Object>中的key，userName--本user的name
	 */
	public static String USER_NAME = "userName";
	
	/**
	 * 对应Map<String, Object>中的key，logName--本user的登录名
	 */
	public static String LOG_NAME = "logName";
	
	/**
	 * 对应Map<String, Object>中的key，departmentId--本user对应的部门Id
	 */
	public static String DEPARTMENT_ID = "departmentId";
	
	/**
	 * 对应Map<String, Object>中的key，departmentName--本user对应的部门name
	 */
	public static String DEPARTMENT_NAME = "departmentName";
	
	/**
	 * 对应Map<String, Object>中的key，webIds--本user对应部门所对应的webIds
	 */
	public static String WEB_IDS = "webIds";
	
	/**
	 * 对应Map<String, Object>中的key，webId--本user对应部门所对应的webId
	 */
	public static String WEB_ID = "webId";
	
	/**
	 * 对应Map<String, Object>中的key，deparmentIds--本user对应部门所对应的webId对应的几个部门Id
	 */
	public static String DEPARMENT_IDS = "deparmentIds";
	
	/**
	 * 对应Map<String, Object>中的key，deparmentNames--本user对应部门所对应的webId对应的几个部门Name
	 */
	public static String DEPARMENT_NAMES = "deparmentNames";
	
	/**
	 * 对应Map<String, Object>中的key，deps--对应的部门,selectTreeService.getInfos方法返回中用到
	 */
	public static String DEPS = "deps";
	
	/**
	 * 对应Map<String, Object>中的key，emps--对应的人员,selectTreeService.getInfos方法返回中用到
	 */
	public static String EMPS = "emps";

	/**
	 * 本步已经处理好，用于判断一个步骤是否处理好
	 */
	public static String OVER = "OVER";

	/**
	 * 本步没有处理好，用于判断一个步骤是否处理好
	 */
	public static String NOT_OVER = "NOT_OVER";

	/**
	 * 表示是save操作
	 */
	public static String SAVE = "SAVE";

	/**
	 * 表示是update操作
	 */
	public static String UPDATE = "UPDATE";

	/**
	 * 降序排列
	 */
	public static String DESC = "DESC";

	/**
	 * 升序排列
	 */
	public static String ASC = "ASC";
	
	/**
	 * 对应Map<String, Object>中的key，docs--对应的公文,WorkflowService.getDoDocsListInfoByUserId方法返回中用到
	 */
	public static String DOCS = "docs";
	
	/**
	 * 确定是什么操作系统，并返回相应的后缀<br>
	 * Windows操作系统返回 WIN<br>
	 * Linux操作系统返回 LNX<br>
	 * 
	 * @return
	 */
	private static String getSystemInfo() {
		String osName = System.getProperty("os.name");
		if (osName == null) {
			System.out
					.println("判断是Windows系统还是Linux系统时出错，位置在cn.com.trueway.innerMail.util.Constants.java");
			return "WIN";
		}
		if (osName.toLowerCase().indexOf("win") != -1) {
			// 如果是window 操作系统
			return "WIN";
		} else {
			// 如果是其他的系统
			return "LNX";
		}
	}

	
	
	/**
	 * 得到上传正文文档附件保存的基路径
	 * 
	 * @return
	 */
	public static String getAttachmentPath() {
		String os = getSystemInfo();

		// 读取配置文件中的属性
		/**
		 * 在Winodows系统服务器上储存附件的基路径<br>
		 * 全部路径为：ATTACACHMENT_PATH + 附件名<br>
		 */
		String ATTACACHMENT_PATH_WIN = SystemParamConfigUtil
				.getParamValueByParam("OA_DOC_ATTACACHMENT_PATH_WIN");
		/**
		 * 在Linux系统服务器上储存附件的基路径<br>
		 * 全部路径为：ATTACACHMENT_PATH + 附件名<br>
		 */
		String ATTACACHMENT_PATH_LNX = SystemParamConfigUtil
				.getParamValueByParam("OA_DOC_ATTACACHMENT_PATH_LNX");

		if (os.equals("WIN")) {
			return ATTACACHMENT_PATH_WIN;
		} else if (os.equals("LNX")) {
			return ATTACACHMENT_PATH_LNX;
		} else {
			return ATTACACHMENT_PATH_WIN;
		}
	}
	
	/**
	 * 得到上传附件文档附件保存的基路径
	 * 
	 * @return
	 */
	public static String getAttachmentExtPath() {
		String os = getSystemInfo();

		// 读取配置文件中的属性
		/**
		 * 在Winodows系统服务器上储存附件的基路径<br>
		 * 全部路径为：ATTACACHMENT_PATH + 附件名<br>
		 */
		String ATTACACHMENT_PATH_WIN = SystemParamConfigUtil
				.getParamValueByParam("OA_DOC_ATTACACHMENTEXT_PATH_WIN");
		/**
		 * 在Linux系统服务器上储存附件的基路径<br>
		 * 全部路径为：ATTACACHMENT_PATH + 附件名<br>
		 */
		String ATTACACHMENT_PATH_LNX = SystemParamConfigUtil
				.getParamValueByParam("OA_DOC_ATTACACHMENTEXT_PATH_LNX");

		if (os.equals("WIN")) {
			return ATTACACHMENT_PATH_WIN;
		} else if (os.equals("LNX")) {
			return ATTACACHMENT_PATH_LNX;
		} else {
			return ATTACACHMENT_PATH_WIN;
		}
	}

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();

		System.out.println(getAttachmentPath());
		System.out.println(getAttachmentExtPath());

		long t2 = System.currentTimeMillis();

		System.out.println(t2 - t1);
	}

	/**
	 * Process的属性，表示本流程未结束
	 */
	public static long PROCESS_NOT_END = 0;
	
	
	/**
	 * Process的属性，流程是否为代办自动处理
	 */
	public static long PROCESS_IS_PROXY = 3;
	
	/**
	 * Process的属性，表示本流程结束
	 */
	public static long PROCESS_END = 1;
	
	/**
	 * Process的属性，表示本流程结束
	 */
	public static long PROCESS_ZUOFEI = 2;
	
	/**
	 * 传阅的stepId
	 */
	public static String CY_STEP_ID = "3A5B87E3-870B-49D3-934C-D44DE500A680";
	
	/**
	 * 待办列表
	 */
	public static String TODO_LIST = "todoList";
	
	/**
	 * 办结列表
	 */
	public static String OVER_LIST = "overList";
	
	/**
	 * 节点类型，开始
	 */
	public static long STEP_TYPE_START = 0;
	
	/**
	 * 节点类型，并行
	 */
	public static long STEP_TYPE_BINGXING = 2;
	
	/**
	 * 节点类型，竞争
	 */
	public static long STEP_TYPE_JINGZHENG = 3;

	/**
	 * 节点类型，结束
	 */
	public static long STEP_TYPE_END = 9;
	
	
	/**
	 * 办文单处理结果
	 * 表单：OA_DOC_BW
	 * 字段：result
	 * 含义：流转中
	 * 值：0
	 */
	public static int BWD_RESULT_LIUZHUANZHONG=0;
	/**
	 * 办文单处理结果
	 * 表单：OA_DOC_BW
	 * 字段：result
	 * 含义：办结
	 * 值：1
	 */
	public static int BWD_RESULT_BANJIE=1;
	/**
	 * 办文单处理结果
	 * 表单：OA_DOC_BW
	 * 字段：result
	 * 含义：待反馈
	 * 值：2
	 */
	public static int BWD_RESULT_DAIFANKUI=2;
	/**
	 * 办文单处理结果
	 * 表单：OA_DOC_BW
	 * 字段：result
	 * 含义：作废
	 * 值：3
	 */
	public static int BWD_RESULT_ZUOFEI=3;
	/**
	 *  已收列表的状态
	 *  表单：OA_DOC_RECEIVE
	 *  字段：status
	 *  含义：已关联
	 *  值：5
	 */
	public static String RECEIVE_STATUS_YIGUANLIAN="5";
	
	/**
	 * 公文的状态：流转中0
	 */
	public static final String DOC_BOX_LIUZZ="0";
	/**
	 * 公文的状态：待阅1
	 */
	public static final String DOC_BOX_DAIYUE="1";
	/**
	 * 公文的状态：已阅2
	 */
	public static final String DOC_BOX_YIYUE="2";
	/**
	 * 公文的状态：待签3
	 */
	public static final String DOC_BOX_DAIQIAN="3";
	/**
	 * 公文的状态：已签4
	 */
	public static final String DOC_BOX_YIQIAN="4";
	/**
	 * 公文的状态：待发送5
	 */
	public static final String DOC_BOX_DAIFA="5";
	/**
	 * 公文的状态：已发送6
	 */
	public static final String DOC_BOX_YIFA="6";
	/**
	 * 公文的状态：作废7
	 */
	public static final String DOC_BOX_ZUOFEI="7";
	
	/**
	 * 已收公文的状态：0 未处理 
	 */
	public static final String RECEIVE_WEICHULI="0";
	/**
	 * 已收公文的状态：1正在处理
	 */
	public static final String RECEIVE_ZHENGBANLI="1";
	/**
	 * 已收公文的状态：2 已办结
	 */
	public static final String RECEIVE_BANJIE="2";
	/**
	 * 已收公文的状态：3 其他
	 */
	public static final String RECEIVE_WUGUAN="3";
	/**
	 * 已收公文的状态： 4 已退文
	 */
	public static final String RECEIVE_TUIWEN="4";
	/**
	 * 已收公文的状态：5 已关联
	 */
	public static final String RECEIVE_GUANLIAN="5";
	
	/**
	 * /**
	 * 公文管理，已发公文，详细，要显示接收单位的状态。
	 * 状态显示：未接收 、已接收 、已阅读 、已处理 、已退文
	 */
	public static final String SENDSTATUS_WEIJIESHOU="未接收";
	/**
	 * /**
	 * 公文管理，已发公文，详细，要显示接收单位的状态。
	 * 状态显示：未接收 、已接收 、已阅读 、已处理 、已退文
	 */
	public static final String SENDSTATUS_YIJIESHOU="已接收";
	/**
	 * /**
	 * 公文管理，已发公文，详细，要显示接收单位的状态。
	 * 状态显示：未接收 、已接收 、已阅读 、已处理 、已退文
	 */
	public static final String SENDSTATUS_YIYUEDU="已阅读";
	/**
	 * /**
	 * 公文管理，已发公文，详细，要显示接收单位的状态。
	 * 状态显示：未接收 、已接收 、已阅读 、已处理 、已退文
	 */
	public static final String SENDSTATUS_YICHULI="已处理";
	/**
	 * /**
	 * 公文管理，已发公文，详细，要显示接收单位的状态。
	 * 状态显示：未接收 、已接收 、已阅读 、已处理 、已退文
	 */
	public static final String SENDSTATUS_YITUIWEN="已退文";
	/**
	 * /**
	 *退文列表 untreadStatus  0：未接收  1：已查看   2为所有   空也为所有
	 * 状态显示：未接收 、已接收 、已阅读 、已处理 、已退文
	 */
	public static final int UNTREAD_STATUS_NOTRECEIVE=0;
	/**
	 * 统一上传所有文件的路径
	 */
	public static final String UPLOAD_FILE_PATH = "attachments/";
	public static final String GENE_FILE_PATH = "pdf/";
	public static final String GENE_HTML_FILE_PATH = "html/";
	public static final String GENE_TRUE_FILE_PATH = "true/";
	public static final String GENE_TXT_FILE_PATH = "txt/";
	public static final String GENE_GD_FILE_PATH = "gdfile/";//归档文件的路劲
	public static final String GENE_ZIP_FILE_PATH = "zip/";//归档文件的路劲
	public static final String GENE_ZIP_EXCEL_PATH = "excel/";//归档文件的路劲
	
	/**
	 * 统一上传所有图片格式文件的路径
	 */
	public static final String UPLOAD_IMG_PATH = "imgs/";
	/**
	 * 接收退文时的状态:已退文
	 */
	public static final String UNTREAD_STATUS_YITUIWEN="已退文";
	/**
	 * 表示所有的情况
	 */
	public static final String ALL="ALL";
	/**
	 * 成功的的情况
	 */
	public static final String SUCCESS="SUCCESS";

	/**
	 * 异常的情况
	 */
	public static final String EXCEPTION = "EXCEPTION";

	/**
	 * 失败的情况
	 */
	public static final String FAIL="FAIL";
	
	//登陆后放到session 中的用户id
	public static final String SESSION_USER_ID="userId";
	
	//登陆后放到session 中的用户名称

	public static final String SESSION_USER_NAME="userName";
	/**
	 * 办文流程
	 */
	public static final String DEFINE_TYPE_DO = "1";
	/**
	 * 发文流程
	 */
	public static final String DEFINE_TYPE_SEND = "0";
	
	/**
	 * 文号大类
	 */
	public static final String DOCNUM_PARENT = "0";
	/**
	 * 文号小类
	 */
	public static final String DOCNUM_CHILD = "1";
	/**
	 * 文号使用
	 */
	public static final String DOCNUM_USE = "1";
	/**
	 * 文号未使用
	 */
	public static final String DOCNUM_UNUSE = "0"; 
	/**
	 * 文号排序
	 */
	public static final String DOCNUM_TIME_ASC = "time_asc";

	public static final String DOCNUM_TIME_DESC = "time_desc";
	
	public static final String DOCNUM_NUM_ASC = "number_asc";

	public static final String DOCNUM_NUM_DESC = "number_desc"; 
	
	//流程当前状态  生效
/**
 * 流程当前状态  生效
 */
	public static final String DEFINE_STATUS_SHENGXIAO="1";
	
	/**
	 * 流程当前状态  失效
	 */
	public static final String DEFINE_STATUS_SHIXIAO="0";

	/**
	 * 流程中发送到下一步时，flag标记位 ,表示：不插入新的Process对象
	 */
	public static final int WORKFLOW_SEND_FLAG_ADD_NO=0;
	
	/**
	 * 流程中发送到下一步时，flag标记位 ,表示：插入新的Process对象
	 */
	public static final int WORKFLOW_SEND_FLAG_ADD_YES=1;
	
	/**
	 * Step 中接收人类型 ，表示本步骤固定了接收人员（自动发送）
	 */
	public static final int STEP_TO_USERTYPE_AUTO_SEND = 5;
	
	/**
	 * Step 中接收人类型 ，表示本步骤自动返回给拟稿人（自动返回）
	 */
	public static final int STEP_TO_USERTYPE_AUTO_BACK = 6;
	
	/**
	 * 路由类型，该类型表示单向
	 */
	public static long ROUTE_TYPE_SINGLE = 0L;
	
	/**
	 * 路由类型，该类型表示可回退
	 */
	public static long ROUTE_TYPE_BACK = 1L;
	
	/**
	 * 路由类型，该类型表示自动 
	 */
	public static long ROUTE_TYPE_AUTO = 2L;

	/**
	 * OA_DOC_RECEIVE表中的BW_TYPE，表示办文
	 */
	public static String BW_TYPE_BANWEN = "0";

	/**
	 * OA_DOC_RECEIVE表中的BW_TYPE，表示传阅
	 */
	public static String BW_TYPE_CHUANYUE = "1";

	/**
	 * OA_DOC_RECEIVE表中的SOURCESTATUS(公文来源)：公文交换平台
	 */
	public static String SOURCESTATUS_GW = "";

	/**
	 * OA_DOC_RECEIVE表中的SOURCESTATUS(公文来源)：手动导入
	 */
	public static String SOURCESTATUS_IMPORT = "1";

	/**
	 * OA_DOC_RECEIVE表中的SOURCESTATUS(公文来源)：其他
	 */
	public static String SOURCESTATUS_OTHER = "2";
	
	/**
	 * OA_DOC_RECEIVE表中的SOURCESTATUS(公文来源)：2011-11-12从公文交换平台迁移
	 */
	public static String SOURCESTATUS_MOVE = "3";
	/**
		办文流转 Iframe的参数的意义2.main:当为true时是主文，当false为附文; 3.writeAble:当true时可写，反之亦然;4.firstStep:当true时是第一步,反之亦然;
	 */
	public static String BWDURLFORIFRAME_MAIN="true";
	/**
	办文流转 Iframe的参数的意义2.main:当为true时是主文，当false为附文; 3.writeAble:当true时可写，反之亦然;4.firstStep:当true时是第一步,反之亦然;
	 */
	public static String BWDURLFORIFRAME_ATTACH="false";
	/**
	办文流转 Iframe的参数的意义2.main:当为true时是主文，当false为附文; 3.writeAble:当true时可写，反之亦然;4.firstStep:当true时是第一步,反之亦然;
	 */
	public static String BWDURLFORIFRAME_WRITEABLE="true";
	/**
	办文流转 Iframe的参数的意义2.main:当为true时是主文，当false为附文; 3.writeAble:当true时可写，反之亦然;4.firstStep:当true时是第一步,反之亦然;
	 */
	public static String BWDURLFORIFRAME_WRITEUNABLE="false";
	/**
	办文流转 Iframe的参数的意义2.main:当为true时是主文，当false为附文; 3.writeAble:当true时可写，反之亦然;4.firstStep:当true时是第一步,反之亦然;
	 */
	public static String BWDURLFORIFRAME_FirstStep="true";
	
	/**
	 * 工作日历中上班时间 9点
	 */
	public static String START_WORK_TIME="9:30";

	/**
	 * 工作日历中下班时间 17点
	 */
	public static String END_WORK_TIME="17:30";
	
	/**
	 * 工作日历中工作日
	 */
	public static String WORK_DAY="-1";

	/**
	 * 工作日历中休息日
	 */
	public static String NOT_WORK_DAY="0";
	/**
	 * 意见标签删除意见后记录时间不需要变化
	 */
	public static String COMMENT_DELETE_NOCHANGE="NOCHANGE";
	
	/**
	 * 导出的最大行数
	 */
	public static final long EXPORT_MAX_ROWNUM=1000L;

	/**
	 * 亮红灯的最大天数，大于等于此数值同时小于亮黄灯的最大天数 则亮黄灯
	 */
	public static final int RED_LIGHT=1;
	/**
	 * 亮黄灯的最大天数，大于等于此数值同时小于亮蓝灯的最大天数 则亮蓝灯
	 */
	public static final int YELLOW_LIGHT=2;
	/**
	 * 亮蓝灯的最大天数，大于等于此数值亮緑灯
	 */
	public static final int BLUE_LIGHT=3;
	
	public static String LICENSE_CHECK_PASSED="3";

	public static int LICENSE_CHECK_NUMBER=0;
	
	public static Map<String,String> licenseInfoMap;
	
	public static TableInfoAction tableInfoAction;
	/**
	 * 是否使用弹性表单
	 */
	public static boolean isFlexibleForm;
	
	public static final String productName = "中威工作流表单系统"; 
	
	public static final String productVersion = "V3.0"; 
	
	public static final String licensePath = PathUtil.getWebRoot() + "license/license.lic";
}
