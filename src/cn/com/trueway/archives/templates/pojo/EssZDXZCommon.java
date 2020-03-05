package cn.com.trueway.archives.templates.pojo;

public class EssZDXZCommon {
	private String id;
	private Integer idstructure;
	private Integer idtag;
	private String tagIds;//选中字段值集合
	private Integer esorder;
	private String tagName;
	private String rule;//总排序规则
	private String ruleOneSort;//单个字段排序规则
	private String ruleFlag;//是否是自定义排序
	private Integer zeroNumber;//补零位数
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getIdstructure() {
		return idstructure;
	}
	public void setIdstructure(Integer idstructure) {
		this.idstructure = idstructure;
	}
	public Integer getIdtag() {
		return idtag;
	}
	public void setIdtag(Integer idtag) {
		this.idtag = idtag;
	}
	public String getTagIds() {
		return tagIds;
	}
	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}
	public Integer getEsorder() {
		return esorder;
	}
	public void setEsorder(Integer esorder) {
		this.esorder = esorder;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getRuleOneSort() {
		return ruleOneSort;
	}
	public void setRuleOneSort(String ruleOneSort) {
		this.ruleOneSort = ruleOneSort;
	}
	public String getRuleFlag() {
		return ruleFlag;
	}
	public void setRuleFlag(String ruleFlag) {
		this.ruleFlag = ruleFlag;
	}
	public Integer getZeroNumber() {
		return zeroNumber;
	}
	public void setZeroNumber(Integer zeroNumber) {
		this.zeroNumber = zeroNumber;
	}
	
}
