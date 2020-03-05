package cn.com.trueway.document.component.taglib.attachment.dao.impl;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import com.asprise.util.tiff.q;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.Attachmentsext_type;
import cn.com.trueway.document.component.taglib.attachment.model.po.CutPages;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.ReceiveAttachmentsext;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttHistoryLog;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsHistory;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachmentsext;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.WfOnlineEditStatus;


public class AttachmentDaoImpl  extends BaseDao  implements AttachmentDao {

	/**
	 * 
	 * 描述：根据ID查出单个【正文附件】（发文...）
	 *
	 * @param id
	 * @return SendAttachments
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:44:45
	 */
	public SendAttachments findSendAtts(String id){
		if(id==null){
			return null;
		}
		return getEm().find(SendAttachments.class, id);
	}
	
	/**
	 *            
	 * 描述：根据ID查出单个【附加附件】（发文...）
	 *
	 * @param id
	 * @return OA_DOC_Attachmentsext
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:45:23
	 */
	public SendAttachmentsext findSendAttsext(String id){
		if(id==null){
			return null;
		}
		return getEm().find(SendAttachmentsext.class, id);
	}
	
	/**
	 * 
	 * 描述：根据ID查出单个【正文附件】（收文...）
	 *
	 * @param id
	 * @return OA_RECDOC_Attachments
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:45:38
	 */
	public ReceiveAttachments findReceiveAtts(String id){
		if(id==null){
			return null;
		}
		return getEm().find(ReceiveAttachments.class, id);
	}

	/**
	 * 
	 * 描述：根据ID查出单个【附加附件】（收文...）
	 *
	 * @param id
	 * @return OA_RECDOC_Attachmentsext
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:45:52
	 */
	public ReceiveAttachmentsext findReceiveAttsext(String id){
		if(id==null){
			return null;
		}
		return getEm().find(ReceiveAttachmentsext.class, id);
	}
	
	/**
	 * 
	 * 描述：根据Docguid查出所有符合条件的【正文附件】（发文...）
	 *
	 * @param docguid
	 * @return List<SendAttachments>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:35:01
	 */
	@SuppressWarnings("unchecked")
	public List<SendAttachments> findAllSendAtts(String docguid,String isHb){
		String sql = "from SendAttachments t where t.docguid=:docguid ";
		if (CommonUtil.stringNotNULL(isHb)){
			sql += " and t.type != '" + isHb +"'";
		}
		sql += " order by t.filetime asc";
		Query hql= getEm().createQuery(sql);
		hql.setParameter("docguid",docguid);
		List attsList = hql.getResultList();
		return attsList;
	}

	
	/**
	 * 
	 * 描述：描述：根据Docguid查出所有符合条件的【附加附件】（发文...）
	 *
	 * @param docguid
	 * @return List<OA_DOC_Attachmentsext>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:37:21
	 */
	@SuppressWarnings("unchecked")
	public List<SendAttachmentsext> findAllSendAttsext(String docguid){
		Query hql= getEm().createQuery("from SendAttachmentsext t where t.docguid =:docguid order by t.filetime");
		hql.setParameter("docguid",docguid);
		List attsextList = hql.getResultList();
		return attsextList;
	}

	/**
	 * 
	 * 描述：根据Docguid查出所有符合条件的【正文附件】（收文...）
	 *
	 * @param docguid
	 * @return List<OA_RECDOC_Attachments>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:38:01
	 */
	@SuppressWarnings("unchecked")
	public List<ReceiveAttachments> findAllReceiveAtts(String docguid){
		Query hql= getEm().createQuery("from ReceiveAttachments t where t.docguid=:docguid order by t.filetime");
		hql.setParameter("docguid",docguid);
		List attsList = hql.getResultList();
		return attsList;
	}

	/**
	 * 
	 * 描述：描述：根据Docguid查出所有符合条件的【附加附件】（收文...）
	 *
	 * @param docguid
	 * @return List<OA_RECDOC_Attachmentsext>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:38:30
	 */
	@SuppressWarnings("unchecked")
	public List<ReceiveAttachmentsext> findAllReceiveAttsext(String docguid){
		Query hql= getEm().createQuery("from ReceiveAttachmentsext t where t.docguid=:docguid order by t.filetime");
		hql.setParameter("docguid",docguid);
		List attsextList = hql.getResultList();
		return attsextList;
	}
	
