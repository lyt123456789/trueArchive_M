package cn.com.trueway.archives.reports.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.archives.reports.dao.ReportsDao;
import cn.com.trueway.archives.reports.pojo.WorkStatistics;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.SystemParamConfigUtil2;

@SuppressWarnings("unchecked")
public class ReportsDaoImpl extends BaseDao implements ReportsDao {

	@Override
	public List<WorkStatistics> getPeopleData(String sql) {
		Query query = this.getEm().createNativeQuery(sql);
		List<String> resultList = query.getResultList();
		List<WorkStatistics> list = new ArrayList<WorkStatistics>();
		if(resultList != null && !resultList.isEmpty()) {
			for(int i = 0; i < resultList.size(); i++) {
				String object = resultList.get(i);
				WorkStatistics wk = new WorkStatistics();
				if(object == null || "".equals(object)) {
					continue;
				}
				wk.setReceivePeople(object);
				list.add(wk);
			}
			return list;
		} else {
			return null;
		}
		
	}

	@Override
	public String getUsingPeopleNum(String sql) {
		BigDecimal count = (BigDecimal) this.getEm().createNativeQuery(sql).getSingleResult();
		String scount = "0";
		if(count == null) {
			scount = "0";
		} else {
			scount = count.stripTrailingZeros().toPlainString();
		}
		return scount;
	}

	@Override
	public Map<String, String> getStoreStatistics(String sql) {
		Query query = this.getEm().createNativeQuery(sql);
		List<Object[]> resultList = query.getResultList();
		Map<String,String> map = new HashMap<String,String>();
		if(resultList != null && !resultList.isEmpty()) {
			for(int i = 0; i < resultList.size(); i++) {
				Object[] object = resultList.get(i);
				map.put("count", object[0]==null?"0":object[0].toString());
				map.put("sum", object[1]==null?"0":object[1].toString());
			}
			return map;
		} else {
			return null;
		}
	}

