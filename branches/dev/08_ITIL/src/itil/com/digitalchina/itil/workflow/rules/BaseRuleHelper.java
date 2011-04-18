package com.digitalchina.itil.workflow.rules;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.service.Service;


/**
 * java������
 * @Class Name BaseRuleHelper
 * @Author yang tao
 * @Create In Mar 4, 2009
 */
public class BaseRuleHelper  {
	Service service = (Service)ContextHolder.getBean("baseService");
	/**
	 * ѡȡ���ʵĹ����ļ�,����ʱ��
	 * @Methods Name getRightRuleFile
	 * @Create In Mar 4, 2009
	 * @param time
	 * @return 
	 * @ReturnType List<String>
	 */
	public List<String> getRightRuleFile(Date time){
		List<String> rulePaths = new ArrayList<String>();
//		List<RuleFile> ruleFiles = service.findAll(RuleFile.class);
//		for(RuleFile ruleFile: ruleFiles) {
//			if(ruleFile.getStart()==null||ruleFile.getStart().before(time)) {
//				if(ruleFile.getEndto()==null||ruleFile.getEndto().after(time)) {
//					rulePaths.add(ruleFile.getPath());
//				}
//			}
//		}
		
		//FIXME,��ʱ����
		rulePaths.add("/com/digitalchina/ibmb2b/order/rules/OrderProductLine.drl");
		
		return rulePaths;
	}
	/**
	 * ѡȡ���ʵĹ����ļ�,����ʱ�䣬������
	 * @Methods Name getRightRuleFile
	 *@Create In Mar 4, 2009
	 * @param time
	 * @param pkg
	 * @return 
	 * @ReturnType List<String>
	 */
	public static List<String> getRightRuleFile(Date time, String pkg){
		List rulePaths = new ArrayList();
		
		//FIXME
		rulePaths.add("/com/digitalchina/info/framework/workflow/rules/OrderProductLine.drl");
		
		return rulePaths;
	}
	
	/**
	 * ���ع����ļ�
	 * @Methods Name readRule
	 *@Create In Mar 4, 2009
	 * @param rulePath
	 * @return 
	 * @ReturnType RuleBase
	 */
	public static RuleBase readRule(List<String> rulePaths){		
		PackageBuilder builder = new PackageBuilder();
		for(int i=0;i<rulePaths.size();i++) {		
			String drl = rulePaths.get(i);	
			Reader source = new InputStreamReader(BaseRuleHelper.class.getResourceAsStream(drl));		
					
			try {
				builder.addPackageFromDrl( source );
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("********** add rule file: "+drl);
		}
				
		RuleBase ruleBase = null;
		try {
			Package pkg = builder.getPackage();			
			ruleBase = RuleBaseFactory.newRuleBase();
			ruleBase.addPackage( pkg );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ruleBase;
	}
	
	/**
	 * ��ȡͬһ�����µ����й����ļ�
	 * @Methods Name readRule
	 * @Create In Mar 4, 2009
	 * @param pkg �����ļ����ڰ���
	 * @return 
	 * @ReturnType RuleBase
	 */
	 
	public static RuleBase readRule(String pkgPath){		
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
		PackageBuilder builder = new PackageBuilder();
		for(int i=0;i<files.length;i++) {
			String fileName = files[i].getName();
			if(fileName.endsWith(".drl")) {			
				String drl = "/"+pkgPath.replace(".", "/")+"/"+fileName;	
				Reader source = new InputStreamReader(BaseRuleHelper.class.getResourceAsStream(drl));		
						
				try {
					builder.addPackageFromDrl( source );
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("********** add rule file: "+fileName);
			}
		}
				
		RuleBase ruleBase = null;
		try {
			Package pkg = builder.getPackage();			
			ruleBase = RuleBaseFactory.newRuleBase();
			ruleBase.addPackage( pkg );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ruleBase;
	}
	/**
	 * �ж�ʱ����Ч
	 * @Methods Name validateTime
	 *@Create In Mar 4, 2009
	 * @param time
	 * @return 
	 * @ReturnType boolean
	 */
	public boolean validateTime(Date time,String strStart,String strEnd){		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date START = null;
		Date END = null;
		try {
			START = sdf.parse(strStart);
			END = sdf.parse(strEnd);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		if(time.after(START)&&time.before(END)){
			return true;
		}
		return false;
	}
}
