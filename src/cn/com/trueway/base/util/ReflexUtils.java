/**
 * 文件名称:ReflexUtils.java
 * 作者:郭杰<br>
 * 邮箱:438680421@qq.com<br>
 * 创建时间:2014-6-18 上午10:46:46
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.base.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.sys.util.SystemParamConfigUtil;

import oracle.sql.CLOB;

/**
 * 描述：TODO 反射工具类<br>
 * 作者：郭杰<br>
 * 邮箱:438680421@qq.com<br>
 * 创建时间:2014-6-18 上午10:46:46<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.6<br>
 */
public class ReflexUtils {

	/**
	 * 创建javaBean 并且将map中的值赋值到bean中
	 * 
	 * @param className
	 *            javabean的class
	 * @param map
	 *            存储值的map,键小写
	 * @return
	 */
	public static Object setBean(Class<?> className, Map<String, Object> map) {
		Object obj = null;
		try {
			obj = className.newInstance();
			Method[] methods = className.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				String mothodName = methods[i].getName();
				if (map.get(mothodName.substring(3, mothodName.length())
						.toLowerCase()) != null) {
					if (mothodName.startsWith("set")) {
						Object value = null;
						if (ConstantUtil.MYSQL.equals(SystemParamConfigUtil
								.getParamValueByParam("database"))
								&& methods[i].getParameterTypes().length == 1
								&& methods[i].getParameterTypes()[0]
										.isAssignableFrom(CLOB.class)) {
							String name = (String) map.get(mothodName
									.substring(3, mothodName.length())
									.toLowerCase());
							CLOB clob = null;
							try {
								clob = CLOB.getEmptyCLOB();
								clob.setBytes(name.getBytes());
								value = clob;
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else {
							value = map.get(mothodName.substring(3,
									mothodName.length()).toLowerCase());
						}
						className.getMethod(mothodName,
								methods[i].getParameterTypes()).invoke(obj,
								new Object[] { value });
					}

				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 将javaBean 有的值放入map中
	 * 
	 * @param className
	 *            javabean的class
	 * @param obj
	 *            javaBean对象
	 * @return
	 */
	public static Map<String, Object> setMap(Object obj) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Method[] methods = obj.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				String mothodName = methods[i].getName();
				if (mothodName.startsWith("get")) {
					Object object = obj
							.getClass()
							.getMethod(mothodName,
									methods[i].getParameterTypes())
							.invoke(obj, new Object[] {});
					if (object != null) {
						Annotation[] annotations = methods[i].getAnnotations();
						String name = mothodName.substring(3,
								mothodName.length()).toLowerCase();
						if (annotations != null) {
							for (Annotation annotation : annotations) {
								if (annotation instanceof TrueAnnotation) {
									TrueAnnotation c = (TrueAnnotation) annotation;
									name = c.name();
								}
							}
							map.put(name, object);
						}
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 将javaBean 有的值放入map中
	 * 
	 * @param className
	 *            javabean的class
	 * @param obj
	 *            javaBean对象
	 * @return
	 */
	public static String[] getColumn(Class<?> className) {

		List<String> list = new ArrayList<String>();
		try {
			Method[] methods = className.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				String mothodName = methods[i].getName();
				if (mothodName.startsWith("get")) {
					Annotation[] annotations = methods[i].getAnnotations();
					if (annotations != null) {
						for (Annotation annotation : annotations) {
							if (annotation instanceof TrueAnnotation) {
								TrueAnnotation c = (TrueAnnotation) annotation;
								list.add(c.name());
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		String[] column = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			column[i] = list.get(i);
		}
		return column;
	}

	/**
	 * 获取class构造器上的属性
	 * 
	 * @param obj
	 * @return
	 */
	public static String getTableName(Class<?> className) {

		String tableName = "";
		if (className != null) {
			Annotation[] annotations = className.getAnnotations();
			if (annotations != null) {
				for (Annotation annotation : annotations) {
					if (annotation instanceof TrueAnnotation) {
						TrueAnnotation c = (TrueAnnotation) annotation;
						tableName = c.name();
					}
				}
			}
			if (!Utils.isNotNullOrEmpty(tableName)) {
				tableName = className.getSimpleName();
			}
		}
		return tableName;
	}

	/**
	 * 获取class构造器上的属性
	 * 
	 * @param obj
	 * @return
	 */
	public static String getId(Class<?> className) {

		try {
			Method[] methods = className.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				String mothodName = methods[i].getName();
				if (mothodName.startsWith("get")) {
					Annotation[] annotations = methods[i].getAnnotations();
					if (annotations != null) {
						for (Annotation annotation : annotations) {
							if (annotation instanceof TrueAnnotation) {
								TrueAnnotation c = (TrueAnnotation) annotation;
								if (Utils.isNotNullOrEmpty(c.id())
										&& c.id().equals("id")) {
									return c.name();
								}
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
