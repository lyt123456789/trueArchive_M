var page = 1;//当前页数
var oldformJSON;
var neRouteType;
if(isFlexibleForm!=null && isFlexibleForm=='1'){			//弹性表单值
	var  params ={};
	params['userId'] = userId;
	params['workflowId'] = workflowId;
	params['nodeId'] = nodeId;
	params['instanceId'] = instanceId;
	params['processId'] = processId;
	params['formId'] = formId;
	params['toUserName'] = toUserName;
	params['title'] = title;
	params['status'] = status;
	if(isOver == '0'){
	}else{
		params['isOver'] = isOver;
	}
	$.ajax({
	    url: flexibleurl,
	    type: 'POST',
	    dataType: 'json',
	 	data : params,
	    timeout: 30000,
	    error: function(o,e){
	    },
	    success: function(data){
	        oldformJSON=data;
	        setFlexibleFormData(oldformJSON);
	    }
	});
}else{							//原先的操作方式
	if(isOver == '0'){
		var  params ={};
		params['userId'] = userId;
		params['workflowId'] = workflowId;
		params['nodeId'] = nodeId;
		params['instanceId'] = instanceId;
		params['processId'] = processId;
		params['formId'] = formId;
		params['oldFormId'] = oldFormId;
		params['dicValue'] = dicValue;
		params['modId'] = modId;
		params['dicId'] = dicId;
		params['matchId'] = matchId;
		params['isContinue'] = isContinue;
		params['origProcId'] = origProcId;
		params['toUserName'] = toUserName;
		params['title'] = title;
		params['status'] = status;
		$.ajax({
		    url: jsonurl,
		    type: 'POST',
		    dataType: 'json',
		 	data : params,
		    timeout: 30000,
		    error: function(o,e){
		    },
		    success: function(data){
		        oldformJSON=data;
		        setActiveData(oldformJSON);
		    }
		});
	}else{
		 setActiveData();
	}
}

//设置弹性表单展示参数值：引入SetTrueformData 方法：参数值：  true内容、 data(具体数据权限)、 pdf附件地址路径
function setFlexibleFormData(trueForm){
	var input = commentJson;
	OCXpdfobj.SetWebType(1);
	OCXpdfobj.SetDocId(instanceId);
	OCXpdfobj.SetSealUrl(sealUrl);
	OCXpdfobj.SetMethodUrl(methodurl);
	try {
		OCXpdfobj.SetWarterText(loginname, 1, 25);
	} catch (e) {
	}
	if (usbkey == 'yiyuan') {
		OCXpdfobj.SetStampType(0);
	} else {
		OCXpdfobj.SetStampType(1);
	}
	setTimeout(function() {
		OCXpdfobj.SetJsonData(input);
		var comments = personalComments.split(",");
		var coms = '';
		for ( var i = 0; i < comments.length; i++) {
			if (comments[i] == 'null') {
				coms += '{"text":""}' + ',';
			} else {
				coms += '{"text":"' + comments[i] + '"}' + ',';
			}
		}
		if (coms != '' && coms.length > 0) {
			coms = coms.substring(0, coms.length - 1);
		}
		var sPopUpData = '{"datas":[' + coms + '],"trueformdatas":[' + coms+ ']}';
		OCXpdfobj.SetPopUpData(sPopUpData);
		var sUserData = '{"userId":"' + userId + '","username":"'
				+ userName + '","workflowId":"' + workflowId
				+ '","nodeId":"' + nodeId + '","instanceId":"' + instanceId
				+ '","processId":"' + processId + '","formId":"' + formId
				+ '","sendtime":"2013-9-16 16:11:29"}';
		OCXpdfobj.SetUserInfo(sUserData);
		if(null != trueForm && '' != trueForm){
			OCXpdfobj.SetTrueformData(formPageJson, JSON2.stringify(trueForm), pdfurl);
		}else{
			OCXpdfobj.SetTrueformData(formPageJson, "", pdfurl);
		}
	}, 10);
}

/**
 * 输入true文件信息
 **/
function setActiveData(trueForm) {
	var input = commentJson;
	var version = getVersionByBbh();
	if (!version) {
		if (isCheck == '1') {
			return;
		}
		if (confirm("未安装控件，是否下载更新?(下载后关闭浏览器点击安装)")) {
			window.location.href = ctx+"/table_download.do";
			return;
		} else {
			window.close();
			history.go(-1);
			return;
		}
	}

	$.ajax({
		url : ctx+'/table_getVersion.do',
		type : 'POST',
		async : false,
		data : {
			'version' : version
		},
		error : function(o, e) {
			alert('加载form元素错误, error:' + e + '。');
		},
		success : function(data) {
			if (data == 'yes') {
				if (isCheck == '1') {
					return;
				}
				if (confirm("控件已过期，是否下载更新?(下载后关闭浏览器点击安装)")) {
					window.location.href = "${ctx}/table_download.do";
					return;
				} else {
					window.close();
					history.go(-1);
					return;
				}
			}
			OCXpdfobj.SetWebType(0);
			OCXpdfobj.SetDocId(instanceId);
			OCXpdfobj.SetSealUrl(sealUrl);
			OCXpdfobj.SetMethodUrl(methodurl);
			try {
				OCXpdfobj.SetWarterText(loginname, 1, 25);
			} catch (e) {
			}
			if (usbkey == 'yiyuan') {
				OCXpdfobj.SetStampType(0);
			} else {
				OCXpdfobj.SetStampType(1);
			}
			setTimeout(function() {
				OCXpdfobj.SetPDFUrl(pdfurl);
				OCXpdfobj.SetJsonData(input);
				OCXpdfobj.SetControlJson(JSON2.stringify(trueForm));
				var comments = personalComments.split(",");
				var coms = '';
				for ( var i = 0; i < comments.length; i++) {
					if (comments[i] == 'null') {
						coms += '{"text":""}' + ',';
					} else {
						coms += '{"text":"' + comments[i] + '"}' + ',';
					}
				}
				if (coms != '' && coms.length > 0) {
					coms = coms.substring(0, coms.length - 1);
				}
				var sPopUpData = '{"datas":[' + coms
						+ '],"trueformdatas":[' + coms + ']}';
				OCXpdfobj.SetPopUpData(sPopUpData);
				var sUserData = '{"userId":"' + userId + '","username":"'
						+ userName + '","workflowId":"' + workflowId
						+ '","nodeId":"' + nodeId + '","instanceId":"'
						+ instanceId + '","processId":"' + processId
						+ '","formId":"' + formId
						+ '","sendtime":"2013-9-16 16:11:29"}';
				OCXpdfobj.SetUserInfo(sUserData);
			}, 1000);
		}
	});
}

function getVersionByBbh() {
	return OCXpdfobj.GetVision();
}

function setDrawType(drawType, red, green, blue, width){
	showHandWrite(0);
	OCXpdfobj.SetDrawType(drawType, red, green, blue, width);
}

function showHandWrite(show){
	OCXpdfobj.ShowHandWrite(show);
}

//设置页面大小
function setPageSize(height, width){
	OCXpdfobj.RefreshView();
	$('#OCXpdfobj').width(width);
	$('#OCXpdfobj').height(height);
}

function setColor() {
 	var color = $('.popover-content button.tw-active').attr('data-value');
	if(color==1){	
		setDrawType(1,255,0,0,3);
	}else{	
		setDrawType(1,0,0,0,3);
	}
}

var curtHeight = 1448; 
var curtWidth = 1024;
//放大或缩小插件
function enlargeOrNarrowPage(type){
	var percent = 0.9;
	var boxHeight = $('.content_box').height()-4;
	if('enlarge' == type){
		curtHeight = curtHeight/percent;
		curtWidth = curtWidth/percent;
		if(curtHeight > 1448){
			curtHeight = 1448;
		}
		if(curtWidth > 1024){
			curtWidth = 1024;
		}
	}else if('narrow' == type){
		curtHeight = curtHeight*percent;
		curtWidth = curtWidth*percent;
		if(curtHeight < boxHeight){
			curtHeight = boxHeight;
			curtWidth = (1024/1448)*boxHeight;
		}
	}else if('enlargeOneTime' == type){
		curtHeight = 1448; 
		curtWidth = 1024;
	}else if('narrowOneTime' == type){
		curtHeight = boxHeight;
		curtWidth = (1024/1448)*boxHeight;
	}
	$('.content_box div').width(curtWidth);
	setPageSize(Math.round(curtHeight), Math.round(curtWidth));
}

