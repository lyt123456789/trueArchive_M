package cn.com.trueway.document.business.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.JDBCBase;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.document.business.model.FreeSet;
import cn.com.trueway.document.business.model.ItemSelect;
import cn.com.trueway.document.business.service.FreeSetService;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.service.FieldInfoService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.TableInfoService;


/**
 * 
 * 描述：自定义查询<br>
 * 作者：张灵<br>
 * 创建时间：2016-1-27 下午05:12:18<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class FreeSetAction extends BaseAction{

	private static final long serialVersionUID = -591662927260414711L;
	
	private FreeSetService freeSetService;
	private ItemService itemService;
	private TableInfoService tableInfoService;
	private FieldInfoService fieldInfoService;
	public JDBCBase jdbcBase;

	public JDBCBase getJdbcBase() {
		return jdbcBase;
	}

	public void setJdbcBase(JDBCBase jdbcBase) {
		this.jdbcBase = jdbcBase;
	}

	public FreeSetService getFreeSetService() {
		return freeSetService;
	}


	public void setFreeSetService(FreeSetService freeSetService) {
		this.freeSetService = freeSetService;
	}


	public ItemService getItemService() {
		return itemService;
	}


	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}


	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}


	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}


	public FieldInfoService getFieldInfoService() {
		return fieldInfoService;
	}


	public void setFieldInfoService(FieldInfoService fieldInfoService) {
		this.fieldInfoService = fieldInfoService;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String freeSet(){
		
		//1 获取所有的事项
		List<WfItem> list = itemService.getAllWfItem();
		List<FreeSet> set = new ArrayList<FreeSet>();
		//2 根据事项获取关联的 业务表 	
		for(int i = 0 ; i < list.size() ; i++){
			FreeSet freeSet = new FreeSet();
			// 获取业务表
			freeSet.setItemId(list.get(i).getId());
			freeSet.setItemName(list.get(i).getVc_sxmc());
			if(i== 0){ // 只塞第一个的值 其他的 动态取值
				List<WfTableInfo> tables =	tableInfoService.getTableByLcid(list.get(i).getLcid());
				//3 根据业务表获取表字段
				for(int j = 0; j< tables.size(); j++){
					List<WfFieldInfo> fields=fieldInfoService.getAllFieldByXbTableId(tables.get(j).getId());
					//把字段名改为大写,用于页面标签元素和字段对应
					if (fields!=null) {
						for (int t = 0; t < fields.size(); t++) {
							if(fields.get(t).getVc_fieldname()!=null){
								fields.get(t).setVc_fieldname(fields.get(t).getVc_fieldname().toUpperCase());
							}
						}
					}
					tables.get(j).setFields(fields);
				}
				freeSet.setTables(tables);
				//根据id查询匹配关系
				Map col = new HashMap();
				col.put("item_id",list.get(i).getId());
				List<ItemSelect> selects = freeSetService.getResult(col);
				ItemSelect select = selects.size()>0?selects.get(0):null;
				
				if(select != null){
					freeSet.setSelect_condition(select.getSelect_condition());
					freeSet.setSelect_result(select.getSelect_result());
					freeSet.setSelect_orderBy(select.getSelect_orderBy());
				}
			}
			
			set.add(freeSet);
		}
		getRequest().setAttribute("set", set);
		return "freeSet";
	}
	
	//左侧事项栏
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String itemLeft(){
		
		//1 获取所有的事项
		List<WfItem> list = itemService.getAllWfItem();
				
		List<FreeSet> set = new ArrayList<FreeSet>();
		
		for(int i = 0 ; i < list.size() ; i++){
			FreeSet freeSet = new FreeSet();
			// 获取业务表
			freeSet.setItemId(list.get(i).getId());
			freeSet.setItemName(list.get(i).getVc_sxmc());
			if(i == 0){ // 只塞第一个的值 其他的 动态取值
				List<WfTableInfo> tables =	tableInfoService.getTableByLcid(list.get(i).getLcid());
				
				//3 根据业务表获取表字段
				for(int j = 0; j< tables.size(); j++){
					List<WfFieldInfo> fields=fieldInfoService.getAllFieldByXbTableId(tables.get(j).getId());
					//把字段名改为大写,用于页面标签元素和字段对应
					if (fields!=null) {
						for (int t = 0; t < fields.size(); t++) {
							if(fields.get(t).getVc_fieldname()!=null){
								fields.get(t).setVc_fieldname(fields.get(t).getVc_fieldname().toUpperCase());
							}
						}
					}
					tables.get(j).setFields(fields);
				}
				freeSet.setTables(tables);
				
				//根据id查询匹配关系
				Map col = new HashMap();
				col.put("item_id",list.get(i).getId());
				List<ItemSelect> selects = freeSetService.getResult(col);
				ItemSelect select = selects.size()>0?selects.get(0):null;
				
				if(select != null){
					freeSet.setSelect_condition(select.getSelect_condition());
					freeSet.setSelect_result(select.getSelect_result());
					freeSet.setSelect_orderBy(select.getSelect_orderBy());
				}
			}
			
			set.add(freeSet);
		}
		getRequest().setAttribute("set", set);
		return "itemLeft";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	//中间表拦
	public String tableMiddle(){
		
		//1根据事项id 获取事项
		String itemId = getRequest().getParameter("itemId");
		if(itemId != null && !itemId.equals("")){
			WfItem item = itemService.getItemById(itemId);
			//2 根据事项获取关联的 业务表 	
			FreeSet freeSet = new FreeSet();
			// 获取业务表
			freeSet.setItemId(item.getId());
			freeSet.setItemName(item.getVc_sxmc());
			
			//根据id查询匹配关系
			Map col = new HashMap();
			col.put("item_id",item.getId());
			List<ItemSelect> selects = freeSetService.getResult(col);
			ItemSelect select = selects.size()>0?selects.get(0):null;
			
			if(select != null){
				freeSet.setSelect_condition(select.getSelect_condition());
				freeSet.setSelect_result(select.getSelect_result());
				freeSet.setSelect_orderBy(select.getSelect_orderBy());
			}
			
			if(select != null){
				// 解析table
				String selectConditions = freeSet.getSelect_condition();
				//表名:中文名,英文名;表名:中文名,英文名
				String[] conditions = selectConditions == null? null:selectConditions.split(";");
				
				if(conditions != null){
					String[] tableNames = new String[conditions.length];
					for(int t = 0 ; t < conditions.length ; t++ ){
						String tableName = conditions[t].split(":")[0];
						tableNames[t] = tableName;
					}
					getRequest().setAttribute("selectTable", tableNames);
				}
				
				
			}
			List<WfTableInfo> tables =	tableInfoService.getTableByLcid(item.getLcid());

			//3 根据业务表获取表字段
			for(int j = 0; j< tables.size(); j++){
				List<WfFieldInfo> fields=fieldInfoService.getAllFieldByXbTableId(tables.get(j).getId());
				//把字段名改为大写,用于页面标签元素和字段对应
				if (fields!=null) {
					for (int t = 0; t < fields.size(); t++) {
						if(fields.get(t).getVc_fieldname()!=null){
							fields.get(t).setVc_fieldname(fields.get(t).getVc_fieldname().toUpperCase());
						}
					}
				}
				tables.get(j).setFields(fields);
			}
			freeSet.setTables(tables);
			getRequest().setAttribute("itemId", itemId);
			getRequest().setAttribute("tables", freeSet);
		}
		return "tableMiddle";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	//右边字段栏
	public String fieldRight(){
		
		String changeState = getRequest().getParameter("changeState");
		if(changeState == null || changeState.equals("") ){
			changeState = "0";
		}
		String itemId = getRequest().getParameter("itemId");
		if(itemId != null && !itemId.equals("")){
			WfItem item = itemService.getItemById(itemId);
			// 获取table ID
			String tableId = getRequest().getParameter("tableId");
			if(tableId != null && !tableId.equals("")){
				WfTableInfo tableInfo = tableInfoService.getTableById(tableId);
				if(tableInfo != null){
					getRequest().setAttribute("tableName", tableInfo.getVc_tablename());
				}
				List<WfFieldInfo> fields=fieldInfoService.getAllFieldByXbTableId(tableId);
				//把字段名改为大写,用于页面标签元素和字段对应
				if (fields!=null) {
					for (int t = 0; t < fields.size(); t++) {
						if(fields.get(t).getVc_fieldname()!=null){
							fields.get(t).setVc_fieldname(fields.get(t).getVc_fieldname().toUpperCase());
						}
					}
				}
				getRequest().setAttribute("fields", fields);
			}
			//根据id查询匹配关系
			Map col = new HashMap();
			col.put("item_id",item.getId());
			List<ItemSelect> selects = freeSetService.getResult(col);
			ItemSelect select = selects.size()>0?selects.get(0):null;
			
			if(select != null&&"0".equals(changeState)){
				String[] conditions = select.getSelect_condition() == null ? null:select.getSelect_condition().split(";");
				String[] results = select.getSelect_result() == null ? null:select.getSelect_result().split(";");
				String[] orders = select.getSelect_orderBy()== null ? null:select.getSelect_orderBy().split(";");
				String[] conditionValues =select.getCondition_value()== null ? null: select.getCondition_value().split(",");
				String[] resultValues = select.getResult_value()== null ? null: select.getResult_value().split(",");
				String[] orderValues = select.getOrderBy_value()== null ? null:select.getOrderBy_value().split(",");
				getRequest().setAttribute("conditions", conditions);
				getRequest().setAttribute("conditionValues", conditionValues);
				getRequest().setAttribute("results", results);
				getRequest().setAttribute("resultValues", resultValues);
				getRequest().setAttribute("orders", orders);
				getRequest().setAttribute("orderValues", orderValues);
			}
			getRequest().setAttribute("changeState", changeState);
		}
		return "fieldRight";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addFree(){
		// 获取事项id
		String itemId = getRequest().getParameter("itemId");
		String condition = getRequest().getParameter("condition");
		String result = getRequest().getParameter("result");
		String order = getRequest().getParameter("order");
		String daiBanSql = getRequest().getParameter("daibansql");
		String yiBanSql = getRequest().getParameter("yibansql");
		String yiBanJieSql = getRequest().getParameter("yibanjiesql");

		String conditions = "";
		// 获取 查询结果 text
		String results = "";
		// 获取 排序text
		String orders = "";
		// 获取 查询条件 value
		String conditionValues = "";
		// 获取 查询结果 value
		String resultValues = "";
		// 获取 排序value
		String orderValues = "";
		// 先根据itemId 查询
		// 判断 itemId 是否为null
		if(itemId != null && !itemId.equals("")){
			//根据id查询匹配关系
			Map col = new HashMap();
			col.put("item_id",itemId);
			List<ItemSelect> selects = freeSetService.getResult(col);
			ItemSelect select = selects.size()>0?selects.get(0):null;
			if(condition != null && !condition.equals("")){
				// 解析json
				JSONArray jCondition = JSONArray.fromObject(condition);
				for(int t = 0 ; t < jCondition.size() ; t++){
					JSONObject jo =jCondition.getJSONObject(t);
					if(jo != null && !"null".equals(jo.toString())){
						conditions += jo.getString("text")+";";
						conditionValues += jo.getString("value")+",";
					}
				}
				if(!conditions.equals("")){
					conditions = conditions.substring(0, conditions.length()-1);
				}
				if(!conditionValues.equals("")){
					conditionValues = conditionValues.substring(0, conditionValues.length()-1);
				}
			}
			if(result != null && !result.equals("")){
				JSONArray jResult = JSONArray.fromObject(result);
				for(int t = 0 ; t < jResult.size() ; t++){
					JSONObject jo =jResult.getJSONObject(t);
					if(jo != null && !"null".equals(jo.toString())){
						results += jo.getString("text")+";";
						resultValues += jo.getString("value")+",";
					}
				}
				if(!results.equals("")){
					results = results.substring(0, results.length()-1);
				}
				if(!resultValues.equals("")){
					resultValues = resultValues.substring(0, resultValues.length()-1);
				}
			}
			if(order != null && !order.equals("")){
				JSONArray jOrder = JSONArray.fromObject(order);
				for(int t = 0 ; t < jOrder.size() ; t++){
					JSONObject jo =jOrder.getJSONObject(t);
					if(jo != null && !"null".equals(jo.toString())){
						orders += jo.getString("text")+";";
						orderValues += jo.getString("value")+",";
					}
				}
				if(!orders.equals("")){
					orders = orders.substring(0, orders.length()-1);
				}
				if(!orderValues.equals("")){
					orderValues = orderValues.substring(0, orderValues.length()-1);
				}
			}
				
			// 获取 查询条件 text
			

			if(select != null){
				// update itemSelect
				select.setCondition_value(resultValues);
				select.setSelect_condition(conditions);
				select.setResult_value(resultValues);
				select.setSelect_result(results);
				select.setOrderBy_value(orderValues);
				select.setSelect_orderBy(orders);
				select.setDaiBanSql(daiBanSql);
				select.setYiBanJieSql(yiBanJieSql);
				select.setYiBanSql(yiBanSql);
				freeSetService.updateItemSelect(select);
			}else{
				select = new ItemSelect();
				select.setItem_id(itemId);
				select.setCondition_value(resultValues);
				select.setSelect_condition(conditions);
				select.setResult_value(resultValues);
				select.setSelect_result(results);
				select.setOrderBy_value(orderValues);
				select.setSelect_orderBy(orders);
				select.setDaiBanSql(daiBanSql);
				select.setYiBanJieSql(yiBanJieSql);
				select.setYiBanSql(yiBanSql);
				// add itemSelect
				freeSetService.addItemSelect(select);
			}
		}
	}
	
	//sql生成栏
	@SuppressWarnings("unchecked")
	public String sqlBottom(){
		String itemId = getRequest().getParameter("itemId");
		
		//根据id查询匹配关系
		@SuppressWarnings("rawtypes")
		Map col = new HashMap();
		col.put("item_id",itemId);
		List<ItemSelect> selects = freeSetService.getResult(col);
		ItemSelect select = selects.size()>0?selects.get(0):null;
		
		getRequest().setAttribute("daiBanSql", select==null?"":select.getDaiBanSql());
		getRequest().setAttribute("yiBanSql", select==null?"":select.getYiBanSql());
		getRequest().setAttribute("yiBanJieSql", select==null?"":select.getYiBanJieSql());
		return "sqlBottom";
		
	}
	
	//预览	返回数据
	@SuppressWarnings("unchecked")
	public String preview(){
		String itemId = getRequest().getParameter("itemId");		
		String type = getRequest().getParameter("type");
		String preview = getRequest().getParameter("preview");
		
		if(itemId==null){
			List<WfItem> itemList = itemService.getAllWfItem();
			itemId =  itemList.get(0).getId();
		}
		
		//根据id查询匹配关系
		Map col = new HashMap();
		col.put("item_id",itemId);
		List<ItemSelect> selects = freeSetService.getResult(col);
		ItemSelect select = selects.size()>0?selects.get(0):new ItemSelect();
		
		String sql = "";
		if("yiban".equals(type))
			sql = select.getYiBanSql();
		else if("yibanjie".equals(type))
			sql = select.getYiBanJieSql();
		else{
			sql = select.getDaiBanSql();
			type = "daiban";
		}
		if(sql==null){
			List<WfItem> itemList = itemService.getAllWfItem();			
			getRequest().setAttribute("itemList", itemList);
		    getRequest().setAttribute("itemId", itemId);
		    getRequest().setAttribute("type", type);			
		    return "freeView";
		}else
			sql = sql.toUpperCase();
		
				//重构sql
				Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
				String userId = employee.getEmployeeGuid();
		
				sql = sql.replace("{P.USERID.VALUE}", userId);		
		
				String noVauleSql = new String(sql);
				while(noVauleSql.indexOf(".VALUE}")>=0){
					String value = noVauleSql.substring(noVauleSql.indexOf("%{T"),noVauleSql.indexOf(".VALUE}%")+8);
					noVauleSql = noVauleSql.replace(value, "%%");
				}
				String proSql = noVauleSql.replaceFirst("SELECT ", "select p.wf_process_uid as process_id,p.wf_item_uid as item_id,p.wf_form_id as form_id,".toUpperCase());
				//获取结果集
				List<Object[]> resultList = freeSetService.getResultBySql(proSql);
			
				List<List<String>> list = new ArrayList<List<String>>();
				for(Object[] str:resultList){
					List<String> newstrs = new ArrayList<String>();
					for(Object string:str){
						String newstr = (String)string==null?"":(String)string;
						newstrs.add(newstr);
					}
					list.add(newstrs);
				}
				
				//获取结果集列名
				String colsName = "";
				
				PreparedStatement pstmt=null;
			    ResultSet rs=null;
			    try {
				Connection conn = jdbcBase.getCon();
			    pstmt = conn.prepareStatement(noVauleSql);
			    rs = pstmt.executeQuery();
			    ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				for(int i=1;i<=count;i++)
					colsName = colsName + rsmd.getColumnName(i)+",";
				if(colsName.length()>0)
					colsName = colsName.substring(0,colsName.length()-1);
				} catch (SQLException e) {
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
			    
			  
				WfItem item = itemService.getItemById(itemId);
				List<WfTableInfo> tables =	tableInfoService.getTableByLcid(item.getLcid());
				
				String conName = "";
				String conEnName = "";				
						// 根据业务表获取表字段
						for(int j = 0; j< tables.size(); j++){
							List<WfFieldInfo> fields=fieldInfoService.getAllFieldByXbTableId(tables.get(j).getId());
							String tableName = tables.get(j).getVc_tablename();
							String tname = "";
							int v = sql.indexOf(tableName);
							if(v>=0){
								String after = sql.substring(v+tableName.length()).trim();
								tname = after.substring(0, after.indexOf(" "));
								}
							if (fields!=null) {
								for (int t = 0; t < fields.size(); t++) {		
									String enName = "{"+tname+"."+fields.get(t).getVc_fieldname().toUpperCase()+".VALUE}";
									String zhName = fields.get(t).getVc_name();
									
									//设置jsp搜索框
									if(sql.indexOf(enName)>=0){
										conName = conName + zhName+",";	
										conEnName = conEnName + enName+",";
									}
									
									//设置jsp表头
									String reName = fields.get(t).getVc_fieldname().toUpperCase();;
									if(colsName.indexOf(reName)>=0){
										colsName = colsName.replace(reName, zhName);
									}
								}
								if(conName.length()>0)
									conName = conName.substring(0,conName.length()-1);
								if(conEnName.length()>0)
									conEnName = conEnName.substring(0,conEnName.length()-1);

							}
						}
			   
				List<WfItem> itemList = itemService.getAllWfItem();
				
				getRequest().setAttribute("preview", preview);
				getRequest().setAttribute("itemList", itemList);
			    getRequest().setAttribute("itemId", itemId);
			    getRequest().setAttribute("type", type);			
			    getRequest().setAttribute("conName", conName);
			    getRequest().setAttribute("conEnName", conEnName);
			    getRequest().setAttribute("colsName", colsName);
			    getRequest().setAttribute("list", list);
					
		return "freeView";
			
	}
	
	//按查询条件返回数据
	public String getTableData(){
		String itemId = getRequest().getParameter("itemId");
		String type = getRequest().getParameter("type");
		String colsName = getRequest().getParameter("colsName");
		String conName = getRequest().getParameter("conName");
		String conEnName = getRequest().getParameter("conEnName");
		
		String[] inputName = conEnName.split(",");
		String[] condition = new String[inputName.length];
		String conditionValue = "";
		
		for(int i=0;i<inputName.length;i++){
			condition[i] = getRequest().getParameter(inputName[i]);
			conditionValue = conditionValue + condition[i]+",";
		}
		
		if(inputName.length>0)
			conditionValue = conditionValue.substring(0,conditionValue.length()-1);

		//根据id查询匹配关系
		Map col = new HashMap();
		col.put("item_id",itemId);
		List<ItemSelect> selects = freeSetService.getResult(col);
		ItemSelect select = selects.size()>0?selects.get(0):null;
		
		String sql = "";
		if("yiban".equals(type))
			sql = select.getYiBanSql();
		else if("yibanjie".equals(type))
			sql = select.getYiBanJieSql();
		else{
			sql = select.getDaiBanSql();
			type = "daiban";
		}
		if(sql==null){
		    getRequest().setAttribute("itemId", itemId);
		    getRequest().setAttribute("type", type);			
		    return "freeView";
		}else
			sql = sql.toUpperCase();
		
		//重构sql
				Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
				String userId = employee.getEmployeeGuid();

				sql = sql.replace("{P.USERID.VALUE}", userId);		

				String noVauleSql = new String(sql);
				for(int i=0;i<inputName.length;i++){
					noVauleSql = noVauleSql.replace(inputName[i], condition[i]);
				}
				
				while(noVauleSql.indexOf(".VALUE}")>=0){
					String value = noVauleSql.substring(noVauleSql.indexOf("%{T"),noVauleSql.indexOf(".VALUE}%")+8);
					noVauleSql = noVauleSql.replace(value, "%%");
				}
				
				String proSql = noVauleSql.replaceFirst("SELECT ", "select p.wf_process_uid as process_id,p.wf_item_uid as item_id,p.wf_form_id as form_id,".toUpperCase());
				//获取结果集
				List<Object[]> resultList = freeSetService.getResultBySql(proSql);
	
		List<List<String>> list = new ArrayList<List<String>>();
		for(Object[] str:resultList){
			List<String> newstrs = new ArrayList<String>();
			for(Object string:str){
				String newstr = (String)string==null?"":(String)string;
				newstrs.add(newstr);
			}
			list.add(newstrs);
		}
		
		List<WfItem> itemList = itemService.getAllWfItem();
		
		getRequest().setAttribute("itemList", itemList);
		getRequest().setAttribute("itemId", itemId);
		getRequest().setAttribute("type", type);			
		getRequest().setAttribute("conName", conName);
		getRequest().setAttribute("conEnName", conEnName);
		getRequest().setAttribute("conditionValue", conditionValue);
		getRequest().setAttribute("colsName", colsName);
		getRequest().setAttribute("list", list);
		return "freeView";	
	}


	public void setWidth(){
		String itemId = getRequest().getParameter("itemId");
		String type = getRequest().getParameter("type");
		String width = getRequest().getParameter("width");
		
		//根据id查询匹配关系
		Map col = new HashMap();
		col.put("item_id",itemId);
		List<ItemSelect> selects = freeSetService.getResult(col);
		ItemSelect select = selects.size()>0?selects.get(0):null;
		
		String newWidth = "";
		if(select.getWidth()==null){
			if("yiban".equals(type))
				newWidth = "daiban;"+width+";yibanjie";
			else if("yibanjie".equals(type))
				newWidth = "daiban;yiban;"+width;
			else
				newWidth = width+";yiban;yibanjie";
		}else{
			if("yiban".equals(type))
				newWidth = select.getWidth().split(";")[0]+";"+width+";"+select.getWidth().split(";")[2];
			else if("yibanjie".equals(type))
				newWidth = select.getWidth().split(";")[0]+";"+select.getWidth().split(";")[1]+";"+width;
			else
				newWidth = width+";"+select.getWidth().split(";")[1]+";"+select.getWidth().split(";")[2];
		}
		
		select.setWidth(newWidth);
		freeSetService.updateItemSelect(select);
	}
	
	public void checkSql(){
		String itemId = getRequest().getParameter("itemId");
		String type = getRequest().getParameter("type");
		
		//根据id查询匹配关系
		Map col = new HashMap();
		col.put("item_id",itemId);
		List<ItemSelect> selects = freeSetService.getResult(col);
		ItemSelect select = selects.size()>0?selects.get(0):null;
				
		String sql = "";
		if("yiban".equals(type))
			sql = select.getYiBanSql().toUpperCase();
		else if("yibanjie".equals(type))
			sql = select.getYiBanJieSql().toUpperCase();
		else
			sql = select.getDaiBanSql().toUpperCase();

		//重构sql
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = employee.getEmployeeGuid();

		sql = sql.replace("{P.USERID.VALUE}", userId);		

		String noVauleSql = new String(sql);
		while(noVauleSql.indexOf(".VALUE}")>=0){
			String value = noVauleSql.substring(noVauleSql.indexOf("%{T"),noVauleSql.indexOf(".VALUE}%")+8);
			noVauleSql = noVauleSql.replace(value, "%%");
		}
		String proSql = noVauleSql.replaceFirst("SELECT ", "select p.wf_process_uid as process_id,p.wf_item_uid as item_id,p.wf_form_id as form_id,".toUpperCase());
		//获取结果集
		List<Object[]> resultList = freeSetService.getResultBySql(proSql);
	}
}
