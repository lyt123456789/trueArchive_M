<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<form onsubmit="return navTabSearch(this);" action="demo_page1.html" method="post">
	<div class="panelBar"> 
		<ul class="toolBar">
			<li><a href="javascript:toaddjsp();"  class="add"><span>添加</span></a></li>
			<li><a class="delete" href="javascript:deleteSelected('chcs');" ><span>删除</span></a></li> 
		</ul>
	</div>
	</form>
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/form_addForm.do">
	<input type="hidden"  value="${allowType }" name="allowType" id="allowType"/>
	<input type="hidden"  value="${glid }" name="glid" id="glid"/>
	
	<input type="hidden"  value="" name="ids" id="ids"/>
	<div id="w_list_print">
		<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="5%">
					<input type="checkbox" id="pchc" onclick="chcChecked(this.id,'chcs')"/>
				</th>
				<th width="15%">角色类型</th>
				<th width="15%">角色名称</th>
				<th ></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="bean" items="${list}">
				<tr>
					<td align="center"><input type="checkbox" name="chcs" value="${bean.id }"/></td>
					<td align="left">${bean.role_typename }</td>
					<td align="left">${bean.role_name }</td>
				</tr>
			</c:forEach>
				
		</tbody>
		</table>
	</div>
	<div class="formBar pa" style="bottom:0px;width:100%;">  
			<ul class="mr5"> 
				<li>
					<div class="button"><div class="buttonContent"><button name="CmdCancel" class="close" type="button" onclick="window.history.go(-1);">返回</button></div></div>
				</li>
			</ul>
		  </div>
	</form>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	<script type="text/javascript">
		function toSet(id){
			g.g('glid').value=id;
			g.g('thisForm').action='${ctx }/allow_getAllowList.do';
			g.g('thisForm').submit();
		};
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
			g.g('thisForm').action='${ctx}/allow_deleteAllow.do';
			g.g('thisForm').submit();
		};
		function toaddjsp(){
			//g.g('glid').value=id;
			g.g('thisForm').action='${ctx}/allow_toAddAllowJsp.do';
			g.g('thisForm').submit();
		};
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
		function toUpdate(){
			g.g('thisForm').action='${ctx}/allow_toAddAllowJsp.do';
			g.g('thisForm').submit();
		};
	</script>
	</body>
		<%@ include file="/common/function.jsp"%>
</html>
