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
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="pendlist"  id="pendlist" action="${ctx }/employeeLeader_getEmployeeList.do" method="post">
 	        <label class="wf-form-label" for="">水印码：</label>
 	        <c:if test="${watermark eq null || watermark eq '' }">
 	        	<input type="text"  style="width: 400px;"  class="wf-form-text"  id="watermark" name="watermark" value="MTU5NjI3NTQ4MTYqMjAxNzA5MDQxMQ==" placeholder="请输入水印码查询">
 	        </c:if>
 	       	<c:if test="${watermark != null && watermark != '' }">
                <input type="text"  style="width: 400px;"  class="wf-form-text"  id="watermark" name="watermark" value="${watermark}" placeholder="请输入水印码查询">
			</c:if>
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/employeeLeader_getEmployeeList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
                        <th align="center" width="5%">序号</th>
						<th align="center" width="20%">登录名</th>
						<th align="center" width="20%">姓名</th>
					<c:if test="${time!=null &&  time!=''}">
						<th align="center" width="20%">时间</th>
					</c:if>
						<th ></th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td align="center">${item.employeeLoginname}</td>
						<td align="center">${item.employeeName}</td>
					<c:if test="${time!=null &&  time!=''}">
						<td align="center">${time}</td>
					</c:if>
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
			skipUrl="<%=request.getContextPath()%>"+"/employeeLeader_getEmployeeList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('pendlist');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
	</script>
</html>