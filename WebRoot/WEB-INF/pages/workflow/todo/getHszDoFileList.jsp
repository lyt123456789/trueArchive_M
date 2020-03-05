<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
			<form name="doFileList" id="doFileList" action="${ctx }/table_getHszDoFileList.do" method="post">
			<input type="hidden" name="itemName" value="${itemName}"/>
			<input type="hidden" name="itemId" value="${itemId}"/>
			<div class="wf-top-tool">
	            <a class="wf-btn-primary" href="javascript:huifu();">
	                <i class="wf-icon-pencil"></i> 恢复
	            </a>
	            <a class="wf-btn-danger" href="javascript:chedishanchu();">
	                <i class="wf-icon-minus-circle"></i> 彻底删除
	            </a>
	        </div>
	            <label class="wf-form-label" for="">标题：</label>
	            <input type="text" class="wf-form-text"  name="wfTitle" value="${wfTitle}" placeholder="输入关键字"/>
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getHszDoFileList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	<tr>
		    		<th width="5%"   style="font-weight:bold;text-align:center;"><input id='chc_all' type='checkbox' onclick='checkAllchc()'/></th>
					<th width="30%" style="font-weight:bold;text-align:center;">办件标题</th>
					<th width="15%" style="font-weight:bold;text-align:center;">事项类别</th>
					<th width="15%" style="font-weight:bold;text-align:center;">当前步骤</th>
					<th width="10%" style="font-weight:bold;text-align:center;">办件状态</th>
				<!-- 	<th width="15%" style="font-weight:bold;text-align:center;">办理时间</th> -->
				    <th width="15%" style="font-weight:bold;text-align:center;">删除人</th>
					<th ></th>
		    	</tr>
		    	</thead>
		    	<tbody>
					<c:forEach items="${doFileList}" var="doFile" varStatus="status">
						<tr> 
							<td style="text-align:center;"><input type="checkbox" name="typeid" value="${doFile.doFile_id}"/></td>
							<td style="text-align:left;" title="${doFile.doFile_title}"><span style="width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
							<c:choose>  
  											<c:when test="${fn:length(doFile.doFile_title) > 25}">  
      										<c:out value="${fn:substring(doFile.doFile_title, 0, 25)}.." />  
  											</c:when>  
 												<c:otherwise>  
    											<c:out value=" ${doFile.doFile_title}" />  
  											</c:otherwise>  
									</c:choose>
							</span></td>
							<td style="text-align:left;">${doFile.itemName}</td>
							<td align="center">${doFile.nodeName}</td>
							<td style="text-align:center;"><c:if test="${doFile.doFile_result==0}">未办理</c:if><c:if test="${doFile.doFile_result==1}">办理中</c:if><c:if test="${doFile.doFile_result==2}">已办结</c:if><c:if test="${doFile.doFile_result==3}">已作废</c:if></td>
							<td style="text-align:center;">${doFile.delUserName}</td>
							<td ></td>
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
	<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/table_getHszDoFileList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
			$('.wf-layout').attr('style','height:100%');
		};
	</script>
<script>
var ids="";
function dianji(id){
	ids =id;
}

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


function huifu(){
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
	
	if(confirm('确定恢复所选办件吗')){
		//异步获取上传成功后的doc信息
		$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"text",//返回值类型
		        data:{"id":ids},
		        url:"${ctx}/table_recoverDoFile.do",
		        success:function(text){
		        	 window.location.reload();
		        }
		    });
	}

}
function chedishanchu(){
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
		        url:"${ctx}/table_deleteDoFile.do",
		        success:function(text){
		        	 window.location.reload();
		        }
		    });
	}
}
function openForm(processId,itemId,formId){
	 var ret = window.showModalDialog('${ctx}/table_openOverForm.do?processId='+processId+'&itemId='+itemId+'&formId='+formId+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
	 if(ret == "refresh"){
      	window.location.reload();
	 }
}

</script>
</html>
