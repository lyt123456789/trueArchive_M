 <!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>

<html>
    <head>
        <title>修改</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
    </head>
    <body onload="openfile();">
    	<form action="${ctx}/template_upload.do" id="form1" name="form1" method="post">
	    	<div id="taoda" style="width: 100%;height: 540px;">
	            <input type="hidden" name="name" id="name" value="${name}">
	            <input type="hidden" name="isbc" id="isbc" value="0">
	            <c:if test="${empty isview}">
	            	<input type="button" value="  保  存  " onclick="saveFileToUrl();if(confirm('是否关闭窗口?'))closeWindow();">
	            </c:if>
	            <input type="button" value="  打  印  " onclick="print();">
	            <input type="button" value="  关  闭  " onclick="closeWindow()">
	            <object id="TANGER_OCX" classid="clsid:6AA93B0B-D450-4a80-876E-3909055B0640" codebase="<%=SystemParamConfigUtil.getParamValueByParam("contentpath") %>/ofctnewclsid.cab#version=5,0,2,7" width="100%" height="100%">
			    <param name="BorderStyle" value="1">
			    <param name="BorderColor" value="14402205">
			    <param name="TitlebarColor" value="15658734">
			    <param name="TitlebarTextColor" value="0">
			    <param name="IsShowToolMenu" value="-1">
			    <param name="IsUseUTF8URL" value="-1">
			    <param name="IsUseUTF8Data" value="-1">
			    <param name="IsShowNetErrorMsg" value="-1">
			    <param name="MaxUploadSize" value="10000000">
			    <param name="CustomMenuCaption" value="我的菜单">
			    <param name="MenubarColor" value="14402205">
				<param name="MakerCaption" value="江苏中威科技软件系统有限公司">
				<param name="MakerKey" value="866CF5B5DB3510905937E18F071E26313A3F4DF4">
				<param name="ProductCaption" value="江苏中威科技软件系统有限公司"> 
				<param name="ProductKey" value="03450F2A369FA8790329FC67D839D116B4B2F065">
			    <param NAME="MenuButtonColor" value="16180947">
			    <param name="MenuBarStyle" value="3">
			    <param name="MenuButtonStyle" value="7">
			    <span STYLE="color:red">
			    	<a href="${ctx}/control/ntko/ntko.doc">无法加载ntko控件帮助文档</a>
			    	&nbsp;&nbsp;
			    	不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。
			    </span>
			</object>
			</div>       
		</form>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
    </body>
   
   	<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
   		<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/tangerocx.js"></script>
    <script type="text/javascript">
    (
			function(){
			    //在页面呈现后,获取文档控件对象
			    TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
			    //TANGER_OCX_OBJ.CreateNew("word.document");
			    TANGER_OCX_OBJ.AddPicFromLocal(
					    "D:\1.bmp",                         //路径
						true,                                                           //是否提示选择文件
						true,                                                           //是否浮动图片
						0,                                                              //如果是浮动图片，相对于左边的Left 单位磅
						0,                                                              //如果是浮动图片，相对于当前段落Top
						1,                                                              //当前光标处
						100,                                                            //无缩放
						1                                                               //文字上方
				);
			}
	)();

    
    function closeWindow()
    {
        if(document.getElementById('isbc').value == '1'){
	    	window.dialogArguments.document.all.vc_path.value=document.getElementById('name').value;
			var obj = window.dialogArguments.document.all.vc_path;
	    	if (obj.fireEvent)
	    	{
	    	obj.fireEvent('onchange');
	    	}
	    	else
	    	{
	    	obj.onchange();
	    	}


        }
    	window.close();
    }

    function openfile(){
    	if("${msg}" != ""){
			alert("${msg}");
			return ;
    	}
        var docURL = '${ctx}/template_download.do?name=${name}';
    	
        var aa = TANGER_OCX_Init(docURL);

        //目前报告采用doc文件流替换字符串的方式
        //关键词:<!--1111-->
        if('${isview}'=='1'){
        	//TANGER_OCX_Init(str);
            //dt();
           // TANGER_OCX_SetDocUser('${userName}');
            //SetReviewMode(true);
            //TANGER_OCX_EnableReviewBar(false);
            //init();
        } 
        if('${bookmark}'!=''){
			var bookmarks='${bookmark}'.split(/,/);
			var bookmarkValues='${bookmarkValue}'.split(/,/);
			for(var i=0;i<bookmarks.length;i++){//_attachmentPic
				var bookmark=bookmarks[i];
				if(bookmark.match(/_attachmentPic/)){//附件图片特殊处理
					//alert(bookmarkValues[i]);
					addPic(bookmarkValues[i]);
				};	
				if(bookmark.match(/_attachmentDoc/)){//附件图片特殊处理
					//alert(bookmarkValues[i]);
					addTemplate(bookmarkValues[i],bookmark.substring(0,bookmark.indexOf("_attachmentDoc")));
					continue;
				};
				var value=bookmarkValues[i];
				value=value.replace(/#textarea#/g,'\r\n');//替换textarea换行特殊字符串 ，防止js解析报错 add by panh
				if(value=='null')value=''; 
				//alert(value+',,,,'+bookmark);
				CopyTextToBookMark(value,bookmark);
			};
        };
        
    }
    
     function addTemplateStart(filename,bookmark){
    	var path="<%=SystemParamConfigUtil.getParamValueByParam("contentpath") %>/tempfile/"+filename;
    	TANGER_OCX_OBJ.ActiveDocument.Application.Selection.HomeKey(6);
		TANGER_OCX_OBJ.AddTemplateFromURL(path);

    } 
     function addTemplateEnd(filename,bookmark){
     	var path="<%=SystemParamConfigUtil.getParamValueByParam("contentpath") %>/tempfile/"+filename;
     	TANGER_OCX_OBJ.ActiveDocument.Application.Selection.EndKey(6);
 		TANGER_OCX_OBJ.AddTemplateFromURL(path);
     } 

  function addTemplate(filename,bookmark){
   		var path="<%=SystemParamConfigUtil.getParamValueByParam("contentpath") %>/tempfile/"+filename;
   		
  		
		TANGER_OCX_SetMarkModify(false);
		
		//TANGER_OCX_OBJ.ActiveDocument.Application.Selection.HomeKey(6); // 跳转到文档头部
		var mark = TANGER_OCX_OBJ.ActiveDocument.BookMarks(bookmark);
		mark.Select(); //选中标签的位置
		var curSel = TANGER_OCX_OBJ.ActiveDocument.Application.Selection;	 // 获得刚选中的书签的位置
		//插入模板
		TANGER_OCX_OBJ.AddTemplateFromURL(path);
		
		if(!TANGER_OCX_OBJ.ActiveDocument.BookMarks.Exists(bookmark))
		{
			alert("Word 模板中不存在名称为：\""+BookMarkName+"\"的书签！");
			return;
		}
		//TANGER_OCX_SetMarkModify(true);
	} 

    function TANGER_OCX_SetMarkModify(boolvalue)
    {
    	TANGER_OCX_SetReviewMode(boolvalue);
    	TANGER_OCX_EnableReviewBar(!boolvalue);
    }
    function TANGER_OCX_SetReviewMode(boolvalue)
    {
    	TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = boolvalue;
    }
    
    function TANGER_OCX_EnableReviewBar(boolvalue)
    {
    	TANGER_OCX_OBJ.ActiveDocument.CommandBars("Reviewing").Enabled = boolvalue;
    	TANGER_OCX_OBJ.ActiveDocument.CommandBars("Track Changes").Enabled = boolvalue;
    	TANGER_OCX_OBJ.IsShowToolMenu = boolvalue;	//关闭或打开工具菜单
    }


    function addPic(filename){
		var path="<%=SystemParamConfigUtil.getParamValueByParam("contentpath") %>/tempfile/"+filename;
		//alert(path);
		//添加本机图片
		     
        TANGER_OCX_OBJ.AddPicFromLocal(
        			"d:\333.jpg",		//路径
					false,	//是否提示选择文件
					true,	//是否浮动图片
					0,		//如果是浮动图片，相对于左边的left单位磅
					0,		//如果是浮动图片，相对于当前段落top
					1,		//当前光标处
					100,	//无缩放
					1		//文字上方
        );
        
        //添加url图片
       /*  TANGER_OCX_OBJ.AddPicFromURL(
        			path,		//url 注意：URL必须返回word支持的图片类型
					true,				//是否浮动图片
					0,					//如果是浮动图片，相对于左边的left单位磅
					0,					//如果是浮动图片，相对于当前段落top
					3,					//当前光标处
					100,				//无缩放
					1					//文字上方
	    ); */
    };

    function print(){
    	try
    	{
        	//document.all("TANGER_OCX")
    		 TANGER_OCX_OBJ.printout(true);
    	}
    	catch(err){
			
        };
    };
    	
    </script>
</html>
