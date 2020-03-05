<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
<title>查看历史</title>
<%@ include file="/common/headerindex.jsp"%>
<!--表单样式-->
<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
<style>html,body{margin:0;padding:0;overflow-x:hidden}</style>
</head>
<script type="text/javascript">
	if(top.window && top.window.process && top.window.process.type){
	    var imported = document.createElement('script');
		imported.src = '${ctx}/common-assets/js/eleCommon.js';
		document.head.appendChild(imported);
	}
</script>
<body>
<table class="list" >
	<tr>
		<td align="center" width="200">修改时间</td>
		<td align="center" width="200">修改人</td>
		<td align="center" width="200"></td>
	</tr>
	<c:forEach items="${attHistoryList}" var="att">
		<tr>
			<td align="center"><fmt:formatDate value="${att.filetime}"  type="both" pattern="yyyy-MM-dd HH:mm"/></td>
			<td align="center"><c:choose><c:when test="${fn:contains(att.editer,';')}">${fn:split(att.editer,';')[1]}</c:when><c:otherwise>${att.editer}</c:otherwise></c:choose></td>
			<td align="center"><input type="button" value="查看内容" onclick="view('${att.localation}','${att.id}')"></td>
		</tr>
	</c:forEach>
</table>
	<script>
		function view(location,id){
		   /*  var sheight = screen.height-70;
		  	var swidth = screen.width-10;
		  	var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes";
		    var url = "${ctx}/attachment_viewDoc.do?filelacation=" + location + "&t=" + new Date().getTime();
			window.showModalDialog(url,"", winoption); */
			var WinHeight = screen.height-70;
			var WinWidth = screen.width-10;
			var url = "${curl}/attachment_viewDoc.do?histroyid=" + id + "&filelacation=" + location + "&t=" + new Date().getTime();
			if(top.window && top.window.process && top.window.process.type){
			    var ipc = top.window.nodeRequire('ipc');
			    var remote = top.window.nodeRequire('remote');
			    var browserwindow = remote.require('browser-window');
			    var win = new browserwindow(
			    		{
			    			width:WinWidth,
			    			height:WinHeight,
			    			'web-preferences':{
			    				'plugins':true
			    			}
			    		});
		        win.loadUrl(url);
			    //win.openDevTools();
			    ipc.on('viewHistory',function(args){
					if(win){
		                win.close();
						win = null;
					}
			    });
			}else{
			    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
			    var loop = setInterval(function(){
				    if(winObj.closed){
						console.info(window.returnValue);
					    clearInterval(loop);
					    //---------------------------
				        //------------------------------
				    }
			    },500);
			}
	 	}
	</script>
</body>

</html>
