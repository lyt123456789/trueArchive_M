<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerindex.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>目录</title>
		<style type="text/css">
			a {
				text-decoration:none;
			}
		</style>
		<script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
	</head>
	<base target="_self"/> 
	<body onload="">
	 	<script type="text/javascript">
	 		var o=new Object();
	 		o.isok='${isok}';
	 		o.filename='${filename}';
	 		parent.document.getElementById("iframe_form").src = '${ctx}/form/html/${filename}?d='+Math.random();
	 		parent.document.getElementById("filename").value= '${filename}';
	 		parent.layer.closeAll();
	 	</script>
	</body>
</html>
