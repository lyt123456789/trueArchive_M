package cn.com.trueway.workflow.set.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "T_WF_CORE_SERVER_PLUGIN")
public class ServerPlugin {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id",length = 36)
	private String id;//主键
	
	@Column(name = "CNAME", nullable = true)
	private String cname;//服务器插件中文名称
	
	@Column(name = "XML_URL", nullable = true)
	private String xml_url;//xml路径
	
	@Column(name = "XML_NAME", nullable = true)
	private String xml_name;//xml信息文件名称
	
	@Column(name = "JAR_URL", nullable = true)
	private String jar_url;//jar路径
	
	@Column(name = "JAR_NAME", nullable = true)
	private String jar_name;//jar包名称
	
	@Column(name = "INTIME", nullable = true)
	private Timestamp intime;//入库时间
	
	@Column(name = "INPERSON", nullable = true)
	private String inperson;//入库人
	
	@Column(name = "UPDATETIME", nullable = true)
	private Timestamp updatetime;//更新时间
	
	@Column(name = "UPDATEPERSON", nullable = true)
	private String updateperson;//更新人
	
	@Column(name = "ZINDEX", nullable = true)
	private Integer zindex;//排序号
	
	@Transient
	private ServerPlugClass serverPlugClass;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getXml_name() {
		return xml_name;
	}
	public void setXml_name(String xml_name) {
		this.xml_name = xml_name;
	}
	public String getJar_name() {
		return jar_name;
	}
	public void setJar_name(String jar_name) {
		this.jar_name = jar_name;
	}
	public Timestamp getIntime() {
		return intime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
	public String getInperson() {
		return inperson;
	}
	public void setInperson(String inperson) {
		this.inperson = inperson;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateperson() {
		return updateperson;
	}
	public void setUpdateperson(String updateperson) {
		this.updateperson = updateperson;
	}
	public Integer getZindex() {
		return zindex;
	}
	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}
	public String getXml_url() {
		return xml_url;
	}
	public void setXml_url(String xml_url) {
		this.xml_url = xml_url;
	}
	public String getJar_url() {
		return jar_url;
	}
	public void setJar_url(String jar_url) {
		this.jar_url = jar_url;
	}
	public ServerPlugClass getServerPlugClass() {
		return serverPlugClass;
	}
	public void setServerPlugClass(ServerPlugClass serverPlugClass) {
		this.serverPlugClass = serverPlugClass;
	}
	
	
}
