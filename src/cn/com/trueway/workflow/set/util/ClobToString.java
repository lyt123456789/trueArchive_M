package cn.com.trueway.workflow.set.util;


import java.io.Reader;

import org.hibernate.lob.SerializableClob;

import oracle.sql.CLOB;

/**
 * @描述 将orcale数据库中的clob字段转化为String类型
 * @作者 未知
 * @时间 未知
 * 
 * @编辑描述 未知
 * @编辑人 范吉锋
 * @编辑时间 2012年8月18日, PM 03:05:58
 */
public class ClobToString {
	public static String clob2String(SerializableClob szclob) {
		CLOB clob = (CLOB) szclob.getWrappedClob();
		if (clob == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer(65535);// 64K
		Reader clobStream = null;
		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];// 每次获取60K
			int i = 0;
			while ((i = clobStream.read(b)) != -1) {
				sb.append(b, 0, i);
			}
		} catch (Exception ex) {
			sb = null;
		} finally {
			try {
				if (clobStream != null)
					clobStream.close();
			} catch (Exception e) {
			}
		}
		if (sb == null)
			return null;
		else
			return sb.toString();
	}

	public static String clob2String(CLOB clob) {
		if (clob == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer(65535);// 64K
		Reader clobStream = null;
		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];// 每次获取60K
			int i = 0;
			while ((i = clobStream.read(b)) != -1) {
				sb.append(b, 0, i);
			}
		} catch (Exception ex) {
			sb = null;
		} finally {
			try {
				if (clobStream != null)
					clobStream.close();
			} catch (Exception e) {
			}
		}
		if (sb == null)
			return null;
		else
			return sb.toString();
	}
}
