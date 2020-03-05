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
			<form name="pendlist"  id="pendlist" action="${ctx }/business_getReceiveLibrary.do" method="post">
					<input type="hidden"   name="sxlx" value="${sxlx}" />
 	        		<label class="wf-form-label" for="">标题名称：</label>
	 	        	<input type="text" class="wf-form-text"  name="title" value="${title}" placeholder="输入关键字"/>
	 	        	<label class="wf-form-label" for="">来文号：</label>
	 	        	<input type="text" class="wf-form-text" name="wh" value="${wh}" placeholder="输入关键字"/>
	 	        	<label class="wf-form-label" for="">来文单位：</label>
	 	        	<input type="text" class="wf-form-text" name="dw" value="${dw}" placeholder="输入关键字"/>
	                <label class="wf-form-label" for="">发送时间：</label>
	                <input type="text" class="wf-form-text wf-form-date" id="beginTime" name="beginTime" value="${beginTime}"  placeholder="输入日期" />
					    至            
					<input type="text" class="wf-form-text wf-form-date" id="endTime" name="endTime"  value="${endTime}"   placeholder="输入日期" /><br>
  					<label class="wf-form-label" for="">事项名称：</label>
	                <select class="wf-form-select" id="itemid" name="itemid" style="margin-top: 5px">
	                	<option value=""></option>
	                	<c:forEach var="m" items='${myPendItems}'>
		 					<option value="${m.id}" <c:if test="${itemid ==m.id}">selected="selected"</c:if>>${m.vc_sxmc}</option>
		 				</c:forEach> 
	                </select>
	 	        	<button class="wf-btn-primary" onclick="document.forms.freeDofileList.submit();" style="margin: 5px 0 0 5px ">
		                <i class="wf-icon-search"></i> 搜索
		            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/business_getReceiveLibrary.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
                        <th align="center" width="5%">序号</th>
						<th>标题</th>
						<th width="10%">事项名称</th>
						<th width="15%">来文号</th>
						<th width="15%">发送时间</th>
						<th width="15%">来文单位</th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td title="${item.title}">
							<a href="#" onclick="openForm('${item.instanceId}','${item.title}');">
									${item.title}
							</a>
						</td>
						<td align="center" title='${item.itemName}'>${item.itemName}</td>
						<td >${item.lwh}</td>
						<td >${fn:substring(item.lwsj,0,16)}</td>
						<td align="left" title="${item.lwdw}">${item.lwdw}</td>
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
			skipUrl="<%=request.getContextPath()%>"+"/business_getReceiveLibrary.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('pendlist');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
</script>
<script>
function openForm(instanceId,process_title){
		var url = '${ctx}/table_openOverForm.do?instanceId='+instanceId+'&t='+new Date();
		openLayerTabs(instanceId,screen.width,screen.height,process_title,url);
}
	 
function openLayerTabs(processId,width,height,title,url){
   window.parent.topOpenLayerTabs(processId,1200,600,title,url);
}
	</script>
</html>