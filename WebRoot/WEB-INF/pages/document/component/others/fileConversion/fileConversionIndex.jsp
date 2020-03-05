<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>文件转换页面</title>
		<meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <META HTTP-EQUIV="Expires" CONTENT="0">
        <%@ include file="/common/header.jsp"%>
	</head>

	<body>
		<div><span>公文管理&nbsp;→&nbsp;文件转换</span></div>
		<form id="tifToWordForm" method="post" ENCTYPE="multipart/form-data">
			<div style="height:33px; margin-top:10px; margin-bottom:10px; padding-left:0px;">
				<label>TIF → WORD:</label>
				<input id="tifFile" name="tifFile" type="file" contenteditable="false">
				<input id="docFileName" name="docFileName" type="hidden">
				<input type="button" class="btn" value="TIF转成WORD" onclick="tifToWord()">
			</div>
		</form>
		<div style="height:33px; margin-top:10px; margin-bottom:10px; padding-left:0px;">
			<label>WORD → CEB:</label>
			<input id="docFile" name="docFile" type="file" contenteditable="false">
			<input type="button" class="btn" value="WORD转成CEB" onclick="wordToCeb()">
		</div>
	</body>
	<script type="text/javascript">
		//检查所选文件是否符合要求，如果符合按fileType返回新的fileName
		function checkSrcFile(srcFile,srcType,destType){
			srcType=srcType.toUpperCase();
			var names = srcFile.split(".");
			if (srcFile.length == 0) {
		    	alert("请选择文件！");
		    	return "";
			}
	    	if (names.length <2 || names[names.length -1].toUpperCase() != srcType){
		        alert("请选择."+srcType+"格式的文件进行转换！");
		        return "";
		    }
		    return names[0]+"."+destType;
		}
	
		function wordToCeb(){
			var objMakercom = new ActiveXObject("MakerCom.MakerExt");
			var nRet1,nRet2,nRet3,nRet4;
			var docFile = document.getElementById("docFile").value;
			var cebFile=checkSrcFile(docFile,"doc","ceb");  
			if(cebFile!=""){
				nRet1 = objMakercom.BeginMaker("");
				if (nRet1 !=0 ){
	       			var gem1 = objMakercom.GetErrorMessage(nRet1);
					alert (gem1);
		     	}
		        nRet2 = objMakercom.SingleFileConvert(docFile,cebFile,"标准模板","","");
			    if (nRet2 !=0)				{
	       			var gem2 = objMakercom.GetErrorMessage(nRet2);
					alert (gem2);
		     	}
				nRet3 = objMakercom.EndMaker();
		  		if (nRet3 !=0){
	       			var gem3 = objMakercom.GetErrorMessage(nRet3);
					alert ("转换失败,请检查是否插入Maker！");
		     	}else{
   					alert("转换成功！");
 				}
			}
		}

		function tifToWord(){
			var tifFile = document.getElementById("tifFile").value;
			//alert(tifFile);
			var docFileFullName=checkSrcFile(tifFile,"tif","doc");
			if(docFileFullName!=""){
				var docName = docFileFullName.split("\\")[docFileFullName.split("\\").length -1];
				document.getElementById("docFileName").value=docName;
				document.getElementById("tifToWordForm").action="${ctx}/fileConversion_tifToWord.do";
				document.getElementById("tifToWordForm").submit();
			}
		}

	</script>
</html>
