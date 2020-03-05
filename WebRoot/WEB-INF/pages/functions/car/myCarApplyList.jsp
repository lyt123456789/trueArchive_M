<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>中威科技工作流表单系统</title>
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
	<form name="overList" id="overList" action="${ctx }/carwf_getMyApplyList.do?itemid=${itemid}" method="post">
	<div class="searchBar">
	<input type="hidden" id="itemid" name="itemid" value="${itemid}"/>
	<input type="hidden" id="workflowid" name="workflowid" value="${workflowid}"/>
			<div  align="right"  style="border-top-style: none;height: 30px" >
			<span class="bread">申请时段：</span>
			<input type="text" id="searchDateFrom" name="searchDateFrom" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="wdate" onkeydown="return false;" value="${searchDateFrom}"/>
			~
			<input type="text" id="searchDateTo" name="searchDateTo" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="wdate" onkeydown="return false;"  value="${searchDateTo}"/>
			<!-- 0:未办理 1:办理中 2:已办结 3:已作废 -->
			<span class="bread">状态：</span>
			<select name="status" id="status">
							<option value="" <c:if test="${'' eq status}">selected="selected"</c:if>>全部</option>
							<option value="0" <c:if test="${'0' eq status}">selected="selected"</c:if>>办理中</option>
							<option value="1" <c:if test="${'1' eq status}">selected="selected"</c:if>>已办结</option>
						</select>
		    
			  <input type="submit" name="button3" id="button3" value="查询"  mice-btn="icon-search"/>&nbsp;&nbsp;
			</div>
			</div>
	</form>
</div>

 <div style="background: none repeat scroll 0 0 #F5F5F5;border: 1px solid #C5C5C5;height: 25px;line-height: 25px;">
			<a href="${ctx}/table_openFirstForm.do?workflowid=${workflowid}&itemid=${itemid}" class="add"><span style="line-height: 25px;margin-left: 20px;padding-left: 20px;">申请出车</span></a>
		</div>
<div class="pageContent" style="overflow:scroll;">  
	
<form id="thisForm" method="POST" name="thisForm" >
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116" >
		<thead>
			<tr>
				<th width="3%" align="center">序号</th>
				<th width="15%" align="center">申请车牌号</th>
				<th width="17%" align="center">申请开始时间</th>
				<th width="10%" align="center">申请结束时间</th>  
				<th width="15%" align="center">申请原因</th>
				<th width="10%" align="center" >状态</th>
				<th width="10%" align="center" >操作</th>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="item" items="${list}" varStatus="status"> 
			<tr target="sid_user" rel="1">
				<td align="center">${(selectIndex-1)*pageSize+status.count }</td>
				<td align="left" title="${item.carnumber }"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"><a href="#" onclick="openForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}');">${item.applystartdate }-${item.carnumber }</a></span></td>
				<td align="center">${item.applystartdate }</td>
				<td align="center">${item.applyenddate }</td>
				<td align="left" title="${item.applyreason }">${item.applyreason }</td>
				<td align="center"><c:if test="${item.status == '0'}"><font color="red">办理中</font></c:if><c:if test="${item.status == '1'}"><font color="red">已办结</font></c:if></td>
				<td align="center"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"><a href="#" onclick="openForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}');">查看</a></span></td> 
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
		skipUrl="<%=request.getContextPath()%>"+"/carwf_getMyApplyList.do";			//提交的url,必须修改!!!*******************
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