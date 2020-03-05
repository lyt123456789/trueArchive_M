<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
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
	<body onload="">
	 	<form id="form1" action="${ctx }/form_readHTMLToString4Code.do" method="post">
	 		<input type="hidden" name="isview" id="isview" value=""/>
	 		<input type="hidden" name="filename" id="filename" value=""/>
	 		<input type="hidden" name="formType" id="formType" value=""/>
	 		<input type="hidden" name="jspfilename" id="jspfilename" value=""/>
	 	</form>
	 	<script type="text/javascript">
	 		var isview=parent.document.getElementById('isview').value; 
	 		var filename=parent.document.getElementById('filename').value;
	 		var formType=parent.document.getElementById('formType').value; 
	 		var jspfilename=parent.document.getElementById('jspfilename').value;
	 		document.getElementById('isview').value=isview;
	 		document.getElementById('filename').value=filename;
	 		document.getElementById('formType').value=formType;
	 		document.getElementById('jspfilename').value=jspfilename;
	 		document.getElementById('form1').submit();
	 	</script>
	</body>
</html>
