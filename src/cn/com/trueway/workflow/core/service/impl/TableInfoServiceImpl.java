package cn.com.trueway.workflow.core.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.TabExpander;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.JDBCBase;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.business.model.DocXgDepartment;
import cn.com.trueway.document.business.model.LowDoc;
import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.ldbook.util.RemoteLogin;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.FlowDao;
import cn.com.trueway.workflow.core.dao.ItemDao;
import cn.com.trueway.workflow.core.dao.PendingDao;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.dao.TableInfoExtendDao;
import cn.com.trueway.workflow.core.dao.WorkflowBasicFlowDao;
import cn.com.trueway.workflow.core.pojo.AutoSend;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.DofileFavourite;
import cn.com.trueway.workflow.core.pojo.DzJcdb;
import cn.com.trueway.workflow.core.pojo.DzJcdbShip;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.EmployeeLeave;
import cn.com.trueway.workflow.core.pojo.EndInstanceId;
import cn.com.trueway.workflow.core.pojo.FollowShip;
import cn.com.trueway.workflow.core.pojo.GetProcess;
import cn.com.trueway.workflow.core.pojo.OfficeInfoView;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.PersonMessage;
import cn.com.trueway.workflow.core.pojo.PushMessage;
import cn.com.trueway.workflow.core.pojo.Reply;
import cn.com.trueway.workflow.core.pojo.Sw;
import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.core.pojo.WfConsult;
import cn.com.trueway.workflow.core.pojo.WfCyName;
import cn.com.trueway.workflow.core.pojo.WfDuBanLog;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.WfRecallLog;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.pojo.vo.Receiver;
import cn.com.trueway.workflow.core.pojo.vo.StepIndexVO;
import cn.com.trueway.workflow.core.pojo.vo.Todos;
import cn.com.trueway.workflow.core.pojo.vo.TrueOperateLog;
import cn.com.trueway.workflow.core.pojo.vo.WfProcessVO;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.set.dao.EmployeeDao;
import cn.com.trueway.workflow.set.dao.EmployeeLeaderDao;
import cn.com.trueway.workflow.set.dao.ZwkjFormDao;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.util.WebSocketUtil;

@SuppressWarnings("unused")
public class TableInfoServiceImpl implements TableInfoService {
	
	private TableInfoDao 			tableInfoDao;
	
	private PendingDao 				pendingDao;
	
	private WorkflowBasicFlowDao 	workflowBasicFlowDao;	
	
	private ItemDao 				itemDao;
	
	private AttachmentDao 			attachmentDao;
	
	private FlowDao 				flowDao;
	
	private EmployeeDao 			employeeDao;
	
	private EmployeeLeaderDao  		employeeLeaderDao;
	
	private ZwkjFormDao				zwkjFormDao;
	
	private TableInfoExtendDao 		tableInfoExtendDao;
	
	public FlowDao getFlowDao() {
		return flowDao;
	}

