// JavaScript Document
function showTime(o,type){	
	var time=setInterval(
	function(){
		var d=new Date();
		var weekday=new Array(7);
			weekday[0]="星期日";
			weekday[1]="星期一";
			weekday[2]="星期二";
			weekday[3]="星期三";
			weekday[4]="星期四";
			weekday[5]="星期五";
			weekday[6]="星期六";
			if(type=='short'){
				$(o).html('当前时间：'+ d.getFullYear()+'年'+d.getMonth()+'月'+d.getDate()+'日'+'&nbsp;&nbsp;'+weekday[d.getDay()]);
			}else{
				$(o).html('当前时间：'+ d.getFullYear()+'年'+d.getMonth()+'月'+d.getDate()+'日'+'&nbsp;&nbsp;'+d.getHours()+'&nbsp;:&nbsp;'+d.getMinutes());
			}
			
		}
		,1000
	);
	
}

/* get, set, and delete cookies */
function getCookie( name ) {
	var start = document.cookie.indexOf( name + "=" );
	var len = start + name.length + 1;
	if ( ( !start ) && ( name != document.cookie.substring( 0, name.length ) ) ) {
		return null;
	}
	if ( start == -1 ) return null;
	var end = document.cookie.indexOf( ";", len );
	if ( end == -1 ) end = document.cookie.length;
	return unescape( document.cookie.substring( len, end ) );
}
function setCookie( name, value, expires, path, domain, secure ) {
	var today = new Date();
	today.setTime( today.getTime() );
	if ( expires ) {
		expires = expires * 1000 * 60 * 60 * 24;
	}
	var expires_date = new Date( today.getTime() + (expires) );
	document.cookie = name+"="+escape( value ) +
		( ( expires ) ? ";expires="+expires_date.toGMTString() : "" ) + 
		( ( path ) ? ";path=" + path : "" ) +
		( ( domain ) ? ";domain=" + domain : "" ) +
		( ( secure ) ? ";secure" : "" );
}
function deleteCookie( name, path, domain ) {
	if ( getCookie( name ) ) document.cookie = name + "=" +
			( ( path ) ? ";path=" + path : "") +
			( ( domain ) ? ";domain=" + domain : "" ) +
			";expires=Thu, 01-Jan-1970 00:00:01 GMT";
}

$(document).ready(function(){
	$('.JQimgBtns').each(function(){
		var src=$(this).attr('src');
		$(this).data('p',src.replace(src.split('/')[src.split('/').length-1],'')).data('i',src.split('/')[src.split('/').length-1]);
		$(this).hover(function(){
			$(this).attr('src',$(this).data('p')+'o.'+$(this).data('i'));
		},function(){
			$(this).attr('src',$(this).data('p')+$(this).data('i'));
		});
	});
});