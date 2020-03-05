package cn.com.trueway.workflow.set.pojo;


/**
 * 人员用户的排列次序
 * @author caiyj
 *
 */
public class EmployeeSort {
	
	private String userId;
	
	private String userName;
	
	private Long sort;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
}
