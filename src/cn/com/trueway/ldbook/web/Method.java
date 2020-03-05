package cn.com.trueway.ldbook.web;
import java.util.Vector;

public class Method
{
	public static final int RequestID_ThirdCountLogin=1183578572;
	public static final int RequestID_ThirdCountSendMsg=890228860;
	public static final int RequestID_HeartBeat=123600583;
	public static final int RequestID_ThirdSendDeleteDocument=-414820928;
	public static final int RequestID_UserOnlineList=376404209;
	public static final int RequestID_ThirdChat=-662201590;
	public static final int RequestID_ThirdExit=-662126384;
	public static final int RequestID_OnThirdFileMsg=1826340274;
	public static final int RequestID_ThirdGroupDataList2=2130466621;
	public static final int RequestID_ThirdGroupFileMsg=506253976;
	public static final int RequestID_PushUserMessage=-2072038483;
	public static final int RequestID_PushFollowMessage=-1351377721;
	public static final int RequestID_RemoveFollowMessage=-1067683619;
	public static final int RequestID_SetSystemNoticeHasOpen=-1906425712;
	public static final int CallbackID_OnThirdCountLoginSuccess=1402682464;
	public static final int CallbackID_OnThirdCountLoginFailed=1040259136;
	public static final int CallbackID_OnThirdCountSendMsgSuccess=805941616;
	public static final int CallbackID_OnThirdCountSendMsgFailed=1852293424;
	public static final int CallbackID_OnThirdCountMsg=212212923;
	public static final int CallbackID_OnHeartBeat=484954206;
	public static final int CallbackID_OnThirdSendDeleteDocumentSuccess=-1895600916;
	public static final int CallbackID_OnUserOnlineNotify1=432377708;
	public static final int CallbackID_OnUserOnlineList3=-327833159;
	public static final int CallbackID_OnUserOfflineNotify=-2056590781;
	public static final int CallbackID_OnThirdChat=-300847967;
	public static final int CallbackID_OnThirdExit=-300772761;
	public static final int CallbackID_OnThirdFileMsgSuccess=1875027431;
	public static final int CallbackID_OnThirdGroupFileMsgSuccess=1531402196;
	public static final int CallbackID_OnThirdGroupDataList=1596597214;
	
	public static class StrList extends Vector {}
	
