	var TANGER_OCX_OBJ, //ntko控件对象
	TANGER_OCX_bDocOpen = false,//是否有文档打开标识
	openDocType;//打开的文档类型

	/*----------在已经加载的ntko<object>的基础上继续初始化ntko控件对象--------------*/
	function init(height){
		TANGER_OCX_OBJ=document.getElementById("TANGER_OCX");
	    //"win64" == window.navigator.platform.toLowerCase() && alert("当前使用的是64位IE浏览器，请使用32位浏览器或者安装IE10以上版本(微软默认IE10以上版本运行32位模式)");
		TANGER_OCX_OBJ.height=height;//初始化ntko控件的高度为填满全屏幕
	};
	
	/*----------设置当前用户--------------*/
	function setUserName(b) {
        TANGER_OCX_OBJ.WebUserName = b
	}
	
	/*-----------初始化自定义的菜单栏，按需定义和加载-------------*/
	function initCustomMenus() {
	    var b, c, d, a = TANGER_OCX_OBJ;
	    for (b = 0; 1 > b; b++)
	        for (a.AddCustomMenu2(b, "自定义菜单(&" + b + ")"),
	        c = 0; 1 > c; c++)
	            for (a.AddCustomMenuItem2(b, c, -1, !0, "自定义菜单组", !1),
	            d = 0; 3 > d; d++)
	                0 == d && a.AddCustomMenuItem2(b, c, d, !1, "自定义菜单1", !1, 100 * b + 20 * c + d),
	                1 == d && a.AddCustomMenuItem2(b, c, d, !1, "自定义菜单2", !1, 100 * b + 20 * c + d),
	                2 == d && a.AddCustomMenuItem2(b, c, d, !1, "自定义菜单3", !1, 100 * b + 20 * c + d)
	}
	
	/*-----------初始化自定义的工具栏，按需定义和加载-------------*/
	/*function initCutomToolBar() {
	    TANGER_OCX_OBJ.AddCustomToolButton("新建", 3),
	    TANGER_OCX_OBJ.AddCustomToolButton("打开", 4),
	    TANGER_OCX_OBJ.AddCustomToolButton("保存", 5),
	    TANGER_OCX_OBJ.AddCustomToolButton("打印", 9),
	    TANGER_OCX_OBJ.AddCustomToolButton("打印预览", 10),
	    TANGER_OCX_OBJ.AddCustomToolButton("关闭文档", -1),
	    TANGER_OCX_OBJ.AddCustomToolButton("功能区", -1)
	}*/
	
	/*----------创建新office文件--------------
	Office Document Type 			ProgID 
	Word文档：						“Word.Document”
	PowerPoint幻灯片：				“PowerPoint.Show”
	Excel工作表：					“Excel.Sheet”
	Excel图表： 						"Excel.Chart"
	Visio画图： 						"Visio.Drawing"
	MS Project项目：					"MSProject.Project"
	WPS文字2003：					"WPSFile.4.8001"
	WPS文字2005-2012(V8版本)：		"WPS.Document"
	WPS文字2013(V9版本)：			"KWPS.Document"
	WPS表格2003：					"ET.Sheet.1.80.01.2001"
	WPS表格2005-2012(V8版本)：		"ET.WorkBook"
	WPS表格2013(V9版本)：			"KET.WorkBook"
	永中OFFICE文档：					"EIOffice.Document"
	*/
	function creatNewOffice(ProgID) {
	    TANGER_OCX_OBJ.CreateNew(ProgID)
	}
	
	/*----------关闭office，加入了保存判断--------------*/
	function closeOffice() {
		if(!TANGER_OCX_bDocOpen){
			alert("没有文档处于打开状态");
		}else{
			if(0 == TANGER_OCX_OBJ.ActiveDocument.Saved){
				if(confirm("你正在编辑的文档还没有保存，保存吗？")){
					TANGER_OCX_OBJ.ShowDialog(2);
					TANGER_OCX_OBJ.Close();
					TANGER_OCX_bDocOpen = false;
				} else {
					TANGER_OCX_OBJ.Close();
					TANGER_OCX_bDocOpen = false;
				}
			}else{
				TANGER_OCX_OBJ.Close();
		    	TANGER_OCX_bDocOpen = false;
			}
		}
	}
	
	/*----------按需求将相应的文档加载到ntko中--------------*/
	function openOfficeFile(a) {
	    return null  == TANGER_OCX_OBJ.Caption ? (alert("控件没有正常加载!"),!1) : (
	    (window.navigator.platform == "Win64" ?(
			TANGER_OCX_OBJ.AddDocTypePlugin(".tif","tif.NtkoDocument","4.0.0.2","${ctx}/widgets/component/ntko/cross-browserNTKO/ntkooledocallx64.cab",51,true),
			TANGER_OCX_OBJ.AddDocTypePlugin(".pdf","PDF.NtkoDocument","4.0.0.2","${ctx}/widgets/component/ntko/cross-browserNTKO/ntkooledocallx64.cab",51,true)
			):(TANGER_OCX_OBJ.AddDocTypePlugin(".pdf","PDF.NtkoDocument","4.0.0.2","${ctx}/widgets/component/ntko/cross-browserNTKO/ntkooledocall.cab",51,true),
			TANGER_OCX_OBJ.AddDocTypePlugin(".tif","tif.NtkoDocument","4.0.0.2","${ctx}/widgets/component/ntko/cross-browserNTKO/ntkooledocall.cab",51,true))),
	    TANGER_OCX_OBJ.BeginOpenFromURL(a),void 0);
	}
	
	/*----------文档控件相关栏目显示和隐藏---------------------------------------------------------------------------------------------*/
	/*----------滚动条开关--------------*/
	function scrollBar(a) {
		!TANGER_OCX_bDocOpen?(alert("没有文档处于打开状态")):(
			TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.DisplayHorizontalScrollBar = a,
	   		TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.DisplayVerticalScrollBar = a
	   	);
	}
	/*----------标尺开关--------------*/
	function rulers(a) {
		!TANGER_OCX_bDocOpen?(alert("没有文档处于打开状态")):(
			TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.DisplayRulers = a,
		    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.View.ShowParagraphs = !0
		);
	}
	/*----------状态栏开关--------------*/
	function statusBar(a) {
		TANGER_OCX_OBJ.statusbar = a
	}
	/*----------标题栏开关--------------*/
	function titleBar(a) {
		TANGER_OCX_OBJ.Titlebar = a
	}
	/*----------菜单栏开关--------------*/
	function menuBar(a) {
		TANGER_OCX_OBJ.Menubar = a
	}
	/*----------自定义工具栏开关--------------*/
	function customToolBar(a) {
	    TANGER_OCX_OBJ.CustomToolBar = a
	}
	/*----------功能区开关--------------*/
	function officeToolBar(a) {
		TANGER_OCX_OBJ.toolbars = a
	}
	
	/*----------文档相关功能启禁用---------------------------------------------------------------------------------------------*/
	/*----------启用和禁用复制--------------*/
	function copyEnable(a) {
		TANGER_OCX_OBJ.IsNoCopy = a 
	}
	
	/*----------设置只读模式--------------*/
	function setReadOnly(a) {
		TANGER_OCX_OBJ.SetReadOnly(a, "123456")
	}
	
	/*----------开启文档保护模式（痕迹保留）--------------*/
	function protectRevision() {
		!TANGER_OCX_bDocOpen?(alert("没有文档处于打开状态")):(
			TANGER_OCX_OBJ.ActiveDocument.Protect(0, !0, "123456")
		);
	}
	
	/*----------禁止新建和打开文档开关--------------*/
	function openEnable(a) {
		TANGER_OCX_OBJ.filenew = a,
		TANGER_OCX_OBJ.fileopen = a
	}
	
	/*----------禁止保存文档开关--------------*/
	function saveEnable(a){
		TANGER_OCX_OBJ.FileSaveAs = a,
		TANGER_OCX_OBJ.filesave = a
	}
	
	/*----------禁止打印文档开关--------------*/
	function printEnable(a) {
	    TANGER_OCX_OBJ.FilePrint = a,
	    TANGER_OCX_OBJ.FilePrintPreview = a
	}
	
	/*----------在打开的文档中插入内容---------------------------------------------------------------------------------------------*/
	/*----------插入空白首页--------------*/
	function insertEmptyPage() {
	    TANGER_OCX_bDocOpen ? (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.goto(1, 2, 1, 1),
	    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Find.ClearFormatting(),
	    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.InsertBreak(7),
	    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.goto(1, 2, 1, 1),
	    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.Font.color = 255) : alert("没有文档处于打开状态")
	}
	
	/*----------插入空白尾页--------------*/
	function insertEmptyEndPage() {
	    TANGER_OCX_bDocOpen ? (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.EndKey(6, 0),
	    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.InsertBreak(7)) : alert("没有文档处于打开状态")
	}
	
	/*----------插入分页符--------------*/
	function insertBreak() {
	    TANGER_OCX_bDocOpen ? (1 == openDocType && insertWordBreak(),
	    2 == openDocType && insertExccelBreak()) : alert("没有文档处于打开状态")
	}
	function insertWordBreak() {
	    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.InsertBreak(0)
	}
	function insertExccelBreak() {
	    var a = "e25"
	      , b = TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.Range(a);
	    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.HPageBreaks.Add(b),
	    TANGER_OCX_OBJ.ActiveDocument.ActiveSheet.VPageBreaks.Add(b)
	}
	
	/*----------插入服务器上的文档--------------*/
	function addTemplateRemoteFile(a) {
	    TANGER_OCX_bDocOpen && 1 == openDocType ? (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeParagraph(),
	    TANGER_OCX_OBJ.AddTemplateFromURL(a)) : alert("没有文档处于打开状态")
	}
	
	/*----------插入本地文档--------------*/
	function addTemplateLocalFile() {
	    TANGER_OCX_bDocOpen && 1 == openDocType ? (TANGER_OCX_OBJ.ActiveDocument.Application.Selection.TypeParagraph(),
	    TANGER_OCX_OBJ.AddTemplateFromLocal("", !0, !1)) : alert("没有文档处于打开状态")
	}
	
	/*----------插入页眉文字--------------*/
	function addHeaderFooter() {
	    var a = TANGER_OCX_OBJ.ActiveDocument
	      , b = TANGER_OCX_OBJ.ActiveDocument.Application;
	    TANGER_OCX_OBJ.ActiveDocument.ActiveWindow.ActivePane.View.SeekView = 9,
	    b.Selection.TypeText("江苏中威科技软件系统有限公司"),
	    b.Selection.TypeParagraph(),
	    b.Selection.Font.Bold = 9999998,
	    b.Selection.Font.Size = 22,
	    b.Selection.TypeText("页眉插入内容演示"),
	    a.ActiveWindow.ActivePane.View.SeekView = 0
	}
	
	/*----------插入文字水印--------------*/
	function addWaterMark(a) {
	    var b, c;
	    try {
	        b = TANGER_OCX_OBJ.ActiveDocument,
	        b.ActiveWindow.ActivePane.View.SeekView = 9,
	        c = b.Application.Selection,
	        c.PageSetup.DifferentFirstpageHeaderFooter = c.PageSetup.DifferentFirstpageHeaderFooter = !1,
	        -1 == c.PageSetup.OddAndEvenPagesHeaderFooter ? c.PageSetup.OddAndEvenPagesHeaderFooter = !1 : "",
	        c.HeaderFooter.Shapes.AddTextEffect(0, a, "宋体", 1, !1, !1, 0, 0).select(),
	        c.ShapeRange.Line.Visible = !1,
	        c.ShapeRange.Fill.Visible = !0,
	        c.ShapeRange.Fill.Solid(),
	        c.ShapeRange.Fill.ForeColor.RGB = 12345,
	        c.ShapeRange.Fill.Transparency = .5,
	        c.ShapeRange.Rotation = 315,
	        c.ShapeRange.LockAspectRatio = !0,
	        c.ShapeRange.Height = b.Application.CentimetersToPoints(4.13),
	        c.ShapeRange.Width = b.Application.CentimetersToPoints(16.52),
	        c.ShapeRange.WrapFormat.AllowOverlap = !0,
	        c.ShapeRange.WrapFormat.Side = 3,
	        c.ShapeRange.WrapFormat.Type = 3,
	        c.ShapeRange.RelativeHorizontalPosition = 0,
	        c.ShapeRange.RelativeVerticalPosition = 0,
	        c.ShapeRange.Left = -999995,
	        c.ShapeRange.Top = -999995,
	        b.ActiveWindow.ActivePane.View.SeekView = 0
	    } catch (d) {
	        //addWaterMark(a)
	    }
	}
	
	/*----------插入图片水印--------------*/
	function addWaterMarkPic(a) {
	    var b, c;
	    try {
	        b = TANGER_OCX_OBJ.ActiveDocument,
	        b.ActiveWindow.ActivePane.View.SeekView = 9,
	        c = b.Application.Selection,
	        c.HeaderFooter.Shapes.AddPicture(a, !1, !0).Select(),
	        c.ShapeRange.Name = "WordPictureWatermark1",
	        c.ShapeRange.PictureFormat.Brightness = .8,
	        c.ShapeRange.PictureFormat.Contrast = .5,
	        c.ShapeRange.LockAspectRatio = !0,
	        c.ShapeRange.Height = b.Application.CentimetersToPoints(4.42),
	        c.ShapeRange.Width = b.Application.CentimetersToPoints(4.92),
	        c.ShapeRange.WrapFormat.AllowOverlap = !0,
	        c.ShapeRange.WrapFormat.Side = 3,
	        c.ShapeRange.WrapFormat.Type = 3,
	        c.ShapeRange.RelativeHorizontalPosition = 0,
	        c.ShapeRange.RelativeVerticalPosition = 0,
	        c.ShapeRange.Left = -999995,
	        c.ShapeRange.Top = -999995,
	        b.ActiveWindow.ActivePane.View.SeekView = 0
	    } catch (d) {
	        //setvisibleinfo("addWaterMark errir:" + d.number + ":" + d.description)
	    }
	}
	
	/*----------留痕模式开关--------------*/
	function setReviewMode(a) {
	    TANGER_OCX_bDocOpen ? (1 == openDocType || 6 == openDocType) && (TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = a):
	    alert("没有文档处于打开状态")
	}
	
	/*----------痕迹显示开关--------------*/
	function showRevisions(a) {
	    TANGER_OCX_bDocOpen ? TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = a:alert("没有文档处于打开状态")
	}
	
	/*----------接受和拒绝所有痕迹--------------*/
	function acceptOrRejectAllRevisions(a) {
	    if (isprotect())
	        return alert("文档保护模式下禁用！"),
	        void 0;
	    if (1 == openDocType || 6 == openDocType)
	        if (a)
	            TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
	        else if (1 == openDocType)
	            TANGER_OCX_OBJ.ActiveDocument.Application.WordBasic.RejectAllChangesInDoc();
	        else {
	            if (6 != openDocType)
	                return;
	            TANGER_OCX_OBJ.ActiveDocument.Revisions.RejectAll()
	        }
	}
	function isprotect() {
	    switch (TANGER_OCX_OBJ.ActiveDocument.ProtectionType) {
	    case -1:
	        return !1;
	    case 0:
	        return !0;
	    case 1:
	        return !0;
	    case 2:
	        return !0;
	    default:
	        return !0
	    }
	}
	
	/*----------文档动态套红头--------------*/
	function TANGER_OCX_AddDocHeader(strHeader) {
	    var i, cNum, lineStr;
	    if (TANGER_OCX_bDocOpen)
	        if (cNum = 15, lineStr = "", 1 == openDocType || 6 == openDocType)
	            try {
	                for (i = 0; cNum > i; i++)
	                    lineStr += "=";
	                with (TANGER_OCX_OBJ.ActiveDocument.Application) {
	                    with (Selection.HomeKey(6, 0),
	                    Selection.TypeText(strHeader),
	                    Selection.TypeParagraph(),
	                    Selection.TypeText(lineStr),
	                    Selection.TypeText("★"),
	                    Selection.TypeText(lineStr),
	                    Selection.TypeParagraph(),
	                    Selection.HomeKey(6, 1),
	                    Selection.ParagraphFormat.Alignment = 1,
	                    Selection.Font)
	                        NameFarEast = "黑体",
	                        Name = "黑体",
	                        Size = 24,
	                        Bold = !1,
	                        Italic = !1,
	                        Underline = 0,
	                        UnderlineColor = 0,
	                        StrikeThrough = !1,
	                        DoubleStrikeThrough = !1,
	                        Outline = !1,
	                        Emboss = !1,
	                        Shadow = !1,
	                        Hidden = !1,
	                        SmallCaps = !1,
	                        AllCaps = !1,
	                        Color = 255,
	                        Engrave = !1,
	                        Superscript = !1,
	                        Subscript = !1,
	                        Spacing = 0,
	                        Scaling = 100,
	                        Position = 0,
	                        Kerning = 0,
	                        Animation = 0,
	                        DisableCharacterSpaceGrid = !1,
	                        EmphasisMark = 0;
	                    Selection.MoveDown(5, 3, 0)
	                }
	                with (TANGER_OCX_OBJ.ActiveDocument.Application) {
	                    with (Selection.EndKey(6),
	                    Selection.TypeParagraph(),
	                    Selection.ParagraphFormat.Alignment = 1,
	                    Selection.Font)
	                        NameFarEast = "宋体",
	                        Name = "宋体",
	                        Size = 24,
	                        Bold = !1,
	                        Italic = !1,
	                        Underline = 0,
	                        UnderlineColor = 0,
	                        StrikeThrough = !1,
	                        DoubleStrikeThrough = !1,
	                        Outline = !1,
	                        Emboss = !1,
	                        Shadow = !1,
	                        Hidden = !1,
	                        SmallCaps = !1,
	                        AllCaps = !1,
	                        Color = 255,
	                        Engrave = !1,
	                        Superscript = !1,
	                        Subscript = !1,
	                        Spacing = 0,
	                        Scaling = 100,
	                        Position = 0,
	                        Kerning = 0,
	                        Animation = 0,
	                        DisableCharacterSpaceGrid = !1,
	                        EmphasisMark = 0;
	                    Selection.TypeText(lineStr),
	                    Selection.TypeText(lineStr),
	                    TANGER_OCX_OBJ.ActiveDocument.Application.Selection.HomeKey(6, 0)
	                }
	            } catch (err) {
	                alert("错误：" + err.number + ":" + err.description)
	            } finally {}
	        else
	           alert("不支持的文档内型!")
	    else alert("没有文档处于打开状态");
	}
	
		/*** 用于正文附件在线编辑后保存***/
	function saveAttHistory(saveUrl,attId,cb){
		if(TANGER_OCX_bDocOpen){
			switch (openDocType){
				case 1:
					fileType = "Word.Document";
					break;
				case 2:
					fileType = "Excel.Sheet";
					break;
				case 3:
					fileType = "PowerPoint.Show";
					break;
				case 4:
					fileType = "Visio.Drawing";
					break;
				case 5:
					fileType = "MSProject.Project";
					break;
				case 6:
					fileType = "WPS Doc";
					break;
				case 7:
					fileType = "Kingsoft Sheet";
					break;
				default :
					fileType = "unkownfiletype";
			}
			var isSuccess = true;
			try{
			var a = TANGER_OCX_OBJ.saveToURL(saveUrl,//提交到的url地址
			"file",//文件域的id，类似<input type=file name=upLoadFile 中的name
			"fileType="+fileType+"&attId="+attId,          //与控件一起提交的参数如："p1=a&p2=b&p3=c"
			0    //与控件一起提交的表单id，也可以是form的序列号，这里应该是0.
			);
			}catch (e) {
				isSuccess = false;
				a = "fail";
			}
			console.log(cb);
			cb(a);
			return a;
		}
	}
	
	//谷歌和火狐浏览器事件接管
	/*通过注册ntko Object时的ForOnSaveToURL属性注册*/
	function OnSaveToURL(type,code,html){
		console.log("SaveToURL成功回调--type:"+type+"|code:"+code+"|html:"+html);
	}
	/*通过注册ntko Object时的ForOnBeginOpenFromURL属性注册*/
	function OnBeginOpenFromURL(type,code,html){
		console.log("BeginOpenFromURL成功回调--type:"+type+"|code:"+code+"|html:"+html);
	}
	/*通过注册ntko Object时的ForOnDocumentOpened属性注册*/
	function OnDocumentOpened(str,doc){
		console.log("OnDocumentOpened成功回调");
		TANGER_OCX_bDocOpen = true;
		//doc.TrackRevisions = true; //经验证，此方法只能在OnDocumentOpened方法体中调用才能成功开启留痕模式
	    openDocType = TANGER_OCX_OBJ.DocType,
	    15 == TANGER_OCX_OBJ.getOfficeVer() && (TANGER_OCX_OBJ.ActiveDocument.Application.Options.UseLocalUserInfo = !0)
	}
	/*通过注册ntko Object时的ForOnpublishAshtmltourl属性注册*/
	function OnpublishAshtmltourl(type,code,html){
		console.log("publishashtmltourl成功回调--type:"+type+"|code:"+code+"|html:"+html);
	}
	/*通过注册ntko Object时的ForOnpublishAspdftourl属性注册*/
	function OnpublishAspdftourl(type,code,html){
		console.log("publishAspdftourl成功回调--type:"+type+"|code:"+code+"|html:"+html);
	}
	/*通过注册ntko Object时的ForOnSaveAsOtherFormatToUrl属性注册*/
	function OnSaveAsOtherFormatToUrl(type,code,html){
		console.log("SaveAsOtherformattourl成功回调--type:"+type+"|code:"+code+"|html:"+html);
	}
	/*通过注册ntko Object时的ForOnDoWebGet属性注册*/
	function OnDoWebGet(type,code,html){
		console.log("OnDoWebGet成功回调--type:"+type+"|code:"+code+"|html:"+html);
	}
	
	function OnDoWebExecute(type,code,html){
		console.log("DoWebExecute成功回调--type:"+type+"|code:"+code+"|html:"+html);
	}
	/*通过注册ntko Object时的ForOnDoWebExecute2属性注册*/
	function OnDoWebExecute2(type,code,html){
		console.log("DoWebExecute2成功回调--type:"+type+"|code:"+code+"|html:"+html);
	}
	/*通过注册ntko Object时的ForOnFileCommand属性注册*/
	function OnFileCommand(TANGER_OCX_str,TANGER_OCX_obj){
		if (TANGER_OCX_str == 3) {
			console.log("不能保存！");
			//TANGER_OCX_OBJ.CancelLastCommand = true;
		}
	}
	/*通过注册ntko Object时的ForOnCustomMenuCmd2属性注册*/
	function OnCustomMenuCmd2(menuPos,submenuPos,subsubmenuPos,menuCaption,menuID){
		console.log("CustomMenuCmd2成功回调--第" + menuPos +","+ submenuPos +","+ subsubmenuPos +"个菜单项,menuID="+menuID+",菜单标题为\""+menuCaption+"\"的命令被执行.");
	}
