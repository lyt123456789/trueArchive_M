<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
	<style>
	.new-htable {
		margin-top:20px;
		width:96%;
		margin-left:3%;
	}
		.new-htable tr th{
			text-align:right;
			color:#333333;
			font-size:16px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;

		}
		.new-htable tr td{
		text-align:left;
			color:#333333;
			font-size:15px;
			font-weight:normal;
			height:46px;
		    vertical-align: middle;
		}
		.new-htable .wf-form-text{
			width:354px;
			text-indent:6px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.new-htable select{
			width:163px;
			height:30px;
			border:1px solid #e6e6e6;
			border-radius:3px;
			vertical-align: middle;
		}
		.wf-form-date{
			width:133px!important;
		}
		.wf-icon-trash{
			cursor:pointer;
			color:red;
		}
		.wf-hover .wf-icon-trash{
			display:inline-block;
		}
	</style>
</head>
<body>
    <div class="wf-layout">
			<div class="wf-list-top" style="display:none;">
				<div class="wf-search-bar">
					<form action="${ctx}/siteManage_showEmpForAuth.do?depId=${depId}" method="post" name="queryForm" id="queryForm">
						<input type="hidden" id="depId" name="depId" value="${depId}" />
						<input type="hidden" id="ifsave" name="ifsave" value="" />
						<input id="userIdAndNames" name="userIdAndNames" value="${userIdAndNames }" type="hidden"></input>
					</form>
				</div>
			</div>
			<div class="wf-list-wrap">
			<div class="loading"></div><!--2017-11-10-->
				<table class="wf-fixtable" layoutH="140" style="width: 100%;" cellspacing="0">
					<thead>
	                    <tr>
	                    <th width="3%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
					    	<th width="5%">序号</th>
					    	<th width="15%">登录名</th>
					    	<th width="17%">姓名</th>
					    	<th width="32%">操作</th>
	                    </tr>
	                </thead>
					<tbody>
						<c:forEach var="item" items="${empList}" varStatus="status">
							<tr>
								<td align="center">
				    				<input type="checkbox" name="selid" value="${item.id},${item.password }" >
				    		    </td>
								<td align="center">${(selectIndex-1)*pageSize+status.count}</td>
								<td align="center" title="${item.loginName}">${item.loginName}</td>
								<td align="center" title="${item.name}">${item.name}</td>
								 
								<td align="center">
									<button class="wf-btn-primary" type="button" onclick="showEmpTree('${item.id}','${depId }');"><i class="wf-icon-arrows" ></i> 角色授予</button>
									<button class="wf-btn-green" type="button" onclick="" style="font-size: 14px; padding: 6px 8px;"><i class="wf-icon-send" ></i> 角色转移</button>
								</td>
							</tr>
						</c:forEach>
	                </tbody>
				</table>
			</div>
			<div class="tw-list-ft" id="pagingWrap">
			</div>
	</div>
</body>
<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	setTimeout("setHeight()",50);
	function setHeight(){
		$(".wf-fixtable-scroller").height($(window).height()-45-80+'px');
		$(".wf-layout").height($(window).height()-45-40+'px');
		$("body").height($(window).height()+'px');
	}
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		skipUrl="<%=request.getContextPath()%>"+"/siteManage_showEmpForAuth.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('queryForm');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
	};
	
	function toUpdate(id){
		if(id != null && id != ''){
			layer.open({
	            title: '修改人员',
	            area: ['800px', '500px'],
	            type: 2,
	            content: '${ctx}/siteManage_toUpdateEmp.do?empId='+id
	        });
		}else{
			alert("请选择人员！");
		}
	}
	
	//批量操作的checkbox方法
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
	
	function deleteEmp(id,depId){
		if(confirm("确定要删除吗？")){
			//ajax调用
			$.ajax({
				url : '${ctx}/siteManage_deleteEmp.do?siteId='+id+'&deptId='+depId,
				type : 'POST',  
				cache : false,
				async : false,
				error : function() {
				alert('AJAX调用错误(siteManage_deleteEmp.do)');
					return false;
				},
				success : function(msg) {   
					if(msg =='success'){
						alert("删除成功！");
						window.location.href = '${ctx}/siteManage_showAllEmp.do' ;
					}else{
						alert("删除人员失败！");
					}
				}
			 });
		}else{
			return;
		}
	}
	
	//将本人角色授予另一人员
	function roleToUser(){
		var url = '${curl}/ztree_showAllDeptTree.do?allEmp=1&userIds=' + userIds + '&t='+new Date();
		layer.open({
			id: 'empLayer',
            title: '选择人员',
            area: ['860px', '600px'],
            type: 2,
            content: url,
            end: function(index){
            	var userIdAndNames = $('#userIdAndNames').val();
            	alert(userIdAndNames);
            	$.ajax({
        			url : 'siteManage_saveRoleUsers.do',
        			type : 'POST',   
        			cache : false,
        			async : false,
        			data :{
        				'roleId':roleId,'userIdAndNames':userIdAndNames
        			},
        			error : function(e) {  
        				alert('AJAX调用错误(siteManage_saveRoleUsers.do)');
        			},
        			success : function(msg) {
        				if(msg == 'true'){
        					alert('修改成功！');
        					window.location.reload();
        				}
        			}
        		});
            }
        });
	}
	
	
	//显示人员树
	function showEmpTree(userId, deptId){
		var url = '${curl}/ztree_showAllDeptTree.do?isTra=1&allEmp=1&t='+new Date();
		top.layer.open({
			id: 'empLayer',
            title: '角色同步',
            area: ['860px', '400px'],
            type: 2,
            content: url,
            btn:["确认选择","取消选择"],
            success:function(){
            	top.$('.layui-layer-shade').css('display', 'inherit');
            	top.$('.layui-layer-setwin').css('display', 'inherit');
            	top.$('.layui-layer-iframe').width(800);
            },
            yes:function(index,layer){
            	var iframeWin = top[layer.find('iframe')[0]['name']];
            	var userIdAndNames = iframeWin.test();
            	if(userIdAndNames == false){
            		/* alert("只能授予一人！"); */
            		return;
            	}
            	$.ajax({
        			url : 'siteManage_roleTransfer.do',
        			type : 'POST',   
        			cache : false,
        			async : false,
        			data :{
        				'userId':userId,'deptId':deptId,'userIdAndNames':userIdAndNames
        			},
        			error : function(e) {  
        				alert('AJAX调用错误(siteManage_roleTransfer.do)');
        			},
        			success : function(msg) {
        				if(msg == 'true'){
        					alert('同步成功！');
        					window.location.reload();
        				}else{
        					alert('同步失败！');
        				}
        			}
        		});
            	top.layer.close(index);
            	top.$('.layui-layer-shade').css('display', 'none');
            	top.$('.layui-layer-setwin').css('display', 'none');
            	top.$('.layui-layer-iframe').width(500);
            }
        });
	}
	</script>
</html>