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
		    <form method="get" id="empForm" name="empForm" action="#" class="tw-form">
		    	<input id="siteId" name="siteId" value="${siteId }" type="hidden"></input>
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
			    	 		<input id="deptName" readonly="readonly" name="deptName" value="${deptName }" style="width: 260px;"></input>
			    	 		<input id="deptId"  name="deptId" value="${deptId }" type="hidden"></input>
			    	 	</td>
				   		<th>登录名</th>
			    	 	<td>
			    	 		<input id="loginName"  name="loginName" style="width: 260px;"></input>
			    	 	</td>
				    </tr>
				    <tr>
				   		<th>密码</th>
			    	 	<td>
			    	 		<input id="pwd" type="password" name="pwd" style="width: 260px;"></input>
			    	 	</td>
				   		<th>确认密码</th>
			    	 	<td>
			    	 		<input id="cfmPwd" type="password" name="cfmPwd" style="width: 260px;"></input>
			    	 	</td>
				    </tr>
				    <tr>
				   		<th>姓名</th>
			    	 	<td>
			    	 		<input id="name"  name="name" style="width: 260px;"></input>
			    	 	</td>
				   		<th>性别</th>
			    	 	<td>
			    	 		<!-- <input id="sex"  name="sex" style="width: 260px;"></input> -->
			    	 		<select id="sex"  name="sex" style="width: 260px;">
			    	 			<option value="0" selected>男</option>
			    	 			<option value="1">女</option>
			    	 		</select>
			    	 	</td>
				    </tr>
				    <tr>
				   		<th>年龄</th>
			    	 	<td>
			    	 		<input id="age" name="age" style="width: 260px;"></input>
			    	 	</td>
				   		<th>手机号码</th>
			    	 	<td>
			    	 		<input id="phone" name="phone" style="width: 260px;"></input>
			    	 	</td>
				    </tr>
				    <tr>
				   		<th>电话</th>
			    	 	<td>
			    	 		<input id="tel"  name="tel" style="width: 260px;"></input>
			    	 	</td>
				   		<th>职务</th>
			    	 	<td>
			    	 		<input id="jobTitle"  name="jobTitle" style="width: 260px;"></input>
			    	 	</td>
				    </tr>
		    	 	<tr>
		    	 		<th>传真</th>
			    	 	<td>
			    	 		<input id="fax"  name="fax" style="width: 260px;"></input>
			    	 	</td>
			    	 	<th>短号</th>
			    	 	<td><input id="shortPhoneNum"  name="shortPhoneNum" style="width: 260px;"></input></td>
			    	 </tr>
			    	 <tr>
			    	 	<th>社保卡号</th>
			    	 	<td><input id="ssnum"  name="ssnum"  value="" style="width: 260px;"></input></td>
			    	 	<th>身份证号</th>
			    	 	<td><input id="sfznum"  name="sfznum"  value="" style="width: 260px;"></input></td>
			    	 </tr>
			    	 <tr>
			    	 	<th>市民卡号</th>
			    	 	<td><input id="smknum"  name="smknum"  value="" style="width: 260px;"></input></td>
			    	 	<th>排序</th>
			    	 	<td><input id="seq"  name="seq"  value="${seq+1}" style="width: 260px;"></input></td>
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
		var pwd = $('#pwd').val();
		var cfmPwd = $('#cfmPwd').val();
		var name = $('#name').val();
		var phone = $('#phone').val();
		var age = $('#age').val();
		var seq = $('#seq').val();
		if(loginName == null || loginName == ''){
			alert("登录名不能为空");
			return;
		}
		if(pwd == null || pwd == ''){
			alert("密码不能为空");
			return;
		}
		if(cfmPwd == null || cfmPwd == ''){
			alert("确认密码不能为空");
			return;
		}
		if(cfmPwd != pwd){
			alert("两次密码不一致");
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
			url : 'siteManage_addEmp.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :$('#empForm').serialize(),
			error : function(e) {  
				alert('AJAX调用错误(siteManage_addEmp.do)');
			},
			success : function(msg) {
				var result = msg.split(";")[0];
				var msg =  msg.split(";")[1];
				if(result == 'success'){
					alert('保存成功！');
				}else{
					alert('保存失败！原因：'+msg);
				}
				window.parent.location.reload();
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			}
		});
	}
</script>
</html>