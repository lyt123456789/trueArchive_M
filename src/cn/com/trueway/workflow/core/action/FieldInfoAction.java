package cn.com.trueway.workflow.core.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.service.FieldInfoService;

public class FieldInfoAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FieldInfoService fieldInfoService;
	
	private WfFieldInfo field;

	public FieldInfoService getFieldInfoService() {
		return fieldInfoService;
	}

	public void setFieldInfoService(FieldInfoService fieldInfoService) {
		this.fieldInfoService = fieldInfoService;
	}
	
	public WfFieldInfo getField() {
		return field;
	}

	public void setField(WfFieldInfo field) {
		this.field = field;
	}

	public String getFieldList(){
		
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		
		if(field == null){
			field = new WfFieldInfo();
		}
		
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count=fieldInfoService.getFieldCountForPage(column, value, field);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<WfFieldInfo> list=fieldInfoService.getFieldListForPage(column, value, field, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		return "fieldList";
	}
	
	public String toAddJsp(){
		return "toAddJsp";
	}
	
	public String add(){
		List<WfFieldInfo> list = new ArrayList<WfFieldInfo>();
		list.add(field);
		fieldInfoService.addField(list);
		return getFieldList();
	}
	
	public String del(){
		String ids = getRequest().getParameter("ids");
		String[] id = ids.split(",");
		for(String strId : id){
			field = fieldInfoService.getFieldById(strId);
			fieldInfoService.delField(field);
		}
		return getFieldList();
	}
	
	public String toEditJsp(){
		String id = getRequest().getParameter("id");
		field = fieldInfoService.getFieldById(id);
		getRequest().setAttribute("field", field);
		return "toEditJsp";
	}
	
	public String edit(){
		fieldInfoService.updateField(field);
		return getFieldList();
	}
	
	
	public void checkFieldName(){
		String vc_fieldname = this.getRequest().getParameter("vc_fieldname").toUpperCase();
		String isExist="no";
		int num = fieldInfoService.isExistField(vc_fieldname);
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
	
	
	/**
	 * 
	 * 描述：根据tableId获取表字段
	 * 作者:蔡亚军
	 * 创建时间:2016-2-3 下午2:34:24
	 */
	public void getFieldById(){
		String id = getRequest().getParameter("id");
		List<WfFieldInfo> list = fieldInfoService.getAllFieldByTableId(id);
		String result = "";
		for(WfFieldInfo field : list){
			result += field.getId()+","+field.getVc_fieldname()+","+field.getVc_name()+";";
		}
		if(result.length() > 0){
			result = result.substring(0, result.length() - 1);
		}
		PrintWriter out =null;
		try {
			out = this.getResponse().getWriter() ;
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			out.flush();
			out.close();
		}
	}
}
