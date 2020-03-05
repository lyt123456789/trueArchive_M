package cn.com.trueway.workflow.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;
import cn.com.trueway.workflow.core.pojo.BusModule;
import cn.com.trueway.workflow.core.pojo.ColumnMapColumn;
import cn.com.trueway.workflow.core.pojo.ColumnPermit;
import cn.com.trueway.workflow.core.pojo.DataDic;
import cn.com.trueway.workflow.core.pojo.DataDicColumnMatch;
import cn.com.trueway.workflow.core.pojo.DataDicMatch;
import cn.com.trueway.workflow.core.pojo.DataDicTable;
import cn.com.trueway.workflow.core.pojo.Employee;

public interface DataCenterService {
	
	/**
	 * 获取业务模块列表的数量
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return int
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午5:06:50
	 */
	int getbusModuleCount(HashMap<String,String> map);
	
	/**
	 * 获取业务模块的列表
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @param pageindex
	 * @param pagesize
	 * @return List<BusModule>
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午5:07:14
	 */
	List<BusModule> getbusModuleList(HashMap<String,String> map, Integer pageindex, Integer pagesize);
	
	/**
	 * 业务模块新增
	 * 描述：TODO 对此方法进行描述
	 * @param busModule void
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午5:07:32
	 */
	void addBusMod(BusModule busModule);
	
	/**
	 * 业务模块修改
	 * 描述：TODO 对此方法进行描述
	 * @param busModule void
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午6:34:45
	 */
	void updateBusMod(BusModule busModule);
	
	/**
	 * 新增数据字典、对应表字段
	 * 描述：TODO 对此方法进行描述
	 * @param dataDic void
	 * 作者:季振华
	 * 创建时间:2016-1-25 上午10:29:39
	 * @return 
	 */
	JSONObject insertDataDic(DataDic dataDic,ArrayList<DataDicTable> tableInfos, Employee emp);
	
	/**
	 * 数据字典修改
	 * 描述：TODO 对此方法进行描述
	 * @param dataDic void
	 * 作者:季振华
	 * 创建时间:2016-1-25 上午10:30:11
	 */
	void updateDataDic(DataDic dataDic);
	
	/**
	 * 测试数据库能否连通
	 * 描述：TODO 对此方法进行描述
	 * @param url
	 * @param userName
	 * @param passWord
	 * @return Boolean
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午5:07:45
	 */
	Boolean testConn(String url, String userName, String passWord);
	
	/**
	 * 通过代码获取业务模块数据
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return JSONObject
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午6:18:58
	 */
	JSONObject getBusModuleListByCode(HashMap<String,String> map);
	
	/**
	 * 通过id查询业务模块
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return BusModule
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午6:18:28
	 */
	BusModule getbusModuleById(HashMap<String,String> map);
	
	/**
	 * 根据id删除业务模块
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return Boolean
	 * 作者:季振华
	 * 创建时间:2016-1-21 下午7:39:24
	 */
	Boolean delBusMod(HashMap<String,String> map);
	
	/**
	 * 获取数据字典的个数
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return Integer
	 * 作者:季振华
	 * 创建时间:2016-1-22 下午5:05:27
	 */
	Integer getDataDicCount(HashMap<String,String> map);
	
	/**
	 * 获取数据字典的列表
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @param pageindex
	 * @param pagesize
	 * @return List<BusModule>
	 * 作者:季振华
	 * 创建时间:2016-1-22 下午5:06:44
	 */
	List<DataDic> getDataDicList(HashMap<String,String> map, Integer pageindex, Integer pagesize);
	
	/**
	 * 通过模块id获取该模块对应的数据库表名
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<DataDicTable>
	 * 作者:季振华
	 * 创建时间:2016-1-22 下午6:57:46
	 * @throws Exception 
	 */
	List<DataDicTable> getTableNamesByModId(HashMap<String,String> map);
	
	/**
	 * 通过id获取当前数字字典对应的表名
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<DataDicTable>
	 * 作者:季振华
	 * 创建时间:2016-1-25 下午3:34:22
	 */
	List<String> getTableNamesById(HashMap<String,String> map);
	
	/**
	 * 获取表字段
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<Object[]>
	 * 作者:季振华
	 * 创建时间:2016-1-25 上午9:41:34
	 */
	List<Object[]> getAddDataDicTableInfo(HashMap<String,String> map);
	
	/**
	 * 获取表中对应字段数据
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<DataDicTable>
	 * 作者:季振华
	 * 创建时间:2016-1-25 下午4:51:08
	 */
	List<DataDicTable> getUpdateTableInfo(HashMap<String,String> map);
	
	/**
	 *获得数据字典匹配的关系表的数量
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return Integer
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午1:48:16
	 */
	Integer getDataDicMatchCount(HashMap<String,String> map);
	
	/**
	 * 获得数据字典匹配的关系表
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<DataDicMatch>
	 * 作者:季振华
	 * 创建时间:2016-1-26 上午11:41:46
	 */
	List<DataDicMatch> getDataDicMatchList(HashMap<String,String> map, Integer pageindex, Integer pagesize);
	
