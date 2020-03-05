package cn.com.trueway.document.component.taglib.docNumber.service;

import java.util.List;

import cn.com.trueway.document.component.docNumberManager.model.po.DocNumOnlyWh;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhBw;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhFw;
import cn.com.trueway.workflow.core.pojo.WfItem;

public interface DocNumberService {
	/**
	 * 
	 * 描述：得到该部门绑定流程的文号实例<br>
	 *
	 * @param webid
	 * @param defid
	 * @return List<Object>
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午10:28:11
	 */
	public List<String[]> getBindWhModelByWebid(String webid,String defid,String value);
	/**
	 * 
	 * 描述：解析文号实例<br>
	 *
	 * @param str
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午10:28:16
	 */
	public String paserModel(String str);
	/**
	 * 
	 * 描述：根据条件得到序号<br>
	 *
	 * @param defId
	 * @param gjdz
	 * @param yearNum
	 * @param lwdwlx
	 * @param webId 
	 * @return int
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午10:29:27
	 */
	public int getDocNum(String defId, String gjdz, String yearNum, String lwdwlx, String webId,String xhModel,String isChildWf,String itemId);
	/**
	 * 
	 * 描述：判断该文号是否使用<br>
	 *
	 * @param defId
	 * @param xh
	 * @param gjdz
	 * @param nh
	 * @param lwdwlx
	 * @return boolean (true 未使用 false 已使用)
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午10:29:31
	 */
	public boolean getIsalreadyUsedDocNumber(String defId, String xh, String gjdz, String nh, String lwdwlx, String isChildWf, String itemId,String webid);
	/**
	 * 
	 * 描述：取得未使用的文号<br>
	 *
	 * @param defId
	 * @param nh
	 * @param jgdz
	 * @param lwdwlx
	 * @param fwxh
	 * @param webid 
	 * @param pageIndex
	 * @param pageSize
	 * @return Object[]
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-8 上午10:29:36
	 */
	public Object[] findDocNumberUnused(String defId,String nh,String jgdz, String lwdwlx, String fwxh, String webid, int pageIndex, int pageSize,String numModel, String isChildWf, String itemId);
	/**
	 * 
	 * 描述：取附号<br>
	 *
	 * @param docNum
	 * @return int
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-26 上午09:54:26
	 */
	public int findDocNumAttach(String docNum);
	
	public DocNumberStrategy findStrategy(String numStra);
	
	public WfItem findItemByWorkFlowId(String workFlowId);
	
	public void addFw(DocNumberWhFw docNumFw);
	
	public void addBw(DocNumberWhBw docNumBw);
	
	public DocNumberWhFw findDocNumFw(String instanceId);
	
	public DocNumberWhBw findDocNumBw(String instanceId);
	
	public void updateFw(DocNumberWhFw docNumberWhFw);
	
	public void updateBw(DocNumberWhBw docNumberWhBw);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param docNumOnlyWh void
	 * 作者:蒋烽
	 * 创建时间:2017-6-29 上午9:58:32
	 */
	void addDocNumOnlyWh(DocNumOnlyWh docNumOnlyWh);
	
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param workflowId
	 * @param itemId
	 * @return List<DocNumOnlyWh>
	 * 作者:蒋烽
	 * 创建时间:2017-6-29 上午9:58:24
	 */
	List<DocNumOnlyWh> getDocNumOnlyWh(String workflowId, String itemId, String isUsed);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param workflowId
	 * @param itemId
	 * @return Integer
	 * 作者:蒋烽
	 * 创建时间:2017-6-29 上午10:48:54
	 */
	public Integer getMaxDocNumOnlyWh(String workflowId, String itemId);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param docNumOnlyWh void
	 * 作者:蒋烽
	 * 创建时间:2017-6-29 上午11:02:38
	 */
	void editNumOnlyWh(DocNumOnlyWh docNumOnlyWh);
	
	/**
	 * 描述：TODO 对此方法进行描述
	 * @param workflowId
	 * @param itemId
	 * @param num
	 * @return DocNumOnlyWh
	 * 作者:蒋烽
	 * 创建时间:2017-6-29 下午3:52:56
	 */
	DocNumOnlyWh getDocNumOnlyWh(String workflowId,String itemId, Integer num, String status);

	public String paserModelByMobile(String str);
}
