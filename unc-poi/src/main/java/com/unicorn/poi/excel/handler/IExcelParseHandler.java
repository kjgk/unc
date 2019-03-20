package com.unicorn.poi.excel.handler;

import com.unicorn.poi.excel.IParserParam;

import java.util.List;

public interface IExcelParseHandler<T> {

    List<T> process(IParserParam parserParam) throws Exception;

}
