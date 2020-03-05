/**
 * 文件名称:ExportExcel.java
 * 作者:WangXF<br>
 * 创建时间:2011-12-16 下午05:03:02
 * CopyRight (c)2009-2011:江苏中威科技软件系统有限公司<br>
 */
package cn.com.trueway.base.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 描述：生成Excel表格的工具类，提供导出简单的Excel表格方法（实现框架:JXL）
 * 作者：WangXF<br>
 * 创建时间：2011-12-16 下午05:03:02<br>
 * 修改人：<修改人中文名或拼音缩写><br>
 * 修改时间：<修改日期，格式：同创建时间><br>
 * 修改原因及地方：<修改原因描述><br>
 * 版本：v1.0<br>
 * JDK版本：JDK1.5<br>
 */
public class ExcelExport {
	
	//工作薄对象
	private WritableWorkbook wbook;
	
	//工作表对象
	private WritableSheet wsheet; 

	//工作表中标题行
	private String biaoti;
	//工作表中标题列数
	private Integer allCelNum;
	
	//工作表中标题行
	private String[] titles;
	
	//显示工作表内容的数据源
	private List<String[]> dataSource;
	
	//标题的单元格格式（包括标题字体样式）
	private WritableCellFormat titleCellFormat;
	
	//内容的单元格格式（包括内容字体样式）
	private WritableCellFormat contentCellFormat;
	
	private static ServletOutputStream ops=null;
	
