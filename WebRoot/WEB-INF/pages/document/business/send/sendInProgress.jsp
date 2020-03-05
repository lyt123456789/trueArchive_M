<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date" %>
<%@ page import="cn.com.trueway.base.util.*" %>
<html>
    <head>
        <title>流程中发送</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <META HTTP-EQUIV="Expires" content="0">
        <link rel="stylesheet" href="${cdn_imgs}/ued.base.css?t=2011" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
		<script type="text/javascript" src="${cdn_js}/base/jquery.js"></script>
		<script language="javascript" type="text/javascript" src="${ctx }/widgets/component/My97DatePicker/WdatePicker.js" defer="defer"></script>
		<script type="text/javascript">
			//正文附件上传和删除后回调的方法
			function controlSaveToWhere(){
				var tagid = "${doc.docguid}"+"atts";
	 			var count = getAttCounts(tagid);
	 			var selectInfo = "";
	 	 		selectInfo = "<select id=\"save_to_where\">";
	 			if(count==0){
	 	 			selectInfo+="<option value=\"toAtt\">保存至正文</option>"; 
	 	 			selectInfo+="<option value=\"toAttext\">保存至附件</option>"; 
	 			}else{
	 	 			selectInfo+="<option value=\"toAttext\">上传至附件</option>"; 
	 			}
	 			selectInfo+="</select>";
	 			$("#show_to_where").html(selectInfo);
	 			loadCss();
			}
		</script>
		<script language="javascript" type="text/javascript" src="${ctx }/widgets/component/taglib/attachment/js/attachment.js"></script>
        <%
	        Date date = new Date();
	        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	        String today = sf.format(date);
        %>
    </head>
    <base target="_self">
    <body>
    	<div class="bread-box">
		<div class="bread-title">
			<span class="bread-icon"></span>
			<span class="bread-start">当前位置：</span><span class="bread">流程中发送</span>
			<span class="bread-right-border"></span>
		</div>
		</div> 
        	<!-- 隐藏字段 -->
            <table class="formTable">
                <tr>
                    <td align="right">
                        <label style="color:red">*</label>主送：
                    </td>
                    <td  colspan="3" align="left">
                        <input type="text" mice-input="input" id="xtoname" name="xtoName" size="60"  readonly="readonly" />
                        <input id="selectXTO" type="button" mice-btn="icon-search" value="选 择" onclick="XTO(990,550)">
                        <input name="doc.xtoFull" type="hidden" id="xto" value="${doc.xtoFull }" />
                    </td>
                </tr>
                <tr>
                    <td align="right">抄送：</td>
                    <td colspan="3" align="left">
                        <input type="text" mice-input="input" id="xccname" name="xccName" size="60" value="${doc.xccName}" readonly="readonly"/>
                        <input id="selectXCC" type="button" mice-btn="icon-search" value="选 择" onclick="XCC(990,550)">
                        <input name="doc.xccFull" type="hidden" id="xcc" value="${doc.xccFull }" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label style="color:red">*</label>标题：
                    </td>
                    <td colspan="3" align="left">
                        <input type="text" mice-input="input" name="title" id="title" size="60" maxlength="70" onblur="clean(this)"/>
                    </td>
                </tr>
                <tr>    
                    <td  align="right"> 发文单位：</td>
                    <td  align="left">
                        <input type="text" mice-input="input" name="fwjg" id="fwjg" size="20"  maxlength="50" onblur="clean(this)"/>
                    </td>
                    <td  align="right">&nbsp;签发人：</td>
                    <td align="left">
                        <input type="text" mice-input="input" name="qfr" id="qfr" size="10"  maxlength="20" onblur="clean(this)"/>
                    </td>
                </tr>
                <tr>
                    <td  align="right">
                  	 	<label style="color:red">*</label>印发单位：
                    </td>
                    	<td align="left">
                        <select mice-select="select" name="yfdw" id="yfdw">
                            <c:choose>
                                <c:when test="${empty webInfos}">
                                    <option>出现问题，请联系管理员</option>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="webInfo" items="${webInfos}">
                                        <option value="${webInfo.departmentName}">${webInfo.departmentName}</option>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </td>
                    <td  align="right">&nbsp;印发日期：</td>
                    <td align="left">
                        <input type="text" mice-input="input" name="yfrq" id="yfrq" size="10" value="<%=today%>" onClick="WdatePicker()">
                    </td>
                </tr>
                <tr>
                    <td  align="right">类型：</td>
                    <td  align="left">
                        <select name="doctype" mice-select="select" id="doctype" >
                            <option value="通知" <c:if test="${doc.doctype == '通知'}">selected</c:if>>通知</option>
                            <option value="命令" <c:if test="${doc.doctype == '命令'}">selected</c:if>>命令</option>
                            <option value="决定" <c:if test="${doc.doctype == '决定'}">selected</c:if>>决定</option>
                            <option value="通告" <c:if test="${doc.doctype == '通告'}">selected</c:if>>通告</option>
                            <option value="通报" <c:if test="${doc.doctype == '通报'}">selected</c:if>>通报</option>
                            <option value="议案" <c:if test="${doc.doctype == '议案'}">selected</c:if>>议案</option>
                            <option value="报告" <c:if test="${doc.doctype == '报告'}">selected</c:if>>报告</option>
                            <option value="请示" <c:if test="${doc.doctype == '请示'}">selected</c:if>>请示</option>
                            <option value="批复" <c:if test="${doc.doctype == '批复'}">selected</c:if>>批复</option>
                            <option value="意见" <c:if test="${doc.doctype == '意见'}">selected</c:if>>意见</option>
                            <option value="函" <c:if test="${doc.doctype == '函'}">selected</c:if>>函</option>
                            <option value="会议纪要" <c:if test="${doc.doctype == '会议纪要'}">selected</c:if>>会议纪要</option>
                            <option value="办文" <c:if test="${doc.doctype == '办文'}">selected</c:if>>办文</option>
                            <option value="简报" <c:if test="${doc.doctype == '简报'}">selected</c:if>>简报</option>
                        </select>
                    </td>
                     <td align="right">
                    	<label style="color:red">*</label>&nbsp;印发份数：
                    </td>
                    <td align="left">
                    	<input type="text"  mice-input="input" name="yffs" id="yffs" size="10" maxlength="20" value="5" onblur="check(this)"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">紧急程度：</td>
                     <td  align="left">
                        <select name="priority"  mice-select="select" id="priority">
                            <option value="3" <c:if test="${doc.priority == '3'}">selected</c:if>>平件</option>
                            <option value="2" <c:if test="${doc.priority == '2'}">selected</c:if>>急件</option>
                            <option value="1" <c:if test="${doc.priority == '1'}">selected</c:if>>紧急</option>
                            <option value="0" <c:if test="${doc.priority == '0'}">selected</c:if>>特急</option>
                        </select>
                    </td>
                     <td align="right">公开方式：</td>
                    <td align="left">
                    	 <select mice-select="select" name="gkfs" id="gkfs">
                     	 	 <option value="主动公开" >主动公开</option> 
                             <option value="申请公开" >申请公开</option>
                        </select>
                    </td>
                </tr> 
                <tr>
                    <td  align="right">发文机构：</td>
                    <td colspan="3" align="left" name="sender">
                        <select mice-select="select" name="doc.sender" id="sender">
                            <c:choose>
                                <c:when test="${empty webInfos}">
                                    <option>出现问题，请联系管理员</option>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="webInfo" items="${webInfos}">
                                        <option value="${webInfo.departmentId}">${webInfo.departmentName}</option>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </td>
                </tr>
                <tr>
                	<td align="right">
                		<label style="color:red">*</label>正文：
                	</td>
         			<td colspan="2" align="left" id="show_atts">
         			</td>
         			<td colspan="2" align="center">
         				<input id="cebid" name="doc.cebid" type="hidden" value="${doc.cebid}" />
         				<trueway:att id="${doc.docguid}atts" ismain="true" docguid="${doc.docguid}" showId="show_atts" uploadAble="true" deleteAble="true" previewAble="true" toStampAble="true" openBtnClass="icon-add" uploadCallback="controlSaveToWhere" deleteCallback="controlSaveToWhere"/>
         			</td>
    			</tr>
                <tr>
                    <td  align="right">附件：
				  	</td>
				  	<td colspan="2" align="left" id="show_attsext">
                    </td>
				  	<td colspan="2" align="center">
				  		 <trueway:att id="${doc.docguid}attsext" docguid="${doc.docguid}" showId="show_attsext" uploadAble="true" deleteAble="true" previewAble="true" openBtnClass="icon-add"/>
                    </td>
                </tr>
                <tr>
                    <td  align="right">可选附件：</td>
                    <td colspan="2">
                    	<c:forEach items="${allAttsext}" var="attsext" varStatus="status">
                    		${attsext.filename}&nbsp;<input name="attext_checkbox" mice-checkbox="checkbox" type="checkbox" value="${attsext.id}" />&nbsp;&nbsp;&nbsp;
                    		<c:if test="${status.count %3 eq 0}">
                    			<br/>
                    		</c:if>
                    	</c:forEach>
                    	<c:forEach items="${commenAttList}" var="comment" varStatus="status">
                    		${comment.fileName}&nbsp;<input name="comment_checkbox" mice-checkbox="checkbox" type="checkbox" value="${comment.commentId}" />&nbsp;&nbsp;&nbsp;
                    		<c:if test="${status.count %3 eq 0}">
                    			<br/>
                    		</c:if>
                    	</c:forEach>
                    </td>
                    <td colspan="2" align="center">
						<input type="button" id="upload_selected" mice-btn="icon-copy" value="批量上传">
                    </td>
                </tr>
                <tr>
	           		<c:if test="${docQjdVo == 'null'}">
	                    <td align="right" >可选办文单：</td>
	      		            <td colspan="2" >
	                    	<c:forEach items="${bwdList}" var="bwd" varStatus="status">
						         ${bwd.docBw.wh}&nbsp;<input id="${bwd.docBw.bwGuid}" name="bwd_radio" mice-radio="radio" type="radio" value="${bwd.docBw.wfInstanceUid}" />&nbsp;&nbsp;&nbsp;
				                <c:if test="${state.count %3==0}">
	                    			<br/>
	                    		</c:if> 
				            </c:forEach>
	                    </td>
               		</c:if>
              		<c:if test="${docQjdVo != 'null'}">
              			<td align="right" >可选请假单：</td>
       		            <td colspan="2" >
					         ${docQjdVo.docQj.wh}&nbsp;<input id="${docQjdVo.docQj.docDuid}" name="qjd_radio" mice-radio="radio" type="radio" value="${docQjdVo.docQj.instanceId}" />&nbsp;&nbsp;&nbsp;
               			</td>
               		</c:if>
                    <c:if test="${docQjdVo == 'null'}">
	                     <td colspan="2" align="center">
	                     	<div id="show_to_where"></div>
							<input mice-btn="icon-apply" id="create_bwd" type="button" value="生成办文单"/>
	                     </td>
                    </c:if>
                    <c:if test="${docQjdVo != 'null'}">
	                     <td colspan="2" align="center">
	                     	<div id="show_to_where"></div>
							<input mice-btn="icon-apply" id="create_qjd" type="button" value="生成请假单"/>
	                     </td>
                    </c:if>
                </tr>
               	<tr>
               		<td  colspan="4" align="center">
               			<input type="button" id="to_send" mice-btn="icon-save-back" value="发送"/>
               		</td>
               	</tr>
         </table>
         <input type="hidden" id="keyword" name="keyword" value="空"/>
	 <script src="${cdn_js}/sea.js"></script>
	 <script type="text/javascript">
	</script>
	<!-- 发送页面相关JS -->
	<script type="text/javascript">
	     var mainSender={},subSender={};
	     var mainNum=5,subNum=5;
	     //主送选择	
    	 function XTO(fWidth, fHeight){
	    		var obj ={};
	    		obj.dep=mainSender;
	    		obj.num=mainNum; 
	    		var retVal = window.showModalDialog('${ctx}/selectTree_showDepartment.do?t='+new Date().getTime(), obj, 
	    			'dialogWidth: '+fWidth+'px;dialogHeight: '+fHeight+'px; status: no; scrollbars: no; Resizable: no; help: no;');
				if (retVal == null)
	    				return;
    			mainSender=retVal.src; //Object
    			mainNum=retVal.num; //String
    			$("#xto").val(_getValue(retVal.src,['name','id','nums','gzname'],'|').join(';'));
    			$("#xtoname").val(_getValue(retVal.src,['name'],'|').join(';'));
	    	}

    	//抄送选择
    	function XCC(fWidth, fHeight){
    		var obj ={};
    		obj.dep=subSender;
    		obj.num=subNum;
    		var retVal = window.showModalDialog('${ctx}/selectTree_showDepartment.do?t='+new Date().getTime(), obj, 
    			'dialogWidth: '+fWidth+'px;dialogHeight: '+fHeight+'px; status: no; scrollbars: no; Resizable: no; help: no;');
    			if (retVal == null)
    				return;
    			subSender=retVal.src; //Object
    			subNum=retVal.num; //String
    			$("#xcc").val(_getValue(retVal.src,['name','id','nums','gzname'],'|').join(';'));
    			$("#xccname").val(_getValue(retVal.src,['name'],'|').join(';'));
    	}
	
    	//获得信息
    	//demo _getValue(obj,['id','name','parentid'],'|')
    	function _getValue(obj,fields,splitstr){
    		var result=[],str='';
    		for(n in obj){
    			var o=obj[n];
    			var children=o.children;
    			if(children==null){
    				str="";
    				var i=0,l=fields.length;
    				for(i;i<l;i++){
    					if(str!=''&&l>1){
    						str+=splitstr;
    					}
    					str+=o[fields[i]];
    				}
    				result.push(str);
    			}
    		}
	    	return result; //Array
    	}
    	
    	function splitstr(str,scale){
			var _str=str,rhtml='<div class="fl cbo">';
			var scale=(!!scale)?scale:49;
			if(_str.indexOf(',')>-1){
				_str=_str.split(',');
				for(var i=0,l=_str.length;i<l;i++){
					rhtml+='<div style="width:'+scale+'%; float:left; line-height:20px;">'+_str[i]+'</div>';
				}
			}else{
				rhtml+=_str;
			}
			rhtml+='</div>';
			document.write(rhtml);
		}

		// 验证印发份数必须为数字
		function check(obj){
		    var re = /[^\d]/g;
		    if(re.test(obj.value)){
		    	obj.value = '';
		    	alert("不能为非数字");
		    }
		}
		
		//打印份数入库
		function daoruPrintNum(){
			//获取CEBID
			var strCebID = document.getElementById("cebid").value;
			if(strCebID != ''){//份数入库和保持CEBID到box库
				var keyAble = GetCAKeyInfnFun();
				if(keyAble!=true){  
					alert("读取CA证书失败，请检查CA是否正确插入");
					return;
				}
				
				// 份数、接收单位、接收单位个数入库
				var r="";
				if(document.getElementById("xcc").value!=""){
					r = document.getElementById("xto").value+";"+document.getElementById("xcc").value;
				}else{
					r = document.getElementById("xto").value
				}
				var arr = r.split(";");
		        var receiver = "";//收文单位字符串.接收单位名称，多个单位间用;分开
				var printNum = "";//打印份数字符串.接收单位对应份数，多个份数间用;分开
		        var counts = arr.length;//接收单位个数
				for(var i=0;i<arr.length;i++){
				  var arrary = arr[i].split("|");
				  printNum += arrary[2] + ";";
				  receiver += arrary[3] + ";"; 
				} 
				receiver=receiver.substring(0,receiver.length-1);
				printNum=printNum.substring(0,printNum.length-1);
				var StampServer_PrintLic2DB = "<%=SystemParamConfigUtil.getParamValueByParam("StampServer_PrintLic2DB")%>";
				var ss = fenshuruku(strCebID,receiver,printNum,counts,StampServer_PrintLic2DB);
				if(ss == false){
					alert("份数入库失败");
					return;
				}
			}
		}

		//清除空格
		function clean(obj){
			obj.value=obj.value.replace(/(^\s*)|(\s*$)/g, "");
		}

		//删除左右两端的空格
		function trim(text){ 
			return (text || "").replace( /^\s+|\s+$/g, "" );
		}
    </script>
	<script language="javascript" type="text/javascript">
		//批量上传
		$("#upload_selected").bind("click",function(){
			var attextIds = "";
			$("input[name='attext_checkbox']").each(function(){
				if(this.checked==true){
					attextIds=attextIds+this.value+",";
				}
			});
			var commentIds = "";
			$("input[name='comment_checkbox']").each(function(){
				if(this.checked==true){
					commentIds=commentIds+this.value+",";
				}
			});
			if (attextIds==""&&commentIds==""){
				alert("请选择要上传的附件");
				return;
			}else{
				attextIds = attextIds.substring(0,attextIds.length-1); 
				commentIds = commentIds.substring(0,commentIds.length-1); 
				//alert(attextIds);
				$.ajax({
				    url: 'send_batchUpload.do',
				    type: 'POST',
				    cache:false,
				    async:false,
				    data:{'attextIds':attextIds,'commentIds':commentIds,"docguid":"${doc.docguid}"},
				    error: function(){
				        alert('批量上传失败');
				    },
				    success: function(msg){
				    	var tagid="${doc.docguid}"+"attsext";
				    	refreshAtt(tagid);
				    }
				});
			}
		});

		//生成办文单
		$("#create_bwd").bind("click",function(){
			var isSelect = false;
			$("input[name='bwd_radio']").each(function(){
				if(this.checked==true){
					isSelect = true;
					//需要的相关参数
					var instanceId = this.value;
					var formid = "${formid}";
					var saveToWhere = $("#save_to_where").val();
					var saveAble = true;
					var printType = "${printType}";
			        var winoption ="dialogHeight:"+(screen.height-70)+"px;dialogWidth:"+ (screen.width-10) +"px;status:no;scroll:yes;resizable:yes;center:yes";
					var url ='${ctx}/tem_toPrintModel.do?t='+new Date().getTime()+'&instanceId='+instanceId+'&formid='+formid+"&recDocguid=${recDocguid}&sendDocguid=${doc.docguid}&saveAble="+saveAble+"&saveToWhere="+saveToWhere+"&printType="+printType;
				    var ret=window.showModalDialog(url,window,winoption);
					var tagid="${doc.docguid}";
					if(ret!=null&&ret=="toAtt"){
						tagid+="atts";
						refreshAtt(tagid);
					}else if(ret!=null&&ret=="toAttext"){
						tagid+="attsext";
						refreshAtt(tagid);
					}
					return false;
				}
			});
			if(!isSelect){
				alert("请选择要生成的办文单");
				return;
			}
		});
		
		//生成请假单
		$("#create_qjd").bind("click",function(){
			var isSelect = false;
			$("input[name='qjd_radio']").each(function(){
				if(this.checked==true){
					isSelect = true;
					//需要的相关参数
					var instanceId = this.value;
					var formid = "${formidQj}";
					var saveToWhere = $("#save_to_where").val();
					var saveAble = true;
					var printType = "${printType}";
			        var winoption ="dialogHeight:"+(screen.height-70)+"px;dialogWidth:"+ (screen.width-10) +"px;status:no;scroll:yes;resizable:yes;center:yes";
					var url ='${ctx}/tem_toPrintModel.do?t='+new Date().getTime()+'&instanceId='+instanceId+'&formid='+formid+"&recDocguid=${recDocguid}&sendDocguid=${doc.docguid}&saveAble="+saveAble+"&saveToWhere="+saveToWhere+"&printType=qj";
				    var ret=window.showModalDialog(url,window,winoption);
					var tagid="${doc.docguid}";
					if(ret!=null&&ret=="toAtt"){
						tagid+="atts";
						refreshAtt(tagid);
					}else if(ret!=null&&ret=="toAttext"){
						tagid+="attsext";
						refreshAtt(tagid);
					}
					return false;
				}
			});
			if(!isSelect){
				alert("请选择要生成的请假单");
				return;
			}
		});

		//发送
		$("#to_send").bind("click",function(){
			var xtoname = document.getElementById("xtoname").value;
			if(xtoname==""||xtoname==null||trim(xtoname).length==0){
				alert("主送不能为空");
				return false;
			}
			var title = document.getElementById("title").value;
			if(title==""||title==null||trim(title).length==0){
				alert("标题不能为空");
				return false;
			}
			var yfdw = document.getElementById("yfdw").value;
			if(yfdw==""||yfdw==null||trim(yfdw).length==0){
				alert("印发单位不能为空");
				return false;
			}
			
			var yffs = document.getElementById("yffs").value;
			if(yffs==""||yffs==null||trim(yffs).length==0){
				alert("印发份数不能为空");
				return false;
			}
			
			if(isNaN(yffs.replace(/(^\s*)|(\s*$)/g, ""))||yffs<0){
				alert("印发份数为数字类型，请输入正确格式");
				return false;
			}
			var tagid = "${doc.docguid}"+"atts";
 			var count = getAttCounts(tagid);
			if (count==0) {
	            alert("请上传正文");
	            return false;
	        }
			var xtoFull=document.getElementById('xto').value;
			var xccName=document.getElementById('xccname').value;
			var xccFull=document.getElementById('xcc').value;
			var fwjg=document.getElementById('fwjg').value;
			yfdw=document.getElementById('yfdw').value;
			var qfr=document.getElementById('qfr').value;
			var yfrq=document.getElementById('yfrq').value;
			var doctype=document.getElementById('doctype').value;
			var priority=document.getElementById('priority').value;
			var gkfs=document.getElementById('gkfs').value;
			var sender=document.getElementById('sender').value; 
			var cebid=document.getElementById('cebid').value;
			var keyword=document.getElementById('keyword').value;
			
			//打印份数入库
    		daoruPrintNum();
			

			$.ajax({
			    url: '${ctx}/send_sendInprogress.do',
			    type: 'POST',
			    cache:false,
			    async:false,
			    data:{'doc.instanceId':'${doc.instanceId}','doc.docguid':'${doc.docguid}','doc.xtoName':xtoname,'doc.xtoFull':xtoFull,'doc.xccName':xccName,'doc.xccFull':xccFull,'doc.title':title,'doc.keyword':keyword,'doc.fwjg':fwjg,'doc.yfdw':yfdw,'doc.qfr':qfr,'doc.yfrq':yfrq,'doc.yffs':yffs,'doc.doctype':doctype,'doc.priority':priority,'doc.gkfs':gkfs,'doc.sender':sender,'doc.cebid':cebid},
			    error: function(){
			        alert('发送失败，请联系管理员！');
			    },
			    success: function(msg){
			    	if(msg=='success'){
						alert("发送成功");
						window.returnValue="sendSuccess";
						window.close();
			    	}
			    }
			 });
		});
	</script>
  	<script type="text/javascript">
  		function  loadCss(){
	   		seajs.use('lib/form',function(){
				$('input[mice-btn]').cssBtn();
				$('input[mice-input]').cssInput();
				$('select[mice-select]').cssSelect();
				$('input[mice-checkbox]').cssCheckbox();
				$('input[mice-radio]').cssRadio();
		    });
  		}
  		loadCss();
  		controlSaveToWhere(); 
  		//动态导入Js文件
   	</script>
    </body>
</html>
