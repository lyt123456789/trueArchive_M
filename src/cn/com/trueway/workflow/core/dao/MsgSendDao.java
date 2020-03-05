package cn.com.trueway.workflow.core.dao;

import java.util.List;

import cn.com.trueway.ldbook.pojo.MsgSend;

public interface MsgSendDao {
	
	public List<MsgSend> getMsgSendList();
	
	public MsgSend findMsgSendByProcessId(String id);
	
	public void updateMsgSend(MsgSend msgSend);

}
