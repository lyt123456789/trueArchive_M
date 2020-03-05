package cn.com.trueway.base.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
public class Paging{
	public static String queryStr="";
	public static int pageIndex=0;
	public static int pageSize=0;
	public static String sortStr="";
	public static int selectIndex=0;
	public static int MaxIndex=0;
	/**
	 * 设置分页参数
	 * @param request 请求参数
	 * @param pagesize 一页显示的信息条数
	 * @param MaxIndex 总条数
	 */
	public static void setPagingParams(HttpServletRequest request,int pagesize,int MaxIndex){
		//获得查询参数
		queryStr=request.getParameter("queryParams")!=null?request.getParameter("queryParams"):null;
		String valueStr=request.getParameter("queryValues")!=null?request.getParameter("queryValues"):null;
		//获得设置分页参数
		String pageIndexStr = request.getParameter("pageIndex")!=null?request.getParameter("pageIndex").trim():"0";
		pageIndex = Integer.parseInt(pageIndexStr);// 当前页面第一条记录的条数
		pageSize=pagesize>0?pagesize:1;//一页显示的条数 最少1
		String selectIndex = request.getParameter("selectIndex")!=null?request.getParameter("selectIndex").trim():"1";
		Paging.selectIndex=Integer.parseInt(selectIndex);
		Paging.MaxIndex=MaxIndex;
		// 设置返回参数,用于保存页数
		request.setAttribute("selectIndex", selectIndex);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("MaxIndex", MaxIndex);
		request.getSession().setAttribute("pageSize", pageSize);
		//保存查询参数
		if (valueStr!=null&&!valueStr.equals("")) {
			String values[]=valueStr.split("\\|\\|");
			for (int i = 0; i < values.length; i++) {
				String avalue[]=values[i].split("\\-\\-");
				request.setAttribute(avalue[0]+"", avalue[1]);				
			}
		}
		//获得排序列名
		if (request.getParameter("sortStr")==null) {
			sortStr="1";
		}else if(request.getParameter("sortStr")!=null){
			 if (request.getParameter("sortStr").equals("")) {
				 sortStr="1";
			}else {
				sortStr=request.getParameter("sortStr");
			}
		}
		request.setAttribute("sortStr", sortStr);
	}
	/**
	 * 更新sql语句排序条件
	 * @param sql 原sql语句
	 * @return 改变排序条件后的sql语句
	 */
	public static String getSortStr(String sql,String entityname){
		String newsql=sql;
		if (!Paging.sortStr.equals("1")) {
			String columnName=sortStr.split("\\-\\-")[0];
			String upordown=sortStr.split("\\-\\-")[1];
			String ascOrDesc=upordown.equals("up")?"asc":"desc";			
			if (newsql.toLowerCase().indexOf("order by")>0) {
				
			}else{
				newsql+=" order by "+entityname+"."+columnName+" "+ascOrDesc;
			}
		}
		return newsql;
	}
	/**
	 * 得到批删除所需id集合
	 * @param deleteStr 页面返回的批删除id字符串
	 * @return 批删除所需所有id集合
	 */
	public static List<Object> getAllDeleteIds(String idstr){
		String ids[]=idstr.split("\\,");
		List<Object> idLists=new ArrayList<Object>();
		for (int i = 0; i < ids.length; i++) {
			idLists.add(ids[i]);
		}
		return idLists;
	}
	/**
	 * 获得查询语句
	 * @param basehql 基本查询语句，支持sql和hql
	 * @param querystr 从jsp页面获得的查询条件
	 * @param entityname 查询语句中变量的名称
	 * @return 完整的查询语句
	 */
	public static String getWhereStr(String querystr,String entityname){
		String params[]=null;
		String condition=" where 1=1 ";
		if(querystr!=null&&!querystr.equals("")){
			params=querystr.split("\\|\\|");
			for (int i = 0; i < params.length; i++) {
				String values[]=params[i].split("\\-\\-");	
				if(values[2].equals("%string%")){
					condition+="and "+entityname+"."+values[0]+" like '%"+values[1]+"%' ";
				}else if(values[2].equals("string")){
					condition+="and "+entityname+"."+values[0]+"='"+values[1]+"' ";
				}else if(values[2].equals("=date")){
					condition+="and to_char("+entityname+"."+values[0]+",'YYYY-MM-DD')='"+values[1]+"'";
				}else if(values[2].equals(">=date")){
					condition+="and to_char("+entityname+"."+values[0]+",'YYYY-MM-DD')>='"+values[1]+"'";
				}else if(values[2].equals("<=date")){
					condition+="and to_char("+entityname+"."+values[0]+",'YYYY-MM-DD')<='"+values[1]+"'";
				}else if(values[2].equals(">date")){
					condition+="and to_char("+entityname+"."+values[0]+",'YYYY-MM-DD')>'"+values[1]+"'";
				}else if(values[2].equals("<date")){
					condition+="and to_char("+entityname+"."+values[0]+",'YYYY-MM-DD')<'"+values[1]+"'";
				}else if(values[2].equals("=number")){
					condition+="and "+entityname+"."+values[0]+"="+values[1]+" ";
				}else if(values[2].equals(">number")){
					condition+="and "+entityname+"."+values[0]+">"+values[1]+" ";
				}else if(values[2].equals("<number")){
					condition+="and "+entityname+"."+values[0]+"<"+values[1]+" ";
				}else if(values[2].equals(">=number")){
					condition+="and "+entityname+"."+values[0]+">="+values[1]+" ";
				}else if(values[2].equals("<=number")){
					condition+="and "+entityname+"."+values[0]+"<="+values[1]+" ";
				}
			}
		}
		return condition;
	}
	
	
}