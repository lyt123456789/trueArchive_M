package cn.com.trueway.unifiledUserMng.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReturnRole implements Serializable{

	private static final long serialVersionUID = -9133961234331182331L;
	
	private String id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 排序号
     */
    private Integer seq;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态 0：禁用 1：启用
     */
    private Integer status;
    
    /**
     * 地区ID
     */
    private String appId;
    
    /**
     * 站点id
     */
    private String siteId;
    
    
    /**
     * 是否默认 0：否 1：是
     */
    private Integer ifDefault;
    
    private String deptName;
    
    private String deptIds;
    
    private String userName;
    
    private String userIds;
    
    private List<UserBO> userList;
    
    private List<DeptBO> deptList;
    
    private List<WbApp> wbAppList;
    
    private List<WbApp> portalAppList;//门户资源列表(resourcetype=1)
	
    private List<WbApp> oaAppList;//OA资源列表(resourcetype=2)
	
    private List<WbApp> wfAppList;//流程资源列表(resourcetype=3)
    
    private String resIds;
    
    private String portalResIds;
    
    private String oaResIds;
    
    private String wfResIds;

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

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Integer getIfDefault() {
		return ifDefault;
	}

	public void setIfDefault(Integer ifDefault) {
		this.ifDefault = ifDefault;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public List<UserBO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserBO> userList) {
		this.userList = userList;
	}

	public List<DeptBO> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<DeptBO> deptList) {
		this.deptList = deptList;
	}

	public List<WbApp> getWbAppList() {
		return wbAppList;
	}

	public void setWbAppList(List<WbApp> wbAppList) {
		this.wbAppList = wbAppList;
	}

	public List<WbApp> getPortalAppList() {
		return portalAppList;
	}

	public void setPortalAppList(List<WbApp> portalAppList) {
		this.portalAppList = portalAppList;
	}

	public List<WbApp> getOaAppList() {
		return oaAppList;
	}

	public void setOaAppList(List<WbApp> oaAppList) {
		this.oaAppList = oaAppList;
	}

	public List<WbApp> getWfAppList() {
		return wfAppList;
	}

	public void setWfAppList(List<WbApp> wfAppList) {
		this.wfAppList = wfAppList;
	}

	public String getResIds() {
		return resIds;
	}

	public void setResIds(String resIds) {
		this.resIds = resIds;
	}

	public String getPortalResIds() {
		return portalResIds;
	}

	public void setPortalResIds(String portalResIds) {
		this.portalResIds = portalResIds;
	}

	public String getOaResIds() {
		return oaResIds;
	}

	public void setOaResIds(String oaResIds) {
		this.oaResIds = oaResIds;
	}

	public String getWfResIds() {
		return wfResIds;
	}

	public void setWfResIds(String wfResIds) {
		this.wfResIds = wfResIds;
	}
}
