package cn.com.trueway.base.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.trueway.workflow.core.pojo.Employee;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 关于文件上传操作等用到的工具类
 */
public class Utils {

	private static final int BUFFER_SIZE = 16 * 1024; // 上传文件的缓冲区大小

	private static Logger logger = Logger.getLogger(Utils.class);

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static boolean delFile(String filePathAndName) {

		File myDelFile = new java.io.File(filePathAndName);
		if (!myDelFile.exists()) {
			return true;
		}
		return myDelFile.delete();
	}

	/**
	 * 删除指定文件路径下面的所有文件和文件夹
	 * 
	 * @param file
	 */
	public static boolean delFiles(File file) {

		boolean flag = false;
		try {
			if (file.exists()) {
				if (file.isDirectory()) {
					String[] contents = file.list();
					for (int i = 0; i < contents.length; i++) {
						File file2X = new File(file.getAbsolutePath() + "/"
								+ contents[i]);
						if (file2X.exists()) {
							if (file2X.isFile()) {
								flag = file2X.delete();
							} else if (file2X.isDirectory()) {
								delFiles(file2X);
							}
						} else {
							throw new RuntimeException("File not exist!");
						}
					}
				}
				flag = file.delete();
			} else {
				throw new RuntimeException("File not exist!");
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 判断文件名是否已经存在，如果存在则在后面家(n)的形式返回新的文件名，否则返回原始文件名 例如：已经存在文件名 log4j.htm
	 * 则返回log4j(1).htm
	 * 
	 * @param fileName
	 *            文件名
	 * @param dir
	 *            判断的文件路径
	 * @return 判断后的文件名
	 */
	public static String checkFileName(String fileName, String dir) {

		boolean isDirectory = new File(dir + fileName).isDirectory();
		if (Utils.isFileExist(fileName, dir)) {
			int index = fileName.lastIndexOf(".");
			StringBuffer newFileName = new StringBuffer();
			String name = isDirectory ? fileName : fileName.substring(0, index);
			String extendName = isDirectory ? "" : fileName.substring(index);
			int nameNum = 1;
			while (true) {
				newFileName.append(name).append("(").append(nameNum)
						.append(")");
				if (!isDirectory) {
					newFileName.append(extendName);
				}
				if (Utils.isFileExist(newFileName.toString(), dir)) {
					nameNum++;
					newFileName = new StringBuffer();
					continue;
				}
				return newFileName.toString();
			}
		}
		return fileName;
	}

	/**
	 * 返回上传的结果，成功与否
	 * 
	 * @param uploadFileName
	 * @param savePath
	 * @param uploadFile
	 * @return
	 */
	public static boolean upload(String uploadFileName, String savePath,
			File uploadFile) {

		boolean flag = false;
		try {
			uploadForName(uploadFileName, savePath, uploadFile);
			flag = true;
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 上传文件并返回上传后的文件名
	 * 
	 * @param uploadFileName
	 *            被上传的文件名称
	 * @param savePath
	 *            文件的保存路径
	 * @param uploadFile
	 *            被上传的文件
	 * @return 成功与否
	 * @throws IOException
	 */
	public static String uploadForName(String uploadFileName, String savePath,
			File uploadFile) throws IOException {

		String newFileName = checkFileName(uploadFileName, savePath);
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(savePath + newFileName);
			fis = new FileInputStream(uploadFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return newFileName;
	}

	/**
	 * 根据路径创建一系列的目录
	 * 
	 * @param path
	 */
	public static boolean mkDirectory(String path) {

		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			file = null;
		}
		return false;
	}

	/**
	 * 将对象数组的每一个元素分别添加到指定集合中,调用Apache commons collections 中的方法
	 * 
	 * @param collection
	 *            目标集合对象
	 * @param arr
	 *            对象数组
	 */
	public static void addToCollection(Collection<?> collection, Object[] arr) {

		if (null != collection && null != arr) {
			CollectionUtils.addAll(collection, arr);
		}
	}

	/**
	 * 将字符串已多个分隔符拆分为数组,调用Apache commons lang 中的方法
	 * 
	 * <pre>
	 *  Example:
	 *  String[] arr = StringUtils.split(&quot;a b,c d,e-f&quot;, &quot; ,&quot;);
	 *  System.out.println(arr.length);//输出6
	 * </pre>
	 * 
	 * @param str
	 *            目标字符串
	 * @param separatorChars
	 *            分隔符字符串
	 * @return 字符串数组
	 */
	public static String[] split(String str, String separatorChars) {

		return StringUtils.split(str, separatorChars);
	}

	/**
	 * 调用指定字段的setter方法
	 * 
	 * <pre>
	 *               Example:
	 *                User user = new User();
	 *                MyUtils.invokeSetMethod(&quot;userName&quot;, user, new Object[] {&quot;张三&quot;});
	 * </pre>
	 * 
	 * @param fieldName
	 *            字段(属性)名称
	 * @param invokeObj
	 *            被调用方法的对象
	 * @param args
	 *            被调用方法的参数数组
	 * @return 成功与否
	 */
	public static boolean invokeSetMethod(String fieldName, Object invokeObj,
			Object[] args) {

		boolean flag = false;
		Field[] fields = invokeObj.getClass().getDeclaredFields(); // 获得对象实体类中所有定义的字段
		Method[] methods = invokeObj.getClass().getDeclaredMethods(); // 获得对象实体类中所有定义的方法
		for (Field f : fields) {
			String fname = f.getName();
			if (fname.equals(fieldName)) {// 找到要更新的字段名
				String mname = "set"
						+ (fname.substring(0, 1).toUpperCase() + fname
								.substring(1));// 组建setter方法
				for (Method m : methods) {
					String name = m.getName();
					if (mname.equals(name)) {
						// 处理Integer参数
						if (f.getType().getSimpleName()
								.equalsIgnoreCase("integer")
								&& args.length > 0) {
							args[0] = Integer.valueOf(args[0].toString());
						}
						// 处理Boolean参数
						if (f.getType().getSimpleName()
								.equalsIgnoreCase("boolean")
								&& args.length > 0) {
							args[0] = Boolean.valueOf(args[0].toString());
						}
						try {
							m.invoke(invokeObj, args);
							flag = true;
						} catch (IllegalArgumentException e) {
							flag = false;
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							flag = false;
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							flag = false;
							e.printStackTrace();
						}
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @param dir
	 * @return
	 */
	public static boolean isFileExist(String fileName, String dir) {

		File files = new File(dir + fileName);
		return (files.exists()) ? true : false;
	}

	/**
	 * 获得随机文件名,保证在同一个文件夹下不同名
	 * 
	 * @param fileName
	 * @param dir
	 * @return
	 */
	public static String getRandomName(String fileName, String dir) {

		String[] split = fileName.split("\\.");// 将文件名已.的形式拆分
		String extendFile = "." + split[split.length - 1].toLowerCase(); // 获文件的有效后缀

		Random random = new Random();
		int add = random.nextInt(1000000); // 产生随机数10000以内
		String ret = add + extendFile;
		while (isFileExist(ret, dir)) {
			add = random.nextInt(1000000);
			ret = fileName + add + extendFile;
		}
		return ret;
	}

	/**
	 * 创建缩略图
	 * 
	 * @param file
	 *            上传的文件流
	 * @param height
	 *            最小的尺寸
	 * @throws IOException
	 */
	public static void createMiniPic(File file, float width, float height)
			throws IOException {

		Image src = javax.imageio.ImageIO.read(file); // 构造Image对象
		int old_w = src.getWidth(null); // 得到源图宽
		int old_h = src.getHeight(null);
		int new_w = 0;
		int new_h = 0; // 得到源图长
		float tempdouble;
		if (old_w >= old_h) {
			tempdouble = old_w / width;
		} else {
			tempdouble = old_h / height;
		}

		if (old_w >= width || old_h >= height) { // 如果文件小于锁略图的尺寸则复制即可
			new_w = Math.round(old_w / tempdouble);
			new_h = Math.round(old_h / tempdouble);// 计算新图长宽
			while (new_w > width && new_h > height) {
				if (new_w > width) {
					tempdouble = new_w / width;
					new_w = Math.round(new_w / tempdouble);
					new_h = Math.round(new_h / tempdouble);
				}
				if (new_h > height) {
					tempdouble = new_h / height;
					new_w = Math.round(new_w / tempdouble);
					new_h = Math.round(new_h / tempdouble);
				}
			}
			BufferedImage tag = new BufferedImage(new_w, new_h,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src, 0, 0, new_w, new_h, null); // 绘制缩小后的图
			FileOutputStream newimage = new FileOutputStream(file); // 输出到文件流
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
			param.setQuality((float) (100 / 100.0), true);// 设置图片质量,100最大,默认70
			encoder.encode(tag, param);
			encoder.encode(tag); // 将JPEG编码
			newimage.close();
		}
	}

	/**
	 * 判断文件类型是否是合法的,就是判断allowTypes中是否包含contentType
	 * 
	 * @param contentType
	 *            文件类型
	 * @param allowTypes
	 *            文件类型列表
	 * @return 是否合法
	 */
	public static boolean isValid(String contentType, String[] allowTypes) {

		if (null == contentType || "".equals(contentType)) {
			return false;
		}
		for (String type : allowTypes) {
			if (contentType.equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 自己封装的一个把源文件对象复制成目标文件对象,以完成文件上传
	 * 
	 */
	public static void copy(File src, File dst) {

		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 自己封装的计算上传文件的大小并格式化 以设置为文件属性
	 */
	public static String formatFileLength(long size) {

		Double a;
		a = Double.valueOf(size);
		StringBuffer sb = new StringBuffer();
		java.text.DecimalFormat myformat = new java.text.DecimalFormat("#0.00");
		if (a >= 1024 * 1024 * 1024) {
			a = a / 1024 / 1024 / 1024 * 1.00;
			sb.append(myformat.format(a));
			sb.append(" GB");
		} else if (a >= 1024 * 1024 && a < 1024 * 1024 * 1024) {
			a = a / 1024 / 1024 * 1.00;
			sb.append(myformat.format(a));
			sb.append(" MB");
		} else if (a >= 1024 && a < 1024 * 1024) {
			a = a / 1024 * 1.00;
			sb.append(myformat.format(a));
			sb.append(" KB");
		} else {
			sb.append(myformat.format(a));
			sb.append(" 字节");
		}

		return sb.toString();
	}

	/**
	 * 根据文件名得到文件的后缀名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtension(String filename) {

		int i = filename.lastIndexOf(".");
		if ((i > -1) && (i < (filename.length() - 1))) {
			return filename.substring(i + 1);
		} else {
			return "data";
		}
	}

	/**
	 * 根据文件名 基路径 得到上传文件在服务器上存储的唯一路径,并创建存储目录
	 * 
	 * @param filename
	 * @param basePath
	 * @return
	 */
	public static String getRealFilePath(String fileName, String basePath) {

		String uuid = UuidGenerator.generate36UUID();
		String fileType = getExtension(fileName);
		String fileName_UUID = uuid.toString() + "." + fileType;// 得到以唯一标识符重命名的文件名

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + File.separator
				+ "MM" + File.separator + "dd" + File.separator);

		String rq = sdf.format(date);
		int j = basePath.lastIndexOf(File.separator);

		// 如果基路径不以"\\"结尾,则添加上
		if ((j > -1) && (j != basePath.length() - 1)) {
			basePath = basePath + File.separator;
		}

		mkDirectory(basePath + rq); // 根据路径创建一系列的目录

		String realFilePath = rq + fileName_UUID; // 得到在服务器上存储的唯一路径
		logger.info(realFilePath);

		return realFilePath;
	}

	/**
	 * 将 str 的后 index 位 --> *
	 * 
	 * @param str
	 * @param index
	 * @return
	 */
	public static String hiddenStr(String str, int index) {

		if (str == null || "".equals(str) || index <= 0 || str.length() < index) {
			return "*****";
		}

		StringBuffer sb = new StringBuffer(str.substring(0, str.length()
				- index));
		for (int i = 0; i < index; i++) {
			sb.append("*");
		}

		return sb.toString();
	}

	/**
	 * @author 何登
	 * @time 2011-12-20 <br>
	 * @功能作用 获取SpringCtx对象
	 * */
	public static Object getService(String serviceName) {

		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext
						.getRequest().getSession().getServletContext());
		try {
			Object obj = wac.getBean(serviceName);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author 何登
	 * @time 2011-12-18 <br>
	 * @功能作用 从Session 中获取当前登录用户信息
	 * */
	public static Employee getemployee() {

		HttpSession session = ServletActionContext.getRequest().getSession();
		Employee employee = (Employee) session.getAttribute("publisher");
		return employee;
	}

	// 将实体属性加入Sql查询 (模糊查询)
	public static String getSql(Class<?> entity, String value) {

		StringBuffer hql = new StringBuffer(" and (");
		Field[] file = entity.getDeclaredFields();
		for (int i = 0; i < file.length; i++) {
			hql.append("  t." + file[i].getName() + " like '%" + value.trim()
					+ "%' or ");
		}
		hql.delete(hql.length() - 3, hql.length());
		hql.append(" )");
		return hql.toString();
	}

	public static String parseClob(Object clobObj) throws Exception {

		StringBuffer sb = new StringBuffer();

		if (clobObj == null) {
			return "无";
		}

		if (clobObj instanceof Clob) {
			Clob clob = (Clob) clobObj;
			Reader reader = clob.getCharacterStream();
			char[] charbuf = new char[4096];
			for (int i = reader.read(charbuf); i > 0; i = reader.read(charbuf)) {
				sb.append(charbuf, 0, i);
			}
		} else {
			sb.append(clobObj);
		}
		return sb.toString();
	}

	public static String parseClobBG(Object clobObj) throws Exception {

		StringBuffer sb = new StringBuffer();

		if (clobObj == null) {
			return "";
		}

		if (clobObj instanceof Clob) {
			Clob clob = (Clob) clobObj;
			Reader reader = clob.getCharacterStream();
			char[] charbuf = new char[4096];
			for (int i = reader.read(charbuf); i > 0; i = reader.read(charbuf)) {
				sb.append(charbuf, 0, i);
			}
		} else {
			sb.append(clobObj);
		}
		return sb.toString();
	}

	public static Object parseObject(Object obj) {

		if (obj == null) {
			return "";
		}
		return obj;
	}

	public static boolean isNotNullOrEmpty(String s) {

		if (null == s) {
			return false;
		}
		if (s.trim().equals("")) {
			return false;
		}
		if (s.trim().equals("null")) {
			return false;
		}
		return true;
	}

	/**
	 * 处理回车符 , ; trs
	 */
	public static String parsePersonalEnter(String content) {

		String newContent = content.substring(content.indexOf("姓名"));

		return newContent.replace("\n", "").replace(";", "").trim();
	}

	/**
	 * 处理 trs
	 */
	public static String parseContent(String content, String begin, String end,
			int index) {

		String newContent = "";

		int beginIndex = content.indexOf(begin);
		int endIndex = 0;

		if ("".equals(end)) {
			endIndex = content.length();
		} else {
			endIndex = content.indexOf(end);
		}

		newContent = content.substring(beginIndex + index, endIndex);

		return newContent.trim();
	}

	/**
	 * 将String转成Clob ,静态方法
	 * 
	 * @param str
	 *            字段
	 * @return clob对象，如果出现错误，返回 null
	 */
	public static Clob stringToClob(String str) {

		if (null == str)
			return null;
		else {
			try {
				java.sql.Clob c = new javax.sql.rowset.serial.SerialClob(
						str.toCharArray());
				return c;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 将Clob转成String ,静态方法
	 * 
	 * @param clob
	 *            字段
	 * @return 内容字串，如果出现错误，返回 null
	 */
	public static String clobToString(Clob clob) {

		if (clob == null)
			return null;
		StringBuffer sb = new StringBuffer();
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
				if (clobStream != null) {
					clobStream.close();
				}
			} catch (Exception e) {
			}
		}
		if (sb == null)
			return null;
		else
			return sb.toString();
	}

	/**
	 * 验证是数字
	 * 
	 * @param wcsx
	 * @return
	 */
	public static boolean isNumber(String wcsx) {

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(wcsx);
		return matcher.matches();
	}
	
	//把byte转换成int
	public static int getIntValue(byte[] b,int s,int e){
		int nr=0;
		if(s+3<e)
		{
			nr =b[s]&0xff;
			nr =nr+(b[s+1]<<8&0x0000ff00);
			nr =nr+(b[s+2]<<16&0x00ff0000);
			nr =nr+(b[s+3]<<24&0xff000000);
		}
		return nr;
	}
	
	//把int转换成byte[]
	public static byte[] getByteValue(int n) {
		byte[] date = new byte[4];
		date[0]=(byte) (n&0xff);
		date[1]=(byte) (n>>8&0xff);
		date[2]=(byte) (n>>16&0xff);
		date[3]=(byte) (n>>24&0xff);
		return date;
	}
	
	//byte合并
	public static byte[] addByte(byte[] data,byte[] datas){
		byte[] returnByte = new byte[data.length+datas.length];
		System.arraycopy(data, 0, returnByte, 0, data.length);
		System.arraycopy(datas, 0, returnByte, data.length, datas.length);
		return returnByte;
	}
	
	//byte截取
	public static byte[] cutByte(byte[] data,int begin,int length){
		byte[] datas = new byte[length];
		for(int z=0;z<length;z++){
			datas[z]=data[begin+z];
		}
		return datas;
	}
}
