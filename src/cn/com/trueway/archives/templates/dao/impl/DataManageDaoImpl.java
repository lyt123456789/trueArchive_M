package cn.com.trueway.archives.templates.dao.impl;

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

import cn.com.trueway.archives.templates.dao.DataManageDao;
import cn.com.trueway.archives.templates.dao.DataTempDao;
import cn.com.trueway.archives.templates.pojo.EssStructure;
import cn.com.trueway.archives.templates.pojo.EssTag;
import cn.com.trueway.archives.templates.pojo.EssTree;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;



public class DataManageDaoImpl extends BaseDao implements DataManageDao {

	@Override
	public int getStructureDataCount(Map<String, Object> map) {
		String condSql = map.get("conSql")!=null?map.get("conSql").toString():"";
		String tableName = map.get("tableName")!=null?map.get("tableName").toString():"";
		List<EssTag> et_show = (List<EssTag>)map.get("showField");
		String keyWord = map.get("keyWord")!=null?map.get("keyWord").toString():"";
		String condSql2="";
		if(!"".equals(keyWord)){
			condSql2+=" and ( ";
			for(int i=0;i<et_show.size();i++){
				if(i==et_show.size()-1){
					condSql2+=" C"+et_show.get(i).getId()+" like ? ";
				}else{
					condSql2+=" C"+et_show.get(i).getId()+" like ? or ";
				}
			}
			condSql2=condSql2+")";
		}
		
		String sql = " select count(*) from "+tableName+" where 1=1 "+condSql+condSql2;
		Query query = this.getEm().createNativeQuery(sql);
		if(!"".equals(keyWord)){
			for(int i=0;i<et_show.size();i++){
				query.setParameter(i+1, "%"+keyWord+"%");
			}
		}
		Object count = query.getSingleResult();
		return Integer.valueOf(count.toString());
	}

	@Override
	public List<Map<String, Object>> getStructureDataList(
			Map<String, Object> map, Integer pageSize, Integer pageIndex) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		String keyWord = map.get("keyWord")!=null?map.get("keyWord").toString():"";
		String condSql = map.get("conSql")!=null?map.get("conSql").toString():"";
		String tableName = map.get("tableName")!=null?map.get("tableName").toString():"";
		List<EssTag> et_show = (List<EssTag>)map.get("showField");
		String orderSql = map.get("orderSql")!=null?map.get("orderSql").toString():"";
		//展示字段
		StringBuffer showSql = new StringBuffer();
		for(int i=0;i<et_show.size();i++){
			showSql.append(",C"+et_show.get(i).getId());
		}
		//条件1
		String conditionSql = condSql;
		//条件2
		String conditionSql2="";
		if(!"".equals(keyWord)){
			conditionSql2+=" and ( ";
			for(int i=0;i<et_show.size();i++){
				if(i==et_show.size()-1){
					conditionSql2+=" C"+et_show.get(i).getId()+" like ? ";
				}else{
					conditionSql2+=" C"+et_show.get(i).getId()+" like ? or ";
				}
			}
			conditionSql2=conditionSql2+")";
		}
		String sql = " select id,espath "+showSql.toString()+" from "+tableName+" where 1=1 "+conditionSql+conditionSql2;
		if(!"".equals(orderSql)){
			sql+=" order by "+orderSql;
		}
		Query query = this.getEm().createNativeQuery(sql);
		if(!"".equals(keyWord)){
			for(int i=0;i<et_show.size();i++){
				query.setParameter(i+1, "%"+keyWord+"%");
			}
		}
		if(pageSize!=null&&pageIndex!=null){
			query.setFirstResult(pageIndex);// 从哪条记录开始
			query.setMaxResults(pageSize);// 每页显示的最大记录数
		}
		List<Object[]> list = query.getResultList();
		if(list!=null && list.size()>0 ){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map2 = new HashMap<String, Object>();
				map2.put("id", list.get(i)[0]);
				for(int j=0;j<et_show.size();j++){
					map2.put("C"+et_show.get(j).getId(), list.get(i)[j+2]);
				}
				returnList.add(map2);
			}
			return returnList;
		}else{
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> getStructureData(Map<String, Object> map) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		/*String condSql = map.get("conSql")!=null?map.get("conSql").toString():"";*/
		String tableName = map.get("tableName")!=null?map.get("tableName").toString():"";
		List<EssTag> et_show = (List<EssTag>)map.get("showField");
		/*String orderSql = map.get("orderSql")!=null?map.get("orderSql").toString():"";*/
		String id = map.get("id")!=null?map.get("id").toString():"";
		//展示字段
		StringBuffer showSql = new StringBuffer();
		for(int i=0;i<et_show.size();i++){
			showSql.append(",C"+et_show.get(i).getId());
		}
		/*//条件
		String conditionSql = condSql;*/
		
		String sql = " select id,espath "+showSql.toString()+" from "+tableName+" where 1=1 and id="+id;
		Query query = this.getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map2 = new HashMap<String, Object>();
				map2.put("id", list.get(i)[0]);
				for(int j=0;j<et_show.size();j++){
					map2.put("C"+et_show.get(j).getId(), list.get(i)[j+1]);
				}
				returnList.add(map2);
			}
			return returnList;
		}else{
			return null;
		}
	}
	
}
