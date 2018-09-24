package com.rats.lu.generator.utils;

import java.text.DateFormat;
import java.util.Date;

public class TemplateUtils {

    public static class MyDateTool {
        public String now() {
            return DateFormat.getDateTimeInstance().format(new Date());
        }
    }
}
