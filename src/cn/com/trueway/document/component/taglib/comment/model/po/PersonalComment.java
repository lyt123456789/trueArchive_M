/**
 * 
 */
package cn.com.trueway.document.component.taglib.comment.model.po;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/** 
 * 描述：个人办公用语<br>
 * 20101206 Add                                  
 */
@Entity
@Table(name="WF_PERSONAL_COMMENT")
public class PersonalComment implements Serializable{
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = 2982350254071192799L;
	
	private String cmnt_id;
	private String user_id;
	private String content;
	private String sort_index;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	public String getCmnt_id() {
		return cmnt_id;
	}
	public void setCmnt_id(String cmnt_id) {
		this.cmnt_id = cmnt_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSort_index() {
		return sort_index;
	}
	public void setSort_index(String sort_index) {
		this.sort_index = sort_index;
	}
	
}
