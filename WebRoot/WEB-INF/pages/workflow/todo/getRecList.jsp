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
			<form name="receivelist"  id="receivelist" action="${ctx}/table_getDoFileReceiveList.do" method="post">
			<input type="hidden" name="status" id="status" value="${status}">
			<div class="wf-top-tool">
	            <a class="wf-btn-primary" href="javascript:batchCharg();">
	                <i class="wf-icon-pencil"></i>批量收取
	            </a>
	        </div>
	            <label class="wf-form-label" for="">标题：</label>
	            <input type="text" class="wf-form-text" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
	            <label class="wf-form-label" for="">来文号：</label>
	            <input type="text" class="wf-form-text" name="lwh" value="${lwh}" placeholder="输入关键字">
	            <label class="wf-form-label" for="">来文单位：</label>
	            <input type="text" class="wf-form-text" name="lwdw" value="${lwdw}" placeholder="输入关键字">
	            <label class="wf-form-label" for="">状态：</label>
                <select class="wf-form-select" id="state" name="state">
                	<option value=""></option>
	 				<option value="1" <c:if test="${state == 1}">selected="selected"</c:if>>已收</option>
	 				<option value="2" <c:if test="${state == 2}">selected="selected"</c:if>>已办理</option>
	 				<option value="3" <c:if test="${state == 3}">selected="selected"</c:if>>已拒收</option>
                </select>
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/table_getDoFileReceiveList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    		<tr>
			            <th width="3%">
						<input type="checkbox" name="selAll" id="selAll" onclick="selDb();"></th>
						<th width="3%" >序号</th>
						<th width="22%" >标题</th>
						<th width="10%" >来文号</th>
						<th width="10%" >来文单位</th>
						<th width="12%" >发文时间</th>
						<th width="12%" >收文/拒收时间</th>
						<th width="10%" >事项列别</th>
						<th width="19%" >操作</th>
			        </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr>
						<td align="center" style="height: 36px;">
			    			<input type="checkbox" name="selid" value="${item.id},${item.receiveType},${item.processId}" >
			    		</td>
						<td align="center">${(selectIndex-1)*pageSize+status.count}</td>							
						<!-- 来自页面发送的数据 -->
						<c:choose>
							<c:when test="${item.receiveType == '2' }">
								<td title="${item.title}">
									<a href="#" onclick="openReceiveForm2('${item.id}');">
										<c:choose>  
	    									<c:when test="${fn:length(item.title) > 24}">  
	      							 			 <c:out value="${fn:substring(item.title, 0, 24)}..." />  
	    									</c:when>  
	   										<c:otherwise>  
	      										<c:out value=" ${item.title}" />  
	    									</c:otherwise>  
										</c:choose>
									</a>
								</td>
							</c:when>
							<c:otherwise>
								<td title="${item.title}">
									<a href="#" onclick="openReceiveForm('${item.id}' ,'${item.pdfpath}','${item.title}');">
										<c:choose>  
		  										<c:when test="${fn:length(item.title) > 24}">  
		    							  				<c:out value="${fn:substring(item.title, 0, 24)}..." />  
		  										</c:when>  
		 											<c:otherwise>  
		    											<c:out value=" ${item.title}" />  
		  										</c:otherwise>  
										</c:choose>
									</a>
								</td>
							</c:otherwise>
						</c:choose>
						<td align="center">${item.lwh}</td>
						<td align="center" title="${item.lwdw}">
							<c:choose>  
	    						<c:when test="${fn:length(item.lwdw) > 15}">  
	      							<c:out value="${fn:substring(item.lwdw, 0, 15)}..." />  
	    						</c:when>  
	   							<c:otherwise>  
	      							<c:out value=" ${item.lwdw}" />  
	    						</c:otherwise>  
							</c:choose>
						</td>
						<td align="center"><fmt:formatDate value="${item.fwsj}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
						<td align="center"><fmt:formatDate value="${item.recDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
						<td align="center" style="text-align: center;">
						<!-- 办文走发文 回复的时候 设置 fromid 为 0  用以区分-->
							<c:choose>
								<c:when test="${item.status == '1'}">	
								</c:when>
								<c:when test="${(item.isInvalid =='1' || item.jrdb =='0' ) &&  item.formId != '0'
											&& jrdb =='true' && (item.receiveType!='2') && item.status != '4'}">	
								<!--receiveType=2 表示有办文发送过来的 -->
									<select  style="width:120px;"  name="itemLcId" id="itemLcId${item.id}">  
										<c:forEach items="${itemList}" var="list">
									    <option value="${list.lcid},${list.id},${list.vc_sxlx}">${list.vc_sxmc}</option>
										</c:forEach>
									</select>
								</c:when>
								<c:when test="${item.jrdb == '1'}">	
								</c:when>
								<c:otherwise>
									${item.itemName}
								</c:otherwise>
							</c:choose>
						</td>
						<td align="center">
							<c:if test="${item.status eq '0' }">
								<a href="#" class="wf-btn wf-btn-primary" onclick="updateStatus('${item.id}','');">仅收取</a>
								<a href="#" class="wf-btn wf-btn-primary" onclick="toPending('${item.processId}','${item.id}','${item.title }');">收取办理</a>
								<a href="#" class="wf-btn wf-btn-danger" onclick="updateStatus('${item.id}','4');">拒收</a>   
							</c:if>
							<c:if test="${item.status eq '1' }">
								<c:if test="${item.jrdb eq '1' }">
								已办理
								</c:if>
								<c:if test="${item.jrdb != '1' }">
								已收 
								</c:if> 
							</c:if>
							<c:if test="${item.status eq '4' }">
								已拒收 
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
			skipUrl="<%=request.getContextPath()%>"+"/table_getDoFileReceiveList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('receivelist');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
