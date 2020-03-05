<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>在线编辑正文</title>
        <meta http-equiv="cache-control" content="no-cache,must-revalidate">
        <meta http-equiv="pragram" content="no-cache">
        <meta http-equiv="expires" content="0">
        <%@ include file="/common/header.jsp" %>
        <link rel="stylesheet" href="${ctx}/assets/themes/default/css/style.css" type="text/css" />
        <script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
        <script type="text/javascript" src="${ctx}/widgets/component/ntko/js/ntko.js"></script>
        <script type="text/javascript"  src="${ctx}/widgets/component/taglib/attachment/js/attachment.js?t=201902011833"></script>
        <style type="text/css">
        	.wf-btn-primary {
				padding: 4px 8px;
			}
			
			html,body,.ocxobjdiv{
				height: 100%;
			}
			
			object{
				height: 96%;
			}
			
			#jzk1{
			    position: absolute;
			    z-index: 998;
			    height: 100%;
			    width: 100%;
			   /* background-image: URL('${ctx}/dwz/style/images/zwbackground.jpg');*/
			}
			
			#dialog{
				margin-top: 19%;
				z-index:999;
			}
        </style>
    </head>
    <body>
    	<div  style="display: none;" id="jzk1">
	 		<div id='fullbg'></div><div id='dialog' style='text-align:center'>
	 			<img src='${ctx}/dwz/style/images/loading.gif'>
	 		</div>
	 	</div>
	 	
		<form action="${ctx}/attachment_saveAttHistory.do" method="post" enctype="multipart/form-data">
			<input type="hidden" id="attId" value="${attId}"/>
			<div class="wf-layout">
				<div class="wf-list-top">
					<div class="wf-search-bar">
						<div class="wf-top-tool" style="margin-top:-17px;">
				            <a class="wf-btn-primary" id="saveAtt" style="cursor: pointer;font-size: 16px;" onclick="">保存</a>
							<c:if test="${isFirst != 'true'}">
								<c:if test="${null == showMarkbtn || '' == showMarkbtn || showMarkbtn != '1'}">
									<input class="wf-btn-primary" id="showMark" style="display: none;font-size: 16px;" type="button" value="显示痕迹"/>
									<input class="wf-btn-green" id="hideMark" style="font-size: 16px;" type="button" value="隐藏痕迹"/>
								</c:if>
							</c:if>
				        	<c:if test="${list!=null && list != '[]'}">
								<select class="wf-form-select" name="dw" id="dw">
								<option value=""></option>
								<c:forEach items="${list}" var="shade" >
									<option value="${shade.vc_path},${shade.id},${shade.position}">${shade.vc_cname}</option>
								</c:forEach>
								</select>
								<a class="wf-btn-green" id="toredHead" style="cursor: pointer;font-size: 16px;" onclick="">套红头</a>
							</c:if>
							<c:if test="${sfqg =='true'}">
								<a class="wf-btn-green" id="cleanAtt" style="cursor: pointer;font-size: 16px;" onclick="">清稿</a>
							</c:if>				
							<a class="wf-btn-green" id="print" style="cursor: pointer;font-size: 16px;">打印</a>	
				        </div>
					</div>
				</div>
			</div>
		</form>
    	<!-- ntko跨浏览器控件加载js (必须) -->
    	<!-- 注意：ie浏览器只能在body中加载，chrome浏览器可以在head中加载 -->
    	<!--控件对象-->
		<div id="ocxobject" class="ocxobjdiv">
			<script type="text/javascript" src="${ctx }/widgets/component/ntko/cross-browserNTKO/ntkoofficecontrol.js"></script>
		</div>
    </body>
    <!-- 加载jquery控件(可选) -->
    <script type="text/javascript" src="${ctx }/widgets/plugin/js/base/jquery.js"></script>
    <!-- ntko文档控件操作方法 -->
    <script type="text/javascript" src="${ctx }/widgets/component/ntko/js/ntkoFunction.js"></script>
    <!-- ntko控件事件方法体，在script体中加入 --><!-- 此回调方式仅支持ie浏览器 -->
    <script language="JScript" for="TANGER_OCX" event="OnDocumentOpened(str,doc)">
    	/* todo something */
    	OnDocumentOpened(str,doc);
    </script>
    <!-- ntko控件相关封装方法 -->
    <script type="text/javascript">
    	var userId = '${sessionScope.loginEmployee.employeeGuid}';
    	var isSaved = false;
    
	    window.onbeforeunload = function(e){
	    	if(!isSaved){
		    	saveEdit();
	    	}
	    	if(TANGER_OCX_OBJ){
	    		TANGER_OCX_OBJ.Close();
	    	}
	    }
	    
	    $("#showMark").bind("click",function(){
	    	TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = true;
	        $("#showMark").css('display','none');
	        $("#hideMark").css('display','');
		});
	    
	    $("#hideMark").bind("click",function(){
	    	TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = false;
	        $("#showMark").css('display','');
	        $("#hideMark").css('display','none');
	    });
    	
	    $("#print").bind("click",function(){
	    	TANGER_OCX_OBJ.PrintOut(true);
	    });
	    
		/*----------按需求调用方法--------------*/
		$(function(){
			debugger;
			init(window.innerHeight-60);//初始化ntko
			menuBar(false);//菜单栏关闭
			setUserName("${empName}");//设置当前用户
			openOfficeFile("${projectUrl}/attachment_download.do?name=a.doc&location=${location}&fileId=${attId}");//加载服务器上的文档
			console.log("${projectUrl}/attachment_download.do?name=a.doc&location=${location}&fileId=${attId}");
			//保存修改后的文档
			$("#saveAtt").bind("click",function(){
				saveEdit();
			});
			
			//清稿操作
			$("#cleanAtt").bind("click",function(){
				qinggao();
			});
			
			/* 套红头  */
			$("#addRedHead").bind("click",function(){
				redHeader();
			});
			
			setTimeout(function(){
				loadBookMark("${templateId}","${instanceId}");
			}, 2000);
		});	
		
		/*通过注册ntko Object时的ForOnDocumentOpened属性注册*/
		function OnDocumentOpened(str,doc){
			console.log("OnDocumentOpened成功回调");
			TANGER_OCX_bDocOpen = true;
			//doc.TrackRevisions = true; //经验证，此方法只能在OnDocumentOpened方法体中调用才能成功开启留痕模式
		    openDocType = TANGER_OCX_OBJ.DocType,
		    15 == TANGER_OCX_OBJ.getOfficeVer() && (TANGER_OCX_OBJ.ActiveDocument.Application.Options.UseLocalUserInfo = !0);
		    if('${isFirst}' != 'true'){
				setReviewMode(true);//开启留痕模式
				showRevisions(true);//显示痕迹
			}
		}
		
		/* 清稿  */
        function qinggao(){
        	if(window.confirm("确定清稿吗？")){
        		acceptOrRejectAllRevisions(true);//清除编辑痕迹，boolevalue=false则还原痕迹，true则接受所有痕迹
        	}
        }
		
		/* 保存修改  */
		function saveEdit(){
			if('${isFirst}' == 'true'){
				acceptOrRejectAllRevisions(true);
			}
			if(!userId){
				alert("登录信息已失效，请重新登录！");
				return;
			}
			zzfs();
			//if(window.confirm("确定保存吗？")){
			    saveAttHistory("${projectUrl}/attachment_saveAttHistory.do?userId="+userId,"${attId}",compute);
			    //alert('保存成功！');
			//}
		}
		
		var saveRedTemplate = false;
		function OnSaveToURL(type,code,html){
			if(saveRedTemplate){
				saveRedTemplate = false;
				return;
			}
			
			if(html=="success"){
				setTimeout(function(){
					if(top.window && top.window.process && top.window.process.type){
			            var remote = top.window.nodeRequire('remote');
					    var browserwindow = remote.require('browser-window');
					    var win = browserwindow.fromId(parseInt($.Request('focusedId')));
						if(win){
							fsjs();
							/* alert('保存成功！'); */
							isSaved = true;
						    win.webContents.send('onlineEdit','success');
		                }
			         }else{
			        	fsjs();
			            /* alert('保存成功！'); */
						isSaved = true;
			            opener.window.returnValue="success";
			            window.close();
					 }
				}, 2000);
		    }else{
		    	alert("保存失败");
		    }	
		}
		
		function compute(ret){
			/* if(ret==""){
				setTimeout(function(){
					console.log('success');
					if(top.window && top.window.process && top.window.process.type){
			            var remote = top.window.nodeRequire('remote');
					    var browserwindow = remote.require('browser-window');
					    var win = browserwindow.fromId(parseInt($.Request('focusedId')));
						if(win){
							alert('保存成功！');
							isSaved = true;
						    win.webContents.send('onlineEdit','success');
		                }
			         }else{
			            alert('保存成功！');
						isSaved = true;
			            opener.window.returnValue="success";
			            window.close();
					 }
				}, 2000);
		    }else{
		    	alert("保存失败");
		    }	 */
		}
		
		function compute1(ret){
			//获取下拉框
        	var fileInfo= $("#dw option:selected").val();
        	
        	if(null == fileInfo || "" == fileInfo){
        		alert("请先选择模板!");
        		return;
        	}
        	var fileName = "";
        	var fileId = "";
        	if(fileInfo!=null && fileInfo!=''){
        		var infos = fileInfo.split(",");
        		fileName = infos[0];
        		fileId = infos[1];
        	}
        	var location = '${sysPath}tempfile/'+fileName+"&isabsolute=1";
    		SetReviewMode(false);
    		
    		//1、加载红头正文模板
        	reloadRedTemplate(location,fileId);
        	SetReviewMode(false);
     		setTimeout(function(){//2、替换常用书签
             	var instanceId = '${instanceId}';
         		loadBookMark(fileId, instanceId);
         		SetReviewMode(true);
         	}, 3000);
		}
		
		//正文模板
        $("#toredHead").bind("click",function(){
        	debugger
        	saveRedTemplate = true;
        	//加载红头之前，保存一下文档
    		var ret = saveAttHistory("${projectUrl}/attachment_saveAttHistory.do","${attId}",function(){});
    		if(ret=="" || ret =="success"){
				//获取下拉框
	        	var fileInfo= $("#dw").val();
	        	
	        	if(null == fileInfo || "" == fileInfo){
	        		alert("请先选择模板!");
	        		return;
	        	}
	        	var fileName = "";
	        	var fileId = "";
	        	if(fileInfo!=null && fileInfo!=''){
	        		var infos = fileInfo.split(",");
	        		fileName = infos[0];
	        		fileId = infos[1];
	        	}
	        	var location = '${sysPath}'+'/tempfile/'+fileName+"&isabsolute=1";
	    		SetReviewMode(false);
	    		//1、加载红头正文模板
	        	reloadRedTemplate(location);
	    		SetReviewMode(false);
	    		//2、替换常用书签
	        	var instanceId = '${instanceId}';
	    		loadBookMark(fileId, instanceId);
	    		SetReviewMode(true);
	    		status = '0';
	    		excuteSave = false;
        	}
        });
		
		
        //重新加载红头模板文件
        function reloadRedTemplate(location,fileId){
    		//正文的下载链接
    		var docURL = '${projectUrl}/attachment_download.do?name=a.doc&location='+location+'&fileId='+fileId;
            TANGER_OCX_Init(docURL);
            //打开修订模式
            //TANGER_OCX_SetDocUser('${userName}');
            var isBlackWord = '${isBlackWord}';
            var reviewMode = true;
            if(isBlackWord == 'true'){
            	reviewMode = false;
            }
            SetReviewMode(reviewMode);
            //设置修订人名字
            //TANGER_OCX_EnableReviewBar(true);
            setShowRevisions(true); 
            
         }
		
		/* 套红头  */
		function redHeader(){
			var fileInfo= $("#dw option:selected").val();
			var fileName = "";
			var fileId = "";
			var position = "";
			if(fileInfo!=null && fileInfo!=''){
				var infos = fileInfo.split(",");
				fileName = infos[0];
				fileId = infos[1];
				position = infos[2];
			}
			if(fileName != null && fileName != ''){
				if(position == "0"){
					addTemplateStart(fileName, fileId);
					//获取底部红头模板
	  	            getButtonTemplate(fileId);
				}else{
					addTemplateEnd(fileName, fileId);
				}
			}else{
				alert("请选择红头模板");
			}
		}
		var fileId4All;
        //文件套红头
        function addTemplateStart(filename,fileId){
			fileId4All = fileId;
        	setReviewMode(false);//开启留痕模式
        	var path="${curl}/tempfile/"+filename;
        	TANGER_OCX_OBJ.ActiveDocument.Application.Selection.HomeKey(6);
        	TANGER_OCX_OBJ.AddTemplateFromURL(path);
        	/* var instanceId = '${instanceId}';
    		loadBookMark(fileId, instanceId);
    		setReviewMode(true);//开启留痕模式 */
        } 
        
        function addTemplateEnd(filename,fileId){
        	fileId4All = fileId;
        	setReviewMode(false);//开启留痕模式
      		var path="${curl}/tempfile/"+filename;
         	TANGER_OCX_OBJ.ActiveDocument.Application.Selection.EndKey(6);
     		TANGER_OCX_OBJ.AddTemplateFromURL(path);
     		/* var instanceId = '${instanceId}';
    		loadBookMark(fileId, instanceId);
    		setReviewMode(true);//开启留痕模式 */
         }
        
    	function OnAddTemplateFromURL(){
    		console.log("OnAddTemplateFromURL成功回调");
    		var instanceId = '${instanceId}';
    		loadBookMark(fileId4All, instanceId);
    		setReviewMode(true);//开启留痕模式 
    	}
    	
        function loadBookMark(templateId, instanceId){
			console.log(templateId);
			console.log(instanceId);
        	var bookmark = "";
          	var bookmarkValue = "";
          	$.ajax({
  				url : '${projectUrl}/template_getBookMarkByTempId.do?fileId=${attId}&instanceId=' + instanceId + '&templateId='+ templateId,
  				type : 'POST',
  				cache : false,
  				async : false,
  				error : function() {
  					alert('获取表单的书签值');
  				},
  				success : function(result) {
  					if(result!=null && result!=''){
  						var vals = result.split(";");
  						if(vals!=null && vals.length==2){
  							bookmark = vals[0];
  							bookmarkValue = vals[1];
							console.log(bookmark);
							console.log(bookmarkValue);
  						}
  					}
  				}
  			});
          	
          	var writeQrcode = '${writeQrcode}';
          	if(bookmark!=''){
      			var bookmarks= bookmark.split(/,/);
      			var bookmarkValues=bookmarkValue.split(/,/);
				console.log(bookmarks);
				console.log(bookmarkValues);
				//alert(bookmarkValues);
      			for(var i=0;i<bookmarks.length;i++){//_attachmentPic
      				var bookmark=bookmarks[i];
      				if(bookmark.match(/_attachmentPic/)){//附件图片特殊处理
      					addPic(bookmarkValues[i]);
      				};	
      				if(bookmark.match(/_qrcodeImg/)){//办结生成二维码图片处理
      					//var mark = TANGER_OCX_OBJ.ActiveDocument.BookMarks.item(bookmark);
      					/*if(TANGER_OCX_OBJ.GetBookMarkValue('qrcode')==' '){
      						continue;
      					}*/
      					if(writeQrcode=='true'){
	      					TANGER_OCX_OBJ.SetBookMarkValue('qrcode',' ');
	      					//addPic(bookmarkValues[i]);
	      					addTemplate(bookmarkValues[i], "qrcode");
	      					TANGER_OCX_OBJ.AddPicFromURL(bookmarkValues[i],false,0,0,3,100,0);
      					}else{
      						continue;
      					}
      				};	
      				if(bookmark.match(/_attachmentDoc/)){//附件图片特殊处理
      					addTemplate(bookmarkValues[i],bookmark.substring(0,bookmark.indexOf("_attachmentDoc")));
      					continue;
      				};
      				var value=bookmarkValues[i];
      				value=value.replace(/#textarea#/g,'\r\n');//替换textarea换行特殊字符串 ，防止js解析报错 add by panh
      				if(value=='null')value='';
      				TANGER_OCX_OBJ.SetBookMarkValue(bookmark,value);
      			};
              };
          }
        
        function addTemplate(filename,bookmark){
        	TANGER_OCX_SetReadOnly(false);
       		var path="${serverUrl}"+filename;
       		console.info(path);
    		//TANGER_OCX_SetMarkModify(false);
    		//TANGER_OCX_OBJ.ActiveDocument.Application.Selection.HomeKey(6); // 跳转到文档头部
    		console.info(bookmark);
    		setTimeout(function(){
	    		var mark = TANGER_OCX_OBJ.ActiveDocument.BookMarks.item(bookmark);
	    		mark.Select(); //选中标签的位置
	    		var curSel = TANGER_OCX_OBJ.ActiveDocument.Application.Selection;	 // 获得刚选中的书签的位置
	    		//插入模板
	    		TANGER_OCX_OBJ.AddTemplateFromURL(path);
	    		if(!TANGER_OCX_OBJ.ActiveDocument.BookMarks.Exists(bookmark))
	    		{
	    			alert("Word 模板中不存在名称为：\""+BookMarkName+"\"的书签！");
	    			return;
	    		}
    		}, 600);
    	} 
        
      //获取配套的文件模板
        function getButtonTemplate(fileId){
			$.ajax({
				url : '${ctx}/template_getButtonTemplate.do?tempId='+fileId,
				type : 'POST',
				cache : false,
				async : false,
				error : function() {
					alert('AJAX调用错误(attachment_getButtonTemplate.do)');
				},
				success : function(result) {
					if(result!=null && result!=''){
						var info = result.split(";");
						var path = info[0];
						var id = info[1];
						addTemplateEnd(path, id);
					}
				}
			});
		}
        
        function addPic(path){
 		    TANGER_OCX_OBJ.AddPicFromURL(
 				path,		//路径
 				false,				//是否浮动图片
 				0,					//如果是浮动图片，相对于左边的left单位磅
 				0,					//如果是浮动图片，相对于当前段落top
 				1,					//当前光标处
 				100,				//无缩放
 				0					//文字上方
 			);
 		}
        
        function test1(){
        	var mark = TANGER_OCX_OBJ.ActiveDocument.BookMarks('test');
        	console.info(mark);
        }
        
        function zzfs() {
    		document.getElementById("jzk1").style.display = '';
    		$('#ocxobject').height("1px");
    	}
    	function fsjs() {
    		document.getElementById("jzk1").style.display = 'none';
    		$('#ocxobject').height("100%");
    	}
    </script>
<script>
(function ($) {
 $.extend({
  Request: function (m) {
   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
   return sValue ? sValue[1] : sValue;
  },
  UrlUpdateParams: function (url, name, value) {
   var r = url;
   if (r != null && r != 'undefined' && r != "") {
    value = encodeURIComponent(value);
    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
    var tmp = name + "=" + value;
    if (url.match(reg) != null) {
     r = url.replace(eval(reg), tmp);
    }
    else {
     if (url.match("[\?]")) {
      r = url + "&" + tmp;
     } else {
      r = url + "?" + tmp;
     }
    }
   }
   return r;
  }
 
 });
})(jQuery);
</script>    
</html>
