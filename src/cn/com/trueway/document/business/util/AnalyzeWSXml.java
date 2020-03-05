/**
 * 文件名称:AnalyzeWSXml.java
 * 作者:Lv☂<br>
 * 创建时间:2013-9-23 下午04:45:53
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.business.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.XmlOptions;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sun.misc.BASE64Encoder;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.document.business.docxg.client.vo.BaseDoc;
import cn.com.trueway.document.business.docxg.client.vo.DocExchangeVo;
import cn.com.trueway.document.business.docxg.client.vo.Queue;
import cn.com.trueway.document.business.model.Doc;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.sys.util.SystemParamConfigUtil;

/**
 * 描述：TODO 对AnalyzeWSXml进行描述<br>
 * 作者：zhuxc<br>
 * 创建时间：2013-9-23 下午04:45:53<br>
 */
public class AnalyzeWSXml {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 待收公文列表
	 * 作者:zhuxc<br>
	 * 创建时间:2013-9-23 下午04:59:33
	 */
	public static LinkedHashMap<String, DocExchangeVo> getToBeReceivedList(String xml){
		LinkedHashMap<String, DocExchangeVo> tobeRecevicedoc=new LinkedHashMap<String, DocExchangeVo>();
		try {
			Document doc = DocumentHelper.parseText(xml);  
			Element root = doc.getRootElement();
			List<Element> docList= root.element("DATA").element("UserArea").element("PaperList").elements("Paper");
			int index = 0;
			for(Element el : docList){
				if(index>=10){
					return tobeRecevicedoc;
				}
				String guid = el.elementText("InboxGuid");				//公文GUID
				String docNum = el.elementText("ArchiveID_Send");		//文号
				String title = el.elementText("Title");					//标题
				String sendTime = el.elementText("SendTime");			//发文时间
				String sendDeptName = el.elementText("SendDeptName");	//发文部门名称
				String deptName = el.elementText("DeptName");			//收文部门名称
//				String mainGive = el.elementText("MainGive");			//主送机关
//				String subGive = el.elementText("SubGive");				//抄送机关
//				String mainDo = el.elementText("MainDo");				//主办机关
//				String subDo = el.elementText("SubDo");					//协办机关
				
				
				BaseDoc docExchangeBox = new BaseDoc();
				Queue queue = new Queue();
				
				docExchangeBox.setDocguid(guid);
				docExchangeBox.setSender("");
//				docExchangeBox.setXto(mainGive);
//				docExchangeBox.setXcc(subGive);
				docExchangeBox.setTitle(title);
				docExchangeBox.setKeyword("");
				docExchangeBox.setDoctype("");	//公文类型
				docExchangeBox.setSubmittm(sdf.parse(sendTime));
				docExchangeBox.setPriority(0L);
				docExchangeBox.setJgdz(docNum);//(原)文号标识,(城管)文号
				docExchangeBox.setFwxh("");
				docExchangeBox.setFwjg(sendDeptName);
				docExchangeBox.setSendstat(1L);
				docExchangeBox.setFwnh("");		
				docExchangeBox.setYfdw("");
				docExchangeBox.setYffs(1L);
				docExchangeBox.setYfrq("");
				docExchangeBox.setQfr("");
				docExchangeBox.setCebid("");
				docExchangeBox.setXtoName("");
				docExchangeBox.setXccName("");
				queue.setId(guid);
				DocExchangeVo docExchangeVo = new DocExchangeVo(docExchangeBox,queue,null,null);
				tobeRecevicedoc.put(guid, docExchangeVo);
				index++;
				
			}	
			
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("公文发送日期日期格式不正确！");
			e.printStackTrace();
		}
		return tobeRecevicedoc;
	}
	
