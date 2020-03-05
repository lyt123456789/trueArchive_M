package cn.com.trueway.workflow.set.util;

import java.text.SimpleDateFormat;

import net.sf.json.JSONObject;
import cn.com.trueway.ldbook.pojo.MsgSend;
import cn.com.trueway.sys.util.SystemParamConfigUtil;

public class MsgToObj {

	/**
	 * 将实体类转换成obj
	 * @param msgSend
	 * @return
	 */
	public static JSONObject msgToObj(MsgSend msgSend, String serverUrl){
		String type = msgSend.getType();
		String zwCom_msg = SystemParamConfigUtil.getParamValueByParam("zwCom_msg");
		JSONObject obj = new JSONObject();	
		obj.put("owner", msgSend.getSendUserName());							//办件发送人员
		obj.put("processId", msgSend.getProcessId());							//待办的唯一标示
		obj.put("sendtime", new SimpleDateFormat("yyyy-MM-dd").format(msgSend.getSendDate()));		//办件发送时间
		obj.put("userId", msgSend.getSendUserId());								//发送的人员id
		obj.put("action", serverUrl+"/table_checkWfpIsOverById.do");			//接口与调用的地址
		if(type!=null){
			if(type.equals("4")){
				obj.put("type", 4);
				obj.put("name", "已办结公文");
				obj.put("imageUrl", serverUrl+"/images/end.png");
				zwCom_msg = zwCom_msg.replace("{title}", msgSend.getTitle()).replace("{type}", "已办结公文");
			}else if(type.equals("5")){
				obj.put("type", 5);
				obj.put("name", "督办公文");
				obj.put("imageUrl", serverUrl+"/images/supervise.png");
				zwCom_msg = zwCom_msg.replace("{title}", msgSend.getTitle()).replace("{type}", "督办公文");
			}
		}else{
			obj.put("type", 3);
			obj.put("name", "待办公文");
			obj.put("imageUrl", serverUrl+"/images/fileMan.png");
			zwCom_msg = zwCom_msg.replace("{title}", msgSend.getTitle()).replace("{type}", "待办公文");
		}
		obj.put("title",zwCom_msg);												//办件的标题
		obj.put("text", zwCom_msg);												//提示的消息内容
		System.out.println("---------obj.toString="+obj.toString());
		return obj;
	}
}