$(function(){
	if($('.content_box div').width()<1020){
	    $('.quick_links_wrap').addClass('easy_wrap');
	}else{
	    $('.quick_links_wrap').removeClass('easy_wrap');
	}		
	
    var cd =  document.body.clientWidth/2-405-123;
    if(  document.getElementById('fjml')){
    	  document.getElementById('fjml').style.left=cd+"px";
    	  document.getElementById('fjml').style.top="50px";
    }
	
	$(".keep-open button").on('click',function(){
		if($('.keep-open .dropdown-menu').is(':hidden')){
			$('.keep-open .dropdown-menu').show();
		}else{
			$('.keep-open .dropdown-menu').hide();			
		}
	});
	$('.quick_links li').on('click',function(){
		$('.quick_links li').removeClass('active');
		$(this).addClass('active');
	});
	
	$('.licheng_wrap ul li a').on('click',function(){
	    $('.licheng_box').show().css('width','20%');
	    $('.contentWrap').css('width','80%');
	    $('.licheng_wrap').hide();
		var curtWidth = 875;
		var curtHeight = (1448/1024)*curtWidth;
	    $('.content_box div').width(curtWidth);
	    setPageSize(Math.round(curtHeight), Math.round(curtWidth));
	    
	    $('.timeline-items .timeline-item').each(function(index){
	        var $this = $(this);
	        var _height = $this.height()+10;
	    	var _time = (index+1)*1000;
	    	var _delayTime = index; 
	    	var _delayTime2 = index+0.4;
	    	var _delayTime3 = index+0.6;
	    	$this.find('.timeline-indicator').addClass('animated fadeInDown').attr('style','-webkit-animation-delay:'+_delayTime+'s; animation-delay:'+_delayTime+'s');
	    	$this.find('.timeline-info').addClass('animated fadeIn').attr('style','-webkit-animation-delay:'+_delayTime2+'s; animation-delay:'+_delayTime2+'s');
	    	$this.find('.widget-box').addClass('animated fadeIn').attr('style','-webkit-animation-delay:'+_delayTime3+'s; animation-delay:'+_delayTime3+'s');	
	    	setTimeout(function(){
	    		$this.find(".timeline-arrow").animate({height:_height});
	    	},_time);  
	    });	    
	});
	$('.licheng_close').on('click',function(){
	    $('.licheng_box').hide();
	    $('.contentWrap').css('width','100%');
	    $('.licheng_wrap').show();
	    enlargeOrNarrowPage('enlargeOneTime');
	    $(".timeline-arrow").height('0px');
	});
	$('.popover-content button').on('click',function(){
		$('.popover-content button').removeClass('tw-active');
		$(this).addClass('tw-active');
		var color = $(this).attr('data-value');
		if(color==1){
			setDrawType(1,255,0,0,3);
		}else{
			setDrawType(1,0,0,0,3);
		}
	});
});


$('.content_box div').bind('resize',function(){
	alert(1);
	if($('.content_box div').width()<1020){
	    $('.quick_links_wrap').addClass('easy_wrap');
	}else{
	    $('.quick_links_wrap').removeClass('easy_wrap');
	}  	    	
});

$(document).ready(function(){
	if(isFlexibleForm == '1'){
		$('#flexibleForm').css("display","none");
	}
	
	/*历程相关代码 begin*/
	$('.wf-course-btn').click(function() {
	    location.reload();
	    return
	});
	function animationDelay(obj,time){
		$(obj).attr('style','-webkit-animation-delay:'+time+'s')
	}
	var len = $('.wf-course-item').length;
	$('.wf-course-item').each(function(index){
		if(index === 0 ){
			$(this).addClass('wf-course-item-level-star');
		}
		if(index === len-1) {
			$(this).find('.wf-course-arrow').remove();
		}
		var $this = $(this);
		var _height = $this.height() -22;
		var _time = (index+1)*1100;
		var _delayTime = index; 
		var _delayTime2 = index+0.4;
		var _delayTime3 = index+0.6;
		var _delayTime4 = index+1;
		$this.find('.wf-course-num').addClass('animated fadeIn').attr('style','-webkit-animation-delay:'+_delayTime+'s; animation-delay:'+_delayTime+'s');
		$this.find('.wf-course-hd').addClass('animated fadeInDown').attr('style','-webkit-animation-delay:'+_delayTime2+'s; animation-delay:'+_delayTime2+'s');
		$this.find('.wf-course-bd').addClass('animated fadeInDown').attr('style','-webkit-animation-delay:'+_delayTime3+'s; animation-delay:'+_delayTime3+'s');
		$this.find('.wf-course-status').addClass('animated fadeIn').attr('style','-webkit-animation-delay:'+_delayTime4+'s; animation-delay:'+_delayTime4+'s');
		setTimeout(function(){
			$this.find(".wf-course-arrow").animate({height:_height});
		},_time);
	});
	/*历程相关代码 end*/
	
	var wHeight = $('.tw-pagedome').height()-48;
	$('.content_box,.licheng_box').css('height',wHeight);
	
	//初始化加载目录
	var html = "";
	var data = document.getElementById("filePages").value; 
	var jsonArr = eval("("+data+")");
	var page = 1;
	for(var i=0;i<jsonArr.length;i++){
		var jsonObj = jsonArr[i];
		html += "<li class=\"dropdown-header\"><a href=\"#\"  onclick=\"changePage('"+page+"')\" >"+jsonObj.name+"</a></li>";
		if(null != jsonObj){
			for(var j=1;j<=jsonObj.pageCount;j++){
				html += "<li><a href=\"#\" onclick=\"changePage('"+page+"')\" id=\"ahref"+page+"\" >第"+j+"页</a></li>";
				page++;
			}
		}
	}
	document.getElementById("bl_nav_zj").innerHTML=html;
	setTimeout(function(){
		$('.keep-open .dropdown-menu li').on('click',function(){
			$('.keep-open .dropdown-menu li').removeClass('active');
			$(this).addClass('active');
		});
		
		var commentJson = '${commentJson}';
		if(commentJson != ''){
			var page = pages.split(',');
//			var page = [0,1];
			if(page.length>0 && page[0]==0){
				for(var i=1;i<page.length;i++){
					//var pageNum = parseInt(page[i])+1;
					var pageNum = parseInt(page[i]);
					var html = $('.keep-open .dropdown-menu').find('#ahref'+pageNum).html();
					$('.keep-open .dropdown-menu').find('#ahref'+pageNum).html(html+'<span class="edit" style="font-weight:bold;font-size:25px; line-height:15px;display:inline-block;vertical-align: middle;" title="内容有改动"></span>');
				}
			}else{
				for(var i=0;i<page.length;i++){
					var pageNum = parseInt(page[i])+1;
					var html = $('.keep-open .dropdown-menu').find('#ahref'+pageNum).html();
					$('.keep-open .dropdown-menu').find('#ahref'+pageNum).html(html+'<span class="edit" style="font-weight:bold;font-size:25px; line-height:15px;display:inline-block;vertical-align: middle;" title="内容有改动"></span>');
				}
			}
	     }		
	},1000);
});

//快速定位到value页
var yys=1;
function changePage(value){
	$(this).addClass('active');
	if(value == 1){
		if(document.getElementById("leftPage")){
			document.getElementById("leftPage").className="b_disable";
		}
		if(document.getElementById("rightPage")){
			document.getElementById("rightPage").className="";
		}
	}else if(value == imageCount){
		if(document.getElementById("leftPage")){
			document.getElementById("leftPage").className="";
		}
		if(document.getElementById("rightPage")){
			document.getElementById("rightPage").className="b_disable";
		}
	}else{
		if(document.getElementById("leftPage")){
			document.getElementById("leftPage").className="";
		}
		if(document.getElementById("rightPage")){
			document.getElementById("rightPage").className="";
		}
	}
	ymChange(value);

	yys=value;
	if(yys != 1){
		$('#flexibleForm').css("display","");		
	}else{
		if('${isFlexibleForm}' == '1'){
			$('#flexibleForm').css("display","none");
		}
	}
}

//切换是否显示附件列表
var len1=1;
function  changeClass(){
	if(len1==0){
		len1=1;
		$('#fjml').removeClass("fouce");
		document.getElementById("fjml").style.display="none";
	    document.getElementById("mianDiv").style.height="0px"; 
	}else{
		$('.keep-open .dropdown-menu').hide();
		$('.content_box').animate({
			scrollTop: 0
		},200);
		len1=0;
		$('#fjml').addClass("fouce");
		document.getElementById("fjml").style.display="";
        document.getElementById("mianDiv").style.height="100px"; 
	}
};

//向左翻页
function changePageToLeft(){
	if(yys>1){
		var val =  parseInt(yys) - 1 ;
		ymChange(val);
		//document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
		//document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+val)).className="hot";
        //getScrollTop();
		if(val == 1){
			document.getElementById("leftPage").className="b_disable";
		}
		if(val < imageCount){
			document.getElementById("rightPage").className="";
		}
		yys=val;
		if(yys != 1){
			$('#flexibleForm').css("display","");
		}else{
			if(isFlexibleForm == '1'){
				$('#flexibleForm').css("display","none");
			}
		}
		
	}else{
		if(isFlexibleForm == '1'){
			$('#flexibleForm').css("display","none");
		}
	}
}

