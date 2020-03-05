var s = ""
s += "<object id=DWebSignSeal classid='CLSID:77709A87-71F9-41AE-904F-886976F99E3E' style='position:absolute;width:0px;height:0px;left:0px;top:0px;' codebase=component/WebSign.cab#version=4,0,5,2 >"
s += "</OBJECT>"
document.write(s)



/*****************盖章******************************
说明：
    WebSign_AddSeal 添加印章接口
    1.设置盖章人 (可以不调用)
    2.设置盖章时间 (可以从服务器上获取,也可以不调用)
    3.设置章所盖的位置 (可以设置相对于表单域的偏移位置,有效防止印章在不同分辨率下错位)
    
WebSign 支持三种印章获取方式
   	1.本地印章文件
   	2.USBKey智能卡钥匙盘
   	3.远程服务器
   	本演示采用第一种方式.
   	
如果一个表单上需要盖多个印章,且每个章绑定不同的区域,可以参照开发文档
WebSign的AddSeal接口可以设置或者获取当前章的Name, 
根据章的Name,可以设置每个章绑定的区域,和验证数据.
当前例子.采用系统默认分配的Name,所有的印章都默认绑定所有表单数据.
***********************************************/	
function WebSign_AddSeal(sealName, sealPostion,signData){
		try{	 		
				//是否已经盖章
				var strObjectName ;
				strObjectName = DWebSignSeal.FindSeal("",0);  
				while(strObjectName  != ""){ 
						if(sealName == strObjectName){
						alert("当前页面已经加盖过印章：【"+sealName+"】请核实");
						return false;
					}
					strObjectName = DWebSignSeal.FindSeal(strObjectName,0);
				}
				 
				//设置当前印章绑定的表单域
				Enc_onclick(signData);
				//设置盖章人，可以是OA的用户名
			  document.all.DWebSignSeal.SetCurrUser("盖章人");
				//document.all.DWebSignSeal.SealSetID = "001";
				//公章否？ 1:公章 0：私章 
				//document.all.DWebSignSeal.CurrSealType = 0;
				//网络版的设置服务器路径地址 如果不设置，系统会自动到注册表中读取。
				//document.all.DWebSignSeal.HttpAddress = "127.0.0.1:1127";
				//document.all.DWebSignSeal.HttpAddress = "http://192.168.1.11:80/inc/seal_interface/";
				//网络版的唯一页面ID ，SessionID
				document.all.DWebSignSeal.RemoteID = "0100018";
				//附加信息
				//document.all.DWebSignSeal.AppendTipInfo = "IP:192.168.0.11\r\nMAC:00-00-00-11-02";
				//设置盖章时间，可以有服务器传过来
		 		//document.all.SetCurrTime("2006-02-07 11:11:11");
				//设置当前印章的位置,相对于sealPostion1 (<div id="sealPostion1"> </div> 也可以是 td) 的位置相左偏移50px,向上偏移50px
				//这样就可以很好的固定印章的位置
				//document.all.DWebSignSeal.SetPosition(50,-50,"sealPostion1");
				document.all.DWebSignSeal.SetPosition(100,10,sealPostion);
				
		    //调用盖章的接口
				if("" == document.all.DWebSignSeal.AddSeal("", sealName)){
					 alert("盖章失败");
					 return false;
				}else{
				//区域锁定
					//document.all.DWebSignSeal.LockSeal(sealName,1);
				//位置锁定，参数为要锁定的印章名，传空字符为锁定所有章的位置，
					//document.all.DWebSignSeal. LockSealPosition("");
				}
		}catch(e) {
		  alert("控件没有安装，请刷新本页面，控件会自动下载。\r\n或者下载安装程序安装。" +e);
		}
	}	
/*******************全屏幕手写****************************
说明：
    WebSign_HandWrite添加印章接口
    1.设置签名人 (可以不调用)
    2.设置签名时间 (可以从服务器上获取,也可以不调用) 
 
   	
如果一个表单上需要盖多个签名,且每个签名绑定不同的区域,可以参照开发文档
WebSign的HandWrite接口可以设置或者获取当前签名的Name, 
根据签名的Name,可以设置每个签名绑定的区域,和验证数据.
当前例子.采用系统默认分配的Name,所有的签名都默认绑定所有表单数据.
***********************************************/	
function WebSign_HandWrite(userName){
	try{ 
		//设置当前印章绑定的表单域
		//Enc_onclick(signData);
		//设置签名人，可以是OA的用户名
		document.all.DWebSignSeal.SetCurrUser(userName);
		//设置签名时间，可以有服务器传过来
 		//document.all.SetCurrTime("2006-02-07 11:11:11");
                 //调用签名的接口
		
		var returnDivId=document.all.DWebSignSeal.HandWrite(0,0x000000,'');//0x000000为黑色，255为红色
		
		return returnDivId;
	}catch(e) {
		alert("控件没有安装，请刷新本页面，控件会自动下载。\r\n或者下载安装程序安装。" +e);
	}
}

