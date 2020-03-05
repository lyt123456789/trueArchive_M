<%@page import="net.sf.json.JSONArray"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page import="java.net.URLDecoder"%><html>
<head>
<meta charset="utf-8">
<%@ include file="/common/header.jsp"%>
<title>版式文件预览</title>
<script src="${ctx}/widgets/plugin/js/base/jquery.js" type="text/javascript"></script>
<script src="${ctx}/widgets/component/taglib/attachment/js/attachment.js" type="text/javascript"></script>
<script src="${ctx}/widgets/plugin/js/sea.js"></script>  
<link href="${ctx}/dwz/style/css/ued.base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/dwz/style/css/ued.demo.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/ued.base.css?t=2011" />
<link rel="stylesheet" href="${ctx}/widgets/theme/dm/css/ued.module.css?t=2012" />
<link rel="stylesheet" href="${ctx}/widgets/theme/mice/form.css?t=2012" />   
<style type="text/css">
    #pagetools{
        position: absolute;
        top:0;
        right: 0;
        display: none;
    }
    #faq{
        margin-top: 10px;
        width: 916px;
       	line-height:180%;
		text-align:center;
        font-size:12px;
        background-color: #efefef;
        z-index: 100;
        border: 1px dashed #cccccc;
    }
    #faq span{
        padding-right: 10px;
    }
    .faqt{
        color: #f00;
    }
    #OBJECTbox{
        
    }
     	
    .dialogform{
        border: 1px solid #BDD3D6;
        /**position: absolute;
        left: 0;
        top: 0;
        z-index: 100;
        display: none;
        
        **/
        margin: 0 auto;
       
        width : 916px;
        font-size: 12px;
        visibility: visible;
        background-color: #EFEFEF;
        
    }
    .dialogform .titlebar{
        background-color: #E7EFEF;
        height: 30px;
        line-height: 30px;
        text-align: left;
        position: relative;
        padding-left: 15px;
        color: #183052;
    }
    .dialogform .titlebar .closebtn{
        position: absolute;
        top: 2px;
        right: 2px;
        text-align: center;
        height: 24px;
        line-height: 24px;
        background-color: #E7F7F7;
        border: 1px solid #5296B5;
        padding: 0 5px;
        display: block;
    }
    .dialogform .contentbox{
        padding: 10px;
        background-color: #f7f7f7;
        overflow: auto;
    }
    form{
        padding: 0;
        margin: 0;
    }
</style>
<script src="${ctx}/form/jsp/widgets/common/js/doublelist.js" type="text/javascript"></script>
	<script type="text/javascript" defer="defer">
	window.onload=function(){
		var listValues='<%=request.getSession().getAttribute("listValues")==null?"":request.getSession().getAttribute("listValues")%>';
		var selects='<%=request.getParameter("selects")==null?"":request.getParameter("selects")%>';
		var limitValue='<%=request.getParameter("limitValue")==null?"":request.getParameter("limitValue")%>';
		var valuestr='<%=request.getParameter("values")==null?"":request.getParameter("values")%>';	
		var instanceId = '<%=request.getParameter("instanceId")==null?"":request.getParameter("instanceId")%>';
		tagvalues(listValues,selects,limitValue,valuestr,instanceId);//标签赋值，权限控制
	};
</script>
<script type="text/javascript">
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
    			//$("#xto").val(_getValue(retVal.src,['name','id','nums','gzname'],'|').join(';'));
    			$("#jsonobjform_fsjg").val(_getValue(retVal.src,['name'],'|').join(';'));
    			//alert( document.getElementById("xto").value);
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
    			//$("#xcc").val(_getValue(retVal.src,['name','id','nums','gzname'],'|').join(';'));
    			$("#jsonobjform_csjg").val(_getValue(retVal.src,['name'],'|').join(';'));
    	}
</script>
</head>
<body>
<div style="width:1024px;margin:0 auto;" >
    <div id="OBJECTbox">
    <OBJECT id="OCXpdfobj" classid="clsid:ECCC5C8C-8DA0-4FAC-935A-CD5229A14BD7" width="1024" height="1448"></OBJECT></div>
</div>
<INPUT type="hidden" id="instanceId" name="instanceId" value='<%=request.getParameter("instanceId")%>'> 
<INPUT type="hidden" id="formId" name="formId" value='<%=request.getParameter("formId")%>'> 
<INPUT type="hidden" id="workFlowId" name="workFlowId" value='<%=request.getParameter("workFlowId")%>'>   
<INPUT type="hidden" id="processId" name="processId" value='<%=request.getParameter("processId")%>'>  
<script src="jquery.min.1.7.2.js"></script>
<script src="${ctx}/pdfocx/json2.js"></script>
<script src="calendar.js"></script>

