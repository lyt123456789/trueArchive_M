<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>公文统计</title>
        <link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
	   <script type="text/javascript" src="${ctx}/widgets/component/FusionCharts/FusionCharts.js"></script>
        <script type="text/javascript" src="${ctx}/widgets/component/My97DatePicker/WdatePicker.js">
            function WdatePicker(){
                $dp.show();
            }
        </script>
		
        <script type="text/javascript">
            window.onload = function(){
            };
            
            function toToday(){
                var c = new Date();
                var year = c.getFullYear();
                var month = c.getMonth() + 1;
                var date = c.getDate();
                if (month < 10) {
                    month = '0' + month
                };
                if (date < 10) {
                    date = '0' + date
                };
                document.getElementById('i_starttime').value = year + '-' + month + '-' + date;
                document.getElementById('i_endtime').value = year + '-' + month + '-' + date;
            }
            
            function toWeek(){
                document.getElementById("start").style.display = "none";
                
                var c = new Date();
                var day = c.getDay();
                if (day == 0) {
                    day = 7
                };
                var allmin = c.getTime();
                
                var c1 = new Date();
                c1.setTime(allmin - (day - 1) * 24 * 60 * 60 * 1000);
                var year1 = c1.getFullYear();
                var month1 = c1.getMonth() + 1;
                var date1 = c1.getDate();
                if (month1 < 10) {
                    month1 = '0' + month1
                };
                if (date1 < 10) {
                    date1 = '0' + date1
                };
                
                var c2 = new Date();
                c2.setTime(allmin + (7 - day) * 24 * 60 * 60 * 1000);
                var year2 = c2.getFullYear();
                var month2 = c2.getMonth() + 1;
                var date2 = c2.getDate();
                if (month2 < 10) {
                    month2 = '0' + month2
                };
                if (date2 < 10) {
                    date2 = '0' + date2
                };
                
                document.getElementById('i_starttime').value = year1 + '-' + month1 + '-' + date1;
                document.getElementById('i_endtime').value = year2 + '-' + month2 + '-' + date2;
            }
            
            function toMonth(){
                document.getElementById("start").style.display = "none";
                
                var c = new Date();
                var year = c.getFullYear();
                var month = c.getMonth() + 1;
                var c1 = new Date(year + '/' + (month + 1) + '/0');
                var date = c1.getDate();
                if (month < 10) {
                    month = '0' + month
                };
                if (date < 10) {
                    date = '0' + date
                };
                document.getElementById('i_starttime').value = year + '-' + month + '-01';
                document.getElementById('i_endtime').value = year + '-' + month + '-' + date;
            }
            
            function toYear(){
                document.getElementById("start").style.display = "none";
                var c = new Date();
                var year = c.getFullYear();
                document.getElementById('i_starttime').value = year + '-01-01';
                document.getElementById('i_endtime').value = year + '-12-31';
            }
            
            function toQuertor(){
            
                document.getElementById("start").style.display = "none";
                
                var c = new Date();
                var year = c.getFullYear();
                var month = c.getMonth() + 1;
                var date = c.getDate();
                var jijie = [{
                    s: 1,
                    e: 3,
                    ss: year + '-01-01',
                    ee: year + '-03-31'
                }, {
                    s: 4,
                    e: 6,
                    ss: year + '-04-01',
                    ee: year + '-06-30'
                }, {
                    s: 7,
                    e: 9,
                    ss: year + '-07-01',
                    ee: year + '-09-30'
                }, {
                    s: 10,
                    e: 12,
                    ss: year + '-10-01',
                    ee: year + '-12-31'
                }];
                for (var i = 0; i < jijie.length; i++) {
                    var o = jijie[i];
                    if (month >= o.s && month <= o.e) {
                        document.getElementById('i_starttime').value = o.ss;
                        document.getElementById('i_endtime').value = o.ee;
                        break;
                    }
                }
                
            }
        </script>
        <script type="text/javascript">
            function defineself(){
                document.getElementById("start").style.display = "block";
            }
            
            function endno(){
                document.getElementById("end").style.display = "none";
            }
            
            function endshow(){
                document.getElementById("end").style.display = "block";
            }
            
            function gogo(){
            	 if (document.getElementById("self").checked) {
            	    if(!checkdate()){
                       return false;
                     }
                 }
                var chcs = document.getElementsByName('radio');
                var count = 0;
                for (var i = 0; i < chcs.length; i++) {
                    if (chcs[i].checked === true) {
                        count = 1;
                        break;
                    }
                }
                if (count == 0) {
                    alert("请选择一种时间条件！");
                    return false;
                }
                
                if (document.getElementById("self").checked) {
                
                    if (document.getElementById('sel_day_start').value == "") {
                        alert("请填写起始时间！");
                        return false;
                    }
                    if (document.getElementById('sel_day_end').value == "") {
                        alert("请填写结束时间！");
                        return false;
                    }
                }
                
				if(document.getElementById('sel_day_start').value != ""){
					document.getElementById('i_starttime').value = document.getElementById('sel_day_start').value;
				}
				
				if(document.getElementById('sel_day_end').value != ""){
					document.getElementById('i_endtime').value = document.getElementById('sel_day_end').value;
				}
                document.getElementById('form1').action = '${ctx}/docStatistics_toStatistics.do';
                document.getElementById('form1').target = '_self';
                document.getElementById('form1').submit();
                
            }

            function checkdate(){
                  var startDate= document.getElementById("sel_day_start").value;
                  var endDate=document.getElementById("sel_day_end").value;
                
                

                  var startDateTemp = startDate.split("-");   
                  var endDateTemp = endDate.split("-"); 
                  var allStartDate = new Date(startDateTemp[0],startDateTemp[1],startDateTemp[2]);   
                  var allEndDate = new Date(endDateTemp[0],endDateTemp[1],endDateTemp[2]);
                  
                  if(allStartDate.getTime()>allEndDate.getTime()){ 
                      alert("开始时间不能大于结束时间！");  
                      return false;   
                  } else{ 
                      return true;
                  }
                   
                    
            }
        </script>
    </head>
    <body>
        <form id="form1" action="${ctx}/docStatistics_toStatistics.do" method="post">
            <input name="startTime" id="i_starttime" type="hidden" value="${starttime}">
			<input name="endTime" id="i_endtime" type="hidden" value="${endtime}">
              <div class="bread-box"><div class="bread-title"><span class="bread-icon"></span><span class="bread-links"><span class="bread-start">当前位置：</span><span class="bread">领导模块 </span><span class="bread-split">&raquo;</span><span class="bread">公文统计</span></span><span class="bread-right-border"></span></div></div>
              
            <table class="formTable" width="100%" border="0" cellspacing="5" cellpadding="0" class="chartTab" >
                <tr>
                    <td width="120" height="40" class="ctabname">
                       	 统计条件
                    </td>
                    <td class="ctabselected pl20">
                        <table>
	        	<tr><td>
	        	<input mice-radio="radio" name="radio" value="week" checked="checked" <c:if test="${radio=='week'}">checked</c:if> onclick="toWeek()" />本 周
	        	<input mice-radio="radio" type="radio" name="radio" value="month" <c:if test="${radio=='month'}">checked</c:if> onclick="toMonth()" />本 月
	        	<input mice-radio="radio" type="radio" name="radio" value="quertor" <c:if test="${radio=='quertor'}">checked</c:if> onclick="toQuertor()" />本 季
	        	<input mice-radio="radio" type="radio" name="radio" value="year" <c:if test="${radio=='year'}">checked</c:if> onclick="toYear()" />本 年
	        	<input mice-radio="radio" type="radio"   name="radio" value="self"  id="self" <c:if test="${radio=='self'}">checked</c:if> onclick="defineself()" />自定义
	        	</td>
	        	   <td id="start" <c:if test="${radio!='self'}"> style="display: none" </c:if> align="right" >
                    <!--日-->
                    <input mice-input="input" id="sel_day_start" name="sel_day_start" value="${sel_day_start}" type="text" onclick="WdatePicker()" class="Wdate">
                    <!--日-->
                    <input mice-input="input" id="sel_day_end" name="sel_day_end" type="text" value="${sel_day_end}" onclick="WdatePicker()" class="Wdate">
            </td>
	        	</tr>
	        	</table>
	     			 <input type="button" class="btn"   value="查 询" onclick="gogo()">
                    </td>
                </tr>
            </table>
            <div class="bb6dotted mlr5">
            </div>
            <div class="bb6dotted mlr5">
            </div>
            <table width="100%" border="0" cellspacing="5" cellpadding="0" class="formTable">
				<tr>
                    <td width="120" height="40" class="ctabname">
                       	 展现方式
                    </td>
                    <td class="ctabselected pl20">
                        <table>
                            <tr>
                                <td>
                                    <input mice-btn="icon-search" type="button" value="列 表" class="btn" onclick="changeChart(-1)"/>
									<input mice-btn="icon-search" type="button" value="柱状图" class="btn" onclick="changeChart(0)"/>
									<input mice-btn="icon-search" type="button" value="饼状图" class="btn" onclick="changeChart(1)"/>
									<input mice-btn="icon-search" type="button" value="折线图" class="btn" onclick="changeChart(2)"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form><hr>
		<c:if test="${starttime!=null && starttime!=''}">
			<div id="xinxi" align="left"  style="height: 30px;margin-top: 20px;"> 从${starttime }到${endtime }公文系统发文办文情况统计:</div>
		</c:if>
		<div id="div_liebiao">
		<display:table class="displayTable" name="docCount" pagesize="1" htmlId="displayTable" id="element" partialList="true" export="false" size="1" excludedParams="*" form="chaxunform">
				<display:column headerClass="split" class="docNum" title="收文数量" sortable="false" >${element.swsl}</display:column>
				<display:column headerClass="split" class="docNum" title="发文数量"sortable="false">${element.fwsl}</display:column>
				<display:column headerClass="split" class="docNum" title="办文数量"sortable="false">${element.bwsl}</display:column>
				<display:column headerClass="split" class="docNum" title="已办数量"sortable="false">${element.ybsl}</display:column>
				<display:column headerClass="split" class="docNum" title="在办数量" sortable="false" >${element.zbsl}</display:column>
				<display:column headerClass="split" class="docNum" title="传阅数量"sortable="false">${element.cysl}</display:column>
				<display:column headerClass="split" class="docNum" title="在传数量"sortable="false">${element.zcsl}</display:column>
				<display:column headerClass="split" class="docNum" title="传阅结束数量"sortable="false">${element.cyjssl}</display:column>
			</display:table>
		</div>
		<div id="chartbox" style="display: none;width: 99%;text-align: center;">
		<table style="border-collapse: collapse;margin: auto;">
			<tr>
				<td valign="middle" align="right">
					<span id="flash_y" style="font-weight: bold;color: #363636">数量</span>
				</td>
				<td>
					<div id="chartdiv" align="center" ></div>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript" src="${cdn_js}/sea.js"></script>
	<script type="text/javascript">
		//该方法仅用于flash图表
   		// emp:  addStrBr('我们你们他们',3,'&#xA;')
   		function addStrBr(str,index,br){
			var count=str.length%index==0?str.length/index:parseInt(str.length/index,10)+1;
			var returnstr='';
			for(var i=0;i<count;i++){
				returnstr+=i==count-1?str.substr(i*index,index):str.substr(i*index,index)+br;
			}
			return returnstr;
   		}
		/*
			参数index:
			-1 代表 列表
			0代表 柱状图
			1代表 饼状图
			2代表 折线图
			3代表 条形图
		*/
		function changeChart(index){
			if(!ishavedata){
				alert("请先统计数据后在查看!");
				return false;
			}
			if(index==-1){
				document.getElementById('div_liebiao').style.display='block';
				document.getElementById('chartbox').style.display='none';
				document.getElementById('xinxi').style.display='block';
			}else if(index==0){
				document.getElementById('div_liebiao').style.display='none';
				document.getElementById('chartbox').style.display='block';
				document.getElementById('xinxi').style.display='none';
				//document.getElementById('chartdiv').style.width=swfHeight+'px';
				showSWF(index,"办公类型","数量");		
						
			}else if(index==1){
				document.getElementById('div_liebiao').style.display='none';
				document.getElementById('chartbox').style.display='block';
				document.getElementById('xinxi').style.display='none';
				
				//document.getElementById('chartdiv').style.width=swfHeight+'px';
				showSWF(index,"","");
			}else if(index==2){
				document.getElementById('div_liebiao').style.display='none';
				document.getElementById('chartbox').style.display='block';
				document.getElementById('xinxi').style.display='none';
				//document.getElementById('chartdiv').style.width=swfHeight+'px';
				showSWF(index,"办公类型","数量");
			}else if(index==3){
				document.getElementById('div_liebiao').style.display='none';
				document.getElementById('chartbox').style.display='block';
				document.getElementById('xinxi').style.display='none';
				//document.getElementById('chartdiv').style.width=swfHeight+'px';
				showSWF(index,"数量","办公类型");
			}
   		}
   		//设置flash标题文字
		function setSWFTitle(){
			var starttime='${starttime}';
			var endtime='${endtime}';
			return '从'+starttime+'到'+endtime+'公文系统发文办文情况统计';
		};
		//显示flash    x:x轴文字  y：y轴文字
		function showSWF(index,x,y){
			var newstr=str;
			var newstr=newstr.replace(/xAxisName=''/,"xAxisName='"+x+"'");
			document.getElementById('flash_y').innerHTML='';
			
			var swfs=['FCF_Column3D.swf','FCF_Pie3D.swf','FCF_Line.swf','FCF_Bar2D.swf'];
   			var myChart = new FusionCharts("${ctx}/widgets/component/FusionCharts/"+swfs[index], "myChartId", swfWidth, swfHeight);
   			myChart.setDataXML(newstr);
   			myChart.render("chartdiv");

   			document.getElementById('flash_y').innerHTML=y;
		}
		var col=['AFD8F8','F6BD0F','8BBA00','FF8E46','008E8E','D64646','8E468E','588526','B3AA00','008ED6','9D080D','A186BE'];
		var count=0;
		//获得页面宽度
		var pageWidth=document.documentElement.offsetWidth;
		var swfWidth=Math.round(pageWidth*8/10);//页面宽度的百分之六十
		var swfHeight=Math.round(swfWidth*2/3);//flash高宽比2:3
		//图表
		var myChart = new FusionCharts("${ctx}/widgets/component/FusionCharts/FCF_Column3D.swf", "myChartId", swfWidth, swfHeight);
		/*
			caption:最上居中标题名称  		xAxisName:X轴名称	
			yAxisName:Y轴名称	baseFontSize:字体大小	
			bgColor:图表背景色，6位16进制颜色值
			canvasBgColor:画布背景色，6位16进制颜色值
			字符串'&#xA;'起换行作用
		*/
		var ishavedata=false;
		var str="<graph caption='"+setSWFTitle()+"'  xAxisName='' yAxisName='' showNames='1' decimalPrecision='0' formatNumberScale='0' baseFontSize='13'>";
		//str+="<set name='我们&#xA;一起' value='100' color='"+col[11]+"'/>";
		
		//需要修改的地方  开始
		if('${docCount}'!=''){
			ishavedata=true;
			str+="<set name='收文数量' value='${docCount.swsl}' color='"+col[count]+"'/>";
			count++;if(count==12){count=0;};
			str+="<set name='发文数量' value='${docCount.fwsl}' color='"+col[count]+"'/>";
			count++;if(count==12){count=0;};
			str+="<set name='办文数量' value='${docCount.bwsl}' color='"+col[count]+"'/>";
			count++;if(count==12){count=0;};
			str+="<set name='已办数量' value='${docCount.ybsl}' color='"+col[count]+"'/>";
			count++;if(count==12){count=0;};
			str+="<set name='在办数量' value='${docCount.zbsl}' color='"+col[count]+"'/>";
			count++;if(count==12){count=0;};
			str+="<set name='传阅数量' value='${docCount.cysl}' color='"+col[count]+"'/>";
			count++;if(count==12){count=0;};
			str+="<set name='在传数量' value='${docCount.zcsl}' color='"+col[count]+"'/>";
			count++;if(count==12){count=0;};
			str+="<set name='已传数量' value='${docCount.cyjssl}' color='"+col[count]+"'/>";
			count++;if(count==12){count=0;};
		}
		str+="</graph>";
		myChart.setDataXML(str);
		myChart.render("chartdiv");
		seajs.use('lib/form',function(){
			$('input[mice-btn]').cssBtn();
			$('input[mice-input]').cssInput();
			$('select[mice-select]').cssSelect();
			$('input[mice-radio]').cssRadio();
	    });
	</script>
    </body>
</html>
