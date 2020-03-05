var appurl = "";
function biggest(obj){	//可拖拽部分的内容框的最大化
	var link = $(obj).attr("maxlink");
	if(link==null || link==''){		return;
	}
	$("#main_change").css("display","none");
	$("#upcoming").css("display","block");
	// refactor
	var main = $(".main_middle");
	var iframe = $(".main_middle>iframe");
	var title = $(obj).parent().prev().text();
	var logo = $(obj).parent().prev().prev().children("img").attr("src");
	
	if (link == iframe.attr("src")) {
		return;
	} else {
		var oUl = $('#upcoming > .main_top > .tab_list');
		var oLi = $('<li></li>');;
		var oLogo = $("<div class='sub_logo'></div>").append($('<img src="'+logo+'">'));
		var oTitle = $("<div class='sub_title'>"+title+"</div>");
		oLi.append(oLogo).append(oTitle);
		oUl.empty().append(oLi);
		main.empty();
		var newIframe = $("<iframe></iframe>");
		newIframe.attr({
			src : link ,
			height : "700",
			width : "100%", 
			frameborder: "0"
		});
		newIframe.appendTo(main);
		newIframe.on("load",function (){
			$("#backk").on('click',function(e){
				e.preventDefault();
				backk();
			})
		});
	}
}
function backk(backIndex){	//最大化内容框的返回功能
	$("#upcoming").css("display","none");
	$("#main_change").css("display","block");
	if (backIndex) {
		$('#tab1>li:eq('+backIndex+')').click()
	}
}

function initPersonInfo(){	//头像和岗位职责初始化
	$.ajax({
		type:"POST",
		dataType:"jsonp",
		jsonp:"callback",
		url:appurl+"/portalInterface_getEmpInfoJson.do?userId="+userId,
		cache: false,
		async: false,
		success:function(msg){
			$(".person-name").empty().html(msg.data.userName);
			$(".job").empty().html(msg.data.jobtitles);
			
			$(".photo-box>img").attr("src",msg.data.headerImg);
			$(".work-desc").html(msg.data.jobDescription);
			$(".lay_content").html(msg.data.jobDescription);
			$("#work-duty").html(msg.data.jobDescription);
			$("#preview>img").attr("src",msg.data.headerImg);
			text_limit();	//岗位职责内容溢出处理
		}
	})
}
/* tab */
function tab(){
	$("#tab1>li").each(function(){
		$(this).on("click",function(){
			var index = $(this).index();
			eventTabIndex = index;
			$(this).addClass("current").siblings().removeClass();
			$("#tab1-cont>.item_list").eq(index).show().siblings().hide();
		})
	})

	if (eventTabIndex != -1) {
		// 刷新后保存上一次的状态
		$('#tab1>li').eq(eventTabIndex).trigger('click');
	}

	$("#tab2>li").each(function(){
		$(this).on("click",function(){
			var index = $(this).index();
			var cont_link = $(this).attr("link")+"&userId="+userId;
			$(this).addClass("current").siblings().removeClass();
			$("#tab2-cont>iframe").attr("src",cont_link);
		})
	})
}

