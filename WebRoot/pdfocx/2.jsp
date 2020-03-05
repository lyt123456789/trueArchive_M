<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<html>
<head>
<meta charset="utf-8">
<title>Demo</title>
<script type="text/javascript">
var cpage=1;
var page = 1;
var browser_version = getBrowser();
//获取浏览器的版本号
function getBrowser() {
    var ua = window.navigator.userAgent;
    var isIE = !!window.ActiveXObject || "ActiveXObject" in window;
    var isChrome = ua.indexOf("Chrome") && window.chrome;
    if (isIE) {
        return "IE";
    } else if (isChrome) {
        return "Chrome";
    } else {
        return "Chrome";
    }
}
</script>
<script type="text/javascript">
	var lens=0;
	function setLens(){
		lens=0;
	}
   
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
       
    var mainSender={},subSender={};
    var mainNum=5,subNum=5;
    
    //主送选择	
    function selectSupman(fWidth, fHeight){
    	var obj ={};
    	obj.dep=mainSender;
    	obj.num=mainNum; 
    	var retVal = window.showModalDialog('${ctx}/selectTree_showDepartment.do?t='+new Date().getTime(), obj, 
    		'dialogWidth: '+fWidth+'px;dialogHeight: '+fHeight+'px; status: no; scrollbars: no; Resizable: no; help: no;');
		if (retVal == null)
    		return;
    		mainSender=retVal.src; //Object
    		mainNum=retVal.num; //String
    	$("#jsonobjform_fsjg").val(_getValue(retVal.src,['name'],'|').join(';'));
    }

    //抄送选择
    function selectSubman(fWidth, fHeight){
    	var obj ={};
    	obj.dep=subSender;
    	obj.num=subNum;
    	var retVal = window.showModalDialog('${ctx}/selectTree_showDepartment.do?t='+new Date().getTime(), obj, 
    			'dialogWidth: '+fWidth+'px;dialogHeight: '+fHeight+'px; status: no; scrollbars: no; Resizable: no; help: no;');
    	if (retVal == null)
    		return;
    		subSender=retVal.src; //Object
    		subNum=retVal.num; //String
    	$("#jsonobjform_csjg").val(_getValue(retVal.src,['name'],'|').join(';'));
    }
    
</script>
</head>
<body onload="initObjBox();" style="overflow-y:hidden">
<div id="mianDiv" style="background-color:#989898;"></div>
<div align="center" style="background-color:#989898;">
<div style="margin:0 auto;" >
  <div  style="display: none;" id="jzk2">
	 <div id='fullbgDY'></div><div id='dialogDY' style='text-align:center'><img src='${ctx}/dwz/style/images/quanq.gif'>正在准备打印，请稍候...</div></div>
    <div  style="display: none;" id="jzk1">
	 <div id='fullbg'></div><div id='dialog' style='text-align:center'><img src='${ctx}/dwz/style/images/quanq.gif'>正在发送，请稍候...</div></div>
    <div style="display: inline;background-color:#989898;z-index:8;padding-left: 16px;" id="OBJECTbox">
    	<object id="OCXpdfobj"  TYPE="application/x-itst-activex" style="width:1024px;height:800px;margin-left:-18px;" clsid="{ECCC5C8C-8DA0-4FAC-935A-CD5229A14BCC}"></object>
    </div>
</div>
</div>
<INPUT type="hidden" id="instanceId" name="instanceId" value='<%=request.getParameter("instanceId")%>'> 
<INPUT type="hidden" id="formId" name="formId" value='<%=request.getParameter("formId")%>'> 
<INPUT type="hidden" id="workFlowId" name="workFlowId" value='<%=request.getParameter("workFlowId")%>'>   
<INPUT type="hidden" id="processId" name="processId" value='<%=request.getParameter("processId")%>'>  
<script src="jquery.min.1.7.2.js"></script>
<script src="${ctx}/pdfocx/json2.js" type="text/javascript"></script>
<script type="text/javascript">
	function initObjBox(){
		var bW=$(window).width();  
		if(bW<=1024){
			document.getElementById("OCXpdfobj").width=bW;
		}
		
		// init obj height
		var height = $(window).height() - 50;
		document.getElementById("OCXpdfobj").style.height = height + 'px';
	}
	
	$(window).bind('resize',function(){
		initObjBox()
	})
