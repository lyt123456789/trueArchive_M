<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	 <link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	 <script type="text/javascript">
	 	function deleteDup(){
	 		var selid = document.getElementsByName('selid');
			var ids = "";
			for(var i = 0 ; i < selid.length; i++){
				if(selid[i].checked){
					ids += selid[i].value + ",";
				}
			}
			if(ids.length > 0){
				ids = ids.substring(0, ids.length - 1);
			}else {
				alert('请选择一个删除对象');
				return;
			}
			if(!confirm("是否确认删除")){
				return;
			}
			location.href = "${ctx}/table_deleteDuplicate.do?ids="+ids;
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
	
	 </script>
</head>		
<body>
<form id="pagerForm" method="post" action="w_list.html">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>
<div class="pageHeader" >
	<form name="pendingList" id="pendingList" action="${ctx }/table_getDuplicateList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					标题：<input type="text" name="wfTitle" />
				</td> 
				<td>
					事项类别：<input type="text" name="itemName" />
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="panelBar">
		<ul class="toolBar"> 
			<li><a class="delete" href="javascript:deleteDup()" target="_self" title="确定要删除吗？" warn="请选择一个对象"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
<div id="pageContent" class="pageContent"  style="overflow:auto;">
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getDuplicateList.do" >
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="5%">
					    <input type="checkbox" name="selAll" id="selAll" onclick="sel();">
					</th>
				<th width="5%" align="center">序号</th>
				<th width="25%" align="center">标题</th>
				<th align="center" width="20%" >事项类别</th>
				<th width="15%" align="center">流程发起人</th>
				<th width="15%" align="center">发送人</th>
				<th width="15%" align="center" >接收时间</th>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="item" items="${list}" varStatus="status"> 
			<tr>
				<td align="center">
			    	<input type="checkbox" name="selid" value="${item.wf_process_uid}" >
			    </td>
				<td align="center">${(selectIndex-1)*pageSize+status.count }</td>
				<td align="left"><a href="${ctx }/table_openOverForm.do?processId=${item.wf_process_uid}">
				<c:choose>  
    											<c:when test="${fn:length(item.process_title) > 25}">  
        										<c:out value="${fn:substring(item.process_title, 0, 25)}.." />  
    											</c:when>  
   												<c:otherwise>  
      											<c:out value=" ${item.process_title}" />  
    											</c:otherwise>  
											</c:choose>
				</a></td>
				<td align="left">${item.item_name }</td>
				<td align="center">${item.owner }</td> 
				<td align="center">${item.employee_name }</td>
				<td align="center">${fn:substring(item.apply_time,0,16) }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</form>
</div>
<div class="formBar pa" style="bottom:0px;width:100%;">  
 <div id="div_god_paging" class="cbo pl5 pr5"></div> 
</div> 
</body>
<%@ include file="/common/function.jsp"%> 
<script>
 
 //分页相关操作代码
 window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/table_getDuplicateList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('thisForm');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	}
$("table.list", document).cssTable();

</script>
<script language="javascript" type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
<script>
		$('#pageContent').height($(window).height()-70);
</script>
</html>