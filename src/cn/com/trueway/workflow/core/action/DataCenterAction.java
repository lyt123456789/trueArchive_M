package cn.com.trueway.workflow.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.BusModule;
import cn.com.trueway.workflow.core.pojo.ColumnMapColumn;
import cn.com.trueway.workflow.core.pojo.ColumnPermit;
import cn.com.trueway.workflow.core.pojo.DataDic;
import cn.com.trueway.workflow.core.pojo.DataDicColumnMatch;
import cn.com.trueway.workflow.core.pojo.DataDicMatch;
import cn.com.trueway.workflow.core.pojo.DataDicTable;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.service.DataCenterService;
import cn.com.trueway.workflow.core.service.FieldInfoService;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.TableInfoService;
/**
 * 数据中心相关功能
 * 描述：TODO 对DataCenterAction进行描述
 * 作者：季振华
 * 创建时间：2016-1-21 下午2:58:12
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class DataCenterAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	
	private BusModule busMod;
	
	private DataDic dataDic;
	
	private DataCenterService dataCenterService;
	
	private TableInfoService tableInfoService;
	
	private FieldInfoService fieldInfoService;
	
	private ItemService itemService;
	
	public BusModule getBusMod() {
		return busMod;
	}

	public void setBusMod(BusModule busMod) {
		this.busMod = busMod;
	}
	
	public DataDic getDataDic() {
		return dataDic;
	}

	public void setDataDic(DataDic dataDic) {
		this.dataDic = dataDic;
	}

	public DataCenterService getDataCenterService() {
		return dataCenterService;
	}

	public void setDataCenterService(DataCenterService dataCenterService) {
		this.dataCenterService = dataCenterService;
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
	
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * 数据中心的业务模块列表
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午3:01:24
	 */
	public String getbusModuleList(){
		HashMap<String,String> map = new HashMap<String,String>();
		String modCode = getRequest().getParameter("modCode");
		map.put("modCode", modCode);
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = dataCenterService.getbusModuleCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<BusModule> list = dataCenterService.getbusModuleList(map, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("modCode", modCode);
		return "busModuleList";
	}
	
	/**
	 * 根据代码查找业务模块
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午6:05:29
	 */
	public void getBusModuleListByCode(){
		HashMap<String,String> map = new HashMap<String,String>();
		String modCode=getRequest().getParameter("modCode");
		map.put("modCode", modCode);
		map.put("status", "0");
		JSONObject result = dataCenterService.getBusModuleListByCode(map);
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 到新增业务模块的页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午4:11:13
	 */
	public String toAddBusMod(){
		return "addBusMod";
	}
	
	/**
	 * 到修改业务模块的页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午6:05:52
	 */
	public String toUpdateBusMod(){
		HashMap<String,String> map = new HashMap<String,String>();
		String id = getRequest().getParameter("id");
		map.put("id", id);
		BusModule busModule = dataCenterService.getbusModuleById(map);
		getRequest().setAttribute("busMod", busModule==null?new BusModule():busModule);
		return "updateBusMod";
	}
	
	/**
	 * 新增、修改业务模块
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午6:08:22
	 */
	public void addBusMod(){
		HashMap<String,String> map = new HashMap<String,String>();
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		if(null != busMod && null != busMod.getId()){
			//修改
			String id = busMod.getId();
			map.put("id", id);
			BusModule busModule = dataCenterService.getbusModuleById(map);
			busMod.setCreateDate(busModule==null?new Date():busModule.getCreateDate());
			busMod.setModifyTime(new Date());
			busMod.setModifyUser(userId);
			dataCenterService.updateBusMod(busMod);
		}else{
			//新增
			busMod.setCreateDate(new Date());
			busMod.setCreateUser(userId);
			busMod.setStatus("0");
			dataCenterService.addBusMod(busMod);
		}
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.write("true");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 测试业务模块配置的数据库能否连接
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午6:08:39
	 */
	public void testConn(){
		String url=getRequest().getParameter("dataAddr");
		String userName=getRequest().getParameter("dataAccount");
		String passWord=getRequest().getParameter("dataPassword");
		Boolean result=dataCenterService.testConn(url,userName,passWord);
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 删除业务模块
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午7:32:38
	 */
	public void delBusMod(){
		HashMap<String,String> map = new HashMap<String,String>();
		String ids=getRequest().getParameter("ids");
		String id = "";
		if(CommonUtil.stringNotNULL(ids)){
			String[] idss = ids.split(",");
			for(String s:idss){
				id += "'" + s + "',";
			}
			if(CommonUtil.stringNotNULL(id) && id.length()>1)
			id = id.substring(0,id.length()-1);
		}
		map.put("id", id);
		Boolean result=dataCenterService.delBusMod(map);
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 获取数据字典列表
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-22 下午1:48:38
	 */
	public String getDataDicList(){
		HashMap<String,String> map = new HashMap<String,String>();
		//分页相关，代码执行顺序不变
		//int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		//int count = dataCenterService.getbusModuleCount(map); 
		//Paging.setPagingParams(getRequest(), pageSize, count);
		//List<BusModule> list = dataCenterService.getbusModuleList(map, Paging.pageIndex, Paging.pageSize);
		List<BusModule> list = dataCenterService.getbusModuleList(map, null, null);
		getRequest().setAttribute("list", list);
		return "dataDicList";
	}
	
	/**
	 * 获取数据字典列表里的一个table
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-22 下午4:31:43
	 */
	public String getDataDicTable(){
		HashMap<String,String> map = new HashMap<String,String>();
		String modId = getRequest().getParameter("modId");
		String dicCode = getRequest().getParameter("dicCode");
		map.put("modId", modId);
		map.put("dicCode", dicCode);
		//分页相关，代码执行顺序不变
		//int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		//int count = dataCenterService.getDataDicCount(map); 
		//Paging.setPagingParams(getRequest(), pageSize, count);
		//List<DataDic> list = dataCenterService.getDataDicList(map, Paging.pageIndex, Paging.pageSize);
		List<DataDic> list = dataCenterService.getDataDicList(map, null, null);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("modId", modId);
		getRequest().setAttribute("dicCode", dicCode);
		return "dataDicTable";
	}
	
	/**
	 * 到新增数据字典的页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-25 上午10:04:24
	 */
	public String toAddDataDic(){
		HashMap<String,String> map = new HashMap<String,String>();
		String modId = getRequest().getParameter("modId");
		map.put("modId", modId);
		List<DataDicTable> tableNames=dataCenterService.getTableNamesByModId(map);
		getRequest().setAttribute("tableNames", tableNames);
		getRequest().setAttribute("modId", modId);
		return "addDataDic";
	}
	
	/**
	 * 到修改数据字典的页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-25 下午2:40:16
	 */
	public String toUpdateDataDic(){
		HashMap<String,String> map = new HashMap<String,String>();
		String modId = getRequest().getParameter("modId");
		String id = getRequest().getParameter("id");
		map.put("modId", modId);
		map.put("id", id);
		List<DataDic> dataDics = dataCenterService.getDataDicList(map, null, null);
		List<String> tableNames=dataCenterService.getTableNamesById(map);
		DataDic dataDic = new DataDic();
		String tableName = "";
		if(null != dataDics && dataDics.size()>0){
			dataDic = dataDics.get(0);
		}
		if(null != tableNames && tableNames.size()>0){
			tableName = tableNames.get(0);
		}
		getRequest().setAttribute("tableName", tableName);
		getRequest().setAttribute("dataDic", dataDic);
		getRequest().setAttribute("modId", modId);
		return "updateDataDic";
	}
	
	/**
	 * 获取表字段
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-25 上午10:04:43
	 */
	public void getTableInfo(){
		HashMap<String,String> map = new HashMap<String,String>();
		String tableName=getRequest().getParameter("tableName");
		String modId=getRequest().getParameter("modId");
		map.put("tableName", tableName);
		map.put("modId", modId);
		List<Object[]> tableInfo=dataCenterService.getAddDataDicTableInfo(map);
		JSONArray jsonArr = new JSONArray();
		for(Object[] os : tableInfo){
			JSONObject json = new JSONObject();
			json.accumulate("filedName", os[0]);
			json.accumulate("filedType", os[1]);
			json.accumulate("filedLength", os[2]);
			json.accumulate("filedDeafult", os[3]);
			json.accumulate("filedEsc", os[4]);
			json.accumulate("filedRule", os[5]);
			json.accumulate("filedPk", os[6]);
			json.accumulate("tableNameDesc", os[7]);
			jsonArr.add(json);
		}
		this.outWirter(jsonArr.toString());
		return;
	}
	
	/**
	 * 新增、修改数据字典
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-25 上午10:24:39
	 */
	public void addDataDic(){
		HashMap<String,String> map = new HashMap<String,String>();
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		String dicName = getRequest().getParameter("dicName");
		String dicCode = getRequest().getParameter("tableNames");
		String category = getRequest().getParameter("category");
		String modId = getRequest().getParameter("modId");
		String id = getRequest().getParameter("id");
		DataDic dataDic = new DataDic();
		if(CommonUtil.stringNotNULL(id)){
			dataDic.setId(id);
		}
		dataDic.setModId(modId);
		dataDic.setDicCode(dicCode);
		dataDic.setDicName(dicName);
		dataDic.setCategory(category);
		dataDic.setCreateDate(new Date());
		dataDic.setCreateUser(userId);
		dataDic.setStatus("0");
		dataDic.setPx(new BigDecimal("999"));
		int tableInfoSize=0;
		if(getRequest().getParameter("tableInfoSize") != null){
			tableInfoSize=Integer.parseInt(getRequest().getParameter("tableInfoSize"));
		}
		ArrayList<DataDicTable> tableInfos=new ArrayList<DataDicTable>() ;
		if (tableInfoSize !=0){
			for (int i=0;i<tableInfoSize;i++){
				String filedIdI = getRequest().getParameter("filedId"+i);
				
				DataDicTable tableInfo=new DataDicTable();
				if(CommonUtil.stringNotNULL(filedIdI)){
					tableInfo.setId(getRequest().getParameter("filedId"+i));
					map.put("id", filedIdI);
					List<DataDicTable> tableInfoOld=dataCenterService.getUpdateTableInfo(map);
					if(null != tableInfoOld && tableInfoOld.size()>0){
						DataDicTable dataDicTable = tableInfoOld.get(0);
						tableInfo.setCreateUser(dataDicTable.getCreateUser());
						tableInfo.setCreateDate(dataDicTable.getCreateDate());
					}
				}else{
					tableInfo.setCreateUser(emp.getEmployeeGuid());
					tableInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
				}
				tableInfo.setTableName(getRequest().getParameter("tableNames"));
				tableInfo.setFiledName(getRequest().getParameter("filedName"+i));
				tableInfo.setFiledType(getRequest().getParameter("filedType"+i));
				tableInfo.setFiledLength(getRequest().getParameter("filedLength"+i));
				tableInfo.setFiledDeafult(getRequest().getParameter("filedDeafult"+i));
				tableInfo.setFileDesc(getRequest().getParameter("filedEsc"+i));
				tableInfo.setFiledRule(getRequest().getParameter("filedRule"+i));
				tableInfo.setFileDpk(getRequest().getParameter("filedPk"+i));
				tableInfo.setTableDesc(dicName);
				tableInfo.setModifyUser(emp.getEmployeeGuid());
				tableInfo.setModifyTime(new Timestamp(System.currentTimeMillis()));
				tableInfo.setStatus("0");
				tableInfo.setPx(new BigDecimal("999"));
				tableInfos.add(tableInfo);
			}
		}
		JSONObject result = dataCenterService.insertDataDic(dataDic,tableInfos,emp);
		this.outWirter(result);
	}
	
	/**
	 * 到修改页面时获取该数据字典对应表字段
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-25 下午5:41:42
	 */
	public void getUpdateTableInfo(){
		HashMap<String,String> map = new HashMap<String,String>();
		String dataDicId=getRequest().getParameter("dataDicId");
		String tableName=getRequest().getParameter("tableName");
//		String modId=getRequest().getParameter("modId");
		map.put("dataDicId", dataDicId);
		map.put("tableName", tableName);
		List<DataDicTable> tableInfo=dataCenterService.getUpdateTableInfo(map);
		JSONArray jsonArr = new JSONArray();
		for(DataDicTable os : tableInfo){
			JSONObject json = new JSONObject();
			json.accumulate("filedId", os.getId());
			json.accumulate("filedName", os.getFiledName());
			json.accumulate("filedType", os.getFiledType());
			json.accumulate("filedLength", os.getFiledLength());
			json.accumulate("filedDeafult", os.getFiledDeafult());
			json.accumulate("filedEsc", os.getFileDesc());
			json.accumulate("filedRule", os.getFiledRule());
			json.accumulate("filedPk", os.getFileDpk());
			json.accumulate("tableNameDesc", os.getTableDesc());
			json.accumulate("tableInfoId", os.getId());
			jsonArr.add(json);
		}
		this.outWirter(jsonArr.toString());
		return;
	}
	
	/**
	 * 删除数据字典
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午3:36:46
	 */
	public void delDataDic(){
		HashMap<String,String> map = new HashMap<String,String>();
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String ids = getRequest().getParameter("ids");
		String[] ids_arr=ids.split(",");
		JSONObject result = null;
		DataDic dataDic = new DataDic();
        for (int i = 0; i < ids_arr.length; i++) {
        	map.put("id", ids_arr[i]);
        	List<DataDic> dataDics = dataCenterService.getDataDicList(map, null, null);
        	if(null!=dataDics && dataDics.size()>0){
        		dataDic = dataDics.get(0);
        	}
        	dataDic.setStatus("1");
    		result=dataCenterService.insertDataDic(dataDic,null,emp);
        }
		this.outWirter(result);
	}
	
	/**
	 * 获取数据字典对应的匹配关系
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午3:37:00
	 */
	public String getDataDicMatch(){
		HashMap<String,String> map = new HashMap<String,String>();
		String dicId = getRequest().getParameter("dicId");
		String matchCode = getRequest().getParameter("matchCode");
		map.put("dicId", dicId);
		map.put("matchCode", matchCode);
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = dataCenterService.getDataDicMatchCount(map); 
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<DataDicMatch> list = dataCenterService.getDataDicMatchList(map, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("dicId", dicId);
		getRequest().setAttribute("matchCode", matchCode);
		return "dataDicMatch";
	}
	
	/**
	 * 到新增数据字典匹配关系的页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午12:00:57
	 */
	public String toAddMatch(){
		HashMap<String,String> map = new HashMap<String,String>();
		String dicId = getRequest().getParameter("dicId");
		map.put("dicId", dicId);
		//查询本数据字典已经匹配过的表
		List<DataDicMatch> list = dataCenterService.getDataDicMatchList(map, null, null);
		String tableName="";
		if(null != list && list.size()>0){
			for(DataDicMatch d:list){
				tableName += "'" + d.getTableCode() + "',";
			}
			if(CommonUtil.stringNotNULL(tableName)&&tableName.length()>1){
				tableName = tableName.substring(0,tableName.length()-1);
			}
		}
		map.put("tableName", tableName);
		//查询所有关联的数据库表
		List<WfTableInfo> tables=tableInfoService.getTableByMap(map);
		if (tables!=null&&tables.size()>0) {
			for (int i = 0; i < tables.size(); i++) {
				List<WfFieldInfo> fields=fieldInfoService.getAllFieldByTableId(tables.get(i).getId());
				//把字段名改为大写,用于页面标签元素和字段对应
				if (fields!=null) {
					for (int j = 0; j < fields.size(); j++) {
						if(fields.get(j).getVc_fieldname()!=null){
							fields.get(j).setVc_fieldname(fields.get(j).getVc_fieldname().toUpperCase());
						}
					}
				}
				tables.get(i).setFields(fields);
			}
		}
		getRequest().setAttribute("tables", tables);
		getRequest().setAttribute("dicId", dicId);
		return "addMatch";
	}
	
	/**
	 * 新增匹配关系
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午3:37:21
	 */
	public void addMatch(){
		HashMap<String,String> map = new HashMap<String,String>();
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		String id = getRequest().getParameter("id");
		String dicId = getRequest().getParameter("dicId");
		String tableCode = getRequest().getParameter("tableCode");
		String formName = getRequest().getParameter("formName");
		DataDicMatch match = new DataDicMatch();
		if(CommonUtil.stringNotNULL(id)){
			//修改
			map.put("id", id);
			DataDicMatch matchOld = dataCenterService.getDataDicMatchById(map);
			if(null != matchOld){
				matchOld.setDataDicId(dicId);
				matchOld.setTableCode(tableCode);
				matchOld.setFormName(formName);
				matchOld.setModifyTime(new Date());
				matchOld.setModifyUser(userId);
				dataCenterService.updateMatch(matchOld);
			}
		}else{
			//新增
			match.setDataDicId(dicId);
			match.setTableCode(tableCode);
			match.setFormName(formName);
			match.setCreateDate(new Date());
			match.setCreateUser(userId);
			match.setStatus("0");
			dataCenterService.addMatch(match);
		}
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.write("true");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 到修改匹配关系页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午4:38:54
	 */
	public String toUpdateMatch(){
		HashMap<String,String> map = new HashMap<String,String>();
		String dicId = getRequest().getParameter("dicId");
		String id = getRequest().getParameter("id");
		map.put("dicId", dicId);
		map.put("id", id);
		DataDicMatch match = dataCenterService.getDataDicMatchById(map);
		//查询本数据字典已经匹配过的表
		List<DataDicMatch> list = dataCenterService.getDataDicMatchList(map, null, null);
		String tableName="";
		if(null != list && list.size()>0){
			for(DataDicMatch d:list){
				tableName += "'" + d.getTableCode() + "',";
			}
			if(CommonUtil.stringNotNULL(tableName)&&tableName.length()>1){
				tableName = tableName.substring(0,tableName.length()-1);
			}
		}
		map.put("tableName", tableName);
		//查询所有关联的数据库表
		List<WfTableInfo> tables=tableInfoService.getTableByMap(map);
		if (tables!=null&&tables.size()>0) {
			for (int i = 0; i < tables.size(); i++) {
				List<WfFieldInfo> fields=fieldInfoService.getAllFieldByTableId(tables.get(i).getId());
				//把字段名改为大写,用于页面标签元素和字段对应
				if (fields!=null) {
					for (int j = 0; j < fields.size(); j++) {
						if(fields.get(j).getVc_fieldname()!=null){
							fields.get(j).setVc_fieldname(fields.get(j).getVc_fieldname().toUpperCase());
						}
					}
				}
				tables.get(i).setFields(fields);
			}
		}
		getRequest().setAttribute("tables", tables);
		getRequest().setAttribute("dicId", dicId);
		getRequest().setAttribute("match", match);
		return "updateMatch";
	}
	
	/**
	 * 删除匹配关系表
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午5:01:08
	 */
	public void delMatch(){
		HashMap<String,String> map = new HashMap<String,String>();
		String ids = getRequest().getParameter("ids");
		String[] ids_arr=ids.split(",");
		JSONObject result = new JSONObject();
		DataDicMatch match = new DataDicMatch();
        for (int i = 0; i < ids_arr.length; i++) {
        	map.put("id", ids_arr[i]);
        	List<DataDicMatch> matches = dataCenterService.getDataDicMatchList(map, null, null);
        	if(null!=matches && matches.size()>0){
        		match = matches.get(0);
        	}
        	match.setStatus("1");
    		dataCenterService.updateMatch(match);
    		result.put("success", true);
        }
		this.outWirter(result);
	}
	
	/**
	 * 到设置数据字典字段匹配的问题
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午2:15:33
	 */
	public String toSetMatch(){
		HashMap<String,String> map = new HashMap<String,String>();
		String modId=getRequest().getParameter("modId");
		String dataDicId=getRequest().getParameter("dicId");
		String matchId = getRequest().getParameter("matchId");
		
		map.put("modId", modId);
		map.put("dataDicId", dataDicId);
		map.put("matchId", matchId);
		map.put("id", dataDicId);
		//获取当前调用的表
		String tableName = "";
		List<String> tableNames=dataCenterService.getTableNamesById(map);
		if(null != tableNames && tableNames.size()>0){
			tableName= tableNames.get(0);
		}
		//获取本地表
		DataDicColumnMatch matchOld = dataCenterService.getDataDicColumnMatch(map);
		String tableName_local="";
		if(null != matchOld){
			tableName_local = matchOld.getTableCode();
		}
		map.put("tableName_local", tableName_local);
		map.put("tableName", tableName);
		List<ColumnMapColumn> list = dataCenterService.getColumnMapColumnList(map);
		List<Object[]> tableInfo=dataCenterService.getAddDataDicTableInfo(map);
		List<Object[]> tableInfo_new = new ArrayList<Object[]>();
		for(Object[] os : tableInfo){
			Object[] o = new Object[os.length+2];
			o[0]=os[0];
			o[1]=os[1];
			o[4]=os[4];
			o[7]=os[7];
			String columnName = "";
			String mapId = "";
			if(null!=list&&list.size()>0){
				for(ColumnMapColumn c:list){
					if(os[0].equals(c.getColumnName())){
						columnName = c.getColumnName_local();
						mapId = c.getId();
					}
				}
			}
			if(null == columnName || "null".equals(columnName)){
				columnName = "";
			}
			o[8]=columnName;
			o[9]=mapId;
			tableInfo_new.add(o);
		}
		map.clear();
		map.put("modId", modId);
		map.put("id", matchId);
		DataDicMatch dicMatchOld = dataCenterService.getDataDicMatchById(map);
		String tableNameNow = "";
		if(null!=dicMatchOld){
			tableNameNow = dicMatchOld.getTableCode();
		}
		map.put("tableNameNow", tableNameNow);
		List<WfTableInfo> tables=tableInfoService.getTableByMap(map);
		List<WfFieldInfo> fields = new ArrayList<WfFieldInfo>();
		if (tables!=null&&tables.size()>0) {
			for (int i = 0; i < tables.size(); i++) {
				fields=fieldInfoService.getAllFieldByTableId(tables.get(i).getId());
				//把字段名改为大写,用于页面标签元素和字段对应
				if (fields!=null) {
					for (int j = 0; j < fields.size(); j++) {
						if(fields.get(j).getVc_fieldname()!=null){
							fields.get(j).setVc_fieldname(fields.get(j).getVc_fieldname().toUpperCase());
						}
					}
				}
				tables.get(i).setFields(fields);
			}
		}
		getRequest().setAttribute("tableInfo_new", tableInfo_new);
		getRequest().setAttribute("tables", tables);
		getRequest().setAttribute("fields", fields);
		getRequest().setAttribute("matchId", matchId);
		getRequest().setAttribute("dicId", dataDicId);
		getRequest().setAttribute("tableInfoSize", tableInfo_new.size());
		getRequest().setAttribute("tableName", tableName);
		return "setDataDic";
	}
	
	
	/**
	 * 新增字段与字段之间的关系
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-27 上午10:49:56
	 */
	public void addColumnMapColumn(){
		HashMap<String,String> map = new HashMap<String,String>();
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String dicId = getRequest().getParameter("dicId");
		int tableInfoSize=0;
		if(getRequest().getParameter("tableInfoSize") != null){
			tableInfoSize=Integer.parseInt(getRequest().getParameter("tableInfoSize"));
		}
		String filedType = "";
		ArrayList<ColumnMapColumn> tableInfos=new ArrayList<ColumnMapColumn>() ;
		if (tableInfoSize !=0){
			for (int i=0;i<tableInfoSize;i++){
				String mapId = getRequest().getParameter("mapId"+i);
				
				ColumnMapColumn tableInfo=new ColumnMapColumn();
				if(CommonUtil.stringNotNULL(mapId)){
					tableInfo.setId(getRequest().getParameter("mapId"+i));
					map.put("id", mapId);
					List<ColumnMapColumn> columnMapOld=dataCenterService.getUpdateColumnMap(map);
					if(null != columnMapOld && columnMapOld.size()>0){
						ColumnMapColumn ColumnMap = columnMapOld.get(0);
						tableInfo.setCreateUser(ColumnMap.getCreateUser());
						tableInfo.setCreateDate(ColumnMap.getCreateDate());
					}
				}else{
					tableInfo.setCreateUser(emp.getEmployeeGuid());
					tableInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
				}
				tableInfo.setDataDicId(dicId);
				tableInfo.setTableName(getRequest().getParameter("tableName"));
				tableInfo.setColumnName(getRequest().getParameter("filedName"+i));
				filedType = getRequest().getParameter("filedType"+i);
				if("date".equalsIgnoreCase(filedType)){
					tableInfo.setColumnType("2");
				}else if("clob".equalsIgnoreCase(filedType)){
					tableInfo.setColumnType("1");
				}else{
					tableInfo.setColumnType("0");
				}
				tableInfo.setTableName_local(getRequest().getParameter("tagTables"+i));
				String tagField =  getRequest().getParameter("tagFields"+i);
				String columnName_local = "";
				String name_local = "";
				if(CommonUtil.stringNotNULL(tagField)&&tagField.indexOf(",")>0){
					String[] tagFields = tagField.split(",");
					columnName_local = tagFields[0];
					name_local = tagFields[1];
				}
				tableInfo.setColumnName_local(columnName_local);
				tableInfo.setName_local(name_local);
				tableInfo.setModifyUser(emp.getEmployeeGuid());
				tableInfo.setModifyTime(new Timestamp(System.currentTimeMillis()));
				tableInfo.setStatus("0");
				tableInfo.setPx(new BigDecimal(i));
				tableInfos.add(tableInfo);
			}
		}
		JSONObject result = dataCenterService.insertColumnMapColumn(tableInfos,emp);
		this.outWirter(result);
	}
	
	public void toCopyUrl(){
		String url = getRequest().getParameter("url");
		String localUrl = SystemParamConfigUtil.getParamValueByParam("localUrl");
//		if(CommonUtil.stringNotNULL(url)){
//			url=url.replace("12_34", "&");
//		}
		url = localUrl + url;
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.print(url);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null) {
				out.close();				
			}
		}
		
	}
	
	/**
	 * 到发起列表
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-27 下午5:45:32
	 */
	public String toSendListFromData(){
		HashMap<String,String> map = new HashMap<String,String>();
		String modId = getRequest().getParameter("modId");
		String dataDicId = getRequest().getParameter("dicId");
		String matchId = getRequest().getParameter("matchId");
		map.put("id", matchId);
		DataDicMatch matchOld = dataCenterService.getDataDicMatchById(map);
		String tableName_local = "";
		if(null!=matchOld){
			tableName_local = matchOld.getTableCode();
		}
		map.put("tableName_local", tableName_local);
		map.put("notNull", "0");//查询columnName_local不为null
		map.put("dataDicId", dataDicId);
		map.put("is_Show", "1");
		map.put("is_Search", "1");
		map.put("matchId", matchId);
		List<ColumnMapColumn> cpLists = dataCenterService.getColumnSearchList(map);//查询条件列表
		List<ColumnMapColumn> cLists = dataCenterService.getColumnMapColumnList(map);//第一行列表市
		List<Object[]> lcidLists = dataCenterService.getIdListsByTableName(map);
		Object[] idObj = null;
		if(null!=lcidLists&&lcidLists.size()>0){
			idObj = lcidLists.get(0);
		}
		String allLcId = "";
		String lcId="";
		String reflc="";
		int num_lcId=0;
		if(null!=idObj){
			lcId=idObj[0]==null?"":idObj[0].toString();
			allLcId += lcId + ",";
			num_lcId++;
			reflc=idObj[1]==null?"":idObj[1].toString();
			if(reflc.indexOf(",")>-1){
				String[] reflcs = reflc.split(",");
				for(String s:reflcs){
					if(!(allLcId.indexOf(s)>-1)){
						allLcId += s + ",";
						num_lcId++;
					}
				}
			}
			if(CommonUtil.stringNotNULL(allLcId)&&allLcId.length()>0){
				allLcId = allLcId.substring(0,allLcId.length()-1);
			}
		}
		String allLcIdStr = "";
		if(CommonUtil.stringNotNULL(allLcId)&&allLcId.indexOf(",")>0){
			String[] allLcIds = allLcId.split(",");
			for(String s:allLcIds){
				allLcIdStr += "'" + s +"',";
			}
			allLcIdStr = allLcIdStr.substring(0,allLcIdStr.length()-1);
		}else{
			allLcIdStr ="'"+allLcId+"'";
		}
		List<WfItem> itemLists = itemService.getItemByLcids(allLcIdStr);
		String columnsql = "";
		String tablesql = "";
		List<String> hLists = new ArrayList<String>();//横向
		List<String> hEnLists = new ArrayList<String>();//横向
		List<ColumnMapColumn> cpLists_new = new ArrayList<ColumnMapColumn>();
		int k = 0;//记录clob字段所在下标
		String clobIndex = "";//记录clob字段所在下标
		String dateIndex = "";//记录时间字段所在下标
		String tableName = "";//引用表表名
		if(null!=cLists&&cLists.size()>0){
			for(ColumnMapColumn c:cLists){
				hLists.add(c.getName_local());
				hEnLists.add(c.getColumnName_local());
				columnsql += c.getColumnName() + ",";
				tablesql = c.getTableName();
				if("1".equals(c.getColumnType())){//字段类型为clob字段
//				if("0".equals(c.getColumnType())){//暂时为var字段
					clobIndex += k + ",";
				}else if("2".equals(c.getColumnType())){
					dateIndex += k + ",";
				}
				k++;
				tableName = c.getTableName();
			}
			if(CommonUtil.stringNotNULL(columnsql)&&columnsql.length()>0){
				columnsql = columnsql.substring(0,columnsql.length()-1);
			}
			if(CommonUtil.stringNotNULL(clobIndex)&&clobIndex.length()>0){
				clobIndex = clobIndex.substring(0,clobIndex.length()-1);
			}
		}
		map.put("clobIndex", clobIndex);
		map.put("tablesql", tablesql);
		map.put("columnsql", columnsql);
		map.put("modId", modId);
		List<Object[]> sLists = null;
		if(CommonUtil.stringNotNULL(columnsql)){
			if(null!=cpLists&&cpLists.size()>0){
				for(ColumnMapColumn c:cpLists){
					if("2".equals(c.getColumnType())){
						String str = c.getColumnName();
						String str_now_begin = getRequest().getParameter(str+"begin");
						getRequest().setAttribute(str+"begin", str_now_begin);
						c.setSearchValue_begin(str_now_begin);
						String str_now_end = getRequest().getParameter(str+"end");
						getRequest().setAttribute(str+"end", str_now_end);
						c.setSearchValue_end(str_now_end);
						cpLists_new.add(c);
					}else{
						String str = c.getColumnName();
						String str_now = getRequest().getParameter(str);
						getRequest().setAttribute(str, str_now);
						c.setSearchValue(str_now);
						cpLists_new.add(c);
						map.put(str, str_now);
					}
				}
			}
			//分页相关，代码执行顺序不变
			int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
			int count = dataCenterService.getDataCountFromDataSource(map,cpLists);
			Paging.setPagingParams(getRequest(), pageSize, count);
			map.put("tableName", tableName);
			sLists = dataCenterService.getDataFromDataSource(map,cpLists, Paging.pageIndex, Paging.pageSize);//获取的数据列表
		}
		getRequest().setAttribute("hLists", hLists);
		getRequest().setAttribute("num_lcId", itemLists.size());
		List<Object[]> sLists_new = new ArrayList<Object[]>();
		String primaryId = dataCenterService.getPrimaryIdByTable(tableName,modId);
		if(CommonUtil.stringIsNULL(primaryId)){
			primaryId = SystemParamConfigUtil.getParamValueByParam("primaryId");
		}
		if(CommonUtil.stringIsNULL(primaryId)){
			primaryId = "Id";
		}
		hEnLists.add(primaryId);
		boolean isDate = false;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		if(null!=sLists&&sLists.size()>0){
			for(Object[] o:sLists){
				Object[] o_new = new Object[o.length+2];
				for(int i=0;i<o.length;i++){
					String[] dateIndexs = dateIndex.split(",");
					if(null != dateIndexs && dateIndexs.length>0){
						for (String s : dateIndexs){
							if(s.equals(i+"")){
								isDate = true;
							}
						}
					}
					if(isDate){
						try {
							String str = o[i]==null?"":o[i].toString();
							if(CommonUtil.stringNotNULL(str)){
								o_new[i]=sf.format(sf2.parse(str));
							}else{
								o_new[i] = "";
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}else{
						o_new[i]=o[i];
					}
					isDate = false;
				}
				String value="";
				String processId = "";
				for(int i=0;i<o.length;i++){
					String s = hEnLists.get(i);
					String str = o[i]==null?"":o[i].toString();
					if(CommonUtil.stringIsNULL(str) || "null".equalsIgnoreCase(str)){
						str = "";
					}
					value += s + "###" + str + ",";
					if(i == (o.length-1)){
						processId = o[i].toString();
					}
				}
				if(CommonUtil.stringNotNULL(value)&&value.length()>0){
					value = value.substring(0,value.length()-1);
				}
				String itemId = "";
				if(null != itemLists && itemLists.size()==1){
					WfItem wfItem = itemLists.get(0);
					itemId = wfItem.getId();
					allLcId = wfItem.getLcid();
				}
				o_new[o.length]=itemId;
				o_new[o.length+1]=processId;
				sLists_new.add(o_new);
			}
		}
		getRequest().setAttribute("sLists_new", sLists_new);
		getRequest().setAttribute("cpLists", cpLists_new);
		getRequest().setAttribute("hEnLists_size", hEnLists.size());
		getRequest().setAttribute("allLcId", allLcId);
		getRequest().setAttribute("modId", modId);
		getRequest().setAttribute("matchId", matchId);
		getRequest().setAttribute("dicId", dataDicId);
		return "toSendListFromData";
	}
	
	/**
	 * 到设置权限属性的页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-1-29 下午3:01:35
	 */
	public String toSetPermit(){
		String modId = getRequest().getParameter("modId");
		getRequest().setAttribute("modId", modId);
		String matchId = getRequest().getParameter("matchId");
		HashMap<String,String> map = new HashMap<String,String>();
		String dataDicId = getRequest().getParameter("dicId");
		map.put("id", matchId);
		DataDicMatch matchOld = dataCenterService.getDataDicMatchById(map);
		String tableName_local = "";
		if(null!=matchOld){
			tableName_local = matchOld.getTableCode();
		}
		map.put("tableName_local", tableName_local);
		map.put("notNull", "0");//查询columnName_local不为null
		map.put("dataDicId", dataDicId);
		List<ColumnPermit> cpLists = new ArrayList<ColumnPermit>();
		List<ColumnMapColumn> cLists = dataCenterService.getColumnMapColumnList(map);
		if(null != cLists && cLists.size()>0){
			for(ColumnMapColumn c:cLists){
				map.put("matchId", c.getId());
				ColumnPermit colPer = dataCenterService.getColPerByMatchId(map);
				ColumnPermit cp = new ColumnPermit();
				if(null != colPer){
					cp.setId(colPer.getId());
					cp.setMatchId(colPer.getMatchId());
					cp.setColName(colPer.getColName());
					cp.setColName_C(colPer.getColName_C());
					cp.setIs_Show(colPer.getIs_Show());
					cp.setIs_Search(colPer.getIs_Search());
					cp.setIs_Back(colPer.getIs_Back());
					if("1".equals(colPer.getColumnType())){
						cp.setFilter(colPer.getFilter());
					}else if ("2".equals(colPer.getColumnType())){
						cp.setFilter(colPer.getFilter());
						String filter = colPer.getFilter();
						if(CommonUtil.stringNotNULL(filter) && filter.indexOf(",")>-1){
							String[] filters = filter.split(",");
							cp.setFilter_begin(filter.split(",")[0]);
							if(null!=filters&&filters.length>1){
								cp.setFilter_end(filter.split(",")[1]);
							}
						}
					}else{
						cp.setFilter(colPer.getFilter());
					}
					
					cp.setSort(colPer.getSort());
					cp.setColumnType(colPer.getColumnType());
				}else{
					cp.setMatchId(c.getId());
					cp.setColName(c.getColumnName_local());
					cp.setColName_C(c.getName_local());
					cp.setIs_Show("0");//默认不显示
					cp.setIs_Search("0");//默认不是检索条件
					cp.setIs_Back("0");//默认不是回写字段
					cp.setFilter("");//默认过滤条件为空
					cp.setSort(new BigDecimal("999"));//默认排序号
					cp.setColumnType(c.getColumnType());//字段类型
				}
				cpLists.add(cp);
			}
		}
		getRequest().setAttribute("dicId", dataDicId);
		getRequest().setAttribute("cpLists", cpLists);
		getRequest().setAttribute("tableInfoSize", cpLists.size());
		getRequest().setAttribute("matchId", matchId);
		return "setPermit";
	}
	
	/**
	 * 新增字段权限属性
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-2-1 下午3:14:40
	 */
	public void addPermit(){
		HashMap<String,String> map = new HashMap<String,String>();
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		int tableInfoSize=0;
		if(getRequest().getParameter("tableInfoSize") != null){
			tableInfoSize=Integer.parseInt(getRequest().getParameter("tableInfoSize"));
		}
		ArrayList<ColumnPermit> tableInfos=new ArrayList<ColumnPermit>() ;
		if (tableInfoSize !=0){
			for (int i=0;i<tableInfoSize;i++){
				String id = getRequest().getParameter("id_cp"+i);
				
				ColumnPermit tableInfo=new ColumnPermit();
				if(CommonUtil.stringNotNULL(id)){
					tableInfo.setId(getRequest().getParameter("id_cp"+i));
					map.put("id", id);
					List<ColumnPermit> columnPermitOld=dataCenterService.getUpdateColumnPermit(map);
					if(null != columnPermitOld && columnPermitOld.size()>0){
						ColumnPermit ColumnMap = columnPermitOld.get(0);
						tableInfo.setCreateUser(ColumnMap.getCreateUser());
						tableInfo.setCreateDate(ColumnMap.getCreateDate());
					}
				}else{
					tableInfo.setCreateUser(emp.getEmployeeGuid());
					tableInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
				}
				tableInfo.setMatchId(getRequest().getParameter("matchId_cp"+i));
				tableInfo.setColName(getRequest().getParameter("colName_cp"+i));
				tableInfo.setColName_C(getRequest().getParameter("colName_Ccp"+i));
				tableInfo.setIs_Show(getRequest().getParameter("is_Show_cp"+i));
				tableInfo.setIs_Search(getRequest().getParameter("is_Search_cp"+i));
				tableInfo.setIs_Back(getRequest().getParameter("is_Back_cp"+i));
				tableInfo.setSort(new BigDecimal(getRequest().getParameter("sort_cp"+i)));
				tableInfo.setModifyUser(emp.getEmployeeGuid());
				tableInfo.setModifyTime(new Timestamp(System.currentTimeMillis()));
				tableInfo.setStatus("0");
				tableInfo.setColumnType(getRequest().getParameter("columnType_cp"+i));
				if("1".equals(getRequest().getParameter("columnType_cp"+i))){
					tableInfo.setFilter(getRequest().getParameter("filter_cp"+i));
				}else if ("2".equals(getRequest().getParameter("columnType_cp"+i))){
					String filter_begin = getRequest().getParameter("filter_cp"+i+"_begin");
					String filter_end = getRequest().getParameter("filter_cp"+i+"_end");
					if(CommonUtil.stringNotNULL(filter_begin) || CommonUtil.stringNotNULL(filter_end)){
						tableInfo.setFilter(filter_begin+","+filter_end);
					}
				}else{
					tableInfo.setFilter(getRequest().getParameter("filter_cp"+i));
				}
				tableInfos.add(tableInfo);
			}
		}
		JSONObject result = dataCenterService.insertColumnPermit(tableInfos,emp);
		this.outWirter(result);
	}
	
	/**
	 * 通过表名获取表单名
	 * 描述：TODO 对此方法进行描述 void
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午3:36:00
	 */
	public void getFormTableName(){
		String vcName = getRequest().getParameter("vcName");
		JSONObject result = new JSONObject();	
		List<WfTableInfo> tables=tableInfoService.getTableByTableName(vcName);
		if(null != tables && tables.size()>0){
			String formName = tables.get(0).getVc_name();
			result.put("success", formName);
		}
		this.outWirter(result);
	}
	
	/**
	 * outWirter方法公用
	 * 描述：TODO 对此方法进行描述
	 * @param data
	 * @param response void
	 * 作者:季振华
	 * 创建时间:2016-1-25 上午9:39:08
	 */
	public void outWirter(String data){
		getResponse().setCharacterEncoding("UTF-8");
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().setHeader("Access-Control-Allow-Origin","*");  
		getResponse().setHeader("Access-Control-Allow-Credentials","true");  
		getResponse().setHeader("Cache-Control","no-cache");
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.print(data);
			out.flush();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(out!=null) {
				out.close();				
			}
		}
	}
	
	/**
	 * outWirter方法公用
	 * 描述：TODO 对此方法进行描述
	 * @param data void
	 * 作者:季振华
	 * 创建时间:2016-1-27 上午11:22:53
	 */
	public void outWirter(JSONObject data){
		getResponse().setCharacterEncoding("UTF-8");
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().setHeader("Access-Control-Allow-Origin","*");  
		getResponse().setHeader("Access-Control-Allow-Credentials","true");  
		getResponse().setHeader("Cache-Control","no-cache");
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			out.print(data);
			out.flush();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(out!=null) {
				out.close();				
			}
		}
	}

}
