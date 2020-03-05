package cn.com.trueway.archives.using.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.lob.SerializableClob;

import cn.com.trueway.archives.using.dao.ArchiveUsingDao;
import cn.com.trueway.archives.using.pojo.ArchiveMsg;
import cn.com.trueway.archives.using.pojo.ArchiveNode;
import cn.com.trueway.archives.using.pojo.Basicdata;
import cn.com.trueway.archives.using.pojo.Transferform;
import cn.com.trueway.archives.using.pojo.Transferstore;
import cn.com.trueway.archives.using.util.ChangeDataType;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;

public class ArchiveUsingDaoImpl extends BaseDao implements ArchiveUsingDao {
	 ChangeDataType dataType = new ChangeDataType();
	@SuppressWarnings("unchecked")
	public List<Basicdata> queryDataList(Map<String,String> map){
		String hql = " from Basicdata where 1=1 ";
		String conditionSql =  map.get("conditionSql");
		if(CommonUtil.stringNotNULL(conditionSql)){
			hql += conditionSql;
		}
		hql += "   order by  type,numIndex asc ";
		int pageIndex = Integer.parseInt(map.get("pageIndex"));
		int pageSize = Integer.parseInt(map.get("pageSize"));
		Query query = this.getEm().createQuery(hql);
		query.setFirstResult(pageIndex);// 从哪条记录开始
		query.setMaxResults(pageSize);// 每页显示的最大记录数
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ArchiveNode> queryNodeList(Map<String, String> map){
		String hql = " from ArchiveNode where 1=1 and (vc_isDel != '1' or vc_isDel is null) ";
		String conditionSql = map.get("conditionSql");
		if(CommonUtil.stringNotNULL(conditionSql)){
			hql += conditionSql;
		}
		hql += " order by to_number(vc_number) asc ";
		return this.getEm().createQuery(hql).getResultList();
	}
	
	public List<Basicdata> queryTypeList(Map<String,String> map){
		String sql = " select type,min(Typename) from T_USING_BASICDATA  where 1=1  ";
		String conditionSql = map.get("conditionSql");
		 if(CommonUtil.stringNotNULL(conditionSql)){
			 sql += conditionSql;
		 }
		 sql += " group by type ";	
		Query query = getEm().createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		List<Basicdata> typeList = new ArrayList<Basicdata>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				Basicdata basicdata = new Basicdata();
				basicdata.setType(getSqlVal(((Object[]) list.get(i))[0]));
				basicdata.setTypeName(getSqlVal(((Object[]) list.get(i))[1]));
				typeList.add(basicdata);
			}
		} else {
			typeList =  null;
		}
		return typeList;
	}
	
	public String getSqlVal(Object obj) {
		String val = "";
		if (obj != null) {
			val = obj.toString();
		}
		return val;
	}
	
	public void addBasicData(Basicdata basicdata){
		if(basicdata.getId() !=null && !"".equals(basicdata)){
			this.getEm().merge(basicdata);
		}else{
			this.getEm().persist(basicdata);
		}
		this.getEm().flush();
		
	}
	
	public void deiBasicData(String ids){
		String hql = " DELETE FROM Basicdata where id in("+ids+") ";
		Query query = getEm().createQuery(hql);
		query.executeUpdate();
	}
	
	public int getDataCount(Map<String,String> map){
		String hql = " from Basicdata where 1=1 ";
		String conditionSql =  map.get("conditionSql");
		if(CommonUtil.stringNotNULL(conditionSql)){
			hql += conditionSql;
		}
		@SuppressWarnings("unchecked")
		List<Basicdata> list =this.getEm().createQuery(hql).getResultList();
		if(list != null){
			return list.size();
		}else{
			return 0;
		}
	}
	
	public void addData(ArchiveNode archiveNode){
		if(archiveNode.getId() !=null && !"".equals(archiveNode)){
			this.getEm().merge(archiveNode);
		}else{
			this.getEm().persist(archiveNode);
		}
		this.getEm().flush();
	}
	
	public int getCountByName(String colName,String tableName){
		String sql = " select count(*) from T_USING_ARCHIVENODE e where 1=1 and  e.VC_FIELNAME = '"+colName+"'  and (e.vc_isDel !='1' or e.vc_isDel is null) and vc_table = '"+tableName+"'";
		Query query = getEm().createNativeQuery(sql);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	public void delData(String ids){
		String sql = " update T_USING_ARCHIVENODE set vc_isdel = '1' where id in ("+ids+")";
		Query query = getEm().createNativeQuery(sql);
		query.executeUpdate();
		this.getEm().flush();
	}
	
	public void addfield(ArchiveNode archiveNode){
		String tableName = archiveNode.getVc_table();
		String feilName = archiveNode.getVc_fielName();
		String vc_length = archiveNode.getVc_length();
		String vc_type = archiveNode.getVc_fielType();
		String sql = " alter table "+tableName+" add ("+feilName+"  "+vc_type;
		if(CommonUtil.stringNotNULL(vc_length.trim())){
			sql += "("+vc_length+")";
		}
		sql +=")";
		Query query = getEm().createNativeQuery(sql);
		query.executeUpdate();
		this.getEm().flush();
	}
	
	public ArchiveNode getArchiveNodeById(String id){
		String hql = " from ArchiveNode where id = '"+id+"'";
		return (ArchiveNode) this.getEm().createQuery(hql).getResultList().get(0);
	}
	
	public void upfield(String fielName,ArchiveNode archiveNode){
		String tableName = archiveNode.getVc_table();
		String newfielName = archiveNode.getVc_fielName();
		if(!newfielName.equals(fielName) ){
			String sql = " alter table "+tableName+" rename column "+fielName+" to "+newfielName;
			Query query = getEm().createNativeQuery(sql);
			query.executeUpdate();
			this.getEm().flush();
		}
	}
	
	@Override
	public void upfieldLength(String fielLength, ArchiveNode archiveNode) {
		String tableName = archiveNode.getVc_table();
		String fielName = archiveNode.getVc_fielName();
		String fielLengthNew = archiveNode.getVc_length();
		String type = archiveNode.getVc_fielType();
		if(!fielLength.equals(fielLengthNew) ){
			String sql = " alter table "+tableName+" modify("+fielName+ " " +type+"("+fielLengthNew+"))";
			Query query = getEm().createNativeQuery(sql);
			query.executeUpdate();
			this.getEm().flush();
		}
	}
	
	public void delfield(ArchiveNode archiveNode){
		String tableName = archiveNode.getVc_table();
		String fielName = archiveNode.getVc_fielName();
		String newfielName = fielName+"del";
		String sql = " alter table "+tableName+" rename column "+fielName+" to "+newfielName;
		Query query = getEm().createNativeQuery(sql);
		query.executeUpdate();
		this.getEm().flush();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Basicdata> queryData(String vc_system){
		String hql = " from Basicdata where 1=1 and type = '"+vc_system+"' order by numIndex  ";
		return (ArrayList<Basicdata>) this.getEm().createQuery(hql).getResultList();
	}
	
	public void insertData(Map<String,String> map){
		String tableName = map.get("tableName");
		String columnSql = map.get("columnSql");
		String valSql = map.get("valSql");
		String sql = " insert into " +tableName+" (" +columnSql+") values ("+valSql+")" ;
		Query query = getEm().createNativeQuery(sql);
		query.executeUpdate();
		this.getEm().flush();
	}
	
	public List<String[]> getValList(List<ArchiveNode> nodeList,Map<String,String> map){
		
		int size = nodeList.size();
		String columnSql = "";
		String orderSql = "";
		for(int i = 0;i<size;i++){
			columnSql +=nodeList.get(i).getVc_fielName()+",";
			if(!"".equals(nodeList.get(i).getVc_fielType())&&("date".equals(nodeList.get(i).getVc_fielType())||"DATE".equals(nodeList.get(i).getVc_fielType()))){
				orderSql +=nodeList.get(i).getVc_fielName()+" desc,";
			}
		}
		if(!"".equals(columnSql)&&columnSql != null){
			orderSql += nodeList.get(0).getVc_fielName()+ " desc";
			columnSql ="id," +columnSql.substring(0,columnSql.length()-1);
			String sql =" select " +columnSql+"  from T_USING_FORM where 1=1 ";
			String condition = map.get("sql");
			if(!"".equals(condition)&&condition != null){
				sql += condition;
			}
			if(!"".equals(orderSql)){
				sql +=" order by "+orderSql;
			}
			Query query = getEm().createNativeQuery(sql);
			String pagesize = map.get("pagesize");
			String pageindex = map.get("pageindex");
			if (pagesize !=null&&pageindex !=null) {
				query.setFirstResult(Integer.parseInt(pageindex));
				query.setMaxResults(Integer.parseInt(pagesize));
			}
			@SuppressWarnings("unchecked")
			List<Object[]> list = query.getResultList();
			List<String[]> returnList = new ArrayList<String[]>();
			if (list.size() > 0) {
				for(int i=0;i<list.size();i++){
					String[] str = new String[size+1];
					for(int j=0;j<=size;j++){
						Object[] o = (Object[]) list.get(i);
						Object ob = o[j];
						str[j] = dataType.ClobToString(ob);
					}
					returnList.add(str);
				}
			} 
			return returnList;
		}else{
			return null;
		}
	}
	
	public int getValCount(List<ArchiveNode> nodeList,Map<String,String> map){
		String count = " select count(*) from T_USING_FORM where 1=1 ";
		String sql = map.get("sql");
		if(!"".equals(sql)&& sql != null){
			count += sql;
		}
		return Integer.parseInt(super.getEm().createNativeQuery(count.toString()).getSingleResult().toString());
	}
	
	public void delByIds(Map<String,String> map){
		String tableName = map.get("tableName");
		String ids = map.get("ids");
		String sql = " DELETE FROM "+tableName+" where id in ("+ids+")";
		Query query = getEm().createNativeQuery(sql);
		query.executeUpdate();
		this.getEm().flush();
	}
	
	public String getVals(Map<String,String> map){
		String tableName = map.get("tableName");
		String feildName = map.get("feildName");
		String id = map.get("id");
		String sql = " select "+feildName+ " from "+tableName + " where id = '"+id+"'";
		Query query = getEm().createNativeQuery(sql);
		Object s = query.getSingleResult();
		String str =  dataType.ClobToString(s);
		return str;
	}
	
	public void updateData(Map<String,String> map){
		String sql = map.get("sql");
		String tableName = map.get("tableName");
		String updateSql = " update "+tableName+" "+sql;
		Query query = getEm().createNativeQuery(updateSql);
		query.executeUpdate();
		this.getEm().flush();
	}

	@Override
	public void addToJYK(String sql) {
		 this.getEm().createNativeQuery(sql).executeUpdate();
		 this.getEm().flush();
	}
	
	public String getIdByCondition(Map<String,String> map){
		String sql = map.get("sql");
		String tableName = map.get("tableName");
		String selectSql = "select id from "+tableName+" where 1=1 "+sql; 
		Query query = getEm().createNativeQuery(selectSql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		if(list.size()>0){
			return dataType.ClobToString(list.get(0));
		}else{
			return null;
		}
	}
	
	public List<String[]> getStoreVal(Map<String,String> map){
		String tableName = map.get("tableName");
		String feildName = map.get("feildName");
		String id = map.get("formId");
		String sql = " select id,"+feildName+ " from "+tableName + " where formId = '"+id+"'";
		Query query = getEm().createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		List<String[]> str = new ArrayList<String[]>();
		if(list.size()>0){
			String[] s = new String[2];
			Object s0 = list.get(0)[0];
			Object s1 = list.get(0)[1];
			if(s0 != null &&!"".equals(s0)){
				s[0] = dataType.ClobToString(s0);
			}else{
				s[0] = "";
			}
			if(s1 != null &&!"".equals(s1)){
				s[1] = dataType.ClobToString(s1);
			}else{
				s[1] = "";
			}
			str.add(s);
		}else{
			return null;
		}
		return str;
	}
	
	public List<String[]> getStoreVal(List<ArchiveNode> nodeList,Map<String,String> map){
		int size = nodeList.size();
		String sql = " select id,path,";
		String formId = map.get("formId");
		for(int i = 0;i<nodeList.size();i++){
			String vc_fielType = nodeList.get(i).getVc_fielName();
			sql +=" "+vc_fielType+",";
		}
		sql =  sql.substring(0,sql.length()-1) + " from T_USING_STORE WHERE 1=1  ";
		sql += " and formId = '"+formId+"'";
		Query query = getEm().createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		List<String[]> returnList = new ArrayList<String[]>();
		if (list.size() > 0) {
			for(int i=0;i<list.size();i++){
				String[] str = new String[size+2];//
				for(int j=0;j<=size+1;j++){
					Object[] o = (Object[]) list.get(i);
					Object ob = o[j];
					str[j] = dataType.ClobToString(ob);
				}
				returnList.add(str);
			}
		} 
		if(returnList.size()>0){
			return returnList;
		}else{
			return null;
		}
	}

	@Override
	public void updateStore(String sql) {
		 this.getEm().createNativeQuery(sql).executeUpdate();
		 this.getEm().flush();
	}

	@Override
	public List<Object[]> getFjList(Map<String, String> map) {
		String formbh = map.get("formbh");
		String sql =  " select *  from t_using_fj WHERE 1=1  ";
		sql += " and formId = '"+formbh+"'";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		/*if(list.size()>0){
			return list;
		}else{
			return null;
		}*/
		return list;
	}

	@Override
	public List<Map<String, Object>> queryFormlist(Map<String, String> map,
			List<ArchiveNode> nodeList) {
		List<Map<String, Object>> datalist = new ArrayList<Map<String,Object>>();
		String id=map.get("id");
		String sql =" select id ";
		for(int i=0;i<nodeList.size();i++){
			sql+=","+nodeList.get(i).getVc_fielName();
		}
		sql+=" from T_USING_FORM where id='"+id+"'";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		if(list!=null&&list.size()>0){	
			for(int i=0;i<list.size();i++){
				Map<String,Object> datamap = new HashMap<String, Object>();
				datamap.put("id", list.get(i)[0].toString());
				for(int j=0;j<nodeList.size();j++){
					String value= list.get(i)[j+1]!=null?list.get(i)[j+1].toString():"";
					datamap.put(nodeList.get(j).getVc_fielName(), value);
				}
				datalist.add(datamap);
			}
			return datalist;
		}else{
			return null;
		}
	}

	@Override
	public List<Object[]> getFJById(String id) {
		String sql =  " select *  from t_using_fj WHERE 1=1  ";
		sql += " and id = '"+id+"'";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		return list;
	}

	@Override
	public void updateArchiveMsg(ArchiveMsg msg) {	
		if(msg.getId() == null || "".equals(msg.getId())){
			msg.setId(null);
			this.getEm().persist(msg);
		}else{
			this.getEm().merge(msg);
		}
	}

	@Override
	public List<ArchiveMsg> getArchiveMsgList(Map<String, String> map,Integer pageIndex,Integer pageSize) {
		String hql = " from ArchiveMsg where 1=1 ";
		String id =  map.get("id");
		if(CommonUtil.stringNotNULL(id)){
			hql += " and id='"+id+"'";
		}
		String msgType =  map.get("msgType");
		if(CommonUtil.stringNotNULL(msgType)){
			hql += " and msgType='"+msgType+"'";
		}
		String status =  map.get("status");
		if(CommonUtil.stringNotNULL(status)){
			hql += " and status='"+status+"'";
		}
		String recevier =  map.get("recevier");
		if(CommonUtil.stringNotNULL(recevier)){
			hql += " and (RECEVIER='"+recevier+"' or RECEVIER is null or RECEVIER=' ' )";
		}
		hql += "   order by  sendTime desc ";
		
		Query query = this.getEm().createQuery(hql);
		if(pageIndex!=null&&pageSize!=null){
			query.setFirstResult(pageIndex);// 从哪条记录开始
			query.setMaxResults(pageSize);// 每页显示的最大记录数
		}
		return query.getResultList();
	}

	@Override
	public int getTransferformCount(Map<String, String> map) {
		String hql = " from Transferform where 1=1 ";
		String statusSe = map.get("statusSe");
		if(CommonUtil.stringNotNULL(statusSe)){
			hql += " and formStatus='"+statusSe+"'";
		}
		String content = map.get("content");
		if(CommonUtil.stringNotNULL(content)){
			hql += " and applyName like '%"+content+"%' or registrantName like '%"+content+"%'";
		}
		Query query = this.getEm().createQuery(hql);
		return query.getResultList()!=null?query.getResultList().size():0;
	}

	@Override
	public List<Transferform> getTransferformList(Map<String, String> map,
			Integer pageIndex, Integer pageSize) {
		String hql = " from Transferform where 1=1 ";
		String id = map.get("id");
		if(CommonUtil.stringNotNULL(id)){
			hql += " and id='"+id+"'";
		}	
		String statusSe = map.get("statusSe");
		if(CommonUtil.stringNotNULL(statusSe)){
			hql += " and formStatus='"+statusSe+"'";
		}
		String content = map.get("content");
		if(CommonUtil.stringNotNULL(content)){
			hql += " and applyName like '%"+content+"%' or registrantName like '%"+content+"%'";
		}
		hql += "   order by  formId desc ";
		
		Query query = this.getEm().createQuery(hql);
		if(pageIndex!=null&&pageSize!=null){
			query.setFirstResult(pageIndex);// 从哪条记录开始
			query.setMaxResults(pageSize);// 每页显示的最大记录数
		}
		return query.getResultList();
	}

	@Override
	public void updateTransferForm(Transferform tf) {
		if(tf.getId() == null || "".equals(tf.getId())){
			tf.setId(null);
			this.getEm().persist(tf);
		}else{
			this.getEm().merge(tf);
		}
	}

	@Override
	public void addToDJK(Transferstore ts) {
		if(ts.getId() == null || "".equals(ts.getId())){
			ts.setId(null);
			this.getEm().persist(ts);
		}else{
			this.getEm().merge(ts);
		}
	}

	@Override
	public List<Transferstore> getTransferstoreList(Map<String, String> map,
			Integer pageIndex, Integer pageSize) {
		String hql = " from Transferstore where 1=1 ";
		String formId = map.get("formId");
		if(CommonUtil.stringNotNULL(formId)){
			hql += " and formId='"+formId+"'";
		}	
		
		Query query = this.getEm().createQuery(hql);
		if(pageIndex!=null&&pageSize!=null){
			query.setFirstResult(pageIndex);// 从哪条记录开始
			query.setMaxResults(pageSize);// 每页显示的最大记录数
		}
		return query.getResultList();
	}

	@Override
	public List<Object[]> getChecDJKList(Map<String, String> map) {
		String sql = " select b.title,b.glid from t_archive_transferform a left join t_archive_transferstore b on a.id=b.formid  "
				+ "where 1=1 ";		
		/*String djdId = map.get("djdId");
		if(CommonUtil.stringNotNULL(djdId)){
			sql += " and a.id='"+djdId+"'";
		}	*/
		String glid = map.get("glid");
		if(CommonUtil.stringNotNULL(glid)){
			sql += " and b.glid='"+glid+"'";
		}	
		String structId = map.get("structId");
		if(CommonUtil.stringNotNULL(structId)){
			sql += " and b.structid='"+structId+"'";
		}	
		sql+=" and a.transfertype in ('档案','资料')  and a.formstatus in ('1','2') ";	
		List<Object[]> list = this.getEm().createNativeQuery(sql).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getFormStutsById(String sql) {
		Query query = this.getEm().createNativeQuery(sql);
		List<String> resultList = query.getResultList();
		if(resultList != null) {
			return resultList.get(0);
		} else {
			return null;
		}
	}

}
