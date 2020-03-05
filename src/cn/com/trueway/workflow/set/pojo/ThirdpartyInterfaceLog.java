package cn.com.trueway.workflow.set.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/** 
 * ClassName: ThirdpartyInterfaceLog <br/> 
 * Function: 第三方节点调用日志实体. <br/> 
 * date: 2018年7月31日 下午3:01:20 <br/> 
 * 
 * @author adolph.jiang
 * @version  
 * @since JDK 1.6 
 */
@Entity
@Table(name = "T_WF_THIRDPARTY_INTERFACE_LOG")
public class ThirdpartyInterfaceLog {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String 	id;
	
	/**
	 * 接口地址
	 */
	@Column(name="INTERFACE_URL")
	private String 	interfaceUrl;
	
	/**
	 * 接口入参
	 */
	@Column(name="PARAMS")
	private String 	params;
	
	/**
	 * 结果
	 */
	@Column(name="RESULT")
	private String 	result;
	
	/**
	 * 请求时间
	 */
	@Column(name="REQUEST_TIME")
	private Date	requestTime;
	
	/**
	 * 回参时间
	 */
	@Column(name="BACK_TIME")
	private Date	backTime;
	
	/**
	 * 办件id
	 */
	@Column(name="INSTANCEID")
	private String instanceId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
}
