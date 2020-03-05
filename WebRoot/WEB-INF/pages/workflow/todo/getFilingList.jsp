<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<form id="chaxunform" name="chaxunform" action="${ctx}/filing_getFilingList.do"  method="post" >
		<input type="hidden" name="itemid" id="itemid" value="${itemid}"/>
		<div class="wf-list-top">
			<div class="wf-search-bar">
	 	       		<label class="wf-form-label" for="">标题：</label>
	 	       		<input type="text" class="wf-form-text"  id="title" name="title" value="${title }" placeholder="输入关键字"/>
	 	        	<label class="wf-form-label" for="">文号：</label>
	 	        	<input type="text" class="wf-form-text" id="wh" name="wh" value="${wh}" placeholder="输入关键字"/>
	                <label class="wf-form-label" for="">归档时间：</label>
	                <input type="text" class="wf-form-text wf-form-date" id="timeFrom" name="timeFrom" value="${timeFrom}"  placeholder="输入日期" />
					    至            
					<input type="text" class="wf-form-text wf-form-date" id="timeTo" name="timeTo"  value="${timeTo}"   placeholder="输入日期" />
		            <button class="wf-btn-primary" onclick="document.forms.chaxunform.submit();">
		                <i class="wf-icon-search"></i> 搜索
		            </button>
			</div>
		</div>
		<div class="wf-list-wrap">
				<table class="wf-fixtable" layoutH="140">
					<thead>
			    	  	<tr>
							<th width="5%" >序号</th>
							<th width="25%" >标题</th>
							<th width="25%" >文号</th>
							<th width="20%" >文种</th>
							<th width="25%" >归档日期</th>
						</tr>
			    	</thead>
			    	<c:forEach items="${filings}" var="filing" varStatus="status">
						<tr style="height:40px"> 
							<td align="center">${(selectIndex-1)*pageSize+status.count}</td>
							<td align="left" style="text-align: left;">
								<a href="#" onclick="openForm('${filing.processId}','${filing.itemId}','${filing.formId}');">${filing.title}</a>
							</td>
							<td align="center">${filing.wh }</td>
							<td align="center">
								<c:choose>
									<c:when test="${filing.type eq '0'}">公文(发文)</c:when>
									<c:when test="${filing.type eq '1'}">公文(办文)</c:when>
								</c:choose>
							</td>
							<td align="center">${filing.filedTime }</td>
						</tr>
					</c:forEach> 
				</table>
		</div>
		<div class="wf-list-ft" id="pagingWrap">
		</div>
		</form>
	</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
function openForm(processId,itemId,formId){
	 var ret = window.showModalDialog('${ctx}/table_openOverForm.do?processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no;");
	 if(ret == "refresh"){
     window.location.reload();
	 }
}

window.onload=function(){ 
	//获得后台分页相关参数-必须
	skipUrl="<%=request.getContextPath()%>"+"/filing_getFilingList.do";			//提交的url,必须修改!!!*******************
	submitForm=document.getElementById('chaxunform');									//提交的表单,必须修改!!!*******************
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
$('#pageContent').height($(window).height()-110);
function checkProcessId(processId){
	var processStr = "";
	$("[check_process]").each(function(index){
		processStr = processStr + ($(this).attr("check_process")+",");
	});
	if(processStr!=""){
		processStr = processStr.substring(0,processStr.length-1);
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{'processStr':processStr},
	        url:"${ctx}/filing_checkFiling.do",
	        success:function(text){
				if(text!=""){
					var processIds = new Array();
					processIds = text.split(",");
					for(var i=0;i<processIds.length;i++){
						$("[check_process='"+processIds[i]+"']").hide();
					}
				}
	        },
	        error:function(){
	        	alert("ajax调用失败");
	        }
	    });
	}
}
</script>
</html>