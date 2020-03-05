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

	<body TOPMARGIN="20" LEFTMARGIN="20">
	<form id="form1" method="POST" name="thisForm" action="${ctx }/form_addForm.do">
	<table class="infotan" width="100%">
<tr>
 <td width="20%" class="bgs ls">内置用户名：</td>
 <td width="80%">${innerUser.name }</td>
</tr> 
<tr>
 <td class="bgs ls">排序号：</td>
 <td>${innerUser.zindex}</td>
</tr>
<tr>
	<td class="bgs ls">入库人：</td>
	<td>
		${innerUser.inperson}
	</td>
</tr>
<tr>
	<td class="bgs ls">入库时间：</td>
	<td>
		<fmt:formatDate value="${innerUser.intime }" pattern="yyyy-MM-dd"/>
	</td>
</tr>
<tr>
	<td class="bgs ls">更新时间：</td>
	<td>
		<fmt:formatDate value="${innerUser.updatetime }" pattern="yyyy-MM-dd"/>
		
	</td>
</tr>
<tr>
	<td class="bgs ls">更新人：</td>
	<td>
		${innerUser.updateperson}
	</td>
</tr>
</table>
					
<div class="formBar pa" style="bottom:0px;width:100%;">  
<ul class="mr5"> 
	<li><a onclick="goHistroy();" name="CmdView" class="buttonActive"><span>返回</span></a></li>
</ul>
</div>
	</form>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	<script type="text/javascript">
		function sub(){
			g.g('form1').action='${ctx }/group_updateInnerUser.do';
			g.g('form1').submit();
		};
		function goHistroy(){
			parent.$('.page iframe:visible').attr('src',parent.$('.page iframe:visible').attr('src'));
		}	   
	</script>
	</body>
	<%@ include file="/common/function.jsp"%>
</html>
