<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>文号管理</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <link rel="stylesheet" type="text/css" href="${ctx}/widgets/${webId}/${indexType}/css/oa_submodule_common.css" />
    </head>
    <body>
	    <div class="w-here">
			<div class="w-here-box"><span>文号管理&nbsp;→&nbsp;策略维护</span></div>
		</div>
		<form action="">
			<table>
				<tr><td><input id="id" name="strategy.strategyId" value="${s[0]}"/></td></tr>
				<tr><td><input id="type" name="strategy.type" readonly="readonly" value="${s[2]}"/></td></tr>
				<tr><td><input id="content" name="strategy.content" readonly="readonly" value="${s[3]}"/></td></tr>
				<tr><td><input id="des" name="strategy.description" id="${s[0]}" type="button" value="${statu.count}"/></td></tr>
				<tr><td><input id="" type="button" value="提交"/></td></tr>
			</table>
		</form>
		<script type="text/javascript" src="${cdn_js}/jquery.min.js"></script>
    	<script type="text/javascript">
    	$("input[type=button]").bind("click",function(){
				var id = $("#id").val().trim();
				var type = $("#type").val().trim();
				var content = $("#content").val().trim();
				var des = $("#des").val().trim();
				try{
					if(id.length==0||type.length==0||des.length==0){
						alert("内容不全");
						return false;
					}else{
						
					}
				}catch(e){
					
				}
	        });
    	</script>
    </body>
</html>
