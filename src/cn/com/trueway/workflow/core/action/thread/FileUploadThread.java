package cn.com.trueway.workflow.core.action.thread;

import java.io.File;
import java.util.HashMap;

import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.workflow.core.service.FlowService;

/**
 * 因为原路径地址的文件生成存在延迟,加入线程确保在文件存在的情况下在进行文件的转移
 * @author caiyj
 *
 */
public class FileUploadThread extends Thread{
	
	private File src;
	
	private File dst;
	
	private SendAttachments att;
	
	private AttachmentService attachmentService;
	
	private FlowService flowService;
	
	private String type;
	
	/**
	 * 初始化
	 * @param src	源文件	
	 * @param dst	目标文件
	 */
	public FileUploadThread(FlowService flowService,File src, File dst, SendAttachments att,
			AttachmentService attachmentService, String type){
		this.src = src;
		this.dst = dst;
		this.att = att;
		this.attachmentService = attachmentService;
		this.type = type;
	}
	
	@Override
	public void run() {
		boolean flag = false;
		while(!flag){
			if(src.exists() && src.length()>0){
				flag = true;
			}
			if(!flag){
				try {
					Thread.sleep(3000);		//如果文件不存在,沉睡3秒,再次检查文件是否存在
					//System.out.println("上传的文件不存在,稍后尝试上传~!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				FileUploadUtils.copy(src,dst);		//文件迁移
				System.out.println("src.length()="+src.length());
				att.setFilesize(src.length());
				attachmentService.addSendAtts(att);
			}
		}
		
		
	}
}
