package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_wf_core_itemrelation")
public class WfItemRelation {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;		//主键
	
	@Column(name="ITEM_ID")
	private String item_id;		//事项ID
	
	@Column(name="DELAY_ITEM_ID")
	private String delay_item_id;	//延期的事项ID
	
	@Column(name="TABLE_ID")
	private String table_id;	//对应表名
	
	@Column(name="COLOUM")
	private String coloum;		//字段英文名
	
	@Column(name="DELAY_DATE")
	private Integer delay_date;		//延期天数	
	
	@Column(name="IS_ONLY")
	private String is_only;	//是否允许多次延期
	
	@Column(name="RESERVE_VALUE")
	private String reserve_value;
	
	@Transient
	private String delay_item_name ;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getDelay_item_id() {
		return delay_item_id;
	}

	public void setDelay_item_id(String delay_item_id) {
		this.delay_item_id = delay_item_id;
	}

	public String getTable_id() {
		return table_id;
	}

	public void setTable_id(String table_id) {
		this.table_id = table_id;
	}

	public String getColoum() {
		return coloum;
	}

	public void setColoum(String coloum) {
		this.coloum = coloum;
	}

	public Integer getDelay_date() {
		return delay_date;
	}

	public void setDelay_date(Integer delay_date) {
		this.delay_date = delay_date;
	}

	public String getIs_only() {
		return is_only;
	}

	public void setIs_only(String is_only) {
		this.is_only = is_only;
	}

	public String getDelay_item_name() {
		return delay_item_name;
	}

	public void setDelay_item_name(String delay_item_name) {
		this.delay_item_name = delay_item_name;
	}

	public String getReserve_value() {
		return reserve_value;
	}

	public void setReserve_value(String reserve_value) {
		this.reserve_value = reserve_value;
	} 

}
