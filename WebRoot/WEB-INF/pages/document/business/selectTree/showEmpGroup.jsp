<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
<head>
<title>选择常用组</title>
		<link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
</head>
<base target="_self">
<body>
	<table class="displayTable">
		<thead>
		<tr>
			<th class="split">组名</th><th class="split">操作</th>
		</tr>
		</thead>
		<c:forEach items="${groups}" var="group">
			<tr><td class="docTitle"><a href="#" onclick="javascript:getGroup('${group.emp}');">${group.name}</a></td><td class="docType"><a href="javascript:deleteGroup('${group.id}');"><span title="点击删除该组" style="display:inline-block;width:16px;height:16px;cursor:pointer;background:url(${ctx}/widgets/theme/dm/images/icon_main.gif);background-position: 0 -20px;"></span></a></td></tr>
		</c:forEach>
	</table>
	<a id="flush" href="#"></a>
	<script src="${cdn_js}/base/jquery.js"></script>
	<script src="${cdn_js}/sea.js"></script>
	<script type="text/javascript">
		function deleteGroup(id){
			$.ajax({
			    url: 'selectTree_deleteEmpGroup.do',
			    type: 'POST',
			    cache:false,
			    async:false,
			    data:{'id':id},
			    error: function(){
			        alert('AJAX调用错误');
			    },
			    success: function(msg){
					if(msg === "SUCCESS") alert("删除成功");
					if(msg === "FAIL") alert("删除失败"); 
			    }
			});
			document.getElementById('flush').href='${ctx}/selectTree_showEmpGroup.do';
			document.getElementById('flush').click();
		}
		
		function getGroup(emp){
			window.returnValue = emp;
			window.close();
		}
		
		seajs.use('lib/hovercolor',function(){
			$('table.displayTable').hovercolor({target:'tbody>tr'});
	    });
	</script>
</body>
</html>