<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	request.setAttribute("map", Constant.licenseInfoMap);
%>
<html>
<head>
<title>国土资源局办公自动化系统</title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerindex.jsp"%>
<!--表单样式-->
<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form id="itemform" method="post" action="${ctx}/item_add.do" style="font-family: 宋体; font-size: 12pt;">
<table class="infotan" width="100%">
	<tr>
		<td width="20%" class="bgs ls">产品名称：</td>
		<td width="80%">${map['productName']}</td>
	</tr>
	<tr>
		<td width="20%" class="bgs ls">版本号：</td>
		<td width="80%">${map['version']}</td>
	</tr>
	<tr>
		<td width="20%" class="bgs ls">用户：</td>
		<td width="80%">${map['customer']}</td>
	</tr>
	<tr>
		<td width="20%" class="bgs ls">有效期：</td>
		<td width="80%">${map['validity']}</td>
	</tr>
	<tr>
		<td width="20%" class="bgs ls">服务器操作系统：</td>
		<td width="80%">${map['system']}</td>
	</tr>
	<tr>
		<td width="20%" class="bgs ls">max地址：</td>
		<td width="80%">${map['mac']}</td>
	</tr>
</table>
</form>
</body>
<%@ include file="/common/function.jsp"%>
</html>
