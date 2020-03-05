package cn.com.trueway.workflow.core.pojo;


/**
 * 意见类型实体
 * @author xuxh
 *
 */
//@Entity
//@Table(name = "WF_Condition")
public class Opinion implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 7014627793517702644L;
	/**
	 * 唯一标识
	 */
	private String uuid;
	
	/**
	 * 意见名称
	 */
	private String name;
	
	/**
	 * 意见类型
	 */
	private int type;
	/**
	 * 外键关联意见内容的id
	 */
	private String opinionOfContentId;
	
	

	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	public String getOpinionOfContentId() {
		return opinionOfContentId;
	}



	public void setOpinionOfContentId(String opinionOfContentId) {
		this.opinionOfContentId = opinionOfContentId;
	}



	public Opinion() {
		super();
	}

	
}
