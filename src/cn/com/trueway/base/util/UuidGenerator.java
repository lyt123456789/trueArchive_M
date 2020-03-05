/**
 * 文件名称：UuidGenerator.java<br>
 * 作者：吴新星<br>
 * 创建时间：Feb 1, 2010 8:32:30 PM
 * CopyRight (c)2009-2011:江苏中威科技信息系统有限公司<br>
 */
package cn.com.trueway.base.util;

import java.util.UUID;

/**
 * 描述：UUID的生成类<br>
 * 作者：吴新星<br>
 * 创建时间：Feb 1, 2010 8:32:30 PM<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5
 */
public class UuidGenerator {

	/**
	 * 生成36位的UUID
	 * 
	 * @return
	 */
	public static String generate36UUID(){
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(generate36UUID());
	}
}
