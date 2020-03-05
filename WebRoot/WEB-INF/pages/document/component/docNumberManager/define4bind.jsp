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
		<table class="searchContent">
			<thead>
	            <tr class="split">
	                <th>&nbsp;流程名称</th>
	                <th>&nbsp;操 作</th>
	            </tr>
			</thead>
			<c:choose>
                <c:when test="${not empty defines}">
                    <c:forEach var="define" items="${defines}">
                        <tr>
                            <td >${define.wfName }</td>
                            <td >
                            	<a href="javascript:gotobangdingWH('${define.wfUid}','${define.wfName}')">绑定文号</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="2">目前本系统还没有定义工作流</td>
                    </tr>
                </c:otherwise>
            </c:choose>
		</table>
	
        <form id="toSelectWh" action="${ctx}/docNumberManager_docNumberBindDefine.do" method="post">
  			<input type="hidden" value="" id="defineid" name="defineid"/>
  			<input type="hidden" value="" id="definename" name="definename"/>
        </form>
        <script type="text/javascript" src="${cdn_js}/base/jquery.js"></script>
        <script src="${cdn_js}/sea.js"></script>
        <script type="text/javascript">
			window.onload=function(){
				//setDifStyleForTable('tbl-main');
			   // setFocusStyleForTable('tbl-main');
			    if('${mes}'!=''){
					alert('${mes}');
				}
			};

			function gotobangdingWH(id,name){
				document.getElementById('defineid').value=id;
				document.getElementById('definename').value=name; 
				document.getElementById('toSelectWh').submit();
			}
			seajs.use('lib/hovercolor',function(){
   				$('table.displayTable').hovercolor({target:'tbody>tr'});
   		    });
		</script>
	</body>
</html>
