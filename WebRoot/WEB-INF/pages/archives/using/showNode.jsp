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
	.wf-icon-trash{
			cursor:pointer;
			color:red;
		}
	/* 
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
		.wf-form-date{
			width:115px!important;
			min-width: 100px!important;
			max-width: 120px!important;
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
		} */
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
	<div class="wf-list-top">
		<div class="wf-search-bar">
			<form name="nodeList"  id="nodeList" action="${ctx }/using_toshowNode.do?a=Math.random();" method="post" style="display:inline-block;width: 100%;">
			    <div class="wf-top-tool">
					<a class="wf-btn" onclick="javascript:add();" target="_self">
						<i class="wf-icon-plus-circle" style="display:inline-block;"></i> 添加
					</a>
					<a  class="wf-btn-primary  del" onclick="javascript:saveAdd();" target="_self">
						<i class="wf-icon-pencil" style="display:inline-block;"></i> 保存新增
					</a>
					<a  class="wf-btn-primary  del" onclick="javascript:saveUpdate();" target="_self">
						<i class="wf-icon-pencil" style="display:inline-block;"></i> 保存修改
					</a>
					<a  class="wf-btn-danger del" onclick="javascript:del();" target="_self">
						<i class="wf-icon-trash" style="display:inline-block;"></i> 删除
					</a>
				</div>
 	        	<%-- <label class="wf-form-label" for="">字段名：</label>
                <input type="text" class="wf-form-text w-auto-10" id="vc_name" name="vc_name" style="width: 90px" value="${vc_name}" placeholder="输入关键字">
	            <button class="wf-btn-primary" type="button" onclick="checkForm('2');">
	                <i class="wf-icon-search"></i> 搜索
	            </button> --%>
				 </form>
		</div>
	</div>
	<div class="wf-list-wrap">
		<div class="loading"></div><!--2017-11-10-->
		<form id="thisForm" method="POST" name="thisForm" action="${ctx }/using_toshowNode.do" >
			<table class="wf-fixtable" id="dataTable" layoutH="140"  style="width: 100%;">
				<thead>
	    	  		<th width="2%"></th>
	    	  		<th align="center" width="8%">排序</th>
                    <th align="center" width="10%">字段名称</th>
                    <th align="center" width="10%">元数据</th>
					<th align="center" width="10%">字段类型</th>
					<th align="center" width="8%">描述</th>
                   	<th align="center" width="8%">字段长度</th>
                   	<th align="center" width="8%">小数位数</th>                  
                   	<th align="center" width="8%">是否编辑</th>
                   	<th align="center" width="8%">是否必填</th>
                   	<th align="center" width="8%">是否展示</th>                  
                    <c:forEach items="${nodeList}" var="item" varStatus="status">
                    	<tr class="istrue"  id = "${item.id }">
                    		<td align="center" itemid="${item.id}">
                    			<input type="checkbox" name="selid" id="${item.id}"  value="${item.id}"  >
                    		</td>
                    		<td align="center"  class="td" onclick="addCheck('${item.id}',1,'inp')"><span>${item.vc_number}</span><input class="inp  ${item.id}"  value="${item.vc_number}"></input></td>
                    		<td align="center"  class="td" onclick="addCheck('${item.id}',2,'inp')"><span>${item.vc_name}</span><input class="inp  ${item.id}"  value="${item.vc_name}"></input></td>
                    		<td align="center"  class="td" onclick="addCheck('${item.id}',3,'inp')"><span>${item.vc_metadataname}</span><input class="inp ${item.id}"   value="${item.vc_metadataname}"></input></td>
                    		<td align="center"  class="td" onclick="addCheck('${item.id}',4,'inp')"><span>${item.vc_type}</span>
                    			<select class="inp  ${item.id}"><option value=""></option></select>
                    		</td>
                    		<td align="center"  class="td" onclick="addCheck('${item.id}',5,'inp')"><span>${item.vc_fielType}</span><input class="inp  ${item.id}"  value="${item.vc_fielType}"></input></td>
                    		<td align="center"  class="td" onclick="addCheck('${item.id}',6,'inp')"><span>${item.vc_length}</span><input class="inp  ${item.id}"   value="${item.vc_length}"></input></td>
                    		<td align="center"  class="td" onclick="addCheck('${item.id}',7,'inp')"><span>${item.vc_dataFormat}</span><input class="inp  ${item.id}" value="${item.vc_dataFormat}"></input></td>
                    		
                    		<td align="center"  class="td" onclick="addCheck('${item.id}',8,'box')"><input class="box ${item.id}" onchange='checkchange(this)' type="checkbox"  name="istrue"  value="${item.vc_isEdit}" <c:if test="${item.vc_isEdit eq '1'}">checked="checked"</c:if>  ></td>
                    		<td align="center"  class="td" onclick="addCheck('${item.id}',9,'box')"><input class="box ${item.id}" onchange='checkchange(this)' type="checkbox"  name="istrue"  value="${item.vc_isNull}" <c:if test="${item.vc_isNull eq '1'}">checked="checked"</c:if>  ></td>
                    		<td align="center"  class="td" onclick="addCheck('${item.id}',10,'box')"><input class="box ${item.id}" onchange='checkchange(this)' type="checkbox"  name="istrue"  value="${item.vc_isShow}" <c:if test="${item.vc_isShow eq '1'}">checked="checked"</c:if>  ></td>	
                    	</tr>
                    </c:forEach>
                 </thead>
			</table>
		</form>
	</div>
	<div class="wf-list-ft" id="pagingWrap"></div>
