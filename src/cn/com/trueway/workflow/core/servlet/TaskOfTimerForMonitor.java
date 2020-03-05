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
public class TaskOfTimerForMonitor  extends HttpServlet {

	private static final long serialVersionUID = 3585511054594373951L;

	private final static long JOB_INTERNAL = 1000*60*5;
	public void init() throws ServletException{
		String str = System.getProperty("java.library.path");
		System.out.println("------------定时器启动成功!-----------"+str);
    	// 新建定时器任务
		Timer timer = new Timer();
		DataToMonitorTimer dbTimer = new DataToMonitorTimer();
		// 参数1：自定义timer实现类
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int delay = calendar.get(Calendar.MINUTE);
		delay = delay%5;
		if(delay != 0){
			delay = 5-delay;
		}
		//定制5分钟执行一次
		timer.scheduleAtFixedRate(dbTimer, delay, JOB_INTERNAL);
    }
}
