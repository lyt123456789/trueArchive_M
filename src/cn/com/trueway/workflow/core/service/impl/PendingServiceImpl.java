package cn.com.trueway.workflow.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.ItemDao;
import cn.com.trueway.workflow.core.dao.ItemRelationDao;
import cn.com.trueway.workflow.core.dao.PendingDao;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.dao.TrueJsonDao;
import cn.com.trueway.workflow.core.dao.WorkflowBasicFlowDao;
import cn.com.trueway.workflow.core.pojo.DelayResult;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.Pending1;
import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfItemRelation;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.vo.ChatDofile;
import cn.com.trueway.workflow.core.pojo.vo.Dofile;
import cn.com.trueway.workflow.core.pojo.vo.Todos;
import cn.com.trueway.workflow.core.service.PendingService;
import cn.com.trueway.workflow.core.service.WorkflowCalendarService;

public class PendingServiceImpl implements PendingService {

	private ItemDao 				itemDao;
	
	private PendingDao 				pendingDao;
	
	private TrueJsonDao				trueJsonDao;
	
	private TableInfoDao 			tableInfoDao;

	private ItemRelationDao 		itemRelationDao;

	private WorkflowBasicFlowDao 	workflowBasicFlowDao;
	
	private WorkflowCalendarService calendarService;
	
	public WorkflowBasicFlowDao getWorkflowBasicFlowDao() {
		return workflowBasicFlowDao;
	}

	public void setWorkflowBasicFlowDao(WorkflowBasicFlowDao workflowBasicFlowDao) {
		this.workflowBasicFlowDao = workflowBasicFlowDao;
	}

	public ItemRelationDao getItemRelationDao() {
		return itemRelationDao;
	}

	public void setItemRelationDao(ItemRelationDao itemRelationDao) {
		this.itemRelationDao = itemRelationDao;
	}
	
	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	public WorkflowCalendarService getCalendarService() {
		return calendarService;
	}


	public void setCalendarService(WorkflowCalendarService calendarService) {
		this.calendarService = calendarService;
	}


	public PendingDao getPendingDao() {
		return pendingDao;
	}


	public void setPendingDao(PendingDao pendingDao) {
		this.pendingDao = pendingDao;
	}

	public TrueJsonDao getTrueJsonDao() {
		return trueJsonDao;
	}

	public void setTrueJsonDao(TrueJsonDao trueJsonDao) {
		this.trueJsonDao = trueJsonDao;
	}

