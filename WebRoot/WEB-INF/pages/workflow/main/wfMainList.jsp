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
<form id="pagerForm" method="post" action="w_list.html">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" onclick="saveWfMainRole();"><span>确认选择</span></a></li>
		</ul>
	</div>
<div id="pageContent" class="pageContent"  style="overflow:auto;">
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/mobileTerminalInterface_getWfMainListByRetrieval.do" >
	<input type="hidden" name="roleId" id="roleId" value="${roleId}">
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="140">
		<thead>
			<tr>
			    <th width="30" ><input type="checkbox" value="1" name="selAll" onclick="sel();"></th>
			    <th width="50" align="center">序号</th>
				<th width="300" align="center">工作流名称</th>
				<th align="center" width="80" >状态</th>
				<th align="center" width="150" >更新日期</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${list}" varStatus="p"> 
			<tr target="sid_user" rel="1">
				<td align="center">
					
						<input type="checkbox" value="${item.wfm_id}" name="selid" 
					<c:forEach var="item2" items="${mainRoleList}">	
						<c:if test="${item2.wfmainId == item.wfm_id}"> checked</c:if>
					</c:forEach>	
						>
					
				</td>
				<td align="center" >${p.count }</td>
				<td class="workflowidtitle" workflownnameid="${item.wfm_id }">${item.wfm_name }</td>
				<td align="center">${item.wfm_status }</td>
				<td align="center">${item.wfm_modifytime }</td>
				<td></td> 
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</form>
</div>
</body>
<%@ include file="/common/function.jsp"%> 
<script>
$("table.list", document).cssTable();
</script>
<script language="javascript" type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script>
	$('#pageContent').height($(window).height()-70);
	
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
	
	
	function saveWfMainRole(){
		var roleId = '${roleId}';
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
			alert('请选择需要绑定工作流程');
			return;
		}
		//获取选中的事项
		if(confirm("确定将这些流程绑定到该角色吗？")){
			//ajax异步提交
			//ajax调用
			$.ajax({
				url : '${ctx}/employeeRole_saveWfMainRole.do?roleId='+roleId+"&ids="+ids,
				type : 'POST',  
				cache : false,
				async : false,
				error : function() {
				alert('AJAX调用错误(item_del.do)');
					return false;
				},
				success : function(result) {   
					var res = eval("("+result+")");
					if(res.success){
						window.close();
						window.returnValue='refresh';
					}else{
						alert("授权失败");
					}
				}
			});
		}
	}
</script>
</html>