<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
</head>
<body>
<div class="main">
	<iframe style="width: 100%;height: 50%;" src="${ctx}/ftm_showFUllTextIndexYesList.do?treeNodes1=${treeNodes1}&treeNodes2=${treeNodes2}"></iframe>
	<iframe style="width: 100%;height: 50%;" src="${ctx}/ftm_showFUllTextIndexNoList.do?treeNodes1=${treeNodes1}&treeNodes2=${treeNodes2}"></iframe>
</div>
</body>
	<%@ include file="/common/widgets.jsp"%>	
	<script>
	 $(function () {
        $(".main").height($(window).height()-10);
    })
    </script>
</html>