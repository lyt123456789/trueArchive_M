package cn.com.trueway.base.util;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import cn.com.trueway.workflow.webService.pojo.ProBaseDoc;
import cn.com.trueway.workflow.webService.pojo.ReceiveSendAtt;

public class ProXmlUtil {
	@SuppressWarnings("unchecked")
	public static List<ProBaseDoc> analysisReceiveXml(String xml){
		List<ProBaseDoc> list = new ArrayList<ProBaseDoc>();
		if(org.apache.commons.lang.StringUtils.isNotBlank(xml)){
			try {
				StringReader sr=new StringReader(xml);
				InputSource is=new InputSource(sr);
				SAXBuilder builder=new SAXBuilder();
				Document document;
				document = builder.build(is);
				Element root = document.getRootElement();
				List<Element> children = root.getChildren();
				for (int i = 0; i < children.size(); i++) {
					Element element=children.get(i);
					String name=element.getName();
					ProBaseDoc proBaseDoc = new ProBaseDoc();
					if("DOCSUMMARY".equals(name)){
						List<Element> children_doc = element.getChildren();
						for (int j = 0; j < children_doc.size(); j++) {
							Element element_doc=children_doc.get(j);
							String name_doc=element_doc.getName();
							if("ID".equals(name_doc)){
								proBaseDoc.setId(element_doc.getText());
							}else if("INSTANCEID".equals(name_doc)){
								proBaseDoc.setInstanceId(element_doc.getText());
							}else if("WENHAO".equals(name_doc)){
								proBaseDoc.setWh(element_doc.getText());
							}else if("TITLE".equals(name_doc)){
								proBaseDoc.setTitle(element_doc.getText());
							}else if("TYPE".equals(name_doc)){
								proBaseDoc.setType(element_doc.getText());
							}else if("TODEPIDS".equals(name_doc)){
								proBaseDoc.setTodepIds(element_doc.getText());
							}else if("TODEPNAMES".equals(name_doc)){
								proBaseDoc.setTodepNames(element_doc.getText());
							}else if("CCDEPIDS".equals(name_doc)){
								proBaseDoc.setCcdepIds(element_doc.getText());
							}else if("CCDEPNAMES".equals(name_doc)){
								proBaseDoc.setCcdepNames(element_doc.getText());
							}else if("SENDERID".equals(name_doc)){
								proBaseDoc.setSenderId(element_doc.getText());
							}else if("SENDERNAME".equals(name_doc)){
								proBaseDoc.setSenderName(element_doc.getText());
							}else if("SENDERTIME".equals(name_doc)){
								proBaseDoc.setSenderTime(strTodate(element_doc.getText()));
							}else if("SENDDEPNAME".equals(name_doc)){
								proBaseDoc.setSendDepName(element_doc.getText());
							}else if("DYFS".equals(name_doc)){
								proBaseDoc.setDyfs(element_doc.getText());
							}else if("ATTS".equals(name_doc)){
								List<ReceiveSendAtt> list_att = new ArrayList<ReceiveSendAtt>();
								List<Element> children_atts = element_doc.getChildren();
								for (int k = 0; k < children_atts.size(); k++) {
									ReceiveSendAtt rsAtt = new ReceiveSendAtt();
									Element element_atts=children_atts.get(k);
									String name_atts=element_atts.getName();
									if("ATT".equals(name_atts)){
										List<Element> children_att = element_atts.getChildren();
										for (int l = 0; l < children_att.size(); l++) {
											Element element_att=children_att.get(l);
											String name_att=element_att.getName();
											if("NAME".equals(name_att)){
												rsAtt.setName(element_att.getText());
											}else if("ATTTYPE".equals(name_att)){
												rsAtt.setAttType(element_att.getText());
											}else if("TYPE".equals(name_att)){
												rsAtt.setType(element_att.getText());
											}else if("ATTTIME".equals(name_att)){
												rsAtt.setAttTime(strTodate(element_att.getText()));
											}else if("URL".equals(name_att)){
												rsAtt.setUrl(element_att.getText());
											}else if("FILESIZE".equals(name_att)){
												rsAtt.setFilesize(Long.valueOf(element_att.getText()));
											}
											
										}
									}
									list_att.add(rsAtt);
								}
								proBaseDoc.setAtt(list_att);
							}
							
						}
					}
					list.add(proBaseDoc);
				}
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				//解析标题字段
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * 精确到时分秒
	 * 描述：TODO 对此方法进行描述
	 * @param str
	 * @return Date
	 * 作者:季振华
	 * 创建时间:2016-12-7 下午4:07:22
	 */
	public static Date strTodate(String str){
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = null;
		 try {
			 if(CommonUtil.stringNotNULL(str)){
				 date =  format.parse(str);
			 }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 精确到日
	 * 描述：TODO 对此方法进行描述
	 * @param str
	 * @return Date
	 * 作者:季振华
	 * 创建时间:2016-12-7 下午4:07:44
	 */
	public static Date strTodate_day(String str){
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = null;
		 try {
			date =  format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static void main(String[] args){
		String ret="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<DOCSUMMARYS><DOCSUMMARY>" +
				"<ID>DC64E854-8795-408A-BAAC-E10DDC9C565D</ID><!--ID -->" +
				"<WENHAO></WENHAO><!--文号 -->" +
				"<TITLE>test20161117001</TITLE><!--标题 -->" +
				"<TYPE></TYPE><!--类型 -->" +
				"<TODEPIDS></TODEPIDS><!--主送单位ID -->" +
				"<TODEPNAMES></TODEPNAMES><!--主送单位名称 -->" +
				"<CCDEPIDS></CCDEPIDS><!--抄送单位ID -->" +
				"<CCDEPNAMES></CCDEPNAMES><!--抄送单位名称 -->" +
				"<SENDERID></SENDERID><!--发送人ID -->" +
				"<SENDERNAME>窦峰</SENDERNAME><!--发送人名称 -->" +
				"<SENDERTIME>2016-11-17 09:50:33</SENDERTIME><!--发送时间 -->" +
				"<SENDDEPNAME>区政府（区政府办、外侨办）</SENDDEPNAME><!--发送单位 -->" +
				"<DYFS>3</DYFS><!--打印份数 -->" +
				"<ATTS>" +
				"<ATT><!--此处可循环 -->" +
				"<NAME>test20161117001</NAME><!--供下载的文件名称 -->" +
				"<ATTTYPE>0</ATTTYPE><!-- 附件类型0：表单；1：正文；2：附件 -->" +
				"<TYPE>pdf</TYPE><!--供下载的文件类型 -->" +
				"<ATTTIME></ATTTIME><!-- 附件生成的时间 -->" +
				"<URL>http://192.168.5.188:19080/trueWorkFlowV2.1_CCQ_GZ/attachment_download.do?isabsolute=1&amp;name=test20161117001.pdf&amp;location=D:/PROJECT/process/pdf/93b81034-9e87-4042-b11b-9ceee85abe29.pdf" +
				"</URL><!--供下载的文件地址 -->" +
				"<FILESIZE>34911</FILESIZE><!--供下载的文件大小 -->" +
				"</ATT>" +
				"</ATTS>" +
				"</DOCSUMMARY></DOCSUMMARYS>";
		List<ProBaseDoc> list = new ArrayList<ProBaseDoc>();
		list=ProXmlUtil.analysisReceiveXml(ret);
		System.out.println(list.get(0).getId());
	}
}
