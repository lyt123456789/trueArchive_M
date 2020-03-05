package cn.com.trueway.workflow.core.pojo.vo;
/**
 * 文件名称： cn.com.trueway.workflow.core.pojo.vo.RolePojo.java<br/>
 * 初始作者： lihanqi<br/>
 * 创建日期： 2019-4-24<br/>
 * 功能说明： 这里用一句话描述这个类的作用--此句话需删除 <br/>  
 * 修改记录：<br/> 
 * 修改作者        日期       修改内容<br/> 
 */
public class RolePojo {
	private String roleId;
	private String roleName;
	private String roleUserIds;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleUserIds() {
		return roleUserIds;
	}
	public void setRoleUserIds(String roleUserIds) {
		this.roleUserIds = roleUserIds;
	}
}
