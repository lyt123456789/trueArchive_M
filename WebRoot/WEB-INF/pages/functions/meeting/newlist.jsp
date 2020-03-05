<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>会议通知消息</title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	 <link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>		
<body>
 	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/meeting_toAddJsp.do" target="_self"><span>新建</span></a></li>
			<li><a class="delete" href="javascript:del()" target="_self" ><span>删除</span></a></li>
			<li><a class="edit" href="javascript:xg_row();" ><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
<div class="pageContent">
	
<form id="newList" method="POST" name="newList" action="${ctx }/meeting_newlist.do" >
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		<thead>
			<tr>
				<th width="5%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
				<th width="5%" align="center">序号</th>
				<th width="20%" align="center">会议名称</th>
				<th align="center" width="20%" >标题</th>
				<th width="15%" align="center">时间</th>
				<th width="20%" align="center">地点</th>
				<th width="10%" align="center">是否上报参会名单</th>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="item" items="${list}" varStatus="status"> 
			<tr >
				<td align="center"><input type="checkbox" name="selid" value="${item.id}" ></td>
				<td align="center">${(selectIndex-1)*10+status.count }</td>
				<td align="center">${item.meetingname }</td>
				<td align="center">${item.newtitle }</td>
				<td align="center">${item.newtime }</td>
				<td align="center">${item.arr }</td>
				<td align="center">
				<c:if test="${item.sbmd==1 }">是</c:if>
				<c:if test="${item.sbmd==0 }">否</c:if>
				</td>
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
		skipUrl="<%=request.getContextPath()%>"+"/meeting_newlist.do";				//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('newList');								//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('div_god_paging');								//分页信息填写对象,必须修改!!!*******************	
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
$("table.list", document).cssTable();
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
function del(){
		var selid = document.getElementsByName('selid');
		var ids = "";
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].checked){
				ids += selid[i].value + ",";
			}
		}
		if(ids.length > 0){
			ids = ids.substring(0, ids.length - 1);
			if(confirm("确定要删除吗？")){
				location.href = "${ctx}/meeting_del.do?ids="+ids;
			}
		}else {
			alert('请选择一个删除对象');
			return;
		}
}
function xg_row(){
	var selid = document.getElementsByName('selid');
	var ids = "";
	for(var i = 0 ; i < selid.length; i++){
		if(selid[i].checked){
			ids += selid[i].value + ",";
		}
	}
	 if(ids.length > 0){
	 	ids = ids.substring(0, ids.length - 1);
	 	if(ids.indexOf(',')>-1){
	 		alert('只能选择一个修改的对象！');
	 	}else{
	 		location.href = "${ctx}/meeting_toEditJsp.do?id="+ids;
	 	}
	 }else{
		alert('请选择要修改的对象！');
    }
}
</script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
</html>