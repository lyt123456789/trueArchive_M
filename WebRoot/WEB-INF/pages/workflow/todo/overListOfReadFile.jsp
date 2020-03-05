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
<style type="text/css">
	.new-htable {
		margin-top:20px;
		width:96%;
		margin-left:3%;
	}
	.new-htable tr th{
		text-align:right;
		color:#333333;
		font-size:16px;
		font-weight:normal;
		height:40px;
	    vertical-align: middle;
	}
	.new-htable tr td{
		text-align:left;
		color:#333333;
		font-size:15px;
		font-weight:normal;
		height:40px;
	    vertical-align: middle;
	}
	.new-htable .tw-form-text{
		width:354px;
		text-indent:6px;
		height:30px;
		border:1px solid #e6e6e6;
		border-radius:3px;
		vertical-align: middle;
	}
	.new-htable select{
		width:163px;
		height:30px;
		border:1px solid #e6e6e6;
		border-radius:3px;
		vertical-align: middle;
	}
	.wf-form-date{
		width:133px!important;
	}
	.wf-hover .icon-reply{
		display:inline-block;
	}
</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="ovelist"  id="ovelist" action="${ctx }/tableExtend_getOverListOfReadFile.do" method="post" style="display:inline-block;">
	 	        <input type="hidden" name="isPortal" value="${isPortal}"/>
	 	        <input type="hidden" name="siteId" value="${siteId}"/>
 	        	<label class="wf-form-label" for="">&nbsp;&nbsp;&nbsp;&nbsp;标题：</label>
                <input type="text" class="wf-form-text" id="wfTitle" name="wfTitle" style="width: 90px" value="${wfTitle}" placeholder="输入关键字">
				<label class="wf-form-label" for="">发文日期：</label>
                <input type="text" class="wf-form-text wf-form-date" id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}"  placeholder="输入日期" value="">
				    至            
				<input type="text" class="wf-form-text wf-form-date" id="commitTimeTo" name="commitTimeTo"  value="${commitTimeTo}"   placeholder="输入日期" value="">
	            <button class="wf-btn-primary" type="submit" onclick="checkForm('2');">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
			</form>
			<span style="position:relative;">
	            <a class="high-search" href="#" onclick="showSearch();">高级搜索</a>
				<div id="high-search">
					<form method="post" id="form" name="form" action="#" class="tw-form">
					<input type="hidden" name="siteId" value="${siteId}"/>
					<table class="new-htable">
                		<tr>
		                    <th width="85">标题：</th>
		                    <td><input type="text"   placeholder="请输入标题关键字" class="tw-form-text" id="wfTitle2" name="wfTitle" value="${wfTitle}"></td>
		                </tr>
		                <tr>
		                    <th>发文日期：</th>
		                    <td><input type="text" class="tw-form-text wf-form-date" id="commitTimeFrom2" name="commitTimeFrom" value="${commitTimeFrom}" style="width:163px;">
								至
								<input type="text" class="tw-form-text wf-form-date" id="commitTimeTo2" name="commitTimeTo" value="${commitTimeTo}" style="width:163px;">
							</td>
		                </tr>
		                <tr>
		                    <th>文号：</th>
		                    <td><input type="text" placeholder="请输入文号" class="tw-form-text" id="wh" name="wh" value="${wh}"></td>
		                </tr>
		                <tr>
		                    <th>来文单位：</th>
		                    <td><input type="text" placeholder="请输入来文单位" class="tw-form-text" id="lwdw" name="lwdw" value="${lwdw}"></td>
		                </tr>
						<tr>
		                    <th></th>
		                    <td>
		                    	<button class="wf-btn-primary" type="submit" onclick="checkForm('1');">
			                		<i class="wf-icon-search"></i> 搜索
			            		</button>
						 		<a class="high-search" href="#" onclick="form.reset()">清空搜索条件</a>
							</td>
						</tr>
            		</table>
            		</form>
				</div>
			</span>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/tableExtend_getOverListOfReadFile.do" >
		<input type="hidden" name="siteId" value="${siteId}"/>
			<table class="wf-fixtable" layoutH="140">
				<thead>
			    	<tr>
			    		<th width="4%">序号</th>
						<th>标题</th>
						<th width="40"></th>
						<th width="10%">当前步骤</th>
						<th width="15%">当前步骤办理人</th>
						<th width="15%">办理时间</th>
						<th width="6%" >状态</th>
						<th width="4%">操作</th>
			    	</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			  		<tr>
						<td align="center">${(selectIndex-1)*pageSize+status.count }</td>
						<td align="left" title="${item.process_title }" >
							<c:choose>
								<c:when test="${item.item_type == '0'}">
									<span class="tp-info-cate FW">${item.item_name }</span>
								</c:when>
								<c:when test="${item.item_type == '1'}">
									<span class="tp-info-cate SW">${item.item_name }</span>
								</c:when>
								<c:when test="${item.item_type == '3'}">
									<span class="tp-info-cate XMSP">${item.item_name }</span>
								</c:when>
								<c:when test="${item.item_type == '4'}">
									<span class="tp-info-cate FKSQ">${item.item_name }</span>
								</c:when>
								<c:otherwise>
									<span class="tp-info-cate">${item.item_name }</span>
								</c:otherwise>
							</c:choose>
							<c:if test="${('1' eq item.isHaveChild)}">
								<span style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
									<c:if test="${!('1' eq item.isChildWf)}"><span class="wf-badge-main">主</span></c:if>
					   			 	<c:if test="${('1' eq item.isChildWf)}"><span class="wf-badge-sub">子</span></c:if>
								</span>
							</c:if>
							
							<span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
								<a href="#" onclick="openForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}','${item.favourite}','${item.isHaveChild}','${item.process_title}','${item.nowProcessId}');">
									<c:choose>  
										<c:when test="${fn:length(item.process_title) > 24}">  
											<c:out value="${fn:substring(item.process_title, 0, 24)}..." />  
										</c:when>  
			   							<c:otherwise>  
			      							<c:out value=" ${item.process_title}" />  
			    						</c:otherwise>  
									</c:choose>
								</a>
							</span>
						</td>
						<td>
						<c:if test="${item.status == '0'}">
								<c:if test="${item.isBack == '3'}">
									<i class="icon-reply" title="收回操作" onclick="recycleTask('${item.wf_instance_uid}','${item.stepIndex}');"></i>
								</c:if>
							</c:if>
							<c:if test="${item.isBack == '2' || item.isBack == '1'}"></c:if>
						</td>
						<td align="center">${item.nodeName}</td>
						<td align="center">${item.entrust_name}</td>
						<td align="center">${fn:substring(item.finish_time,0,16)}&nbsp;</td>
						<td align="center">
							<c:if test="${item.status == '0'}">
								<span class="wf-badge-doing">办</span>
							</c:if>
							<c:if test="${item.status == '1'}">
								<span class="wf-badge-over">结</span>
							</c:if>
							<c:if test="${item.status == '3'}">
								<span class="wf-badge-high">超</span>
							</c:if>
						</td>
						<td align="center">
							<c:if test="${item.favourite == '0'}">
								<span class="collect" id="${item.wf_process_uid}fva" onclick="addCollection('${item.wf_instance_uid}','${item.wf_workflow_uid}','${item.wf_process_uid}fva','${item.wf_process_uid}fvadel');"></span>
								<span class="collected" id="${item.wf_process_uid}fvadel" onclick="deleteCollection('${item.dofileId}','${item.wf_process_uid}fva','${item.wf_process_uid}fvadel');" style="display: none;"></span>
							</c:if>
							<c:if test="${item.favourite == '1'}">
								<span class="collect" id="${item.wf_process_uid}fva" onclick="addCollection('${item.wf_instance_uid}','${item.wf_workflow_uid}','${item.wf_process_uid}fva','${item.wf_process_uid}fvadel');" style="display: none;"></span>
								<span class="collected" id="${item.wf_process_uid}fvadel" onclick="deleteCollection('${item.dofileId}','${item.wf_process_uid}fva','${item.wf_process_uid}fvadel');"></span>
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
			skipUrl="<%=request.getContextPath()%>"+"/tableExtend_getOverListOfReadFile.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('ovelist');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
	</script>
	<script type="text/javascript">
		var hopen=false;
		function showSearch(){
			if(!hopen){
				document.getElementById("wfTitle2").value=document.getElementById("wfTitle").value;
				document.getElementById("commitTimeFrom2").value=document.getElementById("commitTimeFrom").value;
				document.getElementById("commitTimeTo2").value=document.getElementById("commitTimeTo").value;
				
				document.getElementById("wfTitle").value="";
			$('#itemName option:selected').val("");
			document.getElementById("commitTimeFrom").value="";
			document.getElementById("commitTimeTo").value="";
			var wh = document.getElementById("wh").value;
			var lwdw = document.getElementById("lwdw").value;
			var wfTitle = document.getElementById("wfTitle").value;
			var commitTimeFrom2 = document.getElementById("commitTimeFrom2").value;
			var commitTimeTo2 = document.getElementById("commitTimeTo2").value;
			
			document.getElementById("high-search").style.display="block";
			hopen=true;
			}
			else{
				document.getElementById("high-search").style.display="none";
				hopen=false;
			}
			// document.getElementById("hsearch-iframe").src="${ctx}/table_showSearch.do?wh="+
			// encodeURI(encodeURI(wh))+"&lwdw="+encodeURI(encodeURI(lwdw))+"&wfTitle="+
			// encodeURI(encodeURI(wfTitle2))+"&commitTimeFrom="+commitTimeFrom2+"&commitTimeTo="+
			// commitTimeTo2+"&itemType="+itemType2;
			
			// layer.open({
				// type:2,
				// content:"${ctx}/table_showSearch.do?wh="+encodeURI(encodeURI(wh))+"&lwdw="+encodeURI(encodeURI(lwdw))+"&wfTitle="+encodeURI(encodeURI(wfTitle2))+"&commitTimeFrom="+commitTimeFrom2+"&commitTimeTo="+commitTimeTo2+"&itemType="+itemType2,
				// area:["450px","460px"],
				// title:"高级搜索",
				// btn:["完成"],
				// yes:function(index,layero){
					// var iframeWin = window[layero.find('iframe')[0]['name']];
					// var ret = iframeWin.sub();
					// var json = eval('(' + ret + ')');
					// document.getElementById("wfTitle2").value=json.wfTitle;
					// document.getElementById("itemType2").value=json.itemType;
					// document.getElementById("commitTimeFrom2").value=json.commitTimeFrom;
					// document.getElementById("commitTimeTo2").value=json.commitTimeTo;
					// document.getElementById("wh").value=json.wh;
					// document.getElementById("lwdw").value=json.lwdw;
					// checkForm('1');
					// layer.close(index);
				// }
			// });
		}
		
			
		function radions_fw(){
			var fields = "isFw".split(",");
			for(var i = 0; i < fields.length; i++){
				var radios = document.getElementsByName(fields[i]);
				for(var j = 0; j < radios.length; j++){
					if(radios[j].checked){
						document.getElementById("b_"+fields[i]).value = radios[j].value;
					}
				}
			}
		}
		
		function checkTime(){
			var commitTimeFrom = document.getElementById("commitTimeFrom").value;
			var commitTimeTo = document.getElementById("commitTimeTo").value;
			var commitTimeFrom2 = document.getElementById("commitTimeFrom2").value;
			var commitTimeTo2 = document.getElementById("commitTimeTo2").value;
			
			if ((""!=commitTimeFrom&&""!=commitTimeTo)&&commitTimeFrom > commitTimeTo||(""!=commitTimeFrom2&&""!=commitTimeTo2)&&commitTimeFrom2 > commitTimeTo2){
				alert("开始时间不能大于结束时间！");
				document.getElementById("commitTimeFrom").value="";
				document.getElementById("commitTimeTo").value="";
				document.getElementById("commitTimeFrom2").value="";
				document.getElementById("commitTimeTo2").value="";
				return 1;
			}
		}
		
		function checkForm(status){
			var timeCheck=checkTime();
			if(timeCheck!=null&&timeCheck!='undefined'){
				return;
			}
			
			if(status == '2'){
				document.getElementById("wfTitle2").value="";
				document.getElementById("itemName2").value="";
				document.getElementById("commitTimeFrom2").value="";
				document.getElementById("commitTimeTo2").value="";
				document.getElementById("wh").value="";
				document.getElementById("lwdw").value="";
			}
			radions_fw();
			document.getElementById('ovelist').submit();
		}
		

		function recycleTask(instanceId,stepIndex){
			// 判读办件下一步是否 办件（如果是超管 pass，普通用户 go
			var flag = false;
			if(confirm("确定要收回吗？")){
				flag = true;
			}
			if(!flag){
				return;
			}
			$.ajax({   
				url : 'table_nextStepIsOver.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_nextStepIsOver.do)');
				},
				data : {
					'instanceId':instanceId,'stepIndex':stepIndex
				},    
				success : function(msg) {  
					if(msg == 'yes'){
						// 说明 下一步没有办理,或者超管执行收回操作
						$.ajax({
							url : '${ctx}/table_recycleTask.do',
							type : 'POST',
							cache : false,
							async : false,
							data : {
								'instanceId':instanceId,'stepIndex':stepIndex
							},
							error : function() {
								alert('AJAX调用错误');
							},
							success : function(ret) {
								if (ret == 'yes') {
									alert("办件收回成功");
									window.location.reload();
								} else {
									alert("办件收回失败");
								}
							}
						});
					}else{
						alert("该办件下一步骤，已办理，无法回收");
					}
					
				}
			});
			
		}
		
		function changOverFile(processId){
			// 判读办件下一步是否 办件（如果是超管 pass，普通用户 go
			var flag = false;
			if(confirm("确定要收回再办吗？")){
				flag = true;
			}
			if(!flag){
				return;
			}
			$.ajax({   
				url : 'tableExtend_changOverFile.do',
				type : 'POST',   
				cache : false,
				async : false,
				data : {
					'processId':processId
				},    
				success : function(msg) {  
					if(msg == '10000'){
						alert("操作成功");
						window.location.reload();	
					}else if(msg == '10001'){
						alert("传入的唯一id不正确，请联系管理员");
					}
					
				}
			});
			
		}
		
		function openForm(processId,itemId,formId,favourite,isHaveChild,process_title,nowProcessId){
	         var url = '${curl}/table_openOverForm.do?nowprocessId='+nowProcessId+'&favourite='+favourite+'&isHaveChild='+isHaveChild+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
			 openLayerTabs(processId,screen.width,screen.height,process_title,url);
		}

		//查询当前节点人员组内除本身以外的人员
		function pushDb(nodeId,processId){
				var ret = window.showModalDialog('${ctx}/group_getInnerOtherUsers.do?nodeId='+nodeId+'&processId='+processId+'&t='+new Date(),null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no;scroll:no");
				if(ret == 'success'){
					alert("推送成功！");
					window.location.href="${ctx}/table_getPendingList.do";
				}else if(ret == 'fail'){
					alert("推送失败,请联系管理员！");
				}
		}	
		
		//发起续办
		function openContinueForm(contiuneItemId, origProcId, origInstanceId){
			var lcId = '';
			// 根据itemId获取workflowId
			$.ajax({
				url : '${ctx}/item_getWfIdByItemId.do',
				type : 'POST',
				cache : false,
				async : false,
				data : {
					'ids':contiuneItemId
				},
				error : function() {
					alert('AJAX调用错误:item_getWfIdByItemId');
				},
				success : function(ret) {
					if (ret != '') {
						lcId = ret;
					}
				}
			});
			var no = '${no}';
			var status = checkStatus(lcId);
			if(status!='0'){
				var url = "${ctx}/table_openFirstForm.do?workflowid="+lcId+"&itemid="+contiuneItemId+"&no="+no + "&isContinue=yes" + "&origProcId=" + origProcId + "&origInstanceId=" + origInstanceId +'&t='+new Date();
				var ret = window.showModalDialog(url,null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;scroll:no");
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

		function openLayerTabs(processId,width,height,title,url){
			parent.topOpenLayerTabs(processId,1200,600,title,url);
		}
		
		function addCollection(instanceId,workFlowId,id,id2){
			$.ajax({  
				url : 'table_favourite.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_favourite.do)');
				},
				data : {
					instanceId : instanceId,
					workFlowId : workFlowId
				},
				success : function(msg) {
					if(msg == '1'){
						alert("已收藏！");
						return false;
					}else if(msg == '2'){
						alert("收藏成功！");
						$("#"+id2).css('display','');
					    $("#"+id).css('display','none');
					}else if(msg == '3'){
						alert("收藏失败");
					}
				}
			});
		}
		
		function deleteCollection(dofileId,id,id2){
			$.ajax({  
				url : 'table_deletefavourite.do',
				type : 'POST',   
				cache : false,
				async : false,
				data : {
					dofileId : dofileId
				},
				success : function(msg) {
					if(msg == '1'){
						alert("已取消收藏！");
						$("#"+id).css('display','');
					    $("#"+id2).css('display','none');
					}else if(msg == '2'){
						
					}
				}
			});
		}
	</script>
</html>