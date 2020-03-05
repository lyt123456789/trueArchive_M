package cn.com.trueway.workflow.core.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 描述：文件模板(正文模板、红头模板)
 * 作者：蔡亚军
 * 创建时间：2016-3-21 下午03:44:41
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "T_WF_CORE_TEMPLATE")
public class WfTemplateRed {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;				//主键id
	
	@Column(name="VC_CNAME")
	private String vc_cname;		//模板中文名称
	
	@Column(name="VC_ENAME")
	private String vc_ename;		//模板英文名称
	
	@Column(name="LX")
	private String lx;				//类型
	
	@Column(name="GWZL")
	private String gwzl;			//公文种类
	
	@Column(name="VC_PATH")
	private String vc_path;			//文件地址路径
	
	@Column(name="LCID")
	private String lcid;			//流程所属Id
	
	@Column(name="C_CREATEDATE")
	private Timestamp c_createdate;		//流程创建时间
	
	@Column(name="ISREDTAPE")
	private String isRedTape;			//是否为红头文件
	
	@Column(name="REFLCID")
	private String reflcId;				//引用的流程id
	
	@Column(name="POSITION")
	private String position;			//文件位置		//0: 文件头部;    1:文件尾部

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVc_cname() {
		return vc_cname;
	}

	public void setVc_cname(String vcCname) {
		vc_cname = vcCname;
	}

	public String getVc_ename() {
		return vc_ename;
	}

	public void setVc_ename(String vcEname) {
		vc_ename = vcEname;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getGwzl() {
		return gwzl;
	}

	public void setGwzl(String gwzl) {
		this.gwzl = gwzl;
	}

	public String getVc_path() {
		return vc_path;
	}

	public void setVc_path(String vcPath) {
		vc_path = vcPath;
	}

	public String getLcid() {
		return lcid;
	}

	public void setLcid(String lcid) {
		this.lcid = lcid;
	}

	public Timestamp getC_createdate() {
		return c_createdate;
	}

	public void setC_createdate(Timestamp cCreatedate) {
		c_createdate = cCreatedate;
	}

	public String getIsRedTape() {
		return isRedTape;
	}

	public void setIsRedTape(String isRedTape) {
		this.isRedTape = isRedTape;
	}

	public String getReflcId() {
		return reflcId;
	}

	public void setReflcId(String reflcId) {
		this.reflcId = reflcId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
