package cn.com.trueway.ldbook.web;

public class SendRequest
{
	public static byte[] ThirdCountLogin(String szUserID, int type, String strReserved)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_ThirdCountLogin);
		ins.Write(szUserID);
		ins.Write(type);
		ins.Write(strReserved);
		return ins.GetByteStream();
	}
	public static byte[] ThirdCountSendMsg(String szLoginID, String szSrcID, Method.StrList szDstIDList, int type, String szMsg)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_ThirdCountSendMsg);
		ins.Write(szLoginID);
		ins.Write(szSrcID);
		Method.StrList_Write(ins,szDstIDList);
		ins.Write(type);
		ins.Write(szMsg);
		return ins.GetByteStream();
	}
	public static byte[] HeartBeat(String szUserID, String szHeartMsg)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_HeartBeat);
		ins.Write(szUserID);
		ins.Write(szHeartMsg);
		return ins.GetByteStream();
	}
	public static byte[] ThirdSendDeleteDocument(String szLoginID, String szSrcID, String szProcessID)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_ThirdSendDeleteDocument);
		ins.Write(szLoginID);
		ins.Write(szSrcID);
		ins.Write(szProcessID);
		return ins.GetByteStream();
	}
	public static byte[] UserOnlineList(String szUserID, Method.TerminalType terminalType)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_UserOnlineList);
		ins.Write(szUserID);
		Method.TerminalType_Write(ins,terminalType);
		return ins.GetByteStream();
	}
	public static byte[] ThirdChat(String szLogID, String szSrcID, String szDstID)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_ThirdChat);
		ins.Write(szLogID);
		ins.Write(szSrcID);
		ins.Write(szDstID);
		return ins.GetByteStream();
	}
	public static byte[] ThirdExit(String szLogID, String szSrcID)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_ThirdExit);
		ins.Write(szLogID);
		ins.Write(szSrcID);
		return ins.GetByteStream();
	}
	public static byte[] OnThirdFileMsg(String szLogID, String szSrcUserID, String szSrcUserName, String szText, Method.StrList szChatedUserList, String szMsgID)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_OnThirdFileMsg);
		ins.Write(szLogID);
		ins.Write(szSrcUserID);
		ins.Write(szSrcUserName);
		ins.Write(szText);
		Method.StrList_Write(ins,szChatedUserList);
		ins.Write(szMsgID);
		return ins.GetByteStream();
	}
	public static byte[] ThirdGroupDataList2(String szLogID, String szUserID)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_ThirdGroupDataList2);
		ins.Write(szLogID);
		ins.Write(szUserID);
		return ins.GetByteStream();
	}
	public static byte[] ThirdGroupFileMsg(String szLogID, String szDstGroupID, String szDstGroupName, String szSrcUserID, String szSrcUserName, String szText, String szMsgID)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_ThirdGroupFileMsg);
		ins.Write(szLogID);
		ins.Write(szDstGroupID);
		ins.Write(szDstGroupName);
		ins.Write(szSrcUserID);
		ins.Write(szSrcUserName);
		ins.Write(szText);
		ins.Write(szMsgID);
		return ins.GetByteStream();
	}
	public static byte[] PushUserMessage(String szDstUserID, String szSrcUserID, String szSrcUserName, String szText, String szPushProjectID)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_PushUserMessage);
		ins.Write(szDstUserID);
		ins.Write(szSrcUserID);
		ins.Write(szSrcUserName);
		ins.Write(szText);
		ins.Write(szPushProjectID);
		return ins.GetByteStream();
	}
	public static byte[] PushFollowMessage(String szSrcUserID, String szSrcUserName, String szText, String szFollowMsgID)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_PushFollowMessage);
		ins.Write(szSrcUserID);
		ins.Write(szSrcUserName);
		ins.Write(szText);
		ins.Write(szFollowMsgID);
		return ins.GetByteStream();
	}
	public static byte[] RemoveFollowMessage(String szSrcUserID, String szSrcUserName, String szFollowMsgID)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_RemoveFollowMessage);
		ins.Write(szSrcUserID);
		ins.Write(szSrcUserName);
		ins.Write(szFollowMsgID);
		return ins.GetByteStream();
	}
	public static byte[] SetSystemNoticeHasOpen(String szLogID, String szUserID, String szSystemNoticeID, int iState)
	{
		InStream ins = new InStream();
		ins.Write(Method.RequestID_SetSystemNoticeHasOpen);
		ins.Write(szLogID);
		ins.Write(szUserID);
		ins.Write(szSystemNoticeID);
		ins.Write(iState);
		return ins.GetByteStream();
	}
}
