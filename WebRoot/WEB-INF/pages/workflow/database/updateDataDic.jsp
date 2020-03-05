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
	<script type="text/javascript">
	$(function(){
		selectTable();
		//$(".table_solid").tableUI();
		
	});
</script>
</head>
<base target="_self"/>
<body>
<form id="dataDicform"  name="dataDicform"  method="post" action="${ctx}/dataCenter_addBusMod.do" style="font-family: 宋体; font-size: 12pt;">
	<input type="hidden" id="modId" name="modId" value="${modId}">
	<input type="hidden" id="id" name="id" value="${dataDic.id}">
	<table style="height:100px;width:99%" border="0" align="center" cellpadding="0" cellspacing="0" class="contentTable">
       <!--  <tr>
          <td width="20%" height="35" align="right">数据字典代码：</td>
          <td width="50%"><input type="text" name="dm" id="dm"  onmousedown="changeClass('dmtip')"  maxlength="40" class="textInput width_205" /></td>
          <td width="30%" id="dmtip"><span style="color: red;">表名称&nbsp;&nbsp;&nbsp;&nbsp;*</span></td>
        </tr>  -->
        <tr>
          <td width="20%" height="35px" align="right" style="border:0px;">数据字典代码：</td>
          <td width="55%" style="border:0px;">
          <div style="margin-top:12px;width:100px; float:left;height:35px">
            <SELECT  name="tableNames"  id="tableNames" style="width: 205px;height:22px;line-height:22px;font-size:12px">                 
            	<option value=""></option>
            	<c:forEach var="e" items="${tableName}" varStatus="status"> 
                	<option value="${tableName}" selected>${tableName}</option>
                </c:forEach>
			</SELECT>
			</div>
			<div  class="buttonBar" style="height:35px;margin-top:6px;margin-left:50px;width:98px; float:left;">
			<input type="button" style="background: url(../images/qrxz.png) center repeat;width:68px;height:23px;" name="button" value="确认选择" class="confirmBtn" onclick="selectTable();"/>
			</div>
		<!-- 	<div align="left" style="margin-top:12px;width:100px; float:left;height:35px">
				<span id="tableDesc" name="tableDesc"></span>
			</div> -->
		  </td>
		  <td width="25%" ><span style="color: red;">表名称&nbsp;&nbsp;&nbsp;&nbsp;*</span></td>
        </tr>
        <tr>
          <td width="20%" height="35" align="right">数据字典名称：</td>
          <td width="50%"><input type="text" name="dicName"  id="dicName"    value="${dataDic.dicName}" style="width:205px;"/></td>
          <td width="30%"><span style="color: red;">表中文名&nbsp;&nbsp;&nbsp;&nbsp;*</span></td>
        </tr>
        <tr>
          <td width="20%" height="35" align="right">类别：</td>
          <td width="50%">
          	<SELECT style="width:205px;" id="category"  name="category">
				<option value="基础数据"  <c:if test="${'基础数据' == dataDic.category }">selected</c:if>>基础数据</option>
				<option value="业务数据"  <c:if test="${'业务数据' == dataDic.category }">selected</c:if>>业务数据</option>
			</SELECT>
          </td>
          <td></td>
        </tr>
         
      	</table>
      	<table id="tabletitleInfo" style="height:10px;width:98%;table-layout:fixed;"cellspacing="0" cellpadding="0" >
			<tr style="  background-color: #999999;height: 30px;width:100%;color:#fff;">
				<th style=" width:14%">字段名称</th>
				<th style=" width:10%">类型</th>
				<th style=" width:6%">长度</th>
				<th style=" width:10%">缺省值</th>
				<th style=" width:18%">描述</th>
				<th style=" width:20%">生成规则</th>
				<th style=" width:22%">外部链接</th>
			</tr>
		</table>
		<div style="width:100%;height:197px; overflow-y:scroll; overflow-x:hidden;">
			<table id="tableInfo"  border="0" cellspacing="0" >
			<tr>
			</tr>
		    </table>
		</div>
		<div class="formBar pa" style="bottom:0px;width:100%;">  
		<ul class="mr5"> 
		<li><a class="buttonActive" onclick="saveDataDic();" id="save" ><span>保存</span></a></li>
		<li><a class="buttonActive" onclick="javascript:window.close();"  ><span>关闭</span></a></li>
		
		</ul>
