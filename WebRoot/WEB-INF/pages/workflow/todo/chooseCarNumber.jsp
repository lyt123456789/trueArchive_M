<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>车牌号</title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>		
<body>
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<c:forEach var="cars" items="${carList}"> 
			<tr>
				<td align="center"><input type="radio" name="carNumber" value="${cars.key},${cars.value}" onclick="choose()"/></td>
				<td align="center">${cars.value}</td>
			</tr>
		</c:forEach>
	</table>
</body>
<script>
	function choose(){
		var cars = document.getElementsByName("carNumber");
		var carChoosed = "";
		for(var i=0;i < cars.length;i++){
			if(cars[i].checked){
				carChoosed = cars[i].value;
			}
		}
		window.returnValue = carChoosed;
		window.close();
	}
</script>
</html>