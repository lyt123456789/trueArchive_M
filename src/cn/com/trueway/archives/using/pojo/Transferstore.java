package cn.com.trueway.archives.using.pojo;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_archive_transferstore")
public class Transferstore {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	private String id;//字段id
	
	@Column(name="FORMID")
	private String formId;//调卷单id
	
	@Column(name="GLID")
	private String glid;//关联档案表中的数据id
	
	@Column(name="STRUCTID")
	private String structId;//档案数据结构id
	
	@Column(name="ESPATH")
	private String esPath;//档案表中的数据地址结构
	
	@Column(name="ID_PARENT_STRUCTURE")
	private String id_parent_structure;//父结构id
	
	@Column(name="ID_PARENT_PACKAGE")
	private String id_parent_package;//
	
	@Column(name="TREE_NODEID")
	private String tree_nodeid;//数据树的节点id
	
	@Column(name="TITLE")
	private String title;//档案的标题

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGlid() {
		return glid;
	}

	public void setGlid(String glid) {
		this.glid = glid;
	}

	public String getEsPath() {
		return esPath;
	}

	public void setEsPath(String esPath) {
		this.esPath = esPath;
	}

	public String getId_parent_structure() {
		return id_parent_structure;
	}

	public void setId_parent_structure(String id_parent_structure) {
		this.id_parent_structure = id_parent_structure;
	}

	public String getId_parent_package() {
		return id_parent_package;
	}

	public void setId_parent_package(String id_parent_package) {
		this.id_parent_package = id_parent_package;
	}

	public String getTree_nodeid() {
		return tree_nodeid;
	}

	public void setTree_nodeid(String tree_nodeid) {
		this.tree_nodeid = tree_nodeid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getStructId() {
		return structId;
	}

	public void setStructId(String structId) {
		this.structId = structId;
	}
	
	
	
}
