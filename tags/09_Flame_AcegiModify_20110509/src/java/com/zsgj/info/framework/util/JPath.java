package com.zsgj.info.framework.util;

import java.net.URL;

/**
 * Java通用的绝对路径获取终极解决方案 获取classes,WEB-INF等的绝对路径
 * 
 * @测试系统: Windows Linux Unix
 * @测试环境: Tomcat6.0.10 Resin3.1.1 Jboss4.0.0/5.0.0 WebLogic9.1 WebSphere6.1
 *        WasCE1.1 Apusic4.0.3 JFox3 Jetty6.1.3
 * 
 * @author Jxva
 * @support Jxva.Com
 * 
 */
public class JPath {
	public static void main(String args[]) {
		JPath j = new JPath();
		System.out.println(getClassPath());
		System.out.println(getWebinfoPath());
		System.out.println(getJxvaHome());
		System.out.println(getAppPath());
		System.out.println(getRootPath());
		System.out.println(getJxvaFramework());
		System.out.println(j.getRealPath());
	}

	/**
	 * 
	 * @return ~/classes/
	 */
	public   static   String   getClassPath(){ 
		JPath   j=new   JPath(); 
		String   t=j.getRealPath(); 
		t=t.replaceAll( "file:/ ",   " "); // windows
		t=t.replaceAll( "file: ",   " "); // linux,unix
		t=t.replaceAll( "wsjar: ", " "); // websphere wsjar: has to at jar: before
		t=t.replaceAll( "jar: ", " "); // tomcat,jboss,resin,wasce,apusic
		t=t.replaceAll( "zip: ", " "); // weblogic
		t=t.replaceAll( "/./ ", "/ "); // weblogic
		// if this class be included .jar file,will replace "/lib/*.!/ " to "/classes/ "
		t=t.replaceAll( "/lib/([^\\ ' ']+)!/ ", "/classes/ ");   // jar
		t=t.split( "/classes/ ")[0]+ "/classes/ "; 
		return   t; 
}

	/**
	 * if this path include "WEB-INF " will return "WEB-INF " 's absolute path
	 * 
	 * @return ~/WEB-INF/
	 */
	public static String getWebinfoPath() {
		// remove string "classes/ "
		return getClassPath().substring(0, getClassPath().length() - 8);
	}

	/**
	 * 
	 * @return ~/JxvaHome/
	 */
	public static String getJxvaHome() {
		return getWebinfoPath() + "JxvaHome/ ";
	}

	/**
	 * 
	 * @return application 's path
	 */
	public static String getAppPath() {
		String[] s = getWebinfoPath().split("/ ");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length - 1; i++) {
			sb.append(s[i] + "/ ");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @return root 's path
	 */
	public static String getRootPath() {
		return getClassPath().split("/ ")[0] + "/ ";
	}

	/**
	 * JxvaFramework框架的公共目录,确保对根目录有可写权限,一般虚拟主机将无法使用
	 * 
	 * @return JxvaFrameowrk
	 */
	public static String getJxvaFramework() {
		return getRootPath() + "JxvaFramework/ ";
	}

	private String getRealPath() {
		String strClassName = getClass().getName();
		String strPackageName = " ";
		if (getClass().getPackage() != null)
			strPackageName = getClass().getPackage().getName();
		String strClassFileName = " ";
		if (!" ".equals(strPackageName))
			strClassFileName = strClassName.substring(
					strPackageName.length() + 1, strClassName.length());
		else
			strClassFileName = strClassName;
		URL url = getClass().getResource(strClassFileName + ".class ");
		String strURL = url.toString();
		strURL = strURL.replaceAll("%20 ", "   ");
		return strURL;
	}
}