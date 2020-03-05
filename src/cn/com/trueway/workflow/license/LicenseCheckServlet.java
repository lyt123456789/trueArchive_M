package cn.com.trueway.workflow.license;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * 
 * 描述：定期检查license
 * 作者：蔡亚军
 * 创建时间：2014-7-24 上午8:52:29
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class LicenseCheckServlet extends HttpServlet{

	private static final long serialVersionUID = 8089697522863871719L;
	
	public void init() throws ServletException{
		// 新建定时器任务
		String sysPath = getServletContext().getRealPath("/");
		Timer timer = new Timer();
		LicenseTimer licenseTimer = new LicenseTimer(sysPath);
		//60分钟执行一次
		timer.schedule(licenseTimer, new Date(), 24*60*60*1000);
		
	}
}