</script>
<script language="javascript">
var isFlexibleForm='<%=request.getParameter("isFlexibleForm")%>';
var workflowId=document.getElementById('workFlowId').value;
var isWriteNewValue='<%=request.getParameter("isWriteNewValue")%>';
var newInstanceId='<%=request.getParameter("newInstanceId")%>';
var newProcessId='<%=request.getParameter("newProcessId")%>';
var isCanLookAtt='<%=request.getParameter("isCanLookAtt")%>';
var userId = '<%=request.getParameter("userId")%>',
	loginname = '<%=request.getParameter("loginname")%>',
    nodeId='<%=request.getParameter("nodeId")%>',
    instanceId='<%=request.getParameter("instanceId")%>',
    processId='<%=request.getParameter("processId")%>',
    formId='<%=request.getParameter("formId")%>',
    oldFormId='<%=request.getParameter("oldFormId")%>',
    userName='<%=request.getSession().getAttribute("userName")%>',
    deptName='<%=request.getSession().getAttribute("deptName")%>',
    webId='<%=request.getParameter("webId")%>',
    personalComments='<%=request.getSession().getAttribute("personalComments")%>',
    imageCount = window.parent.document.getElementById('imageCount').value,
    pdfPath=window.parent.document.getElementById('allPdfPath').value,
    toUserName=window.parent.document.getElementById('toUserName').value,
    title=window.parent.document.getElementById('title').value,
    status=window.parent.document.getElementById('status').value;
    //pdfPath = pdfPath.split(",")[0];
    workflowId=(workflowId=='null')?'':workflowId;
var param = "";
	if(window.parent.document.getElementById('params')){
		param = window.parent.document.getElementById('params').value;
		param = encodeURI(param);
	}
var dicValue ='<%=request.getParameter("dicValue")%>';
var modId ='<%=request.getParameter("modId")%>';
var dicId ='<%=request.getParameter("dicId")%>';
var matchId ='<%=request.getParameter("matchId")%>';
var no = '<%=request.getParameter("no")%>';
var isContinue = '<%=request.getParameter("isContinue")%>';
var origProcId = '<%=request.getParameter("origProcId")%>';
var jsonurl='${ctx}/table_getFormValueAndLocationOfWeb.do?no='+no+'&params='+param;
var flexibleurl = '${ctx}/table_getFlexibleFormValue.do';
var methodurl = "${curl}/pdfocx/method.html";
var sealUrl='<%=request.getParameter("sealurl")%>';
var usbkey='<%=request.getParameter("usbkey")%>';
var pdfurl=pdfPath;
var isCheck ='<%=request.getParameter("isCheck")%>';
/* if(browser_version=='IE'){
	var temp = '<OBJECT id="OCXpdfobj" codebase="" style="width:1024px;height:800px;margin-left:-18px;" classid="clsid:ECCC5C8C-8DA0-4FAC-935A-CD5229A14BCC" ></OBJECT>';
	document.getElementById("OBJECTbox").innerHTML = temp;
}else if(browser_version=='Chrome'){
	var temp = '<object id="OCXpdfobj"  TYPE="application/x-itst-activex" style="width:1024px;height:800px;margin-left:-18px;" clsid="{ECCC5C8C-8DA0-4FAC-935A-CD5229A14BCC}"></object>';
	document.getElementById("OBJECTbox").innerHTML = temp;
} */
/**
* 筛选后的form json
**/     
var oldformJSON;
/**
* 完整的true json
**/
var StampType = 0;
if(usbkey=='yiyuan'){
	StampType = 0;
}else{
	StampType = 1;
}

//1024 1236 1448
function changeOCXpdfobjWd(status){
	if(status == 1){
		document.getElementById("OCXpdfobj").style.width='1024px';
	}else if(status == 2){
		document.getElementById("OCXpdfobj").style.width='1183px';
	}else if(status == 3){
		document.getElementById("OCXpdfobj").style.width='1342px';
	}
}

var truepaper = {
		  "ServerUrl":sealUrl,
		  "StampType":StampType,
		  "docId":instanceId,
		  "pages":[
		         	 {
		        	  "company" : "trueway",
		              "basicOS" : "ios",
		          	  "version" : 2,
		           	  "width" : 1024,
		              "height" : 1448,
		              "processes":[]}
		           ],
		  "resources":[]
};