/**
 * 向右翻页
 */
function changePageToRight(){
	if(yys<imageCount){
		var val = parseInt(yys) + 1 ;
		ymChange(val);
		//document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+yys)).className="";
		//document.getElementById("mliframe").contentWindow.document.getElementById(("ahref"+val)).className="hot";
        //getScrollTop();
		if(val > 1){
			document.getElementById("leftPage").className="";
		}
		if(imageCount == val){
			document.getElementById("rightPage").className="b_disable";
		}
		yys=val;
		if(yys != 1){
			$('#flexibleForm').css("display","");		
		}else{
			if(isFlexibleForm == '1'){
				$('#flexibleForm').css("display","none");
			}
		}
	}
}

/**
 * 翻页方法
 * @param ym
 */
function ymChange(ym) {
	if(ym==1){
		//调用控件方法，获取高度
		myinterval();
	}else{
		if(page == 1){
			//设置成1024*1448
			SetPosition('1024px', '1448px');
			clearInterval(interval);
		}
	}
	page=ym;
	OCXpdfobj.GoToPage(ym);
	window.scroll(0, 0)
	if($('.licheng_box').is(':visible')){
		enlargeOrNarrowPage('narrowOneTime');		
	}
}

var interval; 
/**
 * 定时任务
 */
function myinterval(){
	interval = setInterval(function(){
		var nowHeight = OCXpdfobj.GetCurPageHeight();
		SetPosition('1024px', nowHeight+'px');
	},500);
}
if(isFlexibleForm=='1'){
	myinterval();
}

/**
 * 设置表单的高度和宽度
 * @param width
 * @param height
 */
function SetPosition(width, height) {
	var beforeHeight = document.getElementById("OCXpdfobj").style.height;
	if(beforeHeight != height){
		document.getElementById("OCXpdfobj").style.width = width;
		document.getElementById("OCXpdfobj").style.height = height;
	}
}


var noid_anid="";
var wfn_self_loop_all="" ;
function routeType(nextNodeId,type,relation,cType,isMergeChild,route_type,wfn_self_loop){
	if(null != wfn_self_loop && '1' == wfn_self_loop){
		wfn_self_loop_all='1';//自循环发送下一步
	}else{
		wfn_self_loop_all='0';
	}
	var trueJSON = getPageData();
	var json = JSON2.stringify(trueJSON.pdfjson);
	if(json == null ||json == "null" || json == 'null'){
		alert("表单未加载完成");
		return ;
	}
	var o = isbt;
	if(o){
		var error = isCheckBt(o.split(";"));
		if(error!=""){
			alert(error);
			return;
		}
	}
	var hsFlag = true;
	var isSend = 0;
	noid_anid=nextNodeId;
	//按钮禁止点击
	document.getElementById(noid_anid).disabled='disabled';
	//当必须上传附件时，检查附件是否为空
	var isUpload = false;
	if(isUploadAttach == '1'){
		$.ajax({
			url : ctx+'/attachment_isAttachExistByNode.do?instanceId=' + instanceId + '&nodeId='+ nodeId,
			type : 'POST',
			cache : false,
			async : false,
			error : function() {
				alert('AJAX调用错误(itemRelation_getIsDelaying.do)');
			},
			success : function(result) {
				if(result=='fail'){
					alert("请上传附件！");
					isUpload = true;
				}
			}
		});
		if(isUpload){
			if(document.getElementById(noid_anid)){
				document.getElementById(noid_anid).disabled='';
			}
			return;
		}
	}
	if(type=='node'){
		if(hsFlag){
			$.ajax({   
				url : 'table_isOnlyPerson.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_isOnlyPerson.do)');
					document.getElementById(noid_anid).disabled='';
				},
				data : {
					'workFlowId':workflowId,
					'nodeId':nodeId,
					'nextNodeId':noid_anid,
					'instanceId':instanceId,
					'processId':processId,
					'formId':formId
				},    
				success : function(person) {
					if(person != ''){			//发送时,默认选中的人员发送
						isSend = prepareForsend(person, noid_anid,route_type,isMergeChild);
					}else{
						if(null != toUserId && '' != toUserId){//督办自动发送给被督办人
							person="gdPerson="+toUserId+";";
							isSend = prepareForsend(person, noid_anid,route_type,isMergeChild);
						}else{//选择人员直接发送
							chooseForsend(noid_anid, workflowId, isMergeChild);
						}
					}
					if(isSend > 0){
						sendNext(wfn_self_loop_all);
					}else{
						/*if(obj.document.getElementById("jzk1")){
							obj.fsjs();
						}*/
					}
				}
			});
		}else{
			parent.closeTabsInLayer();
		}
	}else if(type=='childWf'){		//选择子流程，打开第一个子节点
		sendToChild(noid_anid);
	}else{
		parent.closeTabsInLayer();
	}
}

/**
 * 获取表单的数据
 * @returns {___anonymous14914_14988}
 */
function getPageData() {
	var tempJson = "";
	if(isFlexibleForm!=null && isFlexibleForm=='1'){
		tempJson = OCXpdfobj.GetFlexibleFormData();
	}else{
		tempJson = OCXpdfobj.GetControlJson();
	}
	var formjson = null;
	if (tempJson != '') {
		formjson = JSON2.parse(tempJson);
	}
	var newfeList = [];
	var newfeObj = {};
	if (formjson != null && formjson != 'null') {
		var feList = null;
		if(isFlexibleForm!=null && isFlexibleForm=='1'){
			feList = formjson.flexibleForm;
		}else{
			feList = formjson.trueform;
		}
		i = 0, l = feList.length;
		for (; i < l; i++) {
			var val = "";
			if (feList[i].type == 'checkbox') {
				var temp = feList[i].value;
				if (temp != '' && temp.length > 0) {
					val = temp.replace(new RegExp(";", "gm"), "^");
				}
				if (val == "undefined" || typeof (val) == "undefined") {
					val = "";
				}
				// 拆分 ^
				if (feList[i].actionurl != "") {
					var value = "";
					var vals = val.split("^");
					for ( var t = 0; t < vals.length; t++) {
						var tempVal = vals[t].split("*")[1];
						if (tempVal != "undefined") {
							if (t == 0) {
								value = tempVal;
							} else {
								value += "^" + tempVal;
							}
						} else {
							value = vals[t];
						}
					}
					newfeList.push(feList[i].name + '=' + encodeURI(value));
					newfeObj[feList[i].name] = value;
				} else {
					newfeList.push(feList[i].name + '=' + encodeURI(val));
					newfeObj[feList[i].name] = val;
				}

			} else if (feList[i].type == 'radio') {
				var temp = feList[i].value;
				//111*苏F·00001^222*fk11
				if (temp != '' && temp.length > 0) {
					val = temp.replace(new RegExp(";", "gm"), "^");
				}
				if (val == "undefined" || typeof (val) == "undefined") {
					val = "";
				}
				if (feList[i].actionurl != "") {
					var value = "";
					var vals = val.split("^");
					for ( var t = 0; t < vals.length; t++) {
						var tempVal = vals[t].split("*")[1];
						if (tempVal != "undefined") {
							if (t == 0) {
								value = tempVal;
							} else {
								value += "^" + tempVal;
							}
						} else {
							value = vals[t];
						}

					}
					newfeList.push(feList[i].name + '=' + encodeURI(value));
					newfeObj[feList[i].name] = value;
				} else {
					newfeList.push(feList[i].name + '=' + encodeURI(val));
					newfeObj[feList[i].name] = val;
				}
			} else {
				// text
				val = feList[i].value;
				if (val == "undefined" || typeof (val) == "undefined") {
					val = "";
				}
				newfeList.push(feList[i].name + '=' + encodeURI(val));
				newfeObj[feList[i].name] = val;
			}
		}
	}
	return {
		"pdfjson" : JSON2.parse(getActiveData()),
		"formjson" : newfeObj
	};
}


/**
 * 获取用户输入的信息
 **/
function getActiveData() {
	var d = new Date(), YMDHMS = d.getFullYear() + "-" + (d.getMonth() + 1)
			+ "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes()
			+ ":" + d.getSeconds();
	var trueJSON = OCXpdfobj.GetData();
	return trueJSON;
}

/**
 * 选择人员信息进行发送下一步的准备
 * @param noid_anid
 * @param workFlowId
 * @param isMergeChild
 */

