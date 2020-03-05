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
</head>
    <body >
    <table class="infotan" width="100%">
<tr>
 <td class="bgs tc fb">修改附件类型</td>
</tr>
</table>
    	<div align="center">
    	类型名称:&nbsp;&nbsp;<input type="text" name="attachmentType.att_type" id="attachmentType.att_type" value="${attachmentType.att_type}" onchange="checkname();">
    	<input type="hidden" name="attachmentType.id" id="attachmentType.id" value="${attachmentType.id}">
    	</div>
    	<div class="formBar pa" style="bottom:0px;width:100%;"> 
    	<ul class="mr5"> 
	<li><a id="ok" onclick="edit()" name="CmdView" class="buttonActive" href="javascript:;"><span>保存</span></a></li>
	<li><a id="cancel" onclick="cancel()" name="CmdView" class="buttonActive" href="javascript:;"><span>返回</span></a></li>
</ul>
</div>
    </body>
    <script>
    	function edit(){
        	var name = document.getElementById('attachmentType.att_type').value;
        	var id = document.getElementById('attachmentType.id').value;
    		if(""!=name){
    			$.ajax({
    				url:"${ctx}/attachmentType_editAttachmentType.do",
    				type:"post",
    				data:{"attachmentType.att_type":name,"attachmentType.id":id},
    				async : false
    			});
    			window.returnValue=true;
    			window.close();
    		}else{
    			alert("请填写类型");
    		}
        }

    	function cancel(){
    		window.returnValue=false;
    		window.close();
    	}

    	function checkname(){
    		var name = document.getElementById('attachmentType.att_type').value;
    		if(""!=name){
    			$.post("${ctx}/attachmentType_checkName.do?name="+name, null, function(value) {
					 if(value=="1"){
						  alert(' 此类型已存在!') ;
						  $('#attachmentType.att_type').val('') ;
						  return;
					 }
				 });
        	}
        }
    </script>
    <%@ include file="/common/function.jsp"%>
</html>
