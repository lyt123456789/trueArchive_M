<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/dtree.css">
    <link rel="stylesheet" href="${ctx}/dtree/layui_ext/dtree/font/dtreefont.css">

</head>
<body>
<div class="dtree-toolbar-tool">
	<form class="layui-form layui-form-pane" lay-filter="dtree_addNode_form">
			<div class="layui-form-item">
				<c:if test="${'add' eq editFlag }">
					<label class="layui-form-label">新增节点：</label>
				</c:if>
				<c:if test="${'update' eq editFlag }">
					<label class="layui-form-label">编辑节点：</label>
				</c:if>
				<div class="layui-input-block f-input-par">
					<input type="text" class="layui-input f-input" value="${title}" placeholder="" lay-verify="required" id="nodeTitle" name="nodeTitle">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label" title="">排序号：</label>
				<div class="layui-input-block f-input-par">
					<input type="text" class="layui-input f-input" value="${order}" lay-verify="" id="nodeOrder" name="nodeOrder">
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block" style="margin-left:0px;text-align:center;">
					<button type="button" onclick="editNode();" class="layui-btn layui-btn-normal btn-w100" lay-submit="" lay-filter="dtree_addNode_form">保存</button>
				</div>
			</div>
	</form>
</div>
</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">

	
	function editNode(){
      	var title = document.getElementById("nodeTitle").value;
      	var order = document.getElementById("nodeOrder").value;
      	if("${editFlag}"=="add"){
      		layer.load();
      		$.ajax({
                url:'${ctx}/dataTemp_addTreeNode.do',
                async:true,//是否异步
    	        type:"POST",//请求类型post\get
    	        cache:false,//是否使用缓存
    	        dataType:"text",//返回值类型
                data:{"parentId":"${parentId}","title":title,"order":order},
                success:function(data){
                    if(data == 'success'){ 	
                    	window.parent.parent.parent.refreshTreeNode("${parentId}");
                    	parent.location.reload();
                    }else{
                        layer.msg("添加失败")
                    }
                },
                error:function () {
                    layer.msg("添加失败")
                }
			});
      	}else if("${editFlag}"=="update"){
      		layer.load();
      		$.ajax({
                url:'${ctx}/dataTemp_updateTreeNode.do',
                async:true,//是否异步
      	        type:"POST",//请求类型post\get
      	        cache:false,//是否使用缓存
      	        dataType:"text",//返回值类型
                data:{"id":"${treeId}","title":title,"order":order},
                success:function(data){
                      if(data == 'success'){
                    	  window.parent.parent.parent.refreshTreeNode("${parentId}");
	                      parent.location.reload();
                      }else{
                          layer.msg("修改失败")
                      }
                },
                error:function () {
                    layer.msg("修改失败")
                }
			});
      	}
		
	}
	</script>
	<script>
	(function ($) {
	 $.extend({
	  Request: function (m) {
	   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
	   return sValue ? sValue[1] : sValue;
	  },
	  UrlUpdateParams: function (url, name, value) {
	   var r = url;
	   if (r != null && r != 'undefined' && r != "") {
	    value = encodeURIComponent(value);
	    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
	    var tmp = name + "=" + value;
	    if (url.match(reg) != null) {
	     r = url.replace(eval(reg), tmp);
	    }
	    else {
	     if (url.match("[\?]")) {
	      r = url + "&" + tmp;
	     } else {
	      r = url + "?" + tmp;
	     }
	    }
	   }
	   return r;
	  }
	 
	 });
	})(jQuery);
	</script>
</html>