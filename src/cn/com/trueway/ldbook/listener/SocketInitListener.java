package cn.com.trueway.ldbook.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import cn.com.trueway.ldbook.util.RemoteLogin;

/**
 * 
 * 描述：监听器：socket初始化,以及销毁
 * 作者：蔡亚军
 * 创建时间：2014-7-14 下午2:06:28
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class SocketInitListener extends HttpServlet implements ServletContextListener{

	private static final long serialVersionUID = 3241560018693580050L;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("------监听socket销毁---------");
		RemoteLogin rem = new RemoteLogin();
		rem.choseSocket();
	}

	/**
	 * 初始化生成
	 * @param arg0
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("------监听socket初始化---------");
		RemoteLogin rem = new RemoteLogin(RemoteLogin.loginName);
		rem.checkUser();
	}
}
