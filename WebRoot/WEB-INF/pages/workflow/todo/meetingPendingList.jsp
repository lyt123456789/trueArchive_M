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
	<form name="pendingList" id="pendingList" action="${ctx }/table_getMeetingPending.do" method="post">
	<div class="searchBar" >
		<table class="searchContent">
			<tr> 
				<td>
					标题：<input type="text" name="wfTitle" value="${wfTitle}"/>
				</td> 
				<td>
					事项类别：<input type="text" name="itemName" value="${itemName}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="itemid" value="${itemid }">
	</div>
	</form>
</div>

<div id="pageContent" class="pageContent"  style="overflow:auto;">
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getMeetingPending.do" >
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="5%" align="center">序号</th>
				<th width="8%" align="center">标题</th>
				<th align="center" width="15%" >所属事项</th>
				<!-- 
				<th width="13%" align="center">流程发起人</th>
				<th width="13%" align="center">发送人</th> 
				-->
				<th align="center" width="11%" >当前步骤</th>
				<th width="10%" align="center">接收时间</th>
				<th width="10%" align="center">步骤期限</th>
				<th width="10%" align="center">办件期限</th>
				<th width="8%" align="center">办理状态</th>
				<th width="8%" align="center">申请延期</th>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="item" items="${list}" varStatus="status"> 
			<tr >
				<td align="center">${(selectIndex-1)*pageSize+status.count }</td>
					<td align="left" title="${item.process_title }">
					<c:choose>
						<c:when test="${item.isDelaying != '1'}">
							<c:choose>
								<c:when test="${item.item_type != '2'}">
									<span style="width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
									<c:choose>
									<c:when test="${item.stepIndex!='1' || item.isChildWf == '1'}">
											<a href="#" onclick="openForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}','${item.isCanPush}');"><c:choose>  
    											<c:when test="${fn:length(item.process_title) > 25}">  
        										<c:out value="${fn:substring(item.process_title, 0, 25)}.." />  
    											</c:when>  
   												<c:otherwise>  
      											<c:out value=" ${item.process_title}" />  
    											</c:otherwise>  
											</c:choose></a>
									</c:when>
									<c:otherwise>
									<c:if test="${item.stepIndex=='1'}">
											<a href="${ctx}/table_openPendingForm.do?processId=${item.wf_process_uid}&isDb=true&itemId=${item.item_id }&formId=${item.form_id }&isCy=${isCy}"><c:choose>  
    											<c:when test="${fn:length(item.process_title) > 25}">  
        										<c:out value="${fn:substring(item.process_title, 0, 25)}.." />  
    											</c:when>  
   												<c:otherwise>  
      											<c:out value=" ${item.process_title}" />  
    											</c:otherwise>  
											</c:choose></a>
										</c:if>
									</c:otherwise>
									</c:choose>		
									</span>
								</c:when>
								<c:when test="${item.item_type == '2' || (item.stepIndex=='1' && item.isChildWf == '')}">
									<span style="width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
										<a href="${ctx}/table_openPendingForm.do?processId=${item.wf_process_uid}&isDb=true&itemId=${item.item_id }&formId=${item.form_id }&isCy=${isCy}"><c:choose>  
    											<c:when test="${fn:length(item.process_title) > 25}">  
        										<c:out value="${fn:substring(item.process_title, 0, 25)}.." />  
    											</c:when>  
   												<c:otherwise>  
      											<c:out value=" ${item.process_title}" />  
    											</c:otherwise>  
											</c:choose></a>
									</span>
								</c:when>
							</c:choose>
						</c:when>							
						<c:otherwise>
							<c:choose>  
    											<c:when test="${fn:length(item.process_title) > 25}">  
        										<c:out value="${fn:substring(item.process_title, 0, 25)}.." />  
    											</c:when>  
   												<c:otherwise>  
      											<c:out value=" ${item.process_title}" />  
    											</c:otherwise>  
											</c:choose>
						</c:otherwise>
					</c:choose>
					</td>
				<td align="left">${item.item_name }</td>
				<%-- <td align="center">${item.owner }</td>
				<td align="center">${item.employee_name }</td> --%>
				<td align="center">${item.nodeName}</td>
				 
				<td align="center">${fn:substring(item.apply_time,0,16) }</td>
				<!--
				<td align="center">${item.wfn_deadline }
					<c:choose>
						<c:when test="${item.wfn_deadlineunit=='0'}">工作日</c:when>
						<c:when test="${item.wfn_deadlineunit=='1'}">小时</c:when>
						<c:when test="${item.wfn_deadlineunit=='2'}">天</c:when>
						<c:when test="${item.wfn_deadlineunit=='3'}">周</c:when>
						<c:when test="${item.wfn_deadlineunit=='4'}">月</c:when>
						<c:when test="${item.wfn_deadlineunit=='5'}">年</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</td> 
				
				<c:choose>
					<c:when test="${item.warnType=='0'}"><td align="center" style="color: red">${item.remainTime }</td></c:when>
					<c:when test="${item.warnType=='1'}"><td align="center" style="color:#DE9B24 ">${item.remainTime }</td></c:when>
					<c:when test="${item.warnType=='2'}"><td align="center" style="color: blue">${item.remainTime }</td></c:when>
					<c:otherwise><td align="center" style="color: green">${item.remainTime }</td></c:otherwise>
				</c:choose>
				-->
				<td align="center"><fmt:formatDate value="${item.jdqxDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
				<td align="center"><fmt:formatDate value="${item.zhqxDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
				<td align="center"><c:if test="${item.status == '0'}"><font color="red">办理中</font></c:if><c:if test="${item.status == '1'}"><font color="red">已办结</font></c:if></td>
				<%-- <td align="center"><c:if test="${item.entrust_name!=null && item.entrust_name!=''}">由<font color="purple">${item.entrust_name }</font>委托</c:if></td>--%>				
 				<td align="center">
					<c:choose>
						<c:when test="${item.delay_itemid !=''}">
							<c:if test="${item.isDelaying == '0'}">
								<a href="#" onclick="openDelayForm('${item.delay_lcid}','${item.wf_instance_uid}','${item.delay_itemid}');">申请</a>
							</c:if>
							<c:if test="${item.isDelaying == '1'}">
								<font color="red">延期申请中</font>
								<input type="button" value="查看" onclick="openPendingProcess('${item.wf_instance_uid}');">
							</c:if>
							<c:if test="${item.isDelaying == '2'}">
								<font color="red">已申请</font>
								<input type="button" value="查看" onclick="openOverProcess('${item.wf_instance_uid}');">
							</c:if>
						</c:when>
						<c:otherwise>
							 <font color="Silver">申请</font>
						</c:otherwise>
					</c:choose>
				</td>
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
 function openForm(processId,itemId,formId,isCanPush){
	 var ret = window.showModalDialog('${ctx}/table_openPendingForm.do?processId='+processId+'&isDb=true&itemId='+itemId+'&formId='+formId+'&isCanPush='+isCanPush+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	 if(ret == "undefined" || typeof(ret) == "undefined"){
        //window.location.reload();
 	 }else if(ret == "refresh"){
 		 window.location.href="${ctx}/table_getPendingList.do?itemid="+itemId;
 	 }
 }

 //查询当前节点人员组内除本身以外的人员
 function pushDb(nodeId,processId){
		var ret = window.showModalDialog('${ctx}/group_getInnerOtherUsers.do?nodeId='+nodeId+'&processId='+processId+'&t='+new Date(),null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no");
		if(ret == 'success'){
			alert("推送成功！");
			window.location.href="${ctx}/table_getPendingList.do";
		}else if(ret == 'fail'){
			alert("推送失败,请联系管理员！");
		}
 }
 
 function openPendingProcess(instanceId){ 	//查看子流程的进程
 	//根据instanceId 查询申请延期的 待办信息
 	$.ajax({   
			url : '${ctx}/itemRelation_getBjYqInfo.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(item_getBjYqInfo.do)');
			},
			data : {
				'instanceId':instanceId
			},    
			success : function(result) {  
				var value = result.split(";");
				var instanceid = value[0];		
				var workFlowId = value[1];		
				var ret = window.showModalDialog('${ctx}/table_showProcess.do?instanceId='+instanceid+'&workFlowId='+workFlowId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
			}
		});
 }
 
 function openOverProcess(instanceId){
	 $.ajax({   
			url : '${ctx}/itemRelation_getBjYqOverInfo.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(itemRelation_getBjYqOverInfo.do)');
			},
			data : {
				'instanceId':instanceId
			},    
			success : function(result) {  
				var value = result.split(";");
				var instanceid = value[0];		
				var workFlowId = value[1];		
				var ret = window.showModalDialog('${ctx}/table_showProcess.do?instanceId='+instanceid+'&workFlowId='+workFlowId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
			}
		});
	 
	 
 }
 
 function openDelayForm(lcid,instanceId,delayitemid){
	 var status = checkStatus(lcid);
	 if(status!='0'){
			window.location.href = "${ctx}/table_openFirstForm.do?yqinstanceid="+instanceId+"&workflowid="+lcid+"&itemid="+delayitemid;
	 }else{
			alert("该流程为暂停状态，不可创建实例");
	 }
 }
 
//检查流程状态，“暂停”则不可新建实例
	function checkStatus(lcid){
		var status = "";
		$.ajax({   
			url : '${ctx }/item_getWfStatus.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(item_getWfStatus.do)');
			},
			data : {
				'workflowid':lcid
			},    
			success : function(result) {  
				status = result;
			}
		});
		return status;
	}
	
 //分页相关操作代码
 window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getMeetingPending.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('pendingList');									//提交的表单,必须修改!!!*******************
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
		$('#pageContent').height($(window).height()-70);
</script>
</html>