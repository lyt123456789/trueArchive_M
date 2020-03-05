package cn.com.trueway.workflow.webService.pojo;

import java.util.Date;
import java.util.List;

/**
 * webservice待收接口实体类
 * 描述：TODO 对ProBaseDoc进行描述
 * 作者：季振华
 * 创建时间：2016-12-7 下午3:51:15
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class ProBaseDoc implements java.io.Serializable {

	private static final long serialVersionUID = -6162095726761969752L;
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 实例id
	 */
	private String instanceId;
	/**
	 * 文号
	 */
	private String wh;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 主送部门id
	 */
	private String todepIds;
	/**
	 * 主送部门
	 */
	private String todepNames;
	/**
	 * 抄送部门id
	 */
	private String ccdepIds;
	/**
	 * 抄送部门
	 */
	private String ccdepNames;
	/**
	 * 发送人id
	 */
	private String senderId;
	/**
	 * 发送人姓名
	 */
	private String senderName;
	/**
	 * 发送时间
	 */
	private Date senderTime;
	
	/**
	 * 发送的部门名称
	 */
	private String sendDepName;
	
	/**
	 * 附件集合
	 */
	private List<ReceiveSendAtt> att;
	
	/**
	 * 打印份数
	 */
	private String dyfs;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTodepIds() {
		return todepIds;
	}

	public void setTodepIds(String todepIds) {
		this.todepIds = todepIds;
	}

	public String getTodepNames() {
		return todepNames;
	}

	public void setTodepNames(String todepNames) {
		this.todepNames = todepNames;
	}

	public String getCcdepIds() {
		return ccdepIds;
	}

	public void setCcdepIds(String ccdepIds) {
		this.ccdepIds = ccdepIds;
	}

	public String getCcdepNames() {
		return ccdepNames;
	}

	public void setCcdepNames(String ccdepNames) {
		this.ccdepNames = ccdepNames;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Date getSenderTime() {
		return senderTime;
	}

	public void setSenderTime(Date senderTime) {
		this.senderTime = senderTime;
	}

	public String getSendDepName() {
		return sendDepName;
	}

	public void setSendDepName(String sendDepName) {
		this.sendDepName = sendDepName;
	}

	public List<ReceiveSendAtt> getAtt() {
		return att;
	}

	public void setAtt(List<ReceiveSendAtt> att) {
		this.att = att;
	}

	public String getDyfs() {
		return dyfs;
	}

	public void setDyfs(String dyfs) {
		this.dyfs = dyfs;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	

}