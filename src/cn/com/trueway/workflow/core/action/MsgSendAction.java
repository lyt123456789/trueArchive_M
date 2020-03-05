package cn.com.trueway.workflow.core.action;

import java.util.List;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.ldbook.util.RemoteLogin;
import cn.com.trueway.ldbook.web.Method.StrList;
import cn.com.trueway.workflow.core.service.MsgSendService;

public class MsgSendAction extends BaseAction{
	private static final long serialVersionUID = 235340112769831944L;
	private MsgSendService msgSendService;
	
	public MsgSendService getMsgSendService() {
		return msgSendService;
	}



	public void setMsgSendService(MsgSendService msgSendService) {
		this.msgSendService = msgSendService;
	}


	/**
	 * 
	 * 描述：获取出发送失败
	 * 作者:蔡亚军
	 * 创建时间:2014-8-4 下午6:25:26
	 */
	public void msgSend(){
		//获取判断连接
		RemoteLogin remote = new RemoteLogin();
		//获取发送失败的发送次数少于5的
		List<MsgSend> list = msgSendService.getMsgSendList();
		StrList v = null;
		MsgSend msgSend = null;
		for(int i=0; i<list.size(); i++){
			v = new StrList();
			msgSend = list.get(i);
			String sendName =  msgSend.getSendUserId();
			v.add(msgSend.getRecUserId());
			remote.sendPendingMsg(v, sendName, msgSend.getContent());
			/*//更新状态
			msgSend.setStatus(1);
			msgSendService.updateMsgSend(msgSend);*/
		}
	}

}
