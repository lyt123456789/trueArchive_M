package cn.com.trueway.workflow.core.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.FileUtils;
import cn.com.trueway.base.util.MyUtils;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentTypeFZDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.AttachmentTypeWfMainShip;
import cn.com.trueway.workflow.core.dao.FormPermitDao;
import cn.com.trueway.workflow.core.dao.LabelDao;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.dao.TemplateDao;
import cn.com.trueway.workflow.core.dao.WorkflowBasicFlowDao;
import cn.com.trueway.workflow.core.dao.WorkflowNoteDao;
import cn.com.trueway.workflow.core.dao.WorkflowSoftRouteDao;
import cn.com.trueway.workflow.core.pojo.BasicFlow;
import cn.com.trueway.workflow.core.pojo.Node;
import cn.com.trueway.workflow.core.pojo.SoftRoute;
import cn.com.trueway.workflow.core.pojo.WfBackNode;
import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.core.pojo.WfFormPermit;
import cn.com.trueway.workflow.core.pojo.WfLabel;
import cn.com.trueway.workflow.core.pojo.WfLine;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.pojo.WfTemplate;
import cn.com.trueway.workflow.core.pojo.WfXml;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.dao.GroupDao;
import cn.com.trueway.workflow.set.dao.ZwkjFormDao;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.InnerUser;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.util.SqlFileUtil;

public class WorkflowBasicFlowServiceImpl implements WorkflowBasicFlowService {

	private WorkflowNoteDao 		workflowNoteDao;
	private WorkflowSoftRouteDao 	workflowSoftRouteDao;
	private WorkflowBasicFlowDao 	workflowBasicFlowDao;
	private TableInfoDao 			tableInfoDao;
	private ZwkjFormDao 			zwkjFormDao;
	private TemplateDao 			wftemplateDao;
	private LabelDao 				labelDao;
	private GroupDao 				groupDao;
	private FormPermitDao  			formPermitDao;
	private AttachmentTypeFZDao 	attachmentTypeFZDao;
	private TemplateDao 			templateDao;
	
	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public WorkflowNoteDao getWorkflowNoteDao() {
		return workflowNoteDao;
	}

	public void setWorkflowNoteDao(WorkflowNoteDao workflowNoteDao) {
		this.workflowNoteDao = workflowNoteDao;
	}

	public WorkflowSoftRouteDao getWorkflowSoftRouteDao() {
		return workflowSoftRouteDao;
	}

	public void setWorkflowSoftRouteDao(
			WorkflowSoftRouteDao workflowSoftRouteDao) {
		this.workflowSoftRouteDao = workflowSoftRouteDao;
	}

	public WorkflowBasicFlowDao getWorkflowBasicFlowDao() {
		return workflowBasicFlowDao;
	}

	public void setWorkflowBasicFlowDao(
			WorkflowBasicFlowDao workflowBasicFlowDao) {
		this.workflowBasicFlowDao = workflowBasicFlowDao;
	}
	
	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	public ZwkjFormDao getZwkjFormDao() {
		return zwkjFormDao;
	}

	public void setZwkjFormDao(ZwkjFormDao zwkjFormDao) {
		this.zwkjFormDao = zwkjFormDao;
	}

	public TemplateDao getWftemplateDao() {
		return wftemplateDao;
	}

	public void setWftemplateDao(TemplateDao wftemplateDao) {
		this.wftemplateDao = wftemplateDao;
	}

	public LabelDao getLabelDao() {
		return labelDao;
	}

