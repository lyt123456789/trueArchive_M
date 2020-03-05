package cn.com.trueway.workflow.core.servlet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.trueway.workflow.core.pojo.MonitorInfoBean;
import cn.com.trueway.workflow.core.service.MonitorService;

public class DataToMonitorTimer extends TimerTask{
	
	private static ApplicationContext 	ctx = null;
	
	private static MonitorService 		monitorService = null;
	
	@Override
	public void run() {
		if(ctx == null){
			ctx = new ClassPathXmlApplicationContext(new String[]{"classpath*:spring/*/*.xml","classpath*:spring/*/*/*.xml","classpath*:spring/*/*/*/*.xml"});
		}
		if(monitorService == null){
			monitorService = (MonitorService) ctx.getBean("monitorService");
		}
		MonitorInfoBean info = new MonitorInfoBean();
		Sigar sigar = new Sigar();  
		Mem mem;
		try {
			mem = sigar.getMem();
			// 内存信息
			info.setMaxMemory(mem.getTotal() / 1024L);
			info.setTotalMemory(mem.getTotal() / 1024L);
			info.setFreeMemory(mem.getFree() / 1024L);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			info.setCurrentDate(sdf.format(calendar.getTime()));
			info.setCurrentTime(calendar.get(Calendar.HOUR_OF_DAY));
			info.setCurrentMinute(calendar.get(Calendar.MINUTE));
			double memoryRatio = ((double)(mem.getFree() / 1024L)/(mem.getTotal() / 1024L))*100;
			info.setMemoryRatio((int)memoryRatio);
			long totalCpu = 0;
			double cpuRatio = 0.0;
			CpuInfo infos[] = sigar.getCpuInfoList();  
			 CpuPerc cpuList[] = null;  
		        cpuList = sigar.getCpuPercList();  
		        int count = 0;
		        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用  
		            CpuInfo info1 = infos[i];  
		            totalCpu += info1.getMhz();
		            count++;
		            cpuRatio += cpuList[i].getCombined();    
		        }  
		        cpuRatio = (cpuRatio/count )*100;
		        info.setTotalCpu(totalCpu);
		        info.setCpuRatio((int)cpuRatio);
		        
		    //服务器信息
		    String hostName = "";
		    String ip = "";
	        try {
				InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				hostName = sigar.getNetInfo().getHostName();
			}
	        Runtime r = Runtime.getRuntime();
			Properties props = System.getProperties();
			InetAddress addr;
			try {
				addr = InetAddress.getLocalHost();
				ip = addr.getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	        info.setHostName(ip);
	        monitorService.addMonitorInfo(info);
		} catch (SigarException e) {
			e.printStackTrace();
		}  
	}
}
