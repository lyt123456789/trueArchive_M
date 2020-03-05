package cn.com.trueway.ldbook.listener;

import java.util.Date;
import java.util.Timer;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AutoSendNextServlet extends HttpServlet {
	private static final long serialVersionUID = -8967345631932485837L;

	// 时间间隔(一天)
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
	
	// 增加或减少天数
	public Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}

	public void init() throws ServletException {
		// 新建定时器任务
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 15); // 每晚8点
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime(); // 第一次执行定时任务的时间
		// 如果第一次执行定时任务的时间 小于当前的时间
		// 此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		if (date.before(new Date())) {
			date = this.addDay(date, 0);//后一天开始执行
		}
		Timer timer = new Timer();
		AutoSendNextTimer task = new AutoSendNextTimer();
		// 安排指定的任务在指定的时间开始进行重复的固定延迟执行。
		timer.schedule(task, date, PERIOD_DAY);
		
		//timer.schedule(task,new Date(),10*1000);
	}
}
