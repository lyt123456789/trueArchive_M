<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="keywords" content="">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>   
 	<link href="${ctx}/assets-common/plugins/jquery.treetable/jquery.treetable.css" rel="stylesheet" type="text/css" media="screen"/>
    <script src="${ctx}/assets-common/plugins/jquery.treetable/jquery.treetable.js" type="text/javascript"></script>
    <script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
    <style type="text/css">
    	.display{
			display: none;
		}
		.tw-disabled td {
		    background-color:#f2f2f2!important;
		    cursor:not-allowed;
		    color: #76838f;
		}
		.tw-icon-lock {margin-right:5px;}
    </style>
</head>
<body>
<div class="tw-layout">
	<div class="tw-list-top">
		<div class="tw-search-bar">
			<form name="menuList"  id="menuList" action="${ctx }/menu_getMenuList.do" method="post">
			<div class="tw-top-tool">
	            <a class="tw-btn" href="javascript:toAdd();">
	                <i class="tw-icon-plus-circle"></i> 新建
	            </a>
	            <a class="tw-btn-primary" href="javascript:toUpdate();">
	                <i class="tw-icon-pencil"></i> 修改
	            </a>
	            <a class="tw-btn-danger" href="javascript:del()" target="_self" title="确定要删除吗？" warn="请选择一个对象">
	                <i class="tw-icon-minus-circle"></i> 删除
	            </a>
				<a class="tw-btn-purple display" id="enabled" href="javascript:updateStatus('1')" >
					<i class="tw-icon-play-circle-o"></i> 启用
				</a>
				<a class="tw-btn-orange display" id="forbidden" href="javascript:updateStatus('0')" >
					<i class="tw-icon-close"></i> 禁用
				</a>
	             <!-- <a class="tw-btn-primary" href="#" onclick="jQuery('#example-advanced').treetable('expandAll'); return false;">
	                <i class="tw-icon-pencil"></i> Expand all
	            </a>
	             <a class="tw-btn-primary" href="#" onclick="jQuery('#example-advanced').treetable('collapseAll'); return false;">
	                <i class="tw-icon-pencil"></i> Collapse all
	            </a> -->
	        </div>
	            <label class="tw-form-label" for="">菜单名称：</label>
	            <input type="text" class="tw-form-text" name="menuName" value="${menuName}" placeholder="输入关键字">
	            <button class="tw-btn-primary" type="submit">
	                <i class="tw-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
        <div class="tw-list-wrap">
            <table id="example-advanced" class="tw-table-normal tw-table-select" layoutH="95">
                <thead>
                    <tr>
                        <th width="15%">菜单名称</th>
			    		<th>菜单链接</th>
			    		<th width="10%">外部链接</th>
			    		<th width="10%">排序号</th> 
			    		<th width="18%">状态</th>
                    </tr>
                </thead>
	            <tbody>
	            	 <c:forEach var="item" items="${list}" varStatus="status">
	                    <tr target="selectId"  data-tt-id="${item.menuId}_menu_list"
	                    	<c:if test="${item.menuFaterId !=muneId}"> data-tt-parent-id="${item.menuFaterId}_menu_list"</c:if>
	                    	<c:choose>
	                            <c:when test="${item.disabled eq true}">
	                                class="tw-disabled"
	                            </c:when>
	                            <c:otherwise>
	                                onclick="changeBusMod(${item.menuStatus},${item.isForbidenGrant});"
	                            </c:otherwise>
	                        </c:choose>
	                    	rel = '${item.menuId}'>
	                    	<td style="width:20%;" class="tl" menuId="${item.menuId}">
	                    		<c:if test="${item.disabled eq true}"><i class="tw-icon-lock"></i></c:if>
		                        <c:if test="${item.havaChild}">
		           	     			<span controller="true">
		           	     				${item.menuName}
		           	     			</span>
		                        </c:if>
		                        <c:if test="${!item.havaChild}">
		                            ${item.menuName}
		                        </c:if>
		                    </td>
		                    <td class="tl">
		                        &nbsp;&nbsp;&nbsp;&nbsp;
		                        <c:choose>
		                            <c:when test="${item.menuExtLinks == '1' }">${item.foreignAppAddress}/${item.menuUrl}</c:when>
		                            <c:otherwise>${item.menuUrl}</c:otherwise>
		                        </c:choose>
		                    </td>
		                    <td align="center">
		                        <c:choose>
		                            <c:when test="${item.menuExtLinks == '1' }">
		                                		是
		                            </c:when>
		                            <c:otherwise>
		                     				           否
		                            </c:otherwise>
		                        </c:choose>
		                    </td>
		                    <td  align="center">
		                        ${item.menuSort}
		                    </td>
		                    <td  align="center">
		                        <c:choose>
		                            <c:when test="${item.menuStatus == '1' }">
		                                <span style="color: green;">启用</span>
		                            </c:when>
		                            <c:otherwise>
		                                <span style="color: red;">禁用</span>
		                            </c:otherwise>
		                        </c:choose>
		                    </td>
	                    </tr>
	                </c:forEach>
	            </tbody>
            </table>
        </div>
    </div>
