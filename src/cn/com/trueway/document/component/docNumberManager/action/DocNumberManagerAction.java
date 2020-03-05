package cn.com.trueway.document.component.docNumberManager.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.velocity.runtime.directive.Define;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.document.business.service.SelectTreeService;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberModel;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberType;
import cn.com.trueway.document.component.docNumberManager.model.vo.DocNumber;
import cn.com.trueway.document.component.docNumberManager.model.vo.DocNumberSmallClass;
import cn.com.trueway.document.component.docNumberManager.service.DocNumberManagerService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.service.DepartmentService;


public class DocNumberManagerAction extends BaseAction{
	private static final long serialVersionUID = -1202497579376762782L;
	private Logger logger = Logger.getLogger(this.getClass());
	private DocNumberManagerService  docNumberManagerService;
	private SelectTreeService selectTreeService;
	private DepartmentService departmentService;
	
	public DepartmentService getDepartmentService() {
		return departmentService;
	}
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public SelectTreeService getSelectTreeService() {
		return selectTreeService;
	}
	public void setSelectTreeService(SelectTreeService selectTreeService) {
		this.selectTreeService = selectTreeService;
	}
	private DocNumberType docNumberType;

	public DocNumberType getDocNumberType() {
		return docNumberType;
	}
	public void setDocNumberType(DocNumberType docNumberType) {
		this.docNumberType = docNumberType;
	}
	public DocNumberManagerService getDocNumberManagerService() {
		return docNumberManagerService;
	}
	public void setDocNumberManagerService(
			DocNumberManagerService docNumberManagerService) {
		this.docNumberManagerService = docNumberManagerService;
	}
	
