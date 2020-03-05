package cn.com.trueway.workflow.webService.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_WEBSERVICEINFO")
public class ExchangeInfo implements Serializable {

	private static final long serialVersionUID = 2260271539347148800L;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;			//主键
	
	@Column(name="XML")
	private String xml;        //clob字段,接收到的文件信息
	
	@Column(name="REC_TIME")
	private Date rec_time;		//接收数据的时间
	
	@Column(name="STATUS")
	private String status;		//转换为待办是否成功
	
	@Column(name="BT")
	private String bt;			//标题

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Date getRec_time() {
		return rec_time;
	}

	public void setRec_time(Date rec_time) {
		this.rec_time = rec_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBt() {
		return bt;
	}

	public void setBt(String bt) {
		this.bt = bt;
	}

}
