<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include  file="/common/header.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Pragma" content="no-cache">
    <title>个人办公用语</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/widgets/component/taglib/comment/css/table_common.css" />
    <script type="text/javascript" src="${ctx}/widgets/component/taglib/comment/js/table_common.js">
    </script>
    <script type="text/javascript" src="${ctx}/widgets/plugin/js/common/displayTag_page.js?topages=${pages}">
    </script>
	<script type="text/javascript" src="${ctx}/widgets/plugin/js/base/jquery.js"></script>
    <script type="text/javascript">
        function update(cmnt_id){
            var url = "comment_toUpdate.do?cmnt_id=" + cmnt_id + "&d=" + Math.random();
            var result = window.showModalDialog(url, window, "dialogWidth:400px; dialogHeight:150px; center:yes; help:no; status:no; resizable:yes;");
            if (result == 1) {
                ref();
            }
        }
		
		function deletePC(cmnt_id){
			$.ajax({
				async: true,
				type: "POST",
				cache: false,
				url: "comment_deletePC.do?cmnt_id=" + cmnt_id,
				error: function(){
			   		alert('AJAX调用错误');
				},
				success: function(msg){
					alert("删除成功");
					ref();
				}
			});
        }
        
        function ref(){
            if (document.all) { //IE中利用超链接刷新网页对话框,FF不支持
                //document.getElementById('submit_a').href=window.location+'';//如出现缓存，请使用下面那行万能代码代替
                document.getElementById('submit_a').href = subUrl1(window.location);
                document.getElementById('submit_a').click();
            }
            else {//非IE(Opera除外,它不支持网页对话框)
                window.location.reload();//如出现缓存，请使用下面那行万能代码代替
                //window.location=subUrl(window.location);
            }
        };
        
        function subUrl1(url){
            var newurl = '';
            url += '';
            if (url.search(/god_d=/) > -1) {//有god_d参数
                if (url.search(/god_d=.*&/) > -1) {//god_d参数后有其它参数
                    newurl = url.replace(/god_d=.*&/, 'god_d=' + Math.random() + '&');
                }
                else {//god_d参数后无其它参数
                    newurl = url.substr(0, url.indexOf("god_d=")) + "god_d=" + Math.random();
                }
            }
            else {
                if (url.search(/\?.+/) > -1) {//?后有字符
                    newurl = url + "&god_d=" + Math.random();
                }
                else {
                    if (url.search(/\?$/) > -1) {//?后无字符
                        newurl = url + "god_d=" + Math.random();
                    }
                    else {
                        if (url.search(/\?/) == -1) {//无?
                            newurl = url + "?god_d=" + Math.random();
                        }
                    }
                }
            }
            return newurl;
        }
    </script>
	
    <base target="_self">
</head>
<body>
    <h1>个人办公用语</h1>
    <display:table htmlId="displayTable" id="element" name="personalComments" class="tbl-main" cellspacing="0" cellpadding="0" partialList="false" export="false" size="size" excludedParams="_chk">
        <display:column style="width:50px" property="sort_index" title="排序" sortable="false"/>
        <display:column style="width:400px" property="content" title="内容" sortable="false"/>
        <display:column style="width:100px" title="操作" sortable="false">
            <a href="javascript:update('${element.cmnt_id}')">编辑</a>
            <a href="javascript:deletePC('${element.cmnt_id}')" onclick="return confirm('是否确认删除？');">删除</a>
        </display:column>
    </display:table>
    <div align="center">
        <input type="button" value="新建" onclick="update()">
		<input type="button" value="关闭" onclick="window.close();">
    </div>
	<a id="submit_a" href="#"></a>
</body>
</html>
