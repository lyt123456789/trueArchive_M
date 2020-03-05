<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle")%></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<link rel="stylesheet" href="${ctx}/qrcode/css/look.css">
<script src="${ctx}/qrcode/js/fontSize.js" type="text/javascript"></script>
<title>扫描文件</title>
</head>
<body>
	<c:if test="${not empty view}">
		<div class="m-form-box">
			<div class="m-form-group clearfix">
				<label class="name">发改委单位名称</label> <span class="cont">${view.dwmc}</span>
			</div>
			<div class="m-form-group clearfix">
				<label class="name">文件名称</label> <span class="cont"
					style="width: 70%;">${view.wjmc}</span>
			</div>
			<div class="m-form-group clearfix">
				<label class="name">拟稿人</label> <span class="cont">${view.ngr}</span>
			</div>
			<div class="m-form-group clearfix">
				<label class="name">印发时间</label> <span class="cont">${view.yfsj}</span>
			</div>
			<div class="m-form-group clearfix">
				<label class="name">文号</label> <span class="cont">${view.wh}</span>
			</div>
		</div>
	</c:if>
	<c:if test="${empty view}">
		对不起，未能找到相应的数据
	</c:if>
</body>
</html>