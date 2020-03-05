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
<body >
    <div class="menu">
    	<form id="templateform" action="${ctx}/template_add.do" method="post"  target="_self">
			<table class="infotan" width="100%">
				<tr>
 					<td width="20%" class="bgs ls">中文名：</td>
 					<td width="80%">
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
				<tr>
				 	<td width="20%" class="bgs ls">模板类型：</td>
				 	<td width="80%">
						<select name="template.lx" id="template.lx" style="width: 235px">
					 		<option value="0">缺省模板</option>
                        	<option value="1">文件主体</option>
                        	<option value="2">眉首和版记</option>
						</select>
				 	</td>
				</tr> 
				<tr>
				 	<td width="20%" class="bgs ls">公文种类：</td>
				 	<td width="80%">
						<select name="template.gwzl" id="template.gwzl" style="width: 235px">
				 			<option value="0" >命令</option>
				            <option value="1" >正文模板</option>
				            <option value="2" >公告</option>
	                        <option value="3" >通告</option>
	                        <option value="4" >通知</option>
	                        <option value="5" >通报</option>
	                        <option value="6" >议案</option>
	                        <option value="7" >报告</option>
	                        <option value="8" >请示</option>
	                        <option value="9" >批复</option>
	                        <option value="10" >意见</option>
	                        <option value="11" >函</option>
	                        <option value="12" >会议纪要</option>
	                        <option value="13" >公报</option>
	                        <option value="14" >指示</option>
	                        <option value="15" >决议</option>
	                        <option value="16" >条例</option>
	                        <option value="17" >规定</option>
	                        <option value="18" >其他</option>
						</select> 
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
			</table>
			<div id="osdiv" style="overflow: auto;">
			<div id="fieldDiv" class="w_list_print">
			</div>
			
			<div id="tableDiv" style="display: none;">
				<c:forEach items="${tableList}" var="d">
					<option value="${d.id},${d.vc_tablename}">${d.vc_tablename}</option>
				</c:forEach>
			</div>
			</div>
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
	    	document.getElementById('span_nb').innerHTML='编辑模板';
	    	
		    var path = document.getElementById('vc_path').value;
		    var tablename = document.getElementById('tableDiv').innerHTML;
	    	if(path!=""){
				$.post("${ctx}/template_getField.do?path="+path+"&bkstr="+escape(encodeURIComponent(window.bkstr)), null, function(value) {
					 if(value==""){
						  alert('没有字段!') ;
						  return false;
					 }else{
						  var fields = value.split(","); 
						  var table = "<table class='list' width='100%' targetType='navTab' asc='asc' desc='desc' layoutH='116'>"; 
						  table += "<thead><tr><th width='25%'>标签名</th><th width='25%'>表名</th><th width='25%'>字段名</th><th width='25%'>套打类型</th><th width='25%'>意见标签</th><th width='25%'>附件标签</th><th width='25%'>列表字段</th></tr></thead><tbody>"
						  for(var i = 0; i < fields.length; i++){
							  table += "<tr><td align='center'>";
							  table += "<input type='hidden' name='vc_label' id='vc_label' value='"+fields[i]+"'>" + fields[i];
							  table += "</td><td align='center'><select id='vc_tablename"+i+"' name='vc_tablename' onchange='getFieldList("+i+")'><option value=''></option>"+tablename;
							  table += "</select></td><td align='center'><select id='vc_fieldname"+i+"' name='vc_fieldname' style='width:180px'><option value=''>-----请选择-----</option></select></td>";
							  
								  table += "<td align='center'><select name='vc_type'>";
								  table +='<option value="1">普通标签</option>';
								  table +='<option value="2">意见标签</option>';
								  table +='<option value="6">列表标签</option>';
								  table +='<option value="3">附件标签(标题)</option>';
								  table +='<option value="4">附件标签(图片)</option>';
								  table +='<option value="7">附件标签(内容)</option>';
								  table +='<option value="5">传阅列表标签</option>';
								  table +='<option value="8">正文标签(内容)</option>';
								  table +='<option value="9">生成二维码</option>';
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
						  table += "</tbody></table>";
						  document.getElementById("fieldDiv").innerHTML = table;
				     }
				 });
			}
		}

		/* function getFieldList(num){
			var vc_table= document.getElementById('vc_tablename'+num).value;
			if(vc_table){
				var tableid = vc_table.split(",")[0];
				$.post("${ctx}/field_getFieldById.do?id=" + tableid, null, function(value) {
					if(value){
						var sel = document.getElementById('vc_fieldname'+num);
						sel.options.length=0;
						var field = value.split(',');
						for(var i = 0; i < field.length; i++){
							if(i%3 == 0){
								var opt = new Option(field[i+1]+"("+field[i+2]+")", field[i]+","+field[i+1]);
								sel.add(opt);
							}
						}
					}
				});
			}
		} */
		
		function getFieldList(num){
			var vc_table= document.getElementById('vc_tablename'+num).value;
			if(vc_table){
				var tableid = vc_table.split(",")[0];
				$.post("${ctx}/field_getFieldById.do?id=" + tableid, null, function(value) {
					if(value){
						var sel = document.getElementById('vc_fieldname'+num);
						sel.options.length=0;
						var field = value.split(';');
						for(var i = 0; i < field.length; i++){
							var ids = field[i].split(",");
							var opt = new Option(ids[1]+"("+ids[2]+")", ids[0]+","+ids[1]);
							sel.add(opt);
						}
					}
				});
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
		
		function goHistroy(){
			parent.$('.page iframe:visible').attr('src',parent.$('.page iframe:visible').attr('src'));
		}	   
    </script>
</html>
