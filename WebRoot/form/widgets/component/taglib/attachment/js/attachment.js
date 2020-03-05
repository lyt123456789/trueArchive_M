//回调方法
function callback(inputId){
   var callbackMethod = $("#"+inputId).val();
   if(callbackMethod!="null"&&callbackMethod!=""){ 
   		try{
			eval(callbackMethod+"()");
		}catch(e){
			alert("回调失败，请检查方法："+callbackMethod+"()是否存在");
		}
   }
}

/**
 * tagid,docguid,deleteAble,downloadAble,previewAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt
 * @param {Object} tagid
 * @param {Object} docguid
 * @param {Object} deleteAble
 * @param {Object} downloadAblec
 * @param {Object} previewAble
 * @param {Object} tocebAble
 * @param {Object} toStampAble
 * @param {Object} printStampName
 * @param {Object} detachStampAble
 * @param {Object} openBtnClass
 * @param {Object} otherBtnsClass
 * @param {Object} showId
 * @param {Object} isReciveAtt
 */
function initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt){
	$.ajax({
	    url: 'attachment_showAtts.do',
	    type: 'POST',
	    cache:false,
	    async:false,
	    data:{'tagid':tagid,'docguid':docguid,'deleteAble':deleteAble,'downloadAble':downloadAble,'previewAble':previewAble,'onlineEditAble':onlineEditAble,'tocebAble':tocebAble,'toStampAble':toStampAble,'printStampName':printStampName,'detachStampAble':detachStampAble,'openBtnClass':openBtnClass,'otherBtnsClass':otherBtnsClass,'showId':showId,'isReciveAtt':isReciveAtt},
	    error: function(){
	        alert('附件加载失败');
	    },
	    success: function(retValue){
		   if(showId!=""){
		   		$("#"+showId).html(retValue);
		   }else{
		 	  	$("#"+tagid+"_attbody").html(retValue);
		   }
		   if(retValue!=""){
				var attsCount = retValue.split("<BR>").length-1;
				//alert(attsCount);
				if(tocebAble=="true"&&attsCount>1){ 
					$("#"+tagid+"_toceb").attr("disabled","true");
				}
				//$("#"+tagid+"_upload").attr("disabled",true);
				//$("#"+tagid+"_upload").css("display","none");
				$("#"+tagid+"_attfoot").css("display","none");
			}else{
				//$("#"+tagid+"_upload").attr("disabled",false);
				$("#"+tagid+"_attfoot").css("display","block");
			}
	    }
	});	
}

function initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt){
	$.ajax({
	    url: 'attachment_showAttsext.do',
	    type: 'POST',
	    cache:false,
	    async:false,
	    data:{'tagid':tagid,'docguid':docguid,'deleteAble':deleteAble,'downloadAble':downloadAble,'previewAble':previewAble,'tocebAble':tocebAble,'toStampAble':toStampAble,'printStampName':printStampName,'detachStampAble':detachStampAble,'openBtnClass':openBtnClass,'otherBtnsClass':otherBtnsClass,'showId':showId,'isReciveAtt':isReciveAtt},
	    error: function(){
	        alert('附件加载失败');
	    },
	    success: function(retValue){
	       if(retValue) $("#"+tagid).css("display","");
		   if(showId!=""){
		   		$("#"+showId).html(retValue);
		   }else{
		 	  	$("#"+tagid+"_attbody").html(retValue);
		   }
	    }
	});	
}

function openAttsUpDialog(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt){
	var url = 'attachment_openAttsDialog.do?docguid='+docguid+"&isReciveAtt="+isReciveAtt;
	var ret = window.showModalDialog(url,window,'dialogWidth:500px; dialogHeight:300px; center:yes; help:no; status:no; resizable:yes;scroll:no;');
	initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt);
	//回调方法
	callback(tagid+"_delete_callback");
}

function openAttsextUpDialog(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt){
	var url = 'attachment_openAttsextDialog.do?docguid='+docguid+"&isReciveAtt="+isReciveAtt;;
	var ret=window.showModalDialog(url,window,'dialogWidth:500px; dialogHeight:300px; center:yes; help:no; status:no; resizable:yes;scroll:yes;');
	initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt);
	//回调方法
	callback(tagid+"_upload_callback");
}

function deleteAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,attsId){
	msg = "确定删除吗？";
	if (window.confirm(msg)) {
		$.ajax({
			cache:false,
			async:false,
			type: "POST",
			url: "attachment_removeAtts.do?attsId="+attsId+"&isReciveAtt="+isReciveAtt,
			error: function(){
		   		alert('删除失败');
			},
			success: function(msg){
				initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt);
				//回调方法
				callback(tagid+"_delete_callback");
			}
		});
	}
}
function deleteAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,attsextId){
	msg = "确定删除吗？";
	if (window.confirm(msg)) {
		$.ajax({
			cache:false,
			async:false,
			type: "POST",
			url: "attachment_removeAttsext.do?attsextId="+attsextId+"&isReciveAtt="+isReciveAtt,
			error: function(){
		   		alert('删除失败');
			},
			success: function(msg){
				initAttsext(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt);
				//回调方法
				callback(tagid+"_delete_callback");
			}
		});
	}
}

function modifyAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,attsId){
	art.dialog({
		lock: false,
	    content: '<'+'iframe src="${ctx}/attachment_toModifyAtts.do?attsId='+attsId+'&isReciveAtt='+isReciveAtt+'" height="200" width="300"></'+'iframe>',
	    id: 'EF893M',
	    height:300,
	    width:400,
	    ok:function(msg){
			window.location.reload();
	    },
	 okVal:'关闭'
	});
}

function download(fileLocation,downUrl){
	$.ajax({
		cache:false,
		async:false,
		type: "POST",
		url: "attachment_checkFileExist.do?location="+fileLocation,
		error: function(){
	   		alert('异步检查文件是否存在失败');
		},
		success: function(msg){
			if(msg=="no"){
				alert('附件不存在,无法下载'); 
			}else if(msg=="yes"){
				downUrl = encodeURI(downUrl);
				window.location.href=downUrl;
			}
		}
	});
}

/**
 * 下载文件到IE本地缓存目录下
 * 
 */
function httpDownFileLocal(filepathAndName){
	var filepath = filepathAndName;
	var WshShell = new ActiveXObject("WScript.Shell"); 
	//缓存目录
	var keyValue = WshShell.RegRead('HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Internet Settings\\Cache\\Paths\\Directory');
	var PostRecvImpl = new ActiveXObject("ASPCom.PostRecv");
	var extName = filepath.substring(filepath.lastIndexOf('.'));
	var fileName = new Date().getTime() + extName;
	fileName = keyValue+"\\"+fileName;
	fileName = fileName.replace(/\\/gm,'\\\\');
	PostRecvImpl.HTTPDownloadFile(fileName,filepath);
	return fileName;
}

/**
 * 上传文件到服务器
 * @param {Object} uploadUrl
 * @param {Object} fullFileName
 */
