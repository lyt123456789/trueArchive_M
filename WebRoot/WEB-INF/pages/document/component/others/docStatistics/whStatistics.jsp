<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>文号统计</title>
		<link href="${cdn_imgs}/mice/form.css" type="text/css" rel="stylesheet" />
		<link  href="${cdn_imgs}/ued.base.css" type="text/css" rel="stylesheet" />
		<link href="${cdn_imgs}/dm/css/ued.module.css"  type="text/css" rel="stylesheet" />
        <script type="text/javascript" src="${ctx}/widgets/My97DatePicker/WdatePicker.js" defer="defer" charset="UTF-8"></script>
	</head>
	<body>
		<div class="bread-box">
			<div class="bread-title">
				<span class="bread-icon"></span>
				<span class="bread-links">
					<span class="bread-start">当前位置</span>
					<span class="bread-split">&raquo;</span>
					<span class="bread">文号统计 </span>
				</span>
				<span class="bread-right-border"></span>
			</div>
		</div>
		<form id="chaxunform" name="chaxunform" action="${ctx}/docStatistics_toViewWh.do?receDeptId=${receDeptId}&sendDeptId=${sendDeptId}" method="post" >
			<input type="hidden" name="isReceivedTime" value="${isReceivedTime }"/>
			<input type="hidden" name="receDeptId" value="${receDeptId }"/>
			<input type="hidden" name="sendDeptId" value="${sendDeptId }"/>
			<input type="hidden" name="beginTime" value="${beginTime }"/>
			<input type="hidden" name="endTime" value="${endTime }"/>
			<display:table class="displayTable" name="requestScope.whInfoList" 
				pagesize="8" htmlId="displayTable" id="element" partialList="true"
				export="false" size="${fn:length(whInfoList)}" excludedParams="*"
				requestURI="${ctx}/docStatistics_toViewWh.do" form="chaxunform">
				<display:column headerClass="split" class="docNum" title="序号" sortable="false" maxLength="20">${element_rowNum}</display:column>
				<display:column headerClass="split" class="docNum" title="文号"sortable="false">${element.wh}</display:column>
				<display:column headerClass="split" class="docTitle" title="标题"sortable="false">${element.title}</display:column>
				<display:column headerClass="split" class="docNum" title="发文日期"sortable="false">${element.date}</display:column>
			</display:table>
		</form>
	</body>
</html>
