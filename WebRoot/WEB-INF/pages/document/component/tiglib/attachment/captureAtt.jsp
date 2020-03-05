<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>附件扫描</title>
<link rel="stylesheet" type="text/css" href="${ctx}/gpy/easyui1.2.5/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/gpy/easyui1.2.5/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/gpy/css/my.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/gpy/css/btn2.css" />
<link href="${ctx}/gpy/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/gpy/easyui1.2.5/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${ctx}/gpy/easyui1.2.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src='${ctx}/gpy/easyui1.2.5/locale/easyui-lang-zh_CN.js'></script>
<script type="text/javascript" src='${ctx}/gpy/common.js'></script>
<Script language="javascript">

function getDateStr(MyDate){
	var fullyear = MyDate.getFullYear();
	var month = MyDate.getMonth()+1;
	var day = MyDate.getDate();
	var hours = MyDate.getHours();
	var mintues = MyDate.getMinutes(); 
	var seconds = MyDate.getSeconds();
	var num = ""+fullyear+month+day+hours+mintues+seconds;
	return num;
}

var nFileCount = 0;
var strFile = '';
var index = 0;
function Capture(){
	var MyDate = new Date();
	var str = getDateStr(MyDate);
	fileName = "Img" + str+".jpg";
	strFile = "D:\\test\\" + fileName;
	CaptureOcx1.CaptureImage(strFile);
	ShowImage(strFile);
	nFileCount ++;
	
}

function createInput(o){
	var $in = $("#base64File");
	$in.val(o);
}

function Load(){
	CaptureOcx1.Initial();
	AddDevice();
}

function run(){
	CaptureOcx1.Initial();
	CaptureOcx1.StartRun(index);
}
function sub(){
	if(strFile==''){
		alert("请先拍摄图片，再提交！");
		return;
	}
	if($("#title").val()==''){
		alert("请先选择文件标题！");
		return ;
	} 
	CaptureOcx1.Destory();

	var docguid = document.getElementById('docguid').value;
	var nodeId = document.getElementById('nodeId').value;
	var title = $("#title").val();
//	var type = document.getElementById('type').value;
	var url = "${curl}/attachment_uploadScanNew.do?docguid=" + docguid + "&nodeId=" + nodeId + "&title=" + encodeURI(title) ;
	CaptureOcx1.UpdataFile(url,strFile,0);
	window.close();
	window.returnValue='0';
}
function FileList(){
	filelist1.value = CaptureOcx1.GetSaveImages();
}
function ChangeVideoSize(){
            var   obj=document.getElementById("resolution1").options; 
            var   x = obj.selectedIndex;   
			CaptureOcx1.SetResolutionAt(x);
	SetCaptureOcxSize(x);
}

function SetCaptureOcxSize(index)
{
	CaptureOcx1.width = CaptureOcx1.GetResolutionwidthAt(index);
	CaptureOcx1.height = CaptureOcx1.GetResolutionHeightAt(index);
}

function ChangeRotate(){
            var   obj=document.getElementById("rotate1").options; 
            var   x = obj.selectedIndex;   
            CaptureOcx1.SetRotate(x);
}
 

function SetIDCard(){
//	CaptureOcx1.SetResolution(2592,1944);	//这行可要可不要
//	CaptureOcx1.SetOutputSize(800,530);	//裁剪尺寸
	CaptureOcx1.LoadConfig("E:\\IDCard.xml");	//从配置中装入
}

function CheckDevice(){

        if(CaptureOcx1 != null)
               window.alert("检测到设备"); 
        else
               window.alert("检测不到设备"); 
}



function ChangeDevice()
{
	var obj=document.getElementById("DeviceName") ;
	index=obj.selectedIndex;
	CaptureOcx1.StartRun(index);
	CaptureOcx1.SetResolution(7);
	CaptureOcx1.SetJpgQuanlity(50);	
	CaptureOcx1.RotateVideo(1);
}
 

 

    
    function ShowImage(a) {
	$('#imgFiles').html('');
    var patn = /\.jpg$|\.jpeg$|\.gif$/i;
    if (patn.test(a)) {
        var imgHtml = "<div><h1 id=\"img1\" style=\"border: 1px solid black; filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="+a+");width: 200px; height: 200px\"></h1></div>";
        document.getElementById('imgFiles').innerHTML=imgHtml;
    }
    else {
        alert("您选择的似乎不是图像文件。");
    }

}


</Script>
<style type="text/css">
	*{margin:0;padding:0;}
	select{outline:none;}
	ul{list-style:none;}
	a{text-decoration:none;}
