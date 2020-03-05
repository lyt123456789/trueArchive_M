function callback(inputId){
   var callbackMethod = $("#"+inputId).val();
   if(callbackMethod!="null"&&callbackMethod!=""){ 
   		try{
			//eval(callbackMethod+"()");
		}catch(e){
			alert("\u56de\u8c03\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u65b9\u6cd5\uff1a"+callbackMethod+"()\u662f\u5426\u5b58\u5728");
		}
   }
}

// 初始化 附件控件
function initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst){
	$.ajax({
	    url: 'attachment_showAtts.do',
	    type: 'POST',
	    cache:false,
	    async:false,
	    data:{
	    	'tagid':tagid,'docguid':docguid,'deleteAble':deleteAble,
	    	'downloadAble':downloadAble,'previewAble':previewAble,'onlineEditAble':onlineEditAble,'tocebAble':tocebAble,
	    	'toStampAble':toStampAble,'printStampName':printStampName,'detachStampAble':detachStampAble,'openBtnClass':openBtnClass,
	    	'otherBtnsClass':otherBtnsClass,'showId':showId,'isReciveAtt':isReciveAtt,"nodeId":nodeId,'isFirst':isFirst},
	    error: function(){
	        alert('\u9644\u4ef6\u52a0\u8f7d\u5931\u8d25');
	    },
	    success: function(retValue){
			var size = getPageSize();
			if(docguid != null && docguid.indexOf("attzw") != -1){
				$(".file-type-2").empty().append(retValue);
				var fjHeight = $(".file-type-2").height();
				var c_btn_height = $(".btn-type-2").height();
				document.getElementById("frame1").contentWindow.setPageSize(size[3] - fjHeight - c_btn_height -144, 1024);
			}else{
				$(".file-type-1").empty().append(retValue);
				var fjHeight = $(".file-type-1").height();
				var c_btn_height = $(".btn-type-1").height();
				document.getElementById("frame1").contentWindow.setPageSize(size[3] - fjHeight - c_btn_height -144, 1024);
			}
	    }
	});	
}
//获取设备屏幕尺寸
function getPageSize() {
	var xScroll, yScroll, pageHeight, pageWidth, arrayPageSize;
	if (window.innerHeight && window.scrollMaxY) {
		xScroll = window.innerWidth + window.scrollMaxX;
		yScroll = window.innerHeight + window.scrollMaxY
	} else {
		if (document.body.scrollHeight > document.body.offsetHeight) {
			xScroll = document.body.scrollWidth;
			yScroll = document.body.scrollHeight
		} else {
			xScroll = document.body.offsetWidth;
			yScroll = document.body.offsetHeight
		}
	}
	var windowWidth, windowHeight;
	if (self.innerHeight) { if (document.documentElement.clientWidth) { windowWidth = document.documentElement.clientWidth } else { windowWidth = self.innerWidth } windowHeight = self.innerHeight } else {
		if (document.documentElement && document.documentElement.clientHeight) {
			windowWidth = document.documentElement.clientWidth;
			windowHeight = document.documentElement.clientHeight;
		} else {
			if (document.body) {
				windowWidth = document.body.clientWidth;
				windowHeight = document.body.clientHeight;
			}
		}
	}
	if (yScroll < windowHeight) { pageHeight = windowHeight } else { pageHeight = yScroll }
	if (xScroll < windowWidth) { pageWidth = xScroll } else { pageWidth = windowWidth } arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight);
	return arrayPageSize;
}
//初始化 附件控件
function initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst){
	$.ajax({
	    url: 'attachment_showAtts.do',
	    type: 'POST',
	    cache:false,
	    async:false,
	    data:{
	    	'tagid':tagid,'docguid':docguid,'deleteAble':deleteAble,'downloadAble':downloadAble,
	    	'previewAble':previewAble,'tocebAble':tocebAble,'toStampAble':toStampAble,'printStampName':printStampName,
	    	'detachStampAble':detachStampAble,'openBtnClass':openBtnClass,'otherBtnsClass':otherBtnsClass,'showId':showId,
	    	'isReciveAtt':isReciveAtt,'nodeId':nodeId,'onlineEditAble':onlineEditAble,'isFirst':isFirst
	    },
	    error: function(){
	        alert('\u9644\u4ef6\u52a0\u8f7d\u5931\u8d25');
	    },
	    success: function(retValue){
			var size = getPageSize();
	    	if(docguid != null && docguid.indexOf("attzw") != -1){
				$(".file-type-2").empty().append(retValue);
				var fjHeight = $(".file-type-2").height()
				var c_btn_height = $(".btn-type-2").height()
				document.getElementById("frame1").contentWindow.setPageSize(size[3] - fjHeight - c_btn_height -144, 1024);
			}else{
				$(".file-type-1").empty().append(retValue);
				var fjHeight = $(".file-type-1").height()
				var c_btn_height = $(".btn-type-1").height()
				document.getElementById("frame1").contentWindow.setPageSize(size[3] - fjHeight - c_btn_height - 144, 1024);
			}
		   /*if(showId!=""){
		   		$("#"+showId).html(retValue);
		   }else{
		 	  	$("#"+tagid+"_attbody").html(retValue);
		   }*/
	    }
	});	
}

function openAttsUpDialog(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId){
	var url = 'attachment_openAttsDialog.do?docguid='+docguid+"&isReciveAtt="+isReciveAtt;
	var ret = window.showModalDialog(url,window,'dialogWidth:600px; dialogHeight:400px; center:yes; help:no; status:no; resizable:yes;scroll:no;');
	initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId);
	callback(tagid+"_delete_callback");
}

