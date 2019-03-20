package com.unicorn.poi.excel;

import com.unicorn.poi.excel.meta.ExcelField;
import lombok.Data;

import java.util.Date;

@Data
public class DataPointValue {

    @ExcelField(index = 0)
    private String dataPointTag;

    @ExcelField(index = 12, type = ExcelField.ExcelFieldType.Date)
    private Date date;

    @ExcelField(index = 1, type = ExcelField.ExcelFieldType.Int)
    private Integer value1;

    @ExcelField(index = 2, type = ExcelField.ExcelFieldType.Int)
    private Integer value2;

}