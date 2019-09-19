/**
 * Copyright 2009-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.logging;

import java.lang.reflect.Constructor;

/**
 * @author Clinton Begin
 * @author Eduardo Macarron
 */
public final class LogFactory {

    /**
     * Marker to be used by logging implementations that support markers.
     */
    public static final String MARKER = "MYBATIS";

    private static Constructor<? extends Log> logConstructor;

    static {
        /**
         * 在static语句块中，会依次尝试加载slf4j，commmons-logging，log4j2，log4j,JDK的java.util.logging.Logger
         */
        Runnable runnable = LogFactory::useSlf4jLogging;
        // 这里的Runnable只是起到了一个无参，无返回值的函数式接口的作用
//        LogImplementation logImplementation = () -> LogFactory.useSlf4jLogging();
//        LogImplementation logImplementation1 = LogFactory::useSlf4jLogging;
        tryImplementation(runnable);
        tryImplementation(LogFactory::useCommonsLogging);
        tryImplementation(LogFactory::useLog4J2Logging);
        tryImplementation(LogFactory::useLog4JLogging);
        tryImplementation(LogFactory::useJdkLogging);
        tryImplementation(LogFactory::useNoLogging);
    }

    private LogFactory() {
        // disable construction
    }

    public static Log getLog(Class<?> aClass) {
        return getLog(aClass.getName());
    }

    public static Log getLog(String logger) {
        try {
            return logConstructor.newInstance(logger);
        } catch (Throwable t) {
            throw new LogException("Error creating logger for logger " + logger + ".  Cause: " + t, t);
        }
    }

    /**
     * 这个方法是mybatis支持自定义日志。
     *      mybatis实现自定义日志的方式是：
     *          1.为org.apache.ibatis.logging.Log接口提供一个实现类。
     *          2.在所有的mybatis的最前面调用LogFactory类的静态方法useCustomLogging，并把Log接口的实现类的全类名作为一个字符串传入即可。
     *
     */
    public static synchronized void useCustomLogging(Class<? extends Log> clazz) {
        // 这个方法的作用就是调用把自定义的日志实现类，放入到mybatis中。
        setImplementation(clazz);
    }

    public static synchronized void useSlf4jLogging() {
        setImplementation(org.apache.ibatis.logging.slf4j.Slf4jImpl.class);
    }

    public static synchronized void useCommonsLogging() {
        setImplementation(org.apache.ibatis.logging.commons.JakartaCommonsLoggingImpl.class);
    }

    public static synchronized void useLog4JLogging() {
        setImplementation(org.apache.ibatis.logging.log4j.Log4jImpl.class);
    }

    public static synchronized void useLog4J2Logging() {
        setImplementation(org.apache.ibatis.logging.log4j2.Log4j2Impl.class);
    }

    public static synchronized void useJdkLogging() {
        setImplementation(org.apache.ibatis.logging.jdk14.Jdk14LoggingImpl.class);
    }

    public static synchronized void useStdOutLogging() {
        setImplementation(org.apache.ibatis.logging.stdout.StdOutImpl.class);
    }

    public static synchronized void useNoLogging() {
        setImplementation(org.apache.ibatis.logging.nologging.NoLoggingImpl.class);
    }

    /**
     * 这里是一个假的线程执行，起到的作用仅仅是方法执行一次。
     */
    private static void tryImplementation(Runnable runnable) {
        if (logConstructor == null) {
            //判断logConstructor为null的时候，才会执行， 含有就是，只会获取到第一个执行成功的日志框架，所以一定要注意，日志框架的执行顺序
            try {
                runnable.run();
            } catch (Throwable t) {
                // 这里如果发生异常会忽略掉，这里的异常信息是：找不到指定的类ClassNotFoundExcpetion
                // ignore
            }
        }
    }

    private static void setImplementation(Class<? extends Log> implClass) {
        try {
            // 通过传入的日志实现的Class对象， 通过反射获取String类型参数的构造方法。
            Constructor<? extends Log> candidate = implClass.getConstructor(String.class);
            /************这里只是输出自身的日志*************/
            Log log = candidate.newInstance(LogFactory.class.getName());
            // 注意:只有日志级别是debug级别的时候，才会打出这句日志。
            if (log.isDebugEnabled()) {
                log.debug("Logging initialized using '" + implClass + "' adapter.");
            }
            /*************************/
            // 把获取到的构造方法赋值给成员变量logConstructor
            logConstructor = candidate;
        } catch (Throwable t) {
            throw new LogException("Error setting Log implementation.  Cause: " + t, t);
        }
    }

}
