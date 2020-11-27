package com.lizhen.common.util;


import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;

public class JxlExcelUtils {

	/**
	 * @author
	 * @param objData
	 *            导出内容数组
	 * @param sheetName
	 *            导出工作表的名称
	 * @param columns
	 *            导出Excel的表头数组
	 * @return
	 */
	public static String exportToExcel(HttpServletResponse response, List<LinkedHashMap<String, Object>> objData,
                                       String sheetName, List<String> columns, String filename, String path){
//		// 声明工作簿jxl.write.WritableWorkbook
//		String filePath="C:\\product\\" + filename + ".xls";
        File newfile = new File(path+filename+".xls");
		WritableWorkbook wwb;
		try {
	        if (!newfile.exists()) {
				newfile.createNewFile();
	        }
			// 根据传进来的file对象创建可写入的Excel工作薄
			OutputStream os = response.getOutputStream();
			os = new FileOutputStream(newfile);

			wwb = Workbook.createWorkbook(os);

			/*
			 * 创建一个工作表、sheetName为工作表的名称、"0"为第一个工作表
			 * 打开Excel的时候会看到左下角默认有3个sheet、"sheet1、sheet2、sheet3"这样
			 * 代码中的"0"就是sheet1、其它的一一对应。 createSheet(sheetName,
			 * 0)一个是工作表的名称，另一个是工作表在工作薄中的位置
			 */
			WritableSheet ws = wwb.createSheet(sheetName, 0);

			for (int i = 0; i < objData.size() + 1; i++) {
				ws.setRowView(i, 500);
			}
			for (int i = 0; i < columns.size() - 1; i++) {
				ws.setColumnView(i, 20);
			}
			ws.setColumnView(11, 35);
			ws.setColumnView(columns.size() - 1, 40);

			SheetSettings ss = ws.getSettings();
			ss.setVerticalFreeze(1);// 冻结表头

			WritableFont font1 = new WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.BOLD);
			// WritableFont font2 =new
			// WritableFont(WritableFont.createFont("微软雅黑"), 9
			// ,WritableFont.NO_BOLD);
			WritableCellFormat wcf = new WritableCellFormat(font1);
			/*
			 * WritableCellFormat wcf2 = new WritableCellFormat(font2);
			 * WritableCellFormat wcf3 = new WritableCellFormat(font2);//设置样式，字体
			 */
			// 创建单元格样式
			// WritableCellFormat wcf = new WritableCellFormat();

			// 背景颜色
			wcf.setBackground(jxl.format.Colour.GRAY_25);
			wcf.setAlignment(Alignment.CENTRE); // 平行居中
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
			/*
			 * wcf3.setAlignment(Alignment.CENTRE); //平行居中
			 * wcf3.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			 * wcf3.setVerticalAlignment(VerticalAlignment.CENTRE); //垂直居中
			 * wcf3.setBackground(Colour.LIGHT_ORANGE);
			 * wcf2.setAlignment(Alignment.CENTRE); //平行居中
			 * wcf2.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			 * wcf2.setVerticalAlignment(VerticalAlignment.CENTRE); //垂直居中
			 */
			/*
			 * 这个是单元格内容居中显示 还有很多很多样式
			 */
			wcf.setAlignment(Alignment.CENTRE);

			// 判断一下表头数组是否有数据
			if (columns != null && columns.size() > 0) {

				// 循环写入表头
				for (int i = 0; i < columns.size(); i++) {

					/*
					 * 添加单元格(Cell)内容addCell() 添加Label对象Label()
					 * 数据的类型有很多种、在这里你需要什么类型就导入什么类型 如：jxl.write.DateTime
					 * 、jxl.write.Number、jxl.write.Label Label(i, 0, columns[i],
					 * wcf) 其中i为列、0为行、columns[i]为数据、wcf为样式
					 * 合起来就是说将columns[i]添加到第一行(行、列下标都是从0开始)第i列、样式为什么"色"内容居中
					 */
					ws.addCell(new Label(i, 0, columns.get(i), wcf));
				}

				// 判断表中是否有数据
				if (objData != null && objData.size() > 0) {
					// 循环写入表中数据
					for (int i = 0; i < objData.size(); i++) {

						// 转换成map集合{activyName:测试功能,count:2}
						LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) objData.get(i);

						// 循环输出map中的子集：既列值
						int j = 0;
						for (Object o : map.keySet()) {
							if( o == null){
								o=String.valueOf("");
							}
							// ps：因为要“”通用”“导出功能，所以这里循环的时候不是get("Name"),而是通过map.get(o)
							ws.addCell(new Label(j, i + 1, String.valueOf(map.get(o))));
							j++;
						}
					}
				}
				// 写入Exel工作表
				wwb.write();
				// 关闭Excel工作薄对象
				wwb.close();
				// 关闭流
				os.flush();
				os.close();
				// 删除本地文件
				//File file = new File(filePath);
				//file.delete();
				os = null;
			}
		} catch (IllegalStateException e) {
			System.err.println(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return path;
	}

	/**
	 * @author wangyz
	 * 根据数据表生成excel文件
	 * @param objData 数据列表
	 * @param sheetName excle-sheet名
	 * @param columns title
	 * @param filePath 文件路径
	 * @return
	 */
	public static String exportExcelToFile(HttpServletResponse response, List<LinkedHashMap<String, Object>> objData,
                                           String sheetName, List<String> columns, String filePath){
		// 声明工作簿jxl.write.WritableWorkbook
        File newfile = new File(filePath);
		WritableWorkbook wwb;
		try {
			//父文件夹不存在则创建
			File parentFile = newfile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			if (!newfile.exists()) {
				newfile.createNewFile();
	        }
			// 根据传进来的file对象创建可写入的Excel工作薄
			OutputStream os = response.getOutputStream();
			os = new FileOutputStream(newfile);

			wwb = Workbook.createWorkbook(os);

			/*
			 * 创建一个工作表、sheetName为工作表的名称、"0"为第一个工作表
			 * 打开Excel的时候会看到左下角默认有3个sheet、"sheet1、sheet2、sheet3"这样
			 * 代码中的"0"就是sheet1、其它的一一对应。 createSheet(sheetName,
			 * 0)一个是工作表的名称，另一个是工作表在工作薄中的位置
			 */
			WritableSheet ws = wwb.createSheet(sheetName, 0);

			if (objData!=null&&objData.size()>0) {
				for (int i = 0; i < objData.size() + 1; i++) {
					ws.setRowView(i, 500);
				}
				for (int i = 0; i < columns.size() - 1; i++) {
					ws.setColumnView(i, 20);
				}
			}
			ws.setRowView(0,500);
			ws.setColumnView(11, 35);
			ws.setColumnView(columns.size() - 1, 40);

			SheetSettings ss = ws.getSettings();
			ss.setVerticalFreeze(1);// 冻结表头
			WritableFont font1 = new WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.BOLD);
			WritableCellFormat wcf = new WritableCellFormat(font1);

			// 背景颜色
			wcf.setBackground(jxl.format.Colour.GRAY_25);
			wcf.setAlignment(Alignment.CENTRE); // 平行居中
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
			/*
			 * 这个是单元格内容居中显示 还有很多很多样式
			 */
			wcf.setAlignment(Alignment.CENTRE);

			// 判断一下表头数组是否有数据
			if (columns != null && columns.size() > 0) {

				// 循环写入表头
				for (int i = 0; i < columns.size(); i++) {

					/*
					 * 添加单元格(Cell)内容addCell() 添加Label对象Label()
					 * 数据的类型有很多种、在这里你需要什么类型就导入什么类型 如：jxl.write.DateTime
					 * 、jxl.write.Number、jxl.write.Label Label(i, 0, columns[i],
					 * wcf) 其中i为列、0为行、columns[i]为数据、wcf为样式
					 * 合起来就是说将columns[i]添加到第一行(行、列下标都是从0开始)第i列、样式为什么"色"内容居中
					 */
					ws.setColumnView(i,20);
					ws.addCell(new Label(i, 0, columns.get(i), wcf));
				}

				// 判断表中是否有数据
				if (objData != null && objData.size() > 0) {
					// 循环写入表中数据
					for (int i = 0; i < objData.size(); i++) {

						// 转换成map集合{activyName:测试功能,count:2}
						LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) objData.get(i);

						// 循环输出map中的子集：既列值
						int j = 0;
						for (Object o : map.keySet()) {
							if( o == null){
								o=String.valueOf("");
							}
							// ps：因为要“”通用”“导出功能，所以这里循环的时候不是get("Name"),而是通过map.get(o)
							ws.addCell(new Label(j, i + 1, String.valueOf(map.get(o))));
							j++;
						}
					}
				}
				// 写入Exel工作表
				wwb.write();
				// 关闭Excel工作薄对象
				wwb.close();
				// 关闭流
				os.flush();
				os.close();
				// 删除本地文件
				//File file = new File(filePath);
				//file.delete();
				os = null;
			}
		} catch (IllegalStateException e) {
			System.err.println(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return filePath;
	}

	
	/**
	 * @author
	 * @param objData
	 *            导出内容数组
	 * @param sheetName
	 *            导出工作表的名称
	 * @param columns
	 *            导出Excel的表头数组
	 * @return 生成合同路径
	 */
	public static String exportToExcel1(HttpServletResponse response, List<LinkedHashMap<String, Object>> objData,
                                        String sheetName, List<String> columns, List<String> titles, String filename) {
		// 声明工作簿jxl.write.WritableWorkbook
		String filePath="C:\\product\\" + filename + ".xls";
		WritableWorkbook wwb;
		try {
			// 根据传进来的file对象创建可写入的Excel工作薄
			OutputStream os = response.getOutputStream();
			os = new FileOutputStream(filePath);

			wwb = Workbook.createWorkbook(os);

			/*
			 * 创建一个工作表、sheetName为工作表的名称、"0"为第一个工作表
			 * 打开Excel的时候会看到左下角默认有3个sheet、"sheet1、sheet2、sheet3"这样
			 * 代码中的"0"就是sheet1、其它的一一对应。 createSheet(sheetName,
			 * 0)一个是工作表的名称，另一个是工作表在工作薄中的位置
			 */
			WritableSheet ws = wwb.createSheet(sheetName, 0);

			for (int i = 0; i < objData.size() + 1; i++) {
				ws.setRowView(i, 500);
			}
			for (int i = 0; i < columns.size(); i++) {
				ws.setColumnView(i, 10);
			}

			SheetSettings ss = ws.getSettings();
			ss.setVerticalFreeze(2);// 冻结表头

			ws.mergeCells(0, 0, 0, 1);//设置第一列、第一行和 第一列、第二行合并
			ws.mergeCells(1, 0, 1, 1);
			ws.mergeCells(2, 0, 2, 1);//这里是标题“区域” 它合并的是第3列第1行和第3列第2行
			ws.mergeCells(3, 0, 3, 1);
			ws.mergeCells(4, 0, 4, 1);
			ws.mergeCells(5, 0, 6, 0);
			int x = 7;
			int y = 8;
			int z = ( columns.size() - 5 ) / 2;
			for (int i = 6; i < columns.size() - z; i++) {
				ws.mergeCells(x, 0, y, 0);	
				x = x + 2;
				y = y + 2;
			}
			/*ws.mergeCells(9, 0, 10, 0);
			ws.mergeCells(11, 0, 12, 0);
			ws.mergeCells(13, 0, 14, 0);
			ws.mergeCells(15, 0, 16, 0);
			ws.mergeCells(17, 0, 18, 0);*/
			//for (int i = 4; i < columns.size(); i++) {
			//	ws.mergeCells(i+1, 0, i+2, 0);
			//	i++;
			//}
			WritableFont font1 = new WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.BOLD);
			// WritableFont font2 =new
			// WritableFont(WritableFont.createFont("微软雅黑"), 9
			// ,WritableFont.NO_BOLD);
			WritableCellFormat wcf = new WritableCellFormat(font1);
			/*
			 * WritableCellFormat wcf2 = new WritableCellFormat(font2);
			 * WritableCellFormat wcf3 = new WritableCellFormat(font2);//设置样式，字体
			 */
			// 创建单元格样式
			// WritableCellFormat wcf = new WritableCellFormat();

			// 背景颜色
			wcf.setBackground(jxl.format.Colour.GRAY_25);
			wcf.setAlignment(Alignment.CENTRE); // 平行居中
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
			/*
			 * wcf3.setAlignment(Alignment.CENTRE); //平行居中
			 * wcf3.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			 * wcf3.setVerticalAlignment(VerticalAlignment.CENTRE); //垂直居中
			 * wcf3.setBackground(Colour.LIGHT_ORANGE);
			 * wcf2.setAlignment(Alignment.CENTRE); //平行居中
			 * wcf2.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			 * wcf2.setVerticalAlignment(VerticalAlignment.CENTRE); //垂直居中
			 */
			/*
			 * 这个是单元格内容居中显示 还有很多很多样式
			 */
			wcf.setAlignment(Alignment.CENTRE);

			// 判断一下表头数组是否有数据
			if (columns != null && columns.size() > 0) {

				// 循环写入表头
				for (int i = 0; i < columns.size(); i++) {

					/*
					 * 添加单元格(Cell)内容addCell() 添加Label对象Label()
					 * 数据的类型有很多种、在这里你需要什么类型就导入什么类型 如：jxl.write.DateTime
					 * 、jxl.write.Number、jxl.write.Label Label(i, 0, columns[i],
					 * wcf) 其中i为列、0为行、columns[i]为数据、wcf为样式
					 * 合起来就是说将columns[i]添加到第一行(行、列下标都是从0开始)第i列、样式为什么"色"内容居中
					 */
					ws.addCell(new Label(i, 0, columns.get(i), wcf));

				}
				
				for (int i = 0; i < titles.size(); i++) {
					
					ws.addCell(new Label(i, 1, titles.get(i),wcf));
					
				}

				// 判断表中是否有数据
				if (objData != null && objData.size() > 0) {
					// 循环写入表中数据
					int k = 2;
					for (int i = 0; i < objData.size(); i++) {
						// 转换成map集合{activyName:测试功能,count:2}
						LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) objData.get(i);
						// 循环输出map中的子集：既列值
						int j = 0;
						for (Object o : map.keySet()) {
							// ps：因为要“”通用”“导出功能，所以这里循环的时候不是get("Name"),而是通过map.get(o)
							if( o == null){
								o=String.valueOf("");
							}
							ws.addCell(new Label(j, k, String.valueOf(map.get(o))));
							j++;
						}
						k++;
					}
				}
				// 写入Exel工作表
				wwb.write();
				// 关闭Excel工作薄对象
				wwb.close();
				// 关闭流
				os.flush();
				os.close();
				// 删除本地文件
				//File file = new File(filePath);
				//file.delete();
				os = null;

			}
		} catch (IllegalStateException e) {
			System.err.println(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return filePath;
	}


	/**
	 * 下载excel
	 * 
	 * @author
	 * @param response
	 * @param filename
	 *            文件名 ,如:20110808.xls
	 * @param listData
	 *            数据源
	 * @param sheetName
	 *            表头名称
	 * @param columns
	 *            列名称集合,如：{物品名称，数量，单价}
	 */
	public static String exportexcle(HttpServletResponse response, String filename,
                                     List<LinkedHashMap<String, Object>> listData, String sheetName, List<String> columns, String path) {
		// 调用上面的方法、生成Excel文件
		response.setContentType("application/vnd.ms-excel");
		// response.setHeader("Content-Disposition",
		// "attachment;filename="+filename);
		String str=exportToExcel(response, listData, sheetName, columns, filename,path);
		// try {
		// //response.setHeader("Content-Disposition", "attachment;filename=" +
		// new String(filename.getBytes("gb2312"), "ISO8859-1") + ".xls");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		return str;
	}

	public static void exportexcle1(HttpServletResponse response, String filename,
                                    List<LinkedHashMap<String, Object>> listData, String sheetName, List<String> columns,
                                    List<String> titles) {
		// 调用上面的方法、生成Excel文件
		response.setContentType("application/vnd.ms-excel");
		// response.setHeader("Content-Disposition",
		// "attachment;filename="+filename);
		exportToExcel1(response, listData, sheetName, columns, titles, filename);
		// try {
		// //response.setHeader("Content-Disposition", "attachment;filename=" +
		// new String(filename.getBytes("gb2312"), "ISO8859-1") + ".xls");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
	}


}
