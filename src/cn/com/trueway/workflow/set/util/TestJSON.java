package cn.com.trueway.workflow.set.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

import cn.com.trueway.base.util.FileUtils;

public class TestJSON {

	public JSONObject regeneJSON(String commonJSON,JSONObject jo){
		 HttpClient client = new HttpClient();
		 int startY = jo.getInt("startY");
		 int startX = jo.getInt("startX");
		 PostMethod post = new PostMethod("http://localhost:9898/?ox="+startX+"&oy="+startY);
		 post.setRequestHeader("Content-Type","application/json;charset=utf-8");
		 StringBuffer sb = new StringBuffer();
		 post.setRequestBody("["+commonJSON+"]");
		try {
			int  state =   client.executeMethod(post);
			if(state == 200){
				
				BufferedReader br = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), "UTF-8"));
				String data = null;
				while ((data = br.readLine()) != null) {
					sb.append(data);
				}
				br.close();
				post.abort();
				Runtime.getRuntime().gc();
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject js = 	JSONObject.fromObject(sb.toString());
		return js;
	}
	
	public static void main(String[] args) {
		JSONObject jo= new JSONObject();
		jo.put("startY", 753);
		jo.put("startX", 234);
		TestJSON test = new TestJSON();
		String filePath = "D://node//111111.txt";
		String json = FileUtils.read(filePath);
		int yjHeight = 0;
		JSONObject newJO = test.regeneJSON(json,jo);
		JSONObject js = null;
		if(newJO != null && newJO.containsKey("Data")){
			Object object = newJO.containsKey("Error")?newJO.get("Error"):"";
			if(object== JSONNull.getInstance() || object =="null" || object ==null ){
				String ss = newJO.getJSONObject("Data").getString("JSON");
				js = JSONObject.fromObject(ss);
				yjHeight = newJO.getJSONObject("Data").getInt("MaxY")-jo.getInt("startY")+20;
			}
		}
		String jss = SplitJSONPage.splitPage(js.toString(),1336);
		/*JSONObject js = null;
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
								if(data.getDouble("SignY")>700){
									tempData.add(data);
								}
							}else{
								JSONArray points = data.getJSONArray("points");
								JSONArray tempPoints = new JSONArray();
								for(int k = points.size()-1; k>=0; k--){
									JSONObject point = points.getJSONObject(k);
									if(point.getDouble("cy")>700){
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
					System.out.println(jpage.toString());
				}else if(m==1){
					Set<Integer> processIndexSet = new HashSet<Integer>();
					Set<Integer> dataIndexSet = new HashSet<Integer>();
					Set<Integer> pointIndexSet = new HashSet<Integer>();
					JSONArray processes  = jpage.getJSONArray("processes");
					for(int i = 0 ; i < processes.size(); i++){
						// 遍历 data
						JSONArray datas = processes.getJSONObject(i).getJSONArray("datas");
						
						for( int j = 0 ; j < datas.size() ; j++){
							
							JSONObject data = datas.getJSONObject(j);
							if(data.getString("type").equals("text")){
								if(data.getDouble("SignY")< 700){
									dataIndexSet.add(j);
								}else{
									data.put("SignY", data.getDouble("SignY")-700);
									data.put("startY", ""+(Double.valueOf(data.getString("startY"))-700)+"");
								}
							}else{
								
								JSONArray points = data.getJSONArray("points");
								for(int k = 0; k < points.size(); k++){
									JSONObject point = points.getJSONObject(k);
									if(point.getDouble("cy")< 700){
										pointIndexSet.add(k);
									}else{
										point.put("cy", ""+(Double.valueOf(point.getString("cy"))-700)+"");
									}
								}
								if(pointIndexSet.size() == points.size()){
									dataIndexSet.add(j);
								}
								Integer[] its = {};
								its = pointIndexSet.toArray(its);
								for(int p = its.length-1; p>=0; p--){
									points.remove((int)its[p]);
								}
							}
							
						}
						if(dataIndexSet.size() == datas.size()){
							processIndexSet.add(i);
						}
						Integer[] its = {};
						its = dataIndexSet.toArray(its);
						for(int p = its.length-1; p>=0; p--){
							datas.remove((int)its[p]);
						}
						
					}
					Integer[] its = {};
					its =  processIndexSet.toArray(its);
					for(int p = its.length-1; p>=0; p--){
						processes.remove((int)its[p]);
					}
					if(processIndexSet.size() == processes.size()){
						jpage.remove(1);
					}
				
				
				}
				
			}*/
		System.out.println(jss.toString());
		}
			
	
}
