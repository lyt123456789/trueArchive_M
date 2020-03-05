package cn.com.trueway.workflow.core.service;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfLabel;

public interface LabelService {

	List<WfLabel> getLabelByTemplateId(String vc_templateid);
	
	WfLabel addLabel(WfLabel wfLabel);
	
	boolean delLabel(WfLabel label);
}
