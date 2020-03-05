function choosePersonGroup(nameType,idType){
	$("#"+nameType).blur();
    var visibleWidth = document.body.clientWidth;
    var visibleHeight = document.body.clientHeight;
    var width;
    var height;
    if(visibleWidth>800){
    	width = 800;
    }else{
    	width = visibleWidth-50;
    }
    if(visibleHeight>560){
    	height = 560;
    }else{
    	height = visibleHeight-50;
    }
   	 var nameValue = $("#"+nameType).val();
     var idValue = $("#"+idType).val();
     
     var value = new Array();
     
     if(nameValue){
     	var nameValues = nameValue.split(";");
     	var idValues = idValue.split(";");
     	for(var x in nameValues){
     		if(nameValues[x]){
     			var person = {};
         		person.name = nameValues[x];
         		person.id = idValues[x];
         		value.push(person);
     		}
     	}
     }
     
     //-------该方法供openTreeAndPassValues回调
     //（array）勾选的人员数组[{name:,id:},{name:,id:}]
     var setJsonToArea = function(array){
    	//-----自定义--------------------------
     	var nameValues="";
     	var idValues="";
     	$(array).each(function(i,e){
     		nameValues += e.name+";";
     		idValues += e.id+";";
     	});
     	nameValues = nameValues.substring(0,nameValues.length-1);
     	idValues = idValues.substring(0,idValues.length-1);
     	$("#"+nameType).val(nameValues);
     	$("#"+idType).val(idValues);
     	//-----自定义--------------------------
    };
    
    //-------传入参数，打开人员树
    //（width,height）layer宽高
    //（value）需要在人员树中默认勾选的人员数组[{name:,id:},{name:,id:}]
    //（setJsonToArea）本页面的回调方法，将勾选的人员传入这个方法处理
    openTreeAndPassValues(width,height,value,setJsonToArea);
    //-------传入参数，打开人员树
}