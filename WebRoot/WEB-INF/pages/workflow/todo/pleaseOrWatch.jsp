<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>

<title>请输入意见类型：</title>
<style type="text/css">
	#content{
		width: 100%;
		height: 100%;
		border-radius: 6px;
	}
	#top{
		width: 100%;
		height: 15%;
		background-color: #248BE8;
		font-size: 17px;
    	font-weight: bold;
    	color: #EBF0F6;
	}
	.title{
		position: relative;
	    left: 20px;
	    top: 7px;
	}
	#pleaseBtn{
		background-color: #157AD6;
	}
	#watchBtn{
		background-color: #0865B9;
	}
	.clickBtn{
	    padding: 2px 2px;
	    width: 30px;
	    height: 24px;
	    border: 1px solid white;
	    border-radius: 3px;
	    color: #EBF0F6;
	    float: right;
	    margin-right: 16px;
	    margin-top: 5px;
	    cursor:pointer;
	}
	.clickBtn span{
		position: relative;
		left: 6px;
		top: 0px;
	}
	#main{
		height: 60%;
		width: 100%;
	}
	#text{
	    height: 85%;
	    width: 96%;
	    overflow: auto;
	    margin-left: 2%;
	    margin-top: 1%;
	    font-size: 25px;
	    resize:none;
	    border: 1px solid #248BE8;
	    font-family:SimSun;
	}
	.operateBtn{
		display: inline-block;
	    background-color: #F1BB18;
	    border: 0;
	    height: 37px;
	    width: 105px;
	    font-size: 19px;
	    color: white;
	    margin-left: 118px;
	}
	#bottom{
		width: 82%;
	    height: 20%;
	    margin-left: 7%;
	}
</style>
</head>
<body>
	<div id="content">
		<div id="top">
			<span class="title">
				意见输入
			</span>
			<div id="pleaseBtn" class="clickBtn">
				<span>请</span>
			</div>
			<div id="watchBtn" class="clickBtn">
				<span>阅</span>
			</div>
		</div>
		<div id="main">
			<textarea id="text"><%-- ${oldComment} --%></textarea>
			<input type="hidden" id="textUserId" />
		</div>
		<div id="bottom">
			<div id="submit" class="operateBtn wf-btn-primary">
				<span>确定</span>
			</div>
			<div id="giveup" class="operateBtn wf-btn-primary">
				<span>取消</span>
			</div>
		</div>
	</div>

<script type="text/javascript">
	var routeType = '${routeType}';
	var nodeId = '${nodeId}';
	$(function(){
		$('#giveup').bind('click',function(){
			window.close();
		});
		$('#submit').bind('click',function(){
			if(!stringNotEmpty($('#text').val())){
				return;
			}
			if(top.window && top.window.process && top.window.process.type){
				var remote = top.window.nodeRequire('remote');
               	var browserwindow = remote.require('browser-window');
                var win = browserwindow.fromId(parseInt($.Request('focusedId')));
				if(win){
	                win.webContents.send('message-departmentTree', $('#textUserId').val()+"||"+ $('#text').val());
                }
			}else{
				opener.window.returnValue = $('#textUserId').val()+"||"+ $('#text').val();
				 window.close();
			}
			//parent.operateForm(1,'');
		});
		$('#pleaseBtn').bind('click',function(){
			var url4UserChose='${curl}/ztree_showDepartmentTree.do?nodeId='+nodeId+'&showCheckbox=1&pleaseOrWatch=1&siteId=${siteId}&routType='+routeType+'&t='+new Date();
			var url = url4UserChose;
			var WinWidth = 1000;
			var WinHeight = 620;
			if(top.window && top.window.process && top.window.process.type){
				console.info("封装打开方式");
			    var ipc = top.window.nodeRequire('ipc');
			    var remote = top.window.nodeRequire('remote');
			    var browserwindow = remote.require('browser-window');
	            var winProps = {};
                winProps.width = 1000;
                winProps.height = 620;
	            winProps['web-preferences'] = {'plugins':true};
                var focusedId = browserwindow.getFocusedWindow().id;
                treeWin = new browserwindow(winProps);
				//console.info(focusedId);
		        treeWin.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
			    //win.openDevTools();
				treeWin.on('closed',function(){
					treeWin = null;
				    canClick=true;
				});				
			    ipc.on('message-departmentTree',function(args){
					if(treeWin){
						canClick=true;
						treeWin.close();
						treeWin = null;
						if(args!=null&&args!=''&&args!='undefined'){
							var ret = args.split("||")[0]; 
							var retName = args.split("||")[1]; 
							$('#text').html(retName);
							$('#text').val(retName);
							$('#textUserId').val(ret);
						}
						
					}
			    });
			}else{
				console.info("window.open普通打开方式");
			    treeWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
			    var loop = setInterval(function(){
				    if(treeWin.closed){
						//console.info(window.returnValue);
					    clearInterval(loop);
					    //---------------------------
						var args = window.returnValue;
						if(args!=null&&args!=''&&args!='undefined'){
							var ret = args.split("||")[0]; 
							var retName = args.split("||")[1]; 
							$('#text').html(retName);
							$('#text').val(retName);
							$('#textUserId').val(ret);
						}
				    }
			    },500);
			}			
		});
		$('#watchBtn').bind('click',function(){
			$('#text').html('阅');
			$('#text').val('阅');
			$('#textUserId').val("");
		});
	});
</script>
<script type="text/javascript">
	function stringNotEmpty(str){
		if(str!=null&&str!=''&&str!='undefined'){
			return true;
		}
		return false;
	}
</script>
	<script type="text/javascript">
		(function($) {
			$.extend({
				Request : function(m) {
					var sValue = location.search.match(new RegExp("[\?\&]" + m
							+ "=([^\&]*)(\&?)", "i"));
					return sValue ? sValue[1] : sValue;
				},
				UrlUpdateParams : function(url, name, value) {
					var r = url;
					if (r != null && r != 'undefined' && r != "") {
						value = encodeURIComponent(value);
						var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
						var tmp = name + "=" + value;
						if (url.match(reg) != null) {
							r = url.replace(eval(reg), tmp);
						} else {
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
</body>
</html>