function uploadLocalFile(uploadUrl,fullFileName){
 	var objAspCom  = new ActiveXObject("ASPCom.postrecv");
    var sendxml = objAspCom.SendFile(uploadUrl,fullFileName);
    if (sendxml != 200){
		var strsendxmlErrMessage = objAspCom.GetErrorMessage();
		alert("上载失败!调用组件返回值:" + sendxml + "失败原因:" + strsendxmlErrMessage );
		return false;
	}else{
		//alert ("上传成功!");
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
	//var strlocalcebfile = document.getElementById('cebFileName').value;
	//cebname = cebname.replace(/\\/g,"/");
	//alert(cebname);
	if(filepath == null || filepath == '' || filepath.toLowerCase().indexOf('.ceb') < 1){
		alert('请选择CEB格式文件！');
		return false;
	}
    ctrlXml = "<?xml version='1.0' encoding='GB2312'?><CPreviewCEBParam><PreviewCEBParamXMLVer></PreviewCEBParamXMLVer>";
    ctrlXml += "<ApabiReaderVer>" + verReader + "</ApabiReaderVer>";
    ctrlXml += "<CEBURL></CEBURL>"; //预览服务器端文件时传入
    ctrlXml += "<CEBEncypted>0<CEBEncypted>"; //ceb文件是否加密,1表示是;0表示否
    ctrlXml += "<CEBFileID></CEBFileID>";
    ctrlXml += "<CEBFilePathAndName>" + filepath + "</CEBFilePathAndName>"; //预览本地文件时传入
    ctrlXml += "<Left>0</Left>"; // 打开reader左边框位置 
    ctrlXml += "<Right>1</Right>"; // 打开reader后边框位置 
    ctrlXml += "<Top>0</Top>"; // 打开reader上边框位置 
    ctrlXml += "<Bottom>1</Bottom>"; // 打开reader下边框位置 
    ctrlXml += "<ReaderPlugInVisualStamp>0</ReaderPlugInVisualStamp>"; // 是否显示盖章按钮:1表示显示;0表示不显示
    ctrlXml += "<ReaderPlugInForm>1</ReaderPlugInForm>"; // 是否显示打印按钮,此参数暂时无效 
    ctrlXml += "<ReaderPlugInAttachment>0</ReaderPlugInAttachment>"; // 是否显示附件按钮,此参数暂时无效 
    ctrlXml += "<ReaderPlugInAnnotator>1</ReaderPlugInAnnotator>"; // 是否显示注释按钮 
    ctrlXml += "<ReaderEmbeddingUIFile>1</ReaderEmbeddingUIFile>"; // 是否显示File工具栏 
    ctrlXml += "<ReaderEmbeddingUINavigation>1</ReaderEmbeddingUINavigation>"; // 是否显示Navigation工具栏 
    ctrlXml += "<ReaderEmbeddingUISelect>1</ReaderEmbeddingUISelect>"; // 是否显示Select工具栏 
    ctrlXml += "<ReaderEmbeddingUILayout>1</ReaderEmbeddingUILayout>"; // 是否显示Layout(分页显示)工具栏 
    ctrlXml += "<ReaderEmbeddingUISaveAs>0</ReaderEmbeddingUISaveAs>"; // 是否显示SaveAs工具栏,此参数暂时无效 
    ctrlXml += "<ReaderEmbeddingUIZoom>1</ReaderEmbeddingUIZoom>"; // 是否显示Zoom工具栏 
    ctrlXml += "</CPreviewCEBParam>";
	//alert (ctrlXml);
	var  objSealStampCom  = new ActiveXObject("SealStampComSvr.SealStampCom");
	// 预览ceb文件by xml字符串	
	lRet = objSealStampCom.PreviewCEBByXML("", ctrlXml);
	return true;
}

/**
 * 预览CEB
 *
 */
function viewCEB(filepathAndName){
	//alert(filepathAndName);
	var filepath =httpDownFileLocal(filepathAndName);
	PreviewCebXml(filepath);
}
 
 /**
  * 预览PDF
  *
  */
 function viewPDF(fileLocation){
	 var filename =httpDownFileLocal(fileLocation);
 	 var strURL = "attachment_viewPdf.do?filename="+filename+"&location="+fileLocation;
 	 var sheight = screen.height-70;
     var swidth = screen.width-10;
     var winoption ="left=0,top=0,height="+sheight+",width="+swidth+",toolbar=yes,menubar=yes,location=yes,status=yes,scrollbars=yes,resizable=yes";
     var tmp=window.open(strURL,'',winoption);
 }

/**
 * Word转CEB
 * 
 */
function wordToCEB(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,uploadCebURL,filepathAndName){
	//alert(filepathAndName);
	var docname =httpDownFileLocal(filepathAndName);
	//alert(docname);
	
	var objMakercom = new ActiveXObject("MakerCom.MakerExt");
	var nRet1,nRet2,nRet3,nRet4;
	var cebName = docname.substring(0,docname.lastIndexOf(".")+1)+'ceb';
	var names = docname.split(".");
	//1、打开Maker
	nRet1 = objMakercom.BeginMaker("");
	if (nRet1 !=0 ){
		var gem1 = objMakercom.GetErrorMessage(nRet1);
		alert ("请检查Maker组件是否已安装");
		return "";
	 }
 	//2、单文件转化   参数：[需要转换的DOC文件名],[转换生成的CEB文件名], [转换使用模板名称],[生成日志的文件名],[生成消息的文件名]
	nRet2 = objMakercom.SingleFileConvert(docname,cebName,"标准模板","","");
	if(nRet2 !=0){
		var gem2 = objMakercom.GetErrorMessage(nRet2);
		alert ("转换错误,请检查是否插入Maker");
		return "";
	}
	//3、关闭maker
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
		 alert("转换并导入成功！");
		 return cebName;
	 }
	 return "";
}

