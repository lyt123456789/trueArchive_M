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
	<link href="${ctx}/assets-common/css/common.css?t=20190516" rel="stylesheet" type="text/css" />
	 <link rel="stylesheet" href="css/list.css?t=123">
	<style type="text/css">
	.title{
		background: #008cee;
    	height: 40px;
	}
	.font{
		font-size: 16px;
    	color: #fff;
   	 	padding-top: 8px;
    	padding-left: 18px;
	}
	.table{
		width: 100%;
    	height: 100%;
    	border: none !important;
    	font-size: 16px;
	}
	.input {
		width: 100%;
		height: 30px;
		border-radius: 5px;
		border: 1px solid #9a9a9a;
	}
	.textarea {
		width: 100%;
		height: 100%;
		resize:none;
		border-radius: 5px;
		border: 1px solid #9a9a9a;
	}
	.select {
		width: 100%;
		height: 30px;
		border-radius: 5px;
		border: 1px solid #9a9a9a;
	}
	.cz-btn{
		padding: 10px 30px;
    	font-size: 18px;
    	color: #444;
    	background-color: #e6e6e6;
    	border-color: #e6e6e6;
	}
	.save-btn{
		    padding: 4px 10px;
		    font-size: 16px;
		    color: white;
		    background-color: #008cee;
		    border-color: #008cee;
		    margin-left: 30px;
		    margin-top: 4px;
	}
	.not-btn {
		padding: 4px 10px;
	    font-size: 16px;
	    color: white;
	    background-color:#c2c2c2;
	    border-color: #c2c2c2;
	    margin-left: 30px;
	    margin-top: 4px;
	}
	</style>
</head>
<!-- 评价器信息 -->
<object id="pwdpad" style="display:none" classid="clsid:9347F652-DC86-4CD1-8DAF-373E50CB2C59">
	<param name="_Version" value="65536"/>
	<param name="_ExtentX" value="926"/>
	<param name="_ExtentY" value="423"/>
	<param name="_StockProps" value="0"/>
