package cn.com.trueway.workflow.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.SpinLock;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.dao.TrueJsonDao;
import cn.com.trueway.workflow.core.pojo.TrueJson;
import cn.com.trueway.workflow.core.pojo.TrueJsonLog;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.TrueJsonService;

public class TrueJsonServiceImpl implements TrueJsonService{
	
	private TrueJsonDao  	trueJsonDao;
	
	private TableInfoDao 	tableInfoDao;
	
	private static Map<String, SpinLock> lockMap = new HashMap<String, SpinLock>();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
	
	private SpinLock s3 = new SpinLock();
	
	public TrueJsonDao getTrueJsonDao() {
		return trueJsonDao;
	}

	public void setTrueJsonDao(TrueJsonDao trueJsonDao) {
		this.trueJsonDao = trueJsonDao;
	}

	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	@Override
	public void saveTrueJson(TrueJson entity) {
		s3.lock();
    	SpinLock s = lockMap.get(entity.getInstanceId());
    	s3.unlock();
    	if(null != s){
    		System.out.println(entity.getInstanceId()+"等待解锁,---"+sdf.format(new Date()));
    		s.lock();
		}else{
			SpinLock s2 = new SpinLock();
			s3.lock();
			lockMap.put(entity.getInstanceId(), s2);
			s2.lock();
			s3.unlock();
			System.out.println(entity.getInstanceId()+"得到了锁,---"+sdf.format(new Date()));
		}
		
		try {
			entity.setSaveDate(new Date());
			saveTrueJsonLog(entity);
			//重组json
			entity = NewComposeJson(entity);
			trueJsonDao.saveTrueJson(entity);
			saveTrueJsonLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			s3.lock(); 
			SpinLock s2=lockMap.remove(entity.getInstanceId());
			 if(s2 != null){
	            	System.out.println(entity.getInstanceId()+"s2释放了锁,---"+sdf.format(new Date()));
	            	s2.unlock();
	        }
            s3.unlock();
            if(s != null){
            	System.out.println(entity.getInstanceId()+"s释放了锁,---"+sdf.format(new Date()));
            	s.unlock();
            }
        }
	}
	
