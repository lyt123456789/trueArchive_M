/*
    参数支持id、对象和数组任何混合格式
    高级应用,重载
    用法:g.hideDiv('id1','id2'...);
        g.hideDiv(obj1,obj2...);
        g.hideDiv(['id1','id2'...]);
        g.hideDiv([obj1,obj2...]);
        g.hideDiv('id1',obj2,['id3',obj4]);
*/
god.prototype.hideDiv=function(){
    for(var i=0;i<arguments.length;i++){
        if(typeof(arguments[i])=='string'){this.g(arguments[i]).style.display='none';}
        else if(typeof(arguments[i].length)!='undefined'){var arr=arguments[i];for(var j=0;j<arr.length;j++){this.hideDiv(arr[j]);};}
        else if(typeof(arguments[i])=='object'){arguments[i].style.display='none';}
    }  
};
/*
    参数支持id、对象和数组任何混合格式
    高级应用,重载
    用法:g.showDiv('id1','id2'...);
        g.showDiv(obj1,obj2...);
        g.showDiv(['id1','id2'...]);
        g.showDiv([obj1,obj2...]);
        g.showDiv('id1',obj2,['id3',obj4]);
*/
god.prototype.showDiv=function(){
    for(var i=0;i<arguments.length;i++){
        if(typeof(arguments[i])=='string'){this.g(arguments[i]).style.display='block';}
        else if(typeof(arguments[i].length)!='undefined'){var arr=arguments[i];for(var j=0;j<arr.length;j++){this.showDiv(arr[j]);};}
        else if(typeof(arguments[i])=='object'){arguments[i].style.display='block';}
    } 
};
/*
    参数支持id、对象和数组任何混合格式
    高级应用,重载
    用法:g.disObj('none','id1','id2'...);
        g.disObj('none',obj1,obj2...);
        g.disObj('none',['id1','id2'...]);
        g.disObj('none',[obj1,obj2...]);
        g.disObj('none','id1',obj2,['id3',obj4]);
*/
god.prototype.disObj=function(d,idArr){
    for(var i=1;i<arguments.length;i++){
        if(typeof(arguments[i])=='string'){this.g(arguments[i]).style.display=arguments[0];}
        else if(typeof(arguments[i].length)!='undefined'){var arr=arguments[i];for(var j=0;j<arr.length;j++){this.disObj(arguments[0],arr[j]);};}
        else if(typeof(arguments[i])=='object'){arguments[i].style.display=arguments[0];}
    }
};

god.prototype.setDivStyle=function(jsobj){
    var obj=this.g(jsobj.id);
    for(var o in jsobj){
        obj.style[o]=jsobj[o];
    }               
}
god.prototype.showDivInCenter=function(centerDivid,shadeDivid){
      if(shadeDivid){
         var shadeObj=this.g(shadeDivid);
         shadeObj.style.display='block';
         //document.documentElement.scrollHeight 网页内容全文高(包括滚动条下高度)
         //document.documentElement.clientHeight 浏览器可见高度
         var h=document.body.scrollHeight>document.body.clientHeight?document.body.scrollHeight:document.body.clientHeight;
         var w=document.body.scrollWidth>document.body.clientWidth?document.body.scrollWidth:document.body.clientWidth;
         var shadeJsObj={id:shadeDivid,top:'0px',left:'0px',height:h+'px',width:w+'px',zIndex:200};
         this.setDivStyle(shadeJsObj);
      }
      var centerObj=this.g(centerDivid);
      centerObj.style.display='block';
      var centerObjPos=this.getObjPos(centerObj);
      //获得高度，如果内容不满屏，获得屏幕的高度，如果内容超过屏幕高度，则获得内容总高度
      //<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
      //scrollHeight、offsetHeight、clientHeight(body、documentElement) 
      //正文高度
      var all_h=0;
      var all_w=0;
      if(document.all){
    	  all_h=document.body.offsetHeight;
    	  all_w=document.body.offsetWidth;
      }else{
    	  all_h=document.body.clientHeight;
    	  all_w=document.body.clientWidth;
      }
      var t=(all_h-centerObjPos.height)/2+document.body.scrollTop;
      var l=(all_w-centerObjPos.width)/2+document.body.scrollLeft;
      this.setDivStyle({id:centerDivid,top:t+'px',left:l+'px',zIndex:8252});  
};
god.prototype.showDivWidthObj=function(onObjId,opObjId,pos,index,zindex){
    var onObj=this.g(onObjId);
    var opObj=this.g(opObjId);
    opObj.style.display='block';
    var posObj=this.getWithObjPos(onObj,opObj,pos,index);
    this.setDivStyle({id:opObjId,top:posObj.top+'px',left:posObj.left+'px',zIndex:zindex});
}    
god.prototype.getWithObjPos=function(onObj,opObj,pos,index){
    var t=0;var l=0;
    var onJsObj=this.getObjPos(onObj);
    var opJsObj=this.getObjPos(opObj);
    var border_top=this.chat(this.getObjCurrentStyle(onObj).borderTopWidth);
    var border_left=this.chat(this.getObjCurrentStyle(onObj).borderLeftWidth);
	switch(pos){
		case 'bottom':
	        //border affect location seriously
	        t=onJsObj.top+onJsObj.height-border_top;
	        switch(index){
		      case 1:l=onJsObj.left-border_left-opJsObj.width;break;
		      case 2:l=onJsObj.left-border_left+(onJsObj.width-opJsObj.width);break;
		      case 3:l=onJsObj.left-border_left-(opJsObj.width-onJsObj.width)/2;break;
		      case 4:l=onJsObj.left-border_left;break;
		      case 5:l=onJsObj.left-border_left+onJsObj.width;break;
	        }
	        break;
		case 'top':
	        t=onJsObj.top-opJsObj.height-border_top;
	        switch(index){
		      case 1:l=onJsObj.left-border_left-opJsObj.width;break;
		      case 2:l=onJsObj.left-border_left-opJsObj.width+onJsObj.width;break;
		      case 3:l=onJsObj.left-border_left-(opJsObj.width-onJsObj.width)/2;break;
		      case 4:l=onJsObj.left-border_left;break;
		      case 5:l=onJsObj.left-border_left+onJsObj.width;break;
	        }
	        break;
		case 'left':
	        l=onJsObj.left-opJsObj.width-border_left;
	        switch(index){
		      case 1:t=onJsObj.top-border_top-opJsObj.height;break;
		      case 2:t=onJsObj.top-border_top-opJsObj.height+onJsObj.height;break;
		      case 3:t=onJsObj.top-border_top-(opJsObj.height-onJsObj.height)/2;break;
		      case 4:t=onJsObj.top-border_top;break;
		      case 5:t=onJsObj.top-border_top+onJsObj.height;break;
	        }    
	        break;
		case 'right':
	        l=onJsObj.left+onJsObj.width-border_left;
	        switch(index){
		      case 1:t=onJsObj.top-border_top-opJsObj.height;break;
		      case 2:t=onJsObj.top-border_top-opJsObj.height+onJsObj.height;break;
		      case 3:t=onJsObj.top-border_top-(opJsObj.height-onJsObj.height)/2;break;
		      case 4:t=onJsObj.top-border_top;break;
		      case 5:t=onJsObj.top-border_top+onJsObj.height;break;
	        }
	        ;break;
	}
	return {top:t,left:l};
}