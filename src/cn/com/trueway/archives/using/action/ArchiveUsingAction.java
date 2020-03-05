package cn.com.trueway.archives.using.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import cn.com.trueway.archiveModel.listener.MsgWebSocket;
import cn.com.trueway.archiveModel.service.EssModelService;
import cn.com.trueway.archives.using.pojo.ArchiveMsg;
import cn.com.trueway.archives.using.pojo.ArchiveNode;
import cn.com.trueway.archives.using.pojo.Basicdata;
import cn.com.trueway.archives.using.pojo.Transferform;
import cn.com.trueway.archives.using.pojo.Transferstore;
import cn.com.trueway.archives.using.pojo.Usingform;
import cn.com.trueway.archives.using.service.ArchiveUsingService;
import cn.com.trueway.archives.using.util.FtpUtil;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.AESPlus;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.ExcelExport;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.FileUtils;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.base.util.PathUtil;
import cn.com.trueway.base.util.Utils;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.Employee;
import cn.com.trueway.workflow.core.pojo.WfTemplate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ArchiveUsingAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4786716771613274776L;

	private ArchiveUsingService archiveUsingService; 
	private EssModelService essModelService;
	
	private Basicdata basicdata;
	 
	static String jyd_ftp_ip = SystemParamConfigUtil.getParamValueByParam("jyd_ftp_ip");
	static String jyd_ftp_port = SystemParamConfigUtil.getParamValueByParam("jyd_ftp_port");
	static String jyd_ftp_name = SystemParamConfigUtil.getParamValueByParam("jyd_ftp_name");
	static String jyd_ftp_password = SystemParamConfigUtil.getParamValueByParam("jyd_ftp_password");
	static String jyd_ftp_dirpath = SystemParamConfigUtil.getParamValueByParam("jyd_ftp_dirpath");
	public ArchiveUsingService getArchiveUsingService() {
		return archiveUsingService;
	}
	public void setArchiveUsingService(ArchiveUsingService archiveUsingService) {
		this.archiveUsingService = archiveUsingService;
	}

	public Basicdata getBasicdata() {
		return basicdata;
	}

	public void setBasicdata(Basicdata basicdata) {
		this.basicdata = basicdata;
	}



	public EssModelService getEssModelService() {
		return essModelService;
	}
	public void setEssModelService(EssModelService essModelService) {
		this.essModelService = essModelService;
	}
	/**
	 * 展示数据字典
	 */
	public String toShowData(){
		String dataName = getRequest().getParameter("dataName");
		String type = getRequest().getParameter("type");
		String typeName = getRequest().getParameter("typeName");
		 Map<String,String> map = new HashMap<String,String>();
		 List<Basicdata>  typeList = archiveUsingService.queryTypeList(map);
		 String conditionSql = " ";
		 if(CommonUtil.stringNotNULL(dataName)){
			 conditionSql +=" and dataName like '%"+dataName+"%'";
		 }
		 if(CommonUtil.stringNotNULL(type)){
			 conditionSql +=" and type = '"+type+"'";
		 }
		 map.put("conditionSql", conditionSql);
		 int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		 int count = archiveUsingService.getDataCount(map);
		 Paging.setPagingParams(getRequest(), pageSize, count);
		 map.put("pageIndex", Paging.pageIndex+"");
		 map.put("pageSize", Paging.pageSize+"");
		List<Basicdata> dataList =  archiveUsingService.queryDataList(map);
		getRequest().setAttribute("dataList", dataList);  
		getRequest().setAttribute("typeList", typeList);  
		getRequest().setAttribute("dataName", dataName);  
		getRequest().setAttribute("type", type);  
		getRequest().setAttribute("typeName", typeName);  
		return "toshowBasicdata";
	}
	
	/**
	 * 字典数据添加页面
	 */
	public String addBasicData(){
		//获取所有类型
		Map<String,String> map = new HashMap<String,String>();
		 String conditionSql = "  ";
		 map.put("conditionSql", conditionSql);
		List<Basicdata>  typeList = archiveUsingService.queryTypeList(map);
		getRequest().setAttribute("typeList", typeList);  
		return "addBasicData";
	}

	/**
	 * 保存字典添加数据
	 */
	public String saveBasicData(){
		archiveUsingService.addBasicData(basicdata);
		
		return  toShowData();
	}
	
	/**
	 * 删除字典表数据
	 */
	public String delBasicData(){
		String ids = getRequest().getParameter("ids");
		String[] str = ids.split(",");
		String idstr = "'";
		for(int i=0;i<str.length;i++){
			idstr+=str[i]+"','";
		}
		idstr = idstr.substring(0,idstr.length()-2);
		archiveUsingService.deiBasicData(idstr);
		return  toShowData();
	}
	
	/**
	 * 修改字典表数据
	 */
	public String updateBaiscData(){
		String id = getRequest().getParameter("id");
		Map<String,String> map = new HashMap<String,String>();
		List<Basicdata>  typeList = archiveUsingService.queryTypeList(map);
		 String conditionSql = " and id = '"+id+"'";
		 map.put("conditionSql", conditionSql);
		 int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		 int count = archiveUsingService.getDataCount(map);
		 Paging.setPagingParams(getRequest(), pageSize, count);
		 map.put("pageIndex", Paging.pageIndex+"");
		 map.put("pageSize", Paging.pageSize+"");
		List<Basicdata> dataList =  archiveUsingService.queryDataList(map);
		getRequest().setAttribute("typeList", typeList);  
		getRequest().setAttribute("basicdata", dataList.get(0));  
		return "updateBaiscData";
	}
	
	/**
	 * 展示借阅单、借阅库配置字段
	 * @return
	 */
	public String toshowNode(){
		String vc_name = getRequest().getParameter("vc_name");
		String vc_table = getRequest().getParameter("vc_table");
		Map<String,String> map = new HashMap<String,String>();
		 //获取系统所有类型
		List<Basicdata>  typeList = archiveUsingService.queryTypeList(map);
		 String conditionSql = "";
		 if(CommonUtil.stringNotNULL(vc_name)){
			 conditionSql +=" and vc_name like '%"+vc_name+"%'";
		 }
		 if(CommonUtil.stringNotNULL(vc_table)){
			 if("1".equals(vc_table)){
				 conditionSql +=" and vc_table = 'T_USING_FORM'";
			 }else{
				 conditionSql +=" and vc_table = 'T_USING_STORE'";
			 }
		 }
		 map.put("conditionSql", conditionSql);
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		//获取字段类型
		Map<String,String> typeMap = new HashMap<String,String>();
		 int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		 int count = archiveUsingService.getDataCount(typeMap);
		 Paging.setPagingParams(getRequest(), pageSize, count);
		 typeMap.put("pageIndex", 0+"");
		 typeMap.put("pageSize", count+"");
		 String sql = " and type = 'sjkzd'";
		 typeMap.put("conditionSql", sql);
		 List<Basicdata> sjlxList =  archiveUsingService.queryDataList(typeMap);
		 //数据格式类型
		 typeMap.clear();
		 typeMap.put("pageIndex", 0+"");
		 typeMap.put("pageSize", 1000+"");
		 String sql2 = " and type = 'zdsjgslx'";
		 typeMap.put("conditionSql", sql2);
		 List<Basicdata> sjgslxList =  archiveUsingService.queryDataList(typeMap);
		 
		getRequest().setAttribute("typeList", typeList);  
		getRequest().setAttribute("sjlxList", sjlxList);  
		getRequest().setAttribute("sjgslxList", sjgslxList);
		JSONArray typeJson = JSONArray.fromObject(typeList);
		JSONArray sjlxJson = JSONArray.fromObject(sjlxList);
		JSONArray sjgslxJson = JSONArray.fromObject(sjgslxList);
		getRequest().setAttribute("typeJson", typeJson);  
		getRequest().setAttribute("sjlxJson", sjlxJson);  
		getRequest().setAttribute("sjgslxJson", sjgslxJson); 
		getRequest().setAttribute("nodeList", nodeList);  
		getRequest().setAttribute("vc_name", vc_name);  
		getRequest().setAttribute("vc_table", vc_table);  
		return "toshowNode";
	}
	
	/**
	 * 添加借阅单或借阅库字段，业务表同步增加字段
	 */
	public void addData(){
		PrintWriter out = null;
		String str = getRequest().getParameter("str");
		String vc_table = getRequest().getParameter("vc_table");
		String tableName = "";
		if("1".equals(vc_table)){
			tableName = "T_USING_FORM";
		}else{
			tableName = "T_USING_STORE";
		}
		String[] s = null;
		if(CommonUtil.stringNotNULL(str)){
			s = str.split(";");
		}
		String retrunStr = "";
		try {
			if(s.length>0){
				for(int i=0;i<s.length;i++){
					String[] cstr = s[i].split(",");
					ArchiveNode archiveNode = new ArchiveNode();
					String colName = "C"+cstr[4];
					//判断序号是否已存在
					int count = archiveUsingService.getCountByName(colName,tableName);
					if(count == 0){
						archiveNode.setVc_name(cstr[0]);
						archiveNode.setVc_type(cstr[1]);
						archiveNode.setVc_fielType(cstr[2]);
						archiveNode.setVc_length(cstr[3]);
						archiveNode.setVc_dataFormat(cstr[4]);
						archiveNode.setVc_number(cstr[5]);
						archiveNode.setVc_isEdit(cstr[6]);
						archiveNode.setVc_isNull(cstr[7]);
						archiveNode.setVc_isShow(cstr[8]);
						archiveNode.setVc_createTime(cstr[9]);
						archiveNode.setVc_system(cstr[10]);
						archiveNode.setVc_sysName(cstr[11]);
						archiveNode.setVc_height(cstr[12].trim().equals("")?"1":cstr[12].trim());
						archiveNode.setVc_width(cstr[13]);
						archiveNode.setVc_metadataname(cstr[14]);
						archiveNode.setVc_metadataid(cstr[15]);
						archiveNode.setVc_fielName("C"+cstr[5]);
						archiveNode.setVc_table(tableName);
						archiveUsingService.addData(archiveNode);
						// 业务表中增加字段
						archiveUsingService.addfield(archiveNode);
						retrunStr = "success";
					}else{
						retrunStr = "false";//排序重复
					}
					
				}
			}
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 删除借阅单、借阅库字段，同步隐藏业务表字段
	 * @return
	 */
	public String delData(){
		String ids = getRequest().getParameter("ids"); 
		String[] str = ids.split(",");
		String idstr = "'";
		for(int i=0;i<str.length;i++){
			idstr+=str[i]+"','";
			ArchiveNode archiveNodeOld =  archiveUsingService.getArchiveNodeById(str[i]);
			archiveUsingService.delfield(archiveNodeOld);
		}
		idstr = idstr.substring(0,idstr.length()-2);
		archiveUsingService.delData(idstr);
		return toshowNode();
	}
	
	/**
	 * 修改借阅表、借阅库字段，同步修改业务表字段
	 */
	public void updateData(){
		PrintWriter out = null;
		String vc_table = getRequest().getParameter("vc_table");
		String tableName = "";
		if("1".equals(vc_table)){
			tableName = "T_USING_FORM";
		}else{
			tableName = "T_USING_STORE";
		}
		String str = getRequest().getParameter("str");
		String retrunStr = "";
		try {
			String[] cstr = str.split(",");
			ArchiveNode archiveNode = new ArchiveNode();
			//获取修改前
			ArchiveNode archiveNodeOld =  archiveUsingService.getArchiveNodeById(cstr[0]);
			//判断序号是否已存在
			archiveNode.setId(cstr[0]);
			archiveNode.setVc_name(cstr[1]);
			archiveNode.setVc_type(cstr[2]);
			archiveNode.setVc_fielType(cstr[3]);
			archiveNode.setVc_length(cstr[4]);
			archiveNode.setVc_dataFormat(cstr[5]);
			archiveNode.setVc_number(cstr[6]);
			archiveNode.setVc_isEdit(cstr[7]);
			archiveNode.setVc_isNull(cstr[8]);
			archiveNode.setVc_isShow(cstr[9]);
			archiveNode.setVc_createTime(cstr[10]);
			archiveNode.setVc_fielName("C"+cstr[6]);
			archiveNode.setVc_system(cstr[11]);
			archiveNode.setVc_sysName(cstr[12]);
			archiveNode.setVc_height(cstr[13].trim()==""?"1":cstr[13].trim());
			archiveNode.setVc_width(cstr[14]);
			archiveNode.setVc_table(tableName);
			archiveNode.setVc_metadataname(cstr[15]);
			archiveNode.setVc_metadataid(cstr[16]);
			archiveUsingService.addData(archiveNode); 
			String fielName = archiveNodeOld.getVc_fielName();
			archiveUsingService.upfield(fielName,archiveNode);
			String fielLength = archiveNodeOld.getVc_length();
			archiveUsingService.upfieldLength(fielLength, archiveNode);
			retrunStr = "success";
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 展示借阅单
	 */
	public String showUsingForm(){
		String vc_table = getRequest().getParameter("vc_table");
		String type = getRequest().getParameter("type");
		String id = getRequest().getParameter("id");
		String zzcdFlag = getRequest().getParameter("zzcdFlag");
		Map<String,String> map = new HashMap<String,String>();
		String conditionSql = "";
		String tableName = "";
		 conditionSql +=" and vc_table = 'T_USING_FORM'";
		 tableName = "T_USING_FORM";
		 conditionSql +=" and vc_isShow = '1' ";
		 map.put("conditionSql", conditionSql);
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		Map<String,String> allMap = new HashMap<String,String>();
		allMap.put("conditionSql", " and vc_table = 'T_USING_FORM'");
		List<ArchiveNode> allNodeList = archiveUsingService.queryNodeList(allMap);
		String stutsFailName = "";
		for(int x = 0; x < allNodeList.size(); x++) {
			if("status".equals(allNodeList.get(x).getVc_metadataname())) {
				stutsFailName = allNodeList.get(x).getVc_fielName();
			}
		}
		String stutsSql = "select " + stutsFailName + " from t_using_form where id='"+id+"'";
		String stuts = archiveUsingService.getFormStutsById(stutsSql);
		int asize = 0;
		int bsize = 0;
		List<ArchiveNode> leftList = new ArrayList<ArchiveNode>();
		List<ArchiveNode> rightList = new ArrayList<ArchiveNode>();
		List<ArchiveNode> midList = new ArrayList<ArchiveNode>();
		ArchiveNode anode = new ArchiveNode();
		anode.setId("1");
		String btzd="";
		for(ArchiveNode node : nodeList){
			//获取必填字段
			if("1".equals(node.getVc_isNull())){
				btzd+=node.getVc_fielName()+"--,";
			}
			if("1".equals(node.getVc_width())){
				if(asize == bsize){
					leftList.add(node);
					asize+= Integer.parseInt(node.getVc_height());
				}else if(asize > bsize){
					rightList.add(node);
					bsize+= Integer.parseInt(node.getVc_height());
				}else{
					leftList.add(node);
					asize+= Integer.parseInt(node.getVc_height());
				}
			}else{
					midList.add(node);
			}
		}
		getRequest().setAttribute("btzd", btzd);
		if(leftList.size()>rightList.size()){
			for(int i=1;i<=(leftList.size()-rightList.size());i++){
				rightList.add(anode);
			}
		}else if(leftList.size()<rightList.size()){
			for(int i=1;i<=(rightList.size()-leftList.size());i++){
				leftList.add(anode);
			}
		}
		//生成页面详细展示list
		List<Usingform> leftReturnList = new ArrayList<Usingform>();
		List<Usingform> rightReturnList = new ArrayList<Usingform>();
		List<Usingform> midReturnList = new ArrayList<Usingform>();
		for(ArchiveNode lnode : leftList){
			String vc_type = "";
			Usingform usingForm = new Usingform();
			usingForm.setId(lnode.getId());
			usingForm.setName(lnode.getVc_name());
			usingForm.setFielName(lnode.getVc_fielName());
			if(!"".equals(id)&& id != null){
				usingForm.setVc_val(getVal(id,lnode.getVc_fielName(),tableName));
			}
			usingForm.setTableName(lnode.getVc_table());
			usingForm.setVc_height(lnode.getVc_height());
			usingForm.setVc_weight(lnode.getVc_width());
			usingForm.setVc_isNull(lnode.getVc_isNull());
			usingForm.setVc_edit(lnode.getVc_isEdit());
			if(!"".equals(lnode.getVc_system()) && null != lnode.getVc_system()&& !" ".equals(lnode.getVc_system()) ){
				ArrayList<Basicdata> dataList =  archiveUsingService.queryData(lnode.getVc_system());
				usingForm.setBasicList(dataList);
				vc_type = "select";
			}
			if("".equals(vc_type)&&"1".equals(lnode.getVc_height())){
				vc_type = "input";
			}else if(!"select".equals(vc_type)){
				vc_type = "textarea";
			}
			usingForm.setVc_type(vc_type);
			if(!"1".equals(lnode.getVc_width())&&lnode.getVc_width() != null){
				midReturnList.add(usingForm);
			}else{
				leftReturnList.add(usingForm);
			}
		}
		for(ArchiveNode lnode : rightList){
			String vc_type = "";
			Usingform usingForm = new Usingform();
			usingForm.setId(lnode.getId());
			usingForm.setName(lnode.getVc_name());
			usingForm.setFielName(lnode.getVc_fielName());
			if(!"".equals(id)&& id != null){
				usingForm.setVc_val(getVal(id,lnode.getVc_fielName(),tableName));
			}
			usingForm.setTableName(lnode.getVc_table());
			usingForm.setVc_height(lnode.getVc_height());
			usingForm.setVc_weight(lnode.getVc_width());
			usingForm.setVc_isNull(lnode.getVc_isNull());
			usingForm.setVc_edit(lnode.getVc_isEdit());
			if(!"".equals(lnode.getVc_system()) && null != lnode.getVc_system()&& !" ".equals(lnode.getVc_system()) ){
				ArrayList<Basicdata> dataList =  archiveUsingService.queryData(lnode.getVc_system());
				usingForm.setBasicList(dataList);
				vc_type = "select";
			}
			if("".equals(vc_type)&&"1".equals(lnode.getVc_height())){
				vc_type = "input";
			}else if(!"select".equals(vc_type)){
				vc_type = "textarea";
			}
			usingForm.setVc_type(vc_type);
			if(!"1".equals(lnode.getVc_width())&&lnode.getVc_width() != null){
				midReturnList.add(usingForm);
			}else{
				rightReturnList.add(usingForm);
			}
		}
		
		for(ArchiveNode lnode : midList){
			String vc_type = "";
			Usingform usingForm = new Usingform();
			usingForm.setId(lnode.getId());
			usingForm.setName(lnode.getVc_name());
			usingForm.setFielName(lnode.getVc_fielName());
			if(!"".equals(id)&& id != null){
				usingForm.setVc_val(getVal(id,lnode.getVc_fielName(),tableName));
			}
			usingForm.setTableName(lnode.getVc_table());
			usingForm.setVc_height(lnode.getVc_height());
			usingForm.setVc_weight(lnode.getVc_width());
			usingForm.setVc_isNull(lnode.getVc_isNull());
			usingForm.setVc_edit(lnode.getVc_isEdit());
			if(!"".equals(lnode.getVc_system()) && null != lnode.getVc_system()&& !" ".equals(lnode.getVc_system()) ){
				ArrayList<Basicdata> dataList =  archiveUsingService.queryData(lnode.getVc_system());
				usingForm.setBasicList(dataList);
				vc_type = "select";
			}
			if("".equals(vc_type)&&"1".equals(lnode.getVc_height())){
				vc_type = "input";
			}else if(!"select".equals(vc_type)){
				vc_type = "textarea";
			}
			usingForm.setVc_type(vc_type);
			if(!"1".equals(lnode.getVc_width())&&lnode.getVc_width() != null){
				midReturnList.add(usingForm);
			}else{
				leftReturnList.add(usingForm);
			}
		}
		getRequest().setAttribute("left", leftReturnList);  
		getRequest().setAttribute("right", rightReturnList);  
		getRequest().setAttribute("mid", midReturnList); 
		getRequest().setAttribute("rsize", rightReturnList.size());  
		getRequest().setAttribute("lsize", leftReturnList.size());  
		getRequest().setAttribute("type", type);  
		getRequest().setAttribute("id", id);  
		getRequest().setAttribute("zzcdFlag", zzcdFlag);
		getRequest().setAttribute("stuts", stuts);
		if("1".equals(type)){
			return "addUsingForm";
		}else{
			//显示借阅库信息
			Map<String,String> storeMap = new HashMap<String,String>();
			String storeSql  =" and vc_table = 'T_USING_STORE'";
			storeSql +=" and vc_isShow = '1' ";
			storeMap.put("conditionSql", storeSql);
			//获取借阅库的展示字段
			List<ArchiveNode> storeList = archiveUsingService.queryNodeList(storeMap);
			Map<String,List<Basicdata>> sysmap = new HashMap<String, List<Basicdata>>();
			for(ArchiveNode lnode : storeList){
				if(!"".equals(lnode.getVc_system()) && null != lnode.getVc_system()&& !" ".equals(lnode.getVc_system()) ){
					ArrayList<Basicdata> dataList =  archiveUsingService.queryData(lnode.getVc_system());
					sysmap.put(lnode.getVc_system(), dataList);
				}
			}
			getRequest().setAttribute("sysmap", sysmap);
			getRequest().setAttribute("storeList", storeList); 
			storeMap.put("formId", id);
			List<String[]> valList = archiveUsingService.getStoreVal(storeList,storeMap);
			getRequest().setAttribute("valList", valList); 
			//getRequest().setAttribute("sizeCount", valList.size()); 
			return "showUsingForm";
		}
		
	}
	
	/**
	 * 添加借阅单,编辑借阅单
	 */
	public void addRegist(){
		PrintWriter out = null;
		String str = getRequest().getParameter("str");
		String id = getRequest().getParameter("id");
		String tableName = "T_USING_FORM";
		String retrunStr = "";
		try {
			String[] cstr = str.split(";");
			Map<String,String> map = new HashMap<String,String>();
			if(!"".equals(id)&&id != null){
				String sql = " set ";
				for(int i=0;i<cstr.length;i++){
					String feildName = cstr[i].split("--")[0];
					String feildVal = cstr[i].split("--")[1];
					sql +=feildName +"='"+feildVal.trim()+"',";
				}
				sql = sql.substring(0,sql.length()-1)+ " where id = '"+id+"'";
				map.put("sql", sql);
				map.put("tableName", tableName);
				archiveUsingService.updateData(map);
				retrunStr = id;
				out = getResponse().getWriter();
				out.print(retrunStr);
			}else{//新增
				String newid = UuidGenerator.generate36UUID();
				String columnSql = "";
				String valSql = "'";
				for(int i=0;i<cstr.length;i++){
					String feildName = cstr[i].split("--")[0];
					String feildVal = cstr[i].split("--")[1];
					columnSql +=feildName+",";
					valSql += feildVal.trim()+"','";
				}
				
				if(!"".equals(columnSql)&& columnSql != null){
					columnSql +="id";
					valSql +=newid+"'";
					map.put("columnSql", columnSql);
					map.put("valSql", valSql);
					map.put("tableName", tableName);
					archiveUsingService.insertData(map);
				}
				retrunStr = newid;
				out = getResponse().getWriter();
				out.print(retrunStr);
			}
			
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 借阅管理列表展示页面
	 */
	public String showLendingList(){
		String pagesize = getRequest().getParameter("pageSize");
		String contect = getRequest().getParameter("contect");
		String status = getRequest().getParameter("statusSe");
		String applyFlag = getRequest().getParameter("applyFlag");
		getRequest().setAttribute("applyFlag", applyFlag);
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String,String> map = new HashMap<String,String>();
		String conditionSql = "";
		 conditionSql +=" and vc_table = 'T_USING_FORM'";
		 map.put("conditionSql", conditionSql);
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		
		//查询条件
		Map<String,String> sqlMap = new HashMap<String,String>();
		String sql = "";
		if(!"".equals(contect)&&contect != null){
			sql +=" and (";
			for(ArchiveNode node:nodeList ){
				sql += node.getVc_fielName()+" like '%"+contect+"%' or ";
			}
			sql = sql.substring(0,sql.length()-3)+") ";
		}
		if(status != null && !"".equals(status)) {
			sql +=" and ";
			for(int i=0;i<nodeList.size();i++){
				if("status".equals(nodeList.get(i).getVc_metadataname())) {
					sql += nodeList.get(i).getVc_fielName()+"='"+status+"'";
				}
			}
		}
		Iterator<ArchiveNode> it = nodeList.iterator();
		while(it.hasNext()) {
			ArchiveNode ad = it.next();
			if(!"1".equals(ad.getVc_isShow())) {
				it.remove();
			}
		}
		sqlMap.put("sql", sql);
		
		int count = archiveUsingService.getValCount(nodeList,sqlMap);
		Paging.setPagingParams(getRequest(), pageSize, count);
		sqlMap.put("pagesize", pageSize+"");
		sqlMap.put("pageindex",Paging.pageIndex+"");
		List<String[]> valList = archiveUsingService.getValList(nodeList,sqlMap);
		getRequest().setAttribute("nodeList", nodeList); 
		getRequest().setAttribute("valList", valList); 
		getRequest().setAttribute("contect", contect); 
		getRequest().setAttribute("status", status); 
		getRequest().setAttribute("pageSize", pageSize); 
		return "LendingList";
	}
	
	/**
	 * 检验是否是自己的借阅单或者是自主查档的借阅单
	 * @return
	 */
	public void checkOwnerForm(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String ids = getRequest().getParameter("ids");
		String checkType = getRequest().getParameter("checkType");
		String[] idarr = ids.split(",");
		int count =0;//统计不符合的记录数
		for(int i=0;i<idarr.length;i++){
			String id = idarr[i];
			String djr = emp.getEmployeeName();
			Map<String,String> map = new HashMap<String,String>();
			String conditionSql =" and vc_table = 'T_USING_FORM'";
			 map.put("conditionSql", conditionSql);
			List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
			for(int j=0;j<nodeList.size();j++){
				if("registerLoginName".equals(nodeList.get(j).getVc_metadataname())){	
					String column = nodeList.get(j).getVc_fielName();
					//查询登记人
					String djr2 = getVal(id,column,"T_USING_FORM");
					if("delete".equals(checkType)||"update".equals(checkType)||"fj".equals(checkType)||"search".equals(checkType)){//判断检验类型   删除 修改 附件操作 检索
						if(!djr2.equals(djr)){//不符合
							count++;
						}
					}else if("detail".equals(checkType)){//借阅单明细查看时 可以看自己的和自主查档的
						if(!djr2.equals(djr)&&!djr2.equals("自主查档")){//不符合
							count++;
						}
					}
				}
			} 
		}
		if(count>0){
			outWirter("no",getResponse());
		}else{
			outWirter("yes",getResponse());
		}
	}
	
	/**
	 * 跳转身份验证
	 * @return
	 */
	public String toVerifyJsp(){
		Map<String,String> map = new HashMap<String,String>();
		String zzcdFlag = getRequest().getParameter("zzcdFlag");
		 String conditionSql = " and type = 'zj'";
		 map.put("conditionSql", conditionSql);
		 int count = archiveUsingService.getDataCount(map);
		 map.put("pageIndex", 0+"");
		 map.put("pageSize", count+"");
		List<Basicdata> dataList =  archiveUsingService.queryDataList(map);
		getRequest().setAttribute("dataList", dataList); 
		getRequest().setAttribute("zzcdFlag", zzcdFlag); 
		return "toVerifyJsp";
	}
	
	/**
	 * 身份验证信息保存至借阅单
	 */
	public void addVerifyInfo(){
		PrintWriter out = null;
		String retrunStr = "";
		String usinglyr = getRequest().getParameter("usinglyr");
		String usingzj = getRequest().getParameter("usingzj");
		String usingzjhm = getRequest().getParameter("usingzjhm");
		String zzcdFlag = getRequest().getParameter("zzcdFlag");
		//生成利用单编号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String usingbh = sdf.format(new Date());
		//登记日期
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String usingdjrq =  sdf1.format(new Date());
		//登记人
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String usingdjr = "";
		if("1".equals(zzcdFlag)){
			usingdjr = "自主查档";
		}else if(emp!=null){
			usingdjr = emp.getEmployeeName();;
		}
		//根据元数据配置查询各自对应的业务字段
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", "T_USING_FORM");
		String conditionSql =" and vc_table = 'T_USING_FORM'";
		//conditionSql +=" and vc_metadataname like 'using%' ";
		 map.put("conditionSql", conditionSql);
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		//判断当日是否有登记信息且是否完成
		String sql = "";
		for(int i=0;i<nodeList.size();i++){
			if("usingUser".equals(nodeList.get(i).getVc_metadataname())){
				sql += " and "+nodeList.get(i).getVc_fielName()+"='"+usinglyr+"'";
			}else if("zjmc".equals(nodeList.get(i).getVc_metadataname())){
				sql += " and "+nodeList.get(i).getVc_fielName()+"='"+usingzj+"'";
			}else if("identify".equals(nodeList.get(i).getVc_metadataname())){
				sql += " and "+nodeList.get(i).getVc_fielName()+"='"+usingzjhm+"'";
			}else if("registerDateTime".equals(nodeList.get(i).getVc_metadataname())){
				sql += " and "+nodeList.get(i).getVc_fielName()+"='"+usingdjrq+"'";
			}else if("status".equals(nodeList.get(i).getVc_metadataname())){
				sql += " and ("+nodeList.get(i).getVc_fielName()+" <> '3' or "+nodeList.get(i).getVc_fielName()+" is null) ";
			}
		}
		map.put("sql", sql);
		String id = archiveUsingService.getIdByCondition(map);
		String flag = "edit";
		try {
			String colName = "id,";
			if("".equals(id)||id==null){
				flag="add";
				id = UuidGenerator.generate36UUID();
				String valStr = "'"+id+"',";
				for(int i=0;i<nodeList.size();i++){
					if("usingUser".equals(nodeList.get(i).getVc_metadataname())){
						colName += nodeList.get(i).getVc_fielName()+",";
						valStr += "'"+usinglyr+"',";
					}else if("zjmc".equals(nodeList.get(i).getVc_metadataname())){
						colName += nodeList.get(i).getVc_fielName()+",";
						valStr += "'"+usingzj+"',";
					}else if("identify".equals(nodeList.get(i).getVc_metadataname())){
						colName += nodeList.get(i).getVc_fielName()+",";
						valStr += "'"+usingzjhm+"',";
					}else if("usingFromId".equals(nodeList.get(i).getVc_metadataname())){
						colName += nodeList.get(i).getVc_fielName()+",";
						valStr += "'"+usingbh+"',";
					}else if("registerDateTime".equals(nodeList.get(i).getVc_metadataname())){
						colName += nodeList.get(i).getVc_fielName()+",";
						valStr += "'"+usingdjrq+"',";
					}else if("registerLoginName".equals(nodeList.get(i).getVc_metadataname())){	
						colName += nodeList.get(i).getVc_fielName()+",";
						valStr += "'"+usingdjr+"',";
					}else if("status".equals(nodeList.get(i).getVc_metadataname())) {
						colName += nodeList.get(i).getVc_fielName()+",";
						if("1".equals(zzcdFlag)) {
							valStr += "'"+ "0" +"',";//自主查档  先置为 编辑状态
						} else {
							valStr += "'"+ "2" +"',";//馆内人员  状态直接置为已审核
						}
					}
				}
				map.put("columnSql", colName.substring(0,colName.length()-1));
				map.put("valSql", valStr.substring(0,valStr.length()-1));
				
				archiveUsingService.insertData(map);
			}
			retrunStr = "{\"success\":\"success\",\"id\":\""+id+"\",\"flag\":\""+flag+"\"}";
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	public void delLendingList(){
		
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String ids = getRequest().getParameter("ids"); 
			String[] str = ids.split(",");
			String idstr = "'";
			for(int i=0;i<str.length;i++){
				idstr+=str[i]+"','";
			}
			idstr = idstr.substring(0,idstr.length()-2);
			Map<String,String> map = new HashMap<String,String>();
			map.put("tableName", "T_USING_FORM");
			map.put("ids", idstr);
			archiveUsingService.delByIds(map);
			
			String deletesql =" delete T_USING_STORE where formid in ("+idstr+")";
			archiveUsingService.updateStore(deletesql);
			retrunStr = "success";
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 根据id，feildname,tablename 查询业务表数据
	 */
	public String getVal(String id,String feildName,String tableName){
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put("feildName", feildName);
		map.put("tableName", tableName);
		String returnStr = archiveUsingService.getVals(map);
		return returnStr;
	}
	
	/**
	 * 获取借阅库数据
	 */
	public List<String[]> getStoreVal(String id,String feildName,String tableName){
		Map<String,String> map = new HashMap<String,String>();
		map.put("formId", id);
		map.put("feildName", feildName);
		map.put("tableName", tableName);
		List<String[]> returnStr = archiveUsingService.getStoreVal(map);
		return returnStr;
	}
	
	/**
	 * 借阅单中保存借阅库信息
	 */
	public void saveStore(){
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String jydId = getRequest().getParameter("jydId");
			Map<String,String> storeMap = new HashMap<String,String>();
			String storeSql  =" and vc_table = 'T_USING_STORE'";
			storeSql +=" and vc_isShow = '1' ";
			storeMap.put("conditionSql", storeSql);
			//获取借阅库的展示字段
			List<ArchiveNode> storeList = archiveUsingService.queryNodeList(storeMap);
			
			storeMap.put("formId", jydId);
			List<String[]> valList = archiveUsingService.getStoreVal(storeList,storeMap);
			int dyys=0;
			int fyys=0;
			int zcys=0;
			int pshts=0;
			int jcts=0;
			for(int i=0;valList!=null&&i<valList.size();i++){
				String id = valList.get(i)[0];
				String updateSql = "";
				for(int j=0;j<storeList.size();j++){
					ArchiveNode node = storeList.get(j);
					if("1".equals(node.getVc_isEdit())){
						String column = node.getVc_fielName();
						String name = node.getVc_name();	
						String value = getRequest().getParameter(column+(i+1));
						if("打印页数".equals(name)){
							dyys+=toInt(value);
						}else if("复印页数".equals(name)){
							fyys+=toInt(value);
						}else if("摘抄页数".equals(name)){
							zcys+=toInt(value);
						}else if("拍摄画图数".equals(name)){
							pshts+=toInt(value);
						}else if("借出天数".equals(name)){
							jcts+=toInt(value);
						}
						updateSql+=column+"='"+value+"',";
					}
				}
				if(!"".equals(updateSql)){
					updateSql=updateSql.substring(0,updateSql.length()-1);
					String sql = "update T_USING_STORE set "+updateSql+" where id='"+id+"'";
					archiveUsingService.updateStore(sql);
					System.out.println(sql);
				}
				
				
			}
			
			//修改借阅单的数据
			Map<String,String> formMap = new HashMap<String,String>();
			String formSql  =" and vc_table = 'T_USING_FORM'";
			formSql +=" and vc_isShow = '1' ";
			formMap.put("conditionSql", formSql);
			//获取借阅单的展示字段
			List<ArchiveNode> fromList = archiveUsingService.queryNodeList(formMap);
			String updateSql = "";
			for(int j=0;j<fromList.size();j++){
				ArchiveNode node = fromList.get(j);
				String column = node.getVc_fielName();
				String name = node.getVc_name();
				String value="";
				if("打印页数".equals(name)){
					value=dyys+"";
					updateSql+=column+"='"+value+"',";
				}else if("复印页数".equals(name)){
					value=fyys+"";
					updateSql+=column+"='"+value+"',";
				}else if("摘抄页数".equals(name)){
					value=zcys+"";
					updateSql+=column+"='"+value+"',";
				}else if("拍摄画图数".equals(name)){
					value=pshts+"";
					updateSql+=column+"='"+value+"',";
				}else if("借出天数".equals(name)){
					value=jcts+"";
					updateSql+=column+"='"+value+"',";
				}
			}
			if(!"".equals(updateSql)){
				updateSql=updateSql.substring(0,updateSql.length()-1);
				String sql = "update T_USING_FORM set "+updateSql+" where id='"+jydId+"'";
				archiveUsingService.updateStore(sql);
				System.out.println(sql);
			}
			retrunStr = "success";
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	
	/**
	 * 借阅单中删除借阅库信息
	 */
	public void deleteStore(){
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String id = getRequest().getParameter("id");
			String ids = getRequest().getParameter("ids");
			//删除借阅库信息
			String storesql = " delete  T_USING_STORE where id in ("+ids+")" ;
			archiveUsingService.updateStore(storesql);
			
			//开始更新借阅单信息---------------------------------
			//1.获取借阅单数据
			Map<String,String> map = new HashMap<String,String>();
			map.put("conditionSql", " and vc_table = 'T_USING_FORM'");
			List<ArchiveNode> JYDnodeList = archiveUsingService.queryNodeList(map);
			//map.put("id", id);
			//List<Map<String,Object>> formlist = archiveUsingService.queryFormlist(map,JYDnodeList);
			//2.获取借阅库数据
			Map<String,String> storeMap = new HashMap<String,String>();
			String storeSql  =" and vc_table = 'T_USING_STORE'";
			storeSql +=" and vc_isShow = '1' ";
			storeMap.put("conditionSql", storeSql);
			List<ArchiveNode> storeList = archiveUsingService.queryNodeList(storeMap);
			storeMap.put("formId", id);
			List<String[]> valList = archiveUsingService.getStoreVal(storeList,storeMap);
			int dyys=0;
			int fyys=0;
			int zcys=0;
			int pshts=0;
			int jcts=0;
			String cdnr="";
			String count="";
			if(valList!=null&&valList.size()>0){
				for(int i=0;valList!=null&&i<valList.size();i++){
					for(int j=0;j<storeList.size();j++){
						ArchiveNode node = storeList.get(j);
						if("1".equals(node.getVc_isEdit())){
							String name = node.getVc_name();	
							String value = valList.get(i)[j+1];
							if("打印页数".equals(name)){
								dyys+=toInt(value);
							}else if("复印页数".equals(name)){
								fyys+=toInt(value);
							}else if("摘抄页数".equals(name)){
								zcys+=toInt(value);
							}else if("拍摄画图数".equals(name)){
								pshts+=toInt(value);
							}else if("借出天数".equals(name)){
								jcts+=toInt(value);
							}
							if("档号".equals(node.getVc_name())){
								cdnr+=value+",";
							}
						}else{//非编辑数据
							if("档号".equals(node.getVc_name())){
								cdnr+=valList.get(i)[j+1]+",";
							}
						}
					}	
				}
				count=valList.size()+"";
			}else{
				count="0";
			}
			
			
			//3.修改借阅单的数据
			String updateSql = "";
			for(int j=0;j<JYDnodeList.size();j++){
				ArchiveNode node = JYDnodeList.get(j);
				String column = node.getVc_fielName();
				String name = node.getVc_name();
				String value="";
				if("打印页数".equals(name)){
					value=dyys+"";
					updateSql+=column+"='"+value+"',";
				}else if("复印页数".equals(name)){
					value=fyys+"";
					updateSql+=column+"='"+value+"',";
				}else if("摘抄页数".equals(name)){
					value=zcys+"";
					updateSql+=column+"='"+value+"',";
				}else if("拍摄画图数".equals(name)){
					value=pshts+"";
					updateSql+=column+"='"+value+"',";
				}else if("借出天数".equals(name)){
					value=jcts+"";
					updateSql+=column+"='"+value+"',";
				}else if("查档内容".equals(name)){
					value=cdnr+"";
					updateSql+=column+"='"+value+"',";
				}else if("调阅卷(件)数".equals(name)){
					value=count;
					updateSql+=column+"='"+value+"',";
				}
			}
			if(!"".equals(updateSql)){
				updateSql=updateSql.substring(0,updateSql.length()-1);
				String sql = "update T_USING_FORM set "+updateSql+" where id='"+id+"'";
				archiveUsingService.updateStore(sql);
				System.out.println(sql);
			}
			retrunStr = "success";
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 审核或结束借阅流程
	 */
	public void endBorrow(){
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String id = getRequest().getParameter("id");
			String myd = getRequest().getParameter("myd");
			String cyjg = getRequest().getParameter("cyjg");
			Map<String,String> map = new HashMap<String,String>();
			map.put("tableName", "T_USING_FORM");
			String conditionSql =" and vc_table = 'T_USING_FORM'";
			map.put("conditionSql", conditionSql);
			List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
			String updateSql="";
			for(int i=0;i<nodeList.size();i++){
				if("myd".equals(nodeList.get(i).getVc_metadataname())){
					updateSql+=nodeList.get(i).getVc_fielName()+"='"+myd+"',";
				}
				if("cyjg".equals(nodeList.get(i).getVc_metadataname())){
					updateSql+=nodeList.get(i).getVc_fielName()+"='"+cyjg+"',";
				}
				if("status".equals(nodeList.get(i).getVc_metadataname())){
					updateSql+=nodeList.get(i).getVc_fielName()+"='"+3+"',";
				}
			}
			updateSql=updateSql.substring(0,updateSql.length()-1);
			String sql = "update T_USING_FORM set "+updateSql+" where id='"+id+"'";
			archiveUsingService.updateStore(sql);
			retrunStr = "success";
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 审核借阅单及借阅库内容
	 */
	public void checkBorrow() {
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String id = getRequest().getParameter("id");
			Map<String,String> map = new HashMap<String,String>();
			map.put("tableName", "T_USING_FORM");
			String conditionSql =" and vc_table = 'T_USING_FORM'";
			map.put("conditionSql", conditionSql);
			List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
			String updateSqlF="";
			for(int i=0;i<nodeList.size();i++){
				if("status".equals(nodeList.get(i).getVc_metadataname())){
					updateSqlF+=nodeList.get(i).getVc_fielName()+"='"+2+"',";
					break;
				}
			}
			updateSqlF=updateSqlF.substring(0,updateSqlF.length()-1);
			String sql = "update T_USING_FORM set "+updateSqlF+" where id='"+id+"'";
			archiveUsingService.updateStore(sql);
			
			map.clear();
			map.put("conditionSql", " and vc_table = 'T_USING_STORE'");
			List<ArchiveNode> nodeListS = archiveUsingService.queryNodeList(map);
			String updateSqlS="";
			for(int i=0;i<nodeListS.size();i++){
				if("shr".equals(nodeListS.get(i).getVc_metadataname())) {
					Employee ey = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
					String shr = ey.getEmployeeName();
					updateSqlS += nodeListS.get(i).getVc_fielName()+"='"+shr+"',";
					break;
				}
			}
			updateSqlS=updateSqlS.substring(0,updateSqlS.length()-1);
			String sqlS = "update T_USING_STORE set "+updateSqlS+" where formid='"+id+"'";
			archiveUsingService.updateStore(sqlS);
			
			retrunStr = "success";
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 利用单附件页面
	 * @return
	 */
	public String showFjList(){
		String id = getRequest().getParameter("id");
		Map<String,String> map = new HashMap<String,String>();
		String conditionSql = "";
		 conditionSql +=" and vc_table = 'T_USING_FORM'";
		 conditionSql +=" and vc_isShow = '1' ";
		 map.put("conditionSql", conditionSql);
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		String column =""; 
		for(int i=0;i<nodeList.size();i++){
			if("usingFromId".equals(nodeList.get(i).getVc_metadataname())){
				column=nodeList.get(i).getVc_fielName();
			}
		}
		map.clear();
		map.put("id", id);
		List<Map<String,Object>> formlist = archiveUsingService.queryFormlist(map,nodeList);
		if(formlist!=null&&formlist.size()>0){
			String formbh = formlist.get(0).get(column).toString();
			Map<String,String> map2 = new HashMap<String,String>();
			map2.put("formbh", formbh);
			List<Object[]> list = archiveUsingService.getFjList(map2);
			getRequest().setAttribute("list", list); 
			getRequest().setAttribute("formbh", formbh); 
		}
		
		return "fjList";
	}
	/**
	 * 附件扫描页面
	 * @return
	 */
	public String toScanJsp(){
		String formbh = getRequest().getParameter("formbh");
		String jydfjpath = SystemParamConfigUtil.getParamValueByParam("jyd_file_path");
		getRequest().setAttribute("formbh", formbh); 
		getRequest().setAttribute("jydfjpath", jydfjpath); 
		return "scanJsp";
	}
	
	/**
	 * 保存扫描的附件
	 */
	public void saveJydFj(){
		PrintWriter out = null;
		BASE64Decoder base64Decoder = new BASE64Decoder();
		String retrunStr = "";
		try {
			String formid = getRequest().getParameter("formid");
			String fileName = getRequest().getParameter("fileName");
			String strBase64 = getRequest().getParameter("strBase64");
			//
            if(strBase64.contains("data:image/png;base64,")){
            	strBase64 = strBase64.replace("data:image/png;base64,","");
            }else if(strBase64.contains("data:image/jpg;base64,")){
            	strBase64 = strBase64.replace("data:image/jpg;base64,","");
            }else if(strBase64.contains("data:image/jpeg;base64,")){
            	strBase64 = strBase64.replace("data:image/jpeg;base64,","");
            }else if(strBase64.contains("data:image/gif;base64,")){
            	strBase64 = strBase64.replace("data:image/gif;base64,","");
            }
            byte[] bytes = base64Decoder.decodeBuffer(strBase64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            InputStream input = new ByteArrayInputStream(bytes); 
            FtpUtil.uploadFile(jyd_ftp_ip, Integer.valueOf(jyd_ftp_port), jyd_ftp_name, jyd_ftp_password, jyd_ftp_dirpath, fileName, input);
            
			String sql = "insert into  T_USING_FJ (id,formid,fjpath,filename) values ('"+UuidGenerator.generate36UUID()+"','"+formid+"','"+jyd_ftp_dirpath+fileName+"','"+fileName+"')";
			archiveUsingService.updateStore(sql);
			retrunStr = "success";
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	/**
	 * @return String
	 * @throws IOException 
	 */
	public void download() throws IOException{
		String id = getRequest().getParameter("id");
		List<Object[]>  fjlist = archiveUsingService.getFJById(id);
		String fileName ="";
		if(fjlist!=null&&fjlist.size()>0){
			fileName=fjlist.get(0)[3].toString();
		}
		getResponse().setContentType("application/x-msdownload");
		getResponse().setHeader("Content-Disposition", "attachment; filename="+ fileName);
		FtpUtil.downFile(jyd_ftp_ip, Integer.valueOf(jyd_ftp_port), jyd_ftp_name, jyd_ftp_password, jyd_ftp_dirpath, fileName, getResponse());
	}
	
	
	/**
	 * 删除扫描的附件
	 */
	public void deleteJydFj(){
		PrintWriter out = null;
		String retrunStr = "";
		try {
			String id = getRequest().getParameter("id");
			String sql = "delete  T_USING_FJ  where id in("+id+")";
			archiveUsingService.updateStore(sql);
			retrunStr = "success";
			out = getResponse().getWriter();
			out.print(retrunStr);
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}
	
	
	/**
	 * @return String
	 * @throws IOException 
	 */
	public void printStore() throws IOException{
		String id = getRequest().getParameter("id");
		//显示借阅库信息
		Map<String,String> storeMap = new HashMap<String,String>();
		String storeSql  =" and vc_table = 'T_USING_STORE'";
		storeSql +=" and vc_isShow = '1' ";
		storeMap.put("conditionSql", storeSql);
		//获取借阅库的展示字段
		List<ArchiveNode> storeList = archiveUsingService.queryNodeList(storeMap);
		storeMap.put("formId", id);
		List<String[]> valList = archiveUsingService.getStoreVal(storeList,storeMap);
		

		
		String[] titles = new String[]{"档号","索书号","题名","男方姓名","女方姓名"}; 
		List<String[]> dataSource=new ArrayList<String[]>();
		for(int i=0;i<valList.size();i++){
			String dh ="";
			String ssh ="";
			String tm ="";
			String nanName ="";
			String womanName ="";
			for(int j=0;j<storeList.size();j++){
				if("档号".equals(storeList.get(j).getVc_name())){
					dh=valList.get(i)[j+2];
				}else if("索书号".equals(storeList.get(j).getVc_name())){
					ssh=valList.get(i)[j+2];
				}else if("题名".equals(storeList.get(j).getVc_name())){
					tm=valList.get(i)[j+2];
				}else if("男方姓名".equals(storeList.get(j).getVc_name())){
					nanName=valList.get(i)[j+2];
				}else if("女方姓名".equals(storeList.get(j).getVc_name())){
					womanName=valList.get(i)[j+2];
				}
			}
			
			String[] s= new String[]{dh,ssh,tm,nanName,womanName};
			dataSource.add(s);
		}
		
		ExcelExport e = ExcelExport.getExcelExportInstance("借阅库档号、索书号、题名详情",getResponse());
		e.setBiaoti("借阅库档号、索书号、题名明细");
		e.setAllCelNum(4);
		e.setTitles(titles);
		e.setDataSource(dataSource);
		e.setColWidth(1, 40);
		e.setColWidth(2, 40);
		e.setColWidth(3, 100);
		e.setColWidth(4, 40);
		e.setColWidth(5, 40);
		e.export();
	}
	
	public  int toInt(String a){
		int b=0;
		try {
			if(a==null||"".equals(a)){
				b=0;
			}else{
				b=Integer.valueOf(a);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	//申请借阅的 信息提示
	public void applyForm(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		//修改状态
		String id = getRequest().getParameter("id");
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", "T_USING_FORM");
		String conditionSql =" and vc_table = 'T_USING_FORM'";
		map.put("conditionSql", conditionSql);
		List<ArchiveNode> nodeList = archiveUsingService.queryNodeList(map);
		String updateSqlF="";
		for(int i=0;i<nodeList.size();i++){
			if("status".equals(nodeList.get(i).getVc_metadataname())){
				updateSqlF+=nodeList.get(i).getVc_fielName()+"='"+1+"',";
				break;
			}
		}
		updateSqlF=updateSqlF.substring(0,updateSqlF.length()-1);
		archiveUsingService.updateStore("update T_USING_FORM set "+updateSqlF+" where id='"+id+"'");
		
		//加入申请信息
		ArchiveMsg msg = new ArchiveMsg();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//查询接收人
		String sql = " select a.esidentifier from ESS_PROPVALUE a,ESS_PROP b,ESS_METADATA c where a.id_prop=b.id and b.id_metadata=c.id and c.esidentifier='jdr' and a.esidentifier <> 'Guest'";
		List<String[]> jsrlist = essModelService.queryInfo(sql,1);
		String jsr = "";
		if(jsrlist!=null&&jsrlist.size()>0){
			jsr=jsrlist.get(0)[0];
		}
		msg.setGlid(id);
		msg.setMsgType("1");
		msg.setSender("自主查档");
		msg.setSendTime(sd.format(new Date()));
		msg.setStatus("0");
		msg.setRecevier("");
		msg.setContent_message("您有新的借阅单待审批！");
		archiveUsingService.updateArchiveMsg(msg);
		try {
			MsgWebSocket.onMessage("{\"To\":\"All\",\"msgType\":\"12\"}"); //消息刷新的类型 1:右下角弹框  2：右上角申请中心    12：两者
		} catch (IOException e) {
			e.printStackTrace();
		}
		outWirter("success",getResponse());
		
	}
	//更新借阅申请信息
	public void updateApplyMsg(){
		String id = getRequest().getParameter("id");
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		List<ArchiveMsg> msglist = archiveUsingService.getArchiveMsgList(map,null,null);
		ArchiveMsg msg =msglist.get(0);
		msg.setStatus("1");	
		archiveUsingService.updateArchiveMsg(msg);
	}
	
	
	//调卷单集合页面
	public String showTransferList(){
		String statusSe = getRequest().getParameter("statusSe");
		String content = getRequest().getParameter("content");
		String pagesize = getRequest().getParameter("pageSize");
		String applyFlag = getRequest().getParameter("applyFlag");
		String shFlag = getRequest().getParameter("shFlag");
		
		int pageSize = Integer.parseInt(StringUtils.isNotBlank(pagesize)?pagesize:SystemParamConfigUtil.getParamValueByParam("pagesize"));
		Map<String,String> map = new HashMap<String,String>();
		map.put("statusSe", statusSe);
		map.put("content", content);
		int count = archiveUsingService.getTransferformCount(map);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Transferform> tflist = archiveUsingService.getTransferformList(map, Paging.pageIndex,Paging.pageSize);
		
		getRequest().setAttribute("tflist", tflist);
		getRequest().setAttribute("statusSe", statusSe);
		getRequest().setAttribute("content", content);
		getRequest().setAttribute("applyFlag", applyFlag);
		getRequest().setAttribute("shFlag", shFlag);
		return "transferListJsp";	
	}
	
	//新增调归单
	public String toAddTransferForm(){
		String statusSe = getRequest().getParameter("statusSe");
		getRequest().setAttribute("statusSe", statusSe);
		String id = getRequest().getParameter("id");
		if(id!=null&&!"".equals(id)){//编辑
			Map<String,String> map = new HashMap<String,String>();
			map.put("id", id);
			List<Transferform> tflist = archiveUsingService.getTransferformList(map, null,null);
			if(tflist!=null&&tflist.size()>0){
				getRequest().setAttribute("tf", tflist.get(0));
			}
			getRequest().setAttribute("editFlag", "1");
		}else{//新增
			//生成利用单编号
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String bh = sdf.format(new Date());
			//申请日期
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			String applyTime =  sdf1.format(new Date());
			//接待人
			Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
			String jdr = emp.getEmployeeName();
			Transferform tf = new Transferform();
			tf.setFormId(bh);
			tf.setApplyTime(applyTime);
			tf.setRegistrantName(jdr);
			getRequest().setAttribute("tf", tf);
			getRequest().setAttribute("editFlag", "0");
		}		
		return "transferForm";	
	}
	/**
	 * 展示调阅单
	 */
	public String showTransferForm(){
		String id = getRequest().getParameter("id");
		String gjFlag = getRequest().getParameter("gjFlag");
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		List<Transferform> tflist = archiveUsingService.getTransferformList(map, null,null);
		if(tflist!=null&&tflist.size()>0){
			getRequest().setAttribute("tf", tflist.get(0));
			getRequest().setAttribute("statusSe", tflist.get(0).getFormStatus());
		}
		map.clear();
		map.put("formId", id);
		List<Transferstore> tslist = archiveUsingService.getTransferstoreList(map,null,null);
		getRequest().setAttribute("tslist", tslist);
		getRequest().setAttribute("gjFlag", gjFlag);
		return "transferFormDetail";	
	}
	
	//删除调卷库
	public void deleteDJK(){
		String ids = getRequest().getParameter("ids");
		String deletesql =" delete t_archive_transferstore where id in ("+ids+")";
		archiveUsingService.updateStore(deletesql);
		outWirter("success",getResponse());
	}
	
	//删除调卷单
	public void deleteDJD(){
		String ids = getRequest().getParameter("ids");
		String[] idarr = ids.split(",");
		for(int i=0;i<idarr.length;i++){
			String deletesql =" delete t_archive_transferform where id='"+idarr[i]+"'";
			archiveUsingService.updateStore(deletesql);
			String deletesql2 =" delete t_archive_transferstore where formid='"+idarr[i]+"'";
			archiveUsingService.updateStore(deletesql2);
		}
		outWirter("success",getResponse());
	}
	
	//添加调阅单
	public void addTransferForm(){
		String formId = getRequest().getParameter("formId");
		String applyTime = getRequest().getParameter("applyTime");
		String applyName = getRequest().getParameter("applyName");
		String applyUnit = getRequest().getParameter("applyUnit");
		String transferType = getRequest().getParameter("transferType");
		String registrantName = getRequest().getParameter("registrantName");
		Transferform tf = new Transferform();
		tf.setFormId(formId);
		tf.setApplyTime(applyTime);
		tf.setApplyUnit(applyUnit);
		tf.setRegistrantName(registrantName);
		tf.setTransferType(transferType);
		tf.setApplyName(applyName);
		tf.setFormStatus("1");
		archiveUsingService.updateTransferForm(tf);
		outWirter(tf.getId(),getResponse());
	}
	
	//修改调阅单updateTransferForm
	public void updateTransferForm(){
		Employee emp = (Employee) getSession().getAttribute(MyConstants.loginEmployee);
		String oprateType = getRequest().getParameter("oprateType");//sh:审核  gj：归卷
		
		String id = getRequest().getParameter("id");
		String formId = getRequest().getParameter("formId");
		String applyTime = getRequest().getParameter("applyTime");
		String applyName = getRequest().getParameter("applyName");
		String applyUnit = getRequest().getParameter("applyUnit");
		String transferType = getRequest().getParameter("transferType");
		String registrantName = getRequest().getParameter("registrantName");
		String returnPeople = getRequest().getParameter("returnPeople");
		String returnTime = getRequest().getParameter("returnTime");
		String getPeople = getRequest().getParameter("getPeople");
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		List<Transferform> tflist = archiveUsingService.getTransferformList(map, null,null);
		if(tflist!=null&&tflist.size()>0){
			Transferform tf = tflist.get(0);
			if("sh".equals(oprateType)){
				tf.setFormStatus("2");
			}else if("gj".equals(oprateType)){
				tf.setReturnPeople(returnPeople);
				tf.setReturnTime(returnTime);
				tf.setGetPeople(getPeople);
				tf.setFormStatus("3");
			}else{
				tf.setFormId(formId);
				tf.setApplyTime(applyTime);
				tf.setApplyUnit(applyUnit);
				tf.setRegistrantName(registrantName);
				tf.setTransferType(transferType);
				tf.setApplyName(applyName);
				tf.setFormStatus("1");
				//发送短消息
				ArchiveMsg msg = new ArchiveMsg();
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
				msg.setGlid(id);
				msg.setMsgType("2");//1：借阅申请  2：调卷申请  3：  系统消息
				msg.setSender(emp.getEmployeeName());
				msg.setSendTime(sd.format(new Date()));
				msg.setStatus("0");
				msg.setRecevier("");
				msg.setContent_message("您有新的调卷单待审批！");
				archiveUsingService.updateArchiveMsg(msg);
				try {
					MsgWebSocket.onMessage("{\"To\":\"All\",\"msgType\":\"12\"}"); //1:右下角  2：右上角    12：两者
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			archiveUsingService.updateTransferForm(tf);
		}
		outWirter("success",getResponse());
	}
	
	public void outWirter(String data,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin","*");  
		response.setHeader("Access-Control-Allow-Credentials","true");  
		response.setHeader("Cache-Control","no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(out!=null) {
				out.close();				
			}
		}
	}
}