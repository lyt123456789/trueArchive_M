<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
	<style type="text/css">
	.layout {
	}
	.layout .ly-left {
	  margin-top:0px;
	  float: left;
	  display: block;
	  width: 1%;
	}
	.layout .ly-right {
	  float: left;
	  display: block;
	  width: 99%;
	}
	</style>
</head>
<body>
<div class="layout">
	<div class="ly-left">
	</div>
	<div class="ly-right">
		<div class="wf-layout">
			<div  class="wf-list-wrap" >
				<form target="frame_nr" id="form_nr" method="post" action="${ctx}/depConfig_getDep_F.do">
				</form>
				<iframe name="frame_nr" id="frame1"  class="frame1" frameborder="auto" marginheight="0"  marginwidth="0" border="0" style="height:640px;width:98%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" scrolling="no" src=""></iframe>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
document.getElementById('form_nr').submit();
</script>
</html>