	/**
	 * 重组意见json
	 */
	public TrueJson NewComposeJson(TrueJson entity) {
		String newJSON = "";// 改之后的json
		String currentJson = entity.getTrueJson();
		String processId = entity.getProcessId();
		String userId = entity.getUserId();
		String nodeId = "";
		WfProcess process = tableInfoDao.getProcessById(processId);
		String oldJson = "";
		if (process != null) {
			String instanceId = process.getWfInstanceUid();
			nodeId = process.getNodeUid();
			TrueJson oldTrueJson = trueJsonDao.findTrueJsonForDofile(instanceId);
			if (oldTrueJson != null) {
				if (!processId.equals(oldTrueJson.getProcessId())) {//单人签批暂存除外
					oldJson = oldTrueJson.getTrueJson();
				}
			}
		}
		if (oldJson != null && !"".equals(oldJson)) {// 需要将表单内容 与 数据库内容进行比对
			JSONObject new_obj = JSONObject.fromObject(currentJson);
			JSONArray new_pages = new_obj.getJSONArray("pages");
			JSONObject newFirstPageObj = new_pages.getJSONObject(0);//表单页
			JSONObject old_obj = JSONObject.fromObject(oldJson);
			JSONArray old_pages = old_obj.getJSONArray("pages");
			JSONObject oldFirstPageObj = old_pages.getJSONObject(0);
			if (old_pages.size() > 0) {// 数据库中已存在部分签批意见
				// ---------------------先处理表单页面上的签批意见(只考虑一个节点上只能在一个意见类型中进行操作)----------------------
				// 为了排序，以数据库最新数据为基础，做合并
				JSONArray pagesJson = new JSONArray();
				JSONArray oldFormdata = oldFirstPageObj.getJSONArray("formdata");// 表单上的意见类型
				JSONArray newFormdata = newFirstPageObj.getJSONArray("formdata");
				if (oldFormdata.size() > 0) {
					//---------------删除本节点、登录人员的意见-------------------
					for (int i = 0; i < oldFormdata.size(); i++) {
						JSONObject notion = oldFormdata.getJSONObject(i);
						if (notion.get("values") != null) {
							JSONArray valueArr = notion.getJSONArray("values");
							JSONArray deleteArr = new JSONArray();
							for(int j=0;j<valueArr.size();j++){
								JSONObject value = valueArr.getJSONObject(j);
								if (value.get("nodeId") != null && value.get("userId") != null && value.get("processId") != null) {
									String nodeId1 = value.getString("nodeId");
									String userId1 = value.getString("userId");
									String processId1 = value.getString("processId");
									if (userId1.equals(userId) && nodeId1.equals(nodeId) && processId1.equals(processId)) {
										deleteArr.add(value);
									}
								}
							}
							valueArr.removeAll(deleteArr);
							if (valueArr.size() == 0) {
								oldFormdata.remove(notion);
							} else {
								notion.put("values", valueArr);
								oldFormdata.set(i, notion);
							}
						}
					}
					if (newFormdata.size() > 0) {
						//------------------处理新增意见类型----------------------------
						JSONArray addjson = new JSONArray();
						for (int i = 0; i < newFormdata.size(); i++) {//循环意见类型
							JSONObject notion2 = newFormdata.getJSONObject(i);
							String id2 = notion2.getString("id");
							if(notion2.get("values") != null){
								int addflag = 0;
								for (int j = 0; j < oldFormdata.size(); j++) {//循环意见类型
									JSONObject notion1 = oldFormdata.getJSONObject(j);
									String id1 = notion1.getString("id");
									if(!id1.equals(id2)){
										addflag++;
									}
								}
								if(addflag == oldFormdata.size()){
									addjson.add(notion2);
								}
							}
						}
						//------------------处理修改意见类型----------------------------
						for (int i = 0; i < oldFormdata.size(); i++) {// 循环数据库中经过处理的意见类型
							JSONObject notion1 = oldFormdata.getJSONObject(i);
							String id1 = notion1.getString("id");
							JSONArray valueArr1 = new JSONArray();
							if(notion1.get("values") != null){
								valueArr1 = notion1.getJSONArray("values");
							}
							for (int j = 0; j < newFormdata.size(); j++) {
								JSONObject notion2 = newFormdata.getJSONObject(j);
								String id2 = notion2.getString("id");
								if(id1.equals(id2)){
									if(notion2.get("values") != null){
										JSONArray valueArr2 = notion2.getJSONArray("values");
										for(int z = 0;z<valueArr2.size();z++){
											JSONObject value2 = valueArr2.getJSONObject(z);
											if (value2.get("userId") != null && value2.get("processId") != null) {
												String processId2 = value2.getString("processId");
//												String nodeId2 = value2.get("nodeId")!=null?value2.getString("nodeId"):"";
//												if(CommonUtil.stringIsNULL(nodeId2)){
//													nodeId2 = tableInfoDao.findNodeIdByProcessId(processId2);
//												}
												String userId2 = value2.getString("userId");
												if (userId2.equals(userId)/* && nodeId2.equals(nodeId)*/ && processId2.equals(processId)) {
													valueArr1.add(value2);
												}
											}
										}
									}
								}
							}
							if (valueArr1.size() > 0) {
								notion1.put("values", valueArr1);
								oldFormdata.set(i, notion1);
							}
						}
						for(int i=0;i<addjson.size();i++){
							JSONObject aj = addjson.getJSONObject(i);
							oldFormdata.add(aj);
						}
						oldFirstPageObj.put("formdata", oldFormdata);
						pagesJson.add(oldFirstPageObj);
					} else {//页面表单页无意见
						//有可能根本就没意见、也有可能是表单上删除了意见
						pagesJson.add(oldFirstPageObj);
					}
				} else {// 数据库表单页无意见
					pagesJson.add(newFirstPageObj);
				}
				
				// ---------------------处理附件上的拉框意见----------------------
				JSONArray fjJson = new JSONArray();
				if (new_pages.size() > 1) {// 说明页面附件上有拉框的意见
					if (old_pages.size() == 1) {// 数据库中无附件意见
						for (int i = 1; i < new_pages.size(); i++) {// 循环页面意见
							JSONObject newFjJsonObj = new_pages.getJSONObject(i);
							fjJson.add(newFjJsonObj);
						}
					} else {
						// -----------第一步，处理新多出来的附件页意见--------------------
						for (int i = 1; i < new_pages.size(); i++) {// 循环页面
							JSONObject obj_pages1 = new_pages.getJSONObject(i);
							int pagenum1 = obj_pages1.getInt("page");
							int addpagenum = 0;
							for (int j = 1; j < old_pages.size(); j++) {
								JSONObject obj_pages2 = old_pages.getJSONObject(j);
								int pagenum2 = obj_pages2.getInt("page");
								if (pagenum1 != pagenum2) {
									addpagenum++;
								}
							}
							if (addpagenum == old_pages.size() - 1) {// 新增了附件页意见
								fjJson.add(obj_pages1);
							}
						}
						// -----------第二步，处理同一附件页上的意见--------------------
						for (int i = 1; i < old_pages.size(); i++) {//循环数据库
							JSONObject obj_pages1 = old_pages.getJSONObject(i);
							int pagenum1 = obj_pages1.getInt("page");
							if (obj_pages1.get("processes") != null) {
								JSONArray processArr1 = obj_pages1.getJSONArray("processes");
								// ---------删除数据库中本人在本节点的拉框意见-----------
								for (int j = 1; j < processArr1.size(); j++) {
									JSONObject process1 = processArr1.getJSONObject(j);
									if (process1.get("userId") != null && process1.get("nodeId") != null) {
										String userId1 = process1.getString("userId");
										String nodeId1 = process1.getString("nodeId");
										if (userId1.equals(userId) && nodeId1.equals(nodeId)) {
											processArr1.remove(j);
										}
									}
								}
								// ---------找出页面附件页的本节点、登录人的拉框签批意见-----------
								for (int j = 1; j < new_pages.size(); j++) {
									JSONObject obj_pages2 = new_pages.getJSONObject(j);
									int pagenum2 = obj_pages2.getInt("page");
									if(pagenum2 == pagenum1){
										if (obj_pages2.get("processes") != null) {
											JSONArray processArr2 = obj_pages2.getJSONArray("processes");
											//将页面附件中本节点、登录人的拉框签批意见
											for (int z = 1; z < processArr2.size(); z++) {
												JSONObject process2 = processArr2.getJSONObject(z);
												if (process2.get("userId") != null && process2.get("nodeId") != null) {
													String userId2 = process2.getString("userId");
													String nodeId2 = process2.getString("nodeId");
													if (userId2.equals(userId) && nodeId2.equals(nodeId)) {
														processArr1.add(process2);
													}
												}
											}
										}
									}
								}
								//----------合并附件页的拉框签批意见----------------
								if(processArr1.size() > 0){
									obj_pages1.put("processes", processArr1);
									fjJson.add(obj_pages1);
								}
							} else {
								for (int z = 1; z < new_pages.size(); z++) {// 循环页面意见
									JSONObject newFjJsonObj = new_pages.getJSONObject(z);
									fjJson.add(newFjJsonObj);
								}
							}
						}
					}
				} else {// 页面附件上没有拉框意见
					if (old_pages.size() > 1) {// 但是数据库中已经存在了附件拉框意见
						for (int i = 1; i < old_pages.size(); i++) {// 循环数据库意见
							JSONObject oldFjJsonObj = old_pages.getJSONObject(i);
							fjJson.add(oldFjJsonObj);
						}
					}
				}
				// -------------------重新整合意见------------------------
				JSONArray newArray = new JSONArray();
				for(int i=0;i<pagesJson.size();i++){
					JSONObject pj = pagesJson.getJSONObject(i);
					newArray.add(pj);
				}
				for(int i=0;i<fjJson.size();i++){
					JSONObject fj = fjJson.getJSONObject(i);
					newArray.add(fj);
				}
				old_obj.put("pages", newArray);
				newJSON = old_obj.toString();
			} else {//数据库中不存在签批意见
				newJSON = currentJson;
			}
		} else {//无需比对，直接获取表单内容
			newJSON = currentJson;
		}
		entity.setTrueJson(newJSON);
		return entity;
	}
	
