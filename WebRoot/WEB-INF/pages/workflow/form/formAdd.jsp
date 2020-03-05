<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <%@ include file="/common/headerbase.jsp"%>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/assets/themes/default/css/style.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/assets/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${ctx}/assets/plugins/laydate/laydate.js" type="text/javascript"></script>
	<style type="text/css">
		.font-input{
			height: 25px;
			width: 60px;
		}
	</style>
</head>
<body style="overflow-y:hidden;">
<form id="thisForm" method="POST" name="thisForm" action="${ctx }/form_addForm.do">
<table class="infotan" width="100%">
<tr>
 <td width="8%" class="bgs ls">表单英文名称：</td>
 <td width="15%"><input id="formName" type="text" name="zwkjForm.form_name" size="20" value=""> <span style="color:#C62112 !important;">*</span> </td>
 <td width="8%" class="bgs ls">表单中文名称：</td>
 <td width="15%"><input id="formCaption" type="text" name="zwkjForm.form_caption" size="20" value=""> <span style="color:#C62112 !important;">*</span></td>	
 <td width="8%" valign="top" class="bgs ls">涉及入库表：</td>
 <td width="25%" class="tdcol">
	 	<input  type="text" name="zwkjForm.insert_table"  id="insert_table" value="" style="width: 200px;height: 22px;float: left !important;"/>
	 	<span ><a class="buttonActive" style="display: inline;" href="javascript:selectTable();"><span>选择</span></a></span>
 </td>
 <td width="25%" class="tdcol" align="left">
 	<!-- <a class="wf-btn-blue" onclick="sub()">保存</a> -->
    <a class="wf-btn" onclick="returnToList();" style="float:right; margin-right:10px;">返回</a>
 </td>
</tr> 
<tr>
 <td width="8%" class="bgs ls">开始使用时间：</td>
 <td width="15%"><input id="beginDate" type="text" class="wf-form-text wf-form-date" name="zwkjForm.beginDate" readonly="readonly" value=""></td>
 <td width="8%" class="bgs ls">结束使用时间：</td>
 <td width="15%"><input id="endDate" type="text" class="wf-form-text wf-form-date" name="zwkjForm.endDate" readonly="readonly" value=""></td>
 <td width="8%" class="bgs ls">字体大小：</td>
 <td width="15%"><input id="fontSize" type="number" class="wf-form-text font-input" name="zwkjForm.fontSize" value=""></td>
 <td width="10%" class="tdcol" >
 </td>
</tr> 
</table>	 
		  <iframe id="iframe_form" src="${ctx }/htmlCreateTool/main.html" style="width: 99.8%;margin: auto;height: 350px;border: 1px dashed #C2C2C2"></iframe>
		  <!-- 以下四个隐藏域，用于读取文件流需要的路径和类型信息 -->
		  <input type="hidden" name="zwkjForm.form_htmlfilename" id="filename" value=""/>
		  <input type="hidden" name="workflowId" id="workflowId" value="${workflowId }"/>
	</form>