/*******************弹出窗口手写****************************
说明：
    HandWritePop_onclick 弹出窗口手写
    1.设置签名人 (可以不调用)
    2.设置签名时间 (可以从服务器上获取,也可以不调用)
    3.设置签名所盖的位置 (可以设置相对于表单域的偏移位置,有效防止签名在不同分辨率下错位)
       	
如果一个表单上需要盖多个签名,且每个签名绑定不同的区域,可以参照开发文档
WebSign的HandWrite接口可以设置或者获取当前签名的Name, 
根据签名的Name,可以设置每个签名绑定的区域,和验证数据.
当前例子.采用系统默认分配的Name,所有的签名都默认绑定所有表单数据.
***********************************************/	
function HandWritePop_onclick(userName){
	try{ 
			//设置当前印章绑定的表单域 
			//Enc_onclick(); 
			//设置签名人，可以是OA的用户名
			document.all.DWebSignSeal.SetCurrUser(userName);
			//设置签名时间，可以由服务器传过来
	 		//document.all.SetCurrTime("2006-02-07 11:11:11");
			//设置当前印章的位置,相对于sealPostion1 (<div id="handWritePostion1"> </div>) 的位置相左偏移0px,向上偏移0px
			//这样就可以很好的固定印章的位置
			document.all.DWebSignSeal.SetPosition(0,0,"handWritePostion1");
			//调用签名的接口,并返回值(返回值为存储签名的层的id)
			
			//弹出框
			var returnDivId=document.all.DWebSignSeal.HandWritePop(3,0x000000,100, 640,640,'');//0x000000为黑色，255为红色
			
			return returnDivId;  
		}catch(e) {
		  alert("控件没有安装，请刷新本页面，控件会自动下载。\r\n或者下载安装程序安装。" +e);
		}
}	
		
/*****************表单提交******************************
说明：
    submit 提交表单
    调用WebSign的GetStoreData()接口获取印章的所有数据(印章数据+证书数据+签名数据...)
    把这个值赋值于Hiddle变量,保存到数据库中.
***********************************************/		
 
function submit_onclick(){
try{
	var v = document.all.DWebSignSeal.GetStoreData();
	if(v.length < 200){
		alert("必须先盖章才可以提交");
		return false;
	}
	document.all.form1.sealdata.value = v;
	}catch(e) {
		alert("控件没有安装，请刷新本页面，控件会自动下载。\r\n或者下载安装程序安装。" +e);
		return false;
	}
}
 
 
/***********************************************
说明：
    Enc_onclick 主要设置绑定的表单域。
    WebSign的SetSignData接口支持两种绑定数据方式：
    1.字符串数据
    2.表单域
    一旦数据发生改变，WebSign会自动校验，并提示修改。
***********************************************/	

function Enc_onclick(tex_name) {		
	try{
		//清空原绑定内容	
		document.all.DWebSignSeal.SetSignData("-");		
		// str为待绑定的字符串数据
		//var str = "";
		 //设置绑定的表单域
		 //来文单位
		 document.all.DWebSignSeal.SetSignData("+LIST:laiwendanwei;");
		 //来文日期
		 document.all.DWebSignSeal.SetSignData("+LIST:laiwenDate;");
		 //事由
		 document.all.DWebSignSeal.SetSignData("+LIST:shiyou;");
		 //时间要求
		 document.all.DWebSignSeal.SetSignData("+LIST:time;");
		  //意见
		 document.all.DWebSignSeal.SetSignData("+LIST:"+tex_name+";");
		 
		/*根据表单域内容自己组织绑定内容,当前例子仅仅做与表单域绑定
		如果绑定字符串数据,需要做如下调用
			document.all.DWebSignSeal.SetSignData("+DATA:"+str);		
		*/
	}catch(e) {
		alert("控件没有安装，请刷新本页面，控件会自动下载。\r\n或者下载安装程序安装。" +e);
	}
	}
	
/***********************************************
说明：
    SetUI 设置用户界面风格
***********************************************/	
function SetUI() {
	try{
		 document.all.DWebSignSeal.TipBKLeftColor = 29087;
		 document.all.DWebSignSeal.TipBKRightColor = 65443;
		 document.all.DWebSignSeal.TipLineColor = 65535;
		 document.all.DWebSignSeal.TipTitleColor = 32323;
		 document.all.DWebSignSeal.TipTextColor = 323;
	}catch(e) {
		alert("控件没有安装，请刷新本页面，控件会自动下载。\r\n或者下载安装程序安装。" +e);
	}
 }
 
