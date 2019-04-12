package com.unicorn.poi.excel;

public interface IExcelParser<T> {
    ExcelEntry<T> parse(IParserParam parserParam);
}
