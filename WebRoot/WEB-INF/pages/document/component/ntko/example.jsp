<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Word 预览</title>
        <meta http-equiv="cache-control" content="no-cache,must-revalidate">
        <meta http-equiv="pragram" content="no-cache">
        <meta http-equiv="expires" content="0">
        <%@ include file="/common/header.jsp" %>
        <!-- 加载jquery控件(可选) -->
        <script type="text/javascript" src="${ctx }/widgets/plugin/js/base/jquery.js"></script>
        <!-- ntko跨浏览器控件加载js (必须) -->
        <script type="text/javascript" src="${ctx }/widgets/component/ntko/cross-browserNTKO/ntkoofficecontrol.js"></script>
        <script type="text/javascript">
			var TANGER_OCX_OBJ;//ntko控件对象
			/*----------初始化ntko控件对象--------------*/
			function init(){
				TANGER_OCX_OBJ=document.getElementById("TANGER_OCX");
				if(window.navigator.platform=="Win64"){
					TANGER_OCX_OBJ.AddDocTypePlugin(".tif","tif.NtkoDocument","4.0.0.2","${ctx}/widgets/component/ntko/cross-browserNTKO/ntkooledocallx64.cab",51,true);	
					TANGER_OCX_OBJ.AddDocTypePlugin(".pdf","PDF.NtkoDocument","4.0.0.2","${ctx}/widgets/component/ntko/cross-browserNTKO/ntkooledocallx64.cab",51,true);	
				}else{
				  	TANGER_OCX_OBJ.AddDocTypePlugin(".pdf","PDF.NtkoDocument","4.0.0.2","${ctx}/widgets/component/ntko/cross-browserNTKO/ntkooledocall.cab",51,true);
					TANGER_OCX_OBJ.AddDocTypePlugin(".tif","tif.NtkoDocument","4.0.0.2","${ctx}/widgets/component/ntko/cross-browserNTKO/ntkooledocallx64.cab",51,true);	
				}
			}
        </script>
    </head>
    <body onload="init();">
        <script language="JScript" for="TANGER_OCX" event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
        </script>
    </body>
</html>
