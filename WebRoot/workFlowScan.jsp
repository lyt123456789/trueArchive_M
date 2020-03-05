<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
	<link rel="stylesheet" href="${ctx}/assets-common/css/reset.css">
	<link rel="stylesheet" href="${ctx}/assets-common/css/scan.css">
	<link rel="stylesheet" href="${ctx}/assets-common/css/animate.css">
	<script src="${ctx}/assets-common/js/jquery.nicescroll.js" type="text/jscript"></script>
	<style>
	    .tw-main-list {overflow-x:hidden!important;}
	</style>
</head>
<body>
    <div class="wrapper">
	    <div class="tw-container cl">
                <div class="tw-main-box">
					<div class="tw-main-left">
					    <div class="scan-btn" onclick="check();">开始检测</div>
						<div class="tw-scan" style="display:none;">
							<img src="${ctx}/assets-common/image/scan.gif" />
							<p>流程正在检测中...</p>
						</div>
						<div class="tw-result" style="display:none;">
						    <span class="success"><i class="result-icon"></i>检测完毕</span>
						</div>
					</div>
					<div class="tw-main-right">
						<ul class="tw-main-list">
							<li class="notSet" style="display:none"></li>
							<li class="notSet" style="display:none"></li>
							<li class="notSet" style="display:none"></li>
							<li class="notSet" style="display:none"></li>
							<li class="notSet" style="display:none"></li>
							<li class="notSet" style="display:none"></li>
						</ul>
					</div>
				</div>
		</div>       
	</div>
<script>
    $(document).ready(function(){
		$('.scan-btn').click(function(){
		    //使滚动条一直在最底部
		    //$(".tw-main-list").scrollTop($(".tw-main-list")[0].scrollHeight);		    
		    $(this).hide();
			$('.tw-scan').show();
		});
        $('.tw-main-list').niceScroll({
            cursorcolor: "#ccc",//#CC0071 光标颜色
            cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
            touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
            cursorwidth: "5px", //像素光标的宽度
            cursorborder: "0", // 	游标边框css定义
            cursorborderradius: "5px",//以像素为光标边界半径
            autohidemode: true //是否隐藏滚动条
        });	      
	});
    
    function check(){
    	checkForm();
    	checkTableCreate();
    	checkFormTagMapColumn();
    	checkUserCreate();
    	checkFlowCreate();
    	checkFormPermition();
    	var length = $('.tw-main-list li').length;
		$('.tw-main-list li').each(function(index){
			var $this = $(this);
			var _height = $this.height();
			var _time = (index+1)*1000;
			var _delayTime = index;
			$this.addClass('animated fadeIn').attr('style','-webkit-animation-delay:'+_delayTime+'s; animation-delay:'+_delayTime+'s');
		    if(length === index+1) {
				setTimeout(function(){
				    $('.tw-scan').remove();
				    $('.tw-result').show();
				},_time);		    	
		    }
		});
    }
    $li = $("ul.tw-main-list").children("li.notSet").first();
    function setStep(status, txt){
    	$li.removeClass("notSet").addClass(status).text(txt);
    }
    
    function setSubStep(status, txt){
    	$subli = '<li class="subli '+status+'">'+txt+'</li>';
    	$li.after($subli);
    }
    
    function checkForm(){
    	$.ajax({
			url:"${ctx}/mobileTerminalInterface_checkForm.do",
			type:"post",
			dataType : "json",
			data:{
				"workflowId":"${workflowId}"
			},
			async : false,
			success: function(msg){
				if(msg.status=="success"){
					setStep("success","表单构建成功");
				}else{
					setStep("fail","表单构建失败");
					var forms = msg.forms;
					$(forms).each(function(){
						setSubStep("fail","表单《"+this.formName+"》构建失败，表单html文件"+this.htmlfile+"，表单jsp文件"+this.jspfile);
					});
				}
			}
		});
    }
    
    function checkTableCreate(){
    	$.ajax({
			url:"${ctx}/table_getTableCount.do",
			type:"post",
			dataType : "json",
			data:{
				"workflowId":"${workflowId}"
			},
			async : false,
			success: function(msg){
				if(msg.count>0){
					$li = $li.nextAll(".notSet").first();
					setStep("success","数据表创建成功");
				}else{
					$li = $li.nextAll(".notSet").first();
					setStep("fail","数据表未创建");
				}
			}
		});
    }
    
    function checkFormTagMapColumn(){
    	$.ajax({
			url:"${ctx}/mobileTerminalInterface_checkFormTagMapColumn.do",
			type:"post",
			dataType : "json",
			data:{
				"workflowId":"${workflowId}"
			},
			async : false,
			success: function(msg){
				if(msg.status=="success"){
					$li = $li.nextAll(".notSet").first();
					setStep("success","表单字段匹配成功");
				}else{
					$li = $li.nextAll(".notSet").first();
					setStep("fail","表单字段匹配存在问题");
					var forms = msg.forms;
					$(forms).each(function(){
						if(this.status=='fail'){
							setSubStep("fail","表单《"+this.formName+"》存在问题，"+this.txt);
						}else{
							setSubStep("success","表单《"+this.formName+"》字段匹配成功");
						}
					});
				}
			}
		});
    }
    
    function checkUserCreate(){
    	$.ajax({
			url:"${ctx}/mobileTerminalInterface_checkUserCreate.do",
			type:"post",
			dataType : "json",
			data:{
				"workflowId":"${workflowId}"
			},
			async : false,
			success: function(msg){
				if(msg.status=="success"){
					$li = $li.nextAll(".notSet").first();
					setStep("success","流程用户组设置成功");
				}else{
					$li = $li.nextAll(".notSet").first();
					setStep("fail",msg.txt);
					var users = msg.users;
					$(users).each(function(){
						setSubStep("fail",this.txt);
					});
				}
			}
		});
    }
    
    function checkFlowCreate(){
    	$.ajax({
			url:"${ctx}/mobileTerminalInterface_checkFlowCreate.do",
			type:"post",
			dataType : "json",
			data:{
				"workflowId":"${workflowId}"
			},
			async : false,
			success: function(msg){
				if(msg.status=="success"){
					$li = $li.nextAll(".notSet").first();
					setStep("success",msg.txt);
				}else{
					$li = $li.nextAll(".notSet").first();
					setStep("fail",msg.txt);
					var nodes = msg.nodes;
					$(nodes).each(function(){
						setSubStep(this.status,this.txt);
					});
				}
			}
		});
    }
    
    function checkFormPermition(){
    	$.ajax({
			url:"${ctx}/mobileTerminalInterface_checkFormPermition.do",
			type:"post",
			dataType : "json",
			data:{
				"workflowId":"${workflowId}"
			},
			async : false,
			success: function(msg){
				if(msg.status=="success"){
					$li = $li.nextAll(".notSet").first();
					setStep("success",msg.txt);
				}else{
					$li = $li.nextAll(".notSet").first();
					setStep("fail",msg.txt);
					var nodes = msg.nodes;
					$(nodes).each(function(){
						setSubStep(this.status,this.txt);
					});
				}
			}
		});
    }
</script>	
</body>
</html>