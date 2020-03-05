package cn.com.trueway.workflow.core.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 文件名称： cn.com.trueway.workflow.core.pojo.EmployeeLeave.java<br/>
 * 初始作者： lihanqi<br/>
 * 创建日期： 2019-2-22<br/>
 * 功能说明： 该表是用来存放已经离职交接完的人员Id <br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 */
@Entity
@Table(name = "ZWKJ_EMPLOYEE_LEAVEID")
public class EmployeeLeave implements Serializable {
	
	private static final long serialVersionUID = -2886093820261537127L;
	
	private String id;
	private String userId;

	@GenericGenerator(name = "generator", strategy = "guid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 38)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "USERID", nullable = false, length = 38)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public EmployeeLeave() {

	}

}
