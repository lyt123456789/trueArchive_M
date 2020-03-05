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
		<div class="wf-list-top" style="height:62px;">
			<div class="wf-search-bar">
				<div class="wf-top-tool">
		            <a class="wf-btn" onclick="javascript:add()" external="true" target="_self">
		                <i class="wf-icon-plus-circle"></i> 新建
		            </a>
		            <a class="wf-btn-primary" href="javascript:edit();" >
		                <i class="wf-icon-pencil"></i> 修改
		            </a>
		            <a class="wf-btn-danger" href="javascript:del();" external="true" target="_self" rel="CreatingForms" >
		                <i class="wf-icon-minus-circle"></i> 删除
		            </a>
		        </div>
			</div>
		</div>
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/template_getTemplateList.do" >
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
				    	<tr>
					    	<th width="50">
					    		<input type="checkbox" name="selAll" id="selAll" onclick="sel();">
					    	</th>
				    		<th width="50" align="center">
					    		序号
					    	</th>
					    	<th width="150" align="center">
					    		类型
					    	</th>
					    	<th></th>
				    	</tr>
			    	</thead>
			    	<tbody>
						<c:forEach items="${list}" var="d" varStatus="n">
					    	<tr>
					    		<td align="center">
					    			<input type="checkbox" name="selid" value="${d.id}">
					    		</td>
						    	<td align="center" attid="${d.id}">
						    		${n.count}
						    	</td>
						    	<td align="center">
						    		${d.att_type}
						    	</td>
						    	<td></td>
					    	</tr>
					    </c:forEach>
					</tbody>
				</table>
			
		</div>
		</form>
	</div>
	
</body>
	<script type="text/javascript">

		function sel(){
			var selAll = document.getElementById('selAll');
			var selid = document.getElementsByName('selid');
			for(var i = 0 ; i < selid.length; i++){
				if(selAll.checked){
					selid[i].checked = true;
				}else{
					selid[i].checked = false;
				}
			}
		}
	
		function del(){
			var selid = document.getElementsByName('selid');
			var ids = "";
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].checked){
					ids += selid[i].value + ",";
				}
			}
			if(ids.length > 0){
				ids = ids.substring(0, ids.length - 1);
			}else {
				var id=$(".wf-actived td:eq(1)").attr("attid");
				if(id!=null && id.length>0){
					ids = id;
				}else{
					alert('请至少选择一个对象删除!');
					return;
				}
				
			}
			if(!confirm("确认删除所选对象吗?")){
				return;
			}
			location.href = "${ctx}/attachmentType_delAttachmentType.do?id="+ids;
		}

		function add(){
			var ret=window.showModalDialog("${ctx}/attachmentType_toAddJsp.do",null,"dialogWidth:350px;dialogHeight:150px;help:no;status:no");
			if(true==ret){
				window.location.href="${ctx}/attachmentType_getAttachmentTypeList.do";
			}else{
				//
			}
		}

		function edit(){
			var id=$(".wf-actived td:eq(1)").attr("attid");
			if(id){
				var ret=window.showModalDialog("${ctx}/attachmentType_toEditJsp.do?id="+id,null,"dialogWidth:350px;dialogHeight:150px;help:no;status:no");
				if(true==ret){
					window.location.href="${ctx}/attachmentType_getAttachmentTypeList.do";
				}else{
					//
				}
			}else{
				alert('请选择修改的附件类型！');
			}
		}
	</script>
</html>