	public List<Pending> getPendingList(String conditionSql,String userId, Integer pageIndex, Integer pageSize) {
		List<Pending> pendList = pendingDao.getPendingList(conditionSql,userId, pageIndex, pageSize);

		for(Pending p:pendList){
			String instanceId = p.getWf_instance_uid();
			Integer counts = pendingDao.countProcessListByFId(instanceId);
			if(null != counts && counts>0){
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
			if(StringUtils.isNotBlank(p.getProcess_title())){
				p.setProcess_title(p.getProcess_title().replace("\r\n", "").replace("\r", "").replace("\n", ""));
			}
		}
		return pendList;
	}
	
	public int getCountOfPending(String conditionSql,String userId,String type){
		return pendingDao.getCountOfPending(conditionSql,userId,type);
	}
	
	public int getCountOfPending(String conditionSql){
		return pendingDao.getCountOfPending(conditionSql);
	}

	public WfProcess getProcessByID(String processId) {
		return pendingDao.getProcessByID(processId);
	}


	/**
	 * 计算待办的剩余时间或是否超期
	 * @param 待办列表
	 */
	public void initRemainTime(List<Pending> list) {
		try {
		
			for(Pending pending : list){
				String remainTime = "";		//期限时间，包含单位
				String warnType = "3";		//预警类型 0: 红色  1：黄色  2：蓝色  3：绿色
				Date applyTime = pending.getApply_time();
				Date nowDate = new Date();
				float deadline = Float.parseFloat(pending.getWfn_deadline()==null?"0":pending.getWfn_deadline());		//期限
				String deadlineUnit = pending.getWfn_deadlineunit();//期限单位  0：分钟  1：小时  2：天  3：周  4：月  5：年
				long milSeconds = 0;		//已过毫秒数
//				WfNode node = pendingDao.getWfNode(pending.getWf_node_uid());
//				WfMain wfmain = pendingDao.getWfMainById(pending.getWf_workflow_uid());
				//默认不使用日历时
				milSeconds = nowDate.getTime() - applyTime.getTime();
				
				long remain_milSecs = 0;
				
				long ySeconds = 1000*60*60*24*365;	
				long mSeconds = 1000*60*60*24*30;
				long wSeconds = 1000*60*60*24*7;
				long dSeconds = 1000*60*60*24;
				long hSeconds = 1000*60*60;
				long minSeconds = 1000*60;
				
				if("0".equals(deadlineUnit)){//工作日
					//计算出有无该节点的暂停相关信息
					List<DelayResult> resultlist = itemRelationDao.getDelayResultByInstanceId(pending.getWf_instance_uid());
					int stepIndex = pending.getStepIndex()==null?0:Integer.parseInt(pending.getStepIndex());
					String item_id = pending.getItem_id();
					int delayDays = 0 ;
					WfItemRelation wfItemRelation = itemRelationDao.getItemRelationByItemId(item_id);
					int unit = 0 ;
					if(wfItemRelation!=null){
						unit = wfItemRelation.getDelay_date();
					}
					for(DelayResult delayResult:resultlist){
						Integer isallowed = delayResult.getIs_allowed();
						if(isallowed ==null || isallowed==0){
							continue;
						}
						if(stepIndex == delayResult.getStepindex()){
							delayDays += unit ;
						}
					}
					Date endDate = pendingDao.getEndDate((int)deadline +delayDays, applyTime);
					remain_milSecs = endDate.getTime() - nowDate.getTime();
					if(remain_milSecs>=0){
						long num =  (long) Math.ceil((double)remain_milSecs/(double)dSeconds);
						remainTime = num+"天";
					}
					
				}else if("1".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*hSeconds-milSeconds);
					long num = (long) Math.ceil((double)remain_milSecs/(double)hSeconds);
					remainTime = Math.abs(num)+"小时";
				}else if("2".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*dSeconds-milSeconds);
					long num = (long) Math.ceil((double)remain_milSecs/(double)dSeconds);
					remainTime = Math.abs(num)+"天";
				}else if("3".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*wSeconds-milSeconds);
					long w = (remain_milSecs/wSeconds);
					if(w>0){
						remainTime += Math.abs(w)+"周";
					}else{
						long d = (long) Math.ceil((double)(remain_milSecs-w*wSeconds)/(double)dSeconds);
						if(d!=0) remainTime += Math.abs(d)+"天";
					}
				}else if("4".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*mSeconds-milSeconds);
					long m = (remain_milSecs/mSeconds);
					if(m!=0){
						remainTime += Math.abs(m)+"月";
					}else{
						long d = (long) Math.ceil((double)(remain_milSecs-m*mSeconds)/(double)dSeconds);
						if(d!=0) remainTime += Math.abs(d)+"天";
					}
				}else if("5".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*ySeconds-milSeconds);
					long y = (remain_milSecs/ySeconds);
					if(y!=0) {
						remainTime += Math.abs(y)+"年";
					}
					long m = ((remain_milSecs-y*ySeconds)/mSeconds);
					if(m!=0){
						remainTime += Math.abs(m)+"月";
					}else{
						long d = (long) Math.ceil((double)(remain_milSecs-m*mSeconds)/(double)dSeconds);
						if(d!=0) remainTime += Math.abs(d)+"天";
					}
				}
				if(remain_milSecs <= 0){
					warnType = "0";
				}else if(remain_milSecs <= dSeconds){
					warnType = "1";
				}else if(remain_milSecs <= dSeconds*3){
					warnType = "2";
				}else {
					warnType = "3";
				}
				if(remain_milSecs < 0){
					remainTime = "已超期 ";
				}
				pending.setWarnType(warnType);
				pending.setRemainTime(remainTime);
				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("数字转换错误，请检查节点期限值是否是数字");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 计算待办的剩余时间或是否超期
	 * @param 待办列表
	 */
	public void initRemainTime1(List<Pending1> list) {
		try {
		
			for(Pending1 pending : list){
				String remainTime = "";		//期限时间，包含单位
				String warnType = "3";		//预警类型 0: 红色  1：黄色  2：蓝色  3：绿色
				Date applyTime = pending.getApply_time();
				Date nowDate = new Date();
				float deadline = Float.parseFloat(pending.getWfn_deadline()==null?"0":pending.getWfn_deadline());		//期限
				String deadlineUnit = pending.getWfn_deadlineunit();//期限单位  0：分钟  1：小时  2：天  3：周  4：月  5：年
				long milSeconds = 0;		//已过毫秒数
//				WfNode node = pendingDao.getWfNode(pending.getWf_node_uid());
//				WfMain wfmain = pendingDao.getWfMainById(pending.getWf_workflow_uid());
				//默认不使用日历时
				milSeconds = nowDate.getTime() - applyTime.getTime();
				
				long remain_milSecs = 0;
				
				long ySeconds = 1000*60*60*24*365;	
				long mSeconds = 1000*60*60*24*30;
				long wSeconds = 1000*60*60*24*7;
				long dSeconds = 1000*60*60*24;
				long hSeconds = 1000*60*60;
				long minSeconds = 1000*60;
				
				if("0".equals(deadlineUnit)){//工作日
					//计算出有无该节点的暂停相关信息
					List<DelayResult> resultlist = itemRelationDao.getDelayResultByInstanceId(pending.getWf_instance_uid());
					int stepIndex = pending.getStepIndex()==null?0:Integer.parseInt(pending.getStepIndex());
					String item_id = pending.getItem_id();
					int delayDays = 0 ;
					WfItemRelation wfItemRelation = itemRelationDao.getItemRelationByItemId(item_id);
					int unit = 0 ;
					if(wfItemRelation!=null){
						unit = wfItemRelation.getDelay_date();
					}
					for(DelayResult delayResult:resultlist){
						Integer isallowed = delayResult.getIs_allowed();
						if(isallowed ==null || isallowed==0){
							continue;
						}
						if(stepIndex == delayResult.getStepindex()){
							delayDays += unit ;
						}
					}
					Date endDate = pendingDao.getEndDate((int)deadline +delayDays, applyTime);
					remain_milSecs = endDate.getTime() - nowDate.getTime();
					if(remain_milSecs>=0){
						long num =  (long) Math.ceil((double)remain_milSecs/(double)dSeconds);
						remainTime = num+"天";
					}
					
				}else if("1".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*hSeconds-milSeconds);
					long num = (long) Math.ceil((double)remain_milSecs/(double)hSeconds);
					remainTime = Math.abs(num)+"小时";
				}else if("2".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*dSeconds-milSeconds);
					long num = (long) Math.ceil((double)remain_milSecs/(double)dSeconds);
					remainTime = Math.abs(num)+"天";
				}else if("3".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*wSeconds-milSeconds);
					long w = (remain_milSecs/wSeconds);
					if(w>0){
						remainTime += Math.abs(w)+"周";
					}else{
						long d = (long) Math.ceil((double)(remain_milSecs-w*wSeconds)/(double)dSeconds);
						if(d!=0) remainTime += Math.abs(d)+"天";
					}
				}else if("4".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*mSeconds-milSeconds);
					long m = (remain_milSecs/mSeconds);
					if(m!=0){
						remainTime += Math.abs(m)+"月";
					}else{
						long d = (long) Math.ceil((double)(remain_milSecs-m*mSeconds)/(double)dSeconds);
						if(d!=0) remainTime += Math.abs(d)+"天";
					}
				}else if("5".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*ySeconds-milSeconds);
					long y = (remain_milSecs/ySeconds);
					if(y!=0) {
						remainTime += Math.abs(y)+"年";
					}
					long m = ((remain_milSecs-y*ySeconds)/mSeconds);
					if(m!=0){
						remainTime += Math.abs(m)+"月";
					}else{
						long d = (long) Math.ceil((double)(remain_milSecs-m*mSeconds)/(double)dSeconds);
						if(d!=0) remainTime += Math.abs(d)+"天";
					}
				}
				if(remain_milSecs <= 0){
					warnType = "0";
				}else if(remain_milSecs <= dSeconds){
					warnType = "1";
				}else if(remain_milSecs <= dSeconds*3){
					warnType = "2";
				}else {
					warnType = "3";
				}
				if(remain_milSecs < 0){
					remainTime = "已超期 ";
				}
				pending.setWarnType(warnType);
				pending.setRemainTime(remainTime);
				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("数字转换错误，请检查节点期限值是否是数字");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 已办列表是否已超期
	 * @param list  已办列表
	 */
	public void initIsOvertime(List<Pending> list) {
		try {
			for(Pending pending : list){
				String remainTime = "";		//期限时间，包含单位
				Date applyTime = pending.getApply_time();
				Date nowDate =  new Date();
				if(null!=pending.getFinish_time())
				{
					nowDate = pending.getFinish_time();
				} 
				float deadline = Float.parseFloat(pending.getWfn_deadline()==null?"0":pending.getWfn_deadline());		//期限
				String deadlineUnit = pending.getWfn_deadlineunit();//期限单位  0：分钟  1：小时  2：天  3：周  4：月  5：年
				long milSeconds = 0;		//已过毫秒数
//				WfNode node = pendingDao.getWfNode(pending.getWf_node_uid());
				WfMain wfmain = pendingDao.getWfMainById(pending.getWf_workflow_uid());
				String calendar = wfmain.getWfm_calendar(); 
				//默认不使用日历时
				milSeconds = nowDate.getTime() - applyTime.getTime();
				long remain_milSecs = 0;
				long ySeconds = 1000*60*60*24*365;	
				long mSeconds = 1000*60*60*24*30;
				long wSeconds = 1000*60*60*24*7;
				long dSeconds = 1000*60*60*24;
				long hSeconds = 1000*60*60;
				long minSeconds = 1000*60;
				
				if("0".equals(deadlineUnit)){//工作日
					//calendarId 日历年份
					if(calendar != null && !"".equals(calendar)){
						Date endDate = pendingDao.getEndDate((int)deadline, applyTime);
						remain_milSecs = endDate.getTime() - nowDate.getTime();
					}else{
						remain_milSecs = (long) (deadline*dSeconds-milSeconds);
					}
				}else if("1".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*hSeconds-milSeconds);
				}else if("2".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*dSeconds-milSeconds);
				}else if("3".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*wSeconds-milSeconds);
				}else if("4".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*mSeconds-milSeconds);
				}else if("5".equals(deadlineUnit)){
					remain_milSecs = (long) (deadline*ySeconds-milSeconds);
				}
				if(remain_milSecs < 0){
					remainTime = "超期完成";
				}
				pending.setRemainTime(remainTime);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("数字转换错误，请检查节点期限值是否是数字");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<String> getAllYear(){
		return pendingDao.getAllYear();
	}

	/**
	 * 移动端得到待办列表
	 */
	public String getPendListOfMobile(String userId, int count, Integer column, Integer pagesize,String type) {
		List<Pending> pendList = pendingDao.getPendListOfMobile(userId,count,column,pagesize,type);
		List<Todos> todoList = new ArrayList<Todos>();
		if(pendList.size()!=0 && pendList!=null && !("").equals(pendList)){
			for (Pending pending : pendList) {
				Todos todo = new Todos();
					todo.setTitle(pending.getProcess_title());
					todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getApply_time()));
					todo.setProcessId(pending.getWf_process_uid());
					todo.setInstanceId(pending.getWf_instance_uid());
					todo.setUserId(userId);
					todo.setFormId(pending.getForm_id());
					todo.setNodeId(pending.getWf_node_uid());
					todo.setWorkFlowId(pending.getWf_workflow_uid());
					todo.setItemId(pending.getItem_id());
					todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅
						String path = pending.getPdfPath();
						if(!("").equals(path) && path != null){
							path = path.substring(path.lastIndexOf("/")+1,path.length());
						}
					todo.setPdfPath(path); 
//					todo.setCommentJson(pending.getCommentJson());
				todoList.add(todo);
			}
		}
		return JSONArray.fromObject(todoList).toString();
	}

	/**
	 * web端得到待办列表
	 */
	public StringBuilder getTodoForPortal(String userId, String column, String pagesize, String url, String callback,String itemIds) {
		if(column==null||column.trim().length()==0) column = "1";
		if(pagesize==null||pagesize.trim().length()==0) pagesize = "5";
		List<Pending> pendList = pendingDao.getPendListOfMobile(userId,0,Integer.parseInt(column),Integer.parseInt(pagesize),"",itemIds,"","");
		StringBuilder sb =new StringBuilder();
		sb.append(";"+callback+"([");
		for(Pending n : pendList){
			sb.append("{");
			sb.append("title:\""+(n.getProcess_title().indexOf(",")==-1?n.getProcess_title():n.getProcess_title().split(",")[0])+"\",");
			sb.append("link:\""+url+"/table_openPendingForm.do?processId="+n.getWf_process_uid()+"&isDb=true&itemId="+n.getItem_id()+"&formId="+n.getForm_id()+"&isPortal=1\",");
//			sb.append("link:\""+url+"/table_getPendingList.do?itemid="+itemIds+"\",");
			sb.append("date:\""+ new SimpleDateFormat("yyyy-MM-dd").format(n.getApply_time())+"\"");
			sb.append("},");
		}
		if(sb!=null&&sb.lastIndexOf(",")>-1) sb = new StringBuilder(sb.substring(0, sb.length()-1));
		sb.append("])");
		return sb;
	} 
	
	/**
	 * web端得到待办列表(新) 2014-1-13
	 */
	public String getTodoForPortalNew(String userId, String column, String pagesize, String url, String callback,String itemIds) {
		if(column==null||column.trim().length()==0) column = "1";
		if(pagesize==null||pagesize.trim().length()==0) pagesize = "5";
		List<Pending> pendList = pendingDao.getPendListOfMobile(userId,0,Integer.parseInt(column),Integer.parseInt(pagesize),"",itemIds,"","");
//		StringBuilder sb =new StringBuilder();
		String callBack = null;
		 String outString =";"+callBack+"({";
	    	outString+="data:[],";
			outString+="css:'',";
			outString+="js:";
			outString+="''";
			outString+=",template:";
			
			if(pendList!=null&&pendList.size()>0){
			outString+="'<div style=\"background:url(widgets/images/daiban.png) no-repeat 0 0;padding:5px;overflow:hidden;*zoom:1;\">";
			if(pendList.size()>99){
				outString+="<div ><span style=\"width:25px;height:25px;line-height:25px;background:url(widgets/images/quan.png) no-repeat;color:#ffffff;display:block;text-align:center;float:right;\">99+</span></div>";
			}else{
			   outString+="<div ><span style=\"width:25px;height:25px;line-height:25px;background:url(widgets/images/quan.png) no-repeat;color:#ffffff;display:block;text-align:center;float:right;\">"+pendList.size()+"</span></div>";
			}
			//outString+="'<div >"+remindList.size()+"</div>";
			outString+="<div style=\"padding-top:20px\"><ul class=\"ul1\">";
			if(pendList!=null && pendList.size()>0){
				SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
				for(int i = 0;i<pendList.size();i++){
	        		if(i>=(Integer.valueOf(pagesize)-1)){
	        			break;
	        		}
	        	Pending pend = pendList.get(i);

	    		//http://localhost:8080/functions/day_toDay.do?year=2013&month=8&day=14&state1=0&state2=0&is_update=0
	    		String link = url+"/table_openPendingNoCloseForm.do?processId="+pend.getWf_process_uid()+"&isDb=true&itemId="+pend.getItem_id()+"&formId="+pend.getForm_id()+"&isPortal=1&isCanPush=1";
	    		String time = new SimpleDateFormat("yyyy-MM-dd").format(pend.getApply_time());
			   outString+="<li><span style=\"float:right;\" class=\"timer\">["+time+"]</span><a style=\"display:block;float:left;width:70%;overflow:hidden;text-overflow:ellipsis;\" href=\""+link+"\">"+(pend.getProcess_title().indexOf(",")==-1?pend.getProcess_title():pend.getProcess_title().split(",")[0])+"</a></li>";
			   
				}
			}
			outString+="</ul></div></div>'";
			}else{
				
				outString+="'<div style=\"padding-top:20px;text-align:center\"><img src=\"widgets/images/zwsj.png\"></div>'";	
			}
			outString+="})";
		return outString;
	}


	public List<String> getTypeListOfPending(String userId) {
		return pendingDao.getTypeListOfPending(userId);
	}
	
	@Override
	public String getPendListOfMobile(String userId, int count, int column,
			Integer pagesize, String type, String itemIds,String title,String serverUrl, String isReadFile) {
		List<Pending> pendList = pendingDao.getPendListOfMobile(userId,count,column,pagesize,type,itemIds,title,isReadFile);
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		List<Todos> todoList = new ArrayList<Todos>();
		if(pendList.size()!=0 && pendList!=null && !("").equals(pendList)){
			for (Pending pending : pendList) {
				Todos todo = new Todos();
				if(StringUtils.isNotBlank(pending.getProcess_title())){
					todo.setTitle(pending.getProcess_title().replace("\r\n", "").replace("\n", "").replace("\r", ""));
				}                                                                                                      	
				todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getApply_time()));                                                              	
				todo.setProcessId(pending.getWf_process_uid());                                                                                                 	
				todo.setInstanceId(pending.getWf_instance_uid());                                                                                               	
				todo.setUserId(userId);                                                                                                                         	
				todo.setFormId(pending.getForm_id());                                                                                                           	
				todo.setNodeId(pending.getWf_node_uid());                                                                                                       	
				todo.setWorkFlowId(pending.getWf_workflow_uid());                                                                                               	
				todo.setItemId(pending.getItem_id());                                                                                                           	
				todo.setItemName(pending.getItem_name());                                                                                                       	
				todo.setOwner(pending.getOwner());                                                                                                              	
				todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅                                                                                      	
				// 修改 isMaster 值 routerType =3
				WfNode wfnode = workflowBasicFlowDao.getWfNode(pending.getWf_node_uid());	//获取当前node                                                          	
				String route_type = "";                                                                                                                         	
				String self_loop = "";
				
				if(wfnode!=null){
					route_type = wfnode.getWfn_route_type(); 
					self_loop = wfnode.getWfn_self_loop();
				}
				if(route_type!=null 
						&& (route_type.equals("3") || route_type.equals("4"))){	
					if(CommonUtil.stringNotNULL(self_loop)
							&& "1".equals(self_loop)){//自循环
						//并行结合
						todo.setIsMaster("1");  // 展示节点
					}else{
						//并行结合
						todo.setIsMaster("0");  // 点击完成
					}
					
				}else{
					todo.setIsMaster(pending.getIsMaster());
				}
				/*TrueJson trueJson = trueJsonDao.findTrueJsonForDofile(pending.getWf_instance_uid());
				if(trueJson!=null){
					todo.setCommentJson(trueJson.getTrueJson());
				}else{*/
					todo.setCommentJson(pending.getCommentJson() == null ?null:pending.getCommentJson().trim());
				/*}*/
				if(StringUtils.isNotBlank(pending.getAllInstanceid())){
					todo.setAllInstanceId(pending.getAllInstanceid());
				}else{
					todo.setAllInstanceId(pending.getWf_instance_uid());
				}
				Date date = pending.getJssj();
				if(null != date){
					todo.setIsRead("1");
				}else{
					todo.setIsRead("0");
				}
				
				if(StringUtils.isNotBlank(isReadFile) && isReadFile.equals("1")){
					
				}
				
				todoList.add(todo);
			}
		}
		return JSONArray.fromObject(todoList).toString();
	}
	
	@Override
	public String getPendListOfMobileForDB(String userId, int count, int column,
			Integer pagesize, String type, String itemIds,String title,String serverUrl, String isReadFile) {
		List<Pending> pendList = pendingDao.getPendListOfMobile(userId,count,column,pagesize,type,itemIds,title,isReadFile);
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		List<Todos> todoList = new ArrayList<Todos>();
		if(pendList.size()!=0 && pendList!=null && !("").equals(pendList)){
			for (Pending pending : pendList) {
				Todos todo = new Todos();
				if(StringUtils.isNotBlank(pending.getProcess_title())){
					todo.setTitle(pending.getProcess_title().replace("\r\n", "").replace("\n", "").replace("\r", ""));
				}                                                                                                      	
				todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getApply_time()));                                                              	
				todo.setProcessId(pending.getWf_process_uid());                                                                                                 	
				todo.setInstanceId(pending.getWf_instance_uid());                                                                                               	
				todo.setUserId(userId);                                                                                                                         	
				todo.setFormId(pending.getForm_id());                                                                                                           	
				todo.setNodeId(pending.getWf_node_uid());                                                                                                       	
				todo.setWorkFlowId(pending.getWf_workflow_uid());                                                                                               	
				todo.setItemId(pending.getItem_id());                                                                                                           	
				todo.setItemName(pending.getItem_name());                                                                                                       	
				todo.setOwner(pending.getOwner());                                                                                                              	
				todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅                                                                                      	
				// 修改 isMaster 值 routerType =3                                                                                                                  	
				WfNode wfnode = workflowBasicFlowDao.getWfNode(pending.getWf_node_uid());	//获取当前node                                                          	
				String route_type = "";                                                                                                                         	
				String self_loop = "";
				
				if(wfnode!=null){
					route_type = wfnode.getWfn_route_type(); 
					self_loop = wfnode.getWfn_self_loop();
				}
				if(route_type!=null 
						&& (route_type.equals("3") || route_type.equals("4"))){	
					if(CommonUtil.stringNotNULL(self_loop)
							&& "1".equals(self_loop)){//自循环
						//并行结合
						todo.setIsMaster("1");  // 展示节点
					}else{
						//并行结合
						todo.setIsMaster("0");  // 点击完成
					}
					
				}else{
					todo.setIsMaster(pending.getIsMaster());
				}
				/*TrueJson trueJson = trueJsonDao.findTrueJsonForDofile(pending.getWf_instance_uid());
				if(trueJson!=null){
					todo.setCommentJson(trueJson.getTrueJson());
				}else{
					todo.setCommentJson(pending.getCommentJson() == null ?null:pending.getCommentJson().trim());
				}*/
				if(StringUtils.isNotBlank(pending.getAllInstanceid())){
					todo.setAllInstanceId(pending.getAllInstanceid());
				}else{
					todo.setAllInstanceId(pending.getWf_instance_uid());
				}
				Date date = pending.getJssj();
				if(null != date){
					todo.setIsRead("1");
				}else{
					todo.setIsRead("0");
				}
				todo.setUrgency(pending.getUrgency());
				if(StringUtils.isNotBlank(isReadFile) && isReadFile.equals("1")){
					
				}
				
				todoList.add(todo);
			}
		}
		return JSONArray.fromObject(todoList).toString();
	}
	
	@Override
	public String getOverListOfMobile(String userId, List<Pending> pendList, int column,
			Integer pagesize, String type, String itemIds,String serverUrl) {
		 String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");

		List<Todos> todoList = new ArrayList<Todos>();
		if(pendList.size()!=0 && pendList!=null && !("").equals(pendList)){
			for (Pending pending : pendList) {
				Todos todo = new Todos();
					todo.setTitle(pending.getProcess_title());
					todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getFinish_time()));
					todo.setProcessId(pending.getWf_process_uid());
					todo.setInstanceId(pending.getWf_instance_uid());
					todo.setUserId(userId);
					todo.setFormId(pending.getForm_id());
					todo.setNodeId(pending.getWf_node_uid());
					todo.setWorkFlowId(pending.getWf_workflow_uid());
					todo.setItemId(pending.getItem_id());
					todo.setItemName(pending.getItem_name());
					todo.setOwner(pending.getOwner());
					todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅
					todo.setIsMaster(pending.getIsMaster());
					todo.setIsfavourite(pending.getFavourite());
					todo.setStayuserId(pending.getEntrust_name());
					todo.setNowProcessId(pending.getNowProcessId());
					if(pending.getIsBack()!=null&&pending.getIsBack().equals("5")){//isBack=5，表示并行传阅可收回
						todo.setIsBack("3");//设置移动端也可收回
					}else{
						todo.setIsBack(pending.getIsBack());
					}
					todo.setStatus(pending.getStatus());
					todo.setStepIndex(pending.getStepIndex());
					todo.setUrgency(pending.getUrgency());
				todoList.add(todo);
			}
		}
		return JSONArray.fromObject(todoList).toString();
	}
	
	@Override
	public String getAllPendListOfMobile(String userId, int count, int column,
			Integer pagesize, String type, String itemIds1,String itemIds2,String itemIds3) {
		List<Pending> pendList1 = pendingDao.getPendListOfMobile(userId,count,column,pagesize,type,itemIds1,"","");
		List<Pending> pendList2 = pendingDao.getPendListOfMobile(userId,count,column,pagesize,type,itemIds2,"","");
		List<Pending> pendList3 = pendingDao.getPendListOfMobile(userId,count,column,pagesize,type,itemIds3,"","");
		List<Todos> todoList = new ArrayList<Todos>();
		//条数算法
		int[] listSizeArray = numArithmetics(pendList1.size(),pendList2.size(),pendList3.size(),pagesize);
		//类型0数据
		for(int i=0;i<listSizeArray[0];i++){
			Todos todo = new Todos();
			Pending pending = pendList1.get(i);
			todo.setTitle(pending.getProcess_title());
			todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getApply_time()));
			todo.setProcessId(pending.getWf_process_uid());
			todo.setInstanceId(pending.getWf_instance_uid());
			todo.setUserId(userId);
			todo.setFormId(pending.getForm_id());
			todo.setNodeId(pending.getWf_node_uid());
			todo.setWorkFlowId(pending.getWf_workflow_uid());
			todo.setItemId(pending.getItem_id());
			todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅
				String path = pending.getPdfPath();
				if(!("").equals(path) && path != null){
					path = path.substring(path.lastIndexOf("/")+1,path.length());
				}
			todo.setPdfPath(path); 
