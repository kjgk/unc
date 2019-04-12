package com.unicorn.poi.excel;

import java.io.InputStream;

public interface IParserParam {

    Integer FIRST_SHEET = 0;

    InputStream getExcelInputStream();

    Class getTargetClass();

    Integer getColumnSize();

    Integer getSheetNum();

//    List<String> getHeader();
}
