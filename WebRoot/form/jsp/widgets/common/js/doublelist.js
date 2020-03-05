function addTr(src){
	//获取table
	var tbl=getParentNode(src,'TABLE');
	var templateTr=tbl.rows[1];
   	var newTr=tbl.insertRow(tbl.rows.length);
	for(var i=0;i<templateTr.cells.length;i++){
		var newTd=newTr.insertCell(i);
		newTd.innerHTML=templateTr.cells[i].innerHTML;
	    newTd.style.cssText=templateTr.cells[i].style.cssText;
	}
	return newTr;	
};
function delTr(src,notdeleteindex){
	//获取tr
	var tr=getParentNode(src,'TR');
	//获取table
	var tbl=getParentNode(src,'TABLE');
	if(tr&&tbl&&notdeleteindex){
		if(tbl.rows.length>notdeleteindex){
			tr.parentNode.removeChild(tr);
		};
	};
};
function getParentNode(childnode,ptagname){
	var p=childnode;
	while(p.tagName.toUpperCase()!=ptagname){
		p=p.parentNode;
	};
	return p;
};

function tagvalues(listValues,selects,limitValue,valuess,instanceId){
		try{
			/*动态展现多列表数据,并设置查看权限 111111111111111111*/
			//var listStr='<%=request.getParameter("listValues")==null?"":request.getParameter("listValues")%>';
			//有数据
			var listStr=listValues;
			//无数据
			//var listStr='[{"jl_xz":"","jl_gzdd":"","jl_gzsj":"","jl_zw":""},{"ry_xm":"","ry_nl":"","ry_sr":""}]';
			if(listStr!=''){
				var namelist=new Array();//记录多个列表的包含的某个标签的name(可以反向找到该列表)
				var jsobj=eval('('+listStr+')');
				for(var i=0;i<jsobj.length;i++){//拆分多列表
					var defaultNotDelIndex=3;//默认不可删除的行数
				var obj=jsobj[i];
				var len=null;
				var name=null;
				//循环获取行数
				for(var o in obj){
					name=o;
					len=obj[o].length;
					namelist.push(o);
					break;
				};
				//重写模板行删除行方法，加入默认不可删除行数权限的参数
				var tempobj=document.getElementsByName(name)[0];
				var tr=getParentNode(tempobj,'TR');
				if(len!=null)defaultNotDelIndex+=len;
				tr.cells[tr.cells.length-1].innerHTML='<input type="button"  value="删除行" onclick="delTr(this,'+defaultNotDelIndex+')"/>';
				//列表新增行
				if(name&&len){
					var tempobjbak=document.getElementsByName(name)[0];
					for(var j=0;j<len;j++){ 
						var trr=addTr(tempobjbak);
						//新增行没有删除权限
						trr.cells[trr.cells.length-1].innerHTML='';
					}
				};
				//列表标签赋值
				for(var ob in obj){
					var values=obj[ob];
					var objvs=document.getElementsByName(ob);
					if(values&&objvs){
						for(var jv=1;jv<objvs.length;jv++){//有一行是隐藏域
							objvs[jv].value=values[jv-1];  
						objvs[jv].readOnly=true;//历史数据只读
						};
					};
					
				};
				};
				//给每个列表新增一空行，用于填写新数据
				for(var iv=0;iv<namelist.length;iv++){
					addTr(document.getElementsByName(namelist[iv])[0]);
				}
			};
		}catch(e){
			//alert(e);
		};
		
		//下拉框对应字典表自动赋值 222222222
		try{
			var selectstr=selects;
			var jsobjs=eval('('+selectstr+')'); 
			if(jsobjs&&jsobjs.length>0){
				for(var n=0;n<jsobjs.length;n++){
					var tagtype=jsobjs[n].m.formtagtype;
					var tagname=jsobjs[n].m.formtagname;
					var keys=jsobjs[n].diclist[0].vc_key.split(/,/g);
					var selValues=jsobjs[n].diclist[0].vc_value.split(/,/g);
					var objse=document.getElementsByName(tagname);
					for(var jj=0;jj<objse.length;jj++){
						var t=objse[jj].tagName.toLowerCase();
						if(t=='select'){
							for(var k=0;k<keys.length;k++){
								objse[jj].options.add(new Option(keys[k],selValues[k])); 
							};
						};
					};
				};
			};
		}catch(e){
			//alert(e);
		};  
		
		//标签赋值 333333333333
		if(valuess&&valuess!='null'&&valuess!=''){
			var tagValues=valuess.split(/;/);
			if(tagValues){
				for(var m=0;m<tagValues.length;m++){
					var v=tagValues[m].split(/:/);
					if(v&&v.length>1){
						var os=document.getElementsByName(v[0]);
						if(os){
							for(var ks=0;ks<os.length;ks++){
								if(os[ks].tagName){
									var tagnameSec=os[ks].tagName.toLowerCase();
								}
								//alert(tagnameSec);
								if(tagnameSec=='input'||tagnameSec=='select'){
									if(v[1].indexOf("^")>0){ //--checkbox赋值---  
										var vals = v[1].split("^");
										for(var val=0;val < vals.length;val++){
											if(vals[val] == os[ks].value){
												os[ks].checked = true; 
											}
										}
									}else if(v[1] == os[ks].value){ //--radio赋值---
										os[ks].checked = true;
									}
									os[ks].value=v[1];
								}else if(tagnameSec=='textarea'){
									os[ks].innerHTML=v[1];
								}
							}
						}
					}
				}
			}
		}
		
		//标签许可 44444444444
		if(limitValue&&limitValue!='null'&&limitValue!=''){
			var limVals = limitValue.split(/;/);
			for(var jk=0;jk<limVals.length;jk++){
				var childVal = limVals[jk].split(/:/);
				var tagN     = childVal[0];
				var limit    = childVal[1].split(/,/)[0];
				var typeVal  = childVal[1].split(/,/)[1];
				if(typeVal == 'text' || typeVal == 'textarea'){
					if(document.getElementById(tagN)){
						if(limit == 0){
							document.getElementById(tagN).style.display="none";
							if(tagN == 'xtoname'){
								if(document.getElementById("selectXTO")){
									document.getElementById("selectXTO").style.display="none";
								}
							}
							if(tagN == 'xccname'){
								if(document.getElementById("selectXCC")){
									document.getElementById("selectXCC").style.display="none";
								}
							}
						}else if(limit == 1){
							if(document.getElementById(tagN).className=="Wdate"){
								document.getElementById(tagN).onclick="";
								document.getElementById(tagN).readOnly=true;
							}else{
								document.getElementById(tagN).readOnly=true;
							}
							if(tagN == 'xtoname'){
								if(document.getElementById("selectXTO")){
									document.getElementById("selectXTO").style.display="none";
								}
							}
							if(tagN == 'xccname'){
								if(document.getElementById("selectXCC")){
									document.getElementById("selectXCC").style.display="none";
								}
							}
						}
					}
				}else if(typeVal == 'select'){
					if(document.getElementById(tagN)){
						if(limit == 0){
							document.getElementById(tagN).style.display="none";
						}else if(limit == 1){
							var sel = document.getElementById(tagN);
							sel.onclick = function(){
								var index = this.selectedIndex;
								this.onchange = function(){
									this.selectedIndex = index;
								};
							};
						}
					}
				}else if(typeVal == 'comment'){
					if(document.getElementById(instanceId+tagN+"luru")){
						if(limit == 0){
							document.getElementById(instanceId+tagN).style.display="none";
						}else if(limit == 1){
							document.getElementById(instanceId+tagN+"luru").style.display="none";
							if(document.getElementById("sh")){
								if(tagN=='nbyj'){
									document.getElementById("sh").style.display="none";
								}
							}
							if(document.getElementById(instanceId+tagN+"handwrite")){
								document.getElementById(instanceId+tagN+"handwrite").style.display="none";
							}
							if(document.getElementById(instanceId+tagN+"haveread")){
								document.getElementById(instanceId+tagN+"haveread").style.display="none";
							}
						}
					}
				}else if(typeVal == 'radio'){
						if(limit == 0){
							document.getElementById(tagN+"_div").style.display="none";
						}else if(limit == 1){
							var rad = document.getElementsByName(tagN);
							for(var is=0;is<rad.length;is++){
								rad[is].disabled = true;
							}
						}
				}else if(typeVal == 'checkbox'){
					if(limit == 0){
						if(document.getElementById(tagN+"_div")){
							document.getElementById(tagN+"_div").style.display="none";
						}
					}else if(limit == 1){
						var checkboxs = document.getElementsByName(tagN);
						for(var isCheck=0;isCheck<checkboxs.length;isCheck++){
							checkboxs[isCheck].disabled = true;
						}
					}
				}else if(typeVal == 'attachment'){
					if(limit == 0){
						if(document.getElementById(tagN+"show")){
							document.getElementById(tagN+"show").style.display="none";
						}
						if(document.getElementById(instanceId+tagN+"_upload")){
							document.getElementById(instanceId+tagN+"_upload").style.display="none";
						}
					}else if(limit == 1){
						if(document.getElementById(instanceId+tagN+"_upload")){
							document.getElementById(instanceId+tagN+"_upload").style.display="none";
						}
						if(document.getElementById(instanceId+tagN+"attachmentDel")){
							var attachments = document.getElementsByName(instanceId+tagN+"attachmentDel");
							for(var i = 0 ; i < attachments.length ; i++){
								attachments[i].style.display="none";
							}
						}
					}
				}else if(typeVal == 'wh'){
					if(limit == 0){
						//---------------发文单文号------------
						if(document.getElementById("wh")){
							document.getElementById("wh").style.display="none";
						}
						if(document.getElementById("exteriordn")){
						    document.getElementById("exteriordn").style.display="none";
						}
						//---------------办文单文号------------
						if(document.getElementById("wenhaos")){
							document.getElementById("wenhaos").style.display="none";
						}
						if(document.getElementById("exteriordn_tagid_zhu")){
							document.getElementById("exteriordn_tagid_zhu").style.display="none";
						}
					}else if(limit == 1){
						//---------------发文单文号------------
						if(document.getElementById("wh")){
							document.getElementById("wh").readOnly=true;
						}
						if(document.getElementById("exteriordn")){
							document.getElementById("exteriordn").style.display="none";
						}
						//---------------办文单文号------------
						if(document.getElementById("wenhaos")){
							document.getElementById("wenhaos").readOnly=true;
						}
						if(document.getElementById("exteriordn_tagid_zhu")){
							document.getElementById("exteriordn_tagid_zhu").style.display="none";
						}
					}
				}
			}
		}
};