<script type="text/javascript" src="${ctx }/widgets/common/js/god_Core.js"></script>
<script type="text/javascript">

        $(document).ready(function(){
            $('#iframe_form').height($('body').height()-34);	
        });
		function selectTable(){
			var url = "${ctx}/form_selectTable.do?workflowId=${workflowId}&d="+Math.random();
			layer.open({
				title:'选择表单入库表',
				type:2,
				area:['600px','400px'],
				btn:["确定","取消"],
				content: url,
				yes:function(index,layero){
				 	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
		            var	value = iframeWin.selectTable();
					g.g('insert_table').value=value;
				 	layer.close(index);
				}
			});
		};
	
		//板式预览
		function plate_preview() {
			var filename = document.getElementById('filename').value;
			var json = getLocationJson();
			json = JSON2.stringify(json);
			var w = window.screen.availWidth ? window.screen.availWidth : '800';
			var h = window.screen.availHeight ? window.screen.availHeight: '600';
			window.showModalDialog("${ctx}/form_preview.do?filename=" + filename+ "&json="+json+"&d=" + Math.random(),
							window,'dialogWidth: '+ w+ 'px;dialogHeight: '+ h+ 'px; status: no; scrollbars: no; Resizable: no; help: no;center:yes;');
		}
		

		function getElementLeft(element) {
			var actualLeft = element.offsetLeft;
			var current = element.offsetParent;
			while (current !== null) {
				actualLeft += current.offsetLeft;
				current = current.offsetParent;
			}
			return actualLeft;
		}
		
		function getElementTop(element) {
			var actualTop = element.offsetTop;
			var current = element.offsetParent;
			while (current !== null) {
				actualTop += current.offsetTop;
				current = current.offsetParent;
			}
			return actualTop;
		}
		
		
		function getLocationJson(){
			var body = $(document.getElementById("iframe_form").contentWindow.document.body);
			//获取屏幕的宽度
			var screen = document.getElementById("iframe_form").getBoundingClientRect().right-document.getElementById("iframe_form").getBoundingClientRect().left;
			var factor =  Math.round((1420-screen)/2);
			var json = coverFormToTrueform(body,factor);
			return json;
		}
		
		function coverFormToTrueform(scope,factor){
			var forms={};
			$('input[type=text]',scope).each(function(){
				//console.log('text:: ',this.name);
				$(this).addClass('br');
				forms[this.name]={e:this,t:'text'};
			});
			$('input[type=radio]',scope).each(function(){
				//console.log('radio:: ',this.name);
				$(this).addClass('br');
				if(!forms[this.name]){
					forms[this.name]={e:this,t:'radio'};
				}
			});
			$('input[type=checkbox]',scope).each(function(){
				//console.log('checkbox:: ',this.name);
				$(this).addClass('br');
				if(!forms[this.name]){
					forms[this.name]={e:this,t:'checkbox'};
				}
			});
			$('select',scope).each(function(){
				//console.log('select:: ',this.name);
				$(this).addClass('br');
				forms[this.name]={e:this,t:'select'};
			});
			$('textarea',scope).each(function(){
				//console.log('textarea:: ',this.name);
				$(this).addClass('br');
				forms[this.name]={e:this,t:'textarea'};
			});
			var formpos=[];
			for(var i in forms){
				var ot=$(forms[i].e).closest("td")
					,of=ot.offset()
					,ow=ot.width()
					,oh=ot.height();
					
				if(of==null){
					of=ot.offset();
				}
				if(of!=null){
					 l = of.left,t = of.top;
					if(forms[i].t == 'textarea'){
						 ow = ow - 10;
						 oh = oh -10;
						 l = l+5;
					}  
				    l = Math.round(l);
					t = Math.round(t);
					ow = Math.round(ow);
					oh = Math.round(oh);
					formpos.push({"name":i,"type":forms[i].t,"startX":l-175+factor,"startY":t+50,"width":ow-20,"height":oh});
				}
			}
			return formpos;
		}
		
		function returnToList(){
			var formListUrl = "${ctx}/form_getFormList.do?workflowId=${workflowId}";
			window.location.href=formListUrl;
		}
		
		function saveStringToHtml(htmlStr){
			var formName = document.getElementById("formName").value;
			var formCaption = document.getElementById("formCaption").value;
			if(formName == ""){
				alert("请填写表单英文名！");
				return false;
			}
			if(formCaption == ""){
				alert("请填写表单中文名！");
				return false;
			}
			$.ajax({
    			url:"${ctx}/form_saveStringToHTML.do",
    			type:"post",
    			data:{
    				"htmlString":htmlStr,
    			},
    			async : false,
    			success: function(msg){
    				$("#filename").val(msg);
    				if(msg){
    					g.g('thisForm').submit();
    				}
    			}
    		});
		}
	</script>
	</body>
	<%@ include file="/common/function.jsp"%>
</html>
