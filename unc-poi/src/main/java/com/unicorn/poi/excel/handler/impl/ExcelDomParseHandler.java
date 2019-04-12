package com.unicorn.poi.excel.handler.impl;

import com.unicorn.poi.excel.ExcelEntry;
import com.unicorn.poi.excel.IParserParam;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public class ExcelDomParseHandler<T> extends BaseExcelParseHandler<T> {

    @Override
    public ExcelEntry<T> process(IParserParam parserParam) throws Exception {
        Workbook workbook = generateWorkBook(parserParam);
        Sheet sheet = workbook.getSheetAt(parserParam.getSheetNum());
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<T> result = parseRowToTargetList(rowIterator, parserParam);
        return new ExcelEntry(result, header);
    }

    private Workbook generateWorkBook(IParserParam parserParam) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(parserParam.getExcelInputStream());
    }

    private List<T> parseRowToTargetList(Iterator<Row> rowIterator, IParserParam parserParam) {
        List<T> result = new ArrayList<>();
        for (; rowIterator.hasNext(); ) {
            Row row = rowIterator.next();
            List<String> rowData = parseRowToList(row, parserParam.getColumnSize());
            Optional<T> d = parseRowToTarget(parserParam, rowData);
            d.ifPresent(result::add);
        }
        return result;
    }

    private List<String> parseRowToList(Row row, int size) {
        List<String> dataRow = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if (row.getCell(i) != null) {
                DataFormatter formatter = new DataFormatter();
                String formattedCellValue = formatter.formatCellValue(row.getCell(i));
                dataRow.add(formattedCellValue.trim());
            } else {
                dataRow.add("");
            }
        }
        return dataRow;
    }
}
