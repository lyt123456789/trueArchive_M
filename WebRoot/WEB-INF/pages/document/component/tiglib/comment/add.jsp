<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>新增常用语</title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerindex.jsp"%>
<!--表单样式-->
<link href="${ctx}/widgets/newstyle/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form id="commentform" method="post" action="${ctx}/comment_addPersonalComment.do" style="font-family: 宋体; font-size: 12pt;">
<table class="infotan" width="100%" align="center" style="margin-top: 10px;">
	<tr>
		<td width="30%" class="bgs ls">意见内容111：</td>
		<td width="70%">
			<textarea style="width: 400px; height: 100px;" id="personalComment.content" name="personalComment.content"></textarea>
	</tr>
	<tr>
		<td  class="bgs ls">序号：</td> 
		<td >
			<input type="text" name="personalComment.sort_index" id="personalComment.sort_index" style="width: 200px;height: 22px;float: left !important;" > 
		 &nbsp; &nbsp; &nbsp; &nbsp;<font color="red">(只能填写整数)</font>
		</td>
	</tr>
</table>
<div class="dhrx-link">
    <a class="dhrx-submit" href="javascript:void(0);" onclick="checkForm();">提交</a>
    <a class="dhrx-cancel" href="javascript:void(0);" onclick="window.history.go(-1);">返回</a>
</div>
</form>
</body>
<script type="text/javascript">
function checkForm(){
	document.getElementById('commentform').submit();
}
</script>
<%@ include file="/common/function.jsp"%>
</html>
