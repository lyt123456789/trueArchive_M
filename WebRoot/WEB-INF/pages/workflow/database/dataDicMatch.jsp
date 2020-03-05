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
		<div id="pageContent2" class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
				<thead>
					<tr>
						<th width="5%" align="center"><input type="checkbox" name="allMatchChk" id="allMatchChk" onclick="allChk_matchList();"></th>
						<th width="8%" align="center">序号</th>
						<th width="30%" >匹配表编码</th>
						<th width="25%" >匹配表名称</th>
						<th>复制</th>
					</tr>
				</thead>
				<c:forEach var="e" items="${list}" varStatus="status"> 
					<tr onclick="chooseMatch('${e.id}')">
						<td align="center">
			    			<input type="checkbox" name="chcs_match" value="${e.id}">
			    		</td>
				    	<td align="center" itemid="${e.id}">
				    		${(selectIndex-1)*pageSize+status.count}
				    	</td>
				    	<td workflownnameid="1" title="${e.tableCode}" >
							${e.tableCode}
				    	</td>
				    	<td  workflownnameid="1" title="${e.formName}">
				    		${e.formName}
				    	</td>
				    	<td  workflownnameid="1" align="center">
				    		<a class="wf-btn-blue" onclick="copyUrl('${e.id}');" >
				                <i class="wf-icon-plus-circle"></i> 复制url
				            </a>
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
		$('#pageContent').height($(window).height()-70);
	</script>
</html>