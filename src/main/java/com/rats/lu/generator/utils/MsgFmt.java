package com.rats.lu.generator.utils;

import java.text.MessageFormat;

/**
 * Copyright (C) 2016 
 * <p/>
 *
 * @author : hanbing
 * @version : v1.0
 * @since : 2016/12/12
 */
public class MsgFmt {

    private MsgFmt() {
    }
    public static String getString(String text, String... parms) {
            return MessageFormat.format(text, parms);

    }
}
