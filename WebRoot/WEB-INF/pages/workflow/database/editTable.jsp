<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <style>
        .wf-list-top {position:fixed;top:0;left:0;width:100%;}    
        .wf-table-bd {padding:50px 10px 0px 10px;}
        .wf-table-bd table {font-size: 12px;vertical-align: middle;line-height: 1.5;}
        .wf-table-bd table th{background: #e8eff9;font-size: 12px;border-color: #d0d0d0;padding: 8px 5px;border-style: solid;border-width: 1px 1px 1px 1px;vertical-align: top;white-space: nowrap;line-height: 1.5;cursor: default;}
        .wf-table-bd table td {font-size: 12px;border-right: solid 1px #ededed;border-left: solid 1px #ededed;overflow: hidden;padding: 8px 5px;border-bottom: solid 1px #ededed;vertical-align: middle;line-height: 1.5;}
        .wf-form-text {width: 150px;padding: 2px 4px;margin: 0;height:22px;}
    </style>    
</head>
    <body>
    	<form id="tableform" action="${ctx}/table_edit.do" method="post" onsubmit="return checkTableForm();">
    <div class="wf-list-top pageHeader">
        <div class="wf-search-bar">
            <label class="wf-form-label" for="">中文名：</label>
            <input class="wf-form-text" type="text" name="vc_tname" id="vc_tname" value="${table.vc_name}" size="40">   
            <label class="wf-form-label" for="">表名：</label>
            <input class="wf-form-text" type="text" name="vc_tablename" id="vc_tablename" value="${table.vc_tablename}" onchange="checkTableName();" size="40" readonly="readonly">
		    <input type="hidden" name="tableid" id="tableid" value="${table.id}">
		    <input type="hidden" name="tableid" id="tableid" value="${table.id}">            
 			<div class="wf-top-tool">			
	            <a class="wf-btn" href="javascript:checkTableForm();">
	                <i class="wf-icon-send"></i> 提交
	            </a>
	            <a onclick="ConterAdd();" class="wf-btn-primary" href="javascript:void(0);">
	                <i class="wf-icon-plus-circle"></i> 新增
	            </a>
	            <a onclick="moveUp()" class="wf-btn-blue" href="javascript:void(0);">
	                <i class="wf-icon-level-up"></i> 向上
	            </a>
	            <a onclick="moveDown()" class="wf-btn-blue" href="javascript:void(0);">
	                <i class="wf-icon-level-down"></i> 向下
	            </a>	            	            	            	                        
	        </div> 
	        <a onclick="returnToList();" style="float:right" class="wf-btn-primary">返回</a>                  
        </div>
    </div>

<div id="w_list_print" class="wf-table-bd" style="overflow:hidden;height: 100%;overflow: hidden;overflow-y: auto;box-sizing: border-box;"> 
		<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc">
		<thead>
		    	 		<tr >
		    	 			<th align="center" width="10%">
		    	 				中文名称
		    	 			</th>
		    	 			<th align="center" width="10%">
		    	 				字段名称
		    	 			</th>
		    	 			<th align="center" width="10%">
		    	 				字段类型
		    	 			</th>
		    	 			<th align="center" width="5%">
		    	 				长度
		    	 			</th>
		    	 			<th align="center" width="5%">
		    	 				可空
		    	 			</th>
		    	 			<th align="center" width="25%">
		    	 				缺省值
		    	 			</th>
		    	 			<th align="center" width="30%">
		    	 				字段描述
		    	 			</th>
		    	 		</tr>
		    	</thead>
		    	<tbody>
		    	 		<c:forEach var="d" items="${list}" varStatus="n">
		    	 			<tr valign="middle"  <c:if test="${d.i_tableid != null && d.i_tableid != ''}">onclick="c1(this)"</c:if>
		    	 				<c:if test="${d.i_tableid == null}"> bgcolor="#f9f9f9"</c:if>>
			    	 			<td >
			    	 				<input type="hidden" name="id" id="id" value="${d.id}">
			    	 				<input type="hidden" name="i_tableid" id="i_tableid" value="${d.i_tableid}">
			    	 				<input type="hidden" name="vc_name" id="vc_name" value="${d.vc_name}">
			    	 				${d.vc_name}
			    	 			</td>
			    	 			<td>
			    	 				<input type="hidden" name="vc_fieldname" id="vc_fieldname" value="${d.vc_fieldname}" >
			    	 				${d.vc_fieldname}
			    	 			</td>
			    	 			<td align="center" >
			    	 				<input type="hidden" name="i_fieldtype" id="i_fieldtype" value="${d.i_fieldtype}">
			    	 					<c:if test="${d.i_fieldtype=='0'}">VARCHAR2</c:if>
			    	 					<c:if test="${d.i_fieldtype=='1'}">DATE</c:if>
			    	 					<c:if test="${d.i_fieldtype=='2'}">NUMBER</c:if>
			    	 					<c:if test="${d.i_fieldtype=='3'}">CLOB</c:if>
			    	 			</td>
			    	 			<td align="center" >
			    	 				<input type="hidden" name="i_length" id="i_length" value="${d.i_length}">
			    	 				${d.i_length}
			    	 			</td>
			    	 			<td align="center" >
			    	 				<!-- 
			    	 				<c:if test="${d.b_value == '1'}">是</c:if>
			    	 				<c:if test="${d.b_value != '1'}">否</c:if>
			    	 				<input type="hidden" id="b_value"  name="b_value" value="${d.b_value}">
			    	 				 -->
			    	 				<input type="checkbox" id="b_value_chk" <c:if test="${d.b_value == '1'}">checked</c:if> name="b_value_chk" disabled="disabled">
			    	 				<input type="hidden" id="b_value"  name="b_value" value="${d.b_value}">
			    	 			</td>
			    	 			<td >
			    	 				<input type="hidden" id="vc_value"  name="vc_value" value="${d.vc_value}">
			    	 				${d.vc_value}
			    	 			</td>
			    	 			<td >
			    	 				<input type="hidden" id="vc_comment"  name="vc_comment" value="${d.vc_comment}">
			    	 				${d.vc_comment}
			    	 			</td>
			    	 		</tr>
		    	 		</c:forEach>
		    	 		</tbody>
		    	 		<tbody id="tbodyid">
	    	 </tbody>
    	 </table>
    	 </div>
    	</form>
       <div id="showSql" style="border: 1px;width: 60%;height: 20%;display:none;">
       </div>
    </body>
   
    <script type="text/javascript">
	    function checkTableName(){
			var tablename = document.getElementById('vc_tablename').value ;
			tablename = tablename.replace(/\s+/g,"");  
			if(tablename!=""){
				$.post("${ctx}/table_checkTableName.do?vc_tablename="+tablename, null, function(value) {
					 if(value=="yes"){
						  alert(' 此表已存在!') ;
						  $('#vc_tablename').val('') ;
						  return false;
					 }
				 });
			}
		}
	
	    function checkTableForm(){
			var tablename = document.getElementById('vc_tablename').value ;
			if(!tablename){
				alert('请填写表名');
				return;
			}

			//字段名
			var fieldnames = document.getElementsByName('vc_fieldname');
			var vc_fieldnames = ",";
			for(var i = 0; i < fieldnames.length; i++){
				if(!fieldnames[i].value){
					alert('请将字段名填写完整');
					return;
				}else{
					if(vc_fieldnames.indexOf(","+fieldnames[i].value+",") > -1){
						alert('有相同的字段名');
						return;
					}
					vc_fieldnames += fieldnames[i].value+",";
				}
			}

			var fieldtypes = document.getElementsByName('i_fieldtype');
			var lengths = document.getElementsByName('i_length');
			//字段类型、长度
			for(var i = 0; i < fieldtypes.length; i++){
				if(fieldtypes[i].value){
					if(fieldtypes[i].value == '0' && !lengths[i].value){
						alert('请将VARCHAR2类型长度填写完整');
						return;
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
			document.getElementById('tableform').submit();
		}

	    var selectedTr=null;

	    function c1(obj){ 
	    	obj.style.backgroundColor='#cccccc'; //把点到的那一行变希望的颜色; 
	    	if(selectedTr!=null) selectedTr.style.removeAttribute("backgroundColor"); 
	    	if(selectedTr==obj) selectedTr=null;//加上此句，以控制点击变白，再点击反灰 
	    	else selectedTr=obj; 
	    } 

	    function ConterAdd(){   
			$('#tbodyid').append("<tr valign='middle' onclick='c1(this)'>"+
    	 			"<td width='10%'>"+
	 				"<input type='hidden' name='id' id='id'>"+
	 				"<input type='text' name='vc_name' id='vc_name' style='height:22px'>"+
	 				"</td>"+
	 				"<td >"+
	 				"<input type='text' name='vc_fieldname' id='vc_fieldname' style='height:22px'>"+
	 				"</td>"+
	 				"<td align='center' >"+
	 				"<select name='i_fieldtype' id='i_fieldtype'>"+
	 					"<option value='0'>VARCHAR2</option>"+
	 					"<option value='1'>DATE</option>"+
	 					"<option value='2'>NUMBER</option>"+
	 					"<option value='3'>CLOB</option>"+
	 				"</select>"+
	 				"</td>"+
	 				"<td align='center' >"+
	 				"<input type='text' name='i_length' id='i_length' size='8' maxlength='4' style='height:22px'>"+
	 				"</td>"+
	 				"<td align='center' >"+
	 				"<input type='checkbox' id='b_value_chk'  name='b_value_chk' checked>"+
	 				"<input type='hidden' id='b_value'  name='b_value'>"+
	 				"</td>"+
	 				"<td >"+
	 				"<input type='text' id='vc_value'  name='vc_value' style='height:22px'>"+
	 				"</td>"+
	 				"<td>"+
	 				"<input type='text' id='vc_comment'  name='vc_comment' style='height:22px'>"+
	 				"<input type='hidden' name='i_tableid' id='i_tableid' value=''>"+
	 				"</td>"+
	 				"</tr>"); 
			$('#tbodyid').find('tr').last().find('input').val('');
		}

		function ConterDel(trs){   
			var hs=$('#tbodyid tr');
			if(hs.length<=1){alert('默认最后一行不可删！')}
			else{$(selectedTr).remove()}
		}
	    	   
	    function moveUp(){
		    if(!selectedTr){
				alert('请选择对象');
				return;
			}
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
			
		function goHistroy(){
			parent.$('.page iframe:visible').attr('src',parent.$('.page iframe:visible').attr('src'));
		}
		
		function returnToList(){
			var formListUrl = '${ctx}/table_getTableList.do?workflowId=${workflowId}';
			window.location.href=formListUrl;
		}
    </script>
</html>
