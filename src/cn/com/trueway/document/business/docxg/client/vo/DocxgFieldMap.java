package cn.com.trueway.document.business.docxg.client.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 公文交换元素字段匹配关系
 * @author caiyj
 *
 */
@Entity
@Table(name = "DOCEXCHANGE_DOCXGFIELDMAP")
public class DocxgFieldMap {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;
	
	/**
	 * 事项id
	 */
	@Column(name = "ITEMID")
	private String itemId;
	
	/**
	 * 公文交换字段
	 */
	@Column(name = "TAGNAME")
	private String tagName;
	
	/**
	 *公文交换字段说明 
	 */
	@Column(name = "TAGNDESC")
	private String tagDesc;
	
	/**
	 * 对应的表名
	 */
	@Column(name = "TABLENAME")
	private String tableName;
	
	/**
	 * 对应的字段名
	 */
	@Column(name = "FIELDNAME")
	private String fieldName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTagDesc() {
		return tagDesc;
	}

	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}
	
}
