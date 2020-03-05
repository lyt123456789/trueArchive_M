package cn.com.trueway.workflow.core.servlet;

import java.util.TimerTask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.trueway.workflow.core.action.TableInfoAction;
import cn.com.trueway.workflow.core.dao.PendingDao;

public class AutoOperateTimer extends TimerTask{
	
	public PendingDao pendingDao = null;
	
	@Override
	public void run() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath*:spring/*/*.xml","classpath*:spring/*/*/*.xml","classpath*:spring/*/*/*/*.xml"});
		TableInfoAction tableInfoAction = (TableInfoAction) ctx.getBean("tableInfoAction");
		tableInfoAction.autoOperateForLeave();
	}
}
