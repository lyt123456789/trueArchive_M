/**
 * 文件名称:DocStringDemo.java
 * 作者:Lv☂<br>
 * 创建时间:2013-9-23 下午05:03:41
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.document.business.util;

import cn.com.trueway.base.util.UUID36GeneratorUtil;
import cn.com.trueway.base.util.UuidGenerator;

/**
 * xml示例
 */
public class DocXmlDemo {

	public static String toBeReceiveList(){
		String str = "<?xml version=\"1.0\" encoding=\"gb2312\"?><ReturnInfo>"
			+"<PaperList>"
			+"<Paper>"
			+"<SysGUID>公文GUID</SysGUID>"
			+"<ArchiveID_Send>文号</ArchiveID_Send>"
			+"<Title>标题</Title>"
			+"<SendTime>2013-10-28 12:01:01</SendTime>"
			+"<SendDeptName>发文部门名称</SendDeptName>"
			+"<DeptName>收文部门名称</DeptName>"
			+"<MainGive>主送机关</MainGive>"
			+"<SubGive>抄送机关</SubGive>"
			+"<MainDo>主办机关</MainDo>"
			+"<SubDo>协办机关</SubDo>"
			+"<Archives_Send_Dept_SysGUID>预留字段1"
			+"</Archives_Send_Dept_SysGUID>"
			+"<Archives_Send_Dept_DeptGUID>预留字段2"
			+"</Archives_Send_Dept_DeptGUID>"
			+"<Archives_Send_Dept_Status>预留字段3"
			+"</Archives_Send_Dept_Status>"
			+"<Archives_Send_Dept_IsConfirm>预留字段4"
			+"</Archives_Send_Dept_IsConfirm>"
			+"</Paper>"  
			+"<Paper>"
			+"<SysGUID>公文GUID2</SysGUID>"
			+"<ArchiveID_Send>文号2</ArchiveID_Send>"
			+"<Title>标题2</Title>"
			+"<SendTime>2013-10-28 12:11:11</SendTime>"
			+"<SendDeptName>发文部门名称2</SendDeptName>"
			+"<DeptName>收文部门名称2</DeptName>"
			+"<MainGive>主送机关2</MainGive>"
			+"<SubGive>抄送机关2</SubGive>"
			+"<MainDo>主办机关2</MainDo>"
			+"<SubDo>协办机关2</SubDo>"
			+"<Archives_Send_Dept_SysGUID>2预留字段1"
			+"</Archives_Send_Dept_SysGUID>"
			+"<Archives_Send_Dept_DeptGUID>2预留字段2"
			+"</Archives_Send_Dept_DeptGUID>"
			+"<Archives_Send_Dept_Status>2预留字段3"
			+"</Archives_Send_Dept_Status>"
			+"<Archives_Send_Dept_IsConfirm>2预留字段4"
			+"</Archives_Send_Dept_IsConfirm>"
			+"</Paper>"
			+"</PaperList>"
			+"</ReturnInfo>";
		
		return str;
	}
	
