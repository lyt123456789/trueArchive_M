<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
   
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
	.wf-search-bar {
    font-size: 16px;
    padding: 8px 0px 0px 10px;
    text-align: right;
}
	.wf-icon-trash{
			cursor:pointer;
			color:red;
		}
		.inp{
			text-align:center;
			width: 100px;
			border: none; 
			display:none;
			background-color: transparent;
		}
		.td.checked span{
			display:none;
		}
	
		.td.checked .inp{
			display:inline;
			border:1px solid #ccc;
		}
		.td.changed{
			background:#ccc;
		}
	</style>
</head>
<body>
<div class="wf-layout">
	
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<table id="tab232323" class="wf-fixtable" layoutH="140"  style="width:100%;">
			<thead>
               	<th>序号</th>
               	<th>字段值</th>
				<th>默认值</th>
				<th>是否是固定值</th>
				<th>操作</th>
			</thead>
				<tbody>
                    <c:forEach items="${dataList}" var="item" varStatus="status">	
                    	<tr>
                    		 <td align="center">${pageIndex*pageSize+status.count}</td>
                    		 <td align="center">${item[2]}</td>
                    		 <td align="center">${item[3]}</td>
                    		 <td align="center">${item[4]}</td>
                    		 <td align="center">
                    		 	<button type="button" onclick="javascript:toAddMrz('${item[0]}','${item[1]}','${item[3]}','${item[4]}');" target="_self" class="btn btn-write" style="float:left">
						    		<i class="fa fa-pencil-square-o fa-lg"></i>编辑
						    	</button>
						    	<button type="button" onclick="javascript:deleteMrz('${item[0]}','${item[1]}');" target="_self" class="btn btn-write" style="float:left">
						    		<i class="fa fa-pencil-square-o fa-lg"></i>删除
						    	</button>
						    </td>
                    	</tr>
                    </c:forEach>
                 </tbody>
		</table>
	</div>
	<div class="wf-list-ft" id="pagingWrap">
		
	</div>
	
</div>
</body>
<%@ include file="/common/widgets.jsp"%>
	<script type="text/javascript">
	function toAddMrz(structureId,tagId,mrz,type){
		var html="";
		if(type=="1"){
			html = "<div style='margin: 20px;'>"
				+"<input type='text' id='mrz1' name='mrz1' value='"+mrz+"' />"
				+"<select style='display:none;' id='mrz2' name='mrz2'>"
				+"<option>当前用户</option><option>当前用户机构</option>"
				+"<select>"
				+"<input type='hidden' id='type' name='type' value='1'/>"
				+"<button onclick='changeShow(this)'>固定值</button>"
				+"</div>";
		}else if(type=="2"){
			html = "<div style='margin: 20px;'>"
				+"<input style='display:none;' type='text' id='mrz1' name='mrz1' value='' />"
				+"<select id='mrz2' name='mrz2'>";
				if(mrz=="当前用户"){
					html +="<option selected='selected'>当前用户</option><option>当前用户机构</option>"
				}else{
					html +="<option>当前用户</option><option selected='selected'>当前用户机构</option>"
				}
			html +="<select>"
				+"<input type='hidden' id='type' name='type' value='2'/>"
				+"<button onclick='changeShow(this)'>当前值</button>"
				+"</div>";
		}else{
			html = "<div style='margin: 20px;'>"
				+"<input type='text' id='mrz1' name='mrz1' value='' />"
				+"<select style='display:none;' id='mrz2' name='mrz2'>"
				+"<option>当前用户</option><option>当前用户机构</option>"
				+"<select>"
				+"<input type='hidden' id='type' name='type' value='1'/>"
				+"<button onclick='changeShow(this)'>固定值</button>"
				+"</div>";
		}
		
		layer.open({
		    type: 1,
		    content: html, //这里content是一个DOM，这个元素要放在body根节点下
		    btn: ['确定', '取消'],
            yes: function(index, layero){
            	var mrz1 = document.getElementById("mrz1").value;
            	var mrz2 = document.getElementById("mrz2").value;
            	var nowtype = document.getElementById("type").value;
            	var mrz = '';
            	if(nowtype=="1"){
            		mrz=mrz1;
            	}else if(nowtype=="2"){
            		mrz=mrz2;
            	}
            	$.ajax({
			        async:true,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"json",//返回值类型
			        data:{"structureId":structureId,"tagId":tagId,"mrz":mrz,"type":nowtype},
			        url:"${ctx}/dataTemp_updateMRZ.do",
			        success:function(result){
			        	if(result.flag=="success"){
			        		window.location.reload();
			        	}else{
			        		layer.msg("维护失败");
			        	}
			        }
			    });
            },
            btn2: function(index, layero){
            	
            },
		});
	}
	
	function changeShow(obj){
		var textvalue= obj.innerHTML;
		if(textvalue=='固定值'){
			document.getElementById("mrz1").style.display="none";
			document.getElementById("mrz2").style.display="block";
			document.getElementById("type").value="2";
			obj.innerHTML='当前值';
		}else if(textvalue=='当前值'){
			document.getElementById("mrz1").style.display="block";
			document.getElementById("mrz2").style.display="none";
			document.getElementById("type").value="1";
			obj.innerHTML='固定值';
		}
	}
	
	function deleteMrz(structureId,tagId){
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"json",//返回值类型
	        data:{"structureId":structureId,"tagId":tagId},
	        url:"${ctx}/dataTemp_deleteMRZ.do",
	        success:function(result){
	        	if(result.flag=="success"){
	        		window.location.reload();
	        	}else{
	        		layer.msg("维护失败");
	        	}
	        }
	    });
	}
	
	
	
	</script>
	<script>
	(function ($) {
	 $.extend({
	  Request: function (m) {
	   var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));
	   return sValue ? sValue[1] : sValue;
	  },
	  UrlUpdateParams: function (url, name, value) {
	   var r = url;
	   if (r != null && r != 'undefined' && r != "") {
	    value = encodeURIComponent(value);
	    var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
	    var tmp = name + "=" + value;
	    if (url.match(reg) != null) {
	     r = url.replace(eval(reg), tmp);
	    }
	    else {
	     if (url.match("[\?]")) {
	      r = url + "&" + tmp;
	     } else {
	      r = url + "?" + tmp;
	     }
	    }
	   }
	   return r;
	  }
	 
	 });
	})(jQuery);
	</script>
</html>