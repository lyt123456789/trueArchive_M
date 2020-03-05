<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html style="height:100%">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
</head>
<body style="height:100%">
<div class="tw-layout">
	<div class="tw-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/business_getHandRoundShips.do" >
			<input type="hidden" name="instanceId" value="${instanceId}">
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    		<tr>
						<th align="center" width="5%">序号</th>
						<!-- <th width="30%">传阅人</th> -->
						<th width="10%">发送时间</th>
						<th width="15%">人员</th>
						<th width="10%">处理时间</th>
						<th width="15%">状态</th>
		        	</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    <tr>
					<td>${(selectIndex-1)*pageSize+status.count}</td>
					<%-- <td align="center">${item.senderName}</td> --%>
					<td align="center">${item.addTime}</td>
					<td align="center">${item.userName}</td>
					<td align="center">${fn:substring(item.readTime,0,16)}</td>
					<td align="center">
						<c:if test="${item.isRead eq '0'}">
							<font color="red">未阅</font>
						</c:if>
						<c:if test="${item.isRead eq '1'}">
							已阅
						</c:if>
					</td>
					
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
			skipUrl="<%=request.getContextPath()%>"+"/business_getHandRoundShips.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
	</script>
	<script>
		$(document).ready(function(){
			setTimeout(function(){
				var width = $(top.window).width()-125;
				var height = $(top.window).height()-200;
				var height1 = $(top.window).height()-275;
				$('.tw-layout').height(height);
				$('table').width(width);
				$('.wf-fixtable-scroller').css({width:''});
				$('.wf-fixtable-scroller').height(height1);
				console.log('234');
			},501);
		});
		
	</script>
</html>