	/**
	 * 通过id获得数据字典匹配的关系表
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return DataDicMatch
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午3:51:38
	 */
	DataDicMatch getDataDicMatchById(HashMap<String,String> map);
	
	/**
	 * 新增匹配关系表
	 * 描述：TODO 对此方法进行描述
	 * @param dataDicMatch void
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午3:53:18
	 */
	void addMatch(DataDicMatch dataDicMatch);
	
	/**
	 * 更新匹配关系表
	 * 描述：TODO 对此方法进行描述
	 * @param dataDicMatch void
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午3:52:47
	 */
	void updateMatch(DataDicMatch dataDicMatch);
	
	/**
	 * 获取数据字典子表
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<DataDicTable>
	 * 作者:季振华
	 * 创建时间:2016-1-26 下午3:48:09
	 */
	List<DataDicTable> getDataDicTableById(HashMap<String,String> map);
	
	/**
	 * 获取表对用关系
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<ColumnMapColumn>
	 * 作者:季振华
	 * 创建时间:2016-1-27 上午10:18:20
	 */
	List<ColumnMapColumn> getColumnMapColumnList(HashMap<String,String> map);
	
	/**
	 * 获取数据字典与本地表匹配关系
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return DataDicColumnMatch
	 * 作者:季振华
	 * 创建时间:2016-1-27 下午5:11:50
	 */
	DataDicColumnMatch getDataDicColumnMatch(HashMap<String,String> map);
	
	/**
	 * 数据中心字段与本地表字段匹配关系
	 * 描述：TODO 对此方法进行描述
	 * @param tableInfos
	 * @param emp
	 * @return JSONObject
	 * 作者:季振华
	 * 创建时间:2016-1-27 下午5:12:57
	 */
	JSONObject insertColumnMapColumn(ArrayList<ColumnMapColumn> tableInfos, Employee emp);
	
	/**
	 * 获取数据中心字段与本地表字段匹配关系
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<ColumnMapColumn>
	 * 作者:季振华
	 * 创建时间:2016-1-27 下午5:13:37
	 */
	List<ColumnMapColumn> getUpdateColumnMap(HashMap<String,String> map);
	
	/**
	 * 获取数据中心的表中数据条数
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @param cpLists
	 * @return Integer
	 * 作者:季振华
	 * 创建时间:2016-2-16 上午11:08:13
	 */
	Integer getDataCountFromDataSource(HashMap<String,String> map,List<ColumnMapColumn> cpLists);
	
	/**
	 * 获取数据中心的表中数据
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<Object[]>
	 * 作者:季振华
	 * 创建时间:2016-1-27 下午5:14:24
	 */
	List<Object[]> getDataFromDataSource(HashMap<String,String> map,List<ColumnMapColumn> cpLists, Integer pageindex, Integer pagesize);
	
	/**
	 * 通过表名获取该表对应的itemId和lcId
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<Object[]>
	 * 作者:季振华
	 * 创建时间:2016-1-28 下午2:35:27
	 */
	List<Object[]> getIdListsByTableName(HashMap<String,String> map);
	
	/**
	 * 通过matchId查询该匹配数据的权限属性
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<ColumnMapColumn>
	 * 作者:季振华
	 * 创建时间:2016-1-29 下午4:21:19
	 */
	ColumnPermit getColPerByMatchId(HashMap<String,String> map);
	
	/**
	 * 根据id获取字段权限属性表里的数据
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<ColumnPermit>
	 * 作者:季振华
	 * 创建时间:2016-2-1 下午3:22:53
	 */
	List<ColumnPermit> getUpdateColumnPermit(HashMap<String,String> map);
	
	/**
	 * 新增、修改字段权限属性
	 * 描述：TODO 对此方法进行描述
	 * @param tableInfos
	 * @param emp
	 * @return JSONObject
	 * 作者:季振华
	 * 创建时间:2016-2-1 下午3:49:24
	 */
	JSONObject insertColumnPermit(ArrayList<ColumnPermit> tableInfos, Employee emp);
	
	/**
	 * 根据matchId获得字段权限属性list
	 * 描述：TODO 对此方法进行描述
	 * @param map
	 * @return List<ColumnPermit>
	 * 作者:季振华
	 * 创建时间:2016-2-1 下午4:49:27
	 */
	List<ColumnMapColumn> getColumnSearchList(HashMap<String,String> map);

	/**
	 * 使用了数据回写一个状态位
	 * 描述：TODO 对此方法进行描述
	 * @param map void
	 * 作者:季振华
	 * 创建时间:2016-2-2 下午5:39:16
	 */
	void writeBack(HashMap<String, String> map);
	
	/**
	 * 通过tableName获取该表的主键Id
	 * 描述：TODO 对此方法进行描述
	 * @param tableName
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-3-22 上午10:58:46
	 */
	String getPrimaryIdByTable(String tableName,String modId);
	
}
