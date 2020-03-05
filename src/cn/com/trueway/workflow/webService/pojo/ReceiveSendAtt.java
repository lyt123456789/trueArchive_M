package cn.com.trueway.workflow.webService.pojo;

import java.util.Date;

public class ReceiveSendAtt {

	/**
	 * 附件名称
	 */
	private String name;
	/**
	 * 附件类型
	 */
	private String attType;
	/**
	 * 文件类型
	 */
	private String type;
	/**
	 * 附件上传时间
	 */
	private Date attTime;
	/**
	 * 附件下载地址
	 */
	private String url;
	/**
	 * 附件大小
	 */
	private long filesize;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttType() {
		return attType;
	}
	public void setAttType(String attType) {
		this.attType = attType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getAttTime() {
		return attTime;
	}
	public void setAttTime(Date attTime) {
		this.attTime = attTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	
	
}
