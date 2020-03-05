/**
 * 文件名称:DemoAction.java
 * 作者:吴新星<br>
 * 创建时间:2011-11-29 下午08:55:45
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.example.action;

import org.apache.commons.validator.GenericValidator;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.displaytag.DTPageBean;
import cn.com.trueway.document.example.service.DemoService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;

/**
 * 描述：Demo
 * 作者：吴新星<br>
 * 创建时间：2011-11-29 下午08:55:45<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class DemoAction extends BaseAction {
	private DemoService demoService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DemoService getDemoService() {
		return demoService;
	}

	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}

	public String show(){
		return "show";
	}
	
	/**
	 * 
	 * 描述：DisplayTag例子
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-16 下午02:36:25
	 */
	public String displayTag(){
		String pageIndexName = new org.displaytag.util.ParamEncoder("element").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		int pageIndex = GenericValidator.isBlankOrNull(getRequest().getParameter(pageIndexName)) ? 0 : (Integer.parseInt(getRequest().getParameter(pageIndexName))-1);
		int pageSize=Integer.valueOf(SystemParamConfigUtil.getParamValueByParam("pageSize"));//
		DTPageBean dtPageBean = demoService.queryUsers(pageIndex,pageSize);
		getRequest().setAttribute("userList", dtPageBean.getDataList());
		getRequest().setAttribute("numPerPage", dtPageBean.getNumPerPage());
		getRequest().setAttribute("pages", dtPageBean.getTotalPages());
		getRequest().setAttribute("totalRows", dtPageBean.getTotalRows());
		return "dislpayTag";
	}
	
	/**
	 * 
	 * 描述：导出Excel例子
	 *
	 * @return String
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-16 下午03:19:31
	 */
	public String exportExcel(){
//		String[] titles = new String[]{"编号","姓名","性别"}; 
//		DTPageBean dtPageBean = demoService.queryUsers(0,200);
//		List<User> userList= (List<User>)dtPageBean.getDataList();
//		List<String[]> dataSource=new ArrayList<String[]>();
//		for(User u:userList){
//			String[] s= new String[]{u.getUserId(),u.getUserName(),u.getSex()};
//			dataSource.add(s);
//		}
//		ExcelExport e = ExcelExport.getExcelExportInstance("用户表",getResponse());
//		e.setTitles(titles);
//		e.setDataSource(dataSource);
//		e.setColWidth(1, 70);
//		e.export();
		return null;
	}
}
