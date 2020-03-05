var s = ""
s += "<object id=DWebSignSeal classid='CLSID:77709A87-71F9-41AE-904F-886976F99E3E' style='position:absolute;width:0px;height:0px;left:0px;top:0px;' codebase=component/WebSign.cab#version=4,0,5,2 >"
s += "</OBJECT>"
document.write(s)



function WebSign_AddSeal(sealName, sealPostion,signData){
		try{	 		
				var strObjectName ;
				strObjectName = DWebSignSeal.FindSeal("",0);  
				while(strObjectName  != ""){ 
						if(sealName == strObjectName){
						alert("\u5f53\u524d\u9875\u9762\u5df2\u7ecf\u52a0\u76d6\u8fc7\u5370\u7ae0\uff1a\u3010"+sealName+"\u3011\u8bf7\u6838\u5b9e");
						return false;
					}
					strObjectName = DWebSignSeal.FindSeal(strObjectName,0);
				}
				 
				Enc_onclick(signData);
			  document.all.DWebSignSeal.SetCurrUser("\u76d6\u7ae0\u4eba");
				document.all.DWebSignSeal.RemoteID = "0100018";
				document.all.DWebSignSeal.SetPosition(100,10,sealPostion);
				
				if("" == document.all.DWebSignSeal.AddSeal("", sealName)){
					 alert("\u76d6\u7ae0\u5931\u8d25");
					 return false;
				}else{
				}
		}catch(e) {
		  alert("\u63a7\u4ef6\u6ca1\u6709\u5b89\u88c5\uff0c\u8bf7\u5237\u65b0\u672c\u9875\u9762\uff0c\u63a7\u4ef6\u4f1a\u81ea\u52a8\u4e0b\u8f7d\u3002\r\n\u6216\u8005\u4e0b\u8f7d\u5b89\u88c5\u7a0b\u5e8f\u5b89\u88c5\u3002" +e);
		}
	}	
function WebSign_HandWrite(userName){
	try{ 
		document.all.DWebSignSeal.SetCurrUser(userName);
		
		var returnDivId=document.all.DWebSignSeal.HandWrite(0,0x000000,'');
		
		return returnDivId;
	}catch(e) {
		alert("\u63a7\u4ef6\u6ca1\u6709\u5b89\u88c5\uff0c\u8bf7\u5237\u65b0\u672c\u9875\u9762\uff0c\u63a7\u4ef6\u4f1a\u81ea\u52a8\u4e0b\u8f7d\u3002\r\n\u6216\u8005\u4e0b\u8f7d\u5b89\u88c5\u7a0b\u5e8f\u5b89\u88c5\u3002" +e);
	}
}

function HandWritePop_onclick(userName){
	try{ 
			document.all.DWebSignSeal.SetCurrUser(userName);
			document.all.DWebSignSeal.SetPosition(0,0,"handWritePostion1");
			var returnDivId=document.all.DWebSignSeal.HandWritePop(3,0x000000,100, 640,640,'');//0x000000为黑色，255为红色
			
			return returnDivId;  
		}catch(e) {
		  alert("\u63a7\u4ef6\u6ca1\u6709\u5b89\u88c5\uff0c\u8bf7\u5237\u65b0\u672c\u9875\u9762\uff0c\u63a7\u4ef6\u4f1a\u81ea\u52a8\u4e0b\u8f7d\u3002\r\n\u6216\u8005\u4e0b\u8f7d\u5b89\u88c5\u7a0b\u5e8f\u5b89\u88c5\u3002" +e);
		}
}	
		
 
function submit_onclick(){
try{
	var v = document.all.DWebSignSeal.GetStoreData();
	if(v.length < 200){
		alert("\u5fc5\u987b\u5148\u76d6\u7ae0\u624d\u53ef\u4ee5\u63d0\u4ea4");
		return false;
	}
	document.all.form1.sealdata.value = v;
	}catch(e) {
		alert("\u63a7\u4ef6\u6ca1\u6709\u5b89\u88c5\uff0c\u8bf7\u5237\u65b0\u672c\u9875\u9762\uff0c\u63a7\u4ef6\u4f1a\u81ea\u52a8\u4e0b\u8f7d\u3002\r\n\u6216\u8005\u4e0b\u8f7d\u5b89\u88c5\u7a0b\u5e8f\u5b89\u88c5\u3002" +e);
		return false;
	}
}
 
 

function Enc_onclick(tex_name) {		
	try{
		document.all.DWebSignSeal.SetSignData("-");		
		 document.all.DWebSignSeal.SetSignData("+LIST:laiwendanwei;");
		 document.all.DWebSignSeal.SetSignData("+LIST:laiwenDate;");
		 document.all.DWebSignSeal.SetSignData("+LIST:shiyou;");
		 document.all.DWebSignSeal.SetSignData("+LIST:time;");
		 document.all.DWebSignSeal.SetSignData("+LIST:"+tex_name+";");
		 
	}catch(e) {
		alert("\u63a7\u4ef6\u6ca1\u6709\u5b89\u88c5\uff0c\u8bf7\u5237\u65b0\u672c\u9875\u9762\uff0c\u63a7\u4ef6\u4f1a\u81ea\u52a8\u4e0b\u8f7d\u3002\r\n\u6216\u8005\u4e0b\u8f7d\u5b89\u88c5\u7a0b\u5e8f\u5b89\u88c5\u3002" +e);
	}
	}
	
function SetUI() {
	try{
		 document.all.DWebSignSeal.TipBKLeftColor = 29087;
		 document.all.DWebSignSeal.TipBKRightColor = 65443;
		 document.all.DWebSignSeal.TipLineColor = 65535;
		 document.all.DWebSignSeal.TipTitleColor = 32323;
		 document.all.DWebSignSeal.TipTextColor = 323;
	}catch(e) {
		alert("\u63a7\u4ef6\u6ca1\u6709\u5b89\u88c5\uff0c\u8bf7\u5237\u65b0\u672c\u9875\u9762\uff0c\u63a7\u4ef6\u4f1a\u81ea\u52a8\u4e0b\u8f7d\u3002\r\n\u6216\u8005\u4e0b\u8f7d\u5b89\u88c5\u7a0b\u5e8f\u5b89\u88c5\u3002" +e);
	}
 }
 
