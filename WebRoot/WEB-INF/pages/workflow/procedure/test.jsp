<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
</head>

<body > 
	<script type="text/javascript">
		var isOk='${isOk}';
		var mes='';
		if(isOk=='true'){
			mes='测试成功,注意删除测试数据!';
		}else{
			mes='测试失败，请检查存储过程内容!';
		};
		window.parent.alert(mes);
	</script>
	</body>
</html>
