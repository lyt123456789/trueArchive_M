<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@ include file="/common/header.jsp"%>		
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ include file="/common/headerbase.jsp"%>
<link href="${ctx}/tabInLayer/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/tabInLayer/css/bootstrap-addtabs.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/assets-common/css/trueportal.css">
<script src="portal/js/portal.js" type="text/javascript"></script>
<script src="portal/js/artDialog.js" type="text/javascript"></script>
<script src="portal/js/ZW.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/assets-common/plugins/jquery.backstretch.js"></script>
<script type="text/javascript" src="${ctx}/assets-common/plugins/jquery-ui.js"></script>
<script src="portal/js/uploadPreview.min.js"></script>
<script src="portal/js/json2.js" type="text/javascript"></script>
<script src="portal/js/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/tabInLayer/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${ctx}/tabInLayer/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/tabInLayer/js/bootstrap-addtabs.js" type="text/javascript"></script>
<link href="${ctx}/tabInLayer/plugins/layer/skin/layer.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function changeLink(obj){}
if(top.window && top.window.process && top.window.process.type){
    var imported = document.createElement('script');
	imported.src = '${ctx}/assets-common/js/eleCommon.js';
	document.head.appendChild(imported);
}
</script>
</head>

<body>
	<!--<div class="pass-reset">
		<h3 class="form-title">
			修改密码
			<span class="close_form" id='close_form'>x</span>
		</h3>
	    <div class="pass-form" id="res-box">
			<iframe src="http://61.155.85.74:4681/ts/employee_toIndex.do" id="pass_frame" name="pass_frame" style="width: 100%;height: 100%;" frameborder="0" scrolling="no"></iframe>
		</div>
	</div>-->
	
<div class="tp-layout tp-layout">

	<div class="tp-header">
		<h1 class="tp-logo">
			<img src="${ctx }/images/MHlogo.png" id="path1" style="width: 375px;">
		</h1>
		<div class="tp-settings">
			<div class="tp-settings-user">
				<i></i><span class="JS_userName"></span><span class="JS_time"></span>
			</div>
			<div class="tp-topset">
				<a href="${ctx}/portalHome_portal.do" class="tp-topset-item">
					<i class="tp-topset-icon-home"></i>
					<b>系统首页</b>
				</a>
				<!--<span class="tp-topset-item">
					<i class="tp-topset-icon-edit"></i>
					<b>系统设置</b>
				</span>-->
				<span class="tp-topset-item" onclick="goXgmm();">
					<i class="tp-topset-icon-pwd"></i>
					<b>修改密码</b>
				</span>
				<span class="tp-topset-item" onclick="sysQuit();">
					<i class="tp-topset-icon-quit"></i>
					<b>退出</b>
				</span>
			</div>
		</div>
		<div class="tp-info"></div>
	</div>
	<div class="tp-container clearfix">
		<div class="tp-aside">
			<div id="userInfo" class="tp-aside-info">
				<div class="tp-user-head-wrap">
					<span class="tp-user-head">
						<img class="JS_userImg" src="${ctx}/assets-common/img/portal/user-head.png">
					</span>
					<span class="tp-user-setting"></span>
				</div>
				<div class="tp-user-info">
					<span class="tp-name JS_userName"></span>
					<p class="tp-job JS_userJob"></p>
				</div>
			</div>
			<div class="tp-aside-mod">
				<div class="tp-hd">
					<i class="tp-icon-sys"></i><span>岗位职责</span>
				</div>
				<div class="tp-bd cf">
					<div class="JS_userJobInfo tp-jobinfo" limit="25"></div>
					<a href="javascript:;" class="more fr" id="jobInfo">详情&gt;&gt;</a>
					<div class="JS_userJobInfoAll tp-jobinfo" style="display:none"></div>
				</div>
			</div>
			<div class="tp-aside-mod">
				<div class="tp-hd">
					<i class="tp-icon-sys"></i><span>业务系统</span>
                    <div class="tp-mod-add"><span class="JS_modedit tp-icon-edit"></span></div>
					<div class="tp-mod-add"><span class="JS_modadd tp-icon-add2"></span></div>
				</div>
				<div class="tp-bd">
					<ul class="JS_systemList tp-sys-list">
						
					</ul>
				</div>
			</div>
		</div>
		<div class="tp-main" id="defMain">
			<div class="tp-main-top">
				<div class="JS_eventList tp-event-list-wrap">
					
				</div>
				<div class="tp-event-page-box"></div>
				<div class="tw-setmenu" style="position:absolute;right:0;bottom:0;">
					<a href="javascript:void(0);" onclick="toPortalSet();"><img alt="11" src="portal/image/tw-setmenu.png" style="width:30px;height:30px;"></a>
				</div>
			</div>
			<div class="tp-section cf">
			</div>
		</div>

		<div class="tp-main" id="maxMain" style="display: none">
			<div class="tp-maxmod">
				<div class="tp-maxmod-hd">
					<span class="tp-maxmod-title">日程安排</span>
					<span id="maxBack" class="tp-maxmod-act">
						<i class="tw-icon-arrow-circle-left tw-icon-lg" ></i>
						返回
					</span>
				</div>
				<div class="tp-maxmod-bd">
					
				</div>
			</div>
		</div>


	</div>
	<div class="tp-footer">
		<p>版权所有：江苏中威科技软件系统有限公司</p>
	</div>

	<div class="JS_headModify tp-user-head-modify" style="display:none;">
		<form id="uploadFacebook" method="post" enctype="multipart/form-data" target="personframe">
			
			<div class="tp-uh-modify">
				<input type="hidden" name="userId" class="user" value='${userId}'>
				<input type="hidden" name="fileType" value="" class="JS_imgType"> 
				<div class="tp-uh-img">
					<img src="" class="JS_userImg" id="img-preview">
				</div>
				<div class="tp-uh-info">
					<h6>图片上传要求:</h6>
					<ul>
						<li>图片必须为 .jpg, .png, .bmp等有效格式 </li>
						<li>图片大小必须小于 1024 KB</li>
					</ul>
					<input type="file" class="JS_chooseImg" id="up_img" name="file"/>
					
				</div>
			</div>

		
		

		</form>	
	</div>

    <div id="tabs" style="display:none;">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">                  
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">                 
        </div>
    </div>

	
