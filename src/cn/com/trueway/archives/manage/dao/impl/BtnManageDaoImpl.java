package cn.com.trueway.archives.manage.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import cn.com.trueway.archives.manage.dao.BtnManageDao;
import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.UuidGenerator;

public class BtnManageDaoImpl extends BaseDao implements  BtnManageDao{

	@Override
	public int getBtnManageListCount(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_btn_dic where 1=1");
//		String estitle = (String) map.get("esTitle");
//		if(estitle != null && !"".equals(estitle)) {
//			sql.append(" and estitle like ?");
//		}
		Query query = this.getEm().createNativeQuery(sql.toString());
//		if(estitle != null && !"".equals(estitle)) {
//			query.setParameter(1, "%"+estitle+"%");
//		}
		BigDecimal count = (BigDecimal) query.getSingleResult();
		if(count !=null) {
			return count.intValue();
		} else {
			return 0;
		}
	}

	@Override
	public List<Map<String, String>> getBtnManageList(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,btn_name,btn_description,btn_sort from t_btn_dic where 1=1");
//		String estitle = (String) map.get("esTitle");
//		if(estitle != null && !"".equals(estitle)) {
//			sql.append(" and estitle like ?");
//		}
		String id = (String) map.get("id");
		if(id != null && !"".equals(id)) {
			sql.append(" and id=?");
		}
//		sql.append(" order by btn_sort desc");
		Query query = this.getEm().createNativeQuery(sql.toString());
//		if(estitle != null && !"".equals(estitle)) {
//			query.setParameter(1, "%"+estitle+"%");
//		}
		if(id != null && !"".equals(id)) {
			query.setParameter(1, id);
		}
		Integer pageindex = (Integer)map.get("pageIndex");
		Integer pagesize = (Integer)map.get("pageSize");
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		List<Object[]> lst = query.getResultList();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(int i = 0; i < lst.size(); i++) {
			Object[] obj = lst.get(i);
			Map<String,String> oMap = new HashMap<String,String>();
			oMap.put("id", obj[0]==null?"":obj[0].toString());
			oMap.put("btn_name", obj[1]==null?"":obj[1].toString());
			oMap.put("btn_description", obj[2]==null?"":obj[2].toString());
			oMap.put("btn_sort", obj[3]==null?"":obj[3].toString());
			list.add(oMap);
		}
		return list;
	}

	@Transactional
	@Override
	public void saveBtnManageRecord(Map<String, Object> map) {
		String id = (String) map.get("id");
		String btn_name = (String) map.get("btn_name");
		String btn_description = (String) map.get("btn_description");
		String btn_sort = (String) map.get("btn_sort");
//		String esDate = (String) map.get("esDate");
		if("newAdd".equals(id)) {
			id = UuidGenerator.generate36UUID();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into t_btn_dic(id,btn_name,btn_description,btn_sort) values(?,?,?,?)");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, id);
			query.setParameter(2, btn_name);
			query.setParameter(3, btn_description);
			query.setParameter(4, btn_sort);
			query.executeUpdate();
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("update t_btn_dic t set t.btn_name=?,t.btn_description=?,t.btn_sort=? where t.id=?");
			Query query = this.getEm().createNativeQuery(sql.toString());
			query.setParameter(1, btn_name);
			query.setParameter(2, btn_description);
			query.setParameter(3, btn_sort);
			query.setParameter(4, id);
			query.executeUpdate();
		}
		this.getEm().flush();
		
	}

	@Override
	public void deleteOneBtnManageById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_btn_dic t where t.id=?");
		Query query = this.getEm().createNativeQuery(sql.toString());
		query.setParameter(1, id);
		query.executeUpdate();
		getEm().flush();
	}

}
