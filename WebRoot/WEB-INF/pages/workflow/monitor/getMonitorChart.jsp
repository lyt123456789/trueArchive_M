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
</head>
<body id="allChats">
	<div class="index-cont">
		<div class="chart-list">
			<div class="chart-box fl" id="time-chart"></div>
			<div class="chart-box fr" id="module-chart"></div>
		</div>
		<div class="chart-list">
			<div class="chart-box fl" id="site-chart"></div>
			
		</div>
		<div class="chart-list">
			<div class="chart-box fl" id="zhongduan-chart"></div>
			<!-- <div class="chart-box fr" id="online-chart"></div> -->
			 <div class="chart-box fr" id="banjian-chart"></div>
		</div>
		<div class="chart-box" id="dept-chart" style="width: 1160px"></div>
	</div>
</body>
<script type="text/javascript">

	$('.index-cont').width($('.index-cont').width()*0.85);
	$('#module-chart').width($('#module-chart').width()*0.85) ;
	$('#site-chart').width($('.index-cont').width()) ;
	$('#zhongduan-chart').width($('#zhongduan-chart').width()*0.85) ;
	$('#banjian-chart').width($('#banjian-chart').width()*0.85) ;
	$('#dept-chart').width($('.index-cont').width()) ;
	$('#time-chart').width($('#time-chart').width()*0.85) ; 

	var myChart1 = echarts.init(document.getElementById('time-chart'));
	var option1 = {
	    title: {
	        text: '时间轴',
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
                 var res = params.name+'<br/>'+ params.seriesName +': ' + '<span style="color:#1170dc">'+params.value+'人</span>';  
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
	            data : ['8:00','9:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00']
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
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
	            name:'使用人数',
	            type:'line',
	            stack: '总量',
	            areaStyle: {
	            	color: {
	            		type: 'linear',
					    x: 0,
					    y: 1,
					    x2: 0,
					    y2: 0,
					    colorStops: [{
					        offset: 0, color: '#ecf5fe' // 0% 处的颜色
					    }, {
					        offset: 1, color: '#82baf8' // 100% 处的颜色
					    }],
					    globalCoord: false // 缺省为 false
	            	}
	            },
	            lineStyle: {color: '#2884ed'},
	            itemStyle: {color: '#2884ed'},
	            smooth:true,
	            data:[20, 85, 70, 40, 25, 45, 55, 40, 55, 30, 10]
	        }
	    ]
	};
	myChart1.setOption(option1);

	var myChart2 = echarts.init(document.getElementById('module-chart'));
	var option2 = {
		title: {
	        text: '各模块',
	        left: '10px',
	        top: '10px'
	    },
	    tooltip: {
	        trigger: 'item',
	        formatter: "{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            type:'pie',
	            radius: ['40%', '80%'],
	            avoidLabelOverlap: true,
	            itemStyle:{ 
                    normal:{ 
                        label:{ 
                           show: true, 
                           formatter: '{b} : {c} 人' 
                        }, 
                        labelLine :{show:true}
                    } 
                },
	            data:[
	                {value:35, name:'邮件使用人数'},
	                {value:27, name:'通知使用人数'},
	                {value:52, name:'云存储使用人数'},
	                {value:105, name:'公文使用人数'}
	            ],
	            color: ['#fab15b','#508cfa','#82c34d','#f65e73']
	        }
	    ]
	};
	myChart2.setOption(option2);
	var siteChartHeight = '${siteCount}px';
	document.getElementById('site-chart').style.height = siteChartHeight;
	//document.getElementById('site-chart').style.width = "96%";
	var myChart3 = echarts.init(document.getElementById('site-chart'));
	var siteNames = '${siteNames}';
	var siteDfCounts = '${siteDfCounts}';
	var siteNameArr = siteNames.split(",");//站点
	var siteDfCountArr = new Array();//站点办件数
	var siteDfCountArrStr = siteDfCounts.split(',');
	
	for(var i = 0; i < siteDfCountArrStr.length; i++){
		siteDfCountArr[i] = parseInt(siteDfCountArrStr[i]);
	}
	var option3 = {
	    title: {
	        text: '站点划分',
	        left: '10px',
	        top: '10px'
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    color: '#4ea9f0',
	    height: siteChartHeight,
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '5%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'value',
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
			    splitLine: {
			    	show: true,
			    	lineStyle: {
			    		color: '#f0f0f0'
			    	}
			    }
	        }
	    ],
	    yAxis: {
	        type: 'category',
	        data: siteNameArr,
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
		    splitLine: {
		    	show: true,
		    	lineStyle: {
		    		color: '#f0f0f0'
		    	}
		    }
	    },
	    series: [
	        {
	            type: 'bar',
	            barWidth: '20',
	            data: siteDfCountArr
	        }
	    ]
	};
	myChart3.setOption(option3);

	var myChart4 = echarts.init(document.getElementById('banjian-chart'));
	var itemTypes = '${itemTypes}';
	var itemTypeDfCounts = '${itemTypeDfCounts}';
	var itemTypeArr = itemTypes.split(",");//事项类型
	var itemTypeDfCountArr = new Array();//事项类型办件数
	var itemTypeDfCountArrStr = itemTypeDfCounts.split(',');
	for(var i = 0; i < itemTypeDfCountArrStr.length; i++){
		itemTypeDfCountArr[i] = parseInt(itemTypeDfCountArrStr[i]);
	}
	var option4 = {
	    title: {
	        text: '办件数',
	        left: '10px',
	        top: '10px'
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    color: '#fc6e55',
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '5%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type: 'category',
	            data: itemTypeArr,
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
			    splitLine: {
			    	show: true,
			    	lineStyle: {
			    		color: '#f0f0f0'
			    	}
			    }
	        }
	    ],
	    yAxis: {
	        type: 'value',
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
		    splitLine: {
		    	show: true,
		    	lineStyle: {
		    		color: '#f0f0f0'
		    	}
		    }
	    },
	    series: [
	        {
	            type: 'bar',
	            barWidth: '30',
	            data: itemTypeDfCountArr
	        }
	    ]
	};
	myChart4.setOption(option4);

	var myChart5 = echarts.init(document.getElementById('zhongduan-chart'));
	var option5 = {
		title: {
	        text: '各终端',
	        left: '10px',
	        top: '10px'
	    },
	    tooltip: {
	        trigger: 'item',
	        formatter: "{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            type:'pie',
	            radius: ['50%', '70%'],
	            avoidLabelOverlap: true,
	            itemStyle:{ 
                    normal:{ 
                        label:{ 
                           show: true, 
                           formatter: '{b} : {c} 人' 
                        }, 
                        labelLine :{show:true},
                        borderColor: '#fff',
                		borderWidth: 2,
                    } 
                },
	            data:[
	                {value:50, name:'iOS端使用人数'},
	                {value:64, name:'Android端使用人数'},
	                {value:105, name:'PC端使用人数'},
	            ],
	            color: ['#3e82ff','#9cc936','#ff7447']
	        }
	    ]
	};
	myChart5.setOption(option5);

	/* var myChart6 = echarts.init(document.getElementById('online-chart'));
	var option6 = {
	    title: {
	        text: '登录(在线人数)',
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
                 var res = params.name+'<br/>'+ params.seriesName +': ' + '<span style="color:#1170dc">'+params.value+'人</span>';  
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
	            data : ['8:00','9:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00']
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
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
	            name:'在线人数',
	            type:'line',
	            stack: '总量',
	            areaStyle: {
	            	color: {
	            		type: 'linear',
					    x: 0,
					    y: 1,
					    x2: 0,
					    y2: 0,
					    colorStops: [{
					        offset: 0, color: '#eaf6fd' // 0% 处的颜色
					    }, {
					        offset: 1, color: '#bce5ff' // 100% 处的颜色
					    }],
					    globalCoord: false // 缺省为 false
	            	}
	            },
	            lineStyle: {color: '#5ab4ec'},
	            itemStyle: {color: '#5ab4ec'},
	            smooth:true,
	            data:[20, 85, 70, 40, 25, 45, 55, 40, 55, 30, 10]
	        }
	    ]
	};
	myChart6.setOption(option6); */

	var myChart7 = echarts.init(document.getElementById('dept-chart'));
	var actDeptNames = '${actDeptNames}';
	var deptCounts = '${deptCounts}';
	var actDeptNamesArr = actDeptNames.split(",");//部门名称
	var deptCountsArr = new Array();//部门班级那统计
	var deptCountsArrStr = deptCounts.split(',');
	for(var i = 0; i < deptCountsArrStr.length; i++){
		deptCountsArr[i] = parseInt(deptCountsArrStr[i]);
	}
	
	var option7 = {
	    title: {
	        text: '活跃部门Top10',
	        left: '10px',
	        top: '10px'
	    },
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    color: '#4ea9f0',
	    grid: {
	        left: '3%',
	        right: '5%',
	        bottom: '5%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type: 'category',
	            data:  actDeptNamesArr,
	            axisLine: {
	            	lineStyle: {
	            		color: '#f0f0f0'
	            	}
	            },
	            axisLabel: {
                    textStyle: {
                        color: '#72879b',
                    },
                    interval: 0,
                    formatter:function(value)  
                    {  
                        var ret = "";//拼接加\n返回的类目项  
                        var maxLength = 5;//每项显示文字个数  
                        var valLength = value.length;//X轴类目项的文字个数  
                        var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                        if (rowN > 1)//如果类目项的文字大于3,  
                        {  
                            for (var i = 0; i < rowN; i++) {  
                                var temp = "";//每次截取的字符串  
                                var start = i * maxLength;//开始截取的位置  
                                var end = start + maxLength;//结束截取的位置  
                                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                temp = value.substring(start, end) + "\n";  
                                ret += temp; //凭借最终的字符串  
                            }  
                            return ret;  
                        }  
                        else {  
                            return value;  
                        }  
                    }  
                
                },
                axisTick:{
			        show:false
			    },
			    splitLine: {
			    	show: true,
			    	lineStyle: {
			    		color: '#f0f0f0'
			    	}
			    }
	        }
	    ],
	    yAxis: {
	        type: 'value',
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
		    splitLine: {
		    	show: true,
		    	lineStyle: {
		    		color: '#f0f0f0'
		    	}
		    }
	    },
	    series: [
	        {
	            type: 'bar',
	            barWidth: '30',
	            data: deptCountsArr
	        }
	    ]
	};
	myChart7.setOption(option7);

 	document.getElementById('allChats').style.overflow = "scroll";
 
 </script>
</html>