//				todo.setCommentJson(pending.getCommentJson());
			todoList.add(todo);
		}
		//类型1数据
		for(int i=0;i<listSizeArray[1];i++){
			Todos todo = new Todos();
			Pending pending = pendList2.get(i);
			todo.setTitle(pending.getProcess_title());
			todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getApply_time()));
			todo.setProcessId(pending.getWf_process_uid());
			todo.setInstanceId(pending.getWf_instance_uid());
			todo.setUserId(userId);
			todo.setFormId(pending.getForm_id());
			todo.setNodeId(pending.getWf_node_uid());
			todo.setWorkFlowId(pending.getWf_workflow_uid());
			todo.setItemId(pending.getItem_id());
			todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅
				String path = pending.getPdfPath();
				if(!("").equals(path) && path != null){
					path = path.substring(path.lastIndexOf("/")+1,path.length());
				}
			todo.setPdfPath(path); 
//				todo.setCommentJson(pending.getCommentJson());
			todoList.add(todo);
		}
		//类型2数据
		for(int i=0;i<listSizeArray[2];i++){
			Todos todo = new Todos();
			Pending pending = pendList3.get(i);
			todo.setTitle(pending.getProcess_title());
			todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getApply_time()));
			todo.setProcessId(pending.getWf_process_uid());
			todo.setInstanceId(pending.getWf_instance_uid());
			todo.setUserId(userId);
			todo.setFormId(pending.getForm_id());
			todo.setNodeId(pending.getWf_node_uid());
			todo.setWorkFlowId(pending.getWf_workflow_uid());
			todo.setItemId(pending.getItem_id());
			todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅
				String path = pending.getPdfPath();
				if(!("").equals(path) && path != null){
					path = path.substring(path.lastIndexOf("/")+1,path.length());
				}
			todo.setPdfPath(path); 
