<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
</head>
<body>	
	<div class="wf-layout">
		<div id="pageContent2" class="wf-list-wrap" style="padding:0px 0px 0 10px">
			<table class="wf-fixtable" layoutH="0">
				<thead>
					<tr>
						<th width="5%" align="center"><input type="checkbox" name="allMatchChk" id="allMatchChk" onclick="allChk_matchList();"></th>
						<th width="5%" align="center">序号</th>
						<th width="15%"  align="center">子部门名称</th>
						<th width="15%" align="center">排序号</th>
					</tr>
				</thead>
				<c:forEach var="e" items="${list}" varStatus="status"> 
					<tr onclick="chooseMatch('${e.id}')">
						<td align="center">
			    			<input type="checkbox" name="chcs_match" value="${e.id}">
			    		</td>
				    	<td align="center" itemid="${e.id}">
				    		${status.count}
				    	</td>
				    	<td align="center" workflownnameid="1" title="${e.name}">
							${e.name}
				    	</td>
				    	<td align="center"  workflownnameid="1" title="${e.orderNum}">
				    		${e.orderNum}
				    	</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>


</body>
	<script type="text/javascript">
	</script>
	<%@ include file="/common/function.jsp"%>
		<script>
		$('#pageContent').height($(window).height()-40);
	</script>
</html>