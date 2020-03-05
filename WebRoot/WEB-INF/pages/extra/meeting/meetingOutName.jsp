<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>参会名单详细信息</title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	 <link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>		
<body>
<div style="height:768px;overflow:scroll;" id="div">
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/meetings_getMeetingOutName.do" >
	<div id="w_list_print">
	<div class="panelBar">
		<ul class="toolBar">
			<li><input class="btn" type="button"  value="导出签到表" onclick="exportExecl()"/></li>
		</ul>
	</div>
	<h1 style="font-size: 20px;line-height:25px;text-align:center;font-weight:bold;">${meetingname }参会人员名单</h1>
	<table class="list" width="100%" id="tableId">
		<thead>
			<tr>
				<th align="center">单位</th>
				<th align="center">姓名</th>
				<th align="center">职务</th>
				<th align="center">手机号码</th>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="item" items="${list}" varStatus="status"> 
			<tr >
				<td align="center">${item.departmentGuid }</td>
				<td align="center">${item.employeeName }</td>
				<td align="center">${item.employeeJobtitles }</td>
				<td align="center">${item.employeeMobile }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div align="center">
		<input class="btn" type="button" value="返回" title="返回" onclick="history.back();"/>
	</div>
	</div>
	</form>
</div>
</body>
<%@ include file="/common/function.jsp"%> 
<script>
	function exportExecl(){
		var table=document.getElementById("tableId");
		var oXL = new ActiveXObject("Excel.Application");
		var oWB = oXL.Workbooks.Add();
		var oSheet = oWB.ActiveSheet; 
		var sel=document.body.createTextRange();
		sel.moveToElementText(table);
		sel.select();
		sel.execCommand("Copy");
		oSheet.Paste();
		oXL.Visible = true;
		oXL.UserControl = true; oSheet = null;oWB = null;oXL = null;
	}
</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
</html>