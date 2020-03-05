<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<html >
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
		.new-icon-edit {
			display: inline-block;
			width: 16px;
			height: 16px;
			background: url(${ctx }/images/icon-edit.png) no-repeat center center/100%;
		}
		.dept-list-ul {
			display: none;
			position: absolute;
			top: 70px;
			left: 50px;
			width: 220px;
			border: 1px solid #ccc;
			background: #fff;
			text-align: left;
			padding: 0 10px;
			border-radius: 3px;
			z-index: 9999;
			color: #666;
			font-size: 16px;
		}
		.dept-list-ul li {
			padding: 5px 0;
		}
		.wf-fixtable-wrap .wf-fixtable-tbody td, .wf-fixtable-wrap .wf-fixtable tbody td {
			overflow: visible;
		}
		.wf-fixtable-wrap .wf-fixtable-tbody td.table-div div, .wf-fixtable-wrap .wf-fixtable tbody td.table-div div {
			overflow: visible;
		}
		
		.show_all_deptlist{
			display: inline-block;
			width: 55px;
			cursor: pointer;
		}
		.wf-btn-orange{
			padding: 6px 8px;
			font-size: 14px;
		}
		
		.wf-fixtable-scroller {
			height: auto !important;
		}
	</style>
</head>
<body >
<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="roleForm" id="roleForm" action="${ctx }/siteManage_getRoleList.do?a=Math.random();" method="post" style="display:inline-block;">
			    <input id="isadmin" name="isadmin" type="hidden" value="${isadmin }"></input>
			    <input id="ifsave" name="ifsave" type="hidden" value=""></input>
			    <input id="depId" name="depId" type="hidden" value="${depId }"></input>
			    <input id="deptIdAndNames" name="deptIdAndNames" type="hidden" value="${deptIdAndNames }"></input>
			    <input id="userIdAndNames" name="userIdAndNames" type="hidden" value="${userIdAndNames }"></input>
			    <input id="oldUserIdAndNames" name="oldUserIdAndNames" type="hidden" value="${oldUserIdAndNames }"></input>
			    <input id="portalRes" name="portalRes" value="" type="hidden" value="${portalRes }"></input>
		    	<input id="oaRes" name="oaRes" value="" type="hidden" value="${oaRes }"></input>
		    	<input id="wfRes" name="wfRes" value="" type="hidden" value="${wfRes }"></input>
	    		<div class="wf-top-tool" style="padding-left: 10px;">
			    	<button class="wf-btn-primary" type="button" onclick="addRole();"><i class="wf-icon-plus-circle" ></i> 添加</button>
	                <button class="wf-btn-orange" type="button" onclick="update();"><i class="wf-icon-pencil"></i> 修改</button>
	                <button class="wf-btn-danger" type="button" onclick="deleteRole();"><i class="wf-icon-minus-circle" ></i> 删除</button>
				</div>
	    		<c:if test="${isadmin eq '1' }">
				    <label class="wf-form-label" for="" style="size: 16px" >站点选择：</label>
				    <select id="searchSiteName" name="searchSiteName" onchange="searchSite()">
				    	<c:forEach var="item" items="${siteList}" varStatus="status">
	    	 				<option id="${item.id }" value="${item.id }" <c:if test="${item.id eq siteId}">selected='selected'</c:if>>${item.name }</option>
	    	 			</c:forEach>
				    </select>
			  	</c:if>
				<label class="wf-form-label" for="" style="size: 16px;margin-left: 25px;" >角色名称：</label>
				<input id="name" name="name" type="text" value="${name}" placeholder="输入关键字查询" class="wf-form-text">
	            <button class="wf-btn-primary" type="submit" onclick="doSearch();"><i class="wf-icon-search"></i> 搜索</button>
	            <button class="wf-btn-primary" type="button" onclick="clean();"><i class="wf-icon-remove" ></i> 清空</button>
			</form>
		</div>
	</div>
	<div class="wf-list-wrap" style="height:530px">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/siteManage_getRoleList.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
		    	  		<th width="5%">
		    	  		<input type="checkbox" name="selAll" id="selAll" onclick="sel();">
		    	  		</th>
				    	<th width="5%" align="center">序号</th>
				    	<th width="10%">角色名称</th>
				    	<th width="10%">绑定部门</th> 
				    	<!-- <th width="3%"></th>  -->
				    	<th width="15%">绑定人员</th>
				    	<th width="15%">门户权限</th>
				    	<th width="15%">办公自动化</th>
				    	<!-- <th width="17%">流程</th> -->
				    	<!-- <th width="15%">手机应用</th> -->
				    	<th width="8%">默认</th>
                   </tr>
		    	</thead>
		    	<c:forEach var="item" items="${retRoleList}" varStatus="status">
		    		<tr>
		    			<td align="center"><input type="checkbox" id="selid" name="selid" value="${item.id }"> </td>
						<td>${(selectIndex-1)*pageSize+status.count}</td>
						<td>${item.name}</td>
						<td align="center" class="table-div">
							<div style="display: table;">
								<span style="display: table-cell;vertical-align: middle;position: relative;">
									<c:forEach var="dept" items="${item.deptList}" varStatus="status">
										<c:if test="${status.index <= 2 }">
											${dept.name}<c:if test="${status.index != (fn:length(item.deptList) - 1) }"><br></c:if>
										</c:if>
										<c:if test="${status.index == 3 }">
											<span class="show_all_deptlist" onmouseover="deptOver(this)" onmouseout="deptOut(this)">
												...
											</span>
										</c:if>
									</c:forEach>
									<ul class="dept-list-ul">
										<c:forEach var="dept" items="${item.deptList}" varStatus="status">
											<li>${dept.name}</li>
										</c:forEach>
									</ul>
								</span>
								<span style="text-align: center;display: table-cell;vertical-align: middle;padding-top: 5px;padding-left: 10px;"><a class="new-icon-edit" onclick="showDeptTree('${item.id }','${item.deptIds}');"> </a></span>
							</div>
						</td>
						<td align="center" class="table-div">
							<div style="display: table;">
								<span style="display: table-cell;vertical-align: middle;position: relative;">
									<c:forEach var="user" items="${item.userList}" varStatus="status">
										<c:if test="${status.index <= 2 }">
											${user.name}<c:if test="${status.index != (fn:length(item.userList) - 1) }"><br></c:if>
										</c:if>
										<c:if test="${status.index == 3 }">
											<span class="show_all_deptlist" onmouseover="deptOver(this)" onmouseout="deptOut(this)">
												...
											</span>
										</c:if>
									</c:forEach>
									<ul class="dept-list-ul">
										<c:forEach var="user" items="${item.userList}" varStatus="status">
											<li>${user.name}</li>
										</c:forEach>
									</ul>
								</span>
								<span style="text-align: center;display: table-cell;vertical-align: middle;padding-top: 5px;padding-left: 10px;"><a class="new-icon-edit" onclick="showEmpTree('${item.id }','${item.userIds}');"> </a></span>
							</div>	
						</td>
						<td align="center" class="table-div">
							<div style="display: table;">
								<span style="display: table-cell;vertical-align: middle;position: relative;">
									<c:forEach var="wbApp" items="${item.portalAppList}" varStatus="status">
										<c:if test="${status.index <= 2 }">
											${wbApp.name}<c:if test="${status.index != (fn:length(item.portalAppList) - 1) }"><br></c:if>
										</c:if>
										<c:if test="${status.index == 3 }">
											<span class="show_all_deptlist" onmouseover="deptOver(this)" onmouseout="deptOut(this)">
												...
											</span>
										</c:if>
									</c:forEach>
									<ul class="dept-list-ul">
										<c:forEach var="wbApp" items="${item.portalAppList}" varStatus="status">
											<li>${wbApp.name}</li>
										</c:forEach>
									</ul>
								</span>
								<span style="text-align: center;display: table-cell;vertical-align: middle;padding-top: 5px;padding-left: 10px;"><a class="new-icon-edit" onclick="showRscList('1', '${item.id }','${item.portalResIds}','${item.oaResIds}','${item.wfResIds}');"> </a></span>
							</div>
						</td>
						<td align="center" class="table-div">
							<div style="display: table;">
								<span style="display: table-cell;vertical-align: middle;position: relative;">
									<c:forEach var="wbApp" items="${item.oaAppList}" varStatus="status">
										<c:if test="${status.index <= 2 }">
											${wbApp.name}<c:if test="${status.index != (fn:length(item.oaAppList) - 1) }"><br></c:if>
										</c:if>
										<c:if test="${status.index == 3 }">
											<span class="show_all_deptlist" onmouseover="deptOver(this)" onmouseout="deptOut(this)">
												...
											</span>
										</c:if>
									</c:forEach>
									<ul class="dept-list-ul">
										<c:forEach var="wbApp" items="${item.oaAppList}" varStatus="status">
											<li>${wbApp.name}</li>
										</c:forEach>
									</ul>
								</span>
								<span style="text-align: center;display: table-cell;vertical-align: middle;padding-top: 5px;padding-left: 10px;"><a class="new-icon-edit" onclick="showRscList('2', '${item.id }','${item.portalResIds}','${item.oaResIds}','${item.wfResIds}');"> </a></span>
							</div>
						</td>
						<%-- <td align="center" class="table-div">
							<div style="display: table;">
								<span style="display: table-cell;vertical-align: middle;position: relative;">
									<c:forEach var="wbApp" items="${item.wfAppList}" varStatus="status">
										<c:if test="${status.index <= 2 }">
											${wbApp.name}<c:if test="${status.index != (fn:length(item.wfAppList) - 1) }"><br></c:if>
										</c:if>
										<c:if test="${status.index == 3 }">
											<span class="show_all_deptlist" onmouseover="deptOver(this)" onmouseout="deptOut(this)">
												...
											</span>
										</c:if>
									</c:forEach>
									<ul class="dept-list-ul">
										<c:forEach var="wbApp" items="${item.wfAppList}" varStatus="status">
											<li>${wbApp.name}</li>
										</c:forEach>
									</ul>
								</span>
								<span style="text-align: center;display: table-cell;vertical-align: middle;padding-top: 5px;padding-left: 10px;"><a class="new-icon-edit" onclick="showRscList('3', '${item.id }','${item.portalResIds}','${item.oaResIds}','${item.wfResIds}');"> </a></span>
							</div>
						</td> --%>
						<%-- <td align="center">
							<c:forEach var="wbApp" items="${item.wbAppList}" varStatus="status">
								<c:if test="${wbApp.resourceType == '4'}">
									${wbApp.name}<br>
								</c:if>
							</c:forEach>
						</td> --%>
						<td align="center">
							<input name="ifDefault" id="ifDefault" type="checkbox" <c:if test="${item.ifDefault == 1}">checked="checked"</c:if> disabled="disabled"></input>
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
	<div class="tw-list-ft" id="pagingWrap"></div>
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	window.onload=function(){ 
		//获得后台分页相关参数-必须
		if(parent._selectIndex){
			selectIndex = parent._selectIndex
			parent._selectIndex = null
		} else {
			selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);
		}
		skipUrl="<%=request.getContextPath()%>"+"/siteManage_getRoleList.do";			//提交的url,必须修改!!!*******************
		submitForm=document.getElementById('roleForm');									//提交的表单,必须修改!!!*******************
		innerObj=document.getElementById('pagingWrap');								//分页信息填写对象,必须修改!!!*******************	
		
		MaxIndex=parseInt(<%=request.getAttribute("MaxIndex").toString()%>,10);			//列表数据最大值,无需修改
		pageSize=parseInt(<%=request.getAttribute("pageSize").toString()%>,10);			//列表每页最大值,无需修改
		selectIndex=parseInt(<%=request.getAttribute("selectIndex").toString()%>,10);	//列表当前页数,无需修改
		sortStr='<%=request.getAttribute("sortStr").toString()%>';						//排序字符串，暂时缺省，无需修改
		if(startPaging){startPaging(MaxIndex,pageSize,selectIndex,sortStr,skipUrl,submitForm,innerObj);}
		
		var $subIframe = $('#subIframe', parent.document);
		var subIframeHeight = $subIframe.height();
		
		$('.wf-list-wrap').css('height', subIframeHeight - $('.wf-list-top').outerHeight() - $('.tw-list-ft').outerHeight());
	};
