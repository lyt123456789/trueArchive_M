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
		<form name="freeDofileList" id="freeDofileList" action="${ctx }/doArticlePost_getFreeDofileList.do" method="post">
		<input type="hidden" name="workflowId" id="workflowId" value="${workflowId}"/>
		<%-- <input type="hidden" name="itemid" id="itemid" value="${itemid}"/> --%>
		<input type="hidden" name="bjlx" id="bjlx" value="${bjlx}"/>
		<input type="hidden" name="status" id="status" value="${status}"/>
		<div class="wf-list-top">
			<div class="wf-search-bar">
	 	       		<label class="wf-form-label" for="">标题名称：</label>
	 	        	<input type="text" class="wf-form-text"  name="btfw" value="${btfw }" placeholder="输入关键字"/>
	 	        	<label class="wf-form-label" for="">发文号：</label>
	 	        	<input type="text" class="wf-form-text" name="fwh" value="${fwh}" placeholder="输入关键字"/>
	 	        	<label class="wf-form-label" for="">发送部门：</label>
	 	        	<input type="text" class="wf-form-text" name="fsbm" value="${fsbm}" placeholder="输入关键字"/>
	                <label class="wf-form-label" for="">发送时间：</label>
	                <input type="text" class="wf-form-text wf-form-date" id="fwbeginTime" name="fwbeginTime" value="${fwbeginTime}"  placeholder="输入日期" />
					    至            
					<input type="text" class="wf-form-text wf-form-date" id="fwendTime" name="fwendTime"  value="${fwendTime}"   placeholder="输入日期" /><br>
					<label class="wf-form-label" for="">事项名称：</label>
	                <select class="wf-form-select" id="itemid" name="itemid" style="margin-top: 5px">
	                	<option value=""></option>
	                	<c:forEach var="m" items='${myPendItems}'>
		 					<option value="${m.id}" <c:if test="${itemid ==m.id}">selected="selected"</c:if>>${m.vc_sxmc}</option>
		 				</c:forEach> 
	                </select>		            
		            <button class="wf-btn-primary" onclick="document.forms.freeDofileList.submit();">
		                <i class="wf-icon-search"></i> 搜索
		            </button>
			</div>
		</div>
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
			    	  	<tr>
							<th width="5%" align="center">序号</th>
							<th align="center">标题</th>
							<th width="10%" align="center">发文号</th>
							<th width="10%" align="center">发送时间</th>
							<th width="15%" align="center">主办部门</th>
							<th width="15%" align="center">抄送部门</th>
							<c:if test="${'1' eq isFiling }">
							<th width="5%" align="center" >操作</th> 
							</c:if>
						</tr>
			    	</thead>
			    	<c:forEach var="item" items="${list}" varStatus="status"> 
						<tr>
							<td align="center" width="5%" height=36>${(selectIndex-1)*pageSize+status.count}</td>
							<td align="center" style="text-align: left;" title="${item[0]}">
								<c:if test="${('1' eq item[12])}">
									<c:if test="${!('1' eq item[11])}"><span class="wf-badge-main">主</span></c:if>
					   			 	<c:if test="${('1' eq item[11])}"><span class="wf-badge-sub">子</span></c:if>
				   			 	</c:if>
								<a href="#" onclick="openForm('${item[8]}','${item[9]}','${item[10]}', '${item[0]}');">
								<c:choose>  
									<c:when test="${fn:length(item[0]) > 22}">  
										 <c:out value="${fn:substring(item[0], 0, 22)}..." />  
									</c:when>  
									<c:otherwise>  
										<c:out value=" ${item[0]}" />  
									</c:otherwise>  
								</c:choose>
								</a>
							</td>
							<td align="center" width="10%" title="${item[1]}">${item[1]}</td>
							<td align="center" width="10%" title="<fmt:formatDate value="${item[4]}" pattern="yyyy-MM-dd"></fmt:formatDate>"><fmt:formatDate value="${item[4]}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
							<td align="center" width="15%" title="${item[2]}">
							<c:choose>  
									<c:when test="${fn:length(item[2]) > 22}">  
										 <c:out value="${fn:substring(item[2], 0, 22)}..." />  
									</c:when>  
									<c:otherwise>  
										<c:out value=" ${item[2]}" />  
									</c:otherwise>  
								</c:choose>
							</td>
							<td align="center" width="15%" title="${item[3]}">
							<c:choose>  
									<c:when test="${fn:length(item[3]) > 22}">  
										 <c:out value="${fn:substring(item[3], 0, 22)}..." />  
									</c:when>  
									<c:otherwise>  
										<c:out value=" ${item[3]}" />  
									</c:otherwise>  
								</c:choose>
							</td>
							<c:if test="${'1' eq isFiling }">
							<td align="center" width="5%">
							<c:if test="${null == item[6] }">
							<a href="javascript:void(0)" onclick="filedDofile('${item[8]}','${item[9]}','${item[10]}')" class="icon icon-guidang" title="归档" >
									<i style="width:26px;"></i>	
								</a>
							</c:if>
							<c:if test="${null != item[6] }">
							     已归档
							</c:if>
							</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
		</div>
		<div class="wf-list-ft" id="pagingWrap">
		</div>
		</form>
	</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script>

