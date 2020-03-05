package cn.com.trueway.document.business.action;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.hibernate.lob.SerializableClob;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.business.docxg.client.service.DocExchangeClient;
import cn.com.trueway.document.business.docxg.client.support.GenUserKey;
import cn.com.trueway.document.business.docxg.client.vo.BaseDoc;
import cn.com.trueway.document.business.docxg.client.vo.DepRelationShip;
import cn.com.trueway.document.business.docxg.client.vo.DocExchangeVo;
import cn.com.trueway.document.business.docxg.client.vo.DocxgFieldMap;
import cn.com.trueway.document.business.model.DocBw;
import cn.com.trueway.document.business.model.RecDoc;
import cn.com.trueway.document.business.model.ReceiveProcessShip;
import cn.com.trueway.document.business.model.ReceiveXml;
import cn.com.trueway.document.business.service.FieldMatchingService;
import cn.com.trueway.document.business.service.ReceiveDocService;
import cn.com.trueway.document.business.service.SelectTreeService;
import cn.com.trueway.document.business.util.DocXgConst;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.service.FlowService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.DepartmentService;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
import cn.com.trueway.workflow.set.util.ClobToString;

public class ReceiveDocAction extends BaseAction{
	private static final long serialVersionUID = 6772954495852817985L;
	private Logger logger = Logger.getLogger(this.getClass());
	private ReceiveDocService receiveDocService;
	
	private DocExchangeClient docExchangeClient;
	
	private SelectTreeService selectTreeService;

	private ItemService itemService;
	
	private WorkflowBasicFlowService workflowBasicFlowService;
	
	private TableInfoService tableInfoService;
	
	private ZwkjFormService zwkjFormService;
	
	private DepartmentService departmentService;
	
	private FieldMatchingService fieldMatchingService;
	
	private AttachmentService attachmentService;
	
	private FlowService flowService;
	
