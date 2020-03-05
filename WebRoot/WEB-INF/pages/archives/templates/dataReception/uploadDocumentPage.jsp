<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<title>上传附加附件</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<base target="_self">
<link rel="stylesheet" type="text/css"
	href="${ctx }/widgets/component/taglib/attachment/webuploader-0.1.5/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/widgets/component/taglib/attachment/webuploader-0.1.5/bootstrap-theme.min.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/widgets/component/taglib/attachment/webuploader-0.1.5/webuploader.css">
<script type="text/javascript"
	src="${ctx }/widgets/component/taglib/attachment/webuploader-0.1.5/webuploader.js"></script>
	<!--oader-0.1.5/webuploader.nolog.min.js-->
<style type="text/css">
#custom-demo #custom-queue {
	border: 1px solid #E5E5E5;
	height: 200px;
	overflow: auto;
	margin: 10px 10px;
	padding: 0 3px 3px;
	/* width: 500px; */
}

.tbl-main {
	border: solid #D8D8D8;
	border-width: 1px 0px 0px 1px;
}

.tbl-main td,.tbl-main th {
	padding: 10px 0 6px 0;
	border: solid #D8D8D8;
	border-width: 0px 1px 1px 0px;
	height: 30px;
}
.removeBtn {
	cursor:pointer;
}
.progress {
    margin-bottom:10px!important;
}
#startChoose{
	float: left;
}
</style>
</head>
<base target="_self">
<body style="background-color: #ffffff; overflow-x: hidden;">
	<div id="custom-demo" class="demo">
		<div class="demo-box">
			<table width="100%" class="tbl-main" border="0" cellpadding="5"
				cellspacing="0">
				<!-- <tr>
					<td style="text-align: left; padding-left: 10px;"><font
						color="red">支持：doc,docx,pdf,xls,xlsx,jpg,png,bmp,true,tif,ceb,cebx,rar,zip</font></td>
				</tr> -->
				<tr>
					<td style="text-align: left; padding-left: 10px;">
						<div id="startChoose">选择文件</div>
						&nbsp;&nbsp;&nbsp;
						是否按规则重命名<select  name="nameRule" id="nameRule" ><option value="1">是</option><option value="0">否</option></select>
						&nbsp;&nbsp;&nbsp;
						 文件类型：<select  name="type" id="type">
								<option value="正文">正文</option><option value="附件">附件</option><option value="处理单">处理单</option>
	                           </select>	
					</td>
				</tr>
				<tr>
					<td>
						<div id="custom-queue"></div>
					</td>
				</tr>
				<tr>
					<td valign="middle" style="line-height: 40px; text-align: center;">
						<div class="btn btn-default" type="button" onclick="uploader.upload();">
		                	<i class="wf-icon-upload"></i> 上传
		            	</div>
		            	<div class="wf-btn" type="button" onclick="closeX();">
		                	<i class="wf-icon-close"></i> 取消
		            	</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<input type="hidden" id="docguid" name="docguid" value="" />
	<input type="hidden" id="num" value="0" />
	<input type="hidden" name="content" id="content">
	<input type="hidden" name="dates" id="dates">
</body>
<script type="text/javascript">
	var icon4ext = "";
	var uploader = WebUploader.create({
		swf : "${ctx}/widgets/component/taglib/attachment/webuploader-0.1.5/Uploader.swf",
		server : "${ctx}/dataReception_uploadAtts.do",
		pick : {
			id : "#startChoose",
			multiple : true
		},
		accept : {
			title : "仅支持如下文件的上传……(从后台获取)",//根据后台设置的文件上传后缀限制
			extensions : "doc,docx,pdf,xls,xlsx,jpg,png,bmp,true,tif,ceb,cebx,rar,zip",
			mimeTypes : '.doc,.docx,.pdf,.xls,.xlsx,.jpg,.png,.bmp,.true,.tif,.ceb,.cebx,.rar,.zip'
		},
		prepareNextFile : true,
		formData : {"pid":"${pid}","structureId":"${structureId}","nameRule":$("#nameRule").val(),"type":$("#type").val(),"fileSequence":""},//文件上传的参数列表
		fileNumLimit : 5,
		fileSizeLimit : 1073741824,//1g
		fileSingleSizeLimit : 209715200,//200m
		resize : false,
		thread : 1
	});
	// 当有文件选中进入队列时
	uploader.on('fileQueued', function(file) {
		if("doc,docx,".indexOf(file.ext.toLowerCase())>-1){
			icon4ext = "file-word-o";
		}else if("xls,xlsx,".indexOf(file.ext.toLowerCase())>-1){
			icon4ext = "file-excel-o";
		}else if("pdf,".indexOf(file.ext.toLowerCase())>-1){
			icon4ext = "file-pdf-o";
		}else if("jpg,png,bmp,tif,".indexOf(file.ext.toLowerCase())>-1){
			icon4ext = "file-photo-o";
		}else if("true,rar,zip,7z,".indexOf(file.ext.toLowerCase())>-1){
			icon4ext = "file-archive-o";
		}else if("mov,mp4.".indexOf(file.ext.toLowerCase())>-1){
			icon4ext = "file-video-o";
		}else{
			icon4ext = "file";
		}
		$fileArea = $('<div id="'+file.id+'" class="item"><h4 class="info" style="padding:5px 0px;">'+
				'<i class="wf-icon-'+icon4ext+'" style="padding-right:5px;"></i>'+file.name+'<i class="wf-icon-close removeBtn" style="color:#f75504;padding-left:5px;font-weight:normal;"></i></h4><p class="state" style="padding-bottom:5px;padding-left:18px;">等待上传……</p></div>');
		
		$("#custom-queue").append($fileArea);
		
		$fileArea.find("i.removeBtn").click(function(){
			uploader.removeFile(file.id);
			$("#"+file.id).remove();
			$("#content").val($("#content").val().replace(file.name+",",""));
		});
		$("#content").val($("#content").val()+file.name+",");
		$("#dates").val($("#dates").val()+new Date().getTime()+",");
	});

	//文件上传过程中创建进度条实时显示
	uploader.on('uploadProgress', function(file,percentage) {
        var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');

		$li.find("p.state").hide();
         
        // 避免重复创建
        if ( !$percent.length ) {
        $percent = $('<div class="progress progress-striped active"><div class="progress-bar" role="progressbar" style="width:0%;"></div></div>')
            .appendTo( $li )
            .find('.progress-bar');
        }

        $percent.css( 'width', percentage * 100 + '%' );
	});

	//文件上传成功
	uploader.on('uploadSuccess', function(file,response) {
		console.log(response);
		var id;
		if(response){
			id=response._raw;
		}
		$("#"+file.id).find("p.state").text("上传完成").show();
		$("#"+file.id).find(".progress").hide();
		$("#"+file.id).find("i.removeBtn").remove();
		/* layer.msg("上传完成",{
			time:2000
		}); */
	});

	//文件上传失败
	uploader.on('uploadError', function(file) {
		layer.msg("文件上传失败，请联系管理员",{
			time:2000
		});
	});

	//文件上传结束
	uploader.on('uploadComplete', function(file) {
		if(uploader.getStats().progressNum == 0){
			closeX();
		}
	});
	
	uploader.on("fileQueued",function(file){
		uploader.options.formData.fileSequence += file.name+",";
	});
	
	function closeX(){
		top.layer.closeAll();
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