var trueJSON={
  "company" : "trueway",
  "basicOS" : "ios",
  "version" : 2,
  "width" : 1024,
  "height" : 1448,
  "processes":[]
};

/**
* 当前节点用户信息
**/
var sendJSON={
      "userid":userId, 
      "workflowId":workflowId,
      "nodeId":nodeId,
      "instanceId":instanceId,
      "processesid":processId,
      "formId":formId,
      "sendtime":null,
      "datas":[]
};


if(isWriteNewValue == 'false'){
	if(newInstanceId!="null"){
		instanceId = newInstanceId;
	}
}
var isOver ='<%=request.getParameter("isOver")%>';
var isFlexibleForm = parent.document.getElementById("isFlexibleForm").value;
if(isFlexibleForm!=null && isFlexibleForm=='1'){			//弹性表单值
		var  params ={};
		params['userId'] = userId;
		params['workflowId'] = workflowId;
		params['nodeId'] = nodeId;
		params['instanceId'] = instanceId;
		params['processId'] = processId;
		params['formId'] = formId;
		params['toUserName'] = toUserName;
		params['title'] = title;
		params['status'] = status;
		if(isOver == '0'){
		}else{
			params['isOver'] = isOver;
		}
		$.ajax({
		    url: flexibleurl,
		    type: 'POST',
		    dataType: 'json',
		 	data : params,
		    timeout: 30000,
		    error: function(o,e){
		    },
		    success: function(data){
		        oldformJSON=data;
		        setFlexibleFormData("");
		    }
		});
	
}else{							//原先的操作方式
	if(isOver == '0'){
		var  params ={};
		params['userId'] = userId;
		params['workflowId'] = workflowId;
		params['nodeId'] = nodeId;
		params['instanceId'] = instanceId;
		params['processId'] = processId;
		params['formId'] = formId;
		params['oldFormId'] = oldFormId;
		params['dicValue'] = dicValue;
		params['modId'] = modId;
		params['dicId'] = dicId;
		params['matchId'] = matchId;
		params['isContinue'] = isContinue;
		params['origProcId'] = origProcId;
		params['toUserName'] = toUserName;
		params['title'] = title;
		params['status'] = status;
		$.ajax({
		    url: jsonurl,
		    type: 'POST',
		    dataType: 'json',
		 	data : params,
		    timeout: 30000,
		    error: function(o,e){
		    },
		    success: function(data){
		        oldformJSON=data;
		        setActiveData(oldformJSON);
		    }
		});
	}else{
		 setActiveData();
	}
}

