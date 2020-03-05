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
	<script type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
</head>
    <body>
    <div class="pageHeader" >
	<form name="meetingOutList"  action="${ctx }/meetings_getMeetingOutList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					会议名称：<input type="text" name="name" value="${name}" id="name"/>
				</td>
				<td>
					会议开始时间：<input type="text" name="begtime" value="${begtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH时mm分'})" id="begtime"/>
				</td> 
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
	</div>
        <div class="pageContent">
		    <form id="thisForm" method="POST" name="thisForm" action="${ctx }/meetings_getMeetingOutList.do" >
		    	<div id="w_list_print">
					<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
						<thead>
							<tr>
								<td width="5%" style="font-weight:bold;text-align:center;">序号</td>
								<td width="30%" style="font-weight:bold;text-align:center;">会议名称</td>
								<td width="30%" style="font-weight:bold;text-align:center;">地点</td>
								<td width="20%" style="font-weight:bold;text-align:center;">会议时间</td>
								<td width="15%" style="font-weight:bold;text-align:center;">操作</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${meetingOutList}" var="meet" varStatus="status">
								<tr > 
									<td style="text-align:center;">${(selectIndex-1)*10+status.count}</td>
									<td style="text-align:left;">${meet.meetingname}</td>
									<td style="text-align:left;">${meet.arr}</td> 
									<td style="text-align:center;">${meet.newtime}</td>
									<td style="text-align:center;"><a href="#" onclick="viewname('${meet.instanceid}','${meet.meetingname}');";>查看名单</a></td> 
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
function viewname(instanceId,meetingname){
	window.location.href="${ctx}/meetings_getMeetingOutName.do?instanceId="+instanceId+"&meetingname="+meetingname;	
}
//分页相关操作代码
window.onload=function(){ 
	//获得后台分页相关参数-必须
	skipUrl="<%=request.getContextPath()%>"+"/meetings_getMeetingOutList.do";			//提交的url,必须修改!!!*******************
	submitForm=document.getElementById('meetingOutList');									//提交的表单,必须修改!!!*******************
	innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
	MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
	pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
	selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
	sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
	if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
}
$("table.list", document).cssTable();
</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
</html>
