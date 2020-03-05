package cn.com.trueway.workflow.core.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


import cn.com.trueway.base.actionSupport.BaseAction;

public class DataBaseOracle extends BaseAction{
	
	private static final long serialVersionUID = -1411482229063963345L;

	/**
	* 备份oracle数据库
	* 
	* @param userName
	*            数据库登录名
	* @param passWord
	*            数据库登录密码
	* @param dataBaseName
	*            需要备份的数据库名称
	* @param address
	*            保存地址;如D:/doc/
	*/
	public String createDmp(){
		String path =  getRequest().getSession().getServletContext().getRealPath("")+"/WEB-INF/classes/hibernate.properties";
		//String path = "D:/User/Workspaces/.metadata/.plugins/org.eclipse.wst.server.core/tmp9/wtpwebapps/trueWorkFlowV2.1_GT/WEB-INF/classes/hibernate.properties";
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "data_oracle" + sdf.format(c.getTime());
		String userName = readValue(path,"jdbc.username");
		String passWord = readValue(path,"jdbc.password");
		String url = readValue(path,"jdbc.url");
		String dataBaseName = "orcl_"+ url.substring(url.indexOf("@"),url.lastIndexOf(":")-5);
		/*
		 * String address = getRequest().getSession().getServletContext()
		 * .getRealPath("") + "\\datas\\dmp\\";
		 */
		String address = "D:/";
		// 拼装DOS命令进行数据库备份
		StringBuffer exp = new StringBuffer("exp ");
		exp.append(userName);
		exp.append("/");
		exp.append(passWord);
		exp.append("@");
		exp.append(dataBaseName);
		exp.append(" file=");
		/*
		 * 得到存储地址的最后一个字符 如果有\就直接拼装地址 如果没有\就加上/然后拼装数据库名称
		 */
		String maxIndex = address.substring(address.length() - 1);
		if ("/".equals(maxIndex) || "\\".equals(maxIndex)) {
			exp.append(address);
		} else {
			address = address + "\\";
			exp.append(address);
		}
		File file = new File(address);
		if (!file.exists()) {
			file.mkdir();
		}
		exp.append("workflow_ntgt_" + fileName);
		exp.append(".dmp");
		System.out.println("开始备份........");
		try {
			System.out.println(exp.toString());
			Process p = Runtime.getRuntime().exec(exp.toString());
			InputStreamReader isr = new InputStreamReader(p.getErrorStream());
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("错误") != -1) {
					break;
				}
			}
			p.destroy();
			p.waitFor();
			System.out.println("备份成功......");
			return "success";
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "fail";
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			return "fail";
		}
	}
	
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		InputStream ips = null;
		try {
			ips = new BufferedInputStream(new FileInputStream(filePath));
			props.load(ips);
			String value = props.getProperty(key);
			return value;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (ips != null) {
				try {
					ips.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
