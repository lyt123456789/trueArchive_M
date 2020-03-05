/*传递人员树的值  依赖layer来打开人员树选择界面*/
		/*function openTreeAndPassValues(width,height,jsonToSet,callback){
		    layer.open({
		        title: '选择人员',
		        area: [width+'px', height+'px'],
		        type: 2,
		        maxmin:true,
		        scrollbar:false,
		        content: '${ctx}/departmentTree_showDepartmentTree.do?t='+new Date(),
		        success:function(layero,index){//页面加载完成后，将本页面中已经选中的人员带入到人员树页面中去
		        	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
		        	if(jsonToSet&&jsonToSet.length!=0){
		            	iframeWin.getTreeData(jsonToSet);
		            }else{
		            	iframeWin.setTheSelectedPersonToTree(new Array());
		            }
		        },
		        btn:['确认选择','取消'],
		        yes:function(index,layero){
		        	var value = new Array();
		        	//当点击‘确定’按钮的时候，获取弹出层返回的值
		        	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
		        	value=iframeWin.getValue();
		           	if(value.length!=0){
		           		callback(value);
		           		layer.close(index);
		           	}else{
		           		layer.msg("还未选择人员",{time:1000,icon:0},function(){
		           			callback(value);
		               		layer.close(index);
		           		});
		           	}
		        },
		        btn2:function(index,layero){
		        	layer.close(index);
		        },
				cancel:function(){
				}        
			});
		}*/

		
///*传递人员树的值  依赖layer来打开人员树选择界面*/
function openTreeAndPassValues(width,height,jsonToSet,callback){
    layer.open({
        title: '选择人员',
        area: [width+'px', height+'px'],
        type: 2,
        maxmin:true,
        scrollbar:false,
        content: '${ctx}/tree_showDepartmentTree.do?t='+new Date(),
        success:function(layero,index){//页面加载完成后，将本页面中已经选中的人员带入到人员树页面中去
        	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
            if(jsonToSet&&jsonToSet.length!=0){
            	iframeWin.setTheSelectedPersonToTree(jsonToSet);
            }else{
            	iframeWin.setTheSelectedPersonToTree(new Array());
            }
        },
        btn:['确认选择','取消'],
        yes:function(index,layero){
        	var value = new Array();
           	var iframeWin = window[layero.find('iframe')[0]['name']];//得到打开的layer窗口中iframe的名字
           	value = iframeWin.getJsonOfTheSelectedPerson();
           	if(value.length!=0){
           		callback(value);
           		layer.close(index);
           	}else{
           		layer.msg("还未选择人员",{time:1000,icon:0},function(){
           			callback(value);
               		layer.close(index);
           		});
           	}
        },
        btn2:function(index,layero){
        	layer.close(index);
        },
		cancel:function(){
		}        
	});
}

