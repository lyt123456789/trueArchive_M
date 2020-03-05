<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/monitor/css/reset.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/monitor/css/index.css">
	<script type="text/javascript" src="${ctx}/monitor/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="${ctx}/monitor/js/echarts.min.js"></script>
	<style type="text/css">
		.bj-img{
			padding: 10px 0;
		}
		.bj-tit{
			padding-bottom: 5px;
		}
		.tw-result  span.success i {
       		 background: url(${ctx}/monitor/img/banjian-3.png) ;	
		}
	</style>
	
	
</head>
<body style="overflow: scroll;zoom:0.88">
<div class="index-cont">
		<div class="chart-list" style="height: 350px">
		<div class="chart-box fl" id="time-chart">
		<div class="interface-cf" style="height: 275px">
		<p class="cf-tit">实时情况</p>
			<ul class="banjian-list" style="height: 260px">
				<li style="height: 260px;width: 175px">
					<p class="bj-img" style=" padding: 15px 0;"><img src="${ctx}/monitor/img/banjian-1.png"></p>
					<p class="bj-tit" style=" padding-bottom: 10px;">无归属办件</p>
					<p class="bj-num"><span class="bj-dw">${noOwnerDfCount }</span>件</p>
				</li>
				<li style="height: 260px;width: 175px">
					<p class="bj-img" style=" padding: 15px 0;"><img src="${ctx}/monitor/img/banjian-2.png"></p>
					<p class="bj-tit" style=" padding-bottom: 10px;">意见缺失件</p>
					<p class="bj-num"><span class="bj-dw">${lostCmtDfCount }</span>件</p>
				</li>
				<li style="height: 260px;width: 175px">
					<p class="bj-img" style=" padding: 15px 0;"><img src="${ctx}/monitor/img/banjian-3.png"></p>
					<p class="bj-tit" style=" padding-bottom: 10px;">附件缺失件</p>
					<p class="bj-num"><span class="bj-dw">${lostAttsDfCount }</span>件</p>
				</li >
			</ul>
			</div></div>
	     	<div class="chart-box fr" id="module-chart">
				 <div class="interface-cf">
					<p class="cf-tit">接口异常检查</p>
						<ul class="interface-ul" >
					<c:forEach items="${failInterfaceList}" var="failInterface" varStatus="status">
				
			    	<li>
							<p class="ul-tit">${failInterface.interfaceName} </p>
							<c:choose>
						<c:when test="${failInterface.result == '2' }">
							<button class="ul-btn">接口异常</button>
						</c:when>
						<c:otherwise>
							<button class="interface-btn wait-btn">等待检测</button>
						</c:otherwise>
						</c:choose>
					</li>
					
		    	   </c:forEach>  
		    	   </ul>
		    	  <c:if test="${failInterfaceList == null||fn:length(failInterfaceList)==0}">
		    	   <table style="width: 100%;height: 100%">
				 <tr> <td >
				 <div style="text-align: center"> <img src="${ctx}/monitor/img/noError.png" /></div>
				 
				 
				 </td></tr>
				 </table>
		    	  </c:if>
				</div> 
				  
			</div>
		</div>
		<c:if test="${cpuRates1 !=''&&memoryRates1 !=''&& cpuRates1!=null && memoryRates1!=null}">
		<div class="chart-list">
		<p class="cf-tit">主机1</p>
			<div class="chart-box fl" id="cpu-chart"></div>
			<div class="chart-box fr" id="memory-chart" ></div>
		</div>
		</c:if>
		<c:if test="${cpuRates2 !=''&&memoryRates2 !=''&& cpuRates2!=null && memoryRates2!=null}">
		<div class="chart-list">
		<p class="cf-tit">主机2</p>
			<div class="chart-box fl" id="cpu-chart2"></div>
			<div class="chart-box fr" id="memory-chart2" ></div>
		</div>
		</c:if>
	</div>
