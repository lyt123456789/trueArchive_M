 <!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="cn.com.trueway.base.util.*"%>
<!--实体类 -->
<%@page import="cn.com.trueway.sys.util.SystemParamConfigUtil" %>
<!-- JSTL 标签 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- struts2 标签 -->
<%@ taglib prefix="s" uri="/struts-tags" %>

<!-- 自定义标签库 -->
<%@ taglib uri="http://trueway.cn/taglib" prefix="trueway"%>

<!-- sitemesh 标签 -->
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>

<!-- displaytag 标签 -->
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!-- 简化书写 -->
<c:set var="dateFormatDisplay">yyyy.MM.dd</c:set>
<c:set var="timeFormatDisplay">yyyy-MM-dd HH:mm:ss</c:set>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="curl" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
<!-- UI base path ,author: purecolor@foxmail.com ,date: 2011/12/27 AM 9:21 -->
<% String RSUrl=SystemParamConfigUtil.getParamValueByParam("RSUrl"); pageContext.setAttribute("RSUrl",RSUrl);%>
<c:set var="cdn_js" value="${RSUrl}/plugin/js"/>
<c:set var="cdn_as" value="${RSUrl}/plugin/as"/>
<c:set var="cdn_imgs" value="${RSUrl}/theme"/>

<html>
    <head>
        <title>文号表单</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <!--表单样式-->
		<link rel="stylesheet" href="${curl}/docStatic/css/reset.css" type="text/css">
    	<link rel="stylesheet" href="${curl}/docStatic/css/titanic.css?2" type="text/css">
		<!--<link href="${curl}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
		<link href="${curl}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
		<link href="${curl}/tabInLayer/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="${curl}/tabInLayer/css/bootstrap-addtabs.css" rel="stylesheet" type="text/css" media="screen"/>
		<link href="${curl}/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
		 <link href="${curl}/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/> -->
		<link href="${curl}/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
		<link href="${curl}/dwz/button/ued.button.css" rel="stylesheet" type="text/css" />
		    	
		<!--JS -->
		<script src="${curl}/tabInLayer/js/jquery.min.js" type="text/javascript"></script>
		<script src="${curl}/tabInLayer/js/jquery-migrate-1.2.1.js" type="text/javascript"></script>
		<script src="${curl}/dwz/js/jquery.cookie.js" type="text/javascript"></script>
		<script src="${curl}/dwz/js/jquery.validate.js" type="text/javascript"></script>
		<script src="${curl}/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
		<script src="${curl}/dwz/js/dwz.min.js" type="text/javascript"></script>
		<script src="${curl}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
		<script src="${curl}/pdfocx/json2.js" type="text/javascript"></script>
				
		
        <script type="text/javascript">
        	//全局变量
        	//文号实例
        	var modelVal = null;
        	//序号策略
        	var numModel = null;
        	//年号策略,true:不参与排序|false:参与排序
        	var yearSta = null;
        	function setModel(str){
        		modelVal = str;
				var dnModel = str;
				//策略数组
	        	var array = dnModel.split(';')[0].split(',');
	        	//序号策略
	        	numModel = array[array.length-1].replace('号','');
	        	numModel = numModel.substring(numModel.indexOf("$"),numModel.lastIndexOf("$")+1);
	        	if(numModel.indexOf('$$')!==-1){
	        		numModel = '$'+numModel.split('$$')[1];
	        	}
	        	//年号策略
	        	yearSta = null;
	        	try{
	        		yearSta = array[0].split('$')[1];
	        		if(yearSta === 'nyear') yearSta = false;//年号参与排序
	        		else yearSta = true;//年号不参与排序
	        	}catch(e){}
        	}
        </script>
        <style type="text/css">
        	.dept-select{
        		position: relative;
			    width: 80px;
			    min-width: 80px;
			    border: 1px solid #e6e6e6;
			    border-radius: 4px;
			    height: 32px;
			    line-height: 32px;
			    outline: none;
			    vertical-align: middle;
        	}
        	.default-select-chus {
        		width: 100px;
        		display: inline-block;
        		z-index: 0;
        	}
        	.select-control-chus{
			    width: 10%;
			    min-width: 100px;
			    display: inline-block;
        	}
        	.select-input-chus{
			   	width: 10%;
			    min-width: 80px;
        	}
        	.select-menu-chus{
        		width: 10%;
        		max-height: 194px;
        		min-width: 100px;
        		overflow: auto;
        	}
        	.type-choose {
        		z-index: 1;
        	}
        	.select-menu.hide {
        		display: none;
        	}
        	
        	#whinput span,
        	#whinput input {
        		float: left;
        	}
        </style>
    </head>
	<base target="_self">  
    <body >
    	<div class="titanic-model">
	        <div class="titanic-choose-area">
	            <div class="form-group clearfix">
	                <label class="form-tit">文号类型选择：</label>
	                <div class='default-select type-choose'>
	                    <div class='form-select-control'>
	                    <%-- <c:if test="${isNewModel eq '1' and defaultDept}">
		                    	<input type='text' class='default-select-input' id='type_select' value="${defaultDept.departmentName}" readonly>
		                        <input type="hidden" id="whtype" value="${defaultDept.departmentGuid}" >
	                    	</c:if> --%>
	                        <input type='text' class='default-select-input' id='type_select' value="${firstValue[1]}" readonly>
	                        <input type="hidden" id="whtype" value="${firstValue[0]}" >
	                        <i class='icon-s-arrow'></i>
	                    </div>
	                    <ul class='select-menu' id='select_menu_wh'>
	                    	<c:forEach var="model" items="${list}" varStatus="statu">
							<li data-value="${model[0]}">${model[1]}</li>
							<c:if test="${statu.index == 0}">
								<script type="text/javascript">
									setModel('${model[0]}');
								</script>
							</c:if>
						</c:forEach>
	                    </ul>
	                   <%--  <c:if test="${isNewModel eq '1'}">
		                     <ul class='select-menu-dept'>
		                    <c:forEach var="d" items="${depts}" varStatus="statu">
								<li data-value="${d.departmentGuid}">${d.departmentName}</li>
							</c:forEach>
		                    </ul>
	                    </c:if> --%>
	                </div>
	                
	               <!--  <select id="whtype" class="form-select-control" onchange="createWH(this.value);">
						<c:forEach var="model" items="${list}" varStatus="statu">
							<option value="${model[0]}">${model[1]}</option>
							<c:if test="${statu.index == 0}">
								<script type="text/javascript">
									setModel('${model[0]}');
								</script>
							</c:if>
						</c:forEach>
	                </select> -->
	            </div>
	            
	            <div style="clear: both;"></div>
	            <div class="form-group clearfix" >
	                <label class="form-tit">文号：</label>
	                <div class="form-input-group clearfix" id="whinput">
	                    
	                    
	                </div>
	                <button class="generate-num" onclick="scxh();">生成序号</button>
	            </div>
	            <div style="clear: both;"></div>
	            <div id="tempWH" style="font-size: 15px;display: inline;"></div>
	        </div>
	        <!-- <div class="titanic-list-area">
	            <p>您还可以选择下列暂未使用的文号：</p>
	            <dl class="titanic-list-dl">
	                <dt class="clearfix">
	                    <span class="w-60">未使用的文号</span>
	                    <span class="w-40">操作</span>
	                </dt>
	                <dd class="clearfix">
	                    <span class="w-60">宁政发〔2018〕153号</span>
	                    <span class="w-40"><a href="javascript:;" data-num="153" class="use-btn" onclick="useTitanicNum(this)">使用</a></span>
	                </dd>
	                <dd class="clearfix">
	                    <span class="w-60">宁政发〔2018〕154号</span>
	                    <span class="w-40"><a href="javascript:;" data-num="154" class="use-btn" onclick="useTitanicNum(this)">使用</a></span>
	                </dd>
	            </dl>
	        </div> -->
	    </div>
    <%--<a id="wenhao" href="#"></a>
   	<table class="list" width="100%" targetType="navTab" asc="asc" desc="desc" layoutH="80">
		<tr><td width="270px;">
		<span style="font-family:黑体;font-size:13px;">文号类型选择:</span>
		<select id="whtype" onchange="createWH(this.value)" style="width: 150px">
			<c:forEach var="model" items="${list}" varStatus="statu">
				<option value="${model[0]}">${model[1]}</option>
				<c:if test="${statu.index == 0}">
					<script type="text/javascript">
						setModel('${model[0]}');
						/*
						modelVal = '${model[0]}';
						var dnModel = '${model[0]}';
						//策略数组
			        	var array = dnModel.split(';')[0].split(',');
			        	//序号策略
			        	numModel = array[array.length-1].replace('号','');
			        	numModel = numModel.substring(numModel.indexOf("$"),numModel.lastIndexOf("$")+1);
			        	if(numModel.indexOf('$$')!==-1){
			        		numModel = '$'+numModel.split('$$')[1];
			        	}
			        	//年号策略
			        	yearSta = null;
			        	try{
			        		yearSta = array[0].split('$')[1];
			        		if(yearSta === 'nyear') yearSta = false;//年号参与排序
			        		else yearSta = true;//年号不参与排序
			        	}catch(e){}
			        	*/
					</script>
				</c:if>
			</c:forEach>
    	</select>
    	</td>
		<td>
			<table >
				<tr>
					<td >
						<span style="font-family:黑体;font-size:13px;">文号:</span>
					</td><td >
						<div id="tempWH" style="font-size: 15px;display: inline;"></div>
					</td><td >	
						<a class="buttonActive" href="javascript:scxh();"><span>生成序号</span></a>
					</td>
				</tr>
			</table>
		</td>
		<%-- <td  width="60px;">
		<c:if test="${display != true}">
			<a class="buttonActive" href="javascript:back1();"><span>确定</span></a>
		</c:if>
		</td> --%>
	<%--	</tr>
   	</table>
   	 --%>
	<iframe id="unusednum" height="525px;" width="100%" frameborder="0" scrolling="auto">
	</iframe>
		
	<input type="hidden" id="msg" value=""/>
       
	<script type="text/javascript">
		//格式化字符串
       	function format(str,size){
			if(str.length===size || size == 0){
				return str;
			}else{
				var fs = "";
				for(var i=0,j=size-str.length; i<j; i++){
					fs += "0";
				}
				return fs+str;
			}
		}
       	//解析策略
       	function parseStrategy(str){
       		var list = new Array();
			if(str.indexOf("$")===-1){
				list[0] = str;
			}else{
				//[$fff$$bbbs$]
				if(str.indexOf("$$")!=-1){
					var ostr =  new Array("","","");
					var beginIndex = str.indexOf("$");
					var endIndex = str.lastIndexOf("$");
					ostr[0] = str.substring(0,beginIndex); //[
					list[0] = ostr[0];
					ostr[1] = str.substring(beginIndex, endIndex+1); //$fff$$bbbs$
					list[1] = ostr[1].split("$$")[0]+"$";
					list[2] = "$"+ostr[1].split("$$")[1];

					ostr[2] = str.substring(endIndex+1); //]
					list[3] = ostr[2];
				}else{
					//$fff$
					var begin = str.indexOf("$");
					var end = str.lastIndexOf("$");
					list[0] = str.substring(0,begin);
					list[1] = str.substring(begin, end+1);
					list[2] = str.substring(end+1);
				}
			}
			//alert(list);
			return list;
		}
		function parsestrategyOld(str){
			var reVal = new Array("","","");
			if(str.indexOf("$")==-1){
				reVal[0]=str;
			}else{
				var beginIndex = str.indexOf("$");
				var endIndex = str.lastIndexOf("$");
				reVal[0] = str.substring(0,beginIndex);
				reVal[1] = str.substring(beginIndex, endIndex+1);
				reVal[2] = str.substring(endIndex+1,str.length);
			}
			return reVal;
		}
		//解析文号实例
		function parseModel(str){
			//alert(str);
			var retVal = "";
			if(str.indexOf(";")!=-1){
				var model = str.split(";")[0];
				var key = model.split(",");
				for(var i=0,j=key.length;i<j;i++){
					if(key[i].indexOf("$")===-1){
						retVal += key[i];
					}
					if(key[i].indexOf("$")!=-1){
						var s = parseStrategy(key[i]);
						for(var i1=0,j1=s.length; i1<j1; i1++){
							if(s[i1].indexOf("$")===-1){
								retVal += s[i1];
							}
							if(s[i1].indexOf("$")!=-1){
								var size = $("#"+mid.rnum).attr("maxlength");
								size = size<10?size:0;
								var fxh = $("#"+s[i1].split("$")[1]).val();
								retVal += fxh;
							}
						}
					}
				}
			}        
			return retVal;
		}
		
		//解析文号实例得到生成后的文号
		function parseModel2Id(str){
			//alert(str);
			var retVal = new Array();
			if(str.indexOf(";")!=-1){
				var model = str.split(";")[0];
				var key = model.split(",");
				for(var i=0,j=key.length;i<j;i++){
					if(key[i].indexOf("$")!=-1){
						var s = parseStrategy(key[i]);
						for(var i1=0,j1=s.length; i1<j1; i1++){
							if(s[i1].indexOf("$")!=-1){
								retVal[i1] = s[i1].split("$")[1];
							}
						}
					}
				}
			}       
			return retVal;
		}
		//通用校验数字的格式(input框必须加MaxLength属性)  通过为true
       	function validateNumber(value,size){
           	var val = format(value,size);
           	if(val.match(/^\d*$/)||size==0){
				return true;
			}
			return false;
       	}
	</script>
       <script type="text/javascript">
       	//实例模型
		function model(docnh,gjdz,xh){
			this.docnh=docnh;
			this.gjdz=gjdz;
			this.xh=xh;
		}
		var m = null;
		//动态inout框的id
       	function modelid(vyear,ckv,rnum){
           	this.vyear = vyear;
           	this.ckv = ckv;
           	this.rnum = rnum;
       	}
		// 文号字段下拉
		function defaultSelectHandle() {
			$('.select-control-chus').bind('click', function() {
                var menu = $('.select-menu-chus').hasClass('show');
                if (menu) {
                    $('.select-menu-chus').removeClass('show');
                } else {
                    $('.select-menu-chus').addClass('show');
                }
            });
            $('.select-menu-chus li').bind('click', function() {
                var value = $(this).data('value');
                $('#chus').val(value);
                var txt = $(this).text();
                $('#type_select_chus').val(txt);
                $('.select-menu-chus').removeClass('show');
            });
            $('#type_select_chus').blur(function(){
            	$('#chus').val($(this).val());
            });
		}
       	var mid = new modelid("y","kv","num");
		//解析实例模型
		function createWH(whstr){
			    m = new model("","","");
            	//alert(whstr);
            	if(whstr==''||whstr==null){
					alert("本流程没有绑定文号模型或该文号不属于该流程");
					//window.close();
            	}

				//构建实例模型对象
				var group="false";
            	var arr = whstr.split(";");
				var amodel = arr[0].split(",");
				var afield = arr[1].split(",");
				for(var i=0;i<afield.length;i++){
					if(afield[i]=='gjdz'){
						m.gjdz = amodel[i];
					}
					if(afield[i]=='fwnh'){
						m.docnh = amodel[i];
						mid.vyear = m.docnh.substring(m.docnh.indexOf("$")+1,m.docnh.lastIndexOf("$"));
					}
					if(afield[i]=='fwxh'){
						m.xh = amodel[i];
						if(m.xh.indexOf("$$")===-1){
							mid.rnum = m.xh.substring(m.xh.indexOf("$")+1,m.xh.lastIndexOf("$"));
						}else{
							var cstr = m.xh.split("$$");
							mid.ckv = cstr[0].substring(cstr[0].indexOf("$")+1);
							mid.rnum = cstr[1].substring(0,cstr[1].lastIndexOf("$"));
						}
					}
				}
				
            	$.ajax({
				    url: 'docNumber_paserModel.do',
				    type: 'POST',
				    cache:false,
				    async:false,
				    data:{'dnmodel':whstr},
				    dataType:'text',
				    error: function(){
				        alert('AJAX调用错误(docNumber_paserModel.do)');
				    },
				    success: function(msg){
				    	document.getElementById('whinput').innerHTML=msg;
				    	defaultSelectHandle();
				    }
				});
				//设置全局变量属性
            	setModel(whstr);
            	//设置未使用文号
            	changeUnused();
        	}
			
			function scxh(){
				var size = $("#"+mid.rnum).attr("maxlength");
				size = size<10?size:0;
				var dnmodel = $("#whtype").val();
				var xh=m.xh;
				$.ajax({
				    url: 'docNumber_getNum.do',
				    type: 'POST',
				    cache:false,
				    async:false,
				    dataType:'text',
				    data:{'gjdz':m.gjdz,defid:'${defineId}',webid:'${webId}',
					    yearNum:yearSta==true?$("#"+mid.vyear).val():'',
					    lwdwlx:$('#'+mid.ckv).val(),xhModel:xh,'isChildWf':'${isChildWf}','itemId':'${itemId}'},
				    error: function(){
				        alert('AJAX调用错误(docNumber_getNum.do)');
				    },
				    success: function(msg){
				    	$('#'+mid.rnum).val(msg,size);
				    	//alert(msg);
				    }
				});
			}
			function back1(){
				var nd = $("#"+mid.vyear).val();
				var xh = $("#"+mid.rnum).val()==undefined?null:$("#"+mid.rnum).val();
				if(nd!==undefined&&!validateNumber(nd,4)){
					alert('年号填写有误!');
					$('#'+mid.vyear).val(new Date().getFullYear());
					return;
				}
				//校验序号
				if(xh!=null){
					if(xh==""){
						alert("请先生成序号!");
						return;
					}
					var size = $("#"+mid.rnum).attr("maxlength");
					size = size<10?size:0;
					var fxh = format(xh,size);
					//alert(fxh);
					if(!xh.match(/^\d*$/)){
						alert('文号填写有误!');
						$("#"+mid.rnum).val("");
						return;
					}
					var lwdwlx = $("#"+mid.ckv).val()==undefined?"":$("#"+mid.ckv).val();
					$.ajax({
					    url: 'docNumber_isDocNumUse.do',
					    type: 'POST',
					    cache:false,
					    async:false,
					    dataType:'text',
					    data:{'xh':fxh,webid:'${webId}',defid:'${defineId}','gjdz':m.gjdz,'nh':nd,'lwdwlx':lwdwlx,'isChildWf':'${isChildWf}','itemId':'${itemId}'},
					    error: function(){
					        //alert('AJAX调用错误(docNumber_isDocNumUse.do)');
					        $('#msg').val('error');
					    },
					    success: function(msg){
					    	$('#msg').val(msg);
					    }
					});
					if($('#msg').val()=='false'){
						alert("此文号已使用！");
			    		$("#"+mid.rnum).val('');
			    		return;
					}else if($('#msg').val()=='error'){
						return;
					}
				}
				var obj = new Object();
				obj.docnumber = parseModel($("#whtype").val());
				window.returnValue = obj;
	           	window.close();
			}
			
			function cdv_getvalues(){
				var nd = $("#"+mid.vyear).val();
				var xh = $("#"+mid.rnum).val()==undefined?null:$("#"+mid.rnum).val();
				if(nd!==undefined&&!validateNumber(nd,4)){
					alert('年号填写有误!');
					$('#'+mid.vyear).val(new Date().getFullYear());
					return "20001";
				}
				//校验序号
				if(xh!=null){
					if(xh==""){
						alert("请先生成序号!");
						return "20001";
					}
					var size = $("#"+mid.rnum).attr("maxlength");
					size = size<10?size:0;
					var fxh = xh;
					//alert(fxh);
					if(!xh.match(/^\d*$/)){
						alert('文号填写有误!');
						$("#"+mid.rnum).val("");
						return "20001";
					}
					var lwdwlx = $("#"+mid.ckv).val()==undefined?"":$("#"+mid.ckv).val();
					var model = $('#whtype').val();
					if(model.indexOf('$num4$')!=-1){
						if(fxh.length!=4){
							var t = 4-fxh.length;
							for(var i=0;i<t;i++){
								fxh="0"+fxh;
							}
						}
						$("#"+mid.rnum).val(fxh);
					}
					$.ajax({
					    url: 'docNumber_isDocNumUse.do',
					    type: 'POST',
					    cache:false,
					    async:false,
					    dataType:'text',
					    data:{'xh':fxh,webid:'${webId}',defid:'${defineId}','gjdz':m.gjdz,'nh':nd,'lwdwlx':lwdwlx,'isChildWf':'${isChildWf}','itemId':'${itemId}'},
					    error: function(){
					        //alert('AJAX调用错误(docNumber_isDocNumUse.do)');
					        $('#msg').val('error');
					    },
					    success: function(msg){
					    	$('#msg').val(msg);
					    }
					});
					if($('#msg').val()=='false'){
						alert("此文号已使用！");
			    		$("#"+mid.rnum).val('');
			    		return "20001";
					}else if($('#msg').val()=='error'){
						return "20001";
					}
				}
				var docnumber = parseModel($("#whtype").val());
				if(docnumber == 'undefined' || docnumber == 'null' ||docnumber == undefined || docnumber == null){
					docnumber = '';
				}
				return $("#whtype").val().replace(mid.vyear,nd.length).replace(mid.rnum,fxh.length)+"*"+docnumber;
			}
        </script>
        
        <script type="text/javascript">
			//根据来文类型设置来文号
        	function settype(type){
        		changeUnused();
	        }
	        $(function(){
	        	try{
	        		createWH($('#whtype').val());
	        		changeUnused();
	        	}catch (e) {
				}
		    });
	        $(function(){
				var str = "${value}";
				if(str!==null&&str.length!==0&&str!=="null"){
					//var numsize = $("#"+mid.rnum).attr("maxlength");
					var numsize = document.getElementById(mid.rnum).maxlength;
					numsize = !!numsize?numsize:0;
					$("#"+mid.rnum).val(str.substring(str.indexOf('号')-numsize,str.indexOf('号')));
					$("#"+mid.ckv).val(str.substring(str.indexOf('号')-numsize-2,str.indexOf('号')-numsize));
					//alert(str.substring(str.indexOf('号')-numsize,str.indexOf('号')));
				}
			});
		    function changeUnused(){
			    var lwdwlx = $("#"+mid.ckv).val()==undefined?"":$("#"+mid.ckv).val();
			    var nyear = $("#nyear").val();
			    var year = $("#year").val();
			    var nh = "";
			    if(nyear != null && nyear != 'unfefined'){
			    	nh = nyear;
			    }else if(year != null && year != 'unfefined'){
			    	nh = year;
			    }
			    if(yearSta === false)
				$("#unusednum").attr("src","docNumber_getDocNumUnused.do?defid=${defineId}&webid=${webId}&gjdz="+encodeURI(encodeURI(m.gjdz))+"&lwdwlx="+lwdwlx+"&model="+encodeURI(encodeURI(modelVal))+"&modelVal="+encodeURI(encodeURI(numModel))+"&isChildWf=${isChildWf}&itemId=${itemId}");
			    else
				$("#unusednum").attr("src","docNumber_getDocNumUnused.do?defid=${defineId}&webid=${webId}&gjdz="+encodeURI(encodeURI(m.gjdz))+"&nh="+nh+"&lwdwlx="+lwdwlx+"&model="+encodeURI(encodeURI(modelVal))+"&modelVal="+encodeURI(encodeURI(numModel))+"&isChildWf=${isChildWf}&itemId=${itemId}&t="+new Date());
			};
        </script>
        <script type="text/javascript">
        	$(function(){
        		$('.type-choose').bind('click', '.form-select-control', function() {
                    var menu = $(this).find('.select-menu').hasClass('show');
                    if (menu) {
                    	$(this).find('.select-menu').removeClass('show').addClass('hide');
                    } else {
                    	$(this).find('.select-menu').addClass('show').removeClass('hide');
                    }
                });
                $('.type-choose .select-menu li').bind('click', function() {
                    var value = $(this).data('value');
                    $('#whtype').val(value);
                    var txt = $(this).text();
                    $('#type_select').val(txt);
                   
                    createWH(value)
                    
                    $("#select_menu_wh").addClass('hide');
                });
        		
        	});
        </script>
    </body>
</html>
