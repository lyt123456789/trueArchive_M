package cn.com.trueway.base.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.trueway.sys.util.SystemParamConfigUtil;

public class MySqlJDBCUtil {
	
	public static Connection getConnection()  {
		Connection conn = null;
		String url = SystemParamConfigUtil.getParamValueByParam("mySqlUrl");
		String userName = SystemParamConfigUtil.getParamValueByParam("mySqlUserName");
		String passWord = SystemParamConfigUtil.getParamValueByParam("mySqlPassword");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn =DriverManager.getConnection(url,userName,passWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeJDBC(Connection conn , PreparedStatement ps , ResultSet rs){
		try {
			if(null != rs){
				rs.close();
				rs=null;
			}
			if(null != ps){
				ps.close();
				ps=null;
			}
			if(null != conn){
				conn.close();
				conn=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