</div> 	
<script>
function sysQuit(){
  if(confirm("是否确认退出?")){
		var workflowurl ="<%=SystemParamConfigUtil.getParamValueByParam("workflowurl")%>";
		var ssoUrl ="<%=SystemParamConfigUtil.getParamValueByParam("ssoUrl")%>";
		workflowurl = workflowurl+"/form_loginout.do";
		/*清除 workflow session*/
		 $.ajax({
			  type: "POST",
			  url: workflowurl,
			  success: function (mes){
			 }
		});
		//清楚本应用的ssion
		$.ajax({
			 type: "POST",
			 url: "${ctx}/login_thirdLoginOut.do",
			 async : true,
			 success: function (mes){
				 /*清楚本应用系统的session*/
				 var ssoLogout = ssoUrl+"/logout";
				 var url = ssoLogout+"?service=${curl}/portalHome_portal.do";
				 window.location.href =  url;
			 }
		}); 
	
	}
}

//修改密码
function goXgmm(){
	art.dialog({
		id: 'xgmm',
		title: '修改密码',
		fixed: true,
		lock: true,
		background: '#000000', // 背景色    
		opacity: 0.50,	// 透明度      										
		content: '<form action="/ts/employee_modifyPassWord.do" method="post" id="form2" name="form2" >'+
			' <input type="hidden" id="loginname" name="loginname" value="${loginName}"/> '+
			'<table class="tb-form" width="450px">'+
			'<tr>'+
				'<td class="col-l" width="100px">'+
					'原密码：'+
				'</td>'+
				'<td >'+
					'<input type="password" id="oldpassword" name="oldpassword" style="height: 22px;width: 250px"/>'+ 
				'</td>'+
				'</tr>'+
				'<tr>'+
				'<td class="col-l" width="100px">'+
					'新密码：'+
				'</td>'+
				'<td >'+
					'<input type="password" id="newpassword" name="newpassword" style="height: 22px;width: 250px"/>'+ 
				'</td>'+
				'</tr>'+
				'<tr>'+
				'<td class="col-l" width="100px">'+
					'确认新密码：'+
				'</td>'+
				'<td >'+
					'<input type="password" id="newpassword2" name="newpassword2" style="height: 22px;width: 250px"/>'+ 
				'</td>'+
				'</tr>'+
				'</table> '+
			'</form> ', 
		button: [
				 {
					 name: '提交', 
					 callback: function () {
						var v=new ZW.cl.Validation();//新建验证对象
						var data=[
							{'id':'oldpassword', 'mes':'原密码','types':'notnull'},
							{'id':'newpassword', 'mes':'新密码','types':'notnull'},
							{'id':'newpassword2', 'mes':'确认新密码','types':'notnull'}
						];
						var ispass=v.excute(data);//调用方法,传入参数，完成验证
						if(!ispass){
							return false;
						}
						if($("#newpassword").val() != $("#newpassword2").val()){
							alert("两次密码不一致，请重新确认！");
							return false;	
						}
						var res ='';
						var tsurl ="<%=SystemParamConfigUtil.getParamValueByParam("tsurl")%>";
						var loginname='maxl';
						//var loginname=$("#loginname").val();
						var oldpassword=$("#oldpassword").val();
						var newpassword=$("#newpassword").val();
						 $.getJSON("http://61.155.85.74:4681/ts/employee_modifyPasswd4Portal.do?id=0&callback=?oldpassword="+oldpassword+"&newpassword="+newpassword,function(json){
                         if('yes'==json.result){
								alert('密码修改成功，请妥善保管！！');
								art.dialog({id:'xgmm'}).close();
							}else{
								alert('抱歉，密码修改失败！');
							}
         				 });
						return false;
					 },
					 focus: true
				 },
				 {
					 name: '关闭'
				 }
			 ]
	});
}

