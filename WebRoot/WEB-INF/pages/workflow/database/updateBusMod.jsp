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
<form id="busModform"  name="busModform"  method="post" action="${ctx}/dataCenter_addBusMod.do" style="font-family: 宋体; font-size: 12pt;">
<input type="hidden" name="busMod.id" id="busMod.id"  value="${busMod.id}"/>
<input type="hidden" name="busMod.createUser" id="busMod.createUser"  value="${busMod.createUser}"/>
<input type="hidden" name="busMod.status" id="busMod.status"  value="${busMod.status}"/>
<table class="infotan" width="100%">
	<tr>
		  <td align="right">数据库简称：</td>
		  <td align="left" width="315px"><input type="text" name="busMod.modCode" id="busMod.modCode"   maxlength="40" class="textInput width_205" value="${busMod.modCode}"/></td>
		  <td align="left" width="150px" ><span style="color: red;">*</span></td>
	</tr>
	<tr>
		<td align="right">数据库名称：</td>
	    <td align="left"><input type="text" name="busMod.modName"  id="busMod.modName"   maxlength="50"  class="textInput width_205" value="${busMod.modName}"/></td>
	    <td align="left" ><span style="color: red;">*</span></td>
	</tr>
	<!-- <tr>
		<td height="35" align="right">数据库名称：</td>
	    <td align="left"><input type="text" name="busMod.dataName"  id="busMod.dataName" maxlength="500"  class="textInput width_205" value="${busMod.dataName}"/></td>
	    <td></td>
	</tr> -->
	<tr>
		  <td height="35" align="right">数据库地址：</td>
          <td align="left">
	          jdbc:oracle:thin:@<input type="text" name="busMod.dataAddr" value="${busMod.dataAddr}" id="busMod.dataAddr" maxlength="1000" style="width:170px;" class="textInput width_205" value="${busMod.dataAddr}"/>
	      </td>
      	  <td></td>
	</tr>
	<tr>
	  <td height="35" align="right">账号：</td>
	  <td align="left"><input type="text" name="busMod.dataAccount"  id="busMod.dataAccount"  maxlength="500"  class="textInput width_205" value="${busMod.dataAccount}" /></td>
	  <td align="left" ><span style="color: red;">*</span></td>
	</tr>
	<tr>
	  <td height="35" align="right">密码：</td>
	  <td align="left"><input type="password" name="busMod.dataPassword"  id="busMod.dataPassword"  maxlength="500"  class="textInput width_205" value="${busMod.dataPassword}" /></td>
	  <td align="left" ><span style="color: red;">*</span></td>
	</tr>
	
	
</table>

<div class="formBar pa" style="bottom:0px;width:100%;">  
<ul class="mr5"> 
<li><a class="buttonActive" onclick="saveBusMod();" id="save" style="display:none;"><span>保存</span></a></li>
<li><a class="buttonActive" onclick="testConn();" id="testconn" ><span>测试</span></a></li>
<li><a class="buttonActive" onclick="javascript:window.close();"  ><span>关闭</span></a></li>
</ul>
</div>
</form>
</body>
<script type="text/javascript">
function saveBusMod(){
	$.ajax({   
       url : '${ctx}/dataCenter_addBusMod.do',
       type : 'POST',   
       cache : false,
       async : false,
       error : function() {  
    	   alert("更新失败！");
       },
       data : $('#busModform').serialize(),    
       success : function(data) {
       	    var res = eval("("+data+")");
	       	if(res){
	       		alert("更新成功！");
	       		window.returnValue= "ok"; 
	       	   	window.close();
	       	}else{
	       		alert("更新失败！");
		    }
	      }
	   });
}

function testConn(){
	var dataAddr = document.getElementById("busMod.dataAddr").value;
	var dataAccount = document.getElementById("busMod.dataAccount").value;
	var dataPassword = document.getElementById("busMod.dataPassword").value;
	var modCode = document.getElementById("busMod.modCode").value;
	if(!modCode){
		alert('请填写模块代码！');
		return;
	}
	var modName = document.getElementById("busMod.modName").value;
	if(!modName){
		alert('请填写模块名称！');
		return;
	}
	$.ajax({   
       url : '${ctx}/dataCenter_testConn.do',
       type : 'POST',   
       cache : false,
       async : false,
       error : function() {  
    	   alert("连接失败！");
       },
       data : {
           "dataAddr":dataAddr,"dataAccount":dataAccount,"dataPassword":dataPassword
       },    
       success : function(data) {
       	    var res = eval("("+data+")");
	       	if(res){
	       		alert("连接成功！");
	       		$('#testconn').css('display','none');
				$('#save').css('display','');
				$("input").attr("readOnly",true);
				$("input").attr("unselectable","on");
	       	}else{
	       		alert("连接失败！");
		    }
	      }
	   });
}

</script>
<%@ include file="/common/function.jsp"%>
</html>
