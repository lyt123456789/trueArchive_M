<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<meta http-equiv="cache-control" content="no-cache,must-revalidate">
<meta http-equiv="pragram" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=Edge, Chrome=1" />
<meta name="renderer" content="webkit" />
<title>在线编辑正文</title>
<%@ include file="/common/header.jsp" %>
<script type="text/javascript" src="${ctx }/widgets/component/ntko/js/ntko.js">
</script>
<script type="text/javascript" src="${ctx }/widgets/plugin/js/base/jquery.js"></script>
<script type="text/javascript">
      // 打开doc
     function openfile(location){
		//正文的下载链接
		var docURL = '${ctx}/attachment_download.do?name=a.doc&location='+location;
        TANGER_OCX_Init(docURL);
        //打开修订模式
        TANGER_OCX_SetDocUser('${userName}');
        SetReviewMode(true);
        //设置修订人名字
        TANGER_OCX_EnableReviewBar(true);
        setShowRevisions(true); 
     }
    
      // 清稿编辑
     function qinggao(boolevalue){
      	  SetReviewMode(boolevalue);
          TANGER_OCX_AcceptAllRevisions();
     }
            
     function insertDw(shade){
            	
     }
</script>
<style type="text/css">
   body {background : #999;}
  .btn {padding:5px 0 5px 0;}
  .btn a{ cursor:pointer;display:inline-block;font-family:"Microsoft Yahei"; width:89px; height:34px; line-height:34px; margin:0 10px; font-size:18px; color:#fff; background:url(${ctx}/images/icon11.png) no-repeat;}
</style>
</head>
<body onload="openfile('${location}')">
     <form action="${ctx}/attachment_saveAttHistory.do" method="post" enctype="multipart/form-data">
    	<input type="hidden" id="attId" value="${attId}"/>
    	<input type="hidden" id="shade" value="${shade}"/>
		<input id="saveAtt" type="button" value="保存"/>
		<input id="cleanAtt" type="button" value="清稿"/>
       	<script language="JScript" for=NTKO_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
              TANGER_OCX_OBJ.activeDocument.saved = true;//saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
              setFileOpenedOrClosed(true);
        </script>
  </form>
<script type="text/javascript">
        $("#saveAtt").bind("click",function(){
        	var attId = "${attId}";
            if(window.confirm("确定保存吗？")){
	        	var ret=saveAttHistory("${ctx}/attachment_saveAttHistory.do", attId);
	        	if(ret==""){
	            	alert("保存成功！");
	            }
            }
         });
        
        $("#cleanAtt").bind("click",function(){
      	  	qinggao(true);
         });
</script>
<%@include file="/widgets/component/ntko/ntko.jsp" %>
</body>
</html>
