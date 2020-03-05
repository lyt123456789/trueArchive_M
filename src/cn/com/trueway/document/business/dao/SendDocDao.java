package cn.com.trueway.document.business.dao;

import java.util.List;

import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.business.model.Doc;
import cn.com.trueway.document.business.model.ReceiveProcessShip;


public interface SendDocDao {
	/**
	 * 根据ResultFlag找到对应的Doc对象的docguid
	 * ResultFlag
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String  findDocguidByRF(String ResultFlag);
	/**
	 * 描述：根据docguid查询Docbox;包括正文附件和附加附件<br>
	 *
	 * @param docguid
	 * @return
	 * @throws Exception Doc
	 */
	public Doc findFullDocByDocguid(String docguid);
	/**
	 * 保存Doc对象
	 * 
	 * @param doc
	 * @throws Exception
	 */
	public void saveDoc(Doc doc);
	/**
	 * 描述：根据instanceId找到DOC对象<br>
	 *
	 * @param instanceId
	 * @return
	 * @throws Exception Doc
	 */
	public Doc findDocByInstanceId(String instanceId);
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
	public DTPageBean querySentDocList(int currentPage, int numPerPage, List<String> deps, String keyword, String content,String wh, String status,String userId);
	
	public List<Doc> findDocForSendInProgress(String instanceId);
	
	/**
	 * 
	 * 描述：更新Doc<br>
	 *
	 * @param doc void
	 *
	 * 作者:ZhangYJ<br>
	 * 创建时间:2012-2-7 下午04:02:52
	 */
	public void updateDoc(Doc doc);
	
	/**
	 * 
	 * 描述：发文单<br>
	 *
	 * @param wfuid
	 * @param userId
	 * @param title
	 * @param zh
	 * @param nh
	 * @param xh
	 * @param startTime
	 * @param endTime
	 * @param currentPage
	 * @param numPerPage
	 * @return DTPageBean
	 *
	 * 作者:ZhangYJ<br>
	 * 创建时间:2012-5-31 下午12:11:45
	 */
	public DTPageBean getSendwd(String wfuid, String title, String zh, String nh, String xh, String startTime, String endTime, int currentPage, int numPerPage);
	/**
	 * 
	 * 描述：校验该文是否办理过<br>
	 *
	 * @param wh
	 * @param title
	 * @return String
	 *
	 * 作者:ZhangYJ<br>
	 * 创建时间:2012-6-6 下午06:36:32
	 */
	public String checkbw(String wh, String title,String webId);
	

}
