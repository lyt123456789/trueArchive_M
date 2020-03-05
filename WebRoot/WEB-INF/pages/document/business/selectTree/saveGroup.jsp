<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
<head>
<title>选择发文单位</title>
<base target="_self">
</head>
<body>
	<table>
			<tr><td><span>输入组名</span></td><td><input id="name" type="text" /></td><td><input id="save" type="button" value="保存"/></td></tr>
	</table>
	<script src="${cdn_js}/base/jquery.js"></script>
	<script type="text/javascript">
		var obj = window.dialogArguments;
		$("#save").bind("click",function(){
			$.ajax({
			    url: 'selectTree_saveGroup.do',
			    type: 'POST',
			    cache:false,
			    async:false,
			    data:{'dep':obj.name,'name':$('#name').val()},
			    error: function(){
			        alert('AJAX调用错误');
			    },
			    success: function(msg){
					if(msg === "SUCCESS"){ alert("新增成功"); window.close();};
					if(msg === "FAIL"){ alert("新增失败"); };
			    }
			});
		});
	</script>
</body>
</html>