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
	    	top: 82px;
		  }
		  .upMaxBtn {
		  	position: absolute;
	    	left: 225px;
	    	top: 34px;
		  }
		  .downBtn {
		  	position: absolute;
	    	left: 215px;
	    	top: 319px;
		  }
		  .downMaxBtn {
		  	position: absolute;
	    	left: 215px;
	    	top: 364px;
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
	    	top: 129px;
    	  }
    	  .leftBtn {
    	  	position: absolute;
	    	left: 215px;
	    	top: 176px;
    	  }
    	  .left2Btn {
    	  	position: absolute;
	    	left: 215px;
	    	top: 224px;
    	  }
    	  .doBtn {
    	  	position: absolute;
	    	left: 215px;
	    	top: 272px;
    	  }
	    </style>
	</head>
	<body>
		<div id="isDisplay">
			<input id="structureId" name="structureId" type="hidden" value="${structureId }"/>
			<div id="sortf" class="commonFather">
				<div class="title">选择名称字段</div>
				<div id="abstractTransfer" class="marLef">
					<div class="layui-transfer layui-form layui-border-box" lay-filter="LAY-transfer-1">
						<div class="layui-transfer-box" data-index="0" style="width: 200px; height: 360px;">
							<div class="layui-transfer-header">
								<input type="checkbox" name="layTransferLeftCheckAll" lay-filter="layTransferCheckbox" lay-type="all" lay-skin="primary" title="源">
								<div class="titleOfTransfer" lay-skin="primary">
									<span>源</span>
								</div>
							</div>
							<div class="layui-transfer-search">
								<i class="layui-icon layui-icon-search"></i>
								<input type="input" class="layui-input" onkeyup="searchLike('left')" placeholder="关键词搜索">
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
								<input type="input" class="layui-input" placeholder="关键词搜索" onkeyup="searchLike('right')">
							</div>
							<ul class="layui-transfer-data" style="height: 267px;">
								
							</ul>
						</div>
					</div>
				</div>
				<div class="buttonDiv">
					<button type="button" title="置顶" class="layui-btn layui-btnUpDownmax upMaxBtn" onclick="moveUpMax('sortf')"> 
						<i class="layui-icon">&#xe619;</i>
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" title="上移" class="layui-btn layui-btnUpDown upBtn" onclick="moveUp('sortf')"> 
						<i class="layui-icon">&#xe619;</i>
					</button>
					<button type="button" class="layui-btn layui-btnUpDown rightBtn" onclick="moveToLeft('sortf')">
						<i class="layui-icon layui-icon-prev"></i>
					</button>
					<button type="button" title="正序" class="layui-btn layui-btnUpDown leftBtn" onclick="moveToRight('sortf','ASC')"> 
						正序
					</button>
					<button type="button" title="倒序" class="layui-btn layui-btnUpDown left2Btn" onclick="moveToRight('sortf','DESC')"> 
						倒序
					</button>
					<button type="button" title="自定义" class="layui-btn layui-btnUpDown doBtn" onclick="softByMySelf('sortf')">
						自定义
					</button>
					<button type="button" title="下移" class="layui-btn layui-btnUpDown downBtn" onclick="moveDown('abstract')">
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" title="置底" class="layui-btn layui-btnUpDownmax downMaxBtn" onclick="moveDownMax('sortf')">
						<i class="layui-icon">&#xe61a;</i>
						<i class="layui-icon">&#xe61a;</i>
					</button>
					<button type="button" class="layui-btn layui-btn-normal" onclick="saveChooseData('sortf')">保存</button>
				</div>
			</div>
		</div>
	</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		//复选框选中
		function addSelectedCss(dom) {
			if(!$(dom).hasClass("layui-form-checked")) {
				$(dom).addClass("layui-form-checked");
			} else {
				$(dom).removeClass("layui-form-checked");
			};
			
		}
		
		//模糊搜索
		function searchLike(zy) {
			var input = null;
			var ul = null;
			if(zy == "left") {
				input = $(".layui-transfer-search").find("input").eq(0);
				ul = $("#sortf").find("ul").eq(0);
			} else {
				input = $(".layui-transfer-search").find("input").eq(1);
				ul = $("#sortf").find("ul").eq(1);
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
				var dataFlag = $(indexOne).attr("data-title");
				if(dataFlag == "custom") {
					$(indexOne).remove();  
				} else {
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
		}
		
		//正序倒序右移
		function moveToRight(dom,soft) {
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
				var dataFlag = $(indexOne).attr("data-title");
				var input = $(indexOne).children("input");
				var tagName = $(input).attr("title");
				var tagId = $(input).val();
				if(dataFlag == "custom") {
					var rightIndex = $("#"+dom+" ul" ).eq(1).find("input[value='"+ tagId +"']").parent();
					if(rightIndex.length > 0) {
						layer.msg("自定义数据无法执行正序倒序操作");
						return;
					} else {
						var str = "<li><input type='checkbox' name='layTransferRightCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ tagName +"' value='"+ tagId +"'>";
						str += "<div class='layui-unselect layui-form-checkbox' onclick='addSelectedCss(this)' lay-skin='primary'><span>"+ tagName +"|"+ soft +"</span>"
						str += "<i class='layui-icon layui-icon-ok'></i></div></li>";
						$("#"+dom+" ul" ).eq(1).append(str);
						$(indexOne).remove();
					}
				} else {
					var str = "<li><input type='checkbox' name='layTransferRightCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ tagName +"' value='"+ tagId +"'>";
					str += "<div class='layui-unselect layui-form-checkbox' onclick='addSelectedCss(this)' lay-skin='primary'><span>"+ tagName +"|"+ soft +"</span>"
					str += "<i class='layui-icon layui-icon-ok'></i></div></li>";
					$("#"+dom+" ul" ).eq(1).append(str);
					$(indexOne).remove();
				}
			}     
		}
		
		//自定义排序
		function softByMySelf(dom) {
			//找到选中的li
			var indexLi = $("#"+dom+" ul" ).eq(0).find('.layui-form-checked').parent(); 
			if(typeof(indexLi[0]) == "undefined") {
				layer.msg("请选中后，再进行自定义排序");
				return;
			}
			if(indexLi.length > 1) {
				layer.msg("只能对一条源字段进行自定义排序");
				return;
			}
			var tagName = $(indexLi).find("input").attr("title");
			var tagId = $(indexLi).find("input").val();
			layer.open({
                type: 1,
                title: '自定义排序',
                area:['470px', '230px'],
                btn: ['确定','取消'],
                content: "<div>"
                		+"<div class='layui-form-item' style='margin-top:20px'>"
					    +"<label class='layui-form-label'>排序字段</label>"
					    +"<div class='layui-input-block' style='width:320px'>"
					    +"<input type='text' placeholder='"+ tagName +"' readonly='readonly' autocomplete='off' class='layui-input'>"
					    +"</div>"
					 	+ "</div>"
					 	+"<div class='layui-form-item' style='margin-top:20px'>"
					    +"<label class='layui-form-label'>关键字</label>"
					    +"<div class='layui-input-block' style='width:320px'>"
					    +"<input type='text' id='customVal' placeholder='必填' autocomplete='off' class='layui-input'>"
					    +"</div>"
					 	+ "</div>"
						+"</div>",
                yes: function (index, layero) {
                    //按钮【按钮一】的回调
                    var backreason = layero.find("input[id='customVal']");// 获取输入的退回原因
                    var inputVal = $(backreason).val();
                    if(isEmpty(inputVal)) {
                    	layer.msg("请填写自定义关键字信息！");
                    	return;
                    }
                    if(inputVal.indexOf(",") != -1) {
                    	layer.msg("请勿在关键字信息中输入逗号！");
                    	return;
                    }
                    if(inputVal.indexOf(" ") != -1) {
                    	layer.msg("请勿在关键字信息中输入空格！");
                    	return;
                    }
                    var rightP = $("#"+dom).find("ul").eq(1).find("p");
        			if($(rightP).length > 0) {
        				$(rightP).remove();
        			}
                    $(indexLi).attr("data-title","custom");
                    var str = "<li><input type='checkbox' name='layTransferRightCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ tagName +"' value='"+ tagId +"'>";
					str += "<div class='layui-unselect layui-form-checkbox' onclick='addSelectedCss(this)' lay-skin='primary'><span>"+ tagName +"|"+ inputVal +"</span>"
					str += "<i class='layui-icon layui-icon-ok'></i></div></li>";
					$("#"+dom+" ul" ).eq(1).append(str);
					layer.close(index);
                }
            });
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
			var sortStr = "";
			for(var i = 0; i < li.length; i++) {
				var str = "";
				str += $(li[i]).find("input").val();
				str += " ";
				var span = $(li[i]).find("span").text();
				var spanArr = span.split("|");
				str += spanArr[1];
				sortStr += str + ",";
			}
			sortStr = sortStr.substring(0, sortStr.length-1);
			var saveObj = {};
			saveObj["data"] = sortStr;
			saveObj["idstructure"] = structureId;
			if(isEmpty(sortStr)) {
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
			//obj = 
			$.ajax({
				url:"${ctx}/dataTemp_savePxData.do",
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
		
		//将数据展示在页面上
		function setDataShowInPage() {
			var allYuanList = JSON.parse('${allYuanList}');
			var abstractList = JSON.parse('${abstractList}');
			var leftUl = $("#sortf").find("ul").eq(0);
			var rightUl = $("#sortf").find("ul").eq(1);
			//设置源数据
			if(allYuanList == null || typeof(allYuanList) == 'undefined' || allYuanList[0] == null || typeof(allYuanList[0]) == 'undefined') {
				$(leftUl).append("<p class='layui-none'>无数据</p>");
			} else {
				for(var i = 0; i < allYuanList.length; i++) {
					var str = "";
					var obj = allYuanList[i];
					var isCustom = obj.isCustom;
					if(isEmpty(isCustom)) {
						str += "<li>"
					} else {
						str += "<li data-title='custom'>";
					}
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
					var ruleFlag = obj.ruleFlag;
					if(ruleFlag == "SYSTEM") {
						str += "<li data-title='system'>"
					} else {
						str += "<li data-title='custom'>";
					}
					str +="<input type='checkbox' name='layTransferLeftCheck' lay-skin='primary' lay-filter='layTransferCheckbox' title='"+ obj.tagName +"' value='"+ obj.idtag +"'>";
					str +="<div class='layui-unselect layui-form-checkbox' onclick='addSelectedCss(this)' lay-skin='primary'>";
					str +="<span>"+ obj.tagName +"|"+ obj.ruleOneSort +"</span><i class='layui-icon layui-icon-ok'></i></div></li>";
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