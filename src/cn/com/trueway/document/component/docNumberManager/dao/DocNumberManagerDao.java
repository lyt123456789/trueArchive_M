package cn.com.trueway.document.component.docNumberManager.dao;

import java.util.List;

import org.apache.velocity.runtime.directive.Define;
import org.springframework.dao.DataAccessException;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.Step;
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
 * 描述：文号管理数据处理<br>
 * 作者：张亚杰<br>
 * 创建时间：2011-12-9 上午10:13:14<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public interface DocNumberManagerDao {
	/**
	 * 
	 * 描述：大类文号查询<br>
	 *
	 * @param webid
	 * @param parentid
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
	 * 描述：得到文号类型的总数<br>
	 *
	 * @param webid
	 * @param isparent
	 * @return int
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 上午10:41:21
	 */
	public int getDocnumberTypeCount(String webid,String isparent);
	
	/**
	 * 
	 * 描述：删除对应的文号与流程绑定记录<br>
	 *
	 * @param defineid
	 * @param modelId void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午04:03:27
	 */
	public void deleteDocNumDoc(String defineid,String modelId);
	/**
	 * 
	 * 描述：根据ID得到文号类别<br>
	 *
	 * @param id
	 * @return DocNumberType
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午04:05:58
	 */
	public DocNumberType getSingleDocNumberType(String id);
	
	/**
	 * 
	 * 描述：增加类别<br>
	 *
	 * @param type void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午04:04:55
	 */
	public void addDocNumber(DocNumberType type);
	/**
	 * 
	 * 描述：修改类别<br>
	 *
	 * @param type void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午04:04:58
	 */
	public void updateDocNumber(DocNumberType type);
	
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
	 * 创建时间:2011-12-13 下午04:44:44
	 */
	public List<DocNumberModel> getDocNumModel(String webid, String content, int currentPage, int numPerPage, String lcid);

	/**
	 * 
	 * 描述：删除大类文号同时会删除相关子类<br>
	 *
	 * @param typeids void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 下午04:27:01
	 */
	public void deletebigWh(String[] typeids);
	/**
	 * s
	 * 描述：得到所有的文号类型<br>
	 *
	 * @param webid
	 * @param isparent
	 * @return List<DocNumberType>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 下午04:29:52
	 */
	public List<DocNumberType> getAllWhTypeByWebid(String webid,String isparent,String parentid);
	
	/**
	 * 
	 * 描述：删除小类文号<br>
	 *
	 * @param typeids void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 下午04:27:40
	 */
	public void deletesmallWh(String[] typeids);
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
	 * 
	 * @Title: getWHModelByParam
	 * @Description: 根据参数查询文号实例
	 * @param userid
	 * @param date
	 * @param webid
	 * @return
	 * @throws DataAccessException WhModel    返回类型
	 */
	public DocNumberModel getWHModelByParam(String userid,String date,String webid)throws DataAccessException;
	/**
	 * 
	 * @Title: getAllWHModel
	 * @Description: 查询所有文号实例(分页、条件)
	 * @param pageIndex
	 * @param pagesize
	 * @param webid
	 * @param content
	 * @return
	 * @throws DataAccessException List<WhModel>    返回类型
	 */
	public Object[] getAllWHModel(int pageIndex,int pagesize,String webid,String content)throws DataAccessException;
	/**
	 * 
	 * @Title: deleteManyWHModels
	 * @Description: 删除多个文号实例
	 * @param ids
	 * @throws DataAccessException void    返回类型
	 */
	public void deleteManyWHModels(String ids)throws DataAccessException;
	/**
	 * 修改小类，同时修改MODEL
	 */
	public void updatesmallWh(DocNumberType whType);
	
	/**
	 * 
	 * 描述：根据ID得到实例模型实体<br>
	 *
	 * @param modelId
	 * @return
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-13 下午05:03:20
	 */
	public DocNumberModel getDocNumModelById(String modelId);
	
	/**
	 * 
	 * @Title: updateWhModel
	 * @Description: 更新文号实例
	 * @param whModel
	 * @throws DataAccessException void    返回类型
	 */
	public void updateWhModel(DocNumberModel whModel)throws DataAccessException;
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
	 * 描述：查询本部门所有的流程<br>
	 *
	 * @param depas
	 * @return
	 * @throws DataAccessException List<Define>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-12 上午11:14:21
	 */
	public List<Define> getAllDefines(List<String> depas)throws DataAccessException;
	/**
	 * 
	 * @Title: updateWHModelForBangdingDoc
	 * @Description: 文号绑定办文流程
	 * @param doctypeids
	 * @throws DataAccessException void    返回类型
	 */
	public void updateWHModelForBangdingDoc(String doctypeids,String modelid)throws DataAccessException;
	
	/**
	 * 
	 * 描述：添加文号绑定发文流程信息<br>
	 *
	 * @param doc void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 下午04:42:44
	 */
	public void addDocNumberDoc(DocNumberDoc doc);
	
	/**
	 * 
	 * 描述：根据发文流程id查询相应绑定的文号<br>
	 *
	 * @param doctypeid
	 * @return
	 * @throws DataAccessException List<DocNumberDoc>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 下午04:36:46
	 */
	public List<DocNumberDoc> getWHModelsByDoctypeid(String doctypeid)throws DataAccessException;
	/**
	 * 
	 * @Title: getDocStepByStepId
	 * @Description: 根据公文流程步骤id查询步骤信息
	 * @param stepid
	 * @return
	 * @throws DataAccessException List<Step>    返回类型
	 */
	public List<Step> getDocStepByStepId(String stepid)throws DataAccessException;
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
	 * 描述：查询子类<br>
	 *
	 * @param webid
	 * @param currentPage
	 * @param numPerPage
	 * @param name
	 * @param parentId
	 * @return DTPageBean
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-14 下午04:40:52
	 */
	public List<DocNumberSmallClass> getDocNumSmallClass(int currentPage, int numPerPage,String name, String parentId,String workflowId);
	/**
	 * 
	 * 描述：办文未使用的文号<br>
	 *
	 * @param nh
	 * @param bwlx
	 * @param lwdwlx
	 * @param bwxh
	 * @param order
	 * @param pageIndex
	 * @param pageSize
	 * @return DTPageBean
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-16 下午05:55:17
	 */
	public DTPageBean bwDocNumUsed(String nh, String bwlx, String lwdwlx,String bwxh,String order, int pageIndex, int pageSize);
	/**
	 * 
	 * 描述：发文未使用的文号<br>
	 *
	 * @param jgdz
	 * @param fwnh
	 * @param fwxh
	 * @param order
	 * @param pageIndex
	 * @param pageSize
	 * @return DTPageBean
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-16 下午05:55:04
	 */
	public DTPageBean fwAlreadyUsedDocNum(String jgdz, String fwnh,String fwxh,String order, int pageIndex, int pageSize);
	
	public Define getDefineById(String define);
	/**
	 * 
	 * 描述：新增办文文号<br>
	 *
	 * @param dn void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-16 下午09:32:56
	 */
	public void addDoDocNumber(DocNumber dn);
	/**
	 * 
	 * 描述：新增办文文号<br>
	 *
	 * @param dn void
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-16 下午09:32:56
	 */
	public void addSendDocNumber(DocNumber dn);
	
	public List<DocNumberStrategy> getStrategyList();
	
	public DocNumberStrategy getSingnStrategy(String id);
	
	public void updateStrategy(DocNumberStrategy doc);
	
	public void addStrategy(DocNumberStrategy doc);
	
	public void delStrategy(String id);

	public int getDocNumBigClasses(String conditionSql);

	public int getDocNumBigClasses(String name,String parentid, String workflowId);

	public int getDocNumModelCount(String webid, String content,String workflowId);

	public DocNumberWhFw findDocNumWhFw(String workFlowId,String instanceId);

	public DocNumberWhBw findDocNumWhBw(String workFlowId, String instanceId);
}
