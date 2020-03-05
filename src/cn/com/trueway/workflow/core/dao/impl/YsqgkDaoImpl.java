package cn.com.trueway.workflow.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.dao.YsqgkDao;
import cn.com.trueway.workflow.core.pojo.Ysqgk;

public class YsqgkDaoImpl extends BaseDao implements YsqgkDao {

	@Override
	public Ysqgk getYsqgkByinstanceId(String instanceId) {
		String hql = "from Ysqgk t where t.instanceid='" + instanceId + "'";
		List<Object> list = getEm().createQuery(hql).getResultList();
		if (list != null && list.size() != 0) {
			return (Ysqgk) list.get(0);
		}
		return null;
	}

	@Override
	public void addYsqgk(Ysqgk ysqgk) {
		if (ysqgk.getId() != null && !"".equals(ysqgk.getId())) {
			getEm().merge(ysqgk);
		} else {
			if ("".equals(ysqgk.getId())) {
				ysqgk.setId(null);
			}
			getEm().persist(ysqgk);
		}
	}

	@Override
	public int getCountYsqgk(Map<String, String> map) {
		String sql = "select count(id) from t_wf_core_ysqgk  where 1=1 ";
		if(map!=null){
			if(map.get("issqdj") != null && "1".equals(map.get("issqdj"))){
				sql +=" and (instanceid is null ";
				if (map.get("instanceId") != null && !"".equals(map.get("instanceId"))) {
					sql += " or instanceId = '" + map.get("instanceId") + "'" ;
				}
				sql+=")";
			}else{
				if (map.get("xm") != null && !"".equals(map.get("xm"))) {
					sql += " and (xm like '%" + map.get("xm") + "%' or fr_mc like '%"
							+ map.get("xm") + "%')";
				}
				if (map.get("sqnr") != null && !"".equals(map.get("sqnr"))) {
					sql += " and (sqnrgy like '%" + map.get("sqnr")
							+ "%' or fr_sqnrgy like '%" + map.get("sqnr") + "%')";
				}

				if (map.get("startTime") != null && !"".equals(map.get("startTime"))) {
					sql += " and (fr_sqsj>= to_date('" + map.get("startTime")
							+ "','yyyy-mm-dd') or dqsj >= to_date('"
							+ map.get("startTime") + "','yyyy-mm-dd'))";
				}

				if (map.get("endTime") != null && !"".equals(map.get("endTime"))) {
					sql += " and (fr_sqsj<= to_date('" + map.get("endTime")
							+ "','yyyy-mm-dd') or dqsj <= to_date('"
							+ map.get("endTime") + "','yyyy-mm-dd'))";
				}
			}
		}
		return ((BigDecimal) super.getEm().createNativeQuery(sql)
				.getResultList().get(0)).intValue();
	}

