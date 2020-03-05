<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.trueway.sys.util.SystemParamConfigUtil"  %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<title>崇川区电子政务内网门户</title>
<script src="portal/js/jquery-1.11.2.min.js" type="text/javascript"></script>
		<script src="portal/js/jquery-ui.min.js" type="text/javascript"></script>
		<script src="portal/js/uploadPreview.min.js"></script>
		<script src="portal/js/jquery.form.js" type="text/javascript"></script>	
		<script src="portal/js/common.js" type="text/javascript"></script>
		<script src="portal/js/MyDatePicker/WdatePicker.js" defer="defer" type="text/javascript"></script>      
		<link rel="stylesheet" href="portal/css/style.css">
		<script src="../rm/widgets/plugin/js/sea.js"></script>
		<script src="portal/js/json2.js" type="text/javascript"></script>
<!--[if IE 6]>
<script src="portal/js/DDPngMin.js"></script>
<script>DD_belatedPNG.fix('.logo a,.settings a,.icon-edit,.icon-lock,.icon-quit,.icon-cont img,.icon_ibg,.icon-photo-set,.info_right,#goto-top');</script>
<![endif]--> 		
		<style>
		#calendar-mask,#calendar-mask1{width:100%;height:100%;position:absolute;left:0;top:0;}
			.fl {float: left;}
.fr {float: right;}
.hidden{display:none;}
.mt10{margin-top:10px;}
.mt20{margin-top:20px;}
.mb10{margin-bottom:10px;}
.ml10{margin-left:15px;}
.ml8{margin-left:8px;}
.mr10{margin-right:10px;}
.mf20{margin-left:30px;}
.mr20{margin-right:20px;}
.mr30{margin-right:28px;}
.clear{clear:both; height:0; line-height:0; font-size:0; width:100%;}
.wapper{ width:1000px;}

