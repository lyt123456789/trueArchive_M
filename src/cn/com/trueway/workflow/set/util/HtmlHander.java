package cn.com.trueway.workflow.set.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.UrlCatcher;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.TagBean;

public class HtmlHander {
	
	public String replaceHtml(String htmlPath,String elementId,int yjHeight){
		Calendar calendar = Calendar.getInstance();
	    String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		String dstPath = FileUploadUtils.getRealFolderPath(pdfRoot, Constant.GENE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录

		String newHtmlPath = pdfRoot +dstPath+ String.valueOf(calendar.getTimeInMillis()) + ".html";
		FileInputStream fileinputstream;
		try {
			fileinputstream = new FileInputStream(htmlPath);
			// 下面四行：获得输入流的长度，然后建一个该长度的数组，然后把输入流中的数据以字节的形式读入到数组中，然后关闭流
			int length = fileinputstream.available();
			byte bytes[] = new byte[length];
			fileinputstream.read(bytes);
			fileinputstream.close();
			String templateContent = "";
			templateContent = new String(bytes, "UTF-8");
			
			// 正则匹配
			UrlCatcher u = new UrlCatcher();
			String reg_yj = "<td .*?id=\""+elementId+"\"[^>]*?>";
			String[] yjs = u.getStringByRegEx(templateContent, reg_yj, 2);
			if(yjs != null && yjs.length >0){
				for(int i = 0; i < yjs.length; i++){
					Pattern p = Pattern.compile("height:(.*?)px",Pattern.DOTALL);
					Matcher m =  p.matcher(yjs[i]);
					if(m.find()){
						String tempStr = yjs[i].replace( m.group(0), "height:"+yjHeight+"px");
						templateContent = templateContent.replace(yjs[i],tempStr);
					}

				}
			}
			
			FileOutputStream fileoutputstream = new FileOutputStream(newHtmlPath);// 建立文件输出流
			OutputStreamWriter osw = new OutputStreamWriter(fileoutputstream,"UTF-8");
			osw.write(templateContent);
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	return 	newHtmlPath;
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
	
}
