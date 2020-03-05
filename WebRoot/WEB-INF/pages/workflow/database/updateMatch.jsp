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
<base target="_self"/>
<body>
<form id="matchform"  name="matchform"  method="post" action="${ctx}/dataCenter_addBusMod.do" style="font-family: 宋体; font-size: 12pt;">
	<input type="hidden" id="dicId" name="dicId" value="${dicId}">
	<input type="hidden" id="id" name="id" value="${match.id}">
	<table style="height:100px;width:99%" border="0" align="center" cellpadding="0" cellspacing="0" class="contentTable">
       <!--  <tr>
          <td width="20%" height="35" align="right">数据字典代码：</td>
          <td width="50%"><input type="text" name="dm" id="dm"  onmousedown="changeClass('dmtip')"  maxlength="40" class="textInput width_205" /></td>
          <td width="30%" id="dmtip"><span style="color: red;">表名称&nbsp;&nbsp;&nbsp;&nbsp;*</span></td>
        </tr>  -->
        <tr>
          <td width="20%" height="35px" align="right" style="border:0px;">匹配表名称：</td>
          <td width="55%" style="border:0px;">
          <div style="margin-top:12px;width:100px; float:left;height:35px">
            <SELECT  name="tableCode"  id="tableCode"  onchange="choose();" style="width: 205px;height:22px;line-height:22px;font-size:12px">                 
            	<option value=""></option>
            	<c:forEach var="e" items="${tables}" varStatus="status"> 
                	<option value="${e.vc_tablename}"<c:if test="${e.vc_tablename eq match.tableCode }">selected</c:if>>${e.vc_tablename}</option>
                </c:forEach>
			</SELECT>
			</div>
		  </td>
		  <td width="25%" id="dmtip"><span style="color: red;">表名称&nbsp;&nbsp;&nbsp;&nbsp;*</span></td>
        </tr>
        <tr>
          <td width="20%" height="35" align="right">表单名：</td>
          <td width="50%"><input type="text" name="formName"  id="formName"  maxlength="50"  style="width:205px;"  value="${match.formName}" /></td>
          <td width="30%"><span style="color: red;">表单名&nbsp;&nbsp;&nbsp;&nbsp;*</span></td>
        </tr>
      	</table>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
		<ul class="mr5"> 
		<li><a class="buttonActive" onclick="javascript:saveMatch();" id="save" ><span>保存</span></a></li>
		<li><a class="buttonActive" onclick="javascript:window.close();"  ><span>关闭</span></a></li>
		
		</ul>
</div>
</form>
</body>
<script type="text/javascript">
function saveMatch(){
	var flag = true;
	var nullPattern=/(^\s*)|(\s*$)/g;//空值
	var tableCode =$('#tableCode').val();
	tableCode=tableCode.replace(nullPattern, "");
	if(tableCode==""){
		alert('匹配表名称不能为空!');
    	flag=false;
	}
	if(flag){
		$.ajax({   
	        url : '${ctx}/dataCenter_addMatch.do',
	        type : 'POST',   
	        cache : false,
	        async : false,
	        error : function() {  
	            alert('链接异常，请检查网络');
	        },
	        data : $('#matchform').serialize(),
	        success : function(data) {
	        	var res = eval("("+data+")");
	        	if(res){
					alert("保存成功");
					window.returnValue= "ok"; 
					window.close();
				}else{
					alert("保存失败");
				}
	        }
	    }); 
	}
}
function choose(){
	var vcName = document.getElementById("tableCode").value;
	$.ajax({   
        url : '${ctx}/dataCenter_getFormTableName.do',
        type : 'POST',   
        cache : false,
        async : false,
        error : function() {  
            alert('链接异常，请检查网络');
        },
        data : {
        	'vcName':vcName
        },
        success : function(data) {
        	var res = eval("("+data+")");
        	document.getElementById("formName").innerText=res.success;
        }
    }); 
}
</script>
<%@ include file="/common/function.jsp"%>
</html>
