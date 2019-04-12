package com.unicorn.poi.excel.handler;

import com.unicorn.poi.excel.ExcelEntry;
import com.unicorn.poi.excel.IParserParam;

public interface IExcelParseHandler<T> {

    ExcelEntry<T> process(IParserParam parserParam) throws Exception;

}