var neRouteType="";
var neNodeId="";
function chooseForsend(noid_anid, workFlowId, isMergeChild){
$.ajax({   
	url : 'table_getWfLineOfType.do',
	type : 'POST',   
	cache : false,
	async : false,
	error : function() {  
		alert('AJAX调用错误(table_getWfLineOfType.do)');
	},
	data : {
		'nextNodeId':noid_anid,
		'nodeId':nodeId,
		'workFlowId':workflowId
	},    
	success : function(nodeInfo) { 
		var msg = nodeInfo.split(",")[0];
		var exchange = nodeInfo.split(",")[1];   //给发送下一步传所点击的按钮的属性值--下一步的节点值
		var url4UserChose=curl+'/ztree_showDepartmentTree.do?exchange='+exchange+'&click=true&nodeId='+noid_anid+'&send='+send+'&routType='+msg+'&t='+new Date();
		var url = url4UserChose;
		var WinWidth = 800;
		var WinHeight = 600;
		if(top.window && top.window.process && top.window.process.type){
			console.info("封装打开方式");
		    var ipc = top.window.nodeRequire('ipc');
		    var remote = top.window.nodeRequire('remote');
		    var browserwindow = remote.require('browser-window');
            var winProps = {};
            winProps.width = '800';
            winProps.height = '600';
            winProps['web-preferences'] = {'plugins':true};
            var focusedId = browserwindow.getFocusedWindow().id;
		    var win = new browserwindow(winProps);
			//console.info(focusedId);
	        win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
		    //win.openDevTools();
			win.on('closed',function(){
			    win = null;
			});				    
		    ipc.on('message-departmentTree',function(args){
				if(win){
	                win.close();
					win = null;
					var ret = args;
					 if(ret){
							/*if(obj.document.getElementById("jzk1")){
								obj.zzfs();
							}*/
							if(msg*1 == 2){
								var res = ret.split(";");
								document.getElementById('xtoName').value = res[0];
								document.getElementById('xccName').value = res[1];
							}else{
								document.getElementById('toName').value = ret;
							}
							
							//所选择的路由类型
							if(msg==null || msg =='' || msg=='null'){//重定向时
								neRouteType = 0;
							}else {
								neRouteType = msg;
							}
							var params ={};
							if(neRouteType==2){
								params['name']=document.getElementById('xtoName').value;
								params['cname']=document.getElementById('xccName').value;
								params['type'] = 2;
							}else{
								params['name']=document.getElementById('toName').value;
								params['type'] = 0;
							}
							$.ajax({   
								url : 'table_getUsersByIds.do',
								type : 'POST',   
								cache : false,
								async : false,
								error : function() {  
									alert('AJAX调用错误(table_getUsersByIds.do)');
								},
								data : params,  
								success : function(data) {  
									if(confirm(data+"？")){
										if(isMergeChild=='1'){
											sendPendToChild(workFlowId, noid_anid);
										}else{
											sendNext(wfn_self_loop_all);
										}
									}else{
										if(document.getElementById(noid_anid)){
											document.getElementById(noid_anid).disabled='';
										}
										/*if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}*/
										isSend = 0;
									}
								}
							});
						}else{
							if(document.getElementById(noid_anid)){
								document.getElementById(noid_anid).disabled='';
							}
						}
				}
		    });
		}else{
		    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
		    var loop = setInterval(function(){
			    if(winObj.closed){
					//console.info(window.returnValue);
				    clearInterval(loop);
				    //---------------------------
				    var ret = window.returnValue;
					 if(ret){
							/*if(obj.document.getElementById("jzk1")){
								obj.zzfs();
							}*/
							if(msg*1 == 2){
								var res = ret.split(";");
								document.getElementById('xtoName').value = res[0];
								document.getElementById('xccName').value = res[1];
							}else{
								document.getElementById('toName').value = ret;
							}
							//neNodeId = nextNodeId;
							//所选择的路由类型
							if(msg==null || msg =='' || msg=='null'){//重定向时
								neRouteType = 0;
							}else {
								neRouteType = msg;
							}
							var params ={};
							if(neRouteType==2){
								params['name']=document.getElementById('xtoName').value;
								params['cname']=document.getElementById('xccName').value;
								params['type'] = 2;
							}else{
								params['name']=document.getElementById('toName').value;
								params['type'] = 0;
							}
							$.ajax({   
								url : 'table_getUsersByIds.do',
								type : 'POST',   
								cache : false,
								async : false,
								error : function() {  
									alert('AJAX调用错误(table_getUsersByIds.do)');
								},
								data : params,  
								success : function(data) {  
									if(confirm(data+"？")){
										if(isMergeChild=='1'){
											sendPendToChild(workFlowId, noid_anid);
										}else{
											sendNext(wfn_self_loop_all);
										}
									}else{
										if(document.getElementById(noid_anid)){
											document.getElementById(noid_anid).disabled='';
										}
										/*if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}*/
										isSend = 0;
									}
								}
							});
						}else{
							if(document.getElementById(noid_anid)){
								document.getElementById(noid_anid).disabled='';
							}
						}
			    }
		    },500);
		}
		
	}
});
}


