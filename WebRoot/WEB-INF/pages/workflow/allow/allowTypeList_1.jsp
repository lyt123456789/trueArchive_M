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
	<form onsubmit="return navTabSearch(this);" action="demo_page1.html" method="post">
	<div class="panelBar"> 
		<ul class="toolBar">
		</ul>
	</div>
	</form>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/form_addForm.do">
	<input type="hidden"  value="${allowType }" name="allowType" id="allowType"/>
	<input type="hidden"  value="${glid }" name="glid" id="glid"/>
	
	<div id="w_list_print">
		<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="50">序号</th>
				<th width="200">许可名称</th>
				<th width="100">许可设置</th>
				<th class="tdrs"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="node" items="${list}" varStatus="index">
				<tr>
					<td class="tdrs" align="center">  
						${index.count }
					</td>   
					<td align="left">
						<c:if test="${empty node.wfn_type&&empty node.wfn_name}">结束</c:if>
						<c:if test="${node.wfn_type=='start'}">开始</c:if>
						<c:if test="${empty node.wfn_type&& not empty node.wfn_name}">${node.wfn_name }</c:if>
					</td>
					<td class="tdrs" align="center">  
						<a href="javascript:toSet('${node.wfn_id }');" class="uedset"><span>设置</span></a>
					</td>   
					<td class="tdrs"></td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</div>
	</form>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	<script type="text/javascript">
		function toSet(id){
			g.g('glid').value=id;
			g.g('thisForm').action='${ctx }/allow_getAllowList.do';
			g.g('thisForm').submit();
		};

	</script>
	</body>
		<%@ include file="/common/function.jsp"%>
</html>
