<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	 <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/jquery-1.8.2.min.js"></script>
    <%@ include file="/common/widgets.jsp"%>
</head> 
<body>
<div class="main">
    <div class="menu">
            <div class="tree">
            	 <iframe src="${ctx}/dataManage_toDataTreePage.do?currId=${currId}&businessId=35547CE2-A572-4CDF-83D0-48B404164C59"  name="frame_model_menu" id="model_menu0"  frameborder="1" style="height: 100%;width: 280px;"></iframe>
            </div>
    </div>
    <div class="content">
        <iframe id="frame" width="100%" frameborder="0" src="" class="iframe"></iframe>
    </div>
</div>
<script>
    $(function () {
        $(".main").height($(window).height());
        $(".iframe").height($(window).height());
        $("#model_menu0").height($(window).height());
        $(".content").width($(window).width()-$(".menu").width());
    })
</script>
</body>
</html>