package cn.com.trueway.workflow.core.service;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.AttachmentType;

public interface AttachmentTypeService {

	AttachmentType addAttachmentType(AttachmentType attachmentType);
	
	void delAttachmentType(AttachmentType attachmentType);
	
	List<AttachmentType> getAttachmentTypeList();
	
	List<AttachmentType> getAttachmentTypeByName( String name);
	
	AttachmentType getAttachmentTypeById(String id);
	
	
}
