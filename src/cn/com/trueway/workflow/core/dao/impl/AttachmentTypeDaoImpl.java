package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.workflow.core.dao.AttachmentTypeDao;
import cn.com.trueway.workflow.core.pojo.AttachmentType;

public class AttachmentTypeDaoImpl extends BaseDao implements AttachmentTypeDao {

	public AttachmentType addAttachmentType(AttachmentType attachmentType) {
		if(attachmentType.getId() != null && !"".equals(attachmentType.getId())){
			getEm().merge(attachmentType);
		}else{
			if("".equals(attachmentType.getId())){
				attachmentType.setId(null);
			}
			getEm().persist(attachmentType);
		}
		return attachmentType;
	}

	public void delAttachmentType(AttachmentType attachmentType) {
		getEm().remove(getEm().merge(attachmentType));
	}

	@SuppressWarnings("unchecked")
	public List<AttachmentType> getAttachmentTypeList(){
		String hql = "from AttachmentType";
		return getEm().createQuery(hql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<AttachmentType> getAttachmentTypeByParam( AttachmentType attachmentType){
		String hql = "from AttachmentType where 1=1";
		if(CommonUtil.stringNotNULL(attachmentType.getAtt_type())){
			hql += " and att_type='" + attachmentType.getAtt_type() + "'";
		}
		if(CommonUtil.stringNotNULL(attachmentType.getId())){
			hql += " and id='" + attachmentType.getId() + "'";
		}
		return getEm().createQuery(hql).getResultList();
	}

}
