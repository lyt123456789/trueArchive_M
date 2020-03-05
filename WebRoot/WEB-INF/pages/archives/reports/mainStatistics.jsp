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
	    	iframe {
	    		width:100%;
	    		height:1000px;
	    	}
	    </style>
	</head>
	<body>
		<div class="layui-tab layui-tab-brief" lay-filter="statistics">
		  <ul class="layui-tab-title">
		    <li lay-id="sjst" class="layui-this">数据视图</li>
		    <li lay-id="jgst">结构视图</li>
		    <li lay-id="zdxz">字段选择</li>
		  </ul>
		 </div>
		<div style="height:680px;overflow:auto;">
			<iframe id="tabIframe" name="statisticsIframe" src="${ctx}/rep_toWorkReportsPage.do" frameborder="0"></iframe>
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
				$("#tabIframe").attr("src","${ctx}/rep_toWorkReportsPage.do");
			} else if(tabFlag == "jgst") {
			  	$("#tabIframe").attr("src","${ctx}/rep_toAimStatisticsReports.do");
			} else if(tabFlag == "zdxz") {
			 	$("#tabIframe").attr("src","${ctx}/rep_toOtherAimStatisticsReports.do");
			} 
			layer.close(index);
		});
	});
	
	</script>
</html>