package com.zsgj.info.framework.workflow.handler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.exe.ExecutionContext;

import com.zsgj.info.framework.workflow.action.BaseAction;
import com.zsgj.info.framework.workflow.base.JbpmConfig;


public abstract class BaseActionHandler{
	private static Log log;
	private static final long serialVersionUID = -943053708415871749L;
	static long interval = 0;
	static long MAX_INTERVAL = 1000*10;//10秒钟刷新一次
	static 
	{
		log = LogFactory.getLog(com.zsgj.info.framework.workflow.handler.BaseActionHandler.class);
		if(JbpmConfig.ACTION_TIMEOUT!=null) {
			MAX_INTERVAL = Integer.parseInt(JbpmConfig.ACTION_TIMEOUT)*1000;
		}
	}
	/**
	 * @Field long serialVersionUID 
	 */
	
	static HashMap<String,String> mapAction = new HashMap<String,String>();

	public abstract void execute(ExecutionContext executionContext) throws Exception;
	/**
	 * 获取用户自定义的ActionHandler,遵守指定的创建规则，
	 * 指定对应的流程定义名称、节点名称、事件类型和相应顺序。
	 * @Methods Name getActions
	 * @Create In 2008-3-19 By yang
	 * @ReturnType Map
	 */
	private Map getActions() {
		log.debug("getActions");
		if(interval==0) {
			interval = System.currentTimeMillis();
		}
		else {
			if(System.currentTimeMillis()-interval>MAX_INTERVAL) {
				interval = System.currentTimeMillis();
				mapAction.clear();
			}
			else {
				return mapAction;
			}
		}
		String classpath = "";
		classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		classpath = classpath.replaceAll("%20", " "); 

		classpath += JbpmConfig.ACTION_PACKAGE.replace(".", "/");	
		
		String ss = classpath;
		if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS")!=-1&&classpath.startsWith("/")){
			ss = classpath.substring(1);
		}
		System.out.println("**********************************************action classpath: "+ss);
		File file = new File(ss);
		if(!file.exists()) {
			System.out.println("*********************************************no action class dir find.");
			return null;
		}
		File[] dirs = file.listFiles();
		for(int i=0;i<dirs.length;i++) {//第一层目录
			File defdir = dirs[i];
			String defName = defdir.getName();
			if(!defdir.isDirectory()) {
				continue;
			}
			File[] classfiles = defdir.listFiles();
			for(int j=0;j<classfiles.length;j++) {
				String fileName = classfiles[j].getName();
				String post = ".class";
				if(!fileName.endsWith(post)) {
					continue;
				}
				String className = defName+"."+fileName.substring(0,fileName.length()-post.length());
				System.out.println("********************** find action className: "+defName+"."+className);
				try {
					String classPath = JbpmConfig.ACTION_PACKAGE+"."+className;
					//Class.forName(classPath).getSuperclass().ha
					Object o = Class.forName(classPath).newInstance();
					if(o instanceof BaseAction) {
						String key = ((BaseAction)o).getKey();	
						if(key!=null) {
							//为了减少内存占用，释放掉暂时不用的Action实例，仅保存类的路径，等到实际使用时再行实例化。
							//同时也是为了避免多线程安全问题，等到实际使用时再行实例化，保证不同线程的数据不会产生冲突。
							mapAction.put(key, classPath);
							System.out.println("********************** bind action className: "+defName+"."+className);
							o = null;
//							mapAction.put(key, (BaseAction)o);
//							System.out.println(key+":"+o.getClass().getName());
						}								
					}
				} catch (Exception e) {							
					e.printStackTrace();
				} 				
			}
		}
		return mapAction;
	}
	/**
	 * 根据关键字实例化Action对象并返回。
	 * @Methods Name getAction
	 * @Create In Jul 28, 2008 By yang
	 * @param key
	 * @return 
	 * @ReturnType BaseAction
	 */
	protected BaseAction getAction(String key) {
		if(getActions()==null) {
			return null;
		}
		String classPath = (String)getActions().get(key);
		if(classPath!=null) {
			try {
				Object o = Class.forName(classPath).newInstance();
				if(o instanceof BaseAction) {
					return (BaseAction)o;
				}
			} catch (Exception e) {							
				e.printStackTrace();
			} 	
		}
		return null;
	}
}
