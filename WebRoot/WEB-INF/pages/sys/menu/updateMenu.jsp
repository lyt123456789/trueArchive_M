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
	<form id="menuFrom" method="post" class="tw-form tw-form-horizontal-lg">
		<input type="hidden" name="menuId" value="${sysMenu.menuId}">
		<input type="hidden" name="menuFaterId" value="${sysMenu.menuFaterId}">
		<input type="hidden" name="menuSerial" value="${sysMenu.menuSerial}">
		<div class="tw-form-item" style="size: 16px">
			<label class="tw-form-label"><em>*</em>菜单名称：</label>
		    <div class="tw-form-field" style="size: 16px"> 
		        <input name="menuName" id="menuName" type="text"  onmousedown="changeClass('menuNametip')"  maxlength="100"  class="tw-form-text" value="${sysMenu.menuName}"/>
				<span id="menuNametip"><i></i></span>
            </div>
		</div>
		<div class="tw-form-item" style="size: 16px">
            <label class="tw-form-label"><em>*</em>菜单简称：</label>
			<div class="tw-form-field" style="size: 16px">
				<input type="text" name="menuSimpleName"  maxlength="200"  id="menuSimpleName" value="${sysMenu.menuSimpleName}" class="tw-form-text" />
		        <span id="menuSimpleNametip"><i></i></span>
            </div>
		</div>
		<div class="tw-form-item" style="size: 16px">
            <label class="tw-form-label"><em>*</em>菜单链接：</label>
            <div class="tw-form-field" style="size: 16px">
            	<textarea name="menuUrl" id="menuUrl" onmousedown="changeClass('menuUrltip')" class="tw-form-textarea" style="width:400px; height:62px">${sysMenu.menuUrl}</textarea>
				<span id="menuUrltip"><i></i></span>
			</div>
		</div>
		        <div class="tw-form-item" style="size: 16px;display: none;">
			<label class="tw-form-label" >菜单图片：</label>
            <div class="tw-form-field">
				<input type="file" class="" id="upImg" name="upImg"/>
            </div>
        </div>
		
        <div class="tw-form-item" style="size: 16px;display: none;">
			<label class="tw-form-label">图片地址：</label>
			<div class="tw-form-field" style="size: 16px">
				<input type="text" name="menuPath"  id="menuPath" maxlength="200" value="${sysMenu.menuPath}" class="tw-form-text"/>
				<div id="chooseImg" class="tw-btn-primary" style="size: 16px" >
                   	<i class="tw-icon-search"></i> 从系统本地选择图片
               	</div>
			</div>
		</div>
       
		<div class="tw-form-item"  id="menuExtLinksDiv" style="size: 16px;">
			<label class="tw-form-label" >是否为外部链接：</label>
			<div class="tw-form-field tw-form-field-rc">    
				<label class="tw-form-label-rc">
					<input type="radio" name="menuExtLinks" id="menuExtLinks"  <c:if test="${sysMenu.menuExtLinks=='1'}">checked</c:if>   value="1"><span>是</span>
				</label>
				<label class="tw-form-label-rc">
					<input type="radio" name="menuExtLinks" id="menuExtLinks"  <c:if test="${sysMenu.menuExtLinks=='0'}">checked</c:if>  value="0"><span>否</span>
				</label>
			</div>
		</div>
		<div class="tw-form-item" style="size: 16px;display: none;">
			<label class="tw-form-label" >菜单类型：</label>
			<div class="tw-form-field tw-form-field-rc">    
				<label class="tw-form-label-rc">
					<input type="radio" name="menuType" id="menuType" <c:if test="${sysMenu.menuType=='1'}">checked</c:if>   value="1"><span>相对</span>
				</label>
                <label class="tw-form-label-rc">
                    <input type="radio" name="menuType" id="menuType"  <c:if test="${sysMenu.menuType=='0'}">checked</c:if> value="0"><span>绝对</span>
				</label>
            </div>
		</div>
       	<div class="tw-form-item" style="size: 16px;display: none;">
			<label class="tw-form-label" >打开方式：</label>
			<div class="tw-form-field tw-form-field-rc">    
				<label class="tw-form-label-rc">
					<input type="radio" name="menuRank" id="menuRank" <c:if test="${sysMenu.menuRank=='1'}">checked</c:if>   value="1"><span>弹框</span>
				</label>
                <label class="tw-form-label-rc">
                    <input type="radio" name="menuRank" id="menuRank"  <c:if test="${sysMenu.menuRank=='0'}">checked</c:if> value="0"><span>内嵌</span>
				</label>
            </div>
		</div>
		<div class="tw-form-item" style="size: 16px">
				<label class="tw-form-label" >角标SQL语句：</label>
				<div class="tw-form-field tw-form-field-rc">    
					<label class="tw-form-label-rc">
						<textarea name="countSql" id="countSql" style="width:400px; height:62px" class="tw-form-textarea">${sysMenu.countSql}</textarea>
					</label>
	            </div>
			</div>
		<div class="tw-form-item" style="size: 16px">
			<label class="tw-form-label" >排序号：</label>
            <div class="tw-form-field">
				<input name="menuSort" id="menuSort"  onmousedown="changeClass('menuSort')"  type="text"  maxlength="6"  class="tw-form-text" value="${sysMenu.menuSort}" />
		        <span id="menuSorttip"><i></i></span>
             </div>
        </div> 
        
		<div class="tw-form-action tw-action-left" style="size: 16px">
			<div id="btnSuperSearch" class="tw-btn-primary" onclick="updateMenu();" >
				<i class="tw-icon-search"></i> 保存
			</div>
			<div class="tw-btn" onclick="javascript:parent.layer.closeAll();" style="size: 16px">
				<i class="tw-icon-minus-circle"></i> 关闭
			</div>
       </div>
    </form>
</div>
</body>
<script type="text/javascript">	
	function updateMenu(){
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
			url: "${ctx}/menu_updateMenu.do",
			type:"post",
			error:function(){
				layer.alert("系统错误请重试");
			},
			success:function(result){
				var res = eval("("+result+")");
				if(res.success){
					parent.layer.confirm('更新成功',{
						btn:['确定']
					},function(){
						parent.location.reload();
						parent.layer.closeAll();
					});
				}else{
					layer.alert("更新失败");
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
		
		/* var newArea = '<div class="tw-form-item" id="foriegnAppAddressDiv"><label class="tw-form-label" >外部链接：</label>'+
		'<div class="tw-form-field"><select class="tw-form-select" name="foriegnAppAddress" id="foriegnAppAddress"></select></div></div>';
		 */
		 var newArea = '<div class="tw-form-item" id="foriegnAppAddressDiv"><label class="tw-form-label" >外部链接：</label>'+
			'<div class="tw-form-field"><input class="tw-form-text" name="foriegnAppAddress" id="foriegnAppAddress"></input></div></div>';
		
		var menuExtLinks = function(){
			if($("input[name='menuExtLinks']:checked").val()=='1'){
				$("#menuExtLinksDiv").after(newArea);
				parent.layer.iframeAuto(parent.layer.getFrameIndex(window.name));
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
		};
		
		menuExtLinks();
		
		$("input[name='menuExtLinks']").change(function(){
			menuExtLinks();
		});
		
		if("${sysMenu.foreignAppAddress}"!=""){
			$("#foriegnAppAddress").val("${sysMenu.foreignAppAddress}");
		}
		
		$("#chooseImg").click(function(){
		    parent.layer.open({
		        title: '图片选择',
		        area: ['858px', '600px'],
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