/**
 * 意见标签初始化
 * @param {Object} commentObjId
 */
function initCommentFun(commentObjId){
	var commentInputs = document.getElementById(commentObjId).getElementsByTagName('input');
	var commentInstanceId = commentInputs['commentInstanceId'].value;
	var addAbled = commentInputs['addAbled'].value;
	var updateAbled = commentInputs['updateAbled'].value;
	var deleteAbled = commentInputs['deleteAbled'].value;
	var nodisColumns = commentInputs['nodisColumns'].value;
	var examineAble = commentInputs['examineAble'].value;
	var currentStepId = commentInputs['currentStepId'].value;
	var recordTimeAble= commentInputs['recordTimeAble'].value;
	var isDesc= commentInputs['isDesc'].value;
	var processLinkAble= commentInputs['processLinkAble'].value;
	//alert(instanceId+','+addAbled+','+updateAbled+','+deleteAbled+','+nodisColumns+','+currentStepId+',');
	$.ajax({
	    url: 'comment.do',
	    type: 'POST',
	    cache:false,
	    async:false,
	    data:{'comment.instanceId':commentInstanceId,'commentObjId':commentObjId,'addAbled':addAbled,'updateAbled':updateAbled,'deleteAbled':deleteAbled,'currentStepId':currentStepId ,'nodisColumns':nodisColumns,'examineAble':examineAble,'recordTimeAble':recordTimeAble,'isDesc':isDesc,'processLinkAble':processLinkAble},
	    error: function(){
	        alert('意见标签初始化失败');
	    },
	    success: function(msg){
		   //alert(msg);
	       var tableOBj = document.getElementById(commentObjId);
	       var row = tableOBj.insertRow(0);
	       var col = row.insertCell(0);
	       col.innerHTML = msg;
	      
		   //动态生成签字falsh图		add by panh in 20110902
	       if(window.createQianPiFlash){
	    	   createQianPiFlash(commentObjId);
	    	   try{
	    	   document.all.DWebSignSeal.LockSealPosition("");
	    	   }catch(e){}
	       }
	     
	       //隐藏上一次操作的行(解决falsh的二次生成出现的问题,必须采用此方式)	add by panh in 20110902
	       if(tableOBj.rows.length>=2){
	    	   tableOBj.rows[1].style.display='none';
	       }
	      
	       //关闭子页面窗口 
	       if(window.commentOpener){
	    	   window.commentOpener.close();
	       }
	       
	       //新增手写签批后定位问题
	       if(document.getElementById('a_new_cab_pos')){
	    	   //alert(document.getElementById('a_new_cab_pos'));
	    	   //document.getElementById('a_new_cab_pos').click();
	  	   }
	       
	       jQswapbgcolor();
	       //加载删除按钮样式
	       loadDelBtnCss();
	    }
	});	
}

/**
 * 打开意见标签录入界面
 * @param {Object} commentObjId
 * @param {Object} commentId
 * @param {Object} instanceId
 * @param {Object} addAbled
 * @param {Object} deleteAble
 * @param {Object} updateAble
 * @param {Object} qianpiAble
 * @param {Object} recordTimeAble
 */
