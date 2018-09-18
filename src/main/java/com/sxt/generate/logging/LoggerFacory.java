package com.sxt.generate.logging;

/**
 * ClassName : LoggerFacory
 * Description : LoggerFacory
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public class LoggerFacory {

    public static LoggerAdapter getLogger(Class<?> clazz) {
        try {
            return new GnLogger(clazz);
        } catch (Throwable var2) {
            throw new RuntimeException("error,logger factory get log");
        }
    }
}
