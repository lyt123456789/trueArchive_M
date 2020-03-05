<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="cn.com.trueway.base.util.*"%>
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
			<form id="chaxunform" name="chaxunform" action="${ctx}/rec_receivedDocList.do?type=${type}&departmentId=${departmentId}"  method="post" >
			<input type="hidden" id="type" name="type" value="${type}" />
			<div class="wf-search-bar">
	 	       		<label class="wf-form-label" for="">部门：</label>
	 	        	<select   class="wf-form-select" id="departmentId" name="departmentId" class="departmentId">
						<c:forEach items="${deps}" var="dep">
							<option value="${dep.departmentGuid}" <c:if test="${dep.departmentGuid eq departmentId}">selected="selected"</c:if>>${dep.departmentName}</option>
						</c:forEach>
					</select>
	 	        	<label class="wf-form-label" for="">来文号：</label>
	 	        	<input type="text" class="wf-form-text" id="wh" name="wh" value="${wh}" placeholder="输入关键字"/>
	 	        	<label class="wf-form-label" for="">标题：</label>
	 	        	<input value="${title}" class="wf-form-text" type="text" id="title" name="title" placeholder="输入关键字"/>
		            <label class="wf-form-label" for="">来文单位：</label>
		            <input value="${sendername}"  class="wf-form-text" type="text" size="12" id="sendername" name="sendername" placeholder="输入关键字"/>
		            <button class="wf-btn-primary" type="submit" >
		                <i class="wf-icon-search"></i> 搜索
		            </button>
			</div>
			</form>
		</div>
		<div class="wf-list-wrap">
				<form id="disform" method="POST" name="disform" action="${ctx}/rec_receivedDocList.do" >
				<table class="wf-fixtable" layoutH="140">
					<thead>
						<tr>
							<th width="10%" style="font-weight:bold;text-align:center;">来文号</th>
							<th width="10%" style="font-weight:bold;text-align:center;">发送人员</th>
							<th width="10%" style="font-weight:bold;text-align:center;">来文单位</th>
							<th width="20%" style="font-weight:bold;text-align:center;">标题</th>
							<th width="10%" style="font-weight:bold;text-align:center;">来文时间</th>
							<th width="10%" style="font-weight:bold;text-align:center;">收文时间</th>
							<th width="15%" style="font-weight:bold;text-align:center;">事项类别</th>
							<th width="20%" style="font-weight:bold;text-align:center;">状态</th>
						</tr>
			    	</thead>
			    	<tbody>
						<c:forEach items="${docList}" var="docList" varStatus="status">
							<tr > 
								<td style="text-align:center;">
									<label id="${docList.docguid}wh" value="${docList.wh}"><c:if test="${docList.status==0}">
										<span style="color: #1C488E">${docList.wh}&nbsp;</span></c:if>
										<c:if test="${docList.status==1}"><span style="color: #3B5F00">${docList.wh}&nbsp;</span></c:if>
										<c:if test="${docList.status==2 or docList.status==3 or docList.status==ygl}">${docList.wh}&nbsp;</c:if>
										<c:if test="${docList.status==4}"><span style="color: #C90F1F">${docList.wh}&nbsp;</span></c:if>
									</label>
								</td>
								<td style="text-align:left;">	${docList.sendername}</td>
								<td style="text-align:left;">
									${docList.sendername}
								</td>
								<td style="text-align:left;">
									<a href="#" onclick="turnToDetail('${departmentId}','${docList.docguid}', '${docList.queueXto}');" >${docList.title}</a>
								</td>
								<td style="text-align:left;">${fn:substring(docList.submittm,0,19)}</td>
								<td style="text-align:left;">${fn:substring(docList.recDate,0,19)}</td>
								<td>
									<c:forEach items="${map}" var="map" varStatus="sta">
										<c:if test="${map.key == docList.docguid}">
											<c:if test="${map.value.status == '0'}">
												<select name="itemLcId" id="itemLcId${docList.docguid}">  
										<c:forEach items="${itemList}" var="list">
							            	<option value="${list.lcid},${list.id},${list.vc_sxlx}" <c:if test="${list.vc_sxmc=='收文'}">selected</c:if>>${list.vc_sxmc}</option>
										</c:forEach>
						            </select>
											</c:if>
											<c:if test="${map.value.status == '1'}">
												${map.value.itemName}
											</c:if>
										</c:if>
									</c:forEach>
						            </td>
								<td>
									<c:forEach items="${map}" var="map" varStatus="sta">
										<c:if test="${map.key == docList.docguid}">
											<c:if test="${map.value.status == '0'}">
												<a href="#" onclick="innerPending('${docList.docguid}');">收入待办</a>
											</c:if>
											<c:if test="${map.value.status == '1'}">
												<a href="#" onclick="invaildPending('${docList.docguid}');">作废</a>
											</c:if>
										</c:if>
									</c:forEach>
									
								</td>
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
	 //分页相关操作代码
	 window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/rec_receivedDocList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('disform');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};

