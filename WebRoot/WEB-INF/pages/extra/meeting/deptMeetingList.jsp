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
			<form name="doFileList"  action="${ctx }/meetings_getDeptMeetingList.do?itemIds=${itemIds}" method="post">
				<input type="hidden" name="itemName" value="${itemName}"/>
				<input type="hidden" name="isSuperior" value="${isSuperior}"/>
                <label class="wf-form-label" for="">会议时间：</label>
                <input type="text" class="wf-form-text wf-form-date"  id="searchDateFrom" name="searchDateFrom" value="${searchDateFrom}"  placeholder="输入日期" value="">
				    至         
				<input type="text" class="wf-form-text wf-form-date"  id="searchDateTo" name="searchDateTo"  value="${searchDateTo}"   placeholder="输入日期" value="">
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/meetings_getDeptMeetingList.do?itemIds=${itemIds}" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
						<th width="5%" style="font-weight:bold;text-align:center;">序号</th>
						<th width="15%" style="font-weight:bold;text-align:center;">会议标题</th>
						<th width="20%" style="font-weight:bold;text-align:center;">会议室</th>
						<th width="15%" style="font-weight:bold;text-align:center;">会议开始时间</th>
						<th width="15%" style="font-weight:bold;text-align:center;">会议结束时间</th>
						<th width="15%" style="font-weight:bold;text-align:center;">审核状态</th>
						<th width="15%" style="font-weight:bold;text-align:center;">操作</th>
					</tr>
		    	</thead>
		    	<tbody>
					<c:forEach items="${doFileList}" var="doFile" varStatus="status">
						<tr > 
							<td style="text-align:center;">${(selectIndex-1)*pageSize+status.count}</td>
							<td style="text-align:left;" title="${doFile.doFile_title}"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" ><a href="#" onclick="openForm('${doFile.processId}','${doFile.item_id}','${doFile.formId}');" >${doFile.doFile_title}</a></span></td> 
							<td style="text-align:left;" title="${doFile.roomName}"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" ><a href="#" onclick="openForm('${doFile.processId}','${doFile.item_id}','${doFile.formId}');" >${doFile.roomName}</a></span></td>
							<td style="text-align:center;">${doFile.meetingBeginTime}</td> 
							<td style="text-align:center;">${doFile.meetingEndTime}</td>
							<td style="text-align:center;">
								<c:if test="${doFile.state=='0'}"><font color="red">审核未通过</font></c:if>
								<c:if test="${doFile.state=='1'}"><font color="green">审核通过</font></c:if>
								<c:if test="${doFile.state=='2'}"><font color="red">已取消</font></c:if>
								<c:if test="${doFile.state!='0' && doFile.state!='1' && doFile.state!='2'}">未审核</c:if>
							</td>
							<td style="text-align:center;"><c:if test="${doFile.state!='2'}"><a href="#" onclick="invalid_meeting('${doFile.instanceId}','${doFile.workflowId}');";>取消会议</a></c:if></td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script>
function openForm(processId,itemId,formId){
	 var ret = window.showModalDialog('${ctx}/table_openOverForm.do?processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	 if(ret == "refresh"){
      	window.location.reload();
	 }
}

function invalid_meeting(instanceId,workFlowId){
	if(confirm("确定要取消申请吗？")){
		$.ajax({  
			url : 'table_invalid.do?instanceId='+instanceId+'&workFlowId='+workFlowId,
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				alert('AJAX调用错误(table_invalid.do)');
			},
			success : function(msg) {
				if(msg == 'no'){
					alert("取消失败，请联系管理员！");
					return false;
				}else if(msg == 'yes'){
					//更新会议数据表的状态位
					$.ajax({  
						url : 'meetings_updateMeetingState.do?instanceId='+instanceId+'&state=2',
						type : 'POST',   
						cache : false,
						async : false,
						error : function() {  
							alert('AJAX调用错误(meetings_updateMeetingState.do)');
						},
						success : function(msg) {
							if(msg == 'no'){
								alert("更新状态失败，请联系管理员！");
								return false;
							}else if(msg == 'yes'){
								alert("取消申请成功！");
								window.location.href="${ctx}/meetings_getDeptMeetingList.do?itemIds=${itemIds}";
							}
						}
					});
				}
			}
		});
	}
}

 //分页相关操作代码
 window.onload=function(){ 
		 	var wfTitle = document.getElementById('wfTitle');
		 	var itemName = document.getElementById('itemName');
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/meetings_getDeptMeetingList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('doFileList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};

</script>
</html>
