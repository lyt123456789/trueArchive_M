<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
		<div id="pageContent" class="pageContent"  style="overflow:auto;">
			<table width="100%" class="list">
				<tr>
					<td>
						<textarea  style="width:98%;height:75px;" id="url" name="url" >${url}</textarea>
					</td>
				</tr>
			</table>
		</div>


</body>
</html>