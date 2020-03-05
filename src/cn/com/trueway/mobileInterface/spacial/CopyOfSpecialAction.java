package cn.com.trueway.mobileInterface.spacial;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.FileUploadUtils;
import cn.com.trueway.base.util.UuidGenerator;
import cn.com.trueway.document.component.taglib.attachment.model.po.SendAttachments;
import cn.com.trueway.document.component.taglib.attachment.service.AttachmentService;
import cn.com.trueway.mobileInterface.spacial.bean.SpecialFj;
import cn.com.trueway.mobileInterface.spacial.bean.SpecialInfo;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.core.pojo.WfFieldInfo;
import cn.com.trueway.workflow.core.pojo.WfMain;
import cn.com.trueway.workflow.core.pojo.WfTableInfo;
import cn.com.trueway.workflow.core.service.TableInfoService;
import cn.com.trueway.workflow.core.service.WorkflowBasicFlowService;

/**
 * 专项办手机接口
 * @author Administrator
 *
 */
public class CopyOfSpecialAction extends BaseAction{
	private SpecialService specialService;
	private TableInfoService tableInfoService;
	private WorkflowBasicFlowService workflowBasicFlowService;
	private AttachmentService attachmentService;
	public SpecialService getSpecialService() {
		return specialService;
	}

	public void setSpecialService(SpecialService specialService) {
		this.specialService = specialService;
	}

	public TableInfoService getTableInfoService() {
		return tableInfoService;
	}

	public void setTableInfoService(TableInfoService tableInfoService) {
		this.tableInfoService = tableInfoService;
	}

	public WorkflowBasicFlowService getWorkflowBasicFlowService() {
		return workflowBasicFlowService;
	}

