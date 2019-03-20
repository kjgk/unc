package com.unicorn.poi.excel;

import com.unicorn.poi.excel.param.DefaultParserParam;
import com.unicorn.poi.excel.parser.ExcelSaxParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        IExcelParser<DataPointValue> parser = new ExcelSaxParser<DataPointValue>();

        List<String> headers = Arrays.asList("编号", "区代码", "镇代码", "小班号", "面积", "地类", "森林类别", "事权等级", "保护等级", "林种", "绿地类型", "造林年份", "更新时间");

        IParserParam parserParam = DefaultParserParam.builder()
                .excelInputStream(new FileInputStream("/home/kjgk/Desktop/lhsr/2017年主题库林业小班数据.xlsx"))
                .columnSize(headers.size())
                .sheetNum(IParserParam.FIRST_SHEET)
                .targetClass(DataPointValue.class)
                .header(headers)
                .build();

        List<DataPointValue> list = parser.parse(parserParam);
    }
}


