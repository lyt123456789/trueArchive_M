package cn.com.trueway.archiveModel.listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import cn.com.trueway.base.util.JDBCBase;

public class initDataListener extends JDBCBase implements ServletContextListener {

	private static Map<String,Object> treeJsonMap = new LinkedHashMap<String,Object>();
	
	public static Map<String, Object> getTreeJsonMap() {
		return treeJsonMap;
	}

	public static void setTreeJsonMap(Map<String, Object> treeJsonMap) {
		initDataListener.treeJsonMap = treeJsonMap;
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}	

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//初始化数据树
		initTreeJson();
	}	
	
	/**
	 * 初始化数据树
	 */
	public void initTreeJson(){
		StringBuilder json = new StringBuilder();
		json.append("{\"status\":{\"code\":200,\"message\":\"操作成功\"},\"data\": [");
		StringBuffer sql = new StringBuffer("select t.id,t.title,t.id_parent_node,t.id_structure from ESS_TREE t where t.id_business = 1 ");
		sql.append(" order by t.esorder");
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {
				String _id = String.valueOf(rs.getObject("id"));
				String title = rs.getString("title");
				//String espath = rs.getString("espath");
				String id_parent_node = String.valueOf(rs.getObject("id_parent_node"));
				//String id_business = String.valueOf(rs.getObject("id_business"));
				String id_structure = String.valueOf(rs.getObject("id_structure"));
				//String id_modelstructure = String.valueOf(rs.getObject("id_modelstructure"));
				//String esorder = String.valueOf(rs.getObject("esorder"));
				//String isleaf = String.valueOf(rs.getObject("isleaf"));
				//String id_seq = rs.getString("id_seq");
				
				json.append("{");
				json.append("\"id\":\"").append(_id).append("\",");
				json.append("\"title\":\"").append(title).append("\",");
				json.append("\"parentId\":\"").append(id_parent_node).append("\",");
				json.append("\"checkArr\":\"").append("0\",");
				json.append("\"basicData\":").append(id_structure).append("");
				//json.append("\"basicData\":{\"nodeType\":\"").append(e.getNodeType()).append("\"}");
				json.append("},");
				
				//子结构
				/*if(idStructure!=null && idStructure != 0) {
					List<Map<String, Object>> strucList = essModelDao.getChildStructure(idStructure);
					if(strucList!=null){
						for(Map<String, Object> map:strucList) {
							String idChild = map.get("IDCHILD").toString();
							String title = map.get("TITLE").toString();
							String parentId = map.get("PID").toString();
							json.append("{");
							json.append("\"id\":\"").append(parentId+"-"+idChild).append("\",");
							json.append("\"title\":\"").append(title).append("\",");
							json.append("\"parentId\":\"").append(parentId).append("\",");
							json.append("\"basicData\":").append(idChild).append("");
							json.append("},");
						}
					}
				
				}*/
			}
			closeCon(rs,pstmt,con);
		
			int index = json.lastIndexOf(",");
			if(index>-1){
				json.deleteCharAt(index);
			}
			json.append("]}");
			treeJsonMap.put("treeJson", json);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 重载数据树
	 */
	public void reLoadTreeJson() {
		treeJsonMap.clear();
		initTreeJson();
	}

}
