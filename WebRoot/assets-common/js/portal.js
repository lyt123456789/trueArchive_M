var appurl = "";

function GetQueryString(name,url){
   var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
   var r = url.substr(url.indexOf("?")+1).match(reg);
  
   if (r!=null) return unescape(r[2]); return null;
}

function maxMod(obj){	//可拖拽部分的内容框的最大化
	var link = $(obj).attr("maxlink");
	if(link==null || link==''){		return;
	}
	$("#defMain").hide();
	$("#maxMain").show();
	// refactor
	// var main = $(".tp-maxmod-bd");
	// var iframe = $(".tp-maxmod-bd>iframe");
	var title = $(obj).parents(".tp-hd").text();
	if($(obj).parents(".tp-mod").attr("kind") == "tabs" ){
		var tabTitle = $(obj).parents(".tp-mod").find(".tp-tabs-nav .tp-active").text();
		title += '-' + tabTitle;
	}
	
	// var logo = $(obj).parent().prev().prev().children("img").attr("src");
	var maxHd = $(".tp-maxmod-hd");
	var maxBd = $(".tp-maxmod-bd");
	var maxIframe = $(".tp-maxmod-bd>iframe");


	if (link == maxIframe.attr("src")) {
		return;
	} else {
		maxHd.find(".tp-maxmod-title").text(title);
		maxBd.empty();
		var newIframe = $("<iframe></iframe>");
		newIframe.attr({
		    id : "Mcontainer",
			src : link ,
			height : "700",
			width : "100%", 
			frameborder: "0"
		});
		newIframe.appendTo(maxBd);
		newIframe.on("load",function (){
			$("#maxBack").on('click',function(e){
				e.preventDefault();
				backMaxMod();
			})
		});
	}
}

function backMaxMod(backIndex){	//最大化内容框的返回功能
	$("#defMain").show();
	$("#maxMain").hide();
}

function initPersonInfo(){	//头像和岗位职责初始化
	$.ajax({
		type:"POST",
		dataType:"jsonp",
		jsonp:"callback",
		url:portalurl+" portalInterface_getEmpInfoJson.do?userId="+userId,
		cache: false,
		async: false,
		success:function(msg){
			$(".JS_userName").empty().text(msg.data.userName);
			$(".JS_userJob").empty().text(msg.data.jobtitles);
			$(".JS_userImg").attr("src",msg.data.headerImg);
			$(".JS_userJobInfoAll").html(msg.data.jobDescription);
			var objString = $(".JS_userJobInfoAll").text();
			var objLength = objString.length;
			var num = $(".JS_userJobInfo").attr("limit");
			$(".JS_userJobInfo").text(objString);
			if (objLength>num) {
				$(".JS_userJobInfo").text(objString.substring(0,num)+"...");
			};
		}
	})
}

//初始化发起事项列表
function initTopListEvent(){	
	$.ajax({
		type:"POST",
		dataType:"jsonp",
		jsonp:"callback",
		url:portalurl+"portalInterface_getModuleMenuJson.do?type=1&userId="+userId,
		cache: false,
		async: false,
	    success:function(msg){
	    	//$(".JS_eventList").empty();
	    	var eventList = msg.data.content;
	    	var length = typeof eventList != 'undefined' ? eventList.length : 0,
				i,
				j;
			var html = "";
			for(i=0;i<length;i++){
				html += "<li title='"+ eventList[i].title +"' id='"+ eventList[i].id +"' link='"+ eventList[i].link +"' identify='' sort='10'> <i class='tp-event-icon'> <img src='"+ eventList[i].icon +"'> </i> "+ eventList[i].title +"</li>"
			}
			//$(".JS_eventList").append("<ul class='tp-event-list'>"+ html +"</ul>")
			$(".JS_eventList").html("<ul class='tp-event-list'>"+ html +"</ul>")
	    }
	}).done(function(){
		$(".JS_eventList>.tp-event-list>li").each(function(){
			var oImg = $(this).find('img');
			var timer = null,
				tar = 0;
			
			$(this).on('mouseover' , function (){
				timer = setInterval(function (){
					if(oImg.height() > 380 ){
						if (tar == -350) {
							clearInterval(timer);
						} else {
							tar -= 50;
							oImg.css({
								top : tar + 'px'
							});
						}
					}
				},100);
			}).on('mouseout', function (){
				oImg.css({top : 0});
				tar = 0;
				clearInterval(timer);
				timer = null;
			});
			
			
			$(this).on("click",function(){	//给每一个发起事项配置showmodal的链接地址
				var link = $(this).attr("link");
				var title = $(this).attr("title");
				//var ret = window.showModalDialog(link+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
				topOpenLayerTabs(parseInt(Math.random()*100+1),1200,600,title,link);
				initContentModule();
			})
		});
		loadTopEventListEditBox();
	})

}


