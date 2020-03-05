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
	<script src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
</head>
    <body>
		<table class="infotan mt10" width="100%">
			<tr>
				<td width="10%" style="font-weight:bold;text-align:center;">&nbsp;</td>
				<td width="20%" style="font-weight:bold;text-align:center;">节点名称</td>
			</tr>
			<c:forEach items="${nodes}" var="node">
				<tr>  
					<td width="10%" style="text-align:center;"><input type="radio" name="nodeName" value="${node.wfn_id}"/></td>
					<td width="20%" style="text-align:center;">${node.wfn_name}</td>
				</tr>
			</c:forEach>
		</table>
    </body>
</html>
