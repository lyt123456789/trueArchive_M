;define(function(require,exports,module){
	var $=require('lib/animate');
	var	navTab={
			componentBox:null,
			navPlane:null,
			contentPlane:null,
			settings:{
				id:'navTab',
				tabHeader:'.tabsPageHeader',
				tabContent:'.tabsPageContent',
				tabHScroll:'.tabsPageScroll',
				tabNav:'.item',
				tabPage:'.pages',
				closeBtn:'a.close',
				swapBtn:'a:first',
				selected:'selected',
				loadingTimeout:5000,
				index:0,
				hideCount:0
			},
			init:function(options){
				var $this=this;
				this.settings=$.extend(this.settings,options);
				
				this.componentBox=$('#'+this.settings.id);
				this.navPlane=this.componentBox.find(this.settings.tabHeader);
				this.contentPlane=this.componentBox.find(this.settings.tabContent);
				this.scrollPlane=this.componentBox.find(this.settings.tabHScroll);
				this._getPages().hide();
				this.bindEvent();
				this.switchTab(this.settings.index);
			},
			bindEvent:function(){
				var $this=this;
				this._getNavs().each(function(index){
					var rel=$(this).attr('tid');
					$(this).find($this.settings.swapBtn).unbind('click').bind('click',function(event){
						//$this.switchTab(index);
						$this.switchTab($this._indexId(rel));
					});
					$(this).find($this.settings.closeBtn).unbind('click').bind('click',function(){
						//$this.removeTab(index);
						$this.removeTab($this._indexId(rel));
					}).hover(function(){
						$(this).addClass('hover');
					},function(){
						$(this).removeClass('hover');
					});
				});
				/**
				 * 2012-01-31
				 * 增加导航的滚动效果
				 **/
				this.scrollPlane.bind('mouseover',function(){
					var pw=$this.navPlane.width(),sw=$(this).width();
					var nl=$this.navPlane.css('left').replace('px','');
					/**导航宽于容器时**/
					if(pw>sw){
						ls=pw-sw;
						//console.log('ls: ',ls);
						if(nl<0){
							//console.log('fnl: ',nl);
							$(this).find('.aquo:first').fadeIn();
						}
						if(Math.abs(nl)>=Math.abs(ls)){
							$(this).find('.aquo:last').fadeOut();
						}else{
							$(this).find('.aquo:last').fadeIn();
						}
					}else if(nl<0){
							//console.log('fnl: ',nl);
							$(this).find('.aquo:first').fadeIn();
					}
				}).bind('mouseleave',function(){
					$(this).find('.aquo').fadeOut();
				});
				
				this.scrollPlane.find('.aquo:first').unbind('click').bind('click',function(){
					$this.settings.hideCount--;
					if($this.settings.hideCount<0){
						$(this).fadeOut();
						$this.navPlane.css('left',0);
						$this.settings.hideCount=0;
					}else{
						var nl=$this.navPlane.css('left').replace('px','');
						var vx=$this.navPlane.find('.item').eq($this.settings.hideCount).width()+2;
						$this.navPlane.cssFlash('left',(Math.abs(nl)-Math.abs(vx))*-1);
					};
				});
				this.scrollPlane.find('.aquo:last').unbind('click').bind('click',function(){
					if($this.settings.hideCount>=$this.navPlane.find('.item').size()-1){
						$(this).fadeOut();
						$this.navPlane.css('left',0);
						$this.settings.hideCount=$this.navPlane.find('.item').size()-1;
					}else{
						var nl=$this.navPlane.css('left').replace('px','');
						var pw=$this.navPlane.width(),sw=$this.scrollPlane.width();
						ls=pw-sw;
						if(Math.abs(nl)>=Math.abs(ls)){
							$(this).fadeOut();
							$this.navPlane.css('left',nl);
							return;
						}
						var vx=$this.navPlane.find('.item').eq($this.settings.hideCount).width()+2;
						$this.navPlane.cssFlash('left',(Math.abs(nl)+Math.abs(vx))*-1);
						$this.settings.hideCount++;
					};
						
				});
				
				//end
			},
			addTab:function(data){		
				if(!data.id) return false;
				var index=0;
				//console.log('data.newtab',data.newtab);
				if(data.newtab==true){
					index=this._indexId(data.id+'-'+data.aid);
					if(index>0){
						data.id=data.id+'-'+data.aid;
						this.replaceTab(index,data);
						//console.log('data.id 1:',index+':'+data.id+'-'+data.aid);
					}else{
						index=this._indexId(data.id);
						if(index>0&&this._getPages().eq(index).attr('aid')==data.aid){
							data.id=data.id+'-'+data.aid;
							this.replaceTab(index,data);
							//console.log('data.id 2:',index+':'+data.id);
						}else{
							data.id=data.id+'-'+data.aid;
							this.appendTap(data);
						}
					}
				}else{
					index=this._indexId(data.id+'-'+data.aid);
					if(index>0){
						data.id=data.id+'-'+data.aid;
						this.replaceTab(index,data);
						//console.log('data.id 1:',index+':'+data.id+'-'+data.aid);
					}else{
						index=this._indexId(data.id);
						if(index>0){
							//console.log('data.id 2:',index+':'+data.id);
							this.replaceTab(index,data);
						}else{
							this.appendTap(data);
						}
					}
				}
				this.settings.index=index;
				index=null;
			},
			appendTap:function(data){
				var $this=this;
				var tplHeader='<li tid="'+data.id+'" class="item" mice-e="hover"><a><span><span class="icon">'+data.title+'</span></span></a><a class="close" mice-e="hover"></a></li>';
				var tplContent=(data.way!='iframe')?('<div class="pages" aid="'+data.aid+'"></div>'):('<div class="pages" aid="'+data.aid+'"><div tid="ajax-'+data.id+'" style="height:'+($('.tabsPageContent').height()-2)+'px;width:'+($('.tabsPageContent').width()-2)+'px;" class="ajaxLoading"><div class="ajaxLoadingContent"></div>信息加载中...</div><iframe onload="$(\'div[tid=ajax-'+data.id+']\').fadeOut();" src="'+data.url+'" width="100%" frameborder="0" scrolling="auto" filltarget=".tabsPageContent" /></iframe></div>');
				this.navPlane.append(tplHeader);
				this.contentPlane.append(tplContent);
				setTimeout(function(){
						$('div[tid=ajax-'+data.id+']').fadeOut();
					},$this.settings.loadingTimeout);
					
				if(data.way!='iframe'){
					$.ajax({
						type:'POST',
						url:data.url,
						success:function(content){
							$this._getPages().eq($this._indexId(data.id)).html(content);
						}
					});
				}else{
					var t=$('iframe',this._getPages().eq(this._indexId(data.id))),fillTarget=t.attr('filltarget');
					if(fillTarget!=undefined){
						t.height($(fillTarget).height());
					}
				}
				this.bindEvent();
				this.switchTab(this._indexId(data.id));
			},
			removeTab:function(index){
				this._getNavs().eq(index).remove();
				this._getPages().eq(index).remove();
				this.switchTab(index-1);
			},
			replaceTab:function(index,data){
				var $this=this;
				if(!data.id) return false;
				var tplHeader='<li tid="'+data.id+'" class="item" mice-e="hover"><a><span><span class="icon">'+data.title+'</span></span></a><a class="close" mice-e="hover"></a></li>';
				var tplContent=(data.way!='iframe')?('<div class="pages" aid="'+data.aid+'"></div>'):('<div class="pages" aid="'+data.aid+'"><div tid="ajax-'+data.id+'" style="height:'+($('.tabsPageContent').height()-2)+'px;width:'+($('.tabsPageContent').width()-2)+'px;" class="ajaxLoading"><div class="ajaxLoadingContent"></div>信息加载中...</div><iframe tid="pages-'+data.id+'" onload="$(\'div[tid=ajax-'+data.id+']\').fadeOut();" src="'+data.url+'" width="100%" frameborder="0" scrolling="auto" filltarget=".tabsPageContent" /></iframe></div>');
				this._getNavs().eq(index).replaceWith(tplHeader);
				this._getPages().eq(index).replaceWith(tplContent);
				setTimeout(function(){
						$('div[tid=ajax-'+data.id+']').fadeOut();
					},$this.settings.loadingTimeout);
					
				if(data.way!='iframe'){
					$.ajax({
						type:'POST',
						url:data.url,
						success:function(content){
							$this._getPages().eq($this._indexId(data.id)).append(content);
						}
					});
				}else{
					var t=$('iframe',this._getPages().eq(this._indexId(data.id))),fillTarget=t.attr('filltarget');
					if(fillTarget!=undefined){
						t.height($(fillTarget).height());
					}
				}
				this.bindEvent();
				this.switchTab(index);
			},
			switchTab:function(index){
				this._getNavs().removeClass(this.settings.selected).eq(index).addClass(this.settings.selected);
				this._getPages().hide().eq(index).show();
			},
			_getNavs:function(){
				return this.navPlane.find('> li.item');
			},
			_getPages:function(){
				return this.contentPlane.find('> div.pages');
			},
			_indexId:function(tid){
				if (!tid) return -1;
				var index=-1;
				this._getNavs().each(function(i){
					if($(this).attr('tid')==tid) index=i;
					return ;
				});
				return index;
			}
	}
	module.exports=navTab;
});
