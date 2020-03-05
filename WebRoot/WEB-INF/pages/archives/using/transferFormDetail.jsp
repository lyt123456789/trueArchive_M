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
	
	.not-btn {
		padding: 4px 10px;
	    font-size: 15px;
	    color: white;
	    background-color:#c2c2c2;
	    border-color: #c2c2c2;
	}
	</style>
</head>
<body>
    <div class="tw-layout" style="overflow: auto;">
    	<div style="background-color: aliceblue;height: 40px;">
    	<c:if test="${'1' eq statusSe }">
    		<a class="wf-btn-primary save-btn" href="javascript:" onclick="updateTransferForm('sh')" >审核</a>
    	</c:if>
    	<c:if test="${'2' eq statusSe && '1' eq gjFlag}">
    		<a class="wf-btn-primary save-btn" href="javascript:" onclick="updateTransferForm('gj')" >归卷</a>
    	</c:if>	
    	<c:if test="${'3' eq statusSe && '1' eq gjFlag}">
    		<a class="wf-btn-primary not-btn" href="javascript:">已归卷</a>
    	</c:if>	
    	</div>
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
			     		<td align="left" ><input  class="input" name="applyName" <c:if test="${'1' ne statusSe }">readonly="readonly" </c:if> value="${tf.applyName}"/></td>
			     		
			     	</tr>
			     	<tr>
			     		<td align="center" >申请单位</td>
			     		<td align="left" ><input  class="input" name="applyUnit" <c:if test="${'1' ne statusSe }">readonly="readonly" </c:if> value="${tf.applyUnit }"/></td>
			     		<td align="center" >调卷类型</td>
			     		<td align="left" >
			     			<c:if test="${'1' ne statusSe }">
			     				<input  class="input" name="transferType" readonly="readonly" value="${tf.transferType}"/>
			     			</c:if>
			     			<c:if test="${'1' eq statusSe }">
			     				<select class="select" id="transferType" name="transferType">
				     				<option value="档案" <c:if test="${'档案' eq tf.transferType}">selected="selected"</c:if>>档案</option>
				     				<option value="资料" <c:if test="${'资料' eq tf.transferType}">selected="selected"</c:if>>资料</option>
				     				<option value="现行文件" <c:if test="${'现行文件' eq tf.transferType}">selected="selected"</c:if>>现行文件</option>
			     				</select> 
			     			</c:if>
			     		</td>
			     	</tr>
			     	<c:if test="${ '1' ne statusSe}">
			     		<tr>
				     		<td align="center" >归还人</td>
				     		<td align="left" ><input  class="input" name="returnPeople" <c:if test="${'3' eq statusSe }">readonly="readonly" </c:if> value="${tf.returnPeople}"/></td>
				     		<td align="center" >归还时间</td>
				     		<td align="left" ><input id="returnTime"  class="input" name="returnTime" readonly="readonly" value="${tf.returnTime}"/></td>
				     	</tr>
				     	<tr>
				     		<td align="center" >接收人</td>
				     		<td align="left" ><input  class="input" name="getPeople" <c:if test="${'3' eq statusSe }">readonly="readonly" </c:if> value="${tf.getPeople }"/></td>
				     	</tr>
			     	
			     	</c:if>
				</table>
			</form>
		</div>
		<div style="background-color: aliceblue;height: 40px;">
			<c:if test="${'1' eq statusSe }">
				<a class="wf-btn-primary save-btn" href="javascript:" onclick="searchDj('${tf.id }')" >检索</a>
				<a class="wf-btn-primary save-btn" href="javascript:" onclick="deleteDJK()" >删除</a>
			</c:if>
		</div>
		
		<div class="table" style="overflow: auto;">
			<div>
			    <table cellspacing="0" cellpadding="0">
			  
			    	<tr class="fr_tr">	
			        	<td></td>
			        	<td>标题</td>
			        	<td>结构路径</td>
			        	
			        </tr>  
			        <c:forEach items="${tslist}" var="data" varStatus="status3">
			    		<tr>
			    			<td align="center" >
								<input type="checkbox" name="selid" id="${data.id}"  value="${data.id}"  >
							</td>
							<td>${data.title}</td>
							<td>${data.esPath}</td>
			    		</tr>
			    	</c:forEach>
			    </table>
			</div>
	</div>
		<div  class="main"></div>
	</div>
</body>
<script type="text/javascript">
Date.prototype.Format = function(fmt) { //author: meizz   
    var o = {   
      "M+" : this.getMonth()+1,                 //月份   
      "d+" : this.getDate(),                    //日   
      "h+" : this.getHours(),                   //小时   
      "m+" : this.getMinutes(),                 //分   
      "s+" : this.getSeconds(),                 //秒   
      "q+" : Math.floor((this.getMonth()+3)/3), //季度   
      "S"  : this.getMilliseconds()             //毫秒   
    };   
    if(/(y+)/.test(fmt))   
      fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
    for(var k in o)   
      if(new RegExp("("+ k +")").test(fmt))   
    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
    return fmt;   
}

function setReturnTime() {
	var dom = $("#returnTime");
	if(dom.length > 0) {
		var domVal = $("#returnTime").val();
		if(isEmpty(domVal)) {
			var now = new Date().Format("yyyy-MM-dd"); 
			$("#returnTime").val(now);
		}
	}
}

$(document).ready(function (){
    $(".tw-layout").css("height",$(window).height());
    setReturnTime();
});
function closelayer(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}
	
function searchDj(djdId){
	var url = "${ctx}/model_toSearchJsp.do?djdId="+djdId;
		layer.open({
			title:"调卷检索",
		    type: 2,
		    content: url,
		    area: ['100%', '100%'],
           cancel: function(){
                //右上角关闭回调
                window.location.reload();
           }
		});
}
function deleteDJK(){
	var objs = document.getElementsByTagName('input');
	var ids = '';
	for(var i=0; i<objs.length; i++) {
	   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
		  ids += "'"+objs[i].value+"',";
	   }
	}
	if(ids==""){
		alert("请选择一条数据");
		return;
	}
	ids = ids.substring(0, ids.length-1);
	if(confirm('确定删除所选数据吗')){
		$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"text",//返回值类型
		        data:{"ids":ids},
		        url:"${ctx}/using_deleteDJK.do",
		        success:function(text){
		        	 window.location.reload();
		        }
		    })
	}
}

function updateTransferForm(type){
	var data = $('#itemform').serialize();
	data = data + "&oprateType="+type;
	$.ajax({
        async:true,//是否异步
        type:"POST",//请求类型post\get
        cache:false,//是否使用缓存
        dataType:"text",//返回值类型
        data: data,
        url:"${ctx}/using_updateTransferForm.do",
        success:function(result){
    		if("" == result){
        		alert("保存失败");
        	}else{
        		alert("保存成功");
        		closelayer();
        	}
        }
    });
}
</script>
<%@ include file="/common/function.jsp"%>
</html>
