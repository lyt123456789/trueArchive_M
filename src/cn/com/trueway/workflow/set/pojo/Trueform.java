package cn.com.trueway.workflow.set.pojo;

import java.util.List;

public class Trueform {

	private String id;
	
	private String name;
	
	private String type;
	
	private int x;
	
	private int y;
	
	private int width ;
	
	private int height;
	
	private String value;
	
	private List<KeyValue> values;
	
	private String actionurl;

	private String nodeId;
	
	private String nodeName;
	
	private int urlWidth;
	
	private int urlHeight;

	private String isbt;
	
	private String columnName;
	
	// 是否显示批阅
	private int ispy;
	
	public int getIspy() {
		return ispy;
	}

	public void setIspy(int ispy) {
		this.ispy = ispy;
	}

	private String collumnName;
	//表单元素值格式化	{{id::::value}} - {{id::::value}}
	private String valueformat;
	
	private String bindfields;
	//[string 表单元素值验证	{{id::::value}} - {{id::::value}} ]
	private String verifyformat;
	
	// json 在第几页
	private int page ;
	
	public String getIsbt() {
		return isbt;
	}
	
	private String correlation; //关联字段名称

	public void setIsbt(String isbt) {
		this.isbt = isbt;
	}

	public String getCollumnName() {
		return collumnName;
	}

	public void setCollumnName(String collumnName) {
		this.collumnName = collumnName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<KeyValue> getValues() {
		return values;
	}

	public void setValues(List<KeyValue> values) {
		this.values = values;
	}

	public String getActionurl() {
		return actionurl;
	}

	public void setActionurl(String actionurl) {
		this.actionurl = actionurl;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getValueformat() {
		return valueformat;
	}

	public void setValueformat(String valueformat) {
		this.valueformat = valueformat;
	}

	public String getBindfields() {
		return bindfields;
	}

	public void setBindfields(String bindfields) {
		this.bindfields = bindfields;
	}

	public String getVerifyformat() {
		return verifyformat;
	}

	public void setVerifyformat(String verifyformat) {
		this.verifyformat = verifyformat;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getCorrelation() {
		return correlation;
	}

	public void setCorrelation(String correlation) {
		this.correlation = correlation;
	}
	
	
}

