package cn.com.trueway.workflow.core.pojo.vo;

public class FieldSelectCondition {


	// 预编译 :name
	private String name;
	// 查询条件 like = 1, 等于 = 0
	private int type ;
	
	private String value ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
