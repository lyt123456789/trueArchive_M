package cn.com.trueway.workflow.set.pojo;

import java.util.List;


public class ServerPlugMethod {
	private String id;
	private String name;
	private String zhName;
	private List<ServerPlugParam> inputs;
	private List<ServerPlugParam> outs;
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
	public String getZhName() {
		return zhName;
	}
	public void setZhName(String zhName) {
		this.zhName = zhName;
	}
	public List<ServerPlugParam> getInputs() {
		return inputs;
	}
	public void setInputs(List<ServerPlugParam> inputs) {
		this.inputs = inputs;
	}
	public List<ServerPlugParam> getOuts() {
		return outs;
	}
	public void setOuts(List<ServerPlugParam> outs) {
		this.outs = outs;
	}
	
	
}
