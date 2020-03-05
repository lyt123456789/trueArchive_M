package cn.com.trueway.workflow.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.workflow.core.dao.DataCenterDao;
import cn.com.trueway.workflow.core.pojo.BusModule;
import cn.com.trueway.workflow.core.pojo.ColumnMapColumn;
import cn.com.trueway.workflow.core.pojo.ColumnPermit;
import cn.com.trueway.workflow.core.pojo.DataDic;
import cn.com.trueway.workflow.core.pojo.DataDicColumnMatch;
import cn.com.trueway.workflow.core.pojo.DataDicMatch;
import cn.com.trueway.workflow.core.pojo.DataDicTable;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.service.DataCenterService;

public class DataCenterServiceImpl implements DataCenterService{
	private DataCenterDao dataCenterDao;

	public DataCenterDao getDataCenterDao() {
		return dataCenterDao;
	}

	public void setDataCenterDao(DataCenterDao dataCenterDao) {
		this.dataCenterDao = dataCenterDao;
	}

	@Override
	public int getbusModuleCount(HashMap<String, String> map) {
		return dataCenterDao.getbusModuleCount(map);
	}

	@Override
	public List<BusModule> getbusModuleList(HashMap<String, String> map, Integer pageindex, Integer pagesize) {
		return dataCenterDao.getbusModuleList(map, pageindex, pagesize);
	}
	
	@Override
	public void addBusMod(BusModule busModule) {
		dataCenterDao.addBusMod(busModule);
	}
	
	@Override
	public void updateBusMod(BusModule busModule) {
		dataCenterDao.updateBusMod(busModule);
	}
	
