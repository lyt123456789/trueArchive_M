$(document).ready(function() {

    $("#side-menu").metisMenu();

    $("#side-menu img").each(function(){
		if($(this).attr("src")==""){
		    $(this).hide();
		}
	});
 
})