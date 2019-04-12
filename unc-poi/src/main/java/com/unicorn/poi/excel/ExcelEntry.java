package com.unicorn.poi.excel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExcelEntry<T> {

    private List<T> content;

    private List<String> header;
}
