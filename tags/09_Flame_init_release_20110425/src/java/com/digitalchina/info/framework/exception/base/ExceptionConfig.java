package com.digitalchina.info.framework.exception.base;

import java.util.HashMap;
import java.util.Map;

/**
 * ����뽫�����쳣��Ϣ����������ʱ���أ�����ѡ��ʹ�ô��ࡣ
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