//设置弹性表单展示参数值：引入SetTrueformData 方法：参数值：  true内容、 data(具体数据权限)、 pdf附件地址路径
function setFlexibleFormData(trueForm){
	try {
		OCXpdfobj.UpDataOA("1,2,2,7","http://10.196.118.21:5513/trueOA/showData/TrueToolsSoui.exe");
	} catch (e) {
	}
	
	try {
		OCXpdfobj.SetCloseTitle("关闭窗口");
	} catch (e) {
		// TODO: handle exception
	}
		
		var input = parent.document.getElementById('commentJson').value;
		//var formPageJson = parent.document.getElementById('formPageJson').value;
		var formPageJson = '';
	
		$.ajax({
			url : '${ctx}/table_getFormPageJson.do?a=Math.random()',
			type : 'POST',
			cache : false,
			async : false,
			data : {
				'formId' : formId,
				'instanceId' : instanceId,
				'workflowId' : workflowId
			},
			error : function(o, e) {
				alert('加载form元素错误, error:' + e + '。');
			},
			success : function(data) {
				formPageJson = data;
			}
		});
		OCXpdfobj.SetWebType(1);
		OCXpdfobj.SetDocId(instanceId);
		OCXpdfobj.SetSealUrl(sealUrl);
		OCXpdfobj.SetMethodUrl(methodurl);
		OCXpdfobj.SetPageAnimation(0);
		try {
			var date = new Date();
			var month= date.getMonth()+1+"";
			var day = date.getDate();
			if(month.length==1){
				month = "0"+month+"";
			}
			if(day.length==1){
				day = "0"+day;
			}
			var time = month+'-'+day;
			OCXpdfobj.SetWarterText(loginname+" "+time, 1, 50);
		} catch (e) {
		}
		try {
			OCXpdfobj.SetFormRegular(1);
		} catch (e) {
		}
		
		try {
			//设置字号
			//OCXpdfobj.SetSignType(3);
			OCXpdfobj.SetFirstIndent(1);
			var fontSize = parent.document.getElementById('fontSize');
			var font = parent.document.getElementById('font');
			if(fontSize){
				var fontVal = '宋体';
				if(font){
					fontVal = font.value;
				}
				if(fontSize && fontSize.value==''){
					fontSize.value='16';
				}
				OCXpdfobj.SetFontInfo(fontVal, fontSize.value);
			}
			//设置行间距
			var verticalSpacing = parent.document.getElementById('verticalSpacing');
			if(verticalSpacing && fontSize){
				OCXpdfobj.SetLineDistance(verticalSpacing.value * fontSize.value);
			}
			
			try {
				OCXpdfobj.SetFirstIndent(1);
			} catch (e) {
			}
			OCXpdfobj.SetSignType(2);
			//设置时间格式
			/* var dateFormat = parent.document.getElementById('dateFormat');
			if(dateFormat){
				if(dateFormat.value == '1'){
					OCXpdfobj.SetSignType(2);
				}else if(dateFormat.value == '2'){
					OCXpdfobj.SetSignType(1);
				}else{
					OCXpdfobj.SetSignType(0);
				}
				
			} */
		} catch (e) {
		}
		
		//设置人员意见的自动排序
		try{
		    var  employeeSort = parent.document.getElementById('employeeSort').value;
		 	if(employeeSort!=null && employeeSort!=''){
		 		  OCXpdfobj.SetUserIndex(employeeSort);
		 	}
		 	
		 	$.ajax({
		 		url : '${ctx}/table_processSort.do?a=Math.random()',
				type : 'POST',
				cache : false,
				async : false,
				data : {
					'instanceId' : instanceId
				},
				error : function(e) {
					alert('加载人员排序异常');
				},
				success : function(data) {
					var processSort = data;
				 	if(processSort!=null && processSort!=''){
				 		OCXpdfobj.SetUserSignSort(processSort);
				 	}
				}
		 	});
		 	/*var processSort = parent.document.getElementById('processSort').value;
		 	if(processSort!=null && processSort!=''){
		 		  OCXpdfobj.SetUserSignSort(processSort);
		 	}*/
		 	
		}catch(e){
			
		}
		try {
			OCXpdfobj.SetEditBack(1,255,255,255);
		} catch (e) {
		}
		if (usbkey == 'yiyuan') {
			OCXpdfobj.SetStampType(0);
		} else {
			OCXpdfobj.SetStampType(1);
		}
		setTimeout(function() {
			OCXpdfobj.SetJsonData(input);
			var comments = personalComments.split(",");
			var coms = '';
			for ( var i = 0; i < comments.length; i++) {
				if (comments[i] == 'null') {
					coms += '{"text":""}' + ',';
				} else {
					coms += '{"text":"' + comments[i] + '"}' + ',';
				}
			}
			if (coms != '' && coms.length > 0) {
				coms = coms.substring(0, coms.length - 1);
			}
			var sPopUpData = '{"datas":[' + coms + '],"trueformdatas":[' + coms+ ']}';
			OCXpdfobj.SetPopUpData(sPopUpData);
			var sUserData = '{"userId":"' + userId + '","username":"'
					+ userName + '","workflowId":"' + workflowId
					+ '","nodeId":"' + nodeId + '","instanceId":"' + instanceId
					+ '","processId":"' + processId + '","formId":"' + formId
					+ '","sendtime":"2013-9-16 16:11:29"}';
			OCXpdfobj.SetUserInfo(sUserData);
			if(null != trueForm && '' != trueForm){
				OCXpdfobj.SetTrueformSplitData(formPageJson, JSON2.stringify(trueForm), pdfurl);
			}else{
				OCXpdfobj.SetTrueformSplitData(formPageJson, "", pdfurl);
			}
		}, 10);
	}

	function ymChange(ym) {
		OCXpdfobj.GoToPage(ym);
	}

	function onSeal() {
		OCXpdfobj.OnSeal('${curl}/attachment_uploadFile4Widget.do');
	}

	function setPrint(str) {
		return OCXpdfobj.PostPrintInfo(str);
	}

	function upFileToServer(){
		OCXpdfobj.UpFileToServer('${curl}/attachment_uploadFile4Widget.do');
	}
	
	/**
	 * 获取用户输入的信息
	 **/
	function getActiveData() {
		var d = new Date(), YMDHMS = d.getFullYear() + "-" + (d.getMonth() + 1)
				+ "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes()
				+ ":" + d.getSeconds();
		var trueJSON = OCXpdfobj.GetData();
		return trueJSON;
	}
	
	//设置页面大小
	function setPageSize(height, width){
		OCXpdfobj.RefreshView();
		document.getElementById("OCXpdfobj").style.width = width + 'px';
		document.getElementById("OCXpdfobj").style.height = height + 'px';
	}

	/**
	 * 输入true文件信息
	 **/
	function setActiveData(trueForm) {
		var input = parent.document.getElementById('commentJson').value;
		var version = getVersionByBbh();
		if (!version) {
			if (isCheck == '1') {
				return;
			}
			if (confirm("未安装控件，是否下载更新?(下载后关闭浏览器点击安装)")) {
				window.location.href = "${ctx}/table_download.do";
				return;
			} else {
				window.close();
				history.go(-1);
				return;
			}
		}

		$.ajax({
			url : '${ctx}/table_getVersion.do',
			type : 'POST',
			async : false,
			data : {
				'version' : version
			},
			error : function(o, e) {
				alert('加载form元素错误, error:' + e + '。');
			},
			success : function(data) {
				if (data == 'yes') {
					if (isCheck == '1') {
						return;
					}
					if (confirm("控件已过期，是否下载更新?(下载后关闭浏览器点击安装)")) {
						window.location.href = "${ctx}/table_download.do";
						return;
					} else {
						window.close();
						history.go(-1);
						return;
					}
				}
				OCXpdfobj.SetWebType(0);
				OCXpdfobj.SetDocId(instanceId);
				OCXpdfobj.SetSealUrl(sealUrl);
				OCXpdfobj.SetMethodUrl(methodurl);
				OCXpdfobj.SetPageAnimation(0);
				try {
					OCXpdfobj.SetWarterText(loginname, 1, 50);
				} catch (e) {
				}
				try {
					OCXpdfobj.SetFormRegular(1);
				} catch (e) {
				}
				
				try{
					//设置字号
					var fontSize = parent.document.getElementById('fontSize');
					if(fontSize){
						OCXpdfobj.SetFontInfo("宋体", fontSize.value);
					}
					//设置行间距
					var verticalSpacing = parent.document.getElementById('verticalSpacing');
					if(verticalSpacing && fontSize){
						OCXpdfobj.SetLineDistance(verticalSpacing.value * fontSize.value);
					}
					//设置时间格式
					var dateFormat = parent.document.getElementById('dateFormat');
					if(dateFormat){
						if(dateFormat.value == '1'){
							OCXpdfobj.SetSignType(2);
						}else if(dateFormat.value == '2'){
							OCXpdfobj.SetSignType(1);
						}else{
							OCXpdfobj.SetSignType(0);
						}
						
					}
				}catch (e) {
				}
				
				//设置人员意见的自动排序
				try{
				    var  employeeSort = parent.document.getElementById('employeeSort').value;
				 	if(employeeSort!=null && employeeSort!=''){
				 		  OCXpdfobj.SetUserIndex(employeeSort);
				 	}
				}catch(e){
				}
				
				if (usbkey == 'yiyuan') {
					OCXpdfobj.SetStampType(0);
				} else {
					OCXpdfobj.SetStampType(1);
				}
				setTimeout(function() {
					OCXpdfobj.SetPDFUrl(pdfurl);
					OCXpdfobj.SetJsonData(input);
					if(trueForm){
						OCXpdfobj.SetControlJson(JSON2.stringify(trueForm));
					}
					var comments = personalComments.split(",");
					var coms = '';
					for ( var i = 0; i < comments.length; i++) {
						if (comments[i] == 'null') {
							coms += '{"text":""}' + ',';
						} else {
							coms += '{"text":"' + comments[i] + '"}' + ',';
						}
					}
					if (coms != '' && coms.length > 0) {
						coms = coms.substring(0, coms.length - 1);
					}
					var sPopUpData = '{"datas":[' + coms
							+ '],"trueformdatas":[' + coms + ']}';
					OCXpdfobj.SetPopUpData(sPopUpData);
					var sUserData = '{"userId":"' + userId + '","username":"'
							+ userName + '","workflowId":"' + workflowId
							+ '","nodeId":"' + nodeId + '","instanceId":"'
							+ instanceId + '","processId":"' + processId
							+ '","formId":"' + formId
							+ '","sendtime":"2013-9-16 16:11:29"}';
					OCXpdfobj.SetUserInfo(sUserData);
				}, 1000);
			}
		});
	}

	function objClose() {
		document.getElementById("OBJECTbox").style.display = 'none';
	}
	function objOpen() {
		document.getElementById("OBJECTbox").style.display = '';
	}
	
	
	
	/**
	 * 下一页
	 **/
	function NetPage() {
		cpage++;
		OCXpdfobj.NetPage();
		var sjson = "";
		OCXpdfobj.SetJsonData(sjson);
	}
	function zzfs() {
		document.getElementById("jzk1").style.display = '';
	}
	function fsjs() {
		document.getElementById("jzk1").style.display = 'none';
	}
	function zzdy() {
		document.getElementById("jzk2").style.display = '';
	}
	function dyjs() {
		document.getElementById("jzk2").style.display = 'none';
	}
	/**
	 * 上一页
	 **/
	function PreviousPage() {
		cpage--;
		OCXpdfobj.PreviousPage();
	}
	/**
	 * 获得页码
	 **/
	function GetPageCount() {
		return OCXpdfobj.GetPageCount();
	}

	function getVersionByBbh() {
		return OCXpdfobj.GetVision();
	}

	/**
	 * 打印
	 */
	function PrintPDF(isOver) {
		getPrintPdfPath(isOver);
	}
	/**
	 * 打印
	 */
	function printTrue(fs, url, type) {
		return OCXpdfobj.PrintPDFByStamp(url, "", "trueway", type, fs);
	}

	/*
	 * 旋转
	 */
	function rotate(state) {
		OCXpdfobj.OnRotate(state);
	}
	var si = null;
	var len = 0;
	function getPrintPdfPath(isOver) {

		var trueJSON = getPageData();
		var json = JSON2.stringify(trueJSON.pdfjson);
		var formjson = trueJSON.formjson;
		var pdfNewPath = "";
		json = json.replace(new RegExp("\"", "gm"), "\\\"");
		json = "\"" + json + "\"";
		var params = formjson;
		params['processId'] = processId;
		params['workflowId'] = workflowId;
		params['nodeId'] = nodeId;
		params['instanceId'] = instanceId;
		params['formId'] = formId;
		params['json'] = json;
		params['isOver'] = isOver;
		$.ajax({
			url : '${ctx}/table_generatePdf.do?a=Math.random()',
			type : 'POST',
			cache : false,
			async : false,
			data : params,
			error : function(o, e) {
				alert('加载form元素错误, error:' + e + '。');
			},
			success : function(data) {
				return OCXpdfobj.PrintPDFByStamp(data, "", "", 2, 0);
			}
		});
		return pdfNewPath;
	}

	function dyPrint() {
		var zt = OCXpdfobj.GetPrintStatus();
		if (zt == 0) {
			dyjs();
			alert("打印失败");
			clearInterval(si);
			len = 0;
		} else if (zt == 2) {
			dyjs();
			OCXpdfobj.PrintPDF('');
			clearInterval(si);
			len = 0;
		}
	}
	function getPageData() {
		var tempJson = "";
		if(isFlexibleForm!=null && isFlexibleForm=='1'){
			tempJson = OCXpdfobj.GetFlexibleFormData();
		}else{
			tempJson = OCXpdfobj.GetControlJson();
		}
		var formjson = null;
		if (tempJson != '') {
			formjson = JSON2.parse(tempJson);
		}
		var newfeList = [];
		var newfeObj = {};
		if (formjson != null && formjson != 'null') {
			var feList = null;
			if(isFlexibleForm!=null && isFlexibleForm=='1'){
				feList = formjson.flexibleForm;
			}else{
				feList = formjson.trueform;
			}
			i = 0, l = feList.length;
			for (; i < l; i++) {
				var val = "";
				if (feList[i].type == 'checkbox') {
					var temp = feList[i].value;
					if (temp != '' && temp.length > 0) {
						val = temp.replace(new RegExp(";", "gm"), "^");
					}
					if (val == "undefined" || typeof (val) == "undefined") {
						val = "";
					}
					// 拆分 ^
					if (feList[i].actionurl != "") {
						var value = "";
						var vals = val.split("^");
						for ( var t = 0; t < vals.length; t++) {
							var tempVal = vals[t].split("*")[1];
							if (tempVal != "undefined") {
								if (t == 0) {
									value = tempVal;
								} else {
									value += "^" + tempVal;
								}
							} else {
								value = vals[t];
							}
						}
						newfeList.push(feList[i].name + '=' + encodeURI(value));
						newfeObj[feList[i].name] = value;
					} else {
						newfeList.push(feList[i].name + '=' + encodeURI(val));
						newfeObj[feList[i].name] = val;
					}

				} else if (feList[i].type == 'radio') {
					var temp = feList[i].value;
					//111*苏F·00001^222*fk11
					if (temp != '' && temp.length > 0) {
						val = temp.replace(new RegExp(";", "gm"), "^");
					}
					if (val == "undefined" || typeof (val) == "undefined") {
						val = "";
					}
					if (feList[i].actionurl != "") {
						var value = "";
						var vals = val.split("^");
						for ( var t = 0; t < vals.length; t++) {
							var tempVal = vals[t].split("*")[1];
							if (tempVal != "undefined") {
								if (t == 0) {
									value = tempVal;
								} else {
									value += "^" + tempVal;
								}
							} else {
								value = vals[t];
							}

						}
						newfeList.push(feList[i].name + '=' + encodeURI(value));
						newfeObj[feList[i].name] = value;
					} else {
						newfeList.push(feList[i].name + '=' + encodeURI(val));
						newfeObj[feList[i].name] = val;
					}
				} else {
					// text
					val = feList[i].value;
					if (val == "undefined" || typeof (val) == "undefined") {
						val = "";
					}
					newfeList.push(feList[i].name + '=' + encodeURI(val));
					newfeObj[feList[i].name] = val;
				}
			}
		}
		var jsonData = getActiveData();
		if(!jsonData){
			return null;
		}
		return {
			"pdfjson" : JSON2.parse(jsonData),
			"formjson" : newfeObj
		};
	}

	function PrintCurPage() {
		OCXpdfobj.PrintCurPage();
	}
	
	
	function SetDrawType(drawType, red, green, blue, width){
		OCXpdfobj.SetDrawType(drawType, red, green, blue, width);
	}
	
	
	function ShowHandWrite(show){
		OCXpdfobj.ShowHandWrite(show);
	}
	
	
	function OnRedo(){
		OCXpdfobj.OnRedo();
	}
	
	function OnUndo(){
		OCXpdfobj.OnUndo();
	}

	function copyFormFile(pdfPath, number){
		OCXpdfobj.CopyFormFile(pdfPath,number);
	}

	function isCheckBt(isbt) {
		var tempJson = null;
		if(isFlexibleForm!=null && isFlexibleForm=='1'){
			tempJson = OCXpdfobj.GetFlexibleFormData();
		}else{
			tempJson = OCXpdfobj.GetControlJson();
		}
		var formjson = null;
		if (tempJson != '') {
			formjson = JSON2.parse(tempJson);
		}
		var error = "";
		if (formjson != null && formjson != 'null') {
			var feList = null;
			if(isFlexibleForm!=null && isFlexibleForm=='1'){
				feList = formjson.flexibleForm;
			}else{
				feList = formjson.trueform;
			}
			i = 0, l = feList.length;
			for (; i < l; i++) {
				var val = "";
				if (feList[i].type == 'checkbox') {
					var temp = feList[i].value;
					if (temp != '' && temp.length > 0) {
						val = temp.replace(new RegExp(";", "gm"), "^");
					}
					if (val == "undefined" || typeof (val) == "undefined") {
						val = "";
					}
				} else if (feList[i].type == 'radio') {
					val = feList[i].value;
					if (val == "undefined" || typeof (val) == "undefined") {
						val = "";
					}
				} else {
					// text
					val = feList[i].value;
					if (val == "undefined" || typeof (val) == "undefined") {
						val = "";
					}
				}
				for ( var j = 0; j < isbt.length; j++) {
					if (isbt[j].split(':')[0] == feList[i].name && val == '') {
						//error += isbt[j].split(':')[1] + "不能为空,";
						if(feList[i].type == 'true'){
							var commentJson = getActiveData(); 
							var processId = document.getElementById("processId").value;
							if(commentJson.indexOf(processId) < 0){
								error += isbt[j].split(':')[1] + "不能为空,";
							}
						}else{
							if(val == ''){
								error += isbt[j].split(':')[1] + "不能为空,";
							}
						}
					}
				}
			}
			if (error == "") {
				return error;
			} else {
				return error.substring(0, error.length - 1);
			}
		}
	}
	
	function SetPosition(width, height) {
		//var beforeHeight = document.getElementById("OCXpdfobj").style.height;
		//if(beforeHeight != height){
		//	document.getElementById("OCXpdfobj").style.width = width;
		//	document.getElementById("OCXpdfobj").style.height = height;
		//}
	}
	
	function GetLocalMac(){
		return OCXpdfobj.GetLocalMac();
	}
