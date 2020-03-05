package cn.com.trueway.workflow.log.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.workflow.core.pojo.Employee;

public class LogFilter implements Filter {
	
	 private final static double DEFAULT_USERID = Math.random()*100000.0;  

	 public void destroy() {
	    	
	 }

	 public void doFilter(ServletRequest request, ServletResponse response,
	           FilterChain chain) throws IOException, ServletException {
	       HttpServletRequest req=(HttpServletRequest)request;
	        HttpSession session= req.getSession();
	        if (session==null){
	            MDC.put("userId",DEFAULT_USERID);  
	        }
	        else{
	            Employee customer = (Employee) session.getAttribute(MyConstants.loginEmployee);
	            if (customer==null){
	                MDC.put("userId",DEFAULT_USERID);
	                MDC.put("userName",DEFAULT_USERID);
	            }
	            else
	            {
	                MDC.put("userId",customer.getEmployeeGuid());
	                MDC.put("userName",customer.getEmployeeName());
	            }
	        }

	       chain.doFilter(request, response);
	 }
	    
	 public void init(FilterConfig Config) throws ServletException {

	 }

}
