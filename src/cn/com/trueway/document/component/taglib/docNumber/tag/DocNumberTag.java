package cn.com.trueway.document.component.taglib.docNumber.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class DocNumberTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1510596380534510365L;
	/**
	 * 标签ID
	 */
	private String tagId;
	/**
	 * 部门ID
	 */
	private String webId;
	/**
	 * 流程ID
	 */
	private String defineId;
	/**
	 * 
	 */
	private String displayType;
	
	private String displayVlaue;
	
	private String displayClass;
	
	private String showId;
	
	private String name;
	
	private String value;
	
	private boolean attach = false;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getDisplayVlaue() {
		return displayVlaue==null?"":displayVlaue;
	}

	public void setDisplayVlaue(String displayVlaue) {
		this.displayVlaue = displayVlaue==null?"":displayVlaue;
	}
	
	public String getDisplayClass() {
		return displayClass;
	}

	public void setDisplayClass(String displayClass) {
		this.displayClass = displayClass;
	}

	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}

	public String getDefineId() {
		return defineId;
	}

	public void setDefineId(String defineId) {
		this.defineId = defineId;
	}
	
	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

	public boolean isAttach() {
		return attach;
	}

	public void setAttach(boolean attach) {
		this.attach = attach;
	}

	@Override
	public int doStartTag() throws JspException {
			try {
				JspWriter out = pageContext.getOut();
				String body = this.parseTag();
				out.print(body);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return TagSupport.SKIP_BODY;
	}
	private String parseTag(){
		StringBuilder sb = new StringBuilder();
		if(showId==null){
			sb.append("<input id='").append(tagId).append("'");
			if(value!=null&&value.trim().length()!=0){
				sb.append(" value='").append(value).append("'");
			}else{
				sb.append(" style='display:none;' ");
			}
			if(name!=null&&name.trim().length()!=0){
				sb.append(" name='").append(name).append("'");
			}
			sb.append(" readonly='readonly' type=\"text\" />&nbsp;");
		}
		//sb.append("<input id=\"defid\" type=\"hidden\" value=\""+defineId+"\"/>&nbsp;");
		if(displayType!=null&&displayType.trim().length()!=0){
			if(displayType.equals("radio")){
				sb.append("<input type=\"radio\" value=\""+displayVlaue+"\" onclick=\"open_docnumber_dialog('"+webId+"','"+defineId+"','"+value+"');\"/>");
			}else{
				sb.append("<input type=\"button\" style=\"border:1px solid #ccc; padding:5px 5px; background:#F1F5F6; cursor:pointer;\" value=\""+displayVlaue+"\" onclick=\"open_docnumber_dialog('"+webId+"','"+defineId+"','"+value+"');\" />");
			}
		}else{
			sb.append("<input id='exterior").append(tagId).append("'");
			if(displayVlaue==null||displayVlaue.trim().length()==0){
				sb.append(" style='display:none;' ");
			}
			sb.append(" type=\"button\"  style=\"border:1px solid #ccc;height:25px;width:65px;text-align: center;  background:#F1F5F6; cursor:pointer;\" value=\""+displayVlaue+"\" onclick=\""+tagId+"_open_docnumber_dialog('"+webId+"','"+defineId+"','"+value+"');\" />");
		}
		sb.append("\n<script type=\"text/javascript\">\n");
		sb.append("function "+tagId+"_open_docnumber_dialog(webid,defid,value){\n");
		//根据填入value得值取值(value可能是某个标签的ID,也可能就是文号)
		sb.append("\t var val = document.getElementById(value); \n");
		sb.append("\t value = val==null?value:(val.type==undefined?val.innerHTML:val.value); \n");
		sb.append("\t var obj = new Object(); \n");
		if(attach){
			sb.append("\t var retVal = window.showModalDialog('docNumber_toAttach.do?docNum='+encodeURI(value)+'&defid='+defid+'&t='+new Date().getTime(), obj, 'dialogWidth:950px;dialogHeight:380px; status: no; scrollbars: yes; Resizable: yes; help: no;');\n");
		}else{
			sb.append("\t var retVal = window.showModalDialog('docNumber_toDocNumberModel.do?webid='+webid+'&defineId='+defid+'&value='+encodeURI(value)+'&t='+new Date().getTime(), obj, 'dialogWidth:950px;dialogHeight:380px; status: no; scrollbars: yes; Resizable: yes; help: no;');\n");
		}
		sb.append("\t if(retVal === 'noChange'||retVal==undefined){");
    	sb.append("		return false;");
    	sb.append("}\n");
    	//回填生成的文号(指定Id)
		if(showId!=null&&!"".equals(showId.trim())){
			sb.append("\t var x = document.getElementById('"+showId+"'); \n");
			sb.append("\t if(x.type==undefined){ \n");
			sb.append("\t 	x.innerHTML=retVal.docnumber; \n");
			sb.append("\t }else{ \n");
			sb.append("\t 	x.value=retVal.docnumber; \n");
			sb.append("\t }\n");
		}
		//回填生成的文号(本标签)
		if(showId==null||"".equals(showId.trim())){
			sb.append("\t document.getElementById('"+tagId+"').value=retVal.docnumber; 	\n");
			sb.append("\t document.getElementById('"+tagId+"').style.display='';	\n");
		}
		sb.append("}\n</script>\n");
		sb.append("\n<script type=\"text/javascript\">\n");
		sb.append("function touchme(tagId){ \n");
		sb.append("	document.getElementById('exterior'+tagId).click(); \n");
		sb.append("}\n");
		sb.append("\n</script>\n");
		return sb.toString();
	}
}
