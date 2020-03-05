package cn.com.trueway.document.business.dao.impl;

import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.base.util.Constant;
import cn.com.trueway.document.business.dao.SendDocDao;
import cn.com.trueway.document.business.model.Doc;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsext;

/**
 * 描述：发文<br>
 * 作者：周雪贇<br>
 * 创建时间：2011-12-14 下午04:20:15<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class SendDocDaoImpl extends BaseDao implements SendDocDao{
	/**
	 * 根据ResultFlag找到对应的Doc对象的docguid
	 * ResultFlag
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String  findDocguidByRF(String ResultFlag) {
		String hql = "from Doc d where d.resultFlag = ?"; 
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, ResultFlag);
		Doc doc=(Doc)query.getSingleResult();
		return doc.getDocguid();
	}
	/**
	 * 描述：根据docguid查询Docbox;包括正文附件和附加附件<br>
	 *
	 * @param docguid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Doc findFullDocByDocguid(String docguid) {
		Doc doc=super.getEm().find(Doc.class, docguid);
		String hql = "from SendAttachments d where d.docguid = ?"; 
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, docguid);
		List<SendAttachments> atts=query.getResultList();
		doc.setAtts(atts);		
		String hql2 = "from SendAttachmentsext d where d.docguid = ?"; 
		Query query2 = super.getEm().createQuery(hql2);
		query2.setParameter(1, docguid);
		List<SendAttachmentsext> attexts=query2.getResultList();
		doc.setAttExts(attexts);		
		return doc;
	}
	/**
	 * 保存Doc对象
	 * 
	 * @param doc
	 * @throws Exception
	 */
	public void saveDoc(Doc doc) {
		super.getEm().merge(doc);
	}
	
	/**
	 * 描述：根据instanceId找到DOC对象<br>
	 *
	 * @param instanceId
	 * @return
	 * @throws Exception Doc
	 */
	@SuppressWarnings("unchecked")
	public Doc findDocByInstanceId(String instanceId){
		// 根据instanceId找到对应的Doc对象
		String hql = "from Doc d where d.instanceId = ?";
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, instanceId);
		List list = query.getResultList();
		if (list != null && !list.isEmpty()) {
			return (Doc) list.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 描述：根据条件查询已发列表<br>
	 *
	 * @param currentPage
	 * @param numPerPage
	 * @param xto
	 * @param keyword
	 * @param content
	 * @param status
	 * @return DTPageBean
	 */
	public DTPageBean querySentDocList(int currentPage, int numPerPage, List<String> deps, String keyword, String content,String wh,String status,String userId) {
		StringBuilder countSQL=new StringBuilder();
		StringBuilder objectSQL=new StringBuilder();
		StringBuffer sb = new StringBuffer();
		sb.append(" from OA_DOC_DOCBOX d where (");
		for(String dep:deps){
			sb.append("d.SENDER='");
			sb.append(dep);
			sb.append("' or ");
		}
		sb.delete(sb.length()-3, sb.length());
		sb.append(")");
		sb.append(" and d.").append(keyword).append(" like '%").append(content).append("%' ");
		if(!"%%".equals(wh.split("\\|")[0]))
		sb.append(" and d.jgdz like '").append(wh.split("\\|")[0]).append("' ");
		if(!"%%".equals(wh.split("\\|")[1]))
		sb.append(" and d.fwnh like '").append(wh.split("\\|")[1]).append("' ");
		if(!"%%".equals(wh.split("\\|")[2]))
		sb.append(" and d.fwxh like '").append(wh.split("\\|")[2]).append("' ");
		if(status!=null&&!status.equals("")) sb.append("and d.status="+status);
		if(userId!=null&&!userId.equals("")){
			sb.append("and d.senderid='"+userId+"'");
		}
		countSQL.append("select count(*) ").append(sb.toString());
		objectSQL.append("select * ").append(sb.toString()).append(" order by d.submittm desc");
		return pagingQuery(countSQL.toString(),objectSQL.toString(), currentPage, numPerPage, Doc.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Doc> findDocForSendInProgress(String instanceId) {
		// 根据instanceId找到对应的Doc对象
		String hql = "from Doc d where d.instanceId = :instanceId";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("instanceId", instanceId);
		List<Doc> list = (List<Doc>)query.getResultList();
		return list;
	}
	public void updateDoc(Doc doc) {
		getEm().merge(doc);
	}
	
	public DTPageBean getSendwd(String wfuid, String title, String zh, String nh, String xh, String startTime, String endTime, int currentPage, int numPerPage){
		StringBuilder querySql = new StringBuilder(
				"select distinct t.docguid\n" +
				"          from OA_DOC_DOCBOX t\n" + 
				"          join wf_process p\n" + 
				"            on t.instanceid = p.wf_instance_uid\n" + 
				"          join wf_step s\n" + 
				"            on p.wf_st_uid = s.wf_st_uid\n" + 
				"          join wf_define d\n" + 
				"            on s.wf_uid = d.wf_uid\n" + 
				"         where t.instanceid is not null\n" + 
				//"           and p.is_over = 'OVER'\n" + 
				"           and d.wf_uid in ("+wfuid+""
		);
		//if(wfstuid != null) querySql.append(" and p.wf_st_uid in").append(wfstuid);
		if(title != null && title.trim().length() != 0){
			querySql.append(" and t.title like '%").append(title).append("%'");
		}
        if(zh != null) querySql.append(" and t.jgdz = '").append(zh).append("'");
		if(nh != null) querySql.append(" and t.fwnh = '").append(nh).append("'");
		if(xh != null) querySql.append(" and t.fwxh = '").append(xh).append("'");
		
		if(startTime != null && startTime.trim().length() != 0){
			querySql.append(" and t.submittm > to_date('").append(startTime).append("','yyyy-MM-dd')");
		}
		if(endTime != null && endTime.trim().length() != 0){
			querySql.append(" and t.submittm < to_date('").append(endTime).append("','yyyy-MM-dd')");
		}
		StringBuilder countSql = new StringBuilder("select count(doc.docguid) from (").append(querySql).append(")) doc");
		StringBuilder hql = new StringBuilder("select x.* from oa_doc_docbox x join (").append(querySql).append(")) doc on x.docguid = doc.docguid order by x.status, nvl(x.submittm,to_date(x.yfrq,'yyyy-MM-dd')) desc");
		return pagingQuery(countSql.toString(), hql.toString(), currentPage, numPerPage, Doc.class);
	}
	
	@SuppressWarnings("unchecked")
	public String checkbw(String wh, String title,String webId) {
		String sql = "select bw.bwguid,bw.bwtype from OA_DOC_RECEIVE t join oa_doc_bw bw on t.docguid = bw.docguid where (t.title = '"+title+"' or t.wh = '"+wh+"') and bw.webid='"+webId+"'and bw.result !="+Constant.BWD_RESULT_ZUOFEI;
		List<Object[]> bwlist = (List<Object[]>) getEm().createNativeQuery(sql).getResultList();
		if(bwlist.isEmpty()){
			return null;
		}
		String str = "[";
		for(Object[] o : bwlist){
			str += "{'bwguid':'"+o[0]+"','bwtype':'"+o[1]+"'},";
		}
		if(!str.equals("[")) {
			str = str.substring(0, str.length()-1);
			str += "]";
		}
		return str;
	}
}
