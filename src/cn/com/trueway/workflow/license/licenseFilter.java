package cn.com.trueway.workflow.license;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.trueway.base.util.Constant;


public class licenseFilter implements Filter {

	@Override
	public void destroy() {
		
	}
	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
					throws IOException, ServletException {
		//获取全局变量
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String license_checked = Constant.LICENSE_CHECK_PASSED; 
		if(license_checked!=null && license_checked.equals("1")){
			filterChain.doFilter(servletRequest, servletResponse);
		}else{
			String requestUrl = httpServletRequest.getRequestURI();
			if(passCheckLicense(httpServletRequest)){
				filterChain.doFilter(servletRequest, servletResponse);
			}else{
				String url = "/licenseerror.html";
				servletRequest.getRequestDispatcher(url).forward(servletRequest, servletResponse);
			}
			
		}
	}
	
	/**
	 * 
	 * 描述：检查license文件
	 * @param httpServletRequest
	 * @return boolean
	 * 作者:蔡亚军
	 * 创建时间:2016-8-24 上午11:24:55
	 */
	public boolean passCheckLicense(HttpServletRequest httpServletRequest){
		String requestUrl = httpServletRequest.getRequestURI();
		List<String> list = new ArrayList<String>();
		list.add("setup_login.do");
		list.add("setup_loginOn.do");
		list.add("setup_licenceInfo.do");
		list.add("setup_uploadLicence.do");
		boolean passed = false;
		for(int i=0; i<list.size(); i++){
			if(requestUrl.endsWith(list.get(i))){
				passed = true;
				break;
			}
		}
		if(requestUrl.indexOf("setup_")>-1){
			passed = true;
		}
		return passed;
	}
	
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
