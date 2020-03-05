package cn.com.trueway.workflow.set.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SplitJSONPage {

	public static String splitPage(String json , int maxHeight){
		JSONObject js = null;
		js = JSONObject.fromObject(json);
		JSONObject jpage = null;
		if(js.getJSONArray("pages").size()>0){
			JSONObject jpage0 = null;
			for(int m = 0; m<js.getJSONArray("pages").size(); m++){
				jpage = js.getJSONArray("pages").getJSONObject(m);
				if(m==0 &&jpage.containsKey("page")&& jpage.getInt("page")==0 ){
					jpage0 =JSONObject.fromObject(jpage.toString());
					jpage0.put("page", -1);
				}
			}
			if(jpage0 != null){
				js.getJSONArray("pages").add(0, jpage0);
			}
			for(int m = 0; m<js.getJSONArray("pages").size(); m++){
				jpage = js.getJSONArray("pages").getJSONObject(m);
				jpage.put("page", jpage.getInt("page")+1);
				// 遍历处理意见
				if(m==0){
					JSONArray processes  = jpage.getJSONArray("processes");
					for(int i = 0 ; i < processes.size(); i++){
						// 遍历 data
						JSONArray datas = processes.getJSONObject(i).getJSONArray("datas");
						JSONArray tempData = new JSONArray();
						for( int j = 0 ; j < datas.size() ; j++){
							
							JSONObject data = datas.getJSONObject(j);
							if(data.getString("type").equals("text")){
								if(data.getDouble("SignY")<=maxHeight){
									tempData.add(data);
								}
							}else{
								JSONArray points = data.getJSONArray("points");
								JSONArray tempPoints = new JSONArray();
								for(int k = points.size()-1; k>=0; k--){
									JSONObject point = points.getJSONObject(k);
									if(point.getDouble("cy")<=maxHeight){
										tempPoints.add(point);
									}
								}
								if(tempPoints.size()>0){
									data.remove("points");
									data.put("points", tempPoints);
									tempData.add(data);
								}else{
									// 没有需要新增的
									data.put("points", tempPoints);
								}
							}
						}
						if( tempData != null && tempData.size() >0){
							processes.getJSONObject(i).remove("datas");
							processes.getJSONObject(i).put("datas", tempData);
						}else{
							processes.getJSONObject(i).put("datas", tempData);
						}
					}
				}else if(m==1){
					JSONArray processes  = jpage.getJSONArray("processes");
					for(int i = 0 ; i < processes.size(); i++){
						// 遍历 data
						JSONArray datas = processes.getJSONObject(i).getJSONArray("datas");
						JSONArray tempData = new JSONArray();
						for( int j = 0 ; j < datas.size() ; j++){
							
							JSONObject data = datas.getJSONObject(j);
							if(data.getString("type").equals("text")){
								if(data.getDouble("SignY") > maxHeight){
									data.put("SignY", data.getDouble("SignY")-maxHeight);
									data.put("startY", ""+(Double.valueOf(data.getString("startY"))-maxHeight+data.getInt("height"))+"");
									tempData.add(data);
								}
							}else{
								JSONArray points = data.getJSONArray("points");
								JSONArray tempPoints = new JSONArray();
								for(int k = points.size()-1; k>=0; k--){
									JSONObject point = points.getJSONObject(k);
									if(point.getDouble("cy")>maxHeight){
										point.put("cy", ""+(Double.valueOf(point.getString("cy"))-maxHeight)+"");
										tempPoints.add(point);
									}
								}
								if(tempPoints.size()>0){
									data.remove("points");
									data.put("points", tempPoints);
									tempData.add(data);
								}else{
									// 没有需要新增的
									data.put("points", tempPoints);
								}
							}
						}
						if( tempData != null && tempData.size() >0){
							processes.getJSONObject(i).remove("datas");
							processes.getJSONObject(i).put("datas", tempData);
						}else{
							processes.getJSONObject(i).put("datas", tempData);
						}
					}
				}
				
			}
		}
	
		return js.toString();
	}
}
