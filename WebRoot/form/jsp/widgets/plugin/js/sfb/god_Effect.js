/*层滑动相关开始*/
/*
参数：obj 为对象
	  obj的属性 id:div层的id
		zIndex:层叠
		startX:层起始时x坐标
		startY:层起始时y坐标
		startW:层起始时width
		startH:层起始时height
		endX:层结束后x坐标
		endY:层结束后y坐标
		endW:层结束后width
		endH:层结束后height
		isTrans:是否透明显示
		isHiddenHtml:是否滑动时隐藏html
		millsecond:滑动总毫秒数
		rateSecond:每隔多少毫秒改变状态
用法：g.divMove(o);
*/
god.prototype.divMove=function(obj){
	this.obj=obj;
	this.success=function(){};
	this.ok=function(){
		//强制去除正在运行中的定时器，防止多次频繁操作出现异常
		if(g.div_change!=null&&g.div_change.interval!=null){
			clearInterval(g.div_change.interval);
		}
		var obj=this.obj;
		var o=g.g(obj.id);if(o==null){alert('操作对象不存在,无法继续运行!');return};
		var sx=obj.startX;
		var sy=obj.startY;
		var ex=obj.endX;
		var ey=obj.endY;
		var zindex=obj.zIndex;
		var sen=obj.millsecond;//总毫秒数
		var ras=obj.rateSecond;//每隔多少秒运行
		var rate=Math.round(sen/ras);//频率,需要运行的总次数
		var temp_x=(ex-sx)/rate; //每一次x坐标变动量
		var temp_y=(ey-sy)/rate; //每一次y坐标变动量
		
		var sw=obj.startW;
		var sh=obj.startH;
		var ew=obj.endW;
		var eh=obj.endH;
		var temp_w=(ew-sw)/rate;//每一次width变动量
		var temp_h=(eh-sh)/rate;//每一次height变动量

		var temp_tans_ie=100/rate;
		var temp_tans_firfox=1/rate;
		var isTans=obj.isTrans;
		var isHidden=obj.isHiddenHtml;
		
		var temp_rate=0;
		//把层定位到初始位置,并显示出来
		g.setDivStyle({id:obj.id,top:sy+'px',left:sx+'px',width:sw+'px',height:sh+'px',zIndex:zindex});
		g.showDiv(obj.id);
		//使用setinterval动态改变层的状态
		if(g.div_change==null){g.div_change={};}
		if(isHidden){
			g.div_change.hiddenHtml=o.innerHTML;
			o.innerHTML='&nbsp';
		}
		g.div_change.success=this.success;
		g.div_change.interval=setInterval(
			function(){
				temp_rate++;
				o.style.left=(sx+temp_rate*temp_x)+'px';
				o.style.top=(sy+temp_rate*temp_y)+'px';
				o.style.width=(sw+temp_rate*temp_w)+'px';
				o.style.height=(sh+temp_rate*temp_h)+'px';
				if(isTans){
					o.style.filter='alpha(opacity='+temp_rate*temp_tans_ie+')';
					o.style.opacity=temp_rate*temp_tans_firfox+'';
				}
				if(temp_rate>rate){
					clearInterval(g.div_change.interval);
					o.style.left=ex+'px';
					o.style.top=ey+'px';
					o.style.width=ew+'px';
					o.style.height=eh+'px';
					if(isTans){
						o.style.filter='alpha(opacity=100)';
						o.style.opacity='1';
					}
					if(isHidden){
						o.innerHTML=g.div_change.hiddenHtml;
					}
					g.div_change.success();
				}
			},
			ras
		);
	};
};
/*层滑动相关结束*/


/*层拖动相关开始*/

/*
 		var d=new g.drag([g.g('div123_1'),g.g('div123')],g.g('div111'));
       d.onmousedown=function(o,p){
            o.style.background='green';
            if(p){
                p.style.background='white';
                p.style.borderColor='black';
            }
            
       };
       d.onmousemove=function(o,p){
            
       };       
       d.onmouseup=function(o,p){
            o.style.background='red';
            if(p){
                p.style.background='yellow';
                p.style.borderColor='pink';
            }
       };
       d.ok();
 */
