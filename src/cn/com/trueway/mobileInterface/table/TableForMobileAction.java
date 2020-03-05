/**
 * 文件名称:TableForMobileAction.java
 * 作者:zhuxc<br>
 * 创建时间:2013-11-25 下午03:23:59
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.mobileInterface.table;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Pending;
import cn.com.trueway.workflow.core.service.PendingService;
import cn.com.trueway.workflow.core.service.TableInfoService;

/**
 * 描述： 对TableForMobileAction进行描述
 * 作者：zhuxc
 * 创建时间：2013-11-25 下午03:23:59
 */
public class TableForMobileAction extends BaseAction{
	
	private TableInfoService tableInfoService;
	private PendingService pendingService;
	
	public PendingService getPendingService() {
		return pendingService;
	}
	public void setPendingService(PendingService pendingService) {
		this.pendingService = pendingService;
	}
	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}
	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}
	
	//-工具1
	private JSONObject getJSONObject(){
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			return JSONObject.fromObject(new String (data,"UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(iStream!=null){
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	//-工具1
		private JSONArray getJSONArray(){
			InputStream iStream = null;
			try {
				iStream = getRequest().getInputStream();
				byte[] data = readStream(iStream);
				return JSONArray.fromObject(new String (data,"UTF-8"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(iStream!=null){
					try {
						iStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}
	
	//-工具2
	private byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;  
	}
	
	/**
	 * 得到已办列表(已不用)
	 * @return
	 */
	public void getOverListForMobile(){
		JSONObject jsonObject = getJSONObject(); 
		String itemIds = (String)jsonObject.get("itemIds"); 
		String type = (String)jsonObject.get("typeid");  
		//用户id
		String userId = (String)jsonObject.get("userId");
		Integer column = (String)jsonObject.get("column")==null?0:Integer.parseInt((String)jsonObject.get("column"));
		//页面显示的条数
		Integer pagesize = (String)jsonObject.get("pagesize")==null?10:Integer.parseInt((String)jsonObject.get("pagesize"));
		
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		// 打开流
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String jsonStr = "";
		try {
			String conditionSql = "";
			if(CommonUtil.stringNotNULL(itemIds)){
				itemIds = "'"+itemIds.replaceAll(";", "','")+"'";
				conditionSql = " and p.wf_item_uid in ("+ itemIds +")";
			}
			List<Pending> list= tableInfoService.getOverList(conditionSql,userId, column, pagesize,"1");
			
			String overListJson = JSONArray.fromObject(list).toString();
			//输出json
			jsonStr = "[\"success\":\"true\",{"+overListJson+"}]";
			out.print(jsonStr);
			out.close();
		} catch (Exception e) {
			out.print("[{\"success\":\"false\"}]");
			out.close();
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到公文待办、已办json
	 * void
	 * http://192.168.0.173:8083/trueWorkFlow_zwkj/tableForMobile_getDocListForMobile.do?itemIds=C9291098-C461-4AD5-A9E2-018263CCFE02
	 * 作者:zhuxc<br>
	 * 创建时间:2013-12-4 上午10:41:09
	 */
	public void getDocListForMobile(){
		// 打开流
		PrintWriter out = null;
		String jsonStr = "";
		try {
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			String mobileUrl = SystemParamConfigUtil.getParamValueByParam("mobileUrl");
			String serverUrl = mobileUrl+ getRequest().getContextPath();

			JSONObject jsonObject = getJSONObject(); 
			String itemIds = getRequest().getParameter("itemIds");
			String type = (String)jsonObject.get("type");  
			System.out.println(itemIds);
			String isQj = "";//是否是请假
			if(jsonObject.get("isqjlist")!=null){
				isQj = jsonObject.get("isqjlist").toString();
			}
			
			//用户id
			String userId = (String)jsonObject.get("userId");
			//页数ag
			Integer column = (String)jsonObject.get("column")==null?0:Integer.parseInt((String)jsonObject.get("column"));
			//页面显示的条数
			Integer pagesize = (String)jsonObject.get("pagesize")==null?10:Integer.parseInt((String)jsonObject.get("pagesize"));
			//待办
			if("0".equals(type)){
				//总条数
				int count = pendingService.getCountOfPending("",userId,"");
				jsonStr = pendingService.getPendListOfMobile(userId,count,column + 1,pagesize,"",itemIds,"",serverUrl, "");//待办json（根据itemId获得）
			}else if("1".equals(type)){//已办
				String conditionSql = "";
				if(CommonUtil.stringNotNULL(itemIds)){
					itemIds = "'"+itemIds.replaceAll(";", "','")+"'";
					conditionSql = " and p.wf_item_uid in ("+ itemIds +")";
				}
				List<Pending> list= tableInfoService.getOverList(conditionSql,userId, column, pagesize,"1");
				jsonStr = pendingService.getOverListOfMobile(userId, list, column, pagesize, "", itemIds,serverUrl);
			}
			if("1".equals(isQj)){//请假返回格式另做处理
				jsonStr = "{\"success\":\"true\",\"data\":"+jsonStr+"}";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//输出json
		out.print(jsonStr);
		out.close();
	}
	
	
	/**
	 * 得到我的请假单 列表
	 * @return
	 */
	public void getMyLeaveListForMobile(){
		// 打开流
		PrintWriter out = null;
		String jsonStr = "";
		try {
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			
			JSONObject jsonObject = getJSONObject(); 
			String itemIds = getRequest().getParameter("itemIds");
			System.out.println(itemIds);
			
			//用户id
			String userId = (String)jsonObject.get("userId");
			//页数ag
			Integer column = (String)jsonObject.get("column")==null?0:Integer.parseInt((String)jsonObject.get("column"));
			//页面显示的条数
			Integer pagesize = (String)jsonObject.get("pagesize")==null?10:Integer.parseInt((String)jsonObject.get("pagesize"));
			
			List<Pending> list = null;
			if(itemIds!=null&&!itemIds.equals("")){
				list= tableInfoService.getMyLeaveList("",userId, column, pagesize,itemIds,"");
				jsonStr = JSONArray.fromObject(list).toString()	;
			}else{
				jsonStr = "[]";
			}
			jsonStr = "{\"success\":\"true\",\"data\":"+jsonStr+"}";
		} catch (IOException e) {
			e.printStackTrace();
		}
		//输出json
		out.print(jsonStr);
		out.close();
		
	}
	
	
}