	public void setLabelDao(LabelDao labelDao) {
		this.labelDao = labelDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public FormPermitDao getFormPermitDao() {
		return formPermitDao;
	}

	public void setFormPermitDao(FormPermitDao formPermitDao) {
		this.formPermitDao = formPermitDao;
	}

	public AttachmentTypeFZDao getAttachmentTypeFZDao() {
		return attachmentTypeFZDao;
	}

	public void setAttachmentTypeFZDao(AttachmentTypeFZDao attachmentTypeFZDao) {
		this.attachmentTypeFZDao = attachmentTypeFZDao;
	}

	public void delete(String id) {
		workflowBasicFlowDao.delete(id);
	}

	public void deleteFromWEBID(String webId) {
		workflowBasicFlowDao.deleteFromWEBID(webId);
	}

	public void deleteFromWFIDForNode(String wfid) {
		workflowNoteDao.deleteFromWFID(wfid);
	}

	public void deleteFromWFIDForSoftRoute(String wfid) {
		workflowSoftRouteDao.deleteFromWFID(wfid);
	}

	public void deleteNode(String nodeid) {
		workflowSoftRouteDao.delete(nodeid);
	}

	public void deleteSoftRoute(String routeid) {
		workflowNoteDao.delete(routeid);
	}
	
	public BasicFlow get(String id) {
		return workflowBasicFlowDao.get(id);
	}

	public List<Object[]> getBasicFlowList(String roleIds, String conditionSql, Integer pageindex, Integer pagesize) {
		return workflowBasicFlowDao.getBasicFlowList(roleIds, conditionSql, pageindex,pagesize);
	}

	public Node getNode(String nodeid) {
		return workflowNoteDao.get(nodeid);
	}

	public List<Node> getNodeList(String wfid) {
		return workflowNoteDao.getNodeList(wfid);
	}

	public List<SoftRoute> getRouteList(String wfid) {
		return workflowSoftRouteDao.getRouteList(wfid);
	}

	public SoftRoute getSoftRoute(String routeid) {
		return workflowSoftRouteDao.get(routeid);
	}

	public void save(Node node) {
		workflowNoteDao.save(node);
	}

	public void save(BasicFlow basicFlow) {
		workflowBasicFlowDao.save(basicFlow);
	}

	public void saveSoftRoute(SoftRoute route) {
		workflowSoftRouteDao.save(route);
	}

	public void update(Node node) {
		workflowNoteDao.update(node);
	}

	public void update(BasicFlow basicFlow) {
		workflowBasicFlowDao.update(basicFlow);
	}

	public void updateSoftRoute(SoftRoute route) {
		workflowSoftRouteDao.update(route);
	}
	public void saveWfMain(WfMain wfMain) {
		workflowBasicFlowDao.saveWfMain(wfMain);
	}

	public void deleteWfMain(String id) {
		workflowBasicFlowDao.deleteWfMain(id);
	}

	public WfNode getStartNode(String id) {
		return workflowBasicFlowDao.getStartNode(id);
	}

	public List<WfNode> getNode(String id, String node_id) {

		return workflowBasicFlowDao.getNode(id, node_id);
	}

	public void saveWorkFlow(WfMain main) {
		workflowBasicFlowDao.saveWorkFlow(main);
	}

	public WfMain getWfMainById(String id) {
		return workflowBasicFlowDao.getWfMainById(id);
	}

	public List<WfNode> showNode(String id, String node_id, String instanceId) {
		// 获取节点id,如果为空则说明搜寻的是开始节点
		List<WfNode> list = new ArrayList<WfNode>();
		if (null == node_id || node_id.equals("")) {
			// 查询出开始节点
			WfNode node = getStartNode(id);
			list.add(node);
		} else {
			// 找出该id节点下面的节点
			list = getNode(id, node_id);
		}
		List<WfNode> nodeList = new ArrayList<WfNode>();
		//节点,查询出,节点之间的线条信息
		WfNode wfNode = null;
		for(int i=0; i<list.size(); i++){
			wfNode = list.get(i);
			String nextNodeId = list.get(i).getWfn_id();
			WfLine wfLine = workflowBasicFlowDao.findWfLineByNodeId(id, nextNodeId, node_id);		//两个节点之间的线条信息
			if(wfLine!=null){
				String choice_condition = wfLine.getWfl_choice_condition();		//线条上判断条件
				String choice_rule = wfLine.getWfl_choice_rule();
				if(choice_condition!=null && !choice_condition.equals("")){		//存在线上条件
					boolean fhtj = checkNodeCondition(choice_condition, choice_rule, id, instanceId);	//是否符合条件
					if(fhtj){
						nodeList.add(wfNode);
					}
				}else{
					nodeList.add(wfNode);
				}
			}else{
				nodeList.add(wfNode);
			}
		}
		if(nodeList==null || nodeList.size()==0){
			nodeList = list;
		}
		return nodeList;
	}
	
	/**
	 * 
	 * 描述：
	 * @param condition
	 * @param wfUId
	 * @return boolean
	 * 作者:蔡亚军
	 * 创建时间:2014-12-24 下午2:28:12
	 */
	public boolean checkNodeCondition(String condition, String choice_rule, 
						String wfUId, String instanceId){
		if(instanceId!=null && !instanceId.equals("")){
			String[] rules = choice_rule.split(";");
			for(int i=0; i<rules.length; i++){
				String[] rule = rules[i].split("=");		//re=hyrs(int)[FFF284B7-1836-48A5-80DB-7B8E1F7C6720]
				String rel_name = rule[0];
				String[] field = rule[1].split("[|]");
				String fieldName = field[0];				//字段名称
				String tableName  =  field[1];				//表名
				List<Object> list= zwkjFormDao.findOfficeTableVal(fieldName, tableName, instanceId);
				if(list!=null){
					String value = list.get(0).toString();
					boolean isNum = false;
					try{
						Double.parseDouble(value);
						isNum = true;
					}catch(Exception e){
						e.getMessage();
					}
					if(isNum){
						condition = condition.replace(rel_name, value);
					}else{
						condition = condition.replace(rel_name, "\""+value+"\"");
					}
				}
			}
			//判断逻辑是否正确
			try {
				System.out.println("condition="+condition);
				Object eval = new ScriptEngineManager().getEngineByName("js").eval(condition);
				return (Boolean)eval;
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	public List<WfChild> showChildOfWf(String id, String node_id) {
		return workflowBasicFlowDao.showChildOfWf(id,node_id);
	}

	public void deleteWorkFlow(String id) {
		workflowBasicFlowDao.deleteWorkFlowById(id);
	}

	public WfNode getWfNode(String id) {
		return workflowBasicFlowDao.getWfNode(id);
	}

	public void updateWfMain(WfMain main) {
		workflowBasicFlowDao.updateWfMain(main);
	}

	public WfXml getWfXml(String id) {
		return workflowBasicFlowDao.getWfXml(id);
	}

	public void saveWfXml(WfXml wfXml) {
		workflowBasicFlowDao.saveWfXml(wfXml);
	}
	
	public void updateWfXml(WfXml wfXml){
		workflowBasicFlowDao.updateWfXml(wfXml);
	}

	public void deleteWfXml(String id) {
		workflowBasicFlowDao.deleteWfXml(id);
	}

	public WfNode findFirstNodeId(String workFlowId) {
		return workflowBasicFlowDao.findFirstNodeId(workFlowId);
	}

	public String findFormLocaltion(String formId) {
		return workflowBasicFlowDao.findFormLocaltion(formId);
	}

	public List<WfMain> getWfMainListByRetrieval(String wfname,String province,String begin_rq,String end_rq,Integer pageindex, Integer pagesize) {
		return workflowBasicFlowDao.getWfMainListByRetrieval(wfname, province, begin_rq,end_rq,pageindex,pagesize);
	}

	public WfNode findFormIdByNodeId(String nextNodeId) {
		return workflowBasicFlowDao.findFormIdByNodeId(nextNodeId);
	}
	
	public List<WfNode> getSortNode(String workflowId){
		return workflowBasicFlowDao.getSortNode(workflowId);
	}

	public int getCountFromWfMain(String roleIds, String conditionSql) {
		return workflowBasicFlowDao.getCountFromWfMain(roleIds, conditionSql);
	}

	public int getCountFromWfMainByRetrieval(String wfname, String province,
			String begin_rq, String end_rq) {
		return workflowBasicFlowDao.getCountFromWfMainByRetrieval(wfname, province, begin_rq, end_rq);
	}

	public List<WfMain> getWfMainList() {
		return workflowBasicFlowDao.getWfMainList();
	}

	// -- 判断是否可以删除工作流  是否有走工作流
	public int workFlowIsUserOrNot(String workFlowId) {
		return  workflowBasicFlowDao.getWfProcessCountByWfUId(workFlowId);
	}
	
	public List<WfNode> getNodesByPid(String pid){
		return workflowBasicFlowDao.getNodesByPid(pid);
	}

	public WfLine findWfLineByNodeId(String workFlowId,String nextNodeId,String nodeId) {
		return workflowBasicFlowDao.findWfLineByNodeId(workFlowId,nextNodeId,nodeId);
	}

	public int getCountDoFiles() {
		return workflowBasicFlowDao.getCountDoFiles();
	}

	public String findValue(String tableName, String columnName, String formId,String workFlowId,String instanceId) {
		return workflowBasicFlowDao.findValue(tableName,columnName,formId,workFlowId,instanceId);
	}

	public List<WfNode> getWfNodeList(String workFlowId) {
		return workflowBasicFlowDao.getWfNodeList(workFlowId);
	}

	public List<String> getAllProcedures() throws DataAccessException {
		return workflowBasicFlowDao.getAllProcedures();
	}

	public void save(WfBackNode wfBackNode) {
		workflowBasicFlowDao.save(wfBackNode);
	}

	public List<WfBackNode> getBackNodeListByWfId(String workflowId,String nextNodeId) {
		return workflowBasicFlowDao.getBackNodeListByWfId(workflowId,nextNodeId);
	}

	public void delete(String workflowId, String wfBackNodeId) {
		workflowBasicFlowDao.delete(workflowId,wfBackNodeId);
	}

	public List<WfBackNode> getBackNodeListByWfId(String workflowId) {
		return workflowBasicFlowDao.getBackNodeListByWfId(workflowId);
	}

	@Override
	public WfBackNode getBackNodeByBackNodeId(String workflowId, String backNodeId) {
		return workflowBasicFlowDao.getBackNodeByBackNodeId(workflowId,backNodeId);
	}

	public void update(WfBackNode wfbackNode) {
		workflowBasicFlowDao.update(wfbackNode);
	}

	public WfChild getWfChildByCid(String cid) {
		return workflowBasicFlowDao.getWfChildByCid(cid);
	}

	public List<Object[]> getListWf(String id) {
		return workflowBasicFlowDao.getListWf(id);
	}

	@Override
	public WfChild getWfChildById(String wfc_id) {
		return workflowBasicFlowDao.getWfChildById(wfc_id);
	}

	@Override
	public void deleteWfChild(String wfc_id) {
		workflowBasicFlowDao.deleteWfChild(wfc_id);
	}

	@Override
	public void saveWfChild(WfChild wfChild) {
		workflowBasicFlowDao.saveWfChild(wfChild);
	}

	@Override
	public List<WfLine> findNextWfLineByNodeId(String nextNodeId, String workFlowId) {
		return workflowBasicFlowDao.findNextWfLineByNodeId(nextNodeId,workFlowId);
	}

	@Override
	public WfChild getWfChildByModuleId(String moduleId, String workFlowId) {
		return workflowBasicFlowDao.getWfChildByModuleId(moduleId,workFlowId);
	}

	@Override
	public List<WfNode> getNextNodeByChildWf(String f_workflowId, String child_module) {
		return workflowBasicFlowDao.getNextNodeByChildWf(f_workflowId,child_module);
	}

	@Override
	public void updateNode(String wfn_pid, String wfn_id) {
		workflowBasicFlowDao.updateNode(wfn_pid,wfn_id);
	}

	@Override
	public void updateLine(String wfm_pid, String wfn_id) {
		workflowBasicFlowDao.updateLine(wfm_pid,wfn_id);
		
	}

	@Override
	public String getNodeNameByNodeIds(String nodeid) {
		List<WfNode> wfNodes = workflowBasicFlowDao.getNodesByids(nodeid);
		if(wfNodes!=null&&wfNodes.size()!=0){
			String name="";
			for(int i=0;i<wfNodes.size();i++){
				if(i!=0){
					name+=",";
				}
				WfNode wfNode = wfNodes.get(i);
				name +=wfNode.getWfn_name();
			}
			return name;
		}
		return "";
	}

	// 根据fromid 获取 pdf path
	public String findFormPdf(String formId) {
		return workflowBasicFlowDao.findFormPdf(formId);
	}

	@Override
	public WfNode findNodeById(String nodeId) {
		return workflowBasicFlowDao.findNodeById(nodeId);
	}

	@Override
	public WfChild getWfChildByPidAndCid(String wfc_cid, String wfc_pid) {
		return workflowBasicFlowDao.getWfChildByPidAndCid(wfc_cid, wfc_pid);
	}

	@Override
	public void addNodeToHistroy(String wfm_id) {
		workflowBasicFlowDao.addNodeToHistroy(wfm_id);
	}

	@Override
	public void addLineToHistroy(String wfm_id) {
		workflowBasicFlowDao.addLineToHistroy(wfm_id);
		
	}

	@Override
	public WfNode getWfNodeByModuleId(String moduleId, String workFlowId) {
		return workflowBasicFlowDao.getWfNodeByModuleId(moduleId, workFlowId);
	}
	
	@Override
	public WfMain addWorkFlow(WfMain main) {
		return workflowBasicFlowDao.addWorkFlow(main);
	}

	@Override
	public JSONObject copyWorkFlow(WfMain wfMain, String id) {
		JSONObject result = new JSONObject();
		String wfm_uId = "";
		try{
			wfm_uId = wfMain.getWfm_id();
			//1,将数据表  赋予 新的流程
			WfTableInfo  wfTable = new WfTableInfo();
			wfTable.setLcid(wfm_uId);
			List<WfTableInfo> list = tableInfoDao.getTableListForPage("", "", wfTable, null, null);
			WfTableInfo wfTableInfo = null;
			if(list!=null && list.size()>0){
				for(int i=0; i<list.size(); i++){
					wfTableInfo = list.get(i);
					String reflc = wfTableInfo.getReflc(); 
					reflc += ","+id+",";
					wfTableInfo.setReflc(reflc);
					tableInfoDao.addTable(wfTableInfo);
				}
			}
			//2,表单设置匹配关系
			//2.1,获取formList
			List<ZwkjForm> formlist = zwkjFormDao.getFormListByParamForPage
									("workflowId", wfm_uId, null, null);
			ZwkjForm zwkjForm  = null;
			Map<String, String> htmlMap = new HashMap<String, String>();
			String webRoot = PathUtil.getWebRoot();
			for(int i=0; formlist!=null && i<formlist.size(); i++){
				zwkjForm = formlist.get(i);
				String formId = zwkjForm.getId();
				String htmlPath = zwkjForm.getForm_htmlfilename();
				String jspPath = zwkjForm.getForm_jspfilename();
				//拼凑地址,文件转移
				String all_html = webRoot+"form/html/"+htmlPath;
				String all_jsp = webRoot+"form/jsp/"+jspPath;
				String htm_36 = UuidGenerator.generate36UUID();
				String jsp_36 = UuidGenerator.generate36UUID();
				File old_html = new File(all_html);
				File old_jsp  = new File(all_jsp);
				FileUploadUtils.copy(old_html, new File(webRoot+"form/html/"+htm_36+".html"));
				FileUploadUtils.copy(old_jsp, new File(webRoot+"form/jsp/"+jsp_36+".jsp"));
				List<FormTagMapColumn> mapedList = zwkjFormDao.getFormTagMapColumnByFormId(formId);
				
				//2.2完整实体类,保存表单相关信息
				ZwkjForm from = new ZwkjForm();
				if(zwkjForm.getUpdatetime()==null){
					zwkjForm.setUpdatetime(zwkjForm.getIntime());
				}
				BeanUtils.copyProperties(from, zwkjForm);
				from.setWorkflowId(id);
				from.setId(null);
				from.setForm_htmlfilename(htm_36+".html");
				from.setForm_jspfilename(jsp_36+".jsp");
				ZwkjForm new_form = zwkjFormDao.addForm(from);
				
				//2.3 tag与字段之间的匹配关系
				String new_formId = new_form.getId();
				htmlMap.put(formId, new_formId);		//from表单之间的匹配关系
				FormTagMapColumn formTagMapColumn = null;
				FormTagMapColumn column = null;
				for(int j=0; j<mapedList.size(); j++){
					column = new FormTagMapColumn();
					formTagMapColumn = mapedList.get(j);
					BeanUtils.copyProperties(column, formTagMapColumn);
					column.setId(null);
					column.setFormid(new_formId);
					zwkjFormDao.addFormTagMapColumn(column);
				}
			}
			
			//3.获取套打模板
			Map<String, String> templateMap = new HashMap<String, String>();
			List<WfTemplate> tempList = wftemplateDao.getTemplateByLcid(wfm_uId);
			String realPath = PathUtil.getWebRoot();
			WfTemplate template = null;
			String templateId = "";
			for(int i=0; i<tempList.size(); i++){
				template = tempList.get(i);
				templateId = template.getId();
				String tempPath = template.getVc_path();
				String type = tempPath.substring(tempPath.lastIndexOf("."), tempPath.length());
				//3.1文件转移
				String docName = System.currentTimeMillis()+type;
				String tempFile =  realPath+"/tempfile/" + docName;
				String temBaFile = realPath+"/tempbak/" + docName;
				template = tempList.get(i);
				String docPath = realPath+"/tempfile/"+template.getVc_path();
				File file = new File(docPath);
				MyUtils.copy(file, new File(tempFile));
				MyUtils.copy(file, new File(temBaFile));	
				//3.2
				//新增模板:流程信息
				WfTemplate new_template = new WfTemplate();
				if(template.getC_createdate()==null){
					template.setC_createdate(new Timestamp(System.currentTimeMillis()));
				}
				BeanUtils.copyProperties(new_template,template);
				new_template.setLcid(id);
				new_template.setVc_path(docName);
				new_template.setId(null);
				WfTemplate tem = wftemplateDao.addTemplate(new_template);
				
				String newTempId = tem.getId();
				templateMap.put(templateId, newTempId);
				List<WfLabel>  labelList = labelDao.getLabelByTid(templateId);
				WfLabel label = null;
				WfLabel newLabel = null;
				for(int j=0; j<labelList.size(); j++){
					newLabel = new WfLabel();
					label = labelList.get(j);
					BeanUtils.copyProperties(newLabel, label);
					newLabel.setId(null);
					newLabel.setVc_templateId(newTempId);
					labelDao.addLabel(newLabel);
				}
			}
			
			//4.流程用户组
			Map<String, String> innerUserMap = new HashMap<String, String>();
			List<InnerUser>  groupList = groupDao.getInnerUserList(wfm_uId, "4");
			InnerUser  innerUser = null;
			String innerUserId = "";
			for(int i=0;i<groupList.size();i++){
				innerUser = groupList.get(i);
				innerUserId = innerUser.getId();
				if(innerUser.getIntime()==null){
					innerUser.setIntime(new Timestamp(new Date().getTime()));
				}
				if(innerUser.getUpdatetime()==null){
					innerUser.setUpdatetime(innerUser.getIntime());
				}
				InnerUser new_innerUser = new InnerUser();
				BeanUtils.copyProperties(new_innerUser, innerUser);
				new_innerUser.setId(null);
				new_innerUser.setWorkflowId(id);
				InnerUser inner = groupDao.save(new_innerUser);
				innerUserMap.put(innerUserId, inner.getId());
			}
			//4.复制赋值xml
			WfXml wfXml = workflowBasicFlowDao.getWfXml(wfm_uId);		//流程图
			WfMain newMain = workflowBasicFlowDao.getWfMainById(id);
			WfXml newWfXml = new WfXml();
			newWfXml.setWfx_id(id);
			String xmlContent = wfXml.getWfx_xml();			//原先的xmlContent
			Set<WfNode>  nodeSet = wfMain.getNode_sets();				
			Set<WfLine>  lineSet = wfMain.getLine_sets();
			Set<WfChild>  childSet = wfMain.getChild_sets();

			//5.保存node节点列表
			Iterator<WfNode> it = nodeSet.iterator(); 
			WfNode wfNode = null;
			Map<String, String> nodeMap = new HashMap<String, String>();
			while(it.hasNext()){
				wfNode = it.next();
				String oldNodeId = wfNode.getWfn_id();
				String wfn_id = UuidGenerator.generate36UUID();
				wfNode.setWfn_id(wfn_id);
				wfNode.setWfMain(newMain);
				//5.1替换节点绑定的人员用户组
				String node_innerUserId = wfNode.getWfn_staff();
				String node_new_innerUserId = innerUserMap.get(node_innerUserId);
				wfNode.setWfn_staff(node_new_innerUserId);
				//5.2去除 固定节点人员
				wfNode.setWfn_bd_user("");
				//5.3设置默认模板
				
				String node_templdateId = wfNode.getWfn_defaulttemplate();
				String node_new_templdateId = templateMap.get(node_templdateId);
				wfNode.setWfn_defaulttemplate(node_new_templdateId);
				//替换默认表单
				String node_formId = wfNode.getWfn_defaultform();
				String node_new_formId = htmlMap.get(node_formId);
				wfNode.setWfn_defaultform(node_new_formId);
				workflowBasicFlowDao.saveWfNode(wfNode);
				nodeMap.put(oldNodeId, wfn_id);
			}
			WfLine wfLine = null;
			Iterator<WfLine> lit = lineSet.iterator(); 
			Map<String, String> lineMap = new HashMap<String, String>();
			while(lit.hasNext()){
				wfLine = lit.next();
				String oldLineId = wfLine.getWfl_id();
				String wfl_id = UuidGenerator.generate36UUID();
				wfLine.setWfl_id(wfl_id);
				wfLine.setWfMain(newMain);
				workflowBasicFlowDao.saveWfLine(wfLine);
				lineMap.put(oldLineId, wfl_id);
			}
			
			//子流程child
			WfChild wfChild = null;
			Iterator<WfChild> childlit = childSet.iterator(); 
			Map<String, String> childMap = new HashMap<String, String>();
			while(childlit.hasNext()){
				wfChild = childlit.next();
				String childId = wfChild.getWfc_id();
				String wfl_id = UuidGenerator.generate36UUID();
				wfChild.setWfc_id(wfl_id);
				wfChild.setWfMain(newMain);
				workflowBasicFlowDao.saveWfChild(wfChild);
				childMap.put(childId, wfl_id);
			}
			
			
			
			//5.将xml中相关内容替换掉
				//5.1替换表单信息
			String old_formId = "";
			String new_formId = "";
			for(String key : htmlMap.keySet()){
				old_formId = key;
				if(old_formId!=null && !old_formId.equals("")){
					new_formId = htmlMap.get(old_formId);
					if(new_formId==null){
						new_formId = "";
					}
				}
				xmlContent = xmlContent.replace(old_formId, new_formId);  
			}
				//5.2替换node
			String old_nodeId = "";
			String new_nodeId = "";
			for(String key : nodeMap.keySet()){
				old_nodeId = key;
				new_nodeId = nodeMap.get(old_nodeId);
				xmlContent = xmlContent.replace(old_nodeId, new_nodeId); 
			}
				//5.3替换line线条信息
			String old_lineId = "";
			String new_lineId = "";
			for(String key : lineMap.keySet()){
				old_lineId = key;
				new_lineId = lineMap.get(old_lineId);
				xmlContent = xmlContent.replace(old_lineId, new_lineId); 
			}
			
			//5.4替换Child
			String old_childId = "";
			String new_childId = "";
			for(String key : childMap.keySet()){
				old_childId = key;
				new_childId = childMap.get(old_childId);
				xmlContent = xmlContent.replace(old_childId, new_childId); 
			}
			
			//5.5替换template信息
			String old_templateId = "";
			String new_templateId = "";
			for(String key : templateMap.keySet()){
				old_templateId = key;
				new_templateId = templateMap.get(old_templateId);
				xmlContent = xmlContent.replace(old_templateId, new_templateId);
			}
			
			//5.6替换inner
			String old_innerUserId = "";
			String new_innerUserId = "";
			for(String key : innerUserMap.keySet()){
				old_innerUserId = key;
				new_innerUserId = innerUserMap.get(old_innerUserId);
				xmlContent = xmlContent.replace(old_innerUserId, new_innerUserId);
			}
			newWfXml.setWfx_xml(xmlContent);
			workflowBasicFlowDao.saveWfXml(newWfXml);
			
			//7.复制许可设置
			List<WfFormPermit> permitlist = formPermitDao.findFormPermitListByLcId(wfm_uId);
			WfFormPermit wfFormPermit = null;
			for(int i=0; i<permitlist.size(); i++){
				wfFormPermit = permitlist.get(i);			
				//7.1替换formId
				String formId = wfFormPermit.getVc_formid();
				String newFormId = "";
				for(String key : htmlMap.keySet()){
					if(formId.equals(key)){
						newFormId = htmlMap.get(key);
						break;
					}
				}
				//7.2替换流程id
				//7.3替换nodeId
				String nodeId = wfFormPermit.getVc_missionid();
				String new_missionId = "";
				for(String key : nodeMap.keySet()){
					if(nodeId.equals(key)){
						new_missionId = nodeMap.get(key);
						break;
					}
				}
				//7.4替换流程用户组
				String innerId = wfFormPermit.getVc_roleid();
				String new_innerId = "";
				for(String key : innerUserMap.keySet()){
					if(innerId.equals(key)){
						new_innerId = innerUserMap.get(key);
						break;
					}
				}
				//重新创建新的实体对象
				WfFormPermit perimt = new WfFormPermit();
				BeanUtils.copyProperties(perimt, wfFormPermit);
				perimt.setId(null);
				perimt.setVc_formid(newFormId);
				perimt.setVc_missionid(new_missionId);
				perimt.setLcid(id);
				perimt.setVc_roleid(new_innerId);
				formPermitDao.saveFormPermit(perimt);
			}
			result.put("success", true);
		}catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}

	
	@Override
	public Object[] showNextNode(String id, String node_id){
		// 获取节点id,如果为空则说明搜寻的是开始节点
		List<WfNode> list = new ArrayList<WfNode>();
		if (null == node_id || node_id.equals("")) {
			// 查询出开始节点
			WfNode node = getStartNode(id);
			list.add(node);
		} else {
			// 找出该id节点下面的节点
			list = getNode(id, node_id);
		}
		List<WfNode> nodeList = new ArrayList<WfNode>();
		int k = 0;	
		//节点,查询出,节点之间的线条信息
		WfNode wfNode = null;
		for(int i=0; i<list.size(); i++){
			wfNode = list.get(i);
			String nextNodeId = list.get(i).getWfn_id();
			WfLine wfLine = workflowBasicFlowDao.findWfLineByNodeId(id, nextNodeId, node_id);		//两个节点之间的线条信息
			if(wfLine!=null){
				String choice_condition = wfLine.getWfl_choice_condition();		//线条上判断条件
				if(choice_condition!=null && !choice_condition.equals("")){		//存在线上条件
					k++;
				}else{
					nodeList.add(wfNode);
				}
			}else{
				nodeList.add(wfNode);
			}
		}
		Object[] obj = new Object[2];
		obj[0] = nodeList;
		obj[1] = k;
		return obj;
	}

	public List<WfNode> showNextNodeList(String id, String node_id){
		List<WfNode> list = new ArrayList<WfNode>();
		if (null == node_id || node_id.equals("")) {
			// 查询出开始节点
			WfNode node = getStartNode(id);
			list.add(node);
		} else {
			// 找出该id节点下面的节点
			list = getNode(id, node_id);
		}
		return list;
	}

	@Override
	public WfLine getLineById(String wfl_id) {

		return workflowBasicFlowDao.getLineById(wfl_id);
	
	}

	@Override
	public JSONObject saveWfMainInfo(String wfm_id, WfMain wfMain, WfXml wfXml) {
		JSONObject result = new JSONObject();
		try{
			//如果存在该流程，则删除该流程，再执行保存
			workflowBasicFlowDao.deleteWfMain(wfm_id);
			workflowBasicFlowDao.saveWfMain(wfMain);
			workflowBasicFlowDao.deleteWfXml(wfm_id);
			workflowBasicFlowDao.saveWfXml(wfXml);
			result.put("success", true);
		}catch (Exception e) {
			result.put("success", false);
		}
		return result;
	}

	@Override
	public void geneAttRelationInfo(String id, String newWfm_id, String rootFolder) {
		List<AttachmentTypeWfMainShip>  shipList  = attachmentTypeFZDao.findAttachmentTypeWfMainShipList(id);
		List<String> sqls = new  ArrayList<String>();
		AttachmentTypeWfMainShip entity = null;
		if(shipList!=null && shipList.size()>0){
			for(int i=0; i<shipList.size(); i++){
				entity = shipList.get(i);
				String geneSql = "insert into t_wf_core_attachwfmain_ship(ID,WFUID,ATTACTHTYPEID) values(";
				geneSql += "'"+UuidGenerator.generate36UUID()+"','"+newWfm_id+"','"+entity.getAttacthTypeId()+"');";
				sqls.add(geneSql);
			}
		}
		String officeFolder = rootFolder+"/att";
		new File(officeFolder).mkdirs();
		SqlFileUtil.write(officeFolder+"/atts.sql", sqls);
		
/*			//8.2材料绑定到节点
		List<Object[]> bindList = attachmentTypeDao.findAttachmentTypeBind(id);
		Object[] data = null;
		if(bindList!=null && bindList.size()>0){
			AttachmentTypeBind attachmentTypeBind = null;
			for(int i=0; i<bindList.size(); i++){
				attachmentTypeBind = new AttachmentTypeBind();
				data = bindList.get(i);
				String nodeId = (data[0]!=null)?data[0].toString():"";
				String attachmentTypeId = (data[2]!=null)?data[2].toString():"";
				if(nodeId!=null){
					String newNodeId = "";
					for(String key : nodeMap.keySet()){
						if(nodeId.equals(key)){
							newNodeId = nodeMap.get(key);
						}
					}
					attachmentTypeBind.setNodeId(newNodeId);
					attachmentTypeBind.setAttachmentTypeId(attachmentTypeId);
					attachmentTypeDao.saveAttachmentTypeBind(attachmentTypeBind);
				}
				
			}
		}*/
	}

	@Override
	public void geneBackNodeInfo(String id, String newWfm_id, String rootFolder, Map<String, String> nodeIds) {
		 // 处理路径
		 String templateFolder = rootFolder+"/set";
		 new File(templateFolder).mkdirs();
		
		 List<WfBackNode> oldBackNodes = new ArrayList<WfBackNode>();
		 oldBackNodes = workflowBasicFlowDao.getBackNodeListByWfId(id);
		 Map<String, String> permitIds = new HashMap<String, String>();
		 List<String> backSqls = new ArrayList<String>();
		 for(int i = 0; i<oldBackNodes.size();i++ ){
			  WfBackNode back = oldBackNodes.get(i);
			  String newBackId = UuidGenerator.generate36UUID();
			  permitIds.put(back.getId(), newBackId);
			  String permitSql = workflowBasicFlowDao.geneBackNodeSql(back,newBackId,newWfm_id,nodeIds);
			  backSqls.add(permitSql);
		 }
		 if(backSqls != null && backSqls.size()>0){
			 SqlFileUtil.write(templateFolder+"/back.sql", backSqls);
		 }
	}

	@Override
	public Map<String, String> geneFlowInfo(String id, String newWfm_id, String rootFolder, Map<String, String> tableIds,
			Map<String, String> innerUserIds, Map<String, String> formIds, Map<String, String> templateIds) {

		// 路径 / WF_Main,wf_xml,wf_node,Wf_line
		String templateFolder = rootFolder+"/flow";
		String lineFolder = templateFolder+"/line";
		String nodeFolder =  templateFolder+"/node";
		String mainFolder =  templateFolder+"/main";
		new File(lineFolder).mkdirs();
		new File(nodeFolder).mkdirs();
		new File(mainFolder).mkdirs();
		// 处理 main 本身
		String mainSql =workflowBasicFlowDao.geneMainSql(id,newWfm_id,tableIds);
		List<String> MainSqls = new ArrayList<String>();
		MainSqls.add(mainSql);
		SqlFileUtil.write(mainFolder+"/main.sql", MainSqls);
		 
		// 处理线条
	   List<String> oldLineIds = new  ArrayList<String>();
	   oldLineIds = workflowBasicFlowDao.getOldLineIds(id);
	   Map<String, String> lineIds = new HashMap<String, String>();
	   List<String> lineSqls = new ArrayList<String>();
	   for(int i = 0; i< oldLineIds.size() ;i++){
		   String newLineId = UuidGenerator.generate36UUID();
		   lineIds.put(oldLineIds.get(i), newLineId);
		   String lineSql = workflowBasicFlowDao.geneLineSql(oldLineIds.get(i),newLineId,newWfm_id);
		   lineSqls.add(lineSql);
	   }
	   SqlFileUtil.write(lineFolder+"/line.sql", lineSqls);
	   
	   // 处理node 未处理的 节点默认人员，节点报道
	   Map<String, String> nodeIds = new HashMap<String, String>();
	   List<String> oldNodeIds = new  ArrayList<String>();
	   oldNodeIds = workflowBasicFlowDao.getOldNodeIds(id);
	   List<String> nodeSqls = new ArrayList<String>();
	   for(int i = 0; i< oldNodeIds.size() ;i++){
		   String newNodeId = UuidGenerator.generate36UUID();
		   nodeIds.put(oldNodeIds.get(i), newNodeId);
		   String nodeSql = workflowBasicFlowDao.geneNodeSql(oldNodeIds.get(i),newNodeId,newWfm_id,innerUserIds,templateIds,formIds);
		   nodeSqls.add(nodeSql);
	   }
	   SqlFileUtil.write(nodeFolder+"/node.sql", nodeSqls);
	   
	   // 处理 xml 替换 nodeId,tableId,
	   // 文件名xml_lcid.txt 内容为 xml 表 WFX_XML 字段值
	   String xml_content = "";
	   xml_content = workflowBasicFlowDao.geneXmlSql(id,newWfm_id,innerUserIds,templateIds,formIds,tableIds,nodeIds,lineIds);
	   List<String> xmlSqls = new ArrayList<String>();
	   xmlSqls.add(xml_content);
	   
	   SqlFileUtil.writeTXT(mainFolder+"/xml_"+newWfm_id+".txt", xmlSqls);
	   return nodeIds;
}

	@Override
	public Map<String, String> geneFormInfo(String id, String newWfm_id, String rootFolder) {
		// 1. t_wf_core_form, 	
		List<ZwkjForm> forms = workflowBasicFlowDao.getForms(id);
		// 文件目录迁移
		String destPath = rootFolder+"/form/";
		String formFolder = PathUtil.getWebRoot()+"form/";
		Map<String, String> formIds = new HashMap<String,String>();
		String officeFolder = rootFolder+"/form/sql";
		new File(officeFolder).mkdirs();
		List<String> htmls = new ArrayList<String>();
		for(int i = 0; i< forms.size() ; i++){
			List<String> sqls = new ArrayList<String>();
    		
			ZwkjForm form = forms.get(i);
			String newFormId = UuidGenerator.generate36UUID();
			formIds.put(form.getId(), newFormId);
			String htm_36 = UuidGenerator.generate36UUID();
			String jsp_36 = UuidGenerator.generate36UUID();
			htmls.add(htm_36);
			String coreForm = workflowBasicFlowDao.geneCoreForm(form,newFormId,htm_36,jsp_36,newWfm_id);
			sqls.add(coreForm);
			// 2. t_wf_core_form_map_column
			List<String> mapColumns = workflowBasicFlowDao.geneMapColumns(form.getId(),newFormId);
			sqls.addAll(mapColumns);
			
			// 3. 文件 内容
			String oldHtml = formFolder+"html/"+form.getForm_htmlfilename();
			String oldHtml_view;
			if(CommonUtil.stringIsNULL(form.getForm_htmlfilename())){
				oldHtml_view = formFolder + "html/";
			}else{
				oldHtml_view = formFolder + "html/" + form.getForm_htmlfilename().replace(".html", "_view.html");
			}

			String oldJsp = formFolder+"jsp/"+form.getForm_jspfilename();
			new File(destPath+"html/").mkdirs();
			new File(destPath+"jsp/").mkdirs();
			// 判断文件是否存在
			if(new File(oldHtml).exists()){
				FileUploadUtils.copy(new File(oldHtml), new File(destPath+"html/"+htm_36+".html"));
			}
			
			if(new File(oldHtml_view).exists()){
				FileUploadUtils.copy(new File(oldHtml_view), new File(destPath+ "html/" + htm_36 + "_view.html"));
			}
			
			if(new File(oldJsp).exists()){
				FileUploadUtils.copy(new File(oldJsp), new File(destPath+"jsp/"+jsp_36+".jsp"));
			}
			
			SqlFileUtil.write(officeFolder+"/"+form.getForm_name()+".sql", sqls);
		}
		/*for (int i = 0; i < forms.size(); i++) {
			// relation T_WF_CORE_TABFORMRELATION
				ZwkjForm form = forms.get(i);
				List<String> tabRelations = workflowBasicFlowDao.geneTabRelationColumns(form.getId(), formIds.get(form.getId()), htmls.get(i) + ".html",formIds);

				if(tabRelations!= null && tabRelations.size()>0){
					SqlFileUtil.write(officeFolder + "/" + form.getForm_name()+ "TabRelations.sql", tabRelations); 
				}
		}*/
		
		// html  ,_view ，jsp , 文件中 需要替换 formid
		File file1 = new File(destPath+"html/");
		FileFilter fileFilter = new FileFilter() {
            public boolean accept(File file) {
            	boolean flag = false ;
            	if(file.getName().endsWith(".html")||file.getName().endsWith(".jsp")){
            		flag = true;
            	}
                return flag;
            }
        };
		File[] htmlFiles =  file1.listFiles(fileFilter);
		FileUtils.replaceFile(htmlFiles, formIds);
		File file2 = new File(destPath+"jsp/");
		File[] jspFiles =  file2.listFiles(fileFilter);
		FileUtils.replaceFile(jspFiles, formIds);
		return formIds;
	}

	@Override
	public Map<String, String> geneInnerUserInfo(String id, String newWfm_id, String rootFolder) {
		// sql 存储路径为 /lcid/innerUser
		String officeFolder = rootFolder+"/innerUser";
		new File(officeFolder).mkdirs();
		List<String> oldIds = new ArrayList<String>();
		oldIds = workflowBasicFlowDao.getInnerUserIdsByLcid(id);
		Map<String, String> innerIds = new HashMap<String,String>();
		
		List<String> sqls = new ArrayList<String>();
		for(int i = 0; i<oldIds.size();i++ ){
			String newInnerUserId = UuidGenerator.generate36UUID();
			innerIds.put(oldIds.get(i), newInnerUserId);
			
			String innerUser = workflowBasicFlowDao.geneInnerUsers(oldIds.get(i),newInnerUserId,newWfm_id);
			sqls.add(innerUser);
			
		}
		SqlFileUtil.write(rootFolder+"/innerUser/innerUser.sql", sqls);
		return innerIds;
	}

	@Override
	public Map<String, String> geneOfficeInfo(String id, String newWfm_id, String rootFolder) {
		//t_wf_core_fieldinfo, T_WF_CORE_TABLE
		// 根据流程id 获取 业务表
		String officeFolder = rootFolder+"/office";
		Map<String, String> tableIds = new HashMap<String,String>();
		new File(officeFolder).mkdirs();
	    List<String> tables = new ArrayList<String>();
	    // 存储表名 表名存在 则 执行 update
	    List<String> tableNames = new ArrayList<String>();
	    tables = workflowBasicFlowDao.getOfficeTableByLcid(id);
	    if(tables != null && tables.size()>0){
	    	for(int i = 0; i< tables.size() ; i++){
	    		List<String> sqls = new ArrayList<String>();
	    		String newTableId = UuidGenerator.generate36UUID();
	    		String oldTableId =tables.get(i).split("\\|")[0] ;
	    		String oldTableName =tables.get(i).split("\\|")[1] ;
	    		tableNames.add(oldTableName);
	    			// 表存在的情况下
	    			String hql = "update T_WF_CORE_TABLE t set t.reflc = t.reflc||'"+newWfm_id+",' where t.vc_tablename = '"+oldTableName+"'";
	    			tableNames.add(hql);
	    			// 表不存在的情况 ,
		    		tableIds.put(oldTableId, newTableId);
		    		// 2. 生成 T_WF_CORE_TABLE 记录
		    		String coreTable = workflowBasicFlowDao.geneCoreTableSql(oldTableId,newTableId,newWfm_id);
		    		sqls.add(coreTable);
		    		// 3. 生成 t_wf_core_fieldinfo
		    		List<String> coreFields = workflowBasicFlowDao.geneCoreFieldsSql(oldTableId,newTableId,newWfm_id);
		    		sqls.addAll(coreFields);
		    		// 4. 创建 业务表
		    		String coreOffice = workflowBasicFlowDao.geneCoreOfficeSql(oldTableId,oldTableName);
		    		sqls.add(coreOffice);
		    		// 5. 业务表注释
		    		//String officeComment = workflowBasicFlowDao.geneOfficeComment();
		    		// 6. 业务表字段注释
		    		List<String> fieldComments = workflowBasicFlowDao.genefieldComment(oldTableId,oldTableName);
		    		sqls.addAll(fieldComments);
		    	//	File file = new File(officeFolder+"/"+oldTableName);
		    		SqlFileUtil.write(officeFolder+"/"+oldTableName+".sql", sqls);
	    	}
	    	// 存储表名 和 业务表存在的情况下面 更新 业务表的关联流程的sql
	    	SqlFileUtil.write(officeFolder+"/tables.txt", tableNames);
	    }
		return tableIds;
	}

	@Override
	public Map<String, String> genePermitInfo(String id, String newWfm_id, String rootFolder, Map<String, String> tableIds,
			Map<String, String> innerUserIds, Map<String, String> formIds, Map<String, String> nodeIds) {
		
		 // 处理路径
		 String templateFolder = rootFolder+"/permit";
		 new File(templateFolder).mkdirs();
		 Map<String, String> permitIds = new HashMap<String, String>();
		 
		 List<WfFormPermit> oldPermits = new ArrayList<WfFormPermit>();
		 for(String key : formIds.keySet()){
			 oldPermits = workflowBasicFlowDao.getPermitsByFormId(key);
			
			 List<String> permitSqls = new ArrayList<String>();
			 for(int i = 0; i<oldPermits.size();i++ ){
				
				  WfFormPermit permit = oldPermits.get(i);
				  String newPermitId = UuidGenerator.generate36UUID();
				  permitIds.put(permit.getId(), newPermitId);
				  String permitSql = workflowBasicFlowDao.genePermitSql(permit,newPermitId,newWfm_id,tableIds,innerUserIds,formIds,nodeIds);
				  permitSqls.add(permitSql);
			 }
			 if(permitSqls!= null && permitSqls.size()>0){
				 SqlFileUtil.write(templateFolder+"/"+key+".sql", permitSqls);
			 }
			}
		 
		return permitIds;
	}

	@Override
	public Map<String, String> geneTemplateInfo(String id, String newWfm_id, String rootFolder, Map<String, String> tableIds) {
		// sql 处理 ,模板迁移
		// 路径 / 
		String templateFolder = rootFolder+"/template";
		String sqlFolder = templateFolder+"/sql";
		String fileFolder =  templateFolder+"/doc";
		new File(sqlFolder).mkdirs();
		new File(fileFolder).mkdirs();
		
		String folder = PathUtil.getWebRoot()+"tempfile/";
		Map<String, String> templateIds = new HashMap<String,String>();
		List<WfTemplate> templates = templateDao.getTemplateByLcid(id);
		for(int i = 0; i<templates.size();i++ ){
			List<String> sqls = new ArrayList<String>();
			WfTemplate template = templates.get(i);
			String doc_36 = UuidGenerator.generate36UUID()+".doc";
			// template 表
			String newTemplateId = UuidGenerator.generate36UUID();
			templateIds.put(template.getId(), newTemplateId);
			String templateSql = workflowBasicFlowDao.geneTemplateSql(template,newTemplateId,doc_36,newWfm_id);
			sqls.add(templateSql);
			// label 表
			List<String> labels = workflowBasicFlowDao.geneLabels(template.getId(),newTemplateId,tableIds);
			sqls.addAll(labels);
			// file
			String oldDoc = folder+"/"+template.getVc_path();
			
			if(new File(oldDoc).exists()){
				FileUploadUtils.copy(new File(oldDoc), new File(fileFolder+"/"+doc_36));
			}
			
			SqlFileUtil.write(sqlFolder+"/"+template.getVc_ename()+".sql", sqls);
			
		}
		return templateIds;
	}

	@Override
	public String getWfMainNameById(String id) {
		return workflowBasicFlowDao.getWfMainNameById(id);
	}

	@Override
	public void importAttInfo(String attPath) {
		// 导入流程绑定附件信息
		workflowBasicFlowDao.importSql(attPath+"/atts.sql");
	}

	@Override
	public void importFlowInfo(String flowPath) {
		 //  流程图 WF_Main,wf_xml,wf_node,Wf_line ,待补充子流程（wf_child)
		 //    文件夹 lcid/flow 存储 (line,main,node) 在main文件夹下面(main.sql,xml_lcid.txt)  xml_lcid.txt 存储为这样是因为 clob 内容多无法插入 这个需要考虑
       // 1 wf_main
		String mainPath = flowPath+"/main";
		workflowBasicFlowDao.importSql(mainPath+"/main.sql");
		// 2 wf_xml
		File file = new File(mainPath);
		File[] files =  file.listFiles();
		String xmlPath = "";
		String lcid = "";
				
		for(int i = 0; i < files.length;i++){
			if(files[i].getName().indexOf("xml_")>-1){
				xmlPath = files[i].getName();
				//workflowBasicFlowDao.execClobXml(files[i].getAbsolutePath());
				break;
			}
		}
		if(xmlPath != null && !"".equals(xmlPath)){
			lcid = xmlPath.split("\\_")[1].split("\\.")[0];
			WfXml xml = new WfXml();
			xml.setWfx_id(lcid);
			String clobXml = FileUtils.read(mainPath+"/"+xmlPath);
			xml.setWfx_xml(clobXml);
			workflowBasicFlowDao.saveWfXml(xml);
		}
		// 3 wf_node
		String nodePath = flowPath+"/node";
		workflowBasicFlowDao.importSql(nodePath+"/node.sql");
		// 4 wf_line
		String linePath = flowPath+"/line";
		workflowBasicFlowDao.importSql(linePath+"/line.sql");
		
	}

	@Override
	public void importFormInfo(String formPath) {
		String sqlPath = formPath+"/sql";        // 一行一行的读取文件
		File file = new File(sqlPath);
		File[] sqlFiles =  file.listFiles();
		for(int i = 0; i < sqlFiles.length;i++){
			workflowBasicFlowDao.importSql(sqlPath+"/"+sqlFiles[i].getName());
		}
		// html copy
		String htmlPath = formPath+"/html";
		File file1 = new File(htmlPath);
		File[] htmlFiles =  file1.listFiles();
		String rootPath = PathUtil.getWebRoot()+"/form/";
		for(int i = 0; i < htmlFiles.length ; i++){
			FileUploadUtils.copy(htmlFiles[i], new File(rootPath+"html/"+htmlFiles[i].getName()));
		}
		// jsp copy
		String jspPath = formPath+"/jsp";
		File file2 = new File(jspPath);
		File[] jspFiles =  file2.listFiles();
		for(int i = 0; i < jspFiles.length ; i++){
			FileUploadUtils.copy(jspFiles[i], new File(rootPath+"jsp/"+jspFiles[i].getName()));
		}
	}

	@Override
	public void importInnerUserInfo(String innerUserPath) {
		// 导入用户组信息
		workflowBasicFlowDao.importSql(innerUserPath+"/innerUser.sql");
	}

	@Override
	public void importOfficeInfo(String officePath) {
		// 2. 业务表 office  
		// 考虑到 业务吧可能存在  可能是引用，和 创建 ，分为2块内容 一个是 tables.txt 还有是表名 构建的 sql 文件 tables.txt 存储的是 表名\r\n 关联业务表的sql \r\n存储的是 表名\r\n 关联业务表的sql
		// 如果表名存在 执行下一行的 update 语句 如果不存在 执行以表名 命名的sql文件
		String tablesPath = officePath+"/tables.txt";// 一行一行的读取文件
		List<String> list = FileUtils.readLine(tablesPath);
		for(int i = 0; i< list.size() ; ){
			
			// 判断表是否存在
			boolean isExist = workflowBasicFlowDao.existTable(list.get(i));
			if(isExist){
				// 存在执行update
				workflowBasicFlowDao.execSql(list.get(i+1));
			}else{
				// 不存在执行 sql 文件
				workflowBasicFlowDao.importSql(officePath+"/"+list.get(i)+".sql");
			}
			i= i+2;
		}
	}

	@Override
	public void importPermitInfo(String permitPath) {
		//表单权限
		File file = new File(permitPath);
		File[] sqlFiles =  file.listFiles();
		for(int i = 0; i < sqlFiles.length;i++){
			workflowBasicFlowDao.importSql(permitPath+"/"+sqlFiles[i].getName());
		}
	}

	@Override
	public void importSetInfo(String setPath) {
		// 自动追溯设置
		workflowBasicFlowDao.importSql(setPath+"/back.sql");
	}

	@Override
	public void importTemplateInfo(String templatePath) {
		// 套打模板 放置在 doc 和  sql
		String templateSql = templatePath+"/sql";
		File file = new File(templateSql);
		File[] sqlFiles =  file.listFiles();
		for(int i = 0; i < sqlFiles.length;i++){
			workflowBasicFlowDao.importSql(templateSql+"/"+sqlFiles[i].getName());
		}
		String rootPath = PathUtil.getWebRoot()+"/tempfile/";
		// doc 
		String docPath =  templatePath+"/doc";
		File file1 = new File(docPath);
		File[] docFiles =  file1.listFiles();
		for(int i = 0; i < docFiles.length;i++){
			FileUploadUtils.copy(docFiles[i], new File(rootPath+docFiles[i].getName()));
		}
	}
	
	@Override
	public List<WfChild> findWfChildListByWfPid(String wfPid) {
		return workflowBasicFlowDao.findWfChildListByWfPid(wfPid);
	}

	@Override
	public List<WfChild> findWfChildListByWfcid(String wfcid) {
		return workflowBasicFlowDao.findWfChildListByWfcid(wfcid);
	}

	@Override
	public List<WfNode> findWfNodeByInstanceId(String instanceId, String processId) {
		return workflowBasicFlowDao.findWfNodeByInstanceId(instanceId, processId);
	}
	
	@Override
	public WfNode getEndNode(String workflowId) {
		return workflowBasicFlowDao.getEndNode(workflowId);
	}
	
	@Override
	public List<WfNode> getSortNodeId(String workflowId){
		return workflowBasicFlowDao.getSortNodeId(workflowId);
	}

	@Override
	public List<ZwkjForm> findAllFormByLcId(String workflowId) {
		return workflowBasicFlowDao.findAllFormByLcId(workflowId);
	}

	@Override
	public String findRoleName(String wfn_staff) {
		return workflowBasicFlowDao.findRoleName(wfn_staff);
	}

	@Override
	public int getCountFromWfItem(String roleIds, String conditionSql) {
		return workflowBasicFlowDao.getCountFromWfItem(roleIds,conditionSql);
	}

	@Override
	public List<Object[]> getItemList(String roleIds, String conditionSql,
			int pageIndex, int pageSize) {
		return workflowBasicFlowDao.getItemList(roleIds, conditionSql, pageIndex,pageSize);
	}

	@Override
	public String findRoleUserIds(String wfn_staff) {
		return workflowBasicFlowDao.findRoleUserIds(wfn_staff);
	}
}
