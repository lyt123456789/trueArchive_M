package cn.com.trueway.workflow.core.action;

import java.io.PrintWriter;
import java.util.List;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.workflow.core.pojo.AttachmentType;
import cn.com.trueway.workflow.core.service.AttachmentTypeService;

public class AttachmentTypeAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AttachmentTypeService attachmentTypeService;

	private AttachmentType attachmentType;
	
	public AttachmentTypeService getAttachmentTypeService() {
		return attachmentTypeService;
	}

	public void setAttachmentTypeService(AttachmentTypeService attachmentTypeService) {
		this.attachmentTypeService = attachmentTypeService;
	}
	
	public AttachmentType getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(AttachmentType attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getAttachmentTypeList(){
		List<AttachmentType> list = attachmentTypeService.getAttachmentTypeList();
		getRequest().setAttribute("list", list);
		return "attachmentTypeList";
	}
	
	public String toAddJsp(){
		return "addAttachmentType";
	}
	
	public String addAttachmentType(){
		attachmentTypeService.addAttachmentType(attachmentType);
		return getAttachmentTypeList();
	}
	
	public String toEditJsp(){
		String id = getRequest().getParameter("id");
		attachmentType = attachmentTypeService.getAttachmentTypeById(id);
		getRequest().setAttribute("attachmentType", attachmentType);
		return "editAttachmentType";
	}
	
	public String editAttachmentType(){
		attachmentTypeService.addAttachmentType(attachmentType);
		return getAttachmentTypeList();
	}
	
	public String delAttachmentType(){
		String id = getRequest().getParameter("id");
		String[] ids = id.split(",");
		for(String strId : ids){
			attachmentType = attachmentTypeService.getAttachmentTypeById(strId);
			attachmentTypeService.delAttachmentType(attachmentType);
		}
		return getAttachmentTypeList();
	}
	
	public void checkName(){
		String name = getRequest().getParameter("name");
		List<AttachmentType> list = attachmentTypeService.getAttachmentTypeByName(name);
		String result = "0";
		if(list != null && list.size() > 1){
			result = "1";
		}
		PrintWriter pw = null;
		try{
			pw = getResponse().getWriter();
			pw.write(result);
			pw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			pw.close();
		}
	}
	
}
