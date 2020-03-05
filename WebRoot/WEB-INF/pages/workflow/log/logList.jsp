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
			<form name="pendlist"  id="pendlist" action="${ctx }/log_queryLogList.do" method="post">
				<label class="wf-form-label" for="">办件标题：</label>
                <input style="width: 110px;" type="text" class="wf-form-text" id="prcoess_title" name="prcoess_title" value="${prcoess_title}" placeholder="办件标题">
                
 	        	<label class="wf-form-label" for="">日志信息：</label>
                <input style="width: 110px;" type="text" class="wf-form-text" id="msg" name="msg" value="${msg}" placeholder="日志信息">
                
                <label class="wf-form-label" for="">操作时间：</label>
                <input type="text" class="wf-form-text wf-form-date" id="createtimeFrom" name="createtimeFrom" value="${createtimeFrom}"  placeholder="操作时间" value="">
				    至            
				<input type="text" class="wf-form-text wf-form-date" id="createtimeTo" name="createtimeTo"  value="${createtimeTo}"   placeholder="输入日期" value="">
				
				<label class="wf-form-label" for="">日志等级：</label>
                <select class="wf-form-select" id="loglevel" onchange="changeCondition()" name="loglevel">
                	<option value=""></option>
	 				<option value="WARN" <c:if test="${loglevel eq 'WARN'}">selected="selected"</c:if>>WARN</option>
	 				<option value="ERROR" <c:if test="${loglevel eq 'ERROR'}">selected="selected"</c:if>>ERROR</option>
	 				
                </select>
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
                        <th align="center" width="5%">序号</th>
						<th >日志信息</th>
						<th width="5%">日志等级</th>
						<th width="10%">方法名</th>
						<th width="15%">办件标题</th>
						<th width="10%">操作人员</th>
						<th width="10%">操作时间</th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td title="${item.msg}">
							<a href="javascript:view('${item.logid }');">
								<c:if test="${fn:length(item.msg)>40 }">${fn:substring(item.msg,0,40)}</c:if>
								<c:if test="${fn:length(item.msg)<=40 }">${item.msg}</c:if>
							</a>
						</td>
						<td align="center" title="${item.loglevel}">${item.loglevel} </td>
						<td align="center" title="${item.method}">${item.method}</td>
						<td align="center" title="${item.prcoess_title}">${item.prcoess_title}</td>
						<td align="center" title="${item.username }">${item.username }</td>
						<td align="center" title="${fn:substring(item.createtime,0,16)}">${fn:substring(item.createtime,0,16)}</td>
					</tr>	
		    	</c:forEach>
			</table>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>
</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/log_queryLogList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('pendlist');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	 
	 var layerWF = window.top.layer;
     if (typeof(layerWF) == "undefined"){
         layerWF = layer
     }
     
	function view(logid){
		layerWF.open({
            title :'详细',
            type: 2,
           content: '${ctx}/log_view.do?logid='+logid,
            area: ['900px', '500px'],
        });
	}
	</script>
</html>