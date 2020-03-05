package cn.com.trueway.workflow.core.service.impl;

import java.util.List;

import cn.com.trueway.workflow.core.dao.AttachmentTypeDao;
import cn.com.trueway.workflow.core.pojo.AttachmentType;
import cn.com.trueway.workflow.core.service.AttachmentTypeService;


public class AttachmentTypeServiceImpl implements AttachmentTypeService {

	private AttachmentTypeDao attachmentTypeDao;
	
	public AttachmentTypeDao getAttachmentTypeDao() {
		return attachmentTypeDao;
	}

	public void setAttachmentTypeDao(AttachmentTypeDao attachmentTypeDao) {
		this.attachmentTypeDao = attachmentTypeDao;
	}

	public AttachmentType addAttachmentType(AttachmentType attachmentType) {
		return attachmentTypeDao.addAttachmentType(attachmentType);
	}

	public void delAttachmentType(AttachmentType attachmentType) {
		attachmentTypeDao.delAttachmentType(attachmentType);
	}

	public List<AttachmentType> getAttachmentTypeByName(String name) {
		AttachmentType attachmentType = new AttachmentType();
		attachmentType.setAtt_type(name);
		return attachmentTypeDao.getAttachmentTypeByParam(attachmentType);
	}
	
	public AttachmentType getAttachmentTypeById(String id) {
		AttachmentType attachmentType = new AttachmentType();
		attachmentType.setId(id);
		return attachmentTypeDao.getAttachmentTypeByParam(attachmentType).get(0);
	}

	public List<AttachmentType> getAttachmentTypeList() {
		return attachmentTypeDao.getAttachmentTypeList();
	}

}
