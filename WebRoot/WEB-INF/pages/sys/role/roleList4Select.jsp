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
	<div class="tw-layout">
		<div class="tw-container tm-container">
		    <div class="tm-layout">
				<div class="tw-container">
					<form id="thisForm" method="POST" name="thisForm" action="${ctx}/role_getRoleList.do" >
						<table class="tw-table-list tw-table-select">
							<thead>
								<tr>
									<th width="5%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
									<th width="15%">序号</th>
									<th align="center">角色名称</th>
									<th width="25%">站点</th>
									<th align="center" width="20%">状态</th>
								</tr>
							</thead>
							<tbody>
						    	<c:forEach var="item" items="${list}" varStatus="status"> 
									<tr>
										<td align="center">
						    				<input type="checkbox" name="selid" value="${item.roleId}" >
						    			</td>
							        	<td align="center" id="${item.roleId}" class="item" >${status.count}</td>
							        	<td >${item.roleName}</td>
							        	<td align="center">${item.siteName}</td>
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
		</div>
	</div>
</body>
	<script type="text/javascript">
		
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
		
	    function getIds(){
	    	var selid = document.getElementsByName('selid');
        	var ids = "";
        	for(var i = 0 ; i < selid.length; i++){
        		if(selid[i].checked){
        			ids += selid[i].value + ",";
        		}
        	}
        	return ids;
	    }
	    
	    function setIds(ids){
	    	var selid = document.getElementsByName('selid');
	    	for(var i = 0 ; i < selid.length; i++){
	    		var si = selid[i].value;
	    		if(ids.indexOf(si)>-1){
	    			selid[i].checked = true;
	    		}else{
	    			selid[i].checked = false;
	    		}
	    	}
	    }
	    
	</script>
</html>