/**
 * 
 */
package cn.com.trueway.base.util;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * 描述：得到Spring加载的实例<br>
 * 作者：WangXF<br>
 * 创建时间：2011-12-2 下午09:39:52<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class SpringContextUtil {
	private static ApplicationContext context = null;
	private static Logger logger = Logger.getLogger(SpringContextUtil.class);
	private SpringContextUtil(){}
	
	private static ApplicationContext getInstance(String[] xmlConfigFile){
		if(context==null){
			try{
				context = new ClassPathXmlApplicationContext(xmlConfigFile);
			}catch (Exception e) {
				logger.error(e.getMessage());
				logger.error("所加载的配置文件不符合要求");
			}
		}
		return context;
	}
	
	public static Object getBeanValue(String beanName){
		String[] xmlConfigFile=new String[]{"classpath*:spring/*/*.xml"};
		ApplicationContext context = getInstance(xmlConfigFile);
		try{
			Object object = context.getBean(beanName);
			return object;
		}catch(Exception e){
			logger.error(e.getMessage());
			logger.error("配置文件中没有定义名字为:"+beanName+"的Bean");
		}
		return null;
	}
	
	public static Object getBeanValue1(String beanName){
		String[] xmlConfigFile=new String[]{"classpath*:spring/workflow/group/serviceContext_group_dao.xml","classpath*:spring/common/dataAccessContext.xml"};
		ApplicationContext context = getInstance(xmlConfigFile);
		try{
			Object object = context.getBean(beanName);
			return object;
		}catch(Exception e){
			logger.error(e.getMessage());
			logger.error("配置文件中没有定义名字为:"+beanName+"的Bean");
		}
		return null;
	}
	
	public static Object getBeanValue(String[] xmlConfigFile,String beanName){
		ApplicationContext context = getInstance(xmlConfigFile);
		try{
			Object object = context.getBean(beanName);
			return object;
		}catch(Exception e){
			logger.error(e.getMessage());
			logger.error("配置文件中没有定义名字为:"+beanName+"的Bean");
		}
		return null;
	}
	
	public static Object getBeanValue(ServletContext servletContext,String beanName){
		if(servletContext==null){
			logger.debug("ServletContext不能为空");
			return null;
		}
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		try{
			Object obj = wac.getBean(beanName);
			return obj;
		}catch(Exception e){
			logger.error(e.getMessage());
			logger.error("配置文件中没有定义名字为:"+beanName+"的Bean");
		}
		
		return null;
	}
}
