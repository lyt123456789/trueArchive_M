package cn.com.trueway.document.component.docNumberManager.service;

import java.util.List;

import org.apache.velocity.runtime.directive.Define;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberDoc;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberFlow;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberModel;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberType;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhBw;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhFw;
import cn.com.trueway.document.component.docNumberManager.model.vo.DocNumber;
import cn.com.trueway.document.component.docNumberManager.model.vo.DocNumberSmallClass;

/**
 * 
 * 描述：文号管理业务接口<br>
 * 作者：张亚杰<br>
 * 创建时间：2011-12-7 上午10:12:45<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface DocNumberManagerService {

	/**
	 * 
	 * 描述：查询大类文号<br>
	 *
	 * @param webid
	 * @param typeid 主键ID 为空则查询所有的
	 * @param name
	 * @param currentPage
	 * @param numPerPage
	 * @return DTPageBean
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-10 上午10:14:56
	 */
	public List<DocNumberType> getDocNumBigClassList(String sql, Integer currentPage, Integer numPerPage);
	
	
	/**
	 * 
	 * 描述：编辑文号类型(增加更新大小类)<br>
	 *
	 * @param type
	 * @param webid
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 上午09:11:05
	 */
	public String editDocNumType(DocNumberType type,String op);
	
	/**
	 * 
	 * 描述：得到小类文号<br>
	 *
	 * @param pageIndex
	 * @param pageSize
	 * @param webid
	 * @param name
	 * @param parentid
	 * @return DTPageBean
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-10 下午02:41:29
	 */
	public List<DocNumberSmallClass> getDocNumSmallClass(int pageIndex, int pageSize, String name, String parentid,String workflowId);
	
	/**
	 * 
	 * 描述：根据id得到文号类型<br>
	 *
	 * @param typeid
	 * @return DocNumberType
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 上午09:13:46
	 */
	public DocNumberType getSingleDocNumTypeById(String typeid);
	
	/**
	 * 
	 * 描述：得到文号类型的总数<br>
	 *
	 * @param webid
	 * @param isparent
	 * @return int
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 上午10:42:41
	 */
	public int getDocnumberTypeCount(String webid,String isparent);
	
	/**
	 * 
	 * 描述：得到该部门的所有流程<br>
	 *
	 * @param depas
	 * @return
	 * @throws DataAccessException List<Define>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 上午11:09:52
	 */
	public List<Define> getAllDefines(List<String> depas);

	/**
	 * 
	 * 描述：添加文号实例与流程绑定<br>
	 *
	 * @param doctypeid
	 * @param whmodelids
	 * @throws DataAccessException void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午02:12:42
	 */
	public void addDocNumberDocs(String doctypeid,String docNumModels,String allmodelids)throws DataAccessException;
	
	
	public void addOrDelDoc(String whId, String workFlowId);
	
	/**
	 * 
	 * 描述：查询文号实例<br>
	 *
	 * @param webid
	 * @param content
	 * @param currentPage
	 * @param numPerPage
	 * @return DTPageBean
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午06:28:35
	 */
	public List<DocNumberModel> getDocNumModel(String webid, String content, int currentPage, int numPerPage, String lcid);

	/**
	 * 
	 * 描述：删除大类<br>
	 *
	 * @param typeids void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 下午05:01:14
	 */
	public void deletebigWh(String[] typeids);
	/**
	 * 
	 * 描述：得到所有的文号类型<br>
	 *
	 * @param webid
	 * @param isparent
	 * @return List<DocNumberType>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 下午04:29:28
	 */
	public List<DocNumberType> getAllWhTypeByWebid(String webid,String isparent,String parentid);

	/**
	 * 
	 * 描述：删除小类文号<br>
	 *
	 * @param typeids void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 下午04:33:00
	 */
	public void deletesmallWh(String[] typeids);
	/**
	 * 
	 * 描述：新增文号实例<br>
	 *
	 * @param userId
	 * @param allmodelstr
	 * @param webid
	 * @param modelstr void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 下午05:00:17
	 */
	public void addDocNumModel(String userId,String allmodelstr,String webid,String modelstr,String workflowId);
	/**
	 * 
	 * @Title: addWHModel
	 * @Description: 添加文号实例模型
	 * @param whModel
	 * @throws DataAccessException void    返回类型
	 */
	public void addWHModel(DocNumberModel whModel)throws DataAccessException;
	/**
	 * 
	 * @Title: addWHFlow
	 * @Description: 添加文号实例流程绑定数据
	 * @param whFlow
	 * @throws DataAccessException void    返回类型
	 */
	public void addWHFlow(DocNumberFlow whFlow)throws DataAccessException;
	/**
	 * 根据参数查询文号实例
	 * @Title: getWHModelByParam
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userid
	 * @param date
	 * @param webid
	 * @return
	 * @throws DataAccessException WhModel    返回类型
	 */
	public DocNumberModel getWHModelByParam(String userid,String date,String webid)throws DataAccessException;

	/**
	 * 
	 * 描述：TODO 对此方法进行描述<br>
	 *
	 * @param ids void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午05:15:15
	 */
	public void deleteDocNumModels(String ids);
	
	/**
	 * 
	 * 描述：根据ID得到实例模型实体<br>
	 *
	 * @param modelId
	 * @return
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午05:02:16
	 */
	public DocNumberModel getDocNumModelById(String modelId);
	
	/**
	 * 
	 * @Title: updateWhModel
	 * @Description:  更新文号实例
	 * @param whModel
	 * @throws DataAccessException void    返回类型
	 */
	public void updateWhModel(DocNumberModel whModel)throws DataAccessException;
	/**
	 * 修改小类，同时修改MODEL
	 */
	public void updatesmallWh(DocNumberType whType);
	/**
	 * 
	 * @Title: getAllWhModelByWebid
	 * @Description: 根据webid查询出所有的文号实例
	 * @param webid
	 * @return
	 * @throws DataAccessException List<WhModel>    返回类型
	 */
	public List<DocNumberModel> getAllWhModelByWebid(String webid,String workflowId)throws DataAccessException;

	/**
	 * 
	 * @Title: updateWHModelForBangdingDoc
	 * @Description: 文号绑定发文流程
	 * @param doctypeids
	 * @throws DataAccessException void    返回类型
	 */
	public void updateWHModelForBangdingDoc(String doctypeids,String modelid)throws DataAccessException;

	
	/**
	 * 
	 * 描述：TODO 对此方法进行描述<br>
	 *
	 * @param doctypeid
	 * @return
	 * @throws DataAccessException List<DocNumberDoc>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午02:32:21
	 */
	//public List<DocNumberDoc> getWHModelsByDoctypeid(String doctypeid)throws DataAccessException;
	
	/**
	 * 
	 * 描述：得到绑定的实例ID<br>
	 *
	 * @param doctypeid 流程ID
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午02:33:58
	 */
	public String getBindDocNumModelIds(String doctypeid);
	/**
	 * 
	 * @Title: getDocStepByStepId
	 * @Description: 根据公文流程步骤id查询步骤信息
	 * @param stepid
	 * @return
	 * @throws DataAccessException List<Step>    返回类型
	 */
