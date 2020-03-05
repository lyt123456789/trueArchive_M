var iDeviceIndex = 0;//设备的编号；   0：文档摄像头;1：人像摄像头
var iColorMode = 0;//设定获取的图像的色彩模式；   0: 彩色，1: 灰度，2: 黑白。
var nDpi = 200;//设定拍照后图像存档的DPI;
var szPostfix = ".jpg";
var Capture;//必须得获取object对象
var Content;
var imgeId=0;
var ResSelect;//必须取得分辨率标签对象
var strMergeSource1;//合并图像源文件1
var strMergeSource2;//合并图像源文件2
var strFilePath = "D:\\DocImage\\";
var strFileNames=new Array();
var szTifFileName;
var szAddInFileName;
var continueNo = 1;
var szContinuePath;
var bContinueToTiff=0;
var szFileForBase64 = "";
var iDeviceStatus = -1;
//var bMultiSource = false;
var iCutPageType = -1;
var iWaterMarkType = -1;
var iVideoType = 0;

//打开设备
function OpenDevice(value){
	var iType = parseInt(value);
	//alert(iType);
	iDeviceIndex = iType;
    if(Capture.OpenDevice(iDeviceIndex) == 0)
	{
		WriteInfomation("启动设备"+iType.toString()+"成功");
		Capture.SetCameraExposure(iDeviceIndex,10);//设置自动曝光
		FillInRes();
		ResSelect.style.display="";
	}
	else
	{
		WriteInfomation("启动设备"+iType.toString()+"失败");
	}
}
//读取分辨率列表
function FillInRes()
{	
	var iResVecLenth=ResSelect.options.length;
	var iResWidth=0;
	var iResHeight=0;
	for(var i=0;i<iResVecLenth;i++)
	{
		ResSelect.options.remove(0);	//清除分辨率select选项内容
	}
	iResVecLenth = Capture.GetResolutionCount(iDeviceIndex);
	for(var i=0;i<iResVecLenth;i++)
	{
		iResWidth = Capture.GetResolutionWidth(iDeviceIndex,i);
		iResHeight = Capture.GetResolutionHeight(iDeviceIndex,i);
		var strRes = iResWidth.toString()+'*'+iResHeight.toString();
		var resopp = new Option(strRes,i.toString());
		ResSelect.add(resopp);
	}
}
//关闭设备
function CloseDeviceEx() {
    Capture.CloseDeviceEx();
}
//打开设备属性页对话框
function ShowDevicePage()
{
   	Capture.ShowDevicePages(iDeviceIndex);
}
//获取控件版本信息
function GetOcxVersion(){
	WriteInfomation("控件产品版本:"+Capture.GetOcxVersion());
}
//获取设备状态
function GetDeviceState(){
	switch(Capture.GetDeviceState(iDeviceIndex))
	{
	case -1:
		{
			WriteInfomation("设备状态：设备未启动");
			break;
		}
	case 0:
		{
			WriteInfomation("设备状态：设备正在运行中");
			break;
		}
	case 1:
		{
			WriteInfomation("设备状态：设备未连接");
			break;
		}
	case 2:
		{
			WriteInfomation("设备状态：设备被其他程序占用");
			break;
		}
	default:
		{
			break;
		}
	}
}
//设置是否打开条码识别
function SetBarcode(){
//    bBarcode = !bBarcode;
    
    if (checkBarcode.checked) {
        if(Capture.SetDeviceBarcode(iDeviceIndex,0)==0)
		{
			WriteInfomation("设置条码识别成功");
		}
		else
		{
			WriteInfomation("设置条码识别失败");
		}
    }else{
        if(Capture.SetDeviceBarcode(iDeviceIndex,1)==0)
		{
			WriteInfomation("取消条码识别成功");
		}
		else
		{
			WriteInfomation("取消条码识别失败");
		}
    }
}

//切边类型
function SetCutPageType(value)
{
	var iType = parseInt(value);
	if(Capture.SetCutPageType(iDeviceIndex,iType) == 0)
	{
		WriteInfomation("设置切边方式为"+iType);
	}
}

