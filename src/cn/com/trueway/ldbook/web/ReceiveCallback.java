package cn.com.trueway.ldbook.web;

public class ReceiveCallback
{
	static void _ParseOnThirdCountLoginSuccess(IReceiveCallback receiver, OutStream os)
	{
		String szUserID = os.ReadString();
		int type = os.ReadInt();
		String strReserved = os.ReadString();
		receiver.OnThirdCountLoginSuccess(szUserID, type, strReserved);
	}
	static void _ParseOnThirdCountLoginFailed(IReceiveCallback receiver, OutStream os)
	{
		String szUserID = os.ReadString();
		int errCode = os.ReadInt();
		String strErrMsg = os.ReadString();
		receiver.OnThirdCountLoginFailed(szUserID, errCode, strErrMsg);
	}
	static void _ParseOnThirdCountSendMsgSuccess(IReceiveCallback receiver, OutStream os)
	{
		String szSrcID = os.ReadString();
		int type = os.ReadInt();
		String strReserved = os.ReadString();
		receiver.OnThirdCountSendMsgSuccess(szSrcID, type, strReserved);
	}
	static void _ParseOnThirdCountSendMsgFailed(IReceiveCallback receiver, OutStream os)
	{
		String szSrcID = os.ReadString();
		int errCode = os.ReadInt();
		String strErrMsg = os.ReadString();
		receiver.OnThirdCountSendMsgFailed(szSrcID, errCode, strErrMsg);
	}
	static void _ParseOnThirdCountMsg(IReceiveCallback receiver, OutStream os)
	{
		String szSrcID = os.ReadString();
		int type = os.ReadInt();
		String msg = os.ReadString();
		receiver.OnThirdCountMsg(szSrcID, type, msg);
	}
	static void _ParseOnHeartBeat(IReceiveCallback receiver, OutStream os)
	{
		String szUserID = os.ReadString();
		String szHeartMsg = os.ReadString();
		receiver.OnHeartBeat(szUserID, szHeartMsg);
	}
	static void _ParseOnThirdSendDeleteDocumentSuccess(IReceiveCallback receiver, OutStream os)
	{
		String szLoginID = os.ReadString();
		String szSrcID = os.ReadString();
		String szProcessID = os.ReadString();
		receiver.OnThirdSendDeleteDocumentSuccess(szLoginID, szSrcID, szProcessID);
	}
	static void _ParseOnUserOnlineNotify1(IReceiveCallback receiver, OutStream os)
	{
		String szUserID = os.ReadString();
		Method.TerminalType terminalType = Method.TerminalType_Read(os);
		String szMacAddr = os.ReadString();
		receiver.OnUserOnlineNotify1(szUserID, terminalType, szMacAddr);
	}
	static void _ParseOnUserOnlineList3(IReceiveCallback receiver, OutStream os)
	{
		Method.UserLogInfo3List UserIDList = new Method.UserLogInfo3List();
		Method.UserLogInfo3List_Read(os,UserIDList);
		receiver.OnUserOnlineList3(UserIDList);
	}
	static void _ParseOnUserOfflineNotify(IReceiveCallback receiver, OutStream os)
	{
		String szUserID = os.ReadString();
		receiver.OnUserOfflineNotify(szUserID);
	}
	static void _ParseOnThirdChat(IReceiveCallback receiver, OutStream os)
	{
		String szLogID = os.ReadString();
		String szSrcID = os.ReadString();
		String szDstID = os.ReadString();
		receiver.OnThirdChat(szLogID, szSrcID, szDstID);
	}
	static void _ParseOnThirdExit(IReceiveCallback receiver, OutStream os)
	{
		String szLogID = os.ReadString();
		String szSrcID = os.ReadString();
		receiver.OnThirdExit(szLogID, szSrcID);
	}
	static void _ParseOnThirdFileMsgSuccess(IReceiveCallback receiver, OutStream os)
	{
		String szLogID = os.ReadString();
		String szMsgID = os.ReadString();
		receiver.OnThirdFileMsgSuccess(szLogID, szMsgID);
	}
	static void _ParseOnThirdGroupFileMsgSuccess(IReceiveCallback receiver, OutStream os)
	{
		String szLogID = os.ReadString();
		String szUserID = os.ReadString();
		String szGroupID = os.ReadString();
		String szMsgID = os.ReadString();
		receiver.OnThirdGroupFileMsgSuccess(szLogID, szUserID, szGroupID, szMsgID);
	}
	static void _ParseOnThirdGroupDataList(IReceiveCallback receiver, OutStream os)
	{
		String szUserID = os.ReadString();
		Method.GroupInfoList2 vGroupInfoList2 = new Method.GroupInfoList2();
		Method.GroupInfoList2_Read(os,vGroupInfoList2);
		receiver.OnThirdGroupDataList(szUserID, vGroupInfoList2);
	}
	public static Boolean Parse(IReceiveCallback receiver, byte[] stream)
	{
		OutStream os = new OutStream(stream);
		int method = os.ReadInt();
		switch(method)
		{
		case Method.CallbackID_OnThirdCountLoginSuccess:
				_ParseOnThirdCountLoginSuccess(receiver, os);
				return true;
		case Method.CallbackID_OnThirdCountLoginFailed:
				_ParseOnThirdCountLoginFailed(receiver, os);
				return true;
		case Method.CallbackID_OnThirdCountSendMsgSuccess:
				_ParseOnThirdCountSendMsgSuccess(receiver, os);
				return true;
		case Method.CallbackID_OnThirdCountSendMsgFailed:
				_ParseOnThirdCountSendMsgFailed(receiver, os);
				return true;
		case Method.CallbackID_OnThirdCountMsg:
				_ParseOnThirdCountMsg(receiver, os);
				return true;
		case Method.CallbackID_OnHeartBeat:
				_ParseOnHeartBeat(receiver, os);
				return true;
		case Method.CallbackID_OnThirdSendDeleteDocumentSuccess:
				_ParseOnThirdSendDeleteDocumentSuccess(receiver, os);
				return true;
		case Method.CallbackID_OnUserOnlineNotify1:
				_ParseOnUserOnlineNotify1(receiver, os);
				return true;
		case Method.CallbackID_OnUserOnlineList3:
				_ParseOnUserOnlineList3(receiver, os);
				return true;
		case Method.CallbackID_OnUserOfflineNotify:
				_ParseOnUserOfflineNotify(receiver, os);
				return true;
		case Method.CallbackID_OnThirdChat:
				_ParseOnThirdChat(receiver, os);
				return true;
		case Method.CallbackID_OnThirdExit:
				_ParseOnThirdExit(receiver, os);
				return true;
		case Method.CallbackID_OnThirdFileMsgSuccess:
				_ParseOnThirdFileMsgSuccess(receiver, os);
				return true;
		case Method.CallbackID_OnThirdGroupFileMsgSuccess:
				_ParseOnThirdGroupFileMsgSuccess(receiver, os);
				return true;
		case Method.CallbackID_OnThirdGroupDataList:
				_ParseOnThirdGroupDataList(receiver, os);
				return true;
		}
		return false;
	}
}
