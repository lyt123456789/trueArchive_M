$(function(){
	$(".btngroup").hover(function(){
    	$(this).addClass("hover");
	},function(){
    	$(this).removeClass("hover");
	});

	$('#pageContent').height($(window).height()-70);
});