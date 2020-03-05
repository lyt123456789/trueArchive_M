<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
	<style>
		.new-htable {
			margin-top:20px;
			width:96%;
			margin-left:3%;
		}
		.new-htable tr th{
			text-align:right;
			color:#333333;
			font-size:16px;
			font-weight:normal;
			height:40px;
		    vertical-align: middle;

		}
		.new-htable tr td{
			text-align:left;
			color:#333333;
			font-size:15px;
			font-weight:normal;
			height:40px;
		    vertical-align: middle;
		}
		.new-htable .tw-form-text{
			width:354px;
			text-indent:6px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.new-htable select{
			width:163px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.wf-form-date{
			width:115px!important;
			min-width: 100px!important;
			max-width: 120px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
			display:none;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
		.w-auto-10 {
			width: 9% !important;
			min-width: 9% !important;
		}
		
		.wf-search-bar .wf-form-label{
			margin-left: 0px;
		}
		
		.auto-date-width{
			width:120px!important;
		}
		.title{
			font-size: 20px;
		    position: absolute;
		    text-align: center;
		    background-color: white;
		    width: 100%;
		    height: 40px;
		    top: 2px;
		}	
	</style>
</head>
<body>
<div class="wf-layout">
	<%-- <span class="title">${wfTitle}</span> --%>
	<div class="wf-list-top">
		<!-- <div class="wf-search-bar" style="position: relative;">
		</div> -->
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div>
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/table_openOperateLogList.do?processId=${processId}" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    		<tr>
						<th width="30%" >姓名</th>
						<th >打开时间</th>
						<th >办理时间</th>
					</tr>
		    	</thead>		    	
		    	<c:forEach items="${logList}" var="log" varStatus="status">
			    	<tr>
						<td align="center" title="${log.userName}">${log.userName }</td>
						<td align="center">${fn:substring(log.openTime, 0, 16)}</td>
						<td align="center">${fn:substring(log.operateTime, 0, 16)}</td>
					</tr>
		    	</c:forEach>
			</table>
		</form>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/table_openOperateLogList.do?processId=${processId}";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
</script>
<script type="text/javascript">
	
</script>
<script>

</script>
</html>