function openAttsextUpDialog(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst){
	if(docguid != null && docguid.indexOf("attzw") != -1){
		var arr = $(".file-type-2 div");
		if(null != arr && arr.length>0){
			alert("请先删除已上传的正文。");
			return;
		}
	}
	var url = getHttpServerUrl()+'/attachment_openWebUploader.do?docguid='+docguid+"&isReciveAtt="+isReciveAtt+"&nodeId="+nodeId;
	SetIsOpenDlg(1);
	if(!canClick){
		alert("请关闭当前打开窗口。");
		return;
	}
	canClick = false;
	try{
	if(top.window && top.window.process && top.window.process.type){
	    var ipc = top.window.nodeRequire('ipc');
	    var remote = top.window.nodeRequire('remote');
	    var browserwindow = remote.require('browser-window');
		var winProps = {};
        winProps.width = 545;
        winProps.height = 400;
		winProps['web-preferences'] = {'plugins':true};
        var focusedId = browserwindow.getFocusedWindow().id;
        attWin = new browserwindow(winProps);
        attWin.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
        attWin.on('closed',function(){
	    	SetIsOpenDlg(0);
	    	canClick=true;
	    	attWin = null;
            initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst);
		    callback(tagid+"_upload_callback");
		});
	    ipc.on('message-departmentTree',function(args){
			if(attWin){
				var ret = args;
				getAttachBt(ret);
				SetIsOpenDlg(0);
				canClick=true;
				attWin.close();
				attWin = null;
				
			}
	    });
	}else{
	    var WinWidth = 545;
	    var WinHeight = 400;
	    attWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
	    var loop = setInterval(function(){
		    if(attWin.closed){
			    clearInterval(loop);
				try{
					var ret = winObj.returnValue;
					SetIsOpenDlg(0);
					canClick=true;
					getAttachBt(ret);
					//---------------------------
					initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst);
					callback(tagid+"_upload_callback");
				} catch(e){
					SetIsOpenDlg(0);
					canClick=true;
					getAttachBt('');
					//---------------------------
					initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst);
					callback(tagid+"_upload_callback");
				}
				
		        
		        //------------------------------
		    }
	    },500);
	}
	}catch (e) {
	    var WinWidth = 545;
	    var WinHeight = 400;
	    attWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
	    var loop = setInterval(function(){
		    if(attWin.closed){
			    clearInterval(loop);
			    var ret = winObj.returnValue;
			    SetIsOpenDlg(0);
			    canClick=true;
			    getAttachBt(ret);
			    //---------------------------
		        initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst);
		        callback(tagid+"_upload_callback");
		        
		        //------------------------------
		    }
	    },500);
	}
}

//高拍仪
function openCaptureDialog(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId){
	var url = 'attachment_openCaptureDialog.do?docguid='+docguid+"&isReciveAtt="+isReciveAtt+"&nodeId="+nodeId;
	var ret=window.showModalDialog(url,window,'dialogWidth='+screen.width+';dialogHeight='+screen.height+'; center:yes; help:no; status:no; resizable:yes;scroll:yes;');
	//上传后率性页面
	initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt, nodeId);
	//回调方法
	callback(tagid+"_upload_callback");
}

function deleteAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,attsId,isFirst){
	msg = "\u786e\u5b9a\u5220\u9664\u5417\uff1f";
	if (window.confirm(msg)) {
		$.ajax({
			cache:false,
			async:false,
			type: "POST",
			url: "attachment_removeAtts.do?attsId="+attsId+"&isReciveAtt="+isReciveAtt,
			error: function(){
		   		alert('\u5220\u9664\u5931\u8d25');
			},
			success: function(msg){
				initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst);
				callback(tagid+"_delete_callback");
			}
		});
	}
}
function deleteAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,attsextId){
	msg = "\u786e\u5b9a\u5220\u9664\u5417\uff1f";
	if (window.confirm(msg)) {
		$.ajax({
			cache:false,
			async:false,
			type: "POST",
			url: "attachment_removeAttsext.do?attsextId="+attsextId+"&isReciveAtt="+isReciveAtt,
			error: function(){
		   		alert('\u5220\u9664\u5931\u8d25');
			},
			success: function(msg){
				initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId);
				callback(tagid+"_delete_callback");
			}
		});
	}
}

function modifyAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,attsId){
	art.dialog({
		lock: false,
	    content: '<'+'iframe src="${ctx}/attachment_toModifyAtts.do?attsId='+attsId+'&isReciveAtt='+isReciveAtt+'" height="200" width="300"></'+'iframe>',
	    id: 'EF893M',
	    height:300,
	    width:400,
	    ok:function(msg){
			window.location.reload();
	    },
	 okVal:'\u5173\u95ed'
	});
}

function download(fileLocation,id,name,downUrl,cb){
	try{
		var nodeRequire = top.nodeRequire
		var __dirname = top.__dirname
		if(nodeRequire){
			var remote = nodeRequire('remote')
			var path = remote.require('path')
			var modulePath = path.resolve(__dirname,'../../../app/download.js')
			var hasError = false
			var destPath = path.resolve(__dirname,'../../../')
			destPath = destPath + '/'+name
			try{
				var downloadFn = remote.require(modulePath).downloadFile
				var dialog = remote.require('dialog')
				var dest = dialog.showSaveDialog({defaultPath:destPath},function(dpath){
					if(dpath){
						cb = cb || function(arr,msg){console.log(arr,msg)}
						//http://192.168.5.77:1000/trueWorkFlow/attachment_download.do?name=WEB安全测试.pdf&location=attachments/2018/04/03/DB645E39-5B1E-453F-A492-812D19199CE8.pdf
						downUrl = downUrl.replace(name,encodeURI(name))
						downloadFn(downUrl,dpath,id,cb)
					}
				})
			} catch(e){
				hasError = true
			}
			if(hasError){
				downUrl = encodeURI(encodeURI(downUrl));
				var obj = document.getElementById("download_iframe");
				if(obj){
					obj.src=downUrl;
				}else{
					window.location.href=downUrl;
				}
			}
		} else {
			downUrl = encodeURI(encodeURI(downUrl));
			var obj = document.getElementById("download_iframe");
			if(obj){
				obj.src=downUrl;
			}else{
				window.location.href=downUrl;
			}
		}
	} catch(e){
		downUrl = encodeURI(encodeURI(downUrl));
		var obj = document.getElementById("download_iframe");
		if(obj){
			obj.src=downUrl;
		}else{
			window.location.href=downUrl;
		}
	}
}

