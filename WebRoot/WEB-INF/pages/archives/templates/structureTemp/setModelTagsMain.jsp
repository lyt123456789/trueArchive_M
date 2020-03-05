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
	    .layui-tab {
		    margin: 0px 0;
		    text-align: left!important;
		}
	    </style>
	</head>
	<body>
		<div class="layui-tab layui-tab-brief" lay-filter="statistics">
			  <ul class="layui-tab-title">
			  		<c:if test="${'project' eq esType}">
			  			<li lay-id="project" class="layui-this">项目级</li>
			  		</c:if>
			  		<c:if test="${'project' eq esType || 'file' eq esType}">
			  			<li lay-id="file" <c:if test="${'file' eq esType}">class="layui-this"</c:if>>案卷级</li>
			  		</c:if>
			  		<c:if test="${'project' eq esType || 'file' eq esType || 'innerFile' eq esType }">
			  			<li lay-id="innerFile" <c:if test="${'innerFile' eq esType}">class="layui-this"</c:if>>卷内级</li>
			  		</c:if >
				  	<li lay-id="document" >电子文件级</li>
			  </ul>
		</div>
		<div style="height:590px;overflow:auto;">
			<iframe id="tabIframe" name="statisticsIframe" width="100%" height="99%" src="${ctx}/str_toSetModelTagsEditPage.do?modelId=${modelId}&esType=${esType}&onlyLook=${onlyLook}" frameborder="0"></iframe>
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
			var esType = "";
			if(tabFlag == "project") {
				esType="project";
			} else if(tabFlag == "file") {
				esType="file";
			} else if(tabFlag == "innerFile") {
				esType="innerFile";
			} else if(tabFlag == "document") {
				esType="document";
			}
			$("#tabIframe").attr("src","${ctx}/str_toSetModelTagsEditPage.do?modelId=${modelId}&onlyLook=${onlyLook}&esType="+esType);
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