package cn.com.trueway.functions.workflow.entity.vo;

public class Pend4BreezeVo {

	private String title;//待办标题
	private Integer isChildWf;//区分是否子流程
	private Integer isHaveChild;//区分是否子流程
	private String date;//待办日期
	private String link;//待办链接
	private String pendType;//待办类型
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPendType() {
		return pendType;
	}
	public void setPendType(String pendType) {
		this.pendType = pendType;
	}
	public Integer getIsChildWf() {
		return isChildWf;
	}
	public void setIsChildWf(Integer isChildWf) {
		this.isChildWf = isChildWf;
	}
	public Integer getIsHaveChild() {
		return isHaveChild;
	}
	public void setIsHaveChild(Integer isHaveChild) {
		this.isHaveChild = isHaveChild;
	}
	
	
	
	
	
}
