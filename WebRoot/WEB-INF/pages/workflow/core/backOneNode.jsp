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
	<script src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
</head>
  <body>
	<table class="infotan mt10" width="80%" id="nodeTable" style="margin-left:100px;">
		<tr>
			<td width="20%" style="font-weight:bold;text-align:center;">节点</td>
			<td width="25%" style="font-weight:bold;text-align:center;">返回指定节点</td>
			<td width="20%" style="font-weight:bold;text-align:center;">节点</td>  
			<td width="20%" style="font-weight:bold;text-align:center;"><img src="${ctx}/dwz/button/add.png" title="新增" style="cursor:pointer;" onclick="add();"></td>  
		</tr>
		<c:forEach items="${wfBackNodeList}" var="wfBackNode">
			<tr id="${wfBackNode.id}">  
				<td width="20%" style="text-align:center;">${wfBackNode.fromNodeName}</td>
				<td width="25%" style="text-align:center;">---------------＞</td>
				<td width="20%" style="text-align:center;">${wfBackNode.toNodeName}</td>  
				<td width="20%" style="text-align:center;"><img src="${ctx}/dwz/button/edit.png" title="修改" style="cursor:pointer;" onclick="editNode('${wfBackNode.fromNodeId}','${wfBackNode.toNodeId}','${wfBackNode.id}');">&nbsp;&nbsp;&nbsp;<img src="${ctx}/dwz/button/delete.png" title="删除" style="cursor:pointer;" onclick="deleteNode('${wfBackNode.id}');"></td>  
			</tr>
		</c:forEach>
	</table>
	<div style="display:none;" id="divSelfrom" >     
		<select id="sel1" name="sel1">
			<c:forEach items="${nodes}" var="node">
				<option value="${node.wfn_id}">${node.wfn_name}</option>
			</c:forEach>
		</select>
	</div>
	<div style="display:none;" id="divSelto" >     
		<select id="sel2" name="sel2">
			<c:forEach items="${nodes}" var="node">
				<option value="${node.wfn_id}">${node.wfn_name}</option>
			</c:forEach>
		</select>
	</div>
  </body>
  <script>
	 	function add(){
	 	 	var selectChooses1 = $("#divSelfrom").html();
	 	 	var selectChooses2 = $("#divSelto").html();
	 	 	var fromNodeTd  = "<td style=\"text-align:center;\">"+selectChooses1+"</td>";
	 	 	var otherTd = "<td style=\"text-align:center;\">---------------＞</td>";    
	 	 	var toNodeTd  = "<td style=\"text-align:center;\">"+selectChooses2+"</td>";
	 	 	var operate  = "<td style=\"text-align:center;\"><img src=\"${ctx}/dwz/button/sav.png\" title=\"保存\" style=\"cursor:pointer;\" onclick=\"save();\"></td>";
			$("#nodeTable").append('<tr>' + fromNodeTd + otherTd + toNodeTd + operate + '</tr>');
		}

		function save(){
	 	 	var fromNodeId = $("#sel1").val();
	 	 	var fromNodeName = $("#sel1").find("option:selected").text();
	 	 	var toNodeId   = $("#sel2").val();
	 	 	var toNodeName = $("#sel2").find("option:selected").text();
			$.ajax({   
				url : '${ctx }/table_saveBackNode.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_saveBackNode.do)');
				},
				data : {
					'fromNodeId':fromNodeId,'toNodeId':toNodeId,'workflowId':'${workflowId}','fromNodeName':fromNodeName,'toNodeName':toNodeName
				},    
				success : function(msg) {  
					if(msg == 'yes'){
						alert('保存成功！');
						window.location.href="${ctx}/table_backOneNode.do";
					}else if(msg == 'no'){
						alert('起始节点已经存在，请重新设置！');
						return ;
					}else{
						alert('保存失败！');
						return ;
					}
				}
			});
		}

		function deleteNode(wfBackNodeId){
			if(confirm("确定删除吗？")){
				$.ajax({   
					url : '${ctx }/table_deleteBackNode.do',
					type : 'POST',   
					cache : false,
					async : false,
					error : function() {  
						alert('AJAX调用错误(table_deleteBackNode.do)');
					},
					data : {
						'wfBackNodeId':wfBackNodeId,'workflowId':'${workflowId}'
					},    
					success : function(msg) {  
						if(msg == 'yes'){
							alert('删除成功！');
						}else{
							alert('删除失败！');
						}
						window.location.href="${ctx}/table_backOneNode.do";
					}
				});
			}
		}

		function editNode(fromNodeId,toNodeId,wfBackNodeId){
			var selectChooses1 = $("#divSelfrom").html();
	 	 	var selectChooses2 = $("#divSelto").html();
	 	 	var operate  = "<img src=\"${ctx}/dwz/button/sav.png\" title=\"保存\" style=\"cursor:pointer;\" onclick=\"updateNode('"+wfBackNodeId+"');\">";
	 	 	var cells = document.getElementById(wfBackNodeId).getElementsByTagName("td");
	 	 	cells[0].innerHTML = selectChooses1;
	 	 	cells[2].innerHTML = selectChooses2;
	 	 	cells[3].innerHTML = operate;
	 	 	$("#sel1").val(fromNodeId);
	 	 	$("#sel2").val(toNodeId);
		}

		function updateNode(backId){
			var fromNodeId = $("#sel1").val();
	 	 	var fromNodeName = $("#sel1").find("option:selected").text();
	 	 	var toNodeId   = $("#sel2").val();
	 	 	var toNodeName = $("#sel2").find("option:selected").text();
	 	 	$.ajax({   
				url : '${ctx }/table_updateBackNode.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_updateBackNode.do)');
				},
				data : {
					'backId':backId,'fromNodeId':fromNodeId,'toNodeId':toNodeId,'workflowId':'${workflowId}','fromNodeName':fromNodeName,'toNodeName':toNodeName
				},    
				success : function(msg) {  
					if(msg == 'yes'){
						alert('保存成功！');
						window.location.href="${ctx}/table_backOneNode.do";
					}else if(msg == 'no'){
						alert('起始节点已经存在，请重新设置！');
						return ;
					}else{
						alert('保存失败！');
						return ;
					}
				}
			});
		}
   </script>   
</html>
