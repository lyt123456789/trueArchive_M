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
	 <style> 
		html {overflow-x: hidden; overflow-y: scroll;}
		.table_add td.blue_bg{ background:#F2FBFF; border-width:1px 1px 1px 1px; border-style:solid; border-color:#C0E0ED; line-height:20px;vertical-align:middle;padding-top:10px;*padding-bottom:10px; _padding-bottom:10px;}
		.table_add td.blue3_bg{ background:#F2FBFF; border-width:1px 1px 1px 0px; border-style:solid; border-color:#C0E0ED;line-height:20px; vertical-align:middle; padding-left:15px; padding-top:10px;*padding-bottom:10px; _padding-bottom:10px;}
		.icon-upload {
		    background-image: url(${curl }/widgets/imgs/up.png) !important;
		    height: 25px;
		}
		input{ vertical-align:middle; margin:0; padding:0}
		.file-box{ position:relative;width:430px;float:left;white-space:nowrap;}
		.txt{ height:22px; border:1px solid #cdcdcd; width:332px;}
		.btn{  width:60px;}
		.file{ position:absolute; top:0; right:0px; height:30px; filter:alpha(opacity:0);opacity: 0;width:420px } 
	</style>
</head>		
<body style="overflow:auto">
<%-- <div class="pageHeader" >
	<form name="pendingList"  action="${curl }/meeting_getHyListForQj.do" method="post">
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
</div> --%>
<div class="pageHeader" >
	<form action="${curl }/meeting_getHyListForQj.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<%-- 标题：<input type="text" name="wfTitle" value="${wfTitle}"/> --%>
					会议时间:&nbsp;
			<input type="text" id="searchDateFrom" name="searchDateFrom" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="wdate" onkeydown="return false;" value="${searchDateFrom}" />
		  &nbsp;至:&nbsp;
			<input type="text" id="searchDateTo" name="searchDateTo" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="wdate" onkeydown="return false;" value="${searchDateTo}" />
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
	
<form id="thisForm" method="POST" name="thisForm" action="${curl }/meeting_getHyListForQj.do" >
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="25%" align="center">会议名称</th>
				<th width="25%" align="center">会议时间</th>
				<th width="25%" align="center">会议地点</th>
				<th width="25%" align="center" title="操作">操作</th>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="item" items="${allhyList}" varStatus="status"> 
			<tr >
				<td align="left">${item.meetingname }</td>
				<td align="left">${item.newtime }</td>
				<td align="left">${item.arr }</td>
				<td align="center"><a href="javaScript:void(0)" onclick="selhy('${item.instanceid}','${item.meetingname}');" ><span>请假</span></a></td>
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
 
 //分页相关操作代码
 window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/meeting_getHyListForQj.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	}
$("table.list", document).cssTable();

var returnvalue ;
function selhy(instanceid,meetingname){
	alert("会议选择成功。");
	returnvalue = instanceid+"*"+meetingname;
}

function cdv_getvalues()
{
	if(returnvalue == 'undefined' ||returnvalue == null||returnvalue == 'null'){
		returnvalue = '';
	}
	return returnvalue;
	 
}

</script>
<script type="text/javascript" src="${curl }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${curl }/widgets/common/js/god_paging.js" defer="defer"></script>
<script type="text/javascript" src="${curl }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
</html>