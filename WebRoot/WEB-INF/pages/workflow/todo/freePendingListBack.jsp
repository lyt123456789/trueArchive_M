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
<body>
 <div class="pageHeader" >
	<form name="freePendingList" id="freePendingList" action="${ctx }/table_getFreePendingList.do" method="post">
	<input type="hidden" name="itemid" id="itemid" value="${itemid}"/>
					<input type="hidden" name="status" id="status" value="2"/>
	<div class="searchBar" >
		<table  class="searchContent" >
			<tr>
			<td valign="top" style="text-align: left;">
	 					事项:
	 					&nbsp;
	 					<select id="sel_tagTable" onchange="choose()" >
	 						<option value="">请选择...</option>
	 						<c:forEach var="d" items="${itemList}">
	 							<option value="${d.id}" <c:if test="${d.id==itemid}">selected="selected"</c:if>>${d.vc_sxmc}</option>
	 						</c:forEach>
	 					</select>
	 					&nbsp;
	 				</td>
	 				</tr>
	 				<tr>
				<c:forEach var="condition" items="${conditions}" varStatus="status"> 
			
				<td align="right">
					${condition[1]}:</td><td align="left"><input type="text" name="${condition[0]}.${condition[2]}" value="${condition[3]}"/>
				</td> 
				<c:if test="${ status.index %4 == 3}"></tr><tr></c:if>
				</c:forEach>
				<c:if test="${conditions != null}">
				<td rowspan="2" align="center">
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
				</c:if>
			</tr>
		</table>
	</div>
	</form>
</div>
<div id="pageContent" class="pageContent"  style="overflow:auto;">
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getFreePendingList.do" >
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
			<c:if test="${results != null}"><th width="5%" align="center">序号</th></c:if>
			
			<c:forEach var="result" items="${results}" varStatus="status"> 
				<th align="center">
					${result[1]}
				</th> 
			</c:forEach>
			</tr>
		</thead>	
		<tbody>
		
		<c:forEach var="item" items="${list}" varStatus="status"> 
		<tr>
		<td align="center">${(selectIndex-1)*pageSize+status.count}</td>
		<%
		    int fieldCount = Integer.parseInt(request.getAttribute("fieldCount") == null ?"0":request.getAttribute("fieldCount").toString() );
				for(int i = 0; i < fieldCount  ; i++){
			%>
			<c:set var="step" value="<%=i%>"></c:set>
			<c:if test="${step == 0}">
			<td>
			<c:choose>
								<c:when test="${item[fieldCount] != '2'}">
									<span style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
									<c:choose>
									<c:when test="${item[fieldCount+4]!='1' ||item[fieldCount+5] == '1'}">
											<span style="width:${1024/(fieldCount)}px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"><a href="#" onclick="openForm('${item[fieldCount]}','${item[fieldCount+1]}','${item[fieldCount+2]}');">	${item[step]}</a></span>
									</c:when>
									<c:otherwise>
									<c:if test="${item[fieldCount+4]=='1'}">
											<span style="width:${1024/(fieldCount)}px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"><a href="${ctx}/table_openPendingForm.do?processId=${item[fieldCount]}&isDb=true&itemId=${item[fieldCount+1] }&formId=${item[fieldCount+2]}&isCy=''">${item[step]}</a></span>
										</c:if>
									</c:otherwise>
									</c:choose>		
									</span>
								</c:when>
								<c:when test="${item[fieldCount+3] == '2' || (item[fieldCount+4]=='1' && item[fieldCount+5] == '')}">
									<span style="width:${1024/(fieldCount)}px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
										<a href="${ctx}/table_openPendingForm.do?processId=${item[fieldCount]}&isDb=true&itemId=${item[fieldCount+1]}&formId=${item[fieldCount+2]}&isCy=''">${item[step]}</a>
									</span>
								</c:when>
							</c:choose>
							</td>
			</c:if>
			<c:if test="${step != 0}">
				<td align="center"><span style="width:${1024/(fieldCount)}px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item[step]}</span></td>
			</c:if>
			<% }%>
				</tr>
		</c:forEach>
		</tbody>
		
	</table>
	</div>
	</form>
</div>
<div class="formBar pa" style="bottom:0px;width:100%;">  
 <div id="div_god_paging" class="cbo pl5 pr5"></div> 
</div> 
</body>
<%@ include file="/common/function.jsp"%> 
<script>
function choose(){
	
	var obj= document.getElementById('sel_tagTable');
	var index=obj.selectedIndex; 
	var val = obj.options[index].value;
	document.getElementById('itemid').val = val;
	// 动态生成查询 条件 和结构
	window.location.href="${ctx}/table_getFreePendingList.do?itemid="+val+"&status=1&a="+Math.random();
}

 //分页相关操作代码
 window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getFreePendingList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('freePendingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
 		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改 
 		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	 
	 } 
$("table.list", document).cssTable();
function openForm(processId,itemId,formId,isCanPush){
	 var ret = window.showModalDialog('${ctx}/table_openPendingForm.do?processId='+processId+'&isDb=true&itemId='+itemId+'&formId='+formId+'&isCanPush='+isCanPush+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	 if(ret == "undefined" || typeof(ret) == "undefined"){
       	//window.location.reload();
	 }else if(ret == "refresh"){
		 window.location.href="${ctx}/table_getPendingList.do?itemid=${itemid}";
	 }
}


</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script>
		$('#pageContent').height($(window).height()-110);
</script>
</html>