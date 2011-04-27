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
	  * ����ط�������������׳�������ѭ��
	  * timer�й涨��������������execute();
	  */
	public void execute(ExecutionContext executionContext) throws Exception {
		
		String trans = "";
		/**********************ȡ���ϴ��ļ��е������ȫ�޶���,���÷����������еķ�����ִ��*************************************/
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
		
		/**********************ȡ���ϴ��ļ��е������ȫ�޶���*************************************/
		//��Ҫ��������
		if(trans==null||"".equals(trans)){
			trans = (String)executionContext.getTimer().getTransitionName();
		}		
		if(trans==null||"".equals(trans)){
			new Exception("timer�ϴ��ļ��в�û��ָ��ת��ֵ");
		}
		TaskInstance taskInstance = (TaskInstance)executionContext.getTimer().getTaskInstance();
		try{
			taskInstance.end(trans);
		}catch(Exception e){
			new Exception("Timer����ת��ʱ����");
		}
		
		executionContext.getJbpmContext().save(taskInstance);	
	}
	
//	public void execute(ExecutionContext executionContext) throws Exception {
//		String trans = (String)executionContext.getTimer().getName();
//		System.out.println("ʲô��������"+trans);
//		this.takeChargeOfTranstion(executionContext);
//	}
}