	public static DocExchangeVo toBeReceivedDoc(String xml,String docId){
		BaseDoc doc = new BaseDoc();
		List<ReceiveAttachments> atts = new ArrayList<ReceiveAttachments>();
		List<ReceiveAttachmentsext> attsext = new ArrayList<ReceiveAttachmentsext>();
		try {
			/*
			 * 解析xml
			 */
			Document document = DocumentHelper.parseText(xml);  
			Element root = document.getRootElement();
			Element docRoot = root.element("DATA").element("UserArea").element("UserArea").element("电子公文");
			Element body= root.element("DATA").element("UserArea").element("UserArea").element("电子公文").element("公文体");
			Element main = body.element("主体");
			Element bj = body.element("版记");
			//
			String docguid = docRoot.attributeValue("公文标识");
			if(CommonUtil.stringIsNULL(docguid)){
				docguid = docId;
			}
			String sender = "";			//发送人id???????
			String xto = "";//main.elementText("主送机关");
			//
			String xcc = "";
			String title = main.elementText("标题");
			//主题词
			String keyword = "";
			for(Object obj:bj.element("主题词").elements("题目")){
				Element el = (Element) obj;
				keyword += el.getText();
			}
			
			String doctype = docRoot.attributeValue("公文类别")+docRoot.attributeValue("公文种类");
			String submittm = main.elementText("成文日期");
			Date submitTime = null;
			if(CommonUtil.stringNotNULL(submittm)){
				submitTime = sdf.parse(submittm);
			}
			
			//紧急程度
			String jjcd = body.element("眉首").elementText("紧急程度");
			Long priority = "普通".equals(jjcd)?0L:"较急".equals(jjcd)?1L:"紧急".equals(jjcd)?3L:-1L;
			
			Long sendstat = 0L;			//发送状态??????
			
			String jgdz = body.element("眉首").element("发文字号").elementText("发文机关代字");
			String fwnh = body.element("眉首").element("发文字号").elementText("发文年号");
			String fwxh = body.element("眉首").element("发文字号").elementText("发文序号");
			
			//发文机关
//			List<Element> fwjgs= docRoot.elements("发文机关");
			String fwjg = main.elementText("发文部门名称");
//			for(Element el : fwjgs){
//				if("主办".equals(el.attributeValue("办理类别"))){
//					fwjg += el.attributeValue("组织机构代码");
//				}
//			}
			
			//签发人
			String  qfr = "";
			for(Object obj:main.elements("签发人署名")){
				Element el = (Element) obj;
				qfr += el.getText();
			}
			
			String yfdw = bj.element("印制版记").elementText("印发机关");
			String yfrq = bj.element("印制版记").elementText("印发日期");
			String yffs = bj.element("印制版记").elementText("印发份数");
			
			String cebid = "";  		//cebid??????
			String bodyxml = "";		//公文体??????
			String xtoName = xto;		//???????
			String xccName = xcc;		//???????
			
			doc.setDocguid(docguid);	//唯一标识
			doc.setSender(sender);		//发送者编号
			doc.setXto(xto);			//主送单位
			doc.setXcc(xcc);			//抄送单位
			doc.setTitle(title);		//公文标题
			doc.setKeyword(keyword);	//公文主题词
			doc.setDoctype(doctype);	//公文类型
			doc.setSubmittm(submitTime);	//发送时间
			doc.setPriority(priority);	//紧急程度
			doc.setSendstat(sendstat);	//发送状态
			doc.setJgdz(jgdz);			//文号(标识)
			doc.setFwnh(fwnh);			//文号(年号)
			doc.setFwjg(fwjg);			//发文单位
			doc.setFwxh(fwxh);			//发文序号
			doc.setQfr(qfr);			//签发人
			doc.setYfdw(yfdw);			//印发单位
			doc.setYfrq(yfrq);			//印发日期
			doc.setYffs(Long.parseLong(yffs));			//印发分数
			doc.setCebid(cebid);		//版式文件id
			doc.setBodyxml(bodyxml);	//公文xml
			doc.setXtoName(xtoName);
			doc.setXccName(xccName);
			
			/*
			 * 正文、附件 
			 */
//			Element zw = main.element("正文").element("版式文件");
//			ReceiveAttachments att = new ReceiveAttachments();
//			String attName = zw.attributeValue("文件名");
//			att.setData(zw.getText());
//			att.setDocguid(docguid);
//			att.setFilename(attName);
//			att.setFiletime(new Date());
//			att.setFiletype(attName.substring(attName.lastIndexOf(".")+1));
//			att.setFilesize((long)zw.getText().length());
//			atts.add(att);
//			
//			List<Element> fjs = main.element("附件").elements("版式文件");
//			for(Element el : fjs){
//				ReceiveAttachmentsext attext = new ReceiveAttachmentsext();
//				String attextName = el.attributeValue("文件名");
//				attext.setData(el.getText());
//				attext.setDocguid(docguid);
//				attext.setFilename(attextName);
//				attext.setFiletime(new Date());
//				attext.setFiletype(attextName.substring(attextName.lastIndexOf(".")+1));
//				attext.setFilesize((long)el.getText().length());
//				attsext.add(attext);
//			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		DocExchangeVo docVo = new DocExchangeVo(doc,null,atts,attsext);
		return docVo;
	}
	
	/*
	 * 解析发文doc成xml
	 */
	public static String genXmlForSendDoc(Doc doc) {
		StringBuffer xml = new StringBuffer();
		try {
			xml.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
			xml.append("<paras>");
			String xto = doc.getXto();
			String xtoName = doc.getXtoName();
			String xcc_ = doc.getXcc();
			String xccName = doc.getXccName();
			if(CommonUtil.stringNotNULL(xto) && xto.lastIndexOf(";")==xto.length()-1){
				xto = xto.substring(0,xto.length()-1);
			}
			if(CommonUtil.stringNotNULL(xtoName) && xtoName.lastIndexOf(";")==xtoName.length()-1){
				xtoName = xtoName.substring(0,xtoName.length()-1);
			}
			if(CommonUtil.stringNotNULL(xcc_) && xcc_.lastIndexOf(";")==xcc_.length()-1){
				xcc_ = xcc_.substring(0,xcc_.length()-1);
			}
			if(CommonUtil.stringNotNULL(xccName) && xccName.lastIndexOf(";")==xccName.length()-1){
				xccName = xccName.substring(0,xccName.length()-1);
			}
			xml.append("<DeptGuid>"+xto+"</DeptGuid>");
			xml.append("<DeptName>"+xtoName+"</DeptName>");

			xml.append( "<电子公文 公文标识=\"\" 版本号=\"1.0\" 公文类别=\"行政\" 公文种类=\""+CommonUtil.ToString(doc.getDoctype())+"\">");
				
			xml.append("<发文机关 组织机构代码=\"\" 办理类别=\"主办\">"+CommonUtil.ToString(doc.getFwjg())+"</发文机关>");
			xml.append("<公文体>");
			xml.append("	<眉首>");
			xml.append("<秘密等级></秘密等级>");//普通/秘密/机密/绝密
			xml.append("<保密期限></保密期限>");
			xml.append("<紧急程度></紧急程度>");//普通/较急/紧急
			xml.append("<发文机关标识>");
			xml.append("<发文机关名称>"+CommonUtil.ToString(doc.getFwjg())+"</发文机关名称>");
			xml.append("<标识后缀>文件</标识后缀>");
			xml.append("</发文机关标识>");
			xml.append("<发文字号>");
			xml.append("<发文机关代字>"+CommonUtil.ToString(doc.getJgdz())+"</发文机关代字>");
			xml.append("<发文年号>"+CommonUtil.ToString(doc.getFwnh())+"</发文年号>");
			xml.append("<发文序号>"+CommonUtil.ToString(doc.getFwxh())+"</发文序号>");
			xml.append("</发文字号>");
			xml.append("</眉首>");
			xml.append("	<主体>");
			xml.append("		<标题>"+CommonUtil.ToString(doc.getTitle())+"</标题>");// * 公文标题
			xml.append("		<是否回复>0</是否回复>");
			xml.append("		<关联收文></关联收文>");
			xml.append("		<发文号>"+CommonUtil.ToString(doc.getJgdz())+"["+CommonUtil.ToString(doc.getFwnh())+"]"+CommonUtil.ToString(doc.getFwxh())+"</发文号>");//苏府办[2002]9999号
			xml.append("		<主送机关>"+CommonUtil.ToString(doc.getXtoName())+"</主送机关>");
			//循环正文
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+"/";
			xml.append("<正文>");
			 for(int i = 0;i<doc.getAtts().size();i++){
				 SendAttachments att = doc.getAtts().get(i);
				 if((doc.getInstanceId()+SystemParamConfigUtil.getParamValueByParam("attSuffixName")).equals(att.getDocguid())){
					 xml.append("<版式文件 文件名=\""+doc.getAtts().get(i).getTitle()+"\" 数据类型=\""+doc.getAtts().get(i).getType()+"\" 编码方式=\"BASE64\">" );
						String fileName = basePath+doc.getAtts().get(i).getLocalation();
						String filecontent =  new String( readFile(fileName),"ISO-8859-1");
						xml.append((new BASE64Encoder()).encode(filecontent.getBytes("ISO-8859-1")));		
						xml.append("</版式文件>");
				 }
			 }	
			xml.append("</正文>");
			//循环附件
			xml.append("<附件>");
			 for(int i = 0;i<doc.getAtts().size();i++){
				 SendAttachments att = doc.getAtts().get(i);
				 if((doc.getInstanceId()+SystemParamConfigUtil.getParamValueByParam("attFjSuffixName")).equals(att.getDocguid())){
				     xml.append("	<版式文件 文件名=\""+doc.getAtts().get(i).getTitle()+"\" 数据类型=\""+doc.getAtts().get(i).getType()+"\" 编码方式=\"BASE64\">");
				     String fileName = basePath+doc.getAtts().get(i).getLocalation();
				 	 String filecontent =  new String( readFile(fileName),"ISO-8859-1");
				 	 xml.append((new BASE64Encoder()).encode(filecontent.getBytes("ISO-8859-1")));		
				     xml.append(" </版式文件>");
				 }
			 }
			xml.append("</附件>");
			xml.append("<公文生效标识>");
			xml.append("	<发文机关署名>"+CommonUtil.ToString(doc.getFwjg())+"</发文机关署名>");
			xml.append("	<签发人署名>"+CommonUtil.ToString(doc.getQfr())+"</签发人署名>");
			xml.append("</公文生效标识>");
			xml.append("<成文日期>"+doc.getSubmittm()+"</成文日期>");
			xml.append("<附注>无</附注>");
			xml.append("</主体>");
			xml.append("<版记>");
			xml.append("	<主题词>");
			xml.append("		<词目>空</词目>");
			xml.append("	</主题词>");
			
			if(CommonUtil.stringNotNULL(xccName)){
				String[] xccs = xccName.split(";");
				for(String xcc : xccs){
					if(CommonUtil.stringNotNULL(xcc)){
						xml.append("<抄送机关 抄送类型=\"抄报\">"+xcc+"</抄送机关>");
					}
				}
			}else{
				xml.append("<抄送机关 抄送类型=\"抄报\"></抄送机关>");
			}
			
			xml.append("<印制版记>");
			xml.append("	<印发机关>"+CommonUtil.ToString(doc.getYfdw())+"</印发机关>");//印发单位
			xml.append("	<印发日期>"+CommonUtil.ToString(doc.getYfrq())+"</印发日期>");
			xml.append("	<印发份数>"+CommonUtil.ToString(doc.getYffs())+"</印发份数>");
			xml.append("</印制版记>");
			xml.append("</版记>");
			xml.append("</公文体>");
			xml.append("</电子公文>");
			xml.append("</paras>");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xml.toString();
	}
	
	public static byte[] readFile(String filename) throws IOException {

	    File file =new File(filename);
	    if(filename==null || filename.equals(""))
	    {
	      throw new NullPointerException("无效的文件路径");
	    }
	    long len = file.length();
	    byte[] bytes = new byte[(int)len];

	    BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
	    int r = bufferedInputStream.read( bytes );
	    if (r != len)
	      throw new IOException("读取文件不正确");
	    bufferedInputStream.close();

	    return bytes;
	}
	
	public static String getSendToGwjhptXml(Doc oldDoc, Map<String,String> mapbak){
		StringBuffer xml = new StringBuffer();
		String title = oldDoc.getTitle();
		String downPath= SystemParamConfigUtil.getParamValueByParam("filedownloadUrl");
		String keyword = oldDoc.getKeyword()==null?oldDoc.getTitle():oldDoc.getKeyword();
		String xto = oldDoc.getXto();
		String cto = oldDoc.getXcc()==null?"空":oldDoc.getXcc();
		String xtoName = oldDoc.getXtoName()==null?"空":oldDoc.getXtoName();
		String ctoName = oldDoc.getXccName()==null?"空":oldDoc.getXccName();
		String qfr = oldDoc.getQfr();
		if(qfr==null || qfr.equals("")){
			qfr = mapbak.get("userId");
		}
		String senderId = oldDoc.getSender();
		if(senderId==null || senderId.equals("")){
			senderId = mapbak.get("userId");
		}
		String senderName = mapbak.get("userName");
		String yfdw = oldDoc.getYfdw()==null?"空":oldDoc.getYfdw();
		
		Long jgcd = oldDoc.getPriority();
		String pro = DocXgConst.getPriorityNameByCode(jgcd);
		
		String yffs = oldDoc.getYffs()==null?"0":oldDoc.getYffs()+"";
		
		String jgdz = oldDoc.getJgdz()==null?"空":oldDoc.getJgdz();
		String fwnh = oldDoc.getFwnh()==null?"空":oldDoc.getFwnh();
		String fwxh = oldDoc.getFwxh()== null?"空":oldDoc.getFwxh();
		List<SendAttachments> sattList =  oldDoc.getAtts();
		try {
			xml.append("<?xml version=\"1.0\" encoding=\"gb2313\"?>");
			xml.append("<DOCSUMMARYS>");
			xml.append("<DOCSUMMARY>");
			xml.append("<WENHAO JGDZ=\""+jgdz+"\" WFNH=\""+fwnh+"\" FWXH=\""+fwxh+"\"/>");
			xml.append("<CEBID/>");
			xml.append("<TITLE><![CDATA["+title+"]]></TITLE>");
			xml.append("<KEYWORD><![CDATA["+keyword+"]]></KEYWORD>");
			xml.append("<TYPE>办文</TYPE>");
			xml.append("<PRIORITY>"+pro+"</PRIORITY>");
			xml.append("<TODEPIDS>"+xto+"</TODEPIDS>");
			xml.append("<TODEPNAMES>"+xtoName+"</TODEPNAMES>");
			xml.append("<CCDEPIDS>"+cto+"</CCDEPIDS>");
			xml.append("<CCDEPNAMES>"+ctoName+"</CCDEPNAMES>");
			xml.append("<SENDERID>"+senderId+"</SENDERID>");
			xml.append("<SENDERNAME>"+senderName+"</SENDERNAME>");
			xml.append("<SIGNDEPNAME>"+yfdw+"</SIGNDEPNAME>");
			xml.append("<SIGNER>"+senderName+"</SIGNER>");
			xml.append("<YFDATE></YFDATE>");
			xml.append("<YFSUM>"+yffs+"</YFSUM>");
			xml.append("<ATTS>");
			if(sattList!=null && sattList.size()>0){
				for(SendAttachments att:sattList){
					String loaction = att.getLocalation();
					 xml.append("<ATT>");
						xml.append("<NAME>"+att.getFilename()+"</NAME>");
						xml.append("<TYPE>"+att.getFiletype()+"</TYPE>");
						xml.append("<URL>"+downPath+"?location="+loaction+"</URL>");
						xml.append("<FILESIZE>"+att.getFilesize()+"</FILESIZE>");
					  xml.append("</ATT>");
				}
			}
			 /* xml.append("<ATT>");
				xml.append("<NAME></NAME>");
				xml.append("<TYPE></TYPE>");
				xml.append("<URL></URL>");
				xml.append("<FILESIZE></FILESIZE>");
			  xml.append("</ATT>");*/
			xml.append("</ATTS>");
			xml.append("<ATTEXTS>");
			 /* xml.append("<ATTEXT>");
				xml.append("<NAME></NAME>");
				xml.append("<TYPE></TYPE>");
				xml.append("<URL></URL>");
				xml.append("<IND></IND>");
				xml.append("<FILESIZE></FILESIZE>");
			  xml.append("</ATTEXT>");*/
			xml.append("</ATTEXTS>");
			xml.append("</DOCSUMMARY>");
			xml.append("</DOCSUMMARYS>");
		}catch (Exception e) {
		}
		return xml.toString();
	}
}