<script language="javascript">
var workflowId=document.getElementById('workFlowId').value;
var isWriteNewValue='<%=request.getParameter("isWriteNewValue")%>';
var newInstanceId='<%=request.getParameter("newInstanceId")%>';
var newProcessId='<%=request.getParameter("newProcessId")%>';
var isCanLookAtt='<%=request.getParameter("isCanLookAtt")%>';
var userId = '<%=request.getParameter("userId")%>',
    nodeId='<%=request.getParameter("nodeId")%>',
    instanceId='<%=request.getParameter("instanceId")%>',
    processId='<%=request.getParameter("processId")%>',
    formId='<%=request.getParameter("formId")%>',
    oldFormId='<%=request.getParameter("oldFormId")%>',
    userName='<%=request.getSession().getAttribute("userName")%>',
    webId='<%=request.getParameter("webId")%>',
    personalComments='<%=request.getSession().getAttribute("personalComments")%>',
  //  pdfPath=window.parent.document.getElementById('pdfPath').value;
    pdfPath = '${pdfPath}';
    workflowId=(workflowId=='null')?'':workflowId;
var jsonurl='${ctx}/table_getFormValueAndLocationOfWeb.do';
var pdfurl=pdfPath;
/**
* 筛选后的form json
**/     
var oldformJSON;
/**
* 完整的true json
**/
var trueJSON={
  "company" : "trueway",
  "basicOS" : "ios",
  "version" : 2,
  "width" : 1024,
  "height" : 1448,
  "processes":[]
}
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
var cpage=1;


if(isWriteNewValue == 'false'){
	instanceId = newInstanceId;
	processId  = newProcessId;
}

//+++ by yuxl


/* $.ajax({
    url: jsonurl,
    type: 'POST',
    dataType: 'json',
    data:'userId='+userId+'&workflowId='+workflowId+'&nodeId='+nodeId+'&instanceId='+instanceId+'&processId='+processId+'&formId='+formId+'&oldFormId='+oldFormId,
    timeout: 30000,
    error: function(o,e){
        alert('加载form元素错误, error:'+e+'。');
    },
    success: function(data){
        oldformJSON=data;
        setActiveData(oldformJSON);
        
      //  createForm(data);
    }
}); */
oldformJSON = ${json};
var imageCount = '${imageCount}';
setActiveData(oldformJSON);
function ymChange(){
	var ym = $("#ym").val();
	OCXpdfobj.GoToPage(ym);
}

/**
* 获取用户输入的信息
**/
function getActiveData()
{
    var d = new Date(),
    YMDHMS = d.getFullYear() + "-" +(d.getMonth()+1) + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
    var trueJSON = OCXpdfobj.GetData();
    return trueJSON;
}
/**
* 输入true文件信息
**/
function setActiveData(trueForm){	
	setTimeout(function(){
	    		OCXpdfobj.SetPDFUrl(pdfurl);
	    	      OCXpdfobj.SetControlJson(JSON2.stringify(trueForm));
	    	    	var comments = personalComments.split(",");
	    	    	var coms = '';
	    	    	for(var i=0;i<comments.length;i++){
	    	    		if(comments[i]=='null'){
  	    				coms +='{"text":""}'+',';
	    	    		}else{
  	    				coms +='{"text":"'+comments[i]+'"}'+',';
	    	    		}
	    	        }
	    	    	if(coms!='' && coms.length>0){
	    	    		coms = coms.substring(0,coms.length-1);
	    	    	}
	    	    	var sPopUpData = '{"datas":['+coms+']}';
	    	    	OCXpdfobj.SetPopUpData(sPopUpData);
	    	    	var sUserData = '{"userId":"'+userId+'","username":"'+userName+'","workflowId":"'+workflowId+'","nodeId":"'+nodeId+'","instanceId":"'+instanceId+'","processesId":"'+processId+'","formId":"'+formId+'","sendtime":"2013-9-16 16:11:29"}';
	    	    	OCXpdfobj.SetUserInfo(sUserData);
	    	        document.getElementById("OBJECTbox").style.display='';
	    },1000);
}
/**
* 下一页
**/
function NetPage()
{
    cpage++;
    OCXpdfobj.NetPage();
    var sjson = "";
    OCXpdfobj.SetJsonData(sjson);
}
/**
* 上一页
**/
function PreviousPage()
{
/*  var data = OCXpdfobj.GetControlJson();
	alert(data); 
	getPageData(); */
    cpage--;
    OCXpdfobj.PreviousPage();
}
/**
* 获得页码
**/
function GetPageCount()
{
    return OCXpdfobj.GetPageCount();
}

