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
	<form id="thisForm" method="POST" name="thisForm" action="${ctx }/table_fieldRight.do?tableId='+src.id+'&itemId=${itemId}&type=${type}" >
       <input type="hidden" name="tableName" id="tableName" value="${tableName}">
       <input type="hidden" name="type" id="type" value="${type}"/>
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
					<br/>
					<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="delField(1)"/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<br/>
					<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;" src="${ctx}/widgets/login/right1.png"  onclick="addField(2)"/>
					<br/>
					<br/>
					<img onmouseover="this.style.border='';" onmouseout="this.style.border='';" style="cursor: pointer;"  src="${ctx}/widgets/login/left1.png" onclick="delField(2)"/>
				</td> 
				<td style="">
						条件
						<select id="conditonSelect" size="16" style="width:100%;height: 190px;border: 1px dashed #C2C2C2" multiple="multiple">
						<c:set var="values" value="${conditionValues}"></c:set>
						<c:out value="${status.index}" />
							<c:forEach var="c" items="${conditions}" varStatus="status" >
							<%-- <option value="${c}">${c}</option> --%>
								<option value="${values[status.index]}">${c}</option>
							</c:forEach>
						</select>
						结果
						<select id="resultSelect" size="16" style="width:100%;height: 190px;border: 1px dashed #C2C2C2" multiple="multiple">
						<c:forEach var="r" items="${results}">
							<%-- <option value="${c}">${c}</option> --%>
								<option value="${r}">${r}</option>
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

		window.onload=function(){
			if(parent.condition_old_p != ''){
				ir_condition_old =  JSON2.parse(parent.condition_old_p);
			}
			if(parent.result_old_p != ''){
				ir_result_old = JSON2.parse(parent.result_old_p);
			}
			// 遍历json
			
			var ir_condition_new = null;
	  	    var ir_result_new = null;
	  			if(iframe_right){
	  				ir_condition_new = iframe_right.document.getElementById('conditonSelect');
	  				ir_result_new =  iframe_right.document.getElementById('resultSelect');
	  			};
	  			var condition_option_length = ir_condition_new.options.length;
	  			var result_option_length = ir_result_new.options.length;
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
		}; 
		function checkall(src){
			var v=src.value;
			var selects=g.gbn('vc_limit');
			for(var i=0;i<selects.length;i++){
				selects[i].value=v;
			};
		};
		
		function addField(type){
			// type == 1 查询条件 type == 2 查询结果 
	       	var oldSelect = null;
	       	if(type*1 == 1){
	       		oldSelect=document.getElementById('conditonSelect');
	       	}else if(type*1 == 2){
	       		oldSelect=document.getElementById('resultSelect');
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
	}

	function delField(type){
		var oldSelect = null;
		if(type*1 == 1){
       		oldSelect=document.getElementById('conditonSelect');
       	}else if(type*1 == 2){
       		oldSelect=document.getElementById('resultSelect');
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
	}

	</script>
	<%@ include file="/common/function.jsp"%>
</html>