package cn.com.trueway.archives.manage.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

public class ClobUtil {
	public static String ClobTostring(Clob clob) {
		if(null != clob) {
			String clobStr = "";
			Reader is = null;
			try {
				is = clob.getCharacterStream();
				BufferedReader br = new BufferedReader(is);
				String s = null;
				s = br.readLine();
				StringBuffer sb = new StringBuffer();
				while(s != null) {
					sb.append(s);
					s = br.readLine();
				}
				clobStr = sb.toString();
			} catch(IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return clobStr;
		} else {
			return null;
		}
	}
}
