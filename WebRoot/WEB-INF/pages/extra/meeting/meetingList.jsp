<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>中威科技工作流表单系统</title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
</head>
    <body>
    <div class="pageHeader" >
	<form id="meetingList" name="meetingList"  action="${ctx }/meetings_getMeetingList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					会议主题：<input type="text" name="title" value="${title}" id="title"/>
				</td>
				<td>
					会议地点：<input type="text" name="arr" value="${arr}" id="arr"/>
				</td> 
				<td>
					参与人员：<input type="text" name="person" value="${person}" id="person"/>
				</td>
				<td>
					召集领导：<input type="text" name="zcr" value="${zcr}" id="zcr"/>
				</td>
			</tr>
			<tr>
				<td>
					开始时间：<input type="text" name="begtime" value="${begtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH时mm分'})" id="begtime"/>
				</td>
				<td>
					结束时间：<input type="text" name="endtime" value="${endtime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH时mm分'})" id="endtime"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
        <div class="pageContent">
		    <form id="thisForm" method="POST" name="thisForm" action="${ctx }/meetings_getMeetingList.do" >
		    	<div id="w_list_print">
					<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
						<thead>
							<tr>
								<td width="5%" style="font-weight:bold;text-align:center;">序号</td>
								<td width="20%" style="font-weight:bold;text-align:center;">会议室</td>
								<td width="22%" style="font-weight:bold;text-align:center;">会议开始时间</td>
								<td width="23%" style="font-weight:bold;text-align:center;">会议结束时间</td>
								<td width="15%" style="font-weight:bold;text-align:center;">审核状态</td>
								<td width="15%" style="font-weight:bold;text-align:center;">操作</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${meetingList}" var="list" varStatus="status">
								<tr > 
									<td style="text-align:center;">${(selectIndex-1)*10+status.count}</td>
									<td style="text-align:left;" title="${list.roomname}">
									<span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" ><a href="#" onclick="openForm('${list.processid}','${list.item_id}','${list.formid}');" >${list.roomname}</a></span></td>
									<td style="text-align:center;">${list.meeting_begin_time}</td> 
									<td style="text-align:center;">${list.meeting_end_time}</td>
									<td style="text-align:center;">
										<c:if test="${list.state=='0'}"><font color="red">审核未通过</font></c:if>
										<c:if test="${list.state=='1'}"><font color="green">审核通过</font></c:if>
										<c:if test="${list.state=='2'}"><font color="red">已取消</font></c:if>
										<c:if test="${list.state!='0' && list.state!='1' && list.state!='2'}">未审核</c:if>
									</td>
									<td style="text-align:center;"><a href="#" onclick="invalid_meeting('${list.instanceid}','${list.workflowid}');";>取消会议</a></td> 
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
 //分页相关操作代码
 window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/meetings_getMeetingList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('meetingList');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	}
$("table.list", document).cssTable();

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
								document.getElementById('meetingList').submit();
							}
						}
					});
				}
			}
		});
	}
}
</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
</html>
