package cn.com.trueway.workflow.set.pojo.vo;

import java.util.List;

public class SimpleDeptAndEmpVo {

	private List<SimpleDeptVo> depts;
	
	private List<SimpleEmpVo> emps;
	
	private int vid;
	
	private int cid;

	public List<SimpleDeptVo> getDepts() {
		return depts;
	}


	public int getVid() {
		return vid;
	}


	public void setVid(int vid) {
		this.vid = vid;
	}


	public int getCid() {
		return cid;
	}


	public void setCid(int cid) {
		this.cid = cid;
	}


	public void setDepts(List<SimpleDeptVo> depts) {
		this.depts = depts;
	}

	public List<SimpleEmpVo> getEmps() {
		return emps;
	}

	public void setEmps(List<SimpleEmpVo> emps) {
		this.emps = emps;
	}
	
	
}
