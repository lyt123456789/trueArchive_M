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
			<input type="hidden"  id = "id"   name = "id" value="${id }" />
			<table  class="tw-table "  >
				<tr >
					<td  id="left">
						<table class="table"  id="leftTable">
							     <c:forEach items="${left}" var="item" varStatus="status">
							     	<tr style="height:${item.vc_height * 40}px;" rowspan = "${item.vc_height }">
							     		<td align="center"  <c:if test="${item.vc_isNull eq '1' }">style="color:red;"</c:if> <c:if test="${item.vc_edit eq '0' }">style="color:green !important;"</c:if> >${item.name }<c:if test="${item.id ne '1' }" >：</c:if></td>
							     		<td align="left" >
							     			<c:if test="${item.id ne '1' }" >
							     			<c:choose>
							     				<c:when test="${item.vc_type eq 'input' }">
							     					<input  class="input" name = '${item.fielName }--'  value="${item.vc_val }" />
							     				</c:when>
							     				<c:when test="${item.vc_type eq 'select' }">
							     					<select  class="select" name = '${item.fielName }--' <c:if test="${item.name eq '其他目的' }">style="display:none;" id="qtmd"</c:if> <c:if test="${item.name eq '查档目的' }"> id="cdmd" onchange="showMD(this)"</c:if><c:if test="${item.name eq '查阅结果' }"> id="cyjg"</c:if>>
							     						 <option value=""></option>
							     						 <c:forEach items="${item.basicList}" var="data" varStatus="status">
							     							<option value="${data.feilName }"  <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if>>${data.dataName }</option>
							     						</c:forEach>
							     					</select>
							     				</c:when>
							     				<c:otherwise>
							     					<textarea  class="textarea" rows="${item.vc_height }" cols="${item.vc_weight }" name = '${item.fielName }--' >${item.vc_val }</textarea>
							     				</c:otherwise>
							     			</c:choose>
							     			</c:if>
							     		</td>
							     	</tr>
							</c:forEach>
						</table>
					</td>
					<td 	id="right">
						<table class="table"  id = "rightTable">
							     <c:forEach items="${right}" var="item" varStatus="status">
							     	<tr style="height:${item.vc_height * 40}px;" rowspan = "${item.vc_height }">
							     		<td align="center"  <c:if test="${item.vc_isNull eq '1' }">style="color:red;"</c:if> <c:if test="${item.vc_edit eq '0' }">style="color:green !important;"</c:if>>${item.name }<c:if test="${item.id ne '1' }" >：</c:if></td>
							     		<td align="left" >
							     		<c:if test="${item.id ne '1' }" >
							     			<c:choose>
							     				<c:when test="${item.vc_type eq 'input' }">
							     					<input  class="input" name = '${item.fielName }--'   value="${item.vc_val }" />
							     				</c:when>
							     				<c:when test="${item.vc_type eq 'select' }">
							     					<select  class="select" name = '${item.fielName }--'  <c:if test="${item.name eq '其他目的' }">style="display:none;" id="qtmd"</c:if> <c:if test="${item.name eq '查档目的' }"> id="cdmd" onchange="showMD(this)"</c:if><c:if test="${item.name eq '查阅结果' }"> id="cyjg"</c:if>>
							     						 <option value=""></option>
							     						 <c:forEach items="${item.basicList}" var="data" varStatus="status">
							     							<option value="${data.feilName }"  <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if> >${data.dataName }</option>
							     						</c:forEach>
							     					</select>
							     				</c:when>
							     				<c:otherwise>
							     					<textarea name = '${item.fielName }--'   class="textarea" rows="${item.vc_height }" cols="${item.vc_weight }">${item.vc_val }</textarea>
							     				</c:otherwise>
							     			</c:choose>
							     			</c:if>
							     		</td>
							     	</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan = '2' >
						<table class="table">
							  <c:forEach items="${mid}" var="item" varStatus="status">
							  	<tr  style="height:${item.vc_height * 40}px;" rowspan = "${item.vc_height }">
							  		<td width="18%" align="center"  <c:if test="${item.vc_isNull eq '1' }">style="color:red;"</c:if> <c:if test="${item.vc_edit eq '0' }">style="color:green !important;"</c:if>>${item.name }<c:if test="${item.id ne '1' }" >：</c:if></td>
									  	<c:choose>
									  		<c:when test="${item.vc_weight eq '2' }">
									  		<td colspan = '2'  align="left" >
									  				<c:choose>
										     				<c:when test="${item.vc_type eq 'input' }">
										     					<input  class="input" name = '${item.fielName }--'   value="${item.vc_val }" />
										     				</c:when>
										     				<c:when test="${item.vc_type eq 'select' }">
										     					<select  class="select" name = '${item.fielName }--'  <c:if test="${item.name eq '其他目的' }">style="display:none;" id="qtmd"</c:if> <c:if test="${item.name eq '查档目的' }"> id="cdmd" onchange="showMD(this)"</c:if><c:if test="${item.name eq '查阅结果' }"> id="cyjg"</c:if>>
										     						 <option value=""></option>
										     						 <c:forEach items="${item.basicList}" var="data" varStatus="status">
										     							<option value="${data.feilName }"  <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if> >${data.dataName }</option>
										     						</c:forEach>
										     					</select>
										     				</c:when>
										     				<c:otherwise>
										     					<textarea name = '${item.fielName }--'   class="textarea" rows="${item.vc_height }" cols="${item.vc_weight }">${item.vc_val }</textarea>
										     				</c:otherwise>
										     		</c:choose>
										     	</td>
										     	<td> </td>
									  		</c:when>
									  		<c:otherwise>
									  			<td colspan = '3'>
									  				<c:choose>
										     				<c:when test="${item.vc_type eq 'input' }">
										     					<input  class="input" name = '${item.fielName }--'  value="${item.vc_val }"  />
										     				</c:when>
										     				<c:when test="${item.vc_type eq 'select' }">
										     					<select  class="select" name = '${item.fielName }--'  <c:if test="${item.name eq '其他目的' }">style="display:none;" id="qtmd"</c:if> <c:if test="${item.name eq '查档目的' }"> id="cdmd" onchange="showMD(this)"</c:if><c:if test="${item.name eq '查阅结果' }"> id="cyjg"</c:if>>
										     						 <option value=""></option>
										     						 <c:forEach items="${item.basicList}" var="data" varStatus="status">
										     						<option value="${data.feilName }"  <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if> >${data.dataName }</option>
										     						</c:forEach>
										     					</select>
										     				</c:when>
										     				<c:otherwise>
										     					<textarea name = '${item.fielName }--'  class="textarea" rows="${item.vc_height }" cols="${item.vc_weight }">${item.vc_val }</textarea>
										     				</c:otherwise>
										     		</c:choose>
										     	</td>
									  		</c:otherwise>
									  	</c:choose>
							  	</tr>
						  </c:forEach>
					</table>
					</td>
				</tr>
				<tr>
					<td align="right">
						 <div class="">
							<a class="wf-btn cz-btn"  href="javascript:" onclick="restting()"> 重置
							</a>
						 </div>
					</td>
					<td align="left">
						<div class="">
							<a class="wf-btn-primary save-btn" href="javascript:" onclick="regist()" > 添加
							</a>
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
    if(document.getElementById("cdmd").value=="其他"){
		document.getElementById("qtmd").style.display="";
	}
});
	
	function regist(){
		debugger;
		var str = '';
		var count = 0;
		var btzd = "${btzd}";
		$("input[name$='--']").each( 
				function(){  
					str += $(this).attr("name")+$(this).val()+" ;";
					if(btzd.indexOf($(this).attr("name"))>-1&&$(this).val()==""){
						count++;
					}
				}  
		)
		$("select[name$='--']").each( 
				function(){  
					str += $(this).attr("name")+$(this).val()+" ;";
					if(btzd.indexOf($(this).attr("name"))>-1&&$(this).val()==""){
						count++;
					}
					}  
		)
		$("textarea[name$='--']").each( 
				function(){  
					str += $(this).attr("name")+$(this).val()+" ;";
					if(btzd.indexOf($(this).attr("name"))>-1&&$(this).val()==""){
						count++;
					}
					}  
		)
		var id = $("#id").val();
		if(count>0){
			alert("请将红色部分填写后提交！");
			return;
		}
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"str":str,"id":id,"zzcdFlag":"${zzcdFlag}"},
	        url:"${ctx}/using_addRegist.do",
	        success:function(result){
        		if("" == result){
	        		alert("保存失败");
	        	}else{
	        		alert("保存成功");
        			if(confirm('是否需要扫描附件')){
        				var url = '${ctx}/using_toScanJsp.do?formbh='+id;
        				layer.open({
        					title:"扫描附件",
        				    type: 2,
        				    content: url,
        				    area: ['90%', '90%'],
        		            cancel: function(){
        		                 //右上角关闭回调
        		                 if("${zzcdFlag}"=="1"){
        		                	 parent.changeSrc("${ctx}/model_toSearchJsp.do?zzcdFlag=${zzcdFlag}&jydId=${id}",0)
        		                	var index = parent.layer.getFrameIndex(window.name);
        							parent.layer.close(index);
        		                 }else{
        		                	 parent.location.href="${ctx}/model_toSearchJsp.do?jydId="+id;
        		                 }	
        		            }
        				});
        				
	        		}else{
	        			if("${zzcdFlag}"=="1"){
	        				parent.changeSrc("${ctx}/model_toSearchJsp.do?zzcdFlag=${zzcdFlag}&jydId=${id}",0)
	        				var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
	        			}else{
	        				parent.location.href="${ctx}/model_toSearchJsp.do?jydId="+id;
	        			}
	        		}
	        		
	        		//parent.location.reload();
	        	}
	        	
	        }
	    })
	}
	
	function restting(){
		window.location.reload();
	}
	
	function showMD(obj){
		if(obj.value=="其他"){
			document.getElementById("qtmd").style.display="";
		}else{
			document.getElementById("qtmd").value="";
			document.getElementById("qtmd").style.display="none";
		}
	}
</script>
<%@ include file="/common/function.jsp"%>
</html>
