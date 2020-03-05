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
			<form name="dofileList"  id="dofileList" action="${ctx}/business_getCyDoFileList.do" method="post">
				<input type="hidden" name="itemName" value="${itemName}"/>
				<input type="hidden" name="status" value="${status}"/>
	            <label class="wf-form-label" for="">办件标题：</label>
	            <input type="text" class="wf-form-text" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/table_getDoFileList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    		<tr>
						<th width="5%"><input id='chc_all' type='checkbox' onclick='checkAllchc()'/></th>
						<th align="center" width="5%">序号</th>
						<th width="30%">办件标题</th>
						<th width="15%">事项类别</th>
						<th width="15%">当前步骤</th>
						<th width="10%">办件状态</th>
						<th width="25%">办理时间</th>
						<c:if test="${redirect =='1' }">
							<th  width="10%">操作</th>
						</c:if>
						<c:if test="${favourite=='1'}">
							<th  width="10%">操作</th>
						</c:if>
		        	</tr>
		    	</thead>
		    	<c:forEach items="${doFileList}" var="doFile" varStatus="status">
			    	<tr>
					<td align="center"><input type="checkbox" name="typeid" value="${doFile.doFile_id}"/></td>
					<td>${(selectIndex-1)*pageSize+status.count}</td>
					<td style="text-align: left; padding-left: 10px;" title="${doFile.doFile_title}"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
					 <a href="#" onclick="openForm('${doFile.processId}','${doFile.itemId}','${doFile.formId}','${favourite}','${doFile.doFile_title}');">
						<c:choose>  
							<c:when test="${fn:length(doFile.doFile_title) > 25}">  
							<c:out value="${fn:substring(doFile.doFile_title, 0, 25)}.." />  
							</c:when>  
							<c:otherwise>  
							<c:out value=" ${doFile.doFile_title}" />  
							</c:otherwise>  
						</c:choose></a>
						</span>
						</td>
						<td>${doFile.itemName}</td>
						<td align="center">${doFile.nodeName}</td>
						<td align="center">
							<c:if test="${doFile.doFile_result==0}">
								<span class="wf-badge-not">未</span>
							</c:if>
							<c:if test="${doFile.doFile_result==1}">
								<span class="wf-badge-doing">办</span>
							</c:if>
							<c:if test="${doFile.doFile_result==2}">
								<span class="wf-badge-over">结</span>
							</c:if>
						</td>
						<td>${fn:substring(doFile.do_time,0,16)}</td>
						<c:if test="${redirect =='1' }">
							<td><a href="#" onclick="redirect('${doFile.instanceId}');">重定向</a></td>
						</c:if>
						<c:if test="${favourite=='1'}">
							<td><a href="#" onclick="deleteCollection('${doFile.instanceId}','${doFile.workflowId}');">取消</a></td>
						</c:if>
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
			skipUrl="<%=request.getContextPath()%>"+"/business_getCyDoFileList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('dofileList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
</script>
<script type="text/javascript">
	var ids="";
	function checkAllchc(){ 
		var chc_all=document.getElementById('chc_all');
		var chcs=document.getElementsByName('typeid');
		for(var i=0;i<chcs.length;i++){
			if(chc_all.checked==true){
				chcs[i].checked=true;
			}else{
				chcs[i].checked=false;
			}
		}
	}
	
	function shanchu(){
		var objs = document.getElementsByTagName('input');
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='typeid' && objs[i].checked==true ){
			  ids += objs[i].value+",";
		   }
		}
		if(ids==""){
			alert("请选择一条办件");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		if(confirm('确定删除所选办件吗')){
			//异步获取上传成功后的doc信息
			$.ajax({
			        async:true,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"text",//返回值类型
			        data:{"id":ids},
			        url:"${ctx}/table_falseDeleteDoFile.do",
			        success:function(text){
			        	 window.location.reload();
			        }
			    });
		}
	}
	function openForm(processId,itemId,formId,favourite,process_title){
		if(processId!=''){
		    var url = '${curl}/table_openOverForm.do?favourite='+favourite+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
		    openLayerTabs(processId,screen.width,screen.height,process_title,url);
		}
	}
	
	function redirect(instanceId){
		window.location.href="${ctx}/table_getRedirectDetail.do?instanceId="+instanceId;
	}
	
	function openLayerTabs(processId,width,height,title,url){
	   parent.topOpenLayerTabs(processId,1200,600,title,url);
	}	
</script>
</html>