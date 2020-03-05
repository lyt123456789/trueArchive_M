package cn.com.trueway.workflow.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.NoticeInfoDao;
import cn.com.trueway.workflow.core.dao.TableInfoDao;
import cn.com.trueway.workflow.core.pojo.NoticeInfo;
import cn.com.trueway.workflow.core.pojo.WfChild;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.vo.NodeInfo;
import cn.com.trueway.workflow.core.pojo.vo.TrueJSON;
import cn.com.trueway.workflow.core.service.NoticeInfoService;
import cn.com.trueway.workflow.set.pojo.Trueform;

public class NoticeInfoServiceImpl implements NoticeInfoService{
	
	private NoticeInfoDao noticeInfoDao;
	
	private TableInfoDao  tableInfoDao;

	public NoticeInfoDao getNoticeInfoDao() {
		return noticeInfoDao;
	}

	public void setNoticeInfoDao(NoticeInfoDao noticeInfoDao) {
		this.noticeInfoDao = noticeInfoDao;
	}
	
	public TableInfoDao getTableInfoDao() {
		return tableInfoDao;
	}

	public void setTableInfoDao(TableInfoDao tableInfoDao) {
		this.tableInfoDao = tableInfoDao;
	}

	@Override
	public JSONObject saveNoticeInfo(NoticeInfo noticeInfo) {
		JSONObject result = new JSONObject();
		try{
			noticeInfoDao.saveNoticeInfo(noticeInfo);
			result.put("success", true);
		}catch (Exception e) {
			result.put("success", false);
		}
		return result;
	}

	@Override
	public NoticeInfo findNoticeInfoById(String id) {
		return noticeInfoDao.findNoticeInfoById(id);
	}

	@Override
	public JSONObject updateNoticeInfo(NoticeInfo noticeInfo) {
		JSONObject result = new JSONObject();
		try{
			noticeInfoDao.updateNoticeInfo(noticeInfo);
			result.put("success", true);
		}catch (Exception e) {
			result.put("success", false);
		}
		return result;
	}

	@Override
	public List<NoticeInfo> findNoticeInfoList(String conditionSql,
			Integer pageIndex, Integer pageSize) {
		return noticeInfoDao.findNoticeInfoList(conditionSql, pageIndex, pageSize);
	}

	@Override
	public int findNoticeInfoCount(String conditionSql) {
		return noticeInfoDao.findNoticeInfoCount(conditionSql);
	}
	
	@Override
	public int findXMCount(String conditionSql) {
		return noticeInfoDao.findXMCount(conditionSql);
	}
	
	@Override
	public List<Object[]> findXMList(String conditionSql, Integer pageIndex, Integer pageSize) {
		return noticeInfoDao.findXMList(conditionSql,pageIndex,pageSize);
	}

	@Override
	public NoticeInfo findNoticeInfoByfprocessId(String fprocessId) {
		return noticeInfoDao.findNoticeInfoByfprocessId(fprocessId);
	}

	@Override
	public String getNoticeInfoListOfMobile(List<NoticeInfo> list, String serverUrl) {
		String newPdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
		for(NoticeInfo notice:list){
			String fprocessId = notice.getFprocessId();
			WfProcess wfProcess = tableInfoDao.getProcessById(fprocessId);
			String pdfPath = notice.getPdfPath();
			String[] paths = pdfPath.split(",");
			String path = paths[1];
			if(path.startsWith(newPdfRoot)){
			    path = serverUrl+ "/form/html/workflow/"+path.substring(newPdfRoot.length());
			}else{
			    path = serverUrl+ "/form/html/"+path.substring(path.lastIndexOf("/") + 1);
			}
			Date sendDate = notice.getSendTime();
			try{
				String str_sendtime = sdf.format(sendDate);
				notice.setStr_time(str_sendtime);
			}catch (Exception e) {
			}
			notice.setPdfPath(path); 
			/*TrueJSON trueJson = new TrueJSON();
			trueJson.setMsgInfo("0"); // 崇川不显示 消息中心
			trueJson.setPdfurl(pdfPath);
			trueJson.setTruepaper(wfProcess.getCommentJson());
			trueJson.setNodeInfoList(new ArrayList<NodeInfo>());
			trueJson.setChildList(new ArrayList<WfChild>());
			trueJson.setProcessUrl("");
			trueJson.setTrueform(new ArrayList<Trueform>());
			notice.setCommentJson(JSONObject.fromObject(trueJson).toString());*/
			//notice.setCommentJson(wfProcess.getCommentJson());
		}
		return JSONArray.fromObject(list).toString();
	}
}
