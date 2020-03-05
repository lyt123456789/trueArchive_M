package cn.com.trueway.workflow.core.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Query;

import oracle.sql.BLOB;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.FileUtils;
import cn.com.trueway.base.util.JDBCBase;
import cn.com.trueway.workflow.core.dao.FlowDao;

public class FlowDaoImpl extends BaseDao implements FlowDao {
	
	public JDBCBase jdbcBase;

	public JDBCBase getJdbcBase() {
		return jdbcBase;
	}

	public void setJdbcBase(JDBCBase jdbcBase) {
		this.jdbcBase = jdbcBase;
	}
	@Override
	public void executeSql(String sql,Object[] params,String selectSql) {
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
	    ResultSet rs=null;
	    ResultSet rs2=null;
	    try {
		Connection conn = jdbcBase.getCon();
	    //　先设置空 blob 
		String tempSql = sql.replace("?", "empty_blob()");
	    pstmt = conn.prepareStatement(tempSql);
	    pstmt.executeUpdate();
	    conn.commit();
	    pstmt.close();
	    pstmt2 = conn.prepareStatement(selectSql);
	    rs2 = pstmt2.executeQuery();
	    if(rs2.next()){
	    	 pstmt = conn.prepareStatement(sql);
	    	 for(int i = 0; i < params.length ; i++){
	 	    	BLOB blob  = (BLOB) rs2.getBlob(i+1);
	 	    	pstmt.setBlob(i+1,  FileUtils.readFileToByteArray(params[i].toString(),blob));
	 		 }
	    	pstmt.executeUpdate();
	    }
	    }catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(rs2 != null){
					rs2.close();
				}
	             rs=null;
	             rs2 = null;
	             pstmt.close();
	             pstmt2.close();
	             pstmt2 = null;
	             pstmt=null;
	         } catch (SQLException ex) {
	         }
		}
	}

	@Override
	public void executeSql(String sql,Object[] params) {
		Query query = this.getEm().createNativeQuery(sql);
		for(int i = 0; i < params.length ; i++){
			query.setParameter(i+1, FileUtils.readFileToByteArray(params[i].toString()));
		}
		query.executeUpdate();
	}
}
