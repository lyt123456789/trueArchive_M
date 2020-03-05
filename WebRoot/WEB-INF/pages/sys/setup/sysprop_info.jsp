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
<style>
  #openTrueCMS {padding-left:0.5rem;font-size:18px;text-decoration:none;}
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
	<form name="sysPropForm"  id="sysPropForm" action="" method="post" autocomplete="off">
	<p class="f-18 azxd-box">安装向导</p>
	<p class="step-box"><img src="${ctx}/init/static/h-ui/images/step2.jpg" /></p>
	<!--  <p class="page_title">配置的各项服务</p>-->
	<div style="width: 100%;">
	<div style="width: 70%;margin: 0 auto;">
	<p class="f-16"><i class="Hui-iconfont c-blue">&#xe67e;</i>系统参数设置</p>
    <table class="table table-border table-bordered table-bg" style="width: 100%;">
		<thead>
			<tr>
				<th colspan="2" scope="col"><u>基本</u>设置</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${list}" varStatus="status"> 
				<tr><td width="30%;">${item.dicName }<c:if test="${item.dicRemark eq 'true' }"><font color=red style="position: absolute;">*</font></c:if>：</td>
				<td class="skin-minimal">
					<input type="text" id="${item.dicName }" 
					data-dicName="${item.dicName }" 
					name="${item.dicKey }" value="${item.dicValue }" 
					<c:if test="${item.dicRemark eq true }">class="input-text true"</c:if>
					<c:if test="${item.dicRemark ne true }">class="input-text "</c:if>
					>
				</td></tr>
			
			
			</c:forEach>
		
		</tbody>
	</table>
	</div></div>
    <div class="row cl">
        <div style="text-align:center;">
			<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;上一步&nbsp;&nbsp;" style="margin:0px 10px;" onclick="setp1()">
			<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;下一步&nbsp;&nbsp;" onclick="setp3()">
		</div>
    </div>	
    </form>	
</article>

<!--_footer 作为公共模版分离出去--> 
<script type="text/javascript" src="${ctx}/init/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/jquery/1.9.1/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript" src="${ctx}/init/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="${ctx}/init/lib/jquery.validation/1.14.0/messages_zh.min.js"></script>
<script type="text/javascript" src="${ctx}/init/static/h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/init/static/h-ui/js/H-ui.admin.js"></script> 
<!--/_footer /作为公共模版分离出去--> 
<!--请在下方写此页面业务相关的脚本--> 
<script src="${ctx}/gpy/easyui1.2.5/plugins/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
});



function checkForm(){
	var flag = true;
	$(".true").each(function(){
		var value = $(this).val();
		if(!value){
			alert($(this).attr("data-dicName")+'不能为空！');
			flag = false;
			return ;
		}
		
	});
	
	/**var pagesize = $('#pagesize').val();
	if(!pagesize){
		alert('每页展示列表条数不能为空');
		flag = false;
	}
	var rootPath = $('#rootPath').val();
	if(!rootPath){
		alert('系统根目录不能为空');
		flag = false;
	}
	var fjlx = $('#fjlx').val();
	if(!fjlx){
		alert('附件允许类型不能为空');
		flag = false;
	}
	var sizeLimit = $('#sizeLimit').val();
	if(!sizeLimit){
		alert('附件允许上传大小不能为空');
		flag = false;
	}
	var mobileAddress = $('#mobileAddress').val();
	if(!mobileAddress){
		alert('移动端访问地址不能为空');
		flag = false;
	}
	var attachment_path = $('#attachment_path').val();
	if(!attachment_path){
		alert('不能为空');
		flag = false;
	}
	*/
	
	return flag;
}
function setp1(){
	window.top.location.href="${ctx }/setup_licenceInfo.do";
}

function setp3(){
	var flag = checkForm();
	if(flag){
		
		$('#sysPropForm').form('submit',{
			url:"${ctx}/setup_updateSysProp.do",
			onSubmit: function(){
				return $(this).form('validate');
			},success: function(result){
				var res = eval("("+result+")");
				if(res.success){
					window.top.location.href="${ctx }/setup_databaseInfo.do";
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
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>