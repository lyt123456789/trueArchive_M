package cn.com.trueway.unifiledUserMng.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 资源连接类
 * @author jszw
 *
 */
public class WbApp implements Serializable{

	private static final long serialVersionUID = -6349926201212579008L;
	
	private String id;//id
	
	private String name;//资源名
	
	private String resourceType;//资源类型
	
	private String description;//描述
	
	private String 	seq;//排序号
	
	private String siteId;//站点
	
	private String status;//状态
	
	private String url;//资源地址
	
	private String pId;//父资源id
	
	private List<WbApp> wbAppList;//子资源

	public List<WbApp> getWbAppList() {
		return wbAppList;
	}

	public void setWbAppList(List<WbApp> wbAppList) {
		this.wbAppList = wbAppList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}
	
}
