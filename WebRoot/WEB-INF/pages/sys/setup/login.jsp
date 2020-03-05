<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ page language="java" pageEncoding="UTF-8"%><!--告诉服务器，页面用的是UTF-8编码-->
<%@ include file="/common/header.jsp"%> 
<style>
body{ background:#EEEEEE url(${ctx}/widgets/login/login.jpg) top center no-repeat;}
.loginconter{width:500px;height:540px;position:relative;overflow:hidden;margin:0px auto;}
.username{border:0px;width:223px;height:24px;line-height:24px; color:#666; position:absolute;left:124px;top:286px;}
.password{border:0px;width:223px;height:24px;line-height:24px; color:#666; position:absolute;left:124px;top:333px;}
.loginbtn{border:0px;width:85px;height:69px;position:absolute;left:374px;top:288px; background:none; cursor:pointer;}
</style>
</head>

<body>

<div class="loginconter" >
	<form id="thisForm" method="post" name="thisForm" action="${ctx }/setup_loginOn.do">
			<input class="username" type="text" name="username" id="username" />
			<input class="password" type="password" name="password" id="password" />
			<input class="loginbtn" type="button" name="button" id="button" onclick="login1()" />
	</form>
</div>
<script src="${ctx}/widgets/tree/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/gpy/easyui1.2.5/plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
	window.onload=function(){
		if('${mes}'!='')alert('用户不存在或密码错误，请重新登录');
		document.getElementById('username').focus();
	}; 

	//回车监听
	document.onkeydown = keyDown;
	function keyDown(e){ 
		if((e ? e.which : event.keyCode)==13 ){
			document.getElementById("button").click();
		}
	}
	
	function login1(){
		var username=document.getElementById('username');
		var password=document.getElementById('password');
		if(username.value==''){
			alert('请输入登录名');
			username.focus();
			return;
		};
		if(password.value==''){
			alert('请输入密码');
			password.focus();
			return;
		};
		
		$('#thisForm').form('submit',{
			url:"${ctx}/setup_loginOn.do",
			onSubmit: function(){
				return $(this).form('validate');
			},success: function(result){
				var res = eval("("+result+")");
				if(res.success){
						if(res.error == "0"){
							window.top.location.href="${ctx }/setup_licenceInfo.do";
						}else if(res.error == "1"){
							window.top.location.href="${ctx }/setup_sysProp.do";
						}else if(res.error == "2"){
							window.top.location.href="${ctx }/setup_databaseInfo.do";
						}else if(res.error == "success"){
							window.top.location.href="${ctx }/setup_setupInfo.do";
						}else{
							window.top.location.href="${ctx }/setup_licenceInfo.do";
						}
				}else{
					alert(res.error);
				}
			}
		});
	};   
</script>
</body>
</html>
