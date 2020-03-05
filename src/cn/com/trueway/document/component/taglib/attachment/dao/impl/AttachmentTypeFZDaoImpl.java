package cn.com.trueway.document.component.taglib.attachment.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.document.component.taglib.attachment.dao.AttachmentTypeFZDao;
import cn.com.trueway.document.component.taglib.attachment.model.po.AttachFileType;
import cn.com.trueway.document.component.taglib.attachment.model.po.AttachmentTypeBind;
import cn.com.trueway.document.component.taglib.attachment.model.po.AttachmentTypeFZ;
import cn.com.trueway.document.component.taglib.attachment.model.po.AttachmentTypeSub;
import cn.com.trueway.document.component.taglib.attachment.model.po.AttachmentTypeWfMainShip;

public class AttachmentTypeFZDaoImpl extends BaseDao implements AttachmentTypeFZDao {

	@Override
	public int findAttachmentTypeCount(String conditionSql) {
		String sql = "select count(*) from wf_attachmentsext_type t";
		if(conditionSql!=null && !conditionSql.equals("")){
			sql += " where 1=1 "+ conditionSql;
		}
		return Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttachmentTypeFZ> findAttachmentTypeList(String conditionSql,
			Integer pageIndex, Integer pageSize) {
		String sql = "select t.* from wf_attachmentsext_type t";
		if(conditionSql!=null && !conditionSql.equals("")){
			sql += " where 1=1 "+ conditionSql;
		}
		sql += " order by t.att_seq desc";
		Query query = getEm().createNativeQuery(sql, AttachmentTypeFZ.class);
		if (pageIndex != null && pageSize != null) {
			query.setFirstResult(pageIndex);
			query.setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	@Override
	public void saveAttachmentType(AttachmentTypeFZ attachmentType)
			throws Exception {
		getEm().persist(attachmentType);
	}

	@Override
	public AttachmentTypeFZ findAttachmentTypeById(String id) {
		return getEm().find(AttachmentTypeFZ.class, id);
	}

	@Override
	public void delAttachmentType(AttachmentTypeFZ attachmentType)
			throws Exception {
		getEm().remove(getEm().merge(attachmentType));
	}

	@Override
	public void updateAttachmentType(AttachmentTypeFZ attachmentType)
			throws Exception {
		getEm().merge(attachmentType);
	}

	@Override
	public void saveAttachmentTypeBind(AttachmentTypeBind attachmentTypeBind)
			throws Exception {
		getEm().persist(attachmentTypeBind);
		
	}

	@Override
	public void deleteAttachmentTypeBind(String nodeId) {
		String sql = "delete from t_wf_core_attachment_bind t where t.nodeId = '"+nodeId+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttachmentTypeBind> getAttachmentTypeBindList(String nodeId)
			throws Exception {
		String sql = " select t.* from t_wf_core_attachment_bind t where t.nodeId = '"+nodeId+"'" ;
		return getEm().createNativeQuery(sql, AttachmentTypeBind.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttachmentTypeSub> findAttachmentTypeSubList(String itemId,
			String instanceId, String nodeId) throws Exception {
		//该节点已被添加或者暂存
		String hql = "select t.id,t.instanceid,t.isbqbz, t.issub,t.itemid, t.typeid, " +
				"t2.att_type, t2.att_sid, t2.att_required, t2.att_lack, t.att_count " +
				" from wf_attachmentsext_type_sub t, wf_attachmentsext_type t2," +
				" t_wf_core_attachment_bind t3" +
				" where t.instanceId='"+instanceId+"' and t2.id = t3.attachmenttypeId" +
				" and t3.nodeId = '"+nodeId+"'" +
				" and t.typeid = t2.id ";
		List<Object[]> list = getEm().createNativeQuery(hql).getResultList();
		List<AttachmentTypeSub> sublist  = new ArrayList<AttachmentTypeSub>();
		AttachmentTypeSub attachmentTypeSub = null;
		if(list!=null && list.size()>0){
			for(int i=0; list!=null && i<list.size(); i++){
				attachmentTypeSub = new AttachmentTypeSub();
				Object[] data = list.get(i);
				attachmentTypeSub.setId(data[0]==null?"":data[0].toString());
				attachmentTypeSub.setInstanceId(data[1]==null?"":data[1].toString());
				attachmentTypeSub.setIsbqbz(data[2]==null?"":data[2].toString());
				attachmentTypeSub.setIsSub(data[3]==null?"":data[3].toString());
				attachmentTypeSub.setItemId(data[4]==null?"":data[4].toString());
				attachmentTypeSub.setTypeId(data[5]==null?"":data[5].toString());
				attachmentTypeSub.setTypeName(data[6]==null?"":data[6].toString());
				attachmentTypeSub.setAtt_sid(data[7]==null?"":data[7].toString());
				attachmentTypeSub.setAtt_required(data[8]==null?"":data[8].toString());
				attachmentTypeSub.setAtt_lack(data[9]==null?"":data[9].toString());
				attachmentTypeSub.setAtt_count(data[10]==null?"":data[10].toString());
				sublist.add(attachmentTypeSub);
			}
		}else{
			String sql = "select t.id, t.att_type, t.att_sid, t.att_required, t.att_lack " +
					" from wf_attachmentsext_type t, t_wf_core_attachment_bind t2" +
					" where t.id = t2.attachmenttypeId and t2.nodeId = '"+nodeId+"'";
			List<Object[]> list2 = getEm().createNativeQuery(sql).getResultList();
			for(int i=0; list2!=null && i<list2.size(); i++){
				attachmentTypeSub = new AttachmentTypeSub();
				Object[] data = list2.get(i);
				attachmentTypeSub.setTypeId(data[0]==null?"":data[0].toString());
				attachmentTypeSub.setTypeName(data[1]==null?"":data[1].toString());
				attachmentTypeSub.setAtt_sid(data[2]==null?"":data[2].toString());
				attachmentTypeSub.setAtt_required(data[3]==null?"":data[3].toString());
				attachmentTypeSub.setAtt_lack(data[4]==null?"":data[4].toString());
				sublist.add(attachmentTypeSub);
			}
		}
		return sublist;
	}

	@Override
	public void saveAttachmentTypeSub(AttachmentTypeSub attachmentTypeSub)
			throws Exception {
		getEm().persist(attachmentTypeSub);
	}

	@Override
	public void updateAttachmentTypeSub(AttachmentTypeSub attachmentTypeSub)
			throws Exception {
		getEm().merge(attachmentTypeSub);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttachmentTypeSub> findAttachmentTypeSubById(String instanceId, String nodeId)
			throws Exception {
		// 该节点已被添加或者暂存
		String hql = "select t.id,t.instanceid,  t.isbqbz, t.issub,t.itemid, t.typeid, t2.att_type "
				+ " from wf_attachmentsext_type_sub t, wf_attachmentsext_type t2,"
				+ " t_wf_core_attachment_bind t3"
				+ " where t.instanceId='"
				+ instanceId
				+ "' and t2.id = t3.attachmenttypeId"
				+ " and t.typeid = t2.id "
				+ " and t3.nodeid ='"+nodeId+"'";
		List<Object[]> list = getEm().createNativeQuery(hql).getResultList();
		List<AttachmentTypeSub> sublist = new ArrayList<AttachmentTypeSub>();
		AttachmentTypeSub attachmentTypeSub = null;
		if (list != null && list.size() > 0) {
			for (int i = 0; list != null && i < list.size(); i++) {
				attachmentTypeSub = new AttachmentTypeSub();
				Object[] data = list.get(i);
				attachmentTypeSub.setId(data[0] == null ? "" : data[0].toString());
				attachmentTypeSub.setInstanceId(data[1] == null ? "" : data[1].toString());
				attachmentTypeSub.setIsbqbz(data[2] == null ? "" : data[2].toString());
				attachmentTypeSub.setIsSub(data[3] == null ? "" : data[3].toString());
				attachmentTypeSub.setItemId(data[4] == null ? "" : data[4].toString());
				attachmentTypeSub.setTypeId(data[5] == null ? "" : data[5].toString());
				attachmentTypeSub.setTypeName(data[6] == null ? "" : data[6].toString());
				sublist.add(attachmentTypeSub);
			}
		}
		return sublist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttachmentTypeSub> findUnSubedList(String instanceId,
			String nodeId){
		String hql = "select t.id,t.instanceid,  t.isbqbz, t.issub,t.itemid, t.typeid, t2.att_type " +
				" from wf_attachmentsext_type_sub t, wf_attachmentsext_type t2," +
				" t_wf_core_attachment_bind t3" +
				" where t.instanceId='"+instanceId+"' and t2.id = t3.attachmenttypeId" +
				" and t3.nodeId = '"+nodeId+"'" +
				" and t.typeid = t2.id and t.issub='0'";
		List<Object[]> list = getEm().createNativeQuery(hql).getResultList();
		List<AttachmentTypeSub> sublist  = new ArrayList<AttachmentTypeSub>();
		AttachmentTypeSub attachmentTypeSub = null;
		if(list!=null && list.size()>0){
			for(int i=0; list!=null && i<list.size(); i++){
				attachmentTypeSub = new AttachmentTypeSub();
				Object[] data = list.get(i);
				attachmentTypeSub.setId(data[0]==null?"":data[0].toString());
				attachmentTypeSub.setInstanceId(data[1]==null?"":data[1].toString());
				attachmentTypeSub.setIsbqbz(data[2]==null?"":data[2].toString());
				attachmentTypeSub.setIsSub(data[3]==null?"":data[3].toString());
				attachmentTypeSub.setItemId(data[4]==null?"":data[4].toString());
				attachmentTypeSub.setTypeId(data[5]==null?"":data[5].toString());
				attachmentTypeSub.setTypeName(data[6]==null?"":data[6].toString());
				sublist.add(attachmentTypeSub);
			}
		}
		return sublist;
	}

	@Override
	public void saveAttachTypeWfMainShip(AttachmentTypeWfMainShip attachTypeWfMainShip)
			throws Exception {
		getEm().persist(attachTypeWfMainShip);
	}

	@Override
	public List<AttachmentTypeWfMainShip> findAttachTypeWfMainShipList(
			String attachTypeId) {
		if(attachTypeId==null || attachTypeId.equals("")){
			return null;
		}
		String sql = " from AttachmentTypeWfMainShip t where t.attacthTypeId = '"+attachTypeId+"'";
		return getEm().createQuery(sql).getResultList();
	}

	@Override
	public void deleteAttachTypeWfMainShip(String attachTypeId, String searchIds) {
		String sql = "delete from t_wf_core_attachwfmain_ship t where t.attacthTypeId='"+attachTypeId+"'";
		if(searchIds!=null && !searchIds.equals("")){
			sql += " and t.wfuid in ("+searchIds+")";
		}
		getEm().createNativeQuery(sql).executeUpdate();
		
	}

	@Override
	public List<AttachmentTypeFZ> findAttachmentTypeList(String conditionSql) {
		String hql = "select t.* from wf_attachmentsext_type t, t_wf_core_attachwfmain_ship t2" +
				" where t.id = t2.attacthtypeid ";
		hql += conditionSql;
		return getEm().createNativeQuery(hql, AttachmentTypeFZ.class).getResultList();
	}

	@Override
	public List<AttachFileType> findAttachFileTypeList(String sslx) {
		String hql = " from AttachFileType t ";
		if(sslx!=null && !sslx.equals("")){
			hql += " where att_sslb = '"+sslx+"'";
		}
		return getEm().createQuery(hql).getResultList();
	}

	@Override
	public void saveAttachFileType(AttachFileType attachFileType) {
		getEm().persist(attachFileType);
	}

	@Override
	public AttachFileType findAttachFileTypeById(String id) {
		return getEm().find(AttachFileType.class, id);
	}

	@Override
	public void updateAttachFileType(AttachFileType attachFileType) {
		getEm().merge(attachFileType);
	}

	@Override
	public void deleteAttachFileTypeById(String id) {
		String sql = "delete from wf_attachfile_type_type t where t.id = '"+id+"'";
		getEm().createNativeQuery(sql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttachmentTypeWfMainShip> findAttachmentTypeWfMainShipList(
			String lcId) {
		String hql = " from AttachmentTypeWfMainShip t where t.wfUid = '"+lcId+"'";
		return getEm().createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAttachmentTypeBind(String lcId) {
		String  sql = " select  t.wfn_id,  t.wfn_name, t2.attachmenttypeid " +
				" from wf_node t, t_wf_core_attachment_bind t2 " +
				"where t.wfn_pid = '"+lcId+"' and t2.nodeid = t.wfn_id";
		return getEm().createNativeQuery(sql).getResultList();
	}
}
