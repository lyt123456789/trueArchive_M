<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="keywords" content="">
	<title><%=SystemParamConfigUtil.getParamValueByParam("webTitle") %></title>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/headerbase.jsp"%>    
</head>
<body>
	<div class="tw-super-search">
		<form id="menuFrom" action="#" method="post" class="tw-form tw-form-horizontal-lg">
			<input type="hidden" name="menuFaterId" value="${menuFaterId}">
			<input type="hidden" name="menuStatus" value="1">
			<div class="tw-form-item">
              	<label class="tw-form-label"><em>*</em>菜单名称：</label>
              	<div class="tw-form-field">
              		<input type="text" name="menuName" id="menuName"  onmousedown="changeClass('menuNametip')" class="tw-form-text"  maxlength="100"  />
              		<span id="menuNametip"><i></i></span>
             	</div>
       		</div>
       		<div class="tw-form-item">
              	<label class="tw-form-label"><em>*</em>菜单简称：</label>
              	<div class="tw-form-field">
              		<input type="text" name="menuSimpleName"  id="menuSimpleName" onmousedown="changeClass('menuSimpleNametip')"  maxlength="200"  class="tw-form-text" />
             		 <span id="menuSimpleNametip"><i></i></span>
             	</div>
       		</div>
        	<div class="tw-form-item">
              	<label class="tw-form-label"><em>*</em>菜单链接：</label>
              	<div class="tw-form-field">
              		<textarea name="menuUrl" id="menuUrl" onmousedown="changeClass('menuUrltip')" class="tw-form-textarea" style="width:400px; height:62px"></textarea>
              		<span id="menuUrltip"><i></i></span>
             	</div>
       		</div>

	        <div class="tw-form-item" style="display: none;">
				<label class="tw-form-label" >菜单图片：</label>
	            <div class="tw-form-field">
					<input type="file" class="" id="upImg" name="upImg"/>
	            </div>
	        </div>
             <div class="tw-form-item" style="display: none;">
              	<label class="tw-form-label" >图片地址：</label>
              	<div class="tw-form-field">
              		<input type="text" name="menuPath"   id="menuPath" maxlength="200"  class="tw-form-text"/>
              		<div id="chooseImg" class="tw-btn-primary" >
	                   	<i class="tw-icon-search"></i> 从系统本地选择图片
	               	</div>
             	</div>
       		</div>
       		<div class="tw-form-item" id="menuExtLinksDiv" >
                <label class="tw-form-label" >是否为外部链接：</label>
                <div class="tw-form-field wf-form-field-rc">    
                    <label class="tw-form-label-rc"><input type="radio" value="1" class="tw-form-radio" name="menuExtLinks"><span>是</span></label>
                    <label class="tw-form-label-rc"><input type="radio" checked="checked" value="0" class="tw-form-radio" name="menuExtLinks"><span>否</span></label>
               	</div>
       		</div>

       		<div class="tw-form-item" style="display: none;">
                <label class="tw-form-label" >菜单类型：</label>
                <div class="tw-form-field wf-form-field-rc">    
                    <label class="tw-form-label-rc"><input type="radio" checked="checked" value="1" class="tw-form-radio" name="menuType"><span>相对</span></label>
                    <label class="tw-form-label-rc"><input type="radio" value="0" class="tw-form-radio" name="menuType"><span>绝对</span></label>
               	</div>
       		</div>
      		<div class="tw-form-item" style="display: none;">
				<label class="tw-form-label" >打开方式：</label>
				<div class="tw-form-field tw-form-field-rc">    
					<label class="tw-form-label-rc">
						<input type="radio" name="menuRank" id="menuRank"  value="1"><span>弹框</span>
					</label>
	                <label class="tw-form-label-rc">
	                    <input type="radio" name="menuRank" id="menuRank" checked="checked" value="0"><span>内嵌</span>
					</label>
	            </div>
			</div>
			<div class="tw-form-item">
				<label class="tw-form-label" >角标SQL语句：</label>
				<div class="tw-form-field tw-form-field-rc">    
					<label class="tw-form-label-rc">
						<textarea name="countSql" id="countSql" style="width:400px; height:62px" class="tw-form-textarea"></textarea>
					</label>
	            </div>
			</div>
       		<div class="tw-form-item">
             	<label class="tw-form-label" >排序号：</label>
             	<div class="tw-form-field">
                 	<input type="text" class="tw-form-text"  onmousedown="changeClass('menuSorttip')"  name="menuSort"  id="menuSort" maxlength="6" >
            	 	<span id="menuSorttip"><i></i></span>
             	</div>
        	</div>
        	
        	<div class="tw-form-action wf-action-left">
                <div id="btnSuperSearch" class="tw-btn-primary" onclick="saveMenu();" >
                    <i class="tw-icon-search"></i> 保存
                </div>
                <div class="tw-btn" onclick="javascript:parent.layer.closeAll();">
                    <i class="tw-icon-minus-circle"></i> 关闭
                </div>
       		</div>
   		</form>
	</div>
