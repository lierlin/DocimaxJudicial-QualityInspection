package com.docimax.qualityinspection.util;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Excel表格生成工具包
 * @author Wangzh
 */
public class ExcelGenerator<T> {
    private final Class<T> clazz;
    private final List<T> dataList;

    public ExcelGenerator(Class<T> clazz, List<T> dataList) {
        this.clazz = clazz;
        this.dataList = dataList;
    }

    public byte[] generateExcelBytes() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("数据详情");

            CellStyle headerCellStyle = createHeaderCellStyle(workbook);
            CellStyle dataCellStyle = createDataCellStyle(workbook);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            Field[] fields = clazz.getDeclaredFields();
            int initColumnNum = 0;
            Row headerRow = sheet.createRow(0);

            for (Field field : fields) {
                Schema annotation = field.getAnnotation(Schema.class);
                if (annotation != null) {
                    String title = annotation.title();
                    Cell cell = headerRow.createCell(initColumnNum);
                    cell.setCellValue(title);
                    cell.setCellStyle(headerCellStyle);
                    sheet.setColumnWidth(initColumnNum, 20 * 256);
                    initColumnNum++;
                }
            }

            int rowNum = 1;
            for (T dto : dataList) {
                Row row = sheet.createRow(rowNum++);
                populateRowWithData(row, dto, formatter, dataCellStyle);
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerCellStyle.setFont(headerFont);
        setBorderStyles(headerCellStyle);
        return headerCellStyle;
    }

    private CellStyle createDataCellStyle(Workbook workbook) {
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataCellStyle.setWrapText(true);
        setBorderStyles(dataCellStyle);
        return dataCellStyle;
    }

    private void setBorderStyles(CellStyle cellStyle) {
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
    }

    private void populateRowWithData(Row row, T dto, DateTimeFormatter formatter, CellStyle dataCellStyle) {
        Field[] fields = clazz.getDeclaredFields();
        int cellIndex = 0;

        for (Field field : fields) {
            Schema annotation = field.getAnnotation(Schema.class);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(dto);
                    Cell cell = row.createCell(cellIndex++);
                    if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    } else if (value instanceof Double) {
                        cell.setCellValue((Double) value);
                    } else if (value instanceof Boolean) {
                        cell.setCellValue((Boolean) value);
                    } else if (value instanceof LocalDate) {
                        String format = "";
                        if (value != null) {
                            format = ((LocalDate) value).format(formatter);
                        }
                        cell.setCellValue(format);
                    } else if (value instanceof LocalDateTime) {
                        String format = "";
                        if (value != null) {
                            format = ((LocalDateTime) value).format(formatter);
                        }
                        cell.setCellValue(format);
                    }
                    cell.setCellStyle(dataCellStyle);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
