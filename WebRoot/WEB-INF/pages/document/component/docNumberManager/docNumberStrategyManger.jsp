<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
<html>
    <head>
        <title>文号管理</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
		 <div class="pageContent">
		 	<div id="w_list_print">
				 <table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
					<thead><th>名称</th><th>内容</th><th>类型</th><th>描述</th><th></th></thead>
					<tbody>
					<c:forEach items="${slist}" var="s" varStatus="statu">
						<tr title="双击删除此行"><td ><input readonly="readonly" value="${s.strategyId}"/></td>
						<td ><span title="${s.content}"><input style="width:100%;" value="${s.content}"/></span></td>
						<td ><input readonly="readonly" value="${s.type}"/></td>
						<td ><span title="${s.description}"><input style="width:100%;" value="${s.description}"/></span></td>
						<td ><span title="单击序号增加一行"><input type="button" value="${statu.count}"/></span></td></tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<form id="delform" action="${ctx}/docNumberManager_delStrategy.do" method="post"><input type="hidden" name="id" id="fordelid"/></form>
		<script type="text/javascript">
			$("input[type=button]").one("click",function(){
				$(".displayTable tbody").append("<tr title='双击删除此行'><form action='${ctx}/docNumberManager_editStrategy.do' method='post'><td class='docType'><input maxlength='15' name='id'/></td><td class='docTitle'><input style=\"width:100%;\" maxlength='200' name='content'/></td><td class='docType'><input name='type' maxlength='4'/></td><td class='docNum'><input style=\"width:100%;\" name='description' maxlength='200'/></td><td class='docType'><input type='submit' value='提交'/></td></form></tr>");
			});
			$("tr").live("dblclick",function(){
				var e = e ? e: window.event;
			    var tar =e.srcElement||e.target;
				if(tar.nodeName.toLowerCase()==='tr'||tar.nodeName.toLowerCase()==='td'){
					$("#fordelid").val($(this).find("input:.del").val());
					if(confirm("确认删除!!")){
						if($("#fordelid").val()==''){
							$(this).remove();
						}else{
							$("#delform").submit();
						}
					}
				}
			});
		</script>
    </body>
</html>
