package cn.com.trueway.workflow.set.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "T_WF_CORE_FORM_MAP_COLUMN")
public class FormTagMapColumn {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID",length = 36)
	private String id;//主键
	
	@Column(name = "FORMID", nullable = true)
	private String formid;//表单id
	
	@Column(name = "FORMTAGNAME", nullable = true)
	private String formtagname;//表单标签名称
	
	@Column(name = "FORMTAGTYPE", nullable = true)
	private String formtagtype;//表单标签类型
	
	@Column(name = "TAGTYPE", nullable = true)
	private String tagtype;//自定义标签类型
	
	@Column(name = "TABLENAME", nullable = true)
	private String tablename;//对应表名
	
	@Column(name = "COLUMNNAME", nullable = true)
	private String columnname;//对应字段
	
	@Column(name = "DICDATA", nullable = true)
	private String dicdata;//数据字典
	
	@Column(name = "OTHERDATA", nullable = true)
	private String otherData;//其它属性
	
	@Column(name = "SORT_INDEX", nullable = true)
	private Integer sortIndex;//排序号
	
	@Column(name = "selectDic", nullable = true)
	private String selectDic;//下拉框关联字典表名称
	
	@Column(name = "LISTID", nullable = true)
	private String listId;//列表字段标识
	
	@Column(name = "COLUMNCNAME", nullable = true)
	private String columnCname;//字段中文注释
	
	@Column(name = "DOC_COLUMN", nullable = true)
	private String docColumn;//公文交换平台对象属性
	
	@Column(name = "SERVERPLUGIN_ID", nullable = true)
	private String serverPlugin_id;//服务器插件id
	
	@Column(name = "GET_WAY", nullable = true)
	private String get_way;//获取方式 0 代表唯一，1代表默认
	
	@Column(name = "TRUEAREA", nullable = true)
	private String trueArea;	//是否为意见签批区域
	
	@Column(name = "ISPLEASEORWATCH", nullable = true)
	private String isPleaseOrWatch;//是否使用请阅意见模式
	
	@Column(name = "ASSIGNTABLENAME", nullable = true)
	private String assignTableName;	//赋值对应表名
	
	@Column(name = "ASSIGNCOLUMNNAM", nullable = true)
	private String assignColumnName;	//赋值对应字段
	
	@Column(name = "CONSTANTVALUE", nullable = true)
	private String constantValue;	//默认值
	
	@Column(name = "VALUEFORMAT", nullable = true)
	private String valueformat;	//表单元素值格式化
	
	@Column(name = "BINDFIELDS", nullable = true)
	private String bindfields;	//绑定字段
	
	@Column(name = "TEXTMAXLEN", nullable = true)
	private String textMaxLen;	//文本最大长度
	
	@Column(name = "VERIFYFORMAT", nullable = true)
	private String verifyformat;	//表单元素值验证

	@Column(name = "ASSOCIATED_COLUMNS", nullable = true)
	private String associatedColumns;	//关联必填的字段
	
	@Column(name = "REGULAR_EXPRESSION", nullable = true)
	private String regularExpression;	//正则表达式
	
	@Column(name = "REGULAR_MEANINGS", nullable = true)
	private String regularMeanings;	//正则表达式含义
	
	@Column(name = "GENERATION_MODE", nullable = true)
	private String generationMode;	//值生成模式
	
	@Column(name = "CORRELATION")
	private String correlation; //关联字段名称
	
	@Column(name = "CONNECTFIELD")
	private String connectfield; //关联修改字段名称
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFormid() {
		return formid;
	}
	public void setFormid(String formid) {
		this.formid = formid;
	}
	public String getFormtagname() {
		return formtagname;
	}
	public void setFormtagname(String formtagname) {
		this.formtagname = formtagname;
	}
	public String getFormtagtype() {
		return formtagtype;
	}
	public void setFormtagtype(String formtagtype) {
		this.formtagtype = formtagtype;
	}
	public String getTagtype() {
		return tagtype;
	}
	public void setTagtype(String tagtype) {
		this.tagtype = tagtype;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getDicdata() {
		return dicdata;
	}
	public void setDicdata(String dicdata) {
		this.dicdata = dicdata;
	}
	public String getOtherData() {
		return otherData;
	}
	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}
	public Integer getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	public String getSelectDic() {
		return selectDic;
	}
	public void setSelectDic(String selectDic) {
		this.selectDic = selectDic;
	}
	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getColumnCname() {
		return columnCname;
	}
	public void setColumnCname(String columnCname) {
		this.columnCname = columnCname;
	}
	public String getDocColumn() {
		return docColumn;
	}
	public void setDocColumn(String docColumn) {
		this.docColumn = docColumn;
	}
	public String getServerPlugin_id() {
		return serverPlugin_id;
	}
	public void setServerPlugin_id(String serverPlugin_id) {
		this.serverPlugin_id = serverPlugin_id;
	}
	public String getGet_way() {
		return get_way;
	}
	public void setGet_way(String get_way) {
		this.get_way = get_way;
	}
	public String getAssignTableName() {
		return assignTableName;
	}
	public void setAssignTableName(String assignTableName) {
		this.assignTableName = assignTableName;
	}
	public String getAssignColumnName() {
		return assignColumnName;
	}
	public void setAssignColumnName(String assignColumnName) {
		this.assignColumnName = assignColumnName;
	}
	public String getConstantValue() {
		return constantValue;
	}
	public void setConstantValue(String constantValue) {
		this.constantValue = constantValue;
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
	public String getTrueArea() {
		return trueArea;
	}
	public void setTrueArea(String trueArea) {
		this.trueArea = trueArea;
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