/**
 * 打印
 */
function PrintPDF(){
	var pdfNewPath = getPrintPdfPath();
	 //浏览器中打印
	   /*  var param="toolbar=yes,location=yes,menubar=yes,scrollbars=yes,resizable=yes"; 
	    	window.open(pdfNewPath,'maxwindow',param);   */
   /* 	$.ajax({
   	    url: '${ctx}/table_fileExist.do',
   	    type: 'POST',
   	    data:'newPdfPath='+pdfNewPath,
   	    timeout: 30000,
   	    error: function(o,e){
   	        alert(' error:'+e+'。');
   	    },
   	    success: function(data){
   	    	 //浏览器中打印
   	    /* 	 var param="toolbar=yes,location=yes,menubar=yes,scrollbars=yes,resizable=yes"; 
  /*  	    	window.open(data,'maxwindow',param);   */ 
   	/*     }
   	}); 
   	var param="toolbar=yes,location=yes,menubar=yes,scrollbars=yes,resizable=yes";   */
   	//  window.open(data,'maxwindow',pdfPath[1]);  
   	/* OCXpdfobj.SetPDFUrl(data);
		var param="toolbar=yes,location=yes,menubar=yes,scrollbars=yes,resizable=yes";   
		OCXpdfobj.CreatePDF("E:\\Workspaces\\Eclipse\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp4\\wtpwebapps\\trueWorkFlowV2.1_GT\\form\\html\\test.pdf"); */
// OCXpdfobj.SetPDFUrl(pdfurl); */ */
	   
}

function getPrintPdfPath(){
	var trueJSON =	getPageData();
	var json = JSON2.stringify(trueJSON.pdfjson);
	var formjson = trueJSON.formjson;
	var pdfNewPath = "";
	json = json.replace(new RegExp("\"","gm"),"\\\"");
	json ="\""+json+"\"";
	$.ajax({
	    url: '${ctx}/table_generatePdf.do',
	    type: 'POST',
		async : false,
	    data:'processId='+processId+'&workflowId='+workflowId+'&nodeId='+nodeId+'&instanceId='+instanceId+'&formId='+formId+'&json='+json+'&'+formjson,
	    error: function(o,e){
	        alert('加载form元素错误, error:'+e+'。');
	    },
	    success: function(data){
	    	//var pdfPath = data.split(",");
	    //	pdfNewPath = data;
	    //	alert(data);
	    	//OCXpdfobj.PrintPDF("http://192.168.5.14:8080/trueWorkFlowV2.1_GT/form/html/newInfo_1389163023934merge.pdf");
	    	OCXpdfobj.PrintPDF(data);
	    /* 	var trueJSON = OCXpdfobj.GetData();
	    	OCXpdfobj.CreatePDF(pdfPath[0],pdfPath[1]);  
	    	pdfNewPath = pdfPath[1]; */
    	}
	}); 
	return pdfNewPath;
}

function getPageData(){
	var tempJson = OCXpdfobj.GetControlJson();
	 var formjson= null;
	if(tempJson != ''){
		 formjson= JSON2.parse(tempJson);
	}
    var newfeList=[];
    if(formjson != null && formjson !='null'){
	    var feList=formjson.trueform;
	        i=0,l=feList.length;
	    for(;i<l;i++){
			var val = "";
				if(feList[i].type == 'checkbox'){
	        		var temp = feList[i].value;
					if(temp != '' && temp.length > 0 ){
						val = temp.replace(new RegExp(";","gm"),"^");
					}
					if(val == "undefined" || typeof(val) == "undefined"){
						val = "";
					}
	        		newfeList.push(feList[i].name+'='+ encodeURI(val));
				}else if(feList[i].type == 'radio'){
					val =  feList[i].value;
					if(val == "undefined" || typeof(val) == "undefined"){
						val = "";
					}
		            newfeList.push(feList[i].name+'='+encodeURI(val));
				}else{
					// text
					val = feList[i].value;
					if(val == "undefined" || typeof(val) == "undefined"){
						val = "";
					}
		            newfeList.push(feList[i].name+'='+encodeURI(val));
				}
	    }
    }
    return {
        "pdfjson":JSON2.parse(getActiveData()),
        "formjson":newfeList.join('&')
    };
    
}
</script>
<script type="text/javascript">
	//以下必须有
	function loadCss(){  
   		seajs.use('lib/form',function(){  
			$('input[mice-btn]').cssBtn();
			$('input[mice-input]').cssInput();
			$('select[mice-select]').cssSelect();
	    });
	}
	//以上必须有
</script>
</body>
</html>