function initSystemInfo(){	//业务系统初始化
	$.ajax({
		type:"POST",
		dataType:"jsonp",
		jsonp:"callback",
		url:portalurl+"portalInterface_getSSOMenuJson.do?&userId="+userId,
		cache: false,
		async: false,

	    success:function(msg){
	    	$(".JS_systemList").empty();
	    	var sysList = msg.data.content;
	    	var length = typeof sysList != 'undefined' ? sysList.length : 0,
				i,
				j;
			var html = "";
			for(i=0;i<length;i++){
				html += "<li id='"+ sysList[i].id +"'> <i class='tp-sys-icon'> <img src='"+ sysList[i].icon +"'> </i> <a class='tp-sys-name' href='"+ sysList[i].link +"' target='_blank'>"+ sysList[i].title +"</a></li>"
			}
			$(".JS_systemList").append(html)
	    	
	    	
	    }


	}).done(function (){
		var sList = $('ul.tp-sys-list');
		sList.children('li').each(function (){
			var oImg = $(this).find('img');
			var timer = null,
				tar = 0;
			
			$(this).on('mouseover' , function (){
				timer = setInterval(function (){
					if(oImg.height() > 300 ){
						if (tar == -280) {
							clearInterval(timer);
						} else {
							tar -= 40;
							oImg.css({
								top : tar + 'px'
							});
						}
					}
				},100);
			}).on('mouseout', function (){
				oImg.css({top : 0});
				tar = 0;
				clearInterval(timer);
				timer = null;
			});
			//$(this).on("click",functisson(){	//给每一个发起事项配置showmodal的链接地址
			//	var link = $(this).attr("link");
			//	var ret = window.showModalDialog(link+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
		//		return false;
		//	})
		});
	});
}


function loadTopEventListEditBox(ajaxData){
	page_box();	//分页处理
	
}


function page_box(){	//分页功能
	var li_num = $(".tp-event-list-wrap>.tp-event-list li").length;
	var header_wd = $(".tp-main-top").width();
	var li_wd = header_wd*0.1;
	$(".tp-event-list-wrap>.tp-event-list li").css("width",li_wd);
	var page_num = Math.ceil(li_num/10);
	$(".tp-event-list-wrap>.tp-event-list").css("width",li_num*li_wd);
	$(".tp-event-page-box").html("");
	for(var i=1;i<=page_num;i++){
		if(i==1){
			$(".tp-event-page-box").append("<li><span class='tp-event-page-num'></span></li>");
			$('.JS_eventList').css('height','120px');
		}
		else{
			$('.JS_eventList').css('height','150px');
			$(".tp-event-page-box").css("margin-left",-($(".tp-event-page-box").width()/2));
			$(".tp-event-page-box").show();
			$(".tp-event-page-box").append("<li><span class='tp-event-page-num'></span></li>");
		}
	}
	$(".tp-event-page-box li:first-child").addClass("tw-active");
	$(".tp-event-page-box>li").each(function(){
		var index = $(this).index();
		$(this).children(".tp-event-page-num").html(index+1);
		$(this).children(".tp-event-page-num").on("click",function(){
			$(this).parent().addClass("tw-active").siblings().removeClass();
			$(".tp-event-list-wrap>.tp-event-list").animate({left:-index*header_wd});
		})
	})	
}

function adjustUpDown(objs){	//待办事项溢出上下移动
	$.each(objs,function(){
		var tabs = $(this);
		var tabNav = $(">.tp-tabs-nav" ,tabs);
		var tabNavItem = $(">.tp-nav-item" ,tabNav);
		var itemLt = tabNavItem.length;
		var tabNavHt = itemLt*40;
		var bdHt = $(this).parent(".tp-bd").height() - 20;
		if( tabNavHt > bdHt ){
			tabs.height(bdHt);
		}
	})
}


