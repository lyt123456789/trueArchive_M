<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <style type="text/css">
    	.roleOption{
    		width: 200px;
    	}
    	.button i {
			padding:1px 5px 0px 0px;
		}
		.button {
		    font-family: 'kalingaregular';
			text-decoration:none !important;
		}
		
		.button a {
			display:block;
			text-decoration:none;
		}
		
		.medium {
			padding:2px 14px 2px 14px;
			font-size:16px;
		}
		
		.dark-gray {
			background:#BBBBBB;
			color:#FFF;
		}
		
		.dark-gray:hover {
			background:#A2ABA5;
		}
		
		#jzk1{
		    position: absolute;
		    z-index: 99999999998;
		    height: 100%;
		    width: 100%;
		    background:rgba(255,255,255,.65);
		}
		
		#dialog{
			margin-top: 19%;
			z-index:999999999999;
		}
    </style>
</head>
<body>
	<div  style="display: none;" id="jzk1">
 		<div id='fullbg'></div><div id='dialog' style='text-align:center'>
 			<img src='${ctx}/dwz/style/images/saveroleloading.gif'>
 		</div>
 	</div>

<div class="wf-layout">
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="doItemList"  id="doItemList" action="${ctx }/mobileTerminalInterface_showAllWfList.do" method="post">
	            <label class="wf-form-label" for="">流程名称：</label>
	            <input type="text" class="wf-form-text" name="wfname" value="${wfname}" placeholder="输入关键字">
	            <c:if test="${isAdmin eq '1'}">
	            <label class="wf-form-label" for="">站点：</label>
	            <select id="chooseSiteId" name="chooseSiteId">
	            	<option value="">请选择站点</option>
		            <c:forEach var="s" items="${siteIds}">
		            	<option value="${s.departmentGuid}" <c:if test="${chooseSiteId eq s.departmentGuid}">selected</c:if> >${s.departmentName}</option>
		            </c:forEach>
	            </select>
	            
	            </c:if>
	            <input type="hidden" name="siteId" value="${siteId}" />
	            <input type="hidden" name="isAdmin" value="${isAdmin}" />
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="thisForm" method="POST" name="thisForm" action="${ctx}/mobileTerminalInterface_showAllWfList.do" >
			<input type="hidden" name="siteId" value="${siteId}" />
			<input type="hidden" name="chooseSiteId" value="${chooseSiteId}" />
			<input type="hidden" name="isAdmin" value="${isAdmin}" />
			<table class="wf-fixtable" layoutH="140">
				<thead>
			    	<tr>
			    		<th width="50" align="center">序号</th>
						<th width="120" align="center">事项名称</th>
						<th align="center" width="50" >表单版本</th>
						<th align="center" width="120" >表单时间</th>
						<th align="center" width="80" >节点名称</th>
						<th align="center" width="120" >节点角色</th>
						<th align="center" width="150" >操作</th>
					</tr>
		    	</thead>
		    	<c:forEach items="${list}" var="item" varStatus="status">
		    		<c:if test="${empty item.nodeList or empty item.formList}">
			    		<tr>
			    			<td align="center" >${(selectIndex-1)*pageSize+status.count}</td>
		    				<td class="workflowidtitle" workflownnameid="${item.wfm_id }">
		    					${item.wfm_name }
		    				</td>
		    				<td align="center" ></td>
		    				<td></td>
		    				<td align="center" >${item.wfm_modifytime }</td>
		    				<td></td>
		    				<td></td>
		    			</tr>
		    		</c:if>
		    		<c:forEach items="${item.nodeList}" var="node" varStatus="st">
		    			<tr>
		    				<c:if test="${st.count eq 1}">
			    				<td align="center" rowspan="${fn:length(item.nodeList)}">${(selectIndex-1)*pageSize+status.count}</td>
			    				<td class="workflowidtitle" workflownnameid="${item.wfm_id }" rowspan="${fn:length(item.nodeList)}">
			    					<!-- <font color="#008DEF">
					                	<span style="font-size:25px;line-height:24px;">-</span>
					                </font> -->
					                ${item.wfm_name }
			    				</td>
			    				<td align="center" rowspan="${fn:length(item.nodeList)}">
			    					<c:forEach var="form" items="${item.formList}" varStatus="fo">
			    						<input class="button medium dark-gray" type="button" onclick="showForm('${form.workflowId}','${item.wfItem.id}')" value="表单${fo.count}" /><br /><br />
			    					</c:forEach>
			    				</td>
			    				<td align="center" rowspan="${fn:length(item.nodeList)}">
			    					<c:forEach var="form" items="${item.formList}">
				    					<fmt:formatDate value="${form.beginDate}" pattern="yyyy/MM/dd"/>
				    					-<fmt:formatDate value="${form.endDate}" pattern="yyyy/MM/dd"/><br/><br />
			    					</c:forEach>
			    				</td>
		    				</c:if>
		    				<td>
		    					${node.wfn_name}
		    				</td>
		    				<td>
		    					<select class="roleOption" onchange="changeRole(this)">
			    					<c:forEach var="rol" items="${node.roleList}">
			    						<option value="${rol.roleId}">${rol.roleName}</option>			    						
			    					</c:forEach>
		    					</select>
		    					<input type="hidden" value="${node.roleList[0].roleId}" class="roleId" />
		    					<input type="hidden" value="${node.roleList[0].roleUserIds}" class="roleUserIds" />
		    				</td>
		    				
		    				<td align="center">
							<a class="wf-btn-primary" href="javascript:void(0);" onclick="showEmpTree(this);">
	                			<i class="wf-icon-pencil"></i> 查看
	           				 </a>
							<a class="wf-btn-primary" href="javascript:void(0);" onclick="updateEmpTree(this);">
	                			<i class="wf-icon-pencil"></i> 编辑
	           				 </a>
							</td>
		    			</tr>
		    		</c:forEach>
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
			skipUrl="<%=request.getContextPath()%>"+"/mobileTerminalInterface_showAllWfList.do";			//提交的url,必须修改!!!*******************
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
	
	//编辑流程整体的属性
	function editWfMain(id){
		var url ="${ctx}/mobileTerminalInterface_initWfMain.do?workflowId="+id;
		parent.layer.open({
			title:'编辑流程',
			type:2,
			area:[parent.window.screen.width+"px",(parent.window.screen.height-150)+"px"],
			content: url
		}); 
	}	
	 
	 //修改工作流
	 function modify_row(){
		 var id=$(".wf-actived td:eq(2)").attr("workflownnameid");
		 var workflowname=$(".wf-actived td:eq(2)").text();
		 if(typeof(id)!="undefined"){
			 var flag = checkIsInValidity();
			if(!flag){
				alert("license已过期,请联系管理员！");
				return;
			}
			layer.open({
	            title :'修改工作流',
	            type: 2,
	            content: "${ctx}/mobileTerminalInterface_modifyWorkFlow.do?id="+id+"&workflowname="+encodeURI(encodeURI(workflowname)),
	            area: ['350px', '130px'],
	            end:function(){
	            	window.location.href="${ctx}/mobileTerminalInterface_showAllWfList.do";
	            }
	        });
		 }else if("undefined"==typeof(id)){
				alert("请选择修改对象...");
		}
	 }
	 
	//显示人员树
	function showEmpTree(obj){
		//var roleId = obj.parentElement.parentElement.previousElementSibling.firstChild.firstChild.nextElementSibling.nextElementSibling;
		var userIds = obj.parentElement.parentElement.previousElementSibling.firstChild.firstChild.nextElementSibling.nextElementSibling.nextElementSibling.value;
		sessionStorage.setItem("userIds", userIds);
		var url = '${curl}/ztree_showAllDeptTree.do?allEmp=1&t='+new Date();
		if(document.getElementById("searchSiteName")!=null&&document.getElementById("searchSiteName")!=""&&document.getElementById("searchSiteName")!=undefined){
			url += '&searchSiteName='+document.getElementById("searchSiteName").value;
		}
		top.layer.open({
			id: 'empLayer',
            title: '查看',
            area: ['860px', '600px'],
            type: 2,
            content: url,
            success:function(){
            	top.$('.layui-layer-shade').css('display', 'inherit');
            	top.$('.layui-layer-setwin').css('display', 'inherit');
            	/* if(top.$('.layui-layer-iframe')[0]){ */
            		var sheets = top.document.styleSheets
            		var sheet = sheets[sheets.length-1];
            		sheet.insertRule(".layui-layer-iframe {width:800px !important;top:80px !important}",0)
            		//top.$('.layui-layer-iframe')[0].style.setProperty('width', '800px', 'important');
            		//top.$('.layui-layer-iframe')[0].style.setProperty('top', '80px', 'important');
            	/* } */
            },
            end:function(){
            	var sheets = top.document.styleSheets
        		var sheet = sheets[sheets.length-1];
            	sheet.deleteRule(0)
            	//console.log('end==========================')
            },
            yes:function(index,layer){
            	top.layer.close(index);
            }
        });
	}
	
	function updateEmpTree(obj){
		var roleId = obj.parentElement.parentElement.previousElementSibling.firstChild.firstChild.nextElementSibling.nextElementSibling.value;
		var userIds = obj.parentElement.parentElement.previousElementSibling.firstChild.firstChild.nextElementSibling.nextElementSibling.nextElementSibling.value;
		sessionStorage.setItem("userIds", userIds);
		var url = '${curl}/ztree_showAllDeptTree.do?allEmp=1&t='+new Date();
		if(document.getElementById("searchSiteName")!=null&&document.getElementById("searchSiteName")!=""&&document.getElementById("searchSiteName")!=undefined){
			url += '&searchSiteName='+document.getElementById("searchSiteName").value;
		}
		top.layer.open({
			id: 'empLayer',
            title: '修改',
            area: ['860px', '600px'],
            type: 2,
            content: url,
            btn:["确认选择","取消选择"],
            success:function(){
            	top.$('.layui-layer-shade').css('display', 'inherit');
            	top.$('.layui-layer-setwin').css('display', 'inherit');
            	/* if(top.$('.layui-layer-iframe')[0]){ */
            		var sheets = top.document.styleSheets
            		var sheet = sheets[sheets.length-1];
            		sheet.insertRule(".layui-layer-iframe {width:800px !important;top:80px !important}",0)
            		//top.$('.layui-layer-iframe')[0].style.setProperty('width', '800px', 'important');
            		//top.$('.layui-layer-iframe')[0].style.setProperty('top', '80px', 'important');
            	/* } */
            },
            end:function(){
            	var sheets = top.document.styleSheets
        		var sheet = sheets[sheets.length-1];
            	sheet.deleteRule(0)
            	//console.log('end==========================')
            },
            yes:function(index,layer){
            	var iframeWin = top[layer.find('iframe')[0]['name']];
            	var userIdAndNames = iframeWin.test();
            	top.layer.close(index);
	            zzfs();
            	setTimeout(function(){
	            	$.ajax({
	        			url : '${ctx}/siteManage_saveRoleUsers.do',
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
	        					fsjs();
	        				/* 	top.$('.layui-layer-shade').css('display', 'none');
	        	            	top.$('.layui-layer-setwin').css('display', 'none'); */
	        				}
	        			}
	        		});
					window.location.reload();
            	}, 500);
            }
        });
	}

	function changeRole(obj){
		var changeRoleId = obj.value;
		$.ajax({
			type:'post',
			url:'${ctx}/mobileTerminalInterface_getRoleInfoById.do',
			data:{
				"id":changeRoleId
			},
			success:function(msg){
				if(msg){
					var data = JSON.parse(msg);
					obj.nextElementSibling.value = data.roleId;
					obj.nextElementSibling.nextElementSibling.value = data.roleUserIds;
				}
			}
			
		});
	}
	
	function showForm(workflowId,itemId){
		var url = '${curl}/table_viewForm.do?workflowid='+workflowId+'&itemid='+itemId;
		openLayerTabs(workflowId,screen.width,screen.height,'表单预览',url);
		/* layer.open({
			id: 'viewForm',
            title: '表单预览',
            area: ['860px', '550px'],
            type: 2,
            content: url
        }); */
	}
	
	function openLayerTabs(processId,width,height,title,url,imgPath){		
		window.parent.topOpenLayerTabs(processId,900,600,title,url,imgPath);
	}
	
	function zzfs() {
		document.getElementById("jzk1").style.display = '';
	}
	function fsjs() {
		document.getElementById("jzk1").style.display = 'none';
	}
</script>
</html>