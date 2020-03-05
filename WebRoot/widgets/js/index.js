/**
 * Created by Administrator on 14-4-22.
 */
$(function() {
	/***************************************************************************
	 * 模式之间切换
	 */
	$("#changeModeBtn_s").bind("click", function() {
		$("#old_index").css("display", "none");
		$("#new_index").css("display", "block");
	});
	$("#changeM").bind("click", function() {
		$("#old_index").css("display", "block");
		$("#new_index").css("display", "none");
	});
	/***************************************************************************
	 * 新模式切换
	 */
	$("#backlogMenu>a").bind("click", function() {
		// $(".navTab-panel").on("click","#backlogMenu>a",function(){
		var index = $(this).index();
		$(this).siblings().each(function() {
			var index = $(this).index();
			$(this).removeClass("circle" + (index + 1) + "_open");
		});
		$(this).addClass("circle" + (index + 1) + "_open");
		$(this).parent().css("background-position", (-index * 443) + "px 0");
		$(".backLogWrap>div").addClass("none").eq(index).removeClass("none");
	});
});
	 //表格隔行换色
    $(".dpt_tb tbody tr:odd").addClass("bgH");
	//$("#guideList").nav("#subcontent").init("#subContainer");
	 $("#guideList>li").hover(function(){
         $(this).addClass("hover");
     },function(){
         $(this).removeClass("hover");
     });	
function stableTable(classid,tbid, opt, tbwrap) {
	var _default = {
		theme : 'vsStyle',
		expandLevel : 4,
		beforeExpand : function($treeTable, id) {

		},
		onSelect : function($treeTable, id) {
			window.console && console.log('onSelect:' + id);
		}
	};
	var option = $.extend(_default, opt);
	$(tbid).width($(tbwrap).width() - 30);
	$(tbid).treeTable(option);
	// Highlight selected row
	$("tbody", tbid).on("mousedown", "tr", function() {
		$(classid+" tr.selected").removeClass("selected");
		$(this).addClass("selected");
	});
	// 固定表格头部
	var table = $('<table id="duplicationTB" class="'+classid+' tree_table"></table>'),
	// 头部副本
	head = $(tbid).children("thead").clone();
	table.width($(tbid).width()).append(head);
	// 为了保持没列的宽度一致
	$(tbid).children("tbody").children().eq(0).children().each(
			function(i, e) {
				table.children("thead").children().children().eq(i).attr(
						"style", $(this).attr("style"));
				$(this).css("border-top", "none");
				table.children("thead").children().children().eq(i).css({"border-top":"none",'text-align':'center'});
			});
	$(tbwrap).before(table).height($(tbwrap).height() - 31);
	// $("#dptSite_tb1_wrap").height( $("#dptSite_tb1_wrap").height()-31);
	// 修复边框重合问题
	$(tbid).children("tbody").css("border-top", "none").end().children("thead")
			.remove();
	$(window).resize(function(){
		$(tbid).width($(tbwrap).width() - 30);
		table.width($(tbid).width());
	});

}

function stableTable1(classid,tbid, opt, tbwrap, ids) {
	var _default = {
		theme : 'vsStyle',
		expandLevel : 1,
		beforeExpand : function($treeTable, id) {
			$
		},
		onSelect : function($treeTable, id) {
			window.console && console.log('onSelect:' + id);
		}
	};

	var option = $.extend(_default, opt);
	$(tbid).treeTable(option);
	// Highlight selected row
	$("tbody", tbid).on("mousedown", "tr", function() {
		$(tbid+" tr.selected").removeClass("selected");
		$(this).addClass("selected");
	});
	var flag=true;
	if($("#"+ids).length){
		flag=false;
		$("#"+ids).remove();
	}
	$(tbid).width($(tbwrap).width() - 30);
	// 固定表格头部
	var table = $('<table id="' + ids
			+ '" class="'+classid+' release_table"></table>'),
	// 头部副本
	head = $(tbid).children("thead").clone();
	table.width($(tbid).width()).append(head);
	// 为了保持没列的宽度一致
	$(tbid).children("tbody").children().eq(0).children().each(
			function(i, e) {
				table.children("thead").children().children().eq(i).attr(
						"style", $(this).attr("style"));
				$(this).css("border-top", "none");
				table.children("thead").children().children().eq(i).css({"border-top":"none",'text-align':'center'});
			});
	if(flag){
		$(tbwrap).before(table).height($(tbwrap).height() - 33);
	}
	else{
		$(tbwrap).before(table);
	}
	// $("#dptSite_tb1_wrap").height( $("#dptSite_tb1_wrap").height()-31);
	// 修复边框重合问题
	$(tbid).children("tbody").css("border-top", "none").end().children("thead")
			.remove();
	$(window).resize(function(){
		$(tbid).width($(tbwrap).width() - 30);
		table.width($(tbid).width());
	});

}
