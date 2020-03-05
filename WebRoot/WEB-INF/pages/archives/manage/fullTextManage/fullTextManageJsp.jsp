<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	 <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="${ctx}/lib/bootstrap-material/css/bootstrap-material-design.css">
	<link rel="stylesheet" href="${ctx}/lib/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/lib/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="${ctx}/lib/layui_ext/dtree/font/dtreefont.css">
    <script src="js/jquery-1.8.2.min.js"></script>
    <%@ include file="/common/widgets.jsp"%>
    <style type="text/css">
    	.dtree-nav-item {
    		padding-left: 10px;
    	}
    </style>
</head> 
<body>
<div class="main">
    <div class="menu">
        <div  id="showtree" style="position:relative;overflow-x: hidden;overflow-y: auto;">
			<div  id="toolbarDiv">
			    <ul id="demoTree" class="dtree" data-id="-1"></ul>
			</div>	
		</div>	
    </div>
    <div class="content">
    	<iframe id="frame" width="100%" frameborder="0" src="" class="iframe"></iframe>
    </div>
</div>
<script src="${ctx}/lib/jquery-3.2.1.js"></script>
<script src="${ctx}/lib/popper.js"></script>
<script src="${ctx}/lib/layui/layui.js"></script>
<script src="${ctx}/lib/bootstrap-material/js/bootstrap-material-design.js"></script>
<script>

layui.extend({
	dtree: '${ctx}/lib/layui_ext/dist/dtree'
}).use(['element','layer', 'dtree'], function(){
	var element = layui.element, layer = layui.layer, dtree = layui.dtree, $ = layui.$;
	var dataJson=${treeJson};
	var commonTree = dtree.render({
	    elem: "#demoTree",
	   /*  url: "${ctx}/model_getModelTreeData.do", */
	   data:dataJson,
	    defaultRequest:{nodeId:"id",parentId:"parentId"},
	    dataFormat: "list",  //配置data的风格为list
	    dot:false,
	    type:"all",//全量加载
	    initLevel: "2"
	});		
	// 点击节点触发回调
	dtree.on("node('demoTree')" ,function(obj){
		var $div = obj.dom;
	    commonTree.clickSpread($div);  //调用内置函数展开节点
		var codeId = obj.param.parentId;
		var treeId = obj.param.basicData;
		document.getElementById("frame").src='${ctx}/ftm_showFUllTextIndexList.do?treeId='+treeId;
	});  
});

    $(function () {
        $(".main").height($(window).height());
        $("#showtree").height($(window).height());
        $(".iframe").height($(window).height());
        $("#model_menu").height($(window).height()-50);
        $("#model_menu2").height($(window).height()-50);
        $(".tab span").click(function () {
            $(".tab span").removeClass("act");
            $(this).addClass("act");
            $(".panel .tree").addClass("undis");
            $(".panel .tree").eq($(this).index()).removeClass("undis");
            $(".content .iframe").addClass("undis");
            $(".content .iframe").eq($(this).index()).removeClass("undis");
           // $(".content .iframe").eq($(this).index()).attr("src", $(".content .iframe").eq($(this).index()).attr("src"));
        })
    })
</script>
</body>
</html>