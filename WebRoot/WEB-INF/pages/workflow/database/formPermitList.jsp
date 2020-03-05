<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="panelBar"> 
		<ul class="toolBar">
			<li><a href="javascript:location.href='${ctx}/permit_toAddFormPermitJsp.do?vc_formid=${vc_formid}';" class="add"><span>添加</span></a></li>
		</ul>
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getTableList.do" >
    	<div id="w_list_print" align="center">
			<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
				<thead>
			    	<tr height="30px">
			    		<th width="5%" align="center">
				    		序号
				    	</th>
				    	<th width="15%">
				    		角色类型
				    	</th>
				    	<th width="15%" align="center">
				    		角色名称
				    	</th>
				    	<th width="10%" align="center">
				    		操作
						</th>
						<th  align="center">
				    		
						</th>
			    	</tr>
			    </thead>
			    <tbody>
			    	<c:forEach items="${list}" var="d" varStatus="n">
				    	<tr height="30px">
					    	<td align="center">
					    		${n.count}
					    	</td>
					    	<td >
					    		<c:if test="${d.vc_roletype == '1'}">
					    			内置用户组
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '2'}">
					    			全局用户组
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '3'}">
					    			平台用户组
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '4'}">
					    			流程用户组
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '5'}">
					    			动态角色
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '6'}">
					    			部门
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '7'}">
					    			用户
					    		</c:if>
					    		<c:if test="${d.vc_roletype == '8'}">
					    			部门级联
					    		</c:if>
					    	</td>
					    	<td>
					    		${d.vc_rolename}
					    	</td>
					    	<td align="center">
								<a href="${ctx}/permit_toEditFormPermit.do?vc_formid=${vc_formid}&vc_roletype=${d.vc_roletype}&vc_rolename=${d.vc_rolename}" class="uedset">修改</a>
								<a href="${ctx}/permit_delPermit.do?vc_formid=${vc_formid}&vc_roletype=${d.vc_roletype}&vc_rolename=${d.vc_rolename}" class="uedset">删除</a>
							</td>
							<td>
							</td>
				    	</tr>
				    </c:forEach>
				</tbody>
		    </table>
    	</div>
    	<div class="formBar pa" style="bottom:0px;width:100%;">  
			<ul class="mr5"> 
				<li><a class="buttonActive" href="javascript:location.href='${ctx}/permit_getFormList.do';"><span>返回</span></a></li>
			</ul> 
		</div>
		</form>
    </body>
   
	<script type="text/javascript">
	$("table.list", document).cssTable();

	function sel(){
		var selAll = document.getElementById('selAll');
		var selid = document.getElementsByName('selid');
		for(var i = 0 ; i < selid.length; i++){
			if(selAll.checked){
				selid[i].checked = true;
			}else{
				selid[i].checked = false;
			}
		}
	}

	function del(){
		var selid = document.getElementsByName('selid');
		var ids = "";
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].checked){
				ids += selid[i].value + ",";
			}
		}
		if(ids.length > 0){
			ids = ids.substring(0, ids.length - 1);
		}else {
			alert('请选择一个删除对象');
			return;
		}
		location.href = "${ctx}/table_del.do?ids="+ids;
	}
	</script>
	<%@ include file="/common/function.jsp"%>
</html>
