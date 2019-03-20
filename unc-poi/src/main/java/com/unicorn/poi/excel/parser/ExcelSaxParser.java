package com.unicorn.poi.excel.parser;


import com.unicorn.poi.excel.handler.IExcelParseHandler;
import com.unicorn.poi.excel.handler.impl.Excel2007ParseHandler;

import java.io.InputStream;

public class ExcelSaxParser<T> extends AbstractExcelParser<T> {

    public IExcelParseHandler<T> createHandler(InputStream excelInputStream) {
        try {
            return new Excel2007ParseHandler<>();
//            byte[] header8 = IOUtils.peekFirst8Bytes(excelInputStream);
//            if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
//                return new Excel2003ParseHandler<>();
//            } else if (DocumentFactoryHelper.hasOOXMLHeader(excelInputStream)) {
//                return new Excel2007ParseHandler<>();
//            } else {
//                throw new IllegalArgumentException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
//            }
        } catch (Exception e) {
            logger.error("getParserInstance Error!", e);
            throw new RuntimeException(e);
        }
    }

}
