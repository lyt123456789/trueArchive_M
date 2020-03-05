/**
 * @package test
 * @filename GenUserKey.java<br>
 * @author <a href="mailto:xraycn@qq.com">顾锡均</a><br>
 * @crtdate 2010-7-3
 */
package cn.com.trueway.document.business.docxg.client.support;

import sun.misc.BASE64Encoder;
/**
 * 描述：对GenUserKey进行描述<br>
 * 作者：周雪贇<br>
 * 创建时间：2011-11-30 下午09:59:18<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class GenUserKey {
	
	public static String genUserKey(String depIdOri){
		String depId = depIdOri.substring(1,depIdOri.length());
		String userKey = new BASE64Encoder().encode(depId.getBytes()) + "%=U$%";
		userKey = new BASE64Encoder().encode(userKey.getBytes()).trim();
		return userKey;
	}
	
	public static void main(String[] args) {
		String depid = "{BFA7FFA6-0000-0000-03BF-FE4400000032}";
		String userKey = genUserKey(depid);
		//String userKeyOri = "UWtaQk9ERXhSVUV0TURBd01DMHdNREF3TFRRMU5UUXROek00TlRBd01EQXdNa0pDZlE9PSU9VSQl";
		System.out.println(userKey);
		//System.out.println(userKeyOri);
		//System.out.println(userKeyOri.equals(userKey));
	}
}
