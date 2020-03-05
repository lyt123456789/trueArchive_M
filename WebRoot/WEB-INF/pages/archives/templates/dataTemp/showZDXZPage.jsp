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
	      	width:100%;
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
	    	left: 217px;
	    	top: 110px;
		  }
		  .upMaxBtn {
		  	position: absolute;
	    	left: 227px;
	    	top: 60px;
		  }
		  .downBtn {
		  	position: absolute;
	    	left: 217px;
	    	top: 270px;
		  }
		  .downMaxBtn {
		  	position: absolute;
	    	left: 217px;
	    	top: 320px;
		  }
	    </style>
	</head>
	<body>
		<div id="isDisplay">
			<input id="structureId" name="structureId" type="hidden" value="${structureId }"/>
			
			<div id="grid" class="commonFather">
				<div class="title">选择列表字段</div>
				<div id="gridTransfer" class="marLef"></div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('grid')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('grid')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('grid')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('grid')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveChooseData('grid')">保存</button>
				</div>
			</div>
			
			<div id="ofForm" class="commonFather rightDiv">
				<div class="title">选择表单字段</div>
				<div id="ofFormTransfer" class="marLef"></div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('ofForm')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('ofForm')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('ofForm')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('ofForm')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveChooseData('ofForm')">保存</button>
				</div>
			</div>
			
			<div id="adSearch" class="commonFather">
				<div class="title">综合查询字段</div>
				<div id="adSearchTransfer" class="marLef"></div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('adSearch')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('adSearch')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('adSearch')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('adSearch')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveChooseData('adSearch')">保存</button>
				</div>
			</div>
			
			<div id="ofDdto" class="commonFather rightDiv">
				<div class="title">选择追加携带字段</div>
				<div id="ofDdtoTransfer" class="marLef"></div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('ofDdto')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('ofDdto')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('ofDdto')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('ofDdto')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveChooseData('ofDdto')">保存</button>
				</div>
			</div>
			
			<div id="usingForm" class="commonFather">
				<div class="title">检索功能中参与检索的字段</div>
				<div id="usingFormTransfer" class="marLef"></div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('usingForm')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('usingForm')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('usingForm')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('usingForm')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveChooseData('usingForm')">保存</button>
				</div>
			</div>
			
			<div id="usingGrid" class="commonFather rightDiv">
				<div class="title">检索结果参与显示的字段</div>
				<div id="usingGridTransfer" class="marLef"></div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('usingGrid')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('usingGrid')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('usingGrid')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('usingGrid')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveChooseData('usingGrid')">保存</button>
				</div>
			</div>
		</div>
	</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript" src="${ctx}/layui2.5.4/layui/layui.js"></script>
	<script type="text/javascript">
		function setData() {
			var allZDJson = {};
			
			var gridList = JSON.parse('${gridList}');
			allZDJson['gridList']=gridList;
			
			var ofFormList = JSON.parse('${ofFormList}');
			allZDJson['ofFormList']=ofFormList;
			
			var adSearchList = JSON.parse('${adSearchList}');
			allZDJson['adSearchList']=adSearchList;
			
			var ofDdtoList = JSON.parse('${ofDdtoList}');
			allZDJson['ofDdtoList']=ofDdtoList;
			
			var usingFormList = JSON.parse('${usingFormList}');
			allZDJson['usingFormList']=usingFormList;
			
			var usingGridList = JSON.parse('${usingGridList}');
			allZDJson['usingGridList']=usingGridList;
			
			var allYuanList = JSON.parse('${allYuanList}');
			allZDJson['allYuanList']=allYuanList;
			return allZDJson;
		}
		
		//转换源数据格式
		function formatData(data) {
			if(data == null || data.length == 0 || data[0] == null) {
				return null;	
			}
			var newData = [];
			for(var i = 0; i < data.length; i++) {
				/* {"value": "1", "title": "李白", "disabled": "", "checked": ""}, */
				var jData = {};
				jData['value']=data[i].id;
				jData['title']=data[i].esIdentifier;
				jData['disabled']="";
				jData['checked']="";
				newData.push(jData);
			}
			return newData;
		}
		
		//转换源数据中选中数据的格式
		function formatDataIsChecked(data) {
			if(data == null || data.length == 0 || data[0] == null) {
				return null;	
			}
			var newData = [];
			for(var i = 0; i < data.length; i++) {
				newData.push(data[i].idtag);
			}
			return newData;
		}
		
		var dataJson;
		
		$(function() {
			var dataFlag = "${dataFlag }";
			if(dataFlag == "NO") {
				layer.msg("请先在结构视图页面设置引用字段！");
				$("#isDispaly").css("display","none");
			}
			dataJson = setData();	
		})
		
		//防止无数据时，穿梭框重载改变页面
		function checkYUANData(dom) {
			var dataFlag = "${dataFlag }";
			if(dataFlag == "NO") {
				$("#"+dom).css("display","none");
			}
		}
		
		//加载穿梭框
	 	layui.use('transfer', function(){
	 		checkYUANData("grid");
	 		checkYUANData("ofForm");
	 		checkYUANData("adSearch");
	 		checkYUANData("ofDdto");
	 		checkYUANData("usingForm");
	 		checkYUANData("usingGrid");
		    var transfer = layui.transfer;
		    //渲染
		    transfer.render({
		        elem: '#gridTransfer',//绑定元素
		        title:['源','目标'],
		        showSearch:true,
				data: formatData(dataJson.allYuanList),
		      	id: 'gridTransfer', //定义索引
		      	value:formatDataIsChecked(dataJson.gridList),
				text: {
				 	none: '无数据', //没有数据时的文案
				 	searchNone: '无匹配数据' //搜索无匹配数据时的文案
				} 
		    });
		    
		    transfer.render({
		        elem: '#ofFormTransfer',//绑定元素
		        title:['源','目标'],
		        showSearch:true,
				data: formatData(dataJson.allYuanList),
		      	id: 'ofFormTransfer', //定义索引
		      	value:formatDataIsChecked(dataJson.ofFormList),
				text: {
				 	none: '无数据', //没有数据时的文案
				 	searchNone: '无匹配数据' //搜索无匹配数据时的文案
				} 
		    });
		    
		    transfer.render({
		        elem: '#adSearchTransfer',//绑定元素
		        title:['源','目标'],
		        showSearch:true,
				data: formatData(dataJson.allYuanList),
		      	id: 'adSearchTransfer', //定义索引
		      	value:formatDataIsChecked(dataJson.adSearchList),
				text: {
				 	none: '无数据', //没有数据时的文案
				 	searchNone: '无匹配数据' //搜索无匹配数据时的文案
				} 
		    });
		    
		    transfer.render({
		        elem: '#ofDdtoTransfer',//绑定元素
		        title:['源','目标'],
		        showSearch:true,
				data: formatData(dataJson.allYuanList),
		      	id: 'ofDdtoTransfer', //定义索引
		      	value:formatDataIsChecked(dataJson.ofDdtoList),
				text: {
				 	none: '无数据', //没有数据时的文案
				 	searchNone: '无匹配数据' //搜索无匹配数据时的文案
				} 
		    });
		    
		    transfer.render({
		        elem: '#usingFormTransfer',//绑定元素
		        title:['源','目标'],
		        showSearch:true,
				data: formatData(dataJson.allYuanList),
		      	id: 'usingFormTransfer', //定义索引
		      	value:formatDataIsChecked(dataJson.usingFormList),
				text: {
				 	none: '无数据', //没有数据时的文案
				 	searchNone: '无匹配数据' //搜索无匹配数据时的文案
				} 
		    });
		    
		    transfer.render({
		        elem: '#usingGridTransfer',//绑定元素
		        title:['源','目标'],
		        showSearch:true,
				data: formatData(dataJson.allYuanList),
		      	id: 'usingGridTransfer', //定义索引
		      	value:formatDataIsChecked(dataJson.usingGridList),
				text: {
				 	none: '无数据', //没有数据时的文案
				 	searchNone: '无匹配数据' //搜索无匹配数据时的文案
				} 
		    });
		    
		    setRightPosition(dataJson,"grid");
		    setRightPosition(dataJson,"ofForm");
		    setRightPosition(dataJson,"adSearch");
		    setRightPosition(dataJson,"ofDdto");
		    setRightPosition(dataJson,"usingForm");
		    setRightPosition(dataJson,"usingGrid");
		});
		
		 
		//穿梭框加载时不会按照设定的顺序来，需要删除重载
		function setRightPosition(json,dom) {
			var index = $("#"+dom+ " ul").eq(1); 
			var dataFlag = dom + "List";
			var list = json[dataFlag];
			var str = "";
			for(var i = 0; i < list.length; i++) {
				var inObj = list[i];
				var tagName = inObj.tagName;
				var htmlStr = $(index).children().children("input[title='"+tagName+"']").parent().prop("outerHTML");
				str += htmlStr;
			}
			$("#"+dom+" ul").eq(1).empty();
			$("#"+dom+" ul").eq(1).append(str);
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
			var input = $(ul).find("input");
			var saveArr = [];
			for(var i = 0; i < input.length; i++) {
				var inJson = {};
				inJson["esorder"] = i+1+"";
				inJson["idstructure"] = structureId;
				inJson["idtag"] = input[i].value;
				saveArr.push(inJson);
			}
			var saveObj = {};
			saveObj["data"] = JSON.stringify(saveArr);
			saveObj["tableFlag"] = dom;
			saveObj["idstructure"] = structureId;
			console.log(saveObj);
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