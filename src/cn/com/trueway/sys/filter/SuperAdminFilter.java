package cn.com.trueway.sys.filter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.sys.pojo.Menu;
import cn.com.trueway.workflow.core.pojo.Employee;

public class SuperAdminFilter implements Filter {

	private String urlOfCreateMenu;
	private String urlOfQueryRole;
	private String urlOfQueryMenu;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.urlOfCreateMenu = filterConfig.getInitParameter("urlOfCreateMenu");
		this.urlOfQueryRole = filterConfig.getInitParameter("urlOfQueryRole");
		this.urlOfQueryMenu = filterConfig.getInitParameter("urlOfQueryMenu");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	 HttpServletRequest hrequest = (HttpServletRequest)request;
    	 Employee employee = (Employee) hrequest.getSession().getAttribute(MyConstants.loginEmployee);
    	 List<Menu> flist = new ArrayList<Menu>();
    	 if("1".equals(employee.getIsAdmin())){
        	 Menu m1 = new Menu();
        	 m1.setMenuExtLinks(BigDecimal.valueOf(0));
        	 m1.setMenuUrl(this.urlOfCreateMenu);
        	 m1.setMenuId("1");
        	 m1.setMenuName("公用菜单管理");
        	 flist.add(m1);
        	 Menu m2 = new Menu();
        	 m2.setMenuExtLinks(BigDecimal.valueOf(0));
        	 m2.setMenuUrl(this.urlOfQueryRole);
        	 m2.setMenuId("2");
        	 m2.setMenuName("站点角色查询");
        	 flist.add(m2);
        	 Menu m3 = new Menu();
        	 m3.setMenuExtLinks(BigDecimal.valueOf(0));
        	 m3.setMenuUrl(this.urlOfQueryMenu);
        	 m3.setMenuId("3");
        	 m3.setMenuName("站点菜单查询");
        	 flist.add(m3);
        	 //menuPath
    	 }
    	 request.setAttribute("flist", flist);
    	 chain.doFilter(request, response);
    } 

	public void destroy() {

	}
}