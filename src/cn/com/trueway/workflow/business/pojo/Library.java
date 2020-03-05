package cn.com.trueway.workflow.business.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@Entity
@SqlResultSetMapping(name="LibraryResults", 
        entities={ 
            @EntityResult(entityClass=cn.com.trueway.workflow.business.pojo.Library.class,
            	fields={
	            	@FieldResult(name="instanceId", column="instanceId"),
					@FieldResult(name="title", column="title"),
					@FieldResult(name="lwh", column="lwh"),
					@FieldResult(name="lwdw", column="lwdw"),
					@FieldResult(name="lwsj", column="lwsj"),
					@FieldResult(name="type", column="type"),
					@FieldResult(name="zsdw", column="zsdw"),
					@FieldResult(name="csdw", column="csdw"),
					@FieldResult(name="itemName", column="itemName")
                })
        }
)
public class Library {
	
	@Id
	private String instanceId;		//实例Id
	
	private String title;			//来文标题
	
	private String lwh;			//来文号位
	
	private String lwdw;			//来文单位
	
	private Date lwsj;				//来文时间
	
	private String type;			//类型： 1、发文; 2:收文
	
	private String zsdw;			//主送单位
	
	private String csdw;			//抄送单位
	
	private String itemName;		//事项名称
	
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceid(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLwh() {
		return lwh;
	}

	public void setLwh(String lwh) {
		this.lwh = lwh;
	}

	public String getLwdw() {
		return lwdw;
	}

	public void setLwdw(String lwdw) {
		this.lwdw = lwdw;
	}

	public Date getLwsj() {
		return lwsj;
	}

	public void setLwsj(Date lwsj) {
		this.lwsj = lwsj;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getZsdw() {
		return zsdw;
	}

	public void setZsdw(String zsdw) {
		this.zsdw = zsdw;
	}

	public String getCsdw() {
		return csdw;
	}

	public void setCsdw(String csdw) {
		this.csdw = csdw;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
}