	public static void StrList_Write(InStream ins, StrList v)
	{
		ins.Write(v.size());
		for (int i=0; i<v.size(); i++)
		{
			ins.Write((String)v.get(i));
		}
	}
	public static void StrList_Read(OutStream os,  StrList v)
	{
		int count = os.ReadInt();
		for (int i = 0; i < count; i++)
		{
			v.add(os.ReadString());
		}
	}
	public static enum TerminalType
	{
		TerminalType_PC,
		TerminalType_Android,
		TerminalType_Iphone,
		TerminalType_UnKnown,
		TerminalType_Web,
		TerminalType_Work,
	}
	public static void TerminalType_Write(InStream ins, TerminalType v)
	{
		switch(v)
		{
		case TerminalType_PC:
				ins.Write((int)1344542428);
				break;
		case TerminalType_Android:
				ins.Write((int)1576889094);
				break;
		case TerminalType_Iphone:
				ins.Write((int)-687967442);
				break;
		case TerminalType_UnKnown:
				ins.Write((int)2123886785);
				break;
		case TerminalType_Web:
				ins.Write((int)-1268849813);
				break;
		case TerminalType_Work:
				ins.Write((int)-679628326);
				break;
		}
	}
	public static TerminalType TerminalType_Read(OutStream os)
	{
		TerminalType v = null;
		int e = os.ReadInt();
		switch(e)
		{
		case 1344542428:
				v = TerminalType.TerminalType_PC;
				break;
		case 1576889094:
				v = TerminalType.TerminalType_Android;
				break;
		case -687967442:
				v = TerminalType.TerminalType_Iphone;
				break;
		case 2123886785:
				v = TerminalType.TerminalType_UnKnown;
				break;
		case -1268849813:
				v = TerminalType.TerminalType_Web;
				break;
		case -679628326:
				v = TerminalType.TerminalType_Work;
				break;
		}
		return v;
	}
	public static enum MessageType
	{
		MessageType_Text,
		MessageType_Image,
		MessageType_Sound,
		MessageType_Mixed,
		MessageType_File,
		MessageType_NoticeText,
		MessageType_NoticeMixed,
		MessageType_MicroVideo,
		MessageType_Tip,
		MessageType_NoticeFile,
		MessageType_ThirdText,
	}
	public static void MessageType_Write(InStream ins, MessageType v)
	{
		switch(v)
		{
		case MessageType_Text:
				ins.Write((int)-1315712245);
				break;
		case MessageType_Image:
				ins.Write((int)-2142316739);
				break;
		case MessageType_Sound:
				ins.Write((int)-2133002511);
				break;
		case MessageType_Mixed:
				ins.Write((int)-2138719779);
				break;
		case MessageType_File:
				ins.Write((int)-1316125862);
				break;
		case MessageType_NoticeText:
				ins.Write((int)-633384061);
				break;
		case MessageType_NoticeMixed:
				ins.Write((int)1833584741);
				break;
		case MessageType_MicroVideo:
				ins.Write((int)1982836597);
				break;
		case MessageType_Tip:
				ins.Write((int)-873726211);
				break;
		case MessageType_NoticeFile:
				ins.Write((int)-633797678);
				break;
		case MessageType_ThirdText:
				ins.Write((int)-1811767274);
				break;
		}
	}
	public static MessageType MessageType_Read(OutStream os)
	{
		MessageType v = null;
		int e = os.ReadInt();
		switch(e)
		{
		case -1315712245:
				v = MessageType.MessageType_Text;
				break;
		case -2142316739:
				v = MessageType.MessageType_Image;
				break;
		case -2133002511:
				v = MessageType.MessageType_Sound;
				break;
		case -2138719779:
				v = MessageType.MessageType_Mixed;
				break;
		case -1316125862:
				v = MessageType.MessageType_File;
				break;
		case -633384061:
				v = MessageType.MessageType_NoticeText;
				break;
		case 1833584741:
				v = MessageType.MessageType_NoticeMixed;
				break;
		case 1982836597:
				v = MessageType.MessageType_MicroVideo;
				break;
		case -873726211:
				v = MessageType.MessageType_Tip;
				break;
		case -633797678:
				v = MessageType.MessageType_NoticeFile;
				break;
		case -1811767274:
				v = MessageType.MessageType_ThirdText;
				break;
		}
		return v;
	}
	public static class FontInfo
	{
		public String szFontType;
		public int fontSize;
		public int fontColor;
		public boolean bUnderLine;
		public boolean bBold;
		public boolean bItalic;
	}
	public static void FontInfo_Write(InStream ins, FontInfo v)
	{
		ins.Write(v.szFontType);
		ins.Write(v.fontSize);
		ins.Write(v.fontColor);
		ins.Write(v.bUnderLine);
		ins.Write(v.bBold);
		ins.Write(v.bItalic);
	}
	public static void FontInfo_Read(OutStream os, FontInfo v)
	{
		v.szFontType = os.ReadString();
		v.fontSize = os.ReadInt();
		v.fontColor = os.ReadInt();
		v.bUnderLine = os.ReadBool();
		v.bBold = os.ReadBool();
		v.bItalic = os.ReadBool();
	}
	public static class UserLogInfo3
	{
		public String szUserID;
		public TerminalType terminalType;
		public String szMac;
	}
	public static void UserLogInfo3_Write(InStream ins, UserLogInfo3 v)
	{
		ins.Write(v.szUserID);
		TerminalType_Write(ins, v.terminalType);
		ins.Write(v.szMac);
	}
	public static void UserLogInfo3_Read(OutStream os, UserLogInfo3 v)
	{
		v.szUserID = os.ReadString();
		v.terminalType = TerminalType_Read(os);
		v.szMac = os.ReadString();
	}
	public static class GroupInfo2
	{
		public String szGroupID;
		public String szCreaterID;
		public String szGroupName;
		public StrList szGroupUserList;
		public StrList szAdminList;
		public int iGroupState;
		public int iGroupOfficial;
		public String szGroupVersion;
		public String szGroupHeadPic;
		public int iGroupDisturbState;
	}
	public static void GroupInfo2_Write(InStream ins, GroupInfo2 v)
	{
		ins.Write(v.szGroupID);
		ins.Write(v.szCreaterID);
		ins.Write(v.szGroupName);
		StrList_Write(ins, v.szGroupUserList);
		StrList_Write(ins, v.szAdminList);
		ins.Write(v.iGroupState);
		ins.Write(v.iGroupOfficial);
		ins.Write(v.szGroupVersion);
		ins.Write(v.szGroupHeadPic);
		ins.Write(v.iGroupDisturbState);
	}
	public static void GroupInfo2_Read(OutStream os, GroupInfo2 v)
	{
		v.szGroupID = os.ReadString();
		v.szCreaterID = os.ReadString();
		v.szGroupName = os.ReadString();
		v.szGroupUserList = new StrList();
		StrList_Read(os, v.szGroupUserList);
		v.szAdminList = new StrList();
		StrList_Read(os, v.szAdminList);
		v.iGroupState = os.ReadInt();
		v.iGroupOfficial = os.ReadInt();
		v.szGroupVersion = os.ReadString();
		v.szGroupHeadPic = os.ReadString();
		v.iGroupDisturbState = os.ReadInt();
	}
	public static class UserLogInfo3List extends Vector {}
	
	public static void UserLogInfo3List_Write(InStream ins, UserLogInfo3List v)
	{
		ins.Write(v.size());
		for (int i=0; i<v.size(); i++)
		{
			UserLogInfo3_Write(ins,(UserLogInfo3)v.get(i));
		}
	}
	public static void UserLogInfo3List_Read(OutStream os,  UserLogInfo3List v)
	{
		int count = os.ReadInt();
		for (int i = 0; i < count; i++)
		{
			UserLogInfo3 m = new UserLogInfo3();
			UserLogInfo3_Read(os, m);
			v.add((Object)m);
		}
	}
	public static class GroupInfoList2 extends Vector {}
	
	public static void GroupInfoList2_Write(InStream ins, GroupInfoList2 v)
	{
		ins.Write(v.size());
		for (int i=0; i<v.size(); i++)
		{
			GroupInfo2_Write(ins,(GroupInfo2)v.get(i));
		}
	}
	public static void GroupInfoList2_Read(OutStream os,  GroupInfoList2 v)
	{
		int count = os.ReadInt();
		for (int i = 0; i < count; i++)
		{
			GroupInfo2 m = new GroupInfo2();
			GroupInfo2_Read(os, m);
			v.add((Object)m);
		}
	}
}