	public static String receiveDoc(){
		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?><EpointDataBody><DATA><ReturnInfo><Status>True</Status><Description></Description></ReturnInfo><UserArea>" +
				"<电子公文 公文标识=\""+UuidGenerator.generate36UUID()+"\" 版本号=\"1.0\" 公文类别=\"行政\" 公文种类=\"通知\">"
		
		+"<发文机关 组织机构代码=\"xxxxx\" 办理类别=\"主办\">苏州市政府办公室</发文机关>"
		+"<发文机关 组织机构代码=\"xxxxx\" 办理类别=\"协办\">苏州市市委办公室</发文机关>"
		+"<公文体>"
		+"	<眉首>"
		+"<秘密等级>普通/秘密/机密/绝密</秘密等级>"
		+"<保密期限>1年</保密期限>"
		+"<紧急程度>普通/较急/紧急</紧急程度>"
		+"<发文机关标识>"
		+"<发文机关名称>苏州市政府办公室</发文机关名称>"
		+"<发文机关名称>苏州市委公室</发文机关名称>"
		+"<标识后缀>文件</标识后缀>"
		+"</发文机关标识>"
		+"<发文字号>"
		+"<发文机关代字>苏府办</发文机关代字>"
		+"<发文年号>2002</发文年号>"
		+"<发文序号>9999号</发文序号>"
		+"</发文字号>"
		+"</眉首>"
		+"	<主体>"
		+"		<标题>苏州市政府办公室关于xxxxxxxx的通知</标题>"
		+"		<是否回复>true/false</是否回复>"
		+"		<关联收文>当前已收公文对应的发文编号(平台分配的GUID)</关联收文>"
		+"		<发文号>苏府办[2002]9999号</发文号>"
//		+"		<主送机关>各有关单位</主送机关>"
		+"		<发文部门内码>81f67746-3d8b-48fa-bcf9-42e389ca45ac</发文部门内码>"
		+"		<发文部门名称>环境保护局</发文部门名称>"
		+"		<收文部门名称></收文部门名称>"
		+"<正文>"
		+"	<版式文件 文件名=\"通知.cer\" 数据类型=\"\" 编码方式=\"BASE64\">MIIFATCCA+mgAwIBAgIKEYtdrwAAAAACYjANBgkqhkiG9w0BAQUFADBHMRUwEwYKCZImiZPyLGQBGRYFbG9jYWwxHTAbBgoJkiaJk/IsZAEZFg1ndW90YWl4aW5kaWFuMQ8wDQYDVQQDEwZFUE9JTlQwHhcNMTAwNDIxMDcwMjM0WhcNMTEwNDIxMDcxMjM0WjAPMQ0wCwYDVQQDEwR6NjEwMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxzX1Hpfzh50gpIxzkvvoZarm0JitDvx4CDJPXjUXC+26FHhznSC5xpAOnoxXbrFVOrF4FNVf2pk2R0T7b7Wr8UXtp5AVzLo4DcQlUCVrzle8ykKhjalZTfTyqxDVSHYf20J0JldZ8G98gJMRvbLb7ICCe/TNksUqEy4nhP2TG2wIDAQABo4ICqTCCAqUwDgYDVR0PAQH/BAQDAgTwMEQGCSqGSIb3DQEJDwQ3MDUwDgYIKoZIhvcNAwICAgCAMA4GCCqGSIb3DQMEAgIAgDAHBgUrDgMCBzAKBggqhkiG9w0DBzAdBgNVHQ4EFgQU5AhmxDpueo5STOiepDSRwyAK/O4wEwYDVR0lBAwwCgYIKwYBBQUHAwIwHwYDVR0jBBgwFoAUt4MvKNHITRIK+c1JNze4bzOK1tkwgfYGA1UdHwSB7jCB6zCB6KCB5aCB4oaBt2xkYXA6Ly8vQ049RVBPSU5ULENOPWRrLWRmNmVhMCxDTj1DRFAsQ049UHVibGljJTIwS2V5JTIwU2VydmljZXMsQ049U2VydmljZXMsQ049Q29uZmlndXJhdGlvbixEQz1ndW90YWl4aW5kaWFuLERDPWxvY2FsP2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RDbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludIYmaHR0cDovL2RrLWRmNmVhMC9DZXJ0RW5yb2xsL0VQT0lOVC5jcmwwgf4GCCsGAQUFBwEBBIHxMIHuMIGtBggrBgEFBQcwAoaBoGxkYXA6Ly8vQ049RVBPSU5ULENOPUFJQSxDTj1QdWJsaWMlMjBLZXklMjBTZXJ2aWNlcyxDTj1TZXJ2aWNlcyxDTj1Db25maWd1cmF0aW9uLERDPWd1b3RhaXhpbmRpYW4sREM9bG9jYWw/Y0FDZXJ0aWZpY2F0ZT9iYXNlP29iamVjdENsYXNzPWNlcnRpZmljYXRpb25BdXRob3JpdHkwPAYIKwYBBQUHMAKGMGh0dHA6Ly9kay1kZjZlYTAvQ2VydEVucm9sbC9kay1kZjZlYTBfRVBPSU5ULmNydDANBgkqhkiG9w0BAQUFAAOCAQEAFQOubQzR5v0JOeuee8izhNTkrNWldxh5a8/BEiivkwfuBMg9xREz0jFMP5L3MjIthkG+Ej/1mW4bmPPtYzSsVDHtRvDOpxqgrcVfs9Lk9d0Eb+72nViJXYTpNflYZJqidS33ZKz+iByykW4EvV1qMj0woMc6eia1suKT8SStJSOBT5nskRcXiV2LBKBkAzbFOBv11OmeG64B0g7PujJme/YXFyDxtMNK/9r5fKvOC1BMeMcO9anm4Cb3AhRYtEpvnM3SeEN4lOeOphymmcE4/tV6csOCRl4rjJ5DyXzsrkDZxQcyJNWLHgt6G93m/EbpdxKBvE23HJZZQ396/YjoUw==</版式文件>"
		+"</正文>"
		+"<附件>"
		+"	<版式文件 文件名=\"附件.doc\" 数据类型=\"\" 编码方式=\"BASE64\">MIIFATCCA+mgAwIBAgIKEYtdrwAAAAACYjANBgkqhkiG9w0BAQUFADBHMRUwEwYKCZImiZPyLGQBGRYFbG9jYWwxHTAbBgoJkiaJk/IsZAEZFg1ndW90YWl4aW5kaWFuMQ8wDQYDVQQDEwZFUE9JTlQwHhcNMTAwNDIxMDcwMjM0WhcNMTEwNDIxMDcxMjM0WjAPMQ0wCwYDVQQDEwR6NjEwMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxzX1Hpfzh50gpIxzkvvoZarm0JitDvx4CDJPXjUXC+26FHhznSC5xpAOnoxXbrFVOrF4FNVf2pk2R0T7b7Wr8UXtp5AVzLo4DcQlUCVrzle8ykKhjalZTfTyqxDVSHYf20J0JldZ8G98gJMRvbLb7ICCe/TNksUqEy4nhP2TG2wIDAQABo4ICqTCCAqUwDgYDVR0PAQH/BAQDAgTwMEQGCSqGSIb3DQEJDwQ3MDUwDgYIKoZIhvcNAwICAgCAMA4GCCqGSIb3DQMEAgIAgDAHBgUrDgMCBzAKBggqhkiG9w0DBzAdBgNVHQ4EFgQU5AhmxDpueo5STOiepDSRwyAK/O4wEwYDVR0lBAwwCgYIKwYBBQUHAwIwHwYDVR0jBBgwFoAUt4MvKNHITRIK+c1JNze4bzOK1tkwgfYGA1UdHwSB7jCB6zCB6KCB5aCB4oaBt2xkYXA6Ly8vQ049RVBPSU5ULENOPWRrLWRmNmVhMCxDTj1DRFAsQ049UHVibGljJTIwS2V5JTIwU2VydmljZXMsQ049U2VydmljZXMsQ049Q29uZmlndXJhdGlvbixEQz1ndW90YWl4aW5kaWFuLERDPWxvY2FsP2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RDbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludIYmaHR0cDovL2RrLWRmNmVhMC9DZXJ0RW5yb2xsL0VQT0lOVC5jcmwwgf4GCCsGAQUFBwEBBIHxMIHuMIGtBggrBgEFBQcwAoaBoGxkYXA6Ly8vQ049RVBPSU5ULENOPUFJQSxDTj1QdWJsaWMlMjBLZXklMjBTZXJ2aWNlcyxDTj1TZXJ2aWNlcyxDTj1Db25maWd1cmF0aW9uLERDPWd1b3RhaXhpbmRpYW4sREM9bG9jYWw/Y0FDZXJ0aWZpY2F0ZT9iYXNlP29iamVjdENsYXNzPWNlcnRpZmljYXRpb25BdXRob3JpdHkwPAYIKwYBBQUHMAKGMGh0dHA6Ly9kay1kZjZlYTAvQ2VydEVucm9sbC9kay1kZjZlYTBfRVBPSU5ULmNydDANBgkqhkiG9w0BAQUFAAOCAQEAFQOubQzR5v0JOeuee8izhNTkrNWldxh5a8/BEiivkwfuBMg9xREz0jFMP5L3MjIthkG+Ej/1mW4bmPPtYzSsVDHtRvDOpxqgrcVfs9Lk9d0Eb+72nViJXYTpNflYZJqidS33ZKz+iByykW4EvV1qMj0woMc6eia1suKT8SStJSOBT5nskRcXiV2LBKBkAzbFOBv11OmeG64B0g7PujJme/YXFyDxtMNK/9r5fKvOC1BMeMcO9anm4Cb3AhRYtEpvnM3SeEN4lOeOphymmcE4/tV6csOCRl4rjJ5DyXzsrkDZxQcyJNWLHgt6G93m/EbpdxKBvE23HJZZQ396/YjoUw==</版式文件>"
		+"	<版式文件 文件名=\"附件2.doc\" 数据类型=\"\" 编码方式=\"BASE64\">MIIFATCCA+mgAwIBAgIKEYtdrwAAAAACYjANBgkqhkiG9w0BAQUFADBHMRUwEwYKCZImiZPyLGQBGRYFbG9jYWwxHTAbBgoJkiaJk/IsZAEZFg1ndW90YWl4aW5kaWFuMQ8wDQYDVQQDEwZFUE9JTlQwHhcNMTAwNDIxMDcwMjM0WhcNMTEwNDIxMDcxMjM0WjAPMQ0wCwYDVQQDEwR6NjEwMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxzX1Hpfzh50gpIxzkvvoZarm0JitDvx4CDJPXjUXC+26FHhznSC5xpAOnoxXbrFVOrF4FNVf2pk2R0T7b7Wr8UXtp5AVzLo4DcQlUCVrzle8ykKhjalZTfTyqxDVSHYf20J0JldZ8G98gJMRvbLb7ICCe/TNksUqEy4nhP2TG2wIDAQABo4ICqTCCAqUwDgYDVR0PAQH/BAQDAgTwMEQGCSqGSIb3DQEJDwQ3MDUwDgYIKoZIhvcNAwICAgCAMA4GCCqGSIb3DQMEAgIAgDAHBgUrDgMCBzAKBggqhkiG9w0DBzAdBgNVHQ4EFgQU5AhmxDpueo5STOiepDSRwyAK/O4wEwYDVR0lBAwwCgYIKwYBBQUHAwIwHwYDVR0jBBgwFoAUt4MvKNHITRIK+c1JNze4bzOK1tkwgfYGA1UdHwSB7jCB6zCB6KCB5aCB4oaBt2xkYXA6Ly8vQ049RVBPSU5ULENOPWRrLWRmNmVhMCxDTj1DRFAsQ049UHVibGljJTIwS2V5JTIwU2VydmljZXMsQ049U2VydmljZXMsQ049Q29uZmlndXJhdGlvbixEQz1ndW90YWl4aW5kaWFuLERDPWxvY2FsP2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RDbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludIYmaHR0cDovL2RrLWRmNmVhMC9DZXJ0RW5yb2xsL0VQT0lOVC5jcmwwgf4GCCsGAQUFBwEBBIHxMIHuMIGtBggrBgEFBQcwAoaBoGxkYXA6Ly8vQ049RVBPSU5ULENOPUFJQSxDTj1QdWJsaWMlMjBLZXklMjBTZXJ2aWNlcyxDTj1TZXJ2aWNlcyxDTj1Db25maWd1cmF0aW9uLERDPWd1b3RhaXhpbmRpYW4sREM9bG9jYWw/Y0FDZXJ0aWZpY2F0ZT9iYXNlP29iamVjdENsYXNzPWNlcnRpZmljYXRpb25BdXRob3JpdHkwPAYIKwYBBQUHMAKGMGh0dHA6Ly9kay1kZjZlYTAvQ2VydEVucm9sbC9kay1kZjZlYTBfRVBPSU5ULmNydDANBgkqhkiG9w0BAQUFAAOCAQEAFQOubQzR5v0JOeuee8izhNTkrNWldxh5a8/BEiivkwfuBMg9xREz0jFMP5L3MjIthkG+Ej/1mW4bmPPtYzSsVDHtRvDOpxqgrcVfs9Lk9d0Eb+72nViJXYTpNflYZJqidS33ZKz+iByykW4EvV1qMj0woMc6eia1suKT8SStJSOBT5nskRcXiV2LBKBkAzbFOBv11OmeG64B0g7PujJme/YXFyDxtMNK/9r5fKvOC1BMeMcO9anm4Cb3AhRYtEpvnM3SeEN4lOeOphymmcE4/tV6csOCRl4rjJ5DyXzsrkDZxQcyJNWLHgt6G93m/EbpdxKBvE23HJZZQ396/YjoUw==</版式文件>"
		+"</附件>"
		+"<公文生效标识>"
		+"	<发文机关署名>苏州市政府办公室</发文机关署名>"
		+"<签发人署名>杨市长</签发人署名>"
		+"	<发文机关署名>苏州市委办公室</发文机关署名>"
		+"<签发人署名>王书记</签发人署名>"
		+"</公文生效标识>"
		+"<成文日期>2003-5-20</成文日期>"
		+"<附注>xxxxxxxxxxxxx</附注>"
		+"</主体>"
		+"<版记>"
		+"	<主题词>"
		+"		<词目>通知</词目>"
		+"		<词目>公文</词目>"
		+"	</主题词>"
		+"<抄送机关 抄送类型=\"抄报\">省委、省政府</抄送机关>"
		+"<抄送机关 抄送类型=\"抄送\">各市、区政府</抄送机关>"
		+"<印制版记>"
		+"	<印发机关>苏州市政府办公室</印发机关>"
		+"	<印发日期>2003-01-01</印发日期>"
		+"	<印发份数>15</印发份数>"
		+"</印制版记>"
		+"</版记>"
		+"</公文体>"
		+"</电子公文>" +
		"</UserArea></DATA></EpointDataBody>";

		return xml;
	}
}
