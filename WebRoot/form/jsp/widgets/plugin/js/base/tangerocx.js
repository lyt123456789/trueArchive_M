//通用的js语句和函数
var TANGER_OCX_bDocOpen = false;
var TANGER_OCX_Username
var TANGER_OCX_key=""; //加密签章
var TANGER_OCX_OBJ

//此函数在网页装载时被调用。用来获取控件对象并保存到TANGER_OCX_OBJ
//同时，可以设置初始的菜单状况，打开初始文档等等。
function TANGER_OCX_Init(initdocurl)
{
	TANGER_OCX_bDocOpen = true;
	TANGER_OCX_OBJ=document.all("TANGER_OCX");
	var a=TANGER_OCX_OBJ.OpenFromUrl(initdocurl);
	return a;
}

function TANGER_OCX_Init_no(){
	TANGER_OCX_bDocOpen = true;	
	TANGER_OCX_OBJ=document.all("TANGER_OCX");
}
//开始手写签名
function DoHandSign()
{
	TANGER_OCX_OBJ=document.all("TANGER_OCX");
    if(TANGER_OCX_bDocOpen)
    {	
        TANGER_OCX_OBJ.SetReadOnly(false);
    	TANGER_OCX_OBJ.DoHandSign2(TANGER_OCX_Username,TANGER_OCX_key); 
    }  
}

function CopyTextToBookMark(inputname,BookMarkName)
{
	var bks = TANGER_OCX_OBJ.ActiveDocument.BookMarks;

	TANGER_OCX_OBJ.SetBookMarkValue(BookMarkName,inputname);
}

function setFileOpenedOrClosed(bool)
{
	IsFileOpened = bool;
	fileType = TANGER_OCX_OBJ.DocType ;
}

function saveFileToUrl()
{	
	var myUrl =document.forms[0].action ;
	document.getElementById('isbc').value='1';
	var fileName = document.getElementById("name").value;
	var result  ;
				fileType = "Word.Document";
		 TANGER_OCX_OBJ.saveToURL(myUrl,//提交到的url地址
		"vc_file",//文件域的id，类似<input type=file name=upLoadFile 中的name
		"fileType="+fileType+"&fileName="+fileName,          //与控件一起提交的参数如："p1=a&p2=b&p3=c"
		fileName,    //上传文件的名称，类似<input type=file 的value
		0    //与控件一起提交的表单id，也可以是form的序列号，这里应该是0.
		);
	
}


//设置用户名
function TANGER_OCX_SetDocUser(cuser)
{
    TANGER_OCX_Username = cuser;
	with(TANGER_OCX_OBJ.ActiveDocument.Application)
	{
		UserName = cuser;		
	}	
}


function SetReviewMode(boolvalue)
{
	if(TANGER_OCX_OBJ.doctype==1)
	{
		TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = boolvalue;//设置是否保留痕迹
	}
} 

//切换显示修订工具栏和工具菜单（保护修订）
function TANGER_OCX_EnableReviewBar(boolvalue)
{
	if(!TANGER_OCX_bDocOpen)
	{
		return;
	}
	TANGER_OCX_OBJ.ActiveDocument.CommandBars("Reviewing").Enabled = boolvalue;
	TANGER_OCX_OBJ.ActiveDocument.CommandBars("Track Changes").Enabled = boolvalue;
	TANGER_OCX_OBJ.IsShowToolMenu = boolvalue;	//关闭工具菜单
}

function setShowRevisions(boolevalue)
{
	if(TANGER_OCX_OBJ.doctype==1)
	{
		TANGER_OCX_OBJ.ActiveDocument.ShowRevisions =boolevalue;//设置是否显示痕迹
	}
}

// 清稿编辑
function TANGER_OCX_AcceptAllRevisions()
{
	TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
}
