<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="keywords" content="">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <link rel="stylesheet" href="${ctx}/assets-common/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/assets-common/css/animate.css">
    <link rel="stylesheet" href="${ctx}/assets-common/css/progress.css">
	<script type="text/javascript" src="${ctx}/assets-common/js/jquery-1.11.1.min.js"></script>
</head>
<script type="text/javascript">
	/* $(document).ready(function() {		
		$('.wf-course-btn').click(function() {
		    location.reload();
		    return
		});
		function animationDelay(obj,time){
			$(obj).attr('style','-webkit-animation-delay:'+time+'s')
		}
		var len = $('.wf-course-item').length;
		$('.wf-course-item').each(function(index){
			if(index === 0 ){
				$(this).addClass('wf-course-item-level-star');
			}
			if(index === len-1) {
				$(this).find('.wf-course-arrow').remove();
			}
			var $this = $(this);
			var _height = $this.height() -22;
			var _time = (index+1)*1100;
			var _delayTime = index; 
			var _delayTime2 = index+0.4;
			var _delayTime3 = index+0.6;
			var _delayTime4 = index+1;
			$this.find('.wf-course-num').addClass('animated fadeIn').attr('style','-webkit-animation-delay:'+_delayTime+'s; animation-delay:'+_delayTime+'s');
			$this.find('.wf-course-hd').addClass('animated fadeInDown').attr('style','-webkit-animation-delay:'+_delayTime2+'s; animation-delay:'+_delayTime2+'s');
			$this.find('.wf-course-bd').addClass('animated fadeInDown').attr('style','-webkit-animation-delay:'+_delayTime3+'s; animation-delay:'+_delayTime3+'s');
			$this.find('.wf-course-status').addClass('animated fadeIn').attr('style','-webkit-animation-delay:'+_delayTime4+'s; animation-delay:'+_delayTime4+'s');
			setTimeout(function(){
				$this.find(".wf-course-arrow").animate({height:_height});
			},_time);
		})
	}); */
	
	function ThirdChat(userId){
		/* var macaddr = document.getElementById("macaddr").value;
		if(macaddr==null || macaddr==''){
			return;
		}
		if(type!=null && type=='0'){
			alert("该人员不在线,只能发送离线消息！");
		}
		var userId = o.id;
		if(macaddr!=null){
			macaddr = macaddr.replaceAll(':','-');
		} */
		//ajax异步调取后台
		$.ajax({
			url: '${ctx}/ztree_ThirdChat.do',
	        type: 'POST',
	        cache: false,
	        async: false,
	        data:{
	        	'userId':userId,'macaddr':'${localMac}'
	        },
	   		error: function(){
	   			 alert('AJAX调用错误');
	   		},
	   		success: function(result){
	   			if(result!=null && result=='unOnline'){
	   				alert("当前人员中威通讯录不在线,请登录");
	   			}
	   		}
		});
	}
</script>
<body>
	<div class="wf-course" style="width:95%; margin:10px auto">
	    <a class="wf-course-btn" href="###"><i class="fa fa-refresh"></i>刷新</a>
		<c:forEach items="${processList}" var="process">	        
			<div class="wf-course-item-wrap">
			 <c:choose>
				<c:when test="${process.nodeName=='办结'}">
					<div class="wf-course-item wf-course-item-level-over">
				</c:when>
				<c:otherwise>
					<div class="wf-course-item wf-course-item-level2">
				</c:otherwise>
			</c:choose>
				<span class="wf-course-num">${process.stepIndex}</span>
					<div class="wf-course-arrow"></div>
					<div class="wf-course-hd">
						<span class="wf-course-title">${process.nodeName}</span>
						<c:if test="${process.stepIndex!=1}">
							（来自：<span class="wf-course-name wf-font-blue">${process.fromUserName}</span>　　时间：${fn:substring(process.applyTime,0,16)}）
						</c:if>
					</div>
					<div class="wf-course-bd">
						<div class="wf-cnt-item">
						    <span class="wf-course-name"><a href="#" onclick="ThirdChat('${process.wfProcessUid}');">${process.userName}</a></span>
						</div>
						<div class="wf-cnt-item">
							<i class="wf-course-icon-time"></i>
							接收时间：${fn:substring(process.jssj,0,16)}
						</div>
						<div class="wf-cnt-item">
							<i class="wf-course-icon-time"></i>
							办理时间：${fn:substring(process.finshTime,0,16)}
						</div>
						<div class="wf-course-status">
						<c:choose>
							<c:when test="${process.isOver == 'OVER'}"><i class="fa fa-check-circle" title="已办"></i></c:when>
							<c:when test="${process.isOver == 'NOT_OVER'}">
								<c:choose>
									<c:when test="${process.jssj ==null}">
											
									</c:when>
									<c:otherwise>
									    <i class="fa fa-edit" title="办理中"></i>
									</c:otherwise>
								</c:choose>
							</c:when>
						</c:choose>
						</div>
					</div>					
				</div>
			</div>
		</c:forEach>	    
	</div>	
</body>
</html>