package cn.com.trueway.workflow.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.workflow.core.dao.YsqgkDao;
import cn.com.trueway.workflow.core.pojo.Ysqgk;
import cn.com.trueway.workflow.core.service.YsqgkService;

public class YsqgkServiceImpl implements YsqgkService {
	private YsqgkDao ysqgkDao;

	public YsqgkDao getYsqgkDao() {
		return ysqgkDao;
	}

	public void setYsqgkDao(YsqgkDao ysqgkDao) {
		this.ysqgkDao = ysqgkDao;
	}

	@Override
	public Ysqgk getYsqgkByinstanceId(String instanceId) {
		return ysqgkDao.getYsqgkByinstanceId(instanceId);
	}

	@Override
	public void addYsqgk(Ysqgk ysqgk) {
		ysqgkDao.addYsqgk(ysqgk);
	}

	
	@Override
	public int getCountYsqgk(Map<String, String> map) {
		return ysqgkDao.getCountYsqgk(map);
	}

	@Override
	public List<Ysqgk> getYsqgks(Map<String, String> map, int pageIndex,
			int pageSize) {
		List<Ysqgk> list=ysqgkDao.getYsqgks(map, pageIndex, pageSize);
		for(int i=0;i<list.size();i++){
			Ysqgk ysqgk = list.get(i);
			if(ysqgk.getInstanceid()!=null&&!ysqgk.getInstanceid().equals("")){
				int zt = ysqgkDao.getZtByInstanceid(ysqgk.getInstanceid());
				if(zt>0){
					ysqgk.setZt("办理中");
				}else{
					ysqgk.setZt("已办结");
				}
			}
		}
		return list;
	}

	@Override
	public void delete(String id) {
		ysqgkDao.delete(id);
	}

	@Override
	public Ysqgk getYsqgkById(String id) {
		return ysqgkDao.getYsqgkById(id);
	}

	/**
	 * pageSize为第几页，pageIndex为每页多少字段
	 */
	@Override
	public List<Map<String, String>> getYsqgkList(Map<String, String> map,
			int pageIndex, int pageSize) {

		List<Ysqgk> list = ysqgkDao.getYsqgks(map, (pageIndex - 1) * pageSize,
				pageIndex * pageSize);
		List<Map<String, String>> newList = null;
		if (list != null && list.size() > 0) {
			newList = new ArrayList<Map<String, String>>();
			for (int i = 0; i < list.size(); i++) {
				Ysqgk ysqgk = list.get(i);
				Map<String, String> hashMap = new HashMap<String, String>();
				int lx = ysqgk.getLx();
				hashMap.put("id", ysqgk.getId());
				hashMap.put("instanceId", ysqgk.getInstanceid());
				hashMap.put("djsj",
						ysqgk.getDjsj() == null ? "" : new SimpleDateFormat(
								"yyyy-MM-dd").format(ysqgk.getDjsj()));
				hashMap.put("djrxm",
						ysqgk.getDjrxm() == null ? "" : ysqgk.getDjrxm());
				if (lx == 1) {
					hashMap.put("mc",
							ysqgk.getXm() == null ? "" : ysqgk.getXm());
					hashMap.put("sqnrgy", ysqgk.getSqnrgy() == null ? ""
							: ysqgk.getSqnrgy());
					hashMap.put(
							"sqsj",
							ysqgk.getDqsj() == null ? ""
									: new SimpleDateFormat("yyyy-MM-dd")
											.format(ysqgk.getDqsj()));

				} else {
					hashMap.put("mc",
							ysqgk.getFr_mc() == null ? "" : ysqgk.getFr_mc());
					hashMap.put("sqnrgy", ysqgk.getFr_sqnrgy() == null ? ""
							: ysqgk.getFr_sqnrgy());
					hashMap.put(
							"sqsj",
							ysqgk.getFr_sqsj() == null ? ""
									: new SimpleDateFormat("yyyy-MM-dd")
											.format(ysqgk.getFr_sqsj()));
				}
				newList.add(hashMap);
			}
		}
		return newList;
	}

	/**
	 * 根据父节点获取数据
	 * 
	 * @param fInstanceId
	 * @return
	 */
	@Override
	public Map<String, Object> getYsqgkMapByinstanceId(String fInstanceId) {
		List<Map<String, Object>> list = ysqgkDao.getYsqgkMapByinstanceId(fInstanceId);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateInstanceId(String id, String instanceId, int type) {
		ysqgkDao.updateInstanceId(id,instanceId,type);
	}

	@Override
	public List<Ysqgk> getYsqgks(Map<String, String> map) {
		List<Ysqgk> list=ysqgkDao.getYsqgks(map);
		for(int i=0;i<list.size();i++){
			Ysqgk ysqgk = list.get(i);
			if(ysqgk.getInstanceid()!=null&&!ysqgk.getInstanceid().equals("")){
				int zt = ysqgkDao.getZtByInstanceid(ysqgk.getInstanceid());
				if(zt>0){
					ysqgk.setZt("办理中");
				}else{
					ysqgk.setZt("已办结");
				}
			}
		}
		return list;
	}
}
