<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
</head>
<body>
<form id="vmform" method="post" action="${ctx}/vm_addVm.do?instanceId=${instanceId}" style="font-family: 宋体; font-size: 12pt;">
<table class="infotan" width="100%">
	<tr>
		<td width="20%" class="bgs ls">版本号：</td>
		<td width="80%"><input type="text" name="num" id="num" style="width: 200px;height: 22px"/></td>
	</tr>
	<tr>
		<td width="20%" class="bgs ls">版本内容：</td> 
		<td width="80%">
			<textarea name="content" cols="100" rows="20" id="content"></textarea> 
		</td>
	</tr>
	<tr>
		<td class="bgs ls">已应用项目：</td>
		<td><input type="text" name="applyProject" id="applyProject" style="width: 200px;height: 22px"></td>
	</tr>
	<tr>
      	<td width="20%" class="bgs ls">附件：</td> 
	  	<td width="80%">
		  <span id="fjshow"></span>
	      <trueway:att onlineEditAble="true" id="${instanceId}fj" docguid="${instanceId}fj" showId="fjshow" ismain="true" downloadAble="true" uploadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
	 	</td>
    </tr>
</table>

<div class="formBar pa" style="bottom:0px;width:100%;">  
<ul class="mr5"> 
<li><a class="buttonActive" href="javascript:checkForm();"><span>提交</span></a></li>
<li><a class="buttonActive" href="javascript:window.history.go(-1);"><span>返回</span></a></li>
</ul>
</div>
</form>
</body>
<script type="text/javascript">
	function checkForm(){
		var num = document.getElementById("num").value;
		if(!num){
			alert('请填写版本号');
			return;
		}
		var content = document.getElementById("content").value;
		if(!content){
			alert('请填写内容');
			return;
		}
		var applyProject = document.getElementById("applyProject").value;
		if(!applyProject){
			alert('请填写应用项目');
			return;
		}
		document.getElementById('vmform').submit();
	}

</script>
<script src="/dwz/style/js/jquery-1.7.1.min.js"></script>
<script src="/dwz/style/js/jquery.tab.js"></script>
<script type="text/javascript"> 
	//以下必须有
	function loadCss(){  
   		seajs.use('lib/form',function(){  
			$('input[mice-btn]').cssBtn();
			$('input[mice-input]').cssInput();
			$('select[mice-select]').cssSelect();
	    });
	}
	//以上必须有
</script>
<%@ include file="/common/function.jsp"%>
</html>
