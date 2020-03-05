package cn.com.trueway.document.business.service.impl;

import java.io.FileInputStream;
import cn.com.trueway.document.business.model.ReceiveProcessShip;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.ws.security.util.Base64;
import org.apache.xmlbeans.XmlException;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.DownloadFileUtil;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.document.business.dao.ReceiveDocDao;
import cn.com.trueway.document.business.docxg.client.service.DocExchangeClient;
import cn.com.trueway.document.business.docxg.client.support.DocXgXmlUtil;
import cn.com.trueway.document.business.docxg.client.support.GenUserKey;
import cn.com.trueway.document.business.docxg.client.vo.DocExchangeVo;
import cn.com.trueway.document.business.model.CheckInRecDoc;
import cn.com.trueway.document.business.model.RecDoc;
import cn.com.trueway.document.business.model.ReceiveXml;
import cn.com.trueway.document.business.service.ReceiveDocService;
import cn.com.trueway.document.business.util.AnalyzeWSXml;
import cn.com.trueway.document.business.util.GetAxisInterface;
import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsext;
import cn.com.trueway.workflow.core.dao.ItemDao;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfProcess;

/**
 * 描述: 对接收的公文进行业务处理<br>
 * 作者：周雪贇<br>
 * 创建时间：2011-12-2 下午01:52:41<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class ReceiveDocServiceImpl implements ReceiveDocService {
	private Logger logger = Logger.getLogger(this.getClass());

	private DocExchangeClient docExchangeClient;

	private ReceiveDocDao receiveDocDao;
	
	private AttachmentDao attachmentDao;
	
	private TableInfoDao tableInfoDao;
	
	private ItemDao itemDao;
	
	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public DocExchangeClient getDocExchangeClient() {
		return docExchangeClient;
	}

	public void setDocExchangeClient(DocExchangeClient docExchangeClient) {
		this.docExchangeClient = docExchangeClient;
	}

	public ReceiveDocDao getReceiveDocDao() {
		return receiveDocDao;
	}

	public void setReceiveDocDao(ReceiveDocDao receiveDocDao) {
		this.receiveDocDao = receiveDocDao;
	}

	/**
	 * 描述：获取待收列表的MAP<br>
	 * 
	 * @param deps
	 * @return LinkedHashMap<String,DocExchangeVo>
	 */
	public LinkedHashMap<String, DocExchangeVo> getToBeReceivedList(
			List<ReceiveXml> xmlList) {
		LinkedHashMap<String, DocExchangeVo> tobeRecevicedoc = new LinkedHashMap<String, DocExchangeVo>();
		String dep = null;
		String xml = null;
		if (xmlList != null && xmlList.size() > 0) {
			ReceiveXml receiveXml = null;
			for (int i = 0; i < xmlList.size(); i++) {
				receiveXml = xmlList.get(i);
				dep = receiveXml.getDepId();
				xml = receiveXml.getXml();
				try {// 根据公文交接接口返回的xml信息组装成自己需要的对象
					List<DocExchangeVo> docExchangeVos = DocXgXmlUtil.genDocModelFromXML(xml, dep);
					for (DocExchangeVo docExchangeVo : docExchangeVos) {
						tobeRecevicedoc.put(docExchangeVo.getQueue().getId(),docExchangeVo);
					}
				} catch (XmlException e) {
					logger.error(e.getMessage());
					logger.error("**************ReceiveDocServiceImpl.getToReceivedList()*****************");
					logger.error("***********获取待收列表失败，可能导致的原因是WS接口调用失败****************");
				}
			}
		}
		return tobeRecevicedoc;
	}
	/**
	 * 描述：portal获取待收列表<br>
	 * 
	 * @param deps
	 * @return LinkedHashMap<String,DocExchangeVo>
	 */
	public StringBuilder getToBeReceivedListForPortal(List<String> deps,
			String callback,String url) {
		String dep = null;
		String userKey = null;
		String xml = null;
		StringBuilder sb = new StringBuilder();
		sb.append(";" + callback + "({");
		sb.append("data:{");
		sb.append("list:[");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (deps != null && deps.size() > 0) {
			for (int i = 0; i < deps.size(); i++) {
				dep = deps.get(i);
//				userKey = GenUserKey.genUserKey(dep);
//				xml = docExchangeClient.getToBeReceivedList(userKey);// 调用WS接口获取接收列表
				
				/**************城管****************/
				String urlaxis = "";
				String method =  "";
				xml = docExchangeClient.getToBeReceivedList(GetAxisInterface.getAsixContent(urlaxis, method, null));
				/******************************/
				try {// 根据公文交接接口返回的xml信息组装成自己需要的对象
					List<DocExchangeVo> docExchangeVos = DocXgXmlUtil
							.genDocModelFromXML(xml, dep);
					if (docExchangeVos.size() != 0 && docExchangeVos != null) {
						// 门户中只取前5条展现
						if (docExchangeVos.size() > 5) {
							for (int j = 0; j < 5; j++) {
								sb.append("{title:\""
										+ docExchangeVos.get(j)
												.getDocExchangeBox().getTitle()
										+ "\",");
								sb.append("date:\""
										+ sdf.format(docExchangeVos.get(j)
												.getDocExchangeBox()
												.getSubmittm()) 
										+ "\",");
								sb.append("link:\""+url+"/rec_receiveDocForPortal.do\",");
								sb.append("id:\""+docExchangeVos.get(i).getQueue().getId()+"\",");
								sb.append("departmentId:\""+dep+"\"");
								sb.append("},");
							}
						} else {
							for (DocExchangeVo docExchangeVo : docExchangeVos) {
								sb.append("{title:\""
										+ docExchangeVo.getDocExchangeBox()
												.getTitle() + "\",");
								sb.append("date:\""
										+ sdf.format(docExchangeVo
												.getDocExchangeBox()
												.getSubmittm())
										+ "\",");
								sb.append("link:\""+url+"/rec_receiveDocForPortal.do\",");
								sb.append("id:\""+docExchangeVos.get(i).getQueue().getId()+"\",");
								sb.append("departmentId:\""+dep+"\"");
								//参数:receiveType=one前台已写
								sb.append("},");
							}
						}
					}
				} catch (XmlException e) {
					logger.error(e.getMessage());
					logger
							.error("**************ReceiveDocServiceImpl.getToReceivedList()*****************");
					logger
							.error("***********获取待收列表失败，可能导致的原因是WS接口调用失败****************");
				}
			}
		}
		if (sb != null && sb.lastIndexOf(",") > -1)
			sb = new StringBuilder(sb.substring(0, sb.length() - 1));
		sb.append("]},");
		// css
		sb.append("css:\"");
		sb.append(".R_PORTAL_cbox td{padding:0;line-height:22px;height:22px;}.R_PORTAL_cbox td.date{width:80px;overflow:hidden;text-align:center}.R_PORTAL_cbox td{vertical-align:top}.R_PORTAL_cbox td table td{vertical-align:middle}\"");
		sb.append(",");
		// js
		sb.append("js:\"");
		sb.append("\"");
		sb.append(",");
		// template
		sb.append("template:'");
		sb.append("<table class=\"R_PORTAL_cbox\" width=\"100%\" border=\"0\">{@if list.length!=0}{@each list as ls}<tr><td>${ls.title}</td><td class=\"date\">${ls.date}</td><td>&nbsp;&nbsp;</td><td><a href=\"javascript:;\" onclick=\"refresh(this,\\\'${ls.link}\\\',\\\'"+url+"/rec_getToBeRecForPortal.do?callback=?\\\',\\\'${ls.id}\\\',\\\'${ls.departmentId}\\\');\">收取</a></td></tr>{@/each}{@/if}{@if list.length==0}<tr>暂无信息！</tr>{@/if}</table>'");

		sb.append("})");
		return sb;
	}

	/**
	 * 描述：从公文交换收取一个公文，并且入库<br>
	 * 
	 * @param doc
	 * @param idString
	 * @return String
	 * @throws Exception
	 */
	public String addReceiveOneDoc_old(DocExchangeVo doc, String idString)
			throws Exception {
		// 循环从公文交换平台获取附件
		List<ReceiveAttachments> attList = doc.getAtts();
		try {
			for (ReceiveAttachments attachments : attList) {
				attachments.setId(null);
				attachments.setDocguid(idString);
				// 获取正文附件并返回附件的地址
				String location = DownloadFileUtil.downloadFromUrl(attachments
						.getLocalation(), attachments.getFilename());
				attachments.setLocalation(location);
			}

			List<ReceiveAttachmentsext> attextList = doc.getAttsext();
			for (ReceiveAttachmentsext attachments : attextList) {
				attachments.setId(null);
				attachments.setDocguid(idString);
				// 获取附件并返回附件的地址
				String location = DownloadFileUtil.downloadFromUrl(attachments
						.getLocalation(), attachments.getFilename());
				attachments.setLocalation(location);
			}
		} catch (Exception e) {
			logger.error("收取附件发生异常,请检查,请检查附件是否存在?");
			return "error";
		}
		// 向RECDOC对象里面进行塞值
		RecDoc recDoc = new RecDoc(idString, doc.getDocExchangeBox()
				.getDoctype(), doc.getDocExchangeBox().getKeyword(), doc
				.getDocExchangeBox().getPriority().toString(), doc.getQueue()
				.getXto(), doc.getDocExchangeBox().getSender(), doc
				.getDocExchangeBox().getSubmittm(), doc.getDocExchangeBox()
				.getTitle(), doc.getDocExchangeBox().getJgdz() + "["
				+ doc.getDocExchangeBox().getFwnh() + "]"
				+ doc.getDocExchangeBox().getFwxh() + "号", doc
				.getDocExchangeBox().getXcc(),
				doc.getDocExchangeBox().getXto(), doc.getDocExchangeBox()
						.getYfdw(), doc.getDocExchangeBox().getYfrq(),
				new Date(), attList, doc.getAttsext(), doc.getDocExchangeBox()
						.getCebid());
		String xto = doc.getDocExchangeBox().getXto();
		String xcc = doc.getDocExchangeBox().getXcc();
		recDoc.setXto(xto);
		recDoc.setXcc(xcc);
		recDoc.setXtoName(doc.getDocExchangeBox().getXtoName());
		recDoc.setXccName(doc.getDocExchangeBox().getXccName());
		// 0 未处理 1正在处理 2 已办结 3 其他 4 已退文 5 已关联
		recDoc.setStatus(Constant.RECEIVE_WEICHULI);
		recDoc.setYffs(String
				.valueOf(doc.getDocExchangeBox().getYffs() == 0 ? 1 : doc
						.getDocExchangeBox().getYffs()));
		// 保存RECDOC对象
		receiveDocDao.saveRecDOC(recDoc);

		// 手动插入正文附件
		for (ReceiveAttachments attachments : attList) {
			receiveDocDao.saveRecDocAtts(attachments);
		}
		// //获取本部门的验证码
		// String userKey = GenUserKey.genUserKey(recDoc.getQueueXto());
		// @SuppressWarnings("unused")
		// String temp = docExchangeClient.receiveDoc(userKey, idString);//
		// 通知公文交换系统已收文
		return "success";
	}

	/**
	 * 描述：从公文交换收取一个公文，并且入库<br>
	 * 
	 * @param doc  
	 * @param xml
	 * @return String
	 * @throws Exception
	 */
	public String addReceiveOneDoc(DocExchangeVo docVo, String xml)
			throws Exception {
//		docVo = AnalyzeWSXml.toBeReceivedDoc(xml);
		// 循环从公文交换平台获取附件
		List<ReceiveAttachments> attList = docVo.getAtts();
		List<ReceiveAttachmentsext> attextList = docVo.getAttsext();
		ReceiveAttachments receiveAttachments = null;
		ReceiveAttachmentsext receiveAttachmentsext  = null;
		if(attList==null){
			attList = new ArrayList<ReceiveAttachments>();
		}
		for(int i=0; attextList!=null && i<attextList.size(); i++){
			receiveAttachments = new ReceiveAttachments();
			receiveAttachmentsext = attextList.get(i);
			receiveAttachments.setData(receiveAttachmentsext.getData());
			receiveAttachments.setDocguid(receiveAttachmentsext.getDocguid());
			receiveAttachments.setFileindex(receiveAttachmentsext.getFileindex());
			receiveAttachments.setFilename(receiveAttachmentsext.getFilename());
			receiveAttachments.setFilesize(receiveAttachmentsext.getFilesize());
			receiveAttachments.setFiletime(receiveAttachmentsext.getFiletime());
			receiveAttachments.setFiletype(receiveAttachmentsext.getFiletype());
			receiveAttachments.setId(receiveAttachmentsext.getId());
			receiveAttachments.setLocalation(receiveAttachmentsext.getLocalation());
			attList.add(receiveAttachments);
		}
		
		// 向RECDOC对象里面进行塞值
		RecDoc recDoc = new RecDoc(xml, docVo.getDocExchangeBox()
				.getDoctype(), docVo.getDocExchangeBox().getKeyword(), docVo
				.getDocExchangeBox().getPriority().toString(), docVo.getDocExchangeBox()
				.getXto(), docVo.getDocExchangeBox().getSender(), docVo
				.getDocExchangeBox().getSubmittm(), docVo.getDocExchangeBox()
				.getTitle(), docVo.getDocExchangeBox().getJgdz() + "["
				+ docVo.getDocExchangeBox().getFwnh() + "]"
				+ docVo.getDocExchangeBox().getFwxh() + "号", docVo
				.getDocExchangeBox().getXcc(),
				docVo.getDocExchangeBox().getXto(), docVo.getDocExchangeBox()
						.getYfdw(), docVo.getDocExchangeBox().getYfrq(),
				new Date(), docVo.getAtts(), null, docVo.getDocExchangeBox()
						.getCebid());
		String xto = docVo.getDocExchangeBox().getXto();
		String xcc = docVo.getDocExchangeBox().getXcc();
		recDoc.setXto(xto);
		recDoc.setXcc(xcc);
		recDoc.setXtoName(docVo.getDocExchangeBox().getXtoName());
		recDoc.setXccName(docVo.getDocExchangeBox().getXccName());
		// 0 未处理 1正在处理 2 已办结 3 其他 4 已退文 5 已关联
		recDoc.setStatus(Constant.RECEIVE_WEICHULI);
		recDoc.setYffs(String
				.valueOf(docVo.getDocExchangeBox().getYffs() == 0 ? 1 : docVo
						.getDocExchangeBox().getYffs()));
		// 保存RECDOC对象
		receiveDocDao.saveRecDOC(recDoc);

		// 手动插入正文附件
		for (ReceiveAttachments attachments : attList) {
			attachments.setId(null);
			receiveDocDao.saveRecDocAtts(attachments);
			/********by zhuxc 20131030 附件要在表单中显示，需要插入公文附件表,后缀attzw*******/
			SendAttachments sendAtt = new SendAttachments(attachments.getDocguid()+"attzw",attachments.getFilename(),attachments.getFiletype(),attachments.getFilesize(),attachments.getLocalation(),attachments.getFiletime());
			sendAtt.setTitle(attachments.getFilename());
			sendAtt.setType("正文材料");
			attachmentDao.addSendAtts(sendAtt);
		}
		
		/*for(ReceiveAttachmentsext attachments : attextList ){
			attachments.setId(null);
			*//********by zhuxc 20131030 附件要在表单中显示，需要插入公文附件表，后缀fj*******//*
			SendAttachments sendAtt = new SendAttachments(attachments.getDocguid()+"fj",attachments.getFilename(),attachments.getFiletype(),attachments.getFilesize(),attachments.getLocalation(),attachments.getFiletime());
			sendAtt.setTitle(attachments.getFilename());
			sendAtt.setType("正文材料");
			attachmentDao.addSendAtts(sendAtt);
		}*/
		
		return "success";
	}
	/**
	 * 描述：从公文交换收取一个公文，并且入库<br>
	 * 
	 * @param doc
	 * @param idString
	 * @return String
	 * @throws Exception
	 */
	private String addReceiveOneDocForAll(DocExchangeVo doc, String idString)
			throws Exception {
		// 循环从公文交换平台获取附件
		try {
			List<ReceiveAttachments> attList = doc.getAtts();
			for (ReceiveAttachments attachments : attList) {
				attachments.setId(null);
				attachments.setDocguid(idString);
				// 获取正文附件并返回附件的地址
				String location = DownloadFileUtil.downloadFromUrl(attachments
						.getLocalation(), attachments.getFilename());
				attachments.setLocalation(location);
			}

			List<ReceiveAttachmentsext> attextList = doc.getAttsext();
			for (ReceiveAttachmentsext attachments : attextList) {
				attachments.setId(null);
				attachments.setDocguid(idString);
				// 获取附件并返回附件的地址
				String location = DownloadFileUtil.downloadFromUrl(attachments
						.getLocalation(), attachments.getFilename());
				attachments.setLocalation(location);
			}
			// 向RECDOC对象里面进行塞值
			RecDoc recDoc = new RecDoc(idString, doc.getDocExchangeBox()
					.getDoctype(), doc.getDocExchangeBox().getKeyword(), doc
					.getDocExchangeBox().getPriority().toString(), doc
					.getQueue().getXto(), doc.getDocExchangeBox().getSender(),
					doc.getDocExchangeBox().getSubmittm(), doc
							.getDocExchangeBox().getTitle(), doc
							.getDocExchangeBox().getJgdz()
							+ "["
							+ doc.getDocExchangeBox().getFwnh()
							+ "]"
							+ doc.getDocExchangeBox().getFwxh() + "号", doc
							.getDocExchangeBox().getXcc(), doc
							.getDocExchangeBox().getXto(), doc
							.getDocExchangeBox().getYfdw(), doc
							.getDocExchangeBox().getYfrq(), new Date(),
					attList, doc.getAttsext(), doc.getDocExchangeBox()
							.getCebid());
			String xto = doc.getDocExchangeBox().getXto();
			String xcc = doc.getDocExchangeBox().getXcc();
			recDoc.setXto(xto);
			recDoc.setXcc(xcc);
			recDoc.setXtoName(doc.getDocExchangeBox().getXtoName());
			recDoc.setXccName(doc.getDocExchangeBox().getXccName());
			// 0 未处理 1正在处理 2 已办结 3 其他 4 已退文 5 已关联
			recDoc.setStatus(Constant.RECEIVE_WEICHULI);
			recDoc.setYffs(String
					.valueOf(doc.getDocExchangeBox().getYffs() == 0 ? 1 : doc
							.getDocExchangeBox().getYffs()));
			// 保存RECDOC对象
			receiveDocDao.saveRecDOC(recDoc);

			// 手动插入正文附件
			for (ReceiveAttachments attachments : attList) {
				receiveDocDao.saveRecDocAtts(attachments);
			}
			return "success";
		} catch (Exception e) {
			logger.error("收取附件发生异常,请检查,请检查附件是否存在?");
			return "error";
		}
		// //获取本部门的验证码
		// String userKey = GenUserKey.genUserKey(recDoc.getQueueXto());
		// @SuppressWarnings("unused")
		// String temp = docExchangeClient.receiveDoc(userKey, idString);//
		// 通知公文交换系统已收文
	}

	/**
	 * 描述：从公文交换收取全部<br>
	 * 
	 * @param doc
	 * @param idString
	 * @return String
	 * @throws Exception
	 */
	public String addAllReceiveDoc(Map<String, DocExchangeVo> tobeRecevicedoc,
			String departmentId) throws Exception {
		int i = 0;
		String info = "";
		Set<String> docIds = tobeRecevicedoc.keySet();
		for (String docId : docIds) {
			// 获取抄送单位和主送单位
			i++;
			String xto = tobeRecevicedoc.get(docId).getDocExchangeBox()
					.getXto();
			String xcc = tobeRecevicedoc.get(docId).getDocExchangeBox()
					.getXcc();
			if (xto != null && xto.indexOf(departmentId) != -1
					|| (xcc != null && xcc.indexOf(departmentId) != -1)) {
				DocExchangeVo doc = tobeRecevicedoc.get(docId);
				if (doc != null) {
					// 收取一个公文
					String result = this.addReceiveOneDocForAll(doc, docId);
					if (Constant.SUCCESS.equals(result.toUpperCase())) {
						// 获取本部门的验证码
						String userKey = GenUserKey.genUserKey(doc.getQueue()
								.getXto());
						@SuppressWarnings("unused")
						String temp = docExchangeClient.receiveDoc(userKey,
								docId);// 通知公文交换系统已收文
					} else if (result == null
							|| (result != null && !Constant.SUCCESS
									.equals(result.toUpperCase()))) {
						break;
					}
					logger.info("**********收取的公文标题为"
							+ tobeRecevicedoc.get(docId).getDocExchangeBox()
									.getTitle() + "************");
				}
			}
		}
		logger.info("*************总共收取了" + docIds == null ? 0 : docIds.size()
				+ "个公文***************");
		if (String.valueOf(docIds == null ? 0 : docIds.size()).equals(
				String.valueOf(i))) {
			info = "success";
		} else {
			info = "********还有" + String.valueOf(docIds.size() - i)
					+ "个没有收取成功，请检查收取情况************";
			logger.warn(info);
		}
		return info;
	}

	/**
	 * 描述：根据条件查询已收列表<br>
	 * 
	 * @param currentPage
	 * @param numPerPage
	 * @param xto
	 * @param keyword
	 * @param content
	 * @param status
	 * @return DTPageBean
	 */
	public DTPageBean queryReceivedDoc(int currentPage, int numPerPage,
			List<String> xto, String keyword, String content, String status,
			String timeType, String startTime, String endTime) {
		return receiveDocDao.queryReceivedDoc(currentPage, numPerPage, xto,
				keyword, content, status, timeType, startTime, endTime);
	}

	/**
	 * 描述：根据DOCGUID得到RECEVIEDOC对象<br>
	 * 
	 * @param docguid
	 * @return RecDoc
	 */
	public RecDoc getRecDocByDocguid(String docguid) {
		return receiveDocDao.getRecDocByDocguid(docguid);
	}

	public void saveRecDoc(RecDoc recDoc) {
		receiveDocDao.saveRecDOC(recDoc);

	}

	public void saveCheckInRecDoc(CheckInRecDoc docCheckin) {
		receiveDocDao.saveCheckInRecDoc(docCheckin);
	}

	// 根据docguid 修改备注及其状态位
	public void updateBeizhuByDocguid(RecDoc recDoc) {
		receiveDocDao.updateBeizhuByDocguid(recDoc);
	}

	/**
	 * 
	 * 描述：根据条件查询已收公文数目
	 * 
	 * @param deps
	 * @param wh
	 * @param title
	 * @param sendername
	 * @param statuskey
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @param keyword
	 * @return long
	 * 
	 *         作者:WangXF<br>
	 *         创建时间:2012-2-4 下午02:38:23
	 */
	public long queryReceivedDocCounts(List<String> deps, String wh,
			String title, String sendername, String statuskey, String timeType,
			String startTime, String endTime, String keyword) {
		return receiveDocDao.queryReceivedDocCounts(deps, wh, title,
				sendername, statuskey, timeType, startTime, endTime, keyword);
	}

	public long queryReceivedDocCounts(List<String> deps, String wh,
			String title, String sendername, String statuskey, String timeType,
			String startTime, String endTime, String keyword, boolean isIN,
			String whs) {
		return receiveDocDao.queryReceivedDocCounts(deps, wh, title,
				sendername, statuskey, timeType, startTime, endTime, keyword,
				isIN, whs);
	}

	/**
	 * 
	 * 描述：根据条件查询已收列表
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param deps
	 * @param wh
	 * @param title
	 * @param sendername
	 * @param statuskey
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @param keyword
	 * @return DTPageBean
	 * 
	 *         作者:WangXF<br>
	 *         创建时间:2012-2-4 下午02:16:06
	 */
	public DTPageBean queryReceivedDoc(int currentPage, int pageSize,
			List<String> deps, String wh, String title, String sendername,
			String statuskey, String timeType, String startTime,
			String endTime, String keyword) {
		return receiveDocDao.queryReceivedDoc(currentPage, pageSize, deps, wh,
				title, sendername, statuskey, timeType, startTime, endTime,
				keyword);
	}

	public DTPageBean queryReceivedDoc(int currentPage, int pageSize,
			List<String> deps, String wh, String title, String sendername,
			String statuskey, String timeType, String startTime,
			String endTime, String keyword, boolean isIN, String whs) {
		return receiveDocDao.queryReceivedDoc(currentPage, pageSize, deps, wh,
				title, sendername, statuskey, timeType, startTime, endTime,
				keyword, isIN, whs);
	}

	/**
	 * 
	 * 描述：根据查询条件导出已收公文
	 * 
	 * @param deps
	 * @param keyword
	 * @param content
	 * @param statuskey
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @param keyword2
	 * @param endTime2
	 * @param cols
	 * @return List<String[]>
	 * 
	 *         作者:WangXF<br>
	 *         创建时间:2012-2-3 上午10:28:30
	 */
	public List<String[]> queryReceivedDocForExport(List<String> deps,
			String wh, String title, String sendername, String statuskey,
			String timeType, String startTime, String endTime, String keyword,
			String[] cols) {
		List<Object[]> objList = receiveDocDao.queryReceivedDocForExport(deps,
				wh, title, sendername, statuskey, timeType, startTime, endTime,
				keyword, cols);
		List<String[]> dataList = new ArrayList<String[]>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < objList.size(); i++) {
			Object[] obj = objList.get(i);
			String[] s = new String[obj.length];
			for (int j = 0; j < obj.length; j++) {
				Object o = obj[j];
				if (o instanceof String) {
					s[j] = o.toString();
				}
				if (o instanceof Date) {
					s[j] = sf.format((Date) o);
				}
			}
			dataList.add(s);
		}
		return dataList;
	}

	public List<String[]> queryReceivedDocForExport(List<String> deps,
			String wh, String title, String sendername, String statuskey,
			String timeType, String startTime, String endTime, String keyword,
			String[] cols, boolean isIN, String whs) {
		List<Object[]> objList = receiveDocDao.queryReceivedDocForExport(deps,
				wh, title, sendername, statuskey, timeType, startTime, endTime,
				keyword, cols, isIN, whs);
		List<String[]> dataList = new ArrayList<String[]>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < objList.size(); i++) {
			Object[] obj = objList.get(i);
			String[] s = new String[obj.length];
			for (int j = 0; j < obj.length; j++) {
				Object o = obj[j];
				if (o instanceof String) {
					s[j] = o.toString();
				}
				if (o instanceof Date) {
					s[j] = sf.format((Date) o);
				}
			}
			dataList.add(s);
		}
		return dataList;
	}

	public void viewStatus(String userId, RecDoc recDoc) {
		receiveDocDao.setViewStatus(userId, recDoc);
	}

	public List<RecDoc> checkViewStatus(DTPageBean db, String userId) {
		List<RecDoc> dataList = (List<RecDoc>) db.getDataList();
		Iterator<RecDoc> it = dataList.iterator();
		List<RecDoc> list = new ArrayList<RecDoc>();
		while (it.hasNext()) {
			RecDoc recDoc = it.next();
			if (recDoc.getViewStatus()!=null) {
				String[] status = recDoc.getViewStatus().split(";");
				for (String statu : status) {
					if (statu.split(",")[0].equals(userId)) {
						recDoc.setRecDocViewStatus(Integer.valueOf(statu
								.split(",")[1]));
					}
				}
			}
			list.add(recDoc);
		}
		return list;
	}

	public String findDepIdByEmpId(String userId) {
		return receiveDocDao.findDepIdByEmpId(userId);
	}

	/**
	 * 已收列表(门户)
	 */
	public String queryReceivedDocForPortal(String url, List<String> deps,String callback) {
		StringBuilder sb =new StringBuilder();
		sb.append(";" + callback + "({");
		sb.append("data:{");
		sb.append("list:[");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (deps != null && deps.size() > 0) {
			for (int i = 0; i < deps.size(); i++) {
				List<RecDoc> recDocList = receiveDocDao.queryReceivedDocForPortal(url,deps);
				if (recDocList.size() != 0 && recDocList != null) {
					// 门户中只取前5条展现
					if (recDocList.size() > 5) {
						for (int j = 0; j < 5; j++) {
							sb.append("{title:\""+recDocList.get(j).getTitle()+"\",");
							sb.append("date:\""+ sdf.format(recDocList.get(j).getRecDate()) + "\",");
							sb.append("link:\""+url+"/rec_receivedDocDetail.do?id="+recDocList.get(j).getDocguid()+"&deptId="+deps.get(i)+"\"");
							sb.append("},");
						}
					} else {
						for (RecDoc recDoc : recDocList) {
							sb.append("{title:\""+recDoc.getTitle()+"\",");
							sb.append("date:\""+ sdf.format(recDoc.getRecDate()) + "\",");
							sb.append("link:\""+url+"/rec_receivedDocDetail.do?id="+recDoc.getDocguid()+"&deptId="+deps.get(i)+"\"");
							sb.append("},");
						}
					}
				}
			}
		}
		if (sb != null && sb.lastIndexOf(",") > -1)
			sb = new StringBuilder(sb.substring(0, sb.length() - 1));
		sb.append("]},");
		// css
		sb.append("css:\"");
		sb.append(".R_PORTAL_cbox td{padding:0;line-height:22px;height:22px;}.R_PORTAL_cbox td.date{width:80px;overflow:hidden;text-align:center}.R_PORTAL_cbox td{vertical-align:top}.R_PORTAL_cbox td table td{vertical-align:middle}\"");
		sb.append(",");
		// js
		sb.append("js:\"");
		sb.append("\"");
		sb.append(",");
		// template
		sb.append("template:'");
		sb
				.append("<table class=\"R_PORTAL_cbox\" width=\"100%\" border=\"0\">{@if list.length!=0}{@each list as ls}<tr><td><a href=\"${ls.link}\">${ls.title}</a></td><td class=\"date\">${ls.date}</td></tr>{@/each}{@/if}{@if list.length==0}<tr>暂无信息！</tr>{@/if}</table>'");

		sb.append("})");
		return sb.toString();
	}

	@Override
	public List<RecDoc> getReceivedDocList(String conditionSql,
			Integer pageIndex, Integer pageSize) {
		return receiveDocDao.getReceivedDocList(conditionSql, pageIndex, pageSize);
	}

	@Override
	public int getReceivedDocCount(String conditionSql) {
		return receiveDocDao.getReceivedDocCount(conditionSql);
	}

	@Override
	public void saveReceiveProcessShip(ReceiveProcessShip receiveProcessShip) {
		receiveDocDao.saveReceiveProcessShip(receiveProcessShip);
	}

	@Override
	public Map<String, ReceiveProcessShip> findReceiveProcessShipList(
			List<RecDoc> list) {
		Map<String, ReceiveProcessShip> map  = new HashMap<String, ReceiveProcessShip>();
		for(int i=0; list!=null && i<list.size(); i++){
			String recId = list.get(i).getDocguid();
			ReceiveProcessShip receiveProcessShip = receiveDocDao.findReceiveProcessShipByRecId(recId);
			if(receiveProcessShip!=null){
				String processId = receiveProcessShip.getProcessId();
				if(processId!=null && !processId.equals("")){
					String instanceId = receiveProcessShip.getInstanceId();
					//是否作废
					List<WfProcess> wflist = tableInfoDao.getProcessList(instanceId);
					String itemId = "";
					if(wflist!=null && wflist.size()>1){
						receiveProcessShip.setStatus(2);
						itemId = wflist.get(0).getItemId();
					}else{
						receiveProcessShip.setStatus(1);
					}
					if(itemId!=null && !itemId.equals("")){
						WfItem wfItem = itemDao.getItemById(itemId);
						receiveProcessShip.setItemName(wfItem.getVc_sxmc());
					}
				}else{
					receiveProcessShip.setStatus(0);	//表示未收(展示收入待办)
				}
				map.put(recId, receiveProcessShip);
			}
		}
		return map;
	}

	@Override
	public ReceiveProcessShip findReceiveProcessShipByRecId(String recId) {
		return receiveDocDao.findReceiveProcessShipByRecId(recId);
	}

	@Override
	public List<ReceiveXml> findReceiveXmlList(List<String> deps) {
		List<ReceiveXml> list = new ArrayList<ReceiveXml>();
		String dep = null;
		String userKey = null;
		String xml = null;
		if (deps != null && deps.size() > 0) {
			ReceiveXml receiveXml = null;
			for (int i = 0; i < deps.size(); i++) {
				dep = deps.get(i);
				userKey = GenUserKey.genUserKey(dep);
				xml = docExchangeClient.getToBeReceivedList(userKey);// 调用WS接口获取接收列表
				receiveXml = new ReceiveXml();
				try {
					// 根据公文交接接口返回的xml信息组装成自己需要的对象
					receiveXml.setDepId(dep);
					receiveXml.setXml(xml);
					list.add(receiveXml);
				} catch (Exception e) {
					logger.error(e.getMessage());
					logger.error("**************ReceiveDocServiceImpl.getToReceivedList()*****************");
					logger.error("***********获取待收列表失败，可能导致的原因是WS接口调用失败****************");
				}
			}
		}
		return list;
	}

	@Override
	public void saveReceiveXml(ReceiveXml receiveXml) {
		receiveDocDao.saveReceiveXml(receiveXml);
		
	}

	@Override
	public List<ReceiveXml> findReceiveXmlByRecId(String recId) {
		return receiveDocDao.findReceiveXmlByRecId(recId);
	}

	@Override
	public void updateReceiveProcessShip(ReceiveProcessShip receiveProcessShip) {
		receiveDocDao.updateReceiveProcessShip(receiveProcessShip);
	}
}