/**
解析节点类型、选中发送人员信息（默认人员）
*/
function prepareForsend(person, nextNodeId, route_type, isMergeChild){
	var isSend = 0;
	var persons  = person.split("=");
	if(persons[0] == 'more'){
		alert("该发送节点既设置了固定人员也设定了指定节点,因只能设置一种,请重新设置！");
	}else if(persons[0] == 'bxwqs'){			//并行完全式发送：区分主送、抄送
		var msg = persons[1];
		var res = msg.split(";");
		document.getElementById('xtoName').value = res[0];
		document.getElementById('xccName').value = res[1];
		neNodeId = nextNodeId;
		neRouteType = "2";
		isSend ++;
	}else {										//其他节点模式		
		var zdPerson = persons[1].split(";");
		var zdPersonXtoName = zdPerson[0];
		var zdPersonXccName = new Array();
		if(zdPerson[1] != ''){
			zdPersonXccName = zdPerson[1].split(",");
		}
		if(persons[0] == 'gdPerson'){	
			document.getElementById('toName').value = persons[1].substring(0,persons[1].length-1);
			//单人模式
			neNodeId = nextNodeId;
			neRouteType = route_type;
			//根据用户id 查询人员 name 
			$.ajax({   
				url : 'table_getUserById.do',
				type : 'POST',   
				cache : false,
				async : false,
				error : function() {  
					alert('AJAX调用错误(table_getUserById.do)');
				},
				data : {
					'userId':document.getElementById('toName').value
				},    
				success : function(emp) {  
					var choosePer = false;
					if(emp!=null && emp!=''){
						 var emp = JSON2.parse(emp);
						 var content = "";
						  for(var i=0; i<emp.length; i++){
							 content += emp[i]['departmentName'] + " " +emp[i]['employeeName'];
							 if(i!=emp.length-1){
								 content += ",";
							 }
						 }
						if(confirm("确定要发送给 "+ emp[0]['departmentName'] + " " +emp[0]['employeeName']+" 吗？")){
							sendNext(wfn_self_loop_all);
						}else{
							choosePer = true;
						}
					}else{		//弹出选择人员月面
						choosePer = true;
					}
					if(choosePer){			//取消默认选中人员
						var url4UserChose=curl+'/ztree_showDepartmentTree.do?click=true&nodeId='+noid_anid+'&send=${send}&routType=0&t='+new Date();
						var url = url4UserChose;
						var WinWidth = 800;
						var WinHeight = 600;
						if(top.window && top.window.process && top.window.process.type){
							console.info("封装打开方式："+url);
						    var ipc = top.window.nodeRequire('ipc');
						    var remote = top.window.nodeRequire('remote');
						    var browserwindow = remote.require('browser-window');
	                        var focusedWindow = browserwindow.getFocusedWindow();
	                        var focusedId = focusedWindow.id;
						    var win = new browserwindow({width:WinWidth,height:WinHeight});
		                    win.loadUrl($.UrlUpdateParams(url,'focusedId',focusedId));
						    //win.openDevTools();
						    ipc.on('xdfdfdf',function(args){
								if(win){
					                win.close();
									win = null;
									var ret = args;
							         if(ret){
											var res = ret.split(";");
											document.getElementById('toName').value = ret;
											ret = null;
											var params ={};
											params['name']=document.getElementById('toName').value;
											params['type'] = 0;
											$.ajax({   
												url : 'table_getUsersByIds.do',
												type : 'POST',   
												cache : false,
												async : false,
												error : function() {  
													alert('AJAX调用错误(table_getUsersByIds.do)');
												},
												data : params,  
												success : function(data) {  
													if(confirm(data+"？")){
														sendNext(wfn_self_loop_all);
													}else{
														if(document.getElementById(noid_anid)){
															document.getElementById(noid_anid).disabled='';
														}
														if(obj.document.getElementById("jzk1")){
															obj.fsjs();
														}
													}
												}
											});
										}else{
											if(document.getElementById(noid_anid)){
												document.getElementById(noid_anid).disabled='';
											}
										}
							      	//----------------------------
								}
						    });
						}else{
							console.info("window.open普通打开方式");
						    var winObj = window.open(url, "", "scrollbars=yes,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,width=" + WinWidth + ",height=" + WinHeight + ",left=" + (window.screen.width - WinWidth) / 2 + ",top=" + (window.screen.height - WinHeight - 100) / 2);
						    var loop = setInterval(function(){
							    if(winObj.closed){
									//console.info(window.returnValue);
								    clearInterval(loop);
								    //---------------------------
								    var ret = window.returnValue;
							         if(ret){
											var res = ret.split(";");
											document.getElementById('toName').value = ret;
											ret = null;
											var params ={};
											params['name']=document.getElementById('toName').value;
											params['type'] = 0;
											$.ajax({   
												url : 'table_getUsersByIds.do',
												type : 'POST',   
												cache : false,
												async : false,
												error : function() {  
													alert('AJAX调用错误(table_getUsersByIds.do)');
												},
												data : params,  
												success : function(data) {  
													if(confirm(data+"？")){
														sendNext(wfn_self_loop_all);
													}else{
														if(document.getElementById(noid_anid)){
															document.getElementById(noid_anid).disabled='';
														}
														if(obj.document.getElementById("jzk1")){
															obj.fsjs();
														}
													}
												}
											});
										}else{
											if(document.getElementById(noid_anid)){
												document.getElementById(noid_anid).disabled='';
											}
										}
							        //------------------------------
							    }
						    },500);
						}
					}
				}
			});
		}else if(persons[0] == 'zdPerson'){		//自动追溯功能
			if(zdPersonXtoName != '' && zdPersonXccName.length == 0){//指定节点只有一个人，且为主送
				document.getElementById('toName').value = zdPersonXtoName;
				neNodeId = nextNodeId;
				//单人模式
				neRouteType = 0;
				isSend ++;
				//sendNext();
			}else if(zdPersonXtoName == '' && zdPersonXccName.length == 1){//指定节点只有一个人，但是为抄送
				document.getElementById('toName').value = zdPersonXccName;
				neNodeId = nextNodeId;
				//单人模式
				neRouteType = 0;
				isSend ++;
			} else{
				$.ajax({   
					url : 'table_getWfLineOfType.do',
					type : 'POST',   
					cache : false,
					async : false,
					error : function() {  
						alert('AJAX调用错误(table_getWfLineOfType.do)');
					},
					data : {
						'nextNodeId':nextNodeId,
						'nodeId':'${nodeId}',
						'workFlowId':workflowId
					},    
					success : function(nodeInfo) {  
						var msg = nodeInfo.split(",")[0];
						//指定人员大于1，有主送，有抄送
						if(zdPersonXtoName != '' && zdPersonXccName.length > 0){
							if(msg*1 == 2){
								document.getElementById('xtoName').value = persons[1].split(";")[0];
								document.getElementById('xccName').value = persons[1].split(";")[1];
							}else{
								document.getElementById('toName').value = persons[1].split(";")[1];
							}
							neNodeId = nextNodeId;
							neRouteType = msg;
							
							var params ={};
							if(neRouteType==2){
								params['name']=document.getElementById('xtoName').value;
								params['cname']=document.getElementById('xccName').value;
								params['type'] = 2;
							}else{
								params['name']=document.getElementById('toName').value;
								params['type'] = 0;
							}
							$.ajax({   
								url : 'table_getUsersByIds.do',
								type : 'POST',   
								cache : false,
								async : false,
								error : function() {  
									alert('AJAX调用错误(table_getUsersByIds.do)');
								},
								data : params,  
								success : function(data) {  
									if(confirm(data+"？")){
										sendNext(wfn_self_loop_all);
									}else{
										if(document.getElementById(noid_anid)){
											document.getElementById(noid_anid).disabled='';
										}
										if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}
									}
								}
							});
						}else{
							//给发送下一步传所点击的按钮的属性值--下一步的节点值
							var url4UserChose=ctx+'/ztree_showDepartmentTree.do?click=true&nodeId='+noid_anid+'&send=${send}&routType='+msg+'&t='+new Date();
							ret=window.showModalDialog(url4UserChose,null,"dialogWidth:800px;dialogHeight:500px;help:no;status:no");
							if(ret){
								if(msg*1 == 2){
									var res = ret.split(";");
									document.getElementById('xtoName').value = res[0];
									document.getElementById('xccName').value = res[1];
								}else{
									document.getElementById('toName').value = ret;
								}
								neNodeId = noid_anid;
								//所选择的路由类型
								if(msg==null || msg =='' || msg=='null'){//重定向时
									neRouteType = 0;
								}else {
									neRouteType = msg;
								}
								// 异步调用 根据id 获取人员
								//neRouteType 主送抄送的值
								var params ={};
								if(neRouteType==2){
									params['name']=document.getElementById('xtoName').value;
									params['cname']=document.getElementById('xccName').value;
									params['type'] = 2;
								}else{
									params['name']=document.getElementById('toName').value;
									params['type'] = 0;
								}
								
								$.ajax({   
									url : 'table_getUsersByIds.do',
									type : 'POST',   
									cache : false,
									async : false,
									error : function() {  
										alert('AJAX调用错误(table_getUsersByIds.do)');
									},
									data : params,  
									success : function(data) {  
										if(confirm(data+"？")){
											sendNext(wfn_self_loop_all);
										}else{
											isSend = 0;
											if(document.getElementById(noid_anid)){
												document.getElementById(noid_anid).disabled='';
											}
											if(obj.document.getElementById("jzk1")){
												obj.fsjs();
											}
										}
									}
								});
							}else{
								if(document.getElementById(noid_anid)){
									document.getElementById(noid_anid).disabled='';
								}
							}
						}
					}
				});
			}
		}
	}
	return isSend;
}

//发文中发送-先保存表单再发送
function send(){
	if(isopened){
		alert("已有别人提交请勿办理");
		return;
	}
	//检测是否已经上传附件
	var isSed = true;
	var syntoNotice = 0 ;//是否短信提醒
	if(document.getElementById("syntoNotice")){
		if(document.getElementById("syntoNotice").checked){
			syntoNotice=1;
		} 
	}
	$.ajax({
		url : '${ctx}/attachment_getAttachmentCount.do',
		type : 'POST',  
		cache : false,
		async : false,
		data : {
			"instanceId":instanceId				
		},
		error : function() {
			alert('AJAX调用错误(attachment_getAttachmentCount.do)');
		},
		success : function(msg) {  
			if(msg!=null && msg=='success'){
				isSed = true;
			}else{
				if(confirm("未上传附件或上传文件中无可预览文件,是否继续操作？")){
					
				}else{
					isSed = false;
				}
			}
		}
	});
	if(!isSed){
		return;
	}
	if(isSed){
		/*if(obj.document.getElementById("jzk1")){
			obj.zzfs();
		}*/
		var trueJSON = getPageData();
		json = JSON2.stringify(trueJSON.pdfjson);
		formjson = trueJSON.formjson;
		var tagValue = getProValue();
		var pageValue = tagValue;
		if(formjson != ''){
			pageValue = mergeJson(tagValue,formjson);
		}
				var params = pageValue;
				// from  url 
				params['instanceId'] = instanceId;
				params['workFlowId'] = workflowId;
				params['processId'] = processId;
				params['nodeId'] = nodeId;
				params['finstanceId'] =finstanceId;
				params['formId'] = formId; 
				// form data 
				params['json'] = json; 
				params['oldProcessId'] = oldProcessId; 
				params['isWriteNewValue'] = isWriteNewValue; 
				params['itemId'] = itemId;
				
				$.ajax({
					url : 'table_end.do',
					//url : 'table_end.do?instanceId='+instanceId+'&workFlowId='+workFlowId+'&processId=${processId}&nodeId=${nodeId}&finstanceId=${finstanceId}&formId='+formId,
					type : 'POST',  
					cache : false,
					async : false,
					data : params,
					//data : 'json='+ json +'&oldProcessId=${oldProcessId}&isWriteNewValue=${isWriteNewValue}'+tagValue,
					error : function() {
						/*if(obj.document.getElementById("jzk1")){
							obj.fsjs();
						}*/
						alert('AJAX调用错误(table_end.do)');
					},
					success : function(msg) {  
						var params = pageValue;
						// form data 
						params['instanceId'] = instanceId;
						params['workFlowId'] = workflowId;
						params['processId'] = processId;
						params['nodeId'] = nodeId;
						params['formId'] = formId; 
						params['syntoNotice'] = syntoNotice;
						if(msg != ''){
							$.ajax({  
								url : 'table_sendDoc.do',
								type : 'POST',   
								cache : false,
								async : false,
								//	data : 'instanceId='+instanceId+'&workFlowId='+workFlowId+'&processId=${processId}&nodeId=${nodeId}&formId='+formId+tagValue,
								data : params,
								error : function() {  
									/*if(obj.document.getElementById("jzk1")){
										obj.fsjs();
									}*/
									alert('AJAX调用错误(table_sendDoc.do)');
								},
								success : function(msg) {
									var res = eval("("+msg+")");
									if(res.result =='1'){							//未选择部门时,直接办结办件,但是徐要给出提醒
										alert("未选择主送或者抄送部门,流程办结！");
										/*if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}*/
										alert("发送成功！");
										if(isFirst == 'true'){
											parent.closeTabsInLayer();
										}else{
											parent.closeTabsInLayer();
											if(isPortal=='1'){
												parent.closeTabsInLayer();
											}
										}
									}else if(res.result =='2'){				//异常失败
										alert("发送异常！");
										/*if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}*/
									}else if(res.result =='0'){				//发送成功了
										/*if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}*/
										alert("发送成功！");
										if(isFirst == 'true'){
											parent.closeTabsInLayer();
										}else{
											parent.closeTabsInLayer();
											if(isPortal=='1'){
												parent.closeTabsInLayer();
											}
										}
									}
								}
							});
						}else{
							/*if(obj.document.getElementById("jzk1")){
								obj.fsjs();
							}*/
							alert("发送失败，请联系管理员！");
							return false;
						}
					}
				});
	}
}

