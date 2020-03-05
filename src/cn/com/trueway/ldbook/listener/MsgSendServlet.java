package cn.com.trueway.ldbook.listener;

import java.util.Date;
import java.util.Timer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 
 * 描述：定时线程发送短消息
 * 作者：蔡亚军
 * 创建时间：2014-7-15 上午11:37:48
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class MsgSendServlet extends HttpServlet {
	
	private static final long serialVersionUID = -8967345631932485837L;

	public void init() throws ServletException{
    	// 新建定时器任务
		System.out.println("----发送短信定时器启动------");
		Timer timer = new Timer();
		MsgSendTimer dbTimer = new MsgSendTimer();
		//定制30分钟执行一次:主要用于扫描发送失败的消息
		//timer.schedule(dbTimer,new Date(),60*60*1000);
		timer.schedule(dbTimer,new Date(),3*60*1000);
    }

}
