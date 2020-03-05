<!DOCTYPE HTML>
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
</head>		
<body style="overflow-x: hidden; overflow-y: auto;">
    <div class="wf-layout">
		<form id="myform" method="POST" name="myform" action="${ctx }/freeSet_getTableData.do" >
		<input id="conName" name="conName" type="hidden" value="${conName}">
	   	<input id="colsName" name="colsName" type="hidden" value="${colsName}">
	   	<input id="conEnName" name="conEnName" type="hidden" value="${conEnName}">
	   	<input id="conditionValue" name="conditionValue" type="hidden" value="${conditionValue}">
		<div class="wf-list-top">
			<div class="wf-search-bar" id="conTr">
					<c:if test="${preview ne 'preview'}">
	 	       		<label class="wf-form-label" for="">
	 	       		<select class="wf-form-select"  id="itemId" name="itemId" onchange="change()">
						<c:forEach items="${itemList}" var="item">
							<option <c:if test="${item.id eq itemId}">selected</c:if> value="${item.id}">${item.vc_sxmc}</option>
						</c:forEach>
					</select>
	 	       		</label>
                	<select  class="wf-form-select"  id="type" name="type" onchange="change()">
						<option <c:if test="${type eq 'daiban'}">selected</c:if> value="daiban">待办</option>
						<option <c:if test="${type eq 'yiban'}">selected</c:if> value="yiban">已办</option>
						<option <c:if test="${type eq 'yibanjie'}">selected</c:if> value="yibanjie">已办结</option>
					</select>
		            </c:if>
		            <button class="wf-btn-primary"  onclick="getTableData();">
		                <i class="wf-icon-search"></i> 搜索
		            </button>
		            <c:if test="${preview eq 'preview'}">
	                <div class="btngroup fl ml5">
	                    <div class="btns bluebtn search-icon">
	                        <div class="btn-l"></div>
	                        <div class="btn-m" onclick="setWidth();">
	                            <i></i>设定宽度
	                        </div>
	                        <div class="btn-r"></div>
	                    </div>
	                </div>				
	           		</c:if>
			</div>
		</div>
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
			    	  	<tr id="firstTr">				
						</tr>
			    	</thead>
			    	<tbody>
			    	<c:forEach items="${list}" var="object" varStatus="STATUS">
						<tr class="cl">
							<td>${STATUS.count}</td>
							<c:forEach items="${object}" var="obj" varStatus="status">
								<c:choose>
									<c:when test="${ status.count>3}">
										<td style="display:none">${obj}</td>
									</c:when>
									<c:otherwise>
										<td>${obj}</td>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</c:forEach>
					</tbody>
				</table>
		</div>
		<div class="wf-list-ft" id="pagingWrap">
		</div>
		</form>
	</div>
<script type="text/javascript">
$(function(){
	var colsName = $("#colsName").val();
	var conName = $("#conName").val();
	var conEnName = $("#conEnName").val();
	var conditionValue = $("#conditionValue").val();
	var chtml = '';
	if(conName!=''){	
		conName = conName.split(",");
		conEnName = conEnName.split(",");
		conditionValue = conditionValue.split(",");
		for(i=0;i<conName.length;i++){
			chtml = chtml + '<label class="wf-form-label" for="">'+conName[i]+':</label><input type="text" class="wf-form-text" id="'+conEnName[i]+'" name="'+conEnName[i]+'" value="'+conditionValue[i]+'" type="text" placeholder="输入关键字" />';
			chtml = chtml.replace("undefined", "");
		}
	}
	 $("#conTr").prepend(chtml.replace("undefined", ""));
	var thtml = '<th>序号</th>';
	if(colsName!=''){
		colsName = colsName.split(",");
		for(i=0;i<colsName.length;i++){
			thtml = thtml + '<th>'+colsName[i]+'</th>';
		}
	}
	 $("#firstTr").prepend(thtml);
});

function setWidth(){
	var  th = $("th").first();
	var width = '';
	var trWidth = $("#firstTr").width();
	while(th.width()!=null){
		width = width + th.text()+":"+(th.width()*100/trWidth).toFixed(1)+",";
		th = th.next();
	}
	if(width!='')
		width = width.substring(0,width.length-1);
	
	var itemId = '${itemId}';
	var type = '${type}';
	$.ajax({   
		url : '${ctx }/freeSet_setWidth.do?itemId='+itemId+'&type='+type,
		type : 'POST',   
		cache : false,
		async : false,
		error : function() {
			alert('宽度设置错误！');
		},
		data : {
			'type':type,
			'width':width
		},    
		success : function() {  
			alert('宽度设置成功！');
		}
	});
}

function getTableData(){
	var itemId = $("#itemId").val();
	var type = $("#type").val();
	$.ajax({   
		url : '${ctx }/freeSet_checkSql.do',
		type : 'POST',   
		cache : false,
		async : false,
		error : function() {
			alert('Sql语句错误，请确认后提交！');
		},
		data : {
			'itemId':itemId,
			'type':type
		},    
		success : function() {  
			myform.submit();
		}
	});
	
}

function change(){
	var itemId = $("#itemId").val();
	var type = $("#type").val();
	window.location.href="${ctx}/freeSet_preview.do?itemId="+itemId+"&type="+type;
}
	
function openForm(processId,itemId,formId,favourite){
	if(processId!=''){
		 var ret = window.showModalDialog('${ctx}/table_openOverForm.do?favourite='+favourite+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
		 if(ret == "refresh"){
	      	window.location.reload();
		 }
	}
}


</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript">
/**
 * 重定向：检索办件处于的步骤信息
 */
function redirectPending(instanceId){
	window.location.href="${ctx}/table_getRedirectDetail.do?instanceId="+instanceId;
}
</script>

<script type="text/javascript">
 $(document).ready(function(){
  $(".zlgx-con tr").mousemove(function(){
   $(this).css("background","#e8eff9");
  });
  
  $(".zlgx-con tr").mouseout(function(){
   $(this).css("background","none");
  }); 
 }); 
 
$(".cl").click(function(){
	var processId = $(this).children(1).html();
	var itemId = $(this).children(2).html();
	var formId = $(this).children(3).html();
	openForm(processId,itemId,formId);
});

</script>
</body>
</html>