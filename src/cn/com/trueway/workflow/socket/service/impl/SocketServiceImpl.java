package cn.com.trueway.workflow.socket.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.util.Constant;
import cn.com.trueway.workflow.socket.dao.SocketDao;
import cn.com.trueway.workflow.socket.pojo.SocketLog;
import cn.com.trueway.workflow.socket.service.SocketService;

/** 
 * ClassName: SocketServiceImpl <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2018年4月12日 上午11:12:52 <br/> 
 * 
 * @author adolph.jiang
 * @version  
 * @since JDK 1.6 
 */
public class SocketServiceImpl implements SocketService {
	private SocketDao socketDao;
	
	public SocketDao getSocketDao() {
		return socketDao;
	}

	public void setSocketDao(SocketDao socketDao) {
		this.socketDao = socketDao;
	}
	
	@Override
	public JSONArray checkIsOver(String params) {
		String [] paramss = params.split(";");
		JSONArray arr = new JSONArray();
		for (int i = 0; i < paramss.length; i++) {
			JSONObject obj = new JSONObject();
			String string = paramss[i];
			String [] ids = string.split(",");
			if(ids.length == 2){
				obj.put("code", "1");
				obj.put("instanceId", ids[0]);
				obj.put("userId", ids[1]);
				List<Object[]> list = socketDao.getProcessByParams(ids[0], ids[1]);
				if(list != null && list.size() > 0){
					Object[] objs = list.get(0);
					String isOver = null != objs[0] ? objs[0].toString() : "";
					Integer isOpen = null != objs[1] ? Integer.parseInt(objs[1].toString()):null; 
					if((StringUtils.isNotBlank(isOver) && isOver.equals(Constant.OVER)) || (null != isOpen && isOpen.equals(0))){//判断是否已办理
						//记录日志
						SocketLog socketLog = new SocketLog();
						socketLog.setCloseTime(new Date());
						socketLog.setInstanceId(ids[0]);
						socketLog.setUserId(ids[1]);
						socketLog.setRemark("已经办理仍然提示，所以强制关闭");
						socketDao.addSocketLog(socketLog);
						obj.put("isClose", "true");
					}else{
						obj.put("isClose", "false");
					}
				}else{
					obj.put("isClose", "false");
				}
			}else{
				obj.put("code", "0");
				obj.put("message", "参数不符合规则");
				obj.put("param", string);
			}
			arr.add(obj);
		}
		return arr;
	}
}
