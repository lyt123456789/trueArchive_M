package cn.com.trueway.workflow.core.dao;

import java.util.HashMap;
import java.util.List;

import cn.com.trueway.workflow.core.pojo.BusModule;
import cn.com.trueway.workflow.core.pojo.ColumnMapColumn;
import cn.com.trueway.workflow.core.pojo.ColumnPermit;
import cn.com.trueway.workflow.core.pojo.DataDic;
import cn.com.trueway.workflow.core.pojo.DataDicColumnMatch;
import cn.com.trueway.workflow.core.pojo.DataDicMatch;
import cn.com.trueway.workflow.core.pojo.DataDicTable;

public interface DataCenterDao {
	
	Integer getbusModuleCount(HashMap<String,String> map);
	
	List<BusModule> getbusModuleList(HashMap<String,String> map, Integer pageindex, Integer pagesize);

	void addBusMod(BusModule busModule);
	
	void updateBusMod(BusModule busModule);
	
	DataDic addDataDic(DataDic dataDic);
	
	void updateDataDic(DataDic dataDic);
	
	void addDataDicTable(DataDicTable dataDicTable);
	
	void updateTableInfo(DataDicTable dataDicTable)throws Exception;
	
	Boolean testConn(String url, String userName, String passWord) throws Exception;
	
	boolean getBusModuleListByCode(HashMap<String,String> map) throws Exception;
	
	BusModule getbusModuleById(HashMap<String,String> map);
	
	void delBusMod(HashMap<String,String> map) throws Exception;
	
	Integer getDataDicCount(HashMap<String,String> map);
	
	List<DataDic> getDataDicList(HashMap<String,String> map, Integer pageindex, Integer pagesize);
	
	List<DataDicTable> getTableNamesByModId(HashMap<String,String> map) throws Exception;
	
	List<String> getTableNamesById(HashMap<String,String> map) throws Exception;
	
	List<Object[]> getAddDataDicTableInfo(HashMap<String,String> map) throws Exception;
	
	List<DataDicTable> getUpdateTableInfo(HashMap<String,String> map) throws Exception ;
	
	Integer getDataDicMatchCount(HashMap<String,String> map);
	
	List<DataDicMatch> getDataDicMatchList(HashMap<String,String> map, Integer pageindex, Integer pagesize);
	
	DataDicMatch getDataDicMatchById(HashMap<String,String> map);
	
	void addMatch(DataDicMatch dataDicMatch);
	
	void updateMatch(DataDicMatch dataDicMatch);
	
	List<DataDicTable> getDataDicTableById(HashMap<String,String> map);
	
	DataDicColumnMatch getDataDicColumnMatch(HashMap<String,String> map);
	
	List<ColumnMapColumn> getColumnMapColumnList(HashMap<String,String> map);
	
	void addColumnMapColumn(ColumnMapColumn columnMapColumn);
	
	void updateColumnMapColumn(ColumnMapColumn columnMapColumn);
	
	List<ColumnMapColumn> getUpdateColumnMap(HashMap<String,String> map) throws Exception ;
	
	Integer getDataCountFromDataSource(HashMap<String,String> map,List<ColumnMapColumn> cpLists,List<ColumnMapColumn> cmcList,List<Object[]> fiterList) throws Exception;
	
	List<Object[]> getDataFromDataSource(HashMap<String,String> map,List<ColumnMapColumn> cpLists,List<ColumnMapColumn> cmcList, List<Object[]> fiterList, Integer pageindex, Integer pagesize) throws Exception;
	
	List<Object[]> getIdListsByTableName(HashMap<String,String> map) throws Exception;
	
	List<ColumnPermit> getColPerByMatchId(HashMap<String,String> map) throws Exception;
	
	List<ColumnPermit> getUpdateColumnPermit(HashMap<String, String> map) throws Exception;
	
	void addColumnPermit(ColumnPermit columnPermit);
	
	void updateColumnPermit(ColumnPermit columnPermit);
	
	List<ColumnMapColumn> getColumnSearchList(HashMap<String,String> map);
	
	void writeBackDataSource(HashMap<String, String> map,List<ColumnMapColumn> cmcList)throws Exception;
	
	void delPermit(HashMap<String,String> map) throws Exception;
	
	List<Object[]> getFiterListFromColPer(HashMap<String,String> map);
	
	String getPrimaryIdByTable(String tableName,String modId) throws Exception;
}