function openCommentDialog(commentObjId,commentId,instanceId,addAbled,deleteAble,updateAble,qianpiAble,recordTimeAble,handWriteAble,processLinkAble,isSatisfied){
	//var commentTitle = (document.getElementById("test").getElementsByTagName("input"))[1].value;//标题
	var stepUid = '';
	if(commentId!=''){
		stepUid = (document.getElementById(commentObjId).getElementsByTagName('input'))['currentStepId'].value;//意见所在的步骤唯一标识Id
	}else if((document.getElementById(commentObjId).getElementsByTagName('input'))['currentStepId']){
		stepUid = (document.getElementById(commentObjId).getElementsByTagName('input'))['currentStepId'].value;//意见所在的步骤唯一标识Id
	} 
	var url = 'comment_writeComment.do';
	url = url + '?d='+Math.random()+'&commentObjId=' + commentObjId + '&comment.id=' + commentId + '&currentStepId=' + stepUid + '&comment.instanceId=' + instanceId + '&addAbled=' + addAbled +'&updateAble=' + updateAble + '&deleteAble=' + deleteAble+'&qianpiAble='+qianpiAble+'&processLinkAble='+processLinkAble+'&isSatisfied='+isSatisfied;
	var commentInputs = document.getElementById(commentObjId).getElementsByTagName('input');
	var examineAble = commentInputs['examineAble'].value;
	if (examineAble==="true"){
		url = url + "&comment.approved=1";
	}
	//办文流程中，把节点类型改成‘竞争’
	if(handWriteAble){
		setStepType();
	}
	var ret_msg="";
	if(qianpiAble){//如果是手写签批,默认全屏
		ret_msg = window.showModalDialog(url,window,'dialogWidth:250px; dialogHeight:50px; center:yes; help:no; status:no; resizable:no;scroll:no;');
	}else{
		ret_msg = window.showModalDialog(url,window,'dialogWidth:600px; dialogHeight:400px; center:yes; help:no; status:no; resizable:no;scroll:no;');
	}
	if(ret_msg=='add'){ 
		initCommentFun(commentObjId);
		if(recordTimeAble){
			recordTime(instanceId);
		}
	}
	if(ret_msg=='delete'){
		initCommentFun(commentObjId);
		deleteRecordTime(instanceId);
	}
}
 
 
 function deleteComment(commentObjId,commentId,instanceId){
 	if(window.confirm("确定删除此意见？")){
         $.ajax({
             url: 'comment_deleteComment.do',
             type: 'POST',
             cache: false,
             async: false,
             data: {
                 'comment.id': commentId
             },
             error: function(){
                 alert('AJAX调用删除方法失败！');
                 window.returnValue = '3';
             },
             success: function(msg){
                 if (msg == '0') {
                     alert("系统出现异常，请重试");
                     window.returnValue = '0';
                 } else if (msg == '1') {
             		initCommentFun(commentObjId);
             		deleteRecordTime(instanceId);
                 } else if (msg == '2') {
                     alert("你没有权限删除他人签署的意见信息");
                     window.returnValue = '2';
                 } else {
                     alert("未知错误");
                     window.returnValue = '3';
                 }
             }
         });
     }
 }

/**
 * 判断是否填写
 */
function isOrNotWrite(tagId,currentStepId,errorMsg){ 
	var isWrite=false;
	$.ajax({
	    url: 'comment_isOrNotWrite.do',
	    type: 'POST',
	    cache:false,
	    async:false,
	    data: {
			'tagId': tagId,
			'currentStepId': currentStepId
		},
	    error: function(){
	        alert('意见标签是否填写验证失败');
			return false;
	    },
	    success: function(msg){
			if(msg=="yes"){
				isWrite= true;
			}
	    }
	});	
	if(!isWrite){
		alert(errorMsg);
		return false;
	}else{
		return true;
	}
}
 
 function isOrNotWrite2(tagId,currentStepId){ 
		var isWrite=false;
		$.ajax({
		    url: 'comment_isOrNotWrite.do',
		    type: 'POST',
		    cache:false,
		    async:false,
		    data: {
				'tagId': tagId,
				'currentStepId': currentStepId
			},
		    error: function(){
		        alert('意见标签是否填写验证失败');
				return false;
		    },
		    success: function(msg){
				if(msg=="yes"){
					isWrite= true;
				}
		    }
		});	
		if(!isWrite){
			return false;
		}else{
			return true;
		}
	}



/**
 * 判断是否审核
 * @param {Object} commentObjId
 * @param {Object} commentInstanceId
 * @param {Object} currentStepId
 */