function sendFile(){
	//获取部门 树
	var trueJSON = getPageData();
	json = JSON2.stringify(trueJSON.pdfjson);
	formjson = trueJSON.formjson;
	var tagValue = getProValue();
	var pageValue = tagValue;
	if(formjson != ''){
		pageValue = mergeJson(tagValue,formjson);
	}
	ret=window.showModalDialog('${ctx}/selectTree_showDepartment.do?isSend=1&deptId=${deptId}&t='+new Date(),null,"dialogWidth:980px;dialogHeight:500px;help:no;status:no");
	// ret  为 id 加  name id*name
	if(ret){
		var res = ret.split("*");
		document.getElementById('xtoName').value = res[0];
		var params = pageValue;
		var ids = res[0].split(",");
		// ids 的个数等于1  直接设置为回复人 , 超过1 则弹出框 选择 回复人
		var isend = false;
			params['rebacker'] = res[0];
			params['dffss'] = res[2];
			// from  url 
			params['instanceId'] = instanceId;
			params['workFlowId'] = workflowId;
			params['processId'] = processId;
			params['nodeId'] = nodeId;
			params['formId'] = formId; 
			params['stepIndex'] = stepIndex;
			params['xtoName'] = document.getElementById('xtoName').value;
			params['xccName'] = document.getElementById('xccName').value;
			// form data 
			params['json'] = json; 
			params['itemId'] = itemId;
			$.ajax({  
				url : 'table_doFileDoc.do',
				type : 'POST',   
				cache : false,
				async : false,
				data : params,
				error : function() {  
					alert('AJAX调用错误(table_doFileDoc.do)');
				},
				success : function(msg) {
					alert("发送成功！");
					parent.closeTabsInLayer();
				}
			});
	}else{
		if(document.getElementById(noid_anid)){
			document.getElementById(noid_anid).disabled='';
		}
	}
}

//办件发送下一步
function sendNext(self_loop){
	//将盖完章后的文件上传到服务器，是否盖章，是否上传，都是有控件端判断
	//upFileToServer();
	
	var closeframe= $("#closeframe").val();
	//意见json-----start--------
	var json = '';
	var formjson = ''; //表单元素
	var isHaveFormjson = ''; 
	var trueJSON = getPageData();
	json = JSON2.stringify(trueJSON.pdfjson);
	formjson = trueJSON.formjson;
	isHaveFormjson = 'true';
	//意见json------end--------
	 var isSmsRemind = 0 ;//是否短信提醒
	/*if(document.getElementById("isSmsRemind").checked){
		isSmsRemind=1;
	} */
	var nextNodeId = noid_anid ;
	var xtoName = "";
	var xccName = "";
	if(neRouteType==0){
		xtoName = document.getElementById("toName").value;
	}else if(neRouteType ==1){
		xtoName = document.getElementById("toName").value;
	}else if (neRouteType == 2){
		//==============判断主送抄送====================
		xtoName = document.getElementById("xtoName").value;
		xccName = document.getElementById("xccName").value;
		//====================================//		
	}else if(neRouteType == 3){
		xtoName = document.getElementById("toName").value;
	}else if(neRouteType == 4 || neRouteType == 5 || neRouteType == 6){
		xtoName = document.getElementById("toName").value;
	}
	var tagValue = getProValue();
	var pageValue = tagValue;
	if(formjson != ''){
		pageValue = mergeJson(tagValue,formjson);
	}
	var yqinstanceid = $("#yqinstanceid").val();
	pageValue['yqinstanceid'] = yqinstanceid;
	//----------表单非空校验方法-----------
	var isPassCheck = 'true';
	if (typeof sendNextOfCheckForm != 'undefined' ) {
	    isPassCheck = sendNextOfCheckForm();
	}
	var wcsx=$('#szsx').val();
	if(!wcsx){
		wcsx="";
	}
	if(isPassCheck){
		if(isFirst == 'true'){
			var title = title_column;
			var titles = title.split(",");
			var isHaveTitle = "";
			for(var i=0;i < titles.length;i++){
				var filedValue = pageValue[titles[i]];
				if(filedValue !=""){
					isHaveTitle +=	filedValue;
				}
			}
		}else{
			isHaveTitle = "ishave"; //如果不是第一步，就不用判断
		}
		//检查办件是否处于暂停中
		$.ajax({
			url : ctx+'/itemRelation_getIsDelaying.do',
			type : 'POST',
			cache : false,
			async : false,
			data : {
				'instanceId':instanceId
			},
			error : function() {
				alert('AJAX调用错误(itemRelation_getIsDelaying.do)');
			},
			success : function(msg) {
				if(msg=='1'){
					alert("待办处于延期过程中,请等待!");
					return false;
				}
			}
		});

		if(isHaveTitle != null && isHaveTitle != ""){
			$.ajax({
				url : ctx+'/table_modelIsOrNot.do',
				type : 'POST',
				cache : false,
				async : false,
				data : {
					'workFlowId':workflowId,
					'nodeId':nodeId,
					'nextNodeId':noid_anid,
					'xtoName':xtoName,
					'xccName':xccName
				},
				error : function() {
					alert('AJAX调用错误(table_modelIsOrNot.do)');
				},
				success : function(msg) {
					if(msg == 'no'){
						alert("所选择的发送人的个数，和路由类型不一致！");
						return false;
					}else if(msg == 'yes'){
						/*if(obj.document.getElementById("jzk1")){
							obj.zzfs();
						}else{
							addLoadCover();
						}*/
						var no = no;
						var params = pageValue;
						
						//from  url
						params['wcsx'] = wcsx;
						params['isDb'] = isDb;
						params['isHaveFormjson'] = isHaveFormjson;
						params['itemId'] = itemId;
						params['xtoName'] = xtoName;
						params['xccName'] = xccName;
						params['instanceId'] = instanceId;
						params['formId'] = formId;
						params['nodeId'] = nodeId;
						params['nextNodeId'] = nextNodeId;
						params['workFlowId'] = workflowId;
						params['processId'] = processId;
						params['att_comment_id'] = att_comment_id;
						// from data 
						params['json'] = json;
						params['oldFormId'] = oldFormId;
						params['isSmsRemind'] = isSmsRemind;
						params['isFirst'] = isFirst;
						params['isCy'] = isCy;
						params['firstStep'] = firstStep;
						params['isChildWf'] = isChildWf;
						params['finstanceId'] = finstanceId;
						params['cType'] = cType;
						params['relation'] = relation;
						params['f_proceId'] = f_proceId;
						params['closeframe'] = closeframe;
						params['self_loop'] = self_loop;
						params['no'] = no;
						params['modId'] = modId;
						params['matchId'] = matchId;
						params['dicId'] = dicId;
						params['dicValue'] = dicValue;
							setTimeout(function(){
								var sendnextURL = ctx+'/table_sendNext.do';
								$.ajax({  
									url : sendnextURL,
									type : 'POST',   
									cache : false,
									async : false,
									data : params,
									error : function() {  
										/*if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}*/
										//alert('AJAX调用错误(table_sendNext.do)');
										closeCover();
									},
									success : function(msg) {
										/*if(obj.document.getElementById("jzk1")){
											obj.fsjs();
										}*/
										if(msg == 'over'){
											alert("该待办已经被办理,请重新刷新待办列表页面！");
											return false;
										}else if(msg == 'no'){
											alert("发送下一步有误，请联系管理员！");
											return false;
										}else if(msg == 'yes'){
											if(closeframe=='0'){
												alert("发送成功！");
												//如果是续办办件，更新原办件状态
												if(isFirst == 'true' && origProcId != null && origProcId != ''){
													updateOrigProc(instanceId,origProcId);
												}
												if(directSend == 'true'){
													window.close();
													window.returnValue='choseCurrentprocessId='+processId+'&instanceId='+instanceId;
												}else{
													if(isChildWf == 'true'){	//子流程
														window.close();
														window.returnValue='choseparent';
													}else{
														var itemid= itemId;
														if((isFirst == 'true' || isCy == 'true' || stepIndex=='1') && cType != '1'){
															parent.closeTabsInLayer();
														}else{
															parent.closeTabsInLayer();
															if(isPortal=='1'){
																parent.closeTabsInLayer();
															}
														}
													}
												}
												
											}else if(closeframe==1){
												goExChangeSendValue();
											}
										}
									}
								});	
						},1000);
					}
				}
			});
		}else {  
			/*if(obj.document.getElementById("jzk1")){
				obj.fsjs();
			}*/
			alert("流程标题所对应的存储字段不能为空!");  
			if(document.getElementById(noid_anid)){
				document.getElementById(noid_anid).disabled='';
			}
		}
	}
}

