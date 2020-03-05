package cn.com.trueway.archiveModel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 归档树结构信息表
 * @author alike
 *
 */
@Entity
@Table(name="ESS_TREE")
public class EssTree {
	@Id
	@Column(name="ID")
	private Integer id;//主键
	@Column(name="TITLE")
	private String title;//节点名称
	@Column(name="ESPATH")
	private String espath;//节点PATH
	@Column(name="ID_PARENT_NODE")
	private Integer idParent;//父节点id
	@Column(name="ID_BUSINESS")
	private Integer idBusiness;//关联业务ID
	@Column(name="ID_STRUCTURE")
	private Integer idStructure;//关联结构ID
	@Column(name="ID_MODELSTRUCTURE")
	private Integer idModelStructure;//模板ID
	@Column(name="ESORDER")
	private Integer esorder;//排列顺序
	@Column(name="ISLEAF")
	private Integer isLeaf;//是否为叶子节点
	@Column(name="ID_SEQ")
	private String idSeq;//节点路径
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEspath() {
		return espath;
	}
	public void setEspath(String espath) {
		this.espath = espath;
	}
	public Integer getIdParent() {
		return idParent;
	}
	public void setIdParent(Integer idParent) {
		this.idParent = idParent;
	}
	public Integer getIdBusiness() {
		return idBusiness;
	}
	public void setIdBusiness(Integer idBusiness) {
		this.idBusiness = idBusiness;
	}
	public Integer getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
	public Integer getIdModelStructure() {
		return idModelStructure;
	}
	public void setIdModelStructure(Integer idModelStructure) {
		this.idModelStructure = idModelStructure;
	}
	public Integer getEsorder() {
		return esorder;
	}
	public void setEsorder(Integer esorder) {
		this.esorder = esorder;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getIdSeq() {
		return idSeq;
	}
	public void setIdSeq(String idSeq) {
		this.idSeq = idSeq;
	}
	
	public EssTree() {
		super();
	}
	public EssTree(Integer id, String title, String espath, Integer idParent, Integer idBusiness, Integer idStructure,
			Integer idModelStructure, Integer esorder, Integer isLeaf, String idSeq) {
		super();
		this.id = id;
		this.title = title;
		this.espath = espath;
		this.idParent = idParent;
		this.idBusiness = idBusiness;
		this.idStructure = idStructure;
		this.idModelStructure = idModelStructure;
		this.esorder = esorder;
		this.isLeaf = isLeaf;
		this.idSeq = idSeq;
	}
	@Override
	public String toString() {
		return "EssTree [id=" + id + ", title=" + title + ", espath=" + espath + ", idParent=" + idParent
				+ ", idBusiness=" + idBusiness + ", idStructure=" + idStructure + ", idModelStructure="
				+ idModelStructure + ", esorder=" + esorder + ", isLeaf=" + isLeaf + ", idSeq=" + idSeq + "]";
	}
	
		
}
