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
			<form name="leaderList"  id="leaderList" action="${ctx}/employeeLeader_getDepartmentLeaderList.do" method="post">
	            <a class="wf-btn" href="javascript:toAdd();" target="_self">
	                <i class="wf-icon-plus-circle"></i> 新建
	            </a>
	            <a class="wf-btn-primary" href="javascript:toEdit();">
	                <i class="wf-icon-pencil"></i> 修改
	            </a>
	            <a class="wf-btn-danger" href="javascript:del()" target="_self" title="确定要删除吗？" warn="请选择一个对象">
	                <i class="wf-icon-minus-circle"></i> 删除
	            </a>
	            <label class="wf-form-label" for="">领导姓名：</label>
	            <input type="text" class="wf-form-text" name="leaderName" value="${leaderName}" placeholder="输入关键字">
	            <label class="wf-form-label" for="">人员姓名：</label>
	            <input type="text" class="wf-form-text" name="employeeName" value="${employeeName}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="post" name="thisForm" action="${ctx}/employeeLeader_getDepartmentLeaderList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	<tr>
		    		<th width="3%">
		    			<input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
		    		<th width="5%">序号</th>
			    	<th width="15%">部门</th>
			    	<th width="10%">人员姓名</th>
			    	<th width="10%">人员类型(职务)</th>
			    	<th width="5%">排序号</th> 
			    	<th ></th> 
		    	</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
			    	<tr target="sid_user" rel="1">
			    		<td align="center"><input type="checkbox" name="selid" value="${item.id}" ></td>
	    				<td align="center" itemid="${item.id}" >${(selectIndex-1)*pageSize+status.count}</td>
	    				<td align="center">${item.depName}</td>
	    				<td align="center">${item.leaderName}</td>
	    				<td align="center">${item.empType}</td>
	    				<td align="center">${item.sortId}</td>
	    				<td></td>
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
			skipUrl="<%=request.getContextPath()%>"+"/employeeLeader_getDepartmentLeaderList.do";			//提交的url,必须修改!!!*******************
			submitForm=document.getElementById('leaderList');									//提交的表单,必须修改!!!*******************
			innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
			
			MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
			pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
			sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
			if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		};
		
		//跳转新增页面
		function toAdd(){
			window.location.href = "${ctx}/employeeLeader_toAdd.do";
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
			}else {
				var id=$(".selected td:eq(1)").attr("itemid");
				if(id!=null && id!=''){
					ids = id;
				}else{
					alert('请选择一个删除对象');
					return;
				}
			}
			if(confirm("确定要删除吗？")){
				//ajax调用
				$.ajax({
					url : '${ctx}/employeeLeader_deleteEmployeeLeader.do?ids='+ids,
					type : 'POST',  
					cache : false,
					async : false,
					error : function() {
					alert('AJAX调用错误(employeeLeader_deleteEmployeeLeader.do)');
						return false;
					},
					success : function(result) {   
						var res = eval("("+result+")");
	   					if(res.success){
	   						alert("删除成功");
	   						window.location.href = "${ctx}/employeeLeader_getDepartmentLeaderList.do";
	   					}else{
	   						alert("删除失败");
	   					}
					}
				});
			}
		}
		
		//编辑修改
		function toEdit(){
			var id=$(".wf-actived td:eq(1)").attr("itemid");
			if(id){
				location.href = "${ctx}/employeeLeader_toUpdate.do?id="+id;
			}else{
				alert('请选择要修改的信息！');
			}
		}
	</script>
</html>