//下载license文件
function downloadLicense(downUrl){
	downUrl = encodeURI(downUrl);
	var obj = document.getElementById("download_iframe");
	if(obj){
		obj.src=downUrl;
	}else{
		window.location.href=downUrl;
	}
}


function httpDownFileLocal(filepathAndName){
	var filepath = filepathAndName;
	var WshShell = new ActiveXObject("WScript.Shell"); 
	var keyValue = WshShell.RegRead('HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Internet Settings\\Cache\\Paths\\Directory');
	var PostRecvImpl = new ActiveXObject("ASPCom.PostRecv");
	var extName = filepath.substring(filepath.lastIndexOf('.'));
	var fileName = new Date().getTime() + extName;
	fileName = keyValue+"\\"+fileName;
	fileName = fileName.replace(/\\/gm,'\\\\');
	PostRecvImpl.HTTPDownloadFile(fileName,filepath);
	return fileName;
}

function uploadLocalFile(uploadUrl,fullFileName){
 	var objAspCom  = new ActiveXObject("ASPCom.postrecv");
    var sendxml = objAspCom.SendFile(uploadUrl,fullFileName);
    if (sendxml != 200){
		var strsendxmlErrMessage = objAspCom.GetErrorMessage();
		alert("\u4e0a\u8f7d\u5931\u8d25!\u8c03\u7528\u7ec4\u4ef6\u8fd4\u56de\u503c:" + sendxml + "\u5931\u8d25\u539f\u56e0:" + strsendxmlErrMessage );
		return false;
	}else{
		return true;
    }
}

function PreviewCebXml(filepath){
	var verReader = 2143;
	var strErrMessage, lRet, strCEBFileUrl, ctrlXml, left, right, top, bottom;
	var opUINavigation, opUISelect, opUISaveAs;
	var opUIFile, opUIZoom, opUILayout;
	var opPluginAttachment, opPluginForm, opPluginVisualStamp, opPluginAnnot;
	strErrMessage = "";
	strCEBFileUrl ="";
	if(filepath == null || filepath == '' || filepath.toLowerCase().indexOf('.ceb') < 1){
		alert('\u8bf7\u9009\u62e9CEB\u683c\u5f0f\u6587\u4ef6\uff01');
		return false;
	}
    ctrlXml = "<?xml version='1.0' encoding='GB2312'?><CPreviewCEBParam><PreviewCEBParamXMLVer></PreviewCEBParamXMLVer>";
    ctrlXml += "<ApabiReaderVer>" + verReader + "</ApabiReaderVer>";
    ctrlXml += "<CEBURL></CEBURL>"; 
    ctrlXml += "<CEBEncypted>0<CEBEncypted>"; 
    ctrlXml += "<CEBFileID></CEBFileID>";
    ctrlXml += "<CEBFilePathAndName>" + filepath + "</CEBFilePathAndName>"; 
    ctrlXml += "<Left>0</Left>"; 
    ctrlXml += "<Right>1</Right>";
    ctrlXml += "<Top>0</Top>"; 
    ctrlXml += "<Bottom>1</Bottom>"; 
    ctrlXml += "<ReaderPlugInVisualStamp>0</ReaderPlugInVisualStamp>"; 
    ctrlXml += "<ReaderPlugInForm>1</ReaderPlugInForm>"; 
    ctrlXml += "<ReaderPlugInAttachment>0</ReaderPlugInAttachment>"; 
    ctrlXml += "<ReaderPlugInAnnotator>1</ReaderPlugInAnnotator>"; 
    ctrlXml += "<ReaderEmbeddingUIFile>1</ReaderEmbeddingUIFile>"; 
    ctrlXml += "<ReaderEmbeddingUINavigation>1</ReaderEmbeddingUINavigation>"; 
    ctrlXml += "<ReaderEmbeddingUISelect>1</ReaderEmbeddingUISelect>"; 
    ctrlXml += "<ReaderEmbeddingUILayout>1</ReaderEmbeddingUILayout>"; 
    ctrlXml += "<ReaderEmbeddingUISaveAs>0</ReaderEmbeddingUISaveAs>"; 
    ctrlXml += "<ReaderEmbeddingUIZoom>1</ReaderEmbeddingUIZoom>"; 
    ctrlXml += "</CPreviewCEBParam>";
	var  objSealStampCom  = new ActiveXObject("SealStampComSvr.SealStampCom");
	lRet = objSealStampCom.PreviewCEBByXML("", ctrlXml);
	return true;
}

