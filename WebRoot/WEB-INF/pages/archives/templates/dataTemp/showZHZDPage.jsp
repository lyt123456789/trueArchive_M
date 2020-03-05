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
    	<link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
	    <style type="text/css">
	      .widthheight {
	      	width:480px;
	      	height:340px;
	      }
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
	      	margin-left:15px;
	      	margin-top:15px;
	      	width:490px;
	      	height:475px;
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
	      .psTitle {
	      	font-size: 16px;
		    text-align: center;
		    font-weight: 900;
		    font-family: serif;
		    color: #d2d2d2;
		    margin-bottom: 5px;
		    margin-top:5px;
	      }
	      .marLef {
	      	margin-left:10px;
	      }
	      .buttonDiv {
	      	text-align:center;
	      	width:98%;
	      	height:40px;
	      	margin-top:7px;
	      }
	      .rightDiv {
	      	position:absolute;
	      	left:520px;
	      }
	      #ofForm {
	      	top:0px;
	      }
	      #ofDdto{
	      	top:462px;
	      }
	      #usingGrid {
	      	top:924px;
	      }
	      .layui-btn .layui-icon{
	        font-size: 18px;
    		height: 10px;
    		line-height: 10px;
    		display: block;
    	  }
		  .layui-btnUpDownmax{
	        margin-left: 0;
	    	padding: 0 10px;
	    	height: 30px;
	    	line-height: 30px;
	    	background-color: #5FB878;
	    	border-color: #5FB878;
		  }
		  .layui-btnUpDown {
		  	margin-left: 0;
    		padding: 0 10px;
    		height: 30px;
    		line-height: 30px;
    		background-color: #5FB878;
    		border-color: #5FB878;
		  }
		  .layui-btnCombin {
		  	margin-bottom:3px;
		 	padding: 0 5px;
    		height: 23px;
    		line-height: 23px;
    		background-color: #5FB878;
    		border-color: #5FB878;
		  }
		  .upBtn {
		  	position: absolute;
	    	left: 215px;
	    	top: 152px;
		  }
		  .upMaxBtn {
		  	position: absolute;
	    	left: 225px;
	    	top: 101px;
		  }
		  .downBtn {
		  	position: absolute;
	    	left: 215px;
	    	top: 305px;
		  }
		  .downMaxBtn {
		  	position: absolute;
	    	left: 215px;
	    	top: 356px;
		  }
		  .layui-transfer-active {
    		margin: 0 31px;
    	  }
    	  .titleOfTransfer {
    	  	padding-left: 0;
		    padding-right: 15px;
		    line-height: 38px;
		    background: 0 0;
		    color: #666;
    	  }
    	  .rightBtn {
    	  	position: absolute;
	    	left: 215px;
	    	top: 203px;
    	  }
    	  .leftBtn {
    	  	position: absolute;
	    	left: 215px;
	    	top: 254px;
    	  }
    	  #isDisplayRight {
    	  	position: absolute;
    		top: 0;
    		left: 500px;
    		display:none;
    	  }
    	  #fileCodeProp {
    	  	width:100%;
    	  	margin:20px 0px 10px 0px;
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
	    </style>
	</head>
	<body>
		<div id="isDisplay">
			<input id="structureId" name="structureId" type="hidden" value="${structureId }"/>
			<div id="combine" class="commonFather">
				<div class="title">选择组合字段 </div>
				<div class="psTitle">PS:无构成字段的组合字段将不会保存</div>
				<div class="marLef">
					<div class="layui-transfer layui-form layui-border-box" lay-filter="LAY-transfer-1">
						<div class="layui-transfer-box" data-index="0" style="width: 200px; height: 360px;">
							<div class="layui-transfer-header">
								<div class="titleOfTransfer" lay-skin="primary">
									<span>源</span>
								</div>
							</div>
							<div class="layui-transfer-search">
								<i class="layui-icon layui-icon-search"></i>
								<input type="input" class="layui-input" onkeyup="searchLike('combine','left')" placeholder="关键词搜索">
							</div>
							<ul class="layui-transfer-data" style="height:267px;">
								
							</ul>
						</div>
						<div class="layui-transfer-active"></div>
						<div class="layui-transfer-box" data-index="1" style="width: 200px; height: 360px;">
							<div class="layui-transfer-header">
								<div class="titleOfTransfer" lay-skin="primary">
									<span>目标</span>
								</div>
							</div>
							<div class="layui-transfer-search">
								<i class="layui-icon layui-icon-search"></i>
								<input type="input" class="layui-input" placeholder="关键词搜索" onkeyup="searchLike('combine','right')">
							</div>
							<ul class="layui-transfer-data" style="height: 267px;">
							</ul>
						</div>
					</div>
				</div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('combine')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('combine')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="左移" class="layui-btn layui-btnUpDown rightBtn" onclick="moveToLeft('combine')">
						<i class="layui-icon layui-icon-prev"></i>
					</button>
					<button type="button" title="右移" class="layui-btn layui-btnUpDown leftBtn" onclick="moveToRight('combine')">
						<i class="layui-icon layui-icon-next"></i>
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('combine')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('combine')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveChooseData('combine')">保存</button>
				</div>
			</div>
		</div>
		
		<div id="isDisplayRight">
			<input type="hidden" name="id_tag" id="checkIdTag"/>
			<div id="combineField" class="commonFather">
				<div class="title">选择组合字段构成</div>
				<div class="title">
					连接符 <input style="width:30px;height:23px;display:inline;" type='text' id="connectorInput" class='layui-input'>
					<button type="button" title="拼接连接符" class="layui-btn layui-btnCombin" onclick="moveSymbolToRight('combineField')"> 
						<i class="layui-icon">&#xe602;</i>
					</button>
				</div>
				<div class="marLef">
					<div class="layui-transfer layui-form layui-border-box" lay-filter="LAY-transfer-1">
						<div class="layui-transfer-box" data-index="0" style="width: 200px; height: 360px;">
							<div class="layui-transfer-header">
								<div class="titleOfTransfer" lay-skin="primary">
									<span>源</span>
								</div>
							</div>
							<div class="layui-transfer-search">
								<i class="layui-icon layui-icon-search"></i>
								<input type="input" class="layui-input" onkeyup="searchLike('combineField','left')" placeholder="关键词搜索">
							</div>
							<ul class="layui-transfer-data" style="height: 267px;">
								
							</ul>
						</div>
						<div class="layui-transfer-active"></div>
						<div class="layui-transfer-box" data-index="1" style="width: 200px; height: 360px;">
							<div class="layui-transfer-header">
								<div class="titleOfTransfer" lay-skin="primary">
									<span>目标</span>
								</div>
							</div>
							<div class="layui-transfer-search">
								<i class="layui-icon layui-icon-search"></i>
								<input type="input" class="layui-input" placeholder="关键词搜索" onkeyup="searchLike('combineField','right')">
							</div>
							<ul class="layui-transfer-data" style="height: 267px;">
							</ul>
						</div>
					</div>
				</div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('combineField')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('combineField')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="左移" class="layui-btn layui-btnUpDown rightBtn" onclick="moveToLeft('combineField')">
						<i class="layui-icon layui-icon-prev"></i>
					</button>
					<button type="button" title="右移" class="layui-btn layui-btnUpDown leftBtn" onclick="moveToRight('combineField')">
						<i class="layui-icon layui-icon-next"></i>
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('combineField')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('combineField')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
				</div>
			</div>
		</div>
		
		<div id="fileCodeProp" style="display:none">
			<div class="searchBar">
				<div class="codePropTitle">添加替换值</div>
				<input id="parentId" name="parentId" type="hidden"/>
				<input id="childrenId" name="childrenId" type="hidden"/>
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
					    <col align="center" width="5%">
					    <col align="center" width="19%">
					    <col align="center" width="19%">
					    <col align="center" width="19%">
					    <col align="center" width="19%">
					    <col align="center" width="19%">
					</colgroup>
					<thead>
						<tr>
			    	  		<th></th>
			    	  		<th>操作</th>
			    	  		<th>字段</th>
		    	  			<th>被替换值</th>
		    	  			<th>替换值</th>
		    	  			<th>描述</th>
	                	</tr>
	 	 			</thead>
					<tbody id="fcPropTbody">
				    	
	  				</tbody>
				</table>
			</div>
		</div>
	</body>
	
	<div id="addDiv" hidden="hidden">
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>被替换值：</label>
			<div class='layui-input-block' style='width:320px'>
			    <input type='text' id="tagPropValueAdd" placeholder='必填，最多可填写九个字符' autocomplete='off' class='layui-input'>
			</div>
		</div>
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>替换值：</label>
			<div class='layui-input-block' style='width:320px'>
			 	<input type='text' id='tagCodeValueAdd' placeholder='非必填，最多可填写九个字符' autocomplete='off' class='layui-input'>
			</div>
		</div>
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>描述：</label>
			<div class='layui-input-block' style='width:320px'>
			 	<input type='text' id='descriptionAdd' autocomplete='off' class='layui-input'>
			</div>
		</div>
	</div>
	
	<div id="updateDiv" hidden="hidden">
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>被替换值：</label>
			<div class='layui-input-block' style='width:320px'>
			    <input type='text' id="tagPropValueUpdate" placeholder='必填，最多可填写九个字符' autocomplete='off' class='layui-input'>
			</div>
		</div>
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>替换值：</label>
			<div class='layui-input-block' style='width:320px'>
			 	<input type='text' id='tagCodeValueUpdate' placeholder='必填，最多可填写九个字符' autocomplete='off' class='layui-input'>
			</div>
		</div>
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>描述：</label>
			<div class='layui-input-block' style='width:320px'>
			 	<input type='text' id='descriptionUpdate' autocomplete='off' class='layui-input'>
			</div>
		</div>
	</div>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		//组合字段右侧复选框选中
		function showReplaceData(dom) {
			if(!$(dom).hasClass("layui-form-checked")) {//没有选中
				var div = $("#combineField ul").eq(1).children().find("div");
				for(var i = 0; i < div.length; i++) {
					var divOne = div[i];
					$(divOne).removeClass("layui-form-checked");
				}
				$(dom).addClass("layui-form-checked");
				var idTag = $(dom).prev().val();
				var tagName = $(dom).prev().attr("title");
				var parentTagId = $("#combine ul").eq(1).children().find(".layui-form-checked").prev().val();
				var obj = {
					"structureId": "${structureId }",
					"tagId":idTag,
					"parentTagId":parentTagId
				};
				var layerIndex = layer.load(1,{shade:[0.6,'#000']});
				$.ajax({
					url:"${ctx}/dataTemp_checkAndGetZHZDProp.do",
					type:"post",
					async:false,
					cache: false,
					dataType:"json",
					data:obj,
					success:function(data){
						var flag = data.isHave;
						if(flag == "YES") {
							$("#fileCodeProp").show();
							$("#parentId").val(parentTagId);
							$("#childrenId").val(idTag);
							$("#tagName").val(tagName);
							var tData = data.data;
							if(tData != null && typeof(tData) != 'undefined') {
								$("#fcPropTbody").empty();
								for(var x = 0; x < tData.length; x++) {
									var tObj = tData[x];
									var str = "";
									str += "<tr onclick='changeTRBColor(this)'><td align='center'>";
									str += "<input type='checkbox' name='propId' id='"+ tObj.id +"'  value='"+ tObj.id +"' /></td>";
									str += "<td align='center' title ='"+ tObj.oper +"'>"+ tObj.oper +"</td>";
									str += "<td align='center' title ='"+ tagName +"'>"+ tagName +"</td>";
									str += "<td align='center' title ='"+ tObj.prePropValue +"'>"+ tObj.prePropValue +"</td>";
									str += "<td align='center' title ='"+ tObj.propValue +"'>"+ tObj.propValue +"</td>";
									str += "<td align='center' title ='"+ tObj.description +"'>"+ tObj.description +"</td></tr>";
									$("#fcPropTbody").append(str);
								}
							} else {
								$("#fcPropTbody").empty();
								layer.msg("暂无替换值");
							}
							layer.close(layerIndex);
						} else {
							$("#fileCodeProp").hide();
							layer.msg("请先点击保存，再设置替换值");
							layer.close(layerIndex);
						}
					},
					error:function(){
						layer.msg("系统错误请重试");
						layer.close(layerIndex);
					}	
				});
			} else {//事先已经选中
				$("#parentId").val("");
				$("#childrenId").val("");
				$("#tagName").val("");
				$("#fileCodeProp").hide();
				$("#fcPropTbody").empty();
				$(dom).removeClass("layui-form-checked");
			};
		}
		
		//刷新表格
		function setTbody() {
			var parentId = $("#parentId").val();
			var childrenId = $("#childrenId").val();
			var tagName = $("#tagName").val();
			var obj = {
				"structureId": "${structureId }",
				"parentId":parentId,
				"childrenId":childrenId
			};
			$.ajax({
				url:"${ctx}/dataTemp_getZHZDPropDataJust.do",
				type:"post",
				async:false,
				cache: false,
				dataType:"json",
				data:obj,
				success:function(data){
					var tData = data.data;
					if(tData != null && typeof(tData) != 'undefined') {
						$("#fcPropTbody").empty();
						for(var x = 0; x < tData.length; x++) {
							var tObj = tData[x];
							var str = "";
							str += "<tr onclick='changeTRBColor(this)'><td align='center'>";
							str += "<input type='checkbox' name='propId' id='"+ tObj.id +"'  value='"+ tObj.id +"' /></td>";
							str += "<td align='center' title ='"+ tObj.oper +"'>"+ tObj.oper +"</td>";
							str += "<td align='center' title ='"+ tagName +"'>"+ tagName +"</td>";
							str += "<td align='center' title ='"+ tObj.prePropValue +"'>"+ tObj.prePropValue +"</td>";
							str += "<td align='center' title ='"+ tObj.propValue +"'>"+ tObj.propValue +"</td>";
							str += "<td align='center' title ='"+ tObj.description +"'>"+ tObj.description +"</td></tr>";
							$("#fcPropTbody").append(str);
						}
					} else {
						$("#fcPropTbody").empty();
					}
				},
				error:function(){
					layer.msg("系统错误请重试");
				}	
			});
		}
		
		function add() {
			layer.open({
	            type: 1,
	            title: '新增替换值',
	            area:['470px', '300px'],
	            btn: ['确定','取消'],
	            content: $('#addDiv'),
	            yes: function (index, layero) {
	                //按钮【按钮一】的回调
	                var tagPropValue = $("#tagPropValueAdd").val();
	                if(isEmpty(tagPropValue)) {
	                	layer.msg("请填写被替代值");
	                	return;
	                } else {
	                	if(tagPropValue.length > 9) {
	                		layer.msg("被替代值长度不能超过九位");
		                	return;
	                	}
	                }
	                var tagCodeValue = $("#tagCodeValueAdd").val();
	                if(!isEmpty(tagCodeValue) && tagCodeValue.length > 9) {
	                	layer.msg("替代值长度不能超过九位");
	                	return;
	                }
	                var description = $("#descriptionAdd").val();
	                var parentId = $("#parentId").val();
	    			var childrenId = $("#childrenId").val();
	    			var obj = {
	   					"idStructure": "${structureId }",
	   					"parentTagId":parentId,
	   					"tagId":childrenId,
	   					"prePropValue":tagPropValue,
	   					"propValue":tagCodeValue,
	   					"oper":"替换",
	   					"description":description
	    			};
	    			$.ajax({
	    				url:"${ctx}/dataTemp_checkZHZDPropIsHave.do",
	    				type:"post",
	    				async:false,
	    				cache: false,
	    				dataType:"json",
	    				data:obj,
	    				success:function(data){
	    					var flag = data.isHave;
	    					if(flag == "have") {
	    						layer.msg("抱歉，该字段下存在相同的属性值");
	    					} else {
	    						$.ajax({
	    	        				url:"${ctx}/dataTemp_saveZHZDPropData.do",
	    	        				type:"post",
	    	        				async:false,
	    	        				cache: false,
	    	        				dataType:"json",
	    	        				data:obj,
	    	        				success:function(data){
	    	        					var flag = data.flag;
	    	        					if(flag == "failed") {
	    	        						layer.msg("保存失败");
	    	        					} else {
	    	        						$("#tagPropValueAdd").val("");
	    	        						$("#tagCodeValueAdd").val("");
	    	        						$("#descriptionAdd").val("");
	    	        						layer.msg("新增成功");
	    	        						setTbody();
	    	        						layer.close(index);
	    	        					}
	    	        				},
	    	        				error:function(){
	    	        					layer.msg("系统错误请重试");
	    	        				}	
	    	        			});
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
			   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='propId' && objs[i].checked==true ){
				  ids += objs[i].value+",";
				  n++;
			   }
			}
			if(ids==""){
				alert("请选择一条数据");
				return;
			} else if(n != 1&& n!=0){
				alert("请选择一条数据");
				return;
			}
			ids = ids.substring(0, ids.length-1);
			var tr = $("#"+ids).parent().parent();
			$("#tagPropValueUpdate").val(tr.children().eq(3).text());
			$("#tagCodeValueUpdate").val(tr.children().eq(4).text());
			$("#descriptionUpdate").val(tr.children().eq(5).text());
			layer.open({
	            type: 1,
	            title: '修改替换值',
	            area:['470px', '300px'],
	            btn: ['确定','取消'],
	            content: $('#updateDiv'),
	            yes: function (index, layero) {
	                //按钮【按钮一】的回调
	                var tagPropValue = $("#tagPropValueUpdate").val();
	                if(isEmpty(tagPropValue)) {
	                	layer.msg("请填写被替代值");
	                	return;
	                } else {
	                	if(tagPropValue.length > 9) {
	                		layer.msg("被替代值长度不能超过九位");
		                	return;
	                	}
	                }
	                var tagCodeValue = $("#tagCodeValueUpdate").val();
	                if(!isEmpty(tagCodeValue) && tagCodeValue.length > 9) {
	                	layer.msg("替代值长度不能超过九位");
	                	return;
	                }
	                var description = $("#descriptionUpdate").val();
	                var parentId = $("#parentId").val();
	    			var childrenId = $("#childrenId").val();
	    			var obj = {
	    				"id":ids,
	   					"idStructure": "${structureId }",
	   					"parentTagId":parentId,
	   					"tagId":childrenId,
	   					"prePropValue":tagPropValue,
	   					"propValue":tagCodeValue,
	   					"oper":"替换",
	   					"description":description
	    			};
	    			$.ajax({
	    				url:"${ctx}/dataTemp_checkZHZDPropIsHave.do",
	    				type:"post",
	    				async:false,
	    				cache: false,
	    				dataType:"json",
	    				data:obj,
	    				success:function(data){
	    					var flag = data.isHave;
	    					if(flag == "have") {
	    						layer.msg("抱歉，该字段下存在相同的属性值");
	    					} else {
	    						$.ajax({
	    	        				url:"${ctx}/dataTemp_saveZHZDPropData.do",
	    	        				type:"post",
	    	        				async:false,
	    	        				cache: false,
	    	        				dataType:"json",
	    	        				data:obj,
	    	        				success:function(data){
	    	        					var flag = data.flag;
	    	        					if(flag == "failed") {
	    	        						layer.msg("保存失败");
	    	        					} else {
	    	        						$("#tagPropValueUpdate").val("");
	    	        						$("#tagCodeValueUpdate").val("");
	    	        						$("#descriptionUpdate").val("");
	    	        						layer.msg("修改成功");
	    	        						setTbody();
	    	        						layer.close(index);
	    	        					}
	    	        				},
	    	        				error:function(){
	    	        					layer.msg("系统错误请重试");
	    	        				}	
	    	        			});
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
			   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='propId' && objs[i].checked==true ){
				  ids += objs[i].value+",";
			   }
			}
			if(ids==""){
				alert("请选择一条数据");
				return;
			} 
			ids = ids.substring(0, ids.length-1);
			layer.confirm('确定要删除该属性值？', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url:"${ctx}/dataTemp_deleteZHZDPropData.do",
					type:"post",
					async:false,
					cache: false,
					dataType:"json",
					data:{"idStr":ids},
					success:function(data){
						var flag = data.flag;
						if(flag == "failed") {
							layer.msg("删除失败");
						} else {
							layer.msg("删除成功");
							setTbody();
							layer.close(index);
						}
					},
					error:function(){
						layer.msg("系统错误请重试");
					}	
				});
			}); 
		}
		
		function changeTRBColor(dom) {
			$("#fcPropTable").find("tr td").removeClass("ffd");
			$(dom).find("td").addClass("ffd");
		}
	
		//复选框选中
		function addSelectedCss(dom) {
			if(!$(dom).hasClass("layui-form-checked")) {
				$(dom).addClass("layui-form-checked");
			} else {
				$(dom).removeClass("layui-form-checked");
			};
		}
		
		//右侧复选框选中
		function showMoreAddData(dom) {
			var layerIndex = layer.load(1,{shade:[0.6,'#000']});
			$("#fileCodeProp").hide();
			if(!$(dom).hasClass("layui-form-checked")) {//没有选中
				var div = $("#combine ul").children().find("div");
				for(var i = 0; i < div.length; i++) {
					var divOne = div[i];
					$(divOne).removeClass("layui-form-checked");
				}
				$(dom).addClass("layui-form-checked");
				$("#isDisplayRight").css("display","block");
				var idTag = $(dom).prev().val();
				var obj = {
					"structureId": "${structureId }",
					"idTag":idTag
				};
				$.ajax({
					url:"${ctx}/dataTemp_getOneZHZDData.do",
					type:"post",
					async:false,
					cache: false,
					dataType:"json",
					data:obj,
					success:function(data){
						var etList = data.etList;
						$("#checkIdTag").val(idTag);
						$("#parentId").val("");
						$("#childrenId").val("");
						$("#tagName").val("");
						var leftUl = $("#combineField").find("ul").eq(0);
						var rightUl = $("#combineField").find("ul").eq(1);
						
						//设置源数据
						if(etList == null || typeof(etList) == 'undefined' || etList[0] == null || typeof(etList[0]) == 'undefined') {
							$(leftUl).empty();
							$(leftUl).append("<p class='layui-none'>无数据</p>");
						} else {
							$(leftUl).empty();
							for(var i = 0; i < etList.length; i++) {
								var str = "";
								var obj = etList[i];
								if(obj.id == idTag) {
									continue;
								}
								str += "<li>";
								str +="<input type='checkbox' name='layTransferLeftCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ obj.esIdentifier +"' value='"+ obj.id +"'>";
								str +="<div class='layui-unselect layui-form-checkbox' onclick='addSelectedCss(this)' lay-skin='primary'>";
								str +="<span>"+ obj.esIdentifier +"</span><i class='layui-icon layui-icon-ok'></i></div></li>";
								$(leftUl).append(str);
							}
						}
						
						var commonLst = data.commonLst;
						//设置目标数据
						if(commonLst == null || typeof(commonLst) == 'undefined' || commonLst[0] == null || typeof(commonLst[0]) == 'undefined') {
							$(rightUl).empty();
							$(rightUl).append("<p class='layui-none'>无数据</p>");
						} else {
							$(rightUl).empty();
							for(var i = 0; i < commonLst.length; i++) {
								var str = "";
								var obj = commonLst[i];
								str +="<li>";
								str +="<input type='checkbox' name='layTransferLeftCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ obj.tagName +"' value='"+ obj.idtag +"'>";
								if(obj.idtag == 0) {
									str +="<div class='layui-unselect layui-form-checkbox' onclick='addSelectedCss(this)' lay-skin='primary'>";
									str +="<span>"+ obj.tagName + "|false" +"</span><i class='layui-icon layui-icon-ok'></i></div></li>";
								} else {
									str +="<div class='layui-unselect layui-form-checkbox' onclick='showReplaceData(this)' lay-skin='primary'>";
									str +="<span>"+ obj.tagName + "|true" +"</span><i class='layui-icon layui-icon-ok'></i></div></li>";
								}
								$(rightUl).append(str);
							}
						}
						layer.close(layerIndex);
					},
					error:function(){
						layer.msg("系统错误请重试");
						layer.close(layerIndex);
					}	
				});
			} else {//事先已经选中
				$("#checkIdTag").val("");
				$("#parentId").val("");
				$("#childrenId").val("");
				$("#tagName").val("");
				$(dom).removeClass("layui-form-checked");
				$("#isDisplayRight").hide();
				layer.close(layerIndex);
			};
		}
		
		//模糊搜索
		function searchLike(dom,zy) {
			var input = null;
			var ul = null;
			if(zy == "left") {
				input = $("#"+dom+" .layui-transfer-search").find("input").eq(0);
				ul = $("#"+dom).find("ul").eq(0);
			} else {
				input = $("#"+dom+" .layui-transfer-search").find("input").eq(1);
				ul = $("#"+dom).find("ul").eq(1);
			}
			var value = $(input).val();
			$(ul).children().css("display","none");
			var span = $(ul).find("span");
			var t = 0;
			for(var i = 0; i < span.length; i++) {
				var spanOne = span[i];
				var tagName = $(spanOne).text();
				if(tagName.indexOf(value) != -1 ) {
					t++;
					$(spanOne).parent().parent().css("display","block");
				}
			}
			if(t == 0) {
				$(ul).append("<p class='layui-none'>无数据</p>");
			}
		}
		
		//左移
		function moveToLeft(dom) {
			var leftP = $("#"+dom).find("ul").eq(0).find("p");
			if($(leftP).length > 0) {
				$(leftP).remove();
			}   
			//找到选中的li
			var index = $("#"+dom+" ul" ).eq(1).find('.layui-form-checked').parent(); 
			if(typeof(index[0]) == "undefined") {
				layer.msg("请选中后，再进行移位操作");
				return;
			}
			for(var i=0;i<index.length;i++){
				var indexOne = index[i];
				var input = $(indexOne).children("input");
				var tagName = $(input).attr("title");
				var tagId = $(input).val();
				var dataTitle = $(indexOne).attr("data-title");
				if(dom == "combineField" && tagId == "0") {
					$(indexOne).remove();
					continue;
				}
				var str = "";
				if(dataTitle == 'exist') {
					str += "<li data-title='exist'>";
				} else {
					str += "<li data-title='new'>";
				}
				str += "<input type='checkbox' name='layTransferLeftCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ tagName+"' value='"+ tagId  +"'>";
				str += "<div class='layui-unselect layui-form-checkbox' onclick='addSelectedCss(this)' lay-skin='primary'><span>"+ tagName +"</span>"
				str += "<i class='layui-icon layui-icon-ok'></i></div></li>";
				$("#"+dom+" ul" ).eq(0).append(str);
				$(indexOne).remove();
			}    
		}
		
		//右移
		function moveToRight(dom) {
			var rightP = $("#"+dom).find("ul").eq(1).find("p");
			if($(rightP).length > 0) {
				$(rightP).remove();
			}   
			//找到选中的li
			var index = $("#"+dom+" ul" ).eq(0).find('.layui-form-checked').parent();
			if(typeof(index[0]) == "undefined") {
				layer.msg("请选中后，再进行移位操作");
				return;
			}
			for(var i=0;i<index.length;i++){
				var indexOne = index[i];
				var input = $(indexOne).children("input");
				var tagName = $(input).attr("title");
				var tagId = $(input).val();
				var dataTitle = $(indexOne).attr("data-title");
				var str = "";
				if(dataTitle == "exist") {
					str += "<li data-title='exist'>";
				} else {
					str += "<li data-title='new'>";
				}
				str += "<input type='checkbox' name='layTransferRightCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ tagName +"' value='"+ tagId +"'>";
				if(dom == 'combineField') {
					str += "<div class='layui-unselect layui-form-checkbox' onclick='showReplaceData(this)' lay-skin='primary'><span>"+ tagName+ "|true" +"</span>"
				} else {
					str += "<div class='layui-unselect layui-form-checkbox' onclick='showMoreAddData(this)' lay-skin='primary'><span>"+ tagName +"</span>"
				}
				str += "<i class='layui-icon layui-icon-ok'></i></div></li>";
				$("#"+dom+" ul" ).eq(1).append(str);
				$(indexOne).remove();
			}     
		}
		
		//连接符设置
		function moveSymbolToRight(dom) {
			var  connector = $("#connectorInput").val();
			if(isEmpty(connector)) {
				layer.msg("请填写连接符");
				return;
			}
			if(!checkConnector(connector)) {
				layer.msg("请勿填写特殊字符");
				return;
			}
			var rightP = $("#"+dom).find("ul").eq(1).find("p");
			if($(rightP).length > 0) {
				$(rightP).remove();
			}   
			var str = "<li><input type='checkbox' name='layTransferLeftCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ connector +"' value='0'>";
			str += "<div class='layui-unselect layui-form-checkbox' onclick='addSelectedCss(this)' lay-skin='primary'><span>"+ connector + "|false" +"</span>"
			str += "<i class='layui-icon layui-icon-ok'></i></div></li>";
			$("#"+dom+" ul" ).eq(1).append(str);
		}
		
		//验证特殊字符
		function checkConnector(val){ 
		    var reg = new RegExp("[?*|':\"'\\[\\].<>/?……*（）——|{}【】‘；：”“'、？]"); 
		    var rs = ""; 
		    for (var i = 0, l = val.length; i < val.length; i++) { 
		        rs = rs + val.substr(i, 1).replace(reg, ''); 
		    }
		    if(rs != val) {
		    	return false;
		    } else {
		    	return true;
		    }
		}
		
		//上移
		function moveUp(dom) {
			//找到选中的li
			var index = $("#"+dom+" ul" ).eq(1).find('.layui-form-checked').parent(); 
			if(typeof(index[0]) == "undefined") {
				layer.msg("请选中后，再进行移位操作");
				return;
			}
			for(var i=0;i<index.length;i++){
				if(index.eq(i).index()!=0){
					 index.eq(i).prev().before(index.eq(i));
				}
			}       			
		}
		
		//置顶
		function moveUpMax(dom) {
			//找到选中的li
			var index = $("#"+dom+" ul" ).eq(1).find('.layui-form-checked').parent(); 
			if(typeof(index[0]) == "undefined") {
				layer.msg("请选中后，再进行移位操作");
				return;
			}
			for(var i=index.length-1;i>=0;i--){
				do{
					index.eq(i).prev().before(index.eq(i));
				}while(index.eq(i).index()!=0)
			}       			
		}
		
		//下移
		function moveDown(dom) {
			//找到选中的li
			var index = $("#"+dom+" ul" ).eq(1).find('.layui-form-checked').parent(); 
			if(typeof(index[0]) == "undefined") {
				layer.msg("请选中后，再进行移位操作");
				return;
			}
			for(var i=index.length-1;i>=0;i--){
				if(index.eq(i).index()!=($("#"+dom+" ul li").length-1)){
					 index.eq(i).next().after(index.eq(i));
				}
			}       			
		}
		
		//置底
		function moveDownMax(dom) {
			//找到选中的li
			var index = $("#"+dom+" ul" ).eq(1).find('.layui-form-checked').parent(); 
			if(typeof(index[0]) == "undefined") {
				layer.msg("请选中后，再进行移位操作");
				return;
			}
			for(var i=0;i<index.length;i++){
				do{
					index.eq(i).next().after(index.eq(i));
				}while(index.eq(i).index()!=($("#"+dom+" ul").eq(1).find("li").length-1))
			}       			
		}
		
		//获取并保存数据
		function saveChooseData(dom) {
			var layerIndex = layer.load(1,{shade:[0.6,'#000']});
			var structureId = "${structureId }";
			var checkIdTag = $("#checkIdTag").val();
			
			var nextDom = dom + "Field";
			var nextUL = $("#"+nextDom).find("ul").eq(1);
			var nextLI = $(nextUL).find("li");
			var tagIds = "";
			for(var i = 0; i < nextLI.length; i++) {
				var tagId= $(nextLI[i]).find("input").val();
				if(tagId == "0") {
					var ttl = $(nextLI[i]).find("input").attr("title");
					tagIds += ttl + "|false,";
				} else {
					tagIds += tagId + "|true,";
				}
			}
			tagIds = tagIds.substring(0, tagIds.length-1);
			
			var ul = $("#"+ dom).find("ul").eq(1);
			var li = $(ul).find("li");
			var computeArr = [];
			
			for(var i = 0; i < li.length; i++) {
				var idTag= $(li[i]).find("input").val();
				var existFlag = $(li[i]).attr("data-title");
				var obj = {};
				if(existFlag == "exist") {
					if(idTag == checkIdTag) {//1 老组合字段数据，处于选中状态
						if(isEmpty(tagId)) { //1.1 老组合字段，处于选中状态，构成字段已被清空，剔除！
							continue
						} else {		     //1.2 老组合字段，处于选中状态，构成字段存在，执行更新操作
							obj["tagIds"] = tagIds;
							obj["saveFlag"] = "update";
						}
					} else {				 //2 老组合字段数据，未处于选中状态，不执行任何操作
						obj["tagIds"] = "";
						obj["saveFlag"] = "nochange";
					}
				} else {
					if(idTag == checkIdTag) {//3 新组合字段， 处于选中状态
						if(isEmpty(tagId)) { //3.1 新组合字段，处于选中状态，构成字段为空，剔除！
							continue
						} else {			 //3.2 新组合字段，处于选中状态，构成字段存在，新增！
							obj["tagIds"] = tagIds;
							obj["saveFlag"] = "add";
						}
					} else {				 //4 新组合字段， 处于未选中状态，剔除
						continue;
					}
				}
				obj["idTag"] = idTag;
				obj["idStructure"] = structureId;
				computeArr.push(obj);
			}
			for(var i = 0; i < computeArr.length; i++) {
				var cObj = computeArr[i];
				cObj["esorder"] = (i+1)+"";
			}
			var saveObj = {};
			saveObj["jsonArrStr"] = JSON.stringify(computeArr);
			saveObj["idstructure"] = structureId;
			if(computeArr.length <= 0) {
				layer.confirm('确定清空组合字段？', {icon: 3, title:'提示'}, function(index){
					saveZDXZData(saveObj);
				  	layer.close(index);
				}); 
			} else {
				saveZDXZData(saveObj);
			}
			layer.close(layerIndex);
		}
		
		//刷新首个穿梭框
		function flushPage() {
			//重新请求，假做刷新
			window.location.href="${ctx}/dataTemp_toShowZHZDPage.do?structureId=${structureId}";
		}
		
		//保存数据
		function saveZDXZData(obj) {
			$.ajax({
				url:"${ctx}/dataTemp_saveDataOfZHZD.do",
				type:"post",
				async:true,
				cache: false,
				dataType:"json",
				data:obj,
				success:function(data){
					var flag = data.flag;
					if(flag == "success") {
						layer.msg("修改成功");
						flushPage();
					} else {
						layer.msg("修改失败");
					}
				},
				error:function(){
					layer.msg("系统错误请重试");
				}	
			});
		}
		
		//将数据展示在页面上
		function setDataShowInPage() {
			var allYuanList = JSON.parse('${allYuanList}');
			var abstractList = JSON.parse('${abstractList}');
			var leftUl = $("#combine").find("ul").eq(0);
			var rightUl = $("#combine").find("ul").eq(1);
			
			//设置源数据
			if(allYuanList == null || typeof(allYuanList) == 'undefined' || allYuanList[0] == null || typeof(allYuanList[0]) == 'undefined') {
				$(leftUl).append("<p class='layui-none'>无数据</p>");
			} else {
				for(var i = 0; i < allYuanList.length; i++) {
					var str = "";
					var obj = allYuanList[i];
					str += "<li data-title='new'>";
					str +="<input type='checkbox' name='layTransferLeftCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ obj.esIdentifier +"' value='"+ obj.id +"'>";
					str +="<div class='layui-unselect layui-form-checkbox' onclick='addSelectedCss(this)' lay-skin='primary'>";
					str +="<span>"+ obj.esIdentifier +"</span><i class='layui-icon layui-icon-ok'></i></div></li>";
					$(leftUl).append(str);
				}
			}
			
			//设置目标数据
			if(abstractList == null || typeof(abstractList) == 'undefined' || abstractList[0] == null || typeof(abstractList[0]) == 'undefined') {
				$(rightUl).append("<p class='layui-none'>无数据</p>");
			} else {
				for(var i = 0; i < abstractList.length; i++) {
					var str = "";
					var obj = abstractList[i];
					str +="<li data-title='exist'>";
					str +="<input type='checkbox' name='layTransferLeftCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ obj.tagName +"' value='"+ obj.idtag +"'>";
					str +="<div class='layui-unselect layui-form-checkbox' onclick='showMoreAddData(this)' lay-skin='primary'>";
					str +="<span>"+ obj.tagName +"</span><i class='layui-icon layui-icon-ok'></i></div></li>";
					$(rightUl).append(str);
				}
			}
		}
		
		$(function() {
			var dataFlag = "${dataFlag }";
			if(dataFlag == "NO") {
				layer.msg("请先在结构视图页面设置引用字段！");
				$("#isDispaly").css("display","none");
			}
			setDataShowInPage();	
		})
	</script>
</html>