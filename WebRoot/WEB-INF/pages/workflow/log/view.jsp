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
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form id="itemform" method="post" action="${ctx}/log_view.do" style="font-family: 宋体; font-size: 12pt;">
<table class="infotan" width="100%">

	<c:if test="${log.prcoess_title != null }">
	<tr>
		<td width="20%" class="bgs ls">办件标题：</td>
		<td width="80%">
			${log.prcoess_title }
		</td>
	</tr>
	
	</c:if>
	
	<c:if test="${log.attchmentname != null }">
	<tr>
		<td width="20%" class="bgs ls">附件名称：</td>
		<td width="80%">
			${log.attchmentname }
		</td>
	</tr>
	
	</c:if>
	
	<tr>
		<td width="20%" class="bgs ls">日志信息：</td>
		<td width="80%">
			${log.msg }
		</td>
	</tr>
	<tr>
		<td width="20%" class="bgs ls">日志等级：</td> 
		<td width="80%">
			${log.loglevel }
		</td>
	</tr>
	<tr>
		<td class="bgs ls">对应方法：</td>
		<td>
			${log.method }
		</td>
	</tr>
	<tr>
		<td class="bgs ls">关联信息：</td>
		<td>
			${log.line }
		</td>
	</tr>
	<tr>
		<td class="bgs ls">操作人员：</td>
		<td>
			${log.username }
		</td>
	</tr>	
	<tr>
		<td class="bgs ls">操作时间：</td>
		<td>
			${log.createtime }
		</td>
	</tr>
	
	<tr>
		<td colspan="2" style="text-align: center;">
		<a class="wf-btn" href="javascript:cancel();"><i class="wf-icon-minus-circle"></i><span>关闭</span></a>
		
		</td>
	</tr>		
</table>
</form>
</body>
<script type="text/javascript">
function cancel(){
	parent.layer.closeAll();
}

</script>
<%@ include file="/common/function.jsp"%>
</html>
