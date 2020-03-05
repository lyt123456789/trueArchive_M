package cn.com.trueway.workflow.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 路由实体
 * @author xuxh
 *
 */
@Entity
@Table(name = "WF_SoftRoute")
public class SoftRoute implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -960068532982327058L;
	// Fields
	/**
	 * 唯一标识
	 */
	@Id
	@Column(name = "uuid", length = 36)
	private String uuid;
	
	/**
	 * 对应流程的defineId
	 */
	@Column(name = "defineId")
	private String defineId;
	
	/**
	 * 路由起点 节点id
	 */
	@Column(name = "startNodeId")
	private String startNodeId;
	/**
	 * 路由终点 节点id
	 */
	@Column(name = "endNodeId")
	private String endNodeId;
	/**
	 * 路由类型：
	 * 1 单人
	 * 2顺序串行
	 * 3无序串行
	 * 4并行阅办
	 * 5发散处理
	 * 6抢占式处理
	 * 7用户自选
	 */
	@Column(name = "routeType")
	private int routeType;
	
	/**
	 * 是否反向路由 0否 1是
	 */
	@Column(name = "reverseRoute")
	private int reverseRoute;

	/**
	 * 预定承办人 0否 1是
	 */
	@Column(name = "scheduleUndertaker")
	private int scheduleUndertaker;
	// Constructors

	/** default constructor */
	public SoftRoute() {
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStartNodeId() {
		return startNodeId;
	}

	public void setStartNodeId(String startNodeId) {
		this.startNodeId = startNodeId;
	}

	public String getEndNodeId() {
		return endNodeId;
	}

	public void setEndNodeId(String endNodeId) {
		this.endNodeId = endNodeId;
	}

	public int getRouteType() {
		return routeType;
	}

	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}

	public int getReverseRoute() {
		return reverseRoute;
	}

	public void setReverseRoute(int reverseRoute) {
		this.reverseRoute = reverseRoute;
	}

	public int getScheduleUndertaker() {
		return scheduleUndertaker;
	}

	public void setScheduleUndertaker(int scheduleUndertaker) {
		this.scheduleUndertaker = scheduleUndertaker;
	}

	public String getDefineId() {
		return defineId;
	}

	public void setDefineId(String defineId) {
		this.defineId = defineId;
	}


}
