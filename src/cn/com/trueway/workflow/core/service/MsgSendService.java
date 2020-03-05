package cn.com.trueway.workflow.core.service;

import java.util.List;

import cn.com.trueway.ldbook.pojo.MsgSend;

public interface MsgSendService {
	
	public List<MsgSend> getMsgSendList();
	
	public MsgSend findMsgSendByProcessId(String id);
	
	public void updateMsgSend(MsgSend msgSend);

}
