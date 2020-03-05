;(function($) {
	$.fn.GL = {
		globalObj:{},
		getValue:function(keys){
			return keys?this.globalObj[keys]:this.globalObj;
		},
		setValue:function(keys,values){
			this.globalObj[keys] = values;
		}
	}
})(jQuery);