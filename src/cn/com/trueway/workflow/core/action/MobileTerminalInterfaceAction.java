package cn.com.trueway.workflow.core.action;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.XML;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.MyUtils;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.base.util.XMLStreamUtil;
import cn.com.trueway.document.Step;
import cn.com.trueway.document.business.service.SelectTreeService;
import cn.com.trueway.license.LicenseFactory;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.BasicFlow;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.JsonForXML;
import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.core.pojo.WfCustomStatus;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfFormPermit;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfLine;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfMainJson;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.pojo.WfXml;
import cn.com.trueway.workflow.core.pojo.WfforJson;
import cn.com.trueway.workflow.core.pojo.vo.RolePojo;
import cn.com.trueway.workflow.core.service.CustomStatusService;
import cn.com.trueway.workflow.core.service.DictionaryService;
import cn.com.trueway.workflow.core.service.FieldInfoService;
import cn.com.trueway.workflow.core.service.FormPermitService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.PendingService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.TemplateService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.core.service.WorkflowCalendarService;
import cn.com.trueway.workflow.set.pojo.EmployeeRole;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.pojo.InnerUserMapEmployee;
import cn.com.trueway.workflow.set.pojo.WfMainRole;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.EmployeeRoleService;
import cn.com.trueway.workflow.set.service.GroupService;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
import cn.com.trueway.workflow.set.util.ZipUtil;

