package cn.com.trueway.workflow.core.service;

import java.util.List;

import cn.com.trueway.workflow.core.pojo.WfFiling;
import cn.com.trueway.workflow.core.pojo.vo.Filing;

public interface FilingService {
	boolean insertFiling(WfFiling wfFiling);
	List<Filing> getFilingList(String conditionSql,String deptSql1,String deptSql2,Integer pageIndex, Integer pageSize);
	int getCountOfFilings(String conditionSql,String deptSql1,String deptSql2);
	boolean checkFiling(String processId);
}