	/*----------------------------------------------文号类别维护----------------------------------------------------*/
	/**
	 * 
	 * 描述：文号大类维护列表页<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 上午09:19:08
	 */
	public String docNumberBigClass(){
		String name = getRequest().getParameter("name");
		
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		String conditionSql = "";//查询条件
		if(CommonUtil.stringNotNULL(name) || CommonUtil.stringNotNULL(name)){
			conditionSql = "and t.name like '%"+name+"%' " ;
		}
				
		int count = docNumberManagerService.getDocNumBigClasses(conditionSql);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<DocNumberType> docNumList = docNumberManagerService.getDocNumBigClassList(conditionSql, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("docNumList", docNumList);
		return "docNumberBigClass";
		
	}
	/**
	 * 
	 * 描述：根据id返回单个文号类型<br>
	 *
	 * @return String
	 *
	 */
	public String toSingledocNumClass(){
		String typeid = getRequest().getParameter("typeid");
		String isparent = getRequest().getParameter("isparent");
		DocNumberType type = docNumberManagerService.getSingleDocNumTypeById(typeid);
		getRequest().setAttribute("type", type);
		if(Constant.DOCNUM_CHILD.equals(isparent)){
			List<DocNumberType> docNumList = docNumberManagerService.getDocNumBigClassList("", Paging.pageIndex, Paging.pageSize);
			if(docNumList.size()==0)getRequest().setAttribute("msg", "请先增加大类文号");
			getRequest().setAttribute("bigTypeList",docNumList);
			return "docNumberSmallEdit";
		}
		return "docNumberBigEdit";
	}
	/**
	 * 
	 * 描述：文号类别管理<br>
	 *
	 * @throws IOException void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 上午10:00:47
	 */
	public void docNumTypeManage() throws IOException{
		DocNumberType type = new DocNumberType();
		type.setName(getRequest().getParameter("name"));
		type.setIsparent(getRequest().getParameter("isparent"));
		type.setParentid(getRequest().getParameter("parentid"));
		type.setTypeid(getRequest().getParameter("typeid"));
		type.setType(getRequest().getParameter("type"));
		type.setDoctype(getRequest().getParameter("doctype"));
		// 操作类型 add ， edit
		String op = getRequest().getParameter("op");
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		String workflowId = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		type.setWebid(workflowId);
		type.setCreateLogId(userId);
		if(op == null){
			op = "";
		}
		PrintWriter pw = getResponse().getWriter();
		pw.print(docNumberManagerService.editDocNumType(type,op));
		pw.close();
	}
	
	/**
	 * 
	 * 描述：删除大类文号<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 上午09:00:16
	 */
	public String bigDocNumdelete(){
		String[] typeids=getRequest().getParameterValues("typeid");
		String typeids_h=getRequest().getParameter("typeid_h");
		if(CommonUtil.stringNotNULL(typeids_h)){
			String[] typeidsAll;
			if(null!=typeids && typeids.length>0){
				typeidsAll=new String[typeids.length+1];
				for(int i=0;i<typeids.length;i++){
					typeidsAll[i]=typeids[i];
				}
				typeidsAll[typeids.length] = typeids_h;
			}else{
				typeidsAll=new String[1];
				typeidsAll[0]=typeids_h;
			}
			docNumberManagerService.deletebigWh(typeidsAll);
		}else{
			docNumberManagerService.deletebigWh(typeids);
		}
		return this.docNumberBigClass();
	}
	
	/**
	 * 
	 * 描述：文号小类维护列表页<br>
	 *
	 * @return String
	 *
	 */
	public String docNumberSmallClass(){
		String name=getRequest().getParameter("name");
		String parentid=getRequest().getParameter("parentid");
		String workflowId=getRequest().getParameter("workflowId");
		if(CommonUtil.stringIsNULL(workflowId)){
			workflowId = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		}
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = docNumberManagerService.getDocNumSmallClasses(name, parentid,workflowId);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<DocNumberSmallClass> docNumSmallList = docNumberManagerService.getDocNumSmallClass(Paging.pageIndex, Paging.pageSize ,name, parentid,workflowId);
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("parentid", parentid);
		getRequest().setAttribute("docNumSmallList", docNumSmallList);
		getRequest().setAttribute("allbigtype", docNumberManagerService.getDocNumBigClassList("", Paging.pageIndex, Paging.pageSize));
		return "docNumberSmallClass";
		
	}
	
	public String smallDocNumdelete(){
		String[] typeids=getRequest().getParameterValues("typeid");
		String typeids_h=getRequest().getParameter("typeid_h");
		if(CommonUtil.stringNotNULL(typeids_h)){
			String[] typeidsAll;
			if(null!=typeids && typeids.length>0){
				typeidsAll=new String[typeids.length+1];
				for(int i=0;i<typeids.length;i++){
					typeidsAll[i]=typeids[i];
				}
				typeidsAll[typeids.length] = typeids_h;
			}else{
				typeidsAll=new String[1];
				typeidsAll[0]=typeids_h;
			}
			docNumberManagerService.deletesmallWh(typeidsAll);
		}else{
			docNumberManagerService.deletesmallWh(typeids);
		}
		
		return this.docNumberSmallClass();
	}
	
	/*----------------------------------------------文号实例维护----------------------------------------------------*/
	/**
	 * 
	 * 描述：生成文号实例<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 上午11:30:34
	 * @throws IOException 
	 */
	public void addModel() throws IOException{
		String deptStr = (String) getRequest().getSession().getAttribute(MyConstants.DEPARMENT_ID);
		String depts[] = deptStr.replaceAll("'", "").split(",");
		String webid = depts[depts.length-2];
		//获取当前登录用户	
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		//流程id
		String workflowId = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String modelstr = getRequest().getParameter("amodel").trim();
		String allmodelstr = getRequest().getParameter("modelstr");
		
		List<DocNumberModel> list=docNumberManagerService.getWHModelsByContent(allmodelstr, webid, workflowId);
		//判断文号实例是否重复
		String msg = null;
		if (list==null||list.size()==0) {
			try {
				docNumberManagerService.addDocNumModel(userId, allmodelstr, webid, modelstr,workflowId);
				msg = "文号实例添加成功!";
			} catch (Exception e) {
				logger.error("文号实例添加失败");
				logger.error(e);
				msg = "文号实例添加失败!";
			}
		}else {
			msg = "该文号实例已存在,请重新生成!";
		}
		this.getResponse().getWriter().write(msg);
	}

	/**
	 * 
	 * 描述：文号实例维护页面<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午04:38:38
	 */
	public String docNumberModelManage(){
		String deptStr = (String) getRequest().getSession().getAttribute(MyConstants.DEPARMENT_ID);
		String depts[] = deptStr.replaceAll("'", "").split(",");
		String webid = depts[depts.length-2];
		String workflowId=getRequest().getParameter("workflowId");
		if(CommonUtil.stringIsNULL(workflowId)){
			workflowId = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		}
		getRequest().getSession().setAttribute(MyConstants.workflow_session_id,workflowId);
		String content=getRequest().getParameter("content");
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = docNumberManagerService.getDocNumModelCount(webid,content,workflowId);
		Paging.setPagingParams(getRequest(), pageSize, count);
		
		List<DocNumberModel> docNumModelList = docNumberManagerService.getDocNumModel(webid, content,Paging.pageIndex, Paging.pageSize, workflowId);
		docNumModelList = listMatch(docNumModelList);
		getRequest().setAttribute("content", content);
		getRequest().setAttribute("docNumModelList", docNumModelList);
		getRequest().setAttribute("workflowId", workflowId);
		getRequest().setAttribute("bindAlready", docNumberManagerService.getBindDocNumModelIds(workflowId));
		return "docNumberModelManage";
	}
	
	/**
	 * 对list中字段进行正则处理
	 * 描述：TODO 对此方法进行描述
	 * @param docNumModelList
	 * @return List<DocNumberModel>
	 * 作者:季振华
	 * 创建时间:2016-3-3 上午9:55:52
	 */
	private List<DocNumberModel> listMatch(List<DocNumberModel> docNumModelList){
		List<DocNumberModel> list = new ArrayList<DocNumberModel>();
		String regex = "^.*;";
		Pattern p=Pattern.compile(regex);
		for(DocNumberModel d:docNumModelList){
			Matcher m=p.matcher(d.getContent()==null?"":d.getContent());
			while(m.find()){
				String content = m.group().replace(",","");
				if(CommonUtil.stringNotNULL(content) && content.length()>0){
					d.setContent(content.substring(0,content.length()-1));
				}
			}
			list.add(d);
		}
		return list;
	}
	/**
	 * 
	 * 描述：文号模型创建<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午06:16:59
	 */
	public String toModelCreate(){
		String workflowId=getRequest().getParameter("workflowId");
		if(CommonUtil.stringIsNULL(workflowId)){
			workflowId = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		}
		//查询所有的文号
		List<DocNumberType> big = docNumberManagerService.getAllWhTypeByWebid(null, Constant.DOCNUM_PARENT,null);
		List<DocNumberType> small = docNumberManagerService.getAllWhTypeByWebid(workflowId,Constant.DOCNUM_CHILD,null);
		
		//拼接数据为传至前台所需的数据结构(大类嵌套子类)
		List<Map<String,Object>> allTypes=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < big.size(); i++) {
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("big", big.get(i));
			List<DocNumberType> list=new ArrayList<DocNumberType>();
			for (int j = 0,k = small.size(); j < k; j++) {
				if (small.get(j).getParentid().equals(big.get(i).getTypeid())) {
					list.add(small.get(j));
				}
			}
			m.put("small", list);
			m.put("smallcount", list.size());
			allTypes.add(m);
			
		}
		//返回大类子类嵌套数据给前台
		getRequest().setAttribute("whtypes", allTypes);
		return "toModelCreate";
	}
	
