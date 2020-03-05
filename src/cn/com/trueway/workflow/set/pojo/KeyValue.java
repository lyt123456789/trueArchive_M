package cn.com.trueway.workflow.set.pojo;

public class KeyValue{
	private String key;
	private String val;
	private String otherVal;
	
	public KeyValue(String key, String value, String otherVal) {
		this.key = key;
		this.val = value;
		this.otherVal = otherVal;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String value) {
		this.val = value;
	}
	public String getOtherVal() {
		return otherVal;
	}
	public void setOtherVal(String otherVal) {
		this.otherVal = otherVal;
	}
	
}