function initContentModule(){
	$.ajax({
		type:"POST",
		dataType:"jsonp",
		jsonp:"callback",
		url:portalurl+"portalInterface_getElementMenuJson.do?&userId="+userId,
		cache: false,
		async: true,

	    success:function(msg){
			var mods = msg.Data;
			var drap = msg.drap;
			var length = typeof mods != 'undefined' ? mods.length : 0,
				i,
				j;
			$(".tp-section").empty();
			
			for(i=0;i<length;i++){
				var html;
				var delMod ='';
				if(drap){
					//delMod = '<span class="tp-icon-close"></span>';
				}
				var maxLink = mods[i].maxlink == '' && mods[i].kind !== 'tabs' ? '' : '<span class="tp-icon-max" maxlink="'+ mods[i].maxlink +'"></span>';
				var modHd = '<div class="tp-mod '+ mods[i].modclass +'" id="'+ mods[i].id +'"  kind="'+ mods[i].kind +'" type="'+ mods[i].type +'" link="'+ mods[i].link +'" '+ (typeof mods[i].openType == "undefined" ? '' : 'openType="'+ mods[i].openType +'"' ) +'>'+
							'<div class="tp-hd"><span class="tp-icon"><img src="'+ mods[i].icon +'" ></span><span class="tp-title">'+ mods[i].title + '</span>'+
								'<div class="tp-mod-act">'+
									maxLink + delMod +
								'</div>'+
							'</div>';
				if( mods[i].kind == 'tabs'){
					var tabs = mods[i].tabs;
					var tabsclass = mods[i].tabsclass;
					var htmlTavNav = '<ul class="tp-tabs-nav">';

					for(j=0;j<tabs.length;j++){
						htmlTavNav += '<li class="tp-nav-item" kind="'+ tabs[j].kind +'" type="'+ tabs[j].type +'" link="'+ tabs[j].link +'"maxlink="'+ tabs[j].maxlink +'"><a href="javascript: void(0)" ><i class="tp-home-tabico"></i>'+ tabs[j].title +'<span class="tp-home-tabnum"></span></a></li>';
					}
					htmlTavNav += '</ul>';
					htmlTavBd = '<div class="tp-tabs-bd"></div>';
					html = modHd + '<div class="tp-bd"><div class="tp-tabs '+ tabsclass +'">' + htmlTavNav + htmlTavBd + '</div></div>';
				}else{
					html = modHd + '<div class="tp-bd"></div></div>';
				}
				$(".tp-section").append(html);
			}
			
			
			loadModuleContent($(".tp-section .tp-mod"));
			// tabs 上下滚动
			adjustUpDown($(".tp-section .tp-mod .tp-tabs"));

			
			
			if(drap){
				$( ".tp-section" ).sortable({
				    cursor: "move",
				    item:".tp-mod",
				    handle:".tp-hd",  
				    placeholder: "tp-mod-placeholder",            //只是子元素div可以拖动
				    helper: "tp-mod-helper",
				    opacity: 0.6,                       //拖动时，透明度为0.6
				    revert: false,
				    update:function(){
				    	// alert("11")
				    }
				});
			}

			
		}
	});
}


/**
 * [loadModuleContent 装载每个Module的内容，如果指定模块ID，则只绑定此模块的事件]
 * @param  {[type]} objs     [模块数组]
 * @param  {[type]} moduleId [模块ID]
 * @return {[type]}          [description]
 */
function loadModuleContent(objs, moduleId){

	$.each(objs,function(){
		if(typeof moduleId != 'undefined') {
			if ($(this).attr('id') == moduleId) {
				bindModuleEvents(this);
			}
		} else {
			bindModuleEvents(this);
		}
	});
}