	/**
	 * 
	 * 描述：更新文号实例<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午05:01:33
	 */
	public String toUpdateModel(){
		String modelid=getRequest().getParameter("modelid");
		getRequest().setAttribute("whModel", docNumberManagerService.getDocNumModelById(modelid));
		return "toUpdateModel";
	}
	
	/**
	 * 
	 * 描述：删除实例模型<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午05:07:52
	 */
	public String deleteModels(){
		String ids=getRequest().getParameter("ids");
		docNumberManagerService.deleteDocNumModels(ids);
		return "deleteModels";
	}
	
	/**
	 * 
	 * 描述：更新文号实例<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午05:08:26
	 * @throws IOException 
	 */
	public void updateModel() throws IOException{
		boolean isEmpty=false;
		if (getRequest().getParameter("whSort")!=null&&!getRequest().getParameter("whSort").trim().equals("")) {
			isEmpty=true;
		}
		String modelid = getRequest().getParameter("modelid").trim();
		DocNumberModel whModel = docNumberManagerService.getDocNumModelById(modelid);
		String msg = null;
		if (isEmpty) {
			whModel.setModelindex(Integer.parseInt(getRequest().getParameter("whSort").trim()));
			msg = "修改成功";
		}
		whModel.setModelid(modelid);
		docNumberManagerService.updateWhModel(whModel);
		this.getResponse().getWriter().write(msg);
	}
	
