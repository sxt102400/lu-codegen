package com.rats.lu.generator.utils;

import java.text.MessageFormat;

public class MsgFmt {

    private MsgFmt() {
    }
    public static String getString(String text, String... parms) {
            return MessageFormat.format(text, parms);

    }
}
