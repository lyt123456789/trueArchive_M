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
<c:if test="${'3' eq layout}">
	<div><iframe id="frame3" width="100%" frameborder="0" src="${ctx}/dataReception_toStructureDataPage.do?treeId=${treeId}&structureId=${projectStructureId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}&flag=all" class="iframe"></iframe></div>
	<div><iframe id="frame2" width="100%" frameborder="0" src="${ctx}/dataReception_toStructureDataPage.do?treeId=${treeId}&structureId=${fileStructureId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}" class="iframe"></iframe></div>
	<div><iframe id="frame1" width="100%" frameborder="0" src="${ctx}/dataReception_toStructureDataPage.do?treeId=${treeId}&structureId=${innerFileStructureId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}" class="iframe"></iframe></div>
</c:if>
<c:if test="${'2' eq layout}">
	<div><iframe id="frame2" width="100%" frameborder="0" src="${ctx}/dataReception_toStructureDataPage.do?treeId=${treeId}&structureId=${fileStructureId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}&flag=all" class="iframe"></iframe></div>
	<div><iframe id="frame1" width="100%" frameborder="0" src="${ctx}/dataReception_toStructureDataPage.do?treeId=${treeId}&structureId=${innerFileStructureId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}" class="iframe"></iframe></div>
</c:if>
<c:if test="${'1' eq layout}">
<div><div><iframe id="frame1" width="100%" frameborder="0" src="${ctx}/dataReception_toStructureDataPage.do?treeId=${treeId}&structureId=${innerFileStructureId}&projectStructureId=${projectStructureId}&fileStructureId=${fileStructureId}&innerFileStructureId=${innerFileStructureId}&flag=all" class="iframe"></iframe></div></div>
</c:if>



