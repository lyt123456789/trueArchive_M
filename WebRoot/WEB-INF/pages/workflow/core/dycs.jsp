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
  <body  >
	<br/>
	<br/>
	<span style="padding: 40px">打印次数：<input type="text" id="dycs" name="dycs" /> </span>
	<div class="formBar pa" style="bottom:0px;width:100%;">  
<ul class="mr5"> 
<li><a class="buttonActive" href="javascript:baocun();"><span>确定</span></a></li>
</ul>
</div>
</body>

<script type="text/javascript">//以下必须有
function baocun(){
	var dycs = $('#dycs').val();
	if(!dycs){
		alert('请输入打印次数');
		return;
	}
	var reg = /^\d+$/;
	if(!dycs.match(reg)){
		alert('打印次数只能是数字');
		return;
	};
	window.returnValue = dycs;
	window.close();
}
//以上必须有</script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/sea.js"></script>
</html>
