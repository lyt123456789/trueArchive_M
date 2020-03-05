<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="renderer" content="webkit">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	    <link rel="stylesheet" type="text/css" href="${ctx}/layui2.5.1/layui/css/layui.css">
	    <style type="text/css">
	    </style>
	</head>
	<body>
		<div class="layui-tab layui-tab-brief" lay-filter="statistics">
			  <ul class="layui-tab-title">
			  	<c:if test="${not empty structureId}">
				  	<li lay-id="sjst">数据视图</li>
				    <li lay-id="jgst" class="layui-this">结构视图</li>
				    <li lay-id="zdxz">字段选择</li>
				    <li lay-id="mc">名称</li>
				    <li lay-id="zhzd">组合字段</li>
				    <li lay-id="px">排序</li>
				    <li lay-id="zdz">字段值</li>
				    <li lay-id="dmz">代码值</li>
				    <li lay-id="mrz">默认值</li>
				    <li lay-id="bl">补零</li>
				    <li lay-id="jd">鉴定</li>
			  	</c:if>
			    <c:if test="${empty structureId}">
			    	<li lay-id="sjst" class="layui-this">数据视图</li>
			    </c:if>
			  </ul>
		 </div>
		<div style="height:570px;overflow:auto;">
			<c:if test="${not empty structureId}">
				<iframe id="tabIframe" name="statisticsIframe" width="100%" height="99%" src="${ctx}/dataTemp_toShowJGSTPage.do?treeId=${treeId}&structureId=${structureId}" frameborder="0"></iframe>
			</c:if>
		 	<c:if test="${empty structureId}">
			    	<iframe id="tabIframe" name="statisticsIframe" width="100%" height="99%" src="${ctx}/dataTemp_toShowSJSTPage.do?treeId=${treeId}" frameborder="0"></iframe>
			    </c:if>
		</div>
	</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript" src="${ctx}/layui2.5.1/layui/layui.js"></script>
	<script type="text/javascript">
	layui.use('element', function(){
		var element = layui.element;
		element.on('tab(statistics)', function(){
			var index = layer.load(2,{shade:[0.1,'#fff']});
			var tabFlag = this.getAttribute('lay-id');
			if(tabFlag == "sjst") {
				$("#tabIframe").attr("src","${ctx}/dataTemp_toShowSJSTPage.do?treeId=${treeId}&structureId=${structureId}");
			} else if(tabFlag == "jgst") {
			  	$("#tabIframe").attr("src","${ctx}/dataTemp_toShowJGSTPage.do?treeId=${treeId}&structureId=${structureId}");
			} else if(tabFlag == "zdxz") {
			 	$("#tabIframe").attr("src","${ctx}/dataTemp_toShowZDXZPage.do?structureId=${structureId}");
			} else if(tabFlag == "mc") {
			 	$("#tabIframe").attr("src","${ctx}/dataTemp_toShowMCPage.do?structureId=${structureId}");
			} else if(tabFlag == "px") {
				$("#tabIframe").attr("src","${ctx}/dataTemp_toShowPXPage.do?structureId=${structureId}");
			} else if(tabFlag == "zdz") {
				$("#tabIframe").attr("src","${ctx}/dataTemp_toShowZDZPage.do?structureId=${structureId}");
			} else if(tabFlag == "dmz") {
				$("#tabIframe").attr("src","${ctx}/dataTemp_toShowDMZPage.do?structureId=${structureId}");
			} else if(tabFlag == "bl") {
				$("#tabIframe").attr("src","${ctx}/dataTemp_toShowBLPage.do?structureId=${structureId}");
			} else if(tabFlag == "mrz") {
				$("#tabIframe").attr("src","${ctx}/dataTemp_toShowMRZPage.do?structureId=${structureId}");
			} else if(tabFlag == "zhzd") {
				$("#tabIframe").attr("src","${ctx}/dataTemp_toShowZHZDPage.do?structureId=${structureId}");
			} else if(tabFlag == "jd") {
				$("#tabIframe").attr("src","${ctx}/dataTemp_toShowJDPage.do?structureId=${structureId}");
			}
			layer.close(index);
		});
	});
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