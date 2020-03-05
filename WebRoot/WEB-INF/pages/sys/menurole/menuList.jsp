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
	<script src="${ctx}/widgets/js/index.js" type="text/javascript"></script>
 	<link href="${ctx}/assets-common/plugins/jquery.treetable/jquery.treetable.css" rel="stylesheet" type="text/css" media="screen"/>

    <script src="${ctx}/assets-common/plugins/jquery.treetable/jquery.treetable.js" type="text/javascript"></script>
    <script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
    <style type="text/css">
    	.enabled{
			display: none;
		}
		.forbidden{
			display: none;
		}
		.tw-table-list tbody td{
			font-size:16px;
		}
		.scrollNav{
			position:fixed;
			top:0px;
			background-color: white;
		}
    </style>
</head>
<body>
	<div class="tw-layout">
		<div class="tw-list-top">
			<div class="tw-search-bar">
				<form name="doItemList"  id="doItemList" action="${ctx }/menurole_getMenuList.do" method="post">
				<div class="tw-top-tool">
					<a class="tw-btn" href="javascript:impower_menurole_list();" target="_self">
						<i class="tw-icon-cog"></i> 授权
					</a>
					<a class="tw-btn-danger" href="javascript:expower_menurole_list();">
						<i class="tw-icon-minus-circle"></i> 取消
					</a>
					<a class="tw-btn-danger" href="javascript:impowerAll_menurole_list()" >
						<i class="tw-icon-cogs"></i> 一键授权
					</a>
					
					<a class="tw-btn-danger" href="javascript:expowerAll_menurole_list()" >
						<i class="tw-icon-minus-circle"></i> 一键取消
					</a>
				</div>
					<label class="tw-form-label" for="">菜单名称：</label>
					<input type="text" class="tw-form-text" name="menuName" value="${menuName}" placeholder="输入关键字">
					<input type="hidden" name="roleId" value="${roleId}">
					<button class="tw-btn-primary" type="submit" style="padding: 5px 2px;"> 
						<i class="tw-icon-search" ></i> 搜索
					</button>
				</form>
			</div>
		</div>
        <div class="tw-container">
        <form id="thisForm" method="POST" name="thisForm" action="${ctx }/item_getItemList.do" >
            <table id="example-advanced" class="tw-table-normal tw-table-select" >
                <thead>
                    <tr>
			    		<th width="50%">菜单名称</th>
				    	<th width="10%">状态</th> 
				    	<th >是否授权</th>
			    	</tr>
                </thead>
	            <tbody>
	            	 <c:forEach var="item" items="${list}" varStatus="status">
	                    <tr target="selectId" rel="${item.menuId}" data-tt-id="${item.menuId}_menu_list"  
	                    	<c:if  test="${item.menuFaterId !=muneId}"> data-tt-parent-id="${item.menuFaterId}_menu_list"</c:if>>
	                    	<td style="width:20%;" class="tl" menuId="${item.menuId}">
		                        <c:if test="${item.havaChild}">
		           	     			<span controller="true">
		           	     				${item.menuName}
		           	     			</span>
		                        </c:if>
		                        <c:if test="${!item.havaChild}">
		                            ${item.menuName}
		                        </c:if>
	                    	</td>
	                    	<td align="center">
	                        	<c:choose>
		                            <c:when test="${item.menuStatus == '1' }">
		                                <span style="color: green;">启用</span>
		                            </c:when>
		                            <c:otherwise>
		                                <span style="color: red;">禁用</span>
		                            </c:otherwise>
		                        </c:choose>
		                    </td>
							<td id="${item.menuId}sq_menurole_list"  width="20%" align="center">
		                		<c:if test="${item.isGrant == 1}">
		                			<span style="color: green;">已授权</span>
		                		</c:if>
		                		
		                		<c:if test="${item.isGrant == 0}">
		                			<span style="color: red;">未授权</span>
		                		</c:if>
		                	</td>
	                    </tr>
	                </c:forEach>
	            </tbody>
            </table>
            </form>
        </div>
    </div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#example-advanced").treetable({ expandable: true }).treetable("expandAll");
  	});
	
	$(window).scroll(function() {
		//获取窗口的滚动条的垂直位置   
		var $topMenuScroll=$(window).scrollTop();
		if($topMenuScroll>=1){
			$(".tw-list-top").addClass("scrollNav");
		}else{
			$(".tw-list-top").removeClass("scrollNav");
		}
	});
</script>
<script type="text/javascript">
	function impower_menurole_list(){
		var roleId = '${roleId}';
		var menuId = $(".tw-actived td:eq(0)").attr("menuId");
		if(!menuId){
			layer.alert('请选择相应的菜单!');
			return;
		}
		//授权：ajax
		$.ajax({
			url : '${ctx}/menurole_saveMenuRole.do',
			type : 'POST',
			cache : false,
			async : false,
			error : function() {
				alert('链接异常，请检查网络');
			},
			data : {
				'roleId':roleId,
				'menuId':menuId
			},
			success : function(result) {
				var res = eval("("+result+")");
				if(res.success){
					layer.confirm('授权成功',{
						btn:['确定']
					},function(){
						window.location.reload();
					});
				}else{
					layer.alert("授权失败");
				}
			}
		});
	}
	
	function impowerAll_menurole_list(){
		if(confirm("您确定要一键授权吗？")){
			var roleId = '${roleId}';
			$.ajax({
				url : '${ctx}/menurole_saveAllMenuRole.do',
				type : 'POST',
				cache : false,
				async : false,
				error : function() {
					alert('链接异常，请检查网络');
				},
				data : {
					'roleId':roleId
				},
				success : function(result) {
					var res = eval("("+result+")");
					if(res.success){
						layer.confirm('一键授权成功',{
							btn:['确定']
						},function(){
							window.location.reload();
						});
					}else{
						alert("一键授权失败");
					}
				}
			});
		}
	}
	
	function expower_menurole_list(){
		var roleId = '${roleId}';
		var menuId = $(".tw-actived td:eq(0)").attr("menuId");
		if(!menuId){
			alert('请选择相应的菜单!');
			return;
		}
		//授权：ajax
		$.ajax({
			url : '${ctx}/menurole_removeMenuRole.do',
			type : 'POST',
			cache : false,
			async : false,
			error : function() {
				alert('链接异常，请检查网络');
			},
			data : {
				'roleId':roleId,
				'menuId':menuId
			},
			success : function(result) {
				var res = eval("("+result+")");
				if(res.success){
					layer.confirm('取消授权成功',{
						btn:['确定']
					},function(){
						window.location.reload();
					});
				}else{
					alert("取消授权失败");
				}
			}
		});
	}
	
	function expowerAll_menurole_list(){
		if(confirm("您确定要一键取消吗？")){
			var roleId = '${roleId}';
			$.ajax({
				url : '${ctx}/menurole_removeAllMenuRole.do',
				type : 'POST',
				cache : false,
				async : false,
				error : function() {
					alert('链接异常，请检查网络');
				},
				data : {
					'roleId':roleId
				},
				success : function(result) {
					var res = eval("("+result+")");
					if(res.success){
						layer.confirm('一键取消授权成功',{
							btn:['确定']
						},function(){
							window.location.reload();
						});
					}else{
						alert("一键取消授权失败");
					}
				}
			});
			
		}
	}
	</script>
</html>