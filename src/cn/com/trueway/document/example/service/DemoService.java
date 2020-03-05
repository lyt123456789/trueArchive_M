/**
 * 文件名称:DemoService.java
 * 作者:吴新星<br>
 * 创建时间:2011-12-3 上午11:02:46
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.example.service;

import cn.com.trueway.base.displaytag.DTPageBean;

/**
 * 描述：TODO 对DemoService进行描述<br>
 * 作者：吴新星<br>
 * 创建时间：2011-12-3 上午11:02:46<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface DemoService {
	
	public DTPageBean queryUsers(int currentPage, int numPerPage);
}
