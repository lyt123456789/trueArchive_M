package cn.com.trueway.archiveModel.service;

import javax.servlet.http.HttpServletRequest;

public interface EssEepService {

	/**
	 * 导出eep
	 * @param request
	 * @return
	 */
	String logOutEEP(HttpServletRequest request);
	/**
	 * 私钥签名
	 * @param data
	 * @return
	 */
	String getSign(String data, String id, String struc);
}
