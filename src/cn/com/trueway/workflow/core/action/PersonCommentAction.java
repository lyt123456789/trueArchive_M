package cn.com.trueway.workflow.core.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.document.component.taglib.comment.model.po.PersonalComment;
import cn.com.trueway.document.component.taglib.comment.service.CommentService;

public class PersonCommentAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommentService commentService;
	
	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public void getPersonComment(){
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("utf-8");
			JSONObject jsonObject = getJSONObject();
			String userId;
			try {
				userId = jsonObject.getString("userId");
			} catch (Exception e) {
				userId = getRequest().getParameter("userId");
			}
			// 需要权限是
			List<PersonalComment> personalComments = commentService.getPersonalComments(userId);
			List<String> personComment = new ArrayList<String>();
			for(PersonalComment pc :personalComments){
		    	personComment.add(pc.getContent());
			}
			JSONArray ret =JSONArray.fromObject(personComment);
			out.println(ret.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			out.flush();
			out.close();
		}
		
	}


	private JSONObject getJSONObject() {
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			if(data.length  >0 ){
				return JSONObject.fromObject(new String(data, "utf-8"));
			}else{
				return null;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}

}