god.prototype.drag=function(){
        this.arguments=arguments;//必须
    this.ok=function(){ 
        for(var i=0;i<this.arguments.length;i++){
            //参数合法性判断
            var C=this.arguments[i];               
            if(!(C.push)){C=[C];} 
            o=g.G(C[0]);
            if(!o)continue;                            
            o.dragObj=this; //设置drag对象
            o.parentObj=g.G(C[1]);
            o.activemove=null;
            o.activeup=null;
            if(o.addEventListener){
                o.addEventListener("mousedown",this.down.create(o),false);
            }else{//IE附加事件时，无法将方法对象this作为参数传递，因此使用create方法强制绑定   
                o.attachEvent("onmousedown",this.down.create(o));
            }
        }
        this.ok=null;                   
    };
    this.down.create=this.create;
    this.move.create=this.create;
    this.up.create=this.create;   
};
god.prototype.drag.prototype.down=function(e){ 
    var d=this.dragObj; //drag对象
    e=e||window.event;
    if((e.which && (e.which!=1))||(e.button && (e.button!=1)))return;//去除自定义事件影响
    this.activeObj=this.parentObj||this; 
    this.pos={
        ox:(e.pageX||(e.clientX+document.documentElement.scrollLeft))-parseInt(g.getObjPos(this.activeObj).left,10)+g.chat(g.getObjCurrentStyle(this.activeObj).borderLeftWidth),
        oy:(e.pageY||(e.clientY+document.documentElement.scrollTop))-parseInt(g.getObjPos(this.activeObj).top,10)+g.chat(g.getObjCurrentStyle(this.activeObj).borderTopWidth),
        x:0,
        y:0,
        zIndex:g.getObjCurrentStyle(this.activeObj)['zIndex']
    };
    this.activemove=d.move.create(this);
    this.activeup=d.up.create(this);
    if(document.addEventListener){ 
        document.addEventListener("mousemove",this.activemove,false);
        document.addEventListener("mouseup",this.activeup,false);
    }else{//IE
        document.attachEvent("onmousemove",this.activemove);
        document.attachEvent("onmouseup",this.activeup);
    }
    this.activeObj.style.zIndex=999999;
    d.onmousedown(this,this.parentObj);
    d.stop(e);
};
god.prototype.drag.prototype.move=function(e){               
    var d=this.dragObj; //drag对象
    e=e||window.event;
    if((e.which && (e.which!=1))||(e.button && (e.button!=1)))return;
    this.activeObj.style.top=(e.pageY||(e.clientY+document.documentElement.scrollTop))-parseInt(this.pos.oy,10)+'px'; 
    this.activeObj.style.left=(e.pageX||(e.clientX+document.documentElement.scrollLeft))-parseInt(this.pos.ox,10)+'px';
    d.onmousemove(this,this.parentObj,e);      
    d.stop(e);
};
god.prototype.drag.prototype.up=function(e){        
    var d=this.dragObj;//drag对象        
    e=e||window.event;
    if((e.which && (e.which!=1))||(e.button && (e.button!=1)))return;
    if(document.removeEventListener){
        document.removeEventListener("mousemove",this.activemove,false); 
        document.removeEventListener("mouseup",this.activeup,false);
    }else{
        document.detachEvent("onmousemove",this.activemove); 
        document.detachEvent("onmouseup",this.activeup);
    }
    this.activeObj.style.zIndex=this.pos.zIndex;
    d.onmouseup(this,this.parentObj);
    d.stop(e);
};
/*
    阻止拖动过程中事件冒泡和标签默认行为
*/
god.prototype.drag.prototype.stop=function(e){
   if(e.stopPropagation){e.stopPropagation();}else{e.cancelBubble=true;} 
   if(e.preventDefault){e.preventDefault();}else{e.returnValue=false;} 
};
/*
    把方法绑定到相应的对象上
*/
god.prototype.drag.prototype.create=function(bind){
    var A=bind,B=this;
    return function(e){return B.apply(A,[e]);}
};
god.prototype.drag.prototype.onmousedown=function(o,p){};
god.prototype.drag.prototype.onmousemove=function(o,p,e){};
god.prototype.drag.prototype.onmouseup=function(o,p){}; 
/*层拖动相关结束*/