/**
 * 办件办结
 * @param bjid
 * @returns {Boolean}
 */
function end(bjid){
	if(isopened){
		alert("已有别人提交请勿办理");
		return;
	}
	noid_anid=bjid;
	if(bjid!=null && bjid=='end_auto'){			//父流程自动办结
		noid_anid="end";
	}
	if(document.getElementById(noid_anid)){
		document.getElementById(noid_anid).disabled='disabled';
	}
	var nodeNameNew = "";
	if(childWfAfterNode == 'true'){
		nodeNameNew = '发送下一步';
	}else{
		nodeNameNew = '办结';
	}
	if(isEndReply == 'true'){
		nodeNameNew = '回复';
	}
	if(confirm("确定要"+nodeNameNew+"吗？")){
		var trueJSON = getPageData();
		json = JSON2.stringify(trueJSON.pdfjson);
		if(json == null ||json == "null" || json == 'null'){
			alert("表单未加载完成");
			return ;
		}
		formjson = trueJSON.formjson;
		var o = '${isbt}';
		if(o){
			var error =isCheckBt(o.split(";"));
			if(error!=""){
				alert(error);
				if(document.getElementById(noid_anid)){
					document.getElementById(noid_anid).disabled='';
				}
				return;
			}
		}
		
		var ismeeting =trueJSON.formjson.ismeeting;
		if(ismeeting == 'ismeeting'){
			var b=checkmeetingtime(trueJSON);
			if(b=='1'){
				document.getElementById(noid_anid).disabled='';
				return false;
			}
		}
		var tagValue = getProValue();
		var pageValue =tagValue;
		if(formjson != ''){
			pageValue = mergeJson(tagValue,formjson);
		}
		/*if(obj.document.getElementById("jzk1")){
			obj.zzfs();
		}*/
		var params = pageValue;
		// from  url 
		params['itemId'] = itemId;
		params['instanceId'] = instanceId;
		params['workFlowId'] = workflowId;
		params['processId'] = processId;
		params['nodeId'] = nodeId;
		params['finstanceId'] = finstanceId;
		params['formId'] = formId; 
		// form data 
		params['json'] = json; 
		params['oldProcessId'] = oldProcessId; 
		params['isWriteNewValue'] = isWriteNewValue; 
		$.ajax({  
			url : 'table_end.do',
			type : 'POST',   
			cache : false,
			async : false,
			error : function() {  
				/*if(obj.document.getElementById("jzk1")){
					obj.fsjs();
				}*/
				if(document.getElementById(noid_anid)){
					document.getElementById(noid_anid).disabled='';
				}
				//alert('AJAX调用错误(table_end.do)');
			},
			data : params,
			success : function(msg) {
				if(msg == 'no'){
					alert("办结有误，请联系管理员！");
					if(document.getElementById(noid_anid)){
						document.getElementById(noid_anid).disabled='';
					}
					return false;
				}else if(msg == 'yes'){
					/*if(obj.document.getElementById("jzk1")){
						obj.fsjs();
					}*/
					if(document.getElementById(noid_anid)){
						document.getElementById(noid_anid).disabled='';
					}
					
					if(childWfAfterNode != 'true'){
						if(isEndReply == 'true'){
							alert("回复成功！");
						}else{
							alert("完成办结！");
						}
						
					}else{
						alert("发送成功！");
					}
					if(ismeeting == 'ismeeting'){
						parent.closeTabsInLayer();
					}else{
						if(isFirst == 'true' && finstanceId==''){
							parent.closeTabsInLayer();
						}else{
							parent.closeTabsInLayer();
						}
					}
				}
			}
		});
	}else{
		if(bjid!=null && bjid=='end_auto'){		//表示自动办结:直接办结接口
			parent.closeTabsInLayer();
		}
		if(document.getElementById(noid_anid)){
			document.getElementById(noid_anid).disabled='';
		}
	}
}

//保存当前节点表单
function operateForm(operate){
	//将盖完章后的文件上传到服务器，是否盖章，是否上传，都是有控件端判断
	//upFileToServer();
	
	if(isopened){
		alert("已有别人提交请勿办理");
		return;
	}
	//意见json-----start--------
	var json = '';
	var formjson = ''; //表单元素
	var isHaveFormjson = ''; 
	//修改的到子流程的修改json不保存
	var trueJSON = getPageData();
	json = JSON2.stringify(trueJSON.pdfjson);
	formjson = trueJSON.formjson;
	isHaveFormjson = 'true';
	//意见json------end--------	
	var tagValue = getProValue();//得到属性值
	var pageValue =tagValue;
	if(formjson != ''){
		// 合并 json 对象
		pageValue = mergeJson(tagValue,formjson);
	}
	if(isFirst == 'true'){
		var title = title_column;
		var titles = title.split(",");
		var isHaveTitle = "";
		for(var i=0;i < titles.length;i++){
					var filedValue = pageValue[titles[i]];
					if(filedValue !="" &&  filedValue != undefined ){
						isHaveTitle +=	filedValue;
					}else if( filedValue == undefined ){
						isHaveTitle = "ishave";
						break;
					}
		}
	}else{
		isHaveTitle = "ishave"; //如果不是第一步，就不用判断
	}
	var params  = pageValue;
	// from url
	params['instanceId'] = instanceId;
	params['isHaveFormjson'] = isHaveFormjson;
	params['formId'] = formId;
	params['oldFormId'] = oldFormId;
	params['itemId'] = itemId;
	params['nodeId'] = nodeId;
	params['workFlowId'] = workflowId;
	params['processId'] = processId;
	params['operate'] = operate;
	//from data
	params['isFirst'] = isFirst;
	params['json'] = json;
	params['cType'] = cType;
	params['no'] = no;
	if(isHaveTitle != null && isHaveTitle !="" && isHaveTitle != 'undefined'){
		$.ajax({
			url : ctx+'/table_onlySave.do',
			type : 'POST',  
			cache : false,
			async : false,
			data : params,
			error : function() {
				//alert('AJAX调用错误(table_onlySave.do)');
			},
			success : function(msg) {   
				if(msg != ''){
					if(operate==1){
						alert("完成办理！");
						if(isFirst == 'true' || isCy == 'true'){
							parent.closeTabsInLayer();
						}else{
							parent.closeTabsInLayer();
						}
					}else {
						alert("保存成功！");
					}
					//如果是续办办件，更新原办件状态
					if(isFirst == 'true' && origProcId != null && origProcId != ''){
						updateOrigProc(instanceId,origProcId);
					}
				}else{
					if(operate==1){
						alert("完成办理有误,请联系管理员！");
					}else {
						alert("保存有误,请联系管理员！");
					}
					return false;
				}
			}
		});
	}else {
		/*if(obj.document.getElementById("jzk1")){
			obj.fsjs();
		}*/
		alert("流程标题不能为空!");
	}
}

function mergeJson(a,b){
	// a tagvalue
	// b formjson
	// 将a 的内容 合并到 a 里面	
	
	for(var c in a){
		if(b[c] == ''){
			b[c] = a[c];
		}
		
	}
	return b;
}

