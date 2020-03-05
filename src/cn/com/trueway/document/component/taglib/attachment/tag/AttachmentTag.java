package cn.com.trueway.document.component.taglib.attachment.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
/**
 * 
 * 描述：附件标签<br>
 * 作者：WangXF<br>
 * 创建时间：2011-11-29 下午02:00:35<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class AttachmentTag extends TagSupport{
	
	private static final long serialVersionUID = 1L;
	
	//1、标签的唯一标识
	private String id;
	
	//2、附件所关联的公文ID
	private String docguid;

	//3、是否是正文附件
	private boolean ismain;
	
	//4、是否提供预览功能（ceb,doc）
	private boolean previewAble;

	//5、是否提供Word转CEB功能
	private boolean tocebAble;
	
	//6、是否提供盖章功能
	private boolean toStampAble;
	
	//7、是否提供下载功能
	private boolean downloadAble;
	
	//8、是否提供上传功能
	private boolean uploadAble;
	
	//9、是否提供删除功能
	private boolean deleteAble;
	
	//10、是否提供CEB脱密功能
	private boolean detachStampAble;
	
	//11、带红章打印功能(如果要打印红章此属性不能为空且值等于公章Name)
	private String printStampName;
	
	//12、为上传按钮指定样式
	private String openBtnClass;
	
	//13、为各功能按钮指定样式
	private String otherBtnsClass;
	
	//14、附件显示区域ID
	private String showId;
	
	//15、是否是收文附件
	private boolean isreciveAtt;
	
	//节点ID
	private String nodeId;

	//16、发文中正文的在线编辑与查看编辑历史功能
	private boolean onlineEditAble;

	//17、上传结束时调用的JS方法名（目的用来完成回调，此方法需写在页面上）
	private String uploadCallback;
	
	//18、删除结束时调用的JS方法名（目的用来完成回调，此方法需写在页面上）
	private String deleteCallback;
	
	//19、是否使用高拍仪
	private boolean scanAble;
	
	//20 标识该标签是上传正文材料还是其他材料
	private String isZw;	
	
	//21正文模板选择
	private String zwTemSel;
	
	//22是否是正文第一步
	private String isFirst;
	
	//常量：显示附件内容的DIV ID后一部分 (前一部分为标签ID)
	private static final String ATT_BODY_ID="_attbody";

	//常量：显示附件上传按钮的DIV ID后一部分(前一部分为标签ID)
	private static final String ATT_FOOT_ID="_attfoot";

	//常量：附件上传按钮的 ID后一部分(前一部分为标签ID)
	private static final String UPLOAD_ID="_upload";

	//常量：为了获得附件个数(前一部分为标签ID)
	private static final String SHOW_ID="_showid";

	//常量：刷新按钮的ID后一部分(前一部分为标签ID)
	private static final String REF="_ref";

	//常量：上传后的回调方法名的隐藏域ID后一部分(前一部分为标签ID)
	private static final String UPLOAD_CALLBACK="_upload_callback";

	//常量：删除后的回调方法名的隐藏域ID后一部分(前一部分为标签ID)
	private static final String DELETE_CALLBACK="_delete_callback";

	//高拍仪按钮图片
	private static final String GPY_UPLOADIMG="./dwz/button/upload2.png";
	
	//正文上传按钮的图片
	private static final String UPLOADIMG_ZW="./dwz/button/text-attr1.jpg";
	
	//普通附件上传按钮的图片
	private static final String UPLOADIMG_FJ="./dwz/button/text-attr2.jpg";
	
	private static final String ZWMBIMG_ZWMB = "./dwz/button/text-zwmb.jpg";
	
	public String getId() {
		if(id==null){
			return "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocguid() {
		if(docguid==null){
			return "";
		}
		return docguid;
	}

	public void setDocguid(String docguid) {
		this.docguid = docguid;
	}

	public boolean isIsmain() {
		return ismain;
	}

	public void setIsmain(boolean ismain) {
		this.ismain = ismain;
	}

	public boolean isPreviewAble() {
		return previewAble;
	}

	public void setPreviewAble(boolean previewAble) {
		this.previewAble = previewAble;
	}

	public boolean isTocebAble() {
		return tocebAble;
	}

	public void setTocebAble(boolean tocebAble) {
		this.tocebAble = tocebAble;
	}

	public boolean isDownloadAble() {
		return downloadAble;
	}

	public void setDownloadAble(boolean downloadAble) {
		this.downloadAble = downloadAble;
	}

	public boolean isDeleteAble() {
		return deleteAble;
	}

	public void setDeleteAble(boolean deleteAble) {
		this.deleteAble = deleteAble;
	}

	public boolean isUploadAble() {
		return uploadAble;
	}

	public String getUploadCallback() {
		return uploadCallback;
	}

	public void setUploadCallback(String uploadCallback) {
		this.uploadCallback = uploadCallback;
	}

	public String getDeleteCallback() {
		return deleteCallback;
	}

	public void setDeleteCallback(String deleteCallback) {
		this.deleteCallback = deleteCallback;
	}
	
	public boolean isScanAble() {
		return scanAble;
	}

	public void setScanAble(boolean scanAble) {
		this.scanAble = scanAble;
	}

	public void setUploadAble(boolean uploadAble) {
		this.uploadAble = uploadAble;
	}

	public boolean isDetachStampAble() {
		return detachStampAble;
	}

	public void setDetachStampAble(boolean detachStampAble) {
		this.detachStampAble = detachStampAble;
	}

	public String getPrintStampName() {
		if(printStampName==null){
			return "";
		}
		return printStampName;
	}

	public void setPrintStampName(String printStampName) {
		this.printStampName = printStampName;
	}

	public boolean isToStampAble() {
		return toStampAble;
	}

	public void setToStampAble(boolean toStampAble) {
		this.toStampAble = toStampAble;
	}
	
	public String getOpenBtnClass() {
		if(openBtnClass==null){
			return "";
		}
		return openBtnClass;
	}

	public void setOpenBtnClass(String openBtnClass) {
		this.openBtnClass = openBtnClass;
	}

	public String getOtherBtnsClass() {
		if(otherBtnsClass==null){
			return "";
		}
		return otherBtnsClass;
	}

	public void setOtherBtnsClass(String otherBtnsClass) {
		this.otherBtnsClass = otherBtnsClass;
	}

	public String getShowId() {
		if(showId==null){
			return "";
		}
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

	public boolean isIsreciveAtt() {
		return isreciveAtt;
	}

	public void setIsreciveAtt(boolean isreciveAtt) {
		this.isreciveAtt = isreciveAtt;
	}

	public boolean isOnlineEditAble() {
		return onlineEditAble;
	}

	public void setOnlineEditAble(boolean onlineEditAble) {
		this.onlineEditAble = onlineEditAble;
	}
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getIsZw() {
		return isZw;
	}

	public void setIsZw(String isZw) {
		this.isZw = isZw;
	}

	public String getZwTemSel() {
		return zwTemSel;
	}

	public void setZwTemSel(String zwTemSel) {
		this.zwTemSel = zwTemSel;
	}

	public String getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			StringBuffer html = new StringBuffer();
			html.append(this.getBody());
			out.print(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TagSupport.SKIP_PAGE;
		 //TagSupport.SKIP_BODY;
	}

	
	private String getBody(){
		//拼接参数
		Object args[] = new Object[]{id,docguid,deleteAble,downloadAble,
				previewAble,onlineEditAble,tocebAble,toStampAble,
				printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isreciveAtt,nodeId,isFirst};
		String argsStr=this.getArgStr(args, "','");
		
		StringBuffer body = new StringBuffer();
		//标签DIV开始
		body.append("<div id=\"").append(id).append("\">"); 
		body.append("<div id=\"").append(id).append(ATT_BODY_ID).append("\">");
		body.append("</div>");
		body.append("<div id=\"").append(id).append(ATT_FOOT_ID).append("\">");
		if(showId!=null&&!showId.equals("")){
			body.append("<input type=\"hidden\" id=\"").append(id).append(SHOW_ID).append("\" value=\"").append(showId).append("\" />");
		}else{
			body.append("<input type=\"hidden\" id=\"").append(id).append(SHOW_ID).append("\" value=\"").append(id).append(ATT_BODY_ID).append("\" />");
		}
		body.append("<input type=\"hidden\" id=\"").append(id).append(UPLOAD_CALLBACK).append("\" value=\"").append(uploadCallback).append("\" />");
		body.append("<input type=\"hidden\" id=\"").append(id).append(DELETE_CALLBACK).append("\" value=\"").append(deleteCallback).append("\" />");
		String openBtnID= this.getId()+UPLOAD_ID;
		if(StringUtils.isNotBlank(zwTemSel) && "true".equals(zwTemSel)){
			uploadAble = false;
		}
		if(uploadAble){
			body.append("&nbsp;&nbsp;&nbsp;&nbsp;<img  src=\"");
			if(null!= isZw && isZw.equals("true")){
				body.append(UPLOADIMG_ZW);
			}else{
				body.append(UPLOADIMG_FJ);
			}
			body.append("\" style=\"cursor:pointer;\" title=\"上传附件\"").append(" id=\"").append(openBtnID).append("\" ");			
			
			if(!ismain){
				body.append("onclick=\"openAttsUpDialog('").append(argsStr).append("','"+isFirst+"')\" />");
				body.append("<input type=\"button\" id=\"").append(id).append(REF).append("\"  style=\"display:none\" onclick=\"").append("initAtts('").append(argsStr).append("');").append("\" />");
			}else{
				body.append("onclick=\"openAttsextUpDialog('").append(argsStr).append("')\" />");
				body.append("<input type=\"button\" id=\"").append(id).append(REF).append("\" style=\"display:none\" onclick=\"").append("initAttsext('").append(argsStr).append("');").append("\" />");
			}
		}else{
			if(!("true".equals(zwTemSel))){
				body.append("&nbsp;&nbsp;&nbsp;&nbsp;<img  src=\"");
				if(null!= isZw && isZw.equals("true")){
					body.append(UPLOADIMG_ZW);
				}else{
					body.append(UPLOADIMG_FJ);
				}
				body.append("\" style=\"cursor:pointer;\" title=\"上传附件\"").append(" id=\"").append(openBtnID).append("\" ");			
				
				if(!ismain){
					body.append("onclick=\"\" />");
					body.append("<input type=\"button\" id=\"").append(id).append(REF).append("\"  style=\"display:none\" onclick=\"").append("initAtts('").append(argsStr).append("');").append("\" />");
				}else{
					body.append("onclick=\"\" />");
					body.append("<input type=\"button\" id=\"").append(id).append(REF).append("\" style=\"display:none\" onclick=\"").append("initAttsext('").append(argsStr).append("');").append("\" />");
				}
			}
		}
		//添加高拍仪
		if(scanAble){
			body.append("&nbsp;&nbsp;<img src=\"").append(GPY_UPLOADIMG).append("\" style=\"cursor:pointer;\" title=\"高拍仪\"").append(" id=\"").append(openBtnID).append("\" ");
			body.append("onclick=\"openCaptureDialog('").append(argsStr).append("')\" />");
		}
		//正文模板
		if("true".equals(zwTemSel)){
			body.append("&nbsp;&nbsp;<img src=\"").append(ZWMBIMG_ZWMB).append("\" style=\"cursor:pointer;\" title=\"正文模板\"").append(" id=\"").append(openBtnID).append("\" ");
			body.append("onclick=\"openUploadTemplate('").append(argsStr).append("','"+isFirst+"')\" />");
		}
		
		body.append("<input type=\"button\" id=\"").append(id).append(REF).append("\"  style=\"display:none\" onclick=\"").append("initAtts('").append(argsStr).append("');").append("\" />");
		
		body.append("<script type=\"text/javascript\">");
		body.append("initAttsext('").append(argsStr).append("');");
		body.append("</script>");
		body.append("</div>");
		body.append("</div>");
		return body.toString();
	}
	
	/**
	 * 
	 * 描述：将数据中的元素以字符串的形势用str连接起来返回<br>
	 *
	 * @param obj
	 * @param str
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-11-30 下午05:03:42
	 */
	private String getArgStr(Object[]obj,String str){
		StringBuffer sb = new StringBuffer();
		for(Object o:obj){
			if(o==null){
				sb.append("").append(str);
			}else{
				sb.append(o.toString().trim()).append(str);
			}
		}
		return sb.toString().substring(0,sb.toString().lastIndexOf(str));
	}
}
