function createWindow(url,openPlugIn,isMax){
    var remote = top.window.nodeRequire('remote');
    var browerwindow = remote.require('browser-window');
    var winProps = {};

    winProps.width = '800';
    winProps.height = '600';
    if(openPlugIn){
        winProps['web-preferences'] = {'plugins':true};
    }
    var win = new browerwindow(winProps);
    if(isMax){
        win.maximize();
    }
    win.loadUrl(url);
    return win;
}


function changeLink(obj){
    $(obj).find('a').each(function(i){
		if(!$(this).attr('maxlink')){
		    var url = $(this).attr('href');
            $(this).attr('href','#');
	        $(this).click(function(event){
		        event.preventDefault();
			    createWindow(url,true,true);
		    });
        }
	});
}