function isOrNotApproved(tagId,currentStepId,errorMsg){
	var isApproved=false;
	$.ajax({
    url: 'comment_isOrNotApproved.do',
    type: 'POST',
    cache:false,
    async:false,
    data: {
		'commentObjId': tagId,
		'currentStepId': currentStepId
	},
    error: function(){
        alert('意见标签是否审核验证失败');
    },
    success: function(msg){
		if(msg=="yes"){
			isApproved=true;
		}
    }
	});	
	if(!isApproved){
		alert(errorMsg);
		return false;
	}else{
		return true;
	}
}
 /****************************记录批阅时间*****************************/
 function recordTime(instanceId){
	 $.ajax({
	        url: 'comment_recordTime.do',
	        type: 'POST',
	        cache: false,
	        async: false,
	        data: {
	            'comment.instanceId': instanceId
	        },
	        error: function(){
	            alert('异步记录时间发生错误');
	        },
	        success: function(msg){
	        	markRecordTime(msg,instanceId);
	        }
	    });
 }
 /****************************显示标记批阅时间*****************************/
 function loadRecordTime(instanceId){
	 $.ajax({
	        url: 'comment_loadRecordTime.do',
	        type: 'POST',
	        cache: false,
	        async: false,
	        data: {
	            'comment.instanceId': instanceId
	        },
	        error: function(){
	            alert('异步显示时间发生错误');
	        },
	        success: function(msg){
	        	if(msg!=''&&msg!='null'){
	        		markRecordTime(msg,instanceId);
	        	}
	        }
	    });
 }
 //标记记录时间
 function markRecordTime(cyry,instanceId){
	 if (cyry != "" && cyry != null) {
		 var cyrydata = cyry.split(";");
		 for ( var k = 0; k < cyrydata.length - 1; k++) {
			 var cyrymm = cyrydata[k].split(",");
			 if(document.getElementById(trim(cyrymm[0]))){
				 document.getElementById(trim(cyrymm[0])).innerText = cyrymm[1];
			 }
		 }
	 }	 
 }
 //标记未记录时间的人员
 function markEnRecordTime(cyry,instanceId){  
	 if (cyry != "" && cyry != null) {
			var cyrydata = cyry.split(";");
			for ( var k = 0; k < cyrydata.length - 1; k++) {
				var cyrymm = cyrydata[k].split(",");
				 if(document.getElementById(trim(cyrymm[0]))){
					 document.getElementById(trim(cyrymm[0])).innerText = "";
				 }
 			}
		}	 
 }
 function trim(str){
     return str.replace(/^\s+|\s+$/g, "");
 }
 /****************************删除意见后显示意见*****************************/
 function deleteRecordTime(instanceId){
	 $.ajax({
	        url: 'comment_deleteRecordTime.do',
	        type: 'POST',
	        cache: false,
	        async: false,
	        data: {
	            'comment.instanceId': instanceId
	        },
	        error: function(){
	            alert('异步删除记录时间发生错误');
	        },
	        success: function(msg){
	        	//if(msg.toUpperCase()!="NOCHANGE"){
	        		markEnRecordTime(msg,instanceId);
	        	//}
	        }
	    });
 }

/****************************已阅*****************************/
function saveCommentWithYY(commentObjId,commentId,instanceId,stepId,addAbled,deleteAble,updateAble,qianpiAble,recordTimeAble){
    //var oldContent = "${comment.content}";
	var approved=0;
	if(recordTimeAble){
         recordTime(instanceId);
		 approved=2;
    }
	var content = '已阅';
  	var path="";
    $.ajax({
        url: 'comment_addOrModifyComment.do',
        type: 'POST',
        cache: false,
        async: false,
        data: {
            'comment.instanceId': instanceId,
            'comment.id': commentId,
            'comment.stepId': stepId,
            'comment.content': content,
            'comment.tagId': commentObjId,
            'comment.approved': approved
        },
        error: function(){
            alert('AJAX调用错误');
            window.returnValue = '3';
        },
        success: function(msg){
            if (msg == '0') {
                alert("系统出现异常，请重试");
                window.returnValue = '0';
            } else if (msg == '1') {
            	alert("您已成功录入“已阅”意见!");
                //保存图片
                  initCommentFun(commentObjId);
                  //if(recordTimeAble){
                	//  recordTime(instanceId);
                  //}
                //window.close();
                //window.returnValue = '1';
            } else if (msg == '2') {
                alert("你没有权限修改他人签署的意见信息");
                window.returnValue = '2';
            } else {
                alert("未知错误");
                window.returnValue = '3';
            }
        }
    });
	
}

