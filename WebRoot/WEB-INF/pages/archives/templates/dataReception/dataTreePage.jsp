<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${systitle}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${ctx}/dtree/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/font/dtreefont.css">
    <style type="text/css">
    	
    </style>
</head> 
<style type="text/css">


</style>

</head>
<body>
<div style="position:relative;">
	<div  id="treediv" >
		<div  id="toolbarDiv">
		    <ul id="demoTree" class="dtree" data-id="-1"></ul>
		</div>
	</div>
	<div id="editDiv">
		<iframe id="frame" width="100%" frameborder="0" src="" class="iframe"></iframe>
	</div>
</div>	
	
<script src="${ctx}/lib/jquery-3.2.1.js"></script>
<script src="${ctx}/lib/popper.js"></script>
<script src="${ctx}/lib/layui/layui.js"></script>
<script src="${ctx}/lib/bootstrap-material/js/bootstrap-material-design.js"></script>
<script>
var ctx = "${ctx}";
	layui.extend({
		dtree: '${ctx}/dtree/layui_ext/dtree/dtree'
	}).use(['element','layer', 'dtree'], function(){
		var element = layui.element, layer = layui.layer, dtree = layui.dtree, $ = layui.$;
		var dataJson=${treeJson};
		var commonTree = dtree.render({
		    elem: "#demoTree",
		    url: "${ctx}/dataReception_getSonTreeJson.do",
		    data:dataJson,
		    defaultRequest:{nodeId:"nodeId",parentId:"parentId"},
		    dataFormat: "list",  //配置data的风格为list
		    ficon: ["1","-1"],
		    line:true,
		    type:"load",//分级加载
		    height: "full-150",
		    cache:false,
		    initLevel: "2"
		    
		});	
		
		// 点击节点触发回调
		dtree.on("node('demoTree')" ,function(obj){
		    var $div = obj.dom;
		    commonTree.clickSpread($div);  //调用内置函数展开节点
		    var zhid = obj.param.nodeId;//组合id
		    var treeId = zhid.split("-")[0];
			var parentId = obj.param.parentId;
			var structureId = obj.param.basicData;
			var leaf = obj.param.leaf;	
			if(structureId!="0"&&structureId!="-1"){
				parent.document.getElementById("frame").src='${ctx}/dataReception_toEditDataMainPage.do?structureId='+structureId+'&treeId='+treeId;
			}else if(leaf){
				layer.msg("该节点下未关联结构");
			}
		});  
		
	});
	
	function refreshTreeNode(treeId){
		$("div[dtree-click='itemNodeClick'][dtree-id='demoTree'][data-id='"+treeId+"']").click();
		$("div[dtree-click='itemNodeClick'][dtree-id='demoTree'][data-id='"+treeId+"']").click()
	}
	
	</script>
</body>
</html>
