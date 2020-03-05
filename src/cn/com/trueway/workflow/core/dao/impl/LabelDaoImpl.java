package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.dao.LabelDao;
import cn.com.trueway.workflow.core.pojo.WfLabel;

public class LabelDaoImpl extends BaseDao implements LabelDao {

	public WfLabel addLabel(WfLabel wfLabel) {
		if(wfLabel.getId() != null && !"".equals(wfLabel.getId())){
			getEm().merge(wfLabel);
		}else{
			if("".equals(wfLabel.getId())){
				wfLabel.setId(null);
			}
			getEm().persist(wfLabel);
		}
		return wfLabel;
	}

	@SuppressWarnings("unchecked")
	public List<WfLabel> getLabelByTid(String vcTemplateid) {
		String querySql = "from WfLabel where vc_templateid='"+vcTemplateid+"'";
		return getEm().createQuery( querySql).getResultList();
	}
	
	/**
	 * 删
	 */
	public void delLabel(WfLabel wfLabel){
		getEm().remove(getEm().merge(wfLabel));
	}
	
	public WfLabel getLabelById(String id){
		String querySql = "from WfLabel e where id ='" + id + "'";
		return (WfLabel)getEm().createQuery(querySql).getResultList().get(0);
	}

}
