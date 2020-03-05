package cn.com.trueway.workflow.core.pojo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.com.trueway.workflow.set.pojo.ZwkjForm;

/**
 * 描述：TODO 对事项表进行描述<br>
 * 作者：zhuy<br>
 * 创建时间：2013-04-08 下午10:29:26<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
@Entity
@Table(name = "T_WF_CORE_ITEM")
public class WfItem {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;

	@Column(name="VC_SXMC")
	private String vc_sxmc;
	
	@Column(name="VC_SSBM")
	private String vc_ssbm;
	
	@Column(name="VC_SSBMID")
	private String vc_ssbmid;
	
	@Column(name="VC_WCSX")
	private String vc_wcsx;
	
	@Column(name="VC_SXLX")
	private String vc_sxlx;

	@Column(name="VC_XXLX")
	private String vc_xxlx;
	
	@Column(name="B_YJ")
	private String b_yj;
	
	@Column(name="LCID")
	private String lcid;
	
	@Column(name="RELATEDITEMID")
	private String relatedItemId;//关联事项ID
	
	@Column(name="I_INDEX")	
	private Integer index;
	
	@Transient
	private String delayItemId;
	
	@Transient
	private String delayItemName;
	
	@Column(name="ISFLEXIBLEFORM")
	private String isFlexibleForm;		//是否为弹性表单：  1：表示是
	
	@Column(name="INTERFACE_URL")
	private String interfaceUrl;
	
	@Column(name="OUTSIDE_TABNAME")
	private String outSideTabName;
	
	
	/**
	 * 事项与部门是否绑定 1：已选中
	 */
	@Transient
	private String isChecked;

	private Timestamp c_createdate;
	
	@Transient
	private List<ZwkjForm> forms;
	
	@Transient
	private List<WfNode> nodes;
	
	/**
	 * 待办根据事项排序
	 */
	@Column(name="ITEMSORT")
	private Integer itemSort;

	public String getVc_xxlx() {
		return vc_xxlx;
	}

	public void setVc_xxlx(String vc_xxlx) {
		this.vc_xxlx = vc_xxlx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVc_sxmc() {
		return vc_sxmc;
	}

	public void setVc_sxmc(String vcSxmc) {
		vc_sxmc = vcSxmc;
	}

	public String getVc_ssbm() {
		return vc_ssbm;
	}

	public void setVc_ssbm(String vcSsbm) {
		vc_ssbm = vcSsbm;
	}

	public String getVc_wcsx() {
		return vc_wcsx;
	}

	public void setVc_wcsx(String vcWcsx) {
		vc_wcsx = vcWcsx;
	}

	public String getVc_sxlx() {
		return vc_sxlx;
	}

	public void setVc_sxlx(String vcSxlx) {
		vc_sxlx = vcSxlx;
	}

	public String getB_yj() {
		return b_yj;
	}

	public void setB_yj(String bYj) {
		b_yj = bYj;
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

	public String getVc_ssbmid() {
		return vc_ssbmid;
	}

	public void setVc_ssbmid(String vcSsbmid) {
		vc_ssbmid = vcSsbmid;
	}

	public String getDelayItemId() {
		return delayItemId;
	}

	public void setDelayItemId(String delayItemId) {
		this.delayItemId = delayItemId;
	}

	public String getDelayItemName() {
		return delayItemName;
	}

	public void setDelayItemName(String delayItemName) {
		this.delayItemName = delayItemName;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getRelatedItemId() {
		return relatedItemId;
	}

	public void setRelatedItemId(String relatedItemId) {
		this.relatedItemId = relatedItemId;
	}

	public String getIsFlexibleForm() {
		return isFlexibleForm;
	}

	public void setIsFlexibleForm(String isFlexibleForm) {
		this.isFlexibleForm = isFlexibleForm;
	}

	public String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	public String getOutSideTabName() {
		return outSideTabName;
	}

	public void setOutSideTabName(String outSideTabName) {
		this.outSideTabName = outSideTabName;
	}

	public List<ZwkjForm> getForms() {
		return forms;
	}

	public void setForms(List<ZwkjForm> forms) {
		this.forms = forms;
	}

	public List<WfNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<WfNode> nodes) {
		this.nodes = nodes;
	}

	public Integer getItemSort() {
		return itemSort;
	}

	public void setItemSort(Integer itemSort) {
		this.itemSort = itemSort;
	}
	
}
