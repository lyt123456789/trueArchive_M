<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
<html>
    <head>
        <title>文号管理</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
         <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
		 <table class="displayTable">
		 <thead><th class="split">名称</th><th class="split">内容</th><th class="split">类型</th><th class="split"></th></thead>
		<c:forEach items="${slist}" var="s" varStatus="statu">
			<tr><td><input id="strategy${statu.count}" readonly="readonly" value="${s.strategyId}"/></td>
			<td width="190px;"><span title="${s.content}"><input style="width:190px;" value="${s.content}"/></span></td>
			<td><input readonly="readonly" value="${s.type}"/></td>
			<td><span title="点击序号使用"><input type="button" value="${statu.count}"/></span></td></tr>
		</c:forEach>
		</table>
		
		<script type="text/javascript">
			$("input[type=button]").bind("click",function(){
				var index = $(this).val();
				var obj = new Object();
				obj.biaoshi=$("#strategy"+index).val();
				window.returnValue = obj;
	            window.close();
			});
		</script>
    </body>
</html>
