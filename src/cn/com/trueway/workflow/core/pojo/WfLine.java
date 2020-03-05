package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 工作流Line表
 * @author Administrator(Feng)
 *
 */
@Entity
@Table(name="wf_line")
public class WfLine {
	/**
	 * line id
	 */
	@Id
	@Column(name="wfl_id",length=36)
	private String wfl_id;
	
	/**
	 * 固定角色
	 */
	@Column(name="wfl_fixed_role",length=100)
	private String wfl_fixed_role;
	
	/**
	 * 条件选择
	 */
	@Column(name="wfl_conditions",length=2)
	private String wfl_conditions;
	/**
	 * 上一节点
	 */
	@Column(name="wfl_xBaseMode",length=100)
	private String wfl_xBaseMode;
	/**
	 * 下一节点
	 */
	@Column(name="wfl_wBaseMode",length=100)
	private String wfl_wBaseMode;
	/**
	 * 判断箭头  0：没有，1：开始，2：结束，3：双向
	 */
	@Column(name="wfl_arrow",length=2)
	private String wfl_arrow;
	/**
	 * 路由类型 0：单人，1：并行抢占式，2：并行完全式, 3:并行结合式(相当于并行完全式(无主送抄送,最后办理的人员进入下一节点[下一节点单一且仅绑定单人]));
	 */
	@Column(name="wfl_route_type",length=2)
	private String wfl_route_type;
	
	/**
	 * 备注
	 */
	@Column(name="wfl_remark",length=100)
	private String wfl_remark;
	/**
	 * 线的前端类型
	 */
	@Column(name="WFL_XBASEMODE_TYPE",length=2)
	private String wfl_xBaseMode_type;
	/**
	 * 线的后端类型
	 */
	@Column(name="WFL_WBASEMODE_TYPE",length=2)
	private String wfl_wBaseMode_type;
	
	@Column(name="WFL_CHOICE_CONDITION")
	private String wfl_choice_condition;
	
	@Column(name="WFL_CHOICE_RULE")
	private String wfl_choice_rule;
	
	@ManyToOne(targetEntity=WfMain.class)
	@JoinColumn(name="WFL_PID")
	private WfMain wfMain;

	public String getWfl_remark() {
		return wfl_remark;
	}

	public void setWfl_remark(String wfl_remark) {
		this.wfl_remark = wfl_remark;
	}

	public String getWfl_id() {
		return wfl_id;
	}

	public void setWfl_id(String wfl_id) {
		this.wfl_id = wfl_id;
	}

	public String getWfl_fixed_role() {
		return wfl_fixed_role;
	}

	public void setWfl_fixed_role(String wfl_fixed_role) {
		this.wfl_fixed_role = wfl_fixed_role;
	}

	public String getWfl_conditions() {
		return wfl_conditions;
	}

	public void setWfl_conditions(String wfl_conditions) {
		this.wfl_conditions = wfl_conditions;
	}

	public String getWfl_xBaseMode() {
		return wfl_xBaseMode;
	}

	public void setWfl_xBaseMode(String wfl_xBaseMode) {
		this.wfl_xBaseMode = wfl_xBaseMode;
	}

	public String getWfl_wBaseMode() {
		return wfl_wBaseMode;
	}

	public void setWfl_wBaseMode(String wfl_wBaseMode) {
		this.wfl_wBaseMode = wfl_wBaseMode;
	}

	public String getWfl_arrow() {
		return wfl_arrow;
	}

	public void setWfl_arrow(String wfl_arrow) {
		this.wfl_arrow = wfl_arrow;
	}

	public String getWfl_route_type() {
		return wfl_route_type;
	}

	public void setWfl_route_type(String wfl_route_type) {
		this.wfl_route_type = wfl_route_type;
	}

	public WfMain getWfMain() {
		return wfMain;
	}

	public void setWfMain(WfMain wfMain) {
		this.wfMain = wfMain;
	}

	public String getWfl_xBaseMode_type() {
		return wfl_xBaseMode_type;
	}

	public void setWfl_xBaseMode_type(String wfl_xBaseMode_type) {
		this.wfl_xBaseMode_type = wfl_xBaseMode_type;
	}

	public String getWfl_wBaseMode_type() {
		return wfl_wBaseMode_type;
	}

	public void setWfl_wBaseMode_type(String wfl_wBaseMode_type) {
		this.wfl_wBaseMode_type = wfl_wBaseMode_type;
	}

	public String getWfl_choice_condition() {
		return wfl_choice_condition;
	}

	public void setWfl_choice_condition(String wfl_choice_condition) {
		this.wfl_choice_condition = wfl_choice_condition;
	}

	public String getWfl_choice_rule() {
		return wfl_choice_rule;
	}

	public void setWfl_choice_rule(String wfl_choice_rule) {
		this.wfl_choice_rule = wfl_choice_rule;
	}

}
