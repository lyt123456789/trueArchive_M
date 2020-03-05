package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 * 文件名称： cn.com.trueway.workflow.core.pojo.OfficeInfoView.java<br/>
 * 初始作者： lihanqi<br/>
 * 创建日期： 2018-11-14<br/>
 * 功能说明： 二维码相关业务数据实体类 <br/>
 * 修改记录：<br/>
 * 修改作者 日期 修改内容<br/>
 */
@Entity
@SqlResultSetMapping(name = "OfficeInfoView", entities = { @EntityResult(entityClass = cn.com.trueway.workflow.core.pojo.OfficeInfoView.class, fields = {
		@FieldResult(name = "instanceId", column = "instanceId"),
		@FieldResult(name = "dwmc", column = "dwmc"),
		@FieldResult(name = "wjmc", column = "wjmc"),
		@FieldResult(name = "ngr", column = "ngr"),
		@FieldResult(name = "yfsj", column = "yfsj"),
		@FieldResult(name = "wh", column = "wh")
}) })
public class OfficeInfoView {
	@Id
	private String instanceId; // 实例id
	private String dwmc; // 发改委单位名称 ;
	private String wjmc;// 文件名称 ;
	private String ngr; // 拟稿人 ;
	private String yfsj;// 印发时间 ;
	private String wh; // 文号 ;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getWjmc() {
		return wjmc;
	}

	public void setWjmc(String wjmc) {
		this.wjmc = wjmc;
	}

	public String getNgr() {
		return ngr;
	}

	public void setNgr(String ngr) {
		this.ngr = ngr;
	}

	public String getYfsj() {
		return yfsj;
	}

	public void setYfsj(String yfsj) {
		this.yfsj = yfsj;
	}

	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}
}