</body>
<script type="text/javascript">
  /*  $(".interface-cf").height($("#module-chart").height());  */

	var myChart1 = echarts.init(document.getElementById('cpu-chart'));
	var times = '${times1}';
	var cpuRates = '${cpuRates1}';
	var memoryRates = '${memoryRates1}';
	var timeArr = times.split(",");
	var cpuRatesArr = new Array();
	var cpuArr = cpuRates.split(',');
	for(var i=0;i<cpuArr.length;i++){
		cpuRatesArr[i] = parseInt(cpuArr[i]);
	}
	var memoryRatesArr = new Array();
	var memoryArr = memoryRates.split(',');
	for(var i=0;i<memoryArr.length;i++){
		memoryRatesArr[i] = parseInt(memoryArr[i]);
	}
	var option1 = {
	    title: {
	        text: 'CPU',
	        left: '10px',
	        top: '10px'
	    },
	    tooltip: {
	        trigger : 'item',  
            show:true,  
            showDelay: 0,  
            hideDelay: 0,  
            transitionDuration:0,   
            backgroundColor : '#fff',  
            borderColor : '#dbe1f5',  
            textStyle:{
            	color: '#666'
            },
            borderRadius : 5,  
            borderWidth: 1,  
            padding: [5,10],
            formatter: function (params,ticket,callback) {  
                // console.log(params);  
                 var res = params.name+'<br/>'+ params.seriesName +': ' + '<span style="color:#fa5d5d">'+params.value+'%</span>';  
                 return res;  
            }  
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '5%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            axisLine: {
	            	lineStyle: {
	            		color: '#f0f0f0'
	            	}
	            },
	            axisLabel: {
                    textStyle: {
                        color: '#72879b',
                    }
                },
                axisTick:{
			        show:false
			    },
	            data : timeArr
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            position: 'left',
	            axisLine: {
	            	lineStyle: {
	            		color: '#f0f0f0'
	            	}
	            },
	            axisLabel: {
	                textStyle: {
	                    color: '#72879b',

	                },
	            	formatter: '{value}%'
	            },
	            splitLine: { 
	            	lineStyle: {
		            	color: '#f0f0f0'
		            }
		        },
		        axisTick:{
			        show:false
			    },
	        },
	    ],
	    series : [
	        {
	            name:'CPU使用率',
	            type:'line',
	            symbol: 'circle',
	            symbolSize: 6,
	            areaStyle: {color: '#d6f4ed'},
	            lineStyle: {color: '#0abb92'},
	            itemStyle: {color: '#0abb92'},
	            data: cpuRatesArr
	        }
	    ]
	};
	myChart1.setOption(option1);

	var myChart2 = echarts.init(document.getElementById('memory-chart'));
	var option2 = {
	    title: {
	        text: '内存',
	        left: '10px',
	        top: '10px'
	    },
	    tooltip: {
	        trigger : 'item',  
            show:true,  
            showDelay: 0,  
            hideDelay: 0,  
            transitionDuration:0,   
            backgroundColor : '#fff',  
            borderColor : '#dbe1f5',  
            textStyle:{
            	color: '#666'
            },
            borderRadius : 5,  
            borderWidth: 1,  
            padding: [5,10],
            formatter: function (params,ticket,callback) {  
                // console.log(params);  
                 var res = params.name+'<br/>'+ params.seriesName +': ' + '<span style="color:#fa5d5d">'+params.value+'%</span>';  
                 return res;  
            }  
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '5%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            axisLine: {
	            	lineStyle: {
	            		color: '#f0f0f0'
	            	}
	            },
	            axisLabel: {
                    textStyle: {
                        color: '#72879b',
                    }
                },
	            data : timeArr
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            position: 'left',
	            axisLine: {
	            	lineStyle: {
	            		color: '#f0f0f0'
	            	}
	            },
	            axisLabel: {
	                textStyle: {
	                    color: '#72879b',
	                },
	                formatter: '{value}%'
	            },
	            splitLine: { 
	            	lineStyle: {
		            	color: '#f0f0f0'
		            }
		        }
	        },
	    ],
	    series : [
	        {
	            name:'内存使用率',
	            type:'line',
	            symbol: 'circle',
	            symbolSize: 6,
	            areaStyle: {color: '#e4edff'},
	            lineStyle: {color: '#3e82ff'},
	            itemStyle: {color: '#3e82ff'},
	            data: memoryRatesArr
	        }
	    ]
	};
	myChart2.setOption(option2);
	
</script>

