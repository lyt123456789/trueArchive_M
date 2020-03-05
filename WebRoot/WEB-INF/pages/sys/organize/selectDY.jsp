<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
</head>
<body>
	<div class="tw-layout">
		<div class="tw-list-top">
			<div class="tw-search-bar">
				
					<button class="tw-btn-primary" onclick="sure();">
						<i class="tw-icon-pencil"></i> 确定
					</button>
				
			</div>
		</div>
		<div class="tw-list-wrap">
			<form id="thisForm" method="POST" name="thisForm" action="${ctx }/organize_sureDY.do">
				<table class="tw-fixtable" layoutH="140">
					<thead>
						<tr>
							<th>多音字</th>
							<th>选项</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach var="map" items="${mapAll}" varStatus="status">
							<tr>
								<td align="center">${map.key}</td>
								<td><c:forEach var="str" items="${map.value}">
										<input type="radio" name="${map.key }" value="${str}">${str }
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</c:forEach></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div class="tw-list-ft" id="pagingWrap"></div>
	</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	function sure(){
		//document.getElementById('thisForm').submit();
		$.ajax({
			type:"post",
			url:"${ctx }/organize_sureDY.do",
			data:$('#thisForm').serialize(),
			cache : false,
			async: false,
			success:function(result){
				if(result=="true"){
					alert("初始化成功！");
					var obj = "成功";
					parent.reload();
                   	parent.layer.closeAll();
				}else{
					alert("初始化失败！");
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("错误信息："+errorThrown+"("+textStatus+")!");
			}
		});
	}

</script>
</html>