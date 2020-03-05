package cn.com.trueway.workflow.webService.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import sun.misc.BASE64Encoder;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.PendingDao;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.webService.dao.ArchiveWebServiceDao;
import cn.com.trueway.workflow.webService.service.ArchiveWebService;

public class ArchiveWebServiceImpl implements ArchiveWebService{
	
	private ArchiveWebServiceDao archiveWebServiceDao;
	
	private AttachmentDao attachmentDao;
	
	public ArchiveWebServiceDao getArchiveWebServiceDao() {
		return archiveWebServiceDao;
	}

	public void setArchiveWebServiceDao(ArchiveWebServiceDao archiveWebServiceDao) {
		this.archiveWebServiceDao = archiveWebServiceDao;
	}
	
	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	@Override
	public String loginArchiveSystem() {
		String sessionId = callWebService("logon", null);
		return sessionId;
	}

	@Override
	public String logoutArchiveSystem(String sessionId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionid", sessionId);
		String result = callWebService("logout", map);
		return result;
	}

	@Override
	public String addSendDocument(String instanceId) {
		String sessionId = "";
		String result = "";
		try{
			sessionId = loginArchiveSystem();//登录档案系统
			if(CommonUtil.stringNotNULL(sessionId)){
				List<Object[]> fwList = archiveWebServiceDao.getArchivedFwList(instanceId);
				Map<String, String> map = new HashMap<String, String>();
				String xml = formatSendDocXml(fwList, instanceId);//获取xml
				map.put("sessionid", sessionId);
				map.put("xml", xml);
				result = callWebService("sendDoc", map);
			}else{
				result="failedLogin";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(CommonUtil.stringNotNULL(sessionId)){
				logoutArchiveSystem(sessionId);//退出档案系统
			}
		}
		return result;
	}

	@Override
	public String addAcceptDocument(String instanceId) {
		String sessionId = "";
		String result = "";
		try{
			sessionId = loginArchiveSystem();//登录档案系统
			if(CommonUtil.stringNotNULL(sessionId)){
				List<Object[]> swList = archiveWebServiceDao.getArchivedSwList(instanceId);
				Map<String, String> map = new HashMap<String, String>();
				String xml = formatAcceptDocXml(swList, instanceId);//收文xml
				map.put("sessionid", sessionId);
				map.put("xml", xml);
				result = callWebService("acceptDoc", map);
			}else{
				result="failedLogin";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(CommonUtil.stringNotNULL(sessionId)){
				logoutArchiveSystem(sessionId);//退出档案系统
			}
		}
		return result;
	}
	
	/**
	 * 调用web service接口
	 * @param type
	 * @param map
	 * @return
	 */
	private String callWebService(String type, Map<String, String> map){
		//归档系统url
		String archiveSysUrl = SystemParamConfigUtil.getParamValueByParam("archiveSysUrl");;
		
		String result = "";
		if("logon".equals(type) || "logout".equals(type)){
			archiveSysUrl = archiveSysUrl + "/services/LogonService";
		}else if("sendDoc".equals(type) || "acceptDoc".equals(type)){
			archiveSysUrl = archiveSysUrl + "/services/DocumentService";
		}
		//archiveSysUrl += "?wsdl";
		Service service = new Service();
		Call call;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(archiveSysUrl);
			if("logon".equals(type)){
				System.out.println("Archive web service login start---------------1");
				//登录档案系统
				call.setOperationName("logon");//WSDL里面登录的接口名称
				call.addParameter("user", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口参数登录名
				call.addParameter("password", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口参数登录名
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
				String user = SystemParamConfigUtil.getParamValueByParam("archiveSysUser");
				String password = SystemParamConfigUtil.getParamValueByParam("archiveSysPwd");
				result = (String)call.invoke(new Object[]{user,password});
				System.out.println("Archive web service login end---------------2-------success-------sessionId="+result);
			}else if("logout".equals(type)){
				System.out.println("Archive web service logout start---------------3");
				//退出档案系统
				call.setOperationName("logoff");//WSDL里面退出的接口名称
				call.addParameter("sessionid", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//sessionid
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
				String sessionid = map.get("sessionid");
				result = (String)call.invoke(new Object[]{sessionid});
				System.out.println("Archive web service logout end-----------new----4");
			}else if("sendDoc".equals(type)){
				System.out.println("Archive web service sendDoc start---------------5");
				//传送发文
				call.setOperationName("addSendDocument_ext1");//WSDL里面传递发文的接口名称
				call.addParameter("sessionid", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//sessionid
				call.addParameter("xml", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//发文xml字符串
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
				String sessionid = map.get("sessionid");
				String xml = map.get("xml");
				result = (String)call.invoke(new Object[]{sessionid, xml});
				System.out.println("Archive web service sendDoc end---------------6");
			}else if("acceptDoc".equals(type)){
				System.out.println("Archive web service acceptDoc start---------------7");
				//传送收文
				call.setOperationName("addAcceptDocument_ext1");//WSDL里面传递办文的接口名称
				call.addParameter("sessionid", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//sessionid
				call.addParameter("xml", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//发文xml字符串
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
				String sessionid = map.get("sessionid");
				String xml = map.get("xml");
				result = (String)call.invoke(new Object[]{sessionid, xml});
				System.out.println("Archive web service acceptDoc end---------------8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("result:"+result);
		return result;
	}
	
	/**
	 * 格式化发文xml
	 * @param list
	 * @return
	 */
	private String formatSendDocXml(List<Object[]> list, String instanceId){
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		String fwContent = "<document type=\"send\">";
		if(list != null && list.size() > 0){
			Object[] objs = list.get(0);
			fwContent += "<fwdjh>" + (objs[1] == null ? "" : objs[1].toString()) + "</fwdjh>";//发文登记号
			fwContent += "<zbbm>" + (objs[2] == null ? "" : objs[2].toString()) + "</zbbm>";//主办部门
			fwContent += "<xbbm>" + (objs[3] == null ? "" : objs[3].toString()) + "</xbbm>";//协办部门
			fwContent += "<qcr>" + (objs[4] == null ? "" : objs[4].toString()) + "</qcr>";//起草人
			fwContent += "<xgr>" + (objs[5] == null ? "" : objs[5].toString()) + "</xgr>";//修改人
			fwContent += "<shyj>" + (objs[6] == null ? "" : objs[6].toString()) + "</shyj>";//审核意见
			fwContent += "<qfyj>" + (objs[7] == null ? "" : objs[7].toString()) + "</qfyj>";//签发意见
			fwContent += "<dzr>" + (objs[8] == null ? "" : objs[8].toString()) + "</dzr>";//打字人
			fwContent += "<jjcd>" + (objs[9] == null ? "" : objs[9].toString()) + "</jjcd>";//紧急程度
			fwContent += "<mj>" + (objs[10] == null ? "" : objs[10].toString()) + "</mj>";//密级
			fwContent += "<zrz>" + (objs[11] == null ? "" : objs[11].toString()) + "</zrz>";//责任者
			fwContent += "<wh>" + (objs[12] == null ? "" : objs[12].toString()) + "</wh>";//文号
			fwContent += "<tm>" + (objs[13] == null ? "" : objs[13].toString()) + "</tm>";//文件标题
			fwContent += "<cwrq>" + (objs[14] == null ? "" : objs[14].toString()) + "</cwrq>";//成文日期
			fwContent += "<fs>" + (objs[15] == null ? "" : objs[15].toString()) + "</fs>";//份数
			fwContent += "<ys>" + (objs[16] == null ? "" : objs[16].toString()) + "</ys>";//页数
			fwContent += "<ztc>" + (objs[17] == null ? "" : objs[17].toString()) + "</ztc>";//主题词
			fwContent += "<wz>" + (objs[18] == null ? "" : objs[18].toString()) + "</wz>";//文种
			fwContent += "<gb>" + (objs[19] == null ? "" : objs[19].toString()) + "</gb>";//电子文件稿本
			fwContent += "<lb>" + (objs[20] == null ? "" : objs[20].toString()) + "</lb>";//电子文件类别
			fwContent += "<qwbs>" + (objs[21] == null ? "" : objs[21].toString()) + "</qwbs>";//全文标识
			fwContent += "<djrq>" + (objs[22] == null ? "" : objs[22].toString()) + "</djrq>";//登记日期
			String fwdw = objs[23] == null ? "" : objs[23].toString();
			if(CommonUtil.stringNotNULL(fwdw) && fwdw.contains("*")){
				fwdw = fwdw.substring(fwdw.indexOf("*") + 1, fwdw.length());
			}
			fwContent += "<fwdw>" + fwdw + "</fwdw>";//发往单位
			String csdw = objs[24] == null ? "" : objs[24].toString();
			if(CommonUtil.stringNotNULL(csdw) && csdw.contains("*")){
				csdw = csdw.substring(csdw.indexOf("*") + 1, csdw.length());
			}
			fwContent += "<csdw>" + csdw + "</csdw>";//抄送单位
			fwContent += "<zzjgdm>" + (objs[25] == null ? "" : objs[25].toString()) + "</zzjgdm>";//组织机构代码
			fwContent += "<bz>" + (objs[26] == null ? "" : objs[26].toString()) + "</bz>";//备注
		}
		List<Object[]> process = archiveWebServiceDao.getProcessList(instanceId);
		String operations = getOperationsStr(process);//业务内容
		String orgUsers = getOrgUsers(process);//机构人员
		String attachStr = getAttachToXml(instanceId);//附件内容
		fwContent = fwContent + operations + orgUsers + attachStr + "</document>";
		xml = xml + fwContent; 
//		System.out.println("------Send Doc--------------send xml=" + xml);
		return xml;
	}
	
	/**
	 * 格式化收文xml
	 * @param list
	 * @param instanceId
	 * @return
	 */
	private String formatAcceptDocXml(List<Object[]> list, String instanceId){
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		String swContent = "<document type=\"accept\">";
		if(list != null && list.size() > 0){
			Object[] objs = list.get(0);
			swContent += "<swdjh>" + (objs[1] == null ? "" : objs[1].toString()) + "</swdjh>";//收文登记号，必填
			swContent += "<wjlyfl>" + (objs[2] == null ? "" : objs[2].toString()) + "</wjlyfl>";//文件来源分类
			swContent += "<jjcd>" + (objs[3] == null ? "" : objs[3].toString()) + "</jjcd>";//紧急程度
			swContent += "<mj>" + (objs[4] == null ? "" : objs[4].toString()) + "</mj>";//密级
			swContent += "<zrz>" + (objs[5] == null ? "" : objs[5].toString()) + "</zrz>";//责任者
			swContent += "<wh>" + (objs[6] == null ? "" : objs[6].toString()) + "</wh>";//文号
			swContent += "<tm>" + (objs[7] == null ? "" : objs[7].toString()) + "</tm>";//文件标题
			swContent += "<cwrq>" + (objs[8] == null ? "" : objs[8].toString()) + "</cwrq>";//成文日期
			swContent += "<fs>" + (objs[9] == null ? "" : objs[9].toString()) + "</fs>";//份数
			swContent += "<ys>" + (objs[10] == null ? "" : objs[10].toString()) + "</ys>";//页数
			swContent += "<ztc>" + (objs[11] == null ? "" : objs[11].toString()) + "</ztc>";//主题词
			swContent += "<wz>" + (objs[12] == null ? "" : objs[12].toString()) + "</wz>";//文种
			swContent += "<gb>" + (objs[13] == null ? "" : objs[13].toString()) + "</gb>";//电子文件稿本
			swContent += "<lb>" + (objs[14] == null ? "" : objs[14].toString()) + "</lb>";//电子文件类别
			swContent += "<qwbs>" + (objs[15] == null ? "" : objs[15].toString()) + "</qwbs>";//全文标识
			swContent += "<swrq>" + (objs[16] == null ? "" : objs[16].toString()) + "</swrq>";//收文日期
			swContent += "<swh>" + (objs[17] == null ? "" : objs[17].toString()) + "</swh>";//收文号
			swContent += "<swz>" + (objs[18] == null ? "" : objs[18].toString()) + "</swz>";//收文者
			swContent += "<nbyj>" + (objs[19] == null ? "" : objs[19].toString()) + "</nbyj>";//拟办意见
			swContent += "<ldps>" + (objs[20] == null ? "" : objs[20].toString()) + "</ldps>";//领导批示
			swContent += "<zbbm>" + (objs[21] == null ? "" : objs[21].toString()) + "</zbbm>";//主办部门
			swContent += "<xbbm>" + (objs[22] == null ? "" : objs[22].toString()) + "</xbbm>";//协办部门
			swContent += "<cljg>" + (objs[23] == null ? "" : objs[23].toString()) + "</cljg>";//处理结果
			swContent += "<zzjgdm>" + (objs[24] == null ? "" : objs[24].toString()) + "</zzjgdm>";//组织机构代码
			swContent += "<bz>" + (objs[25] == null ? "" : objs[25].toString()) + "</bz>";//备注
		}
		List<Object[]> process = archiveWebServiceDao.getProcessList(instanceId);
		String operations = getOperationsStr(process);//业务内容
		String orgUsers = getOrgUsers(process);//机构人员
		String attachStr = getAttachToXml(instanceId);//附件内容
		swContent = swContent + operations + orgUsers + attachStr + "</document>";
		xml = xml + swContent; 
//		System.out.println("--------Accept Doc------------accept xml=" + xml);
		return xml;
	}
	
	/**
	 * 获取业务内容
	 * @return
	 */
	private String getOperationsStr(List<Object[]> process){
		
		String operations = "<operations>";//业务操作
		String operatestate = "";
		for(Object[] o : process)
		{
			
			if(null!=o[13]&&"2".equals(o[13].toString()))
			{
				operatestate="撤销";
			}else if (null!=o[25]&&"OVER".equals(o[25].toString()))
			{
				operatestate="已办理";
			}else
			{
				operatestate="未办理";
			}
			
			
			
			operations += "<operation>";
			operations += "<ywbsf>"+o[0]==null?"":o[0].toString()+"</ywbsf>";//业务标识符必填
			operations += "<jgrybsf>"+o[6]==null?"":o[6].toString()+"</jgrybsf>";//机构人员标识符
			operations += "<wjbsf>附件1</wjbsf>";//文件标识符
			operations += "<operatestate>"+operatestate+"</operatestate>";//业务状态
			operations += "<operate>"+o[4]==null?"":o[4].toString()+"</operate>";//业务行为
			operations += "<dotimes>"+o[12]==null?"":o[12].toString()+"</dotimes>";//行为时间
			operations += "<doaccorde></doaccorde>";//行为依据
			operations += "<dobewrite></dobewrite>";//行为描述
			operations += "<jgrybs></jgrybs>";//机构人员标识，作废，不再使用
			operations += "</operation>";
		}
		
		operations += "</operations>";
		return operations;
	}
	
	/**
	 * 获取机构人员
	 * @return
	 */
	private String getOrgUsers(List<Object[]> process){
		String orgUsers = "<orgusers>";//机构人员
		for(Object[] o : process)
		{
			orgUsers += "<orguser>";
			orgUsers += "<jgrybsf>"+o[6]==null?"":o[6].toString()+"</jgrybsf>";//机构人员标识符 必填
			orgUsers += "<jgrylx>"+o[10]==null?"":o[10].toString()+"</jgrylx>";//机构人员类型 必填
			orgUsers += "<jgrymc>"+o[7]==null?"":o[7].toString()+"</jgrymc>";//机构人员名称 必填
			orgUsers += "<zzjgdm></zzjgdm>";//组织机构代码
			orgUsers += "<grzw>"+o[26]==null?"":o[26].toString()+"</grzw>";//个人职位
			orgUsers += "<fjgrybsf>"+o[9]==null?"":o[9].toString()+"</fjgrybsf>";//所属机构人员标识符
			orgUsers += "</orguser>";
		}
		orgUsers += "</orgusers>";
		return orgUsers;
	}
	
	/**
	 * 获取办件附件并转换成xml字符串
	 * @param instanceId
	 * @return
	 */
	private String getAttachToXml(String instanceId){
		System.out.println("----getAttachToXml---------1");
		String attachStr = "";
		List<SendAttachments> attachList = attachmentDao.findSendAttachmentListByInstanceId(instanceId);//获取附件列表
		if(attachList != null && attachList.size() > 0){
			for(int i = 0; i < attachList.size(); i++){
				SendAttachments attach = attachList.get(i);
				String fileName = attach.getFilename();//文件名
				String fileStr = readFromFile(attach.getLocalation());//文件字符串
				attachStr = "<file>" + "<filename>" + fileName + "</filename>" + "<filedata><![CDATA[" + fileStr + "]]></filedata>" + "</file>";
			}
		}
		return attachStr;
	}
	
	/**
	 * 将文件读成字符串，根据文档，返回base64编码字符串
	 * @param fileLocation
	 * @return
	 */
	private String readFromFile(String fileLocation){
		String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
		File attachFile = new File(basePath + fileLocation);
		String fileStr = "";
		if (attachFile.exists()) {
			try {
				FileInputStream fin = new FileInputStream(attachFile);
				int size = fin.available();
				byte[] buffer = new byte[size];
				fin.read(buffer);
				fin.close();
				fileStr = new BASE64Encoder().encode(buffer);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileStr;
	}
	
	public static void main(String args[]){
		ArchiveWebServiceImpl a = new ArchiveWebServiceImpl();
		String b = "attachments/2016/12/21/B6B86B50-D04F-4E6B-902B-0A2C50554530.docx";//指定测试附件路径
		
		String c="{8619dd33-2fa0-40df-b945-b68192602966}[6],{d344a5b3-2e1e-4c0e-a878-4772460a1ba1}[6],{f719a7f2-83df-46c9-b8e0-40ce2f370ec6}[6],{34f105d1-5bde-45d4-af18-8ae7850ca54d}[6],{1afdf051-f97a-4008-b249-9ca46659f58d}[6],{62664cbb-9de5-4e3d-82b8-b05457c4f292}[6],{75d314fe-105c-4a9d-9a17-148e3ef100c1}[6],{d67936d1-3033-4988-a3be-edc4db13123d}[6],{caeb83c5-d841-43e7-b8c2-90d3d2563546}[6],{3e9227ea-08cf-4cd7-bbdc-0c12f36bfa94}[6],{8fcd74cd-238a-4d5c-b654-b35e11dffbba}[6],{7c2a7cf8-f082-400f-8b68-644ddef6c5f8}[6]*纪委,组织部,宣传部,统战部,政策研究室（社建工委）,区委政法委,机关工委,党史办,老干部局,党校,610办公室,信访局";
		//String fileStr = a.readFromFile(b);
		String d = c.substring(c.indexOf("*") + 1, c.length());
		System.out.println(d);
	}
}
