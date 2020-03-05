package cn.com.trueway.document.business.service.impl;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.velocity.runtime.directive.Define;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.document.business.dao.SendDocDao;
import cn.com.trueway.document.business.docxg.client.service.DocExchangeClient;
import cn.com.trueway.document.business.docxg.client.support.DocXgXmlUtil;
import cn.com.trueway.document.business.docxg.client.support.GenUserKey;
import cn.com.trueway.document.business.model.Doc;
import cn.com.trueway.document.business.service.SendDocService;
import cn.com.trueway.document.business.util.AnalyzeWSXml;
import cn.com.trueway.document.business.util.GetAxisInterface;
import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsext;
import cn.com.trueway.document.component.taglib.comment.dao.CommentDao;
import cn.com.trueway.document.component.taglib.comment.model.po.CommentAtt;
import cn.com.trueway.sys.util.SystemParamConfigUtil;

public class SendDocServiceImpl implements SendDocService {
	private Logger logger=Logger.getLogger(this.getClass());
	private SendDocDao sendDocDao;
	private DocExchangeClient docExchangeClient;
	private AttachmentDao attachmentDao;
	private CommentDao commentDao;
	
	public SendDocDao getSendDocDao() {
		return sendDocDao;
	}
	public void setSendDocDao(SendDocDao sendDocDao) {
		this.sendDocDao = sendDocDao;
	}
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public DocExchangeClient getDocExchangeClient() {
		return docExchangeClient;
	}
	public void setDocExchangeClient(DocExchangeClient docExchangeClient) {
		this.docExchangeClient = docExchangeClient;
	}
	
	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	public CommentDao getCommentDao() {
		return commentDao;
	}
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	/**
	 * 描述：根据RESULTFLAG查询出DOC对象，包含附件<br>
	 *
	 * @param ResultFlag
	 * @return Doc
	 */
	public Doc getFullDocByRF(String ResultFlag){
		return sendDocDao.findFullDocByDocguid(sendDocDao.findDocguidByRF(ResultFlag));
	}
	/**
	 * 描述：根据docguid查询出DOC对象，包含附件<br>
	 *
	 * @param ResultFlag
	 * @return Doc
	 */
	public Doc findFullDocByDocguid(String docguid){
		return sendDocDao.findFullDocByDocguid(docguid);
	}
	