</body>
<script type="text/javascript">	
	function saveMenu(){
		var menuName = document.getElementById("menuName").value;
		var menuSimpleName = document.getElementById("menuSimpleName").value;
		var menuUrl = document.getElementById("menuUrl").value;
		if (menuName == null || menuName == "") {
			alert("菜单名称不能为空");
			return;
		}
		if (menuSimpleName == null || menuSimpleName == "") {
			alert("菜单简称不能为空");
			return;
		}
		if (menuUrl == null || menuUrl == "") {
			alert("菜单链接不能为空");
			return;
		}
		if($("input[name='menuExtLinks']:checked").val()=='1'){
			if(!$("#foriegnAppAddress").val()){
				alert("外部应用地址不能为空");
				return;
			}
		}
		
		var options = {
			url: "${ctx}/menu_saveMenu.do",
			type:"post",
			error:function(){
				layer.alert("系统错误请重试");
			},
			success:function(result){
				var res = eval("("+result+")");
				if(res.success){
					parent.layer.confirm('添加成功',{
						btn:['确定']
					},function(){
						parent.location.reload();
						parent.layer.closeAll();
					});
				}else{
					layer.alert("添加失败");
				}
			}
		};
		$("#menuFrom").ajaxSubmit(options);
	}

	function changeClass(id){
		$('#'+id).removeClass("formTip");
		$('#'+id)[0].innerHTML = '<span style="color: red;">*</span>';
		return true;
	}
	
	$(document).ready(function(){
		parent.layer.iframeAuto(parent.layer.getFrameIndex(window.name));
		$("input[name='menuExtLinks']").change(function(){
		/* 	var newArea = '<div class="tw-form-item" id="foriegnAppAddressDiv"><label class="tw-form-label" >外部链接：</label>'+
			'<div class="tw-form-field"><select class="tw-form-select" name="foriegnAppAddress" id="foriegnAppAddress"></select></div></div>'; */
			 var newArea = '<div class="tw-form-item" id="foriegnAppAddressDiv"><label class="tw-form-label" >外部链接：</label>'+
				'<div class="tw-form-field"><input class="tw-form-text" name="foriegnAppAddress" id="foriegnAppAddress"></input></div></div>';
			
			if($("input[name='menuExtLinks']:checked").val()=='1'){
				$("#menuExtLinksDiv").after(newArea);
				parent.layer.iframeAuto(parent.layer.getFrameIndex(window.name));
				
				/* $("#foriegnAppAddress").after('<span class="tw-info"><i></i><a><span style="cursor:pointer" '+
					'onclick="parent.window.location.href=\'${ctx}/application_getApplicationList.do\';">点击新增外部应用地址<span></a></span>'); */
				/* $(res).each(function(i,n){
					$("#foriegnAppAddress").append('<option value="'+n.id+'">'+n.appName+'</option>');
				}); */
				/* $.ajax({
					url:"${ctx}/application_getAllForeignAddress4Select.do",
					type:"post",
					async:false,
					cache: false,
					success:function(result){
						var res = eval("("+result+")");
						if(res.length==0){
							$("#foriegnAppAddress").after('<span class="tw-error"><i></i>没有可以使用的外部应用地址！<a>'+
									'<span style="cursor:pointer" onclick="parent.window.location.href=\'${ctx}/application_getApplicationList.do\';">点击添加<span></a></span>');
							$("#foriegnAppAddress").remove();
						}else{
							$("#foriegnAppAddress").after('<span class="tw-info"><i></i><a><span style="cursor:pointer" '+
									'onclick="parent.window.location.href=\'${ctx}/application_getApplicationList.do\';">点击新增外部应用地址<span></a></span>');
							$(res).each(function(i,n){
								$("#foriegnAppAddress").append('<option value="'+n.id+'">'+n.appName+'</option>');
							});
						}
					},
					error:function(){
						layer.alert("系统错误请重试");
					}	
				}); */
			}else{
				$("#foriegnAppAddressDiv").remove();
			}
       		
		});
		
		$("#chooseImg").click(function(){
			var w ,	h;
			if(parent.document.body.clientWidth>858){
				w=858;
			}else{
				w=parent.document.body.clientWidth-2;
			}
			if(parent.document.body.clientHeight>600){
				h=600;
			}else{
				h=parent.document.body.clientHeight-2;
			}
		    parent.layer.open({
		        title: '图片选择',
		        area: [w+'px', h+'px'],
		        type: 2,
		        maxmin:true,
		        scrollbar:false,
		        content: '${ctx}/icon_previewImg.do?t='+new Date(),
		        success:function(layero,index){
		        	var arr=[];
		        	if($("#menuPath").val()!=null){
		        		arr.push($("#menuPath").val());
		        	}
		        	var iframeWin = parent.window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
		     		iframeWin.imgSetter(1,arr,true);
		        },
		        btn:['确认选择','取消'],
		        yes:function(index,layero){
		           	var iframeWin = parent.window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
		            var	value = iframeWin.imgGetter();
		           	if(value.length==1){
		           		parent.layer.close(index);
		           		$("#menuPath").val(value[0].path);
		           	}else{
		           		parent.layer.close(index);
		           		layer.alert("请只选择一张图片作为图标");
		           	}
		        },
		        btn2:function(index,layero){
		        	layer.close(index);
		        },
				cancel:function(){
				}        
			});
		});
	});
	
</script>
<%@ include file="/common/widgets.jsp"%>