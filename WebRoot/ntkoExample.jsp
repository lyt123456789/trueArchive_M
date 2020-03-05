<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>ntko控件示例</title>
        <meta http-equiv="cache-control" content="no-cache,must-revalidate">
        <meta http-equiv="pragram" content="no-cache">
        <meta http-equiv="expires" content="0">
        <%@ include file="/common/header.jsp" %>
        <%@ include file="/common/widgets.jsp" %>
        <link rel="stylesheet" href="${ctx}/assets/themes/default/css/style.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/assets/themes/default/css/other.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/assets/themes/css/print.css" type="text/css" media="print"/>
    </head>
    <body>
	    <div class="wf-layout">
			<div class="wf-list-top">
				<div class="wf-search-bar">
					<div class="wf-top-tool" style="display:none;" id="hideDiv">
				        <input style="width:400px" class="hideInput" value="tempfile/中威科技演示文档.doc">
				        <a class="wf-btn-green hideButton" style="cursor: pointer">OK</a>
			        </div>
					<div class="wf-top-tool">
			            <a class="wf-btn-primary" style="cursor: pointer;" onclick="creatNewOffice('Word.Document')">新建word</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="creatNewOffice('Excel.Sheet')">新建excel</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="creatNewOffice('PowerPoint.Show')">新建ppt</a>
			        </div>
					<div class="wf-top-tool">
			            <a class="wf-btn-primary" style="cursor: pointer;" onclick="TANGER_OCX_OBJ.ShowDialog(1);">打开本地……</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="openRemoteOfficeDoc();">打开服务器上……</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="TANGER_OCX_OBJ.ShowDialog(3);">另存为到本地……</a>
			            <!-- ShowDialogType={ 0|1|2|3|4|5|6 } 0：新建对象	1：打开	2：保存	3：另存为  4：打印	5：页面设置	6：文件属性 -->
			            <span>（其它保存和预览以及打印操作，详见ntkoExample.jsp源码或者ntko接口文档)</span>
			        </div>
			        <div class="wf-top-tool">
			            <a class="wf-btn-primary" style="cursor: pointer;" onclick="closeOffice();">关闭文档</a>
			        </div>
			        <br>
			        <div class="wf-top-tool" style="margin-top:5px;">
			            <a class="wf-btn-primary" style="cursor: pointer;" onclick="scrollBar(a);a=!a;">滚动条开关</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="rulers(a);a=!a;">标尺开关</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="statusBar(a);a=!a;">状态栏开关</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="titleBar(a);a=!a;">标题栏开关</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="menuBar(a);a=!a;">菜单栏开关</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="initCustomMenus();">初始化自定义菜单按钮</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="customToolBar();">自定义工具栏开关</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="officeToolBar(a);a=!a;">功能区开关</a>
			        </div>
			        <div class="wf-top-tool" style="margin-top:5px;">
			            <a class="wf-btn-primary" style="cursor: pointer;" onclick="copyEnable(a);a=!a;">启用和禁用复制</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="setReadOnly(a);a=!a;">文档只读开关</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="openEnable(a);a=!a;">启用和禁用新建</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="saveEnable(a);a=!a;">启用和禁用保存</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="printEnable(a);a=!a;">启用和禁用打印</a>
			            <a class="wf-btn-green" style="cursor: pointer;" onclick="protectRevision()">文档保护模式(痕迹保留)</a>
			        </div>
			        <br>
			        <div class="wf-top-tool" style="margin-top:5px;">
			            <a class="wf-btn-primary" style="cursor: pointer;" onclick="insertEmptyPage();">插入空白首页</a>
			            <a class="wf-btn" style="cursor: pointer;" onclick="insertEmptyEndPage();">插入空白尾页</a>
			            <a class="wf-btn" style="cursor: pointer;" onclick="insertBreak();">插入分页符</a>
			            <a class="wf-btn" style="cursor: pointer;" onclick="addTemplateRemoteFile('http://192.168.5.17:8104/trueWorkFlowV3.2_basic/attachment_download.do?name=a.doc&location=attachments/2016/08/25/C899C8F8-3EBF-4807-830E-CAAEEF8C899A.doc');">插入网络文件</a>
			            <a class="wf-btn" style="cursor: pointer;" onclick="addTemplateLocalFile();">插入本地文件</a>
			            <a class="wf-btn" style="cursor: pointer;" onclick="addHeaderFooter();">插入页眉文字</a>
			            <a class="wf-btn" style="cursor: pointer;" onclick="addWaterMark('中威科技');">插入文字水印</a>
			            <a class="wf-btn" style="cursor: pointer;" onclick="addWaterMarkPic('http://192.168.5.17:8104/trueWorkFlowV3.2_basic/dwz/themes/default/images/logo.png');">插入图片水印</a>
			        </div>
			        <br>
			        <div class="wf-top-tool" style="margin-top:5px;">
			            <a class="wf-btn-primary" style="cursor: pointer;" onclick="setReviewMode(a);a=!a;">留痕模式开关</a>
			            <a class="wf-btn" style="cursor: pointer;" onclick="showRevisions(a);a=!a;">痕迹显示开关</a>
			            <a class="wf-btn" style="cursor: pointer;" onclick="acceptOrRejectAllRevisions(true);">接收所有痕迹</a>
			            <a class="wf-btn" style="cursor: pointer;" onclick="acceptOrRejectAllRevisions(false);">拒绝所有痕迹</a>
			        </div>
			        <div class="wf-top-tool" style="margin-top:5px;">
			            <a class="wf-btn-primary" style="cursor: pointer;" onclick="TANGER_OCX_AddDocHeader('中威科技软件系统有限公司')">文档动态套红头</a>
			        </div>
				</div>
			</div>
		</div>
    	<!-- ntko跨浏览器控件加载js (必须) -->
    	<!-- 注意：ie浏览器只能在body中加载，chrome浏览器可以在head中加载 -->
    	<!--控件对象-->
		<div id="ocxobject" class="ocxobjdiv">
			<script type="text/javascript" src="${ctx }/widgets/component/ntko/cross-browserNTKO/ntkoofficecontrol.js"></script>
		</div>
    </body>
    <!-- 加载jquery控件(可选) -->
    <script type="text/javascript" src="${ctx }/widgets/plugin/js/base/jquery.js"></script>
    <script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
	<script src="${ctx}/assets/plugins/laydate/laydate.js" type="text/javascript"></script>
    <!-- ntko文档控件操作方法 -->
    <script type="text/javascript" src="${ctx }/widgets/component/ntko/js/ntkoFunction.js"></script>
    
    <!-- ntko控件事件方法体，在script体中加入 --><!-- 此回调方式仅支持ie浏览器 -->
    <script language="JScript" for="TANGER_OCX" event="OnDocumentOpened(str,doc)">
    	/* todo something */
    	OnDocumentOpened(str,doc);
    </script>
    <!-- ntko控件相关封装方法 -->
    <script type="text/javascript">
		/*----------按需求调用方法--------------*/
		$(function(){
			init(window.innerHeight-140);//初始化ntko
		});	
    </script>
    <script type="text/javascript">
    function openRemoteOfficeDoc(){
		$(".wf-search-bar").children().hide();
		$("#hideDiv").show();
		$(".hideButton").click(function(){
			openOfficeFile($(".hideInput").val());
			$(".wf-search-bar").children().show();
			$("#hideDiv").hide();
		});
	};
	
	var a = false;
    </script>
</html>
