package com.sxt.generate.desert.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName : GnLogger
 * Description : GnLogger日志类
 *
 * @version: v1.1
 * @author: hanbing
 * @since : 2015/5/29
 */
public class GnLogger implements LoggerAdapter {

    private Logger logger;

    private boolean debugEnabled  = true;

    @Override
    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public GnLogger(Class clazz){
        logger = Logger.getLogger(clazz != null ? clazz.getName() : " ");
    }

    @Override
    public void error(String var1, Throwable var2) {
        logger.log(Level.SEVERE, var1, var2);
    }

    @Override
    public void error(String var1) {
        logger.log(Level.SEVERE, var1);
    }

    @Override
    public void debug(String var1) {
        logger.log(Level.FINEST, var1);
    }

    @Override
    public void warn(String var1) {
        logger.log(Level.WARNING, var1);
    }

    @Override
    public void info(String var1) {
        logger.log(Level.OFF, var1);
    }
}