</script>
<script type="text/javascript" src="${ctx}/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
<script type="text/javascript">
/**
 * 跳转到相关详细页面
 */
function turnToDetail(departmentId, docguid, queueXto){
	var deptId = "";
	if(departmentId==''){
		deptId = queueXto;
	}else{
		deptId = departmentId;
	}
	window.location.href="${ctx}/rec_receivedDocDetail.do?listType=show&id="+docguid+"&deptId="+deptId;
}


function innerPending(recId){
	var obj = document.getElementById("itemLcId"+recId);
	var sxName = obj.options[obj.selectedIndex].innerHTML;
	if(confirm("确定收取到 '"+sxName+"' 吗？")){
		var selValue = obj.options[obj.selectedIndex].value;
		var workFlowId = "";
		var itemId = "";
		var vc_sxlx = "";//事项类型，用于判断是否是传阅---打开待办为传阅
		if(selValue != null || selValue != ""){
			workFlowId = selValue.split(",")[0];
			itemId = selValue.split(",")[1];
			vc_sxlx = selValue.split(",")[2];
		}
		$.ajax({
			url:'${ctx}/rec_innerPending.do',
			type:'POST',
			cache:false,
			async:true,
			data : {
				'workFlowId':workFlowId,'itemId':itemId,'receiveType':'one','recId':recId
			}, 
			error : function() {
				alert('AJAX调用错误(rec_innerPending.do)');
			},
			success : function(msg) {
					if(msg=='no'){
						alert("选择的事项类型所属工作流节点为空,请创建后再收取！");
						return false;
					}else{
						if(msg.split(";")[0] == 'success'){
							alert("收取成功,请到您待办中查看!");
							window.location.href="${ctx}/rec_receivedDocList.do";
							//不是传阅流程时，收取时生成pdf
							var formId = msg.split(";")[1];
							var instanceId = msg.split(";")[2];
							var processId = msg.split(";")[3];
							$.ajax({
								url:'${ctx}/table_receiveToPdf.do',
								type:'POST',
								cache:false,
								async:true,
								data : {
									'workFlowId':workFlowId,'formId':formId,'processId':processId,'instanceId':instanceId
								}, 
								success : function(msg) {
									
								}
							});
						}else{
							alert("收取失败,请联系管理员!");
							return false;
						}
				}
			}
		});
	}
}

function invaildPending(recId){
	if(confirm("确定作废待办 吗？")){
		$.ajax({
			url:'${ctx}/rec_invaildPending.do',
			type:'POST',
			cache:false,
			async:true,
			data : {
				'recId':recId
			}, 
			error : function() {
				alert('AJAX调用错误(rec_innerPending.do)');
			},
			success : function(msg) {
				alert("作废成功！");
				window.location.href = "${ctx}/rec_receivedDocList.do";
			}
		});
	}
}
</script>
</html>