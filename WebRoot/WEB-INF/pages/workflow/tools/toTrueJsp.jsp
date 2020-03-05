<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/header.jsp"%>
    <%@ include file="/common/headerindex.jsp"%>
    <title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
    <!--表单样式-->
	<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
	 
	 <link rel="stylesheet" href="${ctx}/css/base.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/css/common.css" type="text/css" />
	<script src="${ctx}/widgets/common/js/ajaxfileupload.js"></script>
	<script type="text/javascript">
	
	//开始文件转换
	function Switch(way){
		document.getElementById("form2").reset();
		var file = document.getElementById("content").value;
		
		if ('' == file){
			alert("请至少选择一个文件！");
			return;
		}
		
		//验证文件
		var files = file.split("\r\n");
		for(i=0;i<files.length-1;i++){
			if(files[i].indexOf('正在准备...')>=0){
				alert("文件还未准备就绪！");
				return;
			}
			if(way=='PDFtoTRUE'||way=='PDFtoPDF'){
				if(files[i].substring(files[i].lastIndexOf('.')+1)!='pdf'){
					alert("材料文件不是规定pdf文件！");
					return;
				}
			}
			if(way=='HTMLtoPDF'){
				if(files[i].substring(files[i].lastIndexOf('.')+1)!='html'){
					alert("材料文件不是规定html文件！");
					return;
				}
			}
		}

		$.ajax({  
			url : '${ctx}/supportTools_Switch.do?way='+way+'&emp='+$("#emp").val(),
			type : 'POST',  
			cache : false,
			async : false,
			data:$("#showform").serialize(),
			error : function() {
				alert('转换错误');
			},         
			success: function(result) {  
				alert('转换成功');
				var html = '<br/>转换文件下载链接：<br/><br/>';
				var download = result.split(";");
				for(i=0;i<download.length;i++){
					var fileName = download[i].substring(download[i].lastIndexOf('/')+1);
					html = html +'<a style="color:blue" href="${ctx}/supportTools_download.do?location='+encodeURI(encodeURI(download[i]))+'">'+fileName+'</a><br/>';
				}
				$("#download").html(html);
				$("#download").css('display', 'block');
				$("#content").val('');
			}                
		});
	}
	
	//更新列表转换文件
	function add(val){
		var filename = val.substring(val.lastIndexOf('\\')+1);

		var content = $("#content").val();
		if(content.indexOf(filename)>=0){
			alert("此文件已在转换列表!");
			return false;
		}
		$("#content").val("正在准备..."+filename+"\n"+$("#content").val());
	}
		
	//上传附件
	function doUpload(){
		var fileVal = document.getElementById('file').value;

		if(fileVal==null || fileVal==''){
			alert("请选择文件!");
			return false;
		}

		var fileName = fileVal.substring(fileVal.lastIndexOf('\\')+1);
		var name = fileVal.substring(fileVal.lastIndexOf('\\')+1);
		fileName = encodeURI(encodeURI(fileName));
		//检验文件是否存在
			$.ajaxFileUpload
	        (
	            {
	                url:'${ctx}/supportTools_uploadTempAttsext.do?emp='+$("#emp").val(),
	                secureuri:false,
	                fileElementId:'file',
	                success: function (result) 
	                {	//标注状态
	                	var content = $("#content").val();
	                	content = content.replace("正在准备..."+name,"已就绪..."+name);
	                	$("#content").val(content);
	                },
	                error: function (result)
	                {
	                	alert("文件准备出错！");
	                }
	            }
	        );

	}
	
	//在线浏览 
	function Preview(){
		document.getElementById("form1").reset();
		var fileVal = document.getElementById('file2').value;
		if(fileVal==null || fileVal==''){
			alert("请选择文件!");
			return false;
		}
		var fileType = fileVal.substring(fileVal.lastIndexOf('.')+1);
		if(fileType!='pdf'&&fileType!='true'){
			alert("所选文件不是true或pdf文件！");
			return false;
		}
		document.getElementById("form2").submit();
	    
	}
	
	</script>
	</head>
<style>
fieldset{margin-left:20px;border:1px solid #88999d;padding:5px;}
body{overflow:auto;}
</style>
<body>
<form  id="form1" action=""  style="width:80%">
	<input type="hidden" id="emp" name="emp" value="${emp}">
	<p class="cbo m10">
	<fieldset>
	<legend>文件转换/合并</legend>
	<div>
		<br/>		
		<div style="height:20px;">添加转换/合并任务：</div>
		<input type="file" id="file" name="file"  class="txt" onchange="add(this.value);doUpload()" />
	</div>
		<br/>
		<div style="height:20px;">转换材料列表：</div>
		<div>
		<textarea rows="6" cols="100" readonly id="content" name="content"></textarea>
		<div id="download" style="display:none;">
		
		</div>
		<br/>
		<br/>
		<input type="button" value="开始转换(PDF to TRUE)" onclick="Switch('PDFtoTRUE')">
		<input type="button" value="开始转换(HTML to PDF)" onclick="Switch('HTMLtoPDF')">
		<input type="button" value="开始转换(合并 PDF)" onclick="Switch('PDFtoPDF')">
		</div>
		<br/>
		<span style="color:#88999d;font-size:10px">(* WORD转PDF可以在OFFICE WORD中转换)</span>
	</fieldset>
</form>
<form  id="form2" action="${ctx}/supportTools_showTrueFile.do"  enctype="multipart/form-data" style="width:80%">
	<p class="cbo m10">
	<fieldset>
	<legend>TRUE/PDF文件在线查看</legend>
	<div>
		<br/>		
		<div style="height:20px;">添加查看文件：</div>
		<input type="file" id="file2" name="file2"  class="txt" />
	</div>
		<br/>
		<div>	
		<input type="button" value="在线查看" onclick="Preview()">
		</div>
		<br/>
	</fieldset>
</form>
</body>
</html>