<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>人员选择</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@ include file="/common/header.jsp"%>
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/ued.base.css" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/ui.css?v=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?v=2012" />
	</head>
	<body>
		<script src="${cdn_js}/base/jquery.js"></script>
		<script src="${cdn_js}/sea.js"></script>
		<script>
			var resultData={
				data:{},
				size:0
			};
			seajs.use('ui/tree',function(tui){
				var treeface=new tui.treeUI({
					show:true,
					id:'treeface',
					showBtn:'button',
					width:$(window).width(),
					height:$(window).height(),
					submitBtn:[{name:'设置完成',cls:'ok',style:'',id:'ok'}],
					submitFn:function(e){
						var e=e||window.event,et=e.target||e.srcElement;
						var id=et.id;
						switch(id){
							case "ok":
								window.returnValue=resultData;
								window.close();
							break;
						}
					},
					"resultData":window.resultData.data,
					lock:false,
					callback:function(){
						/****/
						var that=this;
						var t=$(this.treebox,this.el);
						var $tree=$('<ul class="mice-tree tree treeFolder treeCheck expand"></ul>').appendTo(t);
						$.getJSON('${empDataUrl}',function(json){
				
					seajs.use('common/pathBuildTree',function(pbt){
						var pbtree=new pbt.pbTree({
							type:'url',
							data:json,
							target:$tree,
							isSubChild:false,
							order:'asc',
							isDep:false,//表示是人员树
							callback:function(){
								var $this=this;
								seajs.use('lib/tree-test-new',function(){
									$('.tree',t).tree({
										"checkFn":function(){},
										"asynFn":function(){},
										"finded":true,
										autolevel:1,
										//"selectedData":window.dialogArguments,
										file:'file' ,//folder_expandable, file,
										resultData:window.resultData.data
									});
								});
							}
						});
					
				});

			});
			/**
			**/		
		}
	});
});
</script>
</body>
</html>
