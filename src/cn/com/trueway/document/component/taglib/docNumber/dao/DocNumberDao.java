package cn.com.trueway.document.component.taglib.docNumber.dao;

import java.util.List;

import cn.com.trueway.document.component.docNumberManager.model.po.DocNumOnlyWh;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhBw;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhFw;
import cn.com.trueway.workflow.core.pojo.WfItem;


public interface DocNumberDao {
	public List<Object> getBindWhModelByWebid(String webid,String defid);
	
	public Integer getMaxBWXH(String siteId,String wh, String nd, String lwdwlx, String webId,String workflowId);
	public List<String> getMaxBWXH(String webId);
	
	public Integer getMaxFWXH(String siteId,String wh, String nd, String workflowId);
	public int getIsfwDocNumUsed(String siteId,String xh, String gjdz, String nh,String webId,String workflowId);
	public int getIsbwDocNumUsed(String siteId,String xh, String gjdz, String nh, String lwdwlx,String webId,String workflowId);
	
	public List<DocNumberWhFw> fwAlreadyUsedDocNum(String siteId,String workflowId,String jgdz, String fwnh,String fwxh, int pageIndex, int pageSize);
	public List<DocNumberWhBw> bwDocNumUsed(String siteId,String workflowId,String nh, String bwlx, String lwdwlx,String webid, int pageIndex, int pageSize);
	public String getSingleDocNum(String gjdz);
	public Integer getGroupMaxFWXH(String siteId,String gidzs,String yearNum);
	public List<DocNumberWhFw> fwGroupAlreadyUsedDocNum(String siteId,String jgdzs,String fwnh,String workflowId,int pageIndex,int pageSize);
	
	/**
	 * 
	 * 描述：取得附文号<br>
	 *
	 * @param docNum
	 * @return Integer
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-26 上午09:42:12
	 */
	public Integer getDocNumAttach(String docNum);

	public DocNumberStrategy getStrategy(String numStra);

	public WfItem findItemByWorkFlowId(String defId);

	public void addBw(DocNumberWhBw docNumBw);

	public void addFw(DocNumberWhFw docNumFw);

	public DocNumberWhBw findDocNumBw(String instanceId);

	public DocNumberWhFw findDocNumFw(String instanceId);

	public void updateBw(DocNumberWhBw docNumberWhBw);

	public void updateFw(DocNumberWhFw docNumberWhFw);

	public WfItem findItemById(String itemId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param docNumOnlyWh void
	 * 作者:蒋烽
	 * 创建时间:2017-6-29 上午9:58:05
	 */
	void addDocNumOnlyWh(DocNumOnlyWh docNumOnlyWh);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param workflowId
	 * @param itemId
	 * @return List<DocNumOnlyWh>
	 * 作者:蒋烽
	 * 创建时间:2017-6-29 上午9:58:02
	 */
	List<DocNumOnlyWh> selectDocNumOnlyWh(String workflowId, String itemId, String isUsed);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param workflowId
	 * @param itemId
	 * @return Integer
	 * 作者:蒋烽
	 * 创建时间:2017-6-29 上午10:44:17
	 */
	Integer selectMaxDocNumOnlyWh(String workflowId, String itemId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param docNumOnlyWh void
	 * 作者:蒋烽
	 * 创建时间:2017-6-29 上午11:01:54
	 */
	void updateNumOnlyWh(DocNumOnlyWh docNumOnlyWh);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param workflowId
	 * @param itemId
	 * @param num
	 * @return DocNumOnlyWh
	 * 作者:蒋烽
	 * 创建时间:2017-6-29 下午3:50:19
	 */
	DocNumOnlyWh selectDocNumOnlyWh(String workflowId,String itemId, Integer num, String status);
}