	/**
	 * 
	 * 描述：增加【正文附件】（发文...）
	 *
	 * @param atts void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:39:37
	 */
	public SendAttachments addSendAtts(SendAttachments atts){
		if(atts==null){
			return null;
		}
		getEm().persist(atts);
		return atts;
	}
	
	/**
	 * 
	 * 描述：增加【附加附件】（发文...）
	 *
	 * @param attsext void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:40:18
	 */
	public void addSendAttsext(SendAttachmentsext attsext){
		if(attsext==null){
			return;
		}
		getEm().persist(attsext);
	}
	
	/**
	 * 
	 * 描述：增加【正文附件】（收文...）
	 *
	 * @param atts void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:40:36
	 */
	public void addReceiveAtts(ReceiveAttachments atts){
		if(atts==null){
			return;
		}
		getEm().persist(atts);
	}
	
	/**
	 * 
	 * 描述：增加【附加附件】（收文...）
	 *
	 * @param attsext void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:41:11
	 */
	public void addReceiveAttsext(ReceiveAttachmentsext attsext){
		if(attsext==null){
			return;
		}
		getEm().persist(attsext);
	}

	/**
	 * 
	 * 描述：删除【正文附件】（发文...）
	 *
	 * @param attsId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:42:38
	 */
	public void deleteSendAtts(String attsId){
		SendAttachments sendAtts= this.findSendAtts(attsId);
		if(sendAtts!=null){
			String docguid=sendAtts.getDocguid();
			String _sql="delete from OA_DOC_ATTACHMENTS_HISTORY t where t.docguid = :docguid";
			getEm().createNativeQuery(_sql).setParameter("docguid", docguid).executeUpdate();
			String sql="delete from OA_DOC_ATTACHMENTS t where t.id = :id";
			getEm().createNativeQuery(sql).setParameter("id", attsId).executeUpdate();
		}
	}


	/**
	 * 
	 * 描述：删除【附加附件】（发文...）
	 *
	 * @param attsId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:42:38
	 */
	public void deleteSendAttsext(String attsextId){
		String sql="delete from OA_DOC_ATTACHMENTSEXT t where t.id = :id";
		getEm().createNativeQuery(sql.toString()).setParameter("id", attsextId).executeUpdate();
	}


	/**
	 * 
	 * 描述：删除【正文附件】（收文...）
	 *
	 * @param attsId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:42:38
	 */
	public void deleteReceiveAtts(String attsId){
		String sql="delete from OA_RECDOC_ATTACHMENTS t where t.id = :id";
		getEm().createNativeQuery(sql.toString()).setParameter("id",attsId).executeUpdate();
	}
	
	/**
	 * 
	 * 描述：删除【附加附件】（收文...）
	 *
	 * @param attsId
	 * @param isReceive void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-2 上午09:42:38
	 */
	public void deleteReceiveAttsext(String attsextId){
		String sql="delete from OA_RECDOC_ATTACHMENTSEXT t where t.id = :id";
		getEm().createNativeQuery(sql.toString()).setParameter("id",attsextId).executeUpdate();
	}

	/**
	 * 
	 * 描述：正文在线编辑历史 保存
	 *
	 * @param sendAttachmentsHistory void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 下午03:15:13
	 */
	public void addSendAttHistory(SendAttachmentsHistory sendAttachmentsHistory) {
		if(sendAttachmentsHistory==null){
			return;
		}
		getEm().persist(sendAttachmentsHistory);
		
	}

	/**
	 * 
	 * 描述：查询 正文在线编辑历史
	 *
	 * @param docguid
	 * @return List<SendAttachmentsHistory>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 下午03:15:17
	 */
	@SuppressWarnings("unchecked")
	public List<SendAttachmentsHistory> findAllSendAttHistory(String docguid,String id) {
		String hql = "from SendAttachmentsHistory t where t.docguid =:docguid ";
		if(id!=null&&!"".equals(id)){
			hql +=" and  t.fjid =:fjid ";
		}
		hql +=" order by t.filetime desc";
		Query query= getEm().createQuery(hql);
		query.setParameter("docguid",docguid);
		if(id!=null&&!"".equals(id)){
			query.setParameter("fjid",id);
		}
		List attHistoryList = query.getResultList();
		return attHistoryList;
	}

