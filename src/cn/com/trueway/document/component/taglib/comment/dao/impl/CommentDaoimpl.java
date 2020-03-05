/**
 * 
 */
package cn.com.trueway.document.component.taglib.comment.dao.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import cn.com.trueway.base.daoSupport.BaseDao;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.document.component.taglib.comment.dao.CommentDao;
import cn.com.trueway.document.component.taglib.comment.model.po.Comment;
import cn.com.trueway.document.component.taglib.comment.model.po.CommentAtt;
import cn.com.trueway.document.component.taglib.comment.model.po.PersonalComment;
import cn.com.trueway.workflow.core.pojo.WfProcess;


/** 
 * 描述：意见便签用Dao的实现类<br>                                     
 * 作者：顾锡均<br>
 * 创建时间：Feb 24, 2010<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式:同创建时间><br>
 * 修改原因及地方： <修改原因描述><br>
 * 版本：v1.0 <br>           
 * JDK版本：JDK1.5
 */
public class CommentDaoimpl extends BaseDao implements CommentDao{

	public void delete(Comment comment) {
		super.getEm().remove(comment);
	}

	public void save(Comment comment) {
		super.getEm().persist(comment);
	}

	public void update(Comment comment) {
		if (comment != null && comment.getId()!=null && !comment.getId().trim().equals(""))
			super.getEm().merge(comment);
	}

