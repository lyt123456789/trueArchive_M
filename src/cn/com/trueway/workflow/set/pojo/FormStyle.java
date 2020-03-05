package cn.com.trueway.workflow.set.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "t_wf_core_formStyle")
public class FormStyle implements Serializable{
	private static final long serialVersionUID = 8357222145326936654L;
	@Id
	@Column(name = "id")
	private String id;					//主键id
	
	@Column(name = "fontSize")
	private String fontSize;			//字体大小
	
	@Column(name = "verticalSpacing")
	private String verticalSpacing;		//行间距
	
	@Column(name = "dateFormat")
	private String dateFormat;			//时间格式
	
	@Column(name = "font")				//字体
	private String font;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getVerticalSpacing() {
		return verticalSpacing;
	}

	public void setVerticalSpacing(String verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}
	
}
