package cn.com.trueway.workflow.core.dao.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.workflow.core.dao.FilingDao;
import cn.com.trueway.workflow.core.pojo.WfFiling;
import cn.com.trueway.workflow.core.pojo.vo.Filing;

public class FilingDaoImpl extends BaseDao implements FilingDao {

	@Override
	public boolean insertFiling(WfFiling wfFiling) {
		this.getEm().persist(wfFiling);
		return true;	
	}

	@Override
	public List<Filing> getFilingList(String conditionSql, String deptSql1,
			String deptSql2, Integer pageIndex, Integer pageSize) {
		String sql = "select k.title, k.wh, k.type, to_char(k.filedtime,'yyyy-mm-dd hh24:mi') as filedtime ,k.processId,k.itemId,k.formId "
					+ "from (select t.title,t.wh,'0' as type, t.filedtime ,t.processId,t.itemId,t.formId from filed_fw_inf t  where t.filedtime is not null"
					+ deptSql1 +" union select p.title,p.wh,'1' as type, p.filedtime ,p.processId,p.itemId,p.formId from filed_bw_inf p where p.filedtime is not null "
					+ deptSql2+") k "
					+ conditionSql + "order by k.filedtime desc";
		Query query = this.getEm().createNativeQuery(sql);
		if (pageIndex!=null&&pageSize!=null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		List<Object[]> list = query.getResultList();
		List<Filing> filings = new ArrayList<Filing>();
		for(Object[] ob:list){
			Filing filing = new Filing();
			filing.setTitle((String)ob[0]);
			filing.setWh((String)ob[1]);
			filing.setType((Character)ob[2]+"");
			//String[] date = ((String)ob[3]).split(" ");
			filing.setFiledTime((String)ob[3]);
			filing.setProcessId((String)ob[4]);
			filing.setItemId((String)ob[5]);
			filing.setFormId((String)ob[6]);
			filings.add(filing);
		}
		return filings;
	}

	@Override
	public int getCountOfFilings(String conditionSql, String deptSql1,
			String deptSql2) {
		String sql = "select count(*) from (select t.title,t.wh,'0' as type, t.filedtime,t.processId,t.itemId,t.formId from filed_fw_inf t where t.filedtime is not null "
				+ deptSql1 +" union select p.title,p.wh,'1' as type, p.filedtime,p.processId,p.itemId,p.formId from filed_bw_inf p where p.filedtime is not null "
				+ deptSql2+") k "
				+ conditionSql;
		Query query = this.getEm().createNativeQuery(sql);
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public boolean checkFiling(String processId) {
		String hql = "from WfFiling f where f.processId='"+processId+"'";
		List<WfFiling> filings = this.getEm().createQuery(hql).getResultList();
		if(filings.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
}
