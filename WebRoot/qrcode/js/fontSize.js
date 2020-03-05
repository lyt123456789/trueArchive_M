! function() {
    function initFontSize() {
        var deviceWidth = document.documentElement.clientWidth;
		var deviceHeight = document.documentElement.clientHeight;
        var maxWidth = 750; //按照750的标准计算
        var fontSize = '';
		if (deviceHeight > maxWidth) {
				fontSize = "50px";
			} else {
				fontSize = deviceWidth / maxWidth * 100 + 'px';
			}
        document.documentElement.style.fontSize = fontSize;
    }
    window.onload = function() {
        initFontSize()
    }
}();