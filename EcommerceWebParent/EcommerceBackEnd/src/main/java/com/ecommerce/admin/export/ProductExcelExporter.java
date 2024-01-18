package com.ecommerce.admin.export;

import com.ecommerce.common.entity.Product;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.List;

public class ProductExcelExporter extends AbstractExporter{

    private final XSSFWorkbook workbook;

    private XSSFSheet sheet;

    public ProductExcelExporter(){
        workbook = new XSSFWorkbook();
    }
    private void writeHeaderLine(){
        sheet = workbook.createSheet("Brands");
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        cellStyle.setFont(font);

        createCell(row, 0, "Product Id", cellStyle);
        createCell(row, 3, "Product Name", cellStyle);
        createCell(row, 3, "Alias", cellStyle);
        createCell(row, 3, "Enabled", cellStyle);
        createCell(row, 3, "In Stock", cellStyle);
        createCell(row, 3, "Cost", cellStyle);
        createCell(row, 3, "Price", cellStyle);
        createCell(row, 3, "Discount Percent", cellStyle);
        createCell(row, 3, "Length", cellStyle);
        createCell(row, 3, "Width", cellStyle);
        createCell(row, 3, "Height", cellStyle);
        createCell(row, 3, "Weight", cellStyle);
        createCell(row, 3, "Category", cellStyle);
        createCell(row, 5, "Brand", cellStyle);
    }
    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style){

        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);

        if (value instanceof Integer){
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else{
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);

    }
    public  void export(List<Product> listProducts, HttpServletResponse response) throws IOException {

        super.setResponseHeader(response, "application/octet-stream", ".xlsx");

        writeHeaderLine();
        writeDataLines(listProducts);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<Product> listProducts) {
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(12);
        cellStyle.setFont(font);

        for (Product product : listProducts) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            createCell(row, columnIndex++, product.getId(), cellStyle);
            createCell(row, columnIndex++, product.getName(), cellStyle);
            createCell(row, columnIndex++, product.getAlias(), cellStyle);
            createCell(row, columnIndex++, product.isEnabled(), cellStyle);
            createCell(row, columnIndex++, product.isInStock(), cellStyle);
            createCell(row, columnIndex++, product.getCost(), cellStyle);
            createCell(row, columnIndex++, product.getPrice(), cellStyle);
            createCell(row, columnIndex++, product.getDiscountPercent(), cellStyle);
            createCell(row, columnIndex++, product.getLength(), cellStyle);
            createCell(row, columnIndex++, product.getWidth(), cellStyle);
            createCell(row, columnIndex++, product.getHeight(), cellStyle);
            createCell(row, columnIndex++, product.getWeight(), cellStyle);
            createCell(row, columnIndex++, product.getCategory().getName(), cellStyle);
            createCell(row, columnIndex++, product.getBrand().getName(), cellStyle);
        }
    }
}
