package com.ecommerce.admin.export;

import com.ecommerce.common.entity.Category;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.List;

public class CategoryExcelExporter extends AbstractExporter{

    private final XSSFWorkbook workbook;

    private XSSFSheet sheet;

    public CategoryExcelExporter(){
        workbook = new XSSFWorkbook();
    }
    private void writeHeaderLine(){
        sheet = workbook.createSheet("Categories");
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        cellStyle.setFont(font);

        createCell(row, 0, "Category Id", cellStyle);
        createCell(row, 2, "Category Name", cellStyle);
        createCell(row, 3, "Alias", cellStyle);
        createCell(row, 5, "Enabled", cellStyle);
    }
    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style){

        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);

        if (value instanceof Integer){
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else{
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);

    }
    public  void export(List<Category> listCategories, HttpServletResponse response) throws IOException {

        super.setResponseHeader(response, "application/octet-stream", ".xlsx");

        writeHeaderLine();
        writeDataLines(listCategories);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<Category> listCategories) {
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(12);
        cellStyle.setFont(font);

        for (Category category : listCategories) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            createCell(row, columnIndex++, category.getId(), cellStyle);
            createCell(row, columnIndex++, category.getName(), cellStyle);
            createCell(row, columnIndex++, category.getAlias(), cellStyle);
            createCell(row, columnIndex++, category.isEnabled(), cellStyle);
        }
    }
}
