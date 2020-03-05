<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>上传附件</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
		<script type="text/javascript" src="${ctx}/widgets/component/taglib/attachment/uploadify/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/component/taglib/attachment/uploadify/jquery.uploadify.v2.1.4.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/component/taglib/attachment/uploadify/swfobject.js"></script>
		<link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
		<style type="text/css">
			#custom-demo .uploadifyQueueItem {
			  background-color: #F2FBFF;
			  border: none;
			  border-bottom: 1px solid #E5E5E5;
			  font: 11px Verdana, Geneva, sans-serif;
			  height: 50px;
			  margin-top: 0;
			  padding: 10px;
			  width: 350px;
			}
			#custom-demo .uploadifyError {
			  background-color: #FDE5DD !important;
			  border: none !important;
			  border-bottom: 1px solid #FBCBBC !important;
			}
			#custom-demo .uploadifyQueueItem .cancel {
			  float: right;
			}
			#custom-demo .uploadifyQueue .completed {
			  color: #C5C5C5;
			}
			#custom-demo .uploadifyProgress {
			  background-color: #E5E5E5;
			  margin-top: 10px;
			  width: 100%;
			}
			#custom-demo .uploadifyProgressBar {
			  background-color: #0099FF;
			  height: 3px;
			  width: 1px;
			}
			#custom-demo #custom-queue {
			  border: 1px solid #E5E5E5;
			  text-align:left;
			  margin-bottom: 10px;
			  width: 370px;
			}
		</style>
        <script type="text/javascript">
        	var commentId= "${commentId}";
        	var obj = new Object();
			$(function() {
            	$('#custom_file_upload').uploadify({
            		'uploader'       : '${ctx}/widgets/component/taglib/attachment/uploadify/uploadify.swf',
            		'script'         : '${ctx}/comment_toUploadAtt.do',
            		'cancelImg'      : '${ctx}/widgets/component/taglib/attachment/uploadify/cancel.png',
            		'folder'         : '${ctx}',
            		'fileDataName'   : 'commentFile',
            		'wmode'      	 : 'transparent',       //它的默认值是opaque，把它改成transparent就行了，也就是把那片白色区域透明化
            		'displayData'    : 'percentage',        //有speed和percentage两种，一个显示速度，一个显示完成百分比 
            		'multi'          : true,
            		'auto'           : false,
            		'fileExt'        : '*.*',
            		'fileDesc'       : '支持格式：*.*',
            		'queueID'        : 'custom-queue',
            		'queueSizeLimit' : 3,
            		'simUploadLimit' : 3,
            		'sizeLimit'		 : 1073741824, //设置单个文件大小限制，单位为byte
            		'removeCompleted': false,
					'onComplete'	 :function(event, ID, fileObj, response, data){
    					var idAndName = response+"|"+fileObj.name;
    					$("#attIdsAndNames").val($("#attIdsAndNames").val()+idAndName+";");
                		jQuery('#custom_file_uploadUploader').css("display","none"); 
                		if(data.fileCount==0){
							$("#closeWindow").click();
                		}
            		},
            		'scriptData':{'commentId':commentId},
            		'onError'        : function(event, queueID, fileObj) {
            		    alert("文件:" + fileObj.name + "上传失败！请确定文件未超出大小（1G）或文件路径太深！");
            		}
            	});
            });
			function closeX(){
				var idsAndNames = $("#attIdsAndNames").val();
				if(idsAndNames!=""){
					idsAndNames = idsAndNames.substring(0,idsAndNames.length-1);
					window.returnValue=idsAndNames;
				}else{
					window.returnValue="";
				}
                window.close();
			}
        </script>
    </head>
    <base target="_self">
    <body onunload="closeX()">
		<div class="bread-box">
			<div class="bread-title">
				<span class="bread-icon"></span>
				<span class="bread">上传附件 </span>
				<span class="bread-right-border"></span>
			</div>
		</div>
		<div id="custom-demo" class="demo">
			<table class="formTable">
				<tr>
					<td style="text-align:center;">
						<div id="custom-queue"></div>
						<input id="custom_file_upload" type="file" name="commentFile"/>
					</td>
				</tr>
				<tr>
					<td style="text-align:center;display:none">
                         <input id="closeWindow" type="button" value="关闭" onclick="closeX();"/>
                         <input type="hidden" id="attIdsAndNames" name="attIdsAndNames" value="">
                         <!-- 
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <input type="button" value="取消上传" mice-btn="icon-add" onclick="javascript:$('#custom_file_upload').uploadifyClearQueue();"/>
                          -->
					</td>
				</tr>
				<tr>
					<td style="text-align:center;">
                         <input type="button"  id="to_upload" value="开始上传"/>
                         &nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
        	</table>
        	
		</div>
		<script type="text/javascript">
			$("#to_upload").live("click",function(){
				$('#custom_file_upload').uploadifyUpload();
			});
		</script>
    </body>
</html>
