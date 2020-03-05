/**
 * 文件名称:DTPageBean.java
 * 作者:吴新星<br>
 * 创建时间:2011-8-8 上午10:44:19
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.base.displaytag;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：Display Tag 中对应的pageBean <br>
 * 作者：吴新星<br>
 * 创建时间：2011-8-8 上午10:44:19<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class DTPageBean implements Serializable {

	private static final long serialVersionUID = -6618563592781834912L;
	
	/**
	 * 存放数据的List集合
	 */
	private List<?> dataList;
	
	/**
	 * 一页显示的记录数
	 */
	private int numPerPage;
	
	/**
	 * 记录总数
	 */
	private int totalRows;
	
	/**
	 * 总页数
	 */
	private int totalPages;
	
	/**
	 * 当前页码
	 */
	private int currentPage;
	
	//查询SQl语句
	private String pagingSql;

	/**
	 * @return the dataList
	 */
	public List<?> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}

	/**
	 * @return the numPerPage
	 */
	public int getNumPerPage() {
		return numPerPage;
	}

	/**
	 * @param numPerPage the numPerPage to set
	 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	/**
	 * @return the totalRows
	 */
	public int getTotalRows() {
		return totalRows;
	}

	/**
	 * @param totalRows the totalRows to set
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getPagingSql() {
		return pagingSql;
	}

	public void setPagingSql(String pagingSql) {
		this.pagingSql = pagingSql;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("dataList ：\r\n");
		for(int i=0,l=dataList.size();i<l;i++){
			sb.append("第"+ i +"个元素：" +dataList.get(i)).append("\r\n");
		}
		sb.append("一页显示的记录数：").append(numPerPage).append("\r\n");
		sb.append("记录总数：").append(totalRows).append("\r\n");
		sb.append("总页数：").append(totalPages).append("\r\n");
		sb.append("当前页码：").append(currentPage).append("\r\n");
		return sb.toString();
	}
	
}
