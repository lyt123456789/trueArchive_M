package cn.com.trueway.sys.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * Title: Dic
 * Description:系统设置字典表类
 *
 * @author liuyd
 * @date 2016-8-10 下午6:20:45
 */
@Entity
@Table(name = "T_SYS_DIC")
public class Dic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3442720246498547455L;
	
	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "DIC_ID", unique = true, nullable = false, length = 38)
	private String dicId;
	
	@Column(name = "DIC_NAME")
	private String dicName;
	
	@Column(name = "DIC_KEY")
	private String dicKey;
	
	@Column(name = "DIC_VALUE")
	private String dicValue;
	
	@Column(name = "DIC_REMARK")
	private String dicRemark;

	public String getDicId() {
		return dicId;
	}

	public void setDicId(String dicId) {
		this.dicId = dicId;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getDicKey() {
		return dicKey;
	}

	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}

	public String getDicValue() {
		return dicValue;
	}

	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}

	public String getDicRemark() {
		return dicRemark;
	}

	public void setDicRemark(String dicRemark) {
		this.dicRemark = dicRemark;
	}
	
}
