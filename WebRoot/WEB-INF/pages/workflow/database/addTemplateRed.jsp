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
<body style="overflow: auto;">
    <div class="menu">
    	<form id="templateform" action="${ctx}/template_addRed.do" method="post"  target="_self">
			<table class="infotan" width="100%">
				<tr>
 					<td width="20%" class="bgs ls">中文名：</td>
 					<td width="80%">
 						<input type="hidden" name="isRedTape" id="isRedTape" value="${isRedTape}">
						<input type="hidden" name="template.lcid" id="template.lcid" value="${lcid}">
						<input type="hidden" name="lcid" id="lcid" value="${lcid}">
						<input type="text" name="template.vc_cname" id="template.vc_cname" style="height: 22px;width: 235px"> 
 					</td>
				</tr> 
				<tr>
					<td width="20%" class="bgs ls">英文名：</td>
					<td width="80%">
						<input type="text" name="template.vc_ename" id="template.vc_ename" style="height: 22px;width: 235px">
					</td>
				</tr> 
				<tr>
					<td width="20%" class="bgs ls">打开模板：</td>
					<td width="80%">
						<span id="span_nb" onclick="openNTKO()" style="cursor: hand" class="uedword">新增模板</span>
					</td>
				</tr> 
				<tr style="display: none;">
					<td width="20%" class="bgs ls">模板地址：</td>
				 	<td width="80%">
				 		<!-- 
				 		<input type="file" name="vc_path" id="vc_path" onchange="getField()" style="height: 22px;width: 435px"> -->
				 		<input readonly="readonly" type="text" name="vc_path" id="vc_path" onchange="" style="height: 22px;width: 435px">
				 	</td>
				</tr> 
				<tr>
					<td width="20%" class="bgs ls">位置：</td>
				 	<td width="80%">
						<select name="template.position" id="template.position" style="width: 235px" onchange="control();">
				 			<option value="0" >文档开头</option>
				            <option value="1" >文档结尾</option>
						</select> 
				 	</td>
				</tr>
				<tr>
					<td width="20%" class="bgs ls">底部模板关联：</td>
				 	<td width="80%">
						<select name="relationId" id="relationId" style="width: 235px">
							<option value="" >请选择</option>
							<c:forEach items="${relationTemplate}" var="item" varStatus="status">
								<option value="${item.id }" >${item.vc_cname }</option>
							</c:forEach>
						</select> 
				 	</td>
				</tr>
			</table>
	    <div id="osdiv" style="overflow: auto;">
			<div id="fieldDiv"  class="w_list_print"> 
			
			<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="116">
					<thead>
						<tr>
						  	<th width='10%'>
						  		标签名
						  	</th>
						  	<th width='15%'>
						  		表名
						  	</th>
						  	<th >
						  		字段名
						  	</th>
						  	<th width='15%'>
						  		套打类型
						  	</th>
						  	<th width='10%'>
						  		意见标签
						  	</th>
						  	<th width='10%'>
						  		附件标签
						  	</th>
						  	<th width='10%'>
						  		列表字段
						  	</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${labelList}" var="d" varStatus="n">
							<tr>
								<td align='center'>
						  			<input type='hidden' name='vc_label' id='vc_label' value='${d.vc_label}'>
						  			${d.vc_label}
						  		</td>
						  		<td align='center'>
						  			<select id='vc_tablename${n.count}' name='vc_tablename' onchange='getFieldList(${n.count})'>
						  				<c:forEach items="${tableList}" var="t">
						  					<option value="" ></option>
						  					<option value="${t.id},${t.vc_tablename}" <c:if test="${d.vc_tableid == t.id}">selected</c:if>>
						  						${t.vc_tablename}
						  					</option>
						  				</c:forEach>
						  			</select>
						  		</td>
						  		<td align='center'>
						  			<select id='vc_fieldname${n.count}' name='vc_fieldname' style="width: 200px;"><option value="" ></option></select>
						  			<input type="hidden" id="vc_fieldvalue${n.count}" name="vc_fieldvalue" value="${d.vc_fieldid}">
						  		</td>
						  		<td align='center'>
						  			<select name='vc_type'>
						  				<option value="1" <c:if test="${d.vc_type=='1'}">selected="selected"</c:if>>普通标签</option>
									  	<option value="2" <c:if test="${d.vc_type=='2'}">selected="selected"</c:if>>意见标签</option>
									  	<option value="6" <c:if test="${d.vc_type=='6'}">selected="selected"</c:if>>列表标签</option>
									  	<option value="3" <c:if test="${d.vc_type=='3'}">selected="selected"</c:if>>附件标签(标题)</option>
									 	<option value="4" <c:if test="${d.vc_type=='4'}">selected="selected"</c:if>>附件标签(图片)</option>
									 	<option value="7" <c:if test="${d.vc_type=='7'}">selected="selected"</c:if>>附件标签(内容)</option>
									 	<option value="5" <c:if test="${d.vc_type=='5'}">selected="selected"</c:if>>传阅列表标签</option>
								  	</select>
								 </td>
						  		<td align='center'> 
						  			<select name='vc_comment' style="width: 100px;">
										 <option value="" ></option>
										  <c:forEach var="node" items="${commentList}" >
										 	<option value="${node.formtagname }" <c:if test="${d.vc_commentId==node.formtagname}">selected="selected"</c:if>>${node.columnCname }(${node.formtagname })</option>
							    		 </c:forEach>
							  		</select>
						  		</td>
						  		<td align='center'> 
						  			<select name='vc_att' style="width: 100px;">
										 <option value="" ></option>
										  <c:forEach var="node" items="${attachmentList}" >
										 	<option value="${node.formtagname }" <c:if test="${d.vc_att==node.formtagname}">selected="selected"</c:if>>${node.columnCname }(${node.formtagname })</option>
							    		 </c:forEach>
							  		</select>
						  		</td>
						  		<td align='center'> 
						  			<select name='vc_list' style="width: 100px;">
								  		<option value=""></option>
									    <c:forEach var="node" items="${listTagList}" >
									      <option value="${node.tablename },${node.columnname }" <c:if test="${fn:contains(d.vc_list,node.columnname)}">selected="selected"</c:if>>${node.columnCname }(${node.formtagname })</option>
						    		    </c:forEach>
								    </select>
						  		</td>
						  </tr>
						  </c:forEach>
					</tbody>
				</table>
				</div>
				<div id="tableDiv" style="display: none">
    	 		<c:forEach items="${tableList}" var="d">
    	 			<option value="${d.id},${d.vc_tablename}">${d.vc_tablename}</option>
    	 		</c:forEach></div>
    	</form>
	</div>
    <div class="formBar pa" style="bottom:0px;width:100%;">   
	<ul class="mr5"> 
		<li><div class="buttonActive"><div class="buttonContent"><button name="CmdOk" onclick="checkForm()" type="submit">保存</button></div></div></li>
		<li><a onclick="goHistroy();" name="CmdView" class="buttonActive" href="javascript:;"><span>返回</span></a></li>
	</ul>
	</div>
    </body>
	    			
	    		
    <%@ include file="/common/function.jsp"%>
    <script>
    var hs=$(window).height()-155-36;
    $('#osdiv').height(hs);
    </script>
    <script type="text/javascript">
    function getField(){
	    var path = document.getElementById('vc_path').value;
	    var tablename = document.getElementById('tableDiv').innerHTML;
	    var tableValue = document.getElementById('fieldDiv').innerHTML;
    	if(path!=""){
			$.post("${ctx}/template_getField.do?id=${template.id}&path="+path+"&bkstr="+escape(encodeURIComponent(window.bkstr)), null, function(value) {
				 if(value==""){
					  alert('没有字段!') ;
					  return false;
				 }else{
					 var fields = value.split(","); 
					 var newFields = ","+value;
					 var newFieldArray = [];
					 // 记录  没有变化的 label 放置 newFieldArray 数组里面， 值为 varstatus count , 删除掉的书签 值为0
					 <c:forEach var="temp" items="${labelList}" varStatus="zt">
					 if(value.indexOf('${temp.vc_label}') > -1){
						 newFieldArray['${zt.count-1}'] = '${zt.count}';
					 }else{
						 newFieldArray['${zt.count-1}'] = 0;
					 }
					 </c:forEach>
					  var table = "<table class='list' width='100%' targetType='navTab' asc='asc' desc='desc' layoutH='116'>"; 
					  table += "<thead><tr><th>标签名</th><th >表名</th><th >字段名</th><th >套打类型</th><th >意见标签</th><th>附件标签</th><th >列表字段</th></tr></thead><tbody>";
					  // 处理old以后的 书签
					   <c:forEach var="mark" items="${labelList}" varStatus="status">
					   if(value.indexOf('${mark.vc_label}') > -1){
						   newFields = newFields.replace(",${mark.vc_label}",'');
						    table += "<tr><td align='center'>";
							  table += "<input type='hidden' name='vc_label' id='vc_label' value='${mark.vc_label}'>${mark.vc_label}";
							  table += "</td><td align='center'><select id='vc_tablename${status.count}' name='vc_tablename' onchange='getFieldList(${status.count})'><option value=''></option><c:forEach items='${tableList}' var='t'><option value='${t.id},${t.vc_tablename}' <c:if test="${mark.vc_tableid == t.id}">selected</c:if>>${t.vc_tablename}</option></c:forEach>";
							  table += "</select></td><td align='center'><select id='vc_fieldname${status.count}' name='vc_fieldname' style='width:180px'><option value=''></option></select><input type='hidden' id='vc_fieldvalue${status.count}' name='vc_fieldvalue' value='${mark.vc_fieldid}'></td>";
							                          //<td align='center'><select id='vc_fieldname${n.count}' name='vc_fieldname'><option value="" ></option>                         </select><input type="hidden" id="vc_fieldvalue${n.count}" name="vc_fieldvalue" value="${d.vc_fieldid}"></td>
								
							  table += "<td align='center'><select name='vc_type'>";
							  table +='<option value="1">普通标签</option>';
							  table +='<option value="2">意见标签</option>';
							  table +='<option value="6">列表标签</option>';
							  table +='<option value="3">附件标签(标题)</option>';
							  table +='<option value="4">附件标签(图片)</option>';
							  table +='<option value="5">传阅列表标签</option>';
							table += "</select></td>";
								  
							  //新增意见标签套打关联
							  table += "<td align='center'><select name='vc_comment'>";
							  table +='<option value=""></option>';
							  <c:forEach var="node" items="${commentList}" >
							  table +='<option value="${node.formtagname }" <c:if test="${mark.vc_commentId==node.formtagname}">selected="selected"</c:if>>${node.columnCname }(${node.formtagname })</option>';
				    		 </c:forEach>
							  table += "</select></td>";

							// 附件标签套打关联
							  table += "<td align='center'><select name='vc_att'>";
							  table +='<option value=""></option>';
							  <c:forEach var="node" items="${attachmentList}" >
							  table +='<option value="${node.formtagname }" <c:if test="${mark.vc_att==node.formtagname}">selected="selected"</c:if>>${node.columnCname }(${node.formtagname })</option>';
				    		 </c:forEach>
							  table += "</select></td>";

							// 列表字段套打关联
							  table += "<td align='center'><select name='vc_list'>";
							  table +='<option value=""></option>';
							  <c:forEach var="node" items="${listTagList}" >
							  table +='<option value="${node.tablename },${node.columnname }"<c:if test="${fn:contains(mark.vc_list,node.columnname)}">selected="selected"</c:if>>${node.columnCname }(${node.formtagname })</option>';
				    		 </c:forEach>
							  table += "</select></td>";
							  	  
							  table += "</tr>";
							 
					   }					    
					    </c:forEach>
					    
					    // 处理new 书签
					    if(newFields != ''){
							  fields = newFields.split(',');
							for(var i = 1; i < fields.length; i++){
								var  listCount = newFieldArray.length+ i;
							  table += "<tr><td align='center'>";
							  table += "<input type='hidden' name='vc_label' id='vc_label' value='"+fields[i]+"'>" + fields[i];
							  table += "</td><td align='center'><select id='vc_tablename"+listCount+"' name='vc_tablename' onchange='getFieldList("+listCount+")'><option value=''></option>"+tablename;
							  table += "</select></td><td align='center'><select id='vc_fieldname"+listCount+"' name='vc_fieldname' style='width:180px'><option value=''>-----请选择-----</option></select><input type='hidden' id='vc_fieldvalue"+listCount+"' name='vc_fieldvalue' value='"+fields[i]+"'></td>";
							  
								  table += "<td align='center'><select name='vc_type'>";
								  table +='<option value="1">普通标签</option>';
								  table +='<option value="2">意见标签</option>';
								  table +='<option value="6">列表标签</option>';
								  table +='<option value="3">附件标签(标题)</option>';
								  table +='<option value="7">附件标签(类型)</option>';
								  table +='<option value="4">附件标签(图片)</option>';
								  table +='<option value="5">传阅列表标签</option>';
								  table +='<option value="8">日期标签</option>';
								  table += "</select></td>";
								  
							  //新增意见标签套打关联
							  table += "<td align='center'><select name='vc_comment'>";
							  table +='<option value=""></option>';
							  <c:forEach var="node" items="${commentList}" >
							  table +='<option value="${node.formtagname }">${node.columnCname }(${node.formtagname })</option>';
				    		 </c:forEach>
							  table += "</select></td>";
 
							// 附件标签套打关联
							  table += "<td align='center'><select name='vc_att'>";
							  table +='<option value=""></option>';
							  <c:forEach var="node" items="${attachmentList}" >
							  table +='<option value="${node.formtagname }">${node.columnCname }(${node.formtagname })</option>';
				    		 </c:forEach>
							  table += "</select></td>";

							// 列表字段套打关联
							  table += "<td align='center'><select name='vc_list'>";
							  table +='<option value=""></option>';
							  <c:forEach var="node" items="${listTagList}" >
							  table +='<option value="${node.tablename },${node.columnname }">${node.columnCname }(${node.formtagname })</option>';
				    		 </c:forEach>
							  table += "</select></td>";
							  	  
							  table += "</tr>";
						  }
						  }
						  table += "</tbody></table>";
					  
					
					  document.getElementById("fieldDiv").innerHTML = table;
					  for(var t= 0 ; t <  newFieldArray.length; t++){
						  if(newFieldArray[t] != 0){
							  getFieldList(newFieldArray[t]);
						  }
						  
					  }
					   
			     }
			 });
		}
	}

    function control(){
    	var v = document.getElementById("template.position").value;
    	if(v == "0"){
    		document.getElementById("relationId").removeAttribute("disabled");
    	}else if(v == "1"){
    		document.getElementById("relationId").setAttribute("disabled");
    	}
    }
    
		function openNTKO(){
			var vc_path=document.getElementById('vc_path').value;
			//var a=window.open("${ctx}/template_toDocJsp.do",'newwindow','height=600, width=1000,top='+(screen.height-600)/2+',left='+(screen.width-1000)/2+',toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no,status=no');
    		if(vc_path!=''){
    			var bkstr=window.showModalDialog("${ctx}/template_toDocJsp.do?name=" + vc_path,window, 
    			'dialogWidth: 800px;dialogHeight: 600px; status: no; scrollbars: no; Resizable: no; help: no;');
    			window.bkstr=bkstr;
    			if(bkstr)getField();
    		}else{
    			var bkstr=window.showModalDialog("${ctx}/template_toDocJsp.do",window, 
    			'dialogWidth: 800px;dialogHeight: 600px; status: no; scrollbars: no; Resizable: no; help: no;');
    			window.bkstr=bkstr;
    			if(bkstr)getField();
    		};
			
		}

		function checkForm(){
			var vc_cname = document.getElementById('template.vc_cname').value;
			if(!vc_cname){
				alert('请填写中文名');
				return;
			}

			var vc_ename = document.getElementById('template.vc_ename').value;
			if(!vc_ename){
				alert('请填写英文名');
				return;
			}
			
			var vc_path = document.getElementById('vc_path').value;
			if(!vc_path){
				alert('请选择模板');
				return;
			}


			var vc_tablename = document.getElementsByName('vc_tablename');
			if(!vc_tablename){
				alert('没有符合要求的标签');
				return;
			}

			/*
			for(var i = 0; vc_tablename && i < vc_tablename.length; i++){
				if(!vc_tablename[i].value){
					alert('请选择对应表名');
					return;
				}
			}

			var vc_fieldname = document.getElementsByName('vc_fieldname');
			for(var i = 0; vc_fieldname && i < vc_fieldname.length; i++){
				if(!vc_fieldname[i].value){
					alert('请选择对应字段名');
					return;
				}
			}
			*/
			document.getElementById('templateform').submit();

		}
    </script>
    
        <script type="text/javascript">
    function getFieldList(num){
		var vc_table= document.getElementById('vc_tablename'+num).value;
		var vc_fieldvalue= document.getElementById('vc_fieldvalue'+num);
		var fvalue =vc_fieldvalue.value;
		if(vc_table){
			var tableid = vc_table.split(",")[0];
			$.ajax({
				 url: "${ctx}/field_getFieldById.do?id=" + tableid+"&t="+new Date(),
		         type: 'POST',
		         cache: false,
		         async: false,
			    error: function(){
			        alert('AJAX调用错误');
			    },
			    success: function(value){
			    	if(value){
						var sel = document.getElementById('vc_fieldname'+num);
						sel.options.length=0;
						var field = value.split(';');
						for(var i = 0; i < field.length; i++){
							//if(i%3 == 0){
								var ids = field[i].split(",");
								var opt = new Option(ids[1]+"("+ids[2]+")", ids[0]+","+ids[1]);
								if( fvalue == ids[0]){
									opt.selected = "selected";
								}
								sel.add(opt);
							//}
						}
					}
			    }
			});
		}
	}