</script>
<script type="text/javascript">
document.onkeydown = check;
function check(e) {
    var code;
    if (!e) var e = window.event;
    if (e.keyCode) code = e.keyCode;
    else if (e.which) code = e.which;
    if (((event.keyCode == 8) &&                                                    //BackSpace 
         ((event.srcElement.type != "text" && 
         event.srcElement.type != "textarea" && 
         event.srcElement.type != "password") || 
         event.srcElement.readOnly == true)) || 
        ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||    //CtrlN,CtrlR 
        (event.keyCode == 116) ) {                                                   //F5 
        event.keyCode = 0; 
        event.returnValue = false; 
    }
	return true;
}
function mergeJson(a,b){
	// a tagvalue
	// b formjson
	// 将a 的内容 合并到 a 里面	
	for(var c in a){
		if(b[c] == ''){
			b[c] = a[c];
		}
		
	}
	return b;
}
/*
 *根据表单标签获取值 
 */
function getFormTagValue(tagName){
	return OCXpdfobj.GetContrlFormValue(tagName);
}

function setFormValueByTag(tagName, tagVal){
	OCXpdfobj.SetContrlFormValue(tagName, tagVal);
}

function uploadLocalFile(id, path, url){
	OCXpdfobj.UploadFile(id, path, url);
}