</object>
<body>
	<input type="hidden" value="${stuts }"/>
	<input type="hidden" value="${zzcdFlag }"/>
    <div class="tw-layout" style="overflow-y:auto;">
    	<div style="background-color: aliceblue;height: 40px;">
    		<c:if test="${'1' eq zzcdFlag && '0' eq stuts}">
    			<a class="wf-btn-primary save-btn" href="javascript:" onclick="apply('${id }')" >申请利用</a>
    		</c:if>
    		<c:if test="${'1' ne zzcdFlag && '1' eq stuts}">
    			<a class="wf-btn-primary save-btn" href="javascript:" onclick="checkBorrow('${id }')" >审核</a>
    		</c:if>
    		<c:if test="${'1' eq zzcdFlag && '2' eq stuts}">
    			<a class="wf-btn-primary not-btn" href="javascript:">已审核</a>
    		</c:if>
    		<c:if test="${'1' ne zzcdFlag && '2' eq stuts}">
    			<a class="wf-btn-primary save-btn" href="javascript:" onclick="endBorrow('${id }')" >结束</a>
    		</c:if>
    		<c:if test="${'1' eq zzcdFlag && '3' eq stuts}">
    			<a class="wf-btn-primary not-btn" href="javascript:">已结束</a>
    		</c:if>
    		<c:if test="${'1' ne zzcdFlag && '3' eq stuts}">
    			<a class="wf-btn-primary not-btn" href="javascript:">已结束</a>
    		</c:if>
    		
    	</div>
        <div class="tw-container">
			<form id="itemform" method="post" action="${ctx}/using_saveBasicData.do" class="tw-form">
			<input type="hidden"  id="id"  name = "id" value="${id }" />
			<table  class="tw-table "  >
				<tr >
					<td  id="left">
						<table class="table"  id="leftTable">
							     <c:forEach items="${left}" var="item" varStatus="status">
							     	<tr style="height:${item.vc_height * 40}px;" rowspan = "${item.vc_height }">
							     		<td align="center"  <c:if test="${item.vc_isNull eq '1' }">style="color:red;"</c:if> <c:if test="${item.vc_edit eq '0' }">style="color:green !important;"</c:if> >${item.name }<c:if test="${item.id ne '1' }" >：</c:if></td>
							     		<td align="left" >
							     			<c:if test="${item.id ne '1' }" >
							     			<c:choose>
							     				<c:when test="${item.vc_type eq 'input' }">
							     					<input <c:if test="${item.vc_edit eq '0' }">readonly="readonly"</c:if> class="input" name = '${item.fielName }--'   value="${item.vc_val }"  />
							     				</c:when>
							     				<c:when test="${item.vc_type eq 'select' }">
							     					<select  <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if>  <c:if test="${item.vc_edit eq '0' }">disabled=true</c:if> class="select" name = '${item.fielName }--'  <c:if test="${item.name eq '其他目的' }">style="display:none;" id="qtmd"</c:if> <c:if test="${item.name eq '查档目的' }"> id="cdmd" onchange="showMD(this)"</c:if><c:if test="${item.name eq '查阅结果' }"> id="cyjg"</c:if>>
							     						  <option value=""></option>
							     						 <c:forEach items="${item.basicList}" var="data" varStatus="status">
							     							<option value="${data.feilName }" <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if>>${data.dataName }</option>
							     						</c:forEach>
							     					</select>
							     				</c:when>
							     				<c:otherwise>
							     					<textarea <c:if test="${item.vc_edit eq '0' }">readonly="readonly"</c:if> class="textarea" rows="${item.vc_height }" cols="${item.vc_weight }" name = '${item.fielName }--' >${item.vc_val }</textarea>
							     				</c:otherwise>
							     			</c:choose>
							     			</c:if>
							     		</td>
							     	</tr>
							</c:forEach>
						</table>
					</td>
					<td 	id="right">
						<table class="table"  id = "rightTable">
							     <c:forEach items="${right}" var="item" varStatus="status">
							     	<tr style="height:${item.vc_height * 40}px;" rowspan = "${item.vc_height }">
							     		<td align="center"  <c:if test="${item.vc_isNull eq '1' }">style="color:red;"</c:if> <c:if test="${item.vc_edit eq '0' }">style="color:green !important;"</c:if>>${item.name }<c:if test="${item.id ne '1' }" >：</c:if></td>
							     		<td align="left" >
							     		<c:if test="${item.id ne '1' }" >
							     			<c:choose>
							     				<c:when test="${item.vc_type eq 'input' }">
							     					<input <c:if test="${item.vc_edit eq '0' }">readonly="readonly"</c:if> class="input" name = '${item.fielName }--'   value="${item.vc_val }"  />
							     				</c:when>
							     				<c:when test="${item.vc_type eq 'select' }">
							     					<select <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if> <c:if test="${item.vc_edit eq '0' }">disabled=true</c:if> class="select" name = '${item.fielName }--' <c:if test="${item.name eq '其他目的' }">style="display:none;" id="qtmd"</c:if> <c:if test="${item.name eq '查档目的' }"> id="cdmd" onchange="showMD(this)"</c:if><c:if test="${item.name eq '查阅结果' }"> id="cyjg"</c:if> >
							     						  <option value=""></option>
							     						 <c:forEach items="${item.basicList}" var="data" varStatus="status">
							     							<option value="${data.feilName }" <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if>>${data.dataName }</option>
							     						</c:forEach>
							     					</select>
							     				</c:when>
							     				<c:otherwise>
							     					<textarea name = '${item.fielName }--'  <c:if test="${item.vc_edit eq '0' }">readonly="readonly"</c:if> class="textarea" rows="${item.vc_height }" cols="${item.vc_weight }">${item.vc_val }</textarea>
							     				</c:otherwise>
							     			</c:choose>
							     			</c:if>
							     		</td>
							     	</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan = '2' >
						<table class="table">
							  <c:forEach items="${mid}" var="item" varStatus="status">
							  	<tr  style="height:${item.vc_height * 40}px;" rowspan = "${item.vc_height }">
							  		<td width="18%" align="center"  <c:if test="${item.vc_isNull eq '1' }">style="color:red;"</c:if> <c:if test="${item.vc_edit eq '0' }">style="color:green !important;"</c:if>>${item.name }<c:if test="${item.id ne '1' }" >：</c:if></td>
									  	<c:choose>
									  		<c:when test="${item.vc_weight eq '2' }">
									  		<td colspan = '2'  align="left" >
									  				<c:choose>
										     				<c:when test="${item.vc_type eq 'input' }">
										     					<input <c:if test="${item.vc_edit eq '0' }">readonly="readonly"</c:if> class="input" name = '${item.fielName }--'   value="${item.vc_val }"  />
										     				</c:when>
										     				<c:when test="${item.vc_type eq 'select' }">
										     					<select <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if> <c:if test="${item.vc_edit eq '0' }">disabled=true</c:if> class="select" name = '${item.fielName }--'  <c:if test="${item.name eq '其他目的' }">style="display:none;" id="qtmd"</c:if> <c:if test="${item.name eq '查档目的' }"> id="cdmd" onchange="showMD(this)"</c:if><c:if test="${item.name eq '查阅结果' }"> id="cyjg"</c:if>>
										     						  <option value=""></option>
										     						 <c:forEach items="${item.basicList}" var="data" varStatus="status">
										     							<option value="${data.feilName }" <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if>>${data.dataName }</option>
										     						</c:forEach>
										     					</select>
										     				</c:when>
										     				<c:otherwise>
										     					<textarea name = '${item.fielName }--'  <c:if test="${item.vc_edit eq '0' }">readonly="readonly"</c:if> class="textarea" rows="${item.vc_height }" cols="${item.vc_weight }"></textarea>
										     				</c:otherwise>
										     		</c:choose>
										     	</td>
										     	<td> </td>
									  		</c:when>
									  		<c:otherwise>
									  			<td colspan = '3'>
									  				<c:choose>
										     				<c:when test="${item.vc_type eq 'input' }">
										     					<input <c:if test="${item.vc_edit eq '0' }">readonly="readonly"</c:if> class="input" name = '${item.fielName }--'   value="${item.vc_val }"  />
										     				</c:when>
										     				<c:when test="${item.vc_type eq 'select' }">
										     					<select <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if> <c:if test="${item.vc_edit eq '0' }">readonly="readonly"</c:if> class="select" name = '${item.fielName }--'  <c:if test="${item.name eq '其他目的' }">style="display:none;" id="qtmd"</c:if> <c:if test="${item.name eq '查档目的' }"> id="cdmd" onchange="showMD(this)"</c:if><c:if test="${item.name eq '查阅结果' }"> id="cyjg"</c:if>>
										     						 <option value=""></option>
										     						 <c:forEach items="${item.basicList}" var="data" varStatus="status">
										     						<option value="${data.feilName }" <c:if test="${data.feilName eq item.vc_val }">selected="selected" </c:if>>${data.dataName }</option>
										     						</c:forEach>
										     					</select>
										     				</c:when>
										     				<c:otherwise>
										     					<textarea name = '${item.fielName }--'  class="textarea" rows="${item.vc_height }" cols="${item.vc_weight }">${item.vc_val }</textarea>
										     				</c:otherwise>
										     		</c:choose>
										     	</td>
									  		</c:otherwise>
									  	</c:choose>
							  	</tr>
						  </c:forEach>
					</table>
					</td>
				</tr>
			</table>
			</form>
		</div>
		<div style="background-color: aliceblue;height: 40px;">
			<c:if test="${'3' ne stuts}">
    			<a class="wf-btn-primary save-btn" href="javascript:" onclick="saveStore()" >保存</a>
				<a class="wf-btn-primary save-btn" href="javascript:" onclick="deleteStore()" >删除</a>
			</c:if>
				<a class="wf-btn-primary save-btn" href="javascript:" onclick="printStore()" >导出</a>
		</div>
		<div>
			<form method="post" id="form" name="form" action="${ctx }/model_toModelTask4Key.do?type=1" class="tw-form">
				
				<input type="hidden" id="jydId" name="jydId" value="${id}">
				<table  style="width: 100%;" id="storetable">
						<thead>
							<tr style="background-color: #018cee;color: white;font-size: 14px;">
				    	  		<th style="width: 20px;"></th>
				    	  		<th>操作</th>
				    	  		<c:forEach items="${storeList}" var="item" varStatus="status2">
				    	  			<th align="center" style="padding: 4px;font-size: 15px;">${item.vc_name }</th>
				    	  		</c:forEach>
		                    </tr>
				    	</thead>
				    	<c:forEach items="${valList}" var="data" varStatus="status3">
				    		<tr>
				    			<td align="center" >
									<input type="checkbox" name="selid" id="${data[0]}"  value="${data[0]}"  >
								</td>
								<td align="center" >
									<button type="button" class="btn_xq" onclick="showDetail('${data[1]}')"></button>
								</td>
								<c:forEach items="${storeList}" var="item2" varStatus="status4">
									<c:set var="index" value="${status4.count+1}"></c:set>
									<td align="center"  style = "height: 40px;font-size: 16px;" title ="${data[index]}" >
									<c:if test="${item2.vc_isEdit eq '0' }">
										${data[index]}
									</c:if>
				    	  			<c:if test="${item2.vc_isEdit eq '1' }">
				    	  				<c:if test="${' ' eq item2.vc_system}">
					     					<input class="input" style="width: 60px;" name = '${item2.vc_fielName }${status3.count}' value="${data[index]}"  />
					     				</c:if>
					     				<c:if test="${' ' ne item2.vc_system}">
					     					<select class="select"  name = '${item2.vc_fielName }${status3.count}' id="lyfs${status3.count}" >
					     						<option value=""></option>
					     						<c:forEach items="${sysmap[item2.vc_system]}" var="sysdata" varStatus="status">
					     						<option value="${sysdata.feilName }" <c:if test="${sysdata.feilName eq data[index] }">selected="selected" </c:if>>${sysdata.dataName }</option>
					     						</c:forEach>
					     					</select>
					     				</c:if>
									</c:if>
									</td>
				    	  		</c:forEach>
				    		</tr>
				    	</c:forEach>
					</table>
				</form>
		</div>
		<div  class="main"></div>
		<iframe id="download_iframe" name="download_iframe" style="display: none"></iframe>
	</div>