</div>


</body>
	<script type="text/javascript">
	var objArray = [];
	var tdListIndex = [];
	$(document).ready(function (){
		$(".wf-list-wrap").height($(window).height()-68);
	});
	
	//单击td 置为可编辑
	function addCheck(id,n,cla){//记录的id    td的在tr中的序号       区分checkbox与select input框
		if(cla=="box"){
			$("#"+id+" td:eq("+n+")").addClass("changed");
		}
		else{
			$("#"+id+" td:eq("+n+")").addClass("checked");
			$("#"+id+" td:eq("+n+") ."+cla).focus();
		
			var value=$("#"+id+" td:eq("+n+") ."+cla).val();
			$("#"+id+" td:eq("+n+") ."+cla).blur(function(){
				if($("#"+id+" td:eq("+n+") ."+cla).val()!=value){
					$("#"+id+" td:eq("+n+")").addClass("changed");
					$("#"+id+" td:eq("+n+") span").html($("#"+id+" td:eq("+n+") ."+cla).val());
				}
				$("#"+id+" td:eq("+n+")").removeClass("checked");
			});
		}
	}
	
	
	var classN = 0;//新增的序号
	var removeClassN="";//移除掉的序号
	//新增tr
	function add(){
	    var selectStr = " <select class='wf-form-select'  style='width: 90px;' onchange='selectChange(this)'><option value=''>请选择</option>";
	    //字段类型
	    var sjlxList =  '${sjlxJson}';
	    sjlxList = eval("("+sjlxList+")");
	    $.each(sjlxList, function(i, element){
	    	selectStr+="<option value = '"+element.feilName+"'>"+element. dataName+"</option>";
	    });
	    selectStr +="</select>";
	    var str = "<tr id='"+classN+"' >"
		    +"<td class='td' align='center'  onclick ='removetr(this,\""+classN+"\")'><i class='wf-icon-trash' style='display:inline-block;'></i></td>"//删除操作
		    +"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;'></input></td>"//排序
		    +"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;'></input></td>"//名称
		    +"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;'></input></td>"//元数据
			+"<td class='td' align='center'>"+selectStr+"<input type='hidden' class='"+classN+"'/></td>"//字段类型
			+"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;'></input></td>"//描述
			+"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;'></input></td>"//字段长度
			+"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;'></input></td>"//小数位数
			+"<td class='td' align='center'><input onchange='checkchange(this)'  class='"+classN+"' type='checkbox' value='0' style='width: 100px;'></input></td>"//是否编辑
			+"<td class='td' align='center'><input onchange='checkchange(this)'  class='"+classN+"' type='checkbox' value='0' style='width: 100px;'></input></td>"//是否必填
			+"<td class='td' align='center'><input onchange='checkchange(this)'  class='"+classN+"' type='checkbox' value='0' style='width: 100px;'></input></td>"//是否展示
		+"</tr>";
		$("#main tr").eq(0).after(str);
		 classN++;
		/* $(".save").css('display','');
		 $(".del").css('display','none');
		 $(".istrue").css('background-color','#d0d0d0');
		 $("input[name=istrue]").each(function(){
             $(this).attr("disabled",true);
         });
		 $("input[name=selid]").each(function(){
             $(this).attr("disabled",true);
         }); */
	}
	//新增字段时，select的change事件
	function selectChange(_this){
		var feilName = $(_this).val();
		$(_this).next().val($(_this).val());
	}
	//新增字段时，checkbox的change事件
	function checkchange(_this){
		 if ($(_this).val() =='1') { 
		        $(_this).val('0');
		    }else{
		    	$(_this).val('1');
		    }
	}
	//保存新增
	function saveAdd(){
		if(removeClassN != ''&& removeClassN !=null){
			removeClassN = removeClassN.substring(0, removeClassN.length-1);
		}
		var str = "";
		for(var i = 0;i<=classN ;i++ ){
			var chirldstr = "";
			var flag = true;
			for(var j=0;j<removeClassN.length;j++){
				if(i==removeClassN[j]){
					flag=false;
					break;
				}
			}
			if(flag){//如果已经移除的序号，不在拼接
				$("."+i).each(function(){	
					var val = ""; 
					if($(this).val()!='undefined' && $(this).val() != ''){
						val = $(this).val();
					}else{
						val = ' ';
					}
					chirldstr += val+",";		
				});
			}
			if(chirldstr != ''&& chirldstr !=null && chirldstr != ','){
				str += chirldstr.substring(0, chirldstr.length-1) +";";
			}
		}
		str = str.substring(0, str.length-1);
		alert(str)
		return;
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{
	        	"str":str,
	        	"vc_table":vc_table
	        		},
	        url:"${ctx}/using_addData.do",
	        success:function(result){
	        	if("false" == result){
	        		alert("字段排序号不可重复");
	        	}else{
	        		window.location.reload();
	        	}
	        	 
	        }
	    })
	}
	
	function removetr(_this,removeN){
		$(_this).parent().remove();
		removeClassN+=removeN+",";
	}
	
	function back(){
		window.location.reload();
	}
	
	
	function saveUpdate(){
		var objs = document.getElementsByTagName('input');
		var n = 0;
		var ids="";
		var str="";
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
			  ids += objs[i].value+",";
			  n ++;
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		var idsarr = ids.split(",");
		for(var i=0;i<idsarr.length;i++){
			var chirldstr = "";
			$("."+idsarr[i]).each(function(){
				var val = ""; 
				if($(this).val()!='undefined' && $(this).val() != ''){
					val = $(this).val();
				}else{
					val = '';
				}
				chirldstr += val+",";
			});
			if(chirldstr != ''&& chirldstr !=null&& chirldstr !=','){
				str += chirldstr.substring(0, chirldstr.length-1) +";";
			}
		}	
		str = str.substring(0, str.length-1);
		alert("str="+str);
		return;
		//开始校验（模板的字段可以不用检验，具体使用编辑表时，需要检验）
		/* var changeArray = str.split(",");
		var type = changeArray[3];
		var length = changeArray[4];
		var index = changeArray[6];
		if(type == "varchar2" && parseInt(length)>4000) {
			alert("长度超过数据库字段类型上限");
			return;
		}
		if(parseInt(length) < parseInt(lengthOld)) {
			alert("长度必须大于等于之前设定长度");
			return;
		}
		var indexOld = objArray[4];
		if(indexOld != index && $.inArray(index,tdListIndex) >= 0) {
			alert("请重新设定排序号，避免序号重复");
			return;
		} */
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"str":str},
	        url:"${ctx}/using_updateData.do",
	        success:function(result){
	        	if("false" == result){
	        		alert("字段排序号不可重复");
	        	}else{
	        		window.location.reload();
	        	}
	        	 
	        }
	    })
	}
	
	function del(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=="selid" && objs[i].checked==true ){
			  ids += objs[i].value+",";
		   }
		}
		if(ids==""){
			alert("请选择一条数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		if(confirm('确定删除所选数据吗')){
			//异步获取上传成功后的doc信息
			$.ajax({
			        async:true,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"text",//返回值类型
			        data:{"ids":ids},
			        url:"${ctx}/using_delData.do",
			        success:function(text){
			        	 window.location.reload();
			        }
			    })
		}
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