	/*----------------------------------------------文号流程绑定----------------------------------------------------*/
	/**
	 * 
	 * 描述： 转向流程列表页,用于绑定文号<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-12 上午11:15:21
	 */
	public String define4bind(){
//		List<String> deps = new ArrayList<String>();
//		deps = (List<String>) getSession().getAttribute(MyConstants.DEPARMENT_IDS);
//		List<Define> defines = docNumberManagerService.getAllDefines(deps);
//		getRequest().setAttribute("defines", defines);
		//流程id
		String workflowId = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		getRequest().setAttribute("defines", workflowId);
		return "define4bind";
	}
	
	/**
	 * 
	 * 描述： 转向查询所有文号页面 用于绑定办文流程<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-12 上午11:15:21
	 */
	public String docNumberBindDefine(){
		//流程id
		String workflowId = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		//查询和选中流程绑定的文号
		getRequest().setAttribute("whdocs",docNumberManagerService.getBindDocNumModelIds(workflowId));
		
		String deptStr = (String) getRequest().getSession().getAttribute(MyConstants.DEPARMENT_ID);
		String depts[] = deptStr.replaceAll("'", "").split(",");
		String webid = depts[depts.length-2];
		
		getRequest().setAttribute("whmodels", docNumberManagerService.getAllWhModelByWebid(webid,workflowId));
		getRequest().setAttribute("workflowId",workflowId);
		return "docNumberBindDefine";
	}
	/**
	 * 
	 * 描述：文号与流程绑定<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-12 上午10:48:51
	 */
	public String bindDocNumDoc(){
		String defineid=getRequest().getParameter("defineid");
		String whmodelids=getRequest().getParameter("whmodelids"); 
		String allmodelids = getRequest().getParameter("allmodelids"); 
		docNumberManagerService.addDocNumberDocs(defineid, whmodelids,allmodelids);
		getRequest().setAttribute("mes", "文号绑定流程成功");
		return docNumberBindDefine();
	}
	
	public void startOrStopDoc(){
		String workFlowId=getRequest().getParameter("workFlowId");
		String whmodelids=getRequest().getParameter("whmodelid"); 
		docNumberManagerService.addOrDelDoc(whmodelids, workFlowId);
	}
	
	/*----------------------------------------------文号维护----------------------------------------------------*/
	/**
	 * 
	 * 描述：跳转到增加文号页面<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-16 下午02:18:45
	 */
	@SuppressWarnings("unchecked")
	public String todocNumAdd(){
		String define = getRequest().getParameter("define");
		//取得session中存的单位ID
		List<String> deps = new ArrayList<String>();
		deps = (List<String>) getSession().getAttribute(Constant.DEPARMENT_IDS);
		List<Department> departments = new ArrayList<Department>();
		for(String depid : deps){
			departments.add(selectTreeService.findDepartmentById(depid));
		}
		getRequest().setAttribute("define", define);
		getRequest().setAttribute("departments", departments);
		return "docNumAdd";
	}
	/**
	 * 
	 * 描述：文号增加<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-24 上午10:12:17
	 */
	@SuppressWarnings("unchecked")
	public String docNumAdd(){
		String define = getRequest().getParameter("define");
		String docNum = getRequest().getParameter("docNum");
		String title = getRequest().getParameter("title");
		String deparementId = getRequest().getParameter("deparementId");
		String time = getRequest().getParameter("time");
		String ngr=getRequest().getParameter("ngr");
		String security=getRequest().getParameter("security");
		String ngrbm=getRequest().getParameter("ngrbm");
		List<String> deps = (List<String>) getSession().getAttribute(Constant.DEPARMENT_IDS);
		
		boolean flag = docNumberManagerService.addDocNum(define, docNum, title,time, deparementId,ngr,security,ngrbm);
		if(flag){
			getRequest().setAttribute("msg", "新增成功");
		}else{
			getRequest().setAttribute("title", title);
			getRequest().setAttribute("deparementId", deparementId);
			getRequest().setAttribute("docnum", docNum);
			getRequest().setAttribute("time", time);
			getRequest().setAttribute("msg", "新增失败，请重试");
		}
		
		List<Department> departments = new ArrayList<Department>();
		for(String depid : deps){
			departments.add(selectTreeService.findDepartmentById(depid));
		}
		getRequest().setAttribute("define", define);
		getRequest().setAttribute("departments", departments);
		return "docNumAdd";
	}
	
