package cn.com.trueway.archives.templates.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 归档树结构信息表
 * @author alike
 *
 */
@Entity
@Table(name="T_ARCHIVE_TREE")
public class EssTree {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_ARCHIVE_TREE")
	@SequenceGenerator(name="SEQ_ARCHIVE_TREE",sequenceName="SEQ_ARCHIVE_TREE",allocationSize=1)
	@Column(name="ID")
	private Integer id;//主键
	@Column(name="TITLE")
	private String title;//节点名称
	@Column(name="ESPATH")
	private String esPath;//节点PATH
	@Column(name="ID_PARENT_NODE")
	private Integer idParent;//父节点id
	@Column(name="ID_BUSINESS")
	private String idBusiness;//关联业务ID
	@Column(name="ID_STRUCTURE")
	private Integer idStructure;//关联结构ID
	@Column(name="ID_MODELSTRUCTURE")
	private Integer idModelStructure;//模板ID
	@Column(name="ESORDER")
	private Integer esOrder;//排列顺序
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
	public Integer getIdParent() {
		return idParent;
	}
	public void setIdParent(Integer idParent) {
		this.idParent = idParent;
	}
	public String getIdBusiness() {
		return idBusiness;
	}
	public void setIdBusiness(String idBusiness) {
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
	public String getEsPath() {
		return esPath;
	}
	public void setEsPath(String esPath) {
		this.esPath = esPath;
	}
	public Integer getEsOrder() {
		return esOrder;
	}
	public void setEsOrder(Integer esOrder) {
		this.esOrder = esOrder;
	}
	public EssTree() {
		super();
	}
	public EssTree(Integer id, String title, String esPath, Integer idParent, String idBusiness, Integer idStructure,
			Integer idModelStructure, Integer esOrder, Integer isLeaf, String idSeq) {
		super();
		this.id = id;
		this.title = title;
		this.esPath = esPath;
		this.idParent = idParent;
		this.idBusiness = idBusiness;
		this.idStructure = idStructure;
		this.idModelStructure = idModelStructure;
		this.esOrder = esOrder;
		this.isLeaf = isLeaf;
		this.idSeq = idSeq;
	}
	@Override
	public String toString() {
		return "EssTree [id=" + id + ", title=" + title + ", esPath=" + esPath + ", idParent=" + idParent
				+ ", idBusiness=" + idBusiness + ", idStructure=" + idStructure + ", idModelStructure="
				+ idModelStructure + ", esOrder=" + esOrder + ", isLeaf=" + isLeaf + ", idSeq=" + idSeq + "]";
	}
	
		
}
