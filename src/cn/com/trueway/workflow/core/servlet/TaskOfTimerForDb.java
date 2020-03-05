package cn.com.trueway.workflow.core.servlet;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 
 * @ClassName: TaskOfTimerForDb 
 * @Description: 待办设置期限后自动发送
 * @date 2013-11-26 下午04:06:10
 */
public class TaskOfTimerForDb  extends HttpServlet {

	private static final long serialVersionUID = 3585511054594373951L;

	public void init() throws ServletException{
		System.out.println("------------定时器启动成功!-----------");
    	// 新建定时器任务
		Timer timer = new Timer();
		DataToDbTimer dbTimer = new DataToDbTimer();
		// 参数1：自定义timer实现类
		Calendar calendar = Calendar.getInstance();
		//定制30分钟执行一次
		timer.schedule(dbTimer,new Date(),30*60*1000);
    }
}
