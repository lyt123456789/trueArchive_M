<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>常用语修改</title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerindex.jsp"%>
<!--表单样式-->
<link href="${ctx}/widgets/newstyle/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form id="commentform" method="post" action="${ctx}/comment_updatePersonalComment.do" style="font-family: 宋体; font-size: 12pt;">
<input type="hidden" name="personalComment.cmnt_id" id="personalComment.cmnt_id" style="width: 200px;height: 22px" value="${personalComment.cmnt_id}">
<input type="hidden" name="personalComment.user_id" id="personalComment.user_id" style="width: 200px;height: 22px" value="${personalComment.user_id}">
<table class="infotan" width="100%" align="center" style="margin-top: 10px;">
	<tr>
		<td width="30%" class="bgs ls">意见内容2222：</td>
		<td width="70%">
		<textarea style="width: 400px; height: 100px;" id="personalComment.content" name="personalComment.content">${personalComment.content}</textarea>
		</td>
	</tr>
	<tr>
		<td class="bgs ls">序号：</td> 
		<td >
			<input type="text" name="personalComment.sort_index" id="personalComment.sort_index" style="width: 200px;height: 22px;float: left !important;"  value="${personalComment.sort_index}"> 
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
