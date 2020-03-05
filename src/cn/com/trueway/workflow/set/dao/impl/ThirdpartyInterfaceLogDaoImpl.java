package cn.com.trueway.workflow.set.dao.impl;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.set.dao.ThirdpartyInterfaceLogDao;
import cn.com.trueway.workflow.set.pojo.ThirdpartyInterfaceLog;

public class ThirdpartyInterfaceLogDaoImpl extends BaseDao implements ThirdpartyInterfaceLogDao {

	@Override
	public ThirdpartyInterfaceLog insert(ThirdpartyInterfaceLog entity) {
		if(null == entity){
			return null;
		}
		getEm().persist(entity);
		return entity;
	}

	@Override
	public void update(ThirdpartyInterfaceLog entity) {
		if(null == entity){
			return;
		}
		getEm().merge(entity);
	}

}
