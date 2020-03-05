package cn.com.trueway.workflow.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.WfDictionary;
import cn.com.trueway.workflow.core.service.DictionaryService;

/**
 * 
 * 描述：字典表
 * 作者：蔡亚军
 * 创建时间：2016-8-19 下午4:33:59
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class DictionaryAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private DictionaryService dictionaryService;
	
	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
	private WfDictionary dictionary;
	
	public WfDictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(WfDictionary dictionary) {
		this.dictionary = dictionary;
	}

	public String getDictionaryList(){
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		
		if(dictionary == null){
			dictionary = new WfDictionary();
		}
		dictionary.setLcid(lcid);
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = dictionaryService.getDictionaryCountForPage(column, value, dictionary);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<WfDictionary> list = dictionaryService.getDictionaryListForPage(column, value, dictionary, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("lcid", lcid);
		getRequest().setAttribute("list", list);
		return "dictionaryList";
	}
	
	public String toAddJsp(){
		return "toAddJsp";
	}
	
	public String add(){
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String b_public = getRequest().getParameter("b_public");
		if("1".equals(b_public)){
			dictionary.setLcid(null);
		}else{
			dictionary.setLcid(lcid);
		}
		List<WfDictionary> list = new ArrayList<WfDictionary>();
		list.add(dictionary);
		dictionaryService.addDictionary(list);
		return getDictionaryList();
	}
	
	public String del(){
		String id = getRequest().getParameter("ids");
		String[] ids = id.split(",");
		for(String strId: ids){
			dictionary = dictionaryService.getDictionaryById(strId);
			dictionaryService.delDictionary(dictionary);
		}
		return getDictionaryList();
	}
	
	public String toEditJsp(){
		String id = getRequest().getParameter("id");
		dictionary = dictionaryService.getDictionaryById(id);
		String[] key = dictionary.getVc_key().split(",");
		String[] value = dictionary.getVc_value().split(",");
		List<Map<String, String>> outList = new ArrayList<Map<String,String>>();
		for(int i = 0 ; i < key.length; i++){
			Map<String,String> map = new HashMap<String, String>();
			map.put("key", key[i]);
			map.put("value", value[i]);
			outList.add(map);
		}
		getRequest().setAttribute("outList", outList);
		getRequest().setAttribute("default", dictionary.getVc_default());
		return "toEditJsp";
	}
	
	public String edit(){
		List<WfDictionary> list = new ArrayList<WfDictionary>();
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String b_public = getRequest().getParameter("b_public");
		if("1".equals(b_public)){
			dictionary.setLcid(null);
		}else{
			dictionary.setLcid(lcid);
		}
		list.add(dictionary);
		dictionaryService.addDictionary(list);
		return getDictionaryList();
	}
	
	public void checkDictionary(){
		String vc_name = this.getRequest().getParameter("vc_name");
		String isExist="no";
		int num = dictionaryService.isExistDictionary(vc_name);
		if(num > 0){
			isExist="yes";
		}
		PrintWriter out =null;
		try {
			out = this.getResponse().getWriter() ;
			out.write(isExist);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			out.flush();
			out.close();
		}
	}
	
}
