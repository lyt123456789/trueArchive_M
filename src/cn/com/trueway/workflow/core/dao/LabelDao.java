package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfLabel;

public interface LabelDao {

	List<WfLabel> getLabelByTid(String vc_templateid);
	
	WfLabel addLabel(WfLabel wfLabel);
	
	public void delLabel(WfLabel wfLabel);
	
	public WfLabel getLabelById(String id);
	
}