	@Override
	public List<WorkStatistics> getAllReceivePeople(String name) {
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		List<WorkStatistics> list = new ArrayList<WorkStatistics>();
		StringBuffer sql = new StringBuffer("select a.id,a.esidentifier from ESS_PROPVALUE a,ESS_PROP b,ESS_METADATA c where");
		sql.append(" a.id_prop=b.id and b.id_metadata=c.id and c.esidentifier='jdr'");
		if(name != null && !"".equals(name)) {
			sql.append("and a.esidentifier='" + name + "'");
		}
		sql.append("and a.esidentifier<>'Guest'");
		try {
			String jdbc_driverClassName=SystemParamConfigUtil2.getParamValueByParam("jdbc.driverClassName2","hibernate.properties");
			String jdbc_url=SystemParamConfigUtil2.getParamValueByParam("jdbc.url2","hibernate.properties");
			String jdbc_username=SystemParamConfigUtil2.getParamValueByParam("jdbc.username2","hibernate.properties");
			String jdbc_password=SystemParamConfigUtil2.getParamValueByParam("jdbc.password2","hibernate.properties");
			try {
				Class.forName(jdbc_driverClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			con = DriverManager.getConnection(jdbc_url,jdbc_username,jdbc_password); 
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();    
			while (rs.next()) {
				String id = String.valueOf(rs.getObject("id"));
				String sname = String.valueOf(rs.getObject("esidentifier"));
				WorkStatistics wk = new WorkStatistics();
				wk.setId(id);
				wk.setReceivePeople(sname);
				list.add(wk);
			}
			closeCon(rs,pstmt,con);   
		} catch (SQLException e) {
			e.printStackTrace();
			closeCon(rs,pstmt,con);   
		} 
        return list;
	}
	
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

	@Override
	public List<Map<String, String>> getSysDicResult(String sql) {
		Query query = this.getEm().createNativeQuery(sql);
		List<Object[]> resultList = query.getResultList();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if(resultList != null && !resultList.isEmpty()) {
			for(int i = 0; i < resultList.size(); i++) {
				Map<String,String> map = new HashMap<String,String>();
				Object[] object = resultList.get(i);
				map.put("id", object[0]==null?"0":object[0].toString());
				map.put("name", object[1]==null?"0":object[1].toString());
				list.add(map);
			}
			return list;
		} else {
			return null;
		}
	}

	@Override
	public boolean checkRecord(String sql) {
		Query query = this.getEm().createNativeQuery(sql);
		List<Object[]> resultList = query.getResultList();
		if(resultList == null || resultList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<WorkStatistics> getBorrowCdmdList() {
		String sql = "select dataname from t_using_basicdata where type='cdmd' order by numindex";
		Query query = this.getEm().createNativeQuery(sql);
		List<String> resultList = query.getResultList();
		List<WorkStatistics> list = new ArrayList<WorkStatistics>();
		if(resultList != null && !resultList.isEmpty()) {
			for(int i = 0; i < resultList.size(); i++) {
				String object = resultList.get(i);
				WorkStatistics wk = new WorkStatistics();
				if(object == null || "".equals(object)) {
					continue;
				}
				wk.setBorrowCdmd(object);
				list.add(wk);
			}
			return list;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, String> getBorrowDataCount(String sql) {
		Query query = this.getEm().createNativeQuery(sql);
		List<Object[]> resultList = query.getResultList();
		if(resultList != null && !resultList.isEmpty()) {
			Map<String,String> map = new HashMap<String,String>();
			Object[] object = resultList.get(0);
			map.put("juanCount", object[0]==null?"0":object[0].toString());
			map.put("jianCount", object[1]==null?"0":object[1].toString());
			map.put("dataCount", object[2]==null?"0":object[2].toString());
			return map;
		} else {
			return null;
		}
	}

	@Override
	public List<WorkStatistics> getBorrowOtherMdList() {
		String sql = "select dataname from t_using_basicdata where type='qtmd' order by numindex";
		Query query = this.getEm().createNativeQuery(sql);
		List<String> resultList = query.getResultList();
		List<WorkStatistics> list = new ArrayList<WorkStatistics>();
		if(resultList != null && !resultList.isEmpty()) {
			for(int i = 0; i < resultList.size(); i++) {
				String object = resultList.get(i);
				WorkStatistics wk = new WorkStatistics();
				if(object == null || "".equals(object)) {
					continue;
				}
				wk.setBorrowQtmd(object);
				list.add(wk);
			}
			return list;
		} else {
			return null;
		}
	}

	@Override
	public List<WorkStatistics> getBorrowArchiveList() {
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		List<WorkStatistics> list = new ArrayList<WorkStatistics>();
		StringBuffer sql = new StringBuffer("select a.id,a.esidentifier from ESS_PROPVALUE a,ESS_PROP b,ESS_METADATA c where");
		sql.append(" a.id_prop=b.id and b.id_metadata=c.id and c.esidentifier='ArchiveType' order by a.id");
		try {
			String jdbc_driverClassName=SystemParamConfigUtil2.getParamValueByParam("jdbc.driverClassName2","hibernate.properties");
			String jdbc_url=SystemParamConfigUtil2.getParamValueByParam("jdbc.url2","hibernate.properties");
			String jdbc_username=SystemParamConfigUtil2.getParamValueByParam("jdbc.username2","hibernate.properties");
			String jdbc_password=SystemParamConfigUtil2.getParamValueByParam("jdbc.password2","hibernate.properties");
			try {
				Class.forName(jdbc_driverClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
			con = DriverManager.getConnection(jdbc_url,jdbc_username,jdbc_password); 
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();    
			while (rs.next()) {
				String id = String.valueOf(rs.getObject("id"));
				String sname = String.valueOf(rs.getObject("esidentifier"));
				WorkStatistics wk = new WorkStatistics();
				wk.setId(id);
				wk.setBorrowArchive(sname);
				list.add(wk);
			}
			closeCon(rs,pstmt,con);   
		} catch (SQLException e) {
			e.printStackTrace();
			closeCon(rs,pstmt,con);   
		} 
        return list;
	}

}
