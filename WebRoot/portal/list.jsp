<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
    <link href="${ctx}/portal/src/css/common.css" rel="stylesheet">
    <link href="${ctx}/portal/src/css/list.css" rel="stylesheet">
    <script type="text/javascript">
    	$(document).ready(function () {
			$.ajax({
				url: "${url}",
				type: "GET",
				dataType: "jsonp", 
				success: function (data) {
					var obj = data.data;
					var arr = obj.content;
					var html = "";
					$.each( arr, function(index, content){
  						html += "<div class=\"l-row clearfix\">";
  						var className = content.className;
  						if('' != className){
							if(className.toLowerCase() == 'fw'){
								html += "<div class=\"icon fw\">";	
							}else if(className.toLowerCase() == 'sw'){
								html += "<div class=\"icon sw\">";
							}else{
								html += "<div class=\"icon fw\">";
							}
  						}else{
  							html += "<div class=\"icon fw\">";
  						}
  			            html += content.cate+"</div>"+
  			            "<a href=\"javascript:openLayerTabs('"+content.processId+"','"+content.content+"','"+content.content+"');\" class=\"txt\">"+content.title+"</a>"+
  			            "<div class=\"time\">"+content.date+"</div>"+
  			        	"</div>";
					});
					$("#info").empty().append(html);
				}
			});
		});
    </script>
</head>
<body>
    <div class="list1" id="info">
    <a href="javascript:openLayerTabs();"></a>
    </div>
</body>
<script type="text/javascript">
	function openLayerTabs(processId,title,url){
		parent.topOpenLayerTabs(processId,1200,600,title,url);
	}
</script>
</html>