	/**
	 * 
	 * 描述：文号维护<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-15 下午03:07:51
	 */
	public String docNumManage(){
		//String webid = /*(String)getSession().getAttribute(Constant.WEB_ID)*/"sfb";
//		List<String> deps = new ArrayList<String>();
//		deps = (List<String>) getSession().getAttribute(Constant.DEPARMENT_IDS);
		String type = getRequest().getParameter("type");
		String year = getRequest().getParameter("year");
		String lwdwlx = getRequest().getParameter("lwdwlx");
		String isused = getRequest().getParameter("isused");
		String number = getRequest().getParameter("number");
		String define = getRequest().getParameter("define");
		String title = getRequest().getParameter("title");
		String order = getRequest().getParameter("order");
		String model = getRequest().getParameter("model");
		
		//保存页码的参数的名称
		String pageIndexParamName = new ParamEncoder("element").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
		getRequest().setAttribute("pageIndexParamName", pageIndexParamName);
		//获取页码，为分页查询做准备
		String pageNum = getRequest().getParameter(pageIndexParamName);
//		int currentPage = GenericValidator.isBlankOrNull(pageNum) ? 1 : Integer.parseInt(pageNum);
		
		int pageSize=Integer.valueOf(SystemParamConfigUtil.getParamValueByParam("pageSize"));
		DocNumber dn = new DocNumber();
		//dn.setDefine(define);
		dn.setIsused(isused);
		dn.setYear(year);
		dn.setType(type);
		dn.setNumber(number);
		dn.setTitle(title);
		dn.setLwdwlx(lwdwlx);
		
		//得到所有的流程
//		List<Define> defines = docNumberManagerService.getAllDefines(deps);
//		if(define!=null&&define.trim().length()!=0){
//			for(Define def:defines){
//				if(define.equals(def.getWfUid())){
//					dn.setDefine(def.getDefineType());
//					break;
//				}
//			}
//			DTPageBean dt = docNumberManagerService.findDocNum(dn, currentPage, pageSize,order);
//			getRequest().setAttribute("records", dt.getDataList());
//			getRequest().setAttribute("pages", dt.getTotalPages());
//			getRequest().setAttribute("pageSize", dt.getNumPerPage());
//			getRequest().setAttribute("recordSize", dt.getTotalRows());
//		}else{
//			define=defines.get(0).getWfUid();
//		}
		
		getRequest().setAttribute("model", model==null?"":model);
		getRequest().setAttribute("dn", dn);
		getRequest().setAttribute("order", order);
//		getRequest().setAttribute("defines", defines);
		getRequest().setAttribute("define", define);
		return "docNumManager";
	}
	
