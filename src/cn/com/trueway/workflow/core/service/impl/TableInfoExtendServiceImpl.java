package cn.com.trueway.workflow.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.dao.TableInfoExtendDao;
import cn.com.trueway.workflow.core.pojo.AccessLog;
import cn.com.trueway.workflow.core.pojo.AutoFile;
import cn.com.trueway.workflow.core.pojo.AutoSend;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.FileDownloadLog;
import cn.com.trueway.workflow.core.pojo.FollowShip;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.vo.Todos;
import cn.com.trueway.workflow.core.service.TableInfoExtendService;
import cn.com.trueway.workflow.set.dao.GroupDao;
import cn.com.trueway.workflow.set.pojo.LostAttsDf;
import cn.com.trueway.workflow.set.pojo.LostCmtDf;

/**
 * 描述：核心操作类服务层扩展 实现
 * 作者：蒋烽
 * 创建时间：2017-4-13 上午8:45:14
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class TableInfoExtendServiceImpl implements TableInfoExtendService {
	
	private TableInfoExtendDao 	tableInfoExtendDao;
	
	private GroupDao			groupDao;
	
	private TableInfoDao		tableInfoDao;
	
	public TableInfoExtendDao getTableInfoExtendDao() {
		return tableInfoExtendDao;
	}

	public void setTableInfoExtendDao(TableInfoExtendDao tableInfoExtendDao) {
		this.tableInfoExtendDao = tableInfoExtendDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	@Override
	public List<FollowShip> getFollowShips(String instanceId,
			String employeeGuid, String oldInstanceId) {
		return tableInfoExtendDao.selectFollowShip(instanceId, employeeGuid, oldInstanceId);
	}

	@Override
	public void addFollowShip(FollowShip entity) {
		tableInfoExtendDao.insertFollowShip(entity);
	}

	@Override
	public void removeFollowShip(FollowShip entity) {
		tableInfoExtendDao.deleteFollowShip(entity);
	}

	@Override
	public void editFollowShip(FollowShip entity) {
		tableInfoExtendDao.updateFollowShip(entity);
	}

	@Override
	public List<Pending> getFollowList(String conditionSql, String userId,
			Integer pageIndex, Integer pageSize, String status) {
		return tableInfoExtendDao.getFollowList(conditionSql, userId, pageIndex, pageSize, status);
	}

	@Override
	public Integer getCountOfFollow(String conditionSql, String userId,
			String status) {
		return tableInfoExtendDao.getCountOfFollow(conditionSql, userId, status);
	}

	@Override
	public List<Map<String, String>> getOutOfDateList(String conditionSql, Integer pageIndex, Integer pageSize) {
		List<Object[]> instanceIds = tableInfoExtendDao.getOutOfDateList(conditionSql, pageIndex, pageSize);
		List<Map<String, String>> outList = new ArrayList<Map<String, String>>();
		for (Object[] o : instanceIds) {
			String instanceId = null != o[0] ? o[0].toString() : "";
			Map<String, String> map = new HashMap<String, String>();
			List<Object[]> list = tableInfoExtendDao.getOutOfDateListByInstanceId(conditionSql, instanceId);
			if(null != list && list.size()>0){
				Object[] object = list.get(0);
				map.put("processUid", null != object[0] ? object[0].toString() : "");
				map.put("title", null != object[1] ? object[1].toString() : "");
				map.put("instanceId", instanceId);
				map.put("itemName", null != object[5] ? object[5].toString() : "");
				map.put("status", null != object[18] ? object[18].toString() : "");
				map.put("shipinstanceId", null != object[19] ? object[19].toString() : "");
				String username = "",userId = "",fromUserName="",depName="";
				for (Object[] obj : list) {
					userId += (null != obj[3] ? obj[3].toString() : " ") +",";//办件持有人id
					username += (null != obj[4] ? obj[4].toString() : " ") +",";//办件持有人姓名
					String uname = null != obj[6] ? obj[6].toString() : "";
					if(StringUtils.isNotBlank(uname) && fromUserName.indexOf(uname) == -1){
						fromUserName += uname + ",";
					}
					String udep = null != obj[7] ? obj[7].toString() : "";
					if(StringUtils.isNotBlank(udep) && depName.indexOf(udep) == -1){
						depName += udep + ",";
					}
				}
				if(StringUtils.isNotBlank(userId)){
					userId = userId.substring(0, userId.length()-1);
				}
				map.put("userId", userId);
				
				if(StringUtils.isNotBlank(username)){
					username = username.substring(0, username.length()-1);
				}
				map.put("username", username);
				
				if(StringUtils.isNotBlank(fromUserName)){
					fromUserName = fromUserName.substring(0, fromUserName.length()-1);
				}
				map.put("fromUserName", fromUserName);
				
				if(StringUtils.isNotBlank(depName)){
					depName = depName.substring(0, depName.length()-1);
				}
				map.put("depName", depName);
				outList.add(map);
			}else{
				continue;
			}
		}
		return outList;
	}

	@Override
	public int getCountOfOutOfDate(String conditionSql, String type) {
		return tableInfoExtendDao.getCountOfOutOfDate(conditionSql, type);
	}

	@Override
	public List<Object[]> getOutOfDateListByInstanceId(String conditionSql,
			String instanceId) {
		return tableInfoExtendDao.getOutOfDateListByInstanceId(conditionSql, instanceId);
	}

	@Override
	public String getOutOfDateList4Mobile(String conditionSql, String serverUrl,
			Integer pageIndex, Integer pageSize) {
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		List<Object[]> instanceIds = tableInfoExtendDao.getOutOfDateList(conditionSql, pageIndex, pageSize);
		List<Todos> outList = new ArrayList<Todos>();
		for (Object[] o : instanceIds) {
			String instanceId = null != o[0] ? o[0].toString() : "";
			Todos todo = new Todos();
			List<Object[]> list = tableInfoExtendDao.getOutOfDateListByInstanceId(conditionSql, instanceId);
			if(null != list && list.size()>0){
				Object[] object = list.get(0);
				todo.setTitle(null != object[1] ? object[1].toString() : "");
				todo.setDate( null != object[8] ? object[8].toString() : "");
				todo.setProcessId(null != object[0] ? object[0].toString() : "");
				todo.setInstanceId(instanceId);
				todo.setFormId(null != object[9] ? object[9].toString() : "");
				todo.setNodeId(null != object[2] ? object[2].toString() : "");
				todo.setWorkFlowId(null != object[10] ? object[10].toString() : "");
				todo.setItemId(null != object[11] ? object[11].toString() : "");                                             	
				todo.setItemName(null != object[5] ? object[5].toString() : "");                                         	
				todo.setOwner(null != object[12] ? object[12].toString() : "");                                                	
				todo.setItemType(null != object[13] ? object[13].toString() : "");
				String path = null != object[14] ? object[14].toString() : "";
				String isEnd = null != object[16] ? object[16].toString() : "";
				if(!("").equals(path) && path != null){
					if(isEnd!=null && isEnd.equals("1")){               	
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
				todo.setIsMaster(null != object[15] ? object[15].toString() : "");
				String userId = "";
				for (Object[] obj : list) {
					userId += (null != obj[3] ? obj[3].toString() : " ") +",";//办件持有人id
				}
				if(StringUtils.isNotBlank(userId)){
					userId = userId.substring(0, userId.length()-1);
				}
				todo.setUserId(userId);                                 	
				todo.setStayuserId(null != object[17] ? object[17].toString() : "");  
				outList.add(todo);
			}else{
				continue;
			}
		}
		return JSONArray.fromObject(outList).toString();
	}
	
	@Override
	public void deleteFollowShip(String instaceId, String userId) {
		tableInfoExtendDao.deleteFollowShip(instaceId, userId);
	}

	@Override
	public String getTextValue(String commentJson,String processId) {
		String commentText = "";
		try {
			JSONObject obj1 = JSONObject.fromObject(commentJson);
			JSONArray pagesArr = obj1.getJSONArray("pages");
			JSONArray valArr = new JSONArray();
			for (Object object : pagesArr) {
				JSONObject pageObj = JSONObject.fromObject(object);
				JSONArray formdataArr = pageObj.get("formdata") != null ? pageObj.getJSONArray("formdata"):null;
				if(null != formdataArr){
					for (Object object2 : formdataArr) {
						JSONObject formdataObj = JSONObject.fromObject(object2);
						Object object4 = formdataObj.get("values");
						if(object4 != null){
							JSONArray valsArr = formdataObj.getJSONArray("values");
							for (Object object3 : valsArr) {
								valArr.add(object3);
							}
						}
					}
				}
			}
			for (Object object : valArr) {
				JSONObject valObj = JSONObject.fromObject(object);
				if(valObj.get("sign") != null && valObj.getString("sign").equals("0") && valObj.get("processId") != null && valObj.getString("processId").equals(processId)){
					commentText = valObj.getString("text").trim().replace("　", "");
					break;
				}
			}
		} catch (Exception e) {
			commentText = "";
		}
		return commentText;
	}
	
	public static void main(String[] args) {
		TableInfoExtendServiceImpl extendServiceImpl = new TableInfoExtendServiceImpl();
		String commentJson = "{\"StampType\":1,\"pages\":[{\"width\":1024,\"height\":1448,\"basicOS\":\"android_phone\",\"company\":\"trueway\",\"version\":\"2\",\"page\":0,\"isShow\":1,\"formdata\":[{\"id\":\"bjfkqk\",\"type\":\"sign\",\"values\":[]},{\"id\":\"xcb\",\"type\":\"sign\",\"values\":[]},{\"id\":\"lwp\",\"type\":\"sign\",\"values\":[{\"type\":\"words\",\"text\":\"阅\",\"sign\":0,\"color\":\"#000000\",\"font-size\":20,\"processId\":\"575FD2A3-956F-4060-A817-7F7AB5A1EC79\",\"bold\":0,\"valign\":\"top\",\"text-align\":\"left\",\"userId\":\"9b850beb-1ef9-452e-b1d8-7c23a36bd5ff\",\"nodeId\":\"\"},{\"type\":\"words\",\"text\":\"林武平  2018/05/08\",\"sign\":1,\"color\":\"#000000\",\"font-size\":20,\"processId\":\"575FD2A3-956F-4060-A817-7F7AB5A1EC79\",\"bold\":0,\"valign\":\"top\",\"text-align\":\"left\",\"userId\":\"9b850beb-1ef9-452e-b1d8-7c23a36bd5ff\",\"nodeId\":\"\"}]},{\"id\":\"qyyfzb\",\"type\":\"sign\",\"values\":[]},{\"id\":\"jczzjsb\",\"type\":\"sign\",\"values\":[]},{\"id\":\"sl\",\"type\":\"sign\",\"values\":[{\"type\":\"words\",\"text\":\"已阅\",\"sign\":0,\"color\":\"#000000\",\"font-size\":20,\"processId\":\"CB35C811-6C62-44FE-8AFE-DF146294CEE6\",\"bold\":0,\"valign\":\"middle\",\"text-align\":\"left\",\"userId\":\"93db6961-69a3-415c-b45a-358fe32dfdc3\",\"nodeId\":\"321894FB-D7BA-41EC-9BCB-D830D33F3A5D\",\"time\":\"2018-05-08 09:25:38\"},{\"type\":\"words\",\"text\":\"石磊 2018/05/08 \",\"sign\":1,\"color\":\"#000000\",\"font-size\":20,\"processId\":\"CB35C811-6C62-44FE-8AFE-DF146294CEE6\",\"bold\":0,\"valign\":\"middle\",\"text-align\":\"left\",\"userId\":\"93db6961-69a3-415c-b45a-358fe32dfdc3\",\"nodeId\":\"321894FB-D7BA-41EC-9BCB-D830D33F3A5D\",\"time\":\"2018-05-08 09:25:38\"}]},{\"id\":\"tzb\",\"type\":\"sign\",\"values\":[]},{\"id\":\"shllb\",\"type\":\"sign\",\"values\":[]},{\"id\":\"wh\",\"type\":\"sign\",\"values\":[{\"type\":\"words\",\"text\":\"阅\",\"sign\":0,\"color\":\"#000000\",\"font-size\":20,\"processId\":\"1720E8C1-D252-4361-B59A-FB97B8092BD3\",\"bold\":0,\"valign\":\"middle\",\"text-align\":\"left\",\"userId\":\"ea0f5128-9604-4b91-a7b5-2611f201d276\",\"nodeId\":\"321894FB-D7BA-41EC-9BCB-D830D33F3A5D\",\"time\":\"2018-05-08 19:08:17\"},{\"type\":\"words\",\"text\":\"王慧 2018/05/08 \",\"sign\":1,\"color\":\"#000000\",\"font-size\":20,\"processId\":\"1720E8C1-D252-4361-B59A-FB97B8092BD3\",\"bold\":0,\"valign\":\"middle\",\"text-align\":\"left\",\"userId\":\"ea0f5128-9604-4b91-a7b5-2611f201d276\",\"nodeId\":\"321894FB-D7BA-41EC-9BCB-D830D33F3A5D\",\"time\":\"2018-05-08 19:08:17\"}]},{\"id\":\"bgsnbyj\",\"type\":\"sign\",\"values\":[{\"type\":\"words\",\"text\":\"请各位书记阅！\",\"sign\":0,\"color\":\"#000000\",\"font-size\":20,\"processId\":\"116C0C99-9C48-4FFC-BCB1-76E9D9E3A142\",\"bold\":0,\"valign\":\"middle\",\"text-align\":\"left\",\"userId\":\"1ee44db6-feff-4361-8b37-1f0c7e7df96c\",\"nodeId\":\"\"},{\"type\":\"words\",\"text\":\"何水 2018/05/08 \",\"sign\":1,\"color\":\"#000000\",\"font-size\":20,\"processId\":\"116C0C99-9C48-4FFC-BCB1-76E9D9E3A142\",\"bold\":0,\"valign\":\"middle\",\"text-align\":\"left\",\"userId\":\"1ee44db6-feff-4361-8b37-1f0c7e7df96c\",\"nodeId\":\"\"}]},{\"id\":\"qsng\",\"type\":\"sign\",\"values\":[]},{\"id\":\"fangjone\",\"type\":\"sign\",\"values\":[{\"type\":\"words\",\"text\":\"已阅\",\"sign\":0,\"color\":\"#000000\",\"font-size\":20,\"processId\":\"34DE7B3C-6BD3-41D8-B85B-DE8F039063EF\",\"bold\":0,\"valign\":\"top\",\"text-align\":\"left\",\"userId\":\"13f60b65-a583-4f45-97fb-aa265ae19c87\",\"nodeId\":\"321894FB-D7BA-41EC-9BCB-D830D33F3A5D\"},{\"type\":\"words\",\"text\":\"方靖  2018/05/08\",\"sign\":1,\"color\":\"#000000\",\"font-size\":20,\"processId\":\"34DE7B3C-6BD3-41D8-B85B-DE8F039063EF\",\"bold\":0,\"valign\":\"top\",\"text-align\":\"left\",\"userId\":\"13f60b65-a583-4f45-97fb-aa265ae19c87\",\"nodeId\":\"321894FB-D7BA-41EC-9BCB-D830D33F3A5D\"}]},{\"id\":\"bgs\",\"type\":\"sign\",\"values\":[]},{\"id\":\"xsb\",\"type\":\"sign\",\"values\":[]},{\"id\":\"qzy\",\"type\":\"sign\",\"values\":[]},{\"id\":\"zzb\",\"type\":\"sign\",\"values\":[]}],\"stamps\":[],\"processes\":[]},{\"width\":1024,\"height\":1448,\"basicOS\":\"android_phone\",\"company\":\"trueway\",\"version\":\"2\",\"page\":1,\"isShow\":1,\"stamps\":[],\"processes\":[{\"userId\":\"13f60b65-a583-4f45-97fb-aa265ae19c87\",\"workflowId\":\"5B10C65F-D6EF-4AFD-AEF5-2174EDC1ABC3\",\"nodeId\":\"321894FB-D7BA-41EC-9BCB-D830D33F3A5D\",\"instanceId\":\"DC44F227-8E67-4BF6-9F24-A472BF0FB120\",\"processId\":\"34DE7B3C-6BD3-41D8-B85B-DE8F039063EF\",\"formId\":\"640FD861-4C31-42B9-ABC6-34EB62580132\",\"sendtime\":\"2018-05-08 09:17:39\",\"username\":\"方靖\",\"show\":1,\"datas\":[]}]},{\"width\":1024,\"height\":1448,\"basicOS\":\"android_phone\",\"company\":\"trueway\",\"version\":\"2\",\"page\":2,\"isShow\":1,\"stamps\":[],\"processes\":[{\"userId\":\"13f60b65-a583-4f45-97fb-aa265ae19c87\",\"workflowId\":\"5B10C65F-D6EF-4AFD-AEF5-2174EDC1ABC3\",\"nodeId\":\"321894FB-D7BA-41EC-9BCB-D830D33F3A5D\",\"instanceId\":\"DC44F227-8E67-4BF6-9F24-A472BF0FB120\",\"processId\":\"34DE7B3C-6BD3-41D8-B85B-DE8F039063EF\",\"formId\":\"640FD861-4C31-42B9-ABC6-34EB62580132\",\"sendtime\":\"2018-05-08 09:17:39\",\"username\":\"方靖\",\"show\":1,\"datas\":[]}]},{\"width\":1024,\"height\":1448,\"basicOS\":\"android_phone\",\"company\":\"trueway\",\"version\":\"2\",\"page\":3,\"isShow\":1,\"stamps\":[],\"processes\":[{\"userId\":\"13f60b65-a583-4f45-97fb-aa265ae19c87\",\"workflowId\":\"5B10C65F-D6EF-4AFD-AEF5-2174EDC1ABC3\",\"nodeId\":\"321894FB-D7BA-41EC-9BCB-D830D33F3A5D\",\"instanceId\":\"DC44F227-8E67-4BF6-9F24-A472BF0FB120\",\"processId\":\"34DE7B3C-6BD3-41D8-B85B-DE8F039063EF\",\"formId\":\"640FD861-4C31-42B9-ABC6-34EB62580132\",\"sendtime\":\"2018-05-08 09:17:39\",\"username\":\"方靖\",\"show\":1,\"datas\":[]}]}],\"resources\":[],\"ServerUrl\":\"http://61.155.85.74:6082/trueSeal/\",\"docId\":\"DC44F227-8E67-4BF6-9F24-A472BF0FB120\"}";
		String processID = "575FD2A3-956F-4060-A817-7F7AB5A1EC79";
		String commentText = extendServiceImpl.getTextValue(commentJson, processID);
		System.out.println(commentText);
	}

	@Override
	public void addAutoFile(AutoFile entity) {
		tableInfoExtendDao.insertAutoFile(entity);
	}

	@Override
	public void modifyAutoFile(AutoFile entity) {
		tableInfoExtendDao.updateAutoFile(entity);
	}

	@Override
	public void addAccessLog(AccessLog entity) {
		tableInfoExtendDao.addAccessLog(entity);
	}

	@Override
	public Integer countAccessLog(Map<String, String> map) {
		return tableInfoExtendDao.countAccessLog(map);
	}

	@Override
	public List<AccessLog> getAccLog(Map<String, String> map,
			Integer pageIndex, Integer pageSize) {
		return tableInfoExtendDao.selectAccLog(map, pageIndex, pageSize);
	}
	
	@Override
	public List<LostCmtDf> getLostCmtDfList(Map<String,String> map, Integer pageIndex, Integer pageSize){
		return tableInfoExtendDao.getLostCmtDfList(map, pageIndex, pageSize);
	}
	
	@Override
	public int getLostCmtDfCount(Map<String,String> map){
		return tableInfoExtendDao.getLostCmtDfCount(map);
	}
	
	@Override
	public List<LostAttsDf> getLostAttDfList(Map<String,String> map, Integer pageIndex, Integer pageSize){
		return tableInfoExtendDao.getLostAttDfList(map, pageIndex, pageSize);
	}
	
	@Override
	public int getLostAttDfCount(Map<String,String> map){
		return tableInfoExtendDao.getLostAttDfCount(map);
	}

	@Override
	public List<WfNode> skipNextNodes(List<WfNode> nodes, String userId) {
		List<WfNode> list = new ArrayList<WfNode>();
		for (WfNode wfNode : nodes) {
			String groupId = wfNode.getWfn_lastStaff();
			if(StringUtils.isNotBlank(groupId)){
				List<String> innerUserList = groupDao.getUserIdFromInnerUserMap(groupId,userId);
				if(null != innerUserList && innerUserList.size()>0){
					list.add(wfNode);
				}
			}
		}
		if(list.size()<1){
			list = nodes;
		}
		return list;
	}
	
	@Override
	public void sendAutoData(String nodeId, String fromNodeId){
		int i = 1;
		String id = "";
		try {
			//1,查出需要处理的instanceId
			List<String> instanceId = tableInfoExtendDao.getInstanceId(nodeId, fromNodeId);
			WfNode node = tableInfoExtendDao.getWfNodeById(nodeId);
			//2,遍历这些instanceId，将数据插入自动办理的表里面
			for (String string : instanceId) {
				id = string;
				System.out.println("dealAutoData----,i="+i+",instanceId="+string);
				List<WfProcess> list = tableInfoExtendDao.getAutoSendStep(nodeId, fromNodeId, string);
				this.toAutoSendNext(list,node);
				i++;
				
			}
		} catch (Exception e) {
			System.out.println("完犊子，请求失败了，失败的instanceId是："+id);
		}
		
	}
	
	
	// 处理自动办理
	public void toAutoSendNext(List<WfProcess> wfpList,WfNode node) {
		String sameProcess = "", routeType = "";
		Date applyTime = null;
		if (wfpList != null && wfpList.size() > 0) {
			Integer index = wfpList.size() - 1;
			WfProcess lastp = wfpList.get(index);
			if (lastp != null) {
				sameProcess = lastp.getWfProcessUid();
				routeType = node.getWfn_route_type();
				applyTime = lastp.getApplyTime();
			}
			for (int i = 0; i < wfpList.size(); i++) {// 自动办理节点前一个节点 是多人
				WfProcess wp = wfpList.get(i);
				if (wp != null && node != null) {
					int isMaster = 1;
					if(routeType.equals("2")){//并行完全式
						isMaster = wp.getIsMaster();
					}
					if (node.getWfn_autoSend() != null && node.getWfn_autoSend() == 1) {
						if (node.getWfn_autoSendDays() != null && node.getWfn_autoSendDays() != 0) {
							int autosenddays = node.getWfn_autoSendDays();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(applyTime);
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

	@Override
	public List<String> getColumnNames(String tableName) {
		return tableInfoExtendDao.getColumnNames(tableName);
	}

	@Override
	public List<Object[]> getOutSideTab(String tableName, String columns,
			String instanceId) {
		return tableInfoExtendDao.selectOutSideTab(tableName, columns, instanceId);
	}

	@Override
	public void addFileDownloadLog(DoFile file, Employee emp, String type) {
		if(null != file && null != emp){
			FileDownloadLog entity = new FileDownloadLog();
			entity.setDownloadTime(new Date());
			entity.setEmployeeGuid(emp.getEmployeeGuid());
			entity.setEmployeeName(emp.getEmployeeName());
			entity.setFileTitle(file.getDoFile_title());
			entity.setInstanceId(file.getInstanceId());
			entity.setType(type);
			tableInfoExtendDao.insert(entity);
		}
	}

	@Override
	public Integer countFileDownloadLog(Map<String, String> map) {
		return tableInfoExtendDao.countFileDownloadLogs(map);
	}

	@Override
	public List<FileDownloadLog> getFileDownloadLog(Map<String, String> map,
			Integer pageIndex, Integer pageSize) {
		return tableInfoExtendDao.selectFileDownloadLogs(map, pageIndex, pageSize);
	}
}
