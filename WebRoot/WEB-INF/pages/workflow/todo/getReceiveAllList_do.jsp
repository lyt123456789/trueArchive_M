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
			<form name="receiveList"  id="receiveList" action="${ctx }/table_getReceiveAllList.do" method="post">
				<input type="hidden" name="status" id="status" value="${status}">
				<label class="wf-form-label" for="">标题：</label>
	            <input type="text" class="wf-form-text" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
	            <label class="wf-form-label" for="">来文号：</label>
	            <input type="text" class="wf-form-text" name="lwh" value="${lwh}" placeholder="输入关键字">
	            <label class="wf-form-label" for="">来文单位：</label>
	            <input type="text" class="wf-form-text" name="lwdw" value="${lwdw}" placeholder="输入关键字">
	            <label class="wf-form-label" for="">收文时间：</label>
	            <input type="text" class="wf-form-text wf-form-date" id="startTime" name="startTime" value="${startTime}"  placeholder="输入日期" />
				至
				<input type="text" class="wf-form-text wf-form-date" id="endTime" name="endTime"  value="${endTime}"   placeholder="输入日期"/>
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/table_getReceiveAllList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	<tr>
		    		<th  style="width:4%;">序号</th>
					<th  style="width:15%;">标题</th>
					<th  style="width:15%;">来文号</th>
					<th  style="width:14%;">来文单位</th>
					<th  style="width:10%;">收文时间</th>
    				<c:if test="${itemStatus != '1'}">	
                    <th  style="width:16%;">事项类别</th>
                    </c:if>
					<th  style="width:20%;">操作</th>
		    	</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			 	   <tr>
							<td align="center" style="height: 34px;">${(selectIndex-1)*pageSize+status.count }</td>							
							<td title="${item.title}" style="text-align: left;">
								<span style=" white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
								<c:choose>
								<c:when test="${item.receiveType == '2' && item.isReback == '1'}"> 
								<a href="#" onclick="openReceiveForm2('${item.id}');">
								<c:choose>  
    									<c:when test="${fn:length(item.title) > 22}">  
      							 			 <c:out value="${fn:substring(item.title, 0, 22)}..." />  
    									</c:when>  
   										<c:otherwise>  
      										<c:out value=" ${item.title}" />  
    									</c:otherwise>  
									</c:choose>
									</a>
								</c:when>
								<c:otherwise>
								<a href="#" onclick="openReceiveForm('${item.id}','${item.pdfpath}');"> 
									<c:choose>  
    									<c:when test="${fn:length(item.title) > 22}">  
      							 			 <c:out value="${fn:substring(item.title, 0, 22)}..." />  
    									</c:when>  
   										<c:otherwise>  
      										<c:out value="${item.title}" />  
    									</c:otherwise>  
									</c:choose>
								 </a> 
								</c:otherwise>
								</c:choose>
							</span>
							</td>
							<td align="center">${item.lwh}</td>
							<td align="center">${item.lwdw}</td>
							<td align="center"><fmt:formatDate value="${item.recDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
                            <c:if test="${itemStatus != '1'}">
                            <td align="center" style="text-align: center;">
							<!-- 办文走发文 回复的时候 设置 fromid 为 0  用以区分-->
								<c:choose>
									<c:when test="${(item.isInvalid =='1' || item.jrdb =='0' ) &&  item.formId != '0'
										&& jrdb =='true' && (item.receiveType!='2')}">	<!--receiveType=2 表示有办文发送过来的 -->
										<select  style="width:120px;"  name="itemLcId" id="itemLcId${item.id}">  
												<c:forEach items="${itemList}" var="list">
								            		<option value="${list.lcid},${list.id},${list.vc_sxlx}">${list.vc_sxmc}</option>
												</c:forEach>
							           </select>
									</c:when>
									<c:otherwise>
										${item.itemName}
									</c:otherwise>
								</c:choose>
							</td>
							</c:if>
							<td >
							<c:choose>
								<c:when test="${item.receiveType == '2' && item.isReback == '1'}"> 
								<a href="#" class="wf-btn wf-btn-primary" onclick="openReceiveForm2('${item.id}');"><i class="wf-icon-eye"></i> 查看</a>
							</c:when>	
							<c:otherwise>
							<a href="#" class="wf-btn wf-btn-primary"  onclick="openReceiveForm('${item.id}','${item.pdfpath}');"><i class="wf-icon-eye"></i> 查看</a>
							</c:otherwise>
							</c:choose>
							<!--&nbsp;&nbsp;<a href="#"  style ="color: #0e91d5;"  onclick="downgwjhpdf('${item.lwbt}','${item.pdfpath}');">版式文件下载</a>&nbsp;&nbsp;-->
								<c:choose>
									<c:when test="${item.receiveType == '2'}"> 	<!-- receiveType =2 由办文发送,无需收入待办等-->
											<c:if test="${item.status == '4' && item.isReback == '1'}" >
												已回复</c:if>
											<c:if test="${item.status != '4'&& item.isReback == '1'}" >
												<a href="#"   style="color: #0e91d5;" onclick="openFirst('${item.id}','${item.instanceId}','${item.processId}','${item.lwh}','${item.itemId}');">回复</a>
											</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${jrdb =='true'}">		<!--政府办的人员： jrdb(进入待办)  -->
											<c:if test="${item.jrdb =='0' && item.formId != '0'}">
											<c:if test="${itemStatus != '1'}">
											<a href="#" class="wf-btn wf-btn-danger" onclick="toPending('${item.processId}','${item.id}');"><i class="wf-icon-recycle"></i> 收入待办</a>
											</c:if>
										</c:if>
											<c:choose>
											<c:when test="${item.isInvalid =='1'}">
												<a href="#" class="wf-btn wf-btn-primary" style="color: #0e91d5; display:none;" onclick="reInnerPending('${item.processId}','${item.id}','${item.receiveType}');"><i class="wf-icon-recycle"></i> 重新收取</a>
											</c:when>
											<c:otherwise>
											<c:if test="${item.jrdb !='0'}">
												 <a class="wf-btn-danger" style="color: #0e91d5; display:none;" onclick="invalid('${item.id}');" ><i class="wf-icon-minus-circle"></i> 作废</a>
											</c:if>
											</c:otherwise>
										</c:choose>
										</c:if>
									</c:otherwise>
								</c:choose>
								<a href="#" class="wf-btn wf-btn-purple" onclick="downWord('${item.id}', '${item.title}')"><i class="wf-icon-gift"></i> 下载</a>
								<a class="wf-btn-danger" style="color: #0e91d5; display:none;" onclick="print(0,'${item.title}','${item.pdfpath}', '${item.dyfs}', '${item.ydyfs}', '${item.id}');"><i class="wf-icon-minus-circle"></i> 脱密打印</a>
							    <a class="wf-btn-danger" style="color: #0e91d5; display:none;" onclick="print(1,'${item.title}','${item.pdfpath}', '${item.dyfs}', '${item.ydyfs}', '${item.id}');"><i class="wf-icon-minus-circle"></i> 红章打印</a>
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
			skipUrl="${ctx}/table_getReceiveAllList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('receiveList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
