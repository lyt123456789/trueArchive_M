package cn.com.trueway.workflow.tools.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.sun.star.io.IOException;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.HtmlToPdf;
import cn.com.trueway.base.util.MergePdf;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.PDFToTrue;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.set.util.PdfPage;
/**
 * 
 * 描述：提供的操作工具类
 * 作者：蔡亚军
 * 创建时间：2016-1-15 下午2:23:30
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class SupportToolsAction extends BaseAction{

	private static final long serialVersionUID = 3001254588953502148L;
	private File file; 
	private String fileFileName; 
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	/**
	 * 
	 * 描述：预览true文件
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2016-1-15 下午2:24:26
	 */
	
	public String showTrueFile() throws Exception{
		String path = new String(getRequest().getParameter("file2").getBytes("ISO-8859-1"),"UTF-8");
		path = URLDecoder.decode(path,"UTF-8");
		String orgFileName = path.substring(path.indexOf("//")+1);
		Employee employee = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
		if (null != employee){
			if(CommonUtil.stringNotNULL(path) && CommonUtil.stringNotNULL(orgFileName)){
				File file = new File(path);
				String filePath = uploadFile(file, orgFileName);
				if(!filePath.equals("")){
					int imageCount = PdfPage.getPdfPage(filePath);
					getRequest().setAttribute("imageCount", imageCount);
					String fileName = filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
					String pdfPath = serverUrl+ "/form/html/workflow/"+fileName;
					getRequest().setAttribute("pdfPath", pdfPath);
				}
			}
		}
		return "showTrueFile";
	}
	
	
	private String uploadFile(File uploadImg, String fileFileName) throws IOException {
		String end = fileFileName.substring(fileFileName.lastIndexOf("."),
				fileFileName.length());
		String savePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
		File file = new java.io.File(savePath);
		if (!file.exists()){
			file.mkdirs();
		}
		String time = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date()).toString();
		String fileName = "";
		if (fileFileName.indexOf(".") != -1) {
			fileName = time + end;
		} else {
			fileName = time;
		}
		File imageFile = new File(savePath + fileName);
		try{
			FileUploadUtils.copy(uploadImg, imageFile);
		}catch(Exception e){
			e.printStackTrace();
		}
		return savePath + fileName;
	}
	
	/**
	 * 
	 * 描述：上传临时附件
	 *
	 * @return
	 * @throws IOException String
	 *
	 * 作者:张灵<br>
	 * 创建时间:2016-1-22 上午11:11:53
	 */
	public String uploadTempAttsext() throws IOException{
		File attsextFile = this.getFile(); // 要上传的文件
		String uploadfilename = this.getFileFileName().replaceAll(" ", ""); // 上传文件的真实文件名要去空格，否则htmlToPdf方法无法执行
		if (null != attsextFile && attsextFile.exists()&&uploadfilename!=null){
			String dirlocal = SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+"/temp/"+getRequest().getParameter("emp"); // 得到上传文件在服务器上的基路径
			String filelocal = dirlocal+"/"+uploadfilename;
			File files = new File(dirlocal);
			if(!files.exists())
				files.mkdir();
			FileUploadUtils.copy(attsextFile, new File(filelocal));
			getResponse().setContentType("text/xml");
        	getResponse().setCharacterEncoding("GBK");
        	PrintWriter out = null;
			try {
				out = getResponse().getWriter();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
        	out.write("");
		}
		return null;
	}
	
	/**
	 * 
	 * 描述：文件下载（路径转码）
	 *
	 * @return
	 * @throws IOException String
	 *
	 * 作者:张灵<br>
	 * 创建时间:2016-1-22 上午11:11:53
	 */
	public String download() {
		String location = getRequest().getParameter("location");//下载路径含有中文
		
		FileInputStream fileinputstream = null;
		ServletOutputStream out = null;
		try {
			location = URLDecoder.decode(location,"UTF-8");
			String fileNameWithPath =SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+location;

			getResponse().setContentType("multipart/form-data");
			getResponse().setHeader("Content-Disposition", "attachment; filename="+ new String(location.substring(location.lastIndexOf("/")+1).getBytes("utf-8"),"ISO8859-1"));
			
			File file = new File(fileNameWithPath);
			fileinputstream = new FileInputStream(file);
			out = getResponse().getOutputStream();
			
			int len = -1;
			byte[] buffer = new byte[1024];
			while((len=fileinputstream.read(buffer))!=-1){
				out.write(buffer, 0, len);
			}
			if(fileinputstream!=null){
				fileinputstream.close();			
			}
			
		} catch (java.io.IOException e) {
			//Connection reset by peer: socket write error 不做处理
		}finally{
			if(out!=null){
				try {
					out.close();
					out.flush();
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * 描述：文件转换页面
	 * @return String
	 * 作者:张灵
	 * 创建时间:2016-1-22 下午2:27:05
	 */
	public String toTrueJsp(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		getRequest().setAttribute("emp", emp.getEmployeeGuid());	//以人员id作为文件转换暂存的附件关联号,作为临时文件夹名称
		return "toTrueJsp";
	}
	
	/**
	 * 
	 * 描述：文件转换
	 * @return String
	 * 作者:张灵
	 * 创建时间:2016-1-22 下午2:27:05
	 */	
	public void Switch(){
		String way = getRequest().getParameter("way");
		//获取文件转换任务列表
		File fileDir = new File(SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+"/temp/"+getRequest().getParameter("emp"));
		File[] fileFs = fileDir.listFiles();
		String[] files = new String[fileFs.length];
		//服务器转换后文件存储目录
		String newPath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+"/temp/"+"new"+getRequest().getParameter("emp")+"/";
		File newFile = new File(newPath);
		if(newFile.exists()){
			File[] newFiles = newFile.listFiles();
			for(File file:newFiles)
				file.delete();
		}
		else
			newFile.mkdir();
		//开始转换
		for(int i=0;i<fileFs.length;i++)
			files[i] = fileFs[i].getAbsolutePath();
		
		for(int i=0;i<files.length;i++){
			if("PDFtoTRUE".equals(way)){
				String oldpath = files[i];
				String newpath = fileFs[i].getName().replaceAll(".pdf", ".true");
				new PDFToTrue().pdfToTrue(oldpath, null, newPath+newpath);
			}else if("PDFtoPDF".equals(way)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
				Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
				new MergePdf().mergePdfFiles(files, newPath + sdf.format(new Date())+ "-" +emp.getEmployeeName()+".pdf");
				break;
			}else if("HTMLtoPDF".equals(way)){
				String oldpath = files[i];
				String newpath = fileFs[i].getName().replaceAll(".html", ".pdf");
				new HtmlToPdf().htmlToPdf(oldpath, newPath+newpath);  			
			}else{
				
			}
		}
		//删除材料文件
		File[] uploadFile = fileDir.listFiles();
		for(File file:uploadFile)
			file.delete();
		fileDir.delete();
		
		//下载地址
		File newFileDir = new File(newPath);
		String[] downloadPath = newFileDir.list();
		String download = "";
		for(int i=0;i<downloadPath.length;i++)
			download = download + "/temp/"+"new"+getRequest().getParameter("emp")+"/"+downloadPath[i] + ";";
				
		download = download.substring(0,download.length()-1);
		
		try {
			getResponse().getWriter().print(download);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		
	}
}