//获取属性和属性值--------！！！！！！！！！-pdf版时，列表类型有待修改-！！！！！！！！！----
function getProValue(){
	var allValue = value;
	var tagValue= "";
	var tags ={};
	if(!!tagHaveName){
		var tagHaveNames= new Array(); //定义一数组
		tagHaveNames = tagHaveName.split(","); //格式：jl_gzsj,jl_gzdd,jl_zw,jl_xz,ry_xm,ry_nl,ry_sr
		for(var j=0;j<=tagHaveNames.length-1;j++){   
			if(document.getElementsByName(tagHaveNames[j])){
				var tagHaves = new Array();
				tagHaves = document.getElementsByName(tagHaveNames[j]);//所有的属性
				//tagHaveValue += "&"+ tagHaveNames[j]+"=";  //格式：jl_gzsj=a,b,c&jl_gzdd=d,f,g
				var tagHaveValue= "";
				for(var k=0;k<tagHaves.length;k++){
					var ttval = tagHaves[k].value;
					if(ttval=="" || ttval==null){
						ttval = "null";
					}
					if(tagHaves.length > 1){ //多列
						if(tagHaveNames[j] == tagHaves[k]){
							tagHaveValue += ttval+";";
						}else{
							tagHaveValue += ttval+";";
						}
					}else{//单列
						tagHaveValue += ttval+";";  //拼起来，一个列对应多个值
					}
				}
				tags[tagHaveNames[j]] = tagHaveValue.substring(0,tagHaveValue.length-1);
			}
		}  
	}
	
	if(tagName){
			var tagNames = new Array(); //定义一数组
			tagNames = tagName.split(",");
			var foval = "";
			if(allValue != ''){
				allValue = allValue.substring(0,allValue.length-1);
			}
			// 拆分成对象
			var allValues = allValue.split(";");
			var valObj = {};
			if(allValue != ''){
				for(var i = 0; i < allValues.length; i++){
					if(allValues[i] != 'undefined' && typeof(allValues[i]) != 'undefined'){
						var tempKeyValue = allValues[i].split(":");
						if(tempKeyValue.length > 1){
							if(typeof(valObj[tempKeyValue[0].toLowerCase()]) == 'undefined' || valObj[tempKeyValue[0].toLowerCase()] == 'undefined' || valObj[tempKeyValue[0].toLowerCase()] == ''){
								valObj[tempKeyValue[0].toLowerCase()] = tempKeyValue[1];
							}
							
						}else{
							if(typeof(valObj[tempKeyValue[0].toLowerCase()]) == 'undefined' || valObj[tempKeyValue[0].toLowerCase()] == 'undefined' || valObj[tempKeyValue[0].toLowerCase()] == ''){
								valObj[tempKeyValue[0].toLowerCase()] = "";
							}
						}
					}
				}
			}
			
			for(var i=0;i <= tagNames.length-1;i++){   
				if(document.getElementById(tagNames[i])){
					foval = eval(document.getElementById(tagNames[i])).value ;
					tagValue += "&"+tagNames[i]+"="+encodeURI(foval);  
					tags[tagNames[i]] = foval;
				}else{
					//pdf版已经没有表单，getElementById读取不到值
					tagValue += "&"+tagNames[i]+"="+encodeURI(valObj[tagNames[i].toLowerCase()]);
					tags[tagNames[i]] = valObj[tagNames[i].toLowerCase()];
					
				}
			}  
		}
		if(specialTagName){
			var specialTagNames = new Array(); //定义一数组
			specialTagNames = specialTagName.split("##");
			for(var m=0;m <= specialTagNames.length-1;m++){
				var arr = document.getElementsByName(specialTagNames[m]);
				var val = "";
				if(!!arr){
					for(var k=0;k < arr.length;k++){
						if(arr[k].checked){
							val += arr[k].value+"^";//用不常用的符号("^")连接checkbox的值,如果有冲突，需修改
						}
					}
				}
				if(val != '' && val.length > 0){
					tagValue += "&"+specialTagNames[m]+"="+encodeURI(val.substring(0,val.length-1));
					tags[specialTagNames[m]] =val.substring(0,val.length-1);
				}
			}  
	}
	return tags;
}

function isCheckBt(isbt) {
	var tempJson = null;
	if(isFlexibleForm!=null && isFlexibleForm=='1'){
		tempJson = OCXpdfobj.GetFlexibleFormData();
	}else{
		tempJson = OCXpdfobj.GetControlJson();
	}
	var formjson = null;
	if (tempJson != '') {
		formjson = JSON2.parse(tempJson);
	}
	var error = "";
	if (formjson != null && formjson != 'null') {
		var feList = null;
		if(isFlexibleForm!=null && isFlexibleForm=='1'){
			feList = formjson.flexibleForm;
		}else{
			feList = formjson.trueform;
		}
		i = 0, l = feList.length;
		for (; i < l; i++) {
			var val = "";
			if (feList[i].type == 'checkbox') {
				var temp = feList[i].value;
				if (temp != '' && temp.length > 0) {
					val = temp.replace(new RegExp(";", "gm"), "^");
				}
				if (val == "undefined" || typeof (val) == "undefined") {
					val = "";
				}
			} else if (feList[i].type == 'radio') {
				val = feList[i].value;
				if (val == "undefined" || typeof (val) == "undefined") {
					val = "";
				}
			} else {
				// text
				val = feList[i].value;
				if (val == "undefined" || typeof (val) == "undefined") {
					val = "";
				}
			}
			for ( var j = 0; j < isbt.length; j++) {
				if (isbt[j].split(':')[0] == feList[i].name && val == '') {
					error += isbt[j].split(':')[1] + "不能为空,";
				}
			}
		}
		if (error == "") {
			return error;
		} else {
			return error.substring(0, error.length - 1);
		}
	}
}

/**
 * 还原
 */
function onRedo(){
	OCXpdfobj.OnRedo();
}

/**
 * 撤销
 */
function onUndo(){
	OCXpdfobj.OnUndo();
}

/**
 * 打印
 */
function PrintPDF(isOver) {
	getPrintPdfPath(isOver);
}

var si = null;
var len = 0;
function getPrintPdfPath(isOver) {
	var trueJSON = getPageData();
	var json = JSON2.stringify(trueJSON.pdfjson);
	var formjson = trueJSON.formjson;
	var pdfNewPath = "";
	json = json.replace(new RegExp("\"", "gm"), "\\\"");
	json = "\"" + json + "\"";
	var params = formjson;
	params['processId'] = processId;
	params['workflowId'] = workflowId;
	params['nodeId'] = nodeId;
	params['instanceId'] = instanceId;
	params['formId'] = formId;
	params['json'] = json;
	params['isOver'] = isOver;
	$.ajax({
		url : ctx+'/table_generatePdf.do?a=Math.random()',
		type : 'POST',
		cache : false,
		async : false,
		data : params,
		error : function(o, e) {
			alert('加载form元素错误, error:' + e + '。');
		},
		success : function(data) {
			return OCXpdfobj.PrintPDFByStamp(data, "", "", 2, 0);
		}
	});
	return pdfNewPath;
}

// ----历程相关js方法 begin --------

$(".timeline-minus").click(function(){ 
    var sunLength = $(this).closest('.timeline-item').find('.widget-box .widget-main2');
	var item = $(this).closest('.timeline-item');
    sunLength.each(function(index){
	    if(index>0){
		    if(sunLength.is(":hidden")){
		        sunLength.eq(index).fadeIn("slow");	
                $('.timeline-minus').removeClass("icon-plus-sign"); 				
                $('.timeline-minus').addClass("icon-minus-sign");	
				var nHeight = item.height()+10;
                item.find('.timeline-arrow').css('height',nHeight);					
			}else{
		        sunLength.eq(index).fadeOut("fast");
                $('.timeline-minus').removeClass("icon-minus-sign"); 				
                $('.timeline-minus').addClass("icon-plus-sign"); 
				item.find('.timeline-arrow').css('height','110px');			
			}
		}
	});
});

if(!$('.timeline-box1 i.timeline-line').hasClass('timeline-end')){
    $('.timeline-box1 i.timeline-indicator:last').addClass('timeline-wait');
	$(' .timeline-box1 i.timeline-indicator:last').html('待');
}

function ThirdChat(userId){
	/* var macaddr = document.getElementById("macaddr").value;
	if(macaddr==null || macaddr==''){
		return;
	}
	if(type!=null && type=='0'){
		alert("该人员不在线,只能发送离线消息！");
	}
	var userId = o.id;
	if(macaddr!=null){
		macaddr = macaddr.replaceAll(':','-');
	} */
	//ajax异步调取后台
	$.ajax({
		url: '${ctx}/ztree_ThirdChat.do',
        type: 'POST',
        cache: false,
        async: false,
        data:{
        	'userId':userId,'macaddr':$("#macaddr").val()
        },
   		error: function(){
   			 alert('AJAX调用错误');
   		},
   		success: function(result){
   			if(result!=null && result=='unOnline'){
   				alert("当前人员中威通讯录不在线,请登录");
   			}
   		}
	});
}
//------历程相关js方法 end -------
//打开封装版窗口必要方法
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