	@Override
	public List<Ysqgk> getYsqgks(Map<String, String> map, int pageIndex,
			int pageSize) {
		String querySql = "from Ysqgk t where 1=1 ";
		if(map!=null){
			if(map.get("issqdj") != null && "1".equals(map.get("issqdj"))){
				querySql +=" and (instanceid is null ";
				if (map.get("instanceId") != null && !"".equals(map.get("instanceId"))) {
					querySql += " or instanceid = '" + map.get("instanceId") + "'" ;
				}
				querySql+=")";
			}else{
				if (map.get("xm") != null && !"".equals(map.get("xm"))) {
					querySql += " and (t.xm like '%" + map.get("xm")
							+ "%' or t.fr_mc like '%" + map.get("xm") + "%')";
				}
				if (map.get("sqnr") != null && !"".equals(map.get("sqnr"))) {
					querySql += " and (t.sqnrgy like '%" + map.get("sqnr")
							+ "%' or t.fr_sqnrgy like '%" + map.get("sqnr") + "%')";
				}

				if (map.get("startTime") != null && !"".equals(map.get("startTime"))) {
					querySql += " and (t.fr_sqsj>= to_date('" + map.get("startTime")
							+ "','yyyy-mm-dd') or t.dqsj >= to_date('"
							+ map.get("startTime") + "','yyyy-mm-dd'))";
				}

				if (map.get("endTime") != null && !"".equals(map.get("endTime"))) {
					querySql += " and (t.fr_sqsj<= to_date('" + map.get("endTime")
							+ "','yyyy-mm-dd') or t.dqsj <= to_date('"
							+ map.get("endTime") + "','yyyy-mm-dd'))";
				}
			}
		}else{
			querySql +=" and instanceid is null";
		}
		querySql += " order by t.djsj desc";
		Query query = super.getEm().createQuery(querySql.toString());
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public void delete(String id) {
		String sql = "delete t_wf_core_ysqgk where id='" + id + "'";
		super.getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public Ysqgk getYsqgkById(String id) {
		String hql = "from Ysqgk t where t.id='" + id + "'";
		List<Object> list = getEm().createQuery(hql).getResultList();
		if (list != null && list.size() != 0) {
			return (Ysqgk) list.get(0);
		}
		return null;
	}

	@Override
	public int getZtByInstanceid(String instanceid) {
		String sql = "select count(t.wf_process_uid) from t_wf_process t " +
				" where (t.wf_instance_uid='"+instanceid+"' or t.allinstanceid='"+instanceid+"') and t.is_over='NOT_OVER'";
		List<Object> list = getEm().createNativeQuery(sql).getResultList();
		if(list!=null&&list.size()>0){
			return ((BigDecimal)list.get(0)).intValue();
		}
		return 0;
	}

	/**
	 * 根据父节点获取数据
	 * 
	 * @param fInstanceId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getYsqgkMapByinstanceId(String fInstanceId) {
		String sql ="select t.ID,T.LX,T.XM,T.GZDW,T.ZJMC,T.ZJHM,T.TXDZ,T.YZBM,T.LXDH,T.CZ," +
				"T.DZYX,TO_CHAR(T.DQSJ,'YYYY-MM-DD') AS DQSJ,T.SQNRGY,T.FR_MC,T.FR_ZZJGDM," +
				"T.FR_YYZZ,T.FR_FRDB,T.FR_LXRXM,T.FR_LXRDH,T.FR_CZ,T.FR_LXRDZYX," +
				"TO_CHAR(T.FR_SQSJ,'YYYY-MM-DD') AS FR_SQSJ,T.FR_SQNRGY,T.INSTANCEID," +
				"T.DJRID,T.DJRXM,TO_CHAR(T.DJSJ,'YYYY-MM-DD') AS DJSJ from t_wf_core_ysqgk t " +
				" where t.instanceid='"+fInstanceId+"'";
		Query query = getEm().createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		List<Map<String, Object>> newList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			Object[] obj=list.get(i);
			map.put("ID", obj[0]);
			map.put("LX", obj[1]);
			map.put("XM", obj[2]);
			map.put("GZDW", obj[3]);
			map.put("ZJMC", obj[4]);
			map.put("ZJHM", obj[5]);
			map.put("TXDZ", obj[6]);
			map.put("YZBM", obj[7]);
			map.put("LXDH", obj[8]);
			map.put("CZ", obj[9]);
			map.put("DZYX", obj[10]);
			map.put("DQSJ", obj[11]);
			map.put("SQNRGY", obj[12]);
			map.put("FR_MC", obj[13]);
			map.put("FR_ZZJGDM", obj[14]);
			map.put("FR_YYZZ", obj[15]);
			map.put("FR_FRDB", obj[16]);
			map.put("FR_LXRXM", obj[17]);
			map.put("FR_LXRDH", obj[18]);
			map.put("FR_CZ", obj[19]);
			map.put("FR_LXRDZYX", obj[20]);
			map.put("FR_SQSJ", obj[21]);
			map.put("FR_SQNRGY", obj[22]);
			map.put("INSTANCEID", obj[23]);
			map.put("DJRID", obj[24]);
			map.put("DJRXM", obj[25]);
			map.put("DJSJ", obj[26]);
			newList.add(map);
		}
		return newList;
	}

	@Override
	public void updateInstanceId(String id, String instanceId,int type) {
		if(type==2){
			String sql = "update  t_wf_core_ysqgk t set t.instanceid='"
					+instanceId+"' where t.id='"+id+"'";
			super.getEm().createNativeQuery(sql).executeUpdate();
		}else if (type==1){
			String sql = "update  t_wf_core_ysqgk t set t.instanceid='' where t.instanceid='"+instanceId+"'";
			super.getEm().createNativeQuery(sql).executeUpdate();
		}
	}

	@Override
	public List<Ysqgk> getYsqgks(Map<String, String> map) {
		String querySql = "from Ysqgk t where 1=1 ";
		if(map!=null){
			if(map.get("issqdj") != null && "1".equals(map.get("issqdj"))){
				querySql +=" and (instanceid is null ";
				if (map.get("instanceId") != null && !"".equals(map.get("instanceId"))) {
					querySql += " or instanceid = '" + map.get("instanceId") + "'" ;
				}
				querySql+=")";
			}else{
				if (map.get("xm") != null && !"".equals(map.get("xm"))) {
					querySql += " and (t.xm like '%" + map.get("xm")
							+ "%' or t.fr_mc like '%" + map.get("xm") + "%')";
				}
				if (map.get("sqnr") != null && !"".equals(map.get("sqnr"))) {
					querySql += " and (t.sqnrgy like '%" + map.get("sqnr")
							+ "%' or t.fr_sqnrgy like '%" + map.get("sqnr") + "%')";
				}

				if (map.get("startTime") != null && !"".equals(map.get("startTime"))) {
					querySql += " and (t.fr_sqsj>= to_date('" + map.get("startTime")
							+ "','yyyy-mm-dd') or t.dqsj >= to_date('"
							+ map.get("startTime") + "','yyyy-mm-dd'))";
				}

				if (map.get("endTime") != null && !"".equals(map.get("endTime"))) {
					querySql += " and (t.fr_sqsj<= to_date('" + map.get("endTime")
							+ "','yyyy-mm-dd') or t.dqsj <= to_date('"
							+ map.get("endTime") + "','yyyy-mm-dd'))";
				}
			}
		}else{
			querySql +=" and instanceid is null";
		}
		querySql += " order by t.djsj desc";
		Query query = super.getEm().createQuery(querySql.toString());
		return query.getResultList();
	}
}
