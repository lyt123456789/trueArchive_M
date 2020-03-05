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
	    .tableBlock,.tableBlock2 {display:block!important;padding:5px 0px;}
	</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="searchForm"  id="searchForm" action="${ctx }/uNodePermission_showPermissionToEverBody.do" method="post">
	            <label class="wf-form-label" for="">姓名：</label>
	            <input type="text" class="wf-form-text" name="name" value="${name}" placeholder="输入关键字">
	            <label class="wf-form-label" for="">事项名称：</label>
	            <input type="text" class="wf-form-text" name="sxmc" value="${sxmc}" placeholder="输入关键字">
	            <label class="wf-form-label" for="">节点权限：</label>
	            <input type="text" class="wf-form-text" name="nodeName" value="${nodeName}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/uNodePermission_showPermissionToEverBody.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
			    	<tr>
			    		<th width="5%">序号</th>
				    	<th width="10%">姓名</th>
				    	<th width="20%">事项名称</th>
				    	<th>节点权限</th> 
			    	</tr>
		    	</thead>
		    	<c:forEach items="${ulist}" var="d"  varStatus="n">
			    	<tr target="sid_user" >
			    		<td align="center">
				    		${(selectIndex-1)*pageSize+n.count}
				    	</td>
				    	<td align="center">${d.name}</td>
			    		<td align="left">
				    			<c:forEach items="${d.itemSet}" var="i">
				    				<c:set var="iI" value="${fn:indexOf(i,')')}"/>
				    				<c:set var="iL" value="${fn:length(i)}"/>
									<div class="tableBlock"><a href="#" class="wf-btn-border-primary">${fn:substring(i,iI+1,iL)}&nbsp;&nbsp;<i class="wf-icon-caret-right"></i></a></div>
								</c:forEach>
						</td>
			    		<td align="left">
				    			<c:forEach items="${d.nodePermission}" var="e">
									<div class="tableBlock2">
											<c:forEach items="${e}" var="f">
								    			<a class="wf-btn-primary" href="#" target="_self">
								    				<c:set var="fI" value="${fn:indexOf(f,')')}"/>
				    								<c:set var="fL" value="${fn:length(f)}"/>
									                ${fn:substring(f,fI+1,fL)}
									            </a>
								    		</c:forEach>
									</div>
								</c:forEach>
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
			skipUrl="<%=request.getContextPath()%>"+"/uNodePermission_showPermissionToEverBody.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('searchForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		$(document).ready(function() {		
	        $('.wf-list-wrap table td').find('.tableBlock:odd').find('a').addClass('wf-btn-border-orange');
			$('.wf-list-wrap table td').find('.tableBlock2:odd').find('a').addClass('wf-btn-orange');
		});
	</script>
</html>