</script>
<script type="text/javascript">
//是否进入待办： true,需要进入待办
var jrdb = '${jrdb}';
//待收打开
function openReceiveForm(receiveId, path, title){
	/* if(path==null || path==''){
		//alert("表单为空,不予查看！");
		alert("未发现doc、pdf等格式附件，无法查看！");
		return;
	} */
	var url = '${ctx}/table_openReceiveForm.do?receiveId='+receiveId+'&jrdb='+jrdb+'&status=0&t='+new Date();
	
	//var ret = window.showModalDialog('${ctx}/table_openReceiveForm.do?receiveId='+receiveId+'&jrdb='+jrdb+'&status=0&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	openLayerTabs(receiveId,screen.width,screen.height,title,url);
	/* if(ret == 'refresh'){
		window.location.href="${ctx}/table_getDoFileReceiveList.do?status=0";
	} */
}

function openReceiveForm2(receiveId){
	var ret = window.showModalDialog('${ctx}/table_showRebackerForm.do?receiveId='+receiveId+'&jrdb='+jrdb+'&status=0&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	if(ret == 'refresh'){
		window.location.href="${ctx}/table_getDoFileReceiveList.do?status=0";
	}
}

//选中下拉框
function selDb(){
	var selAll = document.getElementById('selAll');
	var selid = document.getElementsByName('selid');
	for(var i = 0 ; i < selid.length; i++){
		if(selAll.checked){
			selid[i].checked = true;
		}else{
			selid[i].checked = false;
		}
	}
}

//更新状态位,待收调整为已收
function updateStatus(recId,state){
	$.ajax({
		url : '${ctx}/table_updateDoFileReceive.do',
		type : 'POST',
		cache : false,
		async : false,
		error : function() {
			alert('收取失败');
		},
		data : {
			'id' : recId, 
			'jrdb': jrdb,
			'state': state
		},
		success : function(result) {
			if (result == 'yes') { //收取成功
				if(state == '4'){
					alert("拒取成功！");
				}else{
					alert("收取成功！");
				}
				window.location.href = '${ctx}/table_getDoFileReceiveList.do?status=3';
			} else if(result == 'over'){
				alert("已收取,请重新刷新列表");
			}else{
				alert('收取待办失败');
			}
		}
	}); 
}

//进入待办： 事项Id, 事项名称，待收Id
function toPending(processId,id,title){
	
	var flag = false;
	$.ajax({
		url : '${ctx}/table_updateDoFileReceive.do',
		type : 'POST',
		cache : false,
		async : false,
		error : function() {
			alert('收取失败');
		},
		data : {
			'id' : id, 
			'jrdb': jrdb,
			'state': ''
		},
		success : function(result) {
			if (result == 'yes' || result == 'over') {
				flag = true;
			}else{
				alert('收取待办失败');
			}
		}
	});
	
	if(flag){
		var obj = document.getElementById("itemLcId"+id);
		if(obj){
			var sxName = obj.options[obj.selectedIndex].innerHTML;
			var workFlowId = "";
			var itemId = "";
			if(confirm("确定收取到 '"+sxName+"' 吗？")){
				var selValue = obj.options[obj.selectedIndex].value;
				if(selValue != null || selValue != ""){
					workFlowId = selValue.split(",")[0];
					itemId = selValue.split(",")[1];
					$.ajax({
						url : '${ctx}/table_innerPending.do',
						type : 'POST',
						cache : false,
						async : false,
						data : {
							'processId' : processId,
							'id' : id, 
							'workFlowId':workFlowId, 
							'itemId':itemId
						},
						error : function() {
							//alert('收入待办列表失败！');
						},
						success : function(result) {
							alert("收入待办成功");
							openForm(processId,itemId,'','','',title);
						}
					});
				}
			}
		}
	}
}

//批量收取
function batchCharg(){
	//获取所有input checkbox
	var objs = 	document.getElementsByName('selid');
	var recIds = "";
	//减少与前台的交互
	for(var i=0; i<objs.length; i++) {
		var id = "";
		if(objs[i].type.toLowerCase() == "checkbox" && objs[i].checked == true ){
			id = objs[i].value.split(",")[0];
			recIds += id +",";	
		}
	}
	if(recIds==null || recIds==''){
		alert("请选择批量收取的事项！");
		return;
	}
	
	if(confirm("确定要收取吗？")){
			$.ajax({
				url : '${ctx}/table_batchUpdateStatus.do',
				type : 'POST',
				cache : false,
				async : false,
				error : function() {
					alert('收取失败');
				},
				data : {
					'recIds' : recIds,
					'jrdb' : jrdb
				},
				success : function(result) {
					if (result == 'yes') { //收取成功
						alert("收取成功！");
						window.location.href = '${ctx}/table_getDoFileReceiveList.do?status=0';
					} else if (result == 'over') {
						alert("已收取,请重新刷新列表");
					} else {
						alert('收取待办失败');
				 }
			 }
		 });
		}
	}
	
	function openForm(processId,itemId,formId,isCanPush,isHaveChild,process_title){
		var url = '${curl}/table_openPendingForm.do?processId='+processId+'&isDb=true&itemId='+itemId+'&formId='+formId+'&isCanPush='+isCanPush+"&isHaveChild="+isHaveChild+'&t='+new Date();
		openLayerTabs(processId,screen.width,screen.height,process_title,url);
 	}
	
	function openLayerTabs(processId,width,height,title,url){
	   window.parent.topOpenLayerTabs(processId,1200,600,title,url);
	}
</script>
</html>