//	public List<Step> getDocStepByStepId(String stepid)throws DataAccessException;
	/**
	 * 
	 * @Title: getWHModelsByModelIds
	 * @Description: 根据文号实例id集合查询文号实例集合
	 * @param modelids
	 * @return
	 * @throws DataAccessException List<WhModel>    返回类型
	 */
	public List<DocNumberModel> getWHModelsByModelIds(List<DocNumberDoc> whdocs)throws DataAccessException;
	/**
	 * 
	 * @Title: getWHModelsByContent
	 * @Description: 根据文号实例内容查询文号实例，用于判断新增文号实例是否重复
	 * @param content
	 * @param webid
	 * @return
	 * @throws DataAccessException List<WhModel>    返回类型
	 */
	public List<DocNumberModel> getWHModelsByContent(String content,String webid,String workflowId)throws DataAccessException;
	
	/**
	 * 
	 * 描述：取得需维护的文号<br>
	 *
	 * @param nh
	 * @param jgdz
	 * @param lwdwlx
	 * @param pageIndex
	 * @param pageSize
	 * @return DTPageBean
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-15 上午09:19:37
	 */
	public DTPageBean findDocNum(DocNumber dn, int pageIndex, int pageSize,String order);
	/**
	 * 
	 * 描述：解析文号模型<br>
	 *
	 * @param model
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午10:36:13
	 */
	public String paserModel(String model);
	/**
	 * 
	 * 描述：根据流程取得文号模型<br>
	 *
	 * @param define
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-16 下午05:53:17
	 */
	public String getModel(String define);
	
	/**
	 * 
	 * 描述：根据流程Id取得文号模型
	 *
	 * @param defineId
	 * @return List<DocNumberModel>
	 *
	 * 作者:ZhangYJ<br>
	 * 创建时间:2012-2-6 上午11:20:06
	 */
	public List<DocNumberModel> findDocNumberModel(String defineId);
	
	
	/**
	 * 
	 * 描述：新增文号<br>
	 *
	 * @param define 流程ID
	 * @param docNum 文号
	 * @param title	标题
	 * @param deparementId 部门ID
	 * @return boolean 是否成功
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-17 上午10:01:32
	 */
	public boolean addDocNum(String define,String docNum,String title,String time,String deparementId,String ngr,String security,String ngrbm);
	
	public List<DocNumberStrategy> getStrategyList();
	
	public DocNumberStrategy getSingnStrategy(String id);
	
	public void addStrategy(String content,String type,String description);
	
	public void updateStrategy(String id,String content,String type,String description);
	
	public void delStrategy(String id);

	public int getDocNumBigClasses(String conditionSql);

	public int getDocNumSmallClasses(String name,String parentid, String workflowId);

	public int getDocNumModelCount(String webid, String content,String workflowId);

	public DocNumberWhFw findDocNumWhFw(String workFlowId,String instanceId);


	public DocNumberWhBw findDocNumWhBw(String workFlowId, String instanceId);
}