function openForm(processId,itemId,formId, process_title){
	var url = '${ctx}/table_openOverForm.do?processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
	 openLayerTabs(processId,screen.width,screen.height,process_title,url);
	/*  var ret = window.showModalDialog('${ctx}/table_openOverForm.do?processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	 if(ret == "refresh"){
      window.location.reload();
	 } */
}
function choose(){
	var obj= document.getElementById('sel_tagTable');
	var index=obj.selectedIndex; 
	var val = obj.options[index].value;
	document.getElementById('itemid').val = val;
	// 动态生成查询 条件 和结构
	window.location.href="${ctx}/table_getFreeDofileList.do?itemid="+val+"&status=1&a="+Math.random();
}

window.onload=function(){ 
	//获得后台分页相关参数-必须
	skipUrl="<%=request.getContextPath()%>"+"/doArticlePost_getFreeDofileList.do";			//提交的url,必须修改!!!*******************
	submitForm=document.getElementById('freeDofileList');									//提交的表单,必须修改!!!*******************
	innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
	MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
	pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
	selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
	sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
	if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
};


function openLayerTabs(processId,width,height,title,url){
	   parent.topOpenLayerTabs(processId,1200,600,title,url);
}	

 </script>
 <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
 <script>
function filedDofile(processId,itemId,formId){
	if(confirm("确认归档？")){
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{'processId':processId,'itemId':itemId,'formId':formId},
	        url:"${ctx}/filing_filedDofile.do",
	        success:function(text){
	        	if(text=='false'){
	        		alert("后台归档失败，请稍后重试");
	        	}
	        	if(text=='true'){
	        		alert("归档成功");
	        		document.forms.freeDofileList.submit();
	        	}
	        },
	        error:function(){
	        	alert("ajax调用失败");
	        }
	    });
	}
}

function checkProcessId(processId){
	var processStr = "";
	$("[check_process]").each(function(index){
		processStr = processStr + ($(this).attr("check_process")+",");
	});
	if(processStr!=""){
		processStr = processStr.substring(0,processStr.length-1);
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{'processStr':processStr},
	        url:"${ctx}/filing_checkFiling.do",
	        success:function(text){
				if(text!=""){
					var processIds = new Array();
					processIds = text.split(",");
					for(var i=0;i<processIds.length;i++){
						$("[check_process='"+processIds[i]+"']").hide();
					}
				}
	        },
	        error:function(){
	        	alert("ajax调用失败");
	        }
	    });
	}
}
 
 </script>
 </html>