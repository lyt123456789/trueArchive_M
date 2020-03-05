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
  	<link rel="stylesheet" type="text/css" href="${ctx}/easyui-tree/static/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/easyui-tree/static/icon.css">
	<script type="text/javascript" src="${ctx}/easyui-tree/static/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/easyui-tree/static/jquery.easyui.min.js"></script>
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
		
		.tm-table-form .textbox {
			border: 1px solid #ccc;
		}
		.input-form-ctrl {
			padding: 0 5px;
			height: 27px;
    		line-height: 27px;
			border: 1px solid #ccc;
			border-radius: 5px;
		}
	</style>
</head>
<body>
	<div class="tw-layout">
		<div class="tw-container">
		    <form method="get" id="empForm" name="empForm" action="#" class="tw-form">
		    	<input id="siteId" name="siteId" value="${siteId }" type="hidden"></input>
		    	<input id="id" name="id" value="${id }" type="hidden"></input>
				<table class="tw-table-form tm-table-form">
			        <colgroup>
			            <col width="20%" />
			            <col />
			            <col width="20%" />
			            <col />
			        </colgroup>
			        <tr>
				   		<th>部门</th>
			    	 	<td>
			    	 		<%-- <input id="deptName" class="easyui-combotree" data-options="require:true" readonly="readonly" name="deptName" value="${deptName }" style="width: 260px;"></input> --%>
			    	 		<select id="deptId" name="deptId" style="width: 260px; height: 29px;" class="easyui-validatebox" data-options="required:true"></select>
			    	 		<%-- <input id="deptId"  name="deptId" value="${depId }" type="hidden"></input> --%>
			    	 		<%-- <input id="currentDeptId"  name="currentDeptId" value="${depId}" type="hidden"></input> --%>
			    	 		
			    	 	</td>
				   		<th>登录名</th>
			    	 	<td>
			    	 		<input class="input-form-ctrl" id="loginName" name="loginName" value="${loginName }" style="width: 260px;"></input>
			    	 	</td>
				    </tr>
				    <tr>
				   		<th>姓名</th>
			    	 	<td>
			    	 		<input class="input-form-ctrl" id="name"  name="name" value="${name }" style="width: 260px;"></input>
			    	 	</td>
				   		<th>性别</th>
			    	 	<td>
			    	 		<!-- <input id="sex"  name="sex" style="width: 260px;"></input> -->
			    	 		<select class="input-form-ctrl" id="sex"  name="sex" style="width: 260px;">
			    	 			<option value="1" <c:if test="${sex == '1' }">selected</c:if>>女</option>
			    	 			<option value="0" <c:if test="${sex == '0' }">selected</c:if>>男</option>
			    	 		</select>
			    	 	</td>
				    </tr>
				    <tr>
				   		<th>年龄</th>
			    	 	<td>
			    	 		<input class="input-form-ctrl" id="age" name="age" value="${age }" style="width: 260px;"></input>
			    	 	</td>
				   		<th>手机号码</th>
			    	 	<td>
			    	 		<input class="input-form-ctrl" id="phone"  name="phone" value="${phone }" style="width: 260px;"></input>
			    	 	</td>
				    </tr>
				    <tr>
				   		<th>电话</th>
			    	 	<td>
			    	 		<input class="input-form-ctrl" id="tel"  name="tel" value="${tel }" style="width: 260px;"></input>
			    	 	</td>
				   		<th>职务</th>
			    	 	<td>
			    	 		<input class="input-form-ctrl" id="jobTitle"  name="jobTitle" value="${jobTitle }" style="width: 260px;"></input>
			    	 	</td>
				    </tr>
		    	 	<tr>
		    	 		<th>传真</th>
			    	 	<td>
			    	 		<input class="input-form-ctrl" id="fax"  name="fax" value="${fax }" style="width: 260px;"></input>
			    	 	</td>
			    	 	<th>短号</th>
			    	 	<td><input class="input-form-ctrl" id="shortPhoneNum" value="${shortPhoneNum }" name="shortPhoneNum" style="width: 260px;"></input></td>
			    	 </tr>
			    	 <tr>
			    	 	<th>社保卡号</th>
			    	 	<td><input class="input-form-ctrl" id="ssnum"  name="ssnum"  value="${ssnum }" style="width: 260px;"></input></td>
			    	 	<th>身份证号</th>
			    	 	<td><input class="input-form-ctrl" id="sfznum"  name="sfznum"  value="${sfznum }" style="width: 260px;"></input></td>
			    	 </tr>
			    	 <tr>
			    	 	<th>市民卡号</th>
			    	 	<td><input class="input-form-ctrl" id="smknum"  name="smknum"  value="${smknum }" style="width: 260px;"></input></td>
			    	 	<th>排序</th>
			    	 	<td><input class="input-form-ctrl" id="seq"  name="seq"  value="${seq }" style="width: 260px;"></input></td>
			    	 </tr>
			    	 <tr>
			    	 	<td align="center" colspan="4">
			    	 		<button class="wf-btn-primary" type="button"  style="size: 16px;margin-top: 10px;" onclick="save();">
			                    <i class="wf-icon-plus-circle" ></i> 确认
			                </button>
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
		var loginName = $('#loginName').val();
		var name = $('#name').val();
		var phone = $('#phone').val();
		var age = $('#age').val();
		var seq = $('#seq').val();
		if(loginName == null || loginName == ''){
			alert("登录名不能为空");
			return;
		}
		if(name == null || name == ''){
			alert("姓名不能为空");
			return;
		}
		if(phone == null || phone == ''){
			alert("手机号码不能为空");
			return;
		}
		if(isNaN(phone)){
			alert("手机号码应为数字");
			return;
		}
		if(seq == null || seq == ''){
			alert("排序不能为空");
			return;
		}
		if(isNaN(seq)){
			alert("排序应为数字");
			return;
		}
		if(age!=""&&age!=null&&isNaN(age)){
			alert("年龄应为数字");
			return;
		}
		$.ajax({
			url : 'siteManage_updateEmp.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :$('#empForm').serialize(),
			error : function(e) {  
				alert('AJAX调用错误(siteManage_updateEmp.do)');
			},
			success : function(msg) {
				if(msg == 'success'){
					alert('保存成功！');
				}else{
					alert('保存失败！');
				}
				//window.parent.parent.location.reload();
				var index = parent.layer.getFrameIndex(window.name); 
				parent.layer.close(index);
			}
		});
	}
</script>
<script type="text/javascript">
	$(function(){
		var siteId = '${siteId}';
		var deptId = '${depId}';
		var userId = '${id}';
		$('#deptId').combotree({
            url : '${ctx}/siteManage_getDeptListBySiteId.do?siteId='+siteId+'&deptId='+deptId+'&id='+userId,
           //	url:'http://192.168.5.133:8086/trueWorkFlow/siteManage_getDeptListBySiteId.do?siteId=b8791369-9b41-47db-b701-33c57e75beda&id=60204b46-7c49-4f76-a0e1-e5b9a181ff0c&deptId=9d13383d-dbd9-4cd6-a2ad-d57748a92f4d',
            parentField : 'pid',
            lines : true,
            panelHeight : '200',
            value : deptId
        });
	});
</script>
</html>