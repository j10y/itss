package com.digitalchina.info.framework.util.asm;

public class BuildUtil {
	
	/*������ת����asm��ʶ����ַ��� */
	public static String  transferClassName(Class cls)
	{
		String clsname = cls.getName();
		return clsname.replace('.', '/');
	}
	
	/*������ת����asm��ʶ����ַ��� */
	public static String  transferClassName(String clsname)
	{
		return clsname.replace('.', '/');
	}
}
