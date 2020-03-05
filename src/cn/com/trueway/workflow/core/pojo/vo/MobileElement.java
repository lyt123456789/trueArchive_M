package cn.com.trueway.workflow.core.pojo.vo;

import java.util.List;

public class MobileElement {

	private List<FormElement> feList ; //表单元素
	
	private List<CommentElement> ceList; //意见元素

	private String commentJson; //意见json
	
	public MobileElement() {
		
	}

	public MobileElement(List<FormElement> feList, List<CommentElement> ceList,
			String commentJson) {
		super();
		this.feList = feList;
		this.ceList = ceList;
		this.commentJson = commentJson;
	}

	public List<FormElement> getFeList() {
		return feList;
	}
	public void setFeList(List<FormElement> feList) {
		this.feList = feList;
	}

	public List<CommentElement> getCeList() {
		return ceList;
	}
	public void setCeList(List<CommentElement> ceList) {
		this.ceList = ceList;
	}

	public String getCommentJson() {
		return commentJson;
	}
	public void setCommentJson(String commentJson) {
		this.commentJson = commentJson;
	}
}