</script>
<script>	
	function searchSite(){
		$('#roleForm').submit();
	}
	
	function deptOver(_this) {
		$(_this).siblings(".dept-list-ul").show();
	}
	function deptOut(_this) {
		$(_this).siblings(".dept-list-ul").hide();
	}
	function update(){
		var selid = document.getElementsByName('selid');
		var roleIds = "";
		for(var i = 0 ; i < selid.length; i++){
			if(selid[i].checked){
				roleIds += selid[i].value + ",";
			}
		}
		if(roleIds.length > 0){
			roleIds = roleIds.substring(0, roleIds.length - 1);
			var roleIdArr = roleIds.split(",");
			if(roleIdArr.length > 1){
				alert("只能编辑一条信息");
				return;
			}
		}else {
			alert('请选择您要编辑的角色');
			return;
		}
		var url =  '${ctx}/siteManage_toUpdateRole.do?roleId='+roleIds+ "&siteId=${siteId}";
		if(document.getElementById("searchSiteName")!=null&&document.getElementById("searchSiteName")!=""&&document.getElementById("searchSiteName")!=undefined){
			url += '&searchSiteName='+document.getElementById("searchSiteName").value;
		}
		top.layer.open({
            title: '修改角色',
            area: ['1190px', '470px'],
            type: 2,
           	// btn: ['确认', '取消'],
            content: url,
            end:function(){
            	window.location.reload();
            /* 	top.$('.layui-layer-shade').css('display', 'none');
            	top.$('.layui-layer-setwin').css('display', 'none'); */
            	var sheets = top.document.styleSheets
        		var sheet = sheets[sheets.length-1]
            	sheet.deleteRule(0)
            },
            success:function(){
            	top.$('.layui-layer-shade').css('display', 'inherit');
            	top.$('.layui-layer-setwin').css('display', 'inherit');
            	/* if(top.$('.layui-layer-iframe')[0]){ */
            		var sheets = top.document.styleSheets
            		var sheet = sheets[sheets.length-1]
            		sheet.insertRule(".layui-layer-iframe {width:800px !important;top:80px !important}",0)
            		//top.$('.layui-layer-iframe')[0].style.setProperty('width', '800px', 'important');
            		//top.$('.layui-layer-iframe')[0].style.setProperty('top', '80px', 'important');
            	/* } */
            }
        });
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
		
	function check(id){
		layer.open({
            title: '查看部门',
            area: ['500px', '300px'],
            type: 2,
           	// btn: ['确认', '取消'],
            content: '${ctx}/siteManage_updateDep.do?depId='+id
        });
	}
	
	function doSearch(){
		var username=$("#name").val();
		var myform = document.getElementById("thisForm");
  		//myform.action="${ctx}/siteManage_showAllDep.do?username="+username+"&siteId="+sitename;
  		//myform.action="${ctx}/siteManage_getRoleList.do";
  		myform.submit();
  	}
	
	function addRole(){
		var url =  '${ctx}/siteManage_toAddRole.do?depId='+depId + "&siteId=${siteId}";
		if(document.getElementById("searchSiteName")!=null&&document.getElementById("searchSiteName")!=""&&document.getElementById("searchSiteName")!=undefined){
			url += '&searchSiteName='+document.getElementById("searchSiteName").value;
		}
		top.layer.open({
            title: '添加角色',
            area: ['1190px', '470px'],
            type: 2,
           	// btn: ['确认', '取消'],
            content: url,
            end:function(){
            	window.location.reload();
            	/* top.$('.layui-layer-shade').css('display', 'none');
            	top.$('.layui-layer-setwin').css('display', 'none'); */
            	var sheets = top.document.styleSheets
        		var sheet = sheets[sheets.length-1]
            	sheet.deleteRule(0)
            },
            success:function(){
            	top.$('.layui-layer-shade').css('display', 'inherit');
            	top.$('.layui-layer-setwin').css('display', 'inherit');
            	/* if(top.$('.layui-layer-iframe')[0]){ */
            		var sheets = top.document.styleSheets
            		var sheet = sheets[sheets.length-1]
            		sheet.insertRule(".layui-layer-iframe {width:800px !important;top:80px !important}",0)
            		//top.$('.layui-layer-iframe')[0].style.setProperty('width', '800px', 'important');
            		//top.$('.layui-layer-iframe')[0].style.setProperty('top', '80px', 'important');
            	/* } */
            }
        });
	}
		
	function clean(){
		$("#name").val("");
		$("#username").val("");
	}
		
	function changeVal(id){
		$("#depId").val(id);
	}
	
	//显示部门树
	function showDeptTree(roleId, deptIds){
		sessionStorage.setItem("userIds", deptIds);
		//var url = '${curl}/ztree_showAllDeptTree.do?allEmp=1&isDeptTree=1&userIds=' + deptIds + '&t='+new Date();
		var url = '${curl}/ztree_showAllDeptTree.do?allEmp=1&isDeptTree=1&t='+new Date();
		if(document.getElementById("searchSiteName")!=null&&document.getElementById("searchSiteName")!=""&&document.getElementById("searchSiteName")!=undefined){
			url += '&searchSiteName='+document.getElementById("searchSiteName").value;
		}
		top.layer.open({
            title: '添加部门',
            area: ['860px', '600px'],
            type: 2,
            content: url,
            btn:["确认选择","取消选择"],
            success:function(){
            	top.$('.layui-layer-shade').css('display', 'inherit');
            	top.$('.layui-layer-setwin').css('display', 'inherit');
            	/* if(top.$('.layui-layer-iframe')[0]){ */
            		var sheets = top.document.styleSheets
            		var sheet = sheets[sheets.length-1]
            		sheet.insertRule(".layui-layer-iframe {width:800px !important;top:80px !important}",0)
            		//top.$('.layui-layer-iframe')[0].style.setProperty('width', '800px', 'important');
            		//top.$('.layui-layer-iframe')[0].style.setProperty('top', '80px', 'important');
            	/* } */
            },
            end:function(){
            	var sheets = top.document.styleSheets
        		var sheet = sheets[sheets.length-1]
            	sheet.deleteRule(0)
            	//console.log('end==========================')
            },
            yes:function(index,layer){
            	var iframeWin = top[layer.find('iframe')[0]['name']];
            	var deptIdAndNames = iframeWin.test();
            	var deptIdAndNameArr = deptIdAndNames.split(';');
            	var newDeptIds = deptIdAndNameArr[0];
            	$.ajax({
        			url : 'siteManage_saveRoleDepts.do',
        			type : 'POST',   
        			cache : false,
        			async : false,
        			data :{
        				'roleId':roleId,'deptIds':newDeptIds
        			},
        			error : function(e) {   
        				alert('AJAX调用错误(siteManage_saveRoleDepts.do)');
        			},
        			success : function(msg) {
        				if(msg == 'true'){
        					alert('修改成功！');
        					window.location.reload();
        					/* top.$('.layui-layer-shade').css('display', 'none');
        	            	top.$('.layui-layer-setwin').css('display', 'none'); */
        	            	
                    		//sheet.insertRule('.layui-layer-iframe','{width:500px !important;top:250px !important}',0)
                    		//sheet.addRule('.layui-layer-iframe','top:250px !important')
        	            	
        	            	/*if(top.$('.layui-layer-iframe')[0]){
        	            		top.$('.layui-layer-iframe')[0].style.setProperty('width', '500px', 'important');
        	            		top.$('.layui-layer-iframe')[0].style.setProperty('top', '250px', 'important');
        	            	}*/
        				}
        			}
        		});
            	top.layer.close(index);
            }
        });
	}
	
	
	//显示人员树
	function showEmpTree(roleId, userIds){
		sessionStorage.setItem("userIds", userIds);
		var url = '${curl}/ztree_showAllDeptTree.do?allEmp=1&t='+new Date();
		if(document.getElementById("searchSiteName")!=null&&document.getElementById("searchSiteName")!=""&&document.getElementById("searchSiteName")!=undefined){
			url += '&searchSiteName='+document.getElementById("searchSiteName").value;
		}
		top.layer.open({
			id: 'empLayer',
            title: '添加人员',
            area: ['860px', '600px'],
            type: 2,
            content: url,
            btn:["确认选择","取消选择"],
            success:function(){
            	top.$('.layui-layer-shade').css('display', 'inherit');
            	top.$('.layui-layer-setwin').css('display', 'inherit');
            	/* if(top.$('.layui-layer-iframe')[0]){ */
            		var sheets = top.document.styleSheets
            		var sheet = sheets[sheets.length-1]
            		sheet.insertRule(".layui-layer-iframe {width:800px !important;top:80px !important}",0)
            		//top.$('.layui-layer-iframe')[0].style.setProperty('width', '800px', 'important');
            		//top.$('.layui-layer-iframe')[0].style.setProperty('top', '80px', 'important');
            	/* } */
            },
            end:function(){
            	var sheets = top.document.styleSheets
        		var sheet = sheets[sheets.length-1]
            	sheet.deleteRule(0)
            	//console.log('end==========================')
            },
            yes:function(index,layer){
            	var iframeWin = top[layer.find('iframe')[0]['name']];
            	var userIdAndNames = iframeWin.test();
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
        				/* 	top.$('.layui-layer-shade').css('display', 'none');
        	            	top.$('.layui-layer-setwin').css('display', 'none'); */
        	            	
        				}
        			}
        		});
            	
            	top.layer.close(index);
            }
        });
	}
	
	function sel(){
		var selAll = document.getElementById("selAll");
		var selid = document.getElementsByName('selid');
		for(var i=0 ; i<selid.length ; i++)
		if(selAll.checked){
			selid[i].checked = true;
		}else{
			selid[i].checked = false;
		}
	}
	
	function deleteRole(){
		var roleIds = "";
		var selid = document.getElementsByName("selid");
		for(var i=0;i<selid.length;i++){
			if(selid[i].checked){
				roleIds += selid[i].value +",";
			}
		}
		if(roleIds.length>0){
			roleIds = roleIds.substring(0,roleIds.length-1);
		}else{
			alert("请选择一个角色");
			return;
		}
		if(confirm("确定要删除这些角色吗？")){
			$.ajax({
				url : '${ctx}/siteManage_deleteRole.do?roleIds='+roleIds,
				type : 'POST',  
				cache : false,
				async : false,
				error : function() {
				alert('AJAX调用错误(siteManage_deleteRole.do)');
					return false;
				},
				success : function(msg) {
					var msgMark = msg.split(';')[0];
					if(msgMark =='success'){
						alert("删除角色成功！");
						window.location.href = '${ctx}/siteManage_getRoleList.do' ;
					}else{
						alert(msg.split(';')[1]);
					}
				}
			 });
		}else{
			return;
		}
	}
	
	//门户资源页面
	function showRscList(resType, roleId, portalResIds, oaResIds, wfResIds){
		var siteId = "";
		if(document.getElementById("searchSiteName")!=null&&document.getElementById("searchSiteName")!=""&&document.getElementById("searchSiteName")!=undefined){
			siteId = document.getElementById("searchSiteName").value;
		}else{
			siteId = "${siteId}";
		}
		var title = "";
		var selectedIds = "";
		if('1' == resType){
			title = "门户资源";
        	selectedIds = portalResIds;
		}else if('2' == resType){
			title = "办公自动化资源";
        	selectedIds = oaResIds;
		}
		var url = '${curl}/siteManage_getResourceList.do?siteId='+siteId+'&resType=' + resType + '&selectedIds=' + selectedIds + '&t='+new Date();
		layer.open({
            title: title,
            area: ['300px', '400px'],
            type: 2,
            content: url,
            cancel:function(index){
            	$('#ifsave').val('0');
            },
            end: function(index){
            	var ifsave = $('#ifsave').val();
            	if(ifsave == "0"){
            		return;
            	}
            	var isExecute = false;
            	var portalResNames = "";
            	var oaResNames = "";
            	
            	if('1' == resType){
            		var portalRes = $('#portalRes').val();
            		if(portalRes != null && portalRes != ''){
            			var portalResArr = portalRes.split(';');
                    	portalResIds = portalResArr[0];
                    	portalResNames = portalResArr[1];
                    	isExecute = true;
            		}else{
            			portalResIds = "";
            			portalResNames = "";
            			isExecute = true;
            		}
        		}else if('2' == resType){
        			var oaRes = $('#oaRes').val();
        			if(oaRes != null && oaRes != ''){
        				var oaResArr = oaRes.split(';');
                    	oaResIds = oaResArr[0];
                    	oaResNames = oaResArr[1];
                    	isExecute = true;
        			}else{
        				oaResIds = "";
        				oaResNames = "";
        				isExecute = true;
        			}
        		}
            	
            	if(isExecute){
            		$.ajax({
            			url : 'siteManage_saveRoleRes.do',
            			type : 'POST',   
            			cache : false,
            			async : false,
            			data :{
            				'roleId':roleId,'portalRes':portalResIds,'oaRes':oaResIds,'resourcetype':resType,
            				'portalResNames':portalResNames,'oaResNames':oaResNames
            			},
            			error : function(e) {  
            				alert('AJAX调用错误(siteManage_saveRoleRes.do)');
            			},
            			success : function(msg) {
            				if(msg == 'true'){
            					alert('修改成功！');
            					window.location.reload();
            				}
            			}
            		});
            	}
            }
        });
	}
</script>
</html>