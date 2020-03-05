package cn.com.trueway.workflow.set.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.trueway.base.actionSupport.BaseAction;
import cn.com.trueway.base.util.CommonUtil;
import cn.com.trueway.base.util.Paging;
import cn.com.trueway.sys.util.SystemParamConfigUtil;
import cn.com.trueway.workflow.set.pojo.Procedure;
import cn.com.trueway.workflow.set.pojo.ProcedureBean;
import cn.com.trueway.workflow.set.service.ZwkjFormService;

public class ProcedureAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ZwkjFormService zwkjFormService;
	private Procedure procedure;
	
	

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

	public ZwkjFormService getZwkjFormService() {
		return zwkjFormService;
	}

	public void setZwkjFormService(ZwkjFormService zwkjFormService) {
		this.zwkjFormService = zwkjFormService;
	}
	/**
	 * 转向列表页
	 * @return
	 */
	public String getList(){
		String column=getRequest().getParameter("column");
		String value=getRequest().getParameter("value");
		String type=getRequest().getParameter("type");
		if (CommonUtil.stringNotNULL(type)) {
			getRequest().setAttribute("type", type);
			
		}
		
		//分页相关，代码执行顺序不变
		int pageSize = Integer.parseInt(SystemParamConfigUtil.getParamValueByParam("pagesize"));
		int count=zwkjFormService.getProcedureListCountByParamForPage(column, value, null, null);
		Paging.setPagingParams(getRequest(), pageSize, count);
		List<Procedure> list=zwkjFormService.getProcedureListByParamForPage(column, value, Paging.pageIndex, Paging.pageSize);
		
		getRequest().setAttribute("list", list);
		return "getList";
	}
	
	public String toAdd(){
		
		return "toAdd";
	}
	public String add(){
		String procedurename=getRequest().getParameter("procedurename");
		String pcname=getRequest().getParameter("pcname");
		
		String[] paramname=getRequest().getParameterValues("paramname");
		String[] paramtype=getRequest().getParameterValues("paramtype");
		
		String[] inoutparam=getRequest().getParameterValues("inoutparam");
		
		String paramcontent=getRequest().getParameter("paramcontent");
		
		Procedure p=new Procedure();
		p.setPname(procedurename);
		if (paramname!=null) {
			String ppname="";
			String pptype="";
			String ppinout="";
			for (int i = 1; i < paramname.length; i++) {
				if (!paramname[i].equals("")) {
					ppname+=paramname[i]+",";
					pptype+=paramtype[i]+",";
					ppinout+=inoutparam[i]+",";
				}
			}
			p.setParamname(ppname);
			p.setParamtype(pptype);
			p.setInouttype(ppinout);
		}
		p.setPcontent(paramcontent);
		p.setIntime(CommonUtil.getNowTimeTimestamp(null));
		p.setPcname(pcname);
		zwkjFormService.addProcedure(p);
		return getList();
	}
	public String toUpdate(){
		String id=getRequest().getParameter("id");
		Procedure p=zwkjFormService.getOneProcedureById(id);
		List<ProcedureBean> params=new ArrayList<ProcedureBean>();
		String[] ppname=p.getParamname()==null?null:p.getParamname().split(",");
		String[] pptype=p.getParamtype()==null?null:p.getParamtype().split(",");
		String[] ppinout=p.getInouttype()==null?null:p.getInouttype().split(",");
		if (ppname!=null) {
			for (int i = 0; i < ppname.length; i++) {
				ProcedureBean pro=new ProcedureBean();
				pro.setPpname(ppname[i]);
				pro.setPptype(pptype[i]);
				pro.setPpinout(ppinout[i]);
				params.add(pro);
			}
		}
		getRequest().setAttribute("procedure", p);
		getRequest().setAttribute("params", params);
		return "toUpdate";
	}
	public String update(){
		String id=getRequest().getParameter("id");
		String procedurename=getRequest().getParameter("procedurename");
		String pcname=getRequest().getParameter("pcname");
		String[] paramname=getRequest().getParameterValues("paramname");
		String[] paramtype=getRequest().getParameterValues("paramtype");
		
		String[] inoutparam=getRequest().getParameterValues("inoutparam");
		
		String paramcontent=getRequest().getParameter("paramcontent");
		
		Procedure p=new Procedure();
		p.setPname(procedurename);
		if (paramname!=null) {
			String ppname="";
			String pptype="";
			String ppinout="";
			for (int i = 1; i < paramname.length; i++) {
				if (!paramname[i].equals("")) {
					ppname+=paramname[i]+",";
					pptype+=paramtype[i]+",";
					ppinout+=inoutparam[i]+",";
				}
			}
			p.setParamname(ppname);
			p.setParamtype(pptype);
			p.setInouttype(ppinout);
		}
		p.setPcontent(paramcontent);
		p.setPcname(pcname);
		p.setId(id);
		zwkjFormService.updateProcedure(p);
		return getList();
	}
	public String view(){
		String id=getRequest().getParameter("id");
		Procedure p=zwkjFormService.getOneProcedureById(id);
		List<ProcedureBean> params=new ArrayList<ProcedureBean>();
		String[] ppname=p.getParamname()==null?null:p.getParamname().split(",");
		String[] pptype=p.getParamtype()==null?null:p.getParamtype().split(",");
		String[] ppinout=p.getInouttype()==null?null:p.getInouttype().split(",");
		if (ppname!=null) {
			for (int i = 0; i < ppname.length; i++) {
				ProcedureBean pro=new ProcedureBean();
				pro.setPpname(ppname[i]);
				pro.setPptype(pptype[i]);
				pro.setPpinout(ppinout[i]);
				params.add(pro);
			}
		}
		getRequest().setAttribute("procedure", p);
		getRequest().setAttribute("params", params);
		return "view";
	}
	
	public String delete(){
		String ids=getRequest().getParameter("ids");
		if (CommonUtil.stringNotNULL(ids)) {
			String[] idarr=ids.split(",");
			for (int i = 0; i < idarr.length; i++) {
				Procedure p=new Procedure();
				p.setId(idarr[i]);
				zwkjFormService.deleteProcedure(p);
			}
		}
		return getList();
	}
	/**
	 * 
	 * @return
	 */
	public String test(){
		String procedurename=getRequest().getParameter("procedurename");
		
		String[] paramname=getRequest().getParameterValues("paramname");
		String[] paramtype=getRequest().getParameterValues("paramtype");
		
		String[] inoutparam=getRequest().getParameterValues("inoutparam");
		
		String paramcontent=getRequest().getParameter("paramcontent");
		
		//拼接存储过程sql
//		String procedureSql="CREATE OR REPLACE PROCEDURE "+procedurename+"(";
//		if (paramname!=null) {
//			if (paramname.length>1) {
//				procedureSql+="\n";
//			}
//			for (int i = 1; i < paramname.length; i++) {
//				if (!paramname[i].equals("")) {
//					procedureSql+=i==paramname.length-1?"\t"+paramname[i]+" "+inoutparam[i]+"  "+paramtype[i]+"\n":"\t"+paramname[i]+" "+inoutparam[i]+"  "+paramtype[i]+",\n";
//				}
//			}
//		}
//		procedureSql+=") IS\n";
//		procedureSql+="BEGIN\n";
//		procedureSql+=paramcontent;
//		procedureSql+="\n END "+procedurename+";";
		String procedureSql=paramcontent;
		procedureSql=procedureSql.replace("\r", "");//非常重要，去除因文本域传参所带来的特殊换行字符串\r
		boolean isOk=zwkjFormService.excuteSql(procedureSql);
		
		if (isOk) {//生成存储过程是否成功
			//输入、输出参数   目前只支持VARCHAR、INTEGER、DATE三种
			//输入、输出参数类型   仅有in、out两种
//			Object[][] obj={
//					{"in",	"VARCHAR","wh1234"},//1输入参数  2输入参数类型  3输入参数值
//					{"in",	"VARCHAR","id1234"},
//					{"in",	"DATE",Timestamp.valueOf("2013-01-01 15:25:33")},
//					{"out",	"INTEGER","oldCount"},//1输出参数 2输出参数类型  3输出值map的key
//					{"out",	"INTEGER","newCount"},
//					{"out",	"DATE","cDate"}
//			};
			Object[][] obj=null;
			if (paramname!=null) {
				obj=new Object[paramname.length-1][3];
				for (int i = 1; i < paramname.length; i++) {
					if (!paramname[i].equals("")) {
						Object[] o=new Object[3];
						o[0]=inoutparam[i];
						o[1]=paramtype[i];
						if (paramtype[i].equals("VARCHAR")) {
							o[2]="测试数据，要删除";
						}else if (paramtype[i].equals("INTEGER")) {
							o[2]=0;
						}else if (paramtype[i].equals("DATE")) {
							o[2]=CommonUtil.getNowTimeTimestamp(null);
						}
						obj[i-1]=o;
					}
				}
			}
			
			//测试存储过程
			try {
				Map<String, Object> map=zwkjFormService.excuteProcedure(obj, procedurename);
			} catch (Exception e) {
				isOk=false;
				e.printStackTrace();
			}
			
		}
		getRequest().setAttribute("isOk", isOk);
		
		
		return "test";
	}
}