</body>
	<script type="text/javascript">
	$(document).ready(function (){
		if("${layout}"=="3"){
			$("#frame1").height(($(window).height()-10)/3);
			$("#frame2").height(($(window).height()-10)/3);
			$("#frame3").height(($(window).height()-10)/3);
		}else if("${layout}"=="2"){
			$("#frame2").height(($(window).height()-10)/3);
		}else if("${layout}"=="1"){
			$("#frame1").height($(window).height()-10);
		}
		
	});
	//输入值校验
	function inValueTest(data) {
		var flag = false;
		if(data.indexOf(",") != -1) {
			flag = true;
		} else if(data.indexOf(";") != -1) {
			flag = true;
		} else if(data.indexOf("'") != -1) {
			flag = true;
		}
		return flag;
	}
	//单击td 置为可编辑
	function addCheck(id,n,cla){//记录的id   ; td的在tr中的序号     ;  区分checkbox与select input框
		if("${onlyLook}"!="1"){
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
	}
	
	
	var classN = 0;//新增的序号
	var removeClassN="";//移除掉的序号
	//新增tr
	function add(){
	    var selectStr = " <select class='wf-form-select'  style='width: 100px;' onchange='selectChange(this)'>";
	    selectStr+="<option value = '文本类型'>文本类型</option>"+
	    		   "<option value = '大文本型'>大文本型</option>"+
	    		   "<option value = '数值类型'>数值类型</option>"+
	    		   "<option value = '浮点类型'>浮点类型</option>";
	    selectStr +="</select>";
	    var str = "<tr id='"+classN+"' >"
		    +"<td class='td' align='center'  onclick ='removetr(this,\""+classN+"\")'><i class='wf-icon-trash' style='display:inline-block;'></i></td>"//删除操作
		    +"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;' ></input></td>"//排序
		    +"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;' dataType='esIdentifier'></input></td>"//名称
		    +"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;' onclick='chooseMateData(this)' readonly><input type='hidden' class='"+classN+"'/></td>"//元数据
			+"<td class='td' align='center'>"+selectStr+"<input type='hidden' class='"+classN+"' value='文本类型'></input></td>"//字段类型
			+"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;'></input></td>"//描述
			+"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;' value='50' dataType='esLength'></input></td>"//字段长度
			+"<td class='td' align='center'><input class='"+classN+"' style='text-align:center;width:90px !important;' value='0' dataType='esDotlength'></input></td>"//小数位数
			+"<td class='td' align='center'><input onchange='checkchange(this)'  class='"+classN+"' type='checkbox' value='1' checked='checked' style='width: 100px;'></input></td>"//是否编辑
			+"<td class='td' align='center'><input onchange='checkchange(this)'  class='"+classN+"' type='checkbox' value='0' style='width: 100px;'></input></td>"//是否必填
		+"</tr>";
		$("#main tr").eq(0).after(str);
		 classN++;
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
		var check = true;
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
						val = '';
					}
					if(inValueTest(val)) {
						layer.msg("请勿输入逗号，分号等特殊字符")
						check=false;
					}
					chirldstr += trim(val)+",";		
				});
			}
			if(chirldstr != ''&& chirldstr !=null && chirldstr != ','){
				str += chirldstr.substring(0, chirldstr.length-1) +";";
			}
		}
		if(str==""){
			layer.msg("无新增记录！")
			return;
		}
		if(!check){
			return;
		}
		str = str.substring(0, str.length-1);
		//再次校验
		check=true;
		var strarr = str.split(";");
		for(var i=0;i<strarr.length;i++){
			var data = strarr[i].split(",");
			/* --------------字段名--------------------- */
			var esIdentifier = data[1];
			if(esIdentifier==''){
				layer.msg("字段名不能为空")
				return;
			}else{//校验是否已经有同名字段
				$.ajax({
			        async:false,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"text",//返回值类型
			        data:{
			        	"esIdentifier":esIdentifier,
			        	"structureId":"${structureId}"
			        },
			        url:"${ctx}/dataTemp_checkIsHaveTag.do",
			        success:function(result){    
			        	if(result=="true"){//有同名字段
			        		layer.msg("已存在同名字段")
							check=false;
			        	}
			        }
			    });
			}
			/* --------------字段类型--------------------- */
			var esType = data[4];
			/* --------------字段长度--------------------- */
			var esLength = data[6];
			if(esType=="大文本型"){//大文本不需要校验	
			}else if(esType=="数值类型"){	
				var reg = new RegExp("^([1-9][0-9]*)$");
				if(!reg.test(esLength)) {
					layer.msg("数值类型字段长度为1-9的整数！")
					return;
				}else{
					if(parseInt(esLength)>9){
						layer.msg("数值类型字段长度为1-9的整数！")
						return;
					}
				}
			}else{//文本类型（以及其他）
				var reg = new RegExp("^([1-9][0-9]*)$");
				if(!reg.test(esLength)) {
					layer.msg("文本浮点类型字段长度为1-4000的整数！")
					return;
				}else{
					if(parseInt(esLength)>4000){
						layer.msg("文本浮点类型字段长度为1-4000的整数！")
						return;
					}
				}
			}
			/* --------------小数位数--------------------- */
			var dotLength = data[7];
			if(dotLength==''){
				layer.msg("小数位数不能为空")
				return;
			}else{
				var reg = new RegExp("^[0-9]*$");
				if(!reg.test(dotLength)) {
					layer.msg("小数位数为包含0的整数！")
					return;
				}
			}
		}	
		if(!check){
			return;
		}
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{
	        	"str":str,
	        	"structureId":"${structureId}"
	        },
	        url:"${ctx}/dataTemp_addTags.do",
	        success:function(result){    
	        	if(result=="success"){
	        		window.location.reload();    
	        	}else{
	        		layer.msg("保存失败");
	        	} 
	        }
	    })
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
			top.layer.alert("请选择需要修改的数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		var idsarr = ids.split(",");
		var check = true;
		for(var i=0;i<idsarr.length;i++){
			var chirldstr = "";
			$("."+idsarr[i]).each(function(){
				var val = ""; 
				if($(this).val()!='undefined' && $(this).val() != ''){
					val = $(this).val();
				}else{
					val = '';
				}
				if(inValueTest(val)) {
					layer.msg("请勿输入逗号，分号等特殊字符")
					check=false;
				}
				chirldstr += trim(val)+",";
			});
			if(chirldstr != ''&& chirldstr !=null&& chirldstr !=','){
				str += chirldstr.substring(0, chirldstr.length-1) +";";
			}
		}	
		if(!check){
			return;
		}
		str = str.substring(0, str.length-1);
		//再次校验
		check=true;
		var strarr = str.split(";");
		for(var i=0;i<strarr.length;i++){
			var data = strarr[i].split(",");
			/* --------------id--------------------- */
			var id= data[0]
			/* --------------字段名--------------------- */
			var esIdentifier = data[2];
			if(esIdentifier==''){
				layer.msg("字段名不能为空")
				return;
			}else{//校验是否已经有同名字段
				var oldTagName = document.getElementById("old_filedName_"+id).value;
				if(oldTagName!=esIdentifier){//修改的时候  只有当和原先值不一样时才校验
					$.ajax({
				        async:false,//是否异步
				        type:"POST",//请求类型post\get
				        cache:false,//是否使用缓存
				        dataType:"text",//返回值类型
				        data:{
				        	"esIdentifier":esIdentifier,
				        	"id":id,
				        	"structureId":"${structureId}"
				        },
				        url:"${ctx}/dataTemp_checkIsHaveTag.do",
				        success:function(result){    
				        	if(result=="true"){//有同名字段
				        		layer.msg("已存在同名字段")
								check=false;
				        	}
				        }
				    });
				}
			}
			/* --------------字段类型--------------------- */
			var esType = data[5];
			var oldType = document.getElementById("old_filedType_"+id).value;
			if(oldType!=esType){
				$.ajax({//检查该字段下是否有数据
			        async:false,//是否异步
			        type:"POST",//请求类型post\get
			        cache:false,//是否使用缓存
			        dataType:"text",//返回值类型
			        data:{
			        	"id":id,
			        	"structureId":"${structureId}"
			        },
			        url:"${ctx}/dataTemp_checkIsHaveData.do",
			        success:function(result){    
			        	if(result=="true"){//有数据
			        		layer.msg("该字段下已有数据，不允许修改类型")
							check=false;
			        	}
			        }
			    });
			}
			/* --------------字段长度--------------------- */
			var esLength = data[7];
			if(esType=="大文本型"){//大文本不需要校验	
			}else if(esType=="数值类型"){	
				var reg = new RegExp("^([1-9][0-9]*)$");
				if(!reg.test(esLength)) {
					layer.msg("数值类型字段长度为1-9的整数！")
					return;
				}else{
					if(parseInt(esLength)>9){
						layer.msg("数值类型字段长度为1-9的整数！")
						return;
					}
				}
			}else{//文本类型（以及其他）
				var reg = new RegExp("^([1-9][0-9]*)$");
				if(!reg.test(esLength)) {
					layer.msg("文本浮点类型字段长度为1-4000的整数！")
					return;
				}else{
					if(parseInt(esLength)>4000){
						layer.msg("文本浮点类型字段长度为1-4000的整数！")
						return;
					}
				}
			}
			/* --------------小数位数--------------------- */
			var dotLength = data[8];
			if(dotLength==''){
				layer.msg("小数位数不能为空")
				return;
			}else{
				var reg = new RegExp("^[0-9]*$");
				if(!reg.test(dotLength)) {
					layer.msg("小数位数为包含0的整数！")
					return;
				}
			}
		}	
		if(!check){
			return;
		}
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"str":str,"structureId":"${structureId}"},
	        url:"dataTemp_updateTags.do",
	        success:function(result){
	        	if("success" == result){
	        		window.location.reload();
	        	}else{
	        		layer.msg("修改失败");
	        	}
	        }
	    })
	}
	
	function del(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=="selid" && objs[i].checked==true ){
			  ids += ""+objs[i].value+",";
		   }
		}
		if(ids==""){
			top.layer.alert("请选择要删除的数据");
			return;
		}
		ids = ids.substring(0, ids.length-1);
		//校验是否能删除
		$.ajax({
	        async:false,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"json",//返回值类型
	        data:{"ids":ids,"structureId":"${structureId}"},
	        url:"${ctx}/dataTemp_checkDeleteTags.do",
	        success:function(result){
	        	 if(result.type=="1"){//规则校验不通过
	        		 layer.msg(result.content);
	        	 }else if(result.type=="2"){
	        		 var msg = "";
	        		 if(result.content!=""){
	        			 msg=result.content+"字段下存在数据,"+"确定删除所选字段吗";
	        		 }else{
	        			 msg="确定删除所选数据吗";
	        		 }
	        		 if(confirm(msg)){
		     				$.ajax({
	     				        async:true,//是否异步
	     				        type:"POST",//请求类型post\get
	     				        cache:false,//是否使用缓存
	     				        dataType:"text",//返回值类型
	     				        data:{"ids":ids,"structureId":"${structureId}"},
	     				        url:"${ctx}/dataTemp_deleteTags.do",
	     				        success:function(result){
	     				        	if(result=="success"){
	     				        		window.location.reload();
	     				        	}else{
	     				        		layer.msg("删除失败");
	     				        	}
	     				        }
	     				    });
	        		 }
	        	 }
	        }
	    });
		
		
	}
	
	//新增的时候在input框上添加事件
	function chooseMateData(obj){
		var index = top.layer.confirm('请选择您要对元数据进行的操作：', {
	        btn: ['编辑','删除',"关闭"], //按钮
	    }, function(){
	    	$(obj).val("using:Name");
	    	$(obj).next().val("111");
	    	top.layer.close(index);
	    }, function(){
	    	$(obj).val("");
	    	$(obj).next().val("");
	    	top.layer.close(index);
	    }, function(){
	    	top.layer.close(index);
	    });
		obj.blur();
	}
	//编辑的时候在td上添加事件
	function chooseMateData_td(id){
		var oldValue = $("#"+id+" td:eq(3) .inp").val();
		var index = top.layer.confirm('请选择您要对元数据进行的操作：', {
	        btn: ['编辑','删除',"关闭"], //按钮
	    }, function(){
	    	var newValue ="壹亿壹";
	    	if(newValue!=oldValue){
	    		$("#"+id+" td:eq(3) .inp").val(newValue);
		    	$("#"+id+" td:eq(3) .inp").next().val("222");
		    	$("#"+id+" td:eq(3) span").html(newValue);
		    	$("#"+id+" td:eq(3)").addClass("changed");
	    	}
	    	top.layer.close(index);
	    }, function(){
	    	$("#"+id+" td:eq(3) .inp").val("");
	    	$("#"+id+" td:eq(3) .inp").next().val("");
	    	$("#"+id+" td:eq(3) span").html("");
	    	$("#"+id+" td:eq(3)").addClass("changed");
	    	top.layer.close(index);
	    }, function(){
	    	top.layer.close(index);
	    });
		obj.blur();
	}
	function removetr(_this,removeN){
		$(_this).parent().remove();
		removeClassN+=removeN+",";
	}
	
	function back(){
		window.location.reload();
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