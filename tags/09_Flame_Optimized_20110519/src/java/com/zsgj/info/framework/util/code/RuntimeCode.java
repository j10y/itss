package com.zsgj.info.framework.util.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import com.zsgj.info.framework.util.PathUtil;

/**
 * ��̬�����࣬MAPPING
 * @Class Name RuntimeCode
 * @Author LM
 * @Create In Apr 13, 2010
 */
public class RuntimeCode {
	static com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
	static String FSP = System.getProperty("file.separator");
	
	/**
	 * 
	 * @Methods Name genEntityAndClass
	 * @Create In 2008-12-2 By sa
	 * @param classesDIR WEB-INF/classes��Ŀ¼
	 * @param sourceClassPkg Ҫ����Դ�ļ�java�İ�·��,��ŷָ�
	 * @param sourceClassName Ҫ����Դ�ļ�������
	 * @param code ʵ�ʵ�JAVA����
	 * @param targetPkgClass Ŀ��·�����մ����·��
	 * @return String
	 */
	public static String genEntityAndClass(
			String classesDIR, 
			String sourceClassPkg, 
			String sourceClassName, 
			String code, 
			Class targetPkgClass){
		
		try {
			
			String pkgDir = sourceClassPkg.replace(".", FSP);
			
			File javaFile = compile(classesDIR, sourceClassName, code);
			
			File classFile = getRunFile(pkgDir, javaFile);
			
			String targetDIR = PathUtil.getPkgPathFromClass(targetPkgClass);
			//String targetDIR = PathUtil.getClassLocationURL(targetPkgClass.getName());
			
			fileCopy(javaFile, targetDIR);
			
			fileCopy(classFile, targetDIR);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return sourceClassPkg+ FSP+ sourceClassName; 
		
	}
	
	
	public static String genEntityAndClass(
			String classesDIR, 
			String sourceClassPkg, 
			String sourceClassName, 
			String code, 
			String targetClassName){
		
		try {
			
			String pkgDir = sourceClassPkg.replace(".", FSP);
			
			File javaFile = compile(classesDIR, sourceClassName, code);
			
			File classFile = getRunFile(pkgDir, javaFile);
			
			String targetDIR = PathUtil.getPkgPathFromClass(targetClassName);
			//String targetDIR = PathUtil.getClassLocationURL(targetPkgClass.getName());
			
			fileCopy(javaFile, targetDIR);
			
			fileCopy(classFile, targetDIR);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return sourceClassPkg+ FSP+ sourceClassName; 
		
	}
	
//	public static void webtest(){
//		String str = "D:/jboss-4.2.2.GA_zys/server/default/deploy/itil.war/WEB-INF/classes";
//		genEntityAndClass(str, "com.digitalchina.itil.config.entity", "Server", "", null);
//	}
	
	/** �ȴ��û�����JavaCode,Ȼ����롢ִ��

	public static void main(String[] args) throws Exception {
		String str= "package com.digitalchina.info.appframework.extjs.page;";

		str = str+ "public class ConfigItem {";
		str = str+ "	public static void main(String[] args) throws Exception {";
		str = str+ "		System.out.println(\"hello\");";

		str = str+ "	}";
		str = str+ "}";
		//run(compile(str));
		File javaFile = compile("ConfigExtend", str);

		File classFile = getRunFile(javaFile);
	
		String pkgPath = PathUtil.getPkgPathFromClass(ConfigItemFinanceInfo.class);
	
		fileCopy(classFile, pkgPath);
		
		System.out.println("dd");
		
	} */
	

	/**
	 * ����JavaCode,������ʱ�ļ�����
	 * @throws IOException
	 */
	public synchronized static File compile(String classPath, String className, String code) throws IOException {
		String LSP = System.getProperty("line.separator");
		com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
		
		File file;
		// ���û���ǰ�ļ�Ŀ¼����һ����ʱ�����ļ�
		/*file = File.createTempFile("JavaRuntime", ".java", new File(System
				.getProperty("user.dir")));*/
		
		file = new File(className + ".java");
		// ��������˳�ʱ,ɾ������ʱjavaԴ�ļ�
		//file.deleteOnExit();
		// ����ļ�����������
		String filename = file.getName();
		String classname = getClassName(filename);
		// ������������ļ�
		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		//out.println(code);
	/*	out.println("package "+pkgName+";");
		out.println("public class " + classname + " {"); */
		out.println(code);
		//out.println("}"); 
		// �ر��ļ���
		out.flush();
		out.close();
		
		// ��������ļ�
		
		//classPath = classPath.replace("/", LSP);
		//{"-classpath","c:\\foo\\bar.jar;.","-d","c:\\","c:\\Some.java"}��
		String[] args = new String[] {
				"-classpath", classPath+";.",
				"-d", System.getProperty("user.dir"), filename };//
		// ���ر����״̬����
		int status = javac.compile(args);
		// �������״̬
		return file;
	}

	
	public static void fileCopy(File sfile, String targetDir){
		String FSP = System.getProperty("file.separator");
		String LSP = System.getProperty("line.separator");
		String filename = sfile.getName();
		try {
			//targetDir = targetDir.replace("/", targetDir).replace("\\", targetDir);
			FileInputStream fi = new FileInputStream(sfile);
			FileOutputStream fo = new FileOutputStream(targetDir+filename);
			byte[] bs = new byte[1024];
			int len;
			while ((len = fi.read(bs)) != -1) {
				fo.write(bs, 0, len);
			}
			fi.close();
			fo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static File getRunFile(String pkgDIR, File javaFile){
		String FSP = System.getProperty("file.separator");
		String filename = javaFile.getName();
		String classname = getClassName(filename);
		File file = new File(javaFile.getParent(), pkgDIR+ FSP+ classname + ".class");
		return file;

	}
	/** ִ�иոձ�������ļ� */
	public static synchronized void run(File file) {
		// ��������˳�ʱ,ɾ������ʱ��������ļ�
	//	String classname = file.getName().replace(".java", "");
		//String classFullName = file.getParent()+"\\"+ classname+ ".class";
		
		String filename = file.getName();
		String classname = getClassName(filename);
		
		
		new File(file.getParent(), classname + ".class").deleteOnExit();
		try {
			// ���������
			Class cls = Class.forName(classname);
			// ӳ��main����
			Method main = cls.getMethod("main", new Class[] { String[].class });
			// ִ��main����
			main.invoke(null, new Object[] { new String[0] });
		} catch (Exception se) {
			se.printStackTrace();
		}

	}

	/** ��ӡ������Ϣ */
	public static void debug(String msg) {
		System.err.println(msg);
	}

	/** ����һ��javaԴ�ļ���������� */
	public static String getClassName(final String filename) {
		return filename.substring(0, filename.length() - 5);

	}

}
