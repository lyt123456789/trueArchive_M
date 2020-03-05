/**
 * 
 */
package cn.com.trueway.document.component.taglib.comment.service;

import java.util.List;
import java.util.Map;

import cn.com.trueway.document.component.taglib.comment.model.po.Comment;
import cn.com.trueway.document.component.taglib.comment.model.po.CommentAtt;
import cn.com.trueway.document.component.taglib.comment.model.po.PersonalComment;
import cn.com.trueway.workflow.core.pojo.WfProcess;

/** 
 * 描述：意见便签用Service<br>                                     
 * 作者：顾锡均<br>
 * 创建时间：Feb 24, 2010<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式:同创建时间><br>
 * 修改原因及地方： <修改原因描述><br>
 * 版本：v1.0 <br>           
 * JDK版本：JDK1.5
 */
public interface CommentService {
	public Comment getById(String id);
	public List<Comment> findByInstanceId(String instanceId, String tagId);
	public List<Comment> findByInstanceId(String instanceId, String tagId,boolean isDesc);
	public List<Comment> getCommentByInstanceId(String instanceId);
	public void save(Comment comment);
	public void update(Comment comment);
	public void delete(Comment comment);
	public void deleteById(String commentId);
	public void saveOrUpdate(Comment comment,List<String> attids);
	public List<PersonalComment> getPersonalComments(String userId);
	public PersonalComment getPCDetail(String cmnt_id);
	public void deletePC(String cmnt_id);
	public void savePersonalComment(String user_id, PersonalComment pc);
//	public Step getStepById(String stepId);
	public List<Comment> getCommentsByStepId(String stepId,String instanceid,String type);
	
	/**
	 * 
	 * 描述：新增意见附件
	 *
	 * @param atts void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:14:21
	 */
	public void addCommentAtt(CommentAtt att);
	
	/**
	 * 
	 * 描述：根据Id删除
	 *
	 * @param id void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:18:53
	 */
	public void deleteCommentAttById(String id);
	

	/**
	 * 
	 * 描述：根据Id删除一些附件
	 *
	 * @param ids void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-5 下午10:15:40
	 */
	public void deleteCommentAtts(List<String> ids);
	
	/**
	 * 
	 * 描述：删除意见的所有附件
	 *
	 * @param commentId void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:20:44
	 */
	public void deleteCommentAtts(String commentId);
	
	/**
	 * 
	 * 描述：更新意见附件
	 *
	 * @param att void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2012-1-4 下午09:19:08
	 */
	public void updateCommnetAtt(CommentAtt att);
	
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
	public CommentAtt findCommnetAttById(String id);
	
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
	public List<CommentAtt> findCommnetAtts(String commentId);
	/**
	 * 描述：根据instanceId,userId查询出意见标签的个数<br>
	 *
	 * @param instanceId
	 * @param userId
	 * @return int
	 */
	public int getCommentSizebyUserId(String instanceId, String userId);
	
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
	public int findCommentCount(String tagId, String stepId, String userId);
	
	/**
	 * 
	 * 描述：查询某一篇文中的的意见标签里所有附件
	 *
	 * 作者:zhaoj<br>
	 * 创建时间:2012-7-28 下午14:10:27
	 */
	public List<CommentAtt> findCommnetAttByInstanceId(String instanceId);
	
	public List<Comment> findCommentsByInstanceId(String instanceId);
	
	public List<Comment> findCommentsByElements(String userId, String wfInstanceUid, String wfProcessUid);
	
	public WfProcess getProcessById(String currentStepId);
	
	public List<PersonalComment> getCommentList(Map<String,String> map, Integer pageindex, Integer pagesize );
	
	public int getCommentCount(Map<String,String> map);
	
	public void addPersonComment(PersonalComment personalComment);
	
	public PersonalComment getPersonalCommentById(String id);

	public void updatePersonalComment(PersonalComment personalComment);
	
	public void deletePersonalComment(PersonalComment personalComment);
	
	/**
	 * 方法描述: [根据意见内容及排序号]<br/>
	 * 初始作者: lihanqi<br/> 
	 * 创建日期: 2018-8-31-上午10:54:31<br/> 
	 * 修改记录：<br/>
	 * 修改作者         日期         修改内容<br/>
	 * @param empId
	 * @param content
	 * @param sortIndex
	 * @return
	 * PersonalComment
	 */
	public PersonalComment getCommentByContentAndSort(String empId,String content, String sortIndex);
}
