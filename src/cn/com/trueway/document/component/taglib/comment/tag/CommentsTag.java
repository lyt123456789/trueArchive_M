/**
 * 
 */
package cn.com.trueway.document.component.taglib.comment.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.workflow.core.pojo.Employee;

/** 
 * 描述：意见标签类<br>                                     
 * 作者：顾锡均<br>
 * 创建时间：Feb 22, 2010<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式:同创建时间><br>
 * 修改原因及地方： <修改原因描述><br>
 * 版本：v1.0 <br>           
 * JDK版本：JDK1.5
 */
public class CommentsTag extends TagSupport{
	private static final long serialVersionUID = 7433869662262186949L;
	private String id;
	private String instanceId;
	private String currentStepId;
	private String title;
	private String signImgSrc = "./widgets/component/taglib/comment/imgs/typein_old.gif";//
	private String signTooltip = "点击我输入意见";//
	private String signedInfoBlock = "SIGN_COMMENT_BLOCK";//
	private String width = "100%";
	private boolean extendAddFun;
	private boolean addAbled;
	private boolean updateAbled;
	private boolean deleteAbled;
	private String nodisColumns="";

	//录入
	private boolean typeinAble;
	//手写
	private boolean handWriteAble;
	//已阅
	private boolean haveReadAble;
	//审核
	private boolean examineAble;
	//记录时间
	private boolean recordTimeAble;
	
	//是否是按录入时间倒序排列
	private boolean isDesc;

	//是否放出插入办理列表链接按钮
	private boolean processLinkAble;
	//是否满意
	private boolean isSatisfied;
	
	private static final String TYPEIN_TITLE="点击录入意见";
	private static final String TYPEIN_IMAG_PATH="./widgets/component/taglib/comment/imgs/typein_new.png";
	
	private static final String HANDWRITE_TITLE="点击手写签批";
	private static final String HANDWRITE_IMAG_PATH="./widgets/component/taglib/comment/imgs/write_new.png";
	
	private static final String HAVEREAD_TITLE="已阅";
	private static final String HAVEREAD_IMAG_PATH="./widgets/component/taglib/comment/imgs/read_new.png";
	
	//private static final String SHENHE_TITLE="审核";
	//private static final String SHENHE_IMAG_PATH="./widgets/component/taglib/comment/imgs/shenhe.gif";
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getCurrentStepId() {
		return currentStepId;
	}

