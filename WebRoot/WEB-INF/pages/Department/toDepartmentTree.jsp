<%@ include file="/common/header.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
	<head>
		<title>部门_人员组织树框架页面</title>
		<link href="${ctx}/widgets/tree/css/index_right.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
	<div class="w-here">
		<div class="w-here-box"><span>当前位置：首页 >> 系统设置 >> 人员授权</span></div>
	</div>
	<table style="margin-top: 5px;margin-left: 5px;" class="tbl-main-right">
		<tr>
			<td class="td-tree"><iframe  class="left-iframe"
				src="${pageContext.request.contextPath}/departmentTree_showDepartmentTree.do"
				name="departmentTree_menu" id="departmentTree_menu" scrolling="auto" frameborder="0"></iframe>
			</td>
			<td width="3px">&nbsp;</td>
			<td>
			
			<iframe  class="td-tree-right-iframe" src="${pageContext.request.contextPath}/common/welcome.jsp" name="departmentTree_show"
				id="departmentTree_show" scrolling="no" frameborder="0"></iframe></td>
		</tr>
	</table>
</body>
	
</html>