/**
 * 判断是否插入CA证书，如果不能正确获取CA信息则返回false否则返回true
 */
function GetCAKeyInfnFun(){
	var  objUsbKeyAux  = new ActiveXObject("UKeyAux.IJszwUsbKey");
	var certinfo = objUsbKeyAux.GetSignCertInfoInKey();
	if(certinfo == null || typeof certinfo == "undefined" || certinfo === undefined || certinfo==""){
		return false;
	}else
	 	return true;
}

/**
 * 获取公章ID,如果该文件盖过章，就返回cebid信息，如果没有则返回空 
 * @param {Object} strCebFileName
 */
function getCEBID(strCebFileName){
	var objGetCEBID, ret1, ret2;
	var objGetCEBID = new ActiveXObject("PrintURLChangeSvr.ChangePrintURL");
	ret1 = objGetCEBID.ChangeURL(strCebFileName, "");
	// 判断成功与否
	var nErrorMsg = objGetCEBID.GetErrorMessage();
	var StringObject = new String(nErrorMsg);
	if (StringObject != ("S_OK")){
		//alert(StringObject);
		delete objGetCEBID;
		return '';	
	}else{
		delete objGetCEBID;
		return ret1;
	}
}

/**
 * 盖章
 *  
 */
function visualStamp(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,uploadCebURL,filepathAndName,LDAPProxySvr,AffixRegisterURL,Printerror){
	//alert(filepathAndName);
	var cebname =httpDownFileLocal(filepathAndName);
	
	var isHasCA = GetCAKeyInfnFun();//如果返回值为false则表示没有CA证书
	if(!isHasCA){
		alert("没有找到CA证书信息，请插入CA！");
		return false;
	}
	var  objUsbKeyAux  = new ActiveXObject("UKeyAux.IJszwUsbKey");
	var certinfo = "";
	certinfo = objUsbKeyAux.GetSignCertInfoInKey();
	//alert ("certinfo="+certinfo); 
	//从xml格式字符串中取到certid列的值,然后拼出盖章时需要的字符串strAuxInfo
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
      
	//alert(strAuxInfo);
	//var AffixRegisterURL,PrintURL,serverip,dept,nRet,varRetXml;
	var objStampClientTool = new ActiveXObject("StampClientTool.StampTool");
	//采用意源Ukey进行盖章操作
	//lRet = objStampClientTool.LocalSealStampEx2(cebname, '', AffixRegisterURL, Printerror, strAuxInfo, varRetXml);
	//采用原来U盘形式的本地化盖章
	nRet = objStampClientTool.LocalSealStamp(cebname,'',AffixRegisterURL,Printerror);
	//获取CEBID
	var strCebID = getCEBID(cebname);
	//将CEBID存到页面的隐藏域中
	document.getElementById("cebid").value = strCebID;
	uploadLocalFile(uploadCebURL,cebname);
	initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt);
	return true;
}

/**
 * 脱密
 * @param {Object} filepathAndName
 */
function detachStamp(filepathAndName){
	var fileName = httpDownFileLocal(filepathAndName);
	//alert(fileName);
	var DetachStampImpl = new ActiveXObject("DetachStampSvr.DetachStamp");
	var nRet = DetachStampImpl.Detachdoc(fileName,0);
	if (nRet == 0){
		PreviewCebXml(fileName);
	}else{
		alert("脱密失败");
	}
	delete DetachStampImpl;
}

