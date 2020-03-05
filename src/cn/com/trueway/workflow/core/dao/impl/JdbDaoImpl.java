package cn.com.trueway.workflow.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.dao.JdbDao;
import cn.com.trueway.workflow.core.pojo.Jbd;
import cn.com.trueway.workflow.core.pojo.Ysqgk;

public class JdbDaoImpl extends BaseDao implements JdbDao {

	@Override
	public Jbd getJbdByinstanceId(String instanceId) {
		String hql = "from Jbd t where t.instanceid='" + instanceId + "'";
		List<Object> list = getEm().createQuery(hql).getResultList();
		if (list != null && list.size() != 0) {
			return (Jbd) list.get(0);
		}
		return null;
	}

	@Override
	public void addJdb(Jbd jbd) {
		if (jbd.getId() != null && !"".equals(jbd.getId())) {
			getEm().merge(jbd);
		} else {
			if ("".equals(jbd.getId())) {
				jbd.setId(null);
			}
			getEm().persist(jbd);
		}
	}

	@Override
	public void delete(String id) {
		String sql = "delete t_wf_core_jbd where id='" + id + "'";
		super.getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public int getCountJdb(Map<String, String> map) {
		String sql = "select count(id) from t_wf_core_jbd  where 1=1 ";
		if(map!=null){
			if(map.get("isjbddj") != null && "1".equals(map.get("isjbddj"))){
				sql +=" and (instanceid is null ";
				if (map.get("instanceId") != null && !"".equals(map.get("instanceId"))) {
					sql += " or instanceId = '" + map.get("instanceId") + "'" ;
				}
				sql+=")";
			}else{
				if (map.get("sqr") != null && !"".equals(map.get("sqr"))) {
					
					sql += " and sqr like '%" + map.get("sqr") + "%'";
				}
				if (map.get("sqfl") != null && !"".equals(map.get("sqfl"))) {
					sql += " and sqfl like '%" + map.get("sqfl") + "%'";
				}

				if (map.get("startTime") != null && !"".equals(map.get("startTime"))) {
					sql += " and jbsj>= to_date('" + map.get("startTime")
							+ "','yyyy-mm-dd')";
				}

				if (map.get("endTime") != null && !"".equals(map.get("endTime"))) {
					sql += " and jbsj<= to_date('" + map.get("endTime")
							+ "','yyyy-mm-dd')";
				}
			}
		}
		return ((BigDecimal) super.getEm().createNativeQuery(sql)
				.getResultList().get(0)).intValue();
	}

	@Override
	public List<Jbd> getJbds(Map<String, String> map, int pageIndex,
			int pageSize) {
		String querySql = "from Jbd t where 1=1 ";
		if(map!=null){
			if(map.get("isjbddj") != null && "1".equals(map.get("isjbddj"))){
				querySql +=" and (instanceid is null ";
				if (map.get("instanceId") != null && !"".equals(map.get("instanceId"))) {
					querySql += " or instanceId = '" + map.get("instanceId") + "'" ;
				}
				querySql+=")";
			}else{
				if (map.get("sqr") != null && !"".equals(map.get("sqr"))) {
					querySql += " and t.sqr like '%" + map.get("sqr") + "%'";
				}
				if (map.get("sqfl") != null && !"".equals(map.get("sqfl"))) {
					querySql += " and t.sqfl like '%" + map.get("sqfl") + "%'";
				}

				if (map.get("startTime") != null && !"".equals(map.get("startTime"))) {
					querySql += " and t.jbsj>= to_date('" + map.get("startTime")
							+ "','yyyy-mm-dd')";
				}

				if (map.get("endTime") != null && !"".equals(map.get("endTime"))) {
					querySql += " and t.jbsj<= to_date('" + map.get("endTime")
							+ "','yyyy-mm-dd')";
				}
			}
		}
		querySql += " order by t.jbsj desc";
		Query query = super.getEm().createQuery(querySql.toString());
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Jbd getJdbById(String id) {
		String hql = "from Jbd t where t.id='" + id + "'";
		List<Object> list = getEm().createQuery(hql).getResultList();
		if (list != null && list.size() != 0) {
			return (Jbd) list.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getJBDMapByinstanceId(String instanceId) {
		String sql ="select T.ID,T.JBDBH,T.SQR,T.XB,T.LXDH,T.SMDH,T.GDLY,T.DWDZ," +
				"to_char(T.JBSJ,'yyyy-mm-dd') as JBSJ,T.BLSX,T.ZYCD,T.SQFL,T.SQGK,T.XXDZ," +
				"T.SQNR,T.INSTANCEID from t_wf_core_jbd T where T.INSTANCEID='" 
					+instanceId+"'";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		List<Map<String, Object>> newList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			Object[] obj=list.get(i);
			map.put("ID", obj[0]);
			map.put("JBDBH", obj[1]);
			map.put("SQR", obj[2]);
			map.put("XB", obj[3]);
			map.put("LXDH", obj[4]);
			map.put("SMDH", obj[5]);
			map.put("GDLY", obj[6]);
			map.put("DWDZ", obj[7]);
			map.put("JBSJ", obj[8]);
			map.put("BLSX", obj[9]);
			map.put("ZYCD", obj[10]);
			map.put("SQFL", obj[11]);
			map.put("SQGK", obj[12]);
			map.put("XXDZ", obj[13]);
			map.put("SQNR", obj[14]);
			map.put("INSTANCEID", obj[15]);
			newList.add(map);
		}
		return newList;
	}

	@Override
	public void updateInstanceId(String id, String instanceId, int type) {
		if(type==2){
			String sql = "update  T_WF_CORE_JBD t set t.instanceid='"
					+instanceId+"' where t.id='"+id+"'";
			super.getEm().createNativeQuery(sql).executeUpdate();
		}else if (type==1){
			String sql = "update  T_WF_CORE_JBD t set t.instanceid='' where t.instanceid='"+instanceId+"'";
			super.getEm().createNativeQuery(sql).executeUpdate();
		}
	}

	@Override
	public List<Jbd> getJbds(Map<String, String> map) {
		String querySql = "from Jbd t where 1=1 ";
		if(map!=null){
			if(map.get("isjbddj") != null && "1".equals(map.get("isjbddj"))){
				querySql +=" and (instanceid is null ";
				if (map.get("instanceId") != null && !"".equals(map.get("instanceId"))) {
					querySql += " or instanceId = '" + map.get("instanceId") + "'" ;
				}
				querySql+=")";
			}else{
				if (map.get("sqr") != null && !"".equals(map.get("sqr"))) {
					querySql += " and t.sqr like '%" + map.get("sqr") + "%'";
				}
				if (map.get("sqfl") != null && !"".equals(map.get("sqfl"))) {
					querySql += " and t.sqfl like '%" + map.get("sqfl") + "%'";
				}

				if (map.get("startTime") != null && !"".equals(map.get("startTime"))) {
					querySql += " and t.jbsj>= to_date('" + map.get("startTime")
							+ "','yyyy-mm-dd')";
				}

				if (map.get("endTime") != null && !"".equals(map.get("endTime"))) {
					querySql += " and t.jbsj<= to_date('" + map.get("endTime")
							+ "','yyyy-mm-dd')";
				}
			}
		}
		querySql += " order by t.jbsj desc";
		Query query = super.getEm().createQuery(querySql.toString());
		return query.getResultList();
	}

}
