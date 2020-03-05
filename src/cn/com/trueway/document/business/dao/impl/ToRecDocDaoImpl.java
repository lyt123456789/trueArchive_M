package cn.com.trueway.document.business.dao.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import oracle.sql.BLOB;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.document.business.dao.ToRecDocDao;
import cn.com.trueway.document.business.model.RecShip;
import cn.com.trueway.document.business.model.ToRecDoc;
import cn.com.trueway.document.business.model.ToRecDocAttachmentSext;
import cn.com.trueway.document.business.model.ToRecDocAttachments;
import cn.com.trueway.document.business.model.ToSendDoc;
import cn.com.trueway.document.business.model.ToSendDocAttachments;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
public class ToRecDocDaoImpl extends BaseDao implements ToRecDocDao{

	@Override
	public int findToRecDocCount(String conditionSql)  throws Exception {
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		int resultCount = 0 ;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql=" select count(*) as rowcount " ;
			sql += " from to_recdoc t where 1=1 and (t.state != '1' or t.state is null ) ";
			if(conditionSql!=null && !conditionSql.equals("")){
				sql+= conditionSql;
			}
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			rs.next();
			resultCount = rs.getInt("rowcount");
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(conn,ps,rs);
		}
		return (Integer)resultCount;
//		String sql = "select count(*) from to_recdoc t where 1=1 and (t.state != '1' or t.state is null )";
//		if(conditionSql!=null && !conditionSql.equals("")){
//			sql+= conditionSql;
//		}
//		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public List<ToRecDoc> findToRecDocList(String conditionSql,
			Integer pageIndex, Integer pageSize) throws Exception {
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		List<ToRecDoc> list=new ArrayList<ToRecDoc>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql="SELECT t.id as id,t.docguid  as docguid,t.state as state,t.creatTime as creatTime , " +
					" t.departmentId as departmentId,t.recUserId  as recUserId,t.bt as bt,t.fwdw as fwdw, " +
					" t.type as type,t.sendTime as sendTime,t.docXml as docXml,t.wh as wh " +
					" from to_recdoc t where 1=1 and (t.state != '1' or t.state is null ) ";
			if(conditionSql!=null && !conditionSql.equals("")){
				sql+= conditionSql;
			}
			sql +=" order by t.state asc, t.creattime desc";
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setMaxRows(pageIndex + pageSize);
			rs=ps.executeQuery();
			if (pageIndex != null && pageSize != null) {
				rs.first();
				rs.relative(pageIndex-1);
			}
			while(rs.next()){
				ToRecDoc toRecDoc=new ToRecDoc();
				
				toRecDoc.setId(rs.getString("id"));
				toRecDoc.setDocguid(rs.getString("docguid"));
				toRecDoc.setState(rs.getString("state"));
				toRecDoc.setCreatTime(rs.getString("creatTime"));
				toRecDoc.setDepartmentId(rs.getString("departmentId"));
				toRecDoc.setRecUserId(rs.getString("recUserId"));
				toRecDoc.setBt(rs.getString("bt"));
				toRecDoc.setFwdw(rs.getString("fwdw"));
				toRecDoc.setType(rs.getString("type"));
				toRecDoc.setSendTime(rs.getString("sendTime"));
				toRecDoc.setDocXml((oracle.sql.CLOB)rs.getClob("docXml"));
				toRecDoc.setWh(rs.getString("wh"));
				
			    list.add(toRecDoc);
			}
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(conn,ps,rs);
		}
		return list;
//		String sql = "select t.* from to_recdoc t where 1=1 and (t.state != '1' or t.state is null )";
//		if(conditionSql!=null && !conditionSql.equals("")){
//			sql+= conditionSql;
//		}
//		Query query = getEm().createNativeQuery(sql, ToRecDoc.class);
//		if (pageIndex!=null&&pageSize!=null) {
//			query.setFirstResult(pageIndex);
//			query.setMaxResults(pageSize);
//		}
//		return query.getResultList();
	}

	@Override
	public void updateToRecDocStatus(String id, String userId) throws Exception{
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql = "update to_recdoc t set t.state='1', t.recUserId='"
					+userId+"' where t.id='"+id+"'";
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.executeQuery();
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(conn,ps,rs);
		}
	}

	@Override
	public int findToRecedDocCount(String userId, String conditionSql) throws Exception {
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		int resultCount = 0 ;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql=" select count(*) as rowcount " ;
			sql += " from to_recdoc t where t.state='1' and t.recUserId ='"+userId+"'";
			if(conditionSql!=null && !conditionSql.equals("")){
				sql+= conditionSql;
			}
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			rs.next();
			resultCount = rs.getInt("rowcount");
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(conn,ps,rs);
		}
		return (Integer)resultCount;
