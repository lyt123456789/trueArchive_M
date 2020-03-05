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
	var isSatisfied= commentInputs['isSatisfied'].value;
	$.ajax({
	    url: 'comment.do',
	    type: 'POST',
	    cache:false,
	    async:false,
	    data:{'comment.instanceId':commentInstanceId,'commentObjId':commentObjId,'addAbled':addAbled,'updateAbled':updateAbled,'deleteAbled':deleteAbled,'currentStepId':currentStepId ,'nodisColumns':nodisColumns,'examineAble':examineAble,'recordTimeAble':recordTimeAble,'isDesc':isDesc,'processLinkAble':processLinkAble,'isSatisfied':isSatisfied},
	    error: function(){
	        alert('\u610f\u89c1\u6807\u7b7e\u521d\u59cb\u5316\u5931\u8d25');
	    },
	    success: function(msg){
		   //alert(msg);
	       var tableOBj = document.getElementById(commentObjId);
	       var row = tableOBj.insertRow(0);
	       var col = row.insertCell(0);
	       col.innerHTML = msg;
	      
	       if(window.createQianPiFlash){
	    	   createQianPiFlash(commentObjId);
	    	   try{
	    	   document.all.DWebSignSeal.LockSealPosition("");
	    	   }catch(e){}
	       }
	     
	       if(tableOBj.rows.length>=2){
	    	   tableOBj.rows[1].style.display='none';
	       }
	      
	       if(window.commentOpener){
	    	   window.commentOpener.close();
	       }
	       
	       if(document.getElementById('a_new_cab_pos')){
	  	   }
	       
	       jQswapbgcolor();
	       loadDelBtnCss();
	    }
	});	
}

function openCommentDialog(commentObjId,commentId,instanceId,addAbled,deleteAble,updateAble,qianpiAble,recordTimeAble,handWriteAble,processLinkAble,isSatisfied){
	var stepUid = '';
	if(commentId!=''){
		stepUid = (document.getElementById(commentObjId).getElementsByTagName('input'))['currentStepId'].value;
	}else if((document.getElementById(commentObjId).getElementsByTagName('input'))['currentStepId']){
		stepUid = (document.getElementById(commentObjId).getElementsByTagName('input'))['currentStepId'].value;
	} 
	var url = 'comment_writeComment.do';
	url = url + '?d='+Math.random()+'&commentObjId=' + commentObjId + '&comment.id=' + commentId + '&currentStepId=' + stepUid + '&comment.instanceId=' + instanceId + '&addAbled=' + addAbled +'&updateAble=' + updateAble + '&deleteAble=' + deleteAble+'&qianpiAble='+qianpiAble+'&processLinkAble='+processLinkAble+'&isSatisfied='+isSatisfied;
	var commentInputs = document.getElementById(commentObjId).getElementsByTagName('input');
	var examineAble = commentInputs['examineAble'].value;
	if (examineAble==="true"){
		url = url + "&comment.approved=1";
	}
	if(handWriteAble){
		setStepType();
	}
	var ret_msg="";
	if(qianpiAble){
		ret_msg = window.showModalDialog(url,window,'dialogWidth:250px; dialogHeight:50px; center:yes; help:no; status:no; resizable:no;scroll:no;');
	}else{
		ret_msg = window.showModalDialog(url,window,'dialogWidth:700px; dialogHeight:500px; center:yes; help:no; status:no; resizable:no;scroll:no;');
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
 	if(window.confirm("\u786e\u5b9a\u5220\u9664\u6b64\u610f\u89c1\uff1f")){
         $.ajax({
             url: 'comment_deleteComment.do',
             type: 'POST',
             cache: false,
             async: false,
             data: {
                 'comment.id': commentId
             },
             error: function(){
                 alert('AJAX\u8c03\u7528\u5220\u9664\u65b9\u6cd5\u5931\u8d25\uff01');
                 window.returnValue = '3';
             },
             success: function(msg){
                 if (msg == '0') {
                     alert("\u7cfb\u7edf\u51fa\u73b0\u5f02\u5e38\uff0c\u8bf7\u91cd\u8bd5");
                     window.returnValue = '0';
                 } else if (msg == '1') {
             		initCommentFun(commentObjId);
             		deleteRecordTime(instanceId);
                 } else if (msg == '2') {
                     alert("\u4f60\u6ca1\u6709\u6743\u9650\u5220\u9664\u4ed6\u4eba\u7b7e\u7f72\u7684\u610f\u89c1\u4fe1\u606f");
                     window.returnValue = '2';
                 } else {
                     alert("\u672a\u77e5\u9519\u8bef");
                     window.returnValue = '3';
                 }
             }
         });
     }
 }

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
	        alert('\u610f\u89c1\u6807\u7b7e\u662f\u5426\u586b\u5199\u9a8c\u8bc1\u5931\u8d25');
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
		        alert('\u610f\u89c1\u6807\u7b7e\u662f\u5426\u586b\u5199\u9a8c\u8bc1\u5931\u8d25');
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
        alert('\u610f\u89c1\u6807\u7b7e\u662f\u5426\u5ba1\u6838\u9a8c\u8bc1\u5931\u8d25');
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
	            alert('\u5f02\u6b65\u8bb0\u5f55\u65f6\u95f4\u53d1\u751f\u9519\u8bef');
	        },
	        success: function(msg){
	        	markRecordTime(msg,instanceId);
	        }
	    });
 }
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
	            alert('\u5f02\u6b65\u8bb0\u5f55\u65f6\u95f4\u53d1\u751f\u9519\u8bef');
	        },
	        success: function(msg){
	        	if(msg!=''&&msg!='null'){
	        		markRecordTime(msg,instanceId);
	        	}
	        }
	    });
 }
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
 function markEnRecordTime(cyry,instanceId){  
	 if (cyry != "" && cyry != null) {
			var cyrydata = cyry.split(";");
			for ( var k = 0; k < cyrydata.length - 1; k++) {
				var cyrymm = cyrydata[k];
				if(document.getElementById(trim(cyrymm))){
				document.getElementById(trim(cyrymm)).innerText ="";
				}
 			}
		}	 
 }
 function trim(str){
     return str.replace(/^\s+|\s+$/g, "");
 }
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
	            alert('\u5f02\u6b65\u8bb0\u5f55\u65f6\u95f4\u53d1\u751f\u9519\u8bef');
	        },
	        success: function(msg){
	        	//alert(msg);
	        	if(msg.toUpperCase()!="NOCHANGE"){
	        		markEnRecordTime(msg,instanceId);
	        	}
	        }
	    });
 }

