<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/headerbase.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	 <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/jquery-1.8.2.min.js"></script>
</head> 
<body><div class="top">
    <div class="top_l" style="line-height: 78px;">
        <a href="javascript:void(0)" style="float: left;"><img src="img/menu_ico.png" height="16" width="19"></a>
        <span>如东县档案利用系统</span>
        <form action="${ctx}/form_loginout.do" id="form1" method="post" target="_self"></form>
    </div>
    <div class="top_m">
       
    </div>
    <div class="top_r">
       

        <a href="javascript:loginout();" class="exit"><img src="img/exit.png"></a>

    </div>
</div>
<div class="main">
	<%-- <iframe id="frame" width="100%" frameborder="0" src="${ctx}/model_toSearchJsp4Zzcd.do?zzcdFlag=${zzcdFlag}" class="iframe"></iframe> --%>
	<iframe id="frame" width="100%" frameborder="0" src="" class="iframe"></iframe>
</div>
<script>
    $(function () {
        $(".main").height($(window).height()-64);
        $(".iframe").height($(window).height()-64);
        layer.open({
			title:'身份验证',
			type:2,
			area:['460px','280px'],
			content:"${ctx}/using_toVerifyJsp.do?zzcdFlag=${zzcdFlag}"
		});
    })
    function showSearchJsp(jydId,flag){
    	if("add"==flag){
    		layer.open({
                type: 2,
                title: "添加借阅单",
                shadeClose: true,
                shade: 0.4,
                area: ['100%', '100%'],
                content: "${ctx}/using_showUsingForm.do?vc_table=1&type=1&zzcdFlag=1&id="+jydId,
                cancel: function(){
                    //右上角关闭回调
               	 window.location.reload();
               }
            });
    	}else if("edit"==flag){
    		$("#frame").attr("src","${ctx}/model_toSearchJsp.do?zzcdFlag=${zzcdFlag}&jydId="+jydId);
    	}
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
</script>
</body>
</html>