//		String sql = "select count(*) from to_recdoc t where t.state='1' and t.recUserId ='"+userId+"'";
//		if(conditionSql!=null && !conditionSql.equals("")){
//			sql+= conditionSql;
//		}
//		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@Override
	public List<ToRecDoc> findToRecedDocList(String userId,
			String conditionSql, Integer pageIndex, Integer pageSize) throws Exception{
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		List<ToRecDoc> list=new ArrayList<ToRecDoc>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql="SELECT t.id as id,t.docguid  as docguid,t.state as state,t.creatTime as creatTime , " +
					" t.departmentId as departmentId,t.recUserId  as recUserId,t.bt as bt,t.fwdw as fwdw, " +
					" t.type as type,t.sendTime as sendTime,t.docXml as docXml,t.wh as wh " +
					" from to_recdoc t where t.state='1' and t.recUserId ='"+userId+"'";
			if(conditionSql!=null && !conditionSql.equals("")){
				sql+= conditionSql;
			}
			sql +=" order by t.state asc, t.creattime desc";
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ps.setMaxRows(pageIndex + pageSize);
			rs=ps.executeQuery();
			if (pageIndex != null && pageSize != null) {
				rs.first();
				rs.relative(pageIndex-1);
			}
			while(rs.next()){
				ToRecDoc toRecDoc=new ToRecDoc();
				
				toRecDoc.setId(rs.getString("id"));
				toRecDoc.setDocguid(rs.getString("docguid"));
				toRecDoc.setState(rs.getString("state"));
				toRecDoc.setCreatTime(rs.getString("creatTime"));
				toRecDoc.setDepartmentId(rs.getString("departmentId"));
				toRecDoc.setRecUserId(rs.getString("recUserId"));
				toRecDoc.setBt(rs.getString("bt"));
				toRecDoc.setFwdw(rs.getString("fwdw"));
				toRecDoc.setType(rs.getString("type"));
				toRecDoc.setSendTime(rs.getString("sendTime"));
				toRecDoc.setDocXml((oracle.sql.CLOB)rs.getClob("docXml"));
				toRecDoc.setWh(rs.getString("wh"));
				
			    list.add(toRecDoc);
			}
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(conn,ps,rs);
		}
		return list;
