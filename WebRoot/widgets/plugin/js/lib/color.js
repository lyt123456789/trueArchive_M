// JavaScript Document
;define(function(require,exports,module){
	var $=require('jquery');
	$.extend($.fn,{
		colorPicker:function(options){
			var settings=$.extend({
				trigger:'click',
				triggerFn:function(){},
				colorCls:'colorpicker-ico',
				autoClose:true			
			},options);
			
			return this.each(function(){
				var $this=$(this).addClass(settings.colorCls);
					$this.bind('click',function(){
						var picker='<div id="mice-colorpicker-'+$this.attr('name')+'" style="border:3px solid #eee;position:absolute;top:'+$this.offset().top+'px;left:'+($this.offset().left+$this.width())+'px;z-index:999;background-color:#FFF;"><div style="background-color:#362C3B;color:#938698;padding:5px;text-align:right;"><span style="float:left;">&raquo; ColorPicker</span> <span class="close" style="color:#FFF;cursor:pointer;">X关闭</span></div>'+bulidColorPicker()+'</div>';
						if($('#mice-colorpicker-'+$this.attr('name')).attr('id')===undefined){
							$(picker).appendTo('body');
							
							$('#mice-colorpicker-'+$this.attr('name')).find('span.close').bind('click',function(){
								$('#mice-colorpicker-'+$this.attr('name')).fadeOut(200);
							});
							$('#mice-colorpicker-'+$this.attr('name')).find('td').bind('click',function(){
								var color=$(this).css('background-color');
								if(/rgb\(/i.test(color))
								{
								  color = color.replace(/^[\s\S]*?rgb\((.*?)\)[\s\S]*/gi, "$1").replace(/\s+/g, "");
								  color=color.split(',');
								  color='#'+toHex(color[0])+toHex(color[1])+toHex(color[2])
								}
								$this.val(color).css({'color':color});
								if(settings.autoClose) $('#mice-colorpicker-'+$this.attr('name')).fadeOut(200);
							})
						}else{
							$('#mice-colorpicker-'+$this.attr('name')).fadeIn(200);
						}
						
					}).bind(settings.trigger,function(){
						settings.triggerFn.call(this);
					});			
			});
		}
	});
	//色彩范围
	var hexch = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F');  
	var cnum = new Array(1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0); 
	
	//创建调色板
	function bulidColorPicker(){  
		var trStr = "<table>";  
		for (i = 0; i < 16; i++) {  
			trStr += '<tr>';  
			for (j = 0; j < 30; j++) {  
				n1 = j % 5;  
				n2 = Math.floor(j / 5) * 3;  
				n3 = n2 + 3;  
				  
				trStr += colorpix((cnum[n3] * n1 + cnum[n2] * (5 - n1)), (cnum[n3 + 1] * n1 + cnum[n2 + 1] * (5 - n1)), (cnum[n3 + 2] * n1 + cnum[n2 + 2] * (5 - n1)), i);  
			}  
			  
			trStr += '</tr>';  
		}  
		trStr += "</table>";  
		return trStr;  
	}  
	//转换为16进制
	function toHex(n){  
		var h, l;  
		  
		n = Math.round(n);  
		l = n % 16;  
		h = Math.floor((n / 16)) % 16;  
		return (hexch[h] + hexch[l]);  
	}
	//返回16进制颜色
	function doColor(c, l){  
		var r, g, b;  
		r = '0x' + c.substring(1, 3);  
		g = '0x' + c.substring(3, 5);  
		b = '0x' + c.substring(5, 7);  
		if (l > 120) {  
			l = l - 120;
			r = (r * (120 - l) + 255 * l) / 120;  
			g = (g * (120 - l) + 255 * l) / 120;  
			b = (b * (120 - l) + 255 * l) / 120;  
		}  
		else {  
			r = (r * l) / 120;  
			g = (g * l) / 120;  
			b = (b * l) / 120;  
		}  
		return '#' + toHex(r) + toHex(g) + toHex(b);  
	}  
	//生成像素点
	function colorpix(r, g, b, n){  
		r = ((r * 16 + r) * 3 * (15 - n) + 0x80 * n) / 15;  
		g = ((g * 16 + g) * 3 * (15 - n) + 0x80 * n) / 15;  
		b = ((b * 16 + b) * 3 * (15 - n) + 0x80 * n) / 15;  
		return '<td  style="background-color:#' + toHex(r) + toHex(g) + toHex(b) + ';height:5px;width:5px;"></td>';  
	} 
});