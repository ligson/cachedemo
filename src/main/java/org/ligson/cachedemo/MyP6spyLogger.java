package org.ligson.cachedemo;

import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.Slf4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简化异常栈输出的 Logger 类，在 spy.properties 文件中增加如下配置
 * <pre>
 *     # specifies the appender to use for logging
 *     appender=com.crux.core.log.MyP6spyLogger
 *     # prints a stack trace for every statement logged
 *     stacktrace=true
 *     # if stacktrace=true, specifies the stack trace to print
 *     stacktraceclass=com.crux
 * </pre>
 */
public class MyP6spyLogger extends Slf4JLogger {
    private Logger log = LoggerFactory.getLogger("p6spy");

    /*
     * 简化异常栈的输出，默认是现实输出完整异常栈，调整为只输出离最近的类的一行
     */
    @Override
    public void logException(Exception e) {
        final String stackTraceClass = P6SpyOptions.getActiveInstance().getStackTraceClass();
        int firstP6spyCall = 0;
        int lastApplicationCall = 0;
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            String className = stackTrace[i].getClassName();
            if (className.startsWith("com.p6spy")) {
                firstP6spyCall = i;
            } else if (className.startsWith(stackTraceClass)) {
                lastApplicationCall = i;
                break;
            }
        }
        int j = lastApplicationCall;

        if (j == 0) { // if app not found, then use whoever was the last guy that called a p6spy class.
            j = 1 + firstP6spyCall;
        }
        log.info("{}.{}({}:{})", stackTrace[j].getClassName(), stackTrace[j].getMethodName(), stackTrace[j].getFileName(), stackTrace[j].getLineNumber());
    }
}
