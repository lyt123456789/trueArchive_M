package cn.com.trueway.workflow.core.servlet;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.com.trueway.ldbook.listener.AutoSendNextTimer;

/**
 * 
 * @ClassName: TaskOfTimerForDuBan 
 * @Description: 每天8点和12点执行定时任务
 * @date 2013-11-26 下午04:06:10
 */
public class TaskOfTimerForDuBan  extends HttpServlet {

	private static final long serialVersionUID = -2555312092808747241L;

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
			calendar.set(Calendar.HOUR_OF_DAY, 8); // 每天8点
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.SECOND,0);
			Date date = calendar.getTime(); // 第一次执行定时任务的时间
			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(Calendar.HOUR_OF_DAY, 12); // 每天12点
			calendar2.set(Calendar.MINUTE, 0);
			calendar2.set(Calendar.SECOND, 0);
			Date date2 = calendar2.getTime(); // 第二次执行定时任务的时间
			// 如果第一次执行定时任务的时间 小于当前的时间
			// 此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
			if (date.before(new Date())) {
				date = this.addDay(date, 1);//后一天开始执行
			}
			if (date2.before(new Date())) {
				date2 = this.addDay(date2, 1);//后一天开始执行
			}
			Timer timer = new Timer();
			DataToDuBanTimer task = new DataToDuBanTimer();
			DataToDuBanTimer task2 = new DataToDuBanTimer();
			AutoOperateTimer task3 = new AutoOperateTimer();
			// 安排指定的任务在指定的时间开始进行重复的固定延迟执行。
			timer.schedule(task, date, PERIOD_DAY);
			timer.schedule(task2, date2, PERIOD_DAY);
			timer.schedule(task3, date, PERIOD_DAY);
			//timer.schedule(task,new Date(),10*1000);
		}
}
