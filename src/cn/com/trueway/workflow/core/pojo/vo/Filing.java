package cn.com.trueway.workflow.core.pojo.vo;

/**
 * 归档查询实体类
 * @author 赵坚
 *2015年8月18日16:46:20
 */
public class Filing {
	private String title;//标题
	private String wh;//文号
	private String type;//文种
	private String filedTime;//归档日期
	private String processId;
	private String itemId;//事项id
	private String formId;
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWh() {
		return wh;
	}
	public void setWh(String wh) {
		this.wh = wh;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFiledTime() {
		return filedTime;
	}
	public void setFiledTime(String filedTime) {
		this.filedTime = filedTime;
	}
	
	
}
