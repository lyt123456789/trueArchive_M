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
			<form name="pressList"  id="pressList" action="${ctx }/business_getPressMsgList.do" method="post">
 	        <input type="hidden" name="isAdmin"  value="${isAdmin}" />
 	        	<label class="wf-form-label" for="">标题：</label>
                <input type="text" class="wf-form-text" id="wfTitle" name="title" value="${title}" placeholder="输入关键字">
                <label class="wf-form-label" for="">办理人员：</label>
                <input type="text" class="wf-form-text" id="wfUserName" name="wfUserName" value="${wfUserName}" placeholder="输入关键字">
                <label class="wf-form-label" for="">提交时间：</label>
                <input type="text" class="wf-form-text wf-form-date" id="commitTimeFrom" name="commitTimeFrom" value="${commitTimeFrom}"  placeholder="输入日期" value="">
				    至            
				<input type="text" class="wf-form-text wf-form-date" id="commitTimeTo" name="commitTimeTo"  value="${commitTimeTo}"   placeholder="输入日期" value="">
				
				<label class="wf-form-label" for="">事项类别：</label>
                <select class="wf-form-select" id="itemId" name="itemId">
                	<option></option>
					<c:forEach var="item" items="${itemList}">
						<option value="${item.id }"  <c:if test="${item.id== itemId}">selected</c:if> >${item.vc_sxmc}</option>
					</c:forEach>
                </select>
			    <input type="hidden" name="itemid" class="filejieshu-text" value="${itemid}" />
	            <button class="wf-btn-primary" onclick="search();">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/business_getPressMsgList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
                       	<th width="30px">序号</th>
						<th>标题</th>
						<th width="100px" >办理部门</th>
						<th width="100px" >办理人员</th>
						<th width="120px">提交时间</th>
						<th width="120px">步骤期限</th>
						<th width="100px">事项类别</th>
						<th width="100px">是否超期</th>
						<th width="100px">催办信息</th>
                    </tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td style="text-align: left;" title="${item.process_title}">
							<span >
								<a href="#" onclick="openPendingForm('${item.wf_process_uid}','${item.item_id}','${item.form_id}',
										'${item.isCanPush}','${item.process_title}');">
								${item.process_title}
								</a>
							</span>
						</td>
						<td  align="center">
							${item.userDeptId }
						</td>
						<td  align="center">
							${item.userName }
						</td>
						<td align="center">${fn:substring(item.apply_time,0,16) }</td>
						<td align="center">${fn:substring(item.jdqxDate,0,16) }</td>
						<td  align="center">${item.item_name}</td>
						<td align="center">
							<c:if test="${item.status == '0'}">
								<span class="wf-badge-doing">办</span>
							</c:if>
							<c:if test="${item.status == '1'}">
								<span class="wf-badge-doing">办</span>
							</c:if>
							<c:if test="${item.status == '3'}">
								<span class="wf-badge-over">超</span>
							</c:if>
						</td>
						<td  align="center" title="${item.presscontent }">
							${item.presscontent }
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
			skipUrl="<%=request.getContextPath()%>"+"/business_getPressMsgList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('pressList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		function checkTime(){
			var commitTimeFrom = document.getElementById("commitTimeFrom").value;
			var commitTimeTo = document.getElementById("commitTimeTo").value;
			if ((""!=commitTimeFrom&&""!=commitTimeTo)&&commitTimeFrom > commitTimeTo){
				alert("开始时间不能大于结束时间！");
				document.getElementById("commitTimeFrom").value="";
				document.getElementById("commitTimeTo").value="";
			}
		}
		
		function search(){
			document.forms.pressList.action="${ctx}/business_getPressMsgList.do";
			document.forms.pressList.submit();
		}
		
		
		function openPendingForm(processId,itemId, formId, isCanPush,process_title){
			//1,断定目前处理人员；ajax
			openForm(processId,itemId,formId,isCanPush,process_title);
							
		}
		
		function openForm(processId,itemId,formId,isCanPush,process_title){
			var url = '${ctx}/table_openOverForm.do?bjFlag=1&processId='+processId+'&isDb=true&itemId='+itemId+'&formId='+formId+'&isCanPush='+isCanPush+'&t='+new Date();
			openLayerTabs(processId,screen.width,screen.height,process_title,url);
		}
		
		function openLayerTabs(processId,width,height,title,url){
			   parent.topOpenLayerTabs(processId,1200,600,title,url);
		}	

	</script>
</html>