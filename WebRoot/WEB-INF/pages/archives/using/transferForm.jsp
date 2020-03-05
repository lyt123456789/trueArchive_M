<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
	<link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
	<style type="text/css">
	.title{
		background: #008cee;
    	height: 40px;
	}
	.font{
		font-size: 16px;
    	color: #fff;
   	 	padding-top: 8px;
    	padding-left: 18px;
	}
	.table{
		width: 100%;
    	height: 100%;
    	border: none !important;
    	font-size: 16px;
	}
	.input {
		width: 100%;
		height: 30px;
		border-radius: 5px;
		border: 1px solid #9a9a9a;
	}
	.textarea {
		width: 100%;
		height: 100%;
		resize:none;
		border-radius: 5px;
		border: 1px solid #9a9a9a;
	}
	.select {
		width: 100%;
		height: 30px;
		border-radius: 5px;
		border: 1px solid #9a9a9a;
	}
	.cz-btn{
		padding: 6px 14px;
    	font-size: 15px;
    	color: #444;
    	background-color: #e6e6e6;
    	border-color: #e6e6e6;
	}
	.save-btn{
		padding: 6px 14px;
    	font-size: 15px;
    	color: #444;
    	background-color: #008cee;
    	border-color: #008cee;
	}
	</style>
</head>
<body>
    <div class="tw-layout" style="overflow: auto;">
        <div class="tw-container">
			<form id="itemform" method="post" action="${ctx}/using_saveBasicData.do" class="tw-form">
				<input type="hidden"  id = "id"   name = "id" value="${tf.id }" />
				<input type="hidden"  id = "registrantName"   name = "registrantName" value="${tf.registrantName}" />
				<table  class="tw-table " >
					<tr>
			     		<td align="center" >申请单编号</td>
			     		<td align="left" ><input  class="input" name="formId" readonly="readonly" value="${tf.formId}"/></td>
			     		<td align="center" >接待人</td>
			     		<td align="left" ><input  class="input" name="registrantName" readonly="registrantName" value="${tf.registrantName}"/></td>
			     	</tr>
			     	<tr>
			     	<td align="center" >申请时间</td>
			     		<td align="left" ><input  class="input" name="applyTime" readonly="readonly" value="${tf.applyTime}"/></td>
			     		<td align="center" >申请人</td>
			     		<td align="left" ><input  class="input" name="applyName" value="${tf.applyName}"/></td>
			     		
			     	</tr>
			     	<tr>
			     		<td align="center" >申请单位</td>
			     		<td align="left" ><input  class="input" name="applyUnit" value="${tf.applyUnit }"/></td>
			     		<td align="center" >调卷类型</td>
			     		<td align="left" >
			     			<select class="select" id="transferType" name="transferType">
			     				<option value="档案" <c:if test="${'档案' eq tf.transferType}">selected="selected"</c:if>>档案</option>
			     				<option value="资料" <c:if test="${'资料' eq tf.transferType}">selected="selected"</c:if>>资料</option>
			     				<option value="现行文件" <c:if test="${'现行文件' eq tf.transferType}">selected="selected"</c:if>>现行文件</option>
			     			</select> 
			     		</td>
			     		
			     	</tr>
					<tr>
						<td align="right" colspan="2">
							 <div class="">
								<a class="wf-btn cz-btn"  href="javascript:" onclick="closelayer()">取消
								</a>
							 </div>
						</td>
						<td align="left" colspan="2">
							<div class="">
								<c:if test="${'0' eq editFlag }">
									<a class="wf-btn-primary save-btn" href="javascript:" onclick="addTransferForm()" > 添加并检索</a>
								</c:if>
								<c:if test="${'1' eq editFlag }">
									<a class="wf-btn-primary save-btn" href="javascript:" onclick="updateTransferForm('0')" > 编辑</a>
									<a class="wf-btn-primary save-btn" href="javascript:" onclick="updateTransferForm('1')" > 编辑并检索</a>
								</c:if>
							 </div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function (){
    var left = $("#leftTable").height()
    var right = $("#rightTable").height()
    var cnum = left - right;
    if(cnum>0){
    	$("#rightTable").css("margin-top","-"+cnum+"px");
    }else{
    	$("#leftTable").css("margin-top","-"+cnum+"px");
    }
    $(".tw-layout").css("height",$(window).height());
});
	function closelayer(){
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
	
	function addTransferForm(){	
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data: $('#itemform').serialize(),
	        url:"${ctx}/using_addTransferForm.do",
	        success:function(result){
        		if("" == result){
	        		alert("保存失败");
	        	}else{
	        		alert("保存成功");
	        		var url = "${ctx}/model_toSearchJsp.do?djdId="+result;
       				parent.layer.open({
       					title:"调卷检索",
       				    type: 2,
       				    content: url,
       				    area: ['100%', '100%'],
       		            cancel: function(){
       		                 //右上角关闭回调
       		                 parent.location.reload();
       		              	 closelayer();
       		            }
       				});
	        	}
	        	
	        }
	    });
	}
function updateTransferForm(flag){
	$.ajax({
        async:true,//是否异步
        type:"POST",//请求类型post\get
        cache:false,//是否使用缓存
        dataType:"text",//返回值类型
        data: $('#itemform').serialize(),
        url:"${ctx}/using_updateTransferForm.do",
        success:function(result){
    		if("" == result){
        		alert("保存失败");
        	}else{
        		alert("保存成功");
        		if(flag=="1"){
        			var url = "${ctx}/model_toSearchJsp.do?djdId=${tf.id}";
       				parent.layer.open({
       					title:"调卷检索",
       				    type: 2,
       				    content: url,
       				    area: ['100%', '100%'],
       		            cancel: function(){
       		                 //右上角关闭回调
       		                 parent.location.reload();
       		            }
       				});
        		}else{
        			parent.location.reload();
        			closelayer();
        		}	
        	}
        }
    });
}
</script>
<%@ include file="/common/function.jsp"%>
</html>
