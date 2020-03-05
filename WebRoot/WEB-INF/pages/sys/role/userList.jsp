<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
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
	<script src="${ctx}/widgets/js/index.js" type="text/javascript"></script>
</head>
<body>
    <div class="tw-layout">
        <div class="tw-list-top">
            <div class="tw-search-bar">
				<form name="doItemList"  id="doItemList" action="${ctx}/roleUser_getUserList.do" method="post">
					<c:if test="${roleId!=null && roleId!=''}">
						<div class="tw-top-tool">
							<a class="tw-btn" href="javascript:toAddUser();" target="_self">
								<i class="tw-icon-plus-circle"></i> 添加
							</a>
							<a class="tw-btn-danger" href="javascript:del()" target="_self" title="确定要删除吗？" warn="请选择一个对象">
								<i class="tw-icon-minus-circle"></i> 删除
							</a>
						</div>
					</c:if>
					<label class="tw-form-label" for="">人员姓名：</label>
					<input type="text" class="tw-form-text" name="itemName" value="${itemName}" placeholder="输入关键字">
					<input type="hidden" name="roleId" value="${roleId}" >
					<button class="tw-btn-primary" type="submit">
						<i class="tw-icon-search"></i> 搜索
					</button>
				</form>
			</div>
		</div>
		<div class="tw-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/item_getItemList.do" >
			<table class="tw-fixtable" layoutH="140" id="menu_list_tb">
				<thead>
		    	<tr>
		    		<th width="5%">序号</th>
			    	<th >所属部门</th>
			    	<th width="50%">人员名称</th>
		    	</tr>
		    	</thead>
			    <tbody>
			    	<c:forEach var="item" items="${roleUserList}" varStatus="status">
			    		<tr>
					        <td align="center" id="${item.guId}" class="item" >${status.count}</td>
					        <td align="center">${item.deptName}</td>
					        <td align="center">${item.userName}</td>
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
/* 	function toAddUser(){
		var roleId = '${roleId}';
		layer.open({
	         title: '人员管理',
	         area: ['700px', '558px'],
	         type: 2,
	         content: '${ctx}/roleUser_toAddUserToRole.do?roleId='+roleId
	     });
	} */
	function toAddUser(){
	     var value = new Array();
	   	//值域赋值的回调方法
	   	 //该方法供openTreeAndPassValues回调
	     var setJsonToArea = function(array){
	    	var roleId = '${roleId}';
	    	var idValues="";
	     	$(array).each(function(i,e){
	     		idValues += e.id+";";
	     	});
			if(idValues.length > 0){
				idValues = idValues.substring(0, idValues.length - 1);
				$.ajax({
					url: '${ctx}/roleUser_addUserToRole.do',
		            type: 'POST',
		            cache: false,
		            async: false,
		            data:{
		            	"roleId":roleId,
		            	"userId":idValues
		            },
				    success: function(msg){
				    	if("success" == msg){
				    		parent.layer.confirm('新增成功',{
								btn:['确定']
							},function(){
								parent.location.reload();
								parent.layer.closeAll();
							});
						}
				    },
				    error: function(){
				        alert('AJAX调用错误');
				    }
				});
			}else{
				layer.alert("请选择添加人员");
			}
	    };
	    parent.openTreeAndPassValues(850,480,value,setJsonToArea);
	}
	
	function del(){
		var id=$(".tw-actived td:eq(0)").attr("id");
		if(id!=null && id!=''){
			layer.confirm('确定要删除吗？',{
				btn:['确定','取消']
			},function(){
	    		$.ajax({
	    		    async:true,//是否异步
	    		    type:"POST",//请求类型post\get
	    		    cache:false,//是否使用缓存
	    		    dataType:"text",//返回值类型
	    		    data:{"id":id},
	    		    url:"${ctx}/roleUser_deleteRoleUser.do",
	    		    success:function(text){
						if(text=='success'){
							layer.confirm('删除成功',{
								btn:['确定']
							},function(){
		    		        	window.location.reload();
							});
	    		        }
	    		    }
	    		});
	    	});
		}else{
    		layer.alert('请选择一个删除对象');
    		return;
		}
	}
</script>
</html>