function byxmlprint(filepathAndName,printNumURL,printStampURL,stampName,printSerialText){
	var isHasCA = GetCAKeyInfnFun();//如果返回值为false则表示没有CA证书
	if(!isHasCA){
		alert("没有找到CA证书信息，请插入CA！");
		return false;
	}
  	var	xmlprint = "<?xml version=\"1.0\" encoding=\"GB2312\" ?>" ;
		xmlprint += "<CAutoPrintCEBParam>";
        xmlprint += "<CEBURL></CEBURL>";           								 //  <!--  网络上CEB的URL，如果使用此url，CEBFilePathAndName必须为空 -->
        xmlprint += "<CEBFilePathAndName>" + filepathAndName + "</CEBFilePathAndName>"; 	 // <!—-本地CEB的路径 -->
        xmlprint += "<PrintNumURL>" + printNumURL + "</PrintNumURL>";  		 //  <!-- 验证打印份数URL，如果此项不为空，则先从该地址验证打印份数 -->
        xmlprint += "<PrintStampURL>" + printStampURL + "</PrintStampURL>";	// <!-- 验证公章URL，如果用了PrintNumURL则只能用SendPrint.aspx否则会重复扣份数 -->
		xmlprint += "<UnitName>"+stampName+"</UnitName>";                      		 	//  <!--  单位公章名称 -->
        xmlprint += "<DeviceStyle></DeviceStyle> ";                         //  <!-- 设备类型 1表示U盘 -->
        xmlprint += "<CheckUnitID></CheckUnitID>";                      	  //   <!-- 是否检查单位标识-->
        xmlprint += "<PrintSerialText>"+printSerialText+"</PrintSerialText>";   // <!-- 打印编号文本信息，如果不为空则输出打印编号，内容为该文本-->
        xmlprint += "<PrintSerialType>1</PrintSerialType>";             	  //   <!-- 打印编号样式类型，暂时没有用到-->
		xmlprint += "<WithDKCard></WithDKCard>";                   			  //   <!-- 默认必须没有值 -->
        xmlprint += "<USEPrintDlg>1<USEPrintDlg>";                    		 //    <!--  是否用打印机对话框选择打印机 -->
        xmlprint += "<CheckPrinter></CheckPrinter>";                   		  //   <!-- 检查打印机，目前适用于HP3800 -->
        xmlprint += "<PrinterName> </PrinterName>";    						   // <!-- 打印机名称，USEPrintDlg不等1时有效 -->
        xmlprint += "<PrinterDriverName> </PrinterDriverName>"; 			   //<!-- 打印机驱动名称，USEPrintDlg不等1时有效-->
        xmlprint += "<PrintTotalCopies>1</PrintTotalCopies>";    			  // <!-- 打印份数，USEPrintDlg不等1时有效-->
        xmlprint += "<PrintBgnPages>1</PrintBgnPages>";     				  // <!-- 打印起始页号，USEPrintDlg不等1时有效-->
        xmlprint += "<PrintEndPages>2</PrintEndPages>";              		  //     <!-- 打印结束页号，USEPrintDlg不等1时有效--> 
        xmlprint += "</CAutoPrintCEBParam>";		
	var ret = 0;
	//alert  (xmlprint);
	var objautoprint = new ActiveXObject("AutoPrintsvr.AutoPrint");	
	ret = objautoprint.AutoPrintByXML(xmlprint);		
}

/**
 * 带红章打印
 * @param {Object} filepathAndName
 */