//				todo.setCommentJson(pending.getCommentJson());
			todoList.add(todo);
		}
		return JSONArray.fromObject(todoList).toString();
	}
	
	/*
	 * 计算待办数据各类型应该输出数据条数 算法
	 */
	private int[] numArithmetics(int num1,int num2,int num3,int total){
		int[] nums = new int[3];
		int ave = total/3;
		if(num1 <= ave){
			nums[0] = num1;
			int ave2 = (total-num1)/2;
			if(num2 <= ave2){
				nums[1] = num2;
				int last = total-num1-num2;
				nums[2] = (last>num3)?num3:last;
			}else if(num3 <= ave2){
				nums[2] = num3;
				int last = total-num1-num3;
				nums[1] = (last>num2)?num2:last;
			}else{
				nums[1] = ave2;
				int last = total-num1-ave2;
				nums[2] = (last>num3)?num3:last;
			}
		}else if(num2 <= ave){
			nums[1] = num2;
			int ave2 = (total-num2)/2;
			if(num1 <= ave2){
				nums[0] = num1;
				int last = total-num1-num2;
				nums[2] = (last>num3)?num3:last;
			}else if(num3 <= ave2){
				nums[2] = num3;
				int last = total-num2-num3;
				nums[0] = (last>num1)?num1:last;
			}else{
				nums[0] = ave2;
				int last = total-num2-ave2;
				nums[2] = (last>num3)?num3:last;
			}
		}
		else if(num3 <= ave){
			nums[2] = num3;
			int ave2 = (total-num3)/2;
			if(num1 <= ave2){
				nums[0] = num1;
				int last = total-num1-num3;
				nums[1] = (last>num2)?num2:last;
			}else if(num2 <= ave2){
				nums[1] = num2;
				int last = total-num2-num3;
				nums[0] = (last>num1)?num1:last;
			}else{
				nums[0] = ave2;
				int last = total-num3-ave2;
				nums[1] = (last>num2)?num2:last;
			}
		}else{
			nums[0] = ave;
			nums[1] = ave;
			nums[2] = total - ave*2;
		}
		return nums;
	}
	
	@Override
	public void initDelayItem1(List<Pending1> list) {
		for(Pending1 pending:list){
			String item_id = pending.getItem_id();
			String instanceId = pending.getWf_instance_uid();
			WfItemRelation wfItemRelation = itemRelationDao.getItemRelationByItemId(item_id);
			if(wfItemRelation==null){
				pending.setDelay_itemid("");
				pending.setIsDelaying(0);
				continue;
			}
			String delay_item_id = wfItemRelation.getDelay_item_id();
			pending.setDelay_itemid(delay_item_id);
			
			WfItem wfItem = itemDao.getItemById(delay_item_id);
			if(wfItem==null){
				continue;
			}
			pending.setDelay_lcid(wfItem.getLcid());
			List<DelayResult> delayResultList = itemRelationDao.getDelayResultByInstanceId(instanceId);
			int isDealying = 0;
			for(DelayResult delayResult : delayResultList){
				int status = delayResult.getStatus();
				if(status==0){
					isDealying = 1 ;
					break;
				}
				if(status ==1){
					isDealying = 2;
				}
			}
			pending.setIsDelaying(isDealying);
		}
	}

	@Override
	public void initDelayItem(List<Pending> list) {
		for(Pending pending:list){
			String item_id = pending.getItem_id();
			String nodename = pending.getNodeName(); 
			if(nodename==null || nodename.equals("")){
				String nodeId =  pending.getWf_node_uid();
				WfNode node = tableInfoDao.getWfNodeById(nodeId);
				if(node!=null){
					pending.setNodeName(node.getWfn_name());
				}
			}
			String instanceId = pending.getAllInstanceid();
			WfItemRelation wfItemRelation = itemRelationDao.getItemRelationByItemId(item_id);
			if(wfItemRelation==null){
				pending.setDelay_itemid("");
				pending.setIsDelaying(0);
				continue;
			}
			String delay_item_id = wfItemRelation.getDelay_item_id();
			pending.setDelay_itemid(delay_item_id);
			
			WfItem wfItem = itemDao.getItemById(delay_item_id);
			if(wfItem==null){
				continue;
			}
			pending.setDelay_lcid(wfItem.getLcid());
			List<DelayResult> delayResultList = itemRelationDao.getDelayResultByInstanceId(instanceId);
			int isDealying = 0;
			for(DelayResult delayResult : delayResultList){
				int status = delayResult.getStatus();
				if(status==0){
					isDealying = 1 ;
					break;
				}
				if(status ==1){
					isDealying = 2;
				}
			}
			pending.setIsDelaying(isDealying);
		}
	}

	public void setBackStatus(List<Pending> list, String isAdmin) {
		pendingDao.setBackStatus(list,isAdmin);
		
	}


	public boolean stepIsOver(String instanceId, String stepIndex) {
		return pendingDao.stepIsOver(instanceId,stepIndex);
	}


	@Override
	public void recycleTask(String instanceId, String stepIndex) {
		pendingDao.recycleTask(instanceId,stepIndex);
		
	}

	@Override
	public WfProcess getProcessByInstanceIds(String instanceIds,String userId) {
		return pendingDao.getProcessByInstanceIds(instanceIds, userId);
	}

	@Override
	public Date getEndDate(int deadline, Date date, String wfm_calendar) {
		return pendingDao.getEndDate(deadline, date, wfm_calendar);
	}

	@Override
	public List<WfProcess> findProcessListByFId(String f_instanceId) {
		return pendingDao.findProcessListByFId(f_instanceId);
	}
	
	@Override
	public List<WfProcess> getProcessByInstanceId(String instanceId) {
		return pendingDao.getProcessByInstanceId(instanceId);
	}

	@Override
	public List<WfProcess> findProcessListByFIdAndDoType(String getfInstancdUid,String doFile) {
		return pendingDao.findProcessListByFIdAndDoType(getfInstancdUid,doFile);
	}

	@Override
	public WfProcess checkInstanceIsOver(WfProcess wfProcess) {
		return pendingDao.checkInstanceIsOver(wfProcess);
	}

	@Override
	public  List<WfProcess> findProcessListById(String insertid) {
		return pendingDao.findProcessListById(insertid);
	}


	@Override
	public String getAllUserIdByFInstanceId(String getfInstancdUid, String mc) {
		List<Object> list = pendingDao.getAllUserIdByFInstanceId(getfInstancdUid,mc);
		String userids=null;
		if(list!=null&&list.size()>0){
			userids="";
			for(int i=0;i<list.size();i++){
				if(i!=0){
					userids +=",";
				}
				userids +=(String)list.get(i);
			}
		}
		return userids;
	}

	@Override
	public String getZUserIdByFInstanceId(String fInstancdUid, String mc) {
		//根据父id获取process
		List<WfProcess> list = pendingDao.getProcessByInstanceId(fInstancdUid);
		String userid="";
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				WfProcess wfProcess = list.get(i);
				if(wfProcess.getDoType()!=null&&wfProcess.getDoType()==3){
					userid=wfProcess.getUserUid();
					break;
				}
			}
			if("".equals(userid)){
				for(int i=0;i<list.size();i++){
					WfProcess wfProcess = list.get(i);
					if(wfProcess.getAction_status()!=null&&wfProcess.getAction_status()==2){
						userid=wfProcess.getUserUid();
						break;
					}
				}
			}
		}
		return userid;
	}

	@Override
	public List<WfProcess> findProcessListByFInstanceid(String wfInstanceUid) {
		//获取子流程
		List<WfProcess> list = pendingDao.findProcessListByFIdAndDoFile(wfInstanceUid,"3");
		if(list!=null&&list.size()>0){
			List<WfProcess> newList = new ArrayList<WfProcess>();
			for(int i=0;i<list.size();i++){
				newList.addAll(pendingDao.findProcessListByFIdAndDoFile(list.get(i).getWfInstanceUid(),"1"));
			}
			return newList;
		}else{
			return pendingDao.findProcessListByFIdAndDoFile(wfInstanceUid,"1");
		}
	}

	@Override
	public String getfinstancdidByInstancdid(String instancdid) {
		List<WfProcess> WfProcess = pendingDao.findProcessListByIdAndDoFile(instancdid, "3");
		if(WfProcess!=null&&WfProcess.size()>0){
			return WfProcess.get(0).getfInstancdUid()==null?WfProcess.get(0).getAllInstanceid():WfProcess.get(0).getfInstancdUid();
		}else{
			return instancdid;
		}
	}
	
	@Override
	public String getUserIdByInstancdid(String instancdid, String deptid) {
		List<Object> list = pendingDao.getAllUserIdByFInstanceId(instancdid,null);
		String userids=null;
		if(list!=null&&list.size()>0){
			userids="";
			for(int i=0;i<list.size();i++){
				userids =(String)list.get(i);
				Employee em = tableInfoDao.findEmpByUserId(userids);
				if(em!=null){
					Department oldDepartment = tableInfoDao.findDeptByDeptId(deptid);
					Department newDepartment1 = tableInfoDao.findDeptByDeptId(em.getDepartmentGuid());
					if(newDepartment1.getDepartmentShortdn().indexOf(oldDepartment.getDepartmentShortdn())!=-1){
						return em.getEmployeeGuid();
					}
				}else{
					if(userids.equals(deptid)){
						return deptid;
					}
				}
			}
		}
		return deptid;
	}

	/**
	 * 根据实例id获取公文id
	 */
	@Override
	public  DoFileReceive getDoFileReceiveByInstanceId(String instanceId) {
		List<DoFileReceive> list = pendingDao.getDoFileReceiveByInstanceId(instanceId);
		if(list!=null&&list.size()>0){
			return  list.get(0);
		}
		return null;
	}

	@Override
	public String getDeparNameByDepartId(String toDepartId) {
		List<Object> list = pendingDao.getDeparNameByDepartId(toDepartId);
		if(list!=null&&list.size()>0){
			return  (String) list.get(0);
		}
		return null;
	}

	@Override
	public WfProcess getEndProcess(int step, String nodeUid,String instanceId) {
		List<Object[]> objs = pendingDao.getEndProcess( step, nodeUid,instanceId);
		if(objs != null && objs.size() > 0){
			Object[] obj = objs.get(0);
			WfProcess wfProcess = new WfProcess();
			wfProcess.setWfProcessUid(obj[0].toString());
			wfProcess.setPdfPath(obj[1].toString());
			return wfProcess;
		}else{
			return null;
		}
		
	}
	/**
	 * 移动端得到待办列表
	 */
	public String getPendListOfMobile(String deptId,String userId, int count, String type,String serverUrl) {
		List<Pending> pendList = pendingDao.getPendListOfMobile(userId,count,type);
		List<ChatDofile> chatList = new ArrayList<ChatDofile>();

		String itemId = "";
		if(pendList.size()!=0 && pendList!=null && !("").equals(pendList)){
			itemId = pendList.get(0).getItem_id();
			int itemCount = 0;
			String itemName = "";
			ChatDofile chatDofile  = new ChatDofile();
			List<Dofile> todoList = new ArrayList<Dofile>();
			WfItem item = itemDao.getItemById(itemId);
			for (Pending pending : pendList) {
				// 获取 当前事项的id
				String tempItemId = pending.getItem_id();
				//item = itemDao.getItemById(tempItemId);
				if(!tempItemId.equals(itemId)){
					item = itemDao.getItemById(tempItemId);
					chatDofile.setCount(itemCount+"");
					chatDofile.setList(todoList);
					chatDofile.setItemName(itemName);
					// 获取消息类型
					switch(Integer.valueOf(item.getVc_xxlx()== null ? "2":item.getVc_xxlx())){
					case 2:
						chatDofile.setXxlx("公文管理");
						break;
					case 4:
						chatDofile.setXxlx("工作任务");
						break;
					case 5:
						chatDofile.setXxlx("工作计划");
						break;
					case 8:
						chatDofile.setXxlx("工作动态");
						break;
					case 11:
						chatDofile.setXxlx("依申请公开");
						break;
					case 17:
						chatDofile.setXxlx("工作日志");
						break;
					case 19:
						chatDofile.setXxlx("车辆管理");
						break;
					case 20:
						chatDofile.setXxlx("会议管理");
						break;
					case 21:
						chatDofile.setXxlx("资产管理");
						break;
					case 22:
						chatDofile.setXxlx("支出管理");
						break;
					}
					chatList.add(chatDofile);
					// 初始化值
					todoList = new ArrayList<Dofile>();
					chatDofile  = new ChatDofile();
					itemId = tempItemId;
					itemCount = 0;
				}
				itemName = pending.getItem_name();
				Dofile todo = new Dofile();
					todo.setTitle(pending.getProcess_title());
					todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getApply_time()));
					todo.setProcessId(pending.getWf_process_uid());
					todo.setInstanceId(pending.getWf_instance_uid());
					todo.setUserId(userId);
					todo.setItemName(pending.getItem_name());
					todo.setItemId(pending.getItem_id());
					todo.setUrl(serverUrl+"/table_openPendingForm.do?processId="+pending.getWf_process_uid()+"&isDb=true&itemId="+pending.getItem_id()+"&formId="+pending.getForm_id()+"&isCanPush=&deptId="+deptId+"&userId="+userId);
					todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅
						String path = pending.getPdfPath();
						if(!("").equals(path) && path != null){
							path = path.substring(path.lastIndexOf("/")+1,path.length());
						}
					todo.setPdfPath(path); 
				todoList.add(todo);
				itemCount ++;
			}
			chatDofile.setCount(itemCount+"");
			chatDofile.setList(todoList);
			// 获取消息类型
			chatDofile.setItemName(itemName);
			switch(Integer.valueOf(item.getVc_xxlx()== null ? "2":item.getVc_xxlx())){
			case 2:
				chatDofile.setXxlx("公文管理");
				break;
			case 4:
				chatDofile.setXxlx("工作任务");
				break;
			case 5:
				chatDofile.setXxlx("工作计划");
				break;
			case 8:
				chatDofile.setXxlx("工作动态");
				break;
			case 11:
				chatDofile.setXxlx("依申请公开");
				break;
			case 17:
				chatDofile.setXxlx("工作日志");
				break;
			case 19:
				chatDofile.setXxlx("车辆管理");
				break;
			case 20:
				chatDofile.setXxlx("会议管理");
				break;
			case 21:
				chatDofile.setXxlx("资产管理");
				break;
			case 22:
				chatDofile.setXxlx("支出管理");
				break;
			}
				
		
			
			chatList.add(chatDofile);
		}
		return JSONArray.fromObject(chatList).toString();
	}
	
	@Override
	public String getOverListOfMobile(String deptId,String userId, List<Pending> pendList, String type, String itemIds,String serverUrl) {
		List<ChatDofile> chatList = new ArrayList<ChatDofile>();
		String itemId = "";
		if(pendList.size()!=0 && pendList!=null && !("").equals(pendList)){
			itemId = pendList.get(0).getItem_id();
			int itemCount = 0;
			String itemName = "";
			ChatDofile chatDofile  = new ChatDofile();
			List<Dofile> todoList = new ArrayList<Dofile>();
			
			for (Pending pending : pendList) {
				// 获取 当前事项的id
				String tempItemId = pending.getItem_id();
				
				if(!tempItemId.equals(itemId)){
					chatDofile.setCount(itemCount+"");
					chatDofile.setList(todoList);
					chatDofile.setItemName(itemName);
					chatList.add(chatDofile);
					// 初始化值
					todoList = new ArrayList<Dofile>();
					chatDofile  = new ChatDofile();
					itemId = tempItemId;
					itemCount = 0;
				}
				itemName = pending.getItem_name();
				Dofile todo = new Dofile();
					todo.setTitle(pending.getProcess_title());
					todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getApply_time()));
					todo.setProcessId(pending.getWf_process_uid());
					todo.setInstanceId(pending.getWf_instance_uid());
					todo.setUserId(userId);
					todo.setItemName(pending.getItem_name());
					todo.setItemId(pending.getItem_id());
					todo.setUrl(serverUrl+"/table_openOverForm.do?processId="+pending.getWf_process_uid()+"&itemId="+pending.getItem_id()+"&formId="+pending.getForm_id()+"&deptId="+deptId+"&userId="+userId);
					todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅
						String path = pending.getPdfPath();
						if(!("").equals(path) && path != null){
							if(pending.getIsEnd()!=null && pending.getIsEnd().equals("1")){
								path = path.split(",")[0];
								path = path.substring(path.lastIndexOf("/")+1,path.length());
							}else{
								path = path.split(",")[1];
								path = path.substring(path.lastIndexOf("/")+1,path.length());
							}
						}
					todo.setPdfPath(path); 
				todoList.add(todo);
				itemCount ++;
			}
			chatDofile.setCount(itemCount+"");
			chatDofile.setList(todoList);
			chatDofile.setItemName(itemName);
			chatList.add(chatDofile);
		}
		return JSONArray.fromObject(chatList).toString();
	}

	@Override
	public int findLhfwCount(String conditionSql, String depIds) {
		return pendingDao.findLhfwCount(conditionSql, depIds);
	}

	@Override
	public List<Pending> findLhfwpendingList(String conditionSql,
			String depIds, Integer pageIndex, Integer pageSize) {
		return pendingDao.findLhfwpendingList(conditionSql, depIds, pageIndex, pageSize);
	}

	@Override
	public String getChatInfoJson(WfProcess wfProcess, String serverUrl,
			String isOver, String client) {
		Pending pending = pendingDao.getPendingById(wfProcess.getWfProcessUid());
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		Todos todo = new Todos();
		todo.setTitle(pending.getProcess_title());
		todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getApply_time()));
		todo.setProcessId(pending.getWf_process_uid());
		todo.setInstanceId(pending.getWf_instance_uid());
		todo.setUserId(wfProcess.getUserUid());
		todo.setFormId(pending.getForm_id());
		todo.setNodeId(pending.getWf_node_uid());
		todo.setWorkFlowId(pending.getWf_workflow_uid());
		todo.setItemId(pending.getItem_id());
		todo.setItemName(pending.getItem_name());
		todo.setOwner(pending.getOwner());
		todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅
		String path = pending.getPdfPath();
		if(client!=null && client.equals("pc")){
			if(isOver.equals("OVER")){
				todo.setType("1");
				todo.setPdfPath(serverUrl + "/table_openOverForm.do?processId="+pending.getWf_process_uid()
						+"&itemId="+pending.getItem_id()+"&isDb=true&formId="+pending.getForm_id()+"&t="+new Date());
			}else{
				todo.setType("0");
				todo.setPdfPath(serverUrl + "/table_openPendingForm.do?processId="+pending.getWf_process_uid()
						+"&itemId="+pending.getItem_id()+"&isDb=true&formId="+pending.getForm_id()+"&t="+new Date());
			}
		}else{
			isOver  = pending.getIsover();
			if("NOT_OVER".equals(isOver)){
				todo.setType("0");
				isOver = "NOT_OVER";
			}else if("OVER".equals(isOver)){
				todo.setType("1");
				isOver = "OVER";
			}
			todo.setIsOver(isOver);								//移动端,判定是打开待办还是已办
			if(CommonUtil.stringNotNULL(path)){
				if("OVER".equals(isOver) && path.split(",").length > 1){
					path = path.split(",")[1];
				}else{
					path = path.split(",")[0];
				}
			}
			if(path.startsWith(newPdfRoot)){
			    path = serverUrl+ "/form/html/workflow/"+path.substring(newPdfRoot.length());
			}else{
			    path = serverUrl+ "/form/html/"+path.substring(path.lastIndexOf("/") + 1);
			}
			todo.setPdfPath(path);
		}
		// 修改 isMaster 值 routerType =3 
		WfNode wfnode = workflowBasicFlowDao.getWfNode(pending.getWf_node_uid());	//获取当前node
		String route_type = "";
		if(wfnode!=null){
			route_type = wfnode.getWfn_route_type(); 
		}
		if(route_type!=null &&
				(route_type.equals("3") || route_type.equals("4") || route_type.equals("5"))){	 //并行结合;并行传阅,并行办理
			todo.setIsMaster("0");  // 点击完成
		}else{
			todo.setIsMaster(pending.getIsMaster());
		}
		todo.setCommentJson(pending.getCommentJson() == null ?null:pending.getCommentJson().trim());
		return JSONArray.fromObject(todo).toString();
	}
	
	@Override
	public String getFollowListOfMobile(String userId, List<Pending> pendList,  String serverUrl) {
		 String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");

		List<Todos> todoList = new ArrayList<Todos>();
		if(pendList.size()!=0 && pendList!=null && !("").equals(pendList)){
			for (Pending pending : pendList) {
				Todos todo = new Todos();
					todo.setTitle(pending.getProcess_title());
					todo.setDate( new SimpleDateFormat("yyyy-MM-dd").format(pending.getApply_time()));
					todo.setProcessId(pending.getWf_process_uid());
					todo.setInstanceId(pending.getWf_instance_uid());
					todo.setUserId(userId);
					todo.setFormId(pending.getForm_id());
					todo.setNodeId(pending.getWf_node_uid());
					todo.setWorkFlowId(pending.getWf_workflow_uid());
					todo.setItemId(pending.getItem_id());
					todo.setItemName(pending.getItem_name());
					todo.setOwner(pending.getOwner());
					todo.setItemType(pending.getItem_type()); //0-发文，1-办文，2-传阅
						String path = pending.getPdfPath();
						if(!("").equals(path) && path != null){
							if(pending.getIsEnd()!=null && pending.getIsEnd().equals("1")){
								path = path.split(",")[0];
							}else{
								path = path.split(",")[1];
							}
						}
						 if(path.startsWith(newPdfRoot)){
						    path = serverUrl+ "/form/html/workflow/"+path.substring(newPdfRoot.length());
						}else{
						    path = serverUrl+ "/form/html/"+path.substring(path.lastIndexOf("/") + 1);
						}
					todo.setPdfPath(path); 
					todo.setIsMaster(pending.getIsMaster());
					todo.setIsfavourite(pending.getFavourite());
					todo.setStayuserId(pending.getEntrust_name());
				todoList.add(todo);
			}
		}
		return JSONArray.fromObject(todoList).toString();
	}

	@Override
	public JSONObject addFormPage(String instanceId) {
		//获取最新已办单
		WfProcess p = this.pendingDao.getRecentProcess(instanceId);
		String pdfPath = null;
		if(p.getPdfPath()!=null && p.getPdfPath().length()>0){
			pdfPath =  p.getPdfPath().split(",")[1];
		}
		int n = pendingDao.setDofileCopyNumber(instanceId);
		JSONObject jo = new JSONObject();
		jo.put("pdfPath", pdfPath);
		jo.put("number", n);
		return jo;
	}

	@Override
	public WfProcess getRecentProcess(String instanceId) {
		return pendingDao.getRecentProcess(instanceId);
	}
	
	@Override
	public void setBackStatus(List<Pending> list, String isAdmin, String userId) {
		pendingDao.setBackStatus(list,isAdmin, userId);
		
	}
	
	@Override
	public int getCountOfExceedPending(String conditionSql,String userId,String type){
		return pendingDao.getCountOfExceedPending(conditionSql,userId,type);
	}
	
	@Override
	public int getCountOfExceedPending2(String conditionSql){
		return pendingDao.getCountOfExceedPending2(conditionSql);
	}
	
	@Override
	public int getCountOfExceedUR(String conditionSql){
		return pendingDao.getCountOfExceedUR(conditionSql);
	}
	
	
	@Override
	public List<Pending> getExceedPendingList(String conditionSql,String userId, Integer pageIndex, Integer pageSize) {
		List<Pending> pendList = pendingDao.getExceedPendingList(conditionSql,userId, pageIndex, pageSize);
		for(Pending p:pendList){
			String instanceId = p.getWf_instance_uid();
			List<WfProcess> wfps = pendingDao.findProcessListByFId(instanceId);
			if(null != wfps && wfps.size()>0){
				//当前办件有子流程
				p.setIsHaveChild("1");
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
					p.setIsHaveChild("1");
				}else{
					//当前办件是主流程
					p.setIsHaveChild("0");
				}
			}
			if(StringUtils.isNotBlank(p.getProcess_title())){
				p.setProcess_title(p.getProcess_title().replace("\r\n", "").replace("\r", "").replace("\n", ""));
			}
		}
		return pendList;
	}
	@Override
	public List<Object[]> getExceedPendingList2(String conditionSql, Integer pageIndex, Integer pageSize) {
		return pendingDao.getExceedPendingList2(conditionSql, pageIndex, pageSize);
	}
	
	@Override
	public List<Object[]> getSendUser(String instanceId) {
		return pendingDao.getSendUser(instanceId);
	}
	
	@Override
	public List<Object[]> getExceedURList(String conditionSql, Integer pageIndex, Integer pageSize) {
		return pendingDao.getExceedURList(conditionSql, pageIndex, pageSize);
	}

	@Override
	public List<Department> getDeptListForpage() {
		
		return pendingDao.getDeptListForpage();
	}

	@Override
	public void recallDoFileForBXCY(String instanceId, String stepIndex,
			String userId) {
		pendingDao.recallDoFileForBXCY(instanceId,stepIndex,userId);
		
	}
}
