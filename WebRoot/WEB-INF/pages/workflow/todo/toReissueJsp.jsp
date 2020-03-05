<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<link rel="stylesheet" href="${ctx}/assets-common/css/common.css" type="text/css" />
</head>
<body>
<div class="tw-layout">
	<div class="tw-container">
	<form id="itemform" method="post" action="${ctx}/item_edit.do" style="font-family: 宋体; font-size: 12pt;">
	<input type="hidden" id="fprocessid" name="fprocessid" value="${fprocessid}">
		<table class="tw-table-form tm-table-form">
			<colgroup>
				<col  width="10%" />
				<col />
			</colgroup>
			<tr>
				<th>待办名称：</th>
				<td>${title}</td>
			</tr>
			<tr>
				<th>主送单位：</th>
				<td>
					<textarea class="tw-form-textarea" id="zsdwName" name="zsdwName" readonly="readonly"></textarea>
					<input type="hidden" id="zsdwId" name="zsdwId">
					<a class="tw-btn-primary" href="javascript:getDepId('zsdwId', 'zsdwName');"><i class="tw-icon-user"></i> 选择</a>
				</td>
			</tr>
			<tr>
				<th>抄送单位：</th>
				<td>
					<textarea class="tw-form-textarea" id="csdwName" name="csdwName" readonly="readonly"></textarea>
					<input type="hidden" id="csdwId" name="csdwId">
					<a class="tw-btn-primary" href="javascript:getDepId('csdwId', 'csdwName');"><i class="tw-icon-user"></i> 选择</a>
				</td>
			</tr>
			<tr>
				<td></td>
                <td>
                	<a id="btnSuperSearch" class="tw-btn-primary tw-btn-lg" href="javascript:checkForm();">
                    	<i class="tw-icon-send"></i> 提交
                	</a>
                	<a class="tw-btn tw-btn-lg" href="javascript:window.close();">
                    	<i class="tw-icon-minus-circle"></i> 取消
                	</a>
                </td>
			</tr>
		</table>
	</form>
	</div>
</div>
</body>
<script type="text/javascript">
function checkForm(){
	//获取主送抄送id
	var zsdwName = $("#zsdwName").val();
	var zsdwId = $("#zsdwId").val();
	var csdwName = $("#csdwName").val();
	var csdwId = $("#csdwId").val();
	var fprocessid = $("#fprocessid").val();
	
	if((zsdwId==null || zsdwId=='') && (csdwId==null || csdwId=='')){
		alert("请选择主送或抄送单位");
		return;
	}
	//ajax异步去提交数据
	$.ajax({   
		url : '${ctx }/table_reissueDofileReceive.do',
		type : 'POST',   
		cache : false,
		async : false,
		error : function() {  
			alert('AJAX调用错误(table_reissueDofileReceive.do)');
		},
		data : {
			'zsdwName':zsdwName, 'zsdwId':zsdwId, 'csdwName':csdwName, 'csdwId':csdwId, 'fprocessid':fprocessid
		},    
		success : function(result) { 
			if(result!=null && result=='success'){
				alert("补发成功！");
				parent.layer.closeAll();
			}
		}
	});
}


function getDepId(tagId, tagName){
	/* var ret = window.showModalDialog('${ctx}/selectTree_showDepartment.do?isHtml=1&fprocessid=${fprocessid}&t='+new Date(),null,"dialogTop=120;dialogLeft=200;dialogWidth=1000px;dialogHeight=500px;help:no;status:no;scroll:yes;");
	if(ret!=null){
		var ids = ret.split('*');
		$("#"+tagId).val(ids[0]);
		$("#"+tagName).val(ids[1]);
	} */
	var url4UserChose='${curl}/selectTree_showDepartment.do?isHtml=1&fprocessid=${fprocessid}&t='+new Date();
	var url = url4UserChose;
	var WinWidth = 1000;
	var WinHeight = 500;
	if(top.window && top.window.process && top.window.process.type){
		console.info("封装打开方式");
	    var ipc = top.window.nodeRequire('ipc');
	    var remote = top.window.nodeRequire('remote');
	    var browserwindow = remote.require('browser-window');
        var winProps = {};
        winProps.width = '1000';
        winProps.height = '500';
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
				var ret = args;
				if(ret){
					var ids = ret.split('*');
					$("#"+tagId).val(ids[0]);
					$("#"+tagName).val(ids[1]);
				}
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
			    var ret = window.returnValue;
				if(ret){
					var ids = ret.split('*');
					$("#"+tagId).val(ids[0]);
					$("#"+tagName).val(ids[1]);
				}
		    }
	    },500);
	}
}
</script>
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
<%@ include file="/common/function.jsp"%>
</html>