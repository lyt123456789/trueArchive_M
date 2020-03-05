/**
 * 文件名称：UuidGenerator.java<br>
 * 作者：吴新星<br>
 * 创建时间：Feb 1, 2010 8:32:30 PM
 * CopyRight (c)2009-2011:江苏中威科技信息系统有限公司<br>
 */
package cn.com.trueway.workflow.set.util;

import java.util.UUID;

/**
 * 描述：UUID的生成类<br>
 * 作者：顾锡均<br>
 * 创建时间：2010-4-26<br>
 * 版本：v1.0 <br>
 * JDK版本：JDK1.5
 */
public final class UuidGenerator {

	/**
	 * 生成36位的UUID
	 * 
	 * @return
	 */
	synchronized final public static String generate36UUID() {
		return UUID.randomUUID().toString().toUpperCase();
	}

	synchronized final public static String generate32UUID() {
		return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}

}
