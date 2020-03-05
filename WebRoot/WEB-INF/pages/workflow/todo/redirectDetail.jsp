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
 <div class="pageHeader" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr> 
				<td>
					<div class="buttonActive">
						<div class="buttonContent">
						<button type="button" onclick="toDofileJsp();">返回</button></div>
					</div>
				</td>
				<td>
					待办信息
				</td> 
				
			</tr>
		</table>
	</div>
</div>
<div id="pageContent" class="pageContent"  style="overflow:auto;">
<form id="thisForm" method="POST" name="thisForm" action="${ctx}/table_getPendingList.do" >
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="5%" align="center">序号</th>
				<th width="20%" align="center">标题</th>
				<th align="center" width="13%" >所属事项</th>
				<th align="center" width="11%" >当前步骤</th>
				<th width="10%" align="center">步骤期限</th>
				<th width="10%" align="center">办件期限</th>
				<th width="10%" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${list}" varStatus="status"> 
			<tr>
				<td align="center">${status.count}</td>
				<td>${item[2]}</td>
				<td>${item[3]}</td>
				<td>${item[1]}
				<c:if test="${item[9] == '2'}">
					<font color="red">(等办)</font>
				</c:if>
				</td>
				<td>${item[6]}</td>
				<td align="center">${item[7]}</td>
				<td align="center">
					 <c:if test="${item[10] == '1'}"> 
						<a href="#" onclick="openDetail('${item[0]}','${item[8]}');">详细</a>
					 </c:if> 
				</td>
			</tr>
		</c:forEach>	
	</table>
	</div>
	<iframe style="width: 98%; height:450px; display: none;" src="" id="detail"></iframe>
	</form>
</div>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
			<div id="div_god_paging" class="cbo pl5 pr5"></div> 
		</div>
    </body>
<%@ include file="/common/function.jsp"%> 
<script>
$("table.list", document).cssTable();
</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script type="text/javascript">
	//打开详细页面:查看该流程目前处于的位置
	function openDetail(instanceId,stepIndex){
		var iframe=document.getElementById("detail");
		iframe.style.display = "";
		iframe.src="${ctx}/table_getProcessDeatil.do?instanceId="+instanceId+"&stepIndex="+stepIndex;
	}
	
	function toDofileJsp(){
		window.location.href = "${ctx}/table_getDoFileList.do?redirect=1";
	}
</script>
<script>
		$('#pageContent').height($(window).height()-70);
</script>
</html>