	public void setCurrentStepId(String currentStepId) {
		this.currentStepId = currentStepId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSignImgSrc() {
		return signImgSrc;
	}

	public void setSignImgSrc(String signImgSrc) {
		this.signImgSrc = signImgSrc;
	}

	public String getSignTooltip() {
		return signTooltip;
	}

	public void setSignTooltip(String signTooltip) {
		this.signTooltip = signTooltip;
	}

	public String getSignedInfoBlock() {
		return signedInfoBlock;
	}

	public void setSignedInfoBlock(String signedInfoBlock) {
		this.signedInfoBlock = signedInfoBlock;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isExtendAddFun() {
		return extendAddFun;
	}

	public void setExtendAddFun(boolean extendAddFun) {
		this.extendAddFun = extendAddFun;
	}

	public boolean isAddAbled() {
		return addAbled;
	}

	public void setAddAbled(boolean addAbled) {
		this.addAbled = addAbled;
	}

	public boolean isUpdateAbled() {
		return updateAbled;
	}

	public void setUpdateAbled(boolean updateAbled) {
		this.updateAbled = updateAbled;
	}

	public boolean isDeleteAbled() {
		return deleteAbled;
	}

	public void setDeleteAbled(boolean deleteAbled) {
		this.deleteAbled = deleteAbled;
	}

	public String getNodisColumns() {
		return nodisColumns;
	}

	public void setNodisColumns(String nodisColumns) {
		this.nodisColumns = nodisColumns;
	}

	
	public boolean isTypeinAble() {
		return typeinAble;
	}

	public void setTypeinAble(boolean typeinAble) {
		this.typeinAble = typeinAble;
	}

	public boolean isHandWriteAble() {
		return handWriteAble;
	}

	public void setHandWriteAble(boolean handWriteAble) {
		this.handWriteAble = handWriteAble;
	}

	public boolean isHaveReadAble() {
		return haveReadAble;
	}

	public void setHaveReadAble(boolean haveReadAble) {
		this.haveReadAble = haveReadAble;
	}

	public boolean isExamineAble() {
		return examineAble;
	}

	public void setExamineAble(boolean examineAble) {
		this.examineAble = examineAble;
	}

	public boolean isRecordTimeAble() {
		return recordTimeAble;
	}

	public void setRecordTimeAble(boolean recordTimeAble) {
		this.recordTimeAble = recordTimeAble;
	}

	public boolean isDesc() {
		return isDesc;
	}

	public void setIsDesc(boolean isDesc) {
		this.isDesc = isDesc;
	}

	public boolean isProcessLinkAble() {
		return processLinkAble;
	}

	public void setProcessLinkAble(boolean processLinkAble) {
		this.processLinkAble = processLinkAble;
	}

	public boolean isSatisfied() {
		return isSatisfied;
	}

	public void setIsSatisfied(boolean isSatisfied) {
		this.isSatisfied = isSatisfied;
	}

	@Override
	public int doStartTag() {
		JspWriter out = pageContext.getOut() ;
		StringBuilder sb = new StringBuilder();
		sb.append("<table width=\"");
		sb.append(width);
		sb.append("\" id=\"");
		sb.append(id);
		sb.append("\">");
		sb.append(this.genSignBlock());
		sb.append("<input type=\"hidden\"").append(" name=\"commentInstanceId\" value=\"").append(instanceId).append("\"/>");
		sb.append("<input type=\"hidden\"").append(" name=\"title\" value=\"").append(title).append("\"/>");
		sb.append("<input type=\"hidden\"").append(" name=\"addAbled\" value=\"").append(addAbled).append("\"/>");
		sb.append("<input type=\"hidden\"").append(" name=\"updateAbled\" value=\"").append(updateAbled).append("\"/>");
		sb.append("<input type=\"hidden\"").append(" name=\"deleteAbled\" value=\"").append(deleteAbled).append("\"/>");
		sb.append("<input type=\"hidden\"").append(" name=\"examineAble\" value=\"").append(examineAble).append("\"/>");
		sb.append("<input type=\"hidden\"").append(" name=\"nodisColumns\" value=\"").append(nodisColumns).append("\"/>");
		sb.append("<input type=\"hidden\"").append(" name=\"recordTimeAble\" value=\"").append(recordTimeAble).append("\"/>");
		sb.append("<input type=\"hidden\"").append(" name=\"isDesc\" value=\"").append(isDesc).append("\"/>");
		sb.append("<input type=\"hidden\"").append(" name=\"processLinkAble\" value=\"").append(processLinkAble).append("\"/>");
		sb.append("<input type=\"hidden\"").append(" name=\"isSatisfied\" value=\"").append(isSatisfied).append("\"/>");
		if(currentStepId!=null && !currentStepId.trim().equals("")){
			sb.append("<input type=\"hidden\"").append(" name=\"currentStepId\"  value=\"").append(currentStepId).append("\"/>"); 
		}else{
			sb.append("<input type=\"hidden\"").append(" name=\"currentStepId\"  value=\"\"/>"); 
		}
		sb.append("</table>");
		sb.append("<script type=\"text/javascript\">initCommentFun('");
		sb.append(id);
		sb.append("');</script>");
		sb.append("<script type=\"text/javascript\">loadRecordTime('");
		sb.append(instanceId);
		sb.append("');</script>");
		try {
			out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	private String genSignBlock(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div name=\"commentDivId\">");
		//自己指定
		if(extendAddFun){
			addAbled=true;
			updateAbled=true;
			deleteAbled=true;
			Object[] args = new Object[]{id,"",instanceId,addAbled,deleteAbled,updateAbled,false};
			sb.append(this.getImgStr(signImgSrc, signTooltip, "onclick", "openCommentDialog", args));
		}
		
		//录入按钮
		if(!extendAddFun && typeinAble){
			addAbled=true;
			updateAbled=true;
			deleteAbled=true;
			Object[] args = new Object[]{id,"",instanceId,addAbled,deleteAbled,updateAbled,false,recordTimeAble,handWriteAble,processLinkAble,isSatisfied};
			sb.append(this.getImgStr(TYPEIN_IMAG_PATH, TYPEIN_TITLE, "onclick", "openCommentDialog", args)).append("&nbsp;&nbsp;&nbsp;");
		}
		//手写按钮
		if(!extendAddFun && handWriteAble){
//			String userId =(String)pageContext.getSession().getAttribute(Constant.SESSION_USER_ID);
//			String userName = (String)pageContext.getSession().getAttribute(Constant.SESSION_USER_NAME);
			//获取当前登录用户
			Employee emp = (Employee) pageContext.getSession().getAttribute(MyConstants.loginEmployee);
			String userId = emp.getEmployeeGuid();
			String userName = emp.getEmployeeName();
			Object[] args = new Object[]{id,instanceId,currentStepId,userId,userName,recordTimeAble};
			sb.append(this.getImgStr(HANDWRITE_IMAG_PATH, HANDWRITE_TITLE, "onclick", "writeAlert", args)).append("&nbsp;&nbsp;&nbsp;");
		}
		//已阅按钮
		if(!extendAddFun && haveReadAble){
			Object[] args = new Object[]{id,"",instanceId,currentStepId,addAbled,deleteAbled,updateAbled,false,recordTimeAble};
			sb.append(this.getImgStr(HAVEREAD_IMAG_PATH, HAVEREAD_TITLE, "onclick", "saveCommentWithYY", args));
		}
		sb.append("</div>");
		return sb.toString();
	}
	
	private String getImgStr(String src,String title,String eventType,String funName,Object[] funArgs){
		StringBuilder imgStr = new StringBuilder();
		imgStr.append("<img ");
		imgStr.append("src=\"");
		imgStr.append(src);
		imgStr.append("\" ");
		imgStr.append("title=\"");
		imgStr.append(title);
		imgStr.append("\" id=\"");
		if((HANDWRITE_TITLE).equals(title)){
			imgStr.append(funArgs[0]+"handwrite");
		}else if((HAVEREAD_TITLE).equals(title)){
			imgStr.append(funArgs[0]+"haveread");
		}else{
			imgStr.append(funArgs[0]+"luru");
		}
		imgStr.append("\" name=\"");
		imgStr.append("yjluru");
		imgStr.append("\" ");
		imgStr.append("style=\"cursor:pointer;\" ");
		imgStr.append(eventType).append("=\"javascript:");
		imgStr.append(funName).append("(").append(this.getArgStr(funArgs));
		imgStr.append(")\" />");
		return imgStr.toString();
	}
	
	/**
	 * 
	 * 描述：拼接调用JS方法所需要的参数<br>
	 *
	 * @param obj
	 * @param str
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-11-30 下午05:03:42
	 */
	private String getArgStr(Object[]obj){
		StringBuilder sb = new StringBuilder();
		int size = obj.length;
		for(int i=0;i<size;i++){
			Object o = obj[i];
			if(i==size-1){
				if(o==null) {
					sb.append(this.surroundStr("", "'"));
				}else if(o instanceof Boolean){
					sb.append(this.surroundStr(o.toString(), ""));
				}else {
					sb.append(this.surroundStr(o.toString(), "'"));
				}
				break;
			}
			if(o==null) {
				sb.append(this.surroundStr("", "'")).append(",");
			}else if(o instanceof Boolean){
				sb.append(this.surroundStr(o.toString(), "")).append(",");
			}else {
				sb.append(this.surroundStr(o.toString(), "'")).append(",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * 描述：处理字符串
	 *
	 * @param str
	 * @param arg
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-7 下午01:48:12
	 */
	private String surroundStr(String str,String arg){
		if(str!=null&&arg!=null){
			return arg+str+arg;
		}
		return "";
	}
}
