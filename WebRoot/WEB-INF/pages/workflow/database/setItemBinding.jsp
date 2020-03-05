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
			<form name="thisForm"  id="thisForm" action="${ctx}/item_setItemBinding.do" method="post">
			<div class="wf-top-tool">
	            <a class="wf-btn-primary" href="javascript:addItemBinding();">
	                <i class="wf-icon-pencil"></i> 绑定
	            </a>
	        </div>
	            <label class="wf-form-label" for="">事项类别：</label>
	            <input type="text" class="wf-form-text" name="itemName" value="${itemName}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="submit">
	                <i class="wf-icon-search"></i> 搜索
	            </button>
            </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<form id="bindItemForm" method="POST" name="bindItemForm" action="${ctx}/item_setItemBinding.do" >
			<table class="wf-fixtable" layoutH="140">
				<thead>
		    	<tr>
		    		<th width="5%"><input type="checkbox" name="selAll" id="selAll" onclick="sel();"></th>
		    		<th width="5%">序号</th>
			    	<th >事项类别</th>
			    	<th width="18%">所属部门</th>
			    	<th width="12%">创建时间</th>
			    	<th width="5%">排序号</th>
		    	</tr>
		    	</thead>
		    	<c:forEach items="${itemList}" var="d" varStatus="n">
			    	<tr target="sid_user" rel="1">
			    		<td align="center">
			    			<input type="checkbox" name="selid" value="${d.id}"  <c:if test="${d.isChecked == '1' }">checked</c:if>>
			    		</td>
				    	<td align="center" itemid="${d.id}">
				    		${n.count}
				    	</td>
				    	<td>
      						${d.vc_sxmc}  
				    	</td>
				    	<td align="center">
				    		${d.vc_ssbm}
				    	</td>
				    	<td  align="center">
				    		${fn:substring(d.c_createdate,0,16)} 
				    	</td>
				    	<td align="center">${d.index}</td>
			    	</tr>
		    	</c:forEach>
			</table>
		</form>
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
	
	
	//将流程绑定
	function addItemBinding(){
		if(confirm("确定将这些事项绑定到该部门吗？")){
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
				alert('请至少选择一个绑定事项');
				return;
			}
			//ajax调用
			$.ajax({
				url : '${ctx}/item_addItemBinding.do?ids='+ids,
				type : 'POST',  
				cache : false,
				async : false,
				error : function() {
				alert('AJAX调用错误(item_addItemBinding.do)');
					return false;
				},
				success : function(msg) {   
					if(msg =='success'){
						alert("绑定成功！");
						window.location.href = '${ctx}/item_setItemBinding.do' ;
					}
				}
			});
		}
	}
</script>
</html>