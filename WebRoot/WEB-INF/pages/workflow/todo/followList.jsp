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
			<form name="ovelist"  id="ovelist" action="${ctx }/tableExtend_getFollowList.do" method="post">
				<input type="hidden" name="status" id="status" value="${status}">
				<input type="hidden" name="owner" id="owner" value="${owner}">
	            <label class="wf-form-label" for="">标题：</label>
	            <input type="text" class="wf-form-text" id="wfTitle" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
				<label class="wf-form-label" for="">事项类别：</label>
	            <select class="wf-form-select" id="itemName" onchange="changeCondition()" name="itemName">
	                <option value=""></option>
	                <c:forEach var="m" items='${myPendItems}'>
		 				<option value="${m.vc_sxmc}" <c:if test="${itemName ==m.vc_sxmc}">selected="selected"</c:if>>${m.vc_sxmc}</option>
		 			</c:forEach> 
	            </select>
				<input type="hidden" name="itemid" class="filejieshu-text" value="${itemid}" />
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/tableExtend_getFollowList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
			    	<tr>
			    		<th width="6%">序号</th>
						<th>标题</th>
						<th width="15%">事项类别</th>
						<th width="10%"  align="center">当前步骤</th>
						<th width="8%">发送人</th>
						<th width="11%">办理时间</th>
						<th width="10%">下一节点人员</th>
						<th width="6%" >状态</th>
						<th width="6%">收藏</th>
			    	</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			  		<tr>
						<td align="center">${(selectIndex-1)*pageSize+status.count }</td>
						<td align="left" title="${item.process_title }" >
							<c:if test="${('1' eq item.isHaveChild)}">
								<span style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
									<c:if test="${!('1' eq item.isChildWf)}"><span class="wf-badge-main">主</span></c:if>
					   			 	<c:if test="${('1' eq item.isChildWf)}"><span class="wf-badge-sub">子</span></c:if>
								</span>
							</c:if>
							<span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
								<a href="#" onclick="openForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}','${item.favourite}','${item.isHaveChild}','${item.process_title}');">
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
						<td align="center">${item.item_name}</td>
						<td align="center">${item.nodeName}</td>
						<td align="center">${item.employee_name }</td>
						<td align="center">${fn:substring(item.finish_time,0,16)}&nbsp;</td>
						<td align="center" title="${item.entrust_name}"><span class="tw-text-hide">${item.entrust_name}</span></td>
						<td align="center">
							<c:if test="${item.status == '0'}">
								<span class="wf-badge-doing">办</span>
							</c:if>
							<c:if test="${item.status == '1'}">
								<span class="wf-badge-over">结</span>
							</c:if>
						</td>
						<td align="center">
							<c:choose>
								<c:when test="${item.favourite == '1'}">
									<a class="wf-btn-orange" href="#">
										<i class="wf-icon-star"></i>
									</a>
								</c:when>
								<c:otherwise>
								</c:otherwise>
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
			skipUrl="<%=request.getContextPath()%>"+"/tableExtend_getFollowList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('ovelist');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};

		
		function openForm(processId,itemId,formId,favourite,isHaveChild,process_title){
	         var url = '${curl}/table_openOverForm.do?status=${status}&favourite='+favourite+'&isHaveChild='+isHaveChild+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
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
	</script>
</html>