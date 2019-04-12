package com.unicorn.poi.excel;

import com.unicorn.poi.excel.param.DefaultParserParam;
import com.unicorn.poi.excel.parser.ExcelSaxParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        IExcelParser<DataPointValue> parser = new ExcelSaxParser<DataPointValue>();
        FileInputStream fileInputStream = new FileInputStream("/home/kjgk/Desktop/lhsr/2016年主题库林业小班数据.xls");
        IParserParam parserParam = DefaultParserParam.builder()
                .excelInputStream(fileInputStream)
                .columnSize(13)
                .targetClass(DataPointValue.class)
                .build();

        ExcelEntry<DataPointValue> entry = parser.parse(parserParam);
        for (DataPointValue dataPointValue : entry.getContent()) {
            System.out.println(dataPointValue);
        }
    }
}


