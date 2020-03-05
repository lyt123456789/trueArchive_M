(function($) {  
$.fn.SelectStyle = function(lj) { 
var timer;
var ons=false;//判断是否已经打开内容 
var f=true;//判断鼠标是否在元素上 
var selcons='<div onMouseOver="show1(this)" onMouseLeave="hide1(this)" class="texts"></div>';
var selxl='<img onclick="show(this)" src="'+lj+'images/select-arr.gif" class="pa cu" style="right:4px;top:4px;" />';
var selxlcon='<div onMouseOver="show2(this)" onMouseLeave="hide2(this)" class="xlcon none"></div>';
$(this).after(selcons);
$(this).next('.texts').after(selxlcon);
$(this).hide();//select隐藏
$(this).next('.texts').attr("id","ojb"+$(this).attr("id"));//给ID赋值
$(this).next('.texts').html($(this).find("option:selected").text()+selxl);
var pos=getElementPos($(this).next('div').attr('id'));//获取位置
$(this).next('.texts').next('.xlcon').css("top",pos.y+$(this).next('.texts').height()+3);
var pot_ojb=$(this).find('option');
for(var i=0;i<pot_ojb.length;i++){
	$(this).next('.texts').next('.xlcon').append("<p value="+pot_ojb.eq(i).val()+" onclick='pcli(this)'>"+pot_ojb.eq(i).html()+"</p>");
	}

window.show=function(ojb){
	clearTimeout(timer);
	if(ons==false){$(ojb).parent().next('.xlcon').slideDown(300,function(){ons=true;});}	
	if(ons==true){$(ojb).parent().next('.xlcon').slideUp(300,function(){ons=false;});}
	}
	
window.show1=function(ojb){
	f=true;
	}
window.show2=function(ojb){
	f=true;
	}
window.hide1=function(ojb){//下拉元素
    f=false;
	timer=setTimeout(function(){if(f==false){$(ojb).next('.xlcon').slideUp(300);ons=false;}},500);
	}
window.hide2=function(ojb){//内容元素
    f=false;
	timer=setTimeout(function(){if(f==false){$(ojb).slideUp(300);ons=false;}},500);
	}
window.pcli=function(obj){
	clearTimeout(timer);
	$(obj).parent('.xlcon').prev('.texts').html($(obj).html()+selxl);
	$(obj).parent('.xlcon').slideUp(300);
	ons=false;
	$(obj).parent('.xlcon').prev('.texts').prev('select').find('option:selected').html($(obj).html());
	$(obj).parent('.xlcon').prev('.texts').prev('select').find('option:selected').val($(obj).attr("value"));
	}
};  
})(jQuery);

//获取元素的位置
function getElementPos(elementId) {
 var ua = navigator.userAgent.toLowerCase();
 var isOpera = (ua.indexOf('opera') != -1);
 var isIE = (ua.indexOf('msie') != -1 && !isOpera); // not opera spoof
 var el = document.getElementById(elementId);
 if(el.parentNode === null || el.style.display == 'none') {
  return false;
 }
 var parent = null;
 var pos = [];     
 var box;     
 if(el.getBoundingClientRect)    //IE
 {         
  box = el.getBoundingClientRect();
  var scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
  var scrollLeft = Math.max(document.documentElement.scrollLeft, document.body.scrollLeft);
  return {x:box.left + scrollLeft, y:box.top + scrollTop};
 }else if(document.getBoxObjectFor)    // gecko    
 {
  box = document.getBoxObjectFor(el); 
  var borderLeft = (el.style.borderLeftWidth)?parseInt(el.style.borderLeftWidth):0; 
  var borderTop = (el.style.borderTopWidth)?parseInt(el.style.borderTopWidth):0; 
  pos = [box.x - borderLeft, box.y - borderTop];
 } else    // safari & opera    
 {
  pos = [el.offsetLeft, el.offsetTop];  
  parent = el.offsetParent;     
  if (parent != el) { 
   while (parent) {  
    pos[0] += parent.offsetLeft; 
    pos[1] += parent.offsetTop; 
    parent = parent.offsetParent;
   }  
  }   
  if (ua.indexOf('opera') != -1 || ( ua.indexOf('safari') != -1 && el.style.position == 'absolute' )) { 
   pos[0] -= document.body.offsetLeft;
   pos[1] -= document.body.offsetTop;         
  }    
 }              
 if (el.parentNode) { 
    parent = el.parentNode;
   } else {
    parent = null;
   }
 while (parent && parent.tagName != 'BODY' && parent.tagName != 'HTML') { // account for any scrolled ancestors
  pos[0] -= parent.scrollLeft;
  pos[1] -= parent.scrollTop;
  if (parent.parentNode) {
   parent = parent.parentNode;
  } else {
   parent = null;
  }
 }
 return {x:pos[0], y:pos[1]};
}