function getFileLocalPath(id){
	return OCXpdfobj.GetPathByFileID(id);
}

function loadCEB(path){
	try {
		OCXpdfobj.PreviewCEB(path);
	} catch (e) {
	}
}

function OnRotate(status){
	try {
		OCXpdfobj.OnRotate(status);
	} catch (e) {
	}
}

function SetIsOpenDlg(status) {
	try{
		OCXpdfobj.SetIsOpenDlg(status);
	}catch (e) {
	}
}

function SearchText() {
	try{
		OCXpdfobj.SearchText();
	}catch (e) {
	}
}

function RefreshView() {
	try{
		OCXpdfobj.RefreshView();
	}catch (e) {
	}
}
function GetNextUserID() {
	try{
		return OCXpdfobj.GetNextUserID();
	}catch (e) {
	}
}
function GetAdviceJsonData(szData) {
	try{
		return OCXpdfobj.GetAdviceJsonData(szData);
	}catch (e) {
	}
}
function GetCurAdviceJsonData() {
	try{
		return OCXpdfobj.GetCurAdviceJsonData();
	}catch (e) {
	}
}
function SetReplaceFileInfo(szFileID,szFileUrl,iFileCount) {
	try{
		return OCXpdfobj.SetReplaceFileInfo(szFileID,szFileUrl,iFileCount);
	}catch (e) {
	}
}
function OneHandleKey(json,info,err,areaId){
	OCXpdfobj.SetSignType(2);
	return OCXpdfobj.GetAutoJson(json,info,"",areaId);
}
function Preview(json) {
	try{
		return OCXpdfobj.Preview(json);
	}catch (e) {
	}
}
</script>
</body>
</html>