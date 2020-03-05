package cn.com.trueway.workflow.core.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 工作流主表
 * @author Administrator(Feng)
 *
 */
@Entity
@Table(name="wf_main")
public class WfMain {
	/**
	 * 工作流id
	 */
	@Id
	@Column(name = "wfm_id", length = 36)
	private String wfm_id;
	/**
	 * 工作流名
	 */
	@Column(name="wfm_name",length=100)
	private String wfm_name;
	/**
	 * 工作流创建时间
	 */
	@Column(name="wfm_createtime",length=100)
	private String wfm_createtime;
	/**
	 * 修改时间
	 */
	@Column(name="wfm_modifytime",length=100)
	private String wfm_modifytime;
	/**
	 * 工作日历id
	 */
	@Column(name="wfm_calendar",length=100)
	private String wfm_calendar;
	/**
	 * 缺省查询表格
	 */
	@Column(name="wfm_defaultform",length=100)
	private String wfm_defaultform;
	
	/**
	 * 流程状态
	 */
	@Column(name="wfm_status",length=2)
	private String wfm_status;
	/**
	 * 开始任务名称
	 */
	@Column(name="wfm_inittasks",length=100)
	private String wfm_inittasks;
	
	/**
	 * 流程标题
	 */
	@Column(name="wfm_title",length=100)
	private String wfm_title;
	
	
	@Column(name="wfm_title_table",length=100)
	private String wfm_title_table;
	
	@Column(name="wfm_title_column",length=100)
	private String wfm_title_column;
	
	@Column(name="wfm_title_name",length=100)
	private String wfm_title_name;
	
	@Column(name="WFM_DEPTID",length=50)
	private String wfm_deptId;
	
	/**
	 * 流程节点名称,特指(第一个节点后面携带条件的节点)
	 */
	@Column(name="WFM_NODENAME")
	private String wfm_nodeName;
	
	@OneToMany(targetEntity=WfNode.class,cascade=CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="WFN_PID")
	private Set<WfNode> node_sets=new HashSet<WfNode>();
	
	@OneToMany(targetEntity=WfLine.class,cascade=CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="WFL_PID")
	private Set<WfLine> line_sets=new HashSet<WfLine>();
	
	@OneToMany(targetEntity=WfChild.class,cascade=CascadeType.ALL)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name="WFC_PID")
	private Set<WfChild> child_sets=new HashSet<WfChild>();
	
	public String getWfm_status() {
		return wfm_status;
	}

	public void setWfm_status(String wfm_status) {
		this.wfm_status = wfm_status;
	}

	public Set<WfNode> getNode_sets() {
		return node_sets;
	}

	public void setNode_sets(Set<WfNode> node_sets) {
		this.node_sets = node_sets;
	}

	public Set<WfLine> getLine_sets() {
		return line_sets;
	}

	public void setLine_sets(Set<WfLine> line_sets) {
		this.line_sets = line_sets;
	}
	
	public String getWfm_id() {
		return wfm_id;
	}

	public void setWfm_id(String wfm_id) {
		this.wfm_id = wfm_id;
	}

	public String getWfm_name() {
		return wfm_name;
	}

	public void setWfm_name(String wfm_name) {
		this.wfm_name = wfm_name;
	}

	public String getWfm_createtime() {
		return wfm_createtime;
	}

	public void setWfm_createtime(String wfm_createtime) {
		this.wfm_createtime = wfm_createtime;
	}

	public String getWfm_modifytime() {
		return wfm_modifytime;
	}

	public void setWfm_modifytime(String wfm_modifytime) {
		this.wfm_modifytime = wfm_modifytime;
	}

	public String getWfm_calendar() {
		return wfm_calendar;
	}

	public void setWfm_calendar(String wfm_calendar) {
		this.wfm_calendar = wfm_calendar;
	}

	public String getWfm_defaultform() {
		return wfm_defaultform;
	}

	public void setWfm_defaultform(String wfm_defaultform) {
		this.wfm_defaultform = wfm_defaultform;
	}

	public String getWfm_inittasks() {
		return wfm_inittasks;
	}

	public void setWfm_inittasks(String wfm_inittasks) {
		this.wfm_inittasks = wfm_inittasks;
	}

	public String getWfm_title_table() {
		return wfm_title_table;
	}

	public void setWfm_title_table(String wfm_title_table) {
		this.wfm_title_table = wfm_title_table;
	}

	public String getWfm_title_column() {
		return wfm_title_column;
	}

	public void setWfm_title_column(String wfm_title_column) {
		this.wfm_title_column = wfm_title_column;
	}

	public String getWfm_title() {
		return wfm_title;
	}

	public void setWfm_title(String wfm_title) {
		this.wfm_title = wfm_title;
	}

	public String getWfm_title_name() {
		return wfm_title_name;
	}

	public void setWfm_title_name(String wfm_title_name) {
		this.wfm_title_name = wfm_title_name;
	}

	public String getWfm_nodeName() {
		return wfm_nodeName;
	}

	public void setWfm_nodeName(String wfm_nodeName) {
		this.wfm_nodeName = wfm_nodeName;
	}

	public Set<WfChild> getChild_sets() {
		return child_sets;
	}

	public void setChild_sets(Set<WfChild> child_sets) {
		this.child_sets = child_sets;
	}

	public String getWfm_deptId() {
		return wfm_deptId;
	}

	public void setWfm_deptId(String wfm_deptId) {
		this.wfm_deptId = wfm_deptId;
	}
}
