<!DOCTYPE HTML>
<html>
    <head>
    	<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
		<%@ include file="/common/header.jsp" %>
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
       	   var docguid= "${docguid}";
       	   var isReciveAtt= "${isReciveAtt}";
         	window.onload=function(){
            	$('#custom_file_upload').uploadify({
            		'uploader'       : '${ctx}/widgets/component/taglib/attachment/uploadify/uploadify.swf?v='+new Date(),
            		'script'         : '${ctx}/attachment_uploadAtts.do',
            		'cancelImg'      : '${ctx}/widgets/component/taglib/attachment/uploadify/cancel.png',
            		'folder'         : '${ctx}/uploads',
            		'fileDataName'   : 'file',
            		'wmode'      	 : 'opaque',       //它的默认值是opaque，把它改成transparent就行了，也就是把那片白色区域透明化
            		'displayData'    : 'percentage',        //有speed和percentage两种，一个显示速度，一个显示完成百分比 
            		'multi'          : true,
            		'auto'           : false,
            		'fileExt'        : '.doc,.docx,.ceb,.pdf,.xls,.xlsx,.jpg,.png,.bmp,.true,.rar,.zip,.7z',
            		//'fileDesc'       : '支持格式：${fjlx}',
            		'queueID'        : 'custom-queue',
            		'queueSizeLimit' : 5,
            		'simUploadLimit' : 10,
            		'sizeLimit'		 : 1073741824, //设置单个文件大小限制，单位为byte
            		'removeCompleted': false,
            		'scriptData'	 :{'docguid':docguid,'isReciveAtt':isReciveAtt},
            		'onSelect'       : function(event, queueID, fileObj)
                    {
            			 $('#title').val(fileObj.name);
            			 var content = $("#content").val();
            			 content += fileObj.name+",";
            			 $("#content").val(content);
                     },
                     "onCancel":  function(event, queueID, fileObj){
                    	 var content = $("#content").val();
                    	 content = content.replace(fileObj.name+",", "");
                    	 $("#content").val(content);
                     },
            		'onError'        : function(event, queueID, fileObj) {
            		    alert("文件:" + fileObj.name + "上传失败！请确定文件大小<1G或文件路径太深！");
            		}
            	});
            };	
			function closeX(){
               window.close();  
			}
        </script>
    </head>
    <base target="_self">
    <body onunload="closeX();" style=" background-color:#ffffff;overflow-x: hidden;">
               <div id="custom-demo" class="demo">
								<div class="demo-box"> 
		<table width="100%" class="tbl-main" border="0" cellpadding="5" cellspacing="0">
                    <tr>
                        <td style="text-align:left;padding-left:10px;"><font color="red">
                        		提示：附件文件只支持.doc,.docx,.ceb,.pdf,.xls,.xlsx,.jpg,.png,.bmp,.true.,.rar,.zip,.7z</font> 
                        </td>
                    </tr>
					<tr>
						<td style="text-align:left;padding-left:10px;">
							<input id="custom_file_upload" type="file" name="file"/>
							<div id="custom-queue"></div>
						</td>
					</tr>
					<tr>
						<td style="text-align:left;padding-left:10px;">
							文件类型：
							<select mice-select="select" name="type" id="type" value="${type}" style="width:250px;height:25px;">
							 	<c:forEach items="${attsextTypeList}" var="attType">
	                                 <option <c:if test="${attType.type == '${type}'}">selected</c:if>>${attType.type}</option>
							 	</c:forEach>
                             </select>
						</td>
					</tr>
					<tr>
						<td style="text-align:left;padding-left:10px;">
							文件标题：
							<input type="text" id="title" name="title" value="${title}" style="width:450px;height:20px"/>
						</td>
					</tr>
					<tr>
						<td valign="middle" style="line-height:40px; text-align:center;">
							  
							&nbsp;&nbsp; 
							<!-- 调用 jquery.uploadify.v2.1.4.js 中的 uploadifySettings-->
                            <input style="padding-left:10px;padding-right:10px;" type="button" value="上传" class="btn" onclick="upload()"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input style="padding-left:10px;padding-right:10px;" type="button" value="撤消" class="btn" onclick="javascript:$('#custom_file_upload').uploadifyClearQueue();"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input style="padding-left:10px;padding-right:10px;" type="button" value="关闭" class="btn" onclick="closeX();"/>
						</td>
                    </tr>
                </table></div>
							</div>
                <input type="hidden" id="docguid" name="docguid" value="" />
				<!-- 用于记录附件个数 -->
	            <input type="hidden" id="num" value="0"/>
	            <input type="hidden" name="content" id="content">
        <a id="a" href="javascript:;" target="_self" onclick="window.location.reload();" style="display: none;"></a>
    </body>
        <script type="text/javascript">
	function upload(){
		//获取上传附件的个数
		var content = document.getElementById("content").value;
		var dates = "";
		if(content!=null && content.length>0){
			var date = new Date();
			var arr = content.split(',');
			var date1 = date.getTime();
			dates = date1+",";
			for(var i=1; i<arr.length-1; i++){
				date1 = parseInt(date1)+1000;
				dates += date1+",";
			}
		}
		//获取当前的时间
		$('#custom_file_upload').uploadifySettings('scriptData',{'title':$('#title').val(),'type':$('#type').val(),'content':content,'dates':dates});
		$('#custom_file_upload').uploadifyUpload();
		$("#content").val("");
	}
	</script>
</html>
