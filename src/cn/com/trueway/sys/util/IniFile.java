package cn.com.trueway.sys.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 描述：init文件实体类
 * 作者：刘钰冬
 * 创建时间：2016-8-15 上午10:57:43
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class IniFile {
	
	private static final Log LOG = LogFactory.getLog(IniFile.class);

	String[] iniLines = new String[1024];

	String iniFileName = null;
	
	InputStreamReader inputStreamReader;

	int c = 0;

	/**
	 * 
	 * @param fileName
	 *            完整的配置文件路径
	 */
	public IniFile(String fileName) {
		iniFileName = fileName;
		try {
			this.inputStreamReader = new InputStreamReader(new FileInputStream(iniFileName),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输入流
	 * @param inputStream
	 */
	public IniFile(InputStream inputStream) {
		try {
			this.inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置ini文件路径名称，比如："c:/config.ini"或者"/usr/.../conf.ini"。
	 * 
	 * @param fileName
	 *            完整的配置文件路径
	 */
	public void setFileName(String fileName) {
		iniFileName = fileName;
	}

	/**
	 * 返回ini文件路径名称。
	 * 
	 * @return String
	 */
	public String getFileName() {
		return iniFileName;
	}

	/**
	 * 向配置文件中写入配置内容，比如：username=admin,其中"username"为Key，"admin"为Value。
	 * 
	 * @param key
	 *            变量名称
	 * @param keyVal
	 *            值
	 */
	public void setIniValue(String key, String keyVal) {
		boolean lineFound = false;
		int lineNum = 0;
		while (lineNum <= c && iniLines[lineNum] != null) {

			if (!iniLines[lineNum].startsWith("#")) {

				iniLines[lineNum] = iniLines[lineNum].replaceFirst(" = ", "=");
				if (iniLines[lineNum].startsWith(key + "=")) {
					iniLines[lineNum] = key + "=" + keyVal;
					lineFound = true;
				}
			}
			lineNum++;
		}
		if (!lineFound)
			iniLines[lineNum] = key + "=" + keyVal;
	}

	/**
	 * 在配置文件中设置String型变量。
	 * 
	 * @param key
	 *            变量名称
	 * @param value
	 *            值
	 */
	public void setString(String key, String value) {
		setIniValue(key, value);
	}

	/**
	 * 在配置文件中设置boolean型变量。
	 * 
	 * @param key
	 *            变量名称
	 * @param value
	 *            值
	 */
	public void setBoolean(String key, boolean value) {
		setIniValue(key, Boolean.toString(value));
	}

	/**
	 * 在配置文件中设置int型变量。
	 * 
	 * @param key
	 *            变量名称
	 * @param value
	 *            值
	 */
	public void setInteger(String key, int value) {
		setIniValue(key, Integer.toString(value));
	}

	/**
	 * 从配置文件中读取配置内容
	 * 
	 * @param key
	 *            变量名称
	 * @return
	 */
	public String getIniValue(String key) {
		String rVal = null;
		String[] pair = new String[2];
		int lineNum = 0;
		while (lineNum <= c && iniLines[lineNum] != null) {
			if (!iniLines[lineNum].startsWith("#")) {
				iniLines[lineNum] = iniLines[lineNum].replaceFirst(" = ", "=");
				if (iniLines[lineNum].startsWith(key + "=")) {
					pair = iniLines[lineNum].split("=");
					if (pair.length == 2) {
						rVal = pair[1].trim();
						break;
					}
				}
			}
			lineNum++;
		}
		return rVal;
	}

	/**
	 * 从配置文件中读取String型变量。
	 * 
	 * @param key
	 *            变量名称
	 * @param def
	 *            默认值
	 * @return
	 */
	public String getString(String key, String def) {
		String iValue = "";
		String value = getIniValue(key);
		if (value != null) {
			iValue = value;
		} else {
			iValue = def;
		}
		return iValue;
	}

	/**
	 * 从配置文件中读取boolean型变量。
	 * 
	 * @param key
	 *            变量名称
	 * @param def
	 *            默认值
	 * @return
	 */
	public boolean getBoolean(String key, boolean def) {
		boolean iValue = def;
		String value = getIniValue(key);
		if (value != null) {
			try {
				iValue = Boolean.valueOf(value).booleanValue();
			} catch (Exception ex) {
			}
		}
		return iValue;
	}

	/**
	 * 从配置文件中读取int型变量。
	 * 
	 * @param key
	 *            变量名称
	 * @param def
	 *            默认值
	 * @return
	 */
	public int getInteger(String key, int def) {
		int iValue = def;
		String value = getIniValue(key);
		if (value != null) {
			try {
				iValue = Integer.parseInt(value);
			} catch (Exception ex) {
			}
		}
		return iValue;
	}

	/**
	 * 将配置文件打开，读取配置内容。 在setFileName(String fileName)方法之后调用，在读取配置内容前调用。
	 */
	public boolean readIni() {
		if (inputStreamReader != null) {
			try {
				BufferedReader  br=new BufferedReader(inputStreamReader); 
				String line = null;
				while (br.ready()) {
					line = br.readLine().trim();
					if (line.length() > 0) {
						iniLines[c] = line;
						c++;
					}
				}
				br.close();
				return true;

			} catch (FileNotFoundException fnf) {
				LOG.error("ERROR: Could not find file " + iniFileName);
				return false;
			} catch (IOException ioe) {
				LOG.error("ERROR: Error while reading file "
						+ iniFileName);
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 读文件,带字符集
	 * @param strCharType
	 * @return
	 */
	public boolean readIni( String strCharType) {
		strCharType = strCharType==null?"":strCharType;
		if( strCharType.length() == 0 )
			strCharType = "UTF-8";
		
		if (iniFileName != null) {
			try {

				BufferedReader  br=new BufferedReader(new InputStreamReader(new FileInputStream(iniFileName),strCharType)); 
				String line = null;
				while (br.ready()) {
					line = br.readLine().trim();
					if (line.length() > 0) {
						iniLines[c] = line;
						c++;
					}
				}
				br.close();
				return true;

			} catch (FileNotFoundException fnf) {
				LOG.error("ERROR: Could not find file " + iniFileName);
				return false;
			} catch (IOException ioe) {
				LOG.error("ERROR: Error while reading file "
						+ iniFileName);
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 将所有配置内容保存到到配置文件。
	 * 
	 * @return
	 */
	public boolean writeIni() {
		boolean rVal = false;
		try 
		{
			int lineNum = 0;
			String strContent = "";
			while (lineNum <= c && iniLines[lineNum] != null) {
				strContent += iniLines[lineNum];
				strContent += "\n";
				lineNum++;
			}
			FileUtils.writeStringToFile(new File(iniFileName), strContent, "UTF-8", false);
			rVal = true;
		} 
		catch (IOException ex) 
		{
			LOG.error("ERROR: Error while writing file " + iniFileName);
		}		
		return rVal;
	}

	/**
	 * 将所有配置内容保存到指定的配置文件。
	 * 
	 * @param fileName
	 *            配置文件路径名称
	 * @return
	 */

	public boolean writeIni(String fileName) {
		boolean rVal = false;
		try 
		{
			int lineNum = 0;
			String strContent = "";
			while (lineNum <= c && iniLines[lineNum] != null) {
				strContent += iniLines[lineNum];
				strContent += "\n";
				lineNum++;
			}
			FileUtils.writeStringToFile(new File(iniFileName), strContent, "UTF-8", false);
			rVal = true;
		} 
		catch (IOException ex) 
		{
			LOG.error("ERROR: Error while writing file " + iniFileName);
		}		
		return rVal;
	}
	public static void main( String[] args ){
		IniFile ini = new IniFile("D:/sdweb.ini");
		ini.readIni();
		String str = ini.getIniValue( "web1" );
		System.out.println( str );
		ini.setIniValue("web2", "南京省政府");
		ini.setIniValue("web1", "我的测试1");
		ini.writeIni();
		
		System.out.println( "web1=" + ini.getIniValue( "web1" ));
		System.out.println( "web2=" + ini.getIniValue( "web2" ));
	}
}