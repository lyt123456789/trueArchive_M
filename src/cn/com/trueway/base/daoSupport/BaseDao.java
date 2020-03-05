/**
 * 文件名称:BaseDao.java
 * 作者:吴新星<br>
 * 创建时间:2011-8-8 上午10:45:19
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.base.daoSupport;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.com.trueway.base.displaytag.DTPageBean;

/**
 * 描述：增加dao的功能，简化代码<br>
 * 作者：吴新星<br>
 * 创建时间：2011-11-29 上午11:14:27<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public abstract class BaseDao{
	
	@PersistenceContext(unitName="persistenceUnit")
	private EntityManager em;
	
	/**
	 * @return the em
	 */
	public EntityManager getEm() {
		return em;
	}

	/**
	 * @param em the em to set
	 */
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	/**
	 * 描述：查 + 分页（基于sql语句实现）<br>
	 *
	 * @param totalRowsSql -- 查记录总数的sql
	 * @param querySql -- 查询语句
	 * @param currentPage -- 当前页码
	 * @param numPerPage -- 一页显示的记录数
	 * @param resultClass -- 返回结果的类
	 * @return DTPageBean -- Display Tag 中对应的pageBean
	 */
	@SuppressWarnings("unchecked")
	public DTPageBean pagingQuery(String totalRowsSql, String querySql, int currentPage, int numPerPage, Class resultClass) {
		//构造返回对象  DTPageBean
		DTPageBean dtPageBean = this.initQuery(totalRowsSql, querySql, currentPage, numPerPage);
		//根据分页sql得到结果集
		List dataList = this.em.createNativeQuery(dtPageBean.getPagingSql(), resultClass).getResultList();
		//数据填充
		dtPageBean.setDataList(dataList);
		return dtPageBean;
	}
	
	/**
	 * 描述：查 + 分页（基于sql语句实现）<br>
	 *
	 * @param totalRowsSql -- 查记录总数的sql
	 * @param querySql -- 查询语句
	 * @param currentPage -- 当前页码
	 * @param numPerPage -- 一页显示的记录数
	 * @param sqlResultSetMapping -- 返回结果的类型
	 * @return DTPageBean -- Display Tag 中对应的pageBean
	 *
	 * 作者:吴新星<br>
	 * 创建时间:2011-12-13 下午10:58:31
	 */
	@SuppressWarnings("unchecked")
	public DTPageBean pagingQuery(String totalRowsSql, String querySql, int currentPage, int numPerPage, String sqlResultSetMapping) {
		//构造返回对象  DTPageBean
		Long start = System.currentTimeMillis();
		DTPageBean dtPageBean = this.initQuery(totalRowsSql, querySql, currentPage, numPerPage); //1
		Long a = System.currentTimeMillis();
		System.out.println("1 : "+(a-start));
		//根据分页sql得到结果集
		List dataList = this.em.createNativeQuery(dtPageBean.getPagingSql(), sqlResultSetMapping).getResultList();//2
		Long b = System.currentTimeMillis();
		System.out.println("2 : "+(b-a));
		//数据填充
		dtPageBean.setDataList(dataList);
		return dtPageBean;
	}
	
	private DTPageBean initQuery(String totalRowsSql, String querySql, int currentPage, int numPerPage){
		//记录总数
		int totalRows = ((BigDecimal)this.em.createNativeQuery(totalRowsSql).getSingleResult()).intValue();
		//开始记录的位置
		int startIndex = (currentPage - 1) * numPerPage;
		//总页数
		int totalPages = 0;
		if(totalRows % numPerPage == 0){
			totalPages = totalRows / numPerPage;
		}else{
			totalPages = totalRows / numPerPage + 1;
		}
		//最后一条记录的位置
		int lastIndex = 0;
		if(totalRows < numPerPage){
			lastIndex = totalRows;
		}else if ((currentPage == totalPages && totalRows % numPerPage == 0) || currentPage < totalPages) {
			lastIndex = currentPage * numPerPage;
		}else if ((currentPage == totalPages && totalRows % numPerPage != 0)) {
			lastIndex = totalRows;
		}
		//分页sql
		StringBuffer pagingSql = new StringBuffer();
		pagingSql.append("select * from ( select my_table.*,rownum as my_rownum from ( ");
		pagingSql.append(querySql);
		pagingSql.append(" ) my_table where rownum <= ").append(lastIndex);
		pagingSql.append(") where my_rownum > ").append(startIndex);
		DTPageBean dtPageBean = new DTPageBean();
		dtPageBean.setPagingSql(pagingSql.toString());
		dtPageBean.setNumPerPage(numPerPage);
		dtPageBean.setTotalRows(totalRows);
		dtPageBean.setTotalPages(totalPages);
		dtPageBean.setCurrentPage(currentPage);
		return  dtPageBean;
	}
}
