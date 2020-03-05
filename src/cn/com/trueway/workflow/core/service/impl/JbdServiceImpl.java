package cn.com.trueway.workflow.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.dao.JdbDao;
import cn.com.trueway.workflow.core.pojo.Jbd;
import cn.com.trueway.workflow.core.service.JdbService;

public class JbdServiceImpl implements JdbService {
	private JdbDao jdbDao;

	public JdbDao getJdbDao() {
		return jdbDao;
	}

	public void setJdbDao(JdbDao jdbDao) {
		this.jdbDao = jdbDao;
	}

	@Override
	public Jbd getJbdByinstanceId(String instanceId) {
		return jdbDao.getJbdByinstanceId(instanceId);
	}

	@Override
	public void addJdb(Jbd jbd) {
		jdbDao.addJdb(jbd);
	}

	@Override
	public void delete(String id) {
		jdbDao.delete(id);
	}

	@Override
	public int getCountJdb(Map<String, String> map) {
		return jdbDao.getCountJdb(map);
	}

	@Override
	public List<Jbd> getJbds(Map<String, String> map, int pageIndex,
			int pageSize) {
		return jdbDao.getJbds(map, pageIndex, pageSize);
	}

	@Override
	public Jbd getJdbById(String id) {
		return jdbDao.getJdbById(id);
	}

	@Override
	public List<Map<String, String>> getJdbList(Map<String, String> map,
			int pageIndex, int pageSize) {
		List<Jbd> list = jdbDao.getJbds(map, (pageIndex - 1) * pageSize,
				pageIndex * pageSize);
		List<Map<String, String>> newList = null;
		if (list != null && list.size() > 0) {
			newList = new ArrayList<Map<String, String>>();
			for (int i = 0; i < list.size(); i++) {
				Jbd jbd = list.get(i);
				Map<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("id", jbd.getId());
				hashMap.put("instanceid", jbd.getInstanceid());
				hashMap.put("sqr", jbd.getSqr() == null ? "" : jbd.getSqr());
				hashMap.put("sqfl", jbd.getSqfl() == null ? "" : jbd.getSqfl());
				hashMap.put("blsx", jbd.getBlsx() == null ? "" : jbd.getBlsx());
				hashMap.put("jbsj",
						jbd.getJbsj() == null ? "" : new SimpleDateFormat(
								"yyyy-MM-dd").format(jbd.getJbsj()));
				newList.add(hashMap);
			}
		}
		return newList;
	}

	@Override
	public Map<String, Object> getJBDMapByinstanceId(String instanceId) {
		List<Map<String, Object>> list = jdbDao.getJBDMapByinstanceId(instanceId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateInstanceId(String id, String instanceId, int type) {
		jdbDao.updateInstanceId(id,instanceId,type);
	}

	@Override
	public List<Map<String, String>> getJdbList(Map<String, String> map) {
		List<Jbd> list = jdbDao.getJbds(map);
		List<Map<String, String>> newList = null;
		if (list != null && list.size() > 0) {
			newList = new ArrayList<Map<String, String>>();
			for (int i = 0; i < list.size(); i++) {
				Jbd jbd = list.get(i);
				Map<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("id", jbd.getId());
				hashMap.put("instanceid", jbd.getInstanceid());
				hashMap.put("sqr", jbd.getSqr() == null ? "" : jbd.getSqr());
				hashMap.put("sqfl", jbd.getSqfl() == null ? "" : jbd.getSqfl());
				hashMap.put("blsx", jbd.getBlsx() == null ? "" : jbd.getBlsx());
				hashMap.put("jbsj",
						jbd.getJbsj() == null ? "" : new SimpleDateFormat(
								"yyyy-MM-dd").format(jbd.getJbsj()));
				newList.add(hashMap);
			}
		}
		return newList;
	}

}
