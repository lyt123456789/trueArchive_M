package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 描述：TODO 数据库字段权限设置<br>
 * 作者：zhuy<br>
 * 创建时间：2013-03-19 下午10:29:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "T_WF_CORE_FORMPERMIT")
public class WfFormPermit {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;			//主键id
	
	@Column(name = "VC_FORMID")
	private String vc_formid;		//表单ID
	
	@Column(name = "VC_TAGNAME")
	private String vc_tagname;			//标签名称
	
	@Column(name = "VC_ROLETYPE")
	private String vc_roletype;					//角色类型 4
	
	@Column(name = "VC_ROLEID")
	private String vc_roleid;		
	
	@Column(name = "VC_ROLENAME")
	private String vc_rolename;		
	
	@Column(name = "VC_LIMIT")
	private String vc_limit;			//权限，0：隐藏；1：只读；2：读写
	
	@Column(name = "I_ORDERID")
	private int i_orderid;			//排序号
	
	@Column(name = "VC_MISSIONID")
	private String vc_missionid;			//任务ID(节点Id)
	
	@Column(name = "LCID")
	private String lcid;		//流程ID
	
	@Column(name = "NODETYPE")
	private String nodetype;

	@Column(name = "ISBT")
	private String isbt;		//是否必填，1：必填，2：不必填
	
	// 是否批阅
	@Column(name = "ISPY")
	private String ispy;	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getIsbt() {
		return isbt;
	}

	public void setIsbt(String isbt) {
		this.isbt = isbt;
	}
	
	public String getVc_formid() {
		return vc_formid;
	}

	public void setVc_formid(String vcFormid) {
		vc_formid = vcFormid;
	}

	public String getVc_tagname() {
		return vc_tagname;
	}

	public void setVc_tagname(String vcTagname) {
		vc_tagname = vcTagname;
	}

	public String getVc_roletype() {
		return vc_roletype;
	}

	public void setVc_roletype(String vcRoletype) {
		vc_roletype = vcRoletype;
	}

	public String getVc_roleid() {
		return vc_roleid;
	}

	public void setVc_roleid(String vcRoleid) {
		vc_roleid = vcRoleid;
	}

	public String getVc_limit() {
		return vc_limit;
	}

	public void setVc_limit(String vcLimit) {
		vc_limit = vcLimit;
	}

	public int getI_orderid() {
		return i_orderid;
	}

	public void setI_orderid(int iOrderid) {
		i_orderid = iOrderid;
	}

	public String getVc_missionid() {
		return vc_missionid;
	}

	public void setVc_missionid(String vcMissionid) {
		vc_missionid = vcMissionid;
	}

	public String getVc_rolename() {
		return vc_rolename;
	}

	public void setVc_rolename(String vcRolename) {
		vc_rolename = vcRolename;
	}

	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public String getNodetype() {
		return nodetype;
	}

	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}

	public String getIspy() {
		return ispy;
	}

	public void setIspy(String ispy) {
		this.ispy = ispy;
	}
	
}
