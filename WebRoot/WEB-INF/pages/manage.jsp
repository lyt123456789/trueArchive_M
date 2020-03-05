<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
 		<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
		<%@ include file="/common/header.jsp"%>
		<%@ include file="/common/headerindex.jsp"%>
		 <script type="text/javascript" src="${ctx}/widgets/plugin/js/common/dialog/artDialog.js"></script>
		<script type="text/javascript" src="${ctx }/flow/js/classMessage.js" defer="defer"></script>
		<script type="text/javascript" src="${ctx }/flow/js/classMessage_consult.js" defer="defer"></script>
		<script src="${ctx}/tabInLayer/plugins/layer/layer.js" type="text/javascript"></script>
		<script src="${ctx}/tabInLayer/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="${ctx}/tabInLayer/js/bootstrap-addtabs.js" type="text/javascript"></script>
		<script type="text/javascript">
$(function(){
	DWZ.init("${ctx}/dwz.frag.xml", {
		loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:true,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${ctx}/dwz/themes"}); // themeBase 相对于index页面的主题base路径 
		}
	});  
});
</script>
    <style>
    .layui-layer {
	    background-color:#eee;
    }   
    .layui-layer-content {
        overflow-y:hidden!important;
    } 
    </style>
	</head>
    <form action="${ctx}/form_loginout.do" id="form1" method="post" target="_self"></form>
	<body scroll="no" id="bodyid">
	<div id="layout">

		<div id="leftside" style="top:0px">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
					
				<div class="accordion" fillSpace="sidebar">
				
					<div class="accordionHeader">
						<h2><span>Folder</span>账号角色管理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="" href="${ctx}/role_toRoleManagePage.do" external="true" target="navTab" rel="toRoleManagePage">角色菜单配置</a></li>
							<li><a id="" href="${ctx}/role_toCasualUserManage.do" external="true" target="navTab" rel="toCasualUserManage">自主查档管理</a></li>
						</ul>
					</div>
					
					<div class="accordionHeader">
							<h2><span>Folder</span>借阅配置</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="" href="${ctx }/using_toshowNode.do?vc_table=1" external="true" target="navTab" rel="showNode1">借阅单管理</a></li>
							<li><a id="" href="${ctx }/using_toshowNode.do?vc_table=2" external="true" target="navTab" rel="showNode2">借阅库管理</a></li>
							<li><a id="" href="${ctx }/using_toShowData.do" external="true" target="navTab" rel="showData">数据字典维护</a></li>
						</ul>
					</div>	
					
					<div class="accordionHeader">
							<h2><span>Folder</span>全文检索配置</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a id="" href="${ctx }/ftm_toFullTextManageJsp.do" external="true" target="navTab" rel="FullTextManageJsp">索引管理</a></li>
						</ul>
					</div>				
				</div>
			</div>
		</div>
		<div id="container" style="top:0px">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<!-- <li tabid="main" class="main"><a href="javascript:;"><span style="width: 0px"><span class="home_icon"></span></span></a></li> -->
							 <li tabid="main" class="main"></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<!-- <li><a href="javascript:;">欢迎页</a></li> -->
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page">
						<div id="divCurPage" layoutH="0">
							<iframe id="mainframe" name="mainframe" frameborder="no" marginheight="0" marginwidth="0" border="0" style="width:100%;" src=""></iframe>
						</div> 
					</div> 
				</div>
			</div>
		</div> 
	</div> 
	<div id="footer">Copyright &copy; 2013 江苏中威科技软件系统有限公司             Tel：0513-81550895</div>
    <div id="tabs" style="display:none;">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">                  
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">                 
        </div>
    </div>
</body>
<script>
$(document).ready(function(){
	var bH=$(window).height();
	$('iframe').height(bH-118);  
	$('#tmcon').width(198); 
	$('#tmcon').height(bH-60-108); 
});
$(window).bind('resize',function(){
	var bH=$(window).height();
	$('iframe').height($('.navTab-panel').height());
});


