package cn.com.trueway.base.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
* 描述：<对此类的描述，可以引用系统设计中的描述><br>                                     
* 作者：潘浩<br>
* 创建时间：2009-12-25 11:00<br>
* 版本：v1.0 <br>           
* JDK版本：JDK1.6
 */
public class JDBCBase extends JdbcDaoSupport{
	/** 
     * 获取数据库的连接 
     * @return 
     */ 
    public Connection getCon() { 
        Connection con = null; 
        try { 
            Class.forName(getDriverName()); 
            con = DriverManager.getConnection(getURL(),getUserName(),getPassword()); 
        } catch (ClassNotFoundException e) { 
            e.printStackTrace(); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } 
        return con;  
    } 
	public String getDriverName(){
		if (MyConstants.jdbc_driverClassName==null) {
			MyConstants.jdbc_driverClassName=SystemParamConfigUtil2.getParamValueByParam("jdbc.driverClassName2","hibernate.properties");
		}
		return MyConstants.jdbc_driverClassName;
	}
	public String getURL(){
		if (MyConstants.jdbc_url==null) {
			MyConstants.jdbc_url=SystemParamConfigUtil2.getParamValueByParam("jdbc.url2","hibernate.properties");
		}
		return MyConstants.jdbc_url;
	}
	public String getUserName(){
		if (MyConstants.jdbc_username==null) {
			MyConstants.jdbc_username=SystemParamConfigUtil2.getParamValueByParam("jdbc.username2","hibernate.properties");
		}
		return MyConstants.jdbc_username;
	}
	public String getPassword(){
		if (MyConstants.jdbc_password==null) {
			MyConstants.jdbc_password=SystemParamConfigUtil2.getParamValueByParam("jdbc.password2","hibernate.properties");
		}
		return MyConstants.jdbc_password;
	}
	/** 
     * rs,sta,con的关闭 
     * @param rs 
     * @param sta 
     * @param con 
     */ 
    public void closeCon(ResultSet rs, PreparedStatement sta, Connection con) { 
        try { 
            if(rs != null) { 
                rs.close(); 
            } 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
            try { 
                if(sta != null) { 
                    sta.close(); 
                } 
            } catch (SQLException e) { 
                e.printStackTrace(); 
            } finally { 
                try { 
                    if(con != null) { 
                        con.close();     
                    } 
                } catch (SQLException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
    }
    
    
    /** 
     *  该方法用来执行insert,update,delete操作 
     * @param sql SQL语句 
     * @param args  不定项参数 
     * @return 
     */ 
    public int executeSQL(String sql,Object...args) { 
        Connection con = getCon(); 
        PreparedStatement sta = null; 
        int rows = 0; 
        try { 
            sta = con.prepareStatement(sql); 
            //★注意下面的循环语句★ 
            for (int i = 0; i < args.length; i++) { 
                sta.setObject(i+1, args[i]); //为什么是i+1呢？因为你从前面的文章知道，那是从1开始的！ 
            } 
            rows = sta.executeUpdate(); 
            if(rows > 0) { 
                System.out.println("operate successfully !"); 
            } else { 
                System.out.println("fail!"); 
            } 
             
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
        	closeCon(null,sta, con); 
        } 
        return rows; 
    }
    
    public List<Map<String, Object>> querySQL(String sql,String mapColumn,Object...args) { 
        Connection con = getCon(); 
        PreparedStatement sta = null; 
        ResultSet rs=null;
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        try { 
            sta = con.prepareStatement(sql); 
            //★注意下面的循环语句★ 
            for (int i = 0; i < args.length; i++) { 
                sta.setObject(i+1, args[i]); //为什么是i+1呢？因为你从前面的文章知道，那是从1开始的！ 
            } 
            rs=sta.executeQuery();
            while(rs.next()) { 
            	if (mapColumn!=null&&!mapColumn.equals("")) {
            		Map<String, Object> m=new HashMap<String, Object>();
					String[] mapColumns=mapColumn.split(",");
					for (int i = 0; i < mapColumns.length; i++) {
						m.put(mapColumns[i], rs.getObject(mapColumns[i]));
					}
					list.add(m);
				}
            } 
             
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } finally { 
        	closeCon(null,sta, con); 
        } 
        return list; 
    }
    
}
