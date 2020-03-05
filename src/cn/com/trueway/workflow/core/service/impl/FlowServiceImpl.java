package cn.com.trueway.workflow.core.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import oracle.sql.BLOB;

import cn.com.trueway.base.util.JDBCBase;
import cn.com.trueway.workflow.core.dao.FlowDao;
import cn.com.trueway.workflow.core.service.FlowService;

public class FlowServiceImpl implements FlowService {

	private FlowDao flowDao;
	
	public FlowDao getFlowDao() {
		return flowDao;
	}
	public void setFlowDao(FlowDao flowDao) {
		this.flowDao = flowDao;
	}
	
	public JDBCBase jdbcBase;

	public JDBCBase getJdbcBase() {
		return jdbcBase;
	}

	public void setJdbcBase(JDBCBase jdbcBase) {
		this.jdbcBase = jdbcBase;
	}

	public void geneSql(String tableName, HashMap<String, String> keyValueSet,
			String whereKey, String whereValue) {
		StringBuffer sql = new StringBuffer("Update "+tableName +" set ");
		StringBuffer select  = new StringBuffer("select ");
		Object[] params = null;
		if(keyValueSet != null && keyValueSet.size() > 0){
			// 遍历map 
			params = new Object[keyValueSet.size()];
			 Set<String>  keys = keyValueSet.keySet();
			 int i = 0;
			 for (Iterator<String> it=keys.iterator();it.hasNext();) {
					String key=(String)it.next();
					String value=keyValueSet.get(key);
					params[i] = value;
					if(i == 0){
						sql.append(key +" = ?");
						select.append(key);
					}else{
						sql.append(" , "+key +" = ? ");
						select.append(" , "+ key );
					}
					i++;
			}
		}
		sql.append(" where "+whereKey+" = '"+whereValue+"'");
		select.append(" from "+tableName+" where "+whereKey+" = '"+whereValue+"' for update" );
		flowDao.executeSql(sql.toString(),params,select.toString());
	}
	
	@Override
	public BLOB getBlob(String tablename, String fieldname,String key,String value){
		PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    oracle.sql.BLOB blob = null;
	    try {
		Connection conn = jdbcBase.getCon();
	    String sql = "select "+fieldname.trim() + " from " + tablename + " where "+key+" ='" + value + "'";
	    pstmt = conn.prepareStatement(sql);
	    rs = pstmt.executeQuery();
	  
	    if (rs.next()) {
	    	blob = (oracle.sql.BLOB)rs.getBlob(fieldname.trim());
	    }
	    }catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
	             rs.close();
	             rs=null;
	             pstmt.close();
	             pstmt=null;
	         } catch (SQLException ex) {
	         }
		}
		return blob;
	}
}
