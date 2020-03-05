package cn.com.trueway.workflow.core.service.impl;

import java.util.List;

import cn.com.trueway.workflow.core.dao.LabelDao;
import cn.com.trueway.workflow.core.pojo.WfLabel;
import cn.com.trueway.workflow.core.service.LabelService;

public class LabelServiceImpl implements LabelService {

	private LabelDao labelDao;
	
	public LabelDao getLabelDao() {
		return labelDao;
	}

	public void setLabelDao(LabelDao labelDao) {
		this.labelDao = labelDao;
	}

	public WfLabel addLabel(WfLabel wfLabel) {
		return labelDao.addLabel( wfLabel);
	}

	public List<WfLabel> getLabelByTemplateId(String vcTemplateid) {
		return labelDao.getLabelByTid( vcTemplateid);
	}
	
	public boolean delLabel(WfLabel label){
		labelDao.delLabel(label);
		return true;
	}

}