function viewCEB(filepathAndName){
	var filepath =httpDownFileLocal(filepathAndName);
	PreviewCebXml(filepath);
}
 
 function viewPDF(fileLocation,filename){
//	 var filename ="";//httpDownFileLocal(fileLocation);
// 	 var strURL = "attachment_viewPdf.do?filename="+filename+"&location="+fileLocation;
// 	 var sheight = screen.height-70;
//     var swidth = screen.width-10;
//     var winoption ="left=0,top=0,height="+sheight+",width="+swidth+",toolbar=yes,menubar=yes,location=yes,status=yes,scrollbars=yes,resizable=yes";
//     var tmp=window.open(strURL,'',winoption);
	 $.ajax({
			url : '${ctx}/attachment_beforeViewDoc.do',
			type : 'POST',   
			cache : false,
			async : false,
			data :{
				'fileLocation':fileLocation
			},
			success : function(msg) {
				if(msg&&(msg.indexOf("table_downloadError.do")!=-1)){
					alert('附件可能包含加密文件，无法查看！');
					return;
				}else{
					var sheight = screen.height-70;
				 	var swidth = screen.width-10;
				 	var url = getHttpServerUrl()+"/attachment_viewDoc.do?filelacation="+fileLocation+"&t=" + new Date().getTime();
				 	try{
				 		SetIsOpenDlg(1);
				 		if(top.window && top.window.process && top.window.process.type){
				 			var ipc = top.window.nodeRequire('ipc');
				 			var win = createWindow(url,true,true);
				 			ipc.on('message-departmentTree-viewpdf',function(args){
				 				if(win){
				 					SetIsOpenDlg(0);
				 	                win.close();
				 					win = null;
				 			        console.info(args);
				 			        //----------------------------
				 			      //----------------------------
				 				}
				 		    });
				 		}else{
				 		    var WinWidth = swidth;
				 		    var WinHeight = sheight;
				 		    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
				 		    var loop = setInterval(function(){
				 			    if(winObj.closed){
				 					console.info(window.returnValue);
				 				    clearInterval(loop);
				 				    SetIsOpenDlg(0);
				 				    //---------------------------
				 			        //------------------------------
				 			    }
				 		    },500);
				 		}
				 	 }catch (e) {
				 		    var WinWidth = swidth;
				 		    var WinHeight = sheight;
				 		    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
				 		    var loop = setInterval(function(){
				 			    if(winObj.closed){
				 					console.info(window.returnValue);
				 				    clearInterval(loop);
				 				    SetIsOpenDlg(0);
				 				    //---------------------------
				 			        //------------------------------
				 			    }
				 		    },500);
				 	}
				}
			}
		});
	 
    
 }

function wordToCEB(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,uploadCebURL,filepathAndName){
	var docname =httpDownFileLocal(filepathAndName);
	
	var objMakercom = new ActiveXObject("MakerCom.MakerExt");
	var nRet1,nRet2,nRet3,nRet4;
	var cebName = docname.substring(0,docname.lastIndexOf(".")+1)+'ceb';
	var names = docname.split(".");
	nRet1 = objMakercom.BeginMaker("");
	if (nRet1 !=0 ){
		var gem1 = objMakercom.GetErrorMessage(nRet1);
		alert ("\u8bf7\u68c0\u67e5Maker\u7ec4\u4ef6\u662f\u5426\u5df2\u5b89\u88c5");
		return "";
	 }
	nRet2 = objMakercom.SingleFileConvert(docname,cebName,"\u6807\u51c6\u6a21\u677f","","");
	if(nRet2 !=0){
		var gem2 = objMakercom.GetErrorMessage(nRet2);
		alert ("\u8f6c\u6362\u9519\u8bef,\u8bf7\u68c0\u67e5\u662f\u5426\u63d2\u5165Maker");
		return "";
	}
	nRet3 = objMakercom.EndMaker();
	 if (nRet3 !=0){
		var gem3 = objMakercom.GetErrorMessage(nRet3);
		return "";
	 }
	 
	 var ret= uploadLocalFile(uploadCebURL,cebName);
	 if(ret===true){
		 var isManAtt=uploadCebURL.split("&isManAtt=")[1];
		 if(isManAtt=="true"){
			 initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt);
		 }else{
			 initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt);
		 }
		 alert("\u8f6c\u6362\u5e76\u5bfc\u5165\u6210\u529f\uff01");
		 return cebName;
	 }
	 return "";
}

function GetCAKeyInfnFun(){
	var  objUsbKeyAux  = new ActiveXObject("UKeyAux.IJszwUsbKey");
	var certinfo = objUsbKeyAux.GetSignCertInfoInKey();
	if(certinfo == null || typeof certinfo == "undefined" || certinfo === undefined || certinfo==""){
		return false;
	}else
	 	return true;
}

function getCEBID(strCebFileName){
	var objGetCEBID, ret1, ret2;
	var objGetCEBID = new ActiveXObject("PrintURLChangeSvr.ChangePrintURL");
	ret1 = objGetCEBID.ChangeURL(strCebFileName, "");
	var nErrorMsg = objGetCEBID.GetErrorMessage();
	var StringObject = new String(nErrorMsg);
	if (StringObject != ("S_OK")){
		delete objGetCEBID;
		return '';	
	}else{
		delete objGetCEBID;
		return ret1;
	}
}