//设置颜色模式
function SetColorMode(value) {
	var iType = parseInt(value);
    switch(iType) 
	{
		case 0:
		{
			iColorMode = 0;
			Capture.SetColorMode(0);
			break;
		}
		case 1:
		{
			iColorMode = 1;
			Capture.SetColorMode(1);
			break;
		}
		case 2:
		{
			iColorMode = 2;
			Capture.SetColorMode(2);
			break;
		}
		default:
		{
			break;
		}
	}
}

//设置文件保存格式
function SetFileType(value){
	var iFileTye = parseInt(value)
	switch(iFileTye) 
	{
		case 0:
			szPostfix = ".jpg";
			break;
		case 1:
			szPostfix = ".bmp";
			break;
		case 2:
			szPostfix = ".png";
			break;
		case 3:
			szPostfix = ".tif";
			break;
		case 4:
			szPostfix = ".pdf";
			break;
		default:
			szPostfix = ".jpg";
			break;
    }
}

//设置分辨率
function SetResIndex(value){
	var iIndex = parseInt(value);
	if(Capture.SetResolutionIndex(iDeviceIndex, iIndex) == 0)
	{
		WriteInfomation("设置分辨率成功,将重新打开设备");
	}
	else
	{
		WriteInfomation("设置分辨率失败");
	}
}
//设置拍照存档的DPI
function SetDPI(iDPIValue){
	if( iDPIValue == "")
	{
		alert("DPI值都不能为空！");
		return;
	}
	if(isDigit(iDPIValue))
	{
		iDpi = parseInt( iDPIValue );
		if( Capture.SetGrabbedDPIEx( iDpi ) == 0 )
			WriteInfomation( "DPI设置成功" );
		else
		{
			WriteInfomation( "DPI设置失败" );
		}
	}
	else
	{
		alert("含有非法字符，请重新输入数字！");
	}
}

//拍照并且存档,若勾选条码则一起获取条码信息
function CaptureToFile() {
	AddWaterMark();//设置水印
	var strFileName;
	//SetFileType();
	
	// if(CusName.value == "")
	// {
	//strFileName = strFilePath + imgeId.toString() + szPostfix;
	strFileName = strFilePath + Date.parse(new Date()) + szPostfix;
	// }
	// else 
	// {
		// strFileName= strFilePath + CusName.value + szPostfix;
	// }
	Capture.GrabToFile(strFileName);
	var strBase64 = Capture.GetBase64String()
	//FtpUploadFile()
	//alert("文件保存为当前目录下的"+strFileName);
	/*if(checkMultiSource.checked)z
	{
		Capture.ImagesToMultiPageFile(strFileName,0);
	}*/
	/*if(szPostfix != ".pdf" && szPostfix != ".tif")
	{
		strFileNames.push(strFileName);
		Preview();
		//此测试页面只保存最后2次拍摄的文件名作为合并图像参数文件名	
		strMergeSource1 = strMergeSource2;
		strMergeSource2 = strFileName;
	}	*/	
	/*var strBarcodeContent = "";
	if (checkBarcode.checked) {    
		var iBarcodeCount = Capture.GetBarcodeCount();
		WriteInfomation("识别到"+iBarcodeCount.toString()+"个条码");
		for(var k = 0; k < iBarcodeCount; k++)
		{
			WriteInfomation("条码"+k.toString()+":["+Capture.GetBarcodeTypeName(k)+"]"+Capture.GetBarcodeContent(k));
			// strBarcodeContent += "[";
			// strBarcodeContent += Capture.GetBarcodeTypeName(k);
			// strBarcodeContent += "]";
			// strBarcodeContent += Capture.GetBarcodeContent(k);
		}
		// getBarcodeInfo(iBarcodeCount,strBarcodeContent);
	}*/
	imgeId++;
	addFj(Date.parse(new Date()) + szPostfix,strBase64);
}
//获取base64字符串
function GetBase64string(){
	if(szFileForBase64 == "")
	{
		AddWaterMark();//设置水印
		var strBase64 = Capture.GetBase64String();//获取图像(提供Base64String接口);
		WriteInfomation(strBase64);
		//return strBase64;
	}
	else
	{
		var szBase64FromFile = Capture.GetBase64FromFile(szFileForBase64);
		WriteInfomation(szBase64FromFile);
	}
}
//获取图片的Base64字符串合并到GetBase64String()中
//function GetBase64FromFile(){
//	var szBase64FromFile = Capture.GetBase64FromFile(szFileForBase64);
//	WriteInfomation(szBase64FromFile);
//}
function OpenDesFileForBase64(){
	var file = document.getElementById("OpenDesFileForBase64");
    var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
	szFileForBase64=file.value;
	WriteInfomation("以下图片文件设为待编码状态："+szFileForBase64);
}
//摄像旋转
function SetDeviceRotation(rotation){
    var nRotation = parseInt(rotation);
	if(nRotation%90 != 0){
		Capture.SetDeviceRotate(iDeviceIndex,0);//不能被90整除的默认不旋转
	}
    //alert(rotation);
    Capture.SetDeviceRotate(iDeviceIndex,nRotation);
}
//设置条码信息
// function getBarcodeInfo(nCount,szContent) {
    // Content.value = "条码数量：" + String(nCount) + "\n\n" + String(szContent);