//加载删除按钮样式
function loadDelBtnCss(){
	//BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid
	$(".del_btn").css("border-right","#7b9ebd 1px solid");
	$(".del_btn").css("border-top","#7b9ebd 1px solid");
	$(".del_btn").css("padding-left","2px");
	$(".del_btn").css("padding-right","2px");
	$(".del_btn").css("font-size","14px");
	$(".del_btn").css("filter","progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde)");
	$(".del_btn").css("border-left","#7b9ebd 1px solid");
	$(".del_btn").css("cursor","hand");
	$(".del_btn").css("color","black");
	$(".del_btn").css("padding-top","2px");
	$(".del_btn").css("border-bottom","#7b9ebd 1px solid");
}

/****************************手写签批相关  开始*****************************/
var temp_Divflashs=new Array();
var all_cab_ids=new Array();//记录当前页面所有已展示的手写签批falsh图id
var all_cab_usernames=new Array();//记录当前页面所有已展示的手写签批falsh图姓名
//js动态展现表格内签字flash 
function createQianPiFlash(tableid){
	var tbl=document.getElementById(tableid).rows[0].cells[0].childNodes[0];
	var trs=tbl.rows;
	for(var i=0;i<trs.length;i++){
		var firstTd=trs[i].cells[0];
		//如果td内容需要进行签字图片转换
		//alert(firstTd.innerHTML);
		//alert(firstTd.childNodes.innerHTML);
		if(firstTd.childNodes&&(firstTd.innerHTML.match(/<DIV/)||firstTd.innerHTML.match(/<div/))){
			var srcDiv=firstTd.childNodes[0];
			var srcId=firstTd.childNodes[1].innerHTML;
			var baseDiv=firstTd.childNodes[2];
			var username=firstTd.childNodes[3].innerHTML;
			temp_Divflashs.push(srcId);
			SetValue_onclick(srcDiv.innerHTML);
			//修改flash签字层的属性 
			$("#"+srcId).css("position","relative");
			$("#"+srcId).css("border","0px solid red");
			//把flash签字层定位到固定位置(新加对象至固定位置)
			baseDiv.parentNode.insertBefore(document.getElementById(srcId), baseDiv);
		};
	};
	try{
		setRightMouseEvent();
	}catch(e){}
};
//根据数据源展现签字图
function SetValue_onclick(str){
	document.all.DWebSignSeal.SetStoreData(str);
	document.all.DWebSignSeal.ShowWebSeals();
	//alert(document.all.DWebSignSeal.GetStoreData());
}
/*
 * 设置签字图层右键菜单
	MENU_DelSeal					= 0x01,	    //删除印章   
   MENU_MoveSeal				= 0x02,      //移动印章       
   MENU_SealInfo				    = 0x04,      //印章信息       
   MENU_UserInfo				    = 0x08,      //身份信息                       
   MENU_DocVerify				= 0x10,      //文档验证   
   MENU_SealVerify				= 0x20,      //签名验证  
   MENU_Field_LockOrUnLock		= 0x40,      //区域解锁  区域锁定         
   MENU_Doc_LockOrUnlock		= 0x80,      //文档解锁  文档锁定    
   MENU_Pos_Lock				= 0x100,     //锁定位置	 
   MENU_Version					= 0x8000		//关于我们

 */
function setRightMouseEvent(){
	var strObjectName ;
	strObjectName = document.all.DWebSignSeal.FindSeal("",0);
	while(strObjectName  != ""){
		document.all.DWebSignSeal.SetMenuItem(strObjectName,0x04);
		strObjectName = document.all.DWebSignSeal.FindSeal(strObjectName,0);
	}

}