	@Override
	public JSONObject insertDataDic(DataDic dataDic,ArrayList<DataDicTable> tableInfos, Employee emp) {
		JSONObject result = new JSONObject();	
		try {
			String dataDicId = "";
			if(null != dataDic){
				dataDicId = dataDic.getId();
			}
			if(dataDicId == null||dataDicId.isEmpty()){
				//新增
				dataDic.setCreateUser(emp.getEmployeeGuid());
				dataDic.setCreateDate(new Timestamp(System
						.currentTimeMillis()));
				dataDic.setModifyUser(emp.getEmployeeGuid());
				dataDic.setModifyTime(new Timestamp(System
						.currentTimeMillis()));
				dataDic.setStatus("0");
				DataDic dataDicNow = dataCenterDao.addDataDic(dataDic);
				if(tableInfos != null && null != dataDicNow){
					for(int i=0;i<tableInfos.size();i++){
						tableInfos.get(i).setDataDicId(dataDicNow.getId()==null?UuidGenerator.generate36UUID():dataDicNow.getId());
						dataCenterDao.addDataDicTable(tableInfos.get(i));
					}
				}
			}
			else{
				//更新
				dataDic.setModifyUser(emp.getEmployeeGuid());
				dataDic.setModifyTime(new Timestamp(System
						.currentTimeMillis()));
				this.dataCenterDao.updateDataDic(dataDic);
				if(tableInfos != null){
					for(int i=0;i<tableInfos.size();i++){
						tableInfos.get(i).setDataDicId(dataDicId);
						if(CommonUtil.stringNotNULL(tableInfos.get(i).getId())){
							dataCenterDao.updateTableInfo(tableInfos.get(i));
						}else{
							dataCenterDao.addDataDicTable(tableInfos.get(i));
						}
					}
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public void updateDataDic(DataDic dataDic) {
		dataCenterDao.updateDataDic(dataDic);
	}
	
	@Override
	public Boolean testConn(String url, String userName, String passWord) {

		Boolean testFlg = false;
		try {
			testFlg = this.dataCenterDao.testConn(url,userName,passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testFlg;
	}
	
	@Override
	public JSONObject getBusModuleListByCode(HashMap<String,String> map) {
		JSONObject result = new JSONObject();
		try {
			boolean mkbmExist =dataCenterDao.getBusModuleListByCode(map);
			if(mkbmExist == false){
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public BusModule getbusModuleById(HashMap<String,String> map){
		return dataCenterDao.getbusModuleById(map);
	}
	
	@Override
	public Boolean delBusMod(HashMap<String,String> map) {

		Boolean rusult = false;
		try {
			dataCenterDao.delBusMod(map);
			rusult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rusult;
	}
	
	@Override
	public Integer getDataDicCount(HashMap<String, String> map) {
		return dataCenterDao.getDataDicCount(map);
	}

	@Override
	public List<DataDic> getDataDicList(HashMap<String, String> map, Integer pageindex, Integer pagesize) {
		return dataCenterDao.getDataDicList(map, pageindex, pagesize);
	}
	
	@Override
	public List<DataDicTable> getTableNamesByModId(HashMap<String, String> map) {
		try {
			return dataCenterDao.getTableNamesByModId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> getTableNamesById(HashMap<String, String> map) {
		try {
			return dataCenterDao.getTableNamesById(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Object[]> getAddDataDicTableInfo(HashMap<String, String> map) {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = dataCenterDao.getAddDataDicTableInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<DataDicTable> getUpdateTableInfo(HashMap<String, String> map){
		List<DataDicTable> list = new ArrayList<DataDicTable>();
		try {
			list = dataCenterDao.getUpdateTableInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Integer getDataDicMatchCount(HashMap<String, String> map){
		return dataCenterDao.getDataDicMatchCount(map);
	}
	
	public List<DataDicMatch> getDataDicMatchList(HashMap<String, String> map, Integer pageindex, Integer pagesize){
		return dataCenterDao.getDataDicMatchList(map, pageindex, pagesize);
	}
	
	public DataDicMatch getDataDicMatchById(HashMap<String, String> map){
		return dataCenterDao.getDataDicMatchById(map);
	}
	
	public void addMatch(DataDicMatch dataDicMatch){
		dataCenterDao.addMatch(dataDicMatch);
	}
	
	public void updateMatch(DataDicMatch dataDicMatch){
		dataCenterDao.updateMatch(dataDicMatch);
	}
	
	public List<DataDicTable> getDataDicTableById(HashMap<String, String> map){
		return dataCenterDao.getDataDicTableById(map);
	}
	
	public List<ColumnMapColumn> getColumnMapColumnList(HashMap<String, String> map){
		return dataCenterDao.getColumnMapColumnList(map);
	}
	
	public DataDicColumnMatch getDataDicColumnMatch(HashMap<String, String> map){
		return dataCenterDao.getDataDicColumnMatch(map);
	}
	
	@Override
	public JSONObject insertColumnMapColumn(ArrayList<ColumnMapColumn> tableInfos, Employee emp) {
		JSONObject result = new JSONObject();	
		try {
			String matchIds = "";
			HashMap<String,String> map = new HashMap<String,String>();
			if(tableInfos != null){
				for(int i=0;i<tableInfos.size();i++){
					if(CommonUtil.stringNotNULL(tableInfos.get(i).getId())){
						dataCenterDao.updateColumnMapColumn(tableInfos.get(i));
					}else{
						dataCenterDao.addColumnMapColumn(tableInfos.get(i));
					}
					matchIds += "'" + tableInfos.get(i).getId() + "',";
				}
			}
			if(CommonUtil.stringNotNULL(matchIds) && matchIds.length()>1){
				matchIds = matchIds.substring(0,matchIds.length()-1);
				map.put("matchIds", matchIds);
				dataCenterDao.delPermit(map);
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<ColumnMapColumn> getUpdateColumnMap(HashMap<String, String> map){
		List<ColumnMapColumn> list = new ArrayList<ColumnMapColumn>();
		try {
			list = dataCenterDao.getUpdateColumnMap(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Integer getDataCountFromDataSource(HashMap<String, String> map,List<ColumnMapColumn> cpLists){
		Integer i = 0;
		try {
			map.put("is_WriteBack", "1");
			List<ColumnMapColumn> cmcList = dataCenterDao.getColumnMapColumnList(map);//回寫字段列表
			List<Object[]> fiterList = dataCenterDao.getFiterListFromColPer(map);//过滤条件列表
			i = dataCenterDao.getDataCountFromDataSource(map,cpLists,cmcList,fiterList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public List<Object[]>  getDataFromDataSource(HashMap<String, String> map,List<ColumnMapColumn> cpLists, Integer pageindex, Integer pagesize){
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			map.put("is_WriteBack", "1");
			List<ColumnMapColumn> cmcList = dataCenterDao.getColumnMapColumnList(map);//回寫字段列表
			List<Object[]> fiterList = dataCenterDao.getFiterListFromColPer(map);//过滤条件列表
			list = dataCenterDao.getDataFromDataSource(map,cpLists,cmcList,fiterList,pageindex,pagesize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Object[]>  getIdListsByTableName(HashMap<String, String> map){
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			list = dataCenterDao.getIdListsByTableName(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ColumnPermit getColPerByMatchId(HashMap<String, String> map){
		List<ColumnPermit> list = new ArrayList<ColumnPermit>();
		try {
			list = dataCenterDao.getColPerByMatchId(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null!=list&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public List<ColumnPermit> getUpdateColumnPermit(HashMap<String, String> map){
		List<ColumnPermit> list = new ArrayList<ColumnPermit>();
		try {
			list = dataCenterDao.getUpdateColumnPermit(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public JSONObject insertColumnPermit(ArrayList<ColumnPermit> tableInfos, Employee emp) {
		JSONObject result = new JSONObject();	
		try {
			if(tableInfos != null){
				for(int i=0;i<tableInfos.size();i++){
					if(CommonUtil.stringNotNULL(tableInfos.get(i).getId())){
						dataCenterDao.updateColumnPermit(tableInfos.get(i));
					}else{
						dataCenterDao.addColumnPermit(tableInfos.get(i));
					}
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<ColumnMapColumn> getColumnSearchList(HashMap<String, String> map){
		return dataCenterDao.getColumnSearchList(map);
	}
	
	public void writeBack(HashMap<String, String> map){
		try {
			map.put("is_WriteBack", "1");
			List<ColumnMapColumn> cmcList = dataCenterDao.getColumnMapColumnList(map);
			dataCenterDao.writeBackDataSource(map,cmcList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getPrimaryIdByTable(String tableName,String modId){
		String primaryId = "";
		try {
			primaryId =  dataCenterDao.getPrimaryIdByTable(tableName,modId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return primaryId;
	}
}