</div>
</form>
</body>
<script type="text/javascript">
function saveDataDic(){
	var flag = true;
	var nullPattern=/(^\s*)|(\s*$)/g;//空值
	var tableNames =$('#tableNames').val();
	tableNames=tableNames.replace(nullPattern, "");
	if(tableNames==""){
		alert('数据字典代码不能为空!');
    	flag=false;
	}
	var dicName =document.getElementById("dicName").value;
	dicName=dicName.replace(nullPattern, "");
	if(dicName==""){
		alert('数据字典名称不能为空!');
    	flag=false;
	}
	if(flag){
		$.ajax({   
	        url : '${ctx}/dataCenter_addDataDic.do',
	        type : 'POST',   
	        cache : false,
	        async : false,
	        error : function() {  
	            alert('链接异常，请检查网络');
	        },
	        data : $('#dataDicform').serialize(),
	        success : function(data) {
	        	var res = eval("("+data+")");
	        	if(res.success){
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

function selectTable(){
	var modId = document.getElementById('modId').value;
	var dataDicId = document.getElementById('id').value;
	var select = document.getElementById('tableNames');
	var tableName=select.options[select.selectedIndex].value;
	if(tableName==null||tableName==""){
		$("#tableInfo tr:gt(0)").remove();
	}else{
		$.ajax({   
	        url : '${ctx}/dataCenter_getUpdateTableInfo.do',
	        type : 'POST',   
	        cache : false,
	        async : false,
	        error : function() {  
	            alert('链接异常，请检查网络');
	        },
	        data : {
	            'tableName':tableName,'modId':modId,'dataDicId':dataDicId
	        },    
	        success : function(data) {
	        	var res = eval("("+data+")");
	        	var html="";
	        	var tableInfoSize=res.length;
	        	var tableNameDesc="";
	        	if(tableInfoSize>0){
	        		for(var i=0;i<tableInfoSize;i++){
	        			html=html + '<tr id="'+i+'" class="sjzdupdatelist" ><td style=" width:14%"><input unselectable="on" style="width:90%;height:20px;line-height:20px;font-size:12px;" type="hidden" id="filedId'+i+'" name="filedId'+i+'" value="'+res[i].filedId+'" readonly="true"/> <input unselectable="on" style="width:90%;height:20px;line-height:20px;font-size:12px;" type="text" id="filedName'+i+'" name="filedName'+i+'" value="'+res[i].filedName+'" readonly="true"/></td>'
        				+'<td style=" width:10%"><input style="width:90%;height:20px;line-height:20px;font-size:12px;" type="text" unselectable="on" id="filedType'+i+'" name="filedType'+i+'" value="'+res[i].filedType+'" readonly="true"/></td>'
        				+'<td style=" width:6%"><input style="width:90%;height:20px;line-height:20px;font-size:12px;" type="text" unselectable="on" id="filedLength'+i+'" name="filedLength'+i+'" value="'+res[i].filedLength+'" readonly="true"/></td>'
        				+'<td style=" width:10%"><input style="width:90%;height:20px;line-height:20px;font-size:12px;" type="text" unselectable="on" id="filedDeafult'+i+'" name="filedDeafult'+i+'" value="'+(res[i].filedDeafult==null? "":res[i].filedDeafult)+'" readonly="true"/></td>'
        				+'<td style=" width:18%"><input style="width:90%;height:20px;line-height:20px;font-size:12px;" type="text" id="filedEsc'+i+'" name="filedEsc'+i+'" value="'+(res[i].filedEsc==null? "":res[i].filedEsc)+'" /></td>'
        				+'<td style=" width:20%"><input style="width:90%;height:20px;line-height:20px;font-size:12px;" type="text" id="filedRule'+i+'" name="filedRule'+i+'" value="'+(res[i].filedRule==null? "":res[i].filedRule)+'" /></td>'
        				+'<td style=" width:22%"><input style="width:90%;height:20px;line-height:20px;font-size:12px;" type="text" id="filedPk'+i+'" name="filedPk'+i+'" value="'+(res[i].filedPk==null? "":res[i].filedPk)+'"/><input type="hidden" id="tableInfoId'+i+'" name="tableInfoId'+i+'" value="'+res[i].tableInfoId+'" /></td></tr>';
		        	}
	        		$("#tableInfo tr:gt(0)").remove();
		        	$('#tableInfo tr:last').after(html);
		        	$('#tableInfo').after('<input type="hidden" id="tableInfoSize" name="tableInfoSize" value="'+tableInfoSize+'" />');	
		        	$(".sjzdupdatelist:odd").css({"background-color":"#e0e0e0"});
			    	$(".sjzdupdatelist:even").css({"background-color":"#f2f2f2"});
			    	tableNameDesc=res[0].tableNameDesc;
		        }else{
		        	alert("数据库连接失败或者表已经不存在！");
		        }
	        	if(tableNameDesc==null){
	        		tableNameDesc="";
	        	}
	        	//document.getElementById("tableDesc").innerText=tableNameDesc;
	        }
	    });
		//$(".sjzdupdate_table_solid").tableUI();
	}
}
</script>
<%@ include file="/common/function.jsp"%>
</html>
