<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <link rel="stylesheet" href="css/login.css">
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ page language="java" pageEncoding="UTF-8"%><!--告诉服务器，页面用的是UTF-8编码-->
<%@ include file="/common/header.jsp"%> 

   
<script type="text/javascript">
	//如果系统为单点登录直接跳过普通登录页面
	//alert('${ssologin}');
	if('${ssologin}'=='1'){
		window.location.href='${ctx }/login_ssoLogin.do';
	};
</script>
</head>

<body <c:if test="${ssologin=='1'}">style="background-color: white;"</c:if> >

<c:if test="${ssologin=='1'}"><h3>系统正在为您努力登录中。。。。。。</h3></c:if>
<div class="login">
    <div class="logo">
                     数字档案管理系统
    </div>
    <div class="inner">
        <div class="form">
        	<form id="thisForm" method="post" name="thisForm" action="${ctx }/login_LoginOn.do">
	            <!--用户名-->
	            <input class="input" type="text" name="username" id="username"  placeholder="请输入账号" />
	            <!--密码-->
	            <input class="input" type="password" name="password" id="password" placeholder="密码"/>
	            <div class="rem" style="width: 50%;float: left;">
	                <input type="checkbox" class="check" id="rememberMe" onclick="saveCookie()"/><span>记住密码</span>
	            </div>
	             <div class="rem" style="width: 50%;float: left;">
	                <input type="checkbox" class="check" id="zzcdFlag"/><span>自主查档</span>
	            </div>
				<input class="input" type="hidden" name="type" id="type" value="2" />
	            <button type="button" class="btn_login" id="button" onclick="login()">登录</button>
	         </form>
        </div>
    </div>
</div>
<script src="${ctx}/widgets/tree/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	window.onload=function(){
		if('${mes}'!='')alert('用户不存在或密码错误，请重新登录');
		document.getElementById('username').focus();
	}; 
	$(function(){
		  //获取cookie
		  var cusername = getCookie('customername');
		  var cpassword = getCookie('customerpass');
		  if(cusername != "" && cpassword != ""){
		    $("#username").val(cusername);
		    $("#password").val(cpassword);
		    document.getElementById('rememberMe').checked=true;
		  }
		});
	
	//回车监听
	document.onkeydown = keyDown;
	function keyDown(e){ 
		if((e ? e.which : event.keyCode)==13 ){
			document.getElementById("button").click();
		}
	}
	
	function login(){
		var zzcdFlag = document.getElementById('zzcdFlag');
		var username=document.getElementById('username').value;
		var password=document.getElementById('password').value;
		if(username==''){
			alert('请输入登录名');
			username.focus();
			return;
		};
		if(password==''){
			alert('请输入密码');
			password.focus();
			return;
		};
		if(zzcdFlag.checked){
			$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"text",//返回值类型
		        data:{"username":username,"password":password},
		        url:"${ctx}/model_checkCasualUser.do",
		        success:function(text){
		        	 if(text=="success"){
		        		document.getElementById('thisForm').action="${ctx }/model_zzcdLogin.do?zzcdFlag=1";
		     			document.getElementById('thisForm').submit();
		        	 }else{
		        		 alert(text);
		        	 }
		        }
		    });
		}else{
			document.getElementById('thisForm').submit();
		}
		
	};  

	function saveCookie(){
		if($('#rememberMe').is(':checked')){
			setCookie('customername', $('#username').val().trim(), 7);
			setCookie('customerpass', $('#password').val().trim(), 7);
		}else{
			clearCookie('customername');
		}
	}
	
	//设置cookie
	var passKey = '4c05c54d952b11e691d76c0b843ea7f9';
	function setCookie(cname, cvalue, exdays) {
		var d = new Date();
		d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
		var expires = "expires=" + d.toUTCString();
		document.cookie = cname + "=" + cvalue + "; "
				+ expires;
	}
	//获取cookie
	function getCookie(cname) {
		var name = cname + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1);
			if (c.indexOf(name) != -1) {
				var cnameValue = unescape(c.substring(name.length, c.length));
				return cnameValue;
			}
		}
		return "";
	}
	//清除cookie  
	function clearCookie(cname) {
		setCookie(cname, "", -1);
	}
</script>
</body>
</html>
