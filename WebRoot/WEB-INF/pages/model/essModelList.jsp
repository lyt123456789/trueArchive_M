<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${systitle}</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="${ctx}/lib/layui/css/layui.css">
	<script src="${ctx}/lib/jquery-3.2.1.js"></script>
	<script src="${ctx}/lib/layui/layui.js"></script>
	<script type="text/javascript">
	  layui.use('layer', function(){
		 var layer = layui.layer;
		  
		}); 
	</script>
</head> 
<style>
.label {
    float: left;
    display: block;
    padding: 9px 7px;
    width: 100px;
    font-weight: 400;
    line-height: 20px;
    text-align: right;
}
</style>
		<frameset cols="1,6" cols="*"  framespacing="0" frameborder="1"   border="1">
			<frame src="${ctx}/model_toModelMenu.do"  name="frame_model_menu" id="model_menu"  frameborder="1"/>
			<frame src="${ctx}/employee/welcome.do"  name="frame_model_task" id="model_task"  frameborder="1"/>
		</frameset>
		
	  <noframes>
	   <body>
	    <p>此网页使用了框架，但您的浏览器不支持框架。</p>
	   </body>
	  </noframes>

</html>
