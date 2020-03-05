package cn.com.trueway.archives.manage.dao.impl;

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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.lob.SerializableClob;
import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archives.manage.dao.FullTextManageDao;
import cn.com.trueway.archives.manage.dao.RoleManageDao;
import cn.com.trueway.archives.manage.pojo.CasualUser;
import cn.com.trueway.archives.manage.pojo.Menu;
import cn.com.trueway.archives.manage.pojo.RoleEmployee;
import cn.com.trueway.archives.manage.pojo.RoleMenu;
import cn.com.trueway.archives.manage.pojo.StructsOfRole;
import cn.com.trueway.archives.manage.pojo.TreeNodeOfRole;
import cn.com.trueway.archives.using.dao.ArchiveUsingDao;
import cn.com.trueway.archives.using.pojo.ArchiveNode;
import cn.com.trueway.archives.using.pojo.Basicdata;
import cn.com.trueway.archives.using.util.ChangeDataType;
import cn.com.trueway.archives.manage.pojo.TrueArchiveRole;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.SystemParamConfigUtil2;
import cn.com.trueway.workflow.core.dao.AttachmentTypeDao;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfNode;

@SuppressWarnings("unchecked")
public class FullTextManageDaoImpl extends BaseDao implements  FullTextManageDao {

	@Override
	public List<EssTree> getTreeNodesList(Map<String, String> map) {
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		List<EssTree> list = new ArrayList<EssTree>();
		StringBuffer sql = new StringBuffer("");
		String treeId = map.get("treeId");
		String type = map.get("type");//father:往前递归     son:往后递归
		if("father".equals(type)){
			sql.append(" select t.id,t.title,t.id_structure from ESS_TREE t  connect by t.id= prior t.id_parent_node start with t.id='"+treeId+"' ");
		}else{
			sql.append(" select t.id,t.title,t.id_structure from ESS_TREE t  where id_structure <>0 and id_structure <>-1   connect by t.id_parent_node= prior t.id start with t.id='"+treeId+"' ");
		}
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
				String _id = String.valueOf(rs.getObject("id"));
				String title = rs.getString("title");
				String id_structure = rs.getString("id_structure");
				EssTree et = new EssTree();
				et.setId(Integer.valueOf(_id));
				et.setTitle(title);
				et.setIdStructure(Integer.valueOf(id_structure));
				list.add(et);
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
	public int getFullTextIndexCount(Map<String, String> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from t_archive_fulltextindex where 1=1 ");
		String treeNodes1 = map.get("treeNodes1");
		if(StringUtils.isNotBlank(treeNodes1)) {
			sql.append(" and ( treenode in ("+treeNodes1+") " );
			String treeNodes2 = map.get("treeNodes2");
			if(StringUtils.isNotBlank(treeNodes2)) {
				sql.append(" or treenode in ("+treeNodes2+") )" );
			}else{
				sql.append(" ) ");
			}
		}
		String flag = map.get("flag");
		if("no".equals(flag)) {
			sql.append(" and (index_path is null or index_path=' ') " );
		}else{//yes 已经建立
			sql.append(" and (index_path is not null and index_path <> ' ') " );
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		List<Object[]> list = query.getResultList();
		if(null != list && list.size() > 0) {
			return list.size();
		} else {
			return 0;
		}
	}

	@Override
	public List<Object[]> getFullTextIndexList(Map<String, String> map,Integer pageindex,Integer pagesize) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,treenode,index_path_name,index_path from t_archive_fulltextindex where 1=1 ");
		String treeNodes1 = map.get("treeNodes1");
		if(StringUtils.isNotBlank(treeNodes1)) {
			sql.append(" and ( treenode in ("+treeNodes1+") " );
			String treeNodes2 = map.get("treeNodes2");
			if(StringUtils.isNotBlank(treeNodes2)) {
				sql.append(" or treenode in ("+treeNodes2+") )" );
			}else{
				sql.append(" ) ");
			}
		}
		String flag = map.get("flag");
		if("no".equals(flag)) {
			sql.append(" and (index_path is null or index_path=' ') " );
		}else if("yes".equals(flag)){//yes 已经建立
			sql.append(" and (index_path is not null and index_path <> ' ') " );
		}else{
			
		}
		String ids = map.get("ids");
		if(StringUtils.isNotBlank(ids)) {
			sql.append(" and id in ("+ids+")" );
		}
		Query query = this.getEm().createNativeQuery(sql.toString());
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}
}
