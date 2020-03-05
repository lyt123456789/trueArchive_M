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

<body style="overflow: auto;">
	<div class="wf-layout">
		<div class="wf-list-wrap">
			<form id="thisForm" method="POST" name="thisForm" action="${ctx }/group_getInnerUserList.do" >
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
								节点名称
							</th>
							<th width="250">
								属于子流程
							</th>
							<th class="tdrs"></th>
						</tr>
			    	</thead>
			    	<c:forEach var="bean" items="${list}" varStatus="b"> 
						<tr>  
							<td align="center"><input type="checkbox" name="chcs" value="${bean.wfn_id}" <c:if test="${fn:contains(ids,bean.wfn_id)}">checked="checked"</c:if>/></td>
							<td align="center">${b.count }</td>
							<td align="center" beanid="${bean.wfn_id}" title="${bean.wfn_name }">${bean.wfn_name }</td>
							<td align="center">${bean.child_name }</td>
							<td class="tdrs"></td>
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
	};
	</script>
</body>
</html>
