package com.sxt.generate.desert.logging;

/**
 * ClassName : LoggerAdapter
 * Description : LoggerAdapter日志接口
 *
 * @version : v1.1
 * @author : hanbing
 * @since : 2015/5/29
 */
public interface LoggerAdapter {

    boolean isDebugEnabled();

    void error(String var1, Throwable var2);

    void error(String var1);

    void debug(String var1);

    void warn(String var1);

    void info(String var1);
}
