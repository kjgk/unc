package com.unicorn.poi.excel;

import java.util.List;

public interface IExcelParser<T> {
    List<T> parse(IParserParam parserParam);
}
