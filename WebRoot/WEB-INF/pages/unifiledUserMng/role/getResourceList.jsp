<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
   <link href="${ctx}/assets-common/css/common.css" type="text/css" rel="stylesheet" />
	<style>
		.new-htable {
			margin-top:20px;
			width:96%;
			margin-left:3%;
		}
		.new-htable tr th{
			text-align:right;
			color:#333333;
			font-size:16px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;

		}
		.new-htable tr td{
		text-align:left;
			color:#333333;
			font-size:15px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;
		}
		.new-htable .wf-form-text{
			width:354px;
			text-indent:6px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.new-htable select{
			width:163px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.wf-form-date{
			width:133px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
		.tw-table-form th, .tw-table-form td{
			border:0
		}
	</style>
	
	
</head>
<body style="overflow-y:auto;">
	<div class="tw-layout" >
		<div class="tw-container">
		    <form method="get" id="empForm" name="empForm" action="#" class="tw-form">
		    	<input id="siteId" name="siteId" value="${siteId }" type="hidden"></input>
				<table class="tw-table-form tm-table-form">
			        <colgroup>
			            <col width="25%" />
			            <col />
			        </colgroup>
			        <c:if test="${resType == '1' }">
				        <c:forEach  items="${portalWbAppList}" var="wbApp" varStatus="status">
				        <c:if test="${wbApp.pId == '0'}">
				            <tr>
						   		<td><input type="checkbox" name="selid" value="${wbApp.id},${wbApp.name }" <c:if test="${fn:contains(selectedIds, wbApp.id)}">checked="checked"</c:if>></input></td>
					    	 	<td>${wbApp.name }</td>
						    </tr>
						 <c:forEach  items="${wbApp.wbAppList}" var="sonWbApp" varStatus="status">
						     <tr>
						   		<td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="selid" value="${sonWbApp.id},${sonWbApp.name }" <c:if test="${fn:contains(selectedIds, sonWbApp.id)}">checked="checked"</c:if>></input></td>
					    	 	<td>&nbsp;&nbsp;&nbsp;&nbsp;${sonWbApp.name }</td>
						    </tr>
						    
						    <c:forEach  items="${sonWbApp.wbAppList}" var="grandsonWbApp" varStatus="status">
						       <tr>
						   		  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="selid" value="${grandsonWbApp.id},${grandsonWbApp.name }" <c:if test="${fn:contains(selectedIds, grandsonWbApp.id)}">checked="checked"</c:if>></input></td>
					    	 	  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${grandsonWbApp.name }</td>
						       </tr>
						    </c:forEach>
						    
						  </c:forEach>
				        </c:if>
					       
				        </c:forEach>
			        </c:if>
			        <c:if test="${resType == '2' }">
				        <c:forEach  items="${oaWbAppList}" var="wbApp" varStatus="status">
				        <c:if test="${wbApp.pId == null || wbApp.pId =='' }">
				            <tr>
						   		<td><input type="checkbox" name="selid" value="${wbApp.id},${wbApp.name }" <c:if test="${fn:contains(selectedIds, wbApp.id)}">checked="checked"</c:if>></input></td>
					    	 	<td>${wbApp.name }</td>
						    </tr>
						 <c:forEach  items="${wbApp.wbAppList}" var="sonWbApp" varStatus="status">
						     <tr>
						   		<td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="selid" value="${sonWbApp.id},${sonWbApp.name }" <c:if test="${fn:contains(selectedIds, sonWbApp.id)}">checked="checked"</c:if>></input></td>
					    	 	<td>&nbsp;&nbsp;&nbsp;&nbsp;${sonWbApp.name }</td>
						    </tr>
						 
						 </c:forEach>
				        
				        </c:if>
					       
				        </c:forEach>
			        </c:if>
			        <c:if test="${resType == '3' }">
				        <c:forEach  items="${wfWbAppList}" var="wbApp" varStatus="status">
				        <c:if test="${wbApp.pId == null || wbApp.pId =='' }">
				            <tr>
						   		<td><input type="checkbox" name="selid" value="${wbApp.id},${wbApp.name }" <c:if test="${fn:contains(selectedIds, wbApp.id)}">checked="checked"</c:if>></input></td>
					    	 	<td>${wbApp.name }</td>
						    </tr>
						 <c:forEach  items="${wbApp.wbAppList}" var="sonWbApp" varStatus="status">
						     <tr>
						   		<td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="selid" value="${sonWbApp.id},${sonWbApp.name }" <c:if test="${fn:contains(selectedIds, sonWbApp.id)}">checked="checked"</c:if>></input></td>
					    	 	<td>&nbsp;&nbsp;&nbsp;&nbsp;${sonWbApp.name }</td>
						    </tr>
						 
						 </c:forEach>
				        
				        </c:if>
					       
				        </c:forEach>
			        </c:if>
			       
			    	 <tr>
			    	 	<td align="center" colspan="2">
			    	 		<button class="wf-btn-primary" type="button"  style="size: 16px;margin-top: 10px;" onclick="save();">
			                    <i class="wf-icon-plus-circle" ></i> 确认
			                </button>
			    	 	</td>
			    	 </tr>
					
			    </table>
		    </form>
		</div>
	</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	function save(){
		parent.document.getElementById("ifsave").value = "1";//是否执行修改保存操作的参数，取消修改和x不需要执行，1需要0不需要
		var retVal = "";
		var sel = document.getElementsByName('selid');
		var ids = "";
		var names = "";
		for(var i = 0 ; i < sel.length; i++){
			if(sel[i].checked){
				var idAndName = sel[i].value;
				var idAndNameArr = idAndName.split(",");
				var id = idAndNameArr[0];
				var name = idAndNameArr[1];
				ids += id + ",";
				names += name + ",";
			}
		}
		if(ids.length > 0){
			ids = ids.substring(0, ids.length - 1);
			names = names.substring(0, names.length - 1);
			retVal = ids + ";" + names;
		}else {
			
		}
		
		var resType = "${resType}";
		if('1' == resType){
			parent.document.getElementById("portalRes").value = retVal;
		}else if('2' == resType){
			parent.document.getElementById("oaRes").value = retVal;
		}else if('3' == resType){
			parent.document.getElementById("wfRes").value = retVal;
		}
		//window.parent.location.reload();
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
</script>
</html>