package cn.com.trueway.base.util;

import cn.com.trueway.base.io.socket.IoSocket;
import cn.com.trueway.base.io.socket.IoSocketForIos;






/**
 * 发送消息公共类
 * @author leo
 *
 */
public class SocketSendPushUtil {
	
	//通知提醒
	public static String TYPE_XXTZ = "1";
	
	//待办信息
	public static String TYPE_XXDB = "2";
	
	//留言
	public static String TYPE_XXLY = "3";
	
	//消息
	public static String TYPE_XXXX = "4";
	
	
	/**
	 * 全部推送（安卓、IOS）
	 * @param userid
	 * @param title
	 * @param date
	 * @param type
	 */
	public static void sendCommon(String userid, String title, String date, String type){
		sendPush(userid, title, date, type);
		sendPushForIOS(userid, title, date, type);
	}
	
	
	/**
	 * 消息推送  安卓
	 * @param userid
	 * @param title
	 * @param date
	 * @param type
	 */
	public static void sendPush(String userid, String title, String date, String type){
		try {
			StringBuffer sbs = new StringBuffer();
			sbs.append("{");
			sbs.append("\"userId\":\"" + userid + "\",");
			sbs.append("\"title\":\"" + title + "\",");
			sbs.append("\"date\":\"" + date + "\",");
			sbs.append("\"type\":\""+type+"\"");
			sbs.append("}");
			IoSocket.send(sbs.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 消息推送  IOS
	 * @param userid
	 * @param title
	 * @param date
	 * @param type
	 */
	public static void sendPushForIOS(String userid, String title, String date, String type){
		try {
			StringBuffer sbs = new StringBuffer();
			sbs.append("{");
			sbs.append("\"userId\":\"" + userid + "\",");
			sbs.append("\"title\":\"" + title + "\",");
			sbs.append("\"date\":\"" + date + "\",");
			sbs.append("\"type\":\""+type+"\"");
			sbs.append("}");
			System.err.println(sbs.toString());
			IoSocketForIos.send(sbs.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
