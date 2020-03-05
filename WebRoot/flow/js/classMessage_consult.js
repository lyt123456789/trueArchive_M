  function CLASS_MSN_MESSAGE_CONSULT(id,width,height,caption,title,message,target,action){ 
      this.id       = id; 
      this.title    = title; 
      this.caption = caption; 
      this.message = message; 
      this.target   = target; 
      this.action   = action; 
      this.width    = width?width:200; 
      this.height   = height?height:120; 
      this.timeout = 150; 
      this.speed    = 20; 
      this.step     = 1; 
      this.right    = screen.width -1; 
      this.bottom   = screen.height; 
      this.left     = this.right - this.width; 
      this.top      = this.bottom - this.height; 
      this.timer    = 0; 
      this.pause    = false;
      this.close    = false;
      this.autoHide = true;
   } 
   /**
   * 消息隐藏方法 
   */ 
   CLASS_MSN_MESSAGE_CONSULT.prototype.hide = function(){ 
      if(this.onunload()){ 
          var offset = this.height>this.bottom-this.top?this.height:this.bottom-this.top; 
          var me = this;
          if(this.timer>0){   
              window.clearInterval(me.timer); 
          } 
  
          var fun = function(){ 
              if(me.pause==false||me.close){
                  var x = me.left; 
                  var y = 0; 
                  var width = me.width; 
                  var height = 0; 
                  if(me.offset>0){ 
                      height = me.offset; 
                  } 
       
                  y = me.bottom - height; 
                  if(y>=me.bottom){ 
                      window.clearInterval(me.timer); 
                      me.Pop.hide(); 
                  } else { 
                      me.offset = me.offset - me.step; 
                  } 
                  me.Pop.show(x,y,width,height);    
              }             
          } 
          this.timer = window.setInterval(fun,this.speed)      
      } 
   } 
  
   /** 
   * 消息卸载事件，可以重写 
   */ 
   CLASS_MSN_MESSAGE_CONSULT.prototype.onunload = function() { 
        return true; 
   } 
   /**
   * 消息命令事件，要实现自己的连接，请重写它 
   */ 
   CLASS_MSN_MESSAGE_CONSULT.prototype.oncommand = function(){ 
        this.close = true;
        this.hide(); 
        window.open("http://www.lost63.com");
   } 
   /** 
   * 消息显示方法 
   */ 
   CLASS_MSN_MESSAGE_CONSULT.prototype.show = function(){ 
        var oPopup = window.createPopup(); //IE5.5+ 
        this.Pop = oPopup; 
  
        var w = this.width; 
        var h = this.height; 
  
        var str = "<div  style='border-right: #455690 1px solid; border-top: #a6b4cf 1px solid; z-index: 99998; left: 0px; border-left: #a6b4cf 1px solid; width: " + w + "px; border-bottom: #455690 1px solid; position: absolute; top: 0px; height: " + h + "px; background-color: #c9d3f3'>" 
          str += "<table style='border-top: #ffffff 1px solid; border-left: #ffffff 1px solid' cellspacing=0 cellpadding=0 width='100%' bgcolor=#cfdef4 border=0>"
           
          str += "<tr>" 
          str += "<td style='font-size: 12px;color: #0f2c8c' width=30 height=24></td>" 
          str += "<td style='padding-left: 4px; font-weight: normal; font-size: 12px; color: #1f336b; padding-top: 4px' valign=center width='100%'>" + this.title + "</td>" 
          str += "</tr>"
          str += "<tr>" 
          /*height：设置里面内容区域的高度 */
          str += "<td style='padding-right: 1px;padding-bottom: 1px' colspan=3 height=" + (h-50) + ">" 
          str += "<div style='border-right: #b9c9ef 1px solid; padding-right: 8px; border-top: #728eb8 1px solid; padding-left: 8px; font-size: 12px; padding-bottom: 8px; border-left: #728eb8 1px solid; width: 100%; color: #1f336b; padding-top: 8px; border-bottom: #b9c9ef 1px solid; height: 100%'>" + this.caption + "<br><br>" 
          str += "<div style='word-break: break-all' align=left>" + this.message + "</div>" 
          str += "</div>" 
          str += "</td>" 
          str += "</tr>"  
          str += "<tr align=center>"
          str += "<td colspan=3>"
          str += "<div style='padding: 2 0 2 0;'>"
          str += "<span id='buttonShow'><input type='button' title=我知道了  value=我知道了 style='width:60px; height:20px; border-right: #002D96 1px solid; padding-right: 2px; border-top: #002D96 1px solid; padding-left: 2px; FONT-SIZE: 12px; filter: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); border-left: #002D96 1px solid; CURSOR: hand; color: black; padding-top: 2px; border-bottom: #002D96 1px solid;'></span>"
          str += "&nbsp;<span id='buttonReply'><input type='button' title=回复  value=回复 style='width:60px; height:20px; border-right: #002D96 1px solid; padding-right: 2px; border-top: #002D96 1px solid; padding-left: 2px; FONT-SIZE: 12px; filter: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); border-left: #002D96 1px solid; CURSOR: hand; color: black; padding-top: 2px; border-bottom: #002D96 1px solid;'></span>"
          str += "&nbsp;&nbsp;<span id='buttonClose'><input type='button' title=关闭  value=关闭 style='width:60px; height:20px; border-right: #002D96 1px solid; padding-right: 2px; border-top: #002D96 1px solid; padding-left: 2px; FONT-SIZE: 12px; filter: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#FFFFFF, EndColorStr=#9DBCEA); border-left: #002D96 1px solid; CURSOR: hand; color: black; padding-top: 2px; border-bottom: #002D96 1px solid;'></span>" 
          str += "</div>"
          str += "</td>"
          str += "</tr>"
          str += "</table>" 
          str += "</div>"
        oPopup.document.body.innerHTML = str; 
        this.offset = 0; 
        var me = this; 
  
        oPopup.document.body.onmouseover = function(){me.pause=true;}
        oPopup.document.body.onmouseout = function(){me.pause=true;}
  
        var fun = function(){ 
            var x = me.left; 
            var y = 0; 
            var width      = me.width; 
            var height     = me.height;
  
                if(me.offset>me.height){ 
                    height = me.height; 
                } else { 
                    height = me.offset; 
                }
  
            y = me.bottom - me.offset; 
            if(y<=me.top){ 
                //me.timeout--; 
                if(me.timeout==0){ 
                    window.clearInterval(me.timer); 
                    if(me.autoHide){
                        me.hide(); 
                    }
                } 
            } else { 
                me.offset = me.offset + me.step; 
            } 
            me.Pop.show(x,y,width,height);   
        } 
  
        this.timer = window.setInterval(fun,this.speed)      
   
    //"确认"关闭事件
   
        var buttonClose = oPopup.document.getElementById("buttonClose"); 
        buttonClose.onclick = function(){ 
            me.close = true;
            me.hide(); 
        }
        
        var buttonShow = oPopup.document.getElementById("buttonShow"); 
        var args = this.target;
        buttonShow.onclick = function(){ 
        	var id = args[0];
        	$.ajax({   
				url : '${ctx }/table_setRead.do',
				type : 'POST',   
				cache : false,
				global:false,
				error : function() {  
					alert('AJAX调用错误(table_setRead.do)');
				},
				data : {
					'id':id
				},    
				success : function(msg) {  
				}
			});
            me.close = true;
            me.hide(); 
        }
        
        var buttonReply = oPopup.document.getElementById("buttonReply"); 
        buttonReply.onclick = function(){ 
        	var id = args[0];
        	var userId = args[1];
        	var userName = args[2];
        	art.dialog({
				title: '回复协商消息',
				lock: true,
			    content: '<'+'iframe id="consultReplyFrame" name="consultReplyFrame" src="${ctx}/table_replyConsult.do?id='+id+'" height="300" width="500"></'+'iframe>',
			    id: 'EF893K',
			    button: [{name:'发送',callback:function(){
				    	var consultReplyFrame = document.getElementById("consultReplyFrame").contentWindow;
				    	var message = consultReplyFrame.document.getElementById("message").value;
				    	if(message==null || message==''){
							alert('请填写回复内容');
							return false;
				    	}
				    	$.ajax({   
							url : '${ctx }/table_sendReply.do',
							type : 'POST',   
							cache : false,
							global:false,
							error : function() {  
								alert('AJAX调用错误(table_sendReply.do)');
							},
							data : {
								'msg':message,'relateId':id,'userId':userId,'userName':userName
							},    
							success : function(msg) {  
								alert('发送成功');
							}
						});
						
				    }},{name:'取消'}]
			});
            me.close = true;
            me.hide(); 
        }
   } 
   /* 
   * 设置速度方法 
   */ 
   CLASS_MSN_MESSAGE_CONSULT.prototype.speed = function(s){ 
      var t = 20; 
      try { 
          t = praseInt(s); 
      } catch(e){} 
      this.speed = t; 
   } 
   /** 
   * 设置步长方法 
   */ 
   CLASS_MSN_MESSAGE_CONSULT.prototype.step = function(s){ 
      var t = 1; 
      try { 
          t = praseInt(s); 
      } catch(e){} 
      this.step = t; 
   } 
  
   CLASS_MSN_MESSAGE_CONSULT.prototype.rect = function(left,right,top,bottom){ 
      try { 
          this.left        = left    !=null?left:this.right-this.width; 
          this.right        = right    !=null?right:this.left +this.width; 
          this.bottom        = bottom!=null?(bottom>screen.height?screen.height:bottom):screen.height; 
          this.top        = top    !=null?top:this.bottom - this.height; 
      } catch(e){} 
   }