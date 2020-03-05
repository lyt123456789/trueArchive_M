<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>附件预览</title>
        <meta http-equiv="cache-control" content="no-cache,must-revalidate">
        <meta http-equiv="pragram" content="no-cache">
        <meta http-equiv="expires" content="0">
        <%@ include file="/common/header.jsp" %>
        <link rel="stylesheet" href="${ctx}/assets/themes/default/css/style.css" type="text/css" />
    </head>
    <body>
		<div class="wf-layout">
			<!-- <div class="wf-list-top">
				<div class="wf-search-bar">
					<div class="wf-top-tool" style="margin-top:-17px;">
						<a class="wf-btn-green" id="print" style="cursor: pointer;font-size: 16px;">打印</a>	
			        </div>
				</div>
			</div> -->
		</div>
    	<!-- ntko跨浏览器控件加载js (必须) -->
    	<!-- 注意：ie浏览器只能在body中加载，chrome浏览器可以在head中加载 -->
    	<!--控件对象-->
		<div id="ocxobject" class="ocxobjdiv">
			<script type="text/javascript" src="${curl }/widgets/component/ntko/cross-browserNTKO/ntkoofficecontrol.js?t=22"></script>
		</div>
    </body>
    <!-- 加载jquery控件(可选) -->
    <script type="text/javascript" src="${curl }/widgets/plugin/js/base/jquery.js"></script>
    <!-- ntko文档控件操作方法 -->
    <script type="text/javascript" src="${curl }/widgets/component/ntko/js/ntkoFunction.js"></script>
    <!-- ntko控件事件方法体，在script体中加入 --><!-- 此回调方式仅支持ie浏览器 -->
    <script language="JScript" for="TANGER_OCX" event="OnDocumentOpened(str,doc)">
    	/* todo something */
    	OnDocumentOpened(str,doc);
    </script>
    <!-- ntko控件相关封装方法 -->
    <script type="text/javascript">
	    window.onbeforeunload = function(){
	    	if(TANGER_OCX_OBJ){
	    		TANGER_OCX_OBJ.Close();
	    	}
	    }	
	    
	    $("#print").bind("click",function(){
	    	TANGER_OCX_OBJ.PrintOut(true);
	    });
	    
	    /*----------按需求调用方法--------------*/
		$(function(){
			init(window.innerHeight-20);//初始化ntko
			officeToolBar(false);//关闭功能区工具栏
			menuBar(false);//菜单栏关闭
			statusBar(false);//状态栏关闭
			openOfficeFile('${curl}/attachment_download.do?name=a.doc&location=${location}');//加载服务器上的文档
		});	
		
		function OnDocumentOpened(str,doc){//在本页面中重写OnDocumentOpened方法
			//console.log("OnDocumentOpened成功回调");
			TANGER_OCX_bDocOpen = true;
			//doc.TrackRevisions = true; //经验证，此方法只能在OnDocumentOpened方法体中调用才能成功开启留痕模式
		    openDocType = TANGER_OCX_OBJ.DocType,
		    15 == TANGER_OCX_OBJ.getOfficeVer() && (TANGER_OCX_OBJ.ActiveDocument.Application.Options.UseLocalUserInfo = !0);
		    setReadOnly(false);
		}
    </script>
</html>
