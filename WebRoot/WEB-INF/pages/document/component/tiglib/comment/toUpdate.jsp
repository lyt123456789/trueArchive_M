<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include  file="/common/header.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
	<title>个人用语维护</title>
	
	<link rel="stylesheet" type="text/css" href="${ctx}/widgets/component/taglib/comment/css/oa_submodule_common.css" />
	<script type="text/javascript" src="${ctx}/widgets/component/taglib/comment/js/oa_submodule_common.js"></script>
	<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
	<script type="text/javascript">
		function saveComment(){
			//校验排序字段
			var sort_index = $("#sort_index").val();
			if (sort_index == ""){
				alert("排序字段不能为空！");
				$("#sort_index").focus();
				return false;
			}
			
			var reg = /^[1-9]\d*$/;
			if (reg.test(sort_index)) {
				if (sort_index.length > 10){
					alert("排序字段不能大于10位！");
					$("#sort_index").focus();
					return false;
				}
			}else{
				alert("排序字段必须为正整数！");
				$("#sort_index").focus();
				return false;
			}
			
			var cmnt_id = $("#cmnt_id").val();
			var content = $("#content").val();
			content=content.replace(/^\s*/,'').replace(/\s*$/,'');
			if (content == ""){
				alert("意见不能为空！");
				$("#content").value='';
				$("#content").focus();
				return false;
			}
			
			
			
			var postData = "[{'cmnt_id':'" + cmnt_id + "', 'content':'" + encodeURIComponent(content) + "', 'sort_index':'" + sort_index + "'}]";
			
			$.ajax({
				async: true,
				type: "POST",
				cache: false,
				url: "comment_savePersonalComment.do",
				data: eval(postData)[0],
				error: function(){
			   		alert('AJAX调用错误');
				},
				success: function(msg){
					alert("保存成功");
					window.returnValue = 1;
					window.close();
				}
			});
		}
		
		window.onload=function(){
	        var cont=document.getElementById('cont');
	        var empty=document.getElementById('empty');
	        empty.style.height=Math.round((document.documentElement.offsetHeight-cont.offsetHeight)/2)+'px';
	    };
	</script>
</head>

<body>
	<div id="empty"></div>
	<div id="cont" style="width:100%">
	 	<table width="100%">
			<tr>
				<td width="20%" align="right">内容：</td>
				<td width="80%">
					<textarea id="content" rows="2" style="width:100%">${pc.content}</textarea>
			 		<input id="cmnt_id" type="hidden" value="${pc.cmnt_id}" />
				</td>
			</tr>
			<tr>
				<td align="right">排序：</td>
				<td><input id="sort_index" type="text" value="${pc.sort_index}"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="保存" onclick="return saveComment()">
					<input type="button" value="关闭" onclick="window.close();">
				</td>
			</tr>
		</table>
	</div>
</body>
</html>