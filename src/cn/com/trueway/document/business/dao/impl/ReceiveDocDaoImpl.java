package cn.com.trueway.document.business.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.business.dao.ReceiveDocDao;
import cn.com.trueway.document.business.model.CheckInRecDoc;
import cn.com.trueway.document.business.model.RecDoc;
import cn.com.trueway.document.business.model.ReceiveProcessShip;
import cn.com.trueway.document.business.model.ReceiveXml;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;

/**
 * 描述：对收文的库操作<br>
 * 作者：周雪贇<br>
 * 创建时间：2011-12-2 上午11:05:31<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class ReceiveDocDaoImpl extends BaseDao implements ReceiveDocDao {
	/**
	 * 描述：插OA_DOC_RECEIVE表<br>
	 * 
	 * @param recDoc
	 */
	public void saveRecDOC(RecDoc recDoc) {
		//recDoc.setDocguid(UuidGenerator.generate36UUID());
		this.getEm().persist(recDoc);
	}

	/**
	 * 描述：保存正文附件<br>
	 * 
	 * @param recDoc
	 */
	public void saveRecDocAtts(ReceiveAttachments att) {
		super.getEm().persist(att);
	}

	/**
	 * 描述：根据条件查询已收列表<br>
	 * 
	 * @param currentPage
	 * @param numPerPage
	 * @param xto
	 * @param keyword
	 * @param content
	 * @param status
	 * @return DTPageBean
	 */
	public DTPageBean queryReceivedDoc(int currentPage, int numPerPage,
			List<String> xto, String keyword, String content, String status,
			String timeType, String startTime, String endTime) {
		StringBuilder countSQL = new StringBuilder();
		StringBuilder objectSQL = new StringBuilder();
		StringBuffer sb = new StringBuffer();
		sb.append(" from OA_DOC_RECEIVE d where (");
		for (String dep : xto) {
			sb.append("d.QUEUE_XTO='");
			sb.append(dep);
			sb.append("' or ");
		}
		sb.delete(sb.length() - 3, sb.length());
		sb.append(")");
		if (keyword != null && !keyword.equals("")) {
			sb
					.append(" and d." + keyword.trim() + " like '%" + content
							+ "%' ");
		}
		if (status != null && !status.equals("")) {
			sb.append("and d.status=" + status);
		}
		if (timeType != null && !timeType.equals("")) {
			if (startTime != null && !startTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" >= TO_DATE(substr('").append(startTime.trim())
						.append("',1,14),'YYYY-MM-DD HH24:mi')");
			}
			if (endTime != null && !endTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" <= TO_DATE(substr('").append(endTime.trim()).append(
						"',1,14),'YYYY-MM-DD HH24:mi')");
			}
		}
		countSQL.append("select count(*) ").append(sb.toString());
		objectSQL.append("select * ").append(sb.toString()).append(
				" order by status,recDate desc,submittm desc");
		return pagingQuery(countSQL.toString(), objectSQL.toString(),
				currentPage, numPerPage, RecDoc.class);
	}

	/**
	 * 描述：根据DOCGUID得到RECEVIEDOC对象<br>
	 * 
	 * @param docguid
	 * @return RecDoc
	 */
	@SuppressWarnings("unchecked")
	public RecDoc getRecDocByDocguid(String docguid) {
		RecDoc recDoc = null;
		List<RecDoc> recDocList;
		String hql = " from RecDoc q where q.docguid = ?  ";
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, docguid);
		recDocList = (List<RecDoc>) query.getResultList();
		recDoc = recDocList.isEmpty() ? null : recDocList.get(0);
		if (recDoc == null)
			return null;
		List<ReceiveAttachments> atts = new ArrayList<ReceiveAttachments>();
		String hqlatts = "from ReceiveAttachments a where a.docguid = ?  ";
		Query queryatts = super.getEm().createQuery(hqlatts);
		queryatts.setParameter(1, docguid);
		atts = queryatts.getResultList();
		recDoc.setAtts(atts);
		return recDoc;
	}

	// 更新已收公文的状态(STATUS)
	public void updateRecDOC(RecDoc recDoc) {
		String sql = "update oa_doc_receive set status=:status  where DOCGUID = :docguid";
		Query query = super.getEm().createNativeQuery(sql);
		query.setParameter("status", recDoc.getStatus());
		query.setParameter("docguid", recDoc.getDocguid());
		query.executeUpdate();
	}

	public void saveCheckInRecDoc(CheckInRecDoc docCheckin) {
		super.getEm().persist(docCheckin);
	}

	// 根据docguid 修改备注及其状态位
	public void updateBeizhuByDocguid(RecDoc recDoc) {
		String sql = "update oa_doc_receive set status=:status,beizhu=:beizhu where DOCGUID = :docguid";

		Query query = super.getEm().createNativeQuery(sql);

		query.setParameter("status", recDoc.getStatus());

		query.setParameter("docguid", recDoc.getDocguid());

		query.setParameter("beizhu", recDoc.getBeizhu());

		query.executeUpdate();
	}

	/**
	 * 
	 * 描述：根据ID更新RecDoc的STATUS状态位
	 * 
	 * @param docguid
	 * @param statusValue
	 *            void
	 * 
	 *            作者:WangXF<br>
	 *            创建时间:2011-12-29 下午01:58:49
	 */
	public void updateRecDocStatus(String docguid, String statusValue) {
		String sqlString = "update OA_DOC_RECEIVE set status =:status where docguid =:docguid";
		Query query = super.getEm().createNativeQuery(sqlString);
		query.setParameter("status", statusValue);
		query.setParameter("docguid", docguid);
		query.executeUpdate();
	}

	/**
	 * 
	 * 描述：根据条件查询已收公文数目<br>
	 * 
	 * @param xto
	 * @param keyword
	 * @param content
	 * @param status
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @return int
	 * 
	 *         作者:WangXF<br>
	 *         创建时间:2012-2-2 下午03:04:21
	 */
	public long queryReceivedDocCounts(List<String> deps, String wh,
			String title, String sendername, String statuskey, String timeType,
			String startTime, String endTime, String keyword) {
		StringBuilder countSQL = new StringBuilder();
		StringBuffer sb = new StringBuffer();
		sb.append(" from OA_DOC_RECEIVE d where (");
		for (String dep : deps) {
			sb.append("d.QUEUE_XTO='");
			sb.append(dep);
			sb.append("' or ");
		}
		sb.delete(sb.length() - 3, sb.length());
		sb.append(")");
		if (wh != null && !wh.equals("")) {
			sb.append(" and d.wh like '%" + wh.trim() + "%' ");
		}
		if (title != null && !title.equals("")) {
			sb.append(" and d.title like '%" + title.trim() + "%' ");
		}
		if (sendername != null && !sendername.equals("")) {
			sb.append(" and d.sendername like '%" + sendername.trim() + "%' ");
		}
		if (statuskey != null && !statuskey.equals("")) {
			sb.append("and d.status=" + statuskey.trim());
		}
		if (timeType != null && !timeType.equals("")) {
			if (startTime != null && !startTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" >= TO_DATE(substr('").append(startTime.trim())
						.append("',1,14),'YYYY-MM-DD HH24:mi')");
			}
			if (endTime != null && !endTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" <= TO_DATE(substr('").append(endTime.trim()).append(
						"',1,14),'YYYY-MM-DD HH24:mi')");
			}
		}
		countSQL.append("select count(*) ").append(sb.toString());
		Query query = getEm().createNativeQuery(countSQL.toString());
		long count = Long.valueOf(String.valueOf(query.getSingleResult()));
		return count;
	}

	public long queryReceivedDocCounts(List<String> deps, String wh,
			String title, String sendername, String statuskey, String timeType,
			String startTime, String endTime, String keyword, boolean isIN,
			String whs) {
		StringBuilder countSQL = new StringBuilder();
		StringBuffer sb = new StringBuffer();
		sb.append(" from OA_DOC_RECEIVE d where (");
		for (String dep : deps) {
			sb.append("d.QUEUE_XTO='");
			sb.append(dep);
			sb.append("' or ");
		}
		sb.delete(sb.length() - 3, sb.length());
		sb.append(")");
		if (wh != null && !wh.equals("")) {
			sb.append(" and d.wh like '%" + wh.trim() + "%' ");
		}
		if (title != null && !title.equals("")) {
			sb.append(" and d.title like '%" + title.trim() + "%' ");
		}
		if (sendername != null && !sendername.equals("")) {
			sb.append(" and d.sendername like '%" + sendername.trim() + "%' ");
		}
		if (statuskey != null && !statuskey.equals("")) {
			sb.append("and d.status=" + statuskey.trim());
		}
		if (timeType != null && !timeType.equals("")) {
			if (startTime != null && !startTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" >= TO_DATE(substr('").append(startTime.trim())
						.append("',1,14),'YYYY-MM-DD HH24:mi')");
			}
			if (endTime != null && !endTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" <= TO_DATE(substr('").append(endTime.trim()).append(
						"',1,14),'YYYY-MM-DD HH24:mi')");
			}
		}
		if (isIN) {
			sb.append(" and NVL(REGEXP_SUBSTR(d.wh,'\\w{2,}',1,1),'空') in "
					+ whs + " ");
		} else {
			sb.append(" and NVL(REGEXP_SUBSTR(d.wh,'\\w{2,}',1,1),'空') not in "
					+ whs + " ");
		}
		countSQL.append("select count(*) ").append(sb.toString());
		Query query = getEm().createNativeQuery(countSQL.toString());
		long count = Long.valueOf(String.valueOf(query.getSingleResult()));
		return count;
	}

	/**
	 * 根据查询条件导出已收公文
	 * 
	 * @param deps
	 * @param keyword
	 * @param content
	 * @param statuskey
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @param cols
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryReceivedDocForExport(List<String> deps,
			String wh, String title, String sendername, String statuskey,
			String timeType, String startTime, String endTime, String keyword,
			String[] cols) {
		StringBuilder querySql = new StringBuilder();
		StringBuffer sb = new StringBuffer();
		sb.append(" from OA_DOC_RECEIVE d where (");
		for (String dep : deps) {
			sb.append("d.QUEUE_XTO='");
			sb.append(dep);
			sb.append("' or ");
		}
		sb.delete(sb.length() - 3, sb.length());
		sb.append(")");
		if (wh != null && !wh.equals("")) {
			sb.append(" and d.wh like '%" + wh.trim() + "%' ");
		}
		if (title != null && !title.equals("")) {
			sb.append(" and d.title like '%" + title.trim() + "%' ");
		}
		if (sendername != null && !sendername.equals("")) {
			sb.append(" and d.sendername like '%" + sendername.trim() + "%' ");
		}
		if (statuskey != null && !statuskey.equals("")) {
			sb.append("and d.status=" + statuskey.trim());
		}
		if (timeType != null && !timeType.equals("")) {
			if (startTime != null && !startTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" >= TO_DATE(substr('").append(startTime.trim())
						.append("',1,14),'YYYY-MM-DD HH24:mi')");
			}
			if (endTime != null && !endTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" <= TO_DATE(substr('").append(endTime.trim()).append(
						"',1,14),'YYYY-MM-DD HH24:mi')");
			}
		}
		if (cols == null || cols.length == 0) {
			return null;
		}
		querySql.append("select ");
		for (int i = 0; i < cols.length; i++) {
			if (i == cols.length - 1) {
				querySql.append("d.").append(cols[i]).append(" ");
			} else {
				querySql.append("d.").append(cols[i]).append(",");
			}

		}
		querySql.append(sb.toString()).append(" order by status,submittm desc");
		Query query = getEm().createNativeQuery(querySql.toString());
		List<Object[]> objList = query.getResultList();
		return objList;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> queryReceivedDocForExport(List<String> deps,
			String wh, String title, String sendername, String statuskey,
			String timeType, String startTime, String endTime, String keyword,
			String[] cols, boolean isIN, String whs) {
		StringBuilder querySql = new StringBuilder();
		StringBuffer sb = new StringBuffer();
		sb.append(" from OA_DOC_RECEIVE d where (");
		for (String dep : deps) {
			sb.append("d.QUEUE_XTO='");
			sb.append(dep);
			sb.append("' or ");
		}
		sb.delete(sb.length() - 3, sb.length());
		sb.append(")");
		if (wh != null && !wh.equals("")) {
			sb.append(" and d.wh like '%" + wh.trim() + "%' ");
		}
		if (title != null && !title.equals("")) {
			sb.append(" and d.title like '%" + title.trim() + "%' ");
		}
		if (sendername != null && !sendername.equals("")) {
			sb.append(" and d.sendername like '%" + sendername.trim() + "%' ");
		}
		if (statuskey != null && !statuskey.equals("")) {
			sb.append("and d.status=" + statuskey.trim());
		}
		if (timeType != null && !timeType.equals("")) {
			if (startTime != null && !startTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" >= TO_DATE(substr('").append(startTime.trim())
						.append("',1,14),'YYYY-MM-DD HH24:mi')");
			}
			if (endTime != null && !endTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" <= TO_DATE(substr('").append(endTime.trim()).append(
						"',1,14),'YYYY-MM-DD HH24:mi')");
			}
		}
		if (cols == null || cols.length == 0) {
			return null;
		}
		querySql.append("select ");
		for (int i = 0; i < cols.length; i++) {
			if (i == cols.length - 1) {
				querySql.append("d.").append(cols[i]).append(" ");
			} else {
				querySql.append("d.").append(cols[i]).append(",");
			}

		}
		if (isIN) {
			sb.append(" and NVL(REGEXP_SUBSTR(d.wh,'\\w{2,}',1,1),'空') in "
					+ whs + " ");
		} else {
			sb.append(" and NVL(REGEXP_SUBSTR(d.wh,'\\w{2,}',1,1),'空') not in "
					+ whs + " ");
		}
		querySql.append(sb.toString()).append(" order by status,submittm desc");
		Query query = getEm().createNativeQuery(querySql.toString());
		List<Object[]> objList = query.getResultList();
		return objList;
	}

	/**
	 * 
	 * 描述：根据条件查询已收列表
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param deps
	 * @param wh
	 * @param title
	 * @param sendername
	 * @param statuskey
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @param keyword
	 * @return DTPageBean
	 * 
	 *         作者:WangXF<br>
	 *         创建时间:2012-2-4 下午02:17:15
	 */
	public DTPageBean queryReceivedDoc(int currentPage, int pageSize,
			List<String> deps, String wh, String title, String sendername,
			String statuskey, String timeType, String startTime,
			String endTime, String keyword) {
		StringBuilder countSQL = new StringBuilder();
		StringBuilder objectSQL = new StringBuilder();
		StringBuffer sb = new StringBuffer();
		sb.append(" from OA_DOC_RECEIVE d where (");
		for (String dep : deps) {
			sb.append("d.QUEUE_XTO='");
			sb.append(dep);
			sb.append("' or ");
		}
		sb.delete(sb.length() - 3, sb.length());
		sb.append(")");
		if (wh != null && !wh.equals("")) {
			sb.append(" and d.wh like '%" + wh.trim() + "%' ");
		}
		if (title != null && !title.equals("")) {
			sb.append(" and d.title like '%" + title.trim() + "%' ");
		}
		if (sendername != null && !sendername.equals("")) {
			sb.append(" and d.sendername like '%" + sendername.trim() + "%' ");
		}
		if (statuskey != null && !statuskey.equals("")) {
			sb.append("and d.status=" + statuskey.trim());
		}
		if (timeType != null && !timeType.equals("")) {
			if (startTime != null && !startTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" >= TO_DATE(substr('").append(startTime.trim())
						.append("',1,14),'YYYY-MM-DD HH24:mi')");
			}
			if (endTime != null && !endTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" <= TO_DATE(substr('").append(endTime.trim()).append(
						"',1,14),'YYYY-MM-DD HH24:mi')");
			}
		}
		countSQL.append("select count(*) ").append(sb.toString());
		objectSQL.append("select * ").append(sb.toString()).append(
				" order by status,recDate desc,submittm desc");
		return pagingQuery(countSQL.toString(), objectSQL.toString(),
				currentPage, pageSize, RecDoc.class);
	}

	public DTPageBean queryReceivedDoc(int currentPage, int pageSize,
			List<String> deps, String wh, String title, String sendername,
			String statuskey, String timeType, String startTime,
			String endTime, String keyword, boolean isIN, String whs) {
		StringBuilder countSQL = new StringBuilder();
		StringBuilder objectSQL = new StringBuilder();
		StringBuffer sb = new StringBuffer();
		sb.append(" from OA_DOC_RECEIVE d where (");
		for (String dep : deps) {
			sb.append("d.QUEUE_XTO='");
			sb.append(dep);
			sb.append("' or ");
		}
		sb.delete(sb.length() - 3, sb.length());
		sb.append(")");
		if (wh != null && !wh.equals("")) {
			sb.append(" and d.wh like '%" + wh.trim() + "%' ");
		}
		if (title != null && !title.equals("")) {
			sb.append(" and d.title like '%" + title.trim() + "%' ");
		}
		if (sendername != null && !sendername.equals("")) {
			sb.append(" and d.sendername like '%" + sendername.trim() + "%' ");
		}
		if (statuskey != null && !statuskey.equals("")) {
			sb.append("and d.status=" + statuskey.trim());
		}
		if (timeType != null && !timeType.equals("")) {
			if (startTime != null && !startTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" >= TO_DATE(substr('").append(startTime.trim())
						.append("',1,14),'YYYY-MM-DD HH24:mi')");
			}
			if (endTime != null && !endTime.equals("")) {
				sb.append(" and d.").append(timeType.trim()).append(
						" <= TO_DATE(substr('").append(endTime.trim()).append(
						"',1,14),'YYYY-MM-DD HH24:mi')");
			}
		}
		if (isIN) {
			sb.append(" and NVL(REGEXP_SUBSTR(d.wh,'\\w{2,}',1,1),'空') in "
					+ whs + " ");
		} else {
			sb.append(" and NVL(REGEXP_SUBSTR(d.wh,'\\w{2,}',1,1),'空') not in "
					+ whs + " ");
		}
		countSQL.append("select count(*) ").append(sb.toString());
		objectSQL.append("select * ").append(sb.toString()).append(
				" order by status,recDate desc,submittm desc");
		return pagingQuery(countSQL.toString(), objectSQL.toString(),
				currentPage, pageSize, RecDoc.class);
	}

	public void setViewStatus(String userId, RecDoc recDoc) {
		StringBuffer sb = new StringBuffer();
		String viewStatus = "";
		if (recDoc.getViewStatus() != null) {
			viewStatus = sb.append(recDoc.getViewStatus()).append(userId)
					.append(",").append("1").append(";").toString();
		} else {
			viewStatus = sb.append(userId).append(",").append("1").append(";")
					.toString();
		}
		String sql = "update oa_doc_receive set viewstatus='" + viewStatus
				+ "' where docguid='" + recDoc.getDocguid()+"'";
		Query query = super.getEm().createNativeQuery(sql);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public String findDepIdByEmpId(String userId) {
		String docguid = "";
		String sql = "select e.department_guid from  ZWKJ_EMPLOYEE e where e.employee_guid='"+userId+"'";
		List<String> str = getEm().createNativeQuery(sql).getResultList();
		if(!("").equals(str) && str!=null && str.size()!=0){  
			docguid =  str.get(0);
		}
		return docguid;
	}

	@SuppressWarnings("unchecked")
	public List<RecDoc> queryReceivedDocForPortal(String url, List<String> deps) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from OA_DOC_RECEIVE d where (");
		for (String dep : deps) {
			sb.append("d.QUEUE_XTO='");
			sb.append(dep);
			sb.append("' or ");
		}
		sb.delete(sb.length() - 3, sb.length());
		sb.append(")");
		sb.append(" order by status,recDate desc,submittm desc");
		return getEm().createNativeQuery(sb.toString(), RecDoc.class).getResultList();
	}

	@Override
	public List<RecDoc> getReceivedDocList(String conditionSql,
			Integer pageIndex, Integer pageSize) {
		StringBuffer hql = new StringBuffer();
			hql.append(" from RecDoc d where 1=1  "+conditionSql);
		Query query = getEm().createQuery(hql.toString());
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult( pageIndex);
			query.setMaxResults( pageSize);
		}
		return query.getResultList();
	}

	@Override
	public int getReceivedDocCount(String conditionSql) {
		String sql = "select count(*) from RecDoc t where 1=1  " +conditionSql;
		return Integer.parseInt(getEm().createQuery(sql).getSingleResult().toString());
	}

	@Override
	public void saveReceiveProcessShip(ReceiveProcessShip receiveProcessShip) {
		getEm().persist(receiveProcessShip);
		
	}

	@Override
	public ReceiveProcessShip findReceiveProcessShipByRecId(String recId) {
		String hql = "from ReceiveProcessShip t where t.recId = '"+recId+"'";
		List<ReceiveProcessShip> list = getEm().createQuery(hql).getResultList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveReceiveXml(ReceiveXml receiveXml) {
		getEm().persist(receiveXml);
	}

	@Override
	public List<ReceiveXml> findReceiveXmlByRecId(String recId) {
		String hql = " from ReceiveXml t where t.recId = '"+recId+"'";
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public void updateReceiveProcessShip(ReceiveProcessShip receiveProcessShip) {
		getEm().merge(receiveProcessShip);
		
	}
}
