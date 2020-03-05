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
		.display{
			display: none;
		}
		.cur1{
			margin-top: 10px;
			background-color:#67bfe8;
			color:white;
			padding: 2px 0 0 6px;
			height: 30px;
			font-size: 16px;
			border-radius: 4px;
		}
		.cur2{
			margin-top: 10px;
			background-color:#b2b2b2;
			color:white;
			padding: 2px 0 0 6px;
			height: 30px;
			font-size: 16px;
			border-radius: 4px;
		}
	</style>
</head>
<body>
	<div class="tw-layout">
		<div class="tw-container tm-container">
		
			<div style="width: 8%;float: left;">
		    <ul>
		    	<c:forEach var="item" items="${sites}" varStatus="status">
		    		 <li <c:if test="${status.count == 1}">class="cur1"</c:if><c:if test="${status.count != 1}">class="cur2"</c:if> id="${item[0]}">${item[1]}</li>
		    	</c:forEach>
			</ul>
		    </div>
		
			<div class="tm-layout-left" style="width:27%;border-left: 1px solid #eee;">
				<div class="tw-list-top">
					<form name="doItemList"  id="doItemList" action="${ctx }/menurole_getRoleList.do" method="post">
					<div class="tw-search-bar">
						<label class="tw-form-label" for="">角色名称：</label>
			            <input type="text" class="tw-form-text" style="width: 125px;"  name="roleName" value="${roleName}" placeholder="输入关键字">
			            <button class="tw-btn-primary" type="submit" style="padding: 5px 2px;">
			                <i class="tw-icon-search"></i> 搜索
			            </button>
					</div>
					</form>
				</div>
				<div class="tw-container">
					<form id="thisForm" method="POST" name="thisForm" action="${ctx}/role_getRoleList.do" >
					<table class="tw-table-list tw-table-select" layoutH="100">
						<thead>
					    	<tr>
						    	<th width="15%">序号</th>
						    	<th align="center">角色名称</th>
						    	<th align="center" width="30%">状态</th>
							</tr>
				    	</thead>
				    	<tbody>
				    	<c:forEach var="item" items="${list}" varStatus="status"> 
			    		  <tr onclick="impowerMenu('${item.roleId}');" id="${item.roleId}" >
					        	<td align="center" id="${item.roleId}" class="item" >${status.count}</td>
					        	<td >${item.roleName}</td>
					        	<td align="center">
					        		<c:if test="${item.roleStatus eq 1}"><font style="color: green;">启用</font></c:if>	
					        		<c:if test="${item.roleStatus eq 0}"><font style="color: red;">禁用</font></c:if>
					        		<c:if test="${empty item.roleStatus}">暂无数据</c:if>	
					        	</td>
					      </tr>
					    </c:forEach>
					    </tbody>
		    		</table>
					</form>
				</div>
			</div>


			<div class="tm-layout-right" style="width:65%;">
				<div class="tw-container" style="padding:0;" layoutH="40">
					<iframe id="frame1"  class="frame1 display"  frameborder="auto" marginheight="0"  marginwidth="0" border="0" 
						src = ""
					style=" display:none; width:98%; height:98%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" 
					></iframe>
					<iframe id="frame2"  class="frame2" frameborder="auto" marginheight="0"  marginwidth="0" border="0" 
						src = "${ctx}/menurole_getMenuList.do?roleId=${firstRoleId}"
					style="width:98%; height:98%;border-width:0px 1px 1px 0px;border-style:solid;border-color:#B8D0D6;" 
					></iframe>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="siteId" name="siteId" value="${siteId }" />
</body>
<script type="text/javascript">
	window.onload = function(){
	    obj_li = document.getElementsByTagName("li"); // 获取li对象数组
	    for(k in obj_li)
	        obj_li[k].onclick=function(){  // 为每个li注册单击事件
		    	for(k in obj_li){
		    		obj_li[k].className = "cur2";//先将所有的li的class变为cur1，实现了切换的效果
		    	}
	            this.className = "cur1";    //再修改点击的li的class为cur2
	            
	            $.ajax({
					async:true,//是否异步
					type:"POST",//请求类型post\get
					cache:false,//是否使用缓存
					dataType:"text",//返回值类型
					data:{
						"siteId":this.id
					},
					url:"${ctx}/menurole_changeRoleList.do",
					success:function(result){
						 var res = eval("("+result+")");
						 $("tbody").empty();
				         $("tbody").append(res.tbody);
				         document.getElementById("frame2").src = "${ctx}/menurole_getMenuList.do?roleId="+res.firstRoleId;
				         document.getElementById("siteId").value = res.siteId;
					}
		    	});
	           
	        }
	}


	var remberId = "";
	//角色授权菜单
	function impowerMenu(roleId){
		if(remberId != ""){
			$("#"+remberId).removeClass("tw-actived");
		}
		document.getElementById(roleId).className = "tw-actived";
		remberId = roleId;
		
		
		$("#frame2").hide();
		$("#frame1").show();
		document.getElementById("frame1").src="${ctx}/menurole_getMenuList.do?roleId="+roleId;
	}
</script>
</html>