package cn.com.trueway.archives.using.pojo;

import java.util.ArrayList;

public class Usingform {

	private String id;//字段id
	
	private String name;//字段名
	
	private String fielName;//字段数据库字段名
	
	private String tableName;//应用表
	
	private String vc_height;//向下合并行
	
	private String vc_weight;//向右合并列
	
	private String vc_type;//展示形式
	
	private String vc_edit;//可编辑与否
	
	private String vc_isNull;//是否必填
	
	private String vc_val;//展示值
	
	private ArrayList<Basicdata> basicList;//关联字典数据

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

	public String getFielName() {
		return fielName;
	}

	public void setFielName(String fielName) {
		this.fielName = fielName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getVc_height() {
		return vc_height;
	}

	public void setVc_height(String vc_height) {
		this.vc_height = vc_height;
	}

	public String getVc_weight() {
		return vc_weight;
	}

	public void setVc_weight(String vc_weight) {
		this.vc_weight = vc_weight;
	}

	public String getVc_type() {
		return vc_type;
	}

	public void setVc_type(String vc_type) {
		this.vc_type = vc_type;
	}

	public ArrayList<Basicdata> getBasicList() {
		return basicList;
	}

	public void setBasicList(ArrayList<Basicdata> basicList) {
		this.basicList = basicList;
	}

	public String getVc_edit() {
		return vc_edit;
	}

	public void setVc_edit(String vc_edit) {
		this.vc_edit = vc_edit;
	}

	public String getVc_isNull() {
		return vc_isNull;
	}

	public void setVc_isNull(String vc_isNull) {
		this.vc_isNull = vc_isNull;
	}

	public String getVc_val() {
		return vc_val;
	}

	public void setVc_val(String vc_val) {
		this.vc_val = vc_val;
	}
	
	
	
}
