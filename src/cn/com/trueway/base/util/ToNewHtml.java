package cn.com.trueway.base.util;

import java.util.*;
import java.io.*;

public class ToNewHtml {
	public static void main(String[] args) {
		try {
			String title = "测试java 生成 Html";
			String content = "字段内容";
			String editer = "作者";
			// 模板路径
			String filePath = "d:\\test.html";
			// 下面这个方法来获取文件流我还没弄明白
			// InputStream is=
			// Temp.class.getClassLoader().getResourceAsStream("whtml");
			String templateContent = "";
			FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
			// 下面四行：获得输入流的长度，然后建一个该长度的数组，然后把输入流中的数据以字节的形式读入到数组中，然后关闭流
			int lenght = fileinputstream.available();
			byte bytes[] = new byte[lenght];
			fileinputstream.read(bytes);
			fileinputstream.close();
			// 通过使用平台的默认字符集解码指定的 byte 数组，构造一个新的
			// String。然后利用字符串的replaceAll()方法进行指定字符的替换
			// 此处除了这种方法之外，应该还可以使用表达式语言${}的方法来进行。
			templateContent = new String(bytes);
			// System.out.print(templateContent);
			templateContent = templateContent.replaceAll("###title###", title);
			templateContent = templateContent.replaceAll("###content###",
					content);
			templateContent = templateContent
					.replaceAll("###author###", editer);
			// 使用平台的默认字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。
			byte tag_bytes[] = templateContent.getBytes();
			// System.out.print(templateContent);
			// 根据时间得文件名。Calendar 的 getInstance 方法返回一个 Calendar
			// 对象，其日历字段已由当前日期和时间初始化
			Calendar calendar = Calendar.getInstance();
			// valueOf()方法进行类型的转换，转换成String型
			String fileame = String.valueOf(calendar.getTimeInMillis())
					+ ".html";
			fileame = "d:\\info_" + fileame;// 生成的html文件保存路径。
			FileOutputStream fileoutputstream = new FileOutputStream(fileame);// 建立文件输出流
			fileoutputstream.write(tag_bytes);
			fileoutputstream.close();
			System.out.println("------------yes-------------");
		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}
}