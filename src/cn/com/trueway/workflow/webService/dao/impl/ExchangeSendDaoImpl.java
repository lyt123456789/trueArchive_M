package cn.com.trueway.workflow.webService.dao.impl;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.pojo.DoFileReceive;
import cn.com.trueway.workflow.webService.dao.ExchangeSendDao;

public class ExchangeSendDaoImpl extends BaseDao implements ExchangeSendDao{
	
	@Override
	public void updateDoFileReceive(DoFileReceive doFileReceive) {
		String sql = "update T_WF_CORE_RECEIVE t set t.toExchangePath = '"+doFileReceive.getToExchangePath()
				+"' where t.id='"+doFileReceive.getId()+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

}
