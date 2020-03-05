package cn.com.trueway.base.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import oracle.sql.CLOB;

import org.hibernate.JDBCException;
import org.springframework.jdbc.core.RowMapper;

/**
 * spring JDBC实体类装载器
 * 
 * @author guoj
 * 
 */
@SuppressWarnings("rawtypes")
public final class ActorMapper implements RowMapper {

	// 查询结果的列名（小写）
	private String[] values;

	// 返回的值的类型（0.返回Object,1.javabean，2：MAP）
	private int type = 0;

	// 常量字段，返回javabean的常量
	public static final int RESULT_JAVABEAN = 1;

	// 常量字段，返回map的常量
	public static final int RESULT_MAP = 2;

	// 常量字段，返回Object的常量
	public static final int RESULT_OBJECT = 3;

	private Class<?> resultBean;

	public Class<?> getResultBean() {

		return resultBean;
	}

	public void setResultBean(Class<?> resultBean) {

		this.resultBean = resultBean;
	}

	public String[] getValues() {

		return values;
	}

	public void setValues(String[] values) {

		this.values = values;
	}

	public int getType() {

		return type;
	}

	public void setType(int type) {

		this.type = type;
	}

	// 为values、classes、type、resultBean赋值
	public ActorMapper(String[] values, int type, Class<?> resultBean)
			throws JDBCException {

		if (values != null) {
			this.setValues(values);
		} else {
			this.setValues(ReflexUtils.getColumn(resultBean));
		}
		this.setType(type);
		if (resultBean != null) {
			this.setResultBean(resultBean);
		}
	}

	@Override
	public Object mapRow(ResultSet rs, int index) throws SQLException {

		if (type == ActorMapper.RESULT_MAP) {
			// 返回map
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < values.length; i++) {
				if (rs.getObject(values[i].toUpperCase()) instanceof CLOB) {
					CLOB clob = (CLOB) rs.getObject(values[i].toUpperCase());
					map.put(values[i].toLowerCase().replaceAll("_", ""),
							clob.getSubString(1, (int) clob.length()));
				} else if (rs.getObject(values[i].toUpperCase()) instanceof Date) {
					try {
						map.put(values[i].toLowerCase().replaceAll("_", ""),
								new Timestamp(new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss").parse(
										rs.getDate(values[i].toUpperCase())
												+ " "
												+ rs.getTime(values[i]
														.toUpperCase()))
										.getTime()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					map.put(values[i].toLowerCase().replaceAll("_", ""),
							rs.getObject(values[i].toUpperCase()));
				}
			}
			return map;
		} else if (type == ActorMapper.RESULT_JAVABEAN) {
			// 返回实体类
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < values.length; i++) {
				if (rs.getObject(values[i].toUpperCase()) instanceof Date) {
					try {
						map.put(values[i].toLowerCase().replaceAll("_", ""),
								new Timestamp(new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss").parse(
										rs.getDate(values[i].toUpperCase())
												+ " "
												+ rs.getTime(values[i]
														.toUpperCase()))
										.getTime()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					map.put(values[i].toLowerCase().replaceAll("_", ""),
							rs.getObject(values[i].toUpperCase()));
				}
			}
			return ReflexUtils.setBean(resultBean, map);
		} else {
			// 如果不是返回javabean或者map则直接返回OBJECT[]
			Object[] object = new Object[values.length];
			for (int i = 0; i < values.length; i++) {
				object[i] = rs.getObject(values[i].toUpperCase());
			}
			return object;
		}
	}
}
