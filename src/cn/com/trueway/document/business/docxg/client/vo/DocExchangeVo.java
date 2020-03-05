package cn.com.trueway.document.business.docxg.client.vo;

// default package

import java.util.ArrayList;
import java.util.List;

import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;

public class DocExchangeVo implements java.io.Serializable {
	private static final long serialVersionUID = -8018654133484306201L;
	private BaseDoc docExchangeBox;
	private Queue queue;
	private List<ReceiveAttachments> atts = new ArrayList<ReceiveAttachments>() ;
	private List<ReceiveAttachmentsext> attsext = new ArrayList<ReceiveAttachmentsext>();
	
	public DocExchangeVo(BaseDoc docExchangeBox, Queue queue,
			List<ReceiveAttachments> atts,
			List<ReceiveAttachmentsext> attsext) {
		this.docExchangeBox = docExchangeBox;
		this.queue = queue;
		this.atts = atts;
		this.attsext = attsext;
	}
	public BaseDoc getDocExchangeBox() {
		return docExchangeBox;
	}
	public void setDocExchangeBox(BaseDoc docExchangeBox) {
		this.docExchangeBox = docExchangeBox;
	}
	public Queue getQueue() {
		return queue;
	}
	public void setQueue(Queue queue) {
		this.queue = queue;
	}
	public List<ReceiveAttachments> getAtts() {
		return atts;
	}
	public void setAtts(List<ReceiveAttachments> atts) {
		this.atts = atts;
	}
	public List<ReceiveAttachmentsext> getAttsext() {
		return attsext;
	}
	public void setAttsext(List<ReceiveAttachmentsext> attsext) {
		this.attsext = attsext;
	}
	
}