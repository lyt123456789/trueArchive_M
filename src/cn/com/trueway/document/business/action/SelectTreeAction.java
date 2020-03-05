package cn.com.trueway.document.business.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.DepSortUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.NTSDep2Json;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.business.docxg.client.service.DocExchangeClient;
import cn.com.trueway.document.business.docxg.client.support.DocXgXmlUtil;
import cn.com.trueway.document.business.docxg.client.support.GenUserKey;
import cn.com.trueway.document.business.docxg.client.vo.DepRelationShip;
import cn.com.trueway.document.business.docxg.client.vo.GwDepart;
import cn.com.trueway.document.business.docxg.client.vo.GwDepartext;
import cn.com.trueway.document.business.docxg.client.vo.VGwDepart;
import cn.com.trueway.document.business.model.Depgroup;
import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.document.business.model.EmpGroup;
import cn.com.trueway.document.business.service.SelectTreeService;
import cn.com.trueway.document.business.util.GetAxisInterface;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.set.pojo.CommonGroup;
import cn.com.trueway.workflow.set.pojo.CommonGroupUsers;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeService;
import cn.com.trueway.workflow.set.service.ZtreeService;

import com.google.gson.Gson;

@SuppressWarnings("unchecked")
public class SelectTreeAction extends BaseAction {
	private static final long serialVersionUID = -697699384976348436L;
	private Logger logger = Logger.getLogger(this.getClass());
	private DocExchangeClient docExchangeClient;
	
	private DepartmentService departmentService;
	
	private EmployeeService employeeService;
	
	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	private SelectTreeService selectTreeService;
	
	private TableInfoService tableInfoService ;
	
	private ZtreeService ztreeService;
	
	private String endTr;// 用于显示部门选择页面的结束行
	private String root;//root="source",生成“大部门”的节点
	
