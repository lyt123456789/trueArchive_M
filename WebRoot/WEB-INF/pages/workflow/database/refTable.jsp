<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
<body >
   <div align="center" style="padding-top: 40px;">
    	<select name="id" id="id" style="width: 250px">
    		<option value="">请选择</option>
    		<c:forEach items="${list}" var="d">
    			<option value="${d.id}">${d.vc_tablename}{${d.vc_name}}</option>
    		</c:forEach>
    	</select>
   </div>
</body>
    <script>
    	function ref(){
        	var id = document.getElementById('id').value;
    		if(""!=id){
    			$.ajax({
    				url:"${ctx}/table_ref.do",
    				type:"post",
    				data:{"id":id,"lcid":"${lcid}"},
    				async : false
    			});
    			window.returnValue=true;
    			window.close();
    		}else{
    			alert("请选择表名");
    		}
        }
    </script>
    <%@ include file="/common/function.jsp"%>
</html>