function throttle (obj, fn, delay) {
	clearTimeout(obj.tId);
	delay = delay || 200;
	obj.tId = setTimeout(function (){
		fn && fn();
	}, delay);
}
/* 右侧回到顶部 */
function backToTop(){
    //置顶按钮显示/隐藏实现
    $(window).scroll(function(){
		throttle(this, function (){
			var scroll_top = $(document).scrollTop();//滚动条到顶部的垂直高度
			scroll_top > 0 ? $("#goto-top").fadeIn(500) : $("#goto-top").fadeOut(500);
		})
    });
	
	//置顶
	$("#goto-top").click(function(e){
        e.preventDefault();
        $(document.documentElement).animate({
            scrollTop: 0
            },200);
        //支持chrome
        $(document.body).animate({
            scrollTop: 0
            },200);
    });
}
function page_box(){	//分页功能
	var li_num = $(".cont-list-box>.cont-list li").length;
	var header_wd = $(".main-header").width();
	var li_wd = header_wd*0.1;
	$(".cont-list-box>.cont-list li").css("width",li_wd);
	var page_num = Math.ceil(li_num/10);
	$(".cont-list-box>.cont-list").css("width",li_num*li_wd);
	$(".page-box").html("");
	for(var i=1;i<=page_num;i++){
		if(i==1){
			$(".page-box").append("<li><span class='page-num'></span></li>");
		}
		else{
			$(".page-box").css("margin-left",-($(".page-box").width()/2));
			$(".page-box").show();
			$(".cont-list-box>.cont-list").css("top",24);
			$(".page-box").append("<li><span class='page-num'></span></li>");
		}
	}
	$(".page-box li:first-child").addClass("choosed");
	$(".page-box>li").each(function(){
		var index = $(this).index();
		$(this).children(".page-num").html(index+1);
		$(this).children(".page-num").on("click",function(){
			$(this).parent().addClass("choosed").siblings().removeClass();
			$(".cont-list-box>.cont-list").animate({left:-index*header_wd});
		})
	})	
}
function save_edit(){	//发起事项列表保存设置
	var new_order=[];
	$(".cont-edit-box>.cont-list>li").each(function(){
		new_order.push({"id":$(this).attr("id")});
	})
	new_order.join(",");
	var a = JSON.stringify(new_order);			    	
	$(".cont-edit-box>.btn-box>.edit-save-btn").on("click",function(){
		$(".lay_outer").fadeOut();
		$(".cont-edit-box").fadeOut();
		$.ajax({
			type:"POST",
			data:a,
			dataType:"jsonp",
			jsonp:"callback",
			url:portalurl+"portalInterface_saveModuleMenuSort.do?userId="+userId+"&new_order="+a,
			success:function(msg){
				if(msg[0].result == "true"){
					initTopListEvent();					
				}				
			}
		})
	})
}
function reset_edit(){	//恢复默认
	$(".cont-edit-box>.btn-box>.edit-reset-btn").on("click",function(){
		$(".lay_outer").fadeOut();
		$(".cont-edit-box").fadeOut();
		$.ajax({
			type:"POST",
			dataType:"jsonp",
			jsonp:"callback",
			url:portalurl+"portalInterface_clearModuleMenuSort.do?userId="+userId,
			success:function(){				
				initTopListEvent();
			}			
		})		
	})
}
function cont_module_sort(){	//内容模块排序
	var json = [];
	$(".drag-part>.drag:visible").each(function(){
			json.push({"id":$(this).attr("id")});						
	})
	json.join(",");
	var b = JSON.stringify(json);
	$.ajax({
		type:"POST",
		data:b,
		dataType:"jsonp",
		jsonp:"callback",
		url:portalurl+"portalInterface_setElementMenuInfo.do?type=1&userId="+userId+"&json="+b
	})
}

function module_set_add(){
	var json = [];
	$(".alreadyadd-list>li").each(function(){
			json.push({"id":$(this).attr("id")});						
	})
	json.join(",");
	var b = JSON.stringify(json);
	$.ajax({
		type:"POST",
		data:b,
		dataType:"jsonp",
		jsonp:"callback",
		url:portalurl+"portalInterface_setElementMenuInfo.do?type=1&userId="+userId+"&json="+b
	})
}

function refreshMyWorkEventList(curItem){	//刷新我的工作事项事件列表
	var link = $(curItem).attr("link");
		link += "&userId="+userId;
	var index = curItem.index();
	$.ajax({
		type:"POST",
		dataType:"jsonp",
		jsonp:"callback",
		url:link + "&t=" + Math.random(),
		cache: false,
		async: false,
		success:function(msg){

			var oWrapper = $("#tab1-cont>.item_list").eq(index);
			oWrapper.empty();
			var data = msg.data == "" ? "<div class='no-item'></div>" : msg.data;
			oWrapper.html(data);
			var childrenLen = curItem.children().length;
			
			
			if(childrenLen == 2){
				if (msg.num != 0)
					curItem.empty().append("<span class='icon_color'>("+ msg.num +")</span>");
			} else if(childrenLen == 3){
				if (msg.num == 0){
					curItem.children().eq(2).remove();
				} else {
					var flag = curItem.find(".icon_color").length > 0;
					if (flag) {
						curItem.find(".icon_color").html("("+ msg.num +")");
					} else {
						curItem.find(".icon_ibg").html(msg.num);
					}
					
				}
			}
			
			var moduleId = $('#tab1-cont').parent().parent().parent().parent().attr("id");
			initSingleModule('.cont-header', moduleId);
		},
		error : function (e){
			alert("Error:" +  e);
		}
	})	
}


