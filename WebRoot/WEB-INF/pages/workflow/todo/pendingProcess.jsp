<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%><html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/headerindex.jsp"%>
    <style>
    .bdxlcon{ display:none;position:absolute;overflow:hidden;width:80px;top:22px;left:0px;border:1px #cccccc solid;z-index:1000;background:#E5EDEF;}
    .bdxlcon a{line-height:25px;display:block;overflow:hidden;width:68px;padding:0px !important;padding-left:10px !important;height:25px;text-align:left;margin:1px;}
    .bdxlcon a:hover{color:#760a0a;background:#cccccc;} 
    </style>
    <script src="pdfocx/json2.js"></script>
</head>
<body>
<div style="padding:5px;">
<div class="pageContent">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li class="lio" onclick=""><a ><span>跟踪</span></a></li>  
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="padding:1px 0px 1px 0px !important;">
			<div>
			<iframe id="frame0" class="frame0" frameborder="auto" marginheight="0" marginwidth="0" border="0" style="float:left;width:100%;height:100%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" src="${ctx}/table_getProcess.do?instanceId=${instanceId}&workFlowId=${workFlowId}&a=Math.random()"></iframe>
			</div>
		</div>
</div>
</div>
</body>