package cn.com.trueway.workflow.core.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.JDBCBase;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.comment.service.CommentService;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.dao.TemplateDao;
import cn.com.trueway.workflow.core.pojo.DoFile;
import cn.com.trueway.workflow.core.pojo.WfItem;
import cn.com.trueway.workflow.core.pojo.WfLabel;
import cn.com.trueway.workflow.core.pojo.WfTemplate;
import cn.com.trueway.workflow.core.service.ItemService;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.TemplateService;
import cn.com.trueway.workflow.set.util.QrcodeUtil;

public class TemplateServiceImpl implements TemplateService {

	private TemplateDao templateDao;
	private TableInfoService tableInfoService;
	private CommentService commentService;
	private ItemService itemService;
	
	private final String QRCODE_FILE_PATH		= "qrcode/";
	
	private final String QRCODE_FILE_TYPE		= "jpg";
	
	
	
	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}
	
	public JDBCBase jdbcBase;

	public JDBCBase getJdbcBase() {
		return jdbcBase;
	}

	public void setJdbcBase(JDBCBase jdbcBase) {
		this.jdbcBase = jdbcBase;
	}

	public Integer getTemplateCountForPage(String column, String value,
			WfTemplate template){
		return templateDao.getTemplateCountForPage(column, value, template);
	}
	
	public List<WfTemplate> getTemplateListForPage(String column, String value,
			WfTemplate template, Integer pageindex, Integer pagesize){
		return templateDao.getTemplateListForPage(column, value, template, pageindex, pagesize);
	}
	
	public List<WfTemplate> getAllTemplateListNotLc(String lcid,String isRedTape){
		return templateDao.getAllTemplateListNotLc(lcid,isRedTape);
	}

	public WfTemplate addTemplate(WfTemplate wfTemplate) {
		return templateDao.addTemplate(wfTemplate);
	}
	
	public boolean delTemplate(WfTemplate wfTemplate){
		templateDao.delTemplate(wfTemplate);
		return true;
	}
	
	public WfTemplate getTemplateById(String id){
		return templateDao.getTemplateById(id);
	}
	
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public List<WfTemplate> getTemplateByLcid(String lcid){
		List<WfTemplate> list = templateDao.getTemplateByLcid(lcid);
		if(list != null && list.size() > 0){
			for(WfTemplate template : list){
				template.setAttflow(null);
			}
		}
		return list;
	}
	
	/**
	 * 根据书签等匹配关系,instanceId获取书签与值的keyValue关系
	 * @param list
	 * @param infoid
	 * @return
	 */
	public Map<String, String> getTemplateValue(List<WfLabel> list, String infoid, String fileId){
		Map<String, String> map = new HashMap<String, String>();
		String tablename = "";
		String fields = "";
		for(WfLabel label : list){ 
			if (CommonUtil.stringNotNULL(label.getVc_type())) {
				String type=label.getVc_type();
				if (type.equals("1")) {//普通标签
					if (CommonUtil.stringNotNULL(label.getVc_tablename())&&CommonUtil.stringNotNULL(label.getVc_fieldname())) {
						//普通标签  根据表名和字段名查询值
						tablename = label.getVc_tablename();
						fields = label.getVc_fieldname();
						String data = "";
						data = getClob(tablename,infoid,fields);
						map.put(label.getVc_label(), data);
					}
				}else if (type.equals("3")) {//附件标签(标题)
					String hql="from SendAttachments s where s.docguid='"+infoid+label.getVc_att()+"'";
					List<SendAttachments> attList=templateDao.getHqlQuery(hql);
					String value="";
					if (attList!=null&&attList.size()>0) {
						for (int i = 0; i < attList.size(); i++) {
							value+=i==attList.size()-1?attList.get(i).getFilename():attList.get(i).getFilename()+";";//\n默认为word文档中的换行符 注意替换
						}
					}
					map.put(label.getVc_label(), value);
				}else if (type.equals("6")) {//普通列表标签
					String fieldInfo=label.getVc_list();
					if (CommonUtil.stringNotNULL(fieldInfo)) {
						tablename = fieldInfo.split(",")[0];
						fields = fieldInfo.split(",")[1];
						List<String> datas = getClob1(tablename,infoid,fields);
						if (datas!=null&&datas.size()>0) {
							//普通列表特殊处理
							String str=label.getVc_label();
							String bj=str.split("_")[0];
							String column=str.split("_")[1];
							String max=str.split("_")[2];
							
							//普通列表标签为准
							Integer index=Integer.parseInt(max);
							for (int i = 0; i <= index; i++) {
								String key=bj+"_"+column+"_"+i;
								String value="";
								if (i<=datas.size()-1) {
									value=datas.get(i);
								}
								map.put(key, value);
							}
						}
					}
				}else if(type.equals("7")){//附件标签(内容)
					// 将 word 内容 读到 string 里面去
					String hql="from SendAttachments s where s.docguid='"+infoid+label.getVc_att()+"'";
					List<SendAttachments> attList=templateDao.getHqlQuery(hql);
					//_attachmentDoc
					String value="";
					if (attList!=null&&attList.size()>0) {
						for (int i = 0; i < attList.size(); i++) {
							String temp=attList.get(i).getLocalation();
							if (CommonUtil.stringNotNULL(temp)) {
								temp=temp.split("/")[4];
							}
							value+=i==attList.size()-1?temp:temp+";";
						}
					}
					if(!"".equals(value)){
						map.put(label.getVc_label()+"_attachmentDoc", value);//附件图片特殊处理标记
					}
					
				}else if (type.equals("4")) {//附件标签(图片)
					String hql="from SendAttachments s where s.docguid='"+infoid+label.getVc_att()+"'";
					List<SendAttachments> attList=templateDao.getHqlQuery(hql);
					String value="";
					if (attList!=null&&attList.size()>0) {
						for (int i = 0; i < attList.size(); i++) {
							String temp=attList.get(i).getLocalation();
							if (CommonUtil.stringNotNULL(temp)) {
								temp=temp.split("/")[4];
							}
							value+=i==attList.size()-1?temp:temp+";";
						}
					}
					map.put(label.getVc_label()+"_attachmentPic", value);//附件图片特殊处理标记
				}else if(type.equals("8")){//正文标签(内容)
					// 将 word 内容 读到 string 里面去
					String hql = "";
					if(StringUtils.isNotBlank(fileId)){
						hql="from SendAttachments s where s.id='"+fileId+"'";
					}else{
						hql="from SendAttachments s where s.docguid='"+infoid+"fj'";
					}
					List<SendAttachments> attList=templateDao.getHqlQuery(hql);
					String value="";
					if (attList!=null && attList.size()>0) {
						if(attList!=null && attList.size()>0){
							value = attList.get(0).getLocalation();
						}
					}
					if(!"".equals(value)){
						map.put(label.getVc_label()+"_attachmentDoc", value);//附件图片特殊处理标记
					}
				}else if(type.equals("9")){//发改委定制办结添加二维码
					DoFile doFile = tableInfoService.getDoFileByElements("",infoid);
					if(doFile!=null && doFile.getQrcodePath()!=null && !doFile.getQrcodePath().equals("")){
						map.put(label.getVc_label()+"_qrcodeImg", doFile.getQrcodePath());//二维码图片特殊
					}
				}
			}
		}
		return map;
	}
	/*
	 * 获取clob字段
	 * */
	public String getClob(String tablename, String instanceid, String fieldname){
		PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    String rtn = "";
	    try {
		Connection conn = jdbcBase.getCon();
	    String sql = "select "+fieldname + " from " + tablename + " where instanceid='" + instanceid + "'";
	    pstmt = conn.prepareStatement(sql);
	    rs = pstmt.executeQuery();
	    java.sql.Clob clob = null;
	    if (rs.next()) {
	        clob = rs.getClob(fieldname);
	        if(clob != null){
	        	rtn = clob.getSubString((long)1,(int)clob.length());
	        }else{
	        	rtn = "";
	        }
	    }
	    }catch (Exception e) {
//			e.printStackTrace();
			
			//如果报错,则为非clob字段执行普通方法获取
			String sql = "select "+fieldname+" from "+tablename+" where INSTANCEID='"+instanceid+"'";
			List<Object> resultList = templateDao.getSqlQuery(sql);
			String data = "";
			if(resultList != null && resultList.size() > 0){
				if(resultList.get(0) != null && !("").equals(resultList.get(0))&& !("NULL").equals(resultList.get(0).toString().toUpperCase())){
					data = resultList.get(0).toString();
				}
			}
			//主送、抄送只取名称
			if(data!=null && !("").equals(data)){
				if(data.indexOf("*") > 0){
					data = data.split("\\*")[1];
					data = data.replaceAll(",", "，");
				}
			}
			return data;
		}finally{
			try {
	             rs.close();
	             rs=null;
	             pstmt.close();
	             pstmt=null;
	         } catch (SQLException ex) {
	         }
		}
	    return rtn;
	}
	/*
	 * 获取clob字段
	 * */
	public List<String> getClob1(String tablename, String instanceid, String fieldname){
		PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    List<String> rtn = new ArrayList<String>();
	    try {
		Connection conn = jdbcBase.getCon();
	    String sql = "select "+fieldname + " from " + tablename + " where instanceid='" + instanceid + "'";
	    pstmt = conn.prepareStatement(sql);
	    rs = pstmt.executeQuery();
	    java.sql.Clob clob = null;
	    while (rs.next()) {
	    	clob = rs.getClob(fieldname);
	        if(clob != null){
	        	rtn.add(clob.getSubString((long)1,(int)clob.length()));
	        }
			
		}
	    }catch (Exception e) {
//			e.printStackTrace();
			
			//如果报错,则为非clob字段执行普通方法获取
			String sql = "select "+fieldname+" from "+tablename+" where INSTANCEID='"+instanceid+"'";
			List<Object> resultList = templateDao.getSqlQuery(sql);
			List<String> data = new ArrayList<String>();
			if(resultList != null && resultList.size() > 0){
				for (int i = 0; i < resultList.size(); i++) {
					data.add(resultList.get(i).toString());
				}
			}
			
			return data;
		}finally{
			try {
	             rs.close();
	             rs=null;
	             pstmt.close();
	             pstmt=null;
	         } catch (SQLException ex) {
	         }
		}
	    return rtn;
	}
	
	public String getAllMeta(String content){
		if(content == null || "".equals(content.trim())){
			return null;
		}
		String regEx = "\\{\\{[^\\}\\{]*\\}\\}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content);
		String tag = "";
		String all = "";
		while(m.find()){
			//标签和内容
			tag = m.group();
			if(tag != null && tag.length() > 4){
				tag = tag.substring(2,tag.length()-2);
				all += tag + ",";
			}
		}
		if(all.length() > 1){
			all = all.substring(0, all.length() - 1);
		}
		return all;
	}
	
	public static void main(String[] args){
//		String regEx = "<!--\\d+\\u002e\\d+-->";
//		regEx = "<(.[^>]*)>";
//		String content = "aaaaaaaaa<!--aa.qwe-->aaaa<!--bb-->aaaaa";
//		Pattern p = Pattern.compile(regEx);
//		Matcher m = p.matcher(content);
//		String tag = "";
//		while(m.find()){
//			tag = m.group();			//标签和内容
//			System.out.println("TemplateServiceImpl.main()"+tag);
//		}
		
		String regEx = "\\{\\{[^\\}\\{]*\\}\\}";
		String content = "aaaaaaaaa{{aa.qwe}}aaaa{{bb}}aaaaa";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content);
		String tag = "";
		while(m.find()){
			tag = m.group();			//标签和内容
			System.out.println("TemplateServiceImpl.main()"+tag);
		}
		
	}

	@Override
	public List<WfTemplate> findWfTemplateList(String ids, String tempType) {
		return templateDao.findWfTemplateList(ids, tempType);
	}
	
	@Override
	public List<WfTemplate> getTemplateBySql(String conditionSql) {
		return templateDao.getTemplateBySql(conditionSql);
	}
	
	/**
	 * 根据书签等匹配关系,instanceId获取书签与值的keyValue关系
	 * @param list
	 * @param infoid
	 * @param fileId
	 * @param serverUrl
	 * @return
	 */
	public Map<String, String> getTemplateValue(List<WfLabel> list, String infoid, String fileId,String serverUrl){
		Map<String, String> map = new HashMap<String, String>();
		String tablename = "";
		String fields = "";
		for(WfLabel label : list){ 
			if (CommonUtil.stringNotNULL(label.getVc_type())) {
				String type=label.getVc_type();
				if (type.equals("1")) {//普通标签
					if (CommonUtil.stringNotNULL(label.getVc_tablename())&&CommonUtil.stringNotNULL(label.getVc_fieldname())) {
						//普通标签  根据表名和字段名查询值
						tablename = label.getVc_tablename();
						fields = label.getVc_fieldname();
						String data = "";
						data = getClob(tablename,infoid,fields);
						map.put(label.getVc_label(), data);
					}
				}else if (type.equals("3")) {//附件标签(标题)
					String hql="from SendAttachments s where s.docguid='"+infoid+label.getVc_att()+"'";
					List<SendAttachments> attList=templateDao.getHqlQuery(hql);
					String value="";
					if (attList!=null&&attList.size()>0) {
						for (int i = 0; i < attList.size(); i++) {
							value+=i==attList.size()-1?attList.get(i).getFilename():attList.get(i).getFilename()+";";//\n默认为word文档中的换行符 注意替换
						}
					}
					map.put(label.getVc_label(), value);
				}else if (type.equals("6")) {//普通列表标签
					String fieldInfo=label.getVc_list();
					if (CommonUtil.stringNotNULL(fieldInfo)) {
						tablename = fieldInfo.split(",")[0];
						fields = fieldInfo.split(",")[1];
						List<String> datas = getClob1(tablename,infoid,fields);
						if (datas!=null&&datas.size()>0) {
							//普通列表特殊处理
							String str=label.getVc_label();
							String bj=str.split("_")[0];
							String column=str.split("_")[1];
							String max=str.split("_")[2];
							
							//普通列表标签为准
							Integer index=Integer.parseInt(max);
							for (int i = 0; i <= index; i++) {
								String key=bj+"_"+column+"_"+i;
								String value="";
								if (i<=datas.size()-1) {
									value=datas.get(i);
								}
								map.put(key, value);
							}
						}
					}
				}else if(type.equals("7")){//附件标签(内容)
					// 将 word 内容 读到 string 里面去
					String hql="from SendAttachments s where s.docguid='"+infoid+label.getVc_att()+"'";
					List<SendAttachments> attList=templateDao.getHqlQuery(hql);
					//_attachmentDoc
					String value="";
					if (attList!=null&&attList.size()>0) {
						for (int i = 0; i < attList.size(); i++) {
							String temp=attList.get(i).getLocalation();
							if (CommonUtil.stringNotNULL(temp)) {
								temp=temp.split("/")[4];
							}
							value+=i==attList.size()-1?temp:temp+";";
						}
					}
					if(!"".equals(value)){
						map.put(label.getVc_label()+"_attachmentDoc", value);//附件图片特殊处理标记
					}
					
				}else if (type.equals("4")) {//附件标签(图片)
					String hql="from SendAttachments s where s.docguid='"+infoid+label.getVc_att()+"'";
					List<SendAttachments> attList=templateDao.getHqlQuery(hql);
					String value="";
					if (attList!=null&&attList.size()>0) {
						for (int i = 0; i < attList.size(); i++) {
							String temp=attList.get(i).getLocalation();
							if (CommonUtil.stringNotNULL(temp)) {
								temp=temp.split("/")[4];
							}
							value+=i==attList.size()-1?temp:temp+";";
						}
					}
					map.put(label.getVc_label()+"_attachmentPic", value);//附件图片特殊处理标记
				}else if(type.equals("8")){//正文标签(内容)
					// 将 word 内容 读到 string 里面去
					String hql = "";
					if(StringUtils.isNotBlank(fileId)){
						hql="from SendAttachments s where s.id='"+fileId+"'";
					}else{
						hql="from SendAttachments s where s.docguid='"+infoid+"fj'";
					}
					List<SendAttachments> attList=templateDao.getHqlQuery(hql);
					String value="";
					if (attList!=null && attList.size()>0) {
						if(attList!=null && attList.size()>0){
							value = attList.get(0).getLocalation();
						}
					}
					if(!"".equals(value)){
						map.put(label.getVc_label()+"_attachmentDoc", value);//附件图片特殊处理标记
					}
				}else if(type.equals("9")){//发改委定制办结添加二维码
					DoFile doFile = tableInfoService.getDoFileByElements("",infoid);
					if(doFile!=null && doFile.getQrcodePath()!=null && !doFile.getQrcodePath().equals("")){
						map.put(label.getVc_label()+"_qrcodeImg", serverUrl+"/form/html/workflow/"+doFile.getQrcodePath());//二维码图片特殊
					}else if(doFile!=null&&StringUtils.isBlank(doFile.getQrcodePath())){
						try{
							String fgw_fw_item = SystemParamConfigUtil.getParamValueByParam("fgw_fw_item");
							WfItem item = itemService.getItemById(doFile.getItemId());
							if(fgw_fw_item!=null && fgw_fw_item.contains(item.getId())){
								String pdfRoot = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");	
								String qrcodePath = FileUploadUtils.getRealFolderPath(pdfRoot, QRCODE_FILE_PATH); // 得到上传文件在服务器上存储的唯一路径,并创建存储目录
								String imgPath = pdfRoot+qrcodePath+UuidGenerator.generate36UUID()+"."+QRCODE_FILE_TYPE;
								String url = "/table_getOfficeInfoByQrcode.do?instanceId="+infoid;
								boolean isSuccess = QrcodeUtil.encoderQRCode(serverUrl+url, imgPath, QRCODE_FILE_TYPE, 16);
								String path = "";
								if(isSuccess){
									path = imgPath.contains(pdfRoot)?imgPath.substring(pdfRoot.length()):imgPath;
									doFile.setQrcodePath(path);
									templateDao.updateDoFile(doFile);
								}
								map.put(label.getVc_label()+"_qrcodeImg", serverUrl+"/form/html/workflow/"+path);//二维码图片特殊
							}
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return map;
	}
}