//		String sql = "select t.* from to_recdoc t where t.state='1' and t.recUserId ='"+userId+"'";
//		if(conditionSql!=null && !conditionSql.equals("")){
//			sql += conditionSql;
//		}
//		sql +=" order by t.state asc, t.creattime desc";
//		Query query = getEm().createNativeQuery(sql, ToRecDoc.class);
//		if (pageIndex!=null&&pageSize!=null) {
//			query.setFirstResult(pageIndex);
//			query.setMaxResults(pageSize);
//		}
//		return query.getResultList();
	}

	@Override
	public ToRecDoc findToRecDocById(String id) throws Exception{
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		List<ToRecDoc> list=new ArrayList<ToRecDoc>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql="SELECT t.id as id,t.docguid  as docguid,t.state as state,t.creatTime as creatTime , " +
					" t.departmentId as departmentId,t.recUserId  as recUserId,t.bt as bt,t.fwdw as fwdw, " +
					" t.type as type,t.sendTime as sendTime,t.docXml as docXml,t.wh as wh " +
					" from to_recdoc t where t.id ='"+id+"'";
			sql +=" order by t.state asc, t.creattime desc";
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			while(rs.next()){
				ToRecDoc toRecDoc=new ToRecDoc();
				
				toRecDoc.setId(rs.getString("id"));
				toRecDoc.setDocguid(rs.getString("docguid"));
				toRecDoc.setState(rs.getString("state"));
				toRecDoc.setCreatTime(rs.getString("creatTime"));
				toRecDoc.setDepartmentId(rs.getString("departmentId"));
				toRecDoc.setRecUserId(rs.getString("recUserId"));
				toRecDoc.setBt(rs.getString("bt"));
				toRecDoc.setFwdw(rs.getString("fwdw"));
				toRecDoc.setType(rs.getString("type"));
				toRecDoc.setSendTime(rs.getString("sendTime"));
				toRecDoc.setDocXml((oracle.sql.CLOB)rs.getClob("docXml"));
				toRecDoc.setWh(rs.getString("wh"));
				
			    list.add(toRecDoc);
			}
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(null,ps,rs);
		}
		return list.get(0);
	}

	@Override
	public List<ToRecDocAttachments> findToRecDocAttachmentsByDocguid (
			String docguid) throws Exception{
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		List<ToRecDocAttachments> list=new ArrayList<ToRecDocAttachments>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql="SELECT t.id as id,t.docguid  as docguid,t.fileIndex as fileIndex,t.fileName as fileName , " +
					" t.fileSize as fileSize,t.fileTime  as fileTime,t.fileType as fileType,t.localation as localation, " +
					" t.content as content " +
					" from to_recdoc_attachments t where t.docguid ='"+docguid+"'  order by t.fileTime";
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			while(rs.next()){
				ToRecDocAttachments toRecDocAtt=new ToRecDocAttachments();
				
				toRecDocAtt.setId(rs.getString("id"));
				toRecDocAtt.setDocguid(rs.getString("docguid"));
				toRecDocAtt.setFileIndex(rs.getInt("fileIndex"));
				toRecDocAtt.setFileName(rs.getString("fileName"));
				toRecDocAtt.setFileSize(rs.getLong("fileSize"));
				toRecDocAtt.setFileTime(rs.getTimestamp("fileTime"));
				toRecDocAtt.setFileType(rs.getString("fileType"));
				toRecDocAtt.setLocalation(rs.getString("localation"));
				toRecDocAtt.setContent((oracle.sql.BLOB)rs.getBlob("content"));
				
			    list.add(toRecDocAtt);
			}
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(conn,ps,rs);
		}
		return list;
//		String sql = " from ToRecDocAttachments t where t.docguid = '"+docguid+"'";
//		return getEm().createQuery(sql).getResultList();
	}

	@Override
	public List<ToRecDocAttachmentSext> findToRecDocAttachmentSextByDocguid(
			String docguid) throws Exception {
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		List<ToRecDocAttachmentSext> list=new ArrayList<ToRecDocAttachmentSext>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql="SELECT t.id as id,t.docguid  as docguid,t.fileIndex as fileIndex,t.fileName as fileName , " +
					" t.fileSize as fileSize,t.fileTime  as fileTime,t.fileType as fileType,t.localation as localation, " +
					" t.content as content " +
					" from to_recdoc_attachmentsext t where t.docguid ='"+docguid+"' order by t.fileTime ";
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			while(rs.next()){
				ToRecDocAttachmentSext toRecDocAttSext=new ToRecDocAttachmentSext();
				
				toRecDocAttSext.setId(rs.getString("id"));
				toRecDocAttSext.setDocguid(rs.getString("docguid"));
				toRecDocAttSext.setFileIndex(rs.getInt("fileIndex"));
				toRecDocAttSext.setFileName(rs.getString("fileName"));
				toRecDocAttSext.setFileSize(rs.getLong("fileSize"));
				toRecDocAttSext.setFileTime(rs.getTimestamp("fileTime"));
				toRecDocAttSext.setFileType(rs.getString("fileType"));
				toRecDocAttSext.setLocalation(rs.getString("localation"));
				toRecDocAttSext.setContent((oracle.sql.BLOB)rs.getBlob("content"));
				
			    list.add(toRecDocAttSext);
			}
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(conn,ps,rs);
		}
		return list;
