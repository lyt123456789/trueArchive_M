package cn.com.trueway.workflow.core.pojo.vo;

/**
 * 用于移动端的意见临时类
 * @author Administrator
 *
 */
public class CommentElement {
	private String content; //意见内容
	
	private String personName; //签署人
	
	private String signTime; //签署时间
	
	private String commentName; //意见中文名
	
	private String instanceId;
	
	private String stepId;
	
	private String tagId;
	
	private String limit; //权限 ---0:隐藏,1:只读,2:读写

	public CommentElement() {
		
	}

	public CommentElement(String content, String personName, String signTime,
			String commentName, String instanceId, String stepId, String tagId,
			String limit) {
		super();
		this.content = content;
		this.personName = personName;
		this.signTime = signTime;
		this.commentName = commentName;
		this.instanceId = instanceId;
		this.stepId = stepId;
		this.tagId = tagId;
		this.limit = limit;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getCommentName() {
		return commentName;
	}
	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}

	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
}
