package cn.com.trueway.archives.manage.pojo;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_ARCHIVE_ROLE_BTN")
public class BtnOfRole {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;
	
	@Column(name = "ROLEID")
	private String roleId;
	
	@Column(name = "BTN_IDS")
	private String btn_ids;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getBtn_ids() {
		return btn_ids;
	}

	public void setBtn_ids(String btn_ids) {
		this.btn_ids = btn_ids;
	}

	
	
	

}