</script>
<script type="text/javascript">
	var status = '${status}';
	var location_url = "${ctx}/table_getReceiveAllList.do?status="+status;
	
	function openForm(processId,itemId,formId){
		 var ret = window.showModalDialog('${ctx}/table_openOverForm.do?isds=1&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;scroll:no");
		 if(ret == "refresh"){
	     window.location.reload();
		 }
	}

	function downgwjhpdf(lwbt,pdfurl){
		if(pdfurl!=''){
			var downUrl = '${filedownloadUrl}?isabsolute=1&name='+lwbt+'.true&location='+pdfurl+"&from=RECEIVE";
			downUrl = encodeURI(downUrl);
			window.location.href=downUrl;
		}else{
			alert("没有需要下载的文件");
		}
	}
	//type 0:脱密打印，1。非脱密打印
	function printpdf(lwbt,pInstanceId,pdfurl,type){
			if(pdfurl!=''){
				if('${usbkey}'=='yiyuan'){
					OCXpdfobj2.SetStampType(0);
				}else{
					OCXpdfobj2.SetStampType(1);
				}
				OCXpdfobj2.SetSealUrl('${sealurl}');
				var downUrl = '${filedownloadUrl}?isabsolute=1&name='+lwbt+'.true&location='+pdfurl;
				downUrl = encodeURI(downUrl);
				//url 公文id 接收单位  类型0:脱密打印，1。非脱密打印 ,2.普通打印
				OCXpdfobj2.PrintPDFByStamp(downUrl,pInstanceId,"",type);
			}else{
				alert("没有需要打印的文件");
			}
	
	}
	//待收已收待办打开
	function openReceiveForm(receiveId,path){
		if(path==null || path==''){
			alert("未发现doc、pdf等格式附件，无法查看！");
		}else{
			var ret = window.showModalDialog('${ctx}/table_openReceiveForm.do?path='+path+'&receiveId='+receiveId+'&status=1&isWeb=1&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
			if(ret == "undefined" || typeof(ret) == "undefined"){
				
			}else if(ret == "refresh"){
		 		 window.location.href=location_url;
		 	}
		}
	}
	
	function openReceiveForm2(receiveId){
		var ret = window.showModalDialog('${ctx}/table_showRebackerForm.do?receiveId='+receiveId+'&status=1&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
		if(ret == 'refresh'){
			window.location.href=location_url;
		}
	}

	//收取作废(删除待办, 更新状态);
	function invalid(id){
		if(confirm("确定要作废吗？")){
		 	$.ajax({
				url : '${ctx}/table_invalidReceive.do',
				type : 'POST',
				cache : false,
				async : false,
				error : function() {
					alert('作废失败');
				},
				data : {
					'id' : id
				},
				success : function(result) {
					if (result == 'success') { //收取成功
						alert("作废成功！");
						window.location.href = location_url;
					} else {
						alert('收取待办失败');
						return;
					}
				}
			}); 
		}
	}
	
	function printTrue(fs,url,type){
		 return OCXpdfobj.PrintPDFByStamp(url,"","trueway",type,fs);
	}


	function print(type, title, path, dys, ydyfs, receiveId){
		var fs = dys-ydyfs;		
		var downUrl = '${filedownloadUrl}?isabsolute=1&name='+title+'.true&location='+path;
		downUrl = encodeURI(downUrl);
		var dyfs = printTrue(fs,downUrl,type);
		if(type==1){
			if(dyfs>fs){
				dyfs=fs;
			}
			fs=fs-dyfs;
			$.ajax({  
				url : 'table_setDyfs.do?receiveId='+receiveId+'&dyfs='+dyfs,
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_setDyfs.do)');
				},
				success : function(msg) {
					if(msg == 'no'){
						alert("更新状态失败，请联系管理员！");
						return false;
					}else if(msg == 'yes'){
						//页面刷新一下
						window.location.href = location_url;
					}
				}
			});
		} 
	}
	
	function downWord(receiveId, title){
		window.location.href='${ctx}/table_downloadTure.do?receiveId='+receiveId+'&name='+encodeURI(title);
	}
	
	
	
	function reInnerPending(processId, id, receiveType){
		var obj = document.getElementById("itemLcId"+id);
		if(obj){
			var sxName = obj.options[obj.selectedIndex].innerHTML;
			var workFlowId = "";
			var itemId = "";
			if(receiveType == 1){
				if(confirm("确定收取到 '"+sxName+"' 吗？")){
					var selValue = obj.options[obj.selectedIndex].value;
					if(selValue != null || selValue != ""){
						workFlowId = selValue.split(",")[0];
						itemId = selValue.split(",")[1];
						vc_sxlx = selValue.split(",")[2];
					}
					innerPending(processId,id,workFlowId,itemId,receiveType);
				}
			}
		}
	}
	
	//进入待办
	function innerPending(processId, id, workFlowId, itemId, receiveType){
		$.ajax({
			url : '${ctx}/table_innerPending.do',
			type : 'POST',
			cache : false,
			async : false,
			error : function() {
				alert('进入待办列表失败！');
			},
			data : {
				'processId' : processId,
				'id' : id, 'workFlowId':workFlowId, 'itemId':itemId ,'receiveType':receiveType
			},
			success : function(result) {
				if (result == 'yes') { 
					$.ajax({
						url : '${ctx}/table_updateInvalid.do',
						type : 'POST',
						cache : false,
						async : false,
						error : function() {
						},
						data : {
							'id' : id
						},
						success : function(result) {
							alert("收取成功,进入待办！");
							window.location.href = location_url;
						}
					});
					
				}else if(result == 'noNode'){
					alert("选择的事项类型所属工作流节点为空,请创建后再收取！");
					return false;
				}
			}
		});
	}
	
	function openFirst(id,instanceid,processId,lwh,itemId){
		var retVal = window.showModalDialog('${ctx}/table_openReply.do?itemId='+itemId+'&lwh='+encodeURI(lwh)+'&receiveId='+id+'&processId='+processId+'&instanceId='+instanceid+'&directSend=true&t='+Math.random(),null,"dialogWidth:700px;dialogHeight:400px;help:no;status:no;scroll:no");
		if (retVal == 'yes') {
			$.ajax({  
				url : 'table_updateReply.do',
				type : 'POST',   
				cache : false,
				async : false,
				data : {
					'processId':processId,
					'id':id,
					'instanceId':instanceid
				},
				error : function() {  
					alert('AJAX调用错误(table_sendDoc.do)');
				},
				success : function(msg) {
					window.location.href = location_url;
				}
			
			});
			//获取 instanceid , processid
			//更新 receive table  回复成功
		}else{
			 if(relation == '1'){//同步
			window.close();
			window.returnValue='refresh';
			 }
		}
	
	}
	
	//进入待办： 事项Id, 事项名称，待收Id
	function toPending(processId,id){
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
							'id' : id, 'workFlowId':workFlowId, 'itemId':itemId
						},
						error : function() {
							alert('收入待办列表失败！');
						},
						success : function(result) {
							alert("收入待办成功");
							window.location.href = location_url;
						}
					});
				}
			}
		}
	}
</script>
</html>