</script>
<script type="text/javascript">   
    function initField(){
		var vc_table= document.getElementsByName('vc_tablename');
		var vc_field= document.getElementsByName('vc_fieldname');
		var vc_fieldvalue= document.getElementsByName('vc_fieldvalue');
		for(var i = 0; vc_table && vc_field && vc_table.length > i; i++){
			var tableid = vc_table[i].value.split(",")[0];
			var sel = vc_field[i];
			var fvalue = vc_fieldvalue[i].value;
			$.ajax({
				 url: "${ctx}/${ctx}/field_getFieldById.do?id=" + tableid+"&t="+new Date(),
		         type: 'POST',
		         cache: false,
		         async: false,
			    error: function(){
			        alert('AJAX调用错误');
			    },
			    success: function(value){
			    	if(value){
						var field = value.split(';');
						for(var j = 0; j < field.length; j++){
							//alert(field[j]);
							var ids = field[j].split(",");
							var opt = new Option(ids[1]+"("+ids[2]+")", ids[0]+","+ids[1]);
							if( fvalue == ids[0]){
								opt.selected = "selected";
							}
							sel.add(opt);
						}
					}
			    }
			});
		}
	}
    
	function goHistroy(){
		parent.$('.page iframe:visible').attr('src',parent.$('.page iframe:visible').attr('src'));
	}	   
</script>
</html>
