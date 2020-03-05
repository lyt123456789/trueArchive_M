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
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<ul>
			<li class="current" id="licence"><a href="javascript:showForm('licence')"><i class="Hui-iconfont">&#xe67a;</i> license文件上传</a></li>
			<li id="sysProp"><a href="javascript:showForm('sysProp')"><i class="Hui-iconfont">&#xe67a;</i> 系统参数设置</a></li>
			<li id="database"><a href="javascript:showForm('database')"><i class="Hui-iconfont">&#xe67a;</i> 数据库初始化</a></li>
		</ul>	
	</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box" id="licenceSection">
<form name="licenceForm"  id="licenceForm" action="" method="post" enctype="multipart/form-data" autocomplete="off" class="form">
	<div class="Hui-article">
	<article class="page-container">
	<p class="f-16"><i class="Hui-iconfont c-blue">&#xe67e;</i>申请license</p>
	<p class="page_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写产品名称、版本号，并根据MAC地址申请license文件。</p>
	<div class="row cl">
	    <label class="form-label col-sm-2 c-blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品名称：</label>
	    <div class="formControls col-sm-9 c-warning"><input class="input-text upload-url" id="productName" name="productName" type="text" value="${productName}" readonly="readonly"/><span style="color: red;">*</span></div>
	</div>
		<div class="row cl">
	    <label class="form-label col-sm-2 c-blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版   本   号：</label>
	    <div class="formControls col-sm-9 c-warning"><input  class="input-text upload-url" name="version" id="version" type="text" value="${version}" autocomplete="off"  readonly="readonly"/><span style="color: red;">*</span></div>
	</div>
		<div class="row cl">
	    <label class="form-label col-sm-2 c-blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MAC 地址：</label>
	    <div class="formControls col-sm-9 c-warning"><input  class="input-text upload-url" name="macAddr" id="macAddr" type="text" disabled="disabled" value="${macAddr }" autocomplete="off"/></div>
	</div>
	<p class="f-16 mt-20"><i class="Hui-iconfont c-blue">&#xe67e;</i>license文件上传</p>
	<p class="page_title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请将license文件上传至服务器上
	  <c:if test="${result!=null && result!=''}">
		<span style="color: red;" id="errorinfo">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${result=='2'}">license超过有效期</c:if>
			<c:if test="${result=='3'}">license的版本验证不正确</c:if>
			<c:if test="${result=='4'}">license的mac验证不正确</c:if>
		</span>	
	 </c:if>
	</p>
	<div class="row cl">
	    <label class="form-label col-sm-2 c-blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;license文件：</label>
	    <div class="formControls col-sm-9">
		    <span class="btn-upload form-group">
				<input class="input-text upload-url" type="text" name="uploadfile" id="uploadfile" readonly="" nullmsg="请选择文件！" style="width:200px">
				<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont"></i> 选择文件</a>
				<input type="file" multiple="" name="licenceFile" id="licenceFile" class="input-file valid">
		    </span>
		    <c:if test="${licensePath!=null && licensePath!=''}">
				<input type="button"  class="btn btn-primary radius"  onclick="downloadLicense('${licensePath}');" value="下载文件">
			</c:if>	
		</div>
	</div>
	<div id="licenceInfo">
		<p class="f-16 mt-20"><i class="Hui-iconfont c-blue">&#xe67e;</i>license信息</p>
		<div class="row cl">
		    <label class="form-label col-sm-2 c-blue">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;有效日期：</label>
		    <div class="formControls col-sm-9 c-warning" id="validity"></div>
		</div>
	</div>
	</article>
	</div>
	</form>
</section>
<section class="Hui-article-box" id="sysPropSection" style="display: none;">
<form name="sysPropForm"  id="sysPropForm" action="" method="post" enctype="multipart/form-data" autocomplete="off" class="form">
	<div class="Hui-article">
	<article class="page-container">
	<p class="f-16"><i class="Hui-iconfont c-blue">&#xe67e;</i>系统参数设置</p>
	<p class="page_title">配置的各项服务</p>
    <table class="table table-border table-bordered table-bg">
		<thead>
			<tr>
				<th colspan="2" scope="col">基本设置</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${list}" varStatus="status"> 
				<tr><td width="20%;" >${item.dicName}<c:if test="${item.dicRemark eq 'true' }"><font color=red style="position: absolute;">*</font></c:if>：</td>
				<td class="skin-minimal">
					<input type="text" id="${item.dicName }" 
					data-dicName="${item.dicName }" 
					name="${item.dicKey }" value="${item.dicValue }" 
					<c:if test="${item.dicRemark eq true }">class="input-text true"</c:if>
					<c:if test="${item.dicRemark ne true }">class="input-text "</c:if>
					<c:if test="${item.sample != null && item.sample != ''}">
					placeholder="示例:${item.sample}"
					</c:if>
					>
				</td></tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="row cl">
        <div style="text-align:center;">
			<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;保存&nbsp;&nbsp;" onclick="saveSysProp()">
		</div>
    </div>
    </article>	
	</div>
	</form>
