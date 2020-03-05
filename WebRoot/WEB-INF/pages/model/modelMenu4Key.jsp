<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${systitle}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx}/lib/bootstrap-material/css/bootstrap-material-design.css">
	<link rel="stylesheet" href="${ctx}/lib/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/lib/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="${ctx}/lib/layui_ext/dtree/font/dtreefont.css">
</head> 
<style type="text/css">


</style>

</head>
<body>
<div style="position:relative;">
	<div  id="toolbarDiv">
	    <ul id="demoTree" class="dtree" data-id="-1"></ul>
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
		var dataJson=${dataJson};
		var commonTree = dtree.render({
		    elem: "#demoTree",
		    url: "${ctx}/model_getModelTreeData.do",
		    data:dataJson,
		    defaultRequest:{nodeId:"nodeId",parentId:"parentId"},
		    dataFormat: "list",  //配置data的风格为list
		    dot:false,
		    type:"load",//分级加载
		    cache:false,
		    initLevel: "2"
		});		
		// 点击节点触发回调
		dtree.on("node('demoTree')" ,function(obj){
		    var $div = obj.dom;
		    commonTree.clickSpread($div);  //调用内置函数展开节点
			var codeId = obj.param.parentId;
			var struc = obj.param.basicData;
			if(struc != '0'&&struc != '-1'){
			 	parent.document.getElementById("frame").src='${ctx}/model_toModelTask4Key.do?structId='+obj.param.basicData+'&codeId='+codeId+'&type=0&jydId=${jydId}&djdId=${djdId}&zzcdFlag=${zzcdFlag}';
			}
		});  	
	});

	</script>
</body>
</html>