</style>
</head>
<body onload="Load();"  style="overflow-x:hidden;overflow-y:auto;">
<div class="container">
	<div class="imageleft clearfix">
		<div class="imagetop">
			<div class="cd_main">
				
					<!--
					<object classid="clsid:49CBC347-34CD-4687-9D5C-C45E3D3314F0" id="CaptureOcx1" width="900" height="580">
					</object>
					
					-->
					<object class="cd_main_a" classid="clsid:3CA842C5-9B56-4329-A7CA-35CA77C7128D" id="CaptureOcx1" >
					</object>
				
				<div class="cd_main_a bk">
                	<div class="rectangletop clearfix">
                     	<div class="rectanglewh rectangleleft clearfix"></div>
                        <div class=" rectanglewh rectangleright fr clearfix"></div>
                     </div>
                     <div class="rectangletop clearfix margintop398">
                     	<div class="rectanglewh rectangleleft1 clearfix"></div>
                        <div class=" rectanglewh rectangleright1 fr clearfix"></div>
                     </div>
            	</div>
			</div>
		</div>
		<div class="imagetop h40">
			<div class="btn clearfix">
				<div class="submitbtn"><a type="button" onclick="sub();"><span>提交</span></a></div> 
				<div class="submitbtn cancellbtn"><a type="button" onclick="CaptureOcx1.Destory();window.close();"><span>取消</span></a></div>
			</div>
		</div>
	</div>
	<div class="imagesinforight clearfix">
		<form name="Reso" id="Reso">
			<div class="sblisttop clearfix">
				<div class="sblist" style="margin-top: 0px;">
					<div class="imgleft">
                    	<img src="${ctx}/gpy/images/icon_xzsb.png" />
                    </div>
                    <div class="btnlist">
                    	<span>请选择设备名称，开始扫描文件</span>
                    </div>
                    <div class="btnlist">
                    	<select name="DeviceName" id="DeviceName" onchange="ChangeDevice()" >
							<option value="0" selected="selected"></option>
							<option value="1"></option>
							<option value="2"></option>
							<option value="3"></option>
							<option value="4"></option>
						</select>
                    </div>
					<Script>
					function AddDevice()
						{
							var i = 0;
							var total = CaptureOcx1.GetDevCount();
							var sel = document.getElementById('DeviceName');
							for( i = 0 ; i < total ; i++ )
							{
								var DevEle = CaptureOcx1.GetDevFriendName(i);
								sel.options[i].innerHTML=DevEle;
							}
						}
					</script>
                    <!--<script type="text/javascript" src="${ctx}/gpy/js/jquery-1.8.3.min.js"></script>
					<script type="text/javascript" src="${ctx}/gpy/js/jquery.select.js"></script>-->
					
				</div>
				<div class="sblist">
					<div class="imgleft">
                    	<img src="${ctx}/gpy/images/icon_jcsb.png" />
                    </div>
                    <div class="btnlist">
                    	<span>检查设备</span>
                    </div>
                    <div class="btnlist">
                    	<div class="jcsbbtn"><a type="button" name="B8" onClick="CheckDevice();">检查设备</a></div>
                    </div>
				</div>
				<div class="sblist">
					<div class="imgleft">
                    	<img src="${ctx}/gpy/images/icon_qdgyy.png" />
                    </div>
                    <div class="btnlist">
                    	<span>启动高影仪</span>
                    </div>
                    <div class="btnlist">
                    	<div class="jcsbbtn"><a type="button" name="B3" onClick="ChangeDevice();">启动</a></div>
                    </div>
				</div>
				<div class="sblist">
                	<div class="imgleft">
                    	<img src="${ctx}/gpy/images/icon_tzgyy.png" />
                    </div>
                    <div class="btnlist">
                    	<span>停止高影仪</span>
                    </div>
                    <div class="btnlist">
                    	<div class="jcsbbtn"><a type="button" name="B5" onClick="CaptureOcx1.Destory();">停止</a></div>
                    </div>
                </div>
                <div class="sblist">
                	<div class="imgleft">
                    	<img src="${ctx}/gpy/images/icon_ps.png" />
                    </div>
                    <div class="btnlist">
                    	<span>拍摄</span>
                    </div>
                    <div class="btnlist">
                    	<div class="jcsbbtn"onClick="Capture()"><a type="button" name="B1">拍摄</a></div>
                    </div>
                </div>
			</div>
		</form>
		<form  id="myForm"  action="${ctx }/attachment_uploadScan.do" method="post">
			<div id="imgFiles" class="imgsl">
			 	
			</div>
			 <div class="wjtitle h30">
            	<span>文件标题:</span>
                <span style="color:#ff0000; float:right; margin-right:8px; line-height:35px;">*</span>
                <input type="text" id="title" name="title" value="${title}" style="padding-left:5px;"/>
            </div>
            <input type="hidden"  name="docguid" id="docguid" value="${docguid}" >
			<input type="hidden"  name="nodeId" id="nodeId" value="${nodeId}" >
		</form>
	</div>

</div>
</body>
</html>