package cn.com.trueway.document.business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 描述：收文时, 多个公文交换元素对应一个数据表字段(文号为列)
 * 作者：蔡亚军
 * 创建时间：2015-1-15 下午3:50:48
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@Table(name = "OA_DOC_RECWH")
public class RecWh{
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	private String id;		//主键id
	
	@Column(name = "ITEMID")
	private String itemId;		//事项id
	
	@Column(name = "TABLEID")
	private String tableId;		//表id
	
	@Column(name = "FIELDID")
	private String fieldId;		//字段id
	
	@Column(name = "CONTENT")
	private String content;		//组织内容

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

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
