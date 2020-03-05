<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
<html>
	<head>
		<title>主页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	</head>

	<body>
		<div class="pageContent">
			<form id="bindForm" action="${ctx}/docNumberManager_bindDocNumDoc.do" method="post">
				<input type="hidden" value="" id="whmodelids" name="whmodelids"/>
				<input type="hidden" value="" id="allmodelids" name="allmodelids"/>
				<input type="hidden" value="${workflowId}" id="" name="defineid"/>
				<div id="w_list_print">
					<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
						<tr>
							<td>
								选择文号:
							</td>
							<td>
								<table style="border-collapse: collapse;width: 100%" class="list">
									<c:forEach var="model" items="${whmodels}" varStatus="statu">
										<c:if test="${statu.index%3 eq 0}">
											<script type="text/javascript">
							             		document.write("<tr>");
							             	</script>
										</c:if>
										
										<td style="text-align: left;padding: 0 0 0 5px;">
											<input name="chc" type="checkbox" value="${model.modelid }"/>
											<script type="text/javascript">
							             		WHContent='${model.content}';
							             		tempWH=WHContent.match(/^.*;/)[0].replace(/,/g,"");
							             		document.write(tempWH.substr(0,tempWH.length-1));
							             	</script>
										</td>
										<c:if test="${statu.count%3 eq 0}">
											<script type="text/javascript">
							             		document.write("</tr>");
							             	</script>
										</c:if>
									</c:forEach>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<input id="bindDocNum" type="button" value="绑 定" class="btn"/>
								<!--
								&nbsp;&nbsp;
								<input id="back" type="button" value="返 回" onclick="window.history.back()" class="btn"/>
							-->
							</td>
						</tr>
					</table>	
				</div>		
		    </form>
	    </div>
	    <script type="text/javascript">
		    window.onload=function(){
			    if('${mes}'!=''){
					alert('${mes}');
				}
			};
			
	    	$(function(){
	    		var whdocs='${whdocs}';
				if(whdocs!=''&&whdocs.length>0){
					var chcs=document.getElementsByName('chc');
					for(var j=0;j<chcs.length;j++){
						if(whdocs.indexOf(chcs[j].value)!==-1){
							chcs[j].checked=true;
							//alert(chcs[j].value);
						}
					}
				}
			});
			$("#bindDocNum").bind("click",function (){
				var chcs=document.getElementsByName('chc');
				var doctypeids='';
				var allTypeIds = '';
				for(var i=0;i<chcs.length;i++){
					if(chcs[i].checked==true){
						doctypeids+=i==chcs.length-1?chcs[i].value:chcs[i].value+',';
					}
					allTypeIds +=i==chcs.length-1?chcs[i].value:chcs[i].value+','
				}
				$('#allmodelids').val(allTypeIds);
				$('#whmodelids').val(doctypeids);
				$('#bindForm').submit();
			});
			
			$("#back").bind("click",function(){
				window.location.href="${ctx}/docNumberManager_define4bind.do";
			});
		</script>
	</body>
</html>