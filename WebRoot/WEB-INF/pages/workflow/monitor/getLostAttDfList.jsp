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
		  <form name="doItemList"  id="doItemList" action="${ctx}/monitor_getLostAttDfList.do" method="post">
			<div class="wf-top-tool">
	         </div>
	            <label class="wf-form-label" for="">标题：</label>
	            <input type="text" class="wf-form-text" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/monitor_getLostCmtDfList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    		<tr>
						<th width="5%" >序号</th>
						<th >办件标题</th>
						<th width="14%">发起人</th>
						<th width="14%">所属站点</th>
						<th width="14%">是否有上传文件</th>
						<th width="14%">文件是否可预览</th>
						<th width="14%">文件是否真实存在</th>
					</tr>
		    	</thead>
		    	<c:forEach items="${lostAttsDfList}" var="doFile" varStatus="status">
			    	<tr>
						<td style="text-align:center;">${(selectIndex-1)*pageSize+status.count}</td>
						<td style="text-align: left; padding-left: 10px;" title="${doFile.title}">
							<a href="#" onclick="openForm('${doFile.processId}','','','','${doFile.title}');"><c:out value=" ${doFile.title}" /></a>
						</td>
						<td style="text-align:center;">${doFile.userName}</td>
						<td style="text-align:center;">${doFile.siteName}</td>
						<td style="text-align:center;" >
							<c:choose>
								<c:when test="${doFile.docguid=='' || doFile.docguid == null}">否</c:when>
								<c:otherwise>是</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${doFile.pages=='' || doFile.pages == null}">否</c:when>
								<c:otherwise>是</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${doFile.pages=='' || doFile.pages == null}">否</c:when>
								<c:otherwise>是</c:otherwise>
							</c:choose>
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
			skipUrl="<%=request.getContextPath()%>"+"/monitor_getLostAttDfList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('doItemList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
</script>
<script type="text/javascript">
function openForm(processId,itemId,formId,favourite,process_title){
	if(processId!=''){
		 var url = '${curl}/table_openOverForm.do?favourite='+favourite+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
		 openLayerTabs(processId,screen.width,screen.height,process_title,url);
	}
}

function openLayerTabs(processId,width,height,title,url){
   parent.topOpenLayerTabs(processId,1200,600,title,url);
}	
</script>
</html>