	@SuppressWarnings("unchecked")
	public List<Comment> findByInstanceId(String instanceId, String tagId) {
		//过虑掉办文中的已阅意见
		String hql = "from Comment t where t.instanceId = ? and t.tagId = ? and t.approved !=? order by t.signdate";
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, instanceId);
		query.setParameter(2, tagId);
		query.setParameter(3, Comment.ISBW_HAVE_READABLE);
		List<Comment> l = query.getResultList();
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<Comment> findByInstanceId(String instanceId, String tagId,boolean isDesc) {
		//过虑掉办文中的已阅意见
		String hql = "from Comment t where t.instanceId = ? and t.tagId = ? and t.approved !=? order by t.signdate";
		if(isDesc){
			hql+=" desc";
		}
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, instanceId);
		query.setParameter(2, tagId);
		query.setParameter(3, Comment.ISBW_HAVE_READABLE);
		List<Comment> l = query.getResultList();
		return l;
	}
	@SuppressWarnings("unchecked")
	public List<Comment> getCommentByInstanceId(String instanceId) {
		String hql = "from Comment t where t.instanceId = ?  order by t.signdate";
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, instanceId);
		List<Comment> l = query.getResultList();
		return l;
	}

	public Comment getById(String id) {
		return super.getEm().find(Comment.class, id);
	}

	public void deleteById(String commentId) {
		Comment comment =  this.getById(commentId);
		if(comment!=null){
			this.delete(comment);
		}
	}
	
	public List<PersonalComment> getPersonalComments(String userId){
		List<PersonalComment> l = getPersonalCommentsOnly(userId);
		if (l.size() == 0){
			initPersonalComment(userId);
			l = getPersonalCommentsOnly(userId);
		}
		
		return l;
	}
	
	@SuppressWarnings("unchecked")
	private List<PersonalComment> getPersonalCommentsOnly(String userId){
		String hql = "from PersonalComment t where t.user_id = ? order by t.sort_index";
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, userId);
		List<PersonalComment> l = query.getResultList();
		return l;
	}
	
	public PersonalComment getPCDetail(String cmnt_id){
		return super.getEm().find(PersonalComment.class, cmnt_id);
	}
	
	public void deletePC(String cmnt_id){
		super.getEm().remove(super.getEm().find(PersonalComment.class, cmnt_id));
	}
	
	public void savePersonalComment(String user_id, PersonalComment pc){
		PersonalComment oldPC = super.getEm().find(PersonalComment.class, pc.getCmnt_id());
		
		//处理sort_index，如果重复，则原index之前、新index之后(含)的sort_index+1
		String sql = 
			"SELECT COUNT(*)\n" +
			"  FROM wf_personal_comment t\n" + 
			" WHERE t.user_id = '" + user_id + "'\n" + 
			"   AND t.sort_index = " + pc.getSort_index();
		
		BigDecimal count = (BigDecimal)super.getEm().createNativeQuery(sql).getSingleResult();
		
		if (count.intValue() > 0){
			sql = 
				"UPDATE wf_personal_comment t\n" +
				"   SET t.sort_index = t.sort_index + 1\n" + 
				" WHERE t.user_id = '" + user_id + "'\n" + 
				"   AND t.sort_index < " + (oldPC == null ? Integer.MAX_VALUE : oldPC.getSort_index()) + "\n" + 
				"   AND t.sort_index >= " + pc.getSort_index();
			
			super.getEm().createNativeQuery(sql).executeUpdate();
		}
		
		super.getEm().merge(pc);
	}
	
	private void initPersonalComment(String userId){
		String sql = 
			"INSERT INTO wf_personal_comment\n" +
			"  (cmnt_id, user_id, CONTENT, sort_index)\n" + 
			"  SELECT func_uuid_gen(), '" + userId + "', t.content, t.sort_index\n" + 
			"    FROM wf_personal_comment t\n" + 
			"   WHERE t.user_id = '-1'";
		
		super.getEm().createNativeQuery(sql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getCommentsByStepId(String stepId,String instanceid,String type) {
		String hql = "from Comment t where t.stepId = :stepId and t.instanceId=:instanceid";
		if(type!=null&&!type.equals("")){
			hql+=" and t.tagId='"+type+"'";
		}
		Query query = super.getEm().createQuery(hql);
		query.setParameter("stepId", stepId);
		query.setParameter("instanceid", instanceid);
		List<Comment> l = query.getResultList();
		return l;
	}

	/**
	 * 
	 * 描述：新增意见附件
	 *
	 * @param atts void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:14:21
	 */
	public void addCommentAtt(CommentAtt att){
		if(att!=null&&att.getId()==null){
			getEm().persist(att);
		}
	}
	
	/**
	 * 
	 * 描述：根据Id删除
	 *
	 * @param id void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:18:53
	 */
	public void deleteCommentAttById(String id){
		String hql = "delete CommentAtt t where t.id = :id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", id);
		query.executeUpdate();
	}
	
	/**
	 * 
	 * 描述：根据Id删除一些附件
	 *
	 * @param ids void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-5 下午10:15:40
	 */
	public void deleteCommentAtts(List<String> ids){
		StringBuilder hql = new StringBuilder("delete CommentAtt t where t.id in (");
		if(ids.isEmpty()){
			hql.append("'')");
		}else{
			for (int i = 0; i < ids.size(); i++) {
				if(i==ids.size()-1){
					hql.append("'").append(ids.get(i)).append("')");
				}else{
					hql.append("'").append(ids.get(i)).append("',");
				}
			}
		}
		Query query = super.getEm().createQuery(hql.toString());
		query.executeUpdate();
	}
	
	/**
	 * 
	 * 描述：删除意见的所有附件
	 *
	 * @param commentId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:20:44
	 */
	public void deleteCommentAtts(String commentId){
		String hql = "delete CommentAtt t where t.commentId = :commentId";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("commentId", commentId);
		query.executeUpdate();
	}
	
	/**
	 * 
	 * 描述：更新意见附件
	 *
	 * @param att void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:19:08
	 */
	public void updateCommnetAtt(CommentAtt att){
		if(att!=null&&att.getId()!=null){
			getEm().merge(att);
		}
	}
	
	/**
	 * 
	 * 描述：根据Id查找附件
	 *
	 * @param id
	 * @return CommentAtt
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:19:29
	 */
	@SuppressWarnings("unchecked")
	public CommentAtt findCommnetAttById(String id){
		String hql = "from CommentAtt t where t.id = :id";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("id", id);
		List<CommentAtt> list = query.getResultList();
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * 描述：根据commentId查找附件
	 *
	 * @param id
	 * @return CommentAtt
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:19:29
	 */
	@SuppressWarnings("unchecked")
	public List<CommentAtt> findCommnetAttBycomId(String commentIds){
		List<String> ids = Arrays.asList(commentIds.split(","));
		String comIds = "";
		for (String id : ids) {
			comIds = comIds+ ",'"+id+"'";
		}		
		comIds = comIds.substring(1,comIds.length());
		String sql = "select t.* from WF_COMMENT_ATTACHMENTS t where t.COMMENT_ID  in ("+comIds+")";
		Query query = super.getEm().createNativeQuery(sql,CommentAtt.class);
		List<CommentAtt> list = query.getResultList();
		return list;
	}
	
	/**
	 * 
	 * 描述：根据意见Id查出所有附件
	 *
	 * @param commentId
	 * @return List<CommentAtt>
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:19:47
	 */
	@SuppressWarnings("unchecked")
	public List<CommentAtt> findCommnetAtts(String commentId){
		String hql = "from CommentAtt t where t.commentId = :commentId";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("commentId", commentId);
		List<CommentAtt> list = query.getResultList();
		return list;
	}
	
	/**
	 * 
	 * 描述：为没有设置意见Id的附件设置意见Id
	 *
	 * @param Ids
	 * @param commentId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-5 下午10:38:42
	 */
	public void updateAttsCommentId(List<String> ids,String commentId){
		StringBuilder hql = new StringBuilder("update CommentAtt t set t.commentId =:commentId where t.id in (");
		if(ids==null||ids.isEmpty()){
			hql.append("'')");
		}else{
			for (int i = 0; i < ids.size(); i++) {
				if(i==ids.size()-1){
					hql.append("'").append(ids.get(i)).append("')");
				}else{
					hql.append("'").append(ids.get(i)).append("',");
				}
			}
		}
		Query query = super.getEm().createQuery(hql.toString());
		query.setParameter("commentId", commentId);
		query.executeUpdate();
	}
	
	public int getCommentSizebyUserId(String instanceId, String userId) {
		String hql = "select count(t.id) from Comment t where t.instanceId =:instanceId and t.userId=:userId";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("instanceId", instanceId);
		query.setParameter("userId", userId);
		String size = query.getSingleResult().toString();
		return Integer.valueOf(size) ;
	}

	/**
	 * 
	 * 描述：查询某步骤，某人，某标签中的意见个数
	 *
	 * @param tagId
	 * @param stepId
	 * @param userId
	 * @return int
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-13 下午05:26:27
	 */
	public int findCommentCount(String tagId, String stepId, String userId) {
		String hql = "select count(t.id) from Comment t where t.stepId =:stepId and t.userId=:userId and t.tagId=:tagId";
		Query query = super.getEm().createQuery(hql);
		query.setParameter("stepId", stepId);
		query.setParameter("userId", userId);
		query.setParameter("tagId", tagId);
		String count = query.getSingleResult().toString();
		return Integer.valueOf(count);
	}

	/**
	 * 
	 * 描述：查询某一篇文中的的意见标签里所有附件
	 *
	 * 作者:zhaoj<br>
	 * 创建时间:2012-7-28 下午14:10:27
	 */
	@SuppressWarnings("unchecked")
	public List<CommentAtt> findCommnetAttByInstanceId(String instanceId) {
		String sql = "select t.* from WF_COMMENT_ATTACHMENTS t where  t.COMMENT_ID in (select t.COMMENT_ID from WF_COMMENT t where t.WF_INSTANCE_ID ='"+instanceId+"' )";
		Query query = super.getEm().createNativeQuery(sql,CommentAtt.class);
		return query.getResultList();
	}


	/**
	 * 
	 * 描述：增加意见标签附件
	 *
	 * @param attsext void
	 *
	 * 作者:zhaoj<br>
	 * 创建时间:2012-7-28 下午15：01
	 */
	public void addcommentAtt(CommentAtt commentAtt){
		if(commentAtt==null){
			return;
		}
		getEm().persist(commentAtt);
	}

	@SuppressWarnings("unchecked")
	public List<Comment> findCommentsByInstanceId(String instanceId) {
		String sql = "select t.* from WF_COMMENT t where t.wf_instance_id ='"+instanceId+"'";
		Query query = super.getEm().createNativeQuery(sql,Comment.class);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Comment> findCommentsByElements(String userId, String wfInstanceUid, String wfProcessUid) {
		String hql = "from Comment t where t.instanceId = ? and t.stepId = ? and t.userId = ?";
		Query query = super.getEm().createQuery(hql);
		query.setParameter(1, wfInstanceUid);
		query.setParameter(2, wfProcessUid);
		query.setParameter(3, userId);
		List<Comment> l = query.getResultList();
		return l;
	}

	@Override
	public WfProcess getProcessById(String processId) {
		return super.getEm().find(WfProcess.class, processId);
	}
	

	@Override
	public List<PersonalComment> getCommentList(Map<String, String> map,
			Integer pageindex, Integer pagesize) {
		String userid = map.get("userid");
		String content = map.get("content");
		String hql =" from PersonalComment t where t.user_id = '"+userid+"'";
		if (CommonUtil.stringNotNULL(content)){
			hql += " and t.content like '%"+content.trim()+"%'";
		}
			hql += " order by t.sort_index asc";
		Query query = getEm().createQuery(hql) ;
		if (pageindex != null && pagesize != null) {
			query.setFirstResult( pageindex);
			query.setMaxResults( pagesize);
		}
		return query.getResultList();
	}

	@Override
	public int getCommentCount(Map<String, String> map) {
		String userid = map.get("userid");
		String content = map.get("content");
		String sql = "select count(*) from WF_PERSONAL_COMMENT t where t.user_id = '"+userid+"'";
		int count = Integer.parseInt(getEm().createNativeQuery(sql).getSingleResult().toString());
		String sql2 =  "select count(*) from WF_PERSONAL_COMMENT t where t.user_id = '"+userid+"'";
		if(CommonUtil.stringNotNULL(content)){
			sql2 += " and t.content like '%"+content.trim()+"%'";
		}
		if(count>0){
			//已经被初始化：重新获取
			return Integer.parseInt(getEm().createNativeQuery(sql2).getSingleResult().toString());
		}
		initPersonalComment(userid);
		return Integer.parseInt(getEm().createNativeQuery(sql2).getSingleResult().toString());
	}

	@Override
	public void addPersonComment(PersonalComment personalComment) {
		super.getEm().persist(personalComment);
	}

	@Override
	public PersonalComment getPersonalCommentById(String id) {
		String hql = " from PersonalComment t where t.cmnt_id = :cmnt_id";
		List<PersonalComment> list = getEm().createQuery(hql).setParameter("cmnt_id", id).getResultList();
		if(list==null || list.size() ==0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public void updatePersonalComment(PersonalComment personalComment) {
		super.getEm().merge(personalComment);
	}

	@Override
	public void deletePersonalComment(PersonalComment personalComment) {
		getEm().remove(getEm().merge(personalComment));
	}

	@Override
	public PersonalComment getCommentByContentAndSort(String empId,String content,
			String sortIndex) {
		String hql= " from PersonalComment t where t.user_id= :user_id and t.content= :content";
		List<PersonalComment> list = getEm().createQuery(hql).setParameter("user_id", empId).setParameter("content", content).getResultList();
		if(list==null || list.size() ==0){
			return null;
		}
		return list.get(0);
	}
}
