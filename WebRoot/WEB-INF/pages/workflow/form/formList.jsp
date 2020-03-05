<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
   	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
</head>
	<body>
	<div class="wf-layout">
	  <form  action="demo_page1.html" method="post">
		<div class="wf-list-top" style="height:62px;">
			<div class="wf-search-bar">
				<div class="wf-top-tool">
		            <a class="wf-btn" href="${ctx}/form_toAddFormJsp.do?workflowId=${workflowId}" external="true"  target="_self" rel="CreatingForms" >
		                <i class="wf-icon-plus-circle"></i> 可视化新建
		            </a>
		            <a class="wf-btn-primary" onclick="modify_row();">
		                <i class="wf-icon-pencil"></i> 可视化编辑
		            </a>
		            <a class="wf-btn-danger" href="javascript:deleteSelected('chcs');" >
		                <i class="wf-icon-minus-circle"></i> 删除
		            </a>
		            <a class="wf-btn-blue" onclick="setHTMLTag2Column();"  >
		                <i class="wf-icon-crosshairs"></i> 设置
		            </a>
		            
					<a class="wf-btn" href="${ctx}/form_toAddFormJspByCode.do?workflowId=${workflowId}" external="true"  target="_self" rel="CreatingForms" >
		                <i class="wf-icon-plus-circle"></i> 代码新建
		            </a>
		            
		            <a class="wf-btn-primary" onclick="modify_rowBycode();"  >
		                <i class="wf-icon-pencil"></i> 源码编辑
		            </a>
		        </div>
			</div>
		</div>
		</form>
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/form_addForm.do">
		<input type="hidden"  value="" name="ids" id="ids"/>
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
						<tr>
							<th width="50">
								<input type="checkbox" id="pchc" onclick="chcChecked(this.id,'chcs')"/>
							</th>
							<th width="200">英文名称</th>
							<th width="200">中文名称</th>
							<th width="100">创建时间</th> 
							<th width="" class="tdrs"></th>
						</tr>
					</thead>
			    	<tbody>
						<c:forEach var="form" items="${list}">
							<tr>
								<td align="center"><input type="checkbox" name="chcs" value="${form.id }"/></td>
								<td align="left" formid="${form.id}">${form.form_name }</td>
								<td align="left">${form.form_caption }</td>
								<td align="center" align="center"><fmt:formatDate value="${form.intime }" pattern="yyyy-MM-dd"></fmt:formatDate></td>
								<td class="tdrs" align="center">  
								</td>   
							</tr> 
						</c:forEach>
					</tbody>
				</table>
			
		</div>
		</form>
	</div>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
<script type="text/javascript">
		function setHTMLTag2Column(){
			var id=$(".wf-actived td:eq(1)").attr("formid");
			if(!id){
				alert('请选择要设定的表单！');
				return;
		     }
			var w=window.screen.availWidth?window.screen.availWidth:'800';
			var h=window.screen.availHeight?window.screen.availHeight-100:'600';
			var url ="${ctx}/form_toHTMLTag2ColumnJsp.do?formid="+id+"&d="+Math.random();
			//用layer的模式打开
			parent.layer.open({
				title:'流程表单配置',
				type:2,
				area:[w+'px', h+'px'],
				content: url
			});
		};

		function modify_row(){
			 var id=$(".wf-actived td:eq(1)").attr("formid");
			 if(id){
			 	location.href = "${ctx}/form_toUpdateFormJsp.do?workflowId=${workflowId}&formid=" + id;
			 }else{
				alert('请选择要修改的表单！');
		     }
		}
		
		function modify_rowBycode(){
			 var id=$(".wf-actived td:eq(1)").attr("formid");
			 if(id){
			 	location.href = "${ctx}/form_toUpdateFormJspByCode.do?workflowId=${workflowId}&formid=" + id;
			 }else{
				alert('请选择要修改的表单！');
		     }
		}

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

		//批删除提示
		function deleteSelected(checkboxesName){
			var chcs=g.gbn(checkboxesName);
			var ids='';
			for(var i=0;i<chcs.length;i++){
				if(chcs[i].checked==true){
					ids+=chcs[i].value+',';
				}
			}
			if(ids.length > 0){
				ids=ids.substr(0,ids.length-1);
			}else {
				var id=$(".wf-actived td:eq(1)").attr("formid");
				if(id!=null && id.length>0){
					ids = id;
				}else{
					alert('请至少选择一条记录删除');
					return;
				}
			}
			if(!confirm("确认删除所选记录吗?")){
				return;
			}
			g.g('ids').value=ids;
			g.g('thisForm').action='${ctx}/form_deleteForm.do';
			g.g('thisForm').submit();
		};
	</script>
	</body>
</html>
