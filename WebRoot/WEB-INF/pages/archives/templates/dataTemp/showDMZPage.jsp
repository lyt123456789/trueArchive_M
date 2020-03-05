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
	      	height:445px;
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
		  .upBtn {
		  	position: absolute;
	    	left: 215px;
	    	top: 124px;
		  }
		  .upMaxBtn {
		  	position: absolute;
	    	left: 225px;
	    	top: 73px;
		  }
		  .downBtn {
		  	position: absolute;
	    	left: 215px;
	    	top: 277px;
		  }
		  .downMaxBtn {
		  	position: absolute;
	    	left: 215px;
	    	top: 328px;
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
	    	top: 175px;
    	  }
    	  .leftBtn {
    	  	position: absolute;
	    	left: 215px;
	    	top: 226px;
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
			<div id="compute" class="commonFather">
				<div class="title">选择要维护的字段</div>
				<div id="abstractTransfer" class="marLef">
					<div class="layui-transfer layui-form layui-border-box" lay-filter="LAY-transfer-1">
						<div class="layui-transfer-box" data-index="0" style="width: 200px; height: 360px;">
							<div class="layui-transfer-header">
								<div class="titleOfTransfer" lay-skin="primary">
									<span>源</span>
								</div>
							</div>
							<div class="layui-transfer-search">
								<i class="layui-icon layui-icon-search"></i>
								<input type="input" class="layui-input" onkeyup="searchLike('compute','left')" placeholder="关键词搜索">
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
								<input type="input" class="layui-input" placeholder="关键词搜索" onkeyup="searchLike('compute','right')">
							</div>
							<ul class="layui-transfer-data" style="height: 267px;">
							</ul>
						</div>
					</div>
				</div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('compute')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('compute')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="左移" class="layui-btn layui-btnUpDown rightBtn" onclick="moveToLeft('compute')">
						<i class="layui-icon layui-icon-prev"></i>
					</button>
					<button type="button" title="右移" class="layui-btn layui-btnUpDown leftBtn" onclick="moveToRight('compute')">
						<i class="layui-icon layui-icon-next"></i>
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('compute')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('compute')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveChooseData('compute')">保存</button>
				</div>
			</div>
		</div>
	
		<div id="fileCodeProp" style="display:none;">
			<div class="searchBar">
				<div class="codePropTitle">填写属性值</div>
				<input id="checkTagId" name="tagId" type="hidden"/>
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
					    <col align="center" width="4%">
					    <col align="center" width="32%">
					    <col align="center" width="32%">
					    <col align="center" width="32%">
					</colgroup>
					<thead>
						<tr>
			    	  		<th></th>
			    	  		<th>属性值</th>
			    	  		<th>代码值</th>
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
			<label class='layui-form-label'>属性值：</label>
			<div class='layui-input-block' style='width:320px'>
			    <input type='text' id="tagPropValueAdd" placeholder='必填' autocomplete='off' class='layui-input'>
			</div>
		</div>
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>代码值：</label>
			<div class='layui-input-block' style='width:320px'>
			 	<input type='text' id='tagCodeValueAdd' placeholder='必填' autocomplete='off' class='layui-input'>
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
			<label class='layui-form-label'>属性值：</label>
			<div class='layui-input-block' style='width:320px'>
			    <input type='text' id="tagPropValueUpdate" placeholder='必填' autocomplete='off' class='layui-input'>
			</div>
		</div>
		<div class='layui-form-item' style='margin-top:20px'>
			<label class='layui-form-label'>代码值：</label>
			<div class='layui-input-block' style='width:320px'>
			 	<input type='text' id='tagCodeValueUpdate' placeholder='必填' autocomplete='off' class='layui-input'>
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
		$(function() {
			var dataFlag = "${dataFlag }";
			if(dataFlag == "NO") {
				layer.msg("请先在结构视图页面设置引用字段！");
				$("#isDispaly").css("display","none");
			}
			setDataShowInPage();	
		})
		
		//将数据展示在页面上
		function setDataShowInPage() {
			var allYuanList = JSON.parse('${allYuanList}');
			var abstractList = JSON.parse('${abstractList}');
			var leftUl = $("#compute").find("ul").eq(0);
			var rightUl = $("#compute").find("ul").eq(1);
			//设置源数据
			if(allYuanList == null || typeof(allYuanList) == 'undefined' || allYuanList[0] == null || typeof(allYuanList[0]) == 'undefined') {
				$(leftUl).append("<p class='layui-none'>无数据</p>");
			} else {
				for(var i = 0; i < allYuanList.length; i++) {
					var str = "";
					var obj = allYuanList[i];
					str += "<li>";
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
					str +="<li>";
					str +="<input type='checkbox' name='layTransferLeftCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ obj.tagName +"' value='"+ obj.idtag +"'>";
					str +="<div class='layui-unselect layui-form-checkbox' onclick='showMoreAddData(this)' lay-skin='primary'>";
					str +="<span>"+ obj.tagName +"</span><i class='layui-icon layui-icon-ok'></i></div></li>";
					$(rightUl).append(str);
				}
			}
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
				var str = "<li><input type='checkbox' name='layTransferLeftCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ tagName+"' value='"+ tagId  +"'>";
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
				var str = "<li><input type='checkbox' name='layTransferRightCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ tagName +"' value='"+ tagId +"'>";
				str += "<div class='layui-unselect layui-form-checkbox' onclick='showMoreAddData(this)' lay-skin='primary'><span>"+ tagName +"</span>"
				str += "<i class='layui-icon layui-icon-ok'></i></div></li>";
				$("#"+dom+" ul" ).eq(1).append(str);
				$(indexOne).remove();
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
		
		//左侧复选框选中
		function addSelectedCss(dom) {
			if(!$(dom).hasClass("layui-form-checked")) {
				$(dom).addClass("layui-form-checked");
			} else {
				$(dom).removeClass("layui-form-checked");
			};
		}
		
		//右侧复选框选中
		function showMoreAddData(dom) {
			if(!$(dom).hasClass("layui-form-checked")) {//没有选中
				var div = $("#compute ul").children().find("div");
				for(var i = 0; i < div.length; i++) {
					var divOne = div[i];
					$(divOne).removeClass("layui-form-checked");
				}
				$(dom).addClass("layui-form-checked");
				var idTag = $(dom).prev().val();
				var obj = {
					"structureId": "${structureId }",
					"idTag":idTag
				};
				var layerIndex = layer.load(1,{shade:[0.6,'#000']});
				$.ajax({
					url:"${ctx}/dataTemp_getDMZAndDMZProp.do",
					type:"post",
					async:false,
					cache: false,
					dataType:"json",
					data:obj,
					success:function(data){
						console.log(data);
						var flag = data.isHave;
						if(flag) {
							layer.close(layerIndex);
							$("#fileCodeProp").css("display","block");
							$("#checkTagId").val(idTag);
							var tData = data.data;
							if(tData != null && typeof(tData) != 'undefined') {
								$("#fcPropTbody").empty();
								for(var x = 0; x < tData.length; x++) {
									var tObj = tData[x];
									var str = "";
									str += "<tr onclick='changeTRBColor(this)'><td align='center'>";
									str += "<input type='checkbox' name='propId' id='"+ tObj.id +"'  value='"+ tObj.id +"' /></td>";
									str += "<td align='center' title ='"+ tObj.tagPropValue +"'>"+ tObj.tagPropValue +"</td>";
									str += "<td align='center' title ='"+ tObj.tagCodeValue +"'>"+ tObj.tagCodeValue +"</td>";
									str += "<td align='center' title ='"+ tObj.description +"'>"+ tObj.description +"</td></tr>";
									$("#fcPropTbody").append(str);
								}
							} else {
								$("#fcPropTbody").empty();
								layer.msg("暂无属性值");
							}
						} else {
							layer.close(layerIndex);
							$("#fileCodeProp").css("display","none");
							$("#checkTagId").val("");
							layer.msg("请先保存此字段，再设置属性值");
						}
					},
					error:function(){
						layer.msg("系统错误请重试");
						layer.close(layerIndex);
					}	
				});
			} else {//事先已经选中
				$(dom).removeClass("layui-form-checked");
			};
		}
		
		//刷新表格
		function setTbody(idTag) {
			var obj = {
				"structureId": "${structureId }",
  				"idTag":idTag
			};
			$.ajax({
				url:"${ctx}/dataTemp_justGetDMZAndDMZProp.do",
				type:"post",
				async:false,
				cache: false,
				dataType:"json",
				data:obj,
				success:function(data){
					var tData = data.list;
					if(tData != null && typeof(tData) != 'undefined') {
						$("#fcPropTbody").empty();
						for(var x = 0; x < tData.length; x++) {
							var tObj = tData[x];
							var str = "";
							str += "<tr onclick='changeTRBColor(this)'><td align='center'>";
							str += "<input type='checkbox' name='propId' id='"+ tObj.id +"'  value='"+ tObj.id +"' /></td>";
							str += "<td align='center' title ='"+ tObj.tagPropValue +"'>"+ tObj.tagPropValue +"</td>";
							str += "<td align='center' title ='"+ tObj.tagCodeValue +"'>"+ tObj.tagCodeValue +"</td>";
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
                title: '新增属性值',
                area:['470px', '300px'],
                btn: ['确定','取消'],
                content: $('#addDiv'),
                yes: function (index, layero) {
                    //按钮【按钮一】的回调
                    var tagPropValue = $("#tagPropValueAdd").val();
                    if(isEmpty(tagPropValue)) {
                    	layer.msg("请填写属性值");
                    	return;
                    }
                    var tagCodeValue = $("#tagCodeValueAdd").val();
                    if(isEmpty(tagCodeValue)) {
                    	layer.msg("请填写代码值");
                    	return;
                    }
                    if(isNotNumber(tagCodeValue)) {
                    	layer.msg("代码值只能填写为含零的正整数");
                    	return;
                    }
                    var description = $("#descriptionAdd").val();
                    var idTag = $("#checkTagId").val();
        			var obj = {
       					"idStructure": "${structureId }",
       					"idTag":idTag,
       					"tagPropValue":tagPropValue,
       					"tagCodeValue":tagCodeValue,
       					"description":description
        			};
        			$.ajax({
        				url:"${ctx}/dataTemp_checkDMZPropIsHave.do",
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
        	        				url:"${ctx}/dataTemp_saveDMZPropData.do",
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
        	        						layer.msg("新增成功");
        	        						setTbody(idTag);
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
			$("#tagPropValueUpdate").val(tr.children().eq(1).text());
			$("#tagCodeValueUpdate").val(tr.children().eq(2).text());
			$("#descriptionUpdate").val(tr.children().eq(3).text());
			$("#tagPropValueUpdate").val();
			layer.open({
                type: 1,
                title: '修改属性值',
                area:['470px', '300px'],
                btn: ['确定','取消'],
                content: $('#updateDiv'),
                yes: function (index, layero) {
                    //按钮【按钮一】的回调
                    var tagPropValue = $("#tagPropValueUpdate").val();
                    if(isEmpty(tagPropValue)) {
                    	layer.msg("请填写属性值");
                    	return;
                    }
                    var tagCodeValue = $("#tagCodeValueUpdate").val();
                    if(isEmpty(tagCodeValue)) {
                    	layer.msg("请填写代码值");
                    	return;
                    }
                    if(isNotNumber(tagCodeValue)) {
                    	layer.msg("代码值只能填写为含零的正整数");
                    	return;
                    }
                    var description = $("#descriptionUpdate").val();
                    var idTag = $("#checkTagId").val();
        			var obj = {
        				"id":ids,
       					"idStructure": "${structureId }",
       					"idTag":idTag,
       					"tagPropValue":tagPropValue,
       					"tagCodeValue":tagCodeValue,
       					"description":description
        			};
        			$.ajax({
        				url:"${ctx}/dataTemp_checkDMZPropIsHave.do",
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
        	        				url:"${ctx}/dataTemp_saveDMZPropData.do",
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
        	        						layer.msg("修改成功");
        	        						setTbody(idTag);
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
					url:"${ctx}/dataTemp_deleteDMZPropData.do",
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
							var idTag =$("#checkTagId").val();
							setTbody(idTag);
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
		
		//获取并保存数据
		function saveChooseData(dom) {
			var layerIndex = layer.load(1,{shade:[0.6,'#000']});
			var structureId = "${structureId }";
			var ul = $("#"+ dom).find("ul").eq(1);
			var li = $(ul).find("li");
			var computeArr = [];
			for(var i = 0; i < li.length; i++) {
				var idTag= $(li[i]).find("input").val();
				var obj = {};
				obj["idtag"] = idTag;
				obj["idstructure"] = structureId;
				obj["esorder"] = i+1;
				computeArr.push(obj);
			}
			var saveObj = {};
			saveObj["jsonArrStr"] = JSON.stringify(computeArr);
			saveObj["idstructure"] = structureId;
			if(computeArr.length <= 0) {
				layer.confirm('确定删除要维护的字段？', {icon: 3, title:'提示'}, function(index){
					saveZDXZData(saveObj);
				  	layer.close(index);
				}); 
			} else {
				saveZDXZData(saveObj);
			}
			layer.close(layerIndex);
		}
		
		//保存数据
		function saveZDXZData(obj) {
			$.ajax({
				url:"${ctx}/dataTemp_saveofDMZData.do",
				type:"post",
				async:true,
				cache: false,
				dataType:"json",
				data:obj,
				success:function(data){
					var flag = data.flag;
					if(flag == "success") {
						layer.msg("修改成功");
					} else {
						layer.msg("修改失败");
					}
				},
				error:function(){
					layer.msg("系统错误请重试");
				}	
			});
		}
		
	</script>
</html>