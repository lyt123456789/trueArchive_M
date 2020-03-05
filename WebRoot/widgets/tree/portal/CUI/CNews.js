// JavaScript Document
var News = Class.create();

News.prototype = {
	initialize : function (params) {
		this.params=params;
		var wc = this;
		wc.parent = null;
	},	
	open : function () {
		var wc = this;
		wc.parent.content.innerHTML = "loading";
		jQuery.post(this.params,function(data){
			wc.parent.content.innerHTML =jQuery(document).parseXML(data);
		});
	}
	
};
News.loaded = true;