// JavaScript Document
define(function(require, exports, module){
	var $=require('jquery');
	$.extend($.fn,{
		//格式化button
		cssBtn:function(options){
			var settings=$.extend({
					boxcls:'mice-btn',
					prefix:'mice-form-button-',
					trigger:null,
					triggerFn:null
				},options);
			return this.each(function(i){
				if($(this).attr('cssrest')=='true') return;
				var v='-v'+i;
				var $this=$(this),cls=$this.attr('mice-btn'),name=$this.attr('name');
				name=(name.indexOf('.')>-1)?name.replace('.','-'):((name.indexOf('#')>-1)?name.replace('#','-'):name);
				$this.wrap('<span id="'+settings.prefix+name+v+'" class="'+settings.boxcls+'"><span><span class="'+cls+'"></span></span></span>');
				$('.'+settings.boxcls).hover(function(){
					$(this).addClass('mice-btn-hover');
				},function(){
					$(this).removeClass('mice-btn-hover');
				});
				$(this).attr('cssrest','true');
			});
		},
		//格式化input
		cssInput:function(options){
			var settings=$.extend({
				boxcls:'mice-input',
				prefix:'mice-form-input-',
				trigger:null,
				triggerFn:null
			},options);
			return this.each(function(i){
				if($(this).attr('cssrest')=='true') return;
				var v='-v'+i;
				var $this=$(this),name=$this.attr('name');
				name=(name.indexOf('.')>-1)?name.replace('.','-'):((name.indexOf('#')>-1)?name.replace('#','-'):name);
				$this.wrap('<span id="'+settings.prefix+name+v+'" class="'+settings.boxcls+'"><span><span></span></span></span>');
				$('#'+settings.prefix+name+v).hover(function(){
					$(this).addClass(settings.boxcls+'-hover');
				},function(){
					$(this).removeClass(settings.boxcls+'-hover');
				});
				$(this).attr('cssrest','true');
			});
		},
		//格式化Radio
		cssRadio:function(options){
			var settings=$.extend({
				boxcls:'mice-radio',
				prefix:'mice-form-radio-',
				trigger:null,
				triggerFn:function(){}
			},options);
			return this.each(function(i){
				if($(this).attr('cssrest')=='true') return;
				var v='-v'+i;
				var $this=$(this),name=$this.attr('name'),cked=this.checked;
				name=(name.indexOf('.')>-1)?name.replace('.','-'):((name.indexOf('#')>-1)?name.replace('#','-'):name);
				var radiotpl='<span id="'+settings.prefix+name+v+'" class="'+settings.prefix+name+' '+settings.boxcls+'"></span>';
				$this.wrap(radiotpl).css({'display':'none'}).parent().addClass((cked)?(settings.boxcls+'-selected'):'').attr('checked',(cked)?'true':'false');
				
				var $rd=$('#'+settings.prefix+name+v),rds='.'+settings.prefix+name;
				if($rd.attr('class')){
					$rd.hover(function(){
							if($(this).attr('checked')=='true')
								$(this).addClass(settings.boxcls+'-selected-hover');
							else
								$(this).addClass(settings.boxcls+'-hover');
						},function(){
								$(this).removeClass(settings.boxcls+'-hover').removeClass(settings.boxcls+'-selected-hover');
						}).bind('click',function(){
							$(rds).attr('checked','false').removeClass(settings.boxcls+'-selected');
							$rd.attr('checked','true').addClass(settings.boxcls+'-selected').find('>:radio').each(function(){
								this.checked=true;
							}).triggerHandler('click');
						});
					
				};
				$(this).attr('cssrest','true');
			});
		},
		//格式化Checkbox
		cssCheckbox:function(options){
			var settings=$.extend({
				boxcls:'mice-checkbox',
				prefix:'mice-form-checkbox-',
				trigger:null,
				triggerFn:function(){}
			},options);
			return this.each(function(i){
				if($(this).attr('cssrest')=='true') return;
				var v='-v'+i;
				var $this=$(this),name=$this.attr('mice-checkbox'),cked=this.checked;
				name=(name.indexOf('.')>-1)?name.replace('.','-'):((name.indexOf('#')>-1)?name.replace('#','-'):name);
				var radiotpl='<span id="'+settings.prefix+name+v+'" class="'+settings.prefix+name+' '+settings.boxcls+'"></span>';
				$this.wrap(radiotpl).css({'display':'none'}).parent().addClass((cked)?(settings.boxcls+'-selected'):'').attr('checked',(cked)?'true':'false');
				var $rd=$('#'+settings.prefix+name+v),rds='.'+settings.prefix+name;
				if($rd.attr('class')){
					$rd.hover(function(){
							if($(this).attr('checked')=='true')
								$(this).addClass(settings.boxcls+'-selected-hover');
							else
								$(this).addClass(settings.boxcls+'-hover');
								
						},function(){
								$(this).removeClass(settings.boxcls+'-hover').removeClass(settings.boxcls+'-selected-hover');
								
						}).bind('click',function(){
							var checked=$(this).attr('checked');
							if(checked=="true"){
								$(this).attr('checked','false').removeClass(settings.boxcls+'-selected').removeClass(settings.boxcls+'-selected-hover').addClass(settings.boxcls+'-hover').find('>:checkbox').each(function(){
									this.checked=false;
								}).triggerHandler('click');
							}else{
								$(this).attr('checked','true').addClass(settings.boxcls+'-selected').addClass(settings.boxcls+'-selected-hover').find('>:checkbox').each(function(){
									this.checked=true;
								}).triggerHandler('click');
							}
						});
				}
				$(this).attr('cssrest','true');
			});
		},
		//格式化Textarea
		cssTextarea:function(options){
			var settings=$.extend({
				boxcls:'mice-textarea',
				prefix:'mice-form-textarea-',
				trigger:null,
				triggerFn:function(){}
			},options);
			return this.each(function(i){
				if($(this).attr('cssrest')=='true') return;
				var v='-v'+i;
				var $this=$(this),name=$this.attr('name'),w=$this.width()+10;
				name=(name.indexOf('.')>-1)?name.replace('.','-'):((name.indexOf('#')>-1)?name.replace('#','-'):name);
				$this.wrap('<div id="'+settings.prefix+name+v+'" class="'+settings.boxcls+'" style="width:'+w+'px;"></div>')
					 .wrap('<span class="c"><span></span></span>').parent().parent()
					 .before('<span class="t"><span></span></span>')
					 .after('<span class="b"><span></span></span>');
				$('#'+settings.prefix+name+v).hover(function(){
					$(this).addClass(settings.boxcls+'-hover');
				},function(){
					$(this).removeClass(settings.boxcls+'-hover');
				});
				$(this).attr('cssrest','true');
			});
		},
		//格式化Select
		cssSelect:function(options){
			var settings=$.extend({
					boxcls:'mice-select',
					ableWrite:false,
					itemHover:'hover',
					selectcls:'selectplane',
					inputcls:'writeplane',
					btncls:'selectbtn',
					prefix:'mice-form-select-',
					trigger:null,
					triggerFn:function(){}
				},options);
			return this.each(function(i){
				if($(this).attr('cssrest')=='true') return;
				var v='-v'+i;
				var hover=[];
				hover[0]=false;
				var $this=$(this), _this=this, name=$this.attr('name'), w=$this.width(),h=$this.height(),list=this.options,listed='';
				name=(name.indexOf('.')>-1)?name.replace('.','-'):((name.indexOf('#')>-1)?name.replace('#','-'):name)+v;
				//选择按钮
				var maxFontSize=0,fs=14;
				var ulchild='';
				for(var i=0,l=list.length;i<l;i++){
					ulchild+='<li>'+list[i].text+'</li>';
					maxFontSize=Math.max(maxFontSize,(list[i].text).length);
				}
				w=(w>0)?w:maxFontSize*15;
				//输入框
				var input=(!settings.ableWrite)?'<span class="'+settings.inputcls+'"><span class="'+settings.btncls+' button"><div name="'+name+'-input" style="width:'+w+'px;"></div></span></span>':'<span class="'+settings.inputcls+'"><span class="'+settings.btncls+' button"><input name="'+name+'-input" style="width:'+w+'px;"/></span></span>';
				var ul='<ul id="'+settings.prefix+name+'-ul" class="'+settings.selectcls+'" style="position:absolute;z-index:99999;display:none;">'+ulchild+'</ul>';
				var $box=$('<span id="'+settings.prefix+name+'" class="cbo '+settings.boxcls+'"></span>');
				$this.wrap($box).css({'display':'none'});	
				
				//绑定selectBox
				$('body').append(ul);
				var $F=$('#'+settings.prefix+name).append(input);	
				$('#'+settings.prefix+name+'-ul').css({'top':($F.offset().top+$F.height()),'left':(!settings.ableWrite)?($('div',$F).offset().left):($('input',$F).offset().left)});
				//下拉按钮滑过事件
				$('.'+settings.btncls,$F).hover(function(){
					$(this).addClass('button-hover');	
				},function(){
					$(this).removeClass('button-hover');	
				}).click(function(){
					$('#'+settings.prefix+name+'-ul').css({'top':($F.offset().top+$F.height()),'left':(!settings.ableWrite)?($('div',$F).offset().left):($('input',$F).offset().left)}).slideDown('fast');
				});
				if(!settings.ableWrite){
					$('div',$F).html(list[_this.selectedIndex].text).click(function(){
						$('#'+settings.prefix+name+'-ul').css({'top':($F.offset().top+$F.height()),'left':($('div',$F).offset().left)}).slideDown('fast');
					});
				}else{
					$('input',$F).val(list[_this.selectedIndex].text).click(function(){
						$('#'+settings.prefix+name+'-ul').css({'top':($F.offset().top+$F.height()),'left':($('input',$F).offset().left)}).slideDown('fast');
					});
				}
				
				$F.mouseleave(function(){
					setTimeout(function(){if(!hover[0]) $('#'+settings.prefix+name+'-ul').slideUp('fast')},500);
				});
				$('#'+settings.prefix+name+'-ul').mouseover(function(){
					hover[0]=true;
				}).mouseleave(function(){
					$(this).slideUp('fast');
					hover[0]=false;
				});
				$('#'+settings.prefix+name+'-ul'+' >li').each(function(i){
					$(this).click(function(){
						(!!settings.ableWrite)?$('input',$F).val($(this).text()):$('div',$F).html($(this).text());
						_this.options[i].selected=true;
						$('#'+settings.prefix+name+'-ul').slideUp('fast');
						if(settings.trigger) settings.triggerFn.call($this);
					});
				});
				$('#'+settings.prefix+name+'-ul').find('li').each(function(){
					$(this).hover(function(){
						$(this).addClass('hover');
					},function(){
						$(this).removeClass('hover');
					});
				});
				$(this).attr('cssrest',"true");
			});
		},
		//验证是否有class
		hasClass:function(name){
			var reg = new RegExp('(\\s|^)' + name + '(\\s|$)');
			return this[0].className.match(reg) ? true : false;
		},
		//验证表单框架
		/**
		regExpArr:{
			username:{
				test:'',
				tips:'',
				error:''
			},
			…………
		}
		**/
		regForm:function(options){
			require('lib/string');
			var settings=$.extend({
					showTips:false,
					regTag:'mice-reg',
					regBlur:'blur',
					regFoucs:'focus',
					regExpArr:{
						username:{
							test:/^([_]|[a-zA-Z0-9]){6,32}$/,
							tips:'用户名为字母、数字、下划线的组合。',
							rightTips:'',
							errorTips:'',
							errorFn:null,
							rightFn:null
						},
						password:{
							test:/^([_]|[a-zA-Z0-9]){6,32}$/,
							tips:'密码为字母、数字、下划线的组合。',
							rightTips:'',
							errorTips:'',
							errorFn:null,
							rightFn:null
						},
						email:{
							test:/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,
							tips:'请输入正确的邮箱地址。示例：web@site.com',
							rightTips:'',
							errorTips:'',
							errorFn:null,
							rightFn:null
						},
						tel:{
							test:/(^([0-9]{3,4}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0-9]{3,4}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/,
							tips:'请输入正确的座机电话号码。示例：0513-88888888或88888888。',
							rightTips:'',
							errorTips:'',
							errorFn:null,
							rightFn:null
						}
					},
					prefix:'mice-regtips-'
				},options);
			if($.isEmptyObject(settings.regExpArr)) return;
			return this.each(function(){
				var $this=$(this);
				$('['+settings.regTag+']',$this).each(function(){
					var regx=$(this).attr(settings.regTag);
					$(this).data('regExpArr',settings.regExpArr[regx]);
					var regBlur=($(this).attr('mice-regevt'))?$(this).attr('mice-regevt'):settings.regBlur;
					$(this).bind(settings.regFoucs,function(){
						var name=$(this).attr('name'),ps=$(this).offset(),$ipt=$(this);
						if($('#'+settings.prefix+name).attr('id')){
							$('#'+settings.prefix+name).find('>p').removeClass().addClass('panel');
						}else{
							$('<div class="mice-regtips" id="'+settings.prefix+name+'" style="position:absolute;top:-1000px;left:0px;"><p class="panel">'+settings.regExpArr[regx].tips+'</p></div>')
								.appendTo($('body'))
								.each(function(){
									$(this).css({top:($ipt.height()-$(this).height()+ps.top)+'px'});
								}).css({left:(ps.left+$(this).width()+12)+'px'}).after('<div id="'+settings.prefix+name+'-bg" class="mice-regtips-bg"></div>').each(function(){
									$('#'+this.id+'-bg').css({width:$(this).width(),height:$(this).height(),top:($(this).offset().top+3)+'px',left:($(this).offset().left+3)+'px'});
								});
						}
					}).bind(regBlur,function(){
						var name=$(this).attr('name');
						if($(this).testForm(regx)){
							$('#'+settings.prefix+name).find('>p').removeClass().addClass('right').each(function(){
								if(!!settings.regExpArr[regx].rightTips){
									$(this).html(settings.regExpArr[regx].rightTips);
								}
							});
							if(settings.regExpArr[regx].rightFn!==null) settings.regExpArr[regx].rightFn.call(this);
						}else{
							$('#'+settings.prefix+name).find('>p').removeClass().addClass('error');
							if(settings.regExpArr[regx].errorFn!==null) settings.regExpArr[regx].errorFn.call(this);
						};
					});
				});
			});
		},
		//测试表单
		testForm:function(regx){
			var $this=$(this),tests=$this.data('regExpArr').test,
				val=$this.val();
			return (new RegExp(tests).test(val));
		}
	});
	module.exports=$;
});