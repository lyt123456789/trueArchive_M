/**
 * 文件名称:UserVo.java
 * 作者:吴新星<br>
 * 创建时间:2011-12-13 下午06:18:23
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.example.model.vo;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 * 描述：TODO 对UserVo进行描述<br>
 * 作者：吴新星<br>
 * 创建时间：2011-12-13 下午06:18:23<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
@SqlResultSetMapping(name="UserVOResults", 
        entities={ 
            @EntityResult(entityClass=cn.com.trueway.document.example.model.vo.UserVO.class, 
            	fields={
                	@FieldResult(name="voUserId", column="userid"),
                	@FieldResult(name="voUserName", column="username"), 
                	@FieldResult(name="voSex", column="sex"),
                	@FieldResult(name="voImg", column="empimg"),
                	@FieldResult(name="voDesc", column="empdesc")
                })
        }
)
@Entity
public class UserVO {
	@Id
	private String voUserId;
	private String voUserName;
	private String voSex;
	private byte[] voImg;
	private String voDesc;
	
	
	
	/**
	 * @return the voUserId
	 */
	public String getVoUserId() {
		return voUserId;
	}
	/**
	 * @param voUserId the voUserId to set
	 */
	public void setVoUserId(String voUserId) {
		this.voUserId = voUserId;
	}
	/**
	 * @return the voUserName
	 */
	public String getVoUserName() {
		return voUserName;
	}
	/**
	 * @param voUserName the voUserName to set
	 */
	public void setVoUserName(String voUserName) {
		this.voUserName = voUserName;
	}
	/**
	 * @return the voSex
	 */
	public String getVoSex() {
		return voSex;
	}
	/**
	 * @param voSex the voSex to set
	 */
	public void setVoSex(String voSex) {
		this.voSex = voSex;
	}
	/**
	 * @return the voDesc
	 */
	public String getVoDesc() {
		return voDesc;
	}
	/**
	 * @param voDesc the voDesc to set
	 */
	public void setVoDesc(String voDesc) {
		this.voDesc = voDesc;
	}
	/**
	 * @return the voImg
	 */
	public byte[] getVoImg() {
		return voImg;
	}
	/**
	 * @param voImg the voImg to set
	 */
	public void setVoImg(byte[] voImg) {
		this.voImg = voImg;
	}
	/**
	 * @return
	 */
	@Override
	public String toString() {
		return new StringBuilder()
			.append("|userId: ").append(this.getVoUserId())
			.append("|usrName: ").append(this.getVoUserName())
			.append("|sex: ").append(this.getVoSex())
			.append("|empImg: ").append(new String(this.getVoImg()))
			.append("|empDesc: ").append(this.getVoDesc())
			.toString();
	}
}
