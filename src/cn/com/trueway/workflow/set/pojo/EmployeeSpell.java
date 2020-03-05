package cn.com.trueway.workflow.set.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * Title: EmployeeSpell 
 * Description: 人员姓名拼音
 * 
 * @author liuyd
 * @date 2016-6-6 上午10:16:02
 */
@Entity
@Table(name = "T_SYS_EmployeeSpell")
public class EmployeeSpell implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 38)
	private String id;

	@Column(name = "employee_guid")
	private String employeeGuid;
	
	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;

	@Column(name = "spell")
	private String spell;//全拼

	@Column(name = "spellhead")
	private String spellhead;//首字母

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeGuid() {
		return employeeGuid;
	}

	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getSpellhead() {
		return spellhead;
	}

	public void setSpellhead(String spellhead) {
		this.spellhead = spellhead;
	}

}
