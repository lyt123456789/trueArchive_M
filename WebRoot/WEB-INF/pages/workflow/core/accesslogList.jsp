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
			height:46px;
		    vertical-align: middle;

		}
		.new-htable tr td{
		text-align:left;
			color:#333333;
			font-size:15px;
			font-weight:normal;
			height:46px;
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
			width:133px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
			display:none;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="accessLogList"  id="accessLogList" action="${ctx}/tableExtend_getAccessLogList.do" method="post" style="display:inline-block;">
				<label class="wf-form-label" for="">&nbsp;&nbsp;&nbsp;&nbsp;方法名：</label>
                <input type="text" class="wf-form-text" id="methodName" name="methodName" style="width: 90px" value="${mehtodName}" placeholder="输入方法名">
                <label class="wf-form-label" for="">&nbsp;&nbsp;&nbsp;&nbsp;用户名：</label>
                <input type="text" class="wf-form-text" id="userName" name="userName" style="width: 90px" value="${userName}" placeholder="输入用户名">
	            <button class="wf-btn-primary" type="submit" onclick="checkForm();">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
			</form>
           
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/tableExtend_getAccessLogList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
                        <th align="center" width="4%">序号</th>
						<th align="center" width="15%">方法名</th>
						<th align="center" width="15%">用户名</th>
						<th align="center" width="15%">开始时间</th>
						<th align="center" width="18%">方法执行时长(单位:ms)</th>
						<th></th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td title="${item.methodName}">${item.methodName}</td>
						<td>${item.userName}</td>
						<td>${item.accessDate}</td>
						<td>${item.accessTime}</td>
						<td></td>
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
			skipUrl="<%=request.getContextPath()%>"+"/tableExtend_getAccessLogList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('accessLogList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
	</script>
	<script type="text/javascript">
		function checkForm(){
			document.getElementById('accessLogList').submit();
		}
	</script>
</html>