function saveCommentWithYY(commentObjId,commentId,instanceId,stepId,addAbled,deleteAble,updateAble,qianpiAble,recordTimeAble){
    //var oldContent = "${comment.content}";
	var approved=0;
	if(recordTimeAble){
         recordTime(instanceId);
		 approved=2;
    }
	var content = '\u5df2\u9605';
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
            alert('AJAX\u8c03\u7528\u9519\u8bef');
            window.returnValue = '3';
        },
        success: function(msg){
            if (msg == '0') {
                alert("\u7cfb\u7edf\u51fa\u73b0\u5f02\u5e38\uff0c\u8bf7\u91cd\u8bd5");
                window.returnValue = '0';
            } else if (msg == '1') {
            	alert("\u60a8\u5df2\u6210\u529f\u5f55\u5165\u201c\u5df2\u9605\u201d\u610f\u89c1!");
                  initCommentFun(commentObjId);
            } else if (msg == '2') {
                alert("\u4f60\u6ca1\u6709\u6743\u9650\u4fee\u6539\u4ed6\u4eba\u7b7e\u7f72\u7684\u610f\u89c1\u4fe1\u606f");
                window.returnValue = '2';
            } else {
                alert("\u672a\u77e5\u9519\u8bef");
                window.returnValue = '3';
            }
        }
    });
	
}

