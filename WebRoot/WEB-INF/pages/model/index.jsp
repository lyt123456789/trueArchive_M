<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerbase.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	 <meta charset="UTF-8">
	 <meta http-equiv="X-UA-Compatible" content="IE=edge" >
    <title></title>
    <link rel="stylesheet" href="css/style.css?t=111">
     <link rel="stylesheet" href="css/pop.css">
     <link rel="stylesheet" href="css/info_css.css?t=33">
    <script src="js/jquery-1.8.2.min.js?t=11"></script>
    <script src="js/pop.js?t=22"></script>
</head> 
<body><div class="top">
    <div class="top_l">
        <a href="javascript:void(0)" style="float: left;"><img src="img/menu_ico.png" height="16" width="19"></a>
        <span>数字档案利用系统</span>
        <form action="${ctx}/form_loginout.do" id="form1" method="post" target="_self"></form>
    </div>
    <div class="top_m">
        <ul>
        	<c:if test="${menuFlag=='success'}" var="flag">
	        	<c:forEach items="${menuList}" var="data" varStatus="status">
		    			<c:if test="${data.isCheck=='yes'}">
		    				<c:if test="${status.count==1}">
		    				 	<li class="act">
		    				</c:if>
		    				<c:if test="${status.count>1}">
		    				 	<li>
		    				</c:if>
	                				<a href="javascript:void(0)" onclick="changeSrc('${ctx}${data.menuUrl }',${data.menuIndex })" >
	                					<img src="${data.menuPic }" class="sh">
	                					<img src="${data.menuActPic }" class="none">
	                					<br>
	                					<span>${data.menuName }</span>
	                				</a>
            				 	</li>
		    			</c:if>
		    	</c:forEach>
        	</c:if>
        	<c:if test="${not flag}">
        		 <li class="act">${menuList }</li>
        	</c:if>
        </ul>
    </div>
    <div class="top_r">
        <a href="javascript:void(0)" class="sqzx" onclick="applyCenter()">
            <img src="img/sqzx.png">申请中心
            <label class="jiaobiao"></label>
        </a>
        <a href="" class="sqzx">
            <img src="img/jrdaxt.png">进入档案系统
        </a>

        <a href="javascript:loginout();" class="exit"><img src="img/exit.png"></a>

    </div>
</div>
<div class="main">
	<iframe id="frame" width="100%" frameborder="0" src="${ctx}/model_toSearchJsp.do" class="iframe"></iframe>
</div>
<div id="winpop">
 <div class="title">您有新的短消息！<span class="close" id="close">X</span></div>
 <div class="con" id="showMsg">
	<c:forEach items="${msglist}" var="msg" varStatus="state">
				<div class="info_box">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td width="10px;"><div class="info_boxl"></div></td>
								<td><div class="info_boxc">
										<div class="info_btop f12blue">
											<c:if test="${'1' eq msg.msgType}">借阅申请</c:if>
											<c:if test="${'2' eq msg.msgType}">调卷申请</c:if>
											<c:if test="${'3' eq msg.msgType}">系统消息</c:if>
										</div>
										<div class="info_bcenter">
											<a style=""  class="splj"
												href="javascript:void(0);"
												onclick="showDetail('${msg.id}','${msg.glid}','${msg.msgType}');">${msg.content_message }</a>
										</div>
										<div class="info_bfoot f12blue">
											<div class="info_icon" title="操作人"></div>
											<div class="info_man">${msg.sender }</div>
											<div class="info_date">${msg.sendTime }</div>
										</div>
									</div></td>
								<td width="10px;"><div class="info_boxr"></div></td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:forEach>