//		String sql = " from ToRecDocAttachmentSext t where t.docguid = '"+docguid+"'";
//		return getEm().createQuery(sql).getResultList();
	}

	@Override
	public ToRecDoc findToRecDocByDocguid(String docguid) throws Exception {
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		List<ToRecDoc> list=new ArrayList<ToRecDoc>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql="SELECT t.id as id,t.docguid  as docguid,t.state as state,t.creatTime as creatTime , " +
					" t.departmentId as departmentId,t.recUserId  as recUserId,t.bt as bt,t.fwdw as fwdw, " +
					" t.type as type,t.sendTime as sendTime,t.docXml as docXml,t.wh as wh " +
					" from to_recdoc t where t.docguid ='"+docguid+"'";
			sql +=" order by t.state asc, t.creattime desc";
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
			while(rs.next()){
				ToRecDoc toRecDoc=new ToRecDoc();
				
				toRecDoc.setId(rs.getString("id"));
				toRecDoc.setDocguid(rs.getString("docguid"));
				toRecDoc.setState(rs.getString("state"));
				toRecDoc.setCreatTime(rs.getString("creatTime"));
				toRecDoc.setDepartmentId(rs.getString("departmentId"));
				toRecDoc.setRecUserId(rs.getString("recUserId"));
				toRecDoc.setBt(rs.getString("bt"));
				toRecDoc.setFwdw(rs.getString("fwdw"));
				toRecDoc.setType(rs.getString("type"));
				toRecDoc.setSendTime(rs.getString("sendTime"));
				toRecDoc.setDocXml((oracle.sql.CLOB)rs.getClob("docXml"));
				toRecDoc.setWh(rs.getString("wh"));
				
			    list.add(toRecDoc);
			}
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(null,ps,rs);
		}
		return (list!=null && list.size()>0)?list.get(0):null;