	public void updateSendAtt(SendAttachments att) {
		if(att!=null&&att.getId()!=null){
			getEm().merge(att);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Attachmentsext_type> findAllAttType() {
		Query hql= getEm().createQuery("from Attachmentsext_type t");
		return hql.getResultList();
	}

	public SendAttachments modifyAtts(String attsId) {
		if(attsId==null){
			return null;
		}
		return getEm().find(SendAttachments.class, attsId);
	}

	public void modifyOfAtts(String attsId, String title, String type) {    
		String sql="update OA_DOC_ATTACHMENTS t set t.ATTTITLE='"+title+"' where t.id ='"+attsId+"'";
		String sql1="update OA_DOC_ATTACHMENTS t set t.ATTTYPE ='"+type+"' where t.id ='"+attsId+"'";
		getEm().createNativeQuery(sql.toString()).executeUpdate();
		getEm().createNativeQuery(sql1.toString()).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<SendAttachments> findSendAttsByDocguid(String instanceId) {
		String sql = "select t.* from oa_doc_attachments t where t.docguid ='"+instanceId+"'";
		return getEm().createNativeQuery(sql,SendAttachments.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<SendAttachments> findSendAttachmentList(String instanceId, List<String> typeList){
		StringBuffer buffer = new StringBuffer(512);
		buffer.append(" select t.* from oa_doc_attachments t where t.docguid like '"+instanceId+"%'");
		if(typeList!=null && typeList.size()>0){
			buffer.append(" and (");
			for(int i=0; i<typeList.size(); i++){
				buffer.append(" t.filetype ='"+typeList.get(i)+"' ");
				if(i!=typeList.size()-1){
					buffer.append(" or ");
				}
			}
			buffer.append(")");
			
		}
		return getEm().createNativeQuery(buffer.toString(),SendAttachments.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SendAttachmentsHistory> findIsEditOfSendAttHistory(String name) {
		Query hql= getEm().createQuery("from SendAttachmentsHistory t where t.docguid =:docguid and t.isHaveEdit='1' order by t.filetime desc");
		hql.setParameter("docguid",name);
		List attHistoryList = hql.getResultList();
		return attHistoryList;
	}

	public void updateSendAttHistory(SendAttachmentsHistory sh) {
		if(sh!=null&&sh.getId()!=null){
			getEm().merge(sh);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SendAttachments> findSendAttsByIdAndUserName(String instanceId, String employeeGuid) {
		String sql = "select t.*, t.rowid from oa_doc_attachments t where t.docguid='"+instanceId+"pushfj' and t.editer like '%"+employeeGuid+"%'";
		return getEm().createNativeQuery(sql,SendAttachments.class).getResultList();
	}

	@Override
	public void deleteAtts(SendAttachments att) {
		if(att==null){
			return ;
		}
		String sql = "delete from OA_DOC_ATTACHMENTS t " +
				" where t.docguid='"+att.getDocguid()+"'" +
				" and t.filename = '"+att.getFilename()+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public SendAttachments findAllSendAtt(String name) {
		String sql = "select t.*, t.rowid from oa_doc_attachments t where t.FILENAME = '"+name+"'";
		List<SendAttachments> list = getEm().createNativeQuery(sql,SendAttachments.class).getResultList();
		
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	
	}

	@Override
	public List<SendAttachments> findSendAttachmentListByInstanceId(
			String instanceId) {
		String sql = "select t.* from oa_doc_attachments t where t.docguid like '"+instanceId+"%' order by t.filetime asc";
		return getEm().createNativeQuery(sql, SendAttachments.class).getResultList();
	}

	@Override
	public List<SendAttachments> getAttachListByNode(Map<String, String> map) {
		String instanceId = "";
		String nodeId = "";
		String type = "";
		if(map != null){
			instanceId = map.get("instanceId");
			nodeId = map.get("nodeId");
			type = map.get("type");
			if(StringUtils.isNotBlank(type) && type.equals("0")){
				String attSuffixName 	= SystemParamConfigUtil.getParamValueByParam("attSuffixName");
				if(StringUtils.isNotBlank(instanceId)){
					instanceId += attSuffixName;
				}
			}
		}
		
		String sql = "select t.* from oa_doc_attachments t where 1=1 ";
		if(CommonUtil.stringNotNULL(instanceId)){
			sql = sql + " and t.docguid like '" + instanceId+"%'";
		}
		if(CommonUtil.stringNotNULL(nodeId)){
			sql = sql + " and t.nodeId like '" + nodeId + "%'";
		}
		return getEm().createNativeQuery(sql, SendAttachments.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<SendAttachments> findAllSendAtts(String docguid){
		String sql = " select t.* from oa_doc_attachments t where t.docguid ='"+docguid+ "' order by t.filetime asc";
		//Query hql= getEm().createQuery("from SendAttachments t where t.docguid=:docguid order by t.isTop desc nulls last, t.filetime asc");
	//	hql.setParameter("docguid",docguid);
		//List attsList = hql.getResultList();
		//return attsList;
		return getEm().createNativeQuery(sql, SendAttachments.class).getResultList();
	}

	@Override
	public void saveCutPages(CutPages entity) {
		getEm().persist(entity);
	}

	@Override
	public void deleteCutPages(CutPages entity) {
		getEm().remove(getEm().merge(entity));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CutPages> findCutPagesListByDocId(String docId) {
		String sql = " select t.* from oa_doc_cutpages t " +
				" where t.docId ='"+docId+ "' order by t.sort asc";
		return getEm().createNativeQuery(sql, CutPages.class).getResultList();
	}

	@Override
	public Integer countNoCuteAtt(Integer fileSize) {
		Integer size = fileSize*1024*1024;
		String sql = "select count(*) from oa_doc_attachments t "+
				"where t.filesize >  "+ size +
				"and t.id not in (select distinct c.docid from oa_doc_cutpages c)";
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getNoCuteAtt(Integer fileSize, Integer pageIndex, Integer pageSize) {
		Integer size = fileSize*1024*1024;
		String sql = "select t.id from oa_doc_attachments t "+
				"where t.filesize >  "+ size +
				"and t.id not in (select distinct c.docid from oa_doc_cutpages c)";
		Query query = getEm().createNativeQuery(sql);
		if(null != pageIndex && null != pageSize){
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return (List<String>)query.getResultList();
	}

	@Override
	public void update(Blob data, Blob pdfData, String fileId){
		String sql = "update oa_doc_attachments t set t.data=empty_blob(),t.pdfdata=empty_blob() where t.id=:id";
		Query query = getEm().createNativeQuery(sql);
		query.setParameter("id", fileId);
		query.executeUpdate();
		
		String sql2 = "update oa_doc_attachments t set t.data=:data,t.pdfdata=:pdfdata where t.id=:id";
		Query query2 = getEm().createNativeQuery(sql2);
		query2.setParameter("data", data);
		query2.setParameter("pdfdata", pdfData);
		query2.setParameter("id", fileId);
		query2.executeUpdate();
	}

	@Override
	public SendAttachmentsHistory findSendAttHistory(String id) {
		String hql = "from SendAttachmentsHistory where id =:id";
		Query query = getEm().createQuery(hql);
		query.setParameter("id", id);
		List<SendAttachmentsHistory> list = query.getResultList();
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public WfOnlineEditStatus findOESByInfo(String attId, String userId) {
		String hql ="  from WfOnlineEditStatus where attId='"+attId+"'";
		if(StringUtils.isNotBlank(userId)){
			hql += "and userId='"+userId+"'";
		}
		try {
			List<WfOnlineEditStatus> list = getEm().createQuery(hql).getResultList();
			if(list!=null && list.size()>0){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addWfOnlineEditStatus(WfOnlineEditStatus wfOnlineEditStatus) {
		if(wfOnlineEditStatus==null){
			return;
		}
		getEm().persist(wfOnlineEditStatus);
		
	}

	@Override
	public void updateWfOnlineEditStatus(WfOnlineEditStatus wfOnlineEditStatus) {
		if(wfOnlineEditStatus!=null && wfOnlineEditStatus.getId()!=null){
			getEm().merge(wfOnlineEditStatus);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CutPages findCutPageById(String id) {
		String sql = " select t.* from oa_doc_cutpages t " +
				" where t.id ='"+id+ "'";
		List<CutPages> list = getEm().createNativeQuery(sql, CutPages.class).getResultList();
		if(null != list && list.size()>0){
			return list.get(0);
		}
		return null; 
	}

	@Override
	public void deleteAttsByIds(String ids) {
		String sql = "delete from oa_doc_attachments t where t.id in("+ids+")";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@Override
	public void addSendAttHistoryLog(SendAttHistoryLog sendAttHistoryLog) {
		if(sendAttHistoryLog==null){
			return;
		}
		getEm().persist(sendAttHistoryLog);
	}
	
}