	public void setWorkflowBasicFlowService(
			WorkflowBasicFlowService workflowBasicFlowService) {
		this.workflowBasicFlowService = workflowBasicFlowService;
	}

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	/**
	 * 上传附件
	 */
	public void uploadFile(){
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		try {
			InputStream in = getRequest().getInputStream();	//文件流
			String bathPath = SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
			int year = new Date().getYear();
			int month = new Date().getMonth();
			int day = new Date().getDate();
			String fname = UuidGenerator.generate36UUID()+".tmp";
			String locPath = "attachments"+File.separator+year+File.separator+month+File.separator+day;
			String folder = bathPath+locPath;
			//上传附件
			boolean flag = UploadFileUtil.doUploadStreamWrite(in, folder, fname);
			if(flag){ 
				SendAttachments att = new SendAttachments();
				att.setLocalation(locPath+File.separator+fname);	//文件路径
				att.setFiletime(new Date());						//上传时间
				File upFile = new File(folder+File.separator+fname);
				if(upFile.exists()){
					att.setFilesize(upFile.length());				//文件大小
				}
				att.setDocguid("-1");
				att.setFilename("0");
				att.setFiletype("0");
				//保存附件信息
				attachmentService.addSendAtts(att);			
//				System.out.println(att.getId());
				out.print("'success':'0','fileid':'"+att.getId()+"'");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			out.print("'success':'0','fileid':''");
		}
	}
	
	/**
	 * 专项办申请
	 */
	public void saveSpecialApply(){
		PrintWriter out = null;
		try {
			out = getResponse().getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		try {
			out = getResponse().getWriter();
			String formJson = URLDecoder.decode(getRequest().getHeader("FORMJSON"),"UTF-8");
			String imageJson = URLDecoder.decode(getRequest().getHeader("FILES"),"UTF-8");
			// 取得客户端传的数据流
			InputStream iStream = getRequest().getInputStream();
			// 转化为byte
			byte[] imgData = readStream(iStream);
			
//			JSONObject json = getJSONObject();
			JSONObject json = JSONObject.fromObject(formJson);
//			JSONObject json = json2;
			String empId = (String) json.get("USERID");							//用户id
			
			String zxbdw = (String) json.get("ZXBDW");	//专项办单位 0：环整办 1：渣土办 2：废整办 3：城管办 4：广告办 5：控违办 6：弄环办
			String jbdw = (String) json.get("JBDW");	//交办单位
			String ly = (String) json.get("LY");		//来源
			String dd = (String) json.get("DD");		//地点
			String flfg = (String) json.get("FLFG");	//法律法规
//			String flfg = "法律法规";	//法律法规
			String jbsxms = (String) json.get("ZXBDW"); //交办事项概述
			String zgyq	= (String) json.get("ZGYQ");	//整改要求
			String zgsj = (String) json.get("ZGSJ");	//整改时间
			String lxr = (String) json.get("LXR");		//联系人
			String lxdh = (String) json.get("LXDH");	//联系电话
			String sj = (String) json.get("SJ");		//时间
//			JSONArray jsonArray = JSONArray.fromObject(json.get("FJ"));	//附件列表 
//			List<SpecialFj> fjList = JSONArray.toList(jsonArray, new SpecialFj(), new JsonConfig());
			SpecialInfo info = new SpecialInfo( zxbdw, jbdw, ly, dd,flfg, jbsxms, zgyq, zgsj, lxr,lxdh, sj);
			
			String processId = UuidGenerator.generate36UUID();
			String instanceId = UuidGenerator.generate36UUID();
			String nodeId = SystemParamConfigUtil.getParamValueByParam("dbNodeId");			//"393692F1-8144-4F5A-A0DF-BBBE7F78D864";//所在节点id
			String itemId = SystemParamConfigUtil.getParamValueByParam("dbItemId");			//"72D7113D-03B3-47CA-A6E2-4E5DA972A7C9";//事项id
			Object[] dbInfo = tableInfoService.getToDbInfoIds(nodeId);//查找流程id、表单id
			if(dbInfo==null){
				System.out.println("!!! 您配置的节点id或事项id有误!!!");
				out.print("{'success':'0'}");
				return ;
			}
			String formId = dbInfo[0].toString();		//	"6ABEF587-85D8-451C-9419-781FE94A11BF";
			String workFlowId = dbInfo[1].toString();	//	"C306DB74-4185-4369-85B7-1D3A76FD9F5C";
			
			//保存表单数据
			specialService.addSpecialInfo(info,instanceId,formId,workFlowId,processId,zxbdw);
			//保存流程数据
			String title = getTitle( workFlowId, formId, instanceId);	//流程标题
			specialService.addProcess(workFlowId,instanceId,itemId,nodeId,formId,processId,title,empId);
			
			//保存附件数据
			if(imgData.length > 0){
				int year = new Date().getYear();
				int month = new Date().getMonth();
				int day = new Date().getDate();
				
				JSONArray jsonArr = JSONArray.fromObject(imageJson);
				
				String kzm = "";
				for(int i=0;i<=jsonArr.size();i++){
					JSONObject jsonF = jsonArr.getJSONObject(0);
					if("image/jpeg".equals(jsonF.getString("type"))){
						kzm = ".jpg";
					}else if("image/png".equals(jsonF.getString("type"))){
						kzm = ".png";
					}else{
						kzm = ".jpg";
					}
					String basePath =  SystemParamConfigUtil.getParamValueByParam("workflow_file_path");
					String dstFolder ="attachments"+File.separator+year+File.separator+month+File.separator+day;
					String fname = UuidGenerator.generate36UUID()+kzm;
					//保存文件
					FileUploadUtils.getFile(imgData, basePath + dstFolder+File.separator+fname, basePath+dstFolder);
					
					SendAttachments att = new SendAttachments();
					att.setLocalation(dstFolder+File.separator+fname);	//文件路径
					att.setFiletime(new Date());						//上传时间
					File upFile = new File(basePath + dstFolder+File.separator+fname);
					if(upFile.exists()){
						att.setFilesize(upFile.length());				//文件大小
					}
					att.setDocguid(instanceId);
					att.setFilename(fname);
					att.setFiletype(kzm);
					//保存附件信息
					attachmentService.addSendAtts(att);	
				}
			}
//			for(SpecialFj fj : fjList){
//				SendAttachments att = new SendAttachments();
//				att.setId(fj.getFj_id());
//				att.setDocguid(instanceId+"fj");
//				att.setFilename(fj.getFj_name());
//				att.setFiletype(fj.getFj_name().substring(fj.getFj_name().lastIndexOf(".")));
//				att.setFileindex(0L);
//				att.setEditer(empId+";"+"拟稿人");
//				att.setTitle(fj.getFj_name());
//				attachmentService.updateSendAtt(att);
//			}
			out.print("{'success':'1'}");
		} catch (Exception e) {
			out.print("{'success':'0'}");
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
		}
		
	}
	
	private String getTitle(String workFlowId,String formId,String instanceId){
		WfMain wf_main = workflowBasicFlowService.getWfMainById(workFlowId);
		String title = getTitle(wf_main, instanceId);//{标题}test---找到手动输入的值
		String name = tableInfoService.getTableAndColumnName(wf_main.getWfm_title_column());//tableName;columnName#tableName;columnName
		//查找列名,用于页面判断
		String columnName = ""; 
		String[] names = name.split("#");
		for (String str : names) {
			columnName += str.split(";")[1]+","; 
		}
		if(!("").equals(columnName) && columnName.length() > 0){
			columnName = columnName.substring(0,columnName.length()-1);
		}
		return title+";"+columnName;
	}
	
	public String getTitle(WfMain wfMain,String instanceId){
		String str=wfMain.getWfm_title_name();
		String tablename=wfMain.getWfm_title_table();
		String names=wfMain.getWfm_title_name();
		String ids=wfMain.getWfm_title_column();
		
		
		WfTableInfo table=tableInfoService.getTableById(tablename);
		if (table!=null) {
			tablename=table.getVc_tablename();
		}
		//解析标题字符串     2013年{姓名}3月{性别}4日
		List<String> cloumnNames=new ArrayList<String>();
		
		String regEx = "\\{[^\\}\\{]*\\}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(names);
		String tag = "";
		String all = "";
		while(m.find()){
			//标签和内容
			tag = m.group();
			if(tag != null && !tag.equals("")){
				tag = tag.substring(1,tag.length()-1);
				cloumnNames.add(tag);
			}
		}
		String[] idsArr=ids==null?null:ids.split(",");
		
		if (cloumnNames!=null) {
			for (int i = 0; i < cloumnNames.size(); i++) {
				WfFieldInfo filed=tableInfoService.getFieldById(idsArr[i]);
				String sql="select "+filed.getVc_fieldname()+",'替代' from "+tablename+" t where t.INSTANCEID='"+instanceId+"'";
				List<Object[]> list=tableInfoService.getListBySql(sql);
				String value="";
				if (list!=null&&list.size()>0) {
					value=list.get(0)[0].toString();
				}
				str=str.replace("{"+cloumnNames.get(i)+"}", value);
				
			}
		}
		
		return str;
	}
	
	/**
	 * 处理流
	 * @param is
	 * @return
	 * @throws Exception
	 */
	private static byte[] readStream(InputStream is) throws Exception {
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
	 * 转换jsonobject
	 * @return
	 */
	private JSONObject getJSONObject(){
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			return JSONObject.fromObject(new String (data,"UTF-8"));
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
	
}
