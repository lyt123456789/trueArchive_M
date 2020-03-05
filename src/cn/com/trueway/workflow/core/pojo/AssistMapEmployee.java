package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
@Entity
@SqlResultSetMapping(name="AssistMapEmployeeResults", 
        entities={ 
            @EntityResult(entityClass=cn.com.trueway.workflow.core.pojo.AssistMapEmployee.class,
            	fields={
	            	@FieldResult(name="employee_id", column="employee_id"),
					@FieldResult(name="employee_name", column="employee_name"),
					@FieldResult(name="employee_shortdn", column="employee_shortdn"),
                })
        }
)
public class AssistMapEmployee {
	@Id
	private String employee_id;//人员id
	private String employee_name;//人员姓名
	private String employee_shortdn;//人员部门结构描述

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmployee_shortdn() {
		return employee_shortdn;
	}

	public void setEmployee_shortdn(String employee_shortdn) {
		this.employee_shortdn = employee_shortdn;
	}

	
}
