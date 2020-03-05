package cn.com.trueway.document.business.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.ws.commons.util.Base64;

import cn.com.trueway.oa.info.SystemParamConfigUtil;

public class GetAxisInterface {
	
	/**
	 * 通用调用接口
	 * @param url  接口url
	 * @param method  接口调用方法
	 * @param paramMap  参数map
	 * @return String   返回的xml
	 *
	 * 创建时间:2013-9-10 下午02:28:34
	 */
	@SuppressWarnings("unchecked")
	public static String getAsixContent(String url ,String method ,Map<String,String> paramMap) {
		try
		   {
			
		   // String endPoint="http://10.132.22.62/CgjWebService/User.asmx";
			String params = "?";
			Set set = paramMap.keySet();
			Iterator i = set.iterator();
			while(i.hasNext()){
				String p = (String) i.next();
				params += p+"="+paramMap.get(p)+"&";
			}
			if(params.length()>1){
				params = params.substring(0,params.length()-1);
				url += params;
			}
			String endPoint=url;
			 
		    Service service = new Service();
		    Call call = (Call)service.createCall();
		    call.setTargetEndpointAddress(new java.net.URL(endPoint));
//		    call.setOperation("SelectOu");
		    call.setUseSOAPAction(true);
//		    call.setSOAPActionURI("");  
		    
		    //call.setOperationName(new QName("http://tempuri.org/","SelectOu"));      
		    call.setOperationName(new QName("http://tempuri.org/",method));  
		    
		    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);  
		    String str=(String)call.invoke( new Object[]{});
		    System.out.println(str);
		    return str;
		   }catch(Exception e)
		   {
		    e.printStackTrace();
		    return null;
		   }  
	}
	
	/**
	 * 根据部门id得到子部门
	 * @param ParentDeptId
	 * @return String
	 *
	 * 作者:zhuxc<br>
	 * 创建时间:2013-11-7 下午05:50:34
	 */
	public static String getDept(String ParentDeptId){
		try
		   {
				String domain = "http://tempuri.org/";
				String endPoint=SystemParamConfigUtil.getParamValueByParam("depEndPoint");
//				String userId = SystemParamConfigUtil.getParamValueByParam("ReceivingClerkID");
				String option = "Organization_SubWebFlow";
				String xml = "<PublicKey>5AhmxDpueo5STOiepDSRwyAK/O4=</PublicKey>";
	
			    Service service = new Service();
			    Call call = (Call)service.createCall();
			    call.setTargetEndpointAddress(new java.net.URL(endPoint));
			    call.setOperation(option);
			    call.setUseSOAPAction(true);
			    call.setSOAPActionURI(domain+option);  
			    call.setOperationName(new QName(domain,option));      
			    call.addParameter(new QName(domain,"strXml"),org.apache.axis.encoding.XMLType.XSD_STRING,
			    	      javax.xml.rpc.ParameterMode.IN);
			    call.addParameter(new QName(domain,"ParentOuGuid"),org.apache.axis.encoding.XMLType.XSD_STRING,
			    	      javax.xml.rpc.ParameterMode.IN);
			    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);  
			    String str=(String)call.invoke( new Object[]{xml,ParentDeptId});
			    System.out.println(str); 
			    return str;
		   }catch(Exception e)
		   {
			    e.printStackTrace();
		   }
		return null;  
	}
	
	/**
	 * 待收列表
	 * @return String
	 *
	 * 作者:zhuxc<br>
	 * 创建时间:2013-11-1 上午09:35:31
	 */
	public static String getUnReceived(){
		try
		   {
			    String domain = "http://tempuri.org/";
//				String endPoint="http://10.132.22.63/EpointExChangeCenter/ManageArchive_Webservice2.asmx";  
//				String option = "InboxArchive_Unreceipted";
	
				String url = SystemParamConfigUtil.getParamValueByParam("unReceivedEndPoint");
				String endPoint =  url.substring(0,url.indexOf("?op="));
				String option =  url.substring(url.indexOf("?op=")+4);
				String cgj_deptId = SystemParamConfigUtil.getParamValueByParam("OA_deptId");
				
			    Service service = new Service();
			    Call call = (Call)service.createCall();
			    call.setTargetEndpointAddress(new java.net.URL(endPoint));
			    call.setOperation(option);
			    call.setUseSOAPAction(true);
			    call.setSOAPActionURI(domain+option);  
			    call.setOperationName(new QName(domain,option));      
			    call.addParameter(new QName(domain,"TokenBase64"),org.apache.axis.encoding.XMLType.XSD_STRING,
			    	      javax.xml.rpc.ParameterMode.IN);
			    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);  
			    String str=(String)call.invoke( new Object[]{cgj_deptId});
			    str = str.substring(str.indexOf("</ReturnInfo><UserArea>")+23,str.indexOf("</UserArea></UserArea></DATA></EpointDataBody>"));
			    return str;
		   }catch(Exception e)
		   {
		    e.printStackTrace();
		   }  
		   return "";
	}
	
	/**
	 * 收取公文
	 * @param docGuid
	 * @return String
	 *
	 * 作者:zhuxc<br>
	 * 创建时间:2013-11-7 下午05:49:54
	 */
	public static String receiveDoc(String docGuid){
		try
		   {
			   	String domain = "http://tempuri.org/";
			   	String url = SystemParamConfigUtil.getParamValueByParam("receiveDocEndPoint");
			   	String endPoint =  url.substring(0,url.indexOf("?op="));
				String option =  url.substring(url.indexOf("?op=")+4);
				String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?><paras><InboxGuid>"+docGuid+"</InboxGuid></paras>";
				String cgj_deptId = SystemParamConfigUtil.getParamValueByParam("OA_deptId");
				
			    Service service = new Service();
			    Call call = (Call)service.createCall();
			    call.setTargetEndpointAddress(new java.net.URL(endPoint));
			    call.setOperation(option);
			    call.setUseSOAPAction(true);
			    call.setSOAPActionURI(domain+option);  
			    call.setOperationName(new QName(domain,option));      
			    call.addParameter(new QName(domain,"TokenBase64"),org.apache.axis.encoding.XMLType.XSD_STRING,
			    	      javax.xml.rpc.ParameterMode.IN);
			    call.addParameter(new QName(domain,"ParasXml"),org.apache.axis.encoding.XMLType.XSD_STRING,
			    	      javax.xml.rpc.ParameterMode.IN);
			    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);  
			    String str=(String)call.invoke( new Object[]{cgj_deptId,xml});
			    return str;    
		   }catch(Exception e)
		   {
		    e.printStackTrace();
		   }  
		   return "";    
	}

	/**
	 * 
	 * 描述：发送公文接口
	 *
	 * 作者:zhuxc<br>
	 * 创建时间:2013-10-31 上午09:51:26
	 */
	public static String sendDoc(String docInfoXml) {
//		docInfoXml = "<?xml version=\"1.0\" encoding=\"gb2312\"?><paras><DeptGuid>66a91d33-1ca3-45c1-ac17-605d9ec94e58</DeptGuid><DeptName>城市管理局</DeptName><电子公文 公文标识=\"\" 版本号=\"1.0\" 公文类别=\"行政\" 公文种类=\"\"><发文机关 组织机构代码=\"\" 办理类别=\"主办\"></发文机关><公文体>	<眉首><秘密等级></秘密等级><保密期限></保密期限><紧急程度></紧急程度><发文机关标识><发文机关名称></发文机关名称><标识后缀>文件</标识后缀></发文机关标识><发文字号><发文机关代字>通城管发</发文机关代字><发文年号>2013</发文年号><发文序号>5</发文序号></发文字号></眉首>	<主体>		<标题>测试发文1</标题>		<是否回复>0</是否回复>		<关联收文></关联收文>		<发文号>通城管发[2013]5</发文号>		<主送机关></主送机关><正文></正文><附件></附件><公文生效标识>	<发文机关署名></发文机关署名>	<签发人署名></签发人署名></公文生效标识><成文日期>null</成文日期><附注>无</附注></主体><版记>	<主题词>		<词目>空</词目>	</主题词><抄送机关 抄送类型=\"抄报\"></抄送机关><印制版记>	<印发机关></印发机关>	<印发日期></印发日期>	<印发份数>0</印发份数></印制版记></版记></公文体></电子公文></paras>";
		try
		   {
			    String domain = "http://tempuri.org/";
			    String url = SystemParamConfigUtil.getParamValueByParam("sendDocEndPoint");
				String endPoint =  url.substring(0,url.indexOf("?op="));
				String option =  url.substring(url.indexOf("?op=")+4);
				String cgj_deptId = SystemParamConfigUtil.getParamValueByParam("OA_deptId");
				
//				File certFile = new File("c:/城管局.cer");
//				FileInputStream file_inputstream=new FileInputStream(certFile);
//				byte[] certByte = new byte[(int) certFile.length()];
//				file_inputstream.read(certByte);
//				String certString = Base64.encode(certByte);
				
			    Service service = new Service();
			    Call call = (Call)service.createCall();
			    call.setTargetEndpointAddress(new java.net.URL(endPoint));
			    call.setOperation(option);
			    call.setUseSOAPAction(true);
			    call.setSOAPActionURI(domain+option);  
			    call.setOperationName(new QName(domain,option));      
			    call.addParameter(new QName(domain,"TokenBase64"),org.apache.axis.encoding.XMLType.XSD_STRING,
			    	      javax.xml.rpc.ParameterMode.IN);
			    call.addParameter(new QName(domain,"ParasXml"),org.apache.axis.encoding.XMLType.XSD_STRING,
			    	      javax.xml.rpc.ParameterMode.IN);
			    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);  
			    String str=(String)call.invoke( new Object[]{cgj_deptId,docInfoXml});
			    System.out.println(str);   
			    return str;
		   }catch(Exception e)
		   {
		    e.printStackTrace();
		   }  
		   return "";
	}
	
	/**
	 * 反馈公文已收
	 * @param docGuid
	 * @return String
	 *
	 * 作者:zhuxc<br>
	 * 创建时间:2013-11-7 下午05:49:38
	 */
	public static String signReceive(String docGuid){
		try
		   {
			   	String domain = "http://tempuri.org/";
			   	String url = SystemParamConfigUtil.getParamValueByParam("responceDocEndPoint");
			   	String endPoint =  url.substring(0,url.indexOf("?op="));
				String option =  url.substring(url.indexOf("?op=")+4);
				String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?><paras><originalPaperGUID>"+docGuid+"</originalPaperGUID></paras>";
				String cgj_deptId = SystemParamConfigUtil.getParamValueByParam("OA_deptId");
				
			    Service service = new Service();
			    Call call = (Call)service.createCall();
			    call.setTargetEndpointAddress(new java.net.URL(endPoint));
			    call.setOperation(option);
			    call.setUseSOAPAction(true);
			    call.setSOAPActionURI(domain+option);  
			    call.setOperationName(new QName(domain,option));      
			    call.addParameter(new QName(domain,"TokenBase64"),org.apache.axis.encoding.XMLType.XSD_STRING,
			    	      javax.xml.rpc.ParameterMode.IN);
			    call.addParameter(new QName(domain,"ParasXml"),org.apache.axis.encoding.XMLType.XSD_STRING,
			    	      javax.xml.rpc.ParameterMode.IN);
			    call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);  
			    String str=(String)call.invoke( new Object[]{cgj_deptId,xml});
			    return str;    
		   }catch(Exception e)
		   {
		    e.printStackTrace();
		   }  
		   return "";    
	}

	public static void main(String[] args) {
		sendDoc("");
	}
}
