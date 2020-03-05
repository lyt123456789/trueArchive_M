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
</head>
<body style="overflow:auto;">
<div class="wf-layout">
	<form id="thisForm" method="POST" name="thisForm" action="${ctx}/field_getFieldList.do" >
		<div class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
		    	<thead>
			    	<tr>
		    		<th width="30px" align="center">
			    		序号
			    	</th>
			    	<th width="" align="center">
			    		标签名
			    	</th>
			    	<th width="" align="center">
			    		中文名称
			    	</th>
			    	<th width="60px" align="center">
			    		标签类型
			    	</th>
			    	<th width="130px" align="center">
			    			权限
			    			<select id="vc_limit_all" style="width:80px" onchange="checkall(this)">
				    			<option value="1">只读</option>
				    			<option value="2">读写</option>
				    			<option value="0">隐藏</option>
				    		</select>
			    	</th><th width="150px" align="center">
			    			是否必填
			    			<select id="vc_isbt_all" style="width:80px" onchange="checkisbt(this)">
				    			<option value="0">非必填</option>
				    			<option value="1">必填</option>
				    		</select>
			    	</th><th width="100px" align="center">
			    			批阅
			    	</th>
		    	</tr>
		    </thead>
	    	<c:forEach items="${list}" var="d" varStatus="n">
		    	<tr>
			    	<td align="center">
			    		${n.count}
			    	</td>
			    	<td>
			    		<input name="vc_tagname" type="hidden" value="${d.formtagname}"/>
			    		${d.formtagname}
			    	</td>
			    	<td>
			    		${d.columnCname}
			    	</td>
			    	<td >
			    		${d.formtagtype}
			    	</td>
			    	<td align="center">
			    		
			    		<select name="vc_limit" style="width:80px">
			    			<option value="0" <c:forEach var="permit" items="${selectedList}"><c:if test="${d.formtagname==permit.vc_tagname&&permit.vc_limit==0}">selected="selected"</c:if></c:forEach>>隐藏</option>
			    			<option value="1" <c:forEach var="permit" items="${selectedList}"><c:if test="${d.formtagname==permit.vc_tagname&&permit.vc_limit==1}">selected="selected"</c:if></c:forEach>>只读</option>
			    			<option value="2" <c:forEach var="permit" items="${selectedList}"><c:if test="${d.formtagname==permit.vc_tagname&&permit.vc_limit==2}">selected="selected"</c:if></c:forEach>>读写</option>
			    		</select>
			    	</td>
			    	<td align="center">
			    		<select name="isbt" style="width:80px">
			    			<option value="0" <c:forEach var="permit" items="${selectedList}"><c:if test="${d.formtagname==permit.vc_tagname&&permit.isbt==2}">selected="selected"</c:if></c:forEach>>非必填</option>
			    			<option value="1" <c:forEach var="permit" items="${selectedList}"><c:if test="${d.formtagname==permit.vc_tagname&&permit.isbt==1}">selected="selected"</c:if></c:forEach>>必填</option>
			    		</select>
			    	</td>
			    	<td align="center">
			    		<select name="ispy" style="width:80px">
			    			<option value="0" <c:forEach var="permit" items="${selectedList}"><c:if test="${d.formtagname==permit.vc_tagname&&permit.ispy==0}">selected="selected"</c:if></c:forEach>>不显示</option>
			    			<option value="1" <c:forEach var="permit" items="${selectedList}"><c:if test="${d.formtagname==permit.vc_tagname&&permit.ispy==1}">selected="selected"</c:if></c:forEach>>显示</option>
			    		</select>
			    	</td>
		    	</tr>
		    </c:forEach>
    		</table>
    	</div> 
	</form>
</div>
</body>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
<script type="text/javascript">
		function checkall(src){
			var v=src.value;
			var selects=g.gbn('vc_limit');
			for(var i=0;i<selects.length;i++){
				selects[i].value=v;
			};
		};
		function checkisbt(src){
			var v=src.value;
			var selects=g.gbn('isbt');
			for(var i=0;i<selects.length;i++){
				selects[i].value=v;
			};
		};
		
	</script>
</html>