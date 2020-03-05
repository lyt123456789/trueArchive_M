/**
 * 
 */
package cn.com.trueway.base.util;

import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

/** 
 * 描述：<对此类的描述，可以引用系统设计中的描述><br>                                     
 * 作者：吴新星<br>
 * 创建时间：Feb 2, 2010<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式:同创建时间><br>
 * 修改原因及地方： <修改原因描述><br>
 * 版本：v1.0 <br>           
 * JDK版本：JDK1.5
 */
public class UUID36GeneratorUtil implements IdentifierGenerator, Configurable {
	@SuppressWarnings("unused")
	private String sep = "";
	
	/* (non-Javadoc)
	 * @see org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.SessionImplementor, java.lang.Object)
	 */
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		//return new UUID36Generator().toString();
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.hibernate.id.Configurable#configure(org.hibernate.type.Type, java.util.Properties, org.hibernate.dialect.Dialect)
	 */
	public void configure(Type type, Properties params, Dialect dialect) throws MappingException {
		sep = PropertiesHelper.getString("separator", params, "");
	}
	

		
	


}
