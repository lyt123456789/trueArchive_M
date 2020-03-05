package cn.com.trueway.sys.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipFile;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil {
	private static Logger logger=Logger.getLogger(ZipUtil.class);
	/**
	 * 递归压缩文件夹
	 * @param srcRootDir 压缩文件夹根目录的子路径
	 * @param file 当前递归压缩的文件或目录对象
	 * @param zos 压缩文件存储对象
	 * @throws Exception
	 */
	private static void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception
	{
		if (file == null) 
		{
			return;
		}				
		
		//如果是文件，则直接压缩该文件
		if (file.isFile())
		{			
			int count, bufferLen = 1024;
			byte data[] = new byte[bufferLen];
			//获取文件相对于压缩文件夹根目录的子路径
			String subPath = file.getAbsolutePath();
			int index = subPath.indexOf(srcRootDir);
			if (index != -1) 
			{
				subPath = subPath.substring(srcRootDir.length() + File.separator.length());
			}
			ZipEntry entry = new ZipEntry(subPath);
			zos.putNextEntry(new ZipEntry(file.getName()));
			FileInputStream fis = new FileInputStream(file);
			//BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			while ((count = fis.read(data, 0, bufferLen)) != -1) 
			{
				zos.write(data, 0, count);
			}
			fis.close();
			zos.closeEntry();
		}
		//如果是目录，则压缩整个目录
		else 
		{
			//压缩目录中的文件或子目录
			File[] childFileList = file.listFiles();
			for (int n=0; n<childFileList.length; n++)
			{
				childFileList[n].getAbsolutePath().indexOf(file.getAbsolutePath());
				zip(srcRootDir, childFileList[n], zos);
			}
		}
	}
	
	/**
	 * 对文件或文件目录进行压缩
	 * @param srcPath 要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
	 * @param zipPath 压缩文件保存的路径。注意：zipPath不能是srcPath路径下的子文件夹
	 * @param zipFileName 压缩文件名
	 * @throws Exception
	 */
	public static void zip(String srcPath, String zipPath, String zipFileName) throws Exception
	{
		if (StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(zipPath) || StringUtils.isEmpty(zipFileName))
		{
			
		}
		CheckedOutputStream cos = null;
		ZipOutputStream zos = null;						
		try
		{
			File srcFile = new File(srcPath);
			
			//判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）
			if (srcFile.isDirectory() && zipPath.indexOf(srcPath)!=-1) 
			{
			}
			
			//判断压缩文件保存的路径是否存在，如果不存在，则创建目录
			File zipDir = new File(zipPath);
			if (!zipDir.exists() || !zipDir.isDirectory())
			{
				zipDir.mkdirs();
			}
			
			//创建压缩文件保存的文件对象
			String zipFilePath = zipPath + File.separator + zipFileName;
			File zipFile = new File(zipFilePath);			
			if (zipFile.exists())
			{
				//检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
				SecurityManager securityManager = new SecurityManager();
				securityManager.checkDelete(zipFilePath);
				//删除已存在的目标文件
				zipFile.delete();				
			}
			//cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
			zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
			
			//如果只是压缩一个文件，则需要截取该文件的父目录
			String srcRootDir = srcPath;
			if (srcFile.isFile())
			{
				int index = srcPath.lastIndexOf(File.separator);
				if (index != -1)
				{
					srcRootDir = srcPath.substring(0, index);
				}
			}
			//调用递归压缩方法进行目录或文件压缩
			zip(srcRootDir, srcFile, zos);
			zos.flush();
		}
		catch (Exception e) 
		{
			throw e;
		}
		finally 
		{			
			try
			{
				if (zos != null)
				{
					zos.close();
				}				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}			
		}
	}
	
	/**
	 * 解压缩zip包
	 * @param zipFilePath zip文件的全路径
	 * @param unzipFilePath 解压后的文件保存的路径
	 * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
	 */
	@SuppressWarnings("unchecked")
	public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception
	{
		if (StringUtils.isEmpty(zipFilePath) || StringUtils.isEmpty(unzipFilePath))
		{
		}
		File zipFile = new File(zipFilePath);
		//如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
		if (includeZipFileName)
		{
			String fileName = zipFile.getName();
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			unzipFilePath = unzipFilePath + File.separator + fileName;
		}
		//创建解压缩文件保存的路径
		File unzipFileDir = new File(unzipFilePath);
		if (!unzipFileDir.exists() || !unzipFileDir.isDirectory())
		{
			unzipFileDir.mkdirs();
		}
		
		//开始解压
		ZipEntry entry = null;
		String entryFilePath = null, entryDirPath = null;
		File entryFile = null, entryDir = null;
		int index = 0, count = 0, bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ZipFile zip = new ZipFile(zipFile);
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zip.entries();
		//循环对压缩包里的每一个文件进行解压		
		while(entries.hasMoreElements())
		{
			entry = entries.nextElement();
			//构建压缩包中一个文件解压后保存的文件全路径
			entryFilePath = unzipFilePath + File.separator + entry.getName();
			//构建解压后保存的文件夹路径
			index = entryFilePath.lastIndexOf(File.separator);
			if (index != -1)
			{
				entryDirPath = entryFilePath.substring(0, index);
			}
			else
			{
				entryDirPath = "";
			}			
			entryDir = new File(entryDirPath);
			//如果文件夹路径不存在，则创建文件夹
			if (!entryDir.exists() || !entryDir.isDirectory())
			{
				entryDir.mkdirs();
			}
			
			//创建解压文件
			entryFile = new File(entryFilePath);
			if (entryFile.exists())
			{
				//检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
				SecurityManager securityManager = new SecurityManager();
				securityManager.checkDelete(entryFilePath);
				//删除已存在的目标文件
				entryFile.delete();	
			}
			
			//写入文件
			bos = new BufferedOutputStream(new FileOutputStream(entryFile));
			bis = new BufferedInputStream(zip.getInputStream(entry));
			while ((count = bis.read(buffer, 0, bufferSize)) != -1)
			{
				bos.write(buffer, 0, count);
			}
			bos.flush();
			bos.close();			
		}
	}
	
	/**
	 * 
	 * 描述：递归删除目录下的所有文件及子目录下所有文件
	 * @param dir
	 * @return boolean
	 * 作者:刘钰冬
	 * 创建时间:2016-9-14 下午2:42:43
	 */
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
        	String[] children = dir.list();
        	//递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
	
	/**
	 * 
	 * 描述：删除空目录
	 * @param dir void
	 * 作者:刘钰冬
	 * 创建时间:2016-9-14 下午2:42:19
	 */
	public static void doDeleteEmptyDir(String dir) {
		boolean success = (new File(dir)).delete();
		if (success) {
			System.out.println("Successfully deleted empty directory: " + dir);
		} else {
			System.out.println("Failed to delete empty directory: " + dir);
		}
	}
	
	public static void zipFiles(String filePath, String descDir) {
		ZipOutputStream zos = null;

		try {
			// 创建一个Zip输出流
			zos = new ZipOutputStream(new FileOutputStream(descDir));
			// 启动压缩
			startZip(zos, "", filePath);

			// System.out.println("******************压缩完毕********************");
		} catch (IOException e) {
			// 压缩失败，则删除创建的文件
			File zipFile = new File(descDir);

			if (zipFile.exists()) {
				zipFile.delete();
			}
			// System.out.println("******************压缩失败********************");
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @description：对目录中所有文件递归遍历进行压缩
	 * @param zos
	 *            ZIP压缩输出流
	 * @param oppositePath
	 *            在zip文件中的相对路径
	 * @param directory
	 *            要压缩的文件的路径
	 * @throws IOException
	 */
	private static void startZip(ZipOutputStream zos, String oppositePath,
			String directory) throws IOException {
		File file = new File(directory);

		if (file.isDirectory()) {
			// 如果是压缩目录
			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {
				File aFile = files[i];

				if (aFile.isDirectory()) {
					// 如果是目录，修改相对地址
					String newOppositePath = oppositePath + aFile.getName()
							+ "/";
					// 压缩目录，这是关键，创建一个目录的条目时，需要在目录名后面加多一个"/"
					ZipEntry entry = new ZipEntry(oppositePath
							+ aFile.getName() + "/");

					zos.putNextEntry(entry);

					zos.closeEntry();
					// 进行递归调用
					startZip(zos, newOppositePath, aFile.getPath());
				} else {
					// 如果不是目录，则进行压缩
					zipFile(zos, oppositePath, aFile);
				}
			}
		} else {
			// 如果是压缩文件，直接调用压缩方法进行压缩
			zipFile(zos, oppositePath, file);
		}
	}
	
	/**
	 * 
	 * @description：压缩单个文件到目录中
	 * @param zos
	 *            zip输出流
	 * @param oppositePath
	 *            在zip文件中的相对路径
	 * @param file
	 *            要压缩的的文件
	 */
	private static void zipFile(ZipOutputStream zos, String oppositePath,
			File file) {
		// 创建一个Zip条目，每个Zip条目都是必须相对于根路径
		InputStream is = null;

		try {
			ZipEntry entry = new ZipEntry(oppositePath + file.getName());
			// 将条目保存到Zip压缩文件当中
			zos.putNextEntry(entry);
			// 从文件输入流当中读取数据，并将数据写到输出流当中.
			is = new FileInputStream(file);

			int length = 0;

			int bufferSize = 1024;

			byte[] buffer = new byte[bufferSize];

			while ((length = is.read(buffer, 0, bufferSize)) >= 0) {
				zos.write(buffer, 0, length);
			}
			zos.closeEntry();
		} catch (IOException ex) {
			logger.error(ex.getCause().getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException ex) {
				logger.error(ex.getCause().getMessage());
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @description：解压文件操作
	 * @param zipFilePath
	 *            zip文件路径
	 * @param descDir
	 *            解压出来的文件保存的目录
	 * @throws IOException
	 */
	public static void unZipFiles(String zipFilePath, String descDir) {
		File zipFile = new File(zipFilePath);

		File pathFile = new File(descDir);

		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = null;

		InputStream in = null;

		OutputStream out = null;

		try {
			zip = new ZipFile(zipFile);
			
			for (Enumeration<? extends java.util.zip.ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
				java.util.zip.ZipEntry entry = entries.nextElement();

				String zipEntryName = entry.getName();

				in = zip.getInputStream(entry);

				String outPath = (descDir + "/" + zipEntryName).replaceAll("\\*", "/");
				// 判断路径是否存在,不存在则创建文件路径
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				if (!file.exists()) {
					file.mkdirs();
				}
				// 判断文件全路径是否为文件夹,如果是上面已经创建,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}
				out = new FileOutputStream(outPath);

				byte[] buf1 = new byte[4 * 1024];

				int len;

				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();

				out.close();
			}
			// System.out.println("******************解压完毕********************");
		} catch (IOException e) {
			logger.error(e.getMessage());
			pathFile.delete();
			// System.out.println("******************解压失败********************");
			e.printStackTrace();
		} finally {
			try {
				if (zip != null) {
					zip.close();
				}
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) 
	{
		String zipPath = "F:\\workflow\\download";
		String dir = "F:\\workflow\\download\\123\\";
		String zipFileName = "test1234565.zip";
		try
		{
			zip(dir, zipPath, zipFileName);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}