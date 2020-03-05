 <!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>流程发送人员选择树</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="${cdn_imgs}/mice/form.css?t=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/ued.base.css" />
		<link rel="stylesheet" href="${cdn_imgs}/mice/ui.css?v=2012" />
		<link rel="stylesheet" href="${cdn_imgs}/dm/css/ued.module.css?v=2012" />
    </head>
    <body>
        <script src="${cdn_js}/base/jquery.js"></script>
		<script src="${cdn_js}/sea.js"></script>
		<script type="text/javascript" src="${cdn_js}/common/dialog/artDialog.js"></script>
		<script>
			function errorMsg(msg){
				art.dialog({
						lock: true,
					    content: msg,
					    id: 'EF893L',
					    height:50,
					    width:260,
					 	opacity: .10,
					 	ok:function(){
				    },
				    okVal:'确认'
					});
			}
			var resultbox={}; //待发送的对象
			var obj = window.dialogArguments;
			var multi = obj.multi;
			var resultData={
				data:{},
				size:0
			};

			function getImportantState(){
				//是否急件 0非急件 1急件
				var importantState=0;
				$("input[name='imptState']").each(function(){
					if(this.checked==true){
						importantState=this.value;
					}
				});
				return importantState;
			}
			seajs.use('ui/tree',function(tui){
				var treeface=new tui.treeUI({
					show:true,
					id:'treeface',
					showBtn:'button',
					width:$(window).width(),
					height:$(window).height(),
					submitBtn:[ {name:'保存为组',cls:'ok',style:'',id:'saveGroup'},
								{name:'选择组',cls:'ok',style:'',id:'selectGroup'},
								{name:'送自己',cls:'ok',style:'',id:'selectOneself'},
								{name:'确定',cls:'ok',style:'',id:'showchecked'},
								{name:'取消',cls:'ok',style:'',id:'cancel'},
								{name:'是否急件:<input type="radio" name="imptState" value="1">是<input type="radio" name="imptState" value="0" checked="checked">否',cls:'',style:'',id:'imptState'}],
								
					submitFn:function(e){
						var e=e||window.event,et=e.target||e.srcElement;
						var id=et.id;
						switch(id){
						//添加组
							case "saveGroup":
								var size = resultData.size;//resultData相当于一个方法   .size是其中的一个属性大小  且只能选择一个人
								$.each(resultData.data,function(k,v){ 
									size++;
								});
								if(size == 0){
										errorMsg("至少选择一项!");
									}else{
										var retObj = {};
										var userInfos = "";
										var index=1;
										$.each(resultData.data,function(k,v){
											userInfos=(index==size?userInfos+v.value+","+v.name:userInfos+v.value+","+v.name+";");
											index++;
										}); 
										retObj.importantState=getImportantState();
										retObj.userInfos = userInfos;
										//window.returnValue=retObj;
										var obj = new Object();
										obj.name=userInfos;
										window.showModalDialog("${ctx}/selectTree_toSaveEmpGroup.do?rnd="+Math.random(),obj, 
												'dialogWidth: 300px;dialogHeight: 30px; status: no; scrollbars: no; Resizable: no; help: no;');

									}
								break;				
						//选择组
							case "selectGroup":
								    var result = window.showModalDialog("${ctx}/selectTree_showEmpGroup.do?rnd="+Math.random(), "", 
												'dialogWidth: 300px;dialogHeight: 350px; status: no; scrollbars: no; Resizable: no; help: no;');
									if(result!=null && result!= undefined){
										var sdata=result.split(';');
										var i=0,l=sdata.length,s;
										for(i;i<l;i++){
											s=sdata[i].split(',');
											if(!resultData.data[s[0]]){
												resultData.data[s[0]]={name:s[1],value:s[0]};
												var t=$('li[tid='+s[0]+']>div>div.ckbox');
												if(t.size()>0){
													t.trigger('click');//,$this.treebox
												}else{
													$('#treeface .treePanelResultbox').append('<p pid="'+s[0]+'" jval="'+s[1]+'">'+s[1]+'</p>');
												}
												
											}
										}
									}
								break;
						//-----分割线------
							case "showchecked":
								var size = resultData.size;//resultData相当于一个方法   .size是其中的一个属性大小  且只能选择一个人
								$.each(resultData.data,function(k,v){ 
									size++;
								});
								if (size==0){
										errorMsg("发送人不能为空！");
					                    return;
					            }
								//只能选择一个人
								if(multi == '0'){//multi相当于获取树形菜单中人员的集合
									if(size > 1){
										errorMsg("只可以选择一个发送人");
										return;
									}
								}
								var retObj = {};
								var userIds = "";
								var index=1;
								$.each(resultData.data,function(k,v){
									userIds=(index==size?userIds+v.value:userIds+v.value+",");
									index++;
								});
								retObj.importantState=getImportantState();
								retObj.userId = userIds;
								window.returnValue=retObj;
								window.close();
								break;
							case "cancel":
								window.returnValue = 'noChange';
								window.close();
							break;
							case "selectOneself":
				            	var retObj = {};
				            	retObj.importantState = getImportantState();
				            	retObj.userId = '${userId}';
				            	window.returnValue = retObj;
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
						$.getJSON('${empDataUrl}&depIds='+obj.depIds.toString(),function(json){
							seajs.use('common/pathBuildTree',function(pbt){
								var pbtree=new pbt.pbTree({
									type:'url',
									data:json,
									target:$tree,
									order:'asc',
									isSubChild:false,
									isDep:false,//表示是人员树
									callback:function(){
										var $this=this;
										seajs.use('lib/tree-test-new',function(){
											$('.tree',t).tree({
												"checkFn":function(){},
												"asynFn":function(){},
												"finded":true,
												autolevel:obj.depIds.toString().length===0?1:0,
												"selectedData":null,
												file:'file', //folder_expandable, file
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
