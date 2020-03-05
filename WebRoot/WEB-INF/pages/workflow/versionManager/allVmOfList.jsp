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
</head>
<body>
    <div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/vm_toAddVm.do" target="_self"><span>新建</span></a></li>
			<li><a class="delete" href="javascript:del()" target="_self" title="确定要删除吗？" warn="请选择一个对象"><span>删除</span></a></li>
			<li><a class="edit" href="javascript:xg_row();" ><span>修改</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<div class="pageHeader" >
	<form name="doVmList"  id="doVmList" action="${ctx}/vm_getAllVMList.do?isAdmin=${isAdmin}" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					更新内容：<input type="text" name="content" value="${content}"/>
				</td>
				<td>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div id="pageContent" class="pageContent"  style="overflow:auto;">
	<form id="thisForm" method="POST" name="thisForm" action="${ctx}/vm_getAllVMList.do?isAdmin=1" >
    	<div id="w_list_print">
    		<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
		    	<thead>
		    	<tr>
		    		<th width="3%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
		    		<th width="3%">序号</th>
			    	<th width="5%">版本号</th>
			    	<th width="30%">更新内容</th>
			    	<th width="10%">已应用项目</th> 
			    	<th width="10%">更新日期</th>
		    	</tr>
		    	</thead>
	    		<c:forEach items="${list}" var="d" varStatus="n">
			    	<tr target="sid_user" rel="1">
			    		<td align="center">
			    			<input type="checkbox" name="selid" value="${d.id}" >
			    		</td>
				    	<td align="center" vmid="${d.id}">
				    		${(selectIndex-1)*pageSize+n.count}
				    	</td>
				    	<td align="center" class="workflowidtitle" workflownnameid="1">
				    		${d.num}
				    	</td>
				    	<td  class="workflowidtitle" workflownnameid="1">
				    		${d.content}
				    	</td>
				    	<td  class="workflowidtitle" workflownnameid="1">
				    		${d.applyProject}
				    	</td>
				    	<td  align="center">
				    		${fn:substring(d.updateDate,0,16)} 
				    	</td>
			    	</tr>
		    	</c:forEach>
    		</table>
    	</div>
    	<div class="formBar pa" style="bottom:0px;width:100%;">  
			<div id="div_god_paging" class="cbo pl5 pr5"></div> 
		</div>
	</form>
</div>
</body>
   	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js" defer="defer"></script>
	<script type="text/javascript" src="${ctx }/widgets/common/js/god_paging.js" defer="defer"></script>
	<script type="text/javascript">
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			skipUrl="<%=request.getContextPath()%>"+"/vm_getAllVMList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('doVmList');									//提交的表单,必须修改!!!*******************
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

		function xg_row(){
			 var id=$(".selected td:eq(1)").attr("vmid");
			 if(id){
			 	location.href = "${ctx}/vm_toEditVm.do?id="+id+"&isAdmin=${isAdmin}";
			 }else{
				alert('请选择要修改的组！');
		     }
		}
	
		function del(){
			if(confirm("确定要删除吗？")){
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
				//ajax调用
				$.ajax({
					url : '${ctx}/vm_delVm.do?ids='+ids,
					type : 'POST',  
					cache : false,
					async : false,
					error : function() {
						alert('AJAX调用错误(vm_delVm.do)');
						return false;
					},
					success : function(msg) {   
						if(msg =='success'){
							window.location.href = '${ctx}/vm_getAllVMList.do' ;
						}
					}
				});
			}
		}
	</script>
	<%@ include file="/common/function.jsp"%>
		<script>
		$('#pageContent').height($(window).height()-60);
	</script>
</html>