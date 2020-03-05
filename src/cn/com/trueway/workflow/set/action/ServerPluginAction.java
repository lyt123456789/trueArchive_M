package cn.com.trueway.workflow.set.action;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.set.pojo.ServerPlugClass;
import cn.com.trueway.workflow.set.pojo.ServerPlugMethod;
import cn.com.trueway.workflow.set.pojo.ServerPlugParam;
import cn.com.trueway.workflow.set.pojo.ServerPlugin;
import cn.com.trueway.workflow.set.service.ServerPluginService;
import cn.com.trueway.workflow.set.util.Constants;
import cn.com.trueway.workflow.set.util.DownloadFileUtil;
import cn.com.trueway.workflow.set.util.MyUtils;

public class ServerPluginAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServerPluginService serverPluginService;
	private ServerPlugin serverPlugin;
	
	private File[] file; // 上传文件域对象
	private String[] fileFileName; // 上传文件名

	public ServerPluginService getServerPluginService() {
		return serverPluginService;
	}

	public void setServerPluginService(ServerPluginService serverPluginService) {
		this.serverPluginService = serverPluginService;
	}

	public ServerPlugin getServerPlugin() {
		return serverPlugin;
	}

	public void setServerPlugin(ServerPlugin serverPlugin) {
		this.serverPlugin = serverPlugin;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	
	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * 转向列表页
	 * @return
	 */
	public String getList(){
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		String type=getRequest().getParameter("type");
		if (CommonUtil.stringNotNULL(type)) {
			getRequest().setAttribute("type", type);
			
		}
		
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count=serverPluginService.getServerPluginListCountByParamForPage(null,column, value, null, null);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<ServerPlugin> list=serverPluginService.getServerPluginListByParamForPage(null,column, value, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		return "getList";
	}
	
	public String toAdd(){
		
		return "toAdd";
	}
	public String add(){
		String serverplugincname=getRequest().getParameter("serverplugincname");
		ServerPlugin p=new ServerPlugin();
		p.setCname(serverplugincname);
		p.setXml_name(fileFileName[0]);
		p.setXml_url(uploadFile(file[0], fileFileName[0]));//开始上传xml
		p.setJar_name(fileFileName[1]);
		p.setJar_url(uploadFile(file[1], fileFileName[1]));//开始上传jar
		p.setIntime(CommonUtil.getNowTimeTimestamp(null));
		
		serverPluginService.addServerPlugin(p);
		
		
		//非常关键，把jar注入eclipse或tomcat运行环境中
		String jarAllUrl=Constants.getAttachmentPath()+p.getJar_url();//上传成功后源jar所在的位置
		String jarEnvAllUrl=PathUtil.getWebRoot()+"WEB-INF/lib/"+p.getJar_name();//需要复制到运行环境中的 位置
		MyUtils.copy(new File(jarAllUrl), new File(jarEnvAllUrl));// 完成上传文件，就是将本地文件复制到服务器上
		return getList();
	}
	
	public String uploadFile(File file,String filename){
		String dstPath =null;
		if (null != file && file.exists() && file.length() != 0) {
			try {
				// 添加源文件至服务端
				String uploadfilename = filename; // 上传文件的真实文件名
				String basePath = Constants.getAttachmentPath();// 得到上传文件在服务器上的基路径
				dstPath = MyUtils.getRealFilePath(uploadfilename, basePath); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
				File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
				MyUtils.copy(file, dstFile);// 完成上传文件，就是将本地文件复制到服务器上
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return dstPath;
	}
	
	
	
	
	public String toUpdate(){
		String id=getRequest().getParameter("id");
		ServerPlugin serverPlugin=serverPluginService.getOneServerPluginById(id);
		getRequest().setAttribute("serverPlugin", serverPlugin);
		return "toUpdate";
	}
	public String update(){
		String serverplugincname=getRequest().getParameter("serverplugincname");
		String id=getRequest().getParameter("id");
		ServerPlugin p=serverPluginService.getOneServerPluginById(id);
		p.setCname(serverplugincname);
		if (file[0]!=null&&CommonUtil.stringNotNULL(fileFileName[0])) {
			p.setXml_name(fileFileName[0]);
			p.setXml_url(uploadFile(file[0], fileFileName[0]));//开始上传xml
		}
		if (file[1]!=null&&CommonUtil.stringNotNULL(fileFileName[1])) {
			p.setJar_name(fileFileName[1]);
			p.setJar_url(uploadFile(file[1], fileFileName[1]));//开始上传jar
		}
		p.setUpdatetime(CommonUtil.getNowTimeTimestamp(null));
		
		serverPluginService.updateServerPlugin(p);
		
		//如果jar重新载入后，必须重新注入环境中
		if (file[1]!=null&&CommonUtil.stringNotNULL(fileFileName[1])) {
			//非常关键，把jar注入eclipse或tomcat运行环境中
			String jarAllUrl=Constants.getAttachmentPath()+p.getJar_url();//上传成功后源jar所在的位置
			String jarEnvAllUrl=PathUtil.getWebRoot()+"WEB-INF/lib/"+p.getJar_name();//需要复制到运行环境中的 位置
			MyUtils.copy(new File(jarAllUrl), new File(jarEnvAllUrl));// 完成上传文件，就是将本地文件复制到服务器上
		}
		//测试调用
		//getServerPluginValue(p.getId());
		
		
		
		return getList();
	}
	public String view(){
		String id=getRequest().getParameter("id");
		ServerPlugin serverPlugin=serverPluginService.getOneServerPluginById(id);
		getRequest().setAttribute("serverPlugin", serverPlugin);
		return "view";
	}
	
	public String delete(){
		String ids=getRequest().getParameter("ids");
		if (CommonUtil.stringNotNULL(ids)) {
			String[] idarr=ids.split(",");
			for (int i = 0; i < idarr.length; i++) {
				ServerPlugin p=new ServerPlugin();
				p.setId(idarr[i]);
				serverPluginService.deleteServerPlugin(p);
			}
		}
		return getList();
	}
	
	public void download() {
		try {
				String id = getRequest().getParameter("id");
				String type = getRequest().getParameter("type");
				String basePath = Constants.getAttachmentPath();// 得到上传文件在服务器上的基路径
				
				String fullPath="";
				String filename="";
				
				ServerPlugin p=serverPluginService.getOneServerPluginById(id);
				
				if (p!=null) {
					if (type.equals("xml")) {
						fullPath=basePath+p.getXml_url();
						filename=p.getXml_name();
					}else if (type.equals("jar")){
						fullPath=basePath+p.getJar_url();
						filename=p.getJar_name();
					}
					// 文件下载
					DownloadFileUtil.downloadFile(getResponse(), fullPath, filename);
					
					parseXmlToBean(fullPath);
				}
//				return null;
		} catch (Exception e) {
			e.printStackTrace();
//			return "fileDownloadError";
		}
	}
	//查询解析所有服务器插件详细信息(包括解析后的方法、参数属性)
	public List<ServerPlugin> getAllServerPlugins(){
		List<ServerPlugin> list=serverPluginService.getAllServerPluginList();
		if (list!=null) {
			for (int i = 0; i < list.size(); i++) {
				ServerPlugin p=list.get(i);
				if (CommonUtil.stringNotNULL(p.getXml_url())) {
//					String basePath = Constants.getAttachmentPath();// 得到上传文件在服务器上的基路径
//					File file=new File(basePath+p.getXml_url());
//					if (file.exists()) {
//						p.setServerPlugClass(parseXmlToBean(basePath+p.getXml_url()));
//					}
					
				}
			}
		}
		return list; 
	}
	
	/**
	 * 解析xml文件内容至bean对象
	 * @return
	 */
	public ServerPlugClass parseXmlToBean(String allfilepath){
		
		
		ServerPlugClass c=new ServerPlugClass();
		//dom4j方式解析
		Document document=null;
		SAXReader saxReader=new SAXReader();
		try {
			document=saxReader.read(new File(allfilepath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		//解析类属性
		Element root=document.getRootElement();
		c.setName(getAttributeValue(root,"name"));
		c.setZhName(getAttributeValue(root,"zhName"));
		c.setJarName(getAttributeValue(root,"jar"));
		c.setClassUrl(getAttributeValue(root,"class"));
		c.setReturnMethod(getAttributeValue(root,"returnMethod"));
		
		List<ServerPlugMethod> methodList=new ArrayList<ServerPlugMethod>();
		//遍历解析方法属性
		Iterator i=root.elementIterator();
		while (i.hasNext()) {//server节点
			ServerPlugMethod m=new ServerPlugMethod();
			Element methodElement=(Element)i.next();
			m.setId(getAttributeValue(methodElement,"id"));
			m.setName(getAttributeValue(methodElement,"name"));
			m.setZhName(getAttributeValue(methodElement,"zhName"));
			//遍历解析参数属性
			Iterator i_param=methodElement.elementIterator();
			while (i_param.hasNext()) {//inputFields、outputFields节点
				Element paramElement=(Element)i_param.next();
				if (paramElement.getName().equals("inputFields")) {
					Iterator i_inout=paramElement.elementIterator();
					List<ServerPlugParam> paramList=new ArrayList<ServerPlugParam>();
					while (i_inout.hasNext()) {//field节点
						ServerPlugParam p=new ServerPlugParam();
						Element fieldElement=(Element)i_inout.next();
						p.setName(getAttributeValue(fieldElement,"name"));
						p.setZhName(getAttributeValue(fieldElement,"zhName"));
						p.setType(getAttributeValue(fieldElement,"type"));
						p.setLength(getAttributeValue(fieldElement,"length"));
						paramList.add(p);
					}
					m.setInputs(paramList);
				}else if (paramElement.getName().equals("outputFields")) {
					Iterator i_inout=paramElement.elementIterator();
					List<ServerPlugParam> paramList=new ArrayList<ServerPlugParam>();
					while (i_inout.hasNext()) {//field节点
						ServerPlugParam p=new ServerPlugParam();
						Element fieldElement=(Element)i_inout.next();
						p.setName(getAttributeValue(fieldElement,"name"));
						p.setZhName(getAttributeValue(fieldElement,"zhName"));
						p.setType(getAttributeValue(fieldElement,"type"));
						p.setLength(getAttributeValue(fieldElement,"length"));
						paramList.add(p);
					}
					m.setOuts(paramList);
				}
			}
			methodList.add(m);
		}
		c.setMethods(methodList);
		return c;
	}
	
	public String getAttributeValue(Element root,String attributeName){
		String value=null;
		List attributes=root.attributes();
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute=(Attribute)attributes.get(i);
			String name=attribute.getName();
			if (name.equals(attributeName)) {
				value=attribute.getValue();break;
			}
		}
		return value;
	}
	
	public String getServerPluginValue(String serverPlugId){
		ServerPlugin p=serverPluginService.getOneServerPluginById(serverPlugId);
		ServerPlugClass c=null;
		String revalue=null;
		if (CommonUtil.stringNotNULL(p.getXml_url())) {
			String basePath = Constants.getAttachmentPath();// 得到上传文件在服务器上的基路径
			File file=new File(basePath+p.getXml_url());
			if (file.exists()) {
				c=parseXmlToBean(basePath+p.getXml_url());
			}
		}
		if (c!=null) {
			//目前只考虑只有一个输出参数的情况
			
			//java反射机制 根据类名称和方法名称 动态调用方法
			try {
				Class class1=Class.forName(c.getClassUrl());
				Object obj=class1.newInstance();
				Method method=class1.getMethod(c.getReturnMethod());
				//输入参数固定为一个，且为map数组
				Map map=new HashMap();
				revalue=(String)method.invoke(obj,map);
				System.out.println("*****************************服务器插件方法"+c.getReturnMethod()+"()执行成功************************************");
				System.out.println("*****************************返回值:"+revalue+"************************************");
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return revalue;
	}
	
}
