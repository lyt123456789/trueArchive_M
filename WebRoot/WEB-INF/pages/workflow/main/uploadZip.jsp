<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/headerbase.jsp"%>
<html>
    <head>
        <title>上传压缩文件</title>
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
			src="${ctx }/widgets/component/taglib/attachment/webuploader-0.1.5/webuploader.nolog.min.js"></script>
        <script type="text/javascript">
        	var uploader;
            $(function() {
            	uploader = WebUploader.create({
    				swf : "${ctx}/widgets/component/taglib/attachment/webuploader-0.1.5/Uploader.swf",
    				server : '${ctx}/mobileTerminalInterface_importWorkFlow.do',
    				pick : {
    					id : "#custom_file_upload",
    					multiple : true
    				},
    				prepareNextFile : true,
    				//formData : {"type":"111",'docguid':"${docguid}",'isReciveAtt':"${isReciveAtt}"},//文件上传的参数列表
    				fileNumLimit : 1,
    				resize : false,
    				auto:true
    			});
            	var loadIndex;
            	//文件上传过程中
            	uploader.on('uploadProgress', function(file,percentage) {
            		loadIndex = layer.load(1,{
    					shade:[0.1,'#fff']
    				});
            	});
    			//文件上传成功
    			uploader.on('uploadSuccess', function(file) {
    				 layer.msg("流程导入完成",{
    					time:2000
    				},function(){
						layer.close(loadIndex);
						parent.layer.close(parent.layer.getFrameIndex(window.name));
    				});
    			});
            });
			
        </script>
    </head>
    <base target="_self">
<body style="background-color: #E2E7EA; overflow-x: hidden;">
	<div class="w-here">
		<div class="w-here-box"><span></span></div>
	</div>
	<div id="custom-demo" class="demo">
		<div class="demo-box">
			<table width="100%" class="tbl-main" border="0" cellpadding="5"
				cellspacing="0">
				<tr>
					<td colspan="2"><font color="red">提示：请上传压缩文件</font></td>
				</tr>
				<tr>
					<td style="text-align: center;">
						<div id="custom_file_upload" type="file" name="file" >上传</div>
						<div id="custom-queue" style="margin: 0 auto;"></div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<input type="hidden" id="docguid" name="docguid" value="" />
	<!-- 用于记录附件个数 -->
	<input type="hidden" id="num" value="0" />
</body>
</html>
