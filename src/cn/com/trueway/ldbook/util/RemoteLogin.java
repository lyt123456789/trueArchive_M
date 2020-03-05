package cn.com.trueway.ldbook.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.com.trueway.base.util.MySqlJDBCUtil;
import cn.com.trueway.ldbook.service.SocketListener;
import cn.com.trueway.ldbook.service.impl.IReceiveCallbackImpl;
import cn.com.trueway.ldbook.web.BotConnection;
import cn.com.trueway.ldbook.web.IReceiveCallback;
import cn.com.trueway.ldbook.web.Method.StrList;
import cn.com.trueway.ldbook.web.Method.TerminalType;
import cn.com.trueway.ldbook.web.SendRequest;
import cn.com.trueway.sys.util.SystemParamConfigUtil;

/**
 * 
 * 描述：远程登录验证
 * 作者：蔡亚军
 * 创建时间：2014-7-11 下午5:49:11
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class RemoteLogin  implements SocketListener {
	
	public static BotConnection botConnection;
	
	public static boolean checkPassed;
	
	public String userId;
	
	public static int status;
	
	public static String loginName = "";
	
	public static Map<String,String> map = new HashMap<String,String>();
	
	public static Map<String,String> groupMap = new HashMap<String,String>();
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public RemoteLogin(String userId){
		this.userId = userId;
	}
	
	public RemoteLogin (){
		
	}
	/**
	 * 
	 * 描述：验证用户,创建socket连接
	 * 作者:蔡亚军
	 * 创建时间:2015-3-13 下午3:24:32
	 */
	public void checkUser(){
		String userName = SystemParamConfigUtil.getParamValueByParam("userName");
		RemoteLogin.loginName = userName;
		this.userId = userName;
		RemoteLogin remoteLogin = new RemoteLogin(userId);
		IReceiveCallback iReceiveCallback = new IReceiveCallbackImpl();
		botConnection = new BotConnection();
		//获取sys中的ip以及端口号,如果不存在,则默认一个ip以及端口号
		String hostname = SystemParamConfigUtil.getParamValueByParam("socketIp");
		if(hostname==null || hostname.equals("")){
			hostname =  "192.168.0.20";
		}
		String socketPort = SystemParamConfigUtil.getParamValueByParam("socketPort");
		int port = 10003;
		
		if(socketPort!=null && !socketPort.equals("")){
			port = Integer.parseInt(socketPort);
		}
//		System.out.println("socketIp:"+hostname+","+"socketPort:"+port);
		botConnection.connect(hostname, port, iReceiveCallback, remoteLogin);
	}

	public static void main(String[] args) {
		RemoteLogin login = new RemoteLogin();
		login.checkUser();
	}
	@Override
	public void finishConnect() {
		
	}

	@Override
	public void reConnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disConnectIM() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectSuccess() {
		String szUserID = userId;
		byte[] byt = SendRequest.ThirdCountLogin(szUserID, 5, "234345445454");
		botConnection.sendMessage(byt);
	}
	
	public void sendPendingMsg(StrList v, String msg){
		String szUserID = userId;
		byte[] byt =  SendRequest.ThirdCountSendMsg(loginName, szUserID,v, 1, msg);
		botConnection.sendMessage(byt);
	}
	
	
	public void sendPendingMsg(StrList v,  String szUserID, String msg){
		logger.warn("--------发送消息----toUserId数目=------"+v.size());
		byte[] byt =  SendRequest.ThirdCountSendMsg(loginName, szUserID, v, 1, msg);
		botConnection.sendMessage(byt);
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param userId  		//推送人的电话
	 * @param userName		//推送人的姓名
	 * @param szText		//推送内容
	 * @param sendUserId	//发送目标的电话
	 * @param szMsgID void	//
	 * 作者:蒋烽
	 * 创建时间:2017-4-11 下午1:38:23
	 */
	public void SendUsersMessage(String userId, String userName, String obj_str, String sendUserId,  String szMsgID){
		String isUseMobile = SystemParamConfigUtil.getParamValueByParam("isUseMobile");//是否需要匹配通讯的人员id，如果没有配或者值不等于1，则不需要匹配，否则要匹配
		logger.warn("--------发送待办提醒到中威通讯录-----"+obj_str);
		//if(null!=userId && !(userId.equals(sendUserId))){
			String szObjectID = "zwoa";
			byte[] byt = null;
			if(StringUtils.isNotBlank(isUseMobile) && isUseMobile.equals("1")){
				System.out.println("-------------------4---------------userId:"+phoneToZwComUserId(userId)+"---SendUserId:"+phoneToZwComUserId(sendUserId));
				byt = SendRequest.PushUserMessage(phoneToZwComUserId(sendUserId), phoneToZwComUserId(userId), userName, obj_str, szObjectID);
			}else{
				System.out.println("-------------------4---------------userId:"+userId+"---SendUserId:"+sendUserId);
				byt = SendRequest.PushUserMessage(sendUserId, userId, userName, obj_str, szObjectID);
			}
			botConnection.sendMessage(byt);
		//}
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param userId  		//推送人的电话
	 * @param userName		//推送人的姓名
	 * @param szText		//推送内容
	 * @param sendUserId	//发送目标的电话
	 * @param szMsgID void	//
	 * 作者:蒋烽
	 * 创建时间:2017-4-11 下午1:38:23
	 */
	public void SendUsersMessage(String userId, String szText, String sendUserId,  String szMsgID){
		logger.warn("--------发送待办提醒到中威通讯录-----"+szText);
		String isUseMobile = SystemParamConfigUtil.getParamValueByParam("isUseMobile");//是否需要匹配通讯的人员id，如果没有配或者值不等于1，则不需要匹配，否则要匹配
		//if(null!=userId && !(userId.equals(sendUserId))){
			byte[] byt = null;
			if(StringUtils.isNotBlank(isUseMobile) && isUseMobile.equals("1")){
				System.out.println("-------------------3---------------userId:"+phoneToZwComUserId(userId)+"---SendUserId:"+phoneToZwComUserId(sendUserId));
				byt = SendRequest.PushFollowMessage(phoneToZwComUserId(sendUserId), phoneToZwComUserId(userId),  szText, szMsgID);
			}else{
				System.out.println("-------------------3---------------userId:"+userId+"---SendUserId:"+sendUserId);
				byt = SendRequest.PushFollowMessage(sendUserId, userId, szText, szMsgID);
			}
			botConnection.sendMessage(byt);
		//}
	}
	
	/**
	 * 
	 * 描述：通知中威通讯录删除对应的消息
	 * @param szLoginID
	 * @param szSrcID		//需要被删除的人员id
	 * @param szProcessID void
	 * 作者:蔡亚军
	 * 创建时间:2015-3-4 下午2:42:54
	 */
	public void deleteThirdPend(String szSrcID, String szProcessID){
		logger.warn("--------通知中威通讯录删除对应的待办信息----szSrcID=------"+szSrcID+";processId:"+szProcessID);
		byte[] byt =  SendRequest.ThirdSendDeleteDocument(loginName, szSrcID, szProcessID);
		botConnection.sendMessage(byt);
	}
	
	/**
	 * 
	 * 描述：获取人员信息
	 * 作者:蔡亚军
	 * 创建时间:2015-3-12 下午4:28:55
	 */
	public void getOnlineList(){
		logger.warn("--------获取在线人员-----");
		byte[] byt =  SendRequest.UserOnlineList(loginName, TerminalType.TerminalType_Web);
		botConnection.sendMessage(byt);
	}
	
	
	public void toThirdChat(String ownerId, String toUserId){
		logger.warn("--------开启中威通讯录聊天-----");
		byte[] byt =  SendRequest.ThirdChat(loginName, ownerId, toUserId);
		botConnection.sendMessage(byt);
	}
	
	public void OnThirdFileMsg(String userId, String userName, String szText, StrList v,  String szMsgID){
		logger.warn("--------转发文件到中威通讯录服务器-----");
		byte[] byt =  SendRequest.OnThirdFileMsg(loginName, userId, userName, szText, v, szMsgID);
		botConnection.sendMessage(byt);
	}
	
	
	public void GroupDataList2(String userId){
		logger.warn("--------获取人员信息群组-----");
		byte[] byt =  SendRequest.ThirdGroupDataList2(loginName, userId);
		botConnection.sendMessage(byt);
	}
	
	public void ThirdGroupFileMsg(String groupId, String groupName, String userId, String username,
				String szText, String szMsgID){
		logger.warn("--------将文件发送到共享文件夹中-----");
		byte[] byt =  SendRequest.ThirdGroupFileMsg(loginName, groupId, groupName, userId, username, szText, szMsgID);
		botConnection.sendMessage(byt);
	}
	
	/**
	 * 
	 * 描述：关闭socket
	 * 作者:蔡亚军
	 * 创建时间:2014-7-14 上午10:53:14
	 */
	public void choseSocket(){
		botConnection.close();
	}
	
	public void checkHeart(){
		byte[] byt =  SendRequest.HeartBeat(userId, "");
		botConnection.sendMessage(byt);
	}
	
	public String phoneToZwComUserId(String phone){
		Connection conn = (Connection)MySqlJDBCUtil.getConnection();
		String sql = "select t.uuid from t_user t where t.user_username=?";
		String uuid = "";
		ResultSet rs=null;
		PreparedStatement ps=null;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, phone);
			rs=ps.executeQuery();
			if(rs.next()){
				uuid=rs.getString("uuid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MySqlJDBCUtil.closeJDBC(conn, ps, rs);
		}
		return uuid;
	}
	
	public void OnThirdFileMsgOfPhone(String userId, String userName, String szText, StrList v,  String szMsgID){
		logger.warn("--------转发文件到中威通讯录服务器-----");
		byte[] byt =  SendRequest.OnThirdFileMsg(loginName, userId, userName, szText, v, szMsgID);
		botConnection.sendMessage(byt);
	}
}
