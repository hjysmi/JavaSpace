package com.lenovo.util;

import com.lenovo.bean.ClosingPrice;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/11.
 */
public class ExeclWrite {
    private final String outputfilepath = System.getProperty("user.dir") + "/test_out.xls";
    private final String output_jvli_filepath = System.getProperty("user.dir") + "/test_out_jvli.xlsx";

    /**
     * 将连中3期之间的距离计算写到execl中
     */
    public void write_jvli(List<Map<String, String>> list_jvli) {
        //创建HSSFWorkbook对象
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建HSSFSheet对象
        XSSFSheet sheet = wb.createSheet("sheet0");
        XSSFFont font = wb.createFont();
        font.setColor(HSSFColor.RED.index);
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        XSSFRow row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("IsZhong");
        row0.createCell(1).setCellValue("fail_count");
        for (int i = 1; i < list_jvli.size() + 1; i++) {
            XSSFRow row = sheet.createRow(i);
            Map<String, String> map = list_jvli.get(i-1);
            row.createCell(0).setCellValue(map.get("IsZhong"));
            row.createCell(1).setCellValue(map.get("fail_count"));
            System.out.println("正在写入中: " + i);
        }
        //输出Excel文件
        FileOutputStream output;
        try {
            output = new FileOutputStream(output_jvli_filepath);
            wb.write(output);
            output.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(List<Map<String, Object>> list) {
        //String outputfilepath = System.getProperty("user.dir") + "/test_out.xlsx";
        //创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("sheet0");
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.RED.index);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);

        HSSFRow row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("id");
        row0.createCell(1).setCellValue("期号");
        row0.createCell(2).setCellValue("开奖号");
        row0.createCell(3).setCellValue("MA20");
        row0.createCell(4).setCellValue("MA60");
        row0.createCell(5).setCellValue("交点");
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            ClosingPrice closingPrice = (ClosingPrice) map.get("price");
            String isIntersection = (String) map.get("isIntersection");
            HSSFRow row = sheet.createRow(i + 1);
            row.setHeight((short) 300);
            row.createCell(0).setCellValue(closingPrice.getId());
            row.createCell(1).setCellValue(closingPrice.getQihao());
            row.createCell(2).setCellValue(closingPrice.getResult());
            row.createCell(3).setCellValue(closingPrice.getMa20());
            row.createCell(4).setCellValue(closingPrice.getMa60());
            HSSFCell cell5 = row.createCell(5);
            if (isIntersection.equals("死叉")) {
                cell5.setCellStyle(cellStyle);
            }
            cell5.setCellValue(isIntersection);
            if (i != 0 && isIntersection.equals(list.get(i - 1).get("isIntersection"))) {
                HSSFCellStyle rowStyle = wb.createCellStyle();
                rowStyle.setFillForegroundColor(HSSFColor.AQUA.index);
                rowStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                row.setRowStyle(rowStyle);
                row.createCell(6).setCellValue("重复了");
                System.out.println("重复了,data:" + closingPrice.toString());
            }

        }
        //输出Excel文件
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(outputfilepath);
            wb.write(output);
            output.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取数据
     *
     * @return
     */
    public List<Map<String, Object>> read() {
        //创建HSSFWorkbook对象
        HSSFWorkbook wb;
        try {
            wb = new HSSFWorkbook(new FileInputStream(outputfilepath));
            //创建HSSFSheet对象
            HSSFSheet sheet = wb.getSheet("sheet0");
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Map<String, Object> map = new HashMap<>();
                ClosingPrice price = new ClosingPrice();
                HSSFRow row = sheet.getRow(i);
                price.setId((int) row.getCell(0).getNumericCellValue());
                price.setQihao(row.getCell(1).getStringCellValue());
                price.setResult(row.getCell(2).getStringCellValue());
                price.setMa20(row.getCell(3).getNumericCellValue());
                price.setMa60(row.getCell(4).getNumericCellValue());
                String isIntersection = row.getCell(5).getStringCellValue();
                map.put("price", price);
                map.put("isIntersection", isIntersection);
                list.add(map);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int read_jvli_lastId() {
        //创建HSSFWorkbook对象
        HSSFWorkbook wb;
        try {
            wb = new HSSFWorkbook(new FileInputStream(output_jvli_filepath));
            //创建HSSFSheet对象
            HSSFSheet sheet = wb.getSheet("sheet0");
            //HSSFRow row = sheet.getRow(sheet.getLastRowNum());
            return sheet.getLastRowNum();
        } catch (IOException e) {
            //e.printStackTrace();
            return 0;
        }
        //return 0;
    }

    public int read_lastId() {
        //创建HSSFWorkbook对象
        HSSFWorkbook wb;
        try {
            wb = new HSSFWorkbook(new FileInputStream(outputfilepath));
            //创建HSSFSheet对象
            HSSFSheet sheet = wb.getSheet("sheet0");
            // HSSFRow row = sheet.getRow(sheet.getLastRowNum());
            return sheet.getLastRowNum();
            //return (int) row.getCell(0).getNumericCellValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
