package cn.com.trueway.workflow.core.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.hibernate.Hibernate;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.FileUtils;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.MyUtils;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.WfLabel;
import cn.com.trueway.workflow.core.pojo.WfNode;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.pojo.WfTemplate;
import cn.com.trueway.workflow.core.service.FieldInfoService;
import cn.com.trueway.workflow.core.service.FlowService;
import cn.com.trueway.workflow.core.service.LabelService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.TemplateService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;
import cn.com.trueway.workflow.set.pojo.FormTagMapColumn;
import cn.com.trueway.workflow.set.pojo.ZwkjForm;
import cn.com.trueway.workflow.set.service.ZwkjFormService;
/**
 * 
 * 描述：正文模板管理(包括: 正文模板、红头模板)
 * 作者：蔡亚军
 * 创建时间：2016-3-21 下午03:32:24
 * 修改人：<修改人中文名或拼音缩写>
 * 修改时间：<修改日期，格式：同创建时间>
 * 修改原因及地方：<修改原因描述>
 * 版本：v1.0
 * JDK版本：JDK1.6
 */
public class TemplateAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private TableInfoService tableInfoService;
	
	private FieldInfoService fieldInfoService;
	
	private TemplateService templateService;
	
	private LabelService labelService;
	
	private WorkflowBasicFlowService workflowBasicFlowService;
	
	private WfTemplate template;
	
	private File vc_file;
	
	private ZwkjFormService zwkjFormService;
	
	private String bkstr;

	private FlowService flowService ; 
	
	public FlowService getFlowService() {
		return flowService;
	}
	public void setFlowService(FlowService flowService) {
		this.flowService = flowService;
	}
	
	public String getBkstr() {
		return bkstr;
	}

	public void setBkstr(String bkstr) {
		this.bkstr = bkstr;
	}

	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}

	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}
	
	public FieldInfoService getFieldInfoService() {
		return fieldInfoService;
	}

	public void setFieldInfoService(FieldInfoService fieldInfoService) {
		this.fieldInfoService = fieldInfoService;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public WfTemplate getTemplate() {
		return template;
	}

	public void setTemplate(WfTemplate template) {
		this.template = template;
	}
	
	public LabelService getLabelService() {
		return labelService;
	}

	public void setLabelService(LabelService labelService) {
		this.labelService = labelService;
	}

	public File getVc_file() {
		return vc_file;
	}

	public void setVc_file(File vcFile) {
		vc_file = vcFile;
	}

	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public String getTemplateList(){
		String column = getRequest().getParameter("column");
		String value = getRequest().getParameter("value");
		String isRedTape = getRequest().getParameter("isRedTape");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id); 
		
		if(template == null){
			template = new WfTemplate();
		}
		
		template.setLcid(lcid);
		if(CommonUtil.stringNotNULL(isRedTape)){
			template.setIsRedTape(isRedTape);
		}
		
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = templateService.getTemplateCountForPage(column, value, template);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<WfTemplate> list = templateService.getTemplateListForPage(column, value, template, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("isRedTape", isRedTape);
		if(CommonUtil.stringNotNULL(isRedTape)){
			return "templateRedList";
		}else{
			return "templateList";
		}
		
	}
	
	public String toAddJsp(){
		String isRedTape = getRequest().getParameter("isRedTape");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		List<WfTableInfo> tableList = tableInfoService.getTableByLcid(lcid);
		getRequest().setAttribute("lcid", lcid);
		getRequest().setAttribute("tableList", tableList);
		
		//查询表单关联了几个节点
//		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
//		List<WfNode> list = workflowBasicFlowService.getNodesByPid(lcid);
//		getRequest().setAttribute("nodeList", list);
		
		//获取绑定流程的所有表单
		String hql="from ZwkjForm z where z.workflowId='"+lcid+"'";
		List<ZwkjForm> formList=zwkjFormService.getFormListByHql(hql, null, null);
		
		List<FormTagMapColumn> resultList = new ArrayList<FormTagMapColumn>();
		List<FormTagMapColumn> attList = new ArrayList<FormTagMapColumn>();
		List<FormTagMapColumn> listTagList = new ArrayList<FormTagMapColumn>();
		//获取绑定流程的所有表单的所有意见标签
		if (formList!=null&&formList.size()>0) {
			for (int i = 0; i < formList.size(); i++) {
				List<FormTagMapColumn> list=zwkjFormService.getFormTagMapColumnByFormId(formList.get(i).getId());
				//过滤意见标签
				for(FormTagMapColumn mapColumn : list){
					if("comment".equals(mapColumn.getFormtagtype())){
						resultList.add(mapColumn);
					}
					if("attachment".equals(mapColumn.getFormtagtype())){
						attList.add(mapColumn);
					}
					if(CommonUtil.stringNotNULL(mapColumn.getListId())){
						listTagList.add(mapColumn);
					}
				}
			}
		}
		List<WfTemplate> relationTemplate = templateService.getTemplateBySql(" and e.position='1' ");
		getRequest().setAttribute("relationTemplate", relationTemplate);
		getRequest().setAttribute("commentList", resultList);
		getRequest().setAttribute("attachmentList", attList);
		getRequest().setAttribute("listTagList", listTagList);
		getRequest().setAttribute("isRedTape", isRedTape);
		if(CommonUtil.stringNotNULL(isRedTape)){
			return "toAddRedJsp";
		}else{
			return "toAddJsp";
		}
	}
	
	/**
	 * 到选择页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-3-23 上午11:43:38
	 */
	public String toRefJsp() {
		String isRedTape = getRequest().getParameter("isRedTape");
		String lcid = (String) getRequest().getSession().getAttribute(
				MyConstants.workflow_session_id);
		List<WfTemplate> list = templateService.getAllTemplateListNotLc(lcid,isRedTape);
		getRequest().setAttribute("list", list); 
		getRequest().setAttribute("isRedTape", isRedTape);
		return "toRefJsp";
	}
	
	/**
	 * 添加选择的模板
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-3-23 上午11:56:56
	 */
	public String ref() {
		String isRedTape = getRequest().getParameter("isRedTape");
		String lcid = (String) getRequest().getSession().getAttribute(
				MyConstants.workflow_session_id);
		String id = getRequest().getParameter("id");
		template = templateService.getTemplateById(id);
		String ref = "";
		// 以,将id隔开，查询时like '%,ID,%'
		if (CommonUtil.stringNotNULL(template.getReflcId())) {
			ref = template.getReflcId() + lcid + ",";
		} else {
			ref = "," + lcid + ",";
		}
		if (CommonUtil.stringNotNULL(isRedTape)){
			template.setIsRedTape(isRedTape);
		}
		template.setC_createdate(new Timestamp(System.currentTimeMillis()));
		template.setReflcId(ref);
		templateService.addTemplate(template);
		getRequest().setAttribute("isRedTape", isRedTape);
		return null;
	}
	
	/**
	 * 模板文件页面
	 * @return
	 */
	public String toDocJsp(){
		String vc_filename = getRequest().getParameter("name");
		String id = getRequest().getParameter("id");
		String result = "";
		if(vc_filename != null && !"".equals(vc_filename)){
			vc_filename = vc_filename.substring(vc_filename.lastIndexOf("/") + 1);
			String dstPath = getSpringContext().getServletContext().getRealPath("") + "/tempfile/" + vc_filename;
			File file = new File(dstPath);
			if(!file.exists()){
				if(StringUtils.isNotBlank(id)){
					WfTemplate wfTemplate = templateService.getTemplateById(id);
					if(null != wfTemplate){
						FileUtils.byteArrayToFile(wfTemplate.getAttflow(), dstPath);
					}
				}
			}
			result = "toEditDocJsp";
		}else{
			vc_filename = System.currentTimeMillis()+".doc";
			result = "toDocJsp";
		}
		getRequest().setAttribute("name", vc_filename);
		return result;
	}
	
	public String add() throws FileNotFoundException, IOException{
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String vc_path = getRequest().getParameter("vc_path");
		template.setVc_path(vc_path);
		template.setC_createdate(new Timestamp(System.currentTimeMillis()));
		template.setLcid(lcid);
		if(StringUtils.isNotBlank(vc_path)){
			String dstPath = getSpringContext().getServletContext().getRealPath("") + "/tempfile/" + vc_path;
			File file = new File(dstPath);
			if(file.exists()){
				template.setAttflow(Hibernate.createBlob(new FileInputStream(file)));
			}
		}
		template = templateService.addTemplate(template);		
		//新增字段对应关系
		String[] vc_fields = getRequest().getParameterValues("vc_fieldname");
		String[] vc_tables = getRequest().getParameterValues("vc_tablename");
		String[] vc_labels = getRequest().getParameterValues("vc_label");
		String[] vc_comments = getRequest().getParameterValues("vc_comment");
		
		String[] vc_types = getRequest().getParameterValues("vc_type");
		String[] vc_atts = getRequest().getParameterValues("vc_att");
		String[] vc_lists = getRequest().getParameterValues("vc_list");
		
		WfLabel label = null;
		for(int i = 0; vc_labels != null && i < vc_labels.length; i++){
			label = new WfLabel();
			label.setVc_label(vc_labels[i]);
			label.setVc_templateId(template.getId());
			if(vc_fields[i] != null&&!vc_fields[i].equals("") && vc_fields[i].split(",").length > 1){
				label.setVc_fieldid(vc_fields[i].split(",")[0]);
				label.setVc_fieldname(vc_fields[i].split(",")[1]);
			}
			if(vc_tables[i] != null&&!vc_tables[i].equals("") && vc_tables[i].split(",").length > 1){
				label.setVc_tableid(vc_tables[i].split(",")[0]);
				label.setVc_tablename(vc_tables[i].split(",")[1]);
			}
			label.setVc_commentId(vc_comments[i]);
			label.setVc_type(vc_types[i]);
			label.setVc_att(vc_atts[i]);
			label.setVc_list(vc_lists[i]);
			labelService.addLabel(label);
			
		}
		return getTemplateList();
	}
	
	/**
	 * 新增红头模板
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-3-23 上午11:17:29
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String addRed() throws FileNotFoundException, IOException{
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
		String isRedTape = getRequest().getParameter("isRedTape");
		String relationId = getRequest().getParameter("relationId");
		String vc_path = getRequest().getParameter("vc_path");
		template.setVc_path(vc_path);
		template.setC_createdate(new Timestamp(System.currentTimeMillis()));
		template.setLcid(lcid);
		template.setIsRedTape(isRedTape);
		template.setRelationId(relationId);
		if(StringUtils.isNotBlank(vc_path)){
			String dstPath = getSpringContext().getServletContext().getRealPath("") + "/tempfile/" + vc_path;
			File file = new File(dstPath);
			if(file.exists()){
				template.setAttflow(Hibernate.createBlob(new FileInputStream(file)));
			}
		}
		template = templateService.addTemplate(template);		
		//新增字段对应关系
		String[] vc_fields = getRequest().getParameterValues("vc_fieldname");
		String[] vc_tables = getRequest().getParameterValues("vc_tablename");
		String[] vc_labels = getRequest().getParameterValues("vc_label");
		String[] vc_comments = getRequest().getParameterValues("vc_comment");
		
		String[] vc_types = getRequest().getParameterValues("vc_type");
		String[] vc_atts = getRequest().getParameterValues("vc_att");
		String[] vc_lists = getRequest().getParameterValues("vc_list");
		
		WfLabel label = null;
		for(int i = 0; vc_labels != null && i < vc_labels.length; i++){
			label = new WfLabel();
			label.setVc_label(vc_labels[i]);
			label.setVc_templateId(template.getId());
			if(vc_fields[i] != null&&!vc_fields[i].equals("") && vc_fields[i].split(",").length > 1){
				label.setVc_fieldid(vc_fields[i].split(",")[0]);
				label.setVc_fieldname(vc_fields[i].split(",")[1]);
			}
			if(vc_tables[i] != null&&!vc_tables[i].equals("") && vc_tables[i].split(",").length > 1){
				label.setVc_tableid(vc_tables[i].split(",")[0]);
				label.setVc_tablename(vc_tables[i].split(",")[1]);
			}
			label.setVc_commentId(vc_comments[i]);
			label.setVc_type(vc_types[i]);
			label.setVc_att(vc_atts[i]);
			label.setVc_list(vc_lists[i]);
			labelService.addLabel(label);
			
		}
		getRequest().setAttribute("isRedTape", isRedTape);
		return getTemplateList();
	}
	
	public String toEditJsp(){
		String id = getRequest().getParameter("id");
		template = templateService.getTemplateById(id);
		List<WfLabel> labelList = labelService.getLabelByTemplateId(template.getId());
		List<WfTableInfo> tableList = tableInfoService.getTableByLcid(template.getLcid());
		
		getRequest().setAttribute("template", template);
		getRequest().setAttribute("labelList", labelList);
		getRequest().setAttribute("tableList", tableList);
		
		//查询表单关联了几个节点
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
//		List<WfNode> list = workflowBasicFlowService.getNodesByPid(lcid);
//		getRequest().setAttribute("nodeList", list);
		
		//获取绑定流程的所有表单
		String hql="from ZwkjForm z where z.workflowId='"+lcid+"'";
		List<ZwkjForm> formList=zwkjFormService.getFormListByHql(hql, null, null);
		
		List<FormTagMapColumn> resultList = new ArrayList<FormTagMapColumn>();
		List<FormTagMapColumn> attList = new ArrayList<FormTagMapColumn>();
		List<FormTagMapColumn> listTagList = new ArrayList<FormTagMapColumn>();
		//获取绑定流程的所有表单的所有意见标签
		if (formList!=null&&formList.size()>0) {
			for (int i = 0; i < formList.size(); i++) {
				List<FormTagMapColumn> list=zwkjFormService.getFormTagMapColumnByFormId(formList.get(i).getId());
				//过滤意见标签
				for(FormTagMapColumn mapColumn : list){
					if("comment".equals(mapColumn.getFormtagtype())){
						resultList.add(mapColumn);
					}
					if("attachment".equals(mapColumn.getFormtagtype())){
						attList.add(mapColumn);
					}
					if(CommonUtil.stringNotNULL(mapColumn.getListId())){
						listTagList.add(mapColumn);
					}
				}
			}
		}
		getRequest().setAttribute("commentList", resultList);
		getRequest().setAttribute("attachmentList", attList);
		getRequest().setAttribute("listTagList", listTagList);
		
		return "toEditJsp";
	}
	
	/**
	 * 到修改红头模板的页面
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-3-23 下午2:15:37
	 */
	public String toEditRedJsp(){
		String isRedTape = getRequest().getParameter("isRedTape");
		String id = getRequest().getParameter("id");
		template = templateService.getTemplateById(id);
		List<WfLabel> labelList = labelService.getLabelByTemplateId(template.getId());
		List<WfTableInfo> tableList = tableInfoService.getTableByLcid(template.getLcid());
		
		getRequest().setAttribute("template", template);
		getRequest().setAttribute("labelList", labelList);
		getRequest().setAttribute("tableList", tableList);
		
		//查询表单关联了几个节点
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id);
//		List<WfNode> list = workflowBasicFlowService.getNodesByPid(lcid);
//		getRequest().setAttribute("nodeList", list);
		
		//获取绑定流程的所有表单
		String hql="from ZwkjForm z where z.workflowId='"+lcid+"'";
		List<ZwkjForm> formList=zwkjFormService.getFormListByHql(hql, null, null);
		
		List<FormTagMapColumn> resultList = new ArrayList<FormTagMapColumn>();
		List<FormTagMapColumn> attList = new ArrayList<FormTagMapColumn>();
		List<FormTagMapColumn> listTagList = new ArrayList<FormTagMapColumn>();
		//获取绑定流程的所有表单的所有意见标签
		if (formList!=null&&formList.size()>0) {
			for (int i = 0; i < formList.size(); i++) {
				List<FormTagMapColumn> list=zwkjFormService.getFormTagMapColumnByFormId(formList.get(i).getId());
				//过滤意见标签
				for(FormTagMapColumn mapColumn : list){
					if("comment".equals(mapColumn.getFormtagtype())){
						resultList.add(mapColumn);
					}
					if("attachment".equals(mapColumn.getFormtagtype())){
						attList.add(mapColumn);
					}
					if(CommonUtil.stringNotNULL(mapColumn.getListId())){
						listTagList.add(mapColumn);
					}
				}
			}
		}
		List<WfTemplate> relationTemplate = templateService.getTemplateBySql(" and e.position='1' ");
		getRequest().setAttribute("relationTemplate", relationTemplate);
		getRequest().setAttribute("commentList", resultList);
		getRequest().setAttribute("attachmentList", attList);
		getRequest().setAttribute("listTagList", listTagList);
		getRequest().setAttribute("isRedTape", isRedTape);
		
		return "toEditRedJsp";
	}
	
	public String edit() throws FileNotFoundException, IOException{
		String templateid = getRequest().getParameter("template.id");
		// 修改模板信息
		WfTemplate template_old = templateService.getTemplateById(templateid);
		template.setVc_path(getRequest().getParameter("vc_path"));
		template.setId(getRequest().getParameter("template.id"));
		template.setC_createdate(new Timestamp(System.currentTimeMillis()));
		if(null!=template_old){
			template.setReflcId(template_old.getReflcId());
			template.setLcid(template_old.getLcid());
			String vc_path = template_old.getVc_path();
			if(StringUtils.isNotBlank(vc_path)){
				String dstPath = getSpringContext().getServletContext().getRealPath("") + "/tempfile/" + vc_path;
				File file = new File(dstPath);
				if(file.exists()){
					template.setAttflow(Hibernate.createBlob(new FileInputStream(file)));
				}
			}
		}
		template = templateService.addTemplate(template);
		
		//每次修改，标签字段映射关系删除重建
		List<WfLabel> list = labelService.getLabelByTemplateId(template.getId());
		for(WfLabel label : list){
			labelService.delLabel(label);
		}
		
		//新增字段对应关系
		String[] vc_fields = getRequest().getParameterValues("vc_fieldname");
		String[] vc_tables = getRequest().getParameterValues("vc_tablename");
		String[] vc_labels = getRequest().getParameterValues("vc_label");
		String[] vc_comments = getRequest().getParameterValues("vc_comment");
		
		String[] vc_types = getRequest().getParameterValues("vc_type");
		String[] vc_atts = getRequest().getParameterValues("vc_att");
		String[] vc_lists = getRequest().getParameterValues("vc_list");
		
		WfLabel label = null;
		for(int i = 0; vc_labels != null && i < vc_labels.length; i++){
			label = new WfLabel();
			label.setVc_label(vc_labels[i]);
			label.setVc_templateId(template.getId());
			if(vc_fields[i] != null&&!vc_fields[i].equals("") && vc_fields[i].split(",").length > 1){
				label.setVc_fieldid(vc_fields[i].split(",")[0]);
				label.setVc_fieldname(vc_fields[i].split(",")[1]);
			}
			if(vc_tables[i] != null&&!vc_tables[i].equals("") && vc_tables[i].split(",").length > 1){
				label.setVc_tableid(vc_tables[i].split(",")[0]);
				label.setVc_tablename(vc_tables[i].split(",")[1]);
			}
			label.setVc_commentId(vc_comments[i]);
			label.setVc_type(vc_types[i]);
			label.setVc_att(vc_atts[i]);
			label.setVc_list(vc_lists[i]);
			labelService.addLabel(label);
			
		}
		return getTemplateList();
	}
	
	/**
	 * 修改红头模板
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:季振华
	 * 创建时间:2016-3-23 下午2:18:51
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String editRed() throws FileNotFoundException, IOException{
		String isRedTape = getRequest().getParameter("isRedTape");
		String templateid = getRequest().getParameter("template.id");
		String relationId = getRequest().getParameter("relationId");
		// 修改模板信息
		WfTemplate template_old = templateService.getTemplateById(templateid);
		template.setVc_path(getRequest().getParameter("vc_path"));
		template.setId(getRequest().getParameter("template.id"));
		template.setRelationId(relationId);
		template.setC_createdate(new Timestamp(System.currentTimeMillis()));
		if(null!=template_old){
			template.setReflcId(template_old.getReflcId());
			template.setLcid(template_old.getLcid());
			String vc_path = template_old.getVc_path();
			if(StringUtils.isNotBlank(vc_path)){
				String dstPath = getSpringContext().getServletContext().getRealPath("") + "/tempfile/" + vc_path;
				File file = new File(dstPath);
				if(file.exists()){
					template.setAttflow(Hibernate.createBlob(new FileInputStream(file)));
				}
			}
		}
		template = templateService.addTemplate(template);
		
		//每次修改，标签字段映射关系删除重建
		List<WfLabel> list = labelService.getLabelByTemplateId(template.getId());
		for(WfLabel label : list){
			labelService.delLabel(label);
		}
		
		//新增字段对应关系
		String[] vc_fields = getRequest().getParameterValues("vc_fieldname");
		String[] vc_tables = getRequest().getParameterValues("vc_tablename");
		String[] vc_labels = getRequest().getParameterValues("vc_label");
		String[] vc_comments = getRequest().getParameterValues("vc_comment");
		
		String[] vc_types = getRequest().getParameterValues("vc_type");
		String[] vc_atts = getRequest().getParameterValues("vc_att");
		String[] vc_lists = getRequest().getParameterValues("vc_list");
		
		WfLabel label = null;
		for(int i = 0; vc_labels != null && i < vc_labels.length; i++){
			label = new WfLabel();
			label.setVc_label(vc_labels[i]);
			label.setVc_templateId(template.getId());
			if(vc_fields[i] != null&&!vc_fields[i].equals("") && vc_fields[i].split(",").length > 1){
				label.setVc_fieldid(vc_fields[i].split(",")[0]);
				label.setVc_fieldname(vc_fields[i].split(",")[1]);
			}
			if(vc_tables[i] != null&&!vc_tables[i].equals("") && vc_tables[i].split(",").length > 1){
				label.setVc_tableid(vc_tables[i].split(",")[0]);
				label.setVc_tablename(vc_tables[i].split(",")[1]);
			}
			label.setVc_commentId(vc_comments[i]);
			label.setVc_type(vc_types[i]);
			label.setVc_att(vc_atts[i]);
			label.setVc_list(vc_lists[i]);
			labelService.addLabel(label);
			
		}
		getRequest().setAttribute("isRedTape", isRedTape);
		return getTemplateList();
	}
	
	public String del(){
		String lcid = (String) getRequest().getSession().getAttribute(
				MyConstants.workflow_session_id);
		String id = getRequest().getParameter("ids");
		String[] ids = id.split(",");
		for (String strId : ids) {
			template = templateService.getTemplateById(strId);
			if (lcid.equals(template.getLcid())) {
				String dstPath = getSpringContext().getServletContext().getRealPath("") + "/tempfile/" + template.getVc_path();
				File f = new File(dstPath);
				if(f.exists()){
					f.delete();
				}
				templateService.delTemplate(template);
				List<WfLabel> list = labelService.getLabelByTemplateId(strId);
				for(WfLabel label : list){
					labelService.delLabel(label);
				}
			} else {
				if (template.getReflcId() != null) {
					String vc_ref = template.getReflcId().replace("," + lcid + ",",
							",");
					template.setReflcId(vc_ref);
				}
				templateService.addTemplate(template);
			}
			
		}
		return getTemplateList();
	}
	
	public void upload(){
		String vc_filename = getRequest().getParameter("name");
		String dstPath = getSpringContext().getServletContext().getRealPath("") + "/tempfile/" + vc_filename;
		MyUtils.copy(vc_file, new File(dstPath));
		dstPath = getRequest().getContextPath() + "/tempfile/" + vc_filename;
	}
	
	@SuppressWarnings("deprecation")
	public void download(){
		String vc_filename = getRequest().getParameter("name");
		String fileNameWithPath = getRequest().getRealPath("")+"/tempfile/" + vc_filename;
		try {
			// 转码（UTF-8-->GB2312），现在环境下的编码是UTF-8，但服务器操作系统的编码是GB2312
			fileNameWithPath = new String(fileNameWithPath.getBytes(), "GB2312");
			// 下载文件时显示的文件名，一定要经过这样的转换，否则乱码
			vc_filename = URLEncoder.encode(vc_filename, "GB2312");
			vc_filename = URLDecoder.decode(vc_filename, "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		File file = new File(fileNameWithPath);
		FileInputStream fileinputstream = null;
		try {
			fileinputstream = new FileInputStream(file);
			long l = file.length();
			int k = 0;
			byte abyte0[] = new byte[65000];
			getResponse().setContentType("application/x-msdownload");
			getResponse().setContentLength((int) l);
			getResponse().setHeader("Content-Disposition", "attachment; filename="
					+ vc_filename);
			while ((long) k < l) {
				int j;
				j = fileinputstream.read(abyte0, 0, 65000);
				k += j;
				getResponse().getOutputStream().write(abyte0, 0, j);
			}
			getResponse().getOutputStream().flush();   
			getResponse().getOutputStream().close();   
			getResponse().flushBuffer();   
		} catch (SocketException se) { 
			System.out.println( "sss:" + se.getMessage()); 
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(fileinputstream!=null){
					fileinputstream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解析word文件，获取其中{{ }}格式的标签
	 */
	public void getField(){
		String path = getRequest().getParameter("path");
		String bkstr=null;
		try {
			bkstr = java.net.URLDecoder.decode(getRequest().getParameter("bkstr"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (bkstr!=null&&bkstr.indexOf(",")!=-1) {
			bkstr=bkstr.substring(0,bkstr.length()-1);
		}
		path = getSpringContext().getServletContext().getRealPath("") + "/tempfile/" +path;
		try{
//            FileInputStream in = new FileInputStream(path);//载入文档  
//            WordExtractor extractor = new WordExtractor(in);
//            
//            String str =  extractor.getText();
//            String meta = templateService.getAllMeta(getDocString(path));
			String meta =bkstr;
            //此处对传阅单字段做特殊处理
            String[] metas=meta.split(",");//把标签数据拆分数组
            List<Map> list=new ArrayList<Map>();
            if (metas!=null) {
            	 //区分获取所有CY_开头的数据
				for (int i = 0; i < metas.length; i++) {
					if (metas[i].indexOf("CY_")==0) {
						setListType(list,metas[i],i);
					}
				}
				//清空所有CY_开头的数据
				for (int i = 0; i < metas.length; i++) {
					if (metas[i].indexOf("CY_")==0) {
						metas[i]="";
					}
				}
			}
            //CY_开头的最后一位数据赋值
            if (list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					Map m=list.get(i);
					int index=Integer.parseInt(m.get("index").toString());
					metas[index]=m.get("all").toString();
				}
			}
            //还原拼接标签字符串
            meta="";
            for (int i = 0; i < metas.length; i++) {
            	if (!metas[i].equals("")) {
					meta+=metas[i]+",";
				}
			}
            if (meta.length()>0) {
				meta=meta.substring(0,meta.length()-1);
			}
            
            meta=unionBookmarkList(meta,"LIST_");
            
    		PrintWriter out;
    		out = this.getResponse().getWriter();
    		if(meta == null){
    			meta = "";
    		}
    		out.write( meta);
    		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 合并list字段到最后一个
	 * @param meta 标签字符串
	 * @param mark 解析标签的特殊字符串 
	 * @return
	 */
	public String unionBookmarkList(String meta,String mark){
		//此处对传阅单字段做特殊处理
        String[] metas=meta.split(",");//把标签数据拆分数组
        List<Map> list=new ArrayList<Map>();
        if (metas!=null) {
        	 //区分获取所有CY_开头的数据
			for (int i = 0; i < metas.length; i++) {
				if (metas[i].indexOf(mark)==0) {
					setListType(list,metas[i],i);
				}
			}
			//清空所有CY_开头的数据
			for (int i = 0; i < metas.length; i++) {
				if (metas[i].indexOf(mark)==0) {
					metas[i]="";
				}
			}
		}
        //CY_开头的最后一位数据赋值
        if (list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				Map m=list.get(i);
				int index=Integer.parseInt(m.get("index").toString());
				metas[index]=m.get("all").toString();
			}
		}
        //还原拼接标签字符串
        meta="";
        for (int i = 0; i < metas.length; i++) {
        	if (!metas[i].equals("")) {
				meta+=metas[i]+",";
			}
		}
        if (meta.length()>0) {
			meta=meta.substring(0,meta.length()-1);
		}
		return meta;
	}
	/**
	 * 过滤数组，用来记录传阅单字段最后一个序号
	 * @param list
	 * @param str
	 * @param index
	 * @return
	 */
	public List<Map> setListType(List<Map> list,String str,int index){
		String bj=str.split("_")[0];
		String column=str.split("_")[1];
		String max=str.split("_")[2];
		boolean isin=false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("column").toString().equals(column)) {
				list.get(i).put("all", str);
				list.get(i).put("bj", bj);
				list.get(i).put("max", max);
				list.get(i).put("index", index);
				isin=true;break;
			}
		}
		if (!isin) {
			Map m=new HashMap();
			m.put("all", str);
			m.put("bj", bj);
			m.put("column", column);
			m.put("max", max);
			m.put("index", index);
			list.add(m);
		}
		
		return list;
	}
	
	public String getDocString(String path){
		//思路：先使用word2003的方式获取，如果获取失败使用word2007方式
		String str=null;
		//word2003 ：图片不会被读取
        try {
        	System.out.println("*********word2003***********");
			FileInputStream in = new FileInputStream(path);//载入文档  
			WordExtractor extractor = new WordExtractor(in);
			str =  extractor.getText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (str==null) {
				try {
					System.out.println("*********word2007***********");
					OPCPackage package1=POIXMLDocument.openPackage(path);
					POIXMLTextExtractor extractor=new XWPFWordExtractor(package1);
					
					str=extractor.getText();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlException e) {
					e.printStackTrace();
				} catch (OpenXML4JException e) {
					e.printStackTrace();
				}finally{
					return str;
				}
					
			}
		}
		System.out.println("解析word文字："+str);
		return str;
	}
	
	
	public String dy(){
		String labels = "${vc_user},<!--vc_mt-->";
		String values = "vc_user用户,vc_mt材料";
		String name = "1365416497890.doc";
		getRequest().setAttribute("labels", labels);
		getRequest().setAttribute("values", values);
		getRequest().setAttribute("name", name);
		return "toDyJsp";
	}

	public String toDyJsp() {
		//String nodeId = getRequest().getParameter("nodeId");
		String instanceId = getRequest().getParameter("instanceId");
		//WfNode node = workflowBasicFlowService.getWfNode(nodeId);
		String templateId = getRequest().getParameter("templateId");
		template = templateService.getTemplateById(templateId);
		if(template == null){
			getRequest().setAttribute("msg", "没有模板，请先设置模板");
			return "toEditDocJsp";
		}
		List<WfLabel> list = labelService.getLabelByTemplateId(templateId);
		Map<String, String> map = templateService.getTemplateValue(list, instanceId, "");
		//解析map数组 分解成可以用ntko控件套打的结构
		String bookmark="";
		String bookmarkValue="";
		Set<String> set=map.keySet();
		for (Iterator it=set.iterator();it.hasNext();) {
			String key=(String)it.next();
			String value=map.get(key);
			bookmark+=key+",";
			if(value==null||value.trim().equals(""))value="null";
			bookmarkValue+=value+",";
		}
		if (bookmark.indexOf(",")!=-1) {
			bookmark=bookmark.substring(0,bookmark.length()-1);
		}
		if (bookmarkValue.indexOf(",")!=-1) {
			bookmarkValue=bookmarkValue.substring(0,bookmarkValue.length()-1);
		}
		getRequest().setAttribute("bookmark", bookmark);
		bookmarkValue=bookmarkValue.replaceAll("\r\n", "#textarea#");//替换textarea换行特殊字符串 ，防止js解析报错 add by panh
		getRequest().setAttribute("bookmarkValue", bookmarkValue);
		getRequest().setAttribute("name", template.getVc_path());
		getRequest().setAttribute("isview", "1");//用于区分编辑或查看套打页面
		/************城管定制*********/
		if("1".equals(getRequest().getParameter("isToDb"))){
			getRequest().setAttribute("isToDb", "1");//用于区分定制页面（是否可以保存到待办）
			return "toEditToDbDocJsp";
		}
		/************************/
		return "toEditDocJsp";

	}

	//===============================================移 动 端 接 口========start================================================
	
	/**
	 * 手机端访问----得到生成的word
	 */
	public void getWordForMobile(){
//		JSONObject jsonObject = getJSONObject(); 		
		//节点id
		String nodeId = getRequest().getParameter("nodeId");
		//实例id
		String instanceId = getRequest().getParameter("instanceId");
//		//节点id
//		String nodeId = (String)jsonObject.get("nodeId");
//		//实例id
//		String instanceId = (String)jsonObject.get("instanceId");
//		//节点id
//		String nodeId = "3F1B002C-0038-4B33-B0E5-31DE619EDE74";
//		//实例id
//		String instanceId = "FC0E8AB5-B71F-4BEC-AF91-E418B310AD26";
		
		WfNode node = workflowBasicFlowService.getWfNode(nodeId);
		String templateId = node.getWfn_defaulttemplate();
		template = templateService.getTemplateById(templateId);
		List<WfLabel> list = labelService.getLabelByTemplateId(templateId);
		Map<String, String> map = templateService.getTemplateValue(list, instanceId, "");
		//模板文件地址
		String srcPath = getSpringContext().getServletContext().getRealPath("") + "\\tempfile\\" + template.getVc_path();
		//临时文件地址，每次将文件拷贝到临时文件，然后再读取临时文件的内容，替换标签，输出到页面，实现套打
		String dstPath = getSpringContext().getServletContext().getRealPath("") + "\\tempfile\\info_" + template.getVc_path();
		boolean isOk=false;
		try{
			/**************************word2003替换文本*******************************/
			FileInputStream in = new FileInputStream(new File(srcPath));
			HWPFDocument hdt = new HWPFDocument(in);
			//读取word文本内容
			Range range = hdt.getRange();
			//替换文本内容
			for (Map.Entry<String,String> entry:map.entrySet()) {
				//进行标签替换
				range.replaceText("{{"+entry.getKey()+"}}",entry.getValue());
			}
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			
			File dstFile = new File(dstPath);
			//检查临时文件是否存在，存在先删除，再重新拷贝模板文件
			if(dstFile.exists()){
				dstFile.delete();
			}
			
			FileOutputStream out = new FileOutputStream(dstPath,true);
			hdt.write(ostream);
			//输出字节流
			out.write(ostream.toByteArray());
			out.close();
			ostream.close();
			//将文件名传到套打页面，来读取显示
			getRequest().setAttribute("name", "info_"+template.getVc_path());
			isOk=true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				XWPFDocument document=new XWPFDocument(POIXMLDocument.openPackage(srcPath));
				List<XWPFParagraph> list2=document.getParagraphs();
				//基本内容替换
				for (int i = 0; i < list2.size(); i++) {
					for (Map.Entry<String,String> entry:map.entrySet()) {
						if (list2.get(i).getParagraphText().indexOf("{{"+entry.getKey()+"}}")!=-1) {
							String value=list2.get(i).getParagraphText();
							List<XWPFRun> runs=list2.get(i).getRuns();
							//删除原来内容
							for (int j = runs.size()-1; j >=0; j--) {
								list2.get(i).removeRun(j);
							}
							//创建新内容
							XWPFRun run=list2.get(i).createRun();
							run.setText(value.replaceAll("\\{\\{"+entry.getKey()+"\\}\\}", entry.getValue()));
							System.out.println(list2.get(i).getParagraphText());
							
						}
					}
				}
				//解析表格
				Iterator it=document.getTablesIterator();
				//表格内容替换添加
				while (it.hasNext()) {
					XWPFTable table=(XWPFTable)it.next();
					int rcount=table.getNumberOfRows();
					for (int i = 0; i < rcount; i++) {
						XWPFTableRow row=table.getRow(i);
						List<XWPFTableCell> cells=row.getTableCells();
						for (XWPFTableCell cell:cells) {
							for (Map.Entry<String,String> entry:map.entrySet()) {
								if (cell.getText().indexOf("{{"+entry.getKey()+"}}")!=-1) {
									String value=cell.getText();
									//删除原有内容
									cell.removeParagraph(0);
									//写入新内容
									cell.setText(value.replaceAll("\\{\\{"+entry.getKey()+"\\}\\}", entry.getValue()));
								}
							}
						}
					}
				}
				//转pdf后得到pdf地址
				String pdfPath = docToPdf(dstPath);
				
				File file = new File(pdfPath);
				FileInputStream fileinputstream =  new FileInputStream(file);
				long l = file.length();
				int k = 0;
				byte abyte0[] = new byte[65000]; 
//				getResponse().setContentType("application/x-msdownload");
//				getResponse().setContentLength((int) l);
//				getResponse().setHeader("Content-Disposition", "attachment; filename=info_"+template.getVc_path().substring(0,template.getVc_path().length()-3)+"pdf");
				while ((long) k < l) {
					int j;
					j = fileinputstream.read(abyte0, 0, 65000);
					k += j;
					getResponse().getOutputStream().write(abyte0, 0, j);
				}
				getResponse().getOutputStream().flush();   
				fileinputstream.close();
				getResponse().getOutputStream().close();   
				getResponse().flushBuffer();   
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * word转pdf
	 * @param filePath
	 * @param filename
	 * @throws IOException 
	 */
	public String docToPdf(String dstPath) throws IOException{
		//文件路径及文件名，除去后缀
		String fileAndPath = dstPath.substring(0,dstPath.length()-4);
		
//		WordToPdfOfPrinter d2p = new WordToPdfOfPrinter();
		
//		d2p.docToPDF(fileAndPath+".doc", fileAndPath+".ps", fileAndPath+".pdf");
		boolean success = (new File(fileAndPath+".ps")).delete();
		boolean success2 = (new File(fileAndPath+".log")).delete();
		
		if(success && success2){
			System.out.println("删除打印机文件成功");
		}
//			uploadPDF(fileId,isManAttStr);
//			getResponse().getWriter().print("success");
		String pdfPath = dstPath.substring(0,dstPath.length()-4)+".pdf";
		return pdfPath;
		//若报错com.jacob.com.ComFailException: VariantChangeType failed
		//那么在C:\Windows\System32\config\systemprofile下创建文件夹Desktop
	}
	
	//===============================================移 动 端 接 口=========end=================================================
	
	private JSONObject getJSONObject(){
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			return JSONObject.fromObject(new String (data,"utf-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(iStream!=null){
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}
	
	/**
	 * 
	 * 描述：判断节点是否绑定模板
	 * 作者:蔡亚军
	 * 创建时间:2014-7-1 下午2:26:04
	 */
	public void nodeIsHaveTemplate(){
		String nodeId = getRequest().getParameter("nodeId");
		WfNode node = workflowBasicFlowService.getWfNode(nodeId);
		String templateId = node.getWfn_defaulttemplate();
		String value="";
		if (CommonUtil.stringNotNULL(templateId)) {
			value="1";//节点有绑定的套打模板
		}else{
			value="0";//节点没有绑定的套打模板
		}
		try {
			getResponse().getWriter().print(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 描述：获取节点上绑定的模板
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2014-7-1 下午2:29:52
	 */
	public String getTemplateForPrint(){
		String nodeId = getRequest().getParameter("nodeId");
		WfNode node = workflowBasicFlowService.getWfNode(nodeId);
		String templateId = node.getWfn_defaulttemplate();
		String[] templateIds=templateId.split(",");
		List<WfTemplate> list =new ArrayList<WfTemplate>();
		for (int i = 0; i < templateIds.length; i++) {
			list.add(templateService.getTemplateById(templateIds[i]));
		}
		
		//加载附件
		for(int i = 0 ; i < list.size(); i++ ){
			WfTemplate temp = list.get(i);
			String fileNameWithPath =getRequest().getRealPath("")+"\\tempfile\\"+temp.getVc_path();
			File downFile = new File(fileNameWithPath);
		}
		
		getRequest().setAttribute("list", list);
		return "getTemplateForPrint";
	}
	
	
	/**
	 * 获取流程绑定的所有套打模板  为流程节点选择字段
	 * 描述：TODO 对此方法进行描述
	 * @return String
	 * 作者:蔡亚军
	 * 创建时间:2014-7-1 下午2:56:26
	 */
	public String getTemplateForNode(){
		String column = getRequest().getParameter("column");
		String value = getRequest().getParameter("value");
		String lcid = (String)getRequest().getSession().getAttribute(MyConstants.workflow_session_id); 
		if(template == null){
			template = new WfTemplate();
		}
		template.setLcid(lcid);
		String redtape = getRequest().getParameter("redtape");		//1:表示红头文件
		template.setIsRedTape(redtape);
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count = templateService.getTemplateCountForPage(column, value, template);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<WfTemplate> list = templateService.getTemplateListForPage(column, value, template, null, null);
		getRequest().setAttribute("list", list);
		String ids=getRequest().getParameter("ids");
		getRequest().setAttribute("ids", ids);
		return "getTemplateForNode";
	}
	
	/**
	 * 
	 * 描述：获取模板的书签以及数据内容
	 * 作者:蔡亚军
	 * 创建时间:2016-7-13 上午11:40:29
	 */
	public void getBookMarkByTempId(){
		String fileId = getRequest().getParameter("fileId");
		String instanceId = getRequest().getParameter("instanceId");	//实例Id
		String templateId = getRequest().getParameter("templateId");	//实例Id
		template = templateService.getTemplateById(templateId);
		String serverUrl = getRequest().getScheme()+"://" + getRequest().getServerName() + ":"	+ getRequest().getLocalPort() + getRequest().getContextPath();
		String content = "";
		if(template!=null){
			List<WfLabel> list = labelService.getLabelByTemplateId(templateId);
			Map<String, String> map = templateService.getTemplateValue(list, instanceId, fileId, serverUrl);
			//解析map数组 分解成可以用ntko控件套打的结构
			String bookmark="";
			String bookmarkValue="";
			Set<String> set=map.keySet();
			for (Iterator it=set.iterator();it.hasNext();) {
				String key=(String)it.next();
				String value=map.get(key);
				bookmark+=key+",";
				if(value==null||value.trim().equals(""))value="null";
				bookmarkValue+=value+",";
			}
			if (bookmark.indexOf(",")!=-1) {
				bookmark=bookmark.substring(0,bookmark.length()-1);
			}
			if (bookmarkValue.indexOf(",")!=-1) {
				bookmarkValue=bookmarkValue.substring(0,bookmarkValue.length()-1);
			}
			//getRequest().setAttribute("bookmark", bookmark);
			bookmarkValue=bookmarkValue.replaceAll("\r\n", "#textarea#");//替换textarea换行特殊字符串 ，防止js解析报错 add by panh
			//getRequest().setAttribute("bookmarkValue", bookmarkValue);
			content  = bookmark+";"+bookmarkValue;
		}
		
		PrintWriter out = null;
		HttpServletResponse response = getResponse();
		response.setCharacterEncoding("utf-8");
		String outStr = "";
		try {
			out = getResponse().getWriter();
			outStr = content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 输出json
		out.print(outStr);
		out.close();
	}
	
	 /**
	  * 获取底部模板
	  * @return
	  */
	 public void getButtonTemplate(){
		 String tempId = getRequest().getParameter("tempId");		//获取头部模板Id
		 WfTemplate temp = templateService.getTemplateById(tempId);
		 String val = "";
		 if(temp!=null){			//模板
			 String relationId = temp.getRelationId();		//关联模板id
			 if(relationId!=null && !relationId.equals("")){
				 WfTemplate temps = templateService.getTemplateById(relationId);
				 if(temps!=null && !temps.equals("")){
					 String vc_path = temps.getVc_path();
					 String vc_id = temps.getId();
					 val = vc_path+";"+vc_id;
				 }
			 }
		 }
		 PrintWriter out = null;
		 try {
			out = this.getResponse().getWriter();
			out.write(val);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	 }
}
