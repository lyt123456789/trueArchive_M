/**
 * 文件名称:DemoServiceImpl.java
 * 作者:吴新星<br>
 * 创建时间:2011-12-3 上午11:03:36
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.example.service.impl;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.example.dao.ExampleDao;
import cn.com.trueway.document.example.service.DemoService;

/**
 * 描述：TODO 对DemoServiceImpl进行描述<br>
 * 作者：吴新星<br>
 * 创建时间：2011-12-3 上午11:03:36<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class DemoServiceImpl implements DemoService {
	
	private ExampleDao exampleDao;
	
	public DTPageBean queryUsers(int currentPage, int numPerPage){
		return exampleDao.queryAllUser("select count(*) from example_user where sex = '男'","select * from example_user where sex = '男' order by user_id ", currentPage, numPerPage);
	}

	/**
	 * @return the exampleDao
	 */
	public ExampleDao getExampleDao() {
		return exampleDao;
	}

	/**
	 * @param exampleDao the exampleDao to set
	 */
	public void setExampleDao(ExampleDao exampleDao) {
		this.exampleDao = exampleDao;
	}
}