	public FlowService getFlowService() {
		return flowService;
	}

	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}

	public ReceiveDocService getReceiveDocService() {
		return receiveDocService;
	}

	public void setReceiveDocService(ReceiveDocService receiveDocService) {
		this.receiveDocService = receiveDocService;
	}
	

	public void setDocExchangeClient(DocExchangeClient docExchangeClient) {
		this.docExchangeClient = docExchangeClient;
	}
	
	
	public SelectTreeService getSelectTreeService() {
		return selectTreeService;
	}

	public void setSelectTreeService(SelectTreeService selectTreeService) {
		this.selectTreeService = selectTreeService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}

	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}
	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public FieldMatchingService getFieldMatchingService() {
		return fieldMatchingService;
	}

	public void setFieldMatchingService(FieldMatchingService fieldMatchingService) {
		this.fieldMatchingService = fieldMatchingService;
	}
	
	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	/**
	 * 描述：待收列表<br>
	 *
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String tobeRecList(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		Department dep = departmentService.findDepartmentById(emp.getDepartmentGuid());
		/*
		 * departId  上级部门id 和 本部门id 如果还有上上级部门id 需要再查下部门表 暂时只支持3及部门
		 * */
		String departId = "'"+dep.getSuperiorGuid()+"','"+dep.getDepartmentGuid()+"'";	//获取当前人员的父机构Id
		List<String> deps = new ArrayList<String>();
		//获取机构匹配
		List<DepRelationShip> list = fieldMatchingService.getDepRelationShipListByDepId(departId);
		DepRelationShip ship = null;
		String docxg_depId = "";
		for(int i=0; list!=null && i<list.size(); i++){
			ship = list.get(i);
			if(ship!=null){
				docxg_depId = ship.getDocxg_depId();
				deps.add(docxg_depId);
			}
		}
		//通过参数DEPS获取待收列表前10条的数据返回到页面
		LinkedHashMap<String, DocExchangeVo> tobeRecevicedoc = null;
		List<ReceiveXml> xmlList = new ArrayList<ReceiveXml>();
		if(deps !=null ){
			xmlList = receiveDocService.findReceiveXmlList(deps);
			tobeRecevicedoc = receiveDocService.getToBeReceivedList(xmlList);
		}
		/*
		if(deps !=null ){
			tobeRecevicedoc = receiveDocService.getToBeReceivedList(deps);
		}*/
		int size=tobeRecevicedoc==null?0:tobeRecevicedoc.size();
		getRequest().setAttribute("tobereceivenum", size);
		getSession().setAttribute("doc_list", tobeRecevicedoc);
		getRequest().setAttribute("depName", getSession().getAttribute(MyConstants.DEPARMENT_NAME));
		deps = null;
		
		//所有事项名称
		List<WfItem> itemList = itemService.getItemList(emp.getDepartmentGuid());
		getRequest().setAttribute("itemList", itemList);
		return "tobeReceiveList";
		
	}
	
	/**
	 * 从公文交换处接收公文, 新增wfProcess过程信息, saveform
	 */
	public void receiveDocToWfProcess () throws Exception{
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		Department dep = departmentService.findDepartmentById(emp.getDepartmentGuid());
	    //String departId = dep.getSuperiorGuid();	//获取当前人员的父机构Id
		/*
		 * departId  上级部门id 和 本部门id 如果还有上上级部门id 需要再查下部门表 暂时只支持3及部门
		 * */
		String departId = "'"+dep.getSuperiorGuid()+"','"+dep.getDepartmentGuid()+"'";	//获取当前人员的父机构Id
		List<String> deps = new ArrayList<String>();
		//获取机构匹配
		List<DepRelationShip> list = fieldMatchingService.getDepRelationShipListByDepId(departId);
		DepRelationShip ship = null;
		String docxg_depId = "";
		for(int i=0; list!=null && i<list.size(); i++){
			ship = list.get(i);
			if(ship!=null){
				docxg_depId = ship.getDocxg_depId();
				deps.add(docxg_depId);
			}
		}
		Date nowTime = new Date();
		
		//通过参数DEPS获取待收列表前10条的数据返回到页面
		LinkedHashMap<String, DocExchangeVo> tobeRecevicedoc=null;
		
		List<ReceiveXml> xmlList = new ArrayList<ReceiveXml>();
		if(deps !=null ){
			xmlList = receiveDocService.findReceiveXmlList(deps);
			tobeRecevicedoc = receiveDocService.getToBeReceivedList(xmlList);
		}
		String idString = getRequest().getParameter("id"); // 获取 公文队列id
		DocExchangeVo doc = tobeRecevicedoc.get(idString);
		receiveDocService.addReceiveOneDoc(doc, idString);//收取一个公文
		RecDoc recDoc = receiveDocService.getRecDocByDocguid(idString);
		String instanceId = UuidGenerator.generate36UUID();
		//收取后直接插入待办列表中
		WfProcess newProcess = new WfProcess();
		newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
		newProcess.setWfInstanceUid(instanceId);
		newProcess.setNodeUid("default");
		newProcess.setFromUserId(userId);
		newProcess.setOwner(userId);
		newProcess.setApplyTime(nowTime);
		newProcess.setFinshTime(nowTime);
		newProcess.setIsOver(Constant.NOT_OVER);
		newProcess.setIsEnd(0);
		newProcess.setIsExchanging(0);
		//主送人ID及标识值
		newProcess.setUserUid(userId);
		newProcess.setIsMaster(1);
		newProcess.setIsShow(1);
		newProcess.setStepIndex(1);
		SendAttachments attachment =null;
		if(recDoc!=null){
			List<ReceiveAttachments> zwlist = recDoc.getAtts();
			List<ReceiveAttachmentsext> fjlist = recDoc.getAttsext();
			ReceiveAttachments receiveAttachment = null;
			for(int i=0; i<zwlist.size(); i++){
				attachment = new SendAttachments();
				receiveAttachment = zwlist.get(i);
				attachment.setDocguid(instanceId+"fj");
				attachment.setTitle(receiveAttachment.getFilename());
				attachment.setLocalation(receiveAttachment.getLocalation());
				attachment.setFilesize(receiveAttachment.getFilesize());
				attachment.setFilename(receiveAttachment.getFilename());
				attachment.setFiletime(receiveAttachment.getFiletime());
				attachment.setFiletype(receiveAttachment.getFiletype());
				attachment.setType("正文材料");
				attachmentService.addSendAtts(attachment);
			}
			ReceiveAttachmentsext receiveAttachmentsext = null;
			for(int i=0; i<fjlist.size(); i++){
				attachment = new SendAttachments();
				receiveAttachmentsext = fjlist.get(i);
				attachment.setDocguid(instanceId+"fj");
				attachment.setTitle(receiveAttachmentsext.getFilename());
				attachment.setLocalation(receiveAttachmentsext.getLocalation());
				attachment.setFilesize(receiveAttachmentsext.getFilesize());
				attachment.setFilename(receiveAttachmentsext.getFilename());
				attachment.setFiletime(receiveAttachmentsext.getFiletime());
				attachment.setFiletype(receiveAttachmentsext.getFiletype());
				attachment.setType("正文材料");
				attachmentService.addSendAtts(attachment);
			}
			
			ReceiveProcessShip receiveProcessShip = new ReceiveProcessShip();
			receiveProcessShip.setInstanceId(instanceId);
			receiveProcessShip.setRecId(recDoc.getDocguid());
			receiveProcessShip.setIdIndex(idString);
			receiveDocService.saveReceiveProcessShip(receiveProcessShip);
			ReceiveXml receiveXml = null;
			for(int i=0; xmlList!=null && i<xmlList.size(); i++){
				receiveXml = xmlList.get(i);
				receiveXml.setRecId(recDoc.getDocguid());
				receiveDocService.saveReceiveXml(receiveXml);
			}
		}
		String title = "";	//预先设置空标题
		newProcess.setProcessTitle(title);
		tableInfoService.save(newProcess);//下一步
		
		// 通知公文交换系统已收文
		if(recDoc != null){
			//获取本部门的验证码
			String userKey = GenUserKey.genUserKey(recDoc.getQueueXto());
			docExchangeClient.receiveDoc(userKey, idString);	
		}
		try{
			String message = "success"+";"+newProcess.getFormId()+";"+newProcess.getWfInstanceUid()+";"+newProcess.getWfProcessUid();
			getResponse().getWriter().print(message);
		} catch (IOException e) {
			getResponse().getWriter().print("fail");
		}
	}
	
	
	public String saveForm(String formId, String workFlowId, String nodeId,
			String instanceId, String itemId, String idIndex,
			LinkedHashMap<String, DocExchangeVo> tobeRecevicedoc){
		// 读取表单所设定的所有表
		List<FormTagMapColumn> tableNameAllLists = zwkjFormService.getTableNameByFormId(formId);
		// ========================列表类型=============================//
		List<FormTagMapColumn> haveLists = new ArrayList<FormTagMapColumn>();
		List<String> tableNameHaveList = new ArrayList<String>();
		// ========================非列表类型=============================//
		List<FormTagMapColumn> lists = new ArrayList<FormTagMapColumn>();
		List<String> tableNameList = new ArrayList<String>();
		// 列表和非列表类型的分开存入list中
		for (FormTagMapColumn ft : tableNameAllLists) {
			if (ft.getListId() != null && !("").equals(ft.getListId())) {
				haveLists.add(ft);
			} else {
				lists.add(ft);
			}
		}
		// 读取两个list中的tableName(去重之后的)
		for (int i = 0, n = haveLists.size(); i < n; i++) { // 列表型
			if (!tableNameHaveList.contains(haveLists.get(i).getTablename())) {
				tableNameHaveList.add(haveLists.get(i).getTablename());
			}
		}
		for (int i = 0, n = lists.size(); i < n; i++) { // 非列表型
			if (!tableNameList.contains(lists.get(i).getAssignTableName())) {
					tableNameList.add(lists.get(i).getAssignTableName());
				}
		}
		String value = "";// 页面回值
		String vc_title ="";
		try {
			value = this.getNotListTypeForm(tableNameList, value, formId, 
					 instanceId, formId, nodeId, workFlowId,itemId,idIndex, tobeRecevicedoc);
			vc_title = getTitle(workFlowId, formId, instanceId).split(";")[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vc_title;
	}
	
	/**
	 * 得到流程的标题和所对应的字段
	 */
	public String getTitle(String workFlowId, String formId, String instanceId) {
		WfMain wf_main = workflowBasicFlowService.getWfMainById(workFlowId);
		String title = getTitle(wf_main, instanceId);// {标题}test---找到手动输入的值
		String name = tableInfoService.getTableAndColumnName(wf_main.getWfm_title_column());// tableName;columnName#tableName;columnName
		// 查找列名,用于页面判断
		String columnName = "";
		// String columnValue = "";
		String[] names = name.split("#");
		for (String str : names) {
			columnName += str.split(";")[1] + ",";
		}
		if (!("").equals(columnName) && columnName.length() > 0) {
			columnName = columnName.substring(0, columnName.length() - 1);
		}
		return title + ";" + columnName;
	}
	
	

	public String getTitle(WfMain wfMain, String instanceId) {
		String str = wfMain.getWfm_title_name();
		String tablename = wfMain.getWfm_title_table();
		String names = wfMain.getWfm_title_name();
		String ids = wfMain.getWfm_title_column();

		WfTableInfo table = tableInfoService.getTableById(tablename);
		if (table != null) {
			tablename = table.getVc_tablename();
		}
		// 解析标题字符串 2013年{姓名}3月{性别}4日
		List<String> cloumnNames = new ArrayList<String>();

		String regEx = "\\{[^\\}\\{]*\\}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(names);
		String tag = "";
		String all = "";
		while (m.find()) {
			// 标签和内容
			tag = m.group();
			if (tag != null && !tag.equals("")) {
				tag = tag.substring(1, tag.length() - 1);
				cloumnNames.add(tag);
			}
		}
		String[] idsArr = ids == null ? null : ids.split(",");

		if (cloumnNames != null) {
			for (int i = 0; i < cloumnNames.size(); i++) {
				WfFieldInfo filed = tableInfoService.getFieldById(idsArr[i]);
				String sql = "select " + filed.getVc_fieldname()
						+ ",'替代' from " + tablename + " t where t.INSTANCEID='"
						+ instanceId + "'";
				List<Object[]> list = tableInfoService.getListBySql(sql);
				String value = "";
				if (list != null && list.size() > 0) {
					String fieldtype = filed.getI_fieldtype() ;
					//clob字段需要处理
					if(fieldtype!=null && fieldtype.equals("3")){		
						value = ClobToString.clob2String((SerializableClob) list.get(0)[0]);
					}else{
						value = list.get(0)[0]==null?"":list.get(0)[0].toString();
					}
				}
				str = str.replace("{" + cloumnNames.get(i) + "}", value);
			}
		}
		return str;
	}
	
	
	public String getNotListTypeForm(List<String> tableNameList, String value, String oldformId,
			String instanceId, String formId, String nodeId, String workFlowId,
			String itemId, String idIndex, LinkedHashMap<String, DocExchangeVo> tobeRecevicedoc)
		throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//根据itemId到数据库中匹配关系
		List<DocxgFieldMap> list  =  fieldMatchingService.getDocxgFieldMapList(itemId);
		StringBuffer searchValue = new StringBuffer();
		String idString = idIndex;
		DocExchangeVo doc = tobeRecevicedoc.get(idString);
		//公文对象
		BaseDoc baseDoc = doc.getDocExchangeBox();
		Object model = baseDoc;
		Field[] fields = model.getClass().getDeclaredFields();//获取实体类的所有属性，返回filed数组
		Map<String,Object> tagValueList = new HashMap<String,Object>();
		for(int i=0; i<tableNameList.size(); i++){
			String tableName = tableNameList.get(i);
			DocxgFieldMap fieldMap = null;
			for(int j=0; j<list.size(); j++){
				fieldMap = list.get(j);
				String table = fieldMap.getTableName(); 
				if(table.equals(tableName)){
					String coloum = fieldMap.getTagName();
					String name  = fieldMap.getFieldName();
				//	System.out.println("--------------coloum="+coloum+ " name="+name);
					//反射获取每一个属性
					Object tagValue = null ;
					for (int k = 0; k < fields.length; k++) {				//遍历所有属性
						String fieldname = fields[k].getName();					//获取属性的名字
					//	System.out.println("-----------fieldname="+fieldname);
						String type = fields[k].getGenericType().toString();//获取属性的类型
						if(fieldname.equals(coloum)){
							if(type.equals("class java.lang.String")){
								Method m = model.getClass().getMethod("get"+coloum.substring(0,1).toUpperCase()+coloum.substring(1));
								tagValue = (String) m.invoke(model);   //调用getter方法获取属性值
							}else if(type.equals("class java.lang.Integer")){
								Method m = model.getClass().getMethod("get"+coloum.substring(0,1).toUpperCase()+coloum.substring(1));
								tagValue = (Integer) m.invoke(model);   //调用getter方法获取属性值
							}else if(type.equals("class java.util.Date")){
								Method m = model.getClass().getMethod("get"+coloum.substring(0,1).toUpperCase()+coloum.substring(1));
								if( m.invoke(model)!=null){
									tagValue = (Date) m.invoke(model);   //调用getter方法获取属性值
									tagValue=sdf.format(tagValue);
								}
							}else if(type.equals("class java.lang.Long")){
								Method m = model.getClass().getMethod("get"+coloum.substring(0,1).toUpperCase()+coloum.substring(1));
								if(coloum.equals("priority")){
									tagValue = (Long) m.invoke(model);   //调用getter方法获取属性值
									tagValue = DocXgConst.getPriorityNameByCode((Long) m.invoke(model));
								}else{
									tagValue = (Long) m.invoke(model);   //调用getter方法获取属性值
								}
							}
						}
						if(name!=null && !name.equals("") && !name.equals("null") && null!=tagValue){
							tagValueList.put(name, tagValue);
						}
					}
					if(name!=null && !name.equals("") && !name.equals("null")){
						searchValue.append(name + ":" + tagValue + ";");
					}
				}
			}
			if(tagValueList!=null && tagValueList.size()>0){
				tagValueList.put("workflowid", workFlowId);
				tagValueList.put("formid", formId);
				tagValueList.put("instanceid", instanceId);
				tagValueList.put("processid", UuidGenerator.generate36UUID());
			}
			fieldMatchingService.saveForm(tableName, tagValueList, instanceId);
		}
		
		if(searchValue!=null){
			value = searchValue.toString();
		}
		return value;
	}
	
	/**
	 * 描述：已收公文列表<br>
	 *
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String receivedDocList(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		Department dep = departmentService.findDepartmentById(emp.getDepartmentGuid());
		String departId = dep.getSuperiorGuid();	//获取当前人员的父机构Id
		String type = getRequest().getParameter("type");
		//文号
		String wh = (String) getRequest().getParameter("wh");
		//标题
		String title = (String) getRequest().getParameter("title");
		//来文单位
		String sendername = (String) getRequest().getParameter("sendername");
		//状态
		String statuskey = (String)getRequest().getParameter("statuskey");
		//时间类型：收文还是发文
		String timeType = (String)getRequest().getParameter("timeType");
		//开始时间
		String startTime = (String)getRequest().getParameter("startTime");
		//结束时间
		String endTime = (String)getRequest().getParameter("endTime");
		//关键字
		String keyword = (String) getRequest().getParameter("keyword");
		
		List<String> deps = new ArrayList<String>();
		//获取DEPARTMENTID
		if(departId!=null && !"".equals(departId)){
			deps.add(departId);
		}
		String conditionSql = "";
		int count = receiveDocService.getReceivedDocCount(conditionSql);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<RecDoc> list = receiveDocService.getReceivedDocList(conditionSql,Paging.pageIndex, Paging.pageSize);
		//sql循环一下,获取状态
		Map<String, ReceiveProcessShip> map = receiveDocService.findReceiveProcessShipList(list);
		
		getRequest().setAttribute("map", map);
		
		getRequest().setAttribute("docList", list);
		
		List<Department> departments = new ArrayList<Department>();
		if(departId!=null && !"".equals(departId)){
			departments.add(selectTreeService.findDepartmentById(departId));
		}else{
			deps = (List<String>) getSession().getAttribute(Constant.DEPARMENT_IDS);
			for(String id : deps){
				departments.add(selectTreeService.findDepartmentById(id));
			}
			if(departId!=null&&!"".equals(departId)){
				getRequest().setAttribute("deptName", selectTreeService.findDepartmentById(departId).getDepartmentName());
			}else{
				getRequest().setAttribute("deptName", "全部");
			}
		}
	
		//状态的常数
		getRequest().setAttribute("RECEIVE_WEICHULI",Constant.RECEIVE_WEICHULI);
		getRequest().setAttribute("RECEIVE_ZHENGBANLI",Constant.RECEIVE_ZHENGBANLI);
		getRequest().setAttribute("RECEIVE_BANJIE",Constant.RECEIVE_BANJIE);
		getRequest().setAttribute("RECEIVE_WUGUAN",Constant.RECEIVE_WUGUAN);
		getRequest().setAttribute("RECEIVE_TUIWEN",Constant.RECEIVE_TUIWEN);
		getRequest().setAttribute("RECEIVE_GUANLIAN",Constant.RECEIVE_GUANLIAN);
		//查询参数
		getRequest().setAttribute("deps", departments);
		getRequest().setAttribute("departmentId", departId);
		getRequest().setAttribute("type", type);
		getRequest().setAttribute("wh", wh);
		getRequest().setAttribute("title", title);
		getRequest().setAttribute("sendername", sendername);
		getRequest().setAttribute("statuskey", statuskey);
		getRequest().setAttribute("timeType", timeType);
		getRequest().setAttribute("startTime", startTime);
		getRequest().setAttribute("endTime", endTime);
		getRequest().setAttribute("keyword", keyword);
		String listType=getRequest().getParameter("listType");
		
		//所有事项名称
		List<WfItem> itemList = itemService.getItemList(emp.getDepartmentGuid());
		getRequest().setAttribute("itemList", itemList);
		
		if("show".equals(listType)){
			return "receivedDocListShow";
		}else{
			return "receivedDocList";
		}
	}

	/**
	 * 描述：已收公文详细<br>
	 *
	 * @return String
	 */
	public String receivedDocDetail(){
		String userId = (String) getSession().getAttribute("userId");
		String deptId = getRequest().getParameter("deptId");
		String docguid = getRequest().getParameter("id");
		RecDoc recDoc =receiveDocService.getRecDocByDocguid(docguid);
		receiveDocService.viewStatus(userId,recDoc);
		getRequest().setAttribute("recDoc", recDoc);
		getRequest().setAttribute("deptId", deptId);
		getRequest().setAttribute("departmentId", deptId);
		Department dept=selectTreeService.findDepartmentById(deptId);
		String deptName="";
		if(dept!=null){
			deptName= dept.getDepartmentName();
		}
		DocBw bw = new DocBw(); 
		bw.setDocGuid(docguid);
		//bw = processMonitorService.getBwDetail(bw);
		if(bw!=null){
			getRequest().setAttribute("bw", bw);
		}
		getRequest().setAttribute("deptName", deptName);
		String listType=getRequest().getParameter("listType");
		if("show".equals(listType)){
			return "receivedDocDetailShow";
		}else {
			return "receivedDocDetail";
		}
	}
	
	/**
	 * 
	 * 描述：进入待办
	 * 作者:蔡亚军
	 * 创建时间:2014-8-19 下午3:45:01
	 */
	public void innerPending(){
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		Department dep = departmentService.findDepartmentById(emp.getDepartmentGuid());
		String departId = "'"+dep.getSuperiorGuid()+"','"+dep.getDepartmentGuid()+"'";	//获取当前人员的父机构Id
		List<String> deps = new ArrayList<String>();
		//获取机构匹配
		List<DepRelationShip> list = fieldMatchingService.getDepRelationShipListByDepId(departId);
		DepRelationShip ship = null;
		String docxg_depId = "";
		for(int i=0; list!=null && i<list.size(); i++){
			ship = list.get(i);
			if(ship!=null){
				docxg_depId = ship.getDocxg_depId();
				deps.add(docxg_depId);
			}
		}
		String workFlowId = getRequest().getParameter("workFlowId");
		String itemId = getRequest().getParameter("itemId");
		String recId = getRequest().getParameter("recId");
		String message = "";
		if(recId!=null){
			ReceiveProcessShip receiveProcessShip = receiveDocService.findReceiveProcessShipByRecId(recId);
			if(receiveProcessShip!=null){
				WfNode wfNode = workflowBasicFlowService.findFirstNodeId(workFlowId);
				String formId = wfNode.getWfn_defaultform();
				String nodeId = wfNode.getWfn_id();
				String instanceId = receiveProcessShip.getInstanceId();
				List<WfProcess> wflist = tableInfoService.getProcessList(instanceId);
				int total = 0;
				WfProcess wfProcess = null;
				if(wflist!=null && wflist.size()>0){
					total =  wflist.size();
					if(total == 1){
						wfProcess = wflist.get(0);
					}
				}
				wfProcess.setNodeUid(wfNode.getWfn_id());	//插入node
				wfProcess.setWfUid(workFlowId);
				wfProcess.setItemId(itemId);
				wfProcess.setFormId(formId);
				wfProcess.setUserUid(userId);
				wfProcess.setUserDeptId(emp.getDepartmentGuid());
				wfProcess.setStatus("0");
				LinkedHashMap<String, DocExchangeVo> tobeRecevicedoc = null;
				 List<ReceiveXml> xmllist = receiveDocService.findReceiveXmlByRecId(recId);
				if(deps !=null ){
					tobeRecevicedoc = receiveDocService.getToBeReceivedList(xmllist);
				}
				String idIndex = receiveProcessShip.getIdIndex();
				//保存form值
				String title = saveForm(formId, workFlowId, nodeId, instanceId, itemId, idIndex, tobeRecevicedoc);
				wfProcess.setProcessTitle(title);
				//process保存数据库
				tableInfoService.update(wfProcess);
				//更新ship中的数据
				receiveProcessShip.setProcessId(wfProcess.getWfProcessUid());
				receiveDocService.updateReceiveProcessShip(receiveProcessShip);
				message =  "success"+";"+wfProcess.getFormId()+";"+wfProcess.getWfInstanceUid()+";"+wfProcess.getWfProcessUid();
			}
		}
		try{
			getResponse().getWriter().print(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void invaildPending(){
		String recId = getRequest().getParameter("recId");
		ReceiveProcessShip receiveProcessShip = receiveDocService.findReceiveProcessShipByRecId(recId);
		String processId = receiveProcessShip.getProcessId();
		WfProcess wfProcess = tableInfoService.getProcessById(processId);
		receiveProcessShip.setProcessId("");
		receiveDocService.updateReceiveProcessShip(receiveProcessShip);
		if(wfProcess!=null){
			String workFlowId = wfProcess.getWfUid();
			String instanceId = wfProcess.getWfInstanceUid();
			wfProcess.setNodeUid("default");	
			wfProcess.setWfUid("");
			wfProcess.setItemId("");
			wfProcess.setFormId("");
			tableInfoService.update(wfProcess);
			WfNode wfNode = workflowBasicFlowService.findFirstNodeId(workFlowId);
			String formId = wfNode.getWfn_defaultform();
			//根据formId查询表单
			ZwkjForm zwkjForm = zwkjFormService.getOneFormById(formId);
			String insert_table = zwkjForm.getInsert_table();
			fieldMatchingService.deleteTableInfo(instanceId, insert_table);
		}
		try{
			getResponse().getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
