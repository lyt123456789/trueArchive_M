<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>无标题文档</title>
	<%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${ctx}/plugin/js/page/god_Core.js"></script> 
	<script type="text/javascript">
  function cutStr(str, strlen){
	    var temp_i = 0;
	    var temp = 0;
	    var temp_str = "";
		str=str.replace(/[\r\n]/g,'').replace(/<[^>]+>/g,'');
	    for (temp_i = 0; temp_i < str.length; temp_i++) {
	    
	        if (Math.abs(str.charCodeAt(temp_i)) > 255) temp += 2;
	        else temp += 1;
	        
	        if (temp > strlen) {
	            temp_str = str.substr(0, temp_i) + "...";
	            break;
	        } else {
	            temp_str = str;
	        }
	    }
	    document.write(temp_str);
	}
</script>
<script type="text/javascript" charset="utf-8" src="${ctx}ueditor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx }ueditor/editor_all_min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx }ueditor/themes/default/ueditor.css" />

<style type="">
.onlyBottom{
	border-top: 0px;
	border-left: 0px;
	border-right: 0px;
	border-bottom: 1px dashed #898989;
}
.tbl_main{
	border-collapse: collapse;
	border: solid red;
	border-width: 1px 0 0 1px;
	width: 80%;
}
.tbl_main td{
	border: solid red;
	border-width: 0 1px 1px 0;
}
.noworkday{
	background-color: #F9CC76;
	cursor: pointer;
}
.workday{
	cursor: pointer;
}
</style>
</head>
<body>
	<form name="form1" id="form1" method="post" enctype="multipart/form-data" action="${ctx }/workday_add.do">
		<input id="wordays" type="hidden" name="wordays" />
		<input id="type" type="hidden" name="type" value="${type }"/>
		<!-- 
	<div class="pagescon">
		<p class="p5">
			<span class="toparr">当前位置：</span>
			<span class="hscol">日历新增</span>
		</p>
	</div> -->
		
		<fieldset class="p10 mt5" style="border:1px #B8B8B8 solid;">
			<legend class="legend">
				<span class="legendarr">日历</span>
			</legend>
			<!-- 
			<table class="infotan mt5" width="100%">
				<tr>
					<td width="12%" class="bgs ls">姓名：</td>
					<td width="38%"><input readonly="readonly" value="${zxwd.name }" type="text" name="name" id="name" style="width: 50%;" class="onlyBottom"/><span style="color: red">*</span></td>
				  	<td width="12%" class="bgs ls">联系电话：</td>
				 	<td width="38%"><input readonly="readonly" value="${zxwd.phone }" type="text" name="phone" id="phone" style="width: 50%;" class="onlyBottom"/><span style="color: red">*</span></td>
				</tr>
				<tr>
				 	<td width="12%" class="bgs ls">单位名称：</td>
				 	<td width="" colspan="3"><input readonly="readonly" value="${zxwd.address }" type="text" name="address" id="address" style="width: 50%;" class="onlyBottom"/><span style="color: red">*</span></td>
				</tr>
			</table>
			 -->
			<div>
				<table>
					<Tr>
						<td><div style="height: 25px;width: 60px;background-color: #F9CC76;">&nbsp;</div></td>
						<td> 代表非工作日(节假日)</td>
					</Tr>
				</table>
				
			</div>
			<div style="width: 80%;margin: auto;text-align: center;">
				<table style="width: 100%;border-collapse: collapse;border: 1px solid #CCCCCC;">
					<tr>
						<td style="text-align: left;"><input type="button" value="<<" onclick="last()" class="btn" title="上一月"/></td>
						<td><div id="div_year" style="width: 200px;"><select id="sel_year" onchange="yearsel()" style="width: 100%;"></select></div></td>
						<td><div id="div_month" style="width: 200px;"><select id="sel_month" onchange="monthsel()" style="width: 100%;"></select></div></td>
						<td style="text-align: right;"><input type="button" value=">>" onclick="next()" class="btn" title="下一月"/></td>
					</tr>
				</table>
			</div>
			<div style="width: 80%;margin: auto;text-align: center;">
				<div id="div_1" style="display: none;"></div>
				<div id="div_2" style="display: none;"></div>
				<div id="div_3" style="display: none;"></div>
				<div id="div_4" style="display: none;"></div>
				<div id="div_5" style="display: none;"></div>
				<div id="div_6" style="display: none;"></div>
				<div id="div_7" style="display: none;"></div>
				<div id="div_8" style="display: none;"></div>
				<div id="div_9" style="display: none;"></div>
				<div id="div_10" style="display: none;"></div>
				<div id="div_11" style="display: none;"></div>
				<div id="div_12" style="display: none;"></div>
			</div>
			
			
		</fieldset>
		
		<div class="cbo tc mt5">  
	  		<a href="#" target="_self" onclick="sub()"><input class="btn" type="button" name="button" id="button" value="提 交" /></a>
	  		&nbsp; 
	  		<a href="#" target="_self" onclick="goHistroy();"><input class="btn" type="button" name="button" id="button" value="返 回" /></a>
		</div>
	</form>
	
	
	<script src="${ctx}inpages/js/jquery-1.7.1.min.js"></script>
	<script src="${ctx}inpages/js/jquery.tab.js"></script>
	<script src="${ctx }js/dialog/artDialog.js"></script>
	
	<script type="text/javascript">
		var cdiv=null;//当前显示的月份层
		window.onload=function(){
			init();
		};
		function g(id){
			return document.getElementById(id);
		}
		function init(){
			var div_year=g('div_year');
			var div_month=g('div_month');
			
			//初始化年份框
			var yearstr='<select id="sel_year" onchange="yearsel()" style="width: 100%;">';
			for(var i=2000;i<=2020;i++){
				yearstr+='<option value="'+i+'">'+i+'年</option>';
			}
			yearstr+="</select>";
			div_year.innerHTML=yearstr;
			
			//初始化月份框
			var monthstr='<select id="sel_month" onchange="monthsel()" style="width: 100%;">';
			for(var i=1;i<=12;i++){
				monthstr+='<option value="'+i+'">'+i+'月</option>';
			}
			monthstr+='</select>';
			div_month.innerHTML=monthstr;
			var sel_year=g('sel_year');
			var sel_month=g('sel_month');
			//默认选中本年
			var cdate=new Date();
			var cyear=cdate.getFullYear();
			sel_year.value=cyear;
			
			//实例化12个月份div数据
			var tyear=parseInt(sel_year.value,10);
			for(var i=1;i<=12;i++){
				//获取当年当月1号是星期几
				var tdate=new Date(tyear,i-1,1);
				var weekday=tdate.getDay();
				//if(weekday==0){weekday=7;}
				//获取当年当月月底是几号(总共多少天)
				tdate=new Date(tyear,i,0);//取巧方式
				var monthday=tdate.getDate();//alert(monthday);
				var str="<table id=\"tbl-"+i+"\" class=\"infotan\" width=\"100%\">";
				str+="<tr><td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td></tr>";
				var index=0;//td总数计数器，非常重要
				var index1=1;//月份天数计数器，非常重要
				for(var j=1;j<=6;j++){
					str+="<tr>"; 
						for(var k=1;k<=7;k++){
							var value="";//td内的值
							if(index>=weekday&&index1<=monthday){
								value=index1;
								if(k==1||k==7){
									str+="<td class=\"\" style=\"background-color: #F9CC76;cursor: pointer;\" title=\"点击设置工作日\" onclick=\"setNoworkday(this)\">"+value+"</td>";//默认选中星期天和星期六为节假日
								}else{
									str+="<td class=\"\" style=\"cursor: pointer;\" title=\"点击设置工作日\" onclick=\"setNoworkday(this)\">"+value+"</td>";
								};
								index1++;
							}else{
								str+="<td>"+value+"</td>";
							}
							index++;
						}
					str+="</tr>";
				}
				str+="</table>";
				g('div_'+i).style.display='none';
				g('div_'+i).innerHTML=str;
			}
			//显示当月层
			//alert(g('div_1').style.display);
			var disid='div_'+sel_month.value;
			g(disid).style.display='';
			cdiv=g(disid);
			
		};
		function last(){
			var sel_month=g('sel_month');
			//隐藏原月份层
			cdiv.style.display='none';
			//显示新月份层
			var index=parseInt(sel_month.value,10)-1;
			if(index==0)index=1;
			var disid='div_'+index;
			g(disid).style.display='';
			cdiv=g(disid);
			//同步改写月份选择框
			sel_month.selectedIndex=index-1;
		};
		function next(){
			var sel_month=g('sel_month');
			//隐藏原月份层
			cdiv.style.display='none';
			//显示新月份层
			var index=parseInt(sel_month.value,10)+1;
			if(index==13)index=12;
			var disid='div_'+index;
			g(disid).style.display='';
			cdiv=g(disid);
			//同步改写月份选择框
			sel_month.selectedIndex=index-1;
		};
		function monthsel(){
			var sel_month=g('sel_month');
			//隐藏原月份层
			cdiv.style.display='none';
			//显示新月份层
			var index=parseInt(sel_month.value,10);
			var disid='div_'+index;
			g(disid).style.display='';
			cdiv=g(disid);
		};
		function yearsel(){
			var sel_year=g('sel_year');
			var sel_month=g('sel_month');
			//实例化12个月份div数据
			var tyear=parseInt(sel_year.value,10);
			for(var i=1;i<=12;i++){
				//获取当年当月1号是星期几
				var tdate=new Date(tyear,i-1,1);
				var weekday=tdate.getDay();
				//if(weekday==0){weekday=7;}
				//获取当年当月月底是几号(总共多少天)
				tdate=new Date(tyear,i,0);//取巧方式
				var monthday=tdate.getDate();//alert(monthday);
				var str="<table id=\"tbl-"+i+"\" class=\"infotan\" width=\"100%\">";
				str+="<tr><td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td></tr>";
				var index=0;//td总数计数器，非常重要
				var index1=1;//月份天数计数器，非常重要
				for(var j=1;j<=6;j++){
					str+="<tr>"; 
						for(var k=1;k<=7;k++){
							var value="";//td内的值
							if(index>=weekday&&index1<=monthday){
								value=index1;
								if(k==1||k==7){
									str+="<td class=\"\" style=\"background-color: #F9CC76;cursor: pointer;\" title=\"点击设置工作日\" onclick=\"setNoworkday(this)\">"+value+"</td>";//默认选中星期天和星期六为节假日
								}else{
									str+="<td class=\"\" style=\"cursor: pointer;\" title=\"点击设置工作日\" onclick=\"setNoworkday(this)\">"+value+"</td>";
								};
								index1++;
							}else{
								str+="<td>"+value+"</td>";
							}
							index++;
						}
					str+="</tr>";
				}
				str+="</table>";
				g('div_'+i).style.display='none';
				g('div_'+i).innerHTML=str;
			}
			var disid='div_'+sel_month.value;
			g(disid).style.display='';
			cdiv=g(disid);
			
		};
		function setNoworkday(o){//原本用classname判断，但ie6 ie8不兼容
			if(o.style.backgroundColor){
				o.style.backgroundColor='';
			}else{
				o.style.backgroundColor='#F9CC76';
			};
		}
		function sub(){
			var wordays=g('wordays');
			var str="";
			var year=g('sel_year').value;
			for(var i=1;i<=12;i++){
				var month=i<10?"0"+i:""+i;
				var tbl=g('tbl-'+i);
				var trs=tbl.rows;
				for(var j=1;j<trs.length;j++){
					var tds=trs[j].cells;
					for(var k=0;k<tds.length;k++){
						if(!tds[k].style.backgroundColor&&tds[k].innerHTML!=''){
							var day=parseInt(tds[k].innerHTML,10)<10?"0"+tds[k].innerHTML:tds[k].innerHTML;
							str+=year+"-"+month+"-"+day+",";
						}
					}
				}
			};
			if(str.match(/,/))str=str.substring(0,str.length-1);
			wordays.value=str;
			alert("开始新增年度日历，请耐心等待。。。");
			document.getElementById('form1').action="${ctx }/workday_add.do";
			document.getElementById('form1').submit();
		};
		
		function goHistroy(){
			parent.$('.page iframe:visible').attr('src',parent.$('.page iframe:visible').attr('src'));
		}	 
	</script>
</body>
</html>
