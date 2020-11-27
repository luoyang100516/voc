package com.lizhen.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



public class ExcelUtil {

    public static boolean isExcel2003(String filePath)
    {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath)
    {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }


    private static String getAutoValue(Cell cell) {
        if (cell==null){
            return null;
        }
        String value=null;
        switch (cell.getCellTypeEnum()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                value= sdf.format(date);
            } else {
                value= NumberToTextConverter.toText(cell.getNumericCellValue());
            }
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                value = "";
                break;
            default:
                value = cell.toString();
                break;
        }

        return value;
    }



    public static String getValue(Cell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    public static Date getCellDate(Cell cell){
        Date date = null;
        if (cell != null) {
            date = cell.getDateCellValue();
        }
        return date;
    }
    public static String getCellString(  Cell cell) {
        String str = "";
        if (cell == null) {
            return str;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            RichTextString text = cell.getRichStringCellValue();
            if (text == null) {
                return str;
            }
            str = StringUtil.nvl(text.getString());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            double text = cell.getNumericCellValue();
            long ltext = Math.round(text);
            if (Double.parseDouble(ltext + ".0") == text) {
                str = String.valueOf(ltext);
            } else {
                str = String.valueOf(text);
            }
        } else {
            return str;
        }
        return str;
    }

    /**
     * 从excel中读取数据转换成对应实体类
     *
     * @param sheet
     * @param fields      对应类中的字段 多个字段用，隔开。 格式如：  name_i,status_n,children.name_ii,children.status_nn,placetime_99.time 其中 _ 左边为字段名称，右边为对应excel中列标号。 index后面跟 数据类型，不写则默认为string
     * @param t
     * @param startRowNum 有效数据起始行
     * @return
     */
    public static List toBeanFromExcel(Sheet sheet, String fields, Class<?> t, int startRowNum) throws Exception {
//        int rowNum = startRowNum;
        List<Object> beanList = new ArrayList<>();
        Row row=null;
//        int lastRow=sheet.getPhysicalNumberOfRows();
//        int lastRowNum=sheet.getLastRowNum();
        for (int iRow0 = startRowNum;sheet.getRow(iRow0)!= null; iRow0 ++) {
            row = sheet.getRow(iRow0);
            if (row==null)
                continue;
            Cell cell=row.getCell(0);
            if (cell==null)
                continue;
            cell.setCellType(CellType.STRING);
            if(StringUtil.isBlank(cell.getStringCellValue()))
                continue;
            HashMap beanmap = new HashMap();
            for (String field : fields.split(",")) {
                String fieldname = field.split("_")[0];
                String valtype = field.split("_")[1];
                int index = 0;
                String fieldtype = "string";
                if (valtype.contains(".")) {
                    index = Integer.valueOf(valtype.split("\\.")[0]);
                    fieldtype = valtype.split("\\.")[1];
                } else {
                    index = Integer.valueOf(valtype);
                }
                Object val = null;
                if ("time".equals(fieldtype)) {
                    val = ExcelUtil.getCellDate(row.getCell(index));
                }else {
                    val = ExcelUtil.getAutoValue(row.getCell(index));
                    if(val!=null){
                        val = String.valueOf(val).trim();
                    }
                }
                if (fieldname.contains(".")) {
                    Object object = beanmap.get(fieldname.split("\\.")[0]);
                    HashMap children = object == null ? new HashMap() : (HashMap) object;
                    children.put(fieldname.split("\\.")[1], val);
                    beanmap.put(fieldname.split("\\.")[0], children);
                } else {
                    beanmap.put(fieldname, val);
                }
            }
            Object bean = JSONObject.toJavaObject((JSON) JSONObject.toJSON(beanmap), t);
            beanList.add(bean);
        }
        return beanList;
    }

}