</section>
<section class="Hui-article-box" id="databaseSection" style="display: none;">
<form name="databaseForm"  id="databaseForm" action="" method="post" enctype="multipart/form-data" autocomplete="off" class="form">
	<div class="Hui-article">
	<article class="page-container">
	<p class="f-16"><i class="Hui-iconfont c-blue">&#xe67e;</i>数据初始化</p>
	<p class="page_title">数据库初始化设置</p>
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-2" style="width: 18%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据库服务器IP：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${dbIp}" placeholder="" id="dbIp" name="dbIp" style="width:60%;"><small class="" id="small_dbIp"></small>
		</div>
    </div>	
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-2"style="width: 18%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据库链接端口：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${dbPort }" placeholder="" id="dbPort" name="dbPort" style="width:60%;"><small class="" id="small_dbPort"></small>
		</div>
    </div>	
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-2" style="width: 18%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据库名称（SID）：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${dbSID }" placeholder="" id="dbSID" name="dbSID" style="width:60%;"><small class="" id="small_dbSID"></small>
		</div>
    </div>	
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-2" style="width: 18%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据库用户：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${dbUserName }" placeholder="" id="dbUserName" name="dbUserName" style="width:60%;"><small class="" id="small_dbUserName"></small>
		</div>
    </div>	
    <div class="row cl">
		<label class="form-label col-xs-4 col-sm-2" style="width: 18%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据库密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" value="${dbPwd }" placeholder="" id="dbPwd" name="dbPwd" style="width:60%;"><small class="" id="small_dbPwd"></small>
		</div>
    </div>	
    <div class="row cl">
        <div style="text-align:center;">
			<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;保存&nbsp;&nbsp;" onclick="saveDbProp()">
		</div>
    </div>
    </article>	
	</div>
	</form>
</section>
<iframe id="download_iframe" name="download_iframe" style="display: none"></iframe>
</body>
<script type="text/javascript" src="${ctx}/init/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="${ctx}/init/static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/init/static/h-ui/js/H-ui.admin.js"></script> 
<script src="${ctx}/gpy/easyui1.2.5/plugins/jquery.form.js" type="text/javascript"></script><script type="text/javascript">  
function showForm(formName){
	$("section").hide();
	$("#"+formName+"Section").show();
	$("li").removeClass("current");
	$("li#"+formName).addClass("current");
}

$("input[id='licenceFile']").change(function(){
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
					}
				}
			});
		}else{
			alert("Licence格式不正确。");
		}
	}
	
});

$(function(){
	var validity = "${validity}";
	var productName = "${productName}";
	var version = "${version}";
	if(validity != ''){
		$("#validity").html(validity);
		$("#productName").val(productName);
		$("#version").val(version);
	}
});

function checkForm(){
	var flag = true;
	$(".true").each(function(index){
		var value = $(this).val();
		if(!value){
			alert($(this).attr("data-dicName")+'不能为空！');
			flag = false;
			return ;
		}
		
	});
	
	return flag;
}
function saveSysProp(){
	var flag = checkForm();
	if(flag){
		
		$('#sysPropForm').form('submit',{
			url:"${ctx}/setup_updateSysProp.do?flag=1",
			onSubmit: function(){
				return $(this).form('validate');
			},success: function(result){
				var res = eval("("+result+")");
				if(res.success){
					alert("保存成功");
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

$("input:enabled").keyup(function(){
	var $id = $(this).attr("id");
	var $smallEl = $("#small_"+$id);
	$smallEl.html("").removeClass("invalid");
	$(this).parent().parent().removeClass("danger");
	
});

function checkDBForm(){
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

function saveDbProp(){
	var flag = checkDBForm();
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

</script>  
</html>