//		String hql = " from ToRecDoc t where t.docguid ='"+docguid+"'";
//		List<ToRecDoc> list = getEm().createQuery(hql).getResultList();
//		return (list!=null && list.size()>0)?list.get(0):null;
	}

	@Override
	public void saveToSendDoc(ToSendDoc toSendDoc) throws Exception {
		getEm().persist(toSendDoc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ToSendDoc> findToSendDocList(String userId,
			String conditionSql, Integer pageIndex, Integer pageSize) {
		String sql = "select distinct t.docguid, t.creattime, t.fwdw, t.bt, t.instanceId, t.wh from to_senddoc t where t.sendUserId='"+userId+"'";
		if(conditionSql!=null && !conditionSql.equals("")){
			sql += conditionSql;
		}
		sql += " order by t.creattime desc";
		Query query = getEm().createNativeQuery(sql);
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.getResultList();
		List<ToSendDoc> sendDocList = new ArrayList<ToSendDoc>();
		Object[] data = null;
		ToSendDoc toSendDoc = null;
		for(int i=0; i<list.size(); i++){
			data = list.get(i);
			toSendDoc = new ToSendDoc();
			toSendDoc.setDocguid(data[0]!=null?data[0].toString():"");
			toSendDoc.setCreatTime(data[1]!=null?data[1].toString():"");
			toSendDoc.setFwdw(data[2]!=null?data[2].toString():"");
			toSendDoc.setBt(data[3]!=null?data[3].toString():"");
			toSendDoc.setInstanceId(data[4]!=null?data[4].toString():"");
			toSendDoc.setWh(data[5]!=null?data[5].toString():"");
			sendDocList.add(toSendDoc);
		}
		return sendDocList;
	}

	@Override
	public int findToSendDocCount(String userId, String conditionSql) {
		String sql = "select count(distinct t.instanceId) from to_senddoc t where t.sendUserId='"+userId+"'";
		if(conditionSql!=null && !conditionSql.equals("")){
			sql += conditionSql;
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findRecDepList(String instanceId) {
		String sql = "select t.name, t2.state from docexchange_departext t, TO_SENDDOC t2 where t2.departmentid = t.guid" +
				" and t2.instanceId = '"+instanceId+"'";
		return getEm().createNativeQuery(sql).getResultList();
	}

	@Override
	public void updateToRecDocAttachments(ToRecDocAttachments entity) throws Exception {
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql = " update to_recdoc_attachments t set t.docguid = '" + entity.getDocguid() + "' , t.fileIndex = '" + entity.getFileIndex() 
					+ "' , t.fileName = '" + entity.getFileName() + "' , t.fileSize = '" + entity.getFileSize() + "' , t.fileTime = to_date('" + sf.format(entity.getFileTime()==null?new Date():entity.getFileTime())
					+ "','yyyy-MM-dd hh24:mi:ss') , t.fileType = '" + entity.getFileType() + "' , t.localation = '" + entity.getLocalation() + "' where t.id = '" + entity.getId() + "'";
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(conn,ps,rs);
		}
		
	}

	@Override
	public void updateToRecDocAttachmentSext(ToRecDocAttachmentSext entity) throws Exception {
		String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ResultSet rs=null;
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn =DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
			String sql = " update to_recdoc_attachmentsext t set t.docguid = '" + entity.getDocguid() + "' , t.fileIndex = '" + entity.getFileIndex() 
					+ "' , t.fileName = '" + entity.getFileName() + "' , t.fileSize = '" + entity.getFileSize() + "' , t.fileTime = to_date('" + sf.format(entity.getFileTime()==null?new Date():entity.getFileTime())
					+ "','yyyy-MM-dd hh24:mi:ss') , t.fileType = '" + entity.getFileType() + "' , t.localation = '" + entity.getLocalation() + "' where t.id = '" + entity.getId() + "'";
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=ps.executeQuery();
		}catch(SQLException se){
		    se.printStackTrace() ;   
		}finally{
			closeJDBC(conn,ps,rs);
		}
//		getEm().merge(entity);
	}

	@Override
	public ToSendDocAttachments saveToSendDocAttachments(
			ToSendDocAttachments toSendDocAttachments) {
		getEm().persist(toSendDocAttachments);
		return toSendDocAttachments;
	}

	@Override
	public void updateToSendDocAttachments(
			ToSendDocAttachments toSendDocAttachments) {
		String filepath = toSendDocAttachments.getLocalation();
		String id = toSendDocAttachments.getId();
		filepath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+filepath;
		//读取文件流
		String updatesql = " update TO_SENDDOC_ATTACHMENTS t set t.content = ? where t.id=?";
		getEm().createNativeQuery(updatesql).setParameter(1, readFileToByteArray(filepath)).setParameter(2, id).executeUpdate();
		
	}

	public byte[] readFileToByteArray(String fileName){ 
		File file = new File(fileName);
		InputStream is = null;
		byte[] bytes = null;
		try {
			is = new FileInputStream(file);
			long length = file.length();
	        if (length > Integer.MAX_VALUE) {
	            // File is too large
	        	System.out.println("File is too large");
	        }
	        bytes = new byte[(int)length];
	        int offset = 0;
	        int numRead = 0;
			while (offset < bytes.length
				       && (numRead = is.read(bytes, offset, bytes.length-offset)) >= 0) {
				    offset += numRead;
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return bytes;
	} 
	
	@Override
	public BLOB getBlob(String tablename, String fieldname,String key,String value) throws Exception{
		PreparedStatement ps=null;
	    ResultSet rs=null;
	    oracle.sql.BLOB blob = null;
	    Connection conn = null;
	    String url=SystemParamConfigUtil.getParamValueByParam("recDoc_url");
		String userName=SystemParamConfigUtil.getParamValueByParam("recDoc_userName");
		String passWord=SystemParamConfigUtil.getParamValueByParam("recDoc_passWord");
		Class.forName("oracle.jdbc.driver.OracleDriver");
	    try {
	    	conn = DriverManager.getConnection("jdbc:oracle:thin:@"+url,userName , passWord);
		    String sql = "select "+fieldname.trim() + " from " + tablename + " where "+key+" ='" + value + "'";
		    ps = conn.prepareStatement(sql);
		    rs = ps.executeQuery();
		  
		    if (rs.next()) {
		    	blob = (oracle.sql.BLOB)rs.getBlob(fieldname.trim());
		    }
	    }catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			closeJDBC(null,ps,rs);
		}
		return blob;
	}
	
	public void closeJDBC(Connection conn , PreparedStatement ps , ResultSet rs){
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
	
	@SuppressWarnings("unchecked")
	@Override
	public RecShip getRecShipByRecAttId(String recAttId){
		String hql = " from RecShip t where t.recAttId=? ";
		Query query = getEm().createQuery(hql);
		query.setParameter(1, recAttId);
		List<RecShip> list = query.getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public void addRecShip(RecShip recShip){
		if(null != recShip.getId()){
			getEm().merge(recShip);
		}else{
			getEm().persist(recShip);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RecShip> getRecShipByRecId(String recId){
		String hql = " from RecShip t where t.recId=? order by t.isZw,t.fileTime,t.createTime ";
		Query query = getEm().createQuery(hql);
		query.setParameter(1, recId);
		List<RecShip> list = query.getResultList();
		if(null != list && list.size()>0){
			return list;
		}else{
			return null;
		}
	}

}