var interval;
function ConsultMsg(){
	//interval = setInterval(showConsultMsg,1000*61);
	 showConsultMsg();
}
//弹出待办提示
function showConsultMsg(){
	$.ajax({   
		url : '${ctx}/table_getNotReadConsultList.do',
		type : 'POST',   
		cache : false,
		global:false,
		dataType:'html',
		error : function() {  
			clearInterval(interval);
			//alert('错误，连接已断开');
		},
		success : function(result) {  
			
			if(result=='[null]'){
				return;
			}
			var obj = eval('('+result+')');
			for(var i=0;i<obj.length;i++){
				var id = obj[i].id;
				var userId = obj[i].fromUserId;
				var userName = obj[i].fromUserName;
				var msg = obj[i].message;

				var args = new Array();
				args.push(id);
				args.push(userId);
				args.push(userName);
				var MSG1 = new CLASS_MSN_MESSAGE_CONSULT("consultId_"+i,240,150,userName+" 说：","协商消息",msg,args,null); 
			    MSG1.rect(null,null,null,screen.height-50); 
			    MSG1.speed    = 10; 
			    MSG1.step    = 5;
			  
				 /**
				 * 显示提示框
				 */ 
				MSG1.show();
				
				setTimeout(function(){MSG1.close=true;MSG1.hide();},1000*30);
				break;//显示第一条，多条显示会引起提示框不停闪烁
			}
			
		}
	});
	
}
</script>
    <script type="text/javascript">
	        var layid,
                taskid,
	            isFull = 0;
            function topOpenLayerTabs(processId,width,height,title,url){
            	if('' != processId){
					$.ajax({
						url : '${ctx}/tableExtend_updateIsOpen.do',
						type : 'POST',
						cache : false,
						async : false,
						data : {
							'processId':processId,
							'isOpen':'1'
						},
						success : function(ret) {
						}
					});
				}
            	
			    if ($('.layui-layer').length == 1) {
					if(document.all && document.addEventListener && window.atob){
				    
					}else {
                        addTabsInLayer(processId,title,url);
                        layer.restore(taskid);
					}
				}else{
                    layer.open({
                        type: 1,
                        title: false,
                        shade: false,
						move: '.nav-tabs',
						moveType: 1,
                        area: [$(window).width()-100+'px','90%'],
                        content: $('#tabs'),
					    success: function(){
			                $('#tabs').addtabs({
				                iframeHeight: ($(window).height()*0.9)+'px',
				                callback: function () { //关闭tabs后回调函数
				                    if($('.layui-layer li[role = "presentation"].active').length == 0){
					                    layer.close(taskid);
						                $('#tabs .nav-tabs').html('');
						                $('#tabs .tab-content').html('');
					                }
				                    if($('.page iframe:visible')[0].contentWindow.autoCheck){
				                    	$('.page iframe:visible')[0].contentWindow.autoCheck()
									} else {
										var selectIndex = $('.page iframe:visible')[0].contentWindow.selectIndex;
									   	var pageSize = $('.page iframe:visible')[0].contentWindow.pageSize;
									   
									   	if(null == selectIndex || '' == selectIndex){
											selectIndex = '1';
									   	}
									   	if(null == pageSize || '' == pageSize){
										   	pageSize = '10';
									   	}
								   
									   	var pageIndex = (parseInt(selectIndex)-1)*parseInt(pageSize);
									   	var visibleUrl = $('.page iframe:visible').attr('src');
									   	if(visibleUrl.indexOf("?") > 0){
										   	visibleUrl = visibleUrl + "&pageSize="+pageSize + "&selectIndex=" + selectIndex + "&pageIndex=" + pageIndex;
									   	}else{
										   	visibleUrl = visibleUrl + "?pageSize="+pageSize + "&selectIndex=" + selectIndex + "&pageIndex=" + pageIndex;
									   	}
									   	$('.page iframe:visible').attr('src',visibleUrl);
										//$('.page iframe:visible').attr('src',$('.page iframe:visible').attr('src'));
									}
                               }
				            });						
                            layid = $('.layui-layer').attr("id");
                            taskid = parseInt(layid.replace("layui-layer",""));						
                            addTabsInLayer(processId,title,url);
                           
					    },
						min: function(){
							$('.layui-layer').height('38px');
							$('.layui-layer').width($(window).width()-100+'px');
							$('.iframeClass').height('0px');
						},					    
						cancel: function(){
							debugger;
						   $('#tabs .nav-tabs').html('');
						   $('#tabs .tab-content').html('');
						   
						   if($('.page iframe:visible')[0].contentWindow.autoCheck){
							   $('.page iframe:visible')[0].contentWindow.autoCheck()
						   } else {
							   var selectIndex = $('.page iframe:visible')[0].contentWindow.selectIndex;
							   var pageSize = $('.page iframe:visible')[0].contentWindow.pageSize;
							   
							   if(null == selectIndex || '' == selectIndex){
								   selectIndex = '1';
							   }
							   if(null == pageSize || '' == pageSize){
								   pageSize = '10';
							   }
						   
						   
							   var pageIndex = (parseInt(selectIndex)-1)*parseInt(pageSize);
							   var visibleUrl = $('.page iframe:visible').attr('src');
							   if(visibleUrl.indexOf("?") > 0){
								   visibleUrl = visibleUrl + "&pageSize="+pageSize + "&selectIndex=" + selectIndex + "&pageIndex=" + pageIndex;
							   }else{
								   visibleUrl = visibleUrl + "?pageSize="+pageSize + "&selectIndex=" + selectIndex + "&pageIndex=" + pageIndex;
							   }
							   $('.page iframe:visible').attr('src',visibleUrl);
						   }
						},
						full: function(){
						    $('.layui-layer').height($(window).height());
						    $('.layui-layer-content').height($(window).height());
						    $('.iframeClass').height($(window).height());
						    isFull = 1;
						},
						restore: function(){
						    $('.layui-layer').height($(window).height()*0.9);
						    $('.layui-layer-content').css('height',$(window).height()*0.9);
						    $('.iframeClass').height($(window).height()*0.9);
						    isFull = 0;
						}
                    });				
				}
			}
			
			function addTabsInLayer(processId,title,url){
				if(url.indexOf('?') >= 0){
					url += '&title='+title
				} else {
					url += '?title='+title
				}
                Addtabs.add({
                    id: processId,
                    title: title,
                    url: url
                });					
			}

			function closeTabsInLayer(processId) {
				if('' != processId){
					$.ajax({
						url : '${ctx}/tableExtend_updateIsOpen.do',
						type : 'POST',
						cache : false,
						async : false,
						data : {
							'processId':processId,
							'isOpen':'0'
						},
						success : function(ret) {
						}
					});
				}
			    var index = $('.layui-layer li[role = "presentation"].active').find('a').attr('aria-controls');
				Addtabs.close(index);   
			}
			
			function maxOrNotToMax(){
			    if(isFull==0){
				    layer.full(taskid);	
					isFull = 0;
				}
				var bH = $(window).height();
				$('.layui-layer,.layui-layer-content,.iframeClass').height(bH+80);

			}

			function maxOrNotToMin(){
			    if(isFull==0){				
				    layer.restore(taskid);	
					var bH = $(window).height();
					$('.layui-layer').height(bH-80);
					$('.layui-layer-content').height(bH-87);
		            $('.iframeClass').height(bH);				    
			    }else{
					var bH = $(window).height();
					$('.layui-layer').height(bH);
					$('.layui-layer-content').height(bH-2);
		            $('.iframeClass').height(bH);
			    }
			}			
        </script>
</html>
