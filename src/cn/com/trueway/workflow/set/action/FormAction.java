/**
 * 文件名称:WorkflowCalendarAction.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-30 下午07:53:55
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.workflow.set.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.FileUtils;
import cn.com.trueway.base.util.HtmlToPdf;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.PDFToTrue;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.UrlCatcher;
import cn.com.trueway.workflow.core.action.thread.PDFToPNGImageThread;
import cn.com.trueway.workflow.core.pojo.WfDictionary;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.pojo.WfTemplate;
import cn.com.trueway.workflow.core.service.DictionaryService;
import cn.com.trueway.workflow.core.service.FieldInfoService;
import cn.com.trueway.workflow.core.service.FlowService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.set.pojo.FormStyle;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.ServerPlugin;
import cn.com.trueway.workflow.set.pojo.TagBean;
import cn.com.trueway.workflow.set.pojo.Trueform;
import cn.com.trueway.workflow.set.pojo.WebJson;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.ServerPluginService;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
import cn.com.trueway.workflow.set.util.PdfToPngImageUtil;

import com.google.gson.Gson;


/**
 * 描述：工作日历
 * 作者：PanH<br>
 * 创建时间：2011-12-30 下午07:53:55<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class FormAction extends BaseAction{
	private ZwkjFormService zwkjFormService;
	private ZwkjForm zwkjForm;
	private TableInfoService tableInfoService;
	private FieldInfoService fieldInfoService;
	private DictionaryService dictionaryService;
	private ServerPluginService serverPluginService;
	private ServerPluginAction serverPluginAction;
	
	
	private FlowService flowService ; 
	
	public FlowService getFlowService() {
		return flowService;
	}
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	public ServerPluginAction getServerPluginAction() {
		return serverPluginAction;
	}
	public void setServerPluginAction(ServerPluginAction serverPluginAction) {
		this.serverPluginAction = serverPluginAction;
	}
	public ServerPluginService getServerPluginService() {
		return serverPluginService;
	}
	public void setServerPluginService(ServerPluginService serverPluginService) {
		this.serverPluginService = serverPluginService;
	}
	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}
	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}
	public FieldInfoService getFieldInfoService() {
		return fieldInfoService;
	}
	public void setFieldInfoService(FieldInfoService fieldInfoService) {
		this.fieldInfoService = fieldInfoService;
	}
	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}
	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}
	
	public ZwkjForm getZwkjForm() {
		return zwkjForm;
	}
	public void setZwkjForm(ZwkjForm zwkjForm) {
		this.zwkjForm = zwkjForm;
	}
	private static final long serialVersionUID = -1108771982823434566L;
	/**
	 * 
	 * @Title: stringNotNULL
	 * @Description: 工具，判断字符非空
	 * @param s
	 * @return boolean    返回类型
	 */
	public boolean stringNotNULL(String s){
		if (s!=null&&!s.equals("")) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @Title: stringIsNULL
	 * @Description: 工具，判断字符为空
	 * @param s
	 * @return boolean    返回类型
	 */
	public boolean stringIsNULL(String s){
		if (s==null||s.equals("")) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @Title: toAddFormJsp
	 * @Description: 转向新增form表单的jsp页面 void    返回类型
	 */
	public String toAddFormJsp(){
		String workflowId = getRequest().getParameter("workflowId");
		getRequest().setAttribute("workflowId", workflowId);
		return "toAddFormJsp";
	}
	
	/**
	 * 
	 * @Title: toAddFormJsp
	 * @Description: 转向新增form表单的jsp页面 void    返回类型
	 */
	public String toAddFormJspByCode(){
		String workflowId = getRequest().getParameter("workflowId");
		getRequest().setAttribute("workflowId", workflowId);
		return "toAddFormJspByCode";
	}

	/**
	 * 
	 * @Title: selectTable
	 * @Description: 选择入库表
	 */
	public String selectTable(){
		String workflowid = this.getRequest().getParameter("workflowId");
		if(CommonUtil.stringIsNULL(workflowid)){
			workflowid=getSession().getAttribute(MyConstants.workflow_session_id)!=null?getSession().getAttribute(MyConstants.workflow_session_id).toString():null;
		}
		List<WfTableInfo> tableList=tableInfoService.getTableByLcid(workflowid);
		this.getRequest().setAttribute("tableList", tableList);
		return "selectTable";
	}
	
	/**
	 * 
	 * @Title: readHTML
	 * @Description: 根据html路径读取html流至页面,用于展示及改写
	 * @return String    返回类型
	 */
	public void readHTMLToString(){
		//传递创建的文件名称名，重复改写此文件
		String  filename=getRequest().getParameter("filename");
		String  jspfilename=getRequest().getParameter("jspfilename");
		String  formType=getRequest().getParameter("formType");//读取文件的类型html、jsp
		String allPath="";
		if (formType.equals("html")) {
			allPath=PathUtil.getWebRoot()+"form/html/"+filename;
		}else if (formType.equals("jsp")){
			allPath=PathUtil.getWebRoot()+"form/jsp/"+jspfilename;
		}
		File file = new File(allPath);
		StringBuilder sb = new StringBuilder();
		if(file.exists()){
			Scanner sc = null;
			try {
				sc = new Scanner(file,"UTF-8");
				while(sc.hasNextLine()){
					sb.append(sc.nextLine()+"\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if(sc!=null){
					sc.close();
				}
			}
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-3-2 下午5:22:10
	 */
	public String readHTMLToString4Code(){
		//传递创建的文件名称名，重复改写此文件
		String  filename=getRequest().getParameter("filename");
		getRequest().setAttribute("filename", filename);
		
		String  jspfilename=getRequest().getParameter("jspfilename");
		if (stringNotNULL(jspfilename)) {
			getRequest().setAttribute("jspfilename", jspfilename);
		}
		
		String  isview=getRequest().getParameter("isview");//是否只是查看
		if (stringNotNULL(isview)) {
			getRequest().setAttribute("isview", isview);
		}
		String  formType=getRequest().getParameter("formType");//读取文件的类型html、jsp
		if (stringNotNULL(formType)) {
			getRequest().setAttribute("formType", formType);
		}
		
		String allPath="";
		if (stringIsNULL(filename)) {
			allPath=PathUtil.getWebRoot()+"form/html/src/basic.html";
		}else{
			if (formType.equals("html")) {
				allPath=PathUtil.getWebRoot()+"form/html/"+filename;
			}else if (formType.equals("jsp")){
				allPath=PathUtil.getWebRoot()+"form/jsp/"+jspfilename;
			}
		}
		getRequest().setAttribute("htmlString", readHTML(allPath));
		
		return "readHTML";
	}
	

	/**
	 * 板式文件预览
	 * @return
	 */
	public String preview(){
		String  filename=getRequest().getParameter("filename");
		String  pdfPath= createBlankPdf(filename);
		PdfToPngImageUtil toimage = new PdfToPngImageUtil();
		toimage.PDFToPNGImage(pdfPath);
		getRequest().setAttribute("pdfPath", pdfPath);
		String json=getRequest().getParameter("json");
        JSONArray array = JSONArray.fromObject(json);
        List<Trueform> trueList = new  ArrayList<Trueform>();
        Trueform  trueform = null;
        for(int i = 0; i < array.size(); i++){ 
        	trueform = new Trueform();
        	trueform.setActionurl("");
        	trueform.setUrlHeight(0);
        	trueform.setUrlWidth(0);
        	trueform.setId("");
        	trueform.setName("");
        	trueform.setNodeId("");
        	trueform.setNodeName("");
        	trueform.setValue("");
        	trueform.setValues(new ArrayList());
        	JSONObject jsonObject = array.getJSONObject(i); 
        	Iterator it = jsonObject.keys();  
            while (it.hasNext()) {  
                 String key = (String) it.next();  
                 String value = jsonObject.getString(key); 
                 if(key!=null && key.equals("name")){
                	 trueform.setName(value);
                 }
                 if(key!=null && key.equals("startX")){
                	 trueform.setX(Integer.parseInt(value));
                 }
                 
                 if(key!=null && key.equals("startY")){
                	 trueform.setY(Integer.parseInt(value));
                 }
                 
                 if(key!=null && key.equals("type")){
                	 trueform.setType(value);
                 }
                 
                 if(key!=null && key.equals("width")){
                	 trueform.setWidth(Integer.parseInt(value));
                 }
                 
                 if(key!=null && key.equals("height")){
                	 trueform.setHeight(Integer.parseInt(value));
                 }
            } 
            if(!trueform.getType().equals("hidden")){
            	trueList.add(trueform);
            }
        } 
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		// 打开流
		WebJson outjson = new WebJson(trueList);
		String sb = JSONObject.fromObject(outjson).toString();
		getRequest().setAttribute("json", sb);
		return "preview";
	}
	
	
	/**
	 * 获取页面表单属性
	 */
	public void getColumnInfo(){
		String  filename=getRequest().getParameter("filename");
		String allPath="";
		if (stringIsNULL(filename)) {
			allPath=PathUtil.getWebRoot()+"form/html/src/basic.html";
		}else{
			allPath=PathUtil.getWebRoot()+"form/html/"+filename;
		}
		allPath= allPath.substring(1, allPath.length());
		// 从html流中获取所有的标签数据
		List<FormTagMapColumn> mapList = new ArrayList<FormTagMapColumn>();
		String htmlString = readHTML(allPath);// 源数据
		List<TagBean> tags = getTagFromHTMLString(htmlString);// 返回页面taglist
		if (tags != null) {
			for (int i = 0; i < tags.size(); i++) {
				FormTagMapColumn m = new FormTagMapColumn();
				m.setFormtagname(tags.get(i).getTagName());
				m.setFormtagtype(tags.get(i).getTagType());
				m.setSelectDic(tags.get(i).getSelect_dic());
				m.setListId(tags.get(i).getListId());
				m.setColumnCname(tags.get(i).getCommentDes());
				mapList.add(m);
			}
		}
		String sb = "";
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		// 打开流
		PrintWriter out;
		JSONArray json = JSONArray.fromObject(mapList);
		sb = json.toString();
		try {
			out = getResponse().getWriter();
			out.print(sb);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *创建空白pdf
	 */
	public String createBlankPdf(String filename){
//		String  filename=getRequest().getParameter("filename");
			String allPath="";
			if (stringIsNULL(filename)) {
				allPath=PathUtil.getWebRoot()+"form/html/src/basic.html";
			}else{
				allPath=PathUtil.getWebRoot()+"form/html/"+filename;
			}
			allPath= allPath.substring(1, allPath.length());
			// html转成pdf
			HtmlToPdf htp = new HtmlToPdf();
			String htmlpath = getPdfPath(allPath);
			htmlpath = htmlpath.substring(1, htmlpath.length());
			String pdfPath = htmlpath.substring(0, htmlpath.lastIndexOf("/")+1)+filename.substring(0, filename.length() - 4) + "pdf";
			if(new File(pdfPath).exists()){
				// 删除文件
				new File(pdfPath).delete();
			}
			htp.htmlToPdf(htmlpath, pdfPath);
			return pdfPath;
	}
	
	/**
	 * 将板式html转换为生成pdf的newHtml
	 * @param htmlpath
	 * @return
	 */
	public String getPdfPath(String htmlpath){
		Calendar calendar = Calendar.getInstance();
		// 生成一个新的html,用于存放值和生成pdf
		String newHtmlPath = PathUtil.getWebRoot() + "form/html/newInfo_"+ String.valueOf(calendar.getTimeInMillis()) + ".html";
		// 从html流中获取所有的标签数据
		List<FormTagMapColumn> mapList = new ArrayList<FormTagMapColumn>();
		String htmlString = readHTML(htmlpath);// 源数据
		List<TagBean> tags = getTagFromHTMLString(htmlString);// 返回页面taglist
		if (tags != null) {
			for (int i = 0; i < tags.size(); i++) {
				FormTagMapColumn m = new FormTagMapColumn();
				m.setFormtagname(tags.get(i).getTagName());
				m.setFormtagtype(tags.get(i).getTagType());
				m.setSelectDic(tags.get(i).getSelect_dic());
				m.setListId(tags.get(i).getListId());
				m.setColumnCname(tags.get(i).getCommentDes());
				mapList.add(m);
			}
		}
		getRequest().setAttribute("columnMapList", mapList);
		
		try {
			// 读取模板文件
			FileInputStream fileinputstream = new FileInputStream(htmlpath);
			// 下面四行：获得输入流的长度，然后建一个该长度的数组，然后把输入流中的数据以字节的形式读入到数组中，然后关闭流
			int length = fileinputstream.available();
			byte bytes[] = new byte[length];
			fileinputstream.read(bytes);
			fileinputstream.close();
			// 通过使用平台的默认字符集解码指定的 byte 数组，构造一个新的 String,
			// 然后利用字符串的replaceAll()方法进行指定字符的替换,此处除了这种方法之外，应该还可以使用表达式语言${}的方法来进行。
			// String start_en =
			// SystemParamConfigUtil.getParamValueByParam("start_en");
			// String templateContent = new String(bytes,"UTF-8");
			String templateContent = "";
			// if("0".equals(start_en)){
			templateContent = new String(bytes, "UTF-8");
			// htmlString = new String(bytes,"UTF-8");
			// }
			// if("1".equals(start_en)){
			// templateContent = new String(bytes,"GB2312");
			// htmlString = new String(bytes,"GB2312");
			// }
			templateContent =templateContent.replace("<html>", "<html><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />");

			// ------------------------------进行内容替换-------start----------------------------------------
			UrlCatcher u = new UrlCatcher();
			// 获取所有input类型标签
			String reg_input = "<INPUT[^<]*>";
			String[] inputs = u.getStringByRegEx(htmlString, reg_input, true);
			// 获取所有select类型标签
			String reg_select = "<SELECT[^<]*>(</select>)?";
			// String reg_select = "<SELECT.*?</select>";
			String[] selects = u.getStringByRegEx(htmlString, reg_select, true);
			// 获取所有textarea类型标签
			String reg_textarea = "<TEXTAREA[^<]*</TEXTAREA>";
			String[] textareas = u.getStringByRegEx(htmlString, reg_textarea,
					true);
			// 获取正文及附件类型的标签
			String reg_spanAtt = "<SPAN[^<]*</SPAN>";
			String[] spanAtts = u.getStringByRegEx(htmlString, reg_spanAtt,
					true);
			// 获取放意见标签的div
			String reg_divComment = "<trueway:comment[^<]*/>";
			String[] divComments = u.getStringByRegEx(htmlString,
					reg_divComment, true);
			// 把值填入html里

			// 替换值
			String req_checkbox = "<input .*?checkbox[^/]*?>.*?</input>";
			String[] checkboxs = u.getStringByRegEx(templateContent, req_checkbox, true);
			for (int k = 0; checkboxs!=null && k < checkboxs.length; k++) {
				templateContent = templateContent.replace(checkboxs[k], "");
			}
			String req_radio = "<input .*?radio[^/]*?>.*?</input>";
			String[] radios = u.getStringByRegEx(templateContent, req_radio, true);
			for (int k = 0; radios!=null && k < radios.length; k++) {
				templateContent = templateContent.replace(radios[k], "");
			}
			if(inputs!= null){
				for(int i = 0 ; i <inputs.length ; i ++ ){
					String valstr = "";
					Pattern p = Pattern.compile("width:(.*?)px",Pattern.DOTALL);
					Matcher m =  p.matcher(inputs[i]);
					if(m.find()){
						valstr = m.group(1);
					}
					int width = 120;
					if(valstr!=null && !valstr.equals("")){
						width = Integer.parseInt(valstr.trim());
					}
					templateContent = templateContent.replace(inputs[i],"<span style='width:"+width+"px;display:-moz-inline-box;display:inline-block;' ></span>");
			    }
			}
			if(selects!= null){
				for(int i = 0 ; i <selects.length ; i ++ ){
					templateContent = templateContent.replace(selects[i],"");
					}
			}
			
			if(textareas != null){
				for(int i = 0 ; i <textareas.length ; i ++ ){
					templateContent = templateContent.replace(textareas[i],"");
					}
			}
			// ----------------------------------进行内容替换--------end----------------------------------------
			// 使用平台的默认字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。
			// byte tag_bytes[] = templateContent.getBytes();
			byte[] tag_bytes = null;
			// if("0".equals(start_en)){
			// tag_bytes = templateContent.getBytes();
			// }
			// if("1".equals(start_en)){
			tag_bytes = templateContent.getBytes("utf-8");
			// }

			// System.out.println(tag_bytes);
			// byte tag_bytes[] = templateContent.getBytes("utf-8");
			FileOutputStream fileoutputstream = new FileOutputStream(
					newHtmlPath);// 建立文件输出流
			OutputStreamWriter osw = new OutputStreamWriter(fileoutputstream,
					"UTF-8");
			osw.write(templateContent);
			osw.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newHtmlPath;
	}
	
	/**
	 * 
	 * @Title: readHTMLToString
	 * @Description: 输入流 按行的方式读取文件为字符串用于teatarea中展示
	 * @param path
	 * @return String    返回类型
	 */
	public String readHTML(String path){
		StringBuffer htmlString=new StringBuffer();//返回页面流格式字符串
		//读取文件，用于展示
		File file=new File(path);
		BufferedReader reader=null;
		if (file.exists()) {
			try {
				reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),Charset.forName("UTF-8")));
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					htmlString.append(tempString+"\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (reader!=null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return htmlString.toString();
	}
	
	/**
	 * 
	 * @Title: saveStringToHTML
	 * @Description: 保存字符串至html
	 * @return String    返回类型
	 */
	public void saveStringToHTML(){
		String  htmlString=getRequest().getParameter("htmlString");
		String  filename=getRequest().getParameter("filename");
		if (filename==null||filename.equals("")) {
			filename=UUID.randomUUID()+".html";
		}
		if (stringNotNULL(htmlString)) {
			String allPath=PathUtil.getWebRoot()+"form/html/"+filename;
			writeHTML(htmlString,allPath,"utf-8");
			//getRequest().setAttribute("isok", writeHTML(htmlString,allPath,"utf-8"));
		}
		PrintWriter pw = null;
		try {
			pw = this.getResponse().getWriter();
			pw.write(filename);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
		//return "saveStringToHTML";
	}
	
	/**
	 * 
	 * @Title: saveStringToHTML
	 * @Description: 保存字符串至html
	 * @return String    返回类型
	 */
	public String saveStringToHTML4Code(){
		String  htmlString=getRequest().getParameter("htmlString");
		String  filename=getRequest().getParameter("filename");
		if (filename==null||filename.equals("")) {
			filename=UUID.randomUUID()+".html";
		}
		getRequest().setAttribute("filename", filename);
		boolean isok=false;//页面保存生成是否成功！
		if (stringNotNULL(htmlString)) {
			//htmlString=htmlString.replaceAll("\n", "");//替换因换行展示所需的特殊字符
			String allPath=PathUtil.getWebRoot()+"form/html/"+filename;
			// TODO
				getRequest().setAttribute("isok", writeHTML(htmlString,allPath,"utf-8"));
			
		}
		return "saveStringToHTML";
	}
	
	/**
	 * 
	 * @Title: writeHTML
	 * @Description: 根据字符串逐行写文件,写中文在tomcat中会出现乱码
	 * @return String    返回类型
	 */
	public boolean writeJsp(String str,String allPath) {
		File file=new File(allPath);
		if (!file.exists()) {
			//file.mkdir();//创建指定目录
			try {
				file.createNewFile();//创建文件
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (file.exists()) {
			BufferedWriter writer=null;
			try {
				writer=new BufferedWriter(new FileWriter(file));
				str=str.replaceAll("\r", "");//批量替换textarea中的字符串换行符\r(非常重要)
				String[] strs=str.split("\n");
				for (int i = 0; i < strs.length; i++) {
					writer.write(strs[i]);
					writer.newLine();
				}
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (writer!=null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @Title: writeHTML
	 * @Description: 根据字符串逐行写文件 
	 * 1、如果html使用utf-8编码流写入
	 *    tomcat conf web.xml中htl使用utf-8编码展示
	 * <mime-mapping>
        <extension>html</extension>
        <mime-type>text/html;charset=utf-8</mime-type>
      </mime-mapping>
      结果各种乱码
	 * 2、如果html使用gb2312编码流写入
	 * tomcat conf web.xml中htl使用gb2312编码展示
	 * <mime-mapping>
        <extension>html</extension>
        <mime-type>text/html;charset=gb2312</mime-type>
      </mime-mapping>
      结果html在tomcat应用中访问时不乱码(使用gb2312编码)，用浏览器直接访问时乱码(浏览器默认编码utf-8，html编码为gb2312)
	 * @return String    返回类型
	 */
	public boolean writeHTML(String str,String allPath,String encode) {
		File file=new File(allPath);
		FileOutputStream ops=null;
		OutputStreamWriter ow=null;
		BufferedWriter writer=null;
		if (!file.exists()) {
			//file.mkdir();//创建指定目录
			try {
				file.createNewFile();//创建文件
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		if (file.exists()) {
			try {
				ops=new FileOutputStream(file);
				ow=new OutputStreamWriter(ops,encode);
				writer=new BufferedWriter(ow);
				str=str.replaceAll("\r", "");//批量替换textarea中的字符串换行符\r(非常重要)
				String[] strs=str.split("\n");
				for (int i = 0; i < strs.length; i++) {
					writer.write(strs[i]);
					writer.newLine();
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					if (writer!=null) {
						writer.close();
					}
					if (ow!=null) {
						ow.close();
					}
					if (ops!=null) {
						ops.close();
					}
					if (file!=null) {
						file=null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return false;
	}
	/**
	 * 
	 * @Title: addForm
	 * @Description: 新增表单信息
	 * @return String    返回类型
	 * @throws IOException 
	 */
	public void addForm() throws IOException{
		//工作流id
		String workflowid = this.getRequest().getParameter("workflowId");
		if(CommonUtil.stringIsNULL(workflowid)){
			workflowid=getSession().getAttribute(MyConstants.workflow_session_id)!=null?getSession().getAttribute(MyConstants.workflow_session_id).toString():null;
		}
		zwkjForm.setId(null);//必须设置
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		zwkjForm.setIntime(Timestamp.valueOf(sdf.format(new Date())));
		//zwkjForm.setInperson("");
		zwkjForm.setWorkflowId(workflowid);
		String dstPath = getSpringContext().getServletContext().getRealPath("") + "/form/html/" + zwkjForm.getForm_htmlfilename();
		
		ZwkjForm form = zwkjFormService.addForm(zwkjForm);
		File file = new File(dstPath);
		if(file.exists()){
			zwkjFormService.updateForm(form.getId(), Hibernate.createBlob(new FileInputStream(file)));
		}
		getResponse().sendRedirect(getRequest().getContextPath()+"/form_getFormList.do?workflowId="+workflowid);
		//return getFormList();
	}
	/**
	 * @Title: getFormList
	 * @Description: 表单列表
	 * @return String    返回类型
	 */
	public String getFormList(){
		String workflowId = getRequest().getParameter("workflowId");
		if(CommonUtil.stringIsNULL(workflowId)){
			workflowId = getSession().getAttribute(MyConstants.workflow_session_id)!=null?getSession().getAttribute(MyConstants.workflow_session_id).toString():null;
		}
		List<ZwkjForm> list=zwkjFormService.getFormListByParamForPage("workflowId", workflowId, null, null);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("workflowId", workflowId);
		return "getFormList";
	}
	
	/**
	 * 
	 * 描述：根据流程id获取表单数量
	 * 作者:蔡亚军
	 * 创建时间:2016-12-28 上午9:25:31
	 */
	public void getFormCount(){
		String workflowId = getRequest().getParameter("workflowId");
		if(CommonUtil.stringIsNULL(workflowId)){
			workflowId = getSession().getAttribute(MyConstants.workflow_session_id)!=null?getSession().getAttribute(MyConstants.workflow_session_id).toString():null;
		}
		List<String> formIds = zwkjFormService.getFormListCountByParamForPage("workflowId", workflowId, null, null);
		JSONObject jo = new JSONObject();
		jo.put("count", formIds.size());
		jo.put("formIds", JSONArray.fromObject(formIds));
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		// 打开流
		PrintWriter out;
		try {
			out = getResponse().getWriter();
			out.print(jo.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Title: toUpdateFormJsp
	 * @Description: 转向新增表单jsp
	 * @return String    返回类型
	 */
	public String toUpdateFormJsp(){
		String id=getRequest().getParameter("formid");
		String workflowId = getRequest().getParameter("workflowId");
		ZwkjForm zwkjForm=zwkjFormService.getOneFormById(id);
		if(null != zwkjForm){
			String dstPath = getSpringContext().getServletContext().getRealPath("") + "/form/html/" + zwkjForm.getForm_htmlfilename();
			File file = new File(dstPath);
			if(!file.exists() && zwkjForm.getData() != null){
				FileUtils.byteArrayToFile(zwkjForm.getData(), dstPath);
			}
		}
		getRequest().setAttribute("zwkjForm", zwkjForm);
		getRequest().setAttribute("workflowId", workflowId);
		return "toUpdateFormJsp";
	}
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:蒋烽
	 * 创建时间:2017-3-2 下午5:02:50
	 */
	public String toUpdateFormJspByCode(){
		String id=getRequest().getParameter("formid");
		String workflowId = getRequest().getParameter("workflowId");
		ZwkjForm zwkjForm=zwkjFormService.getOneFormById(id);
		if(null != zwkjForm){
			String dstPath = getSpringContext().getServletContext().getRealPath("") + "/form/html/" + zwkjForm.getForm_htmlfilename();
			File file = new File(dstPath);
			if(!file.exists() && zwkjForm.getData() != null){
				FileUtils.byteArrayToFile(zwkjForm.getData(), dstPath);
			}
		}
		getRequest().setAttribute("zwkjForm", zwkjForm);
		getRequest().setAttribute("workflowId", workflowId);
		return "toUpdateFormJspByCode";
	}
	
	/**
	 * 
	 * @Title: updateForm
	 * @Description: 更新表单信息
	 * @return String    返回类型
	 * @throws IOException 
	 */
	public void updateForm() throws IOException{
		//此版本ssh框架貌似无法对应时间类型字段,因此采用更新的方式
		ZwkjForm z=zwkjFormService.getOneFormById(zwkjForm.getId());
		z.setForm_name(zwkjForm.getForm_name());
		z.setForm_caption(zwkjForm.getForm_caption());
		z.setForm_type(zwkjForm.getForm_type());
		z.setForm_usage(zwkjForm.getForm_usage());
		z.setForm_htmlfilename(zwkjForm.getForm_htmlfilename());
		z.setForm_url(zwkjForm.getForm_url());
		z.setInsert_table(zwkjForm.getInsert_table());
		z.setBeginDate(zwkjForm.getBeginDate());
		z.setEndDate(zwkjForm.getEndDate());
		z.setFontSize(zwkjForm.getFontSize());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		z.setUpdatetime(Timestamp.valueOf(sdf.format(new Date())));
		String dstPath = getSpringContext().getServletContext().getRealPath("") + "\\form\\html\\" + zwkjForm.getForm_htmlfilename();
		File file = new File(dstPath);
		
		zwkjFormService.updateForm(z);
		if(file.exists()){
			FileInputStream fis = new FileInputStream(file);
			Blob htmlFlow = Hibernate.createBlob(fis);
			zwkjFormService.updateForm(z.getId(), htmlFlow);
		}
		getResponse().sendRedirect(getRequest().getContextPath()+"/form_getFormList.do?workflowId="+z.getWorkflowId());
	}
	/**
	 * 
	 * @Title: deleteForm
	 * @Description: 删除表单信息
	 * @return String    返回类型
	 * @throws IOException 
	 */
	public void deleteForm() throws IOException{
		String ids=getRequest().getParameter("ids");
		String lcid = null;
		if (CommonUtil.stringNotNULL(ids)) {
			String[] idsArr=ids.split(",");
			for (int i = 0; i < idsArr.length; i++) {
				ZwkjForm form=zwkjFormService.getOneFormById(idsArr[i]);
				if (form!=null) {
					//删除表单信息
					zwkjFormService.deleteForm(form);
					//删除表单关联的html文件
					if (!stringIsNULL(form.getForm_htmlfilename())) {
						String allPath=PathUtil.getWebRoot()+"form/html/"+form.getForm_htmlfilename();
						deleteHTML(allPath);
					}
					lcid = form.getWorkflowId();
				}
			}
		}
		getResponse().sendRedirect(getRequest().getContextPath()+"/form_getFormList.do?workflowId="+lcid);
	}
	/**
	 * 
	 * @Title: deleteHTML
	 * @Description: 彻底删除文件
	 * @param allPath void    返回类型
	 */
	public void deleteHTML(String allPath){
		File file=new File(allPath);
		if (file.exists()) {
			file.delete();
		}
	}
	/**
	 * 
	 * @Title: toHTMLTag2ColumnJsp
	 * @Description: 转向表单元素对应字段设置页面
	 * @return String    返回类型
	 */
	public String toHTMLTag2ColumnJsp(){
		//查询所有字典表信息
		List<WfDictionary> dicList=dictionaryService.getDictionaryListForPage(null, null, new WfDictionary(), null, null);
		getRequest().setAttribute("dicList", dicList);
		
		//查询服务器插件信息
		List<ServerPlugin> serverPlugins=serverPluginAction.getAllServerPlugins();
	    getRequest().setAttribute("serverPlugins", serverPlugins);
		String formid=getRequest().getParameter("formid");
		ZwkjForm form=zwkjFormService.getOneFormById(formid);
		getRequest().setAttribute("form", form);
		String allPath="";
		if (form!=null&&!stringIsNULL(form.getForm_htmlfilename())) {
			allPath=PathUtil.getWebRoot()+"form/html/"+form.getForm_htmlfilename();
			getRequest().setAttribute("filename", form.getForm_htmlfilename());
		}
		
		//查询所有关联的数据库表
		//List<String> databases=new ArrayList<String>();
		//databases.add("TYDJD_TEST");
		List<WfTableInfo> tables=tableInfoService.getTableByLcid(form.getWorkflowId());
		if (tables!=null&&tables.size()>0) {
			for (int i = 0; i < tables.size(); i++) {
				List<WfFieldInfo> fields=fieldInfoService.getAllFieldByTableId(tables.get(i).getId());
				//把字段名改为大写,用于页面标签元素和字段对应
				if (fields!=null) {
					for (int j = 0; j < fields.size(); j++) {
						if(fields.get(j).getVc_fieldname()!=null){
							fields.get(j).setVc_fieldname(fields.get(j).getVc_fieldname().toUpperCase());
						}
					}
				}
				tables.get(i).setFields(fields);
			}
		}
		getRequest().setAttribute("tables", tables);
		
		
		//从html流中获取所有的标签数据
		List<FormTagMapColumn> mapList=new ArrayList<FormTagMapColumn>();
		String htmlString = readHTML(allPath);//源数据
		List<TagBean> tags=getTagFromHTMLString(htmlString);//返回页面taglist	
		if (tags!=null) {
			for (int i = 0; i < tags.size(); i++) {
				FormTagMapColumn m=new FormTagMapColumn();
				m.setFormtagname(tags.get(i).getTagName().trim());
				m.setFormtagtype(tags.get(i).getTagType().trim());
				m.setSelectDic(tags.get(i).getSelect_dic());
				m.setListId(tags.get(i).getListId());
				m.setColumnCname(tags.get(i).getCommentDes());
				mapList.add(m);
			}
		}
		//查询已有表单标签和字段对应关系数据,修改页面默认选中
		List<FormTagMapColumn> mapedList=zwkjFormService.getFormTagMapColumnByFormId(formid);
		String addorupdate="";//0新增 1更新       区分新增还是更新 来决定是否onload的时候加载js
		//对应页面标签和已有对应关系之间的差别，(html中增加或删除元素带来的影响)，页面只显示最新页面标签元素
		if (mapedList!=null&&mapedList.size()>0) {
			for (int i = 0; i < mapList.size(); i++) {
				for (int j = 0; j < mapedList.size(); j++) {
					if (mapedList.get(j).getFormtagname().equals(mapList.get(i).getFormtagname())) {
						// tagtype
						mapedList.get(j).setTagtype(mapList.get(i).getTagtype());
						// formtype
						mapedList.get(j).setFormtagtype((mapList.get(i).getFormtagtype().trim()));
						//zname
						mapedList.get(j).setColumnCname((mapList.get(i).getColumnCname()==null?"":mapList.get(i).getColumnCname().trim()));
						if (CommonUtil.stringNotNULL(mapList.get(i).getListId())) {
							mapedList.get(j).setListId(mapList.get(i).getListId());
						}
						mapList.set(i, mapedList.get(j));
						break;
					}
				}
			}
			addorupdate="1";
		}else{
			addorupdate="0";
		}
		getRequest().setAttribute("addorupdate", addorupdate);
		getRequest().setAttribute("columnMapList", mapList);
		
		
		
		//读取html流，并附加用于点击自动对应所需的样式和js ，并用流写入新文件，用于展现
		String newHtmlString =null;
		String[] htmls=htmlString.split("</HTML>");
		if (htmls.length==1) {
			 htmls=htmlString.split("</html>");
		}
		if(htmls.length==2){
			String scriptStr="<script src=\"src/mapSelected.js\" type=\"text/javascript\" ></script>";
			newHtmlString=htmls[0]+scriptStr+"</html>"+htmls[1];
			//设置 隐藏域  在可视化对应时，可见
			newHtmlString=newHtmlString.replaceAll("type=hidden", "userdefine=hidden");
			newHtmlString=newHtmlString.replaceAll("type=HIDDEN", "userdefine=hidden");
			newHtmlString=newHtmlString.replaceAll("type='hidden'", "userdefine=hidden");
			newHtmlString=newHtmlString.replaceAll("type='HIDDEN'", "userdefine=hidden");
			newHtmlString=newHtmlString.replaceAll("type=\"hidden\"", "userdefine=hidden");
			newHtmlString=newHtmlString.replaceAll("type=\"HIDDEN\"", "userdefine=hidden");
		}
		if (newHtmlString!=null) {
			String tempfilename=UUID.randomUUID()+".html";
			String allPath1=PathUtil.getWebRoot()+"form/html/"+tempfilename;
			getRequest().setAttribute("tempfilename",tempfilename);//返回临时html页面名称，一定记得删除
			
				getRequest().setAttribute("isok", writeHTML(newHtmlString,allPath1,"utf-8"));
			
		}
		
		//保存html文件流至jsp文件流
//		if (form!=null&&stringIsNULL(form.getForm_jspfilename())) {
			//html转成jsp需要添加的头部字符
			String jspAddStr="";
			jspAddStr="<%@ page contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>"+"\n"+"<%@ include file=\"/common/header.jsp\"%>"+"\n"+"<%@ include file=\"/common/formJs.jsp\"%>"+"\n";
			String newJspString=jspAddStr+getJspValueString_new(htmlString);
			
			//过滤意见标签的自定义属性commentDes
			if (tags!=null) {
				for (int i = 0; i < tags.size(); i++) {
					TagBean tag=tags.get(i);
					if (CommonUtil.stringNotNULL(tag.getCommentTagDes())) {
						newJspString=newJspString.replaceAll(tag.getCommentTagDes(), "");
					}
				}
			}
			
			String jspfilename=UUID.randomUUID()+".jsp";
			if (form!=null&&CommonUtil.stringNotNULL(form.getForm_jspfilename())) {
				jspfilename=form.getForm_jspfilename();
			}
			String allPath1=PathUtil.getWebRoot()+"form/jsp/"+jspfilename;
			getRequest().setAttribute("isok", writeHTML(newJspString,allPath1,"utf-8"));

			form.setForm_jspfilename(jspfilename);
			zwkjFormService.updateForm(form);
//		}
		getRequest().setAttribute("jspfilename",form.getForm_jspfilename());
		
		
		return "toHTMLTag2ColumnJsp";
	}
	
	//html转成jsp需要添加标签的el表达式value值
	public String getJspValueString_new(String htmlString){
		//思路1：利用js进行动态赋值，针对三种标签input、select、textarea分别作处理
		String newHtmlString =null;
		String[] htmls=htmlString.split("</HEAD>");
		if (htmls.length==1) {
			 htmls=htmlString.split("</head>");
		}
		if(htmls.length==2){
			String scriptStr="";
//			String scriptStr="\n<script src=\"widgets/common/js/doublelist.js\" type=\"text/javascript\" defer=\"defer\"></script>\n"+
//				    "\n<script type=\"text/javascript\" defer=\"defer\">\n"+
//					"window.onload=function(){\n"+
//						"{\n"+
//						"try{\n"+
//							"/*动态展现多列表数据,并设置查看权限*/\n"+
//							"var listStr='<%=request.getParameter(\"listValues\")==null?\"\":request.getParameter(\"listValues\")%>';\n"+
//							"//alert(listStr);\n"+
//							"if(listStr!=''){\n"+
//								"var namelist=new Array();//记录多个列表的包含的某个标签的name(可以反向找到该列表)\n"+
//								"var jsobj=eval('('+listStr+')');\n"+
//								"for(var i=0;i<jsobj.length;i++){//拆分多列表\n"+
//									"var defaultNotDelIndex=3;//默认不可删除的行数\n"+
//									"var obj=jsobj[i];\n"+
//									"var len=null;\n"+
//									"var name=null;\n"+
//									"//循环获取行数\n"+
//									"for(var o in obj){\n"+
//										"name=o;\n"+
//										"len=obj[o].length;\n"+
//										"namelist.push(o);\n"+
//										"break;\n"+
//									"};\n"+
//									"//重写模板行删除行方法，加入默认不可删除行数权限的参数\n"+
//									"var tempobj=document.getElementsByName(name)[0];\n"+
//									"var tr=getParentNode(tempobj,'TR');\n"+
//									"if(len!=null)defaultNotDelIndex+=len;\n"+
//									"tr.cells[tr.cells.length-1].innerHTML='<input type=\"button\"  value=\"删除行\" onclick=\"delTr(this,'+defaultNotDelIndex+')\"/>';\n"+
//									"//列表新增行\n"+
//									"if(name&&len){\n"+
//										"var tempobj=document.getElementsByName(name)[0];\n"+
//										"for(var j=0;j<len;j++){ \n"+
//											"var tr=addTr(tempobj);\n"+
//											"//新增行没有删除权限\n"+
//											"tr.cells[tr.cells.length-1].innerHTML='';\n"+
//										"}\n"+
//									"};\n"+
//									"//列表标签赋值\n"+
//									"for(var o in obj){\n"+
//										"var values=obj[o];\n"+
//										"var objs=document.getElementsByName(o);\n"+
//										"if(values&&objs){\n"+
//											"for(var j=1;j<objs.length;j++){//有一行是隐藏域\n"+
//												"objs[j].value=values[j-1];  \n"+
//												"objs[j].readOnly=true;//历史数据只读\n"+
//											"};\n"+
//										"};\n"+
//										
//									"};\n"+
//								"};\n"+
//								"//给每个列表新增一空行，用于填写新数据\n"+
//								"for(var i=0;i<namelist.length;i++){\n"+
//									"addTr(document.getElementsByName(namelist[i])[0]);\n"+
//								"}\n"+
//							"};\n"+
//						"}catch(e){\n"+
//							"alert(e);\n"+
//						"};\n"+
//						"}\n"+
//						
//						"//下拉框对应字典表自动赋值\n"+
//						"try{\n"+
//							"var selectstr='<%=request.getParameter(\"selects\")%>';\n"+
//							"var jsobjs=eval('('+selectstr+')'); \n"+
//							"if(jsobjs&&jsobjs.length>0){\n"+
//								"for(var i=0;i<jsobjs.length;i++){\n"+
//									"var tagtype=jsobjs[i].m.formtagtype;\n"+
//									"var tagname=jsobjs[i].m.formtagname;\n"+
//									"var keys=jsobjs[i].diclist[0].vc_key.split(/,/g);\n"+
//								    "var values=jsobjs[i].diclist[0].vc_value.split(/,/g);\n"+
//								    "var objs=document.getElementsByName(tagname);\n"+
//								    "for(var j=0;j<objs.length;j++){\n"+
//								    	"var t=objs[j].tagName.toLowerCase();\n"+
//								    	"if(t=='select'){\n"+
//									    	"for(var k=0;k<keys.length;k++){\n"+
//									    		"objs[j].options.add(new Option(keys[k],values[k])); \n"+
//									    	"};\n"+
//								    	"};\n"+
//								    "};\n"+
//								"};\n"+
//							"};\n"+
//						"}catch(e){\n"+
//							"alert(e);\n"+
//						"};\n"+
//					
//					
//					
//					
//					   "//标签赋值\n"+
//					   "var valuestr='<%=request.getParameter(\"values\")%>';\n"+
//					   
//					   "//alert(valuestr);\n"+
//					   
//					   "if(valuestr&&valuestr!='null'&&valuestr!=''){\n"+
//						   "var values=valuestr.split(/;/);\n"+
//						   "if(values){\n"+
//							   "for(var i=0;i<values.length;i++){\n"+
//								   "var v=values[i].split(/:/);\n"+
//								   "if(v&&v.length==2){\n"+
//									   "var os=document.getElementsByName(v[0]);\n"+
//									   "if(os){\n"+
//									     "for(var k=0;k<os.length;k++){\n"+
//										   "var tagname=os[k].tagName.toLowerCase();\n"+
//										   
//										   "//alert(tagname);\n"+
//										   
//										   "if(tagname=='input'||tagname=='select'){\n"+
//											   "os[k].value=v[1];\n"+
//										   "}else if(tagname=='textarea'){\n"+
//											   "os[k].innerHTML=v[1];\n"+
//										   "}\n"+
//										 "}\n"+
//									   "}\n"+
//								   "}\n"+
//							   "}\n"+
//						   "}\n"+
//					   "}\n"+
//					   "//标签许可\n"+
//					   "var limitValue ='<%=request.getParameter(\"limitValue\")%>';\n"+
//					   "if(limitValue&&limitValue!='null'&&limitValue!=''){\n"+
//						   "var limVals = limitValue.split(/;/);\n"+
//							"for(var j=0;j<limVals.length;j++){\n"+
//								"var childVal = limVals[j].split(/:/);\n"+  
//								"var tagN     = childVal[0];\n"+
//								"var limit    = childVal[1].split(/,/)[0];\n"+    
//								"var typeVal  = childVal[1].split(/,/)[1];\n"+
//								"if(typeVal == 'text' || typeVal == 'select'){\n"+
//									"if(limit == 0){\n"+
//										"document.getElementById(tagN).style.display=\"none\";\n"+  
//									"}else if(limit == 1){\n"+    
//										"document.getElementById(tagN).readOnly=true;\n"+
//									"}\n"+  
//								"}else if(typeVal == 'comment'){\n"+
//									"if(limit == 0){\n"+
//										"document.getElementById('${instanceId}'+tagN).style.display=\"none\";\n"+ 
//									"}else if(limit == 1){\n"+    
//										"document.getElementById('${instanceId}'+tagN+\"luru\").style.display=\"none\";\n"+
//									"}\n"+  
//								"}else if(typeVal == 'attachment'){\n"+
//									"if(limit == 0){\n"+
//									"if(document.getElementById(\"attshow\")){"+
//											"document.getElementById(\"attshow\").style.display=\"none\";\n "+
//											"document.getElementById('${instanceId}'+\"att_upload\").style.display=\"none\";\n"+
//										"}\n"+
//									"}else if(limit == 1){\n"+
//										"if(document.getElementById(\"attachmentDel\")){"+
//											"document.getElementById('${instanceId}'+\"att_upload\").style.display=\"none\";\n"+
//											"document.getElementById(\"attachmentDel\").style.display=\"none\";\n"+
//										"}\n"+ 
//									"}\n"+ 
//								"}\n"+	
//							"}\n"+
//						"}\n"+
//					"};\n"+
//					"</script>\n";
			newHtmlString=htmls[0]+scriptStr+"</HEAD>"+htmls[1];
		}
		
		/**
		 * 替换样式文件(相对路径到绝对路径)
		 */
		if(newHtmlString.contains("../jsp/")){
			newHtmlString = newHtmlString.replace("../jsp/", "${ctx}/form/jsp/");
		}
		return newHtmlString;
	}
	
	
	//html转成jsp需要添加标签的el表达式value值,该方法暂时不适用,判断极度麻烦!!!!!!
	public String getJspValueString(String htmlString){
		//思路2：针对三种标签input、select、textarea分别作处理
		UrlCatcher u=new UrlCatcher();
		String reg_input="<INPUT[^<]*>";//获取所有input类型标签
		String[] inputs=u.getStringByRegEx(htmlString, reg_input, true);
		List<String> newInputs=new ArrayList<String>();
		if (inputs!=null&&inputs.length>0) {
			for (int i = 0; i < inputs.length; i++) {
				String tagName=null;//表单元素名称
				//利用正则表达式获取表单元素名称
				String reg_name=" name=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_]+['\"]?";
				String[] names=u.getStringByRegEx(inputs[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					tagName=tagName.substring(5,tagName.length());
				}
				String newStr=inputs[i];
				if (tagName!=null) {
					String valueStr=" value=\"${"+tagName+"}\"";
					if (newStr.indexOf("value")!=-1) {//没有value字符串
						newStr=newStr.substring(0,6)+valueStr+newStr.substring(6,newStr.length());
					}else{//有value字符串
						//替换value=""字符串
						newStr=newStr.replace("value=['\"]?['\"]?",valueStr);
					}
				}
				newInputs.add(newStr);	
			}
			String[] htmlStrings=htmlString.split(reg_input);
			if (htmlStrings!=null) {
				StringBuffer newhtmlString=new StringBuffer();
				for (int i = 0; i < htmlStrings.length; i++) {
					newhtmlString.append(i==htmlStrings.length-1?htmlStrings[i]:htmlStrings[i]+newInputs.get(i));
				}
				htmlString=newhtmlString.toString();
			}
		}
		
		String reg_select="<SELECT[^<]*>";//获取所有select类型标签
		String[] selects=u.getStringByRegEx(htmlString, reg_select, true);
		
		String reg_textarea="<TEXTAREA[^<]*</TEXTAREA>";//获取所有textarea类型标签
		String[] textareas=u.getStringByRegEx(htmlString, reg_textarea, true);
		
		
		
		return htmlString;
	}
	
	/**
	 * 
	 * @Title: getTagFromHTMLString
	 * @Description: 从html字符串流中获取标签信息(非常重要)
	 * @param htmlString
	 * @return List<String[]>    返回类型
	 */
	public List<TagBean> getTagFromHTMLString(String htmlString){
		List<TagBean> tags=new ArrayList<TagBean>();
		
		UrlCatcher u=new UrlCatcher();
		String reg_input="<INPUT[^<]*>";//获取所有input类型标签
		String[] inputs=u.getStringByRegEx(htmlString, reg_input, true);
		
		String reg_select="<SELECT[^<]*>";//获取所有select类型标签
		String[] selects=u.getStringByRegEx(htmlString, reg_select, true);
		
		String reg_textarea="<TEXTAREA[^<]*</TEXTAREA>";//获取所有textarea类型标签
		String[] textareas=u.getStringByRegEx(htmlString, reg_textarea, true);
		
		
		//新增意见标签抓取
		//<trueway:comment typeinAble="true" deleteAbled="true" id="${instanceId}csyj" instanceId="${instanceId}" currentStepId="${instanceId}"/>
		String reg_comment="<trueway:comment[^<]*>";//获取所有comment类型标签
		String[] comments=u.getStringByRegEx(htmlString, reg_comment, true);
		
		//附件标签抓取
		//<trueway:att onlineEditAble="true" id="${instanceId}att" docguid="${instanceId}" showId="attshow" ismain="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
		String reg_att="<trueway:att[^<]*>";//获取所有att类型标签
		String[] atts=u.getStringByRegEx(htmlString, reg_att, true);
		
		
		//文号标签抓取
		//<trueway:dn tagId="dn_tagid_zhu" defineId="${workFlowId}" webId="${webId}" showId="wenhaos" value="wenhaos"/>
		String reg_dn="<trueway:dn[^<]*>";//获取所有dn类型标签
		String[] dns=u.getStringByRegEx(htmlString, reg_dn, true);
		
		/*
		String reg_zd=">[^<]+</a>";
		String zd=uc.getStringByRegEx(datatds[5], reg_zd, false)[0];
		zd=zd.substring(1,zd.length()-4);
		*/
		if (inputs!=null&&inputs.length>0) {
			for (int i = 0; i < inputs.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				String reg_name=" name=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_]+['\"]?";
				String[] names=u.getStringByRegEx(inputs[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					tagName=tagName.substring(5,tagName.length());
				}
				//利用正则表达式获取表单元素类型
				String reg_type=" type=['\"]?[^'\"]+['\"]?";
				String[] types=u.getStringByRegEx(inputs[i], reg_type, true);
				if (types!=null&&types.length>0) {
					tagType=types[0];
					tagType=tagType.trim();
					tagType=tagType.replaceAll("'", "");
					tagType=tagType.replaceAll("\"", "");
					tagType=tagType.substring(5,tagType.length());
				}
				
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(inputs[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				
//				if (tagType==null) {
//					reg_type=" type=['\"]?hidden['\"]?";
//					types=u.getStringByRegEx(inputs[i], reg_type, true);
//					if (types!=null&&types.length>0) {
//						tagType=types[0];
//						tagType=tagType.trim();
//						tagType=tagType.replaceAll("'", "");
//						tagType=tagType.replaceAll("\"", "");
//						tagType=tagType.substring(5,tagType.length());
//					}
//				}
				//如果匹配到元素名称，但没有匹配到元素类型，则认为元素类型为text文本框
				if (tagName!=null&&tagType==null) {
					tagType="text";
				}
				//匹配列表字段属性
				String reg_list=" list=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789]+['\"]?";
				String[] lists=u.getStringByRegEx(inputs[i], reg_list, true);
				String list=null;
				if (lists!=null&&lists.length>0) {
					list=lists[0];
					list=list.trim();
					list=list.replaceAll("'", "");
					list=list.replaceAll("\"", "");
					list=list.substring(5,list.length());
				}
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setListId(list);
					tag.setCommentDes(tagZname);//标签属性zname中文描述
					tag.setCommentTagDes(tagZnameStr);//标签属性zname字符串
					
					//此处过滤radio、checkbox，同组radio或checkbox采用一条记录
					boolean isin=false;
					for (int j = 0; j < tags.size(); j++) {
						TagBean bean=tags.get(j);
						if (bean.getTagName().equals(tagName)&&bean.getTagType().equals(tagType)) {
							isin=true;break;
						}
					}
					if (!isin) {
						tags.add(tag);
					}
					
				}
			}
		}
		
		if (selects!=null&&selects.length>0) {
			for (int i = 0; i < selects.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				String reg_name=" name=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_]+['\"]?";
				String[] names=u.getStringByRegEx(selects[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					tagName=tagName.substring(5,tagName.length());
				}
				tagType="select";
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(selects[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				
				String reg_dic=" dic=['\"]?[^'\"]+['\"]?";
				String[] dics=u.getStringByRegEx(selects[i], reg_dic, true);
				if (dics!=null&&dics.length>0) {
					select_dic=dics[0];
					select_dic=select_dic.trim();
					select_dic=select_dic.replaceAll("'", "");
					select_dic=select_dic.replaceAll("\"", "");
					select_dic=select_dic.trim();
					select_dic=select_dic.substring(4,select_dic.length());
				}
				
				//匹配列表字段属性
				String reg_list=" list=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789]+['\"]?";
				String[] lists=u.getStringByRegEx(selects[i], reg_list, true);
				String list=null;
				if (lists!=null&&lists.length>0) {
					list=lists[0];
					list=list.trim();
					list=list.replaceAll("'", "");
					list=list.replaceAll("\"", "");
					list=list.substring(5,list.length());
				}
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setListId(list);
					tag.setCommentDes(tagZname);//标签属性zname中文描述
					tag.setCommentTagDes(tagZnameStr);//标签属性zname字符串
					tags.add(tag);
				}
			}
		}
		
		if (textareas!=null&&textareas.length>0) {
			for (int i = 0; i < textareas.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				String reg_name=" name=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_]+['\"]?";
				String[] names=u.getStringByRegEx(textareas[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					tagName=tagName.substring(5,tagName.length());
				}
				tagType="textarea";
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(textareas[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				//匹配列表字段属性
				String reg_list=" list=['\"]?[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_0123456789]+['\"]?";
				String[] lists=u.getStringByRegEx(textareas[i], reg_list, true);
				String list=null;
				if (lists!=null&&lists.length>0) {
					list=lists[0];
					list=list.trim();
					list=list.replaceAll("'", "");
					list=list.replaceAll("\"", "");
					list=list.substring(5,list.length());
				}
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setListId(list);
					tag.setCommentDes(tagZname);//标签属性zname中文描述
					tag.setCommentTagDes(tagZnameStr);//标签属性zname字符串
					tags.add(tag);
				}
			}
		}
		
		//新增意见标签抓取
		if (comments!=null&&comments.length>0) {
			for (int i = 0; i < comments.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				//<trueway:comment typeinAble="true" deleteAbled="true" id="${instanceId}csyj" instanceId="${instanceId}" currentStepId="${instanceId}"/>
				String reg_name=" id=['\"]{1}[^'\"]+['\"]{1}";
				String[] names=u.getStringByRegEx(comments[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					//tagName=tagName.substring(16,tagName.length());
					if (tagName.indexOf("}")!=-1) {
						tagName=tagName.split("}")[1];
					}
				}
				tagType="comment";
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(comments[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				//意见标签描述
//				String cname="";
//				String commentTagDes="";
//				String reg_cname=" commentDes=['\"]{1}[^'\"]+['\"]{1}";
//				String[] cnames=u.getStringByRegEx(comments[i], reg_cname, true);
//				if (cnames!=null&&cnames.length>0) {
//					cname=cnames[0];
//					cname=cname.trim();commentTagDes=cname;
//					cname=cname.replaceAll("'", "");
//					cname=cname.replaceAll("\"", "");
//					cname=cname.substring(11,cname.length());
//				}
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setCommentDes(tagZname);//意见标签描述
					tag.setCommentTagDes(tagZnameStr);
					tags.add(tag);
				}
			}
		}
		
		//附件标签抓取
		if (atts!=null&&atts.length>0) {
			for (int i = 0; i < atts.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				//<trueway:att onlineEditAble="true" id="${instanceId}att" docguid="${instanceId}" showId="attshow" ismain="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
				String reg_name=" id=['\"]{1}[^'\"]+['\"]{1}";
				String[] names=u.getStringByRegEx(atts[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					//tagName=tagName.substring(16,tagName.length());
					if (tagName.indexOf("}")!=-1) {
						tagName=tagName.split("}")[1];
					}
				}
				tagType="attachment";
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(atts[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setCommentDes(tagZname);//标签属性zname中文描述
					tag.setCommentTagDes(tagZnameStr);//标签属性zname字符串
					tags.add(tag);
				}
			}
		}
		
		//文号标签抓取
		if (dns!=null&&dns.length>0) {
			for (int i = 0; i < dns.length; i++) {
				TagBean tag=new TagBean();
				String tagName=null;//表单元素名称
				String tagType=null;//表单元素类型
				String select_dic=null;//下拉框关联字典表名称
				
				//利用正则表达式获取表单元素名称
				//<trueway:dn tagId="dn_tagid_zhu" defineId="${workFlowId}" webId="${webId}" showId="wenhaos" value="wenhaos"/>
				String reg_name=" tagId=['\"]{1}[^'\"]+['\"]{1}";
				String[] names=u.getStringByRegEx(dns[i], reg_name, true);
				if (names!=null&&names.length>0) {
					tagName=names[0];
					tagName=tagName.trim();
					tagName=tagName.replaceAll("'", "");
					tagName=tagName.replaceAll("\"", "");
					tagName=tagName.substring(6,tagName.length());
//					if (tagName.indexOf("}")!=-1) {
//						tagName=tagName.split("}")[1];
//					}
				}
				tagType="wh";
				//利用正则表达式获取标签中文描述
				String tagZname=null;//
				String tagZnameStr=null;
				String reg_cname=" zname=['\"]?[^'\"]+['\"]?";
				String[] cnames=u.getStringByRegEx(dns[i], reg_cname, true);
				if (cnames!=null&&cnames.length>0) {
					tagZname=cnames[0];
					tagZname=tagZname.trim();tagZnameStr=tagZname;
					tagZname=tagZname.replaceAll("'", "");
					tagZname=tagZname.replaceAll("\"", "");
					tagZname=tagZname.substring(6,tagZname.length());
				}
				
				if (tagName!=null&&tagType!=null) {
					tag.setTagName(tagName);
					tag.setTagType(tagType);
					tag.setSelect_dic(select_dic);
					tag.setCommentDes(tagZname);//标签属性zname中文描述
					tag.setCommentTagDes(tagZnameStr);//标签属性zname字符串
					tags.add(tag);
				}
			}
		}
		
		return tags;
	}
	/**
	 * 
	 * @Title: addFormTagMapColumn
	 * @Description: 新增
	 * @return String    返回类型
	 */
	public String addFormTagMapColumn(){
		String formid=getRequest().getParameter("formid");
		//返回临时html页面名称，一定记得删除
		String tempfilename=getRequest().getParameter("tempfilename");
		if (!stringIsNULL(tempfilename)) {
			String allpath=PathUtil.getWebRoot()+"form/html/"+tempfilename;
			deleteHTML(allpath);
		}
		//删除原先对应关系
		zwkjFormService.deleteFormTagMapColumnByFormId(formid);
		//增加对应关系
		String[] formtagnames=getRequest().getParameterValues("formtagnames");
		String[] formtagtypes=getRequest().getParameterValues("formtagtypes");
		String[] tagtypes=getRequest().getParameterValues("tagtypes");
		String[] tagTables=getRequest().getParameterValues("tagTables");
		String[] tagFields=getRequest().getParameterValues("tagFields");
		
		String[] assignTagTables = getRequest().getParameterValues("assignTagTables");
		String[] assignTagFields = getRequest().getParameterValues("assignTagFields");
		String[] constantValues = getRequest().getParameterValues("constantValues");
		
		String[] dicdatas=getRequest().getParameterValues("dicdatas");
		String[] otherdatas=getRequest().getParameterValues("otherdatas");
		String[] selectDics=getRequest().getParameterValues("selectDics");
		String[] listIds=getRequest().getParameterValues("listIds");
		String[] commentDeses=getRequest().getParameterValues("commentDeses");
		String[] docColumns=getRequest().getParameterValues("docColumns");
		
		String[] serverPlugins=getRequest().getParameterValues("serverPlugins");
		String[] dics=getRequest().getParameterValues("dics");
		//String[] ways=getRequest().getParameterValues("ways");
		String[] trueAreas=getRequest().getParameterValues("trueArea");
		String[] isPleaseOrWatch=getRequest().getParameterValues("isPleaseOrWatch");

		
		//+++ 验证+++
		String[] valueformat = getRequest().getParameterValues("valueformat");
		String[] bindfields = getRequest().getParameterValues("bindfields");
		String[] verifyformat = getRequest().getParameterValues("verifyformat");
		String[] associatedColumns = this.getRequest().getParameterValues("associatedColumns");	//关联必填的字段
		String[] regularExpression = this.getRequest().getParameterValues("regularExpression");	//正则表达式
		String[] regularMeanings = this.getRequest().getParameterValues("regularMeanings");	//正则表达式含义
		String[] generationMode = this.getRequest().getParameterValues("generationMode");	//值生成模式

		String[] textMaxLen = getRequest().getParameterValues("textMaxLen");

		if (formtagnames!=null&&formtagnames.length>0) {
			for (int i = 0; i < formtagnames.length; i++) {
				String fieldInfo=tagFields[i].equals("null")?"":tagFields[i];
				String assignfieldInfo = assignTagFields[i].equals("null")?"":assignTagFields[i];
				FormTagMapColumn m=new FormTagMapColumn();
				m.setFormid(formid);
				m.setFormtagname(formtagnames[i]);
				m.setFormtagtype(formtagtypes[i]);
				m.setTagtype(tagtypes[i]);
				m.setTablename(tagTables[i].split(";").length>1?tagTables[i].split(";")[0]:null);
				m.setColumnname(fieldInfo.equals("")?"":fieldInfo.split(":")[0]);
				m.setAssignTableName(assignTagTables[i].split(";").length>1?assignTagTables[i].split(";")[0]:null);
				m.setAssignColumnName(assignfieldInfo.equals("")?"":assignfieldInfo.split(":")[0].trim());
				if(formtagtypes[i].equals("attachment")){
					 if(i != 0){
						 m.setTablename(assignTagTables[0].split(";").length>1?assignTagTables[0].split(";")[0]:null);
						 m.setAssignTableName(assignTagTables[0].split(";").length>1?assignTagTables[0].split(";")[0]:null);
					 }
				}
//				if (formtagtypes[i].equals("comment")) {//意见标签读取hmtl的commentDes标签描述
//					m.setColumnCname(commentDeses[i]);
//				}else if (formtagtypes[i].equals("attachment")){ //附件标签暂不做处理
//					//m.setColumnCname(columnCname[i]);
//				}else { //普通标签读取字段中文注释 
//					m.setColumnCname(fieldInfo.equals("")?"":fieldInfo.split(":")[1]);
//				}
				m.setColumnCname(commentDeses[i]);
				m.setDicdata(dicdatas[i]);
				m.setOtherData(otherdatas[i]);
				m.setSortIndex(i);
				m.setSelectDic(selectDics[i]);
				m.setListId(listIds[i]);
				m.setDocColumn(docColumns[i]);
				m.setServerPlugin_id(serverPlugins[i]);
				m.setSelectDic(dics[i]);
				//m.setGet_way(ways[i]);
				m.setTrueArea(trueAreas[i]);
				
				m.setIsPleaseOrWatch(isPleaseOrWatch[i]);
				
				m.setConstantValue(constantValues[i]);
				m.setValueformat(valueformat[i]);
				m.setBindfields(bindfields[i]);
				m.setVerifyformat(verifyformat[i]);
				m.setAssociatedColumns(associatedColumns[i]);
				m.setRegularExpression(regularExpression[i]);
				m.setRegularMeanings(regularMeanings[i]);
				m.setGenerationMode(generationMode[i]);
				m.setTextMaxLen(textMaxLen[i]);
				zwkjFormService.addFormTagMapColumn(m);
			}
		}
		return "addFormTagMapColumn";
	}
	
	/**
	 * 日历
	 * @return
	 * @throws ParseException 
	 */
	public String cal() throws ParseException{
		//日期
		String date = getRequest().getParameter("date");
		//限制完成期限
		String dateLimit = getRequest().getParameter("dateLimit");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		long day = 0L;
		long time = 0L;
		if (date != null && !("").equals(date)) {
		    time = sdf.parse(date).getTime();
			long daL = Long.parseLong(dateLimit);
			daL = daL * 24 * 60 * 60 * 1000;
			time += day;
		}
		return sdf.format(new Date(time));
	}
	
	/**
	 * @Title: loginout
	 * @Description: 退出登录
	 * @return String    返回类型
	 
	 */
	public String loginout(){
		if (getSession()!=null) {
			getSession().invalidate();
		}
		return "loginout";
	}
	
	public String selectDicValues(String formid){
		if (CommonUtil.stringNotNULL(formid)) {
			//查询下拉框对应字典表信息并转换为json字符串用于页面动态赋值
			List<FormTagMapColumn> mapedList=zwkjFormService.getFormTagMapColumnByFormId(formid);
			List<Map> mapList2=new ArrayList<Map>();
			if (mapedList!=null) {
				for (int i = 0; i < mapedList.size(); i++) {
					FormTagMapColumn m=mapedList.get(i);
					if (CommonUtil.stringNotNULL(m.getSelectDic())) {
						List<WfDictionary> dicList=dictionaryService.getDictionaryByName(m.getSelectDic());
						if (dicList!=null&&dicList.size()>0) {
							Map map=new HashMap();
							map.put("m", m);
							map.put("diclist", dicList);
							mapList2.add(map);
						}
					}
				}
			}
			return new Gson().toJson(mapList2);
		}
		return "";
	}
	
	public Map<String, String> selectDicValuesForMobile(String formid){
		Map<String, String> map = new HashMap<String, String>();
		if (CommonUtil.stringNotNULL(formid)) {
			//查询下拉框对应字典表信息并转换为json字符串用于页面动态赋值
			List<FormTagMapColumn> mapedList=zwkjFormService.getFormTagMapColumnByFormId(formid);
			if (mapedList!=null) {
				for (int i = 0; i < mapedList.size(); i++) {
					FormTagMapColumn m=mapedList.get(i);
					if (CommonUtil.stringNotNULL(m.getSelectDic())) {
						List<WfDictionary> dicList=dictionaryService.getDictionaryByName(m.getSelectDic());
						if (dicList!=null&&dicList.size()>0) {
							map.put(m.getFormtagname(), dicList.get(0).getVc_key()+"[--]"+dicList.get(0).getVc_value()+"[--]"+dicList.get(0).getVc_default());
							//map.put(m.getFormtagname(), dicList.get(0).getVc_key());
						}
					}
				}
			}
			return map;
		}
		return null;
	}
	
	
	/**
	 * 存储过程调用demo
	 */
	public void excuteProcedureDemo(){
		
		String prodecureName="office_table1_add";
		//输入、输出参数   目前只支持VARCHAR、INTEGER、DATE三种
		//输入、输出参数类型   仅有in、out两种
		Object[][] obj={
				{"in",	"VARCHAR","wh1234"},//1输入参数  2输入参数类型  3输入参数值
				{"in",	"VARCHAR","id1234"},
				{"in",	"DATE",Timestamp.valueOf("2013-01-01 15:25:33")},
				{"out",	"INTEGER","oldCount"},//1输出参数 2输出参数类型  3输出值map的key
				{"out",	"INTEGER","newCount"},
				{"out",	"DATE","cDate"}
		};
		Map<String, Object> map;
		try {
			map = zwkjFormService.excuteProcedure(obj, prodecureName);
			System.err.println("oldCount:"+map.get("oldCount"));
			System.err.println("newCount:"+map.get("newCount"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 列表字段值数据拼接示例demo
	 * @return
	 */
	public String getListValues(){
		List<Map> list=new ArrayList<Map>();
		
		Map m1=new HashMap();
		String[] jl_gzsj={"2013年10月5日","2013年10月4日","2013年10月5日"};
		String[] jl_gzdd={"天宫","凌霄宝殿","阎罗殿"};
		String[] jl_zw={"齐天大圣","玉皇大帝","地藏王"};
		String[] jl_xz={"1亿英镑","1亿人民币","1亿日元"};
//		m1.put("jl_gzsj", jl_gzsj);
//		m1.put("jl_gzdd", jl_gzdd);
//		m1.put("jl_zw", jl_zw);
//		m1.put("jl_xz", jl_xz);
		m1.put("jl_gzsj", "");
		m1.put("jl_gzdd", "");
		m1.put("jl_zw", "");
		m1.put("jl_xz", "");
		list.add(m1);
		Map m2=new HashMap();
		String[] ry_xm={"熊猫","王八"};
		String[] ry_nl={"1岁","千年"};
		String[] ry_sr={"2020-02-20","3030-03-30"};
//		m2.put("ry_xm", ry_xm);
//		m2.put("ry_nl", ry_nl);
//		m2.put("ry_sr", ry_sr);
		m2.put("ry_xm", "");
		m2.put("ry_nl", "");
		m2.put("ry_sr", "");
		list.add(m2);
		return new Gson().toJson(list);
	}
	
	/**
	 * 
	 * @Title: updateFormLocationJson
	 * @Description: 更新表单信息
	 * @return String    返回类型
	 * @throws IOException 
	 */
	public void updateFormLocationJson() throws IOException{
		//此版本ssh框架貌似无法对应时间类型字段,因此采用更新的方式
		String formId = getRequest().getParameter("formid");
		ZwkjForm z=zwkjFormService.getOneFormById(formId);
		// 根据html生成pdf 并且转成png 图片 使用线程
		String  pdfPath= createBlankPdf(z.getForm_htmlfilename());
		pdfPath = new PDFToTrue().pdfToTrue(pdfPath, null);
		if(pdfPath != null && !pdfPath.equals("")){
			//2 调用 生成 png
			PDFToPNGImageThread ppThread = new PDFToPNGImageThread(pdfPath);
			ppThread.start();
			z.setForm_pdf(pdfPath.substring(pdfPath.lastIndexOf("/") + 1));
		}
		String locations = getRequest().getParameter("locations");
		z.setElementLocationJson(locations);
		
		String formPages = getRequest().getParameter("formPages");
		z.setFormPageJson(formPages);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		z.setUpdatetime(Timestamp.valueOf(sdf.format(new Date())));
		zwkjFormService.updateForm(z);
		getResponse().getWriter().write("yes");
	}
	
	
	
	
	public void changeJSONArrayJson(JSONArray arr){
		for(int i=0; i<arr.size(); i++){
			JSONObject obj2 = (JSONObject)arr.get(i);
			Iterator it = obj2.keys();
			boolean exist = false;
			while(it.hasNext()){
				String key = (String)it.next();
				String value = obj2.getString(key);
				if(key!=null && key.equals("id") && !value.equals("table") && !value.equals("")){
					System.out.println("------2-----"+obj2.toString());
					exist = true;
					break;
				}
			}
			if(!exist){
				it = obj2.keys();
				while(it.hasNext()){
					String key = (String)it.next();
					String value = obj2.getString(key);
					if(value!=null){
						try{
							JSONArray arr2 = JSONArray.fromObject(value);
							changeJSONArrayJson(arr2);
						}catch (Exception e) {
							try{
								JSONObject obj3 = JSONObject.fromObject(value);
								changeJSONObjJson(obj3);
							}catch (Exception e2) {
							}
						}
					}
				}
			}
		}
	}
	
	public void changeJSONObjJson(JSONObject obj2){
		Iterator it = obj2.keys();
		boolean exist = false;
		while(it.hasNext()){
			String key = (String)it.next();
			String value = obj2.getString(key);
			if(key!=null && key.equals("id") && !value.equals("table") && !value.equals("")){
				System.out.println("------3-----"+obj2.get("type"));
				System.out.println("------3-----"+obj2.toString());
				exist = true;
				break;
			}
		}
		if(!exist){
			it = obj2.keys();
			while(it.hasNext()){
				String key = (String)it.next();
				String value = obj2.getString(key);
				if(value!=null){
					try{
						JSONArray arr2 = JSONArray.fromObject(value);
						changeJSONArrayJson(arr2);
					}catch (Exception e) {
						try{
							JSONObject obj3 = JSONObject.fromObject(value);
							changeJSONObjJson(obj3);
						}catch (Exception e2) {
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @Description: 添加表单风格页面
	 * @author: xiep
	 * @time: 2017-6-15 下午5:59:19
	 * @return
	 */
	public String toAddFormStyle(){
		Map<String, String> map = new HashMap<String, String>();
		List<FormStyle> list = zwkjFormService.getFormStyle(map);
		if(list != null && list.size() > 0){
			FormStyle formStyle = list.get(0);
			if(formStyle != null){
				getRequest().setAttribute("id", formStyle.getId());
				getRequest().setAttribute("fontSize", formStyle.getFontSize());
				getRequest().setAttribute("verticalSpacing", formStyle.getVerticalSpacing());
				getRequest().setAttribute("dateFormat", formStyle.getDateFormat());
				getRequest().setAttribute("font", formStyle.getFont());
			} 
		}
		return "addFormStyle";
	}
	
	/**
	 * 
	 * @Description: 添加表单风格
	 * @author: xiep
	 * @time: 2017-6-15 下午6:08:51
	 */
	public void addFormStyle(){
		String id = getRequest().getParameter("id");
		String fontSize = getRequest().getParameter("fontSize");
		String verticalSpacing = getRequest().getParameter("verticalSpacing");
		String dateFormat = getRequest().getParameter("dateFormat");
		String font = getRequest().getParameter("font");
		FormStyle formStyle = new FormStyle();
		if(CommonUtil.stringNotNULL(id)){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			List<FormStyle> list = zwkjFormService.getFormStyle(map);
			if(list != null && list.size() > 0){
				formStyle = list.get(0);
			}
		}
		formStyle.setFontSize(fontSize);
		formStyle.setVerticalSpacing(verticalSpacing);
		formStyle.setDateFormat(dateFormat);
		formStyle.setFont(font);
		zwkjFormService.addFormStyle(formStyle);
	}
	
	public void setColumnRule(){
		String id = this.getRequest().getParameter("id");	//关联必填的字段
		String associatedColumns = this.getRequest().getParameter("associatedColumns");	//关联必填的字段
		String regularExpression = this.getRequest().getParameter("regularExpression");	//正则表达式
		String regularMeanings = this.getRequest().getParameter("regularMeanings");	//正则表达式含义
		String generationMode = this.getRequest().getParameter("generationMode");	//值生成模式
		String correlation = this.getRequest().getParameter("correlation");	//关联字段名称
		String connectfield = this.getRequest().getParameter("connectfield");	//关联修改字段名称
		
		FormTagMapColumn ftmc = zwkjFormService.getFormTagMapColumnById(id);
		ftmc.setAssociatedColumns(associatedColumns);
		ftmc.setRegularExpression(regularExpression);
		ftmc.setRegularMeanings(regularMeanings);
		ftmc.setGenerationMode(generationMode);
		ftmc.setCorrelation(correlation);
		ftmc.setConnectfield(connectfield);
		zwkjFormService.updateFormTagMapColumn(ftmc);
		
	}
	
}