	//构造方法
	private ExcelExport(String excelName,HttpServletResponse response){
		try {
			if(excelName==null||"".equals(excelName)){
				throw new Exception("excelName is null or empty");
			}
			response.setContentType("application/vnd.ms-excel");
			String sheetName=excelName;
			excelName = URLEncoder.encode(excelName, "GB2312");
			excelName = URLDecoder.decode(excelName, "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename="+excelName+".xls");
			ops = response.getOutputStream();
			wbook = Workbook.createWorkbook(ops);
			wsheet=wbook.createSheet(sheetName, 0);
			titleCellFormat = getWritableCellFormat(this.setContentFont("宋体", 14, true, false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK), Alignment.CENTRE, VerticalAlignment.CENTRE, false);
			contentCellFormat = getWritableCellFormat(this.setContentFont("宋体", 12, false, false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK), Alignment.CENTRE, VerticalAlignment.CENTRE, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public WritableWorkbook getWbook() {
		return wbook;
	}

	public WritableSheet getWsheet() {
		return wsheet;
	}

	/**
	 * 
	 * 描述：设置工作表标题
	 *
	 * @param titles void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 上午10:28:50
	 */
	public void setTitles(String[] titles) {
		this.titles = titles;
	}
	


	public void setBiaoti(String biaoti) {
		this.biaoti = biaoti;
	}

	/**
	 * 
	 * 描述：设置工作表内容的数据源
	 *
	 * @param dataSource void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 上午10:29:00
	 */
	public void setDataSource(List<String[]> dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 描述：设置标题字体样式，并返回标题字体样式对象
	 *
	 * @param fontName	字体名称，默认宋体
	 * @param size		字体大小，默认14
	 * @param isBold	是否加粗，默认加粗
	 * @param isItalic	是否斜体，默认非斜体
	 * @param underlingStyle 下划线样式，默认为UnderlineStyle.NO_UNDERLINE
	 * @param colour 字段颜色，默认黑色
	 * @return WritableFont 
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 上午10:29:09
	 */
	public WritableFont setTitleFont(String fontName,int size,boolean isBold,boolean isItalic,UnderlineStyle underlingStyle,Colour colour){
		WritableFont wFont = getWritableFont(fontName, size, isBold, isItalic, underlingStyle, colour);
		return wFont;
	}
	
	/**
	 * 
	 * 描述：设置标题单元格格式，并返回标题单元格格式对象
	 *
	 * @param wFont	标题字体样式对象
	 * @param alignment	单元格左右对齐方式，默认左右居中
	 * @param verticalAlignment 单元格上下对齐方式，默认上下居中
	 * @param wrap	单元格是否自动换行，默认不自动换行
	 * @return WritableCellFormat
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 上午10:34:30
	 */
	public WritableCellFormat setTitleFormat(WritableFont wFont,Alignment alignment,VerticalAlignment verticalAlignment,boolean wrap){
		WritableCellFormat wFormat = getWritableCellFormat(wFont, alignment, verticalAlignment, wrap);
		titleCellFormat=wFormat;
		return titleCellFormat;
	}
	
	/**
	 * 描述：设置内容字体样式，并返回内容字体样式对象
	 *
	 * @param fontName	字体名称，默认宋体
	 * @param size		字体大小，默认12
	 * @param isBold	是否加粗，默认不加粗
	 * @param isItalic	是否斜体，默认非斜体
	 * @param underlingStyle 下划线样式，默认为UnderlineStyle.NO_UNDERLINE
	 * @param colour 字段颜色，默认黑色
	 * @return WritableFont 
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 上午10:36:09
	 */
	public WritableFont setContentFont(String fontName,int size,boolean isBold,boolean isItalic,UnderlineStyle underlingStyle,Colour colour){
		WritableFont wFont = getWritableFont(fontName, size, isBold, isItalic, underlingStyle, colour);
		return wFont;
	}
	
	public void setAllCelNum(Integer allCelNum) {
		this.allCelNum = allCelNum;
	}

	/**
	 * 
	 * 描述：设置内容单元格格式，并返回内容单元格格式对象
	 *
	 * @param wFont	标题字体样式对象
	 * @param alignment	单元格左右对齐方式，默认左右居中
	 * @param verticalAlignment 单元格上下对齐方式，默认上下居中
	 * @param wrap	单元格是否自动换行，默认不自动换行
	 * @return WritableCellFormat
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 上午10:37:59
	 */
	public WritableCellFormat setContentFormat(WritableFont wFont,Alignment alignment,VerticalAlignment verticalAlignment,boolean wrap){
		WritableCellFormat wFormat = getWritableCellFormat(wFont, alignment, verticalAlignment, wrap);
		contentCellFormat=wFormat;
		return contentCellFormat;
	}

	/**
	 * 
	 * 描述：设置工作表的列宽
	 *
	 * @param columnIndex 第几列，从1开始
	 * @param size void	 列宽大小
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 上午10:39:58
	 */
	public void setColWidth(int columnIndex,int size){
		try {
			if(columnIndex<1){
				throw new Exception("colNum:"+columnIndex+" is not allow");
			}
			if(size<1){
				throw new Exception("size:"+size+" is not allow");
			}
			wsheet.setColumnView(columnIndex-1, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 描述：设置工作表的行高
	 *
	 * @param rowIndex 第几行，从1开始
	 * @param size void 行高大小
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 上午10:40:44
	 */
	public void setRowHight(int rowIndex,int size){
		try {
			if(rowIndex<1){
				throw new Exception("colNum:"+rowIndex+" is not allow");
			}
			if(size<1){
				throw new Exception("size:"+size+" is not allow");
			}
			wsheet.setRowView(rowIndex-1, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 得到字体样式对象
	 * 
	 * @param fontName 字体名称
	 * @param size 字体大小
	 * @param isBold 是否加粗
	 * @param isItalic 是否斜体
	 * @param underlingStyle 下划线样式
	 * @param colour 字体颜色
	 * @return
	 */
	private WritableFont getWritableFont(String fontName,int size,boolean isBold,boolean isItalic,UnderlineStyle underlineStyle,Colour colour){
		WritableFont Wfont = new WritableFont(WritableFont.createFont(fontName));
		try {
			if(size<=0){
				throw new Exception("size:"+size+" is not allow");
			}
			Wfont.setPointSize(size);
			if(isBold){
				Wfont.setBoldStyle(WritableFont.BOLD);
			}else{
				Wfont.setBoldStyle(WritableFont.NO_BOLD);
			}
			Wfont.setItalic(isItalic);
			if(underlineStyle!=null){
				Wfont.setUnderlineStyle(underlineStyle);
			}
			if(colour!=null){
				Wfont.setColour(colour);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Wfont;
	}
	
	/**
	 * 得到单元格格式对象
	 * @param wFont 字体
	 * @param alignment 左右居中
	 * @param verticalAlignment 上下居中
	 * @param wrap 是否自动换行
	 * @return
	 */
	private WritableCellFormat getWritableCellFormat(WritableFont wFont,Alignment alignment,VerticalAlignment verticalAlignment,boolean wrap){
		WritableCellFormat wFormat = new WritableCellFormat(wFont);
		try {
			if(wFont==null){
				throw new Exception("WritableFont:"+wFont+" is not allow");
			}
			if(alignment!=null){
				wFormat.setAlignment(alignment);
			}
			if(verticalAlignment!=null){
				wFormat.setVerticalAlignment(verticalAlignment);
			}
			wFormat.setWrap(wrap);
			wFormat.setBorder(Border.ALL, BorderLineStyle.THIN);//默认有表格边框
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wFormat;
	}
	
	/**
	 * 
	 * 描述：得到该工具类实例
	 *
	 * @param excelName
	 * @param response
	 * @return ExcelExportUtil
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 上午10:25:07
	 */
	public static ExcelExport getExcelExportInstance(String excelName,HttpServletResponse response){
		return new ExcelExport(excelName,response);
	}
	
	/**
	 * 
	 * 描述：导出操作
	 * void
	 *
	 * 作者:WangXF<br>
	 * 创建时间:2011-12-17 上午10:17:53
	 */
	public void export(){
		//行号索引
		int rowIndex=0;
		try {
			
			if(biaoti!=null){
				Label label = new Label(0, rowIndex, biaoti, titleCellFormat);
				wsheet.addCell(label);
				if(CommonUtil.stringNotNULL(allCelNum+"")){
					wsheet.mergeCells(0, 0, allCelNum , 0);
				}
				
				rowIndex++;
			}
			if(titles!=null){
				//将标题写稿到工作表
				for (int i = 0; i < titles.length; i++) {
					Label label = new Label(i, rowIndex, titles[i], titleCellFormat);
					wsheet.addCell(label);
				}
				rowIndex++;
			}
			if(dataSource!=null){
				//将内容写稿到工作表
				for(String[] s:dataSource){
					for (int i = 0; i < s.length; i++) {
						Label label = new Label(i, rowIndex, s[i], contentCellFormat);
						wsheet.addCell(label);
					}
					rowIndex++;
				}
			}
			wbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(wbook!=null){
				try {wbook.close();}catch (Exception e){};
			}
			if(ops!=null){
				try {
					ops.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
