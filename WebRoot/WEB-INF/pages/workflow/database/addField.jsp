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
<body>
    <div class="menu">
    	<form id="fieldform" action="${ctx}/field_add.do" method="post">
    	 <table class="infotan" width="100%">
	    	 <tr height="30px">
		    	 <td class="bgs ls" width="15%">
		    	 	名称:
		    	 </td>
		    	 <td width="35%" >
		    	 	<input type="text" name="field.vc_name" id="field.vc_name" style="width: 150px;height: 20px">
		    	 </td>
		    	 <td class="bgs ls" width="15%">
		    	 	字段名:
		    	 </td>
		    	 <td width="35%" >
		    	 	<input type="text" name="field.vc_fieldname" id="field.vc_fieldname" style="width: 150px;height: 20px" onblur="checkFieldName()">
		    	 </td>
	    	 </tr>
	    	 <tr height="30px" >
	    	 	<td class="bgs ls" width="15%">
		    	 	类型:
		    	 </td>
		    	 <td width="35%" >
		    	 	<select name="field.i_fieldtype" id="field.i_fieldtype" style="width: 150px;height: 20px">
		    	 		<option value="0">VARCHAR2</option>
		    	 		<option value="1">DATE</option>
		    	 		<option value="2">NUMBER</option>
		    	 		<option value="3">CLOB</option>
		    	 	</select>
		    	 </td>
		    	 <td class="bgs ls" width="15%">
		    	 	长度:
		    	 </td>
		    	 <td  width="35%" >
		    	 	<input type="text" name="field.i_length" id="field.i_length" style="width: 150px;height: 20px" maxlength="3" 
		    	 		 onpaste="return false;"  onKeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" maxlength="3" onkeyup="this.value=this.value.replace(/[^\d*]/,'')" >
		    	 </td>
	    	 </tr>
	    	 <tr height="30px">
	    	 	<td class="bgs ls" width="15%">
		    	 	是否为空:
		    	 </td>
		    	 <td  width="35%" >
		    	 	<input type="checkbox" name="b_value_chk" id="b_value_chk">
		    	 	<input type="hidden" name="field.b_value" id="field.b_value">
		    	 </td>
		    	 <td class="bgs ls" width="15%">
		    	 	默认值:
		    	 </td>
		    	 <td  width="35%" >
		    	 	<input type="text" name="field.vc_value" id="field.vc_value" style="width: 150px;height: 20px">
		    	 </td>
	    	 </tr>
	    	 <tr height="30px">
	    	 	<td  class="bgs ls" width="15%">
		    	 	描述:
		    	 </td>
		    	 <td  width="35%" >
		    	 	<input type="text" name="field.vc_comment" id="field.vc_comment" style="width: 150px;height: 20px">
		    	 </td>
		    	 <td class="bgs ls" width="15%">
		    	 	排序号:
		    	 </td>
		    	 <td  width="35%" >
			    	 <input type="text" name="field.i_orderid" id="field.i_orderid" style="width: 150px;height: 20px" 
			    	 	 onpaste="return false;"  onKeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" maxlength="3" onkeyup="this.value=this.value.replace(/[^\d*]/,'')">
		    	 </td>
	    	 </tr>
    	 </table>
    	</form>
       </div>
       <div class="formBar pa" style="bottom:0px;width:100%;">  
		<ul class="mr5"> 
			<li><a onclick="" name="CmdView" class="buttonActive" href="javascript:checkField();"><span>保存</span></a></li>
			<li><a onclick="goHistroy();" name="CmdView" class="buttonActive"><span>返回</span></a></li>
		</ul>
	   </div>
    </body>
   
    <script type="text/javascript">
	    function checkFieldName(){
			var fieldname = document.getElementById('field.vc_fieldname').value ;
			fieldname = fieldname.replace(/\s+/g,"");  
			if(fieldname!=""){
				$.post("${ctx}/field_checkFieldName.do?vc_fieldname="+fieldname, null, function(value) {
					 if(value=="yes"){
						  alert(' 此字段已存在!') ;
						  $('#vc_fieldname').val('') ;
						  return false;
					 }
				 });
			}
		}
	
	    function checkField(){
		    var vc_name = document.getElementById('field.vc_name').value; 
		   	var vc_fieldname = document.getElementById('field.vc_fieldname').value;
		   	var i_fieldtype = document.getElementById('field.i_fieldtype').value;
		   	var i_length = document.getElementById('field.i_length').value;
		   	var b_value_chk = document.getElementById('b_value_chk');
		   	if(!vc_name){
				alert('请填写名称');
				return;
			}
		   	if(!vc_fieldname){
				alert('请填写字段名');
				return;
			}
			if(i_fieldtype == '0' && !i_length){
				alert('类型为VARCHAR2时请填写长度');
				return;
			}
			if(b_value_chk.checked){
				document.getElementById('field.b_value').value="1";
			}else{
				document.getElementById('field.b_value').value="0";
			}
			document.getElementById('fieldform').submit();
		}
	    
		function goHistroy(){
			parent.$('.page iframe:visible').attr('src',parent.$('.page iframe:visible').attr('src'));
		}	
    </script>
</html>
