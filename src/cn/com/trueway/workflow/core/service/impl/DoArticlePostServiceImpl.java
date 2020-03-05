package cn.com.trueway.workflow.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.lob.SerializableClob;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.document.business.model.ItemSelect;
import cn.com.trueway.workflow.core.dao.DoArticlePostDao;
import cn.com.trueway.workflow.core.dao.PendingDao;
import cn.com.trueway.workflow.core.pojo.WfProcess;
import cn.com.trueway.workflow.core.pojo.vo.FieldSelectCondition;
import cn.com.trueway.workflow.core.service.DoArticlePostService;
import cn.com.trueway.workflow.set.util.ClobToString;

public class DoArticlePostServiceImpl implements DoArticlePostService {
	
	private DoArticlePostDao doArticlePostDao;
	
	private PendingDao pendingDao;
	
	public PendingDao getPendingDao() {
		return pendingDao;
	}


	public void setPendingDao(PendingDao pendingDao) {
		this.pendingDao = pendingDao;
	}


	public DoArticlePostDao getDoArticlePostDao() {
		return doArticlePostDao;
	}


	public void setDoArticlePostDao(DoArticlePostDao doArticlePostDao) {
		this.doArticlePostDao = doArticlePostDao;
	}

	public ItemSelect getSelectByItemID(String itemId, String type) {
		return doArticlePostDao.getSelectByItemID(itemId,type);
	}
	
	@Override
	public int getCountOfFreeDofile(String conditionSql, String conditionTable,
			String employeeGuid, String type,
			List<FieldSelectCondition> fsList,String bjlx) {
		return doArticlePostDao.getCountOfFreeDofile(conditionSql,conditionTable, employeeGuid, type,fsList,bjlx);
	}

	@Override
	public List<Object> getFreeDofileList(String isEnd, String conditionSql,
			String conditionTable, String resultSql, String userId,
			int pageIndex, int pageSize, List<FieldSelectCondition> fsList,String bjlx) {

		List<Object> list = doArticlePostDao.getFreeDofileList(isEnd, conditionSql,conditionTable,resultSql, userId,pageIndex,pageSize, fsList,bjlx);
		for(int i =0 ; i< list.size() ; i++){
			Object[] obj = (Object[])list.get(i);
			// 最后一个字段为 instanceid
			List<WfProcess> processList = new ArrayList<WfProcess>();
			if(pageIndex != 0 ){//分页时，会查出rownum，算在了object里面
				processList = doArticlePostDao.getProcessList(obj[obj.length-7].toString());
				if(processList.size()>0 && processList != null){
					obj[obj.length-6] = processList.get(0).getWfProcessUid();
					obj[obj.length-5] = processList.get(0).getItemId();
					obj[obj.length-4] =  processList.get(0).getFormId();
				}
			}else{
				processList = doArticlePostDao.getProcessList(obj[obj.length-6].toString());
				if(processList.size()>0 && processList != null){
					obj[obj.length-5] = processList.get(0).getWfProcessUid();
					obj[obj.length-4] = processList.get(0).getItemId();
					obj[obj.length-3] =  processList.get(0).getFormId();
				}
			}
				
			if ("1".equals(bjlx)){
				if(null != obj[2]){
					String objStr2 = obj[2].toString();
					if (objStr2.indexOf("*")>-1){
						String [] objStrs2 = objStr2.split("\\*");
						obj[2] = objStrs2[1];
					}
				}
				if(null != obj[3]){
					String objStr3 = obj[3].toString();
					if (objStr3.indexOf("*")>-1){
						String [] objStrs3 = objStr3.split("\\*");
						obj[3] = objStrs3[1];
					}
					
				}
			}
			String instanceId = "";
			if ("0".equals(bjlx) && null != obj[6]){
				instanceId = obj[6].toString();
			}else if("1".equals(bjlx) && null != obj[7]){
				instanceId = obj[7].toString();
			}
			List<WfProcess> wfps = pendingDao.findProcessListByFId(instanceId);
			if(null != wfps && wfps.size()>0){
				//当前办件有子流程
				obj[obj.length-1] = "1";
			}else{
				//当前办件没有子流程
				List<WfProcess> wfpss = pendingDao.getProcessByInstanceId(instanceId);
				boolean isChild = false;
				for(WfProcess wfp:wfpss){
					if(CommonUtil.stringNotNULL(wfp.getfInstancdUid())){
						isChild = true;
					}
				}
				if(isChild){
					//当前办件是子流程
					obj[obj.length-1] = "1";
				}else{
					//当前办件是主流程
					obj[obj.length-1] = "0";
				}
			}
			
			for(int j= 0; j< obj.length ; j++){
				if(obj[j] instanceof SerializableClob){
					obj[j] = ClobToString.clob2String((SerializableClob)obj[j]);
					String objStr = obj[j].toString();
					String [] objStrs = objStr.split("\\*");
					obj[j] = objStrs[1];
				}
			}
			
		}
		return list;
	}
}
