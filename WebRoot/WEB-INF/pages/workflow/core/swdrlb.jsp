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
	<form name="doFileList" id="doFileList" action="${ctx }/table_getSwdrlbList.do" method="post">
	<div class="panelBar"> 
		<ul class="toolBar">
				<li><a class="edit" href="javascript:xinzeng()" ><span>新增</span></a></li>
		</ul>
	</div>
	</form>
</div>
        <div class="pageContent" id="pageContent">
		    <form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getSwdrlbList.do" >
		    	<div id="w_list_print">
					<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
						<thead>
							<tr>
								<td width="5%" style="font-weight:bold;text-align:center;">序号</td>
								<td width="15%" style="font-weight:bold;text-align:center;">来文标题</td>
								<td width="15%" style="font-weight:bold;text-align:center;">来文单位</td>
								<td width="15%" style="font-weight:bold;text-align:center;">印发单位</td>
								<td width="10%" style="font-weight:bold;text-align:center;">来文号</td>
								<td width="10%" style="font-weight:bold;text-align:center;">发文时间</td>
								<td width="10%" style="font-weight:bold;text-align:center;">收文时间</td>
								<td width="10%" style="font-weight:bold;text-align:center;">份数</td>
								<td width="15%" style="font-weight:bold;text-align:center;">公文类型</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="doFile" varStatus="status">
								<tr > 
									<td style="text-align:center;">${(selectIndex-1)*10+status.count}</td>
									<td style="text-align:left;" >${doFile.lwbt}</td>
									<td style="text-align:left;">${doFile.lwdw}</td>
									<td style="text-;">${doFile.yfdw}</td>
									<td style="text-align:left;">${doFile.lwh}</td>
									<td style="text-align:center;">${doFile.fwsj}</td>
									<td style="text-align:center;">${doFile.swsj}</td>
									<td style="text-align:center;">${doFile.fs}</td>
									<td style="text-align:center;">${doFile.gwlx}</td>
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

function xinzeng(){
	window.location.href='${ctx}/table_getSwdrList1.do';
}

 //分页相关操作代码
 window.onload=function(){ 
	 	var wfTitle = document.getElementById('wfTitle');
	 	var itemName = document.getElementById('itemName');
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getSwdrlbList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('doFileList');									//提交的表单,必须修改!!!*******************
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
<script>
	$('#pageContent').height($(window).height()-80);
</script>
</html>