function visualStamp(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,uploadCebURL,filepathAndName,LDAPProxySvr,AffixRegisterURL,Printerror){
	//alert(filepathAndName);
	var cebname =httpDownFileLocal(filepathAndName);
	
	var isHasCA = GetCAKeyInfnFun();
	if(!isHasCA){
		alert("\u6ca1\u6709\u627e\u5230CA\u8bc1\u4e66\u4fe1\u606f\uff0c\u8bf7\u63d2\u5165CA\uff01");
		return false;
	}
	var  objUsbKeyAux  = new ActiveXObject("UKeyAux.IJszwUsbKey");
	var certinfo = "";
	certinfo = objUsbKeyAux.GetSignCertInfoInKey();
	var unitDoc = new ActiveXObject("Msxml2.DOMDocument");
	unitDoc.async = false;
	unitDoc.loadXML(certinfo);
	unitDoc.setProperty("SelectionLanguage", "XPath"); 
	var oRst = unitDoc.selectSingleNode("//CertInfo/CertID");
	var certid = oRst.text;

	var strAuxInfo = ""
      strAuxInfo += "<AffixSign><SignType>1</SignType><CertID>"
      strAuxInfo += certid
      strAuxInfo += "</CertID><LDAPProxySvr>"+LDAPProxySvr+"</LDAPProxySvr></AffixSign><DeviceStyle>5</DeviceStyle>"
      
	var objStampClientTool = new ActiveXObject("StampClientTool.StampTool");
	nRet = objStampClientTool.LocalSealStamp(cebname,'',AffixRegisterURL,Printerror);
	var strCebID = getCEBID(cebname);
	document.getElementById("cebid").value = strCebID;
	uploadLocalFile(uploadCebURL,cebname);
	initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt);
	return true;
}

function detachStamp(filepathAndName){
	var fileName = httpDownFileLocal(filepathAndName);
	var DetachStampImpl = new ActiveXObject("DetachStampSvr.DetachStamp");
	var nRet = DetachStampImpl.Detachdoc(fileName,0);
	if (nRet == 0){
		PreviewCebXml(fileName);
	}else{
		alert("\u8131\u5bc6\u5931\u8d25");
	}
	delete DetachStampImpl;
}

function byxmlprint(filepathAndName,printNumURL,printStampURL,stampName,printSerialText){
	var isHasCA = GetCAKeyInfnFun();
	if(!isHasCA){
		alert("\u6ca1\u6709\u627e\u5230CA\u8bc1\u4e66\u4fe1\u606f\uff0c\u8bf7\u63d2\u5165CA\uff01");
		return false;
	}
  	var	xmlprint = "<?xml version=\"1.0\" encoding=\"GB2312\" ?>" ;
		xmlprint += "<CAutoPrintCEBParam>";
        xmlprint += "<CEBURL></CEBURL>";           								
        xmlprint += "<CEBFilePathAndName>" + filepathAndName + "</CEBFilePathAndName>"; 	
        xmlprint += "<PrintNumURL>" + printNumURL + "</PrintNumURL>";  		
        xmlprint += "<PrintStampURL>" + printStampURL + "</PrintStampURL>";
		xmlprint += "<UnitName>"+stampName+"</UnitName>";                      		 
        xmlprint += "<DeviceStyle></DeviceStyle> ";                        
        xmlprint += "<CheckUnitID></CheckUnitID>";                      	  
        xmlprint += "<PrintSerialText>"+printSerialText+"</PrintSerialText>";  
        xmlprint += "<PrintSerialType>1</PrintSerialType>";             	
		xmlprint += "<WithDKCard></WithDKCard>";                   			
        xmlprint += "<USEPrintDlg>1<USEPrintDlg>";                    		 
        xmlprint += "<CheckPrinter></CheckPrinter>";                   		
        xmlprint += "<PrinterName> </PrinterName>";    						  
        xmlprint += "<PrinterDriverName> </PrinterDriverName>"; 			 
        xmlprint += "<PrintTotalCopies>1</PrintTotalCopies>";    			
        xmlprint += "<PrintBgnPages>1</PrintBgnPages>";     				 
        xmlprint += "<PrintEndPages>2</PrintEndPages>";              		 
        xmlprint += "</CAutoPrintCEBParam>";		
	var ret = 0;
	var objautoprint = new ActiveXObject("AutoPrintsvr.AutoPrint");	
	ret = objautoprint.AutoPrintByXML(xmlprint);		
}

function printStamp(filepathAndName,stampName,printInfoURL,printNumURL_Serialctrl,printNumURL_Numproc,printStampURL){
	var filenPath = httpDownFileLocal(filepathAndName);
	var cebid=getCEBID(filenPath);
	if(cebid==""){
		alert("\u8be5\u9644\u4ef6\u4e0d\u5e26\u7ea2\u7ae0\uff0c\u8bf7\u8fdb\u884c\u666e\u901a\u6253\u5370");
		return;
	}
	var objautoprint = new ActiveXObject("AutoPrintsvr.AutoPrint");
	var ret= objautoprint.GetCEBPrintInfoEx(cebid,stampName,printInfoURL);
	if(document.all){
		ret_xml= new ActiveXObject("Microsoft.XMLDOM");
		ret_xml.async = false;
		ret_xml.loadXML(ret);
		ret_xml = $(ret_xml).children('DocPrintInfo');
	} else {
		ret_xml= (new DOMParser()).parseFromString(ret, 'text/xml');
	}
	var printSerialText =$(ret_xml).find("SerialNum").text();
	if(printSerialText!=''){
		if(printSerialText.match(/\d*-\d*/)!=null){
			printSerialText=printSerialText.match(/\d*/);
		}
		byxmlprint(filenPath,printNumURL_Serialctrl,printStampURL,stampName,printSerialText);
	}else{
		byxmlprint(filenPath,printNumURL_Numproc,printStampURL,stampName,"");
	}
}