function printStamp(filepathAndName,stampName,printInfoURL,printNumURL_Serialctrl,printNumURL_Numproc,printStampURL){
	var filenPath = httpDownFileLocal(filepathAndName);
	var cebid=getCEBID(filenPath);
	if(cebid==""){
		alert("该附件不带红章，请进行普通打印");
		return;
	}
	var objautoprint = new ActiveXObject("AutoPrintsvr.AutoPrint");
	var ret= objautoprint.GetCEBPrintInfoEx(cebid,stampName,printInfoURL);
	if(document.all){
		ret_xml= new ActiveXObject("Microsoft.XMLDOM");
		ret_xml.async = false;
		ret_xml.loadXML(ret);
		ret_xml = $(ret_xml).children('DocPrintInfo'); //这里的nodes为最顶级的节点
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

//份数入库
function fenshuruku(strCebID,receiver,printNum,counts,StampServer_PrintLic2DB){
	//如果CEBID为空就表示不需要人进行份数入库操作
	if(strCebID==null || strCebID==''){
		return true;
	}
    //拼装入库xml字符串    		
    var strXml = "";
	strXml += "<?xml version=\"1.0\" encoding=\"gb2312\" ?>"
	strXml += "<Doc>";
	strXml += "<DocumentID>"+strCebID+"</DocumentID>" ;
	strXml += "<Receivers>" + receiver + "</Receivers><PrnNums>" + printNum + "</PrnNums><SendType>1</SendType><Count>"+ counts +"</Count>";
	strXml += "</Doc>";
	//alert(strXml);
	//return;
    //xml字符串生成本地xml文件,位置为c:\xml.xml  
    var objGetPrintXML  = new ActiveXObject("StampPubCom.StampPubFuncCom");
    
    var WshShell; 
    try 
    { 
    	WshShell = new ActiveXObject("Wscript.Shell"); 
    } 
    catch(e) 
    { 
        alert("当前IE安全级别不允许操作!"); 
    }
	var keyValue = WshShell.RegRead('HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Internet Settings\\Cache\\Paths\\Directory');
	var xmlFileName = new Date().getTime() + '.xml';
	xmlFileName = keyValue+"\\"+xmlFileName;
	xmlFileName = xmlFileName.replace(/\\/gm,'\\\\');
 	var getxml = objGetPrintXML.BSTR2File(strXml,xmlFileName);
   	//份数入库
    var objAspCom  = new ActiveXObject("ASPCom.postrecv");
    var xml2DBUrl = StampServer_PrintLic2DB;
    var sendxml = objAspCom.SendFile(xml2DBUrl,xmlFileName);
	//alert(sendxml);
    if (sendxml != 200){
		var strsendxmlErrMessage = objAspCom.GetErrorMessage();
		//alert("上载打印控制信息失败!调用组件返回值:" + sendxml + "失败原因:" + strsendxmlErrMessage );
		return false;
	}else{
		//alert ("份数入库成功!");
		return true;
    }
}

/**
 * 根据标签ID得到已上传附件的个数
 * @param {Object} tagid
 */
function getAttCounts(tagid){
	var showid = $("#"+tagid+"_showid").val();
	var attInfo =$("#"+showid).html();
	if(attInfo!=null){
		return attInfo.split("<BR>").length-1;
	}
	return 0;
}

//全屏显示打开的窗口（window.open）
function winOpenFullScreen(strURL){
    var sheight = screen.height-70;
    var swidth = screen.width-10;
    var winoption ="left=0,top=0,height="+sheight+",width="+swidth+",toolbar=yes,menubar=yes,location=yes,status=yes,scrollbars=yes,resizable=yes";
    var tmp=window.open(strURL,'',winoption);
    return tmp;
}

//全屏显示打开的窗口（window.showModalDialog）
function winModalFullScreen(strURL){
    var sheight = screen.height-100;
    var swidth = screen.width-100;
    var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes";
    var tmp=window.showModalDialog(strURL,window,winoption);
    return tmp;
}
function viewDoc(location){
	winOpenFullScreen("attachment_viewDoc.do?filelacation="+location);
}

function onlineEdit(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt,attId, attLocation){
	$.ajax({
		cache: false,
		async: false,
		type: "POST",
		url: "attachment_checkFileExist.do?location=" + attLocation,
		error: function(){
			alert('异步检查文件是否存在失败');
		},
		success: function(msg){
			if (msg == "no") {
				alert('模板文件不存在,无法打开');
			}
			else if (msg == "yes") {
		       	var sheight = screen.height-70;
   			  	var swidth = screen.width-10;
   			  	var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes";
		 	 	var url = "${ctx}/attachment_onlineEditDoc.do?attLocation=" + attLocation + "&attId="+attId + "&t=" + new Date().getTime();
				var ret = window.showModalDialog(url,window, winoption);
				if (ret == "success") {
					alert("保存成功");
					initAtts(tagid,docguid,deleteAble,downloadAble,previewAble,onlineEditAble,tocebAble,toStampAble,printStampName,detachStampAble,openBtnClass,otherBtnsClass,showId,isReciveAtt);
				}
			}
		}
	});
}

function  viewHistory(docguid,id){
	var sheight =300;
    var swidth = 400;
    var winoption ="left=0,top=0,height="+sheight+",width="+swidth+",toolbar=yes,menubar=yes,location=yes,status=yes,scrollbars=yes,resizable=yes";
	var url = "${ctx}/attachment_viewHistory.do?docguid=" + docguid  + "&id="+id+"&t=" +  new Date().getTime();
	var ret = window.showModalDialog(url,"", winoption);
}

//重新加载初始化方法
function refreshAtt(tagid){  
	$("#"+tagid+"_ref").click();
	
	//回调方法
	var deleteCallback = $("#"+tagid+"_delete_callback").val();
	var uploadCallback = $("#"+tagid+"_upload_callback").val();
	if(deleteCallback!=uploadCallback){
		callback(tagid+"_delete_callback");
		callback(tagid+"_upload_callback");
	}else{
		callback(tagid+"_upload_callback");
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
