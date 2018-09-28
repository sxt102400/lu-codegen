package com.rats.lu.generator.utils;

import java.text.DateFormat;
import java.util.Date;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class TemplateUtils {

    public static class MyDateTool {
        public String now() {
            return DateFormat.getDateTimeInstance().format(new Date());
        }
    }
}
