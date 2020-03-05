<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <!--表单样式-->
    <link href="${ctx}/assets-common/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/assets-common/css/timeline.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/assets-common/css/animate.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/assets-common/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<link href="${ctx }/flow/themes/default/easyui.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx }/flow/css/flowPath_show.css" type="text/css" rel="stylesheet" />    
	<link href="${ctx}/assets-common/css/font-awesome.min.css" rel="stylesheet"/>
	<link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" media="screen"/>
    <script src="${ctx}/assets-common/js/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${ctx}/assets-common/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${ctx }/pdfocx/json2.js?x=2022" type="text/javascript"></script>
	<style type="text/css">
		.bodyclass{
			position: fixed;
			top: 0;
			bottom: 15px;
			left: 0;
			right: 0;
		}
		
		.ls{
			height: 60%;
			overflow: auto;
		}
	</style>

</head>
<body class="bodyclass" style="background-color:#989898">
	<div style="width:1024px;margin:0 auto;background-color:#FFF;overflow:auto;height:100%;border-top:1px solid rgb(226, 234, 244);">
	<div region="center" title="" id="contextBody" class="mapContext ls">
		<canvas id="canvas" width="1610" height="1010" style="display:block;"></canvas>
	</div>
	
	<div style="height:40%;overflow:auto">
		<div class="tw-news-btns" style="float: right;margin-right:20px;margin-top: 10px;">
			<span class="tw-btn-primary" onclick="printPage();"><i class="tw-icon-print"></i> 打 印</span>
		</div>
		<div class="timeline-container timeline-style" id="wf-course" style="width: 780px">
		    <div class="tw-layout">
		        <div class="tw-list-wrap">
		            <table class="tw-fixtable" layoutH="140">
		                <thead>
		                    <tr>
		                        <th width="10%">步骤</th>
		                        <th width="10%">办理人员</th>
		                        <th width="18%">收到时间</th>
		                        <th width="18%">处理时间</th>
		                        <th width="15%">办理意见</th>
		                        <th width="10%">提交人员</th>
		                        <th width="9%">备注</th>
		                    </tr>
		                </thead>
		                <tbody>
			            	<c:forEach items="${processList}" var="process">
				                    <tr id="${process.wfProcessUid}Tr">
				                    	<td align="left">
				                    		${process.nodeName}
				                    		<c:if test="${process.isHaveChild == '1'}">
				                    			<span id="${process.wfProcessUid}Href">
					                    			<a href="#" onclick="openChildWf('${process.wfProcessUid}','${process.wfInstanceUid}');">
					                    				<font color="red">
					                    					<span style="font-size:25px;vertical-align:middle;line-height:24px;">+</span>
					                    				</font>
					                    			</a>
				                    			</span>
				                    		</c:if>
				                    	</td>
				                        <td align="left">${process.userName}</td>
				                        <td align="left">${process.str_applyTime}</td>
				                        <td align="left">${process.str_finshTime}</td>
				                        <td align="left">
				                        	<c:if test="${process.commentText == null || process.commentText == ''}">[未签署]</c:if>
				                        	<c:if test="${process.commentText != null && process.commentText != ''}">${process.commentText}</c:if>
				                        </td>
				                        <td align="left">${process.fromUserName}</td>
				                        <td align="left">
				                        	<c:if test="${process.isReturnStep eq '1' }">退回过程</c:if>
				                        	<c:if test="${process.isReturnStep != '1' }">
							            		<c:if test="${process.isBack eq '2' }">废弃过程</c:if>
							            	</c:if>
				                        </td>
				                    </tr>
		                    </c:forEach>
		                </tbody>
		            </table>
		        </div>
		    </div>
		</div>
		<div class="timeline-container timeline-style"  id="printDiv" style="width: 780px;display: none;">
		    <div class="tw-layout">
		        <div class="tw-list-wrap">
		            <table class="tw-fixtable" layoutH="140">
		                <thead>
		                    <tr>
		                        <th width="10%">步骤</th>
		                        <th width="10%">办理人员</th>
		                        <th width="18%">收到时间</th>
		                        <th width="18%">处理时间</th>
		                        <th width="15%">办理意见</th>
		                        <th width="10%">提交人员</th>
		                        <th width="9%">备注</th>
		                    </tr>
		                </thead>
		                <tbody>
			            	<c:forEach items="${processList}" var="process">
				                    <tr>
				                    	<td align="left">
				                    		${process.nodeName}
				                    		<c:if test="${process.isHaveChild == '1'}">
				                    			<a href="#" >
				                    				<font color="red">
				                    					<span style="font-size:25px;vertical-align:middle;line-height:24px;">+</span>
				                    				</font>
				                    			</a>
				                    		</c:if>
				                    	</td>
				                        <td align="left">${process.userName}</td>
				                        <td align="left">${fn:substring(process.applyTime,0,16)}</td>
				                        <td align="left">${fn:substring(process.finshTime,0,16)}</td>
				                        <td align="left">
				                        	<c:if test="${process.commentText == null || process.commentText == ''}">[未签署]</c:if>
				                        	<c:if test="${process.commentText != null && process.commentText != ''}">${process.commentText}</c:if>
				                        </td>
				                        <td align="left">${process.fromUserName}</td>
				                        <td align="left">
				                        	<c:if test="${process.isReturnStep eq '1' }">退回过程</c:if>
				                        	<c:if test="${process.isReturnStep != '1' }">
							            		<c:if test="${process.isBack eq '2' }">废弃过程</c:if>
							            	</c:if>
				                        </td>
				                    </tr>
		                    </c:forEach>
		                </tbody>
		            </table>
		        </div>
		    </div>
		</div>
	</div>
	</div>

	<script>
		$(".timeline-minus").click(function(){ 
		    var sunLength = $(this).closest('.timeline-item').find('.widget-box .widget-main2');
			var item = $(this).closest('.timeline-item');
		    sunLength.each(function(index){
			    if(index>0){
				    if(sunLength.is(":hidden")){
				        sunLength.eq(index).fadeIn("slow");	
		                $('.timeline-minus').removeClass("icon-plus-sign"); 				
		                $('.timeline-minus').addClass("icon-minus-sign");	
						var nHeight = item.height()+10;
		                item.find('.timeline-arrow').css('height',nHeight);					
					}else{
				        sunLength.eq(index).fadeOut("fast");
		                $('.timeline-minus').removeClass("icon-minus-sign"); 				
		                $('.timeline-minus').addClass("icon-plus-sign"); 
						item.find('.timeline-arrow').css('height','110px');			
					}
				}
			});
		});
		
		if(!$('i.timeline-line').hasClass('timeline-end')){
		    $('i.timeline-indicator:last').addClass('timeline-wait');
			$('i.timeline-indicator:last').html('待');
		}
		
		$('.timeline-items .timeline-item').each(function(index){
		    var $this = $(this);
		    var _height = $this.height()+10;
			var _time = (index+1)*1000;
			var _delayTime = index; 
			var _delayTime2 = index+0.4;
			var _delayTime3 = index+0.6;
			$this.find('.timeline-indicator').addClass('animated fadeInDown').attr('style','-webkit-animation-delay:'+_delayTime+'s; animation-delay:'+_delayTime+'s');
			$this.find('.timeline-info').addClass('animated fadeIn').attr('style','-webkit-animation-delay:'+_delayTime2+'s; animation-delay:'+_delayTime2+'s');
			$this.find('.widget-box').addClass('animated fadeIn').attr('style','-webkit-animation-delay:'+_delayTime3+'s; animation-delay:'+_delayTime3+'s');	
			setTimeout(function(){
				$this.find(".timeline-arrow").animate({height:_height});
			},_time);  
		});
		
	</script>
	<script src="${ctx }/flow/js/jquery-1.6.min.js" type="text/javascript"></script>
	<script src="${ctx }/flow/js/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx }/flow/js/xml2json.min.js?x=2022" type="text/javascript"></script>
	<script>
		try{
		var processes = '${processes}';
		var xml = '${xml}';
		var x2js = new X2JS();
		var json = x2js.xml_str2json(xml);
		
		var jsonData = JSON2.parse(processes);
		var nodeInfoArr = json.flow.flow_seq;
        var lineInfoArr = json.flow.flow_line;
		var canvas,context;
        var lineClickRangeArr = [];
		var lineParaArr = [];
		//console.log(jsonData);
		
		console.log(nodeInfoArr)
		var maxTop = 0;
		var maxLeft = 0;
		
		for(var i=0;i<nodeInfoArr.length;i++){
			if(Number(nodeInfoArr[i].top) > maxTop){
				maxTop = Number(nodeInfoArr[i].top)
			}
			if(Number(nodeInfoArr[i].left) > maxLeft){
				maxLeft = Number(nodeInfoArr[i].left)
			}
		}
		
		console.log(maxLeft,maxTop)
		
		maxLeft = maxLeft + 100;
		maxTop = maxTop + 100;
		
		/*var wHeight = $(window).height()
		var wWidth = $(window).width()
		
		var canvasWidth = 1024 > maxLeft  ? 1024 : maxLeft
		var canvasHeight = wHeight > maxTop ? wHeight : maxTop*/
		
		var canvasDom = document.getElementById('canvas')

		
		
		canvasDom.width = maxLeft
		canvasDom.height = maxTop
		
		
		
		
        $(document).ready(function(){
            canvas=document.getElementById("canvas");
            context=canvas.getContext("2d");

            for(var i=0;i<jsonData.length;i++){
                var item = getNodeById(jsonData[i].fromNodeId);
                if(item){
                    jsonData[i].fromModeModule = item.id;
                }
            }
			var modelId = '';
            for(var i=0;i<nodeInfoArr.length;i++){
				var item = nodeInfoArr[i];
				var isDisplay = item.node_isdisplay;
				if(null != isDisplay && '' != isDisplay && isDisplay == '1'){
					modelId += item.id + ",";
					continue;
				}
				if(jsonData.length > 0){
					//console.log(item.id,jsonData[jsonData.length-1].nodemodel);
					if(item.id == jsonData[jsonData.length-1].nodemodel){
						drawShineNode(item.top,item.left,item.width,item.height,item.nodetype,item.node_name);
					} else {
						drawNode(item.top,item.left,item.width,item.height,item.nodetype,item.node_name,checkIsGrayNode(item));
					}
				} else {
					drawNode(item.top,item.left,item.width,item.height,item.nodetype,item.node_name,checkIsGrayNode(item));
				}
            }

            function checkIsGrayNode(item){
				for(var i=0;i<jsonData.length;i++){
                    if(jsonData[i].fromModeModule == item.id){
                        return false;
                    }
                    if(jsonData[i].nodemodel == item.id){
                        return false;
                    }
                }
                return true;
            }

            for(var i=0;i<lineInfoArr.length;i++){
                var item = lineInfoArr[i];
            	if(modelId.indexOf(item.wBaseMode) != -1 || modelId.indexOf(item.xBaseMode) != -1){
            		continue;
            	}
                var startNode = findNode(item.wBaseMode);
                var endNode = findNode(item.xBaseMode);
                lineClickRangeArr.push(null);
                if(startNode && endNode){
                    var lineInfo = findLineInfo(item.wBaseMode,item.xBaseMode);
                    var linePosInfo = getLinePosInfoArr(startNode,endNode,lineInfo);
                    //console.log(linePosInfo)
                    item.lineInfo = lineInfo;
                    if(lineInfo.length > 0){

                    }
                    lineClickRangeArr[i] = getClickRange(linePosInfo[0],linePosInfo[1],linePosInfo[2],linePosInfo[3]);
                    drawLine(linePosInfo[0],linePosInfo[1],linePosInfo[2],linePosInfo[3],linePosInfo[4],linePosInfo[5],lineInfo);
                }
            }

            $('#canvas').click(function(e){
                var mouseX = e.offsetX;
                var mouseY = e.offsetY;
                for(var i=0;i<lineClickRangeArr.length;i++){
                    var rangeItem = lineClickRangeArr[i];
                    var info = lineInfoArr[i];
                    var instanceId = window.location.href.split('?')[1].split('=')[1];
                    if(rangeItem && info.lineInfo.length > 0){
                    	var fromNode = findNode(info.lineInfo[0].fromModeModule);
                    	var toNode = findNode(info.lineInfo[0].nodemodel);
                    	
                        if(mouseX > rangeItem[0] && mouseX<rangeItem[2] && mouseY > rangeItem[1] && mouseY < rangeItem[3]){
                            layer.open({
                            			type:2,
                            			area:['700px','450px'],
                            			fixed:false,
                            			content:'${ctx}/table_showInfo.do?instanceId='+instanceId+'&fromNodeId='+fromNode.node_id+'&nodeId='+toNode.node_id,
                            			title:'详情'
                            		})
                            return;
                        } 
                    }
                }
            });

            $('#canvas').mousemove(function(e){
                var mouseX = e.offsetX;
                var mouseY = e.offsetY;
                var showCursor = false;;
                for(var i=0;i<lineClickRangeArr.length;i++){
                    var rangeItem = lineClickRangeArr[i];
                    var info = lineInfoArr[i];
                    if(rangeItem && info.lineInfo.length > 0){
                        if(mouseX > rangeItem[0] && mouseX<rangeItem[2] && mouseY > rangeItem[1] && mouseY < rangeItem[3]){
                            showCursor = true;
                            break;
                        } 
                    }
                }
                if(showCursor){
                    $("#canvas").css({cursor:'pointer'})
                } else {
                    $("#canvas").css({cursor:''})
                }

            })
        })
		}catch (e) {
		}
        function getLinePosInfoArr(startNode,endNode,lineInfos){//获取线条的位置信息
            if(Math.abs(Number(startNode.left)-Number(endNode.left)) < Math.abs(Number(startNode.top) - Number(endNode.top))){
                var topItem = (Number(startNode.top) > Number(endNode.top) ? startNode : endNode);
                var bottomItem = (startNode == topItem ? endNode : startNode);
                var widthArr = [];
                var tisdo=-1;
                for(var i=0;i<lineInfos.length;i++){
                    var fromId = lineInfos[i].fromModeModule;
                    var toId = lineInfos[i].nodemodel;
                    var isdo = lineInfos[i].userInfo[0].isdo;
                    if(topItem.id == toId){
                        if(getIndexOf(widthArr,'t') < 0){
                            widthArr.push('t');
                        }
                    } else if(bottomItem.id == toId){
                        if(getIndexOf(widthArr,'b') < 0){
                            widthArr.push('b');
                        }
                    }
                    if(tisdo != '1'){
						tisdo = isdo;
					}
                }
				if(lineInfos.length == 0){
					if(topItem == startNode){
						if(getIndexOf(widthArr,'t')<0){
                            widthArr.push('t');
                        }
					} else {
						if(getIndexOf(widthArr,'b')<0){
                            widthArr.push('b');
                        }
					}
				}
                return [Number(topItem.left)+25,Number(topItem.top),Number(bottomItem.left)+25,Number(bottomItem.top)+50,widthArr,tisdo];
            } else {
                var leftItem = (Number(startNode.left) < Number(endNode.left) ? startNode : endNode);
                var rightItem = (leftItem == startNode ? endNode : startNode);
                var widthArr = [];
                lineInfos[i]
                for(var i=0;i<lineInfos.length;i++){
                    var fromId = lineInfos[i].fromModeModule;
                    var toId = lineInfos[i].nodemodel;
                    var isdo = lineInfos[i].userInfo[0].isdo;
                    if(leftItem.id == toId){
                        if(getIndexOf(widthArr,'l')<0){
                            widthArr.push('l');
                        }
                    } else if(rightItem.id == toId){
                        if(getIndexOf(widthArr,'r')<0){
                            widthArr.push('r');
                        }
                    }
                    if(tisdo != '1'){
                        tisdo = isdo;
                    }
                }
				if(lineInfos.length == 0){
					if(leftItem == startNode){
						if(getIndexOf(widthArr,'l')<0){
                            widthArr.push('l');
                        }
					} else {
						if(getIndexOf(widthArr,'r')<0){
                            widthArr.push('r');
                        }
					}
				}
                return [Number(leftItem.left)+50,Number(leftItem.top)+25,Number(rightItem.left),Number(rightItem.top)+25,widthArr,isdo];
            }

            function getIndexOf(arr,item){
                for(var i=0;i<arr.length;i++){
                    if(arr[i] == item){
                        return i;
                    }
                }
                return -1;
            }
        }

        function getTrianglePosInfo(startX,startY,endX,endY,arrowArr){//获取箭头的类型及位置信息
			//if(arrowArr.indexOf('r') >= 0 || arrowArr.indexOf('l') >= 0)
            //if(Math.abs(Number(startX)-Number(endX)) > Math.abs(Number(startY)-Number(endY))){
			if(arrowArr.indexOf('r') >= 0 || arrowArr.indexOf('l') >= 0){
                var left = startX < endX ? [startX-2,startY-3.5] : [endX-2,endY-3.5];
                var right = startX < endX ? [endX-8,endY-3.5] : [startX-8,startY-3.5];
                return {left:left,right:right};
            } else {
                var top = startY < endY ? [startX-5,startY-2] : [endX-5,endY-2];
                var bottom = startY < endY ? [endX-5,endY-5] : [startX-5,startY-5];
                return {top:top,bottom:bottom};
            }
        }
		
		
		function checkIsOcpied(tempParaArr){
            for(var i=0;i<tempParaArr.length;i++){
                var startX1 = tempParaArr[i].startPoint.x;
                var endX1 = tempParaArr[i].endPoint.x;
                var startY1 = tempParaArr[i].startPoint.y;
                var endY1 = tempParaArr[i].endPoint.y;

                for(var j=0;j<lineParaArr.length;j++){
                    var startX2 = lineParaArr[j].startPoint.x;
                    var endX2 = lineParaArr[j].endPoint.x;
                    var startY2 = lineParaArr[j].startPoint.y;
                    var endY2 = lineParaArr[j].endPoint.y; 

                   if(startX1 == startX2 &&  startX1 == endX1 && startX1 == endX2){
                        if((startY1 > startY2 && startY1 < endY2) 
                            || (startY1 > endY2 && startY1 < startY2)
                            || (endY1 > startY2 && endY1 < endX2) 
                            || (endY1 > endY2 && endY1 < startY2)){
                            return true;
                        }
                    } else if(startY1 == startY2 && startY1 == endY1 && startY1 == endY2){
                        if((startX1 > startX2 && startX1 < endX2)
                            || (startX1 > endX2 && startX1 < startX2)
                            || (endX1 > startX2 && endX1 < endX2)
                            || (endX1 > endX2 && endX1 < startX2)){
                                return true;
                            }

                    }
                }
            }
            return false;
        }

        function drawLine(startX,startY,endX,endY,arrow,isdo,lineInfo){//画线
            //context.restore();
            var strokeColor = getColor(lineInfo);
            context.strokeStyle  = strokeColor;
            context.lineWidth = 2;
            var tempParaArr = [];
            //if(Math.abs(startX-endX) > Math.abs(startY - endY)){
			// if(arrow.indexOf('r') >= 0 || arrow.indexOf('l') >= 0){
            //     tempParaArr.push({
            //         startPoint:{x:startX,y:startY},
            //         endPoint:{x:(startX+endX)/2,y:startY}
            //     })
            //     tempParaArr.push({
            //         startPoint:{x:(startX+endX)/2,y:startY},
            //         endPoint:{x:(startX+endX)/2,y:endY}
            //     })
            //     tempParaArr.push({
            //         startPoint:{x:(startX+endX)/2,y:endY},
            //         endPoint:{x:endX,y:endY}
            //     })
            //     var isOcupied = checkIsOcpied(tempParaArr);
            //     console.log(isOcupied)
            //     lineParaArr = lineParaArr.concat(tempParaArr);
            //     if(isOcupied){
            //         if(strokeColor == "#AAAAAA"){
            //             strokeColor = "#666666"
            //         } else {
            //             strokeColor = "#FF0000"
            //         }
            //     }
            //     context.strokeStyle  = strokeColor;
            //     context.beginPath();
            //     context.moveTo(startX,startY);
            // 	context.lineTo((startX+endX)/2,startY);
            // 	context.lineTo((startX+endX)/2,endY);
            // } else {
            //     tempParaArr.push({
            //         startPoint:{x:startX,y:startY},
            //         endPoint:{x:startX,y:(startY+endY)/2}
            //     })
            //     tempParaArr.push({
            //         startPoint:{x:startX,y:(startY+endY)/2},
            //         endPoint:{x:endX,y:(startY+endY)/2}
            //     })
            //     tempParaArr.push({
            //         startPoint:{x:endX,y:(startY+endY)/2},
            //         endPoint:{x:endX,y:endY}
            //     })
            //     var isOcupied = checkIsOcpied(tempParaArr);
            //     console.log(isOcupied)
            //     lineParaArr = lineParaArr.concat(tempParaArr);

            //     if(isOcupied){
            //         if(strokeColor == "#AAAAAA"){
            //             strokeColor = "#666666"
            //         } else {
            //             strokeColor = "#FF0000"
            //         }
            //     }
            //     context.strokeStyle  = strokeColor;
            //     context.beginPath();
            //     context.moveTo(startX,startY);
            //     context.lineTo(startX,(startY+endY)/2);
            // 	context.lineTo(endX,(startY+endY)/2);
            // }
			context.beginPath();
            context.moveTo(startX,startY);
            context.lineTo(endX,endY);
			context.closePath();
            context.stroke();
            
            //context.restore();
			
			//console.log(lineParaArr);
            
            drawTriangle(startX,startY,endX,endY,arrow);
            /* if(lineInfo.length > 0){
                var lineInfoNum = lineInfo.length;
                var tx,ty;
                if(lineInfoNum > 1){
                    if(Math.abs(startX-endX) > Math.abs(startY-endY)){
                        ty = (startY < endY ? startY : endY) - 10;
                        tx = (startX + endX)/2; 
                    } else {
                        tx = (startX < endX ? endX : startX) + 10;
                        ty = (startY + endY)/2; 
                    }
                    //context.save();
                    context.font="12px Georgia";
                    context.fillStyle="#333";
                    context.textAlign="center";
                    context.textBaseline = "middle";
                    context.fillText(lineInfoNum,tx,ty);
                    //context.restore();
                }
            } */

            function getColor(isdo){     
            	if(isdo.length > 0){
            		return '#FF0000'
            	}
                return '#AAAAAA';
            }
        }


        function drawTriangle(startX,startY,endX,endY,arrow){//画箭头
            var trianglePosInfo = getTrianglePosInfo(startX,startY,endX,endY,arrow);

            // var length = Math.sqrt(Math.pow((endY-startY),2) + Math.pow((endX-startX),2));
            // var sinN = Math.abs((endY-startY)/length)
            // var cosN = Math.abs((endX-startX)/length)
            // var sinX = 4 * sinN;
            // var cosY = 4 * cosN;
            // var sinX2 = 8 * sinN;
            // var cosY2 = 8 * cosN;
            // var isYFu = startY < endY;

            for(var i=0;i<arrow.length;i++){
                if(arrow[i] == 'r' && trianglePosInfo.right){
                    draw(startX,startY,endX,endY,'r');
                    // loadTriangle(trianglePosInfo.right[0],trianglePosInfo.right[1],'${ctx }/flow/images/strawberry/triangle_right.png');
                } else if(arrow[i] == 'l' && trianglePosInfo.left){
                    draw(startX,startY,endX,endY,'l');
                    // loadTriangle(trianglePosInfo.left[0],trianglePosInfo.left[1],'${ctx }/flow/images/strawberry/triangle_left.png');
                } else if(arrow[i] == 't' && trianglePosInfo.top){
                    draw(startX,startY,endX,endY,'t');
                    // loadTriangle(trianglePosInfo.top[0],trianglePosInfo.top[1],'${ctx }/flow/images/strawberry/triangle_top.png');
                } else if(arrow[i] == 'b' && trianglePosInfo.bottom){
                    draw(startX,startY,endX,endY,'b');
                    // loadTriangle(trianglePosInfo.bottom[0],trianglePosInfo.bottom[1],'${ctx }/flow/images/strawberry/triangle_bottom.png');
                }
            }
            if(arrow.length == 0){
                if(trianglePosInfo.right){
                    draw(startX,startY,endX,endY,'r');
                    // loadTriangle(trianglePosInfo.right[0],trianglePosInfo.right[1],'${ctx }/flow/images/strawberry/triangle_right_gray.png');
                }
                if(trianglePosInfo.left){
                    draw(startX,startY,endX,endY,'l');
                    // loadTriangle(trianglePosInfo.left[0],trianglePosInfo.left[1],'${ctx }/flow/images/strawberry/triangle_left_gray.png');
                }
                if(trianglePosInfo.top){
                    draw(startX,startY,endX,endY,'t');
                    // loadTriangle(trianglePosInfo.top[0],trianglePosInfo.top[1],'${ctx }/flow/images/strawberry/triangle_top_gray.png');
                }
                if(trianglePosInfo.bottom){
                    draw(startX,startY,endX,endY,'b');
                    // loadTriangle(trianglePosInfo.bottom[0],trianglePosInfo.bottom[1],'${ctx }/flow/images/strawberry/triangle_bottom_gray.png');
                }
            }

            function draw(startX,startY,endX,endY,direction){
                var length = Math.sqrt(Math.pow((endY-startY),2) + Math.pow((endX-startX),2));
                var sinN = Math.abs((endY-startY)/length)
                var cosN = Math.abs((endX-startX)/length)
                var sinX = 4 * sinN;
                var cosY = 4 * cosN;
                var sinX2 = 8 * sinN;
                var cosY2 = 8 * cosN;
                var isYFu = startY < endY;

                if(direction == 'r' || direction == 'b'){
                    //context.save();
                    context.beginPath();
                    //context.strokeColor = "#3E7267";
                    context.fillStyle = "#3E7267";
                    context.moveTo(endX,endY);
                    context.lineTo(endX-(isYFu ? -sinX : +sinX),endY-cosY);
                    context.lineTo(endX+cosY2,endY-(isYFu ? -sinX2 : sinX2));
                    context.lineTo(endX+(isYFu ? -sinX : sinX),endY+cosY);
                    context.lineTo(endX,endY);
					context.closePath();
                    context.fill();
					
                    context.restore();
                } else if(direction == 'l' || direction == 't'){
                    //context.save();
                    context.beginPath();
                    context.fillStyle = "#3E7267";
                    context.moveTo(startX,startY);
                    context.lineTo(startX+(isYFu ? sinX : -sinX),startY-cosY);
                    context.lineTo(startX-cosY2,startY-(isYFu ? sinX2 : -sinX2));
                    context.lineTo(startX-(isYFu ? sinX : -sinX),startY+cosY);
                    context.lineTo(startX,startY);
					context.closePath();
                    context.fill();
					
                    //context.restore();
                }
            }

            function loadTriangle(x,y,src){
                var img = new Image();
                img.onload = function(){
                    context.drawImage(img,x,y,10,7);
                    context.restore();
                }
                img.src = src;
            }
        }

        function getClickRange(startX,startY,endX,endY){//设置线条的点击范围
            var tstartX = startX < endX ? startX : endX;
            var tendX = startX < endX ? endX : startX;
            var tstartY = startY < endY ? startY : endY;
            var tendY = startY < endY ? endY : startY;
            if(Math.abs(Number(tstartX)-Number(tendX)) < Math.abs(Number(tstartY)-Number(tendY))){
                var dis = Math.abs(Number(tstartX)-Number(tendX));
                dis = dis < 10 ? (10-dis) : 0;
                tstartX = tstartX - dis/2;
                tendX = tendX + dis/2;
                return [tstartX,tstartY,tendX,tendY]
            } else {
                var dis = Math.abs(Number(startY)-Number(endY));
                dis = dis<10 ? (10-dis):0;
                tstartY = tstartY - dis/2;
                tendY = tendY + dis/2;
                return [tstartX,tstartY,tendX,tendY];
            }
        }

        function findNode(id){//根据id查找节点
            for(var i=0;i<nodeInfoArr.length;i++){
                if(nodeInfoArr[i].id == id){
                    return nodeInfoArr[i];
                }
            }
            return null;
        }

        function getNodeById(id){
        	if(id == "first"){
        		for(var i=0;i<nodeInfoArr.length;i++){
                    if(nodeInfoArr[i].nodetype == 'start'){
                        return nodeInfoArr[i];
                    }
                }
        	}
        	if(id == "end"){
        		for(var i=0;i<nodeInfoArr.length;i++){
                    if(nodeInfoArr[i].nodetype == 'end'){
                        return nodeInfoArr[i];
                    }
                }
        	}
            for(var i=0;i<nodeInfoArr.length;i++){
                if(nodeInfoArr[i].node_id == id){
                    return nodeInfoArr[i];
                }
            }
            return null;
        }

        function findLineInfo(node1,node2){
            var arr = [];
            for(var i=0;i<jsonData.length;i++){
                var item = jsonData[i];
                if((item.fromModeModule == node1 && item.nodemodel == node2) ||(item.fromModeModule == node2 && item.nodemodel == node1)){
                    arr.push(item);
                }
            }
            return arr;
        }

        function drawNode(top,left,width,height,type,name,isgray){//画节点
            var img = new Image();
            img.onload = function(){
                context.drawImage(img,left,top,width,height);
                context.restore();
            }
            var src = '';
            if(type == 'end'){
                if(isgray){
                    src = '${ctx }/flow/images/strawberry/app_end-gray.png';
                } else {
                    src = '${ctx }/flow/images/strawberry/app_end.png'
                }
            } else if(type == 'start'){
                src = '${ctx }/flow/images/strawberry/app_start.png';
            } else {
                if(isgray){
                    src = '${ctx }/flow/images/strawberry/app_baseMode5-gray.png'
                } else {
                    src = '${ctx }/flow/images/strawberry/app_baseMode5.png'
                }
            }
            img.src = src;
            if(name){
                context.font="12px Georgia";
                context.fillStyle="#333";
                context.textAlign="center";
                context.textBaseline = "middle";
                context.fillText(name,Number(left)+25,Number(top)+55);
            }
        }

        function drawShineNode(top,left,width,height,type,name){
            var showShadow = true;
            var img = null;
            setInterval(draw,300);

            function draw(){
                if(!img){
                    img = new Image();
                    img.onload = function(){
                        context.drawImage(img,left,top,width,height);
                        context.restore();
                    }
                    img.src = type == 'end' ? '${ctx }/flow/images/strawberry/app_end.png' : (type == 'start' ? '${ctx }/flow/images/strawberry/app_start.png' : '${ctx }/flow/images/strawberry/app_baseMode5.png');
                } else {
                    context.drawImage(img,left,top,width,height);
                    context.restore();
                }
                if(showShadow){
                    var tLeft = Number(left);
                    var tTop = Number(top);
                    context.restore();
                    context.beginPath();
                    context.moveTo(tLeft+6,tTop+6);
                    context.lineTo(tLeft+44,tTop+6);
                    context.lineTo(tLeft+44,tTop+44);
                    context.lineTo(tLeft+6,tTop+44);
                    context.lineTo(tLeft+6,tTop+6);
                    context.closePath();
                    context.strokeStyle = '#FF0000';
                    context.lineWidth = 3;
                    context.stroke();
                    context.restore();
                }

                showShadow = !showShadow;
            }

            if(name){
                context.font="12px Georgia";
                context.fillStyle="#333";
                context.textAlign="center";
                context.textBaseline = "middle";
                context.fillText(name,Number(left)+25,Number(top)+55);
            }
        }
		
		// Changes XML to JSON
		function xmlToJson(xml) {

		  // Create the return object
		  var obj = {};

		  if (xml.nodeType == 1) { // element
		    // do attributes
		    if (xml.attributes.length > 0) {
		      obj["@attributes"] = {};
		      for (var j = 0; j < xml.attributes.length; j++) {
		        var attribute = xml.attributes.item(j);
		        obj["@attributes"][attribute.nodeName] = attribute.nodeValue;
		      }
		    }
		  } else if (xml.nodeType == 3) { // text
		    obj = xml.nodeValue;
		  }

		  // do children
		  if (xml.hasChildNodes()) {
		    for (var i = 0; i < xml.childNodes.length; i++) {
		      var item = xml.childNodes.item(i);
		      var nodeName = item.nodeName;
		      if (typeof (obj[nodeName]) == "undefined") {
		        obj[nodeName] = xmlToJson(item);
		      } else {
		        if (typeof (obj[nodeName].length) == "undefined") {
		          var old = obj[nodeName];
		          obj[nodeName] = [];
		          obj[nodeName].push(old);
		        }
		        obj[nodeName].push(xmlToJson(item));
		      }
		    }
		  }
		  return obj;
		};
		
		function printPage(){
			var url = "${curl}/table_getProcessOfMobile.do?instanceId=${instanceId}&isPrint=1";
			var WinWidth = 800;
			var WinHeight = 600;
			if(top.window && top.window.process && top.window.process.type){
				console.info("封装打开方式");
				var ipc = top.window.nodeRequire('ipc');
				var remote = top.window.nodeRequire('remote');
				var browserwindow = remote.require('browser-window');
				var winProps = {};
				winProps.width = '800';
				winProps.height = '600';
				winProps['web-preferences'] = {'plugins':true};
				var focusedId = browserwindow.getFocusedWindow().id;
				var win = new browserwindow(winProps);
				//console.info(focusedId);
				win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
				//win.openDevTools();
				win.on('closed',function(){
					win = null;
				});				    
				ipc.on('message-departmentTree',function(args){
					if(win){
						win.close();
						win = null;
					}
				});
			}else{
				console.info("window.open普通打开方式");
				var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
				var loop = setInterval(function(){
					if(winObj.closed){
						//console.info(window.returnValue);
						clearInterval(loop);
						//---------------------------
						
						 
					}
				},500);
			}
		}
		
		function openChildWf(processId,instanceId){
			$.ajax({   
				url : '${ctx }/table_openChildProcess.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_openChildProcess.do)');
				},
				data : {
					'processId'	:processId,
					'instanceId':instanceId
				},    
				success : function(childWfJson) {  
					if(childWfJson != ''){
						childWfJson = JSON2.parse(childWfJson);
						var i=0,l=childWfJson.length;
						var trBak = "<tr id=\""+processId+"NewTr\"><td colspan=\"7\"><table id=\""+processId+"NewTable\" class=\"infotan mt10\"  width=\"96%\" style=\"line-height:10px;\">";
						trBak += "<thead><tr><th width=\"10%\">步骤</th> <th width=\"10%\">办理人员</th> <th width=\"18%\">收到时间</th>";
						trBak += "<th width=\"18%\">处理时间</th><th width=\"15%\">办理意见</th><th width=\"10%\">提交人员</th><th width=\"9%\">备注</th></tr></thead>";
						trBak += "<tbody></tbody></table></td></tr>";
						//当前行后面添加tr
					    $('#'+processId+"Tr").after(trBak);
					    //取第一个instanceId
					    var childInstanceId = childWfJson[0].wfInstanceUid;
					    for(;i<l;i++){
					    	//用于显示流程分割行
						    if(childInstanceId != childWfJson[i].wfInstanceUid){
						    	childInstanceId = childWfJson[i].wfInstanceUid;
						    	$('#'+processId+"NewTable").append("<tr><td colspan=\"7\">&nbsp;&nbsp;</td></tr>");
							}
						    var tableContent = "";
						    tableContent += "<tr><td align='left'>"+childWfJson[i].nodeName;
						    if(childWfJson[i].isHaveChild == '1'){
						    	tableContent += "<span id='"+childWfJson[i].wfProcessUid+"Href'>"
						    	 			+ "<a href='#' onclick='openChildWf(\""+childWfJson[i].wfProcessUid+"\",\""+childWfJson[i].wfInstanceUid+"\");'>"
						    	 			+ "<font color='red><span style='font-size:25px;vertical-align:middle;line-height:24px;'>+</span>"
						    	 			+ "</font></a></span>";
						    }
							tableContent += "</td><td align='left'>"+childWfJson[i].userName+"</td>";
							tableContent += "<td align='left'>"+childWfJson[i].str_applyTime+"</td>";
							tableContent += "<td align='left'>"+childWfJson[i].str_finshTime+"</td>";
							var commentText = "[未签署]";
							if(null != childWfJson[i].commentText && '' != childWfJson[i].commentText){
								commentText = childWfJson[i].commentText;
							}
							tableContent += "<td align='left'>"+commentText+"</td>";
							tableContent += "<td align='left'>"+childWfJson[i].fromUserName+"</td>";
							var isReturnStep = childWfJson[i].isReturnStep;
							var isBack = childWfJson[i].isBack;
							var status = "";
							if(isReturnStep == '1'){
								status = '退回过程';
							}else{
								if(isBack == '2'){
									status = '废弃过程';
								}
							}
							tableContent += "<td align='left'>"+status+"</td></tr>";
							$('#'+processId+"NewTable").append(tableContent);
						}
						var newHerf = '<a href="#" onclick="closeChildWf(\''+processId+'\',\''+instanceId+'\');"><font color="red" ><span id="'+processId+'Href" style="font-size:35px;vertical-align: middle;line-height:24px;"> -</span></font></a>';
						$('#'+processId+"Href").html(newHerf);
					}
				}
			});
		}

		function closeChildWf(processId,instanceId){
			$('#'+processId+"NewTr").html("");	 
			$('#'+processId+"Href").html('<a href="#" onclick="openChildWf(\''+processId+'\',\''+instanceId+'\');"><font color="red"><span id="'+processId+'Href"  style="font-size:25px;vertical-align: middle;line-height:24px;">+</span></font></a>');
		}
    </script>  	
</body>
<script>
(function ($) {
 $.extend({
  Request: function (m) {
   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
   return sValue ? sValue[1] : sValue;
  },
  UrlUpdateParams: function (url, name, value) {
   var r = url;
   if (r != null && r != 'undefined' && r != "") {
    value = encodeURIComponent(value);
    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
    var tmp = name + "=" + value;
    if (url.match(reg) != null) {
     r = url.replace(eval(reg), tmp);
    }
    else {
     if (url.match("[\?]")) {
      r = url + "&" + tmp;
     } else {
      r = url + "?" + tmp;
     }
    }
   }
   return r;
  }
 
 });
})(jQuery);
</script>
</html>