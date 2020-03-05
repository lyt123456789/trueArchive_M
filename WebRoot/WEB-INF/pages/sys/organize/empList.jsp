<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="keywords" content="">
    <%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>
</head>
<body>
<div class="tw-layout">
	<div class="tw-list-top">
		<div class="tw-search-bar">
			<form name="pendlist"  id="pendlist" action="${ctx}/organize_getEmployeeList.do" method="post">
 	        <label class="tw-form-label" for="">姓名：</label>
                <input name="depId" id="depId" value="${depId }" type="hidden"/>
                <input type="text" class="tw-form-text" id="wfTitle" name="name" value="${name}" placeholder="输入关键字">
	            <button class="tw-btn-primary" type="submit">
	                <i class="tw-icon-search"></i> 搜索
	            </button>
	            
	            <button class="tw-btn-primary" onclick="init('${depId}');">
	                <i class="tw-icon-pencil"></i> 初始化
	            </button>
            </form>
		</div>
	</div>
	<div class="tw-list-wrap">
	<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_getPendingList.do" >
			<table class="tw-fixtable" layoutH="140">
				<thead>
		    	  	<tr>
                        <th align="center" width="4%">序号</th>
						<th width="20%">姓名</th>
						<th width="10%">用户名</th>
						<th width="8%">性别</th>
						<th width="15%">电话号码</th>
                    </tr>
		    	</thead>
		    	<tbody>
				    <c:forEach var="item" items="${list}" varStatus="status"> 
			    		<tr>
					       <td align="center" >${status.count}</td>
					       <td align="center">${item[0]}</td>
					       <td align="center">${item[1]}</td>
					       <td align="center">${item[2]}</td>
					       <td>${item[3]}</td>
					     </tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div class="tw-list-ft" id="pagingWrap">
		
	</div>
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
<script type="text/javascript">
function init(depId){
	//var index = layer.load(2,{shade:[0.8,'#393d49']});
	$.ajax({
		url:"${ctx}/organize_initSpellNew.do",
		type: 'POST',
		cache: false,
	    async: false,
		data:{"depId":depId},
		success:function(result){
			//layer.close(index);
			alert("初始化成功！");
		},
	    error: function(){
	        alert('AJAX调用错误');
	    },
	});
}
</script>
<%@ include file="/common/widgets.jsp"%>
</html>