<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ page language="java" pageEncoding="UTF-8"%><!--告诉服务器，页面用的是UTF-8编码-->
<%@ include file="/common/header.jsp"%>
</head>
<body>
	<h3>系统正在为您努力登录中。。。。。。</h3>
<script type="text/javascript">
	window.location.href='${ctx }/login_toLogin.do';
</script>
</body>
</html>


