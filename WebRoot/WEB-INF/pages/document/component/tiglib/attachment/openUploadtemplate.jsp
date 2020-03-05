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
	<link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="tw-layout">
        <div class="tw-container">
			<table class="tw-table tw-table-form">
				<colgroup>
                    <col  width="25%" />
                    <col />
                </colgroup>
				<tr>
					<th>正文模板：</th>
					<td>
						<select name="template" id="template" class="tw-form-select">
							<c:if test="${fn:length(temList) gt 1}">
							<option value="">--请选择--</option>
							</c:if>
							<c:forEach items="${temList}" var="template">
								<option value="${template.id}">${template.vc_cname}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
                    <th></th>
                    <td>
                    	<button id="btnSuperSearch" class="tw-btn-primary tw-btn-lg" type="button" onclick="addTemplate();">
                       		 <i class="tw-icon-send"></i> 确定
                   		 </button>
                   		 <button class="tw-btn tw-btn-lg" onclick="closeWin();">
                        	<i class="tw-icon-minus-circle"></i> 取消
                   		 </button>
                    </td>
                </tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	function addTemplate(){
		var temId = document.getElementById('template').value;
		$.ajax({  
			url : 'attachment_addTemplateForSendDoc.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :{
				'docguid':'${docguid}',
				'temId':temId,
				'nodeId':'${nodeId}'
			},
			error : function(e) {  
				alert('AJAX调用错误(attachment_addTemplateForSendDoc.do)');
			},
			success : function(msg) {
				if(top.window && top.window.process && top.window.process.type){
			          var remote = top.window.nodeRequire('remote');
			          var browserwindow = remote.require('browser-window');
			          var win = browserwindow.fromId(parseInt($.Request('focusedId')));
			          if(win){
			              win.webContents.send('msg-openUploadTemplate',msg);
			          }
			      }else{
			      	window.returnValue=msg;
			      	window.close();
			      }
			}
		});
	}
	
	function closeWin(){
		window.close();
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
