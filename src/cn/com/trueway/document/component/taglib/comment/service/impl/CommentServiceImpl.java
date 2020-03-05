package cn.com.trueway.document.component.taglib.comment.service.impl;

import java.util.List;
import java.util.Map;

import cn.com.trueway.document.component.taglib.comment.dao.CommentDao;
import cn.com.trueway.document.component.taglib.comment.model.po.Comment;
import cn.com.trueway.document.component.taglib.comment.model.po.CommentAtt;
import cn.com.trueway.document.component.taglib.comment.model.po.PersonalComment;
import cn.com.trueway.document.component.taglib.comment.service.CommentService;
import cn.com.trueway.workflow.core.pojo.WfProcess;

/** 
 * 描述：意见便签用Serivice的实现类<br>                                     
 * 作者：顾锡均<br>
 * 创建时间：Feb 24, 2010<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式:同创建时间><br>
 * 修改原因及地方： <修改原因描述><br>
 * 版本：v1.0 <br>           
 * JDK版本：JDK1.5
 */
public class CommentServiceImpl implements CommentService {
	private CommentDao commentDao;

	public void deleteById(String commentId) {
		commentDao.deleteById(commentId);
	}

	public void delete(Comment comment) {
		commentDao.delete(comment);
	}

	public List<Comment> findByInstanceId(String instanceId, String tagId) {
		return commentDao.findByInstanceId(instanceId, tagId);
	}

	public List<Comment> findByInstanceId(String instanceId, String tagId,boolean isDesc) {
		return commentDao.findByInstanceId(instanceId, tagId, isDesc);
	}

	public List<Comment> getCommentByInstanceId(String instanceId){
		return commentDao.getCommentByInstanceId(instanceId);
	}
	public Comment getById(String id) {
		return commentDao.getById(id);
	}

	public void save(Comment comment) {
		commentDao.save(comment);
	}

	public void update(Comment comment) {
		commentDao.update(comment);
	}
	
	public void saveOrUpdate(Comment comment,List<String> attids) {
		if(comment.getId()!=null){
			commentDao.update(comment);
		}else{
			commentDao.save(comment);
			commentDao.updateAttsCommentId(attids, comment.getId());
		}
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public List<PersonalComment> getPersonalComments(String userId){
		return commentDao.getPersonalComments(userId);
	}
	
	public PersonalComment getPCDetail(String cmnt_id){
		return commentDao.getPCDetail(cmnt_id);
	}
	
	public void deletePC(String cmnt_id){
		commentDao.deletePC(cmnt_id);
	}
	
	public void savePersonalComment(String user_id, PersonalComment pc){
		commentDao.savePersonalComment(user_id, pc);
	}

//	public Step getStepById(String stepId) {
////		System.out.println(stepId);
//		return workflowStepDao.findStepById(stepId);
//	}
	
	//add by panh
	public List<Comment> getCommentsByStepId(String stepId,String instanceid,String type) {
		return commentDao.getCommentsByStepId(stepId,instanceid,type);
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
		commentDao.addCommentAtt(att);
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
		commentDao.deleteCommentAttById(id);
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
		commentDao.deleteCommentAtts(ids);
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
		commentDao.deleteCommentAtts(commentId);
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
		if(this.findCommnetAttById(att.getId())!=null){
			commentDao.updateCommnetAtt(att);
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
	public CommentAtt findCommnetAttById(String id){
		return commentDao.findCommnetAttById(id);
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
	public List<CommentAtt> findCommnetAtts(String commentId){
		return commentDao.findCommnetAtts(commentId);
	}
	
	/**
	 * 描述：根据instanceId,userId查询出意见标签的个数<br>
	 *
	 * @param instanceId
	 * @param userId
	 * @return int
	 */
	public int getCommentSizebyUserId(String instanceId, String userId){
		return commentDao.getCommentSizebyUserId(instanceId, userId);
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
		
		return commentDao.findCommentCount(tagId, stepId,userId);
	}
	
	/**
	 * 
	 * 描述：查询某一篇文中的的意见标签里所有附件
	 *
	 * 作者:zhaoj<br>
	 * 创建时间:2012-7-28 下午14:10:27
	 */
	public List<CommentAtt> findCommnetAttByInstanceId(String instanceId) {
		return commentDao.findCommnetAttByInstanceId(instanceId);
	}
	
	/**
	 * 
	 * 描述：查询某一篇文中的的意见标签里单个附件
	 *
	 * 作者:zhaoj<br>
	 * 创建时间:2012-7-28 下午14:10:27
	 */
	public List<CommentAtt> findCommnetAttBycomId(String commentId) {
		return commentDao.findCommnetAttBycomId(commentId);
	}

	public List<Comment> findCommentsByInstanceId(String instanceId) {
		return commentDao.findCommentsByInstanceId(instanceId);
	}

	public List<Comment> findCommentsByElements(String userId, String wfInstanceUid, String wfProcessUid) {
		return commentDao.findCommentsByElements(userId,wfInstanceUid,wfProcessUid);
	}

	@Override
	public WfProcess getProcessById(String currentStepId) {
		return commentDao.getProcessById(currentStepId);
	}
	
	@Override
	public List<PersonalComment> getCommentList(Map<String, String> map,
			Integer pageindex, Integer pagesize) {
		return commentDao.getCommentList(map, pageindex, pagesize);
	}

	@Override
	public int getCommentCount(Map<String, String> map) {
		return commentDao.getCommentCount(map);
	}

	@Override
	public void addPersonComment(PersonalComment personalComment) {
		commentDao.addPersonComment(personalComment);
	}

	@Override
	public PersonalComment getPersonalCommentById(String id) {
		return commentDao.getPersonalCommentById(id);
	}

	@Override
	public void updatePersonalComment(PersonalComment personalComment) {
		commentDao.updatePersonalComment(personalComment);
	}

	@Override
	public void deletePersonalComment(PersonalComment personalComment) {
		commentDao.deletePersonalComment(personalComment);
	}

	@Override
	public PersonalComment getCommentByContentAndSort(String empId,String content,
			String sortIndex) {
		return commentDao.getCommentByContentAndSort(empId,content,sortIndex);
	}
}
