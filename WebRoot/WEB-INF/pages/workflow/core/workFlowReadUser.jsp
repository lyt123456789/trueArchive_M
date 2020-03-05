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
    	<span style="font-weight:bold;color:blue;line-height:37px;">&nbsp;&nbsp;&nbsp;阅者状态</span>
		<table class="infotan mt10" width="100%">
			<tr>
				<td width="20%" style="font-weight:bold;text-align:left;">办理人员</td>
				<td width="25%" style="font-weight:bold;text-align:center;">收到时间</td>
				<td width="20%" style="font-weight:bold;text-align:center;">完成时间</td>
			</tr>
			<c:if test="${firstStep != true}">
				<c:forEach items="${wfProcessList}" var="process">
					<tr>
						<td width="20%" >${process.userUid}</td>
						<td width="20%" >${fn:substring(process.applyTime,0,16)}</td>
						<td width="20%" >${fn:substring(process.finshTime,0,16)}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${firstStep == true}">
				<tr>
					<td width="20%" >${userName}</td>
					<td width="20%" >&nbsp;</td>
					<td width="20%" >&nbsp;</td>
				</tr>
			</c:if>
		</table>
    </body>
</html>
