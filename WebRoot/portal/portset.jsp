<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<title>门户设置</title> 
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${curl}/assets/css/style.css">
<link rel="stylesheet" href="${curl}/assets/css/font-awesome.min.css">
<script type="text/javascript" src="${curl}/assets/js/jquery-1.11.1.min.js"></script>
<script src="${curl}/assets/js/jquery.form.js" type="text/javascript"></script>
</head>
<body>
	<div class="tw-section" style="position: relative;">
		<!-- <span style=" position: absolute;right: 5px;top: 5px;cursor: pointer; color:#2e9fd1" onclick="cancel();" title="关闭"><i class="fa fa-2x fa-times-circle"></i></span>
		 --><div class="tw-grid">
			<div class="tw-tabs" style="margin-top:10px;">
		        <ul class="tw-tabs-nav">
		            <!-- <li class="tw-nav-item">
		            	<a href="javascript: void(0)">密码修改</a>
		            </li> -->
		            <li class="tw-nav-item">
		            	<a href="javascript: void(0)">发起事项的配置</a>
		            </li>
		            <li class="tw-nav-item">
		            	<a href="javascript: void(0)">框架配置功能</a>
		            </li>
		        </ul>
		        <div class="tw-tabs-bd">
		            <%-- <div class="tw-tab-panel">
		            	<div class="tw-panel-cnt">
		                    <iframe src="${webconsole}/user/modifyPwdPage" id="pass_res" style="width: 100%;height: 420px;" frameborder="0" scrolling="no"></iframe>
		            	</div>
		            </div> --%>
		            <div class="tw-tab-panel">
		            	<div class="tw-panel-cnt">
							<div class="tw-sortable-wrap">
								<div class="tw-sortable-mod">
									<div class="tw-sortable-hd">
										<i class="fa fa-check-circle fa-lg"></i><br />可<br />添<br />加
									</div>
									<div class="tw-sortable-bd">
										<ul class="tw-sortable-list" name="fqsxwpx">
										</ul>
									</div>
								</div>
								<div class="tw-sortable-mod tw-sortable-have">
									<div class="tw-sortable-hd">
										<i class="fa fa-check-circle fa-lg"></i><br />已<br />添<br />加
									</div>
									<div class="tw-sortable-bd">
										<ul class="tw-sortable-list" name="fqsxypx">
										</ul>
									</div>
								</div>
 								<div class="tw-sortable-btns">
									<input type="button" class="tw-btn tw-btn-lg tw-btn-primary" value="保存设置"  onclick="savefqsxSet();">
								</div>
							</div>
		            	</div>
		            </div>
		            <div class="tw-tab-panel">
		            	<div class="tw-panel-cnt">
							
							<div class="tw-sortable-wrap tw-sortable-column">
								<div class="tw-sortable-mod">
									<div class="tw-sortable-hd">
										<i class="fa fa-check-circle fa-lg"></i><br />可<br />添<br />加
									</div>
									<div class="tw-sortable-bd">
										<ul class="tw-sortable-list" name="yskwpx">
										</ul>
									</div>
								</div>

								<div class="tw-sortable-mod tw-sortable-have">
									<div class="tw-sortable-hd">
										<i class="fa fa-check-circle fa-lg"></i><br />已<br />添<br />加
									</div>
									<div class="tw-sortable-bd">
										<ul class="tw-sortable-list" name="yskypx">
										</ul>
									</div>
								</div>
								<div class="tw-sortable-btns">
									<input type="button" class="tw-btn tw-btn-lg tw-btn-primary" value="保存设置" onclick="saveYwkSet();">
								</div>
							</div>
		            	</div>
		            </div>
		        </div>
		    </div>
		</div>
	</div>
