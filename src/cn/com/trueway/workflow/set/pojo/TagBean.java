package cn.com.trueway.workflow.set.pojo;

public class TagBean {
	private String tagName;//标签name属性值
	private String tagType;//标签类型
	private String select_dic;//下拉框关联字典表名称(用于根据字典表自动填充下拉框)
	private String listId;//列表标记
	private String commentDes;//意见标签专属中文描述
	private String commentTagDes;//意见标签html字符串，用于生成jsp时过滤html意见标签的自定义属性commentDes
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public String getSelect_dic() {
		return select_dic;
	}
	public void setSelect_dic(String select_dic) {
		this.select_dic = select_dic;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getCommentDes() {
		return commentDes;
	}
	public void setCommentDes(String commentDes) {
		this.commentDes = commentDes;
	}
	public String getCommentTagDes() {
		return commentTagDes;
	}
	public void setCommentTagDes(String commentTagDes) {
		this.commentTagDes = commentTagDes;
	}
	
}
