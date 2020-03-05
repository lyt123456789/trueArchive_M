package cn.com.trueway.ldbook.web;

public interface IReceiveCallback
{
	void OnThirdCountLoginSuccess(String szUserID, int type, String strReserved);
	void OnThirdCountLoginFailed(String szUserID, int errCode, String strErrMsg);
	void OnThirdCountSendMsgSuccess(String szSrcID, int type, String strReserved);
	void OnThirdCountSendMsgFailed(String szSrcID, int errCode, String strErrMsg);
	void OnThirdCountMsg(String szSrcID, int type, String msg);
	void OnHeartBeat(String szUserID, String szHeartMsg);
	void OnThirdSendDeleteDocumentSuccess(String szLoginID, String szSrcID, String szProcessID);
	void OnUserOnlineNotify1(String szUserID, Method.TerminalType terminalType, String szMacAddr);
	void OnUserOnlineList3(Method.UserLogInfo3List UserIDList);
	void OnUserOfflineNotify(String szUserID);
	void OnThirdChat(String szLogID, String szSrcID, String szDstID);
	void OnThirdExit(String szLogID, String szSrcID);
	void OnThirdFileMsgSuccess(String szLogID, String szMsgID);
	void OnThirdGroupFileMsgSuccess(String szLogID, String szUserID, String szGroupID, String szMsgID);
	void OnThirdGroupDataList(String szUserID, Method.GroupInfoList2 vGroupInfoList2);
}
