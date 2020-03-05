package cn.com.trueway.document.component.taglib.docNumber.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.trueway.client.util.CommonUtils;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.MyConstants;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumOnlyWh;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberStrategy;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhBw;
import cn.com.trueway.document.component.docNumberManager.model.po.DocNumberWhFw;
import cn.com.trueway.document.component.taglib.docNumber.service.DocNumberService;
import cn.com.trueway.workflow.core.pojo.Department;
import cn.com.trueway.workflow.set.service.DepartmentService;

public class DocNumberAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2119747566678652553L;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private DocNumberService  docNumberService;
	
	private DepartmentService departmentService;
	
	public DocNumberService getDocNumberService() {
		return docNumberService;
	}

	public void setDocNumberService(DocNumberService docNumberService) {
		this.docNumberService = docNumberService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
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
	
	private JSONObject getJSONObject() {
		InputStream iStream = null;
		try {
			iStream = getRequest().getInputStream();
			byte[] data = readStream(iStream);
			if(data.length  >0 ){
				return JSONObject.fromObject(new String(data, "utf-8"));
			}else{
				return null;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public String toDocNumberModel(){
		JSONObject jsonObject  = getJSONObject();
		String webid = "";
		String defineId ="";
		String value = "";
		String isChildWf = "";
		String itemId = "";
		String isMobile="";
		boolean display = false;
		if(jsonObject != null && !"".equals(jsonObject)){
			try {
				display = true;
				webid = jsonObject.getString("webId");
				defineId = jsonObject.getString("workflowId");
				value = jsonObject.getString("value");
				isChildWf = jsonObject.getString("isChildWf");
				itemId = jsonObject.getString("itemId");
				isMobile = jsonObject.getString("isMobile");			
			} catch (Exception e) {
			}
		}else{
			display = true;
			webid = getRequest().getParameter("webid");
			defineId = getRequest().getParameter("defineId");
			value = getRequest().getParameter("value");
			isChildWf = getRequest().getParameter("isChildWf");
			itemId = getRequest().getParameter("itemId");
			isMobile = getRequest().getParameter("isMobile");			
		}
		
		
		if(webid == null || ("").equals(webid) || ("null").equals(webid)){
			webid = (String) getSession().getAttribute("webId");
		}
		if(webid == null || ("").equals(webid) || ("null").equals(webid)){
			webid = 	getRequest().getParameter("webId");
		}
		if(defineId == null || ("").equals(defineId) || ("null").equals(defineId)){
			defineId = getRequest().getParameter("workflowId");
			//用于pdf中打开文号 不显示确定按钮
		}
		if(value == null || ("").equals(value) || ("null").equals(value)){
			value = "";
		}
		//int a = value.length();
		List<String[]> list = docNumberService.getBindWhModelByWebid(webid, defineId, value);
		
		getRequest().setAttribute("value", value);
		getRequest().setAttribute("defineId", defineId);
		getRequest().setAttribute("list", list);
		if(list!=null&&list.size()>0){
			getRequest().setAttribute("firstValue", list.get(0));
		}
		getRequest().setAttribute("display", display);
		getRequest().setAttribute("isChildWf", isChildWf);
		getRequest().setAttribute("itemId", itemId);
		getRequest().setAttribute("webId", webid);
		getRequest().setAttribute("isMobile", isMobile);
		if(CommonUtils.isNotBlank(isMobile)){
			return "docModelOfMobile";
		}
		return "docModel";
	}
	
	public void paserModel() throws IOException{
		String model = getRequest().getParameter("dnmodel");
		String isMobile = getRequest().getParameter("isMobile");
		PrintWriter pw = getResponse().getWriter();
		if(CommonUtil.stringNotNULL(isMobile)&&"1".equals(isMobile)){
			pw.print(docNumberService.paserModelByMobile(model));
		}else{
			pw.print(docNumberService.paserModel(model));
		}
		pw.close();
	}
	
	@SuppressWarnings("unchecked")
	public void getNum() throws IOException{
		String defineId = getRequest().getParameter("defid");
		String gjdz = getRequest().getParameter("gjdz");
		String yearNum = getRequest().getParameter("yearNum");
		String lwdwlx = getRequest().getParameter("lwdwlx");
		String isChildWf = getRequest().getParameter("isChildWf");
		String itemId = getRequest().getParameter("itemId");
		List<String> depIds = (List<String>)getSession().getAttribute(MyConstants.DEPARMENT_IDS);
		String webId = "";
		if(depIds == null ||depIds.size() == 0){
			webId = getRequest().getParameter("webid");
			// 如果将他作为父部门 其下没有子部门 则 取他的父部门 ，否则 取当前部门
			if(webId != null  && !"".equals(webId)){
				Collection<Department> depts =	departmentService.findDepartments(webId);
				if(depts  == null||depts.size() ==0){
					webId = departmentService.findDepartmentById(webId).getSuperiorGuid();
				}
			}
			
		}else{
			webId = depIds.get(0);
			// 如果将他作为父部门 其下没有子部门 则 取他的父部门 ，否则 取当前部门
			if(webId != null  && !"".equals(webId)){
				Collection<Department> depts =	departmentService.findDepartments(webId);
				if(depts  == null||depts.size() ==0){
					webId = departmentService.findDepartmentById(webId).getSuperiorGuid();
				}
			}
			
		}
		
//		String webId = getSession().getAttribute(Constant.WEB_ID).toString();
		String xhModel=getRequest().getParameter("xhModel");
		PrintWriter pw = getResponse().getWriter();
		Integer number=docNumberService.getDocNum(defineId, gjdz, yearNum, lwdwlx, webId,xhModel,isChildWf,itemId);
		String numberStr ="";
		if(StringUtils.isNotBlank(xhModel) && xhModel.indexOf("num4")!=-1 && number!=null){
			numberStr = CommonUtil.getFourNumberString(number);
		}else{
			numberStr = number + "";
		}
		pw.print(numberStr);
		pw.close();
	}
	
	public void isDocNumUse() throws IOException{
		String defineId = getRequest().getParameter("defid");
		String xh = getRequest().getParameter("xh");
		String gjdz = getRequest().getParameter("gjdz");
		String nh = getRequest().getParameter("nh");
		String lwdwlx = getRequest().getParameter("lwdwlx");
		String isChildWf = getRequest().getParameter("isChildWf");
		String itemId = getRequest().getParameter("itemId");
		String webid = getRequest().getParameter("webid");
		// 如果将他作为父部门 其下没有子部门 则 取他的父部门 ，否则 取当前部门
		if(webid != null  && !"".equals(webid)){
			Collection<Department> depts =	departmentService.findDepartments(webid);
			if(depts  == null||depts.size() ==0){
				webid = departmentService.findDepartmentById(webid).getSuperiorGuid();
			}
		}
		
		PrintWriter pw = getResponse().getWriter();
		pw.print(docNumberService.getIsalreadyUsedDocNumber(defineId, xh, gjdz, nh, lwdwlx,isChildWf,itemId,webid));
		pw.close();
	}
	/**
	 * 
	 * 描述：取得未使用的文号<br>
	 *
	 * @return String
	 *
	 * 作者:张亚杰<br>
	 * 创建时间:2011-12-05 上午09:25:00
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	public String getDocNumUnused() throws UnsupportedEncodingException{
		String defineId = getRequest().getParameter("defid");
		String xh = getRequest().getParameter("xh");
		String gjdz = URLDecoder.decode(getRequest().getParameter("gjdz"),"utf-8");
		String nh = getRequest().getParameter("nh");
		String lwdwlx = getRequest().getParameter("lwdwlx");
		String numStra = getRequest().getParameter("modelVal");
		String isChildWf = getRequest().getParameter("isChildWf");
		String itemId = getRequest().getParameter("itemId");
		String model = getRequest().getParameter("model");
		String isMobile = getRequest().getParameter("isMobile");
//		String webid = getSession().getAttribute(Constant.WEB_ID).toString();
		List<String> depIds = (List<String>)getSession().getAttribute(MyConstants.DEPARMENT_IDS);
		String webid = "";
		if(depIds == null ||depIds.size() == 0){
			webid = getRequest().getParameter("webid");
			
		}else{
			webid = depIds.get(0);
		}
		
		int pageSize = 10;   
		if(CommonUtil.stringNotNULL(isMobile) && isMobile.equals("1")){
			pageSize = 100;
		}
		Object[] obj1 = null;
		try {
			obj1 = docNumberService.findDocNumberUnused(defineId, nh, gjdz, lwdwlx, xh, webid, Paging.pageIndex, pageSize,numStra,isChildWf,itemId);
		} catch (Exception e) {
		}
		if(obj1 != null){
			Paging.setPagingParams(getRequest(), pageSize, (Integer)obj1[1]);
		}else{
			Paging.setPagingParams(getRequest(), pageSize, 0);
		}
		Object[] obj = null;
		try {
			obj = docNumberService.findDocNumberUnused(defineId, nh, gjdz, lwdwlx, xh, webid, Paging.selectIndex-1, Paging.pageSize,numStra,isChildWf,itemId);
			if(obj1 == null){
				Paging.setPagingParams(getRequest(), pageSize, (Integer)obj[1]);
			}
		} catch (Exception e) {
		}
		if(obj !=null ){
			if(obj[2].toString().equals("fw")){
				List<DocNumberWhFw> fwlist = (List<DocNumberWhFw>)obj[0];
				getRequest().setAttribute("fwlist", fwlist);
			}
			if(obj[2].toString().equals("bw")){
				List<DocNumberWhBw> bwlist = (List<DocNumberWhBw>)obj[0];
				getRequest().setAttribute("bwlist", bwlist);
			}
			getRequest().setAttribute("pages", (Integer)obj[1]);
			getRequest().setAttribute("recordSize", (Integer)obj[1]);
		}else{
			Paging.setPagingParams(getRequest(), pageSize, 1);
			getRequest().setAttribute("bwlist", new ArrayList<Object>());
			getRequest().setAttribute("pages", 1);
			getRequest().setAttribute("recordSize", 1);
		}
		getRequest().setAttribute("model", getRequest().getParameter("model"));
		//序号策略
		DocNumberStrategy dns =docNumberService.findStrategy(numStra);
		getRequest().setAttribute("modelVals", dns);
		getRequest().setAttribute("defid", defineId);
		getRequest().setAttribute("xh", xh);
		getRequest().setAttribute("gjdz", gjdz);
		getRequest().setAttribute("nh", nh);
		getRequest().setAttribute("lwdwlx", lwdwlx);
		getRequest().setAttribute("modelVal", numStra);
		getRequest().setAttribute("isChildWf", isChildWf);
		getRequest().setAttribute("itemId", itemId);
		getRequest().setAttribute("model", model);
		getRequest().setAttribute("webid", webid);
		if(CommonUtil.stringNotNULL(isMobile) && isMobile.equals("1")){
			return "unuserddocnumberMobile";
		}
		return "unuseddocnumber";
	}
	
	public String toAttach(){
		getRequest().setAttribute("docNum", getRequest().getParameter("docNum"));
		return "attach";
	}
	
	public void getDocNumAttach() throws Exception{
		String docNum = getRequest().getParameter("docNum");
		int f = docNumberService.findDocNumAttach(docNum);
		PrintWriter pw = null;
		try {
			pw = getResponse().getWriter();
			pw.write(String.valueOf(f));
		} catch (Exception e) {
			logger.error(e);
			throw new Exception();
		} finally {
			if(pw!=null)
			pw.close();
		}
	}
	
	public String toDocNumModOnlyWh(){
		JSONObject jsonObject  = getJSONObject();
		String workflowId = "";
		String itemId ="";
		String value = "";
		if(jsonObject != null && !"".equals(jsonObject)){
			try {
				workflowId = jsonObject.getString("workflowId");
//				itemId = jsonObject.getString("itemId");
				value =  jsonObject.getString("value");
			} catch (Exception e) {
			}
		}else{
			workflowId = getRequest().getParameter("workflowId");
//			itemId = getRequest().getParameter("itemId");
			value = getRequest().getParameter("value");
		}
		getRequest().setAttribute("oldWh", value);
		getRequest().setAttribute("workflowId", workflowId);
		Integer maxWh = 1;
		Integer wh = docNumberService.getMaxDocNumOnlyWh(workflowId, itemId);
		if(null != wh ){
			maxWh = wh+1;
		}
		List<DocNumOnlyWh> list = docNumberService.getDocNumOnlyWh(workflowId, itemId, "0");
		
		getRequest().setAttribute("list", list);
		getRequest().setAttribute("wh", maxWh+"");
		
		return "docModOnlyWh";
	}
	
	public void getDocNum(){
		JSONObject jsonObject  = getJSONObject();
		String workflowId = "";
		String itemId ="";
		String wh = "";
		if(jsonObject != null && !"".equals(jsonObject)){
			try {
				workflowId = jsonObject.getString("workflowId");
				itemId = jsonObject.getString("itemId");
				wh =  jsonObject.getString("value");
			} catch (Exception e) {
			}
		}else{
			workflowId = getRequest().getParameter("workflowId");
			itemId = getRequest().getParameter("itemId");
			wh = getRequest().getParameter("value");
		}
	}
	
	public void addDocNumModOnlyWh(){
		JSONObject jsonObject  = getJSONObject();
		String workflowId = "";
		String itemId = "";
		String wh = "";
		String oldWh = "";
		if(jsonObject != null && !"".equals(jsonObject)){
			try {
				workflowId = jsonObject.getString("workflowId");
				itemId = jsonObject.getString("itemId");
				wh = jsonObject.getString("wh");
				oldWh = jsonObject.getString("oldWh");
			} catch (Exception e) {
			}
		}else{
			workflowId = getRequest().getParameter("workflowId");
			itemId = getRequest().getParameter("itemId");
			wh = getRequest().getParameter("wh");
			oldWh = getRequest().getParameter("oldWh");
		}
		String flag = "0";
		if(StringUtils.isBlank(oldWh)){
			flag = "1";
		}else{
			if(StringUtils.isNotBlank(wh) && !oldWh.equals(wh)){
				DocNumOnlyWh numOnlyWh = docNumberService.getDocNumOnlyWh(workflowId, itemId, Integer.parseInt(wh), "1");
				if(null != numOnlyWh && StringUtils.isNotBlank(numOnlyWh.getId())){
					flag = "2";//文号已经被使用
				}else{
					DocNumOnlyWh docNumOnlyWh = docNumberService.getDocNumOnlyWh(workflowId, itemId, Integer.parseInt(oldWh), "0");
					docNumOnlyWh.setItemid(itemId);
					docNumOnlyWh.setWorkflowid(workflowId);
					docNumOnlyWh.setWh(Integer.parseInt(oldWh));
					docNumOnlyWh.setIsUsed("0");
					docNumberService.editNumOnlyWh(docNumOnlyWh);
					flag = "1";
				}
			}
		}
		Integer maxWh = docNumberService.getMaxDocNumOnlyWh(workflowId, itemId);
		if(flag.equals("1") && StringUtils.isNotBlank(wh)){
			if(maxWh.equals(Integer.parseInt(wh)-1)){
				DocNumOnlyWh docNumOnlyWh = new DocNumOnlyWh();
				docNumOnlyWh.setItemid(itemId);
				docNumOnlyWh.setWorkflowid(workflowId);
				docNumOnlyWh.setWh(Integer.parseInt(wh));
				docNumOnlyWh.setIsUsed("1");
				docNumberService.addDocNumOnlyWh(docNumOnlyWh);
			}else if(maxWh < (Integer.parseInt(wh)-1)){
				for (int i = (maxWh + 1); i <= Integer.parseInt(wh); i++) {
					DocNumOnlyWh docNumOnlyWh = new DocNumOnlyWh();
					if(i == Integer.parseInt(wh)){
						docNumOnlyWh.setIsUsed("1");
					}else{
						docNumOnlyWh.setIsUsed("0");
					}
					docNumOnlyWh.setItemid(itemId);
					docNumOnlyWh.setWorkflowid(workflowId);
					docNumOnlyWh.setWh(i);
					docNumberService.addDocNumOnlyWh(docNumOnlyWh);
				}
			}else if(maxWh > (Integer.parseInt(wh)-1)){
				DocNumOnlyWh docNumOnlyWh = docNumberService.getDocNumOnlyWh(workflowId, itemId, Integer.parseInt(wh), "0");
				docNumOnlyWh.setIsUsed("1");
				docNumOnlyWh.setItemid(itemId);
				docNumOnlyWh.setWorkflowid(workflowId);
				docNumOnlyWh.setWh(Integer.parseInt(wh));
				docNumberService.editNumOnlyWh(docNumOnlyWh);
			}
			toPage("success");
		}else{
			toPage(flag);
		}
	}
	
}
