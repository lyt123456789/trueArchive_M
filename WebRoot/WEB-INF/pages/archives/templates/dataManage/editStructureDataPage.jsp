<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>详情页</title>
    <link rel="stylesheet" href="css/list.css?t=123">
     <link rel="stylesheet" href="css/form.css?t=111">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <script src="js/jquery-1.8.2.min.js"></script>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <style type="text/css">
    </style>
</head>
<body>
<input type="button" id="save"   value="保存">
<form id="dataForm">
	<input type="hidden" id="id" name="id" value="${id}">
	<input type="hidden" id="treeId" name="treeId" value="${treeId}">
	<input type="hidden" id="structureId" name="structureId" value="${structureId}">
	<input type="hidden" id="parentId" name="parentId" value="${parentId}">
	<div class="show">
	<div class="table2" style="height: 65%;">
	    <table cellspacing="0" cellpadding="0">
			<c:forEach items="${etList}" var="tag" varStatus="state">
				<c:set var="key" value="C${tag.id}"></c:set>
				<c:if test="${state.count%2!=0}">
					<td width="110" class="name "> ${tag.esIdentifier}</td>
					<td width="323"><input type="text" id="${key}" name="${key}" value="${dataMap[key]}"
						<c:if test="${tag.esIsNotNull eq '1'}">
							style="background-color: #ffeeee;border: 1px solid #ff7870;"
						</c:if>
						/>
					</td>
				</c:if>
				<c:if test="${(state.index+1)%2==0 }">
					<td width="110" class="name "> ${tag.esIdentifier}</td>
					<td width="323">
						<input type="text" id="${key}" name="${key}" value="${dataMap[key]}"
							<c:if test="${tag.esIsNotNull eq '1'}">
								style="background-color: #ffeeee;border: 1px solid #ff7870;"
							</c:if>
						/>
					</td>
					</tr>
					<tr>
				</c:if>
			</c:forEach>
		</table>
	</div>
</form>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
$(document).ready(function (){
	$(".show").css("height",$(window).height()+10);
	$(".list_table").css("height",$(".show").height()-$(".table2").height()-$(".dz").height()-10);
});
	
</script>
</html>