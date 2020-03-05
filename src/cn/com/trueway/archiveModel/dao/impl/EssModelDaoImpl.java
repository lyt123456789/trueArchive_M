package cn.com.trueway.archiveModel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import cn.com.trueway.archiveModel.dao.EssModelDao;
import cn.com.trueway.archiveModel.entity.CheckLog;
import cn.com.trueway.archiveModel.entity.EssStructure;
import cn.com.trueway.archiveModel.entity.EssTag;
import cn.com.trueway.archiveModel.entity.EssTree;
import cn.com.trueway.archiveModel.entity.FileAttachment;
import cn.com.trueway.archiveModel.entity.RsaMes;
import cn.com.trueway.archives.using.pojo.ArchiveMsg;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.JDBCBase;
import cn.com.trueway.base.util.Paging;


public class EssModelDaoImpl extends JDBCBase implements EssModelDao {

	@Override
	public List<EssTree> getModelById(String id,String pid) {
		List<EssTree> list = new ArrayList<EssTree>();
		StringBuffer sql = new StringBuffer("select t.id,t.title,t.id_parent_node,t.id_structure,t.isleaf from ESS_TREE t where t.id_business = 1 ");
		if(StringUtils.isNotBlank(id)) {
			sql.append(" and t.id='"+id+"'");
		}
		if(StringUtils.isNotBlank(pid)) {
			sql.append(" and t.ID_PARENT_NODE='"+pid+"'");
		}
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
				String isleaf = String.valueOf(rs.getObject("isleaf"));
				//String id_seq = rs.getString("id_seq");
				EssTree et = new EssTree();
				et.setId(Integer.valueOf(_id));
				et.setTitle(title);
				//et.setEspath(espath);
				et.setIdParent(Integer.valueOf(id_parent_node));
				//et.setIdBusiness(Integer.valueOf(id_business));
				et.setIdStructure(Integer.valueOf(id_structure));
				//et.setIdModelStructure(Integer.valueOf(id_modelstructure));
				//et.setEsorder(Integer.valueOf(esorder));
				et.setIsLeaf(Integer.valueOf(isleaf));
				//et.setIdSeq(id_seq);
				list.add(et);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return list;
	}

