package cn.com.trueway.workflow.core.pojo.vo;

/**
 * 用于移动端的表单临时类
 * @author Administrator
 *
 */
public class FormElement {
	
	private String name; //标签名字
	
	private String chinese;//中文
	
	private String type;  //标签类型--复选框
	
	private String value; //标签的值
	
	private String limit; //权限 ---0:隐藏,1:只读,2:读写
	
	private String db_type; // 数据库字段类型
	
	public FormElement() {
		
	}

	public FormElement(String name, String chinese, String type, String value,
			String limit, String db_type) {
		super();
		this.name = name;
		this.chinese = chinese;
		this.type = type;
		this.value = value;
		this.limit = limit;
		this.db_type = db_type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getChinese() {
		return chinese;
	}
	public void setChinese(String chinese) {
		this.chinese = chinese;
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

	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getDb_type() {
		return db_type;
	}
	public void setDb_type(String db_type) {
		this.db_type = db_type;
	}

}
