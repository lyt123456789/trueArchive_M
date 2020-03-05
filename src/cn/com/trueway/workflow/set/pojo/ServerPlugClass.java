package cn.com.trueway.workflow.set.pojo;

import java.util.List;

public class ServerPlugClass {
	private String name;
	private String zhName;
	private String jarName;
	private String classUrl;
	private String returnMethod;
	private List<ServerPlugMethod> methods;
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
	public String getJarName() {
		return jarName;
	}
	public void setJarName(String jarName) {
		this.jarName = jarName;
	}
	public String getClassUrl() {
		return classUrl;
	}
	public void setClassUrl(String classUrl) {
		this.classUrl = classUrl;
	}
	public String getReturnMethod() {
		return returnMethod;
	}
	public void setReturnMethod(String returnMethod) {
		this.returnMethod = returnMethod;
	}
	public List<ServerPlugMethod> getMethods() {
		return methods;
	}
	public void setMethods(List<ServerPlugMethod> methods) {
		this.methods = methods;
	}
	
	
}
