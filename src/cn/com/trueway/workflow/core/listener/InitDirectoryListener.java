package cn.com.trueway.workflow.core.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.com.trueway.sys.util.SystemParamConfigUtil;


public class InitDirectoryListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//根目录
		String root = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		// 判断根目录下是否存在
		if(!new File(root).exists()){
			// 不存在 则创建
			new File(root).mkdirs();
			// 创建pdf,true,html,temp
			new File(root+"/pdf/").mkdirs();
			new File(root+"/html/").mkdirs();
			new File(root+"/true/").mkdirs();
			new File(root+"/temp/").mkdirs();
			new File(root+"/attachments/").mkdirs();
		}else{
			if(!new File(root+"/pdf/").exists()){
				new File(root+"/pdf/").mkdirs();
			}
			if(!new File(root+"/html/").exists()){
				new File(root+"/html/").mkdirs();
			}
			if(!new File(root+"/true/").exists()){
				new File(root+"/true/").mkdirs();
			}
			if(!new File(root+"/temp/").exists()){
				new File(root+"/temp/").mkdirs();
			}
			if(!new File(root+"/attachments/").exists()){
				new File(root+"/attachments/").mkdirs();
			}
		}
	
	}

}
