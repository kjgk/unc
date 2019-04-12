package com.unicorn.poi.excel.param;

import com.unicorn.poi.excel.IParserParam;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class DefaultParserParam implements IParserParam {

    private InputStream inputStream;
    private Class targetClass;
    private Integer columnSize;
    private Integer sheetNum;

    private DefaultParserParam(InputStream inputStream, Class targetClass, Integer columnSize, Integer sheetNum) {

        if (inputStream.markSupported()) {
            this.inputStream = inputStream;
        } else {
            this.inputStream = new BufferedInputStream(inputStream);
        }
        this.targetClass = targetClass;
        this.columnSize = columnSize;
        this.sheetNum = sheetNum;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public InputStream getExcelInputStream() {
        return inputStream;
    }

    @Override
    public Class getTargetClass() {
        return targetClass;
    }


    @Override
    public Integer getColumnSize() {
        return columnSize;
    }

    @Override
    public Integer getSheetNum() {
        return sheetNum;
    }

    public static class Builder {

        private InputStream inputStream;
        private Class targetClass;
        private Integer columnSize;
        private Integer sheetNum = IParserParam.FIRST_SHEET;

        public Builder excelInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Builder targetClass(Class clazz) {
            this.targetClass = clazz;
            return this;
        }


        public Builder columnSize(Integer columnSize) {
            this.columnSize = columnSize;
            return this;
        }

        public Builder sheetNum(Integer sheetNum) {
            this.sheetNum = sheetNum;
            return this;
        }

        public DefaultParserParam build() {
            return new DefaultParserParam(inputStream, targetClass, columnSize, sheetNum);
        }
    }
}
