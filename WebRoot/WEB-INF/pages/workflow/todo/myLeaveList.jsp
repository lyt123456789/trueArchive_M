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
<form id="pagerForm" method="post" action="w_list.html">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>
<div class="pageHeader" >
	<form name="overList" id="overList" action="${ctx }/table_getMyLeaveList.do?itemid=DC72CE1C-8B96-421D-A6EA-D7367F479894" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					标题：<input type="text" name="wfTitle" value="${wfTitle}"/>
				</td> 
				<td>
					事项类别：<input type="text" name="itemName" value="${itemName}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="itemid" value="${itemid }">
		<input type="hidden" name="statustype" value="${statustype }">
	</div>
	</form>
</div>
 
<div class="pageContent" style="overflow:scroll;">  
	
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getMyLeaveList.do?itemid=DC72CE1C-8B96-421D-A6EA-D7367F479894">
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116" >
		<thead>
			<tr>
				<th width="3%" align="center">序号
				<input type="hidden" name ="itemid" value="DC72CE1C-8B96-421D-A6EA-D7367F479894"/>
				</th>
				<th width="20%" align="center">标题</th>
				<th width="17%" align="center">事项类别</th>
				<th width="10%" align="center">流程发起人</th>  
				<th width="10%" align="center">发送人</th>
				<th width="10%" align="center" >接收时间</th>
				<th width="10%" align="center" >办理时间</th>
				<th width="7%" align="center" >期限</th>
				<th width="8%" align="center" >状态</th>
				<th width="15%" align="center" >提示</th>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="item" items="${list}" varStatus="status"> 
			<tr target="sid_user" rel="1">
				<td align="center">${(selectIndex-1)*10+status.count }</td>
				<td align="left" title="${item.process_title }"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"><a href="#" onclick="openForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}');"><c:choose>  
    											<c:when test="${fn:length(item.process_title) > 25}">  
        										<c:out value="${fn:substring(item.process_title, 0, 25)}.." />  
    											</c:when>  
   												<c:otherwise>  
      											<c:out value=" ${item.process_title}" />  
    											</c:otherwise>  
											</c:choose></a></span></td>
				<td align="left">${item.item_name }</td>
				<td align="center">${item.owner }</td>
				<td align="center">${item.employee_name }</td>
				<td align="center">${fn:substring(item.apply_time,0,16) }</td>
				<td align="center">${fn:substring(item.finish_time,0,16) }</td>
				<td align="center">${item.wfn_deadline }
					<c:choose>
						<c:when test="${item.wfn_deadlineunit=='0'}">工作日</c:when>
						<c:when test="${item.wfn_deadlineunit=='1'}">小时</c:when>
						<c:when test="${item.wfn_deadlineunit=='2'}">天</c:when>
						<c:when test="${item.wfn_deadlineunit=='3'}">周</c:when>
						<c:when test="${item.wfn_deadlineunit=='4'}">月</c:when>
						<c:when test="${item.wfn_deadlineunit=='5'}">年</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</td>
				<td align="center"><c:if test="${item.status == '0'}"><font color="red">办理中</font></c:if><c:if test="${item.status == '1'}"><font color="red">已办结</font></c:if></td>
				<td align="center" style="color: red">${item.remainTime }</td> 
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

 //分页相关操作代码
 window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getMyLeaveList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('overList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	}
$("table.list", document).cssTable();

</script>
<script language="javascript" type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
</html>