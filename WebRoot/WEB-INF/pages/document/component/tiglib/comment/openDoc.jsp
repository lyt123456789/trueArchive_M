<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>Word 预览</title>
        <meta http-equiv="cache-control" content="no-cache,must-revalidate">
        <meta http-equiv="pragram" content="no-cache">
        <meta http-equiv="expires" content="0">
    </head>
    <body>
        <%@include file="/widgets/component/ntko/ntko.jsp" %>
        <script type="text/javascript" src="${ctx }/widgets/component/ntko/js/ntko.js"></script>
        <script language="JScript" for=NTKO_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
            TANGER_OCX_OBJ.activeDocument.saved = true;//saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
            setFileOpenedOrClosed(true);
        </script>
        <script type="text/javascript">
	        //正文的下载链接
			var docURL = '${ctx}/comment_downloadAtt.do?name=a.doc&location=${location}';
	        TANGER_OCX_Init(docURL);
	        TANGER_OCX_EnableReviewBar(false);
        </script>
    </body>
</html>
