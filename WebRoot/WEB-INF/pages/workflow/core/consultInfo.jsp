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
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
<script type="text/javascript">
	
</script>
</head>
    <body>
    	<table class="infotan" width="100%">
    		<tr>
    			<td align="right" width="30%">发送人：</td>
    			<td>${consultInfo.fromUserName}</td>
    		</tr>
    		 
    		<tr>
    			<td align="right" width="30%">发送时间：</td>
    			<td>${fn:substring(consultInfo.sendTime,0,16) }</td>
    		</tr>
    		<tr>
    			<td align="right" width="30%" style="height: 150px">消息内容：</td>
    			<td style="height: 150px">${consultInfo.message }</td>
    		</tr> 
    	</table>
    </body>
</html>
