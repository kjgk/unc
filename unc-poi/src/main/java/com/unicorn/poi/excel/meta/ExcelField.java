package com.unicorn.poi.excel.meta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelField {

    Logger logger = LoggerFactory.getLogger(ExcelField.class);

    int index();

    ExcelFieldType type() default ExcelFieldType.Str;


    enum ExcelFieldType {

        Str {
            @Override
            public Object build(String in) {
                return in;
            }
        },

        Int {
            @Override
            public Object build(String in) {
                return StringUtils.isEmpty(in) ? null : Integer.valueOf(in);
            }
        },

        Num {
            @Override
            public Object build(String in) {
                return StringUtils.isEmpty(in) ? null : Double.valueOf(in);
            }
        },

        Date {
            @Override
            public Object build(String in) {
                if (StringUtils.isEmpty(in)) {
                    return null;
                }
                try {
                    String dateText = "";
                    if (in.matches("\\d{4}$")) {
                        return new SimpleDateFormat("yyyy").parse(in);
                    }
                    if (in.matches("\\d{4}/\\d{2}/\\d{2}$")) {
                        return new SimpleDateFormat("yyyy/MM/dd").parse(in);
                    }
                    if (in.matches("\\d{4}-\\d{2}-\\d{2}$")) {
                        return new SimpleDateFormat("yyyy-MM-dd").parse(in);
                    }
                    if (in.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$")) {
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        return dateFormat1.parse(in);
                    }
                    if (in.matches("\\d{1,2}/\\d{1,2}/\\d{2}$")) {
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yy");
                        return dateFormat1.parse(in);
                    }
                    if (in.matches("\\d{1,2}/\\d{1,2}/\\d{2} \\d{2}:\\d{2}$")) {
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yy HH:mm");
                        return dateFormat1.parse(in);
                    }
                } catch (ParseException e) {
                    logger.error(String.format("未知的日期格式,日期为:%s", in));
                }
                return null;
            }
        };

        public abstract Object build(String str);
    }
}
