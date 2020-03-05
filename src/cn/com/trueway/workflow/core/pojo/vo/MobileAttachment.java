package cn.com.trueway.workflow.core.pojo.vo;

public class MobileAttachment {
	
	private String attId;
	
	private String docguid;
	
	private String name;
	
	private String fileLoaction;
	
	/**
	 * 附件类型(1,正文;2,附件)
	 */
	private String attType;

	public String getDocguid() {
		return docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileLoaction() {
		return fileLoaction;
	}

	public void setFileLoaction(String fileLoaction) {
		this.fileLoaction = fileLoaction;
	}

	public String getAttId() {
		return attId;
	}

	public void setAttId(String attId) {
		this.attId = attId;
	}

	public String getAttType() {
		return attType;
	}

	public void setAttType(String attType) {
		this.attType = attType;
	}
}
