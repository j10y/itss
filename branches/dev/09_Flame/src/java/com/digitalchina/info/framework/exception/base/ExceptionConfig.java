package com.digitalchina.info.framework.exception.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 如果想将所有异常消息在容器启动时加载，可以选用使用此类。
 * @Class Name ExceptionConfig
 * @author peixf
 * @Create In 2007-11-12
 * @deprecated
 */
public class ExceptionConfig
{

    private static ExceptionConfig configInstance = new ExceptionConfig();
    private static Map map = new HashMap();

    public ExceptionConfig()
    {
    }

    public static ExceptionConfig getConfigInstance()
    {
        return configInstance;
    }

    public static void addExceptionMessage(String errorCode, String exceptionMessage)
    {
        map.put(errorCode, exceptionMessage);
    }

    public static String getExceptionMessage(String errorCode)
    {
        return (String)map.get(errorCode);
    }

    public static Map getMap()
    {
        return map;
    }

}
