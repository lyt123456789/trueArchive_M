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
        <div class="tab">
            <span class="act">关键词检索</span>
            <span>综合业务查询</span>
            <span>全文检索</span>
            <input type="hidden" id="jydId" name="jydId" value="${jydId}">
            <input type="hidden" id="djdId" name="djdId" value="${djdId}">
        </div>
        <div class="panel">
            <div class="tree">
            	 <iframe src="${ctx}/model_toModelMenu4Key.do?jydId=${jydId}&djdId=${djdId}&zzcdFlag=${zzcdFlag}"  name="frame_model_menu" id="model_menu0"  frameborder="1" style="height: 100%;width: 280px;"></iframe>
            </div>
            <div class="tree undis">
            	<input type="hidden" id="zhywIds" name="zhywIds"/>
				<iframe src=""  name="frame_model_menu2" id="model_menu1"  frameborder="1" style="height: 100%;width: 280px;"></iframe>
            </div>
            <div class="tree undis">
            	<input type="hidden" id="treeNodeIds" name="treeNodeIds"/>
				<iframe src=""  name="frame_model_menu3" id="model_menu2"  frameborder="1" style="height: 100%;width: 280px;"></iframe>
            </div>
        </div>

    </div>
    <div class="content">
    	<c:if test="${empty jydId}">
    		<iframe id="frame" width="100%" frameborder="0" src="${ctx}/model_toWelcomeJsp.do" class="iframe"></iframe>
    	</c:if>
    	<c:if test="${!empty jydId}">
    		<iframe id="frame" width="100%" frameborder="0" src="${ctx}/model_toModelTask4Key.do?jydId=${jydId}&djdId=${djdId}&zzcdFlag=${zzcdFlag}" class="iframe"></iframe>
    	</c:if>    
        <iframe id="frame2" width="100%" frameborder="0" src="${ctx}/model_toModelTask4Zh.do?jydId=${jydId}&djdId=${djdId}&zzcdFlag=${zzcdFlag}" class="iframe undis"></iframe>
        <iframe id="frame3" width="100%" frameborder="0" src="${ctx}/model_toModelTask4QW.do?jydId=${jydId}&djdId=${djdId}&zzcdFlag=${zzcdFlag}" class="iframe undis"></iframe>
    </div>
</div>
<script>
    $(function () {
        $(".main").height($(window).height());
        $(".iframe").height($(window).height());
        $("#model_menu0").height($(window).height()-50);
        $("#model_menu1").height($(window).height()-50);
        $("#model_menu2").height($(window).height()-50);
        $(".tab span").click(function () {
            $(".tab span").removeClass("act");
            $(this).addClass("act");
            $(".panel .tree").addClass("undis");
            $(".panel .tree").eq($(this).index()).removeClass("undis");
            $(".content .iframe").addClass("undis");
            $(".content .iframe").eq($(this).index()).removeClass("undis");
        })
    })
    
    setTimeout("showdatatree2()",500); 
    setTimeout("showdatatree3()",1000); 
    function showdatatree2(){
    	document.getElementById("model_menu1").src="${ctx}/model_toModelMenu4Zh.do?jydId=${jydId}&djdId=${djdId}&zzcdFlag=${zzcdFlag}";
    }
    function showdatatree3(){
    	document.getElementById("model_menu2").src="${ctx}/model_toModelMenu4QW.do?jydId=${jydId}&djdId=${djdId}&zzcdFlag=${zzcdFlag}";
    }
</script>
</body>
</html>