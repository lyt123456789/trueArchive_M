<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%
        List carlist = (List) request.getAttribute("plancarlist"); 
%>
<html>
	<head>
		<title></title>
		<link href="${ctx}/portal/style/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/portal/style/button/ued.button.css" rel="stylesheet"type="text/css" />
		<link href="${ctx}/portal/style/demo.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
		.bdtab td {
    border: 1px solid #CCCCCC;
    color: #122E67;
    height: 30px;
    line-height: 30px;
    vertical-align: middle;
}
		.tdbg {
    background: none repeat scroll 0 0 #E8EDF3;
    text-align: right !important;
    vertical-align: middle !important;
}
		
		</style>
	<script>
		var carno;
		var car;
		function chooseCar(id){
			carno = id;
			alert("请假人选择成功");
		}
		function cdv_getvalues()
		{
			return carno;
			 
		}
		window.onload=function(){ 
			//获得后台分页相关参数-必须
			//car = '<%=request.getAttribute("personlist").toString()%>';
			//car = car.substring(1,car.length-1);
			//var carlist = car.split(",");
			//carno = carlist[0];
		}

	</script>

	
	<body > 
	<span style="color:red;">*点击人员名称,选择请假人员</span>
	<table   class="bdtab tltd mt5"  style="width: 100%;" id="table1">
	<thead>
			<tr>
				<!-- <th width="50%" align="center"><span class="fb pl10">申请车牌号</span></th>
				<th width="50%" align="center"><span class="fb pl10">操作</span></th> -->
		<!-- 	<td   class="tdbg"  align="center"  style="width: 5% ">序号</td>	 -->
			<td     style="text-align:center !important;"  class="tdbg"  align="center"  style="width: 50% ">请假人员</td>	
			<td     align="center"  style="width: 50% "  >操作</td>
			</tr>
		</thead>	
		<tbody>
		<c:forEach var="item" items="${personlist}" varStatus="status"> 
		
			<tr>
			<%-- 	<td align="center" >${status.index+1 }</td> --%>
				<td  style="text-align:center !important;" class="tdbg"   onclick="chooseCar('${item.EMPLOYEE_GUID }*${item.EMPLOYEE_NAME }')">${item.EMPLOYEE_NAME }</td>
				<td   align="center"  onclick="chooseCar('${item.EMPLOYEE_GUID }*${item.EMPLOYEE_NAME }')">选择</td>
			</tr>
			</c:forEach>
			<tbody>
			</table>
		 
</body>
</html>