#col,#col1{width: 450px;background: #fff;height:465px;}
.tc_title{width: 450px;height: 50px;background: #3b5586;color: #fff;font-family: "黑体";font-size: 24px;line-height: 50px;text-align: center;}
.rlrl{background:url(http://192.168.0.54:9982/rm/portal/image/date.png) no-repeat 158px center;width:21px;height:21px;}
.add_info {width: 420px;margin: 0 auto;}
.add_info span{float: left;line-height: 25px;font-size: 16px;}
.add_info li{width: 420px;height: 30px;margin-top:10px;line-height:25px;}
.txry{width: 180px;font-size:16px; height:25px;border-radius: 5px;border: solid 2px #e3e4e8;line-height:25px;}
.txry_btn{background:url(http://192.168.0.54:9982/rm/portal/image/xzry.png) no-repeat;height: 33px;width: 102px;border:0px;}
.zycd{height: 32px;width: 185px;border-radius: 5px;border: solid 2px #e3e4e8;line-height: 32px;font-size: 16px;padding-bottom:4px;}
.swsj{width: 180px;height: 25px;border-radius: 5px;border: solid 2px #e3e4e8;}
.swsj img{float: right;margin-top: 10px;margin-right: 5px;}
.swnr{width: 300px;height:40px;border-radius: 5px;border: solid 2px #e3e4e8;line-height: 12px;}

.sjtx{margin-left: 20px;}
.tq{width: 25px;height: 25px;border-radius: 5px;border: solid 2px #e3e4e8;line-height:25px;text-align:center;}
.btn{margin: 0 auto;margin-left:50px;}
.btn_l{margin-top: 10px;margin-left:25px;}
.btn_2{margin-top: 10px;margin-left:85px;}
#fade,#fade1 { 
display:none; 
position:absolute; 
top:0%; 
left:0%; 
width:100%; 
height:1024px; 
background-color:black; 
z-index:1001; 
-moz-opacity:0.5; 
opacity:.50; 
filter:alpha(opacity=50); 
} 
#col,#col1{ 
display:none; 
position:fixed; 
top:20%;
left:33%; 

background-color:white; 
z-index:1002; 
overflow:auto; 
} 
		</style>
	</head>
	<body class='bj'>
		<div class="pass-reset">
			<h3 class="form-title">
				修改密码
				<span class="close_form" id='close_form'>x</span>
			</h3>
		    <div class="pass-form" id="res-box">
				<!--<div class="form-line">
					<span class="line-name">旧密码：</span><input  type="password"  id="oldPwdInput" name="oldPwd" />
				</div>
				<div class="form-line">
					<span class="line-name">新密码：</span><input type="password"  id="newpassInput" name="newpass"/>
				</div>
				<div class="form-line">
					<span class="line-name">确认新密码：</span><input type="password"  id="reNewpassInput" name="reNewPwd" />
					<input type="hidden" id="userNameInput" name="userName" value="${loginName}" />
					<input type="hidden" id="userPwd" name="userPwd" value="${passWord}" />
				</div>
				<div class="form-line">
					<div class="form_btn_box">
						<input type="button" class="sure-btn" onclick="modifyPwd(this);" style="cursor: pointer;"  value="确认修改"><input type="button" class="cancel_btn" style="cursor: pointer;" value="取消">
											
					</div>
				</div> -->
							</div>
		</div>
		<!-- end pass-reset content -->
		<a href="##" title="回到顶部" id="goto-top"></a>
		<!-- end go-top content -->
		<div class="header">
			<div class="container clearfix">
				<h1 class="logo">
					<a href="##" title="崇川区电子政务内网门户">崇川区电子政务内网门户</a>
				</h1>
				<div class="settings">
					<a href="http://192.168.0.54:9982/rm/bz_portal.do" class="settings-item icon-home" style="margin-right:0px;" title="返回主页"></a>
						
					<i class="settings-icon icon-edit" title="信息编辑"></i>
					<i class="settings-item icon-lock" title="修改密码"></i>
					<i class="settings-item icon-quit" title="退出" onclick="sysQuit();"></i>
				</div>
			</div>
		</div>
		<!-- end header content -->
		<div class="page">
			<div class="container clearfix">
				<div class="sideinfo">
					<div class="sideinfo-cont person">
						<div class="sideinfo-cont-body">
							<div class="photo-box">
								<img src="">
								<i class="icon-photo-set"></i>
							</div>							
							<p class="person-name"></p>
							<p class="job"></p>
						</div>
					</div>
						<!--
					<div class="sideinfo-cont work">
						<div class="sideinfo-cont-header">
							<h2 class="cont-title">岗位职责</h2>
						</div>
						<div class="sideinfo-cont-body clearfix">
							<p class="work-desc" limit="25"></p>
							<a href="javascript:;" class="more" id="jjlay">详情&gt;&gt;</a>
						</div>
					</div> -->
					<div class="sideinfo-cont system">
						<div class="sideinfo-cont-header">
							<h2 class="cont-title">业务系统</h2>
						</div>
						<div class="sideinfo-cont-body">

						</div>
					</div>
				</div>
				<!-- end sideinfo content -->
				<div class="main" id="main_change">
				    <div class="main-box">
					<div class="main-header">
						<div class="cont-list-box">
							
						</div>
						<!-- end cont-list-box -->
						<ul class="page-box">

						</ul>
						<!-- end page-box content -->
					</div>
					<div class="cont-edit-box">
						<div class="btn-box clearfix">
							<p class="note">
								<i class="icon-noteinfo"></i>您可以拖动图标来修改顺序哦~
							</p>
							<div class="edit-cancel-btn">取消设置</div>
							<div class="edit-save-btn">保存设置</div>
							<div class="edit-reset-btn">恢复默认</div>
						</div>
					</div>
					<!-- end cont-edit-box content -->					
					<div class="no-drag clearfix">
						
					</div>
					<!-- end no-drag content -->
					<div class="drag-part clearfix">
				  						
					</div>
					<!-- end drag-part content -->
					<div class="main_content3">
					    
					</div>										
				</div>
				<div class="main" id="upcoming" style="display:none;">
				  <div class="main_top">
				    <ul class="tab_list">
						<li>
							<div class="sub_logo "><img src=""></div>
							<div class="sub_title">全部待办</div>
						</li>
						<li>
							123
						</li>
					</ul>
					<div class="sub_link"><a href="javascript:##;" id="backk"><img src="portal/image/backk.png"/></a></div>
				  </div>
				  <div class="main_middle" style=" height:700px;">
				    <iframe src="" height="700" width="100%" frameborder="0"></iframe>
				  </div>				  
				</div>
				<!-- end main content -->
				</div>
			</div>
		</div>
		<!-- end page content -->
		<div class="footer">
			<div class="container clearfix">
				<p>技术支持：江苏中威科技软件系统有限公司</p>
			</div>
		</div>
		<!-- end footer content -->
		<!-- jjlayout-->
		<div class="lay_outer" style="display: none;"></div>
	    <div class="layer" style="display: none;">
		  <div class="lay_top"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" >×</a>岗位职责</div>
		  <div class="lay_content">
		         
		  </div>
		</div>
		<!-- <div class="sidebar-item person-set clearfix">
			<div class="friends sidebar-box">
				<i class="icon-friend"></i>
				<span class="message-num">26</span>
			</div>
			<p>人员设置</p>				
		</div> -->
		<!--<div class="sidebar-item module-set clearfix">
			<div class="modules-manage sidebar-box">
				<i class="icon-modules"></i>
			</div>
			<p>模块设置</p>	-->			
		</div> 
		<!-- end sidebar content -->		
		<div class="modules-manage-box">
			<h4 class="title"><i class="icon-modules"></i>模块设置</h4>
			<div class="module-box">
				<h5 class="module-box-title"><i class="icon-canadd"></i>可添加</h5>
				<ul class="canadd-list">					

				</ul>
			</div>
			<div class="module-box">
				<h5 class="module-box-title"><i class="icon-alreadyadd"></i>已添加</h5>
				<ul class="alreadyadd-list">											
				</ul>
			</div>
			<div class="btn-box clearfix">
				<div class="edit-save-btn">保存设置</div>
				<div class="edit-cancel-btn">取消设置</div>
			</div>
		</div>
		<!-- end modules-manage content -->
		<div class="person-set-box">
			<h4 class="title">通讯录</h4>
		</div>
		<!-- end person-set-box content -->
		<div class="a-bigger">
			<div class="clearfix"><span class="close-box">×</span></div>
			<iframe src="" frameborder="0" style="width: 100%;height: 100%;" scrolling="no"></iframe>
		</div>
		<!-- end a-bigger content -->
		<div class="person-edit clearfix">
			<div class="person-edit-header">头像修改</div>
			<div class="person-edit-body">
			<form id="uploadFacebook" method="post" enctype="MULTIPART/FORM-DATA" target="personframe">
				<div class="edit-row" style="height:170px;">
					<div class="img-box">
						<div id="preview">
							<img src="" id="img-preview">
						</div>
					</div>
					<div class="upload-desc">
						<p>图片上传要求:</p>
						<ul>
							<li><b>图片必须为 .jpg, .png, .bmp等有效格式</b></li>
							<li><b>图片大小必须小于 1024 KB</b></li>
							<li><input type="file" class="choose-img" id="up_img" name="file"/>	</li>
						</ul>
						
					</div>
				</div>
				<div class="edit-row">
					<input type="hidden" name="userId" class="user" value='<%=request.getAttribute("userId")%>'>
					<input type="hidden" name="fileType" value="" class="img-type">
					<div class="person-edit-btn">				
						<input type="submit" class="person-edite-save" value="保存">
						<input type="button" class="person-edite-cancel" value="取消">
					</div>	
				</div>				
			</form>	
			</div>
		</div>
		<!-- end person-set content -->
		<iframe src="" frameborder="0" name="personframe" id="per_iframe" style="display:none;"></iframe>	

<!-- calendar -->
<div id="calendar-mask" style="display:none;z-index:-1;">
<form name="shiwu_for" id="shiwu_for" action="" method="post" style="display: block;" onsubmit="return false;">
	<c:choose>
		<c:when test="${empid eq ''}">
			<input type="hidden" name="empid" id="empid" value="${sessionScope.empid}"/>
		</c:when>
		<c:otherwise>
			<input type="hidden" name="empid" id="empid" value="${empid}"/>
		</c:otherwise>
	</c:choose>
	<input type="hidden" name="empname" id="empname" value="${empname}"/>
	<input type="hidden" name="schedule.year" id="year_h_txt"/>
	<input type="hidden" name="schedule.month" id="month_h_txt"/>
	<input type="hidden" name="schedule.day" id="day_h_txt"/>
	<input type="hidden" name="schedule.id" id="id_h_txt"/>
	<input type="hidden" name="schedule.is_read" id="is_read" value="false"/>
	<input type="hidden" name="schedule.start_all_second" id="start_all_second_txt" value=""/>
	<input type="hidden" name="schedule.end_all_second" id="end_all_second_txt" value=""/>
	<input type="hidden" name="schedule.remain_second" id="remain_second_txt" value=""/>
<div id="col">
	<div class="tc_title">添加事务</div>
	<div class="add_info">
		<ul>
			<li>
				<span>提醒人员</span>
					<input type="hidden" readonly="readonly"  id="remindPersonId" name="remindPersonId" value="${sessionScope.userId}"/>
					<input class="txry fl ml10" type="text" readonly="readonly" id="showperson" name="showperson" value="${sessionScope.userName}"/>
					<input class="txry_btn fl ml10" type="button" onclick="chooseemp()"></input>
			</li>
			<li>
				<span>重要程度</span>
				 <select name="schedule.priority" id="priority" class="zycd ml10 fl">
	  				<option>未指定</option>
	  				<option>重要</option>
	  				<option>次重要</option>
	  				<option>不重要</option>
	  				<option>无关紧要</option>
				 </select>
			</li>
			<li>
				<span>事务时间</span>													
				<input class="txry fl rlrl ml10" name="start_txt" id="start_txt" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"
			onkeydown="return false;"/>
				<!--<img src="image/date.png" />-->
			</li>
			<li>
				<span>事务类型</span>
				 <select name="schedule.type" id="type" class="zycd ml10 fl">
  					<option>工作事务</option>
  					<option>个人事务</option>
  				</select>
			</li>
			<li>
				<span>提醒周期</span>
				<select class="zycd ml10 fl" id="recycletype" name="schedule.recycletype">
					<option value="0">无周期</option>
					<option value="1">每周</option>
					<option value="2">每旬</option>
					<option value="3">每月</option>
					<option value="4">每季度</option>
					<option value="5">每半年</option>
					<option value="6">每年</option>
				</select>
			</li>
			<li style="height:45px;">
				<div class="bt_name fl">
					<p style="line-height: 42px;font-size: 16px;">事务内容</p>
					
				</div>
				<textarea class="swnr fl ml10" name="schedule.content" id="content_txta"></textarea>
			</li>
			<li style="height:45px;">
				<div class="bt_name fl">
					<p style="line-height: 42px;font-size: 16px;">事务备注</p>
		
				</div>
				<textarea class="swnr fl ml10" name="schedule.remark" id="remark_txta"></textarea>
			</li>
			<li>
				<span>时间提醒</span>
				<div class="sjtx fl">
					<div class="_1 fl ml8">
						<span>提前</span>
						<input class="tq fl ml8" id="remind_day" name="schedule.remind_day" type="text" value="0"/>
					</div>
					<div class="_1 fl ml8">
						<span>天</span>
						<input class="tq fl ml8" id="remind_hour" name="schedule.remind_hour" type="text" value="6"/>
					</div>
					<div class="_1 fl ml8">
						<span>小时</span>
						<input class="tq fl ml8" id="remind_minute" name="schedule.remind_minute" type="text" value="0"/>
					</div>
				</div>
				<span style="margin-left:10px;">分钟开始提醒</span>
			</li>
		</ul>
		<div class="btn">
			<input name="schedule.webid"  id="webid" type="hidden" value="${sessionScope.webId}"/>
			<a href="javascript:void(0)"><input class="btn_l fl" type="image" src="http://192.168.0.54:9982/rm/portal/image/btn_qr.png" onclick="subRc()"/></a>
			<a href="javascript:void(0)" id="closebt"><input class="btn_l fl" type="image" src="http://192.168.0.54:9982/rm/portal/image/btn_gb.png"/></a>
		</div>
		
	</div>
</div> 
</form>
<div id="fade"></div> 
</div>

<!-- 查看事务 -->
<div id="calendar-mask1" style="display:none;z-index:-1;">

<div id="col1">
	<div class="tc_title">查看事务</div>
	<div class="add_info">
		<ul>
			<li>
				<span>提醒人员</span>
				
					<input class="txry fl ml10" type="text" readonly="readonly" id="ck_showperson" name="showperson" value="${sessionScope.userName}"/>
			</li>
			<li>
				<span>重要程度</span>
				 <select name="schedule.priority" id="ck_priority" class="zycd ml10 fl" disabled="disabled">
	  				<option value="未指定">未指定</option>
	  				<option value="重要">重要</option>
	  				<option value="次重要">次重要</option>
	  				<option value="不重要">不重要</option>
	  				<option value="无关紧要">无关紧要</option>
				 </select>
			</li>
			
			
			<li>
				<span>事务时间</span>													
				<input class="txry fl rlrl ml10" name="start_txt" id="ck_start_txt" type="text" readonly="readonly"/>
				<!--<img src="image/date.png" />-->
			</li>
			<li>
				<span>事务类型</span>
				 <select name="schedule.type" id="ck_type" class="zycd ml10 fl" disabled="disabled">
  					<option>工作事务</option>
  					<option>个人事务</option>
  				</select>
			</li>
			<li>
				<span>提醒周期</span>
				<select class="zycd ml10 fl" id="ck_recycletype" name="schedule.recycletype" disabled="disabled">
					<option value="0">无周期</option>
					<option value="1">每周</option>
					<option value="2">每旬</option>
					<option value="3">每月</option>
					<option value="4">每季度</option>
					<option value="5">每半年</option>
					<option value="6">每年</option>
				</select>
			</li>
			<li style="height:45px;">
				<div class="bt_name fl">
					<p style="line-height: 42px;font-size: 16px;">事务内容</p>
					
				</div>
				<textarea class="swnr fl ml10" name="schedule.content" id="ck_content_txta" style="font-size: 16px;"  readonly="readonly"></textarea>
			</li>
			<li style="height:45px;">
				<div class="bt_name fl">
					<p style="line-height: 42px;font-size: 16px;">事务备注</p>
		
				</div>
				<textarea class="swnr fl ml10" name="schedule.remark" id="ck_remark_txta" style="font-size: 16px;"  readonly="readonly"></textarea>
			</li>
			<li>
				<span>时间提醒</span>
				<div class="sjtx fl">
					<div class="_1 fl ml8">
						<span>提前</span>
						<input class="tq fl ml8" id="ck_remind_day" name="schedule.remind_day" type="text" value="0" readonly="readonly"/>
					</div>
					<div class="_1 fl ml8">
						<span>天</span>
						<input class="tq fl ml8" id="ck_remind_hour" name="schedule.remind_hour" type="text" value="6" readonly="readonly"/>
					</div>
					<div class="_1 fl ml8">
						<span>小时</span>
						<input class="tq fl ml8" id="ck_remind_minute" name="schedule.remind_minute" type="text" value="0" readonly="readonly"/>
					</div>
				</div>
				<span style="margin-left:10px;">分钟开始提醒</span>
			</li>
		</ul>
		<div class="btn">
			<a href="javascript:void(0)" id="closebt1"><input class="btn_2 fl" type="image" src="http://192.168.0.54:9982/rm/portal/image/btn_gb.png"/></a>
		</div>
		
	</div>
</div> 

<div id="fade1"></div> 
</div>

<!-- calerdar end -->		
<script>
					//退出系统
function sysQuit(){
	if(confirm("是否确认退出?"))
	{
		var functionurl ="<%=SystemParamConfigUtil.getParamValueByParam("functionsurl")%>";
		var workflowurl ="<%=SystemParamConfigUtil.getParamValueByParam("workflowurl")%>";
		var urll=functionurl+"/bbs/bbs_clearSession.do";
		//alert(urll);
		/*清除 functions session*/
		 $.ajax({
			  type: "POST",
			  url: urll,
			  success: function (mes){
				//  alert("finctions success!");
			  }
			}); 
		
		 /*清除 rm session*/
		 $.ajax({
			  type: "POST",
			  url: "${ctx}/bz_clearSession.do",
			  success: function (mes){
				//  alert("rm success!");
			  }
			}); 
		
		window.location =  "<%=SystemParamConfigUtil.getParamValueByParam("Logout")%>";
    //	window.location ="http://192.168.0.54:9982/rm/bz_portal.do";
	}
	
}
$(function(){
  //var wd=$('#tpid').width();
  //$('#tpid').picturePl({width:wd, height:285, proportion:true, numberWs:16, fontSize:12, fontBoder:"bold",time:3000});
 
// calendar
var linkbt=$("#linkbt"),
	col=$('#col'),col1=$('#col1'),
	fade=$('#fade'),fade1=$('#fade1'),
	closebt=$("#closebt"),closebt1=$("#closebt1");
	
	closebt.on('click', function(){ 
		col.hide();
		fade.hide();
		$('#calendar-mask').hide().css({
			'z-index' : -1
		});
		return false;
	});
	closebt1.on('click', function(){ 
		col1.hide();
		fade1.hide();
		$('#calendar-mask1').hide().css({
			'z-index' : -1
		});
		return false;
	});
});
//function myFunction(){window.location.reload();}  

function displaySchdule(formattedDate){
	$('#calendar-mask').show().css({
		'z-index' : 0
	});
	$('#start_txt').val(formattedDate);
	$('#col').show();
	$('#fade').show().css({
		height : $(document).height() + 'px'
	});
	
}
function displayCkSchdule(msg){
	$('#calendar-mask1').show().css({
		'z-index' : 0
	});
	//$('#start_txt').val(formattedDate);
	//$("#ck_showperson").val(msg.showperson);
	$("#ck_priority").val(msg.priority);
	$("#ck_start_txt").val(msg.startTime);
	$("#ck_type").val(msg.type);
	
	$("#ck_recycletype").val(msg.recycletype);
	
	$("#ck_content_txta").val(msg.content);
	$("#ck_remark_txta").val(msg.remark);
	
	$("#ck_remind_day").val(msg.remind_day);
	
	$("#ck_remind_hour").val(msg.remind_hour);
	$("#ck_remind_minute").val(msg.remind_minute);
	
	$('#col1').show();
	$('#fade1').show().css({
		height : $(document).height() + 'px'
	});
	
}

function chooseemp(){
	var winoption ="dialogHeight:800px;dialogWidth:600px;status:no;scroll:no;resizable:no;center:yes";
	var ids = $("#remindPersonId").val();
	var names = $("#showperson").val();
	var obj = '';
    if(ids) {
		obj = getIdsNamesObj(ids,names);
	} 
    
    /*layer.open({
        area: ['600px', '800px'],
        type: 2,
       	// btn: ['确认', '取消'],
        content: '${functionsurl}/day_getSettingTree.do?isSingle=issingle'
    });*/
    
    //wait
	var ret=window.showModalDialog("${functionsurl}/day_getSettingTree.do?isSingle=issingle&t="+new Date().getTime(),obj,winoption);	
	if(ret){
		var userName = '';
        var userId = '';
        var userIdName = '';
        var group = '';
        $.each(ret.data,function(k,v){
            if(v.name!=""){
            	userName = v.name;
            	userId = v.value;
            	userIdName +=v.value+","+v.name;
                }
        	
		}); 
        $("#remindPersonId").val(userId);
        $("#showperson").val(userName===''?'':userName);
	}
}

function chooseemp1(){
	var winoption ="dialogHeight:800px;dialogWidth:600px;status:no;scroll:no;resizable:no;center:yes";
	//var ids = $("#remindPersonId").val();
	//var names = $("#showperson").val();
	var obj = '';
    //if(ids) obj = getIdsNamesObj(ids,names); 
    
    //wait
	var ret=window.showModalDialog("${functionsurl}/day_getSettingTree.do?t="+new Date().getTime(),obj,winoption);	
	if(ret){
	
		var userNames = '';
		var userIds = '';
		$.each(ret.data, function(k, v) {
			userNames += v.name + ",";
			userIds += v.value + ",";
		});
		
        $("#partempId").val(userIds.substring(0, userIds.length - 1));
        $("#partempName").val(userNames.substring(0, userNames.length - 1));
	}
}

function subRc() {
	
	setSecond('start_txt','start_txt','start_all_second_txt','end_all_second_txt');
	g('remain_second_txt').value=parseInt(g('remind_day').value,10)*24*60*60*1000+parseInt(g('remind_hour').value,10)*60*60*1000+parseInt(g('remind_minute').value,10)*60*1000;
	var objs = {};
	objs['empid'] = $("#empid").val();
	objs['empname'] = $("#empname").val();
	objs['remindPersonId'] = $("#remindPersonId").val();
	objs['showperson'] = $("#showperson").val();
	objs['priority'] = $("#priority").val();
	if($("#start_txt").val()) {
		objs['start_txt'] = $("#start_txt").val();
	} else {
		alert('"事务时间"不能为空！');
		return
	}
	objs['type'] = $("#type").val();
	objs['recycletype'] = $("#recycletype").val();
	if($("#content_txta").val()){
		objs['content_txta'] = $("#content_txta").val();
	}else{
		alert('"事务内容"不能为空！');
		return
	}
	objs['remark_txta'] = $("#remark_txta").val();
	
	if(check()){
		objs['remind_day'] = $("#remind_day").val();
		objs['remind_hour'] = $("#remind_hour").val();
		objs['remind_minute'] = $("#remind_minute").val();
	}
	objs['is_read'] = $("#is_read").val();
	objs['start_all_second_txt'] = $("#start_all_second_txt").val();
	objs['end_all_second_txt'] = $("#end_all_second_txt").val();
	objs['remain_second_txt'] = $("#remain_second_txt").val();
	objs['webid'] = $("#webid").val();

	//var url = "http://192.168.7.43:9682/functions_ntcc/day_save4portal.do?callback=?";
	var url = "${functionsurl}/day_save4portal.do?callback=?";
	
	$.ajax({
		type : "get",
		async:false, 
		url : url,
		dataType : "json",
		data : objs,
		error : function(XMLHttpRequest, textStatus, errorThrown){
		
	       },
		success : function(json) {
			//通过url匹配日程iframe
			$("#content_txta").val('');
			$("#remark_txta").val('');
			var aIframe = $('iframe');
			aIframe.each(function (){
				var src = $(this).attr('src');
				if (src && src.indexOf('month_toPortalMonth') >= 0) {
					$(this).attr('src', src);
					$('#closebt').click();
				}
			});
		}
	});
}

//通用-把时间转换为秒数
function setSecond(start_txt,end_txt,start_all_second_txt,end_all_second_txt){
	var start_txt=g(start_txt).value;
	var end_txt=g(end_txt).value;
	var start_datestr=start_txt.substr(0,start_txt.length-7);
	var start_timestr=start_txt.substr(start_txt.length-5,start_txt.length);
	var end_datestr=end_txt.substr(0,end_txt.length-7);
	var end_timestr=end_txt.substr(end_txt.length-5,end_txt.length);
	
	var start_year=start_datestr.split('-')[0];
	var start_month=start_datestr.split('-')[1];
	var start_day=start_datestr.split('-')[2];
	var start_hour=start_timestr.split(':')[0];
	var start_minute=start_timestr.split(':')[1];
	
	var end_year=end_datestr.split('-')[0];
	var end_month=end_datestr.split('-')[1];
	var end_day=end_datestr.split('-')[2];
	var end_hour=end_timestr.split(':')[0];
	var end_minute=end_timestr.split(':')[1];
	
	//alert(start_year+'-'+start_month+'-'+start_day+'-'+start_hour+'-'+start_minute+'-'+end_year+'-'+end_month+'-'+end_day+'-'+end_hour+'-'+end_minute);
	var mydate=new Date(start_year,start_month-1,start_day,start_hour,start_minute,0);
	var mydate1=new Date(end_year,end_month-1,end_day,end_hour,end_minute,0);
	g(start_all_second_txt).value=mydate.getTime();
	g(end_all_second_txt).value=mydate1.getTime();
}

function g(id){
	return document.getElementById(id);
}

function check(){
	//数字判断
	var isnumberArrId=new Array("remind_day","remind_hour","remind_minute");
	var isnumberArrName=new Array("天数","小时","分钟");
	var reg = /\D/;
	for(var j=0;j<isnumberArrId.length;j++){
		var isnumberObj=g(isnumberArrId[j]);
		if(isnumberObj.value.match(reg)!=null){
			isnumberObj.select();
			//alert(isnumberArrName[j]+"  必须为数字(例:200)");
			alert("提醒时间必须为数字(例:200)");
			return false;
		}
	}
	//数字范围控制
	var remind_day=parseInt(g('remind_day').value,10);
	var remind_hour=parseInt(g('remind_hour').value,10);
	var remind_minute=parseInt(g('remind_minute').value,10);
	if(remind_day<0||remind_day>365){
		alert('天数范围应在0到365之间');
		g('remind_day').select();
		return false;
	}
	if(remind_hour<0||remind_hour>23){
		alert('小时范围应在0到23之间');
		g('remind_hour').select();
		return false;
	}
	if(remind_minute<0||remind_minute>59){
		alert('分钟范围应在0到59之间');
		g('remind_minute').select();
		return false;
	}
	return true;
}

//人员树使用的已选数据json拼接
function getIdsNamesObj(ids,names){
	var obj = {};
    if( ids != null && names != null && ids!='' && names != ''){
    	ids=ids.indexOf(',')>-1?ids.split(','):[ids];
    	names=names.indexOf(',')>-1?names.split(','):[names];
    	for(var i=0,l=ids.length;i<l;i++){
        	if(names[i]!=null && names[i]!='' && ids[i]!=null && ids[i]!=''){
	    		obj[ids[i]]= new Object;
	    		obj[ids[i]].name = names[i];
	    		obj[ids[i]].value = ids[i];
	    		obj[ids[i]].text = names[i];
	    		obj[ids[i]].type = "item";
        	}
    	}
    }
    return obj;
}
function queryCalendarMaxLinkItem () {		
	var oTarget = null;
	var oMain = $('#main_change') || $(window.top.document).find('#main_change');
	var aHeader = oMain.find('.drag>.cont>.cont-header');
	var subsElems = aHeader.find(".operate > span.biggest");
	subsElems.each(function (){
		var href = $(this).attr('maxLink');
		if (href && href.indexOf('day_toDay') != -1) {
			oTarget = $(this);
		}
	});
	return oTarget;
}

function resetCalendarLink(thisDay) {
	var targetElem = queryCalendarMaxLinkItem();
	if (targetElem) {
		var params = thisDay.split("/");
		var newLink = "http://192.168.0.54:9984/functions/day_toDay.do?year=" + params[2] + "&month=" + params[1] + "&day=" + params[0] + "&state1=0&state2=0";
		targetElem.attr('maxLink', newLink);
	}
}

//重新
</script>
	</body>
	
	
	
</html>