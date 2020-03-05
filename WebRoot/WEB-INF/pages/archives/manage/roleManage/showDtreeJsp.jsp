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
<div style="position:relative;margin-top:20px;">
	<input type="hidden" id="treeNodes" name="treeNodes" value="${treeNodes}">
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
		    //url: "${ctx}/model_getModelTreeData.do",
		    data:dataJson,
		    defaultRequest:{nodeId:"id",parentId:"parentId"},
		    dataFormat: "list",  //配置data的风格为list
		    dot:false,
		    type:"all",//全量加载
		    initLevel: "2",
		    checkbar: true,  
		    checkbarType: "all",
		    checkbarData: "choose" 
		});
		
		dtree.on("chooseDone('demoTree')" ,function(obj){
			 var params = dtree.getCheckbarNodesParam("demoTree");
			 var str = "";
			 for(var i=0;i<params.length;i++){
				 var nodeId = params[i].basicData;
				 if(nodeId!=null){
					 str+=nodeId+",";
				 }
			 }
			 if(str!=null&&str!=""){
				 str=str.substring(0, str.length-1);
			 }
			 document.getElementById("treeNodes").value=str;
		});

		
		// 点击节点触发回调
		dtree.on("node('demoTree')" ,function(obj){
			var $div = obj.dom;
		    commonTree.clickSpread($div);  //调用内置函数展开节点
		}); 
	});
	
	var callbackdata = function () {
		var treeNodes = document.getElementById("treeNodes").value;
	    return treeNodes;
	}
	</script>
</body>
</html>
