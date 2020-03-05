package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.AttachmentType;

public interface AttachmentTypeDao {

	AttachmentType addAttachmentType(AttachmentType attachmentType);
	
	void delAttachmentType(AttachmentType attachmentType);
	
	List<AttachmentType> getAttachmentTypeList();
	
	List<AttachmentType> getAttachmentTypeByParam( AttachmentType attachmentType);
	
}
