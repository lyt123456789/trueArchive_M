<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>中威科技工作流表单系统</title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>

	<body style="overflow: auto;">
	<div class="panelBar"> 
		<ul class="toolBar"> 
			<li><a href="javascript:choose();" class="add"><span>确定选择</span></a></li>
		</ul>
	</div>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/group_getInnerUserList.do" >
		<div id="w_list_print">
		<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
			<thead>
				<tr>
					<th class="tdrs" align="left">
						<select id="sel_template">
							<c:forEach var="bean" items="${list}">
								<option value="${bean.id}">${bean.vc_cname}</option>
							</c:forEach>
						</select>
					</th>
				</tr>
			</thead>
		</table>
		</div>
	</form>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
	<script type="text/javascript">
	$("table.list", document).cssTable();
	function choose(){
		var obj=g.g('sel_template');
		window.returnValue=obj.value;
		window.close();
	};
	</script>
	</body>
	<%@ include file="/common/function.jsp"%>
</html>
