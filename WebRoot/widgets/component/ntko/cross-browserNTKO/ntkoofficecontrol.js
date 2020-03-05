 // 请勿修改，否则可能出错
	var userAgent = navigator.userAgent, 
				rMsie = /(msie\s|trident.*rv:)([\w.]+)/, 
				rFirefox = /(firefox)\/([\w.]+)/, 
				rOpera = /(opera).+version\/([\w.]+)/, 
				rChrome = /(chrome)\/([\w.]+)/, 
				rSafari = /version\/([\w.]+).*(safari)/;
	var browser;
	var version;
	var ua = userAgent.toLowerCase();
	
	function uaMatch(ua) {
		var match = rMsie.exec(ua);
		if (match != null) {
			return { browser : "IE", version : match[2] || "0" };
		}
		var match = rFirefox.exec(ua);
		if (match != null) {
			return { browser : match[1] || "", version : match[2] || "0" };
		}
		var match = rOpera.exec(ua);
		if (match != null) {
			return { browser : match[1] || "", version : match[2] || "0" };
		}
		var match = rChrome.exec(ua);
		if (match != null) {
			return { browser : match[1] || "", version : match[2] || "0" };
		}
		var match = rSafari.exec(ua);
		if (match != null) {
			return { browser : match[2] || "", version : match[1] || "0" };
		}
		if (match != null) {
			return { browser : "", version : "0" };
		}
	}
	var browserMatch = uaMatch(userAgent.toLowerCase());
	if (browserMatch.browser) {
		browser = browserMatch.browser;
		version = browserMatch.version;
	}
	
	var classidx64="6AA93B0B-D450-4a80-876E-3909055B0640";
	//var classidx64="A64E3073-2016-4baf-A89D-FFE1FAA10EE1";
	var classid = "6AA93B0B-D450-4a80-876E-3909055B0640";
	//var classid="C9BC4DFF-4248-4a3c-8A49-63A7D317F404";
	var codebase="http://127.0.0.1:1000/trueWorkFlow/widgets/component/ntko/ofctnewclsid.cab#version=5,0,2,7";
	//http://192.168.5.17:8085/trueWorkFlowV3.2_basic/widgets/component/ntko/ofctnewclsid.cab#version=5,0,2,7
	var codebase64="/widgets/component/ntko/ofctnewclsid.cab#version=5,0,2,7";
	//var codebase64="ofctnewclsidx64.cab#version=5,0,2,6";
	if (browser=="IE"){
		//alert(window.navigator.platform);
		if(window.navigator.platform=="Win32"){
			document.write('<!-- 用来产生编辑状态的ActiveX控件的JS脚本-->   ');
			document.write('<!-- 因为微软的ActiveX新机制，需要一个外部引入的js-->   ');
			document.write('<object id="TANGER_OCX" classid="clsid:'+classid+'"');
			document.write('codebase="'+codebase+'" width="100%" height="100%">   ');
			//---------------------授权部分-----------------------------------------------------------
			document.write('<param name="MakerCaption" value="江苏中威科技软件系统有限公司">   ');
			document.write('<param name="MakerKey" value="866CF5B5DB3510905937E18F071E26313A3F4DF4">   ');
			document.write('<param name="ProductCaption" value="江苏中威科技软件系统有限公司">   ');
			document.write('<param name="ProductKey" value="03450F2A369FA8790329FC67D839D116B4B2F065">   ');
			//---------------------授权部分-----------------------------------------------------------
			document.write('<param name="IsUseUTF8URL" value="-1">   ');
			document.write('<param name="IsUseUTF8Data" value="-1">   ');
			document.write('<param name="BorderStyle" value="1">   ');
			document.write('<param name="BorderColor" value="14402205">   ');
			document.write('<param name="TitlebarColor" value="15658734">   ');
			document.write('<param name="isoptforopenspeed" value="0">   ');
			document.write('<param name="TitlebarTextColor" value="0">   ');
			document.write('<param name="MenubarColor" value="14402205">   ');
			document.write('<param name="MenuButtonColor" VALUE="16180947">   ');
			document.write('<param name="MenuBarStyle" value="3">   ');
			document.write('<param name="MenuButtonStyle" value="7">   ');
			document.write('<param name="WebUserName" value="NTKO">   ');
			document.write('<param name="Caption" value="">   ');
			document.write('<SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>   ');
			document.write('</object>');	
		}if(window.navigator.platform=="Win64"){
			document.write('<!-- 用来产生编辑状态的ActiveX控件的JS脚本-->   ');
			document.write('<!-- 因为微软的ActiveX新机制，需要一个外部引入的js-->   ');
			document.write('<object id="TANGER_OCX" classid="clsid:'+classidx64+'"');
			document.write('codebase="'+codebase64+'" width="100%" height="100%">   ');
			document.write('<param name="IsUseUTF8URL" value="-1">   ');
			document.write('<param name="IsUseUTF8Data" value="-1">   ');
			document.write('<param name="BorderStyle" value="1">   ');
			document.write('<param name="BorderColor" value="14402205">   ');
			document.write('<param name="TitlebarColor" value="15658734">   ');
			document.write('<param name="isoptforopenspeed" value="0">   ');
			document.write('<param name="TitlebarTextColor" value="0">   ');
			//---------------------授权部分-----------------------------------------------------------
			document.write('<param name="MakerCaption" value="江苏中威科技软件系统有限公司">   ');
			document.write('<param name="MakerKey" value="866CF5B5DB3510905937E18F071E26313A3F4DF4">   ');
			document.write('<param name="ProductCaption" value="江苏中威科技软件系统有限公司">   ');
			document.write('<param name="ProductKey" value="D0968A04B0B1E5067A35D5FA58F064405BAE79E3">   ');
			//---------------------授权部分-----------------------------------------------------------
			document.write('<param name="MenubarColor" value="14402205">   ');
			document.write('<param name="MenuButtonColor" VALUE="16180947">   ');
			document.write('<param name="MenuBarStyle" value="3">   ');
			document.write('<param name="MenuButtonStyle" value="7">   ');
			document.write('<param name="WebUserName" value="NTKO">   ');
			document.write('<param name="Caption" value="">   ');
			document.write('<SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>   ');
			document.write('</object>');	
			
		}
		
	}else if (browser=="firefox"){ 	
			document.write('<object id="TANGER_OCX" type="application/ntko-plug"  codebase="'+codebase+'" width="1000px" height="600px" ForOnSaveToURL="OnSaveToURL" ForOnBeginOpenFromURL="OnBeginOpenFromURL" ForOndocumentopened="Ondocumentopened"');
			document.write('ForOnpublishAshtmltourl="OnpublishAshtmltourl"');
			document.write('ForOnpublishAspdftourl="OnpublishAspdftourl"');
			document.write('ForOnSaveAsOtherFormatToUrl="OnSaveAsOtherFormatToUrl"');
			document.write('ForOnDoWebGet="OnDoWebGet"');
			document.write('ForOnDoWebExecute="OnDoWebExecute"');
			document.write('ForOnDoWebExecute2="OnDoWebExecute2"');
			document.write('ForOnFileCommand="OnFileCommand"');
			document.write('ForOnCustomMenuCmd2="OnCustomMenuCmd2"');
			document.write('_IsUseUTF8URL="-1"   ');
			//---------------------授权部分-----------------------------------------------------------
			document.write('_MakerCaption="江苏中威科技软件系统有限公司"   ');
			document.write('_MakerKey="866CF5B5DB3510905937E18F071E26313A3F4DF4"   ');
			document.write('_ProductCaption="江苏中威科技软件系统有限公司"   ');
			document.write('_ProductKey="03450F2A369FA8790329FC67D839D116B4B2F065"   ');
			//---------------------授权部分-----------------------------------------------------------
			document.write('_IsUseUTF8Data="-1"   ');
			document.write('_BorderStyle="1"   ');
			document.write('_BorderColor="14402205"   ');
			document.write('_MenubarColor="14402205"   ');
			document.write('_MenuButtonColor="16180947"   ');
			document.write('_MenuBarStyle="3"  ');
			document.write('_MenuButtonStyle="7"   ');
			document.write('_WebUserName="NTKO"   ');
			document.write('clsid="{'+classid+'}" >');
			document.write('<SPAN STYLE="color:red">尚未安装NTKO Web FireFox跨浏览器插件。</SPAN>   ');
			document.write('</object>   ');
	}else if(browser=="chrome"){
			document.write('<object id="TANGER_OCX" clsid="{'+classid+'}"  ForOnSaveToURL="OnSaveToURL" ForOnBeginOpenFromURL="OnBeginOpenFromURL" ForOnDocumentOpened="OnDocumentOpened"');
			document.write('ForOnpublishAshtmltourl="OnpublishAshtmltourl"');
			document.write('ForOnpublishAspdftourl="OnpublishAspdftourl"');
			document.write('ForOnSaveAsOtherFormatToUrl="OnSaveAsOtherFormatToUrl"');
			document.write('ForOnDoWebGet="OnDoWebGet"');
			document.write('ForOnDoWebExecute="OnDoWebExecute"');
			document.write('ForOnDoWebExecute2="OnDoWebExecute2"');
			document.write('ForOnFileCommand="OnFileCommand"');
			document.write('ForOnCustomMenuCmd2="OnCustomMenuCmd2"');
			document.write('ForOnAddTemplateFromURL="OnAddTemplateFromURL"');
			document.write('codebase="'+codebase+'" width="100%" height="100%" type="application/ntko-plug" ');
	 		//---------------------授权部分-----------------------------------------------------------
			document.write('_MakerCaption="江苏中威科技软件系统有限公司"   ');
			document.write('_MakerKey="866CF5B5DB3510905937E18F071E26313A3F4DF4"   ');
			document.write('_ProductCaption="江苏中威科技软件系统有限公司"   ');
			document.write('_ProductKey="03450F2A369FA8790329FC67D839D116B4B2F065"   ');
			//---------------------授权部分-----------------------------------------------------------
			document.write('_IsUseUTF8URL="-1"   ');
			document.write('_IsUseUTF8Data="-1"   ');
			document.write('_BorderStyle="1"   ');
			document.write('_BorderColor="14402205"   ');
			document.write('_MenubarColor="14402205"   ');
			document.write('_MenuButtonColor="16180947"   ');
			document.write('_MenuBarStyle="3"  ');
			document.write('_MenuButtonStyle="7"   ');
			document.write('_WebUserName="NTKO"   ');
			document.write('_Caption="">    ');
			document.write('<SPAN STYLE="color:red">尚未安装NTKO Web Chrome跨浏览器插件。</SPAN>   ');
			document.write('</object>');
	}else if (Sys.opera){
		alert("sorry,ntko web印章暂时不支持opera!");
	}else if (Sys.safari){
		alert("sorry,ntko web印章暂时不支持safari!");
	}
