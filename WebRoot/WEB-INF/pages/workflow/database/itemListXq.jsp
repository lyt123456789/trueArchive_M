<!DOCTYPE>
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
		<div class="bread-box">
			<div class="bread-title"><span class="bread-icon"></span>
				<span class="bread-start">当前位置：</span>
				<span class="bread">事项管理</span>
				<span class="bread-split">&raquo;</span>
				<span class="bread">事项列表</span>
				<span class="bread-split">&raquo;</span>
				<span class="bread"></span>
			</div>
		</div>
		<table class="formTable" class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
			<tr align="center">
				<td class="label">事项类别</td>
				<td class="label">事项编码</td>
				<td class="label">事项分类</td>
				<td class="label">操作</td>
			</tr>
			<c:forEach items="${list}" var="item">
				<tr>
					<td style="cursor: hand" title="双击复制表单ID到粘贴板" ondblclick="javascript:window.clipboardData.setData('Text','${item.id}');alert('已复制！');">${item.vc_sxmc}</td>
					<td>${item.vc_sxbm}</td> 
					<td>
						${item.vc_sxfl}
					</td> 
					<td>
						<input mice-btn="icon-edit" type="button" id="edit_form" value="编辑事项" onclick="editItem('${form.id}')">
						<input mice-btn="icon-edit" type="button" value="删除事项" onclick="deleteItem('${form.id}')">
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="4">
					<input id="add_item" mice-btn="icon-add" type="button" value="新增">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" mice-btn="icon-back" value="返回" onclick="javascript:history.back()">
				</td>  
			</tr>
		</table>
		<%@ include file="/common/function.jsp"%>
		<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
		<script type="text/javascript">
			var sheight =330;  
	        var swidth = 500;

			$("#add_item").bind("click",function (){
		        var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:no;scroll:no;resizable:no;center:yes";  
				var ret=window.showModalDialog("${ctx}/item_toAddJsp.do",window,winoption);
				if(ret=="success"){
					window.location.reload();
				}
			});
	        
			function editItem( itemId){
		        var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:no;scroll:no;resizable:no;center:yes";
				var ret=window.showModalDialog("${ctx}/workflowForm_toUpdateForm.do?formId="+formId+"&t="+ new Date().getTime(),window,winoption);
				if(ret=="success"){
					window.location.reload();
				}
			}

			function deleteItem( itemId){
				if(window.confirm("确定删除？")){
					$.ajax({
		        		cache:false,
		        		async:false,
		        		type: "POST",
		        		url: "item_del.do",
		        		data:{'id':itemId},
		        		error: function(){
		        	   		alert('异步调用删除方法失败');
		        		},
		        		success: function(ret){
							if(ret=="success"){
								window.location.reload();
							}else if(ret=="fail"){
								alert('删除失败');
							}
		        		}
		        	});
				}

			}
		</script>
		 <script type="text/javascript">
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