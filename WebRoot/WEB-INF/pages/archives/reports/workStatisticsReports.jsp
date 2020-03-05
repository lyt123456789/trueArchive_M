<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="renderer" content="webkit">
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/list.css">
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/font-awesome.css">
		<style>
			.td{
				font-size: 16px;
			    color: #333333;
			    overflow: hidden;
			    font-family: "Microsoft Yahei";
			    padding: 8px 5px;
			    border-bottom: solid 1px #e4eef5;
			    vertical-align: middle;
			    line-height: 1.5;
			}
			.new-htable {
				margin-top:20px;
				width:96%;
				margin-left:3%;
			}
			.new-htable tr th{
				text-align:right;
				color:#333333;
				font-size:16px;
				font-weight:normal;
				height:46px;
			    vertical-align: middle;
		
			}
			.new-htable tr td{
				text-align:left;
				color:#333333;
				font-size:15px;
				font-weight:normal;
				height:46px;
			    vertical-align: middle;
			}
			.new-htable .tw-form-text{
				width:354px;
				text-indent:6px;
				height:30px;
				border:1px solid #e6e6e6;
				border-radius:3px;
				vertical-align: middle;
			}
			.new-htable select{
				width:163px;
				height:30px;
				border:1px solid #e6e6e6;
				border-radius:3px;
				vertical-align: middle;
			}
			
			.wf-icon-trash{
				cursor:pointer;
				color:red;
			}
			.wf-hover .wf-icon-trash{
				display:inline-block;
			}
			.w-auto-10 {
				width: 9% !important;
				min-width: 9% !important;
			}
			.wf-form-label{
				margin-left: 0px;
			}
			.auto-date-width{
				width:120px!important;
			}
			.high-search-btn{
				font-size:14px;
				color:#4284ce;
				margin-left:10px;
			}
			#high-search{
				top:0;
			}
			.search {
				height:35px;
				line-height:35px;
			}
			.wf-search-bar .wf-form-text {
				font-size:14px;
				padding:0px 4px;
				margin-right:6px;
			}
			label {
				margin-left:10px;
			}
			select.sel {
				width:130px!important;
			}
		</style>
	</head>
	<body>
		<div class="wf-layout">
			<div class="wf-list-top">
				<div class="wf-search-bar">
					<form name="bgForm"  id="bgForm" method="post" style="display:inline-block;width: 100%;">
					    <div class="search">
					    	<label>开始时间：</label>
					    	<input class="wf-form-text wf-form-date" id="startTime"  type="text" name="startTime" value="" readonly="readonly"/>
					    	
			        		<label>结束时间：</label>
			        		<input class="wf-form-text wf-form-date" id="endTime"  type="text" name="endTime" value="" readonly="readonly"/>
			        		
			        		<label>接待人：</label>
			        		<select id="receivePeople" name="receivePeople" class="sel">
		        				<option value="" selected="selected">全部</option>
		        				<c:forEach var="list" items="${listPeople}">
							        <option value="${list.receivePeople }">${list.receivePeople }</option>
								</c:forEach>
		    				</select>
		    				
		    				<label>查档目的：</label>
			        		<select id="cdmd" name="cdmd" class="sel">
		        				<option value="" selected="selected">全部</option>
		        				<c:forEach var="list" items="${listCDMD}">
							        <option value="${list['name'] }">${list['name'] }</option>
								</c:forEach>
		    				</select>
		    				
		    				<label>查阅结果：</label>
			        		<select id="cyjg" name="cyjg" class="sel">
		        				<option value="" selected="selected">全部</option>
		        				<c:forEach var="list" items="${listCYMD}">
							        <option value="${list['name'] }">${list['name'] }</option>
								</c:forEach>
		    				</select>
		    				
					    	<button type="button" onclick="getData()" class="btn_seargjc btn btn-js" style="width:75px;margin-right: 10px;background:">
		        				<img src="${ctx}/img/sear_ico.png" style="vertical-align: middle;margin-right: 5px;">查询
		        			</button>
					    	<button type="button" onclick="exportExcel()" target="_self" class="btn btn-write">
					    		<i class="fa fa-pencil-square-o fa-lg"></i>导出
					    	</button>
			            </div>
					</form>
				</div>
			</div>
			<div class="wf-list-wrap">
				<div class="loading"></div><!--2017-11-10-->
				<table id="tab232323" class="wf-fixtable" layoutH="140"  style="width: 100%;">
					<thead>
						<tr>
			    	  		<th align="center" width="7.69%">接待人</th>
			    	  		<th align="center" width="7.69%">利用人数</th>
			    	  		<th align="center" width="7.69%">利用天数</th>
			    	  		<th align="center" width="7.69%">利用人次</th>
			    	  		<th align="center" width="7.69%">调卷（件）总数</th>
			    	  		<th align="center" width="7.69%">打印件数</th>
			    	  		<th align="center" width="7.69%">打印页数</th>
			    	  		<th align="center" width="7.69%">复印件数</th>
			    	  		<th align="center" width="7.69%">复印页数</th>
			    	  		<th align="center" width="7.69%">摘抄件数</th>
			    	  		<th align="center" width="7.69%">摘抄页数</th>
			    	  		<th align="center" width="7.69%">拍摄件数</th>
			    	  		<th align="center" width="7.69%">拍摄画图数</th>
		                </tr>
			    	</thead>
			    	<tbody id="dataBody">
			    	</tbody>
				</table>
			</div>
		</div>
	</body>
	<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
		Date.prototype.Format = function(fmt) { //author: meizz   
		    var o = {   
		      "M+" : this.getMonth()+1,                 //月份   
		      "d+" : this.getDate(),                    //日   
		      "h+" : this.getHours(),                   //小时   
		      "m+" : this.getMinutes(),                 //分   
		      "s+" : this.getSeconds(),                 //秒   
		      "q+" : Math.floor((this.getMonth()+3)/3), //季度   
		      "S"  : this.getMilliseconds()             //毫秒   
		    };   
		    if(/(y+)/.test(fmt))   
		      fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		    for(var k in o)   
		      if(new RegExp("("+ k +")").test(fmt))   
		    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		    return fmt;   
	    }  		
		
		//获取7天前的日期
		function fun_date(aa){
	        var date1 = new Date();
	        var date2 = new Date(date1);
	        date2.setDate(date1.getDate()-7);
	        var time2 = date2.Format("yyyy-MM-dd");
	        return time2;
	    }

		$(function() {
			var now = new Date().Format("yyyy-MM-dd"); 
			$("#endTime").val(now);
			$("#startTime").val(fun_date(-7));
			getData();
		});
		
		function getAllPeople() {
			//异步获取上传成功后的doc信息
			$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"text",//返回值类型
		        url:"${ctx}/rep_getAllReceivePeople.do",
		        success:function(result){
		        	var data =  eval('(' + result + ')');
		        	if(data != null && data.list !== undefined && data.list.length > 0) {
		        		var str = "";
		        		for(var i = 0; i < data.list.length; i++) {
		        			var receivePeople = data.list[i].receivePeople;
		        			str += "<option value='"+ receivePeople +"'>"+ receivePeople +"</option>";
		        		}
		        		$("#receivePeople").append(str);
		        	}
		        }
		    })
		};
		
		function getAllCDMD() {
			//异步获取上传成功后的doc信息
			$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"text",//返回值类型
		        url:"${ctx}/rep_getAllCDMD.do",
		        success:function(result){
		        	var data =  eval('(' + result + ')');
		        	if(data != null && data.list.length > 0) {
		        		 var str = "";
		        		for(var i = 0; i < data.list.length; i++) {
		        			var name = data.list[i].name;
		        			str += "<option value='"+ name +"'>"+ name +"</option>";
		        		}
		        		$("#cdmd").append(str);
		        	}
		        }
		    })
		};
		
		function getAllCYJG() {
			//异步获取上传成功后的doc信息
			$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"text",//返回值类型
		        url:"${ctx}/rep_getAllCYJG.do",
		        success:function(result){
		        	var data =  eval('(' + result + ')');
		        	if(data != null && data.list.length > 0) {
		        		 var str = "";
		        		for(var i = 0; i < data.list.length; i++) {
		        			var name = data.list[i].name;
		        			str += "<option value='"+ name +"'>"+ name +"</option>";
		        		}
		        		$("#cyjg").append(str);
		        	}
		        }
		    })
		};
		
		function getData() {
			layer.load();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			if(!compareDate(startTime,endTime,"dayTime")) {
				alert("开始时间不能晚于结束时间");
				return;
			}
			$.ajax({
		        async:true,//是否异步
		        type:"POST",//请求类型post\get
		        cache:false,//是否使用缓存
		        dataType:"text",//返回值类型
		        data:$("#bgForm").serialize(),
		        url:"${ctx}/rep_getWorkStatisticsReports.do",
		        success:function(result){
		        	var data =  eval('(' + result + ')');
		        	var flag = data.flag;
		        	if(flag == "failed") {
		        		alert(data.msg);
		        	} else if(flag == "lack") {
		        		$("#dataBody").empty();
		        		$("#dataBody").append("<tr><th colspan='13'>"+ data.msg +"</th></tr>");
		        	} else if(flag == "success") {
		        		console.log(data);
		        		$("#dataBody").empty();
		        		var str = "";
		        		var data = data.data;
		        		for(var i = 0; i < data.length; i++) {
		        			var obj = data[i];
		        			str += "<tr><td align='center'>"+ obj.receivePeople + "</td>";
		        			str += "<td align='center'>"+ obj.usingPeopleNum + "</td>";
		        			str += "<td align='center'>"+ obj.usingDays + "</td>";
		        			str += "<td align='center'>"+ obj.usingPeopleTimes + "</td>";
		        			str += "<td align='center'>"+ obj.archivesNum + "</td>";
		        			str += "<td align='center'>"+ obj.printTimes + "</td>";
		        			str += "<td align='center'>"+ obj.printPageNums + "</td>";
		        			str += "<td align='center'>"+ obj.copyTimes + "</td>";
		        			str += "<td align='center'>"+ obj.copyPageNums + "</td>";
		        			str += "<td align='center'>"+ obj.excerptTimes + "</td>";
		        			str += "<td align='center'>"+ obj.excerptPageNums + "</td>";
		        			str += "<td align='center'>"+ obj.photoTimes + "</td>";
		        			str += "<td align='center'>"+ obj.printPageNums + "</td></tr>";
		        		}
		        		$("#dataBody").append(str);
		        	}
		        	layer.closeAll('loading');
		        },
		        error:function() {
		        	layer.closeAll('loading');
		        }
		    })
		}
		
		//导出excel
		function exportExcel() {
			var startTime = $("#startTime").val();
			startTime = encodeURI(encodeURI(startTime));
			
			var endTime = $("#endTime").val();
			endTime = encodeURI(encodeURI(endTime));
			
			var receivePeople = $("#receivePeople option:selected").val();
			receivePeople = encodeURI(encodeURI(receivePeople));
			
			var cdmd = $("#cdmd option:selected").val();
			cdmd = encodeURI(encodeURI(cdmd));
			
			var cyjg = $("#cyjg option:selected").val();
			cyjg = encodeURI(encodeURI(cyjg));
			
			document.location.href = "${ctx}/rep_exportWorkExcel.do?startTime="+startTime+"&endTime="+endTime+"&receivePeople="+receivePeople+"&cdmd="+cdmd+"&cyjg="+cyjg;
		}
	</script>
</html>