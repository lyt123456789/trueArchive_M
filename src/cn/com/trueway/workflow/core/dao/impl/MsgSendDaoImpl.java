package cn.com.trueway.workflow.core.dao.impl;

import java.util.List;


import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.workflow.core.dao.MsgSendDao;

public class MsgSendDaoImpl extends BaseDao implements MsgSendDao{
	
	@Override
	public List<MsgSend> getMsgSendList() {
		String sql = "select t.* from t_wf_core_msg_send t where t.status=0 order by t.sendDate desc";
		return getEm().createNativeQuery(sql,MsgSend.class).getResultList();
	}

	@Override
	public void updateMsgSend(MsgSend msgSend) {
		String sql = "update t_wf_core_msg_send t set t.status = 1 where t.id='"+msgSend.getId()+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public MsgSend findMsgSendByProcessId(String id) {
		String sql = "select t.* from t_wf_core_msg_send t where t.status=2 and t.processId='"+id+"'";
		List<MsgSend>  list  = getEm().createNativeQuery(sql,MsgSend.class).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