/**
 * 描述：提供两种类型接口xml json
 * 作者：hux<br>
 * 创建时间：2013年3月18日17:42:39<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class MobileTerminalInterfaceAction extends BaseAction{
	
	private static String filePath = "d://";
	
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private WorkflowBasicFlowService 	workflowBasicFlowService;
	
	private TableInfoService 			tableInfoService;
	
	private WorkflowCalendarService 	workflowCalendarService;
	
	private ZwkjFormService 			zwkjFormService;
	
	private SelectTreeService 			selectTreeService;
	
	private TemplateService 			templateService;
	
	private DictionaryService 			dictionaryService;
	
	private CustomStatusService 		customStatusService;
	
	private GroupService 				groupService;
	
	private FieldInfoService 			fieldInfoService;
	
	private PendingService 				pendingService;
	
	private EmployeeRoleService 		employeeRoleService;
	
	private FormPermitService 			formPermitService;
	
	private DepartmentService			departmentService;
	
	private ItemService itemService;
	
	private String typeId;
	
	private String webType;
	
	private String wfName;

	private Step step;
	
	private String defineId;
	
	private File file;
	private String fileFileName;
	
	private WfItem item;
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public CustomStatusService getCustomStatusService() {
		return customStatusService;
	}

	public void setCustomStatusService(CustomStatusService customStatusService) {
		this.customStatusService = customStatusService;
	}

	public PendingService getPendingService() {
		return pendingService;
	}

	public void setPendingService(PendingService pendingService) {
		this.pendingService = pendingService;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}

	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}

	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public SelectTreeService getSelectTreeService() {
		return selectTreeService;
	}

	public void setSelectTreeService(SelectTreeService selectTreeService) {
		this.selectTreeService = selectTreeService;
	}
	
	

//	public WorkflowDefineService getWorkflowDefineService() {
//		return workflowDefineService;
//	}
//
//	public void setWorkflowDefineService(WorkflowDefineService workflowDefineService) {
//		this.workflowDefineService = workflowDefineService;
//	}

	public WorkflowCalendarService getWorkflowCalendarService() {
		return workflowCalendarService;
	}

	public void setWorkflowCalendarService(
			WorkflowCalendarService workflowCalendarService) {
		this.workflowCalendarService = workflowCalendarService;
	}

	public FormPermitService getFormPermitService() {
		return formPermitService;
	}

	public void setFormPermitService(FormPermitService formPermitService) {
		this.formPermitService = formPermitService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	public String getWebType() {
		return webType;
	}

	public void setWebType(String webType) {
		this.webType = webType;
	}

	public String getWfName() {
		return wfName;
	}

	public void setWfName(String wfName) {
		this.wfName = wfName;
	}
	
	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}
	
	public String getDefineId() {
		return defineId;
	}

	public void setDefineId(String defineId) {
		this.defineId = defineId;
	}
	
//	public WorkflowFormService getWorkflowFormService() {
//		return workflowFormService;
//	}
//
//	public void setWorkflowFormService(WorkflowFormService workflowFormService) {
//		this.workflowFormService = workflowFormService;
//	}
	
	public FieldInfoService getFieldInfoService() {
		return fieldInfoService;
	}

	public void setFieldInfoService(FieldInfoService fieldInfoService) {
		this.fieldInfoService = fieldInfoService;
	}
	public EmployeeRoleService getEmployeeRoleService() {
		return employeeRoleService;
	}

	public void setEmployeeRoleService(EmployeeRoleService employeeRoleService) {
		this.employeeRoleService = employeeRoleService;
	}
	
	public WfItem getWfItem() {
		return item;
	}

	public void setWfItem(WfItem item) {
		this.item = item;
	}

	public String add(){
		return "addWF";
	}
	public void addwf(){
		BasicFlow bf = new BasicFlow();
		bf.setWorkFlowName(getRequest().getParameter("wfName"));
		int result = LicenseFactory.getLicense().resolveLicense(Constant.licensePath, Constant.productVersion, Constant.productName);
		if(result==1){
			workflowBasicFlowService.save(bf);
		}else{
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.write(result+"");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	public String updatewf(){
		BasicFlow bf = new BasicFlow();
		bf = workflowBasicFlowService.get(getRequest().getParameter("id"));
		bf.setWorkFlowName(getRequest().getParameter("wfName"));
		workflowBasicFlowService.update(bf);
		return listWF();
	}
	
	public String getWf(){
		String id = getRequest().getParameter("id");
		BasicFlow bf = workflowBasicFlowService.get(id);
		getRequest().setAttribute("workflow", JSONObject.fromObject(bf));
		return "makeImg";
	}
//	public void openWF(){
//		String id = getRequest().getParameter("id");
//		WfMain bf = workflowBasicFlowService.get(id);
//		if(bf!=null){
//			getSession().setAttribute("workflow", bf);
//		}
//	}
	
	public void getWFID(){
//		List<BasicFlow> list = workflowBasicFlowService.getBasicFlowList(null);
//		WfforJson json = null;
//		JSONArray jarray = new JSONArray();
//		json = new WfforJson();
//		for(BasicFlow bf:list){
			 //json = new WfforJson();
//			 json.setId(bf.getUuid());
//			 json.setName(bf.getWorkFlowName());
//			 json.setId("1234567890");
//			 json.setName("工作流测试");
//			 jarray.add(json);
//		}
		WfforJson json = null;
		JSONArray jarray = new JSONArray();
		json = new WfforJson();
		json.setId(UuidGenerator.generate36UUID());
		json.setName("工作流测试");
		WfforJson json2=new WfforJson();
		json2.setId(UuidGenerator.generate36UUID());
		json2.setName("我的工作流");
		jarray.add(json);
		jarray.add(json2);
		PrintWriter out = null;
			try {
				getResponse().addHeader("Access-Control-Allow-Origin", "*");
				out = getResponse().getWriter();
				out.write(jarray.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.flush();
			out.close();	
	}

	/**
	 *  获取节点、路由组成图像界面
	 * @return
	 * @throws IOException 
	 */
	public String getWfDefineForImaging() throws IOException{
		String in = this.getRequest().getParameter("id");
		BufferedReader buffRed=null;
		String filepath = filePath +in+".xml" ;
		 buffRed=new BufferedReader(new FileReader(filepath));
		 getResponse().addHeader("Access-Control-Allow-Origin", "*");
		 String tempStr;
	        StringBuffer xmlStrBuff=new StringBuffer();
	       while((tempStr=buffRed.readLine())!=null)
	            xmlStrBuff.append(tempStr);
	       buffRed.close();
	       PrintWriter out = null;
			try {
				getResponse().addHeader("Access-Control-Allow-Origin", "*");
				out = getResponse().getWriter();
				JsonForXML jfx = new JsonForXML();
				jfx.setResult(xmlStrBuff.toString());
				JSONObject j = JSONObject.fromObject(jfx);
				out.write(j.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.flush();
			out.close();
		return null;
	}
	
	/**
	 * 根据id删除流程
	 * @return
	 */
	public String deleteWF(){
		String id = getRequest().getParameter("id");
		//System.out.println(id);
		workflowBasicFlowService.deleteWfMain(id);
		String path = filePath+id+".xml";
		File file = new File(path);
		file.delete();
		return listWF();
//		workflowBasicFlowService.delete(id);
//		workflowBasicFlowService.deleteFromWFIDForNode(id);
//		workflowBasicFlowService.deleteFromWFIDForSoftRoute(id);	
	}
	
	

	/**
	 * 解析xml模板文件流信息
	 */
	public void getReadXMLResourceFile(){
		try {
			InputStream in = this.getRequest().getInputStream();
			String str = XMLStreamUtil.XMLToJsonForInputStream(in);
			//System.out.println(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 发送xml模板文件信息
	 */
	public void sendXMLResourceFile(){
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			boolean b = XMLStreamUtil.jsonToXMLForSend("{\"key\":\"value\"}", out);
			//System.out.println(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理流
	 * @param is
	 * @return
	 * @throws Exception
	 */
	private static byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;  
	}
	/**
	 * 转换jsonobject
	 * @return
	 */
	private JSONObject getJSONObject(){
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			return JSONObject.fromObject(new String (data,"UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(iStream!=null){
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
	 * 展现节点action
	 * @throws Exception 
	 */
	public void test_showNode() throws Exception{
		//获取节点id,如果为空则说明搜寻的是开始节点
		String id=getRequest().getParameter("id");
		String node_id=getRequest().getParameter("node_id");
		List<WfNode> list=new ArrayList<WfNode>();
		list=workflowBasicFlowService.showNode(id, node_id,"");
	}
	

	/**
	 * 打开工作流
	 */
	public void workFlowOpen(){
		System.out.println("打开流程...");
		String id=getRequest().getParameter("id");
		if(null!=id||!id.equals("")){
		WfMain main=workflowBasicFlowService.getWfMainById(id);
		//System.out.println(main.getWfm_name()+":"+main.getWfm_id());
		}else{
			//id为空
		}
	}
	/**
	 * 根据流程id删除流程
	 * @throws Exception 
	 */
	public void deleteWorkFlow() throws Exception{
		System.out.println("删除流程...");
		String id=getRequest().getParameter("id");
		System.out.println(id);
		String [] ids=id.split(",");
		List<String> can_del=new ArrayList<String>();
		List<String> cannot_del=new ArrayList<String>();	//存不能删除的流程名称
		//传入id 判断是否能删除，0为能删除，其余为不能删除
		for(int i=0;i<ids.length;i++){
			if(workflowBasicFlowService.workFlowIsUserOrNot(ids[i])==0){
				can_del.add(ids[i]);
			}else{
				WfMain wfMain = workflowBasicFlowService.getWfMainById(ids[i]);
				cannot_del.add(wfMain.getWfm_name());	
			}
		}
		if(cannot_del.size()==0){
			for(int i=0;i<can_del.size();i++){
				workflowBasicFlowService.deleteWorkFlow(can_del.get(i));
				workflowBasicFlowService.deleteWfXml(can_del.get(i));
			}
		}
		
		if(cannot_del.size()>0){
			PrintWriter out=getResponse().getWriter();
			String msg = "流程：";
			for(String name : cannot_del){
				msg += "["+name+"]";
			}
			msg += "中含有实例，无法删除!";
			out.print(msg);
			out.flush();
			out.close();
		}else{
			PrintWriter out=getResponse().getWriter();
			out.print("删除成功！");
			out.flush();
			out.close();
		}
	}
	/**
	 * 新建流程
	 */
	public String addWorkFlow(){
		System.out.println("新建流程...");
		String wf_name=getRequest().getParameter("wf_name");
		if(null==wf_name||wf_name.equals("")){
			//取出现有的流程名，防止重名
			List<WfMain> list=workflowBasicFlowService.getWfMainList();
			List<String> list_return=new ArrayList<String>();
			for(int i=0;i<list.size();i++){
				list_return.add(list.get(i).getWfm_name());
			}
			getRequest().setAttribute("wf_name_list", JSONArray.fromObject(list_return));
			//System.out.println(JSONArray.fromObject(list_return));
			return "add";
		}else{
			String id=UuidGenerator.generate36UUID();
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			WfMain main=new WfMain();
			main.setWfm_id(id);
			main.setWfm_name(wf_name);
			main.setWfm_createtime(dateFormat.format(new Date()));
			main.setWfm_modifytime(dateFormat.format(new Date()));
			main.setWfm_status("0");
			workflowBasicFlowService.saveWorkFlow(main);
			return null;
		}
	}
	
	/**
	 * 
	 * 描述：保存流程
	 * 作者:蔡亚军
	 * 创建时间:2016-8-24 下午1:10:00
	 */
	public void saveWorkFlow(){
		String depRootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		String employeDn = emp.getEmployeeDn();
		String [] employeDns = employeDn.split(",");
		Collection<Department> list = departmentService.findThirdDepartments(depRootId);
		for (Department department : list) {
			String depHierarchy =department.getDepartmentHierarchy();
			String [] depHierarchys = depHierarchy.split(",");
			if(depHierarchys.length>2 && employeDns[employeDns.length-3].equals(depHierarchys[depHierarchys.length-3])){
				depRootId = department.getDepartmentGuid();
				break;
			}
		}
		String wf_name=getRequest().getParameter("wf_name");
		String id=UuidGenerator.generate36UUID();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		WfMain main=new WfMain();
		main.setWfm_id(id);
		main.setWfm_name(wf_name);
		main.setWfm_createtime(dateFormat.format(new Date()));
		main.setWfm_modifytime(dateFormat.format(new Date()));
		main.setWfm_status("0");
		main.setWfm_deptId(depRootId);
		int result = LicenseFactory.getLicense().resolveLicense(Constant.licensePath, Constant.productVersion, Constant.productName);
		result = 1;
		if(result==1){
			workflowBasicFlowService.saveWorkFlow(main);
		}else{
		}
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.write(result+"");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	
	
	
	/**
	 *修改工作流 
	 * @throws UnsupportedEncodingException 
	 */
	public String modifyWorkFlow() throws UnsupportedEncodingException{
		String depRootId = SystemParamConfigUtil.getParamValueByParam("root_department_id");
		Employee emp = (Employee)getSession().getAttribute(MyConstants.loginEmployee);
		String employeDn = emp.getEmployeeDn();
		String [] employeDns = employeDn.split(",");
		Collection<Department> departs = departmentService.findThirdDepartments(depRootId);
		for (Department department : departs) {
			String depHierarchy =department.getDepartmentHierarchy();
			String [] depHierarchys = depHierarchy.split(",");
			if(depHierarchys.length>2 && employeDns[employeDns.length-3].equals(depHierarchys[depHierarchys.length-3])){
				depRootId = department.getDepartmentGuid();
				break;
			}
		}
		System.out.println("修改工作流...");
		String wf_name=getRequest().getParameter("wf_name");
		String id=getRequest().getParameter("id");
		if(null==wf_name||wf_name.equals("")){
			getRequest().setAttribute("id", id);
			//取出现有的流程名，防止重名
			List<WfMain> list=workflowBasicFlowService.getWfMainList();
			List<String> list_return=new ArrayList<String>();
			for(int i=0;i<list.size();i++){
				if(id.equals(list.get(i).getWfm_id())){
					
				}else{
					list_return.add(list.get(i).getWfm_name());
				}
			}
			getRequest().setAttribute("wf_name_list", JSONArray.fromObject(list_return));
			getRequest().setAttribute("workflowname", URLDecoder.decode(getRequest().getParameter("workflowname"),"UTF-8"));
			return "modify";
		}else{
			//更新wfmain
			WfMain wfMain=workflowBasicFlowService.getWfMainById(id);
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			wfMain.setWfm_name(wf_name);
			wfMain.setWfm_modifytime(dateFormat.format(new Date()));
			wfMain.setWfm_deptId(depRootId);
			workflowBasicFlowService.updateWfMain(wfMain);
			return null;
		}
	}
	/**
	 * 传入Id并且打开流程图
	 */
	public String wrokFlowImgOpen(){
		//System.out.println("打开流程图...");
		String id = this.getRequest().getParameter("id");
		if(CommonUtil.stringIsNULL(id)){
			id=(String) getSession().getAttribute(MyConstants.workflow_session_id);
		}
		getRequest().setAttribute("workflowId", id);
		WfXml wfXml=workflowBasicFlowService.getWfXml(id);
		if(null==wfXml){
			//没有流程图，则打开新流程图
			getRequest().setAttribute("xml", "");
		}else{
			//有流程图，取出xml，返还给workflow_img
			String xml = wfXml.getWfx_xml().replaceAll("\n", "");
			getRequest().setAttribute("xml", xml);
		}
		
		WfMain wfMain=workflowBasicFlowService.getWfMainById(id);
		
		getRequest().setAttribute("flow_id", wfMain.getWfm_id());
		getRequest().setAttribute("flow_create_time", wfMain.getWfm_createtime());
		getRequest().setAttribute("flow_modified_time", wfMain.getWfm_modifytime());
		getRequest().setAttribute("flow_name", wfMain.getWfm_name());
		getRequest().setAttribute("flow_status", wfMain.getWfm_status());
		
		List<WfTableInfo> title_table_list = tableInfoService.getTableByLcid(wfMain.getWfm_id());
		List<List> title_column_list = new ArrayList<List>();
		for(WfTableInfo info : title_table_list){
			List<WfFieldInfo> column_list = tableInfoService.getFieldByTableid(info.getId());
			title_column_list.add(column_list);
		}
		getRequest().setAttribute("flow_initiate_titleNames", wfMain.getWfm_title_name());
		getRequest().setAttribute("flow_initiate_titleIds", wfMain.getWfm_title_column());
		
		getRequest().setAttribute("flow_title_table", wfMain.getWfm_title_table());
		getRequest().setAttribute("flow_title_column", wfMain.getWfm_title_column());
		getRequest().setAttribute("title_table_list", JSONArray.fromObject(title_table_list));
		getRequest().setAttribute("title_column_list", JSONArray.fromObject(title_column_list));
		getRequest().setAttribute("calendar_list", JSONArray.fromObject(pendingService.getAllYear()));
		List<Map<String, String>> empList = dictionaryService.getKeyAndValue("empType");
		getRequest().setAttribute("emp_list",  JSONArray.fromObject(empList));
		List<ZwkjForm> zwkjFormList = zwkjFormService.getFormListByParamForPage("workflowId",id , null, null);
		for(ZwkjForm zwkjForm:zwkjFormList){
			zwkjForm.setElementLocationJson("");
			zwkjForm.setFormPageJson("");
			zwkjForm.setData(null);
		}
		getRequest().setAttribute("form_list",  JSONArray.fromObject(zwkjFormList));
		//getRequest().setAttribute("flow_procedure_list", JSONArray.fromObject(workflowBasicFlowService.getAllProcedures()));
		getRequest().setAttribute("flow_procedure_list", JSONArray.fromObject(zwkjFormService.getAllProcedureList()));
		
		List<WfCustomStatus> list=customStatusService.getCustomStatusByLcid("");
		List<WfCustomStatus> list_return=new ArrayList<WfCustomStatus>();
		for(WfCustomStatus wfObject:list){
			String wfd_id=wfObject.getId();
			String wfd_key=wfObject.getVc_key();
			String wfd_value=wfObject.getVc_value();
			String[] keys=wfd_key.split(",");
			String[] values=wfd_value.split(",");
			for(int i=0;i<keys.length;i++){
				WfCustomStatus customStatus=new WfCustomStatus();
				customStatus.setVc_key(wfd_id+"_"+keys[i]);
				customStatus.setVc_value(values[i]);
				list_return.add(customStatus);
			}
		}
		getRequest().setAttribute("global_list", JSONArray.fromObject(list_return));
		list=customStatusService.getCustomStatusByLcid(id);
		list_return=new ArrayList<WfCustomStatus>();
		for(WfCustomStatus wfObject:list){
			String wfd_id=wfObject.getId();
			String wfd_key=wfObject.getVc_key();
			String wfd_value=wfObject.getVc_value();
			String[] keys=wfd_key.split(",");
			String[] values=wfd_value.split(",");
			for(int i=0;i<keys.length;i++){
				WfCustomStatus customStatus=new WfCustomStatus();
				customStatus.setVc_key(wfd_id+"_"+keys[i]);
				customStatus.setVc_value(values[i]);
				list_return.add(customStatus);
			}
		}
		getRequest().setAttribute("current_list", JSONArray.fromObject(list_return));
		getRequest().setAttribute("template_list", JSONArray.fromObject(templateService.getTemplateByLcid(id)));
		InnerUser innerUser=new InnerUser();
		innerUser.setWorkflowId(id);
		innerUser.setType(4);
		List<InnerUser> list1 = groupService.getInnerUserListForPage(null,null,innerUser,null,null);
		for (InnerUser iu : list1) {
			iu.setEmpJsonTree("");
		}
		getRequest().setAttribute("role_list", JSONArray.fromObject(list1));
		getRequest().setAttribute("wfUId", id);
		return "workflowimg_open";
	}
	
	/**
	 * 传入Id并且打开流程图
	 */
	public String wrokFlowChildImgOpen(){
		String nodeId=(String) getRequest().getParameter("id");
		WfChild child = workflowBasicFlowService.getWfChildById(nodeId);
		String id=child.getWfc_cid();
		WfXml wfXml=workflowBasicFlowService.getWfXml(id);
		if(null==wfXml){
			//没有流程图，则打开新流程图
			getRequest().setAttribute("xml", "");
		}else{
			//有流程图，取出xml，返还给workflow_img
			String xml = wfXml.getWfx_xml().replaceAll("\n", "");
			getRequest().setAttribute("xml", xml);
		}
		WfMain wfMain=workflowBasicFlowService.getWfMainById(id);
		getRequest().setAttribute("flow_id", wfMain.getWfm_id());
		getRequest().setAttribute("flow_create_time", wfMain.getWfm_createtime());
		getRequest().setAttribute("flow_modified_time", wfMain.getWfm_modifytime());
		getRequest().setAttribute("flow_name", wfMain.getWfm_name());
		getRequest().setAttribute("flow_status", wfMain.getWfm_status());
		
		List<WfTableInfo> title_table_list = tableInfoService.getTableByLcid(wfMain.getWfm_id());
		List<List> title_column_list = new ArrayList<List>();
		for(WfTableInfo info : title_table_list){
			List<WfFieldInfo> column_list = tableInfoService.getFieldByTableid(info.getId());
			title_column_list.add(column_list);
		}
		getRequest().setAttribute("flow_initiate_titleNames", wfMain.getWfm_title_name());
		getRequest().setAttribute("flow_initiate_titleIds", wfMain.getWfm_title_column());
		
		getRequest().setAttribute("flow_title_table", wfMain.getWfm_title_table());
		getRequest().setAttribute("flow_title_column", wfMain.getWfm_title_column());
		getRequest().setAttribute("title_table_list", JSONArray.fromObject(title_table_list));
		getRequest().setAttribute("title_column_list", JSONArray.fromObject(title_column_list));
//		getRequest().setAttribute("calendar_list", JSONArray.fromObject(workflowCalendarService.getCalendars("")));
		getRequest().setAttribute("calendar_list", JSONArray.fromObject(pendingService.getAllYear()));
		List<ZwkjForm> zwkjFormList = zwkjFormService.getFormListByParamForPage("workflowId",id , null, null);
		for(ZwkjForm zwkjForm:zwkjFormList){
			zwkjForm.setElementLocationJson("");
		}
		getRequest().setAttribute("form_list",  JSONArray.fromObject(zwkjFormList));
		//getRequest().setAttribute("flow_procedure_list", JSONArray.fromObject(workflowBasicFlowService.getAllProcedures()));
		getRequest().setAttribute("flow_procedure_list", JSONArray.fromObject(zwkjFormService.getAllProcedureList()));
		
		List<WfCustomStatus> list=customStatusService.getCustomStatusByLcid("");
		List<WfCustomStatus> list_return=new ArrayList<WfCustomStatus>();
		for(WfCustomStatus wfObject:list){
			String wfd_id=wfObject.getId();
			String wfd_key=wfObject.getVc_key();
			String wfd_value=wfObject.getVc_value();
			String[] keys=wfd_key.split(",");
			String[] values=wfd_value.split(",");
			for(int i=0;i<keys.length;i++){
				WfCustomStatus customStatus=new WfCustomStatus();
				customStatus.setVc_key(wfd_id+"_"+keys[i]);
				customStatus.setVc_value(values[i]);
				list_return.add(customStatus);
			}
		}
		getRequest().setAttribute("global_list", JSONArray.fromObject(list_return));
		list=customStatusService.getCustomStatusByLcid(id);
		list_return=new ArrayList<WfCustomStatus>();
		for(WfCustomStatus wfObject:list){
			String wfd_id=wfObject.getId();
			String wfd_key=wfObject.getVc_key();
			String wfd_value=wfObject.getVc_value();
			String[] keys=wfd_key.split(",");
			String[] values=wfd_value.split(",");
			for(int i=0;i<keys.length;i++){
				WfCustomStatus customStatus=new WfCustomStatus();
				customStatus.setVc_key(wfd_id+"_"+keys[i]);
				customStatus.setVc_value(values[i]);
				list_return.add(customStatus);
			}
		}
		getRequest().setAttribute("current_list", JSONArray.fromObject(list_return));
		getRequest().setAttribute("template_list", JSONArray.fromObject(templateService.getTemplateByLcid(id)));
		InnerUser innerUser=new InnerUser();
		innerUser.setWorkflowId(id);
		innerUser.setType(4);
		getRequest().setAttribute("role_list", JSONArray.fromObject(groupService.getInnerUserListForPage(null,null,innerUser,null,null)));
		
		return "workflowimg_open";
	}
	
	/**
	 * 图形化保存流程
	 * 逻辑：根据页面传递参数，进行流程保存相关的操作
	 * @return
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public String addWfDefineForImaging() throws JSONException, IOException{
		try{
			String in = this.getRequest().getParameter("xml");
			in = in.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
			org.json.JSONObject json =  XML.toJSONObject(in);
			//获取该流程
			org.json.JSONObject flow = (org.json.JSONObject) json.get("flow");
			//获取流程下相关参数
			WfMain wfMain=new WfMain();
			String wfm_id=flow.getString("flow_id");
			String wfm_name=flow.getString("flow_name");
			String wfm_createtime=flow.getString("flow_create_time");
			String wfm_modifytime=flow.getString("flow_modified_time");
			String wfm_calendar=flow.getString("flow_work_calendar");
			String wfm_defaultform=flow.getString("flow_default_query_form");
			String wfm_inittasks=flow.getString("flow_initiate_tasks");
			String wfm_status = flow.getString("flow_status");
			String wfm_title_table = flow.getString("flow_title_table");
			
			String wfm_title_column_id = flow.getString("flow_initiate_titleIds");
			
			String wfm_title_column_name = flow.getString("flow_initiate_titleNames");
			//封装对象wfMain
			wfMain.setWfm_id(wfm_id);
			wfMain.setWfm_name(wfm_name);
			wfMain.setWfm_createtime(wfm_createtime);
			wfMain.setWfm_modifytime(wfm_modifytime);
			wfMain.setWfm_calendar(wfm_calendar);
			wfMain.setWfm_defaultform(wfm_defaultform);
			wfMain.setWfm_inittasks(wfm_inittasks);
			wfMain.setWfm_status(wfm_status);
			wfMain.setWfm_title_table(wfm_title_table);
			wfMain.setWfm_title_column(wfm_title_column_id);
			wfMain.setWfm_title_name(wfm_title_column_name);
			//封装子表
			Set<WfNode> node_sets=new HashSet<WfNode>();
			Set<WfLine> line_sets=new HashSet<WfLine>();
			Set<WfChild> child_sets=new HashSet<WfChild>();
			//获得node节点
			org.json.JSONArray nodes=flow.getJSONArray("flow_seq");
			for(int i=0;i<nodes.length();i++){
				WfNode wfNode=new WfNode();
				org.json.JSONObject temp=null;
						temp=(org.json.JSONObject) nodes.get(i);
				String wfn_id=temp.getString("node_id");
				String wfn_name=temp.getString("node_name");
				String wfn_createtime=temp.getString("node_create_time");
				String wfn_modifytime=temp.getString("node_modified_time");
				String wfn_deadline=temp.getString("node_dead_line");
				String wfn_deadlineunit=temp.getString("node_dead_line_unit");
				String wfn_deadline_isworkday=temp.getString("node_deadline_isworkday");
				String wfn_inittask=temp.getString("node_initiate_tasks");
				String wfn_defaulttemplate=temp.getString("node_default_template");
				String wfn_defaultform=temp.getString("node_default_form");
				String wfn_global_process_custom=temp.getString("node_global_process_custom");
				String wfn_current_process_custom=temp.getString("node_current_process_custom");
				String wfn_staff=temp.getString("node_staff");
				String wfn_allow_consultation=temp.getString("node_allow_consultation");
				String wfn_allow_delegation=temp.getString("node_allow_delegation");
				String wfn_allow_cc=temp.getString("node_allow_cc");
				String wfn_type=temp.getString("nodetype");
				String wfn_moduleid=temp.getString("id");
				String wfn_procedurename=temp.getString("node_procedure_list");
				String wfn_bd_user=temp.getString("node_bdUser");
				String wfn_route_type=temp.getString("node_route_type");//节点路由类型
				String node_work_state=temp.getString("node_work_state");
				String wfn_form_continue = temp.getString("node_form_continue");
				String wfn_iszf = temp.getString("node_iszf");
				String wfn_sortNumber = temp.getString("node_sort_number");
				Integer i_sortNumber = (wfn_sortNumber!=null && !wfn_sortNumber.equals(""))?Integer.parseInt(wfn_sortNumber):null;

				String wfn_iswcsx = temp.getString("node_iswcsx");
				String wfn_isEndRemind = temp.getString("node_isEndRemind");//是否办结提醒
				String wfn_islhfw = temp.getString("node_islhfw");
				String wfn_isseal = temp.getString("node_isseal");
				String wfn_isUploadAttach = temp.getString("node_isUploadAttach");
				//是否附件名称作为标题
				int wfn_isAttachAsTitle =  temp.getString("node_isAttachAsTitle")==""?0:
				Integer.parseInt(temp.getString("node_isAttachAsTitle"));
				String wfn_tqtxsjline=temp.getString("node_tqtxsj_line");
				String wfn_redtapeTemplate=temp.getString("node_redtape_template");	//套红头文件内容
				if(wfn_tqtxsjline==null||wfn_tqtxsjline.equals("")||wfn_tqtxsjline.equals("undefined")){
					wfn_tqtxsjline="0";
				}
				int wfn_iscountersign = temp.getString("node_iscountersign")==""?0:
					Integer.parseInt(temp.getString("node_iscountersign"));
				int wfn_isoverfirststep = temp.getString("node_isoverfirststep")==""?0:
					Integer.parseInt(temp.getString("node_isoverfirststep"));
				String wfn_txnr=temp.getString("node_txnr_txnrNames");
				String wfn_txnrid=temp.getString("node_txnr_txnrIds");
				int action_status = node_work_state==""?0: Integer.parseInt(node_work_state);
				int isExchange = temp.getString("isexchange")==""?0:Integer.parseInt(temp.getString("isexchange"));
				String wfl_child_merge=temp.getString("node_child_merge");
				String wfn_self_loop=temp.getString("node_self_loop");
				int wfn_allowfair = temp.getString("node_allowfair")==""?0:
						Integer.parseInt(temp.getString("node_allowfair"));
				int wfn_allowprint = temp.getString("node_allowprint")==""?0:
					Integer.parseInt(temp.getString("node_allowprint"));
				String wfn_zwTemSel =  temp.getString("node_zwTemSel")==""?"0":temp.getString("node_zwTemSel");//是否选择正文模板
				// 办文中是否走发文
				int wfn_send_file = temp.getString("node_send_file")==""?0:
					Integer.parseInt(temp.getString("node_send_file"));
				int node_isReply = temp.getString("node_isReply")==""?0:
					Integer.parseInt(temp.getString("node_isReply"));
				// 发起交办
				String node_startJb = temp.getString("node_startJb");
				String wfn_isdefdep=temp.getString("node_isdefdep");
				int wfn_isdisplay = temp.getString("node_isdisplay")==""?0:
					Integer.parseInt(temp.getString("node_isdisplay"));
				int wfn_isEdit = temp.getString("node_isEdit")==""?0:
					Integer.parseInt(temp.getString("node_isEdit"));
				String doubleScreen = temp.getString("node_doubleScreen");
				int wfn_doubleScreen = doubleScreen!=null && !doubleScreen.equals("")?Integer.parseInt(doubleScreen):0;
				String node_emptype = temp.getString("node_emptype");
				int wfn_isfollow = temp.getString("node_isfollow")==""?0:Integer.parseInt(temp.getString("node_isfollow"));
				int wfn_isreissue = temp.getString("node_isreissue")==""?0:Integer.parseInt(temp.getString("node_isreissue"));
				int wfn_formcopy = temp.getString("form_copy")==""?0:Integer.parseInt(temp.getString("form_copy"));
				int wfn_forceback = temp.getString("node_forceback")==""?0:Integer.parseInt(temp.getString("node_forceback"));
				int wfn_doinmobile = temp.getString("node_doinmobile")==""?0:Integer.parseInt(temp.getString("node_doinmobile"));
				int wfn_isautoclosewin = temp.getString("node_isautoclosewin")==""?0:Integer.parseInt(temp.getString("node_isautoclosewin"));
				int wfn_nodeSendAgain = temp.getString("node_send_again")==""?0:Integer.parseInt(temp.getString("node_send_again"));
				int wfn_nodeAutoNoname = temp.getString("node_auto_noname")==""?0:Integer.parseInt(temp.getString("node_auto_noname")); //是否一键签批无落款
				int wfn_nodeNewInput = temp.getString("node_new_input")==""?0:Integer.parseInt(temp.getString("node_new_input"));//是否使用请阅意见框
				String wfn_comment_sort = temp.getString("node_comment_sort");
				String wfn_child_nodeIds = temp.getString("node_child_nodeIds");
				int wfn_nodeSendBack = temp.getString("node_send_back")==""?0:Integer.parseInt(temp.getString("node_send_back"));
				String node_allowUpload = temp.getString("node_allowUpload") == "" ? "0" : temp.getString("node_allowUpload");
				int wfn_oneKeyHandle = temp.getString("node_one_key_handle")==""?0:Integer.parseInt(temp.getString("node_one_key_handle"));
				int wfn_skipUser = temp.getString("node_skip_user")==""?0:Integer.parseInt(temp.getString("node_skip_user"));
				int wfn_autoSend = temp.getString("node_auto_send")==""?0:Integer.parseInt(temp.getString("node_auto_send"));
				int wfn_autoSendDays = temp.getString("node_auto_send_days")==""?0:Integer.parseInt(temp.getString("node_auto_send_days"));
				int wfn_showMarkbtn = temp.getString("node_show_markbtn")==""?0:Integer.parseInt(temp.getString("node_show_markbtn"));
				int wfn_skipNextnodes = temp.getString("node_skip_nextnodes")==""?0:Integer.parseInt(temp.getString("node_skip_nextnodes"));
				String wfn_lastStaff = temp.getString("node_lastStaff")==""?"":temp.getString("node_lastStaff");
				
				int wfn_isautosend = temp.getString("node_isautosend")==""?0:Integer.parseInt(temp.getString("node_isautosend"));
				int wfn_isSupervision = temp.getString("node_issupervision")==""?0:Integer.parseInt(temp.getString("node_issupervision"));
				wfNode.setWfn_id(wfn_id);
				wfNode.setWfn_name(wfn_name);
				wfNode.setWfn_createtime(wfn_createtime);
				wfNode.setWfn_modifytime(wfn_modifytime);
				wfNode.setWfn_deadline(wfn_deadline);
				wfNode.setWfn_deadlineunit(wfn_deadlineunit);
				wfNode.setWfn_deadline_isworkday(wfn_deadline_isworkday);
				wfNode.setWfn_inittask(wfn_inittask);
				wfNode.setWfn_defaulttemplate(wfn_defaulttemplate);
				wfNode.setWfn_defaultform(wfn_defaultform);
				wfNode.setWfn_global_process_custom(wfn_global_process_custom);
				wfNode.setWfn_current_process_custom(wfn_current_process_custom);
				wfNode.setWfn_staff(wfn_staff);
				wfNode.setWfn_allow_consultation(wfn_allow_consultation);
				wfNode.setWfn_allow_delegation(wfn_allow_delegation);
				wfNode.setWfn_allow_cc(wfn_allow_cc);
				wfNode.setWfn_type(wfn_type);
				wfNode.setWfn_moduleid(wfn_moduleid);
				wfNode.setWfn_procedure_name(wfn_procedurename);
				wfNode.setWfn_bd_user(wfn_bd_user);
				wfNode.setWfn_route_type(wfn_route_type);
				wfNode.setAction_status(action_status);
				wfNode.setIsExchange(isExchange);
				wfNode.setWfl_child_merge(wfl_child_merge);
				wfNode.setWfn_self_loop(wfn_self_loop);
				wfNode.setWfn_form_continue(wfn_form_continue);
				wfNode.setWfn_iszf(wfn_iszf);
				wfNode.setWfn_sortNumber(i_sortNumber);
				wfNode.setWfn_tqtxsjline(wfn_tqtxsjline);
				wfNode.setWfn_txnr(wfn_txnr);
				wfNode.setWfn_txnrid(wfn_txnrid);
				wfNode.setWfn_iswcsx(wfn_iswcsx);
				wfNode.setWfn_isEndRemind(wfn_isEndRemind);
				wfNode.setWfn_isseal(wfn_isseal);
				wfNode.setWfn_isUploadAttach(wfn_isUploadAttach);
				wfNode.setWfn_isAttachAsTitle(wfn_isAttachAsTitle);
				wfNode.setWfn_allowfair(wfn_allowfair);
				wfNode.setWfn_allowprint(wfn_allowprint);
				wfNode.setNode_allowUpload(node_allowUpload);
				wfNode.setWfn_send_file(wfn_send_file);
				// 是否发起交办
				wfNode.setNode_startJb(node_startJb);
				//是否是发起的交办流程的回复节点
				wfNode.setNode_isReply(node_isReply);
				wfNode.setWfn_islhfw(wfn_islhfw);
				wfNode.setWfn_isdefdep(wfn_isdefdep);
				wfNode.setWfn_isEdit(wfn_isEdit);
				wfNode.setWfn_isdisplay(wfn_isdisplay);
				wfNode.setWfn_doubleScreen(wfn_doubleScreen);
				wfNode.setWfn_redtapeTemplate(wfn_redtapeTemplate);
				wfNode.setWfn_empType(node_emptype);
				wfNode.setWfn_iscountersign(wfn_iscountersign);
				wfNode.setWfn_isoverfirststep(wfn_isoverfirststep);
				wfNode.setWfn_isfollow(wfn_isfollow);
				wfNode.setWfn_isreissue(wfn_isreissue);
				wfNode.setWfn_formcopy(wfn_formcopy);
				wfNode.setWfn_forceback(wfn_forceback);
				wfNode.setWfn_isDoInMobile(wfn_doinmobile);
				wfNode.setWfn_isautoclosewin(wfn_isautoclosewin);
				wfNode.setWfn_comment_sort(wfn_comment_sort);
				wfNode.setWfn_child_nodeIds(wfn_child_nodeIds);
				wfNode.setWfn_isSendAgain(wfn_nodeSendAgain);
				wfNode.setWfn_autoNoname(wfn_nodeAutoNoname);
				wfNode.setWfn_isUseNewInput(wfn_nodeNewInput);
				wfNode.setWfn_isSendBack(wfn_nodeSendBack);
				wfNode.setWfn_zwTemSel(wfn_zwTemSel);
				wfNode.setWfn_oneKeyHandle(wfn_oneKeyHandle);
				wfNode.setWfn_skipUser(wfn_skipUser);
				wfNode.setWfn_autoSend(wfn_autoSend);
				wfNode.setWfn_autoSendDays(wfn_autoSendDays);
				wfNode.setWfn_showMarkbtn(wfn_showMarkbtn);
				wfNode.setWfn_skipNextnodes(wfn_skipNextnodes);
				wfNode.setWfn_lastStaff(wfn_lastStaff);
				wfNode.setWfn_isAutoSend(wfn_isautosend);
				wfNode.setWfn_isSupervision(wfn_isSupervision);
				node_sets.add(wfNode);
			}
			
			if(in.indexOf("flow_child") > -1){
				try {
					//获得子流程
					org.json.JSONArray childs=flow.getJSONArray("flow_child");
					for(int i=0;i<childs.length();i++){
						WfChild wfChild=new WfChild();
						org.json.JSONObject temp=null;
						temp=(org.json.JSONObject) childs.get(i);
						String wfc_id=temp.getString("node_id");
						String wfc_cid=temp.getString("node_wfc_name");
						String wfc_cname=temp.getString("node_name");
						String wfc_mainmerger=temp.getString("node_wfc_mainmerger");
						String wfc_ctype=temp.getString("node_wfc_ctype");
						String wfc_relation=temp.getString("node_wfc_relation");
						String wfn_moduleid=temp.getString("id");
						String wfc_outparwf=temp.getString("node_wfc_outparwf");
						String wfc_return_pend = temp.getString("node_return_pend");
						String wfc_isSend = temp.getString("node_isSend");
						String wfc_need_f_form = temp.getString("node_wfc_need_f_form");
						wfChild.setWfc_id(wfc_id);
						wfChild.setWfc_cid(wfc_cid);
						wfChild.setWfc_cname(wfc_cname);
						wfChild.setWfc_ctype(wfc_ctype);
						wfChild.setWfc_relation(wfc_relation);
						wfChild.setWfc_moduleId(wfn_moduleid);
						wfChild.setWfc_mainmerger(wfc_mainmerger);
						wfChild.setWfc_outparwf(wfc_outparwf);
						wfChild.setWfc_return_pend(wfc_return_pend);
						wfChild.setWfc_isSend(wfc_isSend);
						wfChild.setIsNeedFForm(wfc_need_f_form);
						child_sets.add(wfChild);
					}
				} catch (Exception e) {
					//获得子流程
					org.json.JSONObject childs=flow.getJSONObject("flow_child");
					WfChild wfChild=new WfChild();
					org.json.JSONObject temp=null;
					temp=(org.json.JSONObject) childs;
					String wfc_id=temp.getString("node_id");
					String wfc_cid=temp.getString("node_wfc_name");
					String wfc_cname=temp.getString("node_name");
					String wfc_ctype=temp.getString("node_wfc_ctype");
					String wfc_mainmerger=temp.getString("node_wfc_mainmerger");
					String wfc_relation=temp.getString("node_wfc_relation");
					String wfn_moduleid=temp.getString("id");
					String wfc_outparwf=temp.getString("node_wfc_outparwf");
					String wfc_return_pend = temp.getString("node_return_pend");
					String wfc_isSend = temp.getString("node_isSend");
					String wfc_nodeName = temp.getString("node_wfc_nodeName");
					String wfc_need_f_form = temp.getString("node_wfc_need_f_form");
					wfChild.setWfc_id(wfc_id);
					wfChild.setWfc_cid(wfc_cid);
					wfChild.setWfc_cname(wfc_cname);
					wfChild.setWfc_ctype(wfc_ctype);
					wfChild.setWfc_relation(wfc_relation);
					wfChild.setWfc_moduleId(wfn_moduleid);
					wfChild.setWfc_mainmerger(wfc_mainmerger);
					wfChild.setWfc_outparwf(wfc_outparwf);
					wfChild.setWfc_return_pend(wfc_return_pend);
					wfChild.setWfc_isSend(wfc_isSend);
					wfChild.setWfc_nodeName(wfc_nodeName);
					wfChild.setIsNeedFForm(wfc_need_f_form);
					child_sets.add(wfChild);
				}
			}
			
			try{
			org.json.JSONArray lines=flow.getJSONArray("flow_line");
			for(int i=0;i<lines.length();i++){
				WfLine wfLine=new WfLine();
				org.json.JSONObject temp=null;
				temp=(org.json.JSONObject) lines.get(i);
				String wfl_id=temp.getString("line_id");
				String wfl_conditions=temp.getString("line_conditions");
				String wfl_xBaseMode=temp.getString("xBaseMode");
				String wfl_wBaseMode=temp.getString("wBaseMode");
				String wfl_arrow=temp.getString("line_arrow");
				String wfl_route_type=temp.getString("line_route_type");
				String wfl_remark=temp.getString("line_remark");
				String wfl_xBaseMode_type=temp.getString("xBaseModeType");
				String wfl_wBaseMode_type=temp.getString("wBaseModeType");
				String wfl_choice_condition = temp.getString("line_choice_condition");
				String wfl_choice_rule = temp.getString("line_choice_rule");
				wfLine.setWfl_id(wfl_id);
				wfLine.setWfl_conditions(wfl_conditions);
				wfLine.setWfl_xBaseMode(wfl_xBaseMode);
				wfLine.setWfl_wBaseMode(wfl_wBaseMode);
				wfLine.setWfl_arrow(wfl_arrow);
				wfLine.setWfl_route_type(wfl_route_type);
				wfLine.setWfl_remark(wfl_remark);
				wfLine.setWfl_xBaseMode_type(wfl_xBaseMode_type);
				wfLine.setWfl_wBaseMode_type(wfl_wBaseMode_type);
				wfLine.setWfl_choice_condition(wfl_choice_condition);
				wfLine.setWfl_choice_rule(wfl_choice_rule);
				line_sets.add(wfLine);
			}
			}catch(JSONException e){
				org.json.JSONObject line=flow.getJSONObject("flow_line");
				WfLine wfLine=new WfLine();
				org.json.JSONObject temp=null;
				temp=line;
				String wfl_id=temp.getString("line_id");
				String wfl_conditions=temp.getString("line_conditions");
				String wfl_xBaseMode=temp.getString("xBaseMode");
				String wfl_wBaseMode=temp.getString("wBaseMode");
				String wfl_arrow=temp.getString("line_arrow");
				String wfl_route_type=temp.getString("line_route_type");
				String wfl_remark=temp.getString("line_remark");
				String wfl_xBaseMode_type=temp.getString("xBaseModeType");
				String wfl_wBaseMode_type=temp.getString("wBaseModeType");
				wfLine.setWfl_id(wfl_id);
				wfLine.setWfl_conditions(wfl_conditions);
				wfLine.setWfl_xBaseMode(wfl_xBaseMode);
				wfLine.setWfl_wBaseMode(wfl_wBaseMode);
				wfLine.setWfl_arrow(wfl_arrow);
				wfLine.setWfl_route_type(wfl_route_type);
				wfLine.setWfl_remark(wfl_remark);
				wfLine.setWfl_xBaseMode_type(wfl_xBaseMode_type);
				wfLine.setWfl_wBaseMode_type(wfl_wBaseMode_type);
				line_sets.add(wfLine);
			}
			
			wfMain.setNode_sets(node_sets);
			wfMain.setLine_sets(line_sets);
			wfMain.setChild_sets(child_sets);
			//-------------------------------------------------------------------
			//根据wfid去数据库判断是否存在该条流程
			WfMain main=workflowBasicFlowService.getWfMainById(wfm_id);
			String result = "";
			if(null==main){		//新增
				//如果不存在该流程，则保存该流程，并且保存该流程图
				workflowBasicFlowService.saveWfMain(wfMain);
				WfXml wfXml=new WfXml();
				wfXml.setWfx_id(wfm_id);
				wfXml.setWfx_xml(in);
				workflowBasicFlowService.saveWfXml(wfXml);
				result = "成功";
			}else{			//修改更细
				WfXml wfXml=new WfXml();
				wfXml.setWfx_id(wfm_id);
				wfXml.setWfx_xml(in);
				JSONObject object = workflowBasicFlowService.saveWfMainInfo(wfm_id, wfMain, wfXml);
				if(object!=null){
					boolean isSuccess = object.get("success")!=null? (Boolean)object.get("success"):false;
					if(isSuccess){
						result = "成功";
					}else{
						result = "失败";
					}
				}else{
					result = "失败";
				}
			}	
			//保存节点到历史节点中
			workflowBasicFlowService.addNodeToHistroy(wfm_id);
			workflowBasicFlowService.addLineToHistroy(wfm_id);
			PrintWriter out=getResponse().getWriter();
			out.print(result);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			PrintWriter out=getResponse().getWriter();
			out.print("失败");
			out.flush();
			out.close();
		}
		return null;
	}
	
	/**
	 * 获取流程列表
	 * @return
	 */
	public String listWF(){
		//获取用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		//查询用户所在的角色
		List<EmployeeRole> roleList = employeeRoleService.findEmployeeRoleById(emp.getEmployeeGuid());
		int allCount = employeeRoleService.findAllWfMainRoleCount();
		String roleIds = "";
		EmployeeRole employeeRole = null;
		if(roleList!=null && roleList.size()>0){
			for(int i=0; i<roleList.size();i++){
				employeeRole = roleList.get(i);
				roleIds += "'"+employeeRole.getId()+"'";
				if(i!=roleList.size()-1){
					roleIds +=",";
				}
			}
		}
		if(allCount==0){
			roleIds = "all";
		}
		//分页相关，代码执行顺序不变
		String conditionSql = "";		//查询检索条件
		//工作流名称
		String wfname=getRequest().getParameter("wfname");
		if(wfname!=null && !wfname.equals("")){
			conditionSql += " and w.wfm_name like '%"+wfname+"%'";
		}
		int count=workflowBasicFlowService.getCountFromWfMain(roleIds, conditionSql );
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Paging.setPagingParams(getRequest(), pageSize, count);
		//List<WfMain> list = workflowBasicFlowService.getBasicFlowList(roleIds, Paging.pageIndex,Paging.pageSize);
		List<Object[]> list = workflowBasicFlowService.getBasicFlowList(roleIds, conditionSql, Paging.pageIndex,Paging.pageSize);
		List<WfMainJson> list_return=new ArrayList<WfMainJson>();
		for(Object[] main:list){
			WfMainJson wfMainJson=null;
			wfMainJson=new WfMainJson();
			wfMainJson.setWfm_id(main[0]==null?"":main[0].toString());
			wfMainJson.setWfm_name(main[1]==null?"":main[1].toString());
			wfMainJson.setWfm_createtime(main[3]==null?"":main[3].toString());
			wfMainJson.setWfm_modifytime(main[2]==null?"":main[2].toString());
			if("0".equals(main[4])){
				wfMainJson.setWfm_status("暂停");
			}else if("1".equals(main[4])){
				wfMainJson.setWfm_status("调试");
			}else if("2".equals(main[4])){
				wfMainJson.setWfm_status("运行");
			}
			list_return.add(wfMainJson);
		}
		getRequest().setAttribute("list", list_return);
		getRequest().setAttribute("wfname", wfname);
		//System.out.println(list_return.toString());
		return "workflow_home";
	}
	
	/**
	 * 
	 * 描述：查询全部的工作流
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2014-8-6 下午5:43:19
	 */
	public String getAllWfMainList(){
		//主流程id
		List<Object[]> list = workflowBasicFlowService.getBasicFlowList("all","",null,null);
		List<WfMainJson> list_return=new ArrayList<WfMainJson>();
		for(Object[] main:list){
			WfMainJson wfMainJson=null;
			wfMainJson=new WfMainJson();
			wfMainJson.setWfm_id(main[0]==null?"":main[0].toString());
			wfMainJson.setWfm_name(main[1]==null?"":main[1].toString());
			wfMainJson.setWfm_createtime(main[3]==null?"":main[3].toString());
			wfMainJson.setWfm_modifytime(main[2]==null?"":main[2].toString());
			if("0".equals(main[4])){
				wfMainJson.setWfm_status("暂停");
			}else if("1".equals(main[4])){
				wfMainJson.setWfm_status("调试");
			}else if("2".equals(main[4])){
				wfMainJson.setWfm_status("运行");
			}
			list_return.add(wfMainJson);
		}
		//roleId
		String roleId = getRequest().getParameter("roleId");
		List<WfMainRole> mainRoleList = employeeRoleService.findWfMainRoleByRoleId(roleId);
		getRequest().setAttribute("list", list_return);
		getRequest().setAttribute("roleId", roleId);
		getRequest().setAttribute("mainRoleList", mainRoleList);
		return "wfMainList";
	}
	/**
	 * 将session_id存入session
	 */
	public void setSessionId(){
		getSession().setAttribute("workflow_defined_id", getRequest().getParameter("session_id"));
		System.out.println("存入session...");
		System.out.println("session--->:"+getSession().getAttribute("workflow_defined_id"));
	}
	
	/**
	 * 检索查询,接收前端模糊查询字段
	 */
	public String getWfMainListByRetrieval(){
		System.out.println("模糊查询开始...");
		String wfname=getRequest().getParameter("wfname");
		String province=getRequest().getParameter("province");
		String begin_rq=getRequest().getParameter("begin_rq");
		String end_rq=getRequest().getParameter("end_rq");
		if("已提交".equals(province)){
			province="1";
		}else if("未提交".equals(province)){
			province="0";
		}
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		//查询用户所在的角色
		List<EmployeeRole> roleList = employeeRoleService.findEmployeeRoleById(emp.getEmployeeGuid());
		int allCount = employeeRoleService.findAllWfMainRoleCount();
		String roleIds = "";
		EmployeeRole employeeRole = null;
		if(roleList!=null && roleList.size()>0){
			for(int i=0; i<roleList.size();i++){
				employeeRole = roleList.get(i);
				roleIds += "'"+employeeRole.getId()+"'";
				if(i!=roleList.size()-1){
					roleIds +=",";
				}
			}
		}
		
		if(allCount==0){
			roleIds = "all";
		}
		String conditionSql = "";		//查询检索条件
		if(wfname!=null && !wfname.equals("")){
			conditionSql += " and w.wfm_name like '%"+wfname+"%'";
		}
		int count=workflowBasicFlowService.getCountFromWfMain(roleIds, conditionSql);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Object[]> list = workflowBasicFlowService.getBasicFlowList(roleIds, conditionSql, Paging.pageIndex,Paging.pageSize);
		List<WfMainJson> list_return=new ArrayList<WfMainJson>();
		for(Object[] main:list){
			WfMainJson wfMainJson=null;
			wfMainJson=new WfMainJson();
			wfMainJson.setWfm_id(main[0]==null?"":main[0].toString());
			wfMainJson.setWfm_name(main[1]==null?"":main[1].toString());
			wfMainJson.setWfm_createtime(main[3]==null?"":main[3].toString());
			wfMainJson.setWfm_modifytime(main[2]==null?"":main[2].toString());
			if("0".equals(main[4])){
				wfMainJson.setWfm_status("暂停");
			}else if("1".equals(main[4])){
				wfMainJson.setWfm_status("调试");
			}else if("2".equals(main[4])){
				wfMainJson.setWfm_status("运行");
			}
			list_return.add(wfMainJson);
		}
		getRequest().setAttribute("list", list_return);
		getRequest().setAttribute("wfname", wfname);
		return "workflow_home";
	}
	
	/**
	 * 
	 * @Title: getListWf 
	 * @Description: 获取去除当前流程的所有流程
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void getListWf(){
		PrintWriter out=null;
		String outStr = "";
		try {
			out = getResponse().getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (out!=null) { 
			try {
				String id = (String) getSession().getAttribute(MyConstants.workflow_session_id);
				String type = getRequest().getParameter("type");
				if(type != null && type.equals("all")){
					id  = null; 
				}
				List<Object[]> listWf = workflowBasicFlowService.getListWf(id);
				for (Object[] main:listWf) {
					outStr += main[0].toString()+","+main[1].toString()+";";
				}
				if(!("").equals(outStr) && outStr.length()>0){
					outStr = outStr.substring(0,outStr.length()-1);
				}
				out.write(outStr);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				out.close();
			}
		}
	}
	
	
	/**
	 * 
	 * 描述：获取流程的全部节点
	 * 作者:蔡亚军
	 * 创建时间:2016-7-11 下午3:12:17
	 */
	public void getWfNodeList(){
		PrintWriter out=null;
		String outStr = "";
		try {
			out = getResponse().getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (out!=null) { 
			try {
				String id = (String) getSession().getAttribute(MyConstants.workflow_session_id);
				List<WfNode> listWf = workflowBasicFlowService.getWfNodeList(id);
				for (WfNode node: listWf) {
					outStr += node.getWfn_id()+","+ node.getWfn_name()+";";
				}
				if(!("").equals(outStr) && outStr.length()>0){
					outStr = outStr.substring(0,outStr.length()-1);
				}
				out.write(outStr);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				out.close();
			}
		}
	}
	
	
	/**
	 * 
	 * 描述：跳转到复制工作流
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2014-11-3 下午2:51:12
	 */
	public String toCopyWorkFlow(){
		String id = getRequest().getParameter("id");
		WfMain wfMain = workflowBasicFlowService.getWfMainById(id);
		getRequest().setAttribute("wfMain", wfMain);
		return "toCopyWorkFlow";
	}
	
	/**
	 * 
	 * 描述：复制保存数据
	 * 作者:蔡亚军
	 * 创建时间:2014-11-3 下午2:55:46
	 */
	public void copyWorkFlow(){
		String wf_name=getRequest().getParameter("wf_name");		//新增流程名称
		String old_workflowId = getRequest().getParameter("old_workflowId");		//被选择复制的流程
		WfMain wfMain = workflowBasicFlowService.getWfMainById(old_workflowId);
		
		WfMain oldWfMain = new WfMain();
		oldWfMain.setLine_sets(wfMain.getLine_sets());
		oldWfMain.setWfm_id(wfMain.getWfm_id());
		oldWfMain.setNode_sets(wfMain.getNode_sets());
		oldWfMain.setChild_sets(wfMain.getChild_sets());

		//重新定义流程对象
		String newWfm_id = UuidGenerator.generate36UUID();
		wfMain.setWfm_id(newWfm_id);
		wfMain.setWfm_name(wf_name);
		wfMain.setLine_sets(null);
		wfMain.setNode_sets(null);
		wfMain.setChild_sets(null);

		workflowBasicFlowService.addWorkFlow(wfMain);
		JSONObject error = null;
		error = workflowBasicFlowService.copyWorkFlow(oldWfMain, newWfm_id);
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			out.write(error.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
	 * 描述：跳转到设置节点选择代码块
	 * 作者:蔡亚军
	 * 创建时间:2014-12-24 上午9:22:56
	 */
	public  String  toSetLineCondition(){
		String condition  = getRequest().getParameter("condition");		//条件选择内容
		String rule =  getRequest().getParameter("rule");	
		String wfUId = (String) getSession().getAttribute(MyConstants.workflow_session_id);	//工作流id
		//获取涉及的关联表
		WfTableInfo table = new WfTableInfo(); 
		if(condition!=null && !condition.equals("")){
			condition = condition.replaceAll("%26", "&");
			condition = condition.replaceAll("%2B", "+");
		}
		table.setLcid(wfUId);
		List<WfTableInfo> list = tableInfoService.getTableListForPage("", "", table, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("condition", condition);
		getRequest().setAttribute("rule", rule);
		return "toLineCondition";
	}
	
	
	/**
	 * 描述：导出流程  sql 文件夹, form 文件夹，template 文件夹
	 * 流程导出
		1.	业务表 （判断表是否存在，不存在插入数据）
		t_wf_core_fieldinfo, T_WF_CORE_TABLE
		2.	表单
		t_wf_core_form, t_wf_core_form_map_column,T_WF_CORE_TABFORMRELATION
		表单文件
		3.	用户组
		t_wf_core_inneruser
		6.	正文模板
		t_wf_core_template,t_wf_core_labelfield 模板本身
		
		4.	流程图 WF_Main,wf_xml,wf_node,Wf_line ,待补充子流程（wf_child)
		5.	许可设置
		t_wf_core_formpermit
		
		7.	工作日历 
		判断是否存在 ，存在不用继续插入
		8.	返回指定节点
		9.	设置办结节点
		10.	文号管理
	 * 作者:Yuxl
	 * 创建时间:2016-8-22 下午1:50:36
	 * 剩余问题 : 1 业务表存在 , 
	 *          2 clob 字段处理
	 * @throws Exception 
	 */
	public void exportWorkFlow() throws Exception{
		// 获取流程 id
		String id = getRequest().getParameter("id");
		String zipName = workflowBasicFlowService.getWfMainNameById(id);
		
		// 1. 业务表 （判断表是否存在，不存在插入数据）
		//tableIds key 为old tableId ,value 为new word id
		String newWfm_id = UuidGenerator.generate36UUID();
		Map<String ,String> tableIds = new HashMap<String ,String>();
		
		String rootFolder = PathUtil.getWebRoot()+"tempfile/"+id;
		File file = new File(rootFolder);
		
		if(file.exists()){
			 //删除生成的文件夹
		    boolean isdelete = ZipUtil.deleteDir(new File(rootFolder));
		    System.out.println(isdelete);
		}
		
		file.mkdirs();
		
		tableIds = workflowBasicFlowService.geneOfficeInfo(id,newWfm_id,rootFolder);
		
		// 2.  form 表单
		Map<String ,String> formIds = new HashMap<String ,String>();
		formIds = workflowBasicFlowService.geneFormInfo(id,newWfm_id,rootFolder);
		
		// 3.  用户组 (只需要添加用户组名，人员到时候添加到用户组里面)
		Map<String ,String> innerUserIds = new HashMap<String ,String>();
		innerUserIds = workflowBasicFlowService.geneInnerUserInfo(id,newWfm_id,rootFolder);
		
		// 6. 正文模板
		Map<String ,String> templateIds = new HashMap<String ,String>();
		templateIds = workflowBasicFlowService.geneTemplateInfo(id,newWfm_id,rootFolder,tableIds);
		
		//10.	文号管理
		// 行权里面 没有 文号功能 
		//7.	工作日历   手动添加
		
		//4.	流程图 WF_Main,wf_xml,wf_node,Wf_line ,待补充子流程（wf_child) 9.	设置办结节点 wfn_isendnode
		Map<String ,String> nodeIds = new HashMap<String ,String>();
		nodeIds = workflowBasicFlowService.geneFlowInfo(id,newWfm_id,rootFolder,tableIds,innerUserIds,formIds,templateIds);
		
		//5.	许可设置 t_wf_core_formpermit
		Map<String ,String> permitIds = new HashMap<String ,String>();
		permitIds = workflowBasicFlowService.genePermitInfo(id,newWfm_id,rootFolder,tableIds,innerUserIds,formIds,nodeIds);
		
		//8.	返回指定节点
	    workflowBasicFlowService.geneBackNodeInfo(id,newWfm_id,rootFolder,nodeIds);
	    
	    //9. 附件材料
	    //workflowBasicFlowService.geneAttRelationInfo(id,newWfm_id,rootFolder);
	    
	    //判断是否存在已生成的zip文件 
	    file = new File(PathUtil.getWebRoot()+"tempfile/"+zipName+".zip");
	    if(file.exists()){
			 //删除生成的文件夹
	    	ZipUtil.doDeleteEmptyDir(PathUtil.getWebRoot()+"tempfile/"+zipName+".zip");
		}
	    
	    ZipUtil.zipFiles(rootFolder, PathUtil.getWebRoot()+"tempfile/"+zipName+".zip" );
	    
	    PrintWriter out=null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		out.write("tempfile/"+zipName+".zip");
		
		out.close();
	}
	
	/**
	 * 描述：流程数据导入
	 * 文件存储在 工程下面 template 文件夹下面 template/lcid/
	 * 1. 流程图 flow
	 * 2. 业务表 office 
	 * 3. 表单 form  包含html,jsp,sql
	 * 4. 用户组 innerUser
	 * 5. 表单权限 permit
	 * 6. 正文模板 templaet 包含doc,sql
	 * 7. set 自动追溯
	  sql 文件可以直接导入，html ，jsp ，doc 导入到对应的文件夹下面
	 * 作者:Yuxl
	 * 创建时间:2016-8-31 下午4:53:58
	 */
	public void importWorkFlow() throws Exception{
		String uploadfilename = getFileFileName();
		String name = uploadfilename.substring(0, uploadfilename.lastIndexOf("."));
		try{
    		File attsFile = getFile();
    		FileUploadUtils.copy(attsFile, new File(PathUtil.getWebRoot()
    				+ "tempfile/" + uploadfilename));
    		getResponse().setContentType("text/xml");
    		getResponse().setCharacterEncoding("GBK");
    		PrintWriter out = getResponse().getWriter();
    		out.write(uploadfilename);
    		ZipUtil.unZipFiles(PathUtil.getWebRoot()+ "tempfile/" + uploadfilename, PathUtil.getWebRoot()+"tempfile/"+name);
    		
    		
    		//0. 导入文件夹 方法 ,解压
    		String rootPath = PathUtil.getWebRoot()+"tempfile/"+name;
    		//String rootPath = PathUtil.getWebRoot()+"tempfile/B21AFE4E-9846-44AE-972B-07481A192405";
    		 // 1. 流程图 WF_Main,wf_xml,wf_node,Wf_line ,待补充子流程（wf_child)
    		 //    文件夹 lcid/flow 存储 (line,main,node) 在main文件夹下面(main.sql,xml_lcid.txt)  xml_lcid.txt 存储为这样是因为 clob 内容多无法插入 这个需要考虑
    		String flowPath = rootPath+"/flow";
    		workflowBasicFlowService.importFlowInfo(flowPath);
    		
    		//2. 业务表 office  
    		// 考虑到 业务吧可能存在  可能是引用，和 创建 ，分为2块内容 一个是 tables.txt 还有是表名 构建的 sql 文件 tables.txt 存储的是 表名\r\n 关联业务表的sql \r\n存储的是 表名\r\n 关联业务表的sql
    		// 如果表名存在 执行下一行的 update 语句 如果不存在 执行以表名 命名的sql文件
    		 String officePath = rootPath+"/office";
    		 workflowBasicFlowService.importOfficeInfo(officePath);
    		// 3. 表单 form  包含html,jsp,sql 执行sql 将html 和 jsp 插入到 form文件夹下面对应的html和jsp 文件下面
    		 String formPath = rootPath+"/form";
    		 workflowBasicFlowService.importFormInfo(formPath);
    		// 4. 用户组 innerUser 执行sql 只插入用户组名 
    		 String innerUserPath = rootPath+"/innerUser";
    		 workflowBasicFlowService.importInnerUserInfo(innerUserPath);
    		// 5. 表单权限 permit 执行sql 
    		 String permitPath = rootPath+"/permit";
    		 workflowBasicFlowService.importPermitInfo(permitPath);
    		// 6. 正文模板 包含doc,sql 执行sql doc下文件 放到template 文件下面
    		// 正文模板这块还有一个内容没有处理 书签关联表字段
    		 String templatePath = rootPath+"/template";
    		 workflowBasicFlowService.importTemplateInfo(templatePath);
    		// 7. set 自动追溯 执行sql
    		 String setPath = rootPath+"/set";
    		 workflowBasicFlowService.importSetInfo(setPath);
    		 //8. 附件材料绑定流程 执行sql
    		 /*String attPath = rootPath+"/att";
    		 workflowBasicFlowService.importAttInfo(attPath);*/
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
   		 	//9.删除掉上传的压缩包，以及解压的文件夹
			MyUtils.delFile(PathUtil.getWebRoot()+ "tempfile/" + uploadfilename);
			MyUtils.delFiles(new File(PathUtil.getWebRoot()+"tempfile/"+name));
		}
		 
	}
	
	/**
	 * 
	 * 描述：跳转到附件上传界面
	 * @return String
	 * 作者:刘钰冬
	 * 创建时间:2016-9-5 上午11:07:18
	 */
	public String toUploadZip(){
		return "uploadZip";
	}
	
	
	/**
	 * 
	 * 描述：修改编辑流程表单
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-12-20 下午3:00:13
	 */
	public String initWfMain(){
		String workflowId = getRequest().getParameter("workflowId");			//流程id
		getRequest().setAttribute("workflowId", workflowId);
		return "initWfMain";
	}
	
	public String workFlowScan(){
		String workflowId = getRequest().getParameter("id");			//流程id
		getRequest().setAttribute("workflowId", workflowId);
		return "workFlowScan";
	}
	
	public void checkForm() throws IOException{
		String workflowId = this.getRequest().getParameter("workflowId");
		List<ZwkjForm> formlist = zwkjFormService.getFormListByParamForPage("workflowId", workflowId, null, null);
		JSONObject status = new JSONObject();
		JSONArray ja = new JSONArray();
		String flag = "success";
		if(formlist.size()>0){
			for(ZwkjForm zf:formlist){
				JSONObject jo = new JSONObject();
				jo.put("formName", zf.getForm_caption());
				String htmlpath = zf.getForm_htmlfilename();	// html文件
				String jsppath = zf.getForm_jspfilename();		//jsp文件
//				String trueFile = zf.getForm_pdf();				//true文件
//				String pdfFile = trueFile.split(".")[0]+".pdf";	//pdf文件
				File htmlfile = new File(PathUtil.getWebRoot()+"/form/html/"+htmlpath);
				File jspfile = new File(PathUtil.getWebRoot()+"/form/jsp/"+jsppath);
				if(!htmlfile.isDirectory()&&htmlfile.exists()){
					jo.put("htmlfile", "exists");
				}else{
					jo.put("htmlfile", "not-exists");
					flag = "fail";
				}
				if(!jspfile.isDirectory()&&jspfile.exists()){
					jo.put("jspfile", "exists");
				}else{
					jo.put("jspfile", "not-exists");
					flag = "fail";
				}
				ja.add(jo);
			}
		}else{
			flag = "fail";
		}
		status.put("status", flag);
		status.put("forms", ja);
		PrintWriter out = getResponse().getWriter();
		out.write(status.toString());
		//out.write("{\"status\":\"falil\",\"forms\":[{\"formName\":\"文件传阅、拟办单\",\"htmlfile\":\"not-exists\",\"jspfile\":\"exists\"}]}");
	}
	
	public void checkFormTagMapColumn() throws IOException{
		String workflowId = this.getRequest().getParameter("workflowId");
		List<ZwkjForm> formlist = zwkjFormService.getFormListByParamForPage("workflowId", workflowId, null, null);
		JSONObject status = new JSONObject();
		JSONArray ja = new JSONArray();
		String flag = "success";
		if(formlist.size()>0){
			for(ZwkjForm zf:formlist){
				List<FormTagMapColumn> formTagMapColumns = zwkjFormService.getTableNameByFormId(zf.getId());
				for(int i=0;i<formTagMapColumns.size();i++){
					FormTagMapColumn tag = formTagMapColumns.get(i);
					if("attachment".equals(tag.getFormtagtype())){
						continue;
					}
					if(CommonUtil.stringIsNULL(tag.getColumnname())){
						flag = "fail";
						JSONObject jo = new JSONObject();
						jo.put("formName", zf.getForm_caption());
						jo.put("status", "fail");
						jo.put("txt", "“"+tag.getFormtagname()+"”标签“取值”位置未设置数据表字段名称，请注意");
						ja.add(jo);
					}
					if(CommonUtil.stringIsNULL(tag.getAssignColumnName())){
						flag = "fail";
						JSONObject jo = new JSONObject();
						jo.put("formName", zf.getForm_caption());
						jo.put("status", "fail");
						jo.put("txt", "“"+tag.getFormtagname()+"”标签“赋值”位置未设置数据表字段名称，请注意");
						ja.add(jo);
					}
					for(int j=i+1;j<formTagMapColumns.size();j++){
						FormTagMapColumn subtag = formTagMapColumns.get(j);
						if("attachment".equals(subtag.getFormtagtype())){
							continue;
						}
						if((tag.getTablename()+tag.getColumnname()+"").equals(subtag.getTablename()+subtag.getColumnname())){
							flag = "fail";
							JSONObject jo = new JSONObject();
							jo.put("formName", zf.getForm_caption());
							jo.put("status", "fail");
							jo.put("txt", "“取值”位置的“"+tag.getFormtagname()+"”标签和“"+subtag.getFormtagname()+"”标签设置的表和字段名称重复，请重新设置");
							ja.add(jo);
						}
						if((tag.getAssignTableName()+tag.getAssignColumnName()+"").equals(subtag.getAssignTableName()+subtag.getAssignColumnName())){
							flag = "fail";
							JSONObject jo = new JSONObject();
							jo.put("formName", zf.getForm_caption());
							jo.put("status", "fail");
							jo.put("txt", "“赋值”位置的“"+tag.getFormtagname()+"”标签和“"+subtag.getFormtagname()+"”标签设置的表和字段名称重复，请重新设置");
							ja.add(jo);
						}
					}
				}
				String truePath = zf.getForm_pdf();				//true文件
				if(CommonUtil.stringIsNULL(truePath)){
					flag = "fail";
					JSONObject jo = new JSONObject();
					jo.put("formName", zf.getForm_caption());
					jo.put("status", "fail");
					jo.put("txt", "未找到生成的true文件，请尝试重新设置");
					ja.add(jo);
					JSONObject jo2 = new JSONObject();
					jo2.put("formName", zf.getForm_caption());
					jo2.put("status", "fail");
					jo2.put("txt", "未找到生成的pdf文件，请尝试重新设置");
					ja.add(jo2);
				}else{
					String pdfPath = truePath.replace("true", "pdf");	//pdf文件
					File trueFile = new File(PathUtil.getWebRoot()+"/form/html/"+truePath);
					File pdfFile = new File(PathUtil.getWebRoot()+"/form/html/"+pdfPath);
					if(!trueFile.isDirectory()&&trueFile.exists()){
					}else{
						flag = "fail";
						JSONObject jo = new JSONObject();
						jo.put("formName", zf.getForm_caption());
						jo.put("status", "fail");
						jo.put("txt", "未找到生成的true文件，请尝试重新设置");
						ja.add(jo);
					}
					if(!pdfFile.isDirectory()&&pdfFile.exists()){
					}else{
						flag = "fail";
						JSONObject jo = new JSONObject();
						jo.put("formName", zf.getForm_caption());
						jo.put("status", "fail");
						jo.put("txt", "未找到生成的pdf文件，请尝试重新设置");
						ja.add(jo);
					}
				}
			}
		}else{
			flag = "fail";
		}
		status.put("status", flag);
		status.put("forms", ja);
		PrintWriter out = getResponse().getWriter();
		out.write(status.toString());
	}
	
	public void checkUserCreate() throws IOException{
		String workflowId = this.getRequest().getParameter("workflowId");
		List<InnerUser> users = groupService.getInnerUserList(workflowId, "4");
		JSONObject status = new JSONObject();
		JSONArray ja = new JSONArray();
		String flag = "success";
		String txt = "success";
		if(users.size()>0){
			for(int i=0;i<users.size();i++){
				InnerUser user = users.get(i);
				List<InnerUserMapEmployee> iume = groupService.getListByInnerUserId(user.getId(),null,"");
				if(iume.size() < 0){
					flag = "fail";
					txt = "角色设置存在问题";
					JSONObject jo = new JSONObject();
					jo.put("userName", user.getName());
					jo.put("status", "fail");
					jo.put("txt", "流程用户组“"+user.getName()+"”中未设置人员，请注意");
					ja.add(jo);
				}else{
					JSONObject jo = new JSONObject();
					jo.put("userName", user.getName());
					jo.put("status", "success");
					jo.put("txt", "流程用户组“"+user.getName()+"”已设置");
					ja.add(jo);
				}
			}
		}else{
			flag = "fail";
			txt = "未设置流程用户组";
		}
		status.put("status", flag);
		status.put("txt", txt);
		status.put("users", ja);
		PrintWriter out = getResponse().getWriter();
		out.write(status.toString());
	}
	
	public void checkFlowCreate() throws IOException{
		String workflowId = this.getRequest().getParameter("workflowId");
		List<WfNode> nodes = workflowBasicFlowService.getWfNodeList(workflowId);
		JSONObject status = new JSONObject();
		JSONArray ja = new JSONArray();
		String flag = "success";
		String txt = "流程构建成功";
		if(nodes.size()>0){
			for(int i=0;i<nodes.size();i++){
				WfNode node = nodes.get(i);
				String wfn_route_type = node.getWfn_route_type();
				String Wfn_staff = node.getWfn_staff();
				if(CommonUtil.stringIsNULL(wfn_route_type)){
					flag = "fail";
					txt = "流程节点设置出错";
					JSONObject jo = new JSONObject();
					jo.put("nodeName", node.getWfn_name());
					jo.put("status", "fail");
					jo.put("txt", "节点“"+node.getWfn_name()+"”未设置路由类型");
					ja.add(jo);
				}
				if(CommonUtil.stringIsNULL(Wfn_staff)){
					flag = "fail";
					txt = "流程节点设置出错";
					JSONObject jo = new JSONObject();
					jo.put("nodeName", node.getWfn_name());
					jo.put("status", "fail");
					jo.put("txt", "节点“"+node.getWfn_name()+"”未设置节点用户组");
					ja.add(jo);
				}
			}
		}else{
			flag = "fail";
			txt = "流程构建出错，未设置流程节点";
		}
		status.put("status", flag);
		status.put("txt", txt);
		status.put("nodes", ja);
		PrintWriter out = getResponse().getWriter();
		out.write(status.toString());
	
	}
	
	public void checkFormPermition() throws IOException{
		String workflowId = this.getRequest().getParameter("workflowId");
		List<ZwkjForm> list=zwkjFormService.getFormListByParamForPage("workflowId", workflowId, null, null);
		JSONObject status = new JSONObject();
		JSONArray ja = new JSONArray();
		String flag = "success";
		String txt = "表单权限设置成功";
		if(list.size() > 0){
			for(ZwkjForm zf: list){
				List<WfNode> nodes = workflowBasicFlowService.getWfNodeList(workflowId);
				List<WfNode> gllist=new ArrayList<WfNode>();
				if (nodes!=null&&nodes.size()>0) {
					for (int i = 0; i < nodes.size(); i++) {
						String defaultForm = nodes.get(i).getWfn_defaultform();
						if (CommonUtil.stringNotNULL(defaultForm)&&zf.getId().equals(defaultForm)) {
							gllist.add(nodes.get(i));
							List<WfFormPermit> wfpList = formPermitService.getPermitByFormId_new(zf.getId(),nodes.get(i).getWfn_id(),"1");
							if(wfpList.size()==0){
								flag = "fail";
								txt = "表单权限设置出错";
								JSONObject jo = new JSONObject();
								jo.put("permitName", zf.getForm_name());
								jo.put("status", "fail");
								jo.put("txt", "表单“"+zf.getForm_caption()+"”的“"+nodes.get(i).getWfn_name()+"”节点未设置用户组权限");
								ja.add(jo);
							}
						}
					}
				}
				if(gllist.size()==0){
					flag = "fail";
					txt = "表单权限设置出错";
					JSONObject jo = new JSONObject();
					jo.put("permitName", zf.getForm_name());
					jo.put("status", "fail");
					jo.put("txt", "表单“"+zf.getForm_name()+"”未设置流程节点");
					ja.add(jo);
				}
			}
		}else{
			flag = "fail";
			txt = "表单权限设置出错，未找到表单";
		}
		status.put("status", flag);
		status.put("txt", txt);
		status.put("nodes", ja);
		PrintWriter out = getResponse().getWriter();
		out.write(status.toString());
	
	}
	
	/**
	 * 
	 * 描述：获取整个流程的全部子流程节点权限。
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-10-21 下午3:18:27
	 */
	public String getChildWfNodeList(){
		String wfUId = getRequest().getParameter("wfUId");			//获取流程的列表
		String ids = getRequest().getParameter("ids");	
		List<WfNode> list = workflowBasicFlowService.getWfNodeList(wfUId);
		
		
		List<WfChild> parList = workflowBasicFlowService.findWfChildListByWfcid(wfUId);
		//获取其子流程
		WfChild child2 = null;
		for(int i=0; i<parList.size(); i++){
			child2 = parList.get(i);
			List<WfNode> nodeList = workflowBasicFlowService.getWfNodeList(child2.getWfMain().getWfm_id());
			WfNode node = null;
			for(int j=0; j<nodeList.size(); j++){
				node =  nodeList.get(j);
				node.setChild_name(child2.getWfc_cname()+"<父>");
				list.add(node);
			}
		}

		
		//获取其子流程
		List<WfChild> childList = workflowBasicFlowService.findWfChildListByWfPid(wfUId);
		WfChild child = null;
		for(int i=0; i<childList.size(); i++){
			child = childList.get(i);
			List<WfNode> nodeList = workflowBasicFlowService.getWfNodeList(child.getWfc_cid());
			WfNode node = null;
			for(int j=0; j<nodeList.size(); j++){
				node =  nodeList.get(j);
				node.setChild_name(child.getWfc_cname()+"<子 >");
				list.add(node);
			}
		}
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("ids", ids);
		return "getChildWfNodeList";
	}
	
	public String showAllWfList(){

		//获取用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String siteId = getRequest().getParameter("siteId");
		String isAdmin = getRequest().getParameter("isAdmin");
		String chooseSiteId = getRequest().getParameter("chooseSiteId");
		
		//查询用户所在的角色
		List<EmployeeRole> roleList = employeeRoleService.findEmployeeRoleById(emp.getEmployeeGuid());
		int allCount = employeeRoleService.findAllWfMainRoleCount();
		String roleIds = "";
		EmployeeRole employeeRole = null;
		if(roleList!=null && roleList.size()>0){
			for(int i=0; i<roleList.size();i++){
				employeeRole = roleList.get(i);
				roleIds += "'"+employeeRole.getId()+"'";
				if(i!=roleList.size()-1){
					roleIds +=",";
				}
			}
		}
		if(allCount==0){
			roleIds = "all";
		}
		//分页相关，代码执行顺序不变
		String conditionSql = "";		//查询检索条件
		//工作流名称
		String wfname=getRequest().getParameter("wfname");
		if(wfname!=null && !wfname.equals("")){
			conditionSql += " and t.vc_sxmc like '%"+wfname+"%'";
		}
		if(siteId!=null && !siteId.equals("")){
			conditionSql += " and t.vc_ssbmid = '"+siteId+"'";
			getRequest().setAttribute("siteId", siteId);
		}
		if(chooseSiteId!=null && !chooseSiteId.equals("")){
			conditionSql += " and t.vc_ssbmid = '"+chooseSiteId+"'";
			getRequest().setAttribute("chooseSiteId", chooseSiteId);
		}
		getRequest().setAttribute("isAdmin",isAdmin);
		List<Department> siteIds = departmentService.queryAllSite();
		getRequest().setAttribute("siteIds", siteIds);
		
//		int count=workflowBasicFlowService.getCountFromWfMain(roleIds, conditionSql );
		int count = workflowBasicFlowService.getCountFromWfItem(roleIds,conditionSql);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Paging.setPagingParams(getRequest(), pageSize, count);
		//List<WfMain> list = workflowBasicFlowService.getBasicFlowList(roleIds, Paging.pageIndex,Paging.pageSize);
//		List<Object[]> list = workflowBasicFlowService.getBasicFlowList(roleIds, conditionSql, Paging.pageIndex,Paging.pageSize);
		List<Object[]> list = workflowBasicFlowService.getItemList(roleIds, conditionSql, Paging.pageIndex,Paging.pageSize);
		List<WfMainJson> list_return=new ArrayList<WfMainJson>();
		for(Object[] main:list){
			WfMainJson wfMainJson=null;
			wfMainJson=new WfMainJson();
			wfMainJson.setWfm_id(main[0]==null?"":main[0].toString());
			wfMainJson.setWfm_name(main[1]==null?"":main[1].toString());
			wfMainJson.setWfm_createtime(main[3]==null?"":main[3].toString());
			wfMainJson.setWfm_modifytime(main[2]==null?"":main[2].toString());
			if("0".equals(main[4])){
				wfMainJson.setWfm_status("暂停");
			}else if("1".equals(main[4])){
				wfMainJson.setWfm_status("调试");
			}else if("2".equals(main[4])){
				wfMainJson.setWfm_status("运行");
			}
			List<WfNode> nodeList = workflowBasicFlowService.getWfNodeList(wfMainJson.getWfm_id());
			if(nodeList!=null && nodeList.size()>0){
				for(int i=0;i<nodeList.size();i++){
					if(nodeList.get(i).getWfn_staff()!=null){
						String staff = nodeList.get(i).getWfn_staff();
						String staffIds = "";
						if(CommonUtil.stringNotNULL(staff)){	//根据itemid查询
							String[] staffs = staff.split(",");
							for(String sta: staffs){
								staffIds += "'"+sta+"',";
							}
							if(staffIds!=null && staffIds.length()>0){
								staffIds = staffIds.substring(0, staffIds.length()-1);
							}
							List<RolePojo> roleListPj = new ArrayList<RolePojo>();
							for(int j=0;j<staffs.length;j++){
								RolePojo rp = new RolePojo();
								rp.setRoleId(staffs[j]);
								rp.setRoleName(workflowBasicFlowService.findRoleName(staffs[j]));
								rp.setRoleUserIds(workflowBasicFlowService.findRoleUserIds(staffs[j]));
								roleListPj.add(rp);
							}
							nodeList.get(i).setRoleList(roleListPj);
							
							//nodeList.get(i).setRoleName(workflowBasicFlowService.findRoleName(staff));
						}
					}
				}
				wfMainJson.setNodeList(nodeList);
			}else{
				wfMainJson.setNodeList(null);
			}
			List<ZwkjForm> formList = workflowBasicFlowService.findAllFormByLcId(wfMainJson.getWfm_id());
			if(formList!=null && formList.size()>0){
				wfMainJson.setFormList(formList);
			}else{
				wfMainJson.setFormList(null);
			}
			WfItem wfItem = tableInfoService.findItemByWorkFlowId(wfMainJson.getWfm_id());
			wfMainJson.setWfItem(wfItem);
			list_return.add(wfMainJson);
		}
		getRequest().setAttribute("list", list_return);
		getRequest().setAttribute("wfname", wfname);
		
		return "workflow_list";
	}
	
	public void getRoleInfoById(){
		String roleId = getRequest().getParameter("id");
		RolePojo rp = new RolePojo();
		rp.setRoleId(roleId);
		rp.setRoleName(workflowBasicFlowService.findRoleName(roleId));
		rp.setRoleUserIds(workflowBasicFlowService.findRoleUserIds(roleId));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("roleId", rp.getRoleId());
		jsonObject.put("roleUserIds", rp.getRoleUserIds());
		toPage(jsonObject.toString());
	}
}
