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
		<div class="wf-list-top">
			<div class="wf-search-bar">
				<div class="wf-top-tool">
					<c:if test="${employeeGuid eq 'riseadmin' }">
					<a class="wf-btn-danger" onclick="del();" href="javascript:;" >
		                <i class="wf-icon-minus-circle"></i> 删除表
		            </a>
		            <a class="wf-btn-danger"  onclick="clearTableData();" href="javascript:;" >
		                <i class="wf-icon-minus-circle"></i> 清空表数据
		            </a>
					</c:if>	
		        </div>
			</div>
		</div>
		<div class="wf-list-wrap">
			<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getTableList.do" >
				<table class="wf-fixtable" layoutH="140">
					<thead>
			    	<tr height="30px">
			    		<th width="5%">
				    		<input type="checkbox" name="selAll" id="selAll" onclick="sel();">
				    	</th>
			    		<th width="5%" align="center">
				    		序号
				    	</th>
				    	<th width="30%" align="center" >
				    		名称
				    	</th>
				    	<th width="30%" align="center">
				    		表名
				    	</th>
				    	<th width="15%" align="center">
				    		创建时间
				    	</th>
			    	</tr>
			    	</thead>
			    	<tbody>
				    	<c:forEach items="${list}" var="d" varStatus="n">
					    	<tr height="30px">
					    		<td align="center">
					    			<input type="checkbox" name="selid" value="${d.vc_tablename}">
					    		</td>
						    	<td align="center" tableid="${d.id}">
						    		${n.count}
						    	</td>
						    	<td >
						    		${d.vc_name}
						    	</td>
						    	<td>
						    		${d.vc_tablename}
						    	</td>
						    	<td align="center">
						    		<fmt:formatDate value="${d.vc_creatdate}" pattern="yyyy-MM-dd"/>
						    	</td>
					    	</tr>
					    </c:forEach>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
//清楚数据表中的数据
function clearTableData(){
	var selid = document.getElementsByName('selid');
	var tableNames = "";
	for(var i = 0 ; i < selid.length; i++){
		if(selid[i].checked){
			tableNames += selid[i].value + ",";
		}
	}
	if(tableNames.length > 0){
		tableNames = tableNames.substring(0, tableNames.length - 1);
	}else {
		alert('请选择一个清空对象');
		return;
	}
	if(confirm('确定清空所选表数据吗')){
		$.ajax({  
			url : 'table_clearTableData.do?tableNames='+tableNames,
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_clearTableData.do)');
			},
			success : function(msg) {
				if(msg=='1'){
					alert("已清空制定表数据！");
					window.location.href="${ctx}/table_getOfficeTableList.do";
				}
				if(msg=='0'){
					alert("备份数据库失败,请检查系统服务器的oracle驱动！");
				}
			}
		});
	}
}


function del(){
	var selid = document.getElementsByName('selid');
	var tableNames = "";
	for(var i = 0 ; i < selid.length; i++){
		if(selid[i].checked){
			tableNames += selid[i].value + ",";
		}
	}
	if(tableNames.length > 0){
		tableNames = tableNames.substring(0, tableNames.length - 1);
	}else {
		alert('请选择一个删除对象');
		return;
	}
	if(confirm('确定删除所选表数据吗')){
		$.ajax({  
			url : 'table_deleteOfficeTables.do?tableNames='+tableNames,
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_deleteOfficeTables.do)');
			},
			success : function(msg) {
				if(msg=='1'){
					alert("已删除指定的业务表！");
					window.location.href="${ctx}/table_getOfficeTableList.do";
				}
				if(msg=='0'){
					alert("备份数据库失败,请检查系统服务器的oracle驱动！");
				}
			}
		});
	}
}

function modify_row(){
	 var id=$(".selected td:eq(1)").attr("tableid");
	 if(id){
	 	location.href = "${ctx}/table_toEditJsp.do?id=" + id;
	 }else{
		alert('请选择要修改的表！');
     }
}

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
</script>
<%@ include file="/common/function.jsp"%>
<script>
	$('#pageContent').height($(window).height()-30);
</script>
</html>
