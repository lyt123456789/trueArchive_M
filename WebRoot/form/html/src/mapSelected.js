var god_current_click_obj=null;//当前选中的标签
var selectedBgColor='pink';//选中背景颜色
var lastBgColor='';//当前选中标签的背景色
function registerTag(){
    //input标签 注册点击效果
	var inputs=document.getElementsByTagName('input');
	for(var i=0;i<inputs.length;i++){
		var o=inputs[i];
		if(o.tagName.toLowerCase()=='input'){
			o.onclick=function(){
				if(god_current_click_obj)god_current_click_obj.style.backgroundColor=lastBgColor;
				god_current_click_obj=this;
				lastBgColor=this.style.backgroundColor;
				this.style.backgroundColor=selectedBgColor;
				if(window.parent.htmlTagToJspTag){//调用父页面方法，关联选中标签行
					window.parent.htmlTagToJspTag(this.name);
				};
				
			}; 
		};
	};
    //select标签 注册点击效果
	var selects=document.getElementsByTagName('select');
	for(var i=0;i<selects.length;i++){
		var o=selects[i];
		if(o.tagName.toLowerCase()=='select'){
			o.onclick=function(){
				if(god_current_click_obj)god_current_click_obj.style.backgroundColor=lastBgColor;
				god_current_click_obj=this;
				lastBgColor=this.style.backgroundColor;
				this.style.backgroundColor=selectedBgColor;
				if(window.parent.htmlTagToJspTag){//调用父页面方法，关联选中标签行
					window.parent.htmlTagToJspTag(this.name);
				};
			};
		};
	};
    //textarea标签 注册点击效果
	var textareas=document.getElementsByTagName('textarea');
	for(var i=0;i<textareas.length;i++){
		var o=textareas[i];
		if(o.tagName.toLowerCase()=='textarea'){
			o.onclick=function(){
				if(god_current_click_obj)god_current_click_obj.style.backgroundColor=lastBgColor;
				god_current_click_obj=this;
				lastBgColor=this.style.backgroundColor;
				this.style.backgroundColor=selectedBgColor;
				if(window.parent.htmlTagToJspTag){//调用父页面方法，关联选中标签行
					window.parent.htmlTagToJspTag(o.name);
				};
			};
			
		};
	};
}; 
//父页面调用,用于映射选中对应标签
function jsp2html(tagname){
	//上次选中效果去除
	if(god_current_click_obj)god_current_click_obj.style.backgroundColor=lastBgColor;
	var isok=false;
	 //input标签 
	var inputs=document.getElementsByTagName('input');
	for(var i=0;i<inputs.length;i++){
		var o=inputs[i];
		if(o.name==tagname){
			god_current_click_obj=o;
			lastBgColor=o.style.backgroundColor;
			o.style.backgroundColor=selectedBgColor;
			o.focus();
			break;isok=true;
		};
	};
	if(isok)return;
    //select标签 
	var selects=document.getElementsByTagName('select');
	for(var i=0;i<selects.length;i++){
		var o=selects[i];
		if(o.name==tagname){
			god_current_click_obj=o;
			lastBgColor=o.style.backgroundColor;
			o.style.backgroundColor=selectedBgColor;
			o.focus();
			break;isok=true;
		};
	};
	if(isok)return;
    //textarea标签 
	var textareas=document.getElementsByTagName('textarea');
	for(var i=0;i<textareas.length;i++){
		var o=textareas[i];
		if(o.name==tagname){
			god_current_click_obj=o;
			lastBgColor=o.style.backgroundColor;
			o.style.backgroundColor=selectedBgColor;
			o.focus();
			break;isok=true;
		};
	};
}