</body>
<script type="text/javascript">

$(document).ready(function (){
	$(".main").height(20);
	$(".tw-layout").css("height",$(window).height());
});

$(document).ready(function (){
    var left = $("#leftTable").height()
    var right = $("#rightTable").height()
    var cnum = left - right;
    if(cnum>0){
    	$("#rightTable").css("margin-top","-"+cnum+"px");
    }else{
    	$("#leftTable").css("margin-top","-"+cnum+"px");
    }
});
	
	function regist(){
		var str = '';
		$("input[name$='--']").each( 
				function(){  
					str += $(this).attr("name")+$(this).val()+" ;";
					}  
		)
		$("select[name$='--']").each( 
				function(){  
					str += $(this).attr("name")+$(this).val()+" ;";
					}  
		)
		$("textarea[name$='--']").each(
				function(){  
					str += $(this).attr("name")+$(this).val()+" ;";
					}  
		)
		var id = $("#id").val();
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:{"str":str,"id":id},
	        url:"${ctx}/using_addRegist.do",
	        success:function(result){
	        	if("false" == result){
	        		alert("保存失败");
	        	}else{
	        		alert("保存成功");
	        		parent.location.reload();
	        	}
	        	 
	        }
	    })
	}
	
	function saveStore(){
		var data = $("#form").serialize();     
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:data,
	        url:"${ctx}/using_saveStore.do",
	        success:function(result){
	        	if("false" == result){
	        		alert("保存失败");
	        	}else{
	        		alert("保存成功");
	        		window.location.reload();
	        	}
	        	 
	        }
	    })
	}
	
	function restting(){
		window.location.reload();
	}
	
	function checkBorrow(id) {
		var data = $("#form").serialize();     
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:data,
	        url:"${ctx}/using_saveStore.do",
	        success:function(result){
	        	if("false" == result){
	        		alert("保存失败");
	        	}else{
	        		$.ajax({
	        	        async:true,//是否异步
	        	        type:"POST",//请求类型post\get
	        	        cache:false,//是否使用缓存
	        	        dataType:"text",//返回值类型
	        	        data:{"id":id},
	        	        url:"${ctx}/using_checkBorrow.do",
	        	        success:function(result){
	        	        	window.location.reload();
	        	        }
	        	    })
	        	}	 
	        }
	    })
		
	}
	
	function endBorrow(id){
		var row = $("#storetable").find("tr").length;
		if(row==1){
			alert("选择要查询的档案");
			return;
		}
		var tm=0;
		for(var i=1;i<row;i++){
			var lyfs = document.getElementById("lyfs"+i).value;
			if(lyfs==""){
				tm++;
			}
		}
		if(tm>0){
			alert("请选择利用方式，并点击下方保存按钮");
			return;
		}
		//先保存下，防止遗漏
		var data = $("#form").serialize();     
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:data,
	        url:"${ctx}/using_saveStore.do",
	        success:function(result){
	        	if("false" == result){
	        		alert("保存失败");
	        	}else{
	        		
	        		
	        		var sc = "";
	        		if($("#cyjg").length > 0) {
	        			sc = $("#cyjg option:selected").val();
	        			if(typeof sc == "undefined" || sc == null || sc == "") {
	        				alert("请选择查阅结果");
	        				return;
	        			}
	        		}
	        		var myd = "7";
	        		//调用评价器
	        		var pwdpad=document.getElementById('pwdpad');	
	        		var lRet= null;
	        		try {
	        			lRet = pwdpad.Open();
	        		}catch(err){
	        			alert("启用评价器失败，请连接评价器后安装PwdPad43OCX.ocx等控件");
	        			return;
	        		} 
	        		if(lRet == 0){
	        			lRet = pwdpad.Evaluate(7,0);
	        			if(lRet != 0) {
	        				alert("评价失败");
	        				return;
	        			}
	        			var flag = pwdpad.PadData;
	        			//1 非常满意 7 四键设备满意 2 基本满意 3 不满意
	        			if(flag != "1" && flag != "7" && flag != "2" && flag != "3") {
	        				alert("评价失败");
	        				return;
	        			}
	        			if(flag == "1") {
	        				myd = "非常满意";
	        			} else if(flag == "7") {
	        				myd = "满意";
	        			} else if(flag == "2") {
	        				myd = "基本满意";
	        			} else if(flag == "3") {
	        				myd = "不满意";
	        			}
	        		} else {
	        			alert("启用评价器失败，请连接评价器后安装PwdPad43OCX.ocx等控件");
	        			return;
	        		} 
	        		$.ajax({
	        	        async:true,//是否异步
	        	        type:"POST",//请求类型post\get
	        	        cache:false,//是否使用缓存
	        	        dataType:"text",//返回值类型
	        	        data:{"id":id,"myd":myd,"cyjg":sc},
	        	        url:"${ctx}/using_endBorrow.do",
	        	        success:function(result){
	        	        	window.location.reload();
	        	        }
	        	    });
	        		
	        	} 
	        }
	    })
		
		
		
	}
	function printStore(){
		document.getElementById("download_iframe").src='${ctx}/using_printStore.do?id=${id}';
	}
	function deleteStore(){
		var objs = document.getElementsByTagName('input');
		var ids = '';
		for(var i=0; i<objs.length; i++) {
		   if(objs[i].type.toLowerCase() == 'checkbox' && objs[i].name=='selid' && objs[i].checked==true ){
			  ids += "'"+objs[i].value+"',";
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
			        data:{"ids":ids,"id":"${id}"},
			        url:"${ctx}/using_deleteStore.do",
			        success:function(text){
			        	 window.location.reload();
			        }
			    })
		}
	}
	
	function showMD(obj){
		if(obj.value=="其他"){
			document.getElementById("qtmd").style.display="";
		}else{
			document.getElementById("qtmd").value="";
			document.getElementById("qtmd").style.display="none";
		}
	}
	
	function apply(id){
		var row = $("#storetable").find("tr").length;
		if(row==1){
			alert("选择要查询的档案");
			return;
		}
		var tm=0;
		for(var i=1;i<row;i++){
			var lyfs = document.getElementById("lyfs"+i).value;
			if(lyfs==""){
				tm++;
			}
		}
		if(tm>0){
			alert("请选择利用方式，并点击下方保存按钮");
			return;
		}
		//先保存下，防止遗漏
		var data = $("#form").serialize();     
		$.ajax({
	        async:true,//是否异步
	        type:"POST",//请求类型post\get
	        cache:false,//是否使用缓存
	        dataType:"text",//返回值类型
	        data:data,
	        url:"${ctx}/using_saveStore.do",
	        success:function(result){
	        	if("false" == result){
	        		alert("保存失败");
	        	}else{
	        		$.ajax({
	        	        async:true,//是否异步
	        	        type:"POST",//请求类型post\get
	        	        cache:false,//是否使用缓存
	        	        dataType:"text",//返回值类型
	        	        data:{"id":id},
	        	        url:"${ctx}/using_applyForm.do",
	        	        success:function(text){
	        	        	if(text == "success") {
	        	        		alert("申请成功");
	        	        		var index = parent.layer.getFrameIndex(window.name);
	        					parent.layer.close(index);
	        	        	}
	        	        }
	        	    });
	        	} 
	        }
	    })
		
	}
	
	function showDetail(esPath){
		var url = '${ctx}/model_showDetail.do?lookType=2&esPath='+esPath;
		var index = parent.layer.open({
			title:"浏览数据",
		    type: 2,
		    content: url,
		    area: ['300px', '195px']
		});
		parent.layer.full(index);
	}
</script>
<%@ include file="/common/function.jsp"%>
</html>
