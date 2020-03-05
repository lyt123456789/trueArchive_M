package cn.com.trueway.unifiledUserMng.entity;

import java.io.Serializable;

/**
 * 角色中部门类
 * @author jszw
 *
 */
public class DeptBO implements Serializable{

	private static final long serialVersionUID = 6254577112477093831L;

	private String id;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
