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
	</style>
</head>
<body>
<div class="tw-layout">
	<div class="tw-container">
	    <form method="get" id="siteForm" name="siteForm" action="#" class="tw-form">
	    	<input type="hidden" id="userIdAndNames"  name="userIdAndNames"></input>
		    <table class="tw-table-form tm-table-form">
		        <colgroup>
		            <col width="20%" />
		            <col />
		        </colgroup>
		        <tr>
					<th>站点名称</th>
					<td >
						<input id="name"  name="name" style="width: 260px;"></input>
						<input type="hidden" id="id"  name="id"></input>
					</td>
				</tr>
				<tr>
					<th>上级部门</th>
					<td >
						<select name="pDeptId" id="pDeptId" style="width: 260px;height: 27px" onclick="setPDeptName();">
							<option value="">-请选择-</option>
							<c:forEach var="item" items="${list}" varStatus="status">
								<option value="${item.id }">${item.name }</option>
							</c:forEach>
						</select>
						<input type="hidden" id="pDeptName" value="${pDeptName }" name="pDeptName"></input>
					</td>
				</tr>
				<tr>
					<th>排序</th>
					<td ><input id="sortIndex"  name="sortIndex" style="width: 260px;" value="${sortIndex }"></input></td>
				</tr>
				<%-- <tr>
					<th>站点管理员</th>
					<td>
						<input id="siteAdaminName"  name="siteAdaminName"  value="${siteAdaminName }" style="width: 260px;" onclick="showEmpTree(1);"></input>
						<input type="hidden"  id="siteAdminGuid"  name="siteAdminGuid"  value="${siteAdminGuid }"></input>
					</td>
				</tr>
				<tr>
					<th>部门管理员</th>
					<td>
						<input id="depAdminName" name="depAdminName" value="${depAdminName }" style="width: 260px;" onclick="showEmpTree(2);"></input>
						<input type="hidden"  id="depAdminGuid"  name="depAdminGuid"  value="${depAdminGuid }"></input>
					</td>
				</tr>
				<tr>
					<th>人员管理员</th>
					<td>
						<input id="empAdminName"  name="empAdminName"  value="${empAdminName }" style="width: 260px;" onclick="showEmpTree(3);"></input>
						<input type="hidden" id="empAdminGuid" name="empAdminGuid"  value="${empAdminGuid }"></input
					</td>
				</tr>
				<tr>
					<th>角色管理员</th>
					<td>
						<input id="roleAdminName"  name="roleAdminName"  value="${roleAdminName }" style="width: 260px;" onclick="showEmpTree(4);"></input>
						<input type="hidden"  id="roleAdminGuid"  name="roleAdminGuid" value="${roleAdminGuid }"></input
					</td>
				</tr> --%>
				<tr>
					<td align="center" colspan="2">
						<button class="tw-btn-primary" type="button" style="size: 16px;margin-top: 10px;" onclick="save();"><i class="wf-icon-plus-circle" ></i> 确认</button>
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
			var pDeptId = $('#pDeptId').val();
			var name = $('#name').val();
			var sortIndex = $('#sortIndex').val();
			if(sortIndex == "" || sortIndex == null){
				alert("排序不能为空!");
				return;
			}
			if(name == "" || name == null){
				alert("站点名称不能为空!");
				return;
			}
			if(pDeptId == "" || pDeptId == null){
				alert("上级部门不能为空!");
				return;
			}
			if(!isRealNum(sortIndex)){
				alert('排序应为数字');
				return;
			}
			
			$.ajax({
				url : 'siteManage_addSite.do',
				type : 'POST',   
				cache : false,
				async : false,
				data :$('#siteForm').serialize(),
				error : function(e) {  
					alert('AJAX调用错误(siteManage_addSite.do)');
				},
				success : function(msg) {
					if(msg == 'success'){
						alert('保存成功！');
					}else{
						alert('保存失败！');
					}
					//window.parent.location.reload();
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				}
			});
		}
		
		function isRealNum(val){
			if(val === "" || val == null){
				return false;
			}
			if(!isNaN(val)){
				return true;
			}else{
				return false;
			}
		}
		
		function setPDeptName(){
			var pDeptName= $('#pDeptId').find("option:selected").text();
			$('#pDeptName').val(pDeptName);
		}
		
		//显示人员树
		function showEmpTree(type){
			var userIds = "";
			if(type == 1){
				userIds = $('#siteAdminGuid').val();
			}else if(type == 2){
				userIds = $('#depAdminGuid').val();
			}else if(type == 3){
				userIds = $('#empAdminGuid').val();
			}else if(type == 4){
				userIds = $('#roleAdminGuid').val();
			}
			var url = '${curl}/ztree_showAllDeptTree.do?allEmp=1&userIds=' + userIds + '&t='+new Date();
			top.layer.open({
	            title: '添加人员',
	            area: ['860px', '600px'],
	            type: 2,
	            content: url,
	            btn:["确认选择","取消选择"],
	            yes:function(index,layer){
	            	var iframeWin = top[layer.find('iframe')[0]['name']];
	            	var userIdAndNames = iframeWin.test();
	            	var userIdAndNameArr = userIdAndNames.split(';');
	            	var userIds = "";
	            	var userNames = "";
	            	if(userIdAndNameArr.length > 1){
	            		userIds = userIdAndNameArr[0];
	            		userNames = userIdAndNameArr[1];
	            	}
	            	if(type == 1){
	    				$('#siteAdminGuid').val(userIds);
	    				$('#siteAdaminName').val(userNames);
	    			}else if(type == 2){
	    				$('#depAdminGuid').val(userIds);
	    				$('#depAdminName').val(userNames);
	    			}else if(type == 3){
	    				$('#empAdminGuid').val(userIds);
	    				$('#empAdminName').val(userNames);
	    			}else if(type == 4){
	    				$('#roleAdminGuid').val(userIds);
	    				$('#roleAdminName').val(userNames);
	    			}
	            	
	            	top.layer.close(index);
	            	top.$('.layui-layer-shade').css('display', 'none');
	            	top.$('.layui-layer-setwin').css('display', 'none');
	            	if(top.$('.layui-layer-iframe')[1]){
	            		top.$('.layui-layer-iframe')[1].style.setProperty('width', '500px', 'important');
	            		top.$('.layui-layer-iframe')[1].style.setProperty('top', '250px', 'important');
	            	}
	            },
	            success:function(){
	            	top.$('.layui-layer-shade').css('display', 'inherit');
	            	top.$('.layui-layer-setwin').css('display', 'inherit');
	            	if(top.$('.layui-layer-iframe')[1]){
	            		top.$('.layui-layer-iframe')[1].style.setProperty('width', '800px', 'important');
	            		top.$('.layui-layer-iframe')[1].style.setProperty('top', '80px', 'important');
	            	}
	            }
	            /* end: function(index){
	            	var userIdAndNames = $('#userIdAndNames').val();
	            	var userIdAndNameArr = userIdAndNames.split(';');
	            	var userIds = "";
	            	var userNames = "";
	            	if(userIdAndNameArr.length > 1){
	            		userIds = userIdAndNameArr[0];
	            		userNames = userIdAndNameArr[1];
	            	}
	            	if(type == 1){
	    				$('#siteAdminGuid').val(userIds);
	    				$('#siteAdaminName').val(userNames);
	    			}else if(type == 2){
	    				$('#depAdminGuid').val(userIds);
	    				$('#depAdminName').val(userNames);
	    			}else if(type == 3){
	    				$('#empAdminGuid').val(userIds);
	    				$('#empAdminName').val(userNames);
	    			}else if(type == 4){
	    				$('#roleAdminGuid').val(userIds);
	    				$('#roleAdminName').val(userNames);
	    			}
	            } */
	        });
		}
	</script>
</html>