package cn.com.trueway.ldbook.listener;

import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cn.com.trueway.ldbook.util.RemoteLogin;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.action.MsgSendAction;
import cn.com.trueway.workflow.core.service.MsgSendService;
/**
 * 
 * 描述：短消息发送task实现类
 * 作者：蔡亚军
 * 创建时间：2015-3-16 上午11:19:17
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class MsgSendTimer extends TimerTask{
	
	public static boolean isConneted = false;
	
	public static boolean isHeart = false;	
	
	public static ApplicationContext ctx = null;
	
	public static MsgSendService msgSendService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void run() {
		try {
			String userName = SystemParamConfigUtil.getParamValueByParam("userName");	//自定义的名称
			MsgSendTimer.isHeart = false;					//先设置isheart = false; 
			RemoteLogin remote = new RemoteLogin(userName);
			remote.checkHeart();							//心跳反应,心跳成功,将isheart改为true
			Thread.sleep(90*1000);						//sleep(预留按安排时间)
			if(MsgSendTimer.isHeart){		
				MsgSendTimer.isConneted = true;
			}else{		//心跳未获取数据, 表示已经端开连接,启用重连
				logger.error("重连中威通讯录服务器！");
				RemoteLogin rem = new RemoteLogin(RemoteLogin.loginName);
				rem.checkUser();
			}
			Thread.sleep(5*1000);
			if(MsgSendTimer.isConneted){
				if(MsgSendTimer.ctx==null){
					MsgSendTimer.ctx = new ClassPathXmlApplicationContext(new String[]{"classpath*:spring/common/*.xml","classpath*:spring/workflow/msgsend/*.xml"});
				}
				if(msgSendService == null){
					msgSendService = (MsgSendService) MsgSendTimer.ctx.getBean("msgSendService");
				}
				MsgSendAction msgSendAction = (MsgSendAction) MsgSendTimer.ctx.getBean("msgSendAction");
				msgSendAction.msgSend();
			}else{
				logger.error("网络连接中威通讯录推送服务器失败,请检查网络");
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
}