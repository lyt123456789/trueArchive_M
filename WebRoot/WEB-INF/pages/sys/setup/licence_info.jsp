<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<base target="_self"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<%@ include file="/common/header.jsp"%>
<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js?type=2016" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/init/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/static/h-ui/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/static/h-ui/skin/blue/skin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/init/static/h-ui/css/style.css" />
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<meta name="keywords" content="">
<meta name="description" content="">
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
    <form name="licenceForm"  id="licenceForm" action="" method="post" enctype="multipart/form-data" autocomplete="off" class="form">
	<p class="f-18 azxd-box">安装向导</p>
	
	<p class="step-box"><img src="${ctx }/init/static/h-ui/images/step1.jpg" /></p>
	<div style="width: 100%;">
	<div style="width: 50%;margin: 0 auto;">
	<p class="f-16"><i class="Hui-iconfont c-blue">&#xe67e;</i>申请license</p>
	<p class="page_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写产品名称、版本号，并根据MAC地址申请Licence文件。</p>
	<div class="row cl">
	    <label class="form-label col-sm-2 c-blue" style="width: 22%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品名称<span style="color: red;">*</span>：</label>
	    <div class="formControls col-sm-9 c-warning"><input class="input-text upload-url" id="productName" name="productName" type="text" value="${productName}" readonly="readonly"/></div>
	</div>
		<div class="row cl">
	    <label class="form-label col-sm-2 c-blue" style="width: 22%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版   本   号<span style="color: red;">*</span>：</label>
	    <div class="formControls col-sm-9 c-warning"><input  class="input-text upload-url" name="version" id="version" type="text" value="${version}" autocomplete="off" readonly="readonly"/></div>
	</div>
		<div class="row cl">
	    <label class="form-label col-sm-2 c-blue" style="width: 22%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MAC 地址：</label>
	    <div class="formControls col-sm-9 c-warning"><input  class="input-text upload-url" name="macAddr" id="macAddr" type="text" disabled="disabled" value="${macAddr }" autocomplete="off"/></div>
	</div>
	<p class="f-16 mt-20"><i class="Hui-iconfont c-blue">&#xe67e;</i>license文件上传</p>
	<p class="page_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请将license文件上传至服务器上
	<span style="color: red;" id="errorinfo">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${result!=null && result!=''}">
			<c:if test="${result=='2'}">license超过有效期</c:if>
			<c:if test="${result=='3'}">license的版本验证不正确</c:if>
			<c:if test="${result=='4'}">license的mac验证不正确</c:if>
		</c:if>
		</span>	
	</p>
	<div class="row cl">
	    <label class="form-label col-sm-2 c-blue" style="width: 24%;"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;license文件：</label>
	    <div class="formControls col-sm-9">
		    <span class="btn-upload form-group">
				<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" readonly="" nullmsg="请选择文件！" style="width:200px">
				<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont"></i> 选择文件</a>
				<input type="file" multiple="" name="licenceFile" id="licenceFile" class="input-file valid">
		    </span>
		      <c:if test="${licensePath!=null && licensePath!=''}">
				<input class="btn btn-primary radius" type="button" onclick="downloadLicense('${licensePath}');" value="下载文件">
			</c:if>	
		</div>
	</div>
	<div id="licenceInfo" style="display: none;">
		<p class="f-16 mt-20"><i class="Hui-iconfont c-blue">&#xe67e;</i>Licence信息</p>
		<div class="row cl">
		    <label class="form-label col-sm-2 c-blue">有效日期：</label>
		    <div class="formControls col-sm-9 c-warning" id="validity"></div>
		</div>
	</div>
    <div class="row cl" id="setBut">
         <div style="text-align:center;">
			<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;下一步&nbsp;&nbsp;" onclick="setp2()">
		</div>
    </div>
    </div>
  </div>
</form>	
</article>
<script type="text/javascript" src="${ctx}/init/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/jquery.validation/1.14.0/messages_zh.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/init/static/h-ui/js/H-ui.admin.js"></script> 
</body>
<script src="${ctx}/gpy/easyui1.2.5/plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">   
$("#licenceFile").change(function(){
	var licenceFile = $("input[id='licenceFile']").val();
	if(licenceFile != null && licenceFile != ''){
		var extStart = licenceFile.lastIndexOf(".");
		var ext = licenceFile.substring(extStart, licenceFile.length).toUpperCase();
		console.log(ext);
		if(ext == '.lic' || ext == '.LIC'){
			$('#licenceForm').form('submit',{
				url:"${ctx}/setup_uploadLicence.do",
				onSubmit: function(){
					return $(this).form('validate');
				},success: function(result){
					var res = eval("("+result+")");
					if(res.success){
						$("#validity").html(res.error.validity);
						$("#setupNext").attr("disabled",false);
						$("#uploadLicence").hide();
						$("#licenceInfo").show();
						$("#setBut").show();
						$("#errorinfo").hide();
					}else{
						alert(res.error);
						var info = res.result;
						$("#errorinfo").show();
						if(info!=null){
							if(info=='2'){
								document.getElementById("errorinfo").innerHTML="license超过有效期";
							}
							if(info=='3'){
								document.getElementById("errorinfo").innerHTML="license的版本验证不正确";
							}
							if(info=='4'){
								document.getElementById("errorinfo").innerHTML="license的mac验证不正确";
							}
						}
						$("#licenceInfo").hide();
						$("#setBut").hide();
					}
				}
			});
		}else{
			alert("Licence格式不正确。");
		}
	}
	
});

function setp2(){
	
	window.top.location.href="${ctx }/setup_sysProp.do";
}
$(function(){
	var validity = "${validity}";
	var productName = "${productName}";
	var version = "${version}";
	if(validity != ''){
		$("#validity").html(validity);
		$("#productName").val(productName);
		$("#version").val(version);
		$("#setupNext").attr("disabled",false);
		$("#uploadLicence").hide();
		$("#licenceInfo").show();
	}else{
		$("#setBut").hide();
	}
});
</script>  
</html>