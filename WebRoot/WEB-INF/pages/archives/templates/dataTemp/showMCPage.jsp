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
			<div id="abstract" class="commonFather">
			<div class="title">选择名称字段</div>
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
								<input type="input" class="layui-input" onkeyup="searchLike('abstract','left')" placeholder="关键词搜索">
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
								<input type="input" class="layui-input" placeholder="关键词搜索" onkeyup="searchLike('abstract','right')">
							</div>
							<ul class="layui-transfer-data" style="height: 267px;">
							</ul>
						</div>
					</div>
				</div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('abstract')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('abstract')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="左移" class="layui-btn layui-btnUpDown rightBtn" onclick="moveToLeft('abstract')">
						<i class="layui-icon layui-icon-prev"></i>
					</button>
					<button type="button" title="右移" class="layui-btn layui-btnUpDown leftBtn" onclick="moveToRight('abstract')">
						<i class="layui-icon layui-icon-next"></i>
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('abstract')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('abstract')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveChooseData('abstract')">保存</button>
				</div>
			</div>
		</div>
	</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript" src="${ctx}/layui2.5.4/layui/layui.js"></script>
	<script type="text/javascript">
		//左侧复选框选中
		function addSelectedCss(dom) {
			if(!$(dom).hasClass("layui-form-checked")) {
				$(dom).addClass("layui-form-checked");
			} else {
				$(dom).removeClass("layui-form-checked");
			};
		}
		
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
			var leftUl = $("#abstract").find("ul").eq(0);
			var rightUl = $("#abstract").find("ul").eq(1);
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
				str += "<div class='layui-unselect layui-form-checkbox' onclick='addSelectedCss(this)' lay-skin='primary'><span>"+ tagName +"</span>"
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
		
		//获取并保存数据
		function saveChooseData(dom) {
			var structureId = "${structureId }";
			var ul = $("#"+ dom).find("ul").eq(1);
			var li = $(ul).find("li");
			var saveArr = [];
			for(var i = 0; i < li.length; i++) {
				var idTag= $(li[i]).find("input").val();
				var inJson = {};
				inJson["esorder"] = i+1;
				inJson["idstructure"] = structureId;
				inJson["idtag"] = idTag;
				saveArr.push(inJson);
			}
			var saveObj = {};
			saveObj["data"] = JSON.stringify(saveArr);
			saveObj["tableFlag"] = dom;
			saveObj["idstructure"] = structureId;
			if(saveArr.length == 0) {
				layer.confirm('确定删除目标数据？', {icon: 3, title:'提示'}, function(index){
					saveZDXZData(saveObj);
				  	layer.close(index);
				}); 
			} else {
				saveZDXZData(saveObj);
			}
		}
		
		//保存数据
		function saveZDXZData(obj) {
			obj = 
			$.ajax({
				url:"${ctx}/dataTemp_saveZDXZDataOfTable.do",
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