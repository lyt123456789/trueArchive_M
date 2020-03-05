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
.tw-btn-green {
  padding: 4px 8px;
  font-size: 15px;
  color: #fff;
  background-color: #5eb95e;
  border-color: #5eb95e;
}

</style>
</head>
<body>
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
		  <form name="doItemList"  id="doItemList" action="${ctx}/monitor_getNoOwnerDofileList.do" method="post">
			<div class="wf-top-tool">
	         </div>
	           <a class="tw-btn-green" href="javascript:activeChecked()" title="确定要激活吗？" warn="请选择一个对象">
	                <i class="wf-icon-pencil"></i> 批量激活
	            </a>
	           <label class="wf-form-label" for="">所属站点：</label>
               <select class="wf-form-select" id="departmentGuid" name="departmentGuid">
                	<option value=""></option>
                	<c:forEach var="m" items='${depts}'>
	 					<option value="${m.departmentGuid}" <c:if test="${departmentGuid ==m.departmentGuid}">selected="selected"</c:if>>${m.departmentName}</option>
	 				</c:forEach> 
                </select> 
	            <label class="wf-form-label" for="">标题：</label>
	            <input type="text" class="wf-form-text" name="wfTitle" value="${wfTitle}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/monitor_getNoOwnerDofileList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    		<tr>
		    		<th width="5%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
						<th width="5%" >序号</th>
						<th width="18%">办件标题</th>
						<th width="14%" >最终办件消失时间</th>
						<th width="14%" >最终办理人员</th>
						<th width="14%" >所属站点</th>
						
						<th  width="10%" >操作</th>
					</tr>
		    	</thead>
		    	<c:forEach items="${noOwnerDofileList}" var="doFile" varStatus="status">
			    	<tr>
			    	    <td align="center">
			    			<input type="checkbox" name="selid" value="${doFile[1]}" >
			    		</td>
						<td style="text-align:center;">${(selectIndex-1)*pageSize+status.count}</td>
						<td style="text-align: left; padding-left: 10px;" title="${doFile[0]}">
							<a href="#" onclick="openForm('${doFile[1]}','','','','${doFile[0]}');"><c:out value=" ${doFile[0]}" /></a>
						</td>
						<td style="text-align:center;">${doFile[6]}</td>
						<td style="text-align:center;">${doFile[7]}</td>
						<td style="text-align:center;">${doFile[8]}</td>
						
						<td align="center"> <a class="tw-btn-green" onclick="activeDofile('${doFile[1]}');" >
	                <i class="wf-icon-pencil"></i> 激活
	            </a></td>
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
			skipUrl="<%=request.getContextPath()%>"+"/monitor_getNoOwnerDofileList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('doItemList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
</script>
<script type="text/javascript">
function openForm(processId,itemId,formId,favourite,process_title){
	if(processId!=''){
		 var url = '${curl}/table_openOverForm.do?favourite='+favourite+'&processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date();
		 openLayerTabs(processId,screen.width,screen.height,process_title,url);
	}
}

function activeDofile(processId){
	$.ajax({  
		url : '${ctx}/monitor_activeNoOwnerDofile.do',
		type : 'POST',   
		cache : false,
		data:{"processId":processId},
		async : false,
		error : function() {  
			alert('AJAX调用错误(monitor_activeNoOwnerDofile.do)');
		},
		success : function(msg) {
			if(msg == 'true'){
				alert("激活成功！");
				window.location.href = '${ctx}/monitor_getNoOwnerDofileList.do';
			}
		}
	});
}

function openLayerTabs(processId,width,height,title,url){
   parent.topOpenLayerTabs(processId,1200,600,title,url);
}	

function sel(){
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

function activeChecked(){
	
		var selid = document.getElementsByName('selid');
		var processIds = "";
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].checked){
				processIds += selid[i].value + ",";
			}
		}
		if(processIds.length > 0){
			processIds = processIds.substring(0, processIds.length - 1);
		}else {
			alert('请选择一个激活对象');
			return;
		}
		//ajax调用
		$.ajax({
			url : '${ctx}/monitor_activeCheckedDofile.do?processIds='+processIds,
			type : 'POST',  
			cache : false,
			async : false,
			error : function() {
			alert('AJAX调用错误(monitor_activeCheckedDofile.do)');
				return false;
			},
			success : function(msg) {   
				if(msg =='success'){
					alert("批量激活成功！")
					window.location.href = '${ctx}/monitor_getNoOwnerDofileList.do' ;
				}
			}
		});
}

</script>
</html>