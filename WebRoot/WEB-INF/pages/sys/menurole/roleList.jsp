<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<style type="text/css">
		.tw-table-list tbody td{
			font-size:16px;
		}
	</style>
</head>
<body>
	<div class="tw-layout">
		<div class="tw-container tm-container">
			<div class="tm-layout-left">
				<div class="tw-list-top">
					<form name="doItemList"  id="doItemList" action="${ctx }/menurole_getRoleList.do" method="post">
					<div class="tw-search-bar">
						<label class="tw-form-label" for="">角色名称：</label>
			            <input type="text" class="tw-form-text" name="roleName" value="${roleName}" placeholder="输入关键字">
			            <button class="tw-btn-primary" type="submit">
			                <i class="tw-icon-search"></i> 搜索
			            </button>
					</div>
					</form>
				</div>
				<div class="tw-container">
					<form id="thisForm" method="POST" name="thisForm" action="${ctx}/role_getRoleList.do" >
					<table class="tw-table-list tw-table-select" layoutH="100">
						<thead>
					    	<tr>
						    	<th width="15%">序号</th>
						    	<th align="center">角色名称</th>
						    	<th align="center" width="30%">状态</th>
							</tr>
				    	</thead>
				    	<tbody>
				    	<c:forEach var="item" items="${list}" varStatus="status"> 
			    		  <tr onclick="impowerMenu('${item.roleId}');" <c:if test="${item.roleId eq firstRoleId}">class="tw-actived"</c:if>>
					        	<td align="center" id="${item.roleId}" class="item" >${status.count}</td>
					        	<td >${item.roleName}</td>
					        	<td align="center">
					        		<c:if test="${item.roleStatus eq 1}"><font style="color: green;">启用</font></c:if>	
					        		<c:if test="${item.roleStatus eq 0}"><font style="color: red;">禁用</font></c:if>
					        		<c:if test="${empty item.roleStatus}">暂无数据</c:if>	
					        	</td>
					      </tr>
					    </c:forEach>
					    </tbody>
		    		</table>
					</form>
				</div>
			</div>


			<div class="tm-layout-right">
				<div class="tw-container2" style="padding:0;;overflow: auto;" layoutH="40">
					<iframe id="frame1"  class="frame1 display"  frameborder="auto" marginheight="0"  marginwidth="0" border="0" 
						src = ""
					style=" display:none; width:98%; height:98%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" 
					></iframe>
					<iframe id="frame2"  class="frame2" frameborder="auto" marginheight="0"  marginwidth="0" border="0" 
						src = "${ctx}/menurole_getMenuList.do?roleId=${firstRoleId}"
					style="width:98%; height:98%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" 
					></iframe>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	$('.tw-container2').height($(window).height()-$('.tw-list-top').height());
});
	//角色授权菜单
	function impowerMenu(roleId){
		$("#frame2").hide();
		$("#frame1").show();
		document.getElementById("frame1").src="${ctx}/menurole_getMenuList.do?roleId="+roleId;
	}
</script>
</html>