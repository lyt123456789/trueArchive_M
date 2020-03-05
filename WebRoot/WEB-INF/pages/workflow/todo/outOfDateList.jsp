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
			<form name="ovelist"  id="ovelist" action="${ctx }/tableExtend_getOutOfDateList.do" method="post">
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
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/tableExtend_getOutOfDateList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
			    	<tr>
			    		<th width="5%">预警</th>
			    		<th width="6%">序号</th>
						<th>标题</th>
						<th width="10%">提交部门</th>
						<th width="8%">提交人</th>
						<th width="15%">办件所有人</th>
						<th width="12%">事项类别</th>
			    	</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			  		<tr>
			  			<td align="center">
				  			<c:choose>
				  				<c:when test="${item.status eq '3'}">
				  					<img src="${ctx}/images/redLight.png" />
				  				</c:when>
				  				<c:when test="${item.status eq '4'}">
				  					<img src="${ctx}/images/yellowLight.png" />
				  				</c:when>
				  				<c:otherwise>
				  					<img src="${ctx}/images/greenLight.png" />
				  				</c:otherwise>
				  			</c:choose>
			  			</td>
						<td align="center">${(selectIndex-1)*pageSize+status.count }</td>
						<td align="left" title="${item.title }" >
							<span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
								<a href="#" onclick="openForm('${item.processUid}','','','','','${item.title}');">
									<c:choose>  
										<c:when test="${fn:length(item.title) > 24}">  
											<c:out value="${fn:substring(item.title, 0, 24)}..." />  
										</c:when>  
			   							<c:otherwise>
			      							<c:out value=" ${item.title}" />  
			    						</c:otherwise>  
									</c:choose>
								</a>
							</span>
							<c:if test="${item.shipinstanceId != null && item.shipinstanceId != ''}"><img src="${ctx}/images/ycb.jpg" /></c:if>
							<c:if test="${item.shipinstanceId == null || item.shipinstanceId == ''}"><img src="${ctx}/images/wcb.jpg" /></c:if>
						</td>
						
						<td align="center" title="${item.depName }">${item.depName}</td>
						<td align="center" title="${item.fromUserName }">${item.fromUserName}</td>
						<td align="center" title="${item.username }">${item.username}</td>
						
						<td align="center" title="${item.itemName }">${item.itemName}</td>
						
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
			skipUrl="<%=request.getContextPath()%>"+"/tableExtend_getOutOfDateList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('ovelist');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		function openForm(processId,itemId,formId,favourite,isHaveChild,process_title){
	         var url = '${curl}/table_openOverForm.do?favourite='+favourite+'&isHaveChild='+isHaveChild+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
			 openLayerTabs(processId,screen.width,screen.height,process_title,url);
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