function bindModuleEvents (module) {
	var moduleRoot = $(module);
	var contentBody = $(moduleRoot).find(".tp-bd");
	var contentHeader = $(moduleRoot).find(".tp-hd");
	
	var moduleId = moduleRoot.attr("id");
	var kind = moduleRoot.attr("kind");
	var type = moduleRoot.attr("type");
	var contentLink = moduleRoot.attr("link");
	var openType = moduleRoot.attr('openType'); 
	var oMaxBtn = contentHeader.find("span.tp-icon-max");
	oMaxBtn.on('click', function (){
		maxMod(this);
	});
	if( kind == "dynamic" ){
		contentBody.html("");
		var linkLen = contentLink.length;
		contentLink.substr(linkLen-3,linkLen-1)==".do" ? contentLink += "?" : contentLink += "&";
		contentLink += "userId="+userId;
		
		$.ajax({
			type:"POST",
			dataType:"jsonp",
			jsonp:"callback",
			url: contentLink,
			cache: false,
			async: true,
		    success:function(msg){
		    	var contents = msg.data.content;
		    	var length = typeof contents != 'undefined' ? contents.length : 0,
				i;
				var type =  msg.data.type;
		    	if(contents==""){
					$(contentBody).html("<div class='tp-msg'><img src='../../assets-common/image/noneData.png' /></div>");
					//$(contentBody).html("<div class='tp-msg'>none</div>");
				}else{
					$(contentBody).html("");
					if(type == "news"){
						var html = '<ul class="tp-info-list">';
						for(i=0;i<length;i++){
							var unRead = "";
							if (contents[i].isnew == "1"){
								unRead = 'tp-unread';
							}
							if(contents[i].cate!==''){
								var instr = '<span class="tp-info-cate">['+ contents[i].cate +']</span>';
							}else{
								var instr = '<span class="tp-info-cate"></span>';
							}
							
							html += '<li class="tp-info-item '+ unRead +'"><a href="'+ contents[i].link +'" title="'+ contents[i].title +'" class="tp-info-title">'+instr+ contents[i].title +'</a><span class="tp-info-date">'+ contents[i].date +'</span></li>' 
						}
						html += "</ul>";
					}
					
					$(contentBody).html(html);			
				}
		    },
			error : function (e){
				$(contentBody).html("<div class='tp-msg'>error</div>");
			}
		}).done(function (){
			contentBody.find("a").each(function(){
				//console.log(openType);
				/*--下载动作判断--*/
				if(openType!=='0' && openType!==0){
					//console.log($(this));
					$(this).off("click").on("click",function(e){
						var link = $(this).attr('href');
						e.preventDefault();
						var ret = window.showModalDialog(link+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
						loadModuleContent($(".tp-section .tp-mod"), moduleId)
					})
				}
			})
		});
				
	} else if( kind == "static" ){
		contentBody.html("");
		var iframe = $('<iframe></iframe>').attr({
			"class" : "cont-page",
			style : "width: 100%;height: 100%;",
			frameborder : 0,
			scrolling : "no",
			src : contentLink
		});
		iframe.appendTo(contentBody);
	} else if( kind == "tabs" ){
		var tabs = $(".tp-tabs" ,contentBody);
		var tabNav = $(">.tp-tabs-nav" ,tabs);
		var tabNavItem = $(">.tp-nav-item" ,tabNav);
		var tabBd = $(">.tp-tabs-bd" ,tabs);
		var currentIndex = $(">.tp-active" ,tabNav).index();
		currentIndex = currentIndex == -1 ? 0 : currentIndex;
		tabNavItem.attr("data-tab-idx", "");
		tabBd.html("");
		// var tabPanel = $(">.tp-tab-panel" ,tabBd);
		var tabLen = tabNavItem.length;
		tabNavItem.each(function(index, value){
			var $this = $(this);
			// var tabLink = $this.attr("link")+"&userId="+userId;
			var tabLink = $this.attr("link")+"&userId="+userId;
			var tabKind = $this.attr("kind");
			var tabType = $this.attr("type");
			var tabMaxLink = $this.attr("maxlink");
			tabBd.append("<div class='tp-tab-panel'></div>");
			var tabPanel = tabBd.find(".tp-tab-panel").eq(index);
			setTimeout(function (){
				if(tabKind == "static"){
					var iframe = $('<iframe></iframe>').attr({
						"class" : "cont-page",
						style : "width: 100%;height: 100%;",
						frameborder : 0,
						scrolling : "no",
						src : tabLink
					});
					iframe.appendTo(tabPanel);
				} else if (tabKind == "dynamic"){
					$.ajax({
						type:"POST",
						dataType:"jsonp",
						jsonp:"callback",
						url: tabLink,
						cache: false,
						async: true,
					    success:function(msg){

					    	var tabPanel = tabBd.find(".tp-tab-panel").eq(index);

					    	if( msg.success == false){
					    		var message = msg.message;
					    		tabPanel.html("<div class='tp-panel-cnt'><div class='tp-msg'>"+message+"</div></div>");
					    	}else{
					    		var contents = msg.data.content;
								
						    	// console.log(contents);
						    	var length = typeof contents != 'undefined' ? contents.length : 0,
								i;
								var num = msg.data.num;
								num = num != '' ? num : 0;
								if(tabNavItem.eq(index).hasClass("tp-active")){
									tabNavItem.eq(index).find(".tp-home-tabnum").html('('+ num + ')')
								}else{
									tabNavItem.eq(index).find(".tp-home-tabnum").html(num);
								}
						    	if(contents==""){
						    		//tabPanel.html("<div class='tp-panel-cnt'><div class='tp-msg'>无数据</div></div>");
									tabPanel.html("<div class='tp-panel-cnt'><div class='tp-msg-a'><img src='../../assets-common/image/noneData.png' /></div></div>");
								}else{
									if(tabType == "event"){				
										var html = '<div class="tp-panel-cnt"><ul class="tp-info-list">';
										for(i=0;i<length;i++){
											var unRead = "";
											if (contents[i].isnew == "1"){
												unRead = 'tp-unread';
											}
											if(contents[i].cate!==''){
												var instr = '<span class="tp-info-cate">['+ contents[i].cate +']</span>';
											}else{
												var instr = '<span class="tp-info-cate"></span>';
											}
											html += '<li class="tp-info-item '+ unRead +'"><a href="'+ contents[i].link +'" title="'+ contents[i].title +'" class="tp-info-title">'+ instr+contents[i].title +'</a><span class="tp-info-date">'+ contents[i].date +'</span></li>' 
										}
										html += "</ul></div>";
										
									} else if(tabType == "news"){
										var html = '<div class="tp-panel-cnt"><ul class="tp-news-list">';
										for(i=0;i<length;i++){
											var unRead = "";
											if (contents[i].isnew == "1"){
												unRead = 'tp-unread';
											}
											if(contents[i].cate!==''){
												var instr = '<span class="tp-info-cate">['+ contents[i].cate +']</span>';
											}else{
												var instr = '<span class="tp-info-cate"></span>';
											}
											html += '<li class="tp-info-item '+ unRead +'"><a href="'+ contents[i].link +'" title="'+ contents[i].title +'" class="tp-info-title">'+instr+ contents[i].title +'</a><span class="tp-info-date">'+ contents[i].date +'</span></li>' 
										}
										html += "</ul></div>";
									}
									
									tabPanel.html(html);	
										
									
								}
					    	}
							
						},
						error : function (e){
							tabPanel.html("<div class='tp-panel-cnt'><div class='tp-msg'>error</div></div>");
						}
					}).done(function (){
						// initSingleModule('.cont-header', moduleId);	
						tabBd.find("a").each(function(){
							$(this).off("click").on("click",function(e){
								e.preventDefault();
								var link = $(this).attr('href');
								var id = GetQueryString('processId',link);
								var title = $(this).attr('title');
								//var ret = window.showModalDialog(link+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
								topOpenLayerTabs(id,1200,600,title,link);
								loadModuleContent($(".tp-section .tp-mod"), moduleId);
							})
						})
					});
				}
				
			}, 600 * index + 200)
		})

		tabs.tabs({
			tabNav : '.tp-tabs-nav',
			tabNavItem : '.tp-nav-item',
			tabCnt : '.tp-tabs-bd',
			tabPanel : '.tp-tab-panel',
			activeClass : 'tp-active',
			currentIndex: currentIndex,
			callback: function(){
				var currentIndex = $(">.tp-active" ,tabNav).index();
				var maxLink = tabNavItem.eq(currentIndex).attr("maxlink");
				var maxLinkobj = contentHeader.find(".tp-icon-max");
				maxLink == "" ? maxLinkobj.hide() : maxLinkobj.show().attr("maxlink", maxLink);
				$(">.tp-nav-item" ,tabNav).each(function(){
					var $num = $(this).find(".tp-home-tabnum");
					var num = $num.text();
					if (num.indexOf("(") != -1) {
						num = num.substring(1,num.length-1)
					}
					if($(this).hasClass('tp-active')){
						$num.html("(" + num + ")")
					}else{
						$num.html(num)
					}
				})
			}
		});

	}



}

/**
 * @description 显示当前时间
 * @param {String} o  html容器
 * @param {String} type 时间类型
 * @return {Null} null null
 */
function showTime(o,title,type){	
	var time=setInterval(
	function(){
		var d=new Date();
		var weekday=new Array(7);
			weekday[0]="星期日";
			weekday[1]="星期一";
			weekday[2]="星期二";
			weekday[3]="星期三";
			weekday[4]="星期四";
			weekday[5]="星期五";
			weekday[6]="星期六";
			var year=testLen(d.getFullYear()) ,month=testLen(d.getMonth()+1) ,day=testLen(d.getDate()) ,hours=testLen(d.getHours()) ,
				minutes=testLen(d.getMinutes()),seconds=testLen(d.getSeconds()),milliseconds=testLen(d.getMilliseconds());
			if(type=='short'){
				$(o).html(title+ year +'年'+ month +'月'+ day +'日'+'&nbsp;&nbsp;'+ hours +'&nbsp;:&nbsp;'+ minutes);
			}else if(type=='normal'){
				$(o).html(title+ year +'年'+ month +'月'+ day +'日'+'&nbsp;&nbsp;'+weekday[d.getDay()]);
			}else{
				$(o).html(title+ year +'年'+ month +'月'+ day +'日'+'&nbsp;&nbsp;'+weekday[d.getDay()]+'&nbsp;&nbsp;'+ hours +'&nbsp;:&nbsp;'+ minutes+'&nbsp;:&nbsp;'+ seconds);
			}
		}
		,1000
	);
	function testLen(s){
		return (s.toString().length)<2?('0'+s):s;
	}
}

function password_reset(){
	$(".sure-btn").click(function(){
		$(".pass-reset").fadeOut();
	})
	$(".cancel_btn").click(function(){
		$(".pass-reset").fadeOut();
	})
	$(".close_form").click(function(){
		$(".pass-reset").fadeOut();
	})
}	

$(document).ready(function() {

	showTime(".JS_time","，您好！")

    var winWidth = $(document).width();
	if(winWidth > 1445){
		$("body").addClass("tp-layout-col3");
	}else{
		$("body").removeClass("tp-layout-col3");
	}
	
	$(window).resize(function(){
		var winWidth = $(document).width();
		if(winWidth > 1445){
			$("body").addClass("tp-layout-col3");
		}else{
			$("body").removeClass("tp-layout-col3");
		}
	})



	portalurl = appurl;
	userId = $(".user").attr("value");
	$.backstretch("/trueOA/assets-common/img/portal/bg-body.jpg",{
		centeredY: false
	});
	initTopListEvent();
	initPersonInfo();
	initSystemInfo();
	initContentModule();



	var flag = true;
	$(".JS_chooseImg").on("change",function(){
		var link = $(this).val();
		var dot = link.lastIndexOf(".");
		var type = link.substring(dot+1);
		var support =new Array();
		support[0] = "jpg";
		support[1] = "png";
		support[2] = "jpeg";
		support[3] = "bmp";
		$(".JS_imgType").attr("value",type);
		flag = true;
		$.each(support, function (i){
			if (support[i] == type) {
				flag = false;
			}
		});
		
		new uploadPreview({ UpBtn: "up_img", DivShow: "preview", ImgShow: "img-preview" });
	});
	
	$(".tp-user-setting").click(function(){
		parent.layer.open({
            title :'修改头像',
            content: $('.JS_headModify'),
            // content: 'test',
            type: 1, 
            area: ['450px', '300px'],
            btn: ['保存', '取消'],
            yes: function(index, layero){ 
			    if (!flag){
					var options = {
						url: appurl+'portalInterface_updateEmpInfo.do',
						success: function(d) {
						  if ("success" == d) {
							// $(".person-edite-cancel").trigger("click");
							$(".JS_chooseImg").data("first", false);
							alert("保存成功");
							layer.close(index);
							initPersonInfo();
						  }
						} 
					};
					$("#uploadFacebook").ajaxSubmit(options);
				} else {
					alert("文件类型不合法，只支持jpg，png，jpeg，bmp");
				}
			}
        }); 
	})

	$("#jobInfo").click(function(){
		parent.layer.open({
            title :'岗位职责',
            content: $('.JS_userJobInfoAll'),
            // content: 'test',
            type: 1, 
            area: ['500px', '300px']
        })
	})
	
	/*系统设置*/
	$(".tp-topset-icon-edit").click(function(){
		var cont_list_wd = $(".cont-list-box").width();
		$(".cont-edit-box").css("width",cont_list_wd);
		var wd = $(".cont-edit-box").width();
	  	$(".lay_outer").fadeIn();						  
		$(".cont-edit-box").fadeIn().css("margin-left",-wd/2);
		$(".cont-edit-box>.btn-box>.edit-cancel-btn").click(function(){
			$(".lay_outer").fadeOut();
			$(".cont-edit-box").fadeOut();
		});
	});
	/*修改密码*/
	$(".tp-topset-icon-pwd").click(function(){
		/*var fm = $("#pass_frame");
		if(fm.length>0){
			//window.frames["childPage"].sonff();
			$(".pass-reset").fadeIn();
			password_reset();
		}*/
	});
});