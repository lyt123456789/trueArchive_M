<!DOCTYPE html>
<%@ include file="/common/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0); %>
<%String sessionUserId = (String) request.getSession().getAttribute("userId");
String sessionUserName = (String) request.getSession().getAttribute("userName"); 
String commentObjId=request.getParameter("commentObjId");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <title>意见填写</title>   
		<script src="${ctx}/widgets/plugin/js/base/jquery.js" type="text/javascript"></script>
		<style type="text/css">
			a {
				text-decoration:none;
			}
			.qianpi_noton{
			}
			.qianpi_on{
				background-color: #316AC5;
				color: #FFF;
			}
			.myclass{
				font:12px 宋体;
			}
		</style>
		<base target="_self"> 
    </head>
    <body style="background-color:#CFE6F8;">
		<a id="submit_a" href="#"></a>
		
		<!-- 普通签批 -->
		<c:if test="${qianpiAble==false}">
	        <div align="center">
               	<c:if test="${isSatisfied != 'null' && not empty isSatisfied }">
		        	<fieldset> 
			       		<legend>评价</legend>
			            <table>
		                        <tr>
		                            <td colspan="2">
		                             	<input type="radio" name="isSatisfied" value="0" checked="checked" />满意&nbsp;&nbsp;&nbsp;&nbsp;
		                             	<input type="radio" name="isSatisfied" value="1" <c:if test="${isSatisfied == 1}">checked="checked"</c:if>/>不满意&nbsp;&nbsp;&nbsp;&nbsp;
		                             </td>
		                        </tr>
	                    </table>
	                </fieldset>
                </c:if>
                <fieldset>
        		<legend>意见</legend>
                    <table>
		                <tr>
		                    <td >
		                        <table style="background-color:#D8EBF9;border-collapse: collapse;">
		                            <tr>
		                                <td align="right"  valign="top" style="background-color:#D8EBF9;padding: 0px; ">
		                                    <select name="SelectComment" size="12" style="width: 160px;overflow:hidden; height:205;" onChange="javascript:selCommentFun(this)" style="background-color:#D8EBF9; ">
												<c:forEach items="${personalComments}" var="personalComment">
													<option value="${personalComment.content}">${personalComment.content}</option>
												</c:forEach>
		                                    </select>
		                                </td>
		                            </tr>
		                        </table>
		                    </td>
		                    <td >
		                        <table style="background-color:#D8EBF9;">
		                            <tr>
		                                <td align="center" width="200" valign="middle" >
		                                    <textarea id="commentContent" name="comment.content" cols="40" rows="12" style="background-color:#D8EBF9; "></textarea>  
		                                    <input type="hidden" id="tagId" name="comment.tagId" value="${tagId}">
		                                    <span id="comment_content" style="display:none">${comment.content}</span> 
		                                </td>
		                            </tr>
		                        </table>
		                    </td>
		                </tr>
		            </table>
	            </fieldset>
	        </div>
         <div align="center" style="margin-top:10px">
         	    <table id="att_table" width="510" style="background-color:#CFE6F8;border-collapse: collapse;">
         	    	<c:forEach items="${attList}" var="att">
         	    		<tr id='${att.id}'>
         	    			<td  align="left"><label class='myclass'>${att.fileName}</label></td>
         	    			<td  align="right">
         	    				<img id="del_att_img"  src="./widgets/component/taglib/comment/imgs/delete.gif" title="删除" onclick="deleteAtt('${att.id}');" />
         	    			</td>
         	    		</tr>
         	    	</c:forEach>
	            </table> 
         </div>
         <div align="center" style="margin-top:10px">
         	    <table id="processLink_table" width="510" style="background-color:#CFE6F8;border-collapse: collapse;">
	            </table> 
         </div>
        <div align="center" style="margin-top:10px">
            <table width="300">
                <tr align="center">
                	<td width="80">
                        <input name="cmdMaintain" type="button" value="维护常用语" onclick="javascript:maintainComment()">
                    </td>
					<td width="80">
                        <input name="cmdMaintain" type="button" value="保存为常用语" onclick="javascript:return saveAsPC()">
                    </td>
                    <!--
					<td width="80">
                        <input id="add_att" type="button" value="上传附件">
                        <input type="hidden" id="attids" value="">
                    </td>
                    -->
                    <td width="80">
                    	<c:if test="${deleteAbled == true && updateAbled==true&& not empty comment.id}">
                                <input name="cmdDelete" type="button" value="删 除 " onclick="javascript:deleteComment('${comment.id}')">
                    	</c:if>
                    </td>
                    <!--
                    <td width="80">
                    	<c:if test="${processLinkAble == true&&addAbled == true and updateAbled == true}">
                              <input id="openSelectProcess" type="button" value="插入办理情况 " >
                    	</c:if>
                    </td> 
                    -->
                    <td width="80">
                    	<c:if test="${addAbled == true and updateAbled == true}">
                                <input name="save" type="button" value="确定" onClick="javascript:saveComment()">
                    	</c:if>
                    </td>
                    <td width="80">
                        <input name="close" id="cancelbtn" type="button" value="取 消 " onclick="javascript:closeX();"></td>
                </tr>
            </table>
        </div>
        </c:if>
        
        <!-- 手写签批 -->
        <c:if test="${qianpiAble==true}">
        	<center style="margin-top: 20px;">
        		<c:if test="${deleteAbled == true && not empty comment.id}">
                        <input name="cmdDelete" type="button" value="删 除 " onclick="javascript:deleteComment('${comment.id}')">
        		</c:if>
	            &nbsp;&nbsp;
	            <input name="close" type="button" value="取 消 " onclick="javascript:window.close();">
              </center>
        </c:if>
        <script type="text/javascript">
        	//保存录入的意见
            function saveComment(){
                var instanceId = "${comment.instanceId}";
                var commentId = "${comment.id}";
                var stepId = '';
	            if (commentId == null || commentId == '') {
	                  stepId = "${currentStepId}";
	            }else {
                    stepId = "${currentStepId}";
               }
                var userId = "<%=sessionUserId%>";
                var userName = "<%=sessionUserName%>";
                var content = $('#commentContent').val(); 
                var isSatisfied;
                if('${isSatisfied}' != null){
                    var isSatisfieds = document.getElementsByName("isSatisfied");
                    for(var j = 0;j < isSatisfieds.length; j++){
						if(isSatisfieds[j].checked){
							isSatisfied = j;
						}
                    }
                } else{
                	isSatisfied = null;
                }
				var table = document.getElementById('processLink_table');
				//tr集合
				var tableChild = table.childNodes;
				if(tableChild.length>0){
					var tr = tableChild.item(0).childNodes;
					if(tr){
						for(var i=0,j=tr.length;i<j;i++){
							//td集合
							var td = tr.item(i).childNodes;
							content+="\r\n"+$(td.item(0).lastChild).html();
						}
					}
				}
              	var cabwrite='';
            	content=content.replace(/(^\s*)|(\s*$)/g,"");    
              	if(content==""){
					alert('没有需要保存的意见信息');
					document.getElementById('commentContent').focus();
					return false;
				}
              	var path="";
              	var attids=$("#attids").val();
                $.ajax({
                    url: 'comment_addOrModifyComment.do',
                    type: 'POST',
                    cache: false,
                    async: false,
                    data: {
                        'comment.instanceId': instanceId,
                        'comment.id': commentId,
                        'comment.stepId': stepId,
                        'comment.userId': userId,
                        'comment.userName': userName,
                        'comment.content': content,
                        'isSatisfied': isSatisfied,
                        'comment.tagId': $("#tagId").val(), //20101207 add
                        'comment.cabWrite':cabwrite, 			//20110901 add
                        //'comment.cabImgPath':path 					//20110902 add
                        'comment.approved':0, //2011 12 07 Wangxf add
                        'attids':attids //2012 01 05 Wangxf add
                    },
                    error: function(){
                        alert('AJAX调用错误');
                        window.returnValue = '3';
                    },
                    success: function(msg){
                        if (msg == '0') {
                            alert("系统出现异常，请重试");
                            window.returnValue = '0';
                        } else if (msg == '1') {
                            window.returnValue = 'add';
                            window.close();
                        } else if (msg == '2') {
                            alert("你没有权限修改他人签署的意见信息");
                            window.returnValue = '2';
                        } else {
                            alert("未知错误");
                            window.returnValue = '3';
                        }
                    }
                });
            }

            function deleteComment(commentId){
            	if(window.confirm("删除此意见？")){
	                $.ajax({
	                    url: 'comment_deleteComment.do',
	                    type: 'POST',
	                    cache: false,
	                    async: false,
	                    data: {
	                        'comment.id': commentId
	                    },
	                    error: function(){
	                        alert('AJAX调用错误');
	                        window.returnValue = '3';
	                    },
	                    success: function(msg){
	                        if (msg == '0') {
	                            alert("系统出现异常，请重试");
	                            window.returnValue = '0';
	                        } else if (msg == '1') {
	                            window.returnValue = 'delete';
	                            window.close();
	                            //window.returnValue = '1';
	                          	//调用父页面方法
	                            //window.opener.initCommentFun('<%=commentObjId%>');
	                        } else if (msg == '2') {
	                            alert("你没有权限删除他人签署的意见信息");
	                            window.returnValue = '2';
	                        } else {
	                            alert("未知错误");
	                            window.returnValue = '3';
	                        }
	                    }
	                });
                }
            }
            
            function selCommentFun(obj){
                var SelectComment = obj;
                var idx = SelectComment.selectedIndex;
                if (idx < 0) {
                    return;
                }
                var text = SelectComment.options(idx).value;
				//20101210 modified
//                document.all("commentContent").value = document.all("commentContent").value + text;
                document.all("commentContent").value = text;
                
				//20110901 modified
				//隐藏签字层
				//document.getElementById('tbl_qianpi').style.display='none';
				//显示文字层
				//document.getElementById('commentContent').style.display='block';
				//设置签字文本选项样式
				//document.getElementById('span_qianpi').className='qianpi_noton';
                 
            }

            function maintainComment(){
                var url = "comment_showPersonalComments.do?d="+Math.random();
				window.showModalDialog(url, window, "dialogWidth:600px; dialogHeight:400px; center:yes; help:no; status:no; resizable:yes;");
				ref();
            }
			
			function saveAsPC(){
				var content = $("#commentContent").val();
				if (content == ""){
					alert("意见不能为空！");
					$("#commentContent").focus();
					return false;
				}
				var postData = "[{'content':'" + encodeURIComponent(content) + "', 'sort_index':'1', 'cmnt_id':''}]";
				
				$.ajax({
					async: false,
					type: "POST",
					cache: false,
					url: "comment_savePersonalComment.do",
					data: eval(postData)[0],
					error: function(){
				   		alert('AJAX调用错误');
					},
					success: function(msg){
						alert("保存常用语成功");
						ref();
					}
				});
			}
			
			function ref(){
	            if (document.all) { //IE中利用超链接刷新网页对话框,FF不支持
	                document.getElementById('submit_a').href=window.location+'';
	                document.getElementById('submit_a').click();
	            }
	            else {//非IE(Opera除外,它不支持网页对话框)
	                window.location.reload();
	            }
	        };
        </script>
        <script type="text/javascript">
        
        	function deleteAtt(attid){
            	if(window.confirm("删除此附件？")){
		        	$.ajax({
		        		    url: '${ctx}/comment_toRemoveAtt.do',
		        		    type: 'POST',
		        		    cache:false,
		        		    async:false,
		        		    data: {'attid': attid},
		        		    error: function(){
		        		        alert('删除失败');
		        		    },
		        		    success: function(msg){
		        		    	$("#"+msg).remove();
		        		    }
		        	});	
            	}
        	}

        $("#add_att").bind("click",function(){
		        var swidth = 550;
        		var sheight =400;
		        var winoption = "dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:no;scroll:yes;resizable:no;center:yes";
				var ret=window.showModalDialog("${ctx}/comment_openUpload.do?commentId=${comment.id}&t="+ new Date().getTime(),window,winoption);
				if(ret!=undefined && ret!=""){
					var atts = ret.split(";");
					var info="";
					for(var i=0;i<atts.length;i++){
						attId = atts[i].split("|")[0];
						attName = atts[i].split("|")[1];
						if($("#attids").val()==""){
							$("#attids").val(attId);
						}else{
							$("#attids").val($("#attids").val()+","+attId);
						}
						info+="<tr id='"+attId+"'>";
						info+="<td  align=\"left\"><label class=\"myclass\">"+attName+"</label></td>";	
						info+="<td  align=\"right\">";	
						info+="<img id=\"del_att_img\"  src=\"./widgets/component/taglib/comment/imgs/delete.gif\" title=\"删除\" onclick=\"deleteAtt('"+attId+"');\"/>";
						info+="</td>";
						info+="</tr>";
					}
					$("#att_table").append(info);
		        	$(".atag").css("text-decoration","none");
				}
        });

        function subTitle(title){
			if(title.length>38){
				return title.substring(0,38)+"...";
			}
			return title;
        }

        function replaceAll(str,s1,s2) { 
            return str.replace(new RegExp(s1,"gm"),s2); 
        }

        function deleteProcess(tr_id){
			$("#"+tr_id).remove();
        }

        $("#openSelectProcess").bind("click",function(){
	        var swidth = 800;
    		var sheight =700;
	        var winoption = "dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:no;scroll:yes;resizable:yes;center:yes";
			var ret=window.showModalDialog("${ctx}/pm_toProcessList.do?forComment=true&t="+ new Date().getTime(),null,winoption);
			if(ret){
				//$("#commentContent").val($("#commentContent").val()+" "+obj.title);  
				var info="";
				info+="<tr id='"+ret.id+"'>";
				info+="<td  align=\"left\"  title=\""+ret.title+"\"><label class=\"myclass\">"+subTitle(ret.title)+"</label><span style='display:none'>"+ret.link+"</span></td>";	
				info+="<td  align=\"right\">";	
				info+="<img id=\"del_att_img\"  src=\"./widgets/component/taglib/comment/imgs/delete.gif\" title=\"删除\" onclick=\"deleteProcess('"+ret.id+"');\"/>";
				info+="</td>";
				info+="</tr>";
				$("#processLink_table").append(info);
			}
        });
        	
			function closeX(){
				var attids = $("#attids").val();
        		$.ajax({
        		    url: '${ctx}/comment_toRemoveSomeAtts.do',
        		    type: 'POST',
        		    cache:false,
        		    async:false,
        		    data: {'attids': attids},
        		    error: function(){
        		    	window.close();
        		    },
        		    success: function(msg){
        		    	window.close();
        		    }
        		});	
			}
			var addAbled ="${addAbled}";
			var updateAbled = "${updateAbled}";
			if(addAbled=="true"&&updateAbled=="true"){
				$("#add_att").attr("disabled","");
			}else{
				$("#add_att").attr("disabled","true");
			}
			var deleteAbled ="${deleteAbled}";
			var commentId ="${comment.id}";
			if(deleteAbled=="true"&&commentId!=""){
			}else{
				$("#del_att_img").remove();
			}
			var index=1;
			var id="processtrid_";
			$('#comment_content').find('A').each(function(e){
				var info="";
				info+="<tr id='"+id+index+"'>";
				info+="<td  align=\"left\"  title=\""+$(this).html()+"\"><label class=\"myclass\">"+subTitle($(this).html())+"</label><span style='display:none'><a href='"+$(this).attr('href')+"' target='"+$(this).attr('target')+"'>"+$(this).html()+"</a></span></td>";	
				info+="<td  align=\"right\">";	
				info+="<img id=\"del_att_img\"  src=\"./widgets/component/taglib/comment/imgs/delete.gif\" title=\"删除\" onclick=\"deleteProcess('"+id+index+"');\"/>";
				info+="</td>";
				info+="</tr>";
				$("#processLink_table").append(info);
				$(this).remove();
				index++;
			});
			$("#commentContent").val(replaceAll($('#comment_content').html(),'<BR>','\r\n'));
     </script>
    </body>
    
</html>
