package cn.com.trueway.workflow.core.pojo;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_DOFILE_FAVOURITE")
public class DofileFavourite {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;
	
	@Column(name="DOFILEID")
	private String dofileId;
	
	@Column(name="FAVOURITETIME")
	private Date  favouriteTime;
	
	@Column(name="USERID")
	private String userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDofileId() {
		return dofileId;
	}

	public void setDofileId(String dofileId) {
		this.dofileId = dofileId;
	}

	public Date getFavouriteTime() {
		return favouriteTime;
	}

	public void setFavouriteTime(Date favouriteTime) {
		this.favouriteTime = favouriteTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
