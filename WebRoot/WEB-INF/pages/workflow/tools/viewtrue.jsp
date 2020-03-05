<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	 <link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	 <link rel="stylesheet" href="${ctx}/css/base.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/css/common.css" type="text/css" />
	<script type="text/javascript">
	function upload(){
		var path = document.getElementById("file").value;
		var orgFileName = path.substring(path.lastIndexOf("\\")+1,path.length);
		if ('' == path){
			alert("请至少选择一个文件！");
			return;
		}
		document.getElementById("orgFileName").value = orgFileName;
		document.getElementById("showform").submit();
	}
	</script>
</head>
<body>
<form  id="showform" action="${ctx}/supportTools_showTrueFile.do" enctype="multipart/form-data">
<input type="hidden" name="orgFileName" id="orgFileName">
	<p class="cbo m10">pdf与true文件阅读器： 预览文件：<input type="file" name="file" id="file" value="${file}" style="width:220px;"/>
	<input type="button" onclick="upload();" value="预览"> 
</form>
</body>
</html>