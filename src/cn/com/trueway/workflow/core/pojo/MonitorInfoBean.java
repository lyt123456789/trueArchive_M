package cn.com.trueway.workflow.core.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_WF_CORE_MONITORINFO")
public class MonitorInfoBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "cn.com.trueway.base.util.UUID36GeneratorUtil")
	@Column(name = "id", length = 36)
	private String id ;
    
	/** *//** 可使用内存. */  
	@Column(name = "TOTALMEMORY")
    private long totalMemory;   
       
    /** *//** 剩余内存. */  
	@Column(name = "FREEMEMORY")
    private long freeMemory;   
       
    /** *//** 最大可使用内存. */  
	@Column(name = "MAXMEMORY")
    private long maxMemory; 
    
    /**
     * 内存利用率
     */
	@Column(name = "MEMORYRATIO")
    private int memoryRatio;
       
    /**
     * 当前日期
     */
	@Column(name = "CURRENTDATE")
    private String currentDate;
    /**
     * 当前时间
     */
	@Column(name = "CURRENTTIME")
    private int currentTime;
	
	/**
	 * 当前日期
	 */
	@Column(name = "CURRENTMINUTE")
	private int currentMinute;
    
	@Column(name = "TOTALCPU")
    private long totalCpu;
    
    /** *//** cpu使用率. */  
	@Column(name = "CPURATIO")
    private int cpuRatio;   
	
	@Column(name = "HOSTNAME")
	private String hostName;//主机名
  
    public long getFreeMemory() {   
        return freeMemory;   
    }   
  
    public void setFreeMemory(long freeMemory) {   
        this.freeMemory = freeMemory;   
    }    
  
    public long getMaxMemory() {   
        return maxMemory;   
    }   
  
    public void setMaxMemory(long maxMemory) {   
        this.maxMemory = maxMemory;   
    }   
  
    public long getTotalMemory() {   
        return totalMemory;   
    }   
  
    public void setTotalMemory(long totalMemory) {   
        this.totalMemory = totalMemory;   
    }   

   

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public int getMemoryRatio() {
		return memoryRatio;
	}

	public void setMemoryRatio(int memoryRatio) {
		this.memoryRatio = memoryRatio;
	}

	public int getCpuRatio() {
		return cpuRatio;
	}

	public void setCpuRatio(int cpuRatio) {
		this.cpuRatio = cpuRatio;
	}


	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public long getTotalCpu() {
		return totalCpu;
	}

	public void setTotalCpu(long totalCpu) {
		this.totalCpu = totalCpu;
	}

	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	public int getCurrentMinute() {
		return currentMinute;
	}

	public void setCurrentMinute(int currentMinute) {
		this.currentMinute = currentMinute;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}   
}
