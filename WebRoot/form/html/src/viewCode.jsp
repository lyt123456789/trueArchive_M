<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerindex.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>目录</title>
		<style type="text/css">
			a {
				text-decoration:none;
			} 
		</style>
	</head> 
	<base target="_self"/>   
	<body TOPMARGIN="0" LEFTMARGIN="1" BOTTOMMARGIN="0" RIGHTMARGIN="1">
		<form id="form1" action="${ctx }/form_saveStringToHTML4Code.do" method="post" >
			<textarea id="SQLContent" name="htmlString" value="" wrap="off" style="width:900px; height:468px;margin: auto;"><c:out value="${htmlString }" escapeXml="true"></c:out></textarea>
			<div align="center" class="cbo" style="padding-left:400px;padding-top:5px;">  
			<c:if test="${empty isview}">
				<div class="buttonActive" style="margin-right: 10px;"><div class="buttonContent" align="center" ><button id="CmdSave" onclick="sub()" type="submit">保存</button></div></div>
			</c:if>
		  	<div class="button"><div class="buttonContent " align="center" ><button id="CmdClose" onclick="window.close();" class="close" type="button">取消</button></div></div>
		  	</div>
		  	<input type="hidden" name="filename" id="filename" value="${filename}"/>
	  	</form>
	  	<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
	  	<script type="text/javascript">
	  	 	function sub(){
	  			document.getElementById('form1').submit();
	  		};
	  	</script>
	</body>
	<script type="text/javascript">  
		$("div.button", document).hoverClass("buttonHover");
	</script>
</html>