$(document).ready(function(){
    $('.JS_systemList .tp-icon-del').hide();
   	$(".JS_modadd").click(function(){
		layer.open({
            title :'添加地址',
            content: '${ctx}/portalMenu_toSelectPrivateLinks.do',
            type: 2,
		    area: ['860px','500px'],
		    btn:["确认"],
			maxmin: true,
			scrollbar: false,
			end:function(){
				initSystemInfo();
				$('.JS_modedit').removeClass('active');
				$('.JS_systemList .tp-icon-del').hide();
			},
            success:function(){
            	initSystemInfo();
		        $('.JS_modedit').removeClass('active');
                $('.JS_systemList .tp-icon-del').hide();
            }
        }); 
	});

   	$(".JS_modedit").click(function(){
		if($('.JS_systemList .tp-icon-del').length > 0) {
		    if($(this).hasClass('active')){
			    $(this).removeClass('active');
		        $('.JS_systemList .tp-icon-del').hide(); 
		    }else{
			    $(this).addClass('active');
		        $('.JS_systemList .tp-icon-del').show();				
		    }
		}else{
		    layer.msg('没有可以删除的菜单！',{time:1000,icon:3});
		}
	});
});

window.onload = function(){
    changeLink('.JS_systemList');
}
</script>
        <script type="text/javascript">
			var layid,taskid;			
		
            function topOpenLayerTabs(processId,width,height,title,url){		
			    if ($('.layui-layer').length == 1) {
					if(document.all && document.addEventListener && window.atob){					    

					}else{
                        addTabsInLayer(processId,title,url);
                        layer.restore(taskid);						
					}
				}else{			
                    layer.open({
                        type: 1,
                        title: false,
                        shade: false,
                        maxmin: true,
						move: '.nav-tabs',
						moveType: 1,
                        area: [$(window).width()-20+'px','90%'],
                        content: $('#tabs'),
					    success: function(){
			                $('#tabs').addtabs({
				                iframeHeight: $(window).height()*0.9-47+'px',
				                callback: function () { //关闭tabs后回调函数
				                    if($('.layui-layer li[role = "presentation"].active').length == 0){
					                    layer.close(taskid);
						                $('#tabs .nav-tabs').html('');
						                $('#tabs .tab-content').html('');
					                }
									$('html,body').css('overflow-y','auto'); 
									loadModuleContent($(".tp-section .tp-mod"), '57D2CBF05C374A87BF59D6C376370C7E');
								}				
				            });						
                            layid = $('.layui-layer').attr("id");
                            taskid = parseInt(layid.replace("layui-layer",""));						
                            addTabsInLayer(processId,title,url);
							$('html,body').css('overflow-y','hidden'); 
					    },
						min: function(){
							$('.layui-layer').height('38px');
							$('.layui-layer').width($(window).width()-20+'px');
							$('.iframeClass').height('0px');
						    $('html,body').css('overflow-y','auto');
						},
						cancel: function(){
						   $('#tabs .nav-tabs').html('');
						   $('#tabs .tab-content').html('');
						   $('html,body').css('overflow-y','auto'); 
						},
						full: function(){
						    $('.layui-layer').height($(window).height());
						    $('.layui-layer-content').height($(window).height());
						    $('.iframeClass').height($(window).height()-47);
						},
						restore: function(){
						    $('.layui-layer').height($(window).height()*0.9);
						    $('.layui-layer-content').css('height',$(window).height()*0.9);
						    $('.iframeClass').height($(window).height()*0.9-47);
                            $('html,body').css('overflow-y','hidden'); 							
						}
                    });				
				}			
			}

			function addTabsInLayer(processId,title,url){
				
                Addtabs.add({
                    id: processId,
                    title: title,
                    url: url
                });	
                $('html,body').css('overflow-y','hidden'); 				
			}		
 
			function closeTabsInLayer() {
			    var index = $('.layui-layer li[role = "presentation"].active').find('a').attr('aria-controls');
				Addtabs.close(index);
			}
			function toPortalSet(){
				layer.open({
					content:"{ctx}/portalHome_getPortalSet.do",
					type:2,
					title:"门户设置",
					area:["950px","600px"],
					closeBtn:1,
					scrollbar:false,
				    end: function(){
					    initTopListEvent();    
					}					
				});
			}
			
			//获取iframe 的ID，主要供子页面调用

		    function getFrameId(f){
			    var frames = document.getElementsByTagName("iframe");
				for(i=0;i<frames.length;i++){
				    if(frames[i].contentWindow == f) {
					    return(frames[i].id);
					}
				}
			}			
        </script>
</body>
</html>