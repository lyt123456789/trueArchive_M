// JavaScript Document
;define(function(require, exports, module){
	var $=require("jquery");
	var navtab=require('lib/navTab');
	/**
	 * 挟持a的函数
	 **/
	function holdFn(e,nw){
		var e=e||window.event;
			if(!!$(this).attr('rel')&&!!$(this).attr('target')&&$(this).attr('href')!='javascript:;'){
				navtab['addTab']({aid:$(this).attr('aid'),id:$(this).attr('rel'),title:$(this).text(),url:$(this).attr('href'),way:$(this).attr('way'),newtab:(!!nw?true:false)});
			}
			if ( e && e.preventDefault ){
				e.preventDefault(); 
			}else{
				window.event.returnValue = false;
			}
			return true;
	}
	//end
	$.extend($.fn,{
		tree:function(options){
			var settings=$.extend({
				checkFn:null,
				selected:'selected',
				exp:'expandable',
				coll:'collapsable',
				firstExp:'first_expandable',
				firstColl:'first_collapsable',
				lastExp:'last_expandable',
				lastColl:'last_collapsable',
				folderExp:'folder_expandable',
				folderColl:'folder_collapsable',
				endExp:'end_expandable',
				endColl:'end_collapsable',
				file:'file',
				ck:'checked',
				unck:'unchecked'
			},options);
			return this.each(function(){
				var $this=$(this);
				var count=$this.children().length;
				$('>li',$this).each(function(){
					var $li=$(this);
					
					var first=$li.prev()[0]?false:true;
					var last=$li.next()[0]?false:true;
					$li.initTree({
						icon:$this.hasClass('treeFolder'),
						ckbox:$this.hasClass('treeCheck'),
						options:settings,
						level:0,
						exp:(count>1?(first?settings.firstExp:(last?settings.lastExp:settings.exp)):settings.endExp),
						coll:(count>1?(first?settings.firstColl:(last?settings.lastColl:settings.coll)):settings.endExp),
						showSub:(!$this.hasClass('collapse')&&($this.hasClass('expand')||(count>1?(first?true:false):true))),
						isLast:(count>1?(last?true:false):true)
					});
				});
				if($this.hasClass('treeCheck')){
					var checkFn=eval($this.attr('oncheck'));
					if(checkFn&&$.isFunction(checkFn)){
						$('div.ckbox',$this).each(function(){
							var ckbox=$(this);
							ckbox.bind('click',function(){
								var checked=$(ckbox).hasClass('checked');
								var items=[];
								if(checked){
									var tnode=$(ckbox).parent().parent();
									var boxes=$('input',tnode);
									if(boxes.size()>1){
										$(boxes).each(function() {
                                            items[items.length]={
												name:$(this).attr('name'),
												value:$(this).val(),
												text:$(this).attr('text')
											};
                                        });
									}else{
										items={
											name:boxes.attr('name'),
											value:boxes.val(),
											text:boxes.attr('text')	
										};
									}
								}
								checkFn({checked:checked, items:items});
							});
						});
					}
				}
				$('a',$this).bind('click',function(e){
					//alert($(this).html());
					$('div.'+settings.selected, $this).removeClass(settings.selected);
					var parent=$(this).parent().addClass(settings.selected);
					$('.ckbox',parent).trigger('click');
					var e = e||window.event;
					if (window.event) {//IE
						e.cancelBubble=true;
					} else { //FF
						e.stopPropagation();
					}
					$(document).trigger('click');
					if(!$(this).attr('target')) return false;
					//在这里hold住a
					if(holdFn.call(this,e)) return false;
				});
			});
		},
		subTree:function(settings,level){
			return this.each(function(){
				$('>li',this).each(function(){
					var $this=$(this);
					var isLast=($this.next()[0]?false:true);
					$this.initTree({
						icon:settings.icon,
						ckbox:settings.ckbox,
						exp:isLast?settings.options.lastExp:settings.options.exp,
						coll:isLast?settings.options.lastColl:settings.options.coll,
						options:settings.options,
						level:level,
						space:isLast?null:settings.space,
						showSub:settings.showSub,
						isLast:isLast
					});
				});
			});
		},
		initTree:function(options){
			var settings=$.extend({
					icon:options.icon,
					ckbox:options.ckbox,
					exp:'',
					coll:'',
					showSub:false,
					level:0,
					options:null,
					isLast:false
				},options);
			return this.each(function(){
				var node=$(this);
				var tree=$('>ul',node);
				var parent=node.parent().prev();
				var checked='unchecked';
				if(settings.ckbox){
					if($('>.checked',parent).size()>0) checked='checked';
				}
				if(tree.size()>0){
					node.children(':first').wrap('<div></div>');
					$('>div',node).prepend('<div class="'+(settings.showSub?settings.coll:settings.exp)+'"></div>'+(settings.ckbox?'<div class="ckbox '+checked+'"></div>':'')+(settings.icon?'<div class="'+(settings.showSub?settings.options.folderColl:settings.options.folderExp)+'"></div>':''));
					settings.showSub?tree.show():tree.hide();
					$('>div>div:first,>div>a',node).bind('click',function(){
						alert($(this));
						var $fnode=$('>li:first',tree);
						if($fnode.children(':first').isTag('a')) tree.subTree(settings,settings.level+1);
						var $this=$(this);
						var isA=$this.isTag('a');
						var $this=isA?$('>div>div',node).eq(settings.level):$this;
						if(isA||tree.is(':hidden')){
							$this.toggleClass(settings.exp).toggleClass(settings.coll);
							if(settings.icon){
								$('>div>div:last',node).toggleClass(settings.options.folderExp).toggleClass(settings.options.folderColl);
							}
						}
						//控制展开和收缩
						(tree.is(':hidden'))?tree.slideDown('fast'):(isA?tree.slideUp('fast'):'');
						return false;
					});
					addSpace(settings.level,node);
					if(settings.subwSub) tree.subTree(settings,settings.level+1);
				}else{
					node.children().wrap('<div></div>');
					$('>div',node).prepend('<div class="node"></div>'+(settings.ckbox?'<div class="ckbox '+checked+'"></div>':'')+(settings.icon?'<div class="file"></div>':'')).find('div.file').bind('click',function(e){
						//在这里打开新窗口
						//在这里hold住a
						var e = e||window.event;
						if(holdFn.call($(this).parent().find('a'),e,true)) return false;
					});
					addSpace(settings.level,node);
					if(settings.isLast) $(node).addClass('last');
				}
				if(settings.ckbox) node._check(settings);
				$('>div',node).hover(function(){
					$(this).addClass('hover');
				},function(){
					$(this).removeClass('hover');
				});
				if($.browser.msie){
					$('>div',node).bind('click',function(){
						$('a',this).trigger('click');
						return false;
					});
				}
			});
			function addSpace(level,node){
				if(level>0){
					var parent=node.parent().parent();
					var space=!parent.next()[0]?'indent':'line';
					var plist='<div class="'+space+'"></div>';
					if(level>1){
						var next=$('>div>div',parent).filter(':first');
						var prev='';
						while(level>1){
							prev=prev+'<div class="'+next.attr('class')+'"></div>';
							next=next.next();
							level--;
						}
						plist=prev+plist;
					}
					$('>div',node).prepend(plist);
				}
			}
		},
		_check:function(settings){
			var node=$(this);
			var ckbox=$('>div>.ckbox',node);
			var $input=node.find('a');
			var tname=$input.attr('tname'),tvalue=$input.attr('tvalue');
			var attrs='text="'+$input.text()+'" ';
			if(tname) attrs+='name="'+tname+'" ';
			if(tvalue) attrs+='value="'+tvalue+'" ';
			ckbox.append('<input type="checkbox" style="display:none;" '+attrs+'/>').bind('click',function(){
				var cked=ckbox.hasClass('checked');
				var aClass=cked?'unchecked':'checked';
				var rClass=cked?'checked':'unchecked';
				ckbox.removeClass(rClass).removeClass(!cked?'indeterminate':'').addClass(aClass);
				$('input',ckbox).attr('checked',!cked);
				$('>ul',node).find('li').each(function(){
					var box=$('div.ckbox',this);
					box.removeClass(rClass).removeClass(!cked?'indeterminate':'').addClass(aClass).find('input').attr('checked',!cked);
				});
				$(node)._checkParent();
				return false;
			});
			var cAttr=$input.attr('checked');
			if(cAttr) cAttr=eval(cAttr);
			if(cAttr){
				ckbox.find('input').attr('checked',true);
				ckbox.removeClass('unchecked').addClass('checked');
				$(node)._checkParent();
			}
		},
		_checkParent:function(){
			if($(this).parent().hasClass('tree')) return;
			var parent=$(this).parent().parent();
			var stree=$('>ul',parent);
			var ckbox=stree.find('>li>a').size()+stree.find('div.ckbox').size();
			var ckboxed=stree.find('div.checked').size();
			var aClass=(ckboxed==ckbox?'checked':(ckboxed!=0?'indeterminate':'unchecked'));
			var rClass=(ckboxed==ckbox?'indeterminate':(ckboxed!=0?'checked':'indeterminate'));
			$('>div>.ckbox',parent).removeClass('unchecked').removeClass('checked').removeClass(rClass).addClass(aClass);
			parent._checkParent();
		},
		addTree:function(options){
			var settings=$.extend({
					tpl:'<ul class="mice-tree tree treeFolder collapse">{tree}</ul>',
					data:[],
					completeFn:function(){}
				},options);
			return this.each(function(){
				var json=settings.data,$this=$(this), tpl=settings.tpl;
				var $build=function(json,tpl){
					var i=0,j=json.length,_tree='';
					for(i;i<j;i++){
						_tree+='<li class="'+json[i].classname+'"><a aid="'+(json[i].id!=''?json[i].id:Math.random()*new Date().getTime())+'" href="'+((json[i].href!='')?json[i].href:'javascript:;')+'" '
								+((json[i].rel!='')?(' rel="'+json[i].rel+'" '):' ')
								+((json[i].target!='')?(' target="'+json[i].target+'"'):'')
								+'way="'+((json[i].way!='iframe')?json[i].way:'iframe')+'" '
								+'>'+json[i].title+'</a>'
								+((json[i].children.length>0)?arguments.callee(json[i].children,'<ul>{tree}</ul>'):'')
								+'</li>';
					}
					return tpl.replace('{tree}',_tree);
				}
				$this.append($build(json,tpl));
				settings.completeFn.call($this);
			});
		}
	});
	
	module.exports=$;
});