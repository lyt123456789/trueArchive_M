<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <link href="${ctx}/assets-common/css/common.css" rel="stylesheet" type="text/css" media="screen"/>
</head>
<body>
    <div class="tw-layout">
        <div class="tw-container">
            <form method="get" id="form" name="form" action="#" class="tw-form">
            <table class="tw-table tw-table-form">
                <colgroup>
                    <col width="40%" />
                    <col />
                </colgroup>
                <tr>
                    <th><em>*</em>退回节点</th>
                    <td>
                    	<select class="tw-form-select" id="nodeId" name="nodeId">
                    		<option value=""></option>
                    		<c:forEach items="${list}" var="node">
                    			<option value="${node.wfn_id}">${node.wfn_name}</option>
                    		</c:forEach>
                    	</select>
                    </td>
                </tr>
                <tr>
                    <th>是否发送短信</th>
                    <td>
                    	<input type="checkbox" id="sendMsg" name="sendMsg"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                    	<a class="tw-btn-primary tw-btn-lg" onclick="sub();">
                        	<i class="tw-icon-send"></i> 提交
                    	</a>
	                    <a class="tw-btn tw-btn-lg" onclick="window.close();">
	                        <i class="tw-icon-minus-circle"></i> 取消
	                    </a>
					</td>
                </tr>
            </table>
            </form>
        </div>
    </div>
    <script type="text/javascript">
    	function sub(){
    		var nodeId = $('#nodeId option:selected').val();
    		var checkObj = document.getElementById("sendMsg");
    		if(null == nodeId || '' == nodeId){
    			alert("清选择需要退回的节点。");
    			return;
    		}
    		
    		var sendMsg = '0';
    		if(null != checkObj && checkObj.checked){
    			sendMsg = '1';
    		}
    		
    		if(top.window && top.window.process && top.window.process.type){
                var remote = top.window.nodeRequire('remote');
	            var browserwindow = remote.require('browser-window');
	            var win = browserwindow.fromId(parseInt($.Request('focusedId')));
				if(win){
					win.webContents.send('message-departmentTree',nodeId+';'+sendMsg);
	                
                }
            }else{
            	opener.window.returnValue = nodeId+';'+sendMsg;
			    window.close();
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
</body>
</html>
