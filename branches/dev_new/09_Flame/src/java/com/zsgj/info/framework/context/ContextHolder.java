package com.zsgj.info.framework.context;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.springframework.context.ApplicationContext;

import com.zsgj.info.framework.util.PropertiesUtil;


/**
 * ApplicationContext存放器, 便于当容器启动以后，在任意位置获得ApplicationContext
 * @Class Name ContextHolder
 * @Author xiaofeng
 * @Create In 2007-11-12
 */
public class ContextHolder {

	private final static ContextHolder instance = new ContextHolder();

	private static ApplicationContext ac;
	
	private static Locale local;
	
	private final static RuleBase ruleBase = RuleBaseFactory.newRuleBase();
	
	private static boolean isRuleBaseSetting;
	
	/**
	 * @Return the boolean isRuleBaseSetting
	 */
	public static boolean isRuleBaseSetting() {
		return isRuleBaseSetting;
	}

	/**
	 * @Param boolean isRuleBaseSetting to set
	 */
	public static void setRuleBaseSetting(boolean isRuleBaseSetting) {
		ContextHolder.isRuleBaseSetting = isRuleBaseSetting;
	}

	private ContextHolder() {
	}

	public static ContextHolder getInstance() {
		return instance;
	}

	public synchronized void setApplicationContext(ApplicationContext ac) {
		this.ac = ac;
	}

	public static ApplicationContext getApplicationContext() {
		return ac;
	}
	
	/**
	 * 提供bean定义的名称，返回spring管理的bean
	 * @Methods Name getBean
	 * @Create In 2008-10-6 By sa
	 * @param name
	 * @return Object
	 */
	public static Object getBean(String name){
		return ContextHolder.getInstance().getApplicationContext().getBean(name);
	}

	public static Locale getLocal() {
		return local;
	}

	public static void setLocal(Locale local) {
		ContextHolder.local = local;
	}

	/**
	 * @Return the RuleBase ruleBase
	 */
	public static RuleBase getRuleBase() {
		Package pkg =readRule();
		try {
			ruleBase.addPackage(pkg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ruleBase;
	}
	
	/**
	 * 通过保名获取所有的该包下的 Package
	 * @param pkgPath
	 * @return
	 */
	private static Package readRule(){
		PackageBuilder builder = new PackageBuilder();
		String pkgPathr=PropertiesUtil.getProperties("system.rulebase.path", "/com/digitalchina/info/framework/util/idgen/");
		//以','拆分,获取所有规则文件的包 
		String[] pkgPaths=pkgPathr.split(",");
		for(String pkgPath: pkgPaths ){
			String classpath = "";
			classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			
			classpath = classpath.replaceAll("%20", " "); 
			classpath += pkgPath.replace(".", "/");			
			String ss = classpath;
			if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS")!=-1&&classpath.startsWith("/")){
				ss = classpath.substring(1);
			}
			System.out.println("********** rule classpath: "+ss);
			File dir = new File(ss);
			if(!dir.exists()) {
				System.out.println("********** no rule classpath find.");
				return null;
			}
			File[] files = dir.listFiles();
			if(files==null||files.length==0) {
				System.out.println("********** no rule file find in the dir.");
				return null;
			}
			for(int i=0;i<files.length;i++) {
				String fileName = files[i].getName();
				if(fileName.endsWith(".drl")) {			
					String drl = "/"+pkgPath.replace(".", "/")+"/"+fileName;	
					Reader source = new InputStreamReader(ContextHolder.class.getResourceAsStream(drl));		
							
					try {
						builder.addPackageFromDrl( source );
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("********** add rule file: "+fileName);
				}
			}
		}	
		Package pkg = null;
		try {
			pkg = builder.getPackage();						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pkg;
	}
}
