package cn.com.trueway.archives.using.util;

import org.hibernate.lob.SerializableClob;

public class ChangeDataType {
	public String ClobToString(Object ob){
		if(ob instanceof SerializableClob){
			SerializableClob sc = (SerializableClob)ob; 
	        char[] buffer = null; 
			try { 
			            //根据CLOB长度创建字符数组 
			    buffer = new char[(int)sc.length()]; 
			            //获取CLOB的字符流Reader,并将内容读入到字符数组中 
			    sc.getCharacterStream().read(buffer); 
			} catch (Exception e) { 
				e.printStackTrace(); 
			} 
			return  String.valueOf(buffer);
		}else{
			return getSqlVal(ob);
		}
	}
	
	public String getSqlVal(Object obj) {
		String val = "";
		if (obj != null) {
			val = obj.toString();
		}
		return val;
	}
}
