<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<base target="_self"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/static/h-ui/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/static/h-ui/skin/blue/skin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/static/h-ui/css/style.css" />
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<meta name="keywords" content="">
<meta name="description" content="">
<style type="text/css">
small{
	display: none;
}
small.invalid{
	color: #a94442;
	display: block;
}

</style>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl">
		    <a class="logo navbar-logo f-l" href="#"><img src="${ctx}/dwz/themes/default/images/logo.png" /></a>
		</div>
	</div>
</header>
<article class="page-container">
    <form action="" method="post" class="form form-horizontal" name="databaseForm" id="databaseForm">
	<p class="f-18 azxd-box">安装向导</p>
	<p class="step-box"><img src="${ctx }/init/static/h-ui/images/step3.jpg" /></p>
	<div style="width: 100%;">
	<div style="width: 50%;margin: 0 auto;">
	<p class="f-16"><i class="Hui-iconfont c-blue">&#xe67e;</i>数据初始化</p>
	<p class="page_title">数据库初始化设置</p>
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-2" style="width: 25%">数据库服务器IP：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${dbIp }" placeholder="" id="dbIp" name="dbIp" style="width:60%;"><small class="" id="small_dbIp"></small>
		</div>
    </div>	
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-2" style="width: 25%">数据库链接端口：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${dbPort }" placeholder="" id="dbPort" name="dbPort" style="width:60%;"><small class="" id="small_dbPort"></small>
		</div>
    </div>	
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-2" style="width: 25%">数据库名称（SID）：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${dbSID }" placeholder="" id="dbSID" name="dbSID" style="width:60%;"><small class="" id="small_dbSID"></small>
		</div>
    </div>	
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-2" style="width: 25%">数据库用户：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${dbUserName }" placeholder="" id="dbUserName" name="dbUserName" style="width:60%;"><small class="" id="small_dbUserName"></small>
		</div>
    </div>	
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-2" style="width: 25%">数据库密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" value="${dbPwd }" placeholder="" id="dbPwd" name="dbPwd" style="width:60%;"><small class="" id="small_dbPwd"></small>
		</div>
    </div>	
    <div class="row cl">
        <div style="text-align:center;">
			<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;上一步&nbsp;&nbsp;" style="margin:0px 10px;" onclick="setp2()">
			<input class="btn btn-danger radius" type="button" value="&nbsp;&nbsp;完成&nbsp;&nbsp;" style="margin:0px 10px;" onclick="save()">
		</div>
    </div>	</div></div>	
    </form>	
</article>

<!--_footer 作为公共模版分离出去--> 
<script type="text/javascript" src="${ctx}/init/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/jquery.validation/1.14.0/messages_zh.min.js"></script> 
<script type="text/javascript" src="${ctx }/init/static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx }/init/static/h-ui/js/H-ui.admin.js"></script> 
<!--/_footer /作为公共模版分离出去--> 
</body>
<script src="${ctx}/gpy/easyui1.2.5/plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">        

function checkForm(){
	var flag = true;
	var dbIp = $('#dbIp').val();
	if(!dbIp){
		alert('数据库服务器IP不能为空');
		flag = false;
	}
	var dbPort = $('#dbPort').val();
	if(!dbPort){
		alert('数据库链接端口不能为空');
		flag = false;
	}
	var dbSID = $('#dbSID').val();
	if(!dbSID){
		alert('数据库名称（SID）不能为空');
		flag = false;
	}
	var dbUserName = $('#dbUserName').val();
	if(!dbUserName){
		alert('数据库用户不能为空');
		flag = false;
	}
	var dbPwd = $('#dbPwd').val();
	if(!dbPwd){
		alert('数据库密码不能为空');
		flag = false;
	}
	return flag;
}

function save(){
	var flag = checkForm();
	if(flag){
		$('#databaseForm').form('submit',{
			url:"${ctx}/setup_updateDbProp.do",
			onSubmit: function(){
				return $(this).form('validate');
			},success: function(result){
				var res = eval("("+result+")");
				if(res.success){
					alert("保存成功");
					window.top.location.href="${ctx }/setup_login.do";
				}else{
					alert(res.error);
				}
			}
		});
	}else{
		var fstInId = $("small.invalid").attr("id").replace("small_","");
		$("#"+fstInId).focus();
	}
	
}

function initDB(){
	$.ajax({
		url : '${ctx}/setup/initDatabase.do',
		type : 'POST',  
		cache : false,
		async : false,
		error : function() {
			return false;
		},
		success : function(result) {  
			var res = eval("("+result+")");
			if(res.success){
				alert("初始化成功");
			}else{
				if(res.error){
					alert(res.error);
				}else{
					alert("初始化失败");
				}
			}
		}
	});
}


$("input:enabled").keyup(function(){
	var $id = $(this).attr("id");
	var $smallEl = $("#small_"+$id);
	$smallEl.html("").removeClass("invalid");
	$(this).parent().parent().removeClass("danger");
	
});

function setp2(){
	window.top.location.href="${ctx }/setup_sysProp.do";
	}

</script>  
</html>