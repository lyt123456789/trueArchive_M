<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>中威科技工作流表单系统</title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${curl}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	 <link href="${curl}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>		
<body>
 <%-- <div class="pageHeader" >
	<form name="pendingList"  action="${curl }/table_getPendingList.do" method="post">
	<div class="searchBar" >
		<table class="searchContent">
			<tr> 
				<td>
					标题：<input type="text" name="wfTitle" value="${wfTitle}"/>
				</td> 
				<td>
					事项名称：<input type="text" name="itemName" value="${itemName}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="itemid" value="${itemid }">
	</div>
	</form>
</div>
 --%>
<div class="pageContent">
	
<form id="pendingList" method="POST" name="pendingList" action="${curl }/meeting_getMyRegisterList.do" >
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="8%" align="center">序号</th>
				<th width="20%" align="center">请假人</th>
				<th width="20%" align="center">请假原因</th>
				<th width="20%" align="center">审批结果</th>
				<th width="20%" align="center">查看请假情况</th>
				<th width="12%" align="center" >状态</th>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="item" items="${list}" varStatus="status"> 
			<tr >
				<td align="center">${(selectIndex-1)*pageSize+status.count }</td>
				<%-- <td align="left">${item.item_name }</td> --%>
				<td align="left" title="${item.QJR }"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item.QJR }</span></td>
				<td align="left" title="${item.QJYY }"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item.QJYY }</span></td>
				<td align="left" title="${item.SPJG }"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item.SPJG }</span></td>
				<td align="center" title="${item.process_title }" ><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"><a href="#" onclick="openForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}');">查看</a></span></td>
				<td align="center"><c:if test="${item.status == '0'}"><font color="red">办理中</font></c:if><c:if test="${item.status == '1'}"><font color="red">已办结</font></c:if></td>
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
	 var ret = window.showModalDialog('${curl}/table_openOverForm.do?processId='+processId+'&isDb=true&itemId='+itemId+'&formId='+formId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	 if(ret == "refresh"){
        window.location.reload();
 	 }
 }
 
 //分页相关操作代码
 window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getPendingList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('pendingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	} 
$("table.list", document).cssTable();

function hyqj(meetingName,meetingInsId){
	location.href = "${curl}/table_openFirstForm.do?workflowid=1915421B-9DD2-4734-804B-329F49046EEE&itemid=F01CD3EB-9754-4174-A0DF-D78A3BEF0C54&meetingName='"+meetingName+"'&meetingInsId='"+meetingInsId+"'";
}

function qjqk(){
	location.href = "${curl}/meeting_getHyqj.do?instanceId=${instanceId}";
}

</script>
<script type="text/javascript" src="${curl }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${curl }/widgets/common/js/god_paging.js" defer="defer"></script>
</html>