<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="renderer" content="webkit">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	    <link rel="stylesheet" type="text/css" href="${ctx}/layui2.5.4/layui/css/layui.css">
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
	    <style type="text/css">
	      body {
	      	overflow:auto;
	      }
    	  body::-webkit-scrollbar {/*滚动条整体样式*/
             width: 5px;     /*高宽分别对应横竖滚动条的尺寸*/
             height: 1px;
          }
		  body::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
       	     border-radius: 13px;
       		 -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.2);
      		 background: #00BFFF;
		  }
		  body::-webkit-scrollbar-track {/*滚动条里面轨道*/
    		 -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.2);
       		 border-radius: 13px;
       		 background: #E0FFFF;
		  }
	      .commonFather {
	      	width:98%;
    	  	margin:0px 0px 0px 10px;
	      	height:160px;
	      	border:solid 1px #dddddd;
	      	position:relative;
	      }
	      .title {
      	    font-size: 16px;
		    text-align: center;
		    font-weight: 900;
		    font-family: serif;
		    color: #037fd6;
		    margin-bottom: 5px;
		    margin-top:5px;
	      }
	      form {
	      	text-align: center;
	      	margin-top:20px;
	      }
	      .saveBut {
	      	text-align: center;
	      	margin-top:20px;
	      }
    	  #fileCodeProp {
    	  	width:98%;
    	  	margin:20px 0px 10px 10px;
    	  }
    	  .searchBar {
    	  	background: #f8f8f8;
    		border-bottom: 1px solid #eee;
    	  }
    	  .codePropTitle {
    	  	font-size: 16px;
		    font-weight: 900;
		    color: #037fd6;
		    font-family: serif;
		    margin: 5px 0px 5px 15px;
    	  }
    	  .codePropBtn {
    	  	margin: 10px 0px 10px 15px;
    	  }
    	  #tableDiv {
    	  	width:100%;
    	  }
    	  .layui-table th {
    	  	text-align:center;
    	  	background:#008cee;
    	  	color:#fff;
    	  	font-size:16px;
    	  }
    	  .ffd {
    	  	background:#ffd;
    	  }
    	  .selectWidth {
    	  	width:235px;
    	  }
    	  .layui-form-select dl dd.layui-this {
    	  	background-color:#008cee;
    	  }
	    </style>
	</head>
	<body>
		<div id="isDisplay">
			<input id="structureId" name="structureId" type="hidden" value="${structureId }"/>
			<div id="combine" class="commonFather">
				<div class="title">选择起始日期和保管期限对应的字段 </div>
				<form class="layui-form">
					 <div class="layui-form-item ">
					 	<div class="layui-inline">
					 		<label class="layui-form-label">起始日期：</label>
					 		<div class="layui-input-block selectWidth">
						      	<select name="se" lay-search id="tagIdSE">
						      		<c:if test="${tagIdSE eq null}">
						      			<option>请选择起始日期！</option>
						      		</c:if>
							        <c:forEach items="${etList}" var="data" varStatus="status">
							        	<option value="${data.id }" <c:if test="${tagIdSE eq data.id}">selected</c:if>>${data.esIdentifier }</option>
	    							</c:forEach>
							     </select>
					   	 	</div>
					 	</div>
					 	<div class="layui-inline">
					 		<label class="layui-form-label">保管期限：</label>
						 	<div class="layui-input-block selectWidth">
						      	<select name="section" lay-search id="tagIdSection">
						      		<c:if test="${tagIdSection eq null}">
						      			<option>请选择保管期限！</option>
						      		</c:if>
							        <c:forEach items="${etList}" var="data" varStatus="status">
							        	<option value="${data.id }" <c:if test="${tagIdSection eq data.id}">selected</c:if>>${data.esIdentifier }</option>
	    							</c:forEach>
							    </select>
						    </div>
					 	</div>
  					</div>
				</form>
				<div class="saveBut">
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveJDData()">保存</button>
				</div>
			</div>
		</div>
		
		<div id="fileCodeProp">
			<div class="searchBar">
				<div class="codePropTitle">添加保管期限时限</div>
				<input id="tagIdSE" name="tagIdSE" type="hidden" value="${tagIdSE }"/>
				<input id="tagIdSection" name="tagIdSection" type="hidden" value="${tagIdSection }"/>
				<input id="tagName" name="tagName" type="hidden"/>
				<div class="codePropBtn">
					<button type="button" onclick="add();" class="btn btn-add">
			    		<i class="fa fa-plus"></i>添加
			    	</button>
			    	<button type="button" onclick="update();" class="btn btn-write">
			    		<i class="fa fa-pencil-square-o fa-lg"></i>修改
			    	</button>
	  				<button type="button" onclick="del();" class="btn btn-danger">
	  					<i class="fa fa-trash-o fa-lg"></i>删除
	  				</button>
				</div>
			</div>
			
			<div id="tableDiv">
				<table class="layui-table" lay-even lay-skin="line" id="fcPropTable">
					<colgroup>
					    <col align="center" width="6%">
					    <col align="center" width="48%">
					    <col align="center" width="48%">
					</colgroup>
					<thead>
						<tr>
			    	  		<th></th>
			    	  		<th>期限字段</th>
			    	  		<th>时间期限</th>
	                	</tr>
	 	 			</thead>
					<tbody id="fcPropTbody">
						<c:forEach items="${tableList}" var="data" varStatus="status">
							<tr onclick="changeTRBColor(this)">
					    		<td align="center">
					    			<input type="checkbox" name="jdId" id="${data['esorder'] }"  value="${data['id'] }" />
					    		</td>
					    		<td align="center" title ="${data['tagKey'] }">${data['tagKey'] }</td>
					    		<td align="center" title ="${data['tagValue'] }">${data['tagValue'] }</td>
				    		</tr>
	    				</c:forEach>
	  				</tbody>
				</table>
			</div>
		</div>
	</body>
	
	<div id="addDiv" hidden="hidden">
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>期限字段：</label>
			<div class='layui-input-block' style='width:320px'>
			    <input type='text' id="jdKeyAdd" placeholder='必填' autocomplete='off' class='layui-input'>
			</div>
		</div>
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>时间期限：</label>
			<div class='layui-input-block' style='width:320px'>
			 	<input type='text' id='jdValueAdd' placeholder='必填，必须为数字' autocomplete='off' class='layui-input'>
			</div>
		</div>
	</div>
	
	<div id="updateDiv" hidden="hidden">
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>期限字段：</label>
			<div class='layui-input-block' style='width:320px'>
			    <input type='text' id="jdKeyUpdate" placeholder='必填' autocomplete='off' class='layui-input'>
			</div>
		</div>
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>时间期限：</label>
			<div class='layui-input-block' style='width:320px'>
			 	<input type='text' id='jdValueUpdate' placeholder='必填，必须为数字' autocomplete='off' class='layui-input'>
			</div>
		</div>
	</div>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript" src="${ctx}/layui2.5.4/layui/layui.js"></script>
	<script type="text/javascript">
		layui.use('form', function(){
			var form = layui.form; 
			form.render();
		});
		
		//保存选中字段
		function saveJDData() {
			var tagIdSE = $("#tagIdSE").val();
			if(isEmpty(tagIdSE)) {
            	layer.msg("请选择起始日期");
            	return;
            }
			var tagIdSection = $("#tagIdSection").val();
			if(isEmpty(tagIdSection)) {
            	layer.msg("请选择保管期限");
            	return;
            }
			var obj = {
				"structureId":"${structureId }",
				"tagIdSE":tagIdSE,
				"tagIdSection":tagIdSection
			};
			$.ajax({
				url:"${ctx}/dataTemp_saveDataOfJD.do",
				type:"post",
				async:false,
				cache: false,
				dataType:"json",
				data:obj,
				success:function(data){
					var flag = data.flag;
					if(flag == "success") {
						layer.msg("保存成功");
						$("#tagIdSE").val(tagIdSE);
						$("#tagIdSection").val(tagIdSection);
					} else {
						layer.msg("保存失败");
					}
				},
				error:function(){
					layer.msg("系统错误请重试");
				}	
			});
		}
		
		function changeTRBColor(dom) {
			$("#fcPropTable").find("tr td").removeClass("ffd");
			$(dom).find("td").addClass("ffd");
		}
		
		function add() {
			layer.open({
	            type: 1,
	            title: '添加',
	            area:['470px', '240px'],
	            btn: ['确定','取消'],
	            content: $('#addDiv'),
	            yes: function (index, layero) {
	                //按钮【按钮一】的回调
	                var jdKeyAdd = $("#jdKeyAdd").val();
	                if(isEmpty(jdKeyAdd)) {
	                	layer.msg("请填写限期字段");
	                	return;
	                }
	                var jdValueAdd = $("#jdValueAdd").val();
	                if(isEmpty(jdValueAdd)) {
	                	layer.msg("请填写时间期限");
	                	return;
	                }
	                if(isNotNumber(jdValueAdd)) {
	                	layer.msg("时间期限需要写成含0的正整数");
	                	return;
	                }
	                var esorder = "";
	                var trList = $("#fcPropTbody").find("tr");
	                if(typeof(trList) == 'undefined' || trList.length <= 0) {
	                	esorder = (0 + 1) + "";
	                } else {
	                	var tdLd = trList.length -1;
	                	var tdList = trList.eq(tdLd).find("td");
	                	var lastEsorder = tdList.eq(0).find("input[name='jdId']").attr("id");
	                	esorder = (parseInt(lastEsorder) + 1) + "";
	                }
	    			var obj = {
	   					"structureId": "${structureId }",
	   					"tagKey":jdKeyAdd,
	   					"tagValue":jdValueAdd,
	   					"esorder":esorder,
	    			};
	    			$.ajax({
	    				url:"${ctx}/dataTemp_saveAddDataOfJD.do",
	    				type:"post",
	    				async:false,
	    				cache: false,
	    				dataType:"json",
	    				data:obj,
	    				success:function(data){
	    					var flag = data.dataFlag;
	    					if(flag == "noMainData") {
	    						layer.msg("抱歉，请先保存起始日期和保管期限");
	    					} else if(flag == "haveRepeat") {
	    						layer.msg("抱歉，限期字段存在重复");
	    					} else {
	    						var saveFlag = data.saveFlag;
	    						if(saveFlag == "success") {
	    							setTableData();
	    							$("#jdKeyAdd").val("");
	    							$("#jdValueAdd").val("")
	    							layer.close(index);
	    						} else {
	    							layer.msg("抱歉，保存失败");
	    						}
	    					}
	    				},
	    				error:function(){
	    					layer.msg("系统错误请重试");
	    				}	
	    			});
	            }
	        });
		}
		
		function update() {
			var objs = document.getElementsByTagName('input');
			var ids = '';
			var n = 0;
			for(var i=0; i<objs.length; i++) {
			   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='jdId' && objs[i].checked==true ){
				  ids += objs[i].value+",";
				  n++;
			   }
			}
			if(ids==""){
				layer.msg("请选择一条数据");
				return;
			} else if(n != 1&& n!=0){
				layer.msg("请只选择一条数据");
				return;
			}
			ids = ids.substring(0, ids.length-1);
			var tr = $("input[value='"+ ids +"']").parent().parent();
			$("#jdKeyUpdate").val(tr.children().eq(1).text());
			$("#jdValueUpdate").val(tr.children().eq(2).text());
			layer.open({
	            type: 1,
	            title: '添加',
	            area:['470px', '240px'],
	            btn: ['确定','取消'],
	            content: $('#updateDiv'),
	            yes: function (index, layero) {
	                //按钮【按钮一】的回调
	                var jdKeyUpdate = $("#jdKeyUpdate").val();
	                if(isEmpty(jdKeyUpdate)) {
	                	layer.msg("请填写限期字段");
	                	return;
	                }
	                var jdValueUpdate = $("#jdValueUpdate").val();
	                if(isEmpty(jdValueUpdate)) {
	                	layer.msg("请填写时间期限");
	                	return;
	                }
	                if(isNotNumber(jdValueUpdate)) {
	                	layer.msg("时间期限需要写成含0的正整数");
	                	return;
	                }
	                var esorder = $("input[value='"+ ids +"']").attr("id");
	    			var obj = {
	    				"id":ids,
	   					"structureId": "${structureId }",
	   					"tagKey":jdKeyUpdate,
	   					"tagValue":jdValueUpdate,
	   					"esorder":esorder
	    			};
	    			$.ajax({
	    				url:"${ctx}/dataTemp_saveAddDataOfJD.do",
	    				type:"post",
	    				async:false,
	    				cache: false,
	    				dataType:"json",
	    				data:obj,
	    				success:function(data){
	    					console.log(data);
	    					var flag = data.dataFlag;
	    					if(flag == "noMainData") {
	    						layer.msg("抱歉，请先保存起始日期和保管期限");
	    					} else if(flag == "haveRepeat") {
	    						layer.msg("抱歉，限期字段存在重复");
	    					} else {
	    						var saveFlag = data.saveFlag;
	    						if(saveFlag == "success") {
	    							var tr = $("input[value='"+ ids +"']").parent().parent();
	    							tr.children().eq(1).text(jdKeyUpdate);
	    							tr.children().eq(2).text(jdValueUpdate);
	    							layer.msg("保存成功");
	    							$("#jdKeyUpdate").val("");
	    							$("#jdValueUpdate").val("")
	    							layer.close(index);
	    						} else {
	    							layer.msg("抱歉，保存失败");
	    						}
	    					}
	    				},
	    				error:function(){
	    					layer.msg("系统错误请重试");
	    				}	
	    			});
	            }
	        });
		}
		
		function del() {
			var objs = document.getElementsByTagName('input');
			var ids = '';
			for(var i=0; i<objs.length; i++) {
			   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='jdId' && objs[i].checked==true ){
				  ids += objs[i].value+",";
			   }
			}
			if(ids==""){
				layer.msg("请选择一条数据");
				return;
			} 
			ids = ids.substring(0, ids.length-1);
			layer.confirm('确定要删除该保管期限时限？', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"${ctx}/dataTemp_deleteTableDataOfJD.do",
					type:"post",
					async:false,
					cache: false,
					dataType:"json",
					data:{"ids":ids},
					success:function(data){
						var flag = data.flag;
						if(flag == "failed") {
							layer.msg("删除失败");
						} else {
							layer.msg("删除成功");
							var idArr = ids.split(",");
							for(var i = 0; i < idArr.length; i++) {
								var oneId = idArr[i];
								var tr = $("input[value='"+ oneId +"']").parent().parent();
								tr.remove();
							}
							layer.close(index);
						}
					},
					error:function(){
						layer.msg("系统错误请重试");
					}	
				});
			}); 
		}
		
		function setTableData() {
			var obj = {
				"structureId": "${structureId }"
			}
			$.ajax({
				url:"${ctx}/dataTemp_getTableDataOfJD.do",
				type:"post",
				async:false,
				cache: false,
				dataType:"json",
				data:obj,
				success:function(data){
					var flag = data.dataFlag;
					if(flag == "have") {
						var tData = data.data;
						if(tData != null && typeof(tData) != 'undefined') {
							$("#fcPropTbody").empty();
							for(var x = 0; x < tData.length; x++) {
								var tObj = tData[x];
								var str = "";
								str += "<tr onclick='changeTRBColor(this)'><td align='center'>";
								str += "<input type='checkbox' name='propId' id='"+ tObj.esorder +"'  value='"+ tObj.id +"' /></td>";
								str += "<td align='center' title ='"+ tObj.tagKey +"'>"+ tObj.tagKey +"</td>";
								str += "<td align='center' title ='"+ tObj.tagValue +"'>"+ tObj.tagValue +"</td></tr>";
								$("#fcPropTbody").append(str);
							}
						} else {
							$("#fcPropTbody").empty();
						}
					}
				},
				error:function(){
					layer.msg("系统错误请重试");
				}	
			});
		}
	</script>
</html>