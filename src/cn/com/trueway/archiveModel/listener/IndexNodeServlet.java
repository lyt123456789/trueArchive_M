package cn.com.trueway.archiveModel.listener;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 
 * 描述：定时线程更新需要建立索引库的树节点
 */
public class IndexNodeServlet extends HttpServlet {
	
	private static final long serialVersionUID = -8967345631932485837L;

	public void init() throws ServletException{
    	// 新建定时器任务
		String str = System.getProperty("java.library.path");
		System.out.println("----索引节点定时器启动------"+str);
		Timer timer = new Timer();
		//IndexNodeTimer dbTimer = new IndexNodeTimer();
		//timer.scheduleAtFixedRate(dbTimer,5,6*60*60*1000);
    }

}
