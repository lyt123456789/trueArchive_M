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
 <div class="pageContent" >
 	<table class="infotan" width="100%">
<tr>
 <td class="bgs tc fb">回复单</td>
</tr>
</table>
	<form name="thisForm"  id="thisForm" action="${ctx }/table_reply.do" method="post">
	<input type="hidden" name="id" id="id" value="${id}"/>
	<INPUT type="hidden" id="instanceId" name="instanceId" value="${instanceId}"> 
	<INPUT type="hidden" id="processId" name="processId" value="${processId}">  
	<INPUT type="hidden" id="itemId" name="itemId" value="${itemId}">  

	<table class="infotan mt5" width="100%">
	<tr>
	<td valign="top" class="bgs ls">文件标题:</td><td class="tdcol" colspan="3"><input type="text" name="title" value="" style="width:400px;"/></td>
	</tr>
	<tr>
		<td valign="top" class="bgs ls">附件：</td>
		<td class="tdcol" colspan="3"><br/> 
			<span id="fjshow"></span>
			<trueway:att onlineEditAble="true" id="${instanceId}fj" docguid="${instanceId}fj" showId="fjshow" ismain="true" uploadAble="true" downloadAble="true" deleteAble="true" previewAble="true" tocebAble="false" toStampAble="true" openBtnClass="icon-add" otherBtnsClass="icon-help" uploadCallback="loadCss" deleteCallback="loadCss"/>
		</td>
	</tr>
<tr>
	<td valign="top" class="bgs ls">来文号：</td><td><input type="text" name="bh" id="bh"  value="${bh}" style="width:400px;"/></td>
	</tr>
</table>
		
		</form>
</div>
<div class="formBar pa" style="bottom:0px;width:100%;">  
<ul class="mr5"> 
<li><a class="buttonActive" href="javascript:send();"><span>提交</span></a></li>
<li><a class="buttonActive" href="javascript:window.close();"><span>返回</span></a></li>
</ul>
</div>

</body>
<%@ include file="/common/function.jsp"%> 

<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script>
function send(){
	// 非空判断
	var title = document.getElementById("title").value;
	if(title == ""){
		alert("标题不能为空");
		return ;
	}
	var bh = document.getElementById("bh").value;
	if(bh == ""){
		alert("办文编号为空");
		return ;
	}
	var fjCount = 0;
	// 判断附件是否为空
	$.ajax({  
			url : 'attachment_getCountOfFj.do',
			type : 'POST',   
			cache : false,
			async : false,
			data : {
				'docguid':'${instanceId}fj'
			},
			error : function() {  
				alert('AJAX调用错误(attachment_getCountOfFj.do)');
			},
			success : function(msg) {
				fjCount = msg;
			}
	});
	if(fjCount == 0 || fjCount == "0"){
		alert("请上传附件");
		return ;
	}
	$.ajax({  
		url : 'table_reply.do',
		type : 'POST',   
		cache : false,
		async : false,
		data : {
			'processId':'${processId}',
			'title':title,
			'bh':bh,
			'id':'${id}',
			'instanceId':'${instanceId}',
			'itemId':'${itemId}'
		},
		error : function() {  
			alert('AJAX调用错误(table_reply.do)');
			 window.returnValue = "no";
		},
		success : function(msg) {
			if(msg == "yes"){
				alert("回复成功");
				window.close();
				  window.returnValue = "yes";
			}else{
				alert("回复失败");
				window.close();
				  window.returnValue = "no";
			}
			
		}
	
	});
	//document.getElementById('thisForm').submit();
}

</script>
</html>