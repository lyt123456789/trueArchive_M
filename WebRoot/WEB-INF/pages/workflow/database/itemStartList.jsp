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
<!--页面样式-->
<style type="text/css">
		table tr th{
			font-size:16px!important;
		}
	</style>
</head>
<body>
<div class="wf-layout">
	<form name="doItemList"  id="doItemList" action="${ctx }/item_getItemList.do" method="post">
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<table class="wf-fixtable" layoutH="140">
			<thead>
	    	<tr>
		    	<th width="100%">事项名称</th>
	    	</tr>
	    	</thead>
	    	<c:forEach items="${itemList}" var="d" varStatus="n">
			    <tr target="sid_user" rel="1">
				    <td align="center">
				    	<a href="javascript:sub('${d.id}','${d.link}','${d.title}');">
					    	${d.title}
						</a>
					</td>
			    </tr>
	    	</c:forEach>
		</table>
	</div>
	</form>
</div>
<script type="text/javascript">
	function sub(url,id,title){
		if(top.window && top.window.process && top.window.process.type){
            var remote = top.window.nodeRequire('remote');
            var browserwindow = remote.require('browser-window');
            var win = browserwindow.fromId(parseInt($.Request('focusedId')));
			if(win){
                win.webContents.send('message-departmentTree',url+"##"+id+"##"+title);
            }
        }else{
        	opener.window.returnValue = url+"##"+id+"##"+title;
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