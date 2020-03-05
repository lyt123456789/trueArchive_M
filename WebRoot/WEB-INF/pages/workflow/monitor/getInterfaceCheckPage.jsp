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
    <link rel="stylesheet" type="text/css" href="${ctx}/monitor/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/monitor/css/index.css">
	<script type="text/javascript" src="${ctx}/monitor/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${ctx}/monitor/js/echarts.min.js"></script>
</head>
<body style="overflow: scroll;zoom:0.88">
<div class="content w1200">
	<div class="check-box">
		<div class="box-info">
			<div class="fl"><img src="${ctx}/monitor/img/check-img.png"></div>
			<div class="fl check-text">
				<p>共${total }项接口，已检测${checked }项</p>
				<p class="gray-text">已检测${checked }项，其中${checkSuccess }项接口正常，${checkFail }项接口异常</p>
			</div>
			<button class="fr check-btn" id="checkButton" onclick="checkInterface();" style="width:120px;line-height: 40px;font-size: 24px ;height: 45px">检测</button>
		</div>
	</div>
	<div class="check-list">
		<ul>
			<c:forEach items="${interfaceList}" var="interface" varStatus="status">
				<li>
					<div class="rel-img rel">
						<img src="${ctx}/monitor/img/${interface.iconPath}">
						<span class="abs-img abs">
							<c:choose>
								<c:when test="${interface.result == '1' }">
									<img src="${ctx}/monitor/img/checked.png">
								</c:when>
								<c:when test="${interface.result == '2' }">
									<img src="${ctx}/monitor/img/error.png">
								</c:when>
								<c:otherwise>
									<img src="${ctx}/monitor/img/check.png">
								</c:otherwise>
							</c:choose>
						</span>
					</div>
					<p class="list-text">${interface.interfaceName}</p>
					<c:choose>
						<c:when test="${interface.result == '1' }">
							<button class="interface-btn">接口正常</button>
						</c:when>
						<c:when test="${interface.result == '2' }">
							<button class="interface-btn error-btn">接口异常</button>
						</c:when>
						<c:otherwise>
							<button class="interface-btn wait-btn">等待检测</button>
						</c:otherwise>
					</c:choose>
					
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
</body>
<script>
function checkInterface(){
	document.getElementById('checkButton').innerHTML='正在检测中...'
	$.ajax({   
		url : '${ctx}/monitor_checkInterface.do',
		type : 'POST',   
		cache : false,
		async : false,
		error : function() {  
			alert('接口检测出错');
		},
		success : function(result) {
			/* document.getElementById('checkButton').innerHTML='检测完毕'; */
			window.location.href = "${ctx}/monitor_getInterfaceCheckPage.do";
			
		}
	});
}
</script>
</html>