	/**
	 * 描述：根据instanceId查出DOC对象及其附件对象<br>
	 *
	 * @param instanceId
	 * @return Doc
	 * @throws Exception 
	 */
	public Doc findFullDocByInstanceId(String instanceId) throws Exception{
		Doc doc = sendDocDao.findDocByInstanceId(instanceId);
		String docguid=doc==null?instanceId:doc.getDocguid();  
		doc = fullDoc(doc,docguid);
		return doc;
	}
	/**
	 * 根据docId找与Doc对象关联的Attachments、AttachmentsExt、ReadOpinion、WriteOpinion,并填充
	 * 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public Doc fullDoc(Doc doc,String docguid) throws Exception {
		// 根据docId找与Doc对象关联的List<SendAttachments>、List<SendAttachmentsext>
		List<SendAttachments> attl = attachmentDao.findAllSendAtts(docguid,null);
		List<SendAttachmentsext> attExtl = attachmentDao.findAllSendAttsext(docguid);
		// 给Doc对象填充属性
		if(doc==null){
			 doc = new Doc();
		}
		doc.setAtts(attl);
		doc.setAttExts(attExtl);
		return doc;
	}
	/**
	 * 描述：发送公文：调用WS接口到公文交换平台并且想本地数据库插入服务<br>
	 *
	 * @param doc
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String sendDoc_old(Doc doc){
		StringBuilder info=new StringBuilder();
		if(doc!=null){
			if(doc.getSender()!=null&&!"".equals(doc.getSender())){
				//sendDocDao.saveDoc(doc);
				String userKey = GenUserKey.genUserKey(doc.getSender());
				String docInfoXml = DocXgXmlUtil.genXmlForSendDoc(doc);
				
				String result = docExchangeClient.sendDoc(userKey, docInfoXml);
				logger.error("***********************"+result+"******************************************");
				if (result.indexOf("SUCCESSINFO") == -1){
					info.append("FAIL,");
					info.append(result);
					logger.error(info.toString());
					logger.error("调用接口发送公文失败，请检查原因");
				} else{
					String flagId = result.substring(result.indexOf("：") + 1, result.indexOf("]]"));
					doc.setResultFlag(flagId);
					doc.setStatus(Constant.DOC_BOX_YIFA);
					doc.setSubmittm(new Date());
					// 保存Doc对象
					sendDocDao.saveDoc(doc);
					logger.error("调用接口发送成功，并成功插库,RESULTFLAGID:"+flagId);
					info.append("SUCCESS,");
					info.append(flagId);
				}
			}	
		}else{
			info.append("发送失败，请检查发送单位是否填写");
			logger.warn("发送失败，请检查发送单位是否填写");
		}
		return info.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String sendDoc(Doc doc){
		StringBuilder info=new StringBuilder();
		if(doc!=null){
			if(doc.getSender()!=null&&!"".equals(doc.getSender())){
				//sendDocDao.saveDoc(doc);
//				String userKey = GenUserKey.genUserKey(doc.getSender());
//				String docInfoXml = DocXgXmlUtil.genXmlForSendDoc(doc);
				
				/**************城管*****************/
				String docInfoXml = AnalyzeWSXml.genXmlForSendDoc(doc);
				String result = GetAxisInterface.sendDoc(docInfoXml);
				/*******************************/
//				String result = docExchangeClient.sendDoc(userKey, docInfoXml);
				logger.error("***********************"+result+"******************************************");
//				if (result.indexOf("SUCCESSINFO") == -1){
				if (result.indexOf("<Status>True</Status>") == -1){
					info.append("FAIL,");
					info.append(result);
					logger.error(info.toString());
					logger.error("调用接口发送公文失败，请检查原因");
				} else{
					String flagId = result.substring(result.indexOf("：") + 1, result.indexOf("]]"));
					doc.setResultFlag(flagId);
					doc.setStatus(Constant.DOC_BOX_YIFA);
					doc.setSubmittm(new Date());
					// 保存Doc对象
					sendDocDao.saveDoc(doc);
					logger.error("调用接口发送成功，并成功插库,RESULTFLAGID:"+flagId);
					info.append("SUCCESS,");
					info.append(flagId);
				}
			}	
		}else{
			info.append("发送失败，请检查发送单位是否填写");
			logger.warn("发送失败，请检查发送单位是否填写");
		}
		return info.toString();
	}
	
	/**
	 * 描述：根据条件查询已发列表<br>
	 *
	 * @param currentPage
	 * @param numPerPage
	 * @param xto
	 * @param keyword
	 * @param content
	 * @param status
	 * @return DTPageBean
	 */
	public DTPageBean getSentDocList(int currentPage, int numPerPage, List<String> deps, String keyword, String content,String wh, String status,String userId){
		return sendDocDao.querySentDocList(currentPage, numPerPage, deps, keyword, content,wh, status,userId);
	}
	
	/**
	 * 
	 * 描述：在办理发送时，根据InstanceId查询Doc
	 *
	 * @param instanceId
	 * @return Doc
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-9 下午04:10:21
	 */
	public List<Doc> findDocForSendInProgress(String instanceId){
		List<Doc> docList=sendDocDao.findDocForSendInProgress(instanceId);
		if(!docList.isEmpty()){
			for(Doc doc : docList){
				List<SendAttachments> atts = attachmentDao.findAllSendAtts(doc.getDocguid(),null);
				List<SendAttachmentsext> attext = attachmentDao.findAllSendAttsext(doc.getDocguid());
				doc.setAtts(atts);
				doc.setAttExts(attext);
			}
		}
		return docList;
	}
	
	/**
	 * 
	 * 描述：根据所传的附件Ids 为docguid增加附加附件
	 *
	 * @param docguid
	 * @param attextIds void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-9 下午08:29:46
	 */
	public void addAttsextForBatchUpload(String docguid, String attextIds,String commentIds) {
		if(attextIds!=null&&docguid!=null){
			//得到所有附件的ID的集合
			List<String> ids = Arrays.asList(attextIds.split(","));
			List<String> ids_man = new ArrayList<String>();
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+"/"; // 得到上传文件在服务器上的基路径
			//先去附加附件表查找附件，如果没找到再去正文附件表里找，找到后修改docguid和id重新保存
			for (int i = 0; i <ids.size(); i++) {
				ReceiveAttachmentsext attext=attachmentDao.findReceiveAttsext(ids.get(i));
				if(attext!=null){
					String dstPath = FileUploadUtils.getRealFilePath("a."+attext.getFiletype(),basePath, Constant.UPLOAD_FILE_PATH);//统一上传的路径
					File srcFile = new File(basePath+attext.getLocalation());
					if(srcFile.exists()){
						FileUploadUtils.copy(srcFile, new File(basePath + dstPath));
					}
					SendAttachmentsext newAttext=this.fullSendAttachmentsext(docguid,attext.getFilename(),attext.getFiletype(),attext.getFilesize(),dstPath);
					attachmentDao.addSendAttsext(newAttext);
				}else{
					ids_man.add(ids.get(i));
				}
			}
			for (int i = 0; i <ids_man.size(); i++) {
				ReceiveAttachments atts = attachmentDao.findReceiveAtts(ids_man.get(i));
				if(atts!=null){
					String dstPath = FileUploadUtils.getRealFilePath("a."+atts.getFiletype(),basePath, Constant.UPLOAD_FILE_PATH);//统一上传的路径
					File srcFile = new File(basePath+atts.getLocalation());
					if(srcFile.exists()){
						FileUploadUtils.copy(srcFile, new File(basePath + dstPath));
					}
					SendAttachmentsext newAttext=this.fullSendAttachmentsext(docguid,atts.getFilename(),atts.getFiletype(),atts.getFilesize(),dstPath);
					attachmentDao.addSendAttsext(newAttext);
				}
			}
			//去意见标签附件表查找附件
			List<CommentAtt> commentAttList=commentDao.findCommnetAttBycomId(commentIds);
			if(commentAttList!=null){
				for (CommentAtt commentAtt : commentAttList) {
					String dstPath = FileUploadUtils.getRealFilePath("a."+commentAtt.getFileType(),basePath, Constant.UPLOAD_FILE_PATH);//统一上传的路径
					File srcFile = new File(basePath+commentAtt.getFileLocation());
					if(srcFile.exists()){
						FileUploadUtils.copy(srcFile, new File(basePath + dstPath));
					}
					SendAttachmentsext newAttext=this.fullSendAttachmentsext(docguid,commentAtt.getFileName(),commentAtt.getFileType(),commentAtt.getFileLength(),dstPath);
					attachmentDao.addSendAttsext(newAttext);
				}
			}
		}
	}
	
	/**
	 *  
	 * 描述：得到附加附件对象 
	 *
	 * @param attrs
	 * @return SendAttachmentsext
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-9 下午04:47:51
	 */
	private SendAttachmentsext fullSendAttachmentsext(String douguid,String name,String type,Long size,String location){
		SendAttachmentsext attsext = new SendAttachmentsext();
		attsext.setDocguid(douguid);
		attsext.setFilename(name);
		attsext.setFiletype(type);
		attsext.setFilesize(size);
		attsext.setLocalation(location);
		attsext.setFiletime(new Date());
		attsext.setFileindex(0L);
		return attsext;
	}

	public void saveDoc(Doc doc){
		sendDocDao.saveDoc(doc);
	}
	
	public void updateDoc(Doc doc){
		sendDocDao.updateDoc(doc);
	}
	
	public void saveOrUpdateDoc(Doc oldDoc, Doc doc, String saveOrUpdate) {
		if (doc != null)
		{
			// 填充Doc的属性
			String xtoFull = doc.getXtoFull();
			String xccFull = doc.getXccFull();
			String xto = splitString(xtoFull, 1);
			String xcc = splitString(xccFull, 1);
			String xtoName = doc.getXtoName();
			if(xtoFull != null && !("").equals(xtoFull)){
				xtoName = splitString(xtoFull, 0);
			}
			String xccName = doc.getXccName();
			if(xccFull != null && !("").equals(xccFull)){
				xccName = splitString(xccFull, 0);
			}
			String bodyxml = "<root><docguid>" + doc.getDocguid()
			+ "</docguid><xto>" + xtoName + "</xto><xcc>" + xccName
			+ "</xcc><title>" + doc.getTitle()
			+ "</title><keyword>" + doc.getKeyword()
			+ "</keyword><type>" + doc.getDoctype()
			+ "</type><priority>" + doc.getPriority()
			+ "</priority></root>";
			oldDoc.setXtoName(xtoName);
			oldDoc.setXccName(xccName);
			oldDoc.setXto(xto);
			oldDoc.setXcc(xcc);
			oldDoc.setXtoFull(xtoFull);
			oldDoc.setXccFull(xccFull);
			oldDoc.setTitle(doc.getTitle());
			oldDoc.setKeyword(doc.getKeyword());
			oldDoc.setFwjg(doc.getFwjg());
			oldDoc.setYfdw(doc.getYfdw());
			//add by jiangrr
			/*
			 * 判断QFR里面是否除了“,”没有其他数据
			 * 如果只有“,”设置为空，不是还设置原值
			 * */
			if(doc.getQfr()!=null){
				String qfrStr = doc.getQfr().replace(",","").trim();
				if(qfrStr!=null&&qfrStr.equals("")){
					oldDoc.setQfr("");
				}else{
					oldDoc.setQfr(doc.getQfr());
				}
			}else{
				oldDoc.setQfr(doc.getQfr());
			}
			oldDoc.setYfrq(doc.getYfrq());
			oldDoc.setYffs(doc.getYffs());
			oldDoc.setDoctype(doc.getDoctype());
			oldDoc.setCebid(doc.getCebid());
			oldDoc.setPriority(doc.getPriority());
			oldDoc.setSender(doc.getSender());
			oldDoc.setBodyxml(bodyxml);			
			oldDoc.setGkfw(doc.getGkfw());
			oldDoc.setHgr(doc.getHgr());
			oldDoc.setFzbhgr(doc.getFzbhgr());
			oldDoc.setRecord(doc.getRecord());
			oldDoc.setGzdbt(doc.getGzdbt());
			oldDoc.setGkfs(doc.getGkfs());
			oldDoc.setFormid(doc.getFormid());
			oldDoc.setFormInstanceId(doc.getFormInstanceId());
			oldDoc.setStatus(doc.getStatus());
			oldDoc.setLhxwjg(doc.getLhxwjg());
			oldDoc.setSendStatus(doc.getSendStatus());
			oldDoc.setSenderId(doc.getSenderId());//
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(oldDoc.getNg_time()==null){
				oldDoc.setNg_time(sdf.format(new Date()));
			}else{
				oldDoc.setNg_time(doc.getNg_time());
			}
	        if (oldDoc.getFwxh()==null) {
	        	oldDoc = paserDocNum(oldDoc,doc.getFwxh());
	        }
		}
		oldDoc.setStatus(Constant.DOC_BOX_LIUZZ);
		// 保存或更新操作
		if (saveOrUpdate.equals(Constant.SAVE))
		{
			// 保存Doc对象
			sendDocDao.saveDoc(oldDoc);
		} else if (saveOrUpdate.equals(Constant.UPDATE))
		{
			// 更新Doc对象
			sendDocDao.updateDoc(oldDoc);
		}
	}
	
	/**
	 * xtoFull、xccFull得到xto、xcc
	 * 
	 * @param oriStr
	 * @param index
	 *            0为单位名称，1为单位Id，2为发文份数，3为单位公章名称
	 * @return
	 */
	private String splitString(String oriStr, int index)
	{
		if (oriStr != null && oriStr.trim().length() > 0)
		{
			StringBuilder sb = new StringBuilder();
			String[] str = oriStr.split(";");
			for (int i = 0, l = str.length; i < l; i++)
			{
				sb.append(str[i].split("\\|")[index]);
				sb.append(";");
			}
			return sb.toString();
		}
		return "";

	}
	
	/**
	 * 描述：解析文号<br>
	 *
	 * @param docNum
	 * @return DocBw
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2012-1-10 下午06:02:17
	 */
	public Doc paserDocNum(Doc dn, String docNum){
		try {
			//取字号
			String regChina = "[\u4e00-\u9fa5]+";
			Pattern p = Pattern.compile(regChina);
			Matcher m = p.matcher(docNum);
			if (m.find()) {
				dn.setJgdz(m.group());
			}
			//取年号
			p = Pattern.compile("\\d{4}");
			m = p.matcher(docNum);
			if(m.find()){
				dn.setFwnh(m.group());
			}
			//取序号
			p = Pattern.compile("\\d+号");
			m = p.matcher(docNum);
			if (m.find()) {
				dn.setFwxh(m.group().replace("号", ""));
			}
		} catch (Exception e) {
		}
        return dn;
	}
	
	public Doc findDocByInstanceId(String instanceId){
		return sendDocDao.findDocByInstanceId(instanceId);
	}
	public DTPageBean getSendwd(List<Define> defines, String title, String wh,
			String startTime, String endTime, int currentPage, int pageSize) {
		String wfuid = "";
//		if(defines!=null&&!defines.isEmpty()){
//			for(Define d : defines){
//	new			if(Constant.DEFINE_TYPE_SEND.equals(d.getDefineType())){
//					wfuid += "'"+d.getWfUid()+"',";
//				}
//			}
//		}
		if(wfuid.length()==0) return new DTPageBean();
		wfuid = wfuid.substring(0, wfuid.length()-1);
		String nh = null,xh = null,zh = null;
		if(wh != null && wh.trim().length() != 0){
			//取字号
			String regChina = "[\u4e00-\u9fa5]+|-|[[A-Za-z]+[\u4e00-\u9fa5]+]+";
	        Pattern p = Pattern.compile(regChina);
	        Matcher m = p.matcher(wh);
	        if (m.find()) {
	        	zh = m.group();
	        }
	        //取年号
	        p = Pattern.compile("\\d{4}");
	        m = p.matcher(wh);
	        if (m.find()) {
	        	nh = m.group();
	        }
	        //取序号
	        p = Pattern.compile("\\d+号");
	        m = p.matcher(wh);
	        if (m.find()) {
	        	xh = m.group().replace("号", "");
	        }
		}
		return sendDocDao.getSendwd(wfuid, title, zh, nh, xh, startTime, endTime, currentPage, pageSize);
	}
	public String checkbw(String wh, String title,String webId) {
		if(wh != null && wh.trim().length() <= 7) wh = null;
		if(title != null && title.trim().length()!=0){
			return sendDocDao.checkbw(wh,title,webId);
		}
		return null;
	}
}
