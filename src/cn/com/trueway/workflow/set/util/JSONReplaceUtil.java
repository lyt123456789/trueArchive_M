package cn.com.trueway.workflow.set.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 描述：手写意见入库部分只替换
 * 作者：蔡亚军
 * 创建时间：2015-1-4 下午3:41:28
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class JSONReplaceUtil {
	/**
	 * 描述：将json 值进行替换
	 * @param json
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2014-9-16 下午4:20:45
	 */
	public static String replaceJson(String json){
		// 将新写的意见改成只读
		if(json == null){
			json = "";
		}
		json = json.replaceAll("\"isWrite\":1", "\"isWrite\":0")
				.replaceAll("\"isSignWrite\":1", "\"isSignWrite\":0")
				.replaceAll("\"isSignWrite\":\"1\"","\"isSignWrite\":0"); // 上面两个出来 pc 和 移动端不统一的问题 意见签名是否可以移动
		// c 端不支持 生成空的array 替换 为[]
		json = json.replace("\"resources\":[\"\"]", "\"resources\":[]");
		// 移动端上传文件 是否是新文件 改为false;
		json = json.replace("\"newfile\":\"true\"", "\"newfile\":\"false\"");
		// 处理意见 中 中文字符
		json = json.replace("'", "‘");
		return json;
	}
	
	/**
	 * 描述：将json 值进行替换
	 * @param json
	 * @return String
	 *
	 * 作者:Yuxl
	 * 创建时间:2014-9-16 下午4:20:45
	 */
	public static String replaceJson2(String json){
		// 将新写的意见改成只读
		if(json == null){
			json = "";
		}
		json = json.replaceAll("\"isSignWrite\":1", "\"isSignWrite\":0")
				.replaceAll("\"isSignWrite\":\"1\"","\"isSignWrite\":0"); // 上面两个出来 pc 和 移动端不统一的问题 意见签名是否可以移动
		// c 端不支持 生成空的array 替换 为[]
		json = json.replace("\"resources\":[\"\"]", "\"resources\":[]");
		// 移动端上传文件 是否是新文件 改为false;
		json = json.replace("\"newfile\":\"true\"", "\"newfile\":\"false\"");
		// 处理意见 中 中文字符
		json = json.replace("'", "‘");
		return json;
	}
	
	
	/**
	 * 
	 * 描述：提取新增的页面（手写或者拉框意见）
	 * @param json
	 * @param processId
	 * @return JSONArray
	 * 作者:蔡亚军
	 * 创建时间:2015-3-2 下午6:17:33
	 */
	public static JSONArray getJSONByProcessId(String json ,String processId){
		if(json != null && !"".equals(json)){
			JSONArray retJa = new JSONArray();
			JSONArray jo = JSONObject.fromObject(json).getJSONArray("pages");
			if(jo != null && jo.size()>0){
				// add page
				for(int i = 0 ,size = jo.size();i < size; i++){
					if(jo.getJSONObject(i).containsKey("processes")){
						JSONArray processes = 	jo.getJSONObject(i).getJSONArray("processes");
						JSONArray newProcess = new JSONArray();
						// add process
						for(int j = 0, len = processes.size(); j < len; j++){
							if(processes.getJSONObject(j).get("processId").equals(processId)){
								newProcess.add(processes.getJSONObject(j));
							}
						}
						if(newProcess != null && !newProcess.isEmpty()){
							JSONObject jobject = new JSONObject();
							jobject.put("basicOS", jo.getJSONObject(i).get("basicOS"));
							jobject.put("company", jo.getJSONObject(i).get("company"));
							jobject.put("height", jo.getJSONObject(i).get("height"));
							jobject.put("isShow", jo.getJSONObject(i).get("isShow"));
							jobject.put("page", jo.getJSONObject(i).get("page"));
							jobject.put("version", jo.getJSONObject(i).get("version"));
							jobject.put("width", jo.getJSONObject(i).get("width"));
							jobject.put("processes", newProcess);
							retJa.add(jobject);
						}
					}
				}
			}
			return retJa;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * 描述：获取
	 * @param json
	 * @return JSONArray
	 * 作者:蔡亚军
	 * 创建时间:2015-3-2 下午6:19:44
	 */
	public static Map<Integer,JSONObject> getSealJson(String json){
		Map<Integer,JSONObject> map = new HashMap<Integer,JSONObject>();
		if(json != null && !"".equals(json)){
			JSONArray stamps = new JSONArray();
			JSONArray jo = JSONObject.fromObject(json).getJSONArray("pages");
			if(jo != null && jo.size()>0){
				// add page
				for(int i = 0 ,size = jo.size();i < size; i++){
					if(jo.getJSONObject(i).containsKey("stamps")){
						//stamps = jo.getJSONObject(i).getJSONArray("stamps");
						map.put(i, jo.getJSONObject(i));
					}
				}
			}
			return map;
		}else{
			return null;
		}
	}
	
	
	public static JSONArray getJSONByProcessId(String json ,String processId,boolean isNew){
		if(json != null && !"".equals(json)){
			JSONArray retJa = new JSONArray();
			JSONArray jo = JSONObject.fromObject(json).getJSONArray("pages");
			if(jo != null && jo.size()>0){
				// add page
				for(int i = 0 ,size = jo.size();i < size; i++){
					if(jo.getJSONObject(i).containsKey("processes")){
						JSONArray processes = 	jo.getJSONObject(i).getJSONArray("processes");
						JSONArray newProcess = new JSONArray();
						// add process
						for(int j = 0, len = processes.size(); j < len; j++){
							if(processes.getJSONObject(j).get("processId").equals(processId)){
								if(isNew){
									// 判断是否是新写的意见
									JSONArray datas = processes.getJSONObject(j).getJSONArray("datas");
									if(datas != null &&!datas.isEmpty()){
										if(datas.getJSONObject(0).containsKey("isWrite")&&datas.getJSONObject(0).getInt("isWrite")==1){
											newProcess.add(processes.getJSONObject(j));
										}else if(jo.getJSONObject(i).containsKey("basicOs")&&jo.getJSONObject(i).getString("basicOs").equalsIgnoreCase("ios")&& j != 0){
											newProcess.add(processes.getJSONObject(j));
										}
									}
								}else{
									newProcess.add(processes.getJSONObject(j));
								}
								
								
							}
						}
						if(newProcess != null && !newProcess.isEmpty()){
							JSONObject jobject = new JSONObject();
							jobject.put("basicOS", jo.getJSONObject(i).get("basicOS"));
							jobject.put("company", jo.getJSONObject(i).get("company"));
							jobject.put("height", jo.getJSONObject(i).get("height"));
							jobject.put("isShow", jo.getJSONObject(i).get("isShow"));
							jobject.put("page", jo.getJSONObject(i).get("page"));
							jobject.put("version", jo.getJSONObject(i).get("version"));
							jobject.put("width", jo.getJSONObject(i).get("width"));
							jobject.put("processes", newProcess);
							retJa.add(jobject);
						}
					}
					
				}
			}
			return retJa;
		}else{
			return null;
		}
		
	}
	
	public static String  setJSON(String oldJSON,JSONArray ja){
		JSONArray jo = JSONObject.fromObject(oldJSON).getJSONArray("pages");
		if(jo != null && !jo.isEmpty()){
			for(int i = 0, size = jo.size(); i < size ; i++){
				int page = jo.getJSONObject(i).getInt("page");
				for(int j = 0; j < ja.size() ; j++){
					if(page == ja.getJSONObject(j).getInt("page")){
						if(jo.getJSONObject(i).containsKey("processes")){
							 for(int t = 0 ; t< ja.getJSONObject(j).getJSONArray("processes").size();t++){
								 jo.getJSONObject(i).getJSONArray("processes").add(ja.getJSONObject(j).getJSONArray("processes").get(t));
							 }
						}else{
							 jo.getJSONObject(i).put("processes", ja.getJSONObject(j).getJSONArray("processes"));

						}
					}
				}
			}
		}
		JSONObject jsobject = JSONObject.fromObject(oldJSON);
		jsobject.put("pages", jo);
		return jsobject.toString();
	}
	
	
	/**
	 * 
	 * 描述：将意见JSONArray数组合并到原先的意见中,
	 * @param oldJSON
	 * @param ja
	 * @param imageCount	两者pdf的相差数(预防差异)
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2015-3-2 上午11:49:00
	 */
	public static String  setJSON(String oldJSON,JSONArray ja, Integer imageCount){
		JSONArray jo = JSONObject.fromObject(oldJSON).getJSONArray("pages");
		if(jo != null && !jo.isEmpty()){
			for(int i = 0, size = jo.size(); i < size ; i++){
				int page = jo.getJSONObject(i).getInt("page");
				for(int j = 0; j < ja.size() ; j++){
					if((page-imageCount) == ja.getJSONObject(j).getInt("page")){
						if(jo.getJSONObject(i).containsKey("processes")){
							 for(int t = 0 ; t< ja.getJSONObject(j).getJSONArray("processes").size();t++){
								 jo.getJSONObject(i).getJSONArray("processes").add(ja.getJSONObject(j).getJSONArray("processes").get(t));
							 }
						}else{
							 jo.getJSONObject(i).put("processes", ja.getJSONObject(j).getJSONArray("processes"));

						}
					}
				}
			}
		}
		JSONObject jsobject = JSONObject.fromObject(oldJSON);
		jsobject.put("pages", jo);
		return jsobject.toString();
	}
	
	
	public static String  setSealJSON(String oldJSON, Map<Integer,JSONObject> map, Integer imageCount){
		JSONArray retjo = JSONObject.fromObject(oldJSON).getJSONArray("pages");
		JSONArray jo = JSONObject.fromObject(oldJSON).getJSONArray("pages");
		JSONArray arr = null;
		JSONObject arr1 = null;
		int page = 0;
		for(Integer key : map.keySet()){
			arr1 = map.get(key);		//盖章内容的数组
			page = arr1.getInt("page");		//意见处于的页面
			arr =  arr1.getJSONArray("stamps");	//执行page上有意见
			if(arr!=null && arr.size()>0){
				for(int i=0; arr!=null && i<arr.size(); i++){
					JSONObject object = arr.getJSONObject(i);
					if(jo != null && jo.size()>0){		
						boolean exist = false;
						for(int j=0; j<jo.size(); j++){
							int pag = jo.getJSONObject(j).getInt("page");		//意见页面
							JSONArray stamp = null;
							try{
								stamp = jo.getJSONObject(j).getJSONArray("stamps");
							}catch(Exception e){
								
							}
							if(pag==(page+1)){
								if(stamp==null){
									JSONObject obj = getJSONObject(arr1, imageCount);
									JSONArray arrt = new JSONArray();
									arrt.add(object);
									obj.put("stamps", arrt);
									retjo.add(obj);
									break;
								}
								exist = true;
								if(stamp.contains(object)){
									//retjo.getJSONObject(j).getJSONArray("stamps").add(object);
								}else{	
									retjo.getJSONObject(j).getJSONArray("stamps").add(object);
								}
							}
						}
						if(!exist){	//不存在相关页面的任何数据
							JSONObject obj = getJSONObject(arr1, imageCount);
							JSONArray arrt = new JSONArray();
							arrt.add(object);
							obj.put("stamps", arrt);
							retjo.add(obj);
						}
					}else{	//原先中没有保存的意见
						JSONObject obj = getJSONObject(arr1, imageCount);
						JSONArray arrt = new JSONArray();
						arrt.add(object);
						obj.put("stamps", arrt);
						retjo.add(obj);
					}
				}
			}
		}
		JSONObject jsobject = JSONObject.fromObject(oldJSON);
		jsobject.put("pages", retjo);
		return jsobject.toString();
	}
	
	
	public static JSONObject getJSONObject(JSONObject arr1, Integer imageCount){
		JSONObject jobject = new JSONObject();
		jobject.put("basicOS", arr1.get("basicOS"));
		jobject.put("company", arr1.get("company"));
		jobject.put("height", arr1.get("height"));
		jobject.put("isShow", arr1.get("isShow"));
		jobject.put("page", Integer.parseInt(arr1.get("page").toString())+imageCount);
		jobject.put("version", arr1.get("version"));
		jobject.put("width", arr1.get("width"));
		return jobject;
	}
	
	
	
	/**
	 * 将当前步骤的意见编辑修改为只读
	 * @param json
	 * @param processId
	 * @param instanceId
	 * @param nodeId
	 * @param userId
	 * @return
	 */
	public static String editCommentWriteRole(String json, String processId,
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
}
