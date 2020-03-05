 <!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>文号表单</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
    </head>
	<base target="_self">  
    <body >
    	<br><br>
		<br/><br/>
		<div id="tb1">
			<div id="tempWH" style="margin-left:100px;font-size:15px;display: inline;">${docNum}</div>&nbsp;&nbsp;&nbsp;
			<input style="width:30px;" type="text" id="attachval" maxlength="2"/>
			<input id="attach" type="button" value="生成附号" class="btn">
			<input id="validate" type="button" value="确定" class="btn">
		</div>
		<br/><br/>
        
        <script src="${curl}/widgets/plugin/js/base/jquery.js"></script>
		<script type="text/javascript">
			//格式化字符串
        	function format(str,size){
				if(str.length===size){
					return str;
				}else{
					var fs = "";
					for(var i=0,j=size-str.length; i<j; i++){
						fs += "0";
					}
					return fs+str;
				}
			}
			function getVal(str){
				if(str.match(/^0\d+$/)){
					return str.substr(1,str.length);
				}
				return str;
			}
			$("#attach").bind("click",function(){
				$.ajax({
				    url: 'docNumber_getDocNumAttach.do',
				    type: 'POST',
				    cache:false,
				    async:false,
				    dataType:'text',
				    data:{'docNum':'${docNum}'},
				    error: function(msg){
				        alert('AJAX调用错误(docNumber_isDocNumUse.do)');
				    },
				    success: function(msg){
					   //alert(msg);
					   $("#attachval").val(format(msg,2));
				    }
				});
			});
        	$("#validate").bind("click",function(){
            	var flag = false;
        		if($("#attachval").val().length==0){
            		 alert("请先生成文号"); 
            		 return;
        		}
            	$.ajax({
				    url: 'docNumber_getDocNumAttach.do',
				    type: 'POST',
				    cache:false,
				    async:false,
				    data:{'docNum':'${docNum}'},
				    dataType:'text',
				    error: function(msg){
				        alert('AJAX调用错误(docNumber_isDocNumUse.do)');
				    },
				    success: function(msg){
					    if(getVal($("#attachval").val())>=msg){
						    flag = true;
					    }
				    }
				});
				if(flag===false){
					alert("文号已被使用!");
					return;
				}
            	var docNum = '${docNum}'+'附'+format($("#attachval").val(),2)+'号';
				var obj = new Object();
				obj.docnumber = docNum;
				window.returnValue = obj;
	           	window.close();
            });
        </script>
    </body>
</html>
