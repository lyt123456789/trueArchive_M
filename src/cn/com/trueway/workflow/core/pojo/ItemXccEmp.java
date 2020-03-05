package cn.com.trueway.workflow.core.pojo;

import cn.com.trueway.workflow.core.pojo.WfItem;

public class ItemXccEmp {
	private WfItem wfItem;
	
	private String empName;

	public ItemXccEmp() {
		super();
	}

	public ItemXccEmp(WfItem wfItem, String empName) {
		this.wfItem = wfItem;
		this.empName = empName;
	}

	public WfItem getWfItem() {
		return wfItem;
	}

	public void setWfItem(WfItem wfItem) {
		this.wfItem = wfItem;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
}