function fenshuruku(strCebID,receiver,printNum,counts,StampServer_PrintLic2DB){
	if(strCebID==null || strCebID==''){
		return true;
	}
    var strXml = "";
	strXml += "<?xml version=\"1.0\" encoding=\"gb2312\" ?>"
	strXml += "<Doc>";
	strXml += "<DocumentID>"+strCebID+"</DocumentID>" ;
	strXml += "<Receivers>" + receiver + "</Receivers><PrnNums>" + printNum + "</PrnNums><SendType>1</SendType><Count>"+ counts +"</Count>";
	strXml += "</Doc>";
    var objGetPrintXML  = new ActiveXObject("StampPubCom.StampPubFuncCom");
    
    var WshShell; 
    try 
    { 
    	WshShell = new ActiveXObject("Wscript.Shell"); 
    } 
    catch(e) 
    { 
        alert("\u5f53\u524dIE\u5b89\u5168\u7ea7\u522b\u4e0d\u5141\u8bb8\u64cd\u4f5c!"); 
    }
	var keyValue = WshShell.RegRead('HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Internet Settings\\Cache\\Paths\\Directory');
	var xmlFileName = new Date().getTime() + '.xml';
	xmlFileName = keyValue+"\\"+xmlFileName;
	xmlFileName = xmlFileName.replace(/\\/gm,'\\\\');
 	var getxml = objGetPrintXML.BSTR2File(strXml,xmlFileName);
    var objAspCom  = new ActiveXObject("ASPCom.postrecv");
    var xml2DBUrl = StampServer_PrintLic2DB;
    var sendxml = objAspCom.SendFile(xml2DBUrl,xmlFileName);
    if (sendxml != 200){
		var strsendxmlErrMessage = objAspCom.GetErrorMessage();
		return false;
	}else{
		return true;
    }
}

function getAttCounts(tagid){
	var showid = $("#"+tagid+"_showid").val();
	var attInfo =$("#"+showid).html();
	if(attInfo!=null){
		return attInfo.split("<BR>").length-1;
	}
	return 0;
}

function winOpenFullScreen(strURL){
    var sheight = screen.height-70;
    var swidth = screen.width-10;
    var winoption ="left=0,top=0,height="+sheight+",width="+swidth+",toolbar=yes,menubar=yes,location=yes,status=yes,scrollbars=yes,resizable=yes";
    var tmp=window.open(strURL,'',winoption);
    return tmp;
}

function winModalFullScreen(strURL){
    var sheight = screen.height-100;
    var swidth = screen.width-100;
    var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes";
    var tmp=window.showModalDialog(strURL,window,winoption);
    return tmp;
}

function viewDoc(location){
	var sheight = screen.height-70;
	var swidth = screen.width-10;
	var url = getHttpServerUrl()+"/attachment_viewDoc.do?filelacation="+ location + "&t=" + new Date().getTime();;
	try{
		SetIsOpenDlg(1);
		if(top.window && top.window.process && top.window.process.type){
			var ipc = top.window.nodeRequire('ipc');
			var win = createWindow(url,true,true);
			ipc.on('message-departmentTree-viewdoc',function(args){
				if(win){
					SetIsOpenDlg(0);
	                win.close();
					win = null;
			        console.info(args);
			        //----------------------------
			      //----------------------------
				}
		    });
		}else{
		    var WinWidth = swidth;
		    var WinHeight = sheight;
		    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		    var loop = setInterval(function(){
			    if(winObj.closed){
					console.info(window.returnValue);
				    clearInterval(loop);
				    SetIsOpenDlg(0);
				    //---------------------------
			        //------------------------------
			    }
		    },500);
		}
	 }catch (e) {
		    var WinWidth = swidth;
		    var WinHeight = sheight;
		    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		    var loop = setInterval(function(){
			    if(winObj.closed){
					console.info(window.returnValue);
				    clearInterval(loop);
				    SetIsOpenDlg(0);
				    //---------------------------
			        //------------------------------
			    }
		    },500);
	}
	 
}

