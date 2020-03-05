<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%
	//获取系统地址
	HttpServletRequest httpRequest = (HttpServletRequest)request;
	String serverUrl = httpRequest.getScheme()+"://" + httpRequest.getServerName() + ":"+ httpRequest.getLocalPort() + httpRequest.getContextPath();
	httpRequest.setAttribute("serverUrl", serverUrl);
%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	 <link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>		
<body>
 <div class="pageHeader" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr> 
				<td>
					<div class="buttonContent"><button type="button" onclick="install();">初始化控件</button></div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="itemid" value="${itemid}">
	</div>
	<div >
		<iframe src="" width="98%;" height="100%;" id="install"></iframe>
	</div>
</div>
<div class="formBar pa" style="bottom:0px;width:100%;">  
 <div id="div_god_paging" class="cbo pl5 pr5"></div> 
<input type="hidden" name="pdfPath" id="pdfPath" value="${serverUrl}/form/html/templateModel.pdf">
<input type="hidden" name="commentJson" id="commentJson" value="">
</div> 
</body>
<%@ include file="/common/function.jsp"%> 
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script>
function install(){
	var iframe = document.getElementById("install");
	iframe .src="${ctx}/pdfocx/1.jsp";
	
}
</script>
</html>