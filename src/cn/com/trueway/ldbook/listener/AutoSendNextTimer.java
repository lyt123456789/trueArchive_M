package cn.com.trueway.ldbook.listener;

import java.util.TimerTask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.trueway.workflow.core.action.AutoSendAction;

public class AutoSendNextTimer extends TimerTask {

	public static boolean isConneted = false;

	public static boolean isHeart = false;

	public static ApplicationContext ctx = null;

	@Override
	public void run() {
		try {
			if (AutoSendNextTimer.ctx == null) {
				AutoSendNextTimer.ctx = new ClassPathXmlApplicationContext(
						new String[] { "classpath*:spring/common/*.xml",
								"classpath*:spring/workflow/autosend/*.xml" });
			}
			AutoSendAction autoSendAction = (AutoSendAction) AutoSendNextTimer.ctx
					.getBean("autoSendAction");
			autoSendAction.findAuto();
		} catch (Exception e) {
			e.getMessage();
		}
	}
}