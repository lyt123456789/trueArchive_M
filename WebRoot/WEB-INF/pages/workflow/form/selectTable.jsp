<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
    <script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${ctx}/assets/plugins/laydate/laydate.js" type="text/javascript"></script>
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body >
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/form_addForm.do">
	<table class="infotan" width="100%">
		<tr>
			<td width="20%">选择</td>
			<td width="40%">表中文名</td>
			<td width="40%">表英文名</td>
		</tr>
		<c:forEach items="${tableList}" var="table">
			<tr>
				<td><input type="checkbox" id="tableName"  name="tableName" value="${table.vc_tablename }"/></td>
				<td>${table.vc_name }</td>
				<td>${table.vc_tablename }</td>
			</tr>
		</c:forEach>
	</table>	
</form> 
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
<script type="text/javascript">
		function selectTable(){
			var table=document.getElementsByName('tableName');
			var tableNames="";
			for (var i=0; i<table.length; i++){  
				if(table[i].checked ==true){      
					tableNames=tableNames+";"+table[i].value;             
				}  
			}  
			if(tableNames.indexOf(";")>-1){
				tableNames = tableNames.substring(1);
			}
			return tableNames;
		}
</script>
</body>
<%@ include file="/common/function.jsp"%>
</html>
