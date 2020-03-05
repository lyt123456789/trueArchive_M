package cn.com.trueway.sys.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_SYS_FEEDBACK")
public class FeedBack {
	
	/**
	 * 
	 */
	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 38)
	private String id;
	
	/**
	 * 反馈内容
	 */
	@Column(name="CONTENT")
	private String content;
	
	/**
	 * 反馈时间
	 */
	@Column(name="N_TIME")
	private Date n_time;
	
	/**
	 * 反馈人id
	 */
	@Column(name="PUBLISHER_ID")
	private String publisher_id;
	
	/**
	 * 设备类型
	 */
	@Column(name="DEVICE")
	private String device;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getN_time() {
		return n_time;
	}

	public void setN_time(Date n_time) {
		this.n_time = n_time;
	}

	public String getPublisher_id() {
		return publisher_id;
	}

	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
}
