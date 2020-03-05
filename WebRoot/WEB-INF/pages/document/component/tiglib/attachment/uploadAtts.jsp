<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>上传正文附件</title>
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
			  /*height: 213px;*/
			margin-bottom: 10px;
			  width: 370px;
			}
		</style>
        <script type="text/javascript">
        	var docguid= "${docguid}";
        	var isReciveAtt= "${isReciveAtt}";
			$(function() {
				// setting  uploadify
            	$('#custom_file_upload').uploadify({
            		'uploader'       : '${ctx}/widgets/component/taglib/attachment/uploadify/uploadify.swf',
            		'script'         : '${ctx}/attachment_uploadAtts.do',
            		'cancelImg'      : '${ctx}/widgets/component/taglib/attachment/uploadify/cancel.png',
            		'folder'         : '${ctx}',
            		'fileDataName'   : 'file',
            		'wmode'      	 : 'opaque',       //它的默认值是opaque，把它改成transparent就行了，也就是把那片白色区域透明化
            		'displayData'    : 'percentage',        //有speed和percentage两种，一个显示速度，一个显示完成百分比 
            		'multi'          : false,
            		'auto'           : false,
            		'fileExt'        : '*.doc;*.docx;*.pdf;*.xls;*.xlsx;*.jpg;*.png;*.bmp;*.true;*.rar;*.zip;*.7z;*.tif;*.ceb',
            		'fileDesc'       : '请上传*.doc;*.docx;*.pdf;*.xls;*.xlsx;*.jpg;*.png;*.bmp;*.true;*.rar;*.zip;*.7z;*.tif;*.ceb',
            		'queueID'        : 'custom-queue',
            		'queueSizeLimit' : 10,
            		'simUploadLimit' : 10,
            		'sizeLimit'		 : 1073741824, //设置单个文件大小限制，单位为byte
            		'removeCompleted': false,
					'onComplete'	 :function(event, ID, fileObj, response, data){
                		jQuery('#custom_file_uploadUploader').css("display","none");
                		alert(fileObj.name);
            		},
            		'scriptData':{'docguid':docguid,'isReciveAtt':isReciveAtt},
            		'onError'        : function(event, queueID, fileObj) {
            		    alert("文件:" + fileObj.name + "上传失败！请确定文件未超出大小（1G）或文件路径太深！");
            		}
            	});
            });
			function closeX(){
                window.close();
			}
        </script>
</head>
<base target="_self">
<body onunload="closeX();" style=" background-color: #E2E7EA; overflow-x: hidden;">
<div class="w-here">
	<div class="w-here-box"><span>上传文件</span></div>
	 </div>
           <div id="custom-demo" class="demo">
			 <div class="demo-box">
	        	<table width="100%" class="tbl-main" border="0" cellpadding="5" cellspacing="0">
                    <tr>
                        <td colspan="2">
                          	<font color="red" >提示：附件文件只支持.doc,.docx,.pdf,.xls,.xlsx,.jpg,.png,.bmp,.true.,.rar,.zip,.7z,.tif</font>
                        </td>
                    </tr>
					<tr>  
						<td style="text-align:center;">
							<input id="custom_file_upload" type="file" name="file" />
							<div id="custom-queue" style="margin:0 auto;"></div>
						</td>
					</tr>
					<tr>
						<td >
							 文件标题：<input type="text" id="title" name="title" value="${title}"/>&nbsp;&nbsp;&nbsp;&nbsp;
							 文件类型：<select mice-select="select" name="type" id="type" value="${type}">
							 <c:forEach items="${attsextTypeList}" var="attType">
	                                 <option <c:if test="${attType.type == '${type}'}">selected</c:if>>${attType.type}</option>
							 	</c:forEach>
                             </select>
						</td>
					</tr>
					<tr>
						<td valign="middle" style="line-height:40px; text-align:center;">
							
							&nbsp;&nbsp; 
                            <input type="button" value="开始上传" class="btn" onclick="javascript:$('#custom_file_upload').uploadifySettings('scriptData',{'title':$('#title').val(),'type':$('#type').val()});$('#custom_file_upload').uploadifyUpload();"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" value="取消上传" class="btn" onclick="javascript:$('#custom_file_upload').uploadifyClearQueue();"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" value="保存" class="btn" onclick="closeX();"/>
						</td>
                    </tr>
                </table>
               </div>
			</div>
            <input type="hidden" id="docguid" name="docguid" value="" />
		<!-- 用于记录附件个数 -->
	    <input type="hidden" id="num" value="0"/>
    </body>
</html>