</div>
</div>
<script>
var websocket = null;
var host = document.location.host; 
var username = "${loginUsername}"; // 获得当前登录人员的userName 
function initWebSocket(){
	//判断当前浏览器是否支持WebSocket 
	 if ('WebSocket' in window) { 
	     websocket = new WebSocket('ws://'+host+'/trueArchive/msgWebSocket/'+username); 
	 } else { 
	     alert('当前浏览器 Not support websocket') 
	 }   
	 //连接发生错误的回调方法 
	 websocket.onerror = function() { 
	 	alert("WebSocket连接发生错误");
	 };     
	 //连接成功建立的回调方法 
	 websocket.onopen = function() {
	 	console.log("WebSocket连接成功");
	 }   
	 //接收到消息的回调方法 
	 websocket.onmessage = function(event) {
		var obj = eval('(' + event.data + ')');
	 	if(obj.msgType=="1"){//右下角弹框更新
	 		$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"text",//返回值类
		        url:"${ctx}/model_getMsg.do",
		        success:function(text){
		        	var list = eval('(' + text + ')');
		        	var showMsg = document.getElementById("showMsg");
		        	var html_str = "";
		        	for(var i=0;i<list.length;i++){
		        		var str="<div class='info_box'><table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr>"+
		        				"<td width='10px;'><div class='info_boxl'></div></td><td><div class='info_boxc'>";
		        		if(list[i].msgType=="1"){
		        			str+="<div class='info_btop f12blue'>借阅申请</div>";
		        		}else if(list[i].msgType=="2"){
		        			str+="<div class='info_btop f12blue'>调卷申请</div>";
		        		}else if(list[i].msgType=="3"){
		        			str+="<div class='info_btop f12blue'>系统消息</div>";
		        		}else{
		        			str+="<div class='info_btop f12blue'>系统消息</div>";
		        		}
		        		str+="<div class='info_bcenter'>"+
		        		     "<a style=''  class='splj' href='javascript:void(0);' onclick='showDetail(\""+list[i].id+"\",\""+list[i].glid+"\",\""+list[i].msgType+"\");'>"+list[i].content_message+"</a>"+
		        		     "</div><div class='info_bfoot f12blue'><div class='info_icon' title='操作人'></div>"+
		        		     "<div class='info_man'>"+list[i].sender+"</div><div class='info_date'>"+list[i].sendTime+"</div>"+
								"</div></div></td><td width='10px;'><div class='info_boxr'></div></td></tr></tbody></table></div>";
		        		html_str+=str;
		        	}
		        	showMsg.innerHTML=html_str;
		        }
		    });
	 		showMsgList();
	 	}else if(obj.msgType=="2"){//右上角申请中心数字更新
	 		
	 	}
	 }  
	 //连接关闭的回调方法 
	 websocket.onclose = function() { 
	     console.log("WebSocket连接成功");
	 }    
	 //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。 
	 window.onbeforeunload = function() { 
	     closeWebSocket(); 
	 }    
}
//关闭WebSocket连接 
function closeWebSocket() { 
    websocket.close(); 
}