	public void setFlowDao(FlowDao flowDao) {
		this.flowDao = flowDao;
	}

	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}
	
	public PendingDao getPendingDao() {
		return pendingDao;
	}

	public void setPendingDao(PendingDao pendingDao) {
		this.pendingDao = pendingDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public ZwkjFormDao getZwkjFormDao() {
		return zwkjFormDao;
	}

	public void setZwkjFormDao(ZwkjFormDao zwkjFormDao) {
		this.zwkjFormDao = zwkjFormDao;
	}
	
	public TableInfoExtendDao getTableInfoExtendDao() {
		return tableInfoExtendDao;
	}

	public void setTableInfoExtendDao(TableInfoExtendDao tableInfoExtendDao) {
		this.tableInfoExtendDao = tableInfoExtendDao;
	}



	public JDBCBase jdbcBase;

	public JDBCBase getJdbcBase() {
		return jdbcBase;
	}

	public void setJdbcBase(JDBCBase jdbcBase) {
		this.jdbcBase = jdbcBase;
	}

	public List<String> getTableCountForPage(String column, String value, WfTableInfo wfTable){
		return tableInfoDao.getTableCountForPage(column, value, wfTable);
	}
	
	public List<WfTableInfo> getTableListForPage(String column, String value,
			WfTableInfo wfTable, Integer pageindex, Integer pagesize){
		return tableInfoDao.getTableListForPage(column, value, wfTable, pageindex, pagesize);
	}
	
	public WfTableInfo getTableById(String id) {
		return tableInfoDao.getTableById(id);
	}

	public WorkflowBasicFlowDao getWorkflowBasicFlowDao() {
		return workflowBasicFlowDao;
	}

	public void setWorkflowBasicFlowDao(WorkflowBasicFlowDao workflowBasicFlowDao) {
		this.workflowBasicFlowDao = workflowBasicFlowDao;
	}

	public int isExistTable(String vc_tablename){
		int num = 0;
		List<WfTableInfo> list = tableInfoDao.getTableByTableName(vc_tablename);
		if(list != null && list.size() > 0){
			num = 1;
		}
		return num;
	}

	public WfTableInfo addTable(WfTableInfo wfTable) {
		return tableInfoDao.addTable(wfTable);
	}
	
	public List<WfTableInfo> getTableByLcid(String lcid){
		return tableInfoDao.getTableByLcid(lcid);
	}
	
	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	public EmployeeLeaderDao getEmployeeLeaderDao() {
		return employeeLeaderDao;
	}

	public void setEmployeeLeaderDao(EmployeeLeaderDao employeeLeaderDao) {
		this.employeeLeaderDao = employeeLeaderDao;
	}

	public String getCreateSql(List<WfFieldInfo> list, String vc_tablename){
		StringBuffer sb = new StringBuffer();
		sb.append(" create table " + vc_tablename + "(");
		WfFieldInfo wfField = null;
		for(int i = 0; list != null && list.size() > i; i++){
			wfField = list.get(i);
			if(wfField.getVc_fieldname()!= null && !"".equals(wfField.getVc_fieldname())){
				sb.append(wfField.getVc_fieldname()+" ");
			}
			if("0".equals(wfField.getI_fieldtype())){
				sb.append("varchar2("+wfField.getI_length()+") ");
			}else if("1".equals(wfField.getI_fieldtype())){
				sb.append("date ");
			}else if("2".equals(wfField.getI_fieldtype())){
				sb.append("number ");
			}else if("3".equals(wfField.getI_fieldtype())) {
				sb.append("clob ");
			}
			if("0".equals(wfField.getI_fieldtype()) && wfField.getVc_value() != null 
					&& !"".equals(wfField.getVc_value())){
				sb.append(" default "+wfField.getVc_value());
			}
			if(!"1".equals(wfField.getB_value())){
				sb.append(" not null");
			}
			if(i < list.size() - 1){
				sb.append(",");
			}
			if(i == list.size() - 1){
				sb.append(")");
			}
		}
		return sb.toString();
	}
	
	public List<String> getForeKeySql(List<WfFieldInfo> list, String tablename){
		List<String> sqlList = new ArrayList<String>();
		String sql = "";
		for(WfFieldInfo wfField : list){
			if(wfField.getVc_ffieldname() != null && !"".equals(wfField.getVc_ffieldname())
					&& wfField.getVc_ftablename() != null && !"".equals(wfField.getVc_ftablename())) {
				sql = "alter table " + tablename + " add constraint fk_" + wfField.getVc_ffieldname() 
					+ " foreign key (" + wfField.getVc_fieldname() + ")"
					+ " references " + wfField.getVc_ftablename() + " (" + wfField.getVc_ffieldname() + ")";
				sqlList.add( sql);
			}
		}
		return sqlList;
	}
	
	public List<String> getCommentSql(List<WfFieldInfo> list, String tablename){
		List<String> sqlList = new ArrayList<String>();
		String sql = "";
		for(WfFieldInfo wfField : list){
			if(wfField.getVc_comment() != null && !"".equals(wfField.getVc_comment())){
				sql = "comment on column " + tablename + "." + wfField.getVc_fieldname() 
					+ " is '" + wfField.getVc_comment() + "'";
				sqlList.add( sql);
			}
		}
		return sqlList;
	}
	
	public String getTableCommentSql(String vc_name, String tablename){
		String sql = "comment on table " + tablename + " is '" + vc_name + "'";
		return sql;
	}
	
	public List<WfTableInfo> getAllTableNotLc(String lcid){
		return tableInfoDao.getAllTableNotLc( lcid);
	}
	
	public List<String> getFieldSql(List<WfFieldInfo> list, String tablename){
		List<String> sqlList = new ArrayList<String>();
		String sql = "";
		for(WfFieldInfo wfField : list){
			sql = "alter table " + tablename + " add "+wfField.getVc_fieldname();
			switch (Integer.parseInt(wfField.getI_fieldtype())) {
				case 0:
					sql += " VARCHAR2(" + wfField.getI_length() + ")";
					break;
				case 1:
					sql += " DATE";
					break;
				case 2:
					sql += " NUMBER";
					break;
				case 3:
					sql += " CLOB";
					break;
				case 4:
					sql += " BLOB";
					break;
				default:
					break;
			}
			if(!"1".equals(wfField.getB_value())){
				sql += " NOT NULL";
			}
			if(wfField.getVc_value() != null && !"".equals(wfField.getVc_value())){
				sql += " DEFAULT "+wfField.getVc_value();
			}
			sqlList.add( sql);
		}
		return sqlList;
	}
	
	public boolean createTable(String sql){
		int result = jdbcBase.executeSQL(sql);
		System.out.println("TableInfoService.create()================="+sql);
		if(result == 1){
			return true;
		}else{
			return false;
		}
	}

	public List<WfTableInfo> getTableByTableName(String vc_tablename) {
		return tableInfoDao.getTableByTableName(vc_tablename);
	}
	
	public List<WfTableInfo> getTableByMap(HashMap<String,String> map) {
		return tableInfoDao.getTableByMap(map);
	}

	
	private List<WfProcess> insertNewProcess(String pdfPath , String title,WfProcess oldProcess,String nextNodeId,
			Integer isEnd,String m_userIds,String c_userIds,String userId,
			String instanceId,int show,String formId,String specialProcess,
			boolean isHaveProcess,String isChildWf,String cType,String relation,
			String finstanceId, String middlePdf,String wcqx, String oldCommontJson,
			String PDFPath, String formPage,String self_loop)  {
		WfNode p_wfNode = tableInfoDao.getWfNodeById(nextNodeId); // 获取节点属性
		String route_type = "0";
		Employee employee = null;
		
		if(p_wfNode!=null){		//获取该节点的路由类型, route_type=3:并行结合
			route_type = p_wfNode.getWfn_route_type();
			if(route_type!=null && route_type.equals("3")){
				List<Employee> emplist = employeeDao.getEmployeeByEmployeeIds(m_userIds);
				if(emplist!=null && emplist.size()>0){
					employee = emplist.get(emplist.size()-1);
				}
			}
		}
		List<WfProcess> returnProcess = new ArrayList<WfProcess>();
		Date nowTime = new Date();
		WebSocketUtil webSocket = new WebSocketUtil();
		if(c_userIds != null){
			String[] c_user_arr = new String[0];//抄送ID数组
			if(!"".equals(c_userIds)){
				 c_user_arr = c_userIds.split(",");
			}
			String[] m_user_arr = new String[0]; //主送ID数组
			if(!"".equals(m_userIds)&&m_userIds!=null){
				m_user_arr = m_userIds.split(",");
			}
			int len = c_user_arr.length+m_user_arr.length;
			
			WfNode wfNode = pendingDao.getWfNode(nextNodeId);
			String  deadline = wfNode.getWfn_deadline();
			String deadlineNow=deadline;
			if(wcqx!=null&&!"".equals(wcqx)){
				deadline = wcqx;
			}
			String  deadlineunit = wfNode.getWfn_deadlineunit();
			String itemid = oldProcess.getItemId();
			WfItem wfItem = itemDao.getItemById(itemid);
			Date jdqxDate =null;
			if(Utils.isNotNullOrEmpty(deadline)&&!deadline.equals("0")){
				jdqxDate = getEndDate(nowTime, deadline, deadlineunit );
			}
			Integer action_status = wfNode.getAction_status();	
			long cz=0;//节点期限和输入的期限的差值
			Date zhqxDate = null;
			if(wfItem!=null){
				String wcsx = wfItem.getVc_wcsx();		//办件完成期限
				if(Utils.isNotNullOrEmpty(wcsx)&&Utils.isNumber(wcsx)
						&&Utils.isNotNullOrEmpty(wcqx)&&Utils.isNumber(wcqx)){
					wcsx = (Integer.valueOf(wcsx)+Integer.valueOf(wcqx))+"";
					if(Utils.isNotNullOrEmpty(deadlineNow)&&Utils.isNumber(deadlineNow)){
						cz=Integer.valueOf(wcqx)-Integer.valueOf(deadlineNow);
					}
				}
				String workFlowid = oldProcess.getWfUid() ;
				WfProcess wfProcess = pendingDao.getFirstStepWfProcess(instanceId,workFlowid);
				Date apply_date = oldProcess.getApplyTime();
				String fInstancdUid = oldProcess.getWfInstanceUid();
				if(fInstancdUid!=null && !fInstancdUid.equals("")){  //该流程为子流程
					wfProcess = pendingDao.getFirstStepWfProcess(fInstancdUid,"");
				}
				if(wfProcess!=null){
					apply_date = wfProcess.getApplyTime();
				}
				
				//计算最后的工作期限
				zhqxDate = oldProcess.getZhqxDate();
				if(zhqxDate!=null){
					if(cz!=0){
						zhqxDate = getEndDate(zhqxDate, cz+"", "0");	//默认为工作日
					}
				}
				if(zhqxDate==null){
					if(Utils.isNotNullOrEmpty(wcsx)&&!wcsx.equals("0")){
						zhqxDate = getEndDate(apply_date, wcsx, "0");	//默认为工作日
					}
				}
			}
			for(int i=0;i<len;i++){
				WfProcess newProcess = new WfProcess();
				newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
				newProcess.setIsBack("0");
				if(!("").equals(cType) && ("1").equals(cType)){//多个实例
					if(!("true").equals(isChildWf)){//是否第一次进入子流程
						newProcess.setfInstancdUid(oldProcess.getfInstancdUid());
						newProcess.setWfInstanceUid(oldProcess.getWfInstanceUid());
						if(CommonUtil.stringNotNULL(self_loop)
								&& "1".equals(self_loop)){
							newProcess.setStepIndex(oldProcess.getStepIndex());    
						}else{
							newProcess.setStepIndex(oldProcess.getStepIndex()+1);    
						}       
					}else{
						String insert=null;
						insert=UuidGenerator.generate36UUID();
						newProcess.setWfInstanceUid(insert);
						List<SendAttachments> list = null;
						List<SendAttachments> oldlist = null;
						if(oldProcess!=null&&oldProcess.getDoType()==3){
							list = pendingDao.findSendAttsByDocguid(oldProcess.getWfInstanceUid()+"fj");
							oldlist = pendingDao.findSendAttsByDocguid(oldProcess.getWfInstanceUid()+"oldfj");
						}else{
							list = pendingDao.findSendAttsByDocguid(finstanceId+"fj");
							oldlist = pendingDao.findSendAttsByDocguid(finstanceId+"oldfj");
						}
						if(oldlist!=null&&oldlist.size()>0){
							for(int j=0;j<oldlist.size();j++){
								SendAttachments sendAttachments = oldlist.get(j);
								SendAttachments newSendAttachments = new SendAttachments();
								newSendAttachments.setDocguid(insert+"oldfj");
								newSendAttachments.setFilename(sendAttachments.getFilename());
								newSendAttachments.setFiletype(sendAttachments.getFiletype());
								newSendAttachments.setFilesize(sendAttachments.getFilesize());
								newSendAttachments.setLocalation(sendAttachments.getLocalation());
								newSendAttachments.setFileindex(sendAttachments.getFileindex());
								newSendAttachments.setFiletime(sendAttachments.getFiletime());
								newSendAttachments.setEditer(sendAttachments.getEditer());
								newSendAttachments.setTitle(sendAttachments.getTitle());
								newSendAttachments.setType(sendAttachments.getType());
								newSendAttachments.setData(sendAttachments.getData());
								newSendAttachments.setPdfData(sendAttachments.getPdfData());
								pendingDao.addSendAtts(newSendAttachments);
							}
						}
						if(list!=null&&list.size()>0){
							for(int j=0;j<list.size();j++){
								SendAttachments sendAttachments = list.get(j);
								SendAttachments newSendAttachments = new SendAttachments();
								newSendAttachments.setDocguid(insert+"fj");
								newSendAttachments.setFilename(sendAttachments.getFilename());
								newSendAttachments.setFiletype(sendAttachments.getFiletype());
								newSendAttachments.setFilesize(sendAttachments.getFilesize());
								newSendAttachments.setLocalation(sendAttachments.getLocalation());
								newSendAttachments.setFileindex(sendAttachments.getFileindex());
								newSendAttachments.setFiletime(sendAttachments.getFiletime());
								newSendAttachments.setEditer(sendAttachments.getEditer());
								newSendAttachments.setTitle(sendAttachments.getTitle());
								newSendAttachments.setType(sendAttachments.getType());
								newSendAttachments.setData(sendAttachments.getData());
								newSendAttachments.setPdfData(sendAttachments.getPdfData());
								pendingDao.addSendAtts(newSendAttachments);
							}
						}
						newProcess.setfInstancdUid(oldProcess.getWfInstanceUid());
						newProcess.setStepIndex(1);    
					}
					newProcess.setIsChildWf("1");
					newProcess.setIsManyInstance("1");
				}else if(("0").equals(cType)){//单个实例
					newProcess.setIsChildWf("1");
					newProcess.setIsManyInstance("0");
					newProcess.setWfInstanceUid(instanceId);
					newProcess.setfInstancdUid(oldProcess.getfInstancdUid());
					if(CommonUtil.stringNotNULL(self_loop)
							&& "1".equals(self_loop)){
						newProcess.setStepIndex(oldProcess.getStepIndex());    
					}else{
						newProcess.setStepIndex(oldProcess.getStepIndex()+1);    
					}       
				}else{
					newProcess.setWfInstanceUid(instanceId);
					if(CommonUtil.stringNotNULL(self_loop)
							&& "1".equals(self_loop)){
						newProcess.setStepIndex(oldProcess.getStepIndex());    
					}else{
						newProcess.setStepIndex(oldProcess.getStepIndex()+1);    
					}       
				}
				
				if("true".equals(isChildWf)){	//子流程且第一步：插入该步骤的当前步骤
					String fInstanceId = oldProcess.getfInstancdUid();
					//查找该流程的stepIndex
					Integer pstep_index = tableInfoDao.getMaxStepIndex(fInstanceId);
					newProcess.setpStepIndex(pstep_index);
				}
				newProcess.setNodeUid(nextNodeId);
				newProcess.setItemId(oldProcess.getItemId());
				newProcess.setFormId(wfNode.getWfn_defaultform());
				newProcess.setOldFormId(oldProcess.getFormId());
				newProcess.setFromUserId(userId);
				newProcess.setOwner(oldProcess.getOwner());
				newProcess.setApplyTime(nowTime);
				newProcess.setJdqxDate(jdqxDate);
				newProcess.setZhqxDate(zhqxDate);
				newProcess.setIsOver(Constant.NOT_OVER);
				newProcess.setIsEnd(isEnd);
				newProcess.setProcessTitle((title==null || "".equals(title)) ? oldProcess.getProcessTitle() : title);
				newProcess.setWfUid(oldProcess.getWfUid());
				if(null != p_wfNode && p_wfNode.getWfn_route_type().equals("6")){
					if(i == (len-1)){
						newProcess.setIsShow(1);
					}else{
						newProcess.setIsShow(0);
					}
				}else{
					newProcess.setIsShow(show);
				}
				newProcess.setStatus("0");
				newProcess.setPdfPath(pdfPath);
				newProcess.setCommentJson(oldProcess.getCommentJson());
				newProcess.setAction_status(action_status);
				newProcess.setIsExchanging(0);
				newProcess.setFromNodeid(oldProcess.getNodeUid());
				newProcess.setToNodeid(nextNodeId);
				newProcess.setAllInstanceid(oldProcess.getAllInstanceid());
				newProcess.setMergePdf(oldProcess.getMergePdf());
				newProcess.setFjbProcessId(oldProcess.getFjbProcessId());
				newProcess.setMergePdf(oldProcess.getMergePdf());
				newProcess.setAttachPath(PDFPath);
				newProcess.setFormPage(formPage);
				newProcess.setSort(i+1);
				if(middlePdf!=null && !middlePdf.equals("")){
					newProcess.setMergePdf(middlePdf);
				}
				Employee currentEmp = null;
				if(route_type!=null && route_type.equals("3")){	//并行结合: m_user_arr有值
					currentEmp = employeeDao.queryEmployeeById(m_user_arr[len-i-1]);
					newProcess.setUserUid(m_user_arr[len-i-1]);	
					//tableIndex排序
					if(employee.getEmployeeGuid().equals(m_user_arr[len-i-1])){
						newProcess.setIsMaster(2);			
					}else{
						newProcess.setIsMaster(1);
					}
					oldProcess.setSendPersonId(m_user_arr[len-i-1]);
					if(show==1){
						try {
							webSocket.apnsPush(oldProcess.getProcessTitle(), oldProcess.getUserUid(), "", "", "", m_user_arr[len-i-1]);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}else{
					if(i>=c_user_arr.length ){
						//主送人ID及标识值
						currentEmp =  employeeDao.queryEmployeeById(m_user_arr[len-i-1]);
						newProcess.setUserUid(m_user_arr[len-i-1]);
						newProcess.setIsMaster(1);
						if(("true").equals(isChildWf)){
							newProcess.setDoType(1);
						}
						oldProcess.setSendPersonId(m_user_arr[len-i-1]);
						if(show==1){
							try {
								webSocket.apnsPush(oldProcess.getProcessTitle(), oldProcess.getUserUid(), "", "", "", m_user_arr[len-i-1]);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}else{
						//抄送
						currentEmp = employeeDao.queryEmployeeById(c_user_arr[i]);
						newProcess.setUserUid(c_user_arr[i]);
						if(("true").equals(isChildWf)){
							newProcess.setIsMaster(1);
							newProcess.setDoType(2);
						}else{
							if(route_type.equals("1")){
								newProcess.setIsMaster(1);
							}else{
								newProcess.setIsMaster(0);
							}
						}
						oldProcess.setSendPersonId(c_user_arr[i]);
						if(show==1){
							try {
								webSocket.apnsPush(oldProcess.getProcessTitle(), oldProcess.getUserUid(), "", "", "", c_user_arr[i]);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				}
				newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
				if(i==len-1){
					if(middlePdf!=null && !middlePdf.equals("")){
						if(oldProcess!=null && oldProcess.getDoType()!=null&& oldProcess.getDoType()==3){
							oldProcess.setPdfPath(middlePdf+","+middlePdf);
						}
					}
				}
				if(isHaveProcess==true){
					tableInfoDao.update(oldProcess);
				}else{
					tableInfoDao.save(oldProcess);//插第一步，需要给发送下一步的人员赋值
				}
	 			tableInfoDao.save(newProcess);//下一步
	 			//如果是督办办件先入库等待定时器扫描发短信
	 			addDuBanList(newProcess);
				addXcc(newProcess);
				returnProcess.add(newProcess);
			}
		}
		return returnProcess;
	}
	
	public int parseJsonArray(String json) throws Exception{
		if(json==null || json.equals("")){
			return 0;
		}
		org.json.JSONArray jsonArray;
		try {
			jsonArray = new org.json.JSONArray(json);
			int size = jsonArray.length();
			int nums = 0;
			for(int i = 0; i<size; i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Integer page = jsonObject.get("page")==null?0:(Integer)jsonObject.get("page");
				if(page!=null && page!=0){
					nums ++;
				}
			}
			return nums;
		} catch (Exception e) {
			org.json.JSONObject obj = new org.json.JSONObject(json);
			jsonArray	= obj.getJSONArray("pages");
			int size = jsonArray.length();
			int nums = 0;
			for(int i = 0; i<size; i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Integer page = jsonObject.get("page")==null?0:(Integer)jsonObject.get("page");
				if(page!=null && page!=0){
					nums ++;
				}
			}
			return nums;
		}
		
	}
	
	
	
	
	
	/**
	 * 根据期限,期限类型查询时间
	 * @param nowTime	当前时间(开始时间)
	 * @param deadline	间隔阶段
	 * @param deadlineunit	间隔类型
	 * @return
	 */
	public Date getEndDate(Date nowTime, String deadline, String deadlineunit){
		if(deadlineunit==null || deadlineunit.equals("")){
			return null;
		}
		int count = 0 ;
		if(deadline!=null && !deadline.equals("")){
			count = Integer.parseInt(deadline);
		}
		Date endDate = null;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(nowTime);
		if(deadlineunit.equals("0")){	
			if(count<0){
				//工作日：使用工作日历
				endDate = pendingDao.getEndDate(count-1, nowTime);
			}else{
				//工作日：使用工作日历 国土oa 不要 +1 天
				endDate = pendingDao.getEndDate(count, nowTime);
				//	endDate = pendingDao.getEndDate(count+1, nowTime);
			}
		}else if("1".equals(deadlineunit)){		//小时
			cal.add(GregorianCalendar.HOUR,count);
			endDate = cal.getTime();
		}else if("2".equals(deadlineunit)){		//天,包含工作日
			cal.add(GregorianCalendar.DATE, count);
			endDate = cal.getTime();
		}else if("3".equals(deadlineunit)){		//周
			cal.add(GregorianCalendar.DATE, 7*count);
			endDate = cal.getTime();
		}else if("4".equals(deadlineunit)){
			cal.add(GregorianCalendar.MONTH, count);
			endDate = cal.getTime();
		}else if("5".equals(deadlineunit)){
			cal.add(GregorianCalendar.YEAR, count);
			endDate = cal.getTime();
		}
		return endDate;
	}
	
	
	/**
	 * 解析以逗号隔开的字符串，过滤掉其中的空字符串，返回一个list集合
	 * 
	 * @param oriString
	 * @return
	 */
	private List<String> filter(String oriString){
		if (oriString.endsWith(",")){
			oriString = oriString.substring(0, oriString.length() - 1);
		}
		String[] ids = oriString.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0, l = ids.length; i < l; i++){
			if (ids[i].trim().length() > 0)
				list.add(ids[i]);
		}
		return list;
	}
	
	/**
	 * 发送流程
	 * create by zhuxc
	 * 2013-4-17 下午9:49:00
	 * @param title		主题
	 * @param m_userIds  主送人  
	 * @param c_userIds  抄送人  
	 * @param userId    办理人
	 * @param workFlowId 流程id
	 * @param nodeId     节点id
	 * @param oldProcessId  当前处理的processID  第一步为null
	 * @param nextNodeId  下一步节点ID
	 * @throws Exception
	 */
	public List<WfProcess> sendProcess(String title,String m_userIds,String c_userIds,String userId,
			String workFlowId,String nodeId,String f_proceId, String oldProcessId, 
			String nextNodeId,String instanceId,String itemId,String formId,String oldformId,
			String pdfPath,String trueJson,String isChildWf,String cType,String relation,
			String finstanceId,String newInstanceIdForChildWf,String specialProcess,String middlePdf, 
			String wcsx,String firstOverPdf, String PDFPath, String formPage, Integer urgency,String self_loop)
					throws Exception {
		// 判断下一步是否是本流的结束步骤,isEnd=1表示是结束---根据下一步的节点查下下一步的节点
		List<WfNode> nodes = workflowBasicFlowDao.getNode(workFlowId, nextNodeId);
		List<WfChild> childs = workflowBasicFlowDao.showChildOfWf(workFlowId, nextNodeId);
		Employee emp = employeeDao.queryEmployeeById(userId);
		String wfPinstanceId = "";
		Integer isEnd = 0;
		if ((nodes==null || nodes.size()==0) && (childs==null || childs.size()==0) ){
			isEnd = 1;
		}
		// 新建或更新oldProcess
		WfProcess oldProcess = pendingDao.getProcessByID(oldProcessId);  
		Date nowTime = new Date();
		List<WfProcess> tempList = new ArrayList<WfProcess>();
		if (oldProcessId !=null && oldProcess != null && !("true").equals(isChildWf)){
			// 更新
			String oldCommentJson = "";
			if(oldProcess!=null){
				oldCommentJson = oldProcess.getCommentJson();
			}
			oldProcess.setFinshTime(nowTime);
			oldProcess.setProcessTitle(title);
			if(!("").equals(cType) && ("true").equals(isChildWf)){
				oldProcess.setIsOver(Constant.NOT_OVER); // 设置当前步骤已经结束
			}else{
				oldProcess.setIsOver(Constant.OVER); // 设置当前步骤已经结束
			}
			// 当前步骤的pdf  取当前的 待办 下一步的已办 
			//String oldPdfPath = oldProcess.getPdfPath().split(",")[0];
			//oldProcess.setPdfPath(oldPdfPath+ ","+pdfPath.split(",")[1]);
			oldProcess.setCommentJson(trueJson);
			//-------------------港闸区--表单的特殊处理---------start----------------//
			if(specialProcess!=null && !("").equals(specialProcess)){
				oldProcess.setFromNodeid(nodeId);
				oldProcess.setToNodeid(nextNodeId);
				String[] specNodes = specialProcess.split(",");
				for (String spNode : specNodes) {
					String spNode1 = spNode.split(";")[0]; //分派工单
					String spNode2 = spNode.split(";")[1]; //部门处理
					//设置特殊名称:部门处理-->分派工单==退单  , 办结待回访-->分派工单==再次交办
					if(oldProcess.getNodeUid().equals(spNode2)&&nextNodeId.equals(spNode1)){
						oldProcess.setNodeName("退单");
						break;
					}
				}
			}
			//-------------------港闸区--表单的特殊处理----------end---------------//
//			tableInfoDao.update(oldProcess);
			//委托人id
			String entrustUser = oldProcess.getEntrustUserId();
			//没有委托人id，则用该用户id
			if(CommonUtil.stringIsNULL(entrustUser)){
				entrustUser = oldProcess.getUserUid();
			}
			if(oldProcess.getIsBack() == null){
				oldProcess.setIsBack("0");
			}
			
			if(CommonUtil.stringNotNULL(self_loop) && "1".equals(self_loop)){
				//自循环模式不更新相关委托人
			}else{
				//更新相关委托人OVER
				//updateOverForEntrust(oldProcess.getWfUid(), oldProcess.getWfInstanceUid(), oldProcess.getNodeUid(), entrustUser,oldProcess.getWfProcessUid());
			}	
			
			//用户判断是插库还是更新
			boolean isHaveProcess = true;
			
			if(trueJson!=null && !("").equals(trueJson)){
				//更新同级步骤的手写意见--主送抄送
				List<WfProcess> processList = tableInfoDao.findWfProcessByInstanceIdAndStepIndex(instanceId,oldProcess.getStepIndex());
				for (WfProcess wfProcess : processList) {
					wfProcess.setCommentJson(trueJson);
					tableInfoDao.update(wfProcess);
				}
			}
			int show = 0;
			
			//子流程的第一步要显示
			if(!("").equals(cType) && ("1").equals(cType)){ //多例模式没有并行完全的概念
				show = 1;
			}
			Integer stepIndex = oldProcess.getStepIndex();
			if(stepIndex!=null && stepIndex==1){		//第一步发送下一步时,isShow改为1; (第一步执行暂存后操作)
				show = 1;
				//在插入办件表之前，查一下该办件是否存在，如果存在，进行更新操作，否则进行插入操作
				DoFile beforeDoFile = tableInfoDao.getDoFileByElements("", instanceId);
				if(null != beforeDoFile && StringUtils.isNotBlank(beforeDoFile.getDoFile_id())){
					beforeDoFile.setDoFile_title(title);
					beforeDoFile.setItemId(itemId);
					beforeDoFile.setWorkflowId(workFlowId);
					beforeDoFile.setInstanceId(instanceId);
					beforeDoFile.setFormId(formId);
					beforeDoFile.setNodeId(nodeId);
					if(null != urgency){
						beforeDoFile.setUrgency(urgency);
					}
					if(oldProcess.getFinshTime()!=null && !("").equals(oldProcess.getFinshTime())){
						beforeDoFile.setDo_time(oldProcess.getFinshTime());
					}else{
						beforeDoFile.setDo_time(oldProcess.getApplyTime());
					}
					tableInfoDao.updateDoFile(beforeDoFile);
				}else{
					//插入办件表
					DoFile doFile = new DoFile();
					doFile.setDoFile_title(title);
					doFile.setItemId(itemId);
					doFile.setWorkflowId(workFlowId);
					doFile.setInstanceId(instanceId);
					doFile.setFormId(formId);
					String itemName = tableInfoDao.findItemNameById(oldProcess.getItemId());
					doFile.setItemName(itemName);
					doFile.setNodeId(nodeId);
					if(oldProcess.getFinshTime()!=null && !("").equals(oldProcess.getFinshTime())){
						doFile.setDo_time(oldProcess.getFinshTime());
					}else{
						doFile.setDo_time(oldProcess.getApplyTime());
					}
					if(null != urgency){
						doFile.setUrgency(urgency);
					}
					//子流程数据不插入t_wf_core_dofile表中
					if(!("true").equals(isChildWf)){
						tableInfoDao.saveDoFile(doFile);
					}
				}
			}
			
			tempList = insertNewProcess(pdfPath,title,oldProcess,nextNodeId,isEnd, m_userIds, c_userIds, 
					userId,instanceId,show,formId,specialProcess,isHaveProcess,isChildWf,cType,relation,
					finstanceId,"", wcsx,oldCommentJson, PDFPath, formPage,self_loop);
			if(tempList != null && tempList.size()>0){
				for(int i = 0 ; i < tempList.size() ; i++){
					if(i == 0 ){
						wfPinstanceId = tempList.get(i).getfInstancdUid();
					}
				}
			}
		} else{
			// 新建 第一步保存发送
			WfProcess newProcess = new WfProcess();
			//第一次进入子流程,入库id需要重新生成
			if(("true").equals(isChildWf)){
				String insert = UuidGenerator.generate36UUID();
				newProcess.setWfProcessUid(insert);
				newProcess.setWfInstanceUid(newInstanceIdForChildWf);
			}else{
				newProcess.setWfInstanceUid(instanceId);
				newProcess.setWfProcessUid(oldProcessId);
				newProcess.setAllInstanceid(instanceId);
			}
			if(!("").equals(cType) && ("1").equals(cType)){//多个实例
				newProcess.setfInstancdUid(finstanceId);
				newProcess.setIsChildWf("1");
				newProcess.setIsManyInstance("1");
				newProcess.setAllInstanceid(finstanceId);
			}else if(("0").equals(cType)){//单个实例
				newProcess.setIsChildWf("1");
				newProcess.setIsManyInstance("0");
				newProcess.setfInstancdUid(finstanceId);
				newProcess.setAllInstanceid(finstanceId);
			}
			//根据父instanceId获取那一步的nodeId
			WfProcess wfProcess = tableInfoDao.getParentProcessByInstanceid(finstanceId);
			if(wfProcess!=null){
				String wfn_nodeId = wfProcess.getNodeUid();
				newProcess.setZhqxDate(wfProcess.getZhqxDate());
				if(wfn_nodeId!=null){
					WfNode p_wfNode = tableInfoDao.getWfNodeById(wfn_nodeId); 
					if(p_wfNode!=null){
						Integer actionStatus = p_wfNode.getAction_status();
						if(actionStatus!=null && actionStatus ==1){	//表示该办件应该为等办办件
							wfProcess.setAction_status(2);
							tableInfoDao.update(wfProcess);
						}
					}
				}
			}
			if(cType!=null && (("1").equals(cType) || ("0").equals(cType) )){
				newProcess.setIsShow(0);
			}else{
				newProcess.setIsShow(1);
			}
			newProcess.setNodeUid(nodeId);
			newProcess.setFromUserId(userId);
			newProcess.setOwner(userId);
			newProcess.setApplyTime(nowTime);
			newProcess.setFinshTime(nowTime);
			newProcess.setIsOver(Constant.OVER);
			newProcess.setIsEnd(isEnd);
			newProcess.setProcessTitle(title);
			newProcess.setWfUid(workFlowId);
			newProcess.setItemId(itemId);
			newProcess.setFormId(formId);
			newProcess.setAttachPath(PDFPath);
			newProcess.setFormPage(formPage);
			//区分发子流程打开第一步时的formId和普通第一步的formId
			if(!("").equals(oldformId)){
				newProcess.setOldFormId(oldformId);
			}else{
				newProcess.setOldFormId(formId);
			}
			if(("true").equals(isChildWf)){
				newProcess.setFromNodeid(wfProcess.getToNodeid());
			}else{
				newProcess.setFromNodeid(nodeId);
			}
			newProcess.setToNodeid(nodeId);
			//主送人ID及标识值
			newProcess.setUserUid(userId);
			newProcess.setUserDeptId(emp.getDepartmentGuid());
			newProcess.setIsMaster(1);
			// 第一步 pdf取  first pdf   
			if(!("").equals(formId)){
				String formPdfPath = workflowBasicFlowDao.findFormPdf(formId);
				formPdfPath = PathUtil.getWebRoot() + "form/html/"+formPdfPath;
				if(firstOverPdf!= null && !"".equals(firstOverPdf)){
					newProcess.setPdfPath(formPdfPath+","+firstOverPdf);
				}else{
					String [] pdfPaths = pdfPath.split(",");
					if(pdfPaths.length>2){
						newProcess.setPdfPath(formPdfPath+","+pdfPath.split(",")[1]);
					}
				}
			}else{
				newProcess.setPdfPath(pdfPath);
			}
			if(("true").equals(isChildWf)){
				newProcess.setDoType(3);
			}
			newProcess.setStepIndex(1);
			newProcess.setStatus("0");
			newProcess.setCommentJson(trueJson);
			newProcess.setIsExchanging(0);
			newProcess.setIsBack("0");
			newProcess.setFjbProcessId(oldProcess==null?null :oldProcess.getFjbProcessId());
			//第一步先插入一条数据
//			tableInfoDao.save(newProcess);
			//用户判断是插库还是更新
			boolean isHaveProcess = false;
			//插入下一步
		//	wfPinstanceId = 
			tempList =	insertNewProcess(pdfPath,title,newProcess,nextNodeId,isEnd, m_userIds, 
					c_userIds, userId,instanceId,1,formId,specialProcess,isHaveProcess,isChildWf,
					cType,relation,finstanceId,middlePdf,wcsx,"", PDFPath, formPage,self_loop);
			if(tempList != null && tempList.size()>0){
				for(int i = 0 ; i < tempList.size() ; i++){
					if(i == 0 ){
						wfPinstanceId = tempList.get(i).getfInstancdUid();
					}
				}
			}
			/*if(!("").equals(f_proceId)){
				//更新主流程表
				WfProcess fWfProcess =  pendingDao.getProcessByID(f_proceId);  
				fWfProcess.setFromNodeid(fWfProcess.getNodeUid());
				fWfProcess.setToNodeid(nodeId);
				tableInfoDao.update(fWfProcess);
			}*/
			//在插入办件表之前，查一下该办件是否存在，如果存在，进行更新操作，否则进行插入操作
			DoFile beforeDoFile = tableInfoDao.getDoFileByElements("", instanceId);
			if(null != beforeDoFile && StringUtils.isNotBlank(beforeDoFile.getDoFile_id())){
				beforeDoFile.setDoFile_title(title);
				beforeDoFile.setItemId(itemId);
				beforeDoFile.setWorkflowId(workFlowId);
				beforeDoFile.setInstanceId(instanceId);
				beforeDoFile.setFormId(formId);
				beforeDoFile.setNodeId(nodeId);
				if(newProcess.getFinshTime()!=null && !("").equals(newProcess.getFinshTime())){
					beforeDoFile.setDo_time(newProcess.getFinshTime());
				}else{
					beforeDoFile.setDo_time(newProcess.getApplyTime());
				}
				if(null != urgency){
					beforeDoFile.setUrgency(urgency);
				}
				tableInfoDao.updateDoFile(beforeDoFile);
			}else{
				//插入办件表
				DoFile doFile = new DoFile();
				doFile.setDoFile_title(title);
				doFile.setItemId(itemId);
				doFile.setWorkflowId(workFlowId);
				doFile.setInstanceId(instanceId);
				doFile.setFormId(formId);
				String itemName = tableInfoDao.findItemNameById(newProcess.getItemId());
				doFile.setItemName(itemName);
				doFile.setNodeId(nodeId);
				if(null != urgency){
					doFile.setUrgency(urgency);
				}
				if(newProcess.getFinshTime()!=null && !("").equals(newProcess.getFinshTime())){
					doFile.setDo_time(newProcess.getFinshTime());
				}else{
					doFile.setDo_time(newProcess.getApplyTime());
				}
				//子流程数据不插入t_wf_core_dofile表中
				if(!("true").equals(isChildWf)){
					tableInfoDao.saveDoFile(doFile);
				}
			}
		}
		WfNode nowNode = workflowBasicFlowDao.findNodeById(nodeId);
		Integer isAotuSend = nowNode.getWfn_isAutoSend();
		if(null != isAotuSend && isAotuSend.equals(1)){
			toAutoSendNext(tempList);
		}
		
		return tempList;
	}
	
	/**
	 * 多子流程插入新的步骤
	 */
	public WfProcess sendNewProcess(String vc_title, String userIds, String employeeGuid,
			String newInstanceId, String workFlowId, String nodeId, String fromNodeId, 
			String toNodeId, WfProcess oldProcess, WfNode wfNode, String isMerge ,
			String commentJson,String pdfPath,String doType,String wfc_ctype,Sw sw, String wcqx, Integer pStepIndex) {
		if (oldProcess !=null){
			Date nowTime = new Date();
			String instanceId = oldProcess.getWfInstanceUid();
			// 更新--当前步骤
			oldProcess.setFinshTime(nowTime);
			oldProcess.setProcessTitle(vc_title);
			oldProcess.setIsOver(Constant.NOT_OVER);
			oldProcess.setFromNodeid(nodeId);
			oldProcess.setToNodeid(fromNodeId);
			oldProcess.setCommentJson(commentJson);
			//插入下一步
			if(userIds != null){
				String  deadline = wfNode.getWfn_deadline();
				Date jdqxDate = null;
				if(Utils.isNotNullOrEmpty(deadline)&&!deadline.equals("0")){
					if(wcqx!=null&&!"".equals(wcqx)){
						deadline=wcqx;
					}
					String  deadlineunit = wfNode.getWfn_deadlineunit();
					jdqxDate = getEndDate(nowTime, deadline, deadlineunit);
				}
				Integer action_status = wfNode.getAction_status();	
				String itemid = oldProcess.getItemId();
				WfItem wfItem = itemDao.getItemById(itemid);
				String wcsx = wfItem.getVc_wcsx();		//办件完成期限
				Date apply_date = oldProcess.getApplyTime();
				Date zhqxDate = getEndDate(apply_date, wcsx, "0");	//默认为工作日
				//插入下一步
				WfProcess newProcess = new WfProcess();
					newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
					//默认都为多例子
					newProcess.setfInstancdUid(instanceId);
					newProcess.setWfInstanceUid(newInstanceId);
					newProcess.setIsChildWf("1");
					if ("1".equals(wfc_ctype)){
						newProcess.setIsManyInstance("1");
					}else{
						newProcess.setIsManyInstance("0");
					}
					newProcess.setNodeUid(wfNode.getWfn_id());
					newProcess.setItemId(oldProcess.getItemId());
					newProcess.setFormId(wfNode.getWfn_defaultform());
					newProcess.setOldFormId(oldProcess.getFormId());
					newProcess.setFromUserId(employeeGuid);
					newProcess.setOwner(oldProcess.getOwner());
					newProcess.setApplyTime(nowTime);
					newProcess.setJdqxDate(jdqxDate);
					newProcess.setZhqxDate(zhqxDate);
					newProcess.setIsOver(Constant.NOT_OVER); //正常待办--NOT_OVER
					newProcess.setIsEnd(0);
					newProcess.setProcessTitle(vc_title);
					newProcess.setWfUid(workFlowId);
					newProcess.setFromNodeid(fromNodeId);
					newProcess.setToNodeid(toNodeId);
					newProcess.setIsShow(1);
					newProcess.setStepIndex(1);    
					newProcess.setStatus("0");
					newProcess.setAction_status(action_status);
					newProcess.setAllInstanceid(oldProcess.getAllInstanceid());
					newProcess.setFjbProcessId(oldProcess.getFjbProcessId());
					newProcess.setPdfPath(pdfPath);
					newProcess.setpStepIndex(pStepIndex);
					if(("1").equals(isMerge)){
						newProcess.setIsExchanging(1);
						newProcess.setCommentJson("");
					}else{
						newProcess.setIsExchanging(0);
						newProcess.setCommentJson(oldProcess.getCommentJson());
						//newProcess.setCommentJson(commentJson);
					}
					Employee currentEmp = employeeDao.queryEmployeeById(userIds);
					newProcess.setUserUid(userIds);
					newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
					newProcess.setIsMaster(1);
					newProcess.setIsBack("0");
					if(("1").equals(doType)){
						//主办
						newProcess.setDoType(1);
					}else if(("2").equals(doType)){
						//协办
						newProcess.setDoType(2);
					}
					oldProcess.setAction_status(2);
					oldProcess.setIsOver(Constant.NOT_OVER);
					oldProcess.setSendPersonId(userIds);
				if(oldProcess.getIsBack() == null){
					oldProcess.setIsBack("0");
				}
				tableInfoDao.update(oldProcess);//更新历史记录
				tableInfoDao.save(newProcess);//下一步
				if(("1").equals(isMerge) || isMerge.equals("2")){	//插入待收
		 			DoFileReceive dfr = new DoFileReceive();
			 		dfr.setInstanceId(newProcess.getWfInstanceUid());
			 		dfr.setpInstanceId(instanceId);
			 		dfr.setProcessId(newProcess.getWfProcessUid());
			 		dfr.setFormUserId(employeeGuid);
			 		dfr.setfProcessId(oldProcess.getWfProcessUid());
			 		if(isMerge.equals("1")){
			 			dfr.setToUserId(userIds);
			 		}else{
			 			dfr.setToDepartId(userIds);
			 		}
			 		dfr.setApplyDate(nowTime);
			 		dfr.setType(1);
			 		dfr.setStatus(0);
			 		// 是否回复 为0
			 		dfr.setIsReback(0);
	//		 		dfr.setReceiveType(0);
		 			tableInfoDao.addDoFileReceive(dfr);
		 			Sw newSw = new Sw();
					newSw.setLwbt(sw.getLwbt());
					newSw.setLwdw(sw.getLwdw());
					newSw.setLwh(sw.getLwh());
					newSw.setYfdw(sw.getYfdw());
					newSw.setGwlx(sw.getGwlx());
					newSw.setFs(sw.getFs());
					newSw.setFwsj(nowTime);
					newSw.setInstanceid(newProcess.getWfInstanceUid());
					tableInfoDao.addSw(newSw);
				}
				return newProcess;	
			}else{
				return null;
			}
		}else{
			return null;
		}
		
	}
	
	/**
	 * 假节点分发插一条步骤数据入库
	 */
	public void addNewProcessOfFake(WfProcess oldProcess,String fromNodeId){
		if (oldProcess !=null && oldProcess != null){
			Date nowTime = new Date();
			//插入下一步
			WfProcess newProcess = new WfProcess();
				newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
				newProcess.setfInstancdUid(oldProcess.getfInstancdUid());
				newProcess.setWfInstanceUid(oldProcess.getWfInstanceUid());
				newProcess.setWfUid(oldProcess.getWfUid());
				newProcess.setFinshTime(nowTime);
				newProcess.setApplyTime(nowTime);
				newProcess.setProcessTitle(oldProcess.getProcessTitle());
				//设置成not_over---且为等办
				newProcess.setIsOver(Constant.OVER);
				newProcess.setFromNodeid(fromNodeId);
				newProcess.setToNodeid(fromNodeId);
				newProcess.setPdfPath(oldProcess.getPdfPath());
				newProcess.setCommentJson(oldProcess.getCommentJson());
				newProcess.setNodeUid(fromNodeId);
				newProcess.setItemId(oldProcess.getItemId());
				newProcess.setFormId(oldProcess.getFormId());
				newProcess.setOldFormId(oldProcess.getFormId());
				newProcess.setFromUserId(oldProcess.getUserUid());
				newProcess.setOwner(oldProcess.getOwner());
				newProcess.setJdqxDate(oldProcess.getJdqxDate());
				newProcess.setZhqxDate(oldProcess.getZhqxDate());
				newProcess.setIsEnd(0);
				newProcess.setIsShow(1);
				newProcess.setStepIndex(oldProcess.getStepIndex()+1);    
				newProcess.setStatus("0");
				newProcess.setAction_status(2);
				newProcess.setAllInstanceid(oldProcess.getAllInstanceid());
				newProcess.setIsExchanging(0);
				newProcess.setUserUid(oldProcess.getUserUid());
				newProcess.setUserDeptId(oldProcess.getUserDeptId());
				newProcess.setIsMaster(1);
				newProcess.setIsBack("0");
				newProcess.setDoType(3);
			tableInfoDao.save(newProcess);//下一步
		}
	}
	
	/**
	 * 点击发送按钮发送
	 */
	public void addNewProcessOfSend(WfProcess wfProcess, String userId,Sw sw, 
			String fjpath, Employee emp,String newInstanceId, String dyfs,Date nowTime, String type, String formUserId){
		if(type.equals("send")){
			wfProcess.setIsOver(Constant.OVER);
			tableInfoDao.update(wfProcess);
		}
		//插入下一步
		WfProcess newProcess = new WfProcess();
			newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
			newProcess.setfInstancdUid("");
			newProcess.setWfInstanceUid(newInstanceId);
			newProcess.setWfUid("");
			newProcess.setFinshTime(nowTime);
			newProcess.setApplyTime(nowTime);
			newProcess.setProcessTitle(wfProcess.getProcessTitle());
			newProcess.setIsOver(Constant.NOT_OVER);
			newProcess.setFromNodeid("");
			newProcess.setToNodeid("");
			newProcess.setPdfPath(wfProcess.getPdfPath());
			newProcess.setCommentJson(wfProcess.getCommentJson());
			newProcess.setNodeUid(wfProcess.getNodeUid());
			newProcess.setItemId(wfProcess.getItemId());
			newProcess.setFormId(wfProcess.getFormId());
			newProcess.setOldFormId("");
			newProcess.setFromUserId(wfProcess.getFromUserId());
			newProcess.setOwner(wfProcess.getOwner());
			newProcess.setIsEnd(0);
			newProcess.setIsShow(1);
			newProcess.setStepIndex(1);    
			newProcess.setStatus("0");
			newProcess.setAction_status(0);
			newProcess.setAllInstanceid(newInstanceId);
			newProcess.setIsExchanging(0);
			emp = employeeDao.queryEmployeeById(userId);
			
			newProcess.setUserUid(userId);
			if(emp != null){
				newProcess.setUserDeptId(emp.getDepartmentGuid());
			}else{
				newProcess.setUserDeptId(userId);
			}
			
			newProcess.setIsMaster(1);
			newProcess.setIsBack("0");
		tableInfoDao.save(newProcess);
		
		List<SendAttachments> attZwList = attachmentDao.findAllSendAtts(wfProcess.getWfInstanceUid()+"attzw",null);
		List<SendAttachments> attFjList = attachmentDao.findAllSendAtts(wfProcess.getWfInstanceUid()+"fj",null);
		//剔除多余的附件名称
		List<SendAttachments>  list = new ArrayList<SendAttachments>();
		list.addAll(attZwList);
		list.addAll(attFjList);
		//剔除垃圾数据
		List<SendAttachments>  attList = new ArrayList<SendAttachments>();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getFiletype()!=null){
				attList.add(list.get(i));
			}
		}
	 	//插入待收
		DoFileReceive dfr = new DoFileReceive();
 			dfr.setInstanceId(newProcess.getWfInstanceUid());
 			dfr.setpInstanceId(wfProcess.getWfInstanceUid());
 			dfr.setProcessId(newProcess.getWfProcessUid());
 			if(formUserId!=null && !formUserId.equals("")){
 				dfr.setFormUserId(formUserId);
 			}else{
 	 			dfr.setFormUserId(wfProcess.getUserUid());
 			}
 			//dfr.setToUserId(userId);
 			dfr.setToDepartId(userId);
 			// 是否回复 为0
 			dfr.setIsReback(0);
 			dfr.setApplyDate(nowTime);
 			dfr.setType(1);
 			dfr.setStatus(0);
 			dfr.setReceiveType("1");
 			dfr.setfProcessId(wfProcess.getWfProcessUid());
 			dfr.setPdfpath(fjpath);
 			dfr.setDyfs(dyfs==null?0:Integer.parseInt(dyfs));
 		if(attList!=null && attList.size()>0){
 			
 		}else{
 			dfr.setPdfpath("");		//如果不存在附件,直接跳过
 		}
		tableInfoDao.addDoFileReceive(dfr);
		Sw newSw = new Sw();
			newSw.setLwbt(sw.getLwbt());
			newSw.setLwdw(sw.getLwdw());
			newSw.setLwh(sw.getLwh());
			newSw.setYfdw(sw.getYfdw());	//发文单位
			newSw.setGwlx(sw.getGwlx());
			newSw.setFs(sw.getFs());
			newSw.setFwsj(nowTime);		//发文时间
			newSw.setInstanceid(newInstanceId);
			newSw.setZsdw(sw.getZsdw());
			newSw.setCsdw(sw.getCsdw());
			newSw.setZtc(sw.getZtc());
			newSw.setJjcd(sw.getJjcd());
		tableInfoDao.addSw(newSw);
	}
	
	

	@Override
	public void saveReissueDofileOfSend(String fProcessId, String toDepId,
			Employee emp, String dyfs, DoFileReceive receive) {
		WfProcess wfProcess = tableInfoDao.getProcessById(fProcessId);
		String instanceId = receive.getInstanceId();
		Date nowTime = receive.getApplyDate();
		Sw sw = tableInfoDao.getSwByInstanceId(instanceId);
		wfProcess.setIsOver(Constant.OVER);
		tableInfoDao.update(wfProcess);
		String newInstanceId = UuidGenerator.generate36UUID();
		//插入下一步
		String processId = UuidGenerator.generate36UUID();
		WfProcess newProcess = new WfProcess();
			newProcess.setWfProcessUid(processId);
			newProcess.setfInstancdUid("");
			newProcess.setWfInstanceUid(newInstanceId);
			newProcess.setWfUid("");
			newProcess.setFinshTime(nowTime);
			newProcess.setApplyTime(nowTime);
			newProcess.setProcessTitle(wfProcess.getProcessTitle());
			newProcess.setIsOver(Constant.NOT_OVER);
			newProcess.setFromNodeid("");
			newProcess.setToNodeid("");
			newProcess.setPdfPath(wfProcess.getPdfPath());
			newProcess.setCommentJson(wfProcess.getCommentJson());
			newProcess.setNodeUid(wfProcess.getNodeUid());
			newProcess.setItemId(wfProcess.getItemId());
			newProcess.setFormId(wfProcess.getFormId());
			newProcess.setOldFormId("");
			newProcess.setFromUserId(wfProcess.getFromUserId());
			newProcess.setOwner(wfProcess.getOwner());
			newProcess.setIsEnd(0);
			newProcess.setIsShow(1);
			newProcess.setStepIndex(1);    
			newProcess.setStatus("0");
			newProcess.setAction_status(0);
			newProcess.setAllInstanceid(newInstanceId);
			newProcess.setIsExchanging(0);
			newProcess.setUserUid(toDepId);
			newProcess.setUserDeptId(toDepId);
			newProcess.setIsMaster(1);
			newProcess.setIsBack("0");
		tableInfoDao.save(newProcess);
		DoFileReceive newDofileReceive = null;
		newDofileReceive = new DoFileReceive();
		newDofileReceive.setInstanceId(newInstanceId);
		newDofileReceive.setpInstanceId(receive.getpInstanceId());
		newDofileReceive.setProcessId(processId);
		newDofileReceive.setFormUserId(emp.getEmployeeGuid());
		//newDofileReceive.setToUserId(depId);
		//newDofileReceive.setApplyDate(new Date());
		newDofileReceive.setApplyDate(receive.getApplyDate());
		newDofileReceive.setStatus(0);
		newDofileReceive.setReceiveType("1");
		newDofileReceive.setDyfs((dyfs!=null&& !dyfs.equals(""))?Integer.parseInt(dyfs):1);
		newDofileReceive.setToDepartId(toDepId);
		newDofileReceive.setfProcessId(fProcessId);
		newDofileReceive.setPdfpath(receive.getPdfpath());
		newDofileReceive.setTrueJson(receive.getTrueJson())	;	
		newDofileReceive.setDyfs(receive.getDyfs());
		tableInfoDao.addDoFileReceive(newDofileReceive);
		
		List<SendAttachments> list = attachmentDao.findSendAttachmentListByInstanceId(receive.getInstanceId());
		if(null != list && list.size()>0){
			SendAttachments att = list.get(0);
			SendAttachments newAtt = new SendAttachments();
			String docguid = att.getDocguid();
			String newDocguid = docguid.replace(receive.getInstanceId(), newInstanceId);
			newAtt.setDocguid(newDocguid);
			newAtt.setEditer(att.getEditer());
			newAtt.setFilefrom(att.getFilefrom());
			newAtt.setFileindex(att.getFileindex());
			newAtt.setFilename(att.getFilename());
			newAtt.setFilesize(att.getFilesize());
			newAtt.setFiletime(att.getFiletime());
			newAtt.setFiletype(att.getFiletype());
			newAtt.setIsSeal(att.getIsSeal());
			newAtt.setLocalation(att.getLocalation());
			newAtt.setNodeId(att.getNodeId());
			newAtt.setOperateLogId(att.getOperateLogId());
			newAtt.setPagecount(att.getPagecount());
			newAtt.setTitle(att.getTitle());
			newAtt.setTmPdfPath(att.getTmPdfPath());
			newAtt.setTopdfpath(att.getTopdfpath());
			newAtt.setType(att.getEditer());
			newAtt.setUploadIndex(att.getUploadIndex());
			newAtt.setData(att.getData());
			newAtt.setPdfData(att.getPdfData());
			attachmentDao.addSendAtts(newAtt);
		}
 		
		Sw newSw = new Sw();
		newSw.setLwbt(sw.getLwbt());
		newSw.setLwdw(sw.getLwdw());
		newSw.setLwh(sw.getLwh());
		newSw.setYfdw(sw.getYfdw());	//发文单位
		newSw.setGwlx(sw.getGwlx());
		newSw.setFs(sw.getFs());
		newSw.setFwsj(nowTime);		//发文时间
		newSw.setInstanceid(newInstanceId);
		newSw.setZsdw(sw.getZsdw());
		newSw.setCsdw(sw.getCsdw());
		newSw.setZtc(sw.getZtc());
		newSw.setJjcd(sw.getJjcd());
		tableInfoDao.addSw(newSw);
	}
	
	/**
	 * 仅仅更新步骤 已完成
	 * create by zhuxc
	 * 2013-4-18 上午10:54:36
	 */
	public void updateProcess(String processId,String operate,String instanceId,String nodeId,
			String userId,String title,String workFlowId,String itemId,String pdfPath,String trueJson){
		Date nowTime = new Date();
		WfProcess process = pendingDao.getProcessByID(processId);  
		if(Integer.parseInt(operate)==1){
			if(null != process){
				process.setFinshTime(nowTime);
				process.setPdfPath(pdfPath);
//				process.setCommentJson(trueJson);
				process.setIsOver(Constant.OVER); // 设置当前步骤已经结束
			}
			// 更新
		}else if(Integer.parseInt(operate)==0 && process==null){
			WfProcess newProcess = new WfProcess();
			newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
			newProcess.setWfInstanceUid(instanceId);
			newProcess.setNodeUid(nodeId);
			newProcess.setFromUserId(userId);
			newProcess.setOwner(userId);
			newProcess.setApplyTime(nowTime);
			newProcess.setFinshTime(nowTime);
			newProcess.setIsOver(Constant.NOT_OVER);
			newProcess.setIsEnd(0);
			newProcess.setProcessTitle(title);
			newProcess.setWfUid(workFlowId);
			newProcess.setItemId(itemId);
			newProcess.setToNodeid(nodeId);
			newProcess.setFromNodeid("first");
			//主送人ID及标识值
			Employee currentEmp = employeeDao.queryEmployeeById(userId);
			newProcess.setUserUid(userId);
			newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
			newProcess.setIsMaster(1);
			newProcess.setIsShow(1);
			newProcess.setStepIndex(1);
			newProcess.setStatus("0");
			newProcess.setPdfPath(pdfPath);
			newProcess.setCommentJson(trueJson);
			newProcess.setIsExchanging(0);	//保存当前流程(不走公文交换)
			tableInfoDao.save(newProcess);
			return;
		}
//		if(trueJson!=null && !("").equals(trueJson)){
//			//更新同级步骤及下一步的手写意见--主送抄送时
//			if(null != process){
//				List<WfProcess> processList = tableInfoDao.findWfProcessByInstanceIdAndStepIndex(instanceId,process.getStepIndex());
//				if(null != processList && processList.size()>0){
//					for (WfProcess wfProcess : processList) {
//						wfProcess.setCommentJson(trueJson);
//						tableInfoDao.update(wfProcess);
//					}
//				}
//			}
//		}
		if(null != process){
			process.setFinshTime(nowTime);
			process.setPdfPath(pdfPath);
//			process.setCommentJson(trueJson);
			process.setStatus("0");
			tableInfoDao.update(process);
		}
		if(Integer.parseInt(operate)==1 && null != process){
			//委托人id
			String entrustUser = process.getEntrustUserId();
			//没有委托人id，则用该用户id
			if(CommonUtil.stringIsNULL(entrustUser)){
				entrustUser = process.getUserUid();
			}
			
			WfNode current = workflowBasicFlowDao.getWfNode(nodeId);
			String self_loop = current.getWfn_self_loop();
			if(CommonUtil.stringNotNULL(self_loop) && "1".equals(self_loop)){
				//自循环模式不更新相关委托人
			}else{
				//更新相关委托人OVER
				//updateOverForEntrust(process.getWfUid(), process.getWfInstanceUid(), process.getNodeUid(), entrustUser,process.getWfProcessUid());
			}
		}
	}
	
	/**
	 * 更新为显示
	 * @param workFlowId  流程id
	 * @param instanceId	实例id
	 * @param nodeId		需要更新的节点Id
	 */
	public void updateProcessShow(String workFlowId,String instanceId,String nodeId){
		List<Object[]> list = tableInfoDao.getNextUserList(workFlowId, instanceId, nodeId);
		tableInfoDao.updateProcessShow(workFlowId, instanceId, nodeId);
		String title = "";
		String fromUserId = "";
		String processId = "";
		
		if(null != list && list.size()>0){
			title = list.get(0)[1] != null ? list.get(0)[1].toString() : "";
			fromUserId = list.get(0)[2] != null ? list.get(0)[2].toString() : "";
			processId = list.get(0)[3] != null ? list.get(0)[3].toString() : "";
		}
		Employee emp = tableInfoDao.findEmpByUserId(processId);
		//websocket推送
		WebSocketUtil webSocket = new WebSocketUtil();
		for(int i=0; i<list.size(); i++){
			Object[] data = list.get(i);
			String userId = data[0]!=null?data[0].toString():"";
			String processTitle = data[1]!=null?data[1].toString():"";
			try {
				webSocket.apnsPush(processTitle, fromUserId, "", "", "", userId);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 是否全部已完成
	 */
	public boolean isAllOver(String workFlowId, String instanceId, String nodeId, String fInstanceId) {
		List<WfProcess> list = tableInfoDao.getNodeProcess(workFlowId, instanceId, nodeId ,fInstanceId);
		boolean allOver = true;
		for(WfProcess p : list){
			if(Constant.NOT_OVER.equals(p.getIsOver())){
				allOver = false;
			}
		}
		return allOver;
	}

	/**
	 * 将该流程下该节点的其他人的步骤改为OVER(已完成)
	 */
	public void updateOver(String workFlowId, String instanceId, String nodeId) {
		List<String> list = tableInfoDao.getUpdateOverUserList(workFlowId, instanceId, nodeId);
		//websocket推送
		WebSocketUtil webSocket = new WebSocketUtil();
		for(int i=0; i<list.size(); i++){
			try {
				webSocket.delBadge(list.get(i), "", "");//webSocket消息推送 人员-1
			} catch (JSONException e) {
				e.printStackTrace();
			}		
		}
		tableInfoDao.updateOver(workFlowId, instanceId, nodeId);
	}
	
	/**
	 * 被委托人或发起委托人有一人办理，则更新两者都为OVER
	 */
	public void updateOverForEntrust(String workFlowId, String instanceId, String nodeId,String entrustUserId,String processId){
		//将查到的相关委托步骤更新
		tableInfoDao.updateOverForEntrust(workFlowId, instanceId, nodeId,entrustUserId,processId);
	}
	
	/**
	 * 流程办结
	 */
	public String updateInstanceOver(String workflowId,String instanceId,String beforeProcessId ,String oldProcessId ,
			String nodeId,String userId,String title,String formId,String trueJson, String pdfPath, String isWriteNewValue,String newProcessId){
		// 新建或更新oldProcess
		WfProcess oldProcess = pendingDao.getProcessByID(oldProcessId); 
		
		Date nowTime = new Date();
		if (oldProcessId !=null && oldProcess != null && !("false").equals(isWriteNewValue)){
			// 更新
			oldProcess.setFinshTime(nowTime);
			oldProcess.setProcessTitle(title);
			oldProcess.setIsOver(Constant.OVER); // 设置当前步骤已经结束
			oldProcess.setStatus("1");
//			oldProcess.setCommentJson(trueJson);
			tableInfoDao.update(oldProcess);
			//委托人id
			String entrustUser = oldProcess.getEntrustUserId();
			//没有委托人id，则用该用户id
			if(CommonUtil.stringIsNULL(entrustUser)){
				entrustUser = oldProcess.getUserUid();
			}
			
			WfNode current = workflowBasicFlowDao.getWfNode(nodeId);
			String self_loop = current.getWfn_self_loop();
			if(CommonUtil.stringNotNULL(self_loop) && "1".equals(self_loop)){
				//自循环模式不更新相关委托人
			}else{
				//更新相关委托人OVER
				//updateOverForEntrust(oldProcess.getWfUid(), oldProcess.getWfInstanceUid(), oldProcess.getNodeUid(), entrustUser,oldProcess.getWfProcessUid());
			}
			
			//插入办结步骤
			this.addEndProcess(instanceId, title, nodeId, formId, oldProcess, pdfPath,newProcessId);
			RemoteLogin remote = new RemoteLogin();
			boolean checkUser = RemoteLogin.checkPassed;
			if(checkUser){
				remote.deleteThirdPend(oldProcess.getUserUid(), oldProcessId);
			}
			//saveEndInstanceId(oldProcess.getWfInstanceUid());
		}else{
			WfProcess beforeWfProcess = pendingDao.getProcessByID(beforeProcessId); 
//			单例默认用多例走
//			WfChild wfChild = tableInfoDao.getwfChildByCId(workflowId);
//			if(("0").equals(wfChild.getWfc_ctype())){
//				instanceId = beforeWfProcess.getWfInstanceUid();
//			}
				//因无中间步骤,设置上一步的doType为4,为了步骤记录
				beforeWfProcess.setDoType(4);
			tableInfoDao.update(beforeWfProcess);
			
			//插入open第一步
			WfProcess newProcess = new WfProcess();
				newProcess.setWfProcessUid(newProcessId);
				newProcess.setWfInstanceUid(instanceId);
				newProcess.setfInstancdUid(beforeWfProcess.getWfInstanceUid());
				newProcess.setWfUid(workflowId);
				newProcess.setNodeUid(nodeId);
				newProcess.setItemId(beforeWfProcess.getItemId());
				newProcess.setFormId(formId);
				newProcess.setOldFormId(beforeWfProcess.getFormId());
				newProcess.setFromUserId(userId);
				Employee currentEmp = employeeDao.queryEmployeeById(userId);
				newProcess.setUserUid(userId);
				newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
				newProcess.setFromNodeid(beforeWfProcess.getNodeUid());
				newProcess.setToNodeid(nodeId);
				newProcess.setOwner(beforeWfProcess.getOwner());
				newProcess.setApplyTime(nowTime);
				newProcess.setFinshTime(nowTime);
				newProcess.setStatus("1");
				if(title==null || "".equals(title)){
					if(oldProcess!=null){
						newProcess.setProcessTitle(oldProcess.getProcessTitle());
					}
				}else{
					newProcess.setProcessTitle(title);
				}
				newProcess.setStepIndex(1);
				newProcess.setIsOver(Constant.NOT_OVER);
				newProcess.setIsEnd(0);
				newProcess.setIsShow(0);
				newProcess.setIsChildWf("1");
				newProcess.setIsManyInstance("1");
				newProcess.setZhqxDate(beforeWfProcess.getZhqxDate());
//				newProcess.setCommentJson(trueJson);
				newProcess.setPdfPath(pdfPath);
			tableInfoDao.save(newProcess);
			//插入办结步骤
			WfProcess newOldProcess = pendingDao.getProcessByID(newProcessId); 
			this.addEndProcess(instanceId, title, nodeId, formId, newOldProcess,"",newProcessId);
			//saveEndInstanceId(newProcess.getWfInstanceUid());
			
			RemoteLogin remote = new RemoteLogin();
			boolean checkUser = RemoteLogin.checkPassed;
			if(checkUser){
				remote.deleteThirdPend(beforeWfProcess.getUserUid(), beforeProcessId);
			}
		}
		tableInfoDao.updateInstanceOver(workflowId, instanceId);
		return newProcessId;
	}
	
	public List<WfProcess> findWfProcessList(String workFlowId,String instanceId,String nodeId, Integer stepIndex){
		return tableInfoDao.findWfProcessList(workFlowId, instanceId, nodeId,stepIndex);
	}

	/**
	 * 
	 * @Title: addEndProcess 
	 * @Description: 插入办结步骤
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void addEndProcess(String instanceId,String title,String nodeId,String formId,WfProcess oldProcess,String pdfPath,String newProcessId){
		instanceId = oldProcess.getWfInstanceUid();
		 //查询该instanceId的最大步骤
		 WfProcess maxProcess = new WfProcess();
		 maxProcess.setWfInstanceUid(instanceId);
		 //stepIndex目前该instacneId的最大步骤：正对分发假节点
		 Integer stepIndex = oldProcess.getStepIndex();
//		 List<WfProcess> list =  getWfProcessByEntity(maxProcess);
		 List<WfProcess> list =  getStepByEntity(maxProcess);
		 if(list!=null && list.size()>0){
			 WfProcess wfProcess = list.get(0);
			 stepIndex = wfProcess.getStepIndex();
		 }		 
		WfProcess newProcess = new WfProcess();
		newProcess.setWfProcessUid(newProcessId);
		newProcess.setWfInstanceUid(oldProcess.getWfInstanceUid());
		newProcess.setNodeUid(nodeId);//办结节点nodeId
		newProcess.setProcessTitle(title);
		newProcess.setItemId(oldProcess.getItemId());
		newProcess.setFormId(formId);
		newProcess.setJssj(new Date());
		newProcess.setOldFormId(oldProcess.getFormId());
		newProcess.setFromUserId(oldProcess.getUserUid());
		newProcess.setUserUid(oldProcess.getUserUid());
		newProcess.setUserDeptId(oldProcess.getUserDeptId());
		newProcess.setFromNodeid(oldProcess.getNodeUid());
		newProcess.setToNodeid(oldProcess.getNodeUid());
		newProcess.setOwner(oldProcess.getOwner());
		newProcess.setApplyTime(new Date(System.currentTimeMillis()+1000));
		newProcess.setFinshTime(new Date(System.currentTimeMillis()+1000));
		newProcess.setIsOver(Constant.OVER);
		newProcess.setStatus("1");
		newProcess.setNodeUid(nodeId);
		newProcess.setIsEnd(1);
		newProcess.setProcessTitle((title==null || "".equals(title)) ? oldProcess.getProcessTitle() : title);
		newProcess.setWfUid(oldProcess.getWfUid());
		newProcess.setIsShow(1);
		newProcess.setStepIndex(stepIndex+1);     
		newProcess.setfInstancdUid(oldProcess.getfInstancdUid());
		newProcess.setIsChildWf(oldProcess.getIsChildWf());
		newProcess.setIsManyInstance(oldProcess.getIsManyInstance());
		newProcess.setZhqxDate(oldProcess.getZhqxDate());
//		newProcess.setCommentJson(oldProcess.getCommentJson());
		newProcess.setPdfPath(oldProcess.getPdfPath());
		newProcess.setFjbProcessId(oldProcess.getFjbProcessId());
		newProcess.setAllInstanceid(oldProcess.getAllInstanceid());
		if(pdfPath!=null && !pdfPath.equals("")){
			newProcess.setPdfPath(pdfPath);
		}
			
		tableInfoDao.save(newProcess);
		saveEndInstanceId(newProcess.getWfInstanceUid());
	}

	/**
	 * 已完成条数
	 */
	public int getCountOfOver(String conditionSql,String userId,String status) {
		return tableInfoDao.getCountOfOver( conditionSql,userId,status);
	}
	
	/**
	 * 已完成条数（城管）
	 */
	public int getCountOfOver(String conditionSql,String userId, String itemid,
			String statustype) {
		return tableInfoDao.getCountOfOver( conditionSql,userId, itemid, statustype);
	}

	/**
	 * 已完成事项列表
	 */
	public List<Pending> getOverList(String conditionSql,String userId, Integer pageIndex,
			Integer pageSize,String status) {
		long startTime = System.currentTimeMillis();
		long endTime;
		List<Pending> pendList = tableInfoDao.getOverList(conditionSql,userId, pageIndex, pageSize,status);

		endTime = System.currentTimeMillis();
		System.out.println("-----------getOverList断点2----------"+(endTime-startTime)/1000.0);
		//根据流程instanceId查找待办条数，查询当前步骤是否是子流程第一步--未往下走就无推送按钮
		if(pendList.size() > 0 && !("").equals(pendList)){
			for (Pending pending : pendList) {
				pending.setNowProcessId(pending.getWf_process_uid());
				//取最新的办理状态
				List<Object> processList = tableInfoDao.findWfProcessByInstanceIddesc(pending.getWf_instance_uid());
				if(processList.size() > 0 && !("").equals(processList)){
//					int size = processList.size();
//					Object[] wfp = null;
//					for(int i=0; i<size; i++){
//						wfp = (Object[]) processList.get(i);
//						if(i==0){
//							if(wfp[7] != null &&wfp[7].toString().equals("2") ){
//								pending.setWf_process_uid(wfp[1]!=null?wfp[1].toString():"");
//							}
//						}
//						if(wfp[4]!=null &&wfp[4].toString().equals("1")){
//							break;
//						}
//						if(wfp[3]!=null &&wfp[3].toString().equals("1") &&wfp[0]!=null && "Over".equalsIgnoreCase(wfp[0].toString())){
//							break;
//						}
//					}
//					pending.setIsEnd( wfp[4]!=null?wfp[4].toString():"");
//					String fileStatus = wfp[2]!=null?wfp[2].toString():"";
//					String pdfPath = wfp[5]!=null?wfp[5].toString():"";
//					String processId = wfp[1]!=null?wfp[1].toString():"";
//					pending.setStatus(fileStatus);
//					pending.setOldForm_id(pending.getWf_process_uid());
//					pending.setWf_process_uid(processId);
//					pending.setEntrust_name(pending.getEntrust_name());
//					pending.setPdfPath(pdfPath);	//pdf路径
					String entrustName = tableInfoDao.findEntrustName(pending.getWf_instance_uid());
					pending.setEntrust_name(entrustName);
					pending.setProcess_title(pending.getProcess_title().replace("\r\n", "").replace("\r", "").replace("\n", ""));
					Object[] wfp = (Object[]) processList.get(0);
					WfNode wfNode = null;
					if(wfp[8]!=null){
						wfNode = tableInfoDao.getWfNodeById(wfp[8].toString());
						if(wfNode!=null){
							pending.setNodeName(wfNode.getWfn_name());
						}
					}
					Integer endCount = tableInfoDao.getCountEndInstance(pending.getWf_instance_uid());
					pending.setStatus(endCount>0?"1":"0");
				}
			}
		}
		endTime = System.currentTimeMillis();
		System.out.println("-----------getOverList断点3----------"+(endTime-startTime)/1000.0);
		return pendList;
		/*List<Pending> pendList2 = new ArrayList<Pending>();
		for(Pending p:pendList){
			String instanceId = p.getWf_instance_uid();
			Integer wfps = pendingDao.countProcessListByFId(instanceId);
			if(null != wfps && wfps>0){
				//当前办件有子流程
				p.setIsHaveChild("1");
			}else{
				//当前办件没有子流程
				Integer wfpss = pendingDao.countByInstanceId(instanceId);
				boolean isChild = false;
					if(null != wfpss && wfpss>0){
						isChild = true;
					}
				if(isChild){
					//当前办件是子流程
					p.setIsHaveChild("1");
				}else{
					//当前办件是主流程
					p.setIsHaveChild("0");
				}
			}
			p.setProcess_title(p.getProcess_title().replace("\r\n", "").replace("\r", "").replace("\n", ""));
			pendList2.add(p);
		}

		endTime = System.currentTimeMillis();
		System.out.println("-----------getOverList断点4----------"+(endTime-startTime)/1000.0);
		return pendList2;*/
	}
	
	/**
	 *  实例的所有意见按钮TagId
	 */
	public String getCommentTagIds(String wfInstanceUid) {
		return tableInfoDao.getCommentTagIds(wfInstanceUid);
	}

	public List<WfFieldInfo> getFieldByTableid(String tableid) {
		return tableInfoDao.getFieldByTableid(tableid);
	}

	public String getTableAndColumnName(String fieldId) {
		//tableName + "," + columnName
		String[] fieldIds = fieldId.split(",");
		String retVal = "";
		for (String fiId : fieldIds) {
			List<String[]> list = tableInfoDao.getTableAndColumnName(fiId);
			if(list != null){
				retVal += list.get(0)[1].toString() +";"+ list.get(0)[2].toString()+"#";
			}
		}
		if(!("").equals(retVal) && retVal.length() > 0){
			retVal = retVal.substring(0,retVal.length()-1);
		}
		return retVal;
	}

	public List<GetProcess> findProcessList(String instanceId) {
		List<GetProcess>  list = tableInfoDao.findProcessList(instanceId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		for(GetProcess getProcess : list){
			String nodeName = getProcess.getNodeName();
			if(nodeName==null || nodeName.equals("")){
				WfNode node = tableInfoDao.getWfNodeById(getProcess.getNodeId());
				if(node!=null){
					getProcess.setNodeName(node.getWfn_name());
				}
			}
			
			Date applyTime = getProcess.getApplyTime();
			if(null != applyTime){
				getProcess.setStr_applyTime(sdf.format(applyTime));
				
			}
			Date finshTime = getProcess.getFinshTime();
			if(null != finshTime){
				getProcess.setStr_finshTime(sdf.format(finshTime));
				
			}
		}
		return list;
	}

	public List<WfProcess> findProcessList(String nodeId, String instanceId,String userId) {
		List<WfProcess> processList = tableInfoDao.findProcessList(nodeId,instanceId,userId);
		List<WfProcess> proList = new ArrayList<WfProcess>();
		for (WfProcess wfProcess : processList) {
			String userName = tableInfoDao.findUserNameByUserId(wfProcess.getUserUid());
			wfProcess.setUserUid(userName);
			proList.add(wfProcess);
		}
		return proList;
	}

	public List<DoFile> getDoFileList(String bigDepId,String conditionSql,Integer pageindex, Integer pagesize) {
		List<DoFile> dofList = tableInfoDao.getDoFileList(bigDepId,conditionSql,pageindex,pagesize);
		for (DoFile doFile : dofList) {
			List<Object[]> processList = tableInfoDao.getFastProcessList(doFile.getInstanceId());
			if(!("3").equals(doFile.getDoFile_result()+"")){
				int result = 0; //0:未办理 1:办理中 2:已办结 3:已作废
				if(processList!=null && !("").equals(processList) && processList.size() != 0){
					boolean isOver = true;
					if(processList.size()!=1){//不只是只有第一步
						for (Object[] wfProcess : processList) {
							if(!Constant.OVER.equals(wfProcess[1])){
								isOver = false;
							}  
						}
						if(isOver == false){
							result = 1;
						}else if(isOver == true){
							result = 2;
						}
					}
				}
				doFile.setDoFile_result(result);
			}else{
				doFile.setDoFile_result(doFile.getDoFile_result());
			}
			if(CommonUtil.stringIsNULL(doFile.getItemName())){
				String itemName = tableInfoDao.findItemNameById(doFile.getItemId());
				doFile.setItemName(itemName);
			}
			if(processList.size()>0 && processList != null){
				doFile.setProcessId((String)processList.get(0)[0]);
			}
			//WfItem item = itemDao.getItemById(doFile.getItemId());
			List<String> itemList = tableInfoDao.getFastItemList(doFile.getItemId());//优化速度用
			if(null != itemList){
				doFile.setItemType(itemList.get(0));
			}
			
			doFile.setEntrustName(tableInfoDao.findEntrustName(doFile.getInstanceId()));
			if(StringUtils.isNotBlank(doFile.getDoFile_title())){
				doFile.setDoFile_title(doFile.getDoFile_title().replace("\r\n", "").replace("\r", "").replace("\n", ""));
			}
		}
		return dofList;
	}

	public List<DoFile> getDoFileList1(String bigDepId,String conditionSql,Integer pageindex, Integer pagesize) {
		List<DoFile> dofileList = new ArrayList<DoFile>();
		List<DoFile> dofList = tableInfoDao.getDoFileList1(bigDepId,conditionSql,pageindex,pagesize);
		for (DoFile doFile : dofList) {
			DoFile doFileBak = new DoFile();
			doFileBak.setDoFile_title(doFile.getDoFile_title());
			doFileBak.setItemId(doFile.getItemId());
			doFileBak.setWorkflowId(doFile.getWorkflowId());
			doFileBak.setInstanceId(doFile.getInstanceId());
			List<WfProcess> processList = tableInfoDao.getProcessList(doFile.getInstanceId());
			if(!("3").equals(doFile.getDoFile_result()+"")){
				int result = 0; //0:未办理 1:办理中 2:已办结 3:已作废
				if(processList!=null && !("").equals(processList) && processList.size() != 0){
					boolean isOver = true;
					if(processList.size()!=1){//不只是只有第一步
						for (WfProcess wfProcess : processList) {
							if(!Constant.OVER.equals(wfProcess.getIsOver())){
								isOver = false;
							}  
							if(wfProcess.getFinshTime()!=null && !("").equals(wfProcess.getFinshTime())){
								doFileBak.setDo_time(wfProcess.getFinshTime());
							}else{
								doFileBak.setDo_time(wfProcess.getApplyTime());
							}
						}
						if(isOver == false){
							result = 1;
						}else if(isOver == true){
							result = 2;
						}
					}
				}
				doFileBak.setDoFile_result(result);
			}else{
				doFileBak.setDoFile_result(doFile.getDoFile_result());
				doFileBak.setDo_time(doFile.getDo_time());
			}
			if(CommonUtil.stringIsNULL(doFile.getItemName())){
				String itemName = tableInfoDao.findItemNameById(doFile.getItemId());
				doFileBak.setItemName(itemName);
			}else{
				doFileBak.setItemName(doFile.getItemName());
			}
			if(processList.size()>0 && processList != null){
				doFileBak.setProcessId(processList.get(0).getWfProcessUid());
			}
			doFileBak.setFormId(doFile.getFormId());
			doFileBak.setNodeId(doFile.getNodeId());
			if(doFile.getNodeId()!=null){
				doFileBak.setNodeName(
						tableInfoDao.getNodeNameById(doFile.getNodeId()));
			}
			dofileList.add(doFileBak);
		}
		return dofileList;
	}

	
	public void saveConsult(WfConsult consult) {
		tableInfoDao.saveConsult(consult);
	}

	public List<WfConsult> getConsultList(String userId,String condition,Integer pageIndex, Integer pageSize) {
		return tableInfoDao.getConsultList(userId,condition, pageIndex, pageSize);
		
	}

	public int getCountConsults(String userId,String condition) {
		return tableInfoDao.getCountConsults(userId, condition);
	}

	public void updateConsultRead(String id) {
		tableInfoDao.updateConsultRead(id);
	}
	
	public void updateConsultReplied(String id) {
		tableInfoDao.updateConsultReplied(id);
	}

	public WfConsult getConsultById(String id) {
		
		return tableInfoDao.getConsultById(id);
	}

	public WfProcess getProcessById(String processId) {
		return tableInfoDao.getProcessById(processId);
	}

	public void saveProcess(WfProcess dupProcess) {
		tableInfoDao.save(dupProcess);
		
	}

	public int getCountOfDuplicate(String conditionSql, String employeeGuid) {
		return tableInfoDao.getCountOfDuplicate( conditionSql,  employeeGuid);
	}

	public List<Pending> getDuplicateList(String conditionSql,
			String employeeGuid, Integer pageIndex, Integer pageSize) {
		return tableInfoDao.getDuplicateList( conditionSql,employeeGuid,  pageIndex,  pageSize);
	}

	public void deleteDuplicate(String ids) {
		tableInfoDao.updateDuplicateNotShow(ids);
	}
	
	public String getClob(String tablename, String instanceid, String fieldname, String formId){
		PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    String rtn = "";
	    try {
		Connection conn = jdbcBase.getCon();
	    String sql = "select "+fieldname.trim() + " from " + tablename + " where instanceid='" + instanceid + "' and formid='"+formId+"'";
	    pstmt = conn.prepareStatement(sql);
	    rs = pstmt.executeQuery();
	    java.sql.Clob clob = null;
	    if (rs.next()) {
	        clob = rs.getClob(fieldname);
	        if(clob != null){
	        	rtn = clob.getSubString((long)1,(int)clob.length());
	        }else{
	        	rtn = "";
	        }
	    }
	    }catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
	             rs.close();
	             rs=null;
	             pstmt.close();
	             pstmt=null;
	         } catch (SQLException ex) {
	         }
		}
	    return rtn;
	}
	
	public String getClobOfWfProcess(String tablename, String instanceid, String fieldname){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String rtn = "";
		try {
			Connection conn = jdbcBase.getCon();
			String sql = "select "+fieldname + " from " + tablename + " where wf_process_uid='" + instanceid + "'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			java.sql.Clob clob = null;
			if (rs.next()) {
				clob = rs.getClob(fieldname);
				if(clob != null){
					rtn = clob.getSubString((long)1,(int)clob.length());
				}else{
					rtn = "";
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				rs=null;
				pstmt.close();
				pstmt=null;
			} catch (SQLException ex) {
			}
		}
		return rtn;
	}

	/**
	 * 是否已有抄送
	 */
	public boolean isDuplicated(WfProcess dupProcess) {
		return tableInfoDao.isDuplicated(dupProcess);
	}

	/**
	 * 是否已有委托
	 */
	public boolean hasEntrust(WfProcess dupProcess) {
		return tableInfoDao.hasEntrust(dupProcess);
	}

	public String findDeptNameByEmpId(Employee emp) {
		return tableInfoDao.findDeptNameByEmpId(emp);
	}

	public List<WfItem> findItemList() {
		return tableInfoDao.findItemList();
	}

	public void savePerMes(PersonMessage perMes) {
		tableInfoDao.savePerMes(perMes);
	}

	public PersonMessage findXccNamesByItemId(String id,String userId) {
		return tableInfoDao.findXccNamesByItemId(id,userId);
	}

	public void updatePerMes(PersonMessage pm) {
		tableInfoDao.updatePerMes(pm);
	}
	
	public void deletePerMes(String id){
		tableInfoDao.deletePerMes(id);
	}


	public void deleteConsult(String ids) {
		tableInfoDao.updateConsultNotShow(ids);
		
	}

	public int getCountDoFiles(String bigDepId,String conditionSql) {
		return tableInfoDao.getCountDoFiles(bigDepId,conditionSql);
	}
	
	/**
	 * 判断节点是否是督办节点，是就入库等待定时器扫描发送短信
	 * @param process	刚添加的process
	 * @param fromUserId
	 */
	public void addDuBanList(WfProcess process){
		Object[] needDuBanInfo = tableInfoDao.findDuBanListByInsId(process.getWfInstanceUid());
		if(needDuBanInfo!=null&&needDuBanInfo[1]!=null&&CommonUtil.stringNotNULL(needDuBanInfo[1]+"")){
			WfDuBanLog dubanLog = new WfDuBanLog();
			dubanLog.setCreateTime(new Date());
			dubanLog.setEmployeeGuid(process.getUserUid());
			Employee currentEmp = employeeDao.queryEmployeeById(process.getUserUid());
			dubanLog.setPhoneNum(currentEmp.getEmployeeMobile());
			dubanLog.setProcessId(process.getWfProcessUid());
			dubanLog.setInstanceId(process.getWfInstanceUid());
			dubanLog.setContent("您有一条《"+process.getProcessTitle()+"》公文为督办件，请及时办理");
			tableInfoDao.saveDuBanLog(dubanLog);//下一步
		}else{		
			WfNode wfNode = pendingDao.getWfNode(process.getNodeUid());
			//如果发送的节点是督办节点，则入库
			if(Integer.valueOf(1).equals(wfNode.getWfn_isSupervision())){
				WfDuBanLog dubanLog = new WfDuBanLog();
				dubanLog.setCreateTime(new Date());
				dubanLog.setEmployeeGuid(process.getUserUid());
				Employee currentEmp = employeeDao.queryEmployeeById(process.getUserUid());
				dubanLog.setPhoneNum(currentEmp.getEmployeeMobile());
				dubanLog.setProcessId(process.getWfProcessUid());
				dubanLog.setInstanceId(process.getWfInstanceUid());
				dubanLog.setContent("您有一条《"+process.getProcessTitle()+"》公文为督办件，请及时办理");
				tableInfoDao.saveDuBanLog(dubanLog);//下一步
			}
		}
	}
	
	/**
	 * 添加个人中心设置的委托
	 * @param process	刚添加的process
	 * @param fromUserId
	 */
	public void addXcc(WfProcess process) {
		PersonMessage personMsg = findXccNamesByItemId(process.getItemId(), process.getUserUid());
		if(personMsg != null){
			String csUserIds = personMsg.getXccempId();
			if(CommonUtil.stringNotNULL(csUserIds)){
				String[] userIds = csUserIds.split(",");
				for(String u_id : userIds){
					WfProcess xccProcess = new WfProcess();
					xccProcess.setWfProcessUid(UuidGenerator.generate36UUID());
					xccProcess.setWfInstanceUid(process.getWfInstanceUid());
					xccProcess.setNodeUid(process.getNodeUid());
					xccProcess.setProcessTitle(process.getProcessTitle());
					xccProcess.setItemId(process.getItemId());
					xccProcess.setFormId(process.getFormId());
					xccProcess.setOldFormId(process.getOldFormId());
					xccProcess.setFromUserId(process.getFromUserId());
					Employee currentEmp = employeeDao.queryEmployeeById(u_id);
					xccProcess.setUserUid(u_id);
					// 委托部门 待定 暂时先取当前人的部门
					xccProcess.setUserDeptId(currentEmp.getDepartmentGuid());
					xccProcess.setOwner(process.getOwner());
					xccProcess.setApplyTime(new Date());
					xccProcess.setIsOver(Constant.NOT_OVER);
					xccProcess.setIsEnd(process.getIsEnd());
					xccProcess.setWfUid(process.getWfUid());
					xccProcess.setIsShow(1);
					xccProcess.setStepIndex(process.getStepIndex());    
					xccProcess.setIsMaster(process.getIsMaster());
					xccProcess.setEntrustUserId(process.getUserUid());
					xccProcess.setAction_status(process.getAction_status());
					xccProcess.setJdqxDate(process.getJdqxDate());
					xccProcess.setZhqxDate(process.getZhqxDate());
					//+++ add
					xccProcess.setAllInstanceid(process.getAllInstanceid());
					xccProcess.setPdfPath(process.getPdfPath());
					xccProcess.setFromNodeid(process.getFromNodeid());
					xccProcess.setToNodeid(process.getToNodeid());
					xccProcess.setIsExchanging(process.getIsExchanging());
					xccProcess.setIsBack(process.getIsBack());
					xccProcess.setCommentJson(process.getCommentJson());
					//+++ end
					if( !tableInfoDao.hasEntrust(xccProcess)){//是否已委托过
						saveProcess(xccProcess);
						System.out.println("委托给："+u_id+" ...");
					}else{
						System.out.println("EmployeeID:"+u_id+" 该用户已委托过，跳过...");
					}
				}
			}
		}
		
	}
	
	public void save(WfProcess newProcess) {
		tableInfoDao.save(newProcess);
	}

	public WfItem findItemByWorkFlowId(String workFlowId) {
		return tableInfoDao.findItemByWorkFlowId(workFlowId);
	}

	public String findNameByEmpId(String userId) {
		return tableInfoDao.findNameByEmpId(userId);
	}

	public List<WfCyName> findWfCyPersonNameByInstanceId(String instanceId) {
		return tableInfoDao.findWfCyPersonNameByInstanceId(instanceId);
	}
	
	public List<WfCyName> findWfCyOfficeNameByInstanceId(String instanceId) {
		return tableInfoDao.findWfCyOfficeNameByInstanceId(instanceId);
	}
	
	public void saveWfCyName(WfCyName wfCyName) {
		tableInfoDao.saveWfCyName(wfCyName);
	}

	public void updateWfCyName(WfCyName wcn) {
		tableInfoDao.updateWfCyName(wcn);
	}

	public void delelteWfCyPersonName(WfCyName wfCyName, String instanceId) {
		tableInfoDao.delelteWfCyPersonName(wfCyName,instanceId);
	}
	
	public void delelteWfCyOfficeName(WfCyName wfCyName,String instanceId) {
		tableInfoDao.delelteWfCyOfficeName(wfCyName,instanceId);
	}

	public String findDeptNameByUserId(String userId) {
		return tableInfoDao.findDeptNameByUserId(userId);
	}

	@Override
	public WfFieldInfo getFieldById(String id) {
		return tableInfoDao.getFieldById(id);
	}

	@Override
	public List<Object[]> getListBySql(String sql) {
		return tableInfoDao.getListBySql(sql);
	}

	@Override
	public List<WfProcess> findProcesses(String workFlowId, String instanceId, String nextNodeId) {
		return tableInfoDao.findProcesses(workFlowId, instanceId, nextNodeId);
	}

	public void updateWfProcess(WfProcess wfProcess) {
		tableInfoDao.updateWfProcess(wfProcess);
	}

	public List<Process> findProcessListByElements(String workFlowId, String instanceId) {
		return tableInfoDao.findProcessListByElements(workFlowId,instanceId);
	}

	public void deleteWfProcess(String workFlowId, String instanceId) {
		tableInfoDao.deleteWfProcess(workFlowId,instanceId);
	}
	
	public void deleteWfProcessByAllInstanceId(String allInstanceId){
		tableInfoDao.deleteWfProcessByAllInstanceId(allInstanceId);
	}
	
	public List<WfProcess> getWfProcessByAllInstanceId(String allInstanceId){
		return tableInfoDao.getWfProcessByAllInstanceId(allInstanceId);
	}

	public DoFile getDoFileByElements(String workFlowId, String instanceId) {
		return tableInfoDao.getDoFileByElements(workFlowId,instanceId);
	}
	
	@Override
	public WfItem findItemByWorkFlowId(String workFlowId,String webId) {
		return tableInfoDao.findItemByWorkFlowId(workFlowId, webId);
	}
	public void updateDoFile(DoFile doFile) {
		tableInfoDao.updateDoFile(doFile);
	}

	public void deleteFwByElements(String workFlowId, String instanceId) {
		tableInfoDao.deleteFwByElements(workFlowId,instanceId);
	}

	public void deleteBwByElements(String workFlowId, String instanceId) {
		tableInfoDao.deleteBwByElements(workFlowId,instanceId);
	}

	public Employee findEmpByUserId(String userId) {
		return tableInfoDao.findEmpByUserId(userId);
	}
	
	public List<Employee> findEmpsByUserIds(String userIds) {
		return tableInfoDao.findEmpsByUserIds(userIds);
	}
	
	@Override
	public Object[] getToDbInfoIds(String nodeId) {
		return tableInfoDao.getToDbInfoIds(nodeId);
	}
	
	@Override
	public List<Pending> getOverList2(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize, String itemid,
			String statustype) {
		
		List<Pending> pendList = tableInfoDao.getOverList2(conditionSql,userId, pageIndex, pageSize,itemid,statustype);
//		//根据流程instanceId查找待办条数，查询当前步骤是否是子流程第一步--未往下走就无推送按钮
//		for (Pending pending : pendList) {
//			List<WfProcess> proList = tableInfoDao.findWfProcessByInstanceIdAndIsChild(pending.getWf_instance_uid());
//			if(proList.size() > 1 && proList != null){
//				pending.setIsCanPush("1");
//			}else{
//				pending.setIsCanPush("0");
//			}
//		}
		if(pendList.size() > 0 && !("").equals(pendList)){
			for (Pending pending : pendList) {
				//取最新的办理状态
				List<WfProcess> processList = tableInfoDao.findWfProcessByInstanceId(pending.getWf_instance_uid());
				if(processList.size() > 0 && !("").equals(processList)){
					pending.setStatus(processList.get(0).getStatus());
				}
			}
		}
		return pendList;
	}

	/**
	 * @param processId
	 * @param string
	 * @param instanceId
	 * @param nodeId
	 * @param userId
	 * @param vc_title
	 * @param workFlowId
	 * @param itemId
	 * @param pdfPath
	 * @param string2
	 * @param formId
	 */
	@Override
	public void updateProcess(String processId, String operate,
			String instanceId, String nodeId, String userId, String title,
			String workFlowId, String itemId, String pdfPath, String trueJson,
			String formId,Integer urgency) {
		Date nowTime = new Date();
		WfProcess process = pendingDao.getProcessByID(processId);  
		if(Integer.parseInt(operate)==1){
			process.setFinshTime(nowTime);
			process.setPdfPath(pdfPath);
//			process.setCommentJson(trueJson);
			process.setIsOver(Constant.OVER); // 设置当前步骤已经结束
			//websocket推送消息
			WebSocketUtil webSocket = new WebSocketUtil();
			try {
				webSocket.delBadge(process.getUserUid(), "", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}else if(Integer.parseInt(operate)==0 && process==null){		//执行保存操作
			WfProcess newProcess = new WfProcess();
			newProcess.setWfProcessUid(processId);
			newProcess.setWfInstanceUid(instanceId);
			newProcess.setNodeUid(nodeId);
			newProcess.setFromUserId(userId);
			newProcess.setOwner(userId);
			newProcess.setApplyTime(nowTime);
			newProcess.setIsOver(Constant.NOT_OVER);
			newProcess.setIsEnd(0);
			newProcess.setProcessTitle(title);
			newProcess.setWfUid(workFlowId);
			newProcess.setItemId(itemId);
			newProcess.setToNodeid(nodeId);
			newProcess.setFromNodeid("first");
			//主送人ID及标识值
			Employee currentEmp = employeeDao.queryEmployeeById(userId);
			newProcess.setUserUid(userId);
			newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
			newProcess.setIsMaster(1);
			newProcess.setIsShow(1);
			newProcess.setStepIndex(1);
			newProcess.setStatus("0");
			newProcess.setPdfPath(pdfPath);
//			newProcess.setCommentJson(trueJson);
			newProcess.setFormId(formId);
			newProcess.setIsExchanging(0);	//保存当前流程(不走公文交换)
			newProcess.setAllInstanceid(instanceId);
//			newProcess.setOldFormId(process.getFormId());
			tableInfoDao.save(newProcess);
			DoFile doFile = new DoFile();
			doFile.setDoFile_title(title);                                                   	
			doFile.setItemId(itemId);                                                        	
			doFile.setWorkflowId(workFlowId);                                                	
			doFile.setInstanceId(instanceId);                                                	
			doFile.setFormId(formId);                                                        	
			String itemName = tableInfoDao.findItemNameById(newProcess.getItemId());         	
			doFile.setItemName(itemName);                                                    	
			doFile.setNodeId(nodeId);                                                        	
			if(newProcess.getFinshTime()!=null && !("").equals(newProcess.getFinshTime())){  	
				doFile.setDo_time(newProcess.getFinshTime());                                	
			}else{                                                                           	
				doFile.setDo_time(newProcess.getApplyTime());                                	
			}                                                                                	
			if(null != urgency){                                                             	
				doFile.setUrgency(urgency);                                                  	
			}                                                                                	
			                                                                                 	
			//父流程插入数据到dofile表中                                                               	
			tableInfoDao.saveDoFile(doFile);                                                 	
			                                                                                 
			
			return;
		}else if(Integer.parseInt(operate) == 0 && process != null){
			DoFile doFile = tableInfoDao.getDoFileByElements("", instanceId);
			if(null == doFile){
				doFile = new DoFile();
				doFile.setDoFile_title(title);
				doFile.setItemId(itemId);
				doFile.setWorkflowId(workFlowId);
				doFile.setInstanceId(instanceId);
				doFile.setFormId(formId);
				String itemName = tableInfoDao.findItemNameById(process.getItemId());
				doFile.setItemName(itemName);
				doFile.setNodeId(nodeId);
				if(process.getFinshTime()!=null && !("").equals(process.getFinshTime())){
					doFile.setDo_time(process.getFinshTime());
				}else{
					doFile.setDo_time(process.getApplyTime());
				}
				if(null != urgency){
					doFile.setUrgency(urgency);
				}
				//父流程插入数据到dofile表中
				tableInfoDao.saveDoFile(doFile);
			}else{
				doFile.setDoFile_title(title);
				doFile.setItemId(itemId);
				doFile.setWorkflowId(workFlowId);
				doFile.setInstanceId(instanceId);
				doFile.setFormId(formId);
				String itemName = tableInfoDao.findItemNameById(process.getItemId());
				doFile.setItemName(itemName);
				doFile.setNodeId(nodeId);
				if(process.getFinshTime()!=null && !("").equals(process.getFinshTime())){
					doFile.setDo_time(process.getFinshTime());
				}else{
					doFile.setDo_time(process.getApplyTime());
				}
				if(null != urgency){
					doFile.setUrgency(urgency);
				}
				tableInfoDao.updateDoFile(doFile);
			}
		}
//		if(trueJson!=null && !("").equals(trueJson)){
//			//更新同级步骤的手写意见--主送抄送时
//			List<WfProcess> processList = tableInfoDao.findWfProcessByInstanceIdAndStepIndex(instanceId,process.getStepIndex());
//			for (WfProcess wfProcess : processList) {
//				wfProcess.setCommentJson(trueJson);
//				tableInfoDao.update(wfProcess);
//			}
//		}
		if(Integer.parseInt(operate) == 1){
			process.setFinshTime(nowTime);
		}else{
			process.setFinshTime(null);
		}
		process.setPdfPath(pdfPath);
//		process.setCommentJson(trueJson);
		process.setStatus("0");
		tableInfoDao.update(process);
		
	}
	/**
	 * 我的请假条（政务中心）
	 */
	public int getCountOfMyLeave(String conditionSql,String userId, String itemid,
			String statustype) {
		return tableInfoDao.getCountOfMyLeave( conditionSql,userId, itemid, statustype);
	}
	@Override
	public List<Pending> getMyLeaveList(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize, String itemid,
			String statustype) {
		
		List<Pending> pendList = tableInfoDao.getMyLeaveList(conditionSql,userId, pageIndex, pageSize,itemid,statustype);
		
		return pendList;
	}

	public boolean otherMultiChildProcessIsEnd(String fInstanceId, String instanceId) {
		return tableInfoDao.otherMultiChildProcessIsEnd(fInstanceId,instanceId);
		
	}

	public boolean otherSingleChildProcessIsEnd(String fInstanceId, String instanceId) {
		return tableInfoDao.otherSingleChildProcessIsEnd(fInstanceId,instanceId);
		
	}
	public boolean isChildProcess(String instanceId) {
		return tableInfoDao.isChildProcess(instanceId);
	}

	public List<WfProcess> findMultiChildProcessList(String fInstanceId) {
		return tableInfoDao.findMultiChildProcessList(fInstanceId);
	}

	public List<WfProcess> findSingleChildProcessList(String fInstanceId) {
		return tableInfoDao.findSingleChildProcessList(fInstanceId);
	}

	@Override
	public WfNode getWfnNodeByInstanceId(String instanceId) {
		return tableInfoDao.getWfnNodeByInstanceId(instanceId);
	}

	@Override
	public WfProcess getWfProcessByInstanceid(String instanceId) {
		return tableInfoDao.getWfProcessByInstanceid(instanceId);
	}

	@Override
	public void updateProcessDate() {
		tableInfoDao.updateProcessDate();
	}
	
	@Override
	public boolean checkIsChildEnd(WfProcess wfProcess){
		String wfChildFinstanceId = wfProcess.getfInstancdUid();
		List<WfProcess> proList = tableInfoDao.getProcessList(wfChildFinstanceId);
		int isAllEnd = 0;
		if(proList!=null && proList.size()>0){
			List<String> wfList = tableInfoDao.getWfProcessByWfChildInstanceId(wfChildFinstanceId);
			if(wfList!=null && wfList.size()>0){
				int isEnd = 0 ;//是否办结
				for (String instanceId : wfList) {
					//分别获取每个实例是否已办结
					List<WfProcess> processList = tableInfoDao.getProcessList(instanceId);
					for (int i = 0; i < processList.size(); i++) {
						if (("1").equals(processList.get(i).getStatus())) {
							isEnd ++;
							break;
						}
					}
				}
				//如果相等则都办完
				if(wfList.size() == isEnd){
					isAllEnd = 1;
				}
			}
		}
		return isAllEnd==1?true:false;
	}
	
	@Override
	public List<String> getWfProcessByWfChildInstanceId(String wfChildFinstanceId){
		return tableInfoDao.getWfProcessByWfChildInstanceId(wfChildFinstanceId);
	}

	@Override
	public WfNode updateMainStatus(WfProcess wfProcess) {
		String wfChildFinstanceId = wfProcess.getfInstancdUid();
		//当前子流程的父流程下的所有子流程
		List<WfProcess> proList = tableInfoDao.getProcessList(wfChildFinstanceId);
		String finstanceId = "";
		if(("0").equals(wfProcess.getIsManyInstance())){
			finstanceId = wfChildFinstanceId;
		}else if(("1").equals(wfProcess.getIsManyInstance())){
			//获取子流程的父instanceId的父instanceId,因为有中间流程
			Integer doType = 0;
			for(WfProcess wfPro: proList){
				if(wfPro.getDoType()==null || (wfPro.getDoType()!=null && wfPro.getDoType()!=3)){
					doType = 1;
				}
			}
			if(proList!=null && proList.size()>0){
				if(doType!=1){
					finstanceId = proList.get(0).getfInstancdUid();
				}else{
					finstanceId = proList.get(0).getWfInstanceUid();
				}
				if(("").equals(finstanceId) || finstanceId == null || ("null").equals(finstanceId)){
					finstanceId = proList.get(0).getWfInstanceUid();
				}
			}
			
		}
		WfNode wfNode = null;
		if(proList!=null && proList.size()>0){
			//父流程等办的那一步
			List<WfProcess> wfProcessList = tableInfoDao.getWfProcessByInstanceidAndStatus(finstanceId);	
			//获取子流程下的多个实例
			List<String> wfList = tableInfoDao.getWfProcessByWfChildInstanceId(wfChildFinstanceId);
			int isAllEnd = 0;
			if(wfList!=null && wfList.size()>0){
				int isEnd = 0 ;//是否办结
				for (String instanceId : wfList) {
					//分别获取每个实例是否已办结
					List<WfProcess> processList = tableInfoDao.getProcessList(instanceId);
					for (int i = 0; i < processList.size(); i++) {
						if (("1").equals(processList.get(i).getStatus())) {
							isEnd ++;
							break;
						}
					}
				}
				//如果相等则都办完
				if(wfList.size() == isEnd){
					isAllEnd = 1;
				}
			}
			
			//都办完
			if(isAllEnd == 1){
				//获取子流程后面是否有节点
				String child_workflowId = wfProcess.getWfUid();
				WfChild wfChild = workflowBasicFlowDao.getWfChildByCid(child_workflowId);
				//获取子流程位置Id
				String child_module = wfChild.getWfc_moduleId();
				//父流程id
				String f_workflowId = wfProcessList.get(0).getWfUid();
				
				List<WfNode> nodeList = workflowBasicFlowDao.getNextNodeByChildWf(f_workflowId,child_module);
				//子流程的下一步一般只会有一个节点--入库
				if(nodeList.size()>0){
					wfNode = nodeList.get(0);
				}else{
					//更新父流程(包含中间流程的那一步)那一步骤
					for(WfProcess process : wfProcessList){
						process.setAction_status(0);
						tableInfoDao.update(process);
					}
				}
			}
		}
		return wfNode;
	}

	@Override
	public void updateProcessDate(String instanceId, String nodeid,
			Integer stepindex, Integer days) {
		tableInfoDao.updateProcessDate(instanceId, nodeid, stepindex, days);
		
	}

	@Override
	public void updateWfProcessStatus(String instanceId) {
		tableInfoDao.updateWfProcessStatus(instanceId);
	}

	@Override
	public List<WfProcess> findBoobyChildProcessList(String sql) {
		return tableInfoDao.findBoobyChildProcessList(sql);
	}

	@Override
	public WfProcess getParentProcessByInstanceid(String instanceId) {
		return tableInfoDao.getParentProcessByInstanceid(instanceId); 
	}
	
	@Override
	public List<WfProcess> getWfProcessList(String instanceId) {
		return tableInfoDao.getWfProcessList(instanceId);
	}
	
	@Override
	public List<WfProcess> getWfProcessList(HashMap<String, String>  map) {
		return tableInfoDao.getWfProcessList(map);
	}

	@Override
	public GetProcess findGetProcessByPInstanceID(
			String pinstanceId, String stepIndex) {
		return tableInfoDao.findGetProcessByPInstanceID(pinstanceId, stepIndex);
	}

	@Override
	public List<GetProcess> findProcess(String pinstanceId) {
		return tableInfoDao.findProcess(pinstanceId);
	}

	@Override
	public List<String> findInstanceIdsByFinstanceId(String finstanceId) {
		return tableInfoDao.findInstanceIdsByFinstanceId(finstanceId);
	}

	@Override
	public void saveNewWfProcess(WfProcess wfProcess, String employeeGuid, String userId) {
		WfProcess newProcess = new WfProcess();
			newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
			newProcess.setfInstancdUid(wfProcess.getfInstancdUid());
			newProcess.setWfInstanceUid(wfProcess.getWfInstanceUid());
			newProcess.setIsChildWf(wfProcess.getIsChildWf());
			newProcess.setIsManyInstance(wfProcess.getIsManyInstance());
			newProcess.setNodeUid(wfProcess.getNodeUid());
			newProcess.setItemId(wfProcess.getItemId());
			newProcess.setFormId(wfProcess.getFormId());
			newProcess.setOldFormId(wfProcess.getFormId());
			newProcess.setFromUserId(employeeGuid);
			newProcess.setOwner(wfProcess.getOwner());
			newProcess.setApplyTime(new Date());
			newProcess.setIsOver(Constant.OVER);
			newProcess.setIsEnd(0);
			newProcess.setProcessTitle(wfProcess.getProcessTitle());
			newProcess.setWfUid(wfProcess.getWfUid());
			newProcess.setIsShow(0);
			newProcess.setStepIndex(wfProcess.getStepIndex()+1);    
			newProcess.setStatus("0");
			newProcess.setPdfPath("");
			newProcess.setCommentJson("");
			newProcess.setAction_status(0);
			Employee currentEmp = employeeDao.queryEmployeeById(userId);
			newProcess.setUserUid(userId);
			newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
			newProcess.setIsMaster(1);
			newProcess.setSendPersonId("");
			newProcess.setIsMerge(2);
			newProcess.setFjbProcessId(wfProcess.getFjbProcessId());
		tableInfoDao.save(newProcess);
	}
	
	@Override
	public WfNode getWfNodeById(String id) {
		return tableInfoDao.getWfNodeById(id);
	}

	@Override
	public WfProcess getWfProcessByColoum(String finStanceId, String userid) {
		return tableInfoDao.getWfProcessByColoum(finStanceId, userid);
	}

	@Override
	public void addDoFileReceive(DoFileReceive doFileReceive) {
		tableInfoDao.addDoFileReceive(doFileReceive);
	}

	@Override
	public List<DoFileReceive> getDoFileReceiveList(String userId,
			Integer pageIndex, Integer pageSize,Integer status, Map<String,String> map) {
		return tableInfoDao.getDoFileReceiveList(userId, pageIndex, pageSize,status,map);
	}

	@Override
	public int getDoFileReceiveCount(String userId, Integer status, Map<String,String> map) {
		return tableInfoDao.getDoFileReceiveCount(userId, status,map);
	}

	@Override
	public DoFileReceive getDoFileReceiveById(String id) {
		return tableInfoDao.getDoFileReceiveById(id);
	}

	@Override
	public void updateDoFileReceive(DoFileReceive doFileReceive) {
		tableInfoDao.updateDoFileReceive(doFileReceive);
	}

	@Override
	public void updateProcess(WfProcess wfProcess) {
		tableInfoDao.updateProcess(wfProcess);
	}

	@Override
	public List<WfProcess> getProcessList(String instanceId) {
		return tableInfoDao.getProcessList(instanceId);
	}

	@Override
	public void updateIsyyById(String id) {
		tableInfoDao.updateIsyyById(id);
	}

	@Override
	public List<GetProcess> getChildWfProcessList(String instanceId,String processId) {
		WfProcess wfProcess = tableInfoDao.getProcessById(processId);
		WfProcess nextwfProcess = tableInfoDao.getProcessByInstanceIdAndDate(instanceId,wfProcess.getApplyTime());
		String beginTime =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(wfProcess.getApplyTime());
		String endTime = null;
		if(nextwfProcess!=null){
//			endTime =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(nextwfProcess.getApplyTime());
		}
		List<GetProcess>  list = tableInfoDao.getChildWfProcessList(instanceId,beginTime,endTime);
		if(list!=null && list.size()>0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			for(GetProcess getProcess : list){
				String nodeName = getProcess.getNodeName();
				if(nodeName==null || nodeName.equals("")){
					WfNode node = tableInfoDao.getWfNodeById(getProcess.getNodeId());
					if(node!=null){
						getProcess.setNodeName(node.getWfn_name());
					}
				}
				
				Date applyTime = getProcess.getApplyTime();
				if(null != applyTime){
					getProcess.setStr_applyTime(sdf.format(applyTime));
					
				}
				Date finshTime = getProcess.getFinshTime();
				if(null != finshTime){
					getProcess.setStr_finshTime(sdf.format(finshTime));
					
				}
			}
		}
		return list;
	}
	
	@Override
	public List<GetProcess> getJBProcessList(String instanceId,String processId) {
		
		List<GetProcess>  list = tableInfoDao.getJBProcessList(processId);
		if(list!=null && list.size()>0){
			for(GetProcess getProcess : list){
				String nodeName = getProcess.getNodeName();
				if(nodeName==null || nodeName.equals("")){
					WfNode node = tableInfoDao.getWfNodeById(getProcess.getNodeId());
					if(node!=null){
						getProcess.setNodeName(node.getWfn_name());
					}
				}
			}
		}
		return list;
	}

	@Override
	public void savePushMessage(PushMessage pm) {
		 tableInfoDao.savePushMessage(pm);
	}

	@Override
	public List<PushMessage> getPushMessageList(String employeeGuid,String userId,String instanceId) {
		return tableInfoDao.getPushMessageList(employeeGuid, userId, instanceId);
	}

	@Override
	public List<WfProcess>  insertAferEndProcess(WfNode wfNode, String pdfNewPath, WfProcess oldProcess,Employee emp, String value, String mergePdf) {
		String userIds = wfNode.getWfn_bd_user();
		Date nowTime = new Date();
		String wfChildFinstanceId = oldProcess.getfInstancdUid();
		List<WfProcess> returnList = new ArrayList<WfProcess>();
		//当前子流程的父流程下的所有子流程
		List<WfProcess> proList = tableInfoDao.getProcessList(wfChildFinstanceId);
		String finstanceId = "";
		if(("0").equals(oldProcess.getIsManyInstance())){
			finstanceId = wfChildFinstanceId;
		}else if(("1").equals(oldProcess.getIsManyInstance())){
			//获取子流程的父instanceId的父instanceId,因为有中间流程
			finstanceId = proList.get(0).getfInstancdUid();
			if(("").equals(finstanceId) || finstanceId == null || ("null").equals(finstanceId)){
				finstanceId = proList.get(0).getWfInstanceUid();
			}
		}
		//父流程等办的那一步
		List<WfProcess> wfProcessList = tableInfoDao.getWfProcessByInstanceidAndStatus(finstanceId);	
		//父步骤
		WfProcess f_wfprocess = wfProcessList.get(0);
		String  deadline = wfNode.getWfn_deadline();
		String  deadlineunit = wfNode.getWfn_deadlineunit();
		Date jdqxDate = getEndDate(nowTime, deadline, deadlineunit );
		
		WfItem wfItem = itemDao.getItemById(f_wfprocess.getItemId());
		String wcsx = wfItem.getVc_wcsx();		//办件完成期限
		
		
		Date zhqxDate = oldProcess.getZhqxDate();
		if(zhqxDate==null){
			zhqxDate = getEndDate(f_wfprocess.getApplyTime(), wcsx, "0");	//默认为工作日
		}
		if(userIds != null && !("").equals(userIds)){
			String[] userIdArr = userIds.split(",");
			for(int i=0;i<userIdArr.length;i++){
				WfProcess newProcess = new WfProcess();
					newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
					newProcess.setIsBack("0");
					newProcess.setfInstancdUid(f_wfprocess.getfInstancdUid());
					newProcess.setWfInstanceUid(f_wfprocess.getWfInstanceUid());
					newProcess.setStepIndex(wfProcessList.get(wfProcessList.size()-1).getStepIndex()+1);    
					newProcess.setIsChildWf(f_wfprocess.getIsChildWf());
					newProcess.setIsManyInstance(f_wfprocess.getIsManyInstance());
					newProcess.setNodeUid(wfNode.getWfn_id());
					newProcess.setItemId(f_wfprocess.getItemId());
					newProcess.setFormId(wfNode.getWfn_defaultform());
					//此处的oldFormId
					newProcess.setOldFormId(f_wfprocess.getFormId());
					//newProcess.setOldFormId(oldProcess.getFormId());
					newProcess.setFromUserId(emp.getEmployeeGuid());
					newProcess.setOwner(oldProcess.getOwner());
					newProcess.setApplyTime(nowTime);
					newProcess.setJdqxDate(jdqxDate);
					newProcess.setZhqxDate(zhqxDate);
					newProcess.setIsOver(Constant.NOT_OVER);
					newProcess.setIsEnd(0);
					newProcess.setProcessTitle(f_wfprocess.getProcessTitle());
					newProcess.setWfUid(f_wfprocess.getWfUid());
					newProcess.setIsShow(1);
					newProcess.setStatus("0");
					newProcess.setPdfPath(pdfNewPath);
					newProcess.setMergePdf(mergePdf);
					if(value==null || value.equals("")){
						newProcess.setCommentJson("");
					}else{
						newProcess.setCommentJson(f_wfprocess.getCommentJson());
					}
					
					newProcess.setAction_status(0);
					newProcess.setIsExchanging(0);
					newProcess.setFromNodeid(oldProcess.getToNodeid());
					newProcess.setToNodeid(wfNode.getWfn_id());
					newProcess.setAllInstanceid(oldProcess.getAllInstanceid());
					//主送人ID及标识值
					Employee currentEmp = employeeDao.queryEmployeeById(userIdArr[i]);
					newProcess.setUserUid(userIdArr[i]);
				    newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
				    
					newProcess.setIsMaster(1);
					newProcess.setFjbProcessId(oldProcess.getFjbProcessId());
	 			tableInfoDao.save(newProcess);//下一步
	 			returnList.add(newProcess);
	 	}
		}
		return returnList;
	}

	@Override
	public String getworkFlowIdByInstanceId(String instanceId) {
		return tableInfoDao.getworkFlowIdByInstanceId(instanceId);
	}
	
	@Override
	public String getFinstanceIdByInstanceId(String instanceId) {
		return tableInfoDao.getFinstanceIdByInstanceId(instanceId);
	}
	
	@Override
	public String getPinstanceId(String instanceId) {
		return tableInfoDao.getPinstanceId(instanceId);
	}

	@Override
	public DoFileReceive getDoFileReceive(String instanceId) {
		return tableInfoDao.getDoFileReceive(instanceId);
	}

	@Override
	public void addSw(Sw sw) {
		tableInfoDao.addSw(sw);
	}

	@Override
	public void addDoFile(DoFile doFile) {
		tableInfoDao.addDoFile(doFile);		
	}

	@Override
	public void addWSPBD(Map<String, String> map) {
		tableInfoDao.addWSPBD(map);
	}

	@Override
	public List<Sw> getSwdrlbList(int pageIndex, int pageSize) {
		return tableInfoDao.getSwdrlbList(pageIndex,pageSize);
	}

	@Override
	public int getCountSwdrlbList() {
		return tableInfoDao.getCountSwdrlbList();
	}

	@Override
	public List<WfTableInfo> getOfficeTableList(Map<String, String> map) {
		return tableInfoDao.getOfficeTableList(map);
	}

	@Override
	public void clearTableData(String[] tableNames) {
		tableInfoDao.clearTableData(tableNames);		
	}

	@Override
	public void removeOfficeTable(String[] tableNames) {
		tableInfoDao.removeOfficeTable(tableNames);
	}

	@Override
	public List<Object[]> getAllTableList() {
		return tableInfoDao.getAllTableList();
	}

	@Override
	public void updateDoFileIsDelete(String id,String isdelete) {
		tableInfoDao.updateDoFileIsDelete(id,isdelete);
	}

	@Override
	public int getCountHszDoFiles(String bigDepId, String conditionSql) {
		return tableInfoDao.getCountHszDoFiles(bigDepId,conditionSql);
	}

	@Override
	public List<DoFile> getHszDoFileList(String bigDepId, String conditionSql,
			Integer pageIndex, Integer pageSize) {
		List<DoFile> dofileList = new ArrayList<DoFile>();
		List<DoFile> dofList = tableInfoDao.getHszDoFileList(bigDepId,conditionSql,pageIndex,pageSize);
		for (DoFile doFile : dofList) {
			DoFile doFileBak = new DoFile();
			doFileBak.setDoFile_title(doFile.getDoFile_title());
			doFileBak.setItemId(doFile.getItemId());
			doFileBak.setDoFile_id(doFile.getDoFile_id());
			doFileBak.setWorkflowId(doFile.getWorkflowId());
			doFileBak.setInstanceId(doFile.getInstanceId());
			doFileBak.setDelUserId(doFile.getDelUserId());
			doFileBak.setDelUserName(doFile.getDelUserName());
			List<WfProcess> processList = tableInfoDao.getProcessList(doFile.getInstanceId());
			if(!("3").equals(doFile.getDoFile_result()+"")){
				int result = 0; //0:未办理 1:办理中 2:已办结 3:已作废
				if(processList!=null && !("").equals(processList) && processList.size() != 0){
					boolean isOver = true;
					if(processList.size()!=1){//不只是只有第一步
						for (WfProcess wfProcess : processList) {
							if(!Constant.OVER.equals(wfProcess.getIsOver())){
								isOver = false;
							}  
							if(wfProcess.getFinshTime()!=null && !("").equals(wfProcess.getFinshTime())){
								doFileBak.setDo_time(wfProcess.getFinshTime());
							}else{
								doFileBak.setDo_time(wfProcess.getApplyTime());
							}
						}
						if(isOver == false){
							result = 1;
						}else if(isOver == true){
							result = 2;
						}
					}
				}
				doFileBak.setDoFile_result(result);
			}else{
				doFileBak.setDoFile_result(doFile.getDoFile_result());
				doFileBak.setDo_time(doFile.getDo_time());
			}
			if(CommonUtil.stringIsNULL(doFile.getItemName())){
				String itemName = tableInfoDao.findItemNameById(doFile.getItemId());
				doFileBak.setItemName(itemName);
			}else{
				doFileBak.setItemName(doFile.getItemName());
			}
			if(processList.size()>0 && processList != null){
				doFileBak.setProcessId(processList.get(0).getWfProcessUid());
			}
			doFileBak.setFormId(doFile.getFormId());
			doFileBak.setNodeId(doFile.getNodeId());
			dofileList.add(doFileBak);
		}
		return dofileList;
	}

	@Override
	public DoFile getDoFileById(String id) {
		return tableInfoDao.getDoFileById(id);
	}

	@Override
	public List<WfProcess> getAllProcessList(String instanceId) {
		List<WfProcess> list = tableInfoDao.getProcessListByInstanceId(instanceId);
		if(list!=null&&list.size()!=0){
			List<WfProcess> list1 =getChildProcessList(instanceId);
			if(list1!=null&&list1.size()!=0){
				list.addAll(list1);
			}
		}
		return list;
	}
	
	private List<WfProcess> getChildProcessList(String pInstanceId){
		List<WfProcess> list = tableInfoDao.getProcessListByPinstanceId(pInstanceId);
		if(list!=null&&list.size()!=0){
			List<WfProcess> list1 =getChildProcessList(list.get(0).getWfInstanceUid());
			if(list1!=null&&list1.size()!=0){
				list.addAll(list1);
			}
		}
		return null;
	}

	@Override
	public void deleteWfProcesss(List<WfProcess> list) {
		if(list!=null){
			for(int i=0;i<list.size();i++){
				WfProcess wfProcess =list.get(i);
				tableInfoDao.deleteWfProcess(wfProcess);
			}
		}
	}

	@Override
	public void deleteDoFile(DoFile doFile) {
		tableInfoDao.deleteDoFile(doFile);
	}

	@Override
	public List<PushMessage> getPushMessageList(String employeeGuid,
			String insertid) {
		return tableInfoDao.getPushMessageList(employeeGuid,insertid);
	}

	@Override
	public List<Object[]> getTableColoumValue(String tableName, String coloum,
			String InstanceId) {
		return tableInfoDao.getTableColoumValue(tableName, coloum, InstanceId);
	}

	@Override
	public List<WfProcess> getWfProcessByInstanceidAndStatus(String finstanceId) {
		return tableInfoDao.getWfProcessByInstanceidAndStatus(finstanceId);
	}
	@Override
	public String getWaitingProcessId(WfProcess process){
		//newPdfPath为子流程的表单
		//父流程等办的那一步
		String wfChildFinstanceId = process.getfInstancdUid();
		//当前子流程的父流程下的所有子流程
		List<WfProcess> proList = tableInfoDao.getProcessList(wfChildFinstanceId);
		String finstanceId2 = "";
		if(("0").equals(process.getIsManyInstance())){
			finstanceId2 = wfChildFinstanceId;
		}else if(("1").equals(process.getIsManyInstance())){
			//获取子流程的父instanceId的父instanceId,因为有中间流程
			if(proList.size() >0){
				finstanceId2 = proList.get(0).getfInstancdUid();
				if(("").equals(finstanceId2) || finstanceId2 == null || ("null").equals(finstanceId2)){
					finstanceId2 = proList.get(0).getWfInstanceUid();
				}
			}
			
		}
		List<WfProcess> wfProcessList = tableInfoDao.getWfProcessByInstanceidAndStatus(finstanceId2);
		String waitingProcessId = "";
		for(WfProcess wfProcess : wfProcessList){
			String is_over = wfProcess.getIsOver();
			if(is_over!=null && is_over.equals("NOT_OVER")){
				waitingProcessId = wfProcess.getWfProcessUid();
				break;
			}
		}
		return waitingProcessId;
	}

	@Override
	public void update(WfProcess oldProcess) {
		tableInfoDao.update(oldProcess);
	}

	@Override
	public WfProcess isGetTs(String insertid) {
		List<WfProcess> list =tableInfoDao.getWfProcessCountList(insertid,1);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<String> getAttachmentTagByForm(String formId) {
		return tableInfoDao.getAttachmentTagByForm(formId);
	}
	@Override
	public WfProcess getMasterProcess(WfProcess oldProcess) {
		return tableInfoDao.getMasterProcess(oldProcess);
	}
	
	@Override
	public List<WfProcess> getNextProcess(WfProcess oldProcess) {
		return tableInfoDao.getNextProcess(oldProcess);
	}

	@Override
	public String getMaxProcessIdByAllInstanceId(String allInstanceId) {
		return tableInfoDao.getMaxProcessIdByAllInstanceId(allInstanceId);
	}

	@Override
	public Sw getSwByInstanceId(String instanceId) {
		return tableInfoDao.getSwByInstanceId(instanceId);
	}

	@Override
	public void updateSw(Sw sw) {
		tableInfoDao.updateSw(sw);
	}

	@Override
	public WfChild getwfChildByCId(String workflowId) {
		return tableInfoDao.getwfChildByCId(workflowId);
	}

	@Override
	public int getStepIndexByInstanceId(String instanceId) {
		return tableInfoDao.getStepIndexByInstanceId(instanceId);
	}
	
	@Override
	public List<Object[]> getDofileReceiveList(Map<String, String> map,
			Integer pageindex, Integer pagesize) {
		return tableInfoDao.getDofileReceiveList(map, pageindex, pagesize);
	}

	@Override
	public int getDofileReceiveCount(Map<String, String> map) {
		return tableInfoDao.getDofileReceiveCount(map);
	}

	@Override
	public  List<Object[]> getReceiveInfo(String fprocessid,String applydate) {
		return tableInfoDao.getReceiveInfo(fprocessid,applydate);
	}

	@Override
	public WfProcess getMaxWfProcessIdByInstanceId(String instanceid) {
		return tableInfoDao.getMaxWfProcessIdByInstanceId(instanceid);
	}

	@Override
	public int getCountMessage(WfProcess wfProcess, String employeeGuid) {
		return tableInfoDao.getCountMessage(wfProcess,employeeGuid);
	}

	@Override
	public void updatePushMessageZt(String employeeGuid, WfProcess wfProcess) {
		 tableInfoDao.updatePushMessageZt( employeeGuid,  wfProcess);
	}

	@Override
	public List<WfProcess> getNodeProcess(String workFlowId, String instanceId,
			String nodeId, String fInstanceId) {
		return tableInfoDao.getNodeProcess(workFlowId, instanceId, nodeId, fInstanceId);
	}
	@Override
	public List<Object[]> getIsBt(String workFlowId, String nodeid,
			String formId,String isbt) {
		return tableInfoDao.getIsBt(workFlowId, nodeid,formId,isbt,null);
	}

	@Override
	public Object[] getIsBtName(String workflowid, String nodeId,
			String newFormId, String formtagname) {
		List<Object[]> list=tableInfoDao.getIsBt(workflowid, nodeId,newFormId,null,formtagname);
		if(list!=null&&list.size()>0){
			return list.get(0); 
		}
		return null;
	}
	
	@Override
	public Object[] getIsBtName(String workflowid, String nodeId,
			String newFormId, String formtagname, String userId) {
		List<Object[]> list=tableInfoDao.getIsBt(workflowid, nodeId,newFormId,null,formtagname,userId);
		if(list!=null&&list.size()>0){
			return list.get(0); 
		}
		return null;
	}
	
	@Override
	public Object[] getIsPy(String workflowid, String nodeId,
			String newFormId, String formtagname) {
		List<Object[]> list=tableInfoDao.getIsPy(workflowid, nodeId,newFormId,null,formtagname);
		if(list!=null&&list.size()>0){
			return list.get(0); 
		}
		return null;
	}

	public String updateInstanceFirstStepOver(String workflowId,
			String instanceId,String nodeId, String userId,
			String title, String formId, String trueJson,
			String pdfPath,String itemId, String processId) {
		Date nowTime = new Date();
		//String newProcessId = "";
		//newProcessId = UuidGenerator.generate36UUID();
		//插入open第一步
		WfProcess newProcess = new WfProcess();
			newProcess.setWfProcessUid(processId);
			newProcess.setWfInstanceUid(instanceId);
			newProcess.setfInstancdUid("");
			newProcess.setWfUid(workflowId);
			newProcess.setNodeUid(nodeId);
			newProcess.setItemId(itemId);
			newProcess.setFormId(formId);
			newProcess.setOldFormId(formId);
			newProcess.setFromUserId(userId);
			Employee currentEmp = employeeDao.queryEmployeeById(userId);
			newProcess.setUserUid(userId);
		    newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
			newProcess.setFromNodeid(nodeId);
			newProcess.setToNodeid(nodeId);
			newProcess.setOwner(userId);
			newProcess.setApplyTime(nowTime);
			newProcess.setFinshTime(nowTime);
			newProcess.setStatus("1");
			if(title != null &&!"".equals(title)){
				newProcess.setProcessTitle(title);
			}else{
				newProcess.setProcessTitle("无标题");
			}
			newProcess.setStepIndex(1);
			newProcess.setIsOver(Constant.NOT_OVER);
			newProcess.setIsEnd(1);
			newProcess.setIsShow(1);
			newProcess.setIsChildWf("");
			newProcess.setIsManyInstance("");
			newProcess.setZhqxDate(nowTime);
			newProcess.setCommentJson(trueJson);
			newProcess.setPdfPath(pdfPath);
		tableInfoDao.save(newProcess);
		saveEndInstanceId(instanceId);
		//插入办结步骤
		WfProcess newOldProcess = pendingDao.getProcessByID(processId); 
		//this.addEndProcess(instanceId, title, nodeId, formId, newOldProcess,"");
		tableInfoDao.updateInstanceOver(workflowId, instanceId);
		
		//插入办件表
		DoFile doFile = new DoFile();
		doFile.setDoFile_title(title);
		doFile.setItemId(itemId);
		doFile.setWorkflowId(workflowId);
		doFile.setInstanceId(instanceId);
		doFile.setFormId(formId);
		String itemName = tableInfoDao.findItemNameById(newProcess.getItemId());
		doFile.setItemName(itemName);
		doFile.setNodeId(nodeId);
		if(newProcess.getFinshTime()!=null && !("").equals(newProcess.getFinshTime())){
			doFile.setDo_time(newProcess.getFinshTime());
		}else{
				doFile.setDo_time(newProcess.getApplyTime());
		}
		//父流程插入数据到dofile表中
		tableInfoDao.saveDoFile(doFile);
		return processId;
	}

	@Override
	public void updatePushMessage(String employeeGuid, WfProcess wfProcess) {
		 tableInfoDao.updatePushMessage( employeeGuid,  wfProcess);
	}

	@Override
	public void updateWfProcessByTs(String employeeGuid, WfProcess wfProcess) {
		tableInfoDao.updateWfProcessByTs(employeeGuid,wfProcess);
	}

	@Override
	public List<Object[]> getNewestWfProcess(String instanceId) {
		return tableInfoDao.getNewestWfProcess(instanceId);
	}

	@Override
	public List<WfProcess> getWfProcessList(WfProcess wfProcess) {
		return tableInfoDao.getWfProcessList(wfProcess);
	}

	@Override
	public void addProcess(WfProcess wfProcess) {
		tableInfoDao.addProcess(wfProcess);
	}

	@Override
	public List<Object[]> getKcqWfProcess() {
		
		return tableInfoDao.getKcqWfProcess();
	}

	@Override
	public Object[] getYwbList(String instanceid, String tableName, String culoum) {
		List<Object[]> list = tableInfoDao.getYwbList( instanceid,  tableName,  culoum);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<PushMessage> getPushMessage(String employeeGuid,
			String wfInstanceUid) {
		return tableInfoDao.getPushMessage(employeeGuid,wfInstanceUid);
		}

	@Override
	public String[] getIntanceIdByZxIntanceId(String instanceId, String doType) {
		List<Object> list = tableInfoDao.getIntanceIdByZxIntanceId( instanceId, doType);
		if(list!=null&&list.size()>0){
			String[] instanceIds=new String[list.size()];
			for(int i=0;i<list.size();i++){
				instanceIds[i]=(String)list.get(i);
			}
			return instanceIds;
		}
		return null;
	}

	@Override
	public List<WfProcess> getWfProcessByEntity(WfProcess wfProcess) {
		return tableInfoDao.getWfProcessByEntity(wfProcess);
	}
	
	@Override
	public int getCountOfDoFileFavourites(String bigDepId, String conditionSql,
			String userId) {
		return tableInfoDao.getCountOfDoFileFavourites(bigDepId, conditionSql, userId);
	}

	@Override
	public List<DoFile> getDoFileFavouriteList(String bigDepId, String conditionSql, String userId, Integer pageindex, Integer pagesize) {
		List<DoFile> dofList = tableInfoDao.getDoFileFavouriteList(bigDepId, conditionSql, userId, pageindex, pagesize);
		for (DoFile doFile : dofList) {
			String instanceId = doFile.getInstanceId();
			List<WfProcess> wfps = pendingDao.findProcessListByFId(instanceId);
			if(null != wfps && wfps.size()>0){
				//当前办件有子流程
				doFile.setIsHaveChild("1");
			}else{
				//当前办件没有子流程
				List<WfProcess> wfpss = pendingDao.getProcessByInstanceId(instanceId);
				boolean isChild = false;
				for(WfProcess wfp:wfpss){
					if(CommonUtil.stringNotNULL(wfp.getfInstancdUid())){
						isChild = true;
					}
				}
				if(isChild){
					//当前办件是子流程
					doFile.setIsHaveChild("1");
				}else{
					//当前办件是主流程
					doFile.setIsHaveChild("0");
				}
			}
			
			List<WfProcess> processList = tableInfoDao.getProcessList(doFile.getInstanceId());
			if(!("3").equals(doFile.getDoFile_result()+"")){
				int result = 0; //0:未办理 1:办理中 2:已办结 3:已作废
				if(processList!=null && !("").equals(processList) && processList.size() != 0){
					boolean isOver = true;
					if(processList.size()!=1){//不只是只有第一步
						for (WfProcess wfProcess : processList) {
							if(!Constant.OVER.equals(wfProcess.getIsOver())){
								isOver = false;
							}  
							if(wfProcess.getFinshTime()!=null && !("").equals(wfProcess.getFinshTime())){
								doFile.setDo_time(wfProcess.getFinshTime());
							}else{
								doFile.setDo_time(wfProcess.getApplyTime());
							}
							doFile.setIsChildWf(wfProcess.getIsChildWf());
						}
						if(isOver == false){
							result = 1;
						}else if(isOver == true){
							result = 2;
						}
					}
				}
				doFile.setDoFile_result(result);
			}
			if(CommonUtil.stringIsNULL(doFile.getItemName())){
				String itemName = tableInfoDao.findItemNameById(doFile.getItemId());
				doFile.setItemName(itemName);
			}
			if(processList.size()>0 && processList != null){
				doFile.setProcessId(processList.get(0).getWfProcessUid());
			}
			
			doFile.setEntrustName(tableInfoDao.findEntrustName(doFile.getInstanceId()));
			WfItem item = itemDao.getItemById(doFile.getItemId());
			if(null != item){
				doFile.setItemType(item.getVc_sxlx());
			}
			if(StringUtils.isNotBlank(doFile.getDoFile_title())){
				doFile.setDoFile_title(doFile.getDoFile_title().replace("\r\n", "").replace("\r", "").replace("\n", ""));
			}
		}
		return dofList;
	}
	
	@Override
	public void saveDofileFavourite(DofileFavourite dofileFavourite) {
		tableInfoDao.saveDofileFavourite(dofileFavourite);
		
	}

	@Override
	public void removeDofileFavourite(DofileFavourite dofileFavourite) {
		tableInfoDao.removeDofileFavourite(dofileFavourite);
	}

	@Override
	public DofileFavourite getDofileFavouriteById(String dofileId, String userId) {
		return tableInfoDao.getDofileFavouriteById(dofileId, userId);
	}
	
	public String getFavDoFileListOfMobile(String conditionSql, String userId,  int count, int column,
			Integer pagesize,String serverUrl){
		List<DoFile> dofileList = new ArrayList<DoFile>();
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		//1,获取dofile列表
		List<DoFile> dofList  = tableInfoDao.getDoFileFavouriteList("", conditionSql, userId, column, pagesize );
		//2,初始化某些dofile列表
		for (DoFile doFile : dofList) {
			DoFile doFileBak = new DoFile();
			doFileBak.setDoFile_title(doFile.getDoFile_title());
			doFileBak.setItemId(doFile.getItemId());
			doFileBak.setDoFile_id(doFile.getDoFile_id());
			doFileBak.setWorkflowId(doFile.getWorkflowId());
			doFileBak.setInstanceId(doFile.getInstanceId());
			List<WfProcess> processList = tableInfoDao.getProcessList(doFile.getInstanceId());
			if(!("3").equals(doFile.getDoFile_result()+"")){
				int result = 0; //0:未办理 1:办理中 2:已办结 3:已作废
				if(processList!=null && !("").equals(processList) && processList.size() != 0){
					boolean isOver = true;
					if(processList.size()!=1){//不只是只有第一步
						for (WfProcess wfProcess : processList) {
							if(!Constant.OVER.equals(wfProcess.getIsOver())){
								isOver = false;
							}  
							if(wfProcess.getFinshTime()!=null && !("").equals(wfProcess.getFinshTime())){
								doFileBak.setDo_time(wfProcess.getFinshTime());
							}else{
								doFileBak.setDo_time(wfProcess.getApplyTime());
							}
						}
						if(isOver == false){
							result = 1;
						}else if(isOver == true){
							result = 2;
						}
					}
				}
				doFileBak.setDoFile_result(result);
			}else{
				doFileBak.setDoFile_result(doFile.getDoFile_result());
				doFileBak.setDo_time(doFile.getDo_time());
			}
			if(CommonUtil.stringIsNULL(doFile.getItemName())){
				String itemName = tableInfoDao.findItemNameById(doFile.getItemId());
				doFileBak.setItemName(itemName);
			}else{
				doFileBak.setItemName(doFile.getItemName());
			}
			if(processList.size()>0 && processList != null){
				doFileBak.setProcessId(processList.get(0).getWfProcessUid());
			}
			doFileBak.setFormId(doFile.getFormId());
			doFileBak.setNodeId(doFile.getNodeId());
			doFileBak.setFavourite(doFile.getFavourite());
			if(doFileBak.getProcessId()!=null&&!"".equals(doFileBak.getProcessId())){
				WfProcess process = tableInfoDao.getProcessById(doFileBak.getProcessId());
				if(process.getNodeUid()!=null&&!"".equals(process.getNodeUid())){
					 WfNode node = tableInfoDao.getWfNodeById(process.getNodeUid());
					 doFileBak.setNodeName(node.getWfn_name());
				}
			}
			dofileList.add(doFileBak);
		}
		
		//3,封装到todos列表中
		List<Todos> todoList = new ArrayList<Todos>();
		if(dofileList.size()!=0 && dofileList!=null && !("").equals(dofileList)){
			for (DoFile doFile : dofileList) {
				Todos todo = new Todos();
					todo.setTitle(doFile.getDoFile_title());
					if(doFile.getDo_time() != null ){
						todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(doFile.getDo_time()));
					}
					String processId = doFile.getProcessId();
					if(processId== null){
						continue;
					}
					todo.setProcessId(processId);
					todo.setInstanceId(doFile.getInstanceId());
					todo.setUserId(userId);
					todo.setFormId(doFile.getFormId());
					todo.setNodeId(doFile.getNodeId());
					todo.setWorkFlowId(doFile.getWorkflowId());
					String itemId = doFile.getItemId();
					todo.setItemId(doFile.getItemId());
					WfItem wfItem = itemDao.getItemById(itemId);
					todo.setItemType(wfItem.getVc_sxlx()); //0-发文，1-办文，2-传阅
					todo.setItemName(wfItem.getVc_sxmc());	//事项名称
					todo.setIsMaster("");
					todo.setIsfavourite("1");
				todoList.add(todo);
			}
		}
		
		return JSONArray.fromObject(todoList).toString();
	}
	
	@Override
	public int getDoFileReceiveCountOfMobile(String userId, Integer status, String condition) {
		return tableInfoDao.getDoFileReceiveCountOfMobile(userId, status,condition);
	}

	@Override
	public String getDofileReceiveListOfMobile(String departId,String userId,Integer pageIndex,
			Integer pageSize,Integer status, String condition) {
		List<DoFileReceive>	list =  tableInfoDao.getDofileReceiveListOfMobile(userId,pageIndex, pageSize, status,condition);
		List<Receiver> receiveList = new ArrayList<Receiver>();
		List<WfItem> itemList = itemDao.getItemList(departId);
		for(int i = 0 ; i < list.size() ; i++){
			DoFileReceive doFile = list.get(i);
			Receiver receiver = new Receiver();
			receiver.setItemName(doFile.getItemName());
			receiver.setReceiveType(doFile.getReceiveType());
			receiver.setTitle(doFile.getTitle());
			receiver.setItemList(itemList);
			receiver.setItemId(doFile.getItemId());
			receiver.setId(doFile.getId());
			receiver.setProcessId(doFile.getProcessId());
			receiveList.add(receiver);
		}
		return JSONArray.fromObject(receiveList).toString();
	}

	@Override
	public void saveDoFile(DoFile doFile) {
		tableInfoDao.saveDoFile(doFile);
	}

	@Override
	public List<DoFileReceive> getDofileFavouriteByFprocessId(
			String fProcessid) {
		return tableInfoDao.getDofileFavouriteByFprocessId(fProcessid);
	}

	@Override
	public String getSwdjh(String type) {
		return tableInfoDao.getSwdjh(type);
	}

	/**
	 * 获取收文信息
	 * @return
	 */
	@Override
	public List<Object[]> findSw(Integer pageIndex, Integer pageSize,
			String deptName, String swdj, String conditionSql, String conditionSql2) {
		return tableInfoDao.findSw(pageIndex,pageSize,deptName,swdj, conditionSql,conditionSql2);
	}

	@Override
	public int findCountSw(String deptName, String swdj, String conditionSql,String conditionSql2) {
		return tableInfoDao.findCountSw(deptName,swdj, conditionSql,conditionSql2);
	}

	@Override
	public void saveMsgSend(MsgSend msgSend) {
		tableInfoDao.saveMsgSend(msgSend);
	}

	@Override
	public DoFileReceive addSend(String userId,Sw sw, String fjpath, Employee emp,
			String newInstanceId, WfProcess wfProcess,boolean isReback,Date nowTime) {
		
		//插入待收
		DoFileReceive dfr = new DoFileReceive();
		dfr.setInstanceId(newInstanceId);
		dfr.setpInstanceId(wfProcess.getWfInstanceUid());
		dfr.setFormUserId(wfProcess.getUserUid());
		dfr.setProcessId(UuidGenerator.generate36UUID());
		 			//dfr.setToUserId(userId);
		// 是否回复 
		if(isReback){
			dfr.setIsReback(1);
		}else{
			dfr.setIsReback(0);
		}
		dfr.setToDepartId(userId);
		dfr.setApplyDate(nowTime);
		dfr.setType(1);
		dfr.setStatus(0);
		// 直接发文
		dfr.setReceiveType("1");
		dfr.setfProcessId(wfProcess.getWfProcessUid());
		dfr.setPdfpath(fjpath);
		tableInfoDao.addDoFileReceive(dfr);

		Sw newSw = new Sw();
		newSw.setLwbt(sw.getLwbt());
		newSw.setLwdw(sw.getLwdw());
		newSw.setLwh(sw.getLwh());
		newSw.setYfdw(sw.getYfdw());
		newSw.setGwlx(sw.getGwlx());
		newSw.setFwsj(nowTime);
		newSw.setInstanceid(newInstanceId);
		newSw.setZsdw(sw.getZsdw());
		newSw.setCsdw(sw.getCsdw());
		newSw.setZtc(sw.getZtc());
		newSw.setJjcd(sw.getJjcd());
		tableInfoDao.addSw(newSw);
		return dfr;
	}

	@Override
	public String findPendDealIngUser(String processId) {
		WfProcess wfp = getProcessById(processId);
		String userName = "";
		if(wfp!=null){
			String instanceId = wfp.getWfInstanceUid();	//流程id
			Integer stepIndex = wfp.getStepIndex();	//步骤
			Date apply_time = wfp.getApplyTime();		//申请时间
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
			String  applyTime = format.format(apply_time);  // 2007-01-18
			userName = tableInfoDao.findPendDealIngUser(processId, instanceId, stepIndex, applyTime);
		}
		return userName;
	}

	@Override
	public int findAllDofileListCount(String wfTitle) {
		return tableInfoDao.findAllDofileListCount(wfTitle);
	}

	@Override
	public List<DoFile> findAllDofileList(String conditionSql, Integer pageIndex,
			Integer pageSize) {
		List<DoFile> dofileList = new ArrayList<DoFile>();
		List<DoFile> dofList = tableInfoDao.findAllDofileList(conditionSql, pageIndex, pageSize);
		for (DoFile doFile : dofList) {
			DoFile doFileBak = new DoFile();
			doFileBak.setDoFile_title(doFile.getDoFile_title());
			doFileBak.setItemId(doFile.getItemId());
			doFileBak.setDoFile_id(doFile.getDoFile_id());
			doFileBak.setWorkflowId(doFile.getWorkflowId());
			doFileBak.setInstanceId(doFile.getInstanceId());
			List<WfProcess> processList = tableInfoDao.getProcessList(doFile.getInstanceId());
			if(!("3").equals(doFile.getDoFile_result()+"")){
				int result = 0; //0:未办理 1:办理中 2:已办结 3:已作废
				if(processList!=null && !("").equals(processList) && processList.size() != 0){
					boolean isOver = true;
					if(processList.size()!=1){//不只是只有第一步
						for (WfProcess wfProcess : processList) {
							if(!Constant.OVER.equals(wfProcess.getIsOver())){
								isOver = false;
							}  
							if(wfProcess.getFinshTime()!=null && !("").equals(wfProcess.getFinshTime())){
								doFileBak.setDo_time(wfProcess.getFinshTime());
							}else{
								doFileBak.setDo_time(wfProcess.getApplyTime());
							}
						}
						if(isOver == false){
							result = 1;
						}else if(isOver == true){
							result = 2;
						}
					}
				}
				doFileBak.setDoFile_result(result);
			}else{
				doFileBak.setDoFile_result(doFile.getDoFile_result());
				doFileBak.setDo_time(doFile.getDo_time());
			}
			if(CommonUtil.stringIsNULL(doFile.getItemName())){
				String itemName = tableInfoDao.findItemNameById(doFile.getItemId());
				doFileBak.setItemName(itemName);
			}else{
				doFileBak.setItemName(doFile.getItemName());
			}
			if(processList.size()>0 && processList != null){
				doFileBak.setProcessId(processList.get(0).getWfProcessUid());
			}
			doFileBak.setFormId(doFile.getFormId());
			doFileBak.setNodeId(doFile.getNodeId());
			dofileList.add(doFileBak);
		}
		return dofileList;
	}
	
	@Override
	public List<DoFileReceive> getDoFileReceiveByPIdAndDeptIds(
			String pInstanceId, String linkDeptIds) {
		
		return tableInfoDao.getDoFileReceiveByPIdAndDeptIds(pInstanceId,linkDeptIds);
	}

	@Override
	public void addRely(Reply reply){
		tableInfoDao.addReply(reply);
		
	}

	@Override
	public DoFileReceive addSend(String deptId, String userId, Sw sw,
			String fjpath, Employee emp, String newInstanceId,
			WfProcess wfProcess, boolean isReback, Date nowTime) {
		// 插入 process
		WfProcess newProcess = new WfProcess();
		newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
		newProcess.setfInstancdUid("");
		newProcess.setWfInstanceUid(newInstanceId);
		newProcess.setWfUid(wfProcess.getWfUid());
		newProcess.setFinshTime(nowTime);
		newProcess.setApplyTime(nowTime);
		newProcess.setProcessTitle(sw.getLwbt());
		newProcess.setIsOver(Constant.NOT_OVER);
		newProcess.setFromNodeid("");
		newProcess.setToNodeid("");
		newProcess.setPdfPath(fjpath);
		newProcess.setNodeUid(wfProcess.getNodeUid());
		newProcess.setItemId(wfProcess.getItemId());
		newProcess.setFormId("0");
		newProcess.setOldFormId("");
		newProcess.setFromUserId(emp.getEmployeeGuid());
		newProcess.setOwner(emp.getEmployeeGuid());
		newProcess.setIsEnd(0);
		newProcess.setIsShow(1);
		newProcess.setStepIndex(1);    
		newProcess.setStatus("0");
		newProcess.setAction_status(0);
		newProcess.setAllInstanceid(newInstanceId);
		newProcess.setIsExchanging(0);
		newProcess.setUserUid(deptId);
		Employee currentEmp = employeeDao.queryEmployeeById(deptId);
		if(currentEmp != null){
			  newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
		}else{
			newProcess.setUserDeptId(deptId);
		}
	     
		newProcess.setIsMaster(1);
		newProcess.setIsBack("0");
		newProcess.setIsExchanging(0);
		newProcess.setFjbProcessId(wfProcess.getFjbProcessId());
		tableInfoDao.save(newProcess);
		
		//插入待收
		DoFileReceive dfr = new DoFileReceive();
		dfr.setInstanceId(newInstanceId);
		dfr.setpInstanceId(wfProcess.getWfInstanceUid());
		dfr.setFormUserId(wfProcess.getUserUid());
		dfr.setProcessId(newProcess.getWfProcessUid());
		 			//dfr.setToUserId(userId);
		// 是否回复 
		if(isReback){
			dfr.setIsReback(1);
		}else{
			dfr.setIsReback(0);
		}
		dfr.setToUserId(userId);
		dfr.setToDepartId(deptId);
		dfr.setApplyDate(nowTime);
		dfr.setType(1);
		dfr.setStatus(0);
		// 直接发文
		dfr.setReceiveType("1");
		dfr.setfProcessId(null);
		dfr.setPdfpath(fjpath);
		tableInfoDao.addDoFileReceive(dfr);

		Sw newSw = new Sw();
		newSw.setLwbt(sw.getLwbt());
		newSw.setLwdw(sw.getLwdw());
		newSw.setLwh(sw.getLwh());
		newSw.setYfdw(sw.getYfdw());
		newSw.setGwlx(sw.getGwlx());
		newSw.setFwsj(nowTime);
		newSw.setInstanceid(newInstanceId);
		newSw.setZsdw(sw.getZsdw());
		newSw.setCsdw(sw.getCsdw());
		newSw.setZtc(sw.getZtc());
		newSw.setJjcd(sw.getJjcd());
		tableInfoDao.addSw(newSw);
		return dfr;
	
	}
	
	@Override
	public List<DocXgDepartment> getDocXgDepartmentListByDepId(String depid) {
		return tableInfoDao.getDocXgDepartmentListByDepId(depid);
	}

	@Override
	public void sendNextProcess(String vc_title, String userId,
			String employeeGuid, String commentJson, String pdfPath,
			WfProcess oldProcess, String nextNodeId) {
		String instanceId = oldProcess.getWfInstanceUid();
		WfNode wfNode = pendingDao.getWfNode(nextNodeId);
		
		String  deadline = wfNode.getWfn_deadline();
		String  deadlineunit = wfNode.getWfn_deadlineunit();
		String itemid = oldProcess.getItemId();
		WfItem wfItem = itemDao.getItemById(itemid);
		String wcsx = wfItem.getVc_wcsx();		//办件完成期限
		Date jdqxDate =null;
		Date nowTime = new Date();
		if(Utils.isNotNullOrEmpty(deadline)&&!deadline.equals("0")){
			jdqxDate = getEndDate(nowTime, deadline, deadlineunit );
		}
		Integer action_status = wfNode.getAction_status();	
		String workFlowid = oldProcess.getWfUid() ;
		WfProcess wfProcess = pendingDao.getFirstStepWfProcess(instanceId,workFlowid);
		Date apply_date = oldProcess.getApplyTime();
		String fInstancdUid = oldProcess.getWfInstanceUid();
		if(fInstancdUid!=null && !fInstancdUid.equals("")){  //该流程为子流程
			wfProcess = pendingDao.getFirstStepWfProcess(fInstancdUid,"");
		}
		if(wfProcess!=null){
			apply_date = wfProcess.getApplyTime();
		}
		//计算最后的工作期限
		Date zhqxDate = null;
		zhqxDate = oldProcess.getZhqxDate();
		if(zhqxDate==null){
			if(Utils.isNotNullOrEmpty(wcsx)&&!wcsx.equals("0")){
				zhqxDate = getEndDate(apply_date, wcsx, "0");	//默认为工作日
			}
		}
		String route_type = wfNode.getWfn_route_type(); 
		
		String m_userIds = "";
		String c_userIds = "";
		if(route_type!=null && route_type.equals("2")){	//并行完成式
			String userIds = wfNode.getWfn_bd_user();
			if(userIds!=null && !userIds.equals("")){
				String[] zscs = userIds.split(";");
				m_userIds = zscs[0];
				c_userIds = zscs[1];
			}
		}else{
			m_userIds = wfNode.getWfn_bd_user();
		}
		if(m_userIds==null || m_userIds.equals("")){
			m_userIds = userId;
		}
		
		Employee employee = null;
		if(route_type!=null && route_type.equals("3")){
			List<Employee> emplist = employeeDao.getEmployeeByEmployeeIds(m_userIds);
			if(emplist!=null && emplist.size()>0){
				employee = emplist.get(emplist.size()-1);
			}
		}
		
		if(c_userIds != null || m_userIds!=null){
			String[] c_user_arr = new String[0];//抄送ID数组
			if(!"".equals(c_userIds)){
				 c_user_arr = c_userIds.split(",");
			}
			String[] m_user_arr = new String[0]; //主送ID数组
			if(!"".equals(m_userIds)&&null != m_userIds){
				m_user_arr = m_userIds.split(",");
			}
			int len = c_user_arr.length+m_user_arr.length;
			//new socketUtil类
			WebSocketUtil webSocket = new WebSocketUtil();
			for(int i=0;i<len; i++){
				//websocket推送消息
				WfProcess newProcess = new WfProcess();
				newProcess.setWfInstanceUid(oldProcess.getWfInstanceUid());
				newProcess.setNodeUid(nextNodeId);
				newProcess.setItemId(oldProcess.getItemId());
				newProcess.setFormId(wfNode.getWfn_defaultform());
				newProcess.setOldFormId(oldProcess.getFormId());
				newProcess.setFromUserId(employeeGuid);
				newProcess.setOwner(oldProcess.getOwner());
				newProcess.setApplyTime(nowTime);
				newProcess.setJdqxDate(jdqxDate);
				newProcess.setZhqxDate(zhqxDate);
				newProcess.setIsOver(Constant.NOT_OVER);
				newProcess.setProcessTitle((vc_title==null || "".equals(vc_title)) ? oldProcess.getProcessTitle() : vc_title);
				newProcess.setWfUid(oldProcess.getWfUid());
				newProcess.setIsShow(1);
				newProcess.setStatus("0");
				newProcess.setPdfPath(pdfPath);
				newProcess.setCommentJson(commentJson);
				newProcess.setAction_status(action_status);
				newProcess.setIsExchanging(0);
				newProcess.setFromNodeid(oldProcess.getNodeUid());
				newProcess.setToNodeid(nextNodeId);
				newProcess.setAllInstanceid(oldProcess.getAllInstanceid());
				newProcess.setMergePdf(oldProcess.getMergePdf());
				newProcess.setIsMaster(1);
				Employee currentEmp = null;
				if(route_type!=null && route_type.equals("3")){	//并行结合: m_user_arr有值
					currentEmp = employeeDao.queryEmployeeById(m_user_arr[len-i-1]);
					newProcess.setUserUid(m_user_arr[len-i-1]);	
					//tableIndex排序
					if(employee.getEmployeeGuid().equals(m_user_arr[len-i-1])){
						newProcess.setIsMaster(2);			
					}else{
						newProcess.setIsMaster(1);
					}
					oldProcess.setSendPersonId(m_user_arr[len-i-1]);  
					try {
						webSocket.apnsPush(oldProcess.getProcessTitle(), employeeGuid, "", "", "", m_user_arr[len-i-1]);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					if(i>=c_user_arr.length ){
						//主送人ID及标识值
						currentEmp =  employeeDao.queryEmployeeById(m_user_arr[len-i-1]);
						newProcess.setUserUid(m_user_arr[len-i-1]);
						newProcess.setIsMaster(1);
						oldProcess.setSendPersonId(m_user_arr[len-i-1]);
						try {
							webSocket.apnsPush(oldProcess.getProcessTitle(), employeeGuid, "", "", "", m_user_arr[len-i-1]);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}else{
						//抄送
						currentEmp = employeeDao.queryEmployeeById(c_user_arr[i]);
						newProcess.setUserUid(c_user_arr[i]);
						newProcess.setIsMaster(0);
						oldProcess.setSendPersonId(c_user_arr[i]);
						try {
							webSocket.apnsPush(oldProcess.getProcessTitle(), employeeGuid, "", "", "", c_user_arr[i]);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			    newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
				newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
				newProcess.setStepIndex(oldProcess.getStepIndex()+1);
				newProcess.setIsEnd(0);
				newProcess.setFjbProcessId(oldProcess.getFjbProcessId());
				newProcess.setfInstancdUid(oldProcess.getfInstancdUid());
				newProcess.setIsChildWf(oldProcess.getIsChildWf());
				newProcess.setIsManyInstance(oldProcess.getIsManyInstance());
				tableInfoDao.save(newProcess);
				//判断发送的节点是否是督办，是就入库等待定时器扫描发送短信
				addDuBanList(newProcess);
			}
		}
	}

	@Override
	public WfProcess findBxjhWfProcess(WfProcess wfProcess) {
		return tableInfoDao.findBxjhWfProcess(wfProcess);
	}

	@Override
	public void falseDeleteDoFile(String dofileId ,String instanceId,Employee emp) {
		// 假删除 dofile 表
		tableInfoDao.falseDeleteDoFile(dofileId,instanceId, emp);
		// 假删除 process
		tableInfoDao.falseDeleteProcess(instanceId);
	}

	@Override
	public void recoverDoFile(String dofileId ,String instanceId) {
		tableInfoDao.recoverDoFile(dofileId,instanceId);
		tableInfoDao.recoverProcess(instanceId);
	}
	
	@Override
	public DzJcdb findDzJcdbById(String no) {
		return tableInfoDao.findDzJcdbById(no);
	}

	@Override
	public void updateDzJcdb(DzJcdb dzJcdb) {
		tableInfoDao.updateDzJcdb(dzJcdb);
	}

	@Override
	public DzJcdbShip findDzJcdbShipById(String instanceId) {
		return tableInfoDao.findDzJcdbShipById(instanceId);
	}

	@Override
	public void saveDzJcdbShip(DzJcdbShip dzJcdbShip) {
		tableInfoDao.saveDzJcdbShip(dzJcdbShip);
	}	
	
	
	/**
	 * 多子流程插入新的步骤
	 */
	public List<String> sendFirstProcess(String userIds,String nextNodeId,WfProcess oldProcess,String commonJSON) {
		Date currentTime = new Date();
		String[] userList = userIds.split(",");
		WfNode firstNode = workflowBasicFlowDao.getWfNode(nextNodeId);
		String formPdfPath = "";
		if(firstNode.getWfn_form_continue() != null && firstNode.getWfn_form_continue().equals("1")){
			formPdfPath = PathUtil.getWebRoot()+"form/html/"+ workflowBasicFlowDao.findFormPdf(firstNode.getWfn_defaultform());
		}else{
			// 沿用表单
			formPdfPath = oldProcess.getPdfPath().split(",")[1];
		}
		List<String> processIdList = new ArrayList<String>();
		for(int i = 0, size = userList.length; i < size ; i++){
			WfProcess newProcess = new WfProcess();
			
			newProcess.setWfProcessUid(UuidGenerator.generate36UUID());
			processIdList.add(newProcess.getWfProcessUid());
			newProcess.setWfInstanceUid(UuidGenerator.generate36UUID());
			newProcess.setFromNodeid(nextNodeId);
			newProcess.setToNodeid(nextNodeId);
			newProcess.setNodeUid(nextNodeId);
			newProcess.setIsChildWf(null);
			newProcess.setIsManyInstance(null);
			newProcess.setItemId(oldProcess.getItemId());
			newProcess.setFormId(firstNode.getWfn_defaultform());
			newProcess.setOldFormId(firstNode.getWfn_defaultform());
			newProcess.setFromUserId(userList[i]);
			newProcess.setOwner(userList[i]);
			newProcess.setApplyTime(currentTime);
			newProcess.setJdqxDate(null);
			newProcess.setZhqxDate(null);
			newProcess.setIsOver(Constant.NOT_OVER); //正常待办--NOT_OVER
			newProcess.setIsEnd(0);
			newProcess.setProcessTitle(oldProcess.getProcessTitle());
			newProcess.setWfUid(firstNode.getNode_startJb());
			newProcess.setIsShow(1);
			newProcess.setStepIndex(1);    
			newProcess.setStatus("0");
			newProcess.setAction_status(null);
			newProcess.setAllInstanceid(oldProcess.getAllInstanceid());
			newProcess.setPdfPath(formPdfPath+","+formPdfPath);
			Employee currentEmp = employeeDao.queryEmployeeById(userList[i]);
			newProcess.setUserUid(userList[i]);
			 newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
			newProcess.setIsMaster(1);
			newProcess.setIsBack("0");
			newProcess.setIsExchanging(0);
			newProcess.setFjbProcessId(oldProcess == null ?null:oldProcess.getWfProcessUid());
			newProcess.setCommentJson(commonJSON);

			//插入办件表
			DoFile doFile = new DoFile();
			doFile.setDoFile_title(oldProcess.getProcessTitle());
			doFile.setItemId(newProcess.getItemId());
			doFile.setWorkflowId(newProcess.getWfUid());
			doFile.setInstanceId(newProcess.getWfInstanceUid());
			doFile.setFormId(newProcess.getFormId());
			String itemName = tableInfoDao.findItemNameById(newProcess.getItemId());
			doFile.setItemName(itemName);
			doFile.setNodeId(newProcess.getNodeUid());
			if(newProcess.getFinshTime()!=null && !("").equals(newProcess.getFinshTime())){
				doFile.setDo_time(newProcess.getFinshTime());
			}else{
				doFile.setDo_time(newProcess.getApplyTime());
			}
			tableInfoDao.saveDoFile(doFile);
			tableInfoDao.save(newProcess);//下一步
		}
			
		return processIdList;
	}

	@Override
	public boolean isAllJBEnd(String fjbProcessId) {
		List<WfProcess> list =  tableInfoDao.isAllJBEnd(fjbProcessId);
		if(list == null || list.size()==0){
			return true;
		}else{
			for(int i = 0 ,size = list.size(); i< size ;i++){
				if(list.get(i).getIsEnd()==0){
					return false;
				}
			}
			return true;
		}
	}
	
	
	
	@Override
	public void saveEndInstanceId(String instanceId) {
		EndInstanceId endInstan = new EndInstanceId();
		endInstan.setInstanceId(instanceId);
		tableInfoDao.saveEndInstanceId(endInstan);
	}
	
	@Override
	public boolean delEndInstanceId(String instanceId) {
		return tableInfoDao.delEndInstanceId(instanceId);
	}

	@Override
	public void deleteErrorProcess() {
		tableInfoDao.deleteErrorProcess();
	}

	@Override
	public void insertFirstTempProcess(String processId, String operate,
			String instanceId, String nodeId, String userId, String title,
			String workFlowId, String itemId, String pdfPath, String trueJson,
			String formId) {
		Date nowTime = new Date();
		WfProcess newProcess = new WfProcess();
		newProcess.setWfProcessUid(processId);
		newProcess.setWfInstanceUid(instanceId);
		newProcess.setNodeUid(nodeId);
		newProcess.setFromUserId(userId);
		newProcess.setOwner(userId);
		newProcess.setApplyTime(nowTime);
		newProcess.setFinshTime(nowTime);
		newProcess.setIsOver(Constant.NOT_OVER);
		newProcess.setIsEnd(0);
		newProcess.setProcessTitle(title);
		newProcess.setWfUid(workFlowId);
		newProcess.setItemId(itemId);
		newProcess.setToNodeid(nodeId);
		newProcess.setFromNodeid("first");
		//主送人ID及标识值
		Employee currentEmp = employeeDao.queryEmployeeById(userId);
		newProcess.setUserUid(userId);
		newProcess.setUserDeptId(currentEmp.getDepartmentGuid());
		newProcess.setIsMaster(1);
		newProcess.setIsShow(1);
		newProcess.setStepIndex(1);
		newProcess.setStatus("0");
		newProcess.setPdfPath(pdfPath);
		newProcess.setCommentJson(trueJson);
		newProcess.setFormId(formId);
		newProcess.setIsExchanging(0);	//保存当前流程(不走公文交换)
		newProcess.setAction_status(2);
		tableInfoDao.save(newProcess);
	}

	@Override
	public List<WfProcess> findWfProcessList(WfProcess wfp) {
		return tableInfoDao.findWfProcessList(wfp);
	}

	@Override
	public List<WfProcess> findWfProcessList(String instanceId,
			Integer stepIndex) {
		return tableInfoDao.findWfProcessList(instanceId, stepIndex,null);
	}

	@Override
	public List<WfProcess> findStepWfPListByUserId(String instanceId,
			Integer stepIndex, String userString) {
		return tableInfoDao.findWfProcessList(instanceId, stepIndex,userString);
	}
	
	@Override
	public int getNewCountDoFiles(String conditionSql) {
		return tableInfoDao.getNewCountDoFiles(conditionSql);
	}

	@Override
	public List<DoFile> getNewDoFileList(String serverUrl,String conditionSql,
			Integer pageIndex, Integer pageSize) {
		List<DoFile> list = new ArrayList<DoFile>();
		List<Object> dofList = tableInfoDao.getNewDoFileList(conditionSql,pageIndex,pageSize);
		if(dofList != null){
			for(int i =0 ; i< dofList.size() ; i++){
				Object[] obj = (Object[])dofList.get(i);
				DoFile dofile = new DoFile();
				dofile.setDoFile_id(obj[0].toString());
				dofile.setWorkflowId(obj[1].toString());
				dofile.setItemId(obj[2].toString());
				dofile.setItemName(obj[3].toString());
				dofile.setInstanceId(obj[4].toString());
				dofile.setFormId(obj[5].toString());
				dofile.setApplyTime(obj[6].toString());
				dofile.setDoFile_title(obj[7] == null ?"":obj[7].toString());
				dofile.setDoFile_result(Integer.valueOf(obj[8].toString()));
				dofile.setNodeName(obj[9] == null ?"":obj[9].toString());
				dofile.setNodeId(obj[10] == null ?"":obj[10].toString());
				dofile.setProcessId(obj[11] == null ?"":obj[11].toString());
				dofile.setLink(serverUrl+"/table_openOverForm.do?favourite=&processId="+obj[11].toString()+"&itemId="+obj[2].toString()+"&formId="+obj[5].toString()+"&t="+new Date());
				list.add(dofile);
			}
		}
		return list;
	}

	@Override
	public void saveAndModifyData(String fprocessid) {
		tableInfoDao.modifyReceiveData(fprocessid);
		tableInfoDao.modifySwData(fprocessid);
	}

	@Override
	public List<DoFileReceive> getDeptIdByFprocessId(String fprocessid) {
		return tableInfoDao.getDeptIdByFprocessId(fprocessid);
	}

	@Override
	public net.sf.json.JSONObject deleteSendInfo(String fProcessId) {
		net.sf.json.JSONObject result = new net.sf.json.JSONObject();
		try{
			//删除父流程数据
			WfProcess wfp = tableInfoDao.getProcessById(fProcessId);
			if(wfp!=null){
				String instanceId = wfp.getWfInstanceUid();
				String workFlowId = wfp.getWfUid();
				DoFile dofile = tableInfoDao.getDoFileByElements(workFlowId, instanceId);
				if(dofile!=null){
					tableInfoDao.deleteWfProcess(workFlowId, instanceId);
					tableInfoDao.deleteDoFile(dofile);
				}
			}
			List<DoFileReceive> receList = tableInfoDao.getDofileFavouriteByFprocessId(fProcessId);
			
			for(DoFileReceive doFileReceive:receList){
				String instanceId = doFileReceive.getInstanceId();
				DoFile file = tableInfoDao.getDoFileByElements("", instanceId);
				if(file!=null){		//已经形成办件
					tableInfoDao.deleteDoFile(file);
				}else{
					tableInfoDao.deleteWfProcess("", instanceId);
					tableInfoDao.deleteDoFileSwList(instanceId);
				}
				//删除receive与sw
				tableInfoDao.deleteDoFileReceiveList(fProcessId);
			}
			result.put("success", true);
		}catch(Exception e){
			result.put("success", false);
		}
		return result;
	}

	@Override
	public WfItem findWfItemById(String id) {
		return itemDao.getItemById(id);
	}

	@Override
	public WfProcess findSendWfProcess(String instanceId) {
		return tableInfoDao.findSendWfProcess(instanceId);
	}

	@Override
	public WfProcess findFakeWfProcess(String instanceId) {
		return tableInfoDao.findFakeWfProcess(instanceId);
	}
	
	@Override
	public String editCommentWriteRole(String json, String processId,
			String instanceId, String nodeId, String userId) {
		//1.意见为空
		if(json==null || json.equals("")||json.equals("{}") ||json.equals("[]")){
			return "";
		}
		//3.找寻相同节点,相同人员调整的内容(后续拓展)
		//2.检测processIdshifo
		Map<String, String> comment = new HashMap<String, String>();
		//解析意见
		net.sf.json.JSONArray js;
		try{
			net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(json);
			if(obj.containsKey("pages")){
				js	= obj.getJSONArray("pages");
				int size = js.size();
				for(int i = 0; i<size; i++){
					net.sf.json.JSONObject jsonObject = (net.sf.json.JSONObject)js.get(i);
					net.sf.json.JSONArray arr = null;
					net.sf.json.JSONArray arr2 = null;
					try{
						arr = jsonObject.getJSONArray("processes");
						arr2 = jsonObject.getJSONArray("stamps");
					}catch (Exception e) {
					}
					if((arr!=null && arr.size()>0) || (arr2!=null && arr2.size()>0) ){
						for(int j=0; j<arr.size(); j++){
							net.sf.json.JSONObject comm = (net.sf.json.JSONObject) arr.get(j);
							String id = (String)comm.get("processId");
							if(processId!=null && processId.equals(id)){
								String old_json = comm.toString();
								String new_json = old_json.replaceAll("\"isWrite\":0", "\"isWrite\":1").replaceAll("\"isSignWrite\":0", "\"isSignWrite\":1").replaceAll("\"isSignWrite\":\"0\"", "\"isSignWrite\":1");
								comment.put(old_json, new_json);
							}
						}
					}
				}
			}
			//遍历map,将意见内容修改编辑权限修改调整
			for (String key : comment.keySet()) {
				String value = comment.get(key);
				json = json.replace(key, value);
			}
			return json;
		}catch (Exception e) {
			return json;
		}
	}

	@Override
	public List<WfProcess> getProcessListByParams(Map<String, String> map){
		return tableInfoDao.getProcessListByParams(map);
	}

	@Override
	public List<Object[]> getDoFileByPsbjList(int i, Map<String, String> map,
			int pageIndex, int pageSize) {
		return null;
	}
	
	/**
	 * 点击发送按钮发送
	 */
	public WfProcess saveWfProcessToNewItem(WfProcess wfProcess, String userId,Sw sw, Employee emp,
			String newInstanceId,String formUserId, String itemId, String newWfrocessId){
		//根据itemId获取一系列的信息
		WfItem item = itemDao.getItemById(itemId);
		String wfUId = "";
		String nodeId = "";
		String formId = "";
		String pdfPath = "";
		if(item!=null){
			wfUId = item.getLcid();
			WfNode wfNode = workflowBasicFlowDao.findFirstNodeId(wfUId);
			nodeId = wfNode.getWfn_id();
			formId = wfNode.getWfn_defaultform();
			ZwkjForm form = zwkjFormDao.getOneFormById(formId);
			pdfPath = form.getForm_pdf();
		}
		
		WfProcess newProcess = new WfProcess();
			newProcess.setWfProcessUid(newWfrocessId);
			newProcess.setfInstancdUid("");
			newProcess.setWfInstanceUid(newInstanceId);
			newProcess.setFinshTime(null);
			newProcess.setApplyTime(new Date());
			newProcess.setProcessTitle(wfProcess.getProcessTitle());
			newProcess.setIsOver(Constant.NOT_OVER);
			newProcess.setFromNodeid(nodeId);
			newProcess.setToNodeid("");
			newProcess.setPdfPath(pdfPath+","+pdfPath);
			newProcess.setCommentJson(wfProcess.getCommentJson());
			newProcess.setNodeUid(nodeId);
			newProcess.setItemId(itemId);
			newProcess.setWfUid(wfUId);
			newProcess.setFormId(formId);
			newProcess.setOldFormId("");
			newProcess.setFromUserId(wfProcess.getOwner());
			newProcess.setOwner(wfProcess.getOwner());
			newProcess.setIsEnd(0);
			newProcess.setIsShow(1);
			newProcess.setStepIndex(1);    
			newProcess.setStatus("0");
			newProcess.setAction_status(0);
			newProcess.setAllInstanceid(newInstanceId);
			newProcess.setIsExchanging(0);
			emp = employeeDao.queryEmployeeById(userId);
			newProcess.setUserUid(wfProcess.getOwner());
			if(emp != null){
				newProcess.setUserDeptId(emp.getDepartmentGuid());
			}else{
				newProcess.setUserDeptId(userId);
			}
			newProcess.setIsMaster(1);
			newProcess.setIsBack("0");
		tableInfoDao.save(newProcess);
		
		List<SendAttachments> attZwList = attachmentDao.findAllSendAtts(wfProcess.getWfInstanceUid()+"attzw",null);
		//剔除多余的附件名称
		List<SendAttachments>  list = new ArrayList<SendAttachments>();
		list.addAll(attZwList);
		//剔除垃圾数据
		List<SendAttachments>  attList = new ArrayList<SendAttachments>();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getFiletype()!=null){
				attList.add(list.get(i));
			}
		}
		Sw newSw = new Sw();
		newSw.setLwbt(sw.getLwbt());
		newSw.setLwdw(sw.getLwdw());
		newSw.setLwh(sw.getLwh());
		newSw.setYfdw(sw.getYfdw());	//发文单位
		newSw.setGwlx(sw.getGwlx());
		newSw.setFs(sw.getFs());
		newSw.setFwsj(new Date());		//发文时间
		newSw.setInstanceid(newInstanceId);
		newSw.setZsdw(sw.getZsdw());
		newSw.setCsdw(sw.getCsdw());
		newSw.setZtc(sw.getZtc());
		newSw.setJjcd(sw.getJjcd());
		tableInfoDao.addSw(newSw);
		return newProcess;
	}
	
	@Override
	public List<LowDoc> getLowDocList(String userId,
			Integer pageIndex, Integer pageSize,Integer status, Map<String,String> map) {
		List<LowDoc> list = tableInfoDao.getLowDocList(userId, pageIndex, pageSize,status,map);
		return list;
	}
	
	@Override
	public List<DoFileReceive> getReceiveAllList(String userId,
			Integer pageIndex, Integer pageSize,Integer status, Map<String,String> map) {
		return tableInfoDao.getReceiveAllList(userId, pageIndex, pageSize,status,map);
	}

	@Override
	public List<String> getDelPendingList(String conditionSql,String userId, Integer pageIndex, Integer pageSize) {
		List<String> pendList = tableInfoDao.getDelPendingList(conditionSql,userId, pageIndex, pageSize);
		return pendList;
	}

	@Override
	public int getCountOfDelPending(String conditionSql, String userId,
			String type) {
		return tableInfoDao.getCountOfDelPending(conditionSql, userId, type);
	}
	@Override
	public int getReceiveAllCount(String userId, Integer status, Map<String,String> map) {
		return tableInfoDao.getReceiveAllCount(userId, status,map);
	}

	@Override
	public List<StepIndexVO> getStepList(String instanceId) {
		List<Integer> stepList = tableInfoDao.getStepIndex(instanceId);//查出所有的办件步骤数(stepIndex)
		if(null != stepList && stepList.size()>0){
			List<StepIndexVO> siVOList = new ArrayList<StepIndexVO>();
			String userPhoneNums = "";
			for (Integer stepIndex : stepList) {//遍历步骤数，查出每一步的办理人数
				List<Object[]> list = tableInfoDao.getProcessByStepIndex(instanceId, stepIndex);
				StepIndexVO indexVO = new StepIndexVO();
				if(null != list && list.size()>0){
					Object[] objs = list.get(0);
					indexVO.setNodeName(null != objs[0]? objs[0].toString():"");
					indexVO.setNodeId(null != objs[12]? objs[12].toString():"");
					indexVO.setApplyTime(null != objs[1]? objs[1].toString():"");
					indexVO.setStepIndex(stepIndex);
					List<WfProcessVO> wfVOList = new ArrayList<WfProcessVO>();
					String isEnd = "0";
					for (Object[] objects : list) {//遍历每步的办理数
						WfProcessVO processVO = new WfProcessVO();
						processVO.setDoType(null != objects[3]? objects[3].toString():"");
						processVO.setUserName(null != objects[4]? objects[4].toString():"");
						String receiveTime = null != objects[5]? objects[5].toString():"";
						processVO.setReceiveTime(receiveTime);
						String finishTime = null != objects[6]? objects[6].toString():"";
						processVO.setFinishTime(finishTime);
						String isOver = null != objects[7]? objects[7].toString():"";
						String isBack = null != objects[8]? objects[8].toString():"";
						isEnd = null != objects[9]? objects[9].toString():"";
						String userPhoneNum = null != objects[10]? objects[10].toString():"";
						if(userPhoneNum.length()==11){
						processVO.setMobileNum(userPhoneNum);
						userPhoneNums += userPhoneNum+",";
						}
						String status = "";//办理情况
						
						if(StringUtils.isNotBlank(isBack) && isBack.equals("2")){
							status = "0";//废弃过程
						}else{
							if(StringUtils.isNotBlank(isOver)){
								if(isOver.equals("OVER")){
									status = "1";//已办结
								}else{
									if(StringUtils.isBlank(receiveTime)){
										status = "2";//未接收
									}else{
										status = "3";//办结中
									}
								}
							}
						}
						processVO.setStatus(status);
						wfVOList.add(processVO);
						
					}
					indexVO.setStepUserList(wfVOList);
					indexVO.setIsEnd(isEnd);
					indexVO.setMobileNum(userPhoneNums);
					indexVO.setModuleid(null != objs[11]? objs[11].toString():"");
					siVOList.add(indexVO);
				}
			}
			return siVOList;
		}
		return null;
		
	}
	
	@Override
	public List<FollowShip> getFollowShips(String instanceId,
			String employeeGuid, String oldInstanceId) {
		return tableInfoExtendDao.selectFollowShip(instanceId, employeeGuid, oldInstanceId);
	}
	
	@Override
	public void editFollowShip(FollowShip entity) {
		tableInfoExtendDao.updateFollowShip(entity);
	}

	@Override
	public List<WfProcess> findWfProcessByInstanceIdAndStepIndex(
			String instanceId, Integer stepindex) {
		return tableInfoDao.findWfProcessByInstanceIdAndStepIndex(instanceId, stepindex);
	}

	@Override
	public WfProcess getOverWfpByInstanceId(String instanceId) {
		return tableInfoDao.getOverWfpByInstanceId(instanceId);
	}
	
	@Override
	public String getDofileReceiveListOfMobile(String userId, Integer pageIndex,Integer pageSize,Integer status, Map<String,String> map, List<WfItem> itemList) {
		List<DoFileReceive> list = tableInfoDao.getDoFileReceiveList(userId, pageIndex, pageSize,status,map);
	  	String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
	  	String serverUrl = map.get("serverUrl");
	  	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list != null && list.size() > 0){
			String pdfPath = "";
			String newPdfPath = "";
			for(int i = 0; i < list.size(); i++){
				DoFileReceive dofileRec = list.get(i);
				if(dofileRec != null){
					pdfPath = dofileRec.getPdfpath();
					 if(pdfPath.startsWith(newPdfRoot)){
						 newPdfPath = serverUrl+ "/form/html/workflow/"+pdfPath.substring(newPdfRoot.length());
				    }else{
				    	newPdfPath = serverUrl+ "/form/html/"+pdfPath.substring(pdfPath.lastIndexOf("/") + 1);
				    }
					 list.get(i).setPdfpath(newPdfPath);
					 if(itemList != null && itemList.size() > 0){
					     list.get(i).setItemList(itemList);
					 }
					 list.get(i).setDate(sdf.format(list.get(i).getFwsj()));
				}
				
			}
		}
		return JSONArray.fromObject(list).toString();
	}
	
	@Override
	public int getCountCyDoFiles(String conditionSql, String userId) {
		return tableInfoDao.getCountCyDoFiles(conditionSql,userId);
	}
	
	@Override
	public List<DoFile> getCyDoFileList(String conditionSql,String userId,Integer pageindex, Integer pagesize) {
		List<DoFile> dofList = tableInfoDao.getCyDoFileList(conditionSql,userId, pageindex,pagesize);
		for (DoFile doFile : dofList) {
			List<WfProcess> processList = tableInfoDao.getProcessList(doFile.getInstanceId());
			if(!("3").equals(doFile.getDoFile_result()+"")){
				int result = 0; //0:未办理 1:办理中 2:已办结 3:已作废
				if(processList!=null && !("").equals(processList) && processList.size() != 0){
					boolean isOver = true;
					if(processList.size()!=1){//不只是只有第一步
						for (WfProcess wfProcess : processList) {
							if(!Constant.OVER.equals(wfProcess.getIsOver())){
								isOver = false;
							}  
							if(wfProcess.getFinshTime()!=null && !("").equals(wfProcess.getFinshTime())){
								doFile.setDo_time(wfProcess.getFinshTime());
							}else{
								doFile.setDo_time(wfProcess.getApplyTime());
							}
						}
						if(isOver == false){
							result = 1;
						}else if(isOver == true){
							result = 2;
						}
					}
				}
				doFile.setDoFile_result(result);
			}
			if(CommonUtil.stringIsNULL(doFile.getItemName())){
				String itemName = tableInfoDao.findItemNameById(doFile.getItemId());
				doFile.setItemName(itemName);
			}
			if(processList.size()>0 && processList != null){
				doFile.setProcessId(processList.get(0).getWfProcessUid());
			}
		}
		return dofList;
	}

	@Override
	public String getCyDofileListOfMobile(String conditionSql,String userId,Integer pageindex, Integer pagesize,String serverUrl) {
		List<DoFile> dofList = tableInfoDao.getCyDoFileList(conditionSql,userId, pageindex,pagesize);
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		List<Todos> todoList = new ArrayList<Todos>();
		if(dofList.size()!=0 && dofList!=null && !("").equals(dofList)){
			for (DoFile dofile : dofList) {
				List<WfProcess> processList = tableInfoDao.getProcessList(dofile.getInstanceId());
				Todos todo = new Todos();
				if(StringUtils.isNotBlank(dofile.getDoFile_title())){
					todo.setTitle(dofile.getDoFile_title().replace("\r\n", "").replace("\n", "").replace("\r", ""));
				}                                                                                                      	
				todo.setDate(dofile.getApplyTime());
				if(null != processList && processList.size()>0){
					todo.setProcessId(processList.get(0).getWfProcessUid());
				}else{
					todo.setProcessId(dofile.getProcessId());
				}                                                                                                 	
				todo.setInstanceId(dofile.getInstanceId());                                                                                               	
				todo.setUserId(userId);                                                                                                                         	
				todo.setFormId(dofile.getFormId());                                                                                                           	
				todo.setNodeId(dofile.getNodeId());                                                                                                       	
				todo.setWorkFlowId(dofile.getWorkflowId());                                                                                               	
				todo.setItemId(dofile.getItemId()); 
				int favCount = tableInfoDao.getFavFileCount(userId, dofile.getInstanceId());
				if(favCount>0){
					todo.setIsfavourite("1");
				}else{
					todo.setIsfavourite("0");
				}
				if(CommonUtil.stringIsNULL(dofile.getItemName())){
					String itemName = tableInfoDao.findItemNameById(dofile.getItemId());
					dofile.setItemName(itemName);
				}
				todo.setItemName(dofile.getItemName());                                                                                                       	
				todoList.add(todo);
			}
		}
		return JSONArray.fromObject(todoList).toString();
	}
	
	@Override
	public List<Object[]> findeWfps(String fromNodeId, String nodeId,
			String instanceId) {
		return tableInfoDao.findeWfps(fromNodeId, nodeId, instanceId);
	}
	
	@Override
	public List<Employee> findToSortEmployeeList(String allInstanceId,
			String nodeIds) {
		return tableInfoDao.findToSortEmployeeList(allInstanceId, nodeIds);
	}

	@Override
	public WfProcess findLastProcess(String instanceId, String nodeId,
			String userId) {
		return tableInfoDao.findLastProcess(instanceId, nodeId, userId);
	}

	@Override
	public Integer countMyProcess(String instanceId, String userId) {
		return tableInfoDao.countMyProcess(instanceId, userId);
	}

	@Override
	public String addTrueJsonNode(String trueJson, Map<String, String> map) {
		try {
			net.sf.json.JSONObject obj1 = net.sf.json.JSONObject.fromObject(trueJson);
			JSONArray pagesArr = obj1.getJSONArray("pages");
			JSONArray valArr = new JSONArray();
			net.sf.json.JSONObject obj2 = pagesArr.getJSONObject(0);
			JSONArray formdataArr = obj2.getJSONArray("formdata");
			if (map != null && !("").equals(map) && map.size() != 0) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					boolean flag = false;
					for (int i = 0; i < formdataArr.size(); i++) {
						net.sf.json.JSONObject obj3 = formdataArr.getJSONObject(i);
						if(entry.getKey().equals(obj3.getJSONObject("id"))){
							flag = true;
						}
					}
					
					if(!flag){
						flag = false;
						
						
					}
				}
			}
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public List<DoFile> getDoFile(String ids) {
		String[] idss = ids.split(",");
		List<String> list = new ArrayList<String>();
		for (String id : idss) {
			list.add(id);
		}
		return tableInfoDao.getDoFile(list);
	}

	
	// 处理自动办理
	public void toAutoSendNext(List<WfProcess> wfpList) {
		String sameProcess = "", nextNodeId = "", fromNodeId = "", routeType = "";
		WfNode wn = null;
		if (wfpList != null && wfpList.size() > 0) {
			WfProcess lastp = wfpList.get(wfpList.size() - 1);
			if (lastp != null) {
				sameProcess = lastp.getWfProcessUid();
				nextNodeId = lastp.getNodeUid();
				wn = workflowBasicFlowDao.findNodeById(nextNodeId);
				fromNodeId = lastp.getFromNodeid();
				routeType = wn.getWfn_route_type();
			}
			for (int i = 0; i < wfpList.size(); i++) {// 自动办理节点前一个节点 是多人
				WfProcess wp = wfpList.get(i);
				if (wp != null && wn != null) {
					int isMaster = 1;
					if(routeType.equals("2")){//并行完全式
						isMaster = wp.getIsMaster();
					}
					if (wn.getWfn_autoSend() != null && wn.getWfn_autoSend() == 1) {
						if (wn.getWfn_autoSendDays() != null && wn.getWfn_autoSendDays() != 0) {
							int autosenddays = wn.getWfn_autoSendDays();
							Calendar calendar = Calendar.getInstance();
							calendar.add(Calendar.DAY_OF_MONTH, autosenddays);
							Date autoDate = calendar.getTime();
							String pId = wp.getWfProcessUid();
							AutoSend autosend = new AutoSend();
							autosend.setProcessid(pId);
							autosend.setCreateDate(new Date());
							autosend.setAutoDate(autoDate);
							autosend.setSameProcess(sameProcess);
							autosend.setIsMaster(isMaster);
							tableInfoDao.saveAutoSend(autosend);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 流程办结
	 */
	@Override
	public String updateInstanceOverAuto(String workflowId,String instanceId,String beforeProcessId ,String oldProcessId ,
			String nodeId,String userId,String title,String formId,String trueJson, String pdfPath, String isWriteNewValue,String newProcessId){
		// 新建或更新oldProcess
		WfProcess oldProcess = pendingDao.getProcessByID(oldProcessId); 
		
		Date nowTime = new Date();
		if (oldProcessId !=null && oldProcess != null && !("false").equals(isWriteNewValue)){
			// 更新
			oldProcess.setFinshTime(nowTime);
			oldProcess.setProcessTitle(title);
			oldProcess.setIsOver(Constant.OVER); // 设置当前步骤已经结束
			oldProcess.setStatus("1");
			oldProcess.setCommentJson(trueJson);
			tableInfoDao.update(oldProcess);
			
			
			//插入办结步骤
			this.addEndProcess(instanceId, title, nodeId, formId, oldProcess, pdfPath,newProcessId);
			RemoteLogin remote = new RemoteLogin();
			boolean checkUser = RemoteLogin.checkPassed;
			if(checkUser){
				remote.deleteThirdPend(oldProcess.getUserUid(), oldProcessId);
			}
		}
		return newProcessId;
	}
	
	public Integer getNoOwnerDofileCount(Map<String, String> map){
		return tableInfoDao.getNoOwnerDofileCount(map);
	}
	
	public List<Object[]> getNoOwnerDofileList(Map<String, String> map,  Integer pageindex, Integer pagesize){
		return tableInfoDao.getNoOwnerDofileList(map, pageindex, pagesize);
	}

	@Override
	public String findWh(String instanceId) {
		return tableInfoDao.findWh(instanceId);
	}

	@Override
	public void addRecallLog(WfRecallLog recallLog) {
		tableInfoDao.addRecallLog(recallLog);
	}

	@Override
	public List<WfDuBanLog> getDuBanMsg() {
		return tableInfoDao.getDuBanMsg();
	}

	@Override
	public List<String> getOverProcessId(String instanceId) {
		if(StringUtils.isNotBlank(instanceId)){
			return tableInfoDao.selectOverProcessId(instanceId);
		}
		return null;
	}
	
	@Override
	public Object[] getNewsProcessByInstanceid(String instanceId) {
		return tableInfoDao.getNewsProcessByInstanceid(instanceId); 
	}

	@Override
	public List<WfProcess> findWfProcessListByIsOver(WfProcess wfp) {
		return tableInfoDao.findWfProcessListByIsOver(wfp);
	}
	
	@Override
	public List<WfProcess> findWfProcessIdByInsIdAndStp(String instanceId,
			Integer stepIndex) {
		return tableInfoDao.findWfProcessIdByInsIdAndStp(instanceId, stepIndex,null);
	}
	
	@Override
	public List<WfProcess> getStepByEntity(WfProcess wfProcess) {
		return tableInfoDao.getStepByEntity(wfProcess);
	}

	@Override
	public WfProcess findEndWfProcessByInstanceId(String instanceId) {
		return tableInfoDao.findEndWfProcessByInstanceId(instanceId);
	}
	
	@Override
	public List<WfProcess> findWfProcessAnyInfo(String workFlowId,String instanceId,String nodeId, Integer stepIndex){
		return tableInfoDao.findWfProcessAnyInfo(workFlowId, instanceId, nodeId,stepIndex);
	}

	@Override
	public void updateIsShowByProcessId(String processId) {
		tableInfoDao.updateIsShowByProcessId(processId);
	}

	@Override
	public OfficeInfoView getOfficeInfoByInstanceId(String instanceId) {
		return tableInfoDao.getOfficeInfoByInstanceId(instanceId);
	}

	@Override
	public String findDuBanListByInsId(String instanceId) {
		Object[] view = tableInfoDao.findDuBanListByInsId(instanceId);
		if(view!=null&&CommonUtil.stringNotNULL(view[1]+"")){
			return view[1]+"";
		}
		return null;
	}

	@Override
	public List<DoFile> findDoFilesByIds(String ids) {
		List<DoFile> doFileList = tableInfoDao.findDoFilesByIds(ids);
		for (DoFile doFile : doFileList) {
			List<Object[]> processList = tableInfoDao.getFastProcessList(doFile.getInstanceId());
			if(!("3").equals(doFile.getDoFile_result()+"")){
				int result = 0; //0:未办理 1:办理中 2:已办结 3:已作废
				if(processList!=null && !("").equals(processList) && processList.size() != 0){
					boolean isOver = true;
					if(processList.size()!=1){//不只是只有第一步
						for (Object[] wfProcess : processList) {
							if(!Constant.OVER.equals(wfProcess[1])){
								isOver = false;
							}  
						}
						if(isOver == false){
							result = 1;
						}else if(isOver == true){
							result = 2;
						}
					}
				}
				doFile.setDoFile_result(result);
			}else{
				doFile.setDoFile_result(doFile.getDoFile_result());
			}
			if(CommonUtil.stringIsNULL(doFile.getItemName())){
				String itemName = tableInfoDao.findItemNameById(doFile.getItemId());
				doFile.setItemName(itemName);
			}
			if(processList.size()>0 && processList != null){
				doFile.setProcessId((String)processList.get(0)[0]);
			}
			//WfItem item = itemDao.getItemById(doFile.getItemId());
			List<String> itemList = tableInfoDao.getFastItemList(doFile.getItemId());//优化速度用
			if(null != itemList){
				doFile.setItemType(itemList.get(0));
			}
			
			doFile.setEntrustName(tableInfoDao.findEntrustName(doFile.getInstanceId()));
			if(StringUtils.isNotBlank(doFile.getDoFile_title())){
				doFile.setDoFile_title(doFile.getDoFile_title().replace("\r\n", "").replace("\r", "").replace("\n", ""));
			}
		}
		return doFileList;
	}
	
	@Override
	public String findDuBanTimeByInsId(String instanceId) {
		Object[] view = tableInfoDao.findDuBanListByInsId(instanceId);
		if(view!=null&&CommonUtil.stringNotNULL(view[2]+"")){
			return view[2]+"";
		}
		return "";
	}

	@Override
	public int getCountOfOperateLog(String instanceId) {
		return tableInfoDao.getCountOfOperateLog(instanceId);
	}

	@Override
	public List<TrueOperateLog> findOperateLogList(String instanceId,Integer pageindex, Integer pagesize) {
		return tableInfoDao.findOperateLogList(instanceId,pageindex,pagesize);
	}

	@Override
	public List<WfProcess> findWfProcessByInsAndIndex(String instanceId,
			Integer stepindex) {
		return tableInfoDao.findWfProcessByInsAndIndex(instanceId, stepindex);
	}

	@Override
	public void updateOverByNodeId(String workFlowId, String instanceId,
			String nodeId) {
		tableInfoDao.updateOverByNodeId(workFlowId, instanceId, nodeId);
	}

	@Override
	public void updateEndProcessStep(String instanceId, String nextNodeId) {
		BigDecimal maxStepIndex = tableInfoDao.findMaxStepIndexByNodeId(instanceId,nextNodeId);
		WfProcess endProcess = tableInfoDao.findEndWfProcessByInstanceId(instanceId);
		if(endProcess!=null&&maxStepIndex!=null&&endProcess.getStepIndex()==maxStepIndex.intValue()){
			endProcess.setStepIndex(endProcess.getStepIndex()+1);
			endProcess.setFinshTime(new Date((new Date()).getTime()+2000));
			tableInfoDao.update(endProcess);
		}
	}

	@Override
	public List<DoFile> setDoFileApplyTime(List<DoFile> doFileList) {
		if(doFileList!=null && doFileList.size()>0){
			for(DoFile doFile : doFileList){
				String applyTime = tableInfoDao.getApplyTimeByInsId(doFile.getInstanceId());
				doFile.setApplyTime(applyTime);
			}
		}
		return doFileList;
	}

	@Override
	public List<String> queryMultDeptByEmpId(String userId) {
		return tableInfoDao.queryMultDeptByEmpId(userId);
	}

	@Override
	public String querySiteIdByDeptId(String deptId) {
		return tableInfoDao.querySiteIdByDeptId(deptId);
	}

	@Override
	public boolean isHavePendingByInsIdAndEmpId(String instanceId, String userId) {
		List<WfProcess> list = tableInfoDao.findPendingByInsIdAndUserId(instanceId,userId);
		return (list!=null&&list.size()>0)?true:false;
	}

	@Override
	public List<Employee> findAllLeaveEmps() {
		return tableInfoDao.findAllLeaveEmps();
	}

	@Override
	public EmployeeLeave findEmpLeave(Employee emp) {
		return tableInfoDao.findEmpLeave(emp);
	}

	@Override
	public void saveEmployeeLeave(EmployeeLeave employeeLeave) {
		tableInfoDao.saveEmloyeeLeave(employeeLeave);
	}

	@Override
	public List<WfProcess> findPendingOfUserId(Employee emp, int fileType) {
		return tableInfoDao.findPendingOfUserId(emp.getEmployeeGuid(),fileType);
	}

	@Override
	public void deleteWhOfFw(String instanceId) {
		tableInfoDao.deleteWhOfFw(instanceId);
	}

	@Override
	public void deleteWhOfBw(String instanceId) {
		tableInfoDao.deleteWhOfBw(instanceId);
	}

	@Override
	public Integer findJjcd(String instanceId) {
		String jjcd = tableInfoDao.findJjcd(instanceId);
		if(jjcd==null){
			return 0;
		}
		if(jjcd.equals("特急")){
			return 3;
		}
		if(jjcd.equals("急")){
			return 2;
		}
		return 0;
	}

	@Override
	public String getViewBhByInstanceId(String instanceId) {
		return tableInfoDao.getViewBhByInstanceId(instanceId);
	}

	@Override
	public String findLwdw(String instanceId) {
		return tableInfoDao.findLwdw(instanceId);
	}

	@Override
	public String findJjcdToString(String instanceId) {
		
		return tableInfoDao.findJjcd(instanceId);
	}

	@Override
	public String checkIsMultDept(Employee emp,String workflowId) {
		if(emp==null){
			return null;
		}
		String nowSiteId = null; 
		WfItem item = tableInfoDao.findItemByWorkFlowId(workflowId);
		if(item!=null){
			nowSiteId = item.getVc_ssbmid();
		}
		if(StringUtils.isNotBlank(nowSiteId) &&!nowSiteId.equals(emp.getSiteId())){
			return nowSiteId;
		}
		return null;
	}

	@Override
	public String getDefaultMultDeptName(Employee emp, String nowSiteId,
			String cval) {
		if(emp==null || nowSiteId==null){
			return cval;
		}
		List<String> deptIds = tableInfoDao.queryMultDeptByEmpId(emp.getEmployeeGuid());
		if(deptIds!=null && deptIds.size()>0){
			for(int i=0;i<deptIds.size();i++){
				String siteId = tableInfoDao.querySiteIdByDeptId(deptIds.get(i));
				if(nowSiteId.equals(siteId)){
					Department department = tableInfoDao.findDeptByDeptId(deptIds.get(i));
					if(department!=null){
						return department.getDepartmentName();
					}
				}
			}
		}
		return cval;
	}

	@Override
	public Object[] getViewInfoByInstanceId(String instanceId) {
		
		return tableInfoDao.getViewInfoByInstanceId(instanceId);
	}
}