function initTopListEvent(){	//初始化发起事项列表
	$(".cont-list-box").empty();
	$.ajax({
		type:"POST",
		dataType:"jsonp",
		jsonp:"callback",
		url:portalurl+"portalInterface_getModuleMenuJson.do?type=1&userId="+userId,
		cache: false,
		async: false,
		success:function(msg){
			
			$(".cont-list-box").empty().html(msg.data);
			$(".cont-list-box>.cont-list>li").each(function(){
				var oImg = $(this).find('img');
				var timer = null,
					tar = 0;
				
				$(this).on('mouseover' , function (){
					timer = setInterval(function (){
						if (tar == -350) {
							clearInterval(timer);
						} else {
							tar -= 50;
							oImg.css({
								top : tar + 'px'
							});
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
					var ret = window.showModalDialog(link+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
					var aLi = $("#tab1>li");

					aLi.each(function (index, value){
						var curLi = $(this);
						refreshMyWorkEventList(curLi);
					});
				})
			});
			
			loadTopEventListEditBox(msg.data);
		}
	})
}

function loadTopEventListEditBox(ajaxData){
	page_box();	//分页处理
	$(".cont-edit-box>.cont-list").html("");
	$(ajaxData).insertBefore(".cont-edit-box>.btn-box");
	reset_edit();
	save_edit();
	var lastOrder = null,curOrder = null;
	$( ".cont-edit-box>.cont-list").sortable({
		cursor: "move",
		item:"li",
		handle:".icon-cont",              //只是子元素li可以拖动
		opacity: 0.6,                       //拖动时，透明度为0.6
		revert: true,
		update:function(){
			var new_order=[];
			$(".cont-edit-box>.cont-list>li").each(function(){
				var obj = {"id":$(this).attr("id")};
				new_order[new_order.length] = obj;
			})
			new_order.join(",");
			var a = JSON.stringify(new_order);
			curOrder = a;
			
			$(".cont-edit-box>.btn-box>.edit-save-btn").off('click').on("click",function(){
				$(".lay_outer").fadeOut();
				$(".cont-edit-box").fadeOut();
				if (lastOrder != curOrder) {
					$.ajax({
						type:"POST",
						data:curOrder,
						dataType:"jsonp",
						jsonp:"callback",
						url:portalurl+"portalInterface_saveModuleMenuSort.do?userId="+userId+"&new_order="+a,
						success:function(msg){
							if(msg[0].result == "true"){
								lastOrder = curOrder;
								initTopListEvent();	
							}				
						}
					})
				}
			})
		}
	});
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
			//console.log(msg.data);
			$(".system>.sideinfo-cont-body").empty().html(msg.data);
		}
	}).done(function (){
		var sList = $(".system>.sideinfo-cont-body").find('ul.system-list');
		sList.children('li').each(function (){
			var oImg = $(this).find('img');
			var timer = null,
				tar = 0;
			
			$(this).on('mouseover' , function (){
				timer = setInterval(function (){
					if (tar == -280) {
						clearInterval(timer);
					} else {
						tar -= 40;
						oImg.css({
							top : tar + 'px'
						});
					}
				},100);
			}).on('mouseout', function (){
				oImg.css({top : 0});
				tar = 0;
				clearInterval(timer);
				timer = null;
			});
		});
	});
}

function loadWorkInfo(callback){	//我的工作事项内容框初始化
	var 
		$aEventLi = $('#tab1').find('>li'),
		totalListItemCount = $aEventLi.length,
		counter = 0;

	// 检查这里的链接配置项，有两个 9984 端口的请求不能成功返回
	$aEventLi.each(function(index, value){
		var _this = this;
		var cont_link = $(_this).attr("link")+"&userId="+userId;
		$("#tab1-cont").append("<div class='item_list'></div>");
		setTimeout(function (){
			$.ajax({
				type:"POST",
				dataType:"jsonp",
				jsonp:"callback",
				url: cont_link,
				cache: false,
				async: true,
				success:function(b){
					var data = b.data;
					var num = b.num;
					
					if(data==""){
						$("#tab1-cont>.item_list").eq(index).html("<div class='no-item'></div>");
						$(_this).children().eq(2).remove();
					}else{
						$("#tab1-cont>.item_list").eq(index).html(data);
						$(_this).children("span").eq(2).html(num);
					}
				},
				error : function (e){
					console.log('Error', e);
				}
			}).done(function (){
				var moduleId = $('#tab1-cont').parent().parent().parent().parent().attr("id");
					initSingleModule('.cont-header', moduleId);
			});
			if (++counter >= totalListItemCount) {
				if (callback) setTimeout(callback, 800);
			}
		}, 600 * index + 200)
	})
}
function adjustUpDown(){	//待办事项溢出上下移动
	var li_num = $("#tab1>li").length;
	var ul_ht = li_num*40;
	var body_ht = $(".tab1-list").height();
	$("#tab").css("height",ul_ht);
	if(ul_ht>body_ht){
		$(".up-btn").css("display","block");
		$(".down-btn").css("display","block");
		$(".info_left").css("padding-bottom","35px");
		var ul_top = $("#tab1").position().top;
		var ht = body_ht-ul_ht;
		$(".down-btn").click(function(){
			if(ul_top>ht){
				$("#tab1").css("top",ul_top-40);
				$(".up-btn").css("background-color","#babdc3");
				ul_top = $("#tab1").position().top;
				if(ul_top<ht||ul_top==ht){
					$(".down-btn").css("background-color","#dae5ed");
				}
			}
		})
		$(".up-btn").click(function(){
			if(ul_top>ht){
				if(ul_top<0){
					$("#tab1").css("top",ul_top+40);
					$(".down-btn").css("background-color","#babdc3");
					ul_top = $("#tab1").position().top;
					if(ul_top>0||ul_top==0){
						$(".up-btn").css("background-color","#dae5ed");
					}
				}
			}else{
				$("#tab1").css("top",ul_top+40);
				$(".down-btn").css("background-color","#babdc3");
				ul_top = $("#tab1").position().top;				
			}
		})
	}
}

function num_init(){

	$("#tab1>li").each(function () {
		if($(this).children().length==3){
			var oSpan = $(this).find("span").eq(2);
			var index = $(this).index();
			if (index == 0){
				var txt = "(" + $(oSpan).html() + ")";
				$(oSpan).html(txt);
			} else {
				$(oSpan).removeClass("icon_color").addClass("icon_ibg");
			}			
			$(this).click(function(){
				if($(this).children().length==3){
					var oSpan = $(this).find("span").eq(2);
					var t = $(oSpan).html();
					$(oSpan).removeClass("icon_ibg").addClass("icon_color");
					
					if (t.indexOf("(") == -1) {
						$(oSpan).html("(" + t + ")");
					}
					$(this).siblings().each(function(){
						if($(this).children().length==3){
							var oSpan = $(this).find("span").eq(2);
							
							var txt = $(oSpan).html();
							if (txt.indexOf("(") != -1) {
								$(oSpan).html(txt.substring(1,txt.length-1));
							}
							
							$(oSpan).removeClass("icon_color").addClass("icon_ibg");
						}
					});
				}
			});			
		}
	});	
}

var compose = function (f, g){
	return function (){
		f(g());
	};
};
var eventTabIndex = 0;
var initializeEventTab = compose(tab, num_init);
/**
 * [initContentModule grap all modules from the remote site]
 * @return {[type]} [description]
 */
function initContentModule(){
	$.ajax({
		type:"POST",
		dataType:"jsonp",
		jsonp:"callback",
		url:portalurl+"portalInterface_getElementMenuJson.do?userId="+userId,
		cache: false,
		async: true,
		success:function(msg){
console.log(msg);
			var length = typeof msg != 'undefined' ? msg.length : 0,
				i;
			$(".no-drag").empty();
			$(".drag-part").empty();
			$(".main_content3").empty();
			
			for(i=0;i<length;i++){
				typeof msg[i].module1 != 'undefined' && $(".no-drag").append(msg[i].module1);
				typeof msg[i].module2 != 'undefined' && $(".no-drag").append(msg[i].module2);
				typeof msg[i].module3 != 'undefined' && $(".no-drag").append(msg[i].module3);
			}
			
			length > 0 && msg[length-1].module4 && $(".main_content3").append(msg[length-1].module4);
			
			
			loadModuleContent($(".no-drag>.drag"));
			//第二次ajax加载我的工作事项，取到每个标签卡里的的内容
			loadWorkInfo(initializeEventTab);
			
			adjustUpDown();
			
			setTimeout(function(){
				$(".schedule_list>li:odd").css("background-color","#f6f7f8");
			},500);		
		
			
			var first_link = $("#tab2>li:first-child").attr("link");
			$("#tab2-cont>iframe").attr("src",first_link+"&userId="+userId);
			//$(".delete-cont").click(delete_module);
			/* sort */
		    $( ".drag-part" ).sortable({
			    cursor: "move",
			    item:".drag",
			    handle:".cont",              //只是子元素div可以拖动
			    opacity: 0.6,                       //拖动时，透明度为0.6
			    revert: false,
			    update:function(){
			    	cont_module_sort();
			    }
			});
			move_list();
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
	var contentBody = $(moduleRoot).find(".cont>.cont-body");
	var contentHeader = $(moduleRoot).find(".cont>.cont-header");
	
	var moduleId = moduleRoot.attr("id");
	var kind = contentBody.attr("kind");
	var contentLink = $(contentBody).attr("link");
	
	var oMaxBtn = contentHeader.find("span.biggest");
	oMaxBtn.on('click', function (){
		biggest(this);
	});
	openEventListByModal(moduleId);
	
	if( kind == "dynamic" ){
		var linkLen = contentLink.length;
		contentLink.substr(linkLen-3,linkLen-1)==".do" ? contentLink += "?" : contentLink += "&";
		contentLink += "userId="+userId;
		
		$.ajax({
			type:"POST",
			dataType:"jsonp",
			jsonp:"callback",
			url:contentLink,
			cache: false,
			async: false,
			success:function(msg){
				if(msg.data==""){
					$(contentBody).html("<div class='no-item'></div>");
				}else{
					$(contentBody).html(msg.data);			
				}
			}
		}).done(function (){
			initSingleModule('.cont-header', moduleId);	
		});			
	} else if( kind == "static" ){
		var iframe = $('<iframe></iframe>').attr({
			"class" : "cont-page",
			style : "width: 100%;height: 100%;",
			frameborder : 0,
			scrolling : "no",
			src : contentLink
		});
		iframe.appendTo(contentBody);
	}
}

function initSingleModule(sHeaderClass , moduleId){
	if (typeof moduleId == 'undefined')return;
	
	var oModule = $('#' + moduleId);
	var oModuleHeader = oModule.find(sHeaderClass);
    if (oModuleHeader) {
		var aA = oModuleHeader.find("a");
		if (aA.length > 0) {
			// 我的工作事项内容框
			aA.off('click').on('click',function(e){
				e.preventDefault();
				var curLi = $("#tab1").find(".current");
				var curLiIndex = $(curLi).index();
				var max_link = curLi.attr("maxlink");
				
				$("#main_change").css("display", "none");
				$("#upcoming").css("display", "block");
				
				var main = $(".main_middle");
				var iframe = main.find("iframe");
				if (iframe.attr('src') == max_link) {
					return ;
				} else {
					//multiple titles and logos
					var aLi = $("#tab1").children('li'),titles = [],logos = [], maxLinks = [];
					$.each(aLi, function (){
						var aSpan = $(this).find('span');
						maxLinks[maxLinks.length] = $(this).attr("maxlink");
						titles[titles.length] = aSpan.eq(1).text();
						logos[logos.length] = aSpan.eq(0).find("img").attr('src');
					});
					var targetUl = $("#upcoming > .main_top > .tab_list").empty();
					$.each(titles, function (index, value){
						var oLi = $("<li></li>").attr("maxlink", maxLinks[index]);
						if (index === curLiIndex) {
							oLi.addClass("active_tab");
						}
						oLi.appendTo(targetUl);
						$("<div class='sub_logo'></div>").append($("<img src='"+ logos[index] +"' />")).appendTo(oLi);
						$("<div class='sub_title'></div>").text(titles[index]).appendTo(oLi);
						oLi.on('click', function (){
							refreshIframeByTabLink(maxLinks[index]);
							$(this).addClass("active_tab").siblings().removeClass("active_tab");
						});
					})
					
					main.empty();
					var newIframe = $("<iframe></iframe>");
					newIframe.attr({
						src : max_link ,
						height : "700",
						width : "100%", 
						frameborder: "0"
					});
					newIframe.appendTo(main);
					newIframe.on("load",function (){
						$("#backk").on('click',function(e){
							e.preventDefault();
							var backIndex = targetUl.find("li.active_tab").index();
							backk(backIndex);
						})
					});
				}
			})
			
			//declare a calc length func to adjust the words width
			var calcLength = function(str){
				var realLength = 0, len = str.length, charCode = -1;
					for (var i = 0; i < len; i++) {
						charCode = str.charCodeAt(i);
						if (charCode >= 0 && charCode <= 128) realLength += 1;
						else realLength += 2;
					}
					return realLength;
			};
			
			var itemList = oModule.find('.item_list');
				itemList.each(function(index){
					var oLi = $('#tab1').find('li:eq('+index+')');
					var eventList = $(this).find(".event-list");
						eventList.each(function(){
							var aA = $(this).find('li>a');
							aA.each(function(){
								//fix a tag's length
								var oSpan = $(this).prev();
								var t1 = oSpan.text(),
									t2 = $(this).text();
									
								//t1 = t1.substring(1, t1.length-1);
								
								var l1 = calcLength(t1),
									l2 = calcLength(t2);
								
								if (l1 >= 12) {
									var endIndex = t1.charAt(12) == ']' ? 8 : 9;
									t1 = t1.substring(0,endIndex) + "..]";
									if (t1.indexOf(']') != t1.length-1) {
									  t1 = t1.substring(0, t1.indexOf(']')-2) + t1.substring(t1.indexOf(']')+1);
									}
								}
								
								
								var fixedWidth = $(this).closest('li').width() - 80;
									fixedWidth = fixedWidth > 162 ? fixedWidth : 162;
								if (+oSpan.width() + +$(this).width() > fixedWidth) {
									$(this).css({
										width : fixedWidth - +oSpan.width() + 'px'
									});
								}
								
								oSpan.text(t1);
								$(this).text(t2);
								
								$(this).off('click').on('click',function(event){
									event.preventDefault();
									var link = $(this).attr('href');
									var ret = window.showModalDialog(link+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
									refreshMyWorkEventList(oLi);
								});
							});
					});
			});
		} else {
			openEventListByModal(moduleId);
		}
	}
}
	/* 将除了我的工作事项列表Module外的link阻止其重定向，改成直接弹窗 */
	function openEventListByModal (moduleId) {
		if (typeof moduleId == 'undefined')return;
		var eventList = $('#' + moduleId).find(".event-list>li");
		eventList.each(function (){
			var oA = $(this).find("a");
			oA.off('click').on('click', function (e){
				e.preventDefault();
				var link = $(this).attr('href');
				var ret = window.showModalDialog(link+'&t='+new Date(),null,"dialogTop=0;dialogLeft=0;dialogWidth="+screen.width+";dialogHeight="+screen.height+";help:no;status:no");
				alert("---1---");
				// 这个是全部请求，如果指定了moduleId就可以指定刷新
				reRenderModules(moduleId);
				//initSingleModule('.cont-header', moduleId);
			});
		});
	}

	/* 根据头部标签刷新iframe */
	function refreshIframeByTabLink(link){
		var main = $(".main_middle");
		var iframe = main.find("iframe");
		if (iframe.attr('src') == link) {
			return ;
		} else {
			main.empty();
			var newIframe = $("<iframe></iframe>");
			newIframe.attr({
				src : link ,
				height : "700",
				width : "100%", 
				frameborder: "0"
			});
			newIframe.appendTo(main);
			newIframe.on("load",function (){
				$("#backk").on('click',function(e){
					e.preventDefault();
					backk();
				})
			});
		}
	}

	function module_set_init(){	//模块设置内容初始化
		$.ajax({
			type:"POST",
			dataType:"jsonp",
			jsonp:"callback",
			url:portalurl+"portalInterface_getElementMenuSetJson.do?userId="+userId,
			cache: false,
			async: false,
			success:function(msg){
				$(".canadd-list").html("");
				$(".alreadyadd-list").html("");
				$(".canadd-list").html(msg.unchecked);
				$(".alreadyadd-list").html(msg.checked);
				module_set();
			}
		})
	}
	function text_limit(){
		var objString = $(".work-desc").text();
		var objLength = objString.length;
		var num = $(".work-desc").attr("limit");
		if (objLength>num) {
			$(".work-desc").text(objString.substring(0,num)+"...");
		};
	}
	function module_set_save(){	//模块保存设置
	 	$(".modules-manage-box").fadeOut();
		$(".lay_outer").fadeOut();
		module_set_add();
		setTimeout(function(){
			initContentModule();
		},500);
	}
	/*function delete_module(){	//删除模块
		$(this).parent().parent().parent().parent().hide();
		cont_module_sort();	
	}*/
	function initPageModules(){	//页面内容初始化
		alert("init=1");
		initTopListEvent();	//发起事项列表
		alert("init=2");
		initSystemInfo();	//内容系统列表
		initContentModule();	//内容模块
		backToTop();	//回到顶部
		initPersonInfo();	//头像和岗位职责内容
		
	}
	function getFullPath(obj){
		if(obj){
			if(window.navigator.userAgent.indexOf("MSIE") >= 1){
				obj.select();
				return document.selection.createRange().text;
			}
			return obj.value;
		}
	}
	
	function move_list(){	//我的查询菜单列表移动
		var li_num = $("#tab2 li").length;
		var body_wd = $(".tab2-list").width();
		var ul_wd = li_num*82;
		$("#tab2").css("width",ul_wd);
		if(ul_wd>body_wd){
			$(".prev-btn").css("display","block");
			$(".next-btn").css("display","block");
			$(".content-header").css("padding","0px 20px");
			body_wd = $(".tab2-list").width();
			var ul_left = $("#tab2").position().left;
			var num = Math.floor(body_wd/82);
			var wd = (num-li_num)*82;
			$(".prev-btn").click(function(){
				if(ul_left>wd){
					if(ul_left<0||ul_left==0){
						$("#tab2").css("left",ul_left-82);
						ul_left = $("#tab2").position().left;
					}
					else{
						ul_left = $("#tab2").position().left;
					}
					if(ul_left<wd||ul_left==wd){
						ul_left = $("#tab2").position().left;
					}
				}
			})
			$(".next-btn").click(function(){
				if(ul_left<0){
					$("#tab2").css("left",ul_left+82);
					ul_left = $("#tab2").position().left;
				}
				else{
					ul_left = $("#tab2").position().left;
				}
			})
			$(".next-btn").click(function(){
				var ul_left = parseInt($("#tab2").css("left"));
				if(ul_left<=(body_wd-ul_wd)){
					$("#tab2").css("left",ul_left+82+"px");
					ul_left = $("#tab2").position().left;
				}	
			})		
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
	
	/**
	 * [reRenderModules 重新刷新模块，如果传入moduleId，则只刷新单个module]
	 * @param  {[type]} moduleId [模块ID]
	 * @return {[type]}          [description]
	 */
	function reRenderModules (moduleId) {
		$.ajax({
			type:"POST",
			dataType:"jsonp",
			jsonp:"callback",
			url:portalurl+"portalInterface_getElementMenuJson.do?userId="+userId,
			cache: false,
			async: true,
			success:function(msg){
				var length = typeof msg != 'undefined' ? msg.length : 0,
					i,
					flag = false;
	
				if (typeof moduleId == 'undefined') {
					$(".no-drag").empty();
					$(".drag-part").empty();
					$(".main_content3").empty();
					
					for(i=0; i<length-1; i++){
						typeof msg[i].module1 != 'undefined' && $(".no-drag").append(msg[i].module1);
						typeof msg[i].module2 != 'undefined' && $(".no-drag").append(msg[i].module2);
						typeof msg[i].module3 != 'undefined' && $(".no-drag").append(msg[i].module3);
					}
					
					length > 0 && msg[length-1].module4 && $(".main_content3").append(msg[length-1].module4);
				} else {
					for(i=0; i<length; i++){
						var moduleJson = msg[i];
						for (var key in moduleJson) {
							try {
								var $dom = $(moduleJson[key]);
								if ($dom.attr('id') == moduleId) {
	
									$('#' + moduleId).replaceWith($dom);
									flag = true;
								}
							} catch (e){}
						}
	
						if (flag) break; 
					}
				}
				
	
				loadModuleContent($(".no-drag>.drag"), moduleId);
				//第二次ajax加载我的工作事项
				loadWorkInfo(initializeEventTab);
			}
		});
	}	
	
	$(document).ready(function(){
		portalurl = appurl;
		userId = $(".user").attr("value");
		alert(userId);
		//全局装载所有模块
		initPageModules();
		$(".a-bigger").css("margin-left",-$(".a-bigger").width()/2);
		$(".icon-lock").click(function(){
			$(".pass-reset").fadeIn();
			password_reset();
		})
		/* 模块编辑 */
		$(".icon-edit").click(function(){
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
		
		$(".modules-manage-box>.btn-box>.edit-save-btn").click(module_set_save);
		$(".modules-manage-box>.btn-box>.edit-cancel-btn").click(function(){
			$(".modules-manage-box").fadeOut();
			$(".lay_outer").fadeOut();
		});
	    $(".close-box").click(function(){
			$(".lay_outer").fadeOut();
			$(".a-bigger").fadeOut();    	
	    });
		
		$(".o_l").click(function(event){
			event.preventDefault();
			$(this).on("click",function(){
				biggest();
			})
		});
		
		
		$('#jjlay').click(function(){
		  $('.lay_outer').fadeIn();						  
		  $('.layer').fadeIn();							  
		})
		$(".close_btn").on('click', function () {
			$(".lay_outer").fadeOut();
			$(".layer").fadeOut();
		});
		/* 头像编辑 */
		$(".icon-photo-set").on("click",function(){
			$(".lay_outer").fadeIn();
			$(".person-edit").fadeIn().css("margin-left",-$(".person-edit").width()/2);
		});
		var flag = true;
		$(".choose-img").on("change",function(){
			var link = $(this).val();
			var dot = link.lastIndexOf(".");
			var type = link.substring(dot+1);
			var support =new Array();
			support[0] = "jpg";
			support[1] = "png";
			support[2] = "jpeg";
			support[3] = "bmp";
			$(".img-type").attr("value",type);
			flag = true;
			$.each(support, function (i){
				if (support[i] == type) {
					flag = false;
				}
			});
			
			new uploadPreview({ UpBtn: "up_img", DivShow: "preview", ImgShow: "img-preview" });
		});
		$(".person-edite-save").click(function(){
			if (!flag){
				var options = {
					url: appurl+'/portalInterface_updateEmpInfo.do',
					success: function(d) {
					  if ("success" == d) {
						$(".person-edite-cancel").trigger("click");
						$(".choose-img").data("first", false);
						alert("保存成功");
						initPersonInfo();
					  }
					} 
				};
				$("#uploadFacebook").ajaxSubmit(options);
			} else {
				alert("文件类型不合法，只支持jpg，png，jpeg，bmp");
			}
		})
				
		$(".person-edite-cancel").on("click",function(){
			$(".lay_outer").fadeOut();
			$(".person-edit").fadeOut();
		});
	})