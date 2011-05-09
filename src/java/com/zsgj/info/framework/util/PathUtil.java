package com.zsgj.info.framework.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import com.zsgj.info.framework.service.Service;

/**
 * �ṩ��һЩ�������class�ļ�λ������λ�ķ�����
 */
public class PathUtil {
	
	public static String getPkgPathFromClass(Class cls) throws IOException {
		String FSP = System.getProperty("file.separator");
		String path = getPathFromClass(cls);
		int lastPkgIndex = path.lastIndexOf(FSP);
	/*	String classname = cls.getName();
		int index = path.indexOf(classname);*/
		path = path.substring(0, lastPkgIndex+1);
		return path;
	}
	
	public static String getPkgPathFromClass(String className) throws IOException {
		String FSP = System.getProperty("file.separator");
		String path = getPathFromClass(className);
		int lastPkgIndex = path.lastIndexOf(FSP);
	/*	String classname = cls.getName();
		int index = path.indexOf(classname);*/
		path = path.substring(0, lastPkgIndex+1);
		return path;
	}

	/**
	 * ��ȡһ�����class�ļ����ڵľ���·���� ����������JDK������࣬Ҳ�������û��Զ�����࣬�����ǵ���������������ࡣ
	 * ֻҪ���ڱ������п��Ա����ص��࣬�����Զ�λ������class�ļ��ľ���·����
	 * @param cls һ�������Class����
	 * @return ������class�ļ�λ�õľ���·���� ���û�������Ķ��壬�򷵻�null��
	 */
	public static String getPathFromClass(Class cls) throws IOException {
		String path = null;
		if (cls == null) {
			throw new NullPointerException();
		}
		URL url = getClassLocationURL(cls);
		if (url != null) {
			path = url.getPath();
			if ("jar".equalsIgnoreCase(url.getProtocol())) {
				try {
					path = new URL(path).getPath();
				} catch (MalformedURLException e) {
				}
				int location = path.indexOf("!/");
				if (location != -1) {
					path = path.substring(0, location);
				}
			}
			File file = new File(path);
			path = file.getCanonicalPath();
		}
		return path;
	}
	
	public static String getPathFromClass(String className) throws IOException {
		String path = null;
		Class cls = null;
		 try {
		 	cls = Class.forName(className);
		 } catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("PathUtil: "+className + "ָ����class������");
		 }	
		 
