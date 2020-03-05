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
	 <script type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
</head>		
<body>
 <div class="pageHeader" >
	<form name="freeOverList" id="freeOverList" action="${ctx }/table_getFreeOverList.do" method="post">
	<input type="hidden" name="itemid" id="itemid" value="${itemid}"/>
	<input type="hidden" name="type" id="type" value="${type}"/>
					<input type="hidden" name="status" id="status" value="2"/>
		<div class="searchBar" >
		<table  class="searchContent" >
	 				<tr>
				<c:forEach var="condition" items="${conditions}" varStatus="status"> 
			
				<td align="right">
					${condition[1]}:</td>
					<c:if test="${condition[4] == 'String'}"><td align="left"><input type="text" name="${condition[0]}.${condition[2]}" value="${condition[3]}" maxlength="50"/></td></c:if> 
					<c:if test="${condition[4] == 'Date'}"><td align="left" colspan="2"><input id="${condition[0]}.${condition[2]}1" type="text" name="${condition[0]}.${condition[2]}" value="${condition[3]}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="wdate" readonly="readonly" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'${condition[0]}.${condition[2]}2\')}'})"/>-<input id="${condition[0]}.${condition[2]}2" type="text" name="${condition[0]}.${condition[2]}" value="${condition[5]}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="wdate" readonly="readonly" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'${condition[0]}.${condition[2]}1\')}'})"/></td></c:if>
					<c:if test="${condition[4] == 'select'}"><td align="left">
					<select id="${condition[0]}.${condition[2]}" name="${condition[0]}.${condition[2]}">
	 					<option value=""></option>
	 					<c:forEach var="d" items='${condition[6]}'>
	 						<option value="${d[1]}" <c:if test="${condition[3]==d[1]}">selected="selected"</c:if>>${d[1]}</option>
	 					</c:forEach> 
	 				</select>
					</td></c:if> 
					<c:if test="${ status.index %4 == 3}"></tr><tr></c:if>
				</c:forEach>
				<c:if test="${conditions != null}">
				<td colspan="2" align="center">
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
				</c:if>
			</tr>
		</table>
	</div>
	</form>
</div>
<div id="pageContent" class="pageContent"  style="overflow:auto;">
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getFreeOverList.do" >
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
				for(int i = 0; i < fieldCount ; i++){
			%>
			<c:set var="step" value="<%=i%>"></c:set>
				<c:if test="${step == 0}">
			<td>
				<span style="width:${1024/(fieldCount)}px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"><a href="#" onclick="openForm('${item[fieldCount]}','${item[fieldCount+1]}','${item[fieldCount+2]}');">${item[step]}</a></span>
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
function openForm(processId,itemId,formId){
	 var ret = window.showModalDialog('${ctx}/table_openOverForm.do?processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	 if(ret == "refresh"){
      window.location.reload();
	 }
}
function choose(){
	
	var obj= document.getElementById('sel_tagTable');
	var index=obj.selectedIndex; 
	var val = obj.options[index].value;
	document.getElementById('itemid').val = val;
	// 动态生成查询 条件 和结构
	window.location.href="${ctx}/table_getFreeOverList.do?itemid="+val+"&status=1&a="+Math.random();
}

 //分页相关操作代码
 window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getFreeOverList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('freeOverList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
 		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改 
 		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	 
	 } ;
 if($("table.list").length>0){
		$("table.list", document).cssTable();
	}

</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script>
$('#pageContent').height($(window).height()-110);
</script>
</html>