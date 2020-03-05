package cn.com.trueway.workflow.license;

import java.io.File;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import cn.com.trueway.base.util.Constant;
import cn.com.trueway.license.LicenseFactory;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.set.util.Constants;
/**
 * 
 * 描述：检查license定时器 作者：蔡亚军 创建时间：2014-7-24 上午8:55:33 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间> 修改原因及地方：<修改原因描述> 版本：v1.0 JDK版本：JDK1.6
 */
public class LicenseTimer extends TimerTask {

	private String syspath;
	
	private Logger logger = Logger.getLogger(this.getClass());

	public LicenseTimer(String syspath) {

		this.syspath = syspath;
	}

	@Override
	public void run() {
		// license路径
		String licensePath = syspath + "license/license.lic";
		File file = new File(licensePath);
		// 检查文件是否存在
		licensePath = licensePath.replace("\\", "/");
		if(!file.exists()){
			System.out.println("未上传license文件,请检查!");
		} else {
			try {
				//int result = LicenseFactory.getLicense().resolveLicense(licensePath, Constant.productVersion, Constant.productName);
				int result = 1;
				if (result == 0) { // 内容为空
					Constant.LICENSE_CHECK_PASSED = "0";
					logger.error("license内容为空!");
				} else if (result == 1) { // 验证通过
					Constant.LICENSE_CHECK_PASSED = "1";
				} else if (result == 2) { // 超过有效期
					Constant.LICENSE_CHECK_PASSED = "2";
					logger.error("license超过有效期!");
				} else if (result == 3) { // 验证不通过
					Constant.LICENSE_CHECK_PASSED = "3";
					logger.error("license验证不通过!");
				} else if(result == 4){	  //mac验证不正确
					Constant.LICENSE_CHECK_PASSED = "4";
					logger.error("license的mac验证不正确!");
				}
				Constant.LICENSE_CHECK_PASSED = "1";
			} catch (Exception e) {
				logger.error("license校验异常!");
			}
		}
	}
}
