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
			<form name="downloadLogList"  id="downloadLogList" action="${ctx}/tableExtend_getDownloadLogList.do" method="post" style="display:inline-block;">
				<label class="wf-form-label" for="">&nbsp;&nbsp;&nbsp;&nbsp;下载人员：</label>
                <input type="text" class="wf-form-text" id="name" name="name" style="width: 90px" value="${name}" placeholder="请输入下载人员">
                <label class="wf-form-label" for="">&nbsp;&nbsp;&nbsp;&nbsp;办件标题：</label>
                <input type="text" class="wf-form-text" id="title" name="title" style="width: 90px" value="${title}" placeholder="请输入办件标题">
                <label class="wf-form-label" for="">&nbsp;&nbsp;&nbsp;&nbsp;下载时间：</label>
                <input type="text" class="wf-form-text wf-form-date" id="beginTime" name="beginTime" value="${beginTime}" readonly="readonly">
                	至
                <input type="text" class="wf-form-text wf-form-date" id="endTime" name="endTime" value="${endTime}" readonly="readonly">
	            <button class="wf-btn-primary" type="submit" onclick="checkForm();">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
			</form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/tableExtend_getDownloadLogList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
                        <th align="center" width="4%">序号</th>
						<th align="left">办件名称</th>
						<th align="center" width="15%">下载人员</th>
						<th align="center" width="15%">下载时间</th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td title="${item.fileTitle}">${item.fileTitle}</td>
						<td>${item.employeeName}</td>
						<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.downloadTime}"/> </td>
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
			skipUrl="<%=request.getContextPath()%>"+"/tableExtend_getDownloadLogList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('downloadLogList');									//提交的表单,必须修改!!!*******************
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
			document.getElementById('downloadLogList').submit();
		}
	</script>
</html>