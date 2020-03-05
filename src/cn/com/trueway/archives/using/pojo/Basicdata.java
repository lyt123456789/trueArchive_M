package cn.com.trueway.archives.using.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 	借阅单、借阅库数据字典表
 * @author hw
 *
 */
@Entity
@Table(name = "T_USING_BASICDATA")
public class Basicdata {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;
	
	@Column(name="DATANAME")
	private String dataName;//页面展示值
	
	@Column(name="FEILNAME")
	private String feilName;//数据库展示值
	
	@Column(name="TYPE")
	private String type;//隶属类别
	
	@Column(name="TYPENAME")
	private String typeName;//类别名称
	
	@Column(name="NUMINDEX")
	private String numIndex;//排序
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getFeilName() {
		return feilName;
	}

	public void setFeilName(String feilName) {
		this.feilName = feilName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumIndex() {
		return numIndex;
	}

	public void setNumIndex(String numIndex) {
		this.numIndex = numIndex;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	

	
}
