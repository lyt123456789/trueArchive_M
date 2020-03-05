function EncodeUtf8(s1){
      var s = escape(s1);
      var sa = s.split("%");
      var retV ="";
      if(sa[0] != ""){
         retV = sa[0];
      }
      for(var i = 1; i < sa.length; i ++){
           if(sa[i].substring(0,1) == "u"){
               retV += Hex2Utf8(Str2Hex(sa[i].substring(1,5)));
                
           }
           else retV += "%" + sa[i];
      }
      return retV;
  }
  function Str2Hex(s){
      var c = "";
      var n;
      var ss = "0123456789ABCDEF";
      var digS = "";
      for(var i = 0; i < s.length; i ++){
         c = s.charAt(i);
         n = ss.indexOf(c);
         digS += Dec2Dig(eval(n));
            
      }
      return digS;
  }
  function Dec2Dig(n1){
      var s = "";
      var n2 = 0;
      for(var i = 0; i < 4; i++){
         n2 = Math.pow(2,3 - i);
         if(n1 >= n2){
            s += '1';
            n1 = n1 - n2;
          }
         else
          s += '0';
      }
      return s;
  }
  function Dig2Dec(s){
      var retV = 0;
      if(s.length == 4){
          for(var i = 0; i < 4; i ++){
              retV += eval(s.charAt(i)) * Math.pow(2, 3 - i);
          }
          return retV;
      }
      return -1;
  } 
  function Hex2Utf8(s){
     var retS = "";
     var tempS = "";
     var ss = "";
     if(s.length == 16){
         tempS = "1110" + s.substring(0, 4);
         tempS += "10" +  s.substring(4, 10); 
         tempS += "10" + s.substring(10,16); 
         var sss = "0123456789ABCDEF";
         for(var i = 0; i < 3; i ++){
            retS += "%";
            ss = tempS.substring(i * 8, (eval(i)+1)*8);
            retS += sss.charAt(Dig2Dec(ss.substring(0,4)));
            retS += sss.charAt(Dig2Dec(ss.substring(4,8)));
         }
         return retS;
     }
     return "";
  } 

//通用的js语句和函数
var TANGER_OCX_bDocOpen = false;
var TANGER_OCX_Username;
var TANGER_OCX_key=""; //加密签章
var TANGER_OCX_OBJ;

//此函数在网页装载时被调用。用来获取控件对象并保存到TANGER_OCX_OBJ
//同时，可以设置初始的菜单状况，打开初始文档等等。
function TANGER_OCX_Init(initdocurl)
{
	TANGER_OCX_bDocOpen = true;
	TANGER_OCX_OBJ=document.all("TANGER_OCX");
	//换成BeginOpenFromURL
	var a=TANGER_OCX_OBJ.OpenFromUrl(initdocurl);
	//var a=TANGER_OCX_OBJ.BeginOpenFromURL(initdocurl); 
	return a;
}

function TANGER_OCX_Init_no(){
	TANGER_OCX_bDocOpen = true;	
	TANGER_OCX_OBJ=document.all("TANGER_OCX");
}
//开始手写签名
function DoHandSign(){
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

//设置用户名
function TANGER_OCX_SetDocUser(cuser)
{
    TANGER_OCX_Username = cuser;
	with(TANGER_OCX_OBJ.ActiveDocument.Application)
	{
		UserName = cuser;		
	}	
}


function SetReviewMode(boolvalue){
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
function TANGER_OCX_AcceptAllRevisions(){
	TANGER_OCX_OBJ.ActiveDocument.AcceptAllRevisions();
}

/**
 * 用于办文单保存
 */
function saveBwdDocFile(){
	//提交到的url地址
	var myUrl =document.forms[0].action ;
	//文件域的id，类似<input type=file name=upLoadFile 中的name
	var fileName = document.getElementById("name").value;
	var result="";
	if(TANGER_OCX_bDocOpen){
		switch (TANGER_OCX_OBJ.doctype)
		{
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
		//与控件一起提交的参数如："p1=a&p2=b&p3=c"
		var args = ""
		//与控件一起提交的表单id，也可以是form的序列号，这里应该是0.
		var formNum = 0;
		result = TANGER_OCX_OBJ.saveToURL(myUrl,"docFile",args,"",formNum);   
		}  
		return result;
}
 /**
  * 用于“编办”中 办文中进行发文流程开始的办文单保存
  */
 function saveBwdDocFile_send(myUrl,fileName,docguid){
	 var newDocguid="";
	 if(TANGER_OCX_bDocOpen){
		 switch (TANGER_OCX_OBJ.doctype)
		 {
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
		 //与控件一起提交的参数如："p1=a&p2=b&p3=c"
		 var args = "docguid="+docguid+"&fileName="+fileName;
			 //与控件一起提交的表单id，也可以是form的序列号，这里应该是0.
		 var formNum = 0;
		 newDocguid = TANGER_OCX_OBJ.saveToURL(myUrl,"docFile",args,"",formNum);   
	 }  
	 return newDocguid;  
 }
/**
 * 用于模板文件编辑后保存
 */
function saveTemp(tempId,filename,fileindex){
	filename=	EncodeUtf8(filename);
	var myUrl =document.forms[0].action ;  
	var result  ;
	if(TANGER_OCX_bDocOpen)
	{
		switch (TANGER_OCX_OBJ.doctype)
		{
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
		var a = TANGER_OCX_OBJ.saveToURL(myUrl,//提交到的url地址
		"upLoadFile",//文件域的id，类似<input type=file name=upLoadFile 中的name
		"fileType="+fileType+"&tempId="+tempId+"&filename="+filename+"&fileindex="+fileindex,          //与控件一起提交的参数如："p1=a&p2=b&p3=c"
		0,
		""    //与控件一起提交的表单id，也可以是form的序列号，这里应该是0.
		);
		return a;
	}
}

/**
 * 用于正文附件在线编辑后保存
 */
function saveAttHistory(saveUrl,attId){
	if(TANGER_OCX_bDocOpen){
		switch (TANGER_OCX_OBJ.doctype){
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
		var a = TANGER_OCX_OBJ.saveToURL(saveUrl,//提交到的url地址
		"file",//文件域的id，类似<input type=file name=upLoadFile 中的name
		"fileType="+fileType+"&attId="+attId,          //与控件一起提交的参数如："p1=a&p2=b&p3=c"
		0    //与控件一起提交的表单id，也可以是form的序列号，这里应该是0.
		);
		return a;
	}

}