		if (cls == null) {
			throw new NullPointerException();
		}
		URL url = getClassLocationURL(cls);
		if (url != null) {
			path = url.getPath();
			if ("jar".equalsIgnoreCase(url.getProtocol())) {
				try {
					path = new URL(path).getPath();
				} catch (MalformedURLException e) {
				}
				int location = path.indexOf("!/");
				if (location != -1) {
					path = path.substring(0, location);
				}
			}
			File file = new File(path);
			path = file.getCanonicalPath();
		}
		return path;
	}

	/**
	 * ͨ��������������ǿ��Ը������ǳ�����������ļ���λ������λĳ�����·����
	 * ���磺ĳ��txt�ļ�����ڳ����Test���ļ���·����../../resource/test.txt��
	 * ��ôʹ�ñ�����Path.getFullPathRelateClass("../../resource/test.txt",Test.class)
	 * �õ��Ľ����txt�ļ�����ϵͳ�еľ���·����
	 * 
	 * @param relatedPath
	 *            ���·��
	 * @param cls
	 *            ������λ����
	 * @return ���·������Ӧ�ľ���·��
	 * @throws IOException
	 *             ��Ϊ����������ѯ�ļ�ϵͳ�����Կ����׳�IO�쳣
	 */
	public static String getFullPathRelateClass(String relatedPath, Class cls)
			throws IOException {
		String path = null;
		if (relatedPath == null) {
			throw new NullPointerException();
		}
		String clsPath = getPathFromClass(cls);
		File clsFile = new File(clsPath);
		String tempPath = clsFile.getParent() + File.separator + relatedPath;
		File file = new File(tempPath);
		path = file.getCanonicalPath();
		return path;
	}
	
	 public static URL getClassLocationURL(String className) {
		 Class cls = null;
		 try {
		 	cls = Class.forName(className);
		 } catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("PathUtil: "+className + "ָ����class������");
		 }		
		 
		  URL result = null;
		  final String clsAsResource = className.replace('.', '/').concat(".class");
		  final ProtectionDomain pd = cls.getProtectionDomain();
		  // java.lang.Class contract does not specify
		  // if 'pd' can ever be null;
		  // it is not the case for Sun's implementations,
		  // but guard against null
		  // just in case:
		  if (pd != null) {
		   final CodeSource cs = pd.getCodeSource();
		   // 'cs' can be null depending on
		   // the classloader behavior:
		   if (cs != null)
		    result = cs.getLocation();

		   if (result != null) {
		    // Convert a code source location into
		    // a full class file location
		    // for some common cases:
		    if ("file".equals(result.getProtocol())) {
		     try {
		      if (result.toExternalForm().endsWith(".jar")
		        || result.toExternalForm().endsWith(".zip"))
		       result = new URL("jar:".concat(
		         result.toExternalForm()).concat("!/")
		         .concat(clsAsResource));
		      else if (new File(result.getFile()).isDirectory())
		    	  result = new URL(result, clsAsResource);
		     } catch (MalformedURLException ignore) {
		     }
		    }
		   }
		  }

		  if (result == null) {
		   // Try to find 'cls' definition as a resource;
		   // this is not
		   // document��d to be legal, but Sun's
		   // implementations seem to //allow this:
		   final ClassLoader clsLoader = cls.getClassLoader();
		   result = clsLoader != null ? clsLoader.getResource(clsAsResource)
		     : ClassLoader.getSystemResource(clsAsResource);
		  }
		  return result;
	 }
	/**
	  * ��ȡ���class�ļ�λ�õ�URL����������Ǳ���������ķ������������������á�
	  */
	 private static URL getClassLocationURL(final Class cls) {
	  if (cls == null)
	   throw new IllegalArgumentException("null input: cls");
	  URL result = null;
	  final String clsAsResource = cls.getName().replace('.', '/').concat(
	    ".class");
	  final ProtectionDomain pd = cls.getProtectionDomain();
	  // java.lang.Class contract does not specify
	  // if 'pd' can ever be null;
	  // it is not the case for Sun's implementations,
	  // but guard against null
	  // just in case:
	  if (pd != null) {
	   final CodeSource cs = pd.getCodeSource();
	   // 'cs' can be null depending on
	   // the classloader behavior:
	   if (cs != null)
	    result = cs.getLocation();

	   if (result != null) {
	    // Convert a code source location into
	    // a full class file location
	    // for some common cases:
	    if ("file".equals(result.getProtocol())) {
	     try {
	      if (result.toExternalForm().endsWith(".jar")
	        || result.toExternalForm().endsWith(".zip"))
	       result = new URL("jar:".concat(
	         result.toExternalForm()).concat("!/")
	         .concat(clsAsResource));
	      else if (new File(result.getFile()).isDirectory())
	    	  result = new URL(result, clsAsResource);
	     } catch (MalformedURLException ignore) {
	     }
	    }
	   }
	  }

	  if (result == null) {
	   // Try to find 'cls' definition as a resource;
	   // this is not
	   // document��d to be legal, but Sun's
	   // implementations seem to //allow this:
	   final ClassLoader clsLoader = cls.getClassLoader();
	   result = clsLoader != null ? clsLoader.getResource(clsAsResource)
	     : ClassLoader.getSystemResource(clsAsResource);
	  }
	  return result;
	 }

	 public static void main(String[] args) {
	/*  try {
	   System.out.println(getPathFromClass(ConfigItem.class));
	   System.out.println(getFullPathRelateClass("/test/abc/..",
	     Service.class));
	  } catch (Exception e) {
	   e.printStackTrace();
	  }*/
	 }



}
