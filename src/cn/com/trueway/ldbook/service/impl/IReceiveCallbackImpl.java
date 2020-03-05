package cn.com.trueway.ldbook.service.impl;

import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.trueway.ldbook.listener.MsgSendTimer;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.ldbook.util.RemoteLogin;
import cn.com.trueway.ldbook.web.IReceiveCallback;
import cn.com.trueway.ldbook.web.Method.GroupInfo2;
import cn.com.trueway.ldbook.web.Method.GroupInfoList2;
import cn.com.trueway.ldbook.web.Method.TerminalType;
import cn.com.trueway.ldbook.web.Method.UserLogInfo3;
import cn.com.trueway.ldbook.web.Method.UserLogInfo3List;
import cn.com.trueway.workflow.core.service.MsgSendService;

public class IReceiveCallbackImpl implements IReceiveCallback{
	
	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	public void OnThirdCountLoginSuccess(String szUserID, int type,
			String strReserved) {
		System.out.println("登录成功！");
		MsgSendTimer.isConneted = true;
		RemoteLogin.checkPassed = true;
		//maybe应该去初始化人员
		RemoteLogin rem = new RemoteLogin(RemoteLogin.loginName);
		rem.getOnlineList();
	}

	@Override
	public void OnThirdCountLoginFailed(String szUserID, int type,
			String strReserved) {
		RemoteLogin.checkPassed = false;
		System.out.println("登录失败！");
	}

	@Override
	public void OnThirdCountSendMsgSuccess(String szSrcID, int type,
			String strReserved) {
 		String json  = strReserved;
		if(json==null || json.equals("")){
			//System.out.println("无任何发送信息!");
		}
		org.json.JSONObject jsonObject = null;
		try {
			json = json.replace("\r\n", "").replaceAll("'(.*?)'", "‘$1’");
			json = json.replace("'", "’");
			jsonObject = new org.json.JSONObject(json);
			String processId = jsonObject.get("processId")==null?"":jsonObject.get("processId").toString();
			//更新状态位
			MsgSendService msgSendService = MsgSendTimer.msgSendService;
			if(msgSendService==null){			//没有msgSendService
				if(MsgSendTimer.ctx==null){
					MsgSendTimer.ctx = new ClassPathXmlApplicationContext(new String[]{"classpath*:spring/common/*.xml","classpath*:spring/workflow/msgsend/*.xml"});
				}
				MsgSendTimer.msgSendService = (MsgSendService) MsgSendTimer.ctx.getBean("msgSendService");
				msgSendService = MsgSendTimer.msgSendService;
			}
			if(processId!=null && !processId.equals("")){
				MsgSend msgSend = msgSendService.findMsgSendByProcessId(processId);
				if(msgSend!=null){
					msgSend.setStatus(1);
					msgSendService.updateMsgSend(msgSend);
				}
			}
			logger.warn("消息发送成功！");
		} catch (JSONException e) {
			logger.debug(e.getMessage());
		}
	}

	@Override
	public void OnThirdCountSendMsgFailed(String szSrcID, int errCode,
			String strErrMsg) {
		String json  = strErrMsg;
		if(json==null || json.equals("")){
			//System.out.println("无任何发送信息!");
		}
		org.json.JSONObject jsonObject = null;
		try {
			jsonObject = new org.json.JSONObject(json);
			//String processId = jsonObject.get("processId")==null?"":jsonObject.get("processId").toString();
			String processId = jsonObject.get("processId")==null?"":jsonObject.get("processId").toString();
			//更新状态位
			MsgSendService msgSendService = MsgSendTimer.msgSendService;
			MsgSend msgSend = msgSendService.findMsgSendByProcessId(processId);
			msgSend.setStatus(0);
			msgSendService.updateMsgSend(msgSend);
			logger.warn("消息发送失败！");
		} catch (JSONException e) {
			//e.printStackTrace();
			logger.debug(e.getMessage());
		}
	}

	@Override
	public void OnThirdCountMsg(String szSrcID, int type, String msg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void OnHeartBeat(String szUserID, String szHeartMsg) {
		MsgSendTimer.isConneted = true;
		MsgSendTimer.isHeart = true;
		System.out.println("OnHeartBeat连接通畅!");
	}

	@Override
	public void OnThirdSendDeleteDocumentSuccess(String szLoginID,
			String szSrcID, String szProcessID) {
		logger.warn("通知中威通讯删除消息成功！用户id="+szSrcID+"; 待办processId="+szProcessID);
	}

	@Override
	public void OnUserOnlineList3(UserLogInfo3List UserIDList) {
		try{
			Enumeration enu = UserIDList.elements();
			while(enu.hasMoreElements()){
				UserLogInfo3 v = (UserLogInfo3)enu.nextElement();
				String userId = v.szUserID;		//userId
				String mac = v.szMac;		//mac地址
				if(RemoteLogin.map.get(userId)==null){
					RemoteLogin.map.put(userId, mac);
				}
			}
		}catch (Exception e) {
			logger.error("获取当前用户在线列表失败！");
			//e.printStackTrace();
		}
	}

	@Override
	public void OnUserOnlineNotify1(String szUserID, TerminalType terminalType, String szMacAddr) {	//上线提醒
		if(RemoteLogin.map.get(szUserID)!=null){
			RemoteLogin.map.remove(szUserID);
			RemoteLogin.map.put(szUserID, szMacAddr);
			logger.warn("用户"+szUserID+"上线！");
		}else{
			RemoteLogin.map.put(szUserID, szMacAddr);
			logger.warn("用户"+szUserID+"上线！");
		}
	}

	@Override
	public void OnUserOfflineNotify(String szUserID) {
		if(RemoteLogin.map.get(szUserID)!=null){
			RemoteLogin.map.remove(szUserID);
			logger.warn("用户"+szUserID+"下线！");
		}
	}

	@Override
	public void OnThirdChat(String szLogID, String szSrcID, String szDstID) {
		logger.warn("开启中威通讯录聊天成功！");
	}

	@Override
	public void OnThirdExit(String szLogID, String szSrcID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnThirdFileMsgSuccess(String szLogID, String szMsgID) {
		System.out.println("文件发送成功！");
		
	}

	@Override
	public void OnThirdGroupDataList(String szUserID,
			GroupInfoList2 vGroupInfoList2) {
		Enumeration enu = vGroupInfoList2.elements();
		String groupInfo = "";
		while(enu.hasMoreElements()){
			GroupInfo2 v = (GroupInfo2)enu.nextElement();
			String groupId = v.szGroupID;		//userId
			String groupName = v.szGroupName;		//mac地址
			groupInfo+= groupId+","+groupName+";";
		}
		if(groupInfo!=null && !groupInfo.equals("")){
			groupInfo = groupInfo.substring(0, groupInfo.length()-1);
		}
		RemoteLogin.groupMap.put(szUserID, groupInfo);
		System.out.println("------ groupInfo--------"+groupInfo);
		
	}

	@Override
	public void OnThirdGroupFileMsgSuccess(String szLogID, String szUserID,
			String szGroupID, String szMsgID) {
		System.out.println("1222");
	}

}
