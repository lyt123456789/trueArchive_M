package cn.com.trueway.workflow.log.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 * 
 * 描述：reslog的实体类
 * 作者：刘钰冬
 * 创建时间：2016-8-15 下午2:18:56
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
@Entity
@SqlResultSetMapping(name="LogResults", 
entities={ 
    @EntityResult(entityClass=cn.com.trueway.workflow.log.pojo.Log.class,
    	fields={
        	@FieldResult(name="logid", column="logid"),
        	@FieldResult(name="method", column="method"),
        	@FieldResult(name="createtime", column="createtime"),
        	@FieldResult(name="loglevel", column="loglevel"),
        	@FieldResult(name="msg", column="msg"),
        	@FieldResult(name="line", column="line"),
        	@FieldResult(name="thread", column="thread"),
        	@FieldResult(name="userid", column="userid"),
        	@FieldResult(name="username", column="username"),
        	@FieldResult(name="instanceid", column="instanceid"),
        	@FieldResult(name="prcoess_title", column="prcoess_title"),
        	@FieldResult(name="attchmentname", column="attchmentname"),
        	@FieldResult(name="attid", column="attid")
        	
        })
}
)
public class Log implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7241935945696170394L;
	
	@Id
	private String logid;
	
	private String method;
	
	private String createtime;
	
	private String loglevel;
	
	private String msg;
	
	private String line;
	
	private String thread;
	
	private String userid;
	
	private String username;
	
	private String instanceid;
	
	private String prcoess_title;		//办件标题
	
	private String attchmentname;		//附件名称
	
	private String attid;
	
	
	
	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getLoglevel() {
		return loglevel;
	}

	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}

	public String getPrcoess_title() {
		return prcoess_title;
	}

	public void setPrcoess_title(String prcoess_title) {
		this.prcoess_title = prcoess_title;
	}

	public String getAttchmentname() {
		return attchmentname;
	}
	
	public void setAttchmentname(String attchmentname) {
		this.attchmentname = attchmentname;
	}
	
	public String getAttid() {
		return attid;
	}
	
	public void setAttid(String attid) {
		this.attid = attid;
	}
 }