initWebSocket();


    $(function () {
        $(".main").height($(window).height()-64);
        $(".iframe").height($(window).height()-64);
        //初始化右下角弹框
        if("${msgnum}"!="0"){
        	 var oclose=document.getElementById("close");
           	 var bt=document.getElementById("bt");
           	 document.getElementById('winpop').style.height='0px';
           	 setTimeout("tips_pop()",2000);
           	 oclose.onclick=function(){tips_pop();}
        }	
        getDbCount();
    });
    
    function getDbCount() {
    	$.ajax({
			url:"${ctx}/model_getdbCount.do",
			type:"post",
			async:false,
			cache: false,
			dataType:"json",//返回值类型
			success:function(data){
				var count = data.dbCount;
				$(".jiaobiao").text(count);
			},
			error:function(){
				alert("系统错误请重试");
			}	
		}); 
    }

	
	function changeSrc(src,index){
    	$(".top_m li").removeClass("act"); 
    	$(".top_m li").eq(index-1).addClass("act");
    	
		$("#frame").attr("src",src);
	}
    function loginout(){
		if(!confirm('是否退出系统?'))return; 
		//document.getElementById('form1').submit();
		//如果系统为单点登录则执行单点登出方法，否则执行普通退出方法
		if('${ssologin}'=='1'){
			var sso_out = '${ssoUrl}/logout';
			//alert(sso_out+'?service=${curl}/form_loginout.do');
			location.href = 'http://192.168.5.103:10086/sso/logout?service=${curl}';
		}else{
			document.getElementById('form1').submit();
			window.close();
		};
	};
	
	function showDetail(id,glid,msgType){
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"id":id},
	        url:"${ctx}/using_updateApplyMsg.do?id="+id,
	        success:function(text){
	        	//刷新消息框
	        	$.ajax({
    		        async:true,//是否异步
    		        type:"POST",//请求类型post\get
    		        cache:false,//是否使用缓存
    		        dataType:"text",//返回值类
    		        url:"${ctx}/model_getMsg.do",
    		        success:function(text){
    		        	var list = eval('(' + text + ')');
    		        	var showMsg = document.getElementById("showMsg");
    		        	var html_str = "";
    		        	if(list.length==0){
    		        		showMsg.innerHTML="";
    		        		hiddenMsgList();
    		        	}else{
    		        		for(var i=0;i<list.length;i++){
	    		        		var str="<div class='info_box'><table border='0' cellpadding='0' cellspacing='0' width='100%'><tbody><tr>"+
	    		        				"<td width='10px;'><div class='info_boxl'></div></td><td><div class='info_boxc'>";
	    		        		if(list[i].msgType=="1"){
	    		        			str+="<div class='info_btop f12blue'>借阅申请</div>";
	    		        		}else if(list[i].msgType=="2"){
	    		        			str+="<div class='info_btop f12blue'>调卷申请</div>";
	    		        		}else if(list[i].msgType=="3"){
	    		        			str+="<div class='info_btop f12blue'>系统消息</div>";
	    		        		}else{
	    		        			str+="<div class='info_btop f12blue'>系统消息</div>";
	    		        		}
	    		        		str+="<div class='info_bcenter'>"+
	    		        		     "<a style=''  class='splj' href='javascript:void(0);' onclick='showDetail(\""+list[i].id+"\",\""+list[i].glid+"\",\""+list[i].msgType+"\");'>"+list[i].content_message+"</a>"+
	    		        		     "</div><div class='info_bfoot f12blue'><div class='info_icon' title='操作人'></div>"+
	    		        		     "<div class='info_man'>"+list[i].sender+"</div><div class='info_date'>"+list[i].sendTime+"</div>"+
	    								"</div></div></td><td width='10px;'><div class='info_boxr'></div></td></tr></tbody></table></div>";
	    		        		html_str+=str;
	    		        	}
	    		        	showMsg.innerHTML=html_str;
	    		        	showMsgList();//展示
    		        	}
    		        }
    		    });
	        	
	        	if(msgType=="1"){//借阅单
	        		layer.open({
		                type: 2,
		                title: "明细",
		                shadeClose: true,
		                shade: 0.4,
		                area: ['90%', '90%'],
		                content: "${ctx}/using_showUsingForm.do?vc_table=1&type=&id="+glid
		            });
	        	}else if(msgType=="2"){//调卷单
	        		layer.open({
		                type: 2,
		                title: "明细",
		                shadeClose: true,
		                shade: 0.4,
		                area: ['90%', '90%'],
		                content: "${ctx}/using_showTransferForm.do?id="+glid
		            });
	        	}else if(msgType=="3"){//直接刷新消息框
	        		
	        	}
	        }
	    });
	}
	
	function applyCenter() {
		layer.open({
            type: 2,
            title: "申请中心",
            shadeClose: true,
            shade: 0.4,
            area: ['90%', '90%'],
            content: "${ctx}/model_toApplyCenter.do?applyFlag=1",
            cancel: function(){
		        //右上角关闭回调
		   	 	getDbCount();
		   }
        });
	}
</script>
</body>
</html>