 <!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
	<head>
		<title>打印</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
	</head>
	<body onload="openfile()">
		<div class="w-here">
			<div class="w-here-box"><span> 打印送检单  → 打印</span></div>
		</div>
		 	<form style="display: none;" id="form1" name="form1" action="" method="post">
            	<input type="hidden" name="name" id="name" value="${name}">
	            <input type="hidden" name="isbc" id="isbc" value="0">
	            <input type="hidden" name="vc_mt" id="vc_mt" value="sb">
	            </form> 
        <div id="taoda" style="width: 100%;height: 540px;">
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
		
	</body> 
	<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
   		<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/tangerocx.js"></script>
	<script type="text/javascript">

		function replacelabel(){
			var labels = '${labels}'.split(",");
			var values = '${values}'.split(",");
			for(var i = 0; labels.length > i; i++){
				alert(labels[i]);
				alert(values[i]);
				CopyTextToBookMark2( "vc_mt", labels[i]);
			}
		}

        function openfile(){
            var docURL = '${ctx}/template_download.do?name=${name}';
            var aa = TANGER_OCX_Init(docURL);
          //  TANGER_OCX_Init(aa);
            replacelabel();
            TANGER_OCX_SetDocUser('${userName}');
            SetReviewMode(true);
            TANGER_OCX_EnableReviewBar(false);

            init();
        }

        function print(){
        	try
        	{
            	//document.all("TANGER_OCX")
        		 TANGER_OCX_OBJ.printout(true);
        	}
        	catch(err){
				
            };
        };

        function CopyTextToBookMark2(inputname,BookMarkName)
        {
        	try
        	{	
        		var inputValue="";
        		var j,elObj,optionItem;
        		var elObj = document.forms[0].elements(inputname);		 
        		if (!elObj)
        		{
        			alert("HTML的FORM中没有此输入域："+ inputname);
        			return;
        		}
        		inputValue = elObj.value;
        		//do copy
        		//DEBUG
        		alert(inputname+"="+inputValue+" Bookmarkname="+BookMarkName);
        		var bkmkObj = TANGER_OCX_OBJ.ActiveDocument.BookMarks(BookMarkName);	
        		alert(bkmkObj);
        		if(!bkmkObj)
        		{
        			alert("Word 模板中不存在名称为：\""+BookMarkName+"\"的书签！");
        		}
        		var saverange = bkmkObj.Range;
        		saverange.Text = inputValue;
        		TANGER_OCX_OBJ.ActiveDocument.Bookmarks.Add(BookMarkName,saverange);
        	}
        	catch(err){}
        	finally{
        	}		
        }
	</script>
</html>
