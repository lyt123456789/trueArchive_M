/**
 * 
 */
package cn.com.trueway.document.component.taglib.comment.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.MineUtils;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.document.component.taglib.comment.model.po.Comment;
import cn.com.trueway.document.component.taglib.comment.model.po.CommentAtt;
import cn.com.trueway.document.component.taglib.comment.model.po.PersonalComment;
import cn.com.trueway.document.component.taglib.comment.service.CommentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.service.TableInfoService;


/** 
 * 描述：审核意见标签Action<br>                                     
 * 作者：顾锡均<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式:同创建时间><br>
 * 修改原因及地方： <修改原因描述><br>
 * 版本：v1.0 <br>           
 * JDK版本：JDK1.5
 */
public class CommentAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6830183329505659435L;
	private Comment comment;
	
	private String commentTitle;
	
	private String currentStepId;
	
	private boolean addAbled;
	
	private boolean updateAbled;
	
	private boolean deleteAbled;
	
	private boolean qianpiAbled;//是否手写签批  add in 20110902
	
	private boolean isSatisfied;//是否满意
	
	private CommentService commentService;
	
	private TableInfoService tableInfoService;
	
	private File commentFile;
	
	private String commentFileFileName;
	
	private PersonalComment personalComment;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static final String CEB_ICON_PATH = "./widgets/component/taglib/comment/imgs/ceb.gif";
	private static final String WORD_ICON_PATH = "./widgets/component/taglib/comment/imgs/word.gif";
	private static final String DOWN_ICON_PATH = "./widgets/component/taglib/comment/imgs/down.gif";

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public String getCommentTitle() {
		return commentTitle;
	}

	public void setCommentTitle(String commentTitle) {
		this.commentTitle = commentTitle;
	}

	public String getCurrentStepId() {
		return currentStepId;
	}

	public void setCurrentStepId(String currentStepId) {
		this.currentStepId = currentStepId;
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

	public boolean isAddAbled() {
		return addAbled;
	}

	public void setAddAbled(boolean addAbled) {
		this.addAbled = addAbled;
	}

	public boolean isQianpiAbled() {
		return qianpiAbled;
	}

	public void setQianpiAbled(boolean qianpiAbled) {
		this.qianpiAbled = qianpiAbled;
	}
	
	public File getCommentFile() {
		return commentFile;
	}

	public void setCommentFile(File commentFile) {
		this.commentFile = commentFile;
	}

	public String getCommentFileFileName() {
		return commentFileFileName;
	}

	public void setCommentFileFileName(String commentFileFileName) {
		this.commentFileFileName = commentFileFileName;
	}
	
	public String test(){
		return "test";
	}
	
	public boolean isSatisfied() {
		return isSatisfied;
	}

	public void setSatisfied(boolean isSatisfied) {
		this.isSatisfied = isSatisfied;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}
	
	public PersonalComment getPersonalComment() {
		return personalComment;
	}

	public void setPersonalComment(PersonalComment personalComment) {
		this.personalComment = personalComment;
	}

	@Override
	public String execute() {
//		String userId = (String)getSession().getAttribute(Constant.SESSION_USER_ID);
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId =  emp.getEmployeeGuid();
		//获取意见标签ID
		String commentObjId = getRequest().getParameter("commentObjId");
		//获取当前步骤ID
		String currentStepId = getRequest().getParameter("currentStepId");
		//获取要显示的列名
		String disColumns=getRequest().getParameter("nodisColumns");
		//获取是否要显示审核
		Boolean examineAble = Boolean.valueOf(getRequest().getParameter("examineAble"));
		Boolean isDesc = Boolean.valueOf(getRequest().getParameter("isDesc"));
		
		Boolean recordTimeAble=Boolean.valueOf(getRequest().getParameter("recordTimeAble"));
		Boolean processLinkAble=Boolean.valueOf(getRequest().getParameter("processLinkAble"));
		Boolean isSatisfied=Boolean.valueOf(getRequest().getParameter("isSatisfied"));
		//查出所有符合条件的标签
		List<Comment> comments = commentService.findByInstanceId(comment.getInstanceId(),commentObjId,isDesc);
		
		StringBuilder builder = new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
		builder.append("<table style=\"border-collapse:collapse;border-spacing:0;\" cellspace=\"0\" cellpadding=\"0\" width=\"90%\" class=\"jQswapbgcolor\">");
		for (int i=0;i<comments.size();i++) {
			boolean addAbled = this.addAbled;
			boolean deleteAbled = this.deleteAbled;
			boolean updateAbled = this.updateAbled;
			Comment oneComment=comments.get(i);
			String new_add_cab_id="";
			if (getSession().getAttribute("new_add_cab_id")!=null) {
				new_add_cab_id=getSession().getAttribute("new_add_cab_id").toString();
			}
			if (!new_add_cab_id.equals("")&&new_add_cab_id.equals(oneComment.getCabWriteId())) {
				builder.append("<tr><td  colspan=\"2\"><a type=\"text\" name=\"input_new_cab_pos\" style=\"border:0px;height:1px;\"/><a id=\"a_new_cab_pos\" href=\"#input_new_cab_pos\"></a></td></tr>");
			}			
			builder.append("<tr ").append("id=\"").append(oneComment.getId()).append("\" ");
			if(examineAble &&(oneComment.getCabWrite()==null||oneComment.getCabWrite().equals(""))){
				//需要审核时无论是不是本人 当前步骤都能修改 但不能删除  (手写除外)
				addAbled=true;
				if(!oneComment.getUserId().equals(userId)){
					deleteAbled=false;
				}else{
					deleteAbled=true;
				}
				updateAbled=true;
			}else if(!oneComment.getUserId().equals(userId)){
					//不是本人的意见
					addAbled=false;
					deleteAbled=false;
					updateAbled=false;
			}else if(!oneComment.getStepId().equals(currentStepId)){
				WfProcess wfprocess = commentService.getProcessById(currentStepId);
				if(wfprocess.getStepIndex() != 1){
					//不是当前步骤
					addAbled=false;
					deleteAbled=false;
					updateAbled=false;
				}
			}
			if(((addAbled && updateAbled) || deleteAbled ) && oneComment.getUserId().equals(userId)){
				builder.append(" ondblclick=\"");
				builder.append("javascript:openCommentDialog('").append(commentObjId).append("','").append(oneComment.getId()).append("','").append(comment.getInstanceId()).append("','");
				builder.append(addAbled).append("','");
				builder.append(deleteAbled).append("','").append(updateAbled);
				builder.append("',");
				//add in  20111012 by panh 
				if (oneComment.getCabWrite()!=null&&oneComment.getCabWriteId()!=null) {
					builder.append(true);
				}else {
					builder.append(false);
				}
				builder.append(",").append(recordTimeAble).append(",").append(false).append(",").append(processLinkAble);
				if (!("").equals(oneComment.getIsSatisfied())) {
					builder.append(",").append(oneComment.getIsSatisfied());
				}else {
					builder.append(",").append("null");
				}
				
				builder.append(")\" title=\"");
				if (oneComment.getCabWrite()!=null&&oneComment.getCabWriteId()!=null) {
					builder.append("双击鼠标左键删除意见信息");
				}else {
					builder.append("双击鼠标左键查看、修改、删除意见信息");
				}
				builder.append("\"");
			}
			builder.append(">");
			
			//加入字符串区分手写签字或普通文字
			String content="";
			if (oneComment.getCabWrite()!=null&&!oneComment.getCabWrite().equals("")) {
				content="<div style='display:none;'>"+oneComment.getCabWrite()+"</div><div style='display:none;'>"+oneComment.getCabWriteId()+"</div><div></div><div style='display:none;'>"+oneComment.getUserName()+"</div>";
			}else {
				content=oneComment.getContent().replaceAll("\r\n", "<br/>");
			}
			 if (!("").equals(oneComment.getIsSatisfied()) && oneComment.getIsSatisfied()!=null) {
		        if (oneComment.getIsSatisfied() == 0) {
		          builder.append("<td align=\"left\" colspan=\"2\">").append("<span>满意,</span>");
		          if (disColumns.indexOf("content") == -1) {
		            builder.append("<span>").append(content).append("</span>");
		          }
		          builder.append("</td>");
		        } else if (oneComment.getIsSatisfied() == 1) {
		          builder.append("<td align=\"left\" colspan=\"2\">").append("<span>不满意,</span>");
		          if (disColumns.indexOf("content") == -1) {
		            builder.append("<span>").append(content).append("</span>");
		          }
		          builder.append("</td>");
		        }
		      } else if (disColumns.indexOf("content") == -1) {
		    	  //content,name,time
		        builder.append("<td align=\"left\" colspan=\"2\">").append(content).append("</td>");
		      }
		
			builder.append("</tr>");
			
			//附件整合
			List<CommentAtt> attList = commentService.findCommnetAtts(oneComment.getId());
			for (CommentAtt att:attList) {
				builder.append("<tr>");
				builder.append("<td align=\"center\">"+att.getFileName()+"</td>");
				builder.append("<td align=\"center\">");
				String fileUrl =getRequest().getScheme()+"://"+ getRequest().getServerName()+":"+getRequest().getServerPort()+getRequest().getContextPath()+"/comment_downloadAtt.do?location="+att.getFileLocation();
				if("ceb".equals(att.getFileType())){
					fileUrl+="&name=a.ceb";
					builder.append("<img id=\"ceb_att_img\" src="+CEB_ICON_PATH+" title=\"点击预览\" onclick=\"viewCeb('"+fileUrl+"','"+att.getFileLocation()+"');\"/>");
					builder.append("&nbsp;&nbsp;");
				}
				if("doc".equals(att.getFileType())){
					fileUrl+="&name=a.doc";
					builder.append("<img id=\"ceb_att_img\" src="+WORD_ICON_PATH+" title=\"点击预览\" onclick=\"viewDoc('"+att.getFileLocation()+"');\"/>");
					builder.append("&nbsp;&nbsp;");
				}
				builder.append("<img id=\"ceb_att_img\" src="+DOWN_ICON_PATH+" title=\"点击下载\" onclick=\"downloadAtt('"+att.getFileName()+"','"+att.getFileLocation()+"');\"/>");
				builder.append("</td>");
				builder.append("</tr>");
			}
			
			if (oneComment.getCabWriteId()==null||oneComment.getCabWrite()==null||oneComment.getCabWriteId().toUpperCase().equals("NULL")) {
				if (disColumns.indexOf("name")==-1) {
					builder.append("<tr><td style=\"width:70%;\"></td>");
					builder.append("<td style=\"text-align:center;\">");
					builder.append(oneComment.getUserName());
					builder.append("</td>");
					builder.append("</tr>");
				}
				if (disColumns.indexOf("time")==-1) {
					builder.append("<tr><td style=\"width:70%;\"></td>");
					builder.append("<td style=\"text-align:center;\">");
					builder.append(dateFormat.format(oneComment.getSigndate()));
					builder.append("</td>");
					builder.append("</tr>");
				}
			}
			if (oneComment.getCabWriteId()!=null&&oneComment.getCabWrite()!=null&&((addAbled && updateAbled) || deleteAbled)) {
					builder.append("<tr><td style=\"width:70%;\"></td>");
					builder.append("<td style=\"text-align:center;\">");
					builder.append("<input class=\"del_btn\" type=\"button\" value = \"删除\" title=\"点击删除手写意见\" onclick=\"deleteComment(");
					builder.append("'"+commentObjId+"','"+oneComment.getId()+"','"+oneComment.getInstanceId()+"')\"/>");
					builder.append("</td>");
					builder.append("</tr>");
			}
			if((oneComment.getCabWrite()==null||!oneComment.getCabWrite().equals(""))&&examineAble){
				if(!oneComment.getUserId().equals(userId)){
					builder.append("<tr><td style=\"width:70%;\"></td>");
					builder.append("<td style=\"text-align:center;\">");
					builder.append("<img alt=\"审核\" id=\"sh\" src=\""+getRequest().getContextPath()+"/widgets/component/taglib/comment/imgs/examine_new.png\" style=\"cursor:pointer\"");
					builder.append("onclick=\"");
					builder.append("javascript:openCommentDialog('").append(commentObjId).append("','").append(oneComment.getId()).append("','").append(comment.getInstanceId()).append("','");
					builder.append(addAbled).append("','");
					builder.append(deleteAbled).append("','").append(updateAbled);
					builder.append("',").append(qianpiAbled);
					builder.append(",").append(recordTimeAble).append(",").append(false).append(",").append(processLinkAble);;
					builder.append(",").append(isSatisfied);
					builder.append(")");  
					builder.append("\"/>");
					builder.append("</td>");
					builder.append("</tr>");
				}
			}
		}
		builder.append("</table>");
//		System.out.println("builder  :  "+builder.toString());
		try {
			super.renderResponse(super.getResponse(), builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 增加或者更新意见内容
	 * @return
	 * @throws IOException 
	 */
	public String addOrModifyComment(){
//		String userId = (String)getSession().getAttribute(Constant.SESSION_USER_ID);
//		String userName = (String)getSession().getAttribute(Constant.SESSION_USER_NAME);
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId =  emp.getEmployeeGuid();
		String userName = emp.getEmployeeName(); 
		
		String attids = getRequest().getParameter("attids");
		String isSatisfied = getRequest().getParameter("isSatisfied");
		getSession().setAttribute("new_add_cab_id",comment.getCabWriteId());
		if(comment.getId()!=null && comment.getId().trim().length()==0){
			comment.setId(null);
		}
		//增加或者修改意见信息
		try {
			comment.setUserId(userId);
			comment.setUserName(userName);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			comment.setSigndate(Timestamp.valueOf(sdf.format(new Date())));
			if(isSatisfied!=null && !("").equals(isSatisfied)){
				comment.setIsSatisfied(Integer.parseInt(isSatisfied));
			}
			if(attids!=null){
				commentService.saveOrUpdate(comment,Arrays.asList(attids.split(",")));
			}else{
				commentService.saveOrUpdate(comment,null);
			}
			comment = new Comment();
			super.renderResponse(super.getResponse(), "1");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				super.renderResponse(super.getResponse(), "0");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	/**
	 * 记录时间
	 * @author zhouxy
	 * @return
	 * @throws IOException 
	 * @throws IOException 
	 */
	public void recordTime() throws IOException{
		PrintWriter out =getResponse().getWriter();
//		String userName = (String)getSession().getAttribute(Constant.SESSION_USER_NAME);
//		String personnelName= processMonitorService.getPersonnelNameByInstanceId(comment.getInstanceId());
//		//根据instanceId求出oa_doc_bw表中的cyry(传阅人员)
//		String cyry=processMonitorService.getCyryByInstanceId(comment.getInstanceId());
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		StringBuilder sb=new StringBuilder();
//		if(personnelName !=null &&!"".equals(personnelName)){
//			String[] personnelNameArray=personnelName.split(",");
//			if(Arrays.asList(personnelNameArray).contains(userName.toString())){
//				//登录用户在“需要记录时间的用户范围之内”
//				if(cyry !=null && !cyry.equals("")){
//					if(cyry.indexOf(userName.toString())==-1){
//						//本用户没有记录时间，需在原有的cyry基础上面增加本用户记录然后更新数据库
//						sb.append(cyry.trim()).append(userName.trim()).append(comment.getInstanceId()).append(",").append(sdf.format(new Date())).append(";");
//						processMonitorService.updateBwCyrybyInstanceId(sb.toString(), comment.getInstanceId());
//						//out.print(sb.toString());
//						cyry = sb.toString();
//					}
//				}else{
//					sb.append(userName.trim()).append(comment.getInstanceId()).append(",").append(sdf.format(new Date())).append(";");
//					processMonitorService.updateBwCyrybyInstanceId(sb.toString(), comment.getInstanceId());
//					//out.print(sb.toString());
//					cyry = sb.toString();
//				}
//			}
//		}
//		String officeName = processMonitorService.getOfficeNameByInstanceId(comment.getInstanceId());
//		String cyks = processMonitorService.getCyksByInstanceId(comment.getInstanceId());
//		String depName = (String)getSession().getAttribute(Constant.DEPARTMENT_NAME);
//		if(officeName !=null){
//			String[] officeNameArray=officeName.split(",");
//			if(Arrays.asList(officeNameArray).contains(depName.toString())){
//				//登录用户在“需要记录时间的用户范围之内”
//				if(cyks !=null && !cyks.equals("")){
//					if(cyks.indexOf(depName.toString())==-1){
//						//本用户没有记录时间，需在原有的cyry基础上面增加本用户记录然后更新数据库
//						sb.append(cyks.trim()).append(depName.trim()).append(comment.getInstanceId()).append(",").append(sdf.format(new Date())).append(";");
//						processMonitorService.updateBwCyksbyInstanceId(sb.toString(), comment.getInstanceId());
//						cyks = sb.toString();
//					}
//				}else{
//					sb.append(depName.trim()).append(comment.getInstanceId()).append(",").append(sdf.format(new Date())).append(";");
//					processMonitorService.updateBwCyksbyInstanceId(sb.toString(), comment.getInstanceId());
//					cyks = sb.toString();
//				}
//			}
//		}
//		out.print((cyry==null?"":cyry)+(cyks==null?"":cyks));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userName = emp.getEmployeeName();
		String userId = emp.getEmployeeGuid();
		String deptName = tableInfoService.findDeptNameByUserId(userId);
		String cyry = userName + comment.getInstanceId()+"," + sdf.format(new Date());
		String cyks = deptName + comment.getInstanceId()+"," + sdf.format(new Date());
		if(("").equals(cyry)){
			out.print((cyry==null?"":cyry));
		}else if(("").equals(cyks)){
			out.print((cyks==null?"":cyks));
		}else{
			out.print((cyry==null?"":cyry) +";" +(cyks==null?"":cyks));
		}
	}
	
	/**
	 * 描述：删除记录时间 未完善，但满足现行需求<br>
	 * @author zhouxy
	 * void
	 * @throws IOException 
	 */
	public void deleteRecordTime() throws IOException{
		PrintWriter out =getResponse().getWriter();
//		String userId = (String)getSession().getAttribute(Constant.SESSION_USER_ID);
//		String userName = (String)getSession().getAttribute(Constant.SESSION_USER_NAME);
//		String personnelName= processMonitorService.getPersonnelNameByInstanceId(comment.getInstanceId());
//		String cyry=processMonitorService.getCyryByInstanceId(comment.getInstanceId());
		//根据instanceId查询出当前意见标签中含有此用户的意见是否等于零，
		//若相等就说明用户只审批了一个意见即当前意见(当前意见已经在触发此action之前删除掉，就size为零)，即可删除时间。
		//待完善：根据意见Id进行详细区别
//		int size=commentService.getCommentSizebyUserId(comment.getInstanceId(), userId);
//		List<String> list = new ArrayList<String>();
//		if(cyry!=null){
//			list=Arrays.asList(cyry.split(";"));
//		}
//		List<String> cyryList=new ArrayList<String>(list);
//		StringBuilder sb=new StringBuilder();
//		StringBuilder excludeBuilder=new StringBuilder();
//		if(size==0){//删除本用户的记录时间
//			if(cyry!=null && !cyry.equals("")){
//					for(Iterator it=cyryList.iterator(); it.hasNext();){
//							String compareField=(String)it.next();
//							if(compareField!=null && compareField!="" &&compareField.indexOf(userName+comment.getInstanceId())>-1){
//								it.remove();
//							}
//					}
//				}
//			for(String str:cyryList){
//				sb.append(str).append(";");
//			}
//			//更新数据库CYRY
//			processMonitorService.updateBwCyrybyInstanceId(sb.toString(), comment.getInstanceId());
//			//传递空白时间的值到页面上
//			List<String> list2=Arrays.asList(personnelName.split(","));
//			List<String> personneListNameList=new ArrayList<String>(list2);
//			if(personnelName !=null && personnelName!=""){
//				if(sb.toString() !=null && sb.toString()!=""){
//					for(Iterator it =personneListNameList.iterator();it.hasNext();){
//							String compareFiled=(String)it.next();
//							if(compareFiled !=null && compareFiled !="" && sb.toString().indexOf(compareFiled.trim()+comment.getInstanceId())>-1){
//								it.remove();
//							}
//						}
//					}
//				for(String str:personneListNameList){
//					excludeBuilder.append(str).append(comment.getInstanceId()).append(";");
//				}
//			}else{
//				logger.warn("未能查出personnelName，请检查表单，添加人员");
//			}
//			out.print(excludeBuilder.toString()); 
//		}else{
//			//记录时间没有变化
//			out.print(Constant.COMMENT_DELETE_NOCHANGE);
//		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userName = emp.getEmployeeName();
		String userId = emp.getEmployeeGuid();
		String deptName = tableInfoService.findDeptNameByUserId(userId);
		String cyry = userName + comment.getInstanceId()+"," + sdf.format(new Date());
		String cyks = deptName + comment.getInstanceId()+"," + sdf.format(new Date());
		if(("").equals(cyry)){
			out.print((cyry==null?"":cyry));
		}else if(("").equals(cyks)){
			out.print((cyks==null?"":cyks));
		}else{
			out.print((cyry==null?"":cyry) +";" +(cyks==null?"":cyks));
		}
	}
	/**
	 * 刷新加载已记录即已存库时间
	 * @author zhouxy
	 * @return
	 * @throws IOException 
	 * @throws IOException 
	 */
	public void loadRecordTime() throws IOException{
		PrintWriter out =getResponse().getWriter();
//		根据instanceId求出oa_doc_bw表中的cyry(传阅人员)
//		String cyry=processMonitorService.getCyryByInstanceId(comment.getInstanceId());
//		String cyks = processMonitorService.getCyksByInstanceId(comment.getInstanceId());
//		out.print((cyry==null?"":cyry)+(cyks==null?"":cyks));
		
		//查出所有符合条件的标签
		List<Comment> comments = commentService.findCommentsByInstanceId(comment.getInstanceId());
		String cyry = "";
		String cyks = "";
		for (Comment comment : comments) {
			cyry += comment.getUserName()+comment.getInstanceId()+","+comment.getSigndate()+";";
			String deptName = tableInfoService.findDeptNameByUserId(comment.getUserId());
			cyks += deptName + comment.getInstanceId()+","+comment.getSigndate()+";";
		}
		if(cyry.length()>0){
			cyry = cyry.substring(0,cyry.length()-1);
		}
		if(cyks.length()>0){
			cyks = cyks.substring(0,cyks.length()-1);
		}
		if(("").equals(cyry)){
			out.print((cyry==null?"":cyry));
		}else if(("").equals(cyks)){
			out.print((cyks==null?"":cyks));
		}else{
			out.print((cyry==null?"":cyry) +";" +(cyks==null?"":cyks));
		}
	}
	
	public String deleteComment() throws IOException{
		if (getSession().getAttribute("new_add_cab_id")!=null) {
			getSession().removeAttribute("new_add_cab_id");
		}
		Comment ss = commentService.getById(comment.getId());
		String userId = ss.getUserId();
//		String sessionUserId = (String) super.getSession().getAttribute("userId");
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String sessionUserId =  emp.getEmployeeGuid();
		//判断是否是自己的意见信息，不是自己的不可以删除
		if(userId.equals(sessionUserId)){
			try {
				commentService.deleteById(comment.getId());
				commentService.deleteCommentAtts(comment.getId());
				super.renderResponse(super.getResponse(), "1");
			} catch (Exception e) {
				e.printStackTrace();
				super.renderResponse(super.getResponse(), "0");
			}
		}else{
			try {
				super.renderResponse(super.getResponse(), "2");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		comment = new Comment();
		return null;
	}
	
	public String writeComment(){
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		if(comment.getId()==null || comment.getId().trim().equals("")){
			comment.setId(null);
			comment.setContent(null);
		}else{
			comment = commentService.getById(comment.getId());
			comment.setContent(comment.getContent().replaceAll("\r\n","<BR>"));
		}
		List<CommentAtt> attList = commentService.findCommnetAtts(comment.getId());
		//20101206 Add, 获取个人办公用语
		List<PersonalComment> personalComments = commentService.getPersonalComments(userId);
		//TODO要加的熟语  
		//===================
//		List<PersonalComment> personalComments  = new ArrayList<PersonalComment>();
//		PersonalComment   aa = new PersonalComment();
//		aa.setCmnt_id("9F827D70-499D-6291-E040-007F010022C8");
//		aa.setContent("同意");
//		aa.setUser_id("{7F000001-0000-0000-6F25-39B700000068}");
//		aa.setSort_index("1");
//		//======================
//		personalComments.add(aa);
		//
		super.getRequest().setAttribute("personalComments", personalComments);
		
		//20101207 Add, 把TagID传给页面
		super.getRequest().setAttribute("tagId", super.getRequest().getParameter("commentObjId"));
		setAddAbled(Boolean.parseBoolean(super.getRequest().getParameter("addAbled")));
		setUpdateAbled(Boolean.parseBoolean(super.getRequest().getParameter("updateAble")));
		setDeleteAbled(Boolean.parseBoolean(super.getRequest().getParameter("deleteAble")));
		
		//判断传过来的值，一个为意见标签里的值，分为：false，true； 一个为修改的时候，数据库里存的值：0和1
		String satf = getRequest().getParameter("isSatisfied");
		if(satf!=null && !("null").equals(satf) && !("").equals(satf) && !("undefined").equals(satf)){
			if(("false").equals(satf)){
				getRequest().setAttribute("isSatisfied", "null");
			}else if(("true").equals(satf)){
				getRequest().setAttribute("isSatisfied", "3");
			}else{
				getRequest().setAttribute("isSatisfied", satf);
			}
		}else{
			getRequest().setAttribute("isSatisfied", satf);
		}
		
		//add in 20110902,用于判断是否手写签批
		boolean qianpiAble=Boolean.parseBoolean(super.getRequest().getParameter("qianpiAble"));
		setQianpiAbled(qianpiAble);
		getRequest().setAttribute("qianpiAble", qianpiAble);
		//add in 20110902,用于更新页面显示flash统计图
		getRequest().setAttribute("cabwriteId", comment.getCabWriteId());
		getRequest().setAttribute("cabwrite", comment.getCabWrite());
		getRequest().setAttribute("attList",attList);
		getRequest().setAttribute("processLinkAble",getRequest().getParameter("processLinkAble"));
		return "writeComment";
	}
	
	//20101206 Add
	public String showPersonalComments(){
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String userId = emp.getEmployeeGuid();
		List<PersonalComment> personalComments = 
			commentService.getPersonalComments(userId);
		super.getRequest().setAttribute("personalComments", personalComments);
		super.getRequest().setAttribute("size", personalComments.size());
		return "showPersonalComments";
	}
	
	
	public String toUpdate(){
		String cmnt_id = super.getRequest().getParameter("cmnt_id");
		PersonalComment pc = commentService.getPCDetail(cmnt_id);
		
		super.getRequest().setAttribute("pc", pc);
		return "toUpdate";
	}
	
	public void deletePC(){
		String cmnt_id = super.getRequest().getParameter("cmnt_id");
		commentService.deletePC(cmnt_id);
	}
	
	public void savePersonalComment() throws Exception{
//		String user_id = super.getSession().getAttribute("userId").toString();
		//获取当前登录用户
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String user_id = emp.getEmployeeGuid();
		String cmnt_id = super.getRequest().getParameter("cmnt_id");
		String content = URLDecoder.decode(super.getRequest().getParameter("content"), "UTF-8");
		String sort_index = super.getRequest().getParameter("sort_index");
		
		PersonalComment pc = new PersonalComment();
		pc.setCmnt_id(cmnt_id);
		pc.setContent(content);
		pc.setSort_index(sort_index);
		pc.setUser_id(user_id);
		
		commentService.savePersonalComment(user_id, pc);
	}
	


	/**
	 * 
	 * @Title: getImgBasePath
	 * @Description: 获取签字图片保存的基路径 void    返回类型
	 */
	public void getImgBasePath(){
		//获得签字图片保存基路径
		String imgBasePath=SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+"cabImg/";
		//获得图片名称
		//String imgName=getRequest().getParameter("imgName");
		String dstPath = MineUtils.getRealImgPath("",imgBasePath); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
		
		@SuppressWarnings("unused")
		File file = new File(imgBasePath+dstPath);
				
		try {
			super.renderResponse(super.getResponse(), imgBasePath+","+dstPath);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	};
	
	/**
	 * 
	 * 描述：判断是否审核
	 * void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-7 下午05:08:24
	 * @throws IOException 
	 */
	public void isOrNotApproved() throws IOException{
		String commentObjId = getRequest().getParameter("commentObjId");
		String currentStepId = getRequest().getParameter("currentStepId");
		String userId = (String)getSession().getAttribute(Constant.SESSION_USER_ID);
		int commentCount=commentService.findCommentCount(commentObjId, currentStepId, userId);
		if(commentCount>0){
			getResponse().getWriter().write("yes");
		}else{
			getResponse().getWriter().write("no");
		}
	}
	
	/**
	 * 
	 * 描述：判断是否填写
	 * void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-13 下午05:03:09
	 */
	public void isOrNotWrite()throws IOException{
		String tagId = getRequest().getParameter("tagId");
		String stepId = getRequest().getParameter("currentStepId");
		String userId = (String)getSession().getAttribute(Constant.SESSION_USER_ID);
		int count= commentService.findCommentCount(tagId,stepId,userId); 
		if(count>0){
			getResponse().getWriter().write("yes");
		}else{
			getResponse().getWriter().write("no");
		}
	}
	
	/**
	 * 
	 * 描述：指向上传页面
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:46:14
	 */
	public String openUpload(){
		getRequest().setAttribute("commentId", getRequest().getParameter("commentId"));
		return "openUpload";
	}
	
	/**
	 * 
	 * 描述：上传附件
	 *
	 * @return String 
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-5 上午09:55:11
	 */
	public void toUploadAtt(){
		if(commentFile!=null&&commentFileFileName!=null){
			String commentId = getRequest().getParameter("commentId");
			String basePath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path"); // 得到上传文件在服务器上的基路径
			String dstPath = FileUploadUtils.getRealFilePath(commentFileFileName, basePath, "attachments/"); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
			CommentAtt att = new CommentAtt();
			att.setCommentId(commentId); 
			att.setFileName(commentFileFileName);// 设置文件名属性
			att.setFileType(FileUploadUtils.getExtension(commentFileFileName));// 设置文件类型(后缀名)的属性
			att.setFileLength(commentFile.length());// 设置文件大小的属性
			att.setFileLocation(dstPath);// 设置上传后在服务器上保存路径的属性
			att.setUploadTime((new Timestamp(new Date().getTime())));// 设置上传时间属性
			File dstFile = new File(basePath + dstPath);// 创建一个服务器上的目标路径文件对象
			try{
				FileUploadUtils.copy(commentFile, dstFile);// 完成上传文件，就是将本地文件复制到服务器上
				commentService.addCommentAtt(att);
				getResponse().getWriter().write(att.getId());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * 描述：删除单个
	 * void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-5 下午10:13:18
	 */
	public void toRemoveAtt(){
		String attid = getRequest().getParameter("attid");
		try{
			commentService.deleteCommentAttById(attid);
			getResponse().getWriter().write(attid);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 描述：删除一些
	 * void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-5 下午10:13:47
	 */
	public void toRemoveSomeAtts(){
		String attids = getRequest().getParameter("attids");
		if(attids!=null){
			commentService.deleteCommentAtts(Arrays.asList(attids.split(",")));
		}
	}
	
	/**
	 * 
	 * 描述 ：检查文件是否存在
	 * void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-15 下午02:32:51
	 * @throws IOException 
	 */
	public void checkFileExist() throws IOException{
		String location =getRequest().getParameter("location");
		String basePath = SystemParamConfigUtil.getParamValueByParam("filePath");
		String fileNameWithPath =basePath+location;
		File downFile = new File(fileNameWithPath);
		if(downFile.exists()){
			//文件存在
			getResponse().getWriter().print("yes");
		}else{
			//文件不存在
			String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			fileNameWithPath = fileNameWithPath.replace(basePath, pdfRoot);
			downFile = new File(fileNameWithPath);
			if(downFile.exists()){
				//文件存在
				getResponse().getWriter().print("yes");
			}else{
				getResponse().getWriter().print("no");
			}
	}
	}
	
	/**
	 * 
	 * 描述：下载
	 * void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-6 下午02:13:53
	 */
	public void downloadAtt(){
		String fileName = getRequest().getParameter("name");
		String location =getRequest().getParameter("location");
		String basePath = SystemParamConfigUtil.getParamValueByParam("filePath");
		if(fileName==null){
			fileName = "a"+location.substring(location.lastIndexOf("."));
		}
		String fileNameWithPath =basePath+location;
		try {
			// 转码（UTF-8-->GB2312），现在环境下的编码是UTF-8，但服务器操作系统的编码是GB2312
			File file = new File(fileNameWithPath);
			// 判断文件是否存在
			if(!file.exists()){
				String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path")+"/";
				fileNameWithPath = fileNameWithPath.replace(basePath, pdfRoot);
				file = new File(fileNameWithPath);
			}
			fileName = URLEncoder.encode(fileName, "GB2312");
			fileName = URLDecoder.decode(fileName, "ISO8859-1");
			FileInputStream fileinputstream = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fileinputstream);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			getResponse().setContentType("application/x-msdownload");
			getResponse().setContentLength((int) l);
			getResponse().setHeader("Content-Disposition", "attachment; filename="+ fileName);
			while ((long) k < l) {
				int j;
				j = bis.read(abyte0, 0, 65000);
				k += j;
				getResponse().getOutputStream().write(abyte0, 0, j);
			}
			fileinputstream.close();
		} catch (IOException e) {
			logger.error("下载失败,文件路径为："+fileNameWithPath, e);
			e.printStackTrace(); 
		}
	}
	
	public String toViewDoc(){
		getRequest().setAttribute("location", getRequest().getParameter("location"));
		return "openDoc";
	}
	
	public String addPersonalComment(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		personalComment.setUser_id(emp.getEmployeeGuid());
		String content  = personalComment.getContent().trim();
		personalComment.setContent(content);
		commentService.addPersonComment(personalComment);
		return getCommentList();
	}
	
	/**
	 * 获取意见列表页面
	 * @return
	 */
	public String getCommentList(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		String content = getRequest().getParameter("content");
		Map<String,String> map = new HashMap<String,String>();
		map.put("userid", emp.getEmployeeGuid());
		map.put("content", content);
		int count = commentService.getCommentCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<PersonalComment> list = commentService.getCommentList(map, Paging.pageIndex, Paging.pageSize);
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("content", content);
		return "tolist";
	}
	
	
	public String updatePersonalComment(){
		String content  = personalComment.getContent().trim();
		personalComment.setContent(content);
		commentService.updatePersonalComment(personalComment);
		return getCommentList();
	}
	
	public void toupdate() throws IOException{
		String cmnt_id = getRequest().getParameter("cmnt_id");
		PersonalComment personalComment = commentService.getPersonalCommentById(cmnt_id);
		JSONObject jo = JSONObject.fromObject(personalComment);
		this.getResponse().getWriter().write(jo.toString());;
	}
	
	/**
	 * 删除指定的意见
	 * @return
	 */
	public String delete(){
		String cmnt_id = getRequest().getParameter("cmnt_id");
		if(cmnt_id!=null && !cmnt_id.equals("")){
			String[] ids = cmnt_id.split(",");
			for(int i=0; i<ids.length; i++){
				PersonalComment personalComment = commentService.getPersonalCommentById(ids[i]);
				if(personalComment!=null){
					commentService.deletePersonalComment(personalComment);
				}
			}
		}
		return getCommentList();
	}
	
	public void commentIsIn(){
		String content = getRequest().getParameter("content");
		String sortIndex = getRequest().getParameter("sortIndex");
		Employee emp = (Employee) getRequest().getSession().getAttribute(MyConstants.loginEmployee);
		PersonalComment comment = commentService.getCommentByContentAndSort(emp.getEmployeeGuid(),content,sortIndex);
		String result="";
		if(comment!=null){
			result = "yes";
		}else{
			result = "no";
		}
		try {
			this.getResponse().getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