	public List<Map<String,Object>> getChildStructure(Integer structure){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql="select d.id_childstructure as \"IDCHILD\",case s.estype when 'project' then '项目目录' when 'file' then '案卷目录' when 'innerFile' then '卷内目录'  when 'document' then '电子文件' end as \"TITLE\"," + 
				"(select c.id_package from ESS_CHILDSTRUCTURE c where c.id_childstructure='"+structure+"') as \"PID\" " + 
				" from ESS_CHILDSTRUCTURE d " + 
				"left join ESS_STRUCTURE s on d.id_childstructure = s.id where  d.esstatus='启用' " + 
				"connect by d.id_structure = prior d.id_childstructure start with d.id_childstructure = '"+structure+"'";
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {			
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("IDCHILD", rs.getObject("IDCHILD"));
				map.put("TITLE", rs.getObject("TITLE"));
				map.put("PID", rs.getObject("PID"));
				list.add(map);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	    return list;
	}
	
	
	@Override
	public List<EssTag> queryTags(Map<String, String> map) {
		List<EssTag> list =new ArrayList<EssTag>();
		String id = map.get("id");
		String struc = map.get("idStructure");
		String esdotlength = map.get("esdotlength");
		StringBuffer sql = new StringBuffer("select * from ESS_TAG t where 1 = 1 ");
		if(StringUtils.isNotBlank(id)) {
			sql.append(" and t.id='"+id+"'");
		}
		if(StringUtils.isNotBlank(struc)) {
			sql.append(" and t.id_Structure='"+struc+"'");
		}
		if(StringUtils.isNotBlank(esdotlength)) {
			sql.append(" and t.esdotlength="+esdotlength);
		}
		sql.append(" order by t.esOrder");
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {			
				EssTag et = new EssTag();
				et.setId(Integer.valueOf(String.valueOf(rs.getObject("ID"))));
				et.setEsRelation(rs.getObject("ESRELATION")!=null?String.valueOf(rs.getObject("ESRELATION")):null);
				et.setEsIdentifier(rs.getObject("ESIDENTIFIER")!=null?String.valueOf(rs.getObject("ESIDENTIFIER")):null);
				et.setIdParent(rs.getObject("ID_PARENT")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_PARENT"))):null);
				et.setEsType(rs.getObject("ESTYPE")!=null?String.valueOf(rs.getObject("ESTYPE")):null);
				et.setEsDescription(rs.getObject("ESDESCRIPTION")!=null?String.valueOf(rs.getObject("ESDESCRIPTION")):null);
				et.setEsOrder(rs.getObject("ESORDER")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESORDER"))):null);
				et.setIdMetadata(rs.getObject("ID_METADATA")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_METADATA"))):null);
				et.setIdStructure(rs.getObject("ID_STRUCTURE")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_STRUCTURE"))):null);
				et.setEsIsnull(rs.getObject("ESISNULL")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESISNULL"))):null);
				et.setEsLength(rs.getObject("ESLENGTH")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESLENGTH"))):null);
				et.setEsDotlength(rs.getObject("ESDOTLENGTH")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESDOTLENGTH"))):null);
				et.setEsIssystem(rs.getObject("ESISSYSTEM")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESISSYSTEM"))):null);
				list.add(et);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return list;
	}
	
	
	@Override
	public List<EssTree> getModelObjListByMap(Map<String, String> map, Paging paging) throws Exception {
		String pid = map.get("pid");
        StringBuilder listCount = new StringBuilder("SELECT count(*) FROM ESS_TREE where 1=1");
        StringBuilder customSql = new StringBuilder("from EssTree t where 1=1 ");
        if(StringUtils.isNotBlank(pid)) {
        	listCount.append(" and ID_PARENT_NODE='"+pid+"'");
        	customSql.append(" and t.idParent='"+pid+"'");
		}
        customSql.append(" order by t.esorder");
       // String count = getSession().createSQLQuery(listCount.toString()).uniqueResult().toString();
        //paging.setCount(Integer.parseInt(count));
        //List<EssTree> mList = getSession().createQuery(customSql.toString()).setFirstResult(paging.getPageIndex())
         //       .setMaxResults(paging.getPageSize()).list();
        return null;
	}

	@Override
	public void saveEssModel(EssTree ess) {
		//getSession().save(ess);
	}

	@Override
	public void updateEssModel(EssTree ess) {
		//getSession().update(ess);
	}

	@Override
	public void deleteEssModelById(String id) {
		//AssertUtils.notEmpty(id);
		String sql ="delete from ESS_TREE e where e.ID = '"+id+"'";
		//getSession().createNativeQuery(sql).executeUpdate();
	}
	
	public Integer deleteEssModelByIds(String ids) {
		//AssertUtils.notEmpty(ids);
		String sql ="delete from ESS_TREE e where e.ID in ("+ids+")";
       // Integer count = getSession().createSQLQuery(sql).executeUpdate();
        return 0;
    }

	
	
	
	
	
	@SuppressWarnings("deprecation")
	public String getOneMesByTag(String sql) {
		
        //SQLQuery query = getSession().createSQLQuery(sql);
        //Object object = query.uniqueResult();
        //if(object!=null) {
        //	return object.toString();
        //}
        return null;
	}

	@Override
	public void saveCheckLog(CheckLog log) {
		//this.getSession().save(log);
	}
	
	@Override
	public String getTopTreeOfBranch(String codeId) {
		//select * from ESS_TREE d where d.id_parent_node=1 connect by d.id = prior d.id_parent_node start with d.id = '1995' 
		StringBuffer sql = new StringBuffer("select d.title from ESS_TREE d where d.id_parent_node=1 connect by d.id = prior d.id_parent_node start with d.id = '"+codeId+"' ");
		
		//Object object = getSession().createSQLQuery(sql.toString()).uniqueResult();
		//if(object!=null) {
        //	return object.toString();
        //}
        return null;
	}

	@Override
	public List<Map<String, Object>> getNeededFields(String struc) {
		String sql="select t.id as \"ID\",m.esidentifier as \"NAME\" from ESS_TAG t,ESS_METADATA m "
				+ "where t.id_metadata=m.id and t.id_structure="+struc+" and t.id_metadata is not null";
		//SQLQuery query = getSession().createSQLQuery(sql);
	    //query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
	    //List<Map<String,Object>> fildList = query.list();
	    return null;
	}
	
	@Override
	public void saveFileAttachement(FileAttachment atta) {
		//this.getSession().save(atta);
	}

	@Override
	public FileAttachment getFileAttachmentById(String id,String sjid,String struc) {
		StringBuffer sql = new StringBuffer(" from FileAttachment where 1=1 ");
		if(StringUtils.isNotBlank(id)) {
			sql.append(" and id='"+id+"'");
		}
		if(StringUtils.isNotBlank(sjid)) {
			sql.append(" and sjid='"+sjid+"'");
		}
		if(StringUtils.isNotBlank(struc)) {
			sql.append(" and idStructure ='"+struc+"'");
		}
		//List<FileAttachment> ff = getSession().createQuery(sql.toString()).getResultList();
		///if(ff!=null&&ff.size()>0) {
		//	return ff.get(0);
		//}
		return null;
	}

	@Override
	public EssStructure getStructureById(String id) {
		String sql = " from EssStructure where id='"+id+"'";
		//List<EssStructure> s = getSession().createQuery(sql).getResultList();
		//if(s!=null&&s.size()>0) {
		//	return s.get(0);
		//}
		return null;
	}
	
	@Override
	public void saveRsaMes(RsaMes rsa) {
		//this.getSession().save(rsa);
	}

	@Override
	public List<Map<String, Object>> getTaskDetailsById(Map<String, String> map,List<EssTag> etlist) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = map.get("sql");
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {
				Map<String,Object> map1 = new HashMap<String, Object>();
				map1.put("id",rs.getObject("ID"));
				for(int i=0;i<etlist.size();i++){
					map1.put("C"+etlist.get(i).getId(),rs.getObject("C"+etlist.get(i).getId()));
				}
				map1.put("espath",rs.getObject("espath"));
				list.add(map1);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}

	@Override
	public List<String[]> getMetaDataList(Map<String, String> map) {
		List<String[]> list = new ArrayList<String[]>();
		String sql = map.get("sql");
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {
				String[] arr = new String[5];
				arr[0]=rs.getObject("id")+"";
				arr[1]=rs.getObject("estitle")+"";
				arr[2]=rs.getObject("esidentifier")+"";
				arr[3]=rs.getObject("esdescription")+"";
				arr[4]=rs.getObject("id_namespace")+"";
				list.add(arr);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}

	@Override
	public List<String[]> getNameSpaceList(Map<String, String> map) {
		List<String[]> list = new ArrayList<String[]>();
		String sql = map.get("sql");
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {
				String[] arr = new String[8];
				arr[0]=rs.getObject("id")+"";
				arr[1]=rs.getObject("esuri")+"";
				arr[2]=rs.getObject("esidentifier")+"";
				arr[3]=rs.getObject("esdate")+"";
				arr[4]=rs.getObject("esdescription")+"";
				arr[5]=rs.getObject("escreator")+"";
				arr[6]=rs.getObject("esversion")+"";
				arr[7]=rs.getObject("estitle")+"";
				list.add(arr);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}

	@Override
	public int getMetaDataCount(Map<String, String> map) {
		List<String[]> list = new ArrayList<String[]>();
		String sql = map.get("sql");
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {
				String[] arr = new String[8];
				arr[0]=rs.getObject("id")+"";
				list.add(arr);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list!=null?list.size():0;
	}

	@Override
	public List<EssTag> queryTagsForShow(Map<String, String> map) {
		List<EssTag> list =new ArrayList<EssTag>();
		String struc = map.get("idStructure");
		StringBuffer sql = new StringBuffer("select t.* from  ESS_TAG t, ESS_RULE_DISPLAYFIELDOFGRID d where t.id_structure=d.id_structure and t.id=d.id_tag");
		if(StringUtils.isNotBlank(struc)) {
			sql.append(" and d.id_Structure='"+struc+"'");
		}
		sql.append(" order by d.esorder");
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {			
				EssTag et = new EssTag();
				et.setId(Integer.valueOf(String.valueOf(rs.getObject("ID"))));
				et.setEsRelation(rs.getObject("ESRELATION")!=null?String.valueOf(rs.getObject("ESRELATION")):null);
				et.setEsIdentifier(rs.getObject("ESIDENTIFIER")!=null?String.valueOf(rs.getObject("ESIDENTIFIER")):null);
				et.setIdParent(rs.getObject("ID_PARENT")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_PARENT"))):null);
				et.setEsType(rs.getObject("ESTYPE")!=null?String.valueOf(rs.getObject("ESTYPE")):null);
				et.setEsDescription(rs.getObject("ESDESCRIPTION")!=null?String.valueOf(rs.getObject("ESDESCRIPTION")):null);
				et.setEsOrder(rs.getObject("ESORDER")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESORDER"))):null);
				et.setIdMetadata(rs.getObject("ID_METADATA")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_METADATA"))):null);
				et.setIdStructure(rs.getObject("ID_STRUCTURE")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_STRUCTURE"))):null);
				et.setEsIsnull(rs.getObject("ESISNULL")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESISNULL"))):null);
				et.setEsLength(rs.getObject("ESLENGTH")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESLENGTH"))):null);
				et.setEsDotlength(rs.getObject("ESDOTLENGTH")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESDOTLENGTH"))):null);
				et.setEsIssystem(rs.getObject("ESISSYSTEM")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESISSYSTEM"))):null);
				list.add(et);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return list;
	}

	@Override
	public List<EssTag> queryTagsForShowOfzhyw(Map<String, String> map) {
		List<EssTag> list =new ArrayList<EssTag>();
		String struc = map.get("idStructure");
		StringBuffer sql = new StringBuffer("select t.* from  ESS_TAG t, ESS_RULE_USINGFORMFIELD d where t.id_structure=d.id_structure and t.id=d.id_tag");
		if(StringUtils.isNotBlank(struc)) {
			sql.append(" and d.id_Structure='"+struc+"'");
		}
		sql.append(" order by d.esorder");
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {			
				EssTag et = new EssTag();
				et.setId(Integer.valueOf(String.valueOf(rs.getObject("ID"))));
				et.setEsRelation(rs.getObject("ESRELATION")!=null?String.valueOf(rs.getObject("ESRELATION")):null);
				et.setEsIdentifier(rs.getObject("ESIDENTIFIER")!=null?String.valueOf(rs.getObject("ESIDENTIFIER")):null);
				et.setIdParent(rs.getObject("ID_PARENT")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_PARENT"))):null);
				et.setEsType(rs.getObject("ESTYPE")!=null?String.valueOf(rs.getObject("ESTYPE")):null);
				et.setEsDescription(rs.getObject("ESDESCRIPTION")!=null?String.valueOf(rs.getObject("ESDESCRIPTION")):null);
				et.setEsOrder(rs.getObject("ESORDER")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESORDER"))):null);
				et.setIdMetadata(rs.getObject("ID_METADATA")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_METADATA"))):null);
				et.setIdStructure(rs.getObject("ID_STRUCTURE")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_STRUCTURE"))):null);
				et.setEsIsnull(rs.getObject("ESISNULL")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESISNULL"))):null);
				et.setEsLength(rs.getObject("ESLENGTH")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESLENGTH"))):null);
				et.setEsDotlength(rs.getObject("ESDOTLENGTH")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESDOTLENGTH"))):null);
				et.setEsIssystem(rs.getObject("ESISSYSTEM")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESISSYSTEM"))):null);
				list.add(et);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return list;
	}
	
	@Override
	public List<EssTag> queryTagsForSearch(Map<String, String> map) {
		List<EssTag> list =new ArrayList<EssTag>();
		String struc = map.get("idStructure");
		StringBuffer sql = new StringBuffer("select t.* from  ESS_TAG t, ESS_RULE_USINGGRIDFIELD d where t.id_structure=d.id_structure and t.id=d.id_tag");
		if(StringUtils.isNotBlank(struc)) {
			sql.append(" and d.id_Structure='"+struc+"'");
		}
		sql.append(" order by d.esorder");
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {			
				EssTag et = new EssTag();
				et.setId(Integer.valueOf(String.valueOf(rs.getObject("ID"))));
				et.setEsRelation(rs.getObject("ESRELATION")!=null?String.valueOf(rs.getObject("ESRELATION")):null);
				et.setEsIdentifier(rs.getObject("ESIDENTIFIER")!=null?String.valueOf(rs.getObject("ESIDENTIFIER")):null);
				et.setIdParent(rs.getObject("ID_PARENT")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_PARENT"))):null);
				et.setEsType(rs.getObject("ESTYPE")!=null?String.valueOf(rs.getObject("ESTYPE")):null);
				et.setEsDescription(rs.getObject("ESDESCRIPTION")!=null?String.valueOf(rs.getObject("ESDESCRIPTION")):null);
				et.setEsOrder(rs.getObject("ESORDER")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESORDER"))):null);
				et.setIdMetadata(rs.getObject("ID_METADATA")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_METADATA"))):null);
				et.setIdStructure(rs.getObject("ID_STRUCTURE")!=null?Integer.valueOf(String.valueOf(rs.getObject("ID_STRUCTURE"))):null);
				et.setEsIsnull(rs.getObject("ESISNULL")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESISNULL"))):null);
				et.setEsLength(rs.getObject("ESLENGTH")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESLENGTH"))):null);
				et.setEsDotlength(rs.getObject("ESDOTLENGTH")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESDOTLENGTH"))):null);
				et.setEsIssystem(rs.getObject("ESISSYSTEM")!=null?Integer.valueOf(String.valueOf(rs.getObject("ESISSYSTEM"))):null);
				list.add(et);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return list;
	}

	@Override
	public String queryTagsForOrder(Map<String, String> map) {
		String orderSql="";
		String struc = map.get("idStructure");
		StringBuffer sql = new StringBuffer("select t.rule from  ESS_RULE_SORTFIELD t where 1=1 ");
		if(StringUtils.isNotBlank(struc)) {
			sql.append(" and t.id_Structure='"+struc+"'");
		}
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {		
				orderSql=rs.getString("rule");
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return orderSql;
	}

	@Override
	public int getTaskDetailsCount(Map<String, String> map, List<EssTag> tags1,
			List<EssTag> tags2) {
		String tableName="ESP_"+map.get("struc");
		String keyWord = map.get("keyWord");
		String orderSql = map.get("orderSql");
		String zhconditionsql = map.get("zhconditionsql");
		StringBuffer conditionSql = null;
		if(keyWord!=null&&!"".equals(keyWord)){
			conditionSql= new StringBuffer();
			if(tags2!=null&&tags2.size()>0) {
				conditionSql.append(" 1=2 ");
				for(int i=0;i<tags2.size();i++) {
					Integer zd = tags2.get(i).getId();
					conditionSql.append(" or C"+zd+" like '%"+keyWord+"%'");
				}
			}
		}
		
        StringBuilder listCount = new StringBuilder("SELECT count(*) FROM "+tableName+" where 1=1 "); 
        if(conditionSql!=null&&!"".equals(conditionSql)){
			listCount.append("and ("+conditionSql+")");
		}
        if(zhconditionsql!=null&&!"".equals(zhconditionsql)){
			listCount.append("and ("+zhconditionsql+")");
		}
        PreparedStatement pstmt;
        int count =0;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(listCount.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {			
				count=Integer.valueOf(rs.getString(1));
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return count;
	}

	@Override
	public List<Map<String, Object>> getTaskDetails(Map<String, String> map,
			List<EssTag> tags1, List<EssTag> tags2, Integer pageSize, Integer pageIndex) {
		PreparedStatement pstmt;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String tableName="ESP_"+map.get("struc");
		String keyWord = map.get("keyWord");
		String keyWordOld = map.get("keyWordOld");
		String orderSql = map.get("orderSql");
		String zhconditionsql = map.get("zhconditionsql");
		StringBuffer sql = new StringBuffer("select ID as ID");
		//展示列
		if(tags1!=null&&tags1.size()>0) {
			for(int i=0;i<tags1.size();i++) {
				Integer zd = tags1.get(i).getId();
				sql.append(",C"+zd+" as C"+zd);	
			}
		}
		sql.append(" from "+tableName+" where 1=1 ");
		//条件1
		StringBuffer conditionSql = null;
		if(keyWord!=null&&!"".equals(keyWord)){
			conditionSql = new StringBuffer();
			if(tags2!=null&&tags2.size()>0) {
				conditionSql.append(" 1=2 ");
				for(int i=0;i<tags2.size();i++) {
					Integer zd = tags2.get(i).getId();
					conditionSql.append(" or C"+zd+" like '%"+keyWord+"%'");
				}
			}
			sql.append("and ("+conditionSql+")");
		}
		//条件2
		StringBuffer conditionSql2 = null;
		if(keyWordOld!=null&&!"".equals(keyWordOld)){
			conditionSql2 = new StringBuffer();
			if(tags2!=null&&tags2.size()>0) {
				conditionSql2.append(" 1=2 ");
				for(int i=0;i<tags2.size();i++) {
					Integer zd = tags2.get(i).getId();
					conditionSql2.append(" or C"+zd+" like '%"+keyWordOld+"%'");
				}
			}
			sql.append("and ("+conditionSql2+")");
		}
		//条件3(动态查询)
		if(zhconditionsql!=null&&!"".equals(zhconditionsql)){
			sql.append("and ("+zhconditionsql+")");
		}
		
		//排序
		if(orderSql!=null&&!"".equals(orderSql)){
			sql.append(" order by "+orderSql);
		}
		
		//分页查询语句：利用ROWNUM
		String newsql ="";
		if(pageSize==null&&pageIndex==null){
			newsql = sql.toString();
		}else{
			int start =  pageIndex;
			int end = pageSize + pageIndex;
			newsql = "SELECT * from (SELECT rownum as rn, t.* FROM ("+sql+") t) WHERE rn>" + start + " AND rn<=" + end;
		}	
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(newsql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {			
				Map<String,Object> map2 = new HashMap<String, Object>();
				map2.put("id",rs.getObject("ID"));
				String value="";
				for(int i=0;i<tags1.size();i++){
					if(rs.getObject("C"+tags1.get(i).getId())!=null){
						value=rs.getObject("C"+tags1.get(i).getId()).toString();
					}else{
						value="";
					}
					
					if(keyWordOld!=null&&!"".equals(keyWordOld)){
						value=value.replace(keyWordOld, "<lable>"+keyWordOld+"</lable>");
					}
					if(keyWord!=null&&!"".equals(keyWord)){
						value=value.replace(keyWord, "<lable>"+keyWord+"</lable>");
					}
					map2.put("C"+tags1.get(i).getId(),value);
				}
				list.add(map2);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return list;
	}

	
	@Override
	public int getTaskDetails4ZHYWCount(Map<String, String> map,
			List<EssTag> tags1, List<EssTag> tags2) {
		PreparedStatement pstmt;
		int count=0;
		String tableName="ESP_"+map.get("struc");
		String keyWord = map.get("keyWord");
		String keyWordOld = map.get("keyWordOld");
		String orderSql = map.get("orderSql");
		String zhconditionsql = map.get("zhconditionsql");
		StringBuffer sql = new StringBuffer("select count(*)");
		//展示列
		sql.append(" from "+tableName+" where 1=1 ");
		//条件1
		StringBuffer conditionSql = null;
		if(keyWord!=null&&!"".equals(keyWord)){
			conditionSql = new StringBuffer();
			if(tags2!=null&&tags2.size()>0) {
				conditionSql.append(" 1=2 ");
				for(int i=0;i<tags2.size();i++) {
					Integer zd = tags2.get(i).getId();
					conditionSql.append(" or C"+zd+" like '%"+keyWord+"%'");
				}
			}
			sql.append("and ("+conditionSql+")");
		}
		//条件2
		StringBuffer conditionSql2 = null;
		if(keyWordOld!=null&&!"".equals(keyWordOld)){
			conditionSql2 = new StringBuffer();
			if(tags2!=null&&tags2.size()>0) {
				conditionSql2.append(" 1=2 ");
				for(int i=0;i<tags2.size();i++) {
					Integer zd = tags2.get(i).getId();
					conditionSql2.append(" or C"+zd+" like '%"+keyWordOld+"%'");
				}
			}
			sql.append("and ("+conditionSql2+")");
		}
		//条件3(动态查询)
		if(zhconditionsql!=null&&!"".equals(zhconditionsql)){
			sql.append("and ("+zhconditionsql+")");
		}
		
		//排序
		if(orderSql!=null&&!"".equals(orderSql)){
			sql.append(" order by "+orderSql);
		}

		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {			
				count=Integer.valueOf(rs.getString(1));
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return count;
	}
	
	@Override
	public List<String[]> getTaskDetails4ZHYW(Map<String, String> map,
			List<EssTag> tags1, List<EssTag> tags2, Integer pageSize, Integer pageIndex) {
		PreparedStatement pstmt;
		List<String[]> list = new ArrayList<String[]>();
		String tableName="ESP_"+map.get("struc");
		String keyWord = map.get("keyWord");
		String keyWordOld = map.get("keyWordOld");
		String orderSql = map.get("orderSql");
		String zhconditionsql = map.get("zhconditionsql");
		StringBuffer sql = new StringBuffer("select ID as ID");
		//展示列
		if(tags1!=null&&tags1.size()>0) {
			for(int i=0;i<tags1.size();i++) {
				Integer zd = tags1.get(i).getId();
				sql.append(",C"+zd+" as C"+zd);	
			}
		}
		sql.append(" from "+tableName+" where 1=1 ");
		//条件1
		StringBuffer conditionSql = null;
		if(keyWord!=null&&!"".equals(keyWord)){
			conditionSql = new StringBuffer();
			if(tags2!=null&&tags2.size()>0) {
				conditionSql.append(" 1=2 ");
				for(int i=0;i<tags2.size();i++) {
					Integer zd = tags2.get(i).getId();
					conditionSql.append(" or C"+zd+" like '%"+keyWord+"%'");
				}
			}
			sql.append("and ("+conditionSql+")");
		}
		//条件2
		StringBuffer conditionSql2 = null;
		if(keyWordOld!=null&&!"".equals(keyWordOld)){
			conditionSql2 = new StringBuffer();
			if(tags2!=null&&tags2.size()>0) {
				conditionSql2.append(" 1=2 ");
				for(int i=0;i<tags2.size();i++) {
					Integer zd = tags2.get(i).getId();
					conditionSql2.append(" or C"+zd+" like '%"+keyWordOld+"%'");
				}
			}
			sql.append("and ("+conditionSql2+")");
		}
		//条件3(动态查询)
		if(zhconditionsql!=null&&!"".equals(zhconditionsql)){
			sql.append("and ("+zhconditionsql+")");
		}
		
		//排序
		if(orderSql!=null&&!"".equals(orderSql)){
			sql.append(" order by "+orderSql);
		}
		
		//分页查询语句：利用ROWNUM
		String newsql ="";
		if(pageSize==null&&pageIndex==null){
			newsql = sql.toString();
		}else{
			int start =  pageIndex;
			int end = pageSize + pageIndex;
			newsql = "SELECT * from (SELECT rownum as rn, t.* FROM ("+sql+") t) WHERE rn>" + start + " AND rn<=" + end;
		}	
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(newsql.toString());
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {			
				String[] arr = new String[5];
				arr[0]=rs.getObject("ID").toString();
				arr[1]=map.get("struc");//structId结构树的id
				arr[2]=map.get("fatherId");//数据树的底层id
				String content="";
				String title="";
				for(int i=0;i<tags1.size();i++){
					String value="";
					if(rs.getObject("C"+tags1.get(i).getId())!=null){
						value=rs.getObject("C"+tags1.get(i).getId()).toString();
					}else{
						value="";
					}
					if(keyWordOld!=null&&!"".equals(keyWordOld)){
						value=value.replace(keyWordOld, "<lable>"+keyWordOld+"</lable>");
					}
					if(keyWord!=null&&!"".equals(keyWord)){
						value=value.replace(keyWord, "<lable>"+keyWord+"</lable>");
					}
					String column = tags1.get(i).getEsIdentifier()+"";
					if("22".equals(tags1.get(i).getIdMetadata()+"")){//关联的元数据是title的话就取值		
						title=value;
					}
					content+=column+": "+value+"   ";
				}
				arr[3]=title;
				arr[4]=content;
				list.add(arr);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        return list;
	}
	
	@Override
	public String[] getMetaDataById(String id) {
		PreparedStatement pstmt;
		String[] arr =null;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement("select * from ESS_METADATA where id='"+id+"'");
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {
				arr = new String[5];
				arr[0]=rs.getObject("id")+"";
				arr[1]=rs.getObject("estitle")+"";
				arr[2]=rs.getObject("esidentifier")+"";
				arr[3]=rs.getObject("esdescription")+"";
				arr[4]=rs.getObject("id_namespace")+"";
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return arr;
	}

	@Override
	public List<String[]> queryInfo(String sql, int returnColumn) {
		List<String[]> list = new ArrayList<String[]>();
		PreparedStatement pstmt;
		try {
			Connection con = getCon();
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();    
			while (rs.next()) {
				String[] arr = new String[returnColumn];
				for(int i=0;i<returnColumn;i++){
					arr[i]=rs.getObject(i+1)!=null?rs.getObject(i+1).toString():"";
				}
				list.add(arr);
			}
			closeCon(rs,pstmt,con);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}
	

}