<script type="text/javascript">
	var myChart3 = echarts.init(document.getElementById('cpu-chart2'));
	var times2 = '${times2}';
	var cpuRates2 = '${cpuRates2}';
	var memoryRates2 = '${memoryRates2}';
	var timeArr2 = times2.split(",");
	var cpuRatesArr2 = new Array();
	var cpuArr2 = cpuRates2.split(',');
	for(var i=0;i<cpuArr2.length;i++){
		cpuRatesArr2[i] = parseInt(cpuArr2[i]);
	}
	var memoryRatesArr2 = new Array();
	var memoryArr2 = memoryRates2.split(',');
	for(var i=0;i<memoryArr2.length;i++){
		memoryRatesArr2[i] = parseInt(memoryArr2[i]);
	}
	var option3 = {
	    title: {
	        text: 'CPU',
	        left: '10px',
	        top: '10px'
	    },
	    tooltip: {
	        trigger : 'item',  
            show:true,  
            showDelay: 0,  
            hideDelay: 0,  
            transitionDuration:0,   
            backgroundColor : '#fff',  
            borderColor : '#dbe1f5',  
            textStyle:{
            	color: '#666'
            },
            borderRadius : 5,  
            borderWidth: 1,  
            padding: [5,10],
            formatter: function (params,ticket,callback) {  
                // console.log(params);  
                 var res = params.name+'<br/>'+ params.seriesName +': ' + '<span style="color:#fa5d5d">'+params.value+'%</span>';  
                 return res;  
            }  
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '5%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            axisLine: {
	            	lineStyle: {
	            		color: '#f0f0f0'
	            	}
	            },
	            axisLabel: {
                    textStyle: {
                        color: '#72879b',
                    }
                },
                axisTick:{
			        show:false
			    },
	            data : timeArr2
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            position: 'left',
	            axisLine: {
	            	lineStyle: {
	            		color: '#f0f0f0'
	            	}
	            },
	            axisLabel: {
	                textStyle: {
	                    color: '#72879b',

	                },
	            	formatter: '{value}%'
	            },
	            splitLine: { 
	            	lineStyle: {
		            	color: '#f0f0f0'
		            }
		        },
		        axisTick:{
			        show:false
			    },
	        },
	    ],
	    series : [
	        {
	            name:'CPU使用率',
	            type:'line',
	            symbol: 'circle',
	            symbolSize: 6,
	            areaStyle: {color: '#d6f4ed'},
	            lineStyle: {color: '#0abb92'},
	            itemStyle: {color: '#0abb92'},
	            data: cpuRatesArr2
	        }
	    ]
	};
	myChart3.setOption(option3);

	var myChart4 = echarts.init(document.getElementById('memory-chart2'));
	var option4 = {
	    title: {
	        text: '内存',
	        left: '10px',
	        top: '10px'
	    },
	    tooltip: {
	        trigger : 'item',  
            show:true,  
            showDelay: 0,  
            hideDelay: 0,  
            transitionDuration:0,   
            backgroundColor : '#fff',  
            borderColor : '#dbe1f5',  
            textStyle:{
            	color: '#666'
            },
            borderRadius : 5,  
            borderWidth: 1,  
            padding: [5,10],
            formatter: function (params,ticket,callback) {  
                // console.log(params);  
                 var res = params.name+'<br/>'+ params.seriesName +': ' + '<span style="color:#fa5d5d">'+params.value+'%</span>';  
                 return res;  
            }  
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '5%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            axisLine: {
	            	lineStyle: {
	            		color: '#f0f0f0'
	            	}
	            },
	            axisLabel: {
                    textStyle: {
                        color: '#72879b',
                    }
                },
	            data : timeArr2
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            position: 'left',
	            axisLine: {
	            	lineStyle: {
	            		color: '#f0f0f0'
	            	}
	            },
	            axisLabel: {
	                textStyle: {
	                    color: '#72879b',
	                },
	                formatter: '{value}%'
	            },
	            splitLine: { 
	            	lineStyle: {
		            	color: '#f0f0f0'
		            }
		        }
	        },
	    ],
	    series : [
	        {
	            name:'内存使用率',
	            type:'line',
	            symbol: 'circle',
	            symbolSize: 6,
	            areaStyle: {color: '#e4edff'},
	            lineStyle: {color: '#3e82ff'},
	            itemStyle: {color: '#3e82ff'},
	            data: memoryRatesArr2
	        }
	    ]
	};
	myChart4.setOption(option4);
	
</script>
</html>