var saveAttId = "";
function onlineEdit(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt, nodeId, attId, attLocation, sfqg,isFirst,serverurl,isFq){
	//当打开正文模板时，进行暂存处理，为后续打开表单吧编辑时自动将文号（如果有）从库里获取并显示到表单里
	var rs = operateForm2(0,'1');
	if(rs==false){
		return;
	}
	/*if(!isFq){
		isFq = false;
	}*/
	if(serverurl == null || serverurl == ''){
		serverurl = getHttpServerUrl();
	}
	
   	var sheight = screen.height;
  	var swidth = screen.width;
  	var instanceId = '';
  	if((docguid != null && docguid != '')){
  		instanceId = docguid.substring(0, 36);
  	}
  	
 	var url = serverurl+"/attachment_onlineEditDoc.do?nodeId="+nodeId+"&attLocation=" + attLocation + "&instanceId=" + instanceId + "&attId="+attId + "&sfqg="+sfqg+ "&isFirst=" + isFirst + "&serverurl="+serverurl+"&t=" + new Date().getTime();
	try{
		SetIsOpenDlg(1);
		if(!canClick){
			alert("请关闭当前打开窗口。");
			return;
		}
		canClick = false;
		
 	if(top.window && top.window.process && top.window.process.type){
	    var ipc = top.window.nodeRequire('ipc');
	    var remote = top.window.nodeRequire('remote');
	    var browserwindow = remote.require('browser-window');
		var winProps = {};
        winProps.width = swidth;
        winProps.height = sheight;
		winProps['web-preferences'] = {'plugins':true};
		winProps['node-integration'] = true
        var focusedId = browserwindow.getFocusedWindow().id;
	    var win = new browserwindow(winProps);
        win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
        //win.openDevTools();
	    win.on('closed',function(){
			win = null;
			 SetIsOpenDlg(0);
			 canClick=true;
//			 try{
//				saveAttId = attId;
//				reloadOcx(saveAttId,isFq);
//			 }catch (e) {
//			 }
			saveAttId = attId;
			reloadOcx(saveAttId);
			 $.ajax({
				type : 'post',
				url : '${ctx}/attachment_updateStatus.do',
				data:{
					"attId":attId
				},
				error:function(){
					alert('AJAX调用错误(attachment_updateStatus.do)');
				},
				success:function(data){
					var result = eval("("+data+")");
					if(result.result=='success'){
						
					}
				}
			 });
			//alert("\u4fdd\u5b58\u6210\u529f");
			initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst);
		});

	    ipc.on('onlineEdit',function(args){
			if(win){
				SetIsOpenDlg(0);
				canClick=true;
                win.close();
				win = null;
//				if(args=='success'){
//				try{
//					saveAttId = attId;
//					reloadOcx(saveAttId,isFq);
//				}catch (e) {
//				}
//				}
				saveAttId = attId;
				reloadOcx(saveAttId);
				 $.ajax({
					type : 'post',
					url : '${ctx}/attachment_updateStatus.do',
					data:{
						"attId":attId
					},
					error:function(){
						alert('AJAX调用错误(attachment_updateStatus.do)');
					},
					success:function(data){
						var result = eval("("+data+")");
						if(result.result=='success'){
							
						}
					}
				 });
				//alert("\u4fdd\u5b58\u6210\u529f");
				initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst);
			}
	    });
	}else{
	    var WinWidth = swidth;
	    var WinHeight = sheight;
	    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
	    var loop = setInterval(function(){
		    if(winObj.closed){
//				console.info(winObj.returnValue);
			    clearInterval(loop);
			    SetIsOpenDlg(0);
//			    if(window.returnValue && window.returnValue=='success'){
			   // try{
			    	saveAttId = attId;
//			    	reloadOcx(saveAttId,isFq);
			    	reloadOcx(saveAttId);
			    //}catch (e) {
			//	}
//				}
			    $.ajax({
					type : 'post',
					url : '${ctx}/attachment_updateStatus.do',
					data:{
						"attId":attId
					},
					error:function(){
						alert('AJAX调用错误(attachment_updateStatus.do)');
					},
					success:function(data){
						var result = eval("("+data+")");
						if(result.result=='success'){
							
						}
					}
				 });
			    canClick=true;
			    //alert("\u4fdd\u5b58\u6210\u529f");
				initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst);
		    }
	    },500);
	}
	}catch (e) {
		var WinWidth = swidth;
	    var WinHeight = sheight;
	    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
	    var loop = setInterval(function(){
		    if(winObj.closed){
//				console.info(window.returnValue);
			    clearInterval(loop);
			    SetIsOpenDlg(0);
//			    if(window.returnValue && window.returnValue=='success'){
		    	try{
					saveAttId = attId;
					reloadOcx(saveAttId,isFq);
				}catch (e) {
				}
//			    }
			    $.ajax({
					type : 'post',
					url : '${ctx}/attachment_updateStatus.do',
					data:{
						"attId":attId
					},
					error:function(){
						alert('AJAX调用错误(attachment_updateStatus.do)');
					},
					success:function(data){
						var result = eval("("+data+")");
						if(result.result=='success'){
							
						}
					}
				 });
			    canClick=true;
			    //alert("\u4fdd\u5b58\u6210\u529f");
				initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst);
		    }
	    },500);
	}
			
}

function  viewHistory(docguid,id){
	var sheight =300;
    var swidth = 400;
	var url = getHttpServerUrl()+"/attachment_viewHistory.do?docguid=" + docguid  + "&id="+id+"&t=" +  new Date().getTime();
	try{
		SetIsOpenDlg(1);
		if(!canClick){
			alert("请关闭当前打开窗口。");
			return;
		}
		canClick = false;
		if(top.window && top.window.process && top.window.process.type){
		    var ipc = top.window.nodeRequire('ipc');
		    var remote = top.window.nodeRequire('remote');
		    var browserwindow = remote.require('browser-window');
			//var winId = browserwindow.getFocusedWindow().id;
		    attWin = new browserwindow({width:swidth,height:sheight});
		    attWin.loadUrl(url);
		    //win.openDevTools();
	        attWin.on('closed',function(){
				attWin = null;
			    SetIsOpenDlg(0);
			    canClick=true;
			});		
		    ipc.on('viewHistory',function(args){
				if(attWin){
					SetIsOpenDlg(0);
					canClick=true;
	                win.close();
					win = null;
			        //----------------------------
			        //----------------------------
				}
		    });
		}else{
		    var WinWidth = swidth;
		    var WinHeight = sheight;
		    attWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		    var loop = setInterval(function(){
			    if(attWin.closed){
					console.info(window.returnValue);
				    clearInterval(loop);
				    SetIsOpenDlg(0);
				    canClick=true;
				    //---------------------------
			        //------------------------------
			    }
		    },500);
		}
	}catch (e) {
		var WinWidth = swidth;
	    var WinHeight = sheight;
	    attWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
	    var loop = setInterval(function(){
		    if(attWin.closed){
				console.info(window.returnValue);
			    clearInterval(loop);
			    SetIsOpenDlg(0);
			    canClick=true;
			    //---------------------------
		        //------------------------------
		    }
	    },500);
	}
	
}

