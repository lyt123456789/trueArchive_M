package cn.com.trueway.workflow.set.pojo.vo;

public class SimpleDeptVo {

	private String departname;
	
	//private String unitname;
	
	private String fdeptid;
	
	private int  departindex;
	
	//private int unitindex;

	private String 	deptid;
	
	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	/*public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}*/

	public int getDepartindex() {
		return departindex;
	}

	public void setDepartindex(int departindex) {
		this.departindex = departindex;
	}

	/*public int getUnitindex() {
		return unitindex;
	}

	public void setUnitindex(int unitindex) {
		this.unitindex = unitindex;
	}
*/
	public String getDeptid() {
		return deptid;
	}

	public String getFdeptid() {
		return fdeptid;
	}

	public void setFdeptid(String fdeptid) {
		this.fdeptid = fdeptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
}
