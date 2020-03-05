<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>上传附加附件</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <link rel="stylesheet" type="text/css"	href="${ctx}/widgets/component/taglib/attachment/css/attachment.css" />
		<script type="text/javascript" src="${ctx}/widgets/component/taglib/attachment/uploadify/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/component/taglib/attachment/uploadify/jquery.uploadify.v2.1.4.js"></script>
		<script type="text/javascript" src="${ctx}/widgets/component/taglib/attachment/uploadify/swfobject.js"></script>
		<style type="text/css">
			#custom-demo .uploadifyQueueItem {
			  background-color: #FFFFFF;
			  border: none;
			  border-bottom: 1px solid #E5E5E5;
			  font: 11px Verdana, Geneva, sans-serif;
			  height: 30px;
			  margin-top: 0;
			  padding: 10px;
			  width: 450px;
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
			  /*height: 300px;*/
			  margin-bottom: 10px;
			  width: 500px;
			}
		</style>
        <script type="text/javascript">
           var res=[];
       	   var docguid= "${docguid}";
       	   var isReciveAtt= false;
           $(function() {
            	$('#custom_file_upload').uploadify({
            		'uploader'       : '${ctx}/widgets/component/taglib/attachment/uploadify/uploadify.swf',
            		'script'         : '${ctx}/meeting_uploadAtts.do',
            		'cancelImg'      : '${ctx}/widgets/component/taglib/attachment/uploadify/cancel.png',
            		'folder'         : '${ctx}/uploads',
            		'fileDataName'   : 'file',
            		'wmode'      	 : 'opaque',       //它的默认值是opaque，把它改成transparent就行了，也就是把那片白色区域透明化
            		'displayData'    : 'percentage',        //有speed和percentage两种，一个显示速度，一个显示完成百分比 
            		'multi'          : true,
            		'auto'           : false,
            		//'fileExt'        : '*.doc;*.ceb',
            		//'fileDesc'       : '支持格式：doc,ceb',
            		'queueID'        : 'custom-queue',
            		'queueSizeLimit' : 1,
            		'simUploadLimit' : 10,
            		'sizeLimit'		 : 1073741824, //设置单个文件大小限制，单位为byte
            		'removeCompleted': false,
            		'scriptData'	 :{'docguid':docguid,'isReciveAtt':isReciveAtt},
            		'onError'        : function(event, queueID, fileObj) {
            		    alert("文件:" + fileObj.name + "上传失败！请确定文件大小<1G或文件路径太深！");
            		},
            		'onComplete':function(event, ID, fileObj, response, data){
            			res.push(response);
            		}
            	});
            });	
			function closeX(){
			   window.returnValue=res;
               window.close();  
			}
			function clearRes(){
				res=null;
			}
        </script>
    </head>
    <base target="_self">
    <body onunload="closeX();" style=" background-color:#ffffff;overflow-x: hidden;">
               <div id="custom-demo" class="demo">
								<div class="demo-box"> 
		<table width="100%" class="tbl-main" border="0" cellpadding="5" cellspacing="0">
                    <tr>
                        <td style="text-align:left;padding-left:10px;"><font color="red">提示：附件文件只支持.doc,.docx,.ceb,.pdf,.xls,.xlsx,.jpg,.png,.bmp,.true。</font> 
                        </td>
                    </tr>
					<tr>
						<td style="text-align:left;padding-left:10px;">
							<input id="custom_file_upload" type="file" name="file"/>
							<div id="custom-queue"></div>
						</td>
					</tr>
					<tr>
						<td valign="middle" style="line-height:40px; text-align:center;">
							  
							&nbsp;&nbsp; 
                            <input style="padding-left:10px;padding-right:10px;" type="button" value="上传" class="btn" onclick="javascript:$('#custom_file_upload').uploadifyUpload();"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input style="padding-left:10px;padding-right:10px;" type="button" value="撤消" class="btn" onclick="javascript:$('#custom_file_upload').uploadifyClearQueue();clearRes();"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input style="padding-left:10px;padding-right:10px;" type="button" value="关闭" class="btn" onclick="closeX();"/>
						</td>
                    </tr>
                </table></div>
							</div>
                <input type="hidden" id="docguid" name="docguid" value="" />
				<!-- 用于记录附件个数 -->
	            <input type="hidden" id="num" value="0"/>
        <a id="a" href="javascript:;" target="_self" onclick="window.location.reload();" style="display: none;"></a>
        <input type="hidden" id="res" value=""/>
    </body>
</html>
