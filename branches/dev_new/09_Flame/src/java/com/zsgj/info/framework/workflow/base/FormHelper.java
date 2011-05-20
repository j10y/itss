package com.zsgj.info.framework.workflow.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.zsgj.info.framework.context.ContextHolder;
/**
 * 支持Form按层次逐级向上查找
 * @Class Name FormHelper
 * @Author yang
 * @Create In Aug 1, 2008
 */
public class FormHelper {
	final static String prefix = "workflow.form.";
	static Log log = LogFactory.getLog(FormHelper.class);
	final static String defaultPath = "workflow.form.default";
	

	/**
	 * 按名称查找form
	 * @Methods Name findFormPath
	 * @Create In Aug 1, 2008 By yang
	 * @param formName
	 * @return 
	 * @ReturnType String
	 */
	private static String findFormPath(String formName){		
		String form = getProperties(prefix+formName,findDefault());//先执行default
		if(form!=null) {
			form = form.trim();
		}
		return form;  
	}
	/**
	 * 查找默认的form
	 * @Methods Name findDefault
	 * @Create In Aug 1, 2008 By yang
	 * @return 
	 * @ReturnType String
	 */
	private static String findDefault(){		
		String form = getProperties(defaultPath,null);
		if(form == null) {
			throw new RuntimeException("No default form defined.");
		}
		return form.trim();  
	}
	
	/**
	 * 从定义层次开始查找Form
	 * @Methods Name findForm
	 * @Create In Aug 1, 2008 By yang
	 * @param definitionName
	 * @return 
	 * @ReturnType String
	 */
	public static String findForm(String definitionName){
		if(definitionName == null) {
			return findDefault();
		}
		String formPath = findFormPath(definitionName);
		if(formPath == null) {
			formPath = findDefault();
		}	
		return formPath;
	}
	
	/**
	 * 从节点层次开始查找Form
	 * @Methods Name findForm
	 * @Create In Aug 1, 2008 By yang
	 * @param definitionName
	 * @param nodeName
	 * @return 
	 * @ReturnType String
	 */
	public static String findForm(String definitionName,String nodeName){
		if(nodeName==null) {
			return findForm(definitionName);
		}
		String formName = definitionName+"_"+nodeName;
		String formPath = findFormPath(formName);
		if(formPath == null) {
			formName = definitionName;
			formPath = findFormPath(formName);
			if(formPath == null) {
				formPath = findDefault();
			}
		}		
		return formPath;
	}
	
	/**
	 * 从任务层次开始查找Form
	 * @Methods Name findForm
	 * @Create In Aug 1, 2008 By yang
	 * @param definitionName
	 * @param nodeName
	 * @param taskName
	 * @return 
	 * @ReturnType String
	 */
	public static String findForm(String definitionName,String nodeName, String taskName){
		if(taskName == null) {
			return findForm(definitionName,nodeName);
		}
		else if(nodeName == null) {
			return findForm(definitionName);
		}
		String formName = definitionName+"_"+nodeName+"_"+taskName;
		String formPath = findFormPath(formName);
		if(formPath == null) {
			formName = definitionName+"_"+nodeName;
			formPath = findFormPath(formName);
			if(formPath == null) {
				formName = definitionName;
				formPath = findFormPath(formName);
				if(formPath == null) {
					formPath = findDefault();
				}
			}
		}		
		return formPath;
	}
	
	@SuppressWarnings("static-access")
	private static String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try{
			message = appContext.getMessage(Key, new Object[0],ContextHolder.getInstance().getLocal());
			return (message != null && !message.equals("") ? message : defaultValue);
		}catch(Exception e){
			return defaultValue;
		}
	}
	
	public static void main(String[] argv) {
		
		String dn = "customer";
		String nn = "检查";
		String tn = "检查信息";
		
		String form = "";
		try {
			form = FormHelper.findForm(dn,nn,tn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(form);
	}
	

	
}
