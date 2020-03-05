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
	    		height:100%;
	    		position:absolute;
	    		top:10px;
	    		left:15%;
	    		width:82%;
	    	}
		    .layui-tab-title li{
	            display: block;
	        }
	        .layui-tab-title{
	            float: left;
	            width: 200px;
	        }
	        .layui-tab-content{
	            float: left;
	            width: 500px;
	        }
	    </style>
	</head>
	<body>
	    <div class="layui-tab layui-tab-brief" lay-filter="statistics">
  		  <ul class="layui-tab-title">
		    <li lay-id="jy" class="layui-this">借阅申请管理</li>
		    <li lay-id="dg">调归卷申请管理</li>
		  </ul>
		<div>
			<iframe id="tabIframe" name="statisticsIframe" src="${ctx}/using_showLendingList.do?statusSe=1&applyFlag=${applyFlag}" frameborder="0"></iframe>
		</div>
	</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript" src="${ctx}/layui2.5.1/layui/layui.js"></script>
	<script type="text/javascript">
	layui.use('element', function(){
		var element = layui.element;
		element.on('tab(statistics)', function(){
		   var tabFlag = this.getAttribute('lay-id');
		   if(tabFlag == "jy") {
			   $("#tabIframe").attr("src","${ctx}/using_showLendingList.do?statusSe=1&applyFlag=${applyFlag}");
		   } else if(tabFlag == "dg") {
			   $("#tabIframe").attr("src","${ctx}/using_showTransferList.do?statusSe=1&shFlag=1&applyFlag=${applyFlag}");
		   } 
		});
	});
	
	</script>
</html>