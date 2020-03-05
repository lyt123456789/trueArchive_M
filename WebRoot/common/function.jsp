<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script>
if($("table.list").length>0){
	$("table.list", document).cssTable();
}
if ($.fn.combox) $("select.combox",document).combox();
$("input[type=text]").addClass("texts");
$("a[target=navTab]", document).each(function(){  
	$(this).click(function(event){
		var $this = $(this);
		var title = $this.attr("title") || $this.text();
		var tabid = $this.attr("rel") || "_blank";
		var fresh = eval($this.attr("fresh") || "true");
		var external = eval($this.attr("external") || "false");
		var url = unescape($this.attr("href")).replaceTmById($(event.target).parents(".unitBox:first"));
		DWZ.debug(url);
		if (!url.isFinishedTm()) {
			alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
			return false; 
		}
		parent.navTab.openTab(tabid, url,{title:title, fresh:fresh, external:external});
		event.preventDefault(); 
	});
});
/* $("html").theme({themeBase:"${ctx}/dwz/themes"});  */ 
</script> 