// }
//设置曝光度
function SetExposure(iExposureVal){
	//alert(iExposureVal);
	if(isDigit(iExposureVal))
		Capture.SetCameraExposure(iDeviceIndex,parseInt(iExposureVal));//当-6<=IExposureVal<=-1为此曝光值，否则为自动曝光
	else alert('请输入数字');
	WriteInfomation(Capture.GetCameraExposureEx(iDeviceIndex));
}
//设置jpg图片质量
function SetJPGQuality(iQuality){
	if(isDigit(iQuality))
		Capture.SetJPGQuality(parseInt(iQuality));
	else alert('请输入数字');
}
//合并图像
function SetMergePageType(iValue){
	var iMergePageType = parseInt(iValue);
	var dstFileName = "D:\\DocImage\\Merge" + iMergePageType.toString() + ".jpg";//合并的目标文件名
	if(strMergeSource1!=null && strMergeSource2!=null)
	{
		if(Capture.MergeImages(strMergeSource1,strMergeSource2,dstFileName,iMergePageType)==0)
		{
			WriteInfomation("将"+strMergeSource1+"和"+strMergeSource2+"合并成功，合并方式为"+iMergePageType.toString());
		}
		else
		{
			WriteInfomation("将"+strMergeSource1+"和"+strMergeSource2+"合并失败，合并方式为"+iMergePageType.toString());
		}
	}
	else 
	{
		alert('合并图像需要2张图片');
	}
}
//正则判断文本框中的内容是否为数字
function isDigit(iVal){
	var patrn=/^-?\d+(\.\d+)?$/;
	if (!patrn.exec(iVal)){
		return false
	}
	return true
} 

//触发自动对焦,只对自动对焦的高拍仪有效
function TriggerAutoFocuse()
{
	Capture.TriggerAutoFocuse();
}

function BtnCreateMultiPageFile() {
//	if(Capture.ImagesToMultiPageFile("d:\\DocImage\\CreatePdf.pdf", 2) == 0)
//	{
//		alert("d:\\DocImage\\CreatePdf.pdf创建成功");
//	}
	if(Capture.ImagesToMultiPageFile("d:\\DocImage\\CreateTIF.tif", 2) == 0)
	{
		alert("d:\\DocImage\\CreateTIF.tif创建成功");
	}
}

