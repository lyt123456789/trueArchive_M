package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "T_WF_CORE_PERSON_MESSAGE")
public class PersonMessage implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -3601053263282892187L;
	/**
	 * 唯一标识
	 */
	private String per_mess_id;
	/**
	 * 用户id
	 */
	private String employeeId;
	/**
	 * 事项Id
	 */
	private String itemId;
	
	/**
	 * 抄送人id
	 */
	private String xccempId;
	
	/**
	 * 抄送人name
	 */
	private String xccempName;
	
	
	public PersonMessage() {
	}

	public PersonMessage(String per_mess_id, String employeeId, String itemId,
			String xccempId, String xccempName) {
		super();
		this.per_mess_id = per_mess_id;
		this.employeeId = employeeId;
		this.itemId = itemId;
		this.xccempId = xccempId;
		this.xccempName = xccempName;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getPer_mess_id() {
		return per_mess_id;
	}
	public void setPer_mess_id(String per_mess_id) {
		this.per_mess_id = per_mess_id;
	}

	@Column(name = "EMPLOYEEID")
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "ITEMID")
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(name = "XCCEMPID")
	public String getXccempId() {
		return xccempId;
	}
	public void setXccempId(String xccempId) {
		this.xccempId = xccempId;
	}

	@Column(name = "XCCEMPNAME")
	public String getXccempName() {
		return xccempName;
	}
	public void setXccempName(String xccempName) {
		this.xccempName = xccempName;
	}
	
}