/****************************手写签批相关  结束*****************************/
/****************************全屏手写签批  开始*****************************/

	var current_qianpi_id=null;//当前div签批falsh对象
	function writeAlert(commentId,instanceId,stepId,userId,userName,recordTimeAble){
		/********手写签批框相关	开始*********/
    	//打开全屏手写框
		var divId=HandWritePop_onclick(userName);
		//********关键点，防止因控件漏洞影响功能*********
		if(divId==''){//如果用户关闭手写手写框
			window.location.reload();
			return false;
		}else if(divId!=''&&($("#"+divId).css("height")).match(/-/)){
			window.location.reload();
			return false;
		}
		
		var divContent=GetValue_onclick(divId);
		/********手写签批框相关	结束*********/
		
		//办文流程中，把节点类型改成‘竞争’
			setStepType();
		
		/********异步保存数据	开始*********/
		var instanceId = instanceId;
        var stepId = stepId;
        var userId = userId;
        var userName = userName;
        var content = '';
        $.ajax({
            url: 'comment_addOrModifyComment.do',
            type: 'POST',
            cache: false,
            async: false,
            data: {
                'comment.instanceId': instanceId,
                'comment.stepId': stepId,
                'comment.userId': userId,
                'comment.userName': userName,
                'comment.content': content,
                'comment.tagId': commentId, //20101207 add
                'comment.cabWrite':divContent, 			//20110901 add
                'comment.cabWriteId':divId, 	//20110901 add
                'comment.approved': '0'
            },
            error: function(){
                alert('AJAX调用错误');
                window.returnValue = '3';
            },
            success: function(msg){ 
                if (msg == '0') {
                    alert("系统出现异常，请重试");
                    window.returnValue = '0';
                } else if (msg == '1') {
                    //alert("成功添加或者修改意见信息");
                    //调用父页面方法
                    window.initCommentFun(commentId);
                    if(recordTimeAble){
                  	  recordTime(instanceId);
                    }
                    //window.close();
                    window.returnValue = '1';
                    //window.location.reload();
                } else if (msg == '2') {
                    alert("你没有权限修改他人签署的意见信息");
                    window.returnValue = '2';
                } else {
                    alert("未知错误");
                    window.returnValue = '3';
                }
            }
        });
		
		
		
		/********异步保存数据	结束*********/
		
		
		
		
	};
	
	//获得最新的签字存库数据源
	function GetValue_onclick(name){
		if(name){
			return document.all.DWebSignSeal.GetStoreDataEx(name);
		}
		return document.all.DWebSignSeal.GetStoreData();
	}
	function viewData(){
		alert(document.all.DWebSignSeal.GetStoreData());
	}
	function setData(){
		document.all.DWebSignSeal.SetStoreData('');
		document.all.DWebSignSeal.ShowWebSeals();
	}
	function resetWrite(){
		document.getElementById('DWebSignSeal').parentNode.removeChild(document.getElementById('DWebSignSeal'));
		TANGER_OCX_OBJ=null;
		
		var s = "<object id=DWebSignSeal classid='CLSID:77709A87-71F9-41AE-904F-886976F99E3E' style='position:absolute;width:0px;height:0px;left:0px;top:0px;' codebase=component/WebSign.cab#version=4,0,5,2 >"
		s += "</OBJECT>";
		$("#main_div_1").html(s);
		TANGER_OCX_bDocOpen = true;	
		TANGER_OCX_OBJ=document.all("TANGER_OCX");
	}
	
	/****************************全屏手写签批 结束*****************************/

  	//隔行换色 edit by 2011-9-30
    function jQswapbgcolor(){
    	$('.jQswapbgcolor').each(function(){
    		var i=1;
    		var $this=$(this);
    		$('tr',$this).each(function(){
    			if(!!$(this).attr('id')){
    				i++;
    			}
    			if(i%2==0){
    				$('td',$(this)).css({'background-color':'#F2F2F2','padding':'3px','color':'#666','letter-spacing':'0.1em'});
    			}else{
    				$('td',$(this)).css({'background-color':'#FCFCFC','padding':'3px','color':'#666','letter-spacing':'0.1em'});
    			}
    			if(!!$(this).attr('id')){
    				$('td',$(this)).css({'padding-top':'8px','border-top':'1px dotted #CCC','color':'#000','line-height':'1.8em'});
    			}
    		});
    	});
  	};
  	$(document).ready(function(){
  		jQswapbgcolor();
  	});
	
	function downloadAtt(name,location){
    	var flag = false;
		$.ajax({
		    url: 'comment_checkFileExist.do',
		    type: 'POST',
		    cache:false,
		    async:false,
		    data: {'location': location},
		    error: function(){
    		    alert("下载失败");
		    },
		    success: function(msg){
				if(msg=="yes"){
					flag = true;
				}else if(msg=="no"){
					alert("要下载的文件不存在");
				}
		    }
		});	
		if(flag){ 
			window.location.href="comment_downloadAtt.do?name="+encodeURI(name)+"&location="+location;
		}
    }
	
	function viewCeb(filepathAndName,location){
		var flag = false;
		$.ajax({
		    url: 'comment_checkFileExist.do',
		    type: 'POST',
		    cache:false,
		    async:false,
		    data: {'location': location},
		    error: function(){
    		    alert("下载失败");
		    },
		    success: function(msg){
				if(msg=="yes"){
					flag = true;
				}else if(msg=="no"){
					alert("要下载的文件不存在");
				}
		    }
		});	
		if(flag){ 
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
			//预览
			
			viewceb(fileName)
		}
	}
	
	function viewceb(filepath){
		var verReader = 3229;
		var strErrMessage, lRet, strCEBFileUrl, ctrlXml, left, right, top, bottom;
		var opUINavigation, opUISelect, opUISaveAs;
		var opUIFile, opUIZoom, opUILayout;
		var opPluginAttachment, opPluginForm, opPluginVisualStamp, opPluginAnnot;
		strErrMessage = "";
		strCEBFileUrl ="";
		if(filepath == null || filepath == '' || filepath.toLowerCase().indexOf('.ceb') < 1){
			alert('请选择CEB格式文件！');
			return false;
		}
	    ctrlXml = "<?xml version='1.0' encoding='GB2312'?><CPreviewCEBParam><PreviewCEBParamXMLVer></PreviewCEBParamXMLVer>";
	    ctrlXml += "<ApabiReaderVer>" + verReader + "</ApabiReaderVer>";
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
	}
	
	function viewDoc(location){
		var flag = false;
		$.ajax({
		    url: 'comment_checkFileExist.do',
		    type: 'POST',
		    cache:false,
		    async:false,
		    data: {'location': location},
		    error: function(){
    		    alert("下载失败");
		    },
		    success: function(msg){
				if(msg=="yes"){
					flag = true;
				}else if(msg=="no"){
					alert("要下载的文件不存在");
				}
		    }
		});	
		if(flag){ 
            var sheight = screen.height-70;
            var swidth = screen.width-10;
            var winoption  ="left=0,top=0,height="+sheight+",width="+swidth+",toolbar=yes,menubar=yes,location=yes,status=yes,scrollbars=yes,resizable=yes";
            window.open("comment_toViewDoc.do?location="+location,'',winoption);
		}
	}

	/**
	 *  设置当前step的Type，用于“已阅”、“阅批”的处理
	 * 
	 * 返回值为 空：不改变   0：并行  1：竞争
	 */
	function setStepType(){
		try{
			parent.document.getElementById('nowStepType').value = 1;
			//alert("节点类型改成竞争成功1");
		}catch(e){
			try{
				document.getElementById('nowStepType').value = 1;
				//alert("节点类型改成竞争成功2");
			}catch(e){
			}
		}
	}
