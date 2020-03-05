<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/header.jsp" %>
<html>
    <head>
        <title>固定人员选择页面</title>
        <meta http-equiv="Expires" CONTENT="0">
        <meta http-equiv="Cache-Control" CONTENT="no-cache">
        <meta http-equiv="Pragma" CONTENT="no-cache">
        <script src="${ctx}/widgets/plugin/js/base/jquery.js" type="text/javascript"></script>
        <script src="${ctx}/widgets/plugin/js/tree/jquery.tree.js" type="text/javascript"></script>
        <script type="text/javascript">
        	String.prototype.endWith = function(str){
				if(str==null || str=="" || this.length==0 || str.length>this.length)
				  return false;
				if(this.substring(this.length-str.length)==str)
				  return true;
				else
				  return false;
				return true;
			}
			
			var obj = window.dialogArguments;
			// JSON字符串
		    var treedata= ${treedata};
	        $(document).ready(function() {
	            var o = { showcheck: true,
						  url:"${ctx}/selectTree_getChildData.do",
	                      cbiconpath: "${ctx}/widgets/theme/dm/common/images/icons/"
	            };
	            o.data = treedata;
	            $("#tree").treeview(o);
	            $("#showchecked").click(function(e) {
	                var s = $("#tree").getTSVs();
					if (s == "" || s.length == 0 || s.length != 1){
	                    alert("固定人员有且只有一个！");
	                    return;
	                }
					if(s[0].endWith('d')){
						alert("固定人员不能是单位！");
	                    return;
					}
					var userName = $("#tree").getTSTs();
					var obj = new Object();
					obj.userId  = s;
					obj.userName = userName;
					window.returnValue = obj;
					window.close();
	            });
	          });
        </script>
    </head>
    <body class='ie'  >
        <div style="margin-top: 10px;margin-bottom: 5px;" align="center">
            <button id="showchecked">设定</button>
            <button style="margin-left: 10px;" onclick="window.returnValue = 'noChange';window.close();">取消</button>
        </div>
        <div style="border-left: 200px;">
            <div id="tree"></div>
        </div>
    </body>
</html>
