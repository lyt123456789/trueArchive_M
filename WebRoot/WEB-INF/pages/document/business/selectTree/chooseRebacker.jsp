<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerindex.jsp"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <!--表单样式-->
		<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
</head>
    <body style="overflow:auto;">
		<table  class="list"  width="340px;" >
			<tr><td align="left" colspan="2" style="font-weight:bold;">请选择回复部门:</td></tr>
			<tr><td align="left" colspan="2" style="font-weight:bold;">全选<input type="checkbox" name="selAll" id="selAll" onclick="selDb();"/></td>
				<tr>
					<c:forEach var="item" items="${list}" varStatus="status"> 
						<td><input  name="${item[0]}" type="checkbox" id="${item[0]}" value="${item[1]}"/>${item[1]}</td>
			<c:if test="${status.count %2 == 0}">
				</tr>
				<tr>
			</c:if>
			<c:if test="${status.count %2 == 1 && status.count==size}">
				<td>&nbsp;</td>
			</c:if>
					</c:forEach>
			</tr>
			<tr>
			    <td valign="top" align="center" colspan="2">
				    <input type="button" name="button" value="确定" onclick='send();'/>
			    </td>
			</tr>
		</table>
    </body>
<script src="${curl}/widgets/plugin/js/base/jquery.js"></script>
<script type="text/javascript">
function send(){
	 
	var chcs=document.getElementsByTagName("input");
	var depValue='';
	for(var i=0;i<chcs.length;i++){
		if(chcs[i].type=="checkbox"&&chcs[i].checked==true&&chcs[i].id != 'selAll'){
			depValue+= chcs[i].id+',';
		}
	}
	 if(depValue!=''){
		 depValue = depValue.substr(0, depValue.length-1);
		  window.returnValue = depValue;
			window.close();
	    }else{
	    	alert("请选择回复部门");
	    }
	  
}


//选中下拉框
function selDb(){
	var selAll = document.getElementById('selAll');
	var chcs=document.getElementsByTagName("input");
	for(var i=0;i<chcs.length;i++){
		if(chcs[i].type=="checkbox" &&chcs[i].id != 'selAll' ){
			if(selAll.checked){
				chcs[i].checked = true;
			}else{
				chcs[i].checked = false;
			}
		}
	}
}

</script>
</html>