//打开上传正文模板页面
function openUploadTemplate(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId,isFirst,serverurl){
	//当打开正文模板时，进行暂存处理，为后续打开表单吧编辑时自动将文号（如果有）从库里获取并显示到表单里
	var rs = operateForm2(0,'1');
	if(rs==false){
		return;
	}
	if(docguid != null && docguid.indexOf("attzw") != -1){
		var arr = $(".file-type-2 div");
		if(null != arr && arr.length>0){
			alert("已存在正文，请删除后，再选择模板。");
			return;
		}
	}
	var sheight = 300;
	var swidth = 400;
	if(null == serverurl || '' == serverurl){
		serverurl = getHttpServerUrl();
	}
	var url = serverurl+"/attachment_openUploadtemplate.do?docguid=" + docguid  + "&nodeId="+nodeId+"&t=" +  new Date().getTime();
	SetIsOpenDlg(1);
	if(!canClick){
		alert("请关闭当前打开窗口。");
		return;
	}
	canClick = false;
	try {
		if(top.window && top.window.process && top.window.process.type){
	        var ipc = top.window.nodeRequire('ipc');
		    var remote = top.window.nodeRequire('remote');
		    var browserwindow = remote.require('browser-window');
			var winProps = {};
	        winProps.width = swidth;
	        winProps.height = sheight;
			winProps['web-preferences'] = {'plugins':true};
	        var focusedId = browserwindow.getFocusedWindow().id;
	        attWin = new browserwindow(winProps);
	        attWin.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
	        attWin.on('closed',function(){
				attWin = null;
			    SetIsOpenDlg(0);
			    canClick=true;
			});
	        ipc.on('msg-openUploadTemplate',function(msg){
	        	if(attWin){
	        		//取回的msg现在包含attid和templateid，分号隔开
					var ret = msg;
					SetIsOpenDlg(0);
					canClick=true;
					attWin.close();
					attWin = null;
					if(ret != null && ret != ''){
		                 //initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId);
		     		    //callback(tagid+"_upload_callback");
		     		    onlineEdit(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt, nodeId, ret, '', '',isFirst,serverurl,true);
		        	}
				}
			});
		}else{
		    var WinWidth = swidth;
		    var WinHeight = sheight;
		    attWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		    var loop = setInterval(function(){
			    if(attWin.closed){
				    clearInterval(loop);
				    var ret = attWin.returnValue;
				    SetIsOpenDlg(0);
				    canClick=true;
				    if(ret != null && ret != ''){
				        //initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId);
				        //callback(tagid+"_upload_callback");
					    onlineEdit(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt, nodeId, ret, '', '',isFirst,serverurl,true);
				    }
			    }
		    },500);
		}
	} catch (e) {
		 var WinWidth = swidth;
		 var WinHeight = sheight;
		 attWin = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		 var loop = setInterval(function(){
			 if(attWin.closed){
				 clearInterval(loop);
				 var ret = winObj.returnValue;
				 SetIsOpenDlg(0);
				 canClick=true;
				 if(ret != null && ret != ''){
					 //initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,nodeId);
				     //callback(tagid+"_upload_callback");
					 onlineEdit(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt, nodeId, ret, '', '',isFirst,serverurl,true);
				 }
			 }
		 },500);
	}
	
}


function refreshAtt(tagid){  
	$("#"+tagid+"_ref").click();
	var deleteCallback = $("#"+tagid+"_delete_callback").val();
	var uploadCallback = $("#"+tagid+"_upload_callback").val();
	if(deleteCallback!=uploadCallback){
		callback(tagid+"_delete_callback");
		callback(tagid+"_upload_callback");
	}else{
		callback(tagid+"_upload_callback");
	} 
}
 
function downloadOnlyPdf(id,name,downUrl,fileType){
	try{
		var nodeRequire = top.nodeRequire
		var __dirname = top.__dirname
		if(nodeRequire){
			var remote = nodeRequire('remote')
			var path = remote.require('path')
			var modulePath = path.resolve(__dirname,'../../../app/download.js')
			var hasError = false
			var destPath = path.resolve(__dirname,'../../../')
			destPath = destPath + '/'+name
			try{
				var downloadFn = remote.require(modulePath).downloadFile
				var dialog = remote.require('dialog')
				var dest = dialog.showSaveDialog({defaultPath:destPath},function(dpath){
					if(dpath){
						cb = cb || function(arr,msg){console.log(arr,msg)}
						//http://192.168.5.77:1000/trueWorkFlow/attachment_download.do?name=WEB安全测试.pdf&location=attachments/2018/04/03/DB645E39-5B1E-453F-A492-812D19199CE8.pdf
						downUrl = downUrl.replace(name,encodeURI(name))
						downloadFn(downUrl,dpath,id,cb)
					}
				})
			} catch(e){
				hasError = true
			}
			if(hasError){
				downUrl = encodeURI(encodeURI(downUrl));
				var obj = document.getElementById("download_iframe");
				if(obj){
					obj.src=downUrl;
				}else{
					window.location.href=downUrl;
				}
			}
		} else {
			downUrl = encodeURI(encodeURI(downUrl));
			var obj = document.getElementById("download_iframe");
			if(obj){
				obj.src=downUrl;
			}else{
				window.location.href=downUrl;
			}
		}
	} catch(e){
		downUrl = encodeURI(encodeURI(downUrl));
		var obj = document.getElementById("download_iframe");
		if(obj){
			obj.src=downUrl;
		}else{
			window.location.href=downUrl;
		}
	}
}

$('.otherdownload').each(function(){
	$(this).bind('click',function(){
		var id=$(this).attr('id');
		if($(this).attr('show')!=='true'){
			$('#content-'+id).fadeIn();
			$(this).attr('show','true');
		}else{
			$('#content-'+id).fadeOut();
			$(this).attr('show','false');
			}
		})
});

(function ($) {
 $.extend({
  Request: function (m) {
   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
   return sValue ? sValue[1] : sValue;
  },
  UrlUpdateParams: function (url, name, value) {
   var r = url;
   if (r != null && r != 'undefined' && r != "") {
    value = encodeURIComponent(value);
    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
    var tmp = name + "=" + value;
    if (url.match(reg) != null) {
     r = url.replace(eval(reg), tmp);
    }
    else {
     if (url.match("[\?]")) {
      r = url + "&" + tmp;
     } else {
      r = url + "?" + tmp;
     }
    }
   }
   return r;
  }
 
 });
})(jQuery);