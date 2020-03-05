<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ page import="cn.com.trueway.base.util.*;"  %>
<html>
    <head>
		<title>办理列表</title>
		<base target="_self" />
		<link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
	</head>

    <body>
		<form id="searchForm" name="form1" action="${ctx}/send_sendwd.do" method="post" >
			<input type="hidden" value="${listType}" name="listType">
			<div class="displayTableForm">
				<ul class="searchUL">
				<li>
					<label>发文号</label><input mice-input="input" type="text" name="wh" id="docbw.wh" value="${wh}">
				</li>
				<li>
					<label>标题</label><input mice-input="input" type="text" name="title" id="docbw.title" value="${title}">
				</li>
				<li>
					<label>发文时间</label><input mice-input="input" type="text" id="docbw.startTime" name="startTime" value="${startTime}" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'docbw.endTime\')}'})"> - 
				</li>
				<li>
					<input mice-input="input" type="text" id="docbw.endTime" name="endTime" value="${endTime}" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'docbw.startTime\')}'})">
				</li>
				<li>
					<input id="search" type="button" value="查&nbsp;询" mice-btn="icon-search" onclick="displaytagform('searchForm',[{f:'${pageIndexParamName}',v:'1'}]);">
				</li>
				</ul>
				<display:table name="requestScope.processList" class="displayTable" pagesize="${requestScope.pageSize}" id="element" htmlId="htmlId" form="searchForm"
								partialList="true" size="${requestScope.totalRows}" requestURI="${ctx}/send_sendwd.do"  excludedParams="*">
				  <c:if test="${forComment eq 'true'}"> <display:column headerClass="split" class="docType"  sortable="false" nulls="false"><input type="radio" onclick="javascript:selectOne('${element.instanceId}','${element.docguid}','${element.title}')"/></display:column></c:if>
				  <display:column headerClass="split" class="docType"  title="序号" sortable="false" nulls="false"><font color="<c:if test="${element.status!=7}">#2A5FB2</c:if>">${element_rowNum}</font></display:column>
			      <display:column headerClass="split" class="docNum"  title="文号" sortable="false" nulls="false"><c:if test="${element.fwnh != null}"><font color="<c:if test="${element.status!=7}">#2A5FB2</c:if>">${element.jgdz}[${element.fwnh}]${element.fwxh}号</font></c:if></display:column>
			      <display:column headerClass="split" class="docTitle"  title="标题" sortable="false" nulls="false"> <a target="_blank" href="${ctx}/pm_sendDetil.do?instanceId=${element.instanceId}&docguid=${element.docguid}&listType=${listType}"><font color="<c:if test="${element.status!=7}">#2A5FB2</c:if>">${element.title }</font></a></display:column>
			      <display:column headerClass="split" class="docSendTime" title="印发单位" sortable="false" nulls="false"><font color="<c:if test="${element.status!=7}">#2A5FB2</c:if>">${element.yfdw}</font></display:column>
			      <display:column headerClass="split" class="docSendTime" title="更新日期" sortable="false" nulls="false"><font color="<c:if test="${element.status!=7}">#2A5FB2</c:if>">${fn:substring(element.submittm,0,16)}</font></display:column>
			      <display:column headerClass="split" class="docType" title="状态" nulls="false" sortable="false"> <c:if test="${element.status==0}"><font color="#2A5FB2">流转中</font></c:if><c:if test="${element.status==6}"><font color="#2A5FB2">已发送</font></c:if><c:if test="${element.status==7}">已作废</c:if></display:column>
				</display:table >
			</div>
		</form>
	<script src="${cdn_js}/base/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
	<script src="${cdn_js}/sea.js"></script>
	<script type="text/javascript">
	//回车监听
	document.onkeydown = function(e){ 
		if((e ? e.which : event.keyCode)==13 ) document.getElementById("search").click();
	};
	seajs.use('lib/form',function(){
		$('input[mice-btn]').cssBtn();
		$('input[mice-input]').cssInput();
		$('select[mice-select]').cssSelect();
    });
	seajs.use('lib/hovercolor',function(){
		$('table.displayTable').hovercolor({target:'tbody>tr'});
    });
	</script>
    </body>
</html>
