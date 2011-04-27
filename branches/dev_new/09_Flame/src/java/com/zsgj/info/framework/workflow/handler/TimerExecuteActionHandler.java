package com.zsgj.info.framework.workflow.handler;

import java.lang.reflect.Method;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.entity.ConfigUnitTimer;

public class TimerExecuteActionHandler  implements ActionHandler {
	
	ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	 /**
	  * 这个地方如果出错了容易出现无线循环
	  * timer中规定好了启动方法用execute();
	  */
	public void execute(ExecutionContext executionContext) throws Exception {
		
		String trans = "";
		/**********************取得上传文件中的类的完全限定名,并用反射区得其中的方法并执行*************************************/
		Long virtualDefinintionId = (Long)executionContext.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		Long nodeId = executionContext.getNode().getId();
		ConfigUnitTimer configTimer = cs.showConfigUnitTimer(virtualDefinintionId, nodeId);
		String timerPath = configTimer.getTimerPath();
		if(timerPath!=null&&!"".equals(timerPath)){
			String timerClass = timerPath.substring(1);
			System.out.println("============"+timerClass+"========");
			timerClass = timerClass.substring(0, timerClass.indexOf("."));
			System.out.println("============"+timerClass+"========");
			timerClass = timerClass.replace("/", ".");
			System.out.println("============"+timerClass+"============");
			
			Class timer = Class.forName(timerClass);
			Method method = timer.getMethod("add", "".getClass());
			Object object = timer.newInstance();
			trans = (String)method.invoke(object, "Y");
		}
		
		/**********************取得上传文件中的类的完全限定名*************************************/
		//主要操作就是
		if(trans==null||"".equals(trans)){
			trans = (String)executionContext.getTimer().getTransitionName();
		}		
		if(trans==null||"".equals(trans)){
			new Exception("timer上传文件中并没有指定转向值");
		}
		TaskInstance taskInstance = (TaskInstance)executionContext.getTimer().getTaskInstance();
		try{
			taskInstance.end(trans);
		}catch(Exception e){
			new Exception("Timer结束转向时出错");
		}
		
		executionContext.getJbpmContext().save(taskInstance);	
	}
	
//	public void execute(ExecutionContext executionContext) throws Exception {
//		String trans = (String)executionContext.getTimer().getName();
//		System.out.println("什么操作不做"+trans);
//		this.takeChargeOfTranstion(executionContext);
//	}
}
