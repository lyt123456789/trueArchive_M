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
<body style="overflow:auto;">
	<form id="thisForm" method="POST" name="thisForm" action="${ctx}/freeSet_fieldRight.do?tableId='+src.id+'&itemId=${itemId}" >
       <input type="hidden" name="tableName" id="tableName" value="${tableName}">
       <input type="hidden" name="sql" id="sql">
    	<input type="hidden" name="changeState" id="changeState" value="${changeState}"/>
    	<div id="w_list_print" align="center">
    		<table style="width: 100%;border-collapse: collapse;">
			<tr>
				<td style="width:40%;">
					<div style="padding-left: 20px;padding-top: 10px;overflow: auto;height: 300px;border: 1px dashed #C2C2C2 " >
						<ul id="black" class="filetree"></ul>
						<select id="fields" size="20" style="width:100%;height: 365px;border: 1px dashed #C2C2C2" multiple="multiple">
							<c:forEach var="m" items="${fields}">
								<option value="${m.id}">${m.vc_name},${m.vc_fieldname},<c:if test="${m.i_fieldtype=='0'}">String</c:if><c:if test="${m.i_fieldtype=='1'}">Date</c:if><c:if test="${m.i_fieldtype=='2'}">Int</c:if><c:if test="${m.i_fieldtype=='3'}">Clob</c:if><c:if test="${m.i_fieldtype==null}">String</c:if></option>
							</c:forEach>
						</select>
					</div>
				</td>
				<td  style="width: 60px;text-align: center;padding: 5px;">
					<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="addField(1)"/>
					<br/>
					<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="delField(1)"/>
					<br/>
					<br/>
					<br/>
					<br/>
					<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="addField(2)"/>
					<br/>
					<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="delField(2)"/>
					<br/>
					<br/>
					<br/>
					<br/>
					<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="addField(3)"/>
					<br/>
					<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="delField(3)"/>
				</td> 
				<td style="">
						条件
						<select id="conditonSelect" size="16" style="width:100%;height: 110px;border: 1px dashed #C2C2C2" multiple="multiple">
						<c:set var="values" value="${conditionValues}"></c:set>
						<c:out value="${status.index}" />
							<c:forEach var="c" items="${conditions}" varStatus="status" >
							<%-- <option value="${c}">${c}</option> --%>
								<option value="${values[status.index]}">${c}</option>
							</c:forEach>
						</select>
						结果
						<select id="resultSelect" size="16" style="width:100%;height: 110px;border: 1px dashed #C2C2C2" multiple="multiple">
						<c:forEach var="r" items="${results}">
							<%-- <option value="${c}">${c}</option> --%>
								<option value="${r}">${r}</option>
							</c:forEach>
						</select>
						排序
						<select id="orderSelect" size="16" style="width:100%;height: 110px;border: 1px dashed #C2C2C2" multiple="multiple">
						<c:forEach var="o" items="${orders}">
							<%-- <option value="${c}">${c}</option> --%>
								<option value="${o}">${o}</option>
							</c:forEach>
						</select>
				</td>
			</tr>
		</table>
    	</div> 
	</form>
