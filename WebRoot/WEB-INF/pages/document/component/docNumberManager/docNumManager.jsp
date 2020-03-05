 <!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List"%>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>文号管理</title>
        <link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
    </head>
    <body>
		<div class="bread-box"><div class="bread-title"><span class="bread-icon"></span><span class="bread-links"><span class="bread-start">当前位置：</span><span class="bread">文号管理 </span><span class="bread-split">&raquo;</span><span class="bread">文号查询</span></span><span class="bread-right-border"></span></div></div>
		<div class="menu" style="text-align: center;">
			<form id="queryForm" action="${ctx}/docNumberManager_docNumManage.do" method="post">
			<div class="displayTableForm">
				<!-- 查询类型，1-查询已使用，2-查询未使用，3-查询所有 -->
				<input type="hidden" id="searchType" name="isused" value="${dn.isused}">
				<input type="hidden" name="order" id="order" value="${order}"/>
				<table class="docformTable" cellspacing="0" cellpadding="0">
				   <tr>
						<td>
							<span style="font-size: 20; font-family: 黑体;">&nbsp;&nbsp;选择流程:</span>
							<select id="define" name="define">
								<c:forEach var="def" items="${defines}">
									<option id="${def.wfUid}" value="${def.wfUid}" <c:if test="${def.wfUid eq define }">selected="selected"</c:if>>${def.wfName}</option>
								</c:forEach>
							</select>
						</td>
						<td >
						  <span style="font-size: 20; font-family: 黑体;">&nbsp;&nbsp;选择实例:</span>
							 <div id="tempModel" style="font-size: 15px;display: inline;"></div>
						</td>
						<td>
							<div id="tempdn" style="font-size: 15px;display: inline;"></div>
						</td>
						<td><input id="query" mice-btn="icon-search" type="button" value="查询所有" class="btn"></td>
					</tr>
					<tr>
						<td >
						标题:
						<input type="text" mice-input="input" name="title" value="${title}">
						</td>
						<td >
						   使用情况:
							 <select id="all" onchange="doSearch(this.value)">
								<option value="2" <c:if test="${dn.isused eq ''}">selected="selected"</c:if>>全部</option>
								<option value="1" <c:if test="${dn.isused eq 1}">selected="selected"</c:if>>已使用</option>
								<option value="0" <c:if test="${dn.isused eq 0}">selected="selected"</c:if>>未使用</option>
							</select>
						</td>
						<td> 
							<input id="addnum" mice-btn="icon-add" type="button" value="新增" class="btn">
						</td>
						<td><input id="recode" mice-btn="icon-add" type="button" value="发文登记" class="btn"></td>
					</tr>
					<tr>
					 <td align="center">
							文号排序:
							  <select id="numOrder" onchange="queryByOrder(this.value)">
								<option value="" >请选择</option>
								<option value="number_asc" <c:if test="${order eq 'number_asc'}">selected="selected"</c:if>>升序</option>
								<option value="number_desc" <c:if test="${order eq 'number_desc'}">selected="selected"</c:if>>降序</option>
							</select>
						</td>
						<td>
							发文时间排序:
							  <select id="timeOrder" onchange="queryByOrder(this.value)">
								<option value="">请选择</option>
								<option value="time_asc" <c:if test="${order eq 'time_asc'}">selected="selected"</c:if>>升序</option>
								<option value="time_desc" <c:if test="${order eq 'time_desc'}">selected="selected"</c:if>>降序</option>
							</select>
						</td>
						<td>
						<input id="exportExcel" mice-btn="icon-expand-all" type="button" value="导出Excel" class="btn"/>
						</td>
						<td></td>
					</tr>
				</table>
				<c:if test="${records ne null}">
		        <display:table name="records" pagesize="10" form="queryForm" class="displayTable" cellspacing="0" cellpadding="0" htmlId="displayTable" id="element" partialList="true" export="false" size="recordSize" excludedParams="*" requestURI="docNumberManager_docNumManage.do">
		            <display:column headerClass="split" class="docNum" title="文号" sortable="false"><c:if test="${dn.define eq 0}">${element.type}[${element.year}]${element.number}</c:if><c:if test="${dn.define eq 1}">[${element.year}]${element.type}${element.lwdwlx}${element.number}</c:if>号</display:column>
		            <display:column headerClass="split" class="docType" title="使用情况"sortable="false" nulls="false"><c:if test="${element.isused eq 0}">未使用</c:if><c:if test="${element.isused eq 1}">已使用</c:if></display:column>
		            <display:column headerClass="split" class="docTitle" title="标题" sortable="false">${element.title}</display:column>
		            <display:column headerClass="split" class="docNgrbm" title="拟稿科室" sortable="false">${element.ngrbm}</display:column>
		             <display:column headerClass="split" class="docNgr" title="拟稿人" sortable="false">${element.ngr}</display:column>
		             <display:column headerClass="split" class="docSecurity" title="密级" sortable="false"><c:if test="${element.security == '2'}">秘密</c:if><c:if test="${element.security == '1'}">机密</c:if><c:if test="${element.security == '0'}">绝密</c:if><c:if test="${element.security == '3'}">&nbsp;</c:if></display:column>
		            <display:column headerClass="split" class="docSendTime" property="time" title="发文时间" format="{0,date,yyyy-MM-dd}" sortable="false" nulls="false"></display:column>
		        </display:table>
				</c:if>
				</div>
			</form>
		</div>
		
		<script type="text/javascript" src="${cdn_js}/base/jquery.js"></script>
       	<script type="text/javascript" src="${cdn_js}/common/dialog/artDialog.js"></script>
        <script type="text/javascript" src="${cdn_js}/sea.js"></script>
        <script type="text/javascript">
	      //格式化字符串
	    	function format(str,size){
				if(str.length===size){
					return str;
				}else{
					var fs = "";
					for(var i=0,j=size-str.length; i<j; i++){
						fs += "0";
					}
					return fs+str;
				}
			}
            //新增文号
        	$("#addnum").bind("click",function(){
            	art.dialog({
    				title: '文号维护',
   					lock: true,
   				    content: '<'+'iframe src="${ctx}/docNumberManager_todocNumAdd.do?define=${define}" height="315" width="450"></'+'iframe>',
   				    id: 'EF893K',
   				    ok: function(){
   				    	$("#query").click();
   				    }
   				});
            });
            //选择流程绑定事件
            $("#define").bind("click",function(){
                //alert("aaa");
            	$.ajax({
				    url: 'docNumberManager_getModel.do',
				    type: 'POST',
				    cache:false,
				    async:false,
				    data:{'define':$("#define").val()},
				    error: function(){
				        alert('AJAX调用错误(docNumberManager_getModel.do)');
				    },
				    success: function(msg){
					    if(msg!==""){
					    	document.getElementById('tempModel').innerHTML=msg;
					    	$("#model").trigger("click");
					    }else{
					    	document.getElementById('tempModel').innerHTML="无实例";
					    	document.getElementById('tempdn').innerHTML="";
					    }
				    }
				});
            });
           //选择实例绑定事件
           $("#model").live("click",function(){
                //alert("aaa");
            	$.ajax({
				    url: 'docNumberManager_paserModel.do',
				    type: 'POST',
				    cache:false,
				    async:false,
				    data:{'dnmodel':$("#model").val()},
				    error: function(){
				        alert('AJAX调用错误(docNumberManager_paserModel.do)');
				    },
				    success: function(msg){
					    if(msg!==""){
					    	document.getElementById('tempdn').innerHTML=msg;
					    }else{
					    	document.getElementById('tempdn').innerHTML="无实例";
					    }
				    }
				});
           });
          
           //查询绑定事件
           $("#query").bind("click",function(){
               //alert(document.getElementById("define").options[]);
        	   if(($("#tempModel").text()==="")){
					alert("请选择流程");
				}else if(($("#tempdn").text()==="")){
					alert("请选择实例");
				}else{
					try{
						displaytagform('queryForm',[{f:'${pageIndexParamName}',v:'1'}]);
					}catch(e){
						$("#queryForm").submit();
					}
				}
            });
           	//序号补全格式
			$("#number").live("focusout",function(){
				if(!$(this).val().match(/\d+/)) $(this).val(''); 
				var _length = $(this).attr("maxlength");
				if(_length<10){
				var _value = $(this).val();
				if(_value.length>=1)
				$(this).val(format(_value,_length));
				if((_value.length!=0&&_value.match(/^0+/))||_value<0){
					$(this).val(format('1',_length));
				}
			  }
			});
			//查询使用未使用文号
			function doSearch(searchType){
				if(searchType=='no'){
					return false;
				}
				if(searchType=='2'){
					$('#searchType').val('');
					$('#order').val('');
				}else{
					if(searchType=='0'){
						$("#timeOrder").attr("disabled","disabled");
					}
					$("#searchType").val(searchType);
				}
				$("#query").click();
			}
			$('#recode').bind('click',function(){
				var retVal = window.showModalDialog('${ctx}/docNumberManager_dj.do',window,'dialogWidth:800px;dialogHeight:700px; status: no; scrollbars: yes; Resizable: yes; help: no;');
				if(retVal)
				alert('登记成功,请重新查询');
			});
			//排序
			function queryByOrder(str){
				$('#order').val(str);
				$("#query").click();
			}
			//导出excel绑定事件
			$("#exportExcel").bind("click",function (){
				var type = $("#type").val();
				var year = $("#year").val();
				var lwdwlx = $("#lwdwlx").val()==undefined?"":$("#lwdwlx").val();
				var define = $("#define").val();
				var order = $("#order").val();
				if(type==undefined||type==null){
					alert("请先选择实例");
					return;
				}
				window.location.href="${ctx}/docNumberManager_exportExcl.do?type="+type+"&year="+year+"&lwdwlx="+"&define="+define+"&order="+order;
			});
			//保留查询状态
        	$(document).ready(function(){
        		if("${dn.isused}"==="0"){
        			$("#timeOrder").attr("disabled","disabled");
            	}
            	if(<%=((List)request.getAttribute("records"))==null?0:((List)request.getAttribute("records")).size()%>===0){
            		$("#all").attr("disabled","disabled");
            		$("#numOrder").attr("disabled","disabled");
            		$("#timeOrder").attr("disabled","disabled");
            	}
            	$("#define").click().val("${define}");
            	$("#model").val("${model}");
            	$("#model").trigger("click");
            	if($("#order").val()==null||$("#order").val()==""){
                	$("numOrder").val("number_asc");
            	}
                $("#number").val("${dn.number}");
            	$("#lwdwlx").val("${dn.lwdwlx}");
            	$("#year").val('${dn.year}');
            });
			seajs.use('lib/hovercolor',function(){
				$('table.displayTable').hovercolor({target:'tbody>tr'});
		    });
			seajs.use('lib/form',function(){
				$('input[mice-btn]').cssBtn();
				$('input[mice-input]').cssInput();
			});
	</script>
	<script type="text/javascript">
	/**
	 seajs.use('lib/form',function(){
			$('input[mice-btn]').cssBtn();
			$('input[mice-input]').cssInput();

			$('#define').cssSelect({trigger:true,triggerFn:function(){
					$.ajax({
					    url: 'docNumberManager_getModel.do',
					    type: 'POST',
					    cache:false,
					    async:false,
					    data:{'define':$("#define").val()},
					    error: function(){
					        alert('AJAX调用错误(docNumberManager_getModel.do)');
					    },
					    success: function(msg){
						    if(msg!==""){
						    	document.getElementById('tempModel').innerHTML=msg;
						    	$("#model").cssSelect({trigger:true,triggerFn:function(){
							    		$.ajax({
										    url: 'docNumberManager_paserModel.do',
										    type: 'POST',
										    cache:false,
										    async:false,
										    data:{'dnmodel':$("#model").val()},
										    error: function(){
										        alert('AJAX调用错误(docNumberManager_paserModel.do)');
										    },
										    success: function(msg){
											    if(msg!==""){
											    	document.getElementById('tempdn').innerHTML=msg;
											    }else{
											    	document.getElementById('tempdn').innerHTML="无实例";
											    }
										    }
										});
									}
								});
						    }else{
						    	document.getElementById('tempModel').innerHTML="无实例";
						    	document.getElementById('tempdn').innerHTML="";
						    }
					    }
					});
				}
			});
	 });
	 **/
	//document.getElementById("${define}").selected="selected";
	//alert($("#define").val());
	</script>
    </body>
</html>
