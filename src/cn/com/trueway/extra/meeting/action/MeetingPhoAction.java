package cn.com.trueway.extra.meeting.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.extra.meeting.bean.MeetingphoApply;
import cn.com.trueway.extra.meeting.service.MeetingPhoService;

public class MeetingPhoAction extends BaseAction{
	private static final long serialVersionUID = -6424063781919899299L;
	private MeetingPhoService meetingphoservice;
	public void setMeetingphoservice(MeetingPhoService meetingphoservice) {
		this.meetingphoservice = meetingphoservice;
	}
	public MeetingPhoService getMeetingphoservice() {
		return meetingphoservice;
	}
	private JSONObject getJSONObject(){
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			return JSONObject.fromObject(new String (data,"UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(iStream!=null){
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
	public void getMeetingApplylistForAPP(){
		try {
//			JSONObject jsonObject = getJSONObject(); // 获得post请求中的json
//		    String userId = (String) jsonObject.get("userId");
//			int pageNum = Integer.parseInt(String.valueOf(jsonObject.get("pageNum")));
			String userId = (String)getRequest().getParameter("userId");
			Integer pageNum = Integer.valueOf(getRequest().getParameter("pageNum"));
//			String userId = "{7F000001-FFFF-FFFF-A50B-3DB800000014}";
//			int pageNum = 1;
			if(pageNum==0){
				pageNum = pageNum+1;
			}
			int pageIndex = (pageNum-1)*10;
			List<MeetingphoApply> meetinglist = meetingphoservice.getMeetingApplylist(userId, pageIndex, 10);
			List retlist = checkMeetingApplylistForAPP(meetinglist);
			String outStr = JSONArray.fromObject(retlist).toString();
			outStr = "{\"data\":"+outStr+"}";
			HttpServletResponse response = getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(outStr);
			out.write(outStr);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private List<Map> checkMeetingApplylistForAPP(List<MeetingphoApply> list) {
		List<Map> reslist = new ArrayList<Map>();
		if(null != list && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Map map = new HashMap();
				MeetingphoApply meetingphoApply =list.get(i);
				map.put("place", meetingphoApply.getRoomname());
				map.put("time", meetingphoApply.getMeeting_begin_time());
				map.put("users", meetingphoApply.getMeeting_bcyry());
				map.put("name", meetingphoApply.getMeeting_subject());
				reslist.add(map);
			}
		}
		return reslist;
	}
}
