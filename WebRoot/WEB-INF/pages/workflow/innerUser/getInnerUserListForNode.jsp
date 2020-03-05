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
	<div class="wf-layout">
<!-- 		<div class="wf-list-top">
			<div class="wf-search-bar">
				<div class="wf-top-tool">
		            <a class="wf-btn"  href="javascript:choose();">
		                <i class="wf-icon-plus-circle"></i> 确定选择
		            </a>
		        </div>
			</div>
		</div> -->
		<div class="wf-list-wrap" style="height: 300px">
			<form id="thisForm" method="POST" name="thisForm" action="${ctx }/group_getInnerGroupForNode.do" >
				<table class="wf-fixtable" layoutH="140">
					<thead>
			    	  	<tr>
							<th width="50">
								<input type="checkbox" id="pchc" onclick="chcChecked(this.id,'chcs')"/>
							</th>
							<th width="50">
								序号
							</th>
							<th width="250">
								用户组
							</th>
							<th width="150">
								组别
							</th>
							<th class="tdrs">等级</th>
						</tr>
			    	</thead>
			    	<c:forEach var="bean" items="${list}" varStatus="b"> 
						<tr>  
							<td align="center"><input type="checkbox" name="chcs" value="${bean.id }" <c:if test="${fn:contains(ids,bean.id)}">checked="checked"</c:if>/></td>
							<td align="center" >${b.count}</td>
							<td align="center" title="${bean.name }">${bean.name }</td>
							<td align="center" <c:if test="${(bean.type==4) && (not empty bean.workflowId)}">title="流程用户组" </c:if> <c:if test="${(bean.type==4) && (empty bean.workflowId)}">title="平台用户组"</c:if>>
							<c:if test="${(bean.type==4) && (not empty bean.workflowId)}">流程用户组 </c:if>
							<c:if test="${(bean.type==4) && (empty bean.workflowId)}">平台用户组</c:if></td>
							<td class="tdrs" title="${bean.grade}">${bean.grade}</td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
	<script type="text/javascript">

	//复选框批选取
	function chcChecked(parentCheckboxId,checkboxesName){
		var p=g.g(parentCheckboxId);
		var arr=g.gbn(checkboxesName);
		if(p&&arr){
			for(var i=0;i<arr.length;i++){
				arr[i].checked=p.checked;
			};
		}
	}; 

	function choose(){
		var chcs=g.gbn('chcs');
		var ids='';
		for(var i=0;i<chcs.length;i++){
			if(chcs[i].checked==true){
				ids+=chcs[i].value+',';
			}
		}
		if(ids!=''){
			ids=ids.substr(0,ids.length-1);
		}
		return ids;
		/* window.returnValue=ids;
		window.close(); */
	};
	</script>
	</body>
	<%@ include file="/common/function.jsp"%>
</html>
