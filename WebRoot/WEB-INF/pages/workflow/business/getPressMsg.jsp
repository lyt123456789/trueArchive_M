<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle")%></title>

<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerindex.jsp"%>
<!--表单样式-->
<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<base target="_self" />
<body>
	<form id="msgForm" method="post" action="${ctx}/business_addPressMsg.do" style="font-family: 宋体; font-size: 12pt;">
		<input type="hidden" name="processId" id="processId" value="${processId }">
		<table class="infotan" width="100%" align="center">
			<tr>
			<td colspan="2" align="center">
				<h1><font align="center">催办</font></h1>
			</td>
			</tr>
			<tr>
				<td width = "28%">办件标题：</td>
				<td width = "72%" title="${pressTitle }">
					${pressTitle}
					<input type="hidden" style="width:200px;" id="pressTitle" name="pressTitle" value="${pressTitle}" readonly="readonly" >
				</td>	
			</tr>
			<tr>
				<td width = "28%">接收人：</td>
				<td width = "72%">
					${userName_wfp}
					<input type="hidden" style="width:200px;" id="userName_wfp" name="userName_wfp" value="${userName_wfp}" readonly="readonly" >
				</td>	
			</tr>
			<tr>
				<td width = "28%">发送时间：</td>
				<td width = "72%">
					${pressApplyTime}
					<input type="hidden" style="width:200px;" id="pressApplyTime" name="pressApplyTime" value="${pressApplyTime}" readonly="readonly" >
				</td>	
			</tr>
			<tr>
				<td width = "28%">是否超期：</td>
				<td width = "72%">
					${isBeyond}
					<input type="hidden" style="width:200px;" id="isBeyond" name="isBeyond" value="${isBeyond}" readonly="readonly" >
				</td>	
			</tr>
			<tr>
				<td width = "28%">催办意见：</td>
				<td width = "72%">
					<textarea style="width: 300px; height: 100px;" id="pressContent" name="pressContent" >${pressContent }</textarea>
				</td>
			</tr>	
		</table>
	</form>
	<script type="text/javascript">
		function checkForm(){
			var processId = document.getElementById("processId").value;
			var userName_wfp = document.getElementById("userName_wfp").value;
			var pressContent = document.getElementById("pressContent").value;
			$.ajax({
				url : '${ctx}/business_addPressMsg.do',
				type : 'POST',  
				cache : false,
				async : false,
				data : {'processId':processId,'userName_wfp':userName_wfp,'pressContent':pressContent},
				error : function() {
					alert('AJAX调用错误(table_addPressMsg.do)');
				},
				success : function(msg) { 
					alert("催办消息发送成功！");
					//parent.location.reload();
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				}
			});
			
		}
	</script>
</body>
</html>