function ShowFolderFileList(folderspec) {
	var fso, f, fc;
	fso = new ActiveXObject("Scripting.FileSystemObject");
	f = fso.GetFolder(folderspec);
	fc = new Enumerator(f.files);
	var s = new Array();
	for ( ; !fc.atEnd(); fc.moveNext()) 
	{
		var File=fc.item();
		var tmpStr=File.Path.substring(File.Path.lastIndexOf(".")+1);
		if(tmpStr=="jpg"||tmpStr=="bmp"||tmpStr=="png"||tmpStr=="tif")
		{
			s.push(File.Path);
		}
	}
	return s;
}
function SetWaterMark(value)
{
	iWaterMarkType = parseInt(value);
}
//添加水印
function AddWaterMark()
{
	switch(iWaterMarkType)
	{
		case -1://不添加水印
			Capture.WaterMark("",-1,0,0,0,0);//参数设置为-1则不添加水印
			break;
		case 0://文字水印
			Capture.WaterMark(WaterContent.value,0,parseInt(WaterTran.value),parseInt(sWaterMarkPos.value),
			parseInt(WaterFontSize.value),parseInt(WaterColor.value));//设置空字符串，并且水印类型设置为0，则会添加时间水印：格式为年月日
			break;
		case 1://图片水印
			Capture.WaterMark(WaterContent.value,1,parseInt(WaterTran.value),parseInt(sWaterMarkPos.value),
			parseInt(WaterFontSize.value),parseInt(WaterColor.value));
			break;
		default:
			break;		
	}
}

// 录制/停止录制视频
function SetVideoType(value)
{
	iVideoType = parseInt(value);
}
function StartRecordingVideo()
{
	if(iVideoType == 0)
	{
		Capture.StartRecordingVideo("D:\\Test.avi");
	}
	else if(iVideoType == 1)
	{
		Capture.StartRecordingVideo("D:\\Test.asf");
	}
}
function StopRecordingVideo()
{
	Capture.StopRecordingVideo();
}
// 更改存储目录
// function ChangePath_onClick(){
	// strFilePath = FilePath.value;
// }

function OpenDesFile()
{
	var file = document.getElementById("OpenDesFile");
    var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
	if(ext!='tif'&&ext!='jpg'&&ext!='bmp'&&ext!='png')
	{
        alert("请选择图片文件用于追加！"); 
		return;
    }
	else 
	{
		szTifFileName=file.value;
		WriteInfomation("以下图片文件设为待追加状态："+szTifFileName);
	}
}
function OpenImageFile()
{
	var file = document.getElementById("OpenImageFile");
    var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
	if(ext!='jpg'&&ext!='bmp'&&ext!='png')
	{
        alert("请选择用于追加的图片(*.jpg;*.bmp;*.png)！"); 
		return;
    }
	else 
	{
		szAddInFileName=file.value;
		WriteInfomation("以下图片被设为追加源："+szAddInFileName);		
	}
}


//形成预览图
function Preview(){
	if(strFileNames.length>=3)
	{
		for(var i=0;i<3;i++)
		{
			ShowPreview(strFileNames[strFileNames.length-3+i],i+1);
		}
	}
	else
	{
		for(var i=0;i<strFileNames.length;i++)
		{
			ShowPreview(strFileNames[i],i+1);
		}
	}
}
function ShowPreview(strFileName,PreviewWinsowsNo) {
    var pic = document.getElementById("preview"+PreviewWinsowsNo);
    //var file = document.getElementById("f");
    var ext = strFileName.substring(strFileName.lastIndexOf(".")+1).toLowerCase();
     // gif在IE浏览器暂时无法显示
    if(ext!='png'&&ext!='jpg'&&ext!='bmp'&&ext!='tif'){
        alert("文件必须为图片！"); 
		return;
     }
     // IE浏览器
    if (document.all) 
	{
		pic.src=strFileName;
		//pic.select();
		var reallocalpath = strFileName;
		var ie6 = /msie 6/i.test(navigator.userAgent);
		//IE6浏览器设置img的src为本地路径可以直接显示图片
		if (ie6) 
		{
			pic.src = reallocalpath; 
		}
		else 
		{ 
			// 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
			pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + reallocalpath + "\")";
			// 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
			pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
       }
	}
	// else
	// {
		// html5Reader(file);
	// }
}
 
//获取设备硬件码
function BtnHID(){
	var strHID = Capture.GetDeviceVidPid(0);
	WriteInfomation("设备硬件码为"+strHID);
}

//把相关信息写入操作记录编辑框中
function WriteInfomation(strInfo){
	//Content.value += ("\r\n"+ strInfo);
}