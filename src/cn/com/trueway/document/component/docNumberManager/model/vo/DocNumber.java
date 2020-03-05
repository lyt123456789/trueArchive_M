package cn.com.trueway.document.component.docNumberManager.model.vo;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

/**
 * 
 * 描述：TODO 对DocNumber进行描述<br>
 * 作者：张亚杰<br>
 * 创建时间：2011-12-14 上午09:22:29<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
@SqlResultSetMapping(name="DocNumVOResults", 
        entities={ 
            @EntityResult(entityClass=cn.com.trueway.document.component.docNumberManager.model.vo.DocNumber.class,
            	fields={
                	@FieldResult(name="year", column="nh"),
                	@FieldResult(name="type", column="lx"), 
                	@FieldResult(name="number", column="xh"),
                	@FieldResult(name="title", column="title"),
                	@FieldResult(name="time", column="usetime"),
                	@FieldResult(name="isused", column="isused"),
                	@FieldResult(name="lwdwlx", column="dw"),
                	@FieldResult(name="ngr", column="ngr"),
                	@FieldResult(name="security", column="miji"),
                	@FieldResult(name="ngrbm",column="ngrbm")
                })
        }
)
@Entity
public class DocNumber implements Comparable<DocNumber>{
	/**
	 * 年号
	 */
	private String year;
	/**
	 * 字号
	 */
	private String type;
	/**
	 * 序号
	 */
	@Id
	private String number;
	/**
	 * 该文标题
	 */
	private String title;
	/**
	 * 是否使用
	 */
	private String isused;
	/**
	 * 使用时间
	 */
	private Date time;
	/**
	 * 来文单位类型
	 */
	private String lwdwlx;
	/**
	 * 发文OR办文
	 */
	@Transient
	private String define;
	@Transient
	private String depGuid;
    /**
     * 拟稿人
     */
	private String ngr;
	/**
	 * 密级
	 */
	private String security;
	/**
	 * 拟稿科室
	 */
	private String ngrbm;
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLwdwlx() {
		return lwdwlx;
	}
	public void setLwdwlx(String lwdwlx) {
		this.lwdwlx = lwdwlx;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getIsused() {
		return isused;
	}
	public void setIsused(String isused) {
		this.isused = isused;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getDefine() {
		return define;
	}
	public void setDefine(String define) {
		this.define = define;
	}
	
	public String getDepGuid() {
		return depGuid;
	}
	public void setDepGuid(String depGuid) {
		this.depGuid = depGuid;
	}
	
	public String getNgr() {
		return ngr;
	}
	public void setNgr(String ngr) {
		this.ngr = ngr;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public String getNgrbm(){
		return this.ngrbm;
	}
	public void setNgrbm(String ngrbm){
		this.ngrbm=ngrbm;
	}
	public int compareTo(DocNumber o){
		String thisJgdz = this.type == null ? "" : this.type;
		String thisFwnh = this.year == null ? "" : this.year;
		String thisFwxh = this.number == null || this.number.equals("") ? "0" : this.number;
		
		String oJgdz = o.getType() == null ? "" : o.getType();
		String oFwnh = o.getYear() == null ? "" : o.getYear();
		String oFwxh = o.getNumber() == null || o.getNumber().equals("") ? "0" : o.getNumber();
		
		if (thisJgdz.equals(oJgdz)){
			if (thisFwnh.equals(oFwnh)){
				return Integer.parseInt(thisFwxh) - Integer.parseInt(oFwxh);
			}else{
				return thisFwnh.compareTo(oFwnh);
			}
		}else{
			return thisJgdz.compareTo(oJgdz);
		}
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("年号:").append(this.year).append(",字号:").append(this.type);
		if(lwdwlx!=null&&lwdwlx.trim().length()!=0){
			sb.append(",来文单位类型:").append(this.lwdwlx);
		}
		sb.append(",序号:").append(this.number).append("");
		if(this.isused!=null&&this.isused.equals("0")){
			sb.append(",使用情况:").append("未使用");
		}else if(this.isused!=null&&this.isused.equals("1")){
			sb.append(",使用情况:").append("已使用").append(",标题:").append(this.title);
		}
		return sb.toString();
	}
	
}