	public DocExchangeClient getDocExchangeClient() {
		return docExchangeClient;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setDocExchangeClient(DocExchangeClient docExchangeClient) {
		this.docExchangeClient = docExchangeClient;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public String getEndTr() {
		return endTr;
	}

	public void setEndTr(String endTr) {
		this.endTr = endTr;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
	
	public SelectTreeService getSelectTreeService() {
		return selectTreeService;
	}

	public void setSelectTreeService(SelectTreeService selectTreeService) {
		this.selectTreeService = selectTreeService;
	}
	
	public ZtreeService getZtreeService() {
		return ztreeService;
	}

	public void setZtreeService(ZtreeService ztreeService) {
		this.ztreeService = ztreeService;
	}

	/**
	 * 获取公文交换机构
	 * @param  depId 读取sys中的配置参数, 用于生成userKey,用于验证是否是否可以同步机构
	 */
	public void initDocXgDept (){
		String depId  = SystemParamConfigUtil.getParamValueByParam("gwjhpt_initdepId");
		String userKey = GenUserKey.genUserKey(depId);
		String xmlStr = docExchangeClient.getAllDocDepartments(userKey);
		try {
			List<VGwDepart> list = DocXgXmlUtil.genDepartmentModelsFromXML(xmlStr);
			selectTreeService.addVGwDepart(list);
			System.out.println("insertDocXgDept:"+"插入部门数据完成！！！！");
			getResponse().getWriter().print("success");
			getResponse().getWriter().close();
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到设置本地机构与公文交换机构对应关系
	 */
	public  String setDepExchangeRelation(){
		List<DepRelationShip> list  = selectTreeService.getDepRelationShipList();
		getRequest().setAttribute("list", list);
		return "relationset";
	}
	
	
	/**
	 * 获取国土局资源管理系统中docexchange_department表手动维护的机构树
	 * @throws Exception
	 */
	public void getExchangeDepartmentTree() throws Exception{
		List<DocXgDepartment> pdept = selectTreeService.getDocXgDept("");
		PrintWriter out = getResponse().getWriter();
		DocXgDepartment dep = null;
		if(pdept!=null && pdept.size()>0){
			out.println("[");
			for (int i = 0; i < pdept.size(); i++) {
				dep = pdept.get(i);
				String rootString = dep!=null?dep.getName():"根节点";
				out.print(" {\"text\":\"<a  id='"+dep.getDeptGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
				out.print(rootString);// 节点的名字
				out.print("</a>\",");
				out.print("\"id\":\"");
				out.print(0);// 节点的id
				out.print("\",\"expanded\":");
				out.print(true);// 默认是否是展开方式
				out.print(",\"classes\":\"");
				out.print("folder");// 节点的样式
				out.print("\"");
				out.println(",");
				out.print("\"children\":");
				out.println("[");
				List<DocXgDepartment> cdepts = selectTreeService.getDocXgDept(dep.getDeptGuid());
				if (cdepts != null && cdepts.size() > 0) {
					createJSON(cdepts, out);
				}	
				out.print("]");
				if(i== (pdept.size()-1)){
					out.println("}");
				}else{
					out.println("},");
				}
			}
			out.print("]");
		}
		
	/*
	  
	 
		if(pdept!=null && pdept.size()>0){
		if(dep!=null){
			// 生成JSON字符串
			String rootString = dep!=null?dep.getName():"根节点";
			out.println("[");
			out.print(" {\"text\":\"<a  id='"+dep.getDeptGuid()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
			out.print(rootString);// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(0);// 节点的id
			out.print("\",\"expanded\":");
			out.print(true);// 默认是否是展开方式
			out.print(",\"classes\":\"");
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println(",");
			out.print("\"children\":");
			out.println("[");
			List<DocXgDepartment> cdepts = selectTreeService.getDocXgDept(dep.getDeptGuid());
			if (cdepts != null && cdepts.size() > 0) {
				createJSON(cdepts, out);
			}	
			out.print("]");
			out.println("}");
			out.print("]");
		}
		 
		}
		*/
		out.close();
	}
	
	public void createJSON(List departments, PrintWriter out)
			throws IOException {
		DocXgDepartment department;
		for (int i = 0; departments!=null && i < departments.size(); i++) {
			department = (DocXgDepartment) departments.get(i);
			out.print(" {\"text\":\"<a  id='"+department.getId()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
			out.print(department.getName());// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(department.getId());// 节点的id
				out.print("\",\"hasChildren\":");
				out.print(false);
				out.print(",\"classes\":\"");
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != departments.size() - 1) {
				out.println(",");
			}
		}
	}
	
	/**
	 * 获取公文交换系统中同步过来的机构树
	 * @throws Exception
	 */
	public void getDocxgDepartmentTree() throws Exception{
		PrintWriter out = getResponse().getWriter();
		List<GwDepart>  list  = selectTreeService.getAllDeps();
		out.println("[");
		for(int i=0; i<list.size(); i++){
			GwDepart depart = list.get(i);
			if(depart!=null){
				// 生成JSON字符串
				String rootString = depart!=null?depart.getName():"根节点";
				//out.print(" {\"text\":\"");// 不显示链接的下划线
				out.print(" {\"text\":\"<a  id='"+depart.getId()+"' href='javascript:;' onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
				out.print(rootString);// 节点的名字
				out.print("</a>\",");
				out.print("\"id\":\"");
				out.print(0);// 节点的id
				out.print("\",\"expanded\":");
				out.print(false);// 默认是否是展开方式
				out.print(",\"classes\":\"");
				out.print("folder");// 节点的样式
				out.print("\"");
				out.println(",");
				out.print("\"children\":");
				out.println("[");
				List<GwDepartext> cdepts = selectTreeService.getDepsByPid(depart.getId());
				if (cdepts != null && cdepts.size() > 0) {
					createJSON2(cdepts, out);
				}	
				out.print("]");
				if(i==list.size()-1){
					out.println("}");
				}else{
					out.println("},");
				}
			}
		}
		out.print("]");
		out.close();
	}
	
	public void createJSON2(List departments, PrintWriter out)
			throws IOException {
		GwDepartext department;
		for (int i = 0; departments!=null && i < departments.size(); i++) {
			department = (GwDepartext) departments.get(i);
			out.print(" {\"text\":\"<a  id='"+department.getGuid()+"' href='javascript:;'  onclick='check(this,0)' style='text-decoration:none;' >");// 不显示链接的下划线
			out.print(department.getName());// 节点的名字
			out.print("</a>\",");
			out.print("\"id\":\"");
			out.print(department.getGuid());// 节点的id
				out.print("\",\"hasChildren\":");
				out.print(false);
				out.print(",\"classes\":\"");
			out.print("folder");// 节点的样式
			out.print("\"");
			out.println("}");// 第一个节点结束
			if (i != departments.size() - 1) {
				out.println(",");
			}
		}
	}
	
	/**
	 * 添加本地国土局机构树与公文交换平台直接的机构对应关系
	 */
	public void addDepartmentRelationShip(){
		//ajax提交数据
		String	gtj_depId = getRequest().getParameter("gtj_depId");
		String  docxg_depId = getRequest().getParameter("docxg_depId");
		String  docxgDepName = getRequest().getParameter("docxgDepName");
		DepRelationShip relationShip = new DepRelationShip();
		relationShip.setGtj_depId(gtj_depId);
		relationShip.setDocxg_depId(docxg_depId);
		relationShip.setDocxg_depName(docxgDepName);
		selectTreeService.addDepRelationShip(relationShip);
		try {
			getResponse().getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id获取机构名称用于展示
	 * @param  type=1 国土局; type=2 公文交换平台
	 */
	public void getDepNameById(){
		String type = getRequest().getParameter("type");
		String depId = getRequest().getParameter("depId");
		String name = "";
		if(type!=null && type.equals("1")){		//到exchange中获取相应的数据
			DocXgDepartment depart = selectTreeService.getDocXgDeptById4Set(depId);
			if(depart!=null){
				name = depart.getName();
			}
		}else if(type!=null && type.equals("2")){
			name = selectTreeService.getDocXgName(depId);
		}
		try {
			getResponse().getWriter().print(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 联合发文(根据id获取部门真实id与名称)
	 */
	public void getLhfwNameById(){
		String depId = getRequest().getParameter("depId");
		String name = "";
		DocXgDepartment depart = selectTreeService.getDocXgDeptById4Set(depId);
		if(depart!=null){
			name = depart.getDeptGuid()+","+depart.getName();
		}
		try {
			getResponse().getWriter().print(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}
	
	private JSONObject getJSONObject() {
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			if(data.length  >0 ){
				return JSONObject.fromObject(new String(data, "GBK"));
			}else{
				return null;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 发送待收列表选择机构
	 * @return
	 */
	public String showDepartment() {
		try {
			// 获取参数值 value = {{cs::::value}}
			JSONObject jsonObject  = getJSONObject();
			String isSend = "";
			String value = "";
			// 当前部门id 需要灰化当前部门
			String currentDeptId = "";
			if(jsonObject != null){
				try {
					value = jsonObject.getString("value");
					currentDeptId = jsonObject.getString("deptId");
				} catch (Exception e) {
					try {
						currentDeptId = jsonObject.getString("deptId");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					System.out.println("无默认数据");
				}
			}else{
				value = getRequest().getParameter("value");
				currentDeptId = getRequest().getParameter("deptId");
			}
			isSend = getRequest().getParameter("isSend");
			getRequest().setAttribute("isSend", isSend);
			String isHtml = getRequest().getParameter("isHtml");
			getRequest().setAttribute("isHtml", isHtml);
			if(currentDeptId!=null && !currentDeptId.equals("")){
				List<Department> ds = (List<Department>) departmentService.findDepartments(currentDeptId);
				// 此部门下面有子部门
				if(ds != null && ds.size() >0){
					//currentDeptId = currentDeptId;
				}else{
					Department dept = departmentService.findDepartmentById(currentDeptId);
					currentDeptId = dept.getSuperiorGuid();
				}
			}
			
			if(currentDeptId != null && !"".equals(currentDeptId)){
				currentDeptId = currentDeptId.substring(1, currentDeptId.length()-1);
			}
			
			if(value == null ||value.equals("undefined")){
				value = "";
			}
			value = value.replace("{{cs::::value}}", "");
			value = value.replace("{{zsdw::::value}}", "");
			value = value.replace("{{csdw::::value}}", "");
			value = value.replace("{{zs::::value}}", "");
			if(!value.equals("")){
				String[] idAndValue = value.split("[*]");
				if(idAndValue.length == 1){
					//只有手动
					getRequest().setAttribute("depNames", idAndValue[0]);
				}else{
					String ids = idAndValue[0];
					String values = idAndValue[1];
					int size = 0;
					if(ids!=null && ids.length()>0){
						size = ids.split(",").length;
					}
					String[] dep_values = values.split(",");
					String depNames = "";
					for(int i=size; dep_values!=null && i<dep_values.length; i++){
						depNames += dep_values[i]+",";
					}
					if(depNames!=null && depNames.length()>0){
						depNames  = depNames.substring(0,depNames.length()-1);
					}
					getRequest().setAttribute("depNames", depNames);
				}
			}
			
			//已发送列表 --补发部门灰化
			String fprocessid = getRequest().getParameter("fprocessid");
			String valueOfSend = "";
			if(fprocessid != null &&!("").equals(fprocessid)){
				
				List<DoFileReceive> dofieList = tableInfoService.getDeptIdByFprocessId(fprocessid);
				if(dofieList != null && dofieList.size() > 0){
					for (DoFileReceive doFileReceive : dofieList) {
						valueOfSend += doFileReceive.getToDepartId()+",";
					}
					if(!("").equals(valueOfSend) && valueOfSend.length() > 0){
						valueOfSend = valueOfSend.substring(0,valueOfSend.length()-1);
					}
				}
				value = valueOfSend + value;
			}
			getRequest().setAttribute("valueOfSend", valueOfSend);
			
			List<DocXgDepartment> pdept = selectTreeService.getDocXgDept("");
			
			List<GwDepart>  list  = selectTreeService.getAllDeps();

			int depListSize = pdept.size();
			JSONArray arr1 = new JSONArray();
			for(int i=0;i<depListSize;i++){
				JSONObject obj1 = new JSONObject();
				DocXgDepartment dep = pdept.get(i);
				String pdepId = dep.getDeptGuid();
				pdepId = pdepId.replace("{", "");		
				pdepId = pdepId.replace("}", "");
				obj1.put("guid", dep.getId());
				obj1.put("id", pdepId);
				obj1.put("name", dep.getName());
				obj1.put("ind", "");
				
				
				String num = "";
				obj1.put("num", 0);
				obj1.put("checked", false);
				
				if(currentDeptId != null &&!"".equals(currentDeptId)&&(currentDeptId.equals(pdepId)||currentDeptId.equals(dep.getPid()))){
					obj1.put("greek", true);
				}else{
					obj1.put("greek", false);
				}
				
				if("1".equals(dep.getHasSub())){
					
					List<DocXgDepartment> cdepts = selectTreeService.getDocXgDept(dep.getDeptGuid());
					int depChildListSize = cdepts.size();
					JSONArray arr2 = new JSONArray();
					for(int j=0;j<depChildListSize;j++){
						JSONObject obj2 = new JSONObject();
						DocXgDepartment cdep = cdepts.get(j);
						String id = cdep.getDeptGuid();
						if(value.indexOf(id) > -1){
							String[] idAndValue = value.split("[*]");
							String ids = idAndValue[0];
							if(ids!=null && ids.length()>0){
								String[] pid = ids.split(",");
								for(int k=0; k<pid.length; k++){
									if(pid[k].contains("{"+pdepId+"}")){
										num = pid[k].substring(pid[k].lastIndexOf("[")+1, pid[k].lastIndexOf("]"));
									}
								}
							}
							obj2.put("num", StringUtils.isBlank(num)?0:Integer.parseInt(num));
							obj2.put("checked", true);
						}else{
							obj2.put("num", 0);
							obj2.put("checked", false);
						}
						
						id = id.replace("{", "");		
						id = id.replace("}", "");
						obj2.put("guid", cdep.getId());
						obj2.put("id", id);
						obj2.put("name", cdep.getName());
						obj2.put("gzname", "");
						if(currentDeptId != null &&!"".equals(currentDeptId)&&(currentDeptId.equals(id)||currentDeptId.equals(cdep.getPid()))){
							obj2.put("greek", true);
						}else{
							obj2.put("greek", false);
						}
						obj2.put("ind", "");
						if("1".equals(cdep.getHasSub())){
							List<DocXgDepartment> ccdepts = selectTreeService.getDocXgDept(cdep.getDeptGuid());
							int depCChildListSize = ccdepts.size();
							JSONArray arr3 = new JSONArray();
							for(int t=0;t<depCChildListSize;t++){
								DocXgDepartment ccdep = ccdepts.get(t);
								JSONObject obj3 = new JSONObject();
								String cid = ccdep.getDeptGuid();
								if(value.indexOf(cid) > -1){
									String[] idAndValue = value.split("[*]");
									String ids = idAndValue[0];
									if(ids!=null && ids.length()>0){
										String[] pid = ids.split(",");
										for(int k=0; k<pid.length; k++){
											if(pid[k].contains("{"+pdepId+"}")){
												num = pid[k].substring(pid[k].lastIndexOf("[")+1, pid[k].lastIndexOf("]"));
											}
										}
									}
									obj3.put("num", StringUtils.isBlank(num)?0:Integer.parseInt(num));
									obj3.put("checked", true);
								}else{
									obj3.put("num", 0);
									obj3.put("checked", false);
								}
								cid = cid.replace("{", "");		
								cid = cid.replace("}", "");		
								obj3.put("id", cid);
								obj3.put("guid", ccdep.getId());
								//obj3.put("seconddept", "true");
								obj3.put("name", ccdep.getName());
								obj3.put("gzname", "");
					
								if(currentDeptId != null && !"".equals(currentDeptId)&&(currentDeptId.equals(id)||currentDeptId.equals(cdep.getPid()))){
									obj3.put("greek", true);
								}else{
									obj3.put("greek", false);
								}
								obj3.put("ind", "");
								arr3.add(obj3);
							}
							if(depCChildListSize> 0){
								obj2.put("children", arr3);
							}
						}
						arr2.add(obj2);
					}
					obj1.put("children", arr2);
				}
				arr1.add(obj1);
			}
			for(int i=0;i<list.size();i++){
				JSONObject obj1 = new JSONObject();
				GwDepart dep = list.get(i);
				String pdepId = dep.getId();
				String num = "";
				if(value.indexOf(pdepId) > -1){
					String[] idAndValue = value.split("[*]");
					String ids = idAndValue[0];
					if(ids!=null && ids.length()>0){
						String[] pid = ids.split(",");
						for(int j=0; j<pid.length; j++){
							if(pid[j].contains("{"+pdepId+"}")){
								num = pid[j].substring(pid[j].lastIndexOf("[")+1, pid[j].lastIndexOf("]"));
							}
						}
					}
					obj1.put("num", StringUtils.isBlank(num)?0:Integer.parseInt(num));
					obj1.put("checked", true);
				}else{
					obj1.put("num", 0);
					obj1.put("checked", false);
				}
				pdepId = pdepId.replace("{", "");		
				pdepId = pdepId.replace("}", "");
				obj1.put("guid", pdepId);
				obj1.put("id", pdepId);
				obj1.put("name", dep.getName());
				obj1.put("ind", "");
				
				List<GwDepartext> cdepts = selectTreeService.getDepsByPid(dep.getId());
				JSONArray arr2 = new JSONArray();
				for(int j=0; j<cdepts.size(); j++){
					JSONObject obj2 = new JSONObject();
					GwDepartext cdep = cdepts.get(j);
					String id = cdep.getGuid();
					id = id.replace("{", "");		
					id = id.replace("}", "");
					obj2.put("guid",id);
					obj2.put("id",id);
					obj2.put("name",cdep.getName());
					obj2.put("gzname","");
					if(value.indexOf(id) > -1){
						String[] idAndValue = value.split("[*]");
						String ids = idAndValue[0];
						if(ids!=null && ids.length()>0){
							String[] pid = ids.split(",");
							for(int k=0; k<pid.length; k++){
								if(pid[k].contains("{"+pdepId+"}")){
									num = pid[k].substring(pid[k].lastIndexOf("[")+1, pid[k].lastIndexOf("]"));
								}
							}
						}
						obj2.put("num", StringUtils.isBlank(num)?0:Integer.parseInt(num));
						obj2.put("checked", true);
					}else{
						obj2.put("num", 0);
						obj2.put("checked", false);
					}
					obj2.put("ind", "");
						
					List<DocXgDepartment> ccdepts = selectTreeService.getDocXgDept(cdep.getGuid());
					int depCChildListSize = ccdepts.size();
					JSONArray arr3 = new JSONArray();
					for(int t=0;t<depCChildListSize;t++){
						JSONObject obj3 = new JSONObject();
						DocXgDepartment ccdep = ccdepts.get(t);
						String cid = ccdep.getDeptGuid();
						if(value.indexOf(cid) > -1){
							String[] idAndValue = value.split("[*]");
							String ids = idAndValue[0];
							if(ids!=null && ids.length()>0){
								String[] pid = ids.split(",");
								for(int k=0; k<pid.length; k++){
									if(pid[k].contains("{"+pdepId+"}")){
										num = pid[k].substring(pid[k].lastIndexOf("[")+1, pid[k].lastIndexOf("]"));
									}
								}
							}
							obj3.put("num", StringUtils.isBlank(num)?0:Integer.parseInt(num));
							obj3.put("checked", true);
						}else{
							obj3.put("num", 0);
							obj3.put("checked", false);
						}
						cid = cid.replace("{", "");		
						cid = cid.replace("}", "");		
						obj3.put("id", cid);
						obj3.put("guid", pdepId);
						obj3.put("name", ccdep.getName());
						obj3.put("gzname", "");
						obj3.put("ind", "");
						arr3.add(obj3);
					}
					if(depCChildListSize> 0){
						obj2.put("children", arr3);
					}
					arr2.add(obj2);
				}
				obj1.put("children", arr2);
				arr1.add(obj1);
			}
			getRequest().setAttribute("json", arr1.toString());
			System.out.println(arr1.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		endTr = "</tr><tr>";
		return "showDepartment";
	}
	
	public String showReceiveDepts(){
		JSONObject obj = getJSONObject();
		String userid = "";
		if(null != obj){
			userid = obj.get("userid")!=null?obj.getString("userid"):"";
		}else{
			userid = this.getRequest().getParameter("userid");
		}
		
		this.getRequest().setAttribute("userid", userid);
		return "showReceiveDepts";
	}
	
	public void getReceiveDepts() throws UnsupportedEncodingException{
		String mc = this.getRequest().getParameter("mc");
		if(CommonUtil.stringIsNULL(mc)){
			mc = "";
		}else{
			mc =URLDecoder.decode(mc,"utf-8");
		}
		List<DocXgDepartment> depts = selectTreeService.getAllDocXgDepts(mc);
		JSONArray jsonTree = new JSONArray();
		for(DocXgDepartment dept:depts){
			if("1".equals(dept.getHasSub())&&"1".equals(dept.getPid())){
				JSONObject jo = new JSONObject();
				jo.put("id", dept.getDeptGuid());
				jo.put("pId", dept.getPid());
				jo.put("name", dept.getName());
				jo.put("open", true);
				jo.put("type", "folder");
				jo.put("isParent", true);
				jo.put("chkDisabled", false);
				jsonTree.add(jo);
			}else{
				JSONObject jo = new JSONObject();
				jo.put("id", dept.getDeptGuid());
				jo.put("pId", dept.getPid());
				jo.put("name", dept.getName());
				jo.put("type", "file");
				jsonTree.add(jo);
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	public void getCommonDepts() throws UnsupportedEncodingException{
		String userId = this.getRequest().getParameter("userid");
		if(CommonUtil.stringIsNULL(userId)){
			Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
			userId = emp.getEmployeeGuid();
		}
		List<CommonGroup> groupList = ztreeService.findAllDeptGroupByUid(userId);
		JSONArray jsonTree = new JSONArray();
		for(int j = 0; j < groupList.size(); j++){
			CommonGroup group = groupList.get(j);
			List<CommonGroupUsers> userList = ztreeService.findAllCommonGroupUsersByGid(group.getId());
			JSONObject jo = new JSONObject();
			jo.put("id", group.getId());
			jo.put("pId", null);
			jo.put("name", group.getName());
			jo.put("open", false);
			jo.put("type", "folder");
			if(userList.isEmpty()){
				jo.put("chkDisabled", true);
			}else{
				jo.put("chkDisabled", false);
			}
			
			jsonTree.add(jo);
			if(!userList.isEmpty()){
				for (int i = 0; i < userList.size(); i++) {
					JSONObject empJo = new JSONObject();
					empJo.put("id", userList.get(i).getEmpId());
					empJo.put("pId", group.getId());
					empJo.put("name", userList.get(i).getEmpName());
					empJo.put("type", "file");
					jsonTree.add(empJo);
				}
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.println(jsonTree.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	public String setDeptGroup(){
		String userId = this.getRequest().getParameter("userid");
		if(CommonUtil.stringIsNULL(userId)){
			Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
			userId = emp.getEmployeeGuid();
		}
		List<CommonGroup> cgs = ztreeService.findAllDeptGroupByUid(userId);
		this.getRequest().setAttribute("cgs", cgs);
		this.getRequest().setAttribute("userId", userId);
		return "setDeptGroup";
	}
	
	/**
	 * 获取相应的机构信息
	 */
	public void getDepmentInfo(){
		PrintWriter out=null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out!=null) { 
			try{
				String depid =getRequest().getParameter("id");
				List list = selectTreeService.getExchanegDepById(depid);
				Gson gson=new Gson();
				out.write(gson.toJson(list));
			} catch (DataAccessException e) {
				out.write("-1");
				e.printStackTrace();
		} finally{
			out.close();
		}
		}
	}
	/*
	 * 调用接口，插入公文交换部门数据
	 */
	public void insertDocXgDept(){
		
		try {
			String xmlStr = GetAxisInterface.getDept("");
			//将字符串转为XML  返回Document对象
			Document doc = DocumentHelper.parseText(xmlStr);  
			//得到该文件的根节点
			Element root = doc.getRootElement();
			List<Element> depList= root.element("DATA").element("UserArea").element("OrgList").elements("OUInfo");
			int depListSize = depList.size();
			for(int i=0;i<depListSize;i++){
				Element dep = depList.get(i);
				//插库
				selectTreeService.insertDept("1",dep.elementText("OUGuid"),dep.elementText("OUName"),"0",dep.elementText("OrderNumber"),dep.elementText("HasSubWeb"));
				if("1".equals(dep.elementText("HasSubWeb"))){
					String childXml = GetAxisInterface.getDept(dep.elementText("OUGuid"));
					Document childDep = DocumentHelper.parseText(childXml);  
					Element childRoot = childDep.getRootElement();
					List<Element> depChildList= childRoot.element("DATA").element("UserArea").element("OrgList").elements("OUInfo");
					int depChildListSize = depChildList.size();
					for(int j=0;j<depChildListSize;j++){
						Element cdep = depChildList.get(j);
						selectTreeService.insertDept(dep.elementText("OUGuid"),cdep.elementText("OUGuid"),cdep.elementText("OUName"),"1",cdep.elementText("OrderNumber"),dep.elementText("HasSubWeb"));
					}
				}
			}
			//结束 
			System.out.println("insertDocXgDept:"+"插入部门数据完成！！！！");
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 选择发文单位统计时
	 * 
	 * @return
	 */
	public String showDepartment4Statistics() {
		try {
			List<String> depIds = (List<String>)getSession().getAttribute(Constant.DEPARMENT_IDS);
			String userKey1 = GenUserKey.genUserKey(depIds.get(0));
			String json = NTSDep2Json.getJson((docExchangeClient.getAllDocDepartments(userKey1)));
			getRequest().setAttribute("json", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		endTr = "</tr><tr>";
		return "showDepartment4Statistics";
	}
	
	/**
	 * 
	 * 用于公文流程选择固定人员
	 * @return
	 */
	public String showPopForStep() { 
		try {
			// 转向选择人员页面
			List departmentIds = (List) getSession().getAttribute(Constant.DEPARMENT_IDS);
			List<Department> deps =  this.getDepsByDepIds(departmentIds);
			deps = DepSortUtil.sortDepartment(deps);
			String treedata = getTreeDataForStep(this.getDepIdsByDeps(deps));
			getRequest().setAttribute("treedata", treedata);
			return "selectEmployeeForStep";
		} catch (Exception e) {
			logger.error("拼JSON字符串时出错！");
			logger.error(e);
			return "error";
		}
	}
	
	/**
	 * 
	 * 用于公文流程选择固定人员
	 * @return
	 */
	public String showPopForAttention() { 
		try {
			// 转向选择人员页面
			List departmentIds = (List) getSession().getAttribute(Constant.DEPARMENT_IDS);
			List<Department> deps =  this.getDepsByDepIds(departmentIds);
			deps = DepSortUtil.sortDepartment(deps);
			String treedata = getTreeDataForStep(this.getDepIdsByDeps(deps));
			getRequest().setAttribute("treedata", treedata);
			return "selectEmployeeForAttention";
		} catch (Exception e) {
			logger.error("拼JSON字符串时出错!");
			logger.error(e);
			return "error";
		}
	}
	/**
	 * 
	 * 用于公文流程选择固定人员
	 * @return
	 */
	public String showEmployeeForSend() { 
		String empDataUrl = SystemParamConfigUtil.getParamValueByParam("empDataUrl");
		getRequest().setAttribute("empDataUrl", empDataUrl);
		return "selectEmployeeForSend";
	}
	/**
	 * 描述：根据depId、checkstate找出下一层节点数据
	 * 
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getChildData() {
		String depId = getRequest().getParameter("value");
		String checkstate = getRequest().getParameter("checkstate");
		try {
			// 转码
			depId = URLDecoder.decode(depId, "UTF-8");
			// 过滤掉部门的标识“d”
			depId = depId.substring(0, depId.length() - 1);
			logger.debug("异步请求的depId为：" + depId);
			if (depId != null && depId.trim().length() > 0) {
				String data = "[" + this.getDataByDepId(depId, checkstate) + "]";
				getResponse().getWriter().print(data);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 把depIds转换为deps
	 * 
	 * @param depIds
	 * @return
	 */
	private List<Department> getDepsByDepIds(List<String> depIds) {
		List<Department> deps = new ArrayList<Department>();
		for (int i = 0, l = depIds.size(); i < l; i++) {
			deps.add(selectTreeService.findDepartmentById(depIds.get(i)));
		}
		return deps;
	}
	
	/**
	 * 把deps转换为depIds
	 * 
	 * @param deps
	 * @return
	 */
	private List<String> getDepIdsByDeps(List<Department> deps){
		List<String> depIds = new ArrayList<String>();
		for(int i=0,l=deps.size();i<l;i++){
			depIds.add(deps.get(i).getDepartmentGuid());
		}
		return depIds;
	}
	
	private String getTreeDataForStep(List<String> departmentIds) {
		StringBuilder data = new StringBuilder();
		data.append("[");
		//#### 部门树 ####
		for (int i = 0, l = departmentIds.size(); i < l; i++) {
			String depId = departmentIds.get(i);
			Department dep = selectTreeService.findDepartmentById(depId);
			data.append(getNodeWithChild(dep, true));
			if (i != l - 1) {
				data.append(",");
			}
		}
		data.append("]");
		return data.toString();
	}
	
	/**
	 * 
	 * 描述：人员树展示
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-2-23 上午11:14:08
	 */
	public String showEmpTree(){
		String empDataUrl = SystemParamConfigUtil.getParamValueByParam("empDataUrl");
		getRequest().setAttribute("empDataUrl", empDataUrl);
		return "showEmpTree";
	}

	/**
	 * 
	 * 描述：部门树展示
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-2-23 上午11:14:08
	 */
	public String showDeptTree(){
		String deptDataUrl = SystemParamConfigUtil.getParamValueByParam("deptDataUrl");
		getRequest().setAttribute("deptDataUrl", deptDataUrl);
		return "showDeptTree";
	}
	
	/**
	 * 得到带字节点的节点数据
	 * 
	 * @param dep
	 * @param isExpand
	 * @return
	 */
	private String getNodeWithChild(Department dep, boolean isExpand) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"id\":\"");// 此id,用于级联操作,保证唯一
		sb.append("root");
		sb.append("\",");
		sb.append("\"value\":\"");
		sb.append(dep.getDepartmentGuid());
		sb.append("d");// 区分一下是depId
		sb.append("\",");
		sb.append("\"text\":\"");
		sb.append(dep.getDepartmentName()); // 节点的名称
		sb.append("\",");
		sb.append("\"showcheck\":true,");// 是否显示选择框
		sb.append("\"isexpand\":");// 是否是展开状态
		sb.append(isExpand);
		sb.append(",\"checkstate\":0,");// 选择框的状态，0表示未被选中
		sb.append("\"hasChildren\":true,");// 是否有子节点
		sb.append("\"ChildNodes\":[");// 子节点加载开始
		sb.append(getDataByDepId(dep.getDepartmentGuid(), "0"));// "0"表示未选中状态
		sb.append("],");// 子节点加载结束
		sb.append("\"complete\":true");
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 根据depId得到此部门下的节点信息（只得到一层）
	 * 
	 * @param depId
	 * @throws IOException
	 */
	private String getDataByDepId(String depId, String checkstate) {
		Map<String, Object> map = selectTreeService.getInfosOneFloor(depId);
		List<Department> deps = (List<Department>) map.get(Constant.DEPS);
		List<Employee> emps = (List<Employee>) map.get(Constant.EMPS);
		return this.getNodeWithoutChild(selectTreeService.findDepartmentById(depId),deps, emps, null, checkstate);
	}
	
	/**
	 * 得到子节点的数据
	 * 
	 * @param dep
	 * @param departments
	 * @param employees
	 * @param oldList
	 * @param checkstate
	 * @return
	 */
	public String getNodeWithoutChild(Department dep,List<Department> departments, List<Employee> employees,List oldList, String checkstate) {
		StringBuilder sb = new StringBuilder();
		boolean flag = false;
		for (int i = 0; i < departments.size(); i++) {
			Department department = (Department) departments.get(i);
			if (dep.getDepartmentGuid().equals(department.getSuperiorGuid())) {
				// 过滤掉“南通市”这个根节点
				if (department.getDepartmentGuid().equals(department.getSuperiorGuid())|| department.getSuperiorGuid() == null) {
					continue;
				}
				sb.append("{");
				sb.append("\"id\":\"");// 此id,用于级联操作,保证唯一
				sb.append(UuidGenerator.generate36UUID());
				sb.append("\",");
				sb.append("\"value\":\"");
				sb.append(department.getDepartmentGuid());
				sb.append("d");// 区分一下是depId
				sb.append("\",");
				sb.append("\"text\":\"");
				sb.append(department.getDepartmentName()); // 节点的名称
				sb.append("\",");
				sb.append("\"showcheck\":true,");// 是否显示选择框
				sb.append("\"isexpand\":false,");// 是否是展开状态
				sb.append("\"checkstate\":");// 选择框的状态，0表示未被选中
				sb.append(checkstate);
				sb.append(",");
				sb.append("\"hasChildren\":true,");// 是否有子节点
				sb.append("\"ChildNodes\":null,");// 子节点加载开始
				sb.append("\"complete\":false");
				sb.append("}");
				if (i != departments.size() - 1) {
					sb.append(",");
				}
				if (!flag) {
					flag = true;
				}
			}
		}
		for (int i = 0; i < employees.size(); i++) {
			Employee employee = (Employee) employees.get(i);
			if (dep.getDepartmentGuid().equals(employee.getDepartmentGuid())) {
				if (flag) {
					if (sb != null && sb.length() > 0
							&& !sb.toString().endsWith(",")) {
						sb.append(",");
					}
					flag = false;
				}
				String eGuid = employee.getEmployeeGuid();
				sb.append("{");
				sb.append("\"id\":\"");// 此id,用于级联操作,保证唯一
				sb.append(UuidGenerator.generate36UUID());
				sb.append("\",");
				sb.append("\"value\":\"");
				sb.append(eGuid); // 节点的id
				sb.append("\",");
				sb.append("\"text\":\"");
				sb.append(employee.getEmployeeName()); // 节点的名称
				sb.append("\",");
				sb.append("\"showcheck\":true,");// 是否显示选择框
				sb.append("\"isexpand\":false,");// 是否是展开状态
				if (oldList != null && oldList.size() > 0) {
					int checkState = 0;
					for (int j = 0, l = oldList.size(); j < l; j++) {
						if (eGuid.equals(oldList.get(j))) {
							checkState = 1;
							break;
						}
					}
					sb.append("\"checkstate\":");
					sb.append(checkState);
					sb.append(",");
				} else {
					sb.append("\"checkstate\":");// 选择框的状态，0表示未被选中,1表示选中，2表示半选
					sb.append(checkstate);
					sb.append(",");
				}
				sb.append("\"hasChildren\":false,");// 是否有子节点
				sb.append("\"ChildNodes\":null,");// 子节点为空
				sb.append("\"complete\":true");
				sb.append("}");
				sb.append(",");
			}
		}
		if (sb != null && sb.length() > 0 && sb.toString().endsWith(",")) {
			return sb.substring(0, sb.length() - 1);
		} else {
			return sb.toString();
		}
	}
	
	public String showGroup(){
		String userId = (String)getSession().getAttribute(Constant.SESSION_USER_ID);
		List<Depgroup> groups = selectTreeService.getDepGroupByUserId(userId);
		getRequest().setAttribute("groups", groups);
		return "showGroup";
	}
	
	public String showEmpGroup(){
		String userId = (String)getSession().getAttribute(Constant.SESSION_USER_ID);
		List<EmpGroup> groups = selectTreeService.queryEmpGroupsByUserId(userId);
		getRequest().setAttribute("groups", groups);
		return "showEmpGroup";
	}
	
	public void saveEmpGroup(){
		String userId = (String)getSession().getAttribute(Constant.SESSION_USER_ID);
		String userInfosStr = getRequest().getParameter("userInfos")==null ? "" : getRequest().getParameter("userInfos");
		String groupName = getRequest().getParameter("groupName")==null ? "" : getRequest().getParameter("groupName");
		
		PrintWriter pw = null;
		try {
			if(userId!=null && !"".equals(userId)){
				pw = getResponse().getWriter();
				EmpGroup e = new EmpGroup();
				e.setUserId(userId);
				e.setEmp(userInfosStr);
				e.setName(groupName);
				selectTreeService.insertEmpGroup(e);
				pw.write("SUCCESS");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if(pw!=null){
				pw.close();
			}
		}
	}
	
	/**
	 * @throws IOException
	 */
	public void deleteEmpGroup() throws IOException{
		String id = getRequest().getParameter("id");
		PrintWriter pw = null;
		try {
			pw = getResponse().getWriter();
			selectTreeService.removeEmpGroupByUserId(id);
			pw.print("SUCCESS");
		}catch (IOException e){
			throw new IOException();
		} catch (Exception e) {
			logger.error("删除失败");
			pw.print(Constant.FAIL);
		} finally {
			if(pw!=null){
				pw.close();
			}
		}
	}
	
	public void deleteGroup() throws IOException{
		String id = getRequest().getParameter("id");
		PrintWriter pw = null;
		try {
			pw = getResponse().getWriter();
			if(selectTreeService.deleteGroupById(id)){
				pw.print(Constant.SUCCESS);
			}else{
				pw.print(Constant.FAIL);
			}
		}catch (IOException e){
			throw new IOException();
		} catch (Exception e) {
			logger.error("删除失败");
			pw.print(Constant.FAIL);
		} finally {
			if(pw!=null){
				pw.close();
			}
		}
	}
	
	public String toSaveGroup(){
		return "saveGroup";
	}

	public String toSaveEmpGroup(){
		return "saveEmpGroup";
	}
	
	public void saveGroup() throws IOException{
		String name = getRequest().getParameter("name");
		String dep = getRequest().getParameter("dep");
		String userId = (String)getSession().getAttribute(Constant.SESSION_USER_ID);
		
		PrintWriter pw = null;
		try {
			pw = getResponse().getWriter();
			selectTreeService.saveGroup(name,dep,userId);
			pw.print(Constant.SUCCESS);
		}catch (IOException e){
			throw new IOException();
		} catch (Exception e) {
			logger.error("增加失败");
			pw.print(Constant.FAIL);
		} finally {
			if(pw!=null){
				pw.close();
			}
		}
		
	}
	
	
	/**
	 * 描述：异步根据id 查询 EmployeeList<br>
	 * void
	 * 作者:吕建<br>
	 * 创建时间:2012-1-18 上午11:10:51
	 * @throws IOException 
	 */
	public void choose() throws IOException{
		String selected = getRequest().getParameter("selected");
		PrintWriter out = getResponse().getWriter();
		getSession().removeAttribute("initIdList");
		StringBuffer sb = new StringBuffer();
		List initIdList;
		String names = ""; 
		
		if(selected != null) {
			String[] nowemployeeid = selected.split(",");
			List nowemployeeids = new ArrayList();
			for (String a : nowemployeeid) {
				if (a.trim().length() == 0){
					continue;
				}
				Employee employee = selectTreeService.queryEmployeeByEmployeeGuid(a);//查询ID对应Employee
				if(employee!=null){
					if(employee.getEmployeeName()!=null && !employee.getEmployeeName().equals("")) {
						sb.append(a).append(",").append(employee.getEmployeeName()).append(";");  //拼接超管中文名字符串
					}
				}
				nowemployeeids.add(a);
			}
			initIdList = nowemployeeids;
		} else {
			initIdList = null;
		}
		if(sb.indexOf(",") > -1){   //去除末尾的逗号
			names = sb.substring(0, sb.length()-1);
		}
		getSession().setAttribute("initIdList", initIdList);
		out.write(names);//向页面输出超管中文名字符串
		out.close();
	
	}
	
	public String  chooseRebacker() throws UnsupportedEncodingException{
//		this.getResponse().setCharacterEncoding("UTF-8");
		// id , name
		String id = getRequest().getParameter("id");
//		String name = new String(getRequest().getParameter("name").getBytes(),"utf-8");
		String name = getRequest().getParameter("name");
		
		String[] ids = id.split(",");
		String[] names = name.split(",");
		
	
		List<String[]> list = new ArrayList<String[]>();
		for(int i = 0 ; i < ids.length ; i++){
			list.add(new String[]{ids[i],names[i]});
		}
		getSession().setAttribute("list", list);
		getSession().setAttribute("size", list.size());
	//	getSession().setAttribute("names", names);
		return "chooseRebacker";
	}
}
