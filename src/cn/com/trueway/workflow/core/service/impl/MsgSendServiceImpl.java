package cn.com.trueway.workflow.core.service.impl;

import java.util.List;

import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.workflow.core.dao.MsgSendDao;
import cn.com.trueway.workflow.core.service.MsgSendService;

public class MsgSendServiceImpl implements MsgSendService{
	
	private MsgSendDao msgSendDao;
	
	public MsgSendDao getMsgSendDao() {
		return msgSendDao;
	}

	public void setMsgSendDao(MsgSendDao msgSendDao) {
		this.msgSendDao = msgSendDao;
	}

	@Override
	public List<MsgSend> getMsgSendList() {
		return msgSendDao.getMsgSendList();
	}

	@Override
	public void updateMsgSend(MsgSend msgSend) {
		 msgSendDao.updateMsgSend(msgSend);
	}

	@Override
	public MsgSend findMsgSendByProcessId(String id) {
		return msgSendDao.findMsgSendByProcessId(id);
	}

}
