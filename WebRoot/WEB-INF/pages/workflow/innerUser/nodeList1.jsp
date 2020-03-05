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
<script type="text/javascript">
</script>
<body>
	<div class="wf-layout">
		<div class="wf-list-top">
			<form name="workflow" id="workflow" action="${ctx}/group_getListForNodeIframe.do" method="post" >
			<input type="hidden" id="nodegroup" name="nodegroup" value="${nodegroup}" />
			<input type="hidden" id="ids" name="ids" value="${ids}" />
			<div class="wf-search-bar">
<!-- 				<div class="wf-top-tool">
		            <a class="wf-btn"  href="javascript:choose();">
		                <i class="wf-icon-plus-circle"></i> 确定选择
		            </a>
		        </div> -->
		        <label class="wf-form-label" for="">名称：</label>
		        <input type="text" name="name" id="name" class="wf-form-text" onkeydown="if(event.keyCode==13) return false;"  value="${name}" placeholder="输入关键字"/>
	            <button class="wf-btn-primary" type="submit" >
	                <i class="wf-icon-search"></i> 搜索
	            </button>
			</div>
			</form>
		</div>
		<div class="wf-list-wrap" style="height: 435px;">
			<form id="thisForm" method="POST" name="thisForm" action="${ctx}/group_getInnerUserList.do" >
			<input type="hidden" name="node_route_type" id="node_route_type" value="${node_route_type}">
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
								人员
							</th>
							<th width="250">
								所属部门
							</th>
							<th class="tdrs"></th>
						</tr>
			    	</thead>
			    	<c:forEach var="bean" items="${mapList}" varStatus="b"> 
						<tr>  
							<td align="center"><input type="checkbox" name="chcs" value="${bean.employee_id }" <c:if test="${fn:contains(ids,bean.employee_id)}">checked="checked"</c:if>/></td>
							<td align="center" >${b.count }</td>
							<td align="center" title="${bean.employee_name }">${bean.employee_name }</td>
							<td align="center" title="${bean.employee_shortdn }">${bean.employee_shortdn }</td>
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

	function xg_row(){
		 var id=$(".selected td:eq(1)").attr("beanid");
		 if(id){
		 	location.href = "${ctx}/group_toUpdateInnerUserJsp.do?id="+id+"&type=${type }";
		 }else{
			alert('请选择要修改的组！');
	     }
	}

	function ck_row(){
		 var id=$(".selected td:eq(1)").attr("beanid");
		 if(id){
		 	location.href = "${ctx}/group_viewInnerUser.do?id="+id+"&type=${type }";
		 }else{
			alert('请选择要查看的组！');
	     }
	}

	function sd_row(){
		 var id=$(".selected td:eq(1)").attr("beanid");
		 if(id){
		 	location.href = "${ctx}/group_toMapEmployeeJsp.do?id="+id+"&type=${type }";
		 }else{
			alert('请选择要设定的组！');
	     }
	}
	

	//批删除提示
	function deleteSelected(checkboxesName){
		var chcs=g.gbn(checkboxesName);
		var ids='';
		for(var i=0;i<chcs.length;i++){
			if(chcs[i].checked==true){
				ids+=chcs[i].value+',';
			}
		}
		if(ids==''){
			alert('请至少选择一条记录删除!');
			return;
		}
		ids=ids.substr(0,ids.length-1);
		if(!confirm("确认删除所选记录吗?")){
			return;
		}
		g.g('ids').value=ids;
		g.g('thisForm').action='${ctx}/group_deleteInnerUser.do';
		g.g('thisForm').submit();
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
		var flag =false;
		if(ids.length >0){
			var id = ids.split(",");
			var node_route_type = $("#node_route_type").val();
			if(node_route_type=='0' && id.length>1){
				alert("路由模式为'单人',请重新选择！");
				flag = true;
			}
		}
		if(!flag){
			return ids;
			/* window.returnValue=ids;
			window.close(); */
		}
		
	};
	</script>
	</body>
</html>
