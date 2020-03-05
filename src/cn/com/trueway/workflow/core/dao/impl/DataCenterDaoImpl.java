package cn.com.trueway.workflow.core.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import oracle.sql.CLOB;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.DataCenterDao;
import cn.com.trueway.workflow.core.pojo.BusModule;
import cn.com.trueway.workflow.core.pojo.ColumnMapColumn;
import cn.com.trueway.workflow.core.pojo.ColumnPermit;
import cn.com.trueway.workflow.core.pojo.DataDic;
import cn.com.trueway.workflow.core.pojo.DataDicColumnMatch;
import cn.com.trueway.workflow.core.pojo.DataDicMatch;
import cn.com.trueway.workflow.core.pojo.DataDicTable;
import cn.com.trueway.workflow.set.util.ClobToString;

public class DataCenterDaoImpl  extends BaseDao implements DataCenterDao{
	
	public Integer getbusModuleCount(HashMap<String,String> map) {
		String modCode = map.get("modCode");
		String hql = "select count(*) from BusModule t where 1=1 ";
		String rep_modCode = "";
		if(CommonUtil.stringNotNULL(modCode)){
			rep_modCode = modCode.replaceAll("%", "\\\\%");
			rep_modCode = rep_modCode.replace("_", "\\_");
			hql += " and modCode like :modCode ";
			if(rep_modCode!=null && !rep_modCode.equals(modCode)){
				hql += " escape '\\'";
			}
		}
		Query query = getEm().createQuery(hql);
		if(CommonUtil.stringNotNULL(modCode) ){
			query.setParameter("modCode", "%"+rep_modCode.trim()+"%");
		}
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<BusModule> getbusModuleList(HashMap<String,String> map, Integer pageindex, Integer pagesize) {
		String modCode = map.get("modCode");
		String hql = "from BusModule t where 1=1 ";
		String rep_modCode = "";
		if(CommonUtil.stringNotNULL(modCode)){
			rep_modCode = modCode.replaceAll("%", "\\\\%");
			rep_modCode = rep_modCode.replace("_", "\\_");
			hql += " and modCode like :modCode ";
			if(rep_modCode!=null && !rep_modCode.equals(modCode)){
				hql += " escape '\\'";
			}
		}
		hql += " order by createDate desc";
		Query query = getEm().createQuery(hql);
		if(CommonUtil.stringNotNULL(modCode) ){
			query.setParameter("modCode", "%"+rep_modCode.trim()+"%");
		}
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}
	
	@Override
	public void addBusMod(BusModule busModule) {
		this.getEm().persist(busModule);
	}
	
	@Override
	public void updateBusMod(BusModule busModule) {
		this.getEm().merge(busModule);
	}
	
	@Override
	public DataDic addDataDic(DataDic dataDic) {
		this.getEm().persist(dataDic);
		return dataDic;
	}
	
	@Override
	public void updateDataDic(DataDic dataDic) {
		this.getEm().merge(dataDic);
	}
	
	@Override
	public void addDataDicTable(DataDicTable dataDicTable) {
		this.getEm().persist(dataDicTable);
	}
	
	@Override
	public void updateTableInfo(DataDicTable dataDicTable) throws Exception {
		this.getEm().merge(dataDicTable);
	}
	
	@Override
	public Boolean testConn(String url, String userName, String passWord)
			throws Exception {
		Boolean testFlg=false;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		try{
			DriverManager.setLoginTimeout(1);
			Connection conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			closeJDBC(conn,null,null);
			testFlg=true;
		}catch(SQLException se){   
			testFlg=false;  
		    se.printStackTrace() ;   
		 }
		
		return testFlg;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean getBusModuleListByCode(HashMap<String,String> map) throws Exception{
		String modCode = map.get("modCode");
		String status = map.get("status");
		String sql = "from BusModule where 1=1 and status= '" + status + "' and modCode= '" + modCode + "'";
		Query query = getEm().createQuery(sql);
		List<BusModule> list = query.getResultList();
	    if(list.size() > 0){
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	@SuppressWarnings("unchecked")
	public BusModule getbusModuleById(HashMap<String,String> map) {
		String id = map.get("id");
		String hql = "from BusModule t where 1=1 ";
		if(CommonUtil.stringNotNULL(id)){
			hql += " and id = '" + id + "'";
		}
		Query query = getEm().createQuery(hql);
		List<BusModule> list = query.getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public void delBusMod(HashMap<String,String> map) throws Exception{
		String id = map.get("id");
		String sql = "delete from  T_WF_CORE_BUSMODULE t  where 1=1 ";
		if(CommonUtil.stringNotNULL(id)){
			sql += " and t.id in (" + id + ")";
		}
		getEm().createNativeQuery(sql).executeUpdate();
		
	}
	
	public Integer getDataDicCount(HashMap<String,String> map) {
		String modId = map.get("modId");
		String dicCode = map.get("dicCode");
		String hql = "select count(*) from DataDic t where 1=1 ";
		String rep_modId = "";
		if(CommonUtil.stringNotNULL(modId)){
			rep_modId = modId.replaceAll("%", "\\\\%");
			rep_modId = rep_modId.replace("_", "\\_");
			hql += " and modId like :modId ";
			if(rep_modId!=null && !rep_modId.equals(modId)){
				hql += " escape '\\'";
			}
		}
		String rep_dicCode = "";
		if(CommonUtil.stringNotNULL(dicCode)){
			rep_dicCode = dicCode.replaceAll("%", "\\\\%");
			rep_dicCode = rep_dicCode.replace("_", "\\_");
			hql += " and dicCode like :dicCode ";
			if(rep_dicCode!=null && !rep_dicCode.equals(dicCode)){
				hql += " escape '\\'";
			}
		}
		hql += " and status = 0 ";
		Query query = getEm().createQuery(hql);
		if(CommonUtil.stringNotNULL(modId) ){
			query.setParameter("modId", "%"+rep_modId.trim()+"%");
		}
		
		if(CommonUtil.stringNotNULL(dicCode) ){
			query.setParameter("dicCode", "%"+rep_dicCode.trim()+"%");
		}
		
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<DataDic> getDataDicList(HashMap<String,String> map, Integer pageindex, Integer pagesize) {
		String modId = map.get("modId");
		String dicCode = map.get("dicCode");
		
		String id = map.get("id");
		String hql = "from DataDic t where 1=1 ";
		String rep_modId = "";
		if(CommonUtil.stringNotNULL(modId)){
			rep_modId = modId.replaceAll("%", "\\\\%");
			rep_modId = rep_modId.replace("_", "\\_");
			hql += " and modId like :modId ";
			if(rep_modId!=null && !rep_modId.equals(modId)){
				hql += " escape '\\'";
			}
		}
		
		String rep_dicCode = "";
		if(CommonUtil.stringNotNULL(dicCode)){
			rep_dicCode = dicCode.replaceAll("%", "\\\\%");
			rep_dicCode = rep_dicCode.replace("_", "\\_");
			hql += " and dicCode like :dicCode ";
			if(rep_dicCode!=null && !rep_dicCode.equals(dicCode)){
				hql += " escape '\\'";
			}
		}
		
		String rep_id = "";
		if(CommonUtil.stringNotNULL(id)){
			rep_id = id.replaceAll("%", "\\\\%");
			rep_id = rep_id.replace("_", "\\_");
			hql += " and id like :id ";
			if(rep_id!=null && !rep_id.equals(id)){
				hql += " escape '\\'";
			}
		}
		
		hql += " and status = 0 order by createDate desc";
		Query query = getEm().createQuery(hql);
		if(CommonUtil.stringNotNULL(modId) ){
			query.setParameter("modId", "%"+rep_modId.trim()+"%");
		}
		if(CommonUtil.stringNotNULL(dicCode) ){
			query.setParameter("dicCode", "%"+rep_dicCode.trim()+"%");
		}
		
		if(CommonUtil.stringNotNULL(id) ){
			query.setParameter("id", "%"+rep_id.trim()+"%");
		}
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<DataDicTable> getTableNamesByModId(HashMap<String,String> map) throws Exception{
		String modId = map.get("modId");
		//查出当前模块id对应的数据库地址，用户名，密码
		String hql ="from BusModule t where t.id='" + modId + "'";
		Query query = getEm().createQuery(hql);
		List<BusModule> busMod = query.getResultList();
		
		String hql2 ="from  DataDic t where t.status = 0 and t.modId= '" + modId + "'";
		Query query2 = getEm().createQuery(hql2);
		List<DataDic> tableNames=query2.getResultList();
		String tableName="";
		if(tableNames.size()>0){
			for(int i=0;i<tableNames.size();i++){
				tableName+="'"+tableNames.get(i).getDicCode()+"'"+",";
			}
		}else{
			tableName="";
		}
		if(CommonUtil.stringNotNULL(tableName)){
			tableName=tableName.substring(0, tableName.length()-1);
		}
		String url=busMod.get(0).getDataAddr();
		String userName=busMod.get(0).getDataAccount();
		String passWord=busMod.get(0).getDataPassword();
		List<DataDicTable> list=new ArrayList<DataDicTable>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		try{
			DriverManager.setLoginTimeout(1);
			
			Connection conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql ="";
			if(CommonUtil.stringNotNULL(tableName)){
				sql ="select TABLE_NAME as tableName from user_tables where TABLE_NAME not in("+tableName+") order by tableName";
			}else{
				sql ="select TABLE_NAME as tableName from user_tables order by tableName";
			}
			 
			PreparedStatement ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				DataDicTable dataDicTable=new DataDicTable();
				dataDicTable.setTableName(rs.getString("tableName"));
			    list.add(dataDicTable);
			}
			closeJDBC(conn,ps,rs);
		}catch(SQLException se){
		    se.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTableNamesById(HashMap<String,String> map){
		String id = map.get("id");
		String sql = "select t.tableName from t_wf_core_dataDicTable t left join t_wf_core_dataDic s on s.id=t.dataDicId where s.id = '" + id + "' and s.status=0 group by t.tableName ";
		Query query = getEm().createNativeQuery(sql);
		List<String> list=query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAddDataDicTableInfo(HashMap<String,String> map) throws Exception{
		String modId = map.get("modId");
		String tableName = map.get("tableName");
		//查出当前模块id对应的数据库地址，用户名，密码
		String hql ="from BusModule t where t.id='" + modId + "'";
		Query query = getEm().createQuery(hql);
		List<BusModule> busMod = query.getResultList();
		String url=busMod.get(0).getDataAddr();
		String userName=busMod.get(0).getDataAccount();
		String passWord=busMod.get(0).getDataPassword();
		List<Object[]> list=new ArrayList<Object[]>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		try{
			Connection conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql="SELECT b.column_name as Filedname,b.data_type  as Filedtype,b.data_length as Filedlength,b.DATA_DEFAULT as Fileddeafult ,a.comments as fileDESC" +
					",'' as filedrule,'' as Filedpk,c.COMMENTS as tableName FROM user_col_comments a ,all_tab_columns  b,user_tab_comments c WHERE a.table_name = b.table_name and a.table_name = ? and b.owner=? and a.column_name=b.COLUMN_NAME and b.TABLE_NAME=c.TABLE_NAME" +
					" order by b.column_name ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, tableName);
			ps.setString(2, userName.toUpperCase());
			rs=ps.executeQuery();
			while(rs.next()){
				Object[] object=new Object[8];
				object[0]=rs.getString("Filedname");
				object[1]=rs.getString("Filedtype");
				object[2]=rs.getString("Filedlength");
				object[3]=rs.getString("Fileddeafult");
				object[4]=rs.getString("fileDESC");
				object[5]=rs.getString("filedrule");
				object[6]=rs.getString("Filedpk");
				object[7]=rs.getString("tableName");
			    list.add(object);
			}
			closeJDBC(conn,ps,rs);
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}
		return list; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DataDicTable> getUpdateTableInfo(HashMap<String, String> map) throws Exception {
		String tableName = map.get("tableName");
		String dataDicId = map.get("dataDicId");
		String id = map.get("id");
		String sql="select * from T_WF_CORE_DATADICTABLE where 1=1 "; 
		if(CommonUtil.stringNotNULL(tableName)){
			sql += " and tableName='" + tableName + "'"; 
		}
		if(CommonUtil.stringNotNULL(dataDicId)){
			sql += " and dataDicId='" + dataDicId + "'"; 
		}
		if(CommonUtil.stringNotNULL(id)){
			sql += " and id='" + id + "'"; 
		}
		sql += "and status=0  order by px";
		Query query = getEm().createNativeQuery(sql,DataDicTable.class);
		List<DataDicTable> list=query.getResultList();
		return list;
	}
	
	public Integer getDataDicMatchCount(HashMap<String,String> map) {
		String dicId = map.get("dicId");
		String matchCode = map.get("matchCode");
		String hql = "select count(*) from DataDicMatch t where 1=1 ";
		String rep_dicId = "";
		if(CommonUtil.stringNotNULL(dicId)){
			rep_dicId = dicId.replaceAll("%", "\\\\%");
			rep_dicId = rep_dicId.replace("_", "\\_");
			hql += " and dataDicId like :dicId ";
			if(rep_dicId!=null && !rep_dicId.equals(dicId)){
				hql += " escape '\\'";
			}
		}
		String rep_matchCode = "";
		if(CommonUtil.stringNotNULL(matchCode)){
			rep_matchCode = matchCode.replaceAll("%", "\\\\%");
			rep_matchCode = rep_matchCode.replace("_", "\\_");
			hql += " and tableCode like :tableCode ";
			if(rep_matchCode!=null && !rep_matchCode.equals(matchCode)){
				hql += " escape '\\'";
			}
		}
		
		hql += " and status=0 ";
		Query query = getEm().createQuery(hql);
		if(CommonUtil.stringNotNULL(dicId) ){
			query.setParameter("dicId", "%"+rep_dicId.trim()+"%");
		}
		if(CommonUtil.stringNotNULL(matchCode) ){
			query.setParameter("tableCode", "%"+rep_matchCode.trim()+"%");
		}
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DataDicMatch> getDataDicMatchList(HashMap<String, String> map, Integer pageindex, Integer pagesize) {
		String dicId = map.get("dicId");
		String matchCode = map.get("matchCode");
		String id = map.get("id");
		
		String sql="select * from T_WF_CORE_TABLE_MAP_DATADIC where 1=1 "; 
		
		String rep_dicId = "";
		if(CommonUtil.stringNotNULL(dicId)){
			rep_dicId = dicId.replaceAll("%", "\\\\%");
			rep_dicId = rep_dicId.replace("_", "\\_");
			sql += " and dataDicId like :dicId ";
			if(rep_dicId!=null && !rep_dicId.equals(dicId)){
				sql += " escape '\\'";
			}
		}
		
		String rep_matchCode = "";
		if(CommonUtil.stringNotNULL(matchCode)){
			rep_matchCode = matchCode.replaceAll("%", "\\\\%");
			rep_matchCode = rep_matchCode.replace("_", "\\_");
			sql += " and tableCode like :tableCode ";
			if(rep_matchCode!=null && !rep_matchCode.equals(matchCode)){
				sql += " escape '\\'";
			}
		}
		
		if(CommonUtil.stringNotNULL(id)){
			sql += " and id = '" + id + "'"; 
		}
		sql += "and status=0  order by createDate";
		Query query = getEm().createNativeQuery(sql,DataDicMatch.class);
		
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		if(CommonUtil.stringNotNULL(dicId) ){
			query.setParameter("dicId", "%"+rep_dicId.trim()+"%");
		}
		if(CommonUtil.stringNotNULL(matchCode) ){
			query.setParameter("tableCode", "%"+rep_matchCode.trim()+"%");
		}
		List<DataDicMatch> list=query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataDicMatch getDataDicMatchById(HashMap<String, String> map){
		String id = map.get("id");
		String sql="select * from T_WF_CORE_TABLE_MAP_DATADIC where 1=1 "; 
		if(CommonUtil.stringNotNULL(id)){
			sql += " and id='" + id + "'"; 
		}
		sql += "and status=0  order by createDate";
		Query query = getEm().createNativeQuery(sql,DataDicMatch.class);
		List<DataDicMatch> list=query.getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	@Override
	public void addMatch(DataDicMatch dataDicMatch){
		this.getEm().persist(dataDicMatch);
	}
	
	@Override
	public void updateMatch(DataDicMatch dataDicMatch){
		this.getEm().merge(dataDicMatch);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DataDicTable> getDataDicTableById(HashMap<String, String> map) {
		String dicId = map.get("dicId");
		String sql="select * from T_WF_CORE_DATADICTABLE where 1=1 "; 
		if(CommonUtil.stringNotNULL(dicId)){
			sql += " and dataDicId='" + dicId + "'"; 
		}
		sql += "and status=0  order by createDate";
		Query query = getEm().createNativeQuery(sql,DataDicTable.class);
		List<DataDicTable> list=query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DataDicColumnMatch getDataDicColumnMatch(HashMap<String, String> map){
		String matchId = map.get("matchId");
		String sql="select * from T_WF_CORE_TABLE_MAP_DATADIC where 1=1 "; 
		if(CommonUtil.stringNotNULL(matchId)){
			sql += " and id='" + matchId + "'"; 
		}
		sql += "and status=0  order by createDate";
		Query query = getEm().createNativeQuery(sql,DataDicColumnMatch.class);
		List<DataDicColumnMatch> list=query.getResultList();
		if(null!=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ColumnMapColumn> getColumnMapColumnList(HashMap<String, String> map) {
		String dataDicId = map.get("dataDicId");
		String tableName = map.get("tableName");
		String tableName_local = map.get("tableName_local");
		String is_Show = map.get("is_Show");
		String is_WriteBack = map.get("is_WriteBack");
		String notNull = map.get("notNull");
		String sql="select c.* from T_WF_CORE_COLUMN_MAP_COLUMN c left join t_wf_core_columnpermit t on c.id=t.matchid where 1=1 "; 
		if(CommonUtil.stringNotNULL(dataDicId)){
			sql += " and c.dataDicId='" + dataDicId + "'"; 
		}
		if(CommonUtil.stringNotNULL(tableName)){
			sql += " and c.tableName='" + tableName + "'"; 
		}
		if(CommonUtil.stringNotNULL(tableName_local)){
			sql += " and c.tableName_local='" + tableName_local + "'"; 
		}
		if(CommonUtil.stringNotNULL(notNull)){
			sql += " and c.columnName_local is not null and columnName_local != 'null' "; 
		}
		if(CommonUtil.stringNotNULL(is_Show) && !"1".equals(is_WriteBack)){
			sql += " and t.is_show = '1' "; 
		}
		if(CommonUtil.stringNotNULL(is_WriteBack)){
			sql += " and t.is_back = '1' "; 
		}
		
		sql += " and c.status=0  order by t.sort";
		Query query = getEm().createNativeQuery(sql,ColumnMapColumn.class);
		List<ColumnMapColumn> list=query.getResultList();
		return list;
	}
	
    public void addColumnMapColumn(ColumnMapColumn columnMapColumn){
    	this.getEm().persist(columnMapColumn);
    }
	
	public void updateColumnMapColumn(ColumnMapColumn columnMapColumn){
		this.getEm().merge(columnMapColumn);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ColumnMapColumn> getUpdateColumnMap(HashMap<String, String> map) throws Exception {
		String id = map.get("id");
		String sql="select * from T_WF_CORE_COLUMN_MAP_COLUMN where 1=1 "; 
		if(CommonUtil.stringNotNULL(id)){
			sql += " and id='" + id + "'"; 
		}
		sql += "and status=0  order by px";
		Query query = getEm().createNativeQuery(sql,ColumnMapColumn.class);
		List<ColumnMapColumn> list=query.getResultList();
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getDataCountFromDataSource(HashMap<String,String> map,List<ColumnMapColumn> cpLists,List<ColumnMapColumn> cmcList,List<Object[]> fiterList) throws Exception{
		String modId = map.get("modId");
		String tablesql = map.get("tablesql");
		//查出当前模块id对应的数据库地址，用户名，密码
		String hql ="from BusModule t where t.id='" + modId + "'";
		Query query = getEm().createQuery(hql);
		List<BusModule> busMod = query.getResultList();
		String url=busMod.get(0).getDataAddr();
		String userName=busMod.get(0).getDataAccount();
		String passWord=busMod.get(0).getDataPassword();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		int resultCount = 0 ;
		try{
			Connection conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql=" select count(*) as rowcount " ;
			sql += " from " + tablesql + " where 1=1 ";
			if(null!=cpLists&&cpLists.size()>0){
				for(ColumnMapColumn c:cpLists){
					if("2".equals(c.getColumnType())){
						String str = c.getColumnName();
						String str_now_begin = c.getSearchValue_begin();
						String str_now_end = c.getSearchValue_end();
						if(CommonUtil.stringNotNULL(str_now_begin)){
							str_now_begin = str_now_begin + " 00:00:00";
							sql += " and " + str + " >  to_date('" + str_now_begin + "','yyyy-MM-dd hh24:mi:ss')" ;
						}
						if(CommonUtil.stringNotNULL(str_now_end)){
							str_now_end = str_now_end + " 23:59:59";
							sql += " and " + str + " <  to_date('" + str_now_end + "','yyyy-MM-dd hh24:mi:ss')" ;
						}
						
					}else{
						String str = c.getColumnName();
						String str_now = map.get(str)==null?"":map.get(str).trim();
						sql += " and ( " + str + " like '%" + str_now  +"%' or " + str + " is null )" ;
					}
				}
			}
			if(null != cmcList && cmcList.size()>0){
				for(ColumnMapColumn c:cmcList){
					sql += " and (" + c.getColumnName() + " != '@'  or " + c.getColumnName() + " is null )";
				}
			}
			if(null != fiterList && fiterList.size()>0){
				for(Object[] o:fiterList){
					if(null!=o[2]&&"2".equals(o[2])){
						String filter = o[1].toString();
						if(CommonUtil.stringNotNULL(filter) && filter.indexOf(",")>-1){
							String[] filters = filter.split(",");
							String filter_begin = filter.split(",")[0];
							if(CommonUtil.stringNotNULL(filter_begin)){
								filter_begin = filter_begin + " 00:00:00";
								sql += " and " + o[0] + " >  to_date('" + filter_begin + "','yyyy-MM-dd hh24:mi:ss')" ;
							}
							if(null!=filters&&filters.length>1){
								String filter_end = filter.split(",")[1];
								if(CommonUtil.stringNotNULL(filter_end)){
									filter_end = filter_end + " 23:59:59";
									sql += " and " + o[0] + " <  to_date('" + filter_end + "','yyyy-MM-dd hh24:mi:ss')" ;
								}
							}
						}
					}else{
						sql += " and " + o[0] + " like '%"+o[1]+"%'";
					}
				}
			}
			PreparedStatement ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			rs.next();
			resultCount = rs.getInt("rowcount");
			closeJDBC(conn,ps,rs);
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}
		return (Integer)resultCount; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getDataFromDataSource(HashMap<String,String> map,List<ColumnMapColumn> cpLists,List<ColumnMapColumn> cmcList, List<Object[]> fiterList ,Integer pageindex, Integer pagesize) throws Exception{
		String modId = map.get("modId");
		String columnsql = map.get("columnsql");
		String processId_dic = map.get("processId_dic");
		String[] columns = columnsql.split(",");
		String tablesql = map.get("tablesql");
		String clobIndex = map.get("clobIndex");
		String tableName= map.get("tableName");
		//查出当前模块id对应的数据库地址，用户名，密码
		String hql ="from BusModule t where t.id='" + modId + "'";
		Query query = getEm().createQuery(hql);
		List<BusModule> busMod = query.getResultList();
		String url=busMod.get(0).getDataAddr();
		String userName=busMod.get(0).getDataAccount();
		String passWord=busMod.get(0).getDataPassword();
		List<Object[]> list=new ArrayList<Object[]>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		try{
			String primaryId = getPrimaryIdByTable(tableName,modId);
			if(CommonUtil.stringIsNULL(primaryId)){
				primaryId = SystemParamConfigUtil.getParamValueByParam("primaryId");
			}
			if(CommonUtil.stringIsNULL(primaryId)){
				primaryId = "Id";
			}
			Connection conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql=" select " + columnsql ;
			if(CommonUtil.stringNotNULL(primaryId)){
				sql += ","+primaryId;
			}
			sql += " from " + tablesql + " where 1=1 ";
			if(CommonUtil.stringNotNULL(processId_dic)){
				sql += " and "+ primaryId  + " = '" + processId_dic + "' ";
			}
			if(null!=cpLists&&cpLists.size()>0){
				for(ColumnMapColumn c:cpLists){
					if("2".equals(c.getColumnType())){//字段类型为date
						String str = c.getColumnName();
						String str_now_begin = c.getSearchValue_begin();
						String str_now_end = c.getSearchValue_end();
						if(CommonUtil.stringNotNULL(str_now_begin)){
							str_now_begin = str_now_begin + " 00:00:00";
							sql += " and " + str + " >  to_date('" + str_now_begin + "','yyyy-MM-dd hh24:mi:ss')" ;
						}
						if(CommonUtil.stringNotNULL(str_now_end)){
							str_now_end = str_now_end + " 23:59:59";
							sql += " and " + str + " <  to_date('" + str_now_end + "','yyyy-MM-dd hh24:mi:ss')" ;
						}
						
					}else if("1".equals(c.getColumnType())){//字段类型为clob
						String str = c.getColumnName();
						String str_now = map.get(str)==null?"":map.get(str).trim();
						sql += " and ( " + str + " like '%" + str_now  +"%' or " + str + " is null )" ;
					}else{//字段类型为var
						String str = c.getColumnName();
						String str_now = map.get(str)==null?"":map.get(str).trim();
						sql += " and ( " + str + " like '%" + str_now  +"%' or " + str + " is null )" ;
					}
				}
			}
			if(null != cmcList && cmcList.size()>0){
				for(ColumnMapColumn c:cmcList){
					sql += " and (" + c.getColumnName() + " != '@'  or " + c.getColumnName() + " is null )";
				}
			}
			if(null != fiterList && fiterList.size()>0){
				for(Object[] o:fiterList){
					if(null!=o[2]&&"2".equals(o[2])){
						String filter = o[1].toString();
						if(CommonUtil.stringNotNULL(filter) && filter.indexOf(",")>-1){
							String[] filters = filter.split(",");
							String filter_begin = filter.split(",")[0];
							if(CommonUtil.stringNotNULL(filter_begin)){
								filter_begin = filter_begin + " 00:00:00";
								sql += " and " + o[0] + " >  to_date('" + filter_begin + "','yyyy-MM-dd hh24:mi:ss')" ;
							}
							if(null!=filters&&filters.length>1){
								String filter_end = filter.split(",")[1];
								if(CommonUtil.stringNotNULL(filter_end)){
									filter_end = filter_end + " 23:59:59";
									sql += " and " + o[0] + " <  to_date('" + filter_end + "','yyyy-MM-dd hh24:mi:ss')" ;
								}
							}
						}
					}else{
						sql += " and " + o[0] + " like '%"+o[1]+"%'";
					}
				}
			}
			PreparedStatement ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if(pageindex != null && pagesize != null){
				ps.setMaxRows(pageindex + pagesize);
			}
			rs=ps.executeQuery();
			if (pageindex != null && pagesize != null) {
				rs.first();
				rs.relative(pageindex-1);
			}
			while(rs.next()){
				if(null != columns && columns.length>0){
					Object[] object=new Object[columns.length];
					if(CommonUtil.stringNotNULL(primaryId)){
						object=new Object[columns.length+1];
					}
					for(int i=0;i<columns.length;i++){
						boolean isClob = false;
						String[] clobIndexs = clobIndex.split(",");
						if(null != clobIndexs && clobIndexs.length>0){
							for (String s : clobIndexs){
								if(s.equals(i+"")){
									isClob = true;
								}
							}
						}
						if(isClob){
							CLOB clob = (CLOB)rs.getClob(columns[i]);
							String str = ClobToString.clob2String(clob);
							if(null!=str && str.indexOf("*")>-1){
								String[] strs = str.split("\\*");
								if(null!=str && strs.length>1){
									object[i]= strs[1];
								}else{
									object[i]= strs[0];
								}
							}else{
								object[i]=str;
							}
						}else{
							object[i]=rs.getString(columns[i]);
						}
					}
					if(CommonUtil.stringNotNULL(primaryId)){
						object[columns.length] = rs.getString(primaryId);
					}
				    list.add(object);
				}
			}
			closeJDBC(conn,ps,rs);
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}
		return list; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getIdListsByTableName (HashMap<String,String> map) throws Exception{
		String tableName_local = map.get("tableName_local");
		String sql="select t.lcid,t.reflc from t_wf_core_table t  where  t.vc_tablename='"+ tableName_local +"'"; 
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list=query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ColumnPermit> getColPerByMatchId (HashMap<String,String> map) throws Exception{
		String matchId = map.get("matchId");
		String sql="select t.* from T_WF_CORE_COLUMNPERMIT t  where  t.matchId ='"+ matchId +"'"; 
		Query query = getEm().createNativeQuery(sql,ColumnPermit.class);
		List<ColumnPermit> list=query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ColumnPermit> getUpdateColumnPermit(HashMap<String, String> map) throws Exception {
		String id = map.get("id");
		String sql="select * from T_WF_CORE_COLUMNPERMIT where 1=1 "; 
		if(CommonUtil.stringNotNULL(id)){
			sql += " and id='" + id + "'"; 
		}
		sql += "and status=0  order by sort";
		Query query = getEm().createNativeQuery(sql,ColumnPermit.class);
		List<ColumnPermit> list=query.getResultList();
		return list;
	}
	
	public void addColumnPermit(ColumnPermit columnPermit){
    	this.getEm().persist(columnPermit);
    }
	
	public void updateColumnPermit(ColumnPermit columnPermit){
		this.getEm().merge(columnPermit);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ColumnMapColumn> getColumnSearchList(HashMap<String, String> map) {
		String dataDicId = map.get("dataDicId");
		String tableName = map.get("tableName");
		String tableName_local = map.get("tableName_local");
		String is_Show = map.get("is_Show");
		String is_Search = map.get("is_Search");
		String notNull = map.get("notNull");
		String sql="select c.* from T_WF_CORE_COLUMN_MAP_COLUMN c left join t_wf_core_columnpermit t on c.id=t.matchid where 1=1 "; 
		if(CommonUtil.stringNotNULL(dataDicId)){
			sql += " and c.dataDicId='" + dataDicId + "'"; 
		}
		if(CommonUtil.stringNotNULL(tableName)){
			sql += " and c.tableName='" + tableName + "'"; 
		}
		if(CommonUtil.stringNotNULL(tableName_local)){
			sql += " and c.tableName_local='" + tableName_local + "'"; 
		}
		if(CommonUtil.stringNotNULL(notNull)){
			sql += " and c.columnName_local is not null and columnName_local != 'null' "; 
		}
		if(CommonUtil.stringNotNULL(is_Show)){
			sql += " and t.is_show = '1' "; 
		}
		if(CommonUtil.stringNotNULL(is_Search)){
			sql += " and t.is_Search = '1' "; 
		}
		
		sql += " and c.status=0  order by t.sort";
		Query query = getEm().createNativeQuery(sql,ColumnMapColumn.class);
		List<ColumnMapColumn> list=query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void writeBackDataSource(HashMap<String,String> map,List<ColumnMapColumn> cmcList) throws Exception{
		String modId = map.get("modId");
		String tablesql = "";
		String columnsql = "";
		String dicValue = map.get("dicValue");
		String tableName = "";
		if(null != cmcList && cmcList.size()>0){
			tablesql = cmcList.get(0).getTableName();
			for(ColumnMapColumn c:cmcList){
				columnsql += c.getColumnName() + " = '@' ,";
				tableName = c.getTableName();
			}
			columnsql = columnsql.substring(0,columnsql.length()-1);
		}
		String primaryId = getPrimaryIdByTable(tableName,modId);
		if(CommonUtil.stringIsNULL(primaryId)){
			primaryId = SystemParamConfigUtil.getParamValueByParam("primaryId");
		}
		if(CommonUtil.stringIsNULL(primaryId)){
			primaryId = "Id";
		}
		if(CommonUtil.stringNotNULL(tablesql) && CommonUtil.stringNotNULL(columnsql) && CommonUtil.stringNotNULL(dicValue)){
			//查出当前模块id对应的数据库地址，用户名，密码
			String hql ="from BusModule t where t.id='" + modId + "'";
			Query query = getEm().createQuery(hql);
			List<BusModule> busMod = query.getResultList();
			String url=busMod.get(0).getDataAddr();
			String userName=busMod.get(0).getDataAccount();
			String passWord=busMod.get(0).getDataPassword();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			try{
				Connection conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
				String sql=" update " +  tablesql ;
				sql += " set  " + columnsql + " where 1=1 and " + primaryId + " = '" + dicValue + "'" ;
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.executeQuery();
				closeJDBC(conn,ps,null);
			}catch(SQLException se){
			    se.printStackTrace() ;   
			}
		}
	}
	
	@Override
	public void delPermit(HashMap<String,String> map) throws Exception{
		String matchIds = map.get("matchIds");
		if(CommonUtil.stringNotNULL(matchIds)){
			String sql = "delete from  t_wf_core_columnpermit t  where 1=1 ";
			sql += " and t.matchid in (" + matchIds + ")";
			getEm().createNativeQuery(sql).executeUpdate();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFiterListFromColPer(HashMap<String, String> map) {
		String dataDicId = map.get("dataDicId");
		String tableName = map.get("tableName");
		String tableName_local = map.get("tableName_local");
		String is_Show = map.get("is_Show");
		String notNull = map.get("notNull");
		String sql="select c.columnname,t.filter,c.columntype from T_WF_CORE_COLUMN_MAP_COLUMN c left join t_wf_core_columnpermit t on c.id=t.matchid where 1=1 "; 
		if(CommonUtil.stringNotNULL(dataDicId)){
			sql += " and c.dataDicId='" + dataDicId + "'"; 
		}
		if(CommonUtil.stringNotNULL(tableName)){
			sql += " and c.tableName='" + tableName + "'"; 
		}
		if(CommonUtil.stringNotNULL(tableName_local)){
			sql += " and c.tableName_local='" + tableName_local + "'"; 
		}
		if(CommonUtil.stringNotNULL(notNull)){
			sql += " and c.columnName_local is not null and columnName_local != 'null' "; 
		}
		if(CommonUtil.stringNotNULL(is_Show)){
			sql += " and t.is_show = '1' "; 
		}
		sql += " and t.filter is not null  "; 
		
		sql += " and c.status=0  order by t.sort";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list=query.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getPrimaryIdByTable(String tableName,String modId) throws Exception{
		String primaryId = "";
		String hql ="from BusModule t where t.id='" + modId + "'";
		Query query = getEm().createQuery(hql);
		List<BusModule> busMod = query.getResultList();
		String url=busMod.get(0).getDataAddr();
		String userName=busMod.get(0).getDataAccount();
		String passWord=busMod.get(0).getDataPassword();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		try {
			Connection conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql = "select a.constraint_name,a.column_name from user_cons_columns a,user_constraints b" +
					" where a.constraint_name=b.constraint_name and b.constraint_type='P' " +
					"and a.table_name='" + tableName.toUpperCase() + "' ";
			PreparedStatement ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			while(rs.next()){
				primaryId = rs.getString("column_name");
			}
			if(CommonUtil.stringIsNULL(primaryId)){//获取表第一个字段
				rs = null;
				String sql_firstCol = "select t.column_name from sys.all_tab_columns t where table_name ='" + tableName.toUpperCase() +
						" 'and t.OWNER = '" + userName.toUpperCase()+ "' and t.COLUMN_ID='1'";
				PreparedStatement ps_firstCol = conn.prepareStatement(sql_firstCol,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs=ps_firstCol.executeQuery();
				while(rs.next()){
					primaryId = rs.getString("column_name");
				}
			}
			closeJDBC(conn,ps,rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return primaryId;
	}
	
	public void closeJDBC(Connection conn , PreparedStatement ps , ResultSet rs){
			try {
				if(null != rs){
					rs.close();
				}
				if(null != ps){
					ps.close();
				}
				if(null != conn){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
