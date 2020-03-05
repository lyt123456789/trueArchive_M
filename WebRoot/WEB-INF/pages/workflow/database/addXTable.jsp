 <!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>

<html>
    <head>
        <title>新增</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
    </head>
    <body>
    	<div class="menu">
    	<form id="searchForm" action="${ctx}/table_add.do" method="post" onsubmit="return checkTableForm();">
    	 <table width="80%" cellspacing=0 cellpadding=5 border=1 bordercolordark="#ffffff" bordercolorlight="#cccccc">
	    	 <tr height="40px">
		    	 <td align="right">
		    	 	中文名
		    	 </td>
		    	 <td align="center">
		    	 	<input type="text" name="vc_tname" id="vc_tname">
		    	 </td>
		    	 <td align="right">
		    	 	表名
		    	 </td>
		    	 <td align="center">
		    	 	T_WF_OFFICE_<input type="text" name="vc_tablename" id="vc_tablename">
		    	 	<input type="hidden" name="vc_parent" id="vc_parent" value="${table.id}" onblur="checkTableName();">
		    	 </td>
		    	  <td align="center">
		    	 	&nbsp;
		    	 </td>
	    	 </tr>
	    	 <tr>
		    	 <td colspan="5">
		    	 	<TABLE id="fieldtable">
		    	 		<tr height="30px">
		    	 			<td bgcolor="gray" width="2%">
		    	 			</td>
		    	 			<td align="center" width="9%">
		    	 				中文名称
		    	 			</td>
		    	 			<td align="center" width="9%">
		    	 				字段名称
		    	 			</td>
		    	 			<td align="center" width="10%">
		    	 				字段类型
		    	 			</td>
		    	 			<td align="center" width="5%">
		    	 				长度
		    	 			</td>
		    	 			<td align="center" width="5%">
		    	 				可空
		    	 			</td>
		    	 			<td align="center" width="15%">
		    	 				缺省值
		    	 			</td>
		    	 			<td align="center" width="20%">
		    	 				字段描述
		    	 			</td>
		    	 			<td align="center" width="10%">
		    	 				关联表
		    	 			</td>
		    	 			<td align="center" width="10%">
		    	 				关联字段
		    	 			</td>
		    	 		</tr>
		    	 		<!-- 
		    	 		<c:forEach var="d" items="${list}" varStatus="n">
		    	 			<tr valign="middle" bgcolor="green" height="30px">
			    	 			<td bgcolor="gray" width="2%">
			    	 			</td>
			    	 			<td align="center" width="10%">
			    	 				<input type="hidden" name="id" id="id" value="${d.id}">
			    	 				<input type="text" name="vc_name" id="vc_name" value="${d.vc_name}" readonly>
			    	 			</td>
			    	 			<td align="center" width="10%">
			    	 				<input type="text" name="vc_fieldname" id="vc_fieldname" value="${d.vc_fieldname}" readonly>
			    	 			</td>
			    	 			<td align="center" width="10%">
			    	 				<select name="i_fieldtype" id="i_fieldtype">
			    	 					<option value="0" <c:if test="${d.i_fieldtype=='0'}">selected</c:if>>varchar2</option>
			    	 					<option value="1" <c:if test="${d.i_fieldtype=='1'}">selected</c:if>>date</option>
			    	 					<option value="2" <c:if test="${d.i_fieldtype=='2'}">selected</c:if>>number</option>
			    	 					<option value="3" <c:if test="${d.i_fieldtype=='3'}">selected</c:if>>clob</option>
			    	 				</select>
			    	 			</td>
			    	 			<td align="center" width="5%">
			    	 				<input type="text" name="i_length" id="i_length" size="8" value="${d.i_length}" readonly>
			    	 			</td>
			    	 			<td align="center" width="5%">
			    	 				<input type="checkbox" id="b_value_chk" <c:if test="${d.b_value == '1'}">checked</c:if> name="b_value_chk">
			    	 				<input type="hidden" id="b_value"  name="b_value" value="${d.b_value}">
			    	 			</td>
			    	 			<td align="center" width="25%">
			    	 				<input type="text" id="vc_value"  name="vc_value" value="${d.vc_value}" readonly>
			    	 			</td>
			    	 			<td align="center" width="30%">
			    	 				<input type="text" id="vc_comment"  name="vc_comment" value="${d.vc_comment}" readonly>
			    	 			</td>
			    	 		</tr>
		    	 		</c:forEach>
		    	 		 -->
			    	 		<tr valign="middle" height="30px" onclick="c1(this)">
			    	 			<td bgcolor="gray" width="2%">
		    	 				</td>
			    	 			<td align="center" width="9%">
			    	 				<input type="hidden" name="id" id="id">
			    	 				<input type="text" name="vc_name" id="vc_name">
			    	 			</td>
			    	 			<td align="center" width="9%">
			    	 				<input type="text" name="vc_fieldname" id="vc_fieldname">
			    	 			</td>
			    	 			<td align="center" width="10%">
			    	 				<select name="i_fieldtype" id="i_fieldtype">
			    	 					<option value="0">varchar2</option>
			    	 					<option value="1">date</option>
			    	 					<option value="2">number</option>
			    	 					<option value="3">clob</option>
			    	 				</select>
			    	 			</td>
			    	 			<td align="center" width="5%">
			    	 				<input type="text" name="i_length" id="i_length" size="8">
			    	 			</td>
			    	 			<td align="center" width="7%">
			    	 				<input type="checkbox" id="b_value_chk"  name="b_value_chk">
			    	 				<input type="hidden" id="b_value"  name="b_value">
			    	 			</td>
			    	 			<td align="center" width="15%">
			    	 				<input type="text" id="vc_value"  name="vc_value">
			    	 			</td>
			    	 			<td align="center" width="20%">
			    	 				<input type="text" id="vc_comment"  name="vc_comment">
			    	 			</td>
			    	 			<td align="center" width="10%">
			    	 				<select  id="vc_ftable"  name="vc_ftable">
			    	 					<option></option>
			    	 					<option value="${table.id},${table.vc_tablename}">${table.vc_tablename}</option>
			    	 				</select>
			    	 			</td>
			    	 			<td align="center" width="10%">
			    	 				<select  id="vc_ffield"  name="vc_ffield">
			    	 					<option></option>
			    	 					<c:forEach items="${fieldList}" var="d">
			    	 						<option value="${d.id},${d.vc_fieldname}">${d.vc_fieldname}</option>
			    	 					</c:forEach>
			    	 				</select>
			    	 			</td>
			    	 		</tr>
			    	 		<TBODY>
	    				</TBODY>
		    	 	</TABLE>
		    	 </td>
	    	 </tr>
	    	 <tr>
	    	 	<td colspan="5">
	    	 		<input type="submit" value="提交">
	    	 		<input type="button" value="删除" onclick="delCol();">
	    	 		<input type="button" value="新增" onclick="addCol('fieldtable');">
	    	 		<input type="button" value="向上" onclick="moveUp()">
	    	 		<input type="button" value="向下" onclick="moveDown()">
	    	 		<input type="button" value="查看sql" onclick="showSql()">
	    	 	</td>
	    	 </tr>
    	 </table>
    	</form>
       </div>
       <div id="showSql" style="border: 1px;width: 60%;height: 20%">
       </div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
    </body>
   
   	<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
   	<script src="${cdn_js}/sea.js"></script>
    <script type="text/javascript">
	    function checkTableName(){
			var tablename = document.getElementById('vc_tablename').value ;
			tablename = tablename.replace(/\s+/g,"");  
			if(tablename!=""){
				$.post("${ctx}/table_checkTableName.do?vc_tablename=T_WF_OFFICE_"+tablename, null, function(value) {
					 if(value=="yes"){
						  alert(' 此表已存在!') ;
						  $('#vc_tablename').val('') ;
						  return false;
					 }
				 });
			}
		}
	
	    function showSql(){
		    var url = "";
			var tablename = document.getElementById('vc_tablename').value ;
			if(!tablename){
				alert('请填写表名');
				return;
			}
			//字段名
			var fieldnames = document.getElementsByName('vc_fieldname');
			var vc_fieldname = '';
			for(var i = 0; i < fieldnames.length; i++){
				if(fieldnames[i].value){
					vc_fieldname += fieldnames[i].value+",";
				}else{
					alert('请将字段名填写完整');
					return;
				}
			}
			if(vc_fieldname.length > 0){
				url += "&vc_fieldname="+vc_fieldname.substring(0,vc_fieldname.length-1);
			}

			var fieldtypes = document.getElementsByName('i_fieldtype');
			var lengths = document.getElementsByName('i_length');
			//字段类型、长度
			var i_fieldtype = '';
			var i_length = '';
			for(var i = 0; i < fieldtypes.length; i++){
				if(fieldtypes[i].value){
					i_fieldtype += fieldtypes[i].value+",";
					i_length += lengths[i].value+",";
					if(fieldtypes[i].value == '0' && !lengths[i]){
						alert('请将VARCHAR2类型长度填写完整');
						return;
					}
				}else{
					alert('请将字段类型填写完整');
					return;
				}
			}
			if(i_fieldtype.length > 0){
				url += "&i_fieldtype="+i_fieldtype.substring(0,i_fieldtype.length-1);
			}
			if(i_length.length > 0){
				url += "&i_length="+i_length.substring(0,i_length.length-1);
			}
			var b_values = document.getElementsByName('b_value_chk');
			var vc_values = document.getElementsByName('vc_value');
			//字段名
			var b_value = '';
			var vc_value = '';
			for(var i = 0; i < b_values.length; i++){
				if(b_values[i].checked){
					b_value += "1,";
				}else{
					b_value += "0,";
				}
				vc_value = vc_values[i].value+",";
			}
			if(vc_value.length > 0){
				url += "&vc_value="+vc_value.substring(0,vc_value.length-1);
			}
			if(b_value.length > 0){
				url += "&b_value="+b_value.substring(0,b_value.length-1);
			}

			tablename = tablename.replace(/\s+/g,"");  
			if(tablename!=""){
				$.post("${ctx}/table_getSql.do?vc_tablename=T_WF_OFFICE_"+tablename+url, null, function(value) {
					document.getElementById('showSql').innerHTML = value;
				});
			}
		}

	    function checkTableForm(){
			var tablename = document.getElementById('vc_tablename').value ;
			if(!tablename){
				alert('请填写表名');
				return false;
			}else{
				
			}

			//字段名
			var fieldnames = document.getElementsByName('vc_fieldname');
			for(var i = 0; i < fieldnames.length; i++){
				if(!fieldnames[i].value){
					alert('请将字段名填写完整');
					return false;
				}
			}

			var fieldtypes = document.getElementsByName('i_fieldtype');
			var lengths = document.getElementsByName('i_length');
			//字段类型、长度
			for(var i = 0; i < fieldtypes.length; i++){
				if(fieldtypes[i].value){
					if(fieldtypes[i].value == '0' && !lengths[i].value){
						alert('请将VARCHAR2类型长度填写完整');
						return false;
					}
				}
			}
			var b_values = document.getElementsByName('b_value_chk');
			var b_valueshidden = document.getElementsByName('b_value');
			//字段名
			for(var i = 0; i < b_values.length; i++){
				if(b_values[i].checked){
					b_valueshidden[i].value = "1";
				}else{
					b_valueshidden[i].value = "0";
				}
			}

			var vc_ftables = document.getElementsByName('vc_ftable');
			var vc_ffields = document.getElementsByName('vc_ffield');
			//字段名
			for(var i = 0; i < vc_ftables.length; i++){
				if((!vc_ftables[i]&& vc_ffields[i]) || (vc_ftables[i]&& !vc_ffields[i])){
					alert('请选择外键对应的表或字段');
					return false;
				}
			}
			document.getElementById('vc_tablename').value = "T_WF_OFFICE_"+tablename;
			return true;
		}

	    var selectedTr=null; 

	    function c1(obj){ 
	    	obj.style.backgroundColor='#cccccc'; //把点到的那一行变希望的颜色; 
	    	if(selectedTr!=null) selectedTr.style.removeAttribute("backgroundColor"); 
	    	if(selectedTr==obj) selectedTr=null;//加上此句，以控制点击变白，再点击反灰 
	    	else selectedTr=obj; 
	    } 

	    function delCol()   {  
	    	try {  
		    	var Elm = selectedTr;
		    	if(!Elm){
					alert('请选择需要删除的行,基础字段无法删除');
					return;
			    }
		    	if(Elm.parentElement.rows.length <= 1) {  
		    		alert("无法删除！");  
		    		return;  
		    	}  
		    	Elm.parentElement.deleteRow(Elm.rowIndex);  
	    	} catch(e) {  
	    		alert("Err   5001:\r\n"   +   e);  
	    	}  
	    }  
	    		    

	    function ConterAdd(add){   
			$(add).parent().prevAll('table').find('tr').last().after('<tr>'+$(add).parent().prevAll('table').find('tr').last().html()+'</tr>');
			$(add).parent().prevAll('table').find('tr').last().find('input').val('');
		}
	    
	    function addCol(id)   {  
	    	try{  
	    		var oTable = document.getElementById(id);  
	    		if(oTable.tagName != "TABLE"){ 
	    	    	alert("Err   5002");  
	    		}
	    		var oList = oTable.children;  
	    		var oTBODY;  
	    		for(var i = 0; i < oList.length; i++)   {  
	    			if(oList[i].tagName == "TBODY")   {  
	    				oTBODY = oList[i];  
	    				break;  
	    			}  
	    		}  
	    		var oTR = oTBODY.lastChild;  
	    		var newTR = oTR.cloneNode(true);  
	    		oTBODY.insertAdjacentElement("beforeEnd", newTR);  
	    		var ele = newTR.getElementsByTagName('input');
	    		for(var i = 0; i < ele.length; i++){
					ele[i].value='';
		    	}
	    	}catch(e) {  
	    		alert("Err 5002:\r\n" + e);  
	    	}  
	    }  
	    	function   addId(node)   {  
	    	try   {  
	    	if(!node.hasChildNodes())   {  
	    	var   prefix   =   node.getAttribute("id").split("_")[0];  
	    	var   postfix   =   node.getAttribute("id").split("_")[1];  
	    	postfix   =   parseInt(postfix)   +   1;  
	    	node.setAttribute("id",prefix   +   "_"   +   postfix);  
	    	node.setAttribute("value","");  
	    	return;  
	    	}  
	    	}   catch(e)   {}  
	    	try   {  
	    	var   oList   =   node.childNodes;  
	    	for(var   i=0;i<oList.length;i++)   {  
	    	addId(oList[i]);  
	    	}  
	    	}   catch(e)   {  
	    	alert("Err   5003:\r\n"   +   e);  
	    	}  
	    }  

		function ConterDel(trs){    
			 var tit=new Array();  
			 $(trs).parent().parent().parent().find('tr').each(function(i){tit[i]=$(this);});	
			 if(tit.length>2){  
				$(trs).parent('td').parent('tr').detach();  
			 } else{
				 $(trs).parent('td').parent('tr').find('input').val('');alert('默认最后一行不可删！');
				 }
		}
	    	   
	    function moveUp(){ 
		    //通过链接对象获取表格行的引用 
		    var _row=selectedTr; 
		    //如果不是第一行，则与上一行交换顺序 
		    if(_row.previousSibling){
			    swapNode(_row,_row.previousSibling); 
			}
	    }
	    function moveDown(){ 
		    //通过链接对象获取表格行的引用 
		    var _row=selectedTr; 
		    //如果不是最后一行，则与下一行交换顺序 
		    if(_row.nextSibling){
			    swapNode(_row,_row.nextSibling); 
			}
	    } 
		//定义通用的函数交换两个结点的位置 
		function swapNode(node1,node2){ 
			//获取父结点 var 
			_parent=node1.parentNode; 
			//获取两个结点的相对位置 
			var _t1=node1.nextSibling; 
			var _t2=node2.nextSibling; 
			//将node2插入到原来node1的位置 
			if(_t1){
				_parent.insertBefore(node2,_t1);
			}else{ 
				_parent.appendChild(node2);
			} 
			//将node1插入到原来node2的位置 
			if(_t2){
				_parent.insertBefore(node1,_t2);
			}else{ 
				_parent.appendChild(node1);
			}
		} 
			     
    </script>
</html>
