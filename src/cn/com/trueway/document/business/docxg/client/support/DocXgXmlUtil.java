package cn.com.trueway.document.business.docxg.client.support;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.document.business.docxg.client.vo.BaseDoc;
import cn.com.trueway.document.business.docxg.client.vo.DocExchangeVo;
import cn.com.trueway.document.business.docxg.client.vo.GwDepart;
import cn.com.trueway.document.business.docxg.client.vo.GwDepartext;
import cn.com.trueway.document.business.docxg.client.vo.OutDoc;
import cn.com.trueway.document.business.docxg.client.vo.Queue;
import cn.com.trueway.document.business.docxg.client.vo.VGwDepart;
import cn.com.trueway.document.business.model.Doc;
import cn.com.trueway.document.business.model.LowDoc;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsext;
import cn.com.trueway.docxg.object.DEPARTMENTCHILD;
import cn.com.trueway.docxg.object.DEPARTMENTSDocument;
import cn.com.trueway.docxg.object.DEPARTMENTSORT;
import cn.com.trueway.docxg.object.DOCSUMMARYSDocument;
import cn.com.trueway.docxg.object.DocAtt;
import cn.com.trueway.docxg.object.DocAttExt;
import cn.com.trueway.docxg.object.DocSummary;
import cn.com.trueway.docxg.object.ZwAttType;
import cn.com.trueway.docxg.object.DEPARTMENTSDocument.DEPARTMENTS;
import cn.com.trueway.docxg.object.DEPARTMENTSORT.DEPARTMENTCHILDREN;
import cn.com.trueway.docxg.object.DOCSUMMARYSDocument.DOCSUMMARYS;
import cn.com.trueway.docxg.object.DocSummary.ATTEXTS;
import cn.com.trueway.docxg.object.DocSummary.ATTS;
import cn.com.trueway.docxg.object.DocSummary.WENHAO;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
/**
 * 描述：与公文交换接口交换的公共信息处理类<br>
 * 作者：周雪贇<br>
 * 创建时间：2011-12-1 上午10:40:23<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public final class DocXgXmlUtil {
	
	
	
	private static Logger logger=Logger.getLogger("DocXgXmlUtil");
	/**
	 * 生成发文所需xml 
	 * //FIXME 现在值是写死的，需要增加传入的参数然后动态设值,其他地方不要随意修改
	 * @return 符合公文交换平台发文xml规则要求的xml
	 */
	synchronized public final static String genXmlForSendDoc(Doc doc){
		DOCSUMMARYSDocument document = DOCSUMMARYSDocument.Factory.newInstance();
        DOCSUMMARYS docsummarys = document.addNewDOCSUMMARYS();
        {
        	DocSummary docSummary = docsummarys.addNewDOCSUMMARY();
        	//<!--文号 = 标识 + "[" + 年号 + "]" + 发文号 + "号"-->
        	{
	        	WENHAO wenhao = docSummary.addNewWENHAO();
	        	wenhao.setJGDZ(doc.getJgdz());
	        	wenhao.setFWNH(doc.getFwnh());
	        	wenhao.setFWXH(doc.getFwxh());
        	}
        	//<!--CEBID(36位)--> 
        	docSummary.setCEBID(doc.getCebid());
        	//<!--公文标题--> 
        	docSummary.setTITLE(doc.getTitle());
        	//<!--公文主题-->
        	docSummary.setKEYWORD(doc.getKeyword());
        	//<!--公文类型-->
        	docSummary.setTYPE(doc.getDoctype());
        	//<!--级别-->
        	docSummary.setPRIORITY(DocXgXmlUtil.longToStr(doc.getPriority()));
        	//<!--收文单位ID-->
        	docSummary.setTODEPIDS(doc.getXto());
        	//<!--收文单位名称-->
        	docSummary.setTODEPNAMES(doc.getXtoName());
        	//<!--抄送单位ID-->
        	docSummary.setCCDEPIDS(doc.getXcc());
        	//<!--抄送单位名称-->
        	docSummary.setCCDEPNAMES(doc.getXccName());
        	//<!--发送单位ID-->
        	docSummary.setSENDERID(doc.getSender());
        	//<!--发送单位名称-->
        	docSummary.setSENDERNAME(doc.getFwjg());
        	//<!--印发单位-->
        	docSummary.setSIGNDEPNAME(doc.getYfdw());
        	//<!--签发人-->
        	docSummary.setSIGNER(doc.getQfr());
        	//<!--印发日期yyyy-MM-dd-->
        	docSummary.setYFDATE(doc.getYfrq());
        	//<!--印发份数-->
        	if(doc.getYffs()!=null&&!"".equals(doc.getYffs())){
        		docSummary.setYFSUM(doc.getYffs().shortValue());
        	}else{
        		docSummary.setYFSUM((short)0);
        	}
        	
        	String downloadUrl = SystemParamConfigUtil.getParamValueByParam("downloadForDocxgUrl"); 
        	
        	//<!--正文附件列表-->
        	{
        		ATTS atts = docSummary.addNewATTS();
        		int attsSize = doc.getAtts().size();
        		for(int i=0;i<attsSize;i++){
        			SendAttachments attachments = (SendAttachments)doc.getAtts().get(i);
        			if(("doc").equals(attachments.getFiletype().toLowerCase())){
        				DocAtt att = atts.addNewATT();
            			att.setNAME(attachments.getFilename());
            			att.setTYPE(ZwAttType.DOC);
            			att.setFILESIZE(BigDecimal.valueOf(attachments.getFilesize()));
            			att.setURL(downloadUrl+attachments.getLocalation());//mengxj 2010年9月14日 17:44:01 修改 直接传递文件路径
        			}else{
        				DocAtt att = atts.addNewATT();
        				att.setNAME(attachments.getFilename());
            			att.setTYPE(ZwAttType.CEB);
            			att.setFILESIZE(BigDecimal.valueOf(attachments.getFilesize()));
            			att.setURL(downloadUrl+attachments.getLocalation());//mengxj 2010年9月14日 17:44:01 修改 直接传递文件路径
        			}
        		}
        	}
        	
        	//<!--正文附件列表-->  
        	{
        		ATTEXTS attexts = docSummary.addNewATTEXTS();
        		int attextsSize = doc.getAttExts().size();
        		for (int i = 0; i < attextsSize; i++) {
        			SendAttachmentsext attachmentsExt = (SendAttachmentsext)doc.getAttExts().get(i);
        			DocAttExt att = attexts.addNewATTEXT();
	    			att.setNAME(attachmentsExt.getFilename());
	    			att.setTYPE(attachmentsExt.getFiletype());
	    			att.setFILESIZE(BigDecimal.valueOf(attachmentsExt.getFilesize()));
	    			att.setIND((short)1);
	    			att.setURL(downloadUrl+attachmentsExt.getLocalation());//mengxj 2010年9月14日 17:44:01 修改 直接传递文件路径
				}
        	}
        }
        XmlOptions xmlOptions = new XmlOptions();
		xmlOptions.setUseDefaultNamespace();
		xmlOptions.setCharacterEncoding("UTF-8");
		String xml = document.xmlText(xmlOptions);
		return xml;
	}
	
	
	synchronized public final static String genXmlForSendOutDoc(OutDoc doc){
		DOCSUMMARYSDocument document = DOCSUMMARYSDocument.Factory.newInstance();
        DOCSUMMARYS docsummarys = document.addNewDOCSUMMARYS();
        {
        	DocSummary docSummary = docsummarys.addNewDOCSUMMARY();
        	//<!--文号 = 标识 + "[" + 年号 + "]" + 发文号 + "号"-->
        	{
	        	WENHAO wenhao = docSummary.addNewWENHAO();
	        	wenhao.setJGDZ(doc.getJgdz());
	        	wenhao.setFWNH(doc.getFwnh());
	        	wenhao.setFWXH(doc.getFwxh());
        	}
        	//<!--CEBID(36位)--> 
        	docSummary.setCEBID(doc.getCebid());
        	//<!--公文标题--> 
        	docSummary.setTITLE(doc.getTitle());
        	//<!--公文主题-->
        	docSummary.setKEYWORD(doc.getKeyword());
        	//<!--公文类型-->
        	docSummary.setTYPE(doc.getDoctype());
        	//<!--级别-->
        	docSummary.setPRIORITY(DocXgXmlUtil.longToStr(doc.getPriority()));
        	//<!--收文单位ID-->
        	docSummary.setTODEPIDS(doc.getXto());
        	//<!--收文单位名称-->
        	docSummary.setTODEPNAMES(doc.getXtoName());
        	//<!--抄送单位ID-->
        	docSummary.setCCDEPIDS(doc.getXcc());
        	//<!--抄送单位名称-->
        	docSummary.setCCDEPNAMES(doc.getXccName());
        	//<!--发送单位ID-->
        	docSummary.setSENDERID(doc.getSender());
        	//<!--发送单位名称-->
        	docSummary.setSENDERNAME(doc.getFwjg());
        	//<!--印发单位-->
        	docSummary.setSIGNDEPNAME(doc.getYfdw());
        	//<!--签发人-->
        	docSummary.setSIGNER(doc.getQfr());
        	//<!--印发日期yyyy-MM-dd-->
        	docSummary.setYFDATE(doc.getYfrq());
        	//<!--印发份数-->
        	if(doc.getYffs()!=null&&!"".equals(doc.getYffs())){
        		docSummary.setYFSUM(doc.getYffs().shortValue());
        	}else{
        		docSummary.setYFSUM((short)0);
        	}
        	
        	String downloadUrl = SystemParamConfigUtil.getParamValueByParam("downloadForDocxgUrl"); 
        	
        	//<!--正文附件列表-->
        	{
        		ATTS atts = docSummary.addNewATTS();
        		int attsSize = doc.getAtts().size();
        		for(int i=0;i<attsSize;i++){
        			SendAttachments attachments = (SendAttachments)doc.getAtts().get(i);
        			if(("doc").equals(attachments.getFiletype().toLowerCase())){
        				DocAtt att = atts.addNewATT();
            			att.setNAME(attachments.getFilename());
            			att.setTYPE(ZwAttType.DOC);
            			att.setFILESIZE(BigDecimal.valueOf(attachments.getFilesize()));
            			att.setURL(downloadUrl+attachments.getLocalation());
        			}else{
        				DocAtt att = atts.addNewATT();
        				att.setNAME(attachments.getFilename());
            			att.setTYPE(ZwAttType.CEB);
            			att.setFILESIZE(BigDecimal.valueOf(attachments.getFilesize()));
            			att.setURL(downloadUrl+attachments.getLocalation());
        			}
        		}
        	}
        	
        	//<!--正文附件列表-->  
        	{
        		ATTEXTS attexts = docSummary.addNewATTEXTS();
        		int attextsSize = doc.getAttExts().size();
        		for (int i = 0; i < attextsSize; i++) {
        			SendAttachmentsext attachmentsExt = (SendAttachmentsext)doc.getAttExts().get(i);
        			DocAttExt att = attexts.addNewATTEXT();
	    			att.setNAME(attachmentsExt.getFilename());
	    			att.setTYPE(attachmentsExt.getFiletype());
	    			att.setFILESIZE(BigDecimal.valueOf(attachmentsExt.getFilesize()));
	    			att.setIND((short)1);
	    			att.setURL(downloadUrl+attachmentsExt.getLocalation());
				}
        	}
        }
        XmlOptions xmlOptions = new XmlOptions();
		xmlOptions.setUseDefaultNamespace();
		xmlOptions.setCharacterEncoding("UTF-8");
		String xml = document.xmlText(xmlOptions);
		return xml;
	}
	
	
	/**
	 * 根据公文交接接口返回的xml信息组装成自己需要的对象
	 * //FIXME 现在无返回值，应该修改为需要返回的对象，里面提供了获取值的方法,其他地方不要随意修改
	 * @param xml 公文接口返回的公文信息xml
	 * @throws XmlException
	 */
	synchronized public final static List<DocExchangeVo> genDocModelFromXML(String xml,String xtoId) throws XmlException{
		if(xml==null || xml.trim().equals("")){
			return null; 
		}
		XmlOptions xmlOptions = new XmlOptions();
		xmlOptions.setCharacterEncoding("UTF-8");
		Map<String,String> map = new HashMap<String,String>();
        map.put("", "http://www.trueway.com.cn/docxg/object");
        xmlOptions.setLoadSubstituteNamespaces(map);
        
		DOCSUMMARYSDocument document = DOCSUMMARYSDocument.Factory.parse(xml,xmlOptions);
		DOCSUMMARYS docsummarys = document.getDOCSUMMARYS();
		DocSummary[] docSummaryArray = docsummarys.getDOCSUMMARYArray();
		
		List<DocExchangeVo> docExchangeVos = new ArrayList<DocExchangeVo>();
		
		for (int i = 0; i < docSummaryArray.length; i++) {
			
			List<ReceiveAttachments> excatts = new ArrayList<ReceiveAttachments>() ;
			List<ReceiveAttachmentsext> excattsext = new ArrayList<ReceiveAttachmentsext>();
			DocSummary docSummary = docSummaryArray[i];
			//FIXME 需要从docSummary获取信息设置到自己的model中
			//xjp start
			BaseDoc docExchangeBox = new BaseDoc();
			Queue queue = new Queue();
			docExchangeBox.setDocguid(docSummary.getDOCGUID());
			docExchangeBox.setSender(docSummary.getSENDERNAME());
			docExchangeBox.setXto(docSummary.getTODEPIDS());
			docExchangeBox.setXcc(docSummary.getCCDEPIDS());
			docExchangeBox.setTitle(docSummary.getTITLE());
			docExchangeBox.setKeyword(docSummary.getKEYWORD());
			docExchangeBox.setDoctype(docSummary.getTYPE());
			docExchangeBox.setSubmittm(DocXgXmlUtil.strTodate(docSummary.getSENDERTIME()));
			docExchangeBox.setPriority(strToLong(docSummary.getPRIORITY()));
			docExchangeBox.setJgdz(docSummary.getWENHAO().getJGDZ());
			docExchangeBox.setFwxh(docSummary.getWENHAO().getFWXH());
			docExchangeBox.setFwjg(docSummary.getSENDERNAME());
			docExchangeBox.setSendstat(1L);
			docExchangeBox.setFwnh(docSummary.getWENHAO().getFWNH());
			docExchangeBox.setYfdw(docSummary.getSIGNDEPNAME());
			docExchangeBox.setYffs(java.lang.Long.valueOf(docSummary.getYFSUM()));
			docExchangeBox.setYfrq(docSummary.getYFDATE());
			docExchangeBox.setQfr(docSummary.getSIGNER());
			docExchangeBox.setCebid(docSummary.getCEBID());
			docExchangeBox.setXtoName(docSummary.getTODEPNAMES());
			docExchangeBox.setXccName(docSummary.getCCDEPNAMES());
			queue.setId(docSummary.getID());
			queue.setDocguid(docSummary.getDOCGUID());
			queue.setGottm(DocXgXmlUtil.strTodate(docSummary.getSENDERTIME()));
			queue.setStatus("未接收");
			queue.setStatuscode(0L);
			queue.setXto(xtoId);
			
			//xjp end
			/*-----------------------------------------正文附件------------------------------------------------------------------------------*/
			ATTS atts = docSummary.getATTS();
			DocAtt[] attArrays = atts.getATTArray();
			for (int j = 0; j < attArrays.length; j++) {
				ReceiveAttachments att = new ReceiveAttachments();
				DocAtt docAtt = attArrays[j];
				String attId = UUID.randomUUID().toString();
				att.setId(attId);
				att.setDocguid(docSummary.getID());
				att.setFilename(docAtt.getNAME());
				att.setFiletype(docAtt.getTYPE().toString());
				att.setFilesize(docAtt.getFILESIZE().longValue());
				att.setFiletime(DocXgXmlUtil.strTodate(docSummary.getSENDERTIME()));
				att.setFileindex(new Integer(j).longValue());
				att.setLocalation(DocXgXmlUtil.downloadFromUrl(docAtt.getURL(), docAtt.getNAME(), "zw"));
				//att.setLocalation(docAtt.getURL());
				excatts.add(att);
			}
			/*-----------------------------------------正文附件结束------------------------------------------------------------------------------*/
			/*-----------------------------------------其他附件------------------------------------------------------------------------------*/
			ATTEXTS attexts = docSummary.getATTEXTS();
			DocAttExt[] attextArrays = attexts.getATTEXTArray();
			for (int j = 0; j < attextArrays.length; j++) {
				ReceiveAttachmentsext attext = new ReceiveAttachmentsext();
				DocAttExt docAttExt = attextArrays[j];
				String attextId = UUID.randomUUID().toString();
				attext.setId(attextId);
				attext.setDocguid(docSummary.getID());
				attext.setFileindex(new Integer(j).longValue());
				attext.setFiletype(docAttExt.getTYPE());
				attext.setFilesize(docAttExt.getFILESIZE().longValue());
				attext.setFiletime(DocXgXmlUtil.strTodate(docSummary.getSENDERTIME()));
				attext.setFilename(docAttExt.getNAME());
				attext.setLocalation(DocXgXmlUtil.downloadFromUrl(docAttExt.getURL(), docAttExt.getNAME(), "fj"));
				//attext.setLocalation(docAttExt.getURL());
				excattsext.add(attext);
			}
			/*-----------------------------------------其他附件------------------------------------------------------------------------------*/
			DocExchangeVo docExchangeVo = new DocExchangeVo(docExchangeBox,queue,excatts,excattsext);
			docExchangeVos.add(docExchangeVo);
		}
		return docExchangeVos; 
	}
	
	/**
	 * 根据接口返回的收发文单位XML转换为自己的对象
	 * //FIXME 转换为自己的对象
	 * @param xml
	 * @throws XmlException
	 */
	synchronized public static final List<VGwDepart> genDepartmentModelsFromXML(String xml) throws XmlException{
		if(xml==null || xml.trim().equals("")){
			return null;
		}
		XmlOptions xmlOptions = new XmlOptions();
		xmlOptions.setCharacterEncoding("UTF-8");
		Map<String,String> map = new HashMap<String,String>();
        map.put("", "http://www.trueway.com.cn/docxg/object");
        xmlOptions.setLoadSubstituteNamespaces(map);
        
        DEPARTMENTSDocument document = DEPARTMENTSDocument.Factory.parse(xml, xmlOptions);
        DEPARTMENTS departments = document.getDEPARTMENTS();
        
        DEPARTMENTSORT[] departmentsorts = departments.getDEPARTMENTSORTArray();
        
        List<VGwDepart> vgwdList = new ArrayList<VGwDepart>();
        
        for (int i = 0; i < departmentsorts.length; i++) {
        	VGwDepart vDepart = new VGwDepart();
        	GwDepart departs = new GwDepart();
        	List<GwDepartext> subDepartextList = new ArrayList<GwDepartext>();
        	//DOCEXCHANGE_DEPART
			DEPARTMENTSORT departmentsort = departmentsorts[i];
			departs.setId(departmentsort.getID());
			departs.setName(departmentsort.getNAME());
			departs.setInd(departmentsort.getIND().longValue());
			
			DEPARTMENTCHILDREN departmentchildren = departmentsort.getDEPARTMENTCHILDREN();
			DEPARTMENTCHILD[] departmentchilds = departmentchildren.getDEPARTMENTCHILDArray();
			for (int j = 0; j < departmentchilds.length; j++) {
				GwDepartext departext = new GwDepartext();
				//DOCEXCHANGE_DEPARTEXT
				DEPARTMENTCHILD departmentchild = departmentchilds[j];
				departext.setGuid(departmentchild.getID());
				departext.setName(departmentchild.getNAME());
				departext.setGZName(departmentchild.getGZNAME());
				departext.setInd(departmentchild.getIND().longValue());
				subDepartextList.add(departext);
			}
			vDepart.setDepart(departs);
			vDepart.setSubDepartextList(subDepartextList);
			vgwdList.add(vDepart);
		}
        return vgwdList;
	}
	
	
	public static Date strTodate(String str){
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = null;
		 try {
			date =  format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Long strToLong(String str){
		if ("特急".equals(str)) {
			return 0L;
		} else if ("紧急".equals(str)) {
			return 1L;
		} else if ("急件".equals(str)) {
			return 2L;
		} else {
			return 3L;
		}
	}
	
	public static String longToStr(long priority){
		if (priority == 0) {
			return "特急";
		} else if (priority == 1) {
			return "紧急";
		} else if (priority == 2) {
			return "急件";
		} else {
			return "平件";
		}
	}
	
	/**
	 * 
	 * @Title: downloadFromUrl
	 * @Description: 收文时附件下载
	 * @param fileUrl
	 * @param fileName
	 * @param zwfj
	 * @return String    返回类型
	 * @author mengxj
	 */
	public static String downloadFromUrl(String fileUrl, String fileName, String zwfj) {
		// 此方法只能用户HTTP协议
		
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到文件在服务器上的基路径
		String dstPath = null ;
//		if(("zw".equals(zwfj))){
//			dstPath = FileUploadUtils.getRealFilePath(fileName,basePath, "OA_EXCDOC_ATTACACHMENT/"); // 得到文件在服务器上存储的唯一路径,并创建存储目录
//		}else{
//			dstPath = FileUploadUtils.getRealFilePath(fileName,basePath, "OA_EXCDOC_ATTACACHMENTEXT/"); // 得到文件在服务器上存储的唯一路径,并创建存储目录
//		}
		dstPath = FileUploadUtils.getRealFilePath(fileName,basePath, Constant.UPLOAD_FILE_PATH);//统一上传的路径
		logger.debug(dstPath);
		try {
			URL url = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			DataOutputStream out = new DataOutputStream(new FileOutputStream(basePath + dstPath));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.flush();
			out.close();
			in.close();
			return dstPath;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 
	 * 描述：解析xml生成List<BaseDoc>
	 * @param xml
	 * @return
	 * @throws XmlException List<BaseDoc>
	 * 作者:蔡亚军
	 * 创建时间:2015-1-23 上午9:47:33
	 */
	synchronized public final static List<BaseDoc> findBaseDocListFromXml(String xml)  throws XmlException{
		if(xml==null || xml.trim().equals("")){
			return null; 
		}
		XmlOptions xmlOptions = new XmlOptions();
		xmlOptions.setCharacterEncoding("UTF-8");
		Map<String,String> map = new HashMap<String,String>();
        map.put("", "http://www.trueway.com.cn/docxg/object");
        xmlOptions.setLoadSubstituteNamespaces(map);
        
		DOCSUMMARYSDocument document = DOCSUMMARYSDocument.Factory.parse(xml,xmlOptions);
		DOCSUMMARYS docsummarys = document.getDOCSUMMARYS();
		DocSummary[] docSummaryArray = docsummarys.getDOCSUMMARYArray();
		List<BaseDoc> baseDocList = new ArrayList<BaseDoc>();
		for (int i = 0; i < docSummaryArray.length; i++) {
			DocSummary docSummary = docSummaryArray[i];
			//FIXME 需要从docSummary获取信息设置到自己的model中
			BaseDoc docExchangeBox = new BaseDoc();
			String wh = docSummary.getWENHAO().getJGDZ() + docSummary.getWENHAO().getFWNH() + docSummary.getWENHAO().getFWXH();
			docExchangeBox.setDocguid(docSummary.getDOCGUID());
			docExchangeBox.setSender(docSummary.getSENDERNAME());
			docExchangeBox.setXto(docSummary.getTODEPIDS());
			docExchangeBox.setXcc(docSummary.getCCDEPIDS());
			docExchangeBox.setTitle(docSummary.getTITLE());
			docExchangeBox.setKeyword(docSummary.getKEYWORD());
			docExchangeBox.setDoctype(docSummary.getTYPE());
			docExchangeBox.setSubmittm(DocXgXmlUtil.strTodate(docSummary.getSENDERTIME()));
			docExchangeBox.setPriority(strToLong(docSummary.getPRIORITY()));
			docExchangeBox.setJgdz(docSummary.getWENHAO().getJGDZ());
			docExchangeBox.setFwxh(docSummary.getWENHAO().getFWXH());
			docExchangeBox.setFwjg(docSummary.getSENDERNAME());
			docExchangeBox.setSendstat(1L);
			docExchangeBox.setFwnh(docSummary.getWENHAO().getFWNH());
			docExchangeBox.setYfdw(docSummary.getSIGNDEPNAME());
			docExchangeBox.setYffs(java.lang.Long.valueOf(docSummary.getYFSUM()));
			docExchangeBox.setYfrq(docSummary.getYFDATE());
			docExchangeBox.setQfr(docSummary.getSIGNER());
			docExchangeBox.setCebid(docSummary.getCEBID());
			docExchangeBox.setXtoName(docSummary.getTODEPNAMES());
			docExchangeBox.setXccName(docSummary.getCCDEPNAMES());
			docExchangeBox.setWh(wh);
			baseDocList.add(docExchangeBox);
		}
		return baseDocList;
	}
	
	
	/**
	 * 根据list生成对应的xml
	 * 描述：TODO 对此方法进行描述
	 * @param list
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-6-15 下午4:23:42
	 */
	synchronized public final static String genXmlForSendDocs(List<LowDoc> list){
		String xml = "<DOCSUMMARYS>";
        {	
        	for(LowDoc lowDoc:list){
        		xml += "<DOCSUMMARY>"+"<!--此处可循环-->"
        			   +"<ID>"+lowDoc.getDocguid()+"</ID>"+"<!--ID-->"
        			   +"<INSTANCEID>"+lowDoc.getInstanceId()+"</INSTANCEID>"+"<!--实例ID-->"
        			   +"<WENHAO>"+lowDoc.getWh()+"</WENHAO>"+"<!--文号-->"
        			   +"<TITLE>"+lowDoc.getTitle()+"</TITLE>"+"<!--标题-->"
        			   +"<TYPE>"+lowDoc.getDoctype()+"</TYPE>"+"<!--类型-->"
        			   +"<TODEPIDS>"+lowDoc.getXto()+"</TODEPIDS>"+"<!--主送单位ID-->"
        			   +"<TODEPNAMES>"+lowDoc.getXtoName()+"</TODEPNAMES>"+"<!--主送单位名称-->"
        			   +"<CCDEPIDS>"+lowDoc.getXcc()+"</CCDEPIDS>"+"<!--抄送单位ID-->"
        			   +"<CCDEPNAMES>"+lowDoc.getXccName()+"</CCDEPNAMES>"+"<!--抄送单位名称-->"
					   +"<SENDERID>"+lowDoc.getSenderId()+"</SENDERID>"+"<!--发送人ID-->"
        			   +"<SENDERNAME>"+lowDoc.getSenderName()+"</SENDERNAME>"+"<!--发送人名称-->"
        			   +"<SENDERTIME>"+lowDoc.getSenderTime()+"</SENDERTIME>"+"<!--发送时间-->"
        			   +"<SENDDEPNAME>"+lowDoc.getFwjg()+"</SENDDEPNAME>"+"<!--发送单位-->"
        			   +"<DYFS>1,1,1</DYFS>"+"<!--打印份数-->";
//			    	   +"<DYFS>"+lowDoc.getDyfs()+"</DYFS>"+"<!--打印份数-->";
        		xml += "<ATTS>";
        		xml += "<ATT>"+"<!--此处可循环-->"
        				+"<NAME>"+lowDoc.getTitle()+"</NAME>"+"<!--供下载的文件名称-->"
        				+"<ATTTYPE>0</ATTTYPE>"+"<!-- 附件类型0：表单；1：正文；2：附件 -->"
        				+"<TYPE>pdf</TYPE>"+"<!--供下载的文件类型-->"
        				+"<ATTTIME></ATTTIME>"+"<!-- 附件生成的时间 -->"
        				+"<URL>"+lowDoc.getPath_pdf()+"</URL>"+"<!--供下载的文件地址-->"
        				+"<FILESIZE>"+BigDecimal.valueOf(lowDoc.getPdfsize())+"</FILESIZE>"+"<!--供下载的文件大小-->"
        				+"</ATT>";
        		xml += "</ATTS>";
    			xml += "</DOCSUMMARY>";
        	}
        	xml += "</DOCSUMMARYS>";
        }
        xml = xml.replace("&", "&amp;");
		return xml;
	}
	
	
	public static void main(String[] args) {
		DocXgXmlUtil.downloadFromUrl("http://192.168.5.103:8186/doc/down.do?fileNameForDown=aa.doc&localtion=upload/2014/05/16/f2f6c698-9b1d-4605-bd01-6a547091ecc7.ceb", "aa.doc", "zw");
		
		//DocXgXmlUtil.downloadFromUrl("http://192.168.0.158:8080/oa/docDataProcess_download.do?localtion=OA_DOC_ATTACACHMENT/2010/08/20/FB6C7EBE-3F70-41C2-8819-389D07F1C290.doc", "aaaa.doc", "zw");

	}
}