	/**
	 * 重组意见json
	 * @param entity
	 * @return
	 */
	public TrueJson composeJson(TrueJson entity){
		String oldJSON = "";//改之前的json
		String newJSON = "";//改之后的json
		String currentJson = entity.getTrueJson();
		System.out.println("当前页面json:" + currentJson);
		String processId = entity.getProcessId();
		String userId = entity.getUserId();
		String nodeId = "";
		WfProcess process = tableInfoDao.getProcessById(processId);
		String oldJson = "";
		if(process != null){
			String instanceId = process.getWfInstanceUid();
			nodeId = process.getNodeUid();
			TrueJson oldTrueJson = trueJsonDao.findTrueJsonForDofile(instanceId);
			if(oldTrueJson != null){
				if(!processId.equals(oldTrueJson.getProcessId())){
					oldJson = oldTrueJson.getTrueJson();
					System.out.println("数据库最新数据json:" + oldJson);
				}
			}
		}
		if(oldJson != null && !"".equals(oldJson)){
			String json = "";
			JSONArray oldFormdata = null, newFormdata = null;
			JSONObject oldPages = null,newPages = null;
			int oldFormdataNum = 0, newFormdataNum = 0;
			String old_pages_str = "",new_pages_str = "";//pages
			String leftStr = "",rightStr = "";
			JSONObject old_obj = JSONObject.fromObject(oldJson);
			JSONArray old_pages = old_obj.getJSONArray("pages");
			List<Map<String,String>> oldList = new ArrayList<Map<String,String>>();
			List<Map<String,String>> newList = new ArrayList<Map<String,String>>();
			if(old_pages!=null && old_pages.size() > 0){
				old_pages_str = old_pages.toString();
				leftStr = oldJson.substring(0,oldJson.indexOf(old_pages_str) + 1);
				rightStr = oldJson.substring(oldJson.indexOf(old_pages_str) + old_pages_str.length() - 1,oldJson.length());
				oldPages = old_pages.getJSONObject(0);//第一页（即表单页面）
				oldFormdata = oldPages.getJSONArray("formdata");//表单上的意见类型
				for(int i=0;i<oldFormdata.size();i++){
					JSONObject object = oldFormdata.getJSONObject(i);
					if(object != null){
						if(object.get("values") != null){
							JSONArray values = object.getJSONArray("values");
							if(values != null && values.size() > 0){
								String id = object.getString("id");
								for(int j=0;j<values.size();j++){
									JSONObject obj = values.getJSONObject(j);
									if(obj != null){
										String nodeId1 = obj.getString("nodeId");
										String userId1 = obj.getString("userId");
										String sign1 = obj.getString("sign");
										if(nodeId.equals(nodeId1) && userId.equals(userId1)){//获取本登录用户本节点的意见
											Map<String,String> map = new HashMap<String,String>();
											map.put("id", id);
											map.put("count", values.size() + "");
											map.put("nodeId", nodeId1);
											map.put("userId", userId1);
											map.put("sign", sign1);
											map.put("value", obj.toString());
											oldList.add(map);
										}
									}
								}
							}
						}
					}
				}
				oldFormdataNum = oldFormdata.size();
				old_pages_str = old_pages_str.substring(1, old_pages_str.length()-1);//去除左右中括号
			}
			JSONArray new_pages = null;
			if(currentJson != null && !"".equals(currentJson)){
				JSONObject new_obj = JSONObject.fromObject(currentJson);
				new_pages = new_obj.getJSONArray("pages");
				if(new_pages != null && new_pages.size() > 0){
					new_pages_str = new_pages.toString();
					leftStr = currentJson.substring(0,currentJson.indexOf(new_pages_str) + 1);
					rightStr = currentJson.substring(currentJson.indexOf(new_pages_str) + new_pages_str.length() - 1,currentJson.length());
					newPages = new_pages.getJSONObject(0);
					newFormdata = newPages.getJSONArray("formdata");
					for(int i=0;i<newFormdata.size();i++){
						JSONObject object = newFormdata.getJSONObject(i);
						if(object != null){
							if(object.get("values") != null){
								JSONArray values = object.getJSONArray("values");
								if(values != null && values.size() > 0){
									String id = object.getString("id");
									for(int j=0;j<values.size();j++){
										JSONObject obj = values.getJSONObject(j);
										if(obj != null){
											String nodeId1 = obj.getString("nodeId");
											String userId1 = obj.getString("userId");
											String sign1 = obj.getString("sign");
											if(nodeId.equals(nodeId1) && userId.equals(userId1)){//获取本登录用户本节点的意见
												Map<String,String> map = new HashMap<String,String>();
												map.put("id", id);
												map.put("count", values.size() + "");
												map.put("nodeId", nodeId1);
												map.put("userId", userId1);
												map.put("sign", sign1);
												map.put("value", obj.toString());
												newList.add(map);
											}
										}
									}
								}
							}
						}
					}
					newFormdataNum = newFormdata.size();
					new_pages_str = new_pages_str.substring(1, new_pages_str.length()-1);
				}
			}
			//---------------------先处理表单页面上的签批意见(只考虑一个节点上只能在一个意见类型中进行操作)----------------------
			//为了排序，已数据库最新数据为基础，做合并
			String pagesJson = "";
			if(newFormdataNum > oldFormdataNum){//表单上 新签批了 意见类型
				pagesJson = newPages.toString();
			}else if(newFormdataNum == oldFormdataNum){//在同一意见类型上做签批（会签的这种情况）
				oldJSON = oldPages.toString();
				json = newPages.toString();
				pagesJson =  oldPages.toString();
				if(json != null && !"".equals(json) && oldJSON != null && !"".equals(oldJSON)){
					JSONObject pages1 = JSONObject.fromObject(oldJSON);//需要修改的json
					JSONObject pages2 = JSONObject.fromObject(json);//用来比对的json
					JSONArray formdata1 = pages1.getJSONArray("formdata");
					JSONArray formdata2 = pages2.getJSONArray("formdata");
					for(int i=0;i<formdata1.size();i++){//循环每一个意见类型
						JSONObject object1 = formdata1.getJSONObject(i);
						if(object1 != null){
							String id1 = object1.getString("id");
							if(object1.get("values") != null){
								JSONArray valueArr1 = object1.getJSONArray("values");
								if(valueArr1 != null && valueArr1.size() > 0){
									String values1 = valueArr1.toString();
									if(id1 != null && !"".equals(id1)){
										if(formdata2 != null){
											for(int j=0;j<formdata2.size();j++){//比对每种意见类型
												JSONObject object2 = formdata2.getJSONObject(j);
												if(object2 != null){
													String id2 = object2.getString("id");
													if(id2 != null && !"".equals(id2)){
														if(id1.equals(id2)){//合并相同种类的意见类型
															if(object2.get("values") != null){
																JSONArray values2 = object2.getJSONArray("values");
																if(values2 !=null && values2.size() > 0){
																	if(values2.toString().equals("")){
																	}else if(!values1.equals(values2.toString())){
																		String addStr = "";
																		List<Map<String, String>> replaceList = new ArrayList<Map<String,String>>();
																		for(int z=0;z<values2.size();z++){
																			String value2Str = values2.getString(z);
																			if(values1.indexOf(value2Str) == -1){//需要合并的意见
																				//再判断一下 意见是否是修改来的
																				JSONObject obj2 = values2.getJSONObject(z);
																				String nodeId2 = null,processId2 = null,userId2 = null;
																				int sign2 = -1;
																				if(obj2 != null){
																					nodeId2 = obj2.getString("nodeId");
																					processId2 = obj2.getString("processId");
																					sign2 = obj2.getInt("sign");
																					userId2 = obj2.getString("userId");
																				}
																				int editfalg = 0;
																				for(int a1=0;a1<valueArr1.size();a1++){
																					JSONObject obj1 = valueArr1.getJSONObject(a1);
																					if(obj1 != null){
																						String nodeId1 = obj1.getString("nodeId");
																						String processId1 = obj1.getString("processId");
																						int sign1 = obj1.getInt("sign");
																						String userId1 = obj1.getString("userId");
																						if(nodeId1.equals(nodeId2) && processId1.equals(processId2) && sign1== sign2 && userId1.equals(userId2)){
																							editfalg++;
																							//说明是修改的意见
																							if(userId.equals(userId1)){//置换自己的
																								Map<String,String> map = new HashMap<String,String>();
																								map.put("replace1", obj1.toString());
																								map.put("replace2", value2Str);
																								replaceList.add(map);
																							}else{//这种情况不做处理（两个人同时修改同一意见类型）
																								
																							}
																						}
																					}
																				}
																				if(editfalg != 0){//修改意见
																					
																				}else{//新增意见
																					if(userId.equals(userId2)){
																						addStr += value2Str + ",";
																					}
																				}
																			}
																		}
																		List<Map<String, String>> deleteList = new ArrayList<Map<String,String>>();
																		if(oldList != null && oldList.size() > 0){
																			for(Map<String,String> oldlistmap:oldList){
																				String old_id = oldlistmap.get("id");
																				String value = oldlistmap.get("value");
																				if(old_id.equals(id2)){
																					String old_nodeId = oldlistmap.get("nodeId");
																					String old_userId = oldlistmap.get("userId");
																					String old_sign = oldlistmap.get("sign");
																					int deleteflag = 0;
																					if(newList != null && newList.size() > 0){
																						for(Map<String,String> newlistmap:newList){
																							String new_id = newlistmap.get("id");
																							if(new_id.equals(id2)){
																								String new_nodeId = newlistmap.get("nodeId");
																								String new_userId = newlistmap.get("userId");
																								String new_sign = newlistmap.get("sign");
																								if(old_nodeId.equals(new_nodeId) && old_userId.equals(new_userId) && old_sign.equals(new_sign)){
																									
																								}else{
																									deleteflag++;
																								}
																							}
																						}
																						if(deleteflag == newList.size()){
																							Map<String,String> deletemap = new HashMap<String,String>();
																							deletemap.put("delete", value);
																							deleteList.add(deletemap);
																						}
																					}else{
																						Map<String,String> deletemap = new HashMap<String,String>();
																						deletemap.put("delete", value);
																						deleteList.add(deletemap);
																					}
																				}
																			}
																		}
																		if (!("").equals(addStr) && addStr.length() > 1) {
																			addStr = addStr.substring(0, addStr.length() - 1);
																			String strStart = pagesJson.substring(0,pagesJson.indexOf(values1));
																			String strEnd = pagesJson.substring(pagesJson.indexOf(values1) + values1.length(),pagesJson.length());
																			String middle =  values1.substring(0,values1.length()-1) + "," + addStr + "]";
																			pagesJson = strStart + middle + strEnd;
																		}
																		if(replaceList != null && replaceList.size() > 0){
																			for(Map<String,String> map:replaceList){
																				String replace1 = map.get("replace1");
																				String replace2 = map.get("replace2");
																				pagesJson = pagesJson.replace(replace1, replace2);
																			}
																		}
																		if(deleteList != null && deleteList.size() > 0){
																			for(Map<String,String> map:deleteList){
																				String delete = map.get("delete");
																				String str1 = "," + delete + ",";
																				String str2 = delete + ",";
																				String str3 = "," + delete;
																				String str4 = delete;
																				if(pagesJson.indexOf(str1) > -1){
																					pagesJson = pagesJson.replace(str1, ",");
																				}
																				if(pagesJson.indexOf(str2) > -1){
																					pagesJson = pagesJson.replace(str2, "");
																				}
																				if(pagesJson.indexOf(str3) > -1){
																					pagesJson = pagesJson.replace(str3, "");
																				}
																				if(pagesJson.indexOf(str4) > -1){
																					String left = pagesJson.substring(0,pagesJson.indexOf(delete)-11);
																					String right = pagesJson.substring(pagesJson.indexOf(delete) + delete.length() + 1,pagesJson.length());
																					pagesJson = left + right;
																				}
																			}
																		}
																	}
																}
															}else{//页面中本意见类型为空
																List<Map<String, String>> deleteList = new ArrayList<Map<String,String>>();
																if(oldList != null && oldList.size() > 0){
																	for(Map<String,String> oldlistmap:oldList){
																		String old_id = oldlistmap.get("id");
																		String count = oldlistmap.get("count");
																		String value = oldlistmap.get("value");
																		if(old_id.equals(id2)){
																			Map<String,String> deletemap = new HashMap<String,String>();
																			deletemap.put("count", count);
																			deletemap.put("delete", value);
																			deleteList.add(deletemap);
																		}
																	}
																}
																if(deleteList != null && deleteList.size() > 0){
																	for(Map<String,String> map:deleteList){
																		String delete = map.get("delete");
																		String count = map.get("count");
																		if(count.equals("2")){
																			String str = object1.toString();
																			String str1 = "," + str + ",";
																			String str2 = str + ",";
																			String str3 = "," + str;
																			if(pagesJson.indexOf(str1) > -1){
																				pagesJson = pagesJson.replace(str1, ",");
																			}
																			if(pagesJson.indexOf(str2) > -1){
																				pagesJson = pagesJson.replace(str2, "");
																			}
																			if(pagesJson.indexOf(str3) > -1){
																				pagesJson = pagesJson.replace(str3, "");
																			}
																		}else{
																			String str1 = "," + delete + ",";
																			String str2 = delete + ",";
																			String str3 = "," + delete;
																			if(pagesJson.indexOf(str1) > -1){
																				pagesJson = pagesJson.replace(str1, ",");
																			}
																			if(pagesJson.indexOf(str2) > -1){
																				pagesJson = pagesJson.replace(str2, "");
																			}
																			if(pagesJson.indexOf(str3) > -1){
																				pagesJson = pagesJson.replace(str3, "");
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}else{//这种情况，是由于页面中删除了意见，然后暂存的
								//此种情况应该是对该意见类型进行的操作，不会去操作其他意见类型
								pagesJson = newPages.toString();
							}
						}
					}	
				}
			} else {//不考虑同时打开表单页面，但是在不同意见类型中签批意见的这种情况
				//只考虑表单上删除了某个意见类型
				pagesJson = newPages.toString();
			}
			//---------------------处理附件上的拉框意见----------------------
			String fjJson = "";
			if(new_pages.size() > 1){//说明页面附件上有拉框的意见
				if(old_pages.size() == 1){//数据库中无附件页面
					for(int i=1;i<new_pages.size();i++){//循环页面意见
						JSONObject newFjJsonObj = new_pages.getJSONObject(i);
						if(newFjJsonObj != null){
							fjJson += newFjJsonObj.toString() + ",";
						}
					}
				}else{
					//-----------第一步，处理多出来的附件页意见--------------------
					for(int i=1;i<new_pages.size();i++){//循环页面
						JSONObject obj_pages1 = new_pages.getJSONObject(i);
						int pagenum1 = obj_pages1.getInt("page");
						int addpagenum = 0;
						for(int j=1;j<old_pages.size();j++){
							JSONObject obj_pages2 = old_pages.getJSONObject(j);
							int pagenum2 = obj_pages2.getInt("page");
							if(pagenum1 != pagenum2){
								addpagenum++;
							}
						}
						if(addpagenum == old_pages.size() -1){//新增了附件页意见
							fjJson += obj_pages1.toString() + ",";
						}
					}
					//-----------第二步，处理同一附件页上的意见--------------------
					for(int j=1;j<old_pages.size();j++){
						JSONObject obj_pages1 = old_pages.getJSONObject(j);
						String pagevalue = obj_pages1.toString();
						String pages1Str = obj_pages1.toString();
						int pagenum1 = obj_pages1.getInt("page");
						for(int i=1;i<new_pages.size();i++){
							JSONObject obj_pages2 = new_pages.getJSONObject(i);
							int pagenum2 = obj_pages2.getInt("page");
							if(pagenum1 == pagenum2){
								String process1 = obj_pages1.getString("processes");
								JSONArray process2 = obj_pages2.getJSONArray("processes");
								if(process2 != null && process2.size() > 0){
									String addStr = "";
									for(int z=0;z<process2.size();z++){
										if(process1.indexOf(process2.getString(z)) == -1){//新的拉框意见
											addStr += process2.getString(z) + ",";
										}
									}
									if(!("").equals(addStr) && addStr.length() > 1){
										addStr = addStr.substring(0, addStr.length() - 1);
										String strStart = pages1Str.substring(0,pages1Str.indexOf(process1));
										String strEnd = pages1Str.substring(pages1Str.indexOf(process1) + process1.length(),pages1Str.length());
										String middle = process1.substring(0,process1.length()-1) + "," + addStr + "]";
										pagevalue = strStart + middle + strEnd;
									}
								}
							}
						}
						fjJson += pagevalue + ",";
					}
				}
			} else {//表单附件上没有拉框意见
				if(old_pages.size() > 1){//但是数据库中已经存在了附件拉框意见
					for(int i=1;i<old_pages.size();i++){//循环数据库意见
						JSONObject oldFjJsonObj = old_pages.getJSONObject(i);
						if(oldFjJsonObj != null){
							fjJson += oldFjJsonObj.toString() + ",";
						}
					}
				}
			}
			if (!("").equals(fjJson) && fjJson.length() > 1) {
				fjJson = fjJson.substring(0, fjJson.length() - 1);
			}
			if("".equals(pagesJson) && "".equals(fjJson)){//有意见的话，不至于这样
				newJSON = leftStr + rightStr;
			}else if(!"".equals(pagesJson) && "".equals(fjJson)){
				newJSON = leftStr + pagesJson + rightStr;
			}else if("".equals(pagesJson) && !"".equals(fjJson)){
				newJSON = leftStr + fjJson + rightStr;
			}else if(!"".equals(pagesJson) && !"".equals(fjJson)){
				newJSON = leftStr + pagesJson + "," + fjJson + rightStr;
			}
		} else {
			newJSON = currentJson;
		}
		System.out.println("修改后的json:" + newJSON);
		entity.setTrueJson(newJSON);
		return entity;
	}

	@Override
	public List<TrueJson> findTrueJsonListByInstanceId(String instanceId) {
		return trueJsonDao.findTrueJsonListByInstanceId(instanceId);
	}

	@Override
	public TrueJson findNewestTrueJson(String instanceId) {
		return trueJsonDao.findTrueJsonForDofile(instanceId);
	}

	@Override
	public TrueJson findNewestTrueJsonByProcessId(String processId) {
		return trueJsonDao.findNewestTrueJsonByProcessId(processId);
	}

	@Override
	public void saveTrueJsonLog(TrueJsonLog log) {
		try {
			trueJsonDao.saveTrueJsonLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveTrueJsonLog(TrueJson log){
		TrueJsonLog entity = new TrueJsonLog();
		entity.setInstanceId(log.getInstanceId());
		entity.setProcessId(log.getProcessId());
		entity.setUserId(log.getUserId());
		entity.setTrueJson(log.getTrueJson());
		entity.setExcute(log.getExcute());
		entity.setReadOrWrite("1");
		entity.setReadOrWriteDate(new Date());
		try {
			trueJsonDao.saveTrueJsonLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int findTrueJsonLogCount(String contionSql) {
		return trueJsonDao.findTrueJsonLogCount(contionSql);
	}

	@Override
	public List<TrueJsonLog> findTrueJsonLogList(String contionSql,Integer pageindex, Integer pagesize) {
		return trueJsonDao.findTrueJsonLogList(contionSql, pageindex, pagesize);
	}
	
	@Override
	public List<TrueJsonLog> getTrueJsonByParams(Map<String, String> map){
		return trueJsonDao.getTrueJsonByParams(map);
	}

	@Override
	public int findDelFileLogCount(String contionSql) {
		return trueJsonDao.findDelFileLogCount(contionSql);
	}

	@Override
	public List<TrueJsonLog> findDelFileLogList(String contionSql,
			Integer pageindex, Integer pagesize) {
		return trueJsonDao.findDelFileLogList(contionSql, pageindex, pagesize);
	}

	@Override
	public TrueJson findNewestTrueJsonByInstanceId(String instanceId) {
		return trueJsonDao.findNewsetTrueJsonByInstanceId(instanceId);
	}

}
