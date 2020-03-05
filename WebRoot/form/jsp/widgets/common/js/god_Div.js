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
        if(typeof(arguments[i])=='string'){this.g(arguments[i]).style.display='';} 
        else if(typeof(arguments[i].length)!='undefined'){var arr=arguments[i];for(var j=0;j<arr.length;j++){this.showDiv(arr[j]);};}
        else if(typeof(arguments[i])=='object'){arguments[i].style.display='';}
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
/*
 * 框架内部方法，用于根据样式对象设置样式
 * */
god.prototype.setDivStyle=function(jsobj){
    var obj=this.g(jsobj.id);
    for(var o in jsobj){
        obj.style[o]=jsobj[o];
    }               
};
/*
 * 居中显示层
 * centerDivid:居中显示层id
 * shadeDivid:背景层id(非必须)
 * */
god.prototype.showDivInCenter=function(centerDivid,isShade){
      if(isShade){
    	 var shadeObj=this.g('god_bg_hide_div');
    	 if(!shadeObj){
    		 var newDiv = document.createElement("div");
    		 newDiv.id='god_bg_hide_div';
    		 document.body.appendChild(newDiv);
    	 }
    	 this.g('god_bg_hide_div').style.display='';//先可见后才能获得高度和宽度，用作计算
         //document.documentElement.scrollHeight 网页内容全文高(包括滚动条下高度)(标准)
         //document.documentElement.clientHeight 浏览器可见高度(标准)
         //document.body.offsetHeight(非标准)
         //document.body.clientHeight(非标准)
         //document.body.scrollHeight(非标准)
    	 var h=0;var w=0;
    	 with(document.documentElement){
    		 h=scrollHeight>clientHeight?scrollHeight:clientHeight;
             w=scrollWidth>clientWidth?scrollWidth:clientWidth;
    	 }
         var shadeJsObj={
        		 id:'god_bg_hide_div',
        		 top:'0px',
        		 left:'0px',
        		 height:h+'px',
        		 width:w+'px',
        		 zIndex:20000,
        		 position:'absolute',
        		 border:'0px',
        		 padding:'0px',
        		 margin:'0px',
        		 filter:'alpha(opacity=50)',
        		 opacity:'0.5',
        		 backgroundColor:'#A0A0A0'
         };
         this.setDivStyle(shadeJsObj);
      }
      var centerObj=this.g(centerDivid);
      centerObj.style.display='';
      var centerObjPos=this.getObjPos(centerObj);
      //获得高度，如果内容不满屏，获得屏幕的高度，如果内容超过屏幕高度，则获得内容总高度
      //<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
      //scrollHeight、offsetHeight、clientHeight(body、documentElement) 
      //正文高度
      var all_h=document.documentElement.clientHeight;
      var all_w=document.documentElement.clientWidth;
      var t=(all_h-centerObjPos.height)/2+document.documentElement.scrollTop;
      var l=(all_w-centerObjPos.width)/2+document.documentElement.scrollLeft;
      if(t<0)t=0;//防止出屏 
      this.setDivStyle({id:centerDivid,top:t+'px',left:l+'px',zIndex:30000,position:'absolute'});  
};
/*
 * 关闭居中显示层
 * centerDivid:居中显示层id
 * */
god.prototype.hideDivInCenter=function(centerDivid){
   this.hideDiv(centerDivid,'god_bg_hide_div');
};
/*
 * 依赖标签对象显示层
 * onObjId:依赖对象id
 * opObjId:显示对象id
 * pos:显示方位(bottom、top、left、right)
 * index:相对于依赖对象某方位上5个顶点的位置(1、2、3、4、5)
 * zindex:
 * */
god.prototype.showDivWidthObj=function(onObjId,opObjId,pos,index,zindex){
    var onObj=typeof(onObjId)=='object'?onObjId:this.g(onObjId);
    var opObj=typeof(opObjId)=='object'?opObjId:this.g(opObjId);
    opObj.style.display='';
    opObj.style.position='absolute';//非常重要，取消目标div原有的定位，获取准确的依赖div定位,防止相互影响
    var posObj=this.getWithObjPos(onObj,opObj,pos,index);
    //alert(posObj.left+','+posObj.top);
    this.setDivStyle({id:opObjId,top:posObj.top+'px',left:posObj.left+'px',zIndex:zindex,position:'absolute'});
};
/*
 * 框架内部方法，用于根据样式值字符串获得相应的数字用于计算(例如：'10px'--10)
 * */
god.prototype.chat=function(s){return parseInt(s.replace(/\D/g,''),10);};
/*
 * 框架内部方法，用于获取依赖标签对象显示层的位置
 * */
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
};