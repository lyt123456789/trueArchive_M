<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table class="infotan mt10" width="100%">
	<tr>
	 	<td width="20%" class="bgs">1------新建/选择工作流</td>
	</tr>
	<tr>
		<td width="20%" class="bgs">2------新建/选择业务表</td>
	</tr>
	<tr>
	 	<td width="20%" class="bgs">3------新建表单及配置</td>  
	</tr>
	<tr>
	 	<td width="20%" class="bgs">4------新建流程用户组</td>
	</tr>
	<tr>
	 	<td width="20%" class="bgs">6------绘制流程图</td>
	</tr>
	<tr>
	 	<td width="20%" class="bgs">7------设置许可权限</td>
    </tr>
    <tr>
	 	<td width="20%" class="bgs">8------流程绑定文号（只限公文流程）</td>
    </tr>
	<tr>
	 	<td width="20%" class="bgs">9------新建工作事项</td>
	</tr>
</table>
</body>
<script src="${ctx}/dwz/style/js/jquery-1.7.1.min.js"></script>
<script src="${ctx}/dwz/style/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/dwz/style/js/jquery.tab.js"></script>
<script>
$('.tablelist').swapRowColor({target:'tbody>tr'});
</script>
</html>