	public void getModel()throws IOException {
		String define = getRequest().getParameter("define");
		PrintWriter pw = null;
		try {
			pw = getResponse().getWriter();
			String model = docNumberManagerService.getModel(define);
			pw.write(model);
		} catch (Exception e) {
			logger.error(e);
			logger.error("根据流程取得模型时出错");
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
	
	public void paserModel() throws IOException {
		String model = getRequest().getParameter("dnmodel");
		PrintWriter pw = null;
		try {
			pw = getResponse().getWriter();
			String str = docNumberManagerService.paserModel(model);
			pw.print(str);
		} catch (Exception e) {
			logger.error(e);
			logger.error("解析模型时出错");
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
	
	/**
	 * 
	 * 描述：文号维护导出excel<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-24 上午10:07:01
	 */
	@SuppressWarnings("unchecked")
	public String exportExcl(){
		String type = getRequest().getParameter("type");
		String year = getRequest().getParameter("year");
		String lwdwlx = getRequest().getParameter("lwdwlx");
		String define = getRequest().getParameter("define");
		String order = getRequest().getParameter("order");
		List<String> deps = new ArrayList<String>();
		deps = (List<String>) getSession().getAttribute(Constant.DEPARMENT_IDS);
		
		DocNumber dn = new DocNumber();
		dn.setYear(year);
		dn.setType(type);
		dn.setLwdwlx(lwdwlx);
		List<Define> defines = docNumberManagerService.getAllDefines(deps);
//		if(define!=null&&define.trim().length()!=0){
//			for(Define def:defines){
//				if(define.equals(def.getWfUid())){
//					dn.setDefine(def.getDefineType());
//					break;
//				}
//			}
//		}
		String[] titles = new String[]{"文号","使用情况","标题","拟稿科室","拟稿人","密级","时间"}; 
		DTPageBean dt = docNumberManagerService.findDocNum(dn, 0, Integer.MAX_VALUE,order);
		List<DocNumber> dnlist= (List<DocNumber>)dt.getDataList();
		List<String[]> dataSource=new ArrayList<String[]>();
		for(DocNumber d: dnlist){
			String docNum = "";
			String isuse = "";
			String miji="";
			if(Constant.DEFINE_TYPE_DO.equals(dn.getDefine())){
				docNum += "["+d.getYear()+"]";
				docNum += d.getType();
				if(d.getLwdwlx()!=null){
					docNum += d.getLwdwlx();
				}
				docNum += d.getNumber()+"号";
			}
			if(Constant.DEFINE_TYPE_SEND.equals(dn.getDefine())){
				docNum += d.getType();
				docNum += "["+d.getYear()+"]";
				if(d.getLwdwlx()!=null){
					docNum += d.getLwdwlx();
				}
				docNum += d.getNumber()+"号";
			}
			if(Constant.DOCNUM_USE.equals(d.getIsused())){
				isuse = "已使用";
			}else{
				isuse = "未使用";
			}
			if(d.getSecurity()!=null){
				if("2".equals(d.getSecurity())){
					miji="秘密";
				}else if("1".equals(d.getSecurity())){
					miji="机密";
				}else if("0".equals(d.getSecurity())){
					miji="绝密";
				}else if("3".equals(d.getSecurity())){
					miji="";
				}else{
					miji=d.getSecurity();
				}
			}
			String[] s= new String[]{docNum,isuse,d.getTitle()==null?"":d.getTitle(),d.getNgrbm()==null?"":d.getNgrbm(),d.getNgr()==null?"":d.getNgr(), miji,d.getTime()==null?"":d.getTime().toString()};
			dataSource.add(s);
		}
//		ExcelExport e = ExcelExport.getExcelExportInstance("文号表",getResponse());
//		e.setTitles(titles);
//		e.setDataSource(dataSource);
//		e.setColWidth(1, 30);
//		e.setColWidth(2, 14);
//		e.setColWidth(3, 80);
//		e.setColWidth(4, 20);
//		e.setColWidth(5, 20);
//		e.setColWidth(6, 10);
//		e.setColWidth(7, 20);
//		e.export();
		return null;
	}
	
	/*----------------------------------------------文号策略维护----------------------------------------------------*/
	
	public String toStrategyList(){
		getRequest().setAttribute("slist", (List<DocNumberStrategy>)docNumberManagerService.getStrategyList());
		return "toStrategyList";
	}
	
	public String toStrategyManageList(){
		getRequest().setAttribute("slist", (List<DocNumberStrategy>)docNumberManagerService.getStrategyList());
		return "toStrategyManageList";
	}
	
	public String editStrategy(){
		String id = getRequest().getParameter("id");
		String content = getRequest().getParameter("content");
		String type = getRequest().getParameter("type");
		String description = getRequest().getParameter("description");
		
		if(id!=null&&id.trim().length()!=0){
			docNumberManagerService.updateStrategy(id, content, type, description);
		}
		/*else{
			docNumberManagerService.addStrategy(content, type, description);
		}*/
		
		return toStrategyManageList();
	}
	public String delStrategy(){
		String id = getRequest().getParameter("id");
		
		if(id!=null&&id.trim().length()!=0){
			docNumberManagerService.delStrategy(id);
		}
		
		return toStrategyManageList();
	}
	public String dj(){
//		List deps = (List<String>) getSession().getAttribute(Constant.DEPARMENT_IDS);
//		List<Define> defines = docNumberManagerService.getAllDefines(deps);
//		for(Define d : defines){
//			if(d.getDefineType().equals(Constant.DEFINE_TYPE_SEND)){
//				getRequest().setAttribute("defineId", d.getWfUid());
//			}
//		}
		return "dj";
	}
}
