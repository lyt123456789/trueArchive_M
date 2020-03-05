package cn.com.trueway.checkSQLInjection.interceptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 特殊字符（关键字）过滤器
 * 
 * @author wangjp
 * 
 */

public class SpecialCharFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 3414879696167061201L;

	@SuppressWarnings("unused")
	private FilterConfig config;
	private String site = "";
	private String skip = "";

	public SpecialCharFilter() {
		super();
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		site = config.getInitParameter("site");
		skip = config.getInitParameter("skip");
	}

	@SuppressWarnings("rawtypes")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Map map = req.getParameterMap();
		Set keSet = map.entrySet();
		String[] skipUri = skip.split(",");
		boolean isSkip = false;
		for (int i = 0; i < skipUri.length; i++) {
			if (skipUri.length>0&&req.getRequestURI().indexOf(skipUri[i]) > 0) {
				// 在跳过站点内
				isSkip = true;
				break;
			}
		}
		if(!isSkip){
			for (Iterator itr = keSet.iterator(); itr.hasNext();) {
				Map.Entry me = (Map.Entry) itr.next();

				Object ok = me.getKey();
				Object ov = me.getValue();

				String[] value = new String[1];
				if (ov instanceof String[]) {
					value = (String[]) ov;
				} else {
					value[0] = ov.toString();
				}

				for (int k = 0; k < value.length; k++) {
					if(!"content".equals(ok)){
						if (checkStr(ok == null ? "" : ok.toString()) || checkStr(value[k])) {
							// request.getRequestDispatcher("/cmsqt/specialCharError.jsp").forward(request,
							// response);// 跳转到制定页面
							// 跳转页面
							res.sendRedirect(req.getContextPath() + "/specialCharError.jsp?key=" + ok);

							System.out.println("warning:存在特殊字符，请判定是否存在恶意攻击（" + ok + "=" + value[k] + "）");
							return;
						}

					}
				}
			}
		}
		/**
		 * 跨站点请求伪造 START
		 */
		String referer = req.getHeader("Referer"); // REFRESH
		// String serverName = request.getServerName();
		boolean flag = false;
		if (referer == null) {
			flag = true;
		}
		if(site.contains(",") && !flag){
			String[] serverName = site.split(",");
			for (int i = 0; i < serverName.length; i++) {
				// System.out.println("serverName=" + serverName[i]);
				if (referer.indexOf(serverName[i]) > 0) {
					// 在可信站点内
					flag = true;
					break;
				}
			}
		}else if(!flag){
			if(referer.indexOf(site) > 0){
				flag = true;
			}
		}
		// System.out.println("referer=" + referer);
		if (!flag) {
			// 通过手动回写页面脚本退出过滤器
			// 必须手动指定编码格式
			res.setContentType("text/html;charset=UTF-8");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print("<script>window.alert('请不要跨站点访问！'); window.history.go(-1);</script>");
			return;
		}
		/**
		 * 跨站点请求伪造 END
		 */

		chain.doFilter(request, response);// 按请求路径正常跳转
	}

	public void destroy() { // 定时器销毁

	}

	/*
	 * 有特殊字符返回true；无特殊字符返回false
	 */
	private boolean checkStr(String str) {

		/*
		 * String[] specialStrArr = { "insert", "select", "delete", "update",
		 * "count", "drop", "and", "grant", "execute", "or", "like", "truncate",
		 * "union", "where", "declare" };
		 */
		String[] specialStrArr = { "insert", "select", "delete", "update", "drop", "and", "grant", "execute", "or", "like", "truncate", "union", "where", "declare" };
		String[] specialCharArr = { "<script", "</script", "alert", "'", "#", "<", ">", "*", "!", "^", "&", "?", "|", "$", "@", "+", "\\", "\'", "\"", "2.2250738585072011e-308" };
		//
		// String[] specialCharArr = {"<script", "'", "#","<", ">", "*", "(",
		// ")", "!", "^", "&" ,"?","%",
		// "|",";","$","@","+","CR","LF",",","\\","\'","\""};

		for (int i = 0; i < specialStrArr.length; i++) {
			if (StringUtils.isNotEmpty(str) && str.toUpperCase().equals(specialStrArr[i].toUpperCase())) {
				// System.out.println(str+";"+specialStrArr[i]);
				return true;
			}
		}

		for (int i = 0; i < specialCharArr.length; i++) {

			/**
			 * JDK1.5 用词方法判断
			 */
			/*
			 * if (-1!=str.indexOf(specialCharArr[i])) {
			 * System.out.println(str+";"+specialCharArr[i]); return true; }
			 */

			if (str.contains(specialCharArr[i])) {
				// System.out.println(str+";"+specialCharArr[i]);
				return true;
			}
		}
		return false;
	}
}
