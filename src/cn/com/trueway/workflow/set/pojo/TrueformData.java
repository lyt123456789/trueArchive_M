package cn.com.trueway.workflow.set.pojo;

import java.util.List;

/**
 * 
 * 描述：表单填写值
 * 作者：蔡亚军
 * 创建时间：2016-12-6 下午2:13:00
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class TrueformData {
	
	private String id;			//标识id
	
	private String name;		//名称
	
	private String type;		//类别
	
	private String value;		//填写的值内容
	
	private List<KeyValue> values;

	private String actionurl;	//跳转的url地址

	private String isbt;		//是否必填
	
	private String isWrite;		//是否可以编辑(1: 表示只读);
	
	private int urlWidth;
	
	private int urlHeight;
	
	private String collumnName;
	
	private String associatedColumns;//关联必填的字段
	
	private String regularExpression;//正则表达式
	
	private String regularMeanings;	//正则表达式含义
	
	private String generationMode;//值生成模式
	
	private String zname; //字段中文名
	
	private String textMaxLen;//文本最大长度
	
	private String correlation; //关联字段名称
	
	private String isPleaseOrWatch; //是否使用请阅意见模式
	
	private String connectfield;//关联修改字段名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getActionurl() {
		return actionurl;
	}

	public void setActionurl(String actionurl) {
		this.actionurl = actionurl;
	}

	public String getIsbt() {
		return isbt;
	}

	public void setIsbt(String isbt) {
		this.isbt = isbt;
	}

	public String getIsWrite() {
		return isWrite;
	}

	public void setIsWrite(String isWrite) {
		this.isWrite = isWrite;
	}

	public int getUrlWidth() {
		return urlWidth;
	}

	public void setUrlWidth(int urlWidth) {
		this.urlWidth = urlWidth;
	}

	public int getUrlHeight() {
		return urlHeight;
	}

	public void setUrlHeight(int urlHeight) {
		this.urlHeight = urlHeight;
	}

	public List<KeyValue> getValues() {
		return values;
	}

	public void setValues(List<KeyValue> values) {
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCollumnName() {
		return collumnName;
	}

	public void setCollumnName(String collumnName) {
		this.collumnName = collumnName;
	}
	
	public String getAssociatedColumns() {
		return associatedColumns;
	}

	public void setAssociatedColumns(String associatedColumns) {
		this.associatedColumns = associatedColumns;
	}

	public String getRegularExpression() {
		return regularExpression;
	}

	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}

	public String getRegularMeanings() {
		return regularMeanings;
	}

	public void setRegularMeanings(String regularMeanings) {
		this.regularMeanings = regularMeanings;
	}

	public String getGenerationMode() {
		return generationMode;
	}

	public void setGenerationMode(String generationMode) {
		this.generationMode = generationMode;
	}

	public String getZname() {
		return zname;
	}

	public void setZname(String zname) {
		this.zname = zname;
	}

	public String getTextMaxLen() {
	    return textMaxLen;
	}

	public void setTextMaxLen(String textMaxLen) {
	    this.textMaxLen = textMaxLen;
	}

	public String getCorrelation() {
		return correlation;
	}

	public void setCorrelation(String correlation) {
		this.correlation = correlation;
	}

	public String getIsPleaseOrWatch() {
		return isPleaseOrWatch;
	}

	public void setIsPleaseOrWatch(String isPleaseOrWatch) {
		this.isPleaseOrWatch = isPleaseOrWatch;
	}

	public String getConnectfield() {
		return connectfield;
	}

	public void setConnectfield(String connectfield) {
		this.connectfield = connectfield;
	}
	
}
