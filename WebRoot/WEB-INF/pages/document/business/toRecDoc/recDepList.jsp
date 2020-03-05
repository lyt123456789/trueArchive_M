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
<body > 
<div id="pageContent" class="pageContent"  style="overflow:auto;" >
	 <div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" >
		<thead>
			<tr>
              <td width="5%" style="font-weight:bold;text-align:center;">序号</td>
			  <td width="20%" style="font-weight:bold;text-align:center;">部门</td>
			  <td width="15%" style="font-weight:bold;text-align:center;">接收情况</td>
			</tr>
		</thead>
		<tbody>
			 <c:forEach var="item"  items="${list}" varStatus="status">
				<tr>
                   <td align="center">${status.count}</td>	
				   <td align="center">${item[0]}</td>	
				   <td align="center">
						<c:if test="${item[1]=='0'}">待收</c:if>
						<c:if test="${item[1]=='1'}"><font color="red">已收</font></c:if>
				 </td>
				</tr>
			</c:forEach> 
		</tbody>
	</table>
	</div>
</div>
<div class="formBar pa" style="bottom:0px;width:100%;">  
	<div id="div_god_paging" class="cbo pl5 pr5"></div> 
</div>
</body>
<%@ include file="/common/function.jsp"%> 
<script>
</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script>
	$('#pageContent').height(400);
</script>
</html>
