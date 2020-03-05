
! function() {
    function initFontSize() {
        var deviceWidth = document.documentElement.clientWidth;
        var deviceHeight = document.documentElement.clientHeight;
        var maxWidth = 750; //按照750的标准计算
        var fontSize = '';
        console.log(window)
        if (window.orientation === 90 || window.orientation === -90 ){
            if (deviceHeight > maxWidth) {
                fontSize = "50px";
            } else {
                fontSize = deviceHeight / maxWidth * 100 + 'px';
            }
        }
        if (window.orientation === 180 || window.orientation === 0 ){
            if (deviceWidth > maxWidth) {
                fontSize = "50px";
            } else {
                fontSize = deviceWidth / maxWidth * 100 + 'px';
            }
        }
        document.documentElement.style.fontSize = fontSize;
    }

    function initDevice() {
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        var isIphoneX = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/) && (screen.height == 812 && screen.width == 375);
        if (isiOS) {
            if (isIphoneX) {
                if (window.orientation === 90 || window.orientation === -90 ){
                    document.body.className = "";
                    document.body.style.paddingLeft = '5%';
                    document.querySelector('.wrapper').style.width = '95%';
                }
                if (window.orientation === 180 || window.orientation === 0) {
                    document.body.className = "app-iosx";
                    document.body.style.paddingLeft = '0';
                    document.querySelector('.wrapper').style.width = '100%';
                }
            } else {
                if (window.orientation === 90 || window.orientation === -90 ){
                    document.body.className = "";
                }
                if (window.orientation === 180 || window.orientation === 0) {
                    document.body.className = "app-ios";
                }
            }
        } else {
            document.body.className = ""
        }
    }
    
   window.onload = function() {
        initFontSize()
        initDevice()
   }
    
    window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", function() {
        initFontSize()
        initDevice()
    }, false);
}();