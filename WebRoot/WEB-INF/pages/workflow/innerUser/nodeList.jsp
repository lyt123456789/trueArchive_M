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
</head>
	<body>
		<iframe src="${ctx}/group_getListForNodeIframe.do?nodegroup=${nodegroup}&ids=${ids}" id="frame1" class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:495px;width:695px;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" scrolling="no"></iframe>
	</body>
<script type="text/javascript">
function choose(){
	var ifr = document.getElementById("frame1");
	var win = ifr.window||ifr.contentWindow;
	return win.choose();
}
</script>
</html>
