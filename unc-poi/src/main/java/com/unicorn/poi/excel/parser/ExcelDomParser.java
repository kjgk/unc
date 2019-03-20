package com.unicorn.poi.excel.parser;

import com.unicorn.poi.excel.handler.impl.ExcelDomParseHandler;
import com.unicorn.poi.excel.handler.IExcelParseHandler;

import java.io.InputStream;

public class ExcelDomParser<T> extends AbstractExcelParser<T> {

    private IExcelParseHandler<T> excelParseHandler;

    public ExcelDomParser() {
        this.excelParseHandler = new ExcelDomParseHandler<>();
    }

    @Override
    protected IExcelParseHandler<T> createHandler(InputStream excelInputStream) {
        return this.excelParseHandler;
    }
}
