package cn.com.trueway.workflow.set.util;


import cn.com.trueway.sys.util.SystemParamConfigUtil;

import com.opensymphony.xwork2.config.ConfigurationException;

/**
 * @描述 常量类，定义本系统中用到的一些常量
 * @作者 李伟
 * @时间 未知
 * 
 * @编辑描述 删除无用数据
 * @编辑人 范吉锋
 * @编辑时间 2012年8月18日, PM 03:11:50
 */
public class Constants {

	/**
	 * 返回分页时每页显示的数据条数<br>
	 * 
	 * @return
	 * @throws ConfigurationException
	 */
	public static Integer getPageSize() {
		// 读取配置文件中的属性
		/**
		 * 分页每页显示的记录大小<br>
		 */
		int PageSize = Integer.valueOf(SystemParamConfigUtil.getParamValueByParam("AMOUNT_ONE_PAGE"));
		return PageSize;
	}

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
			System.out.println("判断是Windows系统还是Linux系统时出错，位置在cn.com.trueway.innerMail.util.Constants.java");
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
	 * 得到附件的基路径
	 * 
	 * @return
	 * @throws ConfigurationException
	 */
	public static String getAttachmentPath() {
		String os = getSystemInfo();

		// 读取配置文件中的属性
		/**
		 * 在Winodows系统服务器上储存附件的基路径<br>
		 * 全部路径为：ATTACACHMENT_PATH + 附件名<br>
		 */
		String ATTACACHMENT_PATH_WIN = SystemParamConfigUtil.getParamValueByParam("ATTACACHMENT_PATH_WIN");
		/**
		 * 在Linux系统服务器上储存附件的基路径<br>
		 * 全部路径为：ATTACACHMENT_PATH + 附件名<br>
		 */
		String ATTACACHMENT_PATH_LNX = SystemParamConfigUtil.getParamValueByParam("ATTACACHMENT_PATH_LNX");

		int i = ATTACACHMENT_PATH_WIN.lastIndexOf("/");
		// 如果基路径不以"/"结尾,则添加上
		if ((i > -1) && (i != ATTACACHMENT_PATH_WIN.length() - 1)) {
			ATTACACHMENT_PATH_WIN = ATTACACHMENT_PATH_WIN + "/";
		}

		if (os.equals("WIN")) {
			return ATTACACHMENT_PATH_WIN;
		} else if (os.equals("LNX")) {
			i = ATTACACHMENT_PATH_LNX.lastIndexOf("/");
			// 如果基路径不以"/"结尾,则添加上
			if ((i > -1) && (i != ATTACACHMENT_PATH_LNX.length() - 1)) {
				ATTACACHMENT_PATH_LNX = ATTACACHMENT_PATH_LNX + "/";
			}
			return ATTACACHMENT_PATH_LNX;
		} else {
			return ATTACACHMENT_PATH_WIN;
		}
	}

	/**
	 * 服务器操作系统的编码
	 * 
	 * @return
	 * @throws ConfigurationException
	 */
	public static String getSysEncode() {
		String os = getSystemInfo();
		/**
		 * Winodows操作系统的编码 默认为GB2312
		 */
		String SYS_ENCODE_WIN = SystemParamConfigUtil.getParamValueByParam("SYS_ENCODE_WIN");
		/**
		 * Linux操作系统的编码 默认为UTF-8
		 */
		String SYS_ENCODE_LNX = SystemParamConfigUtil.getParamValueByParam("SYS_ENCODE_LNX");

		if (os.equals("WIN")) {
			return SYS_ENCODE_WIN;
		} else if (os.equals("LNX")) {
			return SYS_ENCODE_LNX;
		} else {
			return SYS_ENCODE_WIN;
		}
	}
}