</body>
    <script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	<script type="text/javascript">
		$("table.list", document).cssTable();
		
		var iframe_right =	 window.parent.frames['iframe_right'];
		var iframe_middle =	 window.parent.frames['iframe_middle'];
		var ir_condition_old = null;
		var ir_result_old = null;
		var ir_order_old = null;

		window.onload=function(){
			if(parent.condition_old_p != ''){
				ir_condition_old =  JSON2.parse(parent.condition_old_p);
			}
			if(parent.result_old_p != ''){
				ir_result_old = JSON2.parse(parent.result_old_p);
			}
			if(parent.order_old_p != ''){
				ir_order_old = JSON2.parse(parent.order_old_p);
			}
			// 遍历json
			
			var ir_condition_new = null;
	  	    var ir_result_new = null;
	  		var ir_order_new = null;
	  	    
	  			if(iframe_right){
	  				ir_condition_new = iframe_right.document.getElementById('conditonSelect');
	  				ir_result_new =  iframe_right.document.getElementById('resultSelect');
	  				ir_order_new =  iframe_right.document.getElementById('orderSelect');
	  			};
	  			var condition_option_length = ir_condition_new.options.length;
	  			var result_option_length = ir_result_new.options.length;
	  			var order_option_length = ir_order_new.options.length;
	  	        if(ir_condition_old != null){
	  	        	for(var j=0;j<ir_condition_old.length;j++){
	  					// 
	  					var oldValue = ir_condition_old[j].value;  //id
	  					var oldText= ir_condition_old[j].text;    // 表名:中文,英文
	  					if(condition_option_length>0){
	  						var flag = true;
	  						for(var t = 0; t < ir_condition_new.options.length; t++ ){
	  							if(oldValue==ir_condition_new.options[t].value){
	  								flag = false;
	  								break;
	  							}
	  						}
	  						if(flag){
	  							//ir_condition_new.appendChild(ir_condition_old.options[j]);
	  							ir_condition_new.options.add(new Option(oldText,oldValue));
	  						}
	  					}else{
	  						ir_condition_new.options.add(new Option(oldText,oldValue));
	  						//ir_condition_new.options.add(ir_condition_old.options[j]);
	  					}
	  				}
	  	        }
	  	        if(ir_result_old != null){
	  	        	for(var j=0;j<ir_result_old.length;j++){
	  					// 
	  					var oldValue = ir_result_old[j].value;
	  					var oldText= ir_result_old[j].text;    // 表名:中文,英文
	  					if(result_option_length>0){
	  						var flag = true;
	  						for(var t = 0; t < ir_result_new.options.length; t++ ){
	  							if(oldValue==ir_result_new.options[t].value){
	  							    flag = false;
	  								break;
	  							}
	  						}
	  						if(flag){
	  							//ir_result_new.appendChild(ir_result_old.options[j]);
	  							ir_result_new.options.add(new Option(oldText,oldValue));
	  						}
	  					}else{
	  						//ir_result_new.appendChild(ir_result_old.options[j]);
  							ir_result_new.options.add(new Option(oldText,oldValue));
	  					}
	  				}
	  	        }
	  	      if(ir_order_old != null){
	  	        	for(var j=0;j<ir_order_old.length;j++){
	  					// 
	  					var oldValue = ir_order_old[j].value;
	  					var oldText= ir_order_old[j].text;    // 表名:中文,英文
	  					if(order_option_length>0){
	  						var flag = true;
	  						for(var t = 0; t < ir_order_new.options.length; t++ ){
	  							if(oldValue==ir_order_new.options[t].value){
	  							    flag = false;
	  								break;
	  							}
	  						}
	  						if(flag){
	  							//ir_result_new.appendChild(ir_result_old.options[j]);
	  							ir_order_new.options.add(new Option(oldText,oldValue));
	  						}
	  					}else{
	  						//ir_result_new.appendChild(ir_result_old.options[j]);
							ir_order_new.options.add(new Option(oldText,oldValue));
	  					}
	  				}
	  	        }
	  	 	getSql();
		}; 
		function checkall(src){
			var v=src.value;
			var selects=g.gbn('vc_limit');
			for(var i=0;i<selects.length;i++){
				selects[i].value=v;
			};
		};
		
		function addField(type){
			// type == 1 查询条件 type == 2 查询结果 type == 3 查询排序 
	       	var oldSelect = null;
	       	if(type*1 == 1){
	       		oldSelect=document.getElementById('conditonSelect');
	       	}else if(type*1 == 2){
	       		oldSelect=document.getElementById('resultSelect');
	        }else if(type*1 == 3){
	       		oldSelect=document.getElementById('orderSelect');
	        }
	        var	fieldSelect=document.getElementById('fields');
	        
	       	for(var i=0;i<fieldSelect.length;i++){
				if(fieldSelect[i].selected){
					//循环遍历field下拉框
					var isin=false;
					for(var j=0;j<oldSelect.options.length;j++){
						// fieldSelect  中文 {英文}
						var oldValue = oldSelect.options[j].value;
						if(oldValue==fieldSelect[i].value){
							isin=true;break;
						};
					}
					if(!isin){
						oldSelect.options.add(new Option('${tableName}:'+fieldSelect[i].text,fieldSelect[i].value));
						document.getElementById('changeState').value = "1";
					};
				}
	       	}
	       
	       	//document.getElementById('resultSelect') = oldSelect;
	       	getSql();
	}

	function delField(type){
		var oldSelect = null;
		if(type*1 == 1){
       		oldSelect=document.getElementById('conditonSelect');
       	}else if(type*1 == 2){
       		oldSelect=document.getElementById('resultSelect');
        }else if(type*1 == 3){
       		oldSelect=document.getElementById('orderSelect');
        }
		var size=0;
		for(var k=0;k<oldSelect.options.length;k++){
			if(oldSelect.options[k].selected) 
	    	{ 
				size=size+1;
		    	}
		}
		if(size==0){
			return false;	
		}else{
			    for(var k=0;k<oldSelect.options.length;k++){
			    	if(oldSelect.options[k].selected) 
			    	{ 
						var value=oldSelect.options[k].value;
						oldSelect.options.remove(k--);
						document.getElementById('changeState').value = "1";
			    	} 
				}

		}
		getSql();
	}
	//拼接sql
	function getSql(){
		var condition = document.getElementById('conditonSelect');
  	    var result = document.getElementById('resultSelect');
  		var order = document.getElementById('orderSelect');
		var condition_option_length = condition.options.length;
		var result_option_length = result.options.length;
		var order_option_length = order.options.length;
		var condition_sql = "";
		var result_sql = "";
		var order_sql = "";
		var table ="";
		var tableCount = 1;
		for(var j=0;j<condition_option_length;j++){
			var value = condition.options[j].text;
			if(table.indexOf(value.substring(0,value.indexOf(":")))<0){
				table = table + value.substring(0,value.indexOf(":"))+" t"+tableCount+",";
				tableCount = tableCount + 1;
			}
			condition_sql = condition_sql + " and " + value.substring(0,value.indexOf(":"))+"."+value.substring(value.indexOf(",")+1,value.lastIndexOf(","))+" like '%{"+value.substring(0,value.indexOf(":"))+"."+value.substring(value.indexOf(",")+1,value.lastIndexOf(","))+".value}%'";
		}
		for(var j=0;j<result_option_length;j++){
			var value = result.options[j].text;
			if(table.indexOf(value.substring(0,value.indexOf(":")))<0){
				table = table + value.substring(0,value.indexOf(":"))+" t"+tableCount+",";
				tableCount = tableCount + 1;
			}
			result_sql = result_sql + "," +value.substring(0,value.indexOf(":"))+"."+value.substring(value.indexOf(",")+1,value.lastIndexOf(","));
		}
		for(var j=0;j<order_option_length;j++){
			var value = order.options[j].text;
			if(table.indexOf(value.substring(0,value.indexOf(":")))<0){
				table = table + value.substring(0,value.indexOf(":"))+" t"+tableCount+",";
				tableCount = tableCount + 1;
			}
			order_sql = order_sql + "," +value.substring(0,value.indexOf(":"))+"."+value.substring(value.indexOf(",")+1,value.lastIndexOf(","));
		}
		
		if(order_option_length==0)
			order_sql = "";
		else
			order_sql = " order by " + order_sql.substring(1);
		if(result_option_length==0)
			result_sql = "{error:result is null}";
		else
			result_sql = result_sql.substring(1);
		
		if(table!=''){
			table = table.substring(0,table.length-1);
			var tables = table.split(",");
			for(i=0;i<tables.length;i++){
				result_sql = result_sql.replaceAll(tables[i].substring(0,tables[i].indexOf(" ")),tables[i].substring(tables[i].indexOf(" ")+1));
			}
			for(i=0;i<tables.length;i++){
				condition_sql = condition_sql.replaceAll(tables[i].substring(0,tables[i].indexOf(" ")),tables[i].substring(tables[i].indexOf(" ")+1));
				condition_sql = condition_sql+" and p.wf_instance_uid = "+tables[i].substring(tables[i].indexOf(" ")+1)+"."+"instanceid";
			}
			for(i=0;i<tables.length;i++){
				order_sql = order_sql.replaceAll(tables[i].substring(0,tables[i].indexOf(" ")),tables[i].substring(tables[i].indexOf(" ")+1));
			}
			table = ","+table;
		}
		var sql =  "select "+result_sql+" from T_WF_CORE_ITEM i,t_wf_process p"+table+" #type#"+condition_sql+order_sql;
		document.getElementById('sql').value=sql;

	}

	</script>
	<%@ include file="/common/function.jsp"%>
</html>