<script type="text/javascript">
 $(document).ready(function() {
      $("#example-advanced").treetable({ expandable: true }).treetable("expandAll");
  })
</script>
<script type="text/javascript">
	//新增菜单
	function toAdd(){
		var father_menuId=$("#selectId").val();
		parent.layer.open({
			title: '新增菜单',
			area: ['600px', '380px'],
			type: 2,
			content: '${ctx}/menu_toAdd.do?father_menuId='+father_menuId
		});
	}
	//修改菜单
	function toUpdate(){
		var menuId=$(".tw-actived td:eq(0)").attr("menuId");
		if (null != menuId && "" != menuId) {
			parent.layer.open({
				title: '修改菜单',
				area: ['600px', '380px'],
				type: 2,
				content: '${ctx}/menu_toUpdate.do?menuId='+menuId
			});
		} else {
			layer.alert("请选择一个修改对象");
		}
		 
	}
	
	function changeBusMod(menuStatus,isForbidenGrant){
		$("#forbidden").addClass("display");
		$("#enabled").addClass("display");
		if(0 == isForbidenGrant){
			if(1 == menuStatus){
				$("#forbidden").removeClass("display");
			}
			if(0 == menuStatus){
				$("#enabled").removeClass("display");
			}
		}
	}
	
	function updateStatus(menuStatus){
		var menuId=$(".tw-actived td:eq(0)").attr("menuId");
		if(menuId!=null && menuId!=''){
	    	$.ajax({
				async:true,//是否异步
				type:"POST",//请求类型post\get
				cache:false,//是否使用缓存
				dataType:"text",//返回值类型
				data:{
					"menuId":menuId,
					"menuStatus":menuStatus
				},
				url:"${ctx}/menu_updateMenu.do",
				success:function(result){
					var res = eval("("+result+")");
					if(res.success){
						if(menuStatus=="1"){
							$("tr[rel='"+menuId+"']").find("td:last").find("span").replaceWith('<span style="color: green;">启用</span>');
							$("#enabled").addClass("display");
							$("#forbidden").removeClass("display");
						}else{
							$("tr[rel='"+menuId+"']").find("td:last").find("span").replaceWith('<span style="color: red;">禁用</span>');
							$("#forbidden").addClass("display");
							$("#enabled").removeClass("display");
						}
						layer.alert('操作成功');
					}
				}
	    	});
		}
	}
	
	function del(){
		var menuId=$(".tw-actived td:eq(0)").attr("menuId");
		if(menuId!=null && menuId!=''){
			layer.confirm('确定要删除吗？',{
				btn:['确定','取消']
			},function(){
		    	$.ajax({
					async:true,//是否异步
					type:"POST",//请求类型post\get
					cache:false,//是否使用缓存
					dataType:"text",//返回值类型
					data:{
						"menuId":menuId
					},
					url:"${ctx}/menu_deleteMenu.do",
					success:function(result){
						var res = eval("("+result+")");
						if(res.success){
							layer.confirm('删除成功',{
								btn:['确定']
							},function(){
								window.location.reload();
							});
						}
					}
		    	});
			});
		}
	}
</script>   

</body>
</html>