<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="cn.com.trueway.workflow.core.pojo.WfFormPermit"%>
<%@page import="cn.com.trueway.workflow.set.pojo.FormTagMapColumn"%><html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<form id="permitform" method="POST" name="thisForm" action="${ctx }/permit_toPermitJsp.do" >
		<input type="hidden" name="vc_roletype" id="vc_roletype" value="${vc_roletype}">
		<input type="hidden" name="vc_roleid" id="vc_roleid" value="${vc_roleid}">
		<input type="hidden" name="vc_rolename" id="vc_rolename" value="${vc_rolename}">
		<div id="w_list_print" align="center">
			<table class="list" width="70%" align="center" targetType="navTab" asc="asc" desc="desc" layoutH="116">
				<thead>
			    	<tr height="30px">
			    		<td>
			    		选择表单：
			    		<select name="vc_formid" id="vc_formid" style="width: 150px">
				    		<c:forEach items="${formList}" var="d">
				    			<option value="${d.id}">${d.form_caption}</option>
				    		</c:forEach>
			    		</select>
			    		</td>
			    		<td>
			    		选择节点：
			    		<select name="vc_missionid" id="vc_missionid" style="width: 150px">
				    		<c:forEach items="${nodeList}" var="d">
				    			<option value="${d.wfn_id}">${d.wfn_name}</option>
				    		</c:forEach>
			    		</select>
			    		</td>
			    		<td>
			    		<span onclick="submitForm();" style="cursor: hand;">查询</span>
			    		</td>
			    	</tr>
		    	</thead>
	    	</table>
    	</div>
    	<c:if test="${vc_formid != null}">
    	<div id="w_list_print" align="center">
			<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
				<thead>
			    	<tr height="30px">
			    		<th width="5%" align="center">
				    		序号
				    	</th>
				    	<th width="25%" align="center">
				    		字段名
				    	</th>
				    	<th width="20%" align="center">
				    		类型
				    	</th>
				    	<th width="25%" align="center">
				    		权限
				    	</th>
			    	</tr>
			    </thead>
			    <tbody>
			    	<%
						List<FormTagMapColumn> tagList = (List<FormTagMapColumn>)request.getAttribute("tagList");
			    		List<WfFormPermit> list = (List<WfFormPermit>)request.getAttribute("list");
			    		for(int i = 0; tagList != null && tagList.size() > i; i++){
			    			FormTagMapColumn tag = tagList.get(i);
					%>
				    	<tr height="30px">
					    	<td align="center">
					    		<%=i+1 %>
					    	</td>
					    	<td align="center" >
				    	 		<%=tag.getFormtagname() %>
				    	 		<input type="hidden" name="vc_tagname" id="vc_tagname" value="<%=tag.getFormtagname() %>">
					    	</td>
					    	<td >
					    		<%=tag.getFormtagtype() %>
					    	</td>
					    	<td>
					    		<%
									String b_dx = "";
					    			String b_zd = "";
					    			String b_yc = "";
					    			for(WfFormPermit permit : list){
					    				if(tag.getFormtagname().equals(permit.getVc_tagname())){
					    					String limit = permit.getVc_limit();
					    					if("0".equals(limit)){
					    						b_yc = "selected";
					    					}
					    					if("1".equals(limit)){
					    						b_zd = "selected";
					    					}
					    					if("2".equals(limit)){
					    						b_dx = "selected";
					    					}
					    				}
					    			}
					    			if(b_dx.equals("") && b_zd.equals("") && b_yc.equals("")){
					    				b_yc = "selected";
					    			}
								%>
					    		<select name="vc_limit" id="vc_limit">
					    			<option value="2" <%=b_dx %>>读写</option>
					    			<option value="1" <%=b_zd %>>只读</option>
					    			<option value="0" <%=b_yc %>>隐藏</option>
					    		</select>
					    	</td>
				    	</tr>
				    <%
			    		}
				    %>
				</tbody>
		    </table>
    	</div>
    	</c:if>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
			<ul class="mr5"> 
				<li><a class="buttonActive" href="javascript:savePermit();"><span>提交</span></a></li>
				<c:if test="${vc_tagname==null}">
					<li><a class="buttonActive" href="javascript:location.href='${ctx}/permit_getFormPermitList.do?vc_formid=${vc_formid}'"><span>返回</span></a></li>
				</c:if>
				<c:if test="${vc_tagname != null}">
					<li><a class="buttonActive" href="javascript:location.href='${ctx}/permit_getTagPermitList.do?vc_formid=${vc_formid}&vc_tagname=${vc_tagname}&type=${type}'"><span>返回</span></a></li>
				</c:if>
			</ul> 
		</div>
		</form>
    </body>
   
	<script type="text/javascript">
		$("table.list", document).cssTable();
		function submitForm(){
			document.getElementById("permitform").submit();
		}
	</script>
	<%@ include file="/common/function.jsp"%>
</html>
