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
@Table(name = "T_BTN_DIC")
public class BtnDictionary {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;
	
	@Column(name = "BTN_NAME")
	private String btn_name;
	
	@Column(name = "BTN_DESCRIPTION")
	private String btn_description;
	
	@Column(name = "BTN_SORT")
	private String btn_sort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBtn_name() {
		return btn_name;
	}

	public void setBtn_name(String btn_name) {
		this.btn_name = btn_name;
	}

	public String getBtn_description() {
		return btn_description;
	}

	public void setBtn_description(String btn_description) {
		this.btn_description = btn_description;
	}

	public String getBtn_sort() {
		return btn_sort;
	}

	public void setBtn_sort(String btn_sort) {
		this.btn_sort = btn_sort;
	}

	

	
	
	

}