function loadDelBtnCss(){
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

var temp_Divflashs=new Array();
var all_cab_ids=new Array();
var all_cab_usernames=new Array();
function createQianPiFlash(tableid){
	var tbl=document.getElementById(tableid).rows[0].cells[0].childNodes[0];
	var trs=tbl.rows;
	for(var i=0;i<trs.length;i++){
		var firstTd=trs[i].cells[0];
		if(firstTd.childNodes&&(firstTd.innerHTML.match(/<DIV/)||firstTd.innerHTML.match(/<div/))){
			var srcDiv=firstTd.childNodes[0];
			var srcId=firstTd.childNodes[1].innerHTML;
			var baseDiv=firstTd.childNodes[2];
			var username=firstTd.childNodes[3].innerHTML;
			temp_Divflashs.push(srcId);
			SetValue_onclick(srcDiv.innerHTML);
			$("#"+srcId).css("position","relative");
			$("#"+srcId).css("border","0px solid red");
			baseDiv.parentNode.insertBefore(document.getElementById(srcId), baseDiv);
		};
	};
	try{
		setRightMouseEvent();
	}catch(e){}
};
function SetValue_onclick(str){
	document.all.DWebSignSeal.SetStoreData(str);
	document.all.DWebSignSeal.ShowWebSeals();
}
function setRightMouseEvent(){
	var strObjectName ;
	strObjectName = document.all.DWebSignSeal.FindSeal("",0);
	while(strObjectName  != ""){
		document.all.DWebSignSeal.SetMenuItem(strObjectName,0x04);
		strObjectName = document.all.DWebSignSeal.FindSeal(strObjectName,0);
	}

}


	var current_qianpi_id=null;
	function writeAlert(commentId,instanceId,stepId,userId,userName,recordTimeAble){
		var divId=HandWritePop_onclick(userName);
		if(divId==''){
			window.location.reload();
			return false;
		}else if(divId!=''&&($("#"+divId).css("height")).match(/-/)){
			window.location.reload();
			return false;
		}
		
		var divContent=GetValue_onclick(divId);
			setStepType();
		
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
                alert('AJAX\u8c03\u7528\u9519\u8bef');
                window.returnValue = '3';
            },
            success: function(msg){ 
                if (msg == '0') {
                    alert("\u7cfb\u7edf\u51fa\u73b0\u5f02\u5e38\uff0c\u8bf7\u91cd\u8bd5");
                    window.returnValue = '0';
                } else if (msg == '1') {
                    window.initCommentFun(commentId);
                    if(recordTimeAble){
                  	  recordTime(instanceId);
                    }
                    window.returnValue = '1';
                } else if (msg == '2') {
                    alert("\u4f60\u6ca1\u6709\u6743\u9650\u4fee\u6539\u4ed6\u4eba\u7b7e\u7f72\u7684\u610f\u89c1\u4fe1\u606f");
                    window.returnValue = '2';
                } else {
                    alert("\u672a\u77e5\u9519\u8bef");
                    window.returnValue = '3';
                }
            }
        });
		
		
		
		
	};
	
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
    		    alert("\u4e0b\u8f7d\u5931\u8d25");
		    },
		    success: function(msg){
				if(msg=="yes"){
					flag = true;
				}else if(msg=="no"){
					alert("\u8981\u4e0b\u8f7d\u7684\u6587\u4ef6\u4e0d\u5b58\u5728");
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
    		    alert("\u4e0b\u8f7d\u5931\u8d25");
		    },
		    success: function(msg){
				if(msg=="yes"){
					flag = true;
				}else if(msg=="no"){
					alert("\u8981\u4e0b\u8f7d\u7684\u6587\u4ef6\u4e0d\u5b58\u5728");
				}
		    }
		});	
		if(flag){ 
			var filepath = filepathAndName;
			var WshShell = new ActiveXObject("WScript.Shell"); 
			var keyValue = WshShell.RegRead('HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Internet Settings\\Cache\\Paths\\Directory');
			var PostRecvImpl = new ActiveXObject("ASPCom.PostRecv");
			var extName = filepath.substring(filepath.lastIndexOf('.'));
			var fileName = new Date().getTime() + extName;
			fileName = keyValue+"\\"+fileName;
			fileName = fileName.replace(/\\/gm,'\\\\');
			PostRecvImpl.HTTPDownloadFile(fileName,filepath);
			
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
			alert('\u8bf7\u9009\u62e9CEB\u683c\u5f0f\u6587\u4ef6\uff01');
			return false;
		}
	    ctrlXml = "<?xml version='1.0' encoding='GB2312'?><CPreviewCEBParam><PreviewCEBParamXMLVer></PreviewCEBParamXMLVer>";
	    ctrlXml += "<ApabiReaderVer>" + verReader + "</ApabiReaderVer>";
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
    		    alert("\u4e0b\u8f7d\u5931\u8d25");
		    },
		    success: function(msg){
				if(msg=="yes"){
					flag = true;
				}else if(msg=="no"){
					alert("\u8981\u4e0b\u8f7d\u7684\u6587\u4ef6\u4e0d\u5b58\u5728");
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

	function setStepType(){
		try{
			parent.document.getElementById('nowStepType').value = 1;
		}catch(e){
			try{
				document.getElementById('nowStepType').value = 1;
			}catch(e){
			}
		}
	}