<script type="text/javascript">
	$(document).ready(function() {
		$(".tw-sortable-list").sortable({
			connectWith:".tw-sortable-list",
			placeholder: "tw-sortable-placeholder"
		}).disableSelection();
		//发起事项未添加初始化
		$(".tw-sortable-list[name='fqsxwpx']").html("");
		$.ajax({
			url : '${ctx}/portalInterface_getModuleMenuListJson.do?isadd=2&userId=${employee.employeeGuid}',
			type : 'POST',
			dataType : "jsonp",
			jsonp : "callback",
			cache : false,
			async : false,
			error : function() {
				alert('提示：查询未添加事项失败！');
			},
			success : function(result) {
				if (result.data == "") {
				} else {
					$(".tw-sortable-list[name='fqsxwpx']").html(result.data);
				}
			}
		});
		//发起事项已排序初始化
		$(".tw-sortable-list[name='fqsxypx']").html("");
		$.ajax({
			url : '${ctx}/portalInterface_getModuleMenuListJson.do?isadd=1&userId=${employee.employeeGuid}',
			type : 'POST',
			dataType : "jsonp",
			jsonp : "callback",
			cache : false,
			async : false,
			error : function() {
				alert('提示：查询已添加事项失败！');
			},
			success : function(result) {
				if (result.data == "") {
				} else {
					/* console.log(result.data); */
					$(".tw-sortable-list[name='fqsxypx']").html(result.data);
				}
			}
		});
		//元素框未排序
		$(".tw-sortable-list[name='yskwpx']").html("");
		$.ajax({
			url : '${ctx}/portalInterface_getElementMenuListJson.do?isadd=2&userId=${employee.employeeGuid}',
			type : 'POST',
			dataType : "jsonp",
			jsonp : "callback",
			cache : false,
			async : false,
			error : function() {
				alert('提示：未添加元素查询失败！');
			},
			success : function(result) {
				if (result.data == "") {
				} else {
					$(".tw-sortable-list[name='yskwpx']").html(result.data);
				}
			}
		});
		//元素框未排序
		$(".tw-sortable-list[name='yskypx']").html("");
		$.ajax({
			url : '${ctx}/portalInterface_getElementMenuListJson.do?isadd=1&userId=${employee.employeeGuid}',
			type : 'POST',
			dataType : "jsonp",
			jsonp : "callback",
			cache : false,
			async : false,
			error : function() {
				alert('提示：已添加元素查询失败！');
			},
			success : function(result) {
				if (result.data == "") {
				} else {
					$(".tw-sortable-list[name='yskypx']").html(result.data);
				}
			}
		});
	});
	function saveUserInfo(){
		$.ajax({
            type: "POST",
            url:"${ctx}/portalHome_updateUserInfo.do",
            data:$('#userinfoForm').serialize(),// 你的formid
            async: false,
            error: function(e) {
            	alert(e);
            },
            success: function(data) {
            	var res = eval("("+data+")");
 				if(res.success){
 					alert("保存成功");
 					window.parent.iframeSaveClose();
 				}else{
 					alert(res.error);
 				}
            }
        });
	}
	
	function cancel(){
		window.parent.iframecancel();
	}
	
	function savefqsxSet(){
		var fqsx='';
		var fqsxypx='';
		$("ul[name='fqsxwpx']>li").each(function(){
		    var id=$(this).attr("id");
		    fqsx=fqsx+","+id;
		  });
		$("ul[name='fqsxypx']>li").each(function(){
		    var id=$(this).attr("id");
		    fqsxypx=fqsxypx+","+id;
		  });
		if(fqsx!=''){
			fqsx=fqsx.substring(1, fqsx.length);
		}
		if(fqsxypx!=''){
			fqsxypx=fqsxypx.substring(1, fqsxypx.length);
		}
		$.ajax({
            type: "POST",
            url:'${ctx}/portalHome_updateModuleMenuInfo.do?userid=${employee.employeeGuid}&fqsx='+fqsx+'&fqsxypx='+fqsxypx,
            async: false,
            error: function(e) {
            	alert(e);
            },
            success: function(data) {
            	var res = eval("("+data+")");
 				if(res.success){
 					alert("保存成功");
 					var selfIndex = parent.layer.getFrameIndex(window.name);
 					parent.layer.close(selfIndex);
 				}else{
 					alert(res.error);
 				}
            }
        });
	}
	
	function saveYwkSet(){
		var ysk='';
		var yskypx='';
		$("ul[name='yskwpx']>li").each(function(){
		    var id=$(this).attr("id");
		    ysk=ysk+","+id;
		  });
		if(ysk!=''){
			ysk=ysk.substring(1, ysk.length);
		}
		$("ul[name='yskypx']>li").each(function(){
		    var id=$(this).attr("id");
		    yskypx=yskypx+","+id;
		  });
		if(yskypx!=''){
			yskypx=yskypx.substring(1, yskypx.length);
		}
		$.ajax({
            type: "POST",
            url:'${ctx}/portalHome_updateElementMenuInfo.do?userid=${employee.employeeGuid}&ysk='+ysk+'&yskypx='+yskypx,
            async: false,
            error: function(e) {
            	alert(e);
            },
            success: function(data) {
            	var res = eval("("+data+")");
 				if(res.success){
 					alert("保存成功");
 					var selfIndex = parent.layer.getFrameIndex(window.name);
 					parent.layer.close(selfIndex);
 				}else{
 					alert(res.error);
 				}
            }
        });
	}
</script>
<script type="text/javascript" src="${curl}/assets/js/common.js"></script>
<script type="text/javascript" src="${curl}/assets/plugins/jquery.backstretch.js"></script>
<script type="text/javascript" src="${curl}/assets